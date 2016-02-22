package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("customerDao")
public class CustomerDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(CustomerDao.class);
  
  public Class getEntityClass()
  {
    return new Customer().getClass();
  }
  
  public List<Customer> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new Customer(primary.customerNo,primary.name,primary.memberNo,memberInfo.name,primary.papersType,primary.address,primary.phone,primary.fax,primary.postCode,primary.email,primary.papersName,primary.extendData,customerStatus.status) from Customer as primary,MemberInfo as memberInfo,CustomerStatus as customerStatus where primary.memberNo=memberInfo.id and customerStatus.customerNo=primary.customerNo";
    

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
