package gnnt.MEBS.settlement.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import gnnt.MEBS.config.constant.NumberDigits;
import java.math.BigDecimal;

public class QuotePointRunTime
  extends Clone
{
  private String commodityId;
  private String commodityName;
  private String m_FirmId;
  private String firmName;
  private String firmType;
  private Integer quotePoint_B;
  private Integer quotePoint_S;
  private BigDecimal quotePoint_B_RMB;
  private BigDecimal quotePoint_S_RMB;
  
  @ClassDiscription(name="商品代码", key=true, keyWord=true)
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  @ClassDiscription(name="商品名称", keyWord=true)
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  @ClassDiscription(name="会员", key=true, keyWord=true)
  public String getM_FirmId()
  {
    return this.m_FirmId;
  }
  
  public void setM_FirmId(String m_FirmId)
  {
    this.m_FirmId = m_FirmId;
  }
  
  @ClassDiscription(name="买报价点差")
  public Integer getQuotePoint_B()
  {
    return this.quotePoint_B;
  }
  
  public void setQuotePoint_B(Integer quotePoint_B)
  {
    this.quotePoint_B = quotePoint_B;
  }
  
  @ClassDiscription(name="卖报价点差")
  public Integer getQuotePoint_S()
  {
    return this.quotePoint_S;
  }
  
  public void setQuotePoint_S(Integer quotePoint_S)
  {
    this.quotePoint_S = quotePoint_S;
  }
  
  @ClassDiscription(name="买报价点差金额")
  public BigDecimal getQuotePoint_B_RMB()
  {
    return formatDecimals(this.quotePoint_B_RMB, NumberDigits.RMB);
  }
  
  public void setQuotePoint_B_RMB(BigDecimal quotePoint_B_RMB)
  {
    this.quotePoint_B_RMB = quotePoint_B_RMB;
  }
  
  @ClassDiscription(name="卖报价点差金额")
  public BigDecimal getQuotePoint_S_RMB()
  {
    return formatDecimals(this.quotePoint_S_RMB, NumberDigits.RMB);
  }
  
  public void setQuotePoint_S_RMB(BigDecimal quotePoint_S_RMB)
  {
    this.quotePoint_S_RMB = quotePoint_S_RMB;
  }
  
  public QuotePointRunTime() {}
  
  public QuotePointRunTime(String m_FirmId)
  {
    this.m_FirmId = m_FirmId;
  }
  
  public QuotePointRunTime(String commodityId, String commodityName, String m_FirmId, String firmName, String firmType, Integer quotePoint_B, Integer quotePoint_S, BigDecimal quotePoint_B_RMB, BigDecimal quotePoint_S_RMB)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.m_FirmId = m_FirmId;
    this.firmName = firmName;
    this.firmType = firmType;
    this.quotePoint_B = quotePoint_B;
    this.quotePoint_S = quotePoint_S;
    this.quotePoint_B_RMB = quotePoint_B_RMB;
    this.quotePoint_S_RMB = quotePoint_S_RMB;
  }
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
  
  @ClassDiscription(name="会员名称", keyWord=true)
  public String getFirmName()
  {
    return this.firmName;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }
  
  @ClassDiscription(name="会员类型", key=true, keyWord=true, isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="M", value="综合会员"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="S", value="特别会员")})
  public String getFirmType()
  {
    return this.firmType;
  }
  
  public void setFirmType(String firmType)
  {
    this.firmType = firmType;
  }
}
