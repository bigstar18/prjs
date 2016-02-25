package gnnt.MEBS.timebargain.manage.dao;

import java.util.List;

public abstract interface LookupUseBackDsDAO
  extends DAO
{
  public abstract List getBmkTjDistinctCommodityID(String paramString1, String paramString2);
}
