package gnnt.trade.bank.data.jsb;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.CapitalProcessor;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.MarketCashBalance;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import org.apache.log4j.Logger;

public class JsExDataImpl
{
  private BankDAO DAO;
  
  public JsExDataImpl()
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
  
  private String fmtDate(java.util.Date time)
  {
    String result = "";
    try
    {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      result = sdf.format(time);
    }
    catch (Exception e)
    {
      Tool.getExceptionTrace(e);
    }
    return result;
  }
  
  private BankAdapterRMI getAdapter()
  {
    return new CapitalProcessor().getAdapter(JsConstant.bankID);
  }
  
  public ReturnValue send(java.util.Date tradeDate)
  {
    ReturnValue result = new ReturnValue();
    
    Vector<FirmBalance> vector = getFirmBalance(tradeDate);
    double sumFee = 0.0D;
    for (int i = 0; i < vector.size(); i++) {
      sumFee += ((FirmBalance)vector.get(i)).FeeMoney;
    }
    MarketCashBalance mcb = getMarketBalance(JsConstant.bankID, tradeDate);
    log("交易商总手续费[" + sumFee + "]");
    log("交易所留存手续费[" + mcb.feeAMT + "]");
    log("当日扎差变动量[" + mcb.cashAMT + "]");
    mcb.cashAMT += mcb.feeAMT;
    mcb.cash += sumFee - mcb.feeAMT;
    delMarketBalance(JsConstant.bankID, tradeDate);
    addMarketBalance(mcb);
    BankAdapterRMI adapter = getAdapter();
    if (adapter == null)
    {
      result.result = -30011L;
      log("发送江苏银行清算数据，连接适配器异常，错误码：" + result.result);
      return result;
    }
    try
    {
      result = adapter.sendTradeDate(vector, mcb);
      log("发送清算的结果:[" + result.toString() + "]");
    }
    catch (RemoteException e)
    {
      log("调用适配器发送清算异常：" + Common.getExceptionTrace(e));
      e.printStackTrace();
    }
    return result;
  }
  
  private Vector<FirmBalance> getFirmBalance(java.util.Date tradeDate)
  {
    Vector<FirmBalance> result = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = "select seq_F_B_action.nextval actionid,tf.firmtype firmtype, fbf.account account,fbf.account1 account1,fbf.contact contact,ffb.capital capital,fbf.accountName accountName,(ffb.capital-ffb.lastcapital-ffb.fundio-nvl(ffb.tradefee,0)) qyChange,(ffb.capital-ffb.lastcapital-ffb.fundio) mqyChange,case when ffb.tradefee<0 then ffb.tradefee*-1 else 0 end fee,ffb.capital,ffb.fundio from  (select * from F_FIRMBALANCE_BANK f where f.bankcode = '" + 
    












      JsConstant.bankID + "' and f.b_date=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')) ffb," + 
      " (select * from F_B_FIRMIDANDACCOUNT f where f.bankID='" + JsConstant.bankID + "' and f.isOpen=1 ) fbf ," + 
      " (select firmid,0-nvl(sum(mktfee),0) mktfee from t_trade_h where cleardate=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy/MM/dd') group by cleardate,firmid) th, " + 
      " (select * from t_firm) tf" + 
      " where fbf.firmID=ffb.firmID(+)" + 
      " and tf.firmid(+) = ffb.firmID" + 
      " and ffb.firmid = th.firmid(+)";
    try
    {
      log("江苏银行清算sql:" + sql);
      conn = this.DAO.getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      FirmBalance value = null;
      while (rs.next())
      {
        value = new FirmBalance();
        value.firmID = rs.getString("contact");
        value.bankID = JsConstant.bankID;
        value.account = rs.getString("account");
        value.accountName = rs.getString("accountName");
        value.FeeMoney = rs.getDouble("fee");
        value.QYChangeMoney = rs.getDouble("qyChange");
        value.QYMoney = rs.getDouble("capital");
        value.date = tradeDate;
        value.fundio = rs.getDouble("fundio");
        value.firmtype = rs.getString("firmtype");
        value.MQYChangeMoney = rs.getDouble("mqyChange");
        result.add(value);
      }
      log(result.size());
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
  
  private MarketCashBalance getMarketBalance(String bankid, java.util.Date tradeDate)
  {
    MarketCashBalance result = new MarketCashBalance();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = "select * from f_b_marketcashbalance mcb, (select max(tradedate) tradedate from f_b_marketcashbalance where tradedate<to_date('" + 
    
      Tool.fmtDate(tradeDate) + "', 'yyyy-MM-dd')) b, " + 
      "(select nvl(sum(ffb.capital - ffb.lastcapital - ffb.fundio), 0) marketcash, " + 
      "nvl(sum(case when ffb.tradefee<0 then ffb.tradefee*-1 else 0 end), 0) marketfee " + 
      "from (select * " + 
      "from F_FIRMBALANCE_BANK f " + 
      "where f.bankcode = '" + bankid + "' " + 
      "and f.b_date = to_date('" + Tool.fmtDate(tradeDate) + "', 'yyyy-MM-dd')) ffb, " + 
      "(select * " + 
      "from F_B_FIRMIDANDACCOUNT f " + 
      "where f.bankID = '" + bankid + "' " + 
      "and f.isOpen = 1) fbf " + 
      "where fbf.firmID = ffb.firmID(+)) a, " + 
      "(select nvl(sum(decode(type,0,money)),0) daycashio ,nvl(sum(decode(type,1,money)),0) " + 
      "dayfeeio from f_b_transferinfo where bankid='022' and to_char(banktime,'yyyy-MM-dd')='" + Tool.fmtDate(tradeDate) + "' and status=0)c ," + 
      " (select fb.marketfeenew from f_bankfunds fb where fb.bankcode = '022' ) feenew where mcb.tradedate = b.tradedate ";
    try
    {
      log("江苏银行收益头寸sql:" + sql);
      conn = this.DAO.getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        result.bankID = bankid;
        result.bankName = rs.getString("bankName");
        result.lastCash = rs.getDouble("Cash");
        
        result.cash = (rs.getDouble("Cash") - rs.getDouble("daycashio") - rs.getDouble("marketCash"));
        result.cashAMT = rs.getDouble("marketCash");
        result.cashIO = rs.getDouble("daycashio");
        result.lastFee = rs.getDouble("Fee");
        result.fee = (rs.getDouble("fee") - rs.getDouble("dayfeeio") + rs.getDouble("marketfeenew"));
        result.feeAMT = rs.getDouble("marketfeenew");
        result.feeIO = rs.getDouble("dayfeeio");
        result.tradeDate = new java.sql.Date(tradeDate.getTime());
      }
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
  
  public static void main(String[] args)
  {
    double d = 0.0D;
    d += 10.0D;
    System.out.println(d);
    
    System.out.println(d);
  }
  
  private int delMarketBalance(String bankid, java.util.Date tradeDate)
  {
    Connection conn = null;
    PreparedStatement state = null;
    int result = 0;
    String sql = "delete from f_b_marketcashbalance where to_char(tradedate,'yyyy-MM-dd')='" + Tool.fmtDate(tradeDate) + "' and bankid='" + bankid + "'";
    try
    {
      conn = this.DAO.getConnection();
      state = conn.prepareStatement(sql);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      result = -1;
      log(Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      result = -1;
      log(Tool.getExceptionTrace(e));
    }
    finally
    {
      this.DAO.closeStatement(null, state, conn);
    }
    return result;
  }
  
  private int addMarketBalance(MarketCashBalance mcb)
  {
    Connection conn = null;
    PreparedStatement state = null;
    int result = 0;
    String sql = "insert into f_b_marketcashbalance (bankid,bankname,lastcash,cash,cashamt,cashio,lastfee,fee,feeamt,feeio,tradedate) values(?,?,?,?,?,?,?,?,?,?,?)";
    try
    {
      int i = 1;
      conn = this.DAO.getConnection();
      state = conn.prepareStatement(sql);
      state.setString(i++, mcb.bankID);
      state.setString(i++, mcb.bankName);
      state.setDouble(i++, mcb.lastCash);
      state.setDouble(i++, mcb.cash);
      state.setDouble(i++, mcb.cashAMT);
      state.setDouble(i++, mcb.cashIO);
      state.setDouble(i++, mcb.lastFee);
      state.setDouble(i++, mcb.fee);
      state.setDouble(i++, mcb.feeAMT);
      state.setDouble(i++, mcb.feeIO);
      state.setDate(i++, mcb.tradeDate);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      result = -1;
      log(Tool.getExceptionTrace(e));
    }
    catch (Exception e)
    {
      result = -1;
      log(Tool.getExceptionTrace(e));
    }
    finally
    {
      this.DAO.closeStatement(null, state, conn);
    }
    return result;
  }
  
  public void log(String content)
  {
    Logger plog = Logger.getLogger("Processorlog");
    plog.debug(content);
  }
}
