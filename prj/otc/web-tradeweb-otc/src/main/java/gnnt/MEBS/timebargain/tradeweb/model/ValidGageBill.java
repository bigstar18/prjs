package gnnt.MEBS.timebargain.tradeweb.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ValidGageBill
  extends BaseObject
  implements Serializable
{
  private String FirmID;
  private String BreedName;
  private String Quantity;
  private String FrozenQty;
  private static final long serialVersionUID = -9010654975336805961L;
  
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ValidGageBill)) {
      return false;
    }
    ValidGageBill m = (ValidGageBill)o;
    
    return this.FirmID != null ? this.FirmID.equals(m.FirmID) : 
      m.FirmID == null;
  }
  
  public int hashCode()
  {
    return this.FirmID != null ? this.FirmID.hashCode() : 0;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getFirmID()
  {
    return this.FirmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.FirmID = firmID;
  }
  
  public String getBreedName()
  {
    return this.BreedName;
  }
  
  public void setBreedName(String breedName)
  {
    this.BreedName = breedName;
  }
  
  public String getQuantity()
  {
    return this.Quantity;
  }
  
  public void setQuantity(String quantity)
  {
    this.Quantity = quantity;
  }
  
  public String getFrozenQty()
  {
    return this.FrozenQty;
  }
  
  public void setFrozenQty(String frozenQty)
  {
    this.FrozenQty = frozenQty;
  }
}
