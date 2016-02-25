package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.GageWarehouseDAO;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class GageWarehouseDAOJdbc
  extends BaseDAOJdbc
  implements GageWarehouseDAO
{
  public List gageWarehouseList(QueryConditions paramQueryConditions)
  {
    this.logger.debug("------enter gageWarehouseList()------");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" SELECT a.billid billid, a.firmid,a.breedid,a.quantity,a.createtime,b.breedname FROM T_E_GageBill a,t_a_breed b WHERE 1=1 and a.breedid=b.breedid ");
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and " + paramQueryConditions.getFieldsSqlClause());
    }
    this.logger.debug("sql: " + localStringBuffer.toString());
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public List validGageBillList(QueryConditions paramQueryConditions)
  {
    this.logger.debug("------enter validGageBillList()------");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" SELECT a.*,b.breedname FROM T_ValidGageBill a,t_a_breed b WHERE 1=1 and a.Quantity!=0 and a.breedid=b.breedid ");
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and " + paramQueryConditions.getFieldsSqlClause());
    }
    this.logger.debug("sql: " + localStringBuffer.toString());
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public String getRegStockWeight(String paramString)
  {
    String str1 = "0";
    String str2 = "select weight from s_regstock where regstockid='" + paramString + "'";
    List localList = getJdbcTemplate().queryForList(str2);
    if ((localList != null) && (localList.size() > 0))
    {
      Map localMap = (Map)localList.get(0);
      if (localMap != null) {
        str1 = localMap.get("weight").toString();
      }
    }
    return str1;
  }
  
  public long getUseNum(String paramString1, String paramString2)
  {
    this.logger.debug("------enter getUseNum()------");
    long l = 0L;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" select (Quantity-FrozenQty) useNum from T_ValidGageBill where 1=1 ");
    localStringBuffer.append(" and firmID = '" + paramString1 + "' and breedid ='" + paramString2 + "' ");
    this.logger.debug("sql: " + localStringBuffer.toString());
    List localList = getJdbcTemplate().queryForList(localStringBuffer.toString());
    if ((localList != null) && (localList.size() > 0))
    {
      Map localMap = (Map)localList.get(0);
      if (localMap != null) {
        l = Long.parseLong(localMap.get("useNum").toString());
      }
    }
    return l;
  }
  
  public void updateValidGageBill(String paramString1, String paramString2, String paramString3)
  {
    this.logger.debug("------enter updateValidGageBill()------");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" update T_ValidGageBill set Quantity=(Quantity-" + paramString3 + ") WHERE 1=1 ");
    localStringBuffer.append(" and firmID='" + paramString1 + "' ");
    localStringBuffer.append(" and breedid='" + paramString2 + "' ");
    this.logger.debug("sql: " + localStringBuffer.toString());
    getJdbcTemplate().update(localStringBuffer.toString());
  }
  
  public void deleteGageBill(String paramString)
  {
    this.logger.debug("------enter deleteGageBill()------");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" delete FROM T_E_GageBill WHERE 1=1 ");
    localStringBuffer.append(" and billID='" + paramString + "' ");
    this.logger.debug("sql: " + localStringBuffer.toString());
    getJdbcTemplate().update(localStringBuffer.toString());
  }
  
  public void deleteValidGageBill()
  {
    this.logger.debug("------enter deleteGageBill()------");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" delete FROM T_ValidGageBill WHERE 1=1 ");
    localStringBuffer.append(" and Quantity=0");
    this.logger.debug("sql: " + localStringBuffer.toString());
    getJdbcTemplate().update(localStringBuffer.toString());
  }
  
  public List validateFirmList(QueryConditions paramQueryConditions)
  {
    this.logger.debug("------enter validateFirmList()------");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select * from t_firm where 1=1 ");
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and " + paramQueryConditions.getFieldsSqlClause());
    }
    this.logger.debug("sql: " + localStringBuffer.toString());
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public String getIsSettleFlagByModuleID()
  {
    String str1 = "select t.isSettle from m_trademodule t where t.moduleID = '2'";
    List localList = null;
    String str2 = "";
    try
    {
      localList = getJdbcTemplate().queryForList(str1);
      if ((localList != null) && (localList.size() > 0))
      {
        Map localMap = (Map)localList.get(0);
        str2 = localMap.get("isSettle").toString();
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str2;
  }
  
  public long addGageWarehouse(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.logger.debug("------enter addGageWarehouse()------");
    String str = getRegStockWeight(paramString1);
    long l = FreezeAndThaw(paramString1, str, "1", paramString6);
    if (l == 1L)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("insert into T_E_GageBill values ('" + paramString1 + "','" + paramString2 + "'," + paramString3 + "," + paramString4 + ",'" + paramString5 + "',sysdate)");
      this.logger.debug("sql: " + localStringBuffer.toString());
      getJdbcTemplate().update(localStringBuffer.toString());
      localStringBuffer = new StringBuffer();
      localStringBuffer.append(" select * from T_ValidGageBill where 1=1 ");
      localStringBuffer.append(" and firmID = '" + paramString2 + "' and breedid ='" + paramString3 + "' for update ");
      this.logger.debug("sql: " + localStringBuffer.toString());
      List localList = getJdbcTemplate().queryForList(localStringBuffer.toString());
      if (localList.size() > 0) {
        getJdbcTemplate().update("update T_ValidGageBill set Quantity=Quantity+" + paramString4 + " where firmID = '" + paramString2 + "' and breedid ='" + paramString3 + "' ");
      } else {
        getJdbcTemplate().update("insert into T_ValidGageBill values ('" + paramString2 + "'," + paramString3 + "," + paramString4 + ",0)");
      }
    }
    return l;
  }
  
  public long addGageWarehouse(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.logger.debug("------enter addGageWarehouse()------");
    long l = 0L;
    Object localObject;
    if (paramString7.equals("Y"))
    {
      localObject = getRegStockWeight(paramString1);
      l = FreezeAndThaw(paramString1, (String)localObject, "1", paramString6);
    }
    else if (paramString7.equals("N"))
    {
      l = 1L;
    }
    if (l == 1L)
    {
      localObject = new StringBuffer();
      ((StringBuffer)localObject).append("insert into T_E_GageBill values ('" + paramString1 + "','" + paramString2 + "'," + paramString3 + "," + paramString4 + ",'" + paramString5 + "',sysdate)");
      this.logger.debug("sql: " + ((StringBuffer)localObject).toString());
      getJdbcTemplate().update(((StringBuffer)localObject).toString());
      localObject = new StringBuffer();
      ((StringBuffer)localObject).append(" select * from T_ValidGageBill where 1=1 ");
      ((StringBuffer)localObject).append(" and firmID = '" + paramString2 + "' and breedid ='" + paramString3 + "' for update ");
      this.logger.debug("sql: " + ((StringBuffer)localObject).toString());
      List localList = getJdbcTemplate().queryForList(((StringBuffer)localObject).toString());
      if (localList.size() > 0) {
        getJdbcTemplate().update("update T_ValidGageBill set Quantity=Quantity+" + paramString4 + " where firmID = '" + paramString2 + "' and breedid ='" + paramString3 + "' ");
      } else {
        getJdbcTemplate().update("insert into T_ValidGageBill values ('" + paramString2 + "'," + paramString3 + "," + paramString4 + ",0)");
      }
    }
    return l;
  }
  
  public String getBreedIDByCommodityid(String paramString)
  {
    this.logger.debug("------enter getUseNum()------");
    String str = "";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" select breedid from (SELECT commodityid,breedid FROM T_commodity union select commodityid,breedid from t_h_commodity) t WHERE 1=1 ");
    localStringBuffer.append(" and t.commodityid='" + paramString + "' ");
    this.logger.debug("sql: " + localStringBuffer.toString());
    List localList = getJdbcTemplate().queryForList(localStringBuffer.toString());
    Map localMap = (Map)localList.get(0);
    str = (String)localMap.get("BREEDID");
    return str;
  }
  
  public long FreezeAndThaw(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    long l = -1L;
    FreezeAndThawProcedure localFreezeAndThawProcedure = new FreezeAndThawProcedure(getDataSource());
    HashMap localHashMap = new HashMap();
    localHashMap.put("p_regStockID", paramString1);
    localHashMap.put("p_frozenWeight", Double.valueOf(Double.parseDouble(paramString2)));
    localHashMap.put("p_sign", paramString3);
    localHashMap.put("p_operator", paramString4);
    localHashMap.put("p_module", Integer.valueOf(2));
    Map localMap = localFreezeAndThawProcedure.execute(localHashMap);
    l = ((BigDecimal)localMap.get("ret")).intValue();
    this.log.debug(Long.valueOf(l));
    return l;
  }
  
  private class FreezeAndThawProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_S_updateRegstockFrozen";
    
    public FreezeAndThawProcedure(DataSource paramDataSource)
    {
      super("FN_S_updateRegstockFrozen");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_regStockID", 12));
      declareParameter(new SqlParameter("p_frozenWeight", 2));
      declareParameter(new SqlParameter("p_sign", 2));
      declareParameter(new SqlParameter("p_operator", 12));
      declareParameter(new SqlParameter("p_module", 12));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
    }
  }
}
