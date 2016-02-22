package gnnt.bank.adapter.bankBusiness.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class REQ_BODY_F623
{

  @XmlElement
  public String busiNum;

  @XmlElement
  public String busiSerialNum;

  @XmlElement
  public String transType;

  @XmlElement
  public String transSerialNum;

  @XmlElement
  public String orderNum;

  @XmlElement
  public String fromAccountNum;

  @XmlElement
  public String amount;

  @XmlElement
  public String fee;

  @XmlElement
  public String toAccountNum;

  @XmlElement
  public String otherFee;

  @XmlElement
  public String type;

  @XmlElement
  public String tradeTime;

  @XmlElement
  public String currency;

  @XmlElement
  public String brief;

  @XmlElement
  public String filed1;

  @XmlElement
  public String filed2;

  @XmlElement
  public String filed3;
}