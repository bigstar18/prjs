package gnnt.MEBS.broke.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.model.BrokerageForZTree;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("brokerageZTreeDao")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class BrokerageZTreeDao
  extends BaseDao
{
  public Class getEntityClass()
  {
    return BrokerageForZTree.class;
  }
  
  public List getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new BrokerageForZTree(primary.brokerageNo,primary.name,primary.memberNo,broAndOrg) from BrokerageForZTree as primary left join primary.broAndOrg as broAndOrg  ";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null) && (!"".equals(conditions.getFieldsSqlClause())))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    return queryByHQL(hql, names, values, pageInfo, null);
  }
}
