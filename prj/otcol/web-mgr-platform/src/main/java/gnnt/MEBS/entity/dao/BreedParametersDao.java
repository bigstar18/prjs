package gnnt.MEBS.entity.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.entity.model.BreedParameters;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BreedParametersDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(BreedParametersDao.class);
  
  public void add(BreedParameters paramBreedParameters)
  {
    String str = "insert into E_BreedParameters (breedId,PropertyKey,Name,No) values(?,?,?,?)";
    Object[] arrayOfObject = { paramBreedParameters.getBreedId(), paramBreedParameters.getPropertyKey(), paramBreedParameters.getName(), Integer.valueOf(paramBreedParameters.getNo()) };
    int[] arrayOfInt = { 12, 12, 12, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from E_BreedParameters";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List<BreedParameters> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from E_BreedParameters";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new BreedParameters()));
  }
  
  public void delete(QueryConditions paramQueryConditions)
  {
    String str = "delete from E_BreedParameters";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    updateBySQL(str, arrayOfObject);
  }
}
