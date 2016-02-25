package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.Warehouse;
import gnnt.MEBS.delivery.model.inner.WarehouseRelateCommodity;
import java.util.List;
import org.apache.commons.logging.Log;

public class WarehouseDao
  extends DaoHelperImpl
{
  public List<Warehouse> getWarehouses(QueryConditions paramQueryConditions)
  {
    String str = "select w.* from w_warehouse w where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new Warehouse()));
  }
  
  public List getWarehouseList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select w.*,s.name status from w_warehouse w,w_status s where s.kind='Warehouse' and s.value=w.ability)";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public void getWarehouseLock(Warehouse paramWarehouse)
  {
    String str = "select * from w_warehouse where id='" + paramWarehouse.getId() + "' for update";
    this.logger.debug(str);
    queryBySQL(str);
  }
  
  public void addWarehouse(Warehouse paramWarehouse)
  {
    String str = "insert into w_Warehouse(Id,Name,fullName,Ability,Address,linkman,Tel,Fax,Email,Postcode,Max_Capacity,Used_Capacity,Bail) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramWarehouse.getId(), paramWarehouse.getName(), paramWarehouse.getFullName(), Integer.valueOf(paramWarehouse.getAbility()), paramWarehouse.getAddress(), paramWarehouse.getLinkman(), paramWarehouse.getTel(), paramWarehouse.getFax(), paramWarehouse.getEmail(), paramWarehouse.getPostcode(), Double.valueOf(paramWarehouse.getMax_Capacity()), Double.valueOf(paramWarehouse.getUsed_Capacity()), Double.valueOf(paramWarehouse.getBail()) };
    int[] arrayOfInt = { 12, 12, 12, 4, 12, 12, 12, 12, 12, 12, 8, 8, 8 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updateWarehouse(Warehouse paramWarehouse)
  {
    String str = "update w_warehouse set name=?,fullName=?,ability=?,Address=?,linkman=?,Tel=?,Fax=?,Email=?,Postcode=?,Max_Capacity=?,Used_Capacity=?,Bail=? where id=?";
    Object[] arrayOfObject = { paramWarehouse.getName(), paramWarehouse.getFullName(), Integer.valueOf(paramWarehouse.getAbility()), paramWarehouse.getAddress(), paramWarehouse.getLinkman(), paramWarehouse.getTel(), paramWarehouse.getFax(), paramWarehouse.getEmail(), paramWarehouse.getPostcode(), Double.valueOf(paramWarehouse.getMax_Capacity()), Double.valueOf(paramWarehouse.getUsed_Capacity()), Double.valueOf(paramWarehouse.getBail()), paramWarehouse.getId() };
    int[] arrayOfInt = { 12, 12, 4, 12, 12, 12, 12, 12, 12, 8, 8, 8, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deleteWarehouse(QueryConditions paramQueryConditions)
  {
    String str = "delete from w_warehouse";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    updateBySQL(str, arrayOfObject);
  }
  
  public List<WarehouseRelateCommodity> getWarehouseRelateCommodity(Warehouse paramWarehouse)
  {
    String str = "select * from w_commoditywarehouserelated where warehouseId=? order by commodityId";
    Object[] arrayOfObject = { paramWarehouse.getId() };
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new WarehouseRelateCommodity()));
    return localList;
  }
  
  public void relateCommodityWarehouse(WarehouseRelateCommodity paramWarehouseRelateCommodity)
  {
    String str = "insert into w_commoditywarehouserelated values (?,?)";
    Object[] arrayOfObject = { paramWarehouseRelateCommodity.getCommodityId(), paramWarehouseRelateCommodity.getWarehouseId() };
    int[] arrayOfInt = { 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deleteRelateCommodityWarehouse(QueryConditions paramQueryConditions)
  {
    String str = "delete from w_commoditywarehouserelated";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    updateBySQL(str, arrayOfObject);
  }
  
  public void deleteRelateCommodityWarehouse(Warehouse paramWarehouse)
  {
    String str = "delete from w_commoditywarehouserelated where warehouseId=?";
    Object[] arrayOfObject = { paramWarehouse.getId() };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public int deleteWareHouseById(String paramString)
  {
    int i = 0;
    String str = "update w_warehouse set ability = 1 where id = ?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    this.logger.debug(str);
    updateBySQL(str, arrayOfObject, arrayOfInt);
    return i;
  }
  
  public int deleteGoResumeWareHouseById(String paramString)
  {
    int i = 0;
    String str = "update w_warehouse set ability = 0 where id = ?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    this.logger.debug("warehouseId: " + paramString);
    this.logger.debug(str);
    updateBySQL(str, arrayOfObject, arrayOfInt);
    return i;
  }
  
  public boolean getEnterApply(String paramString)
  {
    String str = "select w.*  from w_enter_apply w where warehouseid = '" + paramString + "'";
    List localList = queryBySQL(str);
    boolean bool = false;
    if ((localList != null) && (localList.size() > 0)) {
      bool = true;
    }
    return bool;
  }
  
  public boolean getEnterWare(String paramString)
  {
    String str = "select w.* from w_enter_ware w where warehouseid = '" + paramString + "'";
    List localList = queryBySQL(str);
    boolean bool = false;
    if ((localList != null) && (localList.size() > 0)) {
      bool = true;
    }
    return bool;
  }
  
  public boolean getStandardWare(String paramString)
  {
    String str = "select s.* from S_RegStock s where warehouseid is not null and warehouseid = '" + paramString + "'";
    List localList = queryBySQL(str);
    boolean bool = false;
    if ((localList != null) && (localList.size() > 0)) {
      bool = true;
    }
    return bool;
  }
}
