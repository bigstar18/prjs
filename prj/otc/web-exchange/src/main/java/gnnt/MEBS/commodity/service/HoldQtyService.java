package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.HoldQtyDao;
import gnnt.MEBS.commodity.model.HoldQty;
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

@Service("holdQtyService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class HoldQtyService
  extends BaseService<HoldQty>
{
  private final transient Log logger = LogFactory.getLog(HoldQtyService.class);
  @Autowired
  @Qualifier("holdQtyDao")
  private HoldQtyDao holdQtyDao;
  
  public BaseDao getDao()
  {
    return this.holdQtyDao;
  }
  
  public HoldQty get(HoldQty holdQty)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.firmId", "=", holdQty.getFirmId());
    qc.addCondition("primary.commodityId", "=", holdQty.getCommodityId());
    List<HoldQty> list = this.holdQtyDao.getList(qc, null);
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
  
  public int update(HoldQty obj)
  {
    int num = 0;
    delete(obj);
    add(obj);
    num = 3;
    return num;
  }
}
