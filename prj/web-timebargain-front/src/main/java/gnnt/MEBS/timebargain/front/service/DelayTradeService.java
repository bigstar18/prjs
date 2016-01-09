package gnnt.MEBS.timebargain.front.service;

import gnnt.MEBS.timebargain.server.model.DelayOrder;
import java.util.List;
import java.util.Map;

public abstract interface DelayTradeService {
	public abstract List<Map<Object, Object>> delayQuotation_query();

	public abstract List<Map<Object, Object>> delayOrderQuery(String paramString1, String paramString2);

	public abstract List<Map<Object, Object>> delayCommodityHoldingQuery(String paramString1, String paramString2);

	public abstract String getNeutralSideById(String paramString);

	public abstract DelayOrder getDelayOrderById(String paramString);
}