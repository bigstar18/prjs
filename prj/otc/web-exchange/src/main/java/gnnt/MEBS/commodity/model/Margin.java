package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import gnnt.MEBS.config.constant.NumberDigits;
import java.math.BigDecimal;

public class Margin
  extends SpecialSet
{
  private String commodityId;
  private String commodityName;
  private String firmId;
  private String firmName;
  private Integer marginAlgr;
  private BigDecimal tradeMargin;
  private BigDecimal settleMargin;
  private BigDecimal holidayMargin;
  
  public Margin() {}
  
  public Margin(String commodityId, String commodityName, String firmId, int marginAlgr, BigDecimal tradeMargin, BigDecimal settleMargin, BigDecimal holidayMargin)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.firmId = firmId;
    this.marginAlgr = Integer.valueOf(marginAlgr);
    this.tradeMargin = tradeMargin;
    this.settleMargin = settleMargin;
    this.holidayMargin = holidayMargin;
  }
  
  public Margin(String commodityId, String commodityName, String firmId, int marginAlgr, BigDecimal tradeMargin)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.firmId = firmId;
    this.marginAlgr = Integer.valueOf(marginAlgr);
    this.tradeMargin = tradeMargin;
  }
  
  public Margin(String commodityId, String commodityName, String firmId, String firmName, int marginAlgr, BigDecimal tradeMargin, BigDecimal settleMargin, BigDecimal holidayMargin)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.firmId = firmId;
    this.firmName = firmName;
    this.marginAlgr = Integer.valueOf(marginAlgr);
    this.tradeMargin = tradeMargin;
    this.settleMargin = settleMargin;
    this.holidayMargin = holidayMargin;
  }
  
  public Margin(String commodityId, String commodityName, String firmId, String firmName, int marginAlgr, BigDecimal tradeMargin)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.firmId = firmId;
    this.firmName = firmName;
    this.marginAlgr = Integer.valueOf(marginAlgr);
    this.tradeMargin = tradeMargin;
  }
  
  @ClassDiscription(name="商品代码:", key=true, keyWord=true)
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  @ClassDiscription(name="商品名称:", key=true, keyWord=true)
  public String getCommodityName()
  {
    return this.commodityName;
  }
  
  public void setCommodityName(String commodityName)
  {
    this.commodityName = commodityName;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  @ClassDiscription(name="保证金算法:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="比例"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="固定值")})
  public Integer getMarginAlgr()
  {
    return this.marginAlgr;
  }
  
  public void setMarginAlgr(Integer marginAlgr)
  {
    this.marginAlgr = marginAlgr;
  }
  
  public Integer getMarginAlgr_v()
  {
    return this.marginAlgr;
  }
  
  public void setMarginAlgr_v(Integer marginAlgr)
  {
    if ((this.algorithmArray[0] == 0) && 
      (marginAlgr.intValue() == 1)) {
      this.tradeMargin = this.tradeMargin.divide(new BigDecimal(100));
    }
    if ((this.algorithmArray[1] == 0) && 
      (marginAlgr.intValue() == 1)) {
      this.settleMargin = formatDecimals(this.settleMargin.divide(new BigDecimal(100)), NumberDigits.SETTLEMARGIN);
    }
    if ((this.algorithmArray[2] == 0) && 
      (marginAlgr.intValue() == 1)) {
      this.holidayMargin = formatDecimals(this.holidayMargin.divide(new BigDecimal(100)), NumberDigits.HOLIDAYMARGIN);
    }
    this.marginAlgr = marginAlgr;
  }
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
  
  public BigDecimal getTradeMargin()
  {
    return this.tradeMargin;
  }
  
  public void setTradeMargin(BigDecimal tradeMargin)
  {
    this.tradeMargin = tradeMargin;
  }
  
  public BigDecimal getTradeMargin_v()
  {
    BigDecimal tradeMargin_v = null;
    if (this.marginAlgr != null) {
      if (this.marginAlgr.intValue() == 2) {
        tradeMargin_v = getTradeMargin();
      } else {
        tradeMargin_v = formatDecimals(this.tradeMargin.multiply(new BigDecimal(100)), NumberDigits.TRADEMARGIN - 2);
      }
    }
    return tradeMargin_v;
  }
  
  public void setTradeMargin_v(BigDecimal tradeMargin_v)
  {
    if (this.marginAlgr != null)
    {
      if (this.marginAlgr.intValue() == 2) {
        this.tradeMargin = tradeMargin_v;
      } else {
        this.tradeMargin = tradeMargin_v.divide(new BigDecimal(100));
      }
    }
    else
    {
      this.algorithmArray[0] = false;
      this.tradeMargin = tradeMargin_v;
    }
  }
  
  @ClassDiscription(name="即市占用/冻结:")
  public String getTradeMargin_log()
  {
    String tradeMargin_l = "";
    if (this.marginAlgr.intValue() == 2) {
      tradeMargin_l = getTradeMargin().toString();
    } else {
      tradeMargin_l = formatDecimals(this.tradeMargin.multiply(new BigDecimal(100)), NumberDigits.TRADEMARGIN - 2).toString() + "%";
    }
    return tradeMargin_l;
  }
  
  public BigDecimal getSettleMargin()
  {
    return formatDecimals(this.settleMargin, NumberDigits.SETTLEMARGIN);
  }
  
  public void setSettleMargin(BigDecimal settleMargin)
  {
    this.settleMargin = settleMargin;
  }
  
  public BigDecimal getSettleMargin_v()
  {
    BigDecimal settleMargin_v = null;
    if ((this.marginAlgr != null) && 
      (this.marginAlgr.intValue() == 2)) {
      settleMargin_v = getSettleMargin();
    }
    return settleMargin_v;
  }
  
  public void setSettleMargin_v(BigDecimal settleMargin_v)
  {
    if (this.marginAlgr != null)
    {
      if (this.marginAlgr.intValue() == 2) {
        this.settleMargin = settleMargin_v;
      } else {
        this.settleMargin = settleMargin_v.divide(new BigDecimal(100));
      }
    }
    else
    {
      this.algorithmArray[1] = false;
      this.settleMargin = settleMargin_v;
    }
  }
  
  public String getSettleMargin_log()
  {
    String settleMargin_log = "";
    if (this.marginAlgr.intValue() == 2) {
      settleMargin_log = getSettleMargin().toString();
    } else {
      settleMargin_log = formatDecimals(this.settleMargin.multiply(new BigDecimal(100)), NumberDigits.SETTLEMARGIN - 2).toString() + "%";
    }
    return settleMargin_log;
  }
  
  public BigDecimal getHolidayMargin()
  {
    return formatDecimals(this.holidayMargin, NumberDigits.HOLIDAYMARGIN);
  }
  
  public void setHolidayMargin(BigDecimal holidayMargin)
  {
    this.holidayMargin = holidayMargin;
  }
  
  public BigDecimal getHolidayMargin_v()
  {
    BigDecimal holidayMargin_v = null;
    if ((this.marginAlgr != null) && 
      (this.marginAlgr.intValue() == 2)) {
      holidayMargin_v = getHolidayMargin();
    }
    return holidayMargin_v;
  }
  
  public void setHolidayMargin_v(BigDecimal holidayMargin_v)
  {
    if (this.marginAlgr != null)
    {
      if (this.marginAlgr.intValue() == 2) {
        this.holidayMargin = holidayMargin_v;
      } else {
        this.holidayMargin = holidayMargin_v.divide(new BigDecimal(100));
      }
    }
    else
    {
      this.algorithmArray[2] = false;
      this.holidayMargin = holidayMargin_v;
    }
  }
  
  public String getHolidayMargin_log()
  {
    String holidayMargin_log = "";
    if (this.marginAlgr.intValue() == 2) {
      holidayMargin_log = getHolidayMargin().toString();
    } else {
      holidayMargin_log = formatDecimals(this.holidayMargin.multiply(new BigDecimal(100)), NumberDigits.HOLIDAYMARGIN - 2).toString() + "%";
    }
    return holidayMargin_log;
  }
  
  @ClassDiscription(name="名称:", key=true, keyWord=true, isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Customer", value="客户默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Member", value="会员默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_S_Member", value="特别会员默认")})
  public String getFirmName()
  {
    if (this.firmName != null) {
      return this.firmName;
    }
    return this.firmId;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }
}
