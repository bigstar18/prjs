package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.model.AheadSettle;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface AheadSettleManager
{
  public abstract List listAheadSettle(QueryConditions paramQueryConditions);
  
  public abstract int saveAheadSettle(AheadSettle paramAheadSettle);
  
  public abstract int auditAheadSettle(AheadSettle paramAheadSettle);
  
  public abstract AheadSettle getAheadSettle(String paramString);
  
  public abstract long getHoldQty(String paramString1, String paramString2, int paramInt);
  
  public abstract long getGageQty(String paramString1, String paramString2, int paramInt);
}
