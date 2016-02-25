package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.FirmPermission;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FirmPermissionDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(FirmPermissionDao.class);
  
  public List<FirmPermission> getList()
  {
    String str = "select * from z_firmpermission";
    return queryBySQL(str, null, null, new CommonRowMapper(new FirmPermission()));
  }
  
  public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from z_firmpermission";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public FirmPermission getFirmPermissionById(String paramString)
  {
    String str = "select * from z_firmpermission where firmId=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    List localList = queryBySQL(str, arrayOfObject, arrayOfInt, null, new CommonRowMapper(new FirmPermission()));
    FirmPermission localFirmPermission = null;
    if ((localList.size() > 0) && (localList != null)) {
      localFirmPermission = (FirmPermission)localList.get(0);
    }
    return localFirmPermission;
  }
  
  public void update(FirmPermission paramFirmPermission)
  {
    String str = "update z_firmpermission set BuyDelist=?,SellDelist=?,BuyListing=?,SellListing=?,SellRegstock=? where firmId=?";
    Object[] arrayOfObject = { paramFirmPermission.getBuyDelist(), paramFirmPermission.getSellDelist(), paramFirmPermission.getBuyListing(), paramFirmPermission.getSellListing(), paramFirmPermission.getSellRegstock(), paramFirmPermission.getFirmId() };
    int[] arrayOfInt = { 12, 12, 12, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
