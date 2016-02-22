package gnnt.MEBS.announcement.dao;

import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
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
  
  public List<Notice> getCustomerOKNoticeList(QueryConditions conditions, PageInfo pageInfo, String userId)
  {
    String hql = "select notice from Notice as notice where notice.id in(select noticeId from OKNotice as okNotice where (okNotice.noticeTarget = 'A' and okNotice.recipientType = 'C') or (okNotice.noticeTarget = 'M' and okNotice.recipient = (select t.memberNo from Customer t where t.customerNo = '" + 
      userId + "') and okNotice.recipientType = 'C') or (okNotice.noticeTarget = 'N' " + 
      "and okNotice.recipient = '" + userId + "' and okNotice.recipientType = 'C') or (okNotice.recipientType = 'O' and okNotice.noticeTarget = 'A' and okNotice.isIncludeSub = 'Y'" + 
      " and okNotice.recipient = (select t.memberNo from Customer t where t.customerNo = '" + userId + "')) or (okNotice.recipientType = 'O' and okNotice.noticeTarget = 'M' and okNotice.isIncludeSub = 'Y'" + 
      " and okNotice.recipient = (select t.organizationNo from CustomerMappingBroker t where t.customerNo = '" + userId + "'))) ";
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
  
  public List<Notice> getMemberOKNoticeList(QueryConditions conditions, PageInfo pageInfo, String userId)
  {
    String hql = "select notice from Notice as notice where notice.id in ( select noticeId from OKNotice as okNotice where (okNotice.noticeTarget = 'A' and okNotice.recipientType = 'M') or (okNotice.noticeTarget = 'M' and okNotice.recipientType = 'M' and okNotice.recipient = '" + 
      userId + "') or (okNotice.noticeTarget = 'N' and okNotice.recipientType = 'C' " + 
      "and okNotice.recipient = '" + userId + "'))";
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
