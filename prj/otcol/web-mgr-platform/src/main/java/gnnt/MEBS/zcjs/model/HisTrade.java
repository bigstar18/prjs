package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class HisTrade
  extends Clone
{
  private long tradeNo;
  private Date clearDate;
  private Date tradeDate;
  private Date deliveryDate;
  private double quantity;
  private double price;
  private long breedId;
  private String quality;
  private String deliveryPlace;
  private String expandProperty;
  private String note;
  private String commodityProperties;
  private String firmId_S;
  private String traderId_S;
  private double tradeBail_S;
  private double tradePoundage_S;
  private double deliveryPoundage_S;
  private long submitId_S;
  private long submitId_B;
  private String firmId_B;
  private String traderId_B;
  private double tradeBail_B;
  private double tradePoundage_B;
  private double deliveryPoundage_B;
  private String isRegstock;
  private String regStockId;
  private int status;
  
  public String getQuality()
  {
    return this.quality;
  }
  
  public void setQuality(String paramString)
  {
    this.quality = paramString;
  }
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date paramDate)
  {
    this.clearDate = paramDate;
  }
  
  public long getTradeNo()
  {
    return this.tradeNo;
  }
  
  public void setTradeNo(long paramLong)
  {
    this.tradeNo = paramLong;
  }
  
  public Date getTradeDate()
  {
    return this.tradeDate;
  }
  
  public void setTradeDate(Date paramDate)
  {
    this.tradeDate = paramDate;
  }
  
  public Date getDeliveryDate()
  {
    return this.deliveryDate;
  }
  
  public void setDeliveryDate(Date paramDate)
  {
    this.deliveryDate = paramDate;
  }
  
  public double getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(double paramDouble)
  {
    this.quantity = paramDouble;
  }
  
  public double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(double paramDouble)
  {
    this.price = paramDouble;
  }
  
  public long getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(long paramLong)
  {
    this.breedId = paramLong;
  }
  
  public String getCommodityProperties()
  {
    return this.commodityProperties;
  }
  
  public void setCommodityProperties(String paramString)
  {
    this.commodityProperties = paramString;
  }
  
  public double getTradeBail_S()
  {
    return this.tradeBail_S;
  }
  
  public void setTradeBail_S(double paramDouble)
  {
    this.tradeBail_S = paramDouble;
  }
  
  public double getTradePoundage_S()
  {
    return this.tradePoundage_S;
  }
  
  public void setTradePoundage_S(double paramDouble)
  {
    this.tradePoundage_S = paramDouble;
  }
  
  public double getDeliveryPoundage_S()
  {
    return this.deliveryPoundage_S;
  }
  
  public void setDeliveryPoundage_S(double paramDouble)
  {
    this.deliveryPoundage_S = paramDouble;
  }
  
  public long getSubmitId_S()
  {
    return this.submitId_S;
  }
  
  public void setSubmitId_S(long paramLong)
  {
    this.submitId_S = paramLong;
  }
  
  public String getFirmId_S()
  {
    return this.firmId_S;
  }
  
  public void setFirmId_S(String paramString)
  {
    this.firmId_S = paramString;
  }
  
  public String getTraderId_S()
  {
    return this.traderId_S;
  }
  
  public void setTraderId_S(String paramString)
  {
    this.traderId_S = paramString;
  }
  
  public String getFirmId_B()
  {
    return this.firmId_B;
  }
  
  public void setFirmId_B(String paramString)
  {
    this.firmId_B = paramString;
  }
  
  public String getTraderId_B()
  {
    return this.traderId_B;
  }
  
  public void setTraderId_B(String paramString)
  {
    this.traderId_B = paramString;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public double getTradeBail_B()
  {
    return this.tradeBail_B;
  }
  
  public void setTradeBail_B(double paramDouble)
  {
    this.tradeBail_B = paramDouble;
  }
  
  public double getTradePoundage_B()
  {
    return this.tradePoundage_B;
  }
  
  public void setTradePoundage_B(double paramDouble)
  {
    this.tradePoundage_B = paramDouble;
  }
  
  public double getDeliveryPoundage_B()
  {
    return this.deliveryPoundage_B;
  }
  
  public void setDeliveryPoundage_B(double paramDouble)
  {
    this.deliveryPoundage_B = paramDouble;
  }
  
  public String getIsRegstock()
  {
    return this.isRegstock;
  }
  
  public void setIsRegstock(String paramString)
  {
    this.isRegstock = paramString;
  }
  
  public String getRegStockId()
  {
    return this.regStockId;
  }
  
  public void setRegStockId(String paramString)
  {
    this.regStockId = paramString;
  }
  
  public long getSubmitId_B()
  {
    return this.submitId_B;
  }
  
  public void setSubmitId_B(long paramLong)
  {
    this.submitId_B = paramLong;
  }
  
  public String getDeliveryPlace()
  {
    return this.deliveryPlace;
  }
  
  public void setDeliveryPlace(String paramString)
  {
    this.deliveryPlace = paramString;
  }
  
  public String getExpandProperty()
  {
    return this.expandProperty;
  }
  
  public void setExpandProperty(String paramString)
  {
    this.expandProperty = paramString;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String paramString)
  {
    this.note = paramString;
  }
}
