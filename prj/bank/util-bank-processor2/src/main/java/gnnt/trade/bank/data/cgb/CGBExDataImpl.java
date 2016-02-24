package gnnt.trade.bank.data.cgb;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.CGBCapitalProcessor;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.cgb.CGBBankDAO;
import gnnt.trade.bank.data.cgb.vo.AccountStatusReconciliation;
import gnnt.trade.bank.data.cgb.vo.CGBConstant;
import gnnt.trade.bank.data.cgb.vo.StorageMoneyLedgerBalanceList;
import gnnt.trade.bank.data.cgb.vo.StorageMoneySettlementList;
import gnnt.trade.bank.data.cgb.vo.TransferAccountsTransactionDetailed;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.DateVirtualFunds;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

public class CGBExDataImpl
{
  private CGBBankDAO DAO;
  private CGBCapitalProcessor cp;
  private BankAdapterRMI bankAdapter;
  
  public CGBExDataImpl()
  {
    try
    {
      this.DAO = BankDAOFactory.getCGBDAO();
      this.cp = new CGBCapitalProcessor();
      this.bankAdapter = this.cp.getAdapter(CGBConstant.bankID);
    }
    catch (ClassNotFoundException e)
    {
      log(Tool.getExceptionTrace(e));
    }
    catch (IllegalAccessException e)
    {
      log(Tool.getExceptionTrace(e));
    }
    catch (InstantiationException e)
    {
      log(Tool.getExceptionTrace(e));
    }
  }
  
  public ReturnValue send(Date date)
  {
    log("[广发清算业务date" + date + "]");
    ReturnValue returnValue = sendCGBQS(date);
    
    return returnValue;
  }
  
  public ReturnValue sendCGBQS(Date date)
  {
    ReturnValue result = new ReturnValue();
    Connection conn = null;
    log("[广发清算业务" + date + "]");
    if (this.bankAdapter == null)
    {
      result.result = -920000L;
      result.remark = ("发送[" + CGBConstant.bankID + "]银行交易商清算信息，连接适配器失败");
    }
    else
    {
      result.result = -1L;
      

      boolean creatFileFlag = true;
      try
      {
        conn = this.DAO.getConnection();
        try
        {
          List<TransferAccountsTransactionDetailed> transfer = getZZJYMX(CGBConstant.bankID, date, conn);
          
          List<AccountStatusReconciliation> accountStatus = getKHZHZT(CGBConstant.bankID, date, conn);
          
          List<StorageMoneySettlementList> storageMoney = getCGKHZJJSMX(CGBConstant.bankID, date, conn);
          
          List<StorageMoneyLedgerBalanceList> balance = getCGKHZJTZYEMX(CGBConstant.bankID, date, conn);
          result = this.bankAdapter.sendCGBQS(transfer, accountStatus, storageMoney, balance, date);
          if (creatFileFlag)
          {
            log("中行清算文件生成发送完毕");
            result.result = 0L;
          }
        }
        catch (Exception e)
        {
          result.result = -1L;
          result.remark = "查询数据库数据异常";
          e.printStackTrace();
          ReturnValue localReturnValue1 = result;return localReturnValue1;
        }
        finally
        {
          this.DAO.closeStatement(null, null, conn);
        }
      }
      catch (SQLException e)
      {
        result.result = -1L;
        result.remark = "获取数据库连接异常";
        e.printStackTrace();
      }
      catch (Exception e)
      {
        result.result = -1L;
        result.remark = ("发送[" + CGBConstant.bankID + "]银行交易商清算信息，适配器抛出异常");
        e.printStackTrace();
      }
    }
    return result;
  }
  
  public List<TransferAccountsTransactionDetailed> getZZJYMX(String bankID, Date tradeDate, Connection conn)
    throws Exception
  {
    List<TransferAccountsTransactionDetailed> result = null;
    try
    {
      conn.setAutoCommit(false);
      result = this.DAO.getZZJYMX(bankID, tradeDate, conn);
    }
    catch (Exception e)
    {
      log("生成转账交易对账明细文档文件异常");
      log(Tool.getExceptionTrace(e));
    }
    return result;
  }
  
  public List<AccountStatusReconciliation> getKHZHZT(String bankID, Date tradeDate, Connection conn)
    throws Exception
  {
    boolean flag = false;
    String contact = "";
    List<AccountStatusReconciliation> result = null;
    try
    {
      conn.setAutoCommit(false);
      result = this.DAO.getKHZHZT(bankID, tradeDate, conn);
    }
    catch (Exception e)
    {
      log("生成客户账户状态文档文件异常");
      log(Tool.getExceptionTrace(e));
    }
    return result;
  }
  
  public List<StorageMoneySettlementList> getCGKHZJJSMX(String bankID, Date tradeDate, Connection conn)
    throws Exception
  {
    List<StorageMoneySettlementList> result = null;
    try
    {
      conn.setAutoCommit(false);
      result = this.DAO.getCGKHZJJSMX(bankID, tradeDate, conn);
    }
    catch (Exception e)
    {
      log("生成存管客户交收明细文件异常");
      log(Tool.getExceptionTrace(e));
    }
    return result;
  }
  
  public List<StorageMoneyLedgerBalanceList> getCGKHZJTZYEMX(String bankID, Date tradeDate, Connection conn)
    throws Exception
  {
    List<StorageMoneyLedgerBalanceList> result = null;
    try
    {
      conn.setAutoCommit(false);
      
      result = this.DAO.getCGKHZJTZYEMX(bankID, tradeDate, conn);
    }
    catch (Exception e)
    {
      log("生成存管客户余额明细文件异常");
      log(Tool.getExceptionTrace(e));
    }
    return result;
  }
  
  public static void log(String content)
  {
    Logger plog = Logger.getLogger("Processorlog");
    plog.debug(content);
  }
  
  private ReturnValue addVirtualFunds(Date tradeDate)
  {
    log("添加虚拟资金到数据库" + tradeDate);
    ReturnValue result = new ReturnValue();
    Connection conn = null;
    try
    {
      conn = this.DAO.getConnection();
      Map<String, Double> map = this.DAO.getAllVirtualFunds(null, conn);
      
      DateVirtualFunds dv = new DateVirtualFunds();
      for (int i = 0; i < map.size(); i++)
      {
        Set<String> keySet = map.keySet();
        for (String st : keySet)
        {
          dv.bankID = CGBConstant.bankID;
          dv.firmid = "";
          dv.contact = st;
          dv.virtualFunds = ((Double)map.get(st)).doubleValue();
          dv.createDate = tradeDate;
          if (dv.virtualFunds > 0.0D) {
            this.DAO.addDateVirtualFunds(dv, conn);
          }
        }
      }
    }
    catch (Exception e)
    {
      result.result = -1L;
      result.remark = "保存每日虚拟资金，系统异常";
      log(result.remark);
      e.printStackTrace();
    }
    finally
    {
      this.DAO.closeStatement(null, null, conn);
    }
    return result;
  }
}
