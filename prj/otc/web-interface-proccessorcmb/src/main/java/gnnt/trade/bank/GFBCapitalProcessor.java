package gnnt.trade.bank;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.GFBBankDAO;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.ErrorCode;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfoValue;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

public class GFBCapitalProcessor
  extends CapitalProcessor
{
  private static GFBBankDAO DAO;
  
  public GFBCapitalProcessor()
  {
    try
    {
      DAO = BankDAOFactory.getGFBDAO();
    }
    catch (ClassNotFoundException e)
    {
      log("初始化国付宝DAO对象失败" + Common.getExceptionTrace(e));
    }
    catch (IllegalAccessException e)
    {
      log("初始化国付宝DAO对象失败" + Common.getExceptionTrace(e));
    }
    catch (InstantiationException e)
    {
      log("初始化国付宝DAO对象失败" + Common.getExceptionTrace(e));
    }
  }
  
  public ReturnValue inMoneyMarket(InMoneyMarket imm)
  {
    log("国付宝市场入金 inMoneyMarket imm:" + (imm == null ? "为 null" : new StringBuilder("\n").append(imm.toString()).append("\n").toString()));
    ReturnValue result = new ReturnValue();
    
    Connection conn = null;
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
      result.actionId = DAO.getActionID(conn);
      result.result = result.actionId;
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
  
  public ReturnValue openAccountMarket(CorrespondValue cv)
  {
    log("国付宝市场开户方法 openAccountMarket cv:" + (cv == null ? "为 null" : new StringBuilder("\n").append(cv.toString()).append("\n").toString()));
    ReturnValue rv = new ReturnValue();
    rv.result = -1L;
    label298:
    try
    {
      rv = ifbankTrade(cv.bankID, cv.firmID, cv.contact, 3, 0);
      if (rv.result < 0L)
      {
        ReturnValue localReturnValue1 = rv;return localReturnValue1;
      }
      rv.result = chenckCorrespondValue(cv);
      if (rv.result != 0L)
      {
        rv.remark = ((String)ErrorCode.error.get(Long.valueOf(rv.result)));
        if ((rv.remark == null) || ("".equals(rv.remark)))
        {
          rv.remark = "交易商代码与银行、帐号对应有误!";
          break label298;
        }
      }
      else
      {
        rv.actionId = getMktActionID();
        rv.remark = "开户校验成功";
      }
    }
    catch (Exception e)
    {
      rv.result = -920016L;
      rv.remark = "系统异常";
      log("国付宝市场开户方法异常：" + Common.getExceptionTrace(e));
    }
    finally
    {
      log("国付宝市场开户方法返回：" + rv.toString());
    }
    return rv;
  }
  
  public ReturnValue delAccountMaket(CorrespondValue correspondValue)
  {
    log("国付宝银行账号注销 delAccountMaket correspondValue:" + (correspondValue == null ? "为 null" : new StringBuilder("\n").append(correspondValue.toString()).append("\n").toString()));
    ReturnValue result = ifbankTrade(correspondValue.bankID, correspondValue.firmID, correspondValue.contact, 4, 0);
    if (result.result < 0L) {
      return result;
    }
    CorrespondValue cv = null;
    try
    {
      cv = getCorrespond(correspondValue.bankID, correspondValue.firmID, correspondValue.contact, correspondValue.account);
    }
    catch (SQLException e)
    {
      result.result = -920015L;
      result.remark = "查询账号绑定关系时数据库异常";
      log(Tool.getExceptionTrace(e));
      return result;
    }
    catch (Exception e)
    {
      result.result = -920016L;
      result.remark = "查询账号绑定关系时系统异常";
      log(Tool.getExceptionTrace(e));
      return result;
    }
    if (cv == null)
    {
      result.result = -40014L;
      result.remark = "未查询到绑定信息";
      return result;
    }
    if (cv.frozenFuns > 0.0D)
    {
      result.result = -920002L;
      result.remark = "在途资金不为 0 不能解约";
      return result;
    }
    Connection conn = null;
    try
    {
      conn = DAO.getConnection();
      Vector<CapitalValue> capitals = DAO.getCapitalInfoList(" and bankID='" + cv.bankID.trim() + "' and contact='" + cv.contact.trim() + "' and createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1 ", conn);
      if ((capitals != null) && (capitals.size() > 0))
      {
        result.result = -941002L;
        result.remark = "当日有转账，不能解约";
        ReturnValue localReturnValue2 = result;return localReturnValue2;
      }
      try
      {
        conn.setAutoCommit(false);
        result.result = dataProcess.ifFirmDelAccount(cv.firmID, cv.bankID, conn);
        ReturnValue localReturnValue1;
        if (result.result == -1L)
        {
          conn.rollback();
          result.remark = "账户余额不为0，不满足交易系统解约条件";
          localReturnValue1 = result;
          


















          conn.setAutoCommit(true);
          











          DAO.closeStatement(null, null, conn);
          log("国付宝银行账号注销" + result.toString());return localReturnValue1;
        }
        if (result.result == -3L)
        {
          conn.rollback();
          result.remark = "次账户与其他签约银行，不满足交易系统解约条件";
          localReturnValue1 = result;
          














          conn.setAutoCommit(true);
          











          DAO.closeStatement(null, null, conn);
          log("国付宝银行账号注销" + result.toString());return localReturnValue1;
        }
        String status = DAO.getFirmStatus(cv.firmID, conn);
        if (!"D".equals(status))
        {
          result.result = -1L;
          result.remark = "会员未终止";
          localReturnValue1 = result;
          








          conn.setAutoCommit(true);
          











          DAO.closeStatement(null, null, conn);
          log("国付宝银行账号注销" + result.toString());return localReturnValue1;
        }
        conn.commit();
      }
      catch (SQLException er)
      {
        conn.rollback();
        throw er;
      }
      catch (Exception er)
      {
        conn.rollback();
        throw er;
      }
      finally
      {
        conn.setAutoCommit(true);
      }
      conn.setAutoCommit(true);
      
      result.result = 0L;
      result.remark = "校验解约账户信息成功";
    }
    catch (SQLException e)
    {
      result.result = -40016L;
      result.remark = "解约账号信息时数据库异常";
      log("银行账号注销SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      result.result = -40017L;
      result.remark = "解约账户信息时系统异常";
      log("银行账号注销Exception，错误码=" + result + "  " + Tool.getExceptionTrace(e));
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
      log("国付宝银行账号注销" + result.toString());
    }
    return result;
  }
  
  public ReturnValue outMoneyGS(String bankID, double money)
  {
    System.out.println("{{{{{{{{{{{{{{{{{{{{{{{国付宝市场收益划拨{{{{{{{{{{{{{{{{{{{{{{{");
    System.out.println("bankID[" + bankID + "]money[" + money + "]");
    System.out.println("}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}}");
    
    ReturnValue rv = new ReturnValue();
    
    long actionID = 0L;
    long id = 0L;
    Timestamp curTime = new Timestamp(System.currentTimeMillis());
    
    String auditInfo = "市场收益划拨，银行号:" + bankID;
    Connection conn = null;
    try
    {
      conn = DAO.getConnection();
      
      actionID = getMktActionID();
      CapitalValue cVal = new CapitalValue();
      cVal.trader = "";
      cVal.funID = "";
      cVal.actionID = actionID;
      cVal.firmID = "MarketIncomeOut";
      cVal.contact = "MarketIncomeOut";
      cVal.account = "MarketIncomeOut";
      cVal.bankID = bankID;
      cVal.type = 1;
      cVal.money = money;
      cVal.status = 7;
      cVal.launcher = 0;
      cVal.note = "市场收益划拨";
      
      rv.result = DAO.addCapitalInfo(cVal, conn);
      id = rv.result;
      if (rv.result >= 0L)
      {
        TransferInfoValue payInfo = new TransferInfoValue();
        
        TransferInfoValue receiveInfo = new TransferInfoValue();
        receiveInfo.account = "MarketIncomeOut";
        receiveInfo.accountName = "MarketIncomeOut";
        OutMoneyVO outMoneyInfo = new OutMoneyVO(bankID, "国付宝", money, "MarketIncomeOut", payInfo, receiveInfo, actionID, "");
        
        BankAdapterRMI bankadapter = getAdapter(bankID);
        if (bankadapter == null)
        {
          rv.result = -920000L;
          log("处理器连接适配器[" + bankID + "]失败");
          ReturnValue localReturnValue2 = rv;return localReturnValue2;
        }
        try
        {
          conn.setAutoCommit(false);
          String filter = " and actionid=" + actionID + " and STATUS=" + 7 + " for update";
          Vector<CapitalValue> ll = DAO.getCapitalInfoList(filter, conn);
          if ((ll == null) || (ll.size() <= 0))
          {
            rv.result = -20042L;
            rv.remark = ("没有发现业务流水号为：" + actionID + "状态为处理中的流水");
            log("信息已在处理中");
            localReturnValue1 = rv;
            








            conn.setAutoCommit(true);
            
































            DAO.closeStatement(null, null, conn);return localReturnValue1;
          }
          conn.commit();
        }
        catch (SQLException e)
        {
          conn.rollback();
          e.printStackTrace();
          rv.result = -20015L;
          log("修改记录为处理中失败，SQL异常");
          ReturnValue localReturnValue1 = rv;
          
          conn.setAutoCommit(true);
          
































          DAO.closeStatement(null, null, conn);return localReturnValue1;
        }
        finally
        {
          conn.setAutoCommit(true);
        }
        conn.setAutoCommit(true);
        

        rv = bankadapter.outMoneyMarketDone(outMoneyInfo);
        if (rv.result == 0L)
        {
          DAO.modCapitalInfoStatus(id, rv.funID, 0, curTime, conn);
          log(auditInfo + "银行处理成功,出金成功,银行流水号=" + rv.funID);
        }
        else if (rv.result == 5L)
        {
          DAO.modCapitalInfoStatus(id, rv.funID, 2, curTime, conn);
          log(auditInfo + "银行处理中");
        }
        else if (rv.result == -50010L)
        {
          DAO.modCapitalInfoStatus(id, rv.funID, 5, curTime, conn);
          log(auditInfo + "银行无返回");
        }
        else if (rv.result == -920008L)
        {
          DAO.modCapitalInfoStatus(id, rv.funID, 6, curTime, conn);
          log(auditInfo + "银行返回的市场流水号和市场本身流水不一致");
        }
        else
        {
          DAO.modCapitalInfoStatus(id, rv.funID, 1, curTime, conn);
          log(auditInfo + "银行处理失败，错误码=" + rv.result);
        }
        conn.commit();
      }
    }
    catch (SQLException e1)
    {
      e1.printStackTrace();
    }
    catch (ClassNotFoundException e1)
    {
      e1.printStackTrace();
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return rv;
  }
  
  public FirmBalanceValue getFirmBalance(String bankid, String firmid, String pwd)
  {
    FirmBalanceValue fbv = new FirmBalanceValue();
    BankAdapterRMI bankadapter = getAdapter(bankid);
    try
    {
      double accountBalance;
      double accountBalance;
      if (bankadapter != null)
      {
        CorrespondValue cv = new CorrespondValue();
        cv.firmID = firmid;
        accountBalance = bankadapter.accountQuery(cv, pwd);
      }
      else
      {
        accountBalance = -920000.0D;
      }
      fbv.setFirmId(firmid);
      fbv.setFirmBankId(bankid);
      fbv.setBankBalance(accountBalance);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    return fbv;
  }
  
  public ReturnValue moneyInAudit(long actionID, String funID, boolean funFlag)
  {
    log("审核处理中的信息 moneyInAudit actionID[" + actionID + "]funFlag[" + funFlag + "]");
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
          if ((capital.type == 1) && (capital.firmID.equals("MarketIncomeOut")))
          {
            DAO.modCapitalInfoStatus(capital.iD, funID, 0, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "审核通过", conn);
          }
          else if (capital.type == 1)
          {
            dataProcess.updateFundsFull(capital.bankID, capital.firmID, actionID, capital.money, 1, conn);
            dataProcess.updateFrozenFunds(capital.firmID, -1.0D * capital.money, conn);
            
            bankFrozenFuns(capital.firmID, capital.contact, capital.bankID, null, -1.0D * capital.money, conn);
            DAO.modCapitalInfoStatus(capital.iD, funID, 0, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "审核通过", conn);
          }
          else if (capital.type == 0)
          {
            dataProcess.updateFundsFull(capital.bankID, capital.firmID, actionID, capital.money, 0, conn);
            DAO.modCapitalInfoStatus(capital.iD, funID, 0, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "审核通过", conn);
          }
          DAO.modCapitalInfoTrader(capital.iD, "manual", conn);
          log(actionID + "单边账审核通过");
          conn.commit();
        }
        catch (SQLException e)
        {
          conn.rollback();
          log(actionID + "审核出金SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
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
          if ((capital.type == 1) && (capital.firmID.equals("MarketIncomeOut")))
          {
            DAO.modCapitalInfoStatus(capital.iD, result.funID, 1, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "审核拒绝", conn);
          }
          else if (capital.type == 1)
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
          log(actionID + "单边账审核拒绝");
          conn.commit();
        }
        catch (SQLException e)
        {
          conn.rollback();
          log(actionID + "审核出金SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
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
      result.remark = ("单边账处理[" + actionID + "]数据库异常");
      log(actionID + "审核出金SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      result.result = -30005L;
      result.remark = ("单边账处理[" + actionID + "]系统异常");
      log(Tool.getExceptionTrace(e));
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
}
