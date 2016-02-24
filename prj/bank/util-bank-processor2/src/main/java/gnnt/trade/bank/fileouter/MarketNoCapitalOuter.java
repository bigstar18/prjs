package gnnt.trade.bank.fileouter;

import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.page.PageQuery;
import gnnt.trade.bank.util.Tool;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Vector;

public class MarketNoCapitalOuter
  extends FileOutPut
{
  public MarketNoCapitalOuter(OutputStream os)
  {
    super(os);
  }
  
  public void output(String bankID, Date date, int[] pageinfo)
  {
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = "";
    try
    {
      conn = this.DAO.getConnection();
      sql = "select bank.bankName bankName,fbb.bankid bankID,fbf.firmid firmID,'" + Tool.fmtDate(date) + "' tradeDate " + 
        ",fbb.firmid contact,fbb.funid funID,fbb.comparedate tradeDate,fbb.money money,'' actionID,'市场无流水' result " + 
        ",case when fbf.account is null then '-' when fbb.bankid='005' and fbf.account='999999999999999999' then '-' else fbf.account end account" + 
        ",case when fbb.type=" + 0 + " then '入金' else '出金' end type " + 
        "from f_b_bankcompareinfo fbb,f_b_firmidandaccount fbf,f_b_banks bank where fbb.firmid=fbf.contact(+) " + 
        " and fbb.bankid=fbf.bankid(+) and fbb.bankid=bank.bankid and fbb.status=" + 0 + " " + 
        " and not exists (select funid from f_b_capitalinfo fbc where fbb.funid=fbc.funid and fbb.firmid=fbc.contact " + 
        " and fbb.bankid=fbc.bankid and fbb.type=fbc.type and fbb.money=fbc.money " + 
        " and trunc(fbb.comparedate)=trunc(fbc.banktime) and fbc.status=" + 0 + ") " + 
        " and fbb.bankid='" + bankID + "' and trunc(fbb.comparedate)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
      rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
      this.puter.outputMsg(getHead(), getTypes(), rs, this.os);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (this.DAO != null) {
        this.DAO.closeStatement(rs, state, conn);
      }
    }
  }
  
  private LinkedHashMap<String, Integer> getTypes()
  {
    LinkedHashMap<String, Integer> result = new LinkedHashMap();
    result.put("bankName", Integer.valueOf(1));
    result.put("contact", Integer.valueOf(1));
    result.put("account", Integer.valueOf(1));
    result.put("funID", Integer.valueOf(1));
    result.put("type", Integer.valueOf(1));
    result.put("money", Integer.valueOf(8));
    result.put("result", Integer.valueOf(1));
    
    return result;
  }
  
  private Vector<String> getHead()
  {
    Vector<String> result = new Vector();
    result.add("银行名称");
    result.add("交易账号");
    result.add("银行账号");
    result.add("银行流水号");
    result.add("转账类型");
    result.add("转账金额");
    result.add("对账结果");
    
    return result;
  }
}
