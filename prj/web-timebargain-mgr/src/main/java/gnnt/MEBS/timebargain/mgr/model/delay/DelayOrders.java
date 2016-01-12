package gnnt.MEBS.timebargain.mgr.model.delay;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class DelayOrders extends StandardModel
{
  private static final long serialVersionUID = 5896789208278377968L;
  private Integer orderNo;
  private String commodityID;
  private String customerID;
  private String traderID;
  private Integer flag;
  private Integer delayOrderType;
  private Integer status;
  private Integer withdrawType;
  private Integer quantity;
  private Double price;
  private Integer tradeQty;
  private Double frozenFunds;
  private Double unfrozenFunds;
  private Date orderTime;
  private Date withdrawTime;
  private String ordererIP;
  private String signature;
  private String firmID;
  private String consignerID;
  private String withdrawerID;

  public Integer getOrderNo()
  {
    return this.orderNo;
  }

  public void setOrderNo(Integer orderNo)
  {
    this.orderNo = orderNo;
  }

  public String getCommodityID()
  {
    return this.commodityID;
  }

  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }

  public String getCustomerID()
  {
    return this.customerID;
  }

  public void setCustomerID(String customerID)
  {
    this.customerID = customerID;
  }

  public String getTraderID()
  {
    return this.traderID;
  }

  public void setTraderID(String traderID)
  {
    this.traderID = traderID;
  }

  public Integer getFlag()
  {
    return this.flag;
  }

  public void setFlag(Integer flag)
  {
    this.flag = flag;
  }

  public Integer getDelayOrderType()
  {
    return this.delayOrderType;
  }

  public void setDelayOrderType(Integer delayOrderType)
  {
    this.delayOrderType = delayOrderType;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public Integer getWithdrawType()
  {
    return this.withdrawType;
  }

  public void setWithdrawType(Integer withdrawType)
  {
    this.withdrawType = withdrawType;
  }

  public Integer getQuantity()
  {
    return this.quantity;
  }

  public void setQuantity(Integer quantity)
  {
    this.quantity = quantity;
  }

  public Double getPrice()
  {
    return this.price;
  }

  public void setPrice(Double price)
  {
    this.price = price;
  }

  public Integer getTradeQty()
  {
    return this.tradeQty;
  }

  public void setTradeQty(Integer tradeQty)
  {
    this.tradeQty = tradeQty;
  }

  public Double getFrozenFunds()
  {
    return this.frozenFunds;
  }

  public void setFrozenFunds(Double frozenFunds)
  {
    this.frozenFunds = frozenFunds;
  }

  public Double getUnfrozenFunds()
  {
    return this.unfrozenFunds;
  }

  public void setUnfrozenFunds(Double unfrozenFunds)
  {
    this.unfrozenFunds = unfrozenFunds;
  }

  public Date getOrderTime()
  {
    return this.orderTime;
  }

  public void setOrderTime(Date orderTime)
  {
    this.orderTime = orderTime;
  }

  public Date getWithdrawTime()
  {
    return this.withdrawTime;
  }

  public void setWithdrawTime(Date withdrawTime)
  {
    this.withdrawTime = withdrawTime;
  }

  public String getOrdererIP()
  {
    return this.ordererIP;
  }

  public void setOrdererIP(String ordererIP)
  {
    this.ordererIP = ordererIP;
  }

  public String getSignature()
  {
    return this.signature;
  }

  public void setSignature(String signature)
  {
    this.signature = signature;
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

  public String getWithdrawerID()
  {
    return this.withdrawerID;
  }

  public void setWithdrawerID(String withdrawerID)
  {
    this.withdrawerID = withdrawerID;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}