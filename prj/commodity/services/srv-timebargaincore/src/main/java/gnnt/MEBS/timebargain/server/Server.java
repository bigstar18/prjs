package gnnt.MEBS.timebargain.server;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.checkLogon.check.front.FrontCheck;
import gnnt.MEBS.logonServerUtil.au.LogonActualize;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.ExtendDAO;
import gnnt.MEBS.timebargain.server.dao.ProxyDAO;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.dao.quotation.jdbc.DBTransferImpl;
import gnnt.MEBS.timebargain.server.dao.sim.SimHqDAO;
import gnnt.MEBS.timebargain.server.dao.sim.SimServerDAO;
import gnnt.MEBS.timebargain.server.delay.DelayDeal;
import gnnt.MEBS.timebargain.server.engine.CommodityOrder;
import gnnt.MEBS.timebargain.server.engine.TradeEngine;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.SysLog;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.TradeTime;
import gnnt.MEBS.timebargain.server.quotation.HQEngine;
import gnnt.MEBS.timebargain.server.util.ActiveUserManager;
import gnnt.MEBS.timebargain.server.util.DateUtil;
import gnnt.MEBS.timebargain.server.util.FrontCheckLogon;

public class Server {
	private Log log = LogFactory.getLog(getClass());
	private ServerInit serverInit;
	private FloatingComputer floatingComputer;
	private TradeTimeTask tradeTimeTask;
	private TradeEngine tradeEngine;
	private HQEngine hqEngine;
	private OrderProcess orderProcess;
	private Balance balance;
	private SystemStatus systemStatus;
	private FrontCheckLogon logonManager;
	private ServerDAO serverDAO;
	private TradeDAO tradeDAO;
	private ExtendDAO extendDAO;
	private ProxyDAO proxyDAO;
	public static SimHqDAO simHqDAO;
	public static SimServerDAO simServerDAO;
	private int sectionStopOrder;
	private static Server instance;
	private DelayDeal delayDeal;
	private ActiveUserManager auManagerConsigner = new ActiveUserManager();
	public static final int SECTIONSTOPORDER_ACCEPT = 1;
	public static final int SECTIONSTOPORDER_NOTACCEPT = 2;
	public static final int ROLETYPE_TRADE = 1;
	public static final int ROLETYPE_SIM = 2;
	public static final int ENDWITHDRAW_YES = 1;
	public static final int ENDWITHDRAW_NO = 2;

	public static Server getInstance() {
		if (instance == null) {
			instance = (Server) DAOBeanFactory.getBean("server");
		}
		return instance;
	}

	public void init() {
		this.systemStatus = new SystemStatus();
		this.serverDAO = ((ServerDAO) DAOBeanFactory.getBean("serverDAO"));
		this.tradeDAO = ((TradeDAO) DAOBeanFactory.getBean("tradeDAO"));
		this.extendDAO = ((ExtendDAO) DAOBeanFactory.getBean("extendDAO"));
		this.proxyDAO = ((ProxyDAO) DAOBeanFactory.getBean("proxyDAO"));
		simHqDAO = (SimHqDAO) DAOBeanFactory.getBean("simHqDAO");
		simServerDAO = (SimServerDAO) DAOBeanFactory.getBean("simServerDAO");
		this.serverInit = ServerInit.getInstance();
		this.floatingComputer = FloatingComputer.getInstance();
		this.tradeTimeTask = new TradeTimeTask();
		this.tradeTimeTask.start();
		this.tradeEngine = new TradeEngine(new TradeProcess(this), new QuotationProcess(this));
		this.tradeEngine.setMarket(getServerInit().getMarket());
		this.hqEngine = new HQEngine(new DBTransferImpl(HQEngine.config));
		this.hqEngine.init();
		this.orderProcess = OrderProcess.getInstance();
		this.balance = Balance.getInstance();
		this.delayDeal = DelayDeal.getInstance();
		Map localMap = null;
		try {
			DataSource localDataSource = (DataSource) DAOBeanFactory.getBean("auDataSource");
			int i = Integer.parseInt(DAOBeanFactory.getConfig("callMode"));
			int j = Integer.parseInt(DAOBeanFactory.getConfig("clearRMITimes"));
			long l = Long.parseLong(DAOBeanFactory.getConfig("timeSpace"));
			localMap = (Map) DAOBeanFactory.getBean("auExpireTimeMap");
			this.log.info("启动 AU RMI 服务：模块[15]连接方式[" + i + "]" + "超时配置[" + localMap + "]线程休眠时间[" + l + "]重连次数[" + j + "]AU类型[" + "front" + "]");
			LogonActualize.createInstance(15, i, localDataSource, localMap, l, j, "front");
			FrontCheck.createInstance(localDataSource);
			this.logonManager = FrontCheckLogon.createInstance(localDataSource);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public void statusListener() {
		int i = this.systemStatus.getStatus();
		StringBuffer localStringBuffer = new StringBuffer();
		if (this.systemStatus.getSectionID() != null) {
			this.log.info("市场处于第" + this.systemStatus.getSectionID() + "交易节！");
			localStringBuffer.append("市场处于第【").append(this.systemStatus.getSectionID()).append("】交易节！");
		}
		localStringBuffer.append("市场状态【").append(Constants.SYSTEM_STATUS[i]).append("】");
		try {
			this.serverDAO.insertSysLog(new SysLog(localStringBuffer.toString(), 1501, 1));
		} catch (Exception localException1) {
			localException1.printStackTrace();
			this.log.error("statusListener方法写系统状态日志到db中失败，原因：" + localException1);
		}
		try {
			this.serverDAO.updateSystemStatus(this.systemStatus);
		} catch (Exception localException2) {
			try {
				this.serverDAO.updateSystemStatus(this.systemStatus);
			} catch (Exception localException3) {
				localException3.printStackTrace();
				this.log.error("statusListener方法更新状态到db中失败，原因：" + localException3);
				this.log.error("*******************警告：更新市场状态到db中失败，timebargain整个应用停止服务，请手工重启此timebargain服务！******************");
				System.exit(1);
				return;
			}
		}
		Iterator localIterator = null;
		int j;
		switch (i) {
		case 0:
			this.log.info("市场初始化完成！");
			this.floatingComputer.setStatus(1);
			this.tradeTimeTask.setRunStatus(0);
			this.orderProcess.setConsignerOrderStatus(0);
			this.orderProcess.setTraderOrderStatus(1);
			break;
		case 1:
			this.log.info("市场闭市状态！");
			this.floatingComputer.setStatus(1);
			this.tradeTimeTask.setRunStatus(2);
			this.orderProcess.setConsignerOrderStatus(1);
			this.orderProcess.setTraderOrderStatus(1);
			this.tradeEngine.close();
			break;
		case 2:
			this.log.info("市场结算中！");
			this.floatingComputer.setStatus(1);
			this.tradeTimeTask.setRunStatus(2);
			this.orderProcess.setConsignerOrderStatus(1);
			this.orderProcess.setTraderOrderStatus(1);
			this.tradeEngine.close();
			break;
		case 3:
			this.log.info(" 资金结算完成！");
			this.floatingComputer.setStatus(1);
			this.tradeTimeTask.setRunStatus(2);
			this.orderProcess.setConsignerOrderStatus(1);
			this.orderProcess.setTraderOrderStatus(1);
			this.tradeEngine.open();
			break;
		case 4:
			this.log.info("市场暂停交易！");
			this.floatingComputer.setStatus(0);
			this.tradeTimeTask.setRunStatus(1);
			this.orderProcess.setConsignerOrderStatus(0);
			this.orderProcess.setTraderOrderStatus(1);
			this.tradeEngine.pause();
			break;
		case 5:
			this.log.info("市场交易中！");
			this.floatingComputer.setStatus(0);
			this.tradeTimeTask.setRunStatus(0);
			this.orderProcess.setConsignerOrderStatus(0);
			this.orderProcess.setTraderOrderStatus(0);
			this.tradeEngine.trade();
			break;
		case 6:
			this.log.info("市场节间休息！");
			this.floatingComputer.setStatus(0);
			this.orderProcess.setConsignerOrderStatus(0);
			if (this.sectionStopOrder == 2) {
				this.orderProcess.setTraderOrderStatus(1);
			} else {
				this.orderProcess.setTraderOrderStatus(0);
			}
			try {
				j = 1;
				while (this.tradeEngine.getStatus() == 0) {
					if (this.tradeEngine.commodityOrders.values() != null) {
						localIterator = this.tradeEngine.commodityOrders.values().iterator();
						while (localIterator.hasNext()) {
							CommodityOrder localCommodityOrder1 = (CommodityOrder) localIterator.next();
							if ((localCommodityOrder1.waitOrders.size() > 0) || (localCommodityOrder1.isMatching)) {
								j = 0;
								break;
							}
							j = 1;
						}
					}
					if (j != 0) {
						break;
					}
					Thread.sleep(100L);
				}
			} catch (Exception localException4) {
				this.log.error(localException4);
			}
			this.tradeEngine.pause();
			this.tradeTimeTask.setRunStatus(0);
			TradeTime localTradeTime1 = this.serverInit.getTradeTimeBySectionID(this.systemStatus.getSectionID(), 0);
			adjustEndQuotationTime(localTradeTime1);
			break;
		case 7:
			this.log.info("市场交易结束！");
			this.floatingComputer.setStatus(0);
			this.tradeTimeTask.setRunStatus(2);
			this.orderProcess.setConsignerOrderStatus(1);
			this.orderProcess.setTraderOrderStatus(1);
			try {
				j = 1;
				while (this.tradeEngine.getStatus() == 0) {
					if (this.tradeEngine.commodityOrders.values() != null) {
						localIterator = this.tradeEngine.commodityOrders.values().iterator();
						while (localIterator.hasNext()) {
							CommodityOrder localCommodityOrder2 = (CommodityOrder) localIterator.next();
							if ((!localCommodityOrder2.waitOrders.isEmpty()) || (localCommodityOrder2.isMatching)) {
								j = 0;
								break;
							}
							j = 1;
						}
					}
					if (j != 0) {
						break;
					}
					Thread.sleep(100L);
				}
			} catch (Exception localException5) {
				this.log.error(localException5);
			}
			this.tradeEngine.pause();
			if (Integer.parseInt(DAOBeanFactory.getConfig("EndWithdraw")) == 1) {
				this.balance.autoWithdraw();
			}
			TradeTime localTradeTime2 = this.serverInit.getTradeTimeBySectionID(this.systemStatus.getSectionID());
			adjustEndQuotationTime(localTradeTime2);
			break;
		case 8:
			this.log.info("市场集合竞价交易中！");
			this.floatingComputer.setStatus(1);
			this.tradeTimeTask.setRunStatus(0);
			this.orderProcess.setConsignerOrderStatus(0);
			this.orderProcess.setTraderOrderStatus(0);
			this.tradeEngine.pause();
			break;
		case 9:
			this.log.info("市场集合竞价交易结束！");
			this.floatingComputer.setStatus(1);
			this.tradeTimeTask.setRunStatus(0);
			this.orderProcess.setConsignerOrderStatus(1);
			this.orderProcess.setTraderOrderStatus(1);
			this.tradeEngine.pricing();
			try {
				Thread.sleep(2500L);
			} catch (InterruptedException localInterruptedException) {
			}
			TradeTime localTradeTime3 = this.serverInit.getTradeTimeBySectionID(this.systemStatus.getSectionID());
			adjustBidQuotationTime(localTradeTime3);
			break;
		case 10:
			this.log.info("交易结算完成！");
			this.floatingComputer.setStatus(1);
			this.tradeTimeTask.setRunStatus(2);
			this.orderProcess.setConsignerOrderStatus(1);
			this.orderProcess.setTraderOrderStatus(1);
			this.tradeEngine.close();
			break;
		default:
			this.log.error("未知交易系统状态[" + i + "]");
		}
	}

	public void autoOpenServer() {
		this.log.info("系统判断是否自动开启交易服务器...");
		this.serverInit.loadMarket();
		if (this.serverInit.getMarket().getRunMode().shortValue() == 0) {
			return;
		}
		if ((this.serverInit.isCrossDay()) && (!this.serverInit.checkTradeDay(this.serverDAO.getCurDbDate()))) {
			return;
		}
		initServer();
	}

	public void autoBalance() {
		if (Integer.parseInt(DAOBeanFactory.getConfig("RoleType")) != 2) {
			return;
		}
		this.log.info("正在进行自动闭市结算．．．");
		try {
			close();
			this.balance.autoWithdraw();
			if (this.balance.checkFrozenQtyAtBalance() != 0) {
				return;
			}
			int i = this.balance.balance();
			if (i == 1) {
				this.log.info("自动闭市结算成功！");
			} else if (i == -1) {
				this.log.info("更新商品保证金错误！");
			} else if (i == -2) {
				this.log.info("交收处理出错！");
			} else if (i == -100) {
				this.log.info("执行存储失败！");
			}
		} catch (Exception localException) {
			this.log.error("自动闭市结算失败，原因：" + localException.getMessage());
		}
	}

	public void close() throws Exception {
		if (this.systemStatus.getStatus() != 7) {
			throw new Exception("交易未结束，不能闭市操作！");
		}
		this.balance.autoWithdraw();
		this.tradeDAO.updateExitCommodity();
		this.systemStatus.setStatus(1);
		statusListener();
	}

	public void stop() {
		if (this.floatingComputer != null) {
			this.floatingComputer.close();
			this.floatingComputer = null;
		}
		if (this.tradeTimeTask != null) {
			this.tradeTimeTask.close();
			this.tradeTimeTask = null;
		}
		if (this.tradeEngine != null) {
			this.tradeEngine.shutdown();
			this.tradeEngine = null;
		}
		if (this.delayDeal != null) {
			this.delayDeal.stop();
			this.delayDeal = null;
		}
		this.serverInit = null;
		this.orderProcess = null;
		this.balance = null;
		this.systemStatus = null;
		this.serverDAO = null;
		this.tradeDAO = null;
		this.extendDAO = null;
	}

	public TradeDAO getTradeDAO() {
		return this.tradeDAO;
	}

	public ServerInit getServerInit() {
		return this.serverInit;
	}

	public SystemStatus getSystemStatus() {
		return this.systemStatus;
	}

	public TradeTimeTask getTradeTimeTask() {
		return this.tradeTimeTask;
	}

	public FloatingComputer getFloatingComputer() {
		return this.floatingComputer;
	}

	public OrderProcess getOrderProcess() {
		return this.orderProcess;
	}

	public ExtendDAO getExtendDAO() {
		return this.extendDAO;
	}

	public ProxyDAO getProxyDAO() {
		return this.proxyDAO;
	}

	public TradeEngine getTradeEngine() {
		return this.tradeEngine;
	}

	public ServerDAO getServerDAO() {
		return this.serverDAO;
	}

	public FrontCheckLogon getLogonManager() {
		return this.logonManager;
	}

	public DelayDeal getDelayDeal() {
		return this.delayDeal;
	}

	public ActiveUserManager getAuManagerConsigner() {
		return this.auManagerConsigner;
	}

	public Balance getBalance() {
		return this.balance;
	}

	public boolean initServer() {
		return initServer(true);
	}

	public boolean initServer(boolean paramBoolean) {
		try {
			this.sectionStopOrder = Integer.parseInt(DAOBeanFactory.getConfig("SectionStopOrder"));
			this.systemStatus = this.serverDAO.getSystemStatus();
			this.log.info("正在初始化ServerInit对象．．．");
			System.out.println(DateUtil.getCurDateTime() + "  正在初始化ServerInit对象．．．");
			this.serverInit.init(this);
			this.log.info("初始化ServerInit对象完毕！");
			System.out.println(DateUtil.getCurDateTime() + "  初始化ServerInit对象完毕！");
			this.log.info("正在初始化延期交易对象．．．");
			System.out.println(DateUtil.getCurDateTime() + "  正在初始化延期交易对象．．．");
			this.delayDeal.init(this);
			this.log.info("初始化延期交易对象完毕！");
			System.out.println(DateUtil.getCurDateTime() + "  初始化延期交易对象完毕！");
			this.log.info("正在初始化闭市结算处理对象．．．");
			System.out.println(DateUtil.getCurDateTime() + "  正在初始化闭市结算处理对象．．．");
			this.balance.init(this);
			this.log.info("初始化闭市结算处理对象完毕！");
			System.out.println(DateUtil.getCurDateTime() + "  初始化闭市结算处理对象完毕！");
			if (paramBoolean) {
				String localObject = DateUtil.formatDate(this.serverDAO.getCurDbDate(), "yyyy-MM-dd");
				if ((((String) localObject).compareTo(DateUtil.formatDate(this.systemStatus.getTradeDate(), "yyyy-MM-dd")) > 0)
						|| (this.systemStatus.getStatus() == 0)
						|| ((((String) localObject).equals(DateUtil.formatDate(this.systemStatus.getTradeDate(), "yyyy-MM-dd")))
								&& (DateUtil.formatDate(this.serverInit.getClearDate(), "yyyy-MM-dd").compareTo((String) localObject) > 0)
								&& (this.systemStatus.getStatus() == 3))) {
					if (!this.serverInit.startCheck()) {
						return false;
					}
					this.log.info("正在切换交易日．．．");
					System.out.println(DateUtil.getCurDateTime() + "  正在切换交易日．．．");
					changeTradeDate(this.serverInit.getClearDate());
					this.log.info("切换交易日完毕！");
					System.out.println(DateUtil.getCurDateTime() + "  切换交易日完毕！");
				}
			}
			this.serverInit.loadInitData();
			this.log.info("正在初始化计算浮亏线程．．．");
			System.out.println(DateUtil.getCurDateTime() + "  正在初始化计算浮亏线程．．．");
			this.floatingComputer.init(this);
			this.log.info("初始化计算浮亏线程完毕！");
			System.out.println(DateUtil.getCurDateTime() + "  初始化计算浮亏线程完毕！");
			this.log.info("正在初始化交易节任务．．．");
			System.out.println(DateUtil.getCurDateTime() + "  正在初始化交易节任务．．．");
			this.tradeTimeTask.init(this);
			this.log.info("初始化交易节任务完毕！");
			System.out.println(DateUtil.getCurDateTime() + "  初始化交易节任务完毕！");
			this.log.info("正在初始化撮合引擎．．．");
			System.out.println(DateUtil.getCurDateTime() + "  正在初始化撮合引擎．．．");
			if (ServerShell.RunModeIsSIM) {
				this.tradeEngine.init(this.serverInit.getCommodityList(), simHqDAO.getQuotationList(), this.tradeDAO.getMaxMatcherTradeNo());
			} else {
				this.tradeEngine.init(this.serverInit.getCommodityList(), this.serverDAO.getQuotationsByTrade(),
						this.tradeDAO.getMaxMatcherTradeNo());
			}
			Object localObject = this.tradeDAO.getNotTradeOrders();
			Iterator localIterator = ((List) localObject).iterator();
			while (localIterator.hasNext()) {
				Order localOrder = (Order) localIterator.next();
				this.tradeEngine.addOrder(localOrder);
			}
			this.log.info("初始化撮合引擎完毕！");
			System.out.println(DateUtil.getCurDateTime() + "  初始化撮合引擎完毕！");
			this.log.info("正在初始化委托处理对象．．．");
			System.out.println(DateUtil.getCurDateTime() + "  正在初始化委托处理对象．．．");
			this.orderProcess.init(this);
			this.log.info("初始化委托处理对象完毕！");
			System.out.println(DateUtil.getCurDateTime() + "  初始化委托处理对象完毕！");
			statusListener();
			if (this.delayDeal.isExistDelayTradeTime()) {
				this.log.info("正在初始化延期操作．．．");
				System.out.println(DateUtil.getCurDateTime() + "  正在初始化延期操作．．．");
				this.delayDeal.initDelayOperate();
				this.log.info("初始化延期操作完毕！");
				System.out.println(DateUtil.getCurDateTime() + "  初始化延期操作完毕！");
			}
			this.serverDAO.insertSysLog(new SysLog("交易服务器启动成功！", 1501, 1));
			if (this.hqEngine.getIsReStart()) {
				this.hqEngine.hqStart();
			}
			printWelcome();
			return true;
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("交易服务器初始化失败，原因：" + localException);
		}
		return false;
	}

	private void printWelcome() {
		System.out.println("=============================================================================");
		System.out.println("=                                                                           =");
		System.out.println("=                                                                           =");
		System.out.println("=                      欢迎进入交易服务器系统!!                             =");
		System.out.println("=                                                                           =");
		System.out.println("=                                                                           =");
		System.out.println("=                                                                           =");
		System.out.println("=                            已成功启动!                                    =");
		System.out.println("=                                                                           =");
		System.out.println("=                                                                           =");
		System.out.println("=                                                                           =");
		System.out.println("=                      北京金网安泰信息技术有限公司.                        =");
		System.out.println("=                                                                           =");
		System.out.println("=                                                                           =");
		System.out.println("=                                                        " + DateUtil.getCurDate() + "         =");
		System.out.println("=============================================================================");
	}

	private void changeTradeDate(Date paramDate) {
		this.systemStatus.setTradeDate(paramDate);
		this.systemStatus.setStatus(0);
		this.systemStatus.setSectionID(null);
		this.systemStatus.setNote(null);
		this.systemStatus.setRecoverTime(null);
	}

	private void adjustBidQuotationTime(TradeTime paramTradeTime) {
		String str = paramTradeTime.getStartDate() + " " + paramTradeTime.getStartTime();
		try {
			Date localDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss",
					paramTradeTime.getBidEndDate() + " " + paramTradeTime.getBidEndTime());
			Timestamp localTimestamp1 = DateUtil.GoSecond(localDate, -2);
			Timestamp localTimestamp2 = Timestamp.valueOf(str);
			Timestamp localTimestamp3 = localTimestamp2;
			this.serverDAO.adjustQuotationTime(localTimestamp1, localTimestamp3, localTimestamp2);
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("调整集合竞价的行情时间为开市时间时出错，原因：" + localException);
		}
	}

	private void adjustEndQuotationTime(TradeTime paramTradeTime) {
		String str = paramTradeTime.getEndDate() + " " + paramTradeTime.getEndTime();
		try {
			Date localDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", str);
			Timestamp localTimestamp1 = Timestamp.valueOf(str);
			Timestamp localTimestamp2 = DateUtil.GoSecond(localDate, 5);
			Timestamp localTimestamp3 = Timestamp.valueOf(str + ".999999");
			this.serverDAO.adjustQuotationTime(localTimestamp1, localTimestamp2, localTimestamp3);
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("调整交易结束后的行情时间为结束时间时出错，原因：" + localException);
		}
	}
}
