package gnnt.bank.complex.dao;

import gnnt.bank.platform.util.Configuration;
import java.util.Properties;

public class BankDAOFactory
{
  public static BankDAO getDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    BankDAO dao = null;
    Properties params = new Configuration().getSection("BANK.Processor");
    String className = "gnnt.bank.complex.dao.BankDAO" + params.getProperty("DBType").trim();
    dao = (BankDAO)Class.forName(className).newInstance();
    return dao;
  }
}
