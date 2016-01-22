package gnnt.bank.adapter.bankBusiness.message;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RSP_BODY_F601
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @XmlElement
  public String customId;

  @XmlElement
  public String busiNum;

  @XmlElement
  public String busiName;

  @XmlElement
  public String busiType;

  @XmlElement
  public String serviceType;

  @XmlElement
  public String busiIp;

  @XmlElement
  public String busiIcpNum;

  @XmlElement
  public String payLicenceNum;

  @XmlElement
  public String custServiceTel;

  @XmlElement
  public String custodyType;

  @XmlElement
  public String startDate;

  @XmlElement
  public String endDate;

  @XmlElement
  public String coopType;

  @XmlElement
  public String busiRemark;

  @XmlElement
  public String busiState;

  @XmlElement
  public String totalCheckRule;

  @XmlElement
  public String totalCheckDate;

  @XmlElement
  public String paySign;

  @XmlElement
  public String drawPercent;

  @XmlElement
  public String ctlAmountSign;

  @XmlElement
  public String ctlPercent;

  @XmlElement
  public String confirmSign;

  @XmlElement
  public String result;

  @XmlElement
  public String filed1;

  @XmlElement
  public String filed2;

  @XmlElement
  public String filed3;
}