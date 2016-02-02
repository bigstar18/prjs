package gnnt.MEBS.timebargain.plugin.condition.model;

import java.io.Serializable;
import java.util.Date;

public class ConditionOrder
  implements Serializable
{
  private static final long serialVersionUID = 591215593662564352L;
  private Long orderNo;
  private String customerID;
  private String firmID;
  private String traderID;
  private Double price;
  private Long amount;
  private Short orderType;
  private Short bs_flag;
  private Integer conditionOperation;
  private Integer conditionType;
  private Double conditionPrice;
  private String conditionCmtyID;
  private String cmtyID;
  private Date endDate;
  
  public Long getOrderNo()
  {
    return this.orderNo;
  }
  
  public void setOrderNo(Long paramLong)
  {
    this.orderNo = paramLong;
  }
  
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(Double paramDouble)
  {
    this.price = paramDouble;
  }
  
  public Long getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(Long paramLong)
  {
    this.amount = paramLong;
  }
  
  public Short getOrderType()
  {
    return this.orderType;
  }
  
  public void setOrderType(Short paramShort)
  {
    this.orderType = paramShort;
  }
  
  public Short getBs_flag()
  {
    return this.bs_flag;
  }
  
  public void setBs_flag(Short paramShort)
  {
    this.bs_flag = paramShort;
  }
  
  public Integer getConditionType()
  {
    return this.conditionType;
  }
  
  public void setConditionType(Integer paramInteger)
  {
    this.conditionType = paramInteger;
  }
  
  public Double getConditionPrice()
  {
    return this.conditionPrice;
  }
  
  public void setConditionPrice(Double paramDouble)
  {
    this.conditionPrice = paramDouble;
  }
  
  public Integer getConditionOperation()
  {
    return this.conditionOperation;
  }
  
  public void setConditionOperation(Integer paramInteger)
  {
    this.conditionOperation = paramInteger;
  }
  
  public String getCmtyID()
  {
    return this.cmtyID;
  }
  
  public void setCmtyID(String paramString)
  {
    this.cmtyID = paramString;
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String paramString)
  {
    this.firmID = paramString;
  }
  
  public String getTraderID()
  {
    return this.traderID;
  }
  
  public void setTraderID(String paramString)
  {
    this.traderID = paramString;
  }
  
  public String getConditionCmtyID()
  {
    return this.conditionCmtyID;
  }
  
  public void setConditionCmtyID(String paramString)
  {
    this.conditionCmtyID = paramString;
  }
  
  public Date getEndDate()
  {
    return this.endDate;
  }
  
  public void setEndDate(Date paramDate)
  {
    this.endDate = paramDate;
  }
  
  public int hashCode()
  {
    int i = 1;
    i = 31 * i + (this.orderNo == null ? 0 : this.orderNo.hashCode());
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    if (getClass() != paramObject.getClass()) {
      return false;
    }
    ConditionOrder localConditionOrder = (ConditionOrder)paramObject;
    if (this.orderNo == null)
    {
      if (localConditionOrder.orderNo != null) {
        return false;
      }
    }
    else if (!this.orderNo.equals(localConditionOrder.orderNo)) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return "OrderNo=" + this.orderNo + ",ConditionPrice=" + this.conditionPrice + ",ConditionType=" + this.conditionType + ",ConditionCommtyId=" + this.conditionCmtyID + ",Operation:" + this.conditionOperation;
  }
}
