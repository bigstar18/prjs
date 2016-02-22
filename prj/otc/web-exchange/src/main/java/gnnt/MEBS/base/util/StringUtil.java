package gnnt.MEBS.base.util;

import java.io.PrintStream;

public class StringUtil
{
  public static void main(String[] args)
  {
    String t = "aaaaaaaaaa<table></table>aaa<table></table>";
    String s = "<TABLE";
    


    System.out.println("length=" + t.length());
    
    System.out.println(t.indexOf(s, 0));
    System.out.println(ignoreIndexOf(t, s, 0));
    
    System.out.println(t.lastIndexOf(s));
    System.out.println(ignoreLastIndexOf(t, s));
  }
  
  public static int ignoreIndexOf(String subject, String search)
  {
    return ignoreIndexOf(subject, search, 0);
  }
  
  public static int ignoreIndexOf(String subject, String search, int soffset)
  {
    if ((subject == null) || (search == null)) {
      throw new NullPointerException("输入的参数为空");
    }
    if ((soffset >= subject.length()) && (search.equals(""))) {
      return subject.length();
    }
    for (int i = soffset; i < subject.length(); i++) {
      if (subject.regionMatches(true, i, search, 0, search.length())) {
        return i;
      }
    }
    return -1;
  }
  
  public static int ignoreLastIndexOf(String subject, String search)
  {
    return ignoreLastIndexOf(subject, search, subject.length());
  }
  
  public static int ignoreLastIndexOf(String subject, String search, int soffset)
  {
    if ((subject == null) || (search == null)) {
      throw new NullPointerException("输入的参数为空");
    }
    if ((soffset <= 0) && (search.equals(""))) {
      return 0;
    }
    for (int i = soffset; i > 0; i--) {
      if (subject.regionMatches(true, i, search, 0, search.length())) {
        return i;
      }
    }
    return -1;
  }
}
