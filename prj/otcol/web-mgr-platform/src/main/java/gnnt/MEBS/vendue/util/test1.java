package gnnt.MEBS.vendue.util;

import java.util.Comparator;

public class test1
  implements Comparator<String>
{
  public int compare(String paramString1, String paramString2)
  {
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    arrayOfString1 = paramString1.split(",");
    arrayOfString2 = paramString2.split(",");
    return arrayOfString2[0].trim().compareTo(arrayOfString1[0].trim());
  }
}
