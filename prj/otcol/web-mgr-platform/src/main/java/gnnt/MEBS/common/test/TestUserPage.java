package gnnt.MEBS.common.test;

import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.services.UserService;
import gnnt.MEBS.common.util.SysDataTest;
import gnnt.MEBS.common.util.query.PageInfo;
import gnnt.MEBS.common.util.query.QueryConditions;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TestUserPage
{
  public static void main(String[] paramArrayOfString)
  {
    UserService localUserService = (UserService)SysDataTest.getBean("userService");
    PageInfo localPageInfo = new PageInfo(1, 5, "u.userId", true);
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("u.userId", "=", "lw");
    List localList = localUserService.getUserList(localQueryConditions, localPageInfo, false, true, false);
    System.out.println(localList.size());
    System.out.println(localPageInfo.getTotalRecords());
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      User localUser = (User)localIterator.next();
      System.out.println(localUser.getUserId());
      Set localSet = localUser.getRoleSet();
    }
  }
}
