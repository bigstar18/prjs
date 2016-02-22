package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.QuotePointDao;
import gnnt.MEBS.commodity.model.QuotePoint;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("quotePointService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class, RuntimeException.class})
public class QuotePointService
  extends BaseService<QuotePoint>
{
  private final transient Log logger = LogFactory.getLog(QuotePointService.class);
  @Autowired
  @Qualifier("quotePointDao")
  private QuotePointDao quotePointDao;
  
  public BaseDao getDao()
  {
    return this.quotePointDao;
  }
  
  public QuotePoint get(QuotePoint quotePoint)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.m_firmId", "=", quotePoint.getM_firmId());
    qc.addCondition("primary.commodityId", "=", quotePoint.getCommodityId());
    List<QuotePoint> list = this.quotePointDao.getList(qc, null);
    QuotePoint returnQuotePoint = null;
    if ((list != null) && (list.size() > 0))
    {
      returnQuotePoint = new QuotePoint();
      returnQuotePoint = (QuotePoint)list.get(0);
    }
    else
    {
      returnQuotePoint = null;
    }
    return returnQuotePoint;
  }
  
  public int update(QuotePoint obj)
  {
    int num = 0;
    this.logger.debug("enter update");
    delete(obj);
    add(obj);
    num = 3;
    return num;
  }
}
