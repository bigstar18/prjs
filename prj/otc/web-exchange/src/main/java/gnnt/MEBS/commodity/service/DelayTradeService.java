package gnnt.MEBS.commodity.service;

import gnnt.MEBS.account.dao.MemberInfoDao;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.DelayTradeDao;
import gnnt.MEBS.commodity.dao.DelayTradeProDao;
import gnnt.MEBS.commodity.model.DelayTrade;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("delayTradeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class DelayTradeService
  extends SpecialSetService<DelayTrade>
{
  @Autowired
  @Qualifier("delayTradeDao")
  private DelayTradeDao delayTradeDao;
  @Autowired
  @Qualifier("delayTradeProDao")
  private DelayTradeProDao delayTradeProDao;
  @Autowired
  @Qualifier("memberInfoDao")
  private MemberInfoDao memberInfoDao;
  
  public BaseDao getDao()
  {
    return this.delayTradeDao;
  }
  
  public DelayTrade get(DelayTrade delayTrade)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.f_FirmId", "=", delayTrade.getF_FirmId());
    qc.addCondition("primary.commodityId", "=", delayTrade.getCommodityId());
    List<DelayTrade> list = this.delayTradeDao.getList(qc, null);
    if ((list != null) && (list.size() > 0))
    {
      delayTrade = new DelayTrade();
      delayTrade = (DelayTrade)list.get(0);
    }
    else
    {
      delayTrade = null;
    }
    return delayTrade;
  }
  
  public void updateSlipPointPro(String pType)
  {
    this.delayTradeProDao.updateSlipPointPro(pType);
  }
}
