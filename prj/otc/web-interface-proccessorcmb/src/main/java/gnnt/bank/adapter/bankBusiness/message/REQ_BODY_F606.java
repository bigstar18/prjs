package gnnt.bank.adapter.bankBusiness.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class REQ_BODY_F606
{
  @XmlElement
  public String busiNum;
  @XmlElement
  public String currency;
  @XmlElement
  public String busiSerialNum;
  @XmlElement
  public String memNum;
  @XmlElement
  public String accNum;
  @XmlElement
  public String ammount;
  @XmlElement
  public String time;
  @XmlElement
  public String isCrossLine;
  @XmlElement
  public String filed1;
  @XmlElement
  public String filed2;
  @XmlElement
  public String filed3;
}
