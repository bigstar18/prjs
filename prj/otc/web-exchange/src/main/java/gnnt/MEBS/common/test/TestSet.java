package gnnt.MEBS.common.test;

import gnnt.MEBS.common.model.User;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class TestSet
{
  public static void main(String[] args)
  {
    User u = new User();
    u.setUserId("1");
    u.setName("2");
    User u1 = new User();
    u1.setUserId("1");
    u1.setName("2");
    Set<User> set = new HashSet();
    set.add(u);
    
    System.out.println(set.contains(u1));
  }
}
