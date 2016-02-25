package gnnt.MEBS.zcjs.util;

import gnnt.MEBS.common.model.OnLineUser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnLine
{
  public static Map<Integer, List<OnLineUser>> transforArrayToMap(String[] paramArrayOfString)
  {
    HashMap localHashMap = new HashMap();
    if (paramArrayOfString.length > 0)
    {
      ArrayList localArrayList = new ArrayList();
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        String[] arrayOfString = paramArrayOfString[i].split(",");
        if (arrayOfString.length == 3)
        {
          OnLineUser localOnLineUser = new OnLineUser();
          localOnLineUser.setUserId(arrayOfString[0]);
          localOnLineUser.setLogonTime(arrayOfString[1]);
          localOnLineUser.setLogonIp(arrayOfString[2]);
          localArrayList.add(localOnLineUser);
        }
      }
      localHashMap.put(Integer.valueOf(localArrayList.size()), localArrayList);
    }
    return localHashMap;
  }
}
