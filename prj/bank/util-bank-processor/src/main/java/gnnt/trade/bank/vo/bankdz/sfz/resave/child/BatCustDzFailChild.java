package gnnt.trade.bank.vo.bankdz.sfz.resave.child;

import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;

public class BatCustDzFailChild extends FileFathor
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
  public double BankBalance;
  private int BankBalancelength = 18;
  public double BankFrozen;
  private int BankFrozenlength = 18;
  public double MaketBalance;
  private int MaketBalancelength = 18;
  public double MaketFrozen;
  private int MaketFrozenlength = 18;
  public double BalanceError;
  private int BalanceErrorlength = 18;
  public double FrozenError;
  private int FrozenErrorlength = 18;
  public String Note;
  private int Notelength = 120;
  public Date tradeDate;

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("CustAcctId[" + this.CustAcctId + "]" + str);
    sb.append("CustName[" + this.CustName + "]" + str);
    sb.append("ThirdCustId[" + this.ThirdCustId + "]" + str);
    sb.append("BankBalance[" + this.BankBalance + "]" + str);
    sb.append("BankFrozen[" + this.BankFrozen + "]" + str);
    sb.append("MaketBalance[" + this.MaketBalance + "]" + str);
    sb.append("MaketFrozen[" + this.MaketFrozen + "]" + str);
    sb.append("BalanceError[" + this.BalanceError + "]" + str);
    sb.append("FrozenError[" + this.FrozenError + "]" + str);
    sb.append("Note[" + this.Note + "]" + str);
    if (this.tradeDate == null)
      sb.append("tradeDate is Null" + str);
    else {
      sb.append("tradeDate[" + dateToStr1(this.tradeDate) + "]" + str);
    }
    sb.append(str);
    return sb.toString();
  }

  public void getBody(String str) throws Exception {
    if ((str == null) || (str.trim().length() <= 0)) {
      throw new Exception("传入的信息为空");
    }
    String[] merthods = str.split("&", -1);
    if (merthods.length < this.preferencenum)
      throw new Exception("传入的信息个数小于需要信息个数");
    if (merthods.length > this.preferencenum + 1)
      System.out.println("注意：当个传入的信息数量[" + (this.preferencenum + 1) + "]超出了所需的信息个数[" + merthods.length + "]，是否为错误数据？首信息：" + merthods[0]);
    try
    {
      int n = 0;
      this.CustAcctId = overLong(merthods[(n++)], this.CustAcctIdlength);
      this.CustName = overLong(merthods[(n++)], this.CustNamelength);
      this.ThirdCustId = overLong(merthods[(n++)], this.ThirdCustIdlength);
      this.BankBalance = Double.valueOf(overLong(merthods[(n++)], this.BankBalancelength)).doubleValue();
      this.BankFrozen = Double.valueOf(overLong(merthods[(n++)], this.BankFrozenlength)).doubleValue();
      this.MaketBalance = Double.valueOf(overLong(merthods[(n++)], this.MaketBalancelength)).doubleValue();
      this.MaketFrozen = Double.valueOf(overLong(merthods[(n++)], this.MaketFrozenlength)).doubleValue();
      this.BalanceError = Double.valueOf(overLong(merthods[(n++)], this.BalanceErrorlength)).doubleValue();
      this.FrozenError = Double.valueOf(overLong(merthods[(n++)], this.FrozenErrorlength)).doubleValue();
      this.Note = overLong(merthods[(n++)], this.Notelength);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  public String setBody() throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append(this.CustAcctId + this.separator);
    sb.append(this.CustName + this.separator);
    sb.append(this.ThirdCustId + this.separator);
    sb.append(fmtDouble(this.BankBalance) + this.separator);
    sb.append(fmtDouble(this.BankFrozen) + this.separator);
    sb.append(fmtDouble(this.MaketBalance) + this.separator);
    sb.append(fmtDouble(this.MaketFrozen) + this.separator);
    sb.append(fmtDouble(this.BalanceError) + this.separator);
    sb.append(fmtDouble(this.FrozenError) + this.separator);
    sb.append(this.Note + this.separator);
    return sb.toString();
  }
  public static void main(String[] args) {
    String str = "888800000012114&广州化工交易网&0003&636.00&0.00&9563039.00&290400.00&-9562403.00&-290400.00&已对账且账不平,银行可用余额小于交易网端,银行冻结余额小于交易网端&";

    BatCustDzFailChild child = new BatCustDzFailChild();
    try {
      child.getBody(str);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(child.toString());
  }
}