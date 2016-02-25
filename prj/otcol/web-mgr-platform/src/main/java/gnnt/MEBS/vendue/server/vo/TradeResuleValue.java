package gnnt.MEBS.vendue.server.vo;

public class TradeResuleValue
{
  private long commodityID;
  private String code;
  private String userId;
  private double price;
  private long amount;
  private long submitId;
  private long oldamount;
  
  public long getOldamount()
  {
    return this.oldamount;
  }
  
  public void setOldamount(long paramLong)
  {
    this.oldamount = paramLong;
  }
  
  public long getSubmitId()
  {
    return this.submitId;
  }
  
  public void setSubmitId(long paramLong)
  {
    this.submitId = paramLong;
  }
  
  public long getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(long paramLong)
  {
    this.amount = paramLong;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public long getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(long paramLong)
  {
    this.commodityID = paramLong;
  }
  
  public double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(double paramDouble)
  {
    this.price = paramDouble;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
}
