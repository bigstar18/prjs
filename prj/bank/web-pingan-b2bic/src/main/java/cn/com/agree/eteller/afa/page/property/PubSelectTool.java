package cn.com.agree.eteller.afa.page.property;

import cn.com.agree.eteller.afa.page.selectmodel.PubType;
import cn.com.agree.eteller.generic.utils.CommonType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

public class PubSelectTool
{
  public static PubType[] loadAgentflag()
  {
    ResourceBundle string = 
      ResourceBundle.getBundle("cn.com.agree.eteller.afa.page.property.agentflag");
    List list = new ArrayList();
    Enumeration en = string.getKeys();
    while (en.hasMoreElements())
    {
      String id = (String)en.nextElement();
      String value = string.getString(id);
      list.add(new PubType(id, value));
    }
    PubType[] ct = new PubType[list.size()];
    for (int i = 0; i < ct.length; i++) {
      ct[i] = ((PubType)list.get(i));
    }
    Arrays.sort(ct);
    return ct;
  }
  
  public static PubType[] loadAgentflag1()
  {
    ResourceBundle string = 
      ResourceBundle.getBundle("cn.com.agree.eteller.afa.page.property.agentflag1");
    List list = new ArrayList();
    Enumeration en = string.getKeys();
    while (en.hasMoreElements())
    {
      String id = (String)en.nextElement();
      String value = string.getString(id);
      list.add(new PubType(id, value));
    }
    PubType[] ct = new PubType[list.size()];
    for (int i = 0; i < ct.length; i++) {
      ct[i] = ((PubType)list.get(i));
    }
    Arrays.sort(ct);
    return ct;
  }
  
  public static PubType[] loadSysid()
  {
    ResourceBundle string = 
      ResourceBundle.getBundle("cn.com.agree.eteller.afa.page.property.sysid");
    List list = new ArrayList();
    Enumeration en = string.getKeys();
    while (en.hasMoreElements())
    {
      String id = (String)en.nextElement();
      String value = string.getString(id);
      list.add(new PubType(id, value));
    }
    PubType[] ct = new PubType[list.size()];
    for (int i = 0; i < ct.length; i++) {
      ct[i] = ((PubType)list.get(i));
    }
    Arrays.sort(ct);
    return ct;
  }
  
  public static PubType[] loaActnoadmChkaccpwdctl()
  {
    ResourceBundle string = 
      ResourceBundle.getBundle("cn.com.agree.eteller.afa.page.property.actnomchkaccpwdctl");
    List list = new ArrayList();
    Enumeration en = string.getKeys();
    while (en.hasMoreElements())
    {
      String id = (String)en.nextElement();
      String value = string.getString(id);
      list.add(new PubType(id, value));
    }
    PubType[] ct = new PubType[list.size()];
    for (int i = 0; i < ct.length; i++) {
      ct[i] = ((PubType)list.get(i));
    }
    Arrays.sort(ct);
    return ct;
  }
  
  public static PubType[] loadActnoadmEnpaccpwdctl()
  {
    ResourceBundle string = 
      ResourceBundle.getBundle("cn.com.agree.eteller.afa.page.property.actnomenpaccpwdctl");
    List list = new ArrayList();
    Enumeration en = string.getKeys();
    while (en.hasMoreElements())
    {
      String id = (String)en.nextElement();
      String value = string.getString(id);
      list.add(new PubType(id, value));
    }
    PubType[] ct = new PubType[list.size()];
    for (int i = 0; i < ct.length; i++) {
      ct[i] = ((PubType)list.get(i));
    }
    Arrays.sort(ct);
    return ct;
  }
  
  public static PubType[] loadActnoadmStatus()
  {
    ResourceBundle string = 
      ResourceBundle.getBundle("cn.com.agree.eteller.afa.page.property.actnomstatus");
    List list = new ArrayList();
    Enumeration en = string.getKeys();
    while (en.hasMoreElements())
    {
      String id = (String)en.nextElement();
      String value = string.getString(id);
      list.add(new PubType(id, value));
    }
    PubType[] ct = new PubType[list.size()];
    for (int i = 0; i < ct.length; i++) {
      ct[i] = ((PubType)list.get(i));
    }
    Arrays.sort(ct);
    return ct;
  }
  
  public static PubType[] loadChanneladmautochkacct()
  {
    ResourceBundle string = 
      ResourceBundle.getBundle("cn.com.agree.eteller.afa.page.property.channeladmautochkacct");
    List list = new ArrayList();
    Enumeration en = string.getKeys();
    while (en.hasMoreElements())
    {
      String id = (String)en.nextElement();
      String value = string.getString(id);
      list.add(new PubType(id, value));
    }
    PubType[] ct = new PubType[list.size()];
    for (int i = 0; i < ct.length; i++) {
      ct[i] = ((PubType)list.get(i));
    }
    Arrays.sort(ct);
    return ct;
  }
  
  public static PubType[] loadChanneladmautorevtranctl()
  {
    ResourceBundle string = 
      ResourceBundle.getBundle("cn.com.agree.eteller.afa.page.property.channeladmautorevtranctl");
    List list = new ArrayList();
    Enumeration en = string.getKeys();
    while (en.hasMoreElements())
    {
      String id = (String)en.nextElement();
      String value = string.getString(id);
      list.add(new PubType(id, value));
    }
    PubType[] ct = new PubType[list.size()];
    for (int i = 0; i < ct.length; i++) {
      ct[i] = ((PubType)list.get(i));
    }
    Arrays.sort(ct);
    return ct;
  }
  
  public static PubType[] loadChanneladmbillsavectl()
  {
    ResourceBundle string = 
      ResourceBundle.getBundle("cn.com.agree.eteller.afa.page.property.channeladmbillsavectl");
    List list = new ArrayList();
    Enumeration en = string.getKeys();
    while (en.hasMoreElements())
    {
      String id = (String)en.nextElement();
      String value = string.getString(id);
      list.add(new PubType(id, value));
    }
    PubType[] ct = new PubType[list.size()];
    for (int i = 0; i < ct.length; i++) {
      ct[i] = ((PubType)list.get(i));
    }
    Arrays.sort(ct);
    return ct;
  }
  
  public static PubType[] loadChanneladmerrchkctl()
  {
    ResourceBundle string = 
      ResourceBundle.getBundle("cn.com.agree.eteller.afa.page.property.channeladmerrchkctl");
    List list = new ArrayList();
    Enumeration en = string.getKeys();
    while (en.hasMoreElements())
    {
      String id = (String)en.nextElement();
      String value = string.getString(id);
      list.add(new PubType(id, value));
    }
    PubType[] ct = new PubType[list.size()];
    for (int i = 0; i < ct.length; i++) {
      ct[i] = ((PubType)list.get(i));
    }
    Arrays.sort(ct);
    return ct;
  }
  
  public static PubType[] loadChanneladmstatus()
  {
    ResourceBundle string = 
      ResourceBundle.getBundle("cn.com.agree.eteller.afa.page.property.channeladmstatus");
    List list = new ArrayList();
    Enumeration en = string.getKeys();
    while (en.hasMoreElements())
    {
      String id = (String)en.nextElement();
      String value = string.getString(id);
      list.add(new PubType(id, value));
    }
    PubType[] ct = new PubType[list.size()];
    for (int i = 0; i < ct.length; i++) {
      ct[i] = ((PubType)list.get(i));
    }
    Arrays.sort(ct);
    return ct;
  }
  
  public static CommonType[] loadExcelPubInfo(String code)
  {
    ResourceBundle string = 
      ResourceBundle.getBundle("cn.com.agree.eteller.afa.page.property.etellerExcelPub");
    List list = new ArrayList();
    Enumeration en = string.getKeys();
    String str = "";
    String value = "";
    while (en.hasMoreElements())
    {
      String id = (String)en.nextElement();
      str = id.substring(0, id.indexOf("_"));
      if (str.equals(code))
      {
        value = string.getString(id);
        id = id.substring(id.indexOf("_") + 1);
        list.add(new CommonType(id, value));
      }
    }
    CommonType[] ct = new CommonType[list.size()];
    for (int i = 0; i < ct.length; i++) {
      ct[i] = ((CommonType)list.get(i));
    }
    return ct;
  }
  
  public static String loadRepotInfoBycode(String code)
  {
    ResourceBundle string = 
      ResourceBundle.getBundle("cn.com.agree.eteller.afa.page.property.etellerExcelPub");
    Enumeration en = string.getKeys();
    String str = "";
    while (en.hasMoreElements())
    {
      String id = (String)en.nextElement();
      if (id.equals(code))
      {
        str = string.getString(id);
        break;
      }
    }
    return str;
  }
}
