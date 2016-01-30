package gnnt.MEBS.timebargain.tradeweb.util;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class RandomGUID
{
  public String valueBeforeMD5 = "";
  public String valueAfterMD5 = "";
  private static Random myRand;
  private static SecureRandom mySecureRand = new SecureRandom();
  private static String s_id;
  
  public RandomGUID()
  {
    getRandomGUID(false);
  }
  
  public RandomGUID(boolean paramBoolean)
  {
    getRandomGUID(paramBoolean);
  }
  
  private void getRandomGUID(boolean paramBoolean)
  {
    MessageDigest localMessageDigest = null;
    StringBuffer localStringBuffer1 = new StringBuffer();
    try
    {
      localMessageDigest = MessageDigest.getInstance("MD5");
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      System.out.println("Error: " + localNoSuchAlgorithmException);
    }
    try
    {
      long l1 = System.currentTimeMillis();
      long l2 = 0L;
      if (paramBoolean) {
        l2 = mySecureRand.nextLong();
      } else {
        l2 = myRand.nextLong();
      }
      localStringBuffer1.append(s_id);
      localStringBuffer1.append(":");
      localStringBuffer1.append(Long.toString(l1));
      localStringBuffer1.append(":");
      localStringBuffer1.append(Long.toString(l2));
      this.valueBeforeMD5 = localStringBuffer1.toString();
      localMessageDigest.update(this.valueBeforeMD5.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      StringBuffer localStringBuffer2 = new StringBuffer();
      for (int i = 0; i < arrayOfByte.length; i++)
      {
        int j = arrayOfByte[i] & 0xFF;
        if (j < 16) {
          localStringBuffer2.append('0');
        }
        localStringBuffer2.append(Integer.toHexString(j));
      }
      this.valueAfterMD5 = localStringBuffer2.toString();
    }
    catch (Exception localException)
    {
      System.out.println("Error:" + localException);
    }
  }
  
  public String toString()
  {
    String str = this.valueAfterMD5.toUpperCase();
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(str.substring(0, 8));
    localStringBuffer.append("-");
    localStringBuffer.append(str.substring(8, 12));
    localStringBuffer.append("-");
    localStringBuffer.append(str.substring(12, 16));
    localStringBuffer.append("-");
    localStringBuffer.append(str.substring(16, 20));
    localStringBuffer.append("-");
    localStringBuffer.append(str.substring(20));
    return localStringBuffer.toString();
  }
  
  public static void main(String[] paramArrayOfString)
  {
    for (int i = 0; i < 100; i++)
    {
      RandomGUID localRandomGUID = new RandomGUID();
      System.out.println("Seeding String=" + localRandomGUID.valueBeforeMD5);
      System.out.println("rawGUID=" + localRandomGUID.valueAfterMD5);
      System.out.println("RandomGUID=" + localRandomGUID.toString());
    }
  }
  
  static
  {
    long l = mySecureRand.nextLong();
    myRand = new Random(l);
    try
    {
      s_id = InetAddress.getLocalHost().toString();
    }
    catch (UnknownHostException localUnknownHostException)
    {
      localUnknownHostException.printStackTrace();
    }
  }
}
