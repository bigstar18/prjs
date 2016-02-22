package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.TCDelayFee;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberDelayFeeDao")
public class MemberDelayFeeDao
  extends BaseDao<TCDelayFee>
{
  private final transient Log logger = LogFactory.getLog(MemberDelayFeeDao.class);
  
  public List getTCDelayList(QueryConditions conditions, PageInfo pageInfo)
  {
    this.logger.debug("enter getList");
    String hql = "select distinct primary.commodityId,primary.firmId,commodity.name,memberInfo.name,primary.feeAlgr_v from " + getEntityClass().getName() + " as primary, gnnt.MEBS.commodity.model.Commodity as commodity ,gnnt.MEBS.account.model.MemberInfo as memberInfo where primary.commodityId=commodity.id and memberInfo.id=primary.firmId";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    this.logger.debug("hql:" + hql);
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    this.logger.debug("names:" + names + "   values:" + values + "  pageInfo:" + pageInfo);
    List list = queryByHQL(hql, names, values, pageInfo, null);
    
    List listCount = queryByHQL(hql, names, values, null, null);
    if (pageInfo != null) {
      pageInfo.setTotalRecords(listCount.size());
    }
    this.logger.debug("hql:" + hql);
    return list;
  }
  
  public Class getEntityClass()
  {
    return new TCDelayFee().getClass();
  }
}
