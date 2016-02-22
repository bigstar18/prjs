package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.model.Margin;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("memberMarginDao")
public class MemberMarginDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MemberMarginDao.class);
  
  public Class getEntityClass()
  {
    return new Margin().getClass();
  }
  
  public List<Margin> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new Margin(primary.commodityId,commodity.name,primary.firmId,memberinfo.name,primary.marginAlgr,primary.tradeMargin) from Margin as primary,Commodity as commodity,MemberInfo as memberinfo where primary.commodityId=commodity.id and primary.firmId=memberinfo.id";
    
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
