package gnnt.MEBS.common.test;

import gnnt.MEBS.common.service.RoleService;
import gnnt.MEBS.common.util.SysDataTest;

public class TestRemoveAndUserRelated
{
  public static void main(String[] args)
  {
    RoleService roleService = (RoleService)SysDataTest.getBean("roleService");
    String[] userIds = { "lw" };
    roleService.removeRoleAndUserRelated(1L, userIds);
  }
}
