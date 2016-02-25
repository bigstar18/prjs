package gnnt.bank.platform;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.bank.platform.dao.BankDAO;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferMoneyVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransMoneyTradeRate
  extends TransMoney
{
  protected long tranfer(String bankID, double money, String operator, String firmID, CapitalProcessor processor)
  {
    long result = -1L;
    
    Connection conn = null;
    
    BankDAO dao = processor.getBankDAO();
    
    long capitalID = 0L;
    


    Timestamp curTime = new Timestamp(System.currentTimeMillis());
    try
    {
      try
      {
        conn = dao.getConnection();
        

        result = dao.getActionID(conn);
        

        conn.setAutoCommit(false);
        

        CapitalValue cVal = new CapitalValue();
        cVal.actionID = result;
        cVal.bankID = bankID;
        
        cVal.creditID = "";
        cVal.debitID = "";
        cVal.firmID = firmID;
        cVal.funID = "";
        cVal.money = money;
        cVal.note = "";
        cVal.oprcode = "";
        cVal.status = 0;
        cVal.type = processor.getTransType();
        
        capitalID = dao.addCapitalInfo(cVal, conn);
        
        conn.commit();
      }
      catch (SQLException e)
      {
        result = -4L;
        e.printStackTrace();
        conn.rollback();
      }
      finally
      {
        conn.setAutoCommit(true);
        dao.closeStatement(null, null, conn);
      }
      if (result > 0L)
      {
        BankAdapterRMI adapter = processor.getAdapter(bankID);
        
        TransferMoneyVO transferMoneyVO = new TransferMoneyVO(bankID, firmID, money, null, null, -1L);
        
        ReturnValue rVal = adapter.transferMoney(transferMoneyVO);
        if (rVal.result == 0L)
        {
          try
          {
            conn = processor.getBankDAO().getConnection();
            

            conn.setAutoCommit(false);
            


            conn.commit();
          }
          catch (SQLException e)
          {
            e.printStackTrace();
            conn.rollback();
            result = -4L;
          }
          finally
          {
            conn.setAutoCommit(true);
            dao.closeStatement(null, null, conn);
          }
          if (result == -4L) {
            dao.modCapitalInfoStatus(capitalID, "", 1, curTime, conn);
          }
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result = -5L;
    }
    return result;
  }
}
