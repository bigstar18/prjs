package gnnt.MEBS.broker.mgr.util;

import java.io.IOException;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringUtil
{
  private static final Log log = LogFactory.getLog(StringUtil.class);

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
      log.error("Exception: " + localException);
      return paramString1;
    }
    localMessageDigest.reset();
    localMessageDigest.update(arrayOfByte1);
    byte[] arrayOfByte2 = localMessageDigest.digest();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < arrayOfByte2.length; i++)
    {
      if ((arrayOfByte2[i] & 0xFF) < 16)
        localStringBuffer.append("0");
      localStringBuffer.append(Long.toString(arrayOfByte2[i] & 0xFF, 16));
    }
    return localStringBuffer.toString();
  }

  public static String encodeString(String paramString)
  {
    BASE64Encoder localBASE64Encoder = new BASE64Encoder();
    return localBASE64Encoder.encodeBuffer(paramString.getBytes()).trim();
  }

  public static String decodeString(String paramString)
  {
    BASE64Decoder localBASE64Decoder = new BASE64Decoder();
    try
    {
      return new String(localBASE64Decoder.decodeBuffer(paramString));
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException.getMessage(), localIOException.getCause());
    }
  }

  public static String generateRandomStr(int paramInt)
  {
    String str = "";
    Random localRandom = new Random();
    for (int i = 0; i < paramInt; i++)
      str = str + String.valueOf(localRandom.nextInt(10));
    return str;
  }

  public static void main(String[] paramArrayOfString)
  {
    System.out.println("password:" + encodePassword("1111111111", "MD5"));
  }
}