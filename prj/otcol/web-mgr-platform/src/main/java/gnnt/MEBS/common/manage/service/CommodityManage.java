package gnnt.MEBS.common.manage.service;

import gnnt.MEBS.common.manage.model.Apply_T_CommodityFee;
import gnnt.MEBS.common.manage.model.Apply_T_CommodityMargin;

public abstract interface CommodityManage
{
  public abstract int updateCommodityFeeCheck(Apply_T_CommodityFee paramApply_T_CommodityFee);
  
  public abstract int updateCommodityMarginCheck(Apply_T_CommodityMargin paramApply_T_CommodityMargin);
}
