package gnnt.MEBS.common.test;

import gnnt.MEBS.common.dao.OutSideDao;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.util.SysDataTest;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TestCheckNoUser
{
  public static void main(String[] paramArrayOfString)
  {
    OutSideDao localOutSideDao = (OutSideDao)SysDataTest.getBean("outSideDao");
    String str = "select distinct user from User user,Role role where user not in elements(role.userSet) and role.id=1";
    List localList = localOutSideDao.getList(str);
    System.out.println(localList.size());
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      User localUser = (User)localIterator.next();
      System.out.println(localUser.getUserId());
      Set localSet = localUser.getRoleSet();
    }
  }
}
