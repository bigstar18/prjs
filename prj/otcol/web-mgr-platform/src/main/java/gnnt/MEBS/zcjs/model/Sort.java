package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class Sort
  extends Clone
{
  private long sortId;
  private String sortName;
  
  public long getSortId()
  {
    return this.sortId;
  }
  
  public void setSortId(long paramLong)
  {
    this.sortId = paramLong;
  }
  
  public String getSortName()
  {
    return this.sortName;
  }
  
  public void setSortName(String paramString)
  {
    this.sortName = paramString;
  }
}
