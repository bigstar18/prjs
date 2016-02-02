package gnnt.MEBS.timebargain.server.engine.callAuction;

import gnnt.MEBS.timebargain.server.engine.TradeMatcher;
import gnnt.MEBS.timebargain.server.engine.callAuction.vo.CallAuctionDataBean;
import gnnt.MEBS.timebargain.server.model.Order;
import java.util.List;

public abstract interface CallAuction
{
  public abstract CallAuctionDataBean aggregatePricingByCommodity(List<Order> paramList, List<TradeMatcher> paramList1, double paramDouble, String paramString);
}
