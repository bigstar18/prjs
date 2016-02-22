package gnnt.MEBS.announcement.dao;

import gnnt.MEBS.announcement.model.HisNotice;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("hisNoticeDao")
public class HisNoticeDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(HisNoticeDao.class);
  
  public Class getEntityClass()
  {
    return new HisNotice().getClass();
  }
  
  public List<HisNotice> getMemberOKNoticeList(QueryConditions conditions, PageInfo pageInfo, String userId)
  {
    String hql = "select notice from HisNotice as notice where notice.id in (select noticeId  from HisOKNotice as okNotice where  (okNotice.noticeTarget = 'A' or (okNotice.noticeTarget = 'M' and okNotice.recipient = (select t.memberId from MemberUser t where t.userId = '" + 
      userId + "')) or (okNotice.noticeTarget = 'N' " + 
      "and okNotice.recipient = '" + userId + "')) )";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    List<HisNotice> list = queryByHQL(hql, names, values, pageInfo, null);
    
    return list;
  }
  
  public List<HisNotice> getOrganizationOKNoticeList(QueryConditions conditions, PageInfo pageInfo, String userId)
  {
    String hql = "select notice from HisNotice as notice where notice.id in (select noticeId  from HisOKNotice as okNotice where ((okNotice.noticeTarget = 'A' and okNotice.recipientType = 'M' and okNotice.isIncludeSub = 'Y') or (okNotice.noticeTarget = 'M' and okNotice.recipientType = 'M' and  okNotice.recipient = (select t.memberNo from Brokerage t where t.brokerageNo = '" + 
      userId + "') " + 
      "and okNotice.isIncludeSub = 'Y') or (okNotice.recipientType = 'B' and okNotice.noticeTarget = 'A' and okNotice.recipient = (select t.memberNo from Brokerage t where t.brokerageNo = '" + 
      userId + "')) or (okNotice.recipientType = 'B' and okNotice.noticeTarget = 'M' and okNotice.recipient = '" + userId + "')))";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    List<HisNotice> list = queryByHQL(hql, names, values, pageInfo, null);
    
    return list;
  }
}
