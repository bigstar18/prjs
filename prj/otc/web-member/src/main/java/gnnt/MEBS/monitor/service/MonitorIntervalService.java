package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.MonitorIntervalDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("mIntervalService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MonitorIntervalService
{
  private final transient Log logger = LogFactory.getLog(QMemberVOService.class);
  @Autowired
  @Qualifier("mIntervalDao")
  private MonitorIntervalDao mIntervalDao;
  
  public BaseDao getDao()
  {
    return this.mIntervalDao;
  }
  
  public int getMInterval()
  {
    return this.mIntervalDao.getMInterval();
  }
}
