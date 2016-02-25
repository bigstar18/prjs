package gnnt.MEBS.timebargain.manage.model;

import gnnt.MEBS.timebargain.manage.util.RWProperty;
import gnnt.MEBS.timebargain.manage.util.Utils;

public class MonitorSet
{
  private int refreshTime;
  private int pageSize;
  private static MonitorSet instance = null;
  
  public static synchronized MonitorSet getInstance()
  {
    if (instance == null) {
      instance = new MonitorSet();
    }
    return instance;
  }
  
  private MonitorSet()
  {
    String str1 = RWProperty.readValue("monitorConfig.properties", "pageSize");
    this.pageSize = Utils.parseInt(str1, 10);
    String str2 = RWProperty.readValue("monitorConfig.properties", "timerInterval");
    this.refreshTime = Utils.parseInt(str2, 5);
  }
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int paramInt)
  {
    RWProperty.writeProperties("monitorConfig.properties", "pageSize", paramInt + "");
    this.pageSize = paramInt;
  }
  
  public int getRefreshTime()
  {
    return this.refreshTime;
  }
  
  public void setRefreshTime(int paramInt)
  {
    RWProperty.writeProperties("monitorConfig.properties", "timerInterval", paramInt + "");
    this.refreshTime = paramInt;
  }
}
