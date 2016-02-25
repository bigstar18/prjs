package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.CommodityRule;
import java.util.List;
import java.util.Map;

public class CommodityRuleDao
  extends DaoHelperImpl
{
  public void add(CommodityRule paramCommodityRule)
  {
    String str = "insert into Z_commodityRule (commodityRuleId,breedId,bail,bailMode,tradePoundage,tradePoundageMode,deliveryPoundage,deliveryPoundageMode,maxPrice,minPrice,commodityRuleStatus,commodityRuleFirmId,commodityRuleBusinessDirection) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramCommodityRule.getCommodityRuleId()), Long.valueOf(paramCommodityRule.getBreedId()), Double.valueOf(paramCommodityRule.getBail()), Integer.valueOf(paramCommodityRule.getBailMode()), Double.valueOf(paramCommodityRule.getTradePoundage()), Integer.valueOf(paramCommodityRule.getTradePoundageMode()), Double.valueOf(paramCommodityRule.getDeliveryPoundage()), Integer.valueOf(paramCommodityRule.getDeliveryPoundageMode()), Double.valueOf(paramCommodityRule.getMaxPrice()), Double.valueOf(paramCommodityRule.getMinPrice()), Integer.valueOf(paramCommodityRule.getCommodityRuleStatus()), paramCommodityRule.getCommodityRuleFirmId(), paramCommodityRule.getCommodityRuleBusinessDirection() };
    int[] arrayOfInt = { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void update(CommodityRule paramCommodityRule)
  {
    String str = "update Z_commodityRule set breedId=?,bail=?,bailMode=?,tradePoundage=?,tradePoundageMode=?,deliveryPoundage=?,deliveryPoundageMode=?,maxPrice=?,minPrice=?,commodityRuleStatus=?,commodityRuleFirmId=?,commodityRuleBusinessDirection=?  where commodityRuleId=? ";
    Object[] arrayOfObject = { Long.valueOf(paramCommodityRule.getBreedId()), Double.valueOf(paramCommodityRule.getBail()), Integer.valueOf(paramCommodityRule.getBailMode()), Double.valueOf(paramCommodityRule.getTradePoundage()), Integer.valueOf(paramCommodityRule.getTradePoundageMode()), Double.valueOf(paramCommodityRule.getDeliveryPoundage()), Integer.valueOf(paramCommodityRule.getDeliveryPoundageMode()), Double.valueOf(paramCommodityRule.getMaxPrice()), Double.valueOf(paramCommodityRule.getMinPrice()), Integer.valueOf(paramCommodityRule.getCommodityRuleStatus()), paramCommodityRule.getCommodityRuleFirmId(), paramCommodityRule.getCommodityRuleBusinessDirection(), Long.valueOf(paramCommodityRule.getCommodityRuleId()) };
    int[] arrayOfInt = { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 12, 12, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void delete(long paramLong)
  {
    String str = "delete from Z_commodityRule where commodityRuleId=" + paramLong + "";
    updateBySQL(str);
  }
  
  public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select COMMODITYRULEID,BREEDID,COMMODITYRULEFIRMID,COMMODITYRULEBUSINESSDIRECTION,(case when BAILMODE='1' then BAIL*100.00 else bail end) bail ,BAILMODE,(case when TRADEPOUNDAGEMODE='1' then TRADEPOUNDAGE*100.00 else TRADEPOUNDAGE end) TRADEPOUNDAGE,TRADEPOUNDAGEMODE,(case when DELIVERYPOUNDAGEMODE='1' then DELIVERYPOUNDAGE*100.00 else DELIVERYPOUNDAGE end) DELIVERYPOUNDAGE,DELIVERYPOUNDAGEMODE,MAXPRICE,MINPRICE,COMMODITYRULESTATUS from Z_commodityRule where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List<CommodityRule> getObjectList(QueryConditions paramQueryConditions)
  {
    String str = "select * from Z_commodityRule where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new CommodityRule()));
  }
  
  public CommodityRule getObject(long paramLong)
  {
    String str = "select * from Z_commodityRule where commodityRuleId=" + paramLong + "";
    CommodityRule localCommodityRule = new CommodityRule();
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new CommodityRule()));
    if ((localList != null) && (localList.size() > 0)) {
      localCommodityRule = (CommodityRule)localList.get(0);
    }
    return localCommodityRule;
  }
  
  public long getId()
  {
    long l = 0L;
    String str = "select SEQ_Z_commodityRule.nextVal from dual";
    l = queryForInt(str, null);
    return l;
  }
}
