package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.DelayTrade;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("delayTradeDao")
public class DelayTradeDao
  extends BaseDao<DelayTrade>
{
  private final transient Log logger = LogFactory.getLog(DelayTradeDao.class);
  
  public Class getEntityClass()
  {
    return new DelayTrade().getClass();
  }
  
  public List<DelayTrade> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new DelayTrade(primary.commodityId,primary.f_FirmId,commodity.name,memberInfo.name,primary.delayTradeType,primary.delayTradeTime,primary.isslipPoint ) from DelayTrade as primary,MemberInfo as memberInfo,Commodity as commodity where primary.commodityId=commodity.id and primary.f_FirmId=memberInfo.id";
    

    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    this.logger.debug("hql:" + hql);
    return queryByHQL(hql, names, values, pageInfo, null);
  }
}
