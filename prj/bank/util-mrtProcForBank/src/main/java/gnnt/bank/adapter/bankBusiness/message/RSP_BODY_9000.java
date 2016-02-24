package gnnt.bank.adapter.bankBusiness.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RSP_BODY_9000
{

  @XmlElement
  public String returnCode;

  @XmlElement
  public String errorDescription;

  @XmlElement
  public String keyName;

  @XmlElement
  public String keyValue;

  @XmlElement
  public String verifyValue;

  @XmlElement
  public String keyName1;

  @XmlElement
  public String keyValue1;

  @XmlElement
  public String verifyValue1;

  @XmlElement
  public String field1;
}