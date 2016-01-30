package gnnt.mebsv.hqtrans.dao.factory;

import gnnt.mebsv.hqtrans.dao.HQTransDAO;
import java.sql.SQLException;
import java.util.Properties;

public class HQTransDAOFactory
{
  private static final String DBMS = "gnnt.mebsv.hqtrans.dao.impl.HQTransDAOImpl";
  
  public static HQTransDAO getDAO(Properties paramProperties)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
  {
    HQTransDAO localHQTransDAO = null;
    String str = "gnnt.mebsv.hqtrans.dao.impl.HQTransDAOImpl";
    localHQTransDAO = (HQTransDAO)Class.forName(str).newInstance();
    localHQTransDAO.params = paramProperties;
    localHQTransDAO.getConn();
    return localHQTransDAO;
  }
}
