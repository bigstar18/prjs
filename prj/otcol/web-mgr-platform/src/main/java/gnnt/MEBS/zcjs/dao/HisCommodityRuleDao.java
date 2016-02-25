package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.zcjs.model.HisCommodityRule;
import java.util.List;

public class HisCommodityRuleDao
  extends DaoHelperImpl
{
  public void add(HisCommodityRule paramHisCommodityRule)
  {
    String str = "insert into Z_H_CommodityRule (commodityRuleId,ClearDate,breedId,bail,bailMode,tradePoundage,tradePoundageMode,deliveryPoundage,deliveryPoundageMode,maxPrice,minPrice,commodityRuleStatus,commodityRuleFirmId,commodityRuleBusinessDirection) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramHisCommodityRule.getCommodityRuleId()), paramHisCommodityRule.getClearDate(), Long.valueOf(paramHisCommodityRule.getBreedId()), Double.valueOf(paramHisCommodityRule.getBail()), Integer.valueOf(paramHisCommodityRule.getBailMode()), Double.valueOf(paramHisCommodityRule.getTradePoundage()), Integer.valueOf(paramHisCommodityRule.getTradePoundageMode()), Double.valueOf(paramHisCommodityRule.getDeliveryPoundage()), Integer.valueOf(paramHisCommodityRule.getDeliveryPoundageMode()), Double.valueOf(paramHisCommodityRule.getMaxPrice()), Double.valueOf(paramHisCommodityRule.getMinPrice()), Integer.valueOf(paramHisCommodityRule.getCommodityRuleStatus()), paramHisCommodityRule.getCommodityRuleFirmId(), paramHisCommodityRule.getCommodityRuleBusinessDirection() };
    int[] arrayOfInt = { 2, 91, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List getObject(long paramLong)
  {
    String str = "select * from Z_H_CommodityRule where commodityRuleId='" + paramLong + "'";
    List localList = queryBySQL(str);
    return localList;
  }
}
