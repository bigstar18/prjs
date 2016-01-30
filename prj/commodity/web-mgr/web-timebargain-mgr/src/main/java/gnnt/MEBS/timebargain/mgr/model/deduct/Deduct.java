package gnnt.MEBS.timebargain.mgr.model.deduct;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class Deduct extends StandardModel
{
  private static final long serialVersionUID = -1L;

  @ClassDiscription(name="强减ID", description="")
  private Long deductId;

  @ClassDiscription(name="强减日期", description="")
  private Date deductDate;

  @ClassDiscription(name="强减日期", description="")
  private String commodityId;

  @ClassDiscription(name="强减日期", description="")
  private String deductStatus = "N";

  @ClassDiscription(name="强减日期", description="")
  private Double deductPrice;

  @ClassDiscription(name="亏损方买卖标志", description="")
  private Short loserBSFlag;

  @ClassDiscription(name="亏损方买卖标志", description="")
  private Short loserMode;

  @ClassDiscription(name="亏损方买卖标志", description="")
  private Double lossRate;

  @ClassDiscription(name="自我对冲", description="")
  private Short selfCounteract;

  @ClassDiscription(name="自我对冲", description="")
  private Double profitLvl1;

  @ClassDiscription(name="盈利分级比例2", description="")
  private Double profitLvl2;

  @ClassDiscription(name="盈利分级比例2", description="")
  private Double profitLvl3;

  @ClassDiscription(name="盈利分级比例4", description="")
  private Double profitLvl4;

  @ClassDiscription(name="盈利分级比例5", description="")
  private Double profitLvl5;

  @ClassDiscription(name="盈利分级比例5", description="")
  private Date execTime;

  @ClassDiscription(name="警告", description="")
  private String alert;

  public Long getDeductId()
  {
    return this.deductId;
  }

  public void setDeductId(Long deductId)
  {
    this.deductId = deductId;
  }

  public Date getDeductDate()
  {
    return this.deductDate;
  }

  public void setDeductDate(Date deductDate)
  {
    this.deductDate = deductDate;
  }

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }

  public String getDeductStatus()
  {
    return this.deductStatus;
  }

  public void setDeductStatus(String deductStatus)
  {
    this.deductStatus = deductStatus;
  }

  public Double getDeductPrice()
  {
    return this.deductPrice;
  }

  public void setDeductPrice(Double deductPrice)
  {
    this.deductPrice = deductPrice;
  }

  public Short getLoserBSFlag()
  {
    return this.loserBSFlag;
  }

  public void setLoserBSFlag(Short loserBSFlag)
  {
    this.loserBSFlag = loserBSFlag;
  }

  public Short getLoserMode()
  {
    return this.loserMode;
  }

  public void setLoserMode(Short loserMode)
  {
    this.loserMode = loserMode;
  }

  public Double getLossRate()
  {
    return this.lossRate;
  }

  public void setLossRate(Double lossRate)
  {
    this.lossRate = lossRate;
  }

  public Short getSelfCounteract()
  {
    return this.selfCounteract;
  }

  public void setSelfCounteract(Short selfCounteract)
  {
    this.selfCounteract = selfCounteract;
  }

  public Double getProfitLvl1()
  {
    return this.profitLvl1;
  }

  public void setProfitLvl1(Double profitLvl1)
  {
    this.profitLvl1 = profitLvl1;
  }

  public Double getProfitLvl2()
  {
    return this.profitLvl2;
  }

  public void setProfitLvl2(Double profitLvl2)
  {
    this.profitLvl2 = profitLvl2;
  }

  public Double getProfitLvl3()
  {
    return this.profitLvl3;
  }

  public void setProfitLvl3(Double profitLvl3)
  {
    this.profitLvl3 = profitLvl3;
  }

  public Double getProfitLvl4()
  {
    return this.profitLvl4;
  }

  public void setProfitLvl4(Double profitLvl4)
  {
    this.profitLvl4 = profitLvl4;
  }

  public Double getProfitLvl5()
  {
    return this.profitLvl5;
  }

  public void setProfitLvl5(Double profitLvl5)
  {
    this.profitLvl5 = profitLvl5;
  }

  public Date getExecTime()
  {
    return this.execTime;
  }

  public void setExecTime(Date execTime)
  {
    this.execTime = execTime;
  }

  public String getAlert()
  {
    return this.alert;
  }

  public void setAlert(String alert)
  {
    this.alert = alert;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "deductId", this.deductId);
  }
}