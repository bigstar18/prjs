package gnnt.MEBS.timebargain.mgr.service.impl;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.dao.CommodityIdDao;
import gnnt.MEBS.timebargain.mgr.service.CommodityIdService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("commodityIdService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class CommodityIdServiceImpl extends StandardService
  implements CommodityIdService
{

  @Autowired
  @Qualifier("commodityDao")
  private CommodityIdDao commodityIdDao;

  public CommodityIdDao getCommodityIdDao()
  {
    return this.commodityIdDao;
  }
  public List commodityIdList(String isDesc) {
    List list = null;
    list = this.commodityIdDao.commodityIdList(isDesc);
    return list;
  }

  public List firmIdList(String isDesc)
  {
    List list = null;
    list = this.commodityIdDao.firmIdList(isDesc);
    return list;
  }
}