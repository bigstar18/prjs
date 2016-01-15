// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi

package gnnt.MEBS.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.filters.FilterBase;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class TomcatCommonSecurityFilter extends FilterBase {

	private static final Log a = LogFactory.getLog(TomcatCommonSecurityFilter.class);
	private FilterConfig b;
	private String c;
	private String d;
	private List e;
	private List f;

	public TomcatCommonSecurityFilter() {
		b = null;
		c = null;
		d = null;
		e = new ArrayList();
		f = new ArrayList();
	}

	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {
		String s5;
		String s;
		HttpServletRequest httpservletrequest;
		s5 = s = ((HttpServletRequest) servletrequest).getQueryString();
		httpservletrequest = (HttpServletRequest) servletrequest;
		s = s5;
		if (!gnnt.MEBS.security.a.a.b(s5)) {
			a.error((new StringBuilder("=====================>>可疑IP地址：\n")).append(httpservletrequest.getRemoteAddr()).append(":")
					.append(httpservletrequest.getRemotePort()).append("　访问的URL:").append(httpservletrequest.getRequestURI()).toString());
			a.error((new StringBuilder("=====================>>申请struts_value:")).append(s).toString());
			return;
		}

		String s1 = ((HttpServletRequest) servletrequest).getRequestURI();
		for (Iterator iterator = e.iterator(); iterator.hasNext();) {
			String s2 = (String) iterator.next();
			if (s1.endsWith(s2)) {
				filterchain.doFilter(servletrequest, servletresponse);
				return;
			}
		}

		for (Iterator iterator1 = f.iterator(); iterator1.hasNext();) {
			String s3 = (String) iterator1.next();
			if (s1.equalsIgnoreCase(s3)) {
				filterchain.doFilter(servletrequest, servletresponse);
				return;
			}
		}

		Object obj;
		for (obj = ((Set) (obj = ((Map) (obj = ((HttpServletRequest) servletrequest).getParameterMap())).entrySet())).iterator(); ((Iterator) (obj))
				.hasNext();) {
			java.util.Map.Entry entry;
			String s4;
			if (!a(s4 = (String) (entry = (java.util.Map.Entry) ((Iterator) (obj)).next()).getKey(), (HttpServletRequest) servletrequest))
				return;
			String as[] = (String[]) entry.getValue();
			for (int i = 0; i < as.length; i++)
				if (!a(as[i], (HttpServletRequest) servletrequest))
					return;

		}

		filterchain.doFilter(servletrequest, servletresponse);
	}

	private static boolean a(String s, HttpServletRequest httpservletrequest) {
		if (!gnnt.MEBS.security.a.a.a(s)) {
			a.error((new StringBuilder("=====================>>可疑IP地址：\n")).append(httpservletrequest.getRemoteAddr()).append(":")
					.append(httpservletrequest.getRemotePort()).append("　访问的URL:").append(httpservletrequest.getRequestURI()).toString());
			a.error((new StringBuilder("=====================>>申请sql_value:")).append(s).toString());
			return false;
		} else {
			return true;
		}
	}

	public void init(FilterConfig filterconfig) {
		a.info("========================启动TomcatCommonSecurityFilter=================================");
		b = filterconfig;
		c = b.getInitParameter("IGNORE_EXTS");
		String[] NULL = null;
		if (c != null) {
			NULL = c.split(",");
			for (int i = 0; i < NULL.length; i++)
				e.add(NULL[i]);
		}
		d = b.getInitParameter("IGNORE_URIS");
		if (d != null) {
			NULL = d.split(",");
			for (int j = 0; j < NULL.length; j++)
				f.add(NULL[j]);

		}
	}

	public void destroy() {
	}

	protected Log getLogger() {
		return a;
	}

}
