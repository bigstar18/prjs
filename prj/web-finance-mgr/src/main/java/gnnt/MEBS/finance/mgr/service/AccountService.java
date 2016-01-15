package gnnt.MEBS.finance.mgr.service;

import gnnt.MEBS.finance.mgr.model.Account;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public abstract interface AccountService
{
  public abstract Account getLeafAccountByCode(String paramString);

  public abstract List queryLedger(String paramString1, String paramString2, String paramString3);

  public abstract BigDecimal getBeginBalance(String paramString1, String paramString2, boolean paramBoolean);

  public abstract Map queryDailyBalance(String paramString1, String paramString2);

  public abstract List queryLedgerSum(String paramString1, String paramString2, String paramString3);

  public abstract Map queryAccountBalance(String paramString1, String paramString2, String paramString3);
}