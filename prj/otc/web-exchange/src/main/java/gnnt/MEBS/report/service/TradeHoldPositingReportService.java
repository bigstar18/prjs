package gnnt.MEBS.report.service;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.report.dao.TradeHoldPositingReportDao;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class TradeHoldPositingReportService
{
  private TradeHoldPositingReportDao tradeHoldDao;
  
  public TradeHoldPositingReportDao getTradeHoldDao()
  {
    return this.tradeHoldDao;
  }
  
  public void setTradeHoldDao(TradeHoldPositingReportDao tradeHoldDao)
  {
    this.tradeHoldDao = tradeHoldDao;
  }
  
  public List getCustomerHoldList(QueryConditions qc)
  {
    return this.tradeHoldDao.getCustomerHoldData(qc);
  }
  
  public List getMemberHOldList(QueryConditions qc)
  {
    return this.tradeHoldDao.getMemberHoldData(qc);
  }
}
