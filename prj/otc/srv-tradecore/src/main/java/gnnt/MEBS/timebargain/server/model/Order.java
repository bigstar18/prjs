package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Order
  implements Serializable
{
  private static final long serialVersionUID = 1690197650654049814L;
  private Long orderNo;
  private String firmID;
  private String consignerID;
  private String consignFirmID;
  private String commodityID;
  private Short orderType;
  public static final short ORDER_TYPE_MARKET = 1;
  public static final short ORDER_TYPE_LIMIT = 2;
  private char OC_Flag;
  public static final char OC_FLAG_OPEN = 'O';
  public static final char OC_FLAG_CLOSE = 'C';
  private Short buyOrSell;
  public static final short ORDER_BUY = 1;
  public static final short ORDER_SELL = 2;
  private Double price;
  private Double tradePrice;
  private Long quantity;
  private Short closeMode;
  public static final short CLOSEMODE_NOTSPEC = 1;
  public static final short CLOSEMODE_SPECHOLDNO = 2;
  private Long withdrawID;
  private String traderID;
  private Date orderTime;
  private Double margin;
  private Double fee;
  private Short status;
  public static final short STATUS_NORMAL = 1;
  public static final short STATUS_ALL = 2;
  public static final short STATUS_CANCELED = 3;
  private Short withdrawType;
  public static final short WITHDRAWTYPE_ORDER = 1;
  public static final short WITHDRAWTYPE_AUTO = 2;
  public static final short WITHDRAWTYPE_CLOSEAUTO = 3;
  public static final short WITHDRAWTYPE_HOLDZERO = 4;
  private String withdrawerID;
  private Long holdNo;
  private Double orderPoint;
  private Double stopLossPrice;
  private Double stopProfitPrice;
  private short stopPLFlag;
  public static final short STOPPL_L = 1;
  public static final short STOPPL_P = 2;
  private String otherFirmID;
  private boolean isslipPoint;
  private Long delayTradeTime;
  private double relatedPrice = 0.0D;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, 
      ToStringStyle.DEFAULT_STYLE);
  }
  
  public Long getOrderNo()
  {
    return this.orderNo;
  }
  
  public void setOrderNo(Long orderNo)
  {
    this.orderNo = orderNo;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
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
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public Short getOrderType()
  {
    return this.orderType;
  }
  
  public void setOrderType(Short orderType)
  {
    this.orderType = orderType;
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
  
  public Double getTradePrice()
  {
    return this.tradePrice;
  }
  
  public void setTradePrice(Double tradePrice)
  {
    this.tradePrice = tradePrice;
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
  
  public Long getWithdrawID()
  {
    return this.withdrawID;
  }
  
  public void setWithdrawID(Long withdrawID)
  {
    this.withdrawID = withdrawID;
  }
  
  public String getTraderID()
  {
    return this.traderID;
  }
  
  public void setTraderID(String traderID)
  {
    this.traderID = traderID;
  }
  
  public Date getOrderTime()
  {
    return this.orderTime;
  }
  
  public void setOrderTime(Date orderTime)
  {
    this.orderTime = orderTime;
  }
  
  public Double getMargin()
  {
    return this.margin;
  }
  
  public void setMargin(Double margin)
  {
    this.margin = margin;
  }
  
  public Double getFee()
  {
    return this.fee;
  }
  
  public void setFee(Double fee)
  {
    this.fee = fee;
  }
  
  public Short getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Short status)
  {
    this.status = status;
  }
  
  public Short getWithdrawType()
  {
    return this.withdrawType;
  }
  
  public void setWithdrawType(Short withdrawType)
  {
    this.withdrawType = withdrawType;
  }
  
  public String getWithdrawerID()
  {
    return this.withdrawerID;
  }
  
  public void setWithdrawerID(String withdrawerID)
  {
    this.withdrawerID = withdrawerID;
  }
  
  public Long getHoldNo()
  {
    return this.holdNo;
  }
  
  public void setHoldNo(Long holdNo)
  {
    this.holdNo = holdNo;
  }
  
  public Double getOrderPoint()
  {
    return this.orderPoint;
  }
  
  public void setOrderPoint(Double orderPoint)
  {
    this.orderPoint = orderPoint;
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
  
  public String getOtherFirmID()
  {
    return this.otherFirmID;
  }
  
  public void setOtherFirmID(String otherFirmID)
  {
    this.otherFirmID = otherFirmID;
  }
  
  public short getStopPLFlag()
  {
    return this.stopPLFlag;
  }
  
  public void setStopPLFlag(short stopPLFlag)
  {
    this.stopPLFlag = stopPLFlag;
  }
  
  public double getRelatedPrice()
  {
    return this.relatedPrice;
  }
  
  public void setRelatedPrice(double relatedPrice)
  {
    this.relatedPrice = relatedPrice;
  }
  
  public void setIsslipPoint(boolean isslipPoint)
  {
    this.isslipPoint = isslipPoint;
  }
  
  public boolean isIsslipPoint()
  {
    return this.isslipPoint;
  }
  
  public void setDelayTradeTime(Long delayTradeTime)
  {
    this.delayTradeTime = delayTradeTime;
  }
  
  public Long getDelayTradeTime()
  {
    return this.delayTradeTime;
  }
}
