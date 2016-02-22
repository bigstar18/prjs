package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.CustomerHoldQtyDao;
import gnnt.MEBS.commodity.model.HoldQty;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("customerHoldQtyService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CustomerHoldQtyService
  extends SpecialSetService<HoldQty>
{
  private final transient Log logger = LogFactory.getLog(CustomerHoldQtyService.class);
  @Autowired
  @Qualifier("customerHoldQtyDao")
  private CustomerHoldQtyDao customerHoldQtyDao;
  
  public BaseDao getDao()
  {
    return this.customerHoldQtyDao;
  }
  
  public HoldQty get(HoldQty holdQty)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.firmId", "=", holdQty.getFirmId());
    qc.addCondition("primary.commodityId", "=", holdQty.getCommodityId());
    List<HoldQty> list = this.customerHoldQtyDao.getList(qc, null);
    if ((list != null) && (list.size() > 0))
    {
      holdQty = new HoldQty();
      holdQty = (HoldQty)list.get(0);
    }
    else
    {
      holdQty = null;
    }
    return holdQty;
  }
}
