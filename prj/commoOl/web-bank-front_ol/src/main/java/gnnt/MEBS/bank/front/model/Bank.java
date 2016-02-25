package gnnt.MEBS.bank.front.model;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;
import java.util.Set;

public class Bank
  extends StandardModel
{
  private static final long serialVersionUID = -3602159712250065907L;
  @ClassDiscription(name="银行编号", description="")
  private String bankID;
  @ClassDiscription(name="银行名称", description="")
  private String bankName;
  @ClassDiscription(name="单笔最大转账金额", description="")
  private Double maxPersgltransMoney;
  @ClassDiscription(name="每日最大转账金额", description="")
  private Double maxPertransMoney;
  @ClassDiscription(name="每日最大转账次数", description="")
  private Integer maxPertransCount;
  @ClassDiscription(name="适配器实现类名称", description="")
  private String adapterclassName;
  @ClassDiscription(name="银行状态", description="0 可用,1 不可用")
  private Integer validflag;
  @ClassDiscription(name="当个银行出金审核额度", description="")
  private Double maxAuditMoney;
  @ClassDiscription(name="银行起始转账时间", description="")
  private String begintime;
  @ClassDiscription(name="银行结束转账时间", description="")
  private String endTime;
  @ClassDiscription(name="是否受到交易日和交易时间的约束", description="0 受双重约束,1 不受约束,2 受交易日约束,3 受交易时间约束")
  private Integer control;
  @ClassDiscription(name="交易商和银行绑定列表", description="")
  private Set<FirmIDAndAccount> firmIDAndAccountSet;
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  public void setBankID(String bankID)
  {
    this.bankID = bankID;
  }
  
  public String getBankName()
  {
    return this.bankName;
  }
  
  public void setBankName(String bankName)
  {
    this.bankName = bankName;
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
  
  public Integer getMaxPertransCount()
  {
    return this.maxPertransCount;
  }
  
  public void setMaxPertransCount(Integer maxPertransCount)
  {
    this.maxPertransCount = maxPertransCount;
  }
  
  public String getAdapterclassName()
  {
    return this.adapterclassName;
  }
  
  public void setAdapterclassName(String adapterclassName)
  {
    this.adapterclassName = adapterclassName;
  }
  
  public Integer getValidflag()
  {
    return this.validflag;
  }
  
  public void setValidflag(Integer validflag)
  {
    this.validflag = validflag;
  }
  
  public Double getMaxAuditMoney()
  {
    return this.maxAuditMoney;
  }
  
  public void setMaxAuditMoney(Double maxAuditMoney)
  {
    this.maxAuditMoney = maxAuditMoney;
  }
  
  public String getBegintime()
  {
    return this.begintime;
  }
  
  public void setBegintime(String begintime)
  {
    this.begintime = begintime;
  }
  
  public String getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }
  
  public Integer getControl()
  {
    return this.control;
  }
  
  public void setControl(Integer control)
  {
    this.control = control;
  }
  
  public Set<FirmIDAndAccount> getFirmIDAndAccountSet()
  {
    return this.firmIDAndAccountSet;
  }
  
  public void setFirmIDAndAccountSet(Set<FirmIDAndAccount> firmIDAndAccountSet)
  {
    this.firmIDAndAccountSet = firmIDAndAccountSet;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("bankID", this.bankID);
  }
}
