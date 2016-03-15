package cn.com.agree.eteller.generic.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil
{
  public static Cookie saveCookie(String name, String value, int age, HttpServletRequest req, HttpServletResponse resp)
  {
    Cookie c = null;
    try
    {
      if (value != null) {
        value = URLEncoder.encode(value, "UTF-8");
      }
      c = new Cookie(name, value);
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    c.setMaxAge(age);
    c.setPath(req.getContextPath());
    resp.addCookie(c);
    
    return c;
  }
  
  public static Cookie findCookie(String name, HttpServletRequest req)
  {
    Cookie[] cookies = req.getCookies();
    if (cookies == null) {
      return null;
    }
    for (Cookie c : cookies)
    {
      String cookieName = null;
      try
      {
        cookieName = URLDecoder.decode(c.getName(), "UTF-8");
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
      if (cookieName.equals(name)) {
        return c;
      }
    }
    return null;
  }
  
  public static void deleteCookie(String name, HttpServletRequest req, HttpServletResponse resp)
  {
    saveCookie(name, null, 0, req, resp);
  }
}
