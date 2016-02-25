package gnnt.bank.adapter.bankBusiness.message;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HEAD
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @XmlElement
  public String version;
  @XmlElement
  public String InstID;
  @XmlElement
  public String trmSeqNum;
  @XmlElement
  public String tranDate;
  @XmlElement
  public String tranTime;
  @XmlElement
  public String tradeCode;
  @XmlElement
  public String servName;
  @XmlElement
  public String reserve1;
  @XmlElement
  public String reserve2;
  @XmlElement
  public String reserve3;
}
