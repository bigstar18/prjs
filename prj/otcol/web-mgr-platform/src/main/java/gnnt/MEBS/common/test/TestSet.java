package gnnt.MEBS.common.test;

import gnnt.MEBS.common.model.User;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class TestSet
{
  public static void main(String[] paramArrayOfString)
  {
    User localUser1 = new User();
    localUser1.setUserId("1");
    localUser1.setName("2");
    User localUser2 = new User();
    localUser2.setUserId("1");
    localUser2.setName("2");
    HashSet localHashSet = new HashSet();
    localHashSet.add(localUser1);
    System.out.println(localHashSet.contains(localUser2));
  }
}
