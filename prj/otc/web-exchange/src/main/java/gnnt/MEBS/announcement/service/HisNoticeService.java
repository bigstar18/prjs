package gnnt.MEBS.announcement.service;

import gnnt.MEBS.announcement.dao.HisNoticeDao;
import gnnt.MEBS.announcement.dao.HisOKNoticeDao;
import gnnt.MEBS.announcement.model.HisNotice;
import gnnt.MEBS.announcement.model.HisOKNotice;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("hisNoticeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class HisNoticeService
  extends BaseService<HisNotice>
{
  private final transient Log logger = LogFactory.getLog(HisNoticeService.class);
  @Autowired
  @Qualifier("hisNoticeDao")
  private HisNoticeDao hisNoticeDao;
  @Autowired
  @Qualifier("hisOkNoticeDao")
  private HisOKNoticeDao hisOkNoticeDao;
  
  public BaseDao getDao()
  {
    return this.hisNoticeDao;
  }
  
  public List<HisNotice> getList(QueryConditions qc, PageInfo pageInfo)
  {
    return this.hisNoticeDao.addOKNoticeList(qc, pageInfo);
  }
  
  public Clone get(Clone clone, Long okoticeId)
  {
    Serializable id = clone.getId();
    if (id == null) {
      return getDao().get(clone);
    }
    HisOKNotice okNotice = (HisOKNotice)this.hisOkNoticeDao.getById(okoticeId);
    okNotice.setStatus("Y");
    this.hisOkNoticeDao.update(okNotice);
    
    return getDao().getById(id);
  }
}
