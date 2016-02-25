package gnnt.MEBS.timebargain.manage.util;

public class SqlUtil
{
  public static String makeFilterCondition(String paramString1, String paramString2, boolean paramBoolean)
  {
    if ((paramString2 == null) || (paramString2.trim().length() == 0)) {
      return "";
    }
    if (paramString1.indexOf(' ') >= 0) {
      throw new RuntimeException("id error:[" + paramString1 + "]");
    }
    for (int i = 0; i < paramString2.length(); i++)
    {
      char c = paramString2.charAt(i);
      if ((!Character.isLetterOrDigit(c)) && (c != '-') && (c != ',')) {
        throw new RuntimeException("condition error:[" + paramString2 + "]");
      }
    }
    String[] arrayOfString1 = paramString2.split(",");
    String str1 = null;
    Object localObject1 = null;
    Object localObject2 = null;
    String[] arrayOfString2 = null;
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer();
    for (int j = 0; j < arrayOfString1.length; j++)
    {
      str1 = arrayOfString1[j].trim();
      if (str1.length() != 0)
      {
        arrayOfString2 = arrayOfString1[j].split("-");
        if ((arrayOfString2.length == 1) && (arrayOfString2[0].trim().length() != 0))
        {
          if (localStringBuffer2.length() != 0) {
            localStringBuffer2.append(",");
          }
          localStringBuffer2.append("'").append(arrayOfString2[0].trim()).append("'");
        }
        else if (arrayOfString2.length == 2)
        {
          if (localStringBuffer1.length() != 0) {
            localStringBuffer1.append(" or ");
          }
          localStringBuffer1.append("(").append(paramString1).append(">='").append(arrayOfString2[0].trim()).append("' and ").append(paramString1).append("<='").append(arrayOfString2[1].trim()).append("')");
        }
      }
    }
    String str2 = null;
    if (localStringBuffer1.length() > 0)
    {
      str2 = localStringBuffer1.toString();
      if (localStringBuffer2.length() > 0) {
        str2 = str2 + " or " + paramString1 + " in (" + localStringBuffer2.toString() + ")";
      }
    }
    else if (localStringBuffer2.length() > 0)
    {
      str2 = paramString1 + " in (" + localStringBuffer2.toString() + ")";
    }
    if (str2 == null) {
      return "";
    }
    if (paramBoolean) {
      str2 = str2.replaceAll("'", "");
    }
    return " (" + str2 + ") ";
  }
}
