package gnnt.trade.bank.processorrmi.servlet;
import gnnt.trade.bank.processorrmi.server.CapitalProcessorRMIServer;
import gnnt.trade.bank.util.Configuration;
import gnnt.trade.bank.util.ProcConstants;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
public class CapitalProcessorRMIServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public CapitalProcessorRMIServlet() {
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
			System.out.println("========[CapitalProcessorRMI启动成功]=============");
		} catch (Exception e) {
			System.out.println("CapitalProcessorRMI启动发生异常，异常信息如下："+e.toString());
		}
	}
}
