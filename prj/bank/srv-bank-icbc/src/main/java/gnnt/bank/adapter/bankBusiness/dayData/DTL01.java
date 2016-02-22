package gnnt.bank.adapter.bankBusiness.dayData;

public class DTL01
{
  public String bankId;
  public String futuresId;
  public String transferDate;
  public String transfeTime;
  public String bkSeq;
  public String bankTransferDate;
  public String ftSeq;
  public String bkAcct;
  public String ftAcct;
  public String custName;
  public String tradSrc;
  public String busCd;
  public String ccy;
  public String cashExCd;
  public long trfAmt;
  public long trfFee1;
  public long trfFee2;

  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("bankId:" + this.bankId + sep);
    sb.append("futuresId:" + this.futuresId + sep);
    sb.append("transferDate:" + this.transferDate + sep);
    sb.append("transfeTime:" + this.transfeTime + sep);
    sb.append("bkSeq:" + this.bkSeq + sep);
    sb.append("bankTransferDate:" + this.bankTransferDate + sep);
    sb.append("ftSeq:" + this.ftSeq + sep);
    sb.append("bkAcct:" + this.bkAcct + sep);
    sb.append("ftAcct:" + this.ftAcct + sep);
    sb.append("custName:" + this.custName + sep);
    sb.append("tradSrc:" + this.tradSrc + sep);
    sb.append("busCd:" + this.busCd + sep);
    sb.append("ccy:" + this.ccy + sep);
    sb.append("cashExCd:" + this.cashExCd + sep);
    sb.append("trfAmt:" + this.trfAmt + sep);
    sb.append("trfFee1:" + this.trfFee1 + sep);
    sb.append("trfFee2:" + this.trfFee2 + sep);
    return sb.toString();
  }
}