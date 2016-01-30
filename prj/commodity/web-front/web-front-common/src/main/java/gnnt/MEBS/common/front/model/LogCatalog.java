package gnnt.MEBS.common.front.model;

import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class LogCatalog
  extends StandardModel
{
  private static final long serialVersionUID = -4112756031753238119L;
  @ClassDiscription(name="日志类别号", description="")
  private Integer catalogID;
  @ClassDiscription(name="日志类别名称", description="")
  private String catalogName;
  @ClassDiscription(name="日志所属模块号", description="")
  private Integer moduleID;
  
  public Integer getCatalogID()
  {
    return this.catalogID;
  }
  
  public void setCatalogID(Integer paramInteger)
  {
    this.catalogID = paramInteger;
  }
  
  public String getCatalogName()
  {
    return this.catalogName;
  }
  
  public void setCatalogName(String paramString)
  {
    this.catalogName = paramString;
  }
  
  public Integer getModuleID()
  {
    return this.moduleID;
  }
  
  public void setModuleID(Integer paramInteger)
  {
    this.moduleID = paramInteger;
  }
  
  public PrimaryInfo fetchPKey()
  {
    return new PrimaryInfo("catalogID", this.catalogID);
  }
}
