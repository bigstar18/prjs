package gnnt.MEBS.transformhq.server.quotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HQStatus
{
  private Log log = LogFactory.getLog(HQStatus.class);
  private String status = "0";
  
  public boolean isUsable()
  {
    if (this.status.equals("0")) {
      return true;
    }
    return false;
  }
  
  public void changeStatusTure()
  {
    if (!this.status.equals("0"))
    {
      this.status = "0";
      this.log.warn("当前行情源状态修改为正常状态");
    }
  }
  
  public void changeStatusFalse()
  {
    if (!this.status.equals("1"))
    {
      this.status = "1";
      this.log.warn("当前行情源状态修改为非正常状态");
    }
  }
  
  public String getStatus()
  {
    return this.status;
  }
}
