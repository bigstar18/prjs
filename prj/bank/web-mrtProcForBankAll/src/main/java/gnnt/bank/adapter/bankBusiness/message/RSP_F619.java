package gnnt.bank.adapter.bankBusiness.message;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="in")
public class RSP_F619
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @XmlElement
  public HEAD head = new HEAD();
  @XmlElement
  public RSP_BODY_F619 body = new RSP_BODY_F619();
}
