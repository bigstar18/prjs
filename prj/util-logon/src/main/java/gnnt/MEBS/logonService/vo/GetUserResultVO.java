package gnnt.MEBS.logonService.vo;

public class GetUserResultVO extends ResultVO
{
  private static final long serialVersionUID = -3728502476951594091L;
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