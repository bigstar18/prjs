package gnnt.MEBS.timebargain.front.dao;

import gnnt.MEBS.timebargain.server.model.DelayOrder;

public abstract interface DelayTradeDao {
	public abstract DelayOrder getDelayOrderById(String paramString);
}