package gnnt.trade.bank.dao.ceb;

import gnnt.bank.adapter.bankBusiness.dayData.FCS_10_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_11_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_13_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_99;
import gnnt.bank.adapter.bankBusiness.message.CEB_param;
import gnnt.trade.bank.dao.page.PageQuery;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.BanksInfoValue;
import gnnt.trade.bank.vo.CitysValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmUserValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRightsValue;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

public class CEBBankDaoImpl
  extends CEBBankDao
{
  public CEBBankDaoImpl()
    throws Exception
  {}
  
  public int modCapitalInfoStatus_ceb_f623(String funID, int status, Timestamp bankTime, Connection conn)
    throws SQLException
  {
    long id = 0L;
    PreparedStatement state = null;
    String sql = null;
    int result = 0;
    try
    {
      String funidf = "";
      int n = 1;
      sql = "update F_B_capitalInfo set status=?,FUNID=?||id,bankTime=?,funid2=?||id where FUNID=?";
      state = conn.prepareStatement(sql);
      state.setInt(n++, status);
      state.setString(n++, funID.trim() + "|");
      state.setTimestamp(n++, bankTime);
      state.setString(n++, funID + "|");
      state.setString(n++, funID.trim());
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      result = -1;
      throw e;
    }
    finally
    {
      closeStatement(null, state, null);
    }
    return result;
  }
  
  public Vector<CorrespondValue> getMarketAcount(String filter)
    throws SQLException, ClassNotFoundException
  {
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    CorrespondValue value = null;
    Vector<CorrespondValue> list = new Vector();
    try
    {
      conn = getConnection();
      sql = "select * from F_B_MARKETACOUNT where 1 = 1 " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      log("getMarketAcount" + sql);
      while (rs.next())
      {
        value = new CorrespondValue();
        value.account = rs.getString("account");
        value.notes = rs.getString("notes");
        value.accountName = rs.getString("accountName");
        value.accountBank = rs.getString("accountBank");
        value.bankName = rs.getString("accountBankName");
        value.isCrossline = rs.getString("isCrossLine");
        value.accountBankNum = rs.getString("accountBankNum");
        value.type = rs.getString("type");
        
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
  
  public int addTransfer(CEB_param param)
    throws SQLException, ClassNotFoundException
  {
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    int result = 0;
    try
    {
      conn = getConnection();
      sql = "insert into F_B_TRANSFER(BANKID,FLAG,BUSISERIALNUM,AMOUNT,TRADETIME,ATONCE,ISCROSSLINE,TARGETACCOUNTBANKNUM,TARGETACCOUNTBANK,TARGETACCOUNTBANKNAME,TARGETACCOUNTNAME,TARGETACCOUNT,SOURCEACCOUNTNAME,SOURCEACCOUNT,FCSSERIALNUM,RESULTCODE) values('" + 
        param.bankId + 
        "','" + 
        param.flag + 
        "','" + 
        param.busiSerialNum + 
        "'," + 
        Double.parseDouble(param.amount) + 
        ",'" + 
        param.tradeTime + 
        "','" + 
        param.atOnce + 
        "','" + 
        param.isCrossLine + 
        "','" + 
        param.targetAccountBankNum + 
        "','" + 
        param.targetAccountBank + 
        "','" + 
        param.targetAccountBankName + 
        "','" + 
        param.targetAccountName + 
        "','" + 
        param.targetAccount + 
        "','" + 
        param.sourceAccountName + 
        "','" + 
        param.sourceAccount + 
        "','" + 
        param.fcsSerialNum + 
        "','" + 
        param.resultCode + "')";
      state = conn.prepareStatement(sql);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      result = -1;
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public Date getlastDate(Date date, Connection conn)
    throws SQLException
  {
    Date result = null;
    String sql = "select max(B_Date) bdate from f_firmbalance t where to_char(B_Date,'yyyy-MM-dd')<'" + Tool.fmtDate(date) + "'";
    PreparedStatement state = null;
    ResultSet rs = null;
    try
    {
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      if (rs.next()) {
        result = rs.getDate("bdate");
      }
    }
    catch (SQLException e)
    {
      throw e;
    }
    finally
    {
      closeStatement(rs, state, null);
    }
    if (result == null) {
      result = Tool.strToDate("1900-01-01");
    }
    return result;
  }
  
  public Vector<FirmBalanceValue> getFlote(String[] firmIDs)
    throws SQLException, ClassNotFoundException
  {
    Vector<FirmBalanceValue> result = new Vector();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    String filter = "";
    if ((firmIDs != null) && (firmIDs.length > 0))
    {
      String firms = "";
      for (firmID : firmIDs) {
        if ((firmID != null) && (firmID.trim().length() > 0)) {
          firms = firms + "'" + firmID.trim() + "',";
        }
      }
      if ((firms != null) && (firms.trim().length() > 0)) {
        filter = filter + " and firmid in (" + firms.substring(0, firms.length() - 1) + ") ";
      }
    }
    try
    {
      sql = "select sum(floatingloss) floatingloss,firmID from t_holdposition where 1=1 " + filter + " group by firmid order by firmid";
      conn = getConnection();
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        FirmBalanceValue value = new FirmBalanceValue();
        value.firmId = rs.getString("firmID");
        value.floatingloss = rs.getDouble("floatingloss");
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
  
  public int modCapitalInfoStatus_ceb(long id, String funID, int status, int type, Timestamp bankTime, Connection conn)
    throws SQLException
  {
    PreparedStatement state = null;
    String sql = null;
    int result = 0;
    try
    {
      String funidf = "";
      int n = 1;
      if ((funID == null) || (funID.trim().length() <= 0)) {
        funID = "";
      } else {
        funidf = ",funid2=?";
      }
      sql = "update F_B_capitalInfo set status=?,FUNID=?,type=?,bankTime=?" + funidf + " where id=?";
      
      state = conn.prepareStatement(sql);
      state.setInt(n++, status);
      state.setString(n++, funID.trim());
      state.setInt(n++, type);
      state.setTimestamp(n++, bankTime);
      if ((funidf != null) && (funidf.trim().length() > 0)) {
        state.setString(n++, funID + "|" + id);
      }
      state.setLong(n++, id);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      result = -1;
      throw e;
    }
    finally
    {
      closeStatement(null, state, null);
    }
    return result;
  }
  
  public Vector<FirmRightsValue> getTradeDataMsg(String bankId, String firmid, Date qdate)
    throws SQLException, ClassNotFoundException
  {
    Vector<FirmRightsValue> hmfai = new Vector();
    
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    
    String filter = " ";
    String filter_f_fb = " ";
    String filter_z_v = " ";
    String filter_t_h_f = " ";
    if ((bankId != null) && (!"".equals(bankId.trim()))) {
      filter = filter + " and i.bankId='" + bankId.trim() + "' and i.firmid not like '%D'";
    }
    if ((firmid != null) && (!"".equals(firmid.trim())))
    {
      filter = filter + " and i.firmid='" + firmid + "' ";
      filter_f_fb = filter_f_fb + " and firmid='" + firmid + "' ";
      filter_t_h_f = filter_t_h_f + " and firmid='" + firmid + "' ";
      filter_z_v = filter_z_v + " and firmid='" + firmid + "' ";
    }
    if (qdate != null)
    {
      filter_f_fb = filter_f_fb + " and to_char(b_date,'yyyy-MM-dd')='" + Tool.fmtDate(qdate) + "' ";
      filter_t_h_f = filter_t_h_f + " and to_char(cleardate,'yyyy-MM-dd')='" + Tool.fmtDate(qdate) + "' ";
      filter_z_v = filter_z_v + " and to_char(b_date,'yyyy-MM-dd')<='" + Tool.fmtDate(qdate) + "'";
    }
    try
    {
      conn = getConnection();
      sql = "select i.firmid qy_firmid,i.accountname accountname,i.account account, nvl(a.todaybalance, 0) f_balance, nvl(b.RuntimeMargin, 0) + nvl(b.RuntimeFL, 0) + nvl(b.RuntimeSettleMargin, 0) + nvl(c.floatingloss, 0) t_money, nvl(d.money, 0) v_money, nvl(e.money, 0) z_money, nvl(f.money, 0) pay_money, nvl(g.money, 0) in_money, nvl(h.money, 0) fee_money, nvl(jie.money, 0) jie_money, nvl(dai.money, 0) dai_money from (select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb + ") a," + "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 " + filter_t_h_f + ") b," + 
        "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 " + filter_t_h_f + " group by firmid) c," + "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') " + filter_z_v + " group by firmid) d," + "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') " + filter_z_v + " group by firmid) e," + "(select firmid, nvl(sum(value), 0) money from f_clientledger where code like 'Payout%' " + filter_f_fb + " group by firmid) f," + 
        "(select firmid, nvl(sum(value), 0) money from f_clientledger where code like 'Income%' " + filter_f_fb + " group by firmid) g," + "(select firmid, sum(value) money from f_clientledger where (code like 'TradeFee%' or code like 'SettleFee%' or code = 'BankFee') " + filter_f_fb + " group by firmid) h," + "(select firmid, sum(appeNdamount) money from F_H_FUNDFLOW where oprcode = 207 " + filter_f_fb + " group by firmid) jie," + "(select firmid, sum(appeNdamount) money from F_H_FUNDFLOW where oprcode = 206 " + filter_f_fb + " group by firmid) dai," + "(select firmid,bankid,accountname,account from f_b_Firmidandaccount) i " + "where " + "i.firmid = a.firmid(+) " + "and i.firmid = b.firmid(+) " + "and i.firmid = c.firmid(+) " + "and i.firmid = d.firmid(+) " + 
        "and i.firmid = e.firmid(+) " + "and i.firmid = f.firmid(+) " + "and i.firmid = g.firmid(+) " + "and i.firmid = h.firmid(+) " + "and i.firmid = jie.firmid(+) " + "and i.firmid = dai.firmid(+) " + filter + " order by a.firmid";
      state = conn.prepareStatement(sql);
      System.out.println("----从财务和交易系统获取交易商资金余额及各交易板块权益  getTradeDataMsg---->sql:\n" + sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        FirmRightsValue fai = new FirmRightsValue();
        String qy_firmid = rs.getString("qy_firmid");
        String account = rs.getString("account");
        String accountname = rs.getString("accountname");
        double f_balance = rs.getDouble("f_balance");
        double t_money = rs.getDouble("t_money");
        double v_money = rs.getDouble("v_money");
        double z_money = rs.getDouble("z_money");
        double pay_money = rs.getDouble("pay_money");
        double in_money = rs.getDouble("in_money");
        double fee_money = rs.getDouble("fee_money");
        double jie_money = rs.getDouble("jie_money");
        double dai_money = rs.getDouble("dai_money");
        
        fai.setFirmid(qy_firmid);
        fai.setAccount(account);
        fai.setAccountname(accountname);
        fai.setAvilableBlance(f_balance);
        fai.setTimebargainBalance(t_money);
        fai.setVendueBalance(v_money);
        fai.setZcjsBalance(z_money);
        fai.setPayment(pay_money);
        fai.setIncome(in_money);
        fai.setFee(fee_money);
        fai.setJie(jie_money);
        fai.setDai(dai_money);
        
        hmfai.add(fai);
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
    return hmfai;
  }
  
  public int addFCS_10(FCS_10_Result result)
    throws SQLException, ClassNotFoundException
  {
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    int result1 = 0;
    try
    {
      conn = getConnection();
      sql = "insert into f_b_fcs_10(busiNum,busiName,currency,acct,amount,memBalance,status,busiDate) values('" + result.busiNum + "','" + result.busiName + "','" + result.currency + "','" + result.acct + "','" + result.amount + "','" + result.memBalance + "','" + result.status + "','" + result.busiDate + "')";
      System.out.println("sql10=" + sql);
      state = conn.prepareStatement(sql);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      result1 = -1;
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result1;
  }
  
  public int addFCS_11(FCS_11_Result result)
    throws SQLException, ClassNotFoundException
  {
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    int result1 = 0;
    try
    {
      conn = getConnection();
      sql = "insert into f_b_fcs_11(busiNum,memNum,currency,acct,balance,memBalance,status,busiDate) values('" + result.busiNum + "','" + result.memNum + "','" + result.currency + "','" + result.acct + "','" + result.balance + "','" + result.memBalance + "','" + result.status + "','" + result.busiDate + "')";
      System.out.println("sql11=" + sql);
      state = conn.prepareStatement(sql);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      result1 = -1;
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result1;
  }
  
  public int addFCS_99(FCS_99 result)
    throws SQLException, ClassNotFoundException
  {
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    int result1 = 0;
    try
    {
      conn = getConnection();
      
      sql = "insert into f_b_fcs_99(tradeTime,busiNum,batchNum,fileName,rspCode,errorDescription) values('" + result.tradeTime + "','" + result.busiNum + "','" + result.batchNum + "','" + result.fileName + "','" + result.rspCode + "','" + result.errorDescription + "')";
      System.out.println("sql99=" + sql);
      state = conn.prepareStatement(sql);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      result1 = -1;
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result1;
  }
  
  public int addFCS_13(FCS_13_Result result)
    throws SQLException, ClassNotFoundException
  {
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    int result1 = 0;
    try
    {
      conn = getConnection();
      sql = "insert into f_b_fcs_13(busiNum,tradeTime,busiSerialNum,fcsSerialNum,orderNum,fromAccount,otherNum,amount,fee,otherFee,currency,busiDate,checkDate,adjustStatus,orderStatus,errorDescription ) values('" + 
        result.busiNum + 
        "','" + 
        result.tradeTime + 
        "','" + 
        result.busiSerialNum + 
        "','" + 
        result.fcsSerialNum + 
        "','" + 
        result.orderNum + 
        "','" + 
        result.fromAccount + 
        "','" + 
        result.otherNum + 
        "','" + 
        result.amount + 
        "','" + 
        result.fee + 
        "','" + 
        result.otherFee + 
        "','" + 
        result.currency + 
        "','" + 
        result.busiDate + 
        "','" + 
        result.checkDate + 
        "','" + 
        result.adjustStatus + 
        "','" + 
        result.orderStatus + 
        "','" + 
        result.errorDescription + 
        "')";
      System.out.println("sql13=" + sql);
      state = conn.prepareStatement(sql);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      result1 = -1;
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result1;
  }
  
  public FirmBalanceValue availableBalance(String filter)
  {
    FirmBalanceValue fbv = new FirmBalanceValue();
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    try
    {
      conn = getConnection();
      sql = " select firmid,balance,FROZENFUNDS,lastbalance from f_firmfunds " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        fbv.setFirmId(rs.getString(1));
        fbv.setAvilableBalance(rs.getDouble(2) - rs.getDouble(3));
        fbv.setMarketBalance(rs.getDouble(2));
        fbv.setFrozenBalance(rs.getDouble(3));
        fbv.setLastBalance(rs.getDouble(4));
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return fbv;
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
  
  public int addMarketAcount(CorrespondValue val)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>添加市场账号   addMarketAcount  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    int result = 0;
    try
    {
      conn = getConnection();
      
      sql = "insert into f_b_marketacount (ACCOUNT, ACCOUNTNAME, ACCOUNTBANK, ACCOUNTBANKNAME, ISCROSSLINE, TYPE, ACCOUNTBANKNUM, NOTES)values ('" + val.account + "','" + val.accountName + "','" + val.accountBank + "','" + val.bankName + "','" + val.isCrossline + "','" + val.type + "','" + val.accountBankNum + "','" + val.notes + "')";
      
      state = conn.prepareStatement(sql);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      result = -1;
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public int delMarketAcount(String account)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>删除市场账号   delMarketAcount  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    
    int result = 0;
    String sql;
    try
    {
      conn = getConnection();
      String sql = "delete from f_b_marketacount where account='" + account + "'";
      state = conn.prepareStatement(sql);
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      result = -1;
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public int modMarketAcount(CorrespondValue value)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>修改市场账号   modMarketAcount  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    
    int result = 0;
    String sql;
    try
    {
      conn = getConnection();
      String sql = "update F_B_MARKETACOUNT set account = '" + value.account + "'," + " notes = '" + value.notes + "'," + " accountName = '" + value.accountName + "'," + " accountBank = '" + value.accountBank + "'," + " accountbankname = '" + value.bankName + "'," + " isCrossLine = '" + value.isCrossline + "'," + " accountBankNum = '" + value.accountBankNum + "'," + " type = '" + value.type + "' where account = '" + value.account + "'";
      state = conn.prepareStatement(sql);
      
      state.executeUpdate();
    }
    catch (SQLException e)
    {
      result = -1;
      e.printStackTrace();
      throw e;
    }
    finally
    {
      closeStatement(null, state, conn);
    }
    return result;
  }
  
  public Vector<CEB_param> getTransferList(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>转账流水查询   getTransferList  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    CEB_param value = null;
    Vector<CEB_param> list = new Vector();
    try
    {
      conn = getConnection();
      sql = "select * from F_B_TRANSFER t where 1=1 " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new CEB_param();
        value.fcsSerialNum = rs.getString("fcsSerialNum");
        value.busiSerialNum = rs.getString("busiSerialNum");
        value.amount = rs.getString("amount");
        value.flag = rs.getString("flag");
        value.tradeTime = rs.getString("tradeTime");
        value.sourceAccountName = rs.getString("sourceAccountName");
        value.sourceAccount = rs.getString("sourceAccount");
        value.targetAccountName = rs.getString("targetAccountName");
        value.targetAccount = rs.getString("targetAccount");
        
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
  
  public Vector<FCS_10_Result> getFCS_10_List(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>总账对账结果查询   getFCS_10_List  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    FCS_10_Result value = null;
    Vector<FCS_10_Result> list = new Vector();
    try
    {
      conn = getConnection();
      sql = "select * from f_b_fcs_10 t where 1=1 " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new FCS_10_Result();
        value.busiNum = rs.getString("busiNum");
        value.busiName = rs.getString("busiName");
        value.currency = rs.getString("currency");
        value.acct = rs.getString("acct");
        value.amount = rs.getString("amount");
        value.memBalance = rs.getString("memBalance");
        value.status = rs.getString("status");
        value.busiDate = rs.getString("busiDate");
        
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
  
  public Vector<FCS_11_Result> getFCS_11_List(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>分户账对账结果查询   getFCS_11_List  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    FCS_11_Result value = null;
    Vector<FCS_11_Result> list = new Vector();
    try
    {
      conn = getConnection();
      sql = "select * from f_b_fcs_11 t where 1=1 " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new FCS_11_Result();
        value.busiNum = rs.getString("busiNum");
        value.memNum = rs.getString("memNum");
        value.currency = rs.getString("currency");
        value.acct = rs.getString("acct");
        value.balance = rs.getString("balance");
        value.memBalance = rs.getString("memBalance");
        value.status = rs.getString("status");
        value.busiDate = rs.getString("busiDate");
        
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
  
  public Vector<FCS_13_Result> getFCS_13_List(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>交易明细对账结果查询   getFCS_13_List  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    FCS_13_Result value = null;
    Vector<FCS_13_Result> list = new Vector();
    try
    {
      conn = getConnection();
      sql = "select * from f_b_fcs_13 t where 1=1 " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new FCS_13_Result();
        value.busiNum = rs.getString("busiNum");
        value.tradeTime = rs.getString("tradeTime");
        value.busiSerialNum = rs.getString("busiSerialNum");
        value.fcsSerialNum = rs.getString("fcsSerialNum");
        value.orderNum = rs.getString("orderNum");
        value.fromAccount = rs.getString("fromAccount");
        value.otherNum = rs.getString("otherNum");
        value.amount = rs.getString("amount");
        value.fee = rs.getString("fee");
        value.otherFee = rs.getString("otherFee");
        value.currency = rs.getString("currency");
        value.busiDate = rs.getString("busiDate");
        value.checkDate = rs.getString("checkDate");
        value.adjustStatus = rs.getString("adjustStatus");
        value.orderStatus = rs.getString("orderStatus");
        value.errorDescription = rs.getString("errorDescription");
        
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
  
  public Vector<FCS_99> getFCS_99_List(String filter)
    throws SQLException, ClassNotFoundException
  {
    log("===>>>日终总结果查询   getFCS_99_List  " + new Date());
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    FCS_99 value = null;
    Vector<FCS_99> list = new Vector();
    try
    {
      conn = getConnection();
      sql = "select * from f_b_fcs_99 t where 1=1 " + filter;
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        value = new FCS_99();
        value.tradeTime = rs.getString("tradeTime");
        value.busiNum = rs.getString("busiNum");
        value.batchNum = rs.getString("batchNum");
        value.fileName = rs.getString("fileName");
        value.rspCode = rs.getString("rspCode");
        value.errorDescription = rs.getString("errorDescription");
        
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
