package gnnt.bank.platform.util;

import java.io.PrintStream;
import java.security.MessageDigest;

public class Encryption
{
  private static String md5(byte[] source)
  {
    String s = null;
    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(source);
      byte[] tmp = md.digest();
      char[] str = new char[32];
      int k = 0;
      for (int i = 0; i < 16; i++)
      {
        byte byte0 = tmp[i];
        str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
        str[(k++)] = hexDigits[(byte0 & 0xF)];
      }
      s = new String(str);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return s;
  }
  
  private static String getpwdStr(String firmID, String password, String key)
  {
    if (firmID == null) {
      firmID = "";
    }
    if (password == null) {
      password = "";
    }
    if (key == null) {
      key = "";
    }
    String str = firmID.trim() + password.trim() + key.trim();
    return str;
  }
  
  public static String encryption(String firmID, String password, String key)
  {
    String result = null;
    String pwd = getpwdStr(firmID, password, key);
    result = md5(pwd.getBytes());
    return result;
  }
  
  public static void main(String[] args)
  {
    String str = encryption("0001", "000000", "");
    String str1 = encryption("0002", "000000", "");
    String str2 = encryption("0003", "000000", "");
    String str3 = encryption("0005", "000000", null);
    System.out.println(str + "\n" + str1 + "\n" + str2 + "\n" + str3 + "\n3055bc7d12fdd5ba8d8a5a473d92b747");
  }
}
