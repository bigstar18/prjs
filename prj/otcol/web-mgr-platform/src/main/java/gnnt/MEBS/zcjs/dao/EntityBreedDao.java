package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.EntityBreed;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EntityBreedDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(EntityBreedDao.class);
  
  public void add(EntityBreed paramEntityBreed)
  {
    String str = "insert into E_Breed (breedId,breedName,status) values(?,?,?)";
    Object[] arrayOfObject = { paramEntityBreed.getBreedId(), paramEntityBreed.getBreedName(), Integer.valueOf(paramEntityBreed.getStatus()) };
    int[] arrayOfInt = { 12, 12, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from e_breed";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List<EntityBreed> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from e_breed";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new EntityBreed()));
  }
  
  public EntityBreed getObject(String paramString)
  {
    String str = "select * from e_breed where breedId=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    List localList = queryBySQL(str, arrayOfObject, arrayOfInt, null, new CommonRowMapper(new EntityBreed()));
    EntityBreed localEntityBreed = null;
    if ((localList.size() > 0) && (localList != null)) {
      localEntityBreed = (EntityBreed)localList.get(0);
    }
    return localEntityBreed;
  }
  
  public List getEntityParametersTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
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
  
  public List getEntityQualityTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from E_BreedQuality";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
}
