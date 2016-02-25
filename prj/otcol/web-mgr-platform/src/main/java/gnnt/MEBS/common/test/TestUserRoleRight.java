package gnnt.MEBS.common.test;

import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.services.UserService;
import gnnt.MEBS.common.util.SysDataTest;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TestUserRoleRight
{
  public static void main(String[] paramArrayOfString)
  {
    UserService localUserService = (UserService)SysDataTest.getBean("userService");
    User localUser = localUserService.loadUserById("lw", true, true, true);
    System.out.println(localUser.getName());
    Set localSet = localUser.getRightSet();
    Object localObject1 = localSet.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Right)((Iterator)localObject1).next();
      System.out.println(((Right)localObject2).getName());
    }
    localObject1 = localUser.getRoleSet();
    Object localObject2 = ((Set)localObject1).iterator();
    Right localRight;
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (Role)((Iterator)localObject2).next();
      localObject4 = ((Role)localObject3).getRightSet();
      localObject5 = ((Set)localObject4).iterator();
      while (((Iterator)localObject5).hasNext())
      {
        localRight = (Right)((Iterator)localObject5).next();
        System.out.println(localRight.getName());
      }
    }
    System.out.println("-------------");
    localObject2 = localUser.getRightMap();
    Object localObject3 = ((Map)localObject2).entrySet();
    Object localObject4 = ((Set)localObject3).iterator();
    while (((Iterator)localObject4).hasNext())
    {
      localObject5 = (Map.Entry)((Iterator)localObject4).next();
      System.out.println((String)((Map.Entry)localObject5).getKey());
      localRight = (Right)((Map.Entry)localObject5).getValue();
      System.out.println(localRight.getName());
    }
    System.out.println("-------");
    Object localObject5 = localUserService.getUserById("lw");
    System.out.println(((User)localObject5).getName());
  }
}
