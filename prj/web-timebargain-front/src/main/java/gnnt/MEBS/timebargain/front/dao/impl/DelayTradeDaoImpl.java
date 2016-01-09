package gnnt.MEBS.timebargain.front.dao.impl;

import gnnt.MEBS.common.front.dao.StandardDao;
import gnnt.MEBS.timebargain.front.dao.DelayTradeDao;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("delayTradeDao")
public class DelayTradeDaoImpl extends StandardDao implements DelayTradeDao {
	public gnnt.MEBS.timebargain.server.model.DelayOrder getDelayOrderById(String paramString) {
		gnnt.MEBS.timebargain.server.model.DelayOrder localDelayOrder = new gnnt.MEBS.timebargain.server.model.DelayOrder();
		try {
			gnnt.MEBS.timebargain.front.model.DelayOrder localDelayOrder1 = (gnnt.MEBS.timebargain.front.model.DelayOrder) getHibernateTemplate()
					.get(gnnt.MEBS.timebargain.front.model.DelayOrder.class, Long.valueOf(paramString));
			localDelayOrder.setBuyOrSell(localDelayOrder1.getBsFlag());
			localDelayOrder.setCommodityID(localDelayOrder1.getCommodityID());
			localDelayOrder.setConsignerID(localDelayOrder1.getConsignerID());
			localDelayOrder.setCustomerID(localDelayOrder1.getCustomerID());
			localDelayOrder.setDelayOrderType(localDelayOrder1.getDelayorderType());
			localDelayOrder.setFirmID(localDelayOrder1.getFirmID());
			localDelayOrder.setOrderNo(localDelayOrder1.getOrderNO());
			localDelayOrder.setOrderTime(localDelayOrder1.getOrderTime());
			localDelayOrder.setPrice(localDelayOrder1.getPrice());
			localDelayOrder.setQuantity(localDelayOrder1.getQuantity());
			localDelayOrder.setStatus(localDelayOrder1.getStatus());
			localDelayOrder.setTradeQty(localDelayOrder1.getTradeQty());
			localDelayOrder.setTraderID(localDelayOrder1.getTraderID());
			localDelayOrder.setWd_DelayOrderType(localDelayOrder1.getDelayorderType());
			localDelayOrder.setWithdrawerID(localDelayOrder1.getWithdrawerID());
			localDelayOrder.setWithdrawType(localDelayOrder1.getWithdrawType());
		} catch (DataAccessException localDataAccessException) {
			localDataAccessException.printStackTrace();
		} catch (NumberFormatException localNumberFormatException) {
			localNumberFormatException.printStackTrace();
		}
		return localDelayOrder;
	}
}