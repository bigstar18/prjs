package gnnt.MEBS.timebargain.server.riskcontrol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RiskcontrolEngine
{
  private Log log = LogFactory.getLog(getClass());
  private FloatingComputer floatingComputer;
  private UpdateRiskRateAll updateRiskRateAll;
  private UpdateHighRiskThread updateHighRiskThread;
  private static RiskcontrolEngine instance;
  private int status = 1;
  public static final int STATUS_COMPUTING = 0;
  public static final int STATUS_PAUSE = 1;
  
  public static RiskcontrolEngine getInstance()
  {
    if (instance == null) {
      instance = new RiskcontrolEngine();
    }
    return instance;
  }
  
  public void init()
  {
    this.log.debug("初始化风险控制引擎");
    
    this.floatingComputer = FloatingComputer.getInstance();
    this.floatingComputer.init();
    
    this.updateRiskRateAll = UpdateRiskRateAll.getInstance();
    this.updateRiskRateAll.init();
    








    this.log.debug("初始化风险控制引擎成功！");
  }
  
  public void setStatus(int status)
  {
    this.status = status;
    this.floatingComputer.setStatus(status);
    this.updateRiskRateAll.setStatus(status);
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public FloatingComputer getFloatingComputer()
  {
    return this.floatingComputer;
  }
  
  public void setFloatingComputer(FloatingComputer floatingComputer)
  {
    this.floatingComputer = floatingComputer;
  }
  
  public void stop()
  {
    if (this.floatingComputer != null)
    {
      this.floatingComputer.close();
      this.floatingComputer = null;
    }
    if (this.updateRiskRateAll != null)
    {
      this.updateRiskRateAll.close();
      this.updateRiskRateAll = null;
    }
    if (this.updateHighRiskThread != null)
    {
      this.updateHighRiskThread.close();
      this.updateHighRiskThread = null;
    }
    instance = null;
  }
}
