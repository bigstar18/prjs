package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.HisTrade;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HisTradeDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(HisTradeDao.class);
  
  public void add(HisTrade paramHisTrade)
  {
    String str = "insert into Z_H_Trade (tradeNo,clearDate,tradeDate,deliveryDate,quantity,price,breedId,commodityProperties,firmId_S,traderId_S,tradeBail_S,tradePoundage_S,deliveryPoundage_S,submitId_S,submitId_B,firmId_B,traderId_B,tradeBail_B,tradePoundage_B,deliveryPoundage_B,isRegstock,regStockId,status,deliveryPlace,note,expandProperty,quality) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramHisTrade.getTradeNo()), paramHisTrade.getClearDate(), paramHisTrade.getTradeDate(), paramHisTrade.getDeliveryDate(), Double.valueOf(paramHisTrade.getQuantity()), Double.valueOf(paramHisTrade.getPrice()), Long.valueOf(paramHisTrade.getBreedId()), paramHisTrade.getCommodityProperties(), paramHisTrade.getFirmId_S(), paramHisTrade.getTraderId_S(), Double.valueOf(paramHisTrade.getTradeBail_S()), Double.valueOf(paramHisTrade.getTradePoundage_S()), Double.valueOf(paramHisTrade.getDeliveryPoundage_S()), Long.valueOf(paramHisTrade.getSubmitId_S()), Long.valueOf(paramHisTrade.getSubmitId_B()), paramHisTrade.getFirmId_B(), paramHisTrade.getTraderId_B(), Double.valueOf(paramHisTrade.getTradeBail_B()), Double.valueOf(paramHisTrade.getTradePoundage_B()), Double.valueOf(paramHisTrade.getDeliveryPoundage_B()), paramHisTrade.getIsRegstock(), paramHisTrade.getRegStockId(), Integer.valueOf(paramHisTrade.getStatus()), paramHisTrade.getDeliveryPlace(), paramHisTrade.getNote(), paramHisTrade.getExpandProperty(), paramHisTrade.getQuality() };
    int[] arrayOfInt = { 2, 93, 93, 93, 2, 2, 2, 12, 12, 12, 2, 2, 2, 2, 2, 12, 12, 2, 2, 2, 12, 12, 2, 12, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select zh.*,trunc(deliverydate)-trunc(sysdate) spareDate ,zb.breedName from Z_H_Trade zh,Z_Breed zb where zh.breedId=zb.breedId) ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where  " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public HisTrade getObject(long paramLong)
  {
    String str = "select * from Z_H_Trade where tradeNo=?";
    Object[] arrayOfObject = { Long.valueOf(paramLong) };
    this.logger.debug("sql: " + str);
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new HisTrade()));
    HisTrade localHisTrade = null;
    if ((localList != null) && (localList.size() > 0)) {
      localHisTrade = (HisTrade)localList.get(0);
    }
    return localHisTrade;
  }
  
  public void update(HisTrade paramHisTrade)
  {
    String str = "update Z_h_trade set note=?,expandProperty=?, clearDate=?,tradeDate=?,deliveryDate=?,quantity=?,price=?,breedId=?,quality=?,deliveryPlace=?,commodityProperties=?,firmId_S=?,traderId_S=?,tradeBail_S=?,tradePoundage_S=?,deliveryPoundage_S=?,submitId_S=?,submitId_B=?,firmId_B=?,traderId_B=?,tradeBail_B=?,tradePoundage_B=?,deliveryPoundage_B=?,isRegstock=?,regStockId=?,status=?  where tradeNo=? ";
    Object[] arrayOfObject = { paramHisTrade.getNote(), paramHisTrade.getExpandProperty(), paramHisTrade.getClearDate(), paramHisTrade.getTradeDate(), paramHisTrade.getDeliveryDate(), Double.valueOf(paramHisTrade.getQuantity()), Double.valueOf(paramHisTrade.getPrice()), Long.valueOf(paramHisTrade.getBreedId()), paramHisTrade.getQuality(), paramHisTrade.getDeliveryPlace(), paramHisTrade.getCommodityProperties(), paramHisTrade.getFirmId_S(), paramHisTrade.getTraderId_S(), Double.valueOf(paramHisTrade.getTradeBail_S()), Double.valueOf(paramHisTrade.getTradePoundage_S()), Double.valueOf(paramHisTrade.getDeliveryPoundage_S()), Long.valueOf(paramHisTrade.getSubmitId_S()), Long.valueOf(paramHisTrade.getSubmitId_B()), paramHisTrade.getFirmId_B(), paramHisTrade.getTraderId_B(), Double.valueOf(paramHisTrade.getTradeBail_B()), Double.valueOf(paramHisTrade.getTradePoundage_B()), Double.valueOf(paramHisTrade.getDeliveryPoundage_B()), paramHisTrade.getIsRegstock(), paramHisTrade.getRegStockId(), Integer.valueOf(paramHisTrade.getStatus()), Long.valueOf(paramHisTrade.getTradeNo()) };
    int[] arrayOfInt = { 12, 12, 93, 93, 93, 2, 2, 2, 12, 12, 12, 12, 12, 2, 2, 2, 2, 2, 12, 12, 2, 2, 2, 12, 12, 2, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
