package gnnt.MEBS.delivery.dao.entity;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.DaoHelperImpl;
import gnnt.MEBS.delivery.model.entity.Breed;
import gnnt.MEBS.delivery.model.entity.BreedParameters;
import gnnt.MEBS.delivery.model.entity.BreedProperty;
import gnnt.MEBS.delivery.model.entity.BreedQuality;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EntityBreedDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(EntityBreedDao.class);
  
  public List<Breed> getEntityBreedList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from e_breed";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new Breed()));
  }
  
  public Breed getEntityBreed(String paramString)
  {
    String str = "select * from e_breed where breedId=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    List localList = queryBySQL(str, arrayOfObject, arrayOfInt, null, new CommonRowMapper(new Breed()));
    Breed localBreed = null;
    if ((localList.size() > 0) && (localList != null)) {
      localBreed = (Breed)localList.get(0);
    }
    return localBreed;
  }
  
  public List<BreedParameters> getEntityParameterList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
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
  
  public List<BreedProperty> getEntityPropertyList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from E_BreedProperty";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new BreedProperty()));
  }
  
  public List<BreedQuality> getEntityQualityList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from E_BreedQuality";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new BreedQuality()));
  }
}
