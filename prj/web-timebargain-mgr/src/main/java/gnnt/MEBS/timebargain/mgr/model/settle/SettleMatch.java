package gnnt.MEBS.timebargain.mgr.model.settle;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class SettleMatch extends StandardModel
{
  private static final long serialVersionUID = 7478103997910208866L;

  @ClassDiscription(name=" 配对ID", description="")
  private String matchID;

  @ClassDiscription(name="商品ID", description="")
  private String commodityID;

  @ClassDiscription(name=" 合约因子", description="")
  private Double contractFactor;

  @ClassDiscription(name="交收数量", description="")
  private Integer quantity;

  @ClassDiscription(name="", description="")
  private Double HL_Amount;

  @ClassDiscription(name="状态", description=" 0 未处理，1 处理中，2 处理完成，3 撤销")
  private Integer status;

  @ClassDiscription(name="执行结果", description="1 履约，2 买方违约，3 卖方违约，4 双方违约")
  private Integer result;

  @ClassDiscription(name="交收类型", description="1 到期交收，2 提前交收，3 延期交收")
  private Integer settleType;

  @ClassDiscription(name="买方交易商ID", description="")
  private String firmID_B;

  @ClassDiscription(name="买方交收价", description="")
  private Double buyPrice;

  @ClassDiscription(name="买方参考货款", description="")
  private Double buyPayout_Ref;

  @ClassDiscription(name="已收买方货款", description="")
  private Double buyPayout;

  @ClassDiscription(name="买方交收保证金", description="")
  private Double buyMargin;

  @ClassDiscription(name="收买方违约金", description="")
  private Double takePenalty_B;

  @ClassDiscription(name=" 付买方违约金", description="")
  private Double payPenalty_B;

  @ClassDiscription(name="买方交收盈亏", description="")
  private Double settlePL_B;

  @ClassDiscription(name="买方交收盈亏", description="")
  private String firmID_S;

  @ClassDiscription(name="", description="")
  private Double sellPrice;

  @ClassDiscription(name="卖方交收价", description="")
  private Double sellIncome_Ref;

  @ClassDiscription(name="已付卖方货款", description="")
  private Double sellIncome;

  @ClassDiscription(name="卖方交收保证金", description="")
  private Double sellMargin;

  @ClassDiscription(name="收卖方违约金", description="")
  private Double takePenalty_S;

  @ClassDiscription(name="付卖方违约金", description="")
  private Double payPenalty_S;

  @ClassDiscription(name="卖方交收盈亏", description="")
  private Double settlePL_S;

  @ClassDiscription(name="创建时间", description="")
  private Date createTime;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  @ClassDiscription(name="交收日期", description="")
  private Date settleDate;

  @ClassDiscription(name="修改人", description="")
  private String modifier;

  @ClassDiscription(name="是否货权转移", description="0：未货权转移，1：已货权转移")
  private Integer isTransfer;

  @ClassDiscription(name="交收配对税费", description="")
  private Double buyTax;

  @ClassDiscription(name="商品是否含税", description="0:不含税,1:含税")
  private Integer taxIncluesive;

  public String getMatchID()
  {
    return this.matchID;
  }

  public void setMatchID(String matchID)
  {
    this.matchID = matchID;
  }

  public String getCommodityID()
  {
    return this.commodityID;
  }

  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }

  public Double getContractFactor()
  {
    return this.contractFactor;
  }

  public void setContractFactor(Double contractFactor)
  {
    this.contractFactor = contractFactor;
  }

  public Integer getQuantity()
  {
    return this.quantity;
  }

  public void setQuantity(Integer quantity)
  {
    this.quantity = quantity;
  }

  public Double getHL_Amount()
  {
    return this.HL_Amount;
  }

  public void setHL_Amount(Double hLAmount)
  {
    this.HL_Amount = hLAmount;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public Integer getResult()
  {
    return this.result;
  }

  public void setResult(Integer result)
  {
    this.result = result;
  }

  public Integer getSettleType()
  {
    return this.settleType;
  }

  public void setSettleType(Integer settleType)
  {
    this.settleType = settleType;
  }

  public String getFirmID_B()
  {
    return this.firmID_B;
  }

  public void setFirmID_B(String firmIDB)
  {
    this.firmID_B = firmIDB;
  }

  public Double getBuyPrice()
  {
    return this.buyPrice;
  }

  public void setBuyPrice(Double buyPrice)
  {
    this.buyPrice = buyPrice;
  }

  public Double getBuyPayout_Ref()
  {
    return this.buyPayout_Ref;
  }

  public void setBuyPayout_Ref(Double buyPayoutRef)
  {
    this.buyPayout_Ref = buyPayoutRef;
  }

  public Double getBuyPayout()
  {
    return this.buyPayout;
  }

  public void setBuyPayout(Double buyPayout)
  {
    this.buyPayout = buyPayout;
  }

  public Double getBuyMargin()
  {
    return this.buyMargin;
  }

  public void setBuyMargin(Double buyMargin)
  {
    this.buyMargin = buyMargin;
  }

  public Double getTakePenalty_B()
  {
    return this.takePenalty_B;
  }

  public void setTakePenalty_B(Double takePenaltyB)
  {
    this.takePenalty_B = takePenaltyB;
  }

  public Double getPayPenalty_B()
  {
    return this.payPenalty_B;
  }

  public void setPayPenalty_B(Double payPenaltyB)
  {
    this.payPenalty_B = payPenaltyB;
  }

  public Double getSettlePL_B()
  {
    return this.settlePL_B;
  }

  public void setSettlePL_B(Double settlePLB)
  {
    this.settlePL_B = settlePLB;
  }

  public String getFirmID_S()
  {
    return this.firmID_S;
  }

  public void setFirmID_S(String firmIDS)
  {
    this.firmID_S = firmIDS;
  }

  public Double getSellPrice()
  {
    return this.sellPrice;
  }

  public void setSellPrice(Double sellPrice)
  {
    this.sellPrice = sellPrice;
  }

  public Double getSellIncome_Ref()
  {
    return this.sellIncome_Ref;
  }

  public void setSellIncome_Ref(Double sellIncomeRef)
  {
    this.sellIncome_Ref = sellIncomeRef;
  }

  public Double getSellIncome()
  {
    return this.sellIncome;
  }

  public void setSellIncome(Double sellIncome)
  {
    this.sellIncome = sellIncome;
  }

  public Double getSellMargin()
  {
    return this.sellMargin;
  }

  public void setSellMargin(Double sellMargin)
  {
    this.sellMargin = sellMargin;
  }

  public Double getTakePenalty_S()
  {
    return this.takePenalty_S;
  }

  public void setTakePenalty_S(Double takePenaltyS)
  {
    this.takePenalty_S = takePenaltyS;
  }

  public Double getPayPenalty_S()
  {
    return this.payPenalty_S;
  }

  public void setPayPenalty_S(Double payPenaltyS)
  {
    this.payPenalty_S = payPenaltyS;
  }

  public Double getSettlePL_S()
  {
    return this.settlePL_S;
  }

  public void setSettlePL_S(Double settlePLS)
  {
    this.settlePL_S = settlePLS;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }

  public Date getSettleDate()
  {
    return this.settleDate;
  }

  public void setSettleDate(Date settleDate)
  {
    this.settleDate = settleDate;
  }

  public String getModifier()
  {
    return this.modifier;
  }

  public void setModifier(String modifier)
  {
    this.modifier = modifier;
  }

  public Integer getIsTransfer()
  {
    return this.isTransfer;
  }

  public void setIsTransfer(Integer isTransfer)
  {
    this.isTransfer = isTransfer;
  }

  public Double getBuyTax()
  {
    return this.buyTax;
  }

  public void setBuyTax(Double buyTax)
  {
    this.buyTax = buyTax;
  }

  public Integer getTaxIncluesive()
  {
    return this.taxIncluesive;
  }

  public void setTaxIncluesive(Integer taxIncluesive) {
    this.taxIncluesive = taxIncluesive;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "matchID", this.matchID);
  }
}