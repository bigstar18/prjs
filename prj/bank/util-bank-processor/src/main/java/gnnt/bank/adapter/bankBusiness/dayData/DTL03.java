package gnnt.bank.adapter.bankBusiness.dayData;

public class DTL03
{
  public String bankId;
  public String futuresId;
  public String date;
  public String ccy;
  public String cashExCd;
  public String ftAcct;
  public String custName;
  public int reason;
  public long BkBal;
  public long FtBal;
  public String info1;
  public String info2;
  public String info3;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("bankId:" + this.bankId + sep);
    sb.append("futuresId:" + this.futuresId + sep);
    sb.append("date:" + this.date + sep);
    sb.append("ccy:" + this.ccy + sep);
    sb.append("cashExCd:" + this.cashExCd + sep);
    sb.append("ftAcct:" + this.ftAcct + sep);
    sb.append("custName:" + this.custName + sep);
    sb.append("reason:" + this.reason + sep);
    sb.append("BkBal:" + this.BkBal + sep);
    sb.append("FtBal:" + this.FtBal + sep);
    sb.append("info1:" + this.info1 + sep);
    sb.append("info2:" + this.info2 + sep);
    sb.append("info3:" + this.info3 + sep);
    return sb.toString();
  }
}
