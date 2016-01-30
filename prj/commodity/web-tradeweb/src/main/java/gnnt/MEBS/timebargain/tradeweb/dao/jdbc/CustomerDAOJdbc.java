package gnnt.MEBS.timebargain.tradeweb.dao.jdbc;

import gnnt.MEBS.timebargain.tradeweb.dao.CustomerDAO;
import gnnt.MEBS.timebargain.tradeweb.model.Customer;
import gnnt.MEBS.timebargain.tradeweb.model.CustomerFunds;
import gnnt.MEBS.timebargain.tradeweb.model.Trader;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class CustomerDAOJdbc
  extends BaseDAOJdbc
  implements CustomerDAO
{
  private Log log = LogFactory.getLog(CustomerDAOJdbc.class);
  
  public Customer getCustomerById(String paramString)
  {
    Assert.hasText(paramString);
    String str = "select * from T_Customer where CustomerID=? and Status<>2";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql: " + str);
    this.log.debug("CustomerID:" + arrayOfObject[0]);
    Customer localCustomer = null;
    try
    {
      localCustomer = (Customer)getJdbcTemplate().queryForObject(str, arrayOfObject, new CustomerRowMapper());
      return localCustomer;
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      throw new RuntimeException("客户编码[" + paramString + "]不存在！");
    }
  }
  
  public Trader getTraderById(String paramString)
  {
    Assert.hasText(paramString);
    String str = "select * from M_Trader where TraderID=?";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql: " + str);
    this.log.debug("TraderID:" + arrayOfObject[0]);
    Trader localTrader = null;
    try
    {
      localTrader = (Trader)getJdbcTemplate().queryForObject(str, arrayOfObject, new TraderRowMapper());
      return localTrader;
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      throw new RuntimeException("交易员代码[" + paramString + "]不存在！");
    }
  }
  
  public CustomerFunds getCustomerFundsById(String paramString)
  {
    Assert.hasText(paramString);
    String str = "select * from CustomerFunds where CustomerID=?";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql: " + str);
    this.log.debug("CustomerID:" + arrayOfObject[0]);
    CustomerFunds localCustomerFunds = null;
    try
    {
      localCustomerFunds = (CustomerFunds)getJdbcTemplate().queryForObject(str, arrayOfObject, new CustomerFundsRowMapper());
      return localCustomerFunds;
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      throw new RuntimeException("客户编码[" + paramString + "]不存在！");
    }
  }
  
  public void updateCustomerPassword(Customer paramCustomer)
  {
    String str = "update T_Customer set Password=? where CustomerID=?";
    Object[] arrayOfObject = { paramCustomer.getPassword(), paramCustomer.getCustomerID().trim() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void updateTraderPassword(Trader paramTrader)
  {
    String str = "update M_Trader set Password=? where TraderID=?";
    Object[] arrayOfObject = { paramTrader.getPassword(), paramTrader.getTraderID().trim() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  class TraderRowMapper
    implements RowMapper
  {
    TraderRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToTrader(paramResultSet);
    }
    
    private Trader rsToTrader(ResultSet paramResultSet)
      throws SQLException
    {
      Trader localTrader = new Trader();
      localTrader.setTraderID(paramResultSet.getString("TraderID"));
      localTrader.setName(paramResultSet.getString("Name"));
      localTrader.setPassword(paramResultSet.getString("Password"));
      localTrader.setStatus(paramResultSet.getString("Status"));
      return localTrader;
    }
  }
  
  class CustomerFundsRowMapper
    implements RowMapper
  {
    CustomerFundsRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToCustomerFunds(paramResultSet);
    }
    
    private CustomerFunds rsToCustomerFunds(ResultSet paramResultSet)
      throws SQLException
    {
      CustomerFunds localCustomerFunds = new CustomerFunds();
      localCustomerFunds.setCustomerID(paramResultSet.getString("CustomerID"));
      localCustomerFunds.setBalance(new Double(paramResultSet.getDouble("Balance")));
      localCustomerFunds.setFrozenFunds(new Double(paramResultSet.getDouble("FrozenFunds")));
      localCustomerFunds.setRuntimeFL(new Double(paramResultSet.getDouble("RuntimeFL")));
      localCustomerFunds.setClearFL(new Double(paramResultSet.getDouble("ClearFL")));
      localCustomerFunds.setRuntimeMargin(new Double(paramResultSet.getDouble("RuntimeMargin")));
      localCustomerFunds.setClearMargin(new Double(paramResultSet.getDouble("ClearMargin")));
      localCustomerFunds.setVirtualFunds(new Double(paramResultSet.getDouble("VirtualFunds")));
      return localCustomerFunds;
    }
  }
  
  class CustomerRowMapper
    implements RowMapper
  {
    CustomerRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToCustomer(paramResultSet);
    }
    
    private Customer rsToCustomer(ResultSet paramResultSet)
      throws SQLException
    {
      Customer localCustomer = new Customer();
      localCustomer.setCustomerID(paramResultSet.getString("CustomerID"));
      localCustomer.setGroupID(new Long(paramResultSet.getLong("GroupID")));
      localCustomer.setPassword(paramResultSet.getString("Password"));
      localCustomer.setCustomerName(paramResultSet.getString("CustomerName"));
      localCustomer.setPhone(paramResultSet.getString("Phone"));
      localCustomer.setAddress(paramResultSet.getString("Address"));
      localCustomer.setStatus(new Short(paramResultSet.getShort("Status")));
      localCustomer.setNote(paramResultSet.getString("Note"));
      localCustomer.setModifyTime(paramResultSet.getTimestamp("ModifyTime"));
      localCustomer.setCreateTime(paramResultSet.getTimestamp("CreateTime"));
      return localCustomer;
    }
  }
}
