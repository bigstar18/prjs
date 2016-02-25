package gnnt.MEBS.vendue.manage.tranpass;

public class TranPass
{
  public static final String MD5(String paramString1, String paramString2)
  {
    try
    {
      return paramString2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public static char[] complement(char[] paramArrayOfChar)
    throws Exception
  {
    char[] arrayOfChar = new char[16];
    if (paramArrayOfChar == null) {
      throw new Exception("密码转换时,摘要无效!");
    }
    if (paramArrayOfChar.length < 16)
    {
      for (int i = 0; i < paramArrayOfChar.length; i++) {
        arrayOfChar[i] = paramArrayOfChar[i];
      }
      for (i = paramArrayOfChar.length; i < 16; i++) {
        arrayOfChar[i] = 'z';
      }
      return arrayOfChar;
    }
    return paramArrayOfChar;
  }
  
  public static char[] toChar(String paramString)
  {
    char[] arrayOfChar = new char[paramString.length()];
    for (int i = 0; i < arrayOfChar.length; i++) {
      arrayOfChar[i] = paramString.charAt(i);
    }
    return arrayOfChar;
  }
}
