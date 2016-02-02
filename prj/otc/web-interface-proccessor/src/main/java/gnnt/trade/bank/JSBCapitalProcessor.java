package gnnt.trade.bank;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.jsb.JSBBankDAO;
import gnnt.trade.bank.data.jsb.JsConstant;
import gnnt.trade.bank.data.jsb.JsExDataImpl;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfo;
import gnnt.trade.bank.vo.TransferInfoValue;
import gnnt.trade.bank.vo.TransferMoneyVO;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class JSBCapitalProcessor
  extends CapitalProcessor
{
  private static final int INMONEY = 0;
  private static final int OUTMONEY = 1;
  private static final int RATE = 3;
  private static JSBBankDAO DAO;
  
  public JSBCapitalProcessor()
  {
    try
    {
      DAO = BankDAOFactory.getJSBDAO();
    }
    catch (Exception e)
    {
      log("初始化江苏银行DAO对象处理器失败：" + Tool.getExceptionTrace(e));
    }
  }
  
  public ReturnValue synchroAccountMarket(CorrespondValue cv)
  {
    log(">>>>市场同步账号方法>>>>时间：" + Tool.fmtTime(new Date()) + ">>>>\ncv[" + cv.toString() + "]");
    long checkResult = 0L;
    ReturnValue rv = new ReturnValue();
    CorrespondValue cv2 = null;
    if (checkResult == 0L)
    {
      rv.actionId = getMktActionID();
      try
      {
        List<CorrespondValue> cvresult = DAO.getCorrespondList(" and firmId='" + cv.firmID.trim() + "' ");
        if ((cvresult == null) || (cvresult.size() <= 0))
        {
          rv.result = -40001L;
        }
        else
        {
          cv2 = (CorrespondValue)cvresult.get(0);
          cv2.isOpen = 3;
          cv2.status = cv.status;
          cv2.account = cv.account;
          cv2.bankID = cv.bankID;
          cv2.isCrossline = cv.isCrossline;
          BankAdapterRMI bankadapter = getAdapter(cv2.bankID);
          if (bankadapter == null)
          {
            rv.result = -920000L;
          }
          else
          {
            rv = bankadapter.rgstAccountQuery(cv2);
            if (rv.result == 0L)
            {
              cvresult = DAO.getCorrespondList(" and contact='" + cv2.contact + "' and (bankid is null or bankid='" + cv2.bankID + "')");
              if ((cvresult != null) && (cvresult.size() <= 0)) {
                rv.result = DAO.addCorrespond(cv2);
              } else {
                rv.result = DAO.modCorrespond(cv2);
              }
            }
            else
            {
              log("同步账户失败!");
            }
          }
        }
      }
      catch (SQLException e)
      {
        rv.result = -40006L;
        e.printStackTrace();
        log("市场同步账号 交易商代码与银行帐号对应SQLException," + e);
      }
      catch (Exception e)
      {
        rv.result = -40007L;
        e.printStackTrace();
        log("市场同步账号 交易商代码与银行帐号对应SQLException," + e);
      }
    }
    else
    {
      rv.result = ((int)checkResult);
      rv.remark = "交易商代码与银行、帐号对应有误!";
    }
    return rv;
  }
  
  public ReturnValue delAccountMaket(CorrespondValue correspondValue)
  {
    log(">>>>市场端预解约账号方法>>>>时间：" + Tool.fmtTime(new Date()) + ">>>>\ncv[" + correspondValue.toString() + "]");
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
      ReturnValue localReturnValue2;
      if ((capitals != null) && (capitals.size() > 0))
      {
        result.result = -941002L;
        result.remark = "当日有转账，不能解约";
        localReturnValue2 = result;return localReturnValue2;
      }
      BankAdapterRMI bankadapter = getAdapter(correspondValue.bankID);
      if (bankadapter == null)
      {
        result.result = -920000L;
        result.remark = "连接银行通讯机(适配器)失败";
        localReturnValue2 = result;return localReturnValue2;
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
          











          DAO.closeStatement(null, null, conn);return localReturnValue1;
        }
        if (result.result == -3L)
        {
          conn.rollback();
          result.remark = "次账户与其他签约银行，不满足交易系统解约条件";
          localReturnValue1 = result;
          




































          conn.setAutoCommit(true);
          











          DAO.closeStatement(null, null, conn);return localReturnValue1;
        }
        cv.firmID = cv.contact;
        cv.actionID = correspondValue.actionID;
        cv.signInfo = correspondValue.signInfo;
        cv.bankCardPassword = correspondValue.bankCardPassword;
        ReturnValue returnValue = bankadapter.delAccountQuery(cv);
        if (returnValue == null)
        {
          conn.rollback();
          log("解约，银行返回信息为空");
          result.result = -50010L;
          result.remark = "解约适配器返回对象为空";
          localReturnValue1 = result;
          
















          conn.setAutoCommit(true);
          











          DAO.closeStatement(null, null, conn);return localReturnValue1;
        }
        if (returnValue.result < 0L)
        {
          conn.rollback();
          log(returnValue.toString());
          result.result = returnValue.result;
          result.remark = returnValue.remark;
          
          localReturnValue1 = result;
          








          conn.setAutoCommit(true);
          











          DAO.closeStatement(null, null, conn);return localReturnValue1;
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
      result.remark = "解约账户信息成功";
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
    }
    return result;
  }
  
  public ReturnValue transferPositions(TransferInfo trans)
  {
    System.out.println(">>>>江苏银行头寸、收益划转>>>>时间：" + Tool.fmtTime(new Date()) + ">>>>\n.money[" + trans.money + "]type[" + trans.type + "]");
    ReturnValue rv = new ReturnValue();
    BankAdapterRMI bankadapter = getAdapter(trans.bankid);
    if (bankadapter == null)
    {
      rv.result = -920000L;
    }
    else
    {
      TransferInfoValue pay = new TransferInfoValue();
      pay.account = trans.outAccount;
      pay.accountName = trans.outAccountName;
      TransferInfoValue receive = new TransferInfoValue();
      receive.account = trans.inAccount;
      receive.accountName = trans.inAccountName;
      TransferMoneyVO rmo = new TransferMoneyVO(trans.bankid, null, trans.money, pay, receive, 0L);
      rmo.type = trans.type;
      try
      {
        rv = bankadapter.transferMoney(rmo);
        if (0L == rv.result) {
          trans.status = 0;
        } else if (5L == rv.result) {
          trans.status = 2;
        } else {
          trans.status = 1;
        }
        trans.funId = rv.funID;
        trans.actionId = rv.actionId;
        DAO.addTransfer(trans);
      }
      catch (RemoteException e)
      {
        log(Tool.getExceptionTrace(e));
      }
      catch (SQLException e)
      {
        log(Tool.getExceptionTrace(e));
      }
      catch (ClassNotFoundException e)
      {
        log(Tool.getExceptionTrace(e));
      }
    }
    return rv;
  }
  
  public ReturnValue send(Date tradeDate)
  {
    return new JsExDataImpl().send(tradeDate);
  }
  
  public ReturnValue modTransferStatus(long actionID, String funID, boolean funFlag)
  {
    System.out.println(">>>>银行通知头寸、收益划转确认>>>>时间：" + Tool.fmtTime(new Date()));
    Connection conn = null;
    Timestamp curTime = new Timestamp(System.currentTimeMillis());
    TransferInfo transferInfo = null;
    ReturnValue result = new ReturnValue();
    try
    {
      conn = DAO.getConnection();
      Vector<TransferInfo> list = DAO.getTransferList(" and actionid=" + actionID);
      ReturnValue localReturnValue1;
      for (int i = 0; i < list.size(); i++)
      {
        val = (TransferInfo)list.get(i);
        
        transferInfo = val;
        result.actionId = Long.valueOf(transferInfo.actionId).longValue();
        result.bankTime = Tool.fmtTime(curTime);
        result.funID = transferInfo.funId;
        if ((transferInfo.status == 0) || (transferInfo.status == 1))
        {
          result.result = -20042L;
          result.remark = ("本条记录[" + actionID + "]已经有人处理");
          log(result.remark);
          localReturnValue1 = result;return localReturnValue1;
        }
      }
      if (transferInfo == null)
      {
        result.result = -930000L;
        result.remark = ("未查询到流水[" + actionID + "]信息");
        localReturnValue1 = result;return localReturnValue1;
      }
      if (funFlag) {
        try
        {
          conn.setAutoCommit(false);
          DAO.modTransfer(transferInfo.actionId, funID, 0, curTime, conn);
          log(actionID + "转账流水银行确认审核通过");
          conn.commit();
        }
        catch (SQLException e)
        {
          conn.rollback();
          log(actionID + "审核转账流水SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
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
          DAO.modTransfer(transferInfo.actionId, funID, 1, curTime, conn);
          
          log(actionID + "转账流水银行确认审核拒绝");
          conn.commit();
        }
        catch (SQLException e)
        {
          conn.rollback();
          log(actionID + "审核转账SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
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
      result.remark = ("审核转账流水处理[" + actionID + "]数据库异常");
      log(actionID + "审核审核转账流水SQLException，错误码=" + result + "  " + Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      result.result = -30005L;
      result.remark = ("审核转账流水处理[" + actionID + "]系统异常");
      log(Tool.getExceptionTrace(e));
    }
    finally
    {
      DAO.closeStatement(null, null, conn);
    }
    return result;
  }
  
  public ReturnValue getQSDate(Date date, String type)
  {
    log("查看江苏行清算结果");
    ReturnValue rv = new ReturnValue();
    BankAdapterRMI bankadapter = getAdapter(JsConstant.bankID);
    if (bankadapter == null) {
      rv.result = -920000L;
    } else {
      try
      {
        rv = bankadapter.getQSDate(date, type);
      }
      catch (RemoteException e)
      {
        log(Tool.getExceptionTrace(e));
      }
    }
    return rv;
  }
}
