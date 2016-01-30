package gnnt.MEBS.common.broker.webframe.securitycheck;

import java.io.PrintStream;

public enum UrlCheckResult
{
  SUCCESS(0), NEEDLESSCHECK(0), USERISNULL(-1), AUOVERTIME(-2), AUUSERKICK(-3), NOPURVIEW(-4), NEEDLESSCHECKRIGHT(-5);

  private final int value;

  public int getValue()
  {
    return this.value;
  }

  private UrlCheckResult(int paramInt)
  {
    this.value = paramInt;
  }

  public static void main(String[] paramArrayOfString)
  {
    System.out.println(NOPURVIEW);
  }
}