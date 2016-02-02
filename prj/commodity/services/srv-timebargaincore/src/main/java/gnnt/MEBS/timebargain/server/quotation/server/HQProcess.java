package gnnt.MEBS.timebargain.server.quotation.server;

import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.quotation.HQEngine;
import gnnt.MEBS.timebargain.server.quotation.config.Config;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HQProcess
  extends Thread
{
  private Log log = LogFactory.getLog(HQProcess.class);
  public Config config;
  private boolean runFlag;
  private long currentNum;
  private HQServer quotationServer;
  
  private void initDate()
  {
    this.quotationServer = HQServer.getInstance();
    this.config = HQEngine.config;
  }
  
  public void run()
  {
    int i = 0;
    int j = 0;
    int k = 5;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(0L);
    sleepMill(2000L);
    initDate();
    if (!this.quotationServer.checkTradeTime()) {
      return;
    }
    if (this.quotationServer.getTradeDate() == null)
    {
      this.log.error("没有加载交易节信息，请先初始化市场再开启QUOTATION");
      return;
    }
    long l = Long.parseLong(this.quotationServer.getHQDate() == null ? "0" : this.quotationServer.getHQDate());
    if ((l == 0L) || (this.quotationServer.getTradeDateForLong() > l))
    {
      this.log.info("系统启动boot3：初始化交易数据..!");
      if (!this.quotationServer.initHQData(localCalendar))
      {
        this.log.error("系统初始化行情不成功，请检查数据库连接是否正常或查看错误日志");
        return;
      }
      l = this.quotationServer.getTradeDateForLong();
    }
    this.quotationServer.checkTradeSec();
    this.currentNum = this.quotationServer.loadQuotationNO();
    while (!this.runFlag)
    {
      if (this.quotationServer.getSysInfo() == null)
      {
        this.log.error("交易系统没有设置系统状态，请检查Timebargain配置");
        return;
      }
      if (this.quotationServer.getTradeDate() == null)
      {
        this.log.info("获取不到当前所在交易日");
        sleepMill(this.config.timeSpace);
      }
      else
      {
        if ((this.quotationServer.getTradeDate() != null) && (l == 0L))
        {
          this.log.info("行情表为空，初始化行情数据...");
          if (!this.quotationServer.initHQData(localCalendar))
          {
            this.log.error("系统初始化行情不成功，请检查数据库连接是否正常或查看错误日志");
            return;
          }
          this.currentNum = 0L;
          l = this.quotationServer.getTradeDateForLong();
        }
        if ((this.quotationServer.getTradeDateForLong() > l) && (this.quotationServer.checkInitTime()))
        {
          if (!this.quotationServer.initHQData(localCalendar))
          {
            this.log.error("系统初始化行情不成功，请检查数据库连接是否正常或查看错误日志");
            return;
          }
          this.currentNum = 0L;
          l = this.quotationServer.getTradeDateForLong();
        }
        if (this.quotationServer.quoCodeSet.isEmpty())
        {
          this.quotationServer.loadCodeSet();
          this.log.debug("quoCodeSet:" + this.quotationServer.quoCodeSet.size());
        }
        int m = this.quotationServer.getSysInfo().getStatus();
        if ((m == 0) || (m == 5) || (m == 8) || (m == 9) || (m == 6)) {
          this.quotationServer.updateSysdate();
        }
        int n;
        switch (m)
        {
        case 0: 
          this.log.debug("inter into init status........");
          sleepMill(this.config.timeSpace);
          break;
        case 5: 
          List localList = this.quotationServer.queryQuotation(this.currentNum);
          if ((localList != null) && (localList.size() > 0))
          {
            this.currentNum += localList.size();
            Iterator localIterator = localList.iterator();
            while (localIterator.hasNext())
            {
              Quotation localQuotation = (Quotation)localIterator.next();
              String str = localQuotation.getCommodityID();
              if (this.quotationServer.quoCodeSet.add(str))
              {
                this.quotationServer.addOneComty(str);
                this.quotationServer.addOneData(localQuotation);
              }
            }
            this.quotationServer.transferProductData(localList);
            localCalendar.setTime(this.quotationServer.getTradeTime());
            this.log.debug("Update time = " + localCalendar.getTime());
          }
          if (i != 0) {
            i = 0;
          }
          if (j != 0) {
            j = 0;
          }
          sleepMill(this.config.timeSpace);
          break;
        case 7: 
          if (j == 0)
          {
            this.log.debug("inter into trade close status........");
            this.quotationServer.updateSysdate();
            for (n = 0; n < k; n++)
            {
              sleepMill(1000L);
              if (this.quotationServer.checkLastQuotation()) {
                localCalendar.setTime(this.quotationServer.getTradeTime());
              }
            }
            j = 1;
          }
          sleepMill(this.config.timeSpace);
          break;
        case 10: 
          if (i == 0)
          {
            if (!this.quotationServer.isBackUp()) {
              this.quotationServer.backupProcess();
            }
            i = 1;
          }
          sleepMill(this.config.timeSpace);
          break;
        case 8: 
          sleepMill(this.config.timeSpace);
          break;
        case 9: 
          sleepMill(this.config.timeSpace);
          break;
        case 1: 
        case 2: 
        case 3: 
        case 4: 
        case 6: 
        default: 
          if (j == 0)
          {
            for (n = 0; n < k; n++)
            {
              sleepMill(1000L);
              if (this.quotationServer.checkLastQuotation()) {
                localCalendar.setTime(this.quotationServer.getTradeTime());
              }
            }
            j = 1;
          }
          sleepMill(this.config.timeSpace);
        }
      }
    }
  }
  
  public void sleepMill(long paramLong)
  {
    try
    {
      Thread.sleep(paramLong);
    }
    catch (InterruptedException localInterruptedException)
    {
      this.log.error(localInterruptedException.getMessage());
    }
  }
  
  public boolean isEnd()
  {
    return this.runFlag;
  }
  
  public void shutdown()
  {
    this.runFlag = true;
    if (this.quotationServer == null) {
      this.quotationServer = HQServer.getInstance();
    }
    this.quotationServer.clearData();
  }
}
