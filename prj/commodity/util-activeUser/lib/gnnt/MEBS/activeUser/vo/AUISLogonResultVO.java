package gnnt.MEBS.activeUser.vo;

public class AUISLogonResultVO
  extends AUResultVO
{
  private static final long serialVersionUID = -5637391744278487851L;
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
