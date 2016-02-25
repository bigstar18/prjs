package gnnt.MEBS.delivery.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.LogDao;
import gnnt.MEBS.delivery.model.LogValue;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_logService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class LogService
{
  @Autowired
  @Qualifier("w_logDao")
  private LogDao logDao;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public LogValue getLogById(String paramString)
  {
    return this.logDao.getLogById(paramString);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getLogList(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    return this.logDao.getLogList(paramQueryConditions, paramPageInfo, paramString);
  }
  
  public void addLog(LogValue paramLogValue)
  {
    this.logDao.addLog(paramLogValue);
  }
}
