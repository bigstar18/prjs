package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;
import java.text.DecimalFormat;

public class VCustomerhold
  extends Clone
{
  private Long holdno;
  private String firmid;
  private String customerno;
  private String customname;
  private String commodityid;
  private String organizationno;
  private String organizationname;
  private String managerno;
  private String commodityname;
  private String holdqty;
  private String stoplossprice;
  private String stopprofitprice;
  private String holdmargin;
  private String bsFlag;
  private String holdtime;
  private String holdday;
  private String OFirmid;
  private String memberno;
  private String OFirmname;
  private String openprice;
  private String holdprice;
  private String price;
  private String floatingloss;
  private String tradefee;
  private String delayfee;
  
  public Long getId()
  {
    return getHoldno();
  }
  
  public String getOrganizationname()
  {
    return this.organizationname;
  }
  
  public void setOrganizationname(String organizationname)
  {
    this.organizationname = organizationname;
  }
  
  public Long getHoldno()
  {
    return this.holdno;
  }
  
  public void setHoldno(Long holdno)
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
  
  public String getCustomname()
  {
    return this.customname;
  }
  
  public void setCustomname(String customname)
  {
    this.customname = customname;
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
    this.holdqty = new DecimalFormat(",##0").format(Long.parseLong(holdqty));
  }
  
  public String getStoplossprice()
  {
    return this.stoplossprice;
  }
  
  public void setStoplossprice(String stoplossprice)
  {
    if (stoplossprice == null) {
      stoplossprice = "0";
    }
    if (stoplossprice == "") {
      stoplossprice = "0";
    }
    if (Double.parseDouble(stoplossprice) == 0.0D) {
      this.stoplossprice = "--";
    } else {
      this.stoplossprice = new DecimalFormat(",##0.00").format(Double.parseDouble(stoplossprice));
    }
  }
  
  public String getStopprofitprice()
  {
    return this.stopprofitprice;
  }
  
  public void setStopprofitprice(String stopprofitprice)
  {
    if (stopprofitprice == null) {
      stopprofitprice = "0";
    }
    if (stopprofitprice == "") {
      stopprofitprice = "0";
    }
    if (Double.parseDouble(stopprofitprice) == 0.0D) {
      this.stopprofitprice = "--";
    } else {
      this.stopprofitprice = new DecimalFormat(",##0.00").format(Double.parseDouble(stopprofitprice));
    }
  }
  
  public String getBsFlag()
  {
    return this.bsFlag;
  }
  
  public void setBsFlag(String bsFlag)
  {
    this.bsFlag = bsFlag;
  }
  
  public String getHoldtime()
  {
    return this.holdtime;
  }
  
  public void setHoldtime(String holdtime)
  {
    this.holdtime = holdtime;
  }
  
  public String getOFirmid()
  {
    return this.OFirmid;
  }
  
  public void setOFirmid(String OFirmid)
  {
    this.OFirmid = OFirmid;
  }
  
  public String getOFirmname()
  {
    return this.OFirmname;
  }
  
  public void setOFirmname(String OFirmname)
  {
    this.OFirmname = OFirmname;
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
    if (tradefee == null) {
      tradefee = "0";
    }
    if (tradefee == "") {
      tradefee = "0";
    }
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
  
  public void setOrganizationno(String organizationno)
  {
    this.organizationno = organizationno;
  }
  
  public String getOrganizationno()
  {
    return this.organizationno;
  }
  
  public void setManagerno(String managerno)
  {
    this.managerno = managerno;
  }
  
  public String getManagerno()
  {
    return this.managerno;
  }
  
  public void setHoldmargin(String holdmargin)
  {
    this.holdmargin = new DecimalFormat(",##0.00").format(Double.parseDouble(holdmargin));
  }
  
  public String getHoldmargin()
  {
    return this.holdmargin;
  }
  
  public void setHoldday(String holdday)
  {
    this.holdday = holdday;
  }
  
  public String getHoldday()
  {
    return this.holdday;
  }
  
  public void setCustomerno(String customerno)
  {
    this.customerno = customerno;
  }
  
  public String getCustomerno()
  {
    return this.customerno;
  }
  
  public void setMemberno(String memberno)
  {
    this.memberno = memberno;
  }
  
  public String getMemberno()
  {
    return this.memberno;
  }
}
