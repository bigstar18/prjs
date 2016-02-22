package gnnt.MEBS.settlement.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.settlement.model.AvgPrice;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("avgPriceDao")
public class AvgPriceDao
  extends BaseDao
{
  public Class getEntityClass()
  {
    return new AvgPrice().getClass();
  }
  
  public Object[] getAvg(QueryConditions conditions, PageInfo pageInfo)
  {
    String commodityId = (String)conditions.getConditionValue("primary.commodityId");
    String commodityHql = "select t.minPriceMove from Commodity t where t.id= '" + commodityId + "'";
    List minPriceMoveList = queryByHQL(commodityHql, null, null, null, null);
    BigDecimal roundNum = new BigDecimal(0);
    String avgSize = "2";
    if (minPriceMoveList.size() > 0) {
      roundNum = (BigDecimal)minPriceMoveList.get(0);
    }
    if (roundNum.doubleValue() != 0.01D) {
      avgSize = "0";
    }
    String hql = "select round(avg(primary.price)," + avgSize + "),count(*) , " + roundNum + " , " + avgSize + " from AvgPrice as primary  where 1=1 ";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    List list = queryByHQL(hql, names, values, pageInfo, null);
    if (list.size() > 0) {
      return (Object[])list.get(0);
    }
    return null;
  }
  
  public List<AvgPrice> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new AvgPrice(primary.detailId,primary.commodityId,primary.price,primary.occurTime)from AvgPrice as primary  where 1=1 ";
    




    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
      hql = hql + " order by primary.occurTime desc ";
    }
    List<AvgPrice> list = queryByHQL(hql, names, values, pageInfo, null);
    return list;
  }
}
