package gnnt.bank.adapter.bankBusiness.info;

import gnnt.bank.adapter.bankBusiness.cpnt.AccoutBalance;
import gnnt.bank.adapter.bankBusiness.cpnt.BankAccount;
import gnnt.bank.adapter.bankBusiness.cpnt.FuturesAccount;
import gnnt.bank.adapter.bankBusiness.cpnt.GroupHeader;
import gnnt.bank.adapter.bankBusiness.cpnt.ReturnResult;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QryAccountResponse
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
  public AccoutBalance BkBal = new AccoutBalance();

  @XmlElement
  public FuturesAccount FtAcct = new FuturesAccount();

  @XmlElement
  public AccoutBalance FtBal = new AccoutBalance();

  @XmlElement
  public AccoutBalance FtBal1 = new AccoutBalance();

  @XmlElement
  public String Dgst = "";

  @XmlElement
  public String Mac = "";
}