package gnnt.trade.bank.util;
import gnnt.trade.bank.processorrmi.server.CapitalProcessorRMIServer;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
/**
 * 启动rmi服务
 * @author 薛计涛
 */
public class StartupRmiserver extends HttpServlet{
	public static final long serialVersionUID=1000000000l;
	/**
	 * Constructor of the object.
	 */
	public StartupRmiserver() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
	}
	public void init() throws ServletException {
		try {
			CapitalProcessorRMIServer rmiS=new CapitalProcessorRMIServer();
			Properties props = new Configuration().getSection(ProcConstants.xmlName);
			String RmiIpAddress=props.getProperty(ProcConstants.procAdress);
			String RmiPortNumber=props.getProperty(ProcConstants.procPort);
			String RmiServiceName=props.getProperty(ProcConstants.procServiceName);
			int port=Integer.parseInt(RmiPortNumber);
			rmiS.startRMI(RmiIpAddress, port, RmiServiceName);
			System.out.println("处理器RMI服务启动完成~");
		} catch (Exception e) {
			System.out.println("RMI启动发生异常，异常信息如下：");
			e.printStackTrace();
		}
	}
}
