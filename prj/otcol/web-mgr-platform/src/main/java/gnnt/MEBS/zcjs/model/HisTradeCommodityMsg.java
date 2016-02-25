package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class HisTradeCommodityMsg
  extends Clone
{
  private long tradeCommodityMsgId;
  private Date clearDate;
  private long breedId;
  private String commodityProperties;
  private String commodityName;
  private int deliveryDay;
  private String deliveryPlace;
  private String expandProperty;
  private String note;
  private String quality;
  private double unitVolume;
  private int effectiveDays;
  private String uploadingName;
  protected byte[] uploading;
  
  public String getUploadingName()
  {
    return this.uploadingName;
  }
  
  public void setUploadingName(String paramString)
  {
    this.uploadingName = paramString;
  }
  
  public byte[] getUploading()
  {
    return this.uploading;
  }
  
  public void addUploading(byte[] paramArrayOfByte)
  {
    this.uploading = paramArrayOfByte;
  }
  
  public int getEffectiveDays()
  {
    return this.effectiveDays;
  }
  
  public void setEffectiveDays(int paramInt)
  {
    this.effectiveDays = paramInt;
  }
  
  public double getUnitVolume()
  {
    return this.unitVolume;
  }
  
  public void setUnitVolume(double paramDouble)
  {
    this.unitVolume = paramDouble;
  }
  
  public long getTradeCommodityMsgId()
  {
    return this.tradeCommodityMsgId;
  }
  
  public void setTradeCommodityMsgId(long paramLong)
  {
    this.tradeCommodityMsgId = paramLong;
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
  
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String paramString)
  {
    this.commodityName = paramString;
  }
  
  public int getDeliveryDay()
  {
    return this.deliveryDay;
  }
  
  public void setDeliveryDay(int paramInt)
  {
    this.deliveryDay = paramInt;
  }
  
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
  
  public void setUploading(byte[] paramArrayOfByte)
  {
    this.uploading = paramArrayOfByte;
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
