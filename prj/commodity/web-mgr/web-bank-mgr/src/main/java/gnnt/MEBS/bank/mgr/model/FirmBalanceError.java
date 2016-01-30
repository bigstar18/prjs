package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class FirmBalanceError extends StandardModel
{
  private static final long serialVersionUID = -3219691371596911498L;

  @ClassDiscription(name="交易时间", description="")
  private Date trandateTime;

  @ClassDiscription(name="操作员", description="")
  private String counterID;

  @ClassDiscription(name="资金汇总帐号", description="")
  private String supacctID;

  @ClassDiscription(name="功能代码", description="")
  private String funcCode;

  @ClassDiscription(name="子账户账号", description="")
  private String custacctID;

  @ClassDiscription(name="子账户名称", description="")
  private String custName;

  @ClassDiscription(name="交易网会员代码", description="")
  private String htirdcustID;

  @ClassDiscription(name="交易网流水号 ", description="")
  private String thirdLogno;

  @ClassDiscription(name="币种", description="")
  private String ccyCode;

  @ClassDiscription(name="当天总冻结资金", description="")
  private Double freezeAmount;

  @ClassDiscription(name="当天总解冻资金", description="")
  private Double unFreezeAmount;

  @ClassDiscription(name="当天成交的总货款(卖方)", description="")
  private Double addTranAmount;

  @ClassDiscription(name="当天成交的总货款(买方) ", description="")
  private Double cuttranAmount;

  @ClassDiscription(name="盈利总金额", description="")
  private Double profitAmount;

  @ClassDiscription(name="亏损总金额", description="")
  private Double lossAmount;

  @ClassDiscription(name=" 交易商当天应付给交易网的手续费", description="")
  private Double tranhandFee;

  @ClassDiscription(name="当天交易总比数", description="")
  private Double trancount;

  @ClassDiscription(name="交易网端最新的交易商可用金额", description="")
  private Double newBalance;

  @ClassDiscription(name="交易网端最新的冻结资金", description="")
  private Double newFreezeAmount;

  @ClassDiscription(name="说明 ", description="")
  private String note;

  @ClassDiscription(name="保留域", description="")
  private String reserve;

  @ClassDiscription(name="错误码", description="")
  private String rspcode;

  @ClassDiscription(name="应答描述 ", description="")
  private String rspmsg;

  @ClassDiscription(name="银行", description="")
  private Bank bank;

  @ClassDiscription(name="信息创建时间", description="")
  private Date createTime;

  public Date getTrandateTime()
  {
    return this.trandateTime;
  }

  public void setTrandateTime(Date trandateTime)
  {
    this.trandateTime = trandateTime;
  }

  public String getCounterID()
  {
    return this.counterID;
  }

  public void setCounterID(String counterID)
  {
    this.counterID = counterID;
  }

  public String getSupacctID()
  {
    return this.supacctID;
  }

  public void setSupacctID(String supacctID)
  {
    this.supacctID = supacctID;
  }

  public String getFuncCode()
  {
    return this.funcCode;
  }

  public void setFuncCode(String funcCode)
  {
    this.funcCode = funcCode;
  }

  public String getCustacctID()
  {
    return this.custacctID;
  }

  public void setCustacctID(String custacctID)
  {
    this.custacctID = custacctID;
  }

  public String getCustName()
  {
    return this.custName;
  }

  public void setCustName(String custName)
  {
    this.custName = custName;
  }

  public String getHtirdcustID()
  {
    return this.htirdcustID;
  }

  public void setHtirdcustID(String htirdcustID)
  {
    this.htirdcustID = htirdcustID;
  }

  public String getThirdLogno()
  {
    return this.thirdLogno;
  }

  public void setThirdLogno(String thirdLogno)
  {
    this.thirdLogno = thirdLogno;
  }

  public String getCcyCode()
  {
    return this.ccyCode;
  }

  public void setCcyCode(String ccyCode)
  {
    this.ccyCode = ccyCode;
  }

  public Double getFreezeAmount()
  {
    return this.freezeAmount;
  }

  public void setFreezeAmount(Double freezeAmount)
  {
    this.freezeAmount = freezeAmount;
  }

  public Double getUnFreezeAmount()
  {
    return this.unFreezeAmount;
  }

  public void setUnFreezeAmount(Double unFreezeAmount)
  {
    this.unFreezeAmount = unFreezeAmount;
  }

  public Double getAddTranAmount()
  {
    return this.addTranAmount;
  }

  public void setAddTranAmount(Double addTranAmount)
  {
    this.addTranAmount = addTranAmount;
  }

  public Double getCuttranAmount()
  {
    return this.cuttranAmount;
  }

  public void setCuttranAmount(Double cuttranAmount)
  {
    this.cuttranAmount = cuttranAmount;
  }

  public Double getProfitAmount()
  {
    return this.profitAmount;
  }

  public void setProfitAmount(Double profitAmount)
  {
    this.profitAmount = profitAmount;
  }

  public Double getLossAmount()
  {
    return this.lossAmount;
  }

  public void setLossAmount(Double lossAmount)
  {
    this.lossAmount = lossAmount;
  }

  public Double getTranhandFee()
  {
    return this.tranhandFee;
  }

  public void setTranhandFee(Double tranhandFee)
  {
    this.tranhandFee = tranhandFee;
  }

  public Double getTrancount()
  {
    return this.trancount;
  }

  public void setTrancount(Double trancount)
  {
    this.trancount = trancount;
  }

  public Double getNewBalance()
  {
    return this.newBalance;
  }

  public void setNewBalance(Double newBalance)
  {
    this.newBalance = newBalance;
  }

  public Double getNewFreezeAmount()
  {
    return this.newFreezeAmount;
  }

  public void setNewFreezeAmount(Double newFreezeAmount)
  {
    this.newFreezeAmount = newFreezeAmount;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String note)
  {
    this.note = note;
  }

  public String getReserve()
  {
    return this.reserve;
  }

  public void setReserve(String reserve)
  {
    this.reserve = reserve;
  }

  public String getRspcode()
  {
    return this.rspcode;
  }

  public void setRspcode(String rspcode)
  {
    this.rspcode = rspcode;
  }

  public String getRspmsg()
  {
    return this.rspmsg;
  }

  public void setRspmsg(String rspmsg)
  {
    this.rspmsg = rspmsg;
  }

  public Bank getBank()
  {
    return this.bank;
  }

  public void setBank(Bank bank)
  {
    this.bank = bank;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}