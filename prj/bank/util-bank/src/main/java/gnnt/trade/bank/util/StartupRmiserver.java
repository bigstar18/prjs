package gnnt.trade.bank.util;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.processorrmi.server.CapitalProcessorRMIServer;
import gnnt.trade.bank.vo.Trademodule;

import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
/**
 * 启动rmi服务
 * @author 薛计涛
 */
public class StartupRmiserver extends HttpServlet
{
	public static final long serialVersionUID=1000000000l;
	/**
	 * Constructor of the object.
	 */
	public StartupRmiserver() 
	{
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
	}
	public void init() throws ServletException 
	{
		try
		{
			CapitalProcessorRMIServer rmiS=new CapitalProcessorRMIServer();
			Properties props = new Configuration().getSection("BANK.Processor");
			String RmiIpAddress=props.getProperty("RmiIpAddress");
			String RmiPortNumber=props.getProperty("RmiPortNumber");
			String RmiServiceName=props.getProperty("RmiServiceName");
			int port=Integer.parseInt(RmiPortNumber);
			int dataPort = -1;

			//从数据库中获取 RMI 的配置信息
			int moduleID = 28;
			try{
				moduleID = Integer.parseInt(props.getProperty("SelfModuleID"));
			}catch(Exception e){
				System.out.println("获取配置文件中配置的银行接口模块编号失败，使用默认编号：28");
			}
			BankDAO DAO = BankDAOFactory.getDAO();
			Trademodule trademodule = DAO.getTrademodule(moduleID);
			if(trademodule == null){
				System.out.println("从数据库中获取模块 "+moduleID+" 信息失败，使用配置文件中的配置信息启动处理器 RMI 服务");
			}else{
				if(trademodule.hostip == null || trademodule.hostip.trim().length()<=0){
					System.out.println("从数据库中获取模块 "+moduleID+" 信息，配置的处理器 IP 地址为空，使用配置文件中的配置信息");
				}else{
					RmiIpAddress = trademodule.hostip.trim();
				}
				if(trademodule.port<=0){
					System.out.println("从数据库中获取模块 "+moduleID+" 信息，配置的处理器 RMI 端口错误，使用配置文件中的配置信息");
				}else{
					port = trademodule.port;
				}
				if(trademodule.rmiDataport<=0){
					System.out.println("从数据库中获取模块 "+moduleID+" 信息，配置的处理器 RMI 数据传输端口错误，使用默认数据传输端口");
				}else{
					dataPort = trademodule.rmiDataport;
				}
			}

			rmiS.startRMI(RmiIpAddress, port,dataPort, RmiServiceName);
			System.out.println("处理器RMI服务启动完成~");
		}
		catch (Exception e) 
		{
			System.out.println("RMI启动发生异常，异常信息如下：");
			e.printStackTrace();
		}
	}
}
