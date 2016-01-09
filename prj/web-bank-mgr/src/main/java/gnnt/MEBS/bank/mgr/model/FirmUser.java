package gnnt.MEBS.bank.mgr.model;

import gnnt.MEBS.bank.mgr.model.integrated.MFirm;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class FirmUser extends StandardModel
{
  private static final long serialVersionUID = -4967674470053824787L;

  @ClassDiscription(name="交易商代码", description="")
  private String firmID;

  @ClassDiscription(name="交易商", description="")
  private MFirm firm;

  @ClassDiscription(name="交易商名称 ", description="")
  private String name;

  @ClassDiscription(name="单笔最大转账金额 ", description="")
  private Double maxPersgltransMoney;

  @ClassDiscription(name="每天最大转账金额", description="")
  private Double maxPertransMoney;

  @ClassDiscription(name="每天最大转账次数", description="")
  private Long maxPertranscount;

  @ClassDiscription(name="交易商状态", description="0 已注册,1 禁用或未注册,2注销")
  private Integer status;

  @ClassDiscription(name="注册日期", description="")
  private Date registerDate;

  @ClassDiscription(name="注销日期", description="")
  private Date logoutDate;

  @ClassDiscription(name="审核额度", description="")
  private Double maxAuditMoney;

  @ClassDiscription(name="密码", description="")
  private String password;

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }

  public MFirm getFirm()
  {
    return this.firm;
  }

  public void setFirm(MFirm firm)
  {
    this.firm = firm;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Double getMaxPersgltransMoney()
  {
    return this.maxPersgltransMoney;
  }

  public void setMaxPersgltransMoney(Double maxPersgltransMoney)
  {
    this.maxPersgltransMoney = maxPersgltransMoney;
  }

  public Double getMaxPertransMoney()
  {
    return this.maxPertransMoney;
  }

  public void setMaxPertransMoney(Double maxPertransMoney)
  {
    this.maxPertransMoney = maxPertransMoney;
  }

  public Long getMaxPertranscount()
  {
    return this.maxPertranscount;
  }

  public void setMaxPertranscount(Long maxPertranscount)
  {
    this.maxPertranscount = maxPertranscount;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status)
  {
    this.status = status;
  }

  public Date getRegisterDate()
  {
    return this.registerDate;
  }

  public void setRegisterDate(Date registerDate)
  {
    this.registerDate = registerDate;
  }

  public Date getLogoutDate()
  {
    return this.logoutDate;
  }

  public void setLogoutDate(Date logoutDate)
  {
    this.logoutDate = logoutDate;
  }

  public Double getMaxAuditMoney()
  {
    return this.maxAuditMoney;
  }

  public void setMaxAuditMoney(Double maxAuditMoney)
  {
    this.maxAuditMoney = maxAuditMoney;
  }

  public String getPassword()
  {
    return this.password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "firmID", this.firmID);
  }
}