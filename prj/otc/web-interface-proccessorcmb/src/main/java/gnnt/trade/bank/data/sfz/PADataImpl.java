package gnnt.trade.bank.data.sfz;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.PACapitalProcessor;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.PABankDAO;
import gnnt.trade.bank.data.boc.vo.FileProcessor;
import gnnt.trade.bank.data.sfz.vo.BatQs;
import gnnt.trade.bank.data.sfz.vo.BatQsChild;
import gnnt.trade.bank.data.sfz.vo.PAConstant;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.ReturnValue;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import org.apache.log4j.Logger;

public class PADataImpl
{
  private PABankDAO DAO;
  private FileProcessor fp;
  private PACapitalProcessor cp;
  private BankAdapterRMI bankAdapter;
  
  public PADataImpl()
  {
    try
    {
      this.DAO = BankDAOFactory.getPADAO();
      this.fp = new FileProcessor();
      this.cp = new PACapitalProcessor();
      this.bankAdapter = this.cp.getAdapter(PAConstant.bankID);
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
    log("[平安银行清算业务date" + date + "]");
    ReturnValue returnValue = sentMaketQS(date, PAConstant.bankID);
    return returnValue;
  }
  
  public ReturnValue sentMaketQS(Date date, String bankID)
  {
    log("发送平安银行清算开始");
    log("参数：date[" + Tool.fmtDate(date) + "]bankID[" + bankID + "]");
    ReturnValue rv = new ReturnValue();
    Connection conn = null;
    BatQs bq = new BatQs();
    try
    {
      bq.child = getQSChild(date, bankID);
      bq.rowCount = bq.child.size();
      if (this.bankAdapter == null)
      {
        rv.result = -920000L;
        rv.remark = ("发送[" + PAConstant.bankID + "]银行交易商清算信息，处理器连接适配器失败");
      }
      else
      {
        ReturnValue rrv = this.bankAdapter.sentMaketQS(bq);
        if (rrv.result >= 0L)
        {
          conn = this.DAO.getConnection();
          rv.result = 0L;
          rv.remark = "发送给银行清算文件成功";
        }
        else
        {
          rv.result = rrv.result;
          rv.remark = rrv.remark;
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      rv.result = -2L;
      rv.remark = "发送市场清算文件异常";
    }
    finally
    {
      this.DAO.closeStatement(null, null, conn);
    }
    log("返回结果：\n" + rv.toString());
    return rv;
  }
  
  private Vector<BatQsChild> getQSChild(Date date, String bankID)
  {
    log("获取清算信息  getQSChild  时间：" + Tool.fmtTime(new Date()));
    Vector<BatQsChild> result = new Vector();
    Map<String, BatQsChild> today = null;
    Connection conn = null;
    Date usdate = date;
    try
    {
      conn = this.DAO.getConnection();
      today = this.DAO.getQSChild(bankID, null, null, usdate, conn);
      System.out.println(today.size());
      
      Iterator<Map.Entry<String, BatQsChild>> it = today.entrySet()
        .iterator();
      while (it.hasNext())
      {
        Map.Entry<String, BatQsChild> ent = 
          (Map.Entry)it.next();
        result.add((BatQsChild)ent.getValue());
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      this.DAO.closeStatement(null, null, conn);
    }
    System.out.println("返回信息条数：" + result.size());
    return result;
  }
  
  public static void log(String content)
  {
    Logger plog = Logger.getLogger("Processorlog");
    plog.debug(content);
  }
}
