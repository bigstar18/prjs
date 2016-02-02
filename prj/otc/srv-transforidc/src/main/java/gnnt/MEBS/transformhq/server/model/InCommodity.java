package gnnt.MEBS.transformhq.server.model;

import java.util.List;

public class InCommodity
{
  private String requestCommodityId;
  private String inCommodityId;
  private long timeOut;
  private int repeatSend;
  private int randomStart;
  private int randomEnd;
  private List<String> priceType;
  private boolean quoSupplier = false;
  private String requestType;
  
  public InCommodity() {}
  
  public InCommodity(String requestCommodityId, String inCommodityId, long timeOut, int repeatSend, int randomStart, int randomEnd)
  {
    this.requestCommodityId = requestCommodityId;
    this.inCommodityId = inCommodityId;
    this.timeOut = timeOut;
    this.repeatSend = repeatSend;
    this.randomStart = randomStart;
    this.randomEnd = randomEnd;
  }
  
  public InCommodity(String requestCommodityId, String inCommodityId, long timeOut, int repeatSend, int randomStart, int randomEnd, List<String> priceType)
  {
    this.requestCommodityId = requestCommodityId;
    this.inCommodityId = inCommodityId;
    this.timeOut = timeOut;
    this.repeatSend = repeatSend;
    this.randomStart = randomStart;
    this.randomEnd = randomEnd;
    this.priceType = priceType;
  }
  
  public InCommodity(String requestCommodityId, String inCommodityId, long timeOut, int repeatSend, int randomStart, int randomEnd, List<String> priceType, boolean quoSupplier, String requestType)
  {
    this.requestCommodityId = requestCommodityId;
    this.inCommodityId = inCommodityId;
    this.timeOut = timeOut;
    this.repeatSend = repeatSend;
    this.randomStart = randomStart;
    this.randomEnd = randomEnd;
    this.priceType = priceType;
    this.quoSupplier = quoSupplier;
    this.requestType = requestType;
  }
  
  public void setRequestCommodityId(String requestCommodityId)
  {
    this.requestCommodityId = requestCommodityId;
  }
  
  public String getRequestCommodityId()
  {
    return this.requestCommodityId;
  }
  
  public void setInCommodityId(String inCommodityId)
  {
    this.inCommodityId = inCommodityId;
  }
  
  public String getInCommodityId()
  {
    return this.inCommodityId;
  }
  
  public void setTimeOut(long timeOut)
  {
    this.timeOut = timeOut;
  }
  
  public long getTimeOut()
  {
    return this.timeOut;
  }
  
  public void setRepeatSend(int repeatSend)
  {
    this.repeatSend = repeatSend;
  }
  
  public int getRepeatSend()
  {
    return this.repeatSend;
  }
  
  public void setRandomStart(int randomStart)
  {
    this.randomStart = randomStart;
  }
  
  public int getRandomStart()
  {
    return this.randomStart;
  }
  
  public void setrandomEnd(int randomEnd)
  {
    this.randomEnd = randomEnd;
  }
  
  public int getrandomEnd()
  {
    return this.randomEnd;
  }
  
  public void setPriceType(List<String> priceType)
  {
    this.priceType = priceType;
  }
  
  public List<String> getPriceType()
  {
    return this.priceType;
  }
  
  public void setQuoSupplier(boolean quoSupplier)
  {
    this.quoSupplier = quoSupplier;
  }
  
  public boolean getQuoSupplier()
  {
    return this.quoSupplier;
  }
  
  public void setRequestType(String requestType)
  {
    this.requestType = requestType;
  }
  
  public String getRequestType()
  {
    return this.requestType;
  }
}
