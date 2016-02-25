package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class TradeMsgFeedback
  extends Clone
{
  private long tradeMsgFeedbackId;
  private long goodsOrderNo;
  private long tradeCommodityMsgId;
  private String firmId;
  private String BehaviourFirmId;
  private long discussPriceId;
  private double price;
  private double quantity;
  private String businessDirection;
  private int type;
  private Date occurTime;
  private String isRead;
  private String traderId;
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String paramString)
  {
    this.traderId = paramString;
  }
  
  public long getTradeMsgFeedbackId()
  {
    return this.tradeMsgFeedbackId;
  }
  
  public void setTradeMsgFeedbackId(long paramLong)
  {
    this.tradeMsgFeedbackId = paramLong;
  }
  
  public long getGoodsOrderNo()
  {
    return this.goodsOrderNo;
  }
  
  public void setGoodsOrderNo(long paramLong)
  {
    this.goodsOrderNo = paramLong;
  }
  
  public long getTradeCommodityMsgId()
  {
    return this.tradeCommodityMsgId;
  }
  
  public void setTradeCommodityMsgId(long paramLong)
  {
    this.tradeCommodityMsgId = paramLong;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public long getDiscussPriceId()
  {
    return this.discussPriceId;
  }
  
  public void setDiscussPriceId(long paramLong)
  {
    this.discussPriceId = paramLong;
  }
  
  public double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(double paramDouble)
  {
    this.price = paramDouble;
  }
  
  public double getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(double paramDouble)
  {
    this.quantity = paramDouble;
  }
  
  public String getBusinessDirection()
  {
    return this.businessDirection;
  }
  
  public void setBusinessDirection(String paramString)
  {
    this.businessDirection = paramString;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public Date getOccurTime()
  {
    return this.occurTime;
  }
  
  public void setOccurTime(Date paramDate)
  {
    this.occurTime = paramDate;
  }
  
  public String getIsRead()
  {
    return this.isRead;
  }
  
  public void setIsRead(String paramString)
  {
    this.isRead = paramString;
  }
  
  public String getBehaviourFirmId()
  {
    return this.BehaviourFirmId;
  }
  
  public void setBehaviourFirmId(String paramString)
  {
    this.BehaviourFirmId = paramString;
  }
}
