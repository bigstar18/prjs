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
  
  public void setCustomerID(String customerID)
  {
    this.customerID = customerID;
  }
  
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Market)) {
      return false;
    }
    Market m = (Market)o;
    
    return this.marketCode != null ? this.marketCode.equals(m.marketCode) : m.marketCode == null;
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
  
  public void setFeeMode(Short feeMode)
  {
    this.feeMode = feeMode;
  }
  
  public Double getFeeRate()
  {
    return this.feeRate;
  }
  
  public void setFeeRate(Double feeRate)
  {
    this.feeRate = feeRate;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  public Short getMarginMode()
  {
    return this.marginMode;
  }
  
  public void setMarginMode(Short marginMode)
  {
    this.marginMode = marginMode;
  }
  
  public Double getMarginRate()
  {
    return this.marginRate;
  }
  
  public void setMarginRate(Double marginRate)
  {
    this.marginRate = marginRate;
  }
  
  public String getMarketCode()
  {
    return this.marketCode;
  }
  
  public void setMarketCode(String marketCode)
  {
    this.marketCode = marketCode;
  }
  
  public String getMarketName()
  {
    return this.marketName;
  }
  
  public void setMarketName(String marketName)
  {
    this.marketName = marketName;
  }
  
  public Double getMinPriceMove()
  {
    return this.minPriceMove;
  }
  
  public void setMinPriceMove(Double minPriceMove)
  {
    this.minPriceMove = minPriceMove;
  }
  
  public Double getSpreadDownLmt()
  {
    return this.spreadDownLmt;
  }
  
  public void setSpreadDownLmt(Double spreadDownLmt)
  {
    this.spreadDownLmt = spreadDownLmt;
  }
  
  public Double getSpreadUpLmt()
  {
    return this.spreadUpLmt;
  }
  
  public void setSpreadUpLmt(Double spreadUpLmt)
  {
    this.spreadUpLmt = spreadUpLmt;
  }
  
  public Short getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Short status)
  {
    this.status = status;
  }
}
