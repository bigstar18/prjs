package gnnt.bank.adapter.bankBusiness.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="in")
public class RSP_F620
{
  @XmlElement
  public HEAD head = new HEAD();
  @XmlElement
  public RSP_BODY_F620 body = new RSP_BODY_F620();
}
