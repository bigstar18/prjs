package gnnt.MEBS.timebargain.server;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.model.TradeTime;

public class TradeDateFactory {
	private static final Log log = LogFactory.getLog(TradeDateFactory.class);
	public static final short TYPE_TODAY = 0;
	public static final short TYPE_CROSSDAY = 1;

	public static TradeDate createTradeDate(short word0) {
		TradeDate obj = null;
		switch (word0) {
		case 0: // '\0'
			obj = new TradeDateToday();
			break;

		case 1: // '\001'
			obj = new TradeDateCrossDay();
			break;
		}
		return ((TradeDate) (obj));
	}

	public static void main(String[] paramArrayOfString) {
		TradeDate tradedate = createTradeDate((short) 1);
		System.out.println((new StringBuilder()).append("ClearDate:").append(tradedate.calClearDate()).toString());
		List list = tradedate.getTradeTimes(tradedate.calClearDate());
		for (int i = 0; i < list.size(); i++) {
			TradeTime tradetime = (TradeTime) list.get(i);
			log.debug((new StringBuilder()).append("TradeTime::").append(tradetime).toString());
		}
	}
}
