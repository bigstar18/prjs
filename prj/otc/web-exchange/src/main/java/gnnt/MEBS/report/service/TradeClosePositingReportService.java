package gnnt.MEBS.report.service;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.report.dao.TradeClosePositingReportDao;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class TradeClosePositingReportService
{
  private TradeClosePositingReportDao tradeDao;
  
  public TradeClosePositingReportDao getTradeDao()
  {
    return this.tradeDao;
  }
  
  public void setTradeDao(TradeClosePositingReportDao tradeDao)
  {
    this.tradeDao = tradeDao;
  }
  
  public List getCustomerCloseList(QueryConditions qc)
  {
    return this.tradeDao.getCustomerCloseData(qc);
  }
  
  public List getMemberCloseList(QueryConditions qc)
  {
    return this.tradeDao.getMemberCloseData(qc);
  }
}
