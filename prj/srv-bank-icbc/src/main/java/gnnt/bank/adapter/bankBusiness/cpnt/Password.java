package gnnt.bank.adapter.bankBusiness.cpnt;

import gnnt.bank.adapter.bankBusiness.enumElmt.EncryMode;
import gnnt.bank.adapter.bankBusiness.enumElmt.PasswordType;
import javax.xml.bind.annotation.XmlElement;

public class Password
{

  @XmlElement
  public int Type = PasswordType.QRY.getValue();

  @XmlElement
  public String Enc = EncryMode.NONE.getValue();

  @XmlElement
  public String Pwd = "";
}