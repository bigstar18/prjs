package gnnt.trade.bank;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.hxb.HXBBankDAO;
import gnnt.trade.bank.util.ErrorCode;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfoValue;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class HXBCapitalProcessor
  extends CapitalProcessor
{
  private static final int INMONEY = 0;
  private static final int OUTMONEY = 1;
  private static final int RATE = 2;
  private static HXBBankDAO DAO;
  
  public HXBCapitalProcessor()
  {
    try
    {
      DAO = BankDAOFactory.getHXBDAO();
    }
    catch (Exception e)
    {
      log("初始化华夏DAO对象处理器失败：" + Tool.getExceptionTrace(e));
    }
  }
  
  public String getConfig(String key)
  {
    return Tool.getConfig(key);
  }
  
  public ReturnValue moneyInAudit(long actionID, boolean funFlag)
  {
    log("actionID[" + actionID + "]funFlag[" + funFlag + "]" + Tool.fmtTime(new Date()));
    log("入金申请审核处理中的信息 moneyInAudit actionID[" + actionID + "]funFlag[" + funFlag + "]");
    Connection conn = null;
    Timestamp curTime = new Timestamp(System.currentTimeMillis());
    CapitalValue capital = null;
    ReturnValue result = new ReturnValue();
    try
    {
      conn = DAO.getConnection();
      Vector<CapitalValue> list = DAO.getCapitalInfoList(" and actionid=" + actionID, conn);
      ReturnValue localReturnValue1;
      for (int i = 0; i < list.size(); i++)
      {
        val = (CapitalValue)list.get(i);
        if ((val.type == 1) || (val.type == 0))
        {
          capital = val;
          result.actionId = capital.actionID;
          result.bankTime = Tool.fmtTime(curTime);
          result.funID = capital.funID;
          if ((capital.status == 0) || (capital.status == 1))
          {
            result.result = -20042L;
            result.remark = ("本条记录[" + actionID + "]已经有人处理");
            log(result.remark);
            localReturnValue1 = result;return localReturnValue1;
          }
        }
      }
      if (capital == null)
      {
        result.result = -930000L;
        result.remark = ("未查询到流水[" + actionID + "]信息");
        localReturnValue1 = result;return localReturnValue1;
      }
      if (funFlag) {
        try
        {
          conn.setAutoCommit(false);
          if (capital.type == 1)
          {
            dataProcess.updateFundsFull(capital.bankID, capital.firmID, String.valueOf(capital.actionID), capital.money, 1, conn);
            dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);
            
            bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
            DAO.modCapitalInfoStatus(capital.iD, result.funID, 0, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "审核通过", conn);
          }
          else if (capital.type == 0)
          {
            dataProcess.updateFundsFull(capital.bankID, capital.firmID, String.valueOf(capital.actionID), capital.money, 0, conn);
            DAO.modCapitalInfoStatus(capital.iD, result.funID, 0, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "审核通过", conn);
          }
          DAO.modCapitalInfoTrader(capital.iD, "manual", conn);
          log(actionID + "入金申请审核通过");
          conn.commit();
        }
        catch (SQLException e)
        {
          conn.rollback();
          log(actionID + "入金申请审核出金SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
          throw e;
        }
        finally
        {
          conn.setAutoCommit(true);
        }
      } else {
        try
        {
          conn.setAutoCommit(false);
          if (capital.type == 1)
          {
            dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);
            bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
            DAO.modCapitalInfoStatus(capital.iD, result.funID, 1, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "审核拒绝", conn);
          }
          else if (capital.type == 0)
          {
            DAO.modCapitalInfoStatus(capital.iD, result.funID, 1, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "审核拒绝", conn);
          }
          DAO.modCapitalInfoTrader(capital.iD, "manual", conn);
          log(actionID + "入金申请审核拒绝");
          conn.commit();
        }
        catch (SQLException e)
        {
          conn.rollback();
          log(actionID + "入金申请审核出金SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
          throw e;
        }
        finally
        {
          conn.setAutoCommit(true);
        }
      }
    }
    catch (SQLException e)
    {
      result.result = -30004L;
      result.remark = ("入金申请处理[" + actionID + "]数据库异常");
      log(actionID + "入金申请审核出金SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      result.result = -30005L;
      result.remark = ("入金申请处理[" + actionID + "]系统异常");
      log(Tool.getExceptionTrace(e));
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
  
  public int bankFrozenFuns(String firmID, String bankID, String account, double money, Connection conn)
    throws SQLException
  {
    log("银行接口资金冻结firmID[" + firmID + "]bankID[" + bankID + "]account[" + account + "]money[" + money + "]" + Tool.fmtTime(new Date()));
    int result = 0;
    if ((firmID == null) || (firmID.trim().length() <= 0) || (bankID == null) || (bankID.trim().length() <= 0))
    {
      log("冻结(解冻)资金，信息不完整 firmID=" + firmID + " bankID=" + bankID);
      throw new SQLException("冻结(解冻)资金，信息不完整 firmID=" + firmID + " bankID=" + bankID);
    }
    String filter = " and firmID='" + firmID.trim() + "' and bankID='" + bankID.trim() + "' ";
    if (account != null) {
      filter = filter + " and account='" + account.trim() + "'";
    }
    String filter2 = filter + " for update";
    try
    {
      Vector<CorrespondValue> vector = DAO.getCorrespondList(filter2, conn);
      if ((vector != null) && (vector.size() > 0))
      {
        CorrespondValue cv = (CorrespondValue)vector.get(0);
        if (cv.frozenFuns + money < 0.0D) {
          throw new SQLException("交易商冻结资金不足以释放当前资金：冻结资金[" + cv.frozenFuns + "]当前资金[" + money + "]");
        }
        result = DAO.modBankFrozenFuns(filter, money, conn);
      }
      else
      {
        throw new SQLException("没有找到本条记录+" + filter);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    return result;
  }
  
  public ReturnValue synchroAccount(CorrespondValue correspondValue)
  {
    log("华夏预签约华夏银行 synchroAccount correspondValue:" + (correspondValue == null ? "为 null" : new StringBuilder("\n").append(correspondValue.toString()).append("\n").toString()));
    ReturnValue result = new ReturnValue();
    
    String defaultAccount = Tool.getConfig("DefaultAccount");
    Connection conn = null;
    if ((correspondValue.bankID == null) || (correspondValue.bankID.length() == 0) || 
      (correspondValue.firmID == null) || (correspondValue.firmID.length() == 0))
    {
      result.result = -640000L;
      result.remark = "华夏预签约失败，信息不完整";
      return result;
    }
    try
    {
      conn = DAO.getConnection();
      BankValue bv = DAO.getBank(correspondValue.bankID);
      ReturnValue localReturnValue1;
      if (bv.validFlag != 0)
      {
        result.result = -920017L;
        result.remark = "华夏预签约失败，银行被禁用";
        log("华夏预签约，银行被禁用");
        localReturnValue1 = result;return localReturnValue1;
      }
      int tradeFlag = getTradeTime(bv.addBeginTime, bv.addEndTime);
      if (tradeFlag == 1)
      {
        result.result = -920004L;
        result.remark = "华夏预签约失败，交易时间未到";
        log("华夏预签约失败，交易时间未到");
        localReturnValue1 = result;return localReturnValue1;
      }
      if (tradeFlag == 2)
      {
        result.result = -920005L;
        result.remark = "华夏预签约失败，交易时间已过";
        log("华夏预签约失败，交易时间已过");
        localReturnValue1 = result;return localReturnValue1;
      }
      if (DAO.getFirm(correspondValue.firmID, conn) == null)
      {
        result.result = -640002L;
        result.remark = "华夏预签约失败，交易商不存在";
        log("华夏预签约失败，交易账号不存在，错误码=" + result.result);
        localReturnValue1 = result;return localReturnValue1;
      }
      if (DAO.getCorrespondList(" and bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID + "' and (account='" + correspondValue.account + "' or account='" + defaultAccount + "') and isopen=1", conn).size() > 0)
      {
        result.result = -640004L;
        result.remark = "华夏预签约失败，账号已注册";
        log("华夏预签约失败，账号已注册，错误码=" + result.result);
        localReturnValue1 = result;return localReturnValue1;
      }
      if (DAO.getCorrespondList(" and firmID='" + correspondValue.firmID + "' ", conn).size() > 0)
      {
        BankAdapterRMI bankadapter = getAdapter(correspondValue.bankID);
        ReturnValue returnValue = bankadapter.synchroAccount(correspondValue);
        if ((returnValue != null) && (returnValue.result == 0L))
        {
          List<CorrespondValue> cvresult = DAO.getCorrespondList(" and trim(firmid)='" + correspondValue.firmID.trim() + "' and trim(bankID) is  null");
          if ((cvresult == null) || (cvresult.size() <= 0))
          {
            cvresult = DAO.getCorrespondList(" and trim(firmid)='" + correspondValue.firmID.trim() + "' and trim(bankID)='" + correspondValue.bankID.trim() + "'");
            if ((cvresult != null) && (cvresult.size() > 0)) {
              break label1008;
            }
            Vector<FirmValue> vfv = DAO.getFirmList(" and firmid='" + correspondValue.firmID + "'");
            if ((vfv == null) || (vfv.size() != 1))
            {
              result.result = -40002L;
              result.remark = "通过签约号查询客户信息异常";
              localReturnValue1 = result;return localReturnValue1;
            }
            FirmValue fv = (FirmValue)vfv.get(0);
            if ((correspondValue.card == null) || (!correspondValue.card.equals(fv.card)))
            {
              result.result = -20020L;
              result.remark = "客户证件号码错误";
              localReturnValue1 = result;return localReturnValue1;
            }
            CorrespondValue corr = new CorrespondValue();
            corr.account = correspondValue.account;
            corr.firmID = fv.firmID;
            corr.contact = fv.contact;
            corr.isOpen = 0;
            corr.status = 1;
            corr.accountName = correspondValue.accountName;
            corr.card = correspondValue.card;
            corr.cardType = correspondValue.cardType;
            corr.account1 = correspondValue.account1;
            corr.bankID = correspondValue.bankID;
            
            conn = DAO.getConnection();
            try
            {
              conn.setAutoCommit(false);
              
              DAO.addCorrespond(corr, conn);
              conn.commit();
            }
            catch (SQLException er)
            {
              conn.rollback();
              throw er;
            }
            finally
            {
              conn.setAutoCommit(true);
            }
          }
          DAO.changeBankID(correspondValue.bankID, correspondValue.firmID);
          label1008:
          log("华夏预签约状态" + result);
        }
        else
        {
          log(returnValue.remark);
          result.result = -640001L;
          result.remark = returnValue.remark;
          log("华夏预签约失败，错误码=" + result);
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      result.result = -640005L;
      result.remark = "华夏预签约错误，数据库操作失败";
      log("华夏预签约异常，错误码=" + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.result = -640006L;
      result.remark = "华夏预签约错误，系统异常";
      log("华夏预签约Exception，错误码" + Tool.getExceptionTrace(e));
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
  
  public ReturnValue relevanceAccount(CorrespondValue cv)
  {
    log("银行发起绑定，市场将银行子账号和子账号名称等添加进去" + Tool.fmtTime(new Date()));
    log(cv.toString());
    rv = new ReturnValue();
    if (cv == null)
    {
      rv.result = -40001L;
      rv.remark = "银行发起绑定，传入的信息包为空";
      return rv;
    }
    if (cv.account1 == null)
    {
      rv.result = -40001L;
      rv.remark = ((String)ErrorCode.error.get(Long.valueOf(-40001L)));
      return rv;
    }
    if (cv.firmID == null)
    {
      rv.result = -40002L;
      rv.remark = ((String)ErrorCode.error.get(Long.valueOf(-40002L)));
      return rv;
    }
    try
    {
      synchronized (cv.firmID)
      {
        Connection conn = null;
        try
        {
          if ((cv.contact == null) || (cv.contact.trim().length() <= 0)) {
            cv.contact = cv.firmID;
          }
          List<CorrespondValue> cvresult = DAO.getCorrespondList(" and bankID='" + cv.bankID + "' and firmID='" + cv.firmID + "'");
          ReturnValue localReturnValue4;
          if ((cvresult == null) || (cvresult.size() <= 0))
          {
            cvresult = DAO.getCorrespondList(" and trim(firmid)='" + cv.firmID.trim() + "' and trim(bankID) is  null");
            if ((cvresult != null) && (cvresult.size() > 0))
            {
              CorrespondValue corr = (CorrespondValue)cvresult.get(0);
              corr.account = cv.account;
              corr.isOpen = 1;
              corr.status = 0;
              corr.accountName = cv.accountName;
              corr.card = cv.card;
              corr.cardType = cv.cardType;
              corr.account1 = cv.account1;
              corr.bankID = cv.bankID;
              rv.actionId = getMktActionID();
              conn = DAO.getConnection();
              try
              {
                conn.setAutoCommit(false);
                rv = dataProcess.changeFirmIsOpen(corr.firmID, 1, corr.bankID, conn);
                if (rv.result == 1L) {
                  rv.result = 0L;
                }
                System.out.println("》》》》》》》》》》》》》》 ①《《《《《《《《《《《《测试chenml" + rv.toString());
                if (rv.result < 0L)
                {
                  conn.rollback();
                  ReturnValue localReturnValue1 = rv;
                  






                  conn.setAutoCommit(true);
                  





















































































                  DAO.closeStatement(null, null, conn);return localReturnValue1;
                }
                DAO.openCorrespond(corr, conn);
                conn.commit();
              }
              catch (SQLException er)
              {
                conn.rollback();
                throw er;
              }
              finally
              {
                conn.setAutoCommit(true);
              }
              conn.setAutoCommit(true);
            }
            else
            {
              Vector<FirmValue> vfv = DAO.getFirmList(" and firmid='" + cv.firmID + "'");
              if ((vfv == null) || (vfv.size() != 1))
              {
                rv.result = -40002L;
                rv.remark = "通过签约号查询客户信息异常";
                localReturnValue4 = rv;
                














































































                DAO.closeStatement(null, null, conn);return localReturnValue4;
              }
              FirmValue fv = (FirmValue)vfv.get(0);
              if ((cv.card == null) || (!cv.card.equals(fv.card)))
              {
                rv.result = -20020L;
                rv.remark = "客户证件号码错误";
                localReturnValue4 = rv;
                








































































                DAO.closeStatement(null, null, conn);return localReturnValue4;
              }
              cv.firmID = fv.firmID;
              cv.contact = fv.contact;
              cv.frozenFuns = 0.0D;
              cv.isOpen = 1;
              cv.status = 0;
              cv.opentime = new Date();
              rv.actionId = getMktActionID();
              conn = DAO.getConnection();
              try
              {
                conn.setAutoCommit(false);
                rv = dataProcess.changeFirmIsOpen(fv.firmID, 1, cv.bankID, conn);
                if (rv.result == 1L) {
                  rv.result = 0L;
                }
                System.out.println("》》》》》》》》》》》》》》②《《《《《《《《《《《《测试chenml" + rv.toString());
                if (rv.result < 0L)
                {
                  conn.rollback();
                  ReturnValue localReturnValue3 = rv;
                  






                  conn.setAutoCommit(true);
                  













































                  DAO.closeStatement(null, null, conn);return localReturnValue3;
                }
                DAO.addCorrespond(cv, conn);
                conn.commit();
              }
              catch (SQLException er)
              {
                conn.rollback();
                throw er;
              }
              finally
              {
                conn.setAutoCommit(true);
              }
              conn.setAutoCommit(true);
            }
          }
          else
          {
            if (cvresult.size() != 1)
            {
              rv.result = -920014L;
              rv.remark = "通过银行编号、签约号查询出信息重复";
              localReturnValue4 = rv;
              







































              DAO.closeStatement(null, null, conn);return localReturnValue4;
            }
            CorrespondValue cv2 = (CorrespondValue)cvresult.get(0);
            if ((cv.card == null) || (!cv.card.equals(cv2.card)))
            {
              rv.result = -20020L;
              rv.remark = "客户证件号码错误";
              localReturnValue4 = rv;
              

































              DAO.closeStatement(null, null, conn);return localReturnValue4;
            }
            rv.actionId = getMktActionID();
            conn = DAO.getConnection();
            try
            {
              conn.setAutoCommit(false);
              rv = dataProcess.changeFirmIsOpen(cv2.firmID, 1, cv2.bankID, conn);
              if (rv.result == 1L) {
                rv.result = 0L;
              }
              System.out.println("》》》》》》》》》》》》》》③《《《《《《《《《《《《测试chenml" + rv.toString());
              if (rv.result < 0L)
              {
                conn.rollback();
                ReturnValue localReturnValue2 = rv;
                








                conn.setAutoCommit(true);
                










                DAO.closeStatement(null, null, conn);return localReturnValue2;
              }
              cv.contact = cv2.contact;
              cv.id = cv2.id;
              DAO.openCorrespond(cv, conn);
              conn.commit();
            }
            catch (SQLException er)
            {
              conn.rollback();
              throw er;
            }
            finally
            {
              conn.setAutoCommit(true);
            }
            conn.setAutoCommit(true);
          }
        }
        catch (SQLException e)
        {
          rv.result = -40006L;
          rv.remark = "签约时数据库异常";
          log("银行开户 交易账号代码与银行账号对应SQLException," + Tool.getExceptionTrace(e));
        }
        catch (Exception e)
        {
          rv.result = -40007L;
          rv.remark = "签约时系统异常";
          log("银行开户 交易账号代码与银行账号对应SQLException," + Tool.getExceptionTrace(e));
        }
        finally
        {
          DAO.closeStatement(null, null, conn);
        }
      }
      return rv;
    }
    catch (Exception e)
    {
      rv.result = -40007L;
      rv.remark = "处理器修改交易商信息异常";
      e.printStackTrace();
    }
  }
  
  public ReturnValue inMoneyMarketHx(InMoneyMarket imm)
  {
    log("市场入金 inMoneyMarket imm:" + (imm == null ? "为 null" : new StringBuilder("\n").append(imm.toString()).append("\n").toString()));
    result = new ReturnValue();
    long capitalID = 0L;
    Connection conn = null;
    Timestamp curTime = new Timestamp(System.currentTimeMillis());
    try
    {
      ReturnValue localReturnValue1;
      if (imm.money <= 0.0D)
      {
        result.result = -920019L;
        result.remark = "入金金额必须为正数";
        localReturnValue1 = result;return localReturnValue1;
      }
      conn = DAO.getConnection();
      CorrespondValue cv = getCorrespond(imm.bankID, imm.firmID, imm.contact, imm.account);
      if (cv == null)
      {
        result.result = -920013L;
        result.remark = "未查询到交易账号信息";
        localReturnValue1 = result;return localReturnValue1;
      }
      imm.contact = cv.contact;
      imm.account = cv.account;
      imm.firmID = cv.firmID;
      imm.bankFlag = String.valueOf(cv.isCrossline);
      result = ifbankTrade(imm.bankID, imm.firmID, imm.contact, 0, 0);
      if (result.result != 0L)
      {
        localReturnValue1 = result;return localReturnValue1;
      }
      if (cv.isOpen != 1)
      {
        result.result = -10019L;
        result.remark = "交易账号未签约";
        localReturnValue1 = result;return localReturnValue1;
      }
      if (cv.status != 0)
      {
        result.result = -10020L;
        result.remark = "交易账号不可用";
        localReturnValue1 = result;return localReturnValue1;
      }
      log(imm.toString());
      result.actionId = DAO.getActionID(conn);
      result.result = result.actionId;
      try
      {
        CapitalValue cVal = new CapitalValue();
        cVal.trader = "";
        cVal.actionID = result.actionId;
        cVal.firmID = imm.firmID;
        cVal.contact = imm.contact;
        cVal.bankID = imm.bankID;
        cVal.type = 0;
        cVal.launcher = 0;
        cVal.money = imm.money;
        
        cVal.status = 7;
        cVal.note = "市场端入金";
        log(cVal.toString());
        capitalID = DAO.addCapitalInfo(cVal, conn);
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        result.result = -10026L;
        result.remark = "添加流水信息时数据库异常";
        log("市场端发起入金写资金流水SQLException,数据回滚:" + e);
        localReturnValue1 = result;
        



















































































        DAO.closeStatement(null, null, conn);return localReturnValue1;
      }
      if (result.result <= 0L)
      {
        localReturnValue1 = result;return localReturnValue1;
      }
      TransferInfoValue payInfo = getPayInfo(imm.bankID, imm.firmID, 0, conn);
      TransferInfoValue receiveInfo = getReceiveInfo(imm.bankID, imm.firmID, 0, conn);
      InMoneyVO inMoneyInfo = new InMoneyVO(imm.bankID, null, imm.money, imm.contact, payInfo, imm.accountPwd, receiveInfo, imm.remark, result.actionId);
      inMoneyInfo.setBankFlag(imm.bankFlag);
      inMoneyInfo.setInOutStart(imm.inOutStart);
      inMoneyInfo.setPersonName(imm.personName);
      inMoneyInfo.setAmoutDate(imm.amoutDate);
      inMoneyInfo.setOutAccount(imm.outAccount);
      inMoneyInfo.setOutBankName(imm.bankName);
      BankAdapterRMI bankadapter = getAdapter(imm.bankID);
      if (bankadapter == null)
      {
        result.result = -920000L;
        result.remark = "网络异常，处理器无法连接适配器";
        log(result.remark);
      }
      else
      {
        result = bankadapter.inMoneyQueryBank(inMoneyInfo);
      }
      log("市场端调用适配器入金，市场返回信息：" + result.toString());
      































































      return result;
    }
    catch (SQLException e)
    {
      result.result = -10014L;
      result.remark = "数据库连接异常";
      log("市场端发起入金SQLException:" + result + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      result.result = -10015L;
      result.remark = "系统异常";
      log("市场端发起入金Exception:" + result + Tool.getExceptionTrace(e));
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
  }
}
