package gnnt.mebsv.hqservice.dao.rmidao;

import gnnt.mebsv.hqservice.dao.rmidao.impl.HQRMIDAOJdbc;
import java.sql.SQLException;
import java.util.Properties;

public class HQRMIDAOFactory
{
  public static HQRMIDAO getDAO(Properties paramProperties)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
  {
    HQRMIDAOJdbc localHQRMIDAOJdbc = null;
    localHQRMIDAOJdbc = new HQRMIDAOJdbc();
    localHQRMIDAOJdbc.params = paramProperties;
    localHQRMIDAOJdbc.getConn();
    return localHQRMIDAOJdbc;
  }
}