package gnnt.MEBS.common.security.memoryModel;

import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.service.RightService;
import java.util.Map;

public class RightMemory
{
  private static Map<String, Right> NOWILDCARDRIGHTMAP;
  private static Map<String, Right> WILDCARDRIGHTMAP;
  private static Map<String, Right> SPECIALRIGHTMAP;
  private static Map<Long, Right> ALLRIGHTMAP;
  
  public static Map<Long, Right> getALLRIGHTMAP()
  {
    return ALLRIGHTMAP;
  }
  
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
    RightService rightService = (RightService)SpringContextHelper.getBean("rightService");
    NOWILDCARDRIGHTMAP = rightService.loadWildcardRight(false);
    WILDCARDRIGHTMAP = rightService.loadWildcardRight(true);
    SPECIALRIGHTMAP = rightService.loadSpecialRight();
    ALLRIGHTMAP = rightService.loadAllRight();
  }
}
