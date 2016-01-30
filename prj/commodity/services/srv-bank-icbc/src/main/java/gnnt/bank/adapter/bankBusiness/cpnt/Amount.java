package gnnt.bank.adapter.bankBusiness.cpnt;

import gnnt.bank.adapter.bankBusiness.enumElmt.CurrencyCode;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Amount
{

  @XmlValue
  public long amt;

  @XmlAttribute
  public String ccy = CurrencyCode.RMB.toString();
}