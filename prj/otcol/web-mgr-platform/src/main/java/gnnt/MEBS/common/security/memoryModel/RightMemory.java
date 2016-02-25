package gnnt.MEBS.common.security.memoryModel;

import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.services.RightService;
import gnnt.MEBS.common.util.SysData;
import java.util.Map;

public class RightMemory
{
  private static Map<String, Right> NOWILDCARDRIGHTMAP;
  private static Map<String, Right> WILDCARDRIGHTMAP;
  private static Map<String, Right> SPECIALRIGHTMAP;
  
  public static Map<String, Right> getNOWILDCARDRIGHTMAP()
  {
    return NOWILDCARDRIGHTMAP;
  }
  
  public static Map<String, Right> getWILDCARDRIGHTMAP()
  {
    return WILDCARDRIGHTMAP;
  }
  
  public static Map<String, Right> getSPECIALRIGHTMAP()
  {
    return SPECIALRIGHTMAP;
  }
  
  public static void init()
  {
    RightService localRightService = (RightService)SysData.getBean("rightService");
    NOWILDCARDRIGHTMAP = localRightService.loadWildcardRight(false);
    WILDCARDRIGHTMAP = localRightService.loadWildcardRight(true);
    SPECIALRIGHTMAP = localRightService.loadSpecialRight();
  }
}
