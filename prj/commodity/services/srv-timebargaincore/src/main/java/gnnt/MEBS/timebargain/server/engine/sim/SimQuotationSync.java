package gnnt.MEBS.timebargain.server.engine.sim;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.engine.TradeEngine;
import gnnt.MEBS.timebargain.server.model.Quotation;

public class SimQuotationSync extends Thread {
	private Log logger = LogFactory.getLog(getClass());
	private TradeEngine te;
	private boolean stop;
	private boolean stopStatus;

	public SimQuotationSync(TradeEngine paramTradeEngine) {
		this.te = paramTradeEngine;
	}

	private void updateQuotation(Quotation paramQuotation1, Quotation paramQuotation2) {
		if ((paramQuotation1.buy[0] != paramQuotation2.buy[0]) || (paramQuotation1.buyqty[0] != paramQuotation2.buyqty[0])
				|| (paramQuotation1.sell[0] != paramQuotation2.sell[0]) || (paramQuotation1.sellqty[0] != paramQuotation2.sellqty[0])
				|| (paramQuotation1.highPrice.doubleValue() != paramQuotation2.highPrice.doubleValue())
				|| (paramQuotation1.lowPrice.doubleValue() != paramQuotation2.lowPrice.doubleValue())
				|| (paramQuotation1.spread.doubleValue() != paramQuotation2.spread.doubleValue())
				|| (paramQuotation1.reserveCount.longValue() != paramQuotation2.reserveCount.longValue())
				|| (paramQuotation1.totalAmount.longValue() != paramQuotation2.totalAmount.longValue())
				|| (paramQuotation1.curPrice.doubleValue() != paramQuotation2.curPrice.doubleValue())
				|| (paramQuotation1.curAmount.longValue() != paramQuotation2.curAmount.longValue())
				|| (paramQuotation1.yesterBalancePrice.doubleValue() != paramQuotation2.yesterBalancePrice.doubleValue())
				|| (paramQuotation1.price.doubleValue() != paramQuotation2.price.doubleValue())) {
			this.logger.debug("----SimQuotationSync: updateQuotation : " + paramQuotation1.commodityID);
			paramQuotation1.buy[0] = paramQuotation2.buy[0];
			paramQuotation1.buyqty[0] = paramQuotation2.buyqty[0];
			paramQuotation1.sell[0] = paramQuotation2.sell[0];
			paramQuotation1.sellqty[0] = paramQuotation2.sellqty[0];
			paramQuotation1.highPrice = paramQuotation2.highPrice;
			paramQuotation1.lowPrice = paramQuotation2.lowPrice;
			paramQuotation1.spread = paramQuotation2.spread;
			paramQuotation1.reserveCount = paramQuotation2.reserveCount;
			paramQuotation1.totalAmount = paramQuotation2.totalAmount;
			paramQuotation1.curPrice = paramQuotation2.curPrice;
			paramQuotation1.curAmount = paramQuotation2.curAmount;
			paramQuotation1.yesterBalancePrice = paramQuotation2.yesterBalancePrice;
			paramQuotation1.price = paramQuotation2.price;
			paramQuotation1.updateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
			paramQuotation1.isUpdate = true;
			this.te.quotationsClone.put(paramQuotation1.getCommodityID(), (Quotation) paramQuotation1.clone());
		}
	}

	public void run() {
		logger.info("*** Sim Quotation Getter thread started.");
		stop = false;
		stopStatus = false;
		do {
			if (stop)
				break;
			try {
				if (te.getStatus() == 0) {
					List list = Server.simHqDAO.getQuotationList();
					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							Quotation quotation = (Quotation) list.get(i);
							Quotation quotation1 = (Quotation) te.quotations.get(quotation.commodityID);
							synchronized (quotation1) {
								updateQuotation(quotation1, quotation);
							}
						}

					}
				}
			} catch (Exception exception) {
				logger.error("***** Sim Quotation Getter thread down ! *****", exception);
			}
			try {
				sleep(te.getMatchInterval());
				continue;
			} catch (Exception exception1) {
			}
			break;
		} while (true);
		stopStatus = true;
	}

	public void pleaseStop() {
		this.stop = true;
		interrupt();
	}

	public boolean waitStop(int paramInt) {
		try {
			sleep(paramInt * 1000);
		} catch (InterruptedException localInterruptedException) {
			localInterruptedException.printStackTrace();
		}
		return (this.stopStatus) || (!isAlive());
	}
}
