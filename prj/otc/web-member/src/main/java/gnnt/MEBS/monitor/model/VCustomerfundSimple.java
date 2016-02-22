package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;
import java.text.DecimalFormat;

public class VCustomerfundSimple
  extends Clone
{
  private String customerno;
  private String memberno;
  private String riskrate;
  
  public String getId()
  {
    return getCustomerno();
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
