package gnnt.MEBS.finance.mgr.service.impl;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.finance.mgr.dao.AccountDao;
import gnnt.MEBS.finance.mgr.model.Account;
import gnnt.MEBS.finance.mgr.service.AccountService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("accountService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class AccountServiceImpl extends StandardService
  implements AccountService
{

  @Autowired
  @Qualifier("accountDao")
  private AccountDao accountDao;

  public AccountDao getAccountDao()
  {
    return this.accountDao;
  }

  public Account getLeafAccountByCode(String paramString)
  {
    Account localAccount = null;
    List localList = this.accountDao.getLeafAccountByCode(paramString);
    if ((localList != null) && (localList.size() > 0))
      localAccount = (Account)localList.get(0);
    return localAccount;
  }

  public List queryLedger(String paramString1, String paramString2, String paramString3)
  {
    List localList = null;
    Account localAccount = getLeafAccountByCode(paramString1);
    if (localAccount != null)
      localList = this.accountDao.queryLedger1(paramString1, paramString2, paramString3);
    else
      localList = this.accountDao.queryLedger2(paramString1, paramString2, paramString3);
    return localList;
  }

  public BigDecimal getBeginBalance(String paramString1, String paramString2, boolean paramBoolean)
  {
    return this.accountDao.queryDailyBalance(paramString1, paramString2, paramBoolean);
  }

  public Map queryDailyBalance(String paramString1, String paramString2)
  {
    HashMap localHashMap = new HashMap();
    List localList = this.accountDao.queryDailyBalance(paramString1, paramString2);
    if ((localList != null) && (localList.size() > 0))
    {
      Map localMap = (Map)localList.get(0);
      localHashMap.put("lastDayBalance", (BigDecimal)localMap.get("LASTDAYBALANCE"));
      localHashMap.put("debitAmount", (BigDecimal)localMap.get("DEBITAMOUNT"));
      localHashMap.put("creditAmount", (BigDecimal)localMap.get("CREDITAMOUNT"));
      localHashMap.put("balance", (BigDecimal)localMap.get("TODAYBALANCE"));
    }
    return localHashMap;
  }

  public List queryLedgerSum(String paramString1, String paramString2, String paramString3)
  {
    List localList = null;
    Account localAccount = getLeafAccountByCode(paramString1);
    if (localAccount != null)
      localList = this.accountDao.queryLedgerSum1(paramString1, paramString2, paramString3);
    else
      localList = this.accountDao.queryLedgerSum2(paramString1, paramString2, paramString3);
    return localList;
  }

  public Map queryAccountBalance(String paramString1, String paramString2, String paramString3)
  {
    Map localMap = null;
    List localList = this.accountDao.queryAccountBalance(paramString1, paramString2, paramString3);
    if ((localList != null) && (localList.size() > 0))
      localMap = (Map)localList.get(0);
    return localMap;
  }
}