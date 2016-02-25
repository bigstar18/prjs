package gnnt.trade.bank.util;

import cn.com.infosec.icbc.ReturnValue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class EncodedICBCEBank
{
  public static String createFormData(String ip, String tranDataXmlStr, String cerPath, String keyPath, String pwd)
  {
    try
    {
      if (tranDataXmlStr == null) {
        return null;
      }
      tranDataXmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?> " + tranDataXmlStr;
      System.out.println("tranData明文串：" + tranDataXmlStr);
      if (tranDataXmlStr.indexOf("<merCustomIp>") >= 0)
      {
        String str1 = tranDataXmlStr.substring(0, tranDataXmlStr.indexOf("<merCustomIp>") + 13);
        String str2 = tranDataXmlStr.substring(tranDataXmlStr.indexOf("</merCustomIp>"), tranDataXmlStr.length());
        tranDataXmlStr = str1 + ip + str2;
      }
      byte[] byteTranData = tranDataXmlStr.getBytes();
      
      byte[] Base64TranData = ReturnValue.base64enc(byteTranData);
      String StrBase64TranData = new String(Base64TranData).toString();
      System.out.println("tranData明文串的base64编码：" + StrBase64TranData);
      
      char[] keyPass = pwd.toCharArray();
      byte[] bcert = getCrt(cerPath);
      byte[] bkey = getCrt(keyPath);
      
      byte[] EncCert = ReturnValue.base64enc(bcert);
      String CertBase64 = new String(EncCert).toString();
      System.out.println("证书公钥BASE64编码：" + CertBase64);
      
      byte[] sign = ReturnValue.sign(byteTranData, byteTranData.length, bkey, keyPass);
      if (sign == null)
      {
        System.out.println("错误：签名失败,签名返回为空。请检查证书私钥和私钥保护口令是否正确。");
        return null;
      }
      System.out.println("签名成功。");
      
      byte[] EncSign = ReturnValue.base64enc(sign);
      String SignMsgBase64 = new String(EncSign).toString();
      System.out.println("签名信息BASE64编码：" + SignMsgBase64);
      
      return StrBase64TranData + "<GNNTEND/>" + SignMsgBase64 + "<GNNTEND/>" + CertBase64;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  private static byte[] getCrt(String keyPath)
  {
    try
    {
      FileInputStream in1 = new FileInputStream(keyPath);
      byte[] bcert = new byte[in1.available()];
      in1.read(bcert);
      in1.close();
      
      return bcert;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static void main(String[] args)
  {
    String tranDataXmlStr = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><B2CReq><interfaceName>ICBC_PERBANK_B2C</interfaceName><interfaceVersion>1.0.0.11</interfaceVersion><orderInfo><orderDate>20140201104157</orderDate><curType>001</curType><merID>0200EC24043822</merID><subOrderInfoList><subOrderInfo><orderid>24556</orderid><amount>2</amount><installmentTimes>1</installmentTimes><merAcct>0200004519000100173</merAcct><goodsID></goodsID><goodsName>贵金属</goodsName><goodsNum>1</goodsNum><carriageAmt></carriageAmt></subOrderInfo></subOrderInfoList></orderInfo><custom><verifyJoinFlag>0</verifyJoinFlag><Language>ZH_CN</Language></custom><message><creditType>2</creditType><notifyType>HS</notifyType><resultType>0</resultType><merReference></merReference><merCustomIp>124.207.182.162</merCustomIp><goodsType></goodsType><merCustomID></merCustomID><merCustomPhone></merCustomPhone><goodsAddress></goodsAddress><merOrderRemark>3011</merOrderRemark><merHint></merHint><remark1></remark1><remark2></remark2><merURL>http://124.207.182.190:12068/gnnt.do</merURL><merVAR></merVAR></message></B2CReq>";
    if (tranDataXmlStr.indexOf("") >= 0)
    {
      String str1 = tranDataXmlStr.substring(0, tranDataXmlStr.indexOf("<merCustomIp>") + 13);
      String ip = "*****************";
      String str2 = tranDataXmlStr.substring(tranDataXmlStr.indexOf("</merCustomIp>"), tranDataXmlStr.length());
      System.out.println(str1);
      System.out.println(str2);
      System.out.println();
      System.out.println(str1 + ip + str2);
    }
  }
}
