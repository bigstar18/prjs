package gnnt.MEBS.auto.dwr.bean;

import gnnt.MEBS.timebargain.manage.model.Commodity;
import gnnt.MEBS.timebargain.manage.service.CommodityManager;
import gnnt.MEBS.timebargain.manage.util.SysData;

public class TimebargainCommodity
{
  private CommodityManager commodityManager = (CommodityManager)SysData.getBean("commodityManager");
  
  public Commodity getCommodityById(String paramString)
  {
    Commodity localCommodity = this.commodityManager.getCommodityById(paramString);
    return localCommodity;
  }
}
