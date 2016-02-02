package gnnt.MEBS.timebargain.server.delay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.Function;
import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.DelayDAO;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.engine.TradeEngine;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.Customer;
import gnnt.MEBS.timebargain.server.model.DelayOrder;
import gnnt.MEBS.timebargain.server.model.DelayQuotation;
import gnnt.MEBS.timebargain.server.model.Firm;
import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.SysLog;
import gnnt.MEBS.timebargain.server.model.Trader;

public class DelayOrderProcess {
	private Log log = LogFactory.getLog(getClass());
	private Server server;
	private Map firmQueue;
	private Map traderQueue;
	private Map consignerQueue;
	private Map customerQueue;
	private Market market;
	private DelayDAO delayDAO;
	private ServerDAO serverDAO;
	private TradeEngine tradeEngine;
	private int traderOrderStatus = 1;
	private int consignerOrderStatus = 1;
	public static final int TRADER_ORDER_ACCEPT = 0;
	public static final int TRADER_ORDER_NOTACCEPT = 1;
	public static final int CONSIGNER_ORDER_ACCEPT = 0;
	public static final int CONSIGNER_ORDER_NOTACCEPT = 1;
	private static DelayOrderProcess instance;
	private Map commoditySettleProp;

	public static DelayOrderProcess getInstance() {
		if (instance == null) {
			instance = new DelayOrderProcess();
		}
		return instance;
	}

	public void init(Server paramServer) {
		this.server = paramServer;
		this.tradeEngine = paramServer.getTradeEngine();
		this.delayDAO = ((DelayDAO) DAOBeanFactory.getBean("delayDAO"));
		this.serverDAO = ((ServerDAO) DAOBeanFactory.getBean("serverDAO"));
		this.market = paramServer.getServerInit().getMarket();
		this.customerQueue = paramServer.getServerInit().getCustomerQueue();
		this.consignerQueue = paramServer.getServerInit().getConsignerQueue();
		this.traderQueue = paramServer.getServerInit().getTraderQueue();
		this.firmQueue = paramServer.getServerInit().getFirmQueue();
		this.commoditySettleProp = new HashMap();
		refreshCommoditySettleProp();
	}

	public void refreshCommoditySettleProp() {
		this.delayDAO.loadCommoditySettleProp(this.commoditySettleProp);
	}

	public int order(DelayOrder paramDelayOrder) {
		this.log.debug("DelayOrder:" + paramDelayOrder);
		try {
			paramDelayOrder.setPrice(((Quotation) this.tradeEngine.getQuotations().get(paramDelayOrder.getCommodityID())).getPrice());
			this.log.debug("DelayOrder.getPrice():" + paramDelayOrder.getPrice());
			int i = validateOrder(paramDelayOrder);
			if (i != 0) {
				return i;
			}
			paramDelayOrder.setDelayQuoShowType(this.market.getDelayQuoShowType());
			int j = paramDelayOrder.getDelayOrderType().shortValue();
			int k;
			if (j == 1) {
				if (this.server.getDelayDeal().getDelayStatus().getStatus() != 1) {
					return 50;
				}
				i = validateCommodity(paramDelayOrder);
				if (i != 0) {
					return i;
				}
				k = paramDelayOrder.getBuyOrSell().shortValue();
				if (k == 1) {
					i = buySettleOrder(paramDelayOrder);
				} else if (k == 2) {
					i = sellSettleOrder(paramDelayOrder);
				}
				if (i != 0) {
					return i;
				}
			} else if (j == 2) {
				if (this.server.getDelayDeal().getDelayStatus().getStatus() != 3) {
					return 51;
				}
				k = paramDelayOrder.getBuyOrSell().shortValue();
				DelayQuotation localDelayQuotation = (DelayQuotation) this.server.getDelayDeal().getNeutralSideMap()
						.get(paramDelayOrder.getCommodityID());
				if ((localDelayQuotation == null) || (localDelayQuotation.getNeutralSide().shortValue() == 0)) {
					return 52;
				}
				if (localDelayQuotation.getNeutralSide().shortValue() != k) {
					return 53;
				}
				i = validateCommodity(paramDelayOrder);
				if (i != 0) {
					return i;
				}
				if (k == 1) {
					i = buyNeutralOrder(paramDelayOrder);
				} else if (k == 2) {
					i = sellNeutralOrder(paramDelayOrder);
				}
				if (i != 0) {
					return i;
				}
			} else if (j == 4) {
				k = paramDelayOrder.getWd_DelayOrderType().shortValue();
				if (k == 1) {
					if (this.server.getDelayDeal().getDelayStatus().getStatus() != 1) {
						return 50;
					}
				} else if (k == 2) {
					if (this.server.getDelayDeal().getDelayStatus().getStatus() != 3) {
						return 51;
					}
				} else {
					return 54;
				}
				i = withdrawOrder(paramDelayOrder);
				if (i != 0) {
					return i;
				}
			}
		} catch (Exception localException) {
			this.log.error("延期委托失败，" + paramDelayOrder.toString());
			localException.printStackTrace();
			return 200;
		}
		return 0;
	}

	public void autoWithdraw() {
		this.log.debug("正在自动撤延期委托单...");
		List localList = this.delayDAO.getNotTradeOrderNos();
		int i = 0;
		int j = 1;
		for (int k = 0; k < localList.size(); k++) {
			Long localLong = (Long) localList.get(k);
			DelayOrder localDelayOrder = new DelayOrder();
			localDelayOrder.setWithdrawerID(null);
			localDelayOrder.setWithdrawID(localLong);
			localDelayOrder.setWithdrawType(new Short((short) 4));
			localDelayOrder.setQuantity(null);
			localDelayOrder.setDelayQuoShowType(this.market.getDelayQuoShowType());
			i = this.delayDAO.withdraw(localDelayOrder);
			if ((i == -100) || (i == -99)) {
				this.log.info("闭市时自动撤延期委托号为" + localLong + "的单时执行撤单存储失败！");
				j = 0;
			}
		}
		if (j == 0) {
			this.log.debug("自动撤延期委托单失败");
			this.serverDAO.insertSysLog(new SysLog("自动撤延期委托单失败", 1502, 0));
		} else {
			this.log.debug("自动撤延期委托单成功");
		}
	}

	public int getConsignerOrderStatus() {
		return this.consignerOrderStatus;
	}

	public void setConsignerOrderStatus(int paramInt) {
		this.consignerOrderStatus = paramInt;
	}

	public int getTraderOrderStatus() {
		return this.traderOrderStatus;
	}

	public void setTraderOrderStatus(int paramInt) {
		this.traderOrderStatus = paramInt;
	}

	private int validateOrder(DelayOrder paramDelayOrder) {
		if (this.market.getStatus().shortValue() == 2) {
			return 2;
		}
		String str1 = paramDelayOrder.getTraderID();
		if ((str1 != null) && (!str1.equals("")) && (paramDelayOrder.getConsignerID() != null) && (!paramDelayOrder.getConsignerID().equals(""))) {
			return 5;
		}
		if ((str1 == null) || (str1.equals(""))) {
			if (this.consignerOrderStatus == 1) {
				return 4;
			}
		} else if (this.traderOrderStatus == 1) {
			return 3;
		}
		String str2 = paramDelayOrder.getCustomerID();
		Customer localCustomer = (Customer) this.customerQueue.get(str2);
		if (localCustomer == null) {
			return 32;
		}
		String str3 = localCustomer.getFirmID();
		List localList;
		if ((str1 == null) || (str1.equals(""))) {
			Consigner localObject = (Consigner) this.consignerQueue.get(paramDelayOrder.getConsignerID());
			if (localObject == null) {
				return 38;
			}
			localList = ((Consigner) localObject).getOperateFirmList();
			if ((localList != null) && (localList.size() > 0) && (!localList.contains(str3))) {
				return 37;
			}
		} else {
			Trader localObject = (Trader) this.traderQueue.get(str1);
			if (localObject == null) {
				return 30;
			}
			localList = ((Trader) localObject).getOperateCustomerList();
			if ((localList != null) && (localList.size() > 0) && (!localList.contains(str2))) {
				return 31;
			}
		}
		Object localObject = (Firm) this.firmQueue.get(str3);
		if (localObject == null) {
			return 34;
		}
		if (((Firm) localObject).getStatus() == 1) {
			return 35;
		}
		if (localCustomer.getStatus() == 1) {
			return 33;
		}
		return 0;
	}

	private int validateCommodity(DelayOrder paramDelayOrder) {
		Commodity localCommodity = this.server.getServerInit().getCommodityInListByID(paramDelayOrder.getCommodityID());
		if (localCommodity == null) {
			return 15;
		}
		if (localCommodity.getStatus() == 2) {
			return 10;
		}
		Map localMap = (Map) this.commoditySettleProp.get(Integer.valueOf(paramDelayOrder.getDelayOrderType().shortValue()));
		if ((localMap == null) || (localMap.get(localCommodity.getCommodityID()) == null)) {
			if (paramDelayOrder.getDelayOrderType().shortValue() == 1) {
				return 55;
			}
			return 56;
		}
		if (paramDelayOrder.getQuantity().longValue() % localCommodity.getMinSettleMoveQty() != 0L) {
			return 16;
		}
		if (paramDelayOrder.getQuantity().longValue() < localCommodity.getMinSettleQty()) {
			return 17;
		}
		return 0;
	}

	private int buySettleOrder(DelayOrder paramDelayOrder) {
		String str = ((Customer) this.customerQueue.get(paramDelayOrder.getCustomerID())).getFirmID();
		Firm localFirm = (Firm) this.firmQueue.get(str);
		Commodity localCommodity = this.server.getServerInit().getCommodityInListByID(paramDelayOrder.getCommodityID());
		Order localOrder = new Order();
		localOrder.setCommodityID(paramDelayOrder.getCommodityID());
		localOrder.setQuantity(paramDelayOrder.getQuantity());
		localOrder.setPrice(paramDelayOrder.getPrice());
		localOrder.setBuyOrSell(paramDelayOrder.getBuyOrSell());
		double d = Function.computeMargin(localOrder, localFirm, localCommodity);
		if (d < 0.0D) {
			return 40;
		}
		paramDelayOrder.setMargin(new Double(d));
		paramDelayOrder.setFirmID(str);
		long l = this.delayDAO.buySettleOrder(paramDelayOrder);
		this.log.debug("buySettleOrder,orderRet=" + l);
		if (l < 0L) {
			return (int) l;
		}
		paramDelayOrder.setOrderNo(new Long(l));
		paramDelayOrder.setStatus(new Short((short) 1));
		this.log.debug("buySettleOrder,orderno:" + paramDelayOrder.getOrderNo() + ";status:" + paramDelayOrder.getStatus());
		return 0;
	}

	private int sellSettleOrder(DelayOrder paramDelayOrder) {
		String str = ((Customer) this.customerQueue.get(paramDelayOrder.getCustomerID())).getFirmID();
		paramDelayOrder.setFirmID(str);
		paramDelayOrder.setDelayNeedBill(this.market.getDelayNeedBill());
		long l = this.delayDAO.sellSettleOrder(paramDelayOrder);
		this.log.debug("sellSettleOrder,orderRet=" + l);
		if (l < 0L) {
			return (int) l;
		}
		paramDelayOrder.setOrderNo(new Long(l));
		paramDelayOrder.setStatus(new Short((short) 1));
		this.log.debug("sellSettleOrder,orderno:" + paramDelayOrder.getOrderNo() + ";status:" + paramDelayOrder.getStatus());
		return 0;
	}

	private int withdrawOrder(DelayOrder paramDelayOrder) {
		this.log.debug("delayWithdrawOrder,order.getWithdrawID():" + paramDelayOrder.getWithdrawID());
		long l = -1L;
		String str = paramDelayOrder.getTraderID();
		if ((str == null) || (str.equals(""))) {
			paramDelayOrder.setWithdrawerID(paramDelayOrder.getConsignerID());
		} else {
			paramDelayOrder.setWithdrawerID(str);
		}
		paramDelayOrder.setWithdrawType(new Short((short) 1));
		paramDelayOrder.setQuantity(Long.valueOf(l));
		int i = this.delayDAO.withdraw(paramDelayOrder);
		if (i < 0) {
			this.log.error("延期撤单失败，" + paramDelayOrder.toString());
			return i;
		}
		return 0;
	}

	private int buyNeutralOrder(DelayOrder paramDelayOrder) {
		String str = ((Customer) this.customerQueue.get(paramDelayOrder.getCustomerID())).getFirmID();
		paramDelayOrder.setFirmID(str);
		long l = this.delayDAO.buyNeutralOrder(paramDelayOrder);
		this.log.debug("buyNeutralOrder,orderRet=" + l);
		if (l < 0L) {
			return (int) l;
		}
		paramDelayOrder.setOrderNo(new Long(l));
		paramDelayOrder.setStatus(new Short((short) 1));
		this.log.debug("buyNeutralOrder,orderno:" + paramDelayOrder.getOrderNo() + ";status:" + paramDelayOrder.getStatus());
		return 0;
	}

	private int sellNeutralOrder(DelayOrder paramDelayOrder) {
		String str = ((Customer) this.customerQueue.get(paramDelayOrder.getCustomerID())).getFirmID();
		paramDelayOrder.setFirmID(str);
		paramDelayOrder.setDelayNeedBill(this.market.getDelayNeedBill());
		long l = this.delayDAO.sellNeutralOrder(paramDelayOrder);
		this.log.debug("sellNeutralOrder,orderRet=" + l);
		if (l < 0L) {
			return (int) l;
		}
		paramDelayOrder.setOrderNo(new Long(l));
		paramDelayOrder.setStatus(new Short((short) 1));
		this.log.debug("sellNeutralOrder,orderno:" + paramDelayOrder.getOrderNo() + ";status:" + paramDelayOrder.getStatus());
		return 0;
	}
}
