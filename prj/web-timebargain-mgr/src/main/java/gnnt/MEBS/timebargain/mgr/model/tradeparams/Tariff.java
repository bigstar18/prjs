package gnnt.MEBS.timebargain.mgr.model.tradeparams;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class Tariff extends StandardModel
{

  @ClassDiscription(name="套餐ID", description="")
  private String tariffID;

  @ClassDiscription(name="商品代码", description="")
  private String commodityID;

  @ClassDiscription(name="套餐名称", description="")
  private String tariffName;

  @ClassDiscription(name="套餐比例", description="")
  private Double tariffRate;

  @ClassDiscription(name="手续费算法", description="")
  private Short feeAlgr;

  @ClassDiscription(name="开仓买手续费系数", description="")
  private Double feeRate_B;

  @ClassDiscription(name="开仓卖手续费系数", description="")
  private Double feeRate_S;

  @ClassDiscription(name="今开今平买手续费系数", description="")
  private Double todayCloseFeeRate_B;

  @ClassDiscription(name="今开今平卖手续费系数", description="")
  private Double todayCloseFeeRate_S;

  @ClassDiscription(name="隔夜单平仓买手续费系数", description="")
  private Double historyCloseFeeRate_B;

  @ClassDiscription(name="隔夜单平仓卖手续费系数", description="")
  private Double historyCloseFeeRate_S;

  @ClassDiscription(name="代为转让手续费算法", description="")
  private Short forceCloseFeeAlgr;

  @ClassDiscription(name="代为转让买手续费算法", description="")
  private Double forceCloseFeeRate_B;

  @ClassDiscription(name="代为转让卖手续费算法", description="")
  private Double forceCloseFeeRate_S;

  @ClassDiscription(name="创建时间", description="")
  private Date createTime;

  @ClassDiscription(name="创建人", description="")
  private String createUser;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  public String getTariffID()
  {
    return this.tariffID;
  }

  public void setTariffID(String tariffID)
  {
    this.tariffID = tariffID;
  }

  public String getCommodityID()
  {
    return this.commodityID;
  }

  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }

  public String getTariffName()
  {
    return this.tariffName;
  }

  public void setTariffName(String tariffName)
  {
    this.tariffName = tariffName;
  }

  public Double getTariffRate()
  {
    return this.tariffRate;
  }

  public void setTariffRate(Double tariffRate)
  {
    this.tariffRate = tariffRate;
  }

  public Short getFeeAlgr()
  {
    return this.feeAlgr;
  }

  public void setFeeAlgr(Short feeAlgr)
  {
    this.feeAlgr = feeAlgr;
  }

  public Double getFeeRate_B()
  {
    return this.feeRate_B;
  }

  public void setFeeRate_B(Double feeRateB)
  {
    this.feeRate_B = feeRateB;
  }

  public Double getFeeRate_S()
  {
    return this.feeRate_S;
  }

  public void setFeeRate_S(Double feeRateS)
  {
    this.feeRate_S = feeRateS;
  }

  public Double getTodayCloseFeeRate_B()
  {
    return this.todayCloseFeeRate_B;
  }

  public void setTodayCloseFeeRate_B(Double todayCloseFeeRateB)
  {
    this.todayCloseFeeRate_B = todayCloseFeeRateB;
  }

  public Double getTodayCloseFeeRate_S()
  {
    return this.todayCloseFeeRate_S;
  }

  public void setTodayCloseFeeRate_S(Double todayCloseFeeRateS)
  {
    this.todayCloseFeeRate_S = todayCloseFeeRateS;
  }

  public Double getHistoryCloseFeeRate_B()
  {
    return this.historyCloseFeeRate_B;
  }

  public void setHistoryCloseFeeRate_B(Double historyCloseFeeRateB)
  {
    this.historyCloseFeeRate_B = historyCloseFeeRateB;
  }

  public Double getHistoryCloseFeeRate_S()
  {
    return this.historyCloseFeeRate_S;
  }

  public void setHistoryCloseFeeRate_S(Double historyCloseFeeRateS)
  {
    this.historyCloseFeeRate_S = historyCloseFeeRateS;
  }

  public Short getForceCloseFeeAlgr()
  {
    return this.forceCloseFeeAlgr;
  }

  public void setForceCloseFeeAlgr(Short forceCloseFeeAlgr)
  {
    this.forceCloseFeeAlgr = forceCloseFeeAlgr;
  }

  public Double getForceCloseFeeRate_B()
  {
    return this.forceCloseFeeRate_B;
  }

  public void setForceCloseFeeRate_B(Double forceCloseFeeRateB)
  {
    this.forceCloseFeeRate_B = forceCloseFeeRateB;
  }

  public Double getForceCloseFeeRate_S()
  {
    return this.forceCloseFeeRate_S;
  }

  public void setForceCloseFeeRate_S(Double forceCloseFeeRateS)
  {
    this.forceCloseFeeRate_S = forceCloseFeeRateS;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public String getCreateUser()
  {
    return this.createUser;
  }

  public void setCreateUser(String createUser)
  {
    this.createUser = createUser;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "tariffID", this.tariffID);
  }
}