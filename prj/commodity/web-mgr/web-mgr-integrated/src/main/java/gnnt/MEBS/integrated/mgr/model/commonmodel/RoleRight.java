package gnnt.MEBS.integrated.mgr.model.commonmodel;

import gnnt.MEBS.common.mgr.model.Right;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class RoleRight
  extends StandardModel
{
  private static final long serialVersionUID = -6758600941215931624L;
  @ClassDiscription(name="角色代码", description="")
  private Long roleId;
  @ClassDiscription(name="权限", description="")
  private Right cright;
  
  public Long getRoleId()
  {
    return this.roleId;
  }
  
  public void setRoleId(Long paramLong)
  {
    this.roleId = paramLong;
  }
  
  public Right getCright()
  {
    return this.cright;
  }
  
  public void setCright(Right paramRight)
  {
    this.cright = paramRight;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
