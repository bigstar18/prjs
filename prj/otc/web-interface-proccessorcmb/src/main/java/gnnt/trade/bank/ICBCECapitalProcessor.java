package gnnt.trade.bank;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.icbce.ICBCEBankDAO;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfoValue;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

public class ICBCECapitalProcessor
  extends CapitalProcessor
{
  private static ICBCEBankDAO DAO;
  
  public ICBCECapitalProcessor()
  {
    try
    {
      DAO = BankDAOFactory.getICBCEDAO();
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
  
  public ReturnValue inMoneyMarket(InMoneyMarket imm)
  {
    log("市场入金 inMoneyMarket imm:" + (imm == null ? "为 null" : new StringBuilder("\n").append(imm.toString()).append("\n").toString()));
    ReturnValue result = new ReturnValue();
    
    Connection conn = null;
    long actionID = getMktActionID();
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
      result.actionId = actionID;
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
        result.result = DAO.addCapitalInfo(cVal, conn);
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
      if ((result.result <= 0L) || (result.actionId <= 0L))
      {
        result.result = -1L;
        result.remark = "获取市场流水号失败";
        localReturnValue1 = result;return localReturnValue1;
      }
      TransferInfoValue payInfo = getPayInfo(imm.bankID, imm.firmID, 0, conn);
      TransferInfoValue receiveInfo = getReceiveInfo(imm.bankID, imm.firmID, 0, conn);
      InMoneyVO inMoneyInfo = new InMoneyVO(imm.bankID, null, imm.money, imm.contact, payInfo, imm.accountPwd, receiveInfo, imm.remark, result.actionId);
      inMoneyInfo.setBankFlag(imm.bankFlag);
      inMoneyInfo.setActionID(actionID);
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
      if (result.result >= 0L)
      {
        log("入金订单组建适配器处理成功");
        log("报文：" + result.remark);
      }
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
    return result;
  }
  
  public ReturnValue modCaptailInfoInOutMoney(long actionID, String funID, boolean funFlag, String note)
  {
    log("回调处理银行审核出入金结果的信息 modCaptailInfoOutMoney actionID[" + actionID + "]funID[" + funID + "]funFlag[" + funFlag + "]note[" + note + "]");
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
            dataProcess.updateFundsFull(capital.bankID, capital.firmID, actionID, capital.money, 1, conn);
            dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);
            
            bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
            DAO.modCapitalInfoStatus(capital.iD, funID, 0, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "银行审核通过", conn);
          }
          else if (capital.type == 0)
          {
            dataProcess.updateFundsFull(capital.bankID, capital.firmID, actionID, capital.money, 0, conn);
            DAO.modCapitalInfoStatus(capital.iD, funID, 0, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "银行审核通过", conn);
          }
          DAO.modCapitalInfoTrader(capital.iD, "manual", conn);
          log(actionID + "银行审核通过");
          conn.commit();
        }
        catch (SQLException e)
        {
          conn.rollback();
          log(actionID + "处理银行审核出入金结果SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
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
            DAO.modCapitalInfoNote(capital.iD, "银行审核拒绝：" + note, conn);
          }
          else if (capital.type == 0)
          {
            DAO.modCapitalInfoStatus(capital.iD, result.funID, 1, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "银行审核拒绝：" + note, conn);
          }
          DAO.modCapitalInfoTrader(capital.iD, "manual", conn);
          log(actionID + "银行审核拒绝");
          conn.commit();
        }
        catch (SQLException e)
        {
          conn.rollback();
          log(actionID + "处理银行审核出入金结果SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
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
      result.remark = ("处理银行审核出入金结果[" + actionID + "]数据库异常");
      log(actionID + "处理银行审核出入金结果SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      result.result = -30005L;
      result.remark = ("处理银行审核出入金结果[" + actionID + "]系统异常");
      log(Tool.getExceptionTrace(e));
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
}
