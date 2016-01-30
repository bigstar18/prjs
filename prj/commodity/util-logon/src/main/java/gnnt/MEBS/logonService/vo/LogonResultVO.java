package gnnt.MEBS.logonService.vo;

public class LogonResultVO extends ResultVO
{
  private static final long serialVersionUID = -4008331784951547600L;
  private UserManageVO userManageVO;

  public UserManageVO getUserManageVO()
  {
    return this.userManageVO;
  }

  public void setUserManageVO(UserManageVO paramUserManageVO)
  {
    this.userManageVO = paramUserManageVO;
  }
}