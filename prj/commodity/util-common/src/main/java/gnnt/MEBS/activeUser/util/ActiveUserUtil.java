package gnnt.MEBS.activeUser.util;

import java.util.Random;

public class ActiveUserUtil
{
  private static Random random = new Random();

  public static long createSessionID()
  {
    long t1 = 0x7FFFFFFF & System.currentTimeMillis();
    return t1 << 32 | Math.abs(random.nextInt());
  }
}