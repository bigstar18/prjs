package gnnt.MEBS.common.front.vo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class CheckUserChild
{
  @XmlAttribute(name="name")
  public String name;
  @XmlElement(name="USER_ID")
  public String userID;
  @XmlElement(name="SESSION_ID")
  public String sessionID;
  @XmlElement(name="MODULE_ID")
  public String moduleID;
  @XmlElement(name="F_LOGONTYPE")
  public String fromLogonType;
  @XmlElement(name="LOGONTYPE")
  public String selfLogonType;
  @XmlElement(name="MODULEID")
  public String selfModuleID;
}
