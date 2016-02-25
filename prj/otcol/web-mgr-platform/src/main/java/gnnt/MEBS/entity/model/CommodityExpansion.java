package gnnt.MEBS.entity.model;

import gnnt.MEBS.base.model.Clone;

public class CommodityExpansion
  extends Clone
{
  private String commodityId;
  private String kind;
  private String name;
  private int No;
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String paramString)
  {
    this.commodityId = paramString;
  }
  
  public String getKind()
  {
    return this.kind;
  }
  
  public void setKind(String paramString)
  {
    this.kind = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public int getNo()
  {
    return this.No;
  }
  
  public void setNo(int paramInt)
  {
    this.No = paramInt;
  }
}
