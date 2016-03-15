package cn.com.agree.eteller.usermanager.dao.hibernate;

import cn.com.agree.eteller.usermanager.dao.ISubAppinfoDao;
import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfo;
import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfoPK;
import cn.com.agree.eteller.usermanager.persistence.Funclist;
import cn.com.agree.eteller.usermanager.persistence.Rolelist;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class SubAppinfoDao
  extends HibernateDaoSupport
  implements ISubAppinfoDao
{
  public EtellerSubappinfo getSubAppinfo(String subappid)
  {
    List list = getHibernateTemplate().find(
      "FROM EtellerSubappinfo t WHERE t.comp_id.subappid='" + 
      subappid + "'");
    if ((list == null) || (list.size() == 0)) {
      return null;
    }
    return (EtellerSubappinfo)list.get(0);
  }
  
  public EtellerSubappinfo[] getSubAppinfoByappid(String appid)
  {
    List list = getHibernateTemplate().find(
      "FROM EtellerSubappinfo t WHERE t.comp_id.appid='" + appid + 
      "' order by subappid asc");
    

    return (EtellerSubappinfo[])list.toArray(new EtellerSubappinfo[0]);
  }
  
  public boolean addSubAppinfo(final EtellerSubappinfo ca)
  {
    boolean isAdded = true;
    try
    {
      if (getSubAppinfo(ca.getComp_id().getSubappid()) != null) {
        return false;
      }
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException
        {
          session.save(ca);
          return null;
        }
      });
      isAdded = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isAdded = false;
    }
    return isAdded;
  }
  
  public boolean updateSubAppinfo(EtellerSubappinfo ca)
  {
    boolean isUpdated = false;
    try
    {
      EtellerSubappinfo other = (EtellerSubappinfo)getSession().get(EtellerSubappinfo.class, ca.getComp_id());
      other.getComp_id().setAppid(ca.getComp_id().getAppid());
      other.setAppname(ca.getAppname());
      other.setAppadress(ca.getAppadress());
      other.setSubappdesc(ca.getSubappdesc());
      isUpdated = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isUpdated = false;
    }
    return isUpdated;
  }
  
  public boolean deleteSubAppinfo(final EtellerSubappinfo ca)
  {
    boolean isDeleted = false;
    try
    {
      getHibernateTemplate().delete(getSubAppinfo(ca.getComp_id().getSubappid()));
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException, SQLException
        {
          List list = SubAppinfoDao.this.getHibernateTemplate().find(
            "from Funclist f where f.subappid=?", ca
            .getComp_id().getSubappid());
          SubAppinfoDao.this.logger.info("size:" + list.size());
          for (Iterator iter = list.iterator(); iter.hasNext();)
          {
            SubAppinfoDao.this.logger.info("size:" + list.size());
            Funclist fun = (Funclist)iter.next();
            
            Set set = fun.getRoles();
            for (Iterator iterator = set.iterator(); iterator
                  .hasNext();)
            {
              Rolelist element = (Rolelist)iterator.next();
              element.getFunctions().remove(fun);
            }
            SubAppinfoDao.this.logger.info("size:" + list.size());
            session.delete(fun);
          }
          return null;
        }
      });
      isDeleted = true;
    }
    catch (Exception ex)
    {
      isDeleted = false;
    }
    return isDeleted;
  }
  
  public EtellerSubappinfo[] getAllSubAppinfo()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(EtellerSubappinfo.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (EtellerSubappinfo[])ls.toArray(new EtellerSubappinfo[0]);
  }
  
  public int getMaxSubappid()
  {
    List list = 
      getHibernateTemplate()
      .find(
      "select max(s.comp_id.subappid) from EtellerSubappinfo s where 1=1");
    
    String subappid = (String)list.get(0);
    if (subappid == null) {
      return 0;
    }
    int id = Integer.parseInt(subappid);
    id++;
    return id;
  }
}
