package gnnt.MEBS.bill.core.bo;

public class StockOutAuditBO
  extends BaseBO
{
  private static final long serialVersionUID = -5182029433999518694L;
  private String stockID;
  private String deliveryPerson;
  private String idnumber;
  private String key;
  
  public String getStockID()
  {
    return this.stockID;
  }
  
  public void setStockID(String paramString)
  {
    this.stockID = paramString;
  }
  
  public String getDeliveryPerson()
  {
    return this.deliveryPerson;
  }
  
  public void setDeliveryPerson(String paramString)
  {
    this.deliveryPerson = paramString;
  }
  
  public String getIdnumber()
  {
    return this.idnumber;
  }
  
  public void setIdnumber(String paramString)
  {
    this.idnumber = paramString;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String paramString)
  {
    this.key = paramString;
  }
}
