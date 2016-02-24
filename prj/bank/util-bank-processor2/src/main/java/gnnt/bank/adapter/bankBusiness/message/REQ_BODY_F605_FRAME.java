package gnnt.bank.adapter.bankBusiness.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class REQ_BODY_F605_FRAME
{
  @XmlElement
  public String bindOpenBank;
  @XmlElement
  public String bindOpenOrgNum;
  @XmlElement
  public String bindOpenBankName;
  @XmlElement
  public String bindAccNum;
  @XmlElement
  public String bindAccName;
  @XmlElement
  public String isCrossLine;
  @XmlElement
  public String originBindOpenBank;
  @XmlElement
  public String originBindOpenOrgNum;
  @XmlElement
  public String originBindOpenBankName;
  @XmlElement
  public String originBindAccNum;
  @XmlElement
  public String filed1;
  @XmlElement
  public String filed2;
  @XmlElement
  public String filed3;
}
