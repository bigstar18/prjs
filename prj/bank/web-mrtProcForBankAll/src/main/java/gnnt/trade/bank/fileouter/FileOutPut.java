package gnnt.trade.bank.fileouter;

import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import java.io.OutputStream;

class FileOutPut
{
  protected String operator = "E";
  protected BankDAO DAO = null;
  protected OutPuter puter = null;
  protected OutputStream os = null;
  
  protected FileOutPut(OutputStream os)
  {
    try
    {
      this.DAO = BankDAOFactory.getDAO();
      this.puter = new OutPuter();
      this.os = os;
    }
    catch (Exception localException) {}
  }
  
  protected FileOutPut(OutputStream os, String operator)
  {
    try
    {
      this.operator = operator;
      this.DAO = BankDAOFactory.getDAO();
      this.puter = new OutPuter();
      this.os = os;
    }
    catch (Exception localException) {}
  }
}
