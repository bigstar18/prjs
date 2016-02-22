package gnnt.MEBS.settlement.service;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import gnnt.MEBS.settlement.dao.QuotationDao;
import gnnt.MEBS.settlement.dao.QuotePointClearPriceDao;
import gnnt.MEBS.settlement.model.Quotation;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("quotationService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class QuotationService
  extends BaseService<Quotation>
{
  private final transient Log logger = LogFactory.getLog(QuotationService.class);
  @Autowired
  @Qualifier("quotationDao")
  private QuotationDao quotationDao;
  @Autowired
  @Qualifier("quotePointClearPriceDao")
  private QuotePointClearPriceDao quotePointClearPriceDao;
  
  public BaseDao getDao()
  {
    return this.quotationDao;
  }
  
  public Quotation get(Quotation quotation)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", quotation.getCommodityId());
    List<Quotation> list = this.quotationDao.getList(qc, null);
    if ((list != null) && (list.size() > 0))
    {
      quotation = new Quotation();
      quotation = (Quotation)list.get(0);
    }
    return quotation;
  }
  
  public int update(Quotation obj)
  {
    int num = 0;
    this.logger.debug("enter update");
    Clone objFor = copyObject(obj);
    if (objFor != null)
    {
      num = this.quotePointClearPriceDao.updateCommodityClearPrice(obj.getCommodityId(), obj.getPrice().doubleValue());
      if (num == -1)
      {
        num = -225;
      }
      else if (num == -2)
      {
        num = -226;
      }
      else if (num == -3)
      {
        num = -227;
      }
      else if (num == 1)
      {
        num = 3;
        this.quotePointClearPriceDao.updateBySQL("update t_quotation q set q.CLOSEPRICE=" + obj.getClosePrice() + " where q.commodityid='" + obj.getCommodityId() + "'");
      }
    }
    return num;
  }
}
