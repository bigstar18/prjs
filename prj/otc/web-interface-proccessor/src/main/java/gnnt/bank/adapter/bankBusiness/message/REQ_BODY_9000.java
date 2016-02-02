package gnnt.bank.adapter.bankBusiness.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class REQ_BODY_9000
{
  @XmlElement
  public String operationDate;
  @XmlElement
  public String field1;
}
