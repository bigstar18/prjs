package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class DiscussPrice
  extends Clone
{
  private long discussPriceId;
  private Date modifyDate;
  private long tradeCommodityMsgId;
  private long goodsOrderId;
  private String followFirmId;
  private String followTraderId;
  private String discussPriceFirmId;
  private double discussPrice;
  private double quantity;
  private String businessDirection;
  private double tradeBail;
  private double tradePoundage;
  private int type;
  private int status;
  private String note;
  private String isRegStock;
  private String regStockId;
  private Date discussPriceDate;
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String paramString)
  {
    this.note = paramString;
  }
  
  public long getTradeCommodityMsgId()
  {
    return this.tradeCommodityMsgId;
  }
  
  public void setTradeCommodityMsgId(long paramLong)
  {
    this.tradeCommodityMsgId = paramLong;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public String getIsRegStock()
  {
    return this.isRegStock;
  }
  
  public void setIsRegStock(String paramString)
  {
    this.isRegStock = paramString;
  }
  
  public String getRegStockId()
  {
    return this.regStockId;
  }
  
  public void setRegStockId(String paramString)
  {
    this.regStockId = paramString;
  }
  
  public long getDiscussPriceId()
  {
    return this.discussPriceId;
  }
  
  public void setDiscussPriceId(long paramLong)
  {
    this.discussPriceId = paramLong;
  }
  
  public long getGoodsOrderId()
  {
    return this.goodsOrderId;
  }
  
  public void setGoodsOrderId(long paramLong)
  {
    this.goodsOrderId = paramLong;
  }
  
  public String getFollowFirmId()
  {
    return this.followFirmId;
  }
  
  public void setFollowFirmId(String paramString)
  {
    this.followFirmId = paramString;
  }
  
  public String getFollowTraderId()
  {
    return this.followTraderId;
  }
  
  public void setFollowTraderId(String paramString)
  {
    this.followTraderId = paramString;
  }
  
  public String getDiscussPriceFirmId()
  {
    return this.discussPriceFirmId;
  }
  
  public void setDiscussPriceFirmId(String paramString)
  {
    this.discussPriceFirmId = paramString;
  }
  
  public double getDiscussPrice()
  {
    return this.discussPrice;
  }
  
  public void setDiscussPrice(double paramDouble)
  {
    this.discussPrice = paramDouble;
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
  
  public double getTradeBail()
  {
    return this.tradeBail;
  }
  
  public void setTradeBail(double paramDouble)
  {
    this.tradeBail = paramDouble;
  }
  
  public double getTradePoundage()
  {
    return this.tradePoundage;
  }
  
  public void setTradePoundage(double paramDouble)
  {
    this.tradePoundage = paramDouble;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public Date getDiscussPriceDate()
  {
    return this.discussPriceDate;
  }
  
  public void setDiscussPriceDate(Date paramDate)
  {
    this.discussPriceDate = paramDate;
  }
  
  public Date getModifyDate()
  {
    return this.modifyDate;
  }
  
  public void setModifyDate(Date paramDate)
  {
    this.modifyDate = paramDate;
  }
}
