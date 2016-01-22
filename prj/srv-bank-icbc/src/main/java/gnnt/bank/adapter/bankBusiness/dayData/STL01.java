package gnnt.bank.adapter.bankBusiness.dayData;

public class STL01
{
  public String bankId;
  public String futuresId;
  public String date;
  public String ftSeq;
  public String bkAcct;
  public String ftAcct;
  public String custName;
  public int busType;
  public String trfFlag;
  public String ccy;
  public int cashExCd;
  public long trfAmt;
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
    sb.append("bkAcct:" + this.bkAcct + sep);
    sb.append("ftAcct:" + this.ftAcct + sep);
    sb.append("custName:" + this.custName + sep);
    sb.append("busType:" + this.busType + sep);
    sb.append("trfFlag:" + this.trfFlag + sep);
    sb.append("ccy:" + this.ccy + sep);
    sb.append("cashExCd:" + this.cashExCd + sep);
    sb.append("trfAmt:" + this.trfAmt + sep);
    sb.append("info1:" + this.info1 + sep);
    sb.append("info2:" + this.info2 + sep);
    sb.append("info3:" + this.info3 + sep);
    return sb.toString();
  }
}