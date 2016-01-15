package gnnt.MEBS.finance.mgr.dao.impl;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.finance.mgr.dao.AccountDao;
import gnnt.MEBS.finance.mgr.model.Account;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository("accountDao")
public class AccountDaoImpl extends StandardDao
  implements AccountDao
{
  public List getLeafAccountByCode(String paramString)
  {
    String str = "select * from f_account where not exists(select * from f_account where code like '" + paramString + "%'" + " and AccountLevel>(select AccountLevel from f_account where code = '" + paramString + "')) and code = '" + paramString + "'";
    return queryBySql(str, new Account());
  }

  public List queryLedger1(String paramString1, String paramString2, String paramString3)
  {
    String str = "select * from (select b_date bdate, voucherNo, summary, a.creditcode accountCode, (select name from f_account where code = a.creditcode) accountName, amount debitAmount, 0 creditAmount, contractNo from f_accountbook a where DebitCode = '" + paramString1 + "' " + "and b_date >= to_date('" + paramString2 + "','yyyy-MM-dd') " + "and b_date <= to_date('" + paramString3 + "','yyyy-MM-dd') " + "union " + "select b_date bdate, " + "voucherNo, " + "summary, " + "b.debitcode accountCode, " + "(select name from f_account where code = b.debitcode) accountName, " + "0 debitAmount, " + "amount creditAmount, " + "contractNo " + "from f_accountbook b " + "where CreditCode = '" + paramString1 + "' " + "and b_date >= to_date('" + paramString2 + "','yyyy-MM-dd') " + "and b_date <= to_date('" + paramString3 + "','yyyy-MM-dd')) queryView " + "order by voucherNo";
    return queryBySql(str);
  }

  public List queryLedger2(String paramString1, String paramString2, String paramString3)
  {
    String str = "select * from (select b_date bdate, voucherNo, summary, a.creditcode accountCode, (select name from f_account where code = a.creditcode) accountName, amount debitAmount, 0 creditAmount, contractNo from f_accountbook a where DebitCode in (select a.code from f_account a where a.accountlevel > (select t.accountlevel from f_account t where t.code = '" + paramString1 + "') " + "and a.code like '" + paramString1 + "%') " + "and b_date >= to_date('" + paramString2 + "','yyyy-MM-dd') " + "and b_date <= to_date('" + paramString3 + "','yyyy-MM-dd') " + "union " + "select b_date bdate, " + "voucherNo, " + "summary, " + "b.debitcode accountCode, " + "(select name from f_account where code = b.debitcode) accountName, " + "0 debitAmount, " + "amount creditAmount, " + "contractNo " + "from f_accountbook b " + "where CreditCode in (select a.code " + "from f_account a " + "where a.accountlevel > " + "(select t.accountlevel " + "from f_account t " + "where t.code = '" + paramString1 + "') " + "and a.code like '" + paramString1 + "%') " + "and b_date >= to_date('" + paramString2 + "','yyyy-MM-dd') " + "and b_date <= to_date('" + paramString3 + "','yyyy-MM-dd') " + ") queryView " + "order by voucherNo";
    return queryBySql(str);
  }

  public BigDecimal queryDailyBalance(String paramString1, String paramString2, boolean paramBoolean)
  {
    String str1 = "";
    String str2 = "select * from (select a.b_date,a.accountCode,b.name,a.lastDayBalance,a.DebitAmount,a.CreditAmount,a.todayBalance,b.accountLevel,b.dCFlag from f_DailyBalance a,f_Account b where a.accountCode=b.Code) db where 1=1 ";
    String str3 = "";
    str3 = " and accountCode = '" + paramString1 + "' and b_date =  to_date('" + paramString2 + "','yyyy-MM-dd')";
    str1 = str2 + str3;
    List localList = queryBySql(str1);
    if ((localList != null) && (localList.size() > 0))
      return (BigDecimal)((Map)localList.get(0)).get("LASTDAYBALANCE");
    if (paramBoolean)
    {
      str3 = " and accountCode = '" + paramString1 + "' and b_date <  to_date('" + paramString2 + "','yyyy-MM-dd') " + " order by b_date desc";
      str1 = str2 + str3;
      localList = queryBySql(str1);
      if ((localList != null) && (localList.size() > 0))
        return (BigDecimal)((Map)localList.get(0)).get("TODAYBALANCE");
    }
    return null;
  }

  public List queryDailyBalance(String paramString1, String paramString2)
  {
    String str1 = "select * from (select a.b_date,a.accountCode,b.name,a.lastDayBalance,a.DebitAmount,a.CreditAmount,a.todayBalance,b.accountLevel,b.dCFlag from f_DailyBalance a,f_Account b where a.accountCode=b.Code) db where 1=1 ";
    String str2 = " and accountCode = '" + paramString1 + "' and b_date <  to_date('" + paramString2 + "','yyyy-MM-dd') " + " order by b_date desc";
    str1 = str1 + str2;
    List localList = queryBySql(str1);
    return localList;
  }

  public List queryLedgerSum1(String paramString1, String paramString2, String paramString3)
  {
    String str = "select summaryno,summary,dAmount,cAmount  from (  select cast(b3.summaryno as varchar2(5)) as summaryno,  (select s.summary from f_summary s where s.summaryno = b3.summaryno) summary,  nvl(b1.dAmount, 0) dAmount,  nvl(b2.cAmount, 0) cAmount  from (select a1.summaryno, sum(a1.amount) dAmount  from f_accountbook a1  where a1.debitcode = '" + paramString1 + "' " + " and b_date >= to_date('" + paramString2 + "', 'yyyy-MM-dd') " + " and b_date <= to_date('" + paramString3 + "', 'yyyy-MM-dd') " + " group by a1.summaryno) b1, " + " (select a1.summaryno, sum(a1.amount) cAmount " + " from f_accountbook a1 " + " where a1.creditcode = '" + paramString1 + "' " + " and b_date >= to_date('" + paramString2 + "', 'yyyy-MM-dd') " + " and b_date <= to_date('" + paramString3 + "', 'yyyy-MM-dd') " + " group by a1.summaryno) b2, " + " (select distinct (a3.summaryno) " + " from f_accountbook a3 " + " where (a3.debitcode = '" + paramString1 + "' or a3.creditcode = '" + paramString1 + "') " + " and b_date >= to_date('" + paramString2 + "', 'yyyy-MM-dd') " + " and b_date <= to_date('" + paramString3 + "', 'yyyy-MM-dd') " + " order by a3.summaryno) b3 " + " where b3.summaryno = b1.summaryno(+) " + " and b3.summaryno = b2.summaryno(+) " + " )";
    return queryBySql(str);
  }

  public List queryLedgerSum2(String paramString1, String paramString2, String paramString3)
  {
    String str = " select summaryno, summary,dAmount,cAmount  from (  select cast(b3.summaryno as varchar2(5)) as summaryno,  (select s.summary from f_summary s where s.summaryno = b3.summaryno) summary,  nvl(b1.dAmount, 0) dAmount,  nvl(b2.cAmount, 0) cAmount  from (select a1.summaryno, sum(a1.amount) dAmount  from f_accountbook a1  where a1.debitcode in (select a.code  from f_account a  where a.accountlevel >  (select t.accountlevel  from f_account t  where t.code = '" + paramString1 + "') " + " and a.code like '" + paramString1 + "%') " + " and b_date >= to_date('" + paramString2 + "', 'yyyy-MM-dd') " + " and b_date <= to_date('" + paramString3 + "', 'yyyy-MM-dd') " + " group by a1.summaryno) b1, " + " (select a1.summaryno, sum(a1.amount) cAmount " + " from f_accountbook a1 " + " where a1.creditcode in " + " (select a.code " + " from f_account a " + " where a.accountlevel > " + " (select t.accountlevel " + " from f_account t " + " where t.code = '" + paramString1 + "') " + " and a.code like '" + paramString1 + "%') " + " and b_date >= to_date('" + paramString2 + "', 'yyyy-MM-dd') " + " and b_date <= to_date('" + paramString3 + "', 'yyyy-MM-dd') " + " group by a1.summaryno) b2, " + " (select distinct (a3.summaryno) " + " from f_accountbook a3 " + " where (a3.debitcode in " + " (select a.code  " + " from f_account a " + " where a.accountlevel > " + " (select t.accountlevel " + " from f_account t " + " where t.code = '" + paramString1 + "') " + " and a.code like '" + paramString1 + "%') or " + " a3.creditcode in " + " (select a.code " + " from f_account a " + " where a.accountlevel > " + " (select t.accountlevel " + " from f_account t " + " where t.code = '" + paramString1 + "') " + " and a.code like '" + paramString1 + "%')) " + " and b_date >= to_date('" + paramString2 + "', 'yyyy-MM-dd') " + " and b_date <= to_date('" + paramString3 + "', 'yyyy-MM-dd') " + " order by a3.summaryno) b3 " + " where b3.summaryno = b1.summaryno(+) " + " and b3.summaryno = b2.summaryno(+) " + ")";
    return queryBySql(str);
  }

  public List queryAccountBalance(String paramString1, String paramString2, String paramString3)
  {
    String str = "select b4.code,b4.name,b4.dcflag,nvl(b2.lastdaybalance,0) lastdaybalance,nvl(b3.todaybalance,0) todaybalance,nvl(b3.debitAmount,0) debitAmount,nvl(b3.creditAmount,0) creditAmount from (select min(t.b_date) mindate,max(b_date) maxdate,accountcode from f_dailybalance t where t.b_date>=to_date('" + paramString2 + "','yyyy-MM-dd') and t.b_date<=to_date('" + paramString3 + "','yyyy-MM-dd') and t.accountcode='" + paramString1 + "' group by accountcode) b1," + "(select lastdaybalance,b_date from f_dailybalance where accountcode='" + paramString1 + "' ) b2," + "(select todaybalance,debitAmount,creditAmount,b_date from f_dailybalance where accountcode='" + paramString1 + "')b3, " + "(select * from f_account where code='" + paramString1 + "' ) b4" + " where b1.mindate=b2.b_date and b1.maxdate=b3.b_date and b1.accountcode=b4.code";
    return queryBySql(str);
  }
}