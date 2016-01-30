package gnnt.MEBS.timebargain.mgr.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringUtil
{
  private static final Log log = LogFactory.getLog(StringUtil.class);

  public static String encodePassword(String password, String algorithm)
  {
    byte[] unencodedPassword = password.getBytes();

    MessageDigest md = null;
    try
    {
      md = MessageDigest.getInstance(algorithm);
    } catch (Exception e) {
      log.error("Exception: " + e);

      return password;
    }

    md.reset();

    md.update(unencodedPassword);

    byte[] encodedPassword = md.digest();

    StringBuffer buf = new StringBuffer();

    for (int i = 0; i < encodedPassword.length; i++) {
      if ((encodedPassword[i] & 0xFF) < 16) {
        buf.append("0");
      }

      buf.append(Long.toString(encodedPassword[i] & 0xFF, 16));
    }

    return buf.toString();
  }

  public static String encodeString(String str)
  {
    BASE64Encoder encoder = new BASE64Encoder();
    return encoder.encodeBuffer(str.getBytes()).trim();
  }

  public static String decodeString(String str)
  {
    BASE64Decoder dec = new BASE64Decoder();
    try {
      return new String(dec.decodeBuffer(str));
    } catch (IOException io) {
      throw new RuntimeException(io.getMessage(), io.getCause());
    }
  }

  public static String generateRandomStr(int len)
  {
    String str = "";
    Random random = new Random();
    for (int i = 0; i < len; i++)
    {
      str = str + String.valueOf(random.nextInt(10));
    }
    return str;
  }

  public static void main(String[] args)
  {
  }
}