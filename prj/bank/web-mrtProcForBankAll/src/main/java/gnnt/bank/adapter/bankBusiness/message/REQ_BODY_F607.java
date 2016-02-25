package gnnt.bank.adapter.bankBusiness.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class REQ_BODY_F607
{
  @XmlElement
  public String busiNum;
  @XmlElement
  public String type;
  @XmlElement
  public String memNum;
  @XmlElement
  public String appId;
  @XmlElement
  public String busiSerialNum;
  @XmlElement
  public String outAmount;
  @XmlElement
  public String outAccount;
  @XmlElement
  public String accountBankName;
  @XmlElement
  public String jointLineNum;
  @XmlElement
  public String accountName;
  @XmlElement
  public String account;
  @XmlElement
  public String applyTime;
  @XmlElement
  public String currency;
  @XmlElement
  public String atOnce;
  @XmlElement
  public String isCrossLine;
  @XmlElement
  public String brief;
  @XmlElement
  public String filed1;
  @XmlElement
  public String filed2;
  @XmlElement
  public String filed3;
}
