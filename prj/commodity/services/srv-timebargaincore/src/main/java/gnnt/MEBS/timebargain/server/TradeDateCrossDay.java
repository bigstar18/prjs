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
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeDateCrossDay
  implements TradeDate
{
  private final Log log = LogFactory.getLog(getClass());
  private ServerDAO serverDAO = (ServerDAO)DAOBeanFactory.getBean("serverDAO");
  
  public Date calClearDate()
  {
    Date localDate1 = this.serverDAO.getCurDbDate();
    SystemStatus localSystemStatus = this.serverDAO.getSystemStatus();
    if ((DateUtil.formatDate(localSystemStatus.getTradeDate(), "yyyy-MM-dd").compareTo(DateUtil.formatDate(localDate1, "yyyy-MM-dd")) > 0) || ((DateUtil.formatDate(localSystemStatus.getTradeDate(), "yyyy-MM-dd").compareTo(DateUtil.formatDate(localDate1, "yyyy-MM-dd")) == 0) && (localSystemStatus.getStatus() != 3))) {
      return localSystemStatus.getTradeDate();
    }
    String str1 = DateUtil.formatDate(localDate1, "HH:mm:ss");
    Date localDate2;
    if (str1.compareTo(getLastSectionEndTime()) < 0)
    {
      Date localDate3 = this.serverDAO.getLastClearDate();
      String str2 = DateUtil.formatDate(localDate3, "yyyy-MM-dd");
      if (DateUtil.formatDate(localDate1, "yyyy-MM-dd").compareTo(str2) == 0) {
        localDate2 = DateUtil.GoDate(localDate1, 1);
      } else {
        localDate2 = localDate1;
      }
    }
    else
    {
      localDate2 = DateUtil.GoDate(localDate1, 1);
    }
    return calValidClearDate(localDate2, 1);
  }
  
  private String getLastSectionEndTime()
  {
    String str = null;
    List localList = this.serverDAO.getTradeTimes();
    int i = localList.size();
    if ((localList != null) && (i > 0))
    {
      TradeTime localTradeTime = (TradeTime)localList.get(i - 1);
      str = localTradeTime.getEndTime();
    }
    return str;
  }
  
  private Date calValidClearDate(Date paramDate, int paramInt)
  {
    NotTradeDay localNotTradeDay = this.serverDAO.getNotTradeDay();
    while (!checkTradeDay(localNotTradeDay, paramDate)) {
      paramDate = DateUtil.GoDate(paramDate, paramInt);
    }
    return paramDate;
  }
  
  private boolean checkTradeDay(NotTradeDay paramNotTradeDay, Date paramDate)
  {
    if (paramNotTradeDay != null)
    {
      List localList1 = paramNotTradeDay.getWeekList();
      int i = DateUtil.getWeekDay(paramDate);
      if (localList1.contains(String.valueOf(i))) {
        return false;
      }
      List localList2 = paramNotTradeDay.getDayList();
      if (localList2.contains(DateUtil.convertDateToString(paramDate))) {
        return false;
      }
    }
    return true;
  }
  
  public List getTradeTimes(Date paramDate)
  {
    Date localDate1 = paramDate;
    String str1 = DateUtil.formatDate(localDate1, "yyyy-MM-dd");
    Date localDate2 = calValidClearDate(DateUtil.GoDate(localDate1, -1), -1);
    String str2 = DateUtil.formatDate(localDate2, "yyyy-MM-dd");
    String str3 = DateUtil.formatDate(DateUtil.GoDate(localDate2, 1), "yyyy-MM-dd");
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    String str4 = null;
    Map localMap = this.serverDAO.getDaySectionMap();
    List localList = this.serverDAO.getTradeTimes();
    for (int j = 0; j < localList.size(); j++)
    {
      TradeTime localTradeTime = (TradeTime)localList.get(j);
      if (localTradeTime.getEndTime().compareTo(localTradeTime.getStartTime()) < 0)
      {
        setTradeTimeMillis(localTradeTime, str2, str3);
        i = 1;
      }
      else
      {
        if ((str4 != null) && (localTradeTime.getStartTime().compareTo(str4) < 0)) {
          i = 1;
        }
        if (i != 0) {
          setTradeTimeMillis(localTradeTime, str1, str1);
        } else {
          setTradeTimeMillis(localTradeTime, str2, str2);
        }
      }
      str4 = localTradeTime.getEndTime();
      setTradeTimeStatus(localTradeTime, localDate1, localMap);
      this.serverDAO.updateTradeSectionDateStatus(localTradeTime);
      if (localTradeTime.getStatus().shortValue() == 1) {
        localArrayList.add(localTradeTime);
      }
    }
    return localArrayList;
  }
  
  private void setTradeTimeMillis(TradeTime paramTradeTime, String paramString1, String paramString2)
  {
    try
    {
      if (paramTradeTime.getGatherBid().shortValue() == 1)
      {
        paramTradeTime.setBidStartTimeMillis(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", paramString1 + " " + paramTradeTime.getBidStartTime()).getTime());
        paramTradeTime.setBidEndTimeMillis(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", paramString1 + " " + paramTradeTime.getBidEndTime()).getTime());
        paramTradeTime.setBidStartDate(paramString1);
        paramTradeTime.setBidEndDate(paramString2);
      }
      paramTradeTime.setStartTimeMillis(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", paramString1 + " " + paramTradeTime.getStartTime()).getTime());
      paramTradeTime.setEndTimeMillis(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", paramString2 + " " + paramTradeTime.getEndTime()).getTime());
      paramTradeTime.setStartDate(paramString1);
      paramTradeTime.setEndDate(paramString2);
    }
    catch (ParseException localParseException)
    {
      this.log.error("转换交易节信息时解析日期失败，原因：" + localParseException.getMessage());
      System.out.println(DateUtil.getCurDateTime() + "   转换交易节信息时解析日期失败，原因：" + localParseException.getMessage());
    }
  }
  
  private void setTradeTimeStatus(TradeTime paramTradeTime, Date paramDate, Map paramMap)
  {
    Map localMap = (Map)paramMap.get(Integer.valueOf(DateUtil.getWeekDay(paramDate)));
    if (localMap == null)
    {
      paramTradeTime.setStatus(Short.valueOf((short)0));
    }
    else
    {
      Short localShort = (Short)localMap.get(paramTradeTime.getSectionID());
      if (localShort == null)
      {
        paramTradeTime.setStatus(Short.valueOf((short)0));
      }
      else
      {
        int i = localShort.shortValue();
        if (i == 0) {
          paramTradeTime.setStatus(Short.valueOf((short)1));
        } else {
          paramTradeTime.setStatus(Short.valueOf((short)0));
        }
      }
    }
  }
  
  public Date getRecoverDateByTime(String paramString)
    throws ParseException
  {
    Object localObject = null;
    SystemStatus localSystemStatus = this.serverDAO.getSystemStatus();
    Date localDate1 = localSystemStatus.getTradeDate();
    String str1 = DateUtil.formatDate(localDate1, "yyyy-MM-dd");
    Date localDate2 = calValidClearDate(DateUtil.GoDate(localDate1, -1), -1);
    String str2 = DateUtil.formatDate(localDate2, "yyyy-MM-dd");
    String str3 = DateUtil.formatDate(DateUtil.GoDate(localDate2, 1), "yyyy-MM-dd");
    int i = 0;
    String str4 = null;
    List localList = this.serverDAO.getTradeTimes();
    for (int j = 0; j < localList.size(); j++)
    {
      TradeTime localTradeTime = (TradeTime)localList.get(j);
      if (localTradeTime.getEndTime().compareTo(localTradeTime.getStartTime()) < 0)
      {
        i = 1;
        if (paramString.compareTo(localTradeTime.getStartTime()) >= 0)
        {
          localObject = str2;
          break;
        }
        if (paramString.compareTo(localTradeTime.getEndTime()) < 0)
        {
          localObject = str3;
          break;
        }
      }
      else
      {
        if ((str4 != null) && (localTradeTime.getStartTime().compareTo(str4) < 0)) {
          i = 1;
        }
        if (i != 0)
        {
          localObject = str1;
          break;
        }
        if (paramString.compareTo(localTradeTime.getStartTime()) >= 0)
        {
          localObject = str2;
          break;
        }
      }
      str4 = localTradeTime.getEndTime();
    }
    if (localObject == null) {
      localObject = str1;
    }
    return DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", localObject + " " + paramString);
  }
}
