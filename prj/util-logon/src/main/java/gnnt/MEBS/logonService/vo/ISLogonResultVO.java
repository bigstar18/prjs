package gnnt.MEBS.logonService.vo;

public class ISLogonResultVO extends ResultVO
{
  private static final long serialVersionUID = -5637391744278487851L;
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