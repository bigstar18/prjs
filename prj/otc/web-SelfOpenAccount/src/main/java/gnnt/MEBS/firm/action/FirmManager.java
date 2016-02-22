package gnnt.MEBS.firm.action;

import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.firm.common.Client;
import gnnt.MEBS.firm.common.SysData;
import gnnt.MEBS.firm.service.FirmService;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirmManager
{
  private FirmService firmService;
  public static Map<String, String> phoneCode = new HashMap();
  
  public FirmManager()
  {
    this.firmService = ((FirmService)SysData.getBean("m_firmService"));
  }
  
  public String PersonCode(String memberNo, String personNo)
  {
    boolean flag = this.firmService.PersonCode(memberNo, personNo);
    System.out.println("flag------------------------------------------------------->>>>>>>>>>>>>>" + flag);
    if (flag) {
      return "1";
    }
    return "0";
  }
  
  public String checkedContacterPhoneNo(String ContacterPhoneNo)
  {
    boolean flag = this.firmService.checkedZSContacterPhoneNo(ContacterPhoneNo);
    System.out.println("flag------------------------------------------------------->>>>>>>>>>>>>>" + flag);
    if (flag) {
      return "1";
    }
    return "0";
  }
  
  public String checkMemberForBrokerage(String brokerage)
  {
    Map map = this.firmService.getMemberForBrokerage(brokerage);
    if ((map == null) || (map.get("MEMBERNO") == null)) {
      return "1";
    }
    return "0";
  }
  
  public String getBroker(String memberNo)
  {
    String str = "";
    List<Brokerage> list = this.firmService.getBroker(memberNo);
    for (int i = 0; i < list.size(); i++)
    {
      Map map = (Map)list.get(i);
      str = str + map.get("brokerageno") + "," + map.get("brokerageno");
      if (i != list.size() - 1) {
        str = str + ";";
      }
    }
    if ((list == null) || (list.size() == 0)) {
      str = "-1, ";
    }
    return str;
  }
  
  public String getOrgBroker(String organizationno)
  {
    String str = "";
    List<Brokerage> list = this.firmService.getOrgBroker(organizationno);
    for (int i = 0; i < list.size(); i++)
    {
      Map map = (Map)list.get(i);
      str = str + map.get("brokerageno") + "," + map.get("brokerageno");
      if (i != list.size() - 1) {
        str = str + ";";
      }
    }
    if ((list == null) || (list.size() == 0)) {
      str = " , ";
    }
    return str;
  }
  
  public String getMemberInfor(String memberNo)
  {
    Map map = this.firmService.getMemberInfor(memberNo);
    String str = (String)map.get("notes");
    return str;
  }
  
  public List getMemberIdAndName()
  {
    return this.firmService.getMemberIdAndName();
  }
  
  public boolean checkAccountNum(String bankaccount)
  {
    bankaccount = bankaccount.replaceAll(" ", "");
    return this.firmService.checkAccountNum(bankaccount);
  }
  
  public boolean checkedCardNumber(String cardNumber, String memNo)
  {
    return this.firmService.checkedCardNumber(cardNumber, memNo);
  }
  
  public boolean checkzscheckedCardNumber(String cardNumber, String memNo)
  {
    Map map = this.firmService.getMemberForBrokerage(memNo);
    if (map == null) {
      return true;
    }
    return this.firmService.zscheckedCardNumber(cardNumber, map.get("MEMBERNO").toString());
  }
  
  public String getAllBanks()
  {
    String str = "";
    List list = this.firmService.getAllBank();
    for (int i = 0; i < list.size(); i++)
    {
      Map map = (Map)list.get(i);
      str = str + map.get("bankId") + "," + map.get("bankName");
      if (i != list.size() - 1) {
        str = str + ";";
      }
    }
    return str;
  }
  
  public int sendSMS(String phoneNo)
    throws UnsupportedEncodingException
  {
    return send("您好，欢迎使用长商所网上开户系统，您的注册验证码为:random。【长商所】", phoneNo, false);
  }
  
  public void sendCustomNo(String name, String phoneNo, String customNo)
    throws UnsupportedEncodingException
  {
    Client client = null;
    client = new Client("SDK-JWA-010-00008", "3-5cE1-4");
    try
    {
      String ret = client.mt(phoneNo, name + "，" + "您好，欢迎使用长商所网上开户系统，您的模拟账号为:" + customNo + "。【长商所】", "", "", "");
      System.out.println("模拟账号通知返回码：ret==" + ret);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void sendZSCustomNo(String name, String phoneNo, String customNo)
    throws UnsupportedEncodingException
  {
    Client client = null;
    client = new Client("SDK-JWA-010-00008", "3-5cE1-4");
    try
    {
      String ret = client.mt(phoneNo, name + "，" + "您好，欢迎使用长商所网上开户系统，您的正式账号为:" + customNo + "。【长商所】", "", "", "");
      System.out.println("正式账号短信通知返回码：ret==" + ret);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public int send(String content, String to, boolean isCheckedPhoneNo)
    throws UnsupportedEncodingException
  {
    int returnType = 0;
    if (content.indexOf("random") > 0)
    {
      String randomCode = FirmController.getRandomCode();
      phoneCode.put(to, randomCode);
      content = content.replaceAll("random", randomCode);
    }
    String[] toList = to.split(",");
    Pattern pattern = Pattern.compile("1[3,4,5,8]{1}\\d{9}");
    FirmService firmService = (FirmService)SysData.getBean("m_firmService");
    for (int i = 0; i < toList.length; i++) {
      if ((!"".equals(toList[i])) && (toList[i] != null))
      {
        Matcher matcher = pattern.matcher(toList[i]);
        boolean result = matcher.matches();
        boolean pass = false;
        if (isCheckedPhoneNo) {
          pass = true;
        } else {
          pass = firmService.checkedZSContacterPhoneNo(toList[i]);
        }
        Client client = null;
        client = new Client("SDK-JWA-010-00008", "3-5cE1-4");
        if ((result) && (!pass)) {
          try
          {
            String ret = client.mt(toList[i], content, "", "", "");
            System.out.println("短信发送返回码：ret11111111111111111111111111111111111111111==" + ret);
            returnType = 0;
          }
          catch (Exception e)
          {
            e.printStackTrace();
          }
        } else if (!result) {
          returnType = 1;
        } else {
          returnType = 2;
        }
      }
    }
    return returnType;
  }
}
