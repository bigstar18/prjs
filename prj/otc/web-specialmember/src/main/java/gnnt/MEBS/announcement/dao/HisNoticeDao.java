package gnnt.MEBS.announcement.dao;

import gnnt.MEBS.announcement.model.HisNotice;
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

@Repository("hisNoticeDao")
public class HisNoticeDao
  extends BaseDao
{
  private final transient Log logger = LogFactory.getLog(HisNoticeDao.class);
  
  public Class getEntityClass()
  {
    return new HisNotice().getClass();
  }
  
  public List<HisNotice> addOKNoticeList(QueryConditions conditions, PageInfo pageInfo, String userId)
  {
    HttpServletRequest request = (HttpServletRequest)
      ThreadStore.get(ThreadStoreConstant.REQUEST);
    String memberno = (String)request.getSession().getAttribute(ActionConstant.REGISTERID);
    String hql = "select notice from HisNotice as notice where notice.id in ( select noticeId from HisOKNotice as okNotice where  (okNotice.noticeTarget = 'A' or (okNotice.noticeTarget = 'M' and okNotice.recipient = '" + 
      memberno + "') or (okNotice.noticeTarget = 'N' " + 
      "and okNotice.recipient = '" + userId + "')) and okNotice.recipientType = 'S')";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    this.logger.debug("NoticeDao    hql:" + hql);
    List<HisNotice> list = queryByHQL(hql, names, values, pageInfo, null);
    
    return list;
  }
}
