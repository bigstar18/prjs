package gnnt.MEBS.timebargain.mgr.service;

import gnnt.MEBS.timebargain.mgr.model.settleMatch.SettleMatchM;

public abstract interface SettleMatchService
{
  public abstract int addSettleMatch(String[] paramArrayOfString, SettleMatchM paramSettleMatchM, String paramString)
    throws Exception;
}