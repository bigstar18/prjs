package gnnt.MEBS.vendue.server.dao;

import gnnt.MEBS.vendue.util.Configuration;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;

public class TradeDAOFactory
{
  private static String DAONAME = "gnnt.MEBS.vendue.server.dao.TradeDAO";
  
  public static TradeDAO getDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, NamingException
  {
    TradeDAO localTradeDAO = null;
    Properties localProperties = new Configuration().getSection("MEBS.Database");
    String str = DAONAME + localProperties.getProperty("DBType").trim();
    localTradeDAO = (TradeDAO)Class.forName(str).newInstance();
    return localTradeDAO;
  }
}
