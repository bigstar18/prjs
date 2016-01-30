package gnnt.mebsv.hqservice.service.flash.flashmarket;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.mebsv.hqservice.dao.HQDAO;
import gnnt.mebsv.hqservice.hq.QuotationServer;
import gnnt.mebsv.hqservice.model.ProductDataVO;
import gnnt.mebsv.hqservice.model.ProductInfoVO;
import gnnt.mebsv.hqservice.model.TradeTimeVO;
import gnnt.mebsv.hqservice.tools.HZPY;

public class FlashMarketServer {
	Log log = LogFactory.getLog(FlashMarketServer.class);
	QuotationServer quotationServer;
	HQDAO curDataDAO;

	public FlashMarketServer(QuotationServer paramQuotationServer) {
		this.quotationServer = paramQuotationServer;
		this.curDataDAO = QuotationServer.getCurDataDAO();
	}

	public void initQuotationData() {
		try {
			while (true) {
				flashMarketInfo();
				if ((QuotationServer.tradeTimesMap == null) || (QuotationServer.tradeTimesMap.size() != 0))
					break;
				Thread.sleep(1000L);
			}
			flashProductInfo(false);
			this.quotationServer.m_time = this.curDataDAO.getHqTime();
			checkTDateChange();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public void clearData() {
		this.log.debug("交易日切换，清空行情内存数据");
		Iterator localIterator1 = this.quotationServer.clearMarketList.iterator();
		while (localIterator1.hasNext()) {
			String str = (String) localIterator1.next();
			Iterator localIterator2 = QuotationServer.htProductData.entrySet().iterator();
			while (localIterator2.hasNext()) {
				java.util.Map.Entry localEntry = (java.util.Map.Entry) localIterator2.next();
				ProductDataVO localProductDataVO = (ProductDataVO) localEntry.getValue();
				if (str.equals(localProductDataVO.marketID))
					localIterator2.remove();
			}
		}
		this.quotationServer.isClear = true;
		this.quotationServer.isServiceClear = true;
	}

	public void setIsClear() {
		this.quotationServer.isClear = false;
		this.quotationServer.isServiceClear = false;
		this.quotationServer.clearMarketList.clear();
	}

	public boolean checkTDateChange() {
		boolean bool = false;
		Set localSet = QuotationServer.tradeTimesMap.keySet();
		Iterator localIterator = localSet.iterator();
		while (localIterator.hasNext()) {
			String str = (String) localIterator.next();
			TradeTimeVO[] arrayOfTradeTimeVO = (TradeTimeVO[]) QuotationServer.tradeTimesMap.get(str);
			if (arrayOfTradeTimeVO.length > 0) {
				Integer localInteger = (Integer) this.quotationServer.lastTradeDate.get(str);
				if ((localInteger == null) || (arrayOfTradeTimeVO[0].tradeDate > localInteger.intValue())) {
					this.quotationServer.clearMarketList.add(str);
					this.log.debug("市场     " + str + "   交易日切换，清空行情内存数据");
					localInteger = Integer.valueOf(arrayOfTradeTimeVO[0].tradeDate);
					this.quotationServer.lastTradeDate.put(str, localInteger);
					bool = true;
				} else {
					bool = false;
				}
			}
		}
		return bool;
	}

	public void flashMarketInfo() throws SQLException {
		QuotationServer.tradeTimesMap = this.curDataDAO.queryTradeTimes();
		Set localSet = QuotationServer.tradeTimesMap.keySet();
		Iterator localIterator = localSet.iterator();
		while (localIterator.hasNext()) {
			String str = (String) localIterator.next();
			QuotationServer.tradeMinutesMap.put(str,
					Integer.valueOf(TradeTimeVO.GetTotalMinute((TradeTimeVO[]) QuotationServer.tradeTimesMap.get(str))));
		}
	}

	public void flashProductInfo(boolean paramBoolean) throws SQLException {
		ProductInfoVO[] arrayOfProductInfoVO;
		if (paramBoolean)
			arrayOfProductInfoVO = this.curDataDAO.queryProductInfo(new Date(0L));
		else
			arrayOfProductInfoVO = this.curDataDAO.queryProductInfo(QuotationServer.productInfoModifyTime);
		for (int i = 0; i < arrayOfProductInfoVO.length; i++) {
			try {
				arrayOfProductInfoVO[i].pyName = HZPY.getPYJM(arrayOfProductInfoVO[i].name, "GBK");
			} catch (Exception localException) {
				localException.printStackTrace();
			}
			QuotationServer.htProductInfo.put(arrayOfProductInfoVO[i].marketID + arrayOfProductInfoVO[i].code, arrayOfProductInfoVO[i]);
			if (arrayOfProductInfoVO[i].modifyTime.after(QuotationServer.productInfoModifyTime))
				QuotationServer.productInfoModifyTime = arrayOfProductInfoVO[i].modifyTime;
		}
	}

	public Date getHQTime() {
		return this.curDataDAO.getHQDate();
	}
}