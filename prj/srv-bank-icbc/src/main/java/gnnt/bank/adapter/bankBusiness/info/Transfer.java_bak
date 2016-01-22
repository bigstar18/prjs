package gnnt.bank.adapter.bankBusiness.info;

import gnnt.bank.adapter.bankBusiness.cpnt.Amount;
import gnnt.bank.adapter.bankBusiness.cpnt.BankAccount;
import gnnt.bank.adapter.bankBusiness.cpnt.Customer;
import gnnt.bank.adapter.bankBusiness.cpnt.FuturesAccount;
import gnnt.bank.adapter.bankBusiness.cpnt.GroupHeader;
import gnnt.bank.adapter.bankBusiness.enumElmt.YesNoIndicator;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transfer
{

  @XmlElement
  public GroupHeader GrpHdr = new GroupHeader();

  @XmlElement
  public String BkSeq = "";

  @XmlElement
  public String FtSeq = "";

  @XmlElement
  public YesNoIndicator Resend = YesNoIndicator.N;

  @XmlElement
  public Customer Cust = new Customer();

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
  public int AsynFlag = 0;

  @XmlElement
  public YesNoIndicator AsynStat = YesNoIndicator.N;

  @XmlElement
  public String AsynSeq = "";

  @XmlElement
  public String Dgst1 = "";

  @XmlElement
  public String Mac = "";
}