package gnnt.bank.adapter.bankBusiness.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class REQ_BODY_F609
{

  @XmlElement
  public String busiNum;

  @XmlElement
  public String memNum;

  @XmlElement
  public String mark;

  @XmlElement
  public String virAccType;

  @XmlElement
  public String startNum;

  @XmlElement
  public String queryNum;

  @XmlElement
  public String startDate;

  @XmlElement
  public String endDate;

  @XmlElement
  public String filed1;

  @XmlElement
  public String filed2;

  @XmlElement
  public String filed3;
}