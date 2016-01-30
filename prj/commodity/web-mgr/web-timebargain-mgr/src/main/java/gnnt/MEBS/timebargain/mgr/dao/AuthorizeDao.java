package gnnt.MEBS.timebargain.mgr.dao;

import gnnt.MEBS.timebargain.mgr.model.authorize.OverdueHoldPosition;
import java.util.List;

public abstract interface AuthorizeDao
{
  public abstract List holdPositionsList(String paramString);

  public abstract long getSpecHoldqty(OverdueHoldPosition paramOverdueHoldPosition);

  public abstract long getNotradeqty(OverdueHoldPosition paramOverdueHoldPosition);
}