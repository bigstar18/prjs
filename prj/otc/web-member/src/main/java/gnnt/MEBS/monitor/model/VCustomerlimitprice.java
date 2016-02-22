package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;
import java.text.DecimalFormat;
import java.util.Date;

public class VCustomerlimitprice
  extends Clone
{
  private Long orderno;
  private String holdno;
  private String firmid;
  private String customerno;
  private String customername;
  private String memberno;
  private String organizationno;
  private String organizationname;
  private String brokerageno;
  private String managerno;
  private String commodityname;
  private String commodityid;
  private String bsFlag;
  private String qty;
  private String price;
  private String stoplossprice;
  private String stopprofitprice;
  private String orderday;
  private String ordertimer;
  private Date withdrawtime;
  private Integer withdrawtype;
  private Integer status;
  
  public Long getId()
  {
    return getOrderno();
  }
  
  public String getOrganizationname()
  {
    return this.organizationname;
  }
  
  public void setOrganizationname(String organizationname)
  {
    this.organizationname = organizationname;
  }
  
  public Long getOrderno()
  {
    return this.orderno;
  }
  
  public void setOrderno(Long orderno)
  {
    this.orderno = orderno;
  }
  
  public String getFirmid()
  {
    return this.firmid;
  }
  
  public void setFirmid(String firmid)
  {
    this.firmid = firmid;
  }
  
  public String getCustomerno()
  {
    return this.customerno;
  }
  
  public void setCustomerno(String customerno)
  {
    this.customerno = customerno;
  }
  
  public String getCustomername()
  {
    return this.customername;
  }
  
  public void setCustomername(String customername)
  {
    this.customername = customername;
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
  
  public String getCommodityname()
  {
    return this.commodityname;
  }
  
  public void setCommodityname(String commodityname)
  {
    this.commodityname = commodityname;
  }
  
  public String getCommodityid()
  {
    return this.commodityid;
  }
  
  public void setCommodityid(String commodityid)
  {
    this.commodityid = commodityid;
  }
  
  public String getBsFlag()
  {
    return this.bsFlag;
  }
  
  public void setBsFlag(String bsFlag)
  {
    this.bsFlag = bsFlag;
  }
  
  public String getQty()
  {
    return this.qty;
  }
  
  public void setQty(String qty)
  {
    this.qty = new DecimalFormat(",##0").format(Long.parseLong(qty));
  }
  
  public String getPrice()
  {
    return this.price;
  }
  
  public void setPrice(String price)
  {
    this.price = new DecimalFormat(",##0.00").format(Double.parseDouble(price));
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
  
  public String getOrdertimer()
  {
    return this.ordertimer;
  }
  
  public void setOrdertimer(String ordertimer)
  {
    this.ordertimer = ordertimer;
  }
  
  public Date getWithdrawtime()
  {
    return this.withdrawtime;
  }
  
  public void setWithdrawtime(Date withdrawtime)
  {
    this.withdrawtime = withdrawtime;
  }
  
  public Integer getWithdrawtype()
  {
    return this.withdrawtype;
  }
  
  public void setWithdrawtype(Integer withdrawtype)
  {
    this.withdrawtype = withdrawtype;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  public void setPrimary(String primary) {}
  
  public void setHoldno(String holdno)
  {
    this.holdno = holdno;
    if ((holdno == null) || (holdno == "0")) {
      this.holdno = "--";
    }
  }
  
  public String getHoldno()
  {
    return this.holdno;
  }
  
  public void setOrderday(String orderday)
  {
    this.orderday = orderday;
  }
  
  public String getOrderday()
  {
    return this.orderday;
  }
}
