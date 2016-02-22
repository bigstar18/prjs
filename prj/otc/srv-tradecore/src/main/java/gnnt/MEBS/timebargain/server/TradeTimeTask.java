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
  private Integer sectionID;
  private long timeSpace = 200L;
  private int runStatus = 2;
  public static final int RUNSTATUS_RUNING = 0;
  public static final int RUNSTATUS_PAUSE = 1;
  public static final int RUNSTATUS_STOP = 2;
  public static final int STATUS_RECHECK = -1;
  
  public void init(Server server)
  {
    this.server = server;
    this.diffTime = server.getServerInit().getDiffTime();
    this.status = Server.getSystemStatus().getStatus();
    this.sectionID = Server.getSystemStatus().getSectionID();
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
        SystemStatus systemStatus = judgeSystemStatus(
          System.currentTimeMillis() + 
          this.diffTime);
        if (systemStatus.getStatus() != -1)
        {
          Server.getSystemStatus().setSectionID(
            systemStatus.getSectionID());
          Server.getSystemStatus()
            .setStatus(systemStatus.getStatus());
          this.server.statusListener();
        }
      }
      else if (this.runStatus == 1)
      {
        continueTrade(System.currentTimeMillis() + this.diffTime);
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
    this.server.getServerInit();this.tradeTimeList.addAll(ServerInit.getTradeTimeList());
    if (this.tradeTimeList != null) {
      for (int i = 0; i < this.tradeTimeList.size(); i++)
      {
        TradeTime tt = (TradeTime)this.tradeTimeList.get(i);
        this.log.debug("TradeTime::" + tt);
      }
    }
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public int getRunStatus()
  {
    return this.runStatus;
  }
  
  public void setRunStatus(int runStatus)
  {
    this.runStatus = runStatus;
  }
  
  public void recoverRun()
  {
    setStatus(-1);
    Server.getSystemStatus().setRecoverTime(null);
    Server.getServerDAO().updateSystemRecoverTime(null);
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
  
  private SystemStatus judgeSystemStatus(long currentTime)
  {
    long lastEndTime = 0L;
    SystemStatus systemStatus = new SystemStatus(-1);
    
    int rows = this.tradeTimeList.size();
    for (int i = 0; i < rows; i++)
    {
      TradeTime tradeTime = (TradeTime)this.tradeTimeList.get(i);
      if (i == 0)
      {
        if (currentTime < tradeTime.getStartTimeMillis()) {
          break;
        }
      }
      else if ((currentTime >= lastEndTime) && 
        (currentTime < tradeTime.getStartTimeMillis()))
      {
        if (this.status != 6) {
          changeSystemStatus(systemStatus, 
            6, 
            tradeTime.getSectionID().intValue());
        }
        changeSectionID(systemStatus, tradeTime.getSectionID()
          .intValue());
        break;
      }
      if ((currentTime >= tradeTime.getStartTimeMillis()) && 
        (currentTime < tradeTime.getEndTimeMillis()))
      {
        if (this.status != 5) {
          changeSystemStatus(systemStatus, 
            5, tradeTime
            .getSectionID().intValue());
        }
        changeSectionID(systemStatus, tradeTime.getSectionID()
          .intValue());
        
        break;
      }
      if ((i == rows - 1) && 
        (currentTime >= tradeTime.getEndTimeMillis()))
      {
        if (this.status != 7) {
          changeSystemStatus(systemStatus, 
            7, 
            tradeTime.getSectionID().intValue());
        }
        changeSectionID(systemStatus, tradeTime.getSectionID()
          .intValue());
        break;
      }
      lastEndTime = tradeTime.getEndTimeMillis();
    }
    return systemStatus;
  }
  
  private void changeSystemStatus(SystemStatus systemStatus, int status, int sectionID)
  {
    systemStatus.setStatus(status);
    this.status = status;
    systemStatus.setSectionID(Integer.valueOf(sectionID));
    this.sectionID = Integer.valueOf(sectionID);
  }
  
  private void changeSectionID(SystemStatus systemStatus, int sectionID)
  {
    if ((this.sectionID == null) || (this.sectionID.intValue() != sectionID))
    {
      systemStatus.setSectionID(Integer.valueOf(sectionID));
      this.sectionID = Integer.valueOf(sectionID);
      

      systemStatus.setStatus(this.status);
    }
  }
  
  private void continueTrade(long currentTime)
  {
    String recoverTime = Server.getSystemStatus().getRecoverTime();
    if ((recoverTime == null) || (recoverTime.equals(""))) {
      return;
    }
    try
    {
      long recoverTimeMillis = this.server.getServerInit()
        .getTradeDateInterface().getRecoverDateByTime(recoverTime)
        .getTime();
      if (currentTime >= recoverTimeMillis) {
        recoverRun();
      }
    }
    catch (ParseException pe)
    {
      this.log.error("恢复暂停后的交易时解析日期失败，原因：" + pe.getMessage());
      System.out.println(DateUtil.getCurDateTime() + 
        "   恢复暂停后的交易时解析日期失败，原因：" + pe.getMessage());
    }
  }
}
