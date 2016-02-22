package gnnt.MEBS.announcement.dao;

import gnnt.MEBS.announcement.model.Trade;
import gnnt.MEBS.base.query.hibernate.CacheSet;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("tradeDao")
public class TradeDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(TradeDao.class);
  
  public Class getEntityClass()
  {
    return new Trade().getClass();
  }
  
  public List<Trade> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new Trade(primary.tradeNo, primary.orderNo, primary.holdNo, primary.bs_flag, primary.firmId, primary.commodityId, primary.o_firmId, commodity.name, firm.firmName, primary.o_bs_flag, primary.oc_flag, primary.quantity) from Trade as primary, Commodity as commodity, Firm as firm where primary.firmId = firm.firmId and primary.commodityId = commodity.id ";
    


    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null) && (!"".equals(conditions.getFieldsSqlClause())))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    CacheSet cacheSet = new CacheSet();
    cacheSet.setRegion("queryCache");
    List<Trade> list = queryByHQL(hql, names, values, pageInfo, cacheSet);
    return list;
  }
  
  public List<Trade> getTradeList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new Trade(primary.tradeNo, primary.orderNo, primary.holdNo, primary.bs_flag, primary.firmId, primary.commodityId, primary.o_firmId, commodity.name, firm.firmName, primary.o_bs_flag, primary.oc_flag, primary.quantity) from Trade as primary, CustomerRelateOrganization customerRelateOrg, Commodity as commodity, Firm as firm where customerRelateOrg.customerNo = primary.firmId and primary.firmId = firm.firmId and primary.commodityId = commodity.id ";
    

    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null) && (!"".equals(conditions.getFieldsSqlClause())))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    CacheSet cacheSet = new CacheSet();
    cacheSet.setRegion("queryCache");
    List<Trade> list = queryByHQL(hql, names, values, pageInfo, cacheSet);
    return list;
  }
}
