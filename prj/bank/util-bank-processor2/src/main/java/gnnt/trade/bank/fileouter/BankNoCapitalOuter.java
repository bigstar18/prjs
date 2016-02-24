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

public class BankNoCapitalOuter
  extends FileOutPut
{
  public BankNoCapitalOuter(OutputStream os)
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
      sql = "select fbb.bankName bankName,fbc.firmid firmID,fbc.contact contact,fbc.money money ,case when fbc.type=0 then '入金' else '出金' end type ,case when fbc.funid is null then '-' else fbc.funid end funID ,case when fbf.account is null then '-' when '005'='" + 
      

        bankID + "' and fbf.account='999999999999999999' then '-' else fbf.account end account" + 
        ",fbc.actionid actionID,fbc.bankid bankID,'" + Tool.fmtDate(date) + "' tradeDate " + 
        ",case when fbc.status=" + 0 + " then '成功' else '处理中' end status,'银行无流水' result " + 
        " from f_b_capitalinfo fbc,f_b_firmidandaccount fbf,f_b_banks fbb" + 
        " where fbc.type in (" + 0 + "," + 1 + ") and fbc.bankid=fbb.bankid(+) and fbc.firmid = fbf.firmid(+) and fbc.bankid=fbf.bankid(+) and fbc.status not in (" + 1 + "," + 9 + ") " + 
        " and not exists (select funid from f_b_bankcompareinfo fbb where fbc.funid=fbb.funid " + 
        " and fbc.contact=fbb.firmid and fbc.money=fbb.money and trunc(fbc.banktime)=trunc(fbb.comparedate) " + 
        " and fbc.bankid=fbb.bankid and fbc.type=fbb.type and fbb.status=" + 0 + ") " + 
        " and fbc.bankid='" + bankID + "' and trunc(banktime)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
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
    result.put("funID", Integer.valueOf(1));
    result.put("actionID", Integer.valueOf(4));
    result.put("contact", Integer.valueOf(1));
    result.put("account", Integer.valueOf(1));
    result.put("type", Integer.valueOf(1));
    result.put("money", Integer.valueOf(8));
    result.put("status", Integer.valueOf(1));
    result.put("result", Integer.valueOf(1));
    
    return result;
  }
  
  private Vector<String> getHead()
  {
    Vector<String> result = new Vector();
    result.add("银行名称");
    result.add("银行流水号");
    result.add("交易所流水号");
    result.add("交易账号");
    result.add("银行账号");
    result.add("转账类型");
    result.add("转账金额");
    result.add("转账状态");
    result.add("对账结果");
    
    return result;
  }
}
