package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.Menu;
import gnnt.MEBS.delivery.model.User;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao
  extends DaoHelperImpl
{
  private final transient Log logger = LogFactory.getLog(UserDao.class);
  
  public boolean checkUser(String paramString)
  {
    String str = "select count(*) from w_Users where UserId=?";
    Object[] arrayOfObject = { paramString };
    int i = getJdbcTemplate().queryForInt(str, arrayOfObject, new int[] { 12 });
    return i > 0;
  }
  
  public User getUser(String paramString1, String paramString2)
  {
    String str = "select t.UserId, t.Name, t.Password, t.Manage_Id manage_id, t.roleStatus, t.Manage_Popedom, t.Popedom, (select w.name from w_warehouse w where w.id=t.Manage_Id ) warehousename from w_Users t where t.UserId=? and manage_id=?";
    paramString1 = paramString1.replaceAll(" or ", "").replaceAll("'", "");
    Object[] arrayOfObject = { paramString1, paramString2 };
    this.logger.debug("----getUser()-----UserId=:--" + paramString1);
    this.logger.debug("----getUser()-----sql=:--" + str);
    List localList = queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new User()));
    User localUser = null;
    if (localList.size() > 0) {
      localUser = (User)localList.get(0);
    }
    return localUser;
  }
  
  public void addUser(User paramUser)
  {
    String str = "insert into w_Users(UserId,Name,Password,Manage_Id,roleStatus,Manage_Popedom,Popedom) values(?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramUser.getUserId(), paramUser.getName(), paramUser.getPassword(), paramUser.getManage_id(), Integer.valueOf(paramUser.getRoleStatus()), Integer.valueOf(paramUser.getManage_popedom()), paramUser.getPopedom() };
    int[] arrayOfInt = { 12, 12, 12, 12, 4, 2, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deleteUser(String paramString)
  {
    String str = "delete from w_users where userId=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deleteUser(QueryConditions paramQueryConditions)
  {
    String str = "delete from w_users";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " where " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    updateBySQL(str, arrayOfObject);
  }
  
  public List queryUsers(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = " select * from (select t.UserId, t.Name, t.Password, t.Manage_Id, t.roleStatus, t.Manage_Popedom, t.Popedom, w.name warehousename from w_Users t,w_warehouse w where w.id(+)=t.manage_id) where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("------queryUsers()-----sql=:--" + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List queryTopPermissions(int paramInt)
  {
    String str = " select * from (select t.* from w_menu t where t.popedom in (" + paramInt + ",-1) and t.id like ('__00') order by t.id) where 1=1 ";
    return getJdbcTemplate().query(str, new CommonRowMapper(new Menu()));
  }
  
  public List queryChiPermissions(int paramInt, String paramString)
  {
    String str = " select * from (select t.* from w_menu t where t.popedom in (" + paramInt + ",-1) and t.id like ('" + paramString + "__') and t.id not like ('__00')  order by t.id) where 1=1 ";
    return getJdbcTemplate().query(str, new CommonRowMapper(new Menu()));
  }
  
  public void updateUser(User paramUser)
  {
    String str = " update  w_users w set w.name=?,w.password=?,w.manage_Id=?,w.roleStatus=?,w.manage_Popedom=?,w.popedom=?  where w.userid=? ";
    Object[] arrayOfObject = { paramUser.getName(), paramUser.getPassword(), paramUser.getManage_id(), Integer.valueOf(paramUser.getRoleStatus()), Integer.valueOf(paramUser.getManage_popedom()), paramUser.getPopedom(), paramUser.getUserId() };
    int[] arrayOfInt = { 12, 12, 12, 4, 2, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void freshPermission(String paramString1, String paramString2)
  {
    String str = " update w_users w set w.popedom=? where w.userid=? ";
    Object[] arrayOfObject = { paramString1, paramString2 };
    int[] arrayOfInt = { 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void getUserLock(User paramUser)
  {
    String str = "select * from w_users where userId='" + paramUser.getUserId() + "' for update";
    this.logger.debug(str);
    queryBySQL(str);
  }
  
  public List<User> getUsers(QueryConditions paramQueryConditions)
  {
    String str = "select w.* from w_users w where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new User()));
  }
}
