package gnnt.mebsv.hqservice.dao.factory;

import gnnt.mebsv.hqservice.dao.HQDAO;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Properties;

public class HQDAOFactory
{
  private static String DBMS = "gnnt.mebsv.hqservice.dao.impl.HQDAO";

  public static HQDAO getDAO(Properties paramProperties)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
  {
    HQDAO localHQDAO = null;
    String str = DBMS + paramProperties.getProperty("DBType").trim();
    System.out.print(str);
    localHQDAO = (HQDAO)Class.forName(str).newInstance();
    localHQDAO.params = paramProperties;
    localHQDAO.getConn();
    return localHQDAO;
  }
}