package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import java.text.DecimalFormat;

public class MemberHoldView
  extends Clone
{
  private String holdno;
  private String firmid;
  private String memberno;
  private String smemberno;
  private String commodityid;
  private String commodityname;
  private String holdqty;
  private String bs_flag;
  private String holdtime;
  private String holdday;
  private String o_firmid;
  private String o_firmname;
  private String openprice;
  private String holdprice;
  private String price;
  private String floatingloss;
  private String tradefee;
  private String delayfee;
  
  @ClassDiscription(name="id")
  public String getId()
  {
    return this.holdno;
  }
  
  public String getHoldno()
  {
    return this.holdno;
  }
  
  public String getHoldday()
  {
    return this.holdday;
  }
  
  public void setHoldday(String holdday)
  {
    this.holdday = holdday;
  }
  
  public void setHoldno(String holdno)
  {
    this.holdno = holdno;
  }
  
  public String getFirmid()
  {
    return this.firmid;
  }
  
  public void setFirmid(String firmid)
  {
    this.firmid = firmid;
  }
  
  public String getCommodityid()
  {
    return this.commodityid;
  }
  
  public void setCommodityid(String commodityid)
  {
    this.commodityid = commodityid;
  }
  
  public String getCommodityname()
  {
    return this.commodityname;
  }
  
  public void setCommodityname(String commodityname)
  {
    this.commodityname = commodityname;
  }
  
  public String getHoldqty()
  {
    return this.holdqty;
  }
  
  public void setHoldqty(String holdqty)
  {
    this.holdqty = new DecimalFormat(",##0").format(Double.parseDouble(holdqty));
  }
  
  public String getBs_flag()
  {
    return this.bs_flag;
  }
  
  public void setBs_flag(String bs_flag)
  {
    this.bs_flag = bs_flag;
  }
  
  public String getHoldtime()
  {
    return this.holdtime;
  }
  
  public void setHoldtime(String holdtime)
  {
    this.holdtime = holdtime;
  }
  
  public String getO_firmid()
  {
    return this.o_firmid;
  }
  
  public void setO_firmid(String o_firmid)
  {
    this.o_firmid = o_firmid;
  }
  
  public String getO_firmname()
  {
    return this.o_firmname;
  }
  
  public void setO_firmname(String o_firmname)
  {
    this.o_firmname = o_firmname;
  }
  
  public String getOpenprice()
  {
    return this.openprice;
  }
  
  public void setOpenprice(String openprice)
  {
    this.openprice = new DecimalFormat(",##0.00").format(Double.parseDouble(openprice));
  }
  
  public String getHoldprice()
  {
    return this.holdprice;
  }
  
  public void setHoldprice(String holdprice)
  {
    this.holdprice = new DecimalFormat(",##0.00").format(Double.parseDouble(holdprice));
  }
  
  public String getPrice()
  {
    return this.price;
  }
  
  public void setPrice(String price)
  {
    this.price = new DecimalFormat(",##0.00").format(Double.parseDouble(price));
  }
  
  public String getFloatingloss()
  {
    return this.floatingloss;
  }
  
  public void setFloatingloss(String floatingloss)
  {
    this.floatingloss = new DecimalFormat(",##0.00").format(Double.parseDouble(floatingloss));
  }
  
  public String getTradefee()
  {
    return this.tradefee;
  }
  
  public void setTradefee(String tradefee)
  {
    this.tradefee = new DecimalFormat(",##0.00").format(Double.parseDouble(tradefee));
  }
  
  public String getDelayfee()
  {
    return this.delayfee;
  }
  
  public void setDelayfee(String delayfee)
  {
    this.delayfee = new DecimalFormat(",##0.00").format(Double.parseDouble(delayfee));
  }
  
  public void setPrimary(String primary) {}
  
  public void setMemberno(String memberno)
  {
    this.memberno = memberno;
  }
  
  public String getMemberno()
  {
    return this.memberno;
  }
  
  public void setSmemberno(String smemberno)
  {
    this.smemberno = smemberno;
  }
  
  public String getSmemberno()
  {
    return this.smemberno;
  }
}
