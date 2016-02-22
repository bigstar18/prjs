package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class LimitOrder
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049826L;
  private String traderID;
  private String firmID;
  private String commodityID;
  private Short buyOrSell;
  private Double price;
  private Long quantity;
  private String otherFirmID;
  private Double stopLossPrice;
  private Double stopProfitPrice;
  private Short effective;
  private String consignerID;
  private String consignFirmID;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
  }
  
  public String getTraderID()
  {
    return this.traderID;
  }
  
  public void setTraderID(String traderID)
  {
    this.traderID = traderID;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public Short getBuyOrSell()
  {
    return this.buyOrSell;
  }
  
  public void setBuyOrSell(Short buyOrSell)
  {
    this.buyOrSell = buyOrSell;
  }
  
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(Double price)
  {
    this.price = price;
  }
  
  public Long getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(Long quantity)
  {
    this.quantity = quantity;
  }
  
  public String getOtherFirmID()
  {
    return this.otherFirmID;
  }
  
  public void setOtherFirmID(String otherFirmID)
  {
    this.otherFirmID = otherFirmID;
  }
  
  public Double getStopLossPrice()
  {
    return this.stopLossPrice;
  }
  
  public void setStopLossPrice(Double stopLossPrice)
  {
    this.stopLossPrice = stopLossPrice;
  }
  
  public Double getStopProfitPrice()
  {
    return this.stopProfitPrice;
  }
  
  public void setStopProfitPrice(Double stopProfitPrice)
  {
    this.stopProfitPrice = stopProfitPrice;
  }
  
  public Short getEffective()
  {
    return this.effective;
  }
  
  public void setEffective(Short effective)
  {
    this.effective = effective;
  }
  
  public String getConsignerID()
  {
    return this.consignerID;
  }
  
  public void setConsignerID(String consignerID)
  {
    this.consignerID = consignerID;
  }
  
  public String getConsignFirmID()
  {
    return this.consignFirmID;
  }
  
  public void setConsignFirmID(String consignFirmID)
  {
    this.consignFirmID = consignFirmID;
  }
}
