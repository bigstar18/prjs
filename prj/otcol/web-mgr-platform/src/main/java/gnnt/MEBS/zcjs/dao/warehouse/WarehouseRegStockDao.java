package gnnt.MEBS.zcjs.dao.warehouse;

import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.Delivery;
import gnnt.MEBS.zcjs.model.WarehouseRegStock;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class WarehouseRegStockDao
  extends DaoHelper
{
  public List<Map<String, Object>> getWarehouseTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select s.*,we.origin,we.grade,we.sort,we.qualityStandard,wc.name from S_RegStock s,w_commodity wc,w_enter_ware we where  wc.id=s.BreedID and s.stockId=we.id)  where BreedID in (select breedID from z_breed)";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    System.out.println("sql: ---------  " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public int updateRegstockFrozen(String paramString1, double paramDouble, String paramString2, int paramInt)
  {
    UpdateRegstockFrozen localUpdateRegstockFrozen = new UpdateRegstockFrozen(getDataSource());
    HashMap localHashMap = new HashMap();
    localHashMap.put("p_regStockID", paramString1);
    localHashMap.put("p_frozenWeight", Double.valueOf(paramDouble));
    localHashMap.put("p_sign", Integer.valueOf(paramInt));
    localHashMap.put("p_operator", paramString2);
    localHashMap.put("p_module", "3");
    Map localMap = localUpdateRegstockFrozen.execute(localHashMap);
    int i = ((BigDecimal)localMap.get("ret")).intValue();
    return i;
  }
  
  public int createSettleMatch(Delivery paramDelivery, String paramString)
  {
    CreateSettleMatch localCreateSettleMatch = new CreateSettleMatch(getDataSource());
    HashMap localHashMap = new HashMap();
    localHashMap.put("p_moduleid", Integer.valueOf(3));
    localHashMap.put("p_breedid", Long.valueOf(paramDelivery.getBreedId()));
    localHashMap.put("p_weight", Double.valueOf(paramDelivery.getQuantity()));
    localHashMap.put("p_result", Integer.valueOf(paramDelivery.getType()));
    localHashMap.put("p_commodityId", "");
    localHashMap.put("p_firmID_B", paramDelivery.getFirmId_b());
    localHashMap.put("p_buyPrice", Double.valueOf(paramDelivery.getBuyMargin()));
    localHashMap.put("p_buyPrice", Double.valueOf(paramDelivery.getPrice()));
    localHashMap.put("p_buyMargin", Double.valueOf(paramDelivery.getBuyMargin()));
    localHashMap.put("p_buyPayout", Integer.valueOf(0));
    localHashMap.put("p_firmID_S", paramDelivery.getFirmId_s());
    localHashMap.put("p_sellPrice", Double.valueOf(paramDelivery.getPrice()));
    localHashMap.put("p_sellMargin", Double.valueOf(paramDelivery.getSellMargin()));
    localHashMap.put("p_regStockID", paramDelivery.getRegStockId());
    localHashMap.put("p_contractId", Long.valueOf(paramDelivery.getTradeNo()));
    localHashMap.put("p_xml", "");
    localHashMap.put("p_operator", paramString);
    localHashMap.put("p_module", Integer.valueOf(3));
    Map localMap = localCreateSettleMatch.execute(localHashMap);
    int i = ((BigDecimal)localMap.get("ret")).intValue();
    return i;
  }
  
  public void addRegStock(WarehouseRegStock paramWarehouseRegStock)
  {
    String str = "insert into S_RegStock (RegStockID,OldRegStockID,BreedID,FirmID,WarehouseID,StockID,initWeight,weight,frozenWeight,unitWeight,type,CreateTime,ModifyTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramWarehouseRegStock.getRegStockId(), "", Long.valueOf(paramWarehouseRegStock.getBreedId()), paramWarehouseRegStock.getFirmId(), paramWarehouseRegStock.getWarehouseId(), paramWarehouseRegStock.getStockId(), Double.valueOf(paramWarehouseRegStock.getInitWeight()), Double.valueOf(paramWarehouseRegStock.getWeight()), Double.valueOf(paramWarehouseRegStock.getFrozenWeight()), Double.valueOf(paramWarehouseRegStock.getUnitWeight()), Integer.valueOf(paramWarehouseRegStock.getType()), paramWarehouseRegStock.getCreateTime(), paramWarehouseRegStock.getModifyTime() };
    int[] arrayOfInt = { 12, 12, 2, 12, 12, 12, 2, 2, 2, 2, 2, 93, 93 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public String getRegStockId()
  {
    String str1 = "";
    String str2 = "select SEQ_S_RegStock.nextVal from dual";
    str1 = queryForInt(str2, null) + "";
    return str1;
  }
  
  public void deleteRegstock(String paramString)
  {
    String str = "delete from S_RegStock where RegStockID=?";
    Object[] arrayOfObject = { paramString };
    updateBySQL(str, arrayOfObject);
  }
  
  private class CreateSettleMatch
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_S_createSettleMatch";
    
    public CreateSettleMatch(DataSource paramDataSource)
    {
      super("FN_S_createSettleMatch");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_moduleid", 12));
      declareParameter(new SqlParameter("p_breedid", 2));
      declareParameter(new SqlParameter("p_weight", 2));
      declareParameter(new SqlParameter("p_result", 2));
      declareParameter(new SqlParameter("p_commodityId", 12));
      declareParameter(new SqlParameter("p_firmID_B", 12));
      declareParameter(new SqlParameter("p_buyPrice", 2));
      declareParameter(new SqlParameter("p_buyMargin", 2));
      declareParameter(new SqlParameter("p_buyPayout", 2));
      declareParameter(new SqlParameter("p_firmID_S", 12));
      declareParameter(new SqlParameter("p_sellPrice", 2));
      declareParameter(new SqlParameter("p_sellMargin", 2));
      declareParameter(new SqlParameter("p_regStockID", 12));
      declareParameter(new SqlParameter("p_contractId", 2));
      declareParameter(new SqlParameter("p_xml", 12));
      declareParameter(new SqlParameter("p_operator", 12));
      declareParameter(new SqlParameter("p_module", 12));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
    }
  }
  
  private class UpdateRegstockFrozen
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_S_updateRegstockFrozen";
    
    public UpdateRegstockFrozen(DataSource paramDataSource)
    {
      super("FN_S_updateRegstockFrozen");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_regStockID", 12));
      declareParameter(new SqlParameter("p_frozenWeight", 2));
      declareParameter(new SqlParameter("p_sign", 2));
      declareParameter(new SqlParameter("p_operator", 12));
      declareParameter(new SqlParameter("p_module", 12));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
    }
  }
}
