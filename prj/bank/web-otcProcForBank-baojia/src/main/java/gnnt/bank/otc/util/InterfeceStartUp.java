package gnnt.bank.otc.util;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.bank.otc.dao.BankDAO;
import gnnt.bank.otc.dao.BankDAOImpl;
import gnnt.bank.platform.util.ErrorCode;
import java.io.PrintStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InterfeceStartUp
  extends HttpServlet
{
  public static final long serialVersionUID = 1000000000L;
  
  public InterfeceStartUp()
  {
    System.out.println("---------开始加载AU-------------");
  }
  
  public void destroy()
  {
    super.destroy();
  }
  
  public void init()
    throws ServletException
  {
    try
    {
      System.out.println("进入 " + getClass().getName() + " 的 init 方法");
      BankDAO dao = new BankDAOImpl();
      System.out.println("---------初始化AU--------");
      LogonManager.createInstance("5", dao.getDataSource());
      System.out.println("---------AU初始化完成--------");
      ErrorCode ec = new ErrorCode();
      ec.load();
      System.out.println("---------错误码加载成功--------");
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
  }
}
