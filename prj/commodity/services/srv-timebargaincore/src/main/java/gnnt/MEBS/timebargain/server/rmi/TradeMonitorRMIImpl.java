// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi

package gnnt.MEBS.timebargain.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.engine.CommodityOrder;
import gnnt.MEBS.timebargain.server.engine.PriceOrder;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.TradeMonitor;

// Referenced classes of package gnnt.MEBS.timebargain.server.rmi:
// TradeMonitorRMI

public class TradeMonitorRMIImpl extends UnicastRemoteObject implements TradeMonitorRMI {

	private static final long serialVersionUID = 0x25558020c4dfce18L;
	Log log = LogFactory.getLog(getClass());
	private Server server;
	private ServerInit serverInit;
	private Map saleOrdersResultMap;
	private TradeMonitor tradeMonitor;

	public TradeMonitorRMIImpl(Server server1) throws RemoteException {
		server = server1;
		serverInit = server1.getServerInit();
	}

	public Map querySaleOrders() throws RemoteException, Exception {
		return queryOrdersByCondition(null, "buyQueue", null);
	}

	public Map querySaleOrders(String s, Map map) throws RemoteException {
		return queryOrdersByCondition(s, "BS", map);
	}

	public Map queryWaitOrders(String s, Map map) throws RemoteException {
		return queryOrdersByCondition(s, "W", map);
	}

	public Map queryOrdersByCondition(String s, String s1, Map map) throws RemoteException {
		saleOrdersResultMap = new HashMap();

		int pageSize = ((Integer) map.get("pageSize")).intValue();
		int pageIndex = ((Integer) map.get("pageIndex")).intValue();

		try {
			Map map1 = server.getTradeEngine().commodityOrders;
			if (map1 == null || map1.size() == 0)
				return saleOrdersResultMap;

			HashMap hashmap = new HashMap();
			tradeMonitor = new TradeMonitor();
			if ("W".equals(s1)) {
				String[] as = new String[0];
				String as1[] = (String[]) map1.keySet().toArray(as);
				int k1 = as1.length;
				ArrayList arraylist = new ArrayList();
				for (int i2 = 0; i2 < k1; i2++) {
					String s4 = as1[i2];
					arraylist.addAll(((CommodityOrder) map1.get(s4)).waitOrders);
				}

				as1 = s.split("_");
				for (int l1 = 0; l1 < as1.length; l1++)
					if (null == as1[l1] || "".equals(as1[l1]))
						as1[l1] = "*";

				String s2 = as1[1];
				String s3 = as1[2];
				if (!"*".equals(as1[0])) {
					Object obj = null;
					for (int i3 = 0; i3 < arraylist.size(); i3++) {
						Order order = (Order) arraylist.get(i3);
						if ("*".equals(as1[1]) && !s3.equals((new StringBuilder()).append(order.getStatus()).append("").toString())) {
							arraylist.remove(i3);
							continue;
						}
						if ("0".equals(as1[0]) && !s3.equals("*") && (!s2.equals(order.getFirmID())
								|| !s3.equals((new StringBuilder()).append(order.getStatus()).append("").toString()))) {
							arraylist.remove(i3);
							continue;
						}
						if ("0".equals(as1[0]) && s3.equals("*") && !s2.equals(order.getFirmID())) {
							arraylist.remove(i3);
							continue;
						}
						if ("1".equals(as1[0]) && !s3.equals("*") && (!s2.equals(order.getCommodityID())
								|| !s3.equals((new StringBuilder()).append(order.getStatus()).append("").toString()))) {
							arraylist.remove(i3);
							continue;
						}
						if ("1".equals(as1[0]) && s3.equals("*") && !s2.equals(order.getCommodityID()))
							arraylist.remove(i3);
					}

				}
				int l = arraylist.size();
				hashmap.put("wTotalCount", Integer.valueOf(l));

				int k2 = pageSize * pageIndex <= l ? pageSize * pageIndex : l;
				ArrayList arraylist2 = new ArrayList();
				arraylist2.addAll(arraylist.subList((pageIndex - 1) * pageSize, k2));
				tradeMonitor.setWaitOrders(arraylist2);
			} else if ("BS".equals(s1)) {
				int k = 0;
				for (Iterator iterator = ((CommodityOrder) map1.get(s)).buyQueue.iterator(); iterator.hasNext();) {
					PriceOrder priceorder = (PriceOrder) iterator.next();
					k += priceorder.orderQueue.size();
				}
				hashmap.put("bTotalCount", Integer.valueOf(k));

				int i1 = 0;
				for (Iterator iterator1 = ((CommodityOrder) map1.get(s)).sellQueue.iterator(); iterator1.hasNext();) {
					PriceOrder priceorder1 = (PriceOrder) iterator1.next();
					i1 += priceorder1.orderQueue.size();
				}
				hashmap.put("sTotalCount", Integer.valueOf(i1));

				int j1 = 0;
				ArrayList arraylist1 = new ArrayList();
				int j2 = pageSize * pageIndex <= k ? pageSize * pageIndex : k;
				Iterator iterator2 = ((CommodityOrder) map1.get(s)).buyQueue.iterator();
				while (iterator2.hasNext()) {
					PriceOrder priceorder2 = (PriceOrder) iterator2.next();
					int j3 = 0;
					while (j3 < priceorder2.orderQueue.size()) {
						if (j1 >= (pageIndex - 1) * pageSize && j1 < j2)
							arraylist1.add(priceorder2.orderQueue.get(j3));
						if (++j1 == j2)
							break;
						j3++;
					}
				}
				tradeMonitor.setBuyQueue(arraylist1);

				int l2 = 0;
				ArrayList arraylist3 = new ArrayList();
				int k3 = pageSize * pageIndex <= i1 ? pageSize * pageIndex : i1;
				Iterator iterator3 = ((CommodityOrder) map1.get(s)).sellQueue.iterator();
				while (iterator3.hasNext()) {
					PriceOrder priceorder3 = (PriceOrder) iterator3.next();
					int l3 = 0;
					while (l3 < priceorder3.orderQueue.size()) {
						if (l2 >= (pageIndex - 1) * pageSize && l2 < k3)
							arraylist3.add(priceorder3.orderQueue.get(l3));
						if (++l2 == k3)
							break;
						l3++;
					}
				}
				tradeMonitor.setSellQueue(arraylist3);
			}

			tradeMonitor.setMap(hashmap);
			saleOrdersResultMap.put(s, tradeMonitor);
		} catch (

		Exception exception)

		{
			log.debug((new StringBuilder()).append("method in TradeMonitorRMI.queryBSOrders Throws Exception ").append(exception.toString())
					.toString());
		}

		return saleOrdersResultMap;
	}
}
