
package gnnt.MEBS.logonServerUtil.au;

import gnnt.MEBS.logonService.dao.LogonManagerDAO;
import gnnt.MEBS.logonService.kernel.impl.LogonService_Standard;
import gnnt.MEBS.logonService.po.LogonConfigPO;
import gnnt.MEBS.logonService.vo.LogonTimeOutThreadVO;
import gnnt.MEBS.logonService.vo.RemoteLogonServerVO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <P>类说明：AU 连接管理类
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-24上午09:00:31|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class AUConnectManager {
	/** 日志属性 */
	private transient final Log logger = LogFactory.getLog(this.getClass());

	/** 自己的 AU 编号 */
	private int selfConfigID;

	/** 本身实例 */
	private volatile static AUConnectManager instance;

	/** AU 服务集合 */
	private Map<Integer,RemoteLogonServerVO> logonManagerMap = new HashMap<Integer,RemoteLogonServerVO>();

	/**
	 * 
	 * 构造方法
	 * <br/><br/>
	 */
	private AUConnectManager(){}

	/**
	 * 
	 * 获取本例实例
	 * <br/><br/>
	 * @return
	 */
	public static AUConnectManager getInstance(){
		if(instance == null){
			synchronized(AUConnectManager.class){
				if(instance == null){
					instance = new AUConnectManager();
				}
			}
		}
		return instance;
	}

	/**
	 * 
	 * 初始化 AU 信息
	 * <br/><br/>
	 * @param selfConfigID 本 AU 编号
	 * @param callMode 访问 AU 方式。0 rmi 访问登录管理；1 本地访问，并启动 RMI 服务
	 * @param dataSource
	 * @param auExpireTimeMap 用户登录超时线程中超时时间配置，以毫秒为单位
	 * @param timeSpace 用户超时线程睡眠时间
	 * @param clearRMITimes 重置 RMI 连接次数后重新到数据库中获取连接地址的次数
	 * @throws Exception 
	 */
	public void init(int selfConfigID,int callMode,DataSource dataSource,Map<String,Long> auExpireTimeMap,long timeSpace,int clearRMITimes) throws Exception{
		if(callMode != 0 && callMode != 1){
			throw new IllegalArgumentException("输入访问 AU 方式["+callMode+"]不合法。0：rmi 访问登录管理；1：本地访问，并启动 RMI 服务");
		}

		logger.info("初始化数据库连接.......");

		this.selfConfigID = selfConfigID;

		//生成 DAO 实例
		LogonManagerDAO logonManagerDAO = new LogonManagerDAO();
		logonManagerDAO.setDataSource(dataSource);

		logger.info("初始化配置 AU 信息......");

		LogonConfigPO logonConfigPO = logonManagerDAO.getLogonConfigByID(selfConfigID);

		if(logonConfigPO == null){
			throw new IllegalArgumentException("通过编号 "+selfConfigID+"未查询到 AU 配置信息");
		}

		RemoteLogonServerVO logonManager = new RemoteLogonServerVO();
		logonManager.setLogonConfigPO(logonConfigPO);

		//AU RMI 连接Map
		logonManagerMap.put(selfConfigID, logonManager);

		//如果是本地启动，并且当个配置编号为本服务传入的配置编号，则启动 RMI 服务
		if(callMode == 1){
			LogonService_Standard logonService = new LogonService_Standard();

			LogonTimeOutThreadVO logonTimeOutThreadVO = new LogonTimeOutThreadVO();
			logonTimeOutThreadVO.setTimeSpace(200);
			logonTimeOutThreadVO.setAuExpireTimeMap(auExpireTimeMap);

			logonService.init(selfConfigID, clearRMITimes, logonTimeOutThreadVO, dataSource);

			logonManager.setRmiService(logonService);

			startRMI(logonService);
		}
	}

	/**
	 * 
	 * 获取本 AU 连接信息
	 * <br/><br/>
	 * @return
	 */
	public RemoteLogonServerVO getRemoteLogonServerVO(){
		return logonManagerMap.get(this.selfConfigID);
	}

	/**
	 * 
	 * 获取对应 AU 连接信息
	 * <br/><br/>
	 * @param configID AU 编号
	 * @return
	 */
	public RemoteLogonServerVO getRemoteLogonServerVO(int configID){
		if(logonManagerMap.get(configID) == null){
			synchronized(this.getClass()){
				LogonConfigPO logonConfigPO = LogonActualize.getInstance().getlogonManagerDAO().getLogonConfigByID(configID);
				if(logonConfigPO != null){
					RemoteLogonServerVO logonManager = new RemoteLogonServerVO();
					logonManager.setLogonConfigPO(logonConfigPO);
					//AU RMI 连接Map
					logonManagerMap.put(configID, logonManager);
				}
			}
		}
		return logonManagerMap.get(configID);
	}

	/**
	 * 
	 * 启动 RMI 服务
	 * <br/><br/>
	 * @param logonConfigPO
	 * @throws Exception 
	 */
	private void startRMI(LogonService_Standard logonService) throws Exception{
		if(System.getSecurityManager() != null){
			System.setSecurityManager(new RMISecurityManager());
		}

		LogonConfigPO logonConfigPO = logonService.getSelfLogonConfigPO();

		if(logonConfigPO.getDataPort() != null && logonConfigPO.getDataPort() > 0
				&& RMISocketFactory.getSocketFactory() == null ){
			//定义数据传输端口
			RMISocketFactory.setSocketFactory(new SMRMISocket(logonConfigPO.getDataPort()));
		}

		Registry r = LocateRegistry.getRegistry(logonConfigPO.getPort());
		if(r != null){
			r = LocateRegistry.createRegistry(logonConfigPO.getPort());
		}

		StringBuilder sb = new StringBuilder();
		sb.append("rmi://").append(logonConfigPO.getHostIP()).append(":").append(logonConfigPO.getPort()).append("/").append(logonConfigPO.getServiceName()).append(RemoteLogonServerVO.serviceEnd);
		logger.info("启动 RMI 服务："+sb.toString());
		Naming.rebind(sb.toString(), logonService);
	}

	/**
	 * 数据传输端口设置类
	 */
	private class SMRMISocket extends RMISocketFactory{
		/** 数据传输端口 */
		private int dataPort = 1099;

		/**
		 * 
		 * 构造方法
		 * <br/><br/>
		 * @param dataPort 数据传输端口
		 */
		public SMRMISocket(int dataPort){
			this.dataPort = dataPort;
		}

		@Override
		public ServerSocket createServerSocket(int port) throws IOException {
			if(port<=0){
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

