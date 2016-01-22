package gnnt.MEBS.logonService.vo;

public class CheckUserResultVO extends ResultVO
{
  private static final long serialVersionUID = 7926723787864184497L;
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