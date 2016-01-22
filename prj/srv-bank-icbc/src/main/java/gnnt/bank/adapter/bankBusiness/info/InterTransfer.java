package gnnt.bank.adapter.bankBusiness.info;

import gnnt.bank.adapter.bankBusiness.cpnt.Amount;
import gnnt.bank.adapter.bankBusiness.cpnt.BankAccount;
import gnnt.bank.adapter.bankBusiness.cpnt.GroupHeader;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InterTransfer
{

  @XmlElement
  public GroupHeader GrpHdr = new GroupHeader();

  @XmlElement
  public String FtSeq = "";

  @XmlElement
  public BankAccount RcvAcct = new BankAccount();

  @XmlElement
  public Amount TrfAmt = new Amount();

  @XmlElement
  public String Dgst = "";

  @XmlElement
  public String Mac = "";
}