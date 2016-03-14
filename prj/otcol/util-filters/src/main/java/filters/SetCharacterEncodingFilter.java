package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SetCharacterEncodingFilter implements Filter {
	protected String encoding;
	protected FilterConfig filterConfig;
	protected boolean ignore;

	public SetCharacterEncodingFilter() {
		this.encoding = null;
		this.filterConfig = null;
		this.ignore = true;
	}

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {
		if ((this.ignore) || (servletrequest.getCharacterEncoding() == null)) {
			String s = selectEncoding(servletrequest);
			if (s != null) {
				servletrequest.setCharacterEncoding(s);
			}
		}
		filterchain.doFilter(servletrequest, servletresponse);
	}

	public void init(FilterConfig filterconfig) throws ServletException {
		this.filterConfig = filterconfig;
		this.encoding = filterconfig.getInitParameter("encoding");
		String s = filterconfig.getInitParameter("ignore");
		if (s == null) {
			this.ignore = true;
		} else if (s.equalsIgnoreCase("true")) {
			this.ignore = true;
		} else if (s.equalsIgnoreCase("yes")) {
			this.ignore = true;
		} else {
			this.ignore = false;
		}
	}

	protected String selectEncoding(ServletRequest servletrequest) {
		return this.encoding;
	}
}
