package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.zcjs.model.HisGoodsOrder;
import java.util.List;

public class HisGoodsOrderDao
  extends DaoHelperImpl
{
  public void add(HisGoodsOrder paramHisGoodsOrder)
  {
    String str = "insert into Z_H_GoodsOrder (goodsOrderId,clearDate,goodsOrderNo,tradeCommodityMsgId,firmId,traderId,price,quantity,partBargainQuantity,businessDirection,status,isRegStock,regStockId,tradePoundage,tradeBail,goodsOrderDate,modifyDate,oldGoodsOrderId,isDelist) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramHisGoodsOrder.getGoodsOrderId()), paramHisGoodsOrder.getClearDate(), Long.valueOf(paramHisGoodsOrder.getGoodsOrderNo()), Long.valueOf(paramHisGoodsOrder.getTradeCommodityMsgId()), paramHisGoodsOrder.getFirmId(), paramHisGoodsOrder.getTraderId(), Double.valueOf(paramHisGoodsOrder.getPrice()), Double.valueOf(paramHisGoodsOrder.getQuantity()), Double.valueOf(paramHisGoodsOrder.getPartBargainQuantity()), paramHisGoodsOrder.getBusinessDirection(), Integer.valueOf(paramHisGoodsOrder.getStatus()), paramHisGoodsOrder.getIsRegStock(), paramHisGoodsOrder.getRegStockId(), Double.valueOf(paramHisGoodsOrder.getTradePoundage()), Double.valueOf(paramHisGoodsOrder.getTradeBail()), paramHisGoodsOrder.getGoodsOrderDate(), paramHisGoodsOrder.getModifyDate(), Long.valueOf(paramHisGoodsOrder.getOldGoodsOrderId()), paramHisGoodsOrder.getIsDelist() };
    int[] arrayOfInt = { 2, 93, 2, 2, 12, 12, 2, 2, 2, 12, 2, 12, 12, 2, 2, 93, 93, 2, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public HisGoodsOrder getObject(long paramLong)
  {
    String str = "select * from Z_H_GoodsOrder where GoodsOrderId='" + paramLong + "'";
    HisGoodsOrder localHisGoodsOrder = new HisGoodsOrder();
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new HisGoodsOrder()));
    if ((localList != null) && (localList.size() > 0)) {
      localHisGoodsOrder = (HisGoodsOrder)localList.get(0);
    }
    return localHisGoodsOrder;
  }
}
