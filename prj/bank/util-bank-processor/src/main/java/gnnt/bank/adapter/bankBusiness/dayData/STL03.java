package gnnt.bank.adapter.bankBusiness.dayData;

public class STL03
{
  public String bankId;
  public String futuresId;
  public String date;
  public String ftSeq;
  public String ftAcct;
  public String custName;
  public String ccy;
  public int cashExCd;
  public String busType;
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
    sb.append("ftSeq:" + this.ftSeq + sep);
    sb.append("ftAcct:" + this.ftAcct + sep);
    sb.append("custName:" + this.custName + sep);
    sb.append("ccy:" + this.ccy + sep);
    sb.append("cashExCd:" + this.cashExCd + sep);
    sb.append("busType:" + this.busType + sep);
    sb.append("info1:" + this.info1 + sep);
    sb.append("info2:" + this.info2 + sep);
    sb.append("info3:" + this.info3 + sep);
    return sb.toString();
  }
}
