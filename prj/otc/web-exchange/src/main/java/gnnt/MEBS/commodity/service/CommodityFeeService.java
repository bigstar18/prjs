package gnnt.MEBS.commodity.service;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.commodity.dao.CommodityFeeDao;
import gnnt.MEBS.commodity.model.CommodityFee;
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

@Service("commodityFeeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CommodityFeeService
  extends BaseService<CommodityFee>
{
  private final transient Log logger = LogFactory.getLog(CommodityFeeService.class);
  @Autowired
  @Qualifier("commodityFeeDao")
  private CommodityFeeDao commodityFeeDao;
  
  public BaseDao getDao()
  {
    return this.commodityFeeDao;
  }
  
  public CommodityFee get(CommodityFee commodityFee)
  {
    QueryConditions qc = new QueryConditions();
    qc.addCondition("primary.firmId", "=", commodityFee.getFirmId());
    qc.addCondition("primary.commodityId", "=", 
      commodityFee.getCommodityId());
    List<CommodityFee> list = this.commodityFeeDao.getList(qc, null);
    if ((list != null) && (list.size() > 0))
    {
      commodityFee = new CommodityFee();
      commodityFee = (CommodityFee)list.get(0);
    }
    else
    {
      commodityFee = null;
    }
    return commodityFee;
  }
}
