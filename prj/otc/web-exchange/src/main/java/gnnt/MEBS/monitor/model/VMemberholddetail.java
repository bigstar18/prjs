package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;
import java.text.DecimalFormat;

public class VMemberholddetail
  extends Clone
{
  private String memberno;
  private String membername;
  private String commodityid;
  private String commodityname;
  private String buyqty;
  private String sellqty;
  private String userbuyqty;
  private String usersellqty;
  private String netqty;
  private String bsFlag;
  private String netholdth;
  
  public String getId()
  {
    return null;
  }
  
  public String getMembername()
  {
    return this.membername;
  }
  
  public void setMembername(String membername)
  {
    this.membername = membername;
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
  
  public String getBuyqty()
  {
    return this.buyqty;
  }
  
  public void setBuyqty(String buyqty)
  {
    if (buyqty == null) {
      buyqty = "0";
    }
    if (buyqty == "") {
      buyqty = "0";
    }
    this.buyqty = new DecimalFormat(",##0").format(Long.parseLong(buyqty));
  }
  
  public String getSellqty()
  {
    return this.sellqty;
  }
  
  public void setSellqty(String sellqty)
  {
    this.sellqty = new DecimalFormat(",##0").format(Long.parseLong(sellqty));
  }
  
  public String getUserbuyqty()
  {
    return this.userbuyqty;
  }
  
  public void setUserbuyqty(String userbuyqty)
  {
    if (userbuyqty == null) {
      userbuyqty = "0";
    }
    if (userbuyqty == "") {
      userbuyqty = "0";
    }
    this.userbuyqty = new DecimalFormat(",##0").format(Double.parseDouble(userbuyqty));
  }
  
  public String getUsersellqty()
  {
    return this.usersellqty;
  }
  
  public void setUsersellqty(String usersellqty)
  {
    if (usersellqty == null) {
      usersellqty = "0";
    }
    if (usersellqty == "") {
      usersellqty = "0";
    }
    this.usersellqty = new DecimalFormat(",##0").format(Long.parseLong(usersellqty));
  }
  
  public String getNetqty()
  {
    return this.netqty;
  }
  
  public void setNetqty(String netqty)
  {
    this.netqty = new DecimalFormat(",##0").format(Long.parseLong(netqty));
  }
  
  public String getBsFlag()
  {
    return this.bsFlag;
  }
  
  public void setBsFlag(String bsFlag)
  {
    this.bsFlag = bsFlag;
  }
  
  public String getNetholdth()
  {
    return this.netholdth;
  }
  
  public void setNetholdth(String netholdth)
  {
    this.netholdth = new DecimalFormat(",##0").format(Long.parseLong(netholdth));
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
}
