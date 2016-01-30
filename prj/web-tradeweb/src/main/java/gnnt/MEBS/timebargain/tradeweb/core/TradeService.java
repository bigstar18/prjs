package gnnt.MEBS.timebargain.tradeweb.core;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.rmi.ServerRMI;
import gnnt.MEBS.timebargain.tradeweb.model.Trade;
import gnnt.MEBS.timebargain.tradeweb.service.ConditionOrderManager;
import gnnt.MEBS.timebargain.tradeweb.service.OrdersManager;
import gnnt.MEBS.timebargain.tradeweb.service.impl.ConditionOrderManagerImpl;
import gnnt.MEBS.timebargain.tradeweb.util.DateUtil;
import gnnt.MEBS.timebargain.tradeweb.webapp.action.HttpXmlServlet;

public class TradeService {
	private static final transient Log log = LogFactory.getLog(TradeService.class);
	private Map tradeMap = new HashMap();
	private OrdersManager mgr;
	private ServerRMI rmi;
	private long maxNo;
	public static long diff;
	private Date dbTime = null;
	private static TradeService service;
	private HttpXmlServlet xmlServlet;
	private Date lastTradeDate = null;
	private int isUpdate;
	private Date localDate = null;
	public String tradeDay = null;
	public boolean isFirst = true;
	public static Map<String, Map<String, Map>> tariff;

	public static TradeService getInstance(OrdersManager paramOrdersManager, HttpXmlServlet paramHttpXmlServlet) {
		if (service == null) {
			synchronized (TradeService.class) {
				service = new TradeService(paramOrdersManager, paramHttpXmlServlet);
			}
		}
		return service;
	}

	private TradeService(OrdersManager paramOrdersManager, HttpXmlServlet paramHttpXmlServlet) {
		this.mgr = paramOrdersManager;
		this.xmlServlet = paramHttpXmlServlet;
		this.rmi = paramHttpXmlServlet.getServerRMI();
		new TradeTaken().start();
		new CheckTime().start();
		new ConditionOrderTaken().start();
	}

	public Map getTradeMap() {
		return this.tradeMap;
	}

	public void setTradeMap(Map paramMap) {
		this.tradeMap = paramMap;
	}

	public long getMaxNo() {
		return this.maxNo;
	}

	public void setMaxNo(long paramLong) {
		this.maxNo = paramLong;
	}

	private int date2int(Date paramDate) {
		String str = DateUtil.formatDate(paramDate, "yyyyMMdd");
		return Integer.valueOf(str).intValue();
	}

	public void setServerRMI(ServerRMI paramServerRMI) {
		this.rmi = paramServerRMI;
	}

	public int getIsUpdate() {
		return this.isUpdate;
	}

	public void setIsUpdate(int paramInt) {
		this.isUpdate = paramInt;
	}

	public static void errorException(Exception paramException) {
		StackTraceElement[] arrayOfStackTraceElement = paramException.getStackTrace();
		log.error(paramException.getMessage());
		for (int i = 0; i < arrayOfStackTraceElement.length; i++) {
			log.error(arrayOfStackTraceElement[i].toString());
		}
	}

	private class CheckTime extends Thread {
		private CheckTime() {
		}

		public void run() {
			TradeService.this.dbTime = TradeService.this.mgr.getDBTime();
			TradeService.log.debug("dbTime :" + TradeService.this.dbTime);
			TradeService.diff = TradeService.this.dbTime.getTime() - System.currentTimeMillis();
			TradeService.log
					.debug("当前时间-数据库时间 diff :" + TradeService.this.dbTime.getTime() + " - " + System.currentTimeMillis() + "=" + TradeService.diff);
			long l = new Date().getTime();
			SystemStatus localSystemStatus = null;
			int i = 0;
			for (;;) {
				try {
					if (TradeService.this.rmi == null) {
						TradeService.this.xmlServlet.initRMI();
						TradeService.this.rmi = TradeService.this.xmlServlet.getServerRMI();
					}
					localSystemStatus = TradeService.this.rmi.getSystemStatus();
					Date localDate = localSystemStatus.getTradeDate();
					if (localDate == null) {
						TradeService.log.info("系统还未设置交易时间");
						Thread.sleep(1000L);
					} else {
						if (System.currentTimeMillis() - l > 60000L) {
							TradeService.log.info("TradeDate =" + localDate);
							l = System.currentTimeMillis();
						}
						if (TradeService.this.localDate == null) {
							TradeService.this.localDate = localDate;
							TradeService.tariff = TradeService.this.mgr.tariff();
						}
						if (i == 0) {
							TradeService.this.tradeDay = new SimpleDateFormat("dd").format(localDate);
							TradeService.log.info("tradeDay=" + TradeService.this.tradeDay);
							i = 1;
							TradeService.tariff = TradeService.this.mgr.tariff();
						}
						if (TradeService.this.date2int(localDate) != TradeService.this.date2int(TradeService.this.localDate)) {
							TradeService.log.info("交易日切换，重新校正DIFF，并清空成交缓存");
							TradeService.this.localDate = localDate;
							TradeService.this.dbTime = TradeService.this.mgr.getDBTime();
							TradeService.diff = TradeService.this.dbTime.getTime() - System.currentTimeMillis();
							TradeService.log.info("dbTime=" + TradeService.this.dbTime.getTime() + ", SystemTime=" + System.currentTimeMillis()
									+ ", diff=" + TradeService.diff);
							TradeService.this.tradeMap.clear();
							TradeService.this.tradeDay = new SimpleDateFormat("dd").format(localDate);
							TradeService.log.info("tradeDay=" + TradeService.this.tradeDay);
							TradeService.this.setMaxNo(0L);
							TradeService.tariff = TradeService.this.mgr.tariff();
							TradeService.this.isFirst = true;
							ConditionOrderManagerImpl.clearConditionOrderUpdateTimeCache();
						}
						Thread.sleep(500L);
					}
				} catch (RemoteException localRemoteException) {
					TradeService.errorException(localRemoteException);
					TradeService.log.error("CheckTime rmi remoteexception exception" + localRemoteException.getMessage());
					localRemoteException.printStackTrace();
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException localInterruptedException) {
						localInterruptedException.printStackTrace();
					}
					try {
						TradeService.this.rmi = null;
						TradeService.this.xmlServlet.initRMI();
						TradeService.this.rmi = TradeService.this.xmlServlet.getServerRMI();
					} catch (Exception localException2) {
						localException2.printStackTrace();
					}
				} catch (Exception localException1) {
					TradeService.log.error("CheckTime Thread Error" + localException1.getMessage());
					TradeService.errorException(localException1);
					localException1.printStackTrace();
				}
			}
		}
	}

	private class TradeTaken extends Thread {
		long max = 0L;
		List tradeList = new LinkedList();
		Trade trade = null;
		String firmID = null;
		LinkedList linked = null;
		int waitTradeCounter = 0;

		private TradeTaken() {
		}

		public void run() {
			TradeService.log.debug("The TradeTaken Thread has start..");
			try {
				for (;;) {
					this.max = TradeService.this.getMaxNo();
					this.tradeList = TradeService.this.mgr.tradequery(this.max);
					if ((this.tradeList != null) && (this.tradeList.size() > 0)) {
						for (int i = 0; i < this.tradeList.size(); i++) {
							this.trade = ((Trade) this.tradeList.get(i));
							this.firmID = this.trade.getFirmID();
							this.linked = ((LinkedList) TradeService.this.tradeMap.get(this.firmID));
							if (this.linked == null) {
								this.linked = new LinkedList();
								TradeService.this.tradeMap.put(this.firmID, this.linked);
							}
							if ((this.max + 1L == this.trade.getA_TradeNo().longValue()) || (this.waitTradeCounter == 3)
									|| (TradeService.this.isFirst)) {
								this.linked.add(this.trade);
								this.max = this.trade.getA_TradeNo().longValue();
								TradeService.this.setMaxNo(this.max);
								this.waitTradeCounter = 0;
								TradeService.this.isFirst = false;
							} else {
								this.waitTradeCounter += 1;
								break;
							}
						}
					}
					Thread.sleep(1000L);
				}
			} catch (Exception localException) {
				TradeService.log.error("TradeTaken Thread Error" + localException.getMessage());
				localException.printStackTrace();
				TradeService.errorException(localException);
			}
		}
	}

	private class ConditionOrderTaken extends Thread {
		private ConditionOrderTaken() {
		}

		public void run() {
			ConditionOrderManager localConditionOrderManager = (ConditionOrderManager) TradeService.this.xmlServlet.getBean("conditionOrderManager");
			for (;;) {
				localConditionOrderManager.conditionOrderUpdateTime_query();
				try {
					Thread.sleep(500L);
				} catch (InterruptedException localInterruptedException) {
					localInterruptedException.printStackTrace();
				}
			}
		}
	}
}
