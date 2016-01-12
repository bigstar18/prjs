package gnnt.MEBS.timebargain.mgr.dao;

import java.util.List;

public abstract interface CommodityIdDao
{
  public abstract List commodityIdList(String paramString);

  public abstract List firmIdList(String paramString);
}