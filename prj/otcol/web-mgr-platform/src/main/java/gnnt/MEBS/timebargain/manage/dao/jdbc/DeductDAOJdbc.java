package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.DeductDAO;
import gnnt.MEBS.timebargain.manage.model.Deduct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class DeductDAOJdbc
  extends BaseDAOJdbc
  implements DeductDAO
{
  private Log log = LogFactory.getLog(DeductDAOJdbc.class);
  
  public List getDeductPositionList(Deduct paramDeduct)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    localStringBuffer.append("select d.*,c.commodityID from T_E_DEDUCTPOSITION d,T_COMMODITY c where d.CommodityID = c.CommodityID");
    if (paramDeduct != null)
    {
      if (paramDeduct.getDeductDate() != null)
      {
        localStringBuffer.append(" and d.DeductDate = ?");
        localArrayList.add(paramDeduct.getDeductDate());
      }
      if ((paramDeduct.getCommodityID() != null) && (!"".equals(paramDeduct.getCommodityID())))
      {
        localStringBuffer.append(" and c.commodityID = ?");
        localArrayList.add(paramDeduct.getCommodityID());
      }
      Object[] arrayOfObject = null;
      if ((localArrayList != null) && (localArrayList.size() > 0)) {
        arrayOfObject = localArrayList.toArray();
      }
      this.log.debug("sql: " + localStringBuffer.toString());
      if (arrayOfObject != null) {
        for (int i = 0; i < arrayOfObject.length; i++) {
          this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
        }
      }
      return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public Deduct getDeductPosition(Deduct paramDeduct)
  {
    String str = "select d.*,c.commodityID from T_E_DEDUCTPOSITION d,T_COMMODITY c where c.commodityID = d.commodityID and d.DeductID = ?";
    Object[] arrayOfObject = { paramDeduct.getDeductID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      return (Deduct)getJdbcTemplate().queryForObject(str, arrayOfObject, new DeductRowMapper());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("此记录不存在！");
    }
  }
  
  public Deduct updateDeductPosition(Deduct paramDeduct)
  {
    String str = "update T_E_DEDUCTPOSITION set DeductPrice=?,LoserBSFlag=?,LoserMode=?,LossRate=?,SelfCounteract=?,ProfitLvl1=?,ProfitLvl2=?,ProfitLvl3=?,ProfitLvl4=?,ProfitLvl5=?,DeductDate=?,CommodityID=?,DeductStatus='N' where DeductID = ?";
    Object[] arrayOfObject = { paramDeduct.getDeductPrice(), paramDeduct.getLoserBSFlag(), paramDeduct.getLoserMode(), paramDeduct.getLossRate(), paramDeduct.getSelfCounteract(), paramDeduct.getProfitLvl1(), paramDeduct.getProfitLvl2(), paramDeduct.getProfitLvl3(), paramDeduct.getProfitLvl4(), paramDeduct.getProfitLvl5(), paramDeduct.getDeductDate(), paramDeduct.getCommodityID(), paramDeduct.getDeductID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
      return paramDeduct;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("修改失败！");
    }
  }
  
  public Deduct insertDeductPosition(Deduct paramDeduct)
  {
    String str1 = "select SEQ_T_E_DEDUCTPOSITION.nextval from dual";
    Object localObject = getJdbcTemplate().queryForObject(str1, Object.class);
    Long localLong = null;
    if (localObject != null) {
      localLong = Long.valueOf(Long.parseLong(localObject.toString()));
    }
    String str2 = "insert into T_E_DEDUCTPOSITION (DeductID,DeductDate,CommodityID,DeductStatus,DeductPrice,LoserBSFlag,LoserMode,LossRate,SelfCounteract,ProfitLvl1,ProfitLvl2,ProfitLvl3,ProfitLvl4,ProfitLvl5) values (?,?,?,'N',?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject1 = { localLong, paramDeduct.getDeductDate(), paramDeduct.getCommodityID(), paramDeduct.getDeductPrice(), paramDeduct.getLoserBSFlag(), paramDeduct.getLoserMode(), paramDeduct.getLossRate(), paramDeduct.getSelfCounteract(), paramDeduct.getProfitLvl1(), paramDeduct.getProfitLvl2(), paramDeduct.getProfitLvl3(), paramDeduct.getProfitLvl4(), paramDeduct.getProfitLvl5() };
    this.log.debug("sql: " + str2);
    for (int i = 0; i < arrayOfObject1.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject1[i]);
    }
    try
    {
      getJdbcTemplate().update(str2, arrayOfObject1);
      str1 = "select d.*,c.commodityID from T_E_DEDUCTPOSITION d,T_COMMODITY c where c.commodityID = d.commodityID and d.DeductID = ?";
      Object[] arrayOfObject2 = { localLong };
      this.log.debug("sqlID: " + str1);
      for (int j = 0; j < arrayOfObject2.length; j++) {
        this.log.debug("paramsID[" + j + "]: " + arrayOfObject2[j]);
      }
      return (Deduct)getJdbcTemplate().queryForObject(str1, arrayOfObject2, new DeductRowMapper());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("添加失败！");
    }
  }
  
  public List getDeductDetailList(Deduct paramDeduct)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    localStringBuffer.append("select d.*,case when d.pureHoldQty < 0 then 'S '||abs(d.pureHoldQty) when d.pureHoldQty > 0 then 'B '||d.pureHoldQty else ''||d.pureHoldQty end relPureHoldQty from T_E_DEDUCTDETAIL d");
    if (paramDeduct != null)
    {
      if (paramDeduct.getDeductID() != null)
      {
        localStringBuffer.append(" where d.DeductID = ?");
        localArrayList.add(paramDeduct.getDeductID());
      }
      localStringBuffer.append(" order by d.deductQty");
      Object[] arrayOfObject = null;
      if ((localArrayList != null) && (localArrayList.size() > 0)) {
        arrayOfObject = localArrayList.toArray();
      }
      this.log.debug("sql: " + localStringBuffer.toString());
      if (arrayOfObject != null) {
        for (int i = 0; i < arrayOfObject.length; i++) {
          this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
        }
      }
      return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public List getDeductDetailListQuery(Deduct paramDeduct)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    localStringBuffer.append("select d.*,case when pureHoldQty < 0 then 'S '||abs(pureHoldQty) when pureHoldQty > 0 then 'B '||abs(pureHoldQty) else ''||pureHoldQty end relPureHoldQty from T_E_DEDUCTDETAIL d ");
    if (paramDeduct != null)
    {
      if (paramDeduct.getDeductID() != null)
      {
        localStringBuffer.append(" where d.DeductID = ?");
        localArrayList.add(paramDeduct.getDeductID());
      }
      localStringBuffer.append(" order by deductQty desc");
      Object[] arrayOfObject = null;
      if ((localArrayList != null) && (localArrayList.size() > 0)) {
        arrayOfObject = localArrayList.toArray();
      }
      this.log.debug("sql: " + localStringBuffer.toString());
      if (arrayOfObject != null) {
        for (int i = 0; i < arrayOfObject.length; i++) {
          this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
        }
      }
      return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    }
    return null;
  }
  
  public List getDeductKeepFirmList(Deduct paramDeduct)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    localStringBuffer.append("select d.* from T_E_DEDUCTKEEP d");
    if (paramDeduct != null)
    {
      if (paramDeduct.getDeductID() != null)
      {
        localStringBuffer.append(" where d.DeductID = ?");
        localArrayList.add(paramDeduct.getDeductID());
      }
      Object[] arrayOfObject = null;
      if ((localArrayList != null) && (localArrayList.size() > 0)) {
        arrayOfObject = localArrayList.toArray();
      }
      this.log.debug("sql: " + localStringBuffer.toString());
      if (arrayOfObject != null) {
        for (int i = 0; i < arrayOfObject.length; i++) {
          this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
        }
      }
      return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public List getDeductKeepFirmListQuery(Deduct paramDeduct)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    localStringBuffer.append("select d.* from T_E_DEDUCTKEEP d");
    if (paramDeduct != null)
    {
      if (paramDeduct.getDeductID() != null)
      {
        localStringBuffer.append(" where d.DeductID = ?");
        localArrayList.add(paramDeduct.getDeductID());
      }
      Object[] arrayOfObject = null;
      if ((localArrayList != null) && (localArrayList.size() > 0)) {
        arrayOfObject = localArrayList.toArray();
      }
      this.log.debug("sql: " + localStringBuffer.toString());
      if (arrayOfObject != null) {
        for (int i = 0; i < arrayOfObject.length; i++) {
          this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
        }
      }
      return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    }
    return null;
  }
  
  public Deduct getDeductKeepFirm(Deduct paramDeduct)
  {
    String str = "select d.* from T_E_DEDUCTKEEP d where d.DeductDate = ? and d.CustomerID = ? and d.CommodityID = ?";
    Object[] arrayOfObject = { paramDeduct.getDeductDate(), paramDeduct.getCustomerID(), paramDeduct.getCommodityID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      return (Deduct)getJdbcTemplate().queryForObject(str, arrayOfObject, new DeductRowMapper2());
    }
    catch (Exception localException)
    {
      throw new RuntimeException("此记录不存在！");
    }
  }
  
  public void updateDeductKeepFirm(Deduct paramDeduct)
  {
    String str = "update T_E_DEDUCTKEEP set BS_Flag=?,KeepQty=? where DeductDate = ? and CustomerID = ? and CommodityID = ?";
    Object[] arrayOfObject = { paramDeduct.getBS_Flag(), paramDeduct.getKeepQty(), paramDeduct.getDeductDate(), paramDeduct.getCustomerID(), paramDeduct.getCommodityID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      throw new RuntimeException("修改失败！");
    }
  }
  
  public void insertDeductKeepFirm(Deduct paramDeduct)
  {
    String str1 = "select count(*) from T_CUSTOMER where customerID = ?";
    Object[] arrayOfObject1 = { paramDeduct.getCustomerID() };
    this.log.debug("sqlC: " + str1);
    for (int i = 0; i < arrayOfObject1.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject1[i]);
    }
    if (getJdbcTemplate().queryForInt(str1, arrayOfObject1) < 1) {
      throw new RuntimeException("此交易客户不存在！");
    }
    String str2 = "select HoldQty from T_CUSTOMERHOLDSUM where CustomerID = ? and CommodityID = ? and BS_Flag = ?";
    Object[] arrayOfObject2 = { paramDeduct.getCustomerID(), paramDeduct.getCommodityID(), paramDeduct.getBS_Flag() };
    this.log.debug("sqlQty: " + str2);
    for (int j = 0; j < arrayOfObject2.length; j++) {
      this.log.debug("params[" + j + "]: " + arrayOfObject2[j]);
    }
    Long localLong = null;
    try
    {
      Object localObject = getJdbcTemplate().queryForObject(str2, arrayOfObject2, Object.class);
      if (localObject != null) {
        localLong = Long.valueOf(Long.parseLong(localObject.toString()));
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      throw new RuntimeException("此交易客户没有持仓！");
    }
    if (paramDeduct.getKeepQty().longValue() > localLong.longValue()) {
      throw new RuntimeException("此交易客户持仓小于保留量！");
    }
    String str3 = "insert into T_E_DEDUCTKEEP (DeductID,CustomerID,BS_Flag,KeepQty) values (?,?,?,?)";
    Object[] arrayOfObject3 = { paramDeduct.getDeductID(), paramDeduct.getCustomerID(), paramDeduct.getBS_Flag(), paramDeduct.getKeepQty() };
    this.log.debug("sql: " + str3);
    for (int k = 0; k < arrayOfObject3.length; k++) {
      this.log.debug("params[" + k + "]: " + arrayOfObject3[k]);
    }
    try
    {
      getJdbcTemplate().update(str3, arrayOfObject3);
    }
    catch (Exception localException2)
    {
      throw new RuntimeException("添加失败！");
    }
  }
  
  public void deleteKeepFirm(Deduct paramDeduct)
  {
    String str = "delete from T_E_DEDUCTKEEP where DeductID = ? and BS_Flag = ? and CustomerID = ?";
    Object[] arrayOfObject = { paramDeduct.getDeductID(), paramDeduct.getBS_Flag(), paramDeduct.getCustomerID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (Exception localException)
    {
      throw new RuntimeException("删除失败！");
    }
  }
  
  public Long getDeductQty(Deduct paramDeduct)
  {
    String str = "select sum(DeductQty) deductQty from T_E_DEDUCTDETAIL where DeductID = ?";
    Object[] arrayOfObject = { paramDeduct.getDeductID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    Object localObject = getJdbcTemplate().queryForObject(str, arrayOfObject, Long.class);
    Long localLong = null;
    if (localObject != null) {
      localLong = (Long)localObject;
    }
    return localLong;
  }
  
  public int operateDeduct(Deduct paramDeduct)
  {
    DeductGoStoredProcedure localDeductGoStoredProcedure = new DeductGoStoredProcedure(getDataSource());
    HashMap localHashMap = new HashMap();
    localHashMap.put("p_deductID", paramDeduct.getDeductID());
    Map localMap = localDeductGoStoredProcedure.execute(localHashMap);
    int i = ((Integer)localMap.get("ret")).intValue();
    return i;
  }
  
  public int operateDeductData(Deduct paramDeduct)
  {
    DeductDataStoredProcedure localDeductDataStoredProcedure = new DeductDataStoredProcedure(getDataSource());
    HashMap localHashMap = new HashMap();
    localHashMap.put("p_deductID", paramDeduct.getDeductID());
    Map localMap = localDeductDataStoredProcedure.execute(localHashMap);
    int i = ((Integer)localMap.get("ret")).intValue();
    return i;
  }
  
  public List getDeductDetailSum(Deduct paramDeduct)
  {
    String str = "select sum(DeductedQty) deductedQty,sum(CounteractedQty) counteractedQty from T_E_DEDUCTDETAIL where deductID = ?";
    Object[] arrayOfObject = { paramDeduct.getDeductID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    return getJdbcTemplate().queryForList(str, arrayOfObject);
  }
  
  private class DeductDataStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_DeductData";
    
    public DeductDataStoredProcedure(DataSource paramDataSource)
    {
      super(paramDataSource,"FN_T_DeductData");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      declareParameter(new SqlParameter("p_deductID", 4));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
    }
  }
  
  private class DeductGoStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_T_DeductGo";
    
    public DeductGoStoredProcedure(DataSource paramDataSource)
    {
      super(paramDataSource,"FN_T_DeductGo");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      declareParameter(new SqlParameter("p_deductID", 4));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
    }
  }
  
  class DeductRowMapper2
    implements RowMapper
  {
    DeductRowMapper2() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToDeduct(paramResultSet);
    }
    
    private Deduct rsToDeduct(ResultSet paramResultSet)
      throws SQLException
    {
      Deduct localDeduct = new Deduct();
      localDeduct.setDeductDate(paramResultSet.getTimestamp("DeductDate"));
      localDeduct.setCommodityID(paramResultSet.getString("CommodityID"));
      localDeduct.setCustomerID(paramResultSet.getString("CustomerID"));
      localDeduct.setBS_Flag(new Short(paramResultSet.getShort("BS_Flag")));
      localDeduct.setKeepQty(new Long(paramResultSet.getLong("KeepQty")));
      return localDeduct;
    }
  }
  
  class DeductRowMapper
    implements RowMapper
  {
    DeductRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToDeduct(paramResultSet);
    }
    
    private Deduct rsToDeduct(ResultSet paramResultSet)
      throws SQLException
    {
      Deduct localDeduct = new Deduct();
      localDeduct.setDeductID(new Long(paramResultSet.getLong("DeductID")));
      localDeduct.setDeductDate(paramResultSet.getTimestamp("DeductDate"));
      localDeduct.setCommodityID(paramResultSet.getString("commodityID"));
      localDeduct.setDeductStatus(paramResultSet.getString("DeductStatus"));
      localDeduct.setDeductPrice(new Double(paramResultSet.getDouble("DeductPrice")));
      localDeduct.setLoserBSFlag(new Short(paramResultSet.getShort("LoserBSFlag")));
      localDeduct.setLoserMode(new Short(paramResultSet.getShort("LoserMode")));
      localDeduct.setLossRate(new Double(paramResultSet.getDouble("LossRate")));
      localDeduct.setSelfCounteract(new Short(paramResultSet.getShort("SelfCounteract")));
      localDeduct.setProfitLvl1(new Double(paramResultSet.getDouble("ProfitLvl1")));
      localDeduct.setProfitLvl2(new Double(paramResultSet.getDouble("ProfitLvl2")));
      localDeduct.setProfitLvl3(new Double(paramResultSet.getDouble("ProfitLvl3")));
      localDeduct.setProfitLvl4(new Double(paramResultSet.getDouble("ProfitLvl4")));
      localDeduct.setProfitLvl5(new Double(paramResultSet.getDouble("ProfitLvl5")));
      localDeduct.setExecTime(paramResultSet.getTimestamp("ExecTime"));
      localDeduct.setAlert(paramResultSet.getString("Alert"));
      return localDeduct;
    }
  }
}
