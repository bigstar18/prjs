package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.WxCustomer;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("wxCustomerDao")
public class WxCustomerDao
  extends BaseDao<WxCustomer>
{
  private final transient Log logger = LogFactory.getLog(WxCustomerDao.class);
  
  public Class getEntityClass()
  {
    return new WxCustomer().getClass();
  }
  
  public List<WxCustomer> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new WxCustomer(primary.customerno,primary.name,primary.memberno,memberInfo.name,primary.paperscode,primary.phonepwd,primary.address,primary.contactman,primary.phone,primary.fax,primary.postcode,primary.email,primary.note,primary.extenddata,primary.wxno,primary.wxnickname,primary.paperstype,primary.createtime,primary.signtime,primary.activatetime,primary.freezetime,primary.demisetime,primary.customerStatus.status) from WxCustomer as primary,MemberInfo as memberInfo where primary.memberno=memberInfo.id and primary.wxno is not null";
    







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
