package gnnt.trade.bank.data.ceb;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.CapitalProcessor;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.data.ceb.vo.CebConstant;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.FirmBalance_CEB;
import gnnt.trade.bank.vo.ReturnValue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import org.apache.log4j.Logger;

public class CEBExDataImpl
{
  private BankDAO DAO;
  
  public CEBExDataImpl()
  {
    try
    {
      this.DAO = BankDAOFactory.getDAO();
    }
    catch (ClassNotFoundException e)
    {
      log(Tool.getExceptionTrace(e));
    }
    catch (IllegalAccessException e)
    {
      log(Tool.getExceptionTrace(e));
    }
    catch (InstantiationException e)
    {
      log(Tool.getExceptionTrace(e));
    }
  }
  
  private void log(String content)
  {
    Logger plog = Logger.getLogger("Processorlog");
    plog.debug(content);
  }
  
  public ReturnValue send(Date tradeDate)
  {
    return sendFile_CEB(tradeDate);
  }
  
  private ReturnValue sendFile_CEB(Date date)
  {
    ReturnValue result = new ReturnValue();
    Connection conn = null;
    try
    {
      conn = this.DAO.getConnection();
      Vector<FirmBalance_CEB> vector = getFirmBalance_CEB(date);
      BankAdapterRMI adapter = getAdapter();
      if (adapter == null)
      {
        result.result = -30011L;
        log("发送光大银行清算数据，连接适配器异常，错误码：" + result.result);
        ReturnValue localReturnValue1 = result;return localReturnValue1;
      }
      result = adapter.sendFile_CEB(vector, date);
    }
    catch (SQLException e)
    {
      log("发送光大银行清算数据，数据库异常");
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      log("发送光大银行清算数据，连接适配器异常");
      e.printStackTrace();
    }
    catch (Exception e)
    {
      result.result = -1L;
      result.remark = "发送光大银行清算数据，连接适配器异常";
      log("发送光大银行清算数据，连接适配器异常");
      e.printStackTrace();
    }
    finally
    {
      this.DAO.closeStatement(null, null, conn);
    }
    return result;
  }
  
  private Vector<FirmBalance_CEB> getFirmBalance_CEB(Date qdate)
    throws SQLException, Exception
  {
    Vector<FirmBalance_CEB> result = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    


















    String sql = "select seq_F_B_action.nextval actionid,tf.firmtype firmtype, fbf.account account,fbf.account1 account1,fbf.contact contact,fbf.accountName accountName,(ffb.capital-ffb.lastcapital-ffb.fundio-ffb.marketfee) qyChange,(ffb.capital-ffb.lastcapital-ffb.fundio) mqyChange,(ffb.marketfee) fee,ffb.capital,ffb.fundio from  (select * from F_FIRMBALANCE_BANK f where f.bankcode = '" + 
    








      CebConstant.bankID + "' and f.b_date=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd')) ffb," + 
      " (select * from F_B_FIRMIDANDACCOUNT f where f.bankID='" + CebConstant.bankID + "' and f.isOpen=1 ) fbf ," + 
      " (select * from t_firm) tf" + 
      " where fbf.firmID=ffb.firmID(+)" + 
      " and tf.firmid(+) = ffb.firmID";
    try
    {
      conn = this.DAO.getConnection();
      log(sql);
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        FirmBalance_CEB fb = new FirmBalance_CEB();
        fb.firmID = rs.getString("contact");
        fb.bankID = CebConstant.bankID;
        fb.account = rs.getString("account");
        fb.account1 = rs.getString("account1");
        fb.accountName = rs.getString("accountName");
        fb.cardType = rs.getString("firmtype");
        
        fb.FeeMoney = rs.getDouble("fee");
        fb.QYChangeMoney = rs.getDouble("qyChange");
        fb.MQYChangeMoney = rs.getDouble("mqyChange");
        fb.QYMoney = rs.getDouble("capital");
        fb.useableBalance = rs.getDouble("capital");
        fb.date = qdate;
        result.add(fb);
      }
    }
    catch (SQLException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw e;
    }
    finally
    {
      this.DAO.closeStatement(rs, state, conn);
    }
    return result;
  }
  
  private BankAdapterRMI getAdapter()
  {
    return new CapitalProcessor().getAdapter(CebConstant.bankID);
  }
}
