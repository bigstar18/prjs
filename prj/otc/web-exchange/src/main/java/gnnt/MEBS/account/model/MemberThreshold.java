package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.config.constant.NumberDigits;
import java.math.BigDecimal;

public class MemberThreshold
  extends Clone
{
  private String memberNo;
  private String memberName;
  private BigDecimal minRiskFund;
  private BigDecimal warnTh;
  private BigDecimal frozenTh;
  private BigDecimal m_SelfTradeRate;
  private Integer netHoldWarnTh;
  private BigDecimal cu_F_WarnTh;
  private BigDecimal m_CU_BalanceSum;
  
  @ClassDiscription(name="客户总权益")
  public BigDecimal getM_CU_BalanceSum()
  {
    return this.m_CU_BalanceSum;
  }
  
  public void setM_CU_BalanceSum(BigDecimal m_CU_BalanceSum)
  {
    this.m_CU_BalanceSum = m_CU_BalanceSum;
  }
  
  @ClassDiscription(name="净头寸预警阈值")
  public Integer getNetHoldWarnTh()
  {
    return this.netHoldWarnTh;
  }
  
  public void setNetHoldWarnTh(Integer netHoldWarnTh)
  {
    this.netHoldWarnTh = netHoldWarnTh;
  }
  
  @ClassDiscription(name="客户资金预警阈值")
  public BigDecimal getCu_F_WarnTh()
  {
    return formatDecimals(this.cu_F_WarnTh, NumberDigits.CU_F_WARNTH);
  }
  
  public void setCu_F_WarnTh(BigDecimal cu_F_WarnTh)
  {
    this.cu_F_WarnTh = cu_F_WarnTh;
  }
  
  @ClassDiscription(name="会员编号", key=true, keyWord=true)
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  @ClassDiscription(name="会员名称")
  public String getMemberName()
  {
    return this.memberName;
  }
  
  public void setMemberName(String memberName)
  {
    this.memberName = memberName;
  }
  
  @ClassDiscription(name="会员出金阈值")
  public BigDecimal getMinRiskFund()
  {
    return this.minRiskFund;
  }
  
  public void setMinRiskFund(BigDecimal minRiskFund)
  {
    this.minRiskFund = minRiskFund;
  }
  
  @ClassDiscription(name="会员预警风险率")
  public String getWarnTh_log()
  {
    return formatDecimals(this.warnTh.multiply(new BigDecimal(100)), NumberDigits.WARNTH - 2) + "%";
  }
  
  public BigDecimal getWarnTh()
  {
    return formatDecimals(this.warnTh, NumberDigits.WARNTH);
  }
  
  public void setWarnTh(BigDecimal warnTh)
  {
    this.warnTh = warnTh;
  }
  
  public BigDecimal getWarnTh_v()
  {
    return formatDecimals(this.warnTh.multiply(new BigDecimal(100)), NumberDigits.WARNTH - 2);
  }
  
  public void setWarnTh_v(BigDecimal warnTh_v)
  {
    this.warnTh = warnTh_v.divide(new BigDecimal(100));
  }
  
  @ClassDiscription(name="会员冻结风险率")
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
  
  public BigDecimal getFrozenTh_v()
  {
    return formatDecimals(this.frozenTh.multiply(new BigDecimal(100)), NumberDigits.FROZENTH - 2);
  }
  
  public void setFrozenTh_v(BigDecimal frozenTh_v)
  {
    this.frozenTh = frozenTh_v.divide(new BigDecimal(100));
  }
  
  @ClassDiscription(name="会员非客户头寸交易比例")
  public String getM_SelfTradeRate_log()
  {
    return formatDecimals(this.m_SelfTradeRate.multiply(new BigDecimal(100)), NumberDigits.M_SELF - 2) + "%";
  }
  
  public BigDecimal getM_SelfTradeRate()
  {
    return formatDecimals(this.m_SelfTradeRate, NumberDigits.M_SELF);
  }
  
  public void setM_SelfTradeRate(BigDecimal selfTradeRate)
  {
    this.m_SelfTradeRate = selfTradeRate;
  }
  
  public BigDecimal getM_SelfTradeRate_v()
  {
    return formatDecimals(this.m_SelfTradeRate.multiply(new BigDecimal(100)), NumberDigits.M_SELF - 2);
  }
  
  public void setM_SelfTradeRate_v(BigDecimal selfTradeRate_v)
  {
    this.m_SelfTradeRate = selfTradeRate_v.divide(new BigDecimal(100));
  }
  
  public String getId()
  {
    return this.memberNo;
  }
  
  public MemberThreshold(String memberNo, String memberName, BigDecimal minRiskFund, BigDecimal warnTh, BigDecimal frozenTh, BigDecimal m_SelfTradeRate, Integer netHoldWarnTh, BigDecimal cu_F_WarnTh, BigDecimal m_CU_BalanceSum)
  {
    this.memberNo = memberNo;
    this.memberName = memberName;
    this.minRiskFund = minRiskFund;
    this.warnTh = warnTh;
    this.frozenTh = frozenTh;
    this.m_SelfTradeRate = m_SelfTradeRate;
    this.netHoldWarnTh = netHoldWarnTh;
    this.cu_F_WarnTh = cu_F_WarnTh;
    this.m_CU_BalanceSum = m_CU_BalanceSum;
  }
  
  public MemberThreshold() {}
  
  public void setPrimary(String primary)
  {
    this.memberNo = primary;
  }
}
