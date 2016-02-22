package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;
import java.text.DecimalFormat;

public class VCustomercloseSimple
  extends Clone
{
  private static final long serialVersionUID = -3282780740591508755L;
  private Long tradeno;
  private String customerno;
  private String commodityid;
  private String commodityname;
  private String quantity;
  private String holdday;
  private String holdtime;
  private String memberno;
  private String closeday;
  private String closetime;
  private String closeBsFlag;
  private String closePrice;
  private String closePl;
  private String tradefee;
  private String netPl;
  private String tradetype;
  private String operatetype;
  
  public String getTradetype()
  {
    return this.tradetype;
  }
  
  public void setTradetype(String tradetype)
  {
    this.tradetype = tradetype;
  }
  
  public String getOperatetype()
  {
    return this.operatetype;
  }
  
  public void setOperatetype(String operatetype)
  {
    this.operatetype = operatetype;
  }
  
  public Long getId()
  {
    return getTradeno();
  }
  
  public Long getTradeno()
  {
    return this.tradeno;
  }
  
  public void setTradeno(Long tradeno)
  {
    this.tradeno = tradeno;
  }
  
  public String getCustomerno()
  {
    return this.customerno;
  }
  
  public void setCustomerno(String customerno)
  {
    this.customerno = customerno;
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
  
  public String getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(String quantity)
  {
    this.quantity = new DecimalFormat(",##0").format(Long.parseLong(quantity));
  }
  
  public String getMemberno()
  {
    return this.memberno;
  }
  
  public void setMemberno(String memberno)
  {
    this.memberno = memberno;
  }
  
  public String getCloseBsFlag()
  {
    return this.closeBsFlag;
  }
  
  public void setCloseBsFlag(String closeBsFlag)
  {
    this.closeBsFlag = closeBsFlag;
  }
  
  public String getClosePrice()
  {
    return this.closePrice;
  }
  
  public void setClosePrice(String closePrice)
  {
    this.closePrice = new DecimalFormat(",##0.00").format(Double.parseDouble(closePrice));
  }
  
  public String getClosePl()
  {
    return this.closePl;
  }
  
  public void setClosePl(String closePl)
  {
    this.closePl = new DecimalFormat(",##0.00").format(Double.parseDouble(closePl));
  }
  
  public String getTradefee()
  {
    return this.tradefee;
  }
  
  public void setTradefee(String tradefee)
  {
    this.tradefee = new DecimalFormat(",##0.00").format(Double.parseDouble(tradefee));
  }
  
  public String getNetPl()
  {
    return this.netPl;
  }
  
  public void setNetPl(String netPl)
  {
    this.netPl = new DecimalFormat(",##0.00").format(Double.parseDouble(netPl));
  }
  
  public void setPrimary(String primary) {}
  
  public void setHoldday(String holdday)
  {
    this.holdday = holdday;
  }
  
  public String getHoldday()
  {
    return this.holdday;
  }
  
  public void setHoldtime(String holdtime)
  {
    this.holdtime = holdtime;
  }
  
  public String getHoldtime()
  {
    return this.holdtime;
  }
  
  public void setCloseday(String closeday)
  {
    this.closeday = closeday;
  }
  
  public String getCloseday()
  {
    return this.closeday;
  }
  
  public void setClosetime(String closetime)
  {
    this.closetime = closetime;
  }
  
  public String getClosetime()
  {
    return this.closetime;
  }
}
