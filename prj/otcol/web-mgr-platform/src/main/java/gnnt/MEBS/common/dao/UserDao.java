package gnnt.MEBS.common.dao;

import gnnt.MEBS.common.dao.impl.DaoHelperImpl;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.util.query.PageInfo;
import gnnt.MEBS.common.util.query.QueryConditions;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class UserDao
  extends DaoHelperImpl
{
  private String className = "gnnt.MEBS.common.model.User";
  
  public void addUser(User paramUser)
  {
    getHibernateTemplate().save(paramUser);
  }
  
  public User getUserById(String paramString)
  {
    User localUser = null;
    try
    {
      localUser = (User)getHibernateTemplate().get(this.className, paramString);
    }
    catch (Exception localException)
    {
      this.logger.error("get failed", localException);
    }
    return localUser;
  }
  
  public int checkPassword(String paramString1, String paramString2)
  {
    int i = 0;
    try
    {
      Object localObject = new ArrayList();
      String str = "from User as user where user.userId = '" + paramString1 + "' and user.password = '" + paramString2 + "'";
      localObject = getHibernateTemplate().find(str);
      if ((localObject != null) && (((List)localObject).size() > 0)) {
        i = 1;
      }
    }
    catch (Exception localException)
    {
      this.logger.error("get failed", localException);
    }
    return i;
  }
  
  public void updateUser(User paramUser)
  {
    getHibernateTemplate().saveOrUpdate(paramUser);
  }
  
  public List getUserList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "from User as u where 1=1";
    Object[] arrayOfObject = null;
    String[] arrayOfString = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      arrayOfString = paramQueryConditions.getNameArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql: " + str);
    return queryBySQL(str, arrayOfString, arrayOfObject, paramPageInfo);
  }
  
  public User loadUserById(String paramString)
  {
    User localUser = null;
    try
    {
      localUser = (User)getHibernateTemplate().load(this.className, paramString);
    }
    catch (Exception localException)
    {
      this.logger.error("get failed", localException);
    }
    return localUser;
  }
  
  public List<User> findRoleByProperty(String paramString, Object paramObject)
  {
    this.logger.debug("finding User instance with property: " + paramString + ", value: " + paramObject);
    List localList = null;
    try
    {
      String str = "from User as model where model." + paramString + "= ?";
      localList = getHibernateTemplate().find(str, paramObject);
    }
    catch (Exception localException)
    {
      this.logger.error("find by property name failed", localException);
    }
    return localList;
  }
  
  public void deleteUsers(User paramUser)
  {
    getHibernateTemplate().delete(paramUser);
  }
}
