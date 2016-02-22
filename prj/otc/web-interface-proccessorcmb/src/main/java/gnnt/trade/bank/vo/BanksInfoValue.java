package gnnt.trade.bank.vo;

import java.io.Serializable;

public class BanksInfoValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String nbkcode;
  public String sabkcode;
  public String bnkcity;
  public String nbkname;
  public String nbksname;
  public String nbkaddrss;
  public String cnttel;
  public String cntper;
  public String postcode;
  public String nbkstate;
  public String bkemail;
  public String content;
  
  public String toString()
  {
    String sep = "\n";
    StringBuilder sb = new StringBuilder();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("nbkcode:" + this.nbkcode + sep);
    sb.append("sabkcode:" + this.sabkcode + sep);
    sb.append("bnkcity:" + this.bnkcity + sep);
    sb.append("nbkname:" + this.nbkname + sep);
    sb.append("nbksname:" + this.nbksname + sep);
    sb.append("nbkaddrss:" + this.nbkaddrss + sep);
    sb.append("cnttel:" + this.cnttel + sep);
    sb.append("cntper:" + this.cntper + sep);
    sb.append("postcode:" + this.postcode + sep);
    sb.append("nbkstate:" + this.nbkstate + sep);
    sb.append("bkemail:" + this.bkemail + sep);
    sb.append("content:" + this.content + sep);
    sb.append(sep);
    return sb.toString();
  }
}
