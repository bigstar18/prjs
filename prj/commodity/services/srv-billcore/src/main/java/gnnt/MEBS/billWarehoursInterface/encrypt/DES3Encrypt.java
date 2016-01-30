package gnnt.MEBS.billWarehoursInterface.encrypt;

import com.sun.crypto.provider.SunJCE;
import java.io.PrintStream;
import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DES3Encrypt
{
  public static String byteArr2HexStr(byte[] arrB)
    throws EncryptException
  {
    try
    {
      int iLen = arrB.length;
      
      StringBuffer sb = new StringBuffer(iLen * 2);
      for (int i = 0; i < iLen; i++)
      {
        int intTmp = arrB[i];
        while (intTmp < 0) {
          intTmp += 256;
        }
        if (intTmp < 16) {
          sb.append("0");
        }
        sb.append(Integer.toString(intTmp, 16));
      }
      return sb.toString();
    }
    catch (Exception e)
    {
      throw new EncryptException(e);
    }
  }
  
  public static byte[] hexStr2ByteArr(String strIn)
    throws EncryptException
  {
    try
    {
      byte[] arrB = strIn.getBytes();
      int iLen = arrB.length;
      
      byte[] arrOut = new byte[iLen / 2];
      for (int i = 0; i < iLen; i += 2)
      {
        String strTmp = new String(arrB, i, 2);
        arrOut[(i / 2)] = ((byte)Integer.parseInt(strTmp, 16));
      }
      return arrOut;
    }
    catch (Exception e)
    {
      throw new EncryptException(e);
    }
  }
  
  public static byte[] encrypt(String strKey, byte[] arrB)
    throws EncryptException
  {
    try
    {
      Key key = getKey(strKey.getBytes());
      Cipher encryptCipher = Cipher.getInstance("DES");
      encryptCipher.init(1, key);
      return encryptCipher.doFinal(arrB);
    }
    catch (Exception e)
    {
      throw new EncryptException(e);
    }
  }
  
  public static String encrypt(String strKey, String strIn)
    throws EncryptException
  {
    return byteArr2HexStr(encrypt(strKey, strIn.getBytes()));
  }
  
  public static byte[] decrypt(String strKey, byte[] arrB)
    throws EncryptException
  {
    try
    {
      Key key = getKey(strKey.getBytes());
      Cipher decryptCipher = Cipher.getInstance("DES");
      decryptCipher.init(2, key);
      return decryptCipher.doFinal(arrB);
    }
    catch (Exception e)
    {
      throw new EncryptException(e);
    }
  }
  
  public static String decrypt(String strKey, String strIn)
    throws EncryptException
  {
    return new String(decrypt(strKey, hexStr2ByteArr(strIn)));
  }
  
  private static Key getKey(byte[] arrBTmp)
    throws EncryptException
  {
    try
    {
      Security.addProvider(new SunJCE());
      
      byte[] arrB = new byte[8];
      for (int i = 0; (i < arrBTmp.length) && (i < arrB.length); i++) {
        arrB[i] = arrBTmp[i];
      }
      return new SecretKeySpec(arrB, "DES");
    }
    catch (Exception e)
    {
      throw new EncryptException(e);
    }
  }
  
  public static void main(String[] args)
  {
    try
    {
      DES3Encrypt des = new DES3Encrypt();
      
      System.out.println("*****  加密测试 *****");
      des.enTest("strMsg");
      
      System.out.println("*****  解密测试 *****");
      des.deTest("bf29cca832d07c56");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  private void enTest(String strOriginal)
  {
    try
    {
      System.out.println("Plain   String: " + strOriginal);
      
      String strEncrypt = encrypt("0123456789", strOriginal);
      System.out.println("Encrypted String: " + strEncrypt);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  private void deTest(String strOriginal)
  {
    try
    {
      System.out.println("Encrypted String: " + strOriginal);
      System.out.println("Encrypted String length =  " + 
        strOriginal.length());
      String strPlain = decrypt("0123456789", strOriginal);
      System.out.println("Plain  String: " + strPlain);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
