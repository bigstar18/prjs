package gnnt.MEBS.common.security.util;

import gnnt.MEBS.common.model.Menu;
import gnnt.MEBS.common.model.OnLineUser;
import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IntegrateToMap
{
  public static Map<Long, Menu> integrateToMap(Menu allMenu, Map<Long, Right> rightMap)
  {
    return compareMap(iteratorMenuSet(allMenu), rightMap);
  }
  
  public static Map<Long, Menu> compareMap(Map<Long, Menu> menuMap, Map<Long, Right> rightMap)
  {
    Map<Long, Menu> compareMap = new HashMap();
    
    Set<Long> keySet = menuMap.keySet();
    for (Iterator<Long> it = keySet.iterator(); it.hasNext();)
    {
      Long longObj = (Long)it.next();
      if (rightMap.containsKey(longObj)) {
        compareMap.put(longObj, (Menu)menuMap.get(longObj));
      }
    }
    return compareMap;
  }
  
  public static Map<Long, Menu> iteratorMenuSet(Menu allMenu)
  {
    Map<Long, Menu> iteratorSet = new HashMap();
    Set<Menu> menuSet = allMenu.getMenuSet();
    Iterator<Menu> innerIt;
    for (Iterator<Menu> it = menuSet.iterator(); it.hasNext(); innerIt.hasNext())
    {
      Menu childMenu = (Menu)it.next();
      iteratorSet.put(childMenu.getId(), childMenu);
      
      Set<Menu> innerMenuSet = childMenu.getMenuSet();
      innerIt = innerMenuSet.iterator(); continue;
      
      Menu innerChildMenu = (Menu)innerIt.next();
      iteratorSet.put(innerChildMenu.getId(), innerChildMenu);
      
      Set<Menu> insideMenuSet = innerChildMenu.getMenuSet();
      for (Iterator<Menu> insideIt = insideMenuSet.iterator(); insideIt.hasNext();)
      {
        Menu insideChildMenu = (Menu)insideIt.next();
        iteratorSet.put(insideChildMenu.getId(), insideChildMenu);
      }
    }
    return iteratorSet;
  }
  
  public static Map<User, Integer> roleAssociateUsers(Set<User> roleUsersSet, List<User> usersList)
  {
    Map<User, Integer> ruMap = new HashMap();
    for (int i = 0; i < usersList.size(); i++) {
      if (roleUsersSet.contains(usersList.get(i))) {
        ruMap.put((User)usersList.get(i), Integer.valueOf(1));
      } else {
        ruMap.put((User)usersList.get(i), Integer.valueOf(0));
      }
    }
    return ruMap;
  }
  
  public static Map<Integer, List<OnLineUser>> transforArrayToMap(String[] allUserMessages)
  {
    Map<Integer, List<OnLineUser>> onLineUsersMap = new HashMap();
    if (allUserMessages.length > 0)
    {
      List<OnLineUser> onLineUsersList = new ArrayList();
      for (int i = 0; i < allUserMessages.length; i++)
      {
        String[] perUserMessages = allUserMessages[i].split(",");
        if (perUserMessages.length > 3)
        {
          OnLineUser onLineUser = new OnLineUser();
          onLineUser.setUserId(perUserMessages[0]);
          onLineUser.setLogonTime(perUserMessages[1]);
          onLineUser.setLogonIp(perUserMessages[2]);
          onLineUser.setSessionId(Long.parseLong(perUserMessages[3]));
          
          onLineUsersList.add(onLineUser);
        }
      }
      onLineUsersMap.put(Integer.valueOf(onLineUsersList.size()), onLineUsersList);
    }
    return onLineUsersMap;
  }
  
  public static Map<Right, Integer> sortRight(Right sysRight, Set<Right> rightSet, Set<Right> roleRightSet)
  {
    Map<Right, Integer> resultMap = new HashMap();
    Map<Long, Right> newMap = iteratorRightSet(sysRight);
    
    return putObject(putObject(resultMap, newMap, rightSet, 1), newMap, roleRightSet, 2);
  }
  
  public static Map<Right, Integer> putObject(Map<Right, Integer> resultMap, Map<Long, Right> newMap, Set<Right> rightSet, int belongMark)
  {
    if (rightSet != null) {
      for (Iterator<Right> rightIterator = rightSet.iterator(); rightIterator.hasNext();)
      {
        Right userRight = (Right)rightIterator.next();
        if (newMap.containsKey(userRight.getId())) {
          resultMap.put((Right)newMap.get(userRight.getId()), Integer.valueOf(belongMark));
        } else {
          resultMap.put((Right)newMap.get(userRight.getId()), Integer.valueOf(0));
        }
      }
    }
    return resultMap;
  }
  
  public static Map<Long, Right> iteratorRightSet(Right sysRight)
  {
    Map<Long, Right> newMap = new HashMap();
    newMap.put(sysRight.getId(), sysRight);
    
    Set<Right> firstFloorSet = sysRight.getRightSet();
    Iterator<Right> secondIt;
    for (Iterator<Right> firstIt = firstFloorSet.iterator(); firstIt.hasNext(); secondIt.hasNext())
    {
      Right firstFloorRight = (Right)firstIt.next();
      if (firstFloorRight.getVisible().intValue() == 0) {
        newMap.put(firstFloorRight.getId(), firstFloorRight);
      }
      Set<Right> secondFloorSet = firstFloorRight.getRightSet();
      secondIt = secondFloorSet.iterator(); continue;
      
      Right secondFloorRight = (Right)secondIt.next();
      if (secondFloorRight.getVisible().intValue() == 0) {
        newMap.put(secondFloorRight.getId(), secondFloorRight);
      }
      Set<Right> thirdFloorSet = secondFloorRight.getRightSet();
      for (Iterator<Right> thirdIt = thirdFloorSet.iterator(); thirdIt.hasNext();)
      {
        Right thirdFloorRight = (Right)thirdIt.next();
        if (thirdFloorRight.getVisible().intValue() == 0) {
          newMap.put(thirdFloorRight.getId(), thirdFloorRight);
        }
      }
    }
    return newMap;
  }
}
