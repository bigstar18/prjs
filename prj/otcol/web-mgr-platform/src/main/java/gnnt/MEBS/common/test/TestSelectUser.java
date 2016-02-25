package gnnt.MEBS.common.test;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.services.UserService;
import gnnt.MEBS.common.util.SysDataTest;
import java.io.PrintStream;

public class TestSelectUser
{
  public static void main(String[] paramArrayOfString)
  {
    UserService localUserService = (UserService)SysDataTest.getBean("userService");
    User localUser = localUserService.getUserById("lw");
    System.out.println(localUser.getPassword());
  }
}
