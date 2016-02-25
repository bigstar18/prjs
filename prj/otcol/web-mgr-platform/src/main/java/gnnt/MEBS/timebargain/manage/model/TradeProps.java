package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TradeProps
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049812L;
  private String customerID;
  private Long groupID;
  private Long maxHoldQty;
  private Double minClearDeposit;
  private Double maxOverdraft;
  private Date modifyTime;
  private Double virtualFunds;
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof TradeProps)) {
      return false;
    }
    TradeProps localTradeProps = (TradeProps)paramObject;
    return this.customerID != null ? this.customerID.equals(localTradeProps.customerID) : localTradeProps.customerID == null;
  }
  
  public int hashCode()
  {
    return this.customerID != null ? this.customerID.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public Long getGroupID()
  {
    return this.groupID;
  }
  
  public void setGroupID(Long paramLong)
  {
    this.groupID = paramLong;
  }
  
  public Long getMaxHoldQty()
  {
    return this.maxHoldQty;
  }
  
  public void setMaxHoldQty(Long paramLong)
  {
    this.maxHoldQty = paramLong;
  }
  
  public Double getMaxOverdraft()
  {
    return this.maxOverdraft;
  }
  
  public void setMaxOverdraft(Double paramDouble)
  {
    this.maxOverdraft = paramDouble;
  }
  
  public Double getMinClearDeposit()
  {
    return this.minClearDeposit;
  }
  
  public void setMinClearDeposit(Double paramDouble)
  {
    this.minClearDeposit = paramDouble;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Date paramDate)
  {
    this.modifyTime = paramDate;
  }
  
  public Double getVirtualFunds()
  {
    return this.virtualFunds;
  }
  
  public void setVirtualFunds(Double paramDouble)
  {
    this.virtualFunds = paramDouble;
  }
}
