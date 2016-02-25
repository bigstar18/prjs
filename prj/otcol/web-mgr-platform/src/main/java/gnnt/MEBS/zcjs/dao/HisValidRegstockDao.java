package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.zcjs.model.HisValidRegstock;

public class HisValidRegstockDao
  extends DaoHelperImpl
{
  public void add(HisValidRegstock paramHisValidRegstock)
  {
    String str = "insert into Z_h_ValidRegstock (regstockId,firmId,breedId,commodityProperties,quality,quantity,status,type,clearDate) values(?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramHisValidRegstock.getRegstockId(), paramHisValidRegstock.getFirmId(), Long.valueOf(paramHisValidRegstock.getBreedId()), paramHisValidRegstock.getCommodityProperties(), paramHisValidRegstock.getQuality(), Double.valueOf(paramHisValidRegstock.getQuantity()), Integer.valueOf(paramHisValidRegstock.getStatus()), Integer.valueOf(paramHisValidRegstock.getType()), paramHisValidRegstock.getClearDate() };
    int[] arrayOfInt = { 12, 12, 2, 12, 12, 2, 2, 2, 93 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
