package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class CurrentFund
  extends Clone
{
  private String FirmID;
  private double Balance;
  private double totalFrozenFunds;
  private double LastBalance;
  private double LastWarranty;
  private double SettleMargin;
  private double LastSettleMargin;
  private String ModuleID;
  private double FrozenFunds;
  
  public String getFirmID()
  {
    return this.FirmID;
  }
  
  public void setFirmID(String paramString)
  {
    this.FirmID = paramString;
  }
  
  public double getBalance()
  {
    return this.Balance;
  }
  
  public void setBalance(double paramDouble)
  {
    this.Balance = paramDouble;
  }
  
  public double getTotalFrozenFunds()
  {
    return this.totalFrozenFunds;
  }
  
  public void setTotalFrozenFunds(double paramDouble)
  {
    this.totalFrozenFunds = paramDouble;
  }
  
  public double getLastBalance()
  {
    return this.LastBalance;
  }
  
  public void setLastBalance(double paramDouble)
  {
    this.LastBalance = paramDouble;
  }
  
  public double getLastWarranty()
  {
    return this.LastWarranty;
  }
  
  public void setLastWarranty(double paramDouble)
  {
    this.LastWarranty = paramDouble;
  }
  
  public double getSettleMargin()
  {
    return this.SettleMargin;
  }
  
  public void setSettleMargin(double paramDouble)
  {
    this.SettleMargin = paramDouble;
  }
  
  public double getLastSettleMargin()
  {
    return this.LastSettleMargin;
  }
  
  public void setLastSettleMargin(double paramDouble)
  {
    this.LastSettleMargin = paramDouble;
  }
  
  public String getModuleID()
  {
    return this.ModuleID;
  }
  
  public void setModuleID(String paramString)
  {
    this.ModuleID = paramString;
  }
  
  public double getFrozenFunds()
  {
    return this.FrozenFunds;
  }
  
  public void setFrozenFunds(double paramDouble)
  {
    this.FrozenFunds = paramDouble;
  }
}
