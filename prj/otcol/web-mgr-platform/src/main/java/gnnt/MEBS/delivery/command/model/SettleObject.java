package gnnt.MEBS.delivery.command.model;

import gnnt.MEBS.delivery.model.settle.SettleMatch;

public class SettleObject
{
  private String matchId;
  private double amount;
  private boolean sign;
  private String changeRegStockId;
  private int type;
  private SettleMatch settleMatch;
  
  public SettleMatch getSettleMatch()
  {
    return this.settleMatch;
  }
  
  public void setSettleMatch(SettleMatch paramSettleMatch)
  {
    this.settleMatch = paramSettleMatch;
  }
  
  public String getChangeRegStockId()
  {
    return this.changeRegStockId;
  }
  
  public void setChangeRegStockId(String paramString)
  {
    this.changeRegStockId = paramString;
  }
  
  public String getMatchId()
  {
    return this.matchId;
  }
  
  public void setMatchId(String paramString)
  {
    this.matchId = paramString;
  }
  
  public double getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(double paramDouble)
  {
    this.amount = paramDouble;
  }
  
  public boolean getSign()
  {
    return this.sign;
  }
  
  public void setSign(boolean paramBoolean)
  {
    this.sign = paramBoolean;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
}
