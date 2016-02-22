package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.io.Serializable;
import java.util.Date;

public class CustomerApplySettleSearch
  extends Clone
{
  private String id;
  private String memberNo;
  private String memberName;
  private String organizationno;
  private String organizationname;
  private String brokerageno;
  private String brokeragename;
  private String customerNo;
  private String customerName;
  private String commodityID;
  private String commodityName;
  private Double quantity;
  private Double price;
  private Date updateTime;
  private int bs_flag;
  private Double settleFee;
  private int status;
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  public String getOrganizationno()
  {
    return this.organizationno;
  }
  
  public void setOrganizationno(String organizationno)
  {
    this.organizationno = organizationno;
  }
  
  public String getOrganizationname()
  {
    return this.organizationname;
  }
  
  public void setOrganizationname(String organizationname)
  {
    this.organizationname = organizationname;
  }
  
  public String getBrokerageno()
  {
    return this.brokerageno;
  }
  
  public void setBrokerageno(String brokerageno)
  {
    this.brokerageno = brokerageno;
  }
  
  public String getBrokeragename()
  {
    return this.brokeragename;
  }
  
  public void setBrokeragename(String brokeragename)
  {
    this.brokeragename = brokeragename;
  }
  
  public String getCustomerNo()
  {
    return this.customerNo;
  }
  
  public void setCustomerNo(String customerNo)
  {
    this.customerNo = customerNo;
  }
  
  public String getCustomerName()
  {
    return this.customerName;
  }
  
  public void setCustomerName(String customerName)
  {
    this.customerName = customerName;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  public Double getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(Double quantity)
  {
    this.quantity = quantity;
  }
  
  public Double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(Double price)
  {
    this.price = price;
  }
  
  public Date getUpdateTime()
  {
    return this.updateTime;
  }
  
  public void setUpdateTime(Date updateTime)
  {
    this.updateTime = updateTime;
  }
  
  @ClassDiscription(name="买卖方向", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="买入"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="卖出")})
  public int getBs_flag()
  {
    return this.bs_flag;
  }
  
  public void setBs_flag(int bsFlag)
  {
    this.bs_flag = bsFlag;
  }
  
  public Double getSettleFee()
  {
    return this.settleFee;
  }
  
  public void setSettleFee(Double settleFee)
  {
    this.settleFee = settleFee;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
