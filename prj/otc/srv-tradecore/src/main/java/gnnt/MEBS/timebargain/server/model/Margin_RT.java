package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Margin_RT
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049313L;
  private String commodityID;
  private String firmID;
  private short marginAlgr;
  public static final short MARGINALGR_PERCENT = 1;
  public static final short MARGINALGR_FIXVALUE = 2;
  private double tradeMargin;
  private double settleMargin;
  private double holidayMargin;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  public short getMarginAlgr()
  {
    return this.marginAlgr;
  }
  
  public void setMarginAlgr(short marginAlgr)
  {
    this.marginAlgr = marginAlgr;
  }
  
  public double getTradeMargin()
  {
    return this.tradeMargin;
  }
  
  public void setTradeMargin(double tradeMargin)
  {
    this.tradeMargin = tradeMargin;
  }
  
  public double getSettleMargin()
  {
    return this.settleMargin;
  }
  
  public void setSettleMargin(double settleMargin)
  {
    this.settleMargin = settleMargin;
  }
  
  public double getHolidayMargin()
  {
    return this.holidayMargin;
  }
  
  public void setHolidayMargin(double holidayMargin)
  {
    this.holidayMargin = holidayMargin;
  }
}
