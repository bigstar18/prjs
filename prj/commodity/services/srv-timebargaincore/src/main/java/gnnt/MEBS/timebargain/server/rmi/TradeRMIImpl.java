package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.logonService.vo.CheckUserResultVO;
import gnnt.MEBS.logonService.vo.UserManageVO;
import gnnt.MEBS.timebargain.server.Balance;
import gnnt.MEBS.timebargain.server.OrderProcess;
import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.Customer;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.OrderReturnValue;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.Trader;
import gnnt.MEBS.timebargain.server.util.ActiveUserManager;
import gnnt.MEBS.timebargain.server.util.FrontCheckLogon;
import gnnt.MEBS.timebargain.server.util.StringUtil;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeRMIImpl extends UnicastRemoteObject implements TradeRMI {
	private static final long serialVersionUID = 2690197650654049816L;
	Log log = LogFactory.getLog(getClass());
	private Server server;
	private TradeDAO tradeDAO;
	private ServerDAO serverDAO;
	private FrontCheckLogon logonManager;
	private ServerInit serverInit;
	private ActiveUserManager auManagerConsigner;

	public TradeRMIImpl(Server paramServer) throws RemoteException {
		this.server = paramServer;
		this.tradeDAO = paramServer.getTradeDAO();
		this.serverDAO = paramServer.getServerDAO();
		this.serverInit = paramServer.getServerInit();
		this.logonManager = paramServer.getLogonManager();
		this.auManagerConsigner = paramServer.getAuManagerConsigner();
	}

	public TraderLogonInfo logon(Trader paramTrader) throws RemoteException, Exception {
		if (this.server == null) {
			throw new RemoteException("交易服务器已关闭！");
		}
		TraderLogonInfo localTraderLogonInfo = this.logonManager.logon(paramTrader.getTraderID(), paramTrader.getPassword(), 15,
				paramTrader.getKeyCode(), null, paramTrader.getLogonIP(), paramTrader.getLogonMark(), paramTrader.getLogonType());
		if (localTraderLogonInfo.getSessionID() > 0L) {
			String str = "";
			if (paramTrader.getLogonMark() == 1) {
				str = this.serverDAO.getTraderIDByUserID(paramTrader.getTraderID());
			} else if (paramTrader.getLogonMark() == 0) {
				str = paramTrader.getTraderID();
			}
			this.log.debug("交易员" + str + "，在" + paramTrader.getLogonType() + "端,登录交易服务器成功！");
			loadLoginUserInfoByTraderID(str);
		}
		return localTraderLogonInfo;
	}

	private void loadLoginUserInfoByTraderID(String paramString) {
		this.log.debug("加载交易员：[" + paramString + "]...");
		Trader localTrader = this.serverDAO.getOneTrader(paramString);
		this.serverInit.getTraderQueue().put(paramString, localTrader);
		this.log.debug("交易员：[" + paramString + "] 加载完毕..." + localTrader.toString());
		String str = localTrader.getFirmID();
		this.serverInit.loadOneFirm(str);
		this.serverInit.loadCustomer(str);
	}

	public TraderLogonInfo remoteLogon(String paramString1, long paramLong, String paramString2, String paramString3, int paramInt1,
			String paramString4, int paramInt2) throws RemoteException, Exception {
		TraderLogonInfo localTraderLogonInfo = new TraderLogonInfo();
		if (this.server == null) {
			throw new RemoteException("交易服务器已关闭！");
		}
		Long localLong = new Long(paramLong);
		if (localLong != null) {
			CheckUserResultVO localCheckUserResultVO = this.logonManager.checkUser(paramString1, paramLong, paramString2, paramString3, paramInt1,
					paramString4, paramInt2);
			this.log.debug("CheckUser ; traderID: " + paramString1 + ", logonIP: " + paramString2 + ", fromLogonType: " + paramString3
					+ ", fromModuleID: " + paramInt1 + ", toLogonType: " + paramString4 + ", toModuleID: " + paramInt2);
			this.log.debug(
					"交易员" + paramString1 + "，远程登录， 返回码：" + localCheckUserResultVO.getRecode() + ", 返回信息：" + localCheckUserResultVO.getMessage());
			if (localCheckUserResultVO.getResult() == 1) {
				this.log.debug("交易员" + paramString1 + "远程登录交易服务器成功！");
				localTraderLogonInfo = this.logonManager.getTraderInfo(paramString1);
				localTraderLogonInfo.setSessionID(paramLong);
				loadLoginUserInfoByTraderID(paramString1);
			} else {
				localTraderLogonInfo.setRecode(localCheckUserResultVO.getRecode());
				localTraderLogonInfo.setMessage(localCheckUserResultVO.getMessage());
			}
		} else {
			localTraderLogonInfo.setRecode("-2");
			localTraderLogonInfo.setMessage("没有此用户！");
		}
		return localTraderLogonInfo;
	}

	public boolean isLogon(String paramString1, long paramLong, String paramString2) throws RemoteException, Exception {
		return this.logonManager.isLogon(paramString1, paramLong, 15, paramString2);
	}

	public TraderLogonInfo getTraderInfo(String paramString) throws RemoteException {
		return this.logonManager.getTraderInfo(paramString);
	}

	public long consignerLogon(Consigner paramConsigner) throws RemoteException {
		if (this.server == null) {
			return -204L;
		}
		if ((paramConsigner.getConsignerID() == null) || (paramConsigner.getConsignerID().equals(""))) {
			return -1L;
		}
		if ((paramConsigner.getPassword() == null) || (paramConsigner.getPassword().equals(""))) {
			return -2L;
		}
		long l = this.tradeDAO.consignerLogon(paramConsigner);
		if (l == 0L) {
			l = this.auManagerConsigner.logon(paramConsigner.getConsignerID(), paramConsigner.getLogonIP());
			this.log.debug("代为委托员" + paramConsigner.getConsignerID() + "登录交易服务器成功！");
			Consigner localConsigner = this.serverDAO.getConsigner(paramConsigner.getConsignerID());
			this.serverInit.getConsignerQueue().put(paramConsigner.getConsignerID(), localConsigner);
		}
		return l;
	}

	public OrderReturnValue order(long paramLong, Order paramOrder, String paramString) throws RemoteException, Exception {
		OrderReturnValue localOrderReturnValue = new OrderReturnValue();
		if (this.server == null) {
			localOrderReturnValue.setRetCode(-204);
			return localOrderReturnValue;
		}
		if (paramOrder == null) {
			localOrderReturnValue.setRetCode(-206);
			return localOrderReturnValue;
		}
		if (!isLogon(paramOrder.getTraderID(), paramLong, paramString)) {
			this.log.info("TraderID=" + paramOrder.getTraderID() + ", sessionID=" + paramLong + ", Order:" + paramOrder);
			localOrderReturnValue.setRetCode(1);
			return localOrderReturnValue;
		}
		int i = this.server.getOrderProcess().order(paramOrder);
		localOrderReturnValue.setRetCode(i);
		localOrderReturnValue.setOrderNo(Long.valueOf(paramOrder.getOrderNo() == null ? 0L : paramOrder.getOrderNo().longValue()));
		Date localDate = new Date(System.currentTimeMillis() + this.server.getServerInit().getDiffTime());
		localOrderReturnValue.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate));
		paramOrder.setOrderTime(localDate);
		return localOrderReturnValue;
	}

	public int consignerOrder(long paramLong, Order paramOrder) throws RemoteException {
		if (this.server == null) {
			return -204;
		}
		if (paramOrder == null) {
			return -206;
		}
		String str1 = this.auManagerConsigner.getUserID(paramLong);
		if ((str1 == null) || (!str1.equals(paramOrder.getConsignerID()))) {
			this.log.info("consignerID=" + str1 + ", order.consignerID=" + paramOrder.getConsignerID() + ", Order:" + paramOrder);
			return 1;
		}
		String str2 = paramOrder.getCustomerID();
		Map localMap = this.serverInit.getCustomerQueue();
		if (localMap != null) {
			Customer localCustomer = (Customer) localMap.get(str2);
			if (localCustomer == null) {
				String str3 = this.serverDAO.getFirmByCustmID(str2);
				this.serverInit.loadOneFirm(str3);
				this.serverInit.loadCustomer(str3);
			}
		} else {
			return -204;
		}
		return this.server.getOrderProcess().order(paramOrder);
	}

	public int tradingReComputeFunds() throws RemoteException {
		if (this.server == null) {
			return -204;
		}
		if (this.server.getSystemStatus().getStatus() != 4) {
			return -207;
		}
		return this.tradeDAO.reComputeFunds(2);
	}

	public int checkFrozenQtyAtBalance() throws RemoteException {
		if (this.server == null) {
			throw new RemoteException("交易服务器已关闭！");
		}
		return this.server.getBalance().checkFrozenQtyAtBalance();
	}

	public int balance() throws RemoteException {
		if (this.server == null) {
			throw new RemoteException("交易服务器已关闭！");
		}
		try {
			return this.server.getBalance().balance();
		} catch (Exception localException) {
			throw new RemoteException(localException.getMessage());
		}
	}

	public void logoff(long paramLong, String paramString) throws RemoteException, Exception {
		if (this.server == null) {
			throw new RemoteException("交易服务器已关闭！");
		}
		this.logonManager.logoff(paramLong, 15, paramString);
	}

	public void consignerLogoff(long paramLong) throws RemoteException {
		if (this.server == null) {
			throw new RemoteException("交易服务器已关闭！");
		}
		this.auManagerConsigner.logoff(paramLong);
	}

	public String getUserID(long paramLong, String paramString) throws RemoteException, Exception {
		if (this.server == null) {
			throw new RemoteException("交易服务器已关闭！");
		}
		UserManageVO localUserManageVO = this.logonManager.getUserBySessionID(paramLong, 15, paramString);
		if (localUserManageVO != null) {
			return localUserManageVO.getUserID();
		}
		return null;
	}

	public String getConsignerID(long paramLong) throws RemoteException {
		if (this.server == null) {
			throw new RemoteException("交易服务器已关闭！");
		}
		return this.auManagerConsigner.getUserID(paramLong);
	}

	public List getTraders() throws RemoteException, Exception {
		if (this.server == null) {
			throw new RemoteException("交易服务器已关闭！");
		}
		ArrayList localArrayList = new ArrayList();
		return localArrayList;
	}

	public List getConsigners() throws RemoteException {
		if (this.server == null) {
			throw new RemoteException("交易服务器已关闭！");
		}
		ArrayList localArrayList = new ArrayList();
		String[] arrayOfString1 = this.auManagerConsigner.getAllUsers();
		for (int i = 0; i < arrayOfString1.length; i++) {
			String[] arrayOfString2 = StringUtil.split(arrayOfString1[i], ",");
			HashMap localHashMap = new HashMap();
			localHashMap.put("consignerID", arrayOfString2[0]);
			localHashMap.put("loginTime", arrayOfString2[1]);
			localHashMap.put("loginIP", arrayOfString2[2]);
			localArrayList.add(localHashMap);
		}
		return localArrayList;
	}

	public String getFirmID(String paramString) throws RemoteException {
		if (this.server == null) {
			throw new RemoteException("交易服务器已关闭！");
		}
		return ((Trader) this.server.getServerInit().getTraderQueue().get(paramString)).getFirmID();
	}

	public int settleProcess(String paramString) throws RemoteException {
		if (this.server == null) {
			return -204;
		}
		if ((this.server.getSystemStatus().getStatus() != 1) && (this.server.getSystemStatus().getStatus() != 3)
				&& (this.server.getSystemStatus().getStatus() != 10)) {
			return -202;
		}
		this.log.debug("settleProcess.commodityID:" + paramString);
		int i = this.tradeDAO.settleProcess(paramString);
		if (i == 1) {
			try {
				this.tradeDAO.floatingComputer(null, null, null);
			} catch (Exception localException) {
				this.log.error("手工交收时计算浮亏失败，原因：" + localException);
			}
		}
		return i;
	}

	public int changePassowrd(String paramString1, String paramString2, String paramString3, String paramString4) throws RemoteException {
		if (this.server == null) {
			throw new RemoteException("交易服务器已关闭！");
		}
		return this.logonManager.changePassowrd(paramString1, paramString2, paramString3, paramString4);
	}

	public int deductCloseOrder(Order paramOrder) throws RemoteException {
		this.log.debug("deductCloseOrder.order:" + paramOrder.toString());
		if (this.server.getSystemStatus().getStatus() != 1) {
			return -202;
		}
		String str = this.serverDAO.getFirmByCustmID(paramOrder.getCustomerID());
		if ((str == null) || (str.equals(""))) {
			return -1;
		}
		if ((paramOrder.getBuyOrSell().shortValue() != 1) && (paramOrder.getBuyOrSell().shortValue() != 2)) {
			return -2;
		}
		int i = 0;
		List localList = this.serverInit.getCommodityList();
		for (int j = 0; j < localList.size(); j++) {
			Commodity localCommodity = (Commodity) localList.get(j);
			if (localCommodity.getCommodityID().equals(paramOrder.getCommodityID())) {
				i = 1;
				break;
			}
		}
		if (i == 0) {
			return -3;
		}
		paramOrder.setFirmID(str);
		this.tradeDAO.deductCloseOrder(paramOrder);
		return 0;
	}
}
