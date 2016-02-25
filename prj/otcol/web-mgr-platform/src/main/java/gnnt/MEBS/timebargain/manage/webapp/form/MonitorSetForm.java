package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class MonitorSetForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049882L;
  private int refreshTime;
  private int pageSize;
  
  public int getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(int paramInt)
  {
    this.pageSize = paramInt;
  }
  
  public int getRefreshTime()
  {
    return this.refreshTime;
  }
  
  public void setRefreshTime(int paramInt)
  {
    this.refreshTime = paramInt;
  }
}
