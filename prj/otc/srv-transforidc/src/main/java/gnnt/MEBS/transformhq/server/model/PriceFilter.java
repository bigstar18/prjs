package gnnt.MEBS.transformhq.server.model;

import java.util.LinkedList;

public class PriceFilter
{
  private String commodityID;
  private double comparePrice;
  private double normalPrice;
  private LinkedList<HQBean> glitchPLst;
  private boolean isExist;
  private double priceDiff;
  
  public PriceFilter()
  {
    this.isExist = false;
    this.glitchPLst = new LinkedList();
  }
  
  public PriceFilter(String commodityID, double comparePrice, double normalPrice, LinkedList<HQBean> glitchPLst, boolean isExist, double priceDiff)
  {
    this.commodityID = commodityID;
    this.comparePrice = comparePrice;
    this.normalPrice = normalPrice;
    this.glitchPLst = glitchPLst;
    this.isExist = isExist;
    this.priceDiff = priceDiff;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public double getComparePrice()
  {
    return this.comparePrice;
  }
  
  public void setComparePrice(double comparePrice)
  {
    this.comparePrice = comparePrice;
  }
  
  public double getNormalPrice()
  {
    return this.normalPrice;
  }
  
  public void setNormalPrice(double normalPrice)
  {
    this.normalPrice = normalPrice;
  }
  
  public LinkedList<HQBean> getGlitchPLst()
  {
    return this.glitchPLst;
  }
  
  public void setGlitchPLst(LinkedList<HQBean> glitchPLst)
  {
    this.glitchPLst = glitchPLst;
  }
  
  public boolean isExist()
  {
    return this.isExist;
  }
  
  public void setExist(boolean isExist)
  {
    this.isExist = isExist;
  }
  
  public double getPriceDiff()
  {
    return this.priceDiff;
  }
  
  public void setPriceDiff(double priceDiff)
  {
    this.priceDiff = priceDiff;
  }
}
