package gnnt.bank.adapter.bankBusiness.message;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RSP_BODY_F625
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @XmlElement
  public String busiNum;

  @XmlElement
  public String virAccType;

  @XmlElement
  public String memNum;

  @XmlElement
  public String balance;

  @XmlElement
  public String useableBalance;

  @XmlElement
  public String filed1;

  @XmlElement
  public String filed2;

  @XmlElement
  public String filed3;
}