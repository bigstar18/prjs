package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.CustomerVO;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerVODao")
public class CustomerVODao
  extends BaseDao<CustomerVO>
{
  private final transient Log logger = LogFactory.getLog(CustomerVODao.class);
  
  public Class getEntityClass()
  {
    return new CustomerVO().getClass();
  }
  
  public List<CustomerVO> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new CustomerVO(primary.customerNo,primary.name,primary.memberNo,memberInfo.name,primary.papersType,primary.papersName,primary.address,primary.phone,primary.fax,primary.postCode,primary.email,primary.extendData,primary.phonePWD,primary.customerStatus.status,primary.brokerageNo,primary.brokerageName,primary.managerNo,primary.managerName,primary.organizationNo,primary.organizationName,primary.createTime,primary.signTime,primary.activateTime,primary.freezeTime,primary.demiseTime,primary.useFunds) from CustomerVO as primary,MemberInfo as memberInfo where primary.memberNo=memberInfo.id";
    



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
  
  public List<CustomerVO> getCustomerList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new CustomerVO(primary.customerNo,primary.name,primary.memberNo,memberInfo.name,primary.papersType,primary.papersName,primary.address,primary.phone,primary.fax,primary.postCode,primary.email,primary.extendData,primary.phonePWD,primary.customerStatus.status,primary.brokerageNo,primary.brokerageName,primary.managerNo,primary.managerName,primary.organizationNo,primary.organizationName,primary.createTime,primary.signTime,primary.activateTime,primary.freezeTime,primary.demiseTime,primary.useFunds) from CustomerVO as primary,MemberInfo as memberInfo where primary.memberNo=memberInfo.id";
    



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
