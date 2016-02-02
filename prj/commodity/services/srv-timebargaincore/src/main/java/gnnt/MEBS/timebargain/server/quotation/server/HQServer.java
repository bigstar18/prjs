package gnnt.MEBS.timebargain.server.quotation.server;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.dao.quotation.BaseLoader;
import gnnt.MEBS.timebargain.server.dao.quotation.IDBTransfer;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.quotation.BillDataVO;
import gnnt.MEBS.timebargain.server.model.quotation.DayDataVO;
import gnnt.MEBS.timebargain.server.model.quotation.HQModel;
import gnnt.MEBS.timebargain.server.model.quotation.KlineVO;
import gnnt.MEBS.timebargain.server.model.quotation.TradeTimeVO;
import gnnt.MEBS.timebargain.server.quotation.HQEngine;
import gnnt.MEBS.timebargain.server.util.DateUtil;

public class HQServer {
	private Log log = LogFactory.getLog(HQServer.class);
	private static HQServer instance;
	private List<TradeTimeVO> tradeSeclist;
	private int tradeStaTime;
	private int tradeEndTime;
	private Server server = Server.getInstance();
	private SystemStatus sysInfo = this.server.getSystemStatus();
	private IDBTransfer trans = HQEngine.trans;
	private BaseLoader loader = HQEngine.loader;
	public HashSet<String> quoCodeSet = new HashSet();
	public Map<String, HQModel> currentData = new HashMap();
	private long currentNum;

	public static HQServer getInstance() {
		if (instance == null) {
			synchronized (HQServer.class) {
				if (instance == null) {
					instance = new HQServer();
				}
			}
		}
		return instance;
	}

	public List<TradeTimeVO> getTradeSeclist() {
		return this.tradeSeclist;
	}

	public int getTradeStaTime() {
		return this.tradeStaTime;
	}

	public int getTradeEndTime() {
		return this.tradeEndTime;
	}

	public void updateQuotation(Quotation paramQuotation) {
		this.currentNum += 1L;
		paramQuotation.setNo(Long.valueOf(this.currentNum));
		String str = paramQuotation.getCommodityID();
		HQModel localHQModel = (HQModel) this.currentData.get(paramQuotation.getCommodityID());
		if (localHQModel == null) {
			localHQModel = new HQModel();
			this.currentData.put(str, localHQModel);
		}
		localHQModel.setMark(this.currentNum);
		localHQModel.setQuotation(paramQuotation);
	}

	public List<Quotation> queryQuotation(long paramLong) {
		ArrayList localArrayList = null;
		if (paramLong == this.currentNum) {
			return null;
		}
		localArrayList = new ArrayList();
		Set localSet = this.currentData.entrySet();
		Iterator localIterator = localSet.iterator();
		while (localIterator.hasNext()) {
			Map.Entry localEntry = (Map.Entry) localIterator.next();
			HQModel localHQModel = (HQModel) localEntry.getValue();
			if (localHQModel.getMark() > paramLong) {
				localArrayList.add(localHQModel.getQuotation());
			}
		}
		return localArrayList;
	}

	public boolean checkTradeTime() {
		if (this.loader == null) {
			this.loader = HQEngine.loader;
		}
		this.tradeSeclist = this.loader.loadTradeTime();
		if ((this.tradeSeclist != null) && (this.tradeSeclist.size() != 0)) {
			this.tradeStaTime = getTradeBeginTime(this.tradeSeclist);
			this.tradeEndTime = getTradeEndTime(this.tradeSeclist);
			return true;
		}
		this.log.info("没有加载交易节信息，请先初始化市场再开启QUOTATION");
		return false;
	}

	public void checkTradeSec() {
		this.trans.checkTradeSec(this.tradeSeclist, getTradeDateForLong() + "");
	}

	public boolean checkInitTime() {
		boolean bool = false;
		this.tradeSeclist = this.loader.loadTradeTime();
		this.tradeStaTime = getTradeBeginTime(this.tradeSeclist);
		this.tradeEndTime = getTradeEndTime(this.tradeSeclist);
		int i = Integer.valueOf(this.tradeStaTime).intValue() / 100 * 60 + Integer.valueOf(this.tradeStaTime).intValue() % 100
				- HQEngine.config.clearSpace;
		int j = i / 60 * 100 + i % 60;
		int k = Integer.valueOf(j).intValue() / 100;
		int m = Integer.valueOf(j).intValue() % 100;
		Date localDate = getDBTime();
		bool = ((localDate.getMinutes() > m) && (localDate.getHours() == k)) || (localDate.getHours() > k);
		return bool;
	}

	public void updateSysdate() {
		this.trans.updateSysdate(this.loader.loadSysTime().getTime());
	}

	public SystemStatus getSysInfo() {
		return this.server.getSystemStatus();
	}

	public Date getTradeDate() {
		this.sysInfo = this.server.getSystemStatus();
		return this.sysInfo.getTradeDate();
	}

	public long getTradeDateForLong() {
		this.sysInfo = this.server.getSystemStatus();
		return Integer.parseInt(DateUtil.formatDate(this.sysInfo.getTradeDate(), "yyyyMMdd"));
	}

	public Date getTradeTime() {
		return str2Date(DateUtil.formatDate(getDBTime(), "yyyyMMddHHmmss"));
	}

	public String getHQDate() {
		if (this.trans == null) {
			this.trans = HQEngine.trans;
		}
		return this.trans.getHQDate();
	}

	public Date getDBTime() {
		Date localDate = null;
		localDate = new Date(System.currentTimeMillis() + this.server.getServerInit().getDiffTime());
		return localDate;
	}

	public void loadCodeSet() {
		this.trans.loadCodeSet(this.quoCodeSet);
	}

	public long loadQuotationNO() {
		this.currentNum = this.loader.loadQuotationNO();
		return this.currentNum;
	}

	public void addOneComty(String paramString) {
		this.trans.addOneComty(this.loader.getOneComty(paramString), getDBTime().getTime(), this.loader.getOneCmdtyTradeSec(paramString));
	}

	public void addOneData(Quotation paramQuotation) {
		this.trans.addOneData(paramQuotation);
	}

	public boolean isBackUp() {
		return this.trans.isBackUp();
	}

	public void transferProductData(List<Quotation> paramList) {
		this.trans.transferProductData(paramList);
	}

	public void backupProcess() {
		List localList = null;
		String str1 = this.sysInfo.getTradeDate() + "";
		try {
			str1 = new SimpleDateFormat("yyyyMMdd").format(this.sysInfo.getTradeDate());
			localList = this.loader.loadAllProductData();
			this.trans.insertHisBillData();
			this.log.info("TradeDate:" + this.sysInfo.getTradeDate() + "" + "成交明细备份完毕... ");
		} catch (Exception localException1) {
			errorException(localException1);
		}
		Iterator localIterator = localList.iterator();
		while (localIterator.hasNext()) {
			Object localObject = localIterator.next();
			try {
				Map localMap = (Map) localObject;
				String str2 = (String) localMap.get("COMMODITYID");
				float f = ((BigDecimal) localMap.get("YESTERBALANCEPRICE")).floatValue();
				long l = this.trans.getPreReserveCount(str2);
				ArrayList localArrayList = this.trans.queryBillData(str2);
				if ((localArrayList == null) || (localArrayList.size() == 0)) {
					this.log.info("TradeDate:" + str1 + "" + "Code:" + str2 + "当日没有成交数据");
				} else {
					this.trans.insertHisDayData(str1, localMap);
					KlineVO localKlineVO1 = makeMinKLine(str2, 5, localArrayList, str1, f, l);
					this.trans.insertHisMinKLine(str2, localKlineVO1);
					this.log.info("TradeDate:" + this.sysInfo.getTradeDate() + "Code:" + str2 + "5分钟K线备份完毕... ");
					KlineVO localKlineVO2 = makeMinKLine(str2, 1, localArrayList, str1, f, l);
					this.trans.insertHisMinKLine(str2, localKlineVO2);
					this.log.info("TradeDate:" + this.sysInfo.getTradeDate() + "Code:" + str2 + "1分钟K线备份完毕... ");
				}
			} catch (Exception localException2) {
				errorException(localException2);
			}
		}
		this.trans.updateMaketStatus(-1, 1);
		this.log.info("TradeDate:" + str1 + "备份完成！");
	}

	private KlineVO makeMinKLine(String paramString1, int paramInt, ArrayList<BillDataVO> paramArrayList, String paramString2, float paramFloat,
			long paramLong) {
		Vector localVector = new Vector();
		DayDataVO[] arrayOfDayDataVO = new DayDataVO[0];
		int i = -1;
		double d = 0.0D;
		long l = 0L;
		TradeTimeVO[] arrayOfTradeTimeVO = getTimeRange(paramString1);
		int j = TradeTimeVO.GetTotalMinute(arrayOfTradeTimeVO);
		j /= paramInt;
		float f = this.trans.queryProductUnit(paramString1);
		if (f <= 0.0F) {
			f = 1.0F;
		}
		DayDataVO localObject = null;
		for (int k = 0; k < paramArrayList.size(); k++) {
			BillDataVO localBillDataVO = (BillDataVO) paramArrayList.get(k);
			if (localBillDataVO.curPrice > 0.0F) {
				int n = GetCurrentMinTime(localBillDataVO.date, localBillDataVO.time, paramInt);
				if (n != i) {
					if (localObject != null) {
						if (((DayDataVO) localObject).getTotalAmount() > 0L) {
							((DayDataVO) localObject).setBalancePrice(
									(float) (((DayDataVO) localObject).getTotalMoney() / ((DayDataVO) localObject).getTotalAmount() / f));
						}
						localVector.add(localObject);
					}
					localObject = new DayDataVO();
					try {
						((DayDataVO) localObject).setTime(new SimpleDateFormat("yyyyMMdd").parse(localBillDataVO.date + ""));
					} catch (ParseException localParseException) {
						localParseException.printStackTrace();
					}
					((DayDataVO) localObject).getTime().setHours(n / 10000);
					((DayDataVO) localObject).getTime().setMinutes(n / 100 % 100);
					((DayDataVO) localObject).getTime().setSeconds(n % 100);
					((DayDataVO) localObject).setOpenPrice(localBillDataVO.curPrice);
					((DayDataVO) localObject).setHighPrice(localBillDataVO.curPrice);
					((DayDataVO) localObject).setLowPrice(localBillDataVO.curPrice);
					((DayDataVO) localObject).setClosePrice(localBillDataVO.curPrice);
					((DayDataVO) localObject).setBalancePrice(localBillDataVO.balancePrice);
					((DayDataVO) localObject).setReserveCount(localBillDataVO.reserveCount);
					((DayDataVO) localObject).setTotalAmount(((DayDataVO) localObject).getTotalAmount() + localBillDataVO.totalAmount - l);
					((DayDataVO) localObject).setTotalMoney(((DayDataVO) localObject).getTotalMoney() + localBillDataVO.totalMoney - d);
					i = n;
				} else {
					if (localBillDataVO.curPrice > ((DayDataVO) localObject).getHighPrice()) {
						((DayDataVO) localObject).setHighPrice(localBillDataVO.curPrice);
					}
					if (localBillDataVO.curPrice < ((DayDataVO) localObject).getLowPrice()) {
						((DayDataVO) localObject).setLowPrice(localBillDataVO.curPrice);
					}
					((DayDataVO) localObject).setClosePrice(localBillDataVO.curPrice);
					((DayDataVO) localObject).setBalancePrice(localBillDataVO.balancePrice);
					((DayDataVO) localObject).setReserveCount(localBillDataVO.reserveCount);
					((DayDataVO) localObject).setTotalAmount(((DayDataVO) localObject).getTotalAmount() + localBillDataVO.totalAmount - l);
					((DayDataVO) localObject).setTotalMoney(((DayDataVO) localObject).getTotalMoney() + localBillDataVO.totalMoney - d);
				}
				l = localBillDataVO.totalAmount;
				d = localBillDataVO.totalMoney;
			}
		}
		if (localObject != null) {
			if (((DayDataVO) localObject).getTotalAmount() > 0L) {
				((DayDataVO) localObject)
						.setBalancePrice((float) (((DayDataVO) localObject).getTotalMoney() / ((DayDataVO) localObject).getTotalAmount() / f));
			}
			localVector.addElement(localObject);
		}
		if (localVector.size() > 0) {
			arrayOfDayDataVO = new DayDataVO[j];
			int k = 0;
			for (int m = 0; m < j; m++) {
				Calendar localCalendar = TradeTimeVO.GetDateFromIndex(m * paramInt + (paramInt - 1), arrayOfTradeTimeVO);
				if (k < localVector.size()) {
					DayDataVO localObject1 = (DayDataVO) localVector.elementAt(k);
					String str1 = localCalendar.getTime().toLocaleString();
					String str2 = ((DayDataVO) localObject1).getTime().toLocaleString();
					if (str1.equals(str2)) {
						arrayOfDayDataVO[m] = localObject1;
						k++;
						continue;
					}
				}
				arrayOfDayDataVO[m] = new DayDataVO();
				arrayOfDayDataVO[m].setTime(localCalendar.getTime());
				if (m == 0) {
					arrayOfDayDataVO[m].setDefult(paramFloat, paramLong);
				} else {
					arrayOfDayDataVO[m].setDefult(arrayOfDayDataVO[(m - 1)].getBalancePrice(), arrayOfDayDataVO[(m - 1)].getReserveCount());
				}
			}
		}
		KlineVO localKlineVO = new KlineVO(arrayOfDayDataVO, paramInt);
		return localKlineVO;
	}

	private int GetCurrentMinTime(int paramInt1, int paramInt2, int paramInt3) {
		int j = 0;
		int k = getCheckTime(paramInt1, paramInt2);
		int m = (k / 10000 * 60 + k / 100 % 100) / paramInt3 * paramInt3;
		for (int n = 0; n < this.tradeSeclist.size(); n++) {
			int i = ((TradeTimeVO) this.tradeSeclist.get(n)).endTime * 100;
			if (k != i) {
				j = 1;
			} else {
				j = 0;
				break;
			}
		}
		if ((k % (paramInt3 * 100) >= 0) && (j != 0)) {
			m += paramInt3;
		}
		int n = m / 60 * 10000 + m % 60 * 100;
		return n;
	}

	private int GetCurrent5MinTime(int paramInt1, int paramInt2) {
		int j = 0;
		int k = getCheckTime(paramInt1, paramInt2);
		int m = (k / 10000 * 60 + k / 100 % 100) / 5 * 5;
		for (int n = 0; n < this.tradeSeclist.size(); n++) {
			int i = ((TradeTimeVO) this.tradeSeclist.get(n)).endTime * 100;
			if (k != i) {
				j = 1;
			} else {
				j = 0;
				break;
			}
		}
		if ((k % 500 >= 0) && (j != 0)) {
			m += 5;
		}
		int n = m / 60 * 10000 + m % 60 * 100;
		return n;
	}

	private int getCheckTime(int paramInt1, int paramInt2) {
		long l1 = paramInt1 * 1000000L + paramInt2;
		if (this.tradeSeclist.size() > 0) {
			if (l1 < ((TradeTimeVO) this.tradeSeclist.get(0)).beginDate * 1000000L + ((TradeTimeVO) this.tradeSeclist.get(0)).beginTime * 100) {
				return ((TradeTimeVO) this.tradeSeclist.get(0)).beginTime * 100;
			}
			if (l1 > ((TradeTimeVO) this.tradeSeclist.get(this.tradeSeclist.size() - 1)).endDate * 1000000L
					+ ((TradeTimeVO) this.tradeSeclist.get(this.tradeSeclist.size() - 1)).endTime * 100) {
				return ((TradeTimeVO) this.tradeSeclist.get(this.tradeSeclist.size() - 1)).endTime * 100;
			}
		}
		for (int i = 0; i < this.tradeSeclist.size(); i++) {
			long l2 = ((TradeTimeVO) this.tradeSeclist.get(i)).beginDate * 1000000L + ((TradeTimeVO) this.tradeSeclist.get(i)).beginTime * 100;
			long l3 = ((TradeTimeVO) this.tradeSeclist.get(i)).endDate * 1000000L + ((TradeTimeVO) this.tradeSeclist.get(i)).endTime * 100;
			if ((l1 >= l2) && (l1 <= l3)) {
				return paramInt2;
			}
			if ((i < this.tradeSeclist.size() - 1) && (l1 > l3) && (l1 < ((TradeTimeVO) this.tradeSeclist.get(i + 1)).beginDate * 1000000L
					+ ((TradeTimeVO) this.tradeSeclist.get(i + 1)).beginTime * 100)) {
				return ((TradeTimeVO) this.tradeSeclist.get(i)).endTime * 100;
			}
		}
		return paramInt2;
	}

	private TradeTimeVO[] getTimeRange(String paramString) {
		HashMap localHashMap = this.loader.getCommodityTradeSec();
		String[] arrayOfString = ((String) localHashMap.get(paramString)).split(",");
		int[] arrayOfInt = new int[arrayOfString.length];
		for (int i = 0; i < arrayOfString.length; i++) {
			arrayOfInt[i] = Integer.parseInt(arrayOfString[i]);
		}
		Vector localVector = new Vector();
		for (int j = 0; j < arrayOfInt.length; j++) {
			for (int k = 0; k < this.tradeSeclist.size(); k++) {
				if (arrayOfInt[j] == ((TradeTimeVO) this.tradeSeclist.get(k)).orderID) {
					localVector.add(this.tradeSeclist.get(k));
				}
			}
		}
		if (localVector.size() == 0) {
			for (int j = 0; j < this.tradeSeclist.size(); j++) {
				localVector.add(this.tradeSeclist.get(j));
			}
		}
		return (TradeTimeVO[]) localVector.toArray(new TradeTimeVO[localVector.size()]);
	}

	public boolean initHQData(Calendar paramCalendar) {
		this.log.info("Quotation Init HQ Data....");
		long l = getDBTime().getTime();
		String str = getTradeDateForLong() + "";
		clearData();
		if (!this.trans.clear()) {
			this.log.info("行情初始化失败，隔5秒后再进行一次初始化");
			try {
				Thread.sleep(5000L);
			} catch (InterruptedException localInterruptedException) {
				localInterruptedException.printStackTrace();
			}
			if (!this.trans.clear()) {
				return false;
			}
		}
		HashMap localHashMap = this.loader.getCommodityTradeSec();
		this.trans.transferComodity(this.loader.loadCommodity(), l, localHashMap);
		this.trans.transferComodity(this.loader.loadHisCommodity(), l, localHashMap);
		this.trans.transferTradeTime(this.loader.loadTradeTime(), str);
		this.trans.initCurrentdata(this.loader.loadProductData(paramCalendar), l);
		this.trans.updateMaketStatus(1, 0);
		this.log.info("Quotation Init Process Done....");
		return true;
	}

	public int getTradeBeginTime(List<TradeTimeVO> paramList) {
		int i = ((TradeTimeVO) paramList.get(0)).beginTime;
		int j = 1;
		for (int k = 0; k < paramList.size(); k++) {
			if (k == 0) {
				j = ((TradeTimeVO) paramList.get(0)).orderID;
			} else if (j > ((TradeTimeVO) paramList.get(k)).orderID) {
				j = ((TradeTimeVO) paramList.get(k)).orderID;
				i = ((TradeTimeVO) paramList.get(k)).beginTime;
			}
		}
		return i;
	}

	public int getTradeEndTime(List<TradeTimeVO> paramList) {
		int i = ((TradeTimeVO) paramList.get(0)).endTime;
		int j = 1;
		for (int k = 0; k < paramList.size(); k++) {
			if (k == 0) {
				j = ((TradeTimeVO) paramList.get(0)).orderID;
			} else if (j < ((TradeTimeVO) paramList.get(k)).orderID) {
				j = ((TradeTimeVO) paramList.get(k)).orderID;
				i = ((TradeTimeVO) paramList.get(k)).endTime;
			}
		}
		return i;
	}

	public boolean checkLastQuotation() {
		this.log.info("check Last Quotation Method..");
		List localList = this.loader.loadProductData();
		if ((localList != null) && (localList.size() > 0)) {
			this.trans.transferProductData(localList, 1);
			return true;
		}
		return false;
	}

	public void errorException(Exception paramException) {
		StackTraceElement[] arrayOfStackTraceElement = paramException.getStackTrace();
		this.log.error(paramException.getMessage());
		for (int i = 0; i < arrayOfStackTraceElement.length; i++) {
			this.log.error(arrayOfStackTraceElement[i].toString());
		}
	}

	public Date str2Date(String paramString) {
		Date localDate = null;
		try {
			localDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(paramString);
		} catch (ParseException localParseException) {
			localParseException.printStackTrace();
		}
		return localDate;
	}

	public Map<String, HQModel> getCurrentData() {
		return this.currentData;
	}

	public long getCurrentNum() {
		return this.currentNum;
	}

	public void setCurrentNum(int paramInt) {
		this.currentNum = paramInt;
	}

	public void clearData() {
		this.currentNum = 0L;
		this.currentData.clear();
		this.currentData = null;
		this.currentData = new HashMap();
	}

	public static void main(String[] paramArrayOfString) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTimeInMillis(0L);
		System.out.println(localCalendar.getTime());
		HQServer localHQServer = new HQServer();
		localHQServer.tradeSeclist = new ArrayList();
		TradeTimeVO localTradeTimeVO1 = new TradeTimeVO();
		localTradeTimeVO1.beginTime = 900;
		localTradeTimeVO1.endTime = 1130;
		localHQServer.tradeSeclist.add(localTradeTimeVO1);
		TradeTimeVO localTradeTimeVO2 = new TradeTimeVO();
		localTradeTimeVO2.beginTime = 1330;
		localTradeTimeVO2.endTime = 1600;
		localHQServer.tradeSeclist.add(localTradeTimeVO2);
	}
}
