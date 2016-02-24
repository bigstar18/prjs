package gnnt.bank.adapter.bankBusiness.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="in")
public class REQ_F625
{

  @XmlElement
  public HEAD head = new HEAD();

  @XmlElement
  public REQ_BODY_F625 body = new REQ_BODY_F625();
}