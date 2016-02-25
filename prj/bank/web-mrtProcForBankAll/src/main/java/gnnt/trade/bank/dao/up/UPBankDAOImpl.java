package gnnt.trade.bank.dao.up;

import gnnt.trade.bank.vo.BankCodes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UPBankDAOImpl
  extends UPBankDAO
{
  private final transient Log logger = LogFactory.getLog("Processorlog");
  
  public UPBankDAOImpl()
    throws Exception
  {}
  
  public Vector<BankCodes> getBankCodeList(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>取得银行信息列表   getBankList  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    BankCodes value = null;
    Vector<BankCodes> list = new Vector();
    try
    {
      conn = getConnection();
      
      sql = "select * from f_b_bankcode " + filter;
      
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new BankCodes();
        value.bankName = rs.getString("bank_name");
        value.bankCode = rs.getString("bank_code");
        list.add(value);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return list;
  }
}
