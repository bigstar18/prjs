package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.ProsceniumShow;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProsceniumShowDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(ProsceniumShowDao.class);
  
  public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from Z_ProsceniumShow";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List<ProsceniumShow> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from Z_ProsceniumShow";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    str = str + " order by SerialNumber";
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new ProsceniumShow()));
  }
  
  public void update(ProsceniumShow paramProsceniumShow)
  {
    String str = "update Z_ProsceniumShow set showName=?,nodeKey=?,serialNumber=?,type=?,renderer=?,isShow=? where prosceniumApplication=? and showProperty=?";
    Object[] arrayOfObject = { paramProsceniumShow.getShowName(), paramProsceniumShow.getNodeKey(), Integer.valueOf(paramProsceniumShow.getSerialNumber()), paramProsceniumShow.getType(), paramProsceniumShow.getRenderer(), paramProsceniumShow.getIsShow(), paramProsceniumShow.getProsceniumApplication(), paramProsceniumShow.getShowProperty() };
    int[] arrayOfInt = { 12, 12, 2, 12, 12, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public ProsceniumShow getObject(String paramString1, String paramString2)
  {
    String str = "select * from Z_ProsceniumShow where prosceniumApplication=? and showProperty=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    int[] arrayOfInt = { 12, 12 };
    ProsceniumShow localProsceniumShow = null;
    List localList = queryBySQL(str, arrayOfObject, arrayOfInt, null, new CommonRowMapper(new ProsceniumShow()));
    if (localList.size() > 0) {
      localProsceniumShow = (ProsceniumShow)localList.get(0);
    }
    return localProsceniumShow;
  }
  
  public List getProsceniumapplicationList(QueryConditions paramQueryConditions)
  {
    String str = "select distinct prosceniumapplication from Z_ProsceniumShow ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null);
  }
  
  public void insert(ProsceniumShow paramProsceniumShow)
  {
    String str = "insert into Z_ProsceniumShow (prosceniumApplication,showProperty,showName,nodeKey,serialNumber,type,renderer,isShow) values(?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramProsceniumShow.getProsceniumApplication(), paramProsceniumShow.getShowProperty(), paramProsceniumShow.getShowName(), paramProsceniumShow.getNodeKey(), Integer.valueOf(paramProsceniumShow.getSerialNumber()), paramProsceniumShow.getType(), paramProsceniumShow.getRenderer(), paramProsceniumShow.getIsShow() };
    int[] arrayOfInt = { 12, 12, 12, 12, 2, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
