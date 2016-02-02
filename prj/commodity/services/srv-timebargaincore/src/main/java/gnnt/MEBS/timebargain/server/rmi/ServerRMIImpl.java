package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.TradeTimeTask;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.delay.DelayDeal;
import gnnt.MEBS.timebargain.server.delay.DelayOrderProcess;
import gnnt.MEBS.timebargain.server.engine.TradeEngine;
import gnnt.MEBS.timebargain.server.model.Firm;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.util.DateUtil;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerRMIImpl extends UnicastRemoteObject implements ServerRMI {
	private static final long serialVersionUID = 2690197650654049814L;
	Log log = LogFactory.getLog(getClass());
	private Server server;
	private ServerDAO serverDAO;

	public ServerRMIImpl(Server paramServer) throws RemoteException {
		this.server = paramServer;
		this.serverDAO = paramServer.getServerDAO();
	}

	public void start() throws RemoteException {
		if ((!this.server.getServerInit().isCrossDay())
				&& (DateUtil.formatDate(this.serverDAO.getCurDbDate(), "yyyy-MM-dd")
						.equals(DateUtil.formatDate(this.server.getSystemStatus().getTradeDate(), "yyyy-MM-dd")))
				&& (this.server.getSystemStatus().getStatus() == 3)) {
			throw new RemoteException("资金结算已完成，当天不能做开市准备！");
		}
		if (!this.server.initServer()) {
			throw new RemoteException("交易服务器初始化失败！");
		}
	}

	public void close() throws RemoteException {
		if (this.server.getSystemStatus().getStatus() == 1) {
			throw new RemoteException("交易服务器已闭市！");
		}
		try {
			if (this.server.getDelayDeal().isExistDelayTradeTime()) {
				this.log.info("延期交易闭市操作");
				this.server.getDelayDeal().close();
			}
			this.log.info("交易服务器闭市操作");
			this.server.close();
			this.log.info("闭市操作完成");
		} catch (Exception localException) {
			throw new RemoteException(localException.getMessage());
		}
	}

	public void ctlTrade(int paramInt) throws RemoteException {
		if (paramInt == 0) {
			if ((this.server.getSystemStatus().getStatus() == 5) || (this.server.getSystemStatus().getStatus() == 8)
					|| (this.server.getSystemStatus().getStatus() == 6)) {
				this.server.getSystemStatus().setStatus(4);
				this.server.statusListener();
			} else {
				throw new RemoteException("非交易时间，不允许暂停！");
			}
		} else if ((paramInt == 1) && (this.server.getSystemStatus().getStatus() == 4)) {
			this.server.getTradeTimeTask().recoverRun();
		}
	}

	public void timingContinueTrade(String paramString) throws RemoteException {
		try {
			DateUtil.convertStringToDate("HH:mm:ss", paramString);
		} catch (Exception localException) {
			throw new RemoteException(paramString + "时间格式不正确！");
		}
		this.server.getSystemStatus().setRecoverTime(paramString);
		this.serverDAO.updateSystemRecoverTime(paramString);
	}

	public void tradeEnd() throws RemoteException {
		this.server.getSystemStatus().setStatus(7);
		this.server.statusListener();
	}

	public void recoverTrade() throws RemoteException {
		if (this.server.getSystemStatus().getStatus() != 7) {
			throw new RemoteException("不是交易结束状态，不能恢复交易！");
		}
		if ((!this.server.getServerInit().isCrossDay()) && (!DateUtil.formatDate(this.server.getSystemStatus().getTradeDate(), "yyyy-MM-dd")
				.equals(DateUtil.formatDate(this.serverDAO.getCurDbDate(), "yyyy-MM-dd")))) {
			throw new RemoteException("日期已跨天，不能恢复交易！");
		}
		this.server.getSystemStatus().setStatus(5);
		this.server.statusListener();
	}

	public SystemStatus getSystemStatus() throws RemoteException {
		return this.server.getSystemStatus();
	}

	public void refreshTradeTime() throws RemoteException {
		this.log.debug("start refreshTradeTime");
		this.server.getServerInit().loadTradeTimeList();
		this.server.getServerInit().loadCommodityQueue();
		this.server.getTradeTimeTask().refreshTradeTime();
		this.log.debug("end refreshTradeTime");
	}

	public void refreshMemory() throws RemoteException {
		this.server.getServerInit().loadInitData();
		this.server.getTradeEngine().refresh(this.server.getServerInit().getCommodityList());
		if ((this.server.getDelayDeal() != null) && (this.server.getDelayDeal().getDelayOrderProcess() != null)) {
			this.server.getDelayDeal().getDelayOrderProcess().refreshCommoditySettleProp();
		}
	}

	public void refreshFirmQueue(String paramString) throws RemoteException {
		if ((paramString == null) || (paramString.equals(""))) {
			this.server.getServerInit().loadFirmQueue();
		} else {
			Firm localFirm = (Firm) this.serverDAO.getFirmMap().get(paramString);
			localFirm.setFirmMarginMap(this.serverDAO.getFirmMarginMap(paramString));
			localFirm.setFirmFeeMap(this.serverDAO.getFirmFeeMap(paramString));
			Map localMap = this.server.getServerInit().getFirmQueue();
			localMap.put(paramString, localFirm);
		}
	}

	public Date getCurDbDate() throws RemoteException {
		return this.serverDAO.getCurDbDate();
	}
}
