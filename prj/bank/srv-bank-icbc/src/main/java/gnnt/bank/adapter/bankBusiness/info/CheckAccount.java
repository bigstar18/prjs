package gnnt.bank.adapter.bankBusiness.info;

import gnnt.bank.adapter.bankBusiness.cpnt.BankAccount;
import gnnt.bank.adapter.bankBusiness.cpnt.Customer;
import gnnt.bank.adapter.bankBusiness.cpnt.GroupHeader;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CheckAccount
{

  @XmlElement
  public GroupHeader GrpHdr = new GroupHeader();

  @XmlElement
  public String FtSeq = "";

  @XmlElement
  public Customer Cust = new Customer();

  @XmlElement
  public BankAccount BkAcct = new BankAccount();

  @XmlElement
  public String Dgst = "";

  @XmlElement
  public String Mac = "";
}