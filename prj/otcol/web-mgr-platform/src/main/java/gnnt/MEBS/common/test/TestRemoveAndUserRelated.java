package gnnt.MEBS.common.test;

import gnnt.MEBS.common.services.RoleService;
import gnnt.MEBS.common.util.SysDataTest;

public class TestRemoveAndUserRelated
{
  public static void main(String[] paramArrayOfString)
  {
    RoleService localRoleService = (RoleService)SysDataTest.getBean("roleService");
    String[] arrayOfString = { "lw" };
    localRoleService.removeRoleAndUserRelated(1L, arrayOfString);
  }
}
