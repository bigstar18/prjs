package gnnt.MEBS.delivery.command.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class annotateParseTools
{
  private static final String className = "gnnt.MEBS.delivery.command.util.CommandAnnotate";
  
  public static Map getAnnotateParse(String paramString)
  {
    HashMap localHashMap = new HashMap();
    try
    {
      Class localClass = Class.forName("gnnt.MEBS.delivery.command.util.CommandAnnotate");
      Field localField = localClass.getDeclaredField(paramString.toUpperCase());
      if (localField != null)
      {
        annotateParse localannotateParse = (annotateParse)localField.getAnnotation(annotateParse.class);
        if (localannotateParse != null)
        {
          localHashMap.put("COMMANDBEAN", localannotateParse.commandBean());
          localHashMap.put("RECEIVEBEAN", localannotateParse.receiveBean());
        }
      }
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      localNoSuchFieldException.printStackTrace();
    }
    return localHashMap;
  }
  
  public static String getAnnotateParseContext(String paramString1, String paramString2)
  {
    String str = null;
    Map localMap = getAnnotateParse(paramString1);
    str = (String)localMap.get(paramString2.toUpperCase());
    return str;
  }
}
