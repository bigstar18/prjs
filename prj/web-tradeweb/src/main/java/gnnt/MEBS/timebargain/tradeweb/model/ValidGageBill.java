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
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof ValidGageBill)) {
      return false;
    }
    ValidGageBill localValidGageBill = (ValidGageBill)paramObject;
    return this.FirmID != null ? this.FirmID.equals(localValidGageBill.FirmID) : localValidGageBill.FirmID == null;
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
  
  public void setFirmID(String paramString)
  {
    this.FirmID = paramString;
  }
  
  public String getBreedName()
  {
    return this.BreedName;
  }
  
  public void setBreedName(String paramString)
  {
    this.BreedName = paramString;
  }
  
  public String getQuantity()
  {
    return this.Quantity;
  }
  
  public void setQuantity(String paramString)
  {
    this.Quantity = paramString;
  }
  
  public String getFrozenQty()
  {
    return this.FrozenQty;
  }
  
  public void setFrozenQty(String paramString)
  {
    this.FrozenQty = paramString;
  }
}
