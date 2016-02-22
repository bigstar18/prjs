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
  
  public List<HisNotice> addOKNoticeList(QueryConditions conditions, PageInfo pageInfo)
  {
    String hql = "select new HisNotice(notice.id,okNotice.id,notice.author,notice.title,notice.sendTime) from HisNotice as notice,HisOKNotice as okNotice where notice.id=okNotice.noticeId";
    Object[] values = (Object[])null;
    String[] names = (String[])null;
    if ((conditions != null) && (conditions.getFieldsSqlClause() != null))
    {
      values = conditions.getValueArray();
      names = conditions.getNameArray();
      hql = hql + " and " + conditions.getFieldsSqlClause();
    }
    this.logger.debug("HisNoticeDao    hql:" + hql);
    List<HisNotice> list = queryByHQL(hql, names, values, pageInfo, null);
    
    return list;
  }
}
