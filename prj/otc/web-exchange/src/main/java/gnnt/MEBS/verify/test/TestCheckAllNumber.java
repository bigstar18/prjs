package gnnt.MEBS.verify.test;

import java.io.PrintStream;
import java.util.regex.Pattern;

public class TestCheckAllNumber
{
  public static void main(String[] args)
  {
    String a = "011";
    boolean b = Pattern.matches("(\\-|\\+)?[1-9]{1}\\d*", a);
    System.out.println(b);
  }
}
