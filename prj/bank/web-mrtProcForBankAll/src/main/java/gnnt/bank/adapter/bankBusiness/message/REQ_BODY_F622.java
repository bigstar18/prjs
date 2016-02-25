package gnnt.bank.adapter.bankBusiness.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class REQ_BODY_F622
{
  @XmlElement
  public String busiNum;
  @XmlElement
  public String queryCode;
  @XmlElement
  public String querySerialNum;
  @XmlElement
  public String filed1;
  @XmlElement
  public String filed2;
  @XmlElement
  public String filed3;
}
