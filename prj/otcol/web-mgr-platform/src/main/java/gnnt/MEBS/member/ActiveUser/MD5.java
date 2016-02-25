package gnnt.MEBS.member.ActiveUser;

import java.security.MessageDigest;

public class MD5
{
  public static String encodePassword(String paramString1, String paramString2)
  {
    byte[] arrayOfByte1 = paramString1.getBytes();
    MessageDigest localMessageDigest = null;
    try
    {
      localMessageDigest = MessageDigest.getInstance(paramString2);
    }
    catch (Exception localException)
    {
      return paramString1;
    }
    localMessageDigest.reset();
    localMessageDigest.update(arrayOfByte1);
    byte[] arrayOfByte2 = localMessageDigest.digest();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < arrayOfByte2.length; i++)
    {
      if ((arrayOfByte2[i] & 0xFF) < 16) {
        localStringBuffer.append("0");
      }
      localStringBuffer.append(Long.toString(arrayOfByte2[i] & 0xFF, 16));
    }
    return localStringBuffer.toString();
  }
  
  public static final String getMD5(String paramString1, String paramString2)
  {
    return encodePassword(paramString1 + paramString2, "MD5");
  }
}
