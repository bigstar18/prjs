package gnnt.trade.bank.dao.hxb;

import gnnt.trade.bank.dao.page.PageQuery;
import gnnt.trade.bank.vo.BanksInfoValue;
import gnnt.trade.bank.vo.CitysValue;
import gnnt.trade.bank.vo.FirmUserValue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class HXBBankDAOImpl
  extends HXBBankDAO
{
  public HXBBankDAOImpl()
    throws Exception
  {}
  
  public Vector<BanksInfoValue> getBanksInfo(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>取得他行签约所需他行信息  getBanksInfo  ");
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    Vector<BanksInfoValue> result = new Vector();
    BanksInfoValue value = null;
    try
    {
      conn = getConnection();
      sql = "select * from F_B_mbfenetbank " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new BanksInfoValue();
        value.nbkcode = rs.getString("nbkcode");
        value.sabkcode = rs.getString("sabkcode");
        value.bnkcity = rs.getString("bnkcity");
        value.nbkname = rs.getString("nbkname");
        value.nbksname = rs.getString("nbksname");
        value.nbkaddrss = rs.getString("nbkaddrss");
        value.cnttel = rs.getString("cnttel");
        value.cntper = rs.getString("cntper");
        value.postcode = rs.getString("postcode");
        value.nbkstate = rs.getString("nbkstate");
        value.bkemail = rs.getString("bkemail");
        value.content = rs.getString("content");
        result.add(value);
      }
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
  
  public Vector<CitysValue> getCityOfBank(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>取得他行签约所需的开户行所在地信息  getCityOfBank  ");
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    Vector<CitysValue> result = new Vector();
    CitysValue value = null;
    try
    {
      conn = getConnection();
      sql = "select * from f_b_citys " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new CitysValue();
        value.ID = rs.getString("ID");
        value.cityName = rs.getString("cityName");
        value.parentID = rs.getString("parentID");
        result.add(value);
      }
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
  
  public void changeBankID(String bankID, String firmID)
  {
    log("同步子账号，子账号签约操作，修改对应的bankID的值");
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    try
    {
      conn = getConnection();
      sql = "update F_B_firmidandaccount set bankid = ? where firmID = ?";
      state = conn.prepareStatement(sql);
      state.setString(1, bankID);
      state.setString(2, firmID);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    finally
    {
      closeStatement(null, state, conn);
    }
  }
  
  public Vector<FirmUserValue> getFirmUserList2(String filter, int[] pageinfo, String bankId, String strKey)
    throws SQLException, ClassNotFoundException
  {
    log("查询客户预签约状态、子账户签约状态列表  getFirmUserList  ");
    Vector<FirmUserValue> resultlist = new Vector();
    FirmUserValue result = null;
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    try
    {
      conn = getConnection();
      if ((filter != null) && (filter.indexOf("organizationno") >= 0)) {
        sql = 
          "select fbf.firmID firmid,fbf.contact contact,fbf.firmName firmname,fbf.cardType cardtype,fbf.card card,fbf.status status,mf.firmType firmType,fbfa.account account,fbfa.isopen isopen,case when mf.firmtype<>'C' then fbf.firmid else (select nvl(memberno,'') from m_customerinfo mc where mc.customerno=fbf.firmid) end belevemember  from f_b_firmidandaccount fbfa,f_b_firmuser fbf,m_firm mf,v_customerrelateorganization mc where fbf.firmid=mf.firmid(+)  and mc.customerno=fbf.firmid and fbfa.firmid = fbf.firmid " + filter;
      } else if ((filter != null) && (filter.indexOf("memberno") >= 0)) {
        sql = 
          "select fbf.firmID firmid,fbf.contact contact,fbf.firmName firmname,fbf.cardType cardtype,fbf.card card,fbf.status status,mf.firmType firmType, fbfa.isopen isopen,fbfa.account account,case when mf.firmtype<>'C' then fbf.firmid else (select nvl(memberno,'') from m_customerinfo mc where mc.customerno=fbf.firmid) end belevemember  from f_b_firmidandaccount fbfa,f_b_firmuser fbf,m_firm mf,m_customerinfo mc where fbf.firmid=mf.firmid(+) and  mc.customerno=fbf.firmid and fbfa.firmid = fbf.firmid " + filter;
      } else {
        sql = 
          "select fbf.firmID firmid,fbf.contact contact,fbf.firmName firmname,fbf.cardType cardtype,fbf.card card,fbf.status status,mf.firmType firmType ,fbfa.isopen isopen, fbfa.account account,case when mf.firmtype<>'C' then fbf.firmid else (select nvl(memberno,'') from m_customerinfo mc where mc.customerno=fbf.firmid) end belevemember  from f_b_firmidandaccount fbfa , f_b_firmuser fbf,m_firm mf  where fbf.firmid=mf.firmid(+) and fbfa.firmid = fbf.firmid " + filter;
      }
      log("sql________________bankdaoimpl_hxb--------:" + sql);
      rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
      while (rs.next())
      {
        result = new FirmUserValue();
        result.account = rs.getString("account");
        result.isopen = rs.getInt("isopen");
        result.firmID = rs.getString("firmid");
        result.contact = rs.getString("contact");
        result.firmName = rs.getString("firmname");
        result.cardType = rs.getString("cardtype");
        result.card = rs.getString("card");
        
        result.bankID = bankId;
        result.strkey = strKey;
        result.belevemember = rs.getString("belevemember");
        result.status = rs.getInt("status");
        result.firmType = rs.getString("firmType");
        resultlist.add(result);
      }
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
    return resultlist;
  }
  
  public FirmUserValue getFirmUserValue(String contact)
    throws SQLException, ClassNotFoundException
  {
    log("查询子账户签时的信息内容getFirmUserValue   ");
    FirmUserValue result = new FirmUserValue();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    try
    {
      conn = getConnection();
      if (contact != null)
      {
        sql = 
          " select t.account account,t.accountname accountname,fbf.cardtype cardtype,fbf.card card  from f_b_firmidandaccount t, f_b_firmuser fbf  where t.firmid = fbf.firmid and fbf.contact = " + contact;
        state = conn.prepareStatement(sql);
        rs = state.executeQuery();
        if (rs.next())
        {
          result.account = rs.getString("account");
          result.accountname = rs.getString("accountname");
          result.cardType = rs.getString("cardtype");
          result.card = rs.getString("card");
        }
        return result;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      log("查询数据失败！");
    }
    finally
    {
      closeStatement(rs, state, conn);
    }
    closeStatement(rs, state, conn);
    
    return result;
  }
}
