package gnnt.MEBS.globalLog.service;

import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.dao.OperateLogDao;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("globalLogService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class OperateLogService
  extends BaseService<OperateLog>
{
  private final transient Log logger = LogFactory.getLog(OperateLogService.class);
  @Autowired
  @Qualifier("operateLogDao")
  private OperateLogDao operateLogDao;
  
  public BaseDao getDao()
  {
    return this.operateLogDao;
  }
  
  public int add(OperateLog obj)
  {
    this.logger.debug("enter add");
    if ((ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && (obj.getOperateLogType() == 0)) {
      obj.setOperateLogType(((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue());
    }
    obj.setOperateDate(this.operateLogDao.getSysDate());
    getDao().add(obj);
    ThreadStore.put(ThreadStoreConstant.ISLOG, null);
    int num = 2;
    return num;
  }
}
