package gnnt.MEBS.vendue.server.servlet;

import gnnt.MEBS.vendue.server.bus.RefreshMemoryThreadManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class RunServletAtStart
  extends HttpServlet
{
  public void init()
    throws ServletException
  {
    super.init();
    gnnt.MEBS.vendue.server.dao.BaseDao.isRunWithServer = true;
    RefreshMemoryThreadManager.runRefreshThread();
  }
}
