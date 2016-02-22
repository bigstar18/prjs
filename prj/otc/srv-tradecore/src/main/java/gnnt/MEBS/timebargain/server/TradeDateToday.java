package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.model.NotTradeDay;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.TradeTime;
import gnnt.MEBS.timebargain.server.util.DateUtil;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeDateToday
  implements TradeDate
{
  private final Log log = LogFactory.getLog(getClass());
  private ServerDAO serverDAO;
  
  public TradeDateToday()
  {
    this.serverDAO = ((ServerDAO)DAOBeanFactory.getBean("serverDAO"));
  }
  
  public Date calClearDateByTradeDate(Date tradeDate)
  {
    return tradeDate;
  }
  
  public Date calNextTradeDate(Date curTradeDate)
  {
    return calValidTradeDate(DateUtil.GoDate(curTradeDate, 1), 1);
  }
  
  public Date calTradeDate()
  {
    Date dbDate = this.serverDAO.getCurDbDate();
    SystemStatus systemStatus = this.serverDAO.getSystemStatus();
    


    Date lastTradeDate = this.serverDAO.getLastTradeDate();
    if (lastTradeDate == null) {
      return systemStatus.getTradeDate();
    }
    Date tradeDate;
    if (Server.getInstance().getServerInit().isDayCalc()) {
      tradeDate = DateUtil.GoDate(lastTradeDate, 1);
    } else {
      return systemStatus.getTradeDate();
    }
    Date tradeDate = calValidTradeDate(tradeDate, 1);
    if (tradeDate.getTime() < dbDate.getTime())
    {
      tradeDate = dbDate;
      
      tradeDate = calValidTradeDate(tradeDate, 1);
    }
    return tradeDate;
  }
  
  public List<TradeTime> getTradeTimes(List<TradeTime> allTradeTimes)
  {
    List<TradeTime> result = new ArrayList();
    for (int i = 0; i < allTradeTimes.size(); i++)
    {
      TradeTime tradeTime = (TradeTime)allTradeTimes.get(i);
      if (tradeTime.getStatus().shortValue() == 1) {
        result.add(tradeTime);
      }
    }
    return result;
  }
  
  public List<TradeTime> getAllTradeTimesByTD(Date date)
  {
    String clearDate = DateUtil.formatDate(date, "yyyy-MM-dd");
    List<TradeTime> result = new ArrayList();
    List<TradeTime> tradeTimeList = this.serverDAO.getTradeTimes();
    Map daySectionMap = this.serverDAO.getDaySectionMap();
    for (int i = 0; i < tradeTimeList.size(); i++)
    {
      TradeTime tradeTime = (TradeTime)tradeTimeList.get(i);
      try
      {
        if (tradeTime.getGatherBid().shortValue() == 1)
        {
          tradeTime.setBidStartTimeMillis(
            DateUtil.convertStringToDate(
            "yyyy-MM-dd HH:mm:ss", 
            clearDate + " " + 
            tradeTime.getBidStartTime())
            .getTime());
          tradeTime.setBidEndTimeMillis(DateUtil.convertStringToDate(
            "yyyy-MM-dd HH:mm:ss", 
            clearDate + " " + tradeTime.getBidEndTime())
            .getTime());
          tradeTime.setBidStartDate(clearDate);
          tradeTime.setBidEndDate(clearDate);
        }
        tradeTime.setStartTimeMillis(DateUtil.convertStringToDate(
          "yyyy-MM-dd HH:mm:ss", 
          clearDate + " " + tradeTime.getStartTime()).getTime());
        tradeTime.setEndTimeMillis(DateUtil.convertStringToDate(
          "yyyy-MM-dd HH:mm:ss", 
          clearDate + " " + tradeTime.getEndTime()).getTime());
        tradeTime.setStartDate(clearDate);
        tradeTime.setEndDate(clearDate);
      }
      catch (ParseException pe)
      {
        this.log.error("装载交易节信息时解析日期失败，原因：" + pe.getMessage());
        System.out.println(DateUtil.getCurDateTime() + 
          "   装载交易节信息时解析日期失败，原因：" + pe.getMessage());
      }
      setTradeTimeStatus(tradeTime, date, daySectionMap);
      result.add(tradeTime);
    }
    return result;
  }
  
  private void setTradeTimeStatus(TradeTime tradeTime, Date tradeDate, Map daySectionMap)
  {
    Map sectionMap = 
      (Map)daySectionMap.get(Integer.valueOf(DateUtil.getWeekDay(tradeDate)));
    if (sectionMap == null)
    {
      tradeTime.setStatus(Short.valueOf((short)0));
    }
    else
    {
      Short statusValue = 
        (Short)sectionMap.get(tradeTime.getSectionID());
      if (statusValue == null)
      {
        tradeTime.setStatus(Short.valueOf((short)0));
      }
      else
      {
        short status = statusValue.shortValue();
        if (status == 0) {
          tradeTime.setStatus(Short.valueOf((short)1));
        } else {
          tradeTime.setStatus(Short.valueOf((short)0));
        }
      }
    }
  }
  
  public List getTradeTimes(Date date)
  {
    String clearDate = DateUtil.formatDate(date, "yyyy-MM-dd");
    List result = new ArrayList();
    List tradeTimeList = this.serverDAO.getTradeTimes();
    for (int i = 0; i < tradeTimeList.size(); i++)
    {
      TradeTime tradeTime = (TradeTime)tradeTimeList.get(i);
      try
      {
        if (tradeTime.getGatherBid().shortValue() == 1)
        {
          tradeTime.setBidStartTimeMillis(
            DateUtil.convertStringToDate(
            "yyyy-MM-dd HH:mm:ss", 
            clearDate + " " + 
            tradeTime.getBidStartTime())
            .getTime());
          tradeTime.setBidEndTimeMillis(DateUtil.convertStringToDate(
            "yyyy-MM-dd HH:mm:ss", 
            clearDate + " " + tradeTime.getBidEndTime())
            .getTime());
          tradeTime.setBidStartDate(clearDate);
          tradeTime.setBidEndDate(clearDate);
        }
        tradeTime.setStartTimeMillis(DateUtil.convertStringToDate(
          "yyyy-MM-dd HH:mm:ss", 
          clearDate + " " + tradeTime.getStartTime()).getTime());
        tradeTime.setEndTimeMillis(DateUtil.convertStringToDate(
          "yyyy-MM-dd HH:mm:ss", 
          clearDate + " " + tradeTime.getEndTime()).getTime());
        tradeTime.setStartDate(clearDate);
        tradeTime.setEndDate(clearDate);
      }
      catch (ParseException pe)
      {
        this.log.error("装载交易节信息时解析日期失败，原因：" + pe.getMessage());
        System.out.println(DateUtil.getCurDateTime() + 
          "   装载交易节信息时解析日期失败，原因：" + pe.getMessage());
      }
      this.serverDAO.updateTradeSectionDateStatus(tradeTime);
      if (tradeTime.getStatus().shortValue() == 1) {
        result.add(tradeTime);
      }
    }
    return result;
  }
  
  public Date getRecoverDateByTime(String recoverTime)
    throws ParseException
  {
    SystemStatus systemStatus = this.serverDAO.getSystemStatus();
    String strClearDate = DateUtil.formatDate(systemStatus.getClearDate(), 
      "yyyy-MM-dd");
    return DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", strClearDate + 
      " " + recoverTime);
  }
  
  private Date calValidTradeDate(Date tradeDate, int intervalDay)
  {
    NotTradeDay notTradeDay = this.serverDAO.getNotTradeDay();
    while (!checkTradeDay(notTradeDay, tradeDate)) {
      tradeDate = DateUtil.GoDate(tradeDate, intervalDay);
    }
    return tradeDate;
  }
  
  public boolean checkTradeDay(Date date)
  {
    NotTradeDay notTradeDay = this.serverDAO.getNotTradeDay();
    return checkTradeDay(notTradeDay, date);
  }
  
  private boolean checkTradeDay(NotTradeDay notTradeDay, Date date)
  {
    int week = DateUtil.getWeekDay(date);
    if (notTradeDay != null)
    {
      List weekList = notTradeDay.getWeekList();
      if (weekList.contains(String.valueOf(week))) {
        return false;
      }
      List dayList = notTradeDay.getDayList();
      if (dayList.contains(DateUtil.convertDateToString(date))) {
        return false;
      }
    }
    Map daySectionMap = this.serverDAO.getDaySectionMap();
    Map sectionMap = (Map)daySectionMap.get(Integer.valueOf(week));
    if (sectionMap == null) {
      return false;
    }
    int validTradeTimeSecNum = 0;
    for (Iterator i = sectionMap.values().iterator(); i.hasNext();)
    {
      Short statusValue = (Short)i.next();
      short status = statusValue.shortValue();
      if (status == 0) {
        validTradeTimeSecNum++;
      }
    }
    if (validTradeTimeSecNum == 0)
    {
      this.log.info("尽管日期" + DateUtil.formatDate(date, "yyyy-MM-dd") + 
        "不属于非交易日，但是此日期对应的有效交易节数量为0");
      return false;
    }
    return true;
  }
}
