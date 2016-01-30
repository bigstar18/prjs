package gnnt.bank.adapter.bankBusiness.cpnt;

import gnnt.bank.adapter.bankBusiness.enumElmt.ReturnCode;
import javax.xml.bind.annotation.XmlElement;

public class ReturnResult
{

  @XmlElement
  public String Code = ReturnCode.CODE0000.getValue();

  @XmlElement
  public String Info = "";
}