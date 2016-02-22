package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import gnnt.MEBS.config.constant.NumberDigits;
import java.math.BigDecimal;

public class TCDelayFee
  extends Clone
{
  private String commodityId;
  private String firmId;
  private String firmName;
  private Long stepNo;
  private BigDecimal delayFee;
  private Integer feeAlgr_v;
  private BigDecimal mkt_delayFeeRate;
  
  @ClassDiscription(name="延期费算法:", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="比例"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="固定值")})
  public Integer getFeeAlgr_v()
  {
    return this.feeAlgr_v;
  }
  
  public void setFeeAlgr_v(Integer feeAlgr_v)
  {
    this.feeAlgr_v = feeAlgr_v;
  }
  
  public String getId()
  {
    return null;
  }
  
  @ClassDiscription(name="会员交易商Id", key=true, keyWord=true)
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  @ClassDiscription(name="阶梯阶号")
  public Long getStepNo()
  {
    return this.stepNo;
  }
  
  public void setStepNo(Long stepNo)
  {
    this.stepNo = stepNo;
  }
  
  public BigDecimal getDelayFee()
  {
    return this.delayFee;
  }
  
  public void setDelayFee(BigDecimal delayFee)
  {
    this.delayFee = delayFee;
  }
  
  @ClassDiscription(name="商品代码", key=true, keyWord=true)
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }
  
  public void setPrimary(String primary) {}
  
  @ClassDiscription(name="交易商名称")
  public String getFirmName()
  {
    return this.firmName;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }
  
  @ClassDiscription(name="延期费率")
  public String getDelayFee_log()
  {
    return formatDecimals(getDelayFee().multiply(new BigDecimal(100)), NumberDigits.DELAYFEE - 4) + "%";
  }
  
  public BigDecimal getDelayFee_v()
  {
    return formatDecimals(getDelayFee().multiply(new BigDecimal(100)), NumberDigits.DELAYFEE - 4);
  }
  
  public void setDelayFee_v(BigDecimal delayFee)
  {
    this.delayFee = delayFee.divide(new BigDecimal(100));
  }
  
  public BigDecimal getMkt_delayFeeRate()
  {
    return this.mkt_delayFeeRate;
  }
  
  public void setMkt_delayFeeRate(BigDecimal mkt_delayFeeRate)
  {
    this.mkt_delayFeeRate = mkt_delayFeeRate;
  }
  
  @ClassDiscription(name="市场保留延期费系数")
  public String getMkt_delayFeeRate_log()
  {
    return formatDecimals(getMkt_delayFeeRate().multiply(new BigDecimal(100)), NumberDigits.DELAYFEE - 4) + "%";
  }
  
  public BigDecimal getMkt_delayFeeRate_v()
  {
    return formatDecimals(getMkt_delayFeeRate().multiply(new BigDecimal(100)), NumberDigits.DELAYFEE - 4);
  }
  
  public void setMkt_delayFeeRate_v(BigDecimal mkt_delayFeeRate)
  {
    this.mkt_delayFeeRate = mkt_delayFeeRate.divide(new BigDecimal(100));
  }
}
