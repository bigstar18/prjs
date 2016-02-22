package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import gnnt.MEBS.config.constant.NumberDigits;
import java.math.BigDecimal;

public class CommodityFee
  extends Clone
{
  private String commodityId;
  private String commodityName;
  private String firmId;
  private Integer feeAlgr;
  private BigDecimal feeRate;
  private Integer feeMode;
  private BigDecimal mkt_FeeRate;
  
  public BigDecimal getMkt_FeeRate()
  {
    return this.mkt_FeeRate;
  }
  
  public void setMkt_FeeRate(BigDecimal mkt_FeeRate)
  {
    this.mkt_FeeRate = mkt_FeeRate;
  }
  
  public BigDecimal getMkt_FeeRate_v()
  {
    BigDecimal mkt_FeeRate_v;
    BigDecimal mkt_FeeRate_v;
    if (this.feeAlgr.intValue() == 2) {
      mkt_FeeRate_v = getMkt_FeeRate();
    } else {
      mkt_FeeRate_v = formatDecimals(this.mkt_FeeRate.multiply(new BigDecimal(100)), NumberDigits.MKT_FEERATE - 4);
    }
    return mkt_FeeRate_v;
  }
  
  public void setMkt_FeeRate_v(BigDecimal mkt_FeeRate_v)
  {
    if (this.feeAlgr != null)
    {
      if (this.feeAlgr.intValue() == 2) {
        this.mkt_FeeRate = mkt_FeeRate_v;
      } else {
        this.mkt_FeeRate = mkt_FeeRate_v.divide(new BigDecimal(100));
      }
    }
    else
    {
      this.algorithmArray[1] = false;
      this.mkt_FeeRate = mkt_FeeRate_v;
    }
  }
  
  @ClassDiscription(name="商品代码:", key=true, keyWord=true)
  public String getCommodityId()
  {
    return this.commodityId;
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
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  @ClassDiscription(name="名称:", key=true, keyWord=true, isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Customer", value="客户默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_Member", value="会员默认"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="Def_S_Member", value="特别会员默认")})
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  @ClassDiscription(name="手续费算法:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="比例"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="固定值")})
  public Integer getFeeAlgr()
  {
    return this.feeAlgr;
  }
  
  public void setFeeAlgr(Integer feeAlgr)
  {
    this.feeAlgr = feeAlgr;
  }
  
  public Integer getFeeAlgr_v()
  {
    return this.feeAlgr;
  }
  
  public void setFeeAlgr_v(Integer feeAlgr)
  {
    if ((this.algorithmArray[0] == 0) && 
      (feeAlgr.intValue() == 1)) {
      this.feeRate = this.feeRate.divide(new BigDecimal(100));
    }
    if ((this.algorithmArray[1] == 0) && 
      (feeAlgr.intValue() == 1)) {
      this.mkt_FeeRate = this.mkt_FeeRate.divide(new BigDecimal(100));
    }
    this.feeAlgr = feeAlgr;
  }
  
  public BigDecimal getFeeRate()
  {
    return this.feeRate;
  }
  
  public void setFeeRate(BigDecimal feeRate)
  {
    this.feeRate = feeRate;
  }
  
  public BigDecimal getFeeRate_v()
  {
    BigDecimal feeRate_v;
    BigDecimal feeRate_v;
    if (this.feeAlgr.intValue() == 2) {
      feeRate_v = getFeeRate();
    } else {
      feeRate_v = formatDecimals(this.feeRate.multiply(new BigDecimal(100)), NumberDigits.FEERATE - 4);
    }
    return feeRate_v;
  }
  
  public void setFeeRate_v(BigDecimal feeRate_v)
  {
    if (this.feeAlgr != null)
    {
      if (this.feeAlgr.intValue() == 2) {
        this.feeRate = feeRate_v;
      } else {
        this.feeRate = feeRate_v.divide(new BigDecimal(100));
      }
    }
    else
    {
      this.algorithmArray[0] = false;
      this.feeRate = feeRate_v;
    }
  }
  
  @ClassDiscription(name="手续费系数:")
  public String getFeeRate_log()
  {
    String feeRate_l = "";
    if (this.feeAlgr.intValue() == 2) {
      feeRate_l = getFeeRate().toString();
    } else {
      feeRate_l = formatDecimals(this.feeRate.multiply(new BigDecimal(100)), NumberDigits.FEERATE - 4).toString() + "%";
    }
    return feeRate_l;
  }
  
  @ClassDiscription(name="交易所收取手续费:")
  public String getMkt_FeeRate_log()
  {
    String mkt_FeeRate_log = "";
    if (this.feeAlgr.intValue() == 2) {
      mkt_FeeRate_log = getMkt_FeeRate().toString();
    } else {
      mkt_FeeRate_log = formatDecimals(this.mkt_FeeRate.multiply(new BigDecimal(100)), NumberDigits.MKT_FEERATE - 4).toString() + "%";
    }
    return mkt_FeeRate_log;
  }
  
  @ClassDiscription(name="收取方式:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="单边建仓"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="单边平仓"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="3", value="双边建平")})
  public Integer getFeeMode()
  {
    return this.feeMode;
  }
  
  public void setFeeMode(Integer feeMode)
  {
    this.feeMode = feeMode;
  }
  
  public CommodityFee(String commodityId, String commodityName, String firmId, int feeAlgr, BigDecimal feeRate, int feeMode, BigDecimal mkt_FeeRate)
  {
    this.commodityId = commodityId;
    this.commodityName = commodityName;
    this.firmId = firmId;
    this.feeAlgr = Integer.valueOf(feeAlgr);
    this.feeRate = feeRate;
    this.feeMode = Integer.valueOf(feeMode);
    this.mkt_FeeRate = mkt_FeeRate;
  }
  
  public CommodityFee() {}
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
