package gnnt.MEBS.dbcp.des;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DES
{
  private static String keyCode = "zry5f4/fvCU=";

  public static String encode(String s) {
    String pwd = null;
    try {
      SecretKey sKey = initKey(keyCode);

      Cipher c = Cipher.getInstance("DES");
      c.init(1, sKey);
      pwd = baseEncode(c.doFinal(s.getBytes()));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return pwd;
  }

  public static String decode(String pwd) {
    String orgStr = null;
    try {
      SecretKey sKey = initKey(keyCode);

      Cipher c = Cipher.getInstance("DES");

      c.init(2, sKey);
      byte[] b_orgStr = c.doFinal(baseDecode(pwd));
      orgStr = new String(b_orgStr);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return orgStr;
  }

  public static void setKeyCode() {
    try {
      KeyGenerator keygen = KeyGenerator.getInstance("DES");
      SecretKey deskey = keygen.generateKey();
      keyCode = baseEncode(deskey.getEncoded());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static SecretKey initKey(String encode_key) {
    SecretKey sKey = null;
    try {
      sKey = new SecretKeySpec(baseDecode(encode_key), "DES");
    } catch (Exception e) {
      e.printStackTrace();
    }

    return sKey;
  }

  private static String baseEncode(byte[] b) throws Exception {
    BASE64Encoder base64E = new BASE64Encoder();
    return base64E.encode(b);
  }

  private static byte[] baseDecode(String s) throws Exception {
    BASE64Decoder base64D = new BASE64Decoder();
    return base64D.decodeBuffer(s);
  }

  public static void main(String[] args) throws Exception {
    String str = "";
    while (!"exit".equalsIgnoreCase(str))
      try {
        System.out.print("请输入要加/解密的字符串（回车键确认，输入exit退出）：");
        InputStreamReader reader = new InputStreamReader(System.in);
        str = new BufferedReader(reader).readLine();
        if ((str != null) && (str.trim().length() > 0) && 
          (!"exit".equalsIgnoreCase(str)))
        {
          try {
            System.out.println("字符串加密后为：" + encode(str));
          } catch (Error localError) {
          }
          try {
            System.out.println("字符串解密后为：" + decode(str)); } catch (Error localError1) {
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
  }
}