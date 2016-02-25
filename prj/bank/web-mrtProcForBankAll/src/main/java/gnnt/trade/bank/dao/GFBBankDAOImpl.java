package gnnt.trade.bank.dao;

import gnnt.trade.bank.util.Tool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GFBBankDAOImpl
  extends GFBBankDAO
{
  public GFBBankDAOImpl()
    throws Exception
  {}
  
  public String getFirmStatus(String firmid, Connection conn)
    throws SQLException
  {
    String status = "";
    String firmType = "";
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = "select firmtype from t_firm where firmid=? ";
    try
    {
      state = conn.prepareStatement(sql);
      state.setString(1, firmid);
      rs = state.executeQuery();
      while (rs.next()) {
        firmType = rs.getString("firmtype");
      }
      String sql1 = " select status from ";
      if ("C".equals(firmType)) {
        return "D";
      }
      if ("M".equals(firmType))
      {
        sql1 = sql1 + " t_compmember where m_firmid=? ";
      }
      else if ("S".equals(firmType))
      {
        sql1 = sql1 + " t_specialmember where m_firmid=? ";
      }
      else
      {
        String str1 = status;return str1;
      }
      state = conn.prepareStatement(sql1);
      state.setString(1, firmid);
      rs = state.executeQuery();
      while (rs.next()) {
        status = rs.getString("status");
      }
    }
    catch (Exception e)
    {
      log("查询firmstatus异常：" + Tool.getExceptionTrace(e));
    }
    finally
    {
      closeStatement(rs, state, null);
    }
    return status;
  }
}
