package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.Sort;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SortDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(BreedDao.class);
  
  public List<Map<String, Object>> getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from Z_sort";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List<Sort> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from z_sort";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new Sort()));
  }
  
  public void insert(Sort paramSort)
  {
    String str = "insert into Z_sort (sortId,sortName) values(?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramSort.getSortId()), paramSort.getSortName() };
    int[] arrayOfInt = { 2, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void update(Sort paramSort)
  {
    String str = "update Z_sort set sortName=?  where sortId=? ";
    Object[] arrayOfObject = { paramSort.getSortName(), Long.valueOf(paramSort.getSortId()) };
    int[] arrayOfInt = { 12, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void delete(long paramLong)
  {
    String str = "delete from z_sort where sortId=?";
    Object[] arrayOfObject = { Long.valueOf(paramLong) };
    int[] arrayOfInt = { 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public long getId()
  {
    long l = 0L;
    String str = "select SEQ_Z_sort.nextVal from dual";
    l = queryForInt(str, null);
    return l;
  }
  
  public Sort getObject(long paramLong)
  {
    String str = "select * from z_sort where sortId=" + paramLong;
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new Sort()));
    Sort localSort = null;
    if ((localList != null) && (localList.size() > 0)) {
      localSort = (Sort)localList.get(0);
    }
    return localSort;
  }
}
