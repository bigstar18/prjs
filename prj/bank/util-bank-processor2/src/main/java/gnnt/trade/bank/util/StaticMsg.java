package gnnt.trade.bank.util;

import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.vo.BankValue;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class StaticMsg
{
  private static Map<String, BankValue> bankMap;
  private static String StaticMsgsyn = "StaticMsgsyn";
  private BankDAO DAO;
  
  public StaticMsg()
  {
    try
    {
      this.DAO = BankDAOFactory.getDAO();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
    }
    catch (InstantiationException e)
    {
      e.printStackTrace();
    }
  }
  
  public Map<String, BankValue> getBankMap()
  {
    if (bankMap == null) {
      setBankMap();
    }
    return bankMap;
  }
  
  public BankValue getBank(String bankID)
  {
    Map<String, BankValue> bankMap = getBankMap();
    return (BankValue)bankMap.get(bankID);
  }
  
  private void setBankMap()
  {
    if (bankMap != null) {
      return;
    }
    synchronized (StaticMsgsyn)
    {
      if (bankMap != null) {
        return;
      }
      bankMap = new HashMap();
      try
      {
        Vector<BankValue> bankList = this.DAO.getBankList("");
        if ((bankList != null) && (bankList.size() > 0)) {
          for (BankValue bank : bankList) {
            if (bank != null) {
              bankMap.put(bank.bankID, bank);
            }
          }
        }
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }
    }
  }
}
