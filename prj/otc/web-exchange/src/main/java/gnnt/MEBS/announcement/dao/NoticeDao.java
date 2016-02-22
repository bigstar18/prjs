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
  
  public List<Notice> addOKNoticeList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new Notice(notice.id,okNotice.status,okNotice.id,notice.author,notice.title,notice.sendTime,notice.expiryTime,notice.content) from Notice as notice,OKNotice as okNotice where notice.id=okNotice.noticeId ";
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
