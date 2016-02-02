package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.TradeTime;
import gnnt.MEBS.timebargain.server.util.DateUtil;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeTimeTask
  extends Thread
{
  private final Log log = LogFactory.getLog(getClass());
  private Server server;
  private List tradeTimeList = new ArrayList();
  private boolean threadEnd = false;
  private long diffTime;
  private int status;
  private long lastEndTime = 0L;
  private Integer sectionID;
  private long timeSpace = 200L;
  private int runStatus = 2;
  public static final int RUNSTATUS_RUNING = 0;
  public static final int RUNSTATUS_PAUSE = 1;
  public static final int RUNSTATUS_STOP = 2;
  public static final int STATUS_RECHECK = -1;
  
  public void init(Server paramServer)
  {
    this.server = paramServer;
    this.diffTime = paramServer.getServerInit().getDiffTime();
    this.status = paramServer.getSystemStatus().getStatus();
    this.sectionID = paramServer.getSystemStatus().getSectionID();
    refreshTradeTime();
    this.log.debug("status:" + this.status);
    this.log.debug("sectionID:" + this.sectionID);
    this.log.debug("diffTime:" + this.diffTime);
  }
  
  public void run()
  {
    while (!this.threadEnd)
    {
      if (this.runStatus == 0)
      {
        SystemStatus localSystemStatus = judgeSystemStatus(getDBTime());
        if (localSystemStatus.getStatus() != -1)
        {
          this.server.getSystemStatus().setSectionID(localSystemStatus.getSectionID());
          this.server.getSystemStatus().setStatus(localSystemStatus.getStatus());
          this.server.statusListener();
        }
      }
      else if (this.runStatus == 1)
      {
        continueTrade(getDBTime());
      }
      try
      {
        sleep(this.timeSpace);
      }
      catch (InterruptedException localInterruptedException) {}
    }
  }
  
  public void refreshTradeTime()
  {
    this.tradeTimeList.clear();
    this.tradeTimeList.addAll(this.server.getServerInit().getTradeTimeList());
    if (this.tradeTimeList != null) {
      for (int i = 0; i < this.tradeTimeList.size(); i++)
      {
        TradeTime localTradeTime = (TradeTime)this.tradeTimeList.get(i);
        this.log.debug("TradeTime::" + localTradeTime);
      }
    }
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public int getRunStatus()
  {
    return this.runStatus;
  }
  
  public void setRunStatus(int paramInt)
  {
    this.runStatus = paramInt;
  }
  
  public void recoverRun()
  {
    setStatus(-1);
    this.server.getSystemStatus().setRecoverTime(null);
    this.server.getServerDAO().updateSystemRecoverTime(null);
    setRunStatus(0);
  }
  
  public void close()
  {
    this.log.info("正在关闭交易节任务线程！");
    this.threadEnd = true;
    try
    {
      interrupt();
    }
    catch (Exception localException) {}
    this.log.info("成功关闭交易节任务线程！");
  }
  
  private SystemStatus judgeSystemStatus(long paramLong)
  {
    SystemStatus localSystemStatus = new SystemStatus(-1);
    int j = this.tradeTimeList.size();
    for (int k = 0; k < j; k++)
    {
      TradeTime localTradeTime = (TradeTime)this.tradeTimeList.get(k);
      int i = localTradeTime.getGatherBid().shortValue();
      if (i == 1)
      {
        if (k == 0)
        {
          if (paramLong < localTradeTime.getBidStartTimeMillis()) {
            break;
          }
        }
        else if ((paramLong >= this.lastEndTime) && (paramLong < localTradeTime.getBidStartTimeMillis()))
        {
          if (this.status != 6) {
            changeSystemStatus(localSystemStatus, 6, localTradeTime.getSectionID().intValue());
          }
          changeSectionID(localSystemStatus, localTradeTime.getSectionID().intValue());
          break;
        }
        if ((paramLong >= localTradeTime.getBidStartTimeMillis()) && (paramLong < localTradeTime.getBidEndTimeMillis()))
        {
          if (this.status != 8) {
            changeSystemStatus(localSystemStatus, 8, localTradeTime.getSectionID().intValue());
          }
          changeSectionID(localSystemStatus, localTradeTime.getSectionID().intValue());
          break;
        }
        if ((paramLong >= localTradeTime.getBidEndTimeMillis()) && (paramLong < localTradeTime.getStartTimeMillis()))
        {
          if (this.status != 9) {
            changeSystemStatus(localSystemStatus, 9, localTradeTime.getSectionID().intValue());
          }
          changeSectionID(localSystemStatus, localTradeTime.getSectionID().intValue());
          break;
        }
      }
      if (k == 0)
      {
        if (paramLong < localTradeTime.getStartTimeMillis()) {
          break;
        }
      }
      else if ((paramLong >= this.lastEndTime) && (paramLong < localTradeTime.getStartTimeMillis()))
      {
        if (this.status != 6) {
          changeSystemStatus(localSystemStatus, 6, localTradeTime.getSectionID().intValue());
        }
        changeSectionID(localSystemStatus, localTradeTime.getSectionID().intValue());
        break;
      }
      if ((paramLong >= localTradeTime.getStartTimeMillis()) && (paramLong < localTradeTime.getEndTimeMillis()))
      {
        if (this.status != 5)
        {
          changeSystemStatus(localSystemStatus, 5, localTradeTime.getSectionID().intValue());
          this.lastEndTime = localTradeTime.getEndTimeMillis();
        }
        changeSectionID(localSystemStatus, localTradeTime.getSectionID().intValue());
        if ((this.sectionID != null) && (localTradeTime.getSectionID().intValue() == this.sectionID.intValue())) {
          break;
        }
        this.lastEndTime = localTradeTime.getEndTimeMillis();
        break;
      }
      if ((k == j - 1) && (paramLong >= localTradeTime.getEndTimeMillis()))
      {
        if (this.status != 7) {
          changeSystemStatus(localSystemStatus, 7, localTradeTime.getSectionID().intValue());
        }
        changeSectionID(localSystemStatus, localTradeTime.getSectionID().intValue());
        break;
      }
    }
    return localSystemStatus;
  }
  
  private void changeSystemStatus(SystemStatus paramSystemStatus, int paramInt1, int paramInt2)
  {
    paramSystemStatus.setStatus(paramInt1);
    this.status = paramInt1;
    paramSystemStatus.setSectionID(Integer.valueOf(paramInt2));
    this.sectionID = Integer.valueOf(paramInt2);
  }
  
  private void changeSectionID(SystemStatus paramSystemStatus, int paramInt)
  {
    if ((this.sectionID == null) || (this.sectionID.intValue() != paramInt))
    {
      paramSystemStatus.setSectionID(Integer.valueOf(paramInt));
      this.sectionID = Integer.valueOf(paramInt);
      paramSystemStatus.setStatus(this.status);
    }
  }
  
  private void continueTrade(long paramLong)
  {
    String str = this.server.getSystemStatus().getRecoverTime();
    if ((str == null) || (str.equals(""))) {
      return;
    }
    try
    {
      long l = this.server.getServerInit().getTradeDateInterface().getRecoverDateByTime(str).getTime();
      if (paramLong >= l) {
        recoverRun();
      }
    }
    catch (ParseException localParseException)
    {
      this.log.error("恢复暂停后的交易时解析日期失败，原因：" + localParseException.getMessage());
      System.out.println(DateUtil.getCurDateTime() + "   恢复暂停后的交易时解析日期失败，原因：" + localParseException.getMessage());
    }
  }
  
  private long getDBTime()
  {
    long l;
    try
    {
      l = this.server.getServerDAO().getCurDbDate().getTime();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("取DB时间失败，使用本机时间+diffTime代替");
      l = System.currentTimeMillis() + this.diffTime;
    }
    return l;
  }
}
