package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import gnnt.MEBS.config.constant.NumberDigits;
import java.math.BigDecimal;

public class QuotePoint
  extends SpecialSet
{
  private String commodityId;
  private String commodityName;
  private String m_firmId;
  private Integer quotePointAlgr;
  private BigDecimal quotePointB;
  private BigDecimal quotePointS;
  private String firmName;
  
  @ClassDiscription(name="商品代码:", key=true, keyWord=true)
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  @ClassDiscription(name="商品名称:")
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  public String getM_firmId()
  {
    return this.m_firmId;
  }
  
  public void setM_firmId(String firmId)
  {
    this.m_firmId = firmId;
  }
  
  public QuotePoint(String commodityId, String commodityName, String firmId, BigDecimal quotePointB, BigDecimal quotePointS, int quotePointAlgr)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.m_firmId = firmId;
    this.quotePointB = quotePointB;
    this.quotePointS = quotePointS;
    this.quotePointAlgr = Integer.valueOf(quotePointAlgr);
  }
  
  public QuotePoint(String commodityId, String commodityName, String firmId, String firmName, BigDecimal quotePointB, BigDecimal quotePointS, int quotePointAlgr)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.m_firmId = firmId;
    this.firmName = firmName;
    this.quotePointB = quotePointB;
    this.quotePointS = quotePointS;
    this.quotePointAlgr = Integer.valueOf(quotePointAlgr);
  }
  
  public QuotePoint() {}
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
  
  public void setQuotePointAlgr(Integer quotePointAlgr)
  {
    this.quotePointAlgr = quotePointAlgr;
  }
  
  public Integer getQuotePointAlgr_v()
  {
    return this.quotePointAlgr;
  }
  
  @ClassDiscription(name="报价点差算法:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="比例"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="固定值")})
  public Integer getQuotePointAlgr()
  {
    return this.quotePointAlgr;
  }
  
  public void setQuotePointAlgr_v(Integer quotePointAlgr)
  {
    if ((this.algorithmArray[0] == 0) && 
      (quotePointAlgr.intValue() == 1)) {
      this.quotePointB = this.quotePointB.divide(new BigDecimal(100));
    }
    if ((this.algorithmArray[1] == 0) && 
      (quotePointAlgr.intValue() == 1)) {
      this.quotePointS = this.quotePointS.divide(new BigDecimal(100));
    }
    this.quotePointAlgr = quotePointAlgr;
  }
  
  public BigDecimal getQuotePointB()
  {
    return this.quotePointB;
  }
  
  public void setQuotePointB(BigDecimal quotePointB)
  {
    this.quotePointB = quotePointB;
  }
  
  public BigDecimal getQuotePointB_v()
  {
    BigDecimal quotePointB_v;
    BigDecimal quotePointB_v;
    if (this.quotePointAlgr.intValue() == 2) {
      quotePointB_v = getQuotePointB();
    } else {
      quotePointB_v = formatDecimals(getQuotePointB().multiply(new BigDecimal(100)), NumberDigits.QUOTEPOINT_B_RATE - 4);
    }
    return quotePointB_v;
  }
  
  public void setQuotePointB_v(BigDecimal quotePointB_v)
  {
    if (this.quotePointAlgr != null)
    {
      if (this.quotePointAlgr.intValue() == 2) {
        this.quotePointB = quotePointB_v;
      } else {
        this.quotePointB = quotePointB_v.divide(new BigDecimal(100));
      }
    }
    else
    {
      this.algorithmArray[0] = false;
      this.quotePointB = quotePointB_v;
    }
  }
  
  @ClassDiscription(name="买报价点差:")
  public String getQuotePointB_log()
  {
    String quotePointB_log = "";
    if (this.quotePointAlgr.intValue() == 2) {
      quotePointB_log = getQuotePointB().toString();
    } else {
      quotePointB_log = formatDecimals(getQuotePointB().multiply(new BigDecimal(100)), NumberDigits.QUOTEPOINT_B_RATE - 4).toString() + "%";
    }
    return quotePointB_log;
  }
  
  public BigDecimal getQuotePointS()
  {
    return this.quotePointS;
  }
  
  public void setQuotePointS(BigDecimal quotePointS)
  {
    this.quotePointS = quotePointS;
  }
  
  public BigDecimal getQuotePointS_v()
  {
    BigDecimal quotePointS_v;
    BigDecimal quotePointS_v;
    if (this.quotePointAlgr.intValue() == 2) {
      quotePointS_v = getQuotePointS();
    } else {
      quotePointS_v = getQuotePointS().multiply(new BigDecimal(100));
    }
    return quotePointS_v;
  }
  
  public void setQuotePointS_v(BigDecimal quotePointS_v)
  {
    if (this.quotePointAlgr != null)
    {
      if (this.quotePointAlgr.intValue() == 2) {
        this.quotePointS = quotePointS_v;
      } else {
        this.quotePointS = quotePointS_v.divide(new BigDecimal(100));
      }
    }
    else
    {
      this.algorithmArray[1] = false;
      this.quotePointS = quotePointS_v;
    }
  }
  
  public String getQuotePointS_log()
  {
    String quotePointS_log = "";
    if (this.quotePointAlgr.intValue() == 2) {
      quotePointS_log = getQuotePointS().toString();
    } else {
      quotePointS_log = formatDecimals(getQuotePointS().multiply(new BigDecimal(100)), NumberDigits.QUOTEPOINT_S_RATE - 4).toString() + "%";
    }
    return quotePointS_log;
  }
  
  @ClassDiscription(name="名称:", key=true, keyWord=true, isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Customer", value="客户默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Member", value="会员默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_S_Member", value="特别会员默认")})
  public String getFirmName()
  {
    if (this.firmName != null) {
      return this.firmName;
    }
    return this.m_firmId;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }
}
