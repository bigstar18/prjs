package gnnt.trade.bank.util;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.digest.DigestUtils;

public class MD5orSHA
{
  public static String version = "2.0";
  public static String signType = "1";
  public static String currencyType = "156";
  
  public static String md5(String message)
    throws UnsupportedEncodingException
  {
    return DigestUtils.md5Hex(message.getBytes("UTF-8"));
  }
  
  private static String sha(String message)
    throws UnsupportedEncodingException
  {
    return DigestUtils.shaHex(message.getBytes("UTF-8"));
  }
  
  public static String rgstEncryption(String actionId, String tranDateTime, String markCode, String backgroundMerUrl, String VerficationCode)
  {
    String message = "version=[" + version + "]signType=[" + signType + "]" + 
      "tranCode=[10000]merchantID=[" + markCode + "]" + 
      "merOrderNum=[" + actionId + "]tranDateTime=[" + tranDateTime + "]" + 
      "merURL=[" + backgroundMerUrl + "]VerficationCode=[" + VerficationCode + "]";
    System.out.println("签约加密明文：" + message);
    String messagePWD = "";
    if (signType.equals("1")) {
      try
      {
        messagePWD = md5(message);
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
    } else if (signType.equals("2")) {
      try
      {
        messagePWD = sha(message);
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
    }
    return messagePWD;
  }
  
  public static String delEncryption(String account, String actionId, String tranDateTime, String markCode, String backgroundMerUrl, String VerficationCode)
  {
    String message = "version=[" + version + "]signType=[" + signType + "]" + 
      "tranCode=[10001]merchantID=[" + markCode + "]" + 
      "merOrderNum=[" + actionId + "]tranDateTime=[" + tranDateTime + "]" + 
      "merURL=[" + backgroundMerUrl + "]contractNo=[" + account + "]" + 
      "VerficationCode=[" + VerficationCode + "]";
    System.out.println("解约加密明文：" + message);
    String messagePWD = "";
    if (signType.equals("1")) {
      try
      {
        messagePWD = md5(message);
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
    } else if (signType.equals("2")) {
      try
      {
        messagePWD = sha(message);
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
    }
    return messagePWD;
  }
  
  public static String inMoneyEncryption(double tranAmt, String tranIP, String isRepeatSubmit, String account, String actionId, String tranDateTime, String markCode, String backgroundMerUrl, String VerficationCode, String marketGSAcount, String frontMerUrl)
  {
    String message = "version=[" + version + "]tranCode=[8801]" + 
      "merchantID=[" + markCode + "]contractNo=[" + account + "]" + 
      "merOrderNum=[" + actionId + "]virCardNoIn=[" + marketGSAcount + "]" + 
      "tranAmt=[" + Tool.fmtDouble2(tranAmt) + "]currencyType=[" + currencyType + "]" + 
      "tranDateTime=[" + tranDateTime + "]frontMerUrl=[" + frontMerUrl + "]" + 
      "backgroundMerUrl=[" + backgroundMerUrl + "]signType=[" + signType + "]" + 
      "isRepeatSubmit=[" + isRepeatSubmit + "]tranIP=[" + tranIP + "]" + 
      "VerficationCode=[" + VerficationCode + "]";
    System.out.println("入金加密明文：" + message);
    String messagePWD = "";
    if (signType.equals("1")) {
      try
      {
        messagePWD = md5(message);
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
    } else if (signType.equals("2")) {
      try
      {
        messagePWD = sha(message);
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
    }
    return messagePWD;
  }
  
  public static String outMoneyEncryption(double tranAmt, String tranIP, String isRepeatSubmit, String account, String actionId, String tranDateTime, String markCode, String backgroundMerUrl, String VerficationCode, String marketGSAcount, String frontMerUrl)
  {
    String message = "version=[" + version + "]tranCode=[8802]" + 
      "merchantID=[" + markCode + "]contractNo=[" + account + "]" + 
      "merOrderNum=[" + actionId + "]virCardNo=[" + marketGSAcount + "]" + 
      "tranAmt=[" + Tool.fmtDouble2(tranAmt) + "]currencyType=[" + currencyType + "]" + 
      "tranDateTime=[" + tranDateTime + "]" + 
      "backgroundMerUrl=[" + backgroundMerUrl + "]signType=[" + signType + "]" + 
      "isRepeatSubmit=[" + isRepeatSubmit + "]tranIP=[" + tranIP + "]" + 
      "VerficationCode=[" + VerficationCode + "]";
    System.out.println(message);
    String messagePWD = "";
    if (signType.equals("1")) {
      try
      {
        messagePWD = md5(message);
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
    } else if (signType.equals("2")) {
      try
      {
        messagePWD = sha(message);
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
    }
    return messagePWD;
  }
  
  public static String dateCheckEncryption(String queryType, String actionId, String tranBeginTime, String tranEndTime, String pageNo, String pageSize, String markCode, String backgroundMerUrl, String VerficationCode, String marketGSAcount, String frontMerUrl)
  {
    String message = "version=[" + version + "]signType=[" + signType + "]" + 
      "tranCode=[10004]merchantID=[" + markCode + "]queryType=[" + queryType + "]" + 
      "merOrderNum=[" + actionId + "]tranBeginTime=[" + tranBeginTime + "]" + 
      "tranEndTime=[" + tranEndTime + "]spvVirCardNo=[" + marketGSAcount + "]" + 
      "pageNo=[" + pageNo + "]pageSize=[" + pageSize + "]VerficationCode=[" + VerficationCode + "]";
    
    String messagePWD = "";
    if (signType.equals("1")) {
      try
      {
        messagePWD = md5(message);
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
    } else if (signType.equals("2")) {
      try
      {
        messagePWD = sha(message);
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
    }
    return messagePWD;
  }
  
  public static void main(String[] str)
  {
    String mw = "version=[2.0]signType=[1]tranCode=[10004]merchantID=[0000035628]queryType=[]merOrderNum=[175]tranBeginTime=[20120628000000]tranEndTime=[20120628000000]spvVirCardNo=[0000000002000089403]pageNo=[1]pageSize=[3]VerficationCode=[1111aaaa]";
    try
    {
      System.out.println(mw);
      String miw = md5(mw);
      System.out.println(miw);
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
  }
}
