package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class HisSubmit
  extends Clone
{
  private long submitId;
  private Date ClearDate;
  private String traderId;
  private String firmId;
  private long tradeCommodityMsgId;
  private long discussPriceId;
  private double submitQuantity;
  private double submitPrice;
  private String businessDirection;
  private String isRegstock;
  private String regStockId;
  private double tradeBail;
  private double tradePoundage;
  private int status;
  private Date dailySubmitDate;
  
  public Date getDailySubmitDate()
  {
    return this.dailySubmitDate;
  }
  
  public void setDailySubmitDate(Date paramDate)
  {
    this.dailySubmitDate = paramDate;
  }
  
  public long getSubmitId()
  {
    return this.submitId;
  }
  
  public void setSubmitId(long paramLong)
  {
    this.submitId = paramLong;
  }
  
  public Date getClearDate()
  {
    return this.ClearDate;
  }
  
  public void setClearDate(Date paramDate)
  {
    this.ClearDate = paramDate;
  }
  
  public String getTraderId()
  {
    return this.traderId;
  }
  
  public void setTraderId(String paramString)
  {
    this.traderId = paramString;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public long getTradeCommodityMsgId()
  {
    return this.tradeCommodityMsgId;
  }
  
  public void setTradeCommodityMsgId(long paramLong)
  {
    this.tradeCommodityMsgId = paramLong;
  }
  
  public long getDiscussPriceId()
  {
    return this.discussPriceId;
  }
  
  public void setDiscussPriceId(long paramLong)
  {
    this.discussPriceId = paramLong;
  }
  
  public double getSubmitQuantity()
  {
    return this.submitQuantity;
  }
  
  public void setSubmitQuantity(double paramDouble)
  {
    this.submitQuantity = paramDouble;
  }
  
  public double getSubmitPrice()
  {
    return this.submitPrice;
  }
  
  public void setSubmitPrice(double paramDouble)
  {
    this.submitPrice = paramDouble;
  }
  
  public String getBusinessDirection()
  {
    return this.businessDirection;
  }
  
  public void setBusinessDirection(String paramString)
  {
    this.businessDirection = paramString;
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
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
}
