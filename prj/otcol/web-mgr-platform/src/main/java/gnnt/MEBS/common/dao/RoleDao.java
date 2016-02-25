package gnnt.MEBS.common.dao;

import gnnt.MEBS.common.dao.impl.DaoHelperImpl;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.util.query.PageInfo;
import gnnt.MEBS.common.util.query.QueryConditions;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class RoleDao
  extends DaoHelperImpl
{
  private String className = "gnnt.MEBS.common.model.Role";
  
  public void addRole(Role paramRole)
  {
    getHibernateTemplate().save(paramRole);
  }
  
  public Role getRoleById(long paramLong)
  {
    Role localRole = null;
    try
    {
      localRole = (Role)getHibernateTemplate().get(this.className, Long.valueOf(paramLong));
    }
    catch (Exception localException)
    {
      this.logger.error("get failed", localException);
    }
    return localRole;
  }
  
  public void updateRole(Role paramRole)
  {
    getHibernateTemplate().saveOrUpdate(paramRole);
  }
  
  public Role loadRoleById(long paramLong)
  {
    Role localRole = null;
    try
    {
      localRole = (Role)getHibernateTemplate().load(this.className, Long.valueOf(paramLong));
    }
    catch (Exception localException)
    {
      this.logger.error("get failed", localException);
    }
    return localRole;
  }
  
  public List<Role> findRoleByProperty(String paramString, Object paramObject)
  {
    this.logger.debug("finding Role instance with property: " + paramString + ", value: " + paramObject);
    List localList = null;
    try
    {
      String str = "from Role as model where model." + paramString + "= ?";
      localList = getHibernateTemplate().find(str, paramObject);
    }
    catch (Exception localException)
    {
      this.logger.error("find by property name failed", localException);
    }
    return localList;
  }
  
  public List<Role> getRoleList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "from Role as u where 1=1";
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
  
  public void deleteRoles(Role paramRole)
  {
    getHibernateTemplate().delete(paramRole);
  }
}
