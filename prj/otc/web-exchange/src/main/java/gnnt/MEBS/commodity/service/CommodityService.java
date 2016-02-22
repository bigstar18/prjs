package gnnt.MEBS.commodity.service;

import gnnt.MEBS.account.dao.MemberInfoProDao;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.ArticleParitiesAgioDao;
import gnnt.MEBS.commodity.dao.CommodityDao;
import gnnt.MEBS.commodity.dao.CommodityProDao;
import gnnt.MEBS.commodity.model.ArticleParitiesAgio;
import gnnt.MEBS.commodity.model.Commodity;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("commodityService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CommodityService
  extends BaseService<Commodity>
{
  private final transient Log logger = LogFactory.getLog(CommodityService.class);
  @Autowired
  @Qualifier("commodityDao")
  private CommodityDao commodityDao;
  @Autowired
  @Qualifier("commodityProDao")
  private CommodityProDao commodityProDao;
  @Autowired
  @Qualifier("articleParitiesAgioDao")
  private ArticleParitiesAgioDao articleParitiesAgioDao;
  @Autowired
  @Qualifier("memberInfoProDao")
  private MemberInfoProDao memberInfoProDao;
  
  public BaseDao getDao()
  {
    return this.commodityDao;
  }
  
  public int updateIn(Clone obj)
  {
    int num = 0;
    num = this.commodityProDao.updateIn((String)obj.getId());
    return num;
  }
  
  public int updateOut(Clone obj)
  {
    int num = 0;
    num = this.commodityProDao.updateOut((String)obj.getId());
    return num;
  }
  
  public Commodity get(Commodity commodity)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.id", "=", commodity.getId());
    List<Commodity> list = this.commodityDao.getList(qc, null);
    if ((list != null) && (list.size() > 0))
    {
      commodity = new Commodity();
      commodity = (Commodity)list.get(0);
    }
    else
    {
      commodity = null;
    }
    return commodity;
  }
  
  public List<Commodity> getAllCommodity()
  {
    return this.commodityDao.getList(null, null);
  }
  
  public int add(Commodity obj)
  {
    this.logger.debug("enter add");
    int num = 0;
    Clone clone = (Clone)obj.clone();
    if (obj.getQuoteRate() != null)
    {
      getDao().add(clone);
      ArticleParitiesAgio articleParitiesAgio = new ArticleParitiesAgio(obj.getId(), obj.getName(), obj.getQuoteRate(), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), obj.getId());
      this.articleParitiesAgioDao.add(articleParitiesAgio);
      getDao().flush();
      this.memberInfoProDao.commodityAdd(obj.getId(), "");
    }
    if (clone.getId() != null) {
      obj.setPrimary(clone.getId().toString());
    }
    num = 2;
    return num;
  }
  
  public int update(Commodity obj)
  {
    int num = 0;
    this.logger.debug("enter update");
    Clone objFor = copyObject(obj);
    ArticleParitiesAgio articleParitiesAgio = (ArticleParitiesAgio)this.articleParitiesAgioDao.getById(objFor.getId());
    if ((objFor != null) && (obj.getQuoteRate() != null))
    {
      getDao().update(objFor);
      articleParitiesAgio.setQuoteRate(obj.getQuoteRate());
      this.articleParitiesAgioDao.update(articleParitiesAgio);
    }
    num = 3;
    return num;
  }
  
  public int delete(Commodity obj)
  {
    int num = 0;
    this.logger.debug("enter delete");
    this.memberInfoProDao.commodityDelete(obj.getId());
    

    getDao().delete(obj);
    num = 4;
    return num;
  }
}
