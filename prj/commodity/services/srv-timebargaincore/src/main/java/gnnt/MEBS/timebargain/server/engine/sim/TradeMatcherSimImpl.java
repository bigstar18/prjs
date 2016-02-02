package gnnt.MEBS.timebargain.server.engine.sim;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.engine.AbstractTradeMatcher;
import gnnt.MEBS.timebargain.server.engine.PriceOrder;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.Trade;

public class TradeMatcherSimImpl extends AbstractTradeMatcher {
	private Log logger = LogFactory.getLog(getClass());
	private long lastMatchTime = 0L;
	private long simMatchInterval = 1000L;

	public void pleaseStop() {
		this.stop = true;
	}

	public void run() {
		Quotation localQuotation = (Quotation) this.te.quotations.get(this.commodityCode);
		localQuotation.setCommodityID(this.commodityCode);
		if (localQuotation.curPrice.doubleValue() == 0.0D) {
			this.preTradePrice = localQuotation.closePrice.doubleValue();
		} else {
			this.preTradePrice = localQuotation.curPrice.doubleValue();
		}
		Order localOrder1 = null;
		long l = 0L;
		this.logger.info("*** Sim Trade Matcher started.CmdtyCode:" + this.commodityCode);
		try {
			while (!this.stop) {
				if (this.te.getStatus() == 0) {
					l = System.currentTimeMillis();
					if (l - this.lastMatchTime > this.simMatchInterval) {
						this.lastMatchTime = l;
						Object localObject1 = null;
						PriceOrder localPriceOrder = null;
						Iterator localIterator;
						Order localOrder2;
						if ((localQuotation.buy[0] > 0.0D) && (localQuotation.buyqty[0] > 0L)) {
							synchronized (this.commodityOrder.sellQueue) {
								localIterator = this.commodityOrder.sellQueue.iterator();
								localObject1 = null;
								while (localIterator.hasNext()) {
									localPriceOrder = (PriceOrder) localIterator.next();
									if ((localPriceOrder != null) && (localPriceOrder.orderQueue.size() > 0)) {
										localOrder2 = (Order) localPriceOrder.orderQueue.get(0);
										if (localOrder2.getPrice().doubleValue() <= localQuotation.buy[0]) {
											this.orders.addAll(localPriceOrder.orderQueue);
											localObject1 = localPriceOrder.clone();
											localPriceOrder.orderQueue.clear();
											localIterator.remove();
										}
									} else {
										localObject1 = localPriceOrder;
									}
								}
							}
						}
						if ((localQuotation.sell[0] > 0.0D) && (localQuotation.sellqty[0] > 0L)) {
							synchronized (this.commodityOrder.buyQueue) {
								localIterator = this.commodityOrder.buyQueue.iterator();
								localObject1 = null;
								while (localIterator.hasNext()) {
									localPriceOrder = (PriceOrder) localIterator.next();
									if ((localPriceOrder != null) && (localPriceOrder.orderQueue.size() > 0)) {
										localOrder2 = (Order) localPriceOrder.orderQueue.get(0);
										if (localOrder2.getPrice().doubleValue() >= localQuotation.sell[0]) {
											this.orders.addAll(localPriceOrder.orderQueue);
											localObject1 = localPriceOrder.clone();
											localPriceOrder.orderQueue.clear();
											localIterator.remove();
										}
									} else {
										localObject1 = localPriceOrder;
									}
								}
							}
						}
					}
					while (!this.orders.isEmpty()) {
						synchronized (this.commodityOrder) {
							try {
								if (!this.orders.isEmpty()) {
									localOrder1 = (Order) this.orders.remove(0);
									this.logger.debug("-------Sim match:" + localOrder1.getOrderNo() + " cmdty:" + localOrder1.getCommodityID()
											+ " qty:" + localOrder1.getRemainQty());
									if (localOrder1.getRemainQty().longValue() > 0L) {
										matchOrder(localOrder1);
									}
								}
							} catch (Exception localException3) {
								this.logger.error("Sim Trade Matche Error!", localException3);
							}
						}
					}
				}
				try {
					sleep(this.te.getMatchInterval());
				} catch (Exception localException1) {
				}
			}
		} catch (Exception localException2) {
			this.logger.error("***** Sim TradeMatcher thread down ! *****", localException2);
		}
	}

	public void matchOrder(Order paramOrder) {
		if ((paramOrder.getOrderType().shortValue() == 2) && (2 == paramOrder.getCloseFlag().shortValue())
				&& (paramOrder.getPrice().intValue() == 0)) {
			if (paramOrder.getBuyOrSell().shortValue() == 1) {
				paramOrder.setPrice(Double.valueOf(this.commodityOrder.spreadUpLmt));
			} else {
				paramOrder.setPrice(Double.valueOf(this.commodityOrder.spreadDownLmt));
			}
		}
		Quotation localQuotation = ((Quotation) this.te.quotations.get(paramOrder.getCommodityID())).cloneSimple();
		long l;
		if (paramOrder.getBuyOrSell().shortValue() == 1) {
			if ((localQuotation.sell[0] > 0.0D) && (localQuotation.sellqty[0] > 0L)
					&& (paramOrder.getPrice().doubleValue() >= localQuotation.sell[0])) {
				if (paramOrder.getRemainQty().longValue() > 0L) {
					l = 0L;
					if (paramOrder.getRemainQty().longValue() <= localQuotation.sellqty[0]) {
						l = paramOrder.getRemainQty().longValue();
					} else {
						l = localQuotation.sellqty[0];
					}
					matchTrade(paramOrder, null, localQuotation, l, null, this.tradePriceType);
					if (paramOrder.getRemainQty().longValue() > 0L) {
						this.commodityOrder.enqueueOrder(paramOrder);
					}
				}
			} else {
				this.commodityOrder.enqueueOrder(paramOrder);
			}
		} else
			if ((localQuotation.buy[0] > 0.0D) && (localQuotation.buyqty[0] > 0L) && (paramOrder.getPrice().doubleValue() <= localQuotation.buy[0])) {
			if (paramOrder.getRemainQty().longValue() > 0L) {
				l = 0L;
				if (paramOrder.getRemainQty().longValue() <= localQuotation.buyqty[0]) {
					l = paramOrder.getRemainQty().longValue();
				} else {
					l = localQuotation.buyqty[0];
				}
				matchTrade(null, paramOrder, localQuotation, l, null, this.tradePriceType);
				if (paramOrder.getRemainQty().longValue() > 0L) {
					this.commodityOrder.enqueueOrder(paramOrder);
				}
			}
		} else {
			this.commodityOrder.enqueueOrder(paramOrder);
			return;
		}
	}

	private void matchTrade(Order paramOrder1, Order paramOrder2, Quotation paramQuotation, long paramLong, Short paramShort) {
		matchTrade(paramOrder1, paramOrder2, paramQuotation, paramLong, paramShort, 1);
	}

	private void matchTrade(Order paramOrder1, Order paramOrder2, Quotation paramQuotation, long paramLong, Short paramShort, int paramInt) {
		if ((paramOrder1 == null) && (paramOrder2 == null)) {
			this.logger.error("matchTrade() 中止，原因：buy==null && sell==null");
			return;
		}
		double d = 0.0D;
		if (paramInt == 2) {
			if (paramOrder1 == null) {
				d = getMiddleTradePrice(paramQuotation.buy[0], paramOrder2.getPrice().doubleValue(), this.preTradePrice);
			} else {
				d = getMiddleTradePrice(paramOrder1.getPrice().doubleValue(), paramQuotation.sell[0], this.preTradePrice);
			}
		} else {
			Timestamp localObject = new Timestamp(paramQuotation.updateTime.getTime() + ServerInit.diffTime);
			if (paramOrder1 == null) {
				if (paramOrder2.getOrderTime() == null) {
					paramOrder2.setOrderTime(Server.simServerDAO.getOrderTime(paramOrder2));
				}
				if (paramOrder2.getOrderTime().before((Date) localObject)) {
					d = paramOrder2.getPrice().doubleValue();
				} else {
					d = paramQuotation.buy[0];
				}
			} else {
				if (paramOrder1.getOrderTime() == null) {
					paramOrder1.setOrderTime(Server.simServerDAO.getOrderTime(paramOrder1));
				}
				if (paramOrder1.getOrderTime().before((Date) localObject)) {
					d = paramOrder1.getPrice().doubleValue();
				} else {
					d = paramQuotation.sell[0];
				}
			}
		}
		this.preTradePrice = d;
		Object localObject = "firm_sim";
		Order localOrder = new Order();
		localOrder.setOrderNo(Long.valueOf(this.te.getNextSimOrderNo()));
		localOrder.setQuantity(Long.valueOf(paramLong));
		localOrder.setFirmID((String) localObject);
		localOrder.setCommodityID(paramQuotation.getCommodityID());
		localOrder.setCustomerID((String) localObject + "00");
		localOrder.setFee(Double.valueOf(0.0D));
		localOrder.setMargin(Double.valueOf(0.0D));
		localOrder.setPrice(Double.valueOf(d));
		localOrder.setTradeQty(Long.valueOf(0L));
		localOrder.setStatus(Short.valueOf((short) 1));
		localOrder.setTraderID((String) localObject);
		if (paramOrder1 == null) {
			localOrder.setBuyOrSell(Short.valueOf((short) 1));
			localOrder.setOrderTime(new Date());
			localOrder.setOrderType(Short.valueOf((short) 1));
			paramOrder1 = localOrder;
		} else {
			localOrder.setBuyOrSell(Short.valueOf((short) 2));
			localOrder.setOrderTime(new Date());
			localOrder.setOrderType(Short.valueOf((short) 1));
			paramOrder2 = localOrder;
		}
		Server.simServerDAO.insertOrder(localOrder);
		paramOrder1.setTradeQty(Long.valueOf(paramOrder1.getTradeQty().longValue() + paramLong));
		paramOrder2.setTradeQty(Long.valueOf(paramOrder2.getTradeQty().longValue() + paramLong));
		this.logger.debug("match trade:" + (paramOrder1 != null ? paramOrder1.getOrderNo() : "null") + " "
				+ (paramOrder2 != null ? paramOrder2.getOrderNo() : "null") + " " + d);
		Trade localTrade1 = new Trade();
		localTrade1.setM_TradeNo(Long.valueOf(this.te.getNextTradeNo()));
		localTrade1.setOrderNo(Long.valueOf(paramOrder1 != null ? paramOrder1.getOrderNo().longValue() : 0L));
		localTrade1.setPrice(Double.valueOf(d));
		localTrade1.setQuantity(Long.valueOf(paramLong));
		Trade localTrade2 = new Trade();
		localTrade2.setM_TradeNo(Long.valueOf(this.te.getNextTradeNo()));
		localTrade2.setOrderNo(Long.valueOf(paramOrder2 != null ? paramOrder2.getOrderNo().longValue() : 0L));
		localTrade2.setPrice(Double.valueOf(d));
		localTrade2.setQuantity(Long.valueOf(paramLong));
		localTrade1.setM_TradeNo_Opp(localTrade2.getM_TradeNo());
		localTrade2.setM_TradeNo_Opp(localTrade1.getM_TradeNo());
		if (paramOrder1 == null) {
			this.tradeback.callback(localTrade1, localTrade2);
		} else {
			this.tradeback.callback(localTrade2, localTrade1);
		}
	}

	public void matchTrade(Order paramOrder1, Order paramOrder2, double paramDouble, long paramLong, Short paramShort) {
	}

	public void matchTrade(Order paramOrder1, Order paramOrder2, double paramDouble, long paramLong, Short paramShort, int paramInt) {
	}

	/**
	 * @deprecated
	 */
	private void printQueue(SortedSet paramSortedSet, boolean paramBoolean) {
		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder.append("----未成交委托队列异常[" + (paramBoolean ? "buy" : "sell") + "]---begin-------------\n");
		Iterator localIterator1 = paramSortedSet.iterator();
		PriceOrder localPriceOrder = null;
		int i = 0;
		while (localIterator1.hasNext()) {
			localPriceOrder = (PriceOrder) localIterator1.next();
			localStringBuilder.append("pos=" + i + ", {");
			if (localPriceOrder != null) {
				localStringBuilder.append("po.price=").append(localPriceOrder.price).append(", po.quantity=").append(localPriceOrder.quantity)
						.append("\n");
				if (localPriceOrder.orderQueue.size() == 0) {
					localStringBuilder.append("po.size=0\n");
				} else {
					Iterator localIterator2 = localPriceOrder.orderQueue.iterator();
					Order localOrder = null;
					while (localIterator2.hasNext()) {
						localOrder = (Order) localIterator2.next();
						if (localOrder != null) {
							localStringBuilder.append("[no=" + localOrder.getOrderNo() + ", price=" + localOrder.getPrice() + "],");
						} else {
							localStringBuilder.append("order=null\n");
						}
					}
				}
			} else {
				localStringBuilder.append("po=null\n");
			}
			i++;
			localStringBuilder.append("}\n");
		}
		localStringBuilder.append("----委托队列异常---end-------------\n");
		this.logger.debug(localStringBuilder.toString());
	}

	/**
	 * @deprecated
	 */
	private boolean isOrderQueueNormal(PriceOrder paramPriceOrder1, PriceOrder paramPriceOrder2, boolean paramBoolean) {
		if (paramPriceOrder1 == null) {
			return true;
		}
		if (paramPriceOrder2 == null) {
			return false;
		}
		if (paramPriceOrder1.orderQueue.size() == 0) {
			return false;
		}
		if (paramPriceOrder2.orderQueue.size() == 0) {
			return false;
		}
		if (paramBoolean) {
			return ((Order) paramPriceOrder1.orderQueue.get(0)).getPrice().doubleValue() > ((Order) paramPriceOrder2.orderQueue.get(0)).getPrice()
					.doubleValue();
		}
		return ((Order) paramPriceOrder1.orderQueue.get(0)).getPrice().doubleValue() < ((Order) paramPriceOrder2.orderQueue.get(0)).getPrice()
				.doubleValue();
	}
}
