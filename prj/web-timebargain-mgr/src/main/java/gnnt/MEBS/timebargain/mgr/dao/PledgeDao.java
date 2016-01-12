package gnnt.MEBS.timebargain.mgr.dao;

import gnnt.MEBS.common.mgr.model.StandardModel;

public abstract interface PledgeDao
{
  public abstract void add(StandardModel paramStandardModel);

  public abstract void executeUpdateBySql(String paramString);
}