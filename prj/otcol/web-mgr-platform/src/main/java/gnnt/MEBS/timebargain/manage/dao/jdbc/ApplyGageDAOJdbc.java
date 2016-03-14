package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.ApplyGageDAO;
import gnnt.MEBS.timebargain.manage.model.ApplyGage;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.Date;
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

public class ApplyGageDAOJdbc
  extends BaseDAOJdbc
  implements ApplyGageDAO
{
  private final Log logger = LogFactory.getLog(ApplyGageDAOJdbc.class);
  private static final int BS_FLAG_B = 1;
  private static final int BS_FLAG_S = 2;
  
  public ApplyGage getApplyGage(long paramLong)
  {
    this.logger.debug("------enter getApplyGage()------");
    String str = " select * from t_e_applygage where applyid=? ";
    Object[] arrayOfObject = { Long.valueOf(paramLong) };
    List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
    Map localMap = (Map)localList.get(0);
    ApplyGage localApplyGage = new ApplyGage();
    localApplyGage.setApplyId(Long.parseLong(String.valueOf(localMap.get("APPLYID"))));
    localApplyGage.setCommodityID((String)localMap.get("COMMODITYID"));
    localApplyGage.setFirmID((String)localMap.get("FIRMID"));
    localApplyGage.setCustomerID((String)localMap.get("CUSTOMERID"));
    localApplyGage.setQuantity(Long.parseLong(String.valueOf(localMap.get("QUANTITY"))));
    localApplyGage.setApplyType(Integer.parseInt(String.valueOf(localMap.get("APPLYTYPE"))));
    localApplyGage.setStatus(Integer.parseInt(String.valueOf(localMap.get("STATUS"))));
    localApplyGage.setCreateTime((Date)localMap.get("CREATETIME"));
    localApplyGage.setCreator((String)localMap.get("CREATOR"));
    return localApplyGage;
  }
  
  public List listApplyGage(QueryConditions paramQueryConditions)
  {
    this.logger.debug("------enter listApplyGage()------");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" select * from t_e_applygage where 1=1 ");
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      localStringBuffer.append(" and " + paramQueryConditions.getFieldsSqlClause());
    }
    this.logger.debug("sql: " + localStringBuffer.toString());
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public void saveApplyGage(ApplyGage paramApplyGage)
    throws Exception
  {
    this.logger.debug("------enter saveApplyGage()------");
    String str = " insert into t_e_applygage(applyid,commodityid,firmid,customerid,quantity,applytype,status,createtime,creator,remark1,modifytime,modifier,remark2)  values(?,?,?,?,?,?,1,sysdate,?,?,sysdate,null,null) ";
    Object[] arrayOfObject = { Long.valueOf(paramApplyGage.getApplyId()), paramApplyGage.getCommodityID(), paramApplyGage.getFirmID(), paramApplyGage.getCustomerID(), Long.valueOf(paramApplyGage.getQuantity()), Integer.valueOf(paramApplyGage.getApplyType()), paramApplyGage.getCreator(), paramApplyGage.getRemark1() };
    int[] arrayOfInt = { 4, 12, 12, 12, -5, 4, 12, 12 };
    getJdbcTemplate().update(str, arrayOfObject, arrayOfInt);
  }
  
  public long getApplyGageId()
  {
    String str = "select seq_t_e_applygage.nextval from dual";
    long l = getJdbcTemplate().queryForLong(str, null);
    return l;
  }
  
  public void updateApplyGage(ApplyGage paramApplyGage)
  {
    this.logger.debug("------enter updateApplyGage()------");
    String str = " update t_e_applygage set  commodityid=?,firmid=?,customerid=?,quantity=?,applytype=?,status=?,  createtime=?,creator=?,remark1=?,modifytime=sysdate,modifier=?,remark2=?  where applyid=? ";
    Object[] arrayOfObject = { paramApplyGage.getCommodityID(), paramApplyGage.getFirmID(), paramApplyGage.getCustomerID(), Long.valueOf(paramApplyGage.getQuantity()), Integer.valueOf(paramApplyGage.getApplyType()), Integer.valueOf(paramApplyGage.getStatus()), paramApplyGage.getCreateTime(), paramApplyGage.getCreator(), paramApplyGage.getRemark1(), paramApplyGage.getModifier(), paramApplyGage.getRemark2(), Long.valueOf(paramApplyGage.getApplyId()) };
    int[] arrayOfInt = { 12, 12, 12, -5, 4, 4, 91, 12, 12, 12, 12, -5 };
    getJdbcTemplate().update(str, arrayOfObject, arrayOfInt);
  }
  
  public int auditApplyGage(ApplyGage paramApplyGage)
  {
    Assert.notNull(paramApplyGage.getCommodityID());
    Assert.notNull(paramApplyGage.getCustomerID());
    Assert.notNull(Long.valueOf(paramApplyGage.getQuantity()));
    ApplyGageStoredProcedure localApplyGageStoredProcedure = new ApplyGageStoredProcedure(getDataSource());
    HashMap localHashMap = new HashMap();
    localHashMap.put("P_COMMODITYID", paramApplyGage.getCommodityID());
    localHashMap.put("P_BS_FLAG", Integer.valueOf(2));
    localHashMap.put("P_CUSTOMERID", paramApplyGage.getCustomerID());
    localHashMap.put("P_QUANTITY", Long.valueOf(paramApplyGage.getQuantity()));
    this.log.debug("param:" + localHashMap);
    Map localMap = localApplyGageStoredProcedure.execute(localHashMap);
    return ((Integer)localMap.get("ret")).intValue();
  }
  
  public int auditCancleGage(ApplyGage paramApplyGage)
  {
    Assert.notNull(paramApplyGage.getCommodityID());
    Assert.notNull(paramApplyGage.getCustomerID());
    Assert.notNull(Long.valueOf(paramApplyGage.getQuantity()));
    Assert.notNull(Integer.valueOf(paramApplyGage.getApplyType()));
    CancleGageStoredProcedure localCancleGageStoredProcedure = new CancleGageStoredProcedure(getDataSource());
    HashMap localHashMap = new HashMap();
    localHashMap.put("P_COMMODITYID", paramApplyGage.getCommodityID());
    localHashMap.put("P_CUSTOMERID", paramApplyGage.getCustomerID());
    localHashMap.put("P_QUANTITY", Long.valueOf(paramApplyGage.getQuantity()));
    localHashMap.put("P_APPLYTYPE", Integer.valueOf(paramApplyGage.getApplyType()));
    this.log.debug("param:" + localHashMap);
    Map localMap = localCancleGageStoredProcedure.execute(localHashMap);
    return ((Integer)localMap.get("ret")).intValue();
  }
  
  public List getResultListBySQL(ApplyGage paramApplyGage)
  {
    String str = " select * from T_CustomerHoldSum  where CustomerID = '" + paramApplyGage.getCustomerID() + "' " + " and CommodityID = '" + paramApplyGage.getCommodityID() + "' " + " and bs_flag = 2  ";
    return getJdbcTemplate().queryForList(str);
  }
  
  private class CancleGageStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_GageQtyCancel";
    
    public CancleGageStoredProcedure(DataSource paramDataSource)
    {
      super(paramDataSource,"FN_T_GageQtyCancel");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      declareParameter(new SqlParameter("P_CUSTOMERID", 12));
      declareParameter(new SqlParameter("P_COMMODITYID", 12));
      declareParameter(new SqlParameter("P_QUANTITY", -5));
      declareParameter(new SqlParameter("P_APPLYTYPE", 4));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
    }
  }
  
  private class ApplyGageStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_GageQty";
    
    public ApplyGageStoredProcedure(DataSource paramDataSource)
    {
      super(paramDataSource,"FN_T_GageQty");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      declareParameter(new SqlParameter("P_COMMODITYID", 12));
      declareParameter(new SqlParameter("P_BS_FLAG", 4));
      declareParameter(new SqlParameter("P_CUSTOMERID", 12));
      declareParameter(new SqlParameter("P_QUANTITY", -5));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
    }
  }
}
