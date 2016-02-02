package gnnt.MEBS.transformhq.server.quotation;

import gnnt.MEBS.transformhq.server.model.IPConfig;
import gnnt.MEBS.transformhq.server.tools.foctory.HQBeanFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QuotationCheckThread
  extends Thread
{
  private Log log = LogFactory.getLog(QuotationCheckThread.class);
  private List<IPConfig> ipConfigList = new ArrayList();
  private IPConfig useIPConifg = null;
  private long diffTime = 20000L;
  private long lastUseRoadTime = System.currentTimeMillis();
  private HQStatus hqStatus;
  
  public QuotationCheckThread(HQStatus hqStatus)
  {
    this.hqStatus = hqStatus;
    this.diffTime = (Integer.valueOf(HQBeanFactory.getConfig("diffTime")).intValue() * 1000);
    this.ipConfigList = ((List)HQBeanFactory.getBean("ipConfigList"));
  }
  
  public void run()
  {
    try
    {
      for (;;)
      {
        IPConfig findIPconfig = findHighLevelUseIPConfig();
        if (findIPconfig != null)
        {
          if ((this.useIPConifg == null) || (!this.useIPConifg.isUsable()))
          {
            this.useIPConifg = findIPconfig;
            this.log.warn("行情切换至：" + this.useIPConifg.toString());
          }
          else if (findIPconfig.getRoadLevel() < this.useIPConifg.getRoadLevel())
          {
            this.useIPConifg = findIPconfig;
            this.log.warn("行情切换至：" + this.useIPConifg.toString());
          }
          setLastUseIPConfigTime();
        }
        else if ((this.diffTime >= 0L) && (System.currentTimeMillis() - this.lastUseRoadTime > this.diffTime))
        {
          this.hqStatus.changeStatusFalse();
        }
        sleep(1000L);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      this.log.error("检查行情线路时报错：" + e);
    }
  }
  
  public void setLastUseIPConfigTime()
  {
    this.hqStatus.changeStatusTure();
    this.lastUseRoadTime = System.currentTimeMillis();
  }
  
  public IPConfig findHighLevelUseIPConfig()
  {
    IPConfig highLevel = null;
    for (IPConfig ipConfig : this.ipConfigList) {
      if (ipConfig.isUsable()) {
        if ((highLevel == null) || (ipConfig.getRoadLevel() < highLevel.getRoadLevel())) {
          highLevel = ipConfig;
        }
      }
    }
    return highLevel;
  }
  
  public List<IPConfig> getIPConfigList()
  {
    return this.ipConfigList;
  }
  
  public IPConfig getUseIPConfig()
  {
    return this.useIPConifg;
  }
}
