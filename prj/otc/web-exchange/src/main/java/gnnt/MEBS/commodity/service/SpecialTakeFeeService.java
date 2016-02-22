package gnnt.MEBS.commodity.service;

import gnnt.MEBS.account.dao.SpecialMemberDao;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.SpecialTakeFeeDao;
import gnnt.MEBS.commodity.model.TakeFee;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("specialTakeFeeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SpecialTakeFeeService
  extends SpecialSetService<TakeFee>
{
  @Autowired
  @Qualifier("specialTakeFeeDao")
  private SpecialTakeFeeDao specialTakeFeeDao;
  @Autowired
  @Qualifier("specialMemberDao")
  private SpecialMemberDao specialMemberDao;
  
  public BaseDao getDao()
  {
    return this.specialTakeFeeDao;
  }
  
  public SpecialTakeFeeDao getSpecialTakeFeeDao()
  {
    return this.specialTakeFeeDao;
  }
  
  public void setSpecialTakeFeeDao(SpecialTakeFeeDao specialTakeFeeDao)
  {
    this.specialTakeFeeDao = specialTakeFeeDao;
  }
  
  public TakeFee get(TakeFee takeFee)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.m_FirmId", "=", takeFee.getM_FirmId());
    qc.addCondition("primary.commodityId", "=", takeFee.getCommodityId());
    List<TakeFee> list = this.specialTakeFeeDao.getList(qc, null);
    if ((list != null) && (list.size() > 0))
    {
      takeFee = new TakeFee();
      takeFee = (TakeFee)list.get(0);
    }
    else
    {
      takeFee = null;
    }
    return takeFee;
  }
}
