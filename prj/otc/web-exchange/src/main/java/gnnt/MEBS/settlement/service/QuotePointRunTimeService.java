package gnnt.MEBS.settlement.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.settlement.dao.QuotePointRunTimeDao;
import gnnt.MEBS.settlement.model.QuotePointRunTime;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("quotePointRunTimeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class QuotePointRunTimeService
  extends BaseService<QuotePointRunTime>
{
  private final transient Log logger = LogFactory.getLog(QuotePointRunTimeService.class);
  @Autowired
  @Qualifier("quotePointRunTimeDao")
  private QuotePointRunTimeDao quotePointRunTimeDao;
  
  public BaseDao getDao()
  {
    return this.quotePointRunTimeDao;
  }
  
  public QuotePointRunTime get(QuotePointRunTime quotePointRunTime)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.m_FirmId", "=", quotePointRunTime.getM_FirmId());
    qc.addCondition("primary.commodityId", "=", quotePointRunTime.getCommodityId());
    List<QuotePointRunTime> list = this.quotePointRunTimeDao.getList(qc, null);
    QuotePointRunTime returnQuotePointRunTime = null;
    if ((list != null) && (list.size() > 0))
    {
      returnQuotePointRunTime = new QuotePointRunTime();
      returnQuotePointRunTime = (QuotePointRunTime)list.get(0);
    }
    return returnQuotePointRunTime;
  }
  
  public List<QuotePointRunTime> getSpemcialMemberNo()
  {
    return this.quotePointRunTimeDao.getSpemcialMemberNo(null, null);
  }
}
