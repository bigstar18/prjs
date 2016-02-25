package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.AheadSettle;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.List;

public abstract interface AheadSettleDAO
  extends DAO
{
  public abstract int saveAheadSettle(AheadSettle paramAheadSettle)
    throws Exception;
  
  public abstract int auditAheadSettle(AheadSettle paramAheadSettle)
    throws Exception;
  
  public abstract AheadSettle getAheadSettle(String paramString);
  
  public abstract List listAheadSettle(QueryConditions paramQueryConditions);
  
  public abstract void updateDataByYourSQL(String paramString)
    throws Exception;
  
  public abstract long getHoldQty(String paramString1, String paramString2, int paramInt);
  
  public abstract long getGageQty(String paramString1, String paramString2, int paramInt);
}
