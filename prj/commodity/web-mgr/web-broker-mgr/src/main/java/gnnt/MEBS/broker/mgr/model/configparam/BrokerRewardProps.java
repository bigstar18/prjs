package gnnt.MEBS.broker.mgr.model.configparam;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import java.math.BigDecimal;

public class BrokerRewardProps extends StandardModel
{
  private static final long serialVersionUID = 2323081240287183078L;
  private String brokerId;
  private String commodityId;
  private Integer moduleId;
  private Short rewardType;
  private BigDecimal rewardRate = new BigDecimal("0");
  private BigDecimal firstPayRate = new BigDecimal("0");
  private BigDecimal secondPayRate = new BigDecimal("0");
  private static final BigDecimal rate = new BigDecimal("100");

  public String getBrokerId()
  {
    return this.brokerId;
  }

  public BrokerRewardProps()
  {
  }

  public BrokerRewardProps(String paramString1, String paramString2, Integer paramInteger, Short paramShort, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, BigDecimal paramBigDecimal3)
  {
    this.brokerId = paramString1;
    this.commodityId = paramString2;
    this.moduleId = paramInteger;
    this.rewardType = paramShort;
    this.rewardRate = paramBigDecimal1;
    this.firstPayRate = paramBigDecimal2;
    this.secondPayRate = paramBigDecimal3;
  }

  public void setBrokerId(String paramString)
  {
    this.brokerId = paramString;
  }

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public void setCommodityId(String paramString)
  {
    this.commodityId = paramString;
  }

  public Integer getModuleId()
  {
    return this.moduleId;
  }

  public void setModuleId(Integer paramInteger)
  {
    this.moduleId = paramInteger;
  }

  public Short getRewardType()
  {
    return this.rewardType;
  }

  public void setRewardType(Short paramShort)
  {
    this.rewardType = paramShort;
  }

  public BigDecimal getRewardRate()
  {
    return this.rewardRate;
  }

  public void setRewardRate(BigDecimal paramBigDecimal)
  {
    this.rewardRate = paramBigDecimal;
  }

  public BigDecimal getFirstPayRate()
  {
    return this.firstPayRate;
  }

  public void setFirstPayRate(BigDecimal paramBigDecimal)
  {
    this.firstPayRate = paramBigDecimal;
  }

  public BigDecimal getSecondPayRate()
  {
    return this.secondPayRate;
  }

  public void setSecondPayRate(BigDecimal paramBigDecimal)
  {
    this.secondPayRate = paramBigDecimal;
  }

  public BigDecimal getRewardRateTemp()
  {
    return this.rewardRate.multiply(rate);
  }

  public void setRewardRateTemp(BigDecimal paramBigDecimal)
  {
    this.rewardRate = paramBigDecimal.divide(rate);
  }

  public BigDecimal getFirstPayRateTemp()
  {
    return this.firstPayRate.multiply(rate);
  }

  public void setFirstPayRateTemp(BigDecimal paramBigDecimal)
  {
    this.firstPayRate = paramBigDecimal.divide(rate);
  }

  public BigDecimal getSecondPayRateTemp()
  {
    return this.secondPayRate.multiply(rate);
  }

  public void setSecondPayRateTemp(BigDecimal paramBigDecimal)
  {
    this.secondPayRate = paramBigDecimal.divide(rate);
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}