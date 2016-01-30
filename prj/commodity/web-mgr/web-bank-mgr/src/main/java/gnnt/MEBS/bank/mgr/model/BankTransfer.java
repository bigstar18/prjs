package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class BankTransfer extends StandardModel
{
  private static final long serialVersionUID = 6396337746374792113L;

  @ClassDiscription(name="转账编号", description="")
  private Long id;

  @ClassDiscription(name="转出银行编号", description="")
  private String payBankID;

  @ClassDiscription(name="转入银行编号", description="")
  private String recBankID;

  @ClassDiscription(name="冻结资金", description="")
  private Double money;

  @ClassDiscription(name="钱币种类", description="0 为人民币")
  private Integer moneyType;

  @ClassDiscription(name="银行流水号", description="")
  private String funID;

  @ClassDiscription(name="市场流水号 ", description="")
  private String maerketID;

  @ClassDiscription(name="备注信息 ", description="")
  private String note;

  @ClassDiscription(name="状态", description="\t * 0 成功,1 失败,2 处理中,3 一次审核,4 二次审核,5 银行返回信息为空,6 银行返回市场流水号和市场保存流水号不一致,13 市场假银行出入金待审核状态")
  private Integer status;

  @ClassDiscription(name=" 创建时间", description="")
  private Date createTime;

  @ClassDiscription(name="修改时间 ", description="")
  private Date updateTime;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getPayBankID()
  {
    return this.payBankID;
  }

  public void setPayBankID(String payBankID)
  {
    this.payBankID = payBankID;
  }

  public String getRecBankID()
  {
    return this.recBankID;
  }

  public void setRecBankID(String recBankID)
  {
    this.recBankID = recBankID;
  }

  public Double getMoney()
  {
    return this.money;
  }

  public void setMoney(Double money)
  {
    this.money = money;
  }

  public Integer getMoneyType()
  {
    return this.moneyType;
  }

  public void setMoneyType(Integer moneyType)
  {
    this.moneyType = moneyType;
  }

  public String getFunID()
  {
    return this.funID;
  }

  public void setFunID(String funID)
  {
    this.funID = funID;
  }

  public String getMaerketID()
  {
    return this.maerketID;
  }

  public void setMaerketID(String maerketID)
  {
    this.maerketID = maerketID;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String note)
  {
    this.note = note;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public Date getUpdateTime()
  {
    return this.updateTime;
  }

  public void setUpdateTime(Date updateTime)
  {
    this.updateTime = updateTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "id", this.id);
  }
}