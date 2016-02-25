package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.zcjs.model.HisSubmit;

public class HisSubmitDao
  extends DaoHelperImpl
{
  public void add(HisSubmit paramHisSubmit)
  {
    String str = "insert into Z_H_Submit (submitId,clearDate,traderId,firmId,tradeCommodityMsgId,submitQuantity,submitPrice,businessDirection,isRegstock,regStockId,tradeBail,tradePoundage,discussPriceId,dailySubmitDate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramHisSubmit.getSubmitId()), paramHisSubmit.getClearDate(), paramHisSubmit.getTraderId(), paramHisSubmit.getFirmId(), Long.valueOf(paramHisSubmit.getTradeCommodityMsgId()), Double.valueOf(paramHisSubmit.getSubmitQuantity()), Double.valueOf(paramHisSubmit.getSubmitPrice()), paramHisSubmit.getBusinessDirection(), paramHisSubmit.getIsRegstock(), paramHisSubmit.getRegStockId(), Double.valueOf(paramHisSubmit.getTradeBail()), Double.valueOf(paramHisSubmit.getTradePoundage()), Long.valueOf(paramHisSubmit.getDiscussPriceId()), paramHisSubmit.getDailySubmitDate() };
    int[] arrayOfInt = { 2, 91, 12, 12, 2, 2, 2, 12, 12, 12, 2, 2, 2, 91 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
