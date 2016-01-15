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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContextCommonSecurityFilter implements Filter {

	private transient Log a;
	private FilterConfig b;
	private String c;
	private String d;
	private List e;
	private List f;

	public ContextCommonSecurityFilter() {
		a = LogFactory.getLog(getClass());
		b = null;
		c = null;
		d = null;
		e = new ArrayList();
		f = new ArrayList();
	}

	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {

		Object obj = ((HttpServletRequest) servletrequest).getQueryString();
		HttpServletRequest httpservletrequest = (HttpServletRequest) servletrequest;
		String s1 = ((String) (obj));
		obj = this;
		if (!gnnt.MEBS.security.a.a.b(s1)) {
			((ContextCommonSecurityFilter) (obj)).a
					.error((new StringBuilder("=====================>>可疑IP地址：\n")).append(httpservletrequest.getRemoteAddr()).append(":")
							.append(httpservletrequest.getRemotePort()).append("　访问的URL:").append(httpservletrequest.getRequestURI()).toString());
			((ContextCommonSecurityFilter) (obj)).a.error((new StringBuilder("=====================>>申请struts_value:")).append(s1).toString());
			return;
		}
		String s = ((HttpServletRequest) servletrequest).getRequestURI();
		for (Iterator iterator = e.iterator(); iterator.hasNext();) {
			String s2 = (String) iterator.next();
			if (s.endsWith(s2)) {
				filterchain.doFilter(servletrequest, servletresponse);
				return;
			}
		}

		for (Iterator iterator1 = f.iterator(); iterator1.hasNext();) {
			String s3 = (String) iterator1.next();
			if (s.equalsIgnoreCase(s3)) {
				filterchain.doFilter(servletrequest, servletresponse);
				return;
			}
		}

		Object obj1;
		for (obj1 = ((Set) (obj1 = ((Map) (obj1 = ((HttpServletRequest) servletrequest).getParameterMap())).entrySet()))
				.iterator(); ((Iterator) (obj1)).hasNext();) {
			java.util.Map.Entry entry;
			String s4 = (String) (entry = (java.util.Map.Entry) ((Iterator) (obj1)).next()).getKey();
			if (!a(s4, (HttpServletRequest) servletrequest))
				return;
			String as[] = (String[]) entry.getValue();
			for (int i = 0; i < as.length; i++)
				if (!a(as[i], (HttpServletRequest) servletrequest))
					return;

		}

		filterchain.doFilter(servletrequest, servletresponse);
	}

	private boolean a(String s, HttpServletRequest httpservletrequest) {
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
}
