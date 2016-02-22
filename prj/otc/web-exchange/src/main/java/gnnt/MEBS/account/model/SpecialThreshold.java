package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.config.constant.NumberDigits;
import java.math.BigDecimal;

public class SpecialThreshold
  extends Clone
{
  private String firmId;
  private String memberName;
  private BigDecimal minRiskFund;
  private BigDecimal warnTh;
  private BigDecimal frozenTh;
  private Integer holdWarnTh;
  
  @ClassDiscription(name="特别会员交易商", key=true, keyWord=true)
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  @ClassDiscription(name="特别会员出金阈值")
  public BigDecimal getMinRiskFund()
  {
    return this.minRiskFund;
  }
  
  public void setMinRiskFund(BigDecimal minRiskFund)
  {
    this.minRiskFund = minRiskFund;
  }
  
  @ClassDiscription(name="特别会员预警风险率")
  public String getWarnTh_log()
  {
    return formatDecimals(this.warnTh.multiply(new BigDecimal(100)), NumberDigits.SPECIALWARNTH - 2) + "%";
  }
  
  public BigDecimal getWarnTh()
  {
    return formatDecimals(this.warnTh, NumberDigits.SPECIALWARNTH);
  }
  
  public void setWarnTh(BigDecimal warnTh)
  {
    this.warnTh = warnTh;
  }
  
  public BigDecimal getWarnTh_v()
  {
    return formatDecimals(this.warnTh.multiply(new BigDecimal(100)), 
      NumberDigits.SPECIALWARNTH - 2);
  }
  
  public BigDecimal getFrozenTh_v()
  {
    return formatDecimals(this.frozenTh.multiply(new BigDecimal(100)), NumberDigits.FROZENTH - 2);
  }
  
  public void setFrozenTh_v(BigDecimal frozenTh_v)
  {
    this.frozenTh = frozenTh_v.divide(new BigDecimal(100));
  }
  
  @ClassDiscription(name="特别会员冻结风险率")
  public String getFrozenTh_log()
  {
    return formatDecimals(this.frozenTh.multiply(new BigDecimal(100)), NumberDigits.FROZENTH - 2) + "%";
  }
  
  public BigDecimal getFrozenTh()
  {
    return formatDecimals(this.frozenTh, NumberDigits.FROZENTH);
  }
  
  public void setFrozenTh(BigDecimal frozenTh)
  {
    this.frozenTh = frozenTh;
  }
  
  public void setWarnTh_v(BigDecimal warnTh_v)
  {
    this.warnTh = warnTh_v.divide(new BigDecimal(100));
  }
  
  @ClassDiscription(name="头寸预警阈值")
  public Integer getHoldWarnTh()
  {
    return this.holdWarnTh;
  }
  
  public void setHoldWarnTh(Integer holdWarnTh)
  {
    this.holdWarnTh = holdWarnTh;
  }
  
  public String getId()
  {
    return this.firmId;
  }
  
  @ClassDiscription(name="特别会员名称")
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  public SpecialThreshold(String firmId, String memberName, BigDecimal minRiskFund, BigDecimal warnTh, int holdWarnTh)
  {
    this.firmId = firmId;
    this.memberName = memberName;
    this.minRiskFund = minRiskFund;
    this.warnTh = warnTh;
    this.holdWarnTh = Integer.valueOf(holdWarnTh);
  }
  
  public SpecialThreshold(String firmId, String memberName, BigDecimal minRiskFund, BigDecimal warnTh, int holdWarnTh, BigDecimal frozenTh)
  {
    this.firmId = firmId;
    this.memberName = memberName;
    this.minRiskFund = minRiskFund;
    this.warnTh = warnTh;
    this.holdWarnTh = Integer.valueOf(holdWarnTh);
    this.frozenTh = frozenTh;
  }
  
  public SpecialThreshold() {}
  
  public void setPrimary(String primary)
  {
    this.firmId = primary;
  }
}
