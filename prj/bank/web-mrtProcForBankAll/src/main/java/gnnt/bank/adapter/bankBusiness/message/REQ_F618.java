package gnnt.bank.adapter.bankBusiness.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="in")
public class REQ_F618
{
  @XmlElement
  public HEAD head = new HEAD();
  @XmlElement
  public REQ_BODY_F618 body = new REQ_BODY_F618();
}
