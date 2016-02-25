package gnnt.bank.otc.dao;

public class BankDAOFactory
{
  public static BankDAO getDAO()
    throws ClassNotFoundException, IllegalAccessException, InstantiationException
  {
    BankDAO dao = null;
    String className = "gnnt.bank.otc.dao.BankDAOOracle";
    dao = (BankDAO)Class.forName(className).newInstance();
    return dao;
  }
}
