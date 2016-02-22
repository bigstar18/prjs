package gnnt.MEBS.broke.dao;

import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.model.MBrokerage;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("mBrokerageDao")
public class MBrokerageDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(MBrokerageDao.class);
  
  public Class getEntityClass()
  {
    return new MBrokerage().getClass();
  }
  
  public Brokerage queryByNo(String no, String memberNo)
  {
    Brokerage brokerage = null;
    
    String[] str = { no, memberNo };
    List list = getHibernateTemplate().find("from Brokerage t where t.brokerageNo=? and t.memberNo=?", str);
    if ((list != null) && (list.size() > 0)) {
      brokerage = (Brokerage)list.get(0);
    }
    return brokerage;
  }
  
  public void addMBroker(List<MBrokerage> list)
  {
    if ((list != null) && (list.size() > 0)) {
      for (MBrokerage mBrokerage : list) {
        getHibernateTemplate().save(mBrokerage);
      }
    }
  }
  
  public void deleteBroker(List<MBrokerage> list)
  {
    getHibernateTemplate().deleteAll(list);
  }
  
  public List<MBrokerage> queryMBroker(String memberNo)
  {
    List<MBrokerage> list = null;
    

    list = getHibernateTemplate().find("from MBrokerage t where t.memberNo=?", memberNo);
    return list;
  }
  
  public List<Brokerage> queryBroker(String memberNo)
  {
    List<Brokerage> list = null;
    



    list = getHibernateTemplate().find("from Brokerage t where t.memberNo=?", memberNo);
    return list;
  }
}
