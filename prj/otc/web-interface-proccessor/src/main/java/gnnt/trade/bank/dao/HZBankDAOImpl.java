package gnnt.trade.bank.dao;

import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.TransferInfo;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmRightValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.MarketRightValue;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HZBankDAOImpl
  extends HZBankDAO
{
  public HZBankDAOImpl()
    throws Exception
  {}
  
  public int addBankQS(BankQSVO bq, Connection conn)
    throws SQLException
  {
    log("添加清算日期表");
    int result = 0;
    PreparedStatement state = null;
    String sql = null;
    try
    {
      sql = "insert into F_B_BankQSDate (BANKID,TRADEDATE,TRADETYPE,TRADESTATUS,NOTE) values (?,?,?,?,?)";
      state = conn.prepareStatement(sql);
      state.setString(1, bq.bankID);
      state.setDate(2, new java.sql.Date(bq.tradeDate.getTime()));
      state.setInt(3, bq.tradeType);
      state.setInt(4, bq.tradeStatus);
      state.setString(5, bq.note);
      result = state.executeUpdate();
    }
    catch (SQLException e)
    {
      throw e;
    }
    finally
    {
      closeStatement(null, state, null);
    }
    return result;
  }
  
  public RZQSValue getXYQSValue(String bankID, String[] firmIDs, java.util.Date tradeDate)
    throws SQLException, ClassNotFoundException
  {
    RZQSValue result = new RZQSValue();
    result.bankID = bankID;
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    String filter = " ";
    String fcfilter1 = " ";
    String fcfilter2 = " ";
    String fcfilter2y = " ";
    String thfilter = " ";
    String thfiltery = " ";
    try
    {
      conn = getConnection();
      if ((bankID != null) && (bankID.trim().length() > 0)) {
        filter = filter + " and bankID='" + bankID.trim() + "' ";
      }
      if ((firmIDs != null) && (firmIDs.length > 0))
      {
        String firms = "";
        for (String firmID : firmIDs) {
          if ((firmID != null) && (firmID.trim().length() > 0)) {
            firms = firms + "'" + firmID.trim() + "',";
          }
        }
        if ((firms != null) && (firms.trim().length() > 0))
        {
          firms = "and firmID in (" + firms.substring(0, firms.length() - 1) + ")";
          filter = filter + firms;
          fcfilter1 = fcfilter1 + firms;
          fcfilter2 = fcfilter2 + firms;
          fcfilter2y = fcfilter2y + firms;
          thfilter = thfilter + firms;
          thfiltery = thfiltery + firms;
        }
      }
      if (tradeDate != null)
      {
        result.tradeDate = tradeDate;
        java.util.Date yestoday = getlastDate(tradeDate, conn);
        fcfilter1 = fcfilter1 + " and trunc(b_date)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
        fcfilter2 = fcfilter2 + " and trunc(b_date)<=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
        thfilter = thfilter + " and trunc(cleardate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
        if (yestoday != null)
        {
          fcfilter2y = fcfilter2y + " and trunc(b_date)<=to_date('" + Tool.fmtDate(yestoday) + "','yyyy-MM-dd')";
          thfiltery = thfiltery + " and trunc(cleardate)=to_date('" + Tool.fmtDate(yestoday) + "','yyyy-MM-dd')";
        }
      }
      sql = 
      



























        "select  bank.*,nvl(Fee.value,0) maketMoney,0-(nvl(Income.value,0)-nvl(Payout.value,0)+nvl(PLY.value,0)-abs(nvl(PLK.value,0))+nvl(OtherItem.value,0)+nvl(thf.value,0)-nvl(thfy.value,0)+nvl(YQbcj.value,0)) bankErrorMoney,nvl(Income.value,0)-nvl(Payout.value,0)+nvl(PLY.value,0)-abs(nvl(PLK.value,0))-nvl(Fee.value,0)+nvl(OtherItem.value,0)+nvl(thf.value,0)-nvl(thfy.value,0)+nvl(YQbcj.value,0) firmErrorMoney,nvl(hmsg.rsm,0)-nvl(hmsgy.rsm,0)+nvl(hmsg.rma,0)-nvl(hmsgy.rma,0)+nvl(hmsg.fl,0)-nvl(hmsgy.fl,0)+nvl(zvm.value,0)-nvl(zvmy.value,0)+nvl(thf.value,0)-nvl(thfy.value,0) cash,nvl(balance.todaybalance,0)-nvl(balance.lastbalance,0)-nvl(inMoney.value,0)+nvl(outMoney.value,0) availableBalance,nvl(hmsg.rsm,0)+nvl(hmsg.rma,0)+nvl(hmsg.fl,0)+nvl(zvm.value,0)+nvl(thf.value,0) frozen,nvl(balance.todaybalance,0) balance,nvl(hmsg.rsm,0)+nvl(hmsg.rma,0)+nvl(hmsg.fl,0)+nvl(zvm.value,0)+nvl(thf.value,0)+nvl(balance.todaybalance,0) firmRights ,balance.b_date tradeDate  from (select firmID,sum(value) value from f_clientledger where code like 'Income%' " + fcfilter1 + " group by firmID) Income," + "(select firmID,sum(value) value from f_clientledger where code like 'Payout%' " + fcfilter1 + " group by firmID) Payout," + "(select firmID,sum(value) value from f_clientledger where code like '%PL' and value>0 " + fcfilter1 + " group by firmID) PLY," + "(select firmID,sum(value) value from f_clientledger where code like '%PL' and value<0 " + fcfilter1 + " group by firmID) PLK," + "(select firmID,sum(value) value from f_clientledger where code like '%Fee%' " + fcfilter1 + " group by firmID) Fee," + "(select firmID,sum(value) value from f_clientledger where code like 'OtherItem%' " + fcfilter1 + "  group by firmID) OtherItem," + "(select firmID,sum(case when code in ('Margin_V','Margin_Z') then value else -value end) value from f_clientledger where code in ('MarginBack_V','Margin_V','MarginBack_Z','Margin_Z') " + fcfilter2 + " group by firmID) zvm," + "(select firmID,sum(case when code in ('Margin_V','Margin_Z') then value else -value end) value from f_clientledger where code in ('MarginBack_V','Margin_V','MarginBack_Z','Margin_Z') " + fcfilter2y + " group by firmID) zvmy," + "(select firmID,sum(value) value from f_clientledger where code='Deposit' " + fcfilter1 + " group by firmID) inMoney," + "(select firmID,sum(value) value from f_clientledger where code='Fetch' " + fcfilter1 + " group by firmID) outMoney," + "(select firmID,RuntimeSettleMargin rsm,(RuntimeMargin-RuntimeAssure) rma,RuntimeFL fl from T_h_firm where 1=1 " + thfilter + ") hmsg," + "(select firmID,RuntimeSettleMargin rsm,(RuntimeMargin-RuntimeAssure) rma,RuntimeFL fl from T_h_firm where 1=1 " + thfiltery + ") hmsgy," + "(select firmID,sum(nvl(Floatingloss,0)) value from T_h_firmholdsum where 1=1 " + thfilter + " group by firmid) thf," + "(select firmID,sum(nvl(Floatingloss,0)) value from T_h_firmholdsum where 1=1 " + thfiltery + " group by firmid) thfy," + "(select firmID,todaybalance,lastbalance,b_date from f_firmbalance where 1=1 " + fcfilter1 + ") balance," + "(select * from f_b_firmIDandaccount where isopen=1 and firmID not like '%D%' " + filter + ") bank ," + "(select firmID,sum(value) value from f_clientledger where code ='SettleCompens' " + fcfilter1 + " group by firmID) YQbcj" + " where bank.firmID=Income.firmID(+) and bank.firmID=Payout.firmID(+) and bank.firmID=PLY.firmID(+) and bank.firmID=PLK.firmID(+) " + " and bank.firmID=Fee.firmID(+) and bank.firmID=OtherItem.firmID(+) and bank.firmID=zvm.firmID(+) and bank.firmID=zvmy.firmID(+) " + " and bank.firmID=inMoney.firmID(+) and bank.firmID=outMoney.firmID(+) and bank.firmID=hmsg.firmID(+) and bank.firmID=hmsgy.firmID(+) " + " and bank.firmID=balance.firmID(+) and bank.firmID=thf.firmID(+) and bank.firmID=thfy.firmID(+) and bank.firmID=YQbcj.firmID(+) order by bank.firmID";
      System.out.println("清算sql：" + sql);
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      MarketRightValue mrv = new MarketRightValue();
      mrv.maketMoney = new BigDecimal("0");
      while (rs.next())
      {
        FirmRightValue value = new FirmRightValue();
        value.actionID = String.valueOf(getActionID());
        value.firmID = rs.getString("firmID");
        value.account = rs.getString("account");
        value.availableBalance = rs.getDouble("availableBalance");
        value.billMoney = 0.0D;
        value.cash = rs.getDouble("cash");
        value.cashMoney = 0.0D;
        value.credit = 0.0D;
        value.firmErrorMoney = rs.getDouble("firmErrorMoney");
        mrv.bankErrorMoney += rs.getDouble("bankErrorMoney");
        mrv.maketMoney = mrv.maketMoney.add(rs.getBigDecimal("maketMoney"));
        System.out.println("交易商[" + rs.getString("firmID") + "]的自有资金[" + rs.getBigDecimal("maketMoney") + "]总自有资金[" + mrv.maketMoney + "]");
        log("交易商[" + rs.getString("firmID") + "]的自有资金[" + rs.getBigDecimal("maketMoney") + "]总自有资金[" + mrv.maketMoney + "]");
        result.putFrv(value);
      }
      result.setMarketRight(mrv);
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
  
  public java.util.Date getlastDate(java.util.Date date, Connection conn)
    throws SQLException
  {
    java.util.Date result = null;
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
  
  public RZDZValue getXYDZValue(String bankID, String[] firmIDs, java.util.Date tradeDate)
    throws SQLException, ClassNotFoundException
  {
    RZDZValue result = new RZDZValue();
    result.bankID = bankID;
    Connection conn = null;
    PreparedStatement state = null;
    ResultSet rs = null;
    String sql = null;
    String filter = " ";
    String fcfilter1 = " ";
    String fcfilter2 = " ";
    String fcfilter2y = " ";
    String thfilter = " ";
    String thfiltery = " ";
    try
    {
      conn = getConnection();
      if ((bankID != null) && (bankID.trim().length() > 0)) {
        filter = filter + " and bankID='" + bankID.trim() + "' ";
      }
      if ((firmIDs != null) && (firmIDs.length > 0))
      {
        String firms = "";
        for (String firmID : firmIDs) {
          if ((firmID != null) && (firmID.trim().length() > 0)) {
            firms = firms + "'" + firmID.trim() + "',";
          }
        }
        if ((firms != null) && (firms.trim().length() > 0))
        {
          firms = "and firmID in (" + firms.substring(0, firms.length() - 1) + ")";
          filter = filter + firms;
          fcfilter1 = fcfilter1 + firms;
          fcfilter2 = fcfilter2 + firms;
          fcfilter2y = fcfilter2y + firms;
          thfilter = thfilter + firms;
          thfiltery = thfiltery + firms;
        }
      }
      if (tradeDate != null)
      {
        result.tradeDate = tradeDate;
        java.util.Date yestoday = getlastDate(tradeDate, conn);
        fcfilter1 = fcfilter1 + " and trunc(b_date)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
        fcfilter2 = fcfilter2 + " and trunc(b_date)<=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
        thfilter = thfilter + " and trunc(cleardate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
        if (yestoday != null)
        {
          fcfilter2y = fcfilter2y + " and trunc(b_date)<=to_date('" + Tool.fmtDate(yestoday) + "','yyyy-MM-dd')";
          thfiltery = thfiltery + " and trunc(cleardate)=to_date('" + Tool.fmtDate(yestoday) + "','yyyy-MM-dd')";
        }
      }
      sql = 
      























        "select  bank.*,nvl(Fee.value,0) marketMoney,nvl(Income.value,0)-nvl(Payout.value,0)+nvl(PLY.value,0)-nvl(PLK.value,0)+nvl(OtherItem.value,0)+nvl(thf.value,0)-nvl(thfy.value,0) bankErrorMoney,nvl(Income.value,0)-nvl(Payout.value,0)+nvl(PLY.value,0)-nvl(PLK.value,0)-nvl(Fee.value,0)+nvl(OtherItem.value,0)+nvl(thf.value,0)-nvl(thfy.value,0) firmErrorMoney,nvl(hmsg.rsm,0)-nvl(hmsgy.rsm,0)+nvl(hmsg.rma,0)-nvl(hmsgy.rma,0)+nvl(hmsg.fl,0)-nvl(hmsgy.fl,0)+nvl(zvm.value,0)-nvl(zvmy.value,0)+nvl(thf.value,0)-nvl(thfy.value,0) cash,nvl(balance.todaybalance,0)-nvl(balance.lastbalance,0)-nvl(inMoney.value,0)+nvl(outMoney.value,0) availableBalance,nvl(hmsg.rsm,0)+nvl(hmsg.rma,0)+nvl(hmsg.fl,0)+nvl(zvm.value,0)+nvl(thf.value,0) frozen,nvl(balance.todaybalance,0) balance,nvl(hmsg.rsm,0)+nvl(hmsg.rma,0)+nvl(hmsg.fl,0)+nvl(zvm.value,0)+nvl(thf.value,0)+nvl(balance.todaybalance,0) firmRights ,balance.b_date tradeDate  from (select firmID,sum(value) value from f_clientledger where code like 'Income%' " + fcfilter1 + " group by firmID) Income," + "(select firmID,sum(value) value from f_clientledger where code like 'Payout%' " + fcfilter1 + " group by firmID) Payout," + "(select firmID,sum(value) value from f_clientledger where code like '%PL' and value>0 " + fcfilter1 + " group by firmID) PLY," + "(select firmID,sum(value) value from f_clientledger where code like '%PL' and value<0 " + fcfilter1 + " group by firmID) PLK," + "(select firmID,sum(value) value from f_clientledger where code like '%Fee%' " + fcfilter1 + " group by firmID) Fee," + "(select firmID,sum(value) value from f_clientledger where code like 'OtherItem%' " + fcfilter1 + "  group by firmID) OtherItem," + "(select firmID,sum(case when code in ('Margin_V','Margin_Z') then value else -value end) value from f_clientledger where code in ('MarginBack_V','Margin_V','MarginBack_Z','Margin_Z') " + fcfilter2 + " group by firmID) zvm," + "(select firmID,sum(case when code in ('Margin_V','Margin_Z') then value else -value end) value from f_clientledger where code in ('MarginBack_V','Margin_V','MarginBack_Z','Margin_Z') " + fcfilter2y + " group by firmID) zvmy," + "(select firmID,sum(value) value from f_clientledger where code='Deposit' " + fcfilter1 + " group by firmID) inMoney," + "(select firmID,sum(value) value from f_clientledger where code='Fetch' " + fcfilter1 + " group by firmID) outMoney," + "(select firmID,RuntimeSettleMargin rsm,(RuntimeMargin-RuntimeAssure) rma,RuntimeFL fl from T_h_firm where 1=1 " + thfilter + ") hmsg," + "(select firmID,RuntimeSettleMargin rsm,(RuntimeMargin-RuntimeAssure) rma,RuntimeFL fl from T_h_firm where 1=1 " + thfiltery + ") hmsgy," + "(select firmID,sum(nvl(Floatingloss,0)) value from T_h_firmholdsum where 1=1 " + thfilter + " group by firmid) thf," + "(select firmID,sum(nvl(Floatingloss,0)) value from T_h_firmholdsum where 1=1 " + thfiltery + " group by firmid) thfy," + "(select firmID,todaybalance,lastbalance,b_date from f_firmbalance where 1=1 " + fcfilter1 + ") balance," + "(select * from f_b_firmIDandaccount where isopen=1 and firmID not like '%D%' " + filter + ") bank " + " where bank.firmID=Income.firmID(+) and bank.firmID=Payout.firmID(+) and bank.firmID=PLY.firmID(+) and bank.firmID=PLK.firmID(+) " + " and bank.firmID=Fee.firmID(+) and bank.firmID=OtherItem.firmID(+) and bank.firmID=zvm.firmID(+) and bank.firmID=zvmy.firmID(+) " + " and bank.firmID=inMoney.firmID(+) and bank.firmID=outMoney.firmID(+) and bank.firmID=hmsg.firmID(+) and bank.firmID=hmsgy.firmID(+) " + " and bank.firmID=balance.firmID(+) and bank.firmID=thf.firmID(+) and bank.firmID=thfy.firmID(+) order by bank.firmID";
      state = conn.prepareStatement(sql);
      rs = state.executeQuery();
      while (rs.next())
      {
        FirmDZValue value = new FirmDZValue();
        value.firmID = rs.getString("firmID");
        value.account = rs.getString("account");
        value.firmRights = rs.getDouble("firmRights");
        value.cashRights = 0.0D;
        value.billRights = 0.0D;
        value.availableBalance = rs.getDouble("balance");
        value.cash = rs.getDouble("frozen");
        value.credit = 0.0D;
        result.putFdv(value);
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
  
  public int addTransfer(TransferInfo param)
    throws SQLException, ClassNotFoundException
  {
    Connection conn = null;
    PreparedStatement state = null;
    String sql = null;
    int result = 0;
    try
    {
      conn = getConnection();
      sql = "insert into f_b_transferinfo values('" + 
        param.funId + "','" + param.actionId + "'," + param.money + "," + param.type + ",sysdate,sysdate,'" + 
        param.outAccount + "','" + param.inAccount + "'," + param.status + ",'" + param.outAccountName + "','" + param.inAccountName + "','" + 
        param.bankid + "')";
      log(sql);
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
}
