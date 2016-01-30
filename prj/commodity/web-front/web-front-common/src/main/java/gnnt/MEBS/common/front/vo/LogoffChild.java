package gnnt.MEBS.common.front.vo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class LogoffChild
{
  @XmlAttribute(name="name")
  public String name;
  @XmlElement(name="USER_ID")
  public String userID;
  @XmlElement(name="SESSION_ID")
  public String sessionID;
  @XmlElement(name="TRADER_ID")
  public String traderID;
  @XmlElement(name="CUSTOMER_ID")
  public String customerID;
}
