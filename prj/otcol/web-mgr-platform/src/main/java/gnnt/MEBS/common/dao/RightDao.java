package gnnt.MEBS.common.dao;

import gnnt.MEBS.common.dao.impl.DaoHelperImpl;
import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.util.query.PageInfo;
import gnnt.MEBS.common.util.query.QueryConditions;
import java.util.List;
import org.apache.commons.logging.Log;
import org.hibernate.Filter;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class RightDao
  extends DaoHelperImpl
{
  private String className = "gnnt.MEBS.common.model.Right";
  
  public void addRight(Right paramRight)
  {
    getHibernateTemplate().save(paramRight);
  }
  
  public Right getRightById(long paramLong)
  {
    Right localRight = null;
    try
    {
      localRight = (Right)getHibernateTemplate().get(this.className, Long.valueOf(paramLong));
    }
    catch (RuntimeException localRuntimeException)
    {
      this.logger.error("get failed", localRuntimeException);
    }
    return localRight;
  }
  
  public Right loadRightById(long paramLong)
  {
    Right localRight = null;
    try
    {
      localRight = (Right)getHibernateTemplate().load(this.className, Long.valueOf(paramLong));
    }
    catch (Exception localException)
    {
      this.logger.error("get failed", localException);
    }
    return localRight;
  }
  
  public void updateRight(Right paramRight)
  {
    getHibernateTemplate().saveOrUpdate(paramRight);
  }
  
  public void deleteRight(Right paramRight)
  {
    getHibernateTemplate().delete(paramRight);
  }
  
  public Right getRightByFilter(long paramLong, int paramInt1, int paramInt2)
  {
    Right localRight = null;
    try
    {
      String str = "from Right as model where model.id= ?";
      getHibernateTemplate().enableFilter("rightFilter").setParameter("type1", Integer.valueOf(paramInt1)).setParameter("type2", Integer.valueOf(paramInt2));
      List localList = getHibernateTemplate().find(str, Long.valueOf(paramLong));
      if (localList.size() > 0) {
        localRight = (Right)localList.get(0);
      }
    }
    catch (Exception localException)
    {
      this.logger.error("find by property name failed", localException);
    }
    return localRight;
  }
  
  public void updateDynamicRight(long paramLong)
  {
    Right localRight = (Right)getHibernateTemplate().get(this.className, Long.valueOf(paramLong));
    getHibernateTemplate().saveOrUpdate(localRight);
  }
  
  public List<Right> getRightList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "from Right as u where 1=1";
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
  
  public Right loadTreeRight(long paramLong, int paramInt1, int paramInt2)
  {
    Right localRight = null;
    try
    {
      String str = "from Right as model where model.id= ?";
      getHibernateTemplate().enableFilter("rightTreeFilter").setParameter("type", Integer.valueOf(paramInt1)).setParameter("visible", Integer.valueOf(paramInt2));
      List localList = getHibernateTemplate().find(str, Long.valueOf(paramLong));
      if (localList.size() > 0) {
        localRight = (Right)localList.get(0);
      }
    }
    catch (Exception localException)
    {
      this.logger.error("find by property name failed", localException);
    }
    return localRight;
  }
}
