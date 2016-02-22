package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class HoldQty_RT
  implements Serializable
{
  private static final long serialVersionUID = 4690197650654049821L;
  private String commodityID;
  private String firmID;
  private long oneMaxOrderQty;
  private long oneMinOrderQty;
  private long maxHoldQty;
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  public long getOneMaxOrderQty()
  {
    return this.oneMaxOrderQty;
  }
  
  public void setOneMaxOrderQty(long oneMaxOrderQty)
  {
    this.oneMaxOrderQty = oneMaxOrderQty;
  }
  
  public long getOneMinOrderQty()
  {
    return this.oneMinOrderQty;
  }
  
  public void setOneMinOrderQty(long oneMinOrderQty)
  {
    this.oneMinOrderQty = oneMinOrderQty;
  }
  
  public long getMaxHoldQty()
  {
    return this.maxHoldQty;
  }
  
  public void setMaxHoldQty(long maxHoldQty)
  {
    this.maxHoldQty = maxHoldQty;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
