package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.MemCustomerTakeFee;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memCustomerTakeFeeDao")
public class MemCustomerTakeFeeDao
  extends BaseDao<MemCustomerTakeFee>
{
  private final transient Log logger = LogFactory.getLog(MemCustomerTakeFeeDao.class);
  
  public Class getEntityClass()
  {
    return new MemCustomerTakeFee().getClass();
  }
  
  public List<MemCustomerTakeFee> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new MemCustomerTakeFee(primary.commodityId,primary.m_FirmId,commodity.name,memberInfo.name,primary.feeAlgr,primary.feeRate,primary.feeMode,primary.mkt_FeeRate) from MemCustomerTakeFee as primary,MemberInfo as memberInfo,Commodity as commodity where primary.commodityId=commodity.id and primary.m_FirmId=memberInfo.id";
    

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
