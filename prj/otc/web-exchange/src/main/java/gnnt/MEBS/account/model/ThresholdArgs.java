package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import gnnt.MEBS.config.constant.NumberDigits;
import java.math.BigDecimal;

public class ThresholdArgs
  extends Clone
{
  private String marketCode;
  private BigDecimal c_WarnTh;
  private BigDecimal c_ForceTh;
  private BigDecimal m_WarnTh;
  private BigDecimal m_FrozenTh;
  private BigDecimal m_SelfTradeRate;
  private BigDecimal cm_MinRiskFund;
  private BigDecimal sm_MinRiskFund;
  private BigDecimal sm_WarnTh;
  private BigDecimal sm_FrozenTh;
  private BigDecimal bm_MinRiskFund;
  private String oc_ForceClose;
  private String mchangeStatus;
  private Integer monitorRefresh;
  
  @ClassDiscription(name="强平开关", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="O", value="开"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="C", value="关")})
  public String getOc_ForceClose()
  {
    return this.oc_ForceClose;
  }
  
  public void setOc_ForceClose(String oc_ForceClose)
  {
    this.oc_ForceClose = oc_ForceClose;
  }
  
  public String getMchangeStatus()
  {
    return this.mchangeStatus;
  }
  
  public void setMchangeStatus(String mchangeStatus)
  {
    this.mchangeStatus = mchangeStatus;
  }
  
  @ClassDiscription(name="特别会员冻结风险率")
  public String getSm_FrozenTh_log()
  {
    String ss = 
      formatDecimals(this.sm_FrozenTh.multiply(new BigDecimal(100)), NumberDigits.SM_FROZENTH - 2) + "%";
    return ss;
  }
  
  public BigDecimal getSm_FrozenTh()
  {
    return this.sm_FrozenTh;
  }
  
  public void setSm_FrozenTh(BigDecimal sm_FrozenTh)
  {
    this.sm_FrozenTh = sm_FrozenTh;
  }
  
  public BigDecimal getSm_FrozenTh_v()
  {
    return formatDecimals(this.sm_FrozenTh.multiply(new BigDecimal(100)), 
      NumberDigits.SM_FROZENTH - 2);
  }
  
  public void setSm_FrozenTh_v(BigDecimal frozenTh_v)
  {
    this.sm_FrozenTh = frozenTh_v.divide(new BigDecimal(100));
  }
  
  @ClassDiscription(name="经纪会员最低出金阈值")
  public BigDecimal getBm_MinRiskFund()
  {
    return formatDecimals(this.bm_MinRiskFund, NumberDigits.BM_MINRISKFUND);
  }
  
  public void setBm_MinRiskFund(BigDecimal bm_MinRiskFund)
  {
    this.bm_MinRiskFund = bm_MinRiskFund;
  }
  
  @ClassDiscription(name="默认风险阈值", keyWord=true)
  public String getKeyWord()
  {
    return "";
  }
  
  @ClassDiscription(name="客户预警风险率")
  public String getC_WarnTh_log()
  {
    return 
      formatDecimals(this.c_WarnTh.multiply(new BigDecimal(100)), NumberDigits.C_WARNTH - 2) + "%";
  }
  
  public BigDecimal getC_WarnTh_v()
  {
    return formatDecimals(this.c_WarnTh.multiply(new BigDecimal(100)), 
      NumberDigits.C_WARNTH - 2);
  }
  
  public BigDecimal getC_WarnTh()
  {
    return this.c_WarnTh;
  }
  
  public void setC_WarnTh(BigDecimal warnTh)
  {
    this.c_WarnTh = warnTh;
  }
  
  public void setC_WarnTh_v(BigDecimal warnTh_v)
  {
    this.c_WarnTh = warnTh_v.divide(new BigDecimal(100));
  }
  
  @ClassDiscription(name="客户强平风险率")
  public String getC_ForceTh_log()
  {
    String ss = formatDecimals(this.c_ForceTh, NumberDigits.C_FORCETH - 2) + "%";
    return ss;
  }
  
  public BigDecimal getC_ForceTh()
  {
    return this.c_ForceTh;
  }
  
  public void setC_ForceTh(BigDecimal forceTh)
  {
    this.c_ForceTh = forceTh;
  }
  
  public BigDecimal getC_ForceTh_v()
  {
    return formatDecimals(this.c_ForceTh.multiply(new BigDecimal(100)), 
      NumberDigits.C_FORCETH - 2);
  }
  
  public void setC_ForceTh_v(BigDecimal forceTh_v)
  {
    this.c_ForceTh = forceTh_v.divide(new BigDecimal(100));
  }
  
  @ClassDiscription(name="会员预警风险率")
  public String getM_WarnTh_log()
  {
    String ss = formatDecimals(this.m_WarnTh, NumberDigits.M_WARNTH - 2) + "%";
    return ss;
  }
  
  public BigDecimal getM_WarnTh()
  {
    return this.m_WarnTh;
  }
  
  public void setM_WarnTh(BigDecimal warnTh)
  {
    this.m_WarnTh = warnTh;
  }
  
  public BigDecimal getM_WarnTh_v()
  {
    return formatDecimals(this.m_WarnTh.multiply(new BigDecimal(100)), 
      NumberDigits.M_WARNTH - 2);
  }
  
  public void setM_WarnTh_v(BigDecimal warnTh_v)
  {
    this.m_WarnTh = warnTh_v.divide(new BigDecimal(100));
  }
  
  @ClassDiscription(name="会员冻结风险率")
  public String getM_FrozenTh_log()
  {
    String ss = formatDecimals(this.m_WarnTh, NumberDigits.M_WARNTH - 2) + "%";
    return ss;
  }
  
  public BigDecimal getM_FrozenTh()
  {
    return this.m_FrozenTh;
  }
  
  public void setM_FrozenTh(BigDecimal frozenTh)
  {
    this.m_FrozenTh = frozenTh;
  }
  
  public BigDecimal getM_FrozenTh_v()
  {
    return formatDecimals(this.m_FrozenTh.multiply(new BigDecimal(100)), 
      NumberDigits.M_FROZENTH - 2);
  }
  
  public void setM_FrozenTh_v(BigDecimal frozenTh_v)
  {
    this.m_FrozenTh = frozenTh_v.divide(new BigDecimal(100));
  }
  
  @ClassDiscription(name="会员非客户头寸交易比例")
  public String getM_SelfTradeRate_log()
  {
    String ss = 
      formatDecimals(this.m_SelfTradeRate.multiply(new BigDecimal(100)), NumberDigits.M_SELFTRADERATE - 2) + "%";
    return ss;
  }
  
  public BigDecimal getM_SelfTradeRate()
  {
    return this.m_SelfTradeRate;
  }
  
  public void setM_SelfTradeRate(BigDecimal selfTradeRate)
  {
    this.m_SelfTradeRate = selfTradeRate;
  }
  
  public BigDecimal getM_SelfTradeRate_v()
  {
    return formatDecimals(this.m_SelfTradeRate.multiply(new BigDecimal(100)), 
      NumberDigits.M_SELFTRADERATE - 2);
  }
  
  public void setM_SelfTradeRate_v(BigDecimal selfTradeRate_v)
  {
    this.m_SelfTradeRate = selfTradeRate_v.divide(new BigDecimal(100));
  }
  
  @ClassDiscription(name="特别会员预警阈值")
  public String getSm_WarnTh_log()
  {
    String ss = formatDecimals(this.sm_WarnTh, NumberDigits.SM_WARNTH - 2) + "%";
    return ss;
  }
  
  public BigDecimal getSm_WarnTh()
  {
    return this.sm_WarnTh;
  }
  
  public void setSm_WarnTh(BigDecimal sm_WarnTh)
  {
    this.sm_WarnTh = sm_WarnTh;
  }
  
  public BigDecimal getSm_WarnTh_v()
  {
    return formatDecimals(this.sm_WarnTh.multiply(new BigDecimal(100)), 
      NumberDigits.SM_WARNTH - 2);
  }
  
  public void setSm_WarnTh_v(BigDecimal sm_WarnTh_v)
  {
    this.sm_WarnTh = sm_WarnTh_v.divide(new BigDecimal(100));
  }
  
  public String getId()
  {
    return this.marketCode;
  }
  
  public void setPrimary(String primary)
  {
    this.marketCode = primary;
  }
  
  @ClassDiscription(name="会员最低出金阈值")
  public BigDecimal getCm_MinRiskFund()
  {
    return formatDecimals(this.cm_MinRiskFund, NumberDigits.CM_MINRISKFUND);
  }
  
  public void setCm_MinRiskFund(BigDecimal cm_MinRiskFund)
  {
    this.cm_MinRiskFund = cm_MinRiskFund;
  }
  
  @ClassDiscription(name="特别会员最低出金阈值")
  public BigDecimal getSm_MinRiskFund()
  {
    return formatDecimals(this.sm_MinRiskFund, NumberDigits.SM_MINRISKFUND);
  }
  
  public void setSm_MinRiskFund(BigDecimal sm_MinRiskFund)
  {
    this.sm_MinRiskFund = sm_MinRiskFund;
  }
  
  public String getMarketCode()
  {
    return this.marketCode;
  }
  
  public void setMarketCode(String marketCode)
  {
    this.marketCode = marketCode;
  }
  
  @ClassDiscription(name="交易监控刷新时间值")
  public Integer getMonitorRefresh()
  {
    return this.monitorRefresh;
  }
  
  public void setMonitorRefresh(Integer monitorRefresh)
  {
    this.monitorRefresh = monitorRefresh;
  }
}
