package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.Breed;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BreedDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(BreedDao.class);
  
  public void add(Breed paramBreed)
  {
    String str = "insert into Z_Breed (breedId,breedName,deliveryMinDay,deliveryMaxDay,tradeUnit,unitVolume,sortId,deliveryRatio) values(?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramBreed.getBreedId()), paramBreed.getBreedName(), Integer.valueOf(paramBreed.getDeliveryMinDay()), Integer.valueOf(paramBreed.getDeliveryMaxDay()), paramBreed.getTradeUnit(), Double.valueOf(paramBreed.getUnitVolume()), Long.valueOf(paramBreed.getSortId()), Double.valueOf(paramBreed.getDeliveryRatio()) };
    int[] arrayOfInt = { 2, 12, 2, 2, 12, 2, 2, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select zb.*,zs.sortName from z_breed zb,z_sort zs where zb.sortId=zs.sortid";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    str = str + " order by zb.breedid ";
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List<Breed> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from z_breed";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new Breed()));
  }
  
  public void update(Breed paramBreed)
  {
    String str = "update Z_Breed set breedName=?,deliveryMinDay=?,deliveryMaxDay=?,tradeUnit=?,unitVolume=?,sortId=? ,deliveryRatio=? where breedId=? ";
    Object[] arrayOfObject = { paramBreed.getBreedName(), Integer.valueOf(paramBreed.getDeliveryMinDay()), Integer.valueOf(paramBreed.getDeliveryMaxDay()), paramBreed.getTradeUnit(), Double.valueOf(paramBreed.getUnitVolume()), Long.valueOf(paramBreed.getSortId()), Double.valueOf(paramBreed.getDeliveryRatio()), Long.valueOf(paramBreed.getBreedId()) };
    int[] arrayOfInt = { 12, 2, 2, 12, 2, 2, 2, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public Breed getObject(long paramLong)
  {
    String str = "select * from z_breed where breedId=?";
    Object[] arrayOfObject = { Long.valueOf(paramLong) };
    int[] arrayOfInt = { 2 };
    List localList = queryBySQL(str, arrayOfObject, arrayOfInt, null, new CommonRowMapper(new Breed()));
    Breed localBreed = null;
    if ((localList.size() > 0) && (localList != null)) {
      localBreed = (Breed)localList.get(0);
    }
    return localBreed;
  }
  
  public void delete(Breed paramBreed)
  {
    String str = "delete from z_breed where breedId=?";
    Object[] arrayOfObject = { Long.valueOf(paramBreed.getBreedId()) };
    int[] arrayOfInt = { 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
}
