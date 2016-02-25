package gnnt.MEBS.delivery.workflow;

import gnnt.MEBS.delivery.model.OperateLog;
import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;

public class OriginalModel
{
  private WorkFlowClone object;
  private OperateLog log;
  private int holdAuthority;
  private String operate;
  
  public String getOperate()
  {
    return this.operate;
  }
  
  public void setOperate(String paramString)
  {
    this.operate = paramString;
  }
  
  public WorkFlowClone getObject()
  {
    return this.object;
  }
  
  public void setObject(WorkFlowClone paramWorkFlowClone)
  {
    this.object = paramWorkFlowClone;
  }
  
  public OperateLog getLog()
  {
    return this.log;
  }
  
  public void setLog(OperateLog paramOperateLog)
  {
    this.log = paramOperateLog;
  }
  
  public int getHoldAuthority()
  {
    return this.holdAuthority;
  }
  
  public void setHoldAuthority(int paramInt)
  {
    this.holdAuthority = paramInt;
  }
}
