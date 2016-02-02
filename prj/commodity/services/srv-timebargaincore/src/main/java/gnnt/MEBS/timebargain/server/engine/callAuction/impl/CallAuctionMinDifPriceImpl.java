package gnnt.MEBS.timebargain.server.engine.callAuction.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.engine.TradeMatcher;
import gnnt.MEBS.timebargain.server.engine.callAuction.AbstractCallAuction;
import gnnt.MEBS.timebargain.server.engine.callAuction.vo.CallAuctionDataBean;
import gnnt.MEBS.timebargain.server.model.Order;

public class CallAuctionMinDifPriceImpl extends AbstractCallAuction {
	final transient Log logger = LogFactory.getLog(getClass());

	public CallAuctionDataBean aggregatePricingByCommodity(List<Order> paramList, List<TradeMatcher> paramList1, double paramDouble,
			String paramString) {
		LinkedList localLinkedList1 = new LinkedList();
		LinkedList localLinkedList2 = new LinkedList();
		HashSet localHashSet = new HashSet();
		analysisOrders(paramList, localLinkedList1, localLinkedList2, localHashSet);
		Map localMap = getResultMap(localLinkedList1, localLinkedList2, localHashSet, paramDouble);
		this.logger.info(">>>>>>>>=========数据统计队列=============>>>>>>");
		Iterator localObject = localMap.keySet().iterator();
		while (((Iterator) localObject).hasNext()) {
			this.logger.debug(localMap.get(((Iterator) localObject).next()));
		}
		CallAuctionDataBean localObject1 = getRight_priceAndMaxqty(localMap);
		long l = ((CallAuctionDataBean) localObject1).getQuantity();
		Double localDouble = Double.valueOf(((CallAuctionDataBean) localObject1).getCurPrice());
		this.logger.info("最优价------------->" + l);
		this.logger.info("最大成交量---------->" + localDouble);
		if (l > 0L) {
			refreshTradeMatchers(l, localDouble, localLinkedList1, localLinkedList2, paramList1);
		}
		return localObject1;
	}

	private void analysisOrders(List<Order> paramList, LinkedList<Order> paramLinkedList1, LinkedList<Order> paramLinkedList2, Set<Double> paramSet) {
		for (int i = 0; i < paramList.size(); i++) {
			Order localOrder = (Order) paramList.get(i);
			if (localOrder.getPrice().doubleValue() != 0.0D) {
				paramSet.add(localOrder.getPrice());
				int j;
				int k;
				int m;
				if (localOrder.getBuyOrSell().shortValue() == 1) {
					if (paramLinkedList1.size() == 0) {
						paramLinkedList1.add(localOrder);
					} else {
						j = 0;
						k = paramLinkedList1.size();
						for (m = 0; m < k; m++) {
							if (localOrder.getPrice().doubleValue() > ((Order) paramLinkedList1.get(m)).getPrice().doubleValue()) {
								paramLinkedList1.add(m, localOrder);
								j = 1;
							} else if ((localOrder.getOrderType().shortValue() == 2) && (localOrder.getCloseFlag().shortValue() == 2)
									&& (localOrder.getPrice().doubleValue() == ((Order) paramLinkedList1.get(m)).getPrice().doubleValue())) {
								paramLinkedList1.add(m, localOrder);
								j = 1;
							}
							if (j != 0) {
								break;
							}
						}
						if (j == 0) {
							paramLinkedList1.addLast(localOrder);
						}
					}
				} else if (paramLinkedList2.size() == 0) {
					paramLinkedList2.add(localOrder);
				} else {
					j = 0;
					k = paramLinkedList2.size();
					for (m = 0; m < k; m++) {
						if (localOrder.getPrice().doubleValue() < ((Order) paramLinkedList2.get(m)).getPrice().doubleValue()) {
							paramLinkedList2.add(m, localOrder);
							j = 1;
						} else if ((localOrder.getOrderType().shortValue() == 2) && (localOrder.getCloseFlag().shortValue() == 2)
								&& (localOrder.getPrice().doubleValue() == ((Order) paramLinkedList2.get(m)).getPrice().doubleValue())) {
							paramLinkedList2.add(m, localOrder);
							j = 1;
						}
						if (j != 0) {
							break;
						}
					}
					if (j == 0) {
						paramLinkedList2.addLast(localOrder);
					}
				}
			}
		}
	}

	private Map<Double, CallAuctionDataBean> getResultMap(LinkedList<Order> paramLinkedList1, LinkedList<Order> paramLinkedList2,
			Set<Double> paramSet, double paramDouble) {
		HashMap localHashMap = new HashMap();
		Iterator localIterator = paramSet.iterator();
		while (localIterator.hasNext()) {
			Double localDouble = (Double) localIterator.next();
			CallAuctionDataBean localCallAuctionDataBean = (CallAuctionDataBean) localHashMap.get(localDouble);
			if (localCallAuctionDataBean == null) {
				localCallAuctionDataBean = new CallAuctionDataBean();
				localCallAuctionDataBean.setCurPrice(localDouble.doubleValue());
				localCallAuctionDataBean.setDifPrice(Math.abs(localDouble.doubleValue() - paramDouble));
			}
			Order localOrder;
			for (int i = 0; i < paramLinkedList1.size(); i++) {
				localOrder = (Order) paramLinkedList1.get(i);
				if (localDouble.doubleValue() <= localOrder.getPrice().doubleValue()) {
					localCallAuctionDataBean.setBuyQuantity(localCallAuctionDataBean.getBuyQuantity() + localOrder.getQuantity().longValue());
				}
				if (localDouble.doubleValue() < localOrder.getPrice().doubleValue()) {
					localCallAuctionDataBean
							.setBuyQtyExceptPrice(localCallAuctionDataBean.getBuyQtyExceptPrice() + localOrder.getQuantity().longValue());
				}
			}
			for (int i = 0; i < paramLinkedList2.size(); i++) {
				localOrder = (Order) paramLinkedList2.get(i);
				if (localDouble.doubleValue() >= localOrder.getPrice().doubleValue()) {
					localCallAuctionDataBean.setSellQuantity(localCallAuctionDataBean.getSellQuantity() + localOrder.getQuantity().longValue());
				}
				if (localDouble.doubleValue() > localOrder.getPrice().doubleValue()) {
					localCallAuctionDataBean
							.setSellQtyExceptPrice(localCallAuctionDataBean.getSellQtyExceptPrice() + localOrder.getQuantity().longValue());
				}
			}
			localCallAuctionDataBean.setQuantity(localCallAuctionDataBean.getBuyQuantity() > localCallAuctionDataBean.getSellQuantity()
					? localCallAuctionDataBean.getSellQuantity() : localCallAuctionDataBean.getBuyQuantity());
			localCallAuctionDataBean
					.setQuantityExceptPrice(localCallAuctionDataBean.getBuyQtyExceptPrice() > localCallAuctionDataBean.getSellQtyExceptPrice()
							? localCallAuctionDataBean.getBuyQtyExceptPrice() : localCallAuctionDataBean.getSellQtyExceptPrice());
			localCallAuctionDataBean
					.setMinRemainQuantity(Math.abs(localCallAuctionDataBean.getBuyQuantity() - localCallAuctionDataBean.getSellQuantity()));
			localHashMap.put(localDouble, localCallAuctionDataBean);
		}
		return localHashMap;
	}

	private CallAuctionDataBean getRight_priceAndMaxqty(Map<Double, CallAuctionDataBean> paramMap) {
		CallAuctionDataBean localObject1 = null;
		long l1 = 0L;
		long l2 = 0L;
		long l3 = 0L;
		double d1 = 0.0D;
		ArrayList localArrayList = new ArrayList();
		LinkedList localLinkedList = new LinkedList();
		Iterator localIterator1 = paramMap.keySet().iterator();
		while (localIterator1.hasNext()) {
			Double localDouble = (Double) localIterator1.next();
			CallAuctionDataBean localObject2 = (CallAuctionDataBean) paramMap.get(localDouble);
			long l5 = ((CallAuctionDataBean) localObject2).getQuantity();
			if (l5 >= l1) {
				localLinkedList.addFirst(localObject2);
				l1 = l5;
			}
		}
		if (l1 == 0L) {
			return new CallAuctionDataBean();
		}
		long l4 = 0L;
		Object localObject2 = localLinkedList.iterator();
		while (((Iterator) localObject2).hasNext()) {
			CallAuctionDataBean localObject3 = (CallAuctionDataBean) ((Iterator) localObject2).next();
			if (localArrayList.size() == 0) {
				l4 = ((CallAuctionDataBean) localObject3).getQuantity();
			}
			if (l4 != ((CallAuctionDataBean) localObject3).getQuantity()) {
				break;
			}
			localArrayList.add(localObject3);
		}
		this.logger.info("=====最大成交量相同的价格有" + localArrayList.size() + "个。。。");
		localObject2 = new ArrayList();
		Object localObject3 = new LinkedList();
		if (localArrayList.size() > 0) {
			if (localArrayList.size() == 1) {
				((List) localObject2).add(localArrayList.get(0));
			} else {
				Iterator localIterator2 = localArrayList.iterator();
				while (localIterator2.hasNext()) {
					CallAuctionDataBean localCallAuctionDataBean1 = (CallAuctionDataBean) localIterator2.next();
					long l7 = localCallAuctionDataBean1.getQuantityExceptPrice();
					if (((LinkedList) localObject3).size() == 0) {
						l2 = l7;
					}
					if ((l7 >= l2) && (l7 <= l1)) {
						((LinkedList) localObject3).addFirst(localCallAuctionDataBean1);
						l2 = l7;
					}
				}
			}
		}
		long l6 = 0L;
		Object localObject4 = ((LinkedList) localObject3).iterator();
		while (((Iterator) localObject4).hasNext()) {
			CallAuctionDataBean localObject5 = (CallAuctionDataBean) ((Iterator) localObject4).next();
			if (((List) localObject2).size() == 0) {
				l6 = ((CallAuctionDataBean) localObject5).getQuantityExceptPrice();
			}
			if (l6 != ((CallAuctionDataBean) localObject5).getQuantityExceptPrice()) {
				break;
			}
			((List) localObject2).add(localObject5);
		}
		this.logger.info("=====纯最大成交量相同的价格有" + ((List) localObject2).size() + "个。。。");
		localObject4 = new ArrayList();
		Object localObject5 = new LinkedList();
		if (((List) localObject2).size() > 0) {
			if (((List) localObject2).size() == 1) {
				((List) localObject4).add(((List) localObject2).get(0));
			} else {
				Iterator localIterator3 = ((List) localObject2).iterator();
				while (localIterator3.hasNext()) {
					CallAuctionDataBean localCallAuctionDataBean2 = (CallAuctionDataBean) localIterator3.next();
					long l9 = localCallAuctionDataBean2.getMinRemainQuantity();
					if (((LinkedList) localObject5).size() == 0) {
						l3 = l9;
					}
					if (l9 <= l3) {
						((LinkedList) localObject5).addFirst(localCallAuctionDataBean2);
						l3 = l9;
					}
				}
			}
		}
		long l8 = 0L;
		Object localObject6 = ((LinkedList) localObject5).iterator();
		while (((Iterator) localObject6).hasNext()) {
			CallAuctionDataBean localObject7 = (CallAuctionDataBean) ((Iterator) localObject6).next();
			if (((List) localObject4).size() == 0) {
				l8 = ((CallAuctionDataBean) localObject7).getMinRemainQuantity();
			}
			if (l8 != ((CallAuctionDataBean) localObject7).getMinRemainQuantity()) {
				break;
			}
			((List) localObject4).add(localObject7);
		}
		this.logger.info("=====最小剩余量相同的价格有" + ((List) localObject4).size() + "个。。。");
		localObject6 = new ArrayList();
		Object localObject7 = new LinkedList();
		if (((List) localObject4).size() > 0) {
			if (((List) localObject4).size() == 1) {
				((List) localObject6).add(((List) localObject4).get(0));
			} else {
				Iterator localIterator4 = ((List) localObject4).iterator();
				while (localIterator4.hasNext()) {
					CallAuctionDataBean localCallAuctionDataBean3 = (CallAuctionDataBean) localIterator4.next();
					double d3 = localCallAuctionDataBean3.getDifPrice();
					if (((LinkedList) localObject7).size() == 0) {
						d1 = d3;
					}
					if (d3 <= d1) {
						d1 = d3;
						((LinkedList) localObject7).addFirst(localCallAuctionDataBean3);
					}
				}
			}
		}
		double d2 = 0.0D;
		Object localObject8 = ((LinkedList) localObject7).iterator();
		CallAuctionDataBean localCallAuctionDataBean4;
		while (((Iterator) localObject8).hasNext()) {
			localCallAuctionDataBean4 = (CallAuctionDataBean) ((Iterator) localObject8).next();
			if (((List) localObject6).size() == 0) {
				d2 = localCallAuctionDataBean4.getDifPrice();
			}
			if (d2 != localCallAuctionDataBean4.getDifPrice()) {
				break;
			}
			((List) localObject6).add(localCallAuctionDataBean4);
		}
		this.logger.info("=====最接近昨结算价相同的价格有" + ((List) localObject6).size() + "个。。。");
		if (((List) localObject6).size() > 0) {
			if (((List) localObject6).size() == 1) {
				localObject1 = (CallAuctionDataBean) ((List) localObject6).get(0);
			} else {
				CallAuctionDataBean localObject81 = (CallAuctionDataBean) ((List) localObject6).get(0);
				CallAuctionDataBean localCallAuctionDataBean41 = (CallAuctionDataBean) ((List) localObject6).get(1);
				double d4 = ((CallAuctionDataBean) localObject81).getCurPrice();
				double d5 = localCallAuctionDataBean41.getCurPrice();
				CallAuctionDataBean localCallAuctionDataBean5 = d4 > d5 ? localObject81 : localCallAuctionDataBean41;
				CallAuctionDataBean localCallAuctionDataBean6 = d4 < d5 ? localObject81 : localCallAuctionDataBean41;
				if (localCallAuctionDataBean5.getBuyQuantity() >= localCallAuctionDataBean5.getSellQuantity()) {
					localObject1 = localCallAuctionDataBean5;
				} else {
					localObject1 = localCallAuctionDataBean6;
				}
			}
		}
		return localObject1;
	}

	public static void main(String[] paramArrayOfString) {
		double d = 810.0D;
		CallAuctionMinDifPriceImpl localCallAuctionMinDifPriceImpl = new CallAuctionMinDifPriceImpl();
		yinhexin localyinhexin = new yinhexin();
		List localList = localyinhexin.getList4();
		LinkedList localLinkedList1 = new LinkedList();
		LinkedList localLinkedList2 = new LinkedList();
		HashSet localHashSet = new HashSet();
		localCallAuctionMinDifPriceImpl.analysisOrders(localList, localLinkedList1, localLinkedList2, localHashSet);
		Map localMap = localCallAuctionMinDifPriceImpl.getResultMap(localLinkedList1, localLinkedList2, localHashSet, d);
		System.out.println("resultMap.size()=" + localMap.size());
		Object localObject = localMap.keySet().iterator();
		while (((Iterator) localObject).hasNext()) {
			System.out.println(localMap.get(((Iterator) localObject).next()));
		}
		localObject = localCallAuctionMinDifPriceImpl.getRight_priceAndMaxqty(localMap);
		System.out.println("RIGHT_PRICE=" + ((CallAuctionDataBean) localObject).getCurPrice());
		System.out.println("MAXQTY=" + ((CallAuctionDataBean) localObject).getQuantity());
	}
}
