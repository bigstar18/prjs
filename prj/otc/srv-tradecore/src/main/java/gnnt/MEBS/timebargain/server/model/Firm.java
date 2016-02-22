package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.Map;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Firm
  implements Serializable
{
  private static final long serialVersionUID = 4690197650654049821L;
  private String firmID;
  private char status;
  public static final char STATUS_NORMAL = 'N';
  public static final char STATUS_FORBID = 'F';
  private Map<String, Margin_RT> marginMap;
  private Map<String, Fee_RT> feeMap;
  private Map<String, HoldQty_RT> holdQtyMap;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  public char getStatus()
  {
    return this.status;
  }
  
  public void setStatus(char status)
  {
    this.status = status;
  }
  
  public Map<String, Margin_RT> getMarginMap()
  {
    return this.marginMap;
  }
  
  public void setMarginMap(Map<String, Margin_RT> marginMap)
  {
    this.marginMap = marginMap;
  }
  
  public Map<String, Fee_RT> getFeeMap()
  {
    return this.feeMap;
  }
  
  public void setFeeMap(Map<String, Fee_RT> feeMap)
  {
    this.feeMap = feeMap;
  }
  
  public Map<String, HoldQty_RT> getHoldQtyMap()
  {
    return this.holdQtyMap;
  }
  
  public void setHoldQtyMap(Map<String, HoldQty_RT> holdQtyMap)
  {
    this.holdQtyMap = holdQtyMap;
  }
}
