package gnnt.MEBS.timebargain.tradeweb.model;

import java.io.Serializable;
import java.util.Date;

public class CommodityDesc
  extends BaseObject
  implements Serializable
{
  private String CommodityID;
  private String Name;
  private double LastPrice;
  private double SpreadAlgr;
  private double SpreadUpLmt;
  private double SpreadDownLmt;
  private double MinPriceMove;
  private Date MarketDate;
  private Date SettleDate;
  private String Description;
  
  public String getCommodityID()
  {
    return this.CommodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.CommodityID = commodityID;
  }
  
  public String getName()
  {
    return this.Name;
  }
  
  public void setName(String name)
  {
    this.Name = name;
  }
  
  public double getLastPrice()
  {
    return this.LastPrice;
  }
  
  public void setLastPrice(double lastPrice)
  {
    this.LastPrice = lastPrice;
  }
  
  public double getSpreadAlgr()
  {
    return this.SpreadAlgr;
  }
  
  public void setSpreadAlgr(double spreadAlgr)
  {
    this.SpreadAlgr = spreadAlgr;
  }
  
  public double getSpreadUpLmt()
  {
    return this.SpreadUpLmt;
  }
  
  public void setSpreadUpLmt(double spreadUpLmt)
  {
    this.SpreadUpLmt = spreadUpLmt;
  }
  
  public double getSpreadDownLmt()
  {
    return this.SpreadDownLmt;
  }
  
  public void setSpreadDownLmt(double spreadDownLmt)
  {
    this.SpreadDownLmt = spreadDownLmt;
  }
  
  public double getMinPriceMove()
  {
    return this.MinPriceMove;
  }
  
  public void setMinPriceMove(double minPriceMove)
  {
    this.MinPriceMove = minPriceMove;
  }
  
  public Date getMarketDate()
  {
    return this.MarketDate;
  }
  
  public void setMarketDate(Date marketDate)
  {
    this.MarketDate = marketDate;
  }
  
  public Date getSettleDate()
  {
    return this.SettleDate;
  }
  
  public void setSettleDate(Date settleDate)
  {
    this.SettleDate = settleDate;
  }
  
  public String getDescription()
  {
    return this.Description;
  }
  
  public void setDescription(String description)
  {
    this.Description = description;
  }
  
  public boolean equals(Object o)
  {
    return false;
  }
  
  public int hashCode()
  {
    return 0;
  }
  
  public String toString()
  {
    return null;
  }
}
