package gnnt.bank.adapter.bankBusiness.dayData;

public class DTL04
{
  public String bankId;
  public String futuresId;
  public String date;
  public String ccy;
  public String cashExCd;
  public long bkManageSumBlance;
  public long bkSumBlanceLocal;
  public long bkSumBlanceOther;

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
    sb.append("bkManageSumBlance:" + this.bkManageSumBlance + sep);
    sb.append("bkSumBlanceLocal:" + this.bkSumBlanceLocal + sep);
    sb.append("bkSumBlanceOther:" + this.bkSumBlanceOther + sep);
    return sb.toString();
  }
}