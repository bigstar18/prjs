package gnnt.bank.platform.dao;

import gnnt.bank.platform.util.Tool;

public class BankDAOFactory
{
  public static BankDAO getDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    BankDAO dao = null;
    String className = "gnnt.bank.platform.dao.BankDAO" + Tool.getConfig("DBType").trim();
    dao = (BankDAO)Class.forName(className).newInstance();
    return dao;
  }
}
