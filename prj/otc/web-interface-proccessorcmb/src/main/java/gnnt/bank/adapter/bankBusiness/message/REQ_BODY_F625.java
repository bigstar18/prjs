package gnnt.bank.adapter.bankBusiness.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class REQ_BODY_F625
{
  @XmlElement
  public String busiNum;
  @XmlElement
  public String virAccType;
  @XmlElement
  public String memNum;
  @XmlElement
  public String queryDate;
  @XmlElement
  public String filed1;
  @XmlElement
  public String filed2;
  @XmlElement
  public String filed3;
}
