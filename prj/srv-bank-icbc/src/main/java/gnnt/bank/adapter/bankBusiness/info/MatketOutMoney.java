package gnnt.bank.adapter.bankBusiness.info;

import gnnt.bank.adapter.bankBusiness.cpnt.Amount;
import gnnt.bank.adapter.bankBusiness.cpnt.GroupHeader;
import gnnt.bank.adapter.bankBusiness.enumElmt.TradeType;
import gnnt.bank.adapter.bankBusiness.enumElmt.YesNoIndicator;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MatketOutMoney
{

  @XmlElement
  public GroupHeader GrpHdr = new GroupHeader();

  @XmlElement
  public String FtSeq;

  @XmlElement
  public YesNoIndicator Resend = YesNoIndicator.N;

  @XmlElement
  public String TransType = TradeType.DEBTOUT.getValue();

  @XmlElement
  public Amount TrfAmt = new Amount();

  @XmlElement
  public String Dgst;

  @XmlElement
  public String Mac = "";
}