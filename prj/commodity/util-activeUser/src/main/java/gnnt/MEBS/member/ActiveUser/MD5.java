package gnnt.MEBS.member.ActiveUser;

import java.io.PrintStream;
import java.security.MessageDigest;

public class MD5
{
  public static String encodePassword(String password, String algorithm)
  {
    byte[] unencodedPassword = password.getBytes();
    
    MessageDigest md = null;
    try
    {
      md = MessageDigest.getInstance(algorithm);
    }
    catch (Exception e)
    {
      return password;
    }
    md.reset();
    


    md.update(unencodedPassword);
    

    byte[] encodedPassword = md.digest();
    
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < encodedPassword.length; i++)
    {
      if ((encodedPassword[i] & 0xFF) < 16) {
        buf.append("0");
      }
      buf.append(Long.toString(encodedPassword[i] & 0xFF, 16));
    }
    return buf.toString();
  }
  
  public static final String getMD5(String userId, String pwd)
  {
    return encodePassword(userId + pwd, "MD5");
  }
  
  public static void main(String[] args)
  {
    System.out.println(getMD5("te2_admin", "111111"));
  }
}
