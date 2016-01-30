package gnnt.MEBS.common.core.sysscheduler.impl;

import gnnt.MEBS.common.core.dao.SysSchedulerDAO;
import gnnt.MEBS.common.core.sysscheduler.ISysScheduler;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SysSchedulerImpl
  implements ISysScheduler
{
  private Log log = LogFactory.getLog(SysSchedulerImpl.class);
  private SysSchedulerDAO sysSchedulerDAO;
  private long diffTime;
  
  public void setSysSchedulerDAO(SysSchedulerDAO paramSysSchedulerDAO)
  {
    this.sysSchedulerDAO = paramSysSchedulerDAO;
  }
  
  public void loadInitData()
  {
    this.diffTime = (this.sysSchedulerDAO.getCurDbDate().getTime() - System.currentTimeMillis());
    if (this.diffTime > 0L) {
      this.log.info("DB服务器时间比交易服务器时间早" + this.diffTime + "毫秒");
    } else {
      this.log.info("DB服务器时间比交易服务器时间晚" + (0L - this.diffTime) + "毫秒");
    }
  }
  
  public long getDiffTime()
  {
    return this.diffTime;
  }
  
  public void moveHistory(Date paramDate)
  {
    this.sysSchedulerDAO.moveHistory(paramDate);
  }
}
