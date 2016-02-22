package gnnt.MEBS.trade.service;

import gnnt.MEBS.base.copy.CopyObjectParamUtil;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.trade.dao.DaysectionDao;
import gnnt.MEBS.trade.dao.MarketDao;
import gnnt.MEBS.trade.dao.NotTradeDayDao;
import gnnt.MEBS.trade.model.DaySection;
import gnnt.MEBS.trade.model.Market;
import gnnt.MEBS.trade.model.NotTradeDay;
import gnnt.MEBS.trade.model.vo.NotTradeDayVO;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("notTradeDayService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class NotTradeDayService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(NotTradeDayService.class);
  @Autowired
  @Qualifier("notTradeDayDao")
  private NotTradeDayDao notTradeDayDao;
  @Autowired
  @Qualifier("daysectionDao")
  private DaysectionDao daysectionDao;
  @Autowired
  @Qualifier("marketDao")
  private MarketDao marketDao;
  
  public BaseDao getDao()
  {
    return this.notTradeDayDao;
  }
  
  public NotTradeDayVO getNotTradeDayVO(QueryConditions conditions, PageInfo pageInfo)
  {
    NotTradeDayVO notTradeDayVO = new NotTradeDayVO();
    NotTradeDay notTradeDay = new NotTradeDay();
    if ((this.notTradeDayDao.getList(conditions, pageInfo) != null) && (this.notTradeDayDao.getList(conditions, pageInfo).size() > 0)) {
      notTradeDay = (NotTradeDay)this.notTradeDayDao.getList(conditions, pageInfo).get(0);
    }
    Market market = new Market();
    if ((this.marketDao.getList(conditions, pageInfo) != null) && (this.marketDao.getList(conditions, pageInfo).size() > 0)) {
      market = (Market)this.marketDao.getList(conditions, pageInfo).get(0);
    }
    CopyObjectParamUtil.bindData(notTradeDay, notTradeDayVO);
    notTradeDayVO.setTradeTimeType(market.getTradeTimeType().intValue());
    if ((notTradeDay.getWeek() != null) && (notTradeDay.getWeek().length() > 0))
    {
      String[] weeks = notTradeDay.getWeek().split(",");
      notTradeDayVO.setWeeks(weeks);
    }
    return notTradeDayVO;
  }
  
  public List getNotTradelist(String yAm)
  {
    String sql = "select * from (select distinct(to_char(t.cleardate,'dd')) d from t_a_market_h t where to_char(t.cleardate,'yyyy-MM')='" + yAm + "' ) a order by d";
    return this.notTradeDayDao.querySql(sql);
  }
  
  public int updateDaySection(Map mapWeek)
  {
    int num = 0;
    String sql = "";
    for (int i = 1; i < 8; i++)
    {
      String ri = i;
      String[] weeks = (String[])mapWeek.get(ri);
      if (weeks != null)
      {
        String sectionIDs = "";
        for (int j = 0; j < weeks.length; j++)
        {
          String sectionID = weeks[j];
          if (j != weeks.length - 1) {
            sectionIDs = sectionIDs + sectionID + ",";
          } else {
            sectionIDs = sectionIDs + sectionID;
          }
          DaySection daySection = new DaySection();
          daySection.setStatus(Integer.valueOf(0));
          daySection.setWeekDay(Integer.valueOf(Integer.parseInt(ri)));
          daySection.setSectionId(Long.valueOf(Long.parseLong(sectionID)));
          this.notTradeDayDao.updateDaySectionOther(daySection);
        }
        List<Clone> list = this.notTradeDayDao.queryForList(ri, sectionIDs);
        if ((list != null) && (list.size() > 0)) {
          for (int k = 0; k < list.size(); k++)
          {
            String sectionIDN = ((DaySection)list.get(k)).getSectionId();
            DaySection daySection = new DaySection();
            daySection.setStatus(Integer.valueOf(1));
            daySection.setWeekDay(Integer.valueOf(Integer.parseInt(ri)));
            daySection.setSectionId(Long.valueOf(Long.parseLong(sectionIDN)));
            this.notTradeDayDao.updateDaySectionOther(daySection);
          }
        }
      }
      else
      {
        DaySection daySection = new DaySection();
        daySection.setStatus(Integer.valueOf(1));
        daySection.setWeekDay(Integer.valueOf(Integer.parseInt(ri)));
        this.notTradeDayDao.updateDaySectionNo(daySection);
      }
    }
    num = 3;
    return num;
  }
}
