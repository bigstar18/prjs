package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.CustomerDAO;
import gnnt.MEBS.timebargain.manage.model.Customer;
import gnnt.MEBS.timebargain.manage.model.CustomerFunds;
import gnnt.MEBS.timebargain.manage.util.SqlUtil;
import gnnt.MEBS.timebargain.manage.util.StringUtil;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.util.Assert;

public class CustomerDAOJdbc
  extends BaseDAOJdbc
  implements CustomerDAO
{
  private Log log = LogFactory.getLog(CustomerDAOJdbc.class);
  
  public Customer getCustomerById(String paramString)
  {
    Assert.hasText(paramString);
    String str1 = "select * from T_Firm where firmID = ?";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql: " + str1);
    this.log.debug("FirmID:" + arrayOfObject[0]);
    Customer localCustomer = new Customer();
    String str2 = "";
    String str3 = "";
    String str4 = "";
    String str5 = "";
    try
    {
      List localList = getJdbcTemplate().queryForList(str1, arrayOfObject);
      if ((localList != null) && (localList.size() > 0))
      {
        Map localMap = (Map)localList.get(0);
        if (localMap.get("FirmID") != null)
        {
          str2 = localMap.get("FirmID").toString();
          localCustomer.setCustomerID(str2);
        }
        if (localMap.get("FirmName") != null)
        {
          str3 = localMap.get("FirmName").toString();
          localCustomer.setFirmName(str3);
        }
        if (localMap.get("status") != null)
        {
          str4 = localMap.get("status").toString();
          localCustomer.setStatus(Short.valueOf(Short.parseShort(str4)));
        }
      }
      return localCustomer;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("交易用户ID[" + paramString + "]不存在！");
    }
  }
  
  public void updateBack(Customer paramCustomer)
  {
    String str1 = "select count(*) from T_FirmHoldSum where FirmID = " + paramCustomer.getCustomerID();
    if (getJdbcTemplate().queryForInt(str1) > 0) {
      throw new RuntimeException("此交易商还有持仓，不能退市！");
    }
    String str2 = "update T_Firm set status=2 where firmID = ?";
    Object[] arrayOfObject = { paramCustomer.getCustomerID() };
    this.log.debug("sql: " + str2);
    this.log.debug("FirmID:" + arrayOfObject[0]);
    getJdbcTemplate().update(str2, arrayOfObject);
  }
  
  public void updateGoBack(Customer paramCustomer)
  {
    String str = "update T_Firm set status=0 where firmID = ?";
    Object[] arrayOfObject = { paramCustomer.getCustomerID() };
    this.log.debug("sql: " + str);
    this.log.debug("FirmID:" + arrayOfObject[0]);
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public Customer getKHCustomerById(String paramString)
  {
    Assert.hasText(paramString);
    String str = "select * from T_Customer where CustomerID=?";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql: " + str);
    this.log.debug("CustomerID:" + arrayOfObject[0]);
    Customer localCustomer = null;
    try
    {
      localCustomer = (Customer)getJdbcTemplate().queryForObject(str, arrayOfObject, new KHCustomerRowMapper());
      return localCustomer;
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      localIncorrectResultSizeDataAccessException.printStackTrace();
      throw new RuntimeException("交易用户ID[" + paramString + "]不存在！");
    }
  }
  
  public String getCustomerName(String paramString)
  {
    Assert.hasText(paramString);
    String str = "select FirmName from T_Firm where FirmID=?";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql: " + str);
    this.log.debug("FirmID:" + arrayOfObject[0]);
    try
    {
      return (String)getJdbcTemplate().queryForObject(str, arrayOfObject, String.class);
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      this.log.error("--getCustomerName err:" + localIncorrectResultSizeDataAccessException.getMessage());
    }
    return null;
  }
  
  public String getMaxCustomerID(String paramString)
  {
    String str1 = "T_Firm";
    if ((paramString != null) && (!paramString.equals(""))) {
      str1 = paramString;
    }
    String str2 = "select max(FirmID) from " + str1;
    this.log.debug("sql: " + str2);
    return (String)getJdbcTemplate().queryForObject(str2, String.class);
  }
  
  public String getMinCustomerID(String paramString)
  {
    String str1 = "T_Firm";
    if ((paramString != null) && (!paramString.equals(""))) {
      str1 = paramString;
    }
    String str2 = "select min(FirmID) from " + str1;
    this.log.debug("sql: " + str2);
    return (String)getJdbcTemplate().queryForObject(str2, String.class);
  }
  
  public String getMaxCustomerID(String paramString, Long paramLong)
  {
    if (paramLong == null)
    {
      str1 = "T_Firm";
      if ((paramString != null) && (!paramString.equals(""))) {
        str1 = paramString;
      }
      str2 = "select max(FirmID) from " + str1;
      this.log.debug("sql: " + str2);
      return (String)getJdbcTemplate().queryForObject(str2, String.class);
    }
    String str1 = "";
    String str2 = "where a.groupid=? ";
    if ((paramString != null) && (!paramString.equals("")))
    {
      str1 = "," + paramString + " b ";
      str2 = str2 + " and b.FirmID=a.FirmID ";
    }
    String str3 = "select max(a.FirmID) from T_Firm a " + str1 + str2;
    this.log.debug("sql: " + str3);
    return (String)getJdbcTemplate().queryForObject(str3, new Object[] { paramLong }, String.class);
  }
  
  public String getMinCustomerID(String paramString, Long paramLong)
  {
    if (paramLong == null)
    {
      str1 = "T_Firm";
      if ((paramString != null) && (!paramString.equals(""))) {
        str1 = paramString;
      }
      str2 = "select min(FirmID) from " + str1;
      this.log.debug("sql: " + str2);
      return (String)getJdbcTemplate().queryForObject(str2, String.class);
    }
    String str1 = "";
    String str2 = "where a.groupid=? ";
    if ((paramString != null) && (!paramString.equals("")))
    {
      str1 = "," + paramString + " b ";
      str2 = str2 + " and b.FirmID=a.FirmID ";
    }
    String str3 = "select min(a.FirmID) from T_Firm a " + str1 + str2;
    this.log.debug("sql: " + str3);
    return (String)getJdbcTemplate().queryForObject(str3, new Object[] { paramLong }, String.class);
  }
  
  public List getCustomers(Customer paramCustomer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.*,cf.name firmName,cf.createtime, b.counts customerCounts, t.tcounts from T_firm a,(select T_customer.firmid, count(T_customer.customerid) counts from T_customer group by T_customer.firmid) b,(select M_trader.firmid, count(M_trader.traderid) tcounts from M_trader group by M_trader.firmid) t,M_firm cf where a.firmid = b.firmid(+) and a.firmid = t.firmid(+) and a.firmid = cf.firmid");
    ArrayList localArrayList = new ArrayList();
    if (paramCustomer != null)
    {
      if ((paramCustomer.getCustomerID() != null) && (!paramCustomer.getCustomerID().equals("")))
      {
        localStringBuffer.append(" and a.FirmID like ? || '%'");
        localArrayList.add(paramCustomer.getCustomerID());
      }
      if ((paramCustomer.getCustomerName() != null) && (!paramCustomer.getCustomerName().equals("")))
      {
        localStringBuffer.append(" and cf.name like ? || '%'");
        localArrayList.add(paramCustomer.getCustomerName());
      }
      if (paramCustomer.getStatus() != null)
      {
        localStringBuffer.append(" and a.Status=?");
        localArrayList.add(paramCustomer.getStatus());
      }
    }
    localStringBuffer.append(" order by a.firmID");
    Object[] arrayOfObject = localArrayList.toArray();
    this.log.debug("sql: " + localStringBuffer.toString());
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public List getKHCustomers(Customer paramCustomer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.* ").append("from T_Customer a,T_Firm c where a.FirmID = c.FirmID");
    ArrayList localArrayList = new ArrayList();
    if (paramCustomer != null)
    {
      if ((paramCustomer.getCustomerID() != null) && (!paramCustomer.getCustomerID().equals("")))
      {
        localStringBuffer.append(" and a.firmID = ?");
        localArrayList.add(paramCustomer.getCustomerID());
      }
      if ((paramCustomer.getCustomerName() != null) && (!paramCustomer.getCustomerName().equals("")))
      {
        localStringBuffer.append(" and c.firmName like ? || '%'");
        localArrayList.add(paramCustomer.getCustomerName());
      }
      if (paramCustomer.getStatus() != null)
      {
        localStringBuffer.append(" and a.Status=?");
        localArrayList.add(paramCustomer.getStatus());
      }
      if ((paramCustomer.getFirmID() != null) && (paramCustomer.getFirmID().equals("")))
      {
        localStringBuffer.append(" and c.firmID = ?");
        localArrayList.add(paramCustomer.getFirmID());
      }
    }
    Object[] arrayOfObject = localArrayList.toArray();
    this.log.debug("sql: " + localStringBuffer.toString());
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public List getFirmGroup(Customer paramCustomer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select fg.*,f.* from T_A_FirmGroup fg, T_firm f where fg.groupid = f.groupid");
    ArrayList localArrayList = new ArrayList();
    if (paramCustomer.getGroupID() != null)
    {
      localStringBuffer.append(" and fg.groupid = ?");
      localArrayList.add(paramCustomer.getGroupID());
    }
    Object[] arrayOfObject = null;
    if ((localArrayList != null) && (localArrayList.size() > 0))
    {
      arrayOfObject = localArrayList.toArray();
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
  
  public List getCustomerCounts(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.firmID,a.firmname,b.counts customerCounts from T_firm a, (select T_customer.firmid, count(T_customer.customerid) counts from T_customer group by T_customer.firmid) b where a.firmid = b.firmid(+)");
    ArrayList localArrayList = new ArrayList();
    if ((paramString != null) && (!"".equals(paramString)))
    {
      localStringBuffer.append(" and a.firmID = ?");
      localArrayList.add(paramString);
      Object[] arrayOfObject = localArrayList.toArray();
      this.log.debug("sql: " + localStringBuffer.toString());
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public List getFirmAndCustomer(Customer paramCustomer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.*,b.groupName ").append("from T_Firm a,T_A_FirmGroup b where a.groupid = b.groupID(+)");
    ArrayList localArrayList = new ArrayList();
    if (paramCustomer != null)
    {
      if (paramCustomer.getGroupID() != null)
      {
        localStringBuffer.append(" and b.groupID=?");
        localArrayList.add(paramCustomer.getGroupID());
      }
      if ((paramCustomer.getCustomerID() != null) && (!paramCustomer.getCustomerID().equals("")))
      {
        localStringBuffer.append(" and a.FirmID like ? || '%'");
        localArrayList.add(paramCustomer.getCustomerID());
      }
      if ((paramCustomer.getCustomerName() != null) && (!paramCustomer.getCustomerName().equals("")))
      {
        localStringBuffer.append(" and a.FirmName like ? || '%'");
        localArrayList.add(paramCustomer.getCustomerName());
      }
      if (paramCustomer.getStatus() != null)
      {
        localStringBuffer.append(" and a.Status=?");
        localArrayList.add(paramCustomer.getStatus());
      }
    }
    Object[] arrayOfObject = localArrayList.toArray();
    this.log.debug("sql: " + localStringBuffer.toString());
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public void insertCustomer(Customer paramCustomer)
  {
    String str = "insert into T_Firm(FirmID,Status,ModifyTime) values(?,0,sysdate)";
    Object[] arrayOfObject = { paramCustomer.getCustomerID() };
    try
    {
      getJdbcTemplate().update(str, arrayOfObject);
    }
    catch (DataIntegrityViolationException localDataIntegrityViolationException)
    {
      throw new RuntimeException("主键重复，不能插入相同的记录！");
    }
  }
  
  public void insertKHCustomer(Customer paramCustomer)
  {
    String str1 = paramCustomer.getFirmID() + paramCustomer.getCode();
    String str2 = "select count(*) from T_customer c where c.customerID='" + str1 + "'";
    int i = getJdbcTemplate().queryForInt(str2);
    if (i > 0) {
      throw new RuntimeException("主键重复，不能插入相同的记录！");
    }
    String str3 = "insert into T_Customer (CustomerID,FirmID,Code,Status,CreateTime,ModifyTime) values (?,?,?,?,sysdate,sysdate)";
    Object[] arrayOfObject = { str1, paramCustomer.getFirmID(), paramCustomer.getCode(), paramCustomer.getStatus() };
    this.log.debug("sql: " + str3);
    for (int j = 0; j < arrayOfObject.length; j++) {
      this.log.debug("params[" + j + "]: " + arrayOfObject[j]);
    }
    try
    {
      getJdbcTemplate().update(str3, arrayOfObject);
    }
    catch (DataIntegrityViolationException localDataIntegrityViolationException)
    {
      localDataIntegrityViolationException.printStackTrace();
      throw new RuntimeException("主键重复，不能插入相同的记录！");
    }
  }
  
  public Map insertCustomerRegister(Customer paramCustomer)
  {
    HashMap localHashMap1 = new HashMap();
    String str1 = (String)getJdbcTemplate().queryForObject("select max(CustomerID) from T_Customer", String.class);
    if (str1 == null) {
      str1 = "100000";
    } else {
      str1 = String.valueOf(Integer.parseInt(str1) + 1);
    }
    paramCustomer.setCustomerID(str1);
    CustomerAddStoredProcedure localCustomerAddStoredProcedure = new CustomerAddStoredProcedure(getDataSource());
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("p_CustomerID", paramCustomer.getCustomerID());
    localHashMap2.put("p_GroupID", paramCustomer.getGroupID());
    localHashMap2.put("p_Password", paramCustomer.getPassword());
    localHashMap2.put("p_CustomerName", paramCustomer.getCustomerName());
    localHashMap2.put("p_Phone", paramCustomer.getPhone());
    localHashMap2.put("p_Address", paramCustomer.getAddress());
    localHashMap2.put("p_Status", paramCustomer.getStatus());
    localHashMap2.put("p_Note", paramCustomer.getNote());
    localHashMap2.put("p_retMsg", "");
    Map localMap = localCustomerAddStoredProcedure.execute(localHashMap2);
    printMap(localMap);
    int i = ((Integer)localMap.get("ret")).intValue();
    String str2 = (String)localMap.get("p_retMsg");
    String str3 = "";
    switch (i)
    {
    case -1: 
      str3 = "相同客户ID存在！";
      break;
    case -3: 
      str3 = "市场客户后缀已用完，请增加市场客户后缀后再增加客户！";
      break;
    case -100: 
      str3 = "执行存储失败！";
    }
    if ((str2 != null) && (!str2.equals(""))) {
      str3 = str2 + "市场由于没有设置摊位号或者市场的后缀已用完，没有为他们自动生成客户映射！";
    }
    localHashMap1.put("ret", new Integer(i));
    localHashMap1.put("msg", str3);
    localHashMap1.put("customerID", str1);
    return localHashMap1;
  }
  
  public void updateCustomer(Customer paramCustomer)
  {
    Assert.hasText(paramCustomer.getCustomerID());
    CustomerChgStoredProcedure localCustomerChgStoredProcedure = new CustomerChgStoredProcedure(getDataSource());
    HashMap localHashMap = new HashMap();
    localHashMap.put("p_CustomerID", paramCustomer.getCustomerID());
    localHashMap.put("p_GroupID", paramCustomer.getGroupID());
    localHashMap.put("p_CustomerName", paramCustomer.getCustomerName());
    localHashMap.put("p_Phone", paramCustomer.getPhone());
    localHashMap.put("p_Address", paramCustomer.getAddress());
    localHashMap.put("p_Status", paramCustomer.getStatus());
    localHashMap.put("p_Note", paramCustomer.getNote());
    localHashMap.put("p_ValidDate", paramCustomer.getValidDate());
    Map localMap = localCustomerChgStoredProcedure.execute(localHashMap);
    printMap(localMap);
    int i = ((Integer)localMap.get("ret")).intValue();
    String str1 = (String)localMap.get("p_retMsg");
    String str2 = "";
    switch (i)
    {
    case -1: 
      str2 = "此客户还有持仓，不能退市！";
    }
    if (i != 1) {
      throw new RuntimeException(str2);
    }
    if ((str1 != null) && (!str1.equals(""))) {
      throw new RuntimeException("存盘成功，但" + str1 + "市场由于没有设置摊位号或者市场的后缀已用完，没有为他们自动生成客户映射！");
    }
  }
  
  public void updateKHCustomer(Customer paramCustomer)
  {
    Assert.hasText(paramCustomer.getCustomerID());
    String str = "update T_Customer set Status=? where customerID=?";
    Object[] arrayOfObject = { paramCustomer.getStatus(), paramCustomer.getCustomerID() };
    this.log.debug("sql:" + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void updateCustomerPassword(String paramString1, String paramString2, String paramString3)
  {
    String str1 = "select Password from T_Firm where firmID='" + paramString3 + "'";
    String str2 = (String)getJdbcTemplate().queryForObject(str1, String.class);
    str1 = "update T_Firm set Password=? where firmID=?";
    Object[] arrayOfObject = { StringUtil.encodePassword(paramString2, "MD5"), paramString3 };
    this.log.debug("sql: " + str1);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str1, arrayOfObject);
  }
  
  public void deleteCustomerById(String paramString)
  {
    Assert.hasText(paramString);
    CustomerDelStoredProcedure localCustomerDelStoredProcedure = new CustomerDelStoredProcedure(getDataSource());
    HashMap localHashMap = new HashMap();
    localHashMap.put("p_CustomerID", paramString);
    Map localMap = localCustomerDelStoredProcedure.execute(localHashMap);
    printMap(localMap);
    int i = ((Integer)localMap.get("ret")).intValue();
    String str = "";
    switch (i)
    {
    case -1: 
      str = "此客户还没退市，不能删除！";
    }
    if (i != 1) {
      throw new RuntimeException(str);
    }
  }
  
  public void deleteKHCustomerById(String paramString)
  {
    Assert.hasText(paramString);
    String str = "delete from T_Customer where customerID=?";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql:" + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void chgGroupById(Long paramLong, String paramString)
  {
    String str = "update T_Firm set GroupID=? where FirmID=?";
    Object[] arrayOfObject = { paramLong, paramString };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public CustomerFunds getCustomerFundsById(String paramString)
  {
    Assert.hasText(paramString);
    String str1 = "select * from T_Firm where FirmID=?";
    Object[] arrayOfObject = { paramString };
    this.log.debug("sql: " + str1);
    this.log.debug("CustomerID:" + arrayOfObject[0]);
    CustomerFunds localCustomerFunds = new CustomerFunds();
    try
    {
      List localList = getJdbcTemplate().queryForList(str1, arrayOfObject);
      String str2 = "";
      if ((localList != null) && (localList.size() > 0))
      {
        Map localMap = (Map)localList.get(0);
        if (localMap.get("FirmID") != null)
        {
          str2 = localMap.get("FirmID").toString();
          localCustomerFunds.setCustomerID(str2);
        }
        if (localMap.get("RuntimeFL") != null) {
          localCustomerFunds.setRuntimeFL(Double.valueOf(Double.parseDouble(localMap.get("RuntimeFL").toString())));
        }
        if (localMap.get("ClearFL") != null) {
          localCustomerFunds.setClearFL(Double.valueOf(Double.parseDouble(localMap.get("ClearFL").toString())));
        }
        if (localMap.get("RuntimeMargin") != null) {
          localCustomerFunds.setRuntimeMargin(Double.valueOf(Double.parseDouble(localMap.get("RuntimeMargin").toString())));
        }
        if (localMap.get("ClearMargin") != null) {
          localCustomerFunds.setClearMargin(Double.valueOf(Double.parseDouble(localMap.get("ClearMargin").toString())));
        }
        if (localMap.get("VirtualFunds") != null) {
          localCustomerFunds.setVirtualFunds(Double.valueOf(Double.parseDouble(localMap.get("VirtualFunds").toString())));
        }
      }
      return localCustomerFunds;
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      localIncorrectResultSizeDataAccessException.printStackTrace();
      throw new RuntimeException("CustomerFunds表中交易用户ID[" + paramString + "]不存在！");
    }
  }
  
  public List customerQuery(Customer paramCustomer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select a.*,case when a.Status = 0 then '正常' ").append("when a.Status = 1 then '禁止交易' ").append("when a.Status = 2 then '退市' ").append("else '' end as Status,a.ModifyTime from T_Firm a ");
    this.log.debug("sql: " + localStringBuffer.toString());
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public void addCustomerVirtualFunds(String paramString, Double paramDouble)
  {
    String str = "update T_Firm set VirtualFunds=VirtualFunds + ? where FirmID=?";
    Object[] arrayOfObject = { paramDouble, paramString };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void addCustomerBalance(String paramString, Double paramDouble)
  {
    StringBuffer localStringBuffer;
    Object[] arrayOfObject;
    if ((paramDouble != null) && (paramDouble.doubleValue() > 0.0D))
    {
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("insert into T_dailymoney ").append("(id, createtime, customerid, operation, a_tradeno, money, balance, frozenfunds, virtualfunds, runtimemargin, runtimefl) ").append("select ").append("seq_dailymoney_id.nextval, sysdate, customerid, '101', null,").append(paramDouble).append(", balance, frozenfunds, virtualfunds, runtimemargin, runtimefl ").append("from T_firmfunds where firmid = ? ");
      arrayOfObject = new Object[] { paramString };
      this.log.debug("sql: " + localStringBuffer.toString());
      this.log.debug("params[0]: " + arrayOfObject[0]);
      getJdbcTemplate().update(localStringBuffer.toString(), arrayOfObject);
    }
    if ((paramDouble != null) && (paramDouble.doubleValue() < 0.0D))
    {
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("insert into T_dailymoney ").append("(id, createtime, customerid, operation, a_tradeno, money, balance, frozenfunds, virtualfunds, runtimemargin, runtimefl) ").append("select ").append("seq_dailymoney_id.nextval, sysdate, customerid, '102', null,").append(paramDouble).append(", balance, frozenfunds, virtualfunds, runtimemargin, runtimefl ").append("from T_firmfunds where firmid = ? ");
      arrayOfObject = new Object[] { paramString };
      this.log.debug("sql: " + localStringBuffer.toString());
      this.log.debug("params[0]: " + arrayOfObject[0]);
      getJdbcTemplate().update(localStringBuffer.toString(), arrayOfObject);
    }
  }
  
  private static void printMap(Map paramMap)
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext()) {}
  }
  
  public void updateStatusFirm(Customer paramCustomer)
  {
    if (paramCustomer.getStatus() != null)
    {
      str = paramCustomer.getStatus().toString();
      if ("2".equals(str))
      {
        localObject = "select count(*) from T_FirmHoldSum where FirmID = " + paramCustomer.getFirmID();
        if (getJdbcTemplate().queryForInt((String)localObject) > 0) {
          throw new RuntimeException("此交易商还有持仓，不能退市！");
        }
      }
    }
    String str = "update T_Firm set status=? where firmID=?";
    Object localObject = { paramCustomer.getStatus(), paramCustomer.getFirmID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < localObject.length; i++) {
      this.log.debug("params[" + i + "]: " + localObject[i]);
    }
    getJdbcTemplate().update(str, (Object[])localObject);
  }
  
  public void updateStatusCustomer(Customer paramCustomer)
  {
    String str = "update T_customer set status=? where customerID=?";
    Object[] arrayOfObject = { paramCustomer.getStatus(), paramCustomer.getCustomerID() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public List getApplyWaitList(Customer paramCustomer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select v.* from T_E_VirtualFundsApply v where v.Status = 1");
    ArrayList localArrayList = new ArrayList();
    if (paramCustomer.getApplyID() != null)
    {
      localStringBuffer.append(" and v.ApplyID like ?");
      localArrayList.add(paramCustomer.getApplyID());
    }
    if ((paramCustomer.getFirmID() != null) && (!"".equals(paramCustomer.getFirmID())))
    {
      localStringBuffer.append(" and v.FirmID like ?");
      localArrayList.add(paramCustomer.getFirmID());
    }
    Object[] arrayOfObject = null;
    if (localArrayList != null) {
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
  
  public List getApplyAlreadyList(Customer paramCustomer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select v.* from T_E_VirtualFundsApply v where v.Status <> 1");
    ArrayList localArrayList = new ArrayList();
    if (paramCustomer.getApplyID() != null)
    {
      localStringBuffer.append(" and v.ApplyID like ?");
      localArrayList.add(paramCustomer.getApplyID());
    }
    if ((paramCustomer.getFirmID() != null) && (!"".equals(paramCustomer.getFirmID())))
    {
      localStringBuffer.append(" and v.FirmID like ?");
      localArrayList.add(paramCustomer.getFirmID());
    }
    Object[] arrayOfObject = null;
    if (localArrayList != null) {
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
  
  public void insertNewApp(Customer paramCustomer)
  {
    String str1 = "select count(*) from T_firm where firmID = '" + paramCustomer.getFirmID() + "'";
    try
    {
      if (getJdbcTemplate().queryForInt(str1) < 1) {
        throw new RuntimeException("此交易商不存在！");
      }
    }
    catch (Exception localException)
    {
      throw new RuntimeException("此交易商不存在！");
    }
    String str2 = "insert into T_E_VirtualFundsApply (ApplyID,FirmID,VirtualFunds,Status,CreateTime,Creator,Remark1) values (SEQ_T_E_VIRTUALFUNDSAPPLY.nextval,?,?,1,sysdate,?,?)";
    Object[] arrayOfObject = { paramCustomer.getFirmID(), paramCustomer.getVirtualFunds(), paramCustomer.getCreator(), paramCustomer.getRemark1() };
    this.log.debug("sql: " + str2);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    getJdbcTemplate().update(str2, arrayOfObject);
  }
  
  public List getVirtualFundsApplyById(String paramString)
  {
    String str = "select v.* from T_E_VirtualFundsApply v where v.Status = 1 and v.applyID = " + paramString;
    try
    {
      return getJdbcTemplate().queryForList(str);
    }
    catch (Exception localException)
    {
      throw new RuntimeException("此记录不存在！");
    }
  }
  
  public void CheckVirtual(Customer paramCustomer)
  {
    String str = "update T_E_VirtualFundsApply set Status=?,ModifyTime=sysdate,Modifier=?,Remark2=? where ApplyID=?";
    Object[] arrayOfObject = { paramCustomer.getStatus(), paramCustomer.getCreator(), paramCustomer.getRemark2(), paramCustomer.getApplyID() };
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
      localException.printStackTrace();
      throw new RuntimeException("审核失败！");
    }
  }
  
  public List getFirmPrivilege(Customer paramCustomer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select t.id,t.type,t.typeid,t.kind,t.privilegecode_b,t.privilegecode_s,").append("case when t.kind = 1 then c.breedName else t.kindid end kindid").append(" from T_A_TRADEPRIVILEGE t,(select a.kindid,b.breedname breedName from T_A_TRADEPRIVILEGE a, T_A_breed b where a.kindid = to_char(b.breedid) and a.Type = 1 and a.TypeID = ?) c").append(" where t.Type = 1 and t.TypeID = ? and t.kindid = c.kindid(+) order by t.id asc");
    Object[] arrayOfObject = { paramCustomer.getFirmID(), paramCustomer.getFirmID() };
    this.log.debug("sql: " + localStringBuffer.toString());
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public Customer getFirmPrivilegeById(Customer paramCustomer)
  {
    String str = "select t.* from T_A_TRADEPRIVILEGE t where t.Type = 1 and t.ID = ?";
    Object[] arrayOfObject = { paramCustomer.getId() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      return (Customer)getJdbcTemplate().queryForObject(str, arrayOfObject, new FirmPrivilegeMapRowMapper());
    }
    catch (Exception localException)
    {
      throw new RuntimeException("此记录不存在！");
    }
  }
  
  public void updateFirmPrivilege(Customer paramCustomer)
  {
    String str = "update T_A_TRADEPRIVILEGE set PrivilegeCode_B=?,PrivilegeCode_S=? where ID = ?";
    Object[] arrayOfObject = { paramCustomer.getPrivilegeCode_B(), paramCustomer.getPrivilegeCode_S(), paramCustomer.getId() };
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
  
  public void insertFirmPrivilege(Customer paramCustomer)
  {
    String str1 = "select count(*) from T_A_TRADEPRIVILEGE t where t.Type = 1 and t.KindID = ? and t.TypeID= ?";
    Object[] arrayOfObject1 = { paramCustomer.getKindID(), paramCustomer.getTypeID() };
    if (getJdbcTemplate().queryForInt(str1, arrayOfObject1) > 0) {
      throw new RuntimeException("此商品已有记录！");
    }
    String str2 = "insert into T_A_TRADEPRIVILEGE (ID,Type,TypeID,KindID,PrivilegeCode_B,PrivilegeCode_S,Kind) values (SEQ_T_A_TRADEPRIVILEGE.nextval,1,?,?,?,?,?)";
    Object[] arrayOfObject2 = { paramCustomer.getTypeID(), paramCustomer.getKindID(), paramCustomer.getPrivilegeCode_B(), paramCustomer.getPrivilegeCode_S(), paramCustomer.getKind() };
    this.log.debug("sql: " + str2);
    for (int i = 0; i < arrayOfObject2.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject2[i]);
    }
    try
    {
      getJdbcTemplate().update(str2, arrayOfObject2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("添加失败！");
    }
  }
  
  public void deleteFirmPrivilegeById(String paramString)
  {
    Short localShort = null;
    if ((paramString != null) && (!"".equals(paramString))) {
      localShort = Short.valueOf(Short.parseShort(paramString));
    }
    String str = "delete from T_A_TRADEPRIVILEGE where id = ?";
    Object[] arrayOfObject = { localShort };
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
  
  public List getKHCustomerPrivilege(Customer paramCustomer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select t.id,t.type,t.typeid,t.kind,t.privilegecode_b,t.privilegecode_s,").append("case when t.kind = 1 then c.breedName else t.kindid end kindid").append(" from T_A_TRADEPRIVILEGE t,(select a.kindid,b.breedname breedName from T_A_TRADEPRIVILEGE a, T_A_breed b where a.kindid = to_char(b.breedid) and a.Type = 2 and a.TypeID = ?) c").append(" where t.Type = 2 and t.TypeID = ? and t.kindid = c.kindid(+)");
    Object[] arrayOfObject = { paramCustomer.getCustomerID(), paramCustomer.getCustomerID() };
    this.log.debug("sql: " + localStringBuffer.toString());
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
  }
  
  public Customer getCustomerPrivilegeById(Customer paramCustomer)
  {
    String str = "select t.* from T_A_TRADEPRIVILEGE t where t.Type = 2 and t.ID = ?";
    Object[] arrayOfObject = { paramCustomer.getId() };
    this.log.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      return (Customer)getJdbcTemplate().queryForObject(str, arrayOfObject, new FirmPrivilegeMapRowMapper());
    }
    catch (Exception localException)
    {
      throw new RuntimeException("此记录不存在！");
    }
  }
  
  public void insertCustomerPrivilege(Customer paramCustomer)
  {
    String str1 = "select count(*) from T_A_TRADEPRIVILEGE t where t.Type = 2 and t.KindID = ? and t.TypeID= ?";
    Object[] arrayOfObject1 = { paramCustomer.getKindID(), paramCustomer.getTypeID() };
    if (getJdbcTemplate().queryForInt(str1, arrayOfObject1) > 0) {
      throw new RuntimeException("此商品已有记录！");
    }
    String str2 = "insert into T_A_TRADEPRIVILEGE (ID,Type,TypeID,KindID,PrivilegeCode_B,PrivilegeCode_S,Kind) values (SEQ_T_A_TRADEPRIVILEGE.nextval,2,?,?,?,?,?)";
    Object[] arrayOfObject2 = { paramCustomer.getTypeID(), paramCustomer.getKindID(), paramCustomer.getPrivilegeCode_B(), paramCustomer.getPrivilegeCode_S(), paramCustomer.getKind() };
    this.log.debug("sql: " + str2);
    for (int i = 0; i < arrayOfObject2.length; i++) {
      this.log.debug("params[" + i + "]: " + arrayOfObject2[i]);
    }
    try
    {
      getJdbcTemplate().update(str2, arrayOfObject2);
    }
    catch (Exception localException)
    {
      throw new RuntimeException("添加失败！");
    }
  }
  
  public void updateCustomerPrivilege(Customer paramCustomer)
  {
    String str = "update T_A_TRADEPRIVILEGE set PrivilegeCode_B=?,PrivilegeCode_S=? where ID = ?";
    Object[] arrayOfObject = { paramCustomer.getPrivilegeCode_B(), paramCustomer.getPrivilegeCode_S(), paramCustomer.getId() };
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
  
  public void deleteCustomerPrivilegeById(String paramString)
  {
    Short localShort = null;
    if ((paramString != null) && (!"".equals(paramString))) {
      localShort = Short.valueOf(Short.parseShort(paramString));
    }
    String str = "delete from T_A_TRADEPRIVILEGE where id = ?";
    Object[] arrayOfObject = { localShort };
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
  
  public List getTypePrivilege(Customer paramCustomer)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    localStringBuffer.append("select t.* from T_A_TRADEPRIVILEGE t where 1=1 ");
    if ((paramCustomer.getTypeID() != null) && (!"".equals(paramCustomer.getTypeID())))
    {
      localStringBuffer.append(" and t.TypeID = ?");
      localArrayList.add(paramCustomer.getTypeID());
    }
    if ((paramCustomer.getKindID() != null) && (!"".equals(paramCustomer.getKindID())))
    {
      localStringBuffer.append(" and t.kindID = ?");
      localArrayList.add(paramCustomer.getKindID());
    }
    Object[] arrayOfObject = null;
    if ((localArrayList != null) && (localArrayList.size() > 0)) {
      arrayOfObject = localArrayList.toArray();
    }
    if (arrayOfObject != null)
    {
      this.log.debug("sql: " + localStringBuffer.toString());
      for (int i = 0; i < arrayOfObject.length; i++) {
        this.log.debug("params[" + i + "]: " + arrayOfObject[i]);
      }
      return getJdbcTemplate().queryForList(localStringBuffer.toString(), arrayOfObject);
    }
    this.log.debug("sql: " + localStringBuffer.toString());
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  /**
   * @deprecated
   */
  public void insertSuffix()
  {
    if (getJdbcTemplate().queryForInt("select count(*) from suffix") != 0) {
      throw new RuntimeException("后缀已初始化，不能再初始化！");
    }
    String[] arrayOfString = new String[62];
    for (int i = 0; i < 10; i++) {
      arrayOfString[i] = String.valueOf((char)(48 + i));
    }
    for (i = 10; i < 36; i++) {
      arrayOfString[i] = String.valueOf((char)(55 + i));
    }
    for (i = 36; i < 62; i++) {
      arrayOfString[i] = String.valueOf((char)(61 + i));
    }
    String str = "insert into Suffix(name) values(?)";
    this.log.debug("sql: " + str);
    for (int j = 0; j < 62; j++) {
      for (int k = 0; k < 62; k++)
      {
        Object[] arrayOfObject = { arrayOfString[j] + arrayOfString[k] };
        this.log.debug("letter[" + j + "][" + k + "]:" + arrayOfObject[0]);
        getJdbcTemplate().update(str, arrayOfObject);
      }
    }
  }
  
  /**
   * @deprecated
   */
  public void CustomerRemapById(String paramString)
  {
    Assert.hasText(paramString);
    CustomerRemapStoredProcedure localCustomerRemapStoredProcedure = new CustomerRemapStoredProcedure(getDataSource());
    HashMap localHashMap = new HashMap();
    localHashMap.put("p_CustomerID", paramString);
    Map localMap = localCustomerRemapStoredProcedure.execute(localHashMap);
    printMap(localMap);
    int i = ((Integer)localMap.get("ret")).intValue();
    String str = "";
    switch (i)
    {
    case -2: 
      str = "市场摊位号没有设置！";
      break;
    case -100: 
      str = "执行存储失败！";
    }
    if (i != 1) {
      throw new RuntimeException(str);
    }
  }
  
  /**
   * @deprecated
   */
  public void addCustomerBalance(String paramString, Double paramDouble1, Double paramDouble2) {}
  
  /**
   * @deprecated
   */
  public void deleteCM_CustomerMapById(String paramString1, String paramString2)
  {
    Assert.hasText(paramString1);
    Assert.hasText(paramString2);
    String str = "delete from CM_CustomerMap where MarketCode=? and CustomerID=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    this.log.debug("sql: " + str);
    this.log.debug("marketCode:" + arrayOfObject[0]);
    this.log.debug("customerID:" + arrayOfObject[1]);
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  /**
   * @deprecated
   */
  public void exitMarketCheck(String paramString)
  {
    Assert.hasText(paramString);
    String str = "select count(*) from T_CustomerHoldSum where CustomerID=?";
    Object[] arrayOfObject = { paramString };
    if (getJdbcTemplate().queryForInt(str, arrayOfObject) > 0) {
      throw new RuntimeException("此客户还有持仓，不能退市！");
    }
    if (((Double)getJdbcTemplate().queryForObject("select Balance from T_CustomerFunds where CustomerID=?", arrayOfObject, Double.class)).doubleValue() != 0.0D) {
      throw new RuntimeException("此客户还有资金，不能退市！");
    }
  }
  
  public static void main(String[] paramArrayOfString)
  {
    String[] arrayOfString = new String[62];
    for (int i = 0; i < 10; i++) {
      arrayOfString[i] = String.valueOf((char)(48 + i));
    }
    for (i = 10; i < 36; i++) {
      arrayOfString[i] = String.valueOf((char)(55 + i));
    }
    for (i = 36; i < 62; i++) {
      arrayOfString[i] = String.valueOf((char)(61 + i));
    }
    try
    {
      System.setOut(new PrintStream(new FileOutputStream("D:/Suffix.txt")));
    }
    catch (Exception localException) {}
    String str1 = "01";
    String str2 = "insert into Suffix(MarketCode,name,status) values('" + str1 + "','";
    int j = 1;
    for (int k = 0; k < 62; k++) {
      for (int m = 0; m < 62; m++) {
        j++;
      }
    }
  }
  
  private static String makeSql_tradeprivilege_delete(int paramInt1, String paramString1, int paramInt2, String paramString2)
  {
    String str1 = "";
    if (paramInt1 == 1)
    {
      str2 = SqlUtil.makeFilterCondition("brokerid", paramString1, false);
      str1 = "typeid in ( select firmid from m_b_firmandbroker where " + str2 + ") ";
    }
    else if (paramInt1 == 2)
    {
      str1 = SqlUtil.makeFilterCondition("typeid", paramString1, false);
    }
    String str2 = SqlUtil.makeFilterCondition("kindid", paramString2, false);
    String str3 = "delete from t_a_tradeprivilege where type=1 and " + str1 + " and kind=" + paramInt2 + " and " + str2;
    return str3;
  }
  
  private static String makeSql_tradeprivilege_insert(int paramInt1, String paramString1, int paramInt2, String paramString2, int paramInt3, int paramInt4)
  {
    String str1 = "";
    if (paramInt1 == 1)
    {
      str2 = SqlUtil.makeFilterCondition("brokerid", paramString1, false);
      str1 = "firmid in ( select firmid from m_b_firmandbroker where " + str2 + ") ";
    }
    else if (paramInt1 == 2)
    {
      str1 = SqlUtil.makeFilterCondition("firmid", paramString1, false);
    }
    String str2 = "insert into t_a_tradeprivilege (id, type, typeid, kind, kindid, privilegecode_b, privilegecode_s) ";
    String str3 = "";
    String str4 = "";
    if (paramInt2 == 1)
    {
      str3 = "select seq_t_a_tradeprivilege.nextval,1,f.firmid,1,b.BREEDid," + paramInt3 + "," + paramInt4 + " from m_firm f,T_A_BREED b";
      str4 = SqlUtil.makeFilterCondition("BREEDid", paramString2, true);
    }
    else if (paramInt2 == 2)
    {
      str3 = "select seq_t_a_tradeprivilege.nextval,1,f.firmid,2,c.commodityid," + paramInt3 + "," + paramInt4 + " from m_firm f,t_commodity c";
      str4 = SqlUtil.makeFilterCondition("commodityid", paramString2, false);
    }
    String str5 = str2 + str3 + " where " + str1 + " and " + str4;
    return str5;
  }
  
  public void batchSetInsertFirmPrivilege(int paramInt1, String paramString1, int paramInt2, String paramString2, int paramInt3, int paramInt4)
  {
    String str = makeSql_tradeprivilege_insert(paramInt1, paramString1, paramInt2, paramString2, paramInt3, paramInt4);
    this.log.debug("sql: " + str);
    try
    {
      getJdbcTemplate().update(str);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("添加失败！");
    }
  }
  
  public void batchEmptyDeleteFirmPrivilege(int paramInt1, String paramString1, int paramInt2, String paramString2)
  {
    String str = makeSql_tradeprivilege_delete(paramInt1, paramString1, paramInt2, paramString2);
    this.log.debug("sql: " + str);
    try
    {
      getJdbcTemplate().update(str);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("添加失败！");
    }
  }
  
  /**
   * @deprecated
   */
  private class CustomerRemapStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "CustomerRemap";
    
    public CustomerRemapStoredProcedure(DataSource paramDataSource)
    {
      super("CustomerRemap");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      declareParameter(new SqlParameter("p_CustomerID", 12));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
    }
  }
  
  class FirmPrivilegeMapRowMapper
    implements RowMapper
  {
    FirmPrivilegeMapRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToFirmPrivilegeMap(paramResultSet);
    }
    
    private Customer rsToFirmPrivilegeMap(ResultSet paramResultSet)
      throws SQLException
    {
      Customer localCustomer = new Customer();
      localCustomer.setId(new Short(paramResultSet.getShort("ID")));
      localCustomer.setTypeprivilege(new Short(paramResultSet.getShort("Type")));
      localCustomer.setTypeID(paramResultSet.getString("TypeID"));
      localCustomer.setPrivilegeCode_B(new Short(paramResultSet.getShort("PrivilegeCode_B")));
      localCustomer.setPrivilegeCode_S(new Short(paramResultSet.getShort("PrivilegeCode_S")));
      localCustomer.setKind(new Short(paramResultSet.getShort("Kind")));
      localCustomer.setKindID(paramResultSet.getString("KindID"));
      return localCustomer;
    }
  }
  
  private class CustomerDelStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FirmDel";
    
    public CustomerDelStoredProcedure(DataSource paramDataSource)
    {
      super("FirmDel");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      declareParameter(new SqlParameter("p_CustomerID", 12));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
    }
  }
  
  private class CustomerChgStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FirmChg";
    
    public CustomerChgStoredProcedure(DataSource paramDataSource)
    {
      super("FirmChg");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      declareParameter(new SqlParameter("p_CustomerID", 12));
      declareParameter(new SqlParameter("p_GroupID", -5));
      declareParameter(new SqlParameter("p_CustomerName", 12));
      declareParameter(new SqlParameter("p_Phone", 12));
      declareParameter(new SqlParameter("p_Address", 12));
      declareParameter(new SqlParameter("p_Status", 5));
      declareParameter(new SqlParameter("p_Note", 12));
      declareParameter(new SqlParameter("p_ValidDate", 91));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
    }
  }
  
  private class CustomerAddStoredProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FirmAdd";
    
    public CustomerAddStoredProcedure(DataSource paramDataSource)
    {
      super("FirmAdd");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 4));
      declareParameter(new SqlParameter("p_CustomerID", 12));
      declareParameter(new SqlParameter("p_GroupID", -5));
      declareParameter(new SqlParameter("p_Password", 12));
      declareParameter(new SqlParameter("p_CustomerName", 12));
      declareParameter(new SqlParameter("p_Phone", 12));
      declareParameter(new SqlParameter("p_Address", 12));
      declareParameter(new SqlParameter("p_Status", 5));
      declareParameter(new SqlParameter("p_Note", 12));
      declareParameter(new SqlParameter("p_ValidDate", 91));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
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
      localCustomerFunds.setCustomerID(paramResultSet.getString("FirmID"));
      localCustomerFunds.setRuntimeFL(new Double(paramResultSet.getDouble("RuntimeFL")));
      localCustomerFunds.setClearFL(new Double(paramResultSet.getDouble("ClearFL")));
      localCustomerFunds.setRuntimeMargin(new Double(paramResultSet.getDouble("RuntimeMargin")));
      localCustomerFunds.setClearMargin(new Double(paramResultSet.getDouble("ClearMargin")));
      localCustomerFunds.setVirtualFunds(new Double(paramResultSet.getDouble("VirtualFunds")));
      return localCustomerFunds;
    }
  }
  
  class KHCustomerRowMapper
    implements RowMapper
  {
    KHCustomerRowMapper() {}
    
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
      localCustomer.setCustomerName(paramResultSet.getString("Name"));
      localCustomer.setStatus(new Short(paramResultSet.getShort("Status")));
      localCustomer.setFirmID(paramResultSet.getString("FirmID"));
      localCustomer.setModifyTime(paramResultSet.getTimestamp("ModifyTime"));
      localCustomer.setCreateTime(paramResultSet.getTimestamp("CreateTime"));
      return localCustomer;
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
      localCustomer.setCustomerID(paramResultSet.getString("FirmID"));
      localCustomer.setGroupID(new Long(paramResultSet.getLong("GroupID")));
      localCustomer.setCustomerName(paramResultSet.getString("FirmName"));
      localCustomer.setStatus(new Short(paramResultSet.getShort("Status")));
      localCustomer.setModifyTime(paramResultSet.getTimestamp("ModifyTime"));
      localCustomer.setCreateTime(paramResultSet.getTimestamp("CreateTime"));
      return localCustomer;
    }
  }
}
