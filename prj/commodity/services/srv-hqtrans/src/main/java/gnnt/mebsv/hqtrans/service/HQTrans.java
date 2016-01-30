package gnnt.mebsv.hqtrans.service;

import gnnt.MEBS.util.Configuration;
import gnnt.mebsv.hqtrans.dao.HQTransDAO;
import gnnt.mebsv.hqtrans.dao.factory.HQTransDAOFactory;
import gnnt.mebsv.hqtrans.model.FileParameter;
import gnnt.mebsv.hqtrans.service.io.fileio.FileIO;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HQTrans
  extends Thread
{
  private static Log log = LogFactory.getLog(HQTrans.class);
  private HQTransDAO dao = null;
  private FileIO fileCreate;
  private int timeIntervalCheck;
  
  public boolean init()
  {
    this.fileCreate = new FileIO();
    Properties localProperties = new Configuration().getSection("MEBS.Quotation");
    if (localProperties == null) {
      return false;
    }
    try
    {
      this.dao = HQTransDAOFactory.getDAO(localProperties);
      if (!this.fileCreate.init(this.dao)) {
        return false;
      }
      this.timeIntervalCheck = this.fileCreate.getFileParameter().getTimeIntervalCheck();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return false;
    }
    return true;
  }
  
  public void run()
  {
    try
    {
      for (;;)
      {
        Map localMap = getMarketStatus();
        Iterator localIterator = localMap.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          String str = (String)localEntry.getKey();
          int i = ((Integer)localEntry.getValue()).intValue();
          switch (i)
          {
          case -1: 
            System.out.println("--->Open");
            this.fileCreate.checkCreateKLineFlag(str);
            System.out.print(".");
            break;
          case 1: 
            System.out.println("--->K");
            if (!this.fileCreate.isCreateKLine(str))
            {
              log.debug("Start to create KLine" + str);
              log.debug("Start to create day line file..." + str);
              createKLineFile("day", str);
              log.debug("Start to create 5 minutes line file..." + str);
              createKLineFile("5min", str);
              log.debug("Start to create 1 minutes line file..." + str);
              createKLineFile("min", str);
              log.debug("Finished !" + str);
            }
            System.out.print("Done." + str);
            break;
          default: 
            log.debug("--->default:" + i + ",marketid:" + str);
          }
        }
        try
        {
          Thread.sleep(this.timeIntervalCheck * 1000);
        }
        catch (InterruptedException localInterruptedException2)
        {
          localInterruptedException2.printStackTrace();
        }
      }
    }
    catch (SQLException localSQLException1)
    {
      localSQLException1.printStackTrace();
      try
      {
        Thread.sleep(this.timeIntervalCheck * 1000);
      }
      catch (InterruptedException localInterruptedException1)
      {
        localInterruptedException1.printStackTrace();
      }
      try
      {
        if (this.dao.conn != null)
        {
          this.dao.conn.close();
          System.out.println("数据库连接异常.....关闭Connection..........");
        }
        if (this.dao.conn.isClosed()) {
          if (!init()) {
            System.out.println("数据库重连失败.....");
          } else {
            System.out.println("数据库重连成功....");
          }
        }
      }
      catch (SQLException localSQLException2)
      {
        localSQLException2.printStackTrace();
      }
    }
  }
  
  Map<String, Integer> getMarketStatus()
    throws SQLException
  {
    return this.dao.getMarketStatus();
  }
  
  public void createKLineFile(String paramString1, String paramString2)
  {
    try
    {
      this.fileCreate.createKLineFile(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}
