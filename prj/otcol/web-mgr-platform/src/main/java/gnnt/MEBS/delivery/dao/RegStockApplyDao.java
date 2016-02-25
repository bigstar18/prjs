package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.workflow.RegStockApply;
import java.util.List;
import org.apache.commons.logging.Log;

public class RegStockApplyDao
  extends DaoHelperImpl
{
  public void addRegStockApply(RegStockApply paramRegStockApply)
  {
    String str = "insert into S_RegStockApply (applyID,breedID,firmId,warehouseId,stockID,weight,UnitWeight,status,regStockID,applicant,applyTime,auditor,auditTime,type,rejectedreasons) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramRegStockApply.getApplyId(), Long.valueOf(paramRegStockApply.getBreedId()), paramRegStockApply.getFirmId(), paramRegStockApply.getWarehouseId(), paramRegStockApply.getStockId(), Double.valueOf(paramRegStockApply.getWeight()), Double.valueOf(paramRegStockApply.getUnitWeight()), Integer.valueOf(paramRegStockApply.getStatus()), paramRegStockApply.getRegStockId(), paramRegStockApply.getApplicant(), paramRegStockApply.getApplyTime(), paramRegStockApply.getAuditor(), paramRegStockApply.getAuditTime(), Integer.valueOf(paramRegStockApply.getType()), paramRegStockApply.getRejectedReasons() };
    int[] arrayOfInt = { 2, 2, 12, 12, 12, 2, 2, 2, 12, 12, 93, 12, 93, 2, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updateRegStockApply(RegStockApply paramRegStockApply)
  {
    String str = "update S_RegStockApply set breedID=?,firmId=?,warehouseId=?,stockID=?,weight=?,UnitWeight=?,status=?,regStockID=?,applicant=?,applyTime=?,auditor=?,auditTime=?,type=?,rejectedReasons=? where applyID=?";
    Object[] arrayOfObject = { Long.valueOf(paramRegStockApply.getBreedId()), paramRegStockApply.getFirmId(), paramRegStockApply.getWarehouseId(), paramRegStockApply.getStockId(), Double.valueOf(paramRegStockApply.getWeight()), Double.valueOf(paramRegStockApply.getUnitWeight()), Integer.valueOf(paramRegStockApply.getStatus()), paramRegStockApply.getRegStockId(), paramRegStockApply.getApplicant(), paramRegStockApply.getApplyTime(), paramRegStockApply.getAuditor(), paramRegStockApply.getAuditTime(), Integer.valueOf(paramRegStockApply.getType()), paramRegStockApply.getRejectedReasons(), paramRegStockApply.getApplyId() };
    int[] arrayOfInt = { 2, 12, 12, 12, 2, 2, 2, 12, 12, 93, 12, 93, 2, 12, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public RegStockApply getRegStockApplyLock(String paramString)
  {
    RegStockApply localRegStockApply = new RegStockApply();
    String str = "select * from S_RegStockApply where applyID=" + paramString + " for update";
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new RegStockApply()));
    if ((localList != null) && (localList.size() > 0)) {
      localRegStockApply = (RegStockApply)localList.get(0);
    }
    return localRegStockApply;
  }
  
  public String getRegStockApplyId()
  {
    String str1 = "";
    String str2 = "select SEQ_s_RegStockApply.nextVal from dual";
    str1 = queryForInt(str2, null) + "";
    return str1;
  }
  
  public List getRegStockApplyList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select s.*, c.name commodityName, w.name warehouseName from s_regstockapply s, w_warehouse w, w_commodity c  where s.breedid||'' = c.id(+) and s.warehouseid = w.id(+))";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("---------sql:-- " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public RegStockApply getRegStockApplyForUpdate(String paramString)
  {
    RegStockApply localRegStockApply = new RegStockApply();
    String str = "select * from s_regstockapply where ApplyID='" + paramString + "' for update";
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new RegStockApply()));
    if ((localList != null) && (localList.size() > 0)) {
      localRegStockApply = (RegStockApply)localList.get(0);
    }
    return localRegStockApply;
  }
  
  public List getRegStockApplys(QueryConditions paramQueryConditions)
  {
    String str = "select * from s_regstockapply where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new RegStockApply()));
  }
}
