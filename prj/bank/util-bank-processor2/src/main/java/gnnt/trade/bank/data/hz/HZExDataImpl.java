package gnnt.trade.bank.data.hz;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.HZCapitalProcessor;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.HZBankDAO;
import gnnt.trade.bank.data.boc.vo.FileProcessor;
import gnnt.trade.bank.data.hz.vo.HZConstant;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import org.apache.log4j.Logger;

public class HZExDataImpl
{
  private HZBankDAO DAO;
  private FileProcessor fp;
  private HZCapitalProcessor cp;
  private BankAdapterRMI bankAdapter;
  
  public HZExDataImpl()
  {
    try
    {
      this.DAO = BankDAOFactory.getHZDAO();
      this.fp = new FileProcessor();
      this.cp = new HZCapitalProcessor();
      this.bankAdapter = this.cp.getAdapter(HZConstant.bankID);
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
  
  public ReturnValue send(Date date)
  {
    log("[杭州银行清算业务date" + date + "]");
    ReturnValue returnValue = sendHZQSValue(date);
    return returnValue;
  }
  
  public ReturnValue sendHZQSValue(Date tradeDate)
  {
    ReturnValue result = new ReturnValue();
    Connection conn = null;
    if (this.bankAdapter == null)
    {
      result.result = -920000L;
      result.remark = ("发送[" + HZConstant.bankID + "]银行交易商清算信息，连接适配器失败");
    }
    else
    {
      String[] firmIDs = (String[])null;
      

      Vector<FirmBalance> list = getFirmBalance(tradeDate);
      RZQSValue qs = new HZCapitalProcessor().getQSInfo(list);
      RZDZValue dz = new HZCapitalProcessor().getDZInfo(list);
      try
      {
        result = this.bankAdapter.setRZ(qs, dz, tradeDate);
        if ((result != null) && (result.result == 0L)) {
          try
          {
            conn = this.DAO.getConnection();
            BankQSVO bq2 = new BankQSVO();
            bq2.bankID = HZConstant.bankID;
            bq2.tradeDate = tradeDate;
            bq2.note = "哈尔滨银行清算";
          }
          catch (SQLException e)
          {
            result.result = -1L;
            result.remark = "发送清算成功，插入清算表数据库异常";
            throw e;
          }
          catch (Exception e)
          {
            result.result = -1L;
            result.remark = "发送清算成功，插入清算表系统异常";
            throw e;
          }
        }
      }
      catch (Exception e)
      {
        result.result = -1L;
        result.remark = ("发送[" + HZConstant.bankID + "]银行交易商清算信息，适配器抛出异常");
        e.printStackTrace();
      }
    }
    return result;
  }
  
  private Vector<FirmBalance> getFirmBalance(Date tradeDate)
  {
    Vector<FirmBalance> result = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql1 = "";
    String sql = "";
    
    sql = "select seq_F_B_action.nextval actionid,tf.firmtype firmtype, fbf.account account,fbf.account1 account1,fbf.contact contact,ffb.capital capital,fbf.accountName accountName,(0-(ffb.capital-ffb.lastcapital-ffb.fundio-nvl(th.mktfee,0))) qyChange,(ffb.capital-ffb.lastcapital-ffb.fundio) mqyChange,abs(nvl(th.mktfee,0)) fee,ffb.capital,ffb.fundio from  (select * from F_FIRMBALANCE_BANK f where f.bankcode = '" + 
    










      HZConstant.bankID + "' and f.b_date=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')) ffb," + 
      " (select * from F_B_FIRMIDANDACCOUNT f where f.bankID='" + HZConstant.bankID + "' and f.isOpen=1 ) fbf ," + 
      " (select firmid,0-nvl(sum(mktfee),0) mktfee from t_trade_h where cleardate=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy/MM/dd') group by cleardate,firmid) th, " + 
      " (select * from t_firm) tf" + 
      " where fbf.firmID=ffb.firmID(+)" + 
      " and tf.firmid(+) = ffb.firmID" + 
      " and ffb.firmid = th.firmid(+)";
    try
    {
      log("杭州银行的清算sql为：   " + sql);
      conn = this.DAO.getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      FirmBalance value = null;
      while (rs.next())
      {
        value = new FirmBalance();
        value.firmID = rs.getString("contact");
        value.bankID = HZConstant.bankID;
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
      log("sql1===========" + sql1);
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
  
  public RZQSValue getHRBQSValue(String bankID, String[] firmIDs, Date tradeDate)
  {
    RZQSValue result = null;
    try
    {
      result = this.DAO.getXYQSValue(bankID, firmIDs, tradeDate);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      log(e.getMessage());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if (result == null) {
      result = new RZQSValue();
    }
    return result;
  }
  
  public RZDZValue getHRBDZValue(String bankID, String[] firmIDs, Date tradeDate)
  {
    RZDZValue result = null;
    try
    {
      result = this.DAO.getXYDZValue(bankID, firmIDs, tradeDate);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      log(e.getMessage());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    if (result == null) {
      result = new RZDZValue();
    }
    return result;
  }
  
  public static void log(String content)
  {
    Logger plog = Logger.getLogger("Processorlog");
    plog.debug(content);
  }
}
