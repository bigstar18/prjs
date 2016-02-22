package gnnt.MEBS.settlement.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.config.constant.NumberDigits;
import java.math.BigDecimal;

public class MarketParameters
  extends Clone
{
  private String marketCode;
  private String marketName;
  private Integer clearDelaySecs;
  private BigDecimal customerOpenMinFunds;
  private BigDecimal customerOrgOpenMinFunds;
  private BigDecimal memberOpenMinFunds;
  private BigDecimal specialMemberOpenMinFunds;
  private BigDecimal brokeMemberOpenMinFunds;
  private BigDecimal runMode;
  private Integer clearRunMode;
  private BigDecimal initPresecs;
  
  @ClassDiscription(name="结算模式")
  public Integer getClearRunMode()
  {
    return this.clearRunMode;
  }
  
  public void setClearRunMode(Integer clearRunMode)
  {
    this.clearRunMode = clearRunMode;
  }
  
  @ClassDiscription(name="开市准备提前秒数")
  public BigDecimal getInitPresecs()
  {
    return this.initPresecs;
  }
  
  public void setInitPresecs(BigDecimal initPresecs)
  {
    this.initPresecs = initPresecs;
  }
  
  @ClassDiscription(name="运行模式")
  public BigDecimal getRunMode()
  {
    return this.runMode;
  }
  
  public void setRunMode(BigDecimal runMode)
  {
    this.runMode = runMode;
  }
  
  @ClassDiscription(name="经纪会员激活最小入金")
  public BigDecimal getBrokeMemberOpenMinFunds()
  {
    return formatDecimals(this.brokeMemberOpenMinFunds, NumberDigits.FUNDS);
  }
  
  public void setBrokeMemberOpenMinFunds(BigDecimal brokeMemberOpenMinFunds)
  {
    this.brokeMemberOpenMinFunds = brokeMemberOpenMinFunds;
  }
  
  @ClassDiscription(name="特别会员激活最小入金")
  public BigDecimal getSpecialMemberOpenMinFunds()
  {
    return formatDecimals(this.specialMemberOpenMinFunds, NumberDigits.FUNDS);
  }
  
  public void setSpecialMemberOpenMinFunds(BigDecimal specialMemberOpenMinFunds)
  {
    this.specialMemberOpenMinFunds = specialMemberOpenMinFunds;
  }
  
  public String getMarketCode()
  {
    return this.marketCode;
  }
  
  @ClassDiscription(name="市场", key=true, keyWord=true)
  public String getMarketCode_log()
  {
    return "默认";
  }
  
  public void setMarketCode(String marketCode)
  {
    this.marketCode = marketCode;
  }
  
  @ClassDiscription(name="市场名称")
  public String getMarketName()
  {
    return this.marketName;
  }
  
  public void setMarketName(String marketName)
  {
    this.marketName = marketName;
  }
  
  @ClassDiscription(name="结算延迟秒数")
  public Integer getClearDelaySecs()
  {
    return this.clearDelaySecs;
  }
  
  public void setClearDelaySecs(Integer clearDelaySecs)
  {
    this.clearDelaySecs = clearDelaySecs;
  }
  
  @ClassDiscription(name="客户激活最小入金")
  public BigDecimal getCustomerOpenMinFunds()
  {
    return formatDecimals(this.customerOpenMinFunds, NumberDigits.FUNDS);
  }
  
  public void setCustomerOpenMinFunds(BigDecimal customerOpenMinFunds)
  {
    this.customerOpenMinFunds = customerOpenMinFunds;
  }
  
  @ClassDiscription(name="会员激活最小入金")
  public BigDecimal getMemberOpenMinFunds()
  {
    return formatDecimals(this.memberOpenMinFunds, NumberDigits.FUNDS);
  }
  
  public void setMemberOpenMinFunds(BigDecimal memberOpenMinFunds)
  {
    this.memberOpenMinFunds = memberOpenMinFunds;
  }
  
  @ClassDiscription(name="机构客户激活最小入金")
  public BigDecimal getCustomerOrgOpenMinFunds()
  {
    return formatDecimals(this.customerOrgOpenMinFunds, NumberDigits.FUNDS);
  }
  
  public void setCustomerOrgOpenMinFunds(BigDecimal customerOrgOpenMinFunds)
  {
    this.customerOrgOpenMinFunds = customerOrgOpenMinFunds;
  }
  
  public String getId()
  {
    return this.marketCode;
  }
  
  public void setPrimary(String primary)
  {
    this.marketCode = primary;
  }
}
