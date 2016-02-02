package gnnt.MEBS.timebargain.server.engine;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.model.Order;

public class CommodityOrder implements Serializable {
	final transient Log logger = LogFactory.getLog(getClass());
	String code;
	public double factor = 0.0D;
	public double minPriceMove = 1.0D;
	public double spreadUpLmt;
	public double spreadDownLmt;
	public List<Order> waitOrders;
	public SortedSet<PriceOrder> buyQueue;
	public SortedSet<PriceOrder> sellQueue;
	int maxQty = -1;
	private int withdrawCnt;
	private byte[] lockCnt = new byte[0];
	public boolean isMatching = false;
	static Comparator<PriceOrder> buyComparator = new Comparator<PriceOrder>() {
		public int compare(PriceOrder paramAnonymousPriceOrder1, PriceOrder paramAnonymousPriceOrder2) {
			if (paramAnonymousPriceOrder1.price == paramAnonymousPriceOrder2.price) {
				return 0;
			}
			if (paramAnonymousPriceOrder1.price > paramAnonymousPriceOrder2.price) {
				return -1;
			}
			return 1;
		}
	};
	static Comparator<PriceOrder> sellComparator = new Comparator<PriceOrder>() {
		public int compare(PriceOrder paramAnonymousPriceOrder1, PriceOrder paramAnonymousPriceOrder2) {
			if (paramAnonymousPriceOrder1.price == paramAnonymousPriceOrder2.price) {
				return 0;
			}
			if (paramAnonymousPriceOrder1.price < paramAnonymousPriceOrder2.price) {
				return -1;
			}
			return 1;
		}
	};

	CommodityOrder(String paramString) {
		this.code = paramString;
		this.waitOrders = Collections.synchronizedList(new LinkedList());
		this.buyQueue = new TreeSet(buyComparator);
		this.sellQueue = new TreeSet(sellComparator);
	}

	void addWaitOrders(Order paramOrder) {
		this.waitOrders.add(paramOrder);
	}

	public void addWithdrawCnt() {
		synchronized (this.lockCnt) {
			this.withdrawCnt += 1;
		}
	}

	public void subWithdrawCnt() {
		synchronized (this.lockCnt) {
			if (this.withdrawCnt > 0) {
				this.withdrawCnt -= 1;
			}
		}
	}

	public int getWithdrawCnt() {
		return this.withdrawCnt;
	}

	public void setWithdrawCnt(int paramInt) {
		synchronized (this.lockCnt) {
			this.withdrawCnt = paramInt;
		}
	}

	Order cancelOrder(Long paramLong) {
		Order localObject = null;
		ListIterator localListIterator = this.waitOrders.listIterator();
		while (localListIterator.hasNext()) {
			Order localOrder = (Order) localListIterator.next();
			if (localOrder.getOrderNo().equals(paramLong)) {
				localObject = localOrder;
				localListIterator.remove();
				return localObject;
			}
		}
		if (localObject == null) {
			localObject = cancelOrder(paramLong, this.buyQueue);
			if (localObject != null) {
				return localObject;
			}
			localObject = cancelOrder(paramLong, this.sellQueue);
			if (localObject != null) {
				return localObject;
			}
		}
		return localObject;
	}

	private Order cancelOrder(Long paramLong, SortedSet<PriceOrder> paramSortedSet) {
		int i = 0;
		Order localObject = null;
		Iterator localIterator = paramSortedSet.iterator();
		while (localIterator.hasNext()) {
			PriceOrder localPriceOrder = (PriceOrder) localIterator.next();
			i++;
			ListIterator localListIterator = localPriceOrder.orderQueue.listIterator();
			while (localListIterator.hasNext()) {
				Order localOrder = (Order) localListIterator.next();
				if (localOrder.getOrderNo().equals(paramLong)) {
					localObject = localOrder;
					localListIterator.remove();
					localPriceOrder.quantity -= localOrder.getRemainQty().longValue();
					if (localPriceOrder.quantity == 0L) {
						paramSortedSet.remove(localPriceOrder);
					}
					return localObject;
				}
			}
		}
		return localObject;
	}

	public void enqueueOrder(Order paramOrder) {
		if (paramOrder.getBuyOrSell().shortValue() == 1) {
			addOrder(paramOrder, this.buyQueue);
		} else {
			addOrder(paramOrder, this.sellQueue);
		}
	}

	private void addOrder(Order paramOrder, SortedSet<PriceOrder> paramSortedSet) {
		PriceOrder localObject = null;
		Iterator localIterator = paramSortedSet.iterator();
		while (localIterator.hasNext()) {
			PriceOrder localPriceOrder = (PriceOrder) localIterator.next();
			if (localPriceOrder.price == paramOrder.getPrice().doubleValue()) {
				localObject = localPriceOrder;
				break;
			}
		}
		if (localObject == null) {
			localObject = new PriceOrder(paramOrder.getPrice().doubleValue());
			if ((((PriceOrder) localObject).price == this.spreadDownLmt) || (((PriceOrder) localObject).price == this.spreadUpLmt)) {
				((PriceOrder) localObject).isSpreadLmt = true;
			}
			paramSortedSet.add(localObject);
		}
		((PriceOrder) localObject).addOrder(paramOrder);
	}

	public int locatePrice(Order paramOrder) {
		SortedSet localSortedSet = paramOrder.getBuyOrSell().shortValue() == 1 ? this.buyQueue : this.sellQueue;
		int i = 1;
		Iterator localIterator = localSortedSet.iterator();
		while (localIterator.hasNext()) {
			PriceOrder localPriceOrder = (PriceOrder) localIterator.next();
			if (((paramOrder.getBuyOrSell().shortValue() == 1) && (paramOrder.getPrice().doubleValue() >= localPriceOrder.price))
					|| ((paramOrder.getBuyOrSell().shortValue() == 2) && (paramOrder.getPrice().doubleValue() <= localPriceOrder.price))) {
				return i;
			}
			i++;
		}
		return i;
	}
}
