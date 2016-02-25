package gnnt.MEBS.finance.dao;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.finance.base.dao.oracle.DaoHelperImpl;
import gnnt.MEBS.finance.unit.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;

public class UserDao
  extends DaoHelperImpl
{
  private final transient Log logger = LogFactory.getLog(UserDao.class);
  
  public void createUser(User paramUser)
  {
    String str = "insert into Users(userId,username,password,enabled) values(?,?,?,?)";
    Object[] arrayOfObject = { paramUser.getUserId(), paramUser.getUserName(), paramUser.getPassword(), new Boolean(paramUser.isEnabled()) };
    this.logger.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    updateBySQL(str, arrayOfObject);
    updateUserAuthorities(paramUser);
  }
  
  private void insertAuthority(String paramString1, String paramString2)
  {
    String str = "insert into Authorities(userId,authority) values(?,?)";
    Object[] arrayOfObject = { paramString1, paramString2 };
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    try
    {
      updateBySQL(str, arrayOfObject);
    }
    catch (DataIntegrityViolationException localDataIntegrityViolationException) {}
  }
  
  public void updateUserAuthorities(User paramUser)
  {
    String str1 = "delete from Authorities where userId=?";
    Object[] arrayOfObject = { paramUser.getUserId() };
    this.logger.debug("sql: " + str1);
    this.logger.debug("userId: " + arrayOfObject[0]);
    updateBySQL(str1, arrayOfObject);
    String[] arrayOfString = paramUser.getAuthorities();
    if ((arrayOfString != null) && (arrayOfString.length > 0)) {
      for (int i = 0; i < arrayOfString.length; i++)
      {
        String str2 = arrayOfString[i];
        insertAuthority(paramUser.getUserId(), str2);
      }
    }
  }
  
  public void updateUser(User paramUser)
  {
    String str = "update users set userName=?,password=?,enabled=? where userId=?";
    Object[] arrayOfObject = { paramUser.getUserName(), paramUser.getPassword(), new Boolean(paramUser.isEnabled()), paramUser.getUserId() };
    this.logger.debug("sql: " + str);
    for (int i = 0; i < arrayOfObject.length; i++) {
      this.logger.debug("params[" + i + "]: " + arrayOfObject[i]);
    }
    updateBySQL(str, arrayOfObject);
  }
  
  public void updateUserWithAuthorities(User paramUser)
  {
    updateUser(paramUser);
    updateUserAuthorities(paramUser);
  }
  
  public void deleteUser(String paramString)
  {
    String str1 = "delete from Users where userId=?";
    Object[] arrayOfObject = { paramString };
    this.logger.debug("sql: " + str1);
    this.logger.debug("userId: " + arrayOfObject[0]);
    updateBySQL(str1, arrayOfObject);
    String str2 = "delete from Authorities where userId=?";
    this.logger.debug("sql: " + str2);
    this.logger.debug("userId: " + arrayOfObject[0]);
    updateBySQL(str2, arrayOfObject);
  }
  
  public User getUserById(String paramString)
  {
    String str = "select * from Users where userId=?";
    Object[] arrayOfObject = { paramString };
    this.logger.debug("sql: " + str);
    this.logger.debug("userId: " + arrayOfObject[0]);
    User localUser = null;
    try
    {
      localUser = (User)queryForObject(str, arrayOfObject, new UserRowMapper());
    }
    catch (IncorrectResultSizeDataAccessException localIncorrectResultSizeDataAccessException)
    {
      this.logger.error("UserId " + paramString + " Not exist !");
    }
    return localUser;
  }
  
  private String[] fetchUserAuthorities(String paramString)
  {
    String str = "select authority from Authorities where userId=?";
    Object[] arrayOfObject = { paramString };
    this.logger.debug("sql: " + str);
    this.logger.debug("userId: " + arrayOfObject[0]);
    List localList = queryBySQL(str, arrayOfObject, null);
    String[] arrayOfString = new String[localList.size()];
    for (int i = 0; i < localList.size(); i++) {
      arrayOfString[i] = ((String)((Map)localList.get(i)).get("AUTHORITY"));
    }
    return arrayOfString;
  }
  
  public List getUsers(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select * from Users";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    List localList = queryBySQL(str, arrayOfObject, paramPageInfo, new UserRowMapper());
    return localList;
  }
  
  class UserRowMapper
    implements RowMapper
  {
    UserRowMapper() {}
    
    public Object mapRow(ResultSet paramResultSet, int paramInt)
      throws SQLException
    {
      return rsToUser(paramResultSet);
    }
    
    private User rsToUser(ResultSet paramResultSet)
      throws SQLException
    {
      User localUser = new User();
      localUser.setUserId(paramResultSet.getString("userId"));
      localUser.setUserName(paramResultSet.getString("userName"));
      localUser.setPassword(paramResultSet.getString("password"));
      localUser.setEnabled(paramResultSet.getBoolean("enabled"));
      localUser.setAuthorities(UserDao.this.fetchUserAuthorities(paramResultSet.getString("userId")));
      return localUser;
    }
  }
}
