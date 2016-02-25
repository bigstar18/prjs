package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TradeRule
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049813L;
  private String marketCode;
  private Integer groupID;
  private String customerID;
  private Double FeeDiscountRate;
  private Double MarginDiscountRate;
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof TradeRule)) {
      return false;
    }
    TradeRule localTradeRule = (TradeRule)paramObject;
    return this.groupID != null ? this.groupID.equals(localTradeRule.groupID) : localTradeRule.groupID == null;
  }
  
  public int hashCode()
  {
    return this.groupID != null ? this.groupID.hashCode() : 0;
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
  
  public Double getFeeDiscountRate()
  {
    return this.FeeDiscountRate;
  }
  
  public void setFeeDiscountRate(Double paramDouble)
  {
    this.FeeDiscountRate = paramDouble;
  }
  
  public Integer getGroupID()
  {
    return this.groupID;
  }
  
  public void setGroupID(Integer paramInteger)
  {
    this.groupID = paramInteger;
  }
  
  public Double getMarginDiscountRate()
  {
    return this.MarginDiscountRate;
  }
  
  public void setMarginDiscountRate(Double paramDouble)
  {
    this.MarginDiscountRate = paramDouble;
  }
  
  public String getMarketCode()
  {
    return this.marketCode;
  }
  
  public void setMarketCode(String paramString)
  {
    this.marketCode = paramString;
  }
}
