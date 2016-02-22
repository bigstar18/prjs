package gnnt.MEBS.timebargain.tradeweb.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Trader
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049817L;
  private String TraderID;
  private String Name;
  private String Password;
  private String Status;
  
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Trader)) {
      return false;
    }
    Trader m = (Trader)o;
    
    return this.TraderID != null ? this.TraderID.equals(m.TraderID) : 
      m.TraderID == null;
  }
  
  public int hashCode()
  {
    return this.TraderID != null ? this.TraderID.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, 
      ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getName()
  {
    return this.Name;
  }
  
  public void setName(String name)
  {
    this.Name = name;
  }
  
  public String getPassword()
  {
    return this.Password;
  }
  
  public void setPassword(String password)
  {
    this.Password = password;
  }
  
  public String getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(String status)
  {
    this.Status = status;
  }
  
  public String getTraderID()
  {
    return this.TraderID;
  }
  
  public void setTraderID(String traderID)
  {
    this.TraderID = traderID;
  }
}
