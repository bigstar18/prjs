package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.Commodity;
import gnnt.MEBS.delivery.model.CommodityExpansion;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommodityDao
  extends DaoHelperImpl
{
  private final transient Log logger = LogFactory.getLog(CommodityDao.class);
  
  public List getCommoditys(QueryConditions paramQueryConditions)
  {
    String str = "select * from w_Commodity where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new Commodity()));
  }
  
  public List getT_Commoditys(QueryConditions paramQueryConditions)
  {
    String str = "select * from T_Commodity where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null);
  }
  
  public List getCommodityList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select c.*,s.name status from w_commodity c,w_status s where s.kind='Commodity' and s.value=c.ability ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List checkIdCommodity(Commodity paramCommodity)
  {
    String str = "";
    str = "select * from w_commodity where id='" + paramCommodity.getId() + "' ";
    return queryBySQL(str);
  }
  
  public List checkNameCommodity(Commodity paramCommodity)
  {
    String str = "";
    str = "select * from w_commodity where name='" + paramCommodity.getName() + "' and id!='" + paramCommodity.getId() + "'";
    return queryBySQL(str);
  }
  
  public void addCommodityExpansion(CommodityExpansion paramCommodityExpansion)
  {
    String str = "insert into w_commodityexpansion values (?,?,?,?)";
    Object[] arrayOfObject = { paramCommodityExpansion.getCommodityId(), paramCommodityExpansion.getKind(), paramCommodityExpansion.getName(), Integer.valueOf(paramCommodityExpansion.getNo()) };
    int[] arrayOfInt = { 12, 12, 12, 4 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List getCommodityExpansiones(String paramString1, String paramString2)
  {
    String str = "select * from w_commodityexpansion where commodityId=? and kind=? order by no";
    Object[] arrayOfObject = { paramString1, paramString2 };
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new CommodityExpansion()));
    return localList;
  }
  
  public void addCommodity(Commodity paramCommodity)
  {
    String str = " insert into w_Commodity(id,ability,name,minJS,yshort,ylong,countType,createtime,modifyDate,operationId )values(?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramCommodity.getId(), Integer.valueOf(paramCommodity.getAbility()), paramCommodity.getName(), Double.valueOf(paramCommodity.getMinJS()), Double.valueOf(paramCommodity.getYshort()), Double.valueOf(paramCommodity.getYlong()), paramCommodity.getCountType(), paramCommodity.getCreatetime(), paramCommodity.getModifyDate(), paramCommodity.getOperationId() };
    int[] arrayOfInt = { 12, 4, 12, 8, 8, 8, 12, 91, 91, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updateCommodity(Commodity paramCommodity)
  {
    String str = "update w_commodity set name=?,ability=?,minJS=?,yshort=?,ylong=?,countType=?,modifyDate=?,operationId=? where id=?";
    Object[] arrayOfObject = { paramCommodity.getName(), Integer.valueOf(paramCommodity.getAbility()), Double.valueOf(paramCommodity.getMinJS()), Double.valueOf(paramCommodity.getYshort()), Double.valueOf(paramCommodity.getYlong()), paramCommodity.getCountType(), paramCommodity.getModifyDate(), paramCommodity.getOperationId(), paramCommodity.getId() };
    int[] arrayOfInt = { 12, 4, 8, 8, 8, 12, 91, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void delCommodityExpansiones(String paramString)
  {
    String str = "delete from w_commodityexpansion where commodityId=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void getCommodityLock(Commodity paramCommodity)
  {
    String str = "select * from w_commodity where id='" + paramCommodity.getId() + "' for update";
    queryBySQL(str);
  }
  
  public List getT_Breeds(QueryConditions paramQueryConditions)
  {
    String str = "select * from T_A_Breed where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null);
  }
  
  public List getSettleCommodity(QueryConditions paramQueryConditions)
  {
    String str = "select * from T_settleCommodity where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null);
  }
}
