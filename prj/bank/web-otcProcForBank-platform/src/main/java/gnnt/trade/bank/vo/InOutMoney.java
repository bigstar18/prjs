package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class InOutMoney
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public String firmID;
  public String contact;
  public String account;
  public String accountPwd;
  public double money;
  public String remark;
  public String InOutStart;
  public String personName;
  public String amoutDate;
  public String ortherBankName;
  public String ortherAccount;
  public String payChannel;
  public String systemID;
  public String sysFirmID;
  public String sysToSys;
  public long actionID;
  public long sysAciontID;
  public String funID;
  public String defaultSystem;
  public boolean outFlag;
  public Timestamp bankTime;
  public int type;
  public AbcInfoValue abcInfo;
  public String inOutMoneyFlag;
  public String pwd;
  
  public String getPwd()
  {
    return this.pwd;
  }
  
  public void setPwd(String pwd)
  {
    this.pwd = pwd;
  }
  
  public String getInOutMoneyFlag()
  {
    return this.inOutMoneyFlag;
  }
  
  public void setInOutMoneyFlag(String inOutMoneyFlag)
  {
    this.inOutMoneyFlag = inOutMoneyFlag;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int type)
  {
    this.type = type;
  }
  
  public AbcInfoValue getAbcInfo()
  {
    return this.abcInfo;
  }
  
  public void setAbcInfo(AbcInfoValue abcInfo)
  {
    this.abcInfo = abcInfo;
  }
  
  public Timestamp getBankTime()
  {
    return this.bankTime;
  }
  
  public void setBankTime(Timestamp bankTime)
  {
    this.bankTime = bankTime;
  }
  
  public String getDefaultSystem()
  {
    return this.defaultSystem;
  }
  
  public void setDefaultSystem(String defaultSystem)
  {
    this.defaultSystem = defaultSystem;
  }
  
  public String getFunID()
  {
    return this.funID;
  }
  
  public void setFunID(String funID)
  {
    this.funID = funID;
  }
  
  public long getSysAciontID()
  {
    return this.sysAciontID;
  }
  
  public void setSysAciontID(long sysAciontID)
  {
    this.sysAciontID = sysAciontID;
  }
  
  public long getActionID()
  {
    return this.actionID;
  }
  
  public void setActionID(long actionID)
  {
    this.actionID = actionID;
  }
  
  public String getSystemID()
  {
    return this.systemID;
  }
  
  public void setSystemID(String systemID)
  {
    this.systemID = systemID;
  }
  
  public String getSysFirmID()
  {
    return this.sysFirmID;
  }
  
  public void setSysFirmID(String sysFirmID)
  {
    this.sysFirmID = sysFirmID;
  }
  
  public String getSysToSys()
  {
    return this.sysToSys;
  }
  
  public void setSysToSys(String sysToSys)
  {
    this.sysToSys = sysToSys;
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  public void setBankID(String bankID)
  {
    this.bankID = bankID;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  public String getAccount()
  {
    return this.account;
  }
  
  public void setAccount(String account)
  {
    this.account = account;
  }
  
  public String getAccountPwd()
  {
    return this.accountPwd;
  }
  
  public void setAccountPwd(String accountPwd)
  {
    this.accountPwd = accountPwd;
  }
  
  public double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(double money)
  {
    this.money = money;
  }
  
  public String getRemark()
  {
    return this.remark;
  }
  
  public void setRemark(String remark)
  {
    this.remark = remark;
  }
  
  public String getInOutStart()
  {
    return this.InOutStart;
  }
  
  public void setInOutStart(String inOutStart)
  {
    this.InOutStart = inOutStart;
  }
  
  public String getPersonName()
  {
    return this.personName;
  }
  
  public void setPersonName(String personName)
  {
    this.personName = personName;
  }
  
  public String getAmoutDate()
  {
    return this.amoutDate;
  }
  
  public void setAmoutDate(String amoutDate)
  {
    this.amoutDate = amoutDate;
  }
  
  public String getOrtherBankName()
  {
    return this.ortherBankName;
  }
  
  public void setOrtherBankName(String ortherBankName)
  {
    this.ortherBankName = ortherBankName;
  }
  
  public String getOrtherAccount()
  {
    return this.ortherAccount;
  }
  
  public void setOrtherAccount(String ortherAccount)
  {
    this.ortherAccount = ortherAccount;
  }
  
  public String getPayChannel()
  {
    return this.payChannel;
  }
  
  public void setPayChannel(String payChannel)
  {
    this.payChannel = payChannel;
  }
  
  public boolean getOutFlag()
  {
    return this.outFlag;
  }
  
  public void setOutFlag(boolean outFlag)
  {
    this.outFlag = outFlag;
  }
  
  public String getContact()
  {
    return this.contact;
  }
  
  public void setContact(String contact)
  {
    this.contact = contact;
  }
}
