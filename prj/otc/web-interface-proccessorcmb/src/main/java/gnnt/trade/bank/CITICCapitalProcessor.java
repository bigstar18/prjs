package gnnt.trade.bank;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.citic.CITICBankDAO;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.OutMoneyMarket;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfoValue;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

public class CITICCapitalProcessor
  extends CapitalProcessor
{
  private static CITICBankDAO DAO;
  
  public CITICCapitalProcessor()
  {
    try
    {
      DAO = BankDAOFactory.getCITICDAO();
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
  
  public ReturnValue outMoney2Account(OutMoneyMarket omm, String isCorressline, String reciveAccount, String reciveName, String reciveBankName, String reciveBankCode)
  {
    log("提现: outMoney omm:" + (omm == null ? "为空" : new StringBuilder("\n").append(omm.toString()).append("\n").toString()));
    
    ReturnValue rv = new ReturnValue();
    
    long actionID = 0L;
    long id = 0L;
    Timestamp curTime = new Timestamp(System.currentTimeMillis());
    
    String auditInfo = "市场提现，银行号:" + omm.bankID;
    Connection conn = null;
    try
    {
      conn = DAO.getConnection();
      
      actionID = getMktActionID();
      CapitalValue cVal = new CapitalValue();
      cVal.trader = "";
      cVal.funID = "";
      cVal.actionID = actionID;
      cVal.firmID = omm.firmID;
      cVal.contact = omm.contact;
      cVal.account = omm.account;
      cVal.bankID = omm.bankID;
      cVal.type = 3;
      cVal.money = omm.money;
      cVal.status = 7;
      cVal.launcher = 0;
      cVal.note = "提现";
      log(cVal.toString());
      rv.result = DAO.addCapitalInfo(cVal, conn);
      id = rv.result;
      if (rv.result >= 0L)
      {
        TransferInfoValue payInfo = getPayInfo(omm.bankID, omm.firmID, 1, conn);
        TransferInfoValue receiveInfo = getReceiveInfo(omm.bankID, omm.firmID, 1, conn);
        receiveInfo.isCrossline = isCorressline;
        receiveInfo.account = reciveAccount;
        receiveInfo.accountName = reciveName;
        receiveInfo.bankName = reciveBankName;
        receiveInfo.OpenBankCode = reciveBankCode;
        OutMoneyVO outMoneyInfo = new OutMoneyVO(omm.bankID, "中信银行", omm.money, omm.firmID, payInfo, receiveInfo, actionID, "");
        outMoneyInfo.isCrossline = "03";
        BankAdapterRMI bankadapter = getAdapter(omm.bankID);
        if (bankadapter == null)
        {
          rv.result = -920000L;
          log("处理器连接适配器[" + omm.bankID + "]失败");
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
          DAO.modCapitalInfoNote(id, rv.remark, conn);
          log(auditInfo + "银行处理成功,出金成功,银行流水号=" + rv.funID);
        }
        else if (rv.result == 5L)
        {
          DAO.modCapitalInfoStatus(id, rv.funID, 2, curTime, conn);
          DAO.modCapitalInfoNote(id, rv.remark, conn);
          log(auditInfo + "银行处理中");
        }
        else if (rv.result == -50010L)
        {
          DAO.modCapitalInfoStatus(id, rv.funID, 5, curTime, conn);
          DAO.modCapitalInfoNote(id, rv.remark, conn);
          log(auditInfo + "银行无返回");
        }
        else if (rv.result == -100L)
        {
          DAO.modCapitalInfoStatus(id, rv.funID, 1, curTime, conn);
          DAO.modCapitalInfoNote(id, "银行处理失败:" + rv.remark, conn);
          log(auditInfo + "银行处理失败" + rv.remark);
        }
        else if (rv.result == -920008L)
        {
          DAO.modCapitalInfoStatus(id, rv.funID, 2, curTime, conn);
          log(auditInfo + "银行返回的市场流水号和市场本身流水不一致");
        }
        else
        {
          DAO.modCapitalInfoStatus(id, rv.funID, 2, curTime, conn);
          DAO.modCapitalInfoNote(id, rv.remark, conn);
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
  
  public ReturnValue modCaptailInfoOutMoney2Account(long actionID, String funID, boolean funFlag, String note)
  {
    log("回调处理银行审核待处理提现的信息 modCaptailInfoOutMoney actionID[" + actionID + "]funID[" + funID + "]funFlag[" + funFlag + "]note[" + note + "]");
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
        if ((val.type == 1) || (val.type == 0) || (val.type == 3))
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
            DAO.modCapitalInfoStatus(capital.iD, funID, 0, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "银行审核通过，出金成功", conn);
          }
          else if (capital.type == 0)
          {
            DAO.modCapitalInfoStatus(capital.iD, funID, 0, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "银行审核通过，入金成功", conn);
          }
          else if (capital.type == 3)
          {
            DAO.modCapitalInfoStatus(capital.iD, funID, 0, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "银行审核通过，提现成功", conn);
          }
          DAO.modCapitalInfoTrader(capital.iD, "manual", conn);
          log(actionID + "银行审核通过");
          conn.commit();
        }
        catch (SQLException e)
        {
          conn.rollback();
          log(actionID + "银行审核提现SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
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
            DAO.modCapitalInfoStatus(capital.iD, result.funID, 1, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "银行审核拒绝：" + note, conn);
          }
          else if (capital.type == 0)
          {
            DAO.modCapitalInfoStatus(capital.iD, result.funID, 1, curTime, conn);
            DAO.modCapitalInfoNote(capital.iD, "银行审核拒绝：" + note, conn);
          }
          else if (capital.type == 3)
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
          log(actionID + "审核提现SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
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
      result.remark = ("银行审核处理[" + actionID + "]数据库异常");
      log(actionID + "审核出金SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      result.result = -30005L;
      result.remark = ("银行审核处理[" + actionID + "]系统异常");
      log(Tool.getExceptionTrace(e));
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
  
  public ReturnValue modCaptailInfoOutMoney(long actionID, String funID, boolean funFlag, String note)
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
  
  public ReturnValue openAccountMarket(CorrespondValue cv)
  {
    log("中信签约:" + cv.toString());
    ReturnValue rv = new ReturnValue();
    try
    {
      rv.result = -1L;
      ReturnValue localReturnValue1;
      if ((cv.bankID == null) || (cv.bankID.length() == 0) || 
        (cv.firmID == null) || (cv.firmID.length() == 0) || 
        (cv.account1 == null) || (cv.account1.length() == 0) || 
        (cv.accountName1 == null) || (cv.accountName1.length() == 0))
      {
        rv.result = -1L;
        rv.remark = "信息不完整，请检查输入项";
        localReturnValue1 = rv;return localReturnValue1;
      }
      log("判断是否符合存在对应关系或者是第一个签约的银行");
      CorrespondValue cv1 = null;
      Vector<CorrespondValue> cpV = DAO.getCorrespondList(" and firmID='" + cv.firmID + "' ");
      if (cpV != null) {
        for (CorrespondValue cpv : cpV) {
          if ((cpv.bankID == null) || (cpv.bankID.equals("")) || (cv.bankID.equals(cpv.bankID)))
          {
            log("符合条件,赋值");
            cv1 = cpv;
            rv.result = 0L;
          }
        }
      }
      if (cv1 == null)
      {
        log("不符合上个条件，注册交易账号和银行账号的对应关系");
        log("注册信息");
        cv.isOpen = 0;
        cv.status = 1;
        rv.result = rgstAccount(cv);
        
        cpV = DAO.getCorrespondList(" and bankID='" + cv.bankID + "' and firmID='" + cv.firmID + "' ");
        if ((cpV != null) && (cpV.size() > 0))
        {
          log("注册完成，重新查询对应关系信息，主要是取得ID的值");
          cv1 = (CorrespondValue)cpV.get(0);
          rv.result = 0L;
        }
        else
        {
          rv.result = -1L;
          rv.remark = "添加和中信银行的对应关系失败";
          localReturnValue1 = rv;return localReturnValue1;
        }
      }
      if (rv.result == 0L)
      {
        log("注册信息成功强制签约");
        cv.isOpen = 1;
        cv.status = 0;
        cv.id = cv1.id;
        rv = modAccountMarket(cv, cv);
      }
    }
    catch (Exception e)
    {
      log("中信签约:" + Common.getExceptionTrace(e));
      rv.result = -1L;
      rv.remark = "系统异常，请稍后重新操作";
    }
    finally
    {
      log("中信签约结果：" + rv.toString());
    }
    return rv;
  }
  
  public FirmBalanceValue getFirmBalance(String bankid, String firmid, String pwd)
  {
    log("查询交易账号市场可用资金和银行账号余额 getFirmBalance bankid[" + bankid + "]firmid[" + firmid + "]pwd[" + pwd + "]");
    FirmBalanceValue fv = new FirmBalanceValue();
    try
    {
      ReturnValue rv = ifbankTrade(bankid, firmid, null, 2, 0);
      if (rv.result < 0L)
      {
        fv.setCanOutMoney(rv.result);
        FirmBalanceValue localFirmBalanceValue1 = fv;return localFirmBalanceValue1;
      }
      String acount1 = null;
      Vector<CorrespondValue> cpV = DAO.getCorrespondList(" and firmID='" + firmid + "' and bankid='" + bankid + "' ");
      if ((cpV != null) && (cpV.size() > 0)) {
        acount1 = ((CorrespondValue)cpV.get(0)).account1;
      }
      FirmBalanceValue fv2 = getBankBalance(bankid, firmid, pwd);
      fv.setFirmId(firmid);
      fv.setFirmBankId(bankid);
      fv.setBankAccount(acount1);
      fv.setBankBalance(fv2.bankBalance);
    }
    catch (SQLException e)
    {
      log(Common.getExceptionTrace(e));
      fv = new FirmBalanceValue();
      fv.bankBalance = -920016.0D;
    }
    catch (ClassNotFoundException e)
    {
      log(Common.getExceptionTrace(e));
      fv = new FirmBalanceValue();
      fv.bankBalance = -920016.0D;
    }
    catch (Exception e)
    {
      log(Common.getExceptionTrace(e));
      fv = new FirmBalanceValue();
      fv.bankBalance = -920016.0D;
    }
    finally
    {
      log("查询交易账号余额返回：\n" + fv.toString());
    }
    return fv;
  }
}
