package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class MarketOrder
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049927L;
  private String traderID;
  private String firmID;
  private String commodityID;
  private char OC_Flag;
  private Short buyOrSell;
  private Double price;
  private Long quantity;
  private Short closeMode;
  private Long specHoldNo;
  private String otherFirmID;
  private Double orderPoint;
  private String consignerID;
  private String consignFirmID;
  private boolean isslippoint;
  private Long delayTime;
  
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
  
  public char getOC_Flag()
  {
    return this.OC_Flag;
  }
  
  public void setOC_Flag(char flag)
  {
    this.OC_Flag = flag;
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
  
  public Short getCloseMode()
  {
    return this.closeMode;
  }
  
  public void setCloseMode(Short closeMode)
  {
    this.closeMode = closeMode;
  }
  
  public Long getSpecHoldNo()
  {
    return this.specHoldNo;
  }
  
  public void setSpecHoldNo(Long specHoldNo)
  {
    this.specHoldNo = specHoldNo;
  }
  
  public String getOtherFirmID()
  {
    return this.otherFirmID;
  }
  
  public void setOtherFirmID(String otherFirmID)
  {
    this.otherFirmID = otherFirmID;
  }
  
  public Double getOrderPoint()
  {
    return this.orderPoint;
  }
  
  public void setOrderPoint(Double orderPoint)
  {
    this.orderPoint = orderPoint;
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
  
  public void setDelayTime(Long delayTime)
  {
    this.delayTime = delayTime;
  }
  
  public Long getDelayTime()
  {
    return this.delayTime;
  }
  
  public void setIsslippoint(boolean isslippoint)
  {
    this.isslippoint = isslippoint;
  }
  
  public boolean isIsslippoint()
  {
    return this.isslippoint;
  }
}
