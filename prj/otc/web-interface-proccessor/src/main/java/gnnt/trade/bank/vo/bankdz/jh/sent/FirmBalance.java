package gnnt.trade.bank.vo.bankdz.jh.sent;

import gnnt.trade.bank.data.ccb.vo.CCBConstant;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class FirmBalance
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String bankID;
  public String account;
  public String accountName;
  public String cardType;
  public String card;
  public double FeeMoney;
  public double QYChangeMoney;
  public double MQYChangeMoney;
  public double QYMoney;
  public double lastQYMoney;
  public double fundio;
  public Date date;
  public String futuresId;
  public String firmtype;
  private final int[] fieldlen_ZYQQS = { 8, 15, 17, 17, 40 };
  
  public FirmBalance()
  {
    this.bankID = CCBConstant.bankID;
    this.futuresId = CCBConstant.marketID;
  }
  
  public String toString(String id)
  {
    StringBuilder sb = new StringBuilder();
    


    this.FeeMoney = (this.firmtype.equals("C") ? this.FeeMoney : this.FeeMoney < 0.0D ? this.FeeMoney * -1.0D : 0.0D);
    this.QYChangeMoney = (this.firmtype.equals("C") ? this.QYChangeMoney : this.MQYChangeMoney);
    
    sb.append(fmtStrField(id, this.fieldlen_ZYQQS[0], "0", 0));
    sb.append(fmtStrField(this.firmID, this.fieldlen_ZYQQS[1], " ", 1));
    sb.append(fmtStrField(this.FeeMoney, this.fieldlen_ZYQQS[2], "0", 0));
    sb.append(fmtStrField(this.QYChangeMoney, this.fieldlen_ZYQQS[3], "0", 0));
    sb.append(fmtStrField("", this.fieldlen_ZYQQS[4], " ", 0));
    sb.append("\n");
    return sb.toString();
  }
  
  public static String fmtStrField(String str, int len, String c, int side)
  {
    String _str = str;
    while (_str.getBytes().length < len) {
      if (side == 0) {
        _str = c + _str;
      } else {
        _str = _str + c;
      }
    }
    return _str;
  }
  
  public static String fmtStrField(int str, int len, String c, int side)
  {
    String s = null;
    if (str < 0) {
      s = "-" + fmtStrField(String.valueOf(Math.abs(str)), len - 1, c, side);
    } else {
      s = fmtStrField(String.valueOf(Math.abs(str)), len, c, side);
    }
    return s;
  }
  
  public static String fmtStrField(long str, int len, String c, int side)
  {
    String s = null;
    if (str < 0L) {
      s = "-" + fmtStrField(String.valueOf(Math.abs(str)), len - 1, c, side);
    } else {
      s = fmtStrField(String.valueOf(Math.abs(str)), len, c, side);
    }
    return s;
  }
  
  public static String fmtStrField(float str, int len, String c, int side)
  {
    String s = null;
    if (str < 0.0F) {
      s = "-" + fmtStrField(fmtDouble2(Math.abs(str)), len - 1, c, side);
    } else {
      s = fmtStrField(fmtDouble2(Math.abs(str)), len, c, side);
    }
    return s;
  }
  
  public static String fmtStrField(double str, int len, String c, int side)
  {
    String s = null;
    if (str < 0.0D) {
      s = "-" + fmtStrField(fmtDouble2(Math.abs(str)), len - 1, c, side);
    } else {
      s = fmtStrField(fmtDouble2(Math.abs(str)), len, c, side);
    }
    return s;
  }
  
  public static String fmtDouble2(double num)
  {
    String result = "0.00";
    try
    {
      DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
      nf.applyPattern("###0.00");
      result = nf.format(num);
    }
    catch (Exception localException) {}
    return result;
  }
}
