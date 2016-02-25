package gnnt.trade.bank.vo.bankdz.sfz.resave.child;

import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;

public class KXHfailChild
  extends FileFathor
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected int preferencenum = 9;
  public int number;
  private int numberlength = 5;
  public String funID;
  private int funIDlength = 20;
  public int status;
  private int statuslength = 1;
  public String account1;
  private int account1length = 32;
  public int type;
  private int typelength = 1;
  public String account1Name;
  private int account1Namelength = 120;
  public String firmID;
  private int firmIDlength = 32;
  public Date tradeDate;
  private int tradeDatelength = 8;
  public String counterId;
  private int counterIdlength = 12;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("number[" + this.number + "]" + str);
    sb.append("funID[" + this.funID + "]" + str);
    sb.append("status[" + this.status + "]" + str);
    sb.append("account1[" + this.account1 + "]" + str);
    sb.append("type[" + this.type + "]" + str);
    sb.append("account1Name[" + this.account1Name + "]" + str);
    sb.append("firmID[" + this.firmID + "]" + str);
    sb.append("tradeDate[" + this.tradeDate + "]" + str);
    sb.append("counterId[" + this.counterId + "]" + str);
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
      this.number = Integer.parseInt(overLong(merthods[(n++)], this.numberlength));
      this.funID = overLong(merthods[(n++)], this.funIDlength);
      this.status = Integer.parseInt(overLong(merthods[(n++)], this.statuslength));
      this.account1 = overLong(merthods[(n++)], this.account1length);
      this.type = Integer.parseInt(overLong(merthods[(n++)], this.typelength));
      this.account1Name = overLong(merthods[(n++)], this.account1Namelength);
      this.firmID = overLong(merthods[(n++)], this.firmIDlength);
      this.tradeDate = strToDate1(overLong(merthods[(n++)], this.tradeDatelength));
      this.counterId = overLong(merthods[(n++)], this.counterIdlength);
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
    sb.append(this.number + this.separator);
    sb.append(this.funID + this.separator);
    sb.append(this.status + this.separator);
    sb.append(this.account1 + this.separator);
    sb.append(this.type + this.separator);
    sb.append(this.account1Name + this.separator);
    sb.append(this.firmID + this.separator);
    sb.append(dateToStr1(this.tradeDate) + this.separator);
    sb.append(this.counterId + this.separator);
    return sb.toString();
  }
  
  public static void main(String[] args)
  {
    KXHfailChild kc = new KXHfailChild();
    kc.number = 2;
    kc.funID = "funID";
    kc.status = 1;
    kc.account1 = "account1";
    kc.type = 1;
    kc.account1Name = "account1Name";
    kc.firmID = "firmID";
    kc.tradeDate = kc.strToDate1("20100202");
    kc.counterId = "counterId";
    try
    {
      System.out.println(kc.toString());
      String str = kc.setBody();
      System.out.println(str);
      kc.getBody(str);
      System.out.println(kc.toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
