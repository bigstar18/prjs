package gnnt.MEBS.timebargain.mgr.service.impl;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.settleProps.SettlePropsP;
import gnnt.MEBS.timebargain.mgr.service.SettlePropsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("settlePropsService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class SettlePropsServiceImpl
  implements SettlePropsService
{

  @Autowired
  @Qualifier("com_standardService")
  private StandardService standardService;

  public void addSettleProps(SettlePropsP settleProps, List<SettlePropsP> goodsPropertys)
    throws Exception
  {
    List list = this.standardService.getListBySql("select * from T_settleProps where commodityId='" + settleProps.getCommodityId() + "'", new SettlePropsP());
    this.standardService.deleteBYBulk(list);
    if (goodsPropertys != null)
      for (SettlePropsP goodsProperty : goodsPropertys) {
        goodsProperty.setCommodityId(settleProps.getCommodityId());
        this.standardService.add(goodsProperty);
      }
  }
}