package gnnt.MEBS.activeUser.vo;

public class AULogonResultVO
  extends AUResultVO
{
  private static final long serialVersionUID = -4008331784951547600L;
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
