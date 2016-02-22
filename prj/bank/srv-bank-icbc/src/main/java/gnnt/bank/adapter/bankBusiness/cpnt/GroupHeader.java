package gnnt.bank.adapter.bankBusiness.cpnt;

import gnnt.bank.adapter.bankBusiness.enumElmt.BusinessCode;
import gnnt.bank.adapter.bankBusiness.enumElmt.SystemType;
import gnnt.bank.adapter.bankBusiness.enumElmt.TradeSource;
import javax.xml.bind.annotation.XmlElement;

public class GroupHeader
{

  @XmlElement
  public String Version = "1.0.0";

  @XmlElement
  public int SysType = SystemType.BF.getValue();

  @XmlElement
  public int BusCd = BusinessCode.CODE20001.getValue();

  @XmlElement
  public TradeSource TradSrc = TradeSource.F;

  @XmlElement
  public Institution Sender = new Institution();

  @XmlElement
  public Institution Recver = new Institution();

  @XmlElement
  public String Date = "";

  @XmlElement
  public String Time = "";
}