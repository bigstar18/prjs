package gnnt.trade.bank.vo.bankdz.sfz.resave.child;

import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import java.io.PrintStream;
import java.io.Serializable;

public class BatFailResultChild
  extends FileFathor
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected int preferencenum = 24;
  public long QueryId;
  private int QueryIdlength = 10;
  public String TranDateTime;
  private int TranDateTimelength = 14;
  public String CounterId;
  private int CounterIdlength = 12;
  public String SupAcctId;
  private int SupAcctIdlength = 32;
  public String FuncCode;
  private int FuncCodelength = 4;
  public String CustAcctId;
  private int CustAcctIdlength = 32;
  public String CustName;
  private int CustNamelength = 120;
  public String ThirdCustId;
  private int ThirdCustIdlength = 32;
  public String ThirdLogNo;
  private int ThirdLogNolength = 20;
  public String CcyCode = "RMB";
  private int CcyCodelength = 3;
  public double FreezeAmount;
  private int FreezeAmountlength = 18;
  public double UnfreezeAmount;
  private int UnfreezeAmountlength = 18;
  public double AddTranAmount;
  private int AddTranAmountlength = 18;
  public double CutTranAmount;
  private int CutTranAmountlength = 18;
  public double ProfitAmount;
  private int ProfitAmountlength = 18;
  public double LossAmount;
  private int LossAmountlength = 18;
  public double TranHandFee;
  private int TranHandFeelength = 18;
  public int TranCount;
  private int TranCountlength = 30;
  public double NewBalance;
  private int NewBalancelength = 18;
  public double NewFreezeAmount;
  private int NewFreezeAmountlength = 18;
  public String Note;
  private int Notelength = 120;
  public String Reserve;
  private int Reservelength = 120;
  public String RspCode;
  private final int RspCodelength = 6;
  public String RspMsg;
  private final int RspMsglength = 120;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("QueryId[" + this.QueryId + "]" + str);
    sb.append("TranDateTime[" + this.TranDateTime + "]" + str);
    sb.append("CounterId[" + this.CounterId + "]" + str);
    sb.append("SupAcctId[" + this.SupAcctId + "]" + str);
    sb.append("FuncCode[" + this.FuncCode + "]" + str);
    sb.append("CustAcctId[" + this.CustAcctId + "]" + str);
    sb.append("CustName[" + this.CustName + "]" + str);
    sb.append("ThirdCustId[" + this.ThirdCustId + "]" + str);
    sb.append("ThirdLogNo[" + this.ThirdLogNo + "]" + str);
    sb.append("FreezeAmount[" + this.FreezeAmount + "]" + str);
    sb.append("UnfreezeAmount[" + this.UnfreezeAmount + "]" + str);
    sb.append("AddTranAmount[" + this.AddTranAmount + "]" + str);
    sb.append("CutTranAmount[" + this.CutTranAmount + "]" + str);
    sb.append("ProfitAmount[" + this.ProfitAmount + "]" + str);
    sb.append("LossAmount[" + this.LossAmount + "]" + str);
    sb.append("TranHandFee[" + this.TranHandFee + "]" + str);
    sb.append("TranCount[" + this.TranCount + "]" + str);
    sb.append("NewBalance[" + this.NewBalance + "]" + str);
    sb.append("NewFreezeAmount[" + this.NewFreezeAmount + "]" + str);
    sb.append("Note[" + this.Note + "]" + str);
    sb.append("Reserve[" + this.Reserve + "]" + str);
    sb.append("RspCode[" + this.RspCode + "]" + str);
    sb.append("RspMsg[" + this.RspMsg + "]" + str);
    sb.append(str);
    return sb.toString();
  }
  
  public void getBody(String str)
    throws Exception
  {
    if ((str == null) || (str.trim().length() <= 0)) {
      throw new Exception("传入的信息为空");
    }
    String[] merthods = str.split("&", -1);
    if (merthods.length < this.preferencenum) {
      throw new Exception("传入的信息个数[" + merthods.length + "]小于需要信息个数[" + this.preferencenum + "]。数据信息[" + str + "]");
    }
    if (merthods.length > this.preferencenum + 1) {
      System.out.println("注意：当个传入的信息数量[" + merthods.length + "]超出了所需的信息个数[" + (this.preferencenum + 1) + "]，是否为错误数据。数据信息[" + str + "]");
    }
    try
    {
      int n = 0;
      this.QueryId = Long.parseLong(overLong(merthods[(n++)], this.QueryIdlength));
      this.TranDateTime = overLong(merthods[(n++)], this.TranDateTimelength);
      this.CounterId = overLong(merthods[(n++)], this.CounterIdlength);
      this.SupAcctId = overLong(merthods[(n++)], this.SupAcctIdlength);
      this.FuncCode = overLong(merthods[(n++)], this.FuncCodelength);
      this.CustAcctId = overLong(merthods[(n++)], this.CustAcctIdlength);
      this.CustName = overLong(merthods[(n++)], this.CustNamelength);
      this.ThirdCustId = overLong(merthods[(n++)], this.ThirdCustIdlength);
      this.ThirdLogNo = overLong(merthods[(n++)], this.ThirdLogNolength);
      this.CcyCode = overLong(merthods[(n++)], this.CcyCodelength);
      this.FreezeAmount = Double.valueOf(overLong(merthods[(n++)], this.FreezeAmountlength)).doubleValue();
      this.UnfreezeAmount = Double.valueOf(overLong(merthods[(n++)], this.UnfreezeAmountlength)).doubleValue();
      this.AddTranAmount = Double.valueOf(overLong(merthods[(n++)], this.AddTranAmountlength)).doubleValue();
      this.CutTranAmount = Double.valueOf(overLong(merthods[(n++)], this.CutTranAmountlength)).doubleValue();
      this.ProfitAmount = Double.valueOf(overLong(merthods[(n++)], this.ProfitAmountlength)).doubleValue();
      this.LossAmount = Double.valueOf(overLong(merthods[(n++)], this.LossAmountlength)).doubleValue();
      this.TranHandFee = Double.valueOf(overLong(merthods[(n++)], this.TranHandFeelength)).doubleValue();
      this.TranCount = Integer.parseInt(overLong(merthods[(n++)], this.TranCountlength));
      this.NewBalance = Double.valueOf(overLong(merthods[(n++)], this.NewBalancelength)).doubleValue();
      this.NewFreezeAmount = Double.valueOf(overLong(merthods[(n++)], this.NewFreezeAmountlength)).doubleValue();
      this.Note = overLong(merthods[(n++)], this.Notelength);
      this.Reserve = overLong(merthods[(n++)], this.Reservelength);
      this.RspCode = overLong(merthods[(n++)], 6);
      this.RspMsg = overLong(merthods[(n++)], 120);
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
    sb.append(this.QueryId + this.separator);
    sb.append(this.TranDateTime + this.separator);
    sb.append(this.CounterId + this.separator);
    sb.append(this.SupAcctId + this.separator);
    sb.append(this.FuncCode + this.separator);
    sb.append(this.CustAcctId + this.separator);
    sb.append(this.CustName + this.separator);
    sb.append(this.ThirdCustId + this.separator);
    sb.append(this.ThirdLogNo + this.separator);
    sb.append(this.CcyCode + this.separator);
    sb.append(fmtDouble(this.FreezeAmount) + this.separator);
    sb.append(fmtDouble(this.UnfreezeAmount) + this.separator);
    sb.append(fmtDouble(this.AddTranAmount) + this.separator);
    sb.append(fmtDouble(this.CutTranAmount) + this.separator);
    sb.append(fmtDouble(this.ProfitAmount) + this.separator);
    sb.append(fmtDouble(this.LossAmount) + this.separator);
    sb.append(fmtDouble(this.TranHandFee) + this.separator);
    sb.append(this.TranCount + this.separator);
    sb.append(fmtDouble(this.NewBalance) + this.separator);
    sb.append(fmtDouble(this.NewFreezeAmount) + this.separator);
    sb.append(this.Note + this.separator);
    sb.append(this.Reserve + this.separator);
    sb.append(this.RspCode + this.separator);
    sb.append(this.RspMsg + this.separator);
    return sb.toString();
  }
  
  public static void main(String[] args)
  {
    String str = "3&20100810000000&&11000098301001&30&888800000012104&测试一&0002&2548&RMB&406560.00&0.00&0.00&0.00&9522368.86&0.00&0.00&0&9522368.86&406560.00&交易网清算信息&&ERR044&该会员清算数据有误,清算后会导致银行端余额与交易网的不等&";
    BatFailResultChild bqc = new BatFailResultChild();
    try
    {
      bqc.getBody(str);
      System.out.println();
      System.out.println(bqc.toString());
      System.out.println();
      String str2 = bqc.setBody();
      System.out.println(str);
      System.out.println(str2);
      System.out.println();
      System.out.println(str.equals(str2));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
