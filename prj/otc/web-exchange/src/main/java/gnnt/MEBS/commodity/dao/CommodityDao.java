package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.Commodity;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("commodityDao")
public class CommodityDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(CommodityDao.class);
  
  public Class getEntityClass()
  {
    return new Commodity().getClass();
  }
  
  public List<Commodity> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new Commodity(primary.id ,primary.name,primary.status,primary.tradeMode,primary.contractFactor,primary.minHQMove,primary.minPriceMove,primary.stepMove,primary.spreadAlgr,primary.spreadUpLmt,primary.spreadDownLmt,primary.delayFeeAlgr,primary.marketDate,primary.lastPrice,primary.displayNum,primary.pauseType,apg.quoteRate,primary.contractUnit) from Commodity as primary,ArticleParitiesAgio as apg where primary.id=apg.commodityId";
    


    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    return queryByHQL(hql, names, values, pageInfo, null);
  }
  
  public void add()
  {
    this.logger.debug("enter add");
    getHibernateTemplate().save(null);
  }
  
  public void update()
  {
    this.logger.debug("enter update");
    getHibernateTemplate().update(null);
  }
}
