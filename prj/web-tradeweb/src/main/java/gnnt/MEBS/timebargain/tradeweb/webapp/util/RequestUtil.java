package gnnt.MEBS.timebargain.tradeweb.webapp.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestUtil {
	private static final String STOWED_REQUEST_ATTRIBS = "ssl.redirect.attrib.stowed";
	private static transient Log log = LogFactory.getLog(RequestUtil.class);

	public static String getRequestParameters(HttpServletRequest paramHttpServletRequest) {
		Map localMap = paramHttpServletRequest.getParameterMap();
		return createQueryStringFromMap(localMap, "&").toString();
	}

	public static StringBuffer createQueryStringFromMap(Map paramMap, String paramString) {
		StringBuffer localStringBuffer = new StringBuffer("");
		Set localSet = paramMap.entrySet();
		Iterator localIterator = localSet.iterator();
		while (localIterator.hasNext()) {
			Map.Entry localEntry = (Map.Entry) localIterator.next();
			Object localObject = localEntry.getValue();
			if (localObject == null) {
				append(localEntry.getKey(), "", localStringBuffer, paramString);
			} else if ((localObject instanceof String)) {
				append(localEntry.getKey(), localObject, localStringBuffer, paramString);
			} else if ((localObject instanceof String[])) {
				String[] arrayOfString = (String[]) localObject;
				for (int i = 0; i < arrayOfString.length; i++) {
					append(localEntry.getKey(), arrayOfString[i], localStringBuffer, paramString);
				}
			} else {
				append(localEntry.getKey(), localObject, localStringBuffer, paramString);
			}
		}
		return localStringBuffer;
	}

	private static StringBuffer append(Object paramObject1, Object paramObject2, StringBuffer paramStringBuffer, String paramString) {
		if (paramStringBuffer.length() > 0) {
			paramStringBuffer.append(paramString);
		}
		try {
			paramStringBuffer.append(URLEncoder.encode(paramObject1.toString(), "UTF-8"));
			paramStringBuffer.append("=");
			paramStringBuffer.append(URLEncoder.encode(paramObject2.toString(), "UTF-8"));
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}
		return paramStringBuffer;
	}

	public static void stowRequestAttributes(HttpServletRequest paramHttpServletRequest) {
		if (paramHttpServletRequest.getSession().getAttribute("ssl.redirect.attrib.stowed") != null) {
			return;
		}
		Enumeration localEnumeration = paramHttpServletRequest.getAttributeNames();
		HashMap localHashMap = new HashMap();
		while (localEnumeration.hasMoreElements()) {
			String str = (String) localEnumeration.nextElement();
			localHashMap.put(str, paramHttpServletRequest.getAttribute(str));
		}
		paramHttpServletRequest.getSession().setAttribute("ssl.redirect.attrib.stowed", localHashMap);
	}

	public static void reclaimRequestAttributes(HttpServletRequest paramHttpServletRequest) {
		Map localMap = (Map) paramHttpServletRequest.getSession().getAttribute("ssl.redirect.attrib.stowed");
		if (localMap == null) {
			return;
		}
		Iterator localIterator = localMap.keySet().iterator();
		while (localIterator.hasNext()) {
			String str = (String) localIterator.next();
			paramHttpServletRequest.setAttribute(str, localMap.get(str));
		}
		paramHttpServletRequest.getSession().removeAttribute("ssl.redirect.attrib.stowed");
	}

	public static void setCookie(HttpServletResponse paramHttpServletResponse, String paramString1, String paramString2, String paramString3) {
		if (log.isDebugEnabled()) {
			log.debug("Setting cookie '" + paramString1 + "' on path '" + paramString3 + "'");
		}
		Cookie localCookie = new Cookie(paramString1, paramString2);
		localCookie.setSecure(false);
		localCookie.setPath(paramString3);
		localCookie.setMaxAge(2592000);
		paramHttpServletResponse.addCookie(localCookie);
	}

	public static Cookie getCookie(HttpServletRequest paramHttpServletRequest, String paramString) {
		Cookie[] arrayOfCookie = paramHttpServletRequest.getCookies();
		Cookie localObject = null;
		if (arrayOfCookie == null) {
			return localObject;
		}
		for (int i = 0; i < arrayOfCookie.length; i++) {
			Cookie localCookie = arrayOfCookie[i];
			if ((localCookie.getName().equals(paramString)) && (!localCookie.getValue().equals(""))) {
				localObject = localCookie;
				break;
			}
		}
		return localObject;
	}

	public static void deleteCookie(HttpServletResponse paramHttpServletResponse, Cookie paramCookie, String paramString) {
		if (paramCookie != null) {
			paramCookie.setMaxAge(0);
			paramCookie.setPath(paramString);
			paramHttpServletResponse.addCookie(paramCookie);
		}
	}

	public static String getAppURL(HttpServletRequest paramHttpServletRequest) {
		StringBuffer localStringBuffer = new StringBuffer();
		int i = paramHttpServletRequest.getServerPort();
		if (i < 0) {
			i = 80;
		}
		String str = paramHttpServletRequest.getScheme();
		localStringBuffer.append(str);
		localStringBuffer.append("://");
		localStringBuffer.append(paramHttpServletRequest.getServerName());
		if (((str.equals("http")) && (i != 80)) || ((str.equals("https")) && (i != 443))) {
			localStringBuffer.append(':');
			localStringBuffer.append(i);
		}
		localStringBuffer.append(paramHttpServletRequest.getContextPath());
		return localStringBuffer.toString();
	}
}
