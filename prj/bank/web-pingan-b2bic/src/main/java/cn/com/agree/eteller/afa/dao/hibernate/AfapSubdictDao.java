package cn.com.agree.eteller.afa.dao.hibernate;

import cn.com.agree.eteller.afa.dao.IAfapSubdictDao;
import cn.com.agree.eteller.afa.persistence.AfapMaindict;
import cn.com.agree.eteller.afa.persistence.AfapSubdict;
import cn.com.agree.eteller.afa.persistence.AfapSubdictPK;
import cn.com.agree.eteller.generic.utils.ComFunction;
import cn.com.agree.eteller.generic.utils.Pagination;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AfapSubdictDao
  extends HibernateDaoSupport
  implements IAfapSubdictDao
{
  public boolean addAfapSubdict(final AfapSubdict ca)
  {
    boolean isAdded = false;
    try
    {
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
  
  public boolean deleteAfapSubdict(final AfapSubdict ca)
  {
    boolean isDeleted = false;
    try
    {
      getHibernateTemplate().execute(new HibernateCallback()
      {
        public Object doInHibernate(Session session)
          throws HibernateException, SQLException
        {
          session.delete(ca);
          return null;
        }
      });
      isDeleted = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isDeleted = false;
    }
    return isDeleted;
  }
  
  public AfapSubdict[] getAfapSubdictBymap(final Map map)
  {
    List mainlist = getHibernateTemplate().loadAll(AfapMaindict.class);
    final List list = new ArrayList();
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Criteria criteria = session
          .createCriteria(AfapSubdict.class);
        if ((!ComFunction.isEmpty(map.get("item"))) && (!"0".equals(map.get("item")))) {
          criteria.add(Expression.eq("comp_id.item", map.get("item")));
        }
        if ((!ComFunction.isEmpty(map.get("code"))) && (!"0".equals(map.get("code")))) {
          criteria.add(Expression.eq("comp_id.code", map.get("code")));
        }
        if ((!ComFunction.isEmpty(map.get("codename"))) && (!"0".equals(map.get("codename"))))
        {
          String tmpStr = "%" + map.get("codename") + "%";
          criteria.add(Expression.like("codename", tmpStr));
        }
        list.addAll(criteria.list());
        return null;
      }
    });
    for (Iterator iter = list.iterator(); iter.hasNext();)
    {
      AfapSubdict cf = (AfapSubdict)iter.next();
      for (Iterator iterator = mainlist.iterator(); iterator.hasNext();)
      {
        AfapMaindict main = (AfapMaindict)iterator.next();
        if (cf.getComp_id().getItem().equals(main.getItem()))
        {
          cf.setItemcname(main.getItemcname());
          break;
        }
      }
    }
    return (AfapSubdict[])list.toArray(new AfapSubdict[0]);
  }
  
  public AfapSubdict[] getAfapSubdictBysql2(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find("FROM AfapSubdict sdict WHERE sdict.comp_id.item=?", sql);
    return (AfapSubdict[])list.toArray(new AfapSubdict[0]);
  }
  
  public AfapSubdict[] getAllAfapSubdict()
  {
    List ls = new ArrayList();
    try
    {
      ls = getHibernateTemplate().loadAll(AfapSubdict.class);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return (AfapSubdict[])ls.toArray(new AfapSubdict[0]);
  }
  
  public AfapSubdict[] getLable(String itemename, int flag)
  {
    List list = new ArrayList();
    List mainlist = new ArrayList();
    mainlist = getHibernateTemplate().find("FROM AfapMaindict mdict WHERE mdict.itemename=?", itemename);
    if (!mainlist.isEmpty())
    {
      AfapMaindict[] maindcit = (AfapMaindict[])mainlist.toArray(new AfapMaindict[0]);
      String tmpStr = "";
      tmpStr = maindcit[0].getItem();
      list = getHibernateTemplate().find("FROM AfapSubdict sdict WHERE sdict.comp_id.item=?", tmpStr);
      if (list.isEmpty())
      {
        if (flag == 1)
        {
          AfapSubdictPK subPK = new AfapSubdictPK("00001", "00000");
          String tmp = "全部";
          AfapSubdict subdict = new AfapSubdict(subPK, tmp);
          list.add(0, subdict);
        }
        AfapSubdictPK subPK = new AfapSubdictPK("00001", "00001");
        String tmp = "请添加" + itemename + "的子表详情";
        AfapSubdict subdict = new AfapSubdict(subPK, tmp);
        list.add(subdict);
      }
      else if (flag == 1)
      {
        AfapSubdictPK subPK = new AfapSubdictPK("00001", "00000");
        String tmp = "全部";
        AfapSubdict subdict = new AfapSubdict(subPK, tmp);
        list.add(0, subdict);
      }
    }
    else if (flag == 1)
    {
      AfapSubdictPK subPK = new AfapSubdictPK("00001", "00000");
      String tmp = "全部";
      AfapSubdict subdict = new AfapSubdict(subPK, tmp);
      list.add(0, subdict);
      
      AfapSubdictPK subPK2 = new AfapSubdictPK("00001", "001");
      String tmp2 = "请添加" + itemename + "到数据字典!";
      AfapSubdict subdict2 = new AfapSubdict(subPK2, tmp2);
      list.add(1, subdict2);
    }
    else
    {
      AfapSubdictPK subPK = new AfapSubdictPK("00001", "001");
      String tmp = "请添加" + itemename + "到数据字典!";
      AfapSubdict subdict = new AfapSubdict(subPK, tmp);
      list.add(subdict);
    }
    return (AfapSubdict[])list.toArray(new AfapSubdict[0]);
  }
  
  public AfapSubdict getAfaBranchType(String itemename, int type)
  {
    List list = new ArrayList();
    List mainlist = new ArrayList();
    mainlist = getHibernateTemplate().find("FROM AfapMaindict mdict WHERE mdict.itemename=?", itemename);
    if (!mainlist.isEmpty())
    {
      AfapMaindict[] maindcit = (AfapMaindict[])mainlist.toArray(new AfapMaindict[0]);
      String tmpStr = "";
      tmpStr = maindcit[0].getItem();
      list = getHibernateTemplate().find("FROM AfapSubdict sdict WHERE sdict.comp_id.item=?", tmpStr);
      AfapSubdict localAfapSubdict = (AfapSubdict)list.get(0);
    }
    return (AfapSubdict)list.get(0);
  }
  
  public AfapSubdict getAfaSubdictCodename(String itemename, String code)
  {
    List list = new ArrayList();
    List mainlist = new ArrayList();
    mainlist = getHibernateTemplate().find("FROM AfapMaindict mdict WHERE mdict.itemename=?", itemename);
    if (!mainlist.isEmpty())
    {
      AfapMaindict[] maindcit = (AfapMaindict[])mainlist.toArray(new AfapMaindict[0]);
      String[] str = new String[2];
      String tmpStr = "";
      str[0] = new String();
      str[1] = new String();
      tmpStr = maindcit[0].getItem();
      str[0] = tmpStr;
      str[1] = code;
      Type[] type = new Type[2];
      type[0] = Hibernate.STRING;
      type[1] = Hibernate.STRING;
      list = getHibernateTemplate().find("FROM AfapSubdict sdict WHERE sdict.comp_id.item=? and sdict.comp_id.code=?", new Object[] { str, type });
      AfapSubdict localAfapSubdict = (AfapSubdict)list.get(0);
    }
    return (AfapSubdict)list.get(0);
  }
  
  public boolean updateAfapSubdict(AfapSubdict ca)
  {
    boolean isUpdated = false;
    try
    {
      getHibernateTemplate().update(ca);
      isUpdated = true;
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      isUpdated = false;
    }
    return isUpdated;
  }
  
  public AfapSubdict getAfapSubdictBysql(String sql)
  {
    List list = new ArrayList();
    list = getHibernateTemplate().find(sql);
    return (AfapSubdict)list.get(0);
  }
  
  public List<AfapSubdict> getAfapSubdictByMap(Map<String, String> map, Pagination page)
  {
    Criteria criteria = getSession(true).createCriteria(AfapSubdict.class);
    if (map.get("item") != null) {
      criteria.add(Restrictions.eq("comp_id.item", map.get("item")));
    }
    if (map.get("code") != null) {
      criteria.add(Restrictions.eq("comp_id.code", map.get("code")));
    }
    if (map.get("codename") != null) {
      criteria.add(Restrictions.like("codename", (String)map.get("codename"), MatchMode.ANYWHERE));
    }
    criteria.addOrder(Order.asc("comp_id.item"));
    criteria.addOrder(Order.asc("comp_id.code"));
    page.setAllRecords(Integer.valueOf(criteria.list().size()));
    criteria.setFirstResult(page.getFirstRecord().intValue());
    criteria.setMaxResults(page.getPerPageRecords().intValue());
    
    return criteria.list();
  }
}
