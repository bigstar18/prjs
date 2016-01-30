package gnnt.trade.bank.util;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InterfeceStartUp extends HttpServlet
{
	public static final long serialVersionUID=1000000000l;
	/**
	 * Constructor of the object.
	 */
	private transient final Log logger = LogFactory.getLog(InterfeceStartUp.class);
	public InterfeceStartUp() 
	{
		super();
		System.out.println("---------开始加载AU-------------");
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}
	//	 Initialize global variables
	public void init() throws ServletException 
	{
		try
		{
			BankDAO dao= new BankDAOImpl();
			logger.debug("---------初始化AU--------");
			LogonManager.createInstance("5", dao.getDataSource());
			logger.debug("---------AU初始化完成--------");
			ErrorCode ec = new ErrorCode();
			ec.load();
			logger.debug("---------错误码加载成功--------");
//			System.out.println("---------初始化银行处理器远程调用接口--------");
//			CapitalProcessorRMI rmi = RMIVO.getInstance();
//			if(rmi!=null) 
//			{
//				System.out.println("-----初始化银行处理器远程调用接口成功!!!!!!---------");
//			}
//			else
//			{
//				System.out.println("-----初始化银行处理器远程调用接口失败!!!!!!---------");
//			}
		} 
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
}
