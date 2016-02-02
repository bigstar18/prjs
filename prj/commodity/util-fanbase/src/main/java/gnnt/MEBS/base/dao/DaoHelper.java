package gnnt.MEBS.base.dao;

import gnnt.MEBS.base.util.PageInfo;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;

public abstract interface DaoHelper
{
  public abstract List queryBySQL(String paramString);
  
  public abstract List queryBySQL(String paramString, PageInfo paramPageInfo);
  
  public abstract List queryBySQL(String paramString, Object[] paramArrayOfObject, PageInfo paramPageInfo);
  
  public abstract List queryBySQL(String paramString, Object[] paramArrayOfObject, int[] paramArrayOfInt, PageInfo paramPageInfo);
  
  public abstract List queryBySQL(String paramString, Object[] paramArrayOfObject, PageInfo paramPageInfo, RowMapper paramRowMapper);
  
  public abstract List queryBySQL(String paramString, Object[] paramArrayOfObject, int[] paramArrayOfInt, PageInfo paramPageInfo, RowMapper paramRowMapper);
  
  public abstract int queryForInt(String paramString, Object[] paramArrayOfObject);
  
  public abstract Object queryForObject(String paramString, Object[] paramArrayOfObject, Class paramClass);
  
  public abstract void updateBySQL(String paramString);
  
  public abstract void updateBySQL(String paramString, Object[] paramArrayOfObject, int[] paramArrayOfInt);
  
  public abstract void executeStoredSubprogram(String paramString);
  
  public abstract int callStoredProcedure(String paramString);
  
  public abstract DataSource getDataSource();
}
