package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;

public class LogCataLogSearch
  extends Clone
{
  private Long cataLogId;
  private Long moudleId;
  private String catalogName;
  
  public Long getCataLogId()
  {
    return this.cataLogId;
  }
  
  public void setCataLogId(Long cataLogId)
  {
    this.cataLogId = cataLogId;
  }
  
  public Long getMoudleId()
  {
    return this.moudleId;
  }
  
  public void setMoudleId(Long moudleId)
  {
    this.moudleId = moudleId;
  }
  
  public String getCatalogName()
  {
    return this.catalogName;
  }
  
  public void setCatalogName(String catalogName)
  {
    this.catalogName = catalogName;
  }
  
  public void setPrimary(String primary) {}
  
  public String getId()
  {
    return null;
  }
}
