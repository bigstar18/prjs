package gnnt.MEBS.delivery.command.settleExtra;

public class SettleBalanceCheck
{
  private boolean isBalance;
  private String buyOrSell;
  private boolean isDeduction;
  
  public boolean isBalance()
  {
    return this.isBalance;
  }
  
  public void setIsBalance(boolean paramBoolean)
  {
    this.isBalance = paramBoolean;
  }
  
  public String getBuyOrSell()
  {
    return this.buyOrSell;
  }
  
  public void setBuyOrSell(String paramString)
  {
    this.buyOrSell = paramString;
  }
  
  public boolean isDeduction()
  {
    return this.isDeduction;
  }
  
  public void setIsDeduction(boolean paramBoolean)
  {
    this.isDeduction = paramBoolean;
  }
}
