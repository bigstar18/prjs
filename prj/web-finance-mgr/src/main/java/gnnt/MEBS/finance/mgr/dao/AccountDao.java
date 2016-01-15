package gnnt.MEBS.finance.mgr.dao;

import java.math.BigDecimal;
import java.util.List;

public abstract interface AccountDao
{
  public abstract List getLeafAccountByCode(String paramString);

  public abstract List queryLedger1(String paramString1, String paramString2, String paramString3);

  public abstract List queryLedger2(String paramString1, String paramString2, String paramString3);

  public abstract BigDecimal queryDailyBalance(String paramString1, String paramString2, boolean paramBoolean);

  public abstract List queryDailyBalance(String paramString1, String paramString2);

  public abstract List queryLedgerSum1(String paramString1, String paramString2, String paramString3);

  public abstract List queryLedgerSum2(String paramString1, String paramString2, String paramString3);

  public abstract List queryAccountBalance(String paramString1, String paramString2, String paramString3);
}