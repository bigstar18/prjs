package gnnt.bank.adapter.bankBusiness.message;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RSP_BODY_F609_FRAME
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @XmlElement
  public String fcsSerialNum;

  @XmlElement
  public String memName;

  @XmlElement
  public String memNum;

  @XmlElement
  public String addReduce;

  @XmlElement
  public String tradeAmount;

  @XmlElement
  public String currency;

  @XmlElement
  public String procTime;

  @XmlElement
  public String balance;

  @XmlElement
  public String useableBalance;

  @XmlElement
  public String otherName;

  @XmlElement
  public String otherNum;

  @XmlElement
  public String isCrossLine;

  @XmlElement
  public String busType;

  @XmlElement
  public String brief;

  @XmlElement
  public String filed1;

  @XmlElement
  public String filed2;

  @XmlElement
  public String filed3;
}