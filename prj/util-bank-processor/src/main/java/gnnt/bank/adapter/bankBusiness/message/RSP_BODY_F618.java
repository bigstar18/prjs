package gnnt.bank.adapter.bankBusiness.message;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RSP_BODY_F618
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @XmlElement
  public String busiNum;

  @XmlElement
  public String busiName;

  @XmlElement
  public String busiDate;

  @XmlElement
  public String status;

  @XmlElement
  public String beginDatetime;

  @XmlElement
  public String endDatetime;

  @XmlElement
  public String filed1;

  @XmlElement
  public String filed2;

  @XmlElement
  public String filed3;
}