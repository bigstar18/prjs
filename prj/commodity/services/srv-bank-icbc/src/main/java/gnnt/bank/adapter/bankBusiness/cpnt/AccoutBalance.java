package gnnt.bank.adapter.bankBusiness.cpnt;

import javax.xml.bind.annotation.XmlElement;

public class AccoutBalance
{

  @XmlElement
  public Amount CurBal = new Amount();

  @XmlElement
  public Amount UseBal = new Amount();

  @XmlElement
  public Amount FtcBal = new Amount();
}