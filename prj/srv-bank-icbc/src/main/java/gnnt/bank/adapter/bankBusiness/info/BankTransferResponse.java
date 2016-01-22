package gnnt.bank.adapter.bankBusiness.info;

import gnnt.bank.adapter.bankBusiness.cpnt.Amount;
import gnnt.bank.adapter.bankBusiness.cpnt.GroupHeader;
import gnnt.bank.adapter.bankBusiness.cpnt.ReturnResult;
import gnnt.bank.adapter.util.MAC;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BankTransferResponse
{

  @XmlElement
  public GroupHeader GrpHdr;

  @XmlElement
  public ReturnResult Rst;

  @XmlElement
  public String BkSeq;

  @XmlElement
  public String FtSeq;

  @XmlElement
  public Amount FutureCharge;

  @XmlElement
  public String Digest;

  @XmlElement
  public MAC mac;
}