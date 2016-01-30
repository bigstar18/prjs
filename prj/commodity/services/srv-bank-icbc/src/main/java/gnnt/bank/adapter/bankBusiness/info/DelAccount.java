package gnnt.bank.adapter.bankBusiness.info;

import gnnt.bank.adapter.bankBusiness.cpnt.BankAccount;
import gnnt.bank.adapter.bankBusiness.cpnt.Customer;
import gnnt.bank.adapter.bankBusiness.cpnt.FuturesAccount;
import gnnt.bank.adapter.bankBusiness.cpnt.GroupHeader;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DelAccount
{

  @XmlElement
  public GroupHeader GrpHdr = new GroupHeader();

  @XmlElement
  public String BkSeq = "";

  @XmlElement
  public Customer Cust = new Customer();

  @XmlElement
  public BankAccount BkAcct = new BankAccount();

  @XmlElement
  public FuturesAccount FtAcct = new FuturesAccount();

  @XmlElement
  public String Dgst = "";

  @XmlElement
  public String Mac = "";
}