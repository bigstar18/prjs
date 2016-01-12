package gnnt.MEBS.broker.mgr.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gnnt.MEBS.broker.mgr.dao.BrokerDAO;
import gnnt.MEBS.broker.mgr.model.brokerManagement.Broker;
import gnnt.MEBS.broker.mgr.model.brokerManagement.BrokerMenu;
import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Tools;

@Service("com_brokerRightService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
public class BrokerRightService extends StandardService {

	@Autowired
	@Qualifier("brokerDAO")
	private BrokerDAO brokerDAO;

	public StandardDao getDao() {
		return this.brokerDAO;
	}

	public Broker getBrokerById(String paramString, boolean paramBoolean) {
		Broker localBroker = this.brokerDAO.getBrokerById(paramString);
		if (paramBoolean)
			localBroker.getRightSet().size();
		return localBroker;
	}

	public void saveBrokerRight(Broker paramBroker, String[] paramArrayOfString1, String[] paramArrayOfString2) {
		if ((paramArrayOfString2 != null) && (paramArrayOfString2.length > 0)) {
			String str1 = "";
			for (String str2 : paramArrayOfString2) {
				if (str1.trim().length() > 0)
					str1 = str1 + ",";
				str1 = str1 + str2;
			}
			Set hashset = new HashSet();
			Iterator localIterator = paramBroker.getRightSet().iterator();
			BrokerMenu localBrokerMenu;
			while (localIterator.hasNext()) {
				localBrokerMenu = (BrokerMenu) localIterator.next();
				int m = 1;
				for (int n = 0; n < paramArrayOfString2.length; n++)
					if (localBrokerMenu.getModuleId().intValue() == Tools.strToLong(paramArrayOfString2[n])) {
						m = 0;
						break;
					}
				if (m != 0)
					(hashset).add(localBrokerMenu);
			}
			if ((paramArrayOfString1 != null) && (paramArrayOfString1.length > 0))
				for (int j = 0; j < paramArrayOfString1.length; j++) {
					localBrokerMenu = new BrokerMenu();
					localBrokerMenu.setId(Long.valueOf(Long.parseLong(paramArrayOfString1[j])));
					localBrokerMenu = (BrokerMenu) get(localBrokerMenu);
					if (localBrokerMenu != null)
						(hashset).add(localBrokerMenu);
				}
			paramBroker.setRightSet(hashset);
			update(paramBroker);
		}
	}
}