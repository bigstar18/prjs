package gnnt.MEBS.delivery.util;

import java.io.UnsupportedEncodingException;

public class CharTools
{
  public static String changeStringToHex(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString != null) {
      for (int i = 0; i < paramString.length(); i++)
      {
        byte[] arrayOfByte = paramString.substring(i, i + 1).getBytes();
        localStringBuffer.append(Integer.toHexString(arrayOfByte[0] & 0xFF));
        if (arrayOfByte.length == 2) {
          localStringBuffer.append(Integer.toHexString(arrayOfByte[1] & 0xFF));
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  public static String changeHexToString(String paramString)
  {
    byte[] arrayOfByte = new byte[paramString.length() / 2];
    for (int i = 0; i < arrayOfByte.length; i++) {
      try
      {
        arrayOfByte[i] = ((byte)(0xFF & Integer.parseInt(paramString.substring(i * 2, i * 2 + 2), 16)));
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
    try
    {
      paramString = new String(arrayOfByte, "gb2312");
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    return paramString;
  }
  
  public static String iso2gb(String paramString)
  {
    String str = "";
    try
    {
      str = new String(paramString.getBytes("ISO-8859-1"), "GB2312");
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      str = localUnsupportedEncodingException.toString();
    }
    return str;
  }
  
  public static String gb2iso(String paramString)
  {
    String str = "";
    try
    {
      str = new String(paramString.getBytes("GB2312"), "ISO-8859-1");
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
    }
    return str;
  }
  
  public static String utf8URLencode(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      if ((c >= 0) && (c <= 'ÿ'))
      {
        localStringBuffer.append(c);
      }
      else
      {
        byte[] arrayOfByte = new byte[0];
        try
        {
          arrayOfByte = Character.toString(c).getBytes("UTF-8");
        }
        catch (Exception localException) {}
        for (int j = 0; j < arrayOfByte.length; j++)
        {
          int k = arrayOfByte[j];
          if (k < 0) {
            k += 256;
          }
          localStringBuffer.append("%" + Integer.toHexString(k).toUpperCase());
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  public static String utf8URLdecode(String paramString)
  {
    String str = "";
    int i = 0;
    if ((paramString != null) && (paramString.length() > 0))
    {
      paramString = paramString.toLowerCase();
      i = paramString.indexOf("%e");
      if (i == -1) {
        return paramString;
      }
      while (i != -1)
      {
        str = str + paramString.substring(0, i);
        paramString = paramString.substring(i, paramString.length());
        if ((paramString == "") || (paramString.length() < 9)) {
          return str;
        }
        str = str + codeToWord(paramString.substring(0, 9));
        paramString = paramString.substring(9, paramString.length());
        i = paramString.indexOf("%e");
      }
    }
    return str + paramString;
  }
  
  private static String codeToWord(String paramString)
  {
    String str;
    if (utf8codeCheck(paramString))
    {
      byte[] arrayOfByte = new byte[3];
      arrayOfByte[0] = ((byte)(Integer.parseInt(paramString.substring(1, 3), 16) - 256));
      arrayOfByte[1] = ((byte)(Integer.parseInt(paramString.substring(4, 6), 16) - 256));
      arrayOfByte[2] = ((byte)(Integer.parseInt(paramString.substring(7, 9), 16) - 256));
      try
      {
        str = new String(arrayOfByte, "UTF-8");
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        str = null;
      }
    }
    else
    {
      str = paramString;
    }
    return str;
  }
  
  private static boolean utf8codeCheck(String paramString)
  {
    String str = "";
    if (paramString.startsWith("%e"))
    {
      int i = 0;
      int j = 0;
      while (j != -1)
      {
        j = paramString.indexOf("%", j);
        if (j != -1) {
          j++;
        }
        str = str + j;
        i++;
      }
    }
    return str.equals("147-1");
  }
  
  public static boolean isUtf8Url(String paramString)
  {
    paramString = paramString.toLowerCase();
    int i = paramString.indexOf("%");
    if ((i != -1) && (paramString.length() - i > 9)) {
      paramString = paramString.substring(i, i + 9);
    }
    return utf8codeCheck(paramString);
  }
}
