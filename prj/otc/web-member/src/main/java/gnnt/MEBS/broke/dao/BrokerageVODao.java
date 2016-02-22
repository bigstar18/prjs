package gnnt.MEBS.broke.dao;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.model.BrokerageVO;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("brokerageVODao")
public class BrokerageVODao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(BrokerageVODao.class);
  
  public Class getEntityClass()
  {
    return new BrokerageVO().getClass();
  }
  
  public List<BrokerageVO> getListForSimple(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new gnnt.MEBS.broke.model.BrokerageVO(primary.brokerageNo,primary.name,primary.memberNo,primary.orgNo) from gnnt.MEBS.broke.model.BrokerageVO as primary where 1=1";
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
