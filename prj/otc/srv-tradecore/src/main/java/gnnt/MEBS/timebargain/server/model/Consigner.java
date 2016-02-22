package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Consigner
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049933L;
  private String consignerID;
  private String name;
  private String password;
  private short type;
  private short status;
  private List operateFirmList;
  private String logonIP;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getConsignerID()
  {
    return this.consignerID;
  }
  
  public void setConsignerID(String consignerID)
  {
    this.consignerID = consignerID;
  }
  
  public String getLogonIP()
  {
    return this.logonIP;
  }
  
  public void setLogonIP(String logonIP)
  {
    this.logonIP = logonIP;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public List getOperateFirmList()
  {
    return this.operateFirmList;
  }
  
  public void setOperateFirmList(List operateFirmList)
  {
    this.operateFirmList = operateFirmList;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public short getStatus()
  {
    return this.status;
  }
  
  public void setStatus(short status)
  {
    this.status = status;
  }
  
  public short getType()
  {
    return this.type;
  }
  
  public void setType(short type)
  {
    this.type = type;
  }
}
