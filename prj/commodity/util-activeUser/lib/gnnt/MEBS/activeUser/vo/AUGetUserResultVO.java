package gnnt.MEBS.activeUser.vo;

public class AUGetUserResultVO
  extends AUResultVO
{
  private static final long serialVersionUID = 5404802981669619612L;
  private AUUserManageVO userManageVO;
  
  public AUUserManageVO getUserManageVO()
  {
    return this.userManageVO;
  }
  
  public void setUserManageVO(AUUserManageVO userManageVO)
  {
    this.userManageVO = userManageVO;
  }
}
