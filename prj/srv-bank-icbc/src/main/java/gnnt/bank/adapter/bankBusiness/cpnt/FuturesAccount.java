package gnnt.bank.adapter.bankBusiness.cpnt;

import gnnt.bank.adapter.bankBusiness.enumElmt.AccountStatus;
import gnnt.bank.adapter.bankBusiness.enumElmt.YesNoIndicator;
import javax.xml.bind.annotation.XmlElement;

public class FuturesAccount
{

  @XmlElement
  public String Id = "";

  @XmlElement
  public String Name = "";

  @XmlElement
  public int Status = AccountStatus.NORMAL.getValue();

  @XmlElement
  public Password Pwd = new Password();

  @XmlElement
  public YesNoIndicator OpenFlg = YesNoIndicator.Y;

  @XmlElement
  public String RegDt = "";

  @XmlElement
  public String VldDt = "";
}