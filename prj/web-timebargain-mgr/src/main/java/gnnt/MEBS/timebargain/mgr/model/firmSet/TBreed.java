package gnnt.MEBS.timebargain.mgr.model.firmSet;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class TBreed extends StandardModel
{
  private static final long serialVersionUID = -5540620163866973608L;

  @ClassDiscription(name="品种名称", description="")
  private String breedName;

  @ClassDiscription(name="商品品种ID", description="")
  private Integer breedID;

  @ClassDiscription(name=" 保证金算法", description="")
  private Integer marginAlgr;

  @ClassDiscription(name=" 买保证金系数", description="")
  private Double marginRate_B;

  @ClassDiscription(name="卖保证金系数", description="")
  private Double marginRate_S;

  @ClassDiscription(name="卖保证金系数", description="")
  private Double marginItem1;

  @ClassDiscription(name="买保证金款项2", description="")
  private Double marginItem2;

  @ClassDiscription(name="买保证金款项2", description="")
  private Double marginItem3;

  @ClassDiscription(name="买保证金款项2", description="")
  private Double marginItem4;

  @ClassDiscription(name="买保证金款项2", description="")
  private Double marginItem5;

  @ClassDiscription(name=" 卖保证金款项1", description="")
  private Double marginItem1_S;

  @ClassDiscription(name="卖保证金款项2", description="")
  private Double marginItem2_S;

  @ClassDiscription(name="卖保证金款项3", description="")
  private Double marginItem3_S;

  @ClassDiscription(name="卖保证金款项4", description="")
  private Double marginItem4_S;

  @ClassDiscription(name="卖保证金款项4", description="")
  private Double marginItem5_S;

  @ClassDiscription(name="买担保金系数", description="")
  private Double marginAssure_B;

  @ClassDiscription(name="卖担保金系数", description="")
  private Double marginAssure_S;

  @ClassDiscription(name="买担保金款项1", description="")
  private Double marginItemAssure1;

  @ClassDiscription(name="买担保金款项2", description="")
  private Double marginItemAssure2;

  @ClassDiscription(name="买担保金款项3", description="")
  private Double marginItemAssure3;

  @ClassDiscription(name="买担保金款项3", description="")
  private Double marginItemAssure4;

  @ClassDiscription(name="买担保金款项5", description="")
  private Double marginItemAssure5;

  @ClassDiscription(name="买担保金款项5", description="")
  private Double marginItemAssure1_S;

  @ClassDiscription(name="买担保金款项5", description="")
  private Double marginItemAssure2_S;

  @ClassDiscription(name="卖担保金款项3", description="")
  private Double marginItemAssure3_S;

  @ClassDiscription(name="卖担保金款项4", description="")
  private Double marginItemAssure4_S;

  @ClassDiscription(name="卖担保金款项5", description="")
  private Double marginItemAssure5_S;

  @ClassDiscription(name="买交收保证金算法", description="")
  private Integer settleMarginAlgr_B;

  @ClassDiscription(name="交收保证金买系数", description="")
  private Double settleMarginRate_B;

  @ClassDiscription(name="交收保证金买系数", description="")
  private Integer settleMarginAlgr_S;

  @ClassDiscription(name="交收保证金买系数", description="")
  private Double settleMarginRate_S;

  @ClassDiscription(name="交收货款算法", description="")
  private Integer payoutAlgr;

  @ClassDiscription(name="交收货款系数", description="")
  private Double payoutRate;

  @ClassDiscription(name="最大订货量", description="")
  private Integer maxHoldQty;

  @ClassDiscription(name="最大净订货量", description="")
  private Integer cleanMaxHoldQty;

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

  @ClassDiscription(name="交收手续费算法", description="")
  private Integer settleFeeAlgr;

  @ClassDiscription(name="交收买手续费系数", description="")
  private Double settleFeeRate_B;

  @ClassDiscription(name="交收卖手续费系数", description="")
  private Double settleFeeRate_S;
  private Integer type1;
  private Integer type2;
  private Integer type3;
  private Integer type4;
  private Integer type5;

  public Integer getBreedID()
  {
    return this.breedID;
  }

  public void setBreedID(Integer breedID)
  {
    this.breedID = breedID;
  }

  public String getBreedName()
  {
    return this.breedName;
  }

  public void setBreedName(String breedName)
  {
    this.breedName = breedName;
  }

  public Integer getMarginAlgr()
  {
    return this.marginAlgr;
  }

  public void setMarginAlgr(Integer marginAlgr)
  {
    this.marginAlgr = marginAlgr;
  }

  public Double getMarginRate_B()
  {
    return this.marginRate_B;
  }

  public void setMarginRate_B(Double marginRateB)
  {
    this.marginRate_B = marginRateB;
  }

  public Double getMarginRate_S()
  {
    return this.marginRate_S;
  }

  public void setMarginRate_S(Double marginRateS)
  {
    this.marginRate_S = marginRateS;
  }

  public Double getMarginItem1()
  {
    return this.marginItem1;
  }

  public void setMarginItem1(Double marginItem1)
  {
    this.marginItem1 = marginItem1;
  }

  public Double getMarginItem2()
  {
    return this.marginItem2;
  }

  public void setMarginItem2(Double marginItem2)
  {
    this.marginItem2 = marginItem2;
  }

  public Double getMarginItem3()
  {
    return this.marginItem3;
  }

  public void setMarginItem3(Double marginItem3)
  {
    this.marginItem3 = marginItem3;
  }

  public Double getMarginItem4()
  {
    return this.marginItem4;
  }

  public void setMarginItem4(Double marginItem4)
  {
    this.marginItem4 = marginItem4;
  }

  public Double getMarginItem5()
  {
    return this.marginItem5;
  }

  public void setMarginItem5(Double marginItem5)
  {
    this.marginItem5 = marginItem5;
  }

  public Double getMarginItem1_S()
  {
    return this.marginItem1_S;
  }

  public void setMarginItem1_S(Double marginItem1S)
  {
    this.marginItem1_S = marginItem1S;
  }

  public Double getMarginItem2_S()
  {
    return this.marginItem2_S;
  }

  public void setMarginItem2_S(Double marginItem2S)
  {
    this.marginItem2_S = marginItem2S;
  }

  public Double getMarginItem3_S()
  {
    return this.marginItem3_S;
  }

  public void setMarginItem3_S(Double marginItem3S)
  {
    this.marginItem3_S = marginItem3S;
  }

  public Double getMarginItem4_S()
  {
    return this.marginItem4_S;
  }

  public void setMarginItem4_S(Double marginItem4S)
  {
    this.marginItem4_S = marginItem4S;
  }

  public Double getMarginItem5_S()
  {
    return this.marginItem5_S;
  }

  public void setMarginItem5_S(Double marginItem5S)
  {
    this.marginItem5_S = marginItem5S;
  }

  public Double getMarginAssure_B()
  {
    return this.marginAssure_B;
  }

  public void setMarginAssure_B(Double marginAssureB)
  {
    this.marginAssure_B = marginAssureB;
  }

  public Double getMarginAssure_S()
  {
    return this.marginAssure_S;
  }

  public void setMarginAssure_S(Double marginAssureS)
  {
    this.marginAssure_S = marginAssureS;
  }

  public Double getMarginItemAssure1()
  {
    return this.marginItemAssure1;
  }

  public void setMarginItemAssure1(Double marginItemAssure1)
  {
    this.marginItemAssure1 = marginItemAssure1;
  }

  public Double getMarginItemAssure2()
  {
    return this.marginItemAssure2;
  }

  public void setMarginItemAssure2(Double marginItemAssure2)
  {
    this.marginItemAssure2 = marginItemAssure2;
  }

  public Double getMarginItemAssure3()
  {
    return this.marginItemAssure3;
  }

  public void setMarginItemAssure3(Double marginItemAssure3)
  {
    this.marginItemAssure3 = marginItemAssure3;
  }

  public Double getMarginItemAssure4()
  {
    return this.marginItemAssure4;
  }

  public void setMarginItemAssure4(Double marginItemAssure4)
  {
    this.marginItemAssure4 = marginItemAssure4;
  }

  public Double getMarginItemAssure5()
  {
    return this.marginItemAssure5;
  }

  public void setMarginItemAssure5(Double marginItemAssure5)
  {
    this.marginItemAssure5 = marginItemAssure5;
  }

  public Double getMarginItemAssure1_S()
  {
    return this.marginItemAssure1_S;
  }

  public void setMarginItemAssure1_S(Double marginItemAssure1S)
  {
    this.marginItemAssure1_S = marginItemAssure1S;
  }

  public Double getMarginItemAssure2_S()
  {
    return this.marginItemAssure2_S;
  }

  public void setMarginItemAssure2_S(Double marginItemAssure2S)
  {
    this.marginItemAssure2_S = marginItemAssure2S;
  }

  public Double getMarginItemAssure3_S()
  {
    return this.marginItemAssure3_S;
  }

  public void setMarginItemAssure3_S(Double marginItemAssure3S)
  {
    this.marginItemAssure3_S = marginItemAssure3S;
  }

  public Double getMarginItemAssure4_S()
  {
    return this.marginItemAssure4_S;
  }

  public void setMarginItemAssure4_S(Double marginItemAssure4S)
  {
    this.marginItemAssure4_S = marginItemAssure4S;
  }

  public Double getMarginItemAssure5_S()
  {
    return this.marginItemAssure5_S;
  }

  public void setMarginItemAssure5_S(Double marginItemAssure5S)
  {
    this.marginItemAssure5_S = marginItemAssure5S;
  }

  public Integer getSettleMarginAlgr_B()
  {
    return this.settleMarginAlgr_B;
  }

  public void setSettleMarginAlgr_B(Integer settleMarginAlgrB)
  {
    this.settleMarginAlgr_B = settleMarginAlgrB;
  }

  public Double getSettleMarginRate_B()
  {
    return this.settleMarginRate_B;
  }

  public void setSettleMarginRate_B(Double settleMarginRateB)
  {
    this.settleMarginRate_B = settleMarginRateB;
  }

  public Integer getSettleMarginAlgr_S()
  {
    return this.settleMarginAlgr_S;
  }

  public void setSettleMarginAlgr_S(Integer settleMarginAlgrS)
  {
    this.settleMarginAlgr_S = settleMarginAlgrS;
  }

  public Double getSettleMarginRate_S()
  {
    return this.settleMarginRate_S;
  }

  public void setSettleMarginRate_S(Double settleMarginRateS)
  {
    this.settleMarginRate_S = settleMarginRateS;
  }

  public Integer getPayoutAlgr()
  {
    return this.payoutAlgr;
  }

  public void setPayoutAlgr(Integer payoutAlgr)
  {
    this.payoutAlgr = payoutAlgr;
  }

  public Double getPayoutRate()
  {
    return this.payoutRate;
  }

  public void setPayoutRate(Double payoutRate)
  {
    this.payoutRate = payoutRate;
  }

  public Integer getMaxHoldQty()
  {
    return this.maxHoldQty;
  }

  public void setMaxHoldQty(Integer maxHoldQty)
  {
    this.maxHoldQty = maxHoldQty;
  }

  public Integer getCleanMaxHoldQty()
  {
    return this.cleanMaxHoldQty;
  }

  public void setCleanMaxHoldQty(Integer cleanMaxHoldQty)
  {
    this.cleanMaxHoldQty = cleanMaxHoldQty;
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

  public Integer getSettleFeeAlgr()
  {
    return this.settleFeeAlgr;
  }

  public void setSettleFeeAlgr(Integer settleFeeAlgr)
  {
    this.settleFeeAlgr = settleFeeAlgr;
  }

  public Double getSettleFeeRate_B()
  {
    return this.settleFeeRate_B;
  }

  public void setSettleFeeRate_B(Double settleFeeRateB)
  {
    this.settleFeeRate_B = settleFeeRateB;
  }

  public Double getSettleFeeRate_S()
  {
    return this.settleFeeRate_S;
  }

  public void setSettleFeeRate_S(Double settleFeeRateS)
  {
    this.settleFeeRate_S = settleFeeRateS;
  }

  public Integer getType1()
  {
    return this.type1;
  }

  public void setType1(Integer type1) {
    this.type1 = type1;
  }

  public Integer getType2() {
    return this.type2;
  }

  public void setType2(Integer type2) {
    this.type2 = type2;
  }

  public Integer getType3() {
    return this.type3;
  }

  public void setType3(Integer type3) {
    this.type3 = type3;
  }

  public Integer getType4() {
    return this.type4;
  }

  public void setType4(Integer type4) {
    this.type4 = type4;
  }

  public Integer getType5() {
    return this.type5;
  }

  public void setType5(Integer type5) {
    this.type5 = type5;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "breedID", this.breedID);
  }
}