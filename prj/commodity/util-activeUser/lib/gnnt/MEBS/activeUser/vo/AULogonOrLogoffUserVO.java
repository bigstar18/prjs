package gnnt.MEBS.activeUser.vo;

import java.io.Serializable;

public class AULogonOrLogoffUserVO
  implements Serializable
{
  private static final long serialVersionUID = 5801424245105564486L;
  private int logonOrlogOff;
  private AUUserManageVO userManageVO;
  
  public int getLogonOrlogOff()
  {
    return this.logonOrlogOff;
  }
  
  public void setLogonOrlogOff(int logonOrlogOff)
  {
    this.logonOrlogOff = logonOrlogOff;
  }
  
  public AUUserManageVO getUserManageVO()
  {
    return this.userManageVO;
  }
  
  public void setUserManageVO(AUUserManageVO userManageVO)
  {
    this.userManageVO = userManageVO;
  }
}
