package gnnt.bank.adapter.bankBusiness.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RSP_BODY_F603
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @XmlElement
  public String handleNum;

  @XmlElement
  public List<RSP_BODY_F603_FRAME> frame = new ArrayList();

  public void add(RSP_BODY_F603_FRAME frame)
  {
    this.frame.add(frame);
  }
}