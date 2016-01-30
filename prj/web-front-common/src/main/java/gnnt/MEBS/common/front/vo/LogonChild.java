package gnnt.MEBS.common.front.vo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class LogonChild
{
  @XmlAttribute(name="name")
  public String name;
  @XmlElement(name="USER_ID")
  public String userID;
  @XmlElement(name="TRADER_ID")
  public String traderID;
  @XmlElement(name="CUSTOMER_ID")
  public String customerID;
  @XmlElement(name="PASSWORD")
  public String password;
  @XmlElement(name="REGISTER_WORD")
  public String key;
  @XmlElement(name="VERSIONINFO")
  public String msg;
  @XmlElement(name="LA")
  public String language;
  @XmlElement(name="LOGONTYPE")
  public String logonType;
  @XmlElement(name="MODULEID")
  public String selfModuleID;
}
