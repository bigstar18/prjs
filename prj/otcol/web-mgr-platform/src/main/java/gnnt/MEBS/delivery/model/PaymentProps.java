package gnnt.MEBS.delivery.model;

import gnnt.MEBS.base.model.Clone;

public class PaymentProps
  extends Clone
{
  private String moduleID;
  private long breedID;
  private int settleDayNo;
  private double buyPayoutPct;
  private double sellIncomePct;
  
  public long getBreedID()
  {
    return this.breedID;
  }
  
  public void setBreedID(long paramLong)
  {
    this.breedID = paramLong;
  }
  
  public double getBuyPayoutPct()
  {
    return this.buyPayoutPct;
  }
  
  public void setBuyPayoutPct(double paramDouble)
  {
    this.buyPayoutPct = paramDouble;
  }
  
  public String getModuleID()
  {
    return this.moduleID;
  }
  
  public void setModuleID(String paramString)
  {
    this.moduleID = paramString;
  }
  
  public double getSellIncomePct()
  {
    return this.sellIncomePct;
  }
  
  public void setSellIncomePct(double paramDouble)
  {
    this.sellIncomePct = paramDouble;
  }
  
  public int getSettleDayNo()
  {
    return this.settleDayNo;
  }
  
  public void setSettleDayNo(int paramInt)
  {
    this.settleDayNo = paramInt;
  }
}
