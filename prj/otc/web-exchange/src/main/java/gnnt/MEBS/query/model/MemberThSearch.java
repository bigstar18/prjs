package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;
import java.math.BigDecimal;

public class MemberThSearch
  extends Clone
{
  private String memberNo;
  private String name;
  private Double memberMarginTh;
  private Double customerMarginTh;
  private Double memberFreezeTh;
  private Double memberNetHoldTh;
  private Double traderate;
  
  public Double getMemberNetHoldTh_v()
  {
    BigDecimal b = BigDecimal.valueOf(this.memberNetHoldTh.doubleValue());
    return Double.valueOf(formatDecimals(b.multiply(new BigDecimal(100)), 2).doubleValue());
  }
  
  public Double getMemberFreezeTh_v()
  {
    BigDecimal b = BigDecimal.valueOf(this.memberFreezeTh.doubleValue());
    return Double.valueOf(formatDecimals(b.multiply(new BigDecimal(100)), 2).doubleValue());
  }
  
  public Double getTraderate_v()
  {
    BigDecimal b = BigDecimal.valueOf(this.traderate.doubleValue());
    return Double.valueOf(formatDecimals(b.multiply(new BigDecimal(100)), 2).doubleValue());
  }
  
  public Double getTraderate()
  {
    return this.traderate;
  }
  
  public void setTraderate(Double traderate)
  {
    this.traderate = traderate;
  }
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public Double getMemberMarginTh()
  {
    return this.memberMarginTh;
  }
  
  public void setMemberMarginTh(Double memberMarginTh)
  {
    this.memberMarginTh = memberMarginTh;
  }
  
  public Double getCustomerMarginTh()
  {
    return this.customerMarginTh;
  }
  
  public void setCustomerMarginTh(Double customerMarginTh)
  {
    this.customerMarginTh = customerMarginTh;
  }
  
  public Double getMemberFreezeTh()
  {
    return this.memberFreezeTh;
  }
  
  public void setMemberFreezeTh(Double memberFreezeTh)
  {
    this.memberFreezeTh = memberFreezeTh;
  }
  
  public Double getMemberNetHoldTh()
  {
    return this.memberNetHoldTh;
  }
  
  public void setMemberNetHoldTh(Double memberNetHoldTh)
  {
    this.memberNetHoldTh = memberNetHoldTh;
  }
  
  public void setPrimary(String primary) {}
}
