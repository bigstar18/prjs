package gnnt.MEBS.timebargain.plugin.condition;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.plugin.condition.db.ConditionDao;
import gnnt.MEBS.timebargain.plugin.condition.db.DaoFactory;
import gnnt.MEBS.timebargain.plugin.condition.model.ConditionOrder;
import gnnt.MEBS.timebargain.plugin.condition.model.Quotation;
import gnnt.MEBS.timebargain.plugin.condition.model.SystemStatus;
import gnnt.MEBS.timebargain.plugin.condition.rmi.RMIManager;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.Order;

public class CalculateCenter {
	Log log = LogFactory.getLog(CalculateCenter.class);
	public static HashMap<String, ArrayList<ConditionOrder>> m_CP_orders = new HashMap();
	public static HashMap<String, ArrayList<ConditionOrder>> m_Buy1_orders = new HashMap();
	public static HashMap<String, ArrayList<ConditionOrder>> m_Sell1_orders = new HashMap();
	private static ConditionDao db;
	private Consigner consigner;
	private long sessionID = 0L;

	public static ConditionDao getDAOInstance() {
		if (db == null) {
			synchronized (ConditionDao.class) {
				if (db == null) {
					db = DaoFactory.getInstance().getConditionDao(ConditionServer.config);
				}
			}
		}
		return db;
	}

	public CalculateCenter(Config paramConfig, Consigner paramConsigner) {
		this.consigner = paramConsigner;
	}

	public void calculate(Quotation paramQuotation) {
		this.log.debug("calculate--> Quotation info:" + paramQuotation.getCommodityID() + ": q.price=" + paramQuotation.getPrice() + ",q.buy1="
				+ paramQuotation.getBuy1() + ",q.sell1=" + paramQuotation.getSell1());
		ArrayList localArrayList1 = (ArrayList) m_CP_orders.get(paramQuotation.getCommodityID());
		ArrayList localArrayList2 = new ArrayList();
		if ((localArrayList1 != null) && (localArrayList1.size() > 0)) {
			Iterator localObject1 = localArrayList1.iterator();
			while (((Iterator) localObject1).hasNext()) {
				ConditionOrder localObject2 = (ConditionOrder) ((Iterator) localObject1).next();
				if ((paramQuotation.getCommodityID().equals(((ConditionOrder) localObject2).getConditionCmtyID()))
						&& (computeForCP((ConditionOrder) localObject2, paramQuotation))) {
					if (isSyslogon()) {
						int i = sendOrder((ConditionOrder) localObject2);
						db.updateOrder((ConditionOrder) localObject2, Integer.valueOf(i), Integer.valueOf(1));
						localArrayList2.add(localObject2);
						this.log.debug("send order:" + ((ConditionOrder) localObject2).toString());
					} else {
						this.log.info("系统登录失败...");
						break;
					}
				}
			}
			localArrayList1.removeAll(localArrayList2);
			localArrayList2.clear();
			this.log.debug("CP_orders List Info:" + localArrayList1);
		}
		Object localObject1 = (ArrayList) m_Buy1_orders.get(paramQuotation.getCommodityID());
		Object localObject3;
		if ((localObject1 != null) && (((ArrayList) localObject1).size() > 0)) {
			Iterator localObject2 = ((ArrayList) localObject1).iterator();
			while (((Iterator) localObject2).hasNext()) {
				localObject3 = (ConditionOrder) ((Iterator) localObject2).next();
				if ((paramQuotation.getCommodityID().equals(((ConditionOrder) localObject3).getConditionCmtyID()))
						&& (computeForBuy1((ConditionOrder) localObject3, paramQuotation))) {
					if (isSyslogon()) {
						int j = sendOrder((ConditionOrder) localObject3);
						db.updateOrder((ConditionOrder) localObject3, Integer.valueOf(j), Integer.valueOf(1));
						localArrayList2.add(localObject3);
						this.log.debug(" send order:" + ((ConditionOrder) localObject3).toString());
					} else {
						this.log.info("系统登录失败...");
						break;
					}
				}
			}
			((ArrayList) localObject1).removeAll(localArrayList2);
			localArrayList2.clear();
			this.log.debug("buy1_orders List Info:" + localObject1);
		}
		Object localObject2 = (ArrayList) m_Sell1_orders.get(paramQuotation.getCommodityID());
		if ((localObject2 != null) && (((ArrayList) localObject2).size() > 0)) {
			localObject3 = ((ArrayList) localObject2).iterator();
			while (((Iterator) localObject3).hasNext()) {
				ConditionOrder localConditionOrder = (ConditionOrder) ((Iterator) localObject3).next();
				if ((paramQuotation.getCommodityID().equals(localConditionOrder.getConditionCmtyID()))
						&& (computeForSell1(localConditionOrder, paramQuotation))) {
					if (isSyslogon()) {
						int k = sendOrder(localConditionOrder);
						db.updateOrder(localConditionOrder, Integer.valueOf(k), Integer.valueOf(1));
						localArrayList2.add(localConditionOrder);
						this.log.debug(" send order:" + localConditionOrder.toString());
					} else {
						this.log.info("系统登录失败...");
						break;
					}
				}
			}
			((ArrayList) localObject2).removeAll(localArrayList2);
			localArrayList2.clear();
			this.log.debug("sell1_orders List Info:" + localObject2);
		}
	}

	private boolean computeForCP(ConditionOrder paramConditionOrder, Quotation paramQuotation) {
		this.log.debug("--computeForCP: q.price=" + paramQuotation.getPrice() + ",operation=" + paramConditionOrder.getConditionOperation()
				+ ",order.getConditionPrice=" + paramConditionOrder.getConditionPrice());
		if (paramQuotation.getPrice().doubleValue() == 0.0D) {
			return false;
		}
		if (paramQuotation.getPrice().doubleValue() > paramConditionOrder.getConditionPrice().doubleValue()) {
			if ((paramConditionOrder.getConditionOperation().equals(Constant.ABOVE))
					|| (paramConditionOrder.getConditionOperation().equals(Constant.ABOVE_AND_EQUAL))) {
				return true;
			}
		} else if (paramQuotation.getPrice().doubleValue() < paramConditionOrder.getConditionPrice().doubleValue()) {
			if ((paramConditionOrder.getConditionOperation().equals(Constant.BELOW))
					|| (paramConditionOrder.getConditionOperation().equals(Constant.BELOW_AND_EQUAL))) {
				return true;
			}
		} else if ((paramConditionOrder.getConditionOperation().equals(Constant.EQUAL))
				|| (paramConditionOrder.getConditionOperation().equals(Constant.BELOW_AND_EQUAL))
				|| (paramConditionOrder.getConditionOperation().equals(Constant.ABOVE_AND_EQUAL))) {
			return true;
		}
		return false;
	}

	private boolean computeForBuy1(ConditionOrder paramConditionOrder, Quotation paramQuotation) {
		this.log.debug("-- > computeForBuy1:q.Buy1=" + paramQuotation.getBuy1() + ",operation=" + paramConditionOrder.getConditionOperation()
				+ ",order.getConditionPrice=" + paramConditionOrder.getConditionPrice());
		if (paramQuotation.getBuy1() == 0.0D) {
			return false;
		}
		if (paramQuotation.getBuy1() > paramConditionOrder.getConditionPrice().doubleValue()) {
			if ((paramConditionOrder.getConditionOperation().equals(Constant.ABOVE))
					|| (paramConditionOrder.getConditionOperation().equals(Constant.ABOVE_AND_EQUAL))) {
				return true;
			}
		} else if (paramQuotation.getBuy1() < paramConditionOrder.getConditionPrice().doubleValue()) {
			if ((paramConditionOrder.getConditionOperation().equals(Constant.BELOW))
					|| (paramConditionOrder.getConditionOperation().equals(Constant.BELOW_AND_EQUAL))) {
				return true;
			}
		} else if ((paramConditionOrder.getConditionOperation().equals(Constant.EQUAL))
				|| (paramConditionOrder.getConditionOperation().equals(Constant.BELOW_AND_EQUAL))
				|| (paramConditionOrder.getConditionOperation().equals(Constant.ABOVE_AND_EQUAL))) {
			return true;
		}
		return false;
	}

	private boolean computeForSell1(ConditionOrder paramConditionOrder, Quotation paramQuotation) {
		this.log.debug("-- > computeForBuy1:q.Sell1=" + paramQuotation.getSell1() + ",operation=" + paramConditionOrder.getConditionOperation()
				+ ",order.getConditionPrice=" + paramConditionOrder.getConditionPrice());
		if (paramQuotation.getSell1() == 0.0D) {
			return false;
		}
		if (paramQuotation.getSell1() > paramConditionOrder.getConditionPrice().doubleValue()) {
			if ((paramConditionOrder.getConditionOperation().equals(Constant.ABOVE))
					|| (paramConditionOrder.getConditionOperation().equals(Constant.ABOVE_AND_EQUAL))) {
				return true;
			}
		} else if (paramQuotation.getSell1() < paramConditionOrder.getConditionPrice().doubleValue()) {
			if ((paramConditionOrder.getConditionOperation().equals(Constant.BELOW))
					|| (paramConditionOrder.getConditionOperation().equals(Constant.BELOW_AND_EQUAL))) {
				return true;
			}
		} else if ((paramConditionOrder.getConditionOperation().equals(Constant.EQUAL))
				|| (paramConditionOrder.getConditionOperation().equals(Constant.BELOW_AND_EQUAL))
				|| (paramConditionOrder.getConditionOperation().equals(Constant.ABOVE_AND_EQUAL))) {
			return true;
		}
		return false;
	}

	public synchronized void putOrder(ConditionOrder paramConditionOrder) throws Exception {
		this.log.debug("put order:" + paramConditionOrder.toString());
		long l = db.insertOrder(paramConditionOrder);
		paramConditionOrder.setOrderNo(Long.valueOf(l));
		SystemStatus localSystemStatus = db.getSystemStatus();
		if ((localSystemStatus.getStatus().intValue() == 5) && (calculateOne(paramConditionOrder))) {
			return;
		}
		ArrayList localArrayList;
		if (paramConditionOrder.getConditionType().equals(Constant.BASE_ON_PRICE)) {
			localArrayList = (ArrayList) m_CP_orders.get(paramConditionOrder.getConditionCmtyID());
			if (localArrayList == null) {
				localArrayList = new ArrayList();
			}
			localArrayList.add(paramConditionOrder);
			m_CP_orders.put(paramConditionOrder.getConditionCmtyID(), localArrayList);
		} else if (paramConditionOrder.getConditionType().equals(Constant.BASE_ON_BUY1)) {
			localArrayList = (ArrayList) m_Buy1_orders.get(paramConditionOrder.getConditionCmtyID());
			if (localArrayList == null) {
				localArrayList = new ArrayList();
			}
			localArrayList.add(paramConditionOrder);
			m_Buy1_orders.put(paramConditionOrder.getConditionCmtyID(), localArrayList);
		} else if (paramConditionOrder.getConditionType().equals(Constant.BASE_ON_SELL1)) {
			localArrayList = (ArrayList) m_Sell1_orders.get(paramConditionOrder.getConditionCmtyID());
			if (localArrayList == null) {
				localArrayList = new ArrayList();
			}
			localArrayList.add(paramConditionOrder);
			m_Sell1_orders.put(paramConditionOrder.getConditionCmtyID(), localArrayList);
		}
	}

	public boolean calculateOne(ConditionOrder paramConditionOrder) {
		Quotation localQuotation = db.getSingleQuotation(paramConditionOrder.getConditionCmtyID());
		this.log.debug("-->calculateOne: OrderInfo" + paramConditionOrder.toString());
		int i;
		if (paramConditionOrder.getConditionType().equals(Constant.BASE_ON_PRICE)) {
			if ((computeForCP(paramConditionOrder, localQuotation)) && (isSyslogon())) {
				i = sendOrder(paramConditionOrder);
				db.updateOrder(paramConditionOrder, Integer.valueOf(i), Integer.valueOf(1));
				this.log.debug("BASE_ON_PRICE send order:" + paramConditionOrder + "ret=" + i);
				return true;
			}
		} else if (paramConditionOrder.getConditionType().equals(Constant.BASE_ON_BUY1)) {
			if ((computeForBuy1(paramConditionOrder, localQuotation)) && (isSyslogon())) {
				i = sendOrder(paramConditionOrder);
				db.updateOrder(paramConditionOrder, Integer.valueOf(i), Integer.valueOf(1));
				this.log.debug("BASE_ON_BUY1 send order:" + paramConditionOrder + "ret=" + i);
				return true;
			}
		} else if ((paramConditionOrder.getConditionType().equals(Constant.BASE_ON_SELL1)) && (computeForSell1(paramConditionOrder, localQuotation))
				&& (isSyslogon())) {
			i = sendOrder(paramConditionOrder);
			db.updateOrder(paramConditionOrder, Integer.valueOf(i), Integer.valueOf(1));
			this.log.debug("BASE_ON_SELL1 send order:" + paramConditionOrder + "ret=" + i);
			return true;
		}
		return false;
	}

	public synchronized void removeOrder(ConditionOrder paramConditionOrder) throws Exception {
		this.log.debug("remove order : id=" + paramConditionOrder.getOrderNo() + ",firmid=" + paramConditionOrder.getFirmID());
		db.cancelOneOrder(paramConditionOrder);
		ArrayList localArrayList1 = (ArrayList) m_CP_orders.get(paramConditionOrder.getConditionCmtyID());
		if ((localArrayList1 != null) && (localArrayList1.contains(paramConditionOrder))) {
			removeOrderFromList(localArrayList1, paramConditionOrder);
			return;
		}
		ArrayList localArrayList2 = (ArrayList) m_Buy1_orders.get(paramConditionOrder.getConditionCmtyID());
		if ((localArrayList2 != null) && (localArrayList2.contains(paramConditionOrder))) {
			removeOrderFromList(localArrayList2, paramConditionOrder);
			return;
		}
		ArrayList localArrayList3 = (ArrayList) m_Sell1_orders.get(paramConditionOrder.getConditionCmtyID());
		if ((localArrayList3 != null) && (localArrayList3.contains(paramConditionOrder))) {
			removeOrderFromList(localArrayList3, paramConditionOrder);
			return;
		}
	}

	private synchronized ConditionOrder removeOrderFromList(ArrayList<ConditionOrder> paramArrayList, ConditionOrder paramConditionOrder) {
		ConditionOrder localObject = null;
		Iterator localIterator = paramArrayList.iterator();
		while (localIterator.hasNext()) {
			ConditionOrder localConditionOrder = (ConditionOrder) localIterator.next();
			if ((localConditionOrder.getOrderNo() != null) && (localConditionOrder.getOrderNo().equals(paramConditionOrder.getOrderNo()))) {
				localObject = localConditionOrder;
				break;
			}
		}
		paramArrayList.remove(localObject);
		return localObject;
	}

	private int sendOrder(ConditionOrder paramConditionOrder) {
		Order localOrder = new Order();
		localOrder.setCustomerID(paramConditionOrder.getCustomerID());
		localOrder.setCommodityID(paramConditionOrder.getCmtyID());
		localOrder.setConsignerID(this.consigner.getConsignerID());
		localOrder.setBuyOrSell(paramConditionOrder.getBs_flag());
		localOrder.setOrderType(paramConditionOrder.getOrderType());
		localOrder.setPrice(paramConditionOrder.getPrice());
		localOrder.setQuantity(paramConditionOrder.getAmount());
		if (paramConditionOrder.getOrderType().shortValue() == 2) {
			localOrder.setCloseMode(Short.valueOf((short) 1));
		}
		int i = 0;
		try {
			i = RMIManager.tradeRMI.consignerOrder(this.sessionID, localOrder);
		} catch (RemoteException localRemoteException) {
			localRemoteException.printStackTrace();
		}
		return i;
	}

	private boolean isSyslogon() {
		boolean bool = true;
		try {
			if (RMIManager.tradeRMI.getConsignerID(this.sessionID) == null) {
				this.sessionID = RMIManager.tradeRMI.consignerLogon(this.consigner);
				this.log.debug("sessionID :" + this.sessionID);
				if (this.sessionID < 0L) {
					bool = false;
					this.log.info("系统登录失败！失败代码：" + this.sessionID);
				}
			}
		} catch (RemoteException localRemoteException1) {
			this.log.info("检测到条件下单连接交易核心失败，尝试重新登录");
			try {
				RMIManager.initTradeRmi(ConditionServer.TRADE_RMICONF);
				if (RMIManager.tradeRMI.getConsignerID(this.sessionID) == null) {
					this.sessionID = RMIManager.tradeRMI.consignerLogon(this.consigner);
					this.log.debug("sessionID :" + this.sessionID);
					if (this.sessionID < 0L) {
						bool = false;
						this.log.info("重新系统登录失败！失败代码：" + this.sessionID);
					} else {
						this.log.info("重新系统登录成功！");
					}
				}
			} catch (RemoteException localRemoteException2) {
				bool = false;
				localRemoteException2.printStackTrace();
			}
		}
		return bool;
	}

	public void loadData() {
		db.load_CP_orders(m_CP_orders);
		db.load_Buy1_orders(m_Buy1_orders);
		db.load_Sell1_orders(m_Sell1_orders);
		this.log.debug("加载完成：m_CP_orders.size: " + m_CP_orders.size() + ",m_Buy1_orders.size: " + m_Buy1_orders.size() + m_Buy1_orders
				+ ",m_Sell1_orders.size:" + m_Sell1_orders.size());
	}

	public void backUpOrders() {
		this.log.info("备份过期条件委托数据...");
		db.backUpOrder();
		this.log.info("备份成功...");
	}

	public void clearMemData() {
		this.log.info("清空内存委托队列");
		m_CP_orders.clear();
		m_Buy1_orders.clear();
		m_Sell1_orders.clear();
		this.log.info("内存委托队列已清空");
	}
}
