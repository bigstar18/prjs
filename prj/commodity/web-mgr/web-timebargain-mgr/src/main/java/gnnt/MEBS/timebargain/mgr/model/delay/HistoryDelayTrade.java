package gnnt.MEBS.timebargain.mgr.model.delay;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class HistoryDelayTrade extends StandardModel
{
  private static final long serialVersionUID = 5372400803271758916L;
  private Integer tradeNo;
  private Date clearDate;
  private Integer orderNo;
  private Date tradeTime;
  private String customerID;
  private String commodityID;
  private Integer flag;
  private Integer delayOrderType;
  private Integer quantity;
  private Integer tradeType;
  private String firmID;

  public Integer getTradeNo()
  {
    return this.tradeNo;
  }

  public void setTradeNo(Integer tradeNo)
  {
    this.tradeNo = tradeNo;
  }

  public Date getClearDate()
  {
    return this.clearDate;
  }

  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }

  public Integer getOrderNo()
  {
    return this.orderNo;
  }

  public void setOrderNo(Integer orderNo)
  {
    this.orderNo = orderNo;
  }

  public Date getTradeTime()
  {
    return this.tradeTime;
  }

  public void setTradeTime(Date tradeTime)
  {
    this.tradeTime = tradeTime;
  }

  public String getCustomerID()
  {
    return this.customerID;
  }

  public void setCustomerID(String customerID)
  {
    this.customerID = customerID;
  }

  public String getCommodityID()
  {
    return this.commodityID;
  }

  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
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

  public Integer getQuantity()
  {
    return this.quantity;
  }

  public void setQuantity(Integer quantity)
  {
    this.quantity = quantity;
  }

  public Integer getTradeType()
  {
    return this.tradeType;
  }

  public void setTradeType(Integer tradeType)
  {
    this.tradeType = tradeType;
  }

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}