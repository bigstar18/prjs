package gnnt.MEBS.timebargain.tradeweb.query;

public class OrderField
{
  private String orderField;
  private boolean orderDesc;
  
  public OrderField(String paramString, boolean paramBoolean)
  {
    this.orderField = paramString;
    this.orderDesc = paramBoolean;
  }
  
  public boolean isOrderDesc()
  {
    return this.orderDesc;
  }
  
  public void setOrderDesc(boolean paramBoolean)
  {
    this.orderDesc = paramBoolean;
  }
  
  public String getOrderField()
  {
    return this.orderField;
  }
  
  public void setOrderField(String paramString)
  {
    this.orderField = paramString;
  }
  
  public boolean getOrderDesc()
  {
    return this.orderDesc;
  }
}
