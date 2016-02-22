package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.CommodityPriceProtectDao;
import gnnt.MEBS.commodity.model.CommodityPriceProtect;
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

@Service("commodityPriceProtectService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CommodityPriceProtectService
  extends BaseService<CommodityPriceProtect>
{
  private final transient Log logger = LogFactory.getLog(CommodityPriceProtectService.class);
  @Autowired
  @Qualifier("commodityPriceProtectDao")
  private CommodityPriceProtectDao commodityPriceProtectDao;
  
  public BaseDao getDao()
  {
    return this.commodityPriceProtectDao;
  }
  
  public CommodityPriceProtect get(CommodityPriceProtect commodityPriceProtect)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.commodityId", "=", 
      commodityPriceProtect.getCommodityId());
    List<CommodityPriceProtect> list = this.commodityPriceProtectDao.getList(qc, 
      null);
    if ((list != null) && (list.size() > 0))
    {
      commodityPriceProtect = new CommodityPriceProtect();
      commodityPriceProtect = (CommodityPriceProtect)list.get(0);
    }
    else
    {
      commodityPriceProtect = null;
    }
    return commodityPriceProtect;
  }
}
