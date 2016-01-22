package gnnt.bank.adapter.bankBusiness.info;

import gnnt.bank.adapter.bankBusiness.cpnt.Amount;
import gnnt.bank.adapter.bankBusiness.cpnt.BankAccount;
import gnnt.bank.adapter.bankBusiness.cpnt.FuturesAccount;
import gnnt.bank.adapter.bankBusiness.cpnt.GroupHeader;
import gnnt.bank.adapter.bankBusiness.cpnt.ReturnResult;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReversalResponse
{

  @XmlElement
  public GroupHeader GrpHdr = new GroupHeader();

  @XmlElement
  public ReturnResult Rst = new ReturnResult();

  @XmlElement
  public String BkSeq = "";

  @XmlElement
  public String FtSeq = "";

  @XmlElement
  public BankAccount BkAcct = new BankAccount();

  @XmlElement
  public FuturesAccount FtAcct = new FuturesAccount();

  @XmlElement
  public Amount TrfAmt = new Amount();

  @XmlElement
  public Amount CustCharge = new Amount();

  @XmlElement
  public Amount FutureCharge = new Amount();

  @XmlElement
  public String Dgst = "";

  @XmlElement
  public String Mac = "";
}