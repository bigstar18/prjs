package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;
import java.text.DecimalFormat;

public class VCustomerfund
  extends Clone
{
  private String customerno;
  private String memberno;
  private String organizationno;
  private String organizationname;
  private String brokerageno;
  private String managerno;
  private String customername;
  private String lastbalance;
  private String floatingloss;
  private String netbalance;
  private String livemargin;
  private String margin;
  private String frozenmargin;
  private String riskvalue;
  private String riskrate;
  private String membername;
  
  public String getMembername()
  {
    return this.membername;
  }
  
  public void setMembername(String membername)
  {
    this.membername = membername;
  }
  
  public String getId()
  {
    return getCustomerno();
  }
  
  public String getOrganizationname()
  {
    return this.organizationname;
  }
  
  public void setOrganizationname(String organizationname)
  {
    this.organizationname = organizationname;
  }
  
  public String getCustomerno()
  {
    return this.customerno;
  }
  
  public void setCustomerno(String customerno)
  {
    this.customerno = customerno;
  }
  
  public String getMemberno()
  {
    return this.memberno;
  }
  
  public void setMemberno(String memberno)
  {
    this.memberno = memberno;
  }
  
  public String getOrganizationno()
  {
    return this.organizationno;
  }
  
  public void setOrganizationno(String organizationno)
  {
    this.organizationno = organizationno;
  }
  
  public String getBrokerageno()
  {
    return this.brokerageno;
  }
  
  public void setBrokerageno(String brokerageno)
  {
    this.brokerageno = brokerageno;
  }
  
  public String getManagerno()
  {
    return this.managerno;
  }
  
  public void setManagerno(String managerno)
  {
    this.managerno = managerno;
  }
  
  public String getCustomername()
  {
    return this.customername;
  }
  
  public void setCustomername(String customername)
  {
    this.customername = customername;
  }
  
  public String getLastbalance()
  {
    return this.lastbalance;
  }
  
  public void setLastbalance(String lastbalance)
  {
    this.lastbalance = new DecimalFormat(",##0.00").format(Double.parseDouble(lastbalance));
  }
  
  public String getFloatingloss()
  {
    return this.floatingloss;
  }
  
  public void setFloatingloss(String floatingloss)
  {
    this.floatingloss = new DecimalFormat(",##0.00").format(Double.parseDouble(floatingloss));
  }
  
  public String getNetbalance()
  {
    return this.netbalance;
  }
  
  public void setNetbalance(String netbalance)
  {
    this.netbalance = new DecimalFormat(",##0.00").format(Double.parseDouble(netbalance));
  }
  
  public String getLivemargin()
  {
    return this.livemargin;
  }
  
  public void setLivemargin(String livemargin)
  {
    this.livemargin = new DecimalFormat(",##0.00").format(Double.parseDouble(livemargin));
  }
  
  public String getMargin()
  {
    return this.margin;
  }
  
  public void setMargin(String margin)
  {
    this.margin = new DecimalFormat(",##0.00").format(Double.parseDouble(margin));
  }
  
  public String getFrozenmargin()
  {
    return this.frozenmargin;
  }
  
  public void setFrozenmargin(String frozenmargin)
  {
    this.frozenmargin = new DecimalFormat(",##0.00").format(Double.parseDouble(frozenmargin));
  }
  
  public String getRiskvalue()
  {
    return this.riskvalue;
  }
  
  public void setRiskvalue(String riskvalue)
  {
    this.riskvalue = new DecimalFormat(",##0.00").format(Double.parseDouble(riskvalue));
  }
  
  public String getRiskrate()
  {
    return this.riskrate;
  }
  
  public void setRiskrate(String riskrate)
  {
    if (riskrate == "99999")
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
  
  public void setPrimary(String primary) {}
}
