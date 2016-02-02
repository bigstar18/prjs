package gnnt.trade.bank.data.icbc;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.CapitalProcessor;
import gnnt.trade.bank.ICBCCapitalProcessor;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.data.icbc.vo.FirmRights;
import gnnt.trade.bank.data.icbc.vo.OpenOrDelFirmValue;
import gnnt.trade.bank.data.icbc.vo.TradingDetailsValue;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;

public class ICBCExDataImpl
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
  
  private Vector<FirmBalance> getFirmBalance(Date tradeDate)
  {
    Vector<FirmBalance> result = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = "";
    





















    sql = "select seq_F_B_action.nextval actionid,tf.firmtype firmtype, fbf.account account,fbf.account1 account1,fbf.contact contact,ffb.capital capital,fbf.accountName accountName,(ffb.capital-ffb.lastcapital-ffb.fundio-ffb.tradefee) qyChange,(ffb.capital-ffb.lastcapital-ffb.fundio) mqyChange,ffb.tradefee fee,ffb.capital,ffb.fundio from  (select * from F_FIRMBALANCE_BANK f where f.bankcode = '010' and f.b_date=to_date('" + 
    










      Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')) ffb," + 
      " (select * from F_B_FIRMIDANDACCOUNT f where f.bankID='" + "010" + "' and f.isOpen=1 ) fbf ," + 
      " (select * from t_firm) tf" + 
      " where fbf.firmID=ffb.firmID(+)" + 
      " and fbf.firmID=tf.firmid(+)";
    try
    {
      log("工行的清算sql为：   " + sql);
      conn = this.DAO.getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      FirmBalance value = null;
      while (rs.next())
      {
        value = new FirmBalance();
        value.firmID = rs.getString("contact");
        value.bankID = "010";
        value.account = rs.getString("account");
        value.accountName = rs.getString("accountName");
        value.FeeMoney = rs.getDouble("fee");
        value.QYChangeMoney = rs.getDouble("qyChange");
        value.QYMoney = rs.getDouble("capital");
        value.date = tradeDate;
        value.fundio = rs.getDouble("fundio");
        value.firmtype = rs.getString("firmtype");
        value.MQYChangeMoney = rs.getDouble("MQYChange");
        

        result.add(value);
      }
      log("清算sql查询到的记录： " + result.size() + " 条");
    }
    catch (SQLException e)
    {
      log(Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      log(Tool.getExceptionTrace(e));
    }
    finally
    {
      this.DAO.closeStatement(rs, state, conn);
    }
    return result;
  }
  
  public ReturnValue send(Date tradeDate)
  {
    List<TradingDetailsValue> tradeList = new ArrayList();
    List<FirmRights> rightsList = new ArrayList();
    List<OpenOrDelFirmValue> openordelList = new ArrayList();
    ReturnValue result = new ReturnValue();
    Vector<FirmBalance> list = getFirmBalance(tradeDate);
    try
    {
      tradeList = new ICBCCapitalProcessor().getChangeBalance(list);
      rightsList = new ICBCCapitalProcessor().getRightsList(list);
      openordelList = new ICBCCapitalProcessor().getOpenOrDropMaket("010", tradeDate);
      log("取到的交收明细有：  " + tradeList.size() + "  条 ");
      log("取到客户余额有：  " + rightsList.size() + "  条 ");
      log("取到开销户文件有：  " + openordelList.size() + "  条 ");
      
      result = getAdapter().sendTradeData(rightsList, tradeList, openordelList, tradeDate);
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
    return new CapitalProcessor().getAdapter("010");
  }
}
