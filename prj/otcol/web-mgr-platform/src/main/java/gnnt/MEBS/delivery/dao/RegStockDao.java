package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import java.util.List;
import org.apache.commons.logging.Log;

public class RegStockDao
  extends DaoHelperImpl
{
  public void addRegStock(RegStock paramRegStock)
  {
    String str = "insert into S_RegStock (RegStockID,OldRegStockID,BreedID,FirmID,WarehouseID,StockID,initWeight,weight,frozenWeight,unitWeight,type,CreateTime,ModifyTime) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramRegStock.getRegStockId(), paramRegStock.getOldRegStockId(), Long.valueOf(paramRegStock.getBreedId()), paramRegStock.getFirmId(), paramRegStock.getWarehouseId(), paramRegStock.getStockId(), Double.valueOf(paramRegStock.getInitWeight()), Double.valueOf(paramRegStock.getWeight()), Double.valueOf(paramRegStock.getFrozenWeight()), Double.valueOf(paramRegStock.getUnitWeight()), Integer.valueOf(paramRegStock.getType()), paramRegStock.getCreateTime(), paramRegStock.getModifyTime() };
    int[] arrayOfInt = { 12, 12, 2, 12, 12, 12, 2, 2, 2, 2, 2, 93, 93 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updateRegStock(RegStock paramRegStock)
  {
    String str = "update S_RegStock set OldRegStockID=?,BreedID=?,FirmID=?,WarehouseID=?,StockID=?,initWeight=?,weight=?,frozenWeight=?,unitWeight=?,type=?,CreateTime=?,ModifyTime=? where RegStockID=?";
    Object[] arrayOfObject = { paramRegStock.getOldRegStockId(), Long.valueOf(paramRegStock.getBreedId()), paramRegStock.getFirmId(), paramRegStock.getWarehouseId(), paramRegStock.getStockId(), Double.valueOf(paramRegStock.getInitWeight()), Double.valueOf(paramRegStock.getWeight()), Double.valueOf(paramRegStock.getFrozenWeight()), Double.valueOf(paramRegStock.getUnitWeight()), Integer.valueOf(paramRegStock.getType()), paramRegStock.getCreateTime(), paramRegStock.getModifyTime(), paramRegStock.getRegStockId() };
    int[] arrayOfInt = { 12, 2, 12, 12, 12, 2, 2, 2, 2, 2, 93, 93, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public String getRegStockId()
  {
    String str1 = "";
    String str2 = "select SEQ_S_RegStock.nextVal from dual";
    str1 = queryForInt(str2, null) + "";
    return str1;
  }
  
  public List getRegStockList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select s.*,w.name warehouseName, c.name commodityName from s_Regstock s, w_warehouse w, w_commodity c where s.warehouseid = w.id(+) and s.breedid||'' = c.id(+))";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public RegStock getRegStockForUpdate(String paramString)
  {
    RegStock localRegStock = null;
    String str = "select * from S_RegStock where RegStockID='" + paramString + "' for update";
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new RegStock()));
    if ((localList != null) && (localList.size() > 0)) {
      localRegStock = (RegStock)localList.get(0);
    }
    return localRegStock;
  }
  
  public List getRegStocks(QueryConditions paramQueryConditions)
  {
    String str = "select * from S_RegStock where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new RegStock()));
  }
}
