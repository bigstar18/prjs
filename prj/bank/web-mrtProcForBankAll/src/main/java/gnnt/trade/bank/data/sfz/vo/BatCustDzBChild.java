package gnnt.trade.bank.data.sfz.vo;

import java.io.PrintStream;
import java.io.Serializable;

public class BatCustDzBChild
  extends FileFathor
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected int preferencenum = 10;
  public String CustAcctId;
  private int CustAcctIdlength = 32;
  public String CustName;
  private int CustNamelength = 120;
  public String ThirdCustId;
  private int ThirdCustIdlength = 32;
  public int Status;
  private int Statuslength = 1;
  public int Type;
  private int Typelength = 1;
  public int IsTrueCont;
  private int IsTrueContlength = 1;
  public double Balance;
  private int Balancelength = 18;
  public double UsrBalance;
  private int UsrBalancelength = 18;
  public double FrozenBalance;
  private int FrozenBalancelength = 18;
  public double Interest;
  private int Interestlength = 18;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("CustAcctId[" + this.CustAcctId + "]" + str);
    sb.append("CustName[" + this.CustName + "]" + str);
    sb.append("ThirdCustId[" + this.ThirdCustId + "]" + str);
    sb.append("Status[" + this.Status + "]" + str);
    sb.append("Type[" + this.Type + "]" + str);
    sb.append("IsTrueCont[" + this.IsTrueCont + "]" + str);
    sb.append("Balance[" + this.Balance + "]" + str);
    sb.append("UsrBalance[" + this.UsrBalance + "]" + str);
    sb.append("FrozenBalance[" + this.FrozenBalance + "]" + str);
    sb.append("Interest[" + this.Interest + "]" + str);
    return sb.toString();
  }
  
  public void getBody(String str)
    throws Exception
  {
    if ((str == null) || (str.trim().length() <= 0)) {
      throw new Exception("传入的信息为空");
    }
    String[] merthods = str.split(this.separator, -1);
    if (merthods.length < this.preferencenum) {
      throw new Exception("传入的信息个数小于需要信息个数");
    }
    if (merthods.length > this.preferencenum + 1) {
      System.out.println("注意：当个传入的信息数量超出了所需的信息个数，是否为错误数据？序号：" + merthods[0]);
    }
    try
    {
      int n = 0;
      this.CustAcctId = overLong(merthods[(n++)], this.CustAcctIdlength);
      this.CustName = overLong(merthods[(n++)], this.CustNamelength);
      this.ThirdCustId = overLong(merthods[(n++)], this.ThirdCustIdlength);
      this.Status = Integer.parseInt(overLong(merthods[(n++)], this.Statuslength));
      this.Type = Integer.parseInt(overLong(merthods[(n++)], this.Typelength));
      this.IsTrueCont = Integer.parseInt(overLong(merthods[(n++)], this.IsTrueContlength));
      this.Balance = Double.valueOf(overLong(merthods[(n++)], this.Balancelength)).doubleValue();
      this.UsrBalance = Double.valueOf(overLong(merthods[(n++)], this.UsrBalancelength)).doubleValue();
      this.FrozenBalance = Double.valueOf(overLong(merthods[(n++)], this.FrozenBalancelength)).doubleValue();
      this.Interest = Double.valueOf(overLong(merthods[(n++)], this.Interestlength)).doubleValue();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw e;
    }
  }
  
  public String setBody()
    throws Exception
  {
    StringBuilder sb = new StringBuilder();
    sb.append(this.CustAcctId + this.separator);
    sb.append(this.CustName + this.separator);
    sb.append(this.ThirdCustId + this.separator);
    sb.append(this.Status + this.separator);
    sb.append(this.Type + this.separator);
    sb.append(this.IsTrueCont + this.separator);
    sb.append(this.Balance + this.separator);
    sb.append(this.UsrBalance + this.separator);
    sb.append(this.FrozenBalance + this.separator);
    return sb.toString();
  }
}
