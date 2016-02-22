package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.MemberQuotePointDao;
import gnnt.MEBS.commodity.model.QuotePoint;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberQuotePointService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class, RuntimeException.class})
public class MemberQuotePointService
  extends SpecialSetService<QuotePoint>
{
  private final transient Log logger = LogFactory.getLog(MemberQuotePointService.class);
  @Autowired
  @Qualifier("memberQuotePointDao")
  private MemberQuotePointDao memberQuotePointDao;
  
  public QuotePoint get(QuotePoint quotePoint)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.m_firmId", "=", quotePoint.getM_firmId());
    qc.addCondition("primary.commodityId", "=", quotePoint.getCommodityId());
    List<QuotePoint> list = this.memberQuotePointDao.getList(qc, null);
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
  
  public BaseDao getDao()
  {
    return this.memberQuotePointDao;
  }
}
