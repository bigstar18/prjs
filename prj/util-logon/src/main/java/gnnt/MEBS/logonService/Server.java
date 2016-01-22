package gnnt.MEBS.logonService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.logonService.dao.LogonManagerDAO;
import gnnt.MEBS.logonService.kernel.impl.LogonService_Standard;
import gnnt.MEBS.logonService.po.LogonConfigPO;
import gnnt.MEBS.logonService.util.GnntBeanFactory;
import gnnt.MEBS.logonService.util.Tool;
import gnnt.MEBS.logonService.vo.LogonTimeOutThreadVO;

public class Server {
	private final transient Log logger = LogFactory.getLog(getClass());
	private static volatile Server instance;

	public static Server getInstance() {
		if (instance == null)
			synchronized (Server.class) {
				if (instance == null)
					instance = (Server) GnntBeanFactory.getBean("server");
			}
		return instance;
	}

	public void initServer() throws Exception {
		startRMI();
	}

	private void startRMI() throws Exception {
		if (System.getSecurityManager() != null)
			System.setSecurityManager(new RMISecurityManager());
		int i = Tool.strToInt(GnntBeanFactory.getConfig("selfConfigID"), -1000);
		LogonManagerDAO localLogonManagerDAO = new LogonManagerDAO();
		localLogonManagerDAO.setDataSource((DataSource) GnntBeanFactory.getBean("dataSource"));
		this.logger.info("加载本 AU 信息......");
		LogonConfigPO localLogonConfigPO = localLogonManagerDAO.getLogonConfigByID(i);
		if (localLogonConfigPO == null)
			throw new IllegalArgumentException("通过编号 " + i + "未查询到 AU 配置信息");
		if ((localLogonConfigPO.getDataPort() != null) && (localLogonConfigPO.getDataPort().intValue() > 0)
				&& (RMISocketFactory.getSocketFactory() == null))
			try {
				RMISocketFactory.setSocketFactory(new SMRMISocket(localLogonConfigPO.getDataPort().intValue()));
			} catch (Exception localException) {
				this.logger.error("绑定数据传输端口时异常：", localException);
			}
		Registry localRegistry = LocateRegistry.getRegistry(localLogonConfigPO.getPort().intValue());
		if (localRegistry != null)
			localRegistry = LocateRegistry.createRegistry(localLogonConfigPO.getPort().intValue());
		LogonService_Standard localLogonService_Standard = new LogonService_Standard();
		int j = Tool.strToInt(GnntBeanFactory.getConfig("clearRMITimes"), -1);
		LogonTimeOutThreadVO localLogonTimeOutThreadVO = new LogonTimeOutThreadVO();
		localLogonTimeOutThreadVO.setTimeSpace(Tool.strToLong(GnntBeanFactory.getConfig("logonTimeOutTime"), 200L));
		localLogonTimeOutThreadVO.setAuExpireTimeMap((Map) GnntBeanFactory.getBean("auExpireTimeMap"));
		localLogonService_Standard.init(localLogonConfigPO, j, localLogonTimeOutThreadVO, (DataSource) GnntBeanFactory.getBean("dataSource"));
		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder.append("rmi://").append(localLogonConfigPO.getHostIP()).append(":").append(localLogonConfigPO.getPort()).append("/")
				.append(localLogonConfigPO.getServiceName()).append("LogonService");
		this.logger.info("启动 RMI 服务：" + localStringBuilder.toString());
		Naming.rebind(localStringBuilder.toString(), localLogonService_Standard);
	}

	private class SMRMISocket extends RMISocketFactory {
		/** 数据传输端口 */
		private int dataPort = 1099;

		/**
		 * 
		 * 构造方法
		 * <br/>
		 * <br/>
		 * 
		 * @param dataPort
		 *            数据传输端口
		 */
		public SMRMISocket(int dataPort) {
			this.dataPort = dataPort;
		}

		@Override
		public ServerSocket createServerSocket(int port) throws IOException {
			if (port <= 0) {
				port = this.dataPort;
			}
			return new ServerSocket(port);
		}

		@Override
		public Socket createSocket(String host, int port) throws IOException {
			return new Socket(host, port);
		}
	}
}