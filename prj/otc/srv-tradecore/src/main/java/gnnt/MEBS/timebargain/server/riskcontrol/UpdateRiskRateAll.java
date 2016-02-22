package gnnt.MEBS.timebargain.server.riskcontrol;

import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UpdateRiskRateAll
{
  private Log log = LogFactory.getLog(getClass());
  private long timespace;
  private UpdateRiskRateAllThread thread;
  private static UpdateRiskRateAll instance;
  
  public static UpdateRiskRateAll getInstance()
  {
    if (instance == null) {
      instance = new UpdateRiskRateAll();
    }
    return instance;
  }
  
  public void init()
  {
    close();
    
    this.timespace = Long.parseLong(
      DAOBeanFactory.getConfig("UpdateRiskRateSpace"));
    
    this.thread = new UpdateRiskRateAllThread();
    this.thread.setPriority(1);
    this.thread.init(this.timespace);
    this.thread.start();
    
    this.log.info("启动更新所有交易员风险率线程！");
  }
  
  public void close()
  {
    if (this.thread != null)
    {
      this.thread.close();
      this.thread = null;
    }
  }
  
  public void setStatus(int status)
  {
    if (this.thread != null) {
      this.thread.setStatus(status);
    }
  }
  
  public long getTimespace()
  {
    return this.timespace;
  }
}
