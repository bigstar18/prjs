package gnnt.MEBS.announcement.dao;

import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("noticeDao")
public class NoticeDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(NoticeDao.class);
  
  public Class getEntityClass()
  {
    return new Notice().getClass();
  }
  
  public void addNotice(Notice notice)
  {
    getHibernateTemplate().save(notice.getClass().getName(), notice);
  }
  
  public List<Notice> addOKNoticeList(QueryConditions conditions, PageInfo pageInfo, String userId)
  {
    HttpServletRequest request = (HttpServletRequest)
      ThreadStore.get(ThreadStoreConstant.REQUEST);
    String memberno = (String)request.getSession().getAttribute(ActionConstant.REGISTERID);
    String hql = "select notice from Notice as notice where notice.id in (select noticeId  from OKNotice as okNotice where (okNotice.noticeTarget = 'A' or (okNotice.noticeTarget = 'M' and okNotice.recipient = '" + 
      memberno + "') or (okNotice.noticeTarget = 'N' " + 
      "and okNotice.recipient = '" + userId + "'))  and okNotice.recipientType = 'S')";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    this.logger.debug("NoticeDao    hql:" + hql);
    List<Notice> list = queryByHQL(hql, names, values, pageInfo, null);
    
    return list;
  }
}
