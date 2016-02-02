package gnnt.MEBS.timebargain.server.delay;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.DelayDAO;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.model.DelayStatus;
import gnnt.MEBS.timebargain.server.model.DelayTradeTime;
import gnnt.MEBS.timebargain.server.util.DateUtil;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DelayTradeTimeTask
  extends Thread
{
  private final Log log = LogFactory.getLog(getClass());
  private Server server;
  private DelayDeal delayDeal;
  private DelayDAO delayDAO;
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
    this.delayDeal = paramServer.getDelayDeal();
    this.diffTime = paramServer.getServerInit().getDiffTime();
    this.status = this.delayDeal.getDelayStatus().getStatus();
    this.sectionID = this.delayDeal.getDelayStatus().getSectionID();
    this.delayDAO = ((DelayDAO)DAOBeanFactory.getBean("delayDAO"));
    refreshTradeTime();
    this.log.debug("delayStatus:" + this.status);
    this.log.debug("delaySectionID:" + this.sectionID);
    this.log.debug("delayDiffTime:" + this.diffTime);
  }
  
  public void run()
  {
    while (!this.threadEnd)
    {
      if (this.runStatus == 0)
      {
        DelayStatus localDelayStatus = judgeDelayStatus(getDBTime());
        if (localDelayStatus.getStatus() != -1)
        {
          this.delayDeal.getDelayStatus().setSectionID(localDelayStatus.getSectionID());
          this.delayDeal.getDelayStatus().setStatus(localDelayStatus.getStatus());
          this.delayDeal.statusListener();
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
    this.tradeTimeList.addAll(getTradeTimes(this.server.getServerInit().getClearDate()));
    if ((this.tradeTimeList == null) || (this.tradeTimeList.size() == 0))
    {
      this.delayDeal.getDelayStatus().setSectionID(null);
      this.delayDeal.getDelayStatus().setStatus(5);
      this.delayDeal.getDelayStatus().setRecoverTime(null);
      this.delayDeal.getDelayStatus().setNote(null);
      this.delayDeal.statusListener();
    }
    if (this.tradeTimeList != null)
    {
      this.log.debug("DelayTradeTime.date:" + this.server.getServerInit().getClearDate());
      for (int i = 0; i < this.tradeTimeList.size(); i++)
      {
        DelayTradeTime localDelayTradeTime = (DelayTradeTime)this.tradeTimeList.get(i);
        this.log.debug("DelayTradeTime:" + localDelayTradeTime);
      }
    }
  }
  
  private List getTradeTimes(Date paramDate)
  {
    String str = DateUtil.formatDate(paramDate, "yyyy-MM-dd");
    ArrayList localArrayList = new ArrayList();
    List localList = this.delayDAO.getDelayTradeTimes();
    for (int i = 0; i < localList.size(); i++)
    {
      DelayTradeTime localDelayTradeTime = (DelayTradeTime)localList.get(i);
      try
      {
        localDelayTradeTime.setStartTimeMillis(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", str + " " + localDelayTradeTime.getStartTime()).getTime());
        localDelayTradeTime.setEndTimeMillis(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", str + " " + localDelayTradeTime.getEndTime()).getTime());
      }
      catch (ParseException localParseException)
      {
        this.log.error("装载延期交易节信息时解析日期失败，原因：" + localParseException.getMessage());
        System.out.println(DateUtil.getCurDateTime() + "   装载延期交易节信息时解析日期失败，原因：" + localParseException.getMessage());
      }
      if (localDelayTradeTime.getStatus().shortValue() == 1) {
        localArrayList.add(localDelayTradeTime);
      }
    }
    return localArrayList;
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
    if ((this.tradeTimeList == null) || (this.tradeTimeList.size() == 0)) {
      return;
    }
    setStatus(-1);
    this.delayDeal.getDelayStatus().setRecoverTime(null);
    this.delayDAO.updateDelayRecoverTime(null);
    setRunStatus(0);
  }
  
  public void close()
  {
    this.log.info("正在关闭延期交易节任务线程！");
    this.threadEnd = true;
    try
    {
      interrupt();
    }
    catch (Exception localException) {}
    this.log.info("成功关闭延期交易节任务线程！");
  }
  
  private DelayStatus judgeDelayStatus(long paramLong)
  {
    DelayStatus localDelayStatus = new DelayStatus(-1);
    int i = this.tradeTimeList.size();
    for (int j = 0; j < i; j++)
    {
      DelayTradeTime localDelayTradeTime = (DelayTradeTime)this.tradeTimeList.get(j);
      if (j == 0)
      {
        if (paramLong < localDelayTradeTime.getStartTimeMillis()) {
          break;
        }
        if (paramLong > localDelayTradeTime.getEndTimeMillis()) {
          this.lastEndTime = localDelayTradeTime.getEndTimeMillis();
        }
      }
      else if ((paramLong >= this.lastEndTime) && (paramLong < localDelayTradeTime.getStartTimeMillis()))
      {
        if (this.status != 2) {
          changeDelayStatus(localDelayStatus, 2, localDelayTradeTime.getSectionID().intValue());
        }
        changeSectionID(localDelayStatus, localDelayTradeTime.getSectionID().intValue());
        break;
      }
      if ((paramLong >= localDelayTradeTime.getStartTimeMillis()) && (paramLong < localDelayTradeTime.getEndTimeMillis()))
      {
        if (localDelayTradeTime.getType().shortValue() == 0)
        {
          if (this.status != 1)
          {
            changeDelayStatus(localDelayStatus, 1, localDelayTradeTime.getSectionID().intValue());
            this.lastEndTime = localDelayTradeTime.getEndTimeMillis();
          }
        }
        else if ((localDelayTradeTime.getType().shortValue() == 1) && (this.status != 3))
        {
          changeDelayStatus(localDelayStatus, 3, localDelayTradeTime.getSectionID().intValue());
          this.lastEndTime = localDelayTradeTime.getEndTimeMillis();
        }
        changeSectionID(localDelayStatus, localDelayTradeTime.getSectionID().intValue());
        if ((this.sectionID != null) && (localDelayTradeTime.getSectionID().intValue() == this.sectionID.intValue())) {
          break;
        }
        this.lastEndTime = localDelayTradeTime.getEndTimeMillis();
        break;
      }
      if ((j == i - 1) && (paramLong >= localDelayTradeTime.getEndTimeMillis()))
      {
        if (this.status != 5) {
          changeDelayStatus(localDelayStatus, 5, localDelayTradeTime.getSectionID().intValue());
        }
        changeSectionID(localDelayStatus, localDelayTradeTime.getSectionID().intValue());
        break;
      }
    }
    return localDelayStatus;
  }
  
  private void changeDelayStatus(DelayStatus paramDelayStatus, int paramInt1, int paramInt2)
  {
    paramDelayStatus.setStatus(paramInt1);
    this.status = paramInt1;
    paramDelayStatus.setSectionID(Integer.valueOf(paramInt2));
    this.sectionID = Integer.valueOf(paramInt2);
  }
  
  private void changeSectionID(DelayStatus paramDelayStatus, int paramInt)
  {
    if ((this.sectionID == null) || (this.sectionID.intValue() != paramInt))
    {
      paramDelayStatus.setSectionID(Integer.valueOf(paramInt));
      this.sectionID = Integer.valueOf(paramInt);
      paramDelayStatus.setStatus(this.status);
    }
  }
  
  private void continueTrade(long paramLong)
  {
    String str1 = this.delayDeal.getDelayStatus().getRecoverTime();
    if ((str1 == null) || (str1.equals(""))) {
      return;
    }
    try
    {
      String str2 = DateUtil.formatDate(this.server.getServerInit().getClearDate(), "yyyy-MM-dd");
      long l = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", str2 + " " + str1).getTime();
      if (paramLong >= l) {
        recoverRun();
      }
    }
    catch (ParseException localParseException)
    {
      this.log.error("延期交易恢复暂停后的交易时解析日期失败，原因：" + localParseException.getMessage());
      System.out.println(DateUtil.getCurDateTime() + "   延期交易恢复暂停后的交易时解析日期失败，原因：" + localParseException.getMessage());
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
