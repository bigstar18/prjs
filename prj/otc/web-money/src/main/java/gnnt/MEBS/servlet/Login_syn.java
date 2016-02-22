package gnnt.MEBS.servlet;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.member.ActiveUser.TraderInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Login_syn
  extends HttpServlet
{
  private static final long serialVersionUID = -3204129818412003839L;
  private String MODELID = "";
  
  public void destroy()
  {
    super.destroy();
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setContentType("text/xml");
    response.setCharacterEncoding("GBK");
    
    BufferedReader br = new BufferedReader(new InputStreamReader(request
      .getInputStream()));
    String re = br.readLine();
    StringBuffer strXML = new StringBuffer();
    while (re != null)
    {
      strXML.append(re);
      re = br.readLine();
    }
    PrintWriter out = response.getWriter();
    try
    {
      System.out.println("servlet==============>strXML:" + strXML);
      System.out.println("servlet==========>end");
      String loginxml = strXML.toString();
      if (loginxml == null) {
        return;
      }
      String type = getXmlType("name", parserStrtoDocument(loginxml));
      if ("logoff".equals(type))
      {
        long auSessionId = toLong(getXmlParament(loginxml, "SESSION_ID"));
        LogonManager manager = LogonManager.getInstance();
        String message = "注销成功";
        manager.logoff(auSessionId);
        request.getSession().removeAttribute("LOGINID");
        request.getSession().invalidate();
        StringBuffer ret = new StringBuffer();
        ret
          .append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"logoff\">\n<RESULT>\n<RETCODE>");
        ret.append("0</RETCODE>\n<MESSAGE>");
        ret.append(message);
        ret.append("</MESSAGE>\n</RESULT>\n</REP>\n</GNNT>\n");
        out.print(ret.toString());
        return;
      }
      if ("check_user".equals(type))
      {
        StringBuffer ret = new StringBuffer();
        if ((request.getSession().getAttribute("LOGINID") != null) && 
          (((Long)request.getSession()
          .getAttribute("LOGINID")).longValue() != 0L))
        {
          ret.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"check_user\">\n<RESULT>\n<RETCODE>");
          ret
            .append("0</RETCODE>\n<MESSAGE>success</MESSAGE>\n<MODULE_ID>");
          ret.append(this.MODELID);
          ret.append("</MODULE_ID>\n</RESULT>\n</REP>\n</GNNT>\n");
          out.print(ret.toString());
          System.out.println("servlet==============>return:" + 
            ret.toString());
          return;
        }
        String username = null;
        try
        {
          username = getXmlParament(loginxml, "TRADER_ID");
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        if ("".equals(username)) {
          username = getXmlParament(loginxml, "USER_ID");
        }
        long auSessionId = toLong(getXmlParament(loginxml, "SESSION_ID"));
        
        long sessionID = checkUser(username, auSessionId, request, 
          response);
        
        String message = "";
        if (sessionID > 0L)
        {
          ret.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"check_user\">\n<RESULT>\n<RETCODE>");
          ret
            .append("0</RETCODE>\n<MESSAGE>success</MESSAGE>\n<MODULE_ID>");
          ret.append(this.MODELID);
          ret.append("</MODULE_ID>\n</RESULT>\n</REP>\n</GNNT>\n");
          out.print(ret.toString());
          
          return;
        }
        if (sessionID == -1L) {
          message = "交易员代码不存在！";
        } else if (sessionID == -2L) {
          message = "口令不正确！";
        } else if (sessionID == -3L) {
          message = "禁止登陆！";
        } else if (sessionID == -4L) {
          message = "Key盘验证错误！";
        } else if (sessionID == -5L) {
          message = "交易板块被禁止！";
        } else if (sessionID == -10L) {
          message = "交易代码为空！";
        } else if (sessionID == -11L) {
          message = "sessionID 不能小于或者等于0！";
        } else {
          message = "其他异常！";
        }
        ret.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n<GNNT>\n<REP name=\"check_user\">\n<RESULT>\n<RETCODE>");
        ret.append(sessionID + "</RETCODE>\n<MESSAGE>");
        ret.append(message);
        ret.append("</MESSAGE>\n<MODULE_ID>");
        ret.append(this.MODELID);
        ret.append("</MODULE_ID>\n</RESULT>\n</REP>\n</GNNT>\n");
        out.print(ret.toString());
        System.out.println("servlet==============>return:" + 
          ret.toString());
        return;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doGet(request, response);
  }
  
  public void init(ServletConfig config)
    throws ServletException
  {
    this.MODELID = config.getInitParameter("MODELID");
  }
  
  private Document parserStrtoDocument(String str)
    throws DocumentException
  {
    Document document = null;
    try
    {
      document = DocumentHelper.parseText(str);
    }
    catch (Exception localException) {}
    return document;
  }
  
  private String getXmlType(String type, Document document)
  {
    String result = null;
    try
    {
      Element root = document.getRootElement();
      Element node = root.element("REQ");
      result = node.attributeValue(type);
    }
    catch (Exception localException) {}
    return result;
  }
  
  private String getXmlParament(String xml, String param)
    throws Exception
  {
    xml.replaceAll("\n", "");
    if (xml == null) {
      return "";
    }
    String regBuffer = "<" + param + ">(.*)" + "</" + param + ">";
    Pattern p = Pattern.compile(regBuffer);
    
    Matcher m = p.matcher(xml);
    boolean result = m.find();
    String ret = "";
    if (result)
    {
      ret = m.group();
      ret = ret.replaceAll("<" + param + ">", "");
      ret = ret.replaceAll("</" + param + ">", "");
      System.out.println(m.group());
    }
    return ret;
  }
  
  private long toLong(String s)
  {
    long r = -1L;
    try
    {
      r = Long.parseLong(s);
    }
    catch (Exception localException) {}
    return r;
  }
  
  public static long checkUser(String username, long auSessionId, HttpServletRequest request, HttpServletResponse response)
  {
    LogonManager manager = LogonManager.getInstance();
    if ((username == null) || (username.length() == 0)) {
      return -10L;
    }
    if (auSessionId <= 0L) {
      return -11L;
    }
    TraderInfo traderInfo = manager.remoteLogon(username, "2", auSessionId, 
      request.getRemoteAddr());
    
    long sessionID = traderInfo.auSessionId;
    if (sessionID > 0L)
    {
      request.getSession().setAttribute("FIRMID", traderInfo.firmId);
      request.getSession().setAttribute("LOGINID", new Long(sessionID));
      request.getSession().setAttribute("username", username);
      request.getSession().setMaxInactiveInterval(60000);
      
      addCookie(response, "username", username, 31536000);
      addCookie(response, "LOGINID", sessionID, 31536000);
    }
    return sessionID;
  }
  
  public static long checkUser(long auSessionId, HttpServletRequest request, HttpServletResponse response)
  {
    String userName = getCookieByName(request, "username");
    return checkUser(userName, auSessionId, request, response);
  }
  
  public static long checkUser(HttpServletRequest request, HttpServletResponse response)
  {
    String userName = getCookieByName(request, "username");
    String sessionID = getCookieByName(request, "LOGINID");
    return checkUser(userName, Long.parseLong(sessionID), request, response);
  }
  
  public static void addCookie(HttpServletResponse response, String name, String value, int maxAge)
  {
    Cookie cookie = new Cookie(name, value);
    
    cookie.setPath("/");
    if (maxAge > 0) {
      cookie.setMaxAge(maxAge);
    }
    response.addCookie(cookie);
  }
  
  public static String getCookieByName(HttpServletRequest request, String name)
  {
    Map<String, Cookie> cookieMap = ReadCookieMap(request);
    if (cookieMap.containsKey(name))
    {
      Cookie cookie = (Cookie)cookieMap.get(name);
      return cookie.getValue();
    }
    return null;
  }
  
  private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request)
  {
    Map<String, Cookie> cookieMap = new HashMap();
    
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        cookieMap.put(cookie.getName(), cookie);
      }
    }
    return cookieMap;
  }
}
