package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.dao.ExtendDAO;
import gnnt.MEBS.timebargain.server.model.ApplyBill;
import gnnt.MEBS.timebargain.server.model.Settle;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExtendRMIImpl extends UnicastRemoteObject implements ExtendRMI {
	private static final long serialVersionUID = 2690197650654049815L;
	Log log = LogFactory.getLog(getClass());
	private Server server;
	private ExtendDAO extendDAO;

	public ExtendRMIImpl(Server paramServer) throws RemoteException {
		this.server = paramServer;
		this.extendDAO = paramServer.getExtendDAO();
	}

	public int gage(ApplyBill paramApplyBill) throws RemoteException {
		this.log.debug("gage.applyBill:" + paramApplyBill);
		if (this.server == null) {
			return -204;
		}
		if ((this.server.getSystemStatus().getStatus() != 1) && (this.server.getSystemStatus().getStatus() != 3)
				&& (this.server.getSystemStatus().getStatus() != 10)) {
			return -202;
		}
		paramApplyBill.setBS_Flag(new Short((short) 2));
		return this.extendDAO.gage(paramApplyBill);
	}

	public int gageCancel(ApplyBill paramApplyBill) throws RemoteException {
		this.log.debug("gageCancel.applyBill:" + paramApplyBill);
		if (this.server == null) {
			return -204;
		}
		if ((this.server.getSystemStatus().getStatus() != 1) && (this.server.getSystemStatus().getStatus() != 3)
				&& (this.server.getSystemStatus().getStatus() != 10)) {
			return -202;
		}
		return this.extendDAO.gageCancel(paramApplyBill);
	}

	public int aheadSettle(ApplyBill paramApplyBill) throws RemoteException {
		this.log.debug("aheadSettle.applyBill:" + paramApplyBill);
		if (this.server == null) {
			return -204;
		}
		if ((this.server.getSystemStatus().getStatus() != 1) && (this.server.getSystemStatus().getStatus() != 3)
				&& (this.server.getSystemStatus().getStatus() != 10)) {
			return -202;
		}
		return this.extendDAO.aheadSettle(paramApplyBill);
	}

	public int conferClose(Settle paramSettle) throws RemoteException {
		this.log.debug("conferClose.settle:" + paramSettle);
		if (this.server == null) {
			return -204;
		}
		if (this.server.getSystemStatus().getStatus() != 1) {
			return -202;
		}
		if ((paramSettle.getBGageQty() != null) && (paramSettle.getBGageQty().longValue() != 0L)) {
			return -51;
		}
		if ((paramSettle.getSGageQty() != null) && (paramSettle.getSGageQty().longValue() != 0L)) {
			return -52;
		}
		if (paramSettle.getBHoldQty().longValue() != paramSettle.getSHoldQty().longValue()) {
			return -41;
		}
		paramSettle.setBHoldQty(new Long(paramSettle.getBHoldQty().longValue() - paramSettle.getBGageQty().longValue()));
		paramSettle.setSHoldQty(new Long(paramSettle.getSHoldQty().longValue() - paramSettle.getSGageQty().longValue()));
		return this.extendDAO.conferClose(paramSettle);
	}

	public int waitSettle(ApplyBill paramApplyBill) throws RemoteException {
		this.log.debug("waitSettle.applyBill:" + paramApplyBill);
		if (this.server == null) {
			return -204;
		}
		return this.extendDAO.waitSettle(paramApplyBill);
	}

	public int waitSettleCancel(ApplyBill paramApplyBill) throws RemoteException {
		this.log.debug("waitSettleCancel.applyBill:" + paramApplyBill);
		if (this.server == null) {
			return -204;
		}
		return this.extendDAO.waitSettleCancel(paramApplyBill);
	}
}
