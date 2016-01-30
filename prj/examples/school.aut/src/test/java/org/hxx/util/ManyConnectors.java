package org.hxx.util;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

public class ManyConnectors {
	public static void main(String[] args) throws Exception {
		Server server = new Server();

		SelectChannelConnector connector0 = new SelectChannelConnector();
		connector0.setPort(8080);
		connector0.setMaxIdleTime(30000);
		connector0.setRequestHeaderSize(8192);

		SelectChannelConnector connector1 = new SelectChannelConnector();
		connector1.setHost("127.0.0.1");
		connector1.setPort(8888);
		connector1.setThreadPool(new QueuedThreadPool(20));
		connector1.setName("admin");

		SslSelectChannelConnector ssl_connector = new SslSelectChannelConnector();
		String jetty_home = System.getProperty("jetty.home",
				"../jetty-distribution/target/distribution");
		System.setProperty("jetty.home", jetty_home);
		ssl_connector.setPort(8443);
		SslContextFactory cf = ssl_connector.getSslContextFactory();
		cf.setKeyStore(jetty_home + "/etc/keystore");
		cf.setKeyStorePassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
		cf.setKeyManagerPassword("OBF:1u2u1wml1z7s1z7a1wnl1u2g");

		server.setConnectors(new Connector[] { connector0, connector1,
				ssl_connector });

		// server.setHandler(new HelloHandler());

		server.start();
		server.join();
	}

	// A Web Applications context is a variation of ServletContextHandler that
	// uses the standard layout and web.xml to configure the servlets, filters
	// and other features:
	public static void main2(String[] args) throws Exception {
		String jetty_home = System.getProperty("jetty.home", "..");

		Server server = new Server(8080);

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar(jetty_home + "/webapps/test.war");
		server.setHandler(webapp);

		server.start();
		server.join();
	}

	public class OneWebAppUnassembled {
		public void main(String[] args) throws Exception {
			Server server = new Server(8080);

			WebAppContext context = new WebAppContext();
			// context.setDescriptor(webapp + "/WEB-INF/web.xml");
			context.setResourceBase("../test-jetty-webapp/src/main/webapp");
			context.setContextPath("/");
			context.setParentLoaderPriority(true);

			server.setHandler(context);

			server.start();
			server.join();
		}
	}

	// A Context Handler Collection uses the longest prefix of the request URI
	// (the contextPath) to select a specific context. The following example
	// combines the previous two examples in a single Jetty server:
	public class ManyContexts {
		public void main(String[] args) throws Exception {
			Server server = new Server(8080);

			ServletContextHandler context0 = new ServletContextHandler(
					ServletContextHandler.SESSIONS);
			context0.setContextPath("/ctx0");
			// context0.addServlet(new ServletHolder(new HelloServlet()), "/*");
			// context0.addServlet(new ServletHolder(new HelloServlet(
			// "Buongiorno Mondo")), "/it/*");
			// context0.addServlet(new ServletHolder(new HelloServlet(
			// "Bonjour le Monde")), "/fr/*");

			WebAppContext webapp = new WebAppContext();
			webapp.setContextPath("/ctx1");
			// webapp.setWar(jetty_home + "/webapps/test.war");

			ContextHandlerCollection contexts = new ContextHandlerCollection();
			// contexts.setHandlers(new Handler[] { context0, webapp });

			server.setHandler(contexts);

			server.start();
			server.join();
		}
	}
}
