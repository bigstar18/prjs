package gnnt.MEBS.delivery.model;

import gnnt.MEBS.base.model.Clone;

public class User
  extends Clone
{
  public String userId;
  public String name;
  public String password;
  public String manage_id;
  public int roleStatus;
  public int manage_popedom;
  public String popedom;
  public String warehousename;
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public int getManage_popedom()
  {
    return this.manage_popedom;
  }
  
  public void setManage_popedom(int paramInt)
  {
    this.manage_popedom = paramInt;
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public String getPopedom()
  {
    return this.popedom;
  }
  
  public void setPopedom(String paramString)
  {
    this.popedom = paramString;
  }
  
  public int getRoleStatus()
  {
    return this.roleStatus;
  }
  
  public void setRoleStatus(int paramInt)
  {
    this.roleStatus = paramInt;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  
  public String getWarehousename()
  {
    return this.warehousename;
  }
  
  public void setWarehousename(String paramString)
  {
    this.warehousename = paramString;
  }
  
  public String getManage_id()
  {
    return this.manage_id;
  }
  
  public void setManage_id(String paramString)
  {
    this.manage_id = paramString;
  }
}
