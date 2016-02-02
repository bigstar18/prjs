package gnnt.MEBS.timebargain.server;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.engine.QuotationCallback;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.Quotation;

public class QuotationProcess implements QuotationCallback {
	private Log log = LogFactory.getLog(getClass());
	private Server server;
	private TradeDAO tradeDAO;

	public QuotationProcess(Server paramServer) {
		this.server = paramServer;
		this.tradeDAO = paramServer.getTradeDAO();
	}

	public void callback(Quotation paramQuotation) {
		if ((!updateQuotation(paramQuotation)) && (!updateQuotation(paramQuotation))) {
			this.log.error("*******************警告：更新行情到db中失败，timebargain整个应用停止服务，请手工重启此timebargain服务！******************");
			System.exit(1);
			return;
		}
		calFirmMaxHoldQty(paramQuotation);
	}

	private boolean updateQuotation(Quotation paramQuotation) {
		boolean bool = true;
		try {
			int i = this.tradeDAO.updateQuotation(paramQuotation);
			if (i != 1) {
				this.log.error("更新行情失败，" + paramQuotation.toString());
				bool = false;
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("更新行情失败，" + paramQuotation.toString());
			bool = false;
		}
		return bool;
	}

	private void calFirmMaxHoldQty(Quotation paramQuotation) {
		try {
			Map localMap = (Map) this.server.getServerInit().getCommodityQueue().get(this.server.getSystemStatus().getSectionID());
			if (localMap == null) {
				return;
			}
			Commodity localCommodity = (Commodity) localMap.get(paramQuotation.getCommodityID());
			if ((localCommodity != null) && (localCommodity.getFirmMaxHoldQty() != -1L) && (localCommodity.getFirmMaxHoldQtyAlgr() == 1)) {
				long l = 0L;
				if (paramQuotation.getReserveCount().longValue() > localCommodity.getStartPercentQty()) {
					l = (long) (paramQuotation.getReserveCount().longValue() * localCommodity.getMaxPercentLimit());
				} else {
					l = (long) (localCommodity.getStartPercentQty() * localCommodity.getMaxPercentLimit());
				}
				if (localCommodity.getFirmMaxHoldQty() != l) {
					this.tradeDAO.updateCommodityFirmMaxHoldQty(paramQuotation.getCommodityID(), Long.valueOf(l));
					localCommodity.setFirmMaxHoldQty(l);
				}
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.error("更新行情时计算交易商最大订货量失败，原因：" + localException);
		}
	}
}
