package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class MarketStatus
  extends Clone
{
  private String marketId;
  private int currentState;
  private int nextState;
  private int tradeTimeSerialNumber;
  private String isAuto;
  
  public String getMarketId()
  {
    return this.marketId;
  }
  
  public void setMarketId(String paramString)
  {
    this.marketId = paramString;
  }
  
  public int getCurrentState()
  {
    return this.currentState;
  }
  
  public void setCurrentState(int paramInt)
  {
    this.currentState = paramInt;
  }
  
  public int getNextState()
  {
    return this.nextState;
  }
  
  public void setNextState(int paramInt)
  {
    this.nextState = paramInt;
  }
  
  public int getTradeTimeSerialNumber()
  {
    return this.tradeTimeSerialNumber;
  }
  
  public void setTradeTimeSerialNumber(int paramInt)
  {
    this.tradeTimeSerialNumber = paramInt;
  }
  
  public String getIsAuto()
  {
    return this.isAuto;
  }
  
  public void setIsAuto(String paramString)
  {
    this.isAuto = paramString;
  }
}
