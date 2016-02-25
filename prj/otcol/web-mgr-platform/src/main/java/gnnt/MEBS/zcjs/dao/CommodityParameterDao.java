package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.CommodityParameter;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommodityParameterDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(CommodityParameterDao.class);
  
  public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select zcp.PropertyName ,zcp.Status,c.parameterid , c.commoditypropertyid  commoditypropertyid, c.parametername parametername,c.parameterdescription parameterdescription, c.parameterstatus  parameterstatus, b.* from z_commodityparameter c, z_breed b,z_commodityProperty zcp where c.breedid = b.breedid and zcp.PropertyId=c.commoditypropertyId)";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List<CommodityParameter> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from Z_CommodityParameter";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new CommodityParameter()));
  }
  
  public void add(CommodityParameter paramCommodityParameter)
  {
    String str = "insert into Z_CommodityParameter (parameterId,breedId,commodityPropertyId,parameterName,parameterDescription,parameterStatus) values(?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramCommodityParameter.getParameterId()), Long.valueOf(paramCommodityParameter.getBreedId()), Long.valueOf(paramCommodityParameter.getCommodityPropertyId()), paramCommodityParameter.getParameterName(), paramCommodityParameter.getParameterDescription(), Integer.valueOf(paramCommodityParameter.getParameterStatus()) };
    int[] arrayOfInt = { 2, 2, 2, 12, 12, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public long getId()
  {
    String str = "select seq_z_commodityparameter.nextVal from dual";
    return queryForLong(str);
  }
  
  public void update(CommodityParameter paramCommodityParameter)
  {
    String str = "update Z_CommodityParameter set breedId=?,commodityPropertyId=?,parameterName=?,parameterDescription=?,parameterStatus=?  where parameterId=? ";
    Object[] arrayOfObject = { Long.valueOf(paramCommodityParameter.getBreedId()), Long.valueOf(paramCommodityParameter.getCommodityPropertyId()), paramCommodityParameter.getParameterName(), paramCommodityParameter.getParameterDescription(), Integer.valueOf(paramCommodityParameter.getParameterStatus()), Long.valueOf(paramCommodityParameter.getParameterId()) };
    int[] arrayOfInt = { 2, 2, 12, 12, 2, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void delete(CommodityParameter paramCommodityParameter)
  {
    String str = "delete from Z_commodityParameter where parameterId=?";
    Object[] arrayOfObject = { Long.valueOf(paramCommodityParameter.getParameterId()) };
    updateBySQL(str, arrayOfObject);
  }
  
  public CommodityParameter getObject(long paramLong)
  {
    String str = "select * from z_commodityParameter where parameterId=?";
    Object[] arrayOfObject = { Long.valueOf(paramLong) };
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new CommodityParameter()));
    CommodityParameter localCommodityParameter = null;
    if ((localList.size() > 0) && (localList != null)) {
      localCommodityParameter = (CommodityParameter)localList.get(0);
    }
    return localCommodityParameter;
  }
}
