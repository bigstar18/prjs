package org.hxx.util;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.CharacterEncodingFilter;

import org.hxx.aut.web.AutServlet;

//nohup java -Xms256m -Xmx1g -XX:MaxPermSize=256M -Dfile.encoding=UTF-8 -Duser.language=zh -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:-CMSConcurrentMTEnabled -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -jar aut.jar >autsrv.log 2>&1 &

/**
 * Main application class
 */
public class AutMain {
	private final static Logger LOG = LoggerFactory.getLogger(AutMain.class);

	/**
	 * Main entry point to application. Sets up the resources and launches the
	 * Jetty server.
	 * 
	 * @param args
	 *            The command line arguments.
	 * @throws Exception
	 *             When there is an issue launching the application.
	 */
	public static void main(String[] args) {
		int port = parsePort(args);

		LOG.info("AutMain Web server setup.");
		// create server and configure basic settings
		Server server = new Server();
		server.setStopAtShutdown(true);
		// set up connector
		Connector connector = new SelectChannelConnector();
		connector.setPort(port);
		// connector.setHost("127.0.0.1");
		server.addConnector(connector);

		ServletContextHandler servletCtx = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		servletCtx.setContextPath("/aut");
		servletCtx.addServlet(new ServletHolder(new AutServlet()), "/*");
		FilterHolder filterHolder = servletCtx.addFilter(
				CharacterEncodingFilter.class, "/*",
				EnumSet.of(DispatcherType.REQUEST));
		filterHolder.setInitParameter("encoding", "UTF-8");
		filterHolder.setInitParameter("forceEncoding", "true");

		WebAppContext webappCtx = new WebAppContext();
		webappCtx.setContextPath("/event");
		webappCtx.setWar("./event.war");
		// webappCtx.setWar("./target/event.war");
		webappCtx.setInitParameter(
				"org.eclipse.jetty.servlet.Default.dirAllowed", "false");

		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { servletCtx, webappCtx });
		server.setHandler(contexts);
		// start the server
		try {
			server.start();
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private static int parsePort(String[] args) {
		// set up command line options
		Options options = new Options();
		options.addOption("p", "port", true, "Port to bind to [default: 8080]");
		// parse command line parameters
		CommandLine commandLine = null;
		try {
			commandLine = new PosixParser().parse(options, args);
		} catch (org.apache.commons.cli.ParseException e) {
			LOG.error("Could not parse command line args: ", e);
			printUsageAndExit(options, -1);
		}

		int port = 8080;
		// user provided value precedes config value
		if (commandLine != null && commandLine.hasOption("port")) {
			String val = commandLine.getOptionValue("port");
			// get port to bind to
			port = Integer.parseInt(val);
			LOG.debug("Port set to: " + val);
		}

		return port;
	}

	/**
	 * Helper method to print out the command line arguments available.
	 * 
	 * @param options
	 *            The command line argument definition.
	 * @param exitCode
	 *            The exit code to use when exiting the application.
	 */
	private static void printUsageAndExit(Options options, int exitCode) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("AutMain", options, true);
		System.exit(exitCode);
	}

}
