package gnnt.bank.adapter.bankBusiness.cpnt;

import gnnt.bank.adapter.bankBusiness.enumElmt.BusinessCode;
import javax.xml.bind.annotation.XmlElement;

public class Detail
{

  @XmlElement
  public String OldDate = "";

  @XmlElement
  public String OldTime = "";

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
  public String HdlFlag = "";

  @XmlElement
  public int TrsType = BusinessCode.CODE22001.getValue();
}