package gnnt.MEBS.timebargain.server;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.engine.TradeEngine;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.Customer;
import gnnt.MEBS.timebargain.server.model.Firm;
import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.Trader;
import gnnt.MEBS.timebargain.server.util.Arith;

public class OrderProcess {
	private Log log = LogFactory.getLog(getClass());
	private SystemStatus systemStatus;
	private Map firmQueue;
	private Map traderQueue;
	private Map consignerQueue;
	private Map customerQueue;
	private Map commodityQueue;
	private List commodityList;
	private Market market;
	private TradeDAO tradeDAO;
	private TradeEngine tradeEngine;
	private int traderOrderStatus = 1;
	private int consignerOrderStatus = 1;
	public static final int TRADER_ORDER_ACCEPT = 0;
	public static final int TRADER_ORDER_NOTACCEPT = 1;
	public static final int CONSIGNER_ORDER_ACCEPT = 0;
	public static final int CONSIGNER_ORDER_NOTACCEPT = 1;
	private static OrderProcess instance;

	public static OrderProcess getInstance() {
		if (instance == null) {
			instance = new OrderProcess();
		}
		return instance;
	}

	public void init(Server paramServer) {
		this.systemStatus = paramServer.getSystemStatus();
		this.tradeDAO = ((TradeDAO) DAOBeanFactory.getBean("tradeDAO"));
		this.tradeEngine = paramServer.getTradeEngine();
		this.market = paramServer.getServerInit().getMarket();
		this.customerQueue = paramServer.getServerInit().getCustomerQueue();
		this.consignerQueue = paramServer.getServerInit().getConsignerQueue();
		this.traderQueue = paramServer.getServerInit().getTraderQueue();
		this.firmQueue = paramServer.getServerInit().getFirmQueue();
		this.commodityQueue = paramServer.getServerInit().getCommodityQueue();
		this.commodityList = paramServer.getServerInit().getCommodityList();
	}

	public int order(Order paramOrder) {
		try {
			int i = validateOrder(paramOrder);
			if (i != 0) {
				this.log.info("validateOrder,Ret=" + i + ",order:" + paramOrder);
				return i;
			}
			int j = paramOrder.getOrderType().shortValue();
			if (j == 1) {
				i = validateCommodity(paramOrder);
				if (i != 0) {
					this.log.info("openOrder:validateCommodity,Ret=" + i + ",order:" + paramOrder);
					return i;
				}
				i = openOrder(paramOrder);
				if (i != 0) {
					this.log.info("openOrder,Ret=" + i + ",order:" + paramOrder);
					return i;
				}
			} else if (j == 2) {
				i = validateCommodity(paramOrder);
				if (i != 0) {
					this.log.info("closeOrder:validateCommodity,Ret=" + i + ",order:" + paramOrder);
					return i;
				}
				i = closeOrder(paramOrder);
				if (i != 0) {
					this.log.info("closeOrder,Ret=" + i + ",order:" + paramOrder);
					return i;
				}
			} else if (j == 4) {
				i = withdrawOrder(paramOrder);
				this.log.info("withdrawOrder,Ret=" + i + ",order:" + paramOrder);
				if (i != 0) {
					return i;
				}
			}
		} catch (Exception localException) {
			this.log.error("委托失败，" + paramOrder.toString());
			localException.printStackTrace();
			return 200;
		}
		return 0;
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

	private int validateOrder(Order paramOrder) {
		if (this.market.getStatus().shortValue() == 2) {
			return 2;
		}
		String str1 = paramOrder.getTraderID();
		if ((str1 != null) && (!str1.equals("")) && (paramOrder.getConsignerID() != null) && (!paramOrder.getConsignerID().equals(""))) {
			return 5;
		}
		if ((str1 == null) || (str1.equals(""))) {
			if (this.consignerOrderStatus == 1) {
				return 4;
			}
		} else if (this.traderOrderStatus == 1) {
			return 3;
		}
		String str2 = paramOrder.getCustomerID();
		Customer localCustomer = (Customer) this.customerQueue.get(str2);
		if (localCustomer == null) {
			return 32;
		}
		String str3 = localCustomer.getFirmID();
		List localList;
		if ((str1 == null) || (str1.equals(""))) {
			Consigner localObject = (Consigner) this.consignerQueue.get(paramOrder.getConsignerID());
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
		if (((paramOrder.getCloseFlag() == null) || (paramOrder.getCloseFlag().shortValue() != 2)) && (str1 != null) && (!str1.equals(""))) {
			if (((Firm) localObject).getStatus() == 1) {
				return 35;
			}
			if (localCustomer.getStatus() == 1) {
				return 33;
			}
		}
		return 0;
	}

	private int validateCommodity(Order paramOrder) {
		Object localObject = null;
		if ((paramOrder.getTraderID() == null) || (paramOrder.getTraderID().equals(""))) {
			for (int i = 0; i < this.commodityList.size(); i++) {
				Commodity localCommodity = (Commodity) this.commodityList.get(i);
				if (localCommodity.getCommodityID().equals(paramOrder.getCommodityID())) {
					localObject = localCommodity;
					break;
				}
			}
			if (localObject == null) {
				return 15;
			}
			if ((paramOrder.getCloseFlag() != null) && (paramOrder.getCloseFlag().shortValue() == 2)) {
				localObject = (Commodity) ((Map) this.commodityQueue.get(this.systemStatus.getSectionID())).get(paramOrder.getCommodityID());
				if (localObject == null) {
					return 11;
				}
			}
		} else {
			localObject = (Commodity) ((Map) this.commodityQueue.get(this.systemStatus.getSectionID())).get(paramOrder.getCommodityID());
			if (localObject == null) {
				return 11;
			}
		}
		if ((((Commodity) localObject).getOneMaxHoldQty() != -1L)
				&& (paramOrder.getQuantity().longValue() > ((Commodity) localObject).getOneMaxHoldQty())) {
			return -7;
		}
		if (((Commodity) localObject).getStatus() == 2) {
			return 10;
		}
		if ((paramOrder.getCloseFlag() == null) || (paramOrder.getCloseFlag().shortValue() != 2) || (paramOrder.getPrice().doubleValue() != 0.0D)) {
			if (((Commodity) localObject).getSpreadAlgr() != 4) {
				if (paramOrder.getPrice().doubleValue() > ((Commodity) localObject).getSpreadUpLmt()) {
					return 12;
				}
				if (paramOrder.getPrice().doubleValue() < ((Commodity) localObject).getSpreadDownLmt()) {
					return 13;
				}
			}
			if (((Commodity) localObject).getMinPriceMove() != 0.0D) {
				if (!Arith.divideExactly(paramOrder.getPrice().doubleValue(), ((Commodity) localObject).getMinPriceMove())) {
					return 14;
				}
				paramOrder.setPrice(Double.valueOf(Arith.priceFormat(paramOrder.getPrice().doubleValue())));
			} else {
				return 202;
			}
		}
		if (((Commodity) localObject).getMinQuantityMove() != 0) {
			if (paramOrder.getQuantity().longValue() % ((Commodity) localObject).getMinQuantityMove() != 0L) {
				return 16;
			}
		} else {
			return 201;
		}
		return 0;
	}

	private int openOrder(Order paramOrder) {
		String str = ((Customer) this.customerQueue.get(paramOrder.getCustomerID())).getFirmID();
		Firm localFirm = (Firm) this.firmQueue.get(str);
		Commodity localCommodity = (Commodity) ((Map) this.commodityQueue.get(this.systemStatus.getSectionID())).get(paramOrder.getCommodityID());
		double d1 = Function.computeMargin(paramOrder, localFirm, localCommodity);
		if (d1 < 0.0D) {
			return 40;
		}
		double d2 = Function.computeFee(paramOrder, localFirm, localCommodity);
		if (d2 < 0.0D) {
			return 41;
		}
		paramOrder.setMargin(new Double(d1));
		paramOrder.setFee(new Double(d2));
		paramOrder.setFirmID(str);
		long l = 0L;
		if ((paramOrder.getBillTradeType() != null) && (paramOrder.getBillTradeType().shortValue() == 1)) {
			l = this.tradeDAO.sellBillOrder(paramOrder);
		} else {
			l = this.tradeDAO.openOrder(paramOrder);
		}
		this.log.debug("openOrder,orderRet=" + l);
		if (l < 0L) {
			return (int) l;
		}
		paramOrder.setOrderNo(new Long(l));
		paramOrder.setStatus(new Short((short) 1));
		this.log.debug("openOrder,orderno:" + paramOrder.getOrderNo() + ";status:" + paramOrder.getStatus());
		this.tradeEngine.addOrder(paramOrder);
		return 0;
	}

	private int closeOrder(Order paramOrder) {
		if (paramOrder.getCloseMode().shortValue() != 1) {
			if ((paramOrder.getCloseFlag() != null) && (paramOrder.getCloseFlag().shortValue() == 2)) {
				paramOrder.setCloseAlgr(new Short((short) 1));
			} else {
				paramOrder.setCloseAlgr(this.market.getCloseAlgr());
			}
		}
		String str = ((Customer) this.customerQueue.get(paramOrder.getCustomerID())).getFirmID();
		paramOrder.setFirmID(str);
		if (paramOrder.getCloseFlag() == null) {
			paramOrder.setCloseFlag(new Short((short) 0));
		}
		long l = 0L;
		if ((paramOrder.getBillTradeType() != null) && (paramOrder.getBillTradeType().shortValue() == 2)) {
			l = this.tradeDAO.gageCloseOrder(paramOrder);
		} else {
			l = this.tradeDAO.closeOrder(paramOrder);
		}
		this.log.debug("closeOrder,orderRet=" + l);
		if (l < 0L) {
			return (int) l;
		}
		paramOrder.setOrderNo(new Long(l));
		paramOrder.setStatus(new Short((short) 1));
		this.log.debug("closeOrder,orderno:" + paramOrder.getOrderNo() + ";status:" + paramOrder.getStatus());
		this.tradeEngine.addOrder(paramOrder);
		return 0;
	}

	private int withdrawOrder(Order paramOrder) throws Exception {
		this.log.debug("withdrawOrder,order.getWithdrawID():" + paramOrder.getWithdrawID());
		long l = this.tradeEngine.cancelOrder(paramOrder);
		this.log.debug("withdrawOrder,withdrawQty:" + l);
		if (l < 0L) {
			this.log.error("撤单失败，此委托已成交或已撤单:" + paramOrder.getWithdrawID());
			return 42;
		}
		String str = paramOrder.getTraderID();
		if ((str == null) || (str.equals(""))) {
			paramOrder.setWithdrawerID(paramOrder.getConsignerID());
		} else {
			paramOrder.setWithdrawerID(str);
		}
		paramOrder.setWithdrawType(new Short((short) 1));
		paramOrder.setQuantity(Long.valueOf(l));
		try {
			int i = this.tradeDAO.withdraw(paramOrder);
			if (i < 0) {
				this.log.error("撤单失败，" + paramOrder.toString());
				return i;
			}
		} catch (Exception localException1) {
			this.log.error("撤单执行数据库失败，开始进行3次重试。失败委托：" + paramOrder.toString() + "   失败原因：" + localException1.toString());
			localException1.printStackTrace();
			int j = 0;
			int k = 0;
			while (k < 3) {
				try {
					j = this.tradeDAO.withdraw(paramOrder);
					if (j < 0) {
						this.log.error("撤单失败，" + paramOrder.toString());
					}
				} catch (Exception localException2) {
					this.log.error("重复撤单执行数据库失败，正在进行第" + k + "次重试。失败委托：" + paramOrder.toString() + "   失败原因：" + localException1.toString());
					localException1.printStackTrace();
					j = -100;
					k++;
				}
			}
			return j;
		}
		return 0;
	}
}
