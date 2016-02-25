package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class ProsceniumShow
  extends Clone
{
  private String prosceniumApplication;
  private String showProperty;
  private String showName;
  private String nodeKey;
  private int serialNumber;
  private String type;
  private String renderer;
  private String isShow;
  private String isQuery;
  
  public String getProsceniumApplication()
  {
    return this.prosceniumApplication;
  }
  
  public void setProsceniumApplication(String paramString)
  {
    this.prosceniumApplication = paramString;
  }
  
  public String getShowProperty()
  {
    return this.showProperty;
  }
  
  public void setShowProperty(String paramString)
  {
    this.showProperty = paramString;
  }
  
  public String getShowName()
  {
    return this.showName;
  }
  
  public void setShowName(String paramString)
  {
    this.showName = paramString;
  }
  
  public String getNodeKey()
  {
    return this.nodeKey;
  }
  
  public void setNodeKey(String paramString)
  {
    this.nodeKey = paramString;
  }
  
  public int getSerialNumber()
  {
    return this.serialNumber;
  }
  
  public void setSerialNumber(int paramInt)
  {
    this.serialNumber = paramInt;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public String getRenderer()
  {
    return this.renderer;
  }
  
  public void setRenderer(String paramString)
  {
    this.renderer = paramString;
  }
  
  public String getIsShow()
  {
    return this.isShow;
  }
  
  public void setIsShow(String paramString)
  {
    this.isShow = paramString;
  }
  
  public String getIsQuery()
  {
    return this.isQuery;
  }
  
  public void setIsQuery(String paramString)
  {
    this.isQuery = paramString;
  }
}
