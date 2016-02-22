package gnnt.MEBS.trade.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.trade.model.NotTradeDay;
import gnnt.MEBS.trade.model.SystemStatus;
import gnnt.MEBS.trade.model.vo.BarginCalendarVO;
import gnnt.MEBS.trade.model.vo.DaySectionVO;
import gnnt.MEBS.trade.model.vo.NotTradeDayVO;
import gnnt.MEBS.trade.model.vo.TradeManageVO;
import gnnt.MEBS.trade.service.AgencyService;
import gnnt.MEBS.trade.service.DaysectionService;
import gnnt.MEBS.trade.service.NotTradeDayService;
import gnnt.MEBS.trade.service.TradeTimeService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class NotTradeDayAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(NotTradeDayAction.class);
  @Autowired
  @Qualifier("notTradeDayService")
  private NotTradeDayService notTradeDayService;
  @Autowired
  @Qualifier("daysectionService")
  private DaysectionService daysectionService;
  @Autowired
  @Qualifier("tradeTimeService")
  private TradeTimeService tradeTimeService;
  @Autowired
  @Qualifier("agencyService")
  private AgencyService agencyService;
  private String[] days;
  private Map bcs = new HashMap();
  private DaySectionVO daySectionVO = new DaySectionVO();
  private NotTradeDayVO notTradeDayVO;
  private BarginCalendarVO barginCalendarVO;
  
  public BarginCalendarVO getBarginCalendarVO()
  {
    return this.barginCalendarVO;
  }
  
  public void setBarginCalendarVO(BarginCalendarVO barginCalendarVO)
  {
    this.barginCalendarVO = barginCalendarVO;
  }
  
  public NotTradeDayVO getNotTradeDayVO()
  {
    return this.notTradeDayVO;
  }
  
  public void setNotTradeDayVO(NotTradeDayVO notTradeDayVO)
  {
    this.notTradeDayVO = notTradeDayVO;
  }
  
  public String[] getDays()
  {
    return this.days;
  }
  
  public Map getBcs()
  {
    return this.bcs;
  }
  
  public DaySectionVO getDaySectionVO()
  {
    return this.daySectionVO;
  }
  
  public InService getService()
  {
    return this.notTradeDayService;
  }
  
  public String showNotTradeDay()
  {
    Date date = this.agencyService.getTradeManageVO(null, null).getTradeDate();
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    int dayForWeek = 0;
    if (c.get(7) == 1) {
      dayForWeek = 7;
    } else {
      dayForWeek = c.get(7) - 1;
    }
    this.request.setAttribute("dayForWeek", Integer.valueOf(dayForWeek));
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String dateForYear = simpleDateFormat.format(date);
    this.request.setAttribute("dateForYear", dateForYear);
    int status = this.agencyService.getTradeManageVO(null, null).getStatus().intValue();
    this.request.getSession().setAttribute("statusForNotTrade", Integer.valueOf(status));
    this.notTradeDayVO = this.notTradeDayService.getNotTradeDayVO(null, null);
    if ((status == 3) || (status == 2))
    {
      String nextDateForYear = "";
      String nextTradeDay = "";
      for (int j = 1; j < 365; j++)
      {
        c.add(5, 1);
        nextDateForYear = nextDateForYear + simpleDateFormat.format(c.getTime()) + ",";
        if (!(this.notTradeDayVO.getDay() == null ? "" : this.notTradeDayVO.getDay()).contains(simpleDateFormat.format(c.getTime()))) {
          if (!(this.notTradeDayVO.getWeek() == null ? "" : this.notTradeDayVO.getWeek()).contains(c.get(7)))
          {
            nextTradeDay = simpleDateFormat.format(c.getTime());
            break;
          }
        }
      }
      Calendar calendar = Calendar.getInstance();
      try
      {
        calendar.setTime(simpleDateFormat.parse(nextTradeDay));
        this.request.setAttribute("nextTradeDayWeek", Integer.valueOf(calendar.get(7)));
      }
      catch (ParseException e)
      {
        e.printStackTrace();
      }
      this.request.setAttribute("nextTradeDay", nextTradeDay);
      if ((nextDateForYear != null) && (!"".equals(nextDateForYear))) {
        nextDateForYear = nextDateForYear.substring(0, nextDateForYear.length() - 11);
      }
      if ((nextDateForYear != null) && (nextDateForYear.length() > 1)) {
        nextDateForYear = nextDateForYear.substring(0, nextDateForYear.length() - 1);
      }
      this.request.setAttribute("nextDateForYear", nextDateForYear);
      if ((nextDateForYear != null) && (!"".equals(nextDateForYear)))
      {
        String[] nextDateForWeeks = nextDateForYear.split(",");
        String nextDateWeeks = "";
        for (String nextDateForWeek : nextDateForWeeks) {
          try
          {
            Calendar calendar2 = Calendar.getInstance();
            if (!"".equals(nextDateForWeek))
            {
              calendar2.setTime(simpleDateFormat.parse(nextDateForWeek));
              nextDateWeeks = nextDateWeeks + calendar2.get(7) + ",";
            }
          }
          catch (ParseException e)
          {
            e.printStackTrace();
          }
        }
        if (nextDateWeeks.length() > 0) {
          nextDateWeeks = nextDateWeeks.substring(0, nextDateWeeks.length() - 1);
        }
        this.request.setAttribute("nextDateWeeks", nextDateWeeks);
      }
    }
    Long id = this.notTradeDayVO.getId();
    if (id.longValue() == 0L) {
      id = Long.valueOf(1L);
    }
    this.obj = this.notTradeDayService.getById(id);
    
    String dbDate = this.agencyService.getSysdate();
    this.request.setAttribute("dbDate", dbDate.substring(0, 10));
    
    return getReturnValue();
  }
  
  public String editDaySection()
  {
    this.logger.debug("------enter  editDaySection--------");
    PageInfo pageInfo = new PageInfo(1, 10000, "sectionid", new Boolean(false).booleanValue());
    this.daySectionVO.setDaySectionList(this.daysectionService.getList(null, pageInfo));
    this.obj = this.daySectionVO;
    this.request.setAttribute("daySectionList", this.daysectionService.getList(null, pageInfo));
    this.request.setAttribute("tradeTimeList", this.tradeTimeService.getList(null, pageInfo));
    return getReturnValue();
  }
  
  public String updateNotTradeDay()
  {
    NotTradeDay notTradeDay = new NotTradeDay();
    String[] weeks = this.request.getParameterValues("weeks");
    notTradeDay.setDay(this.notTradeDayVO.getDay());
    Long id = this.notTradeDayVO.getId();
    if (id.longValue() == 0L) {
      id = Long.valueOf(1L);
    }
    notTradeDay.setId(id);
    StringBuffer sb = new StringBuffer();
    if ((weeks != null) && (weeks.length > 0)) {
      for (int k = 0; k < weeks.length; k++) {
        if (k != weeks.length - 1) {
          sb.append(weeks[k]).append(",");
        } else {
          sb.append(weeks[k]);
        }
      }
    }
    notTradeDay.setWeek(sb.toString());
    
    int resultValue = this.notTradeDayService.update(notTradeDay);
    this.obj = notTradeDay;
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String updateDaySection()
  {
    String[] weeks1 = this.request.getParameterValues("week1");
    String[] weeks2 = this.request.getParameterValues("week2");
    String[] weeks3 = this.request.getParameterValues("week3");
    String[] weeks4 = this.request.getParameterValues("week4");
    String[] weeks5 = this.request.getParameterValues("week5");
    String[] weeks6 = this.request.getParameterValues("week6");
    String[] weeks7 = this.request.getParameterValues("week7");
    
    Map mapWeek = new HashMap();
    for (int i = 1; i < 8; i++)
    {
      String ri = i;
      if ("1".equals(ri)) {
        mapWeek.put(ri, weeks1);
      }
      if ("2".equals(ri)) {
        mapWeek.put(ri, weeks2);
      }
      if ("3".equals(ri)) {
        mapWeek.put(ri, weeks3);
      }
      if ("4".equals(ri)) {
        mapWeek.put(ri, weeks4);
      }
      if ("5".equals(ri)) {
        mapWeek.put(ri, weeks5);
      }
      if ("6".equals(ri)) {
        mapWeek.put(ri, weeks6);
      }
      if ("7".equals(ri)) {
        mapWeek.put(ri, weeks7);
      }
    }
    int resultValue = this.notTradeDayService.updateDaySection(mapWeek);
    this.daySectionVO.setDaySectionList(this.daysectionService.getList(null, null));
    this.obj = this.daySectionVO;
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String check()
  {
    this.days = new String[42];
    for (int i = 0; i < 42; i++) {
      this.days[i] = "";
    }
    Calendar thisMonth = Calendar.getInstance();
    String dbDate = this.agencyService.getSysdate();
    Calendar nowdate = Calendar.getInstance();
    thisMonth.set(1, Integer.parseInt(dbDate.substring(0, 4)));
    nowdate.set(1, Integer.parseInt(dbDate.substring(0, 4)));
    thisMonth.set(5, 1);
    if ("0".equals(dbDate.substring(5, 6)))
    {
      thisMonth.set(2, Integer.parseInt(dbDate.substring(6, 7)) - 1);
      nowdate.set(2, Integer.parseInt(dbDate.substring(6, 7)) - 1);
    }
    else
    {
      thisMonth.set(2, Integer.parseInt(dbDate.substring(5, 7)) - 1);
      nowdate.set(2, Integer.parseInt(dbDate.substring(5, 7)) - 1);
    }
    String month = this.request.getParameter("month");
    String year = this.request.getParameter("year");
    this.request.setAttribute("returnYear", String.valueOf(thisMonth.get(1)));
    this.request.setAttribute("returnMonth", String.valueOf(thisMonth.get(2)));
    thisMonth.set(5, 1);
    if ((month != null) && (!month.equals("null"))) {
      thisMonth.set(2, Integer.parseInt(month));
    }
    if ((year != null) && (!year.equals("null"))) {
      thisMonth.set(1, Integer.parseInt(year));
    }
    year = String.valueOf(thisMonth.get(1));
    int m = thisMonth.get(2) + 1;
    month = String.valueOf(thisMonth.get(2));
    if (this.barginCalendarVO == null)
    {
      this.barginCalendarVO = new BarginCalendarVO();
      this.barginCalendarVO.setMonth(Integer.parseInt(month));
      this.barginCalendarVO.setYear(year);
    }
    String month1 = "";
    if (m < 10) {
      month1 = String.valueOf("0" + m);
    } else {
      month1 = String.valueOf(m);
    }
    String yAm = year + "-" + month1;
    thisMonth.setFirstDayOfWeek(1);
    thisMonth.set(5, 1);
    int firstIndex = thisMonth.get(7) - 1;
    int maxIndex = thisMonth.getActualMaximum(5);
    int w = firstIndex + 1;
    
    SystemStatus systemStatus = (SystemStatus)this.agencyService.getList(null, null).get(0);
    Date tradeDate = systemStatus.getTradeDate();
    for (int i = 0; i < maxIndex; i++)
    {
      this.days[(firstIndex + i)] = String.valueOf(i + 1);
      BarginCalendarVO barginCalendarVO = new BarginCalendarVO();
      barginCalendarVO.setWeek(w);
      w++;
      if (w > 7) {
        w = 1;
      }
      this.bcs.put(String.valueOf(i + 1), barginCalendarVO);
    }
    List notTradelist = this.notTradeDayService.getNotTradelist(yAm);
    if ((notTradelist != null) && (notTradelist.size() > 0)) {
      for (int i = 0; i < notTradelist.size(); i++)
      {
        String day = (String)notTradelist.get(i);
        String d = Integer.parseInt(day);
        BarginCalendarVO barginCalendarVO = (BarginCalendarVO)this.bcs.get(d);
        barginCalendarVO.setStatus(1);
        this.bcs.put(d, barginCalendarVO);
      }
    }
    Calendar tradeCalendar = Calendar.getInstance();
    tradeCalendar.setTime(tradeDate);
    if ((tradeCalendar.get(1) == Integer.parseInt(year)) && (tradeCalendar.get(2) + 1 == Integer.parseInt(month1)))
    {
      BarginCalendarVO barginCalendarVO2 = (BarginCalendarVO)this.bcs.get(tradeDate.getDate());
      

      barginCalendarVO2.setStatus(1);
    }
    NotTradeDay notTradeDay = null;
    list();
    if ((this.resultList != null) && (this.resultList.size() > 0))
    {
      notTradeDay = (NotTradeDay)this.resultList.get(0);
      String week = notTradeDay.getWeek();
      String day = notTradeDay.getDay();
      if (day != null)
      {
        String[] notDays = day.split(",");
        for (int i = 0; i < notDays.length; i++) {
          if ((notDays[i] != null) && (!"".equals(notDays[i])) && (notDays[i].indexOf(yAm + "-") >= 0))
          {
            String sd = notDays[i].replaceAll(yAm + "-", "");
            BarginCalendarVO barginCalendarVO = (BarginCalendarVO)this.bcs.get(Integer.parseInt(sd));
            barginCalendarVO.setStatus(-2);
            this.bcs.put(Integer.parseInt(sd), barginCalendarVO);
          }
        }
      }
      if (week != null)
      {
        String[] weeks = week.split(",");
        for (int i = 0; i < weeks.length; i++) {
          if ((weeks[i] != null) && (!"".equals(weeks[i])))
          {
            Set set = this.bcs.entrySet();
            Iterator e = set.iterator();
            while (e.hasNext())
            {
              Map.Entry me = (Map.Entry)e.next();
              BarginCalendarVO barginCalendarVO = (BarginCalendarVO)me.getValue();
              if ((barginCalendarVO.getWeek().intValue() == Integer.parseInt(weeks[i])) && (barginCalendarVO.getStatus().intValue() == 2)) {
                barginCalendarVO.setStatus(-2);
              }
            }
          }
        }
      }
    }
    String today = dbDate.substring(8, 10);
    nowdate.set(5, Integer.parseInt(today));
    int sign = -1;
    
    int sign1 = -1;
    if ((String.valueOf(nowdate.get(1)).equals(year)) && (String.valueOf(nowdate.get(2)).equals(month)))
    {
      sign = 1;
      sign1 = 1;
    }
    else if (nowdate.after(thisMonth))
    {
      sign = 2;
    }
    Set set = this.bcs.entrySet();
    Iterator e = set.iterator();
    
    Date endDate = systemStatus.getEndDate();
    Date nextTradeDate = systemStatus.getNextTradeDate();
    Date sysDateSimple = this.agencyService.getSysdateSimple();
    while (e.hasNext())
    {
      Map.Entry me = (Map.Entry)e.next();
      BarginCalendarVO barginCalendarVO = (BarginCalendarVO)me.getValue();
      String key = (String)me.getKey();
      if (((Integer.parseInt(key) < Integer.parseInt(today)) && (barginCalendarVO.getStatus().intValue() != 1) && (sign == 1)) || ((sign == 2) && (barginCalendarVO.getStatus().intValue() != 1))) {
        barginCalendarVO.setStatus(-1);
      }
      if (nextTradeDate != null) {
        if ((sign1 == 1) && (Integer.parseInt(key) == Integer.parseInt(today)))
        {
          Calendar cc = Calendar.getInstance();
          cc.setTime(sysDateSimple);
          cc.set(5, Integer.parseInt(today));
          if (cc.getTime().before(tradeDate)) {
            barginCalendarVO.setToday(true);
          } else if (cc.getTime().compareTo(tradeDate) == 0) {
            barginCalendarVO.setToday(true);
          } else if ((cc.getTime().compareTo(nextTradeDate) == 0) && (systemStatus.getStatus().intValue() == 3)) {
            barginCalendarVO.setToday(true);
          } else if ((systemStatus.getStatus().intValue() == 3) && (nextTradeDate.after(cc.getTime()))) {
            barginCalendarVO.setToday(true);
          }
        }
        else if ((sign1 == 1) && (Integer.parseInt(key) == Integer.parseInt(today) - 1))
        {
          Calendar cc1 = Calendar.getInstance();
          cc1.setTime(sysDateSimple);
          if (Integer.parseInt(key) == Integer.parseInt(today) - 1) {
            cc1.set(5, Integer.parseInt(today) - 1);
          }
          cc1.add(5, 1);
          if ((cc1.getTime().compareTo(endDate) == 0) && (systemStatus.getStatus().intValue() != 3) && (tradeDate.compareTo(endDate) != 0)) {
            barginCalendarVO.setToday(true);
          }
        }
      }
    }
    return getReturnValue();
  }
}
