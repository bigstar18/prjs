package gnnt.MEBS.vendue.manage.servlet;

public class Right
{
  public String id;
  public String parentId;
  public long marknum;
  public String description;
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setParentId(String paramString)
  {
    this.parentId = paramString;
  }
  
  public String getParentId()
  {
    return this.parentId;
  }
  
  public void setMarknum(long paramLong)
  {
    this.marknum = paramLong;
  }
  
  public long getMarknum()
  {
    return this.marknum;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public String getDescription()
  {
    return this.description;
  }
}
