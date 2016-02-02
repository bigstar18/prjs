package gnnt.MEBS.timebargain.plugin.condition.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.plugin.condition.CalculateCenter;
import gnnt.MEBS.timebargain.plugin.condition.model.RmiConf;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;

public class RMIManager {
	static Log log = LogFactory.getLog(RMIManager.class);
	public static TradeRMI tradeRMI;
	public static ConditionRMI conditionRMI;

	public static boolean initCondditionRmi(RmiConf paramRmiConf, CalculateCenter paramCalculateCenter) {
		try {
			try {
				RMISocketFactory.setSocketFactory(new RmiDataSocket(paramRmiConf.getRmiDataPort()));
			} catch (Exception localException) {
				localException.printStackTrace();
				log.error("绑定数据端口失败" + localException.toString());
			}
			String str = "rmi://" + paramRmiConf.getHostIP() + ":" + paramRmiConf.getPort() + "/" + paramRmiConf.getServiceName();
			LocateRegistry.createRegistry(paramRmiConf.getPort());
			conditionRMI = new ConditionRMIImpl(paramCalculateCenter);
			Naming.rebind(str, conditionRMI);
			log.info("RMI 启动成功");
			return true;
		} catch (RemoteException localRemoteException) {
			localRemoteException.printStackTrace();
		} catch (MalformedURLException localMalformedURLException) {
			localMalformedURLException.printStackTrace();
		}
		return false;
	}

	public static boolean initTradeRmi(RmiConf paramRmiConf) {
		try {
			String str = "rmi://" + paramRmiConf.getHostIP() + ":" + paramRmiConf.getPort() + "/" + paramRmiConf.getServiceName();
			tradeRMI = (TradeRMI) Naming.lookup(str);
			return true;
		} catch (MalformedURLException localMalformedURLException) {
			localMalformedURLException.printStackTrace();
		} catch (RemoteException localRemoteException) {
			localRemoteException.printStackTrace();
		} catch (NotBoundException localNotBoundException) {
			localNotBoundException.printStackTrace();
		}
		return false;
	}
}
