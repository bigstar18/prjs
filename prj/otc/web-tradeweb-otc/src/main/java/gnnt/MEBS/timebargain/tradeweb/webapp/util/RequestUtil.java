package gnnt.MEBS.timebargain.tradeweb.webapp.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestUtil
{
  private static final String STOWED_REQUEST_ATTRIBS = "ssl.redirect.attrib.stowed";
  private static transient Log log = LogFactory.getLog(RequestUtil.class);
  
  public static String getRequestParameters(HttpServletRequest aRequest)
  {
    Map m = aRequest.getParameterMap();
    
    return createQueryStringFromMap(m, "&").toString();
  }
  
  public static StringBuffer createQueryStringFromMap(Map m, String ampersand)
  {
    StringBuffer aReturn = new StringBuffer("");
    Set aEntryS = m.entrySet();
    Iterator aEntryI = aEntryS.iterator();
    while (aEntryI.hasNext())
    {
      Map.Entry aEntry = (Map.Entry)aEntryI.next();
      Object o = aEntry.getValue();
      if (o == null)
      {
        append(aEntry.getKey(), "", aReturn, ampersand);
      }
      else if ((o instanceof String))
      {
        append(aEntry.getKey(), o, aReturn, ampersand);
      }
      else if ((o instanceof String[]))
      {
        String[] aValues = (String[])o;
        for (int i = 0; i < aValues.length; i++) {
          append(aEntry.getKey(), aValues[i], aReturn, ampersand);
        }
      }
      else
      {
        append(aEntry.getKey(), o, aReturn, ampersand);
      }
    }
    return aReturn;
  }
  
  private static StringBuffer append(Object key, Object value, StringBuffer queryString, String ampersand)
  {
    if (queryString.length() > 0) {
      queryString.append(ampersand);
    }
    try
    {
      queryString.append(URLEncoder.encode(key.toString(), "UTF-8"));
      queryString.append("=");
      queryString.append(URLEncoder.encode(value.toString(), "UTF-8"));
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
    return queryString;
  }
  
  public static void stowRequestAttributes(HttpServletRequest aRequest)
  {
    if (aRequest.getSession().getAttribute("ssl.redirect.attrib.stowed") != null) {
      return;
    }
    Enumeration e = aRequest.getAttributeNames();
    Map map = new HashMap();
    while (e.hasMoreElements())
    {
      String name = (String)e.nextElement();
      map.put(name, aRequest.getAttribute(name));
    }
    aRequest.getSession().setAttribute("ssl.redirect.attrib.stowed", map);
  }
  
  public static void reclaimRequestAttributes(HttpServletRequest aRequest)
  {
    Map map = 
      (Map)aRequest.getSession().getAttribute("ssl.redirect.attrib.stowed");
    if (map == null) {
      return;
    }
    Iterator itr = map.keySet().iterator();
    while (itr.hasNext())
    {
      String name = (String)itr.next();
      aRequest.setAttribute(name, map.get(name));
    }
    aRequest.getSession().removeAttribute("ssl.redirect.attrib.stowed");
  }
  
  public static void setCookie(HttpServletResponse response, String name, String value, String path)
  {
    if (log.isDebugEnabled()) {
      log.debug("Setting cookie '" + name + "' on path '" + path + "'");
    }
    Cookie cookie = new Cookie(name, value);
    cookie.setSecure(false);
    cookie.setPath(path);
    cookie.setMaxAge(2592000);
    
    response.addCookie(cookie);
  }
  
  public static Cookie getCookie(HttpServletRequest request, String name)
  {
    Cookie[] cookies = request.getCookies();
    Cookie returnCookie = null;
    if (cookies == null) {
      return returnCookie;
    }
    for (int i = 0; i < cookies.length; i++)
    {
      Cookie thisCookie = cookies[i];
      if (thisCookie.getName().equals(name)) {
        if (!thisCookie.getValue().equals(""))
        {
          returnCookie = thisCookie;
          
          break;
        }
      }
    }
    return returnCookie;
  }
  
  public static void deleteCookie(HttpServletResponse response, Cookie cookie, String path)
  {
    if (cookie != null)
    {
      cookie.setMaxAge(0);
      cookie.setPath(path);
      response.addCookie(cookie);
    }
  }
  
  public static String getAppURL(HttpServletRequest request)
  {
    StringBuffer url = new StringBuffer();
    int port = request.getServerPort();
    if (port < 0) {
      port = 80;
    }
    String scheme = request.getScheme();
    url.append(scheme);
    url.append("://");
    url.append(request.getServerName());
    if (((scheme.equals("http")) && (port != 80)) || ((scheme.equals("https")) && (port != 443)))
    {
      url.append(':');
      url.append(port);
    }
    url.append(request.getContextPath());
    return url.toString();
  }
}
