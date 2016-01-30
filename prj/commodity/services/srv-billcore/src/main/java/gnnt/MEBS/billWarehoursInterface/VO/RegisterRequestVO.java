package gnnt.MEBS.billWarehoursInterface.VO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GNNT")
public class RegisterRequestVO
  extends RequestVO
{
  private static final long serialVersionUID = -1578355921084898801L;
  private PropertyVO property;
  private String firmID;
  private String firmName;
  private String wareHousePassword;
  private String categoryName;
  private String breedName;
  private String unit;
  private double quantity;
  private String stockID;
  private String wareHouseID;
  
  @XmlElement(name="WAREHOUSEPASSWORD")
  public String getWareHousePassword()
  {
    return this.wareHousePassword;
  }
  
  public void setWareHousePassword(String wareHousePassword)
  {
    this.wareHousePassword = wareHousePassword;
  }
  
  @XmlElement(name="CATEGORYNAME")
  public String getCategoryName()
  {
    return this.categoryName;
  }
  
  public void setCategoryName(String categoryName)
  {
    this.categoryName = categoryName;
  }
  
  @XmlElement(name="BREEDNAME")
  public String getBreedName()
  {
    return this.breedName;
  }
  
  public void setBreedName(String breedName)
  {
    this.breedName = breedName;
  }
  
  @XmlElement(name="UNIT")
  public String getUnit()
  {
    return this.unit;
  }
  
  public void setUnit(String unit)
  {
    this.unit = unit;
  }
  
  @XmlElement(name="PROPERTYLIST")
  public PropertyVO getProperty()
  {
    return this.property;
  }
  
  public void setProperty(PropertyVO property)
  {
    this.property = property;
  }
  
  @XmlElement(name="WAREHOUSEID")
  public String getWareHouseID()
  {
    return this.wareHouseID;
  }
  
  public void setWareHouseID(String wareHouseID)
  {
    this.wareHouseID = wareHouseID;
  }
  
  @XmlElement(name="QUANTITY")
  public double getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(double quantity)
  {
    this.quantity = quantity;
  }
  
  @XmlElement(name="FIRMID")
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  @XmlElement(name="FIRMNAME")
  public String getFirmName()
  {
    return this.firmName;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }
  
  @XmlElement(name="STOCKID")
  public String getStockID()
  {
    return this.stockID;
  }
  
  public void setStockID(String stockID)
  {
    this.stockID = stockID;
  }
  
  public RegisterRequestVO()
  {
    setProtocolName(ProtocolName.register);
  }
}
