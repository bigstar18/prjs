package gnnt.MEBS.timebargain.mgr.model.tradeparams;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import gnnt.MEBS.common.mgr.statictools.Tools;
import java.util.Date;

public class HisCommodity extends StandardModel
{
  private static final long serialVersionUID = 5070202470139044074L;

  @ClassDiscription(name="", description="")
  private String commodityID;

  @ClassDiscription(name="", description="")
  private String name;

  @ClassDiscription(name="商品品种", description="")
  private Long breedID;

  @ClassDiscription(name="", description="")
  private Long sortID;

  @ClassDiscription(name="合约因子", description="")
  private Double contractFactor;

  @ClassDiscription(name="状态", description="")
  private Short status;

  @ClassDiscription(name="最小价位浮动", description="")
  private Double minPriceMove;

  @ClassDiscription(name="涨跌幅算法", description="")
  private Short spreadAlgr;

  @ClassDiscription(name="涨幅上限", description="")
  private Double spreadUpLmt;

  @ClassDiscription(name="跌幅下限", description="")
  private Double spreadDownLmt;

  @ClassDiscription(name="手续费算法", description="")
  private Short feeAlgr;

  @ClassDiscription(name="开仓买手续费系数", description="")
  private Double feeRate_B;

  @ClassDiscription(name="开仓卖手续费系数", description="")
  private Double FeeRate_S;

  @ClassDiscription(name="保证金算法", description="")
  private Short marginAlgr;

  @ClassDiscription(name="交易买保证金系数", description="")
  private Double marginRate_B;

  @ClassDiscription(name="交易卖保证金系数", description="")
  private Double marginRate_S;

  @ClassDiscription(name="买担保金系数", description="")
  private Double marginAssure_B;

  @ClassDiscription(name="卖担保金系数", description="")
  private Double marginAssure_S;

  @ClassDiscription(name="今开今平买手续费参数", description="")
  private Double todayCloseFeeRate_B;

  @ClassDiscription(name="今开今平卖手续费参数", description="")
  private Double todayCloseFeeRate_S;

  @ClassDiscription(name="隔夜单平仓买手续费系数", description="")
  private Double historyCloseFeeRate_B;

  @ClassDiscription(name="隔夜单平仓卖手续费系数", description="")
  private Double historyCloseFeeRate_S;

  @ClassDiscription(name="", description="单个交易商品的总订货量不能大于此值-1表示不限制")
  private Long limitCmdtyQty;

  @ClassDiscription(name="交收手续费算法", description="")
  private Short settleFeeAlgr;

  @ClassDiscription(name="代为转让(强平)手续费算法", description="")
  private Short forceCloseFeeAlgr;

  @ClassDiscription(name="交收买手续费", description="")
  private Double settleFeeRate_B;

  @ClassDiscription(name="交收卖手续费", description="")
  private Double settleFeeRate_S;

  @ClassDiscription(name="代为转让(强平)买手续费", description="")
  private Double forceCloseFeeRate_B;

  @ClassDiscription(name="代为转让(强平)卖手续", description="")
  private Double forceCloseFeeRate_S;

  @ClassDiscription(name="买交收保证金算法", description="")
  private Short settleMarginAlgr_B;

  @ClassDiscription(name="卖交收保证金算法", description="")
  private Short settleMarginAlgr_S;

  @ClassDiscription(name="行情订货量", description="")
  private Long reserveCount;

  @ClassDiscription(name="交收保证金买系数", description="")
  private Double settleMarginRate_B;

  @ClassDiscription(name="交收保证金卖系数", description="")
  private Double settleMarginRate_S;

  @ClassDiscription(name="增值税率", description="")
  private Double addedTax;

  @ClassDiscription(name="保证金计算方式", description="")
  private Short marginPriceType;

  @ClassDiscription(name="交收手续费最低金额", description="")
  private Double lowestSettleFee;

  @ClassDiscription(name="交易商净订货量", description="")
  private Long firmCleanQty;

  @ClassDiscription(name="买保证金款项1", description="")
  private Double marginItem1;

  @ClassDiscription(name="买保证金款项2", description="")
  private Double marginItem2;

  @ClassDiscription(name="买保证金款项3", description="")
  private Double marginItem3;

  @ClassDiscription(name="买保证金款项4", description="")
  private Double marginItem4;

  @ClassDiscription(name="买保证金款项5", description="")
  private Double marginItem5;

  @ClassDiscription(name="结算价", description="")
  private Double lastPrice;

  @ClassDiscription(name="卖保证金款项1", description="")
  private Double marginItem1_S;

  @ClassDiscription(name="卖保证金款项2", description="")
  private Double marginItem2_S;

  @ClassDiscription(name="卖保证金款项3", description="")
  private Double marginItem3_S;

  @ClassDiscription(name="卖保证金款项4", description="")
  private Double marginItem4_S;

  @ClassDiscription(name="卖保证金款项5", description="")
  private Double marginItem5_S;

  @ClassDiscription(name="买担保金款项1", description="")
  private Double marginItemAssure1;

  @ClassDiscription(name="买担保金款项2", description="")
  private Double marginItemAssure2;

  @ClassDiscription(name="买担保金款项3", description="")
  private Double marginItemAssure3;

  @ClassDiscription(name="买担保金款项4", description="")
  private Double marginItemAssure4;

  @ClassDiscription(name="买担保金款项5", description="")
  private Double marginItemAssure5;

  @ClassDiscription(name=" 上市日期", description="")
  private Date marketDate;

  @ClassDiscription(name="交收相关日期1", description="")
  private Date settleDate1;

  @ClassDiscription(name="交收相关日期2", description="")
  private Date settleDate2;

  @ClassDiscription(name="交收相关日期3", description="")
  private Date settleDate3;

  @ClassDiscription(name="交收相关日期4", description="")
  private Date settleDate4;

  @ClassDiscription(name="交收相关日期5", description="")
  private Date settleDate5;

  @ClassDiscription(name="交收相关日期", description="")
  private Date settleDate;

  @ClassDiscription(name="卖担保金款项1", description="")
  private Double marginItemAssure1_S;

  @ClassDiscription(name="卖担保金款项2", description="")
  private Double marginItemAssure2_S;

  @ClassDiscription(name="卖担保金款项3", description="")
  private Double marginItemAssure3_S;

  @ClassDiscription(name="卖担保金款项4", description="")
  private Double marginItemAssure4_S;

  @ClassDiscription(name="卖担保金款项5", description="")
  private Double marginItemAssure5_S;

  @ClassDiscription(name="交收货款算法", description="")
  private Short payoutAlgr;

  @ClassDiscription(name="交收货款系数", description="")
  private Double payoutRate;

  @ClassDiscription(name="交易商最大订货量", description="最大持仓量，单个交易商总持仓量不能超过此数量。")
  private Long firmMaxHoldQty;

  @ClassDiscription(name="增值税率系数", description="")
  private Double addedTaxFactor;

  @ClassDiscription(name="交收价计算方式", description="")
  private Short settlePriceType;

  @ClassDiscription(name="计算交收价的提前日", description="")
  private Long beforeDays;

  @ClassDiscription(name="指定交收价", description="")
  private Double specSettlePrice;

  @ClassDiscription(name="买方委托权限", description="")
  private Short orderPrivilege_B;

  @ClassDiscription(name="卖方委托权限", description="")
  private Short orderPrivilege_S;

  @ClassDiscription(name="交易商订货量限制算法", description="")
  private Short firmMaxHoldQtyAlgr;

  @ClassDiscription(name="商品百分比阀值量", description="")
  private Long startPercentQty;

  @ClassDiscription(name="交易商订货量最大百分比", description="")
  private Double maxPercentLimit;

  @ClassDiscription(name="单笔最大订货量", description="")
  private Long oneMaxHoldQty;

  @ClassDiscription(name="最小变动数量", description="")
  private Integer minQuantityMove;

  @ClassDiscription(name="最小交割单位", description="")
  private Integer minSettleMoveQty;

  @ClassDiscription(name="最小交割数量", description="")
  private Integer minSettleQty;

  @ClassDiscription(name="单位数量名称", description="")
  private String contractFactorName;

  @ClassDiscription(name="买方延期补偿比率", description="")
  private Double delayRecoupRate;

  @ClassDiscription(name="卖方延期补偿比率", description="")
  private Double delayRecoupRate_S;

  @ClassDiscription(name="交收方式", description="0：中远期，1：连续现货，2：专场交易  ")
  private Short settleWay;

  @ClassDiscription(name="延期费收取方式", description="")
  private Short delayFeeWay;

  @ClassDiscription(name="", description="")
  private Double maxFeeRate;

  @ClassDiscription(name="仓储补偿费", description="")
  private Double storeRecoupRate;

  @ClassDiscription(name="延期交收申报交收价格类型", description="0：以结算价作为交收价格 ，1：以订立价作为交收价格")
  private Integer delaySettlePriceType;

  @ClassDiscription(name="计算交收保证金提前日", description="")
  private Integer beforeDays_M;

  @ClassDiscription(name="提前交收交收价格方式", description="0:按订立价交收 ；按自主价交收")
  private Integer aheadSettlePriceType;

  @ClassDiscription(name="商品单边订货量限制", description="-1不限制")
  private Long sideHoldLimitQty;

  @ClassDiscription(name="交收保证金计算方式", description="")
  private Integer settleMarginType;

  @ClassDiscription(name="是否启用持仓天数限制", description="0:不启用 ；1：启用")
  private Integer holdDaysLimit;

  @ClassDiscription(name="最长持仓天数", description="")
  private Long maxHoldPositionDay;
  public Date clearDate;
  private Short type;
  private Short type1;
  private Short type2;
  private Short type3;
  private Short type4;

  public String getTradeTime()
  {
    return null;
  }

  public String getContractFactorName() {
    return this.contractFactorName;
  }

  public void setContractFactorName(String contractFactorName) {
    this.contractFactorName = contractFactorName;
  }

  public Date getClearDate()
  {
    return this.clearDate;
  }

  public void setClearDate(Date clearDate) {
    this.clearDate = clearDate;
  }

  public String getCommodityID() {
    return this.commodityID;
  }

  public void setCommodityID(String commodityID) {
    this.commodityID = commodityID;
  }

  public Date getMarketDate() {
    return this.marketDate;
  }

  public void setMarketDate(Date marketDate) {
    this.marketDate = marketDate;
  }

  public void setMarketDate(String marketDate) {
    this.marketDate = Tools.strToDate(marketDate);
  }

  public Date getSettleDate() {
    return this.settleDate;
  }

  public void setSettleDate(Date settleDate) {
    this.settleDate = settleDate;
  }

  public void setSettleDate(String settleDate) {
    this.settleDate = Tools.strToDate(settleDate);
  }

  public Date getSettleDate1() {
    return this.settleDate1;
  }

  public void setSettleDate1(Date settleDate1) {
    this.settleDate1 = settleDate1;
  }

  public void setSettleDate1(String settleDate) {
    this.settleDate1 = Tools.strToDate(settleDate);
  }

  public Date getSettleDate2() {
    return this.settleDate2;
  }

  public void setSettleDate2(Date settleDate2) {
    this.settleDate2 = settleDate2;
  }

  public void setSettleDate2(String settleDate) {
    if (!"".equals(settleDate))
      this.settleDate2 = Tools.strToDate(settleDate);
    else
      this.settleDate2 = null;
  }

  public Date getSettleDate3() {
    return this.settleDate3;
  }

  public void setSettleDate3(Date settleDate3) {
    this.settleDate3 = settleDate3;
  }

  public void setSettleDate3(String settleDate) {
    if (!"".equals(settleDate))
      this.settleDate3 = Tools.strToDate(settleDate);
    else
      this.settleDate3 = null;
  }

  public Date getSettleDate4() {
    return this.settleDate4;
  }

  public void setSettleDate4(Date settleDate4) {
    this.settleDate4 = settleDate4;
  }

  public void setSettleDate4(String settleDate) {
    if (!"".equals(settleDate))
      this.settleDate4 = Tools.strToDate(settleDate);
    else
      this.settleDate4 = null;
  }

  public Date getSettleDate5() {
    return this.settleDate5;
  }

  public void setSettleDate5(Date settleDate5) {
    this.settleDate5 = settleDate5;
  }

  public void setSettleDate5(String settleDate) {
    if (!"".equals(settleDate))
      this.settleDate5 = Tools.strToDate(settleDate);
    else
      this.settleDate5 = null;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getSortID() {
    return this.sortID;
  }

  public void setSortID(Long sortID) {
    this.sortID = sortID;
  }

  public Short getStatus() {
    return this.status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public Double getContractFactor() {
    return this.contractFactor;
  }

  public void setContractFactor(Double contractFactor) {
    this.contractFactor = contractFactor;
  }

  public Double getMinPriceMove() {
    return this.minPriceMove;
  }

  public void setMinPriceMove(Double minPriceMove) {
    this.minPriceMove = minPriceMove;
  }

  public Long getBreedID() {
    return this.breedID;
  }

  public void setBreedID(Long breedID) {
    this.breedID = breedID;
  }

  public Short getSpreadAlgr() {
    return this.spreadAlgr;
  }

  public void setSpreadAlgr(Short spreadAlgr) {
    this.spreadAlgr = spreadAlgr;
  }

  public Double getSpreadUpLmt() {
    return this.spreadUpLmt;
  }

  public void setSpreadUpLmt(Double spreadUpLmt) {
    this.spreadUpLmt = spreadUpLmt;
  }

  public Double getSpreadDownLmt() {
    return this.spreadDownLmt;
  }

  public void setSpreadDownLmt(Double spreadDownLmt) {
    this.spreadDownLmt = spreadDownLmt;
  }

  public Short getFeeAlgr() {
    return this.feeAlgr;
  }

  public void setFeeAlgr(Short feeAlgr) {
    this.feeAlgr = feeAlgr;
  }

  public Double getFeeRate_B() {
    return this.feeRate_B;
  }

  public void setFeeRate_B(Double feeRateB) {
    this.feeRate_B = feeRateB;
  }

  public Double getFeeRate_S() {
    return this.FeeRate_S;
  }

  public void setFeeRate_S(Double feeRateS) {
    this.FeeRate_S = feeRateS;
  }

  public Short getMarginAlgr() {
    return this.marginAlgr;
  }

  public void setMarginAlgr(Short marginAlgr) {
    this.marginAlgr = marginAlgr;
  }

  public Double getMarginRate_B() {
    return this.marginRate_B;
  }

  public void setMarginRate_B(Double marginRateB) {
    this.marginRate_B = marginRateB;
  }

  public Double getMarginRate_S() {
    return this.marginRate_S;
  }

  public void setMarginRate_S(Double marginRateS) {
    this.marginRate_S = marginRateS;
  }

  public Double getMarginAssure_B() {
    return this.marginAssure_B;
  }

  public void setMarginAssure_B(Double marginAssureB) {
    this.marginAssure_B = marginAssureB;
  }

  public Double getMarginAssure_S() {
    return this.marginAssure_S;
  }

  public void setMarginAssure_S(Double marginAssureS) {
    this.marginAssure_S = marginAssureS;
  }

  public Double getMarginItemAssure1()
  {
    return this.marginItemAssure1;
  }

  public void setMarginItemAssure1(Double marginItemAssure1) {
    this.marginItemAssure1 = marginItemAssure1;
  }

  public Double getMarginItemAssure2() {
    return this.marginItemAssure2;
  }

  public void setMarginItemAssure2(Double marginItemAssure2) {
    this.marginItemAssure2 = marginItemAssure2;
  }

  public Double getMarginItemAssure3() {
    return this.marginItemAssure3;
  }

  public void setMarginItemAssure3(Double marginItemAssure3) {
    this.marginItemAssure3 = marginItemAssure3;
  }

  public Double getMarginItemAssure4() {
    return this.marginItemAssure4;
  }

  public void setMarginItemAssure4(Double marginItemAssure4) {
    this.marginItemAssure4 = marginItemAssure4;
  }

  public Double getMarginItemAssure5() {
    return this.marginItemAssure5;
  }

  public void setMarginItemAssure5(Double marginItemAssure5) {
    this.marginItemAssure5 = marginItemAssure5;
  }

  public Double getMarginItem1() {
    return this.marginItem1;
  }

  public void setMarginItem1(Double marginItem1) {
    this.marginItem1 = marginItem1;
  }

  public Double getMarginItem2() {
    return this.marginItem2;
  }

  public void setMarginItem2(Double marginItem2) {
    this.marginItem2 = marginItem2;
  }

  public Double getMarginItem3() {
    return this.marginItem3;
  }

  public void setMarginItem3(Double marginItem3) {
    this.marginItem3 = marginItem3;
  }

  public Double getMarginItem4() {
    return this.marginItem4;
  }

  public void setMarginItem4(Double marginItem4) {
    this.marginItem4 = marginItem4;
  }

  public Double getMarginItem5() {
    return this.marginItem5;
  }

  public void setMarginItem5(Double marginItem5) {
    this.marginItem5 = marginItem5;
  }

  public Double getLastPrice() {
    return this.lastPrice;
  }

  public void setLastPrice(Double lastPrice) {
    this.lastPrice = lastPrice;
  }

  public Double getMarginItemAssure1_S() {
    return this.marginItemAssure1_S;
  }

  public void setMarginItemAssure1_S(Double marginItemAssure1S) {
    this.marginItemAssure1_S = marginItemAssure1S;
  }

  public Double getMarginItemAssure2_S() {
    return this.marginItemAssure2_S;
  }

  public void setMarginItemAssure2_S(Double marginItemAssure2S) {
    this.marginItemAssure2_S = marginItemAssure2S;
  }

  public Double getMarginItemAssure3_S() {
    return this.marginItemAssure3_S;
  }

  public void setMarginItemAssure3_S(Double marginItemAssure3S) {
    this.marginItemAssure3_S = marginItemAssure3S;
  }

  public Double getMarginItemAssure4_S() {
    return this.marginItemAssure4_S;
  }

  public void setMarginItemAssure4_S(Double marginItemAssure4S) {
    this.marginItemAssure4_S = marginItemAssure4S;
  }

  public Double getMarginItemAssure5_S() {
    return this.marginItemAssure5_S;
  }

  public void setMarginItemAssure5_S(Double marginItemAssure5S) {
    this.marginItemAssure5_S = marginItemAssure5S;
  }

  public Double getMarginItem1_S() {
    return this.marginItem1_S;
  }

  public void setMarginItem1_S(Double marginItem1S) {
    this.marginItem1_S = marginItem1S;
  }

  public Double getMarginItem2_S() {
    return this.marginItem2_S;
  }

  public void setMarginItem2_S(Double marginItem2S) {
    this.marginItem2_S = marginItem2S;
  }

  public Double getMarginItem3_S() {
    return this.marginItem3_S;
  }

  public void setMarginItem3_S(Double marginItem3S) {
    this.marginItem3_S = marginItem3S;
  }

  public Double getMarginItem4_S() {
    return this.marginItem4_S;
  }

  public void setMarginItem4_S(Double marginItem4S) {
    this.marginItem4_S = marginItem4S;
  }

  public Double getMarginItem5_S() {
    return this.marginItem5_S;
  }

  public void setMarginItem5_S(Double marginItem5S) {
    this.marginItem5_S = marginItem5S;
  }

  public Double getTodayCloseFeeRate_B() {
    return this.todayCloseFeeRate_B;
  }

  public void setTodayCloseFeeRate_B(Double todayCloseFeeRateB) {
    this.todayCloseFeeRate_B = todayCloseFeeRateB;
  }

  public Double getTodayCloseFeeRate_S() {
    return this.todayCloseFeeRate_S;
  }

  public void setTodayCloseFeeRate_S(Double todayCloseFeeRateS) {
    this.todayCloseFeeRate_S = todayCloseFeeRateS;
  }

  public Double getHistoryCloseFeeRate_B() {
    return this.historyCloseFeeRate_B;
  }

  public void setHistoryCloseFeeRate_B(Double historyCloseFeeRateB) {
    this.historyCloseFeeRate_B = historyCloseFeeRateB;
  }

  public Double getHistoryCloseFeeRate_S() {
    return this.historyCloseFeeRate_S;
  }

  public void setHistoryCloseFeeRate_S(Double historyCloseFeeRateS) {
    this.historyCloseFeeRate_S = historyCloseFeeRateS;
  }

  public Short getSettleFeeAlgr() {
    return this.settleFeeAlgr;
  }

  public void setSettleFeeAlgr(Short settleFeeAlgr) {
    this.settleFeeAlgr = settleFeeAlgr;
  }

  public Short getForceCloseFeeAlgr() {
    return this.forceCloseFeeAlgr;
  }

  public void setForceCloseFeeAlgr(Short forceCloseFeeAlgr) {
    this.forceCloseFeeAlgr = forceCloseFeeAlgr;
  }

  public Double getSettleFeeRate_B() {
    return this.settleFeeRate_B;
  }

  public void setSettleFeeRate_B(Double settleFeeRateB) {
    this.settleFeeRate_B = settleFeeRateB;
  }

  public Double getSettleFeeRate_S() {
    return this.settleFeeRate_S;
  }

  public void setSettleFeeRate_S(Double settleFeeRateS) {
    this.settleFeeRate_S = settleFeeRateS;
  }

  public Double getForceCloseFeeRate_B() {
    return this.forceCloseFeeRate_B;
  }

  public void setForceCloseFeeRate_B(Double forceCloseFeeRateB) {
    this.forceCloseFeeRate_B = forceCloseFeeRateB;
  }

  public Double getForceCloseFeeRate_S() {
    return this.forceCloseFeeRate_S;
  }

  public void setForceCloseFeeRate_S(Double forceCloseFeeRateS) {
    this.forceCloseFeeRate_S = forceCloseFeeRateS;
  }

  public Long getLimitCmdtyQty() {
    return this.limitCmdtyQty;
  }

  public void setLimitCmdtyQty(Long limitCmdtyQty) {
    this.limitCmdtyQty = limitCmdtyQty;
  }

  public Double getSettleMarginRate_B() {
    return this.settleMarginRate_B;
  }

  public void setSettleMarginRate_B(Double settleMarginRateB) {
    this.settleMarginRate_B = settleMarginRateB;
  }

  public Double getSettleMarginRate_S() {
    return this.settleMarginRate_S;
  }

  public void setSettleMarginRate_S(Double settleMarginRateS) {
    this.settleMarginRate_S = settleMarginRateS;
  }

  public Double getAddedTax() {
    return this.addedTax;
  }

  public void setAddedTax(Double addedTax) {
    this.addedTax = addedTax;
  }

  public Short getMarginPriceType() {
    return this.marginPriceType;
  }

  public void setMarginPriceType(Short marginPriceType) {
    this.marginPriceType = marginPriceType;
  }

  public Double getLowestSettleFee() {
    return this.lowestSettleFee;
  }

  public void setLowestSettleFee(Double lowestSettleFee) {
    this.lowestSettleFee = lowestSettleFee;
  }

  public Long getFirmCleanQty() {
    return this.firmCleanQty;
  }

  public void setFirmCleanQty(Long firmCleanQty) {
    this.firmCleanQty = firmCleanQty;
  }

  public Long getReserveCount() {
    return this.reserveCount;
  }

  public void setReserveCount(Long reserveCount) {
    this.reserveCount = reserveCount;
  }

  public Short getSettleMarginAlgr_B() {
    return this.settleMarginAlgr_B;
  }

  public void setSettleMarginAlgr_B(Short settleMarginAlgrB) {
    this.settleMarginAlgr_B = settleMarginAlgrB;
  }

  public Short getSettleMarginAlgr_S() {
    return this.settleMarginAlgr_S;
  }

  public void setSettleMarginAlgr_S(Short settleMarginAlgrS) {
    this.settleMarginAlgr_S = settleMarginAlgrS;
  }

  public Short getPayoutAlgr() {
    return this.payoutAlgr;
  }

  public void setPayoutAlgr(Short payoutAlgr) {
    this.payoutAlgr = payoutAlgr;
  }

  public Double getPayoutRate() {
    return this.payoutRate;
  }

  public void setPayoutRate(Double payoutRate) {
    this.payoutRate = payoutRate;
  }

  public Long getFirmMaxHoldQty() {
    return this.firmMaxHoldQty;
  }

  public void setFirmMaxHoldQty(Long firmMaxHoldQty) {
    this.firmMaxHoldQty = firmMaxHoldQty;
  }

  public Double getAddedTaxFactor() {
    return this.addedTaxFactor;
  }

  public void setAddedTaxFactor(Double addedTaxFactor) {
    this.addedTaxFactor = addedTaxFactor;
  }

  public Short getOrderPrivilege_B() {
    return this.orderPrivilege_B;
  }

  public void setOrderPrivilege_B(Short orderPrivilegeB) {
    this.orderPrivilege_B = orderPrivilegeB;
  }

  public Short getOrderPrivilege_S() {
    return this.orderPrivilege_S;
  }

  public void setOrderPrivilege_S(Short orderPrivilegeS) {
    this.orderPrivilege_S = orderPrivilegeS;
  }

  public Short getSettlePriceType() {
    return this.settlePriceType;
  }

  public void setSettlePriceType(Short settlePriceType) {
    this.settlePriceType = settlePriceType;
  }

  public Long getBeforeDays() {
    return this.beforeDays;
  }

  public void setBeforeDays(Long beforeDays) {
    this.beforeDays = beforeDays;
  }

  public Double getSpecSettlePrice() {
    return this.specSettlePrice;
  }

  public void setSpecSettlePrice(Double specSettlePrice) {
    this.specSettlePrice = specSettlePrice;
  }

  public Short getFirmMaxHoldQtyAlgr() {
    return this.firmMaxHoldQtyAlgr;
  }

  public void setFirmMaxHoldQtyAlgr(Short firmMaxHoldQtyAlgr) {
    this.firmMaxHoldQtyAlgr = firmMaxHoldQtyAlgr;
  }

  public Long getStartPercentQty() {
    return this.startPercentQty;
  }

  public void setStartPercentQty(Long startPercentQty) {
    this.startPercentQty = startPercentQty;
  }

  public Double getMaxPercentLimit() {
    return this.maxPercentLimit;
  }

  public void setMaxPercentLimit(Double maxPercentLimit) {
    this.maxPercentLimit = maxPercentLimit;
  }

  public Long getOneMaxHoldQty() {
    return this.oneMaxHoldQty;
  }

  public void setOneMaxHoldQty(Long oneMaxHoldQty) {
    this.oneMaxHoldQty = oneMaxHoldQty;
  }

  public Integer getMinQuantityMove() {
    return this.minQuantityMove;
  }

  public void setMinQuantityMove(Integer minQuantityMove) {
    this.minQuantityMove = minQuantityMove;
  }

  public Integer getMinSettleMoveQty() {
    return this.minSettleMoveQty;
  }

  public void setMinSettleMoveQty(Integer minSettleMoveQty) {
    this.minSettleMoveQty = minSettleMoveQty;
  }

  public Integer getMinSettleQty() {
    return this.minSettleQty;
  }

  public void setMinSettleQty(Integer minSettleQty) {
    this.minSettleQty = minSettleQty;
  }

  public Double getDelayRecoupRate() {
    return this.delayRecoupRate;
  }

  public void setDelayRecoupRate(Double delayRecoupRate) {
    this.delayRecoupRate = delayRecoupRate;
  }

  public Double getDelayRecoupRate_S() {
    return this.delayRecoupRate_S;
  }

  public void setDelayRecoupRate_S(Double delayRecoupRateS) {
    this.delayRecoupRate_S = delayRecoupRateS;
  }

  public Short getSettleWay() {
    return this.settleWay;
  }

  public void setSettleWay(Short settleWay) {
    this.settleWay = settleWay;
  }

  public Short getDelayFeeWay() {
    return this.delayFeeWay;
  }

  public void setDelayFeeWay(Short delayFeeWay) {
    this.delayFeeWay = delayFeeWay;
  }

  public Double getStoreRecoupRate() {
    return this.storeRecoupRate;
  }

  public void setStoreRecoupRate(Double storeRecoupRate) {
    this.storeRecoupRate = storeRecoupRate;
  }

  public Double getMaxFeeRate() {
    return this.maxFeeRate;
  }

  public void setMaxFeeRate(Double maxFeeRate) {
    this.maxFeeRate = maxFeeRate;
  }

  public Integer getDelaySettlePriceType() {
    return this.delaySettlePriceType;
  }

  public void setDelaySettlePriceType(Integer delaySettlePriceType) {
    this.delaySettlePriceType = delaySettlePriceType;
  }

  public Integer getBeforeDays_M() {
    return this.beforeDays_M;
  }

  public void setBeforeDays_M(Integer beforeDaysM) {
    this.beforeDays_M = beforeDaysM;
  }

  public Integer getAheadSettlePriceType() {
    return this.aheadSettlePriceType;
  }

  public void setAheadSettlePriceType(Integer aheadSettlePriceType) {
    this.aheadSettlePriceType = aheadSettlePriceType;
  }

  public Integer getSettleMarginType() {
    return this.settleMarginType;
  }

  public void setSettleMarginType(Integer settleMarginType) {
    this.settleMarginType = settleMarginType;
  }

  public Long getSideHoldLimitQty() {
    return this.sideHoldLimitQty;
  }

  public void setSideHoldLimitQty(Long sideHoldLimitQty) {
    this.sideHoldLimitQty = sideHoldLimitQty;
  }

  public Integer getHoldDaysLimit()
  {
    return this.holdDaysLimit;
  }

  public void setHoldDaysLimit(Integer holdDaysLimit)
  {
    this.holdDaysLimit = holdDaysLimit;
  }

  public Long getMaxHoldPositionDay()
  {
    return this.maxHoldPositionDay;
  }

  public void setMaxHoldPositionDay(Long maxHoldPositionDay)
  {
    this.maxHoldPositionDay = maxHoldPositionDay;
  }

  public Short getType()
  {
    return this.type;
  }

  public void setType(Short type) {
    this.type = type;
  }

  public Short getType1() {
    return this.type1;
  }

  public void setType1(Short type1) {
    this.type1 = type1;
  }

  public Short getType2() {
    return this.type2;
  }

  public void setType2(Short type2) {
    this.type2 = type2;
  }

  public Short getType3() {
    return this.type3;
  }

  public void setType3(Short type3) {
    this.type3 = type3;
  }

  public Short getType4() {
    return this.type4;
  }

  public void setType4(Short type4) {
    this.type4 = type4;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}