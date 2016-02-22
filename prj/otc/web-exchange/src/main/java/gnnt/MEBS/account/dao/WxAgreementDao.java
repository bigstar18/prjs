package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.WxAgreement;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("wxAgreementDao")
public class WxAgreementDao
  extends BaseDao<WxAgreement>
{
  private final transient Log logger = LogFactory.getLog(WxAgreementDao.class);
  
  public Class getEntityClass()
  {
    return new WxAgreement().getClass();
  }
  
  public List<WxAgreement> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new WxAgreement(primary.agreement1,primary.agreement2,primary.agreement3,primary.agreement4,primary.agreement5) from WxAgreement as primary";
    

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
