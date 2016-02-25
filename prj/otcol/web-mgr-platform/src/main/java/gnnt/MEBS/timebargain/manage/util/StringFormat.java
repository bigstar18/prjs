package gnnt.MEBS.timebargain.manage.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringFormat
{
  private static SimpleDateFormat dateFormat = new SimpleDateFormat();
  private static DecimalFormat numberFormat = new DecimalFormat();
  
  public static boolean isValidDate(String paramString1, String paramString2)
  {
    Date localDate = null;
    synchronized (dateFormat)
    {
      try
      {
        dateFormat.applyPattern(paramString2);
        dateFormat.setLenient(false);
        localDate = dateFormat.parse(paramString1);
      }
      catch (ParseException localParseException) {}
    }
    return localDate != null;
  }
  
  public static boolean isValidInteger(String paramString, int paramInt1, int paramInt2)
  {
    Integer localInteger = null;
    try
    {
      Number localNumber = numberFormat.parse(paramString);
      int i = localNumber.intValue();
      if ((i >= paramInt1) && (i <= paramInt2)) {
        localInteger = new Integer(i);
      }
    }
    catch (ParseException localParseException) {}
    return localInteger != null;
  }
  
  public static boolean isValidEmailAddr(String paramString)
  {
    boolean bool = false;
    if ((paramString != null) && (paramString.indexOf("@") != -1) && (paramString.indexOf(".") != -1)) {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isValidString(String paramString, String[] paramArrayOfString, boolean paramBoolean)
  {
    boolean bool = false;
    for (int i = 0; (paramArrayOfString != null) && (i < paramArrayOfString.length); i++) {
      if (paramBoolean)
      {
        if (paramArrayOfString[i].equalsIgnoreCase(paramString))
        {
          bool = true;
          break;
        }
      }
      else if (paramArrayOfString[i].equals(paramString))
      {
        bool = true;
        break;
      }
    }
    return bool;
  }
  
  public static String toHTMLString(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; (paramString != null) && (i < paramString.length()); i++)
    {
      char c = paramString.charAt(i);
      if (c == '\'') {
        localStringBuffer.append("&#39;");
      } else if (c == '"') {
        localStringBuffer.append("&#34;");
      } else if (c == '<') {
        localStringBuffer.append("&lt;");
      } else if (c == '>') {
        localStringBuffer.append("&gt;");
      } else if (c == '&') {
        localStringBuffer.append("&amp;");
      } else {
        localStringBuffer.append(c);
      }
    }
    return localStringBuffer.toString();
  }
  
  public static Date toDate(String paramString1, String paramString2)
    throws ParseException
  {
    Date localDate = null;
    if (paramString2 == null) {
      paramString2 = "yyyy-MM-dd";
    }
    synchronized (dateFormat)
    {
      dateFormat.applyPattern(paramString2);
      dateFormat.setLenient(false);
      localDate = dateFormat.parse(paramString1);
    }
    return localDate;
  }
  
  public static String getDate(String paramString)
  {
    return paramString.substring(0, paramString.indexOf(" "));
  }
  
  public static Number toNumber(String paramString1, String paramString2)
    throws ParseException
  {
    Number localNumber = null;
    if (paramString2 == null) {
      paramString2 = "######.##";
    }
    synchronized (numberFormat)
    {
      numberFormat.applyPattern(paramString2);
      localNumber = numberFormat.parse(paramString1);
    }
    return localNumber;
  }
  
  public static String replaceInString(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 == null) || (paramString2 == null) || (paramString3 == null)) {
      return paramString1;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    char[] arrayOfChar1 = paramString1.toCharArray();
    int i = arrayOfChar1.length;
    char[] arrayOfChar2 = paramString2.toCharArray();
    int j = arrayOfChar2.length;
    for (int k = 0; k < i; k++) {
      if ((arrayOfChar1[k] == arrayOfChar2[0]) && (k + j <= i))
      {
        int m = 1;
        for (int n = 1; n < j; n++) {
          if (arrayOfChar1[(k + n)] != arrayOfChar2[n])
          {
            m = 0;
            break;
          }
        }
        if (m != 0)
        {
          localStringBuffer.append(paramString3);
          k += j - 1;
        }
        else
        {
          localStringBuffer.append(arrayOfChar1[k]);
        }
      }
      else
      {
        localStringBuffer.append(arrayOfChar1[k]);
      }
    }
    return localStringBuffer.toString();
  }
  
  public static String LTrim(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return "";
    }
    if (paramString.charAt(0) != ' ') {
      return paramString;
    }
    for (int i = 0; (i < paramString.length()) && (paramString.charAt(i) == ' '); i++) {}
    return paramString.substring(i);
  }
  
  public static String RTrim(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return "";
    }
    if (paramString.charAt(paramString.length() - 1) != ' ') {
      return paramString;
    }
    for (int i = paramString.length() - 1; (i > 0) && (paramString.charAt(i) == ' '); i--) {}
    return paramString.substring(0, i + 1);
  }
  
  public static String Trim(String paramString)
  {
    return LTrim(RTrim(paramString));
  }
  
  public static String repeatString(String paramString, int paramInt)
  {
    paramString = getNullString(paramString);
    if (paramInt < 0) {
      return "";
    }
    StringBuffer localStringBuffer = new StringBuffer(paramString.length() * paramInt);
    for (int i = 0; i < paramInt; i++) {
      localStringBuffer.append(paramString);
    }
    return localStringBuffer.toString();
  }
  
  public static String getAlertString(String paramString)
  {
    paramString = getNullString(paramString);
    return paramString.replace('\n', ' ').replace('\r', ' ').replace('"', '\'');
  }
  
  public static String filterEnterString(String paramString)
  {
    paramString = getNullString(paramString);
    return paramString.replace('\n', ' ').replace('\r', ' ');
  }
  
  public static String getNullString(String paramString)
  {
    return paramString == null ? "" : paramString;
  }
  
  public static final String encodeHex(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      String str = Integer.toHexString(paramArrayOfByte[i]);
      localStringBuffer.append(str.length() > 2 ? str.substring(6, 8) : str);
      localStringBuffer.append(" ");
    }
    return localStringBuffer.toString();
  }
  
  public static final String arrayToString(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramArrayOfByte.length; i++) {
      localStringBuffer.append(paramArrayOfByte[i] + " ");
    }
    return localStringBuffer.toString();
  }
}
