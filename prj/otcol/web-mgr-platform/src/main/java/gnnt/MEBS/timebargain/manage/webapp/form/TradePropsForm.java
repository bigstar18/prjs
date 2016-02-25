package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class TradePropsForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049815L;
  private String customerID;
  private String groupID;
  private String maxHoldQty;
  private String minClearDeposit;
  private String maxOverdraft;
  private String modifyTime;
  private String virtualFunds;
  private String crud = "";
  private String moduleName = "";
  
  public String getModuleName()
  {
    return this.moduleName;
  }
  
  public void setModuleName(String paramString)
  {
    this.moduleName = paramString;
  }
  
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
  
  public String getMaxOverdraft()
  {
    return this.maxOverdraft;
  }
  
  public void setMaxOverdraft(String paramString)
  {
    this.maxOverdraft = paramString;
  }
  
  public String getMinClearDeposit()
  {
    return this.minClearDeposit;
  }
  
  public void setMinClearDeposit(String paramString)
  {
    this.minClearDeposit = paramString;
  }
  
  public String getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(String paramString)
  {
    this.modifyTime = paramString;
  }
  
  public String getVirtualFunds()
  {
    return this.virtualFunds;
  }
  
  public void setVirtualFunds(String paramString)
  {
    this.virtualFunds = paramString;
  }
}
