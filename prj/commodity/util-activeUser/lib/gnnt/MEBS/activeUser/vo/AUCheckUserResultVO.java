package gnnt.MEBS.activeUser.vo;

public class AUCheckUserResultVO
  extends AUResultVO
{
  private static final long serialVersionUID = 7926723787864184497L;
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
