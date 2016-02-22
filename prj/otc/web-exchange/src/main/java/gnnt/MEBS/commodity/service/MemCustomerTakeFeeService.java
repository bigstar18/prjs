package gnnt.MEBS.commodity.service;

import gnnt.MEBS.account.dao.MemberInfoDao;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.MemCustomerTakeFeeDao;
import gnnt.MEBS.commodity.model.MemCustomerTakeFee;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memCustomerTakeFeeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MemCustomerTakeFeeService
  extends SpecialSetService<MemCustomerTakeFee>
{
  @Autowired
  @Qualifier("memCustomerTakeFeeDao")
  private MemCustomerTakeFeeDao memCustomerTakeFeeDao;
  @Autowired
  @Qualifier("memberInfoDao")
  private MemberInfoDao memberInfoDao;
  
  public BaseDao getDao()
  {
    return this.memCustomerTakeFeeDao;
  }
  
  public MemCustomerTakeFee get(MemCustomerTakeFee customerTakeFee)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.m_FirmId", "=", customerTakeFee.getM_FirmId());
    qc.addCondition("primary.commodityId", "=", customerTakeFee.getCommodityId());
    List<MemCustomerTakeFee> list = this.memCustomerTakeFeeDao.getList(qc, null);
    if ((list != null) && (list.size() > 0))
    {
      customerTakeFee = new MemCustomerTakeFee();
      customerTakeFee = (MemCustomerTakeFee)list.get(0);
    }
    else
    {
      customerTakeFee = null;
    }
    return customerTakeFee;
  }
}
