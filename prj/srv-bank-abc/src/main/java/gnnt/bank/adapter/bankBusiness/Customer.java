package gnnt.bank.adapter.bankBusiness;

import gnnt.bank.adapter.util.CertificationType;
import javax.xml.bind.annotation.XmlElement;

public class Customer
{

  @XmlElement
  public String Name = "";

  @XmlElement
  public int CertType = CertificationType.IDCARD.getValue();

  @XmlElement
  public String CertId = "";

  @XmlElement
  public int Type = CustomerType.ORGANIZE.getValue();

  @XmlElement
  public String Addr = "";

  @XmlElement
  public String PstCd = "";

  @XmlElement
  public String Email = "";

  @XmlElement
  public String Fax = "";

  @XmlElement
  public String Mobile = "";

  @XmlElement
  public String Tel = "";

  @XmlElement
  public String Bp = "";
}