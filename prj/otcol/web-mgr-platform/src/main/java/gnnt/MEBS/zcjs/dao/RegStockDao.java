package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.ValidRegstock;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RegStockDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(RegStockDao.class);
  
  public ValidRegstock getObject(String paramString)
  {
    String str = "select * from z_validregstock where regstockid='" + paramString + "'  for update";
    ValidRegstock localValidRegstock = null;
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new ValidRegstock()));
    if ((localList != null) && (localList.size() > 0)) {
      localValidRegstock = (ValidRegstock)localList.get(0);
    }
    return localValidRegstock;
  }
  
  public List<ValidRegstock> getRegStockList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from Z_ValidRegstock";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new ValidRegstock()));
  }
  
  public int mod(ValidRegstock paramValidRegstock)
  {
    try
    {
      String str = "update z_validregstock set BreedId=?,CommodityProperties=?,Quality=?,Quantity=?,Status=?,firmId=? where RegstockId=? ";
      Object[] arrayOfObject = { Long.valueOf(paramValidRegstock.getBreedId()), paramValidRegstock.getCommodityProperties(), paramValidRegstock.getQuality(), Double.valueOf(paramValidRegstock.getQuantity()), Integer.valueOf(paramValidRegstock.getStatus()), paramValidRegstock.getFirmId(), paramValidRegstock.getRegstockId() };
      int[] arrayOfInt = { 2, 12, 12, 2, 2, 12, 12 };
      updateBySQL(str, arrayOfObject, arrayOfInt);
      return 1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from Z_ValidRegstock";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public void add(ValidRegstock paramValidRegstock)
  {
    String str = "insert into Z_ValidRegstock (regstockId,breedId,commodityProperties,quality,quantity,status,firmid) values(?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramValidRegstock.getRegstockId(), Long.valueOf(paramValidRegstock.getBreedId()), paramValidRegstock.getCommodityProperties(), paramValidRegstock.getQuality(), Double.valueOf(paramValidRegstock.getQuantity()), Integer.valueOf(paramValidRegstock.getStatus()), paramValidRegstock.getFirmId() };
    int[] arrayOfInt = { 12, 2, 12, 12, 2, 2, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void delete(String paramString)
  {
    String str = "delete from Z_ValidRegstock where regstockId=?";
    Object[] arrayOfObject = { paramString };
    updateBySQL(str, arrayOfObject);
  }
  
  public List getObjectAndBreedName(String paramString)
  {
    String str = "select * from (select z.*,zb.breedname name from z_validregstock z,Z_Breed zb where z.breedId=zb.breedId and z.regstockid='" + paramString + "')";
    List localList = queryBySQL(str, null, null);
    return localList;
  }
  
  public void update(ValidRegstock paramValidRegstock)
  {
    String str = "update Z_ValidRegstock set firmId=?,breedId=?,commodityProperties=?,quality=?,quantity=?,status=?,type=? where regstockId=? ";
    Object[] arrayOfObject = { paramValidRegstock.getFirmId(), Long.valueOf(paramValidRegstock.getBreedId()), paramValidRegstock.getCommodityProperties(), paramValidRegstock.getQuality(), Double.valueOf(paramValidRegstock.getQuantity()), Integer.valueOf(paramValidRegstock.getStatus()), Integer.valueOf(paramValidRegstock.getType()), paramValidRegstock.getRegstockId() };
    int[] arrayOfInt = { 12, 2, 12, 12, 2, 2, 2, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
