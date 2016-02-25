package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.workflow.RegStockToEnterWare;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RegStockToEnterWareDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(EnterWareDao.class);
  
  public List getRegStockToEnterWareList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select t.*,c.name commodityName,c.countType countType,h.name wareHouseName,s.name statusName from s_regStockRollback t,w_commodity c, w_warehouse h,w_status s where t.breedId = c.id(+) and t.warehouseid = h.id(+) and t.status = s.value and s.kind = 'RegStockToEnterWare')";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public void updateRegStockToEnterWare(RegStockToEnterWare paramRegStockToEnterWare)
  {
    String str = "update s_regStockRollback set regStockId=?,breedId=?,firmId=?,warehouseId=?,status=?,stockId=?,type=?,createDate=?,modifyTime=?,rejectedReasons=?,relTurnToWeight=? where id=?";
    Object[] arrayOfObject = { paramRegStockToEnterWare.getRegStockId(), paramRegStockToEnterWare.getBreedId(), paramRegStockToEnterWare.getFirmId(), paramRegStockToEnterWare.getWarehouseId(), Integer.valueOf(paramRegStockToEnterWare.getStatus()), paramRegStockToEnterWare.getStockId(), Integer.valueOf(paramRegStockToEnterWare.getType()), paramRegStockToEnterWare.getCreateDate(), paramRegStockToEnterWare.getModifyTime(), paramRegStockToEnterWare.getRejectedReasons(), Double.valueOf(paramRegStockToEnterWare.getRelTurnToWeight()), paramRegStockToEnterWare.getId() };
    int[] arrayOfInt = { 12, 12, 12, 12, 4, 12, 4, 93, 93, 12, 12, 8 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public RegStockToEnterWare getRegStockToEnterWareLock(String paramString)
  {
    RegStockToEnterWare localRegStockToEnterWare = new RegStockToEnterWare();
    String str = "select * from s_regStockRollback where id='" + paramString + "' for update";
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new RegStockToEnterWare()));
    if ((localList != null) && (localList.size() > 0)) {
      localRegStockToEnterWare = (RegStockToEnterWare)localList.get(0);
    }
    return localRegStockToEnterWare;
  }
  
  public List getRegStockToEnterWares(QueryConditions paramQueryConditions)
  {
    String str = "select * from s_regStockRollback where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new RegStockToEnterWare()));
  }
  
  public String getRegStockToEnterWareId()
  {
    String str1 = "";
    String str2 = "select SEQ_W_REGSTOCKTOENTERWARE.nextVal from dual";
    str1 = queryForInt(str2, null) + "";
    return str1;
  }
  
  public void addRegStockToEnterWare(RegStockToEnterWare paramRegStockToEnterWare)
  {
    String str = "insert into s_regstockrollback (id,regStockId,breedId,firmId,warehouseId,status,stockId,Type,ModifyTime,createDate,rejectedReasons,relTurnToWeight)values (?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramRegStockToEnterWare.getId(), paramRegStockToEnterWare.getRegStockId(), paramRegStockToEnterWare.getBreedId(), paramRegStockToEnterWare.getFirmId(), paramRegStockToEnterWare.getWarehouseId(), Integer.valueOf(paramRegStockToEnterWare.getStatus()), paramRegStockToEnterWare.getStockId(), Integer.valueOf(paramRegStockToEnterWare.getType()), paramRegStockToEnterWare.getModifyTime(), paramRegStockToEnterWare.getCreateDate(), paramRegStockToEnterWare.getRejectedReasons(), Double.valueOf(paramRegStockToEnterWare.getRelTurnToWeight()) };
    int[] arrayOfInt = { 12, 12, 12, 12, 12, 4, 12, 4, 93, 93, 12, 8 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
