package gnnt.trade.bank.dao;

import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class CMBCBankDAOImpl
  extends CMBCBankDAO
{
  private BankDAO DAO;
  
  public CMBCBankDAOImpl()
    throws Exception
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
  
  public Vector<FirmBalance> getFirmBalanceValue(Date tradeDate, Date qsDate, String qsbankID)
  {
    log("民生清算数据查询getFirmBalanceValue-tradeDate[" + tradeDate + "]qsDate[" + qsDate + "]");
    Vector<FirmBalance> vec = new Vector();
    
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    try
    {
      conn = getConnection();
      String sql = "select seq_F_B_action.nextval actionid,tf.firmtype firmtype, fbf.account account,fbf.account1 account1,fbf.contact contact,(ffb.capital+fbc.money) capital,fbf.accountName accountName,(ffb.capital-ffb.lastcapital-ffb.fundio-ffb.tradefee) qyChange,(ffb.capital-ffb.lastcapital-ffb.fundio) mqyChange,ffb.tradefee fee,ffb.capital,ffb.fundio from  (select * from F_FIRMBALANCE_BANK f where f.bankcode = '" + 
      










        qsbankID + "' and f.b_date=to_date('" + Tool.fmtDate(qsDate) + "','yyyy-MM-dd')) ffb," + 
        " (select firmid,sum(case when type = 0 then money else -money end) money from f_b_capitalinfo c  where c.status =0 and fbc.type in (0,1) and c.createdate='" + Tool.fmtDate(tradeDate) + "' and c.bankid='" + qsbankID + "' group by firmid ) fbc ," + 
        " (select * from F_B_FIRMIDANDACCOUNT f where f.bankID='" + qsbankID + "' and f.isOpen=1 ) fbf ," + 
        " (select * from t_firm) tf" + 
        " where fbf.firmID=ffb.firmID(+)" + 
        " and fbf.firmID=tf.firmid(+)" + 
        " and fbf.firmID=fbc.firmID(+) ";
      System.out.println("民生清算sql[" + sql + "]");
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      FirmBalance value = null;
      while (rs.next())
      {
        value = new FirmBalance();
        value.firmID = rs.getString("contact");
        value.bankID = qsbankID;
        value.account = rs.getString("account");
        value.accountName = rs.getString("accountName");
        value.FeeMoney = rs.getDouble("fee");
        value.QYChangeMoney = rs.getDouble("qyChange");
        value.QYMoney = rs.getDouble("capital");
        value.date = tradeDate;
        value.fundio = rs.getDouble("fundio");
        value.firmtype = rs.getString("firmtype");
        value.MQYChangeMoney = rs.getDouble("MQYChange");
        
        vec.add(value);
      }
    }
    catch (SQLException e)
    {
      log(Tool.getExceptionTrace(e));
      vec = null;
    }
    catch (ClassNotFoundException e)
    {
      log(Tool.getExceptionTrace(e));
      vec = null;
    }
    finally
    {
      log("清算数据集合vec==null[" + (vec == null) + "]");
      if (vec != null) {
        log("清算数据集合vec.size()[" + vec.size() + "]");
      }
      this.DAO.closeStatement(rs, state, conn);
    }
    return vec;
  }
}
