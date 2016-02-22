package gnnt.MEBS.common.test;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.service.UserService;
import gnnt.MEBS.common.util.SysDataTest;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;

public class TestUserPage
{
  public static void main(String[] args)
  {
    UserService UserService = (UserService)SysDataTest.getBean("userService");
    PageInfo pageInfo = new PageInfo(1, 5, "u.userId", true);
    QueryConditions condition = new QueryConditions();
    condition.addCondition("u.userId", "=", "lw");
    List<User> list = UserService.getUserList(condition, pageInfo, false, true, false);
    System.out.println(list.size());
    System.out.println(pageInfo.getTotalRecords());
    for (User user : list)
    {
      System.out.println(user.getUserId());
      Set localSet = user.getRoleSet();
    }
  }
}
