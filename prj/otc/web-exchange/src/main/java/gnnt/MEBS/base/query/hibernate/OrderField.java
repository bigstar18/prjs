package gnnt.MEBS.base.query.hibernate;

public class OrderField
{
  private String orderField;
  private boolean orderDesc;
  
  public OrderField(String orderField, boolean orderDesc)
  {
    this.orderField = orderField;
    this.orderDesc = orderDesc;
  }
  
  public boolean isOrderDesc()
  {
    return this.orderDesc;
  }
  
  public void setOrderDesc(boolean orderDesc)
  {
    this.orderDesc = orderDesc;
  }
  
  public String getOrderField()
  {
    return this.orderField;
  }
  
  public void setOrderField(String orderField)
  {
    this.orderField = orderField;
  }
  
  public boolean getOrderDesc()
  {
    return this.orderDesc;
  }
}
