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
  
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CustomerFunds)) {
      return false;
    }
    CustomerFunds m = (CustomerFunds)o;
    
    return this.CustomerID != null ? this.CustomerID.equals(m.CustomerID) : m.CustomerID == null;
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
  
  public void setBalance(Double balance)
  {
    this.Balance = balance;
  }
  
  public Double getClearFL()
  {
    return this.ClearFL;
  }
  
  public void setClearFL(Double clearFL)
  {
    this.ClearFL = clearFL;
  }
  
  public Double getClearMargin()
  {
    return this.ClearMargin;
  }
  
  public void setClearMargin(Double clearMargin)
  {
    this.ClearMargin = clearMargin;
  }
  
  public String getCustomerID()
  {
    return this.CustomerID;
  }
  
  public void setCustomerID(String customerID)
  {
    this.CustomerID = customerID;
  }
  
  public Double getFrozenFunds()
  {
    return this.FrozenFunds;
  }
  
  public void setFrozenFunds(Double frozenFunds)
  {
    this.FrozenFunds = frozenFunds;
  }
  
  public Double getRuntimeFL()
  {
    return this.RuntimeFL;
  }
  
  public void setRuntimeFL(Double runtimeFL)
  {
    this.RuntimeFL = runtimeFL;
  }
  
  public Double getRuntimeMargin()
  {
    return this.RuntimeMargin;
  }
  
  public void setRuntimeMargin(Double runtimeMargin)
  {
    this.RuntimeMargin = runtimeMargin;
  }
  
  public Double getVirtualFunds()
  {
    return this.VirtualFunds;
  }
  
  public void setVirtualFunds(Double virtualFunds)
  {
    this.VirtualFunds = virtualFunds;
  }
  
  public Double getInFund()
  {
    return this.InFund;
  }
  
  public void setInFund(Double inFund)
  {
    this.InFund = inFund;
  }
  
  public Double getInitFund()
  {
    return this.InitFund;
  }
  
  public void setInitFund(Double initFund)
  {
    this.InitFund = initFund;
  }
  
  public Double getOutFund()
  {
    return this.OutFund;
  }
  
  public void setOutFund(Double outFund)
  {
    this.OutFund = outFund;
  }
}
