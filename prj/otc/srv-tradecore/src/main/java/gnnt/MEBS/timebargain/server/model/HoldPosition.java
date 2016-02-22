package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.Date;

public class HoldPosition
  implements Serializable
{
  private static final long serialVersionUID = 900521129145721917L;
  private Long holdNO;
  private String firmID;
  private String traderID;
  private String consignerID;
  private Long tradeNO;
  private String commodityID;
  private short buyOrSell;
  private double openPrice;
  private double holdPrice;
  private Double stopLossPrice;
  private Double stopProfitPrice;
  private Long holdQty;
  private Long frozenQty;
  private Long openQty;
  private Date holdTime;
  private double holdMargin;
  private double floatingLoss;
  private short status;
  private Date atclearDate;
  private double delayFee;
  private String o_firmID;
  private short o_buyOrSell;
  private Date modifyTime;
  
  public Long getHoldNO()
  {
    return this.holdNO;
  }
  
  public void setHoldNO(Long holdNO)
  {
    this.holdNO = holdNO;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  public String getTraderID()
  {
    return this.traderID;
  }
  
  public void setTraderID(String traderID)
  {
    this.traderID = traderID;
  }
  
  public String getConsignerID()
  {
    return this.consignerID;
  }
  
  public void setConsignerID(String consignerID)
  {
    this.consignerID = consignerID;
  }
  
  public Long getTradeNO()
  {
    return this.tradeNO;
  }
  
  public void setTradeNO(Long tradeNO)
  {
    this.tradeNO = tradeNO;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public short getBuyOrSell()
  {
    return this.buyOrSell;
  }
  
  public void setBuyOrSell(short buyOrSell)
  {
    this.buyOrSell = buyOrSell;
  }
  
  public double getOpenPrice()
  {
    return this.openPrice;
  }
  
  public void setOpenPrice(double openPrice)
  {
    this.openPrice = openPrice;
  }
  
  public double getHoldPrice()
  {
    return this.holdPrice;
  }
  
  public void setHoldPrice(double holdPrice)
  {
    this.holdPrice = holdPrice;
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
  
  public Long getHoldQty()
  {
    return this.holdQty;
  }
  
  public void setHoldQty(Long holdQty)
  {
    this.holdQty = holdQty;
  }
  
  public Long getFrozenQty()
  {
    return this.frozenQty;
  }
  
  public void setFrozenQty(Long frozenQty)
  {
    this.frozenQty = frozenQty;
  }
  
  public Long getOpenQty()
  {
    return this.openQty;
  }
  
  public void setOpenQty(Long openQty)
  {
    this.openQty = openQty;
  }
  
  public Date getHoldTime()
  {
    return this.holdTime;
  }
  
  public void setHoldTime(Date holdTime)
  {
    this.holdTime = holdTime;
  }
  
  public double getHoldMargin()
  {
    return this.holdMargin;
  }
  
  public void setHoldMargin(double holdMargin)
  {
    this.holdMargin = holdMargin;
  }
  
  public double getFloatingLoss()
  {
    return this.floatingLoss;
  }
  
  public void setFloatingLoss(double floatingLoss)
  {
    this.floatingLoss = floatingLoss;
  }
  
  public short getStatus()
  {
    return this.status;
  }
  
  public void setStatus(short status)
  {
    this.status = status;
  }
  
  public Date getAtclearDate()
  {
    return this.atclearDate;
  }
  
  public void setAtclearDate(Date atclearDate)
  {
    this.atclearDate = atclearDate;
  }
  
  public double getDelayFee()
  {
    return this.delayFee;
  }
  
  public void setDelayFee(double delayFee)
  {
    this.delayFee = delayFee;
  }
  
  public String getO_firmID()
  {
    return this.o_firmID;
  }
  
  public void setO_firmID(String oFirmID)
  {
    this.o_firmID = oFirmID;
  }
  
  public short getO_buyOrSell()
  {
    return this.o_buyOrSell;
  }
  
  public void setO_buyOrSell(short oBuyOrSell)
  {
    this.o_buyOrSell = oBuyOrSell;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }
}
