package gnnt.MEBS.timebargain.front.callback;

import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.common.front.callbak.LogonCallbak;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class LogonCallbakImpl implements LogonCallbak {

	@Autowired
	@Qualifier("TradeRMI")
	private TradeRMI tradeRMI;
	public static final String LOGON_TYPE_WEB = "web";
	public static final int FROM_MODULE_ID = 15;
	public static final int SELF_MODULE_ID = 15;

	public TradeRMI getTradeRMI() {
		return this.tradeRMI;
	}

	public void setTradeRMI(TradeRMI paramTradeRMI) {
		this.tradeRMI = paramTradeRMI;
	}

	public void logonSuccessCallbak(User paramUser, HttpServletRequest paramHttpServletRequest) {
		try {
			TraderLogonInfo localTraderLogonInfo = this.tradeRMI.remoteLogon(paramUser.getTraderID(), paramUser.getSessionId().longValue(),
					paramUser.getIpAddress(), "web", 15, "web", 15);
			if ((localTraderLogonInfo.getRecode() != null) && (!localTraderLogonInfo.getRecode().equals("")))
				throw new RuntimeException(localTraderLogonInfo.getMessage());
		} catch (MalformedURLException localMalformedURLException) {
			localMalformedURLException.printStackTrace();
		} catch (RemoteException localRemoteException) {
			localRemoteException.printStackTrace();
		} catch (NotBoundException localNotBoundException) {
			localNotBoundException.printStackTrace();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
}