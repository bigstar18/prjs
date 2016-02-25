package gnnt.trade.bank.data.boc.vo;

public class DTL02
{
  public String bankId;
  public String futuresId;
  public String sumDate;
  public String sumTime;
  public String ccy;
  public String cashExCd;
  public long trfAmt;
  public String trfFlag;
  public long trfFee1;
  public long trfFee2;
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("bankId:" + this.bankId + sep);
    sb.append("futuresId:" + this.futuresId + sep);
    sb.append("sumDate:" + this.sumDate + sep);
    sb.append("sumTime:" + this.sumTime + sep);
    sb.append("ccy:" + this.ccy + sep);
    sb.append("cashExCd:" + this.cashExCd + sep);
    sb.append("trfAmt:" + this.trfAmt + sep);
    sb.append("trfFlag:" + this.trfFlag + sep);
    sb.append("trfFee1:" + this.trfFee1 + sep);
    sb.append("trfFee2:" + this.trfFee2 + sep);
    return sb.toString();
  }
}
