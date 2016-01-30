package gnnt.MEBS.timebargain.tradeweb.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CustomerFunds
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049812L;
  private String CustomerID;
  private Double InitFund;
  private Double InFund;
  private Double OutFund;
  private Double Balance;
  private Double FrozenFunds;
  private Double RuntimeFL;
  private Double ClearFL;
  private Double RuntimeMargin;
  private Double ClearMargin;
  private Double VirtualFunds;
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof CustomerFunds)) {
      return false;
    }
    CustomerFunds localCustomerFunds = (CustomerFunds)paramObject;
    return this.CustomerID != null ? this.CustomerID.equals(localCustomerFunds.CustomerID) : localCustomerFunds.CustomerID == null;
  }
  
  public int hashCode()
  {
    return this.CustomerID != null ? this.CustomerID.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public Double getBalance()
  {
    return this.Balance;
  }
  
  public void setBalance(Double paramDouble)
  {
    this.Balance = paramDouble;
  }
  
  public Double getClearFL()
  {
    return this.ClearFL;
  }
  
  public void setClearFL(Double paramDouble)
  {
    this.ClearFL = paramDouble;
  }
  
  public Double getClearMargin()
  {
    return this.ClearMargin;
  }
  
  public void setClearMargin(Double paramDouble)
  {
    this.ClearMargin = paramDouble;
  }
  
  public String getCustomerID()
  {
    return this.CustomerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.CustomerID = paramString;
  }
  
  public Double getFrozenFunds()
  {
    return this.FrozenFunds;
  }
  
  public void setFrozenFunds(Double paramDouble)
  {
    this.FrozenFunds = paramDouble;
  }
  
  public Double getRuntimeFL()
  {
    return this.RuntimeFL;
  }
  
  public void setRuntimeFL(Double paramDouble)
  {
    this.RuntimeFL = paramDouble;
  }
  
  public Double getRuntimeMargin()
  {
    return this.RuntimeMargin;
  }
  
  public void setRuntimeMargin(Double paramDouble)
  {
    this.RuntimeMargin = paramDouble;
  }
  
  public Double getVirtualFunds()
  {
    return this.VirtualFunds;
  }
  
  public void setVirtualFunds(Double paramDouble)
  {
    this.VirtualFunds = paramDouble;
  }
  
  public Double getInFund()
  {
    return this.InFund;
  }
  
  public void setInFund(Double paramDouble)
  {
    this.InFund = paramDouble;
  }
  
  public Double getInitFund()
  {
    return this.InitFund;
  }
  
  public void setInitFund(Double paramDouble)
  {
    this.InitFund = paramDouble;
  }
  
  public Double getOutFund()
  {
    return this.OutFund;
  }
  
  public void setOutFund(Double paramDouble)
  {
    this.OutFund = paramDouble;
  }
}
