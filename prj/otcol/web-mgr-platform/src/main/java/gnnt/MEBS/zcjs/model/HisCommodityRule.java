package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class HisCommodityRule
  extends Clone
{
  private long commodityRuleId;
  private Date clearDate;
  private long breedId;
  private String commodityRuleFirmId;
  private String commodityRuleBusinessDirection;
  private double bail;
  private int bailMode;
  private double tradePoundage;
  private int tradePoundageMode;
  private double deliveryPoundage;
  private int deliveryPoundageMode;
  private double maxPrice;
  private double minPrice;
  private int commodityRuleStatus;
  
  public long getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(long paramLong)
  {
    this.breedId = paramLong;
  }
  
  public long getCommodityRuleId()
  {
    return this.commodityRuleId;
  }
  
  public void setCommodityRuleId(long paramLong)
  {
    this.commodityRuleId = paramLong;
  }
  
  public double getBail()
  {
    return this.bail;
  }
  
  public void setBail(double paramDouble)
  {
    this.bail = paramDouble;
  }
  
  public int getBailMode()
  {
    return this.bailMode;
  }
  
  public void setBailMode(int paramInt)
  {
    this.bailMode = paramInt;
  }
  
  public double getTradePoundage()
  {
    return this.tradePoundage;
  }
  
  public void setTradePoundage(double paramDouble)
  {
    this.tradePoundage = paramDouble;
  }
  
  public int getTradePoundageMode()
  {
    return this.tradePoundageMode;
  }
  
  public void setTradePoundageMode(int paramInt)
  {
    this.tradePoundageMode = paramInt;
  }
  
  public double getDeliveryPoundage()
  {
    return this.deliveryPoundage;
  }
  
  public void setDeliveryPoundage(double paramDouble)
  {
    this.deliveryPoundage = paramDouble;
  }
  
  public int getDeliveryPoundageMode()
  {
    return this.deliveryPoundageMode;
  }
  
  public void setDeliveryPoundageMode(int paramInt)
  {
    this.deliveryPoundageMode = paramInt;
  }
  
  public double getMaxPrice()
  {
    return this.maxPrice;
  }
  
  public void setMaxPrice(double paramDouble)
  {
    this.maxPrice = paramDouble;
  }
  
  public double getMinPrice()
  {
    return this.minPrice;
  }
  
  public void setMinPrice(double paramDouble)
  {
    this.minPrice = paramDouble;
  }
  
  public int getCommodityRuleStatus()
  {
    return this.commodityRuleStatus;
  }
  
  public void setCommodityRuleStatus(int paramInt)
  {
    this.commodityRuleStatus = paramInt;
  }
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date paramDate)
  {
    this.clearDate = paramDate;
  }
  
  public String getCommodityRuleFirmId()
  {
    return this.commodityRuleFirmId;
  }
  
  public void setCommodityRuleFirmId(String paramString)
  {
    this.commodityRuleFirmId = paramString;
  }
  
  public String getCommodityRuleBusinessDirection()
  {
    return this.commodityRuleBusinessDirection;
  }
  
  public void setCommodityRuleBusinessDirection(String paramString)
  {
    this.commodityRuleBusinessDirection = paramString;
  }
}
