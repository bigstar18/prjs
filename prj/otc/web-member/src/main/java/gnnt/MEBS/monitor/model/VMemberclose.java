package gnnt.MEBS.monitor.model;

import gnnt.MEBS.base.model.Clone;
import java.text.DecimalFormat;

public class VMemberclose
  extends Clone
{
  private Long tradeno;
  private Long orderno;
  private String memberno;
  private String membername;
  private String commodityid;
  private String commodityname;
  private String quantity;
  private String holdday;
  private String holdtime;
  private String SMemberno;
  private String OMembername;
  private String openBsFlag;
  private String openprice;
  private String holdprice;
  private String closeday;
  private String closetime;
  private String closeBsFlag;
  private String closePrice;
  private String closePl;
  private String tradefee;
  private String netPl;
  
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
    this.quantity = new DecimalFormat(",##0").format(Double.parseDouble(quantity));
  }
  
  public String getHoldtime()
  {
    return this.holdtime;
  }
  
  public void setHoldtime(String holdtime)
  {
    this.holdtime = holdtime;
  }
  
  public String getOMembername()
  {
    return this.OMembername;
  }
  
  public void setOMembername(String OMembername)
  {
    this.OMembername = OMembername;
  }
  
  public String getOpenBsFlag()
  {
    return this.openBsFlag;
  }
  
  public void setOpenBsFlag(String openBsFlag)
  {
    this.openBsFlag = openBsFlag;
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
  
  public String getClosetime()
  {
    return this.closetime;
  }
  
  public void setClosetime(String closetime)
  {
    this.closetime = closetime;
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
  
  public void setOrderno(Long orderno)
  {
    this.orderno = orderno;
  }
  
  public Long getOrderno()
  {
    return this.orderno;
  }
  
  public void setHoldday(String holdday)
  {
    this.holdday = holdday;
  }
  
  public String getHoldday()
  {
    return this.holdday;
  }
  
  public void setCloseday(String closeday)
  {
    this.closeday = closeday;
  }
  
  public String getCloseday()
  {
    return this.closeday;
  }
  
  public void setMembername(String membername)
  {
    this.membername = membername;
  }
  
  public String getMembername()
  {
    return this.membername;
  }
  
  public void setSMemberno(String sMemberno)
  {
    this.SMemberno = sMemberno;
  }
  
  public String getSMemberno()
  {
    return this.SMemberno;
  }
}
