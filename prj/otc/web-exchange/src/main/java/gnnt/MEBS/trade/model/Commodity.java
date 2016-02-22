package gnnt.MEBS.trade.model;

public class Commodity
{
  private String id;
  
  public Commodity() {}
  
  public Commodity(String id)
  {
    this.id = id;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
}
