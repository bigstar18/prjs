package gnnt.MEBS.verify.test;

import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestDecimals
{
  public static void main(String[] args)
  {
    String inStr = "1";
    
    Pattern p = Pattern.compile("\\d{1,}\\.\\d{1,}");
    Matcher m = p.matcher(inStr);
    boolean b = m.matches();
    if (b)
    {
      int bitPos = inStr.indexOf(".");
      

      int numOfBits = inStr.length() - bitPos - 1;
      System.out.println("小数位数为： " + numOfBits);
    }
    else
    {
      System.out.println("输入的不是小数");
    }
  }
}
