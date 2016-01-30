package gnnt.bank.adapter.bankBusiness.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="in")
public class REQ_F626
{

  @XmlElement
  public HEAD head = new HEAD();

  @XmlElement
  public REQ_BODY_F626 body = new REQ_BODY_F626();
}