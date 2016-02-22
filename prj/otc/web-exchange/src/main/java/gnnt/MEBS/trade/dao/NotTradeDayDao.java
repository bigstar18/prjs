package gnnt.MEBS.trade.dao;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.trade.model.DaySection;
import gnnt.MEBS.trade.model.NotTradeDay;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("notTradeDayDao")
public class NotTradeDayDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(NotTradeDayDao.class);
  
  public Class getEntityClass()
  {
    return new NotTradeDay().getClass();
  }
  
  public void updateDaySectionOther(DaySection daySection)
  {
    final String sql = "update gnnt.MEBS.trade.model.DaySection set Status = " + daySection.getStatus() + ", ModifyTime = sysdate where WeekDay = " + 
      daySection.getWeekDay() + " and SectionID = " + daySection.getSectionId();
    this.logger.debug("updateDaySectionOther    sql:" + sql);
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
      {
        Query query = session.createQuery(sql);
        query.executeUpdate();
        return null;
      }
    });
  }
  
  public void updateDaySectionNo(DaySection daySection)
  {
    final String sql = "update gnnt.MEBS.trade.model.DaySection set Status = " + daySection.getStatus() + ", ModifyTime = sysdate where WeekDay = " + 
      daySection.getWeekDay();
    this.logger.debug("updateDaySectionNo    sql:" + sql);
    getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
      {
        Query query = session.createQuery(sql);
        query.executeUpdate();
        return null;
      }
    });
  }
  
  public List<Clone> queryForList(String ri, String sectionIDs)
  {
    final String sql = "from gnnt.MEBS.trade.model.DaySection where WeekDay = " + ri + " and SectionID not in (" + sectionIDs + ")";
    this.logger.debug("queryForList    sql:" + sql);
    List<Clone> list = (List)getHibernateTemplate().execute(new HibernateCallback()
    {
      public Object doInHibernate(Session session)
        throws HibernateException, SQLException
      {
        Query query = session.createQuery(sql);
        return query.list();
      }
    });
    return list;
  }
}
