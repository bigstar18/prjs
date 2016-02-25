package gnnt.MEBS.base.util;

import java.io.PrintStream;

public class Purview
{
  public static boolean check(int paramInt1, int paramInt2)
  {
    int i = 1 << paramInt2;
    return (paramInt1 & i) == i;
  }
  
  public static boolean check(int paramInt, int... paramVarArgs)
  {
    if (paramVarArgs.length <= 0) {
      return false;
    }
    int i = 0;
    for (int m : paramVarArgs) {
      i += (1 << m);
    }
    return (paramInt & i) == i;
  }
  
  public static int add(int paramInt1, int paramInt2)
  {
    return paramInt1 + (1 << paramInt2);
  }
  
  public static int subtract(int paramInt1, int paramInt2)
  {
    return paramInt1 - (1 << paramInt2);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    int i = 28;
    int j = 3;
    boolean bool = check(i, j);
    System.out.println(bool);
    int k = subtract(i, j);
    bool = check(k, j);
    System.out.println(bool);
    int m = add(i, 5);
    bool = check(m, 5);
    System.out.println(bool);
    bool = check(28, new int[] { 2, 3, 4 });
    System.out.println(bool);
  }
}
