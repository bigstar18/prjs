package gnnt.MEBS.timebargain.mgr.service;

import gnnt.MEBS.timebargain.mgr.model.authorize.OverdueHoldPosition;
import gnnt.MEBS.timebargain.server.model.Order;
import java.util.List;

public abstract interface AuthorizeService
{
  public abstract String getOperateFirm(String paramString);

  public abstract String[] getOperateFirms(String paramString);

  public abstract List holdPositionsList(String paramString);

  public abstract long getHoldQty(OverdueHoldPosition paramOverdueHoldPosition);

  public abstract int insertOrder(Long paramLong, Order paramOrder)
    throws Exception;

  public abstract int insertCloseOrder(Long paramLong, Order paramOrder)
    throws Exception;

  public abstract String resultOrder(int paramInt);
}