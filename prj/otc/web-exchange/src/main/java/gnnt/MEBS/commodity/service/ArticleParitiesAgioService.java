package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.ArticleParitiesAgioDao;
import gnnt.MEBS.commodity.model.ArticleParitiesAgio;
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

@Service("articleParitiesAgioService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ArticleParitiesAgioService
  extends BaseService<ArticleParitiesAgio>
{
  private final transient Log logger = LogFactory.getLog(ArticleParitiesAgioService.class);
  @Autowired
  @Qualifier("articleParitiesAgioDao")
  private ArticleParitiesAgioDao articleParitiesAgioDao;
  
  public BaseDao getDao()
  {
    return this.articleParitiesAgioDao;
  }
  
  public ArticleParitiesAgio get(ArticleParitiesAgio articleParitiesAgio)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", 
      articleParitiesAgio.getCommodityId());
    List<ArticleParitiesAgio> list = this.articleParitiesAgioDao.getList(qc, 
      null);
    if ((list != null) && (list.size() > 0))
    {
      articleParitiesAgio = new ArticleParitiesAgio();
      articleParitiesAgio = (ArticleParitiesAgio)list.get(0);
    }
    else
    {
      articleParitiesAgio = null;
    }
    return articleParitiesAgio;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getList(QueryConditions conditions, PageInfo pageInfo)
  {
    this.logger.debug("enter getList");
    return this.articleParitiesAgioDao.getList(conditions, pageInfo);
  }
}
