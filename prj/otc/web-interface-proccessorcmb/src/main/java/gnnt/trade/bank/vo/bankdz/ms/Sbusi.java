package gnnt.trade.bank.vo.bankdz.ms;

import java.io.Serializable;

public class Sbusi
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String serialNo;
  private String bOrganId;
  private String bCustAcct;
  private String sOrganId;
  private String sBranchId;
  private String sCustType;
  private String sCustAcct;
  private String MoneyType;
  private double money;
  private int TNDays;
  private String info;
  private String procFlag;
  private double inMoney;
  private double outMoney;
  
  public String getSerialNo()
  {
    return this.serialNo;
  }
  
  public void setSerialNo(String serialNo)
  {
    this.serialNo = serialNo;
  }
  
  public String getbOrganId()
  {
    return this.bOrganId;
  }
  
  public void setbOrganId(String bOrganId)
  {
    this.bOrganId = bOrganId;
  }
  
  public String getbCustAcct()
  {
    return this.bCustAcct;
  }
  
  public void setbCustAcct(String bCustAcct)
  {
    this.bCustAcct = bCustAcct;
  }
  
  public String getsOrganId()
  {
    return this.sOrganId;
  }
  
  public void setsOrganId(String sOrganId)
  {
    this.sOrganId = sOrganId;
  }
  
  public String getsBranchId()
  {
    return this.sBranchId;
  }
  
  public void setsBranchId(String sBranchId)
  {
    this.sBranchId = sBranchId;
  }
  
  public String getsCustType()
  {
    return this.sCustType;
  }
  
  public void setsCustType(String sCustType)
  {
    this.sCustType = sCustType;
  }
  
  public String getsCustAcct()
  {
    return this.sCustAcct;
  }
  
  public void setsCustAcct(String sCustAcct)
  {
    this.sCustAcct = sCustAcct;
  }
  
  public String getMoneyType()
  {
    return this.MoneyType;
  }
  
  public void setMoneyType(String moneyType)
  {
    this.MoneyType = moneyType;
  }
  
  public double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(double money)
  {
    this.money = money;
  }
  
  public int getTNDays()
  {
    return this.TNDays;
  }
  
  public void setTNDays(int tNDays)
  {
    this.TNDays = tNDays;
  }
  
  public String getInfo()
  {
    return this.info;
  }
  
  public void setInfo(String info)
  {
    this.info = info;
  }
  
  public String getProcFlag()
  {
    return this.procFlag;
  }
  
  public void setProcFlag(String procFlag)
  {
    this.procFlag = procFlag;
  }
  
  public double getInMoney()
  {
    return this.inMoney;
  }
  
  public void setInMoney(double inMoney)
  {
    this.inMoney = inMoney;
  }
  
  public double getOutMoney()
  {
    return this.outMoney;
  }
  
  public void setOutMoney(double outMoney)
  {
    this.outMoney = outMoney;
  }
}
