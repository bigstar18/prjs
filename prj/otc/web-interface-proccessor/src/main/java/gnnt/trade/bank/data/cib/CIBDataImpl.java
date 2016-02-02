package gnnt.trade.bank.data.cib;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.CIBCapitalProcessor;
import gnnt.trade.bank.CapitalProcessor;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.data.cib.vo.CIBConstant;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import org.apache.log4j.Logger;

public class CIBDataImpl
{
  private BankAdapterRMI bankadapter;
  private BankDAO DAO;
  private Connection conn = null;
  
  private void closeStatement(ResultSet rs, Statement state, Connection conn)
  {
    try
    {
      if (rs != null) {
        rs.close();
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    try
    {
      if (state != null) {
        state.close();
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    try
    {
      if (conn != null) {
        conn.close();
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
  }
  
  private void log(String content)
  {
    Logger plog = Logger.getLogger("Processorlog");
    plog.debug(content);
  }
  
  public ReturnValue send(Date tradeDate)
  {
    RZQSValue QSValue = new RZQSValue();
    RZDZValue DZValue = new RZDZValue();
    
    ReturnValue result = new ReturnValue();
    try
    {
      QSValue = new CIBCapitalProcessor().getXYQSValue(CIBConstant.bankID, null, tradeDate);
      DZValue = new CIBCapitalProcessor().getXYDZValue(CIBConstant.bankID, null, tradeDate);
      
      result = getAdapter().setRZ(QSValue, DZValue, tradeDate);
    }
    catch (RemoteException e)
    {
      log("发送清算出现异常！");
      result.result = -1L;
      result.remark = "发送清算出现异常";
      return result;
    }
    return result;
  }
  
  private BankAdapterRMI getAdapter()
  {
    return new CapitalProcessor().getAdapter(CIBConstant.bankID);
  }
}
