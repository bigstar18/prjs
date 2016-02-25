package gnnt.MEBS.vendue.server.rmi;

import java.io.Serializable;

public abstract interface TradeResuleValueRemote
  extends Serializable
{
  public abstract long getSubmitId();
  
  public abstract void setSubmitId(long paramLong);
  
  public abstract long getAmount();
  
  public abstract void setAmount(long paramLong);
  
  public abstract String getCode();
  
  public abstract void setCode(String paramString);
  
  public abstract long getCommodityID();
  
  public abstract void setCommodityID(long paramLong);
  
  public abstract double getPrice();
  
  public abstract void setPrice(double paramDouble);
  
  public abstract String getUserId();
  
  public abstract void setUserId(String paramString);
}
