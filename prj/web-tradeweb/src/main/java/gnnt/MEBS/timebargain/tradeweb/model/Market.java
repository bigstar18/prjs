package gnnt.MEBS.timebargain.tradeweb.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Market
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049817L;
  private String marketCode;
  private String firmID;
  private String marketName;
  private Short status;
  private Short marginMode;
  private Short feeMode;
  private Double marginRate;
  private Double feeRate;
  private Double spreadUpLmt;
  private Double spreadDownLmt;
  private Double minPriceMove;
  private String customerID;
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Market)) {
      return false;
    }
    Market localMarket = (Market)paramObject;
    return this.marketCode != null ? this.marketCode.equals(localMarket.marketCode) : localMarket.marketCode == null;
  }
  
  public int hashCode()
  {
    return this.marketCode != null ? this.marketCode.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public Short getFeeMode()
  {
    return this.feeMode;
  }
  
  public void setFeeMode(Short paramShort)
  {
    this.feeMode = paramShort;
  }
  
  public Double getFeeRate()
  {
    return this.feeRate;
  }
  
  public void setFeeRate(Double paramDouble)
  {
    this.feeRate = paramDouble;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String paramString)
  {
    this.firmID = paramString;
  }
  
  public Short getMarginMode()
  {
    return this.marginMode;
  }
  
  public void setMarginMode(Short paramShort)
  {
    this.marginMode = paramShort;
  }
  
  public Double getMarginRate()
  {
    return this.marginRate;
  }
  
  public void setMarginRate(Double paramDouble)
  {
    this.marginRate = paramDouble;
  }
  
  public String getMarketCode()
  {
    return this.marketCode;
  }
  
  public void setMarketCode(String paramString)
  {
    this.marketCode = paramString;
  }
  
  public String getMarketName()
  {
    return this.marketName;
  }
  
  public void setMarketName(String paramString)
  {
    this.marketName = paramString;
  }
  
  public Double getMinPriceMove()
  {
    return this.minPriceMove;
  }
  
  public void setMinPriceMove(Double paramDouble)
  {
    this.minPriceMove = paramDouble;
  }
  
  public Double getSpreadDownLmt()
  {
    return this.spreadDownLmt;
  }
  
  public void setSpreadDownLmt(Double paramDouble)
  {
    this.spreadDownLmt = paramDouble;
  }
  
  public Double getSpreadUpLmt()
  {
    return this.spreadUpLmt;
  }
  
  public void setSpreadUpLmt(Double paramDouble)
  {
    this.spreadUpLmt = paramDouble;
  }
  
  public Short getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Short paramShort)
  {
    this.status = paramShort;
  }
}
