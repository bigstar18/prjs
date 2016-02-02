package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.DelayDAO;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.delay.DelayDeal;
import gnnt.MEBS.timebargain.server.delay.DelayOrderProcess;
import gnnt.MEBS.timebargain.server.delay.DelayTradeTimeTask;
import gnnt.MEBS.timebargain.server.model.ApplyBill;
import gnnt.MEBS.timebargain.server.model.Customer;
import gnnt.MEBS.timebargain.server.model.DelayOrder;
import gnnt.MEBS.timebargain.server.model.DelayStatus;
import gnnt.MEBS.timebargain.server.util.ActiveUserManager;
import gnnt.MEBS.timebargain.server.util.DateUtil;
import gnnt.MEBS.timebargain.server.util.FrontCheckLogon;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DelayRMIImpl extends UnicastRemoteObject implements DelayRMI {
	private static final long serialVersionUID = 2690197650654049914L;
	Log log = LogFactory.getLog(getClass());
	private Server server;
	private DelayDeal delayDeal;
	private ServerDAO serverDAO;
	private DelayDAO delayDAO;
	private FrontCheckLogon logonManager;

	public DelayRMIImpl(Server paramServer) throws RemoteException {
		this.server = paramServer;
		this.delayDeal = paramServer.getDelayDeal();
		this.serverDAO = ((ServerDAO) DAOBeanFactory.getBean("serverDAO"));
		this.delayDAO = ((DelayDAO) DAOBeanFactory.getBean("delayDAO"));
		this.logonManager = paramServer.getLogonManager();
	}

	public void ctlTrade(int paramInt) throws RemoteException {
		if (paramInt == 0) {
			if ((this.delayDeal.getDelayStatus().getStatus() == 1) || (this.delayDeal.getDelayStatus().getStatus() == 3)) {
				this.delayDeal.getDelayStatus().setStatus(4);
				this.delayDeal.statusListener();
			} else {
				throw new RemoteException("非延期交易时间，不允许暂停！");
			}
		} else if ((paramInt == 1) && (this.delayDeal.getDelayStatus().getStatus() == 4)) {
			this.delayDeal.getDelayTradeTimeTask().recoverRun();
		}
	}

	public void timingContinueTrade(String paramString) throws RemoteException {
		try {
			DateUtil.convertStringToDate("HH:mm:ss", paramString);
		} catch (Exception localException) {
			throw new RemoteException(paramString + "时间格式不正确！");
		}
		this.delayDeal.getDelayStatus().setRecoverTime(paramString);
		this.delayDAO.updateDelayRecoverTime(paramString);
	}

	public void tradeEnd() throws RemoteException {
		this.delayDeal.getDelayStatus().setStatus(5);
		this.delayDeal.statusListener();
	}

	public void recoverTrade() throws RemoteException {
		if (this.delayDeal.getDelayStatus().getStatus() != 5) {
			throw new RemoteException("不是延期交易结束状态，不能恢复交易！");
		}
		if ((!this.server.getServerInit().isCrossDay()) && (!DateUtil.formatDate(this.delayDeal.getDelayStatus().getTradeDate(), "yyyy-MM-dd")
				.equals(DateUtil.formatDate(this.serverDAO.getCurDbDate(), "yyyy-MM-dd")))) {
			throw new RemoteException("日期已跨天，不能恢复延期交易！");
		}
		this.delayDeal.getDelayTradeTimeTask().recoverRun();
	}

	public DelayStatus getDelayStatus() throws RemoteException {
		return this.delayDeal.getDelayStatus();
	}

	public void refreshDelayTradeTime() throws RemoteException {
		this.log.debug("start refreshDelayTradeTime");
		this.delayDeal.refreshDelayTradeTime();
		this.log.debug("end refreshDelayTradeTime");
	}

	public int order(long paramLong, DelayOrder paramDelayOrder, String paramString) throws RemoteException, Exception {
		if ((this.server == null) || (this.delayDeal == null) || (this.delayDeal.getDelayOrderProcess() == null)) {
			return -204;
		}
		if (paramDelayOrder == null) {
			return -206;
		}
		if (!this.logonManager.isLogon(paramDelayOrder.getTraderID(), paramLong, 15, paramString)) {
			return 1;
		}
		return this.delayDeal.getDelayOrderProcess().order(paramDelayOrder);
	}

	public int consignerOrder(long paramLong, DelayOrder paramDelayOrder) throws RemoteException {
		if ((this.server == null) || (this.delayDeal == null) || (this.delayDeal.getDelayOrderProcess() == null)) {
			return -204;
		}
		if (paramDelayOrder == null) {
			return -206;
		}
		String str1 = this.server.getAuManagerConsigner().getUserID(paramLong);
		if ((str1 == null) || (!str1.equals(paramDelayOrder.getConsignerID()))) {
			return 1;
		}
		String str2 = paramDelayOrder.getCustomerID();
		Map localMap = this.server.getServerInit().getCustomerQueue();
		if (localMap != null) {
			Customer localCustomer = (Customer) localMap.get(str2);
			if (localCustomer == null) {
				String str3 = this.serverDAO.getFirmByCustmID(str2);
				this.server.getServerInit().loadOneFirm(str3);
				this.server.getServerInit().loadCustomer(str3);
			}
		} else {
			return -204;
		}
		return this.delayDeal.getDelayOrderProcess().order(paramDelayOrder);
	}

	public int delaySettleBill(ApplyBill paramApplyBill) throws RemoteException {
		this.log.debug("delaySettleBill.applyBill:" + paramApplyBill);
		if (this.server == null) {
			return -204;
		}
		return this.delayDAO.delaySettleBill(paramApplyBill);
	}

	public int delaySettleBillCancel(ApplyBill paramApplyBill) throws RemoteException {
		this.log.debug("delaySettleBillCancel.applyBill:" + paramApplyBill);
		if (this.server == null) {
			return -204;
		}
		return this.delayDAO.delaySettleBillCancel(paramApplyBill);
	}
}
