package gnnt.bank.adapter.bankBusiness.cpnt;

import gnnt.bank.adapter.bankBusiness.enumElmt.AccountStatus;
import gnnt.bank.adapter.bankBusiness.enumElmt.BankAccountType;
import gnnt.bank.adapter.bankBusiness.enumElmt.YesNoIndicator;
import javax.xml.bind.annotation.XmlElement;

public class BankAccount
{

  @XmlElement
  public String Id = "";

  @XmlElement
  public String Name = "";

  @XmlElement
  public int St = AccountStatus.NORMAL.getValue();

  @XmlElement
  public int Type = BankAccountType.BANKBOOK.getValue();

  @XmlElement
  public Password Pwd = new Password();

  @XmlElement
  public YesNoIndicator OpFlg = YesNoIndicator.Y;

  @XmlElement
  public String RegDt = "";

  @XmlElement
  public String VldDt = "";

  @XmlElement
  public String ICBCFlag = "";

  @XmlElement
  public String RcvCode = "";
}