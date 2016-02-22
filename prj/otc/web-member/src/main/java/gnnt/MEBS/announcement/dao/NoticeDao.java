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
  
  public List<Notice> getMemberOKNoticeList(QueryConditions conditions, PageInfo pageInfo, String userId)
  {
    HttpServletRequest request = (HttpServletRequest)
      ThreadStore.get(ThreadStoreConstant.REQUEST);
    String memberno = (String)request.getSession().getAttribute(ActionConstant.REGISTERID);
    String hql = "select notice from Notice as notice where notice.id in (select noticeId from OKNotice as okNotice where ((okNotice.noticeTarget = 'A' and okNotice.recipientType = 'M') or (okNotice.noticeTarget = 'M' and okNotice.recipient = '" + 
      memberno + "' and okNotice.recipientType = 'M') or (okNotice.noticeTarget = 'N' " + 
      "and okNotice.recipient = '" + userId + "') and okNotice.recipientType = 'M')) ";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    List<Notice> list = queryByHQL(hql, names, values, pageInfo, null);
    
    return list;
  }
  
  public List<Notice> getOrganizationOKNoticeList(QueryConditions conditions, PageInfo pageInfo, String userId)
  {
    HttpServletRequest request = (HttpServletRequest)
      ThreadStore.get(ThreadStoreConstant.REQUEST);
    String memberno = (String)request.getSession().getAttribute(ActionConstant.REGISTERID);
    String hql = "select notice from Notice as notice where notice.id  in (select noticeId from OKNotice as okNotice where (okNotice.noticeTarget = 'A' and okNotice.isIncludeSub = 'Y' and okNotice.recipientType = 'M') or (okNotice.noticeTarget = 'M' and okNotice.isIncludeSub = 'Y' and okNotice.recipient = '" + 
      memberno + "' and okNotice.recipientType = 'M') or (okNotice.noticeTarget = 'A' and okNotice.recipient = '" + memberno + "' and okNotice.recipientType = 'O') or (okNotice.recipientType = 'O' and okNotice.noticeTarget = 'M' and okNotice.recipient = (select t.organization.organizationNO from User t where t.userId = '" + 
      userId + "')))";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    List<Notice> list = queryByHQL(hql, names, values, pageInfo, null);
    
    return list;
  }
}
