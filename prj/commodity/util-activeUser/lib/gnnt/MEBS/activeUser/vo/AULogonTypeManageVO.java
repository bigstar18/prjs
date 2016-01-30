package gnnt.MEBS.activeUser.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AULogonTypeManageVO
  extends AUBaseVO
{
  private static final long serialVersionUID = 7813463771868808545L;
  private Map<Long, AUUserManageVO> sessionMap = new HashMap();
  private Map<String, List<AUUserManageVO>> userMap = new HashMap();
  
  public Map<Long, AUUserManageVO> getSessionMap()
  {
    return this.sessionMap;
  }
  
  public void setSessionMap(Map<Long, AUUserManageVO> sessionMap)
  {
    this.sessionMap = sessionMap;
  }
  
  public Map<String, List<AUUserManageVO>> getUserMap()
  {
    return this.userMap;
  }
  
  public void setUserMap(Map<String, List<AUUserManageVO>> userMap)
  {
    this.userMap = userMap;
  }
}
