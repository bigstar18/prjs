package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.memoryModel.RightMemory;
import java.util.Map;
import javax.annotation.Resource;

public class SpecialHandle
  extends AbstractSecurityHandler
{
  private SecurityHandler secondSecurityHandler;
  @Resource(name="commonAndSpecialRightMap")
  private Map<String, Right> commonAndSpecialRightMap;
  
  public void setSecondSecurityHandler(SecurityHandler secondSecurityHandler)
  {
    this.secondSecurityHandler = secondSecurityHandler;
  }
  
  public void setCommonAndSpecialRightMap(Map<String, Right> commonAndSpecialRightMap)
  {
    this.commonAndSpecialRightMap = commonAndSpecialRightMap;
  }
  
  public int handleRequest(String key, User user)
  {
    int result = -1;
    Map<String, Right> specialRightMap = RightMemory.getSPECIALRIGHTMAP();
    specialRightMap.putAll(this.commonAndSpecialRightMap);
    Right right = (Right)specialRightMap.get(key);
    if (right != null) {
      result = -98;
    } else {
      result = toNextHandle(user, key, this.secondSecurityHandler);
    }
    return result;
  }
}
