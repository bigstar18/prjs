package gnnt.MEBS.timebargain.server.engine;

import gnnt.MEBS.timebargain.server.model.Trade;

public abstract interface TradeCallback
{
  public abstract void callback(Trade paramTrade1, Trade paramTrade2);
}
