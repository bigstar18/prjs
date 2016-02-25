package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.CmdtySortDAO;
import gnnt.MEBS.timebargain.manage.model.CmdtySort;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class CmdtySortDAOJdbc
  extends BaseDAOJdbc
  implements CmdtySortDAO
{
  private Log log = LogFactory.getLog(CmdtySortDAOJdbc.class);
  
  public CmdtySort getCmdtySortById(Long paramLong)
  {
    Assert.notNull(paramLong);
    String str = "select * from T_A_CMDTYSORT where SortID=?";
    Object[] arrayOfObject = { paramLong };
    this.log.debug("sql: " + str);
    this.log.debug("sortID:" + arrayOfObject[0]);
    CmdtySort localCmdtySort = null;
    try
    {
      localCmdtySort = (CmdtySort)getJdbcTemplate().queryForObject(str, arrayOfObject, new CmdtySortRowMapper());
      return localCmdtySort;
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      throw new RuntimeException("商品分类ID[" + paramLong + "]不存在！");
    }
  }
  
  public List getCmdtySorts(CmdtySort paramCmdtySort)
  {
    StringBuffer localStringBuffer = new StringBuffer("select * from T_A_CMDTYSORT");
    this.log.debug("sql: " + localStringBuffer.toString());
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public void insertCmdtySort(CmdtySort paramCmdtySort)
  {
    String str = "insert into T_A_CMDTYSORT(SortID,SortName) values(SEQ_T_A_CMDTYSORT.nextval,?)";
    Object[] arrayOfObject = { paramCmdtySort.getSortName() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (DataIntegrityViolationException localDataIntegrityViolationException)
    {
      localDataIntegrityViolationException.printStackTrace();
      throw new RuntimeException("主键重复，不能插入相同的记录！");
    }
  }
  
  public void updateCmdtySort(CmdtySort paramCmdtySort)
  {
    String str = "update T_A_CMDTYSORT set SortName=? where SortID=?";
    Object[] arrayOfObject = { paramCmdtySort.getSortName(), paramCmdtySort.getSortID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void deleteCmdtySortById(Long paramLong)
  {
    Assert.notNull(paramLong);
    Object[] arrayOfObject = { paramLong };
    String str = "select count(*) from T_A_BREED where SortID=?";
    if (getJdbcTemplate().queryForInt(str, arrayOfObject) > 0) {
      throw new RuntimeException("此市场还有商品品种，不能删除！");
    }
    str = "select count(*) from T_COMMODITY where SortID=?";
    if (getJdbcTemplate().queryForInt(str, arrayOfObject) > 0) {
      throw new RuntimeException("此市场还有商品，不能删除！");
    }
    str = "delete from T_A_CMDTYSORT where SortID=?";
    this.log.debug("sql: " + str);
    this.logger.debug("CmdtySortID: " + arrayOfObject[0]);
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public CmdtySort getGroupSortHoldById(Long paramLong1, Long paramLong2)
  {
    Assert.notNull(paramLong1);
    Assert.notNull(paramLong2);
    String str = "select * from T_A_GROUPSORTHOLD where GroupID=? and SortID=?";
    Object[] arrayOfObject = { paramLong1, paramLong2 };
    this.log.debug("sql: " + str);
    this.log.debug("groupID:" + arrayOfObject[0]);
    this.log.debug("sortID:" + arrayOfObject[1]);
    CmdtySort localCmdtySort = null;
    try
    {
      localCmdtySort = (CmdtySort)getJdbcTemplate().queryForObject(str, arrayOfObject, new GroupSortHoldRowMapper());
      return localCmdtySort;
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      throw new RuntimeException("组ID[" + paramLong1 + "]，分类ID[" + paramLong2 + "]的持仓不存在！");
    }
  }
  
  public List getGroupSortHolds(CmdtySort paramCmdtySort)
  {
    StringBuffer localStringBuffer = new StringBuffer("select a.*,b.GroupName,c.SortName from T_A_GROUPSORTHOLD a,CustomerGroup b,CmdtySort c where a.GroupID=b.GroupID and a.SortID=c.SortID");
    this.log.debug("sql: " + localStringBuffer.toString());
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public void insertGroupSortHold(CmdtySort paramCmdtySort)
  {
    String str = "insert into GroupSortHold(GroupID,SortID,MaxHoldQty,ModifyTime) values(?,?,?,sysdate)";
    Object[] arrayOfObject = { paramCmdtySort.getGroupID(), paramCmdtySort.getSortID(), paramCmdtySort.getMaxHoldQty() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (DataIntegrityViolationException localDataIntegrityViolationException)
    {
      throw new RuntimeException("主键重复，不能插入相同的记录！");
    }
  }
  
  public void updateGroupSortHold(CmdtySort paramCmdtySort)
  {
    String str = "update GroupSortHold set ModifyTime=sysdate,MaxHoldQty=? where GroupID=? and SortID=?";
    Object[] arrayOfObject = { paramCmdtySort.getMaxHoldQty(), paramCmdtySort.getGroupID(), paramCmdtySort.getSortID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void deleteGroupSortHoldById(Long paramLong1, Long paramLong2)
  {
    Assert.notNull(paramLong1);
    Assert.notNull(paramLong2);
    String str = "delete from GroupSortHold where GroupID=? and SortID=?";
    Object[] arrayOfObject = { paramLong1, paramLong2 };
    this.log.debug("sql: " + str);
    this.log.debug("groupID:" + arrayOfObject[0]);
    this.log.debug("sortID:" + arrayOfObject[1]);
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public CmdtySort getCustomerSortHoldById(String paramString, Long paramLong)
  {
    Assert.hasText(paramString);
    Assert.notNull(paramLong);
    String str = "select * from CustomerSortHold where CustomerID=? and SortID=?";
    Object[] arrayOfObject = { paramString, paramLong };
    this.log.debug("sql: " + str);
    this.log.debug("customerID:" + arrayOfObject[0]);
    this.log.debug("sortID:" + arrayOfObject[1]);
    CmdtySort localCmdtySort = null;
    try
    {
      localCmdtySort = (CmdtySort)getJdbcTemplate().queryForObject(str, arrayOfObject, new CustomerSortHoldRowMapper());
      return localCmdtySort;
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      throw new RuntimeException("客户ID[" + paramString + "]，分类ID[" + paramLong + "]的持仓不存在！");
    }
  }
  
  public List getCustomerSortHolds(CmdtySort paramCmdtySort)
  {
    StringBuffer localStringBuffer = new StringBuffer("select a.*,b.CustomerName,c.SortName from CustomerSortHold a,Customer b,CmdtySort c where a.CustomerID=b.CustomerID and a.SortID=c.SortID");
    this.log.debug("sql: " + localStringBuffer.toString());
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public void insertCustomerSortHold(CmdtySort paramCmdtySort)
  {
    String str = "insert into CustomerSortHold(CustomerID,SortID,MaxHoldQty,ModifyTime) values(?,?,?,sysdate)";
    Object[] arrayOfObject = { paramCmdtySort.getCustomerID(), paramCmdtySort.getSortID(), paramCmdtySort.getMaxHoldQty() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (DataIntegrityViolationException localDataIntegrityViolationException)
    {
      throw new RuntimeException("主键重复，不能插入相同的记录！");
    }
  }
  
  public void updateCustomerSortHold(CmdtySort paramCmdtySort)
  {
    String str = "update CustomerSortHold set ModifyTime=sysdate,MaxHoldQty=? where CustomerID=? and SortID=?";
    Object[] arrayOfObject = { paramCmdtySort.getMaxHoldQty(), paramCmdtySort.getCustomerID(), paramCmdtySort.getSortID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void deleteCustomerSortHoldById(String paramString, Long paramLong)
  {
    Assert.hasText(paramString);
    Assert.notNull(paramLong);
    String str = "delete from CustomerSortHold where CustomerID=? and SortID=?";
    Object[] arrayOfObject = { paramString, paramLong };
    this.log.debug("sql: " + str);
    this.log.debug("customerID:" + arrayOfObject[0]);
    this.log.debug("sortID:" + arrayOfObject[1]);
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  class CustomerSortHoldRowMapper
    implements RowMapper
  {
    CustomerSortHoldRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToCmdtySort(paramResultSet);
    }
    
    private CmdtySort rsToCmdtySort(ResultSet paramResultSet)
      throws SQLException
    {
      CmdtySort localCmdtySort = new CmdtySort();
      localCmdtySort.setCustomerID(paramResultSet.getString("CustomerID"));
      localCmdtySort.setSortID(new Long(paramResultSet.getLong("SortID")));
      localCmdtySort.setMaxHoldQty(new Long(paramResultSet.getLong("MaxHoldQty")));
      localCmdtySort.setModifyTime(paramResultSet.getTimestamp("ModifyTime"));
      return localCmdtySort;
    }
  }
  
  class GroupSortHoldRowMapper
    implements RowMapper
  {
    GroupSortHoldRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToCmdtySort(paramResultSet);
    }
    
    private CmdtySort rsToCmdtySort(ResultSet paramResultSet)
      throws SQLException
    {
      CmdtySort localCmdtySort = new CmdtySort();
      localCmdtySort.setGroupID(new Long(paramResultSet.getLong("GroupID")));
      localCmdtySort.setSortID(new Long(paramResultSet.getLong("SortID")));
      localCmdtySort.setMaxHoldQty(new Long(paramResultSet.getLong("MaxHoldQty")));
      localCmdtySort.setModifyTime(paramResultSet.getTimestamp("ModifyTime"));
      return localCmdtySort;
    }
  }
  
  class CmdtySortRowMapper
    implements RowMapper
  {
    CmdtySortRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToCmdtySort(paramResultSet);
    }
    
    private CmdtySort rsToCmdtySort(ResultSet paramResultSet)
      throws SQLException
    {
      CmdtySort localCmdtySort = new CmdtySort();
      localCmdtySort.setSortID(new Long(paramResultSet.getLong("SortID")));
      localCmdtySort.setSortName(paramResultSet.getString("SortName"));
      return localCmdtySort;
    }
  }
}
