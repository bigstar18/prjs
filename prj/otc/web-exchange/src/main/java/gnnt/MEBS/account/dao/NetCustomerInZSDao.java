package gnnt.MEBS.account.dao;

import gnnt.MEBS.account.model.NetCustomerInZS;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("netCustomerInZSDao")
public class NetCustomerInZSDao
  extends BaseDao<NetCustomerInZS>
{
  private final transient Log logger = LogFactory.getLog(NetCustomerInZSDao.class);
  
  public Class getEntityClass()
  {
    return new NetCustomerInZS().getClass();
  }
  
  public List<NetCustomerInZS> getList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new NetCustomerInZS(primary.customerNo,primary.name,primary.memberNo,memberInfo.name,primary.papersType,primary.papersName,primary.address,primary.phone,primary.fax,primary.postCode,primary.email,primary.extendData,primary.phonePWD,primary.customerStatus.status,primary.brokerageNo,primary.brokerageName,primary.managerNo,primary.managerName,primary.organizationNo,primary.organizationName,primary.createTime,primary.signTime,primary.activateTime,primary.freezeTime,primary.demiseTime,primary.useFunds,netCount.protocolno,netCount.accountagreement,netCount.riskoverbook,netCount.accountprotocol,netCount.instruction,netCount.createtime) from NetCustomer as primary,MemberInfo as memberInfo,NetCountInZS netCount where primary.memberNo=memberInfo.id and primary.customerNo=netCount.customerno";
    

































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
