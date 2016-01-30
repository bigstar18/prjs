package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class FundFlowF extends StandardModel
{
  private static final long serialVersionUID = 487654776733350247L;

  @ClassDiscription(name="资金流水ID", description="")
  private Long fundFlowId;

  @ClassDiscription(name="成交合同号", description="")
  private String contractNo;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="发生额", description="")
  private Double amount;

  @ClassDiscription(name="资金余额", description="")
  private Double balance;

  @ClassDiscription(name="附加帐金额", description="")
  private Double appendAmount;

  @ClassDiscription(name="凭证号", description="")
  private Long voucherNo;

  @ClassDiscription(name="发生日期", description="")
  private Date createTime;

  @ClassDiscription(name="交易商", description="")
  private String firmId;

  @ClassDiscription(name="摘要对应业务代码", description="")
  private SummaryF summaryF;

  public String getFirmId()
  {
    return this.firmId;
  }

  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }

  public SummaryF getSummaryF()
  {
    return this.summaryF;
  }

  public void setSummaryF(SummaryF paramSummaryF)
  {
    this.summaryF = paramSummaryF;
  }

  public Long getFundFlowId()
  {
    return this.fundFlowId;
  }

  public void setFundFlowId(Long paramLong)
  {
    this.fundFlowId = paramLong;
  }

  public String getContractNo()
  {
    return this.contractNo;
  }

  public void setContractNo(String paramString)
  {
    this.contractNo = paramString;
  }

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public void setCommodityId(String paramString)
  {
    this.commodityId = paramString;
  }

  public Double getAmount()
  {
    return this.amount;
  }

  public void setAmount(Double paramDouble)
  {
    this.amount = paramDouble;
  }

  public Double getBalance()
  {
    return this.balance;
  }

  public void setBalance(Double paramDouble)
  {
    this.balance = paramDouble;
  }

  public Double getAppendAmount()
  {
    return this.appendAmount;
  }

  public void setAppendAmount(Double paramDouble)
  {
    this.appendAmount = paramDouble;
  }

  public Long getVoucherNo()
  {
    return this.voucherNo;
  }

  public void setVoucherNo(Long paramLong)
  {
    this.voucherNo = paramLong;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "fundFlowId", this.fundFlowId);
  }
}