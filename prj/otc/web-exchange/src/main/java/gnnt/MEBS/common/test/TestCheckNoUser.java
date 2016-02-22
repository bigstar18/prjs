package gnnt.MEBS.common.test;

import gnnt.MEBS.common.dao.OutSideDao;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.util.SysDataTest;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;

public class TestCheckNoUser
{
  public static void main(String[] args)
  {
    OutSideDao outSideDao = (OutSideDao)SysDataTest.getBean("outSideDao");
    String sql = "select distinct user from User user,Role role where user not in elements(role.userSet) and role.id=1";
    
    List<User> list = outSideDao.getList(sql);
    System.out.println(list.size());
    for (User user : list)
    {
      System.out.println(user.getUserId());
      Set localSet = user.getRoleSet();
    }
  }
}
