package gnnt.MEBS.entity.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.entity.model.Breed;
import gnnt.MEBS.entity.model.Commodity;
import gnnt.MEBS.entity.model.CommodityExpansion;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BreedDao
  extends DaoHelperImpl
{
  public final transient Log logger = LogFactory.getLog(BreedDao.class);
  
  public void add(Breed paramBreed)
  {
    String str = "insert into E_Breed (breedId,breedName,status) values(?,?,?)";
    Object[] arrayOfObject = { paramBreed.getBreedId(), paramBreed.getBreedName(), Integer.valueOf(paramBreed.getStatus()) };
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
  
  public List<Breed> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from e_breed";
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
    String str = "update e_Breed set breedName=?,status=? where breedId=? ";
    Object[] arrayOfObject = { paramBreed.getBreedName(), Integer.valueOf(paramBreed.getStatus()), paramBreed.getBreedId() };
    int[] arrayOfInt = { 12, 2, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public Breed getObject(String paramString)
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
  
  public void delete(QueryConditions paramQueryConditions)
  {
    String str = "delete from E_Breed";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    updateBySQL(str, arrayOfObject);
  }
  
  public void synchTBreedName(Breed paramBreed)
  {
    String str = "update t_a_breed set breedName = ? where breedid = ? ";
    Object[] arrayOfObject = { paramBreed.getBreedName(), paramBreed.getBreedId() };
    int[] arrayOfInt = { 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void synchZBreedName(Breed paramBreed)
  {
    String str = "update z_breed set breedName = ? where breedid = ? ";
    Object[] arrayOfObject = { paramBreed.getBreedName(), paramBreed.getBreedId() };
    int[] arrayOfInt = { 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void addCommodityExpansion(CommodityExpansion paramCommodityExpansion)
  {
    String str = "insert into w_commodityexpansion values (?,?,?,?)";
    Object[] arrayOfObject = { paramCommodityExpansion.getCommodityId(), paramCommodityExpansion.getKind(), paramCommodityExpansion.getName(), Integer.valueOf(paramCommodityExpansion.getNo()) };
    int[] arrayOfInt = { 12, 12, 12, 4 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void addCommodity(Commodity paramCommodity)
  {
    String str = " insert into w_Commodity(id,ability,name,minJS,yshort,ylong,countType,createtime,modifyDate,operationId )values(?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramCommodity.getId(), Integer.valueOf(paramCommodity.getAbility()), paramCommodity.getName(), Double.valueOf(paramCommodity.getMinJS()), Double.valueOf(paramCommodity.getYshort()), Double.valueOf(paramCommodity.getYlong()), paramCommodity.getCountType(), paramCommodity.getCreatetime(), paramCommodity.getModifyDate(), paramCommodity.getOperationId() };
    int[] arrayOfInt = { 12, 4, 12, 8, 8, 8, 12, 91, 91, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void delCommodity(String paramString)
  {
    String str = "delete from w_commodity where id=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void delCommodityExpansiones(String paramString)
  {
    String str = "delete from w_commodityexpansion where commodityId=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updateCommodity(Commodity paramCommodity)
  {
    String str = "update w_commodity set name=?,ability=?,minJS=?,yshort=?,ylong=?,countType=?,modifyDate=?,operationId=? where id=?";
    Object[] arrayOfObject = { paramCommodity.getName(), Integer.valueOf(paramCommodity.getAbility()), Double.valueOf(paramCommodity.getMinJS()), Double.valueOf(paramCommodity.getYshort()), Double.valueOf(paramCommodity.getYlong()), paramCommodity.getCountType(), paramCommodity.getModifyDate(), paramCommodity.getOperationId(), paramCommodity.getId() };
    int[] arrayOfInt = { 12, 4, 8, 8, 8, 12, 91, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public Commodity getCommodity(String paramString)
  {
    String str = "select * from w_Commodity where id=? ";
    Object[] arrayOfObject = { paramString };
    Commodity localCommodity = null;
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new Commodity()));
    if ((localList != null) && (localList.size() > 0)) {
      localCommodity = (Commodity)localList.get(0);
    }
    return localCommodity;
  }
  
  public int getTBreed(String paramString)
  {
    String str = "select count(*) result from T_A_Breed b where b.breedID = " + paramString;
    List localList = queryBySQL(str);
    int i = 0;
    if ((localList != null) && (localList.size() > 0)) {
      i = Integer.parseInt(((Map)localList.get(0)).get("result").toString());
    }
    return i;
  }
  
  public int getZBreed(String paramString)
  {
    String str = "select count(*) result from z_breed b where b.breedID = " + paramString;
    List localList = queryBySQL(str);
    int i = 0;
    if ((localList != null) && (localList.size() > 0)) {
      i = Integer.parseInt(((Map)localList.get(0)).get("result").toString());
    }
    return i;
  }
  
  public int getWBreed(String paramString)
  {
    String str = "select count(*) result from w_commoditywarehouserelated where commodityId = " + paramString;
    List localList = queryBySQL(str);
    int i = 0;
    if ((localList != null) && (localList.size() > 0)) {
      i = Integer.parseInt(((Map)localList.get(0)).get("result").toString());
    }
    return i;
  }
}
