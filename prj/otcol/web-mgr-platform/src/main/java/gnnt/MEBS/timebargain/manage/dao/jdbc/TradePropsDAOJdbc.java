package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.TradePropsDAO;
import gnnt.MEBS.timebargain.manage.model.TradeProps;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class TradePropsDAOJdbc
  extends BaseDAOJdbc
  implements TradePropsDAO
{
  private Log log = LogFactory.getLog(TradePropsDAOJdbc.class);
  
  public void updateCustomerMaxHoldQty(String paramString, Long paramLong)
  {
    String str = "select * from T_A_FirmTradeProps where FirmID=?";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql: " + str);
    this.log.debug("FirmID:" + arrayOfObject[0]);
    List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
    int i;
    if ((localList == null) || (localList.size() == 0))
    {
      str = "insert into T_A_FirmTradeProps(FirmID,MaxHoldQty,ModifyTime) values(?,?,sysdate)";
      arrayOfObject = new Object[] { paramString, paramLong };
      this.log.debug("sql: " + str);
      for (i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      getJdbcTemplate().update(str, arrayOfObject);
    }
    else
    {
      str = "update T_A_FirmTradeProps set MaxHoldQty=?,ModifyTime=sysdate where FirmID=?";
      arrayOfObject = new Object[] { paramLong, paramString };
      this.log.debug("sql: " + str);
      for (i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      getJdbcTemplate().update(str, arrayOfObject);
    }
  }
  
  public void updateCustomermMinClearDeposit(String paramString, Double paramDouble)
  {
    String str = "select * from T_A_FirmTradeProps where FirmID=?";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql: " + str);
    this.log.debug("FirmID:" + arrayOfObject[0]);
    List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
    int i;
    if ((localList == null) || (localList.size() == 0))
    {
      str = "insert into T_A_FirmTradeProps(FirmID,MinClearDeposit,ModifyTime) values(?,?,sysdate)";
      arrayOfObject = new Object[] { paramString, paramDouble };
      this.log.debug("sql: " + str);
      for (i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      getJdbcTemplate().update(str, arrayOfObject);
    }
    else
    {
      str = "update T_A_FirmTradeProps set MinClearDeposit=?,ModifyTime=sysdate where FirmID=?";
      arrayOfObject = new Object[] { paramDouble, paramString };
      this.log.debug("sql: " + str);
      for (i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      getJdbcTemplate().update(str, arrayOfObject);
    }
  }
  
  public void updateCustomerMaxOverdraft(String paramString, Double paramDouble)
  {
    String str = "select * from T_A_FirmTradeProps where FirmID=?";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql: " + str);
    this.log.debug("FirmID:" + arrayOfObject[0]);
    List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
    int i;
    if ((localList == null) || (localList.size() == 0))
    {
      str = "insert into T_A_FirmTradeProps(FirmID,MaxOverdraft,ModifyTime) values(?,?,sysdate)";
      arrayOfObject = new Object[] { paramString, paramDouble };
      this.log.debug("sql: " + str);
      for (i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      getJdbcTemplate().update(str, arrayOfObject);
    }
    else
    {
      str = "update T_A_FirmTradeProps set MaxOverdraft=?,ModifyTime=sysdate where FirmID=?";
      arrayOfObject = new Object[] { paramDouble, paramString };
      this.log.debug("sql: " + str);
      for (i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      getJdbcTemplate().update(str, arrayOfObject);
    }
  }
  
  public void updateCustomerDefProps(TradeProps paramTradeProps)
  {
    Assert.notNull(paramTradeProps.getMaxHoldQty());
    Assert.notNull(paramTradeProps.getMinClearDeposit());
    Assert.notNull(paramTradeProps.getMaxOverdraft());
    String str = "select * from T_A_FirmDefProps";
    this.log.debug("sql: " + str);
    List localList = getJdbcTemplate().queryForList(str);
    Object[] arrayOfObject;
    int i;
    if ((localList == null) || (localList.size() == 0))
    {
      str = "insert into T_A_FirmDefProps(MaxHoldQty,MinClearDeposit,MaxOverdraft,ModifyTime) values(?,?,?,sysdate)";
      arrayOfObject = new Object[] { paramTradeProps.getMaxHoldQty() == null ? new Long(0L) : paramTradeProps.getMaxHoldQty(), paramTradeProps.getMinClearDeposit() == null ? new Double(0.0D) : paramTradeProps.getMinClearDeposit(), paramTradeProps.getMaxOverdraft() == null ? new Double(0.0D) : paramTradeProps.getMaxOverdraft() };
      this.log.debug("sql: " + str);
      for (i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      getJdbcTemplate().update(str, arrayOfObject);
    }
    else
    {
      str = "update T_A_FirmDefProps set MaxHoldQty=?,MinClearDeposit=?,MaxOverdraft=?,ModifyTime=sysdate";
      arrayOfObject = new Object[] { paramTradeProps.getMaxHoldQty() == null ? new Long(0L) : paramTradeProps.getMaxHoldQty(), paramTradeProps.getMinClearDeposit() == null ? new Double(0.0D) : paramTradeProps.getMinClearDeposit(), paramTradeProps.getMaxOverdraft() == null ? new Double(0.0D) : paramTradeProps.getMaxOverdraft() };
      this.log.debug("sql: " + str);
      for (i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      getJdbcTemplate().update(str, arrayOfObject);
    }
  }
  
  public void insertFirmValidTradeProps()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("insert into T_A_FirmValidTradeProps(FirmID,MaxHoldQty,MinClearDeposit,MaxOverdraft) select").append(" d.FirmID,").append("nvl(c.MaxHoldQty,nvl(b.MaxHoldQty,a.MaxHoldQty)) MaxHoldQty,").append("nvl(c.MinClearDeposit,nvl(b.MinClearDeposit,a.MinClearDeposit)) MinClearDeposit,").append("nvl(c.MaxOverdraft,nvl(b.MaxOverdraft,a.MaxOverdraft)) MaxOverdraft").append(" from T_A_FIRMDEFPROPS a,T_A_GROUPTRADEPROPS b,T_A_FIRMTRADEPROPS c,T_firm d,T_A_FIRMGROUP f").append(" where d.FirmID = c.FirmID(+) and d.GroupID = b.GroupID(+) and  d.GroupID = f.GroupID");
    String str = "delete from T_A_FirmValidTradeProps";
    try
    {
      getJdbcTemplate().update(str);
      getJdbcTemplate().update(localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.log.error("添加失败");
    }
  }
  
  public TradeProps getCustomerDefProps()
  {
    String str = "select * from T_A_FirmDefProps";
    this.log.debug("sql: " + str);
    TradeProps localTradeProps = null;
    try
    {
      localTradeProps = (TradeProps)getJdbcTemplate().queryForObject(str, new CustomerDefPropsRowMapper());
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      this.log.error("FirmDefProps表中无记录！");
      localTradeProps = new TradeProps();
    }
    return localTradeProps;
  }
  
  public List getGroupTradePropss(TradeProps paramTradeProps)
  {
    String str = "select a.*,b.GroupName from T_A_GroupTradeProps a,T_A_FirmGroup b where a.GroupID=b.GroupID order by a.GroupID";
    this.log.debug("sql: " + str);
    return getJdbcTemplate().queryForList(str);
  }
  
  public TradeProps getGroupTradePropsById(Long paramLong)
  {
    Assert.notNull(paramLong);
    String str = "select * from T_A_GroupTradeProps where GroupID=?";
    Object[] arrayOfObject = { paramLong };
    this.log.debug("sql: " + str);
    this.log.debug("groupID:" + arrayOfObject[0]);
    TradeProps localTradeProps = null;
    try
    {
      localTradeProps = (TradeProps)getJdbcTemplate().queryForObject(str, arrayOfObject, new GroupTradePropsRowMapper());
      return localTradeProps;
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      throw new RuntimeException("GroupTradeProps表中交易用户组ID[" + paramLong + "]不存在！");
    }
  }
  
  public void deleteGroupTradePropsById(Long paramLong)
  {
    Assert.notNull(paramLong);
    String str = "delete from T_A_GroupTradeProps where GroupID=?";
    Object[] arrayOfObject = { paramLong };
    this.log.debug("sql: " + str);
    this.logger.debug("groupID: " + arrayOfObject[0]);
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void insertGroupTradeProps(TradeProps paramTradeProps)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("insert into T_A_GroupTradeProps(GroupID,MaxHoldQty,MinClearDeposit,MaxOverdraft,ModifyTime) values(").append(paramTradeProps.getGroupID()).append(",").append(paramTradeProps.getMaxHoldQty()).append(",").append(paramTradeProps.getMinClearDeposit()).append(",").append(paramTradeProps.getMaxOverdraft()).append(",").append("sysdate)");
    this.log.debug("sql: " + localStringBuffer.toString());
    try
    {
      getJdbcTemplate().update(localStringBuffer.toString());
    }
    catch (DataIntegrityViolationException localDataIntegrityViolationException)
    {
      throw new RuntimeException("GroupTradeProps表中主键重复，不能插入相同的记录！");
    }
  }
  
  public void updateGroupTradeProps(TradeProps paramTradeProps)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("update T_A_GroupTradeProps set MaxHoldQty=").append(paramTradeProps.getMaxHoldQty()).append(",MinClearDeposit=").append(paramTradeProps.getMinClearDeposit()).append(",MaxOverdraft=").append(paramTradeProps.getMaxOverdraft()).append(",ModifyTime=sysdate where GroupID=").append(paramTradeProps.getGroupID());
    this.log.debug("sql: " + localStringBuffer.toString());
    getJdbcTemplate().update(localStringBuffer.toString());
  }
  
  public List getCustomerTradePropss(TradeProps paramTradeProps)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.*,b.FirmName ").append("from T_A_FirmTradeProps a,T_Firm b ").append("where a.FirmID = b.FirmID order by a.FirmID");
    this.log.debug("sql: " + localStringBuffer.toString());
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public TradeProps getCustomerTradePropsById(String paramString)
  {
    Assert.hasText(paramString);
    String str1 = "select * from T_A_FirmTradeProps where FirmID=?";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql: " + str1);
    this.log.debug("FirmID:" + arrayOfObject[0]);
    TradeProps localTradeProps = new TradeProps();
    try
    {
      List localList = getJdbcTemplate().queryForList(str1, arrayOfObject);
      if ((localList != null) && (localList.size() > 0))
      {
        Map localMap = (Map)localList.get(0);
        if (localMap.get("FirmID") != null) {
          localTradeProps.setCustomerID(localMap.get("FirmID").toString());
        }
        if (localMap.get("MaxHoldQty") != null) {
          localTradeProps.setMaxHoldQty(Long.valueOf(Long.parseLong(localMap.get("MaxHoldQty").toString())));
        }
        if (localMap.get("MinClearDeposit") != null) {
          localTradeProps.setMinClearDeposit(Double.valueOf(Double.parseDouble(localMap.get("MinClearDeposit").toString())));
        }
        if (localMap.get("MaxOverdraft") != null) {
          localTradeProps.setMaxOverdraft(Double.valueOf(Double.parseDouble(localMap.get("MaxOverdraft").toString())));
        }
        if (localMap.get("ModifyTime") != null)
        {
          String str2 = localMap.get("ModifyTime").toString();
          SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
          localTradeProps.setModifyTime(new java.sql.Date(localSimpleDateFormat.parse(str2).getTime()));
        }
      }
      return localTradeProps;
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      throw new RuntimeException("FirmTradeProps表中交易用户ID[" + paramString + "]不存在！");
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
      throw new RuntimeException("日期转换异常！");
    }
  }
  
  public void deleteCustomerTradePropsById(String paramString)
  {
    Assert.hasText(paramString);
    String str = "delete from T_A_FirmTradeProps where FirmID=?";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql: " + str);
    this.logger.debug("FirmID: " + arrayOfObject[0]);
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void insertCustomerTradeProps(TradeProps paramTradeProps)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("insert into T_A_FirmTradeProps(FirmID,MaxHoldQty,MinClearDeposit,MaxOverdraft,ModifyTime) values('").append(paramTradeProps.getCustomerID()).append("',").append(paramTradeProps.getMaxHoldQty()).append(",").append(paramTradeProps.getMinClearDeposit()).append(",").append(paramTradeProps.getMaxOverdraft()).append(",").append("sysdate)");
    this.log.debug("sql: " + localStringBuffer.toString());
    try
    {
      getJdbcTemplate().update(localStringBuffer.toString());
    }
    catch (DataIntegrityViolationException localDataIntegrityViolationException)
    {
      localDataIntegrityViolationException.printStackTrace();
      throw new RuntimeException("FirmTradeProps表中不存在此交易商，不能插入相同的记录！");
    }
  }
  
  public void updateCustomerTradeProps(TradeProps paramTradeProps)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("update T_A_FirmTradeProps set MaxHoldQty=").append(paramTradeProps.getMaxHoldQty()).append(",MinClearDeposit=").append(paramTradeProps.getMinClearDeposit()).append(",MaxOverdraft=").append(paramTradeProps.getMaxOverdraft()).append(",ModifyTime=sysdate where FirmID=").append(paramTradeProps.getCustomerID());
    this.log.debug("sql: " + localStringBuffer.toString());
    getJdbcTemplate().update(localStringBuffer.toString());
  }
  
  public List getTradePropss(TradeProps paramTradeProps)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select d.FirmID,d.FirmName, nvl(c.MaxHoldQty,nvl(b.MaxHoldQty,a.MaxHoldQty)) useMaxHoldQty,nvl(c.MinClearDeposit,nvl(b.MinClearDeposit,a.MinClearDeposit)) useMinClearDeposit,nvl(c.MaxOverdraft,nvl(b.MaxOverdraft,a.MaxOverdraft)) useMaxOverdraft,").append("c.MaxHoldQty customerMaxHoldQty,b.MaxHoldQty groupMaxHoldQty,a.MaxHoldQty defMaxHoldQty,").append("c.MinClearDeposit customerMinClearDeposit,b.MinClearDeposit groupMinClearDeposit,a.MinClearDeposit defMinClearDeposit,").append("c.MaxOverdraft customerMaxOverdraft,b.MaxOverdraft groupMaxOverdraft,a.MaxOverdraft defMaxOverdraft ").append("from T_A_FirmDefProps a,T_A_GroupTradeProps b,T_A_FirmTradeProps c,T_Firm d ").append("where d.FirmID = c.FirmID(+) and d.GroupID = b.GroupID(+) and d.Status <> 2 order by d.FirmID");
    this.log.debug("sql: " + localStringBuffer.toString());
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public List getTradePropsTFirm(TradeProps paramTradeProps)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.*,case when maxholdqty = -1 then '无限制' else maxholdqty||'' end as maxholdqty from T_Firm a where 1=1 ");
    ArrayList localArrayList = new ArrayList();
    if ((paramTradeProps != null) && (paramTradeProps.getCustomerID() != null) && (!"".equals(paramTradeProps.getCustomerID()))) {
      localStringBuffer.append(" and a.firmID like '%" + paramTradeProps.getCustomerID() + "%'");
    }
    Object[] arrayOfObject = null;
    if ((localArrayList != null) && (localArrayList.size() > 0)) {
      arrayOfObject = localArrayList.toArray();
    }
    this.log.debug("sql: " + localStringBuffer.toString());
    if (arrayOfObject != null)
    {
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public TradeProps getTradePropsTFirmById(String paramString)
  {
    String str = "select a.* from T_Firm a where a.firmID = ?";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    TradeProps localTradeProps = new TradeProps();
    List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
    if ((localList != null) && (localList.size() > 0))
    {
      Map localMap = (Map)localList.get(0);
      if (localMap.get("FirmID") != null) {
        localTradeProps.setCustomerID(paramString);
      }
      if (localMap.get("MaxHoldQty") != null) {
        localTradeProps.setMaxHoldQty(Long.valueOf(Long.parseLong(localMap.get("MaxHoldQty").toString())));
      }
      if (localMap.get("MinClearDeposit") != null) {
        localTradeProps.setMinClearDeposit(Double.valueOf(Double.parseDouble(localMap.get("MinClearDeposit").toString())));
      }
      if (localMap.get("MaxOverdraft") != null) {
        localTradeProps.setMaxOverdraft(Double.valueOf(Double.parseDouble(localMap.get("MaxOverdraft").toString())));
      }
      if (localMap.get("VirtualFunds") != null) {
        localTradeProps.setVirtualFunds(Double.valueOf(Double.parseDouble(localMap.get("VirtualFunds").toString())));
      }
    }
    return localTradeProps;
  }
  
  public void saveTradePropsTFirm(TradeProps paramTradeProps)
  {
    String str = "update T_Firm set MaxHoldQty=?, MinClearDeposit=?, MaxOverdraft=?,VirtualFunds=? where firmID = ?";
    Object[] arrayOfObject = { paramTradeProps.getMaxHoldQty(), paramTradeProps.getMinClearDeposit(), paramTradeProps.getMaxOverdraft(), paramTradeProps.getVirtualFunds(), paramTradeProps.getCustomerID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  class GroupTradePropsRowMapper
    implements RowMapper
  {
    GroupTradePropsRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToGroupTradeProps(paramResultSet);
    }
    
    private TradeProps rsToGroupTradeProps(ResultSet paramResultSet)
      throws SQLException
    {
      TradeProps localTradeProps = new TradeProps();
      localTradeProps.setGroupID(new Long(paramResultSet.getLong("GroupID")));
      if (paramResultSet.getObject("MaxHoldQty") != null) {
        localTradeProps.setMaxHoldQty(new Long(paramResultSet.getLong("MaxHoldQty")));
      }
      if (paramResultSet.getObject("MinClearDeposit") != null) {
        localTradeProps.setMinClearDeposit(new Double(paramResultSet.getDouble("MinClearDeposit")));
      }
      if (paramResultSet.getObject("MaxOverdraft") != null) {
        localTradeProps.setMaxOverdraft(new Double(paramResultSet.getDouble("MaxOverdraft")));
      }
      localTradeProps.setModifyTime(paramResultSet.getTimestamp("ModifyTime"));
      return localTradeProps;
    }
  }
  
  class CustomerDefPropsRowMapper
    implements RowMapper
  {
    CustomerDefPropsRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToCustomerTradeProps(paramResultSet);
    }
    
    private TradeProps rsToCustomerTradeProps(ResultSet paramResultSet)
      throws SQLException
    {
      TradeProps localTradeProps = new TradeProps();
      localTradeProps.setMaxHoldQty(new Long(paramResultSet.getLong("MaxHoldQty")));
      localTradeProps.setMinClearDeposit(new Double(paramResultSet.getDouble("MinClearDeposit")));
      localTradeProps.setMaxOverdraft(new Double(paramResultSet.getDouble("MaxOverdraft")));
      return localTradeProps;
    }
  }
  
  class CustomerTradePropsRowMapper
    implements RowMapper
  {
    CustomerTradePropsRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToCustomerTradeProps(paramResultSet);
    }
    
    private TradeProps rsToCustomerTradeProps(ResultSet paramResultSet)
      throws SQLException
    {
      TradeProps localTradeProps = new TradeProps();
      localTradeProps.setCustomerID(paramResultSet.getString("FirmID"));
      if (paramResultSet.getObject("MaxHoldQty") != null) {
        localTradeProps.setMaxHoldQty(new Long(paramResultSet.getLong("MaxHoldQty")));
      }
      if (paramResultSet.getObject("MinClearDeposit") != null) {
        localTradeProps.setMinClearDeposit(new Double(paramResultSet.getDouble("MinClearDeposit")));
      }
      if (paramResultSet.getObject("MaxOverdraft") != null) {
        localTradeProps.setMaxOverdraft(new Double(paramResultSet.getDouble("MaxOverdraft")));
      }
      localTradeProps.setModifyTime(paramResultSet.getTimestamp("ModifyTime"));
      return localTradeProps;
    }
  }
}
