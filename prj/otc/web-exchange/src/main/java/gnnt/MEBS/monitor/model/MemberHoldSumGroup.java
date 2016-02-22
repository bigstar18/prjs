package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;
import java.text.DecimalFormat;

public class MemberHoldSumGroup
  extends Clone
{
  private String commodityname;
  private String memberno;
  private String commodityid;
  private String bs_flag;
  private String smemberno;
  private String holdqty;
  private String holdfunds;
  private String floatingloss;
  private String evenprice;
  
  public String getId()
  {
    return null;
  }
  
  public String getBs_flag()
  {
    return this.bs_flag;
  }
  
  public void setBs_flag(String bsFlag)
  {
    this.bs_flag = bsFlag;
  }
  
  public String getMemberno()
  {
    return this.memberno;
  }
  
  public void setMemberno(String memberno)
  {
    this.memberno = memberno;
  }
  
  public String getCommodityid()
  {
    return this.commodityid;
  }
  
  public void setCommodityid(String commodityid)
  {
    this.commodityid = commodityid;
  }
  
  public String getBsflag()
  {
    return this.bs_flag;
  }
  
  public void setBsflag(String bs_flag)
  {
    this.bs_flag = bs_flag;
  }
  
  public String getSmemberno()
  {
    return this.smemberno;
  }
  
  public void setSmemberno(String smemberno)
  {
    this.smemberno = smemberno;
  }
  
  public String getHoldqty()
  {
    return this.holdqty;
  }
  
  public void setHoldqty(String holdqty)
  {
    this.holdqty = holdqty;
  }
  
  public String getHoldfunds()
  {
    return this.holdfunds;
  }
  
  public void setHoldfunds(String holdfunds)
  {
    this.holdfunds = new DecimalFormat(",##0.00").format(Double.parseDouble(holdfunds));
  }
  
  public String getFloatingloss()
  {
    return this.floatingloss;
  }
  
  public void setFloatingloss(String floatingloss)
  {
    this.floatingloss = new DecimalFormat(",##0.00").format(Double.parseDouble(floatingloss));
  }
  
  public String getEvenprice()
  {
    return this.evenprice;
  }
  
  public void setEvenprice(String evenprice)
  {
    this.evenprice = new DecimalFormat(",##0.00").format(Double.parseDouble(evenprice));
  }
  
  public String getCommodityname()
  {
    return this.commodityname;
  }
  
  public void setCommodityname(String commodityname)
  {
    this.commodityname = commodityname;
  }
  
  public void setPrimary(String primary) {}
}
