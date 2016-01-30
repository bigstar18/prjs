package gnnt.MEBS.xhzc.manage.DAO;

import gnnt.MEBS.util.Configuration;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;

public class ManageDAOFactory
{
  private static String DBMS = "gnnt.MEBS.xhzc.manage.DAO.ManageDAO";

  public static ManageDAO getDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, NamingException
  {
    ManageDAO dao = null;
    Properties params = new Configuration().getSection("MEBS.Database");
    String className = DBMS + params.getProperty("DBType").trim();
    dao = (ManageDAO)Class.forName(className).newInstance();
    return dao;
  }
}