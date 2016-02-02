package gnnt.MEBS.timebargain.server.dao;

import gnnt.MEBS.timebargain.server.model.FirmInfo;
import gnnt.MEBS.timebargain.server.model.Order;
import java.util.List;

public abstract interface ProxyDAO
  extends DAO
{
  public abstract FirmInfo getFirmInfoByTraderID(String paramString);
  
  public abstract List queryCustomerHoldSumInfo(String paramString1, String paramString2, String paramString3);
  
  public abstract List queryOrder(Order paramOrder);
  
  public abstract List queryTradeInfo(long paramLong, String paramString);
  
  public abstract List getBroadcasts(long paramLong, String paramString);
}
