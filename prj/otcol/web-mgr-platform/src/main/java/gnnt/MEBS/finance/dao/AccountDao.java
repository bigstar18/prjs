package gnnt.MEBS.finance.dao;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.finance.base.dao.oracle.DaoHelperImpl;
import gnnt.MEBS.finance.unit.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;

public class AccountDao
  extends DaoHelperImpl
{
  private final transient Log logger = LogFactory.getLog(AccountDao.class);
  
  public void createAccount(Account paramAccount)
  {
    String str = "insert into f_Account(code,name,accountlevel,dcflag) values(?,?,?,?)";
    Object[] arrayOfObject = { paramAccount.getCode(), paramAccount.getName(), paramAccount.getAccountLevel(), paramAccount.getDCFlag() };
    this.logger.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    updateBySQL(str, arrayOfObject);
  }
  
  public void deleteAccount(String paramString1, String paramString2)
  {
    try
    {
      String str1 = "delete from f_Account where code=?";
      Object[] arrayOfObject = { paramString1 };
      this.logger.debug("sql: " + str1);
      this.logger.debug("accountcode: " + arrayOfObject[0]);
      updateBySQL(str1, arrayOfObject);
      String str2 = "删除科目 " + paramString1;
      str1 = "insert into f_log values(sysdate,'info','" + paramString2 + "','" + str2 + "')";
      updateBySQL(str1);
    }
    catch (DataAccessException localDataAccessException)
    {
      throw localDataAccessException;
    }
  }
  
  public Account getAccountByCode(String paramString)
  {
    String str = "select * from f_Account where code=?";
    Object[] arrayOfObject = { paramString };
    this.logger.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    Object localObject = null;
    try
    {
      localObject = queryForObject(str, arrayOfObject, new AccountRowMapper());
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      this.logger.error("Account code " + paramString + " Not exist !");
    }
    return (Account)localObject;
  }
  
  public Account getLeafAccountByCode(String paramString)
  {
    Object[] arrayOfObject = null;
    String str = "select * from f_account where not exists(select * from f_account where code like ? and AccountLevel>(select AccountLevel from f_account where code=?)) and code = ? ";
    arrayOfObject = new Object[] { paramString + "%", paramString, paramString };
    this.logger.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    Object localObject = null;
    try
    {
      localObject = queryForObject(str, arrayOfObject, new AccountRowMapper());
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      this.logger.error("Account code " + paramString + "also exist !");
    }
    return (Account)localObject;
  }
  
  public List getAccounts(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from f_Account";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    List localList = queryBySQL(str, arrayOfObject, paramPageInfo, new AccountRowMapper());
    return localList;
  }
  
  public void updateAccount(Account paramAccount)
  {
    String str = "update f_Account set name=?,accountlevel=?,dcflag=? where code=?";
    Object[] arrayOfObject = { paramAccount.getName(), paramAccount.getAccountLevel(), paramAccount.getDCFlag(), paramAccount.getCode() };
    this.logger.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    updateBySQL(str, arrayOfObject);
  }
  
  public int getAccount(String paramString)
  {
    String str = "select count(*) from f_Account where code=?";
    Object[] arrayOfObject = { paramString };
    this.logger.debug("sql: " + str);
    this.logger.debug("summaryNo: " + arrayOfObject[0]);
    return queryForInt(str, arrayOfObject);
  }
  
  class AccountRowMapper
    implements RowMapper
  {
    AccountRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToAccount(paramResultSet);
    }
    
    private Account rsToAccount(ResultSet paramResultSet)
      throws SQLException
    {
      Account localAccount = new Account();
      localAccount.setCode(paramResultSet.getString("code"));
      localAccount.setName(paramResultSet.getString("name"));
      localAccount.setAccountLevel(new Integer(paramResultSet.getInt("accountlevel")));
      localAccount.setDCFlag(paramResultSet.getString("dcflag"));
      return localAccount;
    }
  }
}
