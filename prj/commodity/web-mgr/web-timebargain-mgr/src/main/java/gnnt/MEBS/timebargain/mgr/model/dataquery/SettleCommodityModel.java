package gnnt.MEBS.timebargain.mgr.model.dataquery;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class SettleCommodityModel extends StandardModel
{
  private static final long serialVersionUID = 7221990633995666793L;
  private Date settleProcessDate;

  @ClassDiscription(name="主键（商品代码）", description="")
  private String commodityId;

  @ClassDiscription(name="商品名称", description="")
  private String name;

  @ClassDiscription(name="商品分类ID", description="")
  private Long sortId;

  @ClassDiscription(name="状态", description="")
  private Short status;

  @ClassDiscription(name="合约因子", description="")
  private Double contractFactor;

  @ClassDiscription(name="最小价位变动", description="")
  private Double minPriceMove;

  @ClassDiscription(name="商品品种", description="")
  private Long breedId;

  @ClassDiscription(name="涨跌幅算法", description="")
  private Long spreadAlgr;

  @ClassDiscription(name="涨幅上限", description="")
  private Double spreadUpLmt;

  @ClassDiscription(name="跌幅下限", description="")
  private Double spreadDownLmt;

  @ClassDiscription(name="手续费算法", description="")
  private Long feeAlgr;

  @ClassDiscription(name="手续费系数", description="")
  private Double feeRate_B;

  @ClassDiscription(name="手续费系数", description="")
  private Double feeRate_S;

  @ClassDiscription(name="保证金算法", description="")
  private Long marginAlgr;

  @ClassDiscription(name="交易保证金系数", description="")
  private Double marginRate_B;

  @ClassDiscription(name=" 交易保证金系数", description="")
  private Double marginRate_S;

  @ClassDiscription(name="上市日期", description="")
  private Date marketDate;

  @ClassDiscription(name="交收日期", description="")
  private Date settleDate;

  @ClassDiscription(name="交收相关日期1", description="")
  private Date settleDate1;

  @ClassDiscription(name="买保证金额项1", description="交收前一月首款标准")
  private Double marginItem1;

  @ClassDiscription(name="交收相关日期2", description="")
  private Date settleDate2;

  @ClassDiscription(name="买保证金额项2", description="交收月第一日首款")
  private Double marginItem2;

  @ClassDiscription(name="交收相关日期3", description="")
  private Date settleDate3;

  @ClassDiscription(name="买保证金额项3", description="交收月第十个日首款标准")
  private Double marginItem3;

  @ClassDiscription(name="交收相关日期4", description="")
  private Date settleDate4;

  @ClassDiscription(name="买保证金额项4", description="")
  private Double marginItem4;

  @ClassDiscription(name="价格", description="")
  private Double lastPrice;

  @ClassDiscription(name="卖保证金额项1", description="")
  private Double marginItem1_S;

  @ClassDiscription(name="卖保证金额项2", description="")
  private Double marginItem2_S;

  @ClassDiscription(name="卖保证金额项3", description="")
  private Double marginItem3_S;

  @ClassDiscription(name="卖保证金额项4", description="")
  private Double marginItem4_S;

  @ClassDiscription(name="担保金系数", description="")
  private Double marginAssure_B;

  @ClassDiscription(name="卖担保金系数", description="")
  private Double marginAssure_S;

  @ClassDiscription(name="买担保金款项1", description="")
  private Double marginItemAssure1;

  @ClassDiscription(name="买担保金款项2", description="")
  private Double marginItemAssure2;

  @ClassDiscription(name="买担保金款项3", description="")
  private Double marginItemAssure3;

  @ClassDiscription(name="买担保金款项4", description="")
  private Double marginItemAssure4;

  @ClassDiscription(name="卖担保金款项1", description="")
  private Double marginItemAssure1_S;

  @ClassDiscription(name="卖担保金款项2", description="")
  private Double marginItemAssure2_S;

  @ClassDiscription(name="卖担保金款项3", description="")
  private Double marginItemAssure3_S;

  @ClassDiscription(name="卖担保金款项4", description="")
  private Double marginItemAssure4_S;

  @ClassDiscription(name="今开今平买手续费系数", description="")
  private Double todayCloseFeeRate_B;

  @ClassDiscription(name="今开今平卖手续费系数", description="")
  private Double todayCloseFeeRate_S;

  @ClassDiscription(name="夜单平仓买手续费系数", description="")
  private Double historyCloseFeeRate_B;

  @ClassDiscription(name="隔夜单平仓卖手续费系数", description="")
  private Double historyCloseFeeRate_S;

  @ClassDiscription(name="商品数量限制", description="单个交易商品的总订货量不能大于此值，-1表示不限制")
  private Long limitCmdtyQty;

  @ClassDiscription(name="交收手续费算法", description="")
  private Long settleFeeAlgr;

  @ClassDiscription(name="交收买手续费系数", description="")
  private Double settleFeeRate_B;

  @ClassDiscription(name="收卖手续费系数", description="")
  private Double settleFeeRate_S;

  @ClassDiscription(name="为转让手续费算法", description="")
  private Long forceCloseFeeAlgr;

  @ClassDiscription(name="代为转让买手续费系数", description="")
  private Double forceCloseFeeRate_B;

  @ClassDiscription(name="代为转让卖手续费系数", description="")
  private Double forceCloseFeeRate_S;

  @ClassDiscription(name=" 买交收保证金算法", description="")
  private Long settleMarginAlgr_B;

  @ClassDiscription(name="交收保证金买系数", description="")
  private Double settleMarginRate_B;

  @ClassDiscription(name="卖交收保证金算法", description="")
  private Long settleMarginAlgr_S;

  @ClassDiscription(name="交收保证金卖系数", description="")
  private Double settleMarginRate_S;

  @ClassDiscription(name="行情订货量", description="")
  private Long reserveCount;

  @ClassDiscription(name="增值税率", description="")
  private Double addedTax;

  @ClassDiscription(name="保证金计算方式", description="")
  private Long marginPriceType;

  @ClassDiscription(name="交收手续费最低金额", description="")
  private Double lowestSettleFee;

  @ClassDiscription(name="交易商净订货量", description="")
  private Long firmCleanQty;

  @ClassDiscription(name="最大持仓量", description="最大持仓量，单个交易商总持仓量不能超过此数量。")
  private Long firmMaxHoldQty;

  @ClassDiscription(name="交收货款算法", description="")
  private Long payoutAlgr;

  @ClassDiscription(name="收货款系数", description="")
  private Double payoutRate;

  @ClassDiscription(name="增值税系数", description="")
  private Double addedTaxFactor;

  @ClassDiscription(name="交收相关日期5", description="")
  private Date settleDate5;

  @ClassDiscription(name="买保证金款项5", description="")
  private Double marginItem5;

  @ClassDiscription(name="保证金款项5", description="")
  private Double marginItem5_S;

  @ClassDiscription(name="买担保金款项5", description="")
  private Double marginItemAssure5;

  @ClassDiscription(name="卖担保金款项5", description="")
  private Double marginItemAssure5_S;

  @ClassDiscription(name="交收价计算方式", description="")
  private Long settlePriceType;

  @ClassDiscription(name="计算交收价的提前日", description="")
  private Long beforeDays;

  @ClassDiscription(name="收结算价", description="")
  private Double specSettlePrice;

  @ClassDiscription(name="买方委托权限", description="")
  private Long orderPrivilege_B;

  @ClassDiscription(name="卖方委托权限", description="")
  private Long orderPrivilege_S;

  @ClassDiscription(name="交易商订货量限制算法", description="")
  private Long firmMaxHoldQtyAlgr;

  @ClassDiscription(name="商品百分比阀值量", description="")
  private Long startPercentQty;

  @ClassDiscription(name="交易商订货量最大百分比", description="")
  private Double maxPercentLimit;

  @ClassDiscription(name="单笔最大委托量", description="")
  private Long oneMaxHoldQty;

  @ClassDiscription(name="最小变动数量", description="")
  private Long minQuantityMove;

  @ClassDiscription(name="买方延期补偿比率", description="")
  private Double delayRecoupRate;

  @ClassDiscription(name="交收方式", description="0：中远期 1：连续现货 2：专场交易    ")
  private Long settleWay;

  @ClassDiscription(name="延期费收取方式", description="")
  private Long delayFeeWay;

  @ClassDiscription(name="最大交易手续费系数", description="")
  private Double maxFeeRate;

  @ClassDiscription(name="最小交割单位", description="")
  private Long minSettleMoveQty;

  @ClassDiscription(name="仓储补偿比率", description="")
  private Double storeRecoupRate;

  @ClassDiscription(name="最小交割数量", description="")
  private Long minSettleQty;

  @ClassDiscription(name="卖方延期补偿比率", description="")
  private Double delayRecoupRate_S;

  @ClassDiscription(name="提前交收交收价格类型 ", description="")
  private Long aheadSettlePriceType;

  @ClassDiscription(name="商品单边订货量限制", description="0：以结算价作为交收价格  ，1：以订立价作为交收价格")
  private Long delaySettlePriceType;

  @ClassDiscription(name="交收保证金计算方式", description="")
  private Long settleMarginType;

  @ClassDiscription(name=" 提前几日计算交收保证金价", description="")
  private Long beforeDays_M;

  @ClassDiscription(name="商品单边订货量限制", description="-1不限制")
  private Long sideHoldLimitQty;

  public static long getSerialversionuid()
  {
    return 7221990633995666793L;
  }

  public Date getSettleProcessDate()
  {
    return this.settleProcessDate;
  }

  public void setSettleProcessDate(Date settleProcessDate)
  {
    this.settleProcessDate = settleProcessDate;
  }

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Long getSortId()
  {
    return this.sortId;
  }

  public void setSortId(Long sortId)
  {
    this.sortId = sortId;
  }

  public Short getStatus()
  {
    return this.status;
  }

  public void setStatus(Short status)
  {
    this.status = status;
  }

  public Double getContractFactor()
  {
    return this.contractFactor;
  }

  public void setContractFactor(Double contractFactor)
  {
    this.contractFactor = contractFactor;
  }

  public Double getMinPriceMove()
  {
    return this.minPriceMove;
  }

  public void setMinPriceMove(Double minPriceMove)
  {
    this.minPriceMove = minPriceMove;
  }

  public Long getBreedId()
  {
    return this.breedId;
  }

  public void setBreedId(Long breedId)
  {
    this.breedId = breedId;
  }

  public Long getSpreadAlgr()
  {
    return this.spreadAlgr;
  }

  public void setSpreadAlgr(Long spreadAlgr)
  {
    this.spreadAlgr = spreadAlgr;
  }

  public Double getSpreadUpLmt()
  {
    return this.spreadUpLmt;
  }

  public void setSpreadUpLmt(Double spreadUpLmt)
  {
    this.spreadUpLmt = spreadUpLmt;
  }

  public Double getSpreadDownLmt()
  {
    return this.spreadDownLmt;
  }

  public void setSpreadDownLmt(Double spreadDownLmt)
  {
    this.spreadDownLmt = spreadDownLmt;
  }

  public Long getFeeAlgr()
  {
    return this.feeAlgr;
  }

  public void setFeeAlgr(Long feeAlgr)
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

  public Long getMarginAlgr()
  {
    return this.marginAlgr;
  }

  public void setMarginAlgr(Long marginAlgr)
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

  public Date getMarketDate()
  {
    return this.marketDate;
  }

  public void setMarketDate(Date marketDate)
  {
    this.marketDate = marketDate;
  }

  public Date getSettleDate()
  {
    return this.settleDate;
  }

  public void setSettleDate(Date settleDate)
  {
    this.settleDate = settleDate;
  }

  public Date getSettleDate1()
  {
    return this.settleDate1;
  }

  public void setSettleDate1(Date settleDate1)
  {
    this.settleDate1 = settleDate1;
  }

  public Double getMarginItem1()
  {
    return this.marginItem1;
  }

  public void setMarginItem1(Double marginItem1)
  {
    this.marginItem1 = marginItem1;
  }

  public Date getSettleDate2()
  {
    return this.settleDate2;
  }

  public void setSettleDate2(Date settleDate2)
  {
    this.settleDate2 = settleDate2;
  }

  public Double getMarginItem2()
  {
    return this.marginItem2;
  }

  public void setMarginItem2(Double marginItem2)
  {
    this.marginItem2 = marginItem2;
  }

  public Date getSettleDate3()
  {
    return this.settleDate3;
  }

  public void setSettleDate3(Date settleDate3)
  {
    this.settleDate3 = settleDate3;
  }

  public Double getMarginItem3()
  {
    return this.marginItem3;
  }

  public void setMarginItem3(Double marginItem3)
  {
    this.marginItem3 = marginItem3;
  }

  public Date getSettleDate4()
  {
    return this.settleDate4;
  }

  public void setSettleDate4(Date settleDate4)
  {
    this.settleDate4 = settleDate4;
  }

  public Double getMarginItem4()
  {
    return this.marginItem4;
  }

  public void setMarginItem4(Double marginItem4)
  {
    this.marginItem4 = marginItem4;
  }

  public Double getLastPrice()
  {
    return this.lastPrice;
  }

  public void setLastPrice(Double lastPrice)
  {
    this.lastPrice = lastPrice;
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

  public Long getLimitCmdtyQty()
  {
    return this.limitCmdtyQty;
  }

  public void setLimitCmdtyQty(Long limitCmdtyQty)
  {
    this.limitCmdtyQty = limitCmdtyQty;
  }

  public Long getSettleFeeAlgr()
  {
    return this.settleFeeAlgr;
  }

  public void setSettleFeeAlgr(Long settleFeeAlgr)
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

  public Long getForceCloseFeeAlgr()
  {
    return this.forceCloseFeeAlgr;
  }

  public void setForceCloseFeeAlgr(Long forceCloseFeeAlgr)
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

  public Long getSettleMarginAlgr_B()
  {
    return this.settleMarginAlgr_B;
  }

  public void setSettleMarginAlgr_B(Long settleMarginAlgrB)
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

  public Long getSettleMarginAlgr_S()
  {
    return this.settleMarginAlgr_S;
  }

  public void setSettleMarginAlgr_S(Long settleMarginAlgrS)
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

  public Long getReserveCount()
  {
    return this.reserveCount;
  }

  public void setReserveCount(Long reserveCount)
  {
    this.reserveCount = reserveCount;
  }

  public Double getAddedTax()
  {
    return this.addedTax;
  }

  public void setAddedTax(Double addedTax)
  {
    this.addedTax = addedTax;
  }

  public Long getMarginPriceType()
  {
    return this.marginPriceType;
  }

  public void setMarginPriceType(Long marginPriceType)
  {
    this.marginPriceType = marginPriceType;
  }

  public Double getLowestSettleFee()
  {
    return this.lowestSettleFee;
  }

  public void setLowestSettleFee(Double lowestSettleFee)
  {
    this.lowestSettleFee = lowestSettleFee;
  }

  public Long getFirmCleanQty()
  {
    return this.firmCleanQty;
  }

  public void setFirmCleanQty(Long firmCleanQty)
  {
    this.firmCleanQty = firmCleanQty;
  }

  public Long getFirmMaxHoldQty()
  {
    return this.firmMaxHoldQty;
  }

  public void setFirmMaxHoldQty(Long firmMaxHoldQty)
  {
    this.firmMaxHoldQty = firmMaxHoldQty;
  }

  public Long getPayoutAlgr()
  {
    return this.payoutAlgr;
  }

  public void setPayoutAlgr(Long payoutAlgr)
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

  public Double getAddedTaxFactor()
  {
    return this.addedTaxFactor;
  }

  public void setAddedTaxFactor(Double addedTaxFactor)
  {
    this.addedTaxFactor = addedTaxFactor;
  }

  public Date getSettleDate5()
  {
    return this.settleDate5;
  }

  public void setSettleDate5(Date settleDate5)
  {
    this.settleDate5 = settleDate5;
  }

  public Double getMarginItem5()
  {
    return this.marginItem5;
  }

  public void setMarginItem5(Double marginItem5)
  {
    this.marginItem5 = marginItem5;
  }

  public Double getMarginItem5_S()
  {
    return this.marginItem5_S;
  }

  public void setMarginItem5_S(Double marginItem5S)
  {
    this.marginItem5_S = marginItem5S;
  }

  public Double getMarginItemAssure5()
  {
    return this.marginItemAssure5;
  }

  public void setMarginItemAssure5(Double marginItemAssure5)
  {
    this.marginItemAssure5 = marginItemAssure5;
  }

  public Double getMarginItemAssure5_S()
  {
    return this.marginItemAssure5_S;
  }

  public void setMarginItemAssure5_S(Double marginItemAssure5S)
  {
    this.marginItemAssure5_S = marginItemAssure5S;
  }

  public Long getSettlePriceType()
  {
    return this.settlePriceType;
  }

  public void setSettlePriceType(Long settlePriceType)
  {
    this.settlePriceType = settlePriceType;
  }

  public Long getBeforeDays()
  {
    return this.beforeDays;
  }

  public void setBeforeDays(Long beforeDays)
  {
    this.beforeDays = beforeDays;
  }

  public Double getSpecSettlePrice()
  {
    return this.specSettlePrice;
  }

  public void setSpecSettlePrice(Double specSettlePrice)
  {
    this.specSettlePrice = specSettlePrice;
  }

  public Long getOrderPrivilege_B()
  {
    return this.orderPrivilege_B;
  }

  public void setOrderPrivilege_B(Long orderPrivilegeB)
  {
    this.orderPrivilege_B = orderPrivilegeB;
  }

  public Long getOrderPrivilege_S()
  {
    return this.orderPrivilege_S;
  }

  public void setOrderPrivilege_S(Long orderPrivilegeS)
  {
    this.orderPrivilege_S = orderPrivilegeS;
  }

  public Long getFirmMaxHoldQtyAlgr()
  {
    return this.firmMaxHoldQtyAlgr;
  }

  public void setFirmMaxHoldQtyAlgr(Long firmMaxHoldQtyAlgr)
  {
    this.firmMaxHoldQtyAlgr = firmMaxHoldQtyAlgr;
  }

  public Long getStartPercentQty()
  {
    return this.startPercentQty;
  }

  public void setStartPercentQty(Long startPercentQty)
  {
    this.startPercentQty = startPercentQty;
  }

  public Double getMaxPercentLimit()
  {
    return this.maxPercentLimit;
  }

  public void setMaxPercentLimit(Double maxPercentLimit)
  {
    this.maxPercentLimit = maxPercentLimit;
  }

  public Long getOneMaxHoldQty()
  {
    return this.oneMaxHoldQty;
  }

  public void setOneMaxHoldQty(Long oneMaxHoldQty)
  {
    this.oneMaxHoldQty = oneMaxHoldQty;
  }

  public Long getMinQuantityMove()
  {
    return this.minQuantityMove;
  }

  public void setMinQuantityMove(Long minQuantityMove)
  {
    this.minQuantityMove = minQuantityMove;
  }

  public Double getDelayRecoupRate()
  {
    return this.delayRecoupRate;
  }

  public void setDelayRecoupRate(Double delayRecoupRate)
  {
    this.delayRecoupRate = delayRecoupRate;
  }

  public Long getSettleWay()
  {
    return this.settleWay;
  }

  public void setSettleWay(Long settleWay)
  {
    this.settleWay = settleWay;
  }

  public Long getDelayFeeWay()
  {
    return this.delayFeeWay;
  }

  public void setDelayFeeWay(Long delayFeeWay)
  {
    this.delayFeeWay = delayFeeWay;
  }

  public Double getMaxFeeRate()
  {
    return this.maxFeeRate;
  }

  public void setMaxFeeRate(Double maxFeeRate)
  {
    this.maxFeeRate = maxFeeRate;
  }

  public Long getMinSettleMoveQty()
  {
    return this.minSettleMoveQty;
  }

  public void setMinSettleMoveQty(Long minSettleMoveQty)
  {
    this.minSettleMoveQty = minSettleMoveQty;
  }

  public Double getStoreRecoupRate()
  {
    return this.storeRecoupRate;
  }

  public void setStoreRecoupRate(Double storeRecoupRate)
  {
    this.storeRecoupRate = storeRecoupRate;
  }

  public Long getMinSettleQty()
  {
    return this.minSettleQty;
  }

  public void setMinSettleQty(Long minSettleQty)
  {
    this.minSettleQty = minSettleQty;
  }

  public Double getDelayRecoupRate_S()
  {
    return this.delayRecoupRate_S;
  }

  public void setDelayRecoupRate_S(Double delayRecoupRateS)
  {
    this.delayRecoupRate_S = delayRecoupRateS;
  }

  public Long getAheadSettlePriceType()
  {
    return this.aheadSettlePriceType;
  }

  public void setAheadSettlePriceType(Long aheadSettlePriceType)
  {
    this.aheadSettlePriceType = aheadSettlePriceType;
  }

  public Long getDelaySettlePriceType()
  {
    return this.delaySettlePriceType;
  }

  public void setDelaySettlePriceType(Long delaySettlePriceType)
  {
    this.delaySettlePriceType = delaySettlePriceType;
  }

  public Long getSettleMarginType()
  {
    return this.settleMarginType;
  }

  public void setSettleMarginType(Long settleMarginType)
  {
    this.settleMarginType = settleMarginType;
  }

  public Long getBeforeDays_M()
  {
    return this.beforeDays_M;
  }

  public void setBeforeDays_M(Long beforeDaysM)
  {
    this.beforeDays_M = beforeDaysM;
  }

  public Long getSideHoldLimitQty()
  {
    return this.sideHoldLimitQty;
  }

  public void setSideHoldLimitQty(Long sideHoldLimitQty)
  {
    this.sideHoldLimitQty = sideHoldLimitQty;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}