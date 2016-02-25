package gnnt.MEBS.timebargain.manage.util;

import java.io.PrintStream;

public class FormatPunctuation
{
  public static String EN2CN(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    for (int i = 0; i < arrayOfChar.length; i++) {
      if (((arrayOfChar[i] > '!') && (arrayOfChar[i] < '/')) || ((arrayOfChar[i] > ':') && (arrayOfChar[i] < '@')) || ((arrayOfChar[i] > '[') && (arrayOfChar[i] < '`')) || ((arrayOfChar[i] > '{') && (arrayOfChar[i] < ''))) {
        arrayOfChar[i] = ((char)(arrayOfChar[i] + 65248));
      }
    }
    return new String(arrayOfChar);
  }
  
  public static String CN2EN(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    for (int i = 0; i < arrayOfChar.length; i++) {
      if (((arrayOfChar[i] > 65281) && (arrayOfChar[i] < 65295)) || ((arrayOfChar[i] > 65306) && (arrayOfChar[i] < 65312)) || ((arrayOfChar[i] > 65339) && (arrayOfChar[i] < 65344)) || ((arrayOfChar[i] > 65371) && (arrayOfChar[i] < 65375))) {
        arrayOfChar[i] = ((char)(arrayOfChar[i] - 65248));
      }
    }
    return new String(arrayOfChar);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    String str1 = ",.!@#$%^&*()_+-=[]{};:'|<>,.?/";
    System.out.println(str1);
    String str2 = EN2CN(str1);
    System.out.println(str2);
    String str3 = CN2EN(str2);
    if (str1.equals(str3)) {
      System.out.println(str3);
    }
  }
}
