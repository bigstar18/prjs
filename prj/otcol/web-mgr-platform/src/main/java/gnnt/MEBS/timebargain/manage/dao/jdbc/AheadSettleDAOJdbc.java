package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.AheadSettleDAO;
import gnnt.MEBS.timebargain.manage.model.AheadSettle;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.util.Assert;

public class AheadSettleDAOJdbc
  extends BaseDAOJdbc
  implements AheadSettleDAO
{
  private static final Log logger = LogFactory.getLog(AheadSettleDAOJdbc.class);
  
  public int auditAheadSettle(AheadSettle paramAheadSettle)
    throws Exception
  {
    Assert.notNull(paramAheadSettle.getCommodityID());
    Assert.notNull(paramAheadSettle.getCustomerID_B());
    Assert.notNull(paramAheadSettle.getCustomerID_B());
    Assert.notNull(Long.valueOf(paramAheadSettle.getQuantity()));
    Assert.notNull(Long.valueOf(paramAheadSettle.getGageQty()));
    Assert.notNull(Integer.valueOf(paramAheadSettle.getApplyType()));
    AheadSettleStoredProcedure localAheadSettleStoredProcedure = new AheadSettleStoredProcedure(getDataSource());
    HashMap localHashMap = new HashMap();
    localHashMap.put("P_APPLYID", Long.valueOf(paramAheadSettle.getApplyID()));
    localHashMap.put("P_COMMODITYID", paramAheadSettle.getCommodityID());
    localHashMap.put("P_QUANTITY", Long.valueOf(paramAheadSettle.getQuantity()));
    localHashMap.put("P_PRICE", Double.valueOf(paramAheadSettle.getPrice()));
    localHashMap.put("P_SCUSTOMERID", paramAheadSettle.getCustomerID_S());
    localHashMap.put("P_SGAGEQTY", Long.valueOf(paramAheadSettle.getGageQty()));
    localHashMap.put("P_BCUSTOMERID", paramAheadSettle.getCustomerID_B());
    localHashMap.put("P_QUANTITY", Long.valueOf(paramAheadSettle.getQuantity()));
    localHashMap.put("P_BGAGEQTY", Integer.valueOf(0));
    localHashMap.put("P_MODIFIER", paramAheadSettle.getModifier());
    localHashMap.put("P_APPLYTYPE", Integer.valueOf(1));
    this.log.debug("param:" + localHashMap);
    Map localMap = localAheadSettleStoredProcedure.execute(localHashMap);
    return ((Integer)localMap.get("BRET")).intValue();
  }
  
  public AheadSettle getAheadSettle(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" select * from T_E_APPLYAHEADSETTLE where APPLYID=? ");
    Object[] arrayOfObject = { Integer.valueOf(Integer.parseInt(paramString)) };
    int[] arrayOfInt = { -5 };
    return (AheadSettle)getJdbcTemplate().queryForObject(localStringBuilder.toString(), arrayOfObject, arrayOfInt, new AheadSettle());
  }
  
  public int saveAheadSettle(AheadSettle paramAheadSettle)
    throws Exception
  {
    logger.debug("===JDBC 保持新增提前交收申请------>" + paramAheadSettle.toString());
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" INSERT INTO T_E_APPLYAHEADSETTLE(APPLYID,COMMODITYID,CUSTOMERID_S,CUSTOMERID_B,PRICE,QUANTITY,GAGEQTY,APPLYTYPE,STATUS,CREATETIME,CREATOR,REMARK1,MODIFYTIME,MODIFIER,REMARK2) ");
    localStringBuilder.append(" VALUES (SEQ_T_E_APPLYAHEADSETTLE.NEXTVAL,\t\t   ?,           ?,           ?,    ?,       ?,      ?,        ?,     1,   SYSDATE,      ?,      ?,   SYSDATE,    NULL,   NULL) ");
    int[] arrayOfInt = { 12, 12, 12, 8, -5, -5, 4, 12, 12 };
    Object[] arrayOfObject = { paramAheadSettle.getCommodityID(), paramAheadSettle.getCustomerID_S(), paramAheadSettle.getCustomerID_B(), Double.valueOf(paramAheadSettle.getPrice()), Long.valueOf(paramAheadSettle.getQuantity()), Long.valueOf(paramAheadSettle.getGageQty()), Integer.valueOf(paramAheadSettle.getApplyType()), paramAheadSettle.getCreator(), paramAheadSettle.getRemark1() };
    return getJdbcTemplate().update(localStringBuilder.toString(), arrayOfObject, arrayOfInt);
  }
  
  public List listAheadSettle(QueryConditions paramQueryConditions)
  {
    logger.debug("===JDBC 查询所有满足条件的提前交收记录------>");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" SELECT * FROM T_E_APPLYAHEADSETTLE WHERE 1=1 ");
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuilder.append(" and " + paramQueryConditions.getFieldsSqlClause());
    }
    logger.debug("sql: " + localStringBuilder.toString());
    return getJdbcTemplate().queryForList(localStringBuilder.toString(), arrayOfObject);
  }
  
  public void updateDataByYourSQL(String paramString)
    throws Exception
  {
    getJdbcTemplate().update(paramString);
  }
  
  public long getHoldQty(String paramString1, String paramString2, int paramInt)
  {
    long l = -1L;
    String str = "select holdqty from t_customerholdsum where customerid='" + paramString1 + "' " + "and commodityid='" + paramString2 + "' and bs_flag=" + paramInt;
    List localList = getJdbcTemplate().queryForList(str);
    if ((localList != null) && (localList.size() > 0))
    {
      Map localMap = (Map)localList.get(0);
      l = ((BigDecimal)localMap.get("holdqty")).longValue();
    }
    return l;
  }
  
  public long getGageQty(String paramString1, String paramString2, int paramInt)
  {
    long l = -1L;
    String str = "select gageqty from t_customerholdsum where customerid='" + paramString1 + "' " + "and commodityid='" + paramString2 + "' and bs_flag=" + paramInt;
    List localList = getJdbcTemplate().queryForList(str);
    if ((localList != null) && (localList.size() > 0))
    {
      Map localMap = (Map)localList.get(0);
      l = ((BigDecimal)localMap.get("gageqty")).longValue();
    }
    return l;
  }
  
  private class AheadSettleStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_AheadSettleQty";
    
    public AheadSettleStoredProcedure(DataSource paramDataSource)
    {
      super("FN_T_AheadSettleQty");
      setFunction(true);
      declareParameter(new SqlOutParameter("BRET", 4));
      declareParameter(new SqlParameter("P_APPLYID", -5));
      declareParameter(new SqlParameter("P_COMMODITYID", 12));
      declareParameter(new SqlParameter("P_QUANTITY", -5));
      declareParameter(new SqlParameter("P_PRICE", 8));
      declareParameter(new SqlParameter("P_SCUSTOMERID", 12));
      declareParameter(new SqlParameter("P_SGAGEQTY", -5));
      declareParameter(new SqlParameter("P_BCUSTOMERID", 12));
      declareParameter(new SqlParameter("P_BGAGEQTY", -5));
      declareParameter(new SqlParameter("P_MODIFIER", 12));
      declareParameter(new SqlParameter("P_APPLYTYPE", 4));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
    }
  }
}
