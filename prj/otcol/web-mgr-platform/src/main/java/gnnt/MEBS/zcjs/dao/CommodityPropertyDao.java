package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.CommodityProperty;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommodityPropertyDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(CommodityPropertyDao.class);
  
  public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from Z_CommodityProperty ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List<CommodityProperty> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from Z_CommodityProperty";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new CommodityProperty()));
  }
  
  public void add(CommodityProperty paramCommodityProperty)
  {
    String str = "insert into Z_CommodityProperty (propertyId,propertyName,breedId,parentId,propertyDescription,key,status) values(?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramCommodityProperty.getPropertyId()), paramCommodityProperty.getPropertyName(), Long.valueOf(paramCommodityProperty.getBreedId()), Long.valueOf(paramCommodityProperty.getParentId()), paramCommodityProperty.getPropertyDescription(), paramCommodityProperty.getKey(), Integer.valueOf(paramCommodityProperty.getStatus()) };
    int[] arrayOfInt = { 2, 12, 2, 2, 12, 12, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void update(CommodityProperty paramCommodityProperty)
  {
    String str = "update Z_CommodityProperty set propertyName=?,breedId=?,parentId=?,propertyDescription=?,key=?,status=?  where propertyId=? ";
    Object[] arrayOfObject = { paramCommodityProperty.getPropertyName(), Long.valueOf(paramCommodityProperty.getBreedId()), Long.valueOf(paramCommodityProperty.getParentId()), paramCommodityProperty.getPropertyDescription(), paramCommodityProperty.getKey(), Integer.valueOf(paramCommodityProperty.getStatus()), Long.valueOf(paramCommodityProperty.getPropertyId()) };
    int[] arrayOfInt = { 12, 2, 2, 12, 12, 2, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public CommodityProperty getObject(long paramLong)
  {
    String str = "select * from Z_CommodityProperty where propertyId=?";
    Object[] arrayOfObject = { Long.valueOf(paramLong) };
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new CommodityProperty()));
    CommodityProperty localCommodityProperty = null;
    if ((localList.size() > 0) && (localList != null)) {
      localCommodityProperty = (CommodityProperty)localList.get(0);
    }
    return localCommodityProperty;
  }
}
