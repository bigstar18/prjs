package gnnt.bank.adapter.bankBusiness.info;

import gnnt.bank.adapter.bankBusiness.cpnt.GroupHeader;
import gnnt.bank.adapter.bankBusiness.cpnt.ReturnResult;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CheckAccountResponse
{

  @XmlElement
  public GroupHeader GrpHdr = new GroupHeader();

  @XmlElement
  public ReturnResult Rst = new ReturnResult();

  @XmlElement
  public String BkSeq = "";

  @XmlElement
  public String Dgst = "";

  @XmlElement
  public String Mac = "";

  @XmlElement
  public String FtSeq = "";
}