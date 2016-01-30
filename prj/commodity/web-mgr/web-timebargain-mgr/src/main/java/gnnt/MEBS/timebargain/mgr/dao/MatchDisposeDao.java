package gnnt.MEBS.timebargain.mgr.dao;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.timebargain.mgr.model.settle.SettleMatch;
import java.util.List;
import java.util.Map;

public abstract interface MatchDisposeDao
{
  public abstract Object getFirmFunds(String paramString, Object[] paramArrayOfObject)
    throws Exception;

  public abstract List<StandardModel> getSettleMatchLog(String paramString);

  public abstract SettleMatch getSettleMatchLock(String paramString);

  public abstract void updateSettleMatch(SettleMatch paramSettleMatch);

  public abstract void add(StandardModel paramStandardModel);

  public abstract Object executeProcedure(String paramString, Object[] paramArrayOfObject)
    throws Exception;

  public abstract void updateSettleMargin(String paramString, double paramDouble);

  public abstract void deleteBillFrozenByMatchId(String paramString);

  public abstract List<Map<Object, Object>> queryBySql(String paramString);

  public abstract void executeUpdateBySql(String paramString);
}