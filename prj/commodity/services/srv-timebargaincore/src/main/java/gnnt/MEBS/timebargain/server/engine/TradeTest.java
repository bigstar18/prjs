// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi

package gnnt.MEBS.timebargain.server.engine;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.base.util.SysData;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.Trade;

// Referenced classes of package gnnt.MEBS.timebargain.server.engine:
// TradeEngine, OrderTrace, TradeCallback, QuotationCallback

public class TradeTest {

	public TradeTest() {
	}

	public static void main(String args[]) {
		TradeEngine tradeengine = new TradeEngine(new TradeCallback() {

			public void callback(Trade trade, Trade trade1) {
			}

		}, new QuotationCallback() {

			public void callback(Quotation quotation2) {
			}

		});
		DaoHelper daohelper = (DaoHelper) SysData.getBean("daoHelper");
		List list = daohelper.queryBySQL("select * from t_commodity");
		ArrayList arraylist = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			Commodity commodity = new Commodity();
			commodity.setCommodityID((String) map.get("COMMODITYID"));
			commodity.setContractFactor(((BigDecimal) map.get("CONTRACTFACTOR")).doubleValue());
			arraylist.add(commodity);
		}

		list = daohelper.queryBySQL("select * from t_quotation");
		ArrayList arraylist1 = new ArrayList();
		Quotation quotation1;
		for (Iterator iterator = list.iterator(); iterator.hasNext(); arraylist1.add(quotation1)) {
			Object obj = iterator.next();
			Map map1 = (Map) obj;
			quotation1 = new Quotation();
			quotation1.setCommodityID((String) map1.get("COMMODITYID"));
			quotation1.setYesterBalancePrice(Double.valueOf(((BigDecimal) map1.get("YESTERBALANCEPRICE")).doubleValue()));
			quotation1.updateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		}

		tradeengine.init(arraylist, arraylist1, 0L);
		tradeengine.open();
		tradeengine.trade();
		List list1 = OrderTrace.readTrace();
		System.out.println((new StringBuilder()).append("-----Start:").append(new Date()).toString());
		System.out.println((new StringBuilder()).append("----orders size:").append(list1.size()).toString());
		Order order;
		for (Iterator iterator1 = list1.iterator(); iterator1.hasNext(); tradeengine.addOrder(order))
			order = (Order) iterator1.next();

		System.out.println((new StringBuilder()).append("----orders over:").append(list1.size()).toString());
		while (tradeengine.countWaitOrder() > 0L)
			try {
				System.out.println((new StringBuilder()).append("----te.orders.").append(tradeengine.countWaitOrder()).toString());
				Thread.sleep(1000L);
			} catch (Exception exception) {
			}
		System.out.println((new StringBuilder()).append("-----End:").append(new Date()).toString());
		try {
			Thread.sleep(2000L);
		} catch (Exception exception1) {
		}
		Quotation quotation;
		for (Iterator iterator2 = tradeengine.quotations.values().iterator(); iterator2.hasNext(); System.out.println(quotation))
			quotation = (Quotation) iterator2.next();

	}
}
