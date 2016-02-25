package gnnt.MEBS.common.security.util;

import gnnt.MEBS.common.model.Menu;
import gnnt.MEBS.common.model.OnLineUser;
import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.User;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IntegrateToMap
{
  public static Map<Long, Menu> integrateToMap(Menu paramMenu, Map<Long, Right> paramMap)
  {
    return compareMap(iteratorMenuSet(paramMenu), paramMap);
  }
  
  public static Map<Long, Menu> compareMap(Map<Long, Menu> paramMap, Map<Long, Right> paramMap1)
  {
    HashMap localHashMap = new HashMap();
    Set localSet = paramMap.keySet();
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
    {
      Long localLong = (Long)localIterator.next();
      if (paramMap1.containsKey(localLong)) {
        localHashMap.put(localLong, paramMap.get(localLong));
      }
    }
    return localHashMap;
  }
  
  public static Map<Long, Menu> iteratorMenuSet(Menu paramMenu)
  {
    HashMap localHashMap = new HashMap();
    Set localSet1 = paramMenu.getMenuSet();
    Iterator localIterator1 = localSet1.iterator();
    while (localIterator1.hasNext())
    {
      Menu localMenu1 = (Menu)localIterator1.next();
      localHashMap.put(localMenu1.getId(), localMenu1);
      Set localSet2 = localMenu1.getMenuSet();
      Iterator localIterator2 = localSet2.iterator();
      while (localIterator2.hasNext())
      {
        Menu localMenu2 = (Menu)localIterator2.next();
        localHashMap.put(localMenu2.getId(), localMenu2);
        Set localSet3 = localMenu2.getMenuSet();
        Iterator localIterator3 = localSet3.iterator();
        while (localIterator3.hasNext())
        {
          Menu localMenu3 = (Menu)localIterator3.next();
          localHashMap.put(localMenu3.getId(), localMenu3);
        }
      }
    }
    return localHashMap;
  }
  
  public static Map<User, Integer> roleAssociateUsers(Set<User> paramSet, List<User> paramList)
  {
    HashMap localHashMap = new HashMap();
    for (int i = 0; i < paramList.size(); i++) {
      if (paramSet.contains(paramList.get(i))) {
        localHashMap.put(paramList.get(i), Integer.valueOf(1));
      } else {
        localHashMap.put(paramList.get(i), Integer.valueOf(0));
      }
    }
    return localHashMap;
  }
  
  public static Map<Integer, List<OnLineUser>> transforArrayToMap(String[] paramArrayOfString)
  {
    HashMap localHashMap = new HashMap();
    if (paramArrayOfString.length > 0)
    {
      ArrayList localArrayList = new ArrayList();
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        String[] arrayOfString = paramArrayOfString[i].split(",");
        System.out.println("allUserMessages[i]===" + paramArrayOfString[i]);
        if (arrayOfString.length > 3)
        {
          OnLineUser localOnLineUser = new OnLineUser();
          localOnLineUser.setUserId(arrayOfString[0]);
          localOnLineUser.setLogonTime(arrayOfString[1]);
          localOnLineUser.setLogonIp(arrayOfString[2]);
          localOnLineUser.setSessionId(Long.parseLong(arrayOfString[3]));
          localArrayList.add(localOnLineUser);
        }
      }
      localHashMap.put(Integer.valueOf(localArrayList.size()), localArrayList);
    }
    return localHashMap;
  }
  
  public static Map<Right, Integer> sortRight(Right paramRight, Set<Right> paramSet1, Set<Right> paramSet2)
  {
    HashMap localHashMap = new HashMap();
    Map localMap = iteratorRightSet(paramRight);
    return putObject(putObject(localHashMap, localMap, paramSet1, 1), localMap, paramSet2, 2);
  }
  
  public static Map<Right, Integer> putObject(Map<Right, Integer> paramMap, Map<Long, Right> paramMap1, Set<Right> paramSet, int paramInt)
  {
    if (paramSet != null)
    {
      Iterator localIterator = paramSet.iterator();
      while (localIterator.hasNext())
      {
        Right localRight = (Right)localIterator.next();
        if (paramMap1.containsKey(localRight.getId())) {
          paramMap.put(paramMap1.get(localRight.getId()), Integer.valueOf(paramInt));
        } else {
          paramMap.put(paramMap1.get(localRight.getId()), Integer.valueOf(0));
        }
      }
    }
    return paramMap;
  }
  
  public static Map<Long, Right> iteratorRightSet(Right paramRight)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put(paramRight.getId(), paramRight);
    Set localSet1 = paramRight.getRightSet();
    Iterator localIterator1 = localSet1.iterator();
    while (localIterator1.hasNext())
    {
      Right localRight1 = (Right)localIterator1.next();
      if (localRight1.getVisible().intValue() == 0) {
        localHashMap.put(localRight1.getId(), localRight1);
      }
      Set localSet2 = localRight1.getRightSet();
      Iterator localIterator2 = localSet2.iterator();
      while (localIterator2.hasNext())
      {
        Right localRight2 = (Right)localIterator2.next();
        if (localRight2.getVisible().intValue() == 0) {
          localHashMap.put(localRight2.getId(), localRight2);
        }
        Set localSet3 = localRight2.getRightSet();
        Iterator localIterator3 = localSet3.iterator();
        while (localIterator3.hasNext())
        {
          Right localRight3 = (Right)localIterator3.next();
          if (localRight3.getVisible().intValue() == 0) {
            localHashMap.put(localRight3.getId(), localRight3);
          }
        }
      }
    }
    return localHashMap;
  }
}
