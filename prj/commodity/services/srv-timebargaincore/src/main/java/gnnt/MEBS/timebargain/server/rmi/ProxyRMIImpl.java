package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.timebargain.server.OrderProcess;
import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.dao.ProxyDAO;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.ReturnResult;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.util.DateUtil;
import gnnt.MEBS.timebargain.server.util.FrontCheckLogon;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProxyRMIImpl extends UnicastRemoteObject implements ProxyRMI {
	private static final long serialVersionUID = 2690197650654049816L;
	Log log = LogFactory.getLog(getClass());
	private Server server;
	private ServerDAO serverDAO;
	private ProxyDAO proxyDAO;
	private FrontCheckLogon logonManager;

	public ProxyRMIImpl(Server paramServer) throws RemoteException {
		this.server = paramServer;
		this.serverDAO = paramServer.getServerDAO();
		this.proxyDAO = paramServer.getProxyDAO();
		this.logonManager = paramServer.getLogonManager();
	}

	public ReturnResult getCommodityListByID(String paramString1, long paramLong, String paramString2, String paramString3)
			throws RemoteException, Exception {
		ReturnResult localReturnResult = new ReturnResult();
		if (!isLogon(paramString1, paramLong, paramString3)) {
			localReturnResult.retCode = 1;
			return localReturnResult;
		}
		localReturnResult.retCode = 0;
		localReturnResult.vResult = this.serverDAO.getCommodityListByID(paramString2);
		return localReturnResult;
	}

	public ReturnResult getFirmInfoByTraderID(String paramString1, long paramLong, String paramString2) throws RemoteException, Exception {
		ReturnResult localReturnResult = new ReturnResult();
		if (!isLogon(paramString1, paramLong, paramString2)) {
			localReturnResult.retCode = 1;
			return localReturnResult;
		}
		localReturnResult.retCode = 0;
		localReturnResult.vResult.add(this.proxyDAO.getFirmInfoByTraderID(paramString1));
		return localReturnResult;
	}

	public ReturnResult queryCustomerHoldSumInfo(String paramString1, long paramLong, String paramString2, String paramString3, String paramString4,
			String paramString5) throws RemoteException, Exception {
		ReturnResult localReturnResult = new ReturnResult();
		if (!isLogon(paramString1, paramLong, paramString5)) {
			localReturnResult.retCode = 1;
			return localReturnResult;
		}
		localReturnResult.retCode = 0;
		localReturnResult.vResult = this.proxyDAO.queryCustomerHoldSumInfo(paramString2, paramString3, paramString4);
		return localReturnResult;
	}

	public ReturnResult queryOrder(String paramString1, long paramLong, Order paramOrder, String paramString2) throws RemoteException, Exception {
		ReturnResult localReturnResult = new ReturnResult();
		if (!isLogon(paramString1, paramLong, paramString2)) {
			localReturnResult.retCode = 1;
			return localReturnResult;
		}
		localReturnResult.retCode = 0;
		localReturnResult.vResult = this.proxyDAO.queryOrder(paramOrder);
		return localReturnResult;
	}

	public ReturnResult queryTradeInfo(String paramString1, long paramLong1, long paramLong2, String paramString2, String paramString3)
			throws RemoteException, Exception {
		return queryTradeInfo(paramString1, paramLong1, paramLong2, paramString2, -1L, paramString3);
	}

	public ReturnResult queryTradeInfo(String paramString1, long paramLong1, long paramLong2, String paramString2, long paramLong3,
			String paramString3) throws RemoteException, Exception {
		ReturnResult localReturnResult = new ReturnResult();
		if (!isLogon(paramString1, paramLong1, paramString3)) {
			localReturnResult.retCode = 1;
			return localReturnResult;
		}
		localReturnResult.retCode = 0;
		localReturnResult.vResult = this.proxyDAO.queryTradeInfo(paramLong2, paramString2);
		if (paramLong3 >= 0L) {
			localReturnResult.vResult2 = this.proxyDAO.getBroadcasts(paramLong3, paramString2);
		}
		return localReturnResult;
	}

	public ReturnResult queryQuotation(String paramString) throws RemoteException {
		ReturnResult localReturnResult = new ReturnResult();
		Date localDate = null;
		if ((paramString != null) && (!paramString.equals(""))) {
			String str = DateUtil.formatDate(this.server.getSystemStatus().getTradeDate(), "yyyy-MM-dd");
			try {
				localDate = DateUtil.convertStringToDate("yyyy-MM-dd HHmmss", str + " " + paramString);
			} catch (ParseException localParseException) {
				this.log.error("查询某时间后的行情时转换日期失败：" + localParseException);
				localReturnResult.retCode = 2;
				return localReturnResult;
			}
		}
		localReturnResult.retCode = 0;
		localReturnResult.vResult = this.serverDAO.queryQuotationByTime(localDate);
		return localReturnResult;
	}

	public ReturnResult order(long paramLong, Order paramOrder, String paramString) throws RemoteException, Exception {
		ReturnResult localReturnResult = new ReturnResult();
		if (this.server == null) {
			localReturnResult.retCode = -204;
			return localReturnResult;
		}
		if (paramOrder == null) {
			localReturnResult.retCode = -206;
			return localReturnResult;
		}
		if (!isLogon(paramOrder.getTraderID(), paramLong, paramString)) {
			localReturnResult.retCode = 1;
			return localReturnResult;
		}
		int i = this.server.getOrderProcess().order(paramOrder);
		if (i == 0) {
			localReturnResult.vResult.add(0, paramOrder.getOrderNo());
			localReturnResult.vResult.add(1, paramOrder.getQuantity());
		}
		return localReturnResult;
	}

	public ReturnResult getSystemStatus(String paramString1, long paramLong, String paramString2) throws RemoteException, Exception {
		ReturnResult localReturnResult = new ReturnResult();
		if (this.server == null) {
			localReturnResult.retCode = -204;
			return localReturnResult;
		}
		if (!isLogon(paramString1, paramLong, paramString2)) {
			localReturnResult.retCode = 1;
			return localReturnResult;
		}
		localReturnResult.retCode = 0;
		localReturnResult.vResult.add(0, this.server.getSystemStatus());
		return localReturnResult;
	}

	public boolean isLogon(String paramString1, long paramLong, String paramString2) throws RemoteException, Exception {
		return this.logonManager.isLogon(paramString1, paramLong, 15, paramString2);
	}
}
