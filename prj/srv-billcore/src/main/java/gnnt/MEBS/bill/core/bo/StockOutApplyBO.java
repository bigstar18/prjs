package gnnt.MEBS.bill.core.bo;

public class StockOutApplyBO
  extends BaseBO
{
  private static final long serialVersionUID = -4656133476939960187L;
  private String stockID;
  private String deliveryPerson;
  private String idnumber;
  private String address;
  private String phone;
  private String deliveryStatus;
  private String key;
  
  public String getStockID()
  {
    return this.stockID;
  }
  
  public void setStockID(String stockID)
  {
    this.stockID = stockID;
  }
  
  public String getDeliveryPerson()
  {
    return this.deliveryPerson;
  }
  
  public void setDeliveryPerson(String deliveryPerson)
  {
    this.deliveryPerson = deliveryPerson;
  }
  
  public String getIdnumber()
  {
    return this.idnumber;
  }
  
  public void setIdnumber(String idnumber)
  {
    this.idnumber = idnumber;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  public String getDeliveryStatus()
  {
    return this.deliveryStatus;
  }
  
  public void setDeliveryStatus(String deliveryStatus)
  {
    this.deliveryStatus = deliveryStatus;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
}
