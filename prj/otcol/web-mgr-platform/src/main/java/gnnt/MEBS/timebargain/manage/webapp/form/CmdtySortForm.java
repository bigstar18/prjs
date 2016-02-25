package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class CmdtySortForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049822L;
  private String sortID;
  private String sortName;
  private String maxHoldQty;
  private String groupID;
  private String customerID;
  private String modifyTime;
  private String crud = "";
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String paramString)
  {
    this.crud = paramString;
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public String getGroupID()
  {
    return this.groupID;
  }
  
  public void setGroupID(String paramString)
  {
    this.groupID = paramString;
  }
  
  public String getMaxHoldQty()
  {
    return this.maxHoldQty;
  }
  
  public void setMaxHoldQty(String paramString)
  {
    this.maxHoldQty = paramString;
  }
  
  public String getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(String paramString)
  {
    this.modifyTime = paramString;
  }
  
  public String getSortID()
  {
    return this.sortID;
  }
  
  public void setSortID(String paramString)
  {
    this.sortID = paramString;
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
