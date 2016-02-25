package gnnt.trade.bank.dao.citic;

import gnnt.trade.bank.vo.CapitalMoneyVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class CITICBankDAOImpl
  extends CITICBankDAO
{
  public CITICBankDAOImpl()
    throws Exception
  {}
  
  public Vector<CapitalMoneyVO> getCapitalInfoMoney(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>求和市场流水中资金   getCapitalInfoMoney  " + new Date());
    Vector<CapitalMoneyVO> result = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    CapitalMoneyVO value11 = new CapitalMoneyVO(0, 0);
    CapitalMoneyVO value12 = new CapitalMoneyVO(1, 0);
    CapitalMoneyVO value13 = new CapitalMoneyVO(3, 0);
    CapitalMoneyVO value21 = new CapitalMoneyVO(0, 1);
    CapitalMoneyVO value22 = new CapitalMoneyVO(1, 1);
    CapitalMoneyVO value23 = new CapitalMoneyVO(3, 1);
    CapitalMoneyVO value31 = new CapitalMoneyVO(0, 5);
    CapitalMoneyVO value32 = new CapitalMoneyVO(1, 5);
    CapitalMoneyVO value33 = new CapitalMoneyVO(3, 5);
    try
    {
      conn = getConnection();
      if ((filter != null) && (filter.indexOf("organizationno") >= 0)) {
        sql = "select count(*) rowcount,sum(fbc.money) money,fbc.type type,fbc.status status from f_b_capitalinfo fbc,m_firm mf,f_b_firmidandaccount fbf,f_b_banks fbb,v_customerrelateorganization mc where fbc.bankid=fbb.bankid(+) and fbc.bankid=fbf.bankid and fbc.firmid=mf.firmid(+) and fbc.firmid=fbf.firmid(+) and mc.customerno=fbf.firmid " + filter + "  group by fbc.type,fbc.status ";
      } else if ((filter != null) && (filter.indexOf("memberno") >= 0)) {
        sql = "select count(*) rowcount,sum(fbc.money) money,fbc.type type,fbc.status status from f_b_capitalinfo fbc,m_firm mf,f_b_firmidandaccount fbf,f_b_banks fbb,m_customerinfo mc where fbc.bankid=fbb.bankid(+) and fbc.bankid=fbf.bankid and fbc.firmid=mf.firmid(+) and fbc.firmid=fbf.firmid(+) and mc.customerno=fbf.firmid " + filter + "  group by fbc.type,fbc.status ";
      } else {
        sql = "select count(*) rowcount,sum(fbc.money) money,fbc.type type,fbc.status status from f_b_capitalinfo fbc,m_firm mf,f_b_firmidandaccount fbf,f_b_banks fbb where fbc.bankid=fbb.bankid(+) and fbc.bankid=fbf.bankid and fbc.firmid=mf.firmid(+) and fbc.firmid=fbf.firmid(+) " + filter + "  group by fbc.type,fbc.status ";
      }
      log("sql:" + sql);
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next()) {
        if (rs.getInt("type") == 0)
        {
          if (rs.getInt("status") == 0)
          {
            value11.rowcount = rs.getInt("rowcount");
            value11.money = rs.getDouble("money");
          }
          else if ((rs.getInt("status") == 1) || (rs.getInt("status") == 9))
          {
            value21.rowcount += rs.getInt("rowcount");
            value21.money += rs.getDouble("money");
          }
          else
          {
            value31.rowcount += rs.getInt("rowcount");
            value31.money += rs.getDouble("money");
          }
        }
        else if (rs.getInt("type") == 1)
        {
          if (rs.getInt("status") == 0)
          {
            value12.rowcount = rs.getInt("rowcount");
            value12.money = rs.getDouble("money");
          }
          else if ((rs.getInt("status") == 1) || (rs.getInt("status") == 9))
          {
            value22.rowcount += rs.getInt("rowcount");
            value22.money += rs.getDouble("money");
          }
          else
          {
            value32.rowcount += rs.getInt("rowcount");
            value32.money += rs.getDouble("money");
          }
        }
        else if (rs.getInt("type") == 3) {
          if (rs.getInt("status") == 0)
          {
            value13.rowcount = rs.getInt("rowcount");
            value13.money = rs.getDouble("money");
          }
          else if ((rs.getInt("status") == 1) || (rs.getInt("status") == 9))
          {
            value23.rowcount += rs.getInt("rowcount");
            value23.money += rs.getDouble("money");
          }
          else
          {
            value33.rowcount += rs.getInt("rowcount");
            value33.money += rs.getDouble("money");
          }
        }
      }
      result.add(value11);
      result.add(value12);
      result.add(value21);
      result.add(value22);
      result.add(value31);
      result.add(value32);
      
      result.add(value13);
      result.add(value23);
      result.add(value33);
    }
    catch (SQLException e)
    {
      throw e;
    }
    catch (ClassNotFoundException e)
    {
      throw e;
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    return result;
  }
}
