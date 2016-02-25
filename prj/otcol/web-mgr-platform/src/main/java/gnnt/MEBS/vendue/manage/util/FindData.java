package gnnt.MEBS.vendue.manage.util;

import gnnt.MEBS.vendue.manage.bean.DBBean;
import gnnt.MEBS.vendue.manage.report.AllTrade;
import gnnt.MEBS.vendue.manage.report.BargainIPCheck;
import gnnt.MEBS.vendue.manage.report.BuyerLiftGoods;
import gnnt.MEBS.vendue.manage.report.DistinctDate;
import gnnt.MEBS.vendue.manage.report.DivProTrade;
import gnnt.MEBS.vendue.manage.report.EachProTrade;
import gnnt.MEBS.vendue.manage.report.FinanceOut;
import gnnt.MEBS.vendue.manage.report.ForeignBargain;
import gnnt.MEBS.vendue.manage.report.GradeTradeResult;
import gnnt.MEBS.vendue.manage.report.HJProTrade;
import gnnt.MEBS.vendue.manage.report.HJProTradeDetail;
import gnnt.MEBS.vendue.manage.report.HJTradeResult;
import gnnt.MEBS.vendue.manage.report.OutLogDetail;
import gnnt.MEBS.vendue.manage.report.PaymentDetail;
import gnnt.MEBS.vendue.manage.report.PlanDetail;
import gnnt.MEBS.vendue.manage.report.ProLiftedGoods;
import gnnt.MEBS.vendue.manage.report.SecurityCheck;
import gnnt.MEBS.vendue.manage.report.TradeDetail;
import gnnt.MEBS.vendue.manage.report.TradePro;
import gnnt.MEBS.vendue.manage.report.TradeResult;
import gnnt.MEBS.vendue.manage.report.TraderDebt;
import gnnt.MEBS.vendue.manage.report.UserNotTrade;
import gnnt.MEBS.vendue.manage.report.VariLiftedGoods;
import gnnt.MEBS.vendue.manage.report.VariProTrade;
import gnnt.MEBS.vendue.manage.report.VariProYear;
import gnnt.MEBS.vendue.manage.report.VariTradeResult;
import gnnt.MEBS.vendue.manage.report.XJTradeResult;
import gnnt.MEBS.vendue.manage.report.ZJProLifted;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class FindData
{
  public static int findParTrading(String paramString, int paramInt)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    try
    {
      localDBBean = new DBBean(paramString);
      int i = -1;
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select case when status=2 then section-1 when status=3 and section <(select  ");
      localStringBuffer.append("max(section) from v_curcommodity where tradepartition=" + paramInt + " ) then ");
      localStringBuffer.append("section-1  when status=3 and section= (select max(section) from ");
      localStringBuffer.append("v_curcommodity where tradepartition=" + paramInt + " ) then section else section end");
      localStringBuffer.append(" as section from v_syscurstatus where tradepartition=" + paramInt + "");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet.next()) {
        i = localResultSet.getInt("section");
      }
      localResultSet.close();
      localDBBean.close();
      int k = i;
      return k;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      int j = -1;
      return j;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList buyerLiftGoods(String paramString1, String paramString2, String paramString3)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList localArrayList = new ArrayList();
      localStringBuffer = new StringBuffer();
      BigDecimal localBigDecimal1 = null;
      BigDecimal localBigDecimal2 = null;
      localStringBuffer.append("select t3.str6 v1,sum(t1.amount) v2,");
      localStringBuffer.append("(select name from dictable where type=2 and value=t3.str6)");
      localStringBuffer.append(" v3 from v_hisbargain t1,v_commodity t2,v_commext t3 where t1.commodityid=t2.id and t3.commid=t2.id ");
      localStringBuffer.append("" + paramString3 + " group by t3.str6 ");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet1.next())
      {
        localObject1 = new BuyerLiftGoods();
        ((BuyerLiftGoods)localObject1).setProName(localResultSet1.getString(3));
        localBigDecimal1 = ManaUtil.disBD(localResultSet1.getBigDecimal(2));
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select sum(t4.amount) as n from v_hisbargain t1,v_commodity t2,v_commext t3,");
        localStringBuffer.append("outlog t4 where t1.commodityid=t2.id and t3.commid=t2.id and ");
        localStringBuffer.append("t1.contractid=t4.contractid and t4.finished=2 and t3.str6='" + localResultSet1.getString(1) + "'" + paramString3);
        localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
        if (localResultSet2.next()) {
          localBigDecimal2 = ManaUtil.disBD(localResultSet2.getBigDecimal("n"));
        }
        localResultSet2.close();
        localDBBean.closeStmt();
        ((BuyerLiftGoods)localObject1).setTradeAmount(localBigDecimal1);
        ((BuyerLiftGoods)localObject1).setOutAmount(localBigDecimal2);
        if (localBigDecimal1.compareTo(new BigDecimal(0)) == 0) {
          localBigDecimal1 = new BigDecimal(1);
        }
        ((BuyerLiftGoods)localObject1).setOutRadio(ManaUtil.accuracyNum(localBigDecimal2.divide(localBigDecimal1, 4, 4).multiply(new BigDecimal(100)), ".##"));
        localArrayList.add(localObject1);
      }
      localResultSet1.close();
      localDBBean.close();
      Object localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList findSecCheck(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList localArrayList = new ArrayList();
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select t1.debitaccountname v1,sum(t1.amount) v2 from v_accountbook t1 where t1.summaryno='01'");
      localStringBuffer.append(" and substr(creditaccountcode,0,3)='201' " + paramString2 + " group by t1.debitaccountname ");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = new SecurityCheck();
        ((SecurityCheck)localObject1).setBank(localResultSet.getString(1));
        ((SecurityCheck)localObject1).setTotalMoney(localResultSet.getBigDecimal(2));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      Object localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList findSecCheckDetail(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList localArrayList = new ArrayList();
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select t1.voucherno v1,t2.usercode v2,t2.name v3,t1.amount v4,t1.debitaccountname v5 from v_accountbook t1,v_tradeuserext t2");
      localStringBuffer.append(" where t1.summaryno='01' and t1.creditaccountcode='201'||t1.marketid||t2.usercode");
      localStringBuffer.append(" " + paramString2 + " order by t2.usercode asc,t1.voucherno asc");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = new SecurityCheck();
        ((SecurityCheck)localObject1).setTradeNo(localResultSet.getString(1));
        ((SecurityCheck)localObject1).setUsercode(localResultSet.getString(2));
        ((SecurityCheck)localObject1).setCompanyName(localResultSet.getString(3));
        ((SecurityCheck)localObject1).setMoney(localResultSet.getBigDecimal(4));
        ((SecurityCheck)localObject1).setBank(localResultSet.getString(5));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      Object localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList findCurSecCheck(String paramString1, String paramString2, String paramString3)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList localArrayList = new ArrayList();
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select a.voucherno,d.accountname debitAccountName,b.accountcode creditAccountCode,");
      localStringBuffer.append("d.accountname debitAccountName,(select name from v_tradeuserext tu where b.accountcode=");
      localStringBuffer.append("'201'||a.marketId||tu.usercode) companyname,substr(b.accountcode,6,length(b.accountcode)) usercode,");
      localStringBuffer.append("case when (m.debitcount=n.creditcount) then a.debitamount when (m.debitcount<n.creditcount) ");
      localStringBuffer.append("then b.creditamount else a.debitamount end amount from (  select v.voucherdate,v.summaryNo,");
      localStringBuffer.append("v.vouchersummary,v.voucherno,v.contractno,v.audittime,ve.accountcode,ve.debitamount,v.marketid");
      localStringBuffer.append(" from voucher v,voucherentry ve where v.voucherno=ve.voucherNo and v.summaryno=01 and v.voucherstatus");
      localStringBuffer.append(" = 'audited' and ve.debitamount<>0 " + paramString2 + ") a,(  select v.voucherno,ve.accountcode,ve.creditamount ");
      localStringBuffer.append("from voucher v,voucherentry ve where v.voucherno=ve.voucherNo and v.summaryno=01 and v.voucherstatus ");
      localStringBuffer.append("= 'audited' and ve.creditamount<>0 " + paramString2 + ") b, account d,account e,(  select voucherentry.voucherno,count(1)");
      localStringBuffer.append(" debitcount from voucherentry,voucher where voucher.summaryno=01 and voucherstatus='audited' and");
      localStringBuffer.append(" voucherentry.voucherno=voucher.voucherno and debitamount<>0 " + paramString3 + " group by voucherentry.voucherno) m,");
      localStringBuffer.append("(  select voucherentry.voucherno,count(1) creditcount from voucherentry,voucher where voucher.summaryno=");
      localStringBuffer.append("01 and voucherstatus='audited' and voucherentry.voucherno=voucher.voucherno and creditamount<>0 " + paramString3 + "");
      localStringBuffer.append(" group by voucherentry.voucherno) n where a.voucherno = b.voucherno and a.accountcode=d.accountcode");
      localStringBuffer.append(" and b.accountcode=e.accountcode and a.voucherno=m.voucherno and b.voucherno=n.voucherno");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = new SecurityCheck();
        ((SecurityCheck)localObject1).setTradeNo(localResultSet.getString("voucherno"));
        ((SecurityCheck)localObject1).setUsercode(localResultSet.getString("usercode"));
        ((SecurityCheck)localObject1).setCompanyName(localResultSet.getString("companyname"));
        ((SecurityCheck)localObject1).setMoney(localResultSet.getBigDecimal("amount"));
        ((SecurityCheck)localObject1).setBank(localResultSet.getString("debitAccountName"));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      Object localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList findUserNotTrade(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList localArrayList = new ArrayList();
      localBigDecimal = null;
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select  (select sum(u1.balance) from v_tradeusermarket u1,v_tradeuser u2");
      localStringBuffer.append(" where u1.usercode=u2.usercode and u1.usercode=t1.usercode " + paramString4 + ")");
      localStringBuffer.append(" v1,t2.name v2,t1.frozencaptial v3,t2.usercode v4 from v_tradeuser ");
      localStringBuffer.append("t1,v_tradeuserext t2 where t1.usercode=t2.usercode and t1.status<>3");
      localStringBuffer.append(" and (select sum(u1.balance) from v_tradeusermarket u1,v_tradeuser u2");
      localStringBuffer.append(" where u1.usercode=u2.usercode and u1.usercode=t1.usercode " + paramString4 + ")>0");
      localStringBuffer.append(" and t1.usercode not in (select b.userid from " + paramString2 + " b,v_commodity");
      localStringBuffer.append(" c1,v_commext c where b.commodityid=c1.id and c1.id=c.commid " + paramString3 + ")");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = new UserNotTrade();
        ((UserNotTrade)localObject1).setCompanyName(localResultSet.getString(2));
        localBigDecimal = ManaUtil.disBD(localResultSet.getBigDecimal(1)).subtract(ManaUtil.disBD(localResultSet.getBigDecimal(3)));
        ((UserNotTrade)localObject1).setLastBail(localBigDecimal);
        ((UserNotTrade)localObject1).setUsercode(localResultSet.getString(4));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      Object localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      BigDecimal localBigDecimal = null;
      return localBigDecimal;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static boolean judgeExport(String paramString)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    try
    {
      localDBBean = new DBBean(paramString);
      boolean bool1 = true;
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select section from v_syscurstatus t1,v_syspartition t2 where t1.tradepartition=t2.partitionid");
      localStringBuffer.append(" and t2.validflag=1 and t1.status=2");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet.next()) {
        bool1 = false;
      }
      localResultSet.close();
      localDBBean.close();
      boolean bool3 = bool1;
      return bool3;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      boolean bool2 = false;
      return bool2;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static boolean judgeClose(String paramString)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    try
    {
      localDBBean = new DBBean(paramString);
      boolean bool1 = true;
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select section from v_syscurstatus t1,v_syspartition t2 where t1.tradepartition=t2.partitionid");
      localStringBuffer.append(" and t2.validflag=1 and t1.status<>5 and t1.status<>6");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet.next()) {
        bool1 = false;
      }
      localResultSet.close();
      localDBBean.close();
      boolean bool3 = bool1;
      return bool3;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      boolean bool2 = false;
      return bool2;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static String findMarketName(String paramString, int paramInt1, int paramInt2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    try
    {
      localDBBean = new DBBean(paramString);
      String str1 = null;
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select name from v_dictable where type='" + paramInt2 + "' and value='" + paramInt1 + "'");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet.next()) {
        str1 = localResultSet.getString("name");
      }
      localResultSet.close();
      localDBBean.close();
      String str2 = str1;
      return str2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static boolean judgeCommTrading(String paramString, int paramInt1, int paramInt2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    try
    {
      localDBBean = new DBBean(paramString);
      boolean bool1 = true;
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select * from v_syscurstatus where status=2 and tradepartition=" + paramInt1 + "");
      localStringBuffer.append(" and section=" + paramInt2 + "");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet.next()) {
        bool1 = false;
      }
      localResultSet.close();
      localDBBean.close();
      boolean bool3 = bool1;
      return bool3;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      boolean bool2 = false;
      return bool2;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList bargainIPCheck(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList localArrayList = new ArrayList();
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select t1.name v1,t2.userid v2,t2.tradepartition v3,t2.submitid v4");
      localStringBuffer.append(",t2.code v5,t2.contractid v6,t2.price v7,t2.amount v8,t2.tradedate v9,");
      localStringBuffer.append("t2.section v10,t5.ip v11 from v_tradeuserext t1," + paramString2 + " t2,v_commodity t3,v_commext t4");
      localStringBuffer.append("," + paramString3 + " t5 where t1.usercode=t2.userid and t2.commodityid=t3.id and ");
      localStringBuffer.append("t3.id=t4.commid and t2.submitid=t5.id" + paramString4 + " order by t2.contractid desc");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = new BargainIPCheck();
        ((BargainIPCheck)localObject1).setCompanyName(localResultSet.getString(1));
        ((BargainIPCheck)localObject1).setUserId(localResultSet.getString(2));
        ((BargainIPCheck)localObject1).setSubmitId(localResultSet.getString(4));
        ((BargainIPCheck)localObject1).setCode(localResultSet.getString(5));
        ((BargainIPCheck)localObject1).setContractId(localResultSet.getString(6));
        ((BargainIPCheck)localObject1).setPrice(localResultSet.getBigDecimal(7));
        ((BargainIPCheck)localObject1).setTradeAmount(localResultSet.getBigDecimal(8));
        ((BargainIPCheck)localObject1).setTradeDate(localResultSet.getString(9).substring(0, 10));
        ((BargainIPCheck)localObject1).setSection(localResultSet.getString(10));
        ((BargainIPCheck)localObject1).setIp(localResultSet.getString(11));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      Object localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList exportForeignBar(String paramString1, String paramString2, String paramString3)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    String str1 = "";
    String str2 = "";
    try
    {
      localDBBean = new DBBean(paramString1);
      if ("1".equals(paramString2))
      {
        str1 = "bargain";
        str2 = "dailymoney";
      }
      else if ("2".equals(paramString2))
      {
        str1 = "hisbargain";
        str2 = "hismoney";
      }
      ArrayList localArrayList = new ArrayList();
      localStringBuffer = new StringBuffer();
      String str3 = null;
      localStringBuffer.append("select distinct b.contractID v1,b.userid v2,b.code v3,m.name v4,c.str1 v5,b.price v6,b.amount v7,");
      localStringBuffer.append("b.price*b.amount v8,b.section v9,b.tradedate v10,b.submitid v11 from " + str1 + "");
      localStringBuffer.append(" b,commext c,commodity c1,tradeuser t1,tradeuserext t2," + str2 + " dm,marketallcall m ");
      localStringBuffer.append("where c1.id=b.commodityID and c1.id=c.commid and to_char(m.marketid)=c.str7");
      localStringBuffer.append(" and b.userid=t1.usercode and t1.usercode=t2.usercode and b.contractid=dm.contractno and ");
      localStringBuffer.append("dm.market1<>dm.market2 and (dm.operation=504 or dm.operation=503) " + paramString3 + " order by b.tradedate desc,b.contractid desc");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet1.next())
      {
        localObject1 = new ForeignBargain();
        str3 = localResultSet1.getString(1);
        ((ForeignBargain)localObject1).setContractId(str3);
        ((ForeignBargain)localObject1).setUsercode(localResultSet1.getString(2));
        ((ForeignBargain)localObject1).setCode(localResultSet1.getString(3));
        ((ForeignBargain)localObject1).setBelongMarket(localResultSet1.getString(4));
        ((ForeignBargain)localObject1).setVari(localResultSet1.getString(5));
        ((ForeignBargain)localObject1).setPrice(localResultSet1.getBigDecimal(6));
        ((ForeignBargain)localObject1).setTradeAmount(localResultSet1.getBigDecimal(7));
        ((ForeignBargain)localObject1).setTradeMoney(localResultSet1.getBigDecimal(8));
        ((ForeignBargain)localObject1).setTradeDate(localResultSet1.getString(10).substring(0, 10));
        ((ForeignBargain)localObject1).setSubmitId(localResultSet1.getString(11));
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select sum(money) v1 from " + str2 + " where (operation=503 or operation=504)");
        localStringBuffer.append(" and contractno=" + ((ForeignBargain)localObject1).getContractId() + " and firmid='" + ((ForeignBargain)localObject1).getUsercode() + "' ");
        localStringBuffer.append(" and market1=market2");
        localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
        if (localResultSet2.next()) {
          ((ForeignBargain)localObject1).setSelfSecurity(ManaUtil.disBD(localResultSet2.getBigDecimal(1)));
        } else {
          ((ForeignBargain)localObject1).setSelfSecurity(new BigDecimal(0));
        }
        localResultSet2.close();
        localDBBean.closeStmt();
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select sum(money) v1,(select name from marketallcall where marketid=market2");
        localStringBuffer.append(") v2 from " + str2 + " where market1<>market2 and (operation=504 or operation=503)");
        localStringBuffer.append(" and contractno=" + ((ForeignBargain)localObject1).getContractId() + " and firmid='" + ((ForeignBargain)localObject1).getUsercode() + "' ");
        localStringBuffer.append("group by market1,market2");
        localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
        while (localResultSet2.next())
        {
          ForeignBargain localForeignBargain = new ForeignBargain();
          localForeignBargain.setContractId(((ForeignBargain)localObject1).getContractId());
          localForeignBargain.setUsercode(((ForeignBargain)localObject1).getUsercode());
          localForeignBargain.setCode(((ForeignBargain)localObject1).getCode());
          localForeignBargain.setBelongMarket(((ForeignBargain)localObject1).getBelongMarket());
          localForeignBargain.setVari(((ForeignBargain)localObject1).getVari());
          localForeignBargain.setPrice(((ForeignBargain)localObject1).getPrice());
          localForeignBargain.setTradeAmount(((ForeignBargain)localObject1).getTradeAmount());
          localForeignBargain.setTradeMoney(((ForeignBargain)localObject1).getTradeMoney());
          localForeignBargain.setTradeDate(((ForeignBargain)localObject1).getTradeDate());
          localForeignBargain.setSubmitId(((ForeignBargain)localObject1).getSubmitId());
          localForeignBargain.setSelfSecurity(((ForeignBargain)localObject1).getSelfSecurity());
          localForeignBargain.setTradeMarket(localResultSet2.getString(2));
          localForeignBargain.setSecurity(ManaUtil.disBD(localResultSet2.getBigDecimal(1)));
          localArrayList.add(localForeignBargain);
        }
        localResultSet2.close();
        localDBBean.closeStmt();
      }
      localResultSet1.close();
      localDBBean.close();
      Object localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static BigDecimal queryCurProAmount(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select sum(c1.amount) as s from bargain b,commodity c1,commExt c,tradeUserExt u,");
      localStringBuffer.append("dictable d where b.userID=u.userCode and b.commodityID=c1.id and c.commid=c1.id and d.type=");
      localStringBuffer.append("" + paramString3 + " and c.str6=d.value " + paramString2 + " and c.str6='" + paramString4 + "'");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet.next()) {
        localBigDecimal1 = localResultSet.getBigDecimal("s");
      }
      localResultSet.close();
      localDBBean.close();
      BigDecimal localBigDecimal2 = localBigDecimal1;
      return localBigDecimal2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static BigDecimal queryHisProAmount(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select sum(c1.amount) as s from v_hisbargain b,v_commodity c1,v_commExt c,v_tradeUserExt u,");
      localStringBuffer.append("v_dictable d where b.userID=u.userCode and b.commodityID=c1.id and c.commid=c1.id and d.type=");
      localStringBuffer.append("" + paramString3 + " and c.str6=d.value " + paramString2 + " and c.str6='" + paramString4 + "'");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet.next()) {
        localBigDecimal1 = localResultSet.getBigDecimal("s");
      }
      localResultSet.close();
      localDBBean.close();
      BigDecimal localBigDecimal2 = localBigDecimal1;
      return localBigDecimal2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList[] getCurTradeResult(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList[] arrayOfArrayList1 = new ArrayList[3];
      arrayOfArrayList1[0] = new ArrayList();
      arrayOfArrayList1[1] = new ArrayList();
      arrayOfArrayList1[2] = new ArrayList();
      localStringBuffer1 = new StringBuffer();
      int i = 0;
      HashMap localHashMap = new HashMap();
      localStringBuffer1.append("select count(distinct t2.str1) as v from v_commodity t1,v_commext t2," + paramString5 + " t3 where t1.id=");
      localStringBuffer1.append("t2.commid and t3.commodityid=t1.id" + paramString2);
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      if (localResultSet1.next()) {
        i = localResultSet1.getInt("v");
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      String[][] arrayOfString = new String[i][6];
      localStringBuffer1 = new StringBuffer();
      localStringBuffer1.append("select distinct t2.str1 from v_commodity t1,v_commext t2," + paramString5 + " t3 where t1.id=");
      localStringBuffer1.append("t2.commid and t3.commodityid=t1.id" + paramString2);
      int j = 0;
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      while (localResultSet1.next())
      {
        localHashMap.put(localResultSet1.getString("str1"), new Integer(j));
        arrayOfString[j][0] = localResultSet1.getString("str1");
        arrayOfString[j][1] = "0";
        arrayOfString[j][2] = "0";
        arrayOfString[j][3] = "0";
        arrayOfString[j][4] = "0";
        arrayOfString[j][5] = "0";
        j++;
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      localStringBuffer1 = new StringBuffer();
      localStringBuffer1.append("select value,name from v_dictable where type=" + paramString4 + "");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      BigDecimal localBigDecimal3 = new BigDecimal(0);
      BigDecimal localBigDecimal4 = new BigDecimal(0);
      BigDecimal localBigDecimal5 = new BigDecimal(0);
      BigDecimal localBigDecimal6 = new BigDecimal(0);
      BigDecimal localBigDecimal7 = new BigDecimal(0);
      BigDecimal localBigDecimal8 = new BigDecimal(0);
      BigDecimal localBigDecimal9 = null;
      BigDecimal localBigDecimal10 = null;
      BigDecimal localBigDecimal11 = null;
      ResultSet localResultSet3 = null;
      Object localObject1 = null;
      Object localObject2;
      Object localObject3;
      BigDecimal localBigDecimal12;
      Object localObject4;
      while (localResultSet1.next())
      {
        localObject2 = new BigDecimal(0);
        localObject3 = new BigDecimal(0);
        localBigDecimal12 = new BigDecimal(0);
        localObject4 = new XJTradeResult();
        String str1 = localResultSet1.getString("name");
        String str2 = localResultSet1.getString("value");
        StringBuffer localStringBuffer2 = new StringBuffer();
        localStringBuffer2.append("select sum(amount) as planamount,str1 from( select " + paramString7 + " ");
        localStringBuffer2.append("t1.*,t2.str1 from v_commodity t1,v_commext t2," + paramString5 + " t3 ");
        localStringBuffer2.append("where t1.id=t2.commid and t3.commodityid=t1.id and t2.str6='" + str2 + "'");
        localStringBuffer2.append(" " + paramString2 + ") group by str1");
        localResultSet3 = localDBBean.executeQuery(localStringBuffer2.toString());
        int m = 0;
        ArrayList localArrayList = new ArrayList();
        while (localResultSet3.next())
        {
          if (m == 0) {
            ((XJTradeResult)localObject4).setProName(str1);
          }
          TradeResult localTradeResult = new TradeResult();
          localBigDecimal1 = ManaUtil.disBD(localResultSet3.getBigDecimal("planamount"));
          localTradeResult.setPlanAmount(localBigDecimal1);
          localObject2 = ((BigDecimal)localObject2).add(localBigDecimal1);
          localBigDecimal6 = localBigDecimal6.add(localBigDecimal1);
          String str3 = localResultSet3.getString("str1");
          localTradeResult.setVari(str3);
          if (localHashMap.containsKey(str3))
          {
            localObject5 = (Integer)localHashMap.get(str3);
            arrayOfString[localObject5.intValue()][1] = String.valueOf(new BigDecimal(arrayOfString[localObject5.intValue()][1]).add(localBigDecimal1));
          }
          Object localObject5 = new StringBuffer();
          ((StringBuffer)localObject5).append("select sum(t1.amount) as su,max(t1.price) as ma,min(t1.price) as mi");
          ((StringBuffer)localObject5).append(",sum(t1.price*t1.amount)/sum(t1.amount) as av,sum(t1.price*t1.amount)");
          ((StringBuffer)localObject5).append(" as trademoney from " + paramString6 + " t1,v_commodity t2,v_commext t3 where t2.id=");
          ((StringBuffer)localObject5).append("t3.commid and t1.commodityid=t2.id and t3.str6='" + str2 + "' and t3.str1='" + str3 + "'");
          ((StringBuffer)localObject5).append(" " + paramString3 + "");
          localResultSet2 = localDBBean.executeQuery(((StringBuffer)localObject5).toString());
          if (localResultSet2.next())
          {
            localBigDecimal2 = ManaUtil.disBD(localResultSet2.getBigDecimal("su"));
            localTradeResult.setTradeAmount(localBigDecimal2);
            localObject3 = ((BigDecimal)localObject3).add(localBigDecimal2);
            localBigDecimal7 = localBigDecimal7.add(localBigDecimal2);
            localBigDecimal3 = ManaUtil.disBD(localResultSet2.getBigDecimal("ma"));
            localTradeResult.setMaxPrice(localBigDecimal3);
            localBigDecimal4 = ManaUtil.disBD(localResultSet2.getBigDecimal("mi"));
            localTradeResult.setMinPrice(localBigDecimal4);
            localBigDecimal5 = ManaUtil.disBD(localResultSet2.getBigDecimal("trademoney"));
            localTradeResult.setTradeMoney(localBigDecimal5);
            localBigDecimal12 = localBigDecimal12.add(localBigDecimal5);
            localBigDecimal8 = localBigDecimal8.add(localBigDecimal5);
            Integer localInteger;
            if (localHashMap.containsKey(str3))
            {
              localInteger = (Integer)localHashMap.get(str3);
              arrayOfString[localInteger.intValue()][2] = String.valueOf(new BigDecimal(arrayOfString[localInteger.intValue()][2]).add(localBigDecimal2));
            }
            if (localHashMap.containsKey(str3))
            {
              localInteger = (Integer)localHashMap.get(str3);
              arrayOfString[localInteger.intValue()][3] = String.valueOf(new BigDecimal(arrayOfString[localInteger.intValue()][3]).add(localBigDecimal5));
            }
            if (localHashMap.containsKey(str3))
            {
              localInteger = (Integer)localHashMap.get(str3);
              arrayOfString[localInteger.intValue()][4] = String.valueOf(localBigDecimal3);
            }
            if (localHashMap.containsKey(str3))
            {
              localInteger = (Integer)localHashMap.get(str3);
              arrayOfString[localInteger.intValue()][5] = String.valueOf(localBigDecimal4);
            }
            localBigDecimal9 = localBigDecimal5;
            localBigDecimal10 = localBigDecimal2;
            if (localBigDecimal10.compareTo(new BigDecimal(0)) <= 0) {
              localBigDecimal10 = new BigDecimal(1);
            }
            localBigDecimal11 = localBigDecimal9.divide(localBigDecimal10, 0, 4);
            localTradeResult.setAvgPrice(localBigDecimal11);
          }
          localResultSet2.close();
          localDBBean.closeStmt();
          if (localBigDecimal1.compareTo(new BigDecimal(0)) > 0) {
            localTradeResult.setTradeRatio(ManaUtil.accuracyNum(localBigDecimal2.divide(localBigDecimal1, 4, 4).multiply(new BigDecimal(100)), ".##"));
          } else {
            localTradeResult.setTradeRatio(new BigDecimal(0.0D));
          }
          localArrayList.add(localTradeResult);
          m++;
        }
        localResultSet3.close();
        localDBBean.closeStmt();
        if (localArrayList.size() > 0)
        {
          ((XJTradeResult)localObject4).setTradeResult(localArrayList);
          if (((BigDecimal)localObject2).compareTo(new BigDecimal(0)) > 0)
          {
            ((XJTradeResult)localObject4).setXJPlanAmount((BigDecimal)localObject2);
            ((XJTradeResult)localObject4).setXJTradeAmount((BigDecimal)localObject3);
            ((XJTradeResult)localObject4).setXJTradeRatio(ManaUtil.accuracyNum(((BigDecimal)localObject3).divide((BigDecimal)localObject2, 4, 4).multiply(new BigDecimal(100)), ".##"));
            ((XJTradeResult)localObject4).setXJTradeMoney(localBigDecimal12);
          }
          arrayOfArrayList1[0].add(localObject4);
        }
      }
      localResultSet1.close();
      localDBBean.close();
      if (localBigDecimal6.compareTo(new BigDecimal(0)) > 0)
      {
        localObject2 = new HJTradeResult();
        ((HJTradeResult)localObject2).setHJPlanAmount(localBigDecimal6);
        ((HJTradeResult)localObject2).setHJTradeAmount(localBigDecimal7);
        ((HJTradeResult)localObject2).setHJTradeRatio(ManaUtil.accuracyNum(localBigDecimal7.divide(localBigDecimal6, 4, 4).multiply(new BigDecimal(100)), ".##"));
        ((HJTradeResult)localObject2).setHJTradeMoney(localBigDecimal8);
        arrayOfArrayList1[1].add(localObject2);
      }
      for (int k = 0; k < arrayOfString.length; k++) {
        if (new BigDecimal(arrayOfString[k][1]).compareTo(new BigDecimal(0)) > 0)
        {
          localObject3 = new VariTradeResult();
          ((VariTradeResult)localObject3).setVari(arrayOfString[k][0]);
          ((VariTradeResult)localObject3).setVPlanAmount(new BigDecimal(arrayOfString[k][1]));
          ((VariTradeResult)localObject3).setVTradeAmount(new BigDecimal(arrayOfString[k][2]));
          localBigDecimal12 = new BigDecimal(arrayOfString[k][2]);
          if (localBigDecimal12.compareTo(new BigDecimal(0)) <= 0) {
            localBigDecimal12 = new BigDecimal(1);
          }
          localObject4 = new BigDecimal(arrayOfString[k][3]).divide(localBigDecimal12, 0, 4);
          ((VariTradeResult)localObject3).setVAvgPrice((BigDecimal)localObject4);
          ((VariTradeResult)localObject3).setVTradeRatio(ManaUtil.accuracyNum(new BigDecimal(arrayOfString[k][2]).divide(new BigDecimal(arrayOfString[k][1]), 4, 4).multiply(new BigDecimal(100)), ".##"));
          ((VariTradeResult)localObject3).setVTradeMoney(new BigDecimal(arrayOfString[k][3]));
          arrayOfArrayList1[2].add(localObject3);
        }
      }
      ArrayList[] arrayOfArrayList2 = arrayOfArrayList1;
      return arrayOfArrayList2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer1 = null;
      return localStringBuffer1;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList[] getProLiftedGoods(String paramString1, String paramString2, String paramString3)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList[] arrayOfArrayList = new ArrayList[2];
      arrayOfArrayList[0] = new ArrayList();
      arrayOfArrayList[1] = new ArrayList();
      localStringBuffer = new StringBuffer();
      int i = 0;
      localStringBuffer.append("select count(pro) n from (select t2.str6 pro from v_commodity t1,v_commext t2,v_hisbargain t3 where t1.id=t2.commid");
      localStringBuffer.append(" and t1.id=t3.commodityid " + paramString2 + " group by (t2.str6,t2.str1) order by t2.str6 asc )");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet1.next()) {
        i = localResultSet1.getInt("n");
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select (select name from v_dictable where type=" + paramString3 + " and value=t2.str6) v1,t2.str1 v2,sum(t3.amount)");
      localStringBuffer.append(" v3,t2.str6 v4,sum(t3.amount*t3.price) v5 from v_commodity t1,v_commext t2,v_hisbargain t3 where t1.id=t2.commid");
      localStringBuffer.append(" and t1.id=t3.commodityid " + paramString2 + " group by (t2.str6,t2.str1) order by t2.str6 asc ");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      Object localObject1 = "";
      Object localObject2 = "";
      String str1 = "";
      String str2 = "";
      Object localObject3 = "";
      String str3 = "";
      Object localObject4 = new BigDecimal(0);
      Object localObject5 = new BigDecimal(0);
      Object localObject6 = new BigDecimal(0);
      Object localObject7 = new BigDecimal(0);
      Object localObject8 = new BigDecimal(0);
      Object localObject9 = new BigDecimal(0);
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      BigDecimal localBigDecimal3 = new BigDecimal(0);
      BigDecimal localBigDecimal4 = new BigDecimal(0);
      BigDecimal localBigDecimal5 = new BigDecimal(0);
      BigDecimal localBigDecimal6 = new BigDecimal(0);
      BigDecimal localBigDecimal7 = new BigDecimal(0);
      BigDecimal localBigDecimal8 = new BigDecimal(0);
      BigDecimal localBigDecimal9 = new BigDecimal(0);
      BigDecimal localBigDecimal10 = new BigDecimal(0);
      BigDecimal localBigDecimal11 = new BigDecimal(0);
      BigDecimal localBigDecimal12 = new BigDecimal(0);
      ArrayList localArrayList = new ArrayList();
      for (int j = 0; localResultSet1.next(); j++)
      {
        localBigDecimal1 = new BigDecimal(0);
        localBigDecimal3 = new BigDecimal(0);
        localBigDecimal2 = new BigDecimal(0);
        localBigDecimal4 = new BigDecimal(0);
        localBigDecimal5 = new BigDecimal(0);
        localBigDecimal6 = new BigDecimal(0);
        str2 = localResultSet1.getString(4);
        str3 = localResultSet1.getString(1);
        localObject2 = str2;
        str1 = localResultSet1.getString(2);
        localObject10 = new VariLiftedGoods();
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select sum(t4.amount) v1,sum(t4.capital) v2 from v_commodity t1,v_commext t2,v_hisbargain t3,v_outlog t4 where");
        localStringBuffer.append(" t1.id=t2.commid and t1.id=t3.commodityid and t3.contractid=t4.contractid and t2.str1=");
        localStringBuffer.append("'" + str1 + "' and t4.finished=2 and t2.str6='" + (String)localObject2 + "' " + paramString2 + "");
        localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
        if (localResultSet2.next())
        {
          localBigDecimal3 = ManaUtil.disBD(localResultSet2.getBigDecimal(1));
          localBigDecimal4 = ManaUtil.disBD(localResultSet2.getBigDecimal(2));
          ((VariLiftedGoods)localObject10).setLiftedAmount(localBigDecimal3);
          ((VariLiftedGoods)localObject10).setLiftedMoney(localBigDecimal4);
        }
        localResultSet2.close();
        localDBBean.closeStmt();
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select sum(t4.money) v1,sum(t4.money/case when t3.price>t2.num1 then t2.num1 else t3.price end)");
        localStringBuffer.append(" v2 from v_commodity t1,v_commext t2,v_hisbargain t3,v_hismoney t4 where t1.id=t2.commid and t1.id=");
        localStringBuffer.append("t3.commodityid and t3.contractid=t4.contractno and t2.str1='" + str1 + "' and t4.operation=512");
        localStringBuffer.append(" and t2.str6='" + (String)localObject2 + "' " + paramString2 + "");
        localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
        if (localResultSet2.next())
        {
          localBigDecimal5 = ManaUtil.disBD(localResultSet2.getBigDecimal(1));
          localBigDecimal6 = ManaUtil.disBD(localResultSet2.getBigDecimal(2));
          ((VariLiftedGoods)localObject10).setLKMoney(localBigDecimal5);
          ((VariLiftedGoods)localObject10).setLKAmount(localBigDecimal6);
        }
        localResultSet2.close();
        localDBBean.closeStmt();
        localBigDecimal1 = ManaUtil.disBD(localResultSet1.getBigDecimal(3));
        localBigDecimal2 = ManaUtil.disBD(localResultSet1.getBigDecimal(5));
        localBigDecimal7 = localBigDecimal7.add(localBigDecimal1);
        localBigDecimal8 = localBigDecimal8.add(localBigDecimal3);
        localBigDecimal9 = localBigDecimal9.add(localBigDecimal5);
        localBigDecimal10 = localBigDecimal10.add(localBigDecimal2);
        localBigDecimal11 = localBigDecimal11.add(localBigDecimal4);
        localBigDecimal12 = localBigDecimal12.add(localBigDecimal6);
        if (j == 0)
        {
          localObject1 = str2;
          localObject3 = str3;
          localObject4 = localBigDecimal1;
          localObject5 = localBigDecimal3;
          localObject6 = localBigDecimal5;
          localObject7 = localBigDecimal2;
          localObject8 = localBigDecimal4;
          localObject9 = localBigDecimal6;
        }
        ProLiftedGoods localProLiftedGoods;
        if (!((String)localObject2).equals(localObject1))
        {
          localProLiftedGoods = new ProLiftedGoods();
          localProLiftedGoods.setProId(Integer.parseInt((String)localObject2));
          localProLiftedGoods.setProName((String)localObject3);
          localProLiftedGoods.setVariLiftedGoods(localArrayList);
          localProLiftedGoods.setZJTradeAmount((BigDecimal)localObject4);
          localProLiftedGoods.setZJLiftedAmount((BigDecimal)localObject5);
          localProLiftedGoods.setZJLKMoney((BigDecimal)localObject6);
          localProLiftedGoods.setZJTradeMoney((BigDecimal)localObject7);
          localProLiftedGoods.setZJLiftedMoney((BigDecimal)localObject8);
          localProLiftedGoods.setZJLKAmount((BigDecimal)localObject9);
          arrayOfArrayList[0].add(localProLiftedGoods);
          localArrayList = new ArrayList();
          localObject1 = localObject2;
          localObject3 = str3;
          localObject4 = localBigDecimal1;
          localObject5 = localBigDecimal3;
          localObject6 = localBigDecimal5;
          localObject7 = localBigDecimal2;
          localObject8 = localBigDecimal4;
          localObject9 = localBigDecimal6;
        }
        else if (j > 0)
        {
          localObject4 = ((BigDecimal)localObject4).add(localBigDecimal1);
          localObject5 = ((BigDecimal)localObject5).add(localBigDecimal3);
          localObject6 = ((BigDecimal)localObject6).add(localBigDecimal5);
          localObject7 = ((BigDecimal)localObject7).add(localBigDecimal2);
          localObject8 = ((BigDecimal)localObject8).add(localBigDecimal4);
          localObject9 = ((BigDecimal)localObject9).add(localBigDecimal6);
        }
        ((VariLiftedGoods)localObject10).setTradeAmount(localBigDecimal1);
        ((VariLiftedGoods)localObject10).setTradeMoney(localBigDecimal2);
        ((VariLiftedGoods)localObject10).setVari(str1);
        localArrayList.add(localObject10);
        if (j == i - 1)
        {
          localProLiftedGoods = new ProLiftedGoods();
          localProLiftedGoods.setProId(Integer.parseInt((String)localObject2));
          localProLiftedGoods.setProName((String)localObject3);
          localProLiftedGoods.setVariLiftedGoods(localArrayList);
          localProLiftedGoods.setZJTradeAmount((BigDecimal)localObject4);
          localProLiftedGoods.setZJLiftedAmount((BigDecimal)localObject5);
          localProLiftedGoods.setZJLKMoney((BigDecimal)localObject6);
          localProLiftedGoods.setZJTradeMoney((BigDecimal)localObject7);
          localProLiftedGoods.setZJLiftedMoney((BigDecimal)localObject8);
          localProLiftedGoods.setZJLKAmount((BigDecimal)localObject9);
          arrayOfArrayList[0].add(localProLiftedGoods);
        }
      }
      localResultSet1.close();
      localDBBean.close();
      if (arrayOfArrayList[0].size() > 0)
      {
        localObject10 = new ZJProLifted();
        ((ZJProLifted)localObject10).setZJTradeAmount(localBigDecimal7);
        ((ZJProLifted)localObject10).setZJLiftedAmount(localBigDecimal8);
        ((ZJProLifted)localObject10).setZJLKMoney(localBigDecimal9);
        ((ZJProLifted)localObject10).setZJTradeMoney(localBigDecimal10);
        ((ZJProLifted)localObject10).setZJLiftedMoney(localBigDecimal11);
        ((ZJProLifted)localObject10).setZJLKAmount(localBigDecimal12);
        arrayOfArrayList[1].add(localObject10);
      }
      Object localObject10 = arrayOfArrayList;
      return localObject10;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList[] getProLiftedGoodsExt(String paramString1, String paramString2, String paramString3)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList[] arrayOfArrayList1 = new ArrayList[3];
      arrayOfArrayList1[0] = new ArrayList();
      arrayOfArrayList1[1] = new ArrayList();
      arrayOfArrayList1[2] = new ArrayList();
      localStringBuffer = new StringBuffer();
      int i = 0;
      int j = 0;
      HashMap localHashMap = new HashMap();
      localStringBuffer.append("select count(distinct t2.str1) v1 from v_commodity t1,v_commext t2,v_hisbargain t3 where t1.id=t2.commid");
      localStringBuffer.append(" and t1.id=t3.commodityid " + paramString2 + "");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet1.next()) {
        j = localResultSet1.getInt(1);
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      String[][] arrayOfString = new String[j][9];
      int k = 0;
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select distinct t2.str1 v1 from v_commodity t1,v_commext t2,v_hisbargain t3 where t1.id=t2.commid");
      localStringBuffer.append(" and t1.id=t3.commodityid " + paramString2 + "");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet1.next())
      {
        localHashMap.put(localResultSet1.getString(1), new Integer(k));
        arrayOfString[k][0] = localResultSet1.getString(1);
        arrayOfString[k][1] = "0";
        arrayOfString[k][2] = "0";
        arrayOfString[k][3] = "0";
        arrayOfString[k][4] = "0";
        arrayOfString[k][5] = "0";
        arrayOfString[k][6] = "0";
        arrayOfString[k][7] = "0";
        arrayOfString[k][8] = "0";
        k++;
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select count(pro) n from (select t2.str6 pro from v_commodity t1,v_commext t2,v_hisbargain t3 where t1.id=t2.commid");
      localStringBuffer.append(" and t1.id=t3.commodityid " + paramString2 + " group by (t2.str6,t2.str1) order by t2.str6 asc )");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet1.next()) {
        i = localResultSet1.getInt("n");
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select (select name from dictable where type=" + paramString3 + " and value=t2.str6) v1,t2.str1 v2,sum(t3.amount)");
      localStringBuffer.append(" v3,t2.str6 v4,sum(t3.amount*t3.price) v5 from commodity t1,commext t2,hisbargain t3 where t1.id=t2.commid");
      localStringBuffer.append(" and t1.id=t3.commodityid " + paramString2 + " group by (t2.str6,t2.str1) order by t2.str6 asc ");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      Object localObject1 = "";
      Object localObject2 = "";
      String str1 = "";
      String str2 = "";
      Object localObject3 = "";
      String str3 = "";
      Object localObject4 = new BigDecimal(0);
      Object localObject5 = new BigDecimal(0);
      Object localObject6 = new BigDecimal(0);
      Object localObject7 = new BigDecimal(0);
      Object localObject8 = new BigDecimal(0);
      Object localObject9 = new BigDecimal(0);
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      BigDecimal localBigDecimal3 = new BigDecimal(0);
      BigDecimal localBigDecimal4 = new BigDecimal(0);
      BigDecimal localBigDecimal5 = new BigDecimal(0);
      BigDecimal localBigDecimal6 = new BigDecimal(0);
      BigDecimal localBigDecimal7 = new BigDecimal(0);
      BigDecimal localBigDecimal8 = new BigDecimal(0);
      BigDecimal localBigDecimal9 = new BigDecimal(0);
      BigDecimal localBigDecimal10 = new BigDecimal(0);
      BigDecimal localBigDecimal11 = new BigDecimal(0);
      BigDecimal localBigDecimal12 = new BigDecimal(0);
      ArrayList localArrayList = new ArrayList();
      Object localObject10;
      Object localObject11;
      for (int m = 0; localResultSet1.next(); m++)
      {
        localBigDecimal1 = new BigDecimal(0);
        localBigDecimal3 = new BigDecimal(0);
        localBigDecimal2 = new BigDecimal(0);
        localBigDecimal4 = new BigDecimal(0);
        localBigDecimal5 = new BigDecimal(0);
        localBigDecimal6 = new BigDecimal(0);
        str2 = localResultSet1.getString(4);
        str3 = localResultSet1.getString(1);
        localObject2 = str2;
        str1 = localResultSet1.getString(2);
        localObject10 = new VariLiftedGoods();
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select sum(t4.amount) v1,sum(t4.capital) v2 from v_commodity t1,v_commext t2,v_hisbargain t3,v_outlog t4 where");
        localStringBuffer.append(" t1.id=t2.commid and t1.id=t3.commodityid and t3.contractid=t4.contractid and t2.str1=");
        localStringBuffer.append("'" + str1 + "' and t4.finished=2 and t2.str6='" + (String)localObject2 + "' " + paramString2 + "");
        localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
        if (localResultSet2.next())
        {
          localBigDecimal3 = ManaUtil.disBD(localResultSet2.getBigDecimal(1));
          localBigDecimal4 = ManaUtil.disBD(localResultSet2.getBigDecimal(2));
          ((VariLiftedGoods)localObject10).setLiftedAmount(localBigDecimal3);
          ((VariLiftedGoods)localObject10).setLiftedMoney(localBigDecimal4);
        }
        localResultSet2.close();
        localDBBean.closeStmt();
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select sum(t4.money) v1,sum(t4.money/case when t3.price>t2.num1 then t2.num1 else t3.price end)");
        localStringBuffer.append(" v2 from v_commodity t1,v_commext t2,v_hisbargain t3,v_hismoney t4 where t1.id=t2.commid and t1.id=");
        localStringBuffer.append("t3.commodityid and t3.contractid=t4.contractno and t2.str1='" + str1 + "' and t4.operation=512");
        localStringBuffer.append(" and t2.str6='" + (String)localObject2 + "' " + paramString2 + "");
        localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
        if (localResultSet2.next())
        {
          localBigDecimal5 = ManaUtil.disBD(localResultSet2.getBigDecimal(1));
          localBigDecimal6 = ManaUtil.disBD(localResultSet2.getBigDecimal(2));
          ((VariLiftedGoods)localObject10).setLKMoney(localBigDecimal5);
          ((VariLiftedGoods)localObject10).setLKAmount(localBigDecimal6);
        }
        localResultSet2.close();
        localDBBean.closeStmt();
        localBigDecimal1 = ManaUtil.disBD(localResultSet1.getBigDecimal(3));
        localBigDecimal2 = ManaUtil.disBD(localResultSet1.getBigDecimal(5));
        localBigDecimal7 = localBigDecimal7.add(localBigDecimal1);
        localBigDecimal8 = localBigDecimal8.add(localBigDecimal3);
        localBigDecimal9 = localBigDecimal9.add(localBigDecimal5);
        localBigDecimal10 = localBigDecimal10.add(localBigDecimal2);
        localBigDecimal11 = localBigDecimal11.add(localBigDecimal4);
        localBigDecimal12 = localBigDecimal12.add(localBigDecimal6);
        if (m == 0)
        {
          localObject1 = str2;
          localObject3 = str3;
          localObject4 = localBigDecimal1;
          localObject5 = localBigDecimal3;
          localObject6 = localBigDecimal5;
          localObject7 = localBigDecimal2;
          localObject8 = localBigDecimal4;
          localObject9 = localBigDecimal6;
        }
        if (!((String)localObject2).equals(localObject1))
        {
          localObject11 = new ProLiftedGoods();
          ((ProLiftedGoods)localObject11).setProId(Integer.parseInt((String)localObject2));
          ((ProLiftedGoods)localObject11).setProName((String)localObject3);
          ((ProLiftedGoods)localObject11).setVariLiftedGoods(localArrayList);
          ((ProLiftedGoods)localObject11).setZJTradeAmount((BigDecimal)localObject4);
          ((ProLiftedGoods)localObject11).setZJLiftedAmount((BigDecimal)localObject5);
          ((ProLiftedGoods)localObject11).setZJLKMoney((BigDecimal)localObject6);
          ((ProLiftedGoods)localObject11).setZJTradeMoney((BigDecimal)localObject7);
          ((ProLiftedGoods)localObject11).setZJLiftedMoney((BigDecimal)localObject8);
          ((ProLiftedGoods)localObject11).setZJLKAmount((BigDecimal)localObject9);
          arrayOfArrayList1[0].add(localObject11);
          localArrayList = new ArrayList();
          localObject1 = localObject2;
          localObject3 = str3;
          localObject4 = localBigDecimal1;
          localObject5 = localBigDecimal3;
          localObject6 = localBigDecimal5;
          localObject7 = localBigDecimal2;
          localObject8 = localBigDecimal4;
          localObject9 = localBigDecimal6;
        }
        else if (m > 0)
        {
          localObject4 = ((BigDecimal)localObject4).add(localBigDecimal1);
          localObject5 = ((BigDecimal)localObject5).add(localBigDecimal3);
          localObject6 = ((BigDecimal)localObject6).add(localBigDecimal5);
          localObject7 = ((BigDecimal)localObject7).add(localBigDecimal2);
          localObject8 = ((BigDecimal)localObject8).add(localBigDecimal4);
          localObject9 = ((BigDecimal)localObject9).add(localBigDecimal6);
        }
        if (localHashMap.containsKey(str1))
        {
          localObject11 = (Integer)localHashMap.get(str1);
          arrayOfString[localObject11.intValue()][1] = String.valueOf(new BigDecimal(arrayOfString[localObject11.intValue()][1]).add(localBigDecimal1));
          arrayOfString[localObject11.intValue()][2] = String.valueOf(new BigDecimal(arrayOfString[localObject11.intValue()][2]).add(localBigDecimal2));
          arrayOfString[localObject11.intValue()][3] = String.valueOf(new BigDecimal(arrayOfString[localObject11.intValue()][3]).add(localBigDecimal3));
          arrayOfString[localObject11.intValue()][4] = String.valueOf(new BigDecimal(arrayOfString[localObject11.intValue()][4]).add(localBigDecimal4));
          arrayOfString[localObject11.intValue()][5] = String.valueOf(new BigDecimal(arrayOfString[localObject11.intValue()][5]).add(localBigDecimal6));
          arrayOfString[localObject11.intValue()][6] = String.valueOf(new BigDecimal(arrayOfString[localObject11.intValue()][6]).add(localBigDecimal5));
        }
        ((VariLiftedGoods)localObject10).setTradeAmount(localBigDecimal1);
        ((VariLiftedGoods)localObject10).setTradeMoney(localBigDecimal2);
        ((VariLiftedGoods)localObject10).setVari(str1);
        localArrayList.add(localObject10);
        if (m == i - 1)
        {
          localObject11 = new ProLiftedGoods();
          ((ProLiftedGoods)localObject11).setProId(Integer.parseInt((String)localObject2));
          ((ProLiftedGoods)localObject11).setProName((String)localObject3);
          ((ProLiftedGoods)localObject11).setVariLiftedGoods(localArrayList);
          ((ProLiftedGoods)localObject11).setZJTradeAmount((BigDecimal)localObject4);
          ((ProLiftedGoods)localObject11).setZJLiftedAmount((BigDecimal)localObject5);
          ((ProLiftedGoods)localObject11).setZJLKMoney((BigDecimal)localObject6);
          ((ProLiftedGoods)localObject11).setZJTradeMoney((BigDecimal)localObject7);
          ((ProLiftedGoods)localObject11).setZJLiftedMoney((BigDecimal)localObject8);
          ((ProLiftedGoods)localObject11).setZJLKAmount((BigDecimal)localObject9);
          arrayOfArrayList1[0].add(localObject11);
        }
      }
      localResultSet1.close();
      localDBBean.close();
      if (arrayOfArrayList1[0].size() > 0)
      {
        localObject10 = new ZJProLifted();
        ((ZJProLifted)localObject10).setZJTradeAmount(localBigDecimal7);
        ((ZJProLifted)localObject10).setZJLiftedAmount(localBigDecimal8);
        ((ZJProLifted)localObject10).setZJLKMoney(localBigDecimal9);
        ((ZJProLifted)localObject10).setZJTradeMoney(localBigDecimal10);
        ((ZJProLifted)localObject10).setZJLiftedMoney(localBigDecimal11);
        ((ZJProLifted)localObject10).setZJLKAmount(localBigDecimal12);
        arrayOfArrayList1[1].add(localObject10);
      }
      for (int n = 0; n < arrayOfString.length; n++)
      {
        localObject11 = new VariLiftedGoods();
        ((VariLiftedGoods)localObject11).setVari(arrayOfString[n][0]);
        ((VariLiftedGoods)localObject11).setTradeAmount(new BigDecimal(arrayOfString[n][1]));
        ((VariLiftedGoods)localObject11).setTradeMoney(new BigDecimal(arrayOfString[n][2]));
        ((VariLiftedGoods)localObject11).setLiftedAmount(new BigDecimal(arrayOfString[n][3]));
        ((VariLiftedGoods)localObject11).setLiftedMoney(new BigDecimal(arrayOfString[n][4]));
        ((VariLiftedGoods)localObject11).setLKAmount(new BigDecimal(arrayOfString[n][5]));
        ((VariLiftedGoods)localObject11).setLKMoney(new BigDecimal(arrayOfString[n][6]));
        arrayOfArrayList1[2].add(localObject11);
      }
      ArrayList[] arrayOfArrayList2 = arrayOfArrayList1;
      return arrayOfArrayList2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList[] getTraderDebt(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, int paramInt2, int paramInt3)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    ArrayList[] arrayOfArrayList = new ArrayList[2];
    arrayOfArrayList[0] = new ArrayList();
    arrayOfArrayList[1] = new ArrayList();
    ArrayList localArrayList = new ArrayList();
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      str = null;
      double d = 0.0D;
      int i = 0;
      int j = 0;
      int k = 0;
      localStringBuffer.append("select partitionid from v_syspartition where rownum=1 and validflag=1");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet.next()) {
        i = localResultSet.getInt("partitionid");
      }
      localResultSet.close();
      localDBBean.closeStmt();
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select count(usercode) n from v_tradeuser where tradecount>0 " + paramString2 + " ");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet.next()) {
        j = localResultSet.getInt("n");
      }
      localResultSet.close();
      localDBBean.closeStmt();
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select usercode from (select usercode,rownum rown from (select usercode from");
      localStringBuffer.append(" v_tradeuser where tradecount>0 " + paramString2 + " order by usercode)) where rown between");
      localStringBuffer.append(" (" + paramInt2 + "-1)*" + paramInt3 + "+1 and " + paramInt2 + "*" + paramInt3 + "");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        str = localResultSet.getString(1);
        TraderDebt localTraderDebt = new TraderDebt();
        localTraderDebt.setTrader(localResultSet.getString(1));
        localTraderDebt.setDebt(0.0D);
        localArrayList.add(localTraderDebt);
      }
      localResultSet.close();
      localDBBean.close();
      for (int m = 0; m < localArrayList.size(); m++)
      {
        d = 0.0D;
        localObject1 = (TraderDebt)localArrayList.get(m);
        ((TraderDebt)localObject1).setDebt(d);
        arrayOfArrayList[0].add(localObject1);
      }
      DivPage localDivPage = new DivPage();
      if (paramInt3 > 0) {
        if (j % paramInt3 != 0) {
          k = j / paramInt3 + 1;
        } else {
          k = j / paramInt3;
        }
      }
      localDivPage.setPageCount(k);
      localDivPage.setPageIndex(paramInt2);
      localDivPage.setTotalCount(j);
      localDivPage.setPageSize(paramInt3);
      arrayOfArrayList[1].add(localDivPage);
      Object localObject1 = arrayOfArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      String str = null;
      return str;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList queryTraderDebt(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    BigDecimal localBigDecimal = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      str = null;
      double d = 0.0D;
      int i = 0;
      int j = 0;
      int k = 0;
      localStringBuffer.append("select partitionid from v_syspartition where rownum=1 and validflag=1");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet.next()) {
        i = localResultSet.getInt("partitionid");
      }
      localResultSet.close();
      localDBBean.closeStmt();
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select t1.name,t2.usercode,t1.mgtele from v_tradeuserext t1,v_tradeuser t2,v_operpartuser t3 where");
      localStringBuffer.append(" t1.usercode=t2.usercode and t1.id=t3.userid and t3.newstatus=1 and t2.tradecount>0");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        str = localResultSet.getString(2);
        TraderDebt localTraderDebt1 = new TraderDebt();
        localTraderDebt1.setTrader(str);
        localTraderDebt1.setDebt(0.0D);
        localTraderDebt1.setName(localResultSet.getString(1));
        localTraderDebt1.setMGTel(localResultSet.getString(3));
        localArrayList2.add(localTraderDebt1);
      }
      localResultSet.close();
      localDBBean.closeStmt();
      for (int m = 0; m < localArrayList2.size(); m++)
      {
        d = 0.0D;
        localBigDecimal = new BigDecimal(0);
        TraderDebt localTraderDebt2 = (TraderDebt)localArrayList2.get(m);
        if (d > 0.0D)
        {
          localTraderDebt2.setDebt(d);
          localStringBuffer = new StringBuffer();
          localStringBuffer.append("select sum(t1.amount) v1,sum(t1.amount*t1.price) from v_hisbargain t1,v_commodity t2,v_commext t3 where t1.tradedate>=");
          localStringBuffer.append("to_date('" + paramString3 + " 00:00:00','yyyy-mm-dd hh24:mi:ss') and t1.tradedate<=");
          localStringBuffer.append("to_date('" + paramString4 + " 23:59:59','yyyy-mm-dd  hh24:mi:ss') and t1.userid='" + localTraderDebt2.getTrader() + "'");
          localStringBuffer.append(" and t1.commodityid=t2.id and t2.id=t3.commid and t3.str7=" + paramInt + "");
          localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
          if (localResultSet.next())
          {
            localTraderDebt2.setTradeAmount(ManaUtil.disBD(localResultSet.getBigDecimal(1)));
            localTraderDebt2.setTradeMoney(ManaUtil.disBD(localResultSet.getBigDecimal(2)));
          }
          localResultSet.close();
          localDBBean.closeStmt();
          localStringBuffer = new StringBuffer();
          localStringBuffer.append("select sum(t2.amount) v1 from v_hisbargain t1,v_outlog t2,v_commodity t3,v_commext t4 where t1.tradedate>=");
          localStringBuffer.append("to_date('" + paramString3 + " 00:00:00','yyyy-mm-dd hh24:mi:ss') and t1.tradedate<=");
          localStringBuffer.append("to_date('" + paramString4 + " 23:59:59','yyyy-mm-dd  hh24:mi:ss') and userid=");
          localStringBuffer.append("'" + localTraderDebt2.getTrader() + "' and t1.contractid=t2.contractid and t2.finished=2");
          localStringBuffer.append(" and t1.commodityid=t3.id and t3.id=t4.commid and t4.str7=" + paramInt + "");
          localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
          if (localResultSet.next()) {
            localTraderDebt2.setOutAmount(ManaUtil.disBD(localResultSet.getBigDecimal(1)));
          }
          localResultSet.close();
          localDBBean.closeStmt();
          localStringBuffer = new StringBuffer();
          localStringBuffer.append("select sum(money) v1 from v_hismoney where operation=506 and contractno>0 and infodate>=");
          localStringBuffer.append("to_date('" + paramString3 + " 00:00:00','yyyy-mm-dd hh24:mi:ss') and infodate<=");
          localStringBuffer.append("to_date('" + paramString4 + " 23:59:59','yyyy-mm-dd  hh24:mi:ss') and firmid=");
          localStringBuffer.append("'" + localTraderDebt2.getTrader() + "' and market2=" + paramInt + "");
          localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
          if (localResultSet.next()) {
            localBigDecimal = ManaUtil.disBD(localResultSet.getBigDecimal(1));
          }
          localResultSet.close();
          localDBBean.closeStmt();
          localTraderDebt2.setArrivedPayment(localBigDecimal);
          localArrayList1.add(localTraderDebt2);
        }
      }
      ArrayList localArrayList3 = localArrayList1;
      return localArrayList3;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      String str = null;
      return str;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList queryTraderDebtThr(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, int paramInt2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    Object localObject1 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      str = null;
      double d = 0.0D;
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select t1.name,t2.usercode,t1.mgtele from v_tradeuserext t1,v_tradeuser t2,v_operpartuser t3 where");
      localStringBuffer.append(" t1.usercode=t2.usercode and t1.id=t3.userid and t3.newstatus=1 and t2.tradecount>0");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        str = localResultSet.getString(2);
        localObject2 = new TraderDebt();
        ((TraderDebt)localObject2).setTrader(str);
        ((TraderDebt)localObject2).setDebt(0.0D);
        ((TraderDebt)localObject2).setName(localResultSet.getString(1));
        ((TraderDebt)localObject2).setMGTel(localResultSet.getString(3));
        localArrayList2.add(localObject2);
      }
      localResultSet.close();
      localDBBean.close();
      int i = localArrayList2.size() / paramInt2;
      int j = localArrayList2.size() % paramInt2;
      if (j > 0) {
        i++;
      }
      Object localObject2 = new ThreadQuery[i];
      for (int k = 0; k < i; k++)
      {
        localObject2[k] = new ThreadQuery();
        localObject2[k].setArrayAgr(localArrayList2);
        localObject2[k].setJndi(paramString1);
        localObject2[k].setFilter(paramString2);
        localObject2[k].setMarketId(paramInt1);
        localObject2[k].setStartDate(paramString3);
        localObject2[k].setEndDate(paramString4);
        localObject2[k].setResult(localArrayList1);
        localObject2[k].setStartCuror(k * paramInt2);
        if (k == i - 1) {
          localObject2[k].setEndCuror(localArrayList2.size());
        } else {
          localObject2[k].setEndCuror((k + 1) * paramInt2);
        }
        localObject2[k].start();
      }
      k = 0;
      while (k == 0)
      {
        int m = 0;
        for (int n = 0; (n < i) && (localObject2[n].getGameOver().booleanValue()); n++) {
          m++;
        }
        if (m == i) {
          k = 1;
        }
        Thread.sleep(50L);
      }
      System.out.println("----->result" + localArrayList1.size() + "circle" + i + "remainder" + j);
      System.out.println("------>交易商总数:" + localArrayList2.size());
      ArrayList localArrayList3 = localArrayList1;
      return localArrayList3;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      String str = null;
      return str;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getTraderDebt(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select usercode,name,tradeamount,trademoney,arrivedpayment,outamount,");
      localStringBuffer.append("debtmoney,mgtel from v_traderdebt where 1=1 " + paramString2 + "");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = new TraderDebt();
        ((TraderDebt)localObject1).setName(localResultSet.getString("name"));
        ((TraderDebt)localObject1).setTrader(localResultSet.getString("usercode"));
        ((TraderDebt)localObject1).setTradeAmount(localResultSet.getBigDecimal("tradeamount"));
        ((TraderDebt)localObject1).setTradeMoney(localResultSet.getBigDecimal("trademoney"));
        ((TraderDebt)localObject1).setArrivedPayment(localResultSet.getBigDecimal("arrivedpayment"));
        ((TraderDebt)localObject1).setOutAmount(localResultSet.getBigDecimal("outamount"));
        ((TraderDebt)localObject1).setDebt(localResultSet.getDouble("debtmoney"));
        ((TraderDebt)localObject1).setMGTel(localResultSet.getString("mgtel"));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Object localObject1 = null;
      return localObject1;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getPaymentDetail(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ArrayList localArrayList = new ArrayList();
    ResultSet localResultSet2 = null;
    BigDecimal localBigDecimal1 = null;
    BigDecimal localBigDecimal2 = null;
    BigDecimal localBigDecimal3 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select t1.contractid v1,t3.str2 v2,(select name from v_tradeuserext where usercode=");
      localStringBuffer.append("t1.userid) v3,t3.str1 v4,t1.price v5,t1.amount t6,t1.price*t1.amount v7,");
      localStringBuffer.append("t1.amount-(t1.actualamount+t1.fellbackamount) v8,(t1.amount-(t1.actualamount+t1.fellbackamount))");
      localStringBuffer.append("*t1.price v9,t1.actualamount*case when t1.price>t3.num1 then t3.num1 else t1.price end v10,");
      localStringBuffer.append("t1.poundage v11,case when t1.price>t3.num1 or t3.num1 is null then (t1.price-t3.num1)*");
      localStringBuffer.append("t1.actualamount else 0 end v12,t3.num1 v13,t3.str4 v14,t1.userid v15,t1.code v16,");
      localStringBuffer.append("(select max(t4.infodate) from v_hismoney t4 where t1.contractid=t4.contractno and operation=512) v17 from v_hisbargain");
      localStringBuffer.append(" t1,commodity t2,commext t3 where t1.commodityid=t2.id and t2.id=t3.commid and t1.status>0 " + paramString2 + "");
      localStringBuffer.append(" order by t1.contractid asc");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet1.next())
      {
        localBigDecimal1 = new BigDecimal(0);
        localBigDecimal2 = new BigDecimal(0);
        localBigDecimal3 = new BigDecimal(0);
        localObject1 = new PaymentDetail();
        ((PaymentDetail)localObject1).setContractId(localResultSet1.getString(1));
        ((PaymentDetail)localObject1).setKuDian(localResultSet1.getString(2));
        ((PaymentDetail)localObject1).setBuyerName(localResultSet1.getString(3));
        ((PaymentDetail)localObject1).setVari(localResultSet1.getString(4));
        ((PaymentDetail)localObject1).setPrice(ManaUtil.disBD(localResultSet1.getBigDecimal(5)));
        ((PaymentDetail)localObject1).setAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(6)));
        ((PaymentDetail)localObject1).setTradeMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(7)));
        ((PaymentDetail)localObject1).setExauAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(8)));
        ((PaymentDetail)localObject1).setExauMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(9)).setScale(2, 4));
        ((PaymentDetail)localObject1).setPoundage(ManaUtil.disBD(localResultSet1.getBigDecimal(11)));
        ((PaymentDetail)localObject1).setCode(localResultSet1.getString(16));
        if ((localResultSet1.getString(17) != null) && (!"null".equals(localResultSet1.getString(17))) && (!"".equals(localResultSet1.getString(17)))) {
          ((PaymentDetail)localObject1).setPaymentDate(localResultSet1.getString(17).substring(0, 10));
        } else {
          ((PaymentDetail)localObject1).setPaymentDate("");
        }
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select sum(money) v1 from hismoney where operation=510 and contractno=");
        localStringBuffer.append("" + ((PaymentDetail)localObject1).getContractId() + " and firmid='LK'");
        localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
        if (localResultSet2.next()) {
          localBigDecimal1 = ManaUtil.disBD(localResultSet2.getBigDecimal(1));
        }
        localResultSet2.close();
        localDBBean.closeStmt();
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select sum(money) v1 from v_hismoney where operation=511 and contractno=");
        localStringBuffer.append("" + ((PaymentDetail)localObject1).getContractId() + " and firmid='" + localResultSet1.getString(15) + "'");
        localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
        if (localResultSet2.next()) {
          localBigDecimal2 = ManaUtil.disBD(localResultSet2.getBigDecimal(1));
        }
        localResultSet2.close();
        localDBBean.closeStmt();
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select sum(money) v1 from v_hismoney where operation=511 and contractno=");
        localStringBuffer.append("" + ((PaymentDetail)localObject1).getContractId() + " and firmid='CZ'");
        localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
        if (localResultSet2.next()) {
          localBigDecimal3 = ManaUtil.disBD(localResultSet2.getBigDecimal(1));
        }
        localResultSet2.close();
        localDBBean.closeStmt();
        if (ManaUtil.disBD(localResultSet1.getBigDecimal(12)).compareTo(new BigDecimal(0)) <= 0) {
          ((PaymentDetail)localObject1).setPayKuDianMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(10)).subtract(((PaymentDetail)localObject1).getPoundage()));
        } else {
          ((PaymentDetail)localObject1).setPayKuDianMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(10)));
        }
        ((PaymentDetail)localObject1).setPayKuDianMoney(((PaymentDetail)localObject1).getPayKuDianMoney().subtract(localBigDecimal1.add(localBigDecimal2)).setScale(2, 4));
        if (ManaUtil.disBD(localResultSet1.getBigDecimal(12)).compareTo(new BigDecimal(0)) > 0) {
          ((PaymentDetail)localObject1).setPayupMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(12)).subtract(((PaymentDetail)localObject1).getPoundage()));
        } else {
          ((PaymentDetail)localObject1).setPayupMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(12)));
        }
        ((PaymentDetail)localObject1).setPayupMoney(((PaymentDetail)localObject1).getPayupMoney().add(localBigDecimal3).setScale(2, 4));
        ((PaymentDetail)localObject1).setProcurePrice(ManaUtil.disBD(localResultSet1.getBigDecimal(13)));
        ((PaymentDetail)localObject1).setGrade(localResultSet1.getString(14));
        localArrayList.add(localObject1);
      }
      localResultSet1.close();
      localDBBean.close();
      localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Object localObject1 = null;
      return localObject1;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getProTrade(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList1 = new ArrayList();
    try
    {
      localDBBean = new DBBean(paramString1);
      HJProTrade localHJProTrade = new HJProTrade();
      localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      StringBuffer localStringBuffer = new StringBuffer();
      ArrayList localArrayList2 = new ArrayList();
      localStringBuffer.append("select sum(t1.amount) v1,sum(t1.amount*t1.price) v2,(select name from v_dictable where ");
      localStringBuffer.append("type=2 and value=t3.str6) v3,t3.str6 v4 from v_hisbargain t1,v_commodity t2,v_commext t3 ");
      localStringBuffer.append(" where t1.commodityid=t2.id and t2.id=t3.commid " + paramString2 + " group by t3.str6");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = new TradePro();
        ((TradePro)localObject1).setTradeAmount(localResultSet.getBigDecimal(1));
        ((TradePro)localObject1).setTradeMoney(localResultSet.getBigDecimal(2));
        ((TradePro)localObject1).setProName(localResultSet.getString(3));
        ((TradePro)localObject1).setProId(localResultSet.getInt(4));
        localBigDecimal1 = localBigDecimal1.add(((TradePro)localObject1).getTradeAmount());
        localBigDecimal2 = localBigDecimal2.add(((TradePro)localObject1).getTradeMoney());
        localArrayList2.add(localObject1);
      }
      if (localArrayList2.size() > 0)
      {
        localHJProTrade.setTradePro(localArrayList2);
        localHJProTrade.setHJTradeAmount(localBigDecimal1);
        localHJProTrade.setHJTradeMoney(localBigDecimal2);
        localArrayList1.add(localHJProTrade);
      }
      localResultSet.close();
      localDBBean.close();
      Object localObject1 = localArrayList1;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      BigDecimal localBigDecimal1 = null;
      return localBigDecimal1;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getDisDate(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select distinct trunc(tradedate) from v_hisbargain t1,v_commodity t2,v_commext t3 where ");
      localStringBuffer.append("t1.commodityid=t2.id and t2.id=t3.commid " + paramString2 + "");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = new DistinctDate();
        ((DistinctDate)localObject1).setDisDate(localResultSet.getString(1));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Object localObject1 = null;
      return localObject1;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getEachProAmount(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select sum(t1.amount) v1,sum(t1.amount*t1.price) v2 from v_hisbargain t1,v_commodity t2,");
      localStringBuffer.append("v_commext t3 where t1.commodityid=t2.id and t2.id=t3.commid " + paramString2 + "");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet.next())
      {
        localObject1 = new TradePro();
        ((TradePro)localObject1).setTradeAmount(ManaUtil.disBD(localResultSet.getBigDecimal(1)));
        ((TradePro)localObject1).setTradeMoney(ManaUtil.disBD(localResultSet.getBigDecimal(2)));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Object localObject1 = null;
      return localObject1;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getProVariTrade(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select sum(t1.amount*t1.price)/sum(t1.amount) v1,t3.str1 v2 from v_hisbargain t1,");
      localStringBuffer.append("v_commodity t2,v_commext t3 where t1.commodityid=t2.id and t2.id=t3.commid " + paramString2 + "");
      localStringBuffer.append(" group by t3.str1 ");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = new VariProTrade();
        ((VariProTrade)localObject1).setAvgPrice(ManaUtil.disBD(localResultSet.getBigDecimal(1)));
        ((VariProTrade)localObject1).setVari(localResultSet.getString(2));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Object localObject1 = null;
      return localObject1;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getEachVariAmount(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select sum(t1.amount*t1.price)/sum(t1.amount) v1 from v_hisbargain t1,v_commodity t2,");
      localStringBuffer.append("v_commext t3 where t1.commodityid=t2.id and t2.id=t3.commid " + paramString2 + "");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet.next())
      {
        localObject1 = new VariProTrade();
        ((VariProTrade)localObject1).setAvgPrice(ManaUtil.disBD(localResultSet.getBigDecimal(1)));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Object localObject1 = null;
      return localObject1;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getAllPro(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    Object localObject1 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList localArrayList1 = new ArrayList();
      localStringBuffer = new StringBuffer();
      int i = 0;
      localStringBuffer.append("select count(n) n from (select t3.str6 n  from v_hisbargain t1,v_commodity t2,v_commext t3 where");
      localStringBuffer.append(" t1.commodityid=t2.id and t2.id=t3.commid  " + paramString2 + " group by t2.category,t3.str6)");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet.next()) {
        i = localResultSet.getInt("n");
      }
      localResultSet.close();
      localDBBean.closeStmt();
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select sum(t1.amount) v1,sum(t1.amount*t1.price) v2,(select name from v_dictable");
      localStringBuffer.append(" where type=2 and value=t3.str6) v3,(select name from v_dictable where type=1 and value=");
      localStringBuffer.append("t2.category) v4,t3.str6 v5,t2.category v6  from v_hisbargain t1,v_commodity t2,v_commext");
      localStringBuffer.append(" t3 where t1.commodityid=t2.id and t2.id=t3.commid " + paramString2 + " group by");
      localStringBuffer.append(" t2.category,t3.str6 order by t2.category");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      Object localObject2 = "";
      Object localObject3 = "";
      String str1 = "";
      Object localObject4 = "";
      String str2 = "";
      String str3 = "";
      String str4 = null;
      Object localObject5 = new BigDecimal(0);
      Object localObject6 = new BigDecimal(0);
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      ArrayList localArrayList2 = new ArrayList();
      for (int j = 0; localResultSet.next(); j++)
      {
        localObject7 = new EachProTrade();
        localBigDecimal1 = new BigDecimal(0);
        localBigDecimal2 = new BigDecimal(0);
        str4 = "";
        localBigDecimal1 = ManaUtil.disBD(localResultSet.getBigDecimal(1));
        localBigDecimal2 = ManaUtil.disBD(localResultSet.getBigDecimal(2));
        str4 = localResultSet.getString(3);
        str2 = localResultSet.getString(6);
        str3 = localResultSet.getString(4);
        localObject3 = str2;
        if (j == 0)
        {
          localObject2 = str2;
          localObject4 = str3;
          localObject5 = localBigDecimal1;
          localObject6 = localBigDecimal2;
        }
        AllTrade localAllTrade;
        if (!((String)localObject3).equals(localObject2))
        {
          localAllTrade = new AllTrade();
          localAllTrade.setAllTradeAmount((BigDecimal)localObject5);
          localAllTrade.setAllTradeMoney((BigDecimal)localObject6);
          localAllTrade.setCategoryName((String)localObject4);
          localAllTrade.setCategoryId(Integer.parseInt((String)localObject2));
          localAllTrade.setEachProTrade(localArrayList2);
          localArrayList1.add(localAllTrade);
          localObject2 = localObject3;
          localObject4 = str3;
          localArrayList2 = new ArrayList();
          localObject5 = localBigDecimal1;
          localObject6 = localBigDecimal2;
        }
        else if (j > 0)
        {
          localObject5 = ((BigDecimal)localObject5).add(localBigDecimal1);
          localObject6 = ((BigDecimal)localObject6).add(localBigDecimal2);
        }
        ((EachProTrade)localObject7).setTradeAmount(localBigDecimal1);
        ((EachProTrade)localObject7).setTradeMoney(localBigDecimal2);
        ((EachProTrade)localObject7).setProName(str4);
        ((EachProTrade)localObject7).setProId(localResultSet.getInt(5));
        localArrayList2.add(localObject7);
        if (j == i - 1)
        {
          localAllTrade = new AllTrade();
          localAllTrade.setAllTradeAmount((BigDecimal)localObject5);
          localAllTrade.setAllTradeMoney((BigDecimal)localObject6);
          localAllTrade.setCategoryName((String)localObject4);
          localAllTrade.setCategoryId(Integer.parseInt((String)localObject2));
          localAllTrade.setEachProTrade(localArrayList2);
          localArrayList1.add(localAllTrade);
        }
      }
      localResultSet.close();
      localDBBean.close();
      Object localObject7 = localArrayList1;
      return localObject7;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localObject1 != null) {
          localObject1.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getEachPro(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select sum(t1.amount) v1,sum(t1.amount*t1.price) v2,(select name from v_dictable");
      localStringBuffer.append(" where type=2 and value=t3.str6) v3,t3.str6 v4 from v_hisbargain t1,v_commodity t2,v_commext t3");
      localStringBuffer.append(" where t1.commodityid=t2.id and t2.id=t3.commid " + paramString2 + "");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = new EachProTrade();
        ((EachProTrade)localObject1).setProName(localResultSet.getString(3));
        ((EachProTrade)localObject1).setTradeAmount(ManaUtil.disBD(localResultSet.getBigDecimal(1)));
        ((EachProTrade)localObject1).setTradeMoney(ManaUtil.disBD(localResultSet.getBigDecimal(2)));
        ((EachProTrade)localObject1).setProId(localResultSet.getInt(4));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Object localObject1 = null;
      return localObject1;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getSinglePro(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select sum(t1.amount) v1,sum(t1.amount*t1.price) v2 from v_hisbargain t1,v_commodity t2");
      localStringBuffer.append(",v_commext t3 where t1.commodityid=t2.id and t2.id=t3.commid " + paramString2 + "");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = new EachProTrade();
        ((EachProTrade)localObject1).setTradeAmount(ManaUtil.disBD(localResultSet.getBigDecimal(1)));
        ((EachProTrade)localObject1).setTradeMoney(ManaUtil.disBD(localResultSet.getBigDecimal(2)));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Object localObject1 = null;
      return localObject1;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList[] getSpecialPro(String paramString1, String paramString2, HashMap paramHashMap, int paramInt1, String paramString3, int paramInt2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    ArrayList[] arrayOfArrayList = new ArrayList[2];
    arrayOfArrayList[0] = new ArrayList();
    arrayOfArrayList[1] = new ArrayList();
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList localArrayList = new ArrayList();
      str1 = "";
      String str2 = "";
      StringBuffer localStringBuffer = new StringBuffer();
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      BigDecimal localBigDecimal3 = new BigDecimal(0);
      BigDecimal localBigDecimal4 = new BigDecimal(0);
      localStringBuffer.append("select sum(t1.amount) v1,sum(t1.amount*t1.price) v2,(select name from v_dictable");
      localStringBuffer.append(" where type=2 and value=t3.str6) v3,t3.str6 v4 from v_hisbargain t1,v_commodity t2,v_commext t3");
      localStringBuffer.append(" where t1.commodityid=t2.id and t2.id=t3.commid " + paramString2 + "");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString() + " and t3.str6=" + paramInt1 + " group by t3.str6");
      EachProTrade localEachProTrade1 = new EachProTrade();
      if (localResultSet1.next())
      {
        localEachProTrade1.setProName(localResultSet1.getString(3));
        localEachProTrade1.setTradeAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(1)));
        localEachProTrade1.setTradeMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(2)));
        localEachProTrade1.setProId(localResultSet1.getInt(4));
        localBigDecimal1 = localEachProTrade1.getTradeAmount();
        localBigDecimal2 = localEachProTrade1.getTradeMoney();
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      if (!ManaUtil.checkStr(localEachProTrade1.getProName()))
      {
        localResultSet1 = localDBBean.executeQuery("select name v1 from v_dictable where type=2 and value=" + paramInt1 + "");
        if (localResultSet1.next())
        {
          localEachProTrade1.setProName(localResultSet1.getString(1));
          localEachProTrade1.setProId(paramInt1);
          localEachProTrade1.setTradeAmount(new BigDecimal(0));
          localEachProTrade1.setTradeMoney(new BigDecimal(0));
        }
        localResultSet1.close();
        localDBBean.closeStmt();
      }
      String[] arrayOfString = paramString3.split(",");
      for (int i = 0; i < arrayOfString.length; i++)
      {
        str2 = "";
        localObject1 = new DivProTrade();
        EachProTrade localEachProTrade3 = new EachProTrade();
        if (ManaUtil.checkStr(arrayOfString[i]))
        {
          str2 = paramHashMap.get(new Integer(arrayOfString[i])).toString();
          str1 = " and t3.str6=" + arrayOfString[i] + " and t1.tradedate<to_date('" + str2 + " 00:00:00'";
          str1 = str1 + ",'yyyy-mm-dd hh24:mi:ss') group by t3.str6";
          localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString() + str1);
          if (localResultSet2.next())
          {
            ((DivProTrade)localObject1).setProId(localResultSet2.getInt(4));
            ((DivProTrade)localObject1).setProName(localResultSet2.getString(3));
            localBigDecimal3 = ManaUtil.disBD(localResultSet2.getBigDecimal(1));
            localBigDecimal4 = ManaUtil.disBD(localResultSet2.getBigDecimal(2));
            ((DivProTrade)localObject1).setTradeAmountAgo(localBigDecimal3);
            ((DivProTrade)localObject1).setTradeMoneyAgo(localBigDecimal4);
            localEachProTrade3.setProId(localResultSet2.getInt(4));
            localEachProTrade3.setProName(localResultSet2.getString(3));
            localBigDecimal1 = localBigDecimal1.add(localBigDecimal3);
            localBigDecimal2 = localBigDecimal2.add(localBigDecimal4);
          }
          localResultSet2.close();
          localDBBean.closeStmt();
          if (!ManaUtil.checkStr(((DivProTrade)localObject1).getProName()))
          {
            localResultSet1 = localDBBean.executeQuery("select name v1 from dictable where type=2 and value=" + arrayOfString[i] + "");
            if (localResultSet1.next())
            {
              ((DivProTrade)localObject1).setProName(localResultSet1.getString(1));
              ((DivProTrade)localObject1).setProId(Integer.parseInt(arrayOfString[i]));
              ((DivProTrade)localObject1).setTradeAmountAgo(new BigDecimal(0));
              ((DivProTrade)localObject1).setTradeMoneyAgo(new BigDecimal(0));
              localEachProTrade3.setProId(Integer.parseInt(arrayOfString[i]));
              localEachProTrade3.setProName(((DivProTrade)localObject1).getProName());
            }
            localResultSet1.close();
            localDBBean.closeStmt();
          }
          str1 = " and t3.str6=" + arrayOfString[i] + " and t1.tradedate>=to_date('" + str2 + " 00:00:00'";
          str1 = str1 + ",'yyyy-mm-dd hh24:mi:ss') group by t3.str6";
          localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString() + str1);
          if (localResultSet2.next())
          {
            ((DivProTrade)localObject1).setTradeAmountLater(ManaUtil.disBD(localResultSet2.getBigDecimal(1)));
            ((DivProTrade)localObject1).setTradeMoneyLater(ManaUtil.disBD(localResultSet2.getBigDecimal(2)));
            localEachProTrade3.setTradeAmount(ManaUtil.disBD(localResultSet2.getBigDecimal(1)));
            localEachProTrade3.setTradeMoney(ManaUtil.disBD(localResultSet2.getBigDecimal(2)));
          }
          else
          {
            ((DivProTrade)localObject1).setTradeAmountLater(new BigDecimal(0));
            ((DivProTrade)localObject1).setTradeMoneyLater(new BigDecimal(0));
            localEachProTrade3.setTradeAmount(new BigDecimal(0));
            localEachProTrade3.setTradeMoney(new BigDecimal(0));
          }
          localResultSet2.close();
          localDBBean.closeStmt();
          localArrayList.add(localObject1);
          arrayOfArrayList[1].add(localEachProTrade3);
        }
      }
      localEachProTrade1.setDivProTrade(localArrayList);
      arrayOfArrayList[0].add(localEachProTrade1);
      EachProTrade localEachProTrade2 = new EachProTrade();
      if ((localEachProTrade1.getProName() != null) && ((localEachProTrade1.getProId() != 13) || (paramInt2 != 1)))
      {
        localEachProTrade2.setProId(localEachProTrade1.getProId());
        localEachProTrade2.setProName(localEachProTrade1.getProName());
        localEachProTrade2.setTradeAmount(localBigDecimal1);
        localEachProTrade2.setTradeMoney(localBigDecimal2);
        arrayOfArrayList[1].add(localEachProTrade2);
      }
      localDBBean.close();
      Object localObject1 = arrayOfArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      String str1 = null;
      return str1;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getAllSpecialPro(String paramString1, String paramString2, HashMap paramHashMap, int paramInt1, String[] paramArrayOfString1, int paramInt2, String[] paramArrayOfString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    try
    {
      localDBBean = new DBBean(paramString1);
      String str1 = "";
      str2 = "";
      StringBuffer localStringBuffer = new StringBuffer();
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = new HashMap();
      HashMap localHashMap3 = new HashMap();
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      BigDecimal localBigDecimal3 = new BigDecimal(0);
      BigDecimal localBigDecimal4 = new BigDecimal(0);
      EachProTrade localEachProTrade1 = new EachProTrade();
      EachProTrade localEachProTrade2 = new EachProTrade();
      BigDecimal localBigDecimal5 = new BigDecimal(0);
      BigDecimal localBigDecimal6 = new BigDecimal(0);
      localStringBuffer.append("select sum(t1.amount) v1,sum(t1.amount*t1.price) v2,(select name from v_dictable");
      localStringBuffer.append(" where type=2 and value=t3.str6) v3,t3.str6 v4 from v_hisbargain t1,v_commodity t2,v_commext t3");
      localStringBuffer.append(" where t1.commodityid=t2.id and t2.id=t3.commid " + paramString2 + "");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString() + " and t3.str6=" + paramInt1 + " group by t3.str6");
      if (localResultSet1.next())
      {
        localEachProTrade1.setProName(localResultSet1.getString(3));
        localEachProTrade1.setTradeAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(1)));
        localEachProTrade1.setTradeMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(2)));
        localEachProTrade1.setProId(localResultSet1.getInt(4));
        localBigDecimal1 = localEachProTrade1.getTradeAmount();
        localBigDecimal2 = localEachProTrade1.getTradeMoney();
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      if (!ManaUtil.checkStr(localEachProTrade1.getProName()))
      {
        localResultSet1 = localDBBean.executeQuery("select name v1 from v_dictable where type=2 and value=" + paramInt1 + "");
        if (localResultSet1.next())
        {
          localEachProTrade1.setProName(localResultSet1.getString(1));
          localEachProTrade1.setProId(paramInt1);
          localEachProTrade1.setTradeAmount(new BigDecimal(0));
          localEachProTrade1.setTradeMoney(new BigDecimal(0));
        }
        localResultSet1.close();
        localDBBean.closeStmt();
      }
      for (int i = 0; i < paramArrayOfString1.length; i++)
      {
        localObject1 = paramArrayOfString1[i].split(",");
        for (j = 0; j < localObject1.length; j++)
        {
          localBigDecimal5 = new BigDecimal(0);
          localBigDecimal6 = new BigDecimal(0);
          str2 = "";
          localObject2 = new EachProTrade();
          if (ManaUtil.checkStr(localObject1[j]))
          {
            str2 = paramHashMap.get(new Integer(localObject1[j])).toString();
            str1 = " and t3.str6=" + localObject1[j] + " and t1.tradedate<to_date('" + str2 + " 00:00:00'";
            str1 = str1 + ",'yyyy-mm-dd hh24:mi:ss') and t2.category=" + (i + 1) + " group by t3.str6";
            localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString() + str1);
            if (localResultSet2.next())
            {
              ((EachProTrade)localObject2).setProId(localResultSet2.getInt(4));
              ((EachProTrade)localObject2).setProName(localResultSet2.getString(3));
              localBigDecimal5 = ManaUtil.disBD(localResultSet2.getBigDecimal(1));
              localBigDecimal6 = ManaUtil.disBD(localResultSet2.getBigDecimal(2));
              localBigDecimal1 = localBigDecimal1.add(localBigDecimal5);
              localBigDecimal2 = localBigDecimal2.add(localBigDecimal6);
            }
            localResultSet2.close();
            localDBBean.closeStmt();
            if (!ManaUtil.checkStr(((EachProTrade)localObject2).getProName()))
            {
              localResultSet1 = localDBBean.executeQuery("select name v1 from v_dictable where type=2 and value=" + localObject1[j] + "");
              if (localResultSet1.next())
              {
                ((EachProTrade)localObject2).setProName(localResultSet1.getString(1));
                ((EachProTrade)localObject2).setProId(Integer.parseInt(localObject1[j]));
              }
              localResultSet1.close();
              localDBBean.closeStmt();
            }
            str1 = " and t3.str6=" + localObject1[j] + " and t1.tradedate>=to_date('" + str2 + " 00:00:00'";
            str1 = str1 + ",'yyyy-mm-dd hh24:mi:ss') and t2.category=" + (i + 1) + " group by t3.str6";
            localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString() + str1);
            if (localResultSet2.next())
            {
              ((EachProTrade)localObject2).setTradeAmount(ManaUtil.disBD(localResultSet2.getBigDecimal(1)));
              ((EachProTrade)localObject2).setTradeMoney(ManaUtil.disBD(localResultSet2.getBigDecimal(2)));
            }
            else
            {
              ((EachProTrade)localObject2).setTradeAmount(new BigDecimal(0));
              ((EachProTrade)localObject2).setTradeMoney(new BigDecimal(0));
            }
            localResultSet2.close();
            localDBBean.closeStmt();
            localArrayList2.add(localObject2);
          }
        }
      }
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString() + " and t3.str6=" + paramInt2 + " group by t3.str6");
      if (localResultSet1.next())
      {
        localEachProTrade2.setProName(localResultSet1.getString(3));
        localEachProTrade2.setTradeAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(1)));
        localEachProTrade2.setTradeMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(2)));
        localEachProTrade2.setProId(localResultSet1.getInt(4));
        localBigDecimal3 = localEachProTrade2.getTradeAmount();
        localBigDecimal4 = localEachProTrade2.getTradeMoney();
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      if (!ManaUtil.checkStr(localEachProTrade2.getProName()))
      {
        localResultSet1 = localDBBean.executeQuery("select name v1 from v_dictable where type=2 and value=" + paramInt2 + "");
        if (localResultSet1.next())
        {
          localEachProTrade2.setProName(localResultSet1.getString(1));
          localEachProTrade2.setProId(paramInt2);
          localEachProTrade2.setTradeAmount(new BigDecimal(0));
          localEachProTrade2.setTradeMoney(new BigDecimal(0));
        }
        localResultSet1.close();
        localDBBean.closeStmt();
      }
      for (i = 0; i < paramArrayOfString2.length; i++)
      {
        localObject1 = paramArrayOfString2[i].split(",");
        for (j = 0; j < localObject1.length; j++)
        {
          str2 = "";
          localObject2 = new EachProTrade();
          if (ManaUtil.checkStr(localObject1[j]))
          {
            str2 = paramHashMap.get(new Integer(localObject1[j])).toString();
            str1 = " and t3.str6=" + localObject1[j] + " and t1.tradedate<to_date('" + str2 + " 00:00:00'";
            str1 = str1 + ",'yyyy-mm-dd hh24:mi:ss') group by t3.str6";
            localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString() + str1);
            if (localResultSet2.next())
            {
              ((EachProTrade)localObject2).setProId(localResultSet2.getInt(4));
              ((EachProTrade)localObject2).setProName(localResultSet2.getString(3));
              localBigDecimal5 = ManaUtil.disBD(localResultSet2.getBigDecimal(1));
              localBigDecimal6 = ManaUtil.disBD(localResultSet2.getBigDecimal(2));
              localBigDecimal3 = localBigDecimal3.add(localBigDecimal5);
              localBigDecimal4 = localBigDecimal4.add(localBigDecimal6);
            }
            localResultSet2.close();
            localDBBean.closeStmt();
            if (!ManaUtil.checkStr(((EachProTrade)localObject2).getProName()))
            {
              localResultSet1 = localDBBean.executeQuery("select name v1 from v_dictable where type=2 and value=" + localObject1[j] + "");
              if (localResultSet1.next())
              {
                ((EachProTrade)localObject2).setProName(localResultSet1.getString(1));
                ((EachProTrade)localObject2).setProId(Integer.parseInt(localObject1[j]));
              }
              localResultSet1.close();
              localDBBean.closeStmt();
            }
            str1 = " and t3.str6=" + localObject1[j] + " and t1.tradedate>=to_date('" + str2 + " 00:00:00'";
            str1 = str1 + ",'yyyy-mm-dd hh24:mi:ss') group by t3.str6";
            localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString() + str1);
            if (localResultSet2.next())
            {
              ((EachProTrade)localObject2).setTradeAmount(ManaUtil.disBD(localResultSet2.getBigDecimal(1)));
              ((EachProTrade)localObject2).setTradeMoney(ManaUtil.disBD(localResultSet2.getBigDecimal(2)));
            }
            else
            {
              ((EachProTrade)localObject2).setTradeAmount(new BigDecimal(0));
              ((EachProTrade)localObject2).setTradeMoney(new BigDecimal(0));
            }
            localResultSet2.close();
            localDBBean.closeStmt();
            localArrayList2.add(localObject2);
          }
        }
      }
      BigDecimal localBigDecimal7 = null;
      Object localObject1 = null;
      for (int j = 0; j < localArrayList2.size(); j++)
      {
        localBigDecimal7 = new BigDecimal(0);
        localObject1 = new BigDecimal(0);
        localObject2 = (EachProTrade)localArrayList2.get(j);
        if (!localHashMap1.containsKey(new Integer(((EachProTrade)localObject2).getProId()))) {
          localHashMap1.put(new Integer(((EachProTrade)localObject2).getProId()), ((EachProTrade)localObject2).getProName());
        }
        if (localHashMap2.containsKey(new Integer(((EachProTrade)localObject2).getProId())))
        {
          localBigDecimal7 = (BigDecimal)localHashMap2.get(new Integer(((EachProTrade)localObject2).getProId()));
          localBigDecimal7 = localBigDecimal7.add(((EachProTrade)localObject2).getTradeAmount());
          localHashMap2.put(new Integer(((EachProTrade)localObject2).getProId()), localBigDecimal7);
        }
        else
        {
          localHashMap2.put(new Integer(((EachProTrade)localObject2).getProId()), ((EachProTrade)localObject2).getTradeAmount());
        }
        if (localHashMap3.containsKey(new Integer(((EachProTrade)localObject2).getProId())))
        {
          localObject1 = (BigDecimal)localHashMap3.get(new Integer(((EachProTrade)localObject2).getProId()));
          localObject1 = ((BigDecimal)localObject1).add(((EachProTrade)localObject2).getTradeMoney());
          localHashMap3.put(new Integer(((EachProTrade)localObject2).getProId()), localObject1);
        }
        else
        {
          localHashMap3.put(new Integer(((EachProTrade)localObject2).getProId()), ((EachProTrade)localObject2).getTradeMoney());
        }
      }
      Set localSet = localHashMap1.entrySet();
      Object localObject2 = localSet.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = new EachProTrade();
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject2).next();
        ((EachProTrade)localObject3).setProId(Integer.parseInt(localEntry.getKey().toString()));
        ((EachProTrade)localObject3).setProName(localEntry.getValue().toString());
        ((EachProTrade)localObject3).setTradeAmount((BigDecimal)localHashMap2.get(localEntry.getKey()));
        ((EachProTrade)localObject3).setTradeMoney((BigDecimal)localHashMap3.get(localEntry.getKey()));
        localArrayList1.add(localObject3);
      }
      localEachProTrade1.setTradeAmount(localBigDecimal1);
      localEachProTrade1.setTradeMoney(localBigDecimal2);
      localArrayList1.add(localEachProTrade1);
      localEachProTrade2.setTradeAmount(localBigDecimal3);
      localEachProTrade2.setTradeMoney(localBigDecimal4);
      localArrayList1.add(localEachProTrade2);
      localDBBean.close();
      Object localObject3 = localArrayList1;
      return localObject3;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      String str2 = null;
      return str2;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getCategory(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select name v1,value v2 from v_dictable where type=1 ");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = new AllTrade();
        ((AllTrade)localObject1).setCategoryId(localResultSet.getInt(2));
        ((AllTrade)localObject1).setCategoryName(localResultSet.getString(1));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Object localObject1 = null;
      return localObject1;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getVari(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      str = "";
      localStringBuffer.append("select distinct t2.str1 v1 from v_commodity t1,v_commext t2 where t1.id=t2.commid " + paramString2 + "");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet1.next())
      {
        localObject1 = new VariProYear();
        ((VariProYear)localObject1).setVari(localResultSet1.getString(1));
        localStringBuffer = new StringBuffer();
        str = "";
        localStringBuffer.append("select distinct t2.str5 v1 from v_commodity t1,v_commext t2 where t1.id=t2.commid and ");
        localStringBuffer.append(" t2.str1='" + ((VariProYear)localObject1).getVari() + "'");
        localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
        while (localResultSet2.next()) {
          str = str + localResultSet2.getString(1) + ",";
        }
        ((VariProYear)localObject1).setVari(((VariProYear)localObject1).getVari() + ":" + str);
        localArrayList.add(localObject1);
      }
      localResultSet1.close();
      localDBBean.close();
      Object localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      String str = null;
      return str;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getProYear(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select distinct t2.str5 v1 from v_commodity t1,v_commext t2 where t1.id=t2.commid " + paramString2 + "");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject1 = new VariProYear();
        ((VariProYear)localObject1).setProYear(localResultSet.getString(1));
        localArrayList.add(localObject1);
      }
      localResultSet.close();
      localDBBean.close();
      localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Object localObject1 = null;
      return localObject1;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getOutLogDetail(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    String str1 = null;
    Object localObject1 = null;
    Object localObject2 = null;
    String str2 = null;
    HashMap localHashMap = null;
    Set localSet = null;
    Iterator localIterator = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select  t4.outid v1,t3.contractid v2,t3.tradedate v3,t3.code v4, t4.amount v5");
      localStringBuffer.append(",t3.userid v6,t2.str2 v7,t2.str3 v8,t3.amount v9,t3.price v10,(select t5.name");
      localStringBuffer.append(" from v_tradeuserext t5 where t5.usercode=t3.userid) v11 from v_commodity t1,v_commext");
      localStringBuffer.append(" t2,v_hisbargain t3,v_outlog t4 where t1.id=t2.commid and t1.id=t3.commodityid and");
      localStringBuffer.append(" t3.contractid=t4.contractid and t4.finished=2 " + paramString2 + " order by t3.contractid asc");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject3 = new OutLogDetail();
        str1 = null;
        localObject1 = null;
        localObject2 = null;
        str2 = "";
        ((OutLogDetail)localObject3).setOutId(localResultSet.getString(1));
        ((OutLogDetail)localObject3).setContractId(localResultSet.getString(2));
        ((OutLogDetail)localObject3).setTradeDate(localResultSet.getString(3).substring(0, 10));
        ((OutLogDetail)localObject3).setCode(localResultSet.getString(4));
        ((OutLogDetail)localObject3).setAmount(ManaUtil.disBD(localResultSet.getBigDecimal(5)));
        ((OutLogDetail)localObject3).setUserCode(localResultSet.getString(6));
        ((OutLogDetail)localObject3).setSellerName(localResultSet.getString(7));
        ((OutLogDetail)localObject3).setTradeAmount(ManaUtil.disBD(localResultSet.getBigDecimal(9)));
        ((OutLogDetail)localObject3).setPrice(ManaUtil.disBD(localResultSet.getBigDecimal(10)));
        ((OutLogDetail)localObject3).setBuyerName(localResultSet.getString(11));
        str1 = localResultSet.getString(8);
        if (str1 != null)
        {
          localHashMap = ManaUtil.addPlaceGrade(str1);
          localSet = localHashMap.entrySet();
          localIterator = localSet.iterator();
          while (localIterator.hasNext())
          {
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            str2 = str2 + localEntry.getKey().toString() + "/";
          }
        }
        ((OutLogDetail)localObject3).setWareHouse(str2);
        localArrayList.add(localObject3);
      }
      localResultSet.close();
      localDBBean.close();
      localObject3 = localArrayList;
      return localObject3;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Object localObject3 = null;
      return localObject3;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException4) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getFinanceOut(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList localArrayList1 = new ArrayList();
      localArrayList2 = null;
      StringBuffer localStringBuffer = new StringBuffer();
      int i = 0;
      String str = "";
      BigDecimal localBigDecimal = null;
      localStringBuffer.append("select distinct c.str6 v1,(select name from v_dictable where type=2 and value=c.str6) v2 from v_hisbargain b,");
      localStringBuffer.append("v_commodity c1,v_commExt c where b.commodityID=c1.id and c.commid=c1.id " + paramString2 + " order by c.str6");
      localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet2.next())
      {
        localObject1 = new FinanceOut();
        localArrayList2 = new ArrayList();
        i = localResultSet2.getInt(1);
        str = "";
        localBigDecimal = new BigDecimal(0);
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select (select name from v_dictable where type=1 and value=c1.category) v1,c.str5 v2,c1.category v3,sum(b.amount)");
        localStringBuffer.append(" v4 from v_hisbargain b,v_commodity c1,v_commExt c,v_tradeUserExt u where b.commodityID=c1.id and c.commid=c1.id and c.str6='" + i + "'");
        localStringBuffer.append(" and b.userid=u.usercode " + paramString2 + " group by c1.category,c.str5 order by c1.category asc");
        localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
        while (localResultSet1.next())
        {
          localBigDecimal = localBigDecimal.add(localResultSet1.getBigDecimal(4));
          str = str + localResultSet1.getString(2) + "年" + localResultSet1.getString(1) + ":" + ManaUtil.disBD(localResultSet1.getBigDecimal(4)) + ";";
        }
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select b.contractID v1,b.code v2,c.str2 v3,c.Str3 v4,c.Str4 v5,b.price v6,u.name v7,c.str1 v8,c1.amount v9,");
        localStringBuffer.append("c.str6 v10,(select name from v_dictable where type=2 and value=c.str6) v11,b.actualamount v12,(select sum(amount)");
        localStringBuffer.append("from v_outlog o where o.contractid=b.contractid and finished=2) v13,b.fellbackamount v14,b.userid v15,b.tradedate v16");
        localStringBuffer.append(",c1.id v17,b.amount*b.price v18,c.str5 v19 from v_hisbargain b,v_commodity c1,v_commExt c,v_tradeUserExt u where b.userID=u.userCode");
        localStringBuffer.append(" and b.commodityID=c1.id and c.commid=c1.id and c.str6='" + i + "' " + paramString2 + " order by b.contractid asc");
        localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
        while (localResultSet1.next())
        {
          PlanDetail localPlanDetail = new PlanDetail();
          localPlanDetail.setContractId(localResultSet1.getString(1));
          localPlanDetail.setCode(localResultSet1.getString(2));
          localPlanDetail.setKudian(localResultSet1.getString(3));
          localPlanDetail.setStr3(localResultSet1.getString(4));
          localPlanDetail.setPrice(ManaUtil.disBD(localResultSet1.getBigDecimal(6)));
          localPlanDetail.setName(localResultSet1.getString(7));
          localPlanDetail.setVari(localResultSet1.getString(8));
          localPlanDetail.setAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(9)));
          localPlanDetail.setProId(localResultSet1.getInt(10));
          localPlanDetail.setProName(localResultSet1.getString(11));
          localPlanDetail.setActualAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(12)));
          localPlanDetail.setLiftedAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(13)));
          localPlanDetail.setFellBackAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(14)));
          localPlanDetail.setUserCode(localResultSet1.getString(15));
          localPlanDetail.setTradeDate(localResultSet1.getString(16).substring(0, 10));
          localPlanDetail.setCommodityId(localResultSet1.getString(17));
          localPlanDetail.setTradeMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(18)));
          localPlanDetail.setProYear(localResultSet1.getString(19));
          localArrayList2.add(localPlanDetail);
        }
        localResultSet1.close();
        localDBBean.closeStmt();
        if (localArrayList2.size() > 0)
        {
          str = "总成交量:" + localBigDecimal.toString() + ";其中:" + str;
          ((FinanceOut)localObject1).setProName(localResultSet2.getString(2));
          ((FinanceOut)localObject1).setPlanDetail(localArrayList2);
          ((FinanceOut)localObject1).setProAmount(str);
          localArrayList1.add(localObject1);
        }
      }
      localResultSet2.close();
      localDBBean.close();
      Object localObject1 = localArrayList1;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      ArrayList localArrayList2 = null;
      return localArrayList2;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList[] getTradeDetail(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList[] arrayOfArrayList = new ArrayList[2];
      arrayOfArrayList[0] = new ArrayList();
      arrayOfArrayList[1] = new ArrayList();
      localStringBuffer = new StringBuffer();
      int i = 0;
      localStringBuffer.append("select count(t1.contractid) n from v_hisbargain t1,v_commext t2,v_commodity t3 where");
      localStringBuffer.append(" t1.commodityid=t2.commid and t2.commid=t3.id and t1.status>0 " + paramString2 + "");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet1.next()) {
        i = localResultSet1.getInt("n");
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select (select name from v_dictable where type=2 and value=t2.str6) v1,t1.amount v2,t2.str6 v3,");
      localStringBuffer.append("t1.amount*t1.price v4,t1.price v5,t2.num1 v6,t1.amount*t2.num1 v7,t1.actualamount v8,");
      localStringBuffer.append("t1.price*t1.actualamount v9,t2.num1*t1.actualamount v10,t1.amount-t1.actualamount-");
      localStringBuffer.append("t1.fellbackamount v11,t1.fellbackamount v12,case when t1.result=1 then t1.fellbackamount*t1.price");
      localStringBuffer.append(" else 0 end v13,t2.str2 v14,t1.code v15,t1.contractid v16,t2.str3 v17,t2.str1 v18");
      localStringBuffer.append(",(select opertime from v_contractoperstatus where status=2 and contractid=t1.contractid) v19 ");
      localStringBuffer.append(",t2.str4 v20,(select sum(money) from v_hismoney where operation=512 and contractno=t1.contractid) v21");
      localStringBuffer.append(",t1.tradedate v22 from v_hisbargain t1,v_commext t2,v_commodity t3 where t1.commodityid=t2.commid and");
      localStringBuffer.append(" t2.commid=t3.id and t1.status>0 " + paramString2 + " order by t2.str6 asc,t1.contractid asc,t1.tradedate asc ");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      Object localObject1 = "";
      Object localObject2 = "";
      String str1 = "";
      Object localObject3 = null;
      Object localObject4 = null;
      Object localObject5 = null;
      Object localObject6 = null;
      String str2 = "";
      Object localObject7 = "";
      String str3 = "";
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      BigDecimal localBigDecimal3 = new BigDecimal(0);
      BigDecimal localBigDecimal4 = new BigDecimal(0);
      BigDecimal localBigDecimal5 = new BigDecimal(0);
      BigDecimal localBigDecimal6 = new BigDecimal(0);
      BigDecimal localBigDecimal7 = new BigDecimal(0);
      BigDecimal localBigDecimal8 = new BigDecimal(0);
      BigDecimal localBigDecimal9 = new BigDecimal(0);
      BigDecimal localBigDecimal10 = new BigDecimal(0);
      BigDecimal localBigDecimal11 = new BigDecimal(0);
      BigDecimal localBigDecimal12 = new BigDecimal(0);
      BigDecimal localBigDecimal13 = new BigDecimal(0);
      BigDecimal localBigDecimal14 = new BigDecimal(0);
      BigDecimal localBigDecimal15 = new BigDecimal(0);
      BigDecimal localBigDecimal16 = new BigDecimal(0);
      int j = 0;
      BigDecimal localBigDecimal17 = new BigDecimal(0);
      BigDecimal localBigDecimal18 = new BigDecimal(0);
      BigDecimal localBigDecimal19 = new BigDecimal(0);
      BigDecimal localBigDecimal20 = new BigDecimal(0);
      BigDecimal localBigDecimal21 = new BigDecimal(0);
      BigDecimal localBigDecimal22 = new BigDecimal(0);
      BigDecimal localBigDecimal23 = new BigDecimal(0);
      BigDecimal localBigDecimal24 = new BigDecimal(0);
      BigDecimal localBigDecimal25 = new BigDecimal(0);
      BigDecimal localBigDecimal26 = new BigDecimal(0);
      BigDecimal localBigDecimal27 = new BigDecimal(0);
      BigDecimal localBigDecimal28 = new BigDecimal(0);
      BigDecimal localBigDecimal29 = new BigDecimal(0);
      BigDecimal localBigDecimal30 = new BigDecimal(0);
      BigDecimal localBigDecimal31 = new BigDecimal(0);
      BigDecimal localBigDecimal32 = new BigDecimal(0);
      BigDecimal localBigDecimal33 = new BigDecimal(0);
      BigDecimal localBigDecimal34 = new BigDecimal(0);
      BigDecimal localBigDecimal35 = new BigDecimal(0);
      BigDecimal localBigDecimal36 = new BigDecimal(0);
      int k = 0;
      BigDecimal localBigDecimal37 = new BigDecimal(0);
      BigDecimal localBigDecimal38 = new BigDecimal(0);
      BigDecimal localBigDecimal39 = new BigDecimal(0);
      BigDecimal localBigDecimal40 = new BigDecimal(0);
      BigDecimal localBigDecimal41 = new BigDecimal(0);
      BigDecimal localBigDecimal42 = new BigDecimal(0);
      ArrayList localArrayList = new ArrayList();
      int m = 0;
      Timestamp localTimestamp = new Timestamp(System.currentTimeMillis());
      while (localResultSet1.next())
      {
        str2 = "";
        localObject8 = new TradeDetail();
        ((TradeDetail)localObject8).setCode(localResultSet1.getString(15));
        ((TradeDetail)localObject8).setContractNo(localResultSet1.getString(16));
        ((TradeDetail)localObject8).setSellerName(localResultSet1.getString(14));
        ((TradeDetail)localObject8).setVari(localResultSet1.getString(18));
        ((TradeDetail)localObject8).setTradeAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(2)));
        ((TradeDetail)localObject8).setPrice(ManaUtil.disBD(localResultSet1.getBigDecimal(5)));
        ((TradeDetail)localObject8).setTradeMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(4)));
        ((TradeDetail)localObject8).setBuyCost(ManaUtil.disBD(localResultSet1.getBigDecimal(6)));
        ((TradeDetail)localObject8).setLoanMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(7)));
        ((TradeDetail)localObject8).setActualAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(8)));
        ((TradeDetail)localObject8).setActualMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(9)));
        ((TradeDetail)localObject8).setHaveLoanMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(10)));
        ((TradeDetail)localObject8).setExhauAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(11)));
        ((TradeDetail)localObject8).setFellBackAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(12)));
        ((TradeDetail)localObject8).setBuyerFellBackMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(13)));
        ((TradeDetail)localObject8).setKuDianMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(21)));
        ((TradeDetail)localObject8).setTradeDate(localResultSet1.getString(22).substring(0, 10));
        if (((TradeDetail)localObject8).getActualMoney().compareTo(((TradeDetail)localObject8).getHaveLoanMoney()) < 0)
        {
          ((TradeDetail)localObject8).setReturnLoanMoney(((TradeDetail)localObject8).getActualMoney());
          ((TradeDetail)localObject8).setNotLoanMoney(((TradeDetail)localObject8).getHaveLoanMoney().subtract(((TradeDetail)localObject8).getActualMoney()));
        }
        else
        {
          ((TradeDetail)localObject8).setReturnLoanMoney(((TradeDetail)localObject8).getHaveLoanMoney());
          ((TradeDetail)localObject8).setNotLoanMoney(new BigDecimal(0));
        }
        ((TradeDetail)localObject8).setNotLoanInter(((TradeDetail)localObject8).getNotLoanMoney().multiply(new BigDecimal(3.7332D)).divide(new BigDecimal(360), 2, 4));
        ((TradeDetail)localObject8).setPoundage(((TradeDetail)localObject8).getTradeMoney().multiply(new BigDecimal(0.0008D)).setScale(2, 4));
        ((TradeDetail)localObject8).setIncome(((TradeDetail)localObject8).getActualMoney().add(((TradeDetail)localObject8).getBuyerFellBackMoney()));
        ((TradeDetail)localObject8).setExpenditure(((TradeDetail)localObject8).getHaveLoanMoney().add(((TradeDetail)localObject8).getPoundage()));
        ((TradeDetail)localObject8).setDiffProfitLoss(((TradeDetail)localObject8).getIncome().subtract(((TradeDetail)localObject8).getExpenditure()));
        if (((TradeDetail)localObject8).getActualMoney().subtract(((TradeDetail)localObject8).getHaveLoanMoney()).compareTo(new BigDecimal(0)) < 0) {
          ((TradeDetail)localObject8).setApproFinance(((TradeDetail)localObject8).getHaveLoanMoney().subtract(((TradeDetail)localObject8).getActualMoney()).add(((TradeDetail)localObject8).getNotLoanInter()).add(((TradeDetail)localObject8).getPoundage()));
        } else {
          ((TradeDetail)localObject8).setApproFinance(((TradeDetail)localObject8).getNotLoanInter().add(((TradeDetail)localObject8).getPoundage()));
        }
        ((TradeDetail)localObject8).setGradeStr(localResultSet1.getString(20));
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select opertime from v_acceptpayment where contractid=" + ((TradeDetail)localObject8).getContractNo() + "");
        localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
        while (localResultSet2.next()) {
          if (localResultSet2.getString("opertime") != null) {
            str2 = str2 + localResultSet2.getString("opertime").substring(0, 10).replace('-', '/') + ";";
          }
        }
        localResultSet2.close();
        localDBBean.closeStmt();
        ((TradeDetail)localObject8).setPayDay(str2);
        if (localResultSet1.getString(19) != null)
        {
          ((TradeDetail)localObject8).setConfirmListDay(localResultSet1.getString(19).substring(0, 10).replace('-', '/'));
          localStringBuffer = new StringBuffer();
          localStringBuffer.append("select min(opertime) t from acceptpayment where contractid=");
          localStringBuffer.append("" + ((TradeDetail)localObject8).getContractNo() + " and opertime>to_date('" + ((TradeDetail)localObject8).getConfirmListDay() + "");
          localStringBuffer.append(" 00:00:00','yyyy-mm-dd hh24:mi:ss')");
          localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
          if (localResultSet2.next())
          {
            if (localResultSet2.getString("t") != null) {
              ((TradeDetail)localObject8).setDelayDays(ManaUtil.betweenDays(new Date(((TradeDetail)localObject8).getConfirmListDay()), new Date(localResultSet2.getString("t").substring(0, 10).replace('-', '/'))));
            } else {
              ((TradeDetail)localObject8).setDelayDays(0);
            }
          }
          else {
            ((TradeDetail)localObject8).setDelayDays(0);
          }
          localResultSet2.close();
          localDBBean.closeStmt();
        }
        else
        {
          ((TradeDetail)localObject8).setDelayDays(0);
        }
        str1 = localResultSet1.getString(3);
        str3 = localResultSet1.getString(1);
        localObject2 = str1;
        localBigDecimal23 = localBigDecimal23.add(((TradeDetail)localObject8).getTradeAmount());
        localBigDecimal24 = localBigDecimal24.add(((TradeDetail)localObject8).getTradeMoney());
        localBigDecimal25 = localBigDecimal25.add(((TradeDetail)localObject8).getLoanMoney());
        localBigDecimal26 = localBigDecimal26.add(((TradeDetail)localObject8).getActualAmount());
        localBigDecimal27 = localBigDecimal27.add(((TradeDetail)localObject8).getActualMoney());
        localBigDecimal28 = localBigDecimal28.add(((TradeDetail)localObject8).getHaveLoanMoney());
        localBigDecimal29 = localBigDecimal29.add(ManaUtil.disBD(((TradeDetail)localObject8).getConcelContract()));
        localBigDecimal30 = localBigDecimal30.add(((TradeDetail)localObject8).getExhauAmount());
        localBigDecimal31 = localBigDecimal31.add(((TradeDetail)localObject8).getFellBackAmount());
        localBigDecimal32 = localBigDecimal32.add(((TradeDetail)localObject8).getBuyerFellBackMoney());
        localBigDecimal33 = localBigDecimal33.add(((TradeDetail)localObject8).getReturnLoanMoney());
        localBigDecimal34 = localBigDecimal34.add(((TradeDetail)localObject8).getNotLoanMoney());
        localBigDecimal35 = localBigDecimal35.add(((TradeDetail)localObject8).getNotLoanInter());
        localBigDecimal36 = localBigDecimal36.add(ManaUtil.disBD(((TradeDetail)localObject8).getDelayLoanInter()));
        k += ((TradeDetail)localObject8).getDelayDays();
        localBigDecimal37 = localBigDecimal37.add(((TradeDetail)localObject8).getPoundage());
        localBigDecimal38 = localBigDecimal38.add(((TradeDetail)localObject8).getIncome());
        localBigDecimal39 = localBigDecimal39.add(((TradeDetail)localObject8).getExpenditure());
        localBigDecimal40 = localBigDecimal40.add(((TradeDetail)localObject8).getDiffProfitLoss());
        localBigDecimal41 = localBigDecimal41.add(((TradeDetail)localObject8).getApproFinance());
        localBigDecimal42 = localBigDecimal42.add(((TradeDetail)localObject8).getKuDianMoney());
        if (m == 0)
        {
          localObject1 = str1;
          localObject7 = str3;
          localBigDecimal1 = localBigDecimal1.add(((TradeDetail)localObject8).getTradeAmount());
          localBigDecimal3 = localBigDecimal3.add(((TradeDetail)localObject8).getTradeMoney());
          localBigDecimal5 = localBigDecimal5.add(((TradeDetail)localObject8).getLoanMoney());
          localBigDecimal6 = localBigDecimal6.add(((TradeDetail)localObject8).getActualAmount());
          localBigDecimal7 = localBigDecimal7.add(((TradeDetail)localObject8).getActualMoney());
          localBigDecimal8 = localBigDecimal8.add(((TradeDetail)localObject8).getHaveLoanMoney());
          localBigDecimal9 = localBigDecimal9.add(ManaUtil.disBD(((TradeDetail)localObject8).getConcelContract()));
          localBigDecimal10 = localBigDecimal10.add(((TradeDetail)localObject8).getExhauAmount());
          localBigDecimal11 = localBigDecimal11.add(((TradeDetail)localObject8).getFellBackAmount());
          localBigDecimal12 = localBigDecimal12.add(((TradeDetail)localObject8).getBuyerFellBackMoney());
          localBigDecimal13 = localBigDecimal13.add(((TradeDetail)localObject8).getReturnLoanMoney());
          localBigDecimal14 = localBigDecimal14.add(((TradeDetail)localObject8).getNotLoanMoney());
          localBigDecimal15 = localBigDecimal15.add(((TradeDetail)localObject8).getNotLoanInter());
          localBigDecimal16 = localBigDecimal16.add(ManaUtil.disBD(((TradeDetail)localObject8).getDelayLoanInter()));
          j += ((TradeDetail)localObject8).getDelayDays();
          localBigDecimal17 = localBigDecimal17.add(((TradeDetail)localObject8).getPoundage());
          localBigDecimal18 = localBigDecimal18.add(((TradeDetail)localObject8).getIncome());
          localBigDecimal19 = localBigDecimal19.add(((TradeDetail)localObject8).getExpenditure());
          localBigDecimal20 = localBigDecimal20.add(((TradeDetail)localObject8).getDiffProfitLoss());
          localBigDecimal21 = localBigDecimal21.add(((TradeDetail)localObject8).getApproFinance());
          localBigDecimal22 = localBigDecimal22.add(((TradeDetail)localObject8).getKuDianMoney());
        }
        HJProTradeDetail localHJProTradeDetail;
        if (!((String)localObject2).equals(localObject1))
        {
          localHJProTradeDetail = new HJProTradeDetail();
          localHJProTradeDetail.setProName((String)localObject7);
          localHJProTradeDetail.setTradeAmount(localBigDecimal1);
          localHJProTradeDetail.setTradeMoney(localBigDecimal3);
          localHJProTradeDetail.setPrice(localBigDecimal3.divide(localBigDecimal1, 2, 4));
          localHJProTradeDetail.setBuyCost(localBigDecimal5.divide(localBigDecimal1, 4, 4));
          localHJProTradeDetail.setLoanMoney(localBigDecimal5);
          localHJProTradeDetail.setActualAmount(localBigDecimal6);
          localHJProTradeDetail.setActualMoney(localBigDecimal7);
          localHJProTradeDetail.setHaveLoanMoney(localBigDecimal8);
          localHJProTradeDetail.setConcelContract(localBigDecimal9);
          localHJProTradeDetail.setExhauAmount(localBigDecimal10);
          localHJProTradeDetail.setFellBackAmount(localBigDecimal11);
          localHJProTradeDetail.setBuyerFellBackMoney(localBigDecimal12);
          localHJProTradeDetail.setReturnLoanMoney(localBigDecimal13);
          localHJProTradeDetail.setNotLoanMoney(localBigDecimal14);
          localHJProTradeDetail.setNotLoanInter(localBigDecimal15);
          localHJProTradeDetail.setDelayDays(j);
          localHJProTradeDetail.setPoundage(localBigDecimal17);
          localHJProTradeDetail.setIncome(localBigDecimal18);
          localHJProTradeDetail.setExpenditure(localBigDecimal19);
          localHJProTradeDetail.setDiffProfitLoss(localBigDecimal20);
          localHJProTradeDetail.setApproFinance(localBigDecimal21);
          localHJProTradeDetail.setTradeDetail(localArrayList);
          localHJProTradeDetail.setKuDianMoney(localBigDecimal22);
          arrayOfArrayList[0].add(localHJProTradeDetail);
          localArrayList = new ArrayList();
          localObject1 = localObject2;
          localObject7 = str3;
          localBigDecimal1 = ((TradeDetail)localObject8).getTradeAmount();
          localBigDecimal3 = ((TradeDetail)localObject8).getTradeMoney();
          localBigDecimal5 = ((TradeDetail)localObject8).getLoanMoney();
          localBigDecimal6 = ((TradeDetail)localObject8).getActualAmount();
          localBigDecimal7 = ((TradeDetail)localObject8).getActualMoney();
          localBigDecimal8 = ((TradeDetail)localObject8).getHaveLoanMoney();
          localBigDecimal9 = ManaUtil.disBD(((TradeDetail)localObject8).getConcelContract());
          localBigDecimal10 = ((TradeDetail)localObject8).getExhauAmount();
          localBigDecimal11 = ((TradeDetail)localObject8).getFellBackAmount();
          localBigDecimal12 = ((TradeDetail)localObject8).getBuyerFellBackMoney();
          localBigDecimal13 = ((TradeDetail)localObject8).getReturnLoanMoney();
          localBigDecimal14 = ((TradeDetail)localObject8).getNotLoanMoney();
          localBigDecimal15 = ((TradeDetail)localObject8).getNotLoanInter();
          localBigDecimal16 = ManaUtil.disBD(((TradeDetail)localObject8).getDelayLoanInter());
          j = ((TradeDetail)localObject8).getDelayDays();
          localBigDecimal17 = ((TradeDetail)localObject8).getPoundage();
          localBigDecimal18 = ((TradeDetail)localObject8).getIncome();
          localBigDecimal19 = ((TradeDetail)localObject8).getExpenditure();
          localBigDecimal20 = ((TradeDetail)localObject8).getDiffProfitLoss();
          localBigDecimal21 = ((TradeDetail)localObject8).getApproFinance();
          localBigDecimal22 = ((TradeDetail)localObject8).getKuDianMoney();
        }
        else if (m > 0)
        {
          localBigDecimal1 = localBigDecimal1.add(((TradeDetail)localObject8).getTradeAmount());
          localBigDecimal3 = localBigDecimal3.add(((TradeDetail)localObject8).getTradeMoney());
          localBigDecimal5 = localBigDecimal5.add(((TradeDetail)localObject8).getLoanMoney());
          localBigDecimal6 = localBigDecimal6.add(((TradeDetail)localObject8).getActualAmount());
          localBigDecimal7 = localBigDecimal7.add(((TradeDetail)localObject8).getActualMoney());
          localBigDecimal8 = localBigDecimal8.add(((TradeDetail)localObject8).getHaveLoanMoney());
          localBigDecimal9 = localBigDecimal9.add(ManaUtil.disBD(((TradeDetail)localObject8).getConcelContract()));
          localBigDecimal10 = localBigDecimal10.add(((TradeDetail)localObject8).getExhauAmount());
          localBigDecimal11 = localBigDecimal11.add(((TradeDetail)localObject8).getFellBackAmount());
          localBigDecimal12 = localBigDecimal12.add(((TradeDetail)localObject8).getBuyerFellBackMoney());
          localBigDecimal13 = localBigDecimal13.add(((TradeDetail)localObject8).getReturnLoanMoney());
          localBigDecimal14 = localBigDecimal14.add(((TradeDetail)localObject8).getNotLoanMoney());
          localBigDecimal15 = localBigDecimal15.add(((TradeDetail)localObject8).getNotLoanInter());
          localBigDecimal16 = localBigDecimal16.add(ManaUtil.disBD(((TradeDetail)localObject8).getDelayLoanInter()));
          j += ((TradeDetail)localObject8).getDelayDays();
          localBigDecimal17 = localBigDecimal17.add(((TradeDetail)localObject8).getPoundage());
          localBigDecimal18 = localBigDecimal18.add(((TradeDetail)localObject8).getIncome());
          localBigDecimal19 = localBigDecimal19.add(((TradeDetail)localObject8).getExpenditure());
          localBigDecimal20 = localBigDecimal20.add(((TradeDetail)localObject8).getDiffProfitLoss());
          localBigDecimal21 = localBigDecimal21.add(((TradeDetail)localObject8).getApproFinance());
          localBigDecimal22 = localBigDecimal22.add(((TradeDetail)localObject8).getKuDianMoney());
        }
        localArrayList.add(localObject8);
        if (m == i - 1)
        {
          localHJProTradeDetail = new HJProTradeDetail();
          localHJProTradeDetail.setProName((String)localObject7);
          localHJProTradeDetail.setTradeAmount(localBigDecimal1);
          localHJProTradeDetail.setTradeMoney(localBigDecimal3);
          localHJProTradeDetail.setPrice(localBigDecimal3.divide(localBigDecimal1, 2, 4));
          localHJProTradeDetail.setBuyCost(localBigDecimal5.divide(localBigDecimal1, 4, 4));
          localHJProTradeDetail.setLoanMoney(localBigDecimal5);
          localHJProTradeDetail.setActualAmount(localBigDecimal6);
          localHJProTradeDetail.setActualMoney(localBigDecimal7);
          localHJProTradeDetail.setHaveLoanMoney(localBigDecimal8);
          localHJProTradeDetail.setConcelContract(localBigDecimal9);
          localHJProTradeDetail.setExhauAmount(localBigDecimal10);
          localHJProTradeDetail.setFellBackAmount(localBigDecimal11);
          localHJProTradeDetail.setBuyerFellBackMoney(localBigDecimal12);
          localHJProTradeDetail.setReturnLoanMoney(localBigDecimal13);
          localHJProTradeDetail.setNotLoanMoney(localBigDecimal14);
          localHJProTradeDetail.setNotLoanInter(localBigDecimal15);
          localHJProTradeDetail.setDelayDays(j);
          localHJProTradeDetail.setPoundage(localBigDecimal17);
          localHJProTradeDetail.setIncome(localBigDecimal18);
          localHJProTradeDetail.setExpenditure(localBigDecimal19);
          localHJProTradeDetail.setDiffProfitLoss(localBigDecimal20);
          localHJProTradeDetail.setApproFinance(localBigDecimal21);
          localHJProTradeDetail.setTradeDetail(localArrayList);
          localHJProTradeDetail.setKuDianMoney(localBigDecimal22);
          arrayOfArrayList[0].add(localHJProTradeDetail);
        }
        m++;
      }
      localResultSet1.close();
      localDBBean.close();
      if (arrayOfArrayList[0].size() > 0)
      {
        localObject8 = new HJProTradeDetail();
        ((HJProTradeDetail)localObject8).setTradeAmount(localBigDecimal23);
        ((HJProTradeDetail)localObject8).setTradeMoney(localBigDecimal24);
        ((HJProTradeDetail)localObject8).setLoanMoney(localBigDecimal25);
        ((HJProTradeDetail)localObject8).setActualAmount(localBigDecimal26);
        ((HJProTradeDetail)localObject8).setActualMoney(localBigDecimal27);
        ((HJProTradeDetail)localObject8).setHaveLoanMoney(localBigDecimal28);
        ((HJProTradeDetail)localObject8).setConcelContract(localBigDecimal29);
        ((HJProTradeDetail)localObject8).setExhauAmount(localBigDecimal30);
        ((HJProTradeDetail)localObject8).setFellBackAmount(localBigDecimal31);
        ((HJProTradeDetail)localObject8).setBuyerFellBackMoney(localBigDecimal32);
        ((HJProTradeDetail)localObject8).setReturnLoanMoney(localBigDecimal33);
        ((HJProTradeDetail)localObject8).setNotLoanMoney(localBigDecimal34);
        ((HJProTradeDetail)localObject8).setNotLoanInter(localBigDecimal35);
        ((HJProTradeDetail)localObject8).setDelayDays(k);
        ((HJProTradeDetail)localObject8).setPoundage(localBigDecimal37);
        ((HJProTradeDetail)localObject8).setIncome(localBigDecimal38);
        ((HJProTradeDetail)localObject8).setExpenditure(localBigDecimal39);
        ((HJProTradeDetail)localObject8).setDiffProfitLoss(localBigDecimal40);
        ((HJProTradeDetail)localObject8).setApproFinance(localBigDecimal41);
        ((HJProTradeDetail)localObject8).setKuDianMoney(localBigDecimal42);
        arrayOfArrayList[1].add(localObject8);
      }
      Object localObject8 = arrayOfArrayList;
      return localObject8;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList[] getTradeDetailGather(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    Object localObject1 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList[] arrayOfArrayList = new ArrayList[2];
      arrayOfArrayList[0] = new ArrayList();
      arrayOfArrayList[1] = new ArrayList();
      localStringBuffer = new StringBuffer();
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      BigDecimal localBigDecimal3 = new BigDecimal(0);
      BigDecimal localBigDecimal4 = new BigDecimal(0);
      BigDecimal localBigDecimal5 = new BigDecimal(0);
      BigDecimal localBigDecimal6 = new BigDecimal(0);
      BigDecimal localBigDecimal7 = new BigDecimal(0);
      BigDecimal localBigDecimal8 = new BigDecimal(0);
      BigDecimal localBigDecimal9 = new BigDecimal(0);
      BigDecimal localBigDecimal10 = new BigDecimal(0);
      BigDecimal localBigDecimal11 = new BigDecimal(0);
      BigDecimal localBigDecimal12 = new BigDecimal(0);
      BigDecimal localBigDecimal13 = new BigDecimal(0);
      BigDecimal localBigDecimal14 = new BigDecimal(0);
      BigDecimal localBigDecimal15 = new BigDecimal(0);
      BigDecimal localBigDecimal16 = new BigDecimal(0);
      BigDecimal localBigDecimal17 = new BigDecimal(0);
      BigDecimal localBigDecimal18 = new BigDecimal(0);
      BigDecimal localBigDecimal19 = new BigDecimal(0);
      localStringBuffer.append("select (select name from dictable where type=2 and value=t2.str6) v1,t2.str1 v2,");
      localStringBuffer.append("sum(t1.amount) v3,sum(t1.amount*t1.price) v4,avg(t1.price) v5,avg(t2.num1) v6,");
      localStringBuffer.append("sum(t1.amount*t2.num1) v7,sum(t1.actualamount) v8,sum(t1.price*t1.actualamount)");
      localStringBuffer.append(" v9,sum(t2.num1*t1.actualamount) v10,sum(t1.amount-t1.actualamount-t1.fellbackamount)");
      localStringBuffer.append(" v11,sum(t1.fellbackamount) v12,sum(case when t1.result=1 then t1.fellbackamount*");
      localStringBuffer.append("t1.price else 0 end) v13,sum(case when t1.actualamount*t1.price<t1.actualamount*t2.num1");
      localStringBuffer.append(" then  t1.actualamount*t1.price else t1.actualamount*t2.num1 end) v14,sum(case when");
      localStringBuffer.append(" t1.actualamount*t1.price< t1.actualamount*t2.num1 then (t1.actualamount*t2.num1)-");
      localStringBuffer.append("(t1.actualamount*t1.price) else 0 end) v15, sum(round(t1.amount*t1.price*0.0008,2)) v16,");
      localStringBuffer.append("sum(case when (t1.actualamount*t1.price)- (t1.actualamount*t2.num1)<0 then (");
      localStringBuffer.append("t1.actualamount*t2.num1)-(t1.actualamount*t1.price) else 0 end) v17 from ");
      localStringBuffer.append("hisbargain t1,commext t2,commodity t3 where t1.commodityid=t2.commid and");
      localStringBuffer.append(" t1.commodityid=t3.id  and t1.status>0 " + paramString2 + " group by t2.str6,t2.str1");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject2 = new HJProTradeDetail();
        ((HJProTradeDetail)localObject2).setProName(localResultSet.getString(1));
        ((HJProTradeDetail)localObject2).setVari(localResultSet.getString(2));
        ((HJProTradeDetail)localObject2).setTradeAmount(ManaUtil.disBD(localResultSet.getBigDecimal(3)));
        ((HJProTradeDetail)localObject2).setTradeMoney(ManaUtil.disBD(localResultSet.getBigDecimal(4)));
        ((HJProTradeDetail)localObject2).setPrice(((HJProTradeDetail)localObject2).getTradeMoney().divide(((HJProTradeDetail)localObject2).getTradeAmount(), 2, 4));
        ((HJProTradeDetail)localObject2).setLoanMoney(ManaUtil.disBD(localResultSet.getBigDecimal(7)));
        ((HJProTradeDetail)localObject2).setBuyCost(((HJProTradeDetail)localObject2).getLoanMoney().divide(((HJProTradeDetail)localObject2).getTradeAmount(), 4, 4));
        ((HJProTradeDetail)localObject2).setActualAmount(ManaUtil.disBD(localResultSet.getBigDecimal(8)));
        ((HJProTradeDetail)localObject2).setActualMoney(ManaUtil.disBD(localResultSet.getBigDecimal(9)));
        ((HJProTradeDetail)localObject2).setHaveLoanMoney(ManaUtil.disBD(localResultSet.getBigDecimal(10)));
        ((HJProTradeDetail)localObject2).setExhauAmount(ManaUtil.disBD(localResultSet.getBigDecimal(11)));
        ((HJProTradeDetail)localObject2).setFellBackAmount(ManaUtil.disBD(localResultSet.getBigDecimal(12)));
        ((HJProTradeDetail)localObject2).setBuyerFellBackMoney(ManaUtil.disBD(localResultSet.getBigDecimal(13)));
        ((HJProTradeDetail)localObject2).setReturnLoanMoney(ManaUtil.disBD(localResultSet.getBigDecimal(14)));
        ((HJProTradeDetail)localObject2).setNotLoanMoney(ManaUtil.disBD(localResultSet.getBigDecimal(15)));
        ((HJProTradeDetail)localObject2).setPoundage(ManaUtil.disBD(localResultSet.getBigDecimal(16)));
        ((HJProTradeDetail)localObject2).setNotLoanInter(((HJProTradeDetail)localObject2).getNotLoanMoney().multiply(new BigDecimal(3.7332D)).divide(new BigDecimal(360), 2, 4));
        ((HJProTradeDetail)localObject2).setIncome(((HJProTradeDetail)localObject2).getActualMoney().add(((HJProTradeDetail)localObject2).getBuyerFellBackMoney()));
        ((HJProTradeDetail)localObject2).setExpenditure(((HJProTradeDetail)localObject2).getHaveLoanMoney().add(((HJProTradeDetail)localObject2).getPoundage()));
        ((HJProTradeDetail)localObject2).setDiffProfitLoss(((HJProTradeDetail)localObject2).getIncome().subtract(((HJProTradeDetail)localObject2).getExpenditure()));
        ((HJProTradeDetail)localObject2).setApproFinance(((HJProTradeDetail)localObject2).getNotLoanInter().add(((HJProTradeDetail)localObject2).getPoundage()).add(ManaUtil.disBD(localResultSet.getBigDecimal(17))));
        arrayOfArrayList[0].add(localObject2);
        localBigDecimal1 = localBigDecimal1.add(((HJProTradeDetail)localObject2).getTradeAmount());
        localBigDecimal2 = localBigDecimal2.add(((HJProTradeDetail)localObject2).getTradeMoney());
        localBigDecimal3 = localBigDecimal3.add(((HJProTradeDetail)localObject2).getLoanMoney());
        localBigDecimal4 = localBigDecimal4.add(((HJProTradeDetail)localObject2).getActualAmount());
        localBigDecimal5 = localBigDecimal5.add(((HJProTradeDetail)localObject2).getActualMoney());
        localBigDecimal6 = localBigDecimal6.add(((HJProTradeDetail)localObject2).getHaveLoanMoney());
        localBigDecimal8 = localBigDecimal8.add(((HJProTradeDetail)localObject2).getExhauAmount());
        localBigDecimal9 = localBigDecimal9.add(((HJProTradeDetail)localObject2).getFellBackAmount());
        localBigDecimal10 = localBigDecimal10.add(((HJProTradeDetail)localObject2).getBuyerFellBackMoney());
        localBigDecimal11 = localBigDecimal11.add(((HJProTradeDetail)localObject2).getReturnLoanMoney());
        localBigDecimal12 = localBigDecimal12.add(((HJProTradeDetail)localObject2).getNotLoanMoney());
        localBigDecimal13 = localBigDecimal13.add(((HJProTradeDetail)localObject2).getNotLoanInter());
        localBigDecimal15 = localBigDecimal15.add(((HJProTradeDetail)localObject2).getPoundage());
        localBigDecimal16 = localBigDecimal16.add(((HJProTradeDetail)localObject2).getIncome());
        localBigDecimal17 = localBigDecimal17.add(((HJProTradeDetail)localObject2).getExpenditure());
        localBigDecimal18 = localBigDecimal18.add(((HJProTradeDetail)localObject2).getDiffProfitLoss());
        localBigDecimal19 = localBigDecimal19.add(((HJProTradeDetail)localObject2).getApproFinance());
      }
      if (arrayOfArrayList[0].size() > 0)
      {
        localObject2 = new HJProTradeDetail();
        ((HJProTradeDetail)localObject2).setTradeAmount(localBigDecimal1);
        ((HJProTradeDetail)localObject2).setTradeMoney(localBigDecimal2);
        ((HJProTradeDetail)localObject2).setLoanMoney(localBigDecimal3);
        ((HJProTradeDetail)localObject2).setActualAmount(localBigDecimal4);
        ((HJProTradeDetail)localObject2).setActualMoney(localBigDecimal5);
        ((HJProTradeDetail)localObject2).setHaveLoanMoney(localBigDecimal6);
        ((HJProTradeDetail)localObject2).setConcelContract(localBigDecimal7);
        ((HJProTradeDetail)localObject2).setExhauAmount(localBigDecimal8);
        ((HJProTradeDetail)localObject2).setFellBackAmount(localBigDecimal9);
        ((HJProTradeDetail)localObject2).setBuyerFellBackMoney(localBigDecimal10);
        ((HJProTradeDetail)localObject2).setReturnLoanMoney(localBigDecimal11);
        ((HJProTradeDetail)localObject2).setNotLoanMoney(localBigDecimal12);
        ((HJProTradeDetail)localObject2).setNotLoanInter(localBigDecimal13);
        ((HJProTradeDetail)localObject2).setPoundage(localBigDecimal15);
        ((HJProTradeDetail)localObject2).setIncome(localBigDecimal16);
        ((HJProTradeDetail)localObject2).setExpenditure(localBigDecimal17);
        ((HJProTradeDetail)localObject2).setDiffProfitLoss(localBigDecimal18);
        ((HJProTradeDetail)localObject2).setApproFinance(localBigDecimal19);
        arrayOfArrayList[1].add(localObject2);
      }
      Object localObject2 = arrayOfArrayList;
      return localObject2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localObject1 != null) {
          localObject1.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList[] getTradeDetailVariGather(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    Object localObject1 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList[] arrayOfArrayList = new ArrayList[2];
      arrayOfArrayList[0] = new ArrayList();
      arrayOfArrayList[1] = new ArrayList();
      localStringBuffer = new StringBuffer();
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      BigDecimal localBigDecimal3 = new BigDecimal(0);
      BigDecimal localBigDecimal4 = new BigDecimal(0);
      BigDecimal localBigDecimal5 = new BigDecimal(0);
      BigDecimal localBigDecimal6 = new BigDecimal(0);
      BigDecimal localBigDecimal7 = new BigDecimal(0);
      BigDecimal localBigDecimal8 = new BigDecimal(0);
      BigDecimal localBigDecimal9 = new BigDecimal(0);
      BigDecimal localBigDecimal10 = new BigDecimal(0);
      BigDecimal localBigDecimal11 = new BigDecimal(0);
      BigDecimal localBigDecimal12 = new BigDecimal(0);
      BigDecimal localBigDecimal13 = new BigDecimal(0);
      BigDecimal localBigDecimal14 = new BigDecimal(0);
      BigDecimal localBigDecimal15 = new BigDecimal(0);
      BigDecimal localBigDecimal16 = new BigDecimal(0);
      BigDecimal localBigDecimal17 = new BigDecimal(0);
      BigDecimal localBigDecimal18 = new BigDecimal(0);
      BigDecimal localBigDecimal19 = new BigDecimal(0);
      localStringBuffer.append("select t2.str1 v1,t2.str1 v2,sum(t1.amount) v3,sum(t1.amount*t1.price) v4,");
      localStringBuffer.append("avg(t1.price) v5,avg(t2.num1) v6,sum(t1.amount*t2.num1) v7,sum(t1.actualamount) v8,");
      localStringBuffer.append("sum(t1.price*t1.actualamount) v9,sum(t2.num1*t1.actualamount) v10,sum(t1.amount");
      localStringBuffer.append("-t1.actualamount-t1.fellbackamount) v11,sum(t1.fellbackamount) v12,sum(case when ");
      localStringBuffer.append("t1.result=1 then t1.fellbackamount*t1.price else 0 end) v13,sum(case when ");
      localStringBuffer.append("t1.actualamount*t1.price<t1.actualamount*t2.num1 then  t1.actualamount*t1.price");
      localStringBuffer.append(" else t1.actualamount*t2.num1 end) v14,sum(case when t1.actualamount*t1.price");
      localStringBuffer.append("< t1.actualamount*t2.num1 then (t1.actualamount*t2.num1)-(t1.actualamount*t1.price)");
      localStringBuffer.append(" else 0 end) v15, sum(round(t1.amount*t1.price*0.0008,2)) v16,");
      localStringBuffer.append("sum(case when (t1.actualamount*t1.price)- (t1.actualamount*t2.num1)<0 then (");
      localStringBuffer.append("t1.actualamount*t2.num1)-(t1.actualamount*t1.price) else 0 end) v17 from ");
      localStringBuffer.append("hisbargain t1,commext t2,commodity t3 where t1.commodityid=t2.commid and");
      localStringBuffer.append(" t1.commodityid=t3.id  and t1.status>0 " + paramString2 + " group by t2.str1");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject2 = new HJProTradeDetail();
        ((HJProTradeDetail)localObject2).setVari(localResultSet.getString(2));
        ((HJProTradeDetail)localObject2).setTradeAmount(ManaUtil.disBD(localResultSet.getBigDecimal(3)));
        ((HJProTradeDetail)localObject2).setTradeMoney(ManaUtil.disBD(localResultSet.getBigDecimal(4)));
        ((HJProTradeDetail)localObject2).setPrice(((HJProTradeDetail)localObject2).getTradeMoney().divide(((HJProTradeDetail)localObject2).getTradeAmount(), 2, 4));
        ((HJProTradeDetail)localObject2).setLoanMoney(ManaUtil.disBD(localResultSet.getBigDecimal(7)));
        ((HJProTradeDetail)localObject2).setBuyCost(((HJProTradeDetail)localObject2).getLoanMoney().divide(((HJProTradeDetail)localObject2).getTradeAmount(), 4, 4));
        ((HJProTradeDetail)localObject2).setActualAmount(ManaUtil.disBD(localResultSet.getBigDecimal(8)));
        ((HJProTradeDetail)localObject2).setActualMoney(ManaUtil.disBD(localResultSet.getBigDecimal(9)));
        ((HJProTradeDetail)localObject2).setHaveLoanMoney(ManaUtil.disBD(localResultSet.getBigDecimal(10)));
        ((HJProTradeDetail)localObject2).setExhauAmount(ManaUtil.disBD(localResultSet.getBigDecimal(11)));
        ((HJProTradeDetail)localObject2).setFellBackAmount(ManaUtil.disBD(localResultSet.getBigDecimal(12)));
        ((HJProTradeDetail)localObject2).setBuyerFellBackMoney(ManaUtil.disBD(localResultSet.getBigDecimal(13)));
        ((HJProTradeDetail)localObject2).setReturnLoanMoney(ManaUtil.disBD(localResultSet.getBigDecimal(14)));
        ((HJProTradeDetail)localObject2).setNotLoanMoney(ManaUtil.disBD(localResultSet.getBigDecimal(15)));
        ((HJProTradeDetail)localObject2).setPoundage(ManaUtil.disBD(localResultSet.getBigDecimal(16)));
        ((HJProTradeDetail)localObject2).setNotLoanInter(((HJProTradeDetail)localObject2).getNotLoanMoney().multiply(new BigDecimal(3.7332D)).divide(new BigDecimal(360), 2, 4));
        ((HJProTradeDetail)localObject2).setIncome(((HJProTradeDetail)localObject2).getActualMoney().add(((HJProTradeDetail)localObject2).getBuyerFellBackMoney()));
        ((HJProTradeDetail)localObject2).setExpenditure(((HJProTradeDetail)localObject2).getHaveLoanMoney().add(((HJProTradeDetail)localObject2).getPoundage()));
        ((HJProTradeDetail)localObject2).setDiffProfitLoss(((HJProTradeDetail)localObject2).getIncome().subtract(((HJProTradeDetail)localObject2).getExpenditure()));
        ((HJProTradeDetail)localObject2).setApproFinance(((HJProTradeDetail)localObject2).getNotLoanInter().add(((HJProTradeDetail)localObject2).getPoundage()).add(ManaUtil.disBD(localResultSet.getBigDecimal(17))));
        arrayOfArrayList[0].add(localObject2);
        localBigDecimal1 = localBigDecimal1.add(((HJProTradeDetail)localObject2).getTradeAmount());
        localBigDecimal2 = localBigDecimal2.add(((HJProTradeDetail)localObject2).getTradeMoney());
        localBigDecimal3 = localBigDecimal3.add(((HJProTradeDetail)localObject2).getLoanMoney());
        localBigDecimal4 = localBigDecimal4.add(((HJProTradeDetail)localObject2).getActualAmount());
        localBigDecimal5 = localBigDecimal5.add(((HJProTradeDetail)localObject2).getActualMoney());
        localBigDecimal6 = localBigDecimal6.add(((HJProTradeDetail)localObject2).getHaveLoanMoney());
        localBigDecimal8 = localBigDecimal8.add(((HJProTradeDetail)localObject2).getExhauAmount());
        localBigDecimal9 = localBigDecimal9.add(((HJProTradeDetail)localObject2).getFellBackAmount());
        localBigDecimal10 = localBigDecimal10.add(((HJProTradeDetail)localObject2).getBuyerFellBackMoney());
        localBigDecimal11 = localBigDecimal11.add(((HJProTradeDetail)localObject2).getReturnLoanMoney());
        localBigDecimal12 = localBigDecimal12.add(((HJProTradeDetail)localObject2).getNotLoanMoney());
        localBigDecimal13 = localBigDecimal13.add(((HJProTradeDetail)localObject2).getNotLoanInter());
        localBigDecimal15 = localBigDecimal15.add(((HJProTradeDetail)localObject2).getPoundage());
        localBigDecimal16 = localBigDecimal16.add(((HJProTradeDetail)localObject2).getIncome());
        localBigDecimal17 = localBigDecimal17.add(((HJProTradeDetail)localObject2).getExpenditure());
        localBigDecimal18 = localBigDecimal18.add(((HJProTradeDetail)localObject2).getDiffProfitLoss());
        localBigDecimal19 = localBigDecimal19.add(((HJProTradeDetail)localObject2).getApproFinance());
      }
      if (arrayOfArrayList[0].size() > 0)
      {
        localObject2 = new HJProTradeDetail();
        ((HJProTradeDetail)localObject2).setTradeAmount(localBigDecimal1);
        ((HJProTradeDetail)localObject2).setTradeMoney(localBigDecimal2);
        ((HJProTradeDetail)localObject2).setLoanMoney(localBigDecimal3);
        ((HJProTradeDetail)localObject2).setActualAmount(localBigDecimal4);
        ((HJProTradeDetail)localObject2).setActualMoney(localBigDecimal5);
        ((HJProTradeDetail)localObject2).setHaveLoanMoney(localBigDecimal6);
        ((HJProTradeDetail)localObject2).setConcelContract(localBigDecimal7);
        ((HJProTradeDetail)localObject2).setExhauAmount(localBigDecimal8);
        ((HJProTradeDetail)localObject2).setFellBackAmount(localBigDecimal9);
        ((HJProTradeDetail)localObject2).setBuyerFellBackMoney(localBigDecimal10);
        ((HJProTradeDetail)localObject2).setReturnLoanMoney(localBigDecimal11);
        ((HJProTradeDetail)localObject2).setNotLoanMoney(localBigDecimal12);
        ((HJProTradeDetail)localObject2).setNotLoanInter(localBigDecimal13);
        ((HJProTradeDetail)localObject2).setPoundage(localBigDecimal15);
        ((HJProTradeDetail)localObject2).setIncome(localBigDecimal16);
        ((HJProTradeDetail)localObject2).setExpenditure(localBigDecimal17);
        ((HJProTradeDetail)localObject2).setDiffProfitLoss(localBigDecimal18);
        ((HJProTradeDetail)localObject2).setApproFinance(localBigDecimal19);
        arrayOfArrayList[1].add(localObject2);
      }
      Object localObject2 = arrayOfArrayList;
      return localObject2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localObject1 != null) {
          localObject1.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getNotOutedLog(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    ArrayList localArrayList = new ArrayList();
    String str1 = null;
    Object localObject1 = null;
    Object localObject2 = null;
    String str2 = null;
    HashMap localHashMap = null;
    Set localSet = null;
    Iterator localIterator = null;
    String str3 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("select t3.contractid v1,t3.tradedate v2,t3.code v3,t3.userid v4,t2.str2 v5,");
      localStringBuffer.append("t2.str3 v6,t3.amount v7,t3.price v8,(select t5.name from v_tradeuserext t5 ");
      localStringBuffer.append("where t5.usercode=t3.userid) v9,(select sum(amount) from v_outlog where contractid=");
      localStringBuffer.append("t3.contractid and finished=2) v10 from v_commodity t1,v_commext t2,v_hisbargain t3 ");
      localStringBuffer.append("where t1.id=t2.commid and t1.id=t3.commodityid and (select nvl(sum(t4.amount),0) ");
      localStringBuffer.append("from v_outlog t4 where t4.contractid=t3.contractid and t4.finished=2)<t3.amount");
      localStringBuffer.append(" " + paramString2 + " order by t3.contractid asc ");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet1.next())
      {
        localObject3 = new OutLogDetail();
        str1 = null;
        localObject1 = null;
        localObject2 = null;
        str2 = "";
        str3 = "";
        ((OutLogDetail)localObject3).setContractId(localResultSet1.getString(1));
        ((OutLogDetail)localObject3).setTradeDate(localResultSet1.getString(2).substring(0, 10));
        ((OutLogDetail)localObject3).setCode(localResultSet1.getString(3));
        ((OutLogDetail)localObject3).setAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(10)));
        ((OutLogDetail)localObject3).setUserCode(localResultSet1.getString(4));
        ((OutLogDetail)localObject3).setSellerName(localResultSet1.getString(5));
        ((OutLogDetail)localObject3).setTradeAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(7)));
        ((OutLogDetail)localObject3).setPrice(ManaUtil.disBD(localResultSet1.getBigDecimal(8)));
        ((OutLogDetail)localObject3).setBuyerName(localResultSet1.getString(9));
        str1 = localResultSet1.getString(6);
        if (str1 != null)
        {
          localHashMap = ManaUtil.addPlaceGrade(str1);
          localSet = localHashMap.entrySet();
          localIterator = localSet.iterator();
          while (localIterator.hasNext())
          {
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            str2 = str2 + localEntry.getKey().toString() + "/";
          }
        }
        ((OutLogDetail)localObject3).setWareHouse(str2);
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select outid from v_outlog where contractid=" + ((OutLogDetail)localObject3).getContractId() + " and");
        localStringBuffer.append(" finished=2 ");
        localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
        while (localResultSet2.next()) {
          str3 = str3 + localResultSet2.getString(1) + "/";
        }
        localResultSet2.close();
        localDBBean.closeStmt();
        ((OutLogDetail)localObject3).setOutId(str3);
        localArrayList.add(localObject3);
      }
      localResultSet1.close();
      localDBBean.close();
      localObject3 = localArrayList;
      return localObject3;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      Object localObject3 = null;
      return localObject3;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList[] getMarketDetailGather(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet = null;
    Object localObject1 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList[] arrayOfArrayList = new ArrayList[2];
      arrayOfArrayList[0] = new ArrayList();
      arrayOfArrayList[1] = new ArrayList();
      localStringBuffer = new StringBuffer();
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      BigDecimal localBigDecimal3 = new BigDecimal(0);
      BigDecimal localBigDecimal4 = new BigDecimal(0);
      BigDecimal localBigDecimal5 = new BigDecimal(0);
      BigDecimal localBigDecimal6 = new BigDecimal(0);
      BigDecimal localBigDecimal7 = new BigDecimal(0);
      BigDecimal localBigDecimal8 = new BigDecimal(0);
      BigDecimal localBigDecimal9 = new BigDecimal(0);
      BigDecimal localBigDecimal10 = new BigDecimal(0);
      BigDecimal localBigDecimal11 = new BigDecimal(0);
      BigDecimal localBigDecimal12 = new BigDecimal(0);
      BigDecimal localBigDecimal13 = new BigDecimal(0);
      BigDecimal localBigDecimal14 = new BigDecimal(0);
      BigDecimal localBigDecimal15 = new BigDecimal(0);
      BigDecimal localBigDecimal16 = new BigDecimal(0);
      BigDecimal localBigDecimal17 = new BigDecimal(0);
      BigDecimal localBigDecimal18 = new BigDecimal(0);
      BigDecimal localBigDecimal19 = new BigDecimal(0);
      localStringBuffer.append("select (select name from dictable where type=4 and value=t2.str7) v1,t2.str1 v2,");
      localStringBuffer.append("sum(t1.amount) v3,sum(t1.amount*t1.price) v4,avg(t1.price) v5,avg(t2.num1) v6,");
      localStringBuffer.append("sum(t1.amount*t2.num1) v7,sum(t1.actualamount) v8,sum(t1.price*t1.actualamount)");
      localStringBuffer.append(" v9,sum(t2.num1*t1.actualamount) v10,sum(t1.amount-t1.actualamount-t1.fellbackamount)");
      localStringBuffer.append(" v11,sum(t1.fellbackamount) v12,sum(case when t1.result=1 then t1.fellbackamount*");
      localStringBuffer.append("t1.price else 0 end) v13,sum(case when t1.actualamount*t1.price<t1.actualamount*t2.num1");
      localStringBuffer.append(" then  t1.actualamount*t1.price else t1.actualamount*t2.num1 end) v14,sum(case when");
      localStringBuffer.append(" t1.actualamount*t1.price< t1.actualamount*t2.num1 then (t1.actualamount*t2.num1)-");
      localStringBuffer.append("(t1.actualamount*t1.price) else 0 end) v15, sum(round(t1.amount*t1.price*0.0008,2)) v16,");
      localStringBuffer.append("sum(case when (t1.actualamount*t1.price)- (t1.actualamount*t2.num1)<0 then (");
      localStringBuffer.append("t1.actualamount*t2.num1)-(t1.actualamount*t1.price) else 0 end) v17 from ");
      localStringBuffer.append("hisbargain t1,commext t2,commodity t3 where t1.commodityid=t2.commid and");
      localStringBuffer.append(" t1.commodityid=t3.id  and t1.status>0 " + paramString2 + " group by t2.str7,t2.str1");
      localResultSet = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet.next())
      {
        localObject2 = new HJProTradeDetail();
        ((HJProTradeDetail)localObject2).setMarketName(localResultSet.getString(1));
        ((HJProTradeDetail)localObject2).setVari(localResultSet.getString(2));
        ((HJProTradeDetail)localObject2).setTradeAmount(ManaUtil.disBD(localResultSet.getBigDecimal(3)));
        ((HJProTradeDetail)localObject2).setTradeMoney(ManaUtil.disBD(localResultSet.getBigDecimal(4)));
        ((HJProTradeDetail)localObject2).setPrice(((HJProTradeDetail)localObject2).getTradeMoney().divide(((HJProTradeDetail)localObject2).getTradeAmount(), 2, 4));
        ((HJProTradeDetail)localObject2).setLoanMoney(ManaUtil.disBD(localResultSet.getBigDecimal(7)));
        ((HJProTradeDetail)localObject2).setBuyCost(((HJProTradeDetail)localObject2).getLoanMoney().divide(((HJProTradeDetail)localObject2).getTradeAmount(), 4, 4));
        ((HJProTradeDetail)localObject2).setActualAmount(ManaUtil.disBD(localResultSet.getBigDecimal(8)));
        ((HJProTradeDetail)localObject2).setActualMoney(ManaUtil.disBD(localResultSet.getBigDecimal(9)));
        ((HJProTradeDetail)localObject2).setHaveLoanMoney(ManaUtil.disBD(localResultSet.getBigDecimal(10)));
        ((HJProTradeDetail)localObject2).setExhauAmount(ManaUtil.disBD(localResultSet.getBigDecimal(11)));
        ((HJProTradeDetail)localObject2).setFellBackAmount(ManaUtil.disBD(localResultSet.getBigDecimal(12)));
        ((HJProTradeDetail)localObject2).setBuyerFellBackMoney(ManaUtil.disBD(localResultSet.getBigDecimal(13)));
        ((HJProTradeDetail)localObject2).setReturnLoanMoney(ManaUtil.disBD(localResultSet.getBigDecimal(14)));
        ((HJProTradeDetail)localObject2).setNotLoanMoney(ManaUtil.disBD(localResultSet.getBigDecimal(15)));
        ((HJProTradeDetail)localObject2).setPoundage(ManaUtil.disBD(localResultSet.getBigDecimal(16)));
        ((HJProTradeDetail)localObject2).setNotLoanInter(((HJProTradeDetail)localObject2).getNotLoanMoney().multiply(new BigDecimal(3.7332D)).divide(new BigDecimal(360), 2, 4));
        ((HJProTradeDetail)localObject2).setIncome(((HJProTradeDetail)localObject2).getActualMoney().add(((HJProTradeDetail)localObject2).getBuyerFellBackMoney()));
        ((HJProTradeDetail)localObject2).setExpenditure(((HJProTradeDetail)localObject2).getHaveLoanMoney().add(((HJProTradeDetail)localObject2).getPoundage()));
        ((HJProTradeDetail)localObject2).setDiffProfitLoss(((HJProTradeDetail)localObject2).getIncome().subtract(((HJProTradeDetail)localObject2).getExpenditure()));
        ((HJProTradeDetail)localObject2).setApproFinance(((HJProTradeDetail)localObject2).getNotLoanInter().add(((HJProTradeDetail)localObject2).getPoundage()).add(ManaUtil.disBD(localResultSet.getBigDecimal(17))));
        arrayOfArrayList[0].add(localObject2);
        localBigDecimal1 = localBigDecimal1.add(((HJProTradeDetail)localObject2).getTradeAmount());
        localBigDecimal2 = localBigDecimal2.add(((HJProTradeDetail)localObject2).getTradeMoney());
        localBigDecimal3 = localBigDecimal3.add(((HJProTradeDetail)localObject2).getLoanMoney());
        localBigDecimal4 = localBigDecimal4.add(((HJProTradeDetail)localObject2).getActualAmount());
        localBigDecimal5 = localBigDecimal5.add(((HJProTradeDetail)localObject2).getActualMoney());
        localBigDecimal6 = localBigDecimal6.add(((HJProTradeDetail)localObject2).getHaveLoanMoney());
        localBigDecimal8 = localBigDecimal8.add(((HJProTradeDetail)localObject2).getExhauAmount());
        localBigDecimal9 = localBigDecimal9.add(((HJProTradeDetail)localObject2).getFellBackAmount());
        localBigDecimal10 = localBigDecimal10.add(((HJProTradeDetail)localObject2).getBuyerFellBackMoney());
        localBigDecimal11 = localBigDecimal11.add(((HJProTradeDetail)localObject2).getReturnLoanMoney());
        localBigDecimal12 = localBigDecimal12.add(((HJProTradeDetail)localObject2).getNotLoanMoney());
        localBigDecimal13 = localBigDecimal13.add(((HJProTradeDetail)localObject2).getNotLoanInter());
        localBigDecimal15 = localBigDecimal15.add(((HJProTradeDetail)localObject2).getPoundage());
        localBigDecimal16 = localBigDecimal16.add(((HJProTradeDetail)localObject2).getIncome());
        localBigDecimal17 = localBigDecimal17.add(((HJProTradeDetail)localObject2).getExpenditure());
        localBigDecimal18 = localBigDecimal18.add(((HJProTradeDetail)localObject2).getDiffProfitLoss());
        localBigDecimal19 = localBigDecimal19.add(((HJProTradeDetail)localObject2).getApproFinance());
      }
      if (arrayOfArrayList[0].size() > 0)
      {
        localObject2 = new HJProTradeDetail();
        ((HJProTradeDetail)localObject2).setTradeAmount(localBigDecimal1);
        ((HJProTradeDetail)localObject2).setTradeMoney(localBigDecimal2);
        ((HJProTradeDetail)localObject2).setLoanMoney(localBigDecimal3);
        ((HJProTradeDetail)localObject2).setActualAmount(localBigDecimal4);
        ((HJProTradeDetail)localObject2).setActualMoney(localBigDecimal5);
        ((HJProTradeDetail)localObject2).setHaveLoanMoney(localBigDecimal6);
        ((HJProTradeDetail)localObject2).setConcelContract(localBigDecimal7);
        ((HJProTradeDetail)localObject2).setExhauAmount(localBigDecimal8);
        ((HJProTradeDetail)localObject2).setFellBackAmount(localBigDecimal9);
        ((HJProTradeDetail)localObject2).setBuyerFellBackMoney(localBigDecimal10);
        ((HJProTradeDetail)localObject2).setReturnLoanMoney(localBigDecimal11);
        ((HJProTradeDetail)localObject2).setNotLoanMoney(localBigDecimal12);
        ((HJProTradeDetail)localObject2).setNotLoanInter(localBigDecimal13);
        ((HJProTradeDetail)localObject2).setPoundage(localBigDecimal15);
        ((HJProTradeDetail)localObject2).setIncome(localBigDecimal16);
        ((HJProTradeDetail)localObject2).setExpenditure(localBigDecimal17);
        ((HJProTradeDetail)localObject2).setDiffProfitLoss(localBigDecimal18);
        ((HJProTradeDetail)localObject2).setApproFinance(localBigDecimal19);
        arrayOfArrayList[1].add(localObject2);
      }
      Object localObject2 = arrayOfArrayList;
      return localObject2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer = null;
      return localStringBuffer;
    }
    finally
    {
      try
      {
        if (localResultSet != null) {
          localResultSet.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localObject1 != null) {
          localObject1.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList[] getMarProTradeDetail(String paramString1, String paramString2)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList[] arrayOfArrayList1 = new ArrayList[2];
      arrayOfArrayList1[0] = new ArrayList();
      arrayOfArrayList1[1] = new ArrayList();
      int i = 0;
      Object localObject1 = "";
      Object localObject2 = "";
      String str1 = "";
      Object localObject3 = "";
      String str2 = "";
      String str3 = null;
      int j = 0;
      StringBuffer localStringBuffer = new StringBuffer();
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      BigDecimal localBigDecimal3 = new BigDecimal(0);
      BigDecimal localBigDecimal4 = new BigDecimal(0);
      BigDecimal localBigDecimal5 = new BigDecimal(0);
      BigDecimal localBigDecimal6 = new BigDecimal(0);
      BigDecimal localBigDecimal7 = new BigDecimal(0);
      BigDecimal localBigDecimal8 = new BigDecimal(0);
      BigDecimal localBigDecimal9 = new BigDecimal(0);
      BigDecimal localBigDecimal10 = new BigDecimal(0);
      BigDecimal localBigDecimal11 = new BigDecimal(0);
      BigDecimal localBigDecimal12 = new BigDecimal(0);
      BigDecimal localBigDecimal13 = new BigDecimal(0);
      BigDecimal localBigDecimal14 = new BigDecimal(0);
      BigDecimal localBigDecimal15 = new BigDecimal(0);
      BigDecimal localBigDecimal16 = new BigDecimal(0);
      BigDecimal localBigDecimal17 = new BigDecimal(0);
      BigDecimal localBigDecimal18 = new BigDecimal(0);
      BigDecimal localBigDecimal19 = new BigDecimal(0);
      BigDecimal localBigDecimal20 = new BigDecimal(0);
      BigDecimal localBigDecimal21 = new BigDecimal(0);
      BigDecimal localBigDecimal22 = new BigDecimal(0);
      BigDecimal localBigDecimal23 = new BigDecimal(0);
      BigDecimal localBigDecimal24 = new BigDecimal(0);
      BigDecimal localBigDecimal25 = new BigDecimal(0);
      BigDecimal localBigDecimal26 = new BigDecimal(0);
      BigDecimal localBigDecimal27 = new BigDecimal(0);
      BigDecimal localBigDecimal28 = new BigDecimal(0);
      BigDecimal localBigDecimal29 = new BigDecimal(0);
      BigDecimal localBigDecimal30 = new BigDecimal(0);
      BigDecimal localBigDecimal31 = new BigDecimal(0);
      BigDecimal localBigDecimal32 = new BigDecimal(0);
      BigDecimal localBigDecimal33 = new BigDecimal(0);
      BigDecimal localBigDecimal34 = new BigDecimal(0);
      BigDecimal localBigDecimal35 = new BigDecimal(0);
      BigDecimal localBigDecimal36 = new BigDecimal(0);
      BigDecimal localBigDecimal37 = new BigDecimal(0);
      BigDecimal localBigDecimal38 = new BigDecimal(0);
      ArrayList localArrayList = new ArrayList();
      localStringBuffer.append("select count(*) v1 from (select (select name from dictable where type=4 and value=");
      localStringBuffer.append("t2.str7) v1 from hisbargain t1,commext t2,commodity t3 where t1.commodityid=t2.commid");
      localStringBuffer.append(" and t1.commodityid=t3.id  and t1.status>0 " + paramString2 + "  group by t2.str7,t2.str6,t2.str1)");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      if (localResultSet1.next()) {
        i = localResultSet1.getInt(1);
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("select (select name from dictable where type=4 and value=t2.str7) v1,t2.str1 v2,");
      localStringBuffer.append("sum(t1.amount) v3,sum(t1.amount*t1.price) v4,avg(t1.price) v5,avg(t2.num1) v6,");
      localStringBuffer.append("sum(t1.amount*t2.num1) v7,sum(t1.actualamount) v8,sum(t1.price*t1.actualamount)");
      localStringBuffer.append(" v9,sum(t2.num1*t1.actualamount) v10,sum(t1.amount-t1.actualamount-t1.fellbackamount)");
      localStringBuffer.append(" v11,sum(t1.fellbackamount) v12,sum(case when t1.result=1 then t1.fellbackamount*");
      localStringBuffer.append("t1.price else 0 end) v13,sum(case when t1.actualamount*t1.price<t1.actualamount*t2.num1");
      localStringBuffer.append(" then  t1.actualamount*t1.price else t1.actualamount*t2.num1 end) v14,sum(case when");
      localStringBuffer.append(" t1.actualamount*t1.price< t1.actualamount*t2.num1 then (t1.actualamount*t2.num1)-");
      localStringBuffer.append("(t1.actualamount*t1.price) else 0 end) v15, sum(round(t1.amount*t1.price*0.0008,2)) v16,");
      localStringBuffer.append("sum(case when (t1.actualamount*t1.price)- (t1.actualamount*t2.num1)<0 then (");
      localStringBuffer.append("t1.actualamount*t2.num1)-(t1.actualamount*t1.price) else 0 end) v17,(select name from");
      localStringBuffer.append(" dictable  where type=2 and value=t2.str6) v18,t2.str6 v19,t2.str7 v20 from ");
      localStringBuffer.append("hisbargain t1,commext t2,commodity t3 where t1.commodityid=t2.commid and");
      localStringBuffer.append(" t1.commodityid=t3.id  and t1.status>0  " + paramString2 + "  group by t2.str7,t2.str6,");
      localStringBuffer.append("t2.str1 order by t2.str7 asc");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet1.next())
      {
        localObject4 = new TradeDetail();
        str3 = "";
        ((TradeDetail)localObject4).setProName(localResultSet1.getString(18));
        ((TradeDetail)localObject4).setVari(localResultSet1.getString(2));
        ((TradeDetail)localObject4).setTradeAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(3)));
        ((TradeDetail)localObject4).setTradeMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(4)));
        ((TradeDetail)localObject4).setPrice(((TradeDetail)localObject4).getTradeMoney().divide(((TradeDetail)localObject4).getTradeAmount(), 2, 4));
        ((TradeDetail)localObject4).setLoanMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(7)));
        ((TradeDetail)localObject4).setBuyCost(((TradeDetail)localObject4).getLoanMoney().divide(((TradeDetail)localObject4).getTradeAmount(), 4, 4));
        ((TradeDetail)localObject4).setActualAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(8)));
        ((TradeDetail)localObject4).setActualMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(9)));
        ((TradeDetail)localObject4).setHaveLoanMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(10)).setScale(2, 4));
        ((TradeDetail)localObject4).setExhauAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(11)));
        ((TradeDetail)localObject4).setFellBackAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(12)));
        ((TradeDetail)localObject4).setBuyerFellBackMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(13)));
        ((TradeDetail)localObject4).setReturnLoanMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(14)));
        ((TradeDetail)localObject4).setNotLoanMoney(ManaUtil.disBD(localResultSet1.getBigDecimal(15)));
        ((TradeDetail)localObject4).setPoundage(ManaUtil.disBD(localResultSet1.getBigDecimal(16)));
        ((TradeDetail)localObject4).setNotLoanInter(((TradeDetail)localObject4).getNotLoanMoney().multiply(new BigDecimal(3.7332D)).divide(new BigDecimal(360), 2, 4));
        ((TradeDetail)localObject4).setIncome(((TradeDetail)localObject4).getActualMoney().add(((TradeDetail)localObject4).getBuyerFellBackMoney()));
        ((TradeDetail)localObject4).setExpenditure(((TradeDetail)localObject4).getHaveLoanMoney().add(((TradeDetail)localObject4).getPoundage()));
        ((TradeDetail)localObject4).setDiffProfitLoss(((TradeDetail)localObject4).getIncome().subtract(((TradeDetail)localObject4).getExpenditure()));
        ((TradeDetail)localObject4).setApproFinance(((TradeDetail)localObject4).getNotLoanInter().add(((TradeDetail)localObject4).getPoundage()).add(ManaUtil.disBD(localResultSet1.getBigDecimal(17))));
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select distinct t2.str5 v1 from hisbargain t1,commext t2,commodity t3 where");
        localStringBuffer.append(" t1.commodityid=t2.commid and t1.commodityid=t3.id  and t1.status>0");
        localStringBuffer.append(" and t2.str6=" + localResultSet1.getInt(19) + " and t2.str7=" + localResultSet1.getInt(20) + " and t2.str1=");
        localStringBuffer.append("'" + ((TradeDetail)localObject4).getVari() + "'" + paramString2 + "");
        localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
        while (localResultSet2.next()) {
          str3 = str3 + localResultSet2.getString(1) + "/";
        }
        localResultSet2.close();
        localDBBean.closeStmt();
        if (str3 != null) {
          str3 = str3.substring(0, str3.length() - 1);
        }
        ((TradeDetail)localObject4).setProcureTime(str3);
        localBigDecimal20 = localBigDecimal20.add(((TradeDetail)localObject4).getTradeAmount());
        localBigDecimal21 = localBigDecimal21.add(((TradeDetail)localObject4).getTradeMoney());
        localBigDecimal22 = localBigDecimal22.add(((TradeDetail)localObject4).getLoanMoney());
        localBigDecimal23 = localBigDecimal23.add(((TradeDetail)localObject4).getActualAmount());
        localBigDecimal24 = localBigDecimal24.add(((TradeDetail)localObject4).getActualMoney());
        localBigDecimal25 = localBigDecimal25.add(((TradeDetail)localObject4).getHaveLoanMoney());
        localBigDecimal27 = localBigDecimal27.add(((TradeDetail)localObject4).getExhauAmount());
        localBigDecimal28 = localBigDecimal28.add(((TradeDetail)localObject4).getFellBackAmount());
        localBigDecimal29 = localBigDecimal29.add(((TradeDetail)localObject4).getBuyerFellBackMoney());
        localBigDecimal30 = localBigDecimal30.add(((TradeDetail)localObject4).getReturnLoanMoney());
        localBigDecimal31 = localBigDecimal31.add(((TradeDetail)localObject4).getNotLoanMoney());
        localBigDecimal32 = localBigDecimal32.add(((TradeDetail)localObject4).getNotLoanInter());
        localBigDecimal34 = localBigDecimal34.add(((TradeDetail)localObject4).getPoundage());
        localBigDecimal35 = localBigDecimal35.add(((TradeDetail)localObject4).getIncome());
        localBigDecimal36 = localBigDecimal36.add(((TradeDetail)localObject4).getExpenditure());
        localBigDecimal37 = localBigDecimal37.add(((TradeDetail)localObject4).getDiffProfitLoss());
        localBigDecimal38 = localBigDecimal38.add(((TradeDetail)localObject4).getApproFinance());
        str1 = localResultSet1.getString(20);
        str2 = localResultSet1.getString(1);
        localObject2 = str1;
        if (j == 0)
        {
          localObject1 = str1;
          localObject3 = str2;
          localBigDecimal1 = localBigDecimal1.add(((TradeDetail)localObject4).getTradeAmount());
          localBigDecimal2 = localBigDecimal2.add(((TradeDetail)localObject4).getTradeMoney());
          localBigDecimal3 = localBigDecimal3.add(((TradeDetail)localObject4).getLoanMoney());
          localBigDecimal4 = localBigDecimal4.add(((TradeDetail)localObject4).getActualAmount());
          localBigDecimal5 = localBigDecimal5.add(((TradeDetail)localObject4).getActualMoney());
          localBigDecimal6 = localBigDecimal6.add(((TradeDetail)localObject4).getHaveLoanMoney());
          localBigDecimal7 = localBigDecimal7.add(ManaUtil.disBD(((TradeDetail)localObject4).getConcelContract()));
          localBigDecimal8 = localBigDecimal8.add(((TradeDetail)localObject4).getExhauAmount());
          localBigDecimal9 = localBigDecimal9.add(((TradeDetail)localObject4).getFellBackAmount());
          localBigDecimal10 = localBigDecimal10.add(((TradeDetail)localObject4).getBuyerFellBackMoney());
          localBigDecimal11 = localBigDecimal11.add(((TradeDetail)localObject4).getReturnLoanMoney());
          localBigDecimal12 = localBigDecimal12.add(((TradeDetail)localObject4).getNotLoanMoney());
          localBigDecimal13 = localBigDecimal13.add(((TradeDetail)localObject4).getNotLoanInter());
          localBigDecimal14 = localBigDecimal14.add(ManaUtil.disBD(((TradeDetail)localObject4).getDelayLoanInter()));
          localBigDecimal15 = localBigDecimal15.add(((TradeDetail)localObject4).getPoundage());
          localBigDecimal16 = localBigDecimal16.add(((TradeDetail)localObject4).getIncome());
          localBigDecimal17 = localBigDecimal17.add(((TradeDetail)localObject4).getExpenditure());
          localBigDecimal18 = localBigDecimal18.add(((TradeDetail)localObject4).getDiffProfitLoss());
          localBigDecimal19 = localBigDecimal19.add(((TradeDetail)localObject4).getApproFinance());
        }
        HJProTradeDetail localHJProTradeDetail;
        if (!((String)localObject2).equals(localObject1))
        {
          localHJProTradeDetail = new HJProTradeDetail();
          localHJProTradeDetail.setMarketName((String)localObject3 + "市场");
          localHJProTradeDetail.setTradeAmount(localBigDecimal1);
          localHJProTradeDetail.setTradeMoney(localBigDecimal2);
          localHJProTradeDetail.setPrice(localBigDecimal2.divide(localBigDecimal1, 2, 4));
          localHJProTradeDetail.setBuyCost(localBigDecimal3.divide(localBigDecimal1, 4, 4));
          localHJProTradeDetail.setLoanMoney(localBigDecimal3);
          localHJProTradeDetail.setActualAmount(localBigDecimal4);
          localHJProTradeDetail.setActualMoney(localBigDecimal5);
          localHJProTradeDetail.setHaveLoanMoney(localBigDecimal6);
          localHJProTradeDetail.setConcelContract(localBigDecimal7);
          localHJProTradeDetail.setExhauAmount(localBigDecimal8);
          localHJProTradeDetail.setFellBackAmount(localBigDecimal9);
          localHJProTradeDetail.setBuyerFellBackMoney(localBigDecimal10);
          localHJProTradeDetail.setReturnLoanMoney(localBigDecimal11);
          localHJProTradeDetail.setNotLoanMoney(localBigDecimal12);
          localHJProTradeDetail.setNotLoanInter(localBigDecimal13);
          localHJProTradeDetail.setPoundage(localBigDecimal15);
          localHJProTradeDetail.setIncome(localBigDecimal16);
          localHJProTradeDetail.setExpenditure(localBigDecimal17);
          localHJProTradeDetail.setDiffProfitLoss(localBigDecimal18);
          localHJProTradeDetail.setApproFinance(localBigDecimal19);
          localHJProTradeDetail.setTradeDetail(localArrayList);
          arrayOfArrayList1[0].add(localHJProTradeDetail);
          localArrayList = new ArrayList();
          localObject1 = localObject2;
          localObject3 = str2;
          localBigDecimal1 = ((TradeDetail)localObject4).getTradeAmount();
          localBigDecimal2 = ((TradeDetail)localObject4).getTradeMoney();
          localBigDecimal3 = ((TradeDetail)localObject4).getLoanMoney();
          localBigDecimal4 = ((TradeDetail)localObject4).getActualAmount();
          localBigDecimal5 = ((TradeDetail)localObject4).getActualMoney();
          localBigDecimal6 = ((TradeDetail)localObject4).getHaveLoanMoney();
          localBigDecimal7 = ManaUtil.disBD(((TradeDetail)localObject4).getConcelContract());
          localBigDecimal8 = ((TradeDetail)localObject4).getExhauAmount();
          localBigDecimal9 = ((TradeDetail)localObject4).getFellBackAmount();
          localBigDecimal10 = ((TradeDetail)localObject4).getBuyerFellBackMoney();
          localBigDecimal11 = ((TradeDetail)localObject4).getReturnLoanMoney();
          localBigDecimal12 = ((TradeDetail)localObject4).getNotLoanMoney();
          localBigDecimal13 = ((TradeDetail)localObject4).getNotLoanInter();
          localBigDecimal14 = ManaUtil.disBD(((TradeDetail)localObject4).getDelayLoanInter());
          localBigDecimal15 = ((TradeDetail)localObject4).getPoundage();
          localBigDecimal16 = ((TradeDetail)localObject4).getIncome();
          localBigDecimal17 = ((TradeDetail)localObject4).getExpenditure();
          localBigDecimal18 = ((TradeDetail)localObject4).getDiffProfitLoss();
          localBigDecimal19 = ((TradeDetail)localObject4).getApproFinance();
        }
        else if (j > 0)
        {
          localBigDecimal1 = localBigDecimal1.add(((TradeDetail)localObject4).getTradeAmount());
          localBigDecimal2 = localBigDecimal2.add(((TradeDetail)localObject4).getTradeMoney());
          localBigDecimal3 = localBigDecimal3.add(((TradeDetail)localObject4).getLoanMoney());
          localBigDecimal4 = localBigDecimal4.add(((TradeDetail)localObject4).getActualAmount());
          localBigDecimal5 = localBigDecimal5.add(((TradeDetail)localObject4).getActualMoney());
          localBigDecimal6 = localBigDecimal6.add(((TradeDetail)localObject4).getHaveLoanMoney());
          localBigDecimal7 = localBigDecimal7.add(ManaUtil.disBD(((TradeDetail)localObject4).getConcelContract()));
          localBigDecimal8 = localBigDecimal8.add(((TradeDetail)localObject4).getExhauAmount());
          localBigDecimal9 = localBigDecimal9.add(((TradeDetail)localObject4).getFellBackAmount());
          localBigDecimal10 = localBigDecimal10.add(((TradeDetail)localObject4).getBuyerFellBackMoney());
          localBigDecimal11 = localBigDecimal11.add(((TradeDetail)localObject4).getReturnLoanMoney());
          localBigDecimal12 = localBigDecimal12.add(((TradeDetail)localObject4).getNotLoanMoney());
          localBigDecimal13 = localBigDecimal13.add(((TradeDetail)localObject4).getNotLoanInter());
          localBigDecimal14 = localBigDecimal14.add(ManaUtil.disBD(((TradeDetail)localObject4).getDelayLoanInter()));
          localBigDecimal15 = localBigDecimal15.add(((TradeDetail)localObject4).getPoundage());
          localBigDecimal16 = localBigDecimal16.add(((TradeDetail)localObject4).getIncome());
          localBigDecimal17 = localBigDecimal17.add(((TradeDetail)localObject4).getExpenditure());
          localBigDecimal18 = localBigDecimal18.add(((TradeDetail)localObject4).getDiffProfitLoss());
          localBigDecimal19 = localBigDecimal19.add(((TradeDetail)localObject4).getApproFinance());
        }
        localArrayList.add(localObject4);
        if (j == i - 1)
        {
          localHJProTradeDetail = new HJProTradeDetail();
          localHJProTradeDetail.setMarketName((String)localObject3 + "市场");
          localHJProTradeDetail.setTradeAmount(localBigDecimal1);
          localHJProTradeDetail.setTradeMoney(localBigDecimal2);
          localHJProTradeDetail.setPrice(localBigDecimal2.divide(localBigDecimal1, 2, 4));
          localHJProTradeDetail.setBuyCost(localBigDecimal3.divide(localBigDecimal1, 4, 4));
          localHJProTradeDetail.setLoanMoney(localBigDecimal3);
          localHJProTradeDetail.setActualAmount(localBigDecimal4);
          localHJProTradeDetail.setActualMoney(localBigDecimal5);
          localHJProTradeDetail.setHaveLoanMoney(localBigDecimal6);
          localHJProTradeDetail.setConcelContract(localBigDecimal7);
          localHJProTradeDetail.setExhauAmount(localBigDecimal8);
          localHJProTradeDetail.setFellBackAmount(localBigDecimal9);
          localHJProTradeDetail.setBuyerFellBackMoney(localBigDecimal10);
          localHJProTradeDetail.setReturnLoanMoney(localBigDecimal11);
          localHJProTradeDetail.setNotLoanMoney(localBigDecimal12);
          localHJProTradeDetail.setNotLoanInter(localBigDecimal13);
          localHJProTradeDetail.setPoundage(localBigDecimal15);
          localHJProTradeDetail.setIncome(localBigDecimal16);
          localHJProTradeDetail.setExpenditure(localBigDecimal17);
          localHJProTradeDetail.setDiffProfitLoss(localBigDecimal18);
          localHJProTradeDetail.setApproFinance(localBigDecimal19);
          localHJProTradeDetail.setTradeDetail(localArrayList);
          arrayOfArrayList1[0].add(localHJProTradeDetail);
        }
        j++;
      }
      if (arrayOfArrayList1[0].size() > 0)
      {
        localObject4 = new HJProTradeDetail();
        ((HJProTradeDetail)localObject4).setTradeAmount(localBigDecimal20);
        ((HJProTradeDetail)localObject4).setTradeMoney(localBigDecimal21);
        ((HJProTradeDetail)localObject4).setLoanMoney(localBigDecimal22);
        ((HJProTradeDetail)localObject4).setActualAmount(localBigDecimal23);
        ((HJProTradeDetail)localObject4).setActualMoney(localBigDecimal24);
        ((HJProTradeDetail)localObject4).setHaveLoanMoney(localBigDecimal25);
        ((HJProTradeDetail)localObject4).setConcelContract(localBigDecimal26);
        ((HJProTradeDetail)localObject4).setExhauAmount(localBigDecimal27);
        ((HJProTradeDetail)localObject4).setFellBackAmount(localBigDecimal28);
        ((HJProTradeDetail)localObject4).setBuyerFellBackMoney(localBigDecimal29);
        ((HJProTradeDetail)localObject4).setReturnLoanMoney(localBigDecimal30);
        ((HJProTradeDetail)localObject4).setNotLoanMoney(localBigDecimal31);
        ((HJProTradeDetail)localObject4).setNotLoanInter(localBigDecimal32);
        ((HJProTradeDetail)localObject4).setPoundage(localBigDecimal34);
        ((HJProTradeDetail)localObject4).setIncome(localBigDecimal35);
        ((HJProTradeDetail)localObject4).setExpenditure(localBigDecimal36);
        ((HJProTradeDetail)localObject4).setDiffProfitLoss(localBigDecimal37);
        ((HJProTradeDetail)localObject4).setApproFinance(localBigDecimal38);
        arrayOfArrayList1[1].add(localObject4);
      }
      Object localObject4 = arrayOfArrayList1;
      return localObject4;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      ArrayList[] arrayOfArrayList2 = null;
      return arrayOfArrayList2;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList[] getSaleTradeResult(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    ResultSet localResultSet3 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList[] arrayOfArrayList1 = new ArrayList[3];
      arrayOfArrayList1[0] = new ArrayList();
      arrayOfArrayList1[1] = new ArrayList();
      arrayOfArrayList1[2] = new ArrayList();
      localStringBuffer1 = new StringBuffer();
      int i = 0;
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = null;
      Object localObject1 = null;
      Object localObject2 = null;
      Object localObject3 = null;
      localStringBuffer1.append("select count(distinct t2.str1) as v from commodity t1,commext t2," + paramString5 + " t3 where t1.id=");
      localStringBuffer1.append("t2.commid and t3.commodityid=t1.id" + paramString2);
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      if (localResultSet1.next()) {
        i = localResultSet1.getInt("v");
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      Object[][] arrayOfObject = new Object[i][8];
      localStringBuffer1 = new StringBuffer();
      localStringBuffer1.append("select distinct t2.str1 from commodity t1,commext t2," + paramString5 + " t3 where t1.id=");
      localStringBuffer1.append("t2.commid and t3.commodityid=t1.id" + paramString2);
      int j = 0;
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      while (localResultSet1.next())
      {
        localHashMap1.put(localResultSet1.getString("str1"), new Integer(j));
        arrayOfObject[j][0] = localResultSet1.getString("str1");
        arrayOfObject[j][1] = "0";
        arrayOfObject[j][2] = "0";
        arrayOfObject[j][3] = "0";
        arrayOfObject[j][4] = "0";
        arrayOfObject[j][5] = "0";
        arrayOfObject[j][6] = "";
        arrayOfObject[j][7] = null;
        j++;
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      localStringBuffer1 = new StringBuffer();
      localStringBuffer1.append("select value,name from dictable where type=" + paramString4 + "");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      BigDecimal localBigDecimal3 = new BigDecimal(0);
      BigDecimal localBigDecimal4 = new BigDecimal(0);
      BigDecimal localBigDecimal5 = new BigDecimal(0);
      BigDecimal localBigDecimal6 = new BigDecimal(0);
      BigDecimal localBigDecimal7 = new BigDecimal(0);
      BigDecimal localBigDecimal8 = new BigDecimal(0);
      Object localObject4 = null;
      Object localObject5 = null;
      Object localObject6 = null;
      Object localObject7 = null;
      String[][] arrayOfString1 = (String[][])null;
      HashMap localHashMap3 = null;
      String[] arrayOfString2 = null;
      String[] arrayOfString3 = null;
      String[] arrayOfString4 = null;
      ArrayList localArrayList1 = null;
      BigDecimal localBigDecimal9 = null;
      BigDecimal localBigDecimal10 = null;
      BigDecimal localBigDecimal11 = null;
      BigDecimal localBigDecimal12 = null;
      BigDecimal localBigDecimal13 = null;
      String[][] arrayOfString5 = (String[][])null;
      String[][] arrayOfString6 = (String[][])null;
      int k = 0;
      Integer localInteger1 = null;
      BigDecimal localBigDecimal14 = null;
      Object localObject8;
      Object localObject9;
      BigDecimal localBigDecimal15;
      Object localObject10;
      while (localResultSet1.next())
      {
        localObject8 = new BigDecimal(0);
        localObject9 = new BigDecimal(0);
        localBigDecimal15 = new BigDecimal(0);
        arrayOfString1 = (String[][])null;
        localHashMap3 = new HashMap();
        arrayOfString2 = null;
        arrayOfString3 = null;
        arrayOfString4 = null;
        localArrayList1 = null;
        localObject10 = new XJTradeResult();
        localHashMap2 = new HashMap();
        String str1 = localResultSet1.getString("name");
        String str2 = localResultSet1.getString("value");
        StringBuffer localStringBuffer2 = new StringBuffer();
        localStringBuffer2.append("select distinct str1 from( select " + paramString7 + " ");
        localStringBuffer2.append("t1.*,t2.str1 from commodity t1,commext t2," + paramString5 + " t3 ");
        localStringBuffer2.append("where t1.id=t2.commid and t3.commodityid=t1.id and t2.str6='" + str2 + "'");
        localStringBuffer2.append(" " + paramString2 + ")");
        localResultSet3 = localDBBean.executeQuery(localStringBuffer2.toString());
        int i1 = 0;
        ArrayList localArrayList2 = new ArrayList();
        while (localResultSet3.next())
        {
          String str3 = localResultSet3.getString("str1");
          localBigDecimal9 = new BigDecimal(0);
          localBigDecimal10 = new BigDecimal(0);
          localBigDecimal11 = new BigDecimal(0);
          localBigDecimal12 = new BigDecimal(0);
          localBigDecimal13 = new BigDecimal(0);
          if (i1 == 0) {
            ((XJTradeResult)localObject10).setProName(str1);
          }
          TradeResult localTradeResult = new TradeResult();
          localStringBuffer1 = new StringBuffer();
          localStringBuffer1.append("select str4 v1 from( select " + paramString7 + " ");
          localStringBuffer1.append("t1.*,t2.str1,t2.str4 from commodity t1,commext t2," + paramString5 + " t3 ");
          localStringBuffer1.append("where t1.id=t2.commid and t3.commodityid=t1.id and t2.str6='" + str2 + "'");
          localStringBuffer1.append(" and t2.str1='" + str3 + "' " + paramString2 + ")");
          localResultSet2 = localDBBean.executeQuery(localStringBuffer1.toString());
          while (localResultSet2.next()) {
            if (localResultSet2.getString(1) != null) {
              if (localTradeResult.getGrade() != null) {
                localTradeResult.setGrade(ManaUtil.splitSameGrade(localResultSet2.getString(1) + "/" + localTradeResult.getGrade()));
              } else {
                localTradeResult.setGrade(ManaUtil.splitSameGrade(localResultSet2.getString(1)));
              }
            }
          }
          localResultSet2.close();
          localDBBean.closeStmt();
          localHashMap3 = new HashMap();
          if (localTradeResult.getGrade() != null)
          {
            arrayOfString2 = localTradeResult.getGrade().split("/");
            arrayOfString1 = new String[arrayOfString2.length][6];
            for (i2 = 0; i2 < arrayOfString2.length; i2++)
            {
              localHashMap3.put(arrayOfString2[i2], new Integer(i2));
              arrayOfString1[i2][0] = arrayOfString2[i2];
              arrayOfString1[i2][1] = "0";
              arrayOfString1[i2][2] = "0";
              arrayOfString1[i2][3] = "0";
              arrayOfString1[i2][4] = "0";
              arrayOfString1[i2][5] = "0";
            }
          }
          localStringBuffer1 = new StringBuffer();
          localStringBuffer1.append(" select " + paramString7 + " t1.*,t2.str3 from commodity t1,commext t2," + paramString5 + " t3 ");
          localStringBuffer1.append("where t1.id=t2.commid and t3.commodityid=t1.id and t2.str6='" + str2 + "'and ");
          localStringBuffer1.append("t2.str1= '" + str3 + "' " + paramString2 + "");
          localResultSet2 = localDBBean.executeQuery(localStringBuffer1.toString());
          Integer localInteger3;
          while (localResultSet2.next()) {
            if (localResultSet2.getString("str3") != null)
            {
              arrayOfString3 = localResultSet2.getString("str3").split(";");
              for (i2 = 0; i2 < arrayOfString3.length; i2++) {
                if (arrayOfString3[i2] != null)
                {
                  arrayOfString4 = arrayOfString3[i2].split(",");
                  if (localHashMap3.containsKey(arrayOfString4[2]))
                  {
                    localInteger3 = (Integer)localHashMap3.get(arrayOfString4[2]);
                    arrayOfString1[localInteger3.intValue()][1] = String.valueOf(new BigDecimal(arrayOfString1[localInteger3.intValue()][1]).add(new BigDecimal(arrayOfString4[8])));
                  }
                }
              }
            }
          }
          localResultSet2.close();
          localDBBean.closeStmt();
          arrayOfString3 = null;
          arrayOfString4 = null;
          localStringBuffer1 = new StringBuffer();
          localStringBuffer1.append("select t3.str3,t1.price from " + paramString6 + " t1,commodity t2,commext t3 where t2.id=");
          localStringBuffer1.append("t3.commid and t1.commodityid=t2.id and t3.str6='" + str2 + "' and t3.str1='" + str3 + "'");
          localStringBuffer1.append("" + paramString3 + "");
          localResultSet2 = localDBBean.executeQuery(localStringBuffer1.toString());
          while (localResultSet2.next())
          {
            localBigDecimal14 = ManaUtil.disBD(localResultSet2.getBigDecimal("price"));
            if (localResultSet2.getString("str3") != null)
            {
              arrayOfString3 = localResultSet2.getString("str3").split(";");
              for (i2 = 0; i2 < arrayOfString3.length; i2++) {
                if (arrayOfString3[i2] != null)
                {
                  arrayOfString4 = arrayOfString3[i2].split(",");
                  if (localHashMap3.containsKey(arrayOfString4[2]))
                  {
                    localInteger3 = (Integer)localHashMap3.get(arrayOfString4[2]);
                    arrayOfString1[localInteger3.intValue()][2] = String.valueOf(new BigDecimal(arrayOfString1[localInteger3.intValue()][2]).add(new BigDecimal(arrayOfString4[8])));
                    arrayOfString1[localInteger3.intValue()][3] = String.valueOf(new BigDecimal(arrayOfString1[localInteger3.intValue()][3]).add(new BigDecimal(arrayOfString4[8]).multiply(localBigDecimal14)));
                    if (new BigDecimal(arrayOfString1[localInteger3.intValue()][4]).compareTo(new BigDecimal(0)) == 0) {
                      arrayOfString1[localInteger3.intValue()][4] = localBigDecimal14.toString();
                    } else if (new BigDecimal(arrayOfString1[localInteger3.intValue()][4]).compareTo(localBigDecimal14) < 0) {
                      arrayOfString1[localInteger3.intValue()][4] = localBigDecimal14.toString();
                    }
                    if (new BigDecimal(arrayOfString1[localInteger3.intValue()][5]).compareTo(new BigDecimal(0)) == 0) {
                      arrayOfString1[localInteger3.intValue()][5] = localBigDecimal14.toString();
                    } else if (new BigDecimal(arrayOfString1[localInteger3.intValue()][5]).compareTo(localBigDecimal14) > 0) {
                      arrayOfString1[localInteger3.intValue()][5] = localBigDecimal14.toString();
                    }
                  }
                }
              }
            }
          }
          localResultSet2.close();
          localDBBean.closeStmt();
          for (int i2 = 0; i2 < arrayOfString1.length; i2++)
          {
            arrayOfString1[i2][1] = String.valueOf(new BigDecimal(arrayOfString1[i2][1]).divide(new BigDecimal(10000), 2, 4));
            if (new BigDecimal(arrayOfString1[i2][1]).compareTo(new BigDecimal(0)) > 0)
            {
              localBigDecimal9 = localBigDecimal9.add(new BigDecimal(arrayOfString1[i2][1]));
              arrayOfString1[i2][2] = String.valueOf(new BigDecimal(arrayOfString1[i2][2]).divide(new BigDecimal(10000), 2, 4));
              localBigDecimal10 = localBigDecimal10.add(new BigDecimal(arrayOfString1[i2][2]));
              arrayOfString1[i2][3] = String.valueOf(new BigDecimal(arrayOfString1[i2][3]).divide(new BigDecimal(10000), 2, 4));
              localBigDecimal13 = localBigDecimal13.add(new BigDecimal(arrayOfString1[i2][3]));
              if (localBigDecimal11.compareTo(new BigDecimal(0)) == 0) {
                localBigDecimal11 = new BigDecimal(arrayOfString1[i2][4]);
              } else if (localBigDecimal11.compareTo(new BigDecimal(arrayOfString1[i2][4])) < 0) {
                localBigDecimal11 = new BigDecimal(arrayOfString1[i2][4]);
              }
              if (localBigDecimal12.compareTo(new BigDecimal(0)) == 0) {
                localBigDecimal12 = new BigDecimal(arrayOfString1[i2][5]);
              } else if (localBigDecimal12.compareTo(new BigDecimal(arrayOfString1[i2][5])) > 0) {
                localBigDecimal12 = new BigDecimal(arrayOfString1[i2][5]);
              }
            }
          }
          if (localBigDecimal9.compareTo(new BigDecimal(0)) > 0)
          {
            localTradeResult.setPlanAmount(localBigDecimal9);
            localObject8 = ((BigDecimal)localObject8).add(localBigDecimal9);
            localBigDecimal6 = localBigDecimal6.add(localBigDecimal9);
            localTradeResult.setVari(str3);
            Integer localInteger2;
            if (localHashMap1.containsKey(str3))
            {
              localInteger2 = (Integer)localHashMap1.get(str3);
              arrayOfObject[localInteger2.intValue()][1] = String.valueOf(new BigDecimal(arrayOfObject[localInteger2.intValue()][1].toString()).add(localBigDecimal9));
            }
            localTradeResult.setTradeAmount(localBigDecimal10);
            localObject9 = ((BigDecimal)localObject9).add(localBigDecimal10);
            localBigDecimal7 = localBigDecimal7.add(localBigDecimal10);
            localTradeResult.setMaxPrice(localBigDecimal11);
            localTradeResult.setMinPrice(localBigDecimal12);
            localTradeResult.setTradeMoney(localBigDecimal13);
            localBigDecimal15 = localBigDecimal15.add(localBigDecimal13);
            localBigDecimal8 = localBigDecimal8.add(localBigDecimal13);
            if (localHashMap1.containsKey(str3))
            {
              localInteger2 = (Integer)localHashMap1.get(str3);
              arrayOfObject[localInteger2.intValue()][2] = String.valueOf(new BigDecimal(arrayOfObject[localInteger2.intValue()][2].toString()).add(localBigDecimal10));
            }
            if (localHashMap1.containsKey(str3))
            {
              localInteger2 = (Integer)localHashMap1.get(str3);
              arrayOfObject[localInteger2.intValue()][3] = String.valueOf(new BigDecimal(arrayOfObject[localInteger2.intValue()][3].toString()).add(localBigDecimal13));
            }
            if (localHashMap1.containsKey(str3))
            {
              localInteger2 = (Integer)localHashMap1.get(str3);
              if (new Double(arrayOfObject[localInteger2.intValue()][4].toString()).doubleValue() == 0.0D) {
                arrayOfObject[localInteger2.intValue()][4] = String.valueOf(localBigDecimal11);
              }
              if (localBigDecimal11.compareTo(new BigDecimal(arrayOfObject[localInteger2.intValue()][4].toString())) > 0) {
                arrayOfObject[localInteger2.intValue()][4] = String.valueOf(localBigDecimal11);
              }
            }
            if (localHashMap1.containsKey(str3))
            {
              localInteger2 = (Integer)localHashMap1.get(str3);
              if (new Double(arrayOfObject[localInteger2.intValue()][5].toString()).doubleValue() == 0.0D) {
                arrayOfObject[localInteger2.intValue()][5] = String.valueOf(localBigDecimal12);
              }
              if ((localBigDecimal12.compareTo(new BigDecimal(arrayOfObject[localInteger2.intValue()][5].toString())) < 0) && (localBigDecimal12.doubleValue() > 0.0D)) {
                arrayOfObject[localInteger2.intValue()][5] = String.valueOf(localBigDecimal12);
              }
            }
            if (localHashMap1.containsKey(str3))
            {
              localInteger2 = (Integer)localHashMap1.get(str3);
              if (arrayOfObject[localInteger2.intValue()][7] == null)
              {
                arrayOfObject[localInteger2.intValue()][7] = arrayOfString1;
              }
              else
              {
                arrayOfString5 = (String[][])arrayOfObject[localInteger2.intValue()][7];
                localHashMap3 = new HashMap();
                for (int i4 = 0; i4 < arrayOfString5.length; i4++) {
                  localHashMap3.put(arrayOfString5[i4][0], new Integer(i4));
                }
                k = 0;
                for (i4 = 0; i4 < arrayOfString1.length; i4++) {
                  if ((!localHashMap3.containsKey(arrayOfString1[i4][0])) && (new BigDecimal(arrayOfString1[i4][1]).compareTo(new BigDecimal(0)) > 0)) {
                    k++;
                  }
                }
                arrayOfString6 = new String[arrayOfString5.length + k][6];
                for (i4 = 0; i4 < arrayOfString5.length; i4++)
                {
                  arrayOfString6[i4][0] = arrayOfString5[i4][0];
                  arrayOfString6[i4][1] = arrayOfString5[i4][1];
                  arrayOfString6[i4][2] = arrayOfString5[i4][2];
                  arrayOfString6[i4][3] = arrayOfString5[i4][3];
                  arrayOfString6[i4][4] = arrayOfString5[i4][4];
                  arrayOfString6[i4][5] = arrayOfString5[i4][5];
                }
                k = 0;
                for (i4 = 0; i4 < arrayOfString1.length; i4++) {
                  if (localHashMap3.containsKey(arrayOfString1[i4][0]))
                  {
                    localInteger1 = (Integer)localHashMap3.get(arrayOfString1[i4][0]);
                    if (new BigDecimal(arrayOfString6[localInteger1.intValue()][1]).compareTo(new BigDecimal(0)) > 0)
                    {
                      arrayOfString6[localInteger1.intValue()][1] = String.valueOf(new BigDecimal(arrayOfString6[localInteger1.intValue()][1]).add(new BigDecimal(arrayOfString1[i4][1])));
                      arrayOfString6[localInteger1.intValue()][2] = String.valueOf(new BigDecimal(arrayOfString6[localInteger1.intValue()][2]).add(new BigDecimal(arrayOfString1[i4][2])));
                      arrayOfString6[localInteger1.intValue()][3] = String.valueOf(new BigDecimal(arrayOfString6[localInteger1.intValue()][3]).add(new BigDecimal(arrayOfString1[i4][3])));
                      if (new BigDecimal(arrayOfString6[localInteger1.intValue()][4]).compareTo(new BigDecimal(arrayOfString1[i4][4])) < 0) {
                        arrayOfString6[localInteger1.intValue()][4] = arrayOfString1[i4][4];
                      }
                      if (new BigDecimal(arrayOfString6[localInteger1.intValue()][5]).compareTo(new BigDecimal(arrayOfString1[i4][5])) > 0) {
                        arrayOfString6[localInteger1.intValue()][5] = arrayOfString1[i4][5];
                      }
                    }
                  }
                  else if (new BigDecimal(arrayOfString1[i4][1]).compareTo(new BigDecimal(0)) > 0)
                  {
                    arrayOfString6[(arrayOfString5.length + k)][0] = arrayOfString1[i4][0];
                    arrayOfString6[(arrayOfString5.length + k)][1] = arrayOfString1[i4][1];
                    arrayOfString6[(arrayOfString5.length + k)][2] = arrayOfString1[i4][2];
                    arrayOfString6[(arrayOfString5.length + k)][3] = arrayOfString1[i4][3];
                    arrayOfString6[(arrayOfString5.length + k)][4] = arrayOfString1[i4][4];
                    arrayOfString6[(arrayOfString5.length + k)][5] = arrayOfString1[i4][5];
                    k++;
                  }
                }
                arrayOfObject[localInteger2.intValue()][7] = arrayOfString6;
              }
            }
            localArrayList1 = new ArrayList();
            for (int i3 = 0; i3 < arrayOfString1.length; i3++)
            {
              GradeTradeResult localGradeTradeResult2 = new GradeTradeResult();
              localGradeTradeResult2.setGrade(arrayOfString1[i3][0]);
              if (new BigDecimal(arrayOfString1[i3][1]).compareTo(new BigDecimal(0)) > 0)
              {
                localGradeTradeResult2.setPlanAmount(new BigDecimal(arrayOfString1[i3][1]));
                localGradeTradeResult2.setTradeAmount(new BigDecimal(arrayOfString1[i3][2]));
                localGradeTradeResult2.setTradeMoney(new BigDecimal(arrayOfString1[i3][3]));
                localGradeTradeResult2.setMaxPrice(new BigDecimal(arrayOfString1[i3][4]));
                localGradeTradeResult2.setMinPrice(new BigDecimal(arrayOfString1[i3][5]));
                localObject4 = localGradeTradeResult2.getTradeMoney();
                localObject5 = localGradeTradeResult2.getTradeAmount();
                if (((BigDecimal)localObject5).compareTo(new BigDecimal(0)) <= 0) {
                  localObject5 = new BigDecimal(1);
                }
                localObject6 = ((BigDecimal)localObject4).divide((BigDecimal)localObject5, 0, 4);
                if (localGradeTradeResult2.getMaxPrice() != localGradeTradeResult2.getMinPrice())
                {
                  if (((BigDecimal)localObject6).compareTo(localGradeTradeResult2.getMaxPrice()) > 0) {
                    localObject6 = localGradeTradeResult2.getMaxPrice();
                  }
                  if (((BigDecimal)localObject6).compareTo(localGradeTradeResult2.getMinPrice()) < 0) {
                    localObject6 = localGradeTradeResult2.getMinPrice();
                  }
                }
                else
                {
                  localObject6 = localBigDecimal3;
                }
                localGradeTradeResult2.setAvgPrice((BigDecimal)localObject6);
                localArrayList1.add(localGradeTradeResult2);
              }
            }
            localObject4 = localBigDecimal13;
            localObject5 = localBigDecimal10;
            if (((BigDecimal)localObject5).compareTo(new BigDecimal(0)) <= 0) {
              localObject5 = new BigDecimal(1);
            }
            localObject6 = ((BigDecimal)localObject4).divide((BigDecimal)localObject5, 0, 4);
            if (localBigDecimal11 != localBigDecimal12)
            {
              if (((BigDecimal)localObject6).compareTo(localBigDecimal11) > 0) {
                localObject6 = localBigDecimal11;
              }
              if (((BigDecimal)localObject6).compareTo(localBigDecimal12) < 0) {
                localObject6 = localBigDecimal12;
              }
            }
            else
            {
              localObject6 = localBigDecimal11;
            }
            localTradeResult.setAvgPrice((BigDecimal)localObject6);
            if (localBigDecimal9.compareTo(new BigDecimal(0)) > 0) {
              localTradeResult.setTradeRatio(ManaUtil.accuracyNum(localBigDecimal10.divide(localBigDecimal9, 4, 4).multiply(new BigDecimal(100)), ".##"));
            } else {
              localTradeResult.setTradeRatio(new BigDecimal(0.0D));
            }
            localTradeResult.setGradeDetail(localArrayList1);
            localArrayList2.add(localTradeResult);
          }
          i1++;
        }
        if (localArrayList2.size() > 0)
        {
          ((XJTradeResult)localObject10).setTradeResult(localArrayList2);
          if (((BigDecimal)localObject8).compareTo(new BigDecimal(0)) > 0)
          {
            ((XJTradeResult)localObject10).setXJPlanAmount((BigDecimal)localObject8);
            ((XJTradeResult)localObject10).setXJTradeAmount((BigDecimal)localObject9);
            ((XJTradeResult)localObject10).setXJTradeRatio(ManaUtil.accuracyNum(((BigDecimal)localObject9).divide((BigDecimal)localObject8, 4, 4).multiply(new BigDecimal(100)), ".##"));
            ((XJTradeResult)localObject10).setXJTradeMoney(localBigDecimal15);
          }
          arrayOfArrayList1[0].add(localObject10);
        }
      }
      localResultSet1.close();
      localDBBean.close();
      if (localBigDecimal6.compareTo(new BigDecimal(0)) > 0)
      {
        localObject8 = new HJTradeResult();
        ((HJTradeResult)localObject8).setHJPlanAmount(localBigDecimal6);
        ((HJTradeResult)localObject8).setHJTradeAmount(localBigDecimal7);
        ((HJTradeResult)localObject8).setHJTradeRatio(ManaUtil.accuracyNum(localBigDecimal7.divide(localBigDecimal6, 4, 4).multiply(new BigDecimal(100)), ".##"));
        ((HJTradeResult)localObject8).setHJTradeMoney(localBigDecimal8);
        arrayOfArrayList1[1].add(localObject8);
      }
      for (int m = 0; m < arrayOfObject.length; m++) {
        if (new BigDecimal(arrayOfObject[m][1].toString()).compareTo(new BigDecimal(0)) > 0)
        {
          localObject9 = new VariTradeResult();
          ((VariTradeResult)localObject9).setVari(arrayOfObject[m][0].toString());
          ((VariTradeResult)localObject9).setVPlanAmount(new BigDecimal(arrayOfObject[m][1].toString()));
          ((VariTradeResult)localObject9).setVTradeAmount(new BigDecimal(arrayOfObject[m][2].toString()));
          localBigDecimal15 = new BigDecimal(arrayOfObject[m][2].toString());
          if (localBigDecimal15.compareTo(new BigDecimal(0)) <= 0) {
            localBigDecimal15 = new BigDecimal(1);
          }
          localObject10 = new BigDecimal(arrayOfObject[m][3].toString()).divide(localBigDecimal15, 0, 4);
          if (((BigDecimal)localObject10).compareTo(new BigDecimal(arrayOfObject[m][4].toString())) > 0) {
            localObject10 = new BigDecimal(arrayOfObject[m][4].toString());
          }
          if (((BigDecimal)localObject10).compareTo(new BigDecimal(arrayOfObject[m][5].toString())) < 0) {
            localObject10 = new BigDecimal(arrayOfObject[m][5].toString());
          }
          ((VariTradeResult)localObject9).setVAvgPrice((BigDecimal)localObject10);
          ((VariTradeResult)localObject9).setVTradeRatio(ManaUtil.accuracyNum(new BigDecimal(arrayOfObject[m][2].toString()).divide(new BigDecimal(arrayOfObject[m][1].toString()), 4, 4).multiply(new BigDecimal(100)), ".##"));
          ((VariTradeResult)localObject9).setVTradeMoney(new BigDecimal(arrayOfObject[m][3].toString()));
          ((VariTradeResult)localObject9).setGrade(arrayOfObject[m][6].toString());
          arrayOfString5 = (String[][])arrayOfObject[m][7];
          localArrayList1 = new ArrayList();
          for (int n = 0; n < arrayOfString5.length; n++)
          {
            GradeTradeResult localGradeTradeResult1 = new GradeTradeResult();
            localGradeTradeResult1.setGrade(arrayOfString5[n][0]);
            if (new BigDecimal(arrayOfString5[n][1]).compareTo(new BigDecimal(0)) > 0)
            {
              localGradeTradeResult1.setPlanAmount(new BigDecimal(arrayOfString5[n][1]));
              localGradeTradeResult1.setTradeAmount(new BigDecimal(arrayOfString5[n][2]));
              localGradeTradeResult1.setTradeMoney(new BigDecimal(arrayOfString5[n][3]));
              localGradeTradeResult1.setMaxPrice(new BigDecimal(arrayOfString5[n][4]));
              localGradeTradeResult1.setMinPrice(new BigDecimal(arrayOfString5[n][5]));
              localObject4 = localGradeTradeResult1.getTradeMoney();
              localObject5 = localGradeTradeResult1.getTradeAmount();
              if (((BigDecimal)localObject5).compareTo(new BigDecimal(0)) <= 0) {
                localObject5 = new BigDecimal(1);
              }
              localObject6 = ((BigDecimal)localObject4).divide((BigDecimal)localObject5, 0, 4);
              if (localGradeTradeResult1.getMaxPrice() != localGradeTradeResult1.getMinPrice())
              {
                if (((BigDecimal)localObject6).compareTo(localGradeTradeResult1.getMaxPrice()) > 0) {
                  localObject6 = localGradeTradeResult1.getMaxPrice();
                }
                if (((BigDecimal)localObject6).compareTo(localGradeTradeResult1.getMinPrice()) < 0) {
                  localObject6 = localGradeTradeResult1.getMinPrice();
                }
              }
              else
              {
                localObject6 = localBigDecimal3;
              }
              localGradeTradeResult1.setAvgPrice((BigDecimal)localObject6);
              localArrayList1.add(localGradeTradeResult1);
            }
          }
          ((VariTradeResult)localObject9).setGradeArray(localArrayList1);
          arrayOfArrayList1[2].add(localObject9);
        }
      }
      ArrayList[] arrayOfArrayList2 = arrayOfArrayList1;
      return arrayOfArrayList2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer1 = null;
      return localStringBuffer1;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException8) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException9) {}
      try
      {
        if (localResultSet3 != null) {
          localResultSet3.close();
        }
      }
      catch (Exception localException10) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList[] getZJSaleTradeResult(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    ResultSet localResultSet3 = null;
    ResultSet localResultSet4 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList[] arrayOfArrayList1 = new ArrayList[3];
      arrayOfArrayList1[0] = new ArrayList();
      arrayOfArrayList1[1] = new ArrayList();
      arrayOfArrayList1[2] = new ArrayList();
      localStringBuffer1 = new StringBuffer();
      int i = 0;
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = null;
      Object localObject1 = null;
      Object localObject2 = null;
      Object localObject3 = null;
      String str1 = null;
      int j = 0;
      String[][] arrayOfString1 = (String[][])null;
      String[][] arrayOfString2 = (String[][])null;
      HashMap localHashMap3 = null;
      String[] arrayOfString3 = null;
      String[] arrayOfString4 = null;
      String[] arrayOfString5 = null;
      ArrayList localArrayList1 = null;
      BigDecimal localBigDecimal1 = null;
      BigDecimal localBigDecimal2 = null;
      BigDecimal localBigDecimal3 = null;
      BigDecimal localBigDecimal4 = null;
      BigDecimal localBigDecimal5 = null;
      String[][] arrayOfString6 = (String[][])null;
      String[][] arrayOfString7 = (String[][])null;
      int k = 0;
      Integer localInteger1 = null;
      BigDecimal localBigDecimal6 = null;
      localStringBuffer1.append("select count(distinct t2.str1) as v from commodity t1,commext t2," + paramString5 + " t3 where t1.id=");
      localStringBuffer1.append("t2.commid and t3.commodityid=t1.id" + paramString2);
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      if (localResultSet1.next()) {
        i = localResultSet1.getInt("v");
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      Object[][] arrayOfObject = new Object[i][8];
      localStringBuffer1 = new StringBuffer();
      localStringBuffer1.append("select distinct t2.str1 from commodity t1,commext t2," + paramString5 + " t3 where t1.id=");
      localStringBuffer1.append("t2.commid and t3.commodityid=t1.id" + paramString2);
      int m = 0;
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      while (localResultSet1.next())
      {
        localHashMap1.put(localResultSet1.getString("str1"), new Integer(m));
        arrayOfObject[m][0] = localResultSet1.getString("str1");
        arrayOfObject[m][1] = "0";
        arrayOfObject[m][2] = "0";
        arrayOfObject[m][3] = "0";
        arrayOfObject[m][4] = "0";
        arrayOfObject[m][5] = "0";
        arrayOfObject[m][6] = "";
        arrayOfObject[m][7] = null;
        m++;
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      localStringBuffer1 = new StringBuffer();
      localStringBuffer1.append("select value,name from dictable where type=" + paramString4 + "");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      BigDecimal localBigDecimal7 = new BigDecimal(0);
      BigDecimal localBigDecimal8 = new BigDecimal(0);
      BigDecimal localBigDecimal9 = new BigDecimal(0);
      BigDecimal localBigDecimal10 = new BigDecimal(0);
      BigDecimal localBigDecimal11 = new BigDecimal(0);
      BigDecimal localBigDecimal12 = new BigDecimal(0);
      BigDecimal localBigDecimal13 = new BigDecimal(0);
      BigDecimal localBigDecimal14 = new BigDecimal(0);
      BigDecimal localBigDecimal15 = null;
      BigDecimal localBigDecimal16 = null;
      BigDecimal localBigDecimal17 = null;
      Object localObject4;
      Object localObject5;
      BigDecimal localBigDecimal18;
      Object localObject6;
      Object localObject7;
      while (localResultSet1.next())
      {
        localObject4 = new BigDecimal(0);
        localObject5 = new BigDecimal(0);
        localBigDecimal18 = new BigDecimal(0);
        localObject6 = new XJTradeResult();
        localHashMap2 = new HashMap();
        String str2 = localResultSet1.getString("name");
        String str3 = localResultSet1.getString("value");
        StringBuffer localStringBuffer2 = new StringBuffer();
        localObject7 = new StringBuffer();
        localStringBuffer2.append("select distinct t2.str1 from commodity t1,commext t2," + paramString5 + " t3 where");
        localStringBuffer2.append(" t1.id=t2.commid and t3.commodityid=t1.id and t2.str6='" + str3 + "' " + paramString2 + "");
        localResultSet3 = localDBBean.executeQuery(localStringBuffer2.toString());
        int i2 = 0;
        ArrayList localArrayList2 = new ArrayList();
        while (localResultSet3.next())
        {
          localBigDecimal1 = new BigDecimal(0);
          localBigDecimal2 = new BigDecimal(0);
          localBigDecimal3 = new BigDecimal(0);
          localBigDecimal4 = new BigDecimal(0);
          localBigDecimal5 = new BigDecimal(0);
          TradeResult localTradeResult = new TradeResult();
          String str4 = localResultSet3.getString("str1");
          if (i2 == 0) {
            ((XJTradeResult)localObject6).setProName(str2);
          }
          localTradeResult.setVari(str4);
          localObject7 = new StringBuffer();
          ((StringBuffer)localObject7).append("select str4 v1 from( select " + paramString7 + " ");
          ((StringBuffer)localObject7).append("t1.*,t2.str1,t2.str4 from commodity t1,commext t2," + paramString5 + " t3 ");
          ((StringBuffer)localObject7).append("where t1.id=t2.commid and t3.commodityid=t1.id and t2.str6='" + str3 + "'");
          ((StringBuffer)localObject7).append(" and t2.str1='" + str4 + "' " + paramString2 + ")");
          localResultSet4 = localDBBean.executeQuery(((StringBuffer)localObject7).toString());
          while (localResultSet4.next()) {
            if (localResultSet4.getString(1) != null) {
              if (localTradeResult.getGrade() != null) {
                localTradeResult.setGrade(ManaUtil.splitSameGrade(localResultSet4.getString(1) + "/" + localTradeResult.getGrade()));
              } else {
                localTradeResult.setGrade(ManaUtil.splitSameGrade(localResultSet4.getString(1)));
              }
            }
          }
          localResultSet4.close();
          localDBBean.closeStmt();
          localHashMap3 = new HashMap();
          if (localTradeResult.getGrade() != null)
          {
            arrayOfString3 = localTradeResult.getGrade().split("/");
            arrayOfString1 = new String[arrayOfString3.length][6];
            arrayOfString2 = new String[arrayOfString3.length][6];
            for (i3 = 0; i3 < arrayOfString3.length; i3++)
            {
              localHashMap3.put(arrayOfString3[i3], new Integer(i3));
              arrayOfString1[i3][0] = arrayOfString3[i3];
              arrayOfString1[i3][1] = "0";
              arrayOfString1[i3][2] = "0";
              arrayOfString1[i3][3] = "0";
              arrayOfString1[i3][4] = "0";
              arrayOfString1[i3][5] = "0";
              arrayOfString2[i3][0] = arrayOfString3[i3];
              arrayOfString2[i3][1] = "0";
              arrayOfString2[i3][2] = "0";
              arrayOfString2[i3][3] = "0";
              arrayOfString2[i3][4] = "0";
              arrayOfString2[i3][5] = "0";
            }
          }
          j = 0;
          str1 = "";
          localBigDecimal7 = new BigDecimal(0);
          localStringBuffer2 = new StringBuffer();
          localStringBuffer2.append("select partitionid from syspartition where validflag=1");
          localResultSet2 = localDBBean.executeQuery(localStringBuffer2.toString());
          Integer localInteger4;
          while (localResultSet2.next())
          {
            j = localResultSet2.getInt(1);
            str1 = " and t3.tradepartition=" + j;
            localStringBuffer2 = new StringBuffer();
            localStringBuffer2.append(" select " + paramString7 + " t1.*,t2.str3 from commodity t1,commext t2," + paramString5 + " t3 ");
            localStringBuffer2.append("where t1.id=t2.commid and t3.commodityid=t1.id and t2.str6='" + str3 + "'and ");
            localStringBuffer2.append("t2.str1= '" + str4 + "' " + str1 + " " + paramString2 + "");
            localResultSet4 = localDBBean.executeQuery(localStringBuffer2.toString());
            while (localResultSet4.next()) {
              if (localResultSet4.getString("str3") != null)
              {
                arrayOfString4 = localResultSet4.getString("str3").split(";");
                for (i3 = 0; i3 < arrayOfString4.length; i3++) {
                  if (arrayOfString4[i3] != null)
                  {
                    arrayOfString5 = arrayOfString4[i3].split(",");
                    if (localHashMap3.containsKey(arrayOfString5[2]))
                    {
                      localInteger4 = (Integer)localHashMap3.get(arrayOfString5[2]);
                      arrayOfString1[localInteger4.intValue()][1] = String.valueOf(new BigDecimal(arrayOfString1[localInteger4.intValue()][1]).add(new BigDecimal(arrayOfString5[8])));
                    }
                  }
                }
              }
            }
            localResultSet4.close();
            localDBBean.closeStmt();
            arrayOfString4 = null;
            arrayOfString5 = null;
            for (i3 = 0; i3 < arrayOfString1.length; i3++)
            {
              arrayOfString2[i3][1] = String.valueOf(new BigDecimal(arrayOfString1[i3][1]).divide(new BigDecimal(10000), 2, 4).add(new BigDecimal(arrayOfString2[i3][1])));
              arrayOfString1[i3][1] = "0";
            }
          }
          localResultSet2.close();
          localDBBean.closeStmt();
          j = 0;
          str1 = "";
          localBigDecimal8 = new BigDecimal(0);
          localBigDecimal11 = new BigDecimal(0);
          localBigDecimal17 = new BigDecimal(0);
          localStringBuffer2 = new StringBuffer();
          localStringBuffer2.append("select partitionid from syspartition where validflag=1");
          localResultSet2 = localDBBean.executeQuery(localStringBuffer2.toString());
          while (localResultSet2.next())
          {
            j = localResultSet2.getInt(1);
            str1 = " and t1.tradepartition=" + j;
            localObject7 = new StringBuffer();
            ((StringBuffer)localObject7).append("select t3.str3,t1.price from " + paramString6 + " t1,commodity t2,commext t3 where t2.id=");
            ((StringBuffer)localObject7).append("t3.commid and t1.commodityid=t2.id and t3.str6='" + str3 + "' and t3.str1='" + str4 + "'");
            ((StringBuffer)localObject7).append(" " + str1 + " " + paramString3 + "");
            localResultSet4 = localDBBean.executeQuery(((StringBuffer)localObject7).toString());
            while (localResultSet4.next())
            {
              localBigDecimal6 = ManaUtil.disBD(localResultSet4.getBigDecimal("price"));
              if (localResultSet4.getString("str3") != null)
              {
                arrayOfString4 = localResultSet4.getString("str3").split(";");
                for (i3 = 0; i3 < arrayOfString4.length; i3++) {
                  if (arrayOfString4[i3] != null)
                  {
                    arrayOfString5 = arrayOfString4[i3].split(",");
                    if (localHashMap3.containsKey(arrayOfString5[2]))
                    {
                      localInteger4 = (Integer)localHashMap3.get(arrayOfString5[2]);
                      arrayOfString1[localInteger4.intValue()][2] = String.valueOf(new BigDecimal(arrayOfString1[localInteger4.intValue()][2]).add(new BigDecimal(arrayOfString5[8])));
                      arrayOfString1[localInteger4.intValue()][3] = String.valueOf(new BigDecimal(arrayOfString1[localInteger4.intValue()][3]).add(new BigDecimal(arrayOfString5[8]).multiply(localBigDecimal6)));
                      if (new BigDecimal(arrayOfString1[localInteger4.intValue()][4]).compareTo(new BigDecimal(0)) == 0) {
                        arrayOfString1[localInteger4.intValue()][4] = localBigDecimal6.toString();
                      } else if (new BigDecimal(arrayOfString1[localInteger4.intValue()][4]).compareTo(localBigDecimal6) < 0) {
                        arrayOfString1[localInteger4.intValue()][4] = localBigDecimal6.toString();
                      }
                      if (new BigDecimal(arrayOfString1[localInteger4.intValue()][5]).compareTo(new BigDecimal(0)) == 0) {
                        arrayOfString1[localInteger4.intValue()][5] = localBigDecimal6.toString();
                      } else if (new BigDecimal(arrayOfString1[localInteger4.intValue()][5]).compareTo(localBigDecimal6) > 0) {
                        arrayOfString1[localInteger4.intValue()][5] = localBigDecimal6.toString();
                      }
                    }
                  }
                }
              }
            }
            localResultSet4.close();
            localDBBean.closeStmt();
            for (i3 = 0; i3 < arrayOfString1.length; i3++)
            {
              arrayOfString2[i3][2] = String.valueOf(new BigDecimal(arrayOfString1[i3][2]).divide(new BigDecimal(10000), 2, 4).add(new BigDecimal(arrayOfString2[i3][2])));
              arrayOfString2[i3][3] = String.valueOf(new BigDecimal(arrayOfString1[i3][3]).divide(new BigDecimal(10000), 2, 4).add(new BigDecimal(arrayOfString2[i3][3])));
              if (new BigDecimal(arrayOfString2[i3][4]).intValue() == 0) {
                arrayOfString2[i3][4] = arrayOfString1[i3][4];
              } else if (new BigDecimal(arrayOfString2[i3][4]).compareTo(new BigDecimal(arrayOfString1[i3][4])) < 0) {
                arrayOfString2[i3][4] = arrayOfString1[i3][4];
              }
              if (new BigDecimal(arrayOfString2[i3][5]).intValue() == 0) {
                arrayOfString2[i3][5] = arrayOfString1[i3][5];
              } else if (new BigDecimal(arrayOfString2[i3][5]).compareTo(new BigDecimal(arrayOfString1[i3][5])) > 0) {
                arrayOfString2[i3][5] = arrayOfString1[i3][5];
              }
              arrayOfString1[i3][2] = "0";
              arrayOfString1[i3][3] = "0";
            }
          }
          localResultSet2.close();
          localDBBean.closeStmt();
          for (int i3 = 0; i3 < arrayOfString2.length; i3++) {
            if (new BigDecimal(arrayOfString2[i3][1]).compareTo(new BigDecimal(0)) > 0)
            {
              localBigDecimal1 = localBigDecimal1.add(new BigDecimal(arrayOfString2[i3][1]));
              localBigDecimal2 = localBigDecimal2.add(new BigDecimal(arrayOfString2[i3][2]));
              localBigDecimal5 = localBigDecimal5.add(new BigDecimal(arrayOfString2[i3][3]));
              if (localBigDecimal3.compareTo(new BigDecimal(0)) == 0) {
                localBigDecimal3 = new BigDecimal(arrayOfString2[i3][4]);
              } else if (localBigDecimal3.compareTo(new BigDecimal(arrayOfString2[i3][4])) < 0) {
                localBigDecimal3 = new BigDecimal(arrayOfString2[i3][4]);
              }
              if (localBigDecimal4.compareTo(new BigDecimal(0)) == 0) {
                localBigDecimal4 = new BigDecimal(arrayOfString2[i3][5]);
              } else if (localBigDecimal4.compareTo(new BigDecimal(arrayOfString2[i3][5])) > 0) {
                localBigDecimal4 = new BigDecimal(arrayOfString2[i3][5]);
              }
            }
          }
          if (localBigDecimal1.compareTo(new BigDecimal(0)) > 0)
          {
            localTradeResult.setPlanAmount(localBigDecimal1);
            localObject4 = ((BigDecimal)localObject4).add(localBigDecimal1);
            localBigDecimal12 = localBigDecimal12.add(localBigDecimal1);
            localTradeResult.setTradeAmount(localBigDecimal2);
            localObject5 = ((BigDecimal)localObject5).add(localBigDecimal2);
            localBigDecimal13 = localBigDecimal13.add(localBigDecimal2);
            localTradeResult.setTradeMoney(localBigDecimal5);
            localBigDecimal18 = localBigDecimal18.add(localBigDecimal5);
            localBigDecimal14 = localBigDecimal14.add(localBigDecimal5);
            localTradeResult.setMaxPrice(localBigDecimal3);
            localTradeResult.setMinPrice(localBigDecimal4);
            Integer localInteger2;
            if (localHashMap1.containsKey(str4))
            {
              localInteger2 = (Integer)localHashMap1.get(str4);
              arrayOfObject[localInteger2.intValue()][1] = String.valueOf(new BigDecimal(arrayOfObject[localInteger2.intValue()][1].toString()).add(localBigDecimal1));
            }
            if (localHashMap1.containsKey(str4))
            {
              localInteger2 = (Integer)localHashMap1.get(str4);
              arrayOfObject[localInteger2.intValue()][2] = String.valueOf(new BigDecimal(arrayOfObject[localInteger2.intValue()][2].toString()).add(localBigDecimal2));
            }
            if (localHashMap1.containsKey(str4))
            {
              localInteger2 = (Integer)localHashMap1.get(str4);
              arrayOfObject[localInteger2.intValue()][3] = String.valueOf(new BigDecimal(arrayOfObject[localInteger2.intValue()][3].toString()).add(localBigDecimal5));
            }
            if (localHashMap1.containsKey(str4))
            {
              localInteger2 = (Integer)localHashMap1.get(str4);
              if (new Double(arrayOfObject[localInteger2.intValue()][4].toString()).doubleValue() == 0.0D) {
                arrayOfObject[localInteger2.intValue()][4] = String.valueOf(localBigDecimal3);
              }
              if (localBigDecimal3.compareTo(new BigDecimal(arrayOfObject[localInteger2.intValue()][4].toString())) > 0) {
                arrayOfObject[localInteger2.intValue()][4] = String.valueOf(localBigDecimal3);
              }
            }
            if (localHashMap1.containsKey(str4))
            {
              localInteger2 = (Integer)localHashMap1.get(str4);
              if (new Double(arrayOfObject[localInteger2.intValue()][5].toString()).doubleValue() == 0.0D) {
                arrayOfObject[localInteger2.intValue()][5] = String.valueOf(localBigDecimal4);
              }
              if ((localBigDecimal4.compareTo(new BigDecimal(arrayOfObject[localInteger2.intValue()][5].toString())) < 0) && (localBigDecimal4.doubleValue() > 0.0D)) {
                arrayOfObject[localInteger2.intValue()][5] = String.valueOf(localBigDecimal4);
              }
            }
            if (localHashMap1.containsKey(str4))
            {
              localInteger2 = (Integer)localHashMap1.get(str4);
              if (arrayOfObject[localInteger2.intValue()][7] == null)
              {
                arrayOfObject[localInteger2.intValue()][7] = arrayOfString2;
              }
              else
              {
                arrayOfString6 = (String[][])arrayOfObject[localInteger2.intValue()][7];
                localHashMap3 = new HashMap();
                for (int i5 = 0; i5 < arrayOfString6.length; i5++) {
                  localHashMap3.put(arrayOfString6[i5][0], new Integer(i5));
                }
                k = 0;
                for (i5 = 0; i5 < arrayOfString2.length; i5++) {
                  if ((!localHashMap3.containsKey(arrayOfString2[i5][0])) && (new BigDecimal(arrayOfString2[i5][1]).compareTo(new BigDecimal(0)) > 0)) {
                    k++;
                  }
                }
                arrayOfString7 = new String[arrayOfString6.length + k][6];
                for (i5 = 0; i5 < arrayOfString6.length; i5++)
                {
                  arrayOfString7[i5][0] = arrayOfString6[i5][0];
                  arrayOfString7[i5][1] = arrayOfString6[i5][1];
                  arrayOfString7[i5][2] = arrayOfString6[i5][2];
                  arrayOfString7[i5][3] = arrayOfString6[i5][3];
                  arrayOfString7[i5][4] = arrayOfString6[i5][4];
                  arrayOfString7[i5][5] = arrayOfString6[i5][5];
                }
                k = 0;
                for (i5 = 0; i5 < arrayOfString2.length; i5++) {
                  if (localHashMap3.containsKey(arrayOfString2[i5][0]))
                  {
                    localInteger1 = (Integer)localHashMap3.get(arrayOfString2[i5][0]);
                    if (new BigDecimal(arrayOfString7[localInteger1.intValue()][1]).compareTo(new BigDecimal(0)) > 0)
                    {
                      arrayOfString7[localInteger1.intValue()][1] = String.valueOf(new BigDecimal(arrayOfString7[localInteger1.intValue()][1]).add(new BigDecimal(arrayOfString2[i5][1])));
                      arrayOfString7[localInteger1.intValue()][2] = String.valueOf(new BigDecimal(arrayOfString7[localInteger1.intValue()][2]).add(new BigDecimal(arrayOfString2[i5][2])));
                      arrayOfString7[localInteger1.intValue()][3] = String.valueOf(new BigDecimal(arrayOfString7[localInteger1.intValue()][3]).add(new BigDecimal(arrayOfString2[i5][3])));
                      if (new BigDecimal(arrayOfString7[localInteger1.intValue()][4]).compareTo(new BigDecimal(arrayOfString2[i5][4])) < 0) {
                        arrayOfString7[localInteger1.intValue()][4] = arrayOfString2[i5][4];
                      }
                      if (new BigDecimal(arrayOfString7[localInteger1.intValue()][5]).compareTo(new BigDecimal(arrayOfString2[i5][5])) > 0) {
                        arrayOfString7[localInteger1.intValue()][5] = arrayOfString2[i5][5];
                      }
                    }
                  }
                  else if (new BigDecimal(arrayOfString2[i5][1]).compareTo(new BigDecimal(0)) > 0)
                  {
                    arrayOfString7[(arrayOfString6.length + k)][0] = arrayOfString2[i5][0];
                    arrayOfString7[(arrayOfString6.length + k)][1] = arrayOfString2[i5][1];
                    arrayOfString7[(arrayOfString6.length + k)][2] = arrayOfString2[i5][2];
                    arrayOfString7[(arrayOfString6.length + k)][3] = arrayOfString2[i5][3];
                    arrayOfString7[(arrayOfString6.length + k)][4] = arrayOfString2[i5][4];
                    arrayOfString7[(arrayOfString6.length + k)][5] = arrayOfString2[i5][5];
                    k++;
                  }
                }
                arrayOfObject[localInteger2.intValue()][7] = arrayOfString7;
              }
            }
            localArrayList1 = new ArrayList();
            for (int i4 = 0; i4 < arrayOfString2.length; i4++)
            {
              GradeTradeResult localGradeTradeResult = new GradeTradeResult();
              localGradeTradeResult.setGrade(arrayOfString2[i4][0]);
              if (new BigDecimal(arrayOfString2[i4][1]).compareTo(new BigDecimal(0)) > 0)
              {
                localGradeTradeResult.setPlanAmount(new BigDecimal(arrayOfString2[i4][1]));
                localGradeTradeResult.setTradeAmount(new BigDecimal(arrayOfString2[i4][2]));
                localGradeTradeResult.setTradeMoney(new BigDecimal(arrayOfString2[i4][3]));
                localGradeTradeResult.setMaxPrice(new BigDecimal(arrayOfString2[i4][4]));
                localGradeTradeResult.setMinPrice(new BigDecimal(arrayOfString2[i4][5]));
                localBigDecimal15 = localGradeTradeResult.getTradeMoney();
                localBigDecimal16 = localGradeTradeResult.getTradeAmount();
                if (localBigDecimal16.compareTo(new BigDecimal(0)) <= 0) {
                  localBigDecimal16 = new BigDecimal(1);
                }
                localBigDecimal17 = localBigDecimal15.divide(localBigDecimal16, 0, 4);
                if (localGradeTradeResult.getMaxPrice() != localGradeTradeResult.getMinPrice())
                {
                  if (localBigDecimal17.compareTo(localGradeTradeResult.getMaxPrice()) > 0) {
                    localBigDecimal17 = localGradeTradeResult.getMaxPrice();
                  }
                  if (localBigDecimal17.compareTo(localGradeTradeResult.getMinPrice()) < 0) {
                    localBigDecimal17 = localGradeTradeResult.getMinPrice();
                  }
                }
                else
                {
                  localBigDecimal17 = localBigDecimal9;
                }
                localGradeTradeResult.setAvgPrice(localBigDecimal17);
                localArrayList1.add(localGradeTradeResult);
              }
            }
            localBigDecimal15 = localBigDecimal5;
            localBigDecimal16 = localBigDecimal2;
            if (localBigDecimal16.compareTo(new BigDecimal(0)) <= 0) {
              localBigDecimal16 = new BigDecimal(1);
            }
            localBigDecimal17 = localBigDecimal15.divide(localBigDecimal16, 0, 4);
            if (localBigDecimal3 != localBigDecimal4)
            {
              if (localBigDecimal17.compareTo(localBigDecimal3) > 0) {
                localBigDecimal17 = localBigDecimal3;
              }
              if (localBigDecimal17.compareTo(localBigDecimal4) < 0) {
                localBigDecimal17 = localBigDecimal4;
              }
            }
            else
            {
              localBigDecimal17 = localBigDecimal3;
            }
            localTradeResult.setAvgPrice(localBigDecimal17);
            if (localHashMap1.containsKey(str4))
            {
              Integer localInteger3 = (Integer)localHashMap1.get(str4);
              if (!"".equals(arrayOfObject[localInteger3.intValue()][6])) {
                arrayOfObject[localInteger3.intValue()][6] = ManaUtil.splitSameGrade(arrayOfObject[localInteger3.intValue()][6] + "/" + localTradeResult.getGrade());
              } else {
                arrayOfObject[localInteger3.intValue()][6] = ManaUtil.splitSameGrade(localTradeResult.getGrade());
              }
            }
            if (localBigDecimal1.compareTo(new BigDecimal(0)) > 0) {
              localTradeResult.setTradeRatio(ManaUtil.accuracyNum(localBigDecimal2.divide(localBigDecimal1, 4, 4).multiply(new BigDecimal(100)), ".##"));
            } else {
              localTradeResult.setTradeRatio(new BigDecimal(0.0D));
            }
            localTradeResult.setGradeDetail(localArrayList1);
            localArrayList2.add(localTradeResult);
          }
          i2++;
        }
        localResultSet3.close();
        localDBBean.closeStmt();
        if (localArrayList2.size() > 0)
        {
          ((XJTradeResult)localObject6).setTradeResult(localArrayList2);
          if (((BigDecimal)localObject4).compareTo(new BigDecimal(0)) > 0)
          {
            ((XJTradeResult)localObject6).setXJPlanAmount((BigDecimal)localObject4);
            ((XJTradeResult)localObject6).setXJTradeAmount((BigDecimal)localObject5);
            ((XJTradeResult)localObject6).setXJTradeRatio(ManaUtil.accuracyNum(((BigDecimal)localObject5).divide((BigDecimal)localObject4, 4, 4).multiply(new BigDecimal(100)), ".##"));
            ((XJTradeResult)localObject6).setXJTradeMoney(localBigDecimal18);
          }
          arrayOfArrayList1[0].add(localObject6);
        }
      }
      localResultSet1.close();
      localDBBean.close();
      if (localBigDecimal12.compareTo(new BigDecimal(0)) > 0)
      {
        localObject4 = new HJTradeResult();
        ((HJTradeResult)localObject4).setHJPlanAmount(localBigDecimal12);
        ((HJTradeResult)localObject4).setHJTradeAmount(localBigDecimal13);
        ((HJTradeResult)localObject4).setHJTradeRatio(ManaUtil.accuracyNum(localBigDecimal13.divide(localBigDecimal12, 4, 4).multiply(new BigDecimal(100)), ".##"));
        ((HJTradeResult)localObject4).setHJTradeMoney(localBigDecimal14);
        arrayOfArrayList1[1].add(localObject4);
      }
      for (int n = 0; n < arrayOfObject.length; n++) {
        if (new BigDecimal(arrayOfObject[n][1].toString()).compareTo(new BigDecimal(0)) > 0)
        {
          localObject5 = new VariTradeResult();
          ((VariTradeResult)localObject5).setVari(arrayOfObject[n][0].toString());
          ((VariTradeResult)localObject5).setVPlanAmount(new BigDecimal(arrayOfObject[n][1].toString()));
          ((VariTradeResult)localObject5).setVTradeAmount(new BigDecimal(arrayOfObject[n][2].toString()));
          localBigDecimal18 = new BigDecimal(arrayOfObject[n][2].toString());
          if (localBigDecimal18.compareTo(new BigDecimal(0)) <= 0) {
            localBigDecimal18 = new BigDecimal(1);
          }
          localObject6 = new BigDecimal(arrayOfObject[n][3].toString()).divide(localBigDecimal18, 0, 4);
          if (((BigDecimal)localObject6).compareTo(new BigDecimal(arrayOfObject[n][4].toString())) > 0) {
            localObject6 = new BigDecimal(arrayOfObject[n][4].toString());
          }
          if (((BigDecimal)localObject6).compareTo(new BigDecimal(arrayOfObject[n][5].toString())) < 0) {
            localObject6 = new BigDecimal(arrayOfObject[n][5].toString());
          }
          ((VariTradeResult)localObject5).setVAvgPrice((BigDecimal)localObject6);
          ((VariTradeResult)localObject5).setVTradeRatio(ManaUtil.accuracyNum(new BigDecimal(arrayOfObject[n][2].toString()).divide(new BigDecimal(arrayOfObject[n][1].toString()), 4, 4).multiply(new BigDecimal(100)), ".##"));
          ((VariTradeResult)localObject5).setVTradeMoney(new BigDecimal(arrayOfObject[n][3].toString()));
          ((VariTradeResult)localObject5).setGrade(arrayOfObject[n][6].toString());
          arrayOfString6 = (String[][])arrayOfObject[n][7];
          localArrayList1 = new ArrayList();
          for (int i1 = 0; i1 < arrayOfString6.length; i1++)
          {
            localObject7 = new GradeTradeResult();
            ((GradeTradeResult)localObject7).setGrade(arrayOfString6[i1][0]);
            if (new BigDecimal(arrayOfString6[i1][1]).compareTo(new BigDecimal(0)) > 0)
            {
              ((GradeTradeResult)localObject7).setPlanAmount(new BigDecimal(arrayOfString6[i1][1]));
              ((GradeTradeResult)localObject7).setTradeAmount(new BigDecimal(arrayOfString6[i1][2]));
              ((GradeTradeResult)localObject7).setTradeMoney(new BigDecimal(arrayOfString6[i1][3]));
              ((GradeTradeResult)localObject7).setMaxPrice(new BigDecimal(arrayOfString6[i1][4]));
              ((GradeTradeResult)localObject7).setMinPrice(new BigDecimal(arrayOfString6[i1][5]));
              localBigDecimal15 = ((GradeTradeResult)localObject7).getTradeMoney();
              localBigDecimal16 = ((GradeTradeResult)localObject7).getTradeAmount();
              if (localBigDecimal16.compareTo(new BigDecimal(0)) <= 0) {
                localBigDecimal16 = new BigDecimal(1);
              }
              localBigDecimal17 = localBigDecimal15.divide(localBigDecimal16, 0, 4);
              if (((GradeTradeResult)localObject7).getMaxPrice() != ((GradeTradeResult)localObject7).getMinPrice())
              {
                if (localBigDecimal17.compareTo(((GradeTradeResult)localObject7).getMaxPrice()) > 0) {
                  localBigDecimal17 = ((GradeTradeResult)localObject7).getMaxPrice();
                }
                if (localBigDecimal17.compareTo(((GradeTradeResult)localObject7).getMinPrice()) < 0) {
                  localBigDecimal17 = ((GradeTradeResult)localObject7).getMinPrice();
                }
              }
              else
              {
                localBigDecimal17 = localBigDecimal9;
              }
              ((GradeTradeResult)localObject7).setAvgPrice(localBigDecimal17);
              localArrayList1.add(localObject7);
            }
          }
          ((VariTradeResult)localObject5).setGradeArray(localArrayList1);
          arrayOfArrayList1[2].add(localObject5);
        }
      }
      ArrayList[] arrayOfArrayList2 = arrayOfArrayList1;
      return arrayOfArrayList2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer1 = null;
      return localStringBuffer1;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException10) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException11) {}
      try
      {
        if (localResultSet3 != null) {
          localResultSet3.close();
        }
      }
      catch (Exception localException12) {}
      try
      {
        if (localResultSet4 != null) {
          localResultSet4.close();
        }
      }
      catch (Exception localException13) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList[] getProYearTradeResult(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    ResultSet localResultSet3 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList[] arrayOfArrayList1 = new ArrayList[3];
      arrayOfArrayList1[0] = new ArrayList();
      arrayOfArrayList1[1] = new ArrayList();
      arrayOfArrayList1[2] = new ArrayList();
      localStringBuffer1 = new StringBuffer();
      int i = 0;
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = null;
      Object localObject1 = null;
      Object localObject2 = null;
      Object localObject3 = null;
      localStringBuffer1.append("select count(*) as v from (select distinct t2.str1,t2.str5 from commodity t1,commext t2,");
      localStringBuffer1.append("" + paramString5 + " t3 where t1.id=t2.commid and t3.commodityid=t1.id" + paramString2 + ")");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      if (localResultSet1.next()) {
        i = localResultSet1.getInt("v");
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      String[][] arrayOfString = new String[i][7];
      localStringBuffer1 = new StringBuffer();
      localStringBuffer1.append("select distinct t2.str1,t2.str5 from commodity t1,commext t2," + paramString5 + " t3 where t1.id=");
      localStringBuffer1.append("t2.commid and t3.commodityid=t1.id" + paramString2);
      int j = 0;
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      while (localResultSet1.next())
      {
        localHashMap1.put(localResultSet1.getString("str5") + "年" + localResultSet1.getString("str1"), new Integer(j));
        arrayOfString[j][0] = (localResultSet1.getString("str5") + "年" + localResultSet1.getString("str1"));
        arrayOfString[j][1] = "0";
        arrayOfString[j][2] = "0";
        arrayOfString[j][3] = "0";
        arrayOfString[j][4] = "0";
        arrayOfString[j][5] = "0";
        arrayOfString[j][6] = "";
        j++;
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      localStringBuffer1 = new StringBuffer();
      localStringBuffer1.append("select value,name from dictable where type=" + paramString4 + "");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      BigDecimal localBigDecimal3 = new BigDecimal(0);
      BigDecimal localBigDecimal4 = new BigDecimal(0);
      BigDecimal localBigDecimal5 = new BigDecimal(0);
      BigDecimal localBigDecimal6 = new BigDecimal(0);
      BigDecimal localBigDecimal7 = new BigDecimal(0);
      BigDecimal localBigDecimal8 = new BigDecimal(0);
      BigDecimal localBigDecimal9 = null;
      BigDecimal localBigDecimal10 = null;
      BigDecimal localBigDecimal11 = null;
      Object localObject4 = null;
      Object localObject5;
      Object localObject6;
      BigDecimal localBigDecimal12;
      Object localObject7;
      while (localResultSet1.next())
      {
        localObject5 = new BigDecimal(0);
        localObject6 = new BigDecimal(0);
        localBigDecimal12 = new BigDecimal(0);
        localObject7 = new XJTradeResult();
        localHashMap2 = new HashMap();
        String str1 = localResultSet1.getString("name");
        String str2 = localResultSet1.getString("value");
        StringBuffer localStringBuffer2 = new StringBuffer();
        localStringBuffer2.append("select sum(amount) as planamount,str1,str5 from( select " + paramString7 + " ");
        localStringBuffer2.append("t1.*,t2.str1,t2.str5 from commodity t1,commext t2," + paramString5 + " t3 ");
        localStringBuffer2.append("where t1.id=t2.commid and t3.commodityid=t1.id and t2.str6='" + str2 + "'");
        localStringBuffer2.append(" " + paramString2 + ") group by str1,str5");
        localResultSet3 = localDBBean.executeQuery(localStringBuffer2.toString());
        int m = 0;
        ArrayList localArrayList = new ArrayList();
        while (localResultSet3.next())
        {
          if (m == 0) {
            ((XJTradeResult)localObject7).setProName(str1);
          }
          TradeResult localTradeResult = new TradeResult();
          localBigDecimal1 = ManaUtil.disBD(localResultSet3.getBigDecimal("planamount")).divide(new BigDecimal(10000), 2, 4);
          localTradeResult.setPlanAmount(localBigDecimal1);
          localObject5 = ((BigDecimal)localObject5).add(localBigDecimal1);
          localBigDecimal6 = localBigDecimal6.add(localBigDecimal1);
          String str3 = localResultSet3.getString("str1");
          String str4 = localResultSet3.getString("str5");
          localTradeResult.setVari(str4 + "年" + str3);
          if (localHashMap1.containsKey(str4 + "年" + str3))
          {
            localObject8 = (Integer)localHashMap1.get(str4 + "年" + str3);
            arrayOfString[localObject8.intValue()][1] = String.valueOf(new BigDecimal(arrayOfString[localObject8.intValue()][1]).add(localBigDecimal1));
          }
          Object localObject8 = new StringBuffer();
          ((StringBuffer)localObject8).append("select sum(t1.amount) as su,max(t1.price) as ma,min(t1.price) as mi");
          ((StringBuffer)localObject8).append(",sum(t1.price*t1.amount)/sum(t1.amount) as av,sum(t1.price*t1.amount)");
          ((StringBuffer)localObject8).append(" as trademoney from " + paramString6 + " t1,commodity t2,commext t3 where t2.id=");
          ((StringBuffer)localObject8).append("t3.commid and t1.commodityid=t2.id and t3.str6='" + str2 + "' and t3.str1='" + str3 + "'");
          ((StringBuffer)localObject8).append(" and t3.str5='" + str4 + "' " + paramString3 + "");
          localResultSet2 = localDBBean.executeQuery(((StringBuffer)localObject8).toString());
          Integer localInteger;
          if (localResultSet2.next())
          {
            localBigDecimal2 = ManaUtil.disBD(localResultSet2.getBigDecimal("su")).divide(new BigDecimal(10000), 2, 4);
            localTradeResult.setTradeAmount(localBigDecimal2);
            localObject6 = ((BigDecimal)localObject6).add(localBigDecimal2);
            localBigDecimal7 = localBigDecimal7.add(localBigDecimal2);
            localBigDecimal3 = ManaUtil.disBD(localResultSet2.getBigDecimal("ma"));
            localTradeResult.setMaxPrice(localBigDecimal3);
            localBigDecimal4 = ManaUtil.disBD(localResultSet2.getBigDecimal("mi"));
            localTradeResult.setMinPrice(localBigDecimal4);
            localBigDecimal5 = ManaUtil.disBD(localResultSet2.getBigDecimal("trademoney")).divide(new BigDecimal(10000), 2, 4);
            localTradeResult.setTradeMoney(localBigDecimal5);
            localBigDecimal12 = localBigDecimal12.add(localBigDecimal5);
            localBigDecimal8 = localBigDecimal8.add(localBigDecimal5);
            if (localHashMap1.containsKey(str4 + "年" + str3))
            {
              localInteger = (Integer)localHashMap1.get(str4 + "年" + str3);
              arrayOfString[localInteger.intValue()][2] = String.valueOf(new BigDecimal(arrayOfString[localInteger.intValue()][2]).add(localBigDecimal2));
            }
            if (localHashMap1.containsKey(str4 + "年" + str3))
            {
              localInteger = (Integer)localHashMap1.get(str4 + "年" + str3);
              arrayOfString[localInteger.intValue()][3] = String.valueOf(new BigDecimal(arrayOfString[localInteger.intValue()][3]).add(localBigDecimal5));
            }
            if (localHashMap1.containsKey(str4 + "年" + str3))
            {
              localInteger = (Integer)localHashMap1.get(str4 + "年" + str3);
              if (new Double(arrayOfString[localInteger.intValue()][4]).doubleValue() == 0.0D) {
                arrayOfString[localInteger.intValue()][4] = String.valueOf(localBigDecimal3);
              }
              if (localBigDecimal3.compareTo(new BigDecimal(arrayOfString[localInteger.intValue()][4])) > 0) {
                arrayOfString[localInteger.intValue()][4] = String.valueOf(localBigDecimal3);
              }
            }
            if (localHashMap1.containsKey(str4 + "年" + str3))
            {
              localInteger = (Integer)localHashMap1.get(str4 + "年" + str3);
              if (new Double(arrayOfString[localInteger.intValue()][5]).doubleValue() == 0.0D) {
                arrayOfString[localInteger.intValue()][5] = String.valueOf(localBigDecimal4);
              }
              if ((localBigDecimal4.compareTo(new BigDecimal(arrayOfString[localInteger.intValue()][5])) < 0) && (localBigDecimal4.doubleValue() > 0.0D)) {
                arrayOfString[localInteger.intValue()][5] = String.valueOf(localBigDecimal4);
              }
            }
            localBigDecimal9 = localBigDecimal5;
            localBigDecimal10 = localBigDecimal2;
            if (localBigDecimal10.compareTo(new BigDecimal(0)) <= 0) {
              localBigDecimal10 = new BigDecimal(1);
            }
            localBigDecimal11 = localBigDecimal9.divide(localBigDecimal10, 0, 4);
            if (localBigDecimal3 != localBigDecimal4)
            {
              if (localBigDecimal11.compareTo(localBigDecimal3) > 0) {
                localBigDecimal11 = localBigDecimal3;
              }
              if (localBigDecimal11.compareTo(localBigDecimal4) < 0) {
                localBigDecimal11 = localBigDecimal4;
              }
            }
            else
            {
              localBigDecimal11 = localBigDecimal3;
            }
            localTradeResult.setAvgPrice(localBigDecimal11);
          }
          localResultSet2.close();
          localDBBean.closeStmt();
          localObject8 = new StringBuffer();
          ((StringBuffer)localObject8).append("select str4 v1 from( select " + paramString7 + " ");
          ((StringBuffer)localObject8).append("t1.*,t2.str1,t2.str4 from commodity t1,commext t2," + paramString5 + " t3 ");
          ((StringBuffer)localObject8).append("where t1.id=t2.commid and t3.commodityid=t1.id and t2.str6='" + str2 + "'");
          ((StringBuffer)localObject8).append(" and t2.str1='" + str3 + "' and t2.str5='" + str4 + "' " + paramString2 + ")");
          localResultSet2 = localDBBean.executeQuery(((StringBuffer)localObject8).toString());
          while (localResultSet2.next()) {
            if (localResultSet2.getString(1) != null) {
              localTradeResult.setGrade(ManaUtil.splitSameGrade(localResultSet2.getString(1)));
            }
          }
          localResultSet2.close();
          localDBBean.closeStmt();
          if (localHashMap1.containsKey(str4 + "年" + str3))
          {
            localInteger = (Integer)localHashMap1.get(str4 + "年" + str3);
            if (!"".equals(arrayOfString[localInteger.intValue()][6])) {
              arrayOfString[localInteger.intValue()][6] = ManaUtil.splitSameGrade(arrayOfString[localInteger.intValue()][6] + "/" + localTradeResult.getGrade());
            } else {
              arrayOfString[localInteger.intValue()][6] = ManaUtil.splitSameGrade(localTradeResult.getGrade());
            }
          }
          if (localBigDecimal1.compareTo(new BigDecimal(0)) > 0) {
            localTradeResult.setTradeRatio(ManaUtil.accuracyNum(localBigDecimal2.divide(localBigDecimal1, 4, 4).multiply(new BigDecimal(100)), ".##"));
          } else {
            localTradeResult.setTradeRatio(new BigDecimal(0.0D));
          }
          localArrayList.add(localTradeResult);
          m++;
        }
        localResultSet3.close();
        localDBBean.closeStmt();
        if (localArrayList.size() > 0)
        {
          ((XJTradeResult)localObject7).setTradeResult(localArrayList);
          if (((BigDecimal)localObject5).compareTo(new BigDecimal(0)) > 0)
          {
            ((XJTradeResult)localObject7).setXJPlanAmount((BigDecimal)localObject5);
            ((XJTradeResult)localObject7).setXJTradeAmount((BigDecimal)localObject6);
            ((XJTradeResult)localObject7).setXJTradeRatio(ManaUtil.accuracyNum(((BigDecimal)localObject6).divide((BigDecimal)localObject5, 4, 4).multiply(new BigDecimal(100)), ".##"));
            ((XJTradeResult)localObject7).setXJTradeMoney(localBigDecimal12);
          }
          arrayOfArrayList1[0].add(localObject7);
        }
      }
      localResultSet1.close();
      localDBBean.close();
      if (localBigDecimal6.compareTo(new BigDecimal(0)) > 0)
      {
        localObject5 = new HJTradeResult();
        ((HJTradeResult)localObject5).setHJPlanAmount(localBigDecimal6);
        ((HJTradeResult)localObject5).setHJTradeAmount(localBigDecimal7);
        ((HJTradeResult)localObject5).setHJTradeRatio(ManaUtil.accuracyNum(localBigDecimal7.divide(localBigDecimal6, 4, 4).multiply(new BigDecimal(100)), ".##"));
        ((HJTradeResult)localObject5).setHJTradeMoney(localBigDecimal8);
        arrayOfArrayList1[1].add(localObject5);
      }
      for (int k = 0; k < arrayOfString.length; k++) {
        if (new BigDecimal(arrayOfString[k][1]).compareTo(new BigDecimal(0)) > 0)
        {
          localObject6 = new VariTradeResult();
          ((VariTradeResult)localObject6).setVari(arrayOfString[k][0]);
          ((VariTradeResult)localObject6).setVPlanAmount(new BigDecimal(arrayOfString[k][1]));
          ((VariTradeResult)localObject6).setVTradeAmount(new BigDecimal(arrayOfString[k][2]));
          localBigDecimal12 = new BigDecimal(arrayOfString[k][2]);
          if (localBigDecimal12.compareTo(new BigDecimal(0)) <= 0) {
            localBigDecimal12 = new BigDecimal(1);
          }
          localObject7 = new BigDecimal(arrayOfString[k][3]).divide(localBigDecimal12, 0, 4);
          if (((BigDecimal)localObject7).compareTo(new BigDecimal(arrayOfString[k][4])) > 0) {
            localObject7 = new BigDecimal(arrayOfString[k][4]);
          }
          if (((BigDecimal)localObject7).compareTo(new BigDecimal(arrayOfString[k][5])) < 0) {
            localObject7 = new BigDecimal(arrayOfString[k][5]);
          }
          ((VariTradeResult)localObject6).setVAvgPrice((BigDecimal)localObject7);
          ((VariTradeResult)localObject6).setVTradeRatio(ManaUtil.accuracyNum(new BigDecimal(arrayOfString[k][2]).divide(new BigDecimal(arrayOfString[k][1]), 4, 4).multiply(new BigDecimal(100)), ".##"));
          ((VariTradeResult)localObject6).setVTradeMoney(new BigDecimal(arrayOfString[k][3]));
          ((VariTradeResult)localObject6).setGrade(arrayOfString[k][6]);
          arrayOfArrayList1[2].add(localObject6);
        }
      }
      ArrayList[] arrayOfArrayList2 = arrayOfArrayList1;
      return arrayOfArrayList2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer1 = null;
      return localStringBuffer1;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException8) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException9) {}
      try
      {
        if (localResultSet3 != null) {
          localResultSet3.close();
        }
      }
      catch (Exception localException10) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList[] getZJProYearTradeResult(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    ResultSet localResultSet3 = null;
    ResultSet localResultSet4 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList[] arrayOfArrayList1 = new ArrayList[3];
      arrayOfArrayList1[0] = new ArrayList();
      arrayOfArrayList1[1] = new ArrayList();
      arrayOfArrayList1[2] = new ArrayList();
      localStringBuffer1 = new StringBuffer();
      int i = 0;
      HashMap localHashMap1 = new HashMap();
      HashMap localHashMap2 = null;
      Object localObject1 = null;
      Object localObject2 = null;
      Object localObject3 = null;
      String str1 = null;
      int j = 0;
      String str2 = null;
      localStringBuffer1.append("select count(*) v from (select distinct t2.str1,t2.str5 from commodity t1,commext t2,");
      localStringBuffer1.append("" + paramString5 + " t3 where t1.id=t2.commid and t3.commodityid=t1.id" + paramString2 + ")");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      if (localResultSet1.next()) {
        i = localResultSet1.getInt("v");
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      String[][] arrayOfString = new String[i][7];
      localStringBuffer1 = new StringBuffer();
      localStringBuffer1.append("select distinct t2.str1,t2.str5 from commodity t1,commext t2," + paramString5 + " t3 where t1.id=");
      localStringBuffer1.append("t2.commid and t3.commodityid=t1.id" + paramString2);
      int k = 0;
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      while (localResultSet1.next())
      {
        localHashMap1.put(localResultSet1.getString("str5") + "年" + localResultSet1.getString("str1"), new Integer(k));
        arrayOfString[k][0] = (localResultSet1.getString("str5") + "年" + localResultSet1.getString("str1"));
        arrayOfString[k][1] = "0";
        arrayOfString[k][2] = "0";
        arrayOfString[k][3] = "0";
        arrayOfString[k][4] = "0";
        arrayOfString[k][5] = "0";
        arrayOfString[k][6] = "";
        k++;
      }
      localResultSet1.close();
      localDBBean.closeStmt();
      localStringBuffer1 = new StringBuffer();
      localStringBuffer1.append("select value,name from dictable where type=" + paramString4 + "");
      localResultSet1 = localDBBean.executeQuery(localStringBuffer1.toString());
      BigDecimal localBigDecimal1 = new BigDecimal(0);
      BigDecimal localBigDecimal2 = new BigDecimal(0);
      BigDecimal localBigDecimal3 = new BigDecimal(0);
      BigDecimal localBigDecimal4 = new BigDecimal(0);
      BigDecimal localBigDecimal5 = new BigDecimal(0);
      BigDecimal localBigDecimal6 = new BigDecimal(0);
      BigDecimal localBigDecimal7 = new BigDecimal(0);
      BigDecimal localBigDecimal8 = new BigDecimal(0);
      BigDecimal localBigDecimal9 = null;
      BigDecimal localBigDecimal10 = null;
      BigDecimal localBigDecimal11 = null;
      Object localObject4;
      Object localObject5;
      BigDecimal localBigDecimal12;
      Object localObject6;
      while (localResultSet1.next())
      {
        localObject4 = new BigDecimal(0);
        localObject5 = new BigDecimal(0);
        localBigDecimal12 = new BigDecimal(0);
        localObject6 = new XJTradeResult();
        localHashMap2 = new HashMap();
        String str3 = localResultSet1.getString("name");
        String str4 = localResultSet1.getString("value");
        StringBuffer localStringBuffer2 = new StringBuffer();
        StringBuffer localStringBuffer3 = new StringBuffer();
        localStringBuffer2.append("select distinct t2.str1,t2.str5 from commodity t1,commext t2," + paramString5 + " t3 where");
        localStringBuffer2.append(" t1.id=t2.commid and t3.commodityid=t1.id and t2.str6='" + str4 + "' " + paramString2 + "");
        localResultSet3 = localDBBean.executeQuery(localStringBuffer2.toString());
        int n = 0;
        ArrayList localArrayList = new ArrayList();
        while (localResultSet3.next())
        {
          if (n == 0) {
            ((XJTradeResult)localObject6).setProName(str3);
          }
          TradeResult localTradeResult = new TradeResult();
          String str5 = localResultSet3.getString("str1");
          str2 = localResultSet3.getString("str5");
          localTradeResult.setVari(str2 + "年" + str5);
          j = 0;
          str1 = "";
          localBigDecimal1 = new BigDecimal(0);
          localStringBuffer2 = new StringBuffer();
          localStringBuffer2.append("select partitionid from syspartition where validflag=1");
          localResultSet2 = localDBBean.executeQuery(localStringBuffer2.toString());
          while (localResultSet2.next())
          {
            j = localResultSet2.getInt(1);
            str1 = " and t3.tradepartition=" + j;
            localStringBuffer2 = new StringBuffer();
            localStringBuffer2.append("select sum(amount) as planamount from( select " + paramString7 + " t1.* from commodity");
            localStringBuffer2.append(" t1,commext t2," + paramString5 + " t3 where t1.id=t2.commid and t3.commodityid=t1.id");
            localStringBuffer2.append(" and t2.str6='" + str4 + "' and t2.str1='" + str5 + "' and t2.str5='" + str2 + "' ");
            localStringBuffer2.append("" + str1 + " " + paramString2 + ")");
            localResultSet4 = localDBBean.executeQuery(localStringBuffer2.toString());
            if (localResultSet4.next()) {
              localBigDecimal1 = localBigDecimal1.add(ManaUtil.disBD(localResultSet4.getBigDecimal("planamount")).divide(new BigDecimal(10000), 2, 4));
            }
            localResultSet4.close();
            localDBBean.closeStmt();
          }
          localResultSet2.close();
          localDBBean.closeStmt();
          if (localBigDecimal1.compareTo(new BigDecimal(0)) > 0)
          {
            localTradeResult.setPlanAmount(localBigDecimal1);
            localObject4 = ((BigDecimal)localObject4).add(localBigDecimal1);
            localBigDecimal6 = localBigDecimal6.add(localBigDecimal1);
            Integer localInteger;
            if (localHashMap1.containsKey(str2 + "年" + str5))
            {
              localInteger = (Integer)localHashMap1.get(str2 + "年" + str5);
              arrayOfString[localInteger.intValue()][1] = String.valueOf(new BigDecimal(arrayOfString[localInteger.intValue()][1]).add(localBigDecimal1));
            }
            j = 0;
            str1 = "";
            localBigDecimal2 = new BigDecimal(0);
            localBigDecimal5 = new BigDecimal(0);
            localBigDecimal11 = new BigDecimal(0);
            localStringBuffer2 = new StringBuffer();
            localStringBuffer2.append("select partitionid from syspartition where validflag=1");
            localResultSet2 = localDBBean.executeQuery(localStringBuffer2.toString());
            while (localResultSet2.next())
            {
              j = localResultSet2.getInt(1);
              str1 = " and t1.tradepartition=" + j;
              localStringBuffer3 = new StringBuffer();
              localStringBuffer3.append("select sum(t1.amount) as su,sum(t1.price*t1.amount)/sum(t1.amount) as av");
              localStringBuffer3.append(",sum(t1.price*t1.amount) as trademoney from " + paramString6 + " t1,commodity t2,");
              localStringBuffer3.append("commext t3 where t2.id=t3.commid and t1.commodityid=t2.id and t3.str6=");
              localStringBuffer3.append("'" + str4 + "' and t3.str1='" + str5 + "' and t3.str5='" + str2 + "' " + paramString3 + "");
              localStringBuffer3.append(" " + str1 + "");
              localResultSet4 = localDBBean.executeQuery(localStringBuffer3.toString());
              if (localResultSet4.next())
              {
                localBigDecimal2 = localBigDecimal2.add(ManaUtil.disBD(localResultSet4.getBigDecimal("su")).divide(new BigDecimal(10000), 2, 4));
                localBigDecimal5 = localBigDecimal5.add(ManaUtil.disBD(localResultSet4.getBigDecimal("trademoney")).divide(new BigDecimal(10000), 2, 4));
              }
            }
            localResultSet2.close();
            localDBBean.closeStmt();
            localTradeResult.setTradeAmount(localBigDecimal2);
            localObject5 = ((BigDecimal)localObject5).add(localBigDecimal2);
            localBigDecimal7 = localBigDecimal7.add(localBigDecimal2);
            localTradeResult.setTradeMoney(localBigDecimal5);
            localBigDecimal12 = localBigDecimal12.add(localBigDecimal5);
            localBigDecimal8 = localBigDecimal8.add(localBigDecimal5);
            if (localHashMap1.containsKey(str2 + "年" + str5))
            {
              localInteger = (Integer)localHashMap1.get(str2 + "年" + str5);
              arrayOfString[localInteger.intValue()][2] = String.valueOf(new BigDecimal(arrayOfString[localInteger.intValue()][2]).add(localBigDecimal2));
            }
            if (localHashMap1.containsKey(str2 + "年" + str5))
            {
              localInteger = (Integer)localHashMap1.get(str2 + "年" + str5);
              arrayOfString[localInteger.intValue()][3] = String.valueOf(new BigDecimal(arrayOfString[localInteger.intValue()][3]).add(localBigDecimal5));
            }
            localBigDecimal9 = localBigDecimal5;
            localBigDecimal10 = localBigDecimal2;
            if (localBigDecimal10.compareTo(new BigDecimal(0)) <= 0) {
              localBigDecimal10 = new BigDecimal(1);
            }
            localBigDecimal11 = localBigDecimal9.divide(localBigDecimal10, 0, 4);
            localStringBuffer3 = new StringBuffer();
            localStringBuffer3.append("select max(t1.price) as ma,min(t1.price) as mi from " + paramString6 + " t1,commodity t2");
            localStringBuffer3.append(",commext t3 where t2.id=t3.commid and t1.commodityid=t2.id and t3.str6=");
            localStringBuffer3.append("'" + str4 + "' and t3.str1='" + str5 + "' and t3.str5='" + str2 + "' and ");
            localStringBuffer3.append("t1.tradepartition in (select partitionid from syspartition where validflag=1)");
            localStringBuffer3.append(" " + paramString3 + "");
            localResultSet4 = localDBBean.executeQuery(localStringBuffer3.toString());
            if (localResultSet4.next())
            {
              localBigDecimal3 = ManaUtil.disBD(localResultSet4.getBigDecimal("ma"));
              localTradeResult.setMaxPrice(localBigDecimal3);
              localBigDecimal4 = ManaUtil.disBD(localResultSet4.getBigDecimal("mi"));
              localTradeResult.setMinPrice(localBigDecimal4);
              if (localHashMap1.containsKey(str2 + "年" + str5))
              {
                localInteger = (Integer)localHashMap1.get(str2 + "年" + str5);
                if (new Double(arrayOfString[localInteger.intValue()][4]).doubleValue() == 0.0D) {
                  arrayOfString[localInteger.intValue()][4] = String.valueOf(localBigDecimal3);
                }
                if (localBigDecimal3.compareTo(new BigDecimal(arrayOfString[localInteger.intValue()][4])) > 0) {
                  arrayOfString[localInteger.intValue()][4] = String.valueOf(localBigDecimal3);
                }
              }
              if (localHashMap1.containsKey(str2 + "年" + str5))
              {
                localInteger = (Integer)localHashMap1.get(str2 + "年" + str5);
                if (new Double(arrayOfString[localInteger.intValue()][5]).doubleValue() == 0.0D) {
                  arrayOfString[localInteger.intValue()][5] = String.valueOf(localBigDecimal4);
                }
                if ((localBigDecimal4.compareTo(new BigDecimal(arrayOfString[localInteger.intValue()][5])) < 0) && (localBigDecimal4.doubleValue() > 0.0D)) {
                  arrayOfString[localInteger.intValue()][5] = String.valueOf(localBigDecimal4);
                }
              }
            }
            localResultSet4.close();
            localDBBean.closeStmt();
            if (localBigDecimal3 != localBigDecimal4)
            {
              if (localBigDecimal11.compareTo(localBigDecimal3) > 0) {
                localBigDecimal11 = localBigDecimal3;
              }
              if (localBigDecimal11.compareTo(localBigDecimal4) < 0) {
                localBigDecimal11 = localBigDecimal4;
              }
            }
            else
            {
              localBigDecimal11 = localBigDecimal3;
            }
            localTradeResult.setAvgPrice(localBigDecimal11);
            localResultSet4.close();
            localDBBean.closeStmt();
            localStringBuffer3 = new StringBuffer();
            localStringBuffer3.append("select str4 v1 from( select " + paramString7 + " ");
            localStringBuffer3.append("t1.*,t2.str1,t2.str4 from commodity t1,commext t2," + paramString5 + " t3 ");
            localStringBuffer3.append("where t1.id=t2.commid and t3.commodityid=t1.id and t2.str6='" + str4 + "'");
            localStringBuffer3.append(" and t2.str1='" + str5 + "' and t2.str5='" + str2 + "' " + paramString2 + ")");
            localResultSet4 = localDBBean.executeQuery(localStringBuffer3.toString());
            while (localResultSet4.next()) {
              if (localResultSet4.getString(1) != null) {
                localTradeResult.setGrade(ManaUtil.splitSameGrade(localResultSet4.getString(1)));
              }
            }
            localResultSet4.close();
            localDBBean.closeStmt();
            if (localHashMap1.containsKey(str2 + "年" + str5))
            {
              localInteger = (Integer)localHashMap1.get(str2 + "年" + str5);
              if (!"".equals(arrayOfString[localInteger.intValue()][6])) {
                arrayOfString[localInteger.intValue()][6] = ManaUtil.splitSameGrade(arrayOfString[localInteger.intValue()][6] + "/" + localTradeResult.getGrade());
              } else {
                arrayOfString[localInteger.intValue()][6] = ManaUtil.splitSameGrade(localTradeResult.getGrade());
              }
            }
            if (localBigDecimal1.compareTo(new BigDecimal(0)) > 0) {
              localTradeResult.setTradeRatio(ManaUtil.accuracyNum(localBigDecimal2.divide(localBigDecimal1, 4, 4).multiply(new BigDecimal(100)), ".##"));
            } else {
              localTradeResult.setTradeRatio(new BigDecimal(0.0D));
            }
            localArrayList.add(localTradeResult);
          }
          n++;
        }
        localResultSet3.close();
        localDBBean.closeStmt();
        if (localArrayList.size() > 0)
        {
          ((XJTradeResult)localObject6).setTradeResult(localArrayList);
          if (((BigDecimal)localObject4).compareTo(new BigDecimal(0)) > 0)
          {
            ((XJTradeResult)localObject6).setXJPlanAmount((BigDecimal)localObject4);
            ((XJTradeResult)localObject6).setXJTradeAmount((BigDecimal)localObject5);
            ((XJTradeResult)localObject6).setXJTradeRatio(ManaUtil.accuracyNum(((BigDecimal)localObject5).divide((BigDecimal)localObject4, 4, 4).multiply(new BigDecimal(100)), ".##"));
            ((XJTradeResult)localObject6).setXJTradeMoney(localBigDecimal12);
          }
          arrayOfArrayList1[0].add(localObject6);
        }
      }
      localResultSet1.close();
      localDBBean.close();
      if (localBigDecimal6.compareTo(new BigDecimal(0)) > 0)
      {
        localObject4 = new HJTradeResult();
        ((HJTradeResult)localObject4).setHJPlanAmount(localBigDecimal6);
        ((HJTradeResult)localObject4).setHJTradeAmount(localBigDecimal7);
        ((HJTradeResult)localObject4).setHJTradeRatio(ManaUtil.accuracyNum(localBigDecimal7.divide(localBigDecimal6, 4, 4).multiply(new BigDecimal(100)), ".##"));
        ((HJTradeResult)localObject4).setHJTradeMoney(localBigDecimal8);
        arrayOfArrayList1[1].add(localObject4);
      }
      for (int m = 0; m < arrayOfString.length; m++) {
        if (new BigDecimal(arrayOfString[m][1]).compareTo(new BigDecimal(0)) > 0)
        {
          localObject5 = new VariTradeResult();
          ((VariTradeResult)localObject5).setVari(arrayOfString[m][0]);
          ((VariTradeResult)localObject5).setVPlanAmount(new BigDecimal(arrayOfString[m][1]));
          ((VariTradeResult)localObject5).setVTradeAmount(new BigDecimal(arrayOfString[m][2]));
          localBigDecimal12 = new BigDecimal(arrayOfString[m][2]);
          if (localBigDecimal12.compareTo(new BigDecimal(0)) <= 0) {
            localBigDecimal12 = new BigDecimal(1);
          }
          localObject6 = new BigDecimal(arrayOfString[m][3]).divide(localBigDecimal12, 0, 4);
          if (((BigDecimal)localObject6).compareTo(new BigDecimal(arrayOfString[m][4])) > 0) {
            localObject6 = new BigDecimal(arrayOfString[m][4]);
          }
          if (((BigDecimal)localObject6).compareTo(new BigDecimal(arrayOfString[m][5])) < 0) {
            localObject6 = new BigDecimal(arrayOfString[m][5]);
          }
          ((VariTradeResult)localObject5).setVAvgPrice((BigDecimal)localObject6);
          ((VariTradeResult)localObject5).setVTradeRatio(ManaUtil.accuracyNum(new BigDecimal(arrayOfString[m][2]).divide(new BigDecimal(arrayOfString[m][1]), 4, 4).multiply(new BigDecimal(100)), ".##"));
          ((VariTradeResult)localObject5).setVTradeMoney(new BigDecimal(arrayOfString[m][3]));
          ((VariTradeResult)localObject5).setGrade(arrayOfString[m][6]);
          arrayOfArrayList1[2].add(localObject5);
        }
      }
      ArrayList[] arrayOfArrayList2 = arrayOfArrayList1;
      return arrayOfArrayList2;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      StringBuffer localStringBuffer1 = null;
      return localStringBuffer1;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException10) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException11) {}
      try
      {
        if (localResultSet4 != null) {
          localResultSet4.close();
        }
      }
      catch (Exception localException12) {}
      try
      {
        if (localResultSet3 != null) {
          localResultSet3.close();
        }
      }
      catch (Exception localException13) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
  
  public static ArrayList getOutPlan(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    DBBean localDBBean = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    try
    {
      localDBBean = new DBBean(paramString1);
      ArrayList localArrayList1 = new ArrayList();
      localArrayList2 = null;
      StringBuffer localStringBuffer = new StringBuffer();
      int i = 0;
      String str1 = null;
      String str2 = null;
      String str3 = null;
      String str4 = "";
      BigDecimal localBigDecimal = null;
      localStringBuffer.append("select distinct c.str6 v1,c.str1 v2,c.str5 v3,(select name from dictable where");
      localStringBuffer.append(" type=2 and value=c.str6) v4 from " + paramString3 + " b,commodity c1,commExt c where ");
      localStringBuffer.append("b.commodityID=c1.id and c.commid=c1.id " + paramString2 + " order by c.str6");
      localResultSet2 = localDBBean.executeQuery(localStringBuffer.toString());
      while (localResultSet2.next())
      {
        localObject1 = new FinanceOut();
        localArrayList2 = new ArrayList();
        i = localResultSet2.getInt(1);
        str1 = localResultSet2.getString(4);
        str2 = localResultSet2.getString(3);
        str3 = localResultSet2.getString(2);
        str4 = "";
        localBigDecimal = new BigDecimal(0);
        localStringBuffer = new StringBuffer();
        localStringBuffer.append("select b.contractID v1,b.code v2,c.str2 v3,c.Str3 v4,c.Str4 v5,b.price v6,");
        localStringBuffer.append("(select name from tradeuserext where usercode=b.userid) v7,c.str1 v8,b.amount v9 ");
        localStringBuffer.append(",b.userid v10 from " + paramString3 + " b,commodity c1,commExt c where  b.commodityID=c1.id ");
        localStringBuffer.append("and c.commid=c1.id and c.str6='" + i + "' and c.str1='" + str3 + "' and c.str5='" + str2 + "'");
        localStringBuffer.append(" " + paramString2 + " order by b.code asc");
        localResultSet1 = localDBBean.executeQuery(localStringBuffer.toString());
        while (localResultSet1.next())
        {
          PlanDetail localPlanDetail = new PlanDetail();
          localPlanDetail.setContractId(localResultSet1.getString(1));
          localPlanDetail.setCode(localResultSet1.getString(2));
          localPlanDetail.setKudian(localResultSet1.getString(3));
          localPlanDetail.setStr3(localResultSet1.getString(4));
          localPlanDetail.setPrice(ManaUtil.disBD(localResultSet1.getBigDecimal(6)));
          localPlanDetail.setName(localResultSet1.getString(7));
          localPlanDetail.setVari(localResultSet1.getString(8));
          localPlanDetail.setAmount(ManaUtil.disBD(localResultSet1.getBigDecimal(9)));
          localPlanDetail.setUserCode(localResultSet1.getString(10));
          localBigDecimal = localBigDecimal.add(localPlanDetail.getAmount());
          localArrayList2.add(localPlanDetail);
        }
        localResultSet1.close();
        localDBBean.closeStmt();
        if (localArrayList2.size() > 0)
        {
          str4 = str1 + "(" + str2 + str3 + "成交量:" + localBigDecimal.toString() + ")";
          ((FinanceOut)localObject1).setProName(str1);
          ((FinanceOut)localObject1).setPlanDetail(localArrayList2);
          ((FinanceOut)localObject1).setProAmount(str4);
          localArrayList1.add(localObject1);
        }
      }
      localResultSet2.close();
      localDBBean.close();
      Object localObject1 = localArrayList1;
      return localObject1;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      ArrayList localArrayList2 = null;
      return localArrayList2;
    }
    finally
    {
      try
      {
        if (localResultSet1 != null) {
          localResultSet1.close();
        }
      }
      catch (Exception localException6) {}
      try
      {
        if (localResultSet2 != null) {
          localResultSet2.close();
        }
      }
      catch (Exception localException7) {}
      if (localDBBean != null) {
        localDBBean.close();
      }
    }
  }
}
