package gnnt.bank.adapter.bankBusiness.message;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RSP_BODY_F608_FRAME
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @XmlElement
  public String memNum;

  @XmlElement
  public String outAmount;

  @XmlElement
  public String accountBank;

  @XmlElement
  public String accountBankName;

  @XmlElement
  public String accountName;

  @XmlElement
  public String account;

  @XmlElement
  public String applyTime;

  @XmlElement
  public String currency;

  @XmlElement
  public String isCrossLine;

  @XmlElement
  public String atOnce;

  @XmlElement
  public String applyId;

  @XmlElement
  public String brief;

  @XmlElement
  public String filed1;

  @XmlElement
  public String filed2;

  @XmlElement
  public String filed3;
}