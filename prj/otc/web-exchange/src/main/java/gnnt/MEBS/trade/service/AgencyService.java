package gnnt.MEBS.trade.service;

import gnnt.MEBS.base.copy.CopyObjectParamUtil;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.DateUtil;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.trade.dao.DaysectionDao;
import gnnt.MEBS.trade.dao.MarketDao;
import gnnt.MEBS.trade.dao.NotTradeDayDao;
import gnnt.MEBS.trade.dao.SystemStatusDao;
import gnnt.MEBS.trade.model.DaySection;
import gnnt.MEBS.trade.model.Market;
import gnnt.MEBS.trade.model.NotTradeDay;
import gnnt.MEBS.trade.model.SystemStatus;
import gnnt.MEBS.trade.model.vo.TradeManageVO;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("agencyService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class AgencyService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(AgencyService.class);
  @Autowired
  @Qualifier("systemStatusDao")
  private SystemStatusDao systemStatusDao;
  @Autowired
  @Qualifier("marketDao")
  private MarketDao marketDao;
  @Autowired
  @Qualifier("daysectionDao")
  private DaysectionDao daysectionDao;
  @Autowired
  @Qualifier("notTradeDayDao")
  private NotTradeDayDao notTradeDayDao;
  
  public BaseDao getDao()
  {
    return this.systemStatusDao;
  }
  
  public TradeManageVO getTradeManageVO(QueryConditions conditions, PageInfo pageInfo)
  {
    TradeManageVO tradeManageVO = new TradeManageVO();
    SystemStatus systemStatus = new SystemStatus();
    Market market = new Market();
    if (this.marketDao.getList(conditions, pageInfo).size() > 0) {
      market = (Market)this.marketDao.getList(conditions, pageInfo).get(0);
    }
    if (this.systemStatusDao.getList(conditions, pageInfo).size() > 0) {
      systemStatus = (SystemStatus)this.systemStatusDao.getList(conditions, pageInfo).get(0);
    }
    CopyObjectParamUtil.bindData(systemStatus, tradeManageVO);
    tradeManageVO.setRunMode(market.getRunMode().intValue());
    tradeManageVO.setSysDate(this.systemStatusDao.getSysdate());
    return tradeManageVO;
  }
  
  public String getSysdate()
  {
    return this.systemStatusDao.getSysdate();
  }
  
  public Date getSysdateSimple()
  {
    return this.systemStatusDao.getSysdateSimple();
  }
  
  public boolean checkTradeDay(Date date)
  {
    NotTradeDay notTradeDay = (NotTradeDay)this.notTradeDayDao.getList(null, null).get(0);
    return checkTradeDay(notTradeDay, date);
  }
  
  private boolean checkTradeDay(NotTradeDay notTradeDay, Date date)
  {
    int week = DateUtil.getWeekDay(date);
    if (notTradeDay != null)
    {
      String weekStr = notTradeDay.getWeek();
      if (weekStr != null) {
        if (weekStr.contains(week)) {
          return false;
        }
      }
      String dayStr = notTradeDay.getDay();
      if (dayStr != null) {
        if (dayStr.contains(DateUtil.convertDateToString(date))) {
          return false;
        }
      }
    }
    List<DaySection> daySectionList = this.daysectionDao.getList(new QueryConditions("primary.weekDay", "=", Integer.valueOf(week)), null);
    if ((daySectionList == null) || (daySectionList.size() == 0)) {
      return false;
    }
    int validTradeTimeSecNum = 0;
    for (DaySection daySection : daySectionList) {
      if (daySection.getStatus().intValue() == 0) {
        validTradeTimeSecNum++;
      }
    }
    if (validTradeTimeSecNum == 0) {
      return false;
    }
    return true;
  }
}
