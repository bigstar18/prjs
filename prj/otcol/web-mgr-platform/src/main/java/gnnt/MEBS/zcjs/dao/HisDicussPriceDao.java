package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.zcjs.model.HisDiscussPrice;
import java.util.List;
import org.apache.commons.logging.Log;

public class HisDicussPriceDao
  extends DaoHelperImpl
{
  public void add(HisDiscussPrice paramHisDiscussPrice)
  {
    String str = "insert into Z_H_DiscussPrice (discussPriceId,clearDate,tradeCommodityMsgId,goodsOrderId,followFirmId,followTraderId,discussPriceFirmId,discussPrice,quantity,businessDirection,tradeBail,tradePoundage,type,status,isRegStock,regStockId,discussPriceDate,modifyDate,note) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramHisDiscussPrice.getDiscussPriceId()), paramHisDiscussPrice.getClearDate(), Long.valueOf(paramHisDiscussPrice.getTradeCommodityMsgId()), Long.valueOf(paramHisDiscussPrice.getGoodsOrderId()), paramHisDiscussPrice.getFollowFirmId(), paramHisDiscussPrice.getFollowTraderId(), paramHisDiscussPrice.getDiscussPriceFirmId(), Double.valueOf(paramHisDiscussPrice.getDiscussPrice()), Double.valueOf(paramHisDiscussPrice.getQuantity()), paramHisDiscussPrice.getBusinessDirection(), Double.valueOf(paramHisDiscussPrice.getTradeBail()), Double.valueOf(paramHisDiscussPrice.getTradePoundage()), Integer.valueOf(paramHisDiscussPrice.getType()), Integer.valueOf(paramHisDiscussPrice.getStatus()), paramHisDiscussPrice.getIsRegStock(), paramHisDiscussPrice.getRegStockId(), paramHisDiscussPrice.getDiscussPriceDate(), paramHisDiscussPrice.getModifyDate(), paramHisDiscussPrice.getNote() };
    int[] arrayOfInt = { 2, 93, 2, 2, 12, 12, 12, 2, 2, 12, 2, 2, 2, 2, 12, 12, 93, 93, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public HisDiscussPrice getObject(long paramLong)
  {
    String str = "select * from z_h_discussprice where DiscussPriceId=?";
    Object[] arrayOfObject = { Long.valueOf(paramLong) };
    this.logger.debug("sql: " + str);
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new HisDiscussPrice()));
    HisDiscussPrice localHisDiscussPrice = null;
    if ((localList != null) && (localList.size() > 0)) {
      localHisDiscussPrice = (HisDiscussPrice)localList.get(0);
    }
    return localHisDiscussPrice;
  }
}
