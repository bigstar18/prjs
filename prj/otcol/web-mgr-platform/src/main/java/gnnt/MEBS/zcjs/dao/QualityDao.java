package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.Quality;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QualityDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(BroadcastDao.class);
  
  public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from (select b.*,q.qualityid qualityid,q.qualityname qualityname, q.status status from z_quality q, z_breed b where q.breedid = b.breedid)";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List<Quality> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from Z_Quality ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, null, new CommonRowMapper(new Quality()));
  }
  
  public long getId()
  {
    String str = "select seq_z_quality.nextVal from dual";
    return queryForLong(str);
  }
  
  public void add(Quality paramQuality)
  {
    String str = "insert into Z_Quality (QualityId,BreedId,QualityName,Status) values(?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramQuality.getQualityId()), Long.valueOf(paramQuality.getBreedId()), paramQuality.getQualityName(), Integer.valueOf(paramQuality.getStatus()) };
    int[] arrayOfInt = { 2, 2, 12, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void update(Quality paramQuality)
  {
    String str = "update Z_Quality set breedId=?,qualityName=?,status=?  where qualityId=? ";
    Object[] arrayOfObject = { Long.valueOf(paramQuality.getBreedId()), paramQuality.getQualityName(), Integer.valueOf(paramQuality.getStatus()), Long.valueOf(paramQuality.getQualityId()) };
    int[] arrayOfInt = { 2, 12, 2, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public Quality getObject(long paramLong)
  {
    String str = "select * from z_quality where QualityId=?";
    Object[] arrayOfObject = { Long.valueOf(paramLong) };
    int[] arrayOfInt = { 2 };
    Quality localQuality = null;
    List localList = queryBySQL(str, arrayOfObject, arrayOfInt, null, new CommonRowMapper(new Quality()));
    if ((localList != null) && (localList.size() > 0)) {
      localQuality = (Quality)localList.get(0);
    }
    return localQuality;
  }
}
