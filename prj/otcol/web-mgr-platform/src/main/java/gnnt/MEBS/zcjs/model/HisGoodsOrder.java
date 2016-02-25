package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class HisGoodsOrder
  extends Clone
{
  private long goodsOrderId;
  private Date clearDate;
  private long goodsOrderNo;
  private long tradeCommodityMsgId;
  private String firmId;
  private String traderId;
  private double price;
  private double quantity;
  private double partBargainQuantity;
  private String businessDirection;
  private int status;
  private String isRegStock;
  private String regStockId;
  private double tradePoundage;
  private double tradeBail;
  private Date goodsOrderDate;
  private Date modifyDate;
  private long oldGoodsOrderId;
  private String isDelist;
  
  public String getIsDelist()
  {
    return this.isDelist;
  }
  
  public void setIsDelist(String paramString)
  {
    this.isDelist = paramString;
  }
  
  public Date getModifyDate()
  {
    return this.modifyDate;
  }
  
  public void setModifyDate(Date paramDate)
  {
    this.modifyDate = paramDate;
  }
  
  public long getOldGoodsOrderId()
  {
    return this.oldGoodsOrderId;
  }
  
  public void setOldGoodsOrderId(long paramLong)
  {
    this.oldGoodsOrderId = paramLong;
  }
  
  public Date getGoodsOrderDate()
  {
    return this.goodsOrderDate;
  }
  
  public void setGoodsOrderDate(Date paramDate)
  {
    this.goodsOrderDate = paramDate;
  }
  
  public long getGoodsOrderId()
  {
    return this.goodsOrderId;
  }
  
  public void setGoodsOrderId(long paramLong)
  {
    this.goodsOrderId = paramLong;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String paramString)
  {
    this.traderId = paramString;
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
  
  public double getPartBargainQuantity()
  {
    return this.partBargainQuantity;
  }
  
  public void setPartBargainQuantity(double paramDouble)
  {
    this.partBargainQuantity = paramDouble;
  }
  
  public String getBusinessDirection()
  {
    return this.businessDirection;
  }
  
  public void setBusinessDirection(String paramString)
  {
    this.businessDirection = paramString;
  }
  
  public double getTradePoundage()
  {
    return this.tradePoundage;
  }
  
  public void setTradePoundage(double paramDouble)
  {
    this.tradePoundage = paramDouble;
  }
  
  public double getTradeBail()
  {
    return this.tradeBail;
  }
  
  public void setTradeBail(double paramDouble)
  {
    this.tradeBail = paramDouble;
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
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date paramDate)
  {
    this.clearDate = paramDate;
  }
  
  public long getGoodsOrderNo()
  {
    return this.goodsOrderNo;
  }
  
  public void setGoodsOrderNo(long paramLong)
  {
    this.goodsOrderNo = paramLong;
  }
}
