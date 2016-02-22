package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.CommodityFee;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("commodityFeeDao")
public class CommodityFeeDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(CommodityFeeDao.class);
  
  public Class getEntityClass()
  {
    return new CommodityFee().getClass();
  }
  
  public List<CommodityFee> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new CommodityFee(primary.commodityId,commodity.name,primary.firmId,primary.feeAlgr,primary.feeRate,primary.feeMode,primary.mkt_FeeRate) from CommodityFee as primary ,Commodity commodity where primary.commodityId=commodity.id";
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
}
