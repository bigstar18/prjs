package gnnt.MEBS.timebargain.tradeweb.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Orders
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049814L;
  private Long A_OrderNo;
  private Long M_OrderNo;
  private Long A_OrderNo_W;
  private String CommodityID;
  private String CustomerID;
  private String FirmID;
  private String TraderID;
  private Short BS_Flag;
  private Short OrderType;
  private Short Status;
  private Short FailCode;
  private Long Quantity;
  private Double Price;
  private Short CloseMode;
  private Double SpecPrice;
  private Short TimeFlag;
  private Long TradeQty;
  private Double FrozenFunds;
  private Double UnfrozenFunds;
  private Date OrderTime;
  private Date WithdrawTime;
  private String OrdererIP;
  private String Signature;
  private String MarketCode;
  private Long lastID;
  private String updateTime;
  
  public String getUpdateTime()
  {
    return this.updateTime;
  }
  
  public void setUpdateTime(String updateTime)
  {
    this.updateTime = updateTime;
  }
  
  public String getCommodityID()
  {
    return this.CommodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.CommodityID = commodityID;
  }
  
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Orders)) {
      return false;
    }
    Orders m = (Orders)o;
    
    return this.A_OrderNo != null ? this.A_OrderNo.equals(m.A_OrderNo) : m.A_OrderNo == null;
  }
  
  public int hashCode()
  {
    return this.A_OrderNo != null ? this.A_OrderNo.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public Long getOrderNo()
  {
    return this.A_OrderNo;
  }
  
  public void setOrderNo(Long orderNo)
  {
    this.A_OrderNo = orderNo;
  }
  
  public Long getOrderNo_W()
  {
    return this.A_OrderNo_W;
  }
  
  public void setOrderNo_W(Long orderNo_W)
  {
    this.A_OrderNo_W = orderNo_W;
  }
  
  public Short getBS_Flag()
  {
    return this.BS_Flag;
  }
  
  public void setBS_Flag(Short flag)
  {
    this.BS_Flag = flag;
  }
  
  public Short getCloseMode()
  {
    return this.CloseMode;
  }
  
  public void setCloseMode(Short closeMode)
  {
    this.CloseMode = closeMode;
  }
  
  public String getCustomerID()
  {
    return this.CustomerID;
  }
  
  public void setCustomerID(String customerID)
  {
    this.CustomerID = customerID;
  }
  
  public Short getFailCode()
  {
    return this.FailCode;
  }
  
  public void setFailCode(Short failCode)
  {
    this.FailCode = failCode;
  }
  
  public Double getFrozenFunds()
  {
    return this.FrozenFunds;
  }
  
  public void setFrozenFunds(Double frozenFunds)
  {
    this.FrozenFunds = frozenFunds;
  }
  
  public Long getM_OrderNo()
  {
    return this.M_OrderNo;
  }
  
  public void setM_OrderNo(Long orderNo)
  {
    this.M_OrderNo = orderNo;
  }
  
  public String getOrdererIP()
  {
    return this.OrdererIP;
  }
  
  public void setOrdererIP(String ordererIP)
  {
    this.OrdererIP = ordererIP;
  }
  
  public Date getOrderTime()
  {
    return this.OrderTime;
  }
  
  public void setOrderTime(Date orderTime)
  {
    this.OrderTime = orderTime;
  }
  
  public Short getOrderType()
  {
    return this.OrderType;
  }
  
  public void setOrderType(Short orderType)
  {
    this.OrderType = orderType;
  }
  
  public Double getPrice()
  {
    return this.Price;
  }
  
  public void setPrice(Double price)
  {
    this.Price = price;
  }
  
  public Long getQuantity()
  {
    return this.Quantity;
  }
  
  public void setQuantity(Long quantity)
  {
    this.Quantity = quantity;
  }
  
  public String getSignature()
  {
    return this.Signature;
  }
  
  public void setSignature(String signature)
  {
    this.Signature = signature;
  }
  
  public Double getSpecPrice()
  {
    return this.SpecPrice;
  }
  
  public void setSpecPrice(Double specPrice)
  {
    this.SpecPrice = specPrice;
  }
  
  public Short getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(Short status)
  {
    this.Status = status;
  }
  
  public Short getTimeFlag()
  {
    return this.TimeFlag;
  }
  
  public void setTimeFlag(Short timeFlag)
  {
    this.TimeFlag = timeFlag;
  }
  
  public Long getTradeQty()
  {
    return this.TradeQty;
  }
  
  public void setTradeQty(Long tradeQty)
  {
    this.TradeQty = tradeQty;
  }
  
  public String getTraderID()
  {
    return this.TraderID;
  }
  
  public void setTraderID(String traderID)
  {
    this.TraderID = traderID;
  }
  
  public Double getUnfrozenFunds()
  {
    return this.UnfrozenFunds;
  }
  
  public void setUnfrozenFunds(Double unfrozenFunds)
  {
    this.UnfrozenFunds = unfrozenFunds;
  }
  
  public Date getWithdrawTime()
  {
    return this.WithdrawTime;
  }
  
  public void setWithdrawTime(Date withdrawTime)
  {
    this.WithdrawTime = withdrawTime;
  }
  
  public String getMarketCode()
  {
    return this.MarketCode;
  }
  
  public void setMarketCode(String marketCode)
  {
    this.MarketCode = marketCode;
  }
  
  public Long getLastID()
  {
    return this.lastID;
  }
  
  public void setLastID(Long lastID)
  {
    this.lastID = lastID;
  }
  
  public String getFirmID()
  {
    return this.FirmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.FirmID = firmID;
  }
}
