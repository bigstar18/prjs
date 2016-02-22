package gnnt.trade.bank.data.boc;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.BOCCapitalProcessor;
import gnnt.trade.bank.dao.BOCBankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.data.boc.vo.AccountStatusReconciliation;
import gnnt.trade.bank.data.boc.vo.BOCConstant;
import gnnt.trade.bank.data.boc.vo.FileProcessor;
import gnnt.trade.bank.data.boc.vo.StorageMoneyLedgerBalanceList;
import gnnt.trade.bank.data.boc.vo.StorageMoneySettlementList;
import gnnt.trade.bank.data.boc.vo.TransferAccountsTransactionDetailed;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.DateVirtualFunds;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

public class BOCExDataImpl
{
  private BOCBankDAO DAO;
  private FileProcessor fp;
  private BOCCapitalProcessor cp;
  private BankAdapterRMI bankAdapter;
  
  public BOCExDataImpl()
  {
    try
    {
      this.DAO = BankDAOFactory.getBOCDAO();
      this.fp = new FileProcessor();
      this.cp = new BOCCapitalProcessor();
      this.bankAdapter = this.cp.getAdapter(BOCConstant.bankID);
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
    log("[中行清算业务date" + date + "]");
    ReturnValue returnValue = sendZHQS(date);
    
    return returnValue;
  }
  
  public ReturnValue sendZHQS(Date date)
  {
    ReturnValue result = new ReturnValue();
    Connection conn = null;
    log("[中行清算业务" + date + "]");
    if (this.bankAdapter == null)
    {
      result.result = -920000L;
      result.remark = ("发送[" + BOCConstant.bankID + "]银行交易商清算信息，连接适配器失败");
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
          if (creatFileFlag) {
            creatFileFlag = getZZJYMX(BOCConstant.bankID, date, conn);
          }
          if (creatFileFlag) {
            creatFileFlag = getKHZHZT(BOCConstant.bankID, date, conn);
          }
          if (creatFileFlag) {
            creatFileFlag = getCGKHZJJSMX(BOCConstant.bankID, date, conn);
          }
          if (creatFileFlag) {
            creatFileFlag = getCGKHZJTZYEMX(BOCConstant.bankID, date, conn);
          }
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
        result.remark = ("发送[" + BOCConstant.bankID + "]银行交易商清算信息，适配器抛出异常");
        e.printStackTrace();
      }
    }
    return result;
  }
  
  public boolean getZZJYMX(String bankID, Date tradeDate, Connection conn)
    throws Exception
  {
    boolean flag = false;
    String contact = "";
    List<TransferAccountsTransactionDetailed> result = null;
    try
    {
      conn.setAutoCommit(false);
      result = this.DAO.getZZJYMX(bankID, tradeDate, conn);
      contact = this.fp.setS01(result, tradeDate);
      String fileName = "S01" + Common.df7.format(tradeDate);
      flag = this.bankAdapter.createDataFile(fileName, contact, tradeDate);
    }
    catch (Exception e)
    {
      log("生成转账交易对账明细文档文件异常");
      log(Tool.getExceptionTrace(e));
    }
    return flag;
  }
  
  public boolean getKHZHZT(String bankID, Date tradeDate, Connection conn)
    throws Exception
  {
    boolean flag = false;
    String contact = "";
    List<AccountStatusReconciliation> result = null;
    try
    {
      conn.setAutoCommit(false);
      result = this.DAO.getKHZHZT(bankID, tradeDate, conn);
      contact = this.fp.setS02(result, tradeDate);
      String fileName = "S02" + Common.df7.format(tradeDate);
      flag = this.bankAdapter.createDataFile(fileName, contact, tradeDate);
    }
    catch (Exception e)
    {
      log("生成客户账户状态文档文件异常");
      log(Tool.getExceptionTrace(e));
    }
    return flag;
  }
  
  public boolean getCGKHZJJSMX(String bankID, Date tradeDate, Connection conn)
    throws Exception
  {
    List<StorageMoneySettlementList> result = null;
    boolean flag = false;
    String contact = "";
    try
    {
      conn.setAutoCommit(false);
      result = this.DAO.getCGKHZJJSMX(bankID, tradeDate, conn);
      contact = this.fp.setS06(result, tradeDate);
      String fileName = "S06" + Common.df7.format(tradeDate);
      flag = this.bankAdapter.createDataFile(fileName, contact, tradeDate);
    }
    catch (Exception e)
    {
      log("生成存管客户交收明细文件异常");
      log(Tool.getExceptionTrace(e));
    }
    return flag;
  }
  
  public boolean getCGKHZJTZYEMX(String bankID, Date tradeDate, Connection conn)
    throws Exception
  {
    List<StorageMoneyLedgerBalanceList> result = null;
    boolean flag = false;
    String contact = "";
    try
    {
      conn.setAutoCommit(false);
      
      result = this.DAO.getCGKHZJTZYEMX(bankID, tradeDate, conn);
      contact = this.fp.setS07(result, tradeDate);
      String fileName = "S07" + Common.df7.format(tradeDate);
      flag = this.bankAdapter.createDataFile(fileName, contact, tradeDate);
    }
    catch (Exception e)
    {
      log("生成存管客户交收明细文件异常");
      log(Tool.getExceptionTrace(e));
    }
    return flag;
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
          dv.bankID = BOCConstant.bankID;
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
