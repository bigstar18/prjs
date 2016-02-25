package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.Trade;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;

public class TradeDao
  extends DaoHelperImpl
{
  public List<Trade> getObjectList(QueryConditions paramQueryConditions)
  {
    String str = "select * from Z_Trade where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new Trade()));
  }
  
  public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select zh.*,trunc(deliverydate)-trunc(sysdate) spareDate ,zb.breedName from Z_Trade zh,Z_Breed zb where zh.breedId=zb.breedId) ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where  " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public Trade getObject(long paramLong)
  {
    String str = "select * from Z_Trade where tradeNo=?";
    Object[] arrayOfObject = { Long.valueOf(paramLong) };
    this.logger.debug("sql: " + str);
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new Trade()));
    Trade localTrade = null;
    if ((localList != null) && (localList.size() > 0)) {
      localTrade = (Trade)localList.get(0);
    }
    return localTrade;
  }
  
  public void delete(long paramLong)
  {
    String str = "delete from Z_Trade where TradeNo=" + paramLong + "";
    updateBySQL(str);
  }
  
  public long getId()
  {
    long l = 0L;
    String str = "select SEQ_Z_TRADE.nextVal from dual";
    l = queryForInt(str, null);
    return l;
  }
  
  public void add(Trade paramTrade)
  {
    String str = "insert into Z_Trade (tradeNo,deliveryDate,tradeDate,quantity,price,breedId,commodityProperties,firmId_S,traderId_S,tradeBail_S,tradePoundage_S,deliveryPoundage_S,submitId_S,submitId_B,firmId_B,traderId_B,tradeBail_B,tradePoundage_B,deliveryPoundage_B,isRegstock,regStockId,deliveryPlace,note,expandProperty,quality) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramTrade.getTradeNo()), paramTrade.getDeliveryDate(), paramTrade.getTradeDate(), Double.valueOf(paramTrade.getQuantity()), Double.valueOf(paramTrade.getPrice()), Long.valueOf(paramTrade.getBreedId()), paramTrade.getCommodityProperties(), paramTrade.getFirmId_S(), paramTrade.getTraderId_S(), Double.valueOf(paramTrade.getTradeBail_S()), Double.valueOf(paramTrade.getTradePoundage_S()), Double.valueOf(paramTrade.getDeliveryPoundage_S()), Long.valueOf(paramTrade.getSubmitId_S()), Long.valueOf(paramTrade.getSubmitId_B()), paramTrade.getFirmId_B(), paramTrade.getTraderId_B(), Double.valueOf(paramTrade.getTradeBail_B()), Double.valueOf(paramTrade.getTradePoundage_B()), Double.valueOf(paramTrade.getDeliveryPoundage_B()), paramTrade.getIsRegstock(), paramTrade.getRegStockId(), paramTrade.getDeliveryPlace(), paramTrade.getNote(), paramTrade.getExpandProperty(), paramTrade.getQuality() };
    int[] arrayOfInt = { 2, 93, 93, 2, 2, 2, 12, 12, 12, 2, 2, 2, 2, 2, 12, 12, 2, 2, 2, 12, 12, 12, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
