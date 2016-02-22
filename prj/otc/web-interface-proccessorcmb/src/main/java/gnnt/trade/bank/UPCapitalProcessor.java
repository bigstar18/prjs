package gnnt.trade.bank;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.up.UPBankDAO;
import gnnt.trade.bank.util.Arith;
import gnnt.trade.bank.util.ErrorCode;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.OutMoneyMarket;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfoValue;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class UPCapitalProcessor
  extends CapitalProcessor
{
  private static UPBankDAO DAO;
  
  public UPCapitalProcessor()
  {
    try
    {
      DAO = BankDAOFactory.getUPDAO();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
    }
    catch (InstantiationException e)
    {
      e.printStackTrace();
    }
  }
  
  public long rgstAccount(CorrespondValue correspondValue)
  {
    log("银行账号注册 rgstAccount correspondValue:" + (correspondValue == null ? "为 null" : new StringBuilder("\n").append(correspondValue.toString()).append("\n").toString()));
    long result = ifbankTrade(correspondValue.bankID, correspondValue.firmID, correspondValue.contact, 3, 0).result;
    if (result < 0L) {
      return result;
    }
    String defaultAccount = Tool.getConfig("DefaultAccount");
    Connection conn = null;
    if ((correspondValue.bankID == null) || (correspondValue.bankID.length() == 0) || 
      (correspondValue.firmID == null) || (correspondValue.firmID.length() == 0) || 
      (correspondValue.account == null) || (correspondValue.account.length() == 0)) {
      return -40001L;
    }
    try
    {
      conn = DAO.getConnection();
      if (DAO.getFirm(correspondValue.firmID, conn) == null)
      {
        result = -40002L;
        log("银行账号注册，交易账号不存在，错误码=" + result);
      }
      else if (DAO.getBank(correspondValue.bankID, conn) == null)
      {
        result = -40003L;
        log("银行账号注册，银行不存在，错误码=" + result);
      }
      else if (DAO.getCorrespondList(" and bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID + "' and isopen=1", conn).size() > 0)
      {
        result = -40004L;
        log("银行账号注册，账号已注册，错误码=" + result);
      }
      List<CorrespondValue> cvresult = DAO.getCorrespondList(" and bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID + "'");
      if ((cvresult == null) || (cvresult.size() <= 0))
      {
        cvresult = DAO.getCorrespondList(" and trim(firmID)='" + correspondValue.firmID.trim() + "' and trim(bankID) is null ");
        if ((cvresult != null) && (cvresult.size() > 0))
        {
          correspondValue.isOpen = 1;
          try
          {
            conn.setAutoCommit(false);
            dataProcess.changeFirmIsOpen(correspondValue.firmID, 1, correspondValue.bankID, conn);
            DAO.openCorrespond(correspondValue, conn);
            conn.commit();
          }
          catch (SQLException e)
          {
            conn.rollback();
            throw e;
          }
          finally
          {
            conn.setAutoCommit(true);
          }
          log("满足注册条件，修改为签约状态" + result);
        }
        else
        {
          Vector<FirmValue> vfv = DAO.getFirmList(" and firmID='" + correspondValue.firmID + "'");
          if ((vfv == null) || (vfv.size() != 1))
          {
            result = -40002L;
            
            long l1 = result;return l1;
          }
          correspondValue.card = ((FirmValue)vfv.get(0)).card;
          correspondValue.cardType = ((FirmValue)vfv.get(0)).cardType;
          conn = DAO.getConnection();
          try
          {
            conn.setAutoCommit(false);
            dataProcess.changeFirmIsOpen(correspondValue.firmID, 1, correspondValue.bankID, conn);
            correspondValue.isOpen = 1;
            DAO.addCorrespond(correspondValue, conn);
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
      }
      else
      {
        CorrespondValue cv2 = (CorrespondValue)cvresult.get(0);
        

        conn = DAO.getConnection();
        try
        {
          conn.setAutoCommit(false);
          dataProcess.changeFirmIsOpen(cv2.firmID, 1, cv2.bankID, conn);
          correspondValue.isOpen = 1;
          DAO.openCorrespond(correspondValue, conn);
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
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      result = -40006L;
      log("银行账号注册SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result = -40007L;
      log("银行账号注册Exception，错误码=" + result + "  " + Tool.getExceptionTrace(e));
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
  
  public ReturnValue inMoneyMarket(InMoneyMarket imm, String account, String bankCode, String bankName, String bankProvince, String bankCity, String accountType)
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
      CorrespondValue cv = getCorrespond(imm.bankID, imm.firmID, imm.contact, null);
      if (cv == null)
      {
        result.result = -920013L;
        result.remark = "未查询到交易账号信息";
        localReturnValue1 = result;return localReturnValue1;
      }
      imm.contact = cv.contact;
      
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
      payInfo.account = account;
      payInfo.accountType = accountType;
      payInfo.bankName = bankName;
      payInfo.OpenBankCode = bankCode;
      payInfo.province = bankProvince;
      payInfo.city = bankCity;
      TransferInfoValue receiveInfo = getReceiveInfo(imm.bankID, imm.firmID, 0, conn);
      

      InMoneyVO inMoneyInfo = new InMoneyVO(imm.amoutDate, imm.bankName, imm.outAccount, imm.personName, imm.inOutStart, imm.bankID, null, imm.money, imm.contact, payInfo, imm.accountPwd, receiveInfo, imm.remark, result.actionId);
      inMoneyInfo.setBankFlag(imm.bankFlag);
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
  
  public ReturnValue outMoneyMarket(OutMoneyMarket omm, String account, String bankCode, String bankName, String bankProvince, String bankCity, String accountType)
  {
    log("出金: outMoney omm:" + (omm == null ? "为空" : new StringBuilder("\n").append(omm.toString()).append("\n").toString()));
    ReturnValue result = new ReturnValue();
    Connection conn = null;
    
    Timestamp curTime = new Timestamp(System.currentTimeMillis());
    try
    {
      CorrespondValue cv = getCorrespond(omm.bankID, omm.firmID, omm.contact, null);
      ReturnValue localReturnValue1;
      if (cv == null)
      {
        result.result = -920013L;
        result.remark = "出金，未查询到交易账号绑定信息";
        log(result.remark);
        localReturnValue1 = result;return localReturnValue1;
      }
      if (cv.isOpen != 1)
      {
        result.result = -20008L;
        result.remark = "交易账号未签约。";
        log(result.remark);
        localReturnValue1 = result;return localReturnValue1;
      }
      if (cv.status != 0)
      {
        result.result = -20009L;
        result.remark = "交易账号不可用";
        log(result.remark);
        localReturnValue1 = result;return localReturnValue1;
      }
      omm.firmID = cv.firmID;
      omm.contact = cv.contact;
      omm.account = cv.account;
      result = ifbankTrade(omm.bankID, omm.firmID, omm.contact, 1, 0);
      if (result.result < 0L)
      {
        localReturnValue1 = result;return localReturnValue1;
      }
      conn = DAO.getConnection();
      result = checkArgs(conn, omm.money, omm.firmID, omm.bankID);
      if (result.result != 0L)
      {
        localReturnValue1 = result;return localReturnValue1;
      }
      long actionId = handleOUtMoney(conn, omm.bankID, "", omm.money, omm.firmID, omm.contact, omm.account, null, omm.type);
      result.result = actionId;
      if (result.result <= 0L) {
        result.remark = ((String)ErrorCode.error.get(Long.valueOf(result.result)));
      }
      result = outMoneyAutoAudit(result.result, account, bankCode, 
        bankName, bankProvince, bankCity, accountType);
    }
    catch (SQLException e)
    {
      result.result = -20004L;
      result.remark = "数据库发生异常，请联系交易所";
      log("市场发起出金SQLException，错误码=" + result.result + "  " + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      result.result = -20005L;
      result.remark = "转账系统异常";
      log("市场发起出金Exception，错误码=" + result.result + "  " + Tool.getExceptionTrace(e));
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
  
  public ReturnValue outMoneyAutoAudit(long actionID, String account, String bankCode, String bankName, String bankProvince, String bankCity, String accountType)
  {
    log("出金自动审核 outMoneyAutoAudit actionID[" + actionID + "]");
    ReturnValue result = new ReturnValue();
    Connection conn = null;
    Timestamp curTime = new Timestamp(System.currentTimeMillis());
    CapitalValue capital = null;
    try
    {
      conn = DAO.getConnection();
      Vector<CapitalValue> list = DAO.getCapitalInfoList(" and actionid=" + actionID, conn);
      for (int i = 0; i < list.size(); i++)
      {
        val = (CapitalValue)list.get(i);
        if (val.type == 1) {
          capital = val;
        }
      }
      ReturnValue localReturnValue2;
      if (capital == null)
      {
        result.result = -20011L;
        result.remark = ("没有发现业务流水号为：" + actionID + "的流水");
        log(result.remark);
        localReturnValue2 = result;return localReturnValue2;
      }
      try
      {
        conn.setAutoCommit(false);
        list = DAO.getCapitalInfoList(" and actionid=" + actionID + " and STATUS=" + 7 + " for update", conn);
        if ((list == null) || (list.size() <= 0))
        {
          result.result = -20011L;
          result.remark = ("没有发现业务流水号为：" + actionID + "状态为处理中的流水");
          log(result.remark);
          ReturnValue localReturnValue1 = result;
          








          conn.setAutoCommit(true);
          






















































































          DAO.closeStatement(null, null, conn);return localReturnValue1;
        }
        DAO.modCapitalInfoStatus(capital.iD, result.funID, 2, curTime, conn);
        DAO.modCapitalInfoNote(capital.iD, "处理中", conn);
        DAO.modCapitalInfoTrader(capital.iD, "manual", conn);
        conn.commit();
      }
      catch (SQLException e)
      {
        conn.rollback();
        throw e;
      }
      finally
      {
        conn.setAutoCommit(true);
      }
      conn.setAutoCommit(true);
      if (result.result < 0L)
      {
        localReturnValue2 = result;return localReturnValue2;
      }
      try
      {
        if (capital.launcher == 0)
        {
          BankAdapterRMI bankadapter = getAdapter(capital.bankID);
          if (bankadapter == null)
          {
            result.result = -920000L;
            result.remark = ("网络异常，处理器无法连接适配器[" + capital.bankID + "]");
            log(result.remark);
          }
          else
          {
            Vector<CorrespondValue> csv = DAO.getCorrespondList(" and bankid='" + capital.bankID + "' and firmid='" + capital.firmID + "'", conn);
            TransferInfoValue payInfo = getPayInfo(capital.bankID, capital.firmID, 1, conn);
            TransferInfoValue receiveInfo = getReceiveInfo(capital.bankID, capital.firmID, 1, conn);
            receiveInfo.account = account;
            receiveInfo.accountType = accountType;
            receiveInfo.bankName = bankName;
            receiveInfo.OpenBankCode = bankCode;
            receiveInfo.province = bankProvince;
            receiveInfo.city = bankCity;
            
            OutMoneyVO outMoneyInfo = new OutMoneyVO(capital.bankID, "", capital.money, capital.contact, payInfo, receiveInfo, actionID, capital.funID);
            outMoneyInfo.isCrossline = String.valueOf(((CorrespondValue)csv.get(0)).isCrossline);
            result = bankadapter.outMoneyMarketDone(outMoneyInfo);
          }
        }
        else
        {
          result.actionId = capital.actionID;
          result.funID = capital.funID;
        }
        conn.setAutoCommit(false);
        if (result.result == 0L)
        {
          dataProcess.updateFundsFull(capital.bankID, capital.firmID, actionID, capital.money, 1, conn);
          dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);
          bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
          DAO.modCapitalInfoStatus(capital.iD, result.funID, 0, curTime, conn);
          DAO.modCapitalInfoNote(capital.iD, "出金成功", conn);
          log(actionID + "出金成功,银行流水号=" + result.funID);
          result.type = 0;
          result.remark = "出金成功";
        }
        else if (result.result == 5L)
        {
          DAO.modCapitalInfoStatus(capital.iD, result.funID, 2, curTime, conn);
          DAO.modCapitalInfoNote(capital.iD, result.remark, conn);
          log(actionID + "银行处理处理中");
          result.type = 2;
        }
        else if (result.result == -50010L)
        {
          DAO.modCapitalInfoStatus(capital.iD, result.funID, 5, curTime, conn);
          DAO.modCapitalInfoNote(capital.iD, result.remark, conn);
          log(actionID + "银行无返回");
          result.type = 5;
        }
        else if (result.result == -920008L)
        {
          DAO.modCapitalInfoStatus(capital.iD, result.funID, 6, curTime, conn);
          DAO.modCapitalInfoNote(capital.iD, result.remark, conn);
          log(actionID + "银行返回信息异常");
          result.type = 6;
        }
        else
        {
          dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);
          bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
          DAO.modCapitalInfoStatus(capital.iD, result.funID, 1, curTime, conn);
          DAO.modCapitalInfoNote(capital.iD, result.remark, conn);
          log(actionID + "银行处理失败,退还全部出金和手续费，错误码=" + result);
          result.type = 1;
        }
        conn.commit();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        conn.rollback();
        result.result = -20013L;
        result.remark = ("处理出金流水[" + actionID + "]数据库异常");
        log(actionID + result.remark + Tool.getExceptionTrace(e));
      }
      finally
      {
        conn.setAutoCommit(true);
      }
      if (result.result == -20013L)
      {
        DAO.modCapitalInfoStatus(capital.iD, capital.funID, 8, curTime, conn);
        DAO.modCapitalInfoNote(capital.iD, "数据库异常", conn);
        result.type = 8;
      }
      result.actionId = capital.actionID;
      result.funID = capital.funID;
    }
    catch (SQLException e)
    {
      result.result = -30004L;
      log(actionID + "审核出金SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      result.result = -30005L;
      log(actionID + "审核出金Exception，错误码=" + result + "  " + Tool.getExceptionTrace(e));
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
  
  public ReturnValue outMoneyAudit(long actionID, boolean funFlag)
  {
    log("{{{{{{{{{{{{{{{{{{{{{{出金审核{{{{{{{{{{{{{{{{{{{{{{{{");
    log("市场流水号：" + actionID + " 审核结果：" + (funFlag ? "通过" : "驳回"));
    log("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
    String auditInfo = "审核业务流水号：" + actionID + ";";
    
    Connection conn = null;
    

    Timestamp curTime = new Timestamp(System.currentTimeMillis());
    

    CapitalValue capital = null;
    

    ReturnValue rVal = null;
    

    ReturnValue result = new ReturnValue();
    result.result = 0L;
    if ((Tool.getConfig("AuditBeginTime") != null) && (Tool.getConfig("AuditBeginTime").length() > 0))
    {
      Time AuditBeginTime = Tool.convertTime(Tool.getConfig("AuditBeginTime"));
      Time curSqlTime = Tool.convertTime(Tool.fmtOnlyTime(curTime));
      if ((AuditBeginTime != null) && (AuditBeginTime.after(curSqlTime)))
      {
        result.result = -20018L;
        result.remark = "出金审核时间未到";
        log("审核时间未到！");
        return result;
      }
    }
    if ((Tool.getConfig("AuditEndTime") != null) && (Tool.getConfig("AuditEndTime").length() > 0))
    {
      Time AuditEndTime = Tool.convertTime(Tool.getConfig("AuditEndTime"));
      Time curSqlTime = Tool.convertTime(Tool.fmtOnlyTime(curTime));
      if ((AuditEndTime != null) && (curSqlTime.after(AuditEndTime)))
      {
        result.result = -20017L;
        result.remark = "出金审核时间已过";
        log("审核时间已过！");
        return result;
      }
    }
    try
    {
      conn = DAO.getConnection();
      
      Vector<CapitalValue> list = DAO.getCapitalInfoList("and actionID='" + actionID + "'", conn);
      for (int i = 0; i < list.size(); i++)
      {
        CapitalValue val = (CapitalValue)list.get(i);
        if (val.type == 1) {
          capital = val;
        }
      }
      if (capital != null)
      {
        TransferInfoValue payInfo = getPayInfo(capital.bankID, capital.firmID, 1, conn);
        
        TransferInfoValue receiveInfo = getReceiveInfo(capital.bankID, capital.firmID, 1, conn);
        BankValue bv = DAO.getBank(capital.bankID);
        OutMoneyVO outMoneyInfo = new OutMoneyVO(capital.bankID, bv.bankName, capital.money, capital.contact, payInfo, receiveInfo, actionID, capital.funID);
        if (funFlag)
        {
          long number = tradeDate(capital.bankID);
          if (number != 0L)
          {
            result.result = number;
            result.remark = ((String)ErrorCode.error.get(Long.valueOf(number)));
            ReturnValue localReturnValue2 = result;return localReturnValue2;
          }
          if (result.result == 0L) {
            try
            {
              try
              {
                conn.setAutoCommit(false);
                Vector<CapitalValue> ll = DAO.getCapitalInfoList("and actionID='" + actionID + "' and status=" + 3 + " for update", conn);
                if ((ll == null) || (ll.size() <= 0))
                {
                  result.result = -20042L;
                  result.remark = "信息已经在处理中";
                  log("信息已在处理中");
                  localReturnValue1 = result;
                  










                  conn.setAutoCommit(true);
                  

                  conn.setAutoCommit(false);
                  

                  dataProcess.updateFundsFull(capital.bankID, capital.firmID, actionID, capital.money, 1, conn);
                  dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);
                  bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
                  DAO.modCapitalInfoStatus(capital.iD, "", 0, curTime, conn);
                  DAO.modCapitalInfoNote(capital.iD, "出金成功", conn);
                  

                  log(auditInfo + "审核通过，银行处理成功,出金成功,扣除手续费成功");
                  














                  conn.setAutoCommit(true);
                  
































































































                  DAO.closeStatement(null, null, conn);return localReturnValue1;
                }
                DAO.modCapitalInfoStatus(capital.iD, capital.funID, 2, curTime, conn);
                conn.commit();
              }
              catch (SQLException e)
              {
                conn.rollback();
                e.printStackTrace();
                result.result = -20015L;
                result.remark = "出金审核SQL异常";
                log("修改记录为处理中失败，SQL异常");
                ReturnValue localReturnValue1 = result;
                
                conn.setAutoCommit(true);
                

                conn.setAutoCommit(false);
                

                dataProcess.updateFundsFull(capital.bankID, capital.firmID, actionID, capital.money, 1, conn);
                dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);
                bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
                DAO.modCapitalInfoStatus(capital.iD, "", 0, curTime, conn);
                DAO.modCapitalInfoNote(capital.iD, "出金成功", conn);
                

                log(auditInfo + "审核通过，银行处理成功,出金成功,扣除手续费成功");
                














                conn.setAutoCommit(true);
                
































































































                DAO.closeStatement(null, null, conn);return localReturnValue1;
              }
              finally
              {
                conn.setAutoCommit(true);
                

                conn.setAutoCommit(false);
                

                dataProcess.updateFundsFull(capital.bankID, capital.firmID, actionID, capital.money, 1, conn);
                dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);
                bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
                DAO.modCapitalInfoStatus(capital.iD, "", 0, curTime, conn);
                DAO.modCapitalInfoNote(capital.iD, "出金成功", conn);
                

                log(auditInfo + "审核通过，银行处理成功,出金成功,扣除手续费成功");
              }
              conn.setAutoCommit(true);
              

              conn.setAutoCommit(false);
              

              dataProcess.updateFundsFull(capital.bankID, capital.firmID, actionID, capital.money, 1, conn);
              dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);
              bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
              DAO.modCapitalInfoStatus(capital.iD, "", 0, curTime, conn);
              DAO.modCapitalInfoNote(capital.iD, "出金成功", conn);
              

              log(auditInfo + "审核通过，银行处理成功,出金成功,扣除手续费成功");
              


              conn.commit();
            }
            catch (SQLException e)
            {
              e.printStackTrace();
              conn.rollback();
              result.result = -20013L;
              result.remark = "审核出金SQLException，数据回滚，需要手工处理出金和手续费";
              log(auditInfo + "审核出金SQLException，数据回滚，需要手工处理出金和手续费，错误码=" + result + "  " + e);
            }
            finally
            {
              conn.setAutoCommit(true);
            }
          }
          if (result.result == -20013L)
          {
            DAO.modCapitalInfoStatus(capital.iD, capital.funID, 1, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核通过，系统异常，需手工处理出金", conn);
          }
        }
        else
        {
          try
          {
            try
            {
              conn.setAutoCommit(false);
              Vector<CapitalValue> ll = DAO.getCapitalInfoList("and actionID='" + actionID + "' and status=" + 3 + " for update", conn);
              if ((ll == null) || (ll.size() <= 0))
              {
                result.result = -20042L;
                result.remark = "信息已经在处理中";
                log("信息已在处理中");
                e = result;
                










                conn.setAutoCommit(true);
                



























                conn.setAutoCommit(true);
                
































                DAO.closeStatement(null, null, conn);return e;
              }
              DAO.modCapitalInfoStatus(capital.iD, capital.funID, 2, curTime, conn);
              conn.commit();
            }
            catch (SQLException e)
            {
              conn.rollback();
              e.printStackTrace();
              result.result = -20015L;
              result.remark = "修改流水状态为处理中失败，SQL异常";
              log("修改记录为处理中失败，SQL异常");
              e = result;
              
              conn.setAutoCommit(true);
              



























              conn.setAutoCommit(true);
              
































              DAO.closeStatement(null, null, conn);return e;
            }
            finally
            {
              conn.setAutoCommit(true);
            }
            conn.setAutoCommit(true);
            
            rVal = new ReturnValue();
            rVal.result = 0L;
            

            conn.setAutoCommit(false);
            if (rVal.result == 0L)
            {
              dataProcess.updateFrozenFunds(capital.firmID, -1.0D * Arith.add(capital.money, 0.0D), conn);
              
              bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * Arith.add(capital.money, 0.0D), conn);
              DAO.modCapitalInfoStatus(capital.iD, rVal == null ? "" : rVal.funID, 1, curTime, conn);
              
              DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核拒绝成功", conn);
            }
            log(auditInfo + "审核拒绝成功");
            conn.commit();
          }
          catch (SQLException e)
          {
            e.printStackTrace();
            conn.rollback();
            result.result = -20014L;
            result.remark = "出金审核拒绝SQL异常，数据回滚";
            log(auditInfo + "审核拒绝出金SQLException，数据回滚，需要手工处理出金和手续费，错误码=" + result + "  " + e);
          }
          finally
          {
            conn.setAutoCommit(true);
          }
          if (result.result == -20014L)
          {
            DAO.modCapitalInfoStatus(capital.iD, rVal.funID, 1, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "[" + capital.note + "]审核拒绝出金 资金解冻出错，需要手工处理出金", conn);
            log(auditInfo + "审核拒绝出金 资金解冻出错，需要手工处理出金");
          }
        }
      }
      else
      {
        result.result = -20011L;
        result.remark = ("没有发现流水号为：" + actionID + "的记录");
        log(auditInfo + "没有发现业务流水号为：" + actionID + "的资金流水");
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      result.result = -30004L;
      result.remark = "出金审核SQL异常";
      log(auditInfo + "审核出金SQLException，错误码=" + result + "  " + e);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.result = -30005L;
      result.remark = "审核出金异常";
      log(auditInfo + "审核出金Exception，错误码=" + result + "  " + e);
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
}
