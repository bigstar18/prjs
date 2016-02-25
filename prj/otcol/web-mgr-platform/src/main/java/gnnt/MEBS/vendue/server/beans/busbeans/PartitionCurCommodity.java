package gnnt.MEBS.vendue.server.beans.busbeans;

import gnnt.MEBS.vendue.server.beans.dtobeans.SysCurStatus;
import java.util.ArrayList;
import java.util.List;

public class PartitionCurCommodity
  implements Cloneable
{
  private Long partitionId;
  private SysCurStatus sysCurStatus;
  private List curCommodityCompositBeanList = new ArrayList();
  private List curCommodityCompositBeanListForOutPut = new ArrayList();
  
  public Long getPartitionId()
  {
    return this.partitionId;
  }
  
  public void setPartitionId(Long paramLong)
  {
    this.partitionId = paramLong;
  }
  
  public List getCurCommodityCompositBeanList()
  {
    return this.curCommodityCompositBeanList;
  }
  
  public void setCurCommodityCompositBeanList(List paramList)
  {
    this.curCommodityCompositBeanList = paramList;
  }
  
  public SysCurStatus getSysCurStatus()
  {
    return this.sysCurStatus;
  }
  
  public void setSysCurStatus(SysCurStatus paramSysCurStatus)
  {
    this.sysCurStatus = paramSysCurStatus;
  }
  
  public List getCurCommodityCompositBeanListForOutPut()
  {
    return this.curCommodityCompositBeanListForOutPut;
  }
  
  public void setCurCommodityCompositBeanListForOutPut(List paramList)
  {
    this.curCommodityCompositBeanListForOutPut = paramList;
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    PartitionCurCommodity localPartitionCurCommodity = (PartitionCurCommodity)super.clone();
    localPartitionCurCommodity.curCommodityCompositBeanList = ((ArrayList)((ArrayList)localPartitionCurCommodity.curCommodityCompositBeanList).clone());
    return localPartitionCurCommodity;
  }
}
