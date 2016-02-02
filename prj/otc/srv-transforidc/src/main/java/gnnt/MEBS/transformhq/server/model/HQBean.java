package gnnt.MEBS.transformhq.server.model;

import java.util.Date;

public class HQBean
{
  private String commodityID;
  private String buyPrice;
  private String sellPrice;
  private String formerPrice;
  private String price;
  private String clientContents;
  private String serverContents;
  private Date hqDate;
  private String hqBeanInetAddress;
  private String dateType;
  private long priceTime;
  
  public HQBean() {}
  
  public HQBean(String commodityID, String buyPrice, String sellPrice, String formerPrice, String price, String clientContents, String serverContents, Date hqDate, String dateType)
  {
    this.commodityID = commodityID;
    this.buyPrice = buyPrice;
    this.sellPrice = sellPrice;
    this.formerPrice = formerPrice;
    this.price = price;
    this.clientContents = clientContents;
    this.serverContents = serverContents;
    this.hqDate = hqDate;
    this.dateType = dateType;
  }
  
  public String getPrice()
  {
    return this.price;
  }
  
  public void setPrice(String price)
  {
    this.price = price;
  }
  
  public String getClientContents()
  {
    return this.clientContents;
  }
  
  public void setClientContents(String clientContents)
  {
    this.clientContents = clientContents;
  }
  
  public String getServerContents()
  {
    return this.serverContents;
  }
  
  public void setServerContents(String serverContents)
  {
    this.serverContents = serverContents;
  }
  
  public String getBuyPrice()
  {
    return this.buyPrice;
  }
  
  public void setBuyPrice(String buyPrice)
  {
    this.buyPrice = buyPrice;
  }
  
  public String getSellPrice()
  {
    return this.sellPrice;
  }
  
  public void setSellPrice(String sellPrice)
  {
    this.sellPrice = sellPrice;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public Date getHqDate()
  {
    return this.hqDate;
  }
  
  public void setHqDate(Date hqDate)
  {
    this.hqDate = hqDate;
  }
  
  public String getDateType()
  {
    return this.dateType;
  }
  
  public void setDateType(String dateType)
  {
    this.dateType = dateType;
  }
  
  public String getFormerPrice()
  {
    return this.formerPrice;
  }
  
  public void setFormerPrice(String formerPrice)
  {
    this.formerPrice = formerPrice;
  }
  
  public void setHqBeanInetAddress(String hqBeanInetAddress)
  {
    this.hqBeanInetAddress = hqBeanInetAddress;
  }
  
  public String getHqBeanInetAddress()
  {
    return this.hqBeanInetAddress;
  }
  
  public void setPriceTime(long priceTime)
  {
    this.priceTime = priceTime;
  }
  
  public long getPriceTime()
  {
    return this.priceTime;
  }
  
  public String toString()
  {
    return this.priceTime + ":" + getCommodityID() + ":" + getPrice() + " from: " + getHqBeanInetAddress() + " 处理时间:" + (System.currentTimeMillis() - this.priceTime);
  }
}
