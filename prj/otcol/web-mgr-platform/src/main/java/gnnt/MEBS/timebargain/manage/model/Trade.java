package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Trade
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049819L;
  private Long A_TradeNo;
  private Long M_TradeNo;
  private Long A_OrderNo;
  private Long A_TradeNo_Closed;
  private Date TradeTime;
  private String CustomerID;
  private String Uni_Cmdty_Code;
  private Short BS_Flag;
  private Short OrderType;
  private Double Price;
  private Long Quantity;
  private Double Close_PL;
  private Double TradeFee;
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Trade)) {
      return false;
    }
    Trade localTrade = (Trade)paramObject;
    return this.A_TradeNo != null ? this.A_TradeNo.equals(localTrade.A_TradeNo) : localTrade.A_TradeNo == null;
  }
  
  public int hashCode()
  {
    return this.A_TradeNo != null ? this.A_TradeNo.hashCode() : 0;
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
  
  public Long getA_TradeNo()
  {
    return this.A_TradeNo;
  }
  
  public void setA_TradeNo(Long paramLong)
  {
    this.A_TradeNo = paramLong;
  }
  
  public Long getA_TradeNo_Closed()
  {
    return this.A_TradeNo_Closed;
  }
  
  public void setA_TradeNo_Closed(Long paramLong)
  {
    this.A_TradeNo_Closed = paramLong;
  }
  
  public Short getBS_Flag()
  {
    return this.BS_Flag;
  }
  
  public void setBS_Flag(Short paramShort)
  {
    this.BS_Flag = paramShort;
  }
  
  public Double getClose_PL()
  {
    return this.Close_PL;
  }
  
  public void setClose_PL(Double paramDouble)
  {
    this.Close_PL = paramDouble;
  }
  
  public String getCustomerID()
  {
    return this.CustomerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.CustomerID = paramString;
  }
  
  public Long getM_TradeNo()
  {
    return this.M_TradeNo;
  }
  
  public void setM_TradeNo(Long paramLong)
  {
    this.M_TradeNo = paramLong;
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
  
  public Double getTradeFee()
  {
    return this.TradeFee;
  }
  
  public void setTradeFee(Double paramDouble)
  {
    this.TradeFee = paramDouble;
  }
  
  public Date getTradeTime()
  {
    return this.TradeTime;
  }
  
  public void setTradeTime(Date paramDate)
  {
    this.TradeTime = paramDate;
  }
  
  public String getUni_Cmdty_Code()
  {
    return this.Uni_Cmdty_Code;
  }
  
  public void setUni_Cmdty_Code(String paramString)
  {
    this.Uni_Cmdty_Code = paramString;
  }
}
