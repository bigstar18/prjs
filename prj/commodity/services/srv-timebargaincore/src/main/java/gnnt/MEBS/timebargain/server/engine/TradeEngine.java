package gnnt.MEBS.timebargain.server.engine;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerShell;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.engine.callAuction.CallAuction;
import gnnt.MEBS.timebargain.server.engine.sim.SimQuotationSync;
import gnnt.MEBS.timebargain.server.engine.sim.TradeMatcherSimImpl;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Quotation;

public class TradeEngine {
	final transient Log logger = LogFactory.getLog(getClass());
	public static final int STATUS_TRADING = 0;
	public static final int STATUS_PAUSE = 1;
	public static final int STATUS_OPEN = 2;
	public static final int STATUS_CLOSE = 3;
	protected Set<Long> orderNos = new HashSet();
	public Map<String, CommodityOrder> commodityOrders = new HashMap();
	public Map<String, Quotation> quotations = new HashMap();
	public Map<String, Quotation> quotationsClone = new HashMap();
	private int status = 3;
	private long curTradeNo;
	private long curSimOrderNo;
	private int matchInterval = 100;
	protected QuotationTaker quotationTaker;
	protected List<TradeMatcher> tradeMatchers = new ArrayList();
	private TradeCallback tradeCallback;
	public SimQuotationSync simQuotationSync = new SimQuotationSync(this);
	private Market market;
	List<Quotation> qtQueryList = new ArrayList();

	public TradeEngine(TradeCallback paramTradeCallback, QuotationCallback paramQuotationCallback) {
		this.tradeCallback = paramTradeCallback;
		this.quotationTaker = new QuotationTaker(this, paramQuotationCallback);
		this.quotationTaker.start();
	}

	public void init(List<Commodity> paramList, List<Quotation> paramList1, long paramLong) {
		this.status = 3;
		this.orderNos.clear();
		Iterator localObject1 = this.tradeMatchers.iterator();
		Object localObject2;
		while (((Iterator) localObject1).hasNext()) {
			localObject2 = (TradeMatcher) ((Iterator) localObject1).next();
			((TradeMatcher) localObject2).pleaseStop();
		}
		this.tradeMatchers.clear();
		loadCommodity(paramList);
		loadQuotation(paramList1);
		if (ServerShell.RunModeIsSIM) {
			this.simQuotationSync.pleaseStop();
			this.simQuotationSync = new SimQuotationSync(this);
			this.simQuotationSync.start();
		}
		Iterator localIterator = paramList.iterator();
		while (localIterator.hasNext()) {
			Commodity localCommodity = (Commodity) localIterator.next();
			Quotation localObject11 = (Quotation) this.quotations.get(localCommodity.getCommodityID());
			localObject2 = (Quotation) this.quotationsClone.get(localCommodity.getCommodityID());
			if (localObject11 == null) {
				localObject11 = new Quotation();
				((Quotation) localObject11).commodityID = localCommodity.getCommodityID();
				((Quotation) localObject11).yesterBalancePrice = Double.valueOf(localCommodity.getLastPrice());
				((Quotation) localObject11).price = Double.valueOf(localCommodity.getLastPrice());
				((Quotation) localObject11).updateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
				this.quotations.put(((Quotation) localObject11).commodityID, localObject11);
				this.quotationsClone.put(((Quotation) localObject11).getCommodityID(), (Quotation) ((Quotation) localObject11).clone());
			}
			TradeMatcher localObject3 = null;
			if (ServerShell.RunModeIsSIM) {
				localObject3 = new TradeMatcherSimImpl();
			} else if (ServerShell.RunModeIsSpecial) {
				localObject3 = new TradeMatcherSpeImpl();
			} else {
				localObject3 = new TradeMatcherImpl();
			}
			((TradeMatcher) localObject3).init(this, localCommodity.getCommodityID());
			this.tradeMatchers.add(localObject3);
			((TradeMatcher) localObject3).start();
		}
		if (ServerShell.RunModeIsSIM) {
			this.curSimOrderNo = Server.simServerDAO.getMinOrderNo();
		}
		this.curTradeNo = paramLong;
		this.status = 2;
	}

	public void refresh(List<Commodity> paramList) {
		Iterator localIterator = paramList.iterator();
		while (localIterator.hasNext()) {
			Commodity localCommodity = (Commodity) localIterator.next();
			CommodityOrder localCommodityOrder = (CommodityOrder) this.commodityOrders.get(localCommodity.getCommodityID());
			if (localCommodityOrder == null) {
				localCommodityOrder = new CommodityOrder(localCommodity.getCommodityID());
				localCommodityOrder.factor = localCommodity.getContractFactor();
				localCommodityOrder.minPriceMove = localCommodity.getMinPriceMove();
				localCommodityOrder.spreadUpLmt = localCommodity.getSpreadUpLmt();
				localCommodityOrder.spreadDownLmt = localCommodity.getSpreadDownLmt();
				this.commodityOrders.put(localCommodity.getCommodityID(), localCommodityOrder);
				TradeMatcher localObject = null;
				if (ServerShell.RunModeIsSIM) {
					localObject = new TradeMatcherSimImpl();
				} else if (ServerShell.RunModeIsSpecial) {
					localObject = new TradeMatcherSpeImpl();
				} else {
					localObject = new TradeMatcherImpl();
				}
				((TradeMatcher) localObject).init(this, localCommodity.getCommodityID());
				this.tradeMatchers.add(localObject);
				Quotation localQuotation1 = (Quotation) this.quotations.get(localCommodity.getCommodityID());
				Quotation localQuotation2 = (Quotation) this.quotationsClone.get(localCommodity.getCommodityID());
				if (localQuotation1 == null) {
					localQuotation1 = new Quotation();
					localQuotation1.commodityID = localCommodity.getCommodityID();
					localQuotation1.yesterBalancePrice = Double.valueOf(localCommodity.getLastPrice());
					localQuotation1.price = Double.valueOf(localCommodity.getLastPrice());
					localQuotation1.updateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
					this.quotations.put(localQuotation1.commodityID, localQuotation1);
					this.quotationsClone.put(localQuotation1.commodityID, (Quotation) localQuotation1.clone());
				} else {
					localQuotation1.yesterBalancePrice = Double.valueOf(localCommodity.getLastPrice());
					localQuotation2.yesterBalancePrice = Double.valueOf(localCommodity.getLastPrice());
				}
				((TradeMatcher) localObject).start();
			} else {
				localCommodityOrder.factor = localCommodity.getContractFactor();
				localCommodityOrder.minPriceMove = localCommodity.getMinPriceMove();
			}
		}
	}

	public void open() {
		if (this.commodityOrders == null) {
			throw new TradeEngineException("Trade Engine does NOT initiated!");
		}
		if (this.status == 2) {
			this.logger.info("******* Trade Engine RE opened! ********");
		} else if (this.status == 3) {
			this.status = 2;
			this.logger.info("******* Trade Engine opened! ********");
		} else {
			throw new TradeEngineException("Trade Engine does NOT closed!");
		}
	}

	public void close() {
		this.status = 3;
		this.logger.info("******* Trade Engine closed! ********");
	}

	public void pause() {
		this.status = 1;
		this.logger.info("******* Trade Engine paused! ********");
	}

	public void trade() {
		if (this.status != 3) {
			this.status = 0;
			this.logger.info("******* Trade Engine started trading! ********");
		} else {
			throw new TradeEngineException("Trade Engine can not start trading, when closed!");
		}
	}

	public void shutdown() {
		this.logger.info("******* Trade Engine shutdown! ********");
		this.status = 3;
		this.quotationTaker.pleaseStop();
		Iterator localIterator = this.tradeMatchers.iterator();
		while (localIterator.hasNext()) {
			TradeMatcher localTradeMatcher = (TradeMatcher) localIterator.next();
			localTradeMatcher.pleaseStop();
		}
		this.quotationTaker = null;
		this.tradeMatchers = null;
		this.commodityOrders = null;
		this.orderNos = null;
		this.quotations = null;
		this.quotationsClone = null;
	}

	public void pricing() {
		if ((this.status == 2) || (this.status == 1)) {
			if (this.status == 2) {
				pause();
			}
			aggregatePricing();
			this.logger.info("******* Trade Engine execute AggregatePricing ! ********");
		} else {
			throw new TradeEngineException("Trade Engine can not execute aggregate pricing, when closed or trading!");
		}
	}

	public TradeCallback getTradeCallback() {
		return this.tradeCallback;
	}

	public int getStatus() {
		return this.status;
	}

	public long countWaitOrder() {
		long l = 0L;
		Iterator localIterator = this.commodityOrders.values().iterator();
		while (localIterator.hasNext()) {
			CommodityOrder localCommodityOrder = (CommodityOrder) localIterator.next();
			l += localCommodityOrder.waitOrders.size();
		}
		return l;
	}

	public synchronized long getNextTradeNo() {
		return ++this.curTradeNo;
	}

	public synchronized long getNextSimOrderNo() {
		return --this.curSimOrderNo;
	}

	private void loadCommodity(List<Commodity> paramList) {
		this.logger.info("Load Commodity:" + paramList.size());
		this.commodityOrders.clear();
		Iterator localIterator = paramList.iterator();
		while (localIterator.hasNext()) {
			Commodity localCommodity = (Commodity) localIterator.next();
			CommodityOrder localCommodityOrder = new CommodityOrder(localCommodity.getCommodityID());
			localCommodityOrder.factor = localCommodity.getContractFactor();
			localCommodityOrder.minPriceMove = localCommodity.getMinPriceMove();
			localCommodityOrder.spreadUpLmt = localCommodity.getSpreadUpLmt();
			localCommodityOrder.spreadDownLmt = localCommodity.getSpreadDownLmt();
			this.commodityOrders.put(localCommodityOrder.code, localCommodityOrder);
		}
	}

	private void loadQuotation(List<Quotation> paramList) {
		this.logger.info("Load Quotation:" + paramList.size());
		this.quotations.clear();
		this.quotationsClone.clear();
		Iterator localIterator = paramList.iterator();
		while (localIterator.hasNext()) {
			Quotation localQuotation = (Quotation) localIterator.next();
			localQuotation.updateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
			this.quotations.put(localQuotation.commodityID, localQuotation);
			this.quotationsClone.put(localQuotation.commodityID, (Quotation) localQuotation.clone());
		}
	}

	public synchronized void addOrder(Order paramOrder) {
		if (this.status == 3) {
			throw new OrderException("Trade Engine closed!");
		}
		this.logger.debug("Add Order:" + paramOrder.getOrderNo() + " Current TE status:" + this.status);
		Short localShort = paramOrder.getStatus();
		if ((localShort.shortValue() == 1) || (localShort.shortValue() == 2)) {
			if (this.orderNos.add(paramOrder.getOrderNo())) {
				CommodityOrder localCommodityOrder = (CommodityOrder) this.commodityOrders.get(paramOrder.getCommodityID());
				if (localCommodityOrder != null) {
					localCommodityOrder.addWaitOrders(paramOrder);
					OrderTrace.getInstance().writeOrder(paramOrder);
				} else {
					throw new OrderException("Not existed commodity!");
				}
			} else {
				throw new OrderException("Repeated orderNo!");
			}
		} else {
			throw new OrderException("Cannot process Order status:" + paramOrder.getStatus());
		}
	}

	/**
	 * @deprecated
	 */
	public long cancelOrder(Long paramLong) {
		long l = -1L;
		Order localOrder = null;
		Iterator localIterator = this.commodityOrders.values().iterator();
		while (localIterator.hasNext()) {
			CommodityOrder localCommodityOrder = (CommodityOrder) localIterator.next();
			localOrder = localCommodityOrder.cancelOrder(paramLong);
			if (localOrder != null) {
				l = localOrder.getRemainQty().longValue();
				int i = localCommodityOrder.locatePrice(localOrder);
				if ((i <= 5) && (!ServerShell.RunModeIsSIM)) {
					String str = localOrder.getCommodityID();
					Quotation localQuotation = (Quotation) this.quotations.get(str);
					localQuotation.updateTop5(localCommodityOrder);
					this.quotationsClone.put(str, (Quotation) localQuotation.clone());
				}
				this.logger.debug("position:" + i + " updateTime:" + ((Quotation) this.quotations.get(localOrder.getCommodityID())).updateTime);
				this.logger.debug("Cancel Order No:" + paramLong + " canceled quantity:" + l);
				break;
			}
		}
		OrderTrace.getInstance().writeCancelOrder(paramLong);
		return l;
	}

	public long cancelOrder(Order order) throws Exception {
		long l = -1L;
		Object obj = null;
		Long long1 = order.getWithdrawID();
		CommodityOrder commodityorder = (CommodityOrder) commodityOrders.get(order.getCommodityID());
		commodityorder.addWithdrawCnt();
		try {
			synchronized (commodityorder) {
				Order order1;
				try {
					order1 = commodityorder.cancelOrder(long1);
				} catch (Exception exception1) {
					logger.error(
							(new StringBuilder()).append("cmdtyOrder.cancelOrder(").append(long1).append(") fail :").append(exception1).toString());
					logger.info("撤单失败后，重试10次。。。");
					int j = 0;
					do
						try {
							order1 = commodityorder.cancelOrder(long1);
							break;
						} catch (Exception exception2) {
							logger.error((new StringBuilder()).append("retry cmdtyOrder.cancelOrder(").append(long1).append(") fail,errCnt=")
									.append(j).append(" :").append(exception2).toString());
							if (++j >= 10)
								throw new Exception(exception1);
							try {
								Thread.sleep(3L);
							} catch (InterruptedException interruptedexception) {
								logger.error((new StringBuilder()).append("retry cmdtyOrder.cancelOrder(").append(long1)
										.append(") sleep fail,errCnt=").append(j).append(" :").append(interruptedexception).toString());
							}
						}
					while (true);
				}
				if (order1 != null) {
					l = order1.getRemainQty().longValue();
					int i = commodityorder.locatePrice(order1);
					if (i <= 5 && !ServerShell.RunModeIsSIM) {
						String s = order1.getCommodityID();
						Quotation quotation = (Quotation) quotations.get(s);
						quotation.updateTop5(commodityorder);
						quotationsClone.put(s, (Quotation) quotation.clone());
					}
					logger.debug((new StringBuilder()).append("position:").append(i).append(" updateTime:")
							.append(((Quotation) quotations.get(order1.getCommodityID())).updateTime).toString());
					logger.debug((new StringBuilder()).append("Cancel Order No:").append(long1).append(" canceled quantity:").append(l).toString());
				}
			}
			commodityorder.subWithdrawCnt();
		} catch (Exception exception) {
			commodityorder.subWithdrawCnt();
			throw new Exception(exception);
		}
		OrderTrace.getInstance().writeCancelOrder(long1);
		return l;
	}

	public int getMatchInterval() {
		return this.matchInterval;
	}

	public void setMatchInterval(int paramInt) {
		this.logger.info("Set match interval to " + paramInt + " ms.");
		this.matchInterval = paramInt;
	}

	protected void aggregatePricing() {
		Iterator localIterator = this.commodityOrders.values().iterator();
		while (localIterator.hasNext()) {
			CommodityOrder localCommodityOrder = (CommodityOrder) localIterator.next();
			String str = localCommodityOrder.code;
			if (localCommodityOrder.waitOrders.size() > 0) {
				this.logger.info("Pricing cmdty:" + str);
				((CallAuction) DAOBeanFactory.getBean("callAuction")).aggregatePricingByCommodity(localCommodityOrder.waitOrders, this.tradeMatchers,
						((Quotation) this.quotations.get(str)).yesterBalancePrice.doubleValue(), str);
			}
		}
	}

	/**
	 * @deprecated
	 */
	private void aggregatePricingByCommodity(List<Order> paramList) {
		LinkedList localLinkedList1 = new LinkedList();
		LinkedList localLinkedList2 = new LinkedList();
		HashMap localHashMap1 = new HashMap();
		HashMap localHashMap2 = new HashMap();
		HashSet localHashSet = new HashSet();
		for (int i = 0; i < paramList.size(); i++) {
			Order localOrder1 = (Order) paramList.get(i);
			if (localOrder1.getPrice().doubleValue() != 0.0D) {
				localHashSet.add(localOrder1.getPrice());
				int j;
				int k;
				int m;
				Long localLong;
				if (localOrder1.getBuyOrSell().shortValue() == 1) {
					if (localLinkedList1.size() == 0) {
						localLinkedList1.add(localOrder1);
					} else {
						j = 0;
						k = localLinkedList1.size();
						for (m = 0; m < k; m++) {
							if (localOrder1.getPrice().doubleValue() > ((Order) localLinkedList1.get(m)).getPrice().doubleValue()) {
								localLinkedList1.add(m, localOrder1);
								j = 1;
							} else if ((localOrder1.getOrderType().shortValue() == 2) && (localOrder1.getCloseFlag().shortValue() == 2)
									&& (localOrder1.getPrice().doubleValue() == ((Order) localLinkedList1.get(m)).getPrice().doubleValue())) {
								localLinkedList1.add(m, localOrder1);
								j = 1;
							}
							if (j != 0) {
								break;
							}
						}
						if (j == 0) {
							localLinkedList1.addLast(localOrder1);
						}
					}
					localLong = (Long) localHashMap1.get(localOrder1.getPrice());
					if (localLong == null) {
						localHashMap1.put(localOrder1.getPrice(), localOrder1.getQuantity());
					} else {
						localHashMap1.put(localOrder1.getPrice(), Long.valueOf(localLong.longValue() + localOrder1.getQuantity().longValue()));
					}
				} else {
					if (localLinkedList2.size() == 0) {
						localLinkedList2.add(localOrder1);
					} else {
						j = 0;
						k = localLinkedList2.size();
						for (m = 0; m < k; m++) {
							if (localOrder1.getPrice().doubleValue() < ((Order) localLinkedList2.get(m)).getPrice().doubleValue()) {
								localLinkedList2.add(m, localOrder1);
								j = 1;
							} else if ((localOrder1.getOrderType().shortValue() == 2) && (localOrder1.getCloseFlag().shortValue() == 2)
									&& (localOrder1.getPrice().doubleValue() == ((Order) localLinkedList2.get(m)).getPrice().doubleValue())) {
								localLinkedList2.add(m, localOrder1);
								j = 1;
							}
							if (j != 0) {
								break;
							}
						}
						if (j == 0) {
							localLinkedList2.addLast(localOrder1);
						}
					}
					localLong = (Long) localHashMap2.get(localOrder1.getPrice());
					if (localLong == null) {
						localHashMap2.put(localOrder1.getPrice(), localOrder1.getQuantity());
					} else {
						localHashMap2.put(localOrder1.getPrice(), Long.valueOf(localLong.longValue() + localOrder1.getQuantity().longValue()));
					}
				}
			}
		}
		this.logger.debug("All Price:" + localHashSet);
		Iterator localIterator = localHashSet.iterator();
		long l1 = 0L;
		Object localObject1 = new Double(0.0D);
		Object localObject2;
		while (localIterator.hasNext()) {
			long l3 = 0L;
			long l4 = 0L;
			Double localDouble1 = (Double) localIterator.next();
			localObject2 = localHashMap1.keySet().iterator();
			Double localDouble2;
			while (((Iterator) localObject2).hasNext()) {
				localDouble2 = (Double) ((Iterator) localObject2).next();
				if (localDouble2.doubleValue() >= localDouble1.doubleValue()) {
					l3 += ((Long) localHashMap1.get(localDouble2)).intValue();
				}
			}
			this.logger.debug("Price:" + localDouble1 + " buy may trade:" + l3);
			localObject2 = localHashMap2.keySet().iterator();
			while (((Iterator) localObject2).hasNext()) {
				localDouble2 = (Double) ((Iterator) localObject2).next();
				if (localDouble2.doubleValue() <= localDouble1.doubleValue()) {
					l4 += ((Long) localHashMap2.get(localDouble2)).intValue();
				}
			}
			this.logger.debug("Price:" + localDouble1 + " sell may trade:" + l4);
			long l2 = l3 <= l4 ? l3 : l4;
			if (l2 > l1) {
				l1 = l2;
				localObject1 = localDouble1;
			}
		}
		this.logger.debug("Right Price:" + localObject1 + " maxQty:" + l1);
		if (l1 > 0L) {
			localObject2 = null;
			Order localOrder2 = null;
			long l5 = 0L;
			for (int n = 0; (n < localLinkedList1.size()) && (l5 < l1); n++) {
				localObject2 = (Order) localLinkedList1.get(n);
				do {
					long l6 = 0L;
					localOrder2 = (Order) localLinkedList2.getFirst();
					if ((localOrder2 != null) && (localOrder2.getPrice().doubleValue() <= ((Order) localObject2).getPrice().doubleValue())) {
						if (localOrder2.getRemainQty().longValue() <= ((Order) localObject2).getRemainQty().longValue()) {
							localLinkedList2.removeFirst();
							l6 = localOrder2.getRemainQty().longValue();
							((TradeMatcher) this.tradeMatchers.get(0)).matchTrade((Order) localObject2, localOrder2,
									((Double) localObject1).doubleValue(), l6, null);
						} else {
							l6 = ((Order) localObject2).getRemainQty().longValue();
							((TradeMatcher) this.tradeMatchers.get(0)).matchTrade((Order) localObject2, localOrder2,
									((Double) localObject1).doubleValue(), l6, null);
						}
					}
					l5 += l6;
				} while ((((Order) localObject2).getRemainQty().longValue() > 0L) && (l5 < l1));
			}
		}
	}

	List<Quotation> queryQuotation() {
		this.qtQueryList.clear();
		Iterator localIterator = this.quotationsClone.values().iterator();
		while (localIterator.hasNext()) {
			Quotation localQuotation = (Quotation) localIterator.next();
			if (localQuotation.isUpdate) {
				this.qtQueryList.add(localQuotation);
				localQuotation.isUpdate = false;
			}
		}
		return this.qtQueryList;
	}

	public Map<String, Quotation> getQuotations() {
		return this.quotations;
	}

	public Market getMarket() {
		return this.market;
	}

	public void setMarket(Market paramMarket) {
		this.market = paramMarket;
	}
}
