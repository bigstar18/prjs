package gnnt.bank.adapter.bankBusiness.message;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class REQ_BODY_F612
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @XmlElement
  public String busiNum;
  @XmlElement
  public String flag;
  @XmlElement
  public String busiSerialNum;
  @XmlElement
  public String currency;
  @XmlElement
  public String amount;
  @XmlElement
  public String tradeTime;
  @XmlElement
  public String atOnce;
  @XmlElement
  public String isCrossLine;
  @XmlElement
  public String targetAccountBankNum;
  @XmlElement
  public String targetAccountBank;
  @XmlElement
  public String targetAccountBankName;
  @XmlElement
  public String targetAccountName;
  @XmlElement
  public String targetAccount;
  @XmlElement
  public String sourceAccountName;
  @XmlElement
  public String sourceAccount;
  @XmlElement
  public String remark;
  @XmlElement
  public String filed1;
  @XmlElement
  public String filed2;
  @XmlElement
  public String filed3;
}
