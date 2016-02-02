package gnnt.trade.bank.data.hxb;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.CapitalProcessor;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.ReturnValue;
import java.io.PrintStream;
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

public class ExchangeDataImpl
  implements ExchangeData
{
  private BankDAO DAO;
  private Connection conn = null;
  
  public Vector<QS> getQS(String bankId, Date date)
  {
    Vector<QS> rst = new Vector();
    
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    QS val = null;
    try
    {
      this.DAO = BankDAOFactory.getDAO();
      Connection conn = this.DAO.getConnection();
      



















































      sql = "select fbf.contact,       fbf.account1,nvl(abs(ffb.capital - ffb.lastcapital-ffb.fundio),0.00) amt,'01' type,case when ffb.capital - ffb.lastcapital-ffb.fundio <0 then '1' when ffb.capital - ffb.lastcapital-ffb.fundio >=0 then '2' when ffb.capital - ffb.lastcapital-ffb.fundio is null then '1' end dcflag from f_b_firmidandaccount fbf  ,f_firmbalance_bank ffb where ffb.bankcode='" + 
      








        bankId + "' and fbf.bankid='" + bankId + "' and fbf.isopen=1 and length(fbf.account1)=6 and trunc(b_date)=to_date('" + date + "','yyyy-MM-dd') and fbf.firmid =ffb.firmid(+) ";
      System.out.println("sql=" + sql);
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        val = new QS();
        if (rs.getString("contact") != null)
        {
          val.firmID = rs.getString("contact");
          val.account = rs.getString("account1");
          val.direction = rs.getInt("dcFlag");
          val.amt = rs.getDouble("amt");
          val.type = rs.getString("type");
          val.abstct = "";
          rst.add(val);
          System.out.println("firmID=" + val.firmID + "  " + "account=" + val.account + "  " + "amt=" + val.amt + "  " + "type=" + val.type);
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      log(Tool.getExceptionTrace(e));
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      log(Tool.getExceptionTrace(e));
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
      log(Tool.getExceptionTrace(e));
    }
    catch (InstantiationException e)
    {
      e.printStackTrace();
      log(Tool.getExceptionTrace(e));
    }
    finally
    {
      closeStatement(rs, state, this.conn);
    }
    return rst;
  }
  
  public Vector<DZ> getDZ(String bankId, Date date)
  {
    Vector<DZ> rst = new Vector();
    
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    DZ val = null;
    try
    {
      this.DAO = BankDAOFactory.getDAO();
      Connection conn = this.DAO.getConnection();
      









      sql = "select fbf.account1 AccountNo,fbf.contact MerAccountNo, ff.capital-(ff.capital-ff.lastcapital-ff.fundio) amt,ff.capital-(ff.capital-ff.lastcapital-ff.fundio) amtuse ,ff.bankcode ,capital interests from F_FirmBalance_bank ff, f_b_firmidandaccount fbf where ff.firmid(+) = fbf.firmid and fbf.isopen = 1 and ff.bankcode = '" + 
      
        bankId + "' and fbf.bankid = '" + bankId + "'" + 
        "and length(fbf.account1)=6 and trunc(ff.b_date) = to_date('" + Tool.fmtDate(date) + "', 'yyyy-MM-dd')";
      System.out.println("sql=" + sql);
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next()) {
        if (rs.getString("AccountNo") != null)
        {
          val = new DZ();
          val.account = rs.getString("AccountNo");
          val.firmId = rs.getString("MerAccountNo");
          val.amt = rs.getDouble("amt");
          val.useAmt = rs.getDouble("amtuse");
          val.interests = rs.getDouble("interests");
          rst.add(val);
          System.out.println("firmId=" + val.firmId + "  " + "amt=" + val.amt + "  " + "useAmt=" + val.useAmt);
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      log(Tool.getExceptionTrace(e));
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      log(Tool.getExceptionTrace(e));
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
      log(Tool.getExceptionTrace(e));
    }
    catch (InstantiationException e)
    {
      e.printStackTrace();
      log(Tool.getExceptionTrace(e));
    }
    finally
    {
      closeStatement(rs, state, this.conn);
    }
    return rst;
  }
  
  public List<ZZH> getZZH(String bankId, String date, Connection conn)
  {
    List<ZZH> rst = new ArrayList();
    
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    ZZH val = null;
    try
    {
      sql = 
      

        "select f.firmid firm_id,name,todaybalance fund1,'2' type from f_firmbalance f,m_firm m,f_b_firmidandaccount fb where f.firmid=m.firmid and f.firmid=fb.firmid and bankid='" + bankId + "' " + "and b_date=to_date('" + date + "','yyyy-mm-dd') order by f.firmid";
      
      System.out.println("sql=" + sql);
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next()) {
        if ((rs.getString("firm_id") != null) && (!rs.getString("firm_id").trim().startsWith("111")))
        {
          val = new ZZH();
          val.firmId = rs.getString("firm_id");
          val.firmName = rs.getString("name");
          val.amt = rs.getDouble("fund1");
          val.type = rs.getInt("type");
          rst.add(val);
        }
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return rst;
  }
  
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
  
  public ReturnValue send(String bankId, Date tradeDate)
  {
    ReturnValue result = new ReturnValue();
    ReturnValue result1 = new ReturnValue();
    ReturnValue result2 = new ReturnValue();
    try
    {
      Vector<QS> qs = getQS(bankId, tradeDate);
      result1 = getAdapter().hxSentQS(qs, tradeDate);
      if (result1.result == 0L)
      {
        Vector<DZ> dz = getDZ(bankId, tradeDate);
        result2 = getAdapter().hxSentDZ(dz, tradeDate);
      }
      result1.result += result2.result; ReturnValue 
        tmp96_95 = result;tmp96_95.remark = (tmp96_95.remark + result1.remark + result2.remark);
    }
    catch (RemoteException e)
    {
      log(Tool.getExceptionTrace(e));
    }
    return result;
  }
  
  private BankAdapterRMI getAdapter()
  {
    return new CapitalProcessor().getAdapter(HXConstant.bankID);
  }
}
