package gnnt.MEBS.announcement.service;

import gnnt.MEBS.announcement.dao.NoticeDao;
import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("noticeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class NoticeService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(NoticeService.class);
  @Autowired
  @Qualifier("noticeDao")
  private NoticeDao noticeDao;
  
  public BaseDao getDao()
  {
    return this.noticeDao;
  }
  
  public int addNotice(Notice notice)
  {
    int num = 0;
    this.noticeDao.addNotice(notice);
    num = 1;
    return num;
  }
  
  public List<Notice> getOKNoticeList(QueryConditions qc, PageInfo pageInfo, String userId)
  {
    return this.noticeDao.addOKNoticeList(qc, pageInfo, userId);
  }
}
