package gnnt.mebsv.hqservice.service.flash.flashcurdata;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import gnnt.mebsv.hqservice.hq.QuotationServer;
import gnnt.mebsv.hqservice.model.BillDataVO;
import gnnt.mebsv.hqservice.model.ProductDataVO;
import gnnt.mebsv.hqservice.model.SortListVO;
import gnnt.mebsv.hqservice.model.TradeTimeVO;
import gnnt.mebsv.hqservice.service.flash.FlashMarketInfoThread;
import gnnt.mebsv.hqservice.tools.Sort;

public abstract class BFlashDataServer {
	protected static QuotationServer quotationServer;

	public abstract boolean flashCurData() throws SQLException;

	public abstract Date getHQTime();

	public BFlashDataServer(QuotationServer paramQuotationServer) {
		try {
			quotationServer = paramQuotationServer;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public boolean chenckRunFlag() {
		return FlashMarketInfoThread.initFlag;
	}

	public void initCurData() {
		try {
			QuotationServer.getCurDataDAO().getAllCurData(QuotationServer.htProductData, quotationServer.cclass_id, quotationServer.cclass_bq,
					quotationServer.buySell);
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
		}
	}

	public void calculateProductDataVO() {
		if (quotationServer.m_time == null)
			try {
				quotationServer.m_time = QuotationServer.getCurDataDAO().getHqTime();
			} catch (Exception localException) {
			}
		if (quotationServer.m_time == null)
			return;
		Enumeration localEnumeration = QuotationServer.htProductData.elements();
		while (localEnumeration.hasMoreElements()) {
			ProductDataVO localProductDataVO = (ProductDataVO) localEnumeration.nextElement();
			String str = localProductDataVO.marketID;
			int i = (quotationServer.getHqDate(str).getYear() + 1900) * 10000 + (quotationServer.getHqDate(str).getMonth() + 1) * 100
					+ quotationServer.getHqDate(str).getDate();
			int j = quotationServer.getHqDate(str).getHours() * 100 + quotationServer.getHqDate(localProductDataVO.marketID).getMinutes();
			int k = TradeTimeVO.GetIndexFromTimeAndYear(i, j, (TradeTimeVO[]) QuotationServer.tradeTimesMap.get(str));
			int m = TradeTimeVO.GetTimeFromIndex(k - 5, (TradeTimeVO[]) QuotationServer.tradeTimesMap.get(str));
			if ((localProductDataVO.bUpdated) || (localProductDataVO.time.getHours() * 100 + localProductDataVO.time.getMinutes() != j)) {
				if ((localProductDataVO.yesterBalancePrice > 0.0F) && (localProductDataVO.curPrice > 0.0F))
					localProductDataVO.upRate = ((localProductDataVO.curPrice - localProductDataVO.yesterBalancePrice)
							/ localProductDataVO.yesterBalancePrice * 100.0F);
				else
					localProductDataVO.upRate = 0.0F;
				if (localProductDataVO.yesterBalancePrice > 0.0F)
					localProductDataVO.shakeRate = ((localProductDataVO.highPrice - localProductDataVO.lowPrice)
							/ localProductDataVO.yesterBalancePrice * 100.0F);
				else
					localProductDataVO.shakeRate = 0.0F;
				int n = 0;
				if (QuotationServer.tradeMinutesMap.size() > 0)
					n = ((Integer) QuotationServer.tradeMinutesMap.get(str)).intValue();
				if ((localProductDataVO.averAmount5 > 0L) && (n > 0)) {
					if (k > -1)
						localProductDataVO.amountRate = ((float) (localProductDataVO.totalAmount / (k + 1) / (localProductDataVO.averAmount5 / n)));
					else
						localProductDataVO.amountRate = ((float) (localProductDataVO.totalAmount / (localProductDataVO.averAmount5 / n)));
				} else
					localProductDataVO.amountRate = 1.0F;
				int i1 = 0;
				int i2 = 0;
				for (int i3 = 0; i3 < 3; i3++) {
					i1 += localProductDataVO.buyAmount[i3];
					i2 += localProductDataVO.sellAmount[i3];
				}
				if (i1 + i2 > 0)
					localProductDataVO.consignRate = ((i1 - i2) / (i1 + i2) * 100.0F);
				else
					localProductDataVO.consignRate = 0.0F;
				float f = localProductDataVO.curPrice;
				for (int i4 = localProductDataVO.billData.size() - 1; i4 >= 0; i4--) {
					BillDataVO localBillDataVO = (BillDataVO) localProductDataVO.billData.get(i4);
					if ((localBillDataVO.time / 100 <= m) || (i4 == 0)) {
						f = localBillDataVO.curPrice;
						break;
					}
				}
				if (f > 0.0F)
					localProductDataVO.upRate5min = ((localProductDataVO.curPrice - f) / f * 100.0F);
				else
					localProductDataVO.upRate5min = 0.0F;
				localProductDataVO.bUpdated = false;
			}
		}
	}

	public void processSort() {
		QuotationServer.sortLists = new SortListVO[10];
		Vector localVector = new Vector(QuotationServer.htProductData.values());
		for (int i = localVector.size() - 1; i >= 0; i--) {
			ProductDataVO localProductDataVO = (ProductDataVO) localVector.get(i);
			int j = quotationServer.getProductType(localProductDataVO.marketID + localProductDataVO.code);
			if ((j == 3) || (j == 2) || (j == 4) || (j == 6) || (j == -1))
				localVector.removeElementAt(i);
		}
		for (int i = 0; i < 10; i++) {
			QuotationServer.sortLists[i] = new SortListVO();
			QuotationServer.sortLists[i].sortKey = i;
			Collections.sort(localVector, new Sort(i));
			QuotationServer.sortLists[i].codeList = getListFromStockV(localVector);
		}
	}

	public String[] getListFromStockV(Vector paramVector) {
		String[] arrayOfString = new String[paramVector.size()];
		for (int i = 0; i < paramVector.size(); i++)
			arrayOfString[i] = (((ProductDataVO) paramVector.elementAt(i)).marketID + ((ProductDataVO) paramVector.elementAt(i)).code);
		return arrayOfString;
	}

	public void printcurData() {
		Enumeration localEnumeration = QuotationServer.htProductData.elements();
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Time\tCode\tPrice\tAver\tAmount");
		while (localEnumeration.hasMoreElements()) {
			ProductDataVO localProductDataVO = (ProductDataVO) localEnumeration.nextElement();
			System.out.println(localProductDataVO);
			System.out.println(localProductDataVO.time.getHours() + ":" + localProductDataVO.time.getMinutes() + ":"
					+ localProductDataVO.time.getSeconds() + "\t" + localProductDataVO.code + "\t" + localProductDataVO.curPrice + "\t"
					+ localProductDataVO.balancePrice + "\t" + localProductDataVO.totalAmount);
		}
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
}