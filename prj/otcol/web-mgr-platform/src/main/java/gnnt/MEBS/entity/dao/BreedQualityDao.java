package gnnt.MEBS.entity.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.entity.model.BreedQuality;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BreedQualityDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(BreedQualityDao.class);
  
  public void add(BreedQuality paramBreedQuality)
  {
    String str = "insert into E_BreedQuality (breedId,QualityName,No) values(?,?,?)";
    Object[] arrayOfObject = { paramBreedQuality.getBreedId(), paramBreedQuality.getQualityName(), Integer.valueOf(paramBreedQuality.getNo()) };
    int[] arrayOfInt = { 12, 12, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
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
  
  public List<BreedQuality> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
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
  
  public void delete(QueryConditions paramQueryConditions)
  {
    String str = "delete from E_BreedQuality";
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
