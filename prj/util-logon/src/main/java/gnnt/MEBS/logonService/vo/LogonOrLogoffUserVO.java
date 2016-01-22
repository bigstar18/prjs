package gnnt.MEBS.logonService.vo;

import java.io.Serializable;

public class LogonOrLogoffUserVO
  implements Serializable
{
  private static final long serialVersionUID = 5801424245105564486L;
  private int logonOrlogOff;
  private UserManageVO userManageVO;

  public int getLogonOrlogOff()
  {
    return this.logonOrlogOff;
  }

  public void setLogonOrlogOff(int paramInt)
  {
    this.logonOrlogOff = paramInt;
  }

  public UserManageVO getUserManageVO()
  {
    return this.userManageVO;
  }

  public void setUserManageVO(UserManageVO paramUserManageVO)
  {
    this.userManageVO = paramUserManageVO;
  }
}