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

public class TradeDateCrossDay
  implements TradeDate
{
  private final Log log = LogFactory.getLog(getClass());
  private ServerDAO serverDAO;
  
  public TradeDateCrossDay()
  {
    this.serverDAO = ((ServerDAO)DAOBeanFactory.getBean("serverDAO"));
  }
  
  public Date calClearDateByTradeDate(Date tradeDate)
  {
    return DateUtil.GoDate(tradeDate, getCrossDays());
  }
  
  public Date calNextTradeDate(Date curTradeDate)
  {
    Date nextTradeDate = calValidTradeDate(
      DateUtil.GoDate(curTradeDate, 1), 1);
    this.log.info("交易日 " + DateUtil.formatDate(curTradeDate, "yyyy-MM-dd") + 
      " 的下一交易日为 " + 
      DateUtil.formatDate(nextTradeDate, "yyyy-MM-dd"));
    return nextTradeDate;
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
  
  private int getCrossDays()
  {
    int crossDays = 0;
    String endTime = null;
    List<TradeTime> lst = this.serverDAO.getTradeTimes();
    for (int i = 0; i < lst.size(); i++)
    {
      TradeTime tradeTime = (TradeTime)lst.get(i);
      if ((endTime != null) && 
        (tradeTime.getStartTime().compareTo(endTime) < 0)) {
        crossDays++;
      }
      if (tradeTime.getEndTime().compareTo(tradeTime.getStartTime()) < 0) {
        crossDays++;
      }
      endTime = tradeTime.getEndTime();
    }
    return crossDays;
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
    Date tradeDate = date;
    
    String strTradeDate = DateUtil.formatDate(tradeDate, "yyyy-MM-dd");
    
    List<TradeTime> result = new ArrayList();
    int crossDays = 0;
    String endTime = null;
    Map daySectionMap = this.serverDAO.getDaySectionMap();
    List<TradeTime> tradeTimeList = this.serverDAO.getTradeTimes();
    for (int i = 0; i < tradeTimeList.size(); i++)
    {
      TradeTime tradeTime = (TradeTime)tradeTimeList.get(i);
      this.log.debug("tradeTime[" + i + "]" + tradeTime.toString());
      if (tradeTime.getEndTime().compareTo(tradeTime.getStartTime()) < 0)
      {
        if ((endTime != null) && 
          (tradeTime.getStartTime().compareTo(endTime) < 0)) {
          crossDays++;
        }
        this.log.debug("结束时间小于开始时间则跨天");
        setTradeTimeMillis(tradeTime, DateUtil.formatDate(
          DateUtil.GoDate(tradeDate, crossDays), "yyyy-MM-dd"), 
          DateUtil.formatDate(DateUtil.GoDate(tradeDate, crossDays + 1), 
          "yyyy-MM-dd"));
        crossDays++;
        this.log.debug("tradeTime[" + i + "]" + tradeTime.toString());
      }
      else
      {
        if ((endTime != null) && 
          (tradeTime.getStartTime().compareTo(endTime) < 0)) {
          crossDays++;
        }
        setTradeTimeMillis(tradeTime, DateUtil.formatDate(
          DateUtil.GoDate(tradeDate, crossDays), "yyyy-MM-dd"), 
          DateUtil.formatDate(DateUtil.GoDate(tradeDate, crossDays), 
          "yyyy-MM-dd"));
        
        this.log.debug("tradeTime[" + i + "]" + tradeTime.toString());
      }
      endTime = tradeTime.getEndTime();
      setTradeTimeStatus(tradeTime, tradeDate, daySectionMap);
      
      result.add(tradeTime);
    }
    return result;
  }
  
  private void setTradeTimeMillis(TradeTime tradeTime, String startDate, String endDate)
  {
    try
    {
      if (tradeTime.getGatherBid().shortValue() == 1)
      {
        tradeTime.setBidStartTimeMillis(DateUtil.convertStringToDate(
          "yyyy-MM-dd HH:mm:ss", 
          startDate + " " + tradeTime.getBidStartTime())
          .getTime());
        tradeTime.setBidEndTimeMillis(DateUtil.convertStringToDate(
          "yyyy-MM-dd HH:mm:ss", 
          startDate + " " + tradeTime.getBidEndTime()).getTime());
        tradeTime.setBidStartDate(startDate);
        tradeTime.setBidEndDate(endDate);
      }
      tradeTime.setStartTimeMillis(DateUtil.convertStringToDate(
        "yyyy-MM-dd HH:mm:ss", 
        startDate + " " + tradeTime.getStartTime()).getTime());
      tradeTime.setEndTimeMillis(DateUtil.convertStringToDate(
        "yyyy-MM-dd HH:mm:ss", 
        endDate + " " + tradeTime.getEndTime()).getTime());
      tradeTime.setStartDate(startDate);
      tradeTime.setEndDate(endDate);
    }
    catch (ParseException pe)
    {
      this.log.error("转换交易节信息时解析日期失败，原因：" + pe.getMessage());
      System.out.println(DateUtil.getCurDateTime() + 
        "   转换交易节信息时解析日期失败，原因：" + pe.getMessage());
    }
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
  
  public Date getRecoverDateByTime(String recoverTime)
    throws ParseException
  {
    SystemStatus systemStatus = this.serverDAO.getSystemStatus();
    
    Date tradeDate = systemStatus.getTradeDate();
    
    Date clearDate = systemStatus.getClearDate();
    

    String strTradeDate = DateUtil.formatDate(tradeDate, "yyyy-MM-dd");
    
    String strClearDate = DateUtil.formatDate(clearDate, "yyyy-MM-dd");
    

    List<TradeTime> tradeTimeList = ServerInit.getTradeTimeList();
    

    TradeTime lastTradeTime = null;
    if ((tradeTimeList != null) && (tradeTimeList.size() > 0)) {
      lastTradeTime = 
        (TradeTime)tradeTimeList.get(tradeTimeList.size() - 1);
    } else {
      throw new IllegalArgumentException(
        "getRecoverDateByTime  Fail tradeTimeList is Null");
    }
    String strRecoverDate = strTradeDate;
    if (recoverTime.compareTo(lastTradeTime.getEndTime()) < 0) {
      strRecoverDate = strClearDate;
    }
    return DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", 
      strRecoverDate + " " + recoverTime);
  }
}
