package gnnt.MEBS.commodity.service;

import gnnt.MEBS.account.dao.MemberInfoDao;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.MemCustomerDelayTradeDao;
import gnnt.MEBS.commodity.model.MemCustomerDelayTrade;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memCustomerDelayTradeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MemCustomerDelayTradeService
  extends SpecialSetService<MemCustomerDelayTrade>
{
  @Autowired
  @Qualifier("memCustomerDelayTradeDao")
  private MemCustomerDelayTradeDao memCustomerDelayTradeDao;
  @Autowired
  @Qualifier("memberInfoDao")
  private MemberInfoDao memberInfoDao;
  
  public BaseDao getDao()
  {
    return this.memCustomerDelayTradeDao;
  }
  
  public MemCustomerDelayTrade get(MemCustomerDelayTrade memCustomerDelayTrade)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.f_FirmId", "=", memCustomerDelayTrade.getF_FirmId());
    qc.addCondition("primary.commodityId", "=", memCustomerDelayTrade.getCommodityId());
    List<MemCustomerDelayTrade> list = this.memCustomerDelayTradeDao.getList(qc, null);
    if ((list != null) && (list.size() > 0))
    {
      memCustomerDelayTrade = new MemCustomerDelayTrade();
      memCustomerDelayTrade = (MemCustomerDelayTrade)list.get(0);
    }
    else
    {
      memCustomerDelayTrade = null;
    }
    return memCustomerDelayTrade;
  }
}
