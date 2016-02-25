package gnnt.trade.bank.vo.bankdz.sfz.sent.child;

import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import java.io.PrintStream;
import java.io.Serializable;

public class BatQsChild
  extends FileFathor
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected int preferencenum = 22;
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
  public double toDhykAmount;
  public double yeDhykAmount;
  public double toQianAmount;
  public double yeQianAmount;
  
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
    sb.append("当天总冻结金额FreezeAmount[" + fmtDouble(this.FreezeAmount) + "]" + str);
    sb.append("当天总解冻金额UnfreezeAmount[" + fmtDouble(this.UnfreezeAmount) + "]" + str);
    sb.append("当天成交的总货款(卖方)AddTranAmount[" + fmtDouble(this.AddTranAmount) + "]" + str);
    sb.append("当天成交的总货款(买方)CutTranAmount[" + fmtDouble(this.CutTranAmount) + "]" + str);
    sb.append("盈利总金额ProfitAmount[" + fmtDouble(this.ProfitAmount) + "]" + str);
    sb.append("亏损总金额LossAmount[" + fmtDouble(this.LossAmount) + "]" + str);
    sb.append("当天应该付给交易网的手续费总额TranHandFee[" + fmtDouble(this.TranHandFee) + "]" + str);
    sb.append("当天交易总比数TranCount[" + this.TranCount + "]" + str);
    sb.append("交易网端最新的交易商可用余额NewBalance[" + fmtDouble(this.NewBalance) + "]" + str);
    sb.append("交易网端最新的冻结资金NewFreezeAmount[" + fmtDouble(this.NewFreezeAmount) + "]" + str);
    sb.append("Note[" + this.Note + "]" + str);
    sb.append("Reserve[" + this.Reserve + "]" + str);
    sb.append("当日订货盈亏[" + this.toDhykAmount + "]" + str);
    sb.append("上日订货盈亏[" + this.toDhykAmount + "]" + str);
    sb.append("当日欠款[" + this.toQianAmount + "]" + str);
    sb.append("上日欠款[" + this.yeQianAmount + "]" + str);
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
      throw new Exception("传入的信息个数小于需要信息个数");
    }
    if (merthods.length > this.preferencenum + 1) {
      System.out.println("注意：当个传入的信息数量超出了所需的信息个数，是否为错误数据？序号：" + merthods[0]);
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
    sb.append(overLong(this.TranDateTime, this.TranDateTimelength) + this.separator);
    sb.append(overLong(this.CounterId, this.CounterIdlength) + this.separator);
    sb.append(overLong(this.SupAcctId, this.SupAcctIdlength) + this.separator);
    sb.append(overLong(this.FuncCode, this.FuncCodelength) + this.separator);
    sb.append(overLong(this.CustAcctId, this.CustAcctIdlength) + this.separator);
    sb.append(overLong(this.CustName, this.CustNamelength) + this.separator);
    sb.append(overLong(this.ThirdCustId, this.ThirdCustIdlength) + this.separator);
    sb.append(overLong(this.ThirdLogNo, this.ThirdLogNolength) + this.separator);
    sb.append(overLong(this.CcyCode, this.CcyCodelength) + this.separator);
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
    sb.append(overLong(this.Note, this.Notelength) + this.separator);
    sb.append(overLong(this.Reserve, this.Reservelength) + this.separator);
    return sb.toString();
  }
  
  public static void main(String[] args)
  {
    String str = "3&20100818000000&1&11000098301001&30&888800000012104&测试一&0002&2683&RMB&4331021.54&4331021.54&0.00&0.00&0.00&0.00&0.00&0&5521000.02&4331021.54&交易网清算信息&&";
    String str2 = "1&20100818000000&1&11000098301001&30&888800000012134&测试二&0004&2679&RMB&1401000.00&1401000.00&0.00&0.00&0.00&0.00&0.00&0&8224004.28&1401000.00&交易网清算信息&&";
    String str3 = "2&20100818000000&1&11000098301001&30&888800000012114&广州化工交易网&0003&2684&RMB&7061891.03&7061891.03&0.00&0.00&0.00&0.00&0.00&0&2785978.21&7061891.03&交易网清算信息&&";
    System.out.println("--------------------------------测试一--------------------------------");
    showMsg(5521000.0199999996D, 2792560.0D, str);
    System.out.println("--------------------------------测试二--------------------------------");
    showMsg(8224004.2800000003D, 1520658.1200000001D, str2);
    System.out.println("--------------------------------广州化工测试--------------------------------");
    showMsg(2785978.21D, 8827061.9700000007D, str3);
  }
  
  public static void showMsg(double bankky, double bankdj, String str)
  {
    BatQsChild bqc = new BatQsChild();
    try
    {
      bqc.getBody(str);
      










      System.out.println();
      System.out.println("银行记录上日银行可用[" + bankky + "]");
      System.out.println("银行记录上日银行冻结[" + bankdj + "]");
      System.out.println("当天冻结[" + bqc.FreezeAmount + "]-当天解冻[" + bqc.UnfreezeAmount + "]=[" + (bqc.FreezeAmount - bqc.UnfreezeAmount) + "]");
      System.out.println("当前冻结资金[" + bqc.NewFreezeAmount + "]");
      System.out.println("卖方货款[" + bqc.AddTranAmount + "]-买方货款[" + bqc.CutTranAmount + "]+盈利[" + bqc.ProfitAmount + "]-亏损[" + bqc.LossAmount + "]-手续费[" + bqc.TranHandFee + "]-冻结资金[" + bqc.FreezeAmount + "]+解冻资金[" + bqc.UnfreezeAmount + "]=[" + (bqc.AddTranAmount - bqc.CutTranAmount + bqc.ProfitAmount - bqc.LossAmount - bqc.TranHandFee - bqc.FreezeAmount + bqc.UnfreezeAmount) + "]");
      System.out.println("当前可用资金[" + bqc.NewBalance + "]");
      System.out.println("根据市场信息计算上日银行冻结[" + (bqc.NewFreezeAmount - (bqc.FreezeAmount - bqc.UnfreezeAmount)) + "]");
      System.out.println("根据市场信息计算上日银行可用[" + (bqc.NewBalance - (bqc.AddTranAmount - bqc.CutTranAmount + bqc.ProfitAmount - bqc.LossAmount - bqc.TranHandFee - bqc.FreezeAmount + bqc.UnfreezeAmount)) + "]");
      System.out.println("可用扎差[" + (bqc.NewBalance - (bqc.AddTranAmount - bqc.CutTranAmount + bqc.ProfitAmount - bqc.LossAmount - bqc.TranHandFee - bqc.FreezeAmount + bqc.UnfreezeAmount) - bankky) + "]");
      System.out.println("冻结扎差[" + (bqc.NewFreezeAmount - (bqc.FreezeAmount - bqc.UnfreezeAmount) - bankdj) + "]");
      System.out.println("银行计算市场可用：卖方货款[" + bqc.AddTranAmount + "]-买方货款[" + bqc.CutTranAmount + "]+盈利[" + bqc.ProfitAmount + "]-亏损[" + bqc.LossAmount + "]-手续费[" + bqc.TranHandFee + "]-冻结资金[" + bqc.FreezeAmount + "]+解冻资金[" + bqc.UnfreezeAmount + "]+上日可用资金[" + bankky + "]=[" + (bqc.AddTranAmount - bqc.CutTranAmount + bqc.ProfitAmount - bqc.LossAmount - bqc.TranHandFee - bqc.FreezeAmount + bqc.UnfreezeAmount + bankky) + "]");
      System.out.println("银行计算市场冻结：当天冻结[" + bqc.FreezeAmount + "]-当天解冻[" + bqc.UnfreezeAmount + "]+银行冻结[" + bankdj + "]=[" + (bqc.FreezeAmount - bqc.UnfreezeAmount + bankdj) + "]");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
