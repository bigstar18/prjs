package gnnt.MEBS.timebargain.mgr.model.firmSet;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class FirmBreedTradeFee extends StandardModel
{
  private static final long serialVersionUID = -4557309635499454544L;

  @ClassDiscription(name="交易商ID", description="")
  private String firmID;

  @ClassDiscription(name="品种ID", description="")
  private Integer breedID;

  @ClassDiscription(name="手续费算法", description="")
  private Integer feeAlgr;

  @ClassDiscription(name="开仓买手续费系数", description="")
  private Double feeRate_B;

  @ClassDiscription(name="开仓卖手续费系数", description="")
  private Double feeRate_S;

  @ClassDiscription(name="今开今平买手续费系数", description="")
  private Double todayCloseFeeRate_B;

  @ClassDiscription(name="今开今平卖手续费系数", description="")
  private Double todayCloseFeeRate_S;

  @ClassDiscription(name="今开今平卖手续费系数", description="")
  private Double historyCloseFeeRate_B;

  @ClassDiscription(name="隔夜单平仓卖手续费系数", description="")
  private Double historyCloseFeeRate_S;

  @ClassDiscription(name="代为转让手续费算法", description="")
  private Integer forceCloseFeeAlgr;

  @ClassDiscription(name="代为转让买手续费系数", description="")
  private Double forceCloseFeeRate_B;

  @ClassDiscription(name="代为转让卖手续费系数", description="")
  private Double forceCloseFeeRate_S;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }

  public Integer getBreedID()
  {
    return this.breedID;
  }

  public void setBreedID(Integer breedID)
  {
    this.breedID = breedID;
  }

  public Integer getFeeAlgr()
  {
    return this.feeAlgr;
  }

  public void setFeeAlgr(Integer feeAlgr)
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

  public Integer getForceCloseFeeAlgr()
  {
    return this.forceCloseFeeAlgr;
  }

  public void setForceCloseFeeAlgr(Integer forceCloseFeeAlgr)
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
    return null;
  }
}