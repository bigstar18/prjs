package gnnt.MEBS.common.manage.dao;

import gnnt.MEBS.common.manage.model.Apply_T_CommodityFee;
import gnnt.MEBS.common.manage.model.Apply_T_CommodityMargin;

public abstract interface CommodityDAO
{
  public abstract void updateCommodityFee(Apply_T_CommodityFee paramApply_T_CommodityFee);
  
  public abstract int checkCommodityID(Apply_T_CommodityFee paramApply_T_CommodityFee);
  
  public abstract void updateCommodityMargin(Apply_T_CommodityMargin paramApply_T_CommodityMargin);
  
  public abstract int checkMarginCommodityID(Apply_T_CommodityMargin paramApply_T_CommodityMargin);
}
