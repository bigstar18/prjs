package gnnt.MEBS.timebargain.tradeweb.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Trade
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049816L;
  private Long TradeNo;
  private Long OrderNo;
  private Date TradeTime;
  private Short BS_Flag;
  private Short OrderType;
  private String FirmID;
  private String CommodityID;
  private Double OpenPrice;
  private Double Price;
  private Long Quantity;
  private Double Close_PL;
  private Double TradeFee;
  private Double HoldPrice;
  private Date HoldTime;
  private Integer TradeType;
  private Long holdNO;
  private String otherFirmID;
  
  public Date getHoldTime()
  {
    return this.HoldTime;
  }
  
  public void setHoldTime(Date holdTime)
  {
    this.HoldTime = holdTime;
  }
  
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Trade)) {
      return false;
    }
    Trade m = (Trade)o;
    
    return this.TradeNo != null ? this.TradeNo.equals(m.TradeNo) : 
      m.TradeNo == null;
  }
  
  public int hashCode()
  {
    return this.TradeNo != null ? this.TradeNo.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, 
      ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public Long getOrderNo()
  {
    return this.OrderNo;
  }
  
  public void setOrderNo(Long orderNo)
  {
    this.OrderNo = orderNo;
  }
  
  public Long getTradeNo()
  {
    return this.TradeNo;
  }
  
  public void setTradeNo(Long tradeNo)
  {
    this.TradeNo = tradeNo;
  }
  
  public Short getBS_Flag()
  {
    return this.BS_Flag;
  }
  
  public void setBS_Flag(Short flag)
  {
    this.BS_Flag = flag;
  }
  
  public Double getClose_PL()
  {
    return this.Close_PL;
  }
  
  public void setClose_PL(Double close_PL)
  {
    this.Close_PL = close_PL;
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
  
  public Double getTradeFee()
  {
    return this.TradeFee;
  }
  
  public void setTradeFee(Double tradeFee)
  {
    this.TradeFee = tradeFee;
  }
  
  public Date getTradeTime()
  {
    return this.TradeTime;
  }
  
  public void setTradeTime(Date tradeTime)
  {
    this.TradeTime = tradeTime;
  }
  
  public String getCommodityID()
  {
    return this.CommodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.CommodityID = commodityID;
  }
  
  public String getFirmID()
  {
    return this.FirmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.FirmID = firmID;
  }
  
  public Double getHoldPrice()
  {
    return this.HoldPrice;
  }
  
  public void setHoldPrice(Double holdPrice)
  {
    this.HoldPrice = holdPrice;
  }
  
  public Integer getTradeType()
  {
    return this.TradeType;
  }
  
  public void setTradeType(Integer tradeType)
  {
    this.TradeType = tradeType;
  }
  
  public Long getHoldNO()
  {
    return this.holdNO;
  }
  
  public void setHoldNO(Long holdNO)
  {
    this.holdNO = holdNO;
  }
  
  public String getOtherFirmID()
  {
    return this.otherFirmID;
  }
  
  public void setOtherFirmID(String otherFirmID)
  {
    this.otherFirmID = otherFirmID;
  }
  
  public Double getOpenPrice()
  {
    return this.OpenPrice;
  }
  
  public void setOpenPrice(Double openPrice)
  {
    this.OpenPrice = openPrice;
  }
}
