package gnnt.MEBS.verify.test;

import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestAll
{
  public static void main(String[] s)
  {
    String reDate = "^[a-zA-Z0-9一-龥]+$";
    Pattern com = Pattern.compile(reDate);
    Matcher mat = com.matcher("爱情aaa_1111");
    if (mat.find()) {
      System.out.println("日期格式正确");
    }
    System.out.println("日期格式错误");
  }
}
