package gnnt.MEBS.entity.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.entity.model.BreedProperty;
import java.util.List;
import org.apache.commons.logging.Log;

public class BreedPropertyDao
  extends DaoHelperImpl
{
  public List<BreedProperty> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
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
}
