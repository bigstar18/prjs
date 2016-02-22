package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.CustomerHoldQtyDao;
import gnnt.MEBS.commodity.model.CustomerHoldQty;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
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
  extends BaseService<CustomerHoldQty>
{
  private final transient Log logger = LogFactory.getLog(CustomerHoldQtyService.class);
  @Autowired
  @Qualifier("customerHoldQtyDao")
  private CustomerHoldQtyDao customerHoldQtyDao;
  
  public BaseDao getDao()
  {
    return this.customerHoldQtyDao;
  }
  
  public CustomerHoldQty get(CustomerHoldQty holdQty)
  {
    QueryConditions qc = new QueryConditions();
    CustomerHoldQty holdQty1 = null;
    qc.addCondition("primary.customerNo", "=", holdQty.getCustomerNo());
    qc.addCondition("primary.commodityId", "=", holdQty.getCommodityId());
    List<CustomerHoldQty> list = getList(qc, null);
    if ((list != null) && (list.size() > 0))
    {
      holdQty1 = new CustomerHoldQty();
      holdQty1 = (CustomerHoldQty)list.get(0);
    }
    return holdQty1;
  }
}
