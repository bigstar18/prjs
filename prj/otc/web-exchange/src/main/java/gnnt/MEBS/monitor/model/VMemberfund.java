package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;
import java.text.DecimalFormat;

public class VMemberfund
  extends Clone
{
  private String memberno;
  private String flAccept;
  private String lastbalance;
  private String flHedge;
  private String netfl;
  private String balance;
  private String frozenth;
  private String freebalance;
  private String riskrate;
  private String status;
  
  public String getId()
  {
    return getMemberno();
  }
  
  public String getMemberno()
  {
    return this.memberno;
  }
  
  public void setMemberno(String memberno)
  {
    this.memberno = memberno;
  }
  
  public String getFlAccept()
  {
    return this.flAccept;
  }
  
  public void setFlAccept(String flAccept)
  {
    this.flAccept = new DecimalFormat(",##0.00").format(Double.parseDouble(flAccept));
  }
  
  public String getFlHedge()
  {
    return this.flHedge;
  }
  
  public void setFlHedge(String flHedge)
  {
    this.flHedge = new DecimalFormat(",##0.00").format(Double.parseDouble(flHedge));
  }
  
  public String getNetfl()
  {
    return this.netfl;
  }
  
  public void setNetfl(String netfl)
  {
    this.netfl = new DecimalFormat(",##0.00").format(Double.parseDouble(netfl));
  }
  
  public String getBalance()
  {
    return this.balance;
  }
  
  public void setBalance(String balance)
  {
    this.balance = new DecimalFormat(",##0.00").format(Double.parseDouble(balance));
  }
  
  public String getFrozenth()
  {
    return this.frozenth;
  }
  
  public void setFrozenth(String frozenth)
  {
    this.frozenth = new DecimalFormat(",##0.00").format(Double.parseDouble(frozenth));
  }
  
  public String getFreebalance()
  {
    return this.freebalance;
  }
  
  public void setFreebalance(String freebalance)
  {
    this.freebalance = new DecimalFormat(",##0.00").format(Double.parseDouble(freebalance));
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
    if (this.status.equalsIgnoreCase("冻结")) {
      setRiskrate("--");
    }
  }
  
  public void setPrimary(String primary) {}
  
  public void setLastbalance(String lastbalance)
  {
    this.lastbalance = new DecimalFormat(",##0.00").format(Double.parseDouble(lastbalance));
  }
  
  public String getLastbalance()
  {
    return this.lastbalance;
  }
  
  public void setRiskrate(String riskrate)
  {
    if (riskrate == "--")
    {
      this.riskrate = "--";
      return;
    }
    if (this.status == "冻结")
    {
      this.riskrate = "--";
    }
    else if (riskrate == "99999")
    {
      this.riskrate = "安全";
    }
    else
    {
      double tmpnum = Double.parseDouble(riskrate);
      if (tmpnum >= 200.0D) {
        this.riskrate = "安全";
      } else {
        this.riskrate = new DecimalFormat(",##0.00").format(Double.parseDouble(riskrate));
      }
    }
  }
  
  public String getRiskrate()
  {
    return this.riskrate;
  }
}
