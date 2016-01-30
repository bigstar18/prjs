package gnnt.mebsv.hqservice.hq;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.quotation.PreData;
import gnnt.MEBS.util.Configuration;
import gnnt.mebsv.hqservice.dao.HQDAO;
import gnnt.mebsv.hqservice.dao.factory.HQDAOFactory;
import gnnt.mebsv.hqservice.hq.Index.IndexCalculator;
import gnnt.mebsv.hqservice.hq.Index.Series;
import gnnt.mebsv.hqservice.model.BillDataVO;
import gnnt.mebsv.hqservice.model.ClientSocket;
import gnnt.mebsv.hqservice.model.MarketInfoVO;
import gnnt.mebsv.hqservice.model.MinDataVO;
import gnnt.mebsv.hqservice.model.ProductDataVO;
import gnnt.mebsv.hqservice.model.ProductInfoVO;
import gnnt.mebsv.hqservice.model.SortListVO;
import gnnt.mebsv.hqservice.model.TradeTimeVO;
import gnnt.mebsv.hqservice.service.flash.FlashDataThread;
import gnnt.mebsv.hqservice.service.flash.FlashMarketInfoThread;

public class QuotationServer {
	private Log log = LogFactory.getLog(QuotationServer.class);
	private int pushQuote = 10;
	public int buySell;
	private static QuotationServer quotation;
	public static Properties params;
	int iMinLineInterval;
	public String cclass_id = "ALL";
	public String cclass_bq = "0";
	private static FlashDataThread flashCacheThread;
	private static FlashMarketInfoThread flashMarketInfoThread;
	private static IndexCalculator indexCalculator;
	private Series series;
	public static HashMap tradeTimesMap = new HashMap();
	private static ArrayList arraylist1 = new ArrayList();
	public static HashMap tradeMinutesMap = new HashMap();
	public Map<String, Date> m_time = new HashMap();
	public static Hashtable htProductData = new Hashtable();
	public static Hashtable htProductInfo = new Hashtable();
	public static Date productInfoModifyTime = new Date(0L);
	public static SortListVO[] sortLists = new SortListVO[0];
	private static HQDAO curDataDAO;
	public boolean bDataLoaded = false;
	public HashMap lastTradeDate = new HashMap();
	public boolean isClear = false;
	public boolean isServiceClear;
	public List<String> clearMarketList = new ArrayList();

	public static HQDAO getCurDataDAO() {
		return curDataDAO;
	}

	private QuotationServer() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		System.out.println("");
		System.out.println(new Date().toLocaleString() + " Start ...");
		params = new Configuration().getSection("MEBS.Quotation");
		params.list(System.out);
		this.pushQuote = Integer.parseInt(new Configuration().getSection("MEBS.HQService").getProperty("PushQuote"));
		this.cclass_id = params.getProperty("CClass_ID", "ALL");
		this.cclass_bq = params.getProperty("CClass_bq", "0");
		this.buySell = Integer.parseInt(params.getProperty("BuySell", "5"));
		this.iMinLineInterval = Integer.parseInt(params.getProperty("MinLineInterval", "60"));
		if ((this.iMinLineInterval <= 0) || (this.iMinLineInterval > 60))
			this.iMinLineInterval = 60;
		curDataDAO = HQDAOFactory.getDAO(params);
		this.m_time = curDataDAO.getHqTime();
		flashMarketInfoThread = new FlashMarketInfoThread(this);
		flashMarketInfoThread.start();
		flashCacheThread = new FlashDataThread(this);
		flashCacheThread.start();
		indexCalculator = new IndexCalculator(params, this);
		indexCalculator.start();
		this.series = new Series();
	}

	public static QuotationServer getInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
		if (quotation == null)
			synchronized (QuotationServer.class) {
				if (quotation == null)
					quotation = new QuotationServer();
			}
		return quotation;
	}

	public TradeTimeVO[] getTradeTimes(String paramString) {
		return (TradeTimeVO[]) tradeTimesMap.get(paramString);
	}

	public Map getTradeTimesMap() {
		return tradeTimesMap;
	}

	public MarketInfoVO getMarketInfo() {
		return curDataDAO.getMarketInfo(null);
	}

	public int getMinLineInterval() {
		return this.iMinLineInterval;
	}

	public Map getHqDate() {
		return this.m_time == null ? null : this.m_time;
	}

	public Date getHqDate(String paramString) {
		return this.m_time == null ? null : (Date) ((Date) this.m_time.get(paramString)).clone();
	}

	public ProductInfoVO[] getAllProductInfo() {
		if (!this.bDataLoaded)
			return new ProductInfoVO[0];
		Enumeration localEnumeration = htProductInfo.elements();
		Vector localVector = new Vector();
		while (localEnumeration.hasMoreElements())
			localVector.add(localEnumeration.nextElement());
		Collections.sort(localVector, new CodeSort());
		ProductInfoVO[] arrayOfProductInfoVO1 = indexCalculator.getIndexInfoVO();
		if (arrayOfProductInfoVO1.length > 0)
			for (int i = 0; i < arrayOfProductInfoVO1.length; i++)
				for (int j = 0; j < localVector.size(); j++)
					if (arrayOfProductInfoVO1[i].code.equals(((ProductInfoVO) localVector.get(j)).code)) {
						localVector.remove(j);
						break;
					}
		for (int i = 0; i < arrayOfProductInfoVO1.length; i++)
			localVector.insertElementAt(arrayOfProductInfoVO1[i], i);
		ProductInfoVO[] arrayOfProductInfoVO2 = this.series.getSeriesInfoVO();
		for (int j = 0; j < arrayOfProductInfoVO2.length; j++)
			localVector.insertElementAt(arrayOfProductInfoVO2[j], j);
		return (ProductInfoVO[]) localVector.toArray(new ProductInfoVO[localVector.size()]);
	}

	public ProductDataVO getProductData(String paramString) {
		return (ProductDataVO) htProductData.get(paramString);
	}

	public ProductDataVO[] getProductData(String[] paramArrayOfString) {
		Vector localVector = new Vector();
		for (int i = 0; i < paramArrayOfString.length; i++) {
			ProductDataVO localProductDataVO = (ProductDataVO) htProductData.get(paramArrayOfString[i]);
			if (localProductDataVO != null)
				localVector.add(localProductDataVO);
		}
		ProductDataVO[] arrayOfProductDataVO = (ProductDataVO[]) localVector.toArray(new ProductDataVO[localVector.size()]);
		return arrayOfProductDataVO;
	}

	public Vector getAllProductData() {
		return new Vector(htProductData.values());
	}

	public ProductDataVO[] getModifiedProductData(ClientSocket paramClientSocket) {
		long l = paramClientSocket.quoteListTime;
		Vector localVector = new Vector();
		Enumeration localEnumeration = htProductData.elements();
		Object localObject;
		while (localEnumeration.hasMoreElements()) {
			localObject = (ProductDataVO) localEnumeration.nextElement();
			if (((ProductDataVO) localObject).lUpdateTime > l) {
				localVector.add(localObject);
				paramClientSocket.vpSize.add(((ProductDataVO) localObject).marketID + ((ProductDataVO) localObject).code);
			}
			if (localVector.size() > this.pushQuote)
				break;
		}
		if ((localVector.size() == 0) && (paramClientSocket.vpSize.size() < htProductData.size())) {
			localObject = htProductData.elements();
			while (((Enumeration) localObject).hasMoreElements()) {
				int i = 0;
				ProductDataVO localProductDataVO = (ProductDataVO) ((Enumeration) localObject).nextElement();
				while ((i < paramClientSocket.vpSize.size())
						&& (!(paramClientSocket.vpSize.get(i) + "").equals(localProductDataVO.marketID + localProductDataVO.code)))
					i++;
				if (i == paramClientSocket.vpSize.size()) {
					localVector.add(localProductDataVO);
					paramClientSocket.vpSize.add(localProductDataVO.marketID + localProductDataVO.code);
				}
				if (localVector.size() > this.pushQuote)
					break;
			}
		}
		return (ProductDataVO[]) localVector.toArray(new ProductDataVO[localVector.size()]);
	}

	public ProductDataVO getOneUpdateProductData(long paramLong, String paramString) {
		ProductDataVO localProductDataVO = (ProductDataVO) htProductData.get(paramString);
		if (localProductDataVO.lUpdateTime > paramLong)
			return localProductDataVO;
		return null;
	}

	public MinDataVO[] getMinData(String paramString) {
		ProductDataVO localProductDataVO = (ProductDataVO) htProductData.get(paramString);
		if (localProductDataVO == null)
			return new MinDataVO[0];
		Vector localVector = new Vector();
		int i = -1;
		BillDataVO localObject = null;
		for (int j = 0; j < localProductDataVO.billData.size(); j++) {
			BillDataVO localBillDataVO = (BillDataVO) localProductDataVO.billData.get(j);
			int k = localBillDataVO.time / 10000 * 60 * 60 + localBillDataVO.time / 100 % 100 * 60 + localBillDataVO.time % 100;
			int m = k / this.iMinLineInterval * this.iMinLineInterval;
			if (k % this.iMinLineInterval >= 0)
				m += this.iMinLineInterval;
			int n = m / 60 / 60 * 10000 + m / 60 % 60 * 100 + m % 60;
			n %= 240000;
			if (n != i) {
				if (localObject != null) {
					MinDataVO localMinDataVO2 = new MinDataVO();
					localMinDataVO2.tradeDate = localObject.tradeDate;
					localMinDataVO2.time = i;
					localMinDataVO2.curPrice = localObject.curPrice;
					localMinDataVO2.totalAmount = localObject.totalAmount;
					localMinDataVO2.totalMoney = localObject.totalMoney;
					localMinDataVO2.averPrice = localObject.balancePrice;
					localMinDataVO2.reserveCount = localObject.reserveCount;
					localVector.add(localMinDataVO2);
				}
				i = n;
			}
			localObject = localBillDataVO;
		}
		if (localObject != null) {
			MinDataVO localMinDataVO1 = new MinDataVO();
			localMinDataVO1.tradeDate = localObject.tradeDate;
			localMinDataVO1.time = i;
			localMinDataVO1.curPrice = localObject.curPrice;
			localMinDataVO1.totalAmount = localObject.totalAmount;
			localMinDataVO1.totalMoney = localObject.totalMoney;
			localMinDataVO1.averPrice = localObject.balancePrice;
			localMinDataVO1.reserveCount = localObject.reserveCount;
			localVector.add(localMinDataVO1);
		}
		return (MinDataVO[]) localVector.toArray(new MinDataVO[localVector.size()]);
	}

	public SortListVO GetSortList(int paramInt) {
		if ((paramInt >= 0) && (paramInt < 10) && (sortLists != null) && (sortLists.length == 10))
			return sortLists[paramInt];
		return new SortListVO();
	}

	public int getProductType(String paramString) {
		ProductInfoVO localProductInfoVO = (ProductInfoVO) htProductInfo.get(paramString);
		if (localProductInfoVO != null)
			return localProductInfoVO.status;
		return -1;
	}

	public BillDataVO calculateBill(Quotation paramQuotation, PreData paramPreData) {
		BillDataVO localBillDataVO = getBillInstance(paramQuotation);
		System.out.println(localBillDataVO.time + "---------------------------------");
		if (paramQuotation.openAmount != null)
			localBillDataVO.openAmount = Integer.parseInt(paramQuotation.openAmount.longValue() - paramPreData.preOpenAmount + "");
		if (paramQuotation.closeAmount != null)
			localBillDataVO.closeAmount = Integer.parseInt(paramQuotation.closeAmount.longValue() - paramPreData.preCloseAmount + "");
		return localBillDataVO;
	}

	public BillDataVO getBillInstance(Quotation paramQuotation) {
		BillDataVO localBillDataVO = new BillDataVO();
		if (paramQuotation == null)
			return localBillDataVO;
		Timestamp localTimestamp = paramQuotation.createTime;
		localBillDataVO.commodityID = paramQuotation.commodityID;
		localBillDataVO.tradeDate = ((localTimestamp.getYear() + 1900) * 10000 + (localTimestamp.getMonth() + 1) * 100 + localTimestamp.getDate());
		localBillDataVO.time = (localTimestamp.getHours() * 10000 + localTimestamp.getMinutes() * 100 + localTimestamp.getSeconds());
		localBillDataVO.curPrice = Float.parseFloat(paramQuotation.curPrice + "");
		localBillDataVO.reserveCount = Integer.parseInt(paramQuotation.reserveCount + "");
		localBillDataVO.totalMoney = paramQuotation.totalMoney.doubleValue();
		localBillDataVO.totalAmount = paramQuotation.totalAmount.longValue();
		localBillDataVO.buyPrice = Float.parseFloat(paramQuotation.buy[0] + "");
		localBillDataVO.sellPrice = Float.parseFloat(paramQuotation.sell[0] + "");
		localBillDataVO.openAmount = Integer.parseInt(paramQuotation.openAmount + "");
		localBillDataVO.closeAmount = Integer.parseInt(paramQuotation.closeAmount + "");
		localBillDataVO.tradeCue = Integer.parseInt(paramQuotation.tradeCue + "");
		localBillDataVO.balancePrice = Float.parseFloat(paramQuotation.price + "");
		return localBillDataVO;
	}

	public ProductDataVO transProductDate(Quotation paramQuotation, ProductDataVO paramProductDataVO) {
		String str1 = paramQuotation.commodityID;
		Timestamp localTimestamp = paramQuotation.createTime;
		String str2 = "00";
		String str3 = str2 + str1;
		paramProductDataVO.time = localTimestamp;
		paramProductDataVO.yesterBalancePrice = Float.parseFloat(paramQuotation.yesterBalancePrice + "");
		paramProductDataVO.closePrice = Float.parseFloat(paramQuotation.closePrice + "");
		paramProductDataVO.openPrice = Float.parseFloat(paramQuotation.openPrice + "");
		if (paramProductDataVO.closePrice == 0.0F)
			paramProductDataVO.closePrice = paramProductDataVO.openPrice;
		paramProductDataVO.highPrice = Float.parseFloat(paramQuotation.highPrice + "");
		paramProductDataVO.lowPrice = Float.parseFloat(paramQuotation.lowPrice + "");
		paramProductDataVO.curPrice = Float.parseFloat(paramQuotation.curPrice + "");
		paramProductDataVO.curAmount = Integer.parseInt(paramQuotation.curAmount + "");
		paramProductDataVO.openAmount = Integer.parseInt(paramQuotation.openAmount + "");
		paramProductDataVO.closeAmount = Integer.parseInt(paramQuotation.closeAmount + "");
		paramProductDataVO.reserveCount = Integer.parseInt(paramQuotation.reserveCount + "");
		paramProductDataVO.reserveChange = Integer.parseInt(paramQuotation.reserveChange + "");
		paramProductDataVO.balancePrice = Float.parseFloat(paramQuotation.price + "");
		paramProductDataVO.totalMoney = Float.parseFloat(paramQuotation.totalMoney + "");
		paramProductDataVO.totalAmount = Integer.parseInt(paramQuotation.totalAmount + "");
		for (int i = 0; i < this.buySell; i++) {
			paramProductDataVO.buyPrice[i] = Float.parseFloat(paramQuotation.buy[i] + "");
			paramProductDataVO.sellPrice[i] = Float.parseFloat(paramQuotation.sell[i] + "");
			paramProductDataVO.buyAmount[i] = Integer.parseInt(paramQuotation.buyqty[i] + "");
			paramProductDataVO.sellAmount[i] = Integer.parseInt(paramQuotation.sellqty[i] + "");
		}
		paramProductDataVO.outAmount = Integer.parseInt(paramQuotation.outAmount + "");
		paramProductDataVO.inAmount = Integer.parseInt(paramQuotation.inAmount + "");
		paramProductDataVO.tradeCue = Integer.parseInt(paramQuotation.tradeCue + "");
		paramProductDataVO.no = Integer.parseInt(paramQuotation.no + "");
		return paramProductDataVO;
	}

	private class CodeSort implements Comparator {
		private CodeSort() {
		}

		public int compare(Object paramObject1, Object paramObject2) {
			ProductInfoVO localProductInfoVO1 = (ProductInfoVO) paramObject1;
			ProductInfoVO localProductInfoVO2 = (ProductInfoVO) paramObject2;
			return localProductInfoVO1.code.compareTo(localProductInfoVO2.code);
		}
	}
}