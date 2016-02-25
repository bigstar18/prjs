package gnnt.MEBS.finance.dao;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.finance.base.dao.oracle.DaoHelperImpl;
import gnnt.MEBS.finance.unit.Tradeuser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;

public class TradeuserDao
  extends DaoHelperImpl
{
  private final transient Log logger = LogFactory.getLog(TradeuserDao.class);
  
  public List getTradeusers(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    List localList = null;
    String str = "select * from f_firm";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    localList = queryBySQL(str, arrayOfObject, paramPageInfo, new tradeuserRowMapper());
    return localList;
  }
  
  public void createTradeuser(Tradeuser paramTradeuser)
  {
    try
    {
      String str = "insert into f_firm (firmId,name,password,fullname,bank,bankAccount,address,contactMan,phone,fax,postCode,eMail,createTime)  values(?,?,?,?,?,?,?,?,?,?,?,?";
      str = str + ",sysdate)";
      Object[] arrayOfObject = { paramTradeuser.getFirmId(), paramTradeuser.getName(), paramTradeuser.getPassword(), paramTradeuser.getFullname(), paramTradeuser.getBank(), paramTradeuser.getBankAccount(), paramTradeuser.getAddress(), paramTradeuser.getContactMan(), paramTradeuser.getPhone(), paramTradeuser.getFax(), paramTradeuser.getPostCode(), paramTradeuser.getEmail() };
      int[] arrayOfInt = { 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12 };
      updateBySQL(str, arrayOfObject, arrayOfInt);
      executeStoredSubprogram(" SP_F_SynchFirm ('" + paramTradeuser.getFirmId() + "') ");
    }
    catch (DataAccessException localDataAccessException)
    {
      throw localDataAccessException;
    }
  }
  
  public Tradeuser getTradeuserById(String paramString)
  {
    String str = "select * from f_firm where firmId=?";
    Object[] arrayOfObject = { paramString };
    this.logger.debug("sql: " + str);
    this.logger.debug("firmId: " + arrayOfObject[0]);
    Tradeuser localTradeuser = null;
    try
    {
      localTradeuser = (Tradeuser)queryForObject(str, arrayOfObject, new tradeuserRowMapper());
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      this.logger.error("firmId " + paramString + " Not exist !");
    }
    return localTradeuser;
  }
  
  public void updateTradeuser(Tradeuser paramTradeuser)
  {
    String str = "update f_firm set name=?,fullname=?,bank=?,bankAccount=?,address=?,contactMan=?,phone=?,fax=?,postCode=?,eMail=?";
    str = str + " where firmId=?";
    Object[] arrayOfObject = { paramTradeuser.getName(), paramTradeuser.getFullname(), paramTradeuser.getBank(), paramTradeuser.getBankAccount(), paramTradeuser.getAddress(), paramTradeuser.getContactMan(), paramTradeuser.getPhone(), paramTradeuser.getFax(), paramTradeuser.getPostCode(), paramTradeuser.getEmail(), paramTradeuser.getFirmId() };
    int[] arrayOfInt = { 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void repairTradeuser(String paramString1, String paramString2)
  {
    String str = "update f_firm set password=? where firmId=?";
    Object[] arrayOfObject = { paramString2, paramString1 };
    this.logger.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    updateBySQL(str, arrayOfObject);
  }
  
  class tradeuserRowMapper
    implements RowMapper
  {
    tradeuserRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToUser(paramResultSet);
    }
    
    private Tradeuser rsToUser(ResultSet paramResultSet)
      throws SQLException
    {
      Tradeuser localTradeuser = new Tradeuser();
      localTradeuser.setFirmId(paramResultSet.getString("firmId"));
      localTradeuser.setName(paramResultSet.getString("name"));
      localTradeuser.setFullname(paramResultSet.getString("fullname"));
      localTradeuser.setBank(paramResultSet.getString("bank"));
      localTradeuser.setBankAccount(paramResultSet.getString("bankAccount"));
      localTradeuser.setAddress(paramResultSet.getString("address"));
      localTradeuser.setContactMan(paramResultSet.getString("contactMan"));
      localTradeuser.setCreateTime(paramResultSet.getTimestamp("createTime"));
      localTradeuser.setEmail(paramResultSet.getString("email"));
      localTradeuser.setFax(paramResultSet.getString("fax"));
      localTradeuser.setPhone(paramResultSet.getString("phone"));
      localTradeuser.setPostCode(paramResultSet.getString("postCode"));
      return localTradeuser;
    }
  }
}
