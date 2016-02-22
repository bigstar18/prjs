package gnnt.MEBS.report.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.util.Date;

public class SpecialMemberHoldReport
  extends Clone
{
  private Date clearDate;
  private String s_memberNo;
  private String s_memberName;
  private String commodityId;
  private String commodityName;
  private Integer buyqty;
  private Integer sellqty;
  private Integer netqty;
  private Double delayfee;
  private Double endcapital;
  
  public String getS_memberNo()
  {
    return this.s_memberNo;
  }
  
  public void setS_memberNo(String s_memberNo)
  {
    this.s_memberNo = s_memberNo;
  }
  
  public String getS_memberName()
  {
    return this.s_memberName;
  }
  
  public void setS_memberName(String s_memberName)
  {
    this.s_memberName = s_memberName;
  }
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  public Integer getBuyqty()
  {
    return this.buyqty;
  }
  
  public void setBuyqty(Integer buyqty)
  {
    this.buyqty = buyqty;
  }
  
  public Integer getSellqty()
  {
    return this.sellqty;
  }
  
  public void setSellqty(Integer sellqty)
  {
    this.sellqty = sellqty;
  }
  
  public Integer getNetqty()
  {
    return this.netqty;
  }
  
  public void setNetqty(Integer netqty)
  {
    this.netqty = netqty;
  }
  
  public Double getDelayfee()
  {
    return this.delayfee;
  }
  
  public void setDelayfee(Double delayfee)
  {
    this.delayfee = delayfee;
  }
  
  public Double getEndcapital()
  {
    return this.endcapital;
  }
  
  public void setEndcapital(Double endcapital)
  {
    this.endcapital = endcapital;
  }
  
  public Date getClearDate()
  {
    return this.clearDate;
  }
  
  public void setClearDate(Date clearDate)
  {
    this.clearDate = clearDate;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
