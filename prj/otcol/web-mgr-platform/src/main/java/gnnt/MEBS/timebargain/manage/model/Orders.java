package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Orders
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049816L;
  private Long A_OrderNo;
  private Long M_OrderNo;
  private Long A_OrderNo_W;
  private String Uni_Cmdty_Code;
  private String CustomerID;
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
  private Short closeAlgr;
  private String commodityID;
  private String firmID;
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String paramString)
  {
    this.firmID = paramString;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Orders)) {
      return false;
    }
    Orders localOrders = (Orders)paramObject;
    return this.A_OrderNo != null ? this.A_OrderNo.equals(localOrders.A_OrderNo) : localOrders.A_OrderNo == null;
  }
  
  public int hashCode()
  {
    return this.A_OrderNo != null ? this.A_OrderNo.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public Long getA_OrderNo()
  {
    return this.A_OrderNo;
  }
  
  public void setA_OrderNo(Long paramLong)
  {
    this.A_OrderNo = paramLong;
  }
  
  public Long getA_OrderNo_W()
  {
    return this.A_OrderNo_W;
  }
  
  public void setA_OrderNo_W(Long paramLong)
  {
    this.A_OrderNo_W = paramLong;
  }
  
  public Short getBS_Flag()
  {
    return this.BS_Flag;
  }
  
  public void setBS_Flag(Short paramShort)
  {
    this.BS_Flag = paramShort;
  }
  
  public Short getCloseMode()
  {
    return this.CloseMode;
  }
  
  public void setCloseMode(Short paramShort)
  {
    this.CloseMode = paramShort;
  }
  
  public String getCustomerID()
  {
    return this.CustomerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.CustomerID = paramString;
  }
  
  public Short getFailCode()
  {
    return this.FailCode;
  }
  
  public void setFailCode(Short paramShort)
  {
    this.FailCode = paramShort;
  }
  
  public Double getFrozenFunds()
  {
    return this.FrozenFunds;
  }
  
  public void setFrozenFunds(Double paramDouble)
  {
    this.FrozenFunds = paramDouble;
  }
  
  public Long getM_OrderNo()
  {
    return this.M_OrderNo;
  }
  
  public void setM_OrderNo(Long paramLong)
  {
    this.M_OrderNo = paramLong;
  }
  
  public String getOrdererIP()
  {
    return this.OrdererIP;
  }
  
  public void setOrdererIP(String paramString)
  {
    this.OrdererIP = paramString;
  }
  
  public Date getOrderTime()
  {
    return this.OrderTime;
  }
  
  public void setOrderTime(Date paramDate)
  {
    this.OrderTime = paramDate;
  }
  
  public Short getOrderType()
  {
    return this.OrderType;
  }
  
  public void setOrderType(Short paramShort)
  {
    this.OrderType = paramShort;
  }
  
  public Double getPrice()
  {
    return this.Price;
  }
  
  public void setPrice(Double paramDouble)
  {
    this.Price = paramDouble;
  }
  
  public Long getQuantity()
  {
    return this.Quantity;
  }
  
  public void setQuantity(Long paramLong)
  {
    this.Quantity = paramLong;
  }
  
  public String getSignature()
  {
    return this.Signature;
  }
  
  public void setSignature(String paramString)
  {
    this.Signature = paramString;
  }
  
  public Double getSpecPrice()
  {
    return this.SpecPrice;
  }
  
  public void setSpecPrice(Double paramDouble)
  {
    this.SpecPrice = paramDouble;
  }
  
  public Short getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(Short paramShort)
  {
    this.Status = paramShort;
  }
  
  public Short getTimeFlag()
  {
    return this.TimeFlag;
  }
  
  public void setTimeFlag(Short paramShort)
  {
    this.TimeFlag = paramShort;
  }
  
  public Long getTradeQty()
  {
    return this.TradeQty;
  }
  
  public void setTradeQty(Long paramLong)
  {
    this.TradeQty = paramLong;
  }
  
  public String getTraderID()
  {
    return this.TraderID;
  }
  
  public void setTraderID(String paramString)
  {
    this.TraderID = paramString;
  }
  
  public Double getUnfrozenFunds()
  {
    return this.UnfrozenFunds;
  }
  
  public void setUnfrozenFunds(Double paramDouble)
  {
    this.UnfrozenFunds = paramDouble;
  }
  
  public String getUni_Cmdty_Code()
  {
    return this.Uni_Cmdty_Code;
  }
  
  public void setUni_Cmdty_Code(String paramString)
  {
    this.Uni_Cmdty_Code = paramString;
  }
  
  public Date getWithdrawTime()
  {
    return this.WithdrawTime;
  }
  
  public void setWithdrawTime(Date paramDate)
  {
    this.WithdrawTime = paramDate;
  }
  
  public Short getCloseAlgr()
  {
    return this.closeAlgr;
  }
  
  public void setCloseAlgr(Short paramShort)
  {
    this.closeAlgr = paramShort;
  }
}
