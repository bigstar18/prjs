package gnnt.MEBS.timebargain.mgr.service;

import java.util.List;

public abstract interface CommodityIdService
{
  public abstract List commodityIdList(String paramString);

  public abstract List firmIdList(String paramString);
}