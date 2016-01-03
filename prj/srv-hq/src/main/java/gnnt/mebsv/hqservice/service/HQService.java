package gnnt.mebsv.hqservice.service;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.util.Configuration;
import gnnt.mebsv.hqservice.hq.QuotationServer;
import gnnt.mebsv.hqservice.model.BillDataVO;
import gnnt.mebsv.hqservice.model.ClientSocket;
import gnnt.mebsv.hqservice.model.MinDataVO;
import gnnt.mebsv.hqservice.model.ProductDataVO;
import gnnt.mebsv.hqservice.model.TradeTimeVO;
import gnnt.mebsv.hqservice.model.dictionary.AddrDictionary;
import gnnt.mebsv.hqservice.model.dictionary.IndustryDictionary;
import gnnt.mebsv.hqservice.model.pojo.AddrVO;
import gnnt.mebsv.hqservice.model.pojo.IndustryVO;
import gnnt.mebsv.hqservice.service.dictionaryserver.IDictionaryServer;
import gnnt.mebsv.hqservice.service.dictionaryserver.impl.DictionaryServer;
import gnnt.mebsv.hqservice.tools.HQServiceUtil;

public class HQService {
	private Log log = LogFactory.getLog(HQService.class);
	QuotationServer quotation;
	String defaultClientVersion = "F3.1.9.0000";
	int pushNum;
	IDictionaryServer dictionaryServer;

	public void init() {
		try {
			this.quotation = QuotationServer.getInstance();
			this.pushNum = Integer.parseInt(new Configuration().getSection("MEBS.HQService").getProperty("PushNum"));
			this.dictionaryServer = new DictionaryServer();
		} catch (Exception localException) {
			localException.printStackTrace();
			this.log.debug(localException.toString());
		}
	}

	public int validateClientVersion(String paramString) {
		int i = 0;
		String[] arrayOfString1 = new String[0];
		String[] arrayOfString2 = new String[0];
		if ((paramString != null) && (!"".equals(paramString))) {
			arrayOfString1 = paramString.split("\\.");
			if (arrayOfString1[0].length() == 1)
				arrayOfString1[0] = new StringBuilder().append("F").append(arrayOfString1[0]).toString();
			if ((this.defaultClientVersion != null) && (!"".equals(this.defaultClientVersion)))
				arrayOfString2 = this.defaultClientVersion.split("\\.");
			if (Integer.parseInt(arrayOfString1[0].substring(1, 2)) > Integer.parseInt(arrayOfString2[0].substring(1, 2)))
				i = 1;
			else if (Integer.parseInt(arrayOfString1[0].substring(1, 2)) == Integer.parseInt(arrayOfString2[0].substring(1, 2))) {
				if (Integer.parseInt(arrayOfString1[1]) > Integer.parseInt(arrayOfString2[1]))
					i = 1;
				else if (Integer.parseInt(arrayOfString1[1]) == Integer.parseInt(arrayOfString2[1])) {
					if (Integer.parseInt(arrayOfString1[2]) > Integer.parseInt(arrayOfString2[2]))
						i = 1;
					else if (Integer.parseInt(arrayOfString1[2]) == Integer.parseInt(arrayOfString2[2])) {
						if (Integer.parseInt(arrayOfString1[3]) >= Integer.parseInt(arrayOfString2[3]))
							i = 1;
						else
							i = 0;
					} else
						i = 0;
				} else
					i = 0;
			} else
				i = 0;
		}
		return i;
	}

	public byte[] getMinLineInterval() throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		localDataOutputStream.writeByte(12);
		localDataOutputStream.writeInt(this.quotation.getMinLineInterval());
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getMarketSortData(int paramInt) throws IOException {
		Vector localVector = new Vector();
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		localDataOutputStream.writeByte(10);
		String[] arrayOfString = this.quotation.GetSortList(3).codeList;
		if (paramInt > arrayOfString.length)
			paramInt = arrayOfString.length;
		localDataOutputStream.writeInt(paramInt);
		localDataOutputStream.writeInt(paramInt * 9);
		int i = 0;
		for (int j = arrayOfString.length - 1; i < paramInt; j--) {
			localVector.add(arrayOfString[j]);
			i++;
		}
		for (i = 0; i < paramInt; i++)
			localVector.add(arrayOfString[i]);
		arrayOfString = (String[]) localVector.toArray(new String[localVector.size()]);
		localVector.clear();
		ProductDataVO[] arrayOfProductDataVO = this.quotation.getProductData(arrayOfString);
		for (int j = 0; j < arrayOfProductDataVO.length; j++) {
			localDataOutputStream.writeUTF(arrayOfProductDataVO[j].marketID);
			localDataOutputStream.writeUTF(arrayOfProductDataVO[j].code);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[j].curPrice);
			if (arrayOfProductDataVO[j].upRate > 0.0F)
				localDataOutputStream.writeByte(0);
			else if (arrayOfProductDataVO[j].upRate < 0.0F)
				localDataOutputStream.writeByte(1);
			else
				localDataOutputStream.writeByte(2);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[j].upRate);
		}
		arrayOfString = this.quotation.GetSortList(4).codeList;
		int j = 0;
		for (int k = arrayOfString.length - 1; j < paramInt; k--) {
			localVector.add(arrayOfString[k]);
			j++;
		}
		arrayOfString = (String[]) localVector.toArray(new String[localVector.size()]);
		localVector.clear();
		arrayOfProductDataVO = this.quotation.getProductData(arrayOfString);
		for (j = 0; j < arrayOfProductDataVO.length; j++) {
			localDataOutputStream.writeUTF(arrayOfProductDataVO[j].marketID);
			localDataOutputStream.writeUTF(arrayOfProductDataVO[j].code);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[j].curPrice);
			if (arrayOfProductDataVO[j].upRate > 0.0F)
				localDataOutputStream.writeByte(0);
			else if (arrayOfProductDataVO[j].upRate < 0.0F)
				localDataOutputStream.writeByte(1);
			else
				localDataOutputStream.writeByte(2);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[j].shakeRate);
		}
		arrayOfString = this.quotation.GetSortList(8).codeList;
		j = 0;
		for (int k = arrayOfString.length - 1; j < paramInt; k--) {
			localVector.add(arrayOfString[k]);
			j++;
		}
		for (j = 0; j < paramInt; j++)
			localVector.add(arrayOfString[j]);
		arrayOfString = (String[]) localVector.toArray(new String[localVector.size()]);
		localVector.clear();
		arrayOfProductDataVO = this.quotation.getProductData(arrayOfString);
		for (j = 0; j < arrayOfProductDataVO.length; j++) {
			localDataOutputStream.writeUTF(arrayOfProductDataVO[j].marketID);
			localDataOutputStream.writeUTF(arrayOfProductDataVO[j].code);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[j].curPrice);
			if (arrayOfProductDataVO[j].upRate5min > 0.0F)
				localDataOutputStream.writeByte(0);
			else if (arrayOfProductDataVO[j].upRate5min < 0.0F)
				localDataOutputStream.writeByte(1);
			else
				localDataOutputStream.writeByte(2);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[j].upRate5min);
		}
		arrayOfString = this.quotation.GetSortList(5).codeList;
		j = 0;
		for (int k = arrayOfString.length - 1; j < paramInt; k--) {
			localVector.add(arrayOfString[k]);
			j++;
		}
		arrayOfString = (String[]) localVector.toArray(new String[localVector.size()]);
		localVector.clear();
		arrayOfProductDataVO = this.quotation.getProductData(arrayOfString);
		for (j = 0; j < arrayOfProductDataVO.length; j++) {
			localDataOutputStream.writeUTF(arrayOfProductDataVO[j].marketID);
			localDataOutputStream.writeUTF(arrayOfProductDataVO[j].code);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[j].curPrice);
			if (arrayOfProductDataVO[j].upRate > 0.0F)
				localDataOutputStream.writeByte(0);
			else if (arrayOfProductDataVO[j].upRate < 0.0F)
				localDataOutputStream.writeByte(1);
			else
				localDataOutputStream.writeByte(2);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[j].amountRate);
		}
		arrayOfString = this.quotation.GetSortList(7).codeList;
		j = 0;
		for (int k = arrayOfString.length - 1; j < paramInt; k--) {
			localVector.add(arrayOfString[k]);
			j++;
		}
		for (j = 0; j < paramInt; j++)
			localVector.add(arrayOfString[j]);
		arrayOfString = (String[]) localVector.toArray(new String[localVector.size()]);
		localVector.clear();
		arrayOfProductDataVO = this.quotation.getProductData(arrayOfString);
		for (j = 0; j < arrayOfProductDataVO.length; j++) {
			localDataOutputStream.writeUTF(arrayOfProductDataVO[j].marketID);
			localDataOutputStream.writeUTF(arrayOfProductDataVO[j].code);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[j].curPrice);
			if (arrayOfProductDataVO[j].upRate > 0.0F)
				localDataOutputStream.writeByte(0);
			else if (arrayOfProductDataVO[j].upRate < 0.0F)
				localDataOutputStream.writeByte(1);
			else
				localDataOutputStream.writeByte(2);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[j].consignRate);
		}
		arrayOfString = this.quotation.GetSortList(6).codeList;
		j = 0;
		for (int k = arrayOfString.length - 1; j < paramInt; k--) {
			localVector.add(arrayOfString[k]);
			j++;
		}
		arrayOfString = (String[]) localVector.toArray(new String[localVector.size()]);
		localVector.clear();
		arrayOfProductDataVO = this.quotation.getProductData(arrayOfString);
		for (j = 0; j < arrayOfProductDataVO.length; j++) {
			localDataOutputStream.writeUTF(arrayOfProductDataVO[j].marketID);
			localDataOutputStream.writeUTF(arrayOfProductDataVO[j].code);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[j].curPrice);
			if (arrayOfProductDataVO[j].upRate > 0.0F)
				localDataOutputStream.writeByte(0);
			else if (arrayOfProductDataVO[j].upRate < 0.0F)
				localDataOutputStream.writeByte(1);
			else
				localDataOutputStream.writeByte(2);
			localDataOutputStream.writeFloat((float) (arrayOfProductDataVO[j].totalMoney / 100.0D));
		}
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getDate() throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		int i = this.quotation.getHqDate().size();
		Set localSet = this.quotation.getHqDate().keySet();
		Iterator localIterator = localSet.iterator();
		while (localIterator.hasNext()) {
			String str = localIterator.next().toString();
			localDataOutputStream.writeByte(9);
			localDataOutputStream.writeUTF(str);
			Date localDate = (Date) this.quotation.getHqDate().get(str);
			Calendar localCalendar = Calendar.getInstance();
			localCalendar.setTime(localDate);
			localDataOutputStream.writeInt(localCalendar.get(1) * 10000 + (localCalendar.get(2) + 1) * 100 + localCalendar.get(5));
			localDataOutputStream.writeInt(HQServiceUtil.dateToHHmmss(localDate));
		}
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getDate(String paramString) throws IOException {
		if ((paramString == null) || ("".equals(paramString)))
			paramString = "00";
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		localDataOutputStream.writeByte(9);
		localDataOutputStream.writeUTF(paramString);
		Date localDate = (Date) this.quotation.getHqDate().get(paramString);
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(localDate);
		localDataOutputStream.writeInt(localCalendar.get(1) * 10000 + (localCalendar.get(2) + 1) * 100 + localCalendar.get(5));
		localDataOutputStream.writeInt(HQServiceUtil.dateToHHmmss(localDate));
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getMinData(ClientSocket paramClientSocket) throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		Set localSet = paramClientSocket.codeList.entrySet();
		Iterator localIterator = localSet.iterator();
		while (localIterator.hasNext()) {
			Map.Entry localEntry = (Map.Entry) localIterator.next();
			String str1 = ((String[]) localEntry.getValue())[2];
			String str2 = ((String[]) localEntry.getValue())[0];
			String str3 = new StringBuilder().append(str1).append(str2).toString();
			System.out.println(new StringBuilder().append("推送过程的").append(str3).toString());
			MinDataVO[] arrayOfMinDataVO = this.quotation.getMinData(str3);
			if (arrayOfMinDataVO == null)
				return new byte[0];
			Vector localVector = new Vector();
			if (paramClientSocket.minType == 0) {
				for (int i = 0; i < arrayOfMinDataVO.length; i++)
					if (arrayOfMinDataVO[i].tradeDate > paramClientSocket.minLastDate) {
						localVector.add(arrayOfMinDataVO[i]);
						paramClientSocket.minLastDate = arrayOfMinDataVO[i].tradeDate;
						paramClientSocket.minLastTime = arrayOfMinDataVO[i].time;
					} else if ((arrayOfMinDataVO[i].tradeDate == paramClientSocket.minLastDate)
							&& (arrayOfMinDataVO[i].time > paramClientSocket.minLastTime)) {
						localVector.add(arrayOfMinDataVO[i]);
						paramClientSocket.minLastDate = arrayOfMinDataVO[i].tradeDate;
						paramClientSocket.minLastTime = arrayOfMinDataVO[i].time;
					}
			} else {
				int i = arrayOfMinDataVO.length - paramClientSocket.minLastTime;
				if (i < 0)
					;
				for (i = 0; i < arrayOfMinDataVO.length; i++) {
					paramClientSocket.minLastTime = i;
					localVector.add(arrayOfMinDataVO[i]);
				}
			}
			if (localVector.size() == 0)
				return new byte[0];
			localDataOutputStream.writeByte(6);
			localDataOutputStream.writeUTF(str1);
			localDataOutputStream.writeUTF(str2);
			localDataOutputStream.writeByte(paramClientSocket.minType);
			localDataOutputStream.writeInt(paramClientSocket.minLastDate);
			localDataOutputStream.writeInt(paramClientSocket.minLastTime);
			localDataOutputStream.writeInt(localVector.size());
			for (int i = 0; i < localVector.size(); i++) {
				MinDataVO localMinDataVO = (MinDataVO) localVector.get(i);
				localDataOutputStream.writeInt(localMinDataVO.tradeDate);
				localDataOutputStream.writeInt(localMinDataVO.time);
				localDataOutputStream.writeFloat(localMinDataVO.curPrice);
				localDataOutputStream.writeLong(localMinDataVO.totalAmount);
				localDataOutputStream.writeFloat(localMinDataVO.averPrice);
				localDataOutputStream.writeInt(localMinDataVO.reserveCount);
			}
		}
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getMinData(ClientSocket paramClientSocket, byte paramByte, int paramInt1, int paramInt2) throws IOException {
		System.out.println(new StringBuilder().append("分时查询条件").append(paramClientSocket.codeList).toString());
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		Set localSet = paramClientSocket.codeList.entrySet();
		Iterator localIterator = localSet.iterator();
		int i = 1;
		while (localIterator.hasNext()) {
			Map.Entry localEntry = (Map.Entry) localIterator.next();
			String str1 = ((String[]) localEntry.getValue())[2];
			String str2 = ((String[]) localEntry.getValue())[0];
			String str3 = new StringBuilder().append(str1).append(str2).toString();
			MinDataVO[] arrayOfMinDataVO = this.quotation.getMinData(str3);
			System.out.println(new StringBuilder().append("当前分时数据").append(str1).append(str2).append(arrayOfMinDataVO.length).toString());
			if (arrayOfMinDataVO != null)
				i = 0;
			localDataOutputStream.writeByte(6);
			localDataOutputStream.writeUTF(str1);
			localDataOutputStream.writeUTF(str2);
			localDataOutputStream.writeByte(paramByte);
			localDataOutputStream.writeInt(paramInt1);
			localDataOutputStream.writeInt(paramInt2);
			Vector localVector = new Vector();
			if (paramByte == 0) {
				for (int j = 0; j < arrayOfMinDataVO.length; j++)
					if (arrayOfMinDataVO[j].tradeDate > paramInt1)
						localVector.add(arrayOfMinDataVO[j]);
					else if ((arrayOfMinDataVO[j].tradeDate == paramInt1) && (arrayOfMinDataVO[j].time > paramInt2))
						localVector.add(arrayOfMinDataVO[j]);
			} else {
				int j = arrayOfMinDataVO.length - paramInt2;
				if (j < 0)
					;
				for (j = 0; j < arrayOfMinDataVO.length; j++)
					localVector.add(arrayOfMinDataVO[j]);
			}
			if (localVector.size() > 0)
				this.log.debug(new StringBuilder().append("最后一个分钟数据：").append((MinDataVO) localVector.get(localVector.size() - 1)).toString());
			localDataOutputStream.writeInt(localVector.size());
			for (int j = 0; j < localVector.size(); j++) {
				MinDataVO localMinDataVO = (MinDataVO) localVector.get(j);
				localDataOutputStream.writeInt(localMinDataVO.tradeDate);
				localDataOutputStream.writeInt(localMinDataVO.time);
				localDataOutputStream.writeFloat(localMinDataVO.curPrice);
				localDataOutputStream.writeLong(localMinDataVO.totalAmount);
				localDataOutputStream.writeFloat(localMinDataVO.averPrice);
				localDataOutputStream.writeInt(localMinDataVO.reserveCount);
			}
		}
		if (i != 0)
			return new byte[0];
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getBillData(String paramString1, String paramString2, byte paramByte, long paramLong1, long paramLong2,
			ClientSocket paramClientSocket, String paramString3) throws IOException {
		System.out.println(new StringBuilder().append("读取成交明细数据").append(this.pushNum).toString());
		this.pushNum = Integer.parseInt(new Configuration().getSection("MEBS.HQService").getProperty("PushNum"));
		System.out.println(new StringBuilder().append("读取成交明细数据").append(this.pushNum).toString());
		String str = new StringBuilder().append(paramString1).append(paramString2).toString();
		ProductDataVO localProductDataVO = this.quotation.getProductData(str);
		if (localProductDataVO == null)
			return new byte[0];
		int i = 1;
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		localDataOutputStream.writeByte(7);
		localDataOutputStream.writeUTF(paramString1);
		localDataOutputStream.writeUTF(paramString2);
		localDataOutputStream.writeByte(paramByte);
		localDataOutputStream.writeLong(paramLong1);
		localDataOutputStream.writeLong(paramLong2);
		localDataOutputStream.writeUTF(paramString3);
		Vector localVector = new Vector();
		long l;
		if (paramByte == 0) {
			paramClientSocket.billDataPage = paramByte;
			for (int j = 0; j < localProductDataVO.billData.size(); j++) {
				if (((BillDataVO) localProductDataVO.billData.get(j)).tradeDate > paramLong1)
					localVector.add(localProductDataVO.billData.get(j));
				else if ((((BillDataVO) localProductDataVO.billData.get(j)).tradeDate == paramLong1)
						&& (((BillDataVO) localProductDataVO.billData.get(j)).time > paramLong2))
					localVector.add(localProductDataVO.billData.get(j));
				l = ((BillDataVO) localProductDataVO.billData.get(j)).totalAmount;
				if (!paramClientSocket.differTypebillLastTotalAMap.containsKey(str))
					paramClientSocket.differTypebillLastTotalAMap.put(str, Long.valueOf(l));
				else if (l > ((Long) paramClientSocket.differTypebillLastTotalAMap.get(str)).longValue())
					paramClientSocket.differTypebillLastTotalAMap.put(str, Long.valueOf(l));
				if (localVector.size() > 0)
					i++;
				if ((i > this.pushNum) && (localVector.size() == this.pushNum))
					break;
			}
		} else if (paramByte == 1) {
			int j = (int) (localProductDataVO.billData.size() - paramLong2);
			if (j < 0)
				;
			for (j = 0; j < localProductDataVO.billData.size(); j++) {
				localVector.add(localProductDataVO.billData.get(j));
				l = ((BillDataVO) localProductDataVO.billData.get(j)).totalAmount;
				if (!paramClientSocket.billLastTotalAMap.containsKey(str))
					paramClientSocket.billLastTotalAMap.put(str, Long.valueOf(l));
				else if (l > ((Long) paramClientSocket.billLastTotalAMap.get(str)).longValue())
					paramClientSocket.billLastTotalAMap.put(str, Long.valueOf(l));
			}
			paramClientSocket.billDataPage = paramByte;
		} else if (paramByte == 2) {
			paramClientSocket.billDataPage = 0;
			if (paramLong1 == 0L)
				for (int j = 0; j < localProductDataVO.billData.size(); j++) {
					if (((BillDataVO) localProductDataVO.billData.get(j)).totalAmount > paramLong1) {
						localVector.add(localProductDataVO.billData.get(j));
						paramClientSocket.differTypebillLastTotalAMap.put(str,
								Long.valueOf(((BillDataVO) localProductDataVO.billData.get(j)).totalAmount));
					}
					if (localVector.size() > 0)
						i++;
					if ((i > this.pushNum) && (localVector.size() == this.pushNum))
						break;
				}
			else
				for (int j = 0; j < localProductDataVO.billData.size(); j++) {
					if (((BillDataVO) localProductDataVO.billData.get(j)).totalAmount > paramLong1) {
						localVector.add(localProductDataVO.billData.get(j));
						l = ((BillDataVO) localProductDataVO.billData.get(j)).totalAmount;
						if (!paramClientSocket.differTypebillLastTotalAMap.containsKey(str))
							paramClientSocket.differTypebillLastTotalAMap.put(str, Long.valueOf(l));
						else if (l > ((Long) paramClientSocket.differTypebillLastTotalAMap.get(str)).longValue())
							paramClientSocket.differTypebillLastTotalAMap.put(str, Long.valueOf(l));
					}
					if (localVector.size() > 0)
						i++;
					if ((i > this.pushNum) && (localVector.size() == this.pushNum))
						break;
				}
		}
		localDataOutputStream.writeInt(localVector.size());
		for (int j = 0; j < localVector.size(); j++) {
			BillDataVO localBillDataVO = (BillDataVO) localVector.get(j);
			localDataOutputStream.writeInt(localBillDataVO.tradeDate);
			localDataOutputStream.writeInt(localBillDataVO.time);
			localDataOutputStream.writeInt(localBillDataVO.reserveCount);
			localDataOutputStream.writeFloat(localBillDataVO.buyPrice);
			localDataOutputStream.writeFloat(localBillDataVO.curPrice);
			localDataOutputStream.writeFloat(localBillDataVO.sellPrice);
			localDataOutputStream.writeLong(localBillDataVO.totalAmount);
			localDataOutputStream.writeDouble(localBillDataVO.totalMoney);
			localDataOutputStream.writeInt(localBillDataVO.openAmount);
			localDataOutputStream.writeInt(localBillDataVO.closeAmount);
			localDataOutputStream.writeFloat(localBillDataVO.balancePrice);
			localDataOutputStream.writeUTF(localBillDataVO.obligate);
		}
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getBillData(ClientSocket paramClientSocket) throws IOException {
		this.pushNum = Integer.parseInt(new Configuration().getSection("MEBS.HQService").getProperty("PushNum"));
		Set localSet = paramClientSocket.codeList.entrySet();
		Iterator localIterator = localSet.iterator();
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		int i = 1;
		while (localIterator.hasNext()) {
			Vector localVector = new Vector();
			String[] arrayOfString = (String[]) ((Map.Entry) localIterator.next()).getValue();
			String str1 = arrayOfString[2];
			String str2 = arrayOfString[0];
			String str3 = new StringBuilder().append(str1).append(str2).toString();
			ProductDataVO localProductDataVO = this.quotation.getProductData(str3);
			if (localProductDataVO == null)
				return new byte[0];
			int j = 1;
			for (int k = 0; k < localProductDataVO.billData.size(); k++) {
				long l = ((BillDataVO) localProductDataVO.billData.get(k)).totalAmount;
				if (paramClientSocket.billDataPage == 1) {
					if (((BillDataVO) localProductDataVO.billData.get(k)).tradeDate > paramClientSocket.billLastDate) {
						paramClientSocket.billLastDate = ((BillDataVO) localProductDataVO.billData.get(k)).tradeDate;
						paramClientSocket.billLastTime = ((BillDataVO) localProductDataVO.billData.get(k)).time;
						if (!paramClientSocket.billLastTotalAMap.containsKey(str3)) {
							localVector.add(localProductDataVO.billData.get(k));
							paramClientSocket.billLastTotalAMap.put(str3, Long.valueOf(l));
						} else if (l > ((Long) paramClientSocket.billLastTotalAMap.get(str3)).longValue()) {
							localVector.add(localProductDataVO.billData.get(k));
							paramClientSocket.billLastTotalAMap.put(str3, Long.valueOf(l));
						}
					} else if (((BillDataVO) localProductDataVO.billData.get(k)).tradeDate == paramClientSocket.billLastDate) {
						if (!paramClientSocket.billLastTotalAMap.containsKey(str3)) {
							localVector.add(localProductDataVO.billData.get(k));
							paramClientSocket.billLastDate = ((BillDataVO) localProductDataVO.billData.get(k)).tradeDate;
							paramClientSocket.billLastTime = ((BillDataVO) localProductDataVO.billData.get(k)).time;
							paramClientSocket.billLastTotalAMap.put(str3, Long.valueOf(l));
						} else if (l > ((Long) paramClientSocket.billLastTotalAMap.get(str3)).longValue()) {
							localVector.add(localProductDataVO.billData.get(k));
							paramClientSocket.billLastDate = ((BillDataVO) localProductDataVO.billData.get(k)).tradeDate;
							paramClientSocket.billLastTime = ((BillDataVO) localProductDataVO.billData.get(k)).time;
							paramClientSocket.billLastTotalAMap.put(str3, Long.valueOf(l));
						}
					}
				} else if (((BillDataVO) localProductDataVO.billData.get(k)).tradeDate > paramClientSocket.billLastDate) {
					paramClientSocket.billLastDate = ((BillDataVO) localProductDataVO.billData.get(k)).tradeDate;
					paramClientSocket.billLastTime = ((BillDataVO) localProductDataVO.billData.get(k)).time;
					if (!paramClientSocket.differTypebillLastTotalAMap.containsKey(str3)) {
						localVector.add(localProductDataVO.billData.get(k));
						paramClientSocket.differTypebillLastTotalAMap.put(str3, Long.valueOf(l));
					} else if (l > ((Long) paramClientSocket.differTypebillLastTotalAMap.get(str3)).longValue()) {
						localVector.add(localProductDataVO.billData.get(k));
						paramClientSocket.differTypebillLastTotalAMap.put(str3, Long.valueOf(l));
					}
				} else if (((BillDataVO) localProductDataVO.billData.get(k)).totalAmount > (paramClientSocket.differTypebillLastTotalAMap
						.get(str3) == null ? 0L : ((Long) paramClientSocket.differTypebillLastTotalAMap.get(str3)).longValue())) {
					localVector.add(localProductDataVO.billData.get(k));
					paramClientSocket.billLastDate = ((BillDataVO) localProductDataVO.billData.get(k)).tradeDate;
					paramClientSocket.billLastTime = ((BillDataVO) localProductDataVO.billData.get(k)).time;
					paramClientSocket.differTypebillLastTotalAMap.put(str3, Long.valueOf(l));
				}
				if (localVector.size() > 0)
					j++;
				if ((j > this.pushNum) && (localVector.size() == this.pushNum))
					break;
			}
			if (localVector.size() > 0) {
				localDataOutputStream.writeByte(7);
				localDataOutputStream.writeUTF(str1);
				localDataOutputStream.writeUTF(str2);
				localDataOutputStream.writeByte(paramClientSocket.type);
				localDataOutputStream.writeLong(paramClientSocket.billLastDate);
				localDataOutputStream.writeLong(paramClientSocket.billLastTime);
				localDataOutputStream.writeUTF(paramClientSocket.strCode);
				localDataOutputStream.writeInt(localVector.size());
				for (int k = 0; k < localVector.size(); k++) {
					BillDataVO localBillDataVO = (BillDataVO) localVector.get(k);
					localDataOutputStream.writeInt(localBillDataVO.tradeDate);
					localDataOutputStream.writeInt(localBillDataVO.time);
					localDataOutputStream.writeInt(localBillDataVO.reserveCount);
					localDataOutputStream.writeFloat(localBillDataVO.buyPrice);
					localDataOutputStream.writeFloat(localBillDataVO.curPrice);
					localDataOutputStream.writeFloat(localBillDataVO.sellPrice);
					localDataOutputStream.writeLong(localBillDataVO.totalAmount);
					localDataOutputStream.writeDouble(localBillDataVO.totalMoney);
					localDataOutputStream.writeInt(localBillDataVO.openAmount);
					localDataOutputStream.writeInt(localBillDataVO.closeAmount);
					localDataOutputStream.writeFloat(localBillDataVO.balancePrice);
					localDataOutputStream.writeUTF(localBillDataVO.obligate);
				}
				i = 0;
			}
			localVector.clear();
		}
		if (i != 0)
			return new byte[0];
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getTradeTimes(String paramString) throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		localDataOutputStream.writeByte(8);
		Object localObject = null;
		if (paramString == null) {
			localObject = this.quotation.getTradeTimesMap();
		} else {
			localObject = new HashMap();
			((Map) localObject).put(paramString, this.quotation.getTradeTimesMap().get(paramString));
		}
		Set localSet = ((Map) localObject).keySet();
		Iterator localIterator = localSet.iterator();
		localDataOutputStream.writeInt(((Map) localObject).size());
		while (localIterator.hasNext()) {
			String str = (String) localIterator.next();
			TradeTimeVO[] arrayOfTradeTimeVO = (TradeTimeVO[]) ((Map) localObject).get(str);
			localDataOutputStream.writeUTF(str);
			localDataOutputStream.writeInt(arrayOfTradeTimeVO.length);
			for (int i = 0; i < arrayOfTradeTimeVO.length; i++) {
				localDataOutputStream.writeInt(arrayOfTradeTimeVO[i].orderID);
				localDataOutputStream.writeInt(arrayOfTradeTimeVO[i].beginDate);
				localDataOutputStream.writeInt(arrayOfTradeTimeVO[i].beginTime);
				localDataOutputStream.writeInt(arrayOfTradeTimeVO[i].endDate);
				localDataOutputStream.writeInt(arrayOfTradeTimeVO[i].endTime);
				localDataOutputStream.writeInt(arrayOfTradeTimeVO[i].tradeDate);
				localDataOutputStream.writeInt(arrayOfTradeTimeVO[i].status);
			}
		}
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	public long getTradeTime() {
		Map localMap = this.quotation.getTradeTimesMap();
		Set localSet = localMap.keySet();
		Iterator localIterator = localSet.iterator();
		long l1 = 0L;
		while (localIterator.hasNext()) {
			String str = (String) localIterator.next();
			TradeTimeVO[] arrayOfTradeTimeVO = (TradeTimeVO[]) localMap.get(str);
			for (int i = 0; i < arrayOfTradeTimeVO.length; i++) {
				long l2 = arrayOfTradeTimeVO[i].beginTime + arrayOfTradeTimeVO[i].endTime;
				l1 += l2;
			}
		}
		return l1;
	}

	public byte[] GetQuoteList(ClientSocket paramClientSocket) throws IOException {
		ProductDataVO[] arrayOfProductDataVO = this.quotation.getModifiedProductData(paramClientSocket);
		if (arrayOfProductDataVO.length == 0)
			return new byte[0];
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		localDataOutputStream.writeByte(5);
		localDataOutputStream.writeInt(arrayOfProductDataVO.length);
		for (int i = 0; i < arrayOfProductDataVO.length; i++) {
			localDataOutputStream.writeUTF(arrayOfProductDataVO[i].marketID);
			localDataOutputStream.writeUTF(arrayOfProductDataVO[i].code);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].yesterBalancePrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].closePrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].openPrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].highPrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].lowPrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].curPrice);
			localDataOutputStream.writeLong(arrayOfProductDataVO[i].totalAmount);
			localDataOutputStream.writeDouble(arrayOfProductDataVO[i].totalMoney);
			localDataOutputStream.writeInt(arrayOfProductDataVO[i].curAmount);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].consignRate);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].amountRate);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].balancePrice);
			localDataOutputStream.writeInt(arrayOfProductDataVO[i].reserveCount);
			localDataOutputStream.writeInt(arrayOfProductDataVO[i].buyAmount[0]);
			localDataOutputStream.writeInt(arrayOfProductDataVO[i].sellAmount[0]);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].buyPrice[0]);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].sellPrice[0]);
			localDataOutputStream.writeUTF(arrayOfProductDataVO[i].expStr);
			if (arrayOfProductDataVO[i].lUpdateTime > paramClientSocket.quoteListTime)
				paramClientSocket.quoteListTime = arrayOfProductDataVO[i].lUpdateTime;
		}
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getQuoteDetail(ClientSocket paramClientSocket, String paramString) throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		int i = paramClientSocket.type;
		int j = paramClientSocket.billLastTime;
		String str = new StringBuilder().append(paramClientSocket.strMarket).append(paramClientSocket.strCode).toString();
		long l = paramClientSocket.billLastTotalAMap.get(str) == null ? 0L : ((Long) paramClientSocket.billLastTotalAMap.get(str)).longValue();
		ProductDataVO localProductDataVO = this.quotation.getProductData(str);
		if (localProductDataVO == null)
			return new byte[0];
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		localDataOutputStream.writeByte(7);
		localDataOutputStream.writeUTF(paramClientSocket.strMarket);
		localDataOutputStream.writeUTF(paramClientSocket.strCode);
		localDataOutputStream.writeInt(paramClientSocket.billLastTime);
		Vector localVector = new Vector();
		if (i == 0) {
			for (int k = 0; k < localProductDataVO.billData.size(); k++)
				if (((BillDataVO) localProductDataVO.billData.get(k)).totalAmount > l)
					localVector.add(localProductDataVO.billData.get(k));
		} else {
			int k = localProductDataVO.billData.size() - j;
			if (k < 0)
				;
			for (k = 0; k < localProductDataVO.billData.size(); k++)
				localVector.add(localProductDataVO.billData.get(k));
		}
		localDataOutputStream.writeInt(localVector.size());
		for (int k = 0; k < localVector.size(); k++) {
			BillDataVO localBillDataVO = (BillDataVO) localVector.get(k);
			localDataOutputStream.writeInt(localBillDataVO.time);
			localDataOutputStream.writeInt(localBillDataVO.reserveCount);
			localDataOutputStream.writeFloat(localBillDataVO.buyPrice);
			localDataOutputStream.writeFloat(localBillDataVO.curPrice);
			localDataOutputStream.writeFloat(localBillDataVO.sellPrice);
			localDataOutputStream.writeLong(localBillDataVO.totalAmount);
			localDataOutputStream.writeDouble(localBillDataVO.totalMoney);
			localDataOutputStream.writeInt(localBillDataVO.openAmount);
			localDataOutputStream.writeInt(localBillDataVO.closeAmount);
			localDataOutputStream.writeFloat(localBillDataVO.balancePrice);
			if (localBillDataVO.time > paramClientSocket.billLastTime)
				paramClientSocket.billLastTime = localBillDataVO.time;
			if (localBillDataVO.totalAmount > (paramClientSocket.billLastTotalAMap.get(paramString) == null ? 0L
					: ((Long) paramClientSocket.billLastTotalAMap.get(paramString)).longValue()))
				paramClientSocket.billLastTotalAMap.put(paramString, Long.valueOf(localBillDataVO.totalAmount));
		}
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getSortData(byte paramByte1, byte paramByte2, int paramInt1, int paramInt2) throws IOException {
		if ((paramInt1 < 1) || (paramInt2 < paramInt1) || (paramByte1 < 0) || (paramByte1 > 10)) {
			this.log.debug(new StringBuilder().append("error 1 ").append(new Date()).append(" sortBy:").append(paramByte1).append(" isDescend:")
					.append(paramByte2).append(" start:").append(paramInt1).append(" end:").append(paramInt2).toString());
			return new byte[0];
		}
		if (this.quotation.GetSortList(paramByte1) == null) {
			this.log.debug(new StringBuilder().append("error 2 ").append(new Date()).append(" sortBy:").append(paramByte1).append(" isDescend:")
					.append(paramByte2).append(" start:").append(paramInt1).append(" end:").append(paramInt2).toString());
			return new byte[0];
		}
		String[] localObject1 = this.quotation.GetSortList(paramByte1).codeList;
		int i = localObject1.length;
		if (localObject1 == null) {
			this.log.debug(new StringBuilder().append("error 3 ").append(new Date()).append(" sortBy:").append(paramByte1).append(" isDescend:")
					.append(paramByte2).append(" start:").append(paramInt1).append(" end:").append(paramInt2).toString());
			return new byte[0];
		}
		if (paramByte2 == 1) {
			String[] localObject2 = new String[localObject1.length];
			for (int j = 0; j < localObject1.length; j++)
				localObject2[j] = localObject1[(localObject1.length - 1 - j)];
			localObject1 = localObject2;
		}
		Object localObject2 = new Vector();
		for (int j = paramInt1 - 1; (j < localObject1.length) && (j >= paramInt1 - 1) && (j < paramInt2); j++)
			((Vector) localObject2).add(localObject1[j]);
		ProductDataVO[] arrayOfProductDataVO = this.quotation
				.getProductData((String[]) ((Vector) localObject2).toArray(new String[((Vector) localObject2).size()]));
		if (arrayOfProductDataVO == null) {
			this.log.debug(new StringBuilder().append("error 4 ").append(new Date()).append(" sortBy:").append(paramByte1).append(" isDescend:")
					.append(paramByte2).append(" start:").append(paramInt1).append("end").append(paramInt2).toString());
			return new byte[0];
		}
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		localDataOutputStream.writeByte(5);
		localDataOutputStream.writeByte(paramByte1);
		localDataOutputStream.writeByte(paramByte2);
		localDataOutputStream.writeInt(i);
		localDataOutputStream.writeInt(paramInt1);
		localDataOutputStream.writeInt(arrayOfProductDataVO.length);
		for (int k = 0; k < ((Vector) localObject2).size(); k++) {
			localDataOutputStream.writeUTF(arrayOfProductDataVO[k].code);
			localDataOutputStream.writeUTF(arrayOfProductDataVO[k].marketID);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[k].yesterBalancePrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[k].closePrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[k].openPrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[k].highPrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[k].lowPrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[k].curPrice);
			localDataOutputStream.writeLong(arrayOfProductDataVO[k].totalAmount);
			localDataOutputStream.writeDouble(arrayOfProductDataVO[k].totalMoney);
			localDataOutputStream.writeInt(arrayOfProductDataVO[k].curAmount);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[k].amountRate);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[k].balancePrice);
			localDataOutputStream.writeInt(arrayOfProductDataVO[k].reserveCount);
			localDataOutputStream.writeInt(arrayOfProductDataVO[k].buyAmount[0]);
			localDataOutputStream.writeInt(arrayOfProductDataVO[k].sellAmount[0]);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[k].buyPrice[0]);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[k].sellPrice[0]);
		}
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getCurStatusByCodes(ClientSocket paramClientSocket) throws IOException {
		Vector localVector = new Vector();
		if (paramClientSocket.codeList == null) {
			this.log.debug("codeList is NULL");
			return new byte[0];
		}
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		Set localSet = paramClientSocket.codeList.entrySet();
		Iterator localIterator = localSet.iterator();
		int i = 1;
		while (localIterator.hasNext()) {
			Map.Entry localObject1 = (Map.Entry) localIterator.next();
			String str1 = ((String[]) localObject1.getValue())[2];
			String localObject2 = ((String[]) localObject1.getValue())[0];
			String str2 = ((String[]) localObject1.getValue())[1];
			long l1 = Long.parseLong(new StringBuilder()
					.append((str2 == null) || ("".equals(str2)) ? Integer.valueOf(paramClientSocket.minLastTime) : str2).append("").toString());
			String str3 = new StringBuilder().append(str1).append(localObject2).toString();
			ProductDataVO localProductDataVO = this.quotation.getProductData(str3);
			if (localProductDataVO == null)
				return new byte[0];
			long l2 = localProductDataVO.lUpdateTime;
			if (l2 > l1) {
				((String[]) ((Map.Entry) localObject1).getValue())[1] = String.valueOf(l2);
				localVector.add(localProductDataVO);
				i = 0;
			}
		}
		if (i != 0)
			return new byte[0];
		localDataOutputStream.writeByte(4);
		localDataOutputStream.writeByte(paramClientSocket.isAll);
		localDataOutputStream.writeInt(localVector.size());
		Object localObject1 = Calendar.getInstance();
		for (int j = 0; j < localVector.size(); j++) {
			ProductDataVO localObject2 = (ProductDataVO) localVector.get(j);
			localDataOutputStream.writeUTF(((ProductDataVO) localObject2).marketID);
			localDataOutputStream.writeUTF(((ProductDataVO) localObject2).code);
			((Calendar) localObject1).setTime(((ProductDataVO) localObject2).time);
			localDataOutputStream.writeInt(
					((Calendar) localObject1).get(1) * 10000 + (((Calendar) localObject1).get(2) + 1) * 100 + ((Calendar) localObject1).get(5));
			localDataOutputStream.writeInt(
					((Calendar) localObject1).get(11) * 10000 + ((Calendar) localObject1).get(12) * 100 + ((Calendar) localObject1).get(13));
			localDataOutputStream.writeFloat(((ProductDataVO) localObject2).closePrice);
			localDataOutputStream.writeFloat(((ProductDataVO) localObject2).openPrice);
			localDataOutputStream.writeFloat(((ProductDataVO) localObject2).highPrice);
			localDataOutputStream.writeFloat(((ProductDataVO) localObject2).lowPrice);
			localDataOutputStream.writeFloat(((ProductDataVO) localObject2).curPrice);
			localDataOutputStream.writeLong(((ProductDataVO) localObject2).totalAmount);
			localDataOutputStream.writeDouble(((ProductDataVO) localObject2).totalMoney);
			localDataOutputStream.writeInt(((ProductDataVO) localObject2).curAmount);
			localDataOutputStream.writeFloat(((ProductDataVO) localObject2).consignRate);
			localDataOutputStream.writeFloat(((ProductDataVO) localObject2).amountRate);
			localDataOutputStream.writeFloat(((ProductDataVO) localObject2).balancePrice);
			localDataOutputStream.writeInt(((ProductDataVO) localObject2).reserveCount);
			localDataOutputStream.writeFloat(((ProductDataVO) localObject2).yesterBalancePrice);
			localDataOutputStream.writeInt(((ProductDataVO) localObject2).reserveChange);
			if (paramClientSocket.isAll == 1) {
				localDataOutputStream.writeInt(((ProductDataVO) localObject2).inAmount);
				localDataOutputStream.writeInt(((ProductDataVO) localObject2).outAmount);
				localDataOutputStream.writeInt(((ProductDataVO) localObject2).buyAmount.length);
				for (int k = 0; k < ((ProductDataVO) localObject2).buyAmount.length; k++)
					localDataOutputStream.writeInt(localObject2.buyAmount[k]);
				localDataOutputStream.writeInt(((ProductDataVO) localObject2).sellAmount.length);
				for (int k = 0; k < ((ProductDataVO) localObject2).sellAmount.length; k++)
					localDataOutputStream.writeInt(localObject2.sellAmount[k]);
				localDataOutputStream.writeInt(((ProductDataVO) localObject2).buyPrice.length);
				for (int k = 0; k < ((ProductDataVO) localObject2).buyPrice.length; k++)
					localDataOutputStream.writeFloat(localObject2.buyPrice[k]);
				localDataOutputStream.writeInt(((ProductDataVO) localObject2).sellPrice.length);
				for (int k = 0; k < ((ProductDataVO) localObject2).sellPrice.length; k++)
					localDataOutputStream.writeFloat(localObject2.sellPrice[k]);
			}
			localDataOutputStream.writeUTF(((ProductDataVO) localObject2).expStr);
		}
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getCurStatusByCodes(Map paramMap, byte paramByte) throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		Vector localVector = new Vector();
		for (int i = 0; i < paramMap.size(); i++) {
			long l = Long.parseLong(((String[]) (String[]) paramMap.get(Integer.valueOf(i)))[1]);
			String str = new StringBuilder().append(((String[]) (String[]) paramMap.get(Integer.valueOf(i)))[2])
					.append(((String[]) (String[]) paramMap.get(Integer.valueOf(i)))[0]).toString();
			ProductDataVO localProductDataVO2 = this.quotation.getProductData(str);
			if ((localProductDataVO2 != null) && (localProductDataVO2.lUpdateTime > l))
				localVector.add(localProductDataVO2);
		}
		localDataOutputStream.writeByte(4);
		localDataOutputStream.writeByte(paramByte);
		localDataOutputStream.writeInt(localVector.size());
		Calendar localCalendar = Calendar.getInstance();
		for (int j = 0; j < localVector.size(); j++) {
			ProductDataVO localProductDataVO1 = (ProductDataVO) localVector.get(j);
			localDataOutputStream.writeUTF(localProductDataVO1.marketID);
			localDataOutputStream.writeUTF(localProductDataVO1.code);
			localCalendar.setTime(localProductDataVO1.time);
			localDataOutputStream.writeInt(localCalendar.get(1) * 10000 + (localCalendar.get(2) + 1) * 100 + localCalendar.get(5));
			localDataOutputStream.writeInt(localCalendar.get(11) * 10000 + localCalendar.get(12) * 100 + localCalendar.get(13));
			localDataOutputStream.writeFloat(localProductDataVO1.closePrice);
			localDataOutputStream.writeFloat(localProductDataVO1.openPrice);
			localDataOutputStream.writeFloat(localProductDataVO1.highPrice);
			localDataOutputStream.writeFloat(localProductDataVO1.lowPrice);
			localDataOutputStream.writeFloat(localProductDataVO1.curPrice);
			localDataOutputStream.writeLong(localProductDataVO1.totalAmount);
			localDataOutputStream.writeDouble(localProductDataVO1.totalMoney);
			localDataOutputStream.writeInt(localProductDataVO1.curAmount);
			localDataOutputStream.writeFloat(localProductDataVO1.consignRate);
			localDataOutputStream.writeFloat(localProductDataVO1.amountRate);
			localDataOutputStream.writeFloat(localProductDataVO1.balancePrice);
			localDataOutputStream.writeInt(localProductDataVO1.reserveCount);
			localDataOutputStream.writeFloat(localProductDataVO1.yesterBalancePrice);
			localDataOutputStream.writeInt(localProductDataVO1.reserveChange);
			if (paramByte == 1) {
				localDataOutputStream.writeInt(localProductDataVO1.inAmount);
				localDataOutputStream.writeInt(localProductDataVO1.outAmount);
				localDataOutputStream.writeInt(localProductDataVO1.buyAmount.length);
				for (int k = 0; k < localProductDataVO1.buyAmount.length; k++)
					localDataOutputStream.writeInt(localProductDataVO1.buyAmount[k]);
				localDataOutputStream.writeInt(localProductDataVO1.sellAmount.length);
				for (int k = 0; k < localProductDataVO1.sellAmount.length; k++)
					localDataOutputStream.writeInt(localProductDataVO1.sellAmount[k]);
				localDataOutputStream.writeInt(localProductDataVO1.buyPrice.length);
				for (int k = 0; k < localProductDataVO1.buyPrice.length; k++)
					localDataOutputStream.writeFloat(localProductDataVO1.buyPrice[k]);
				localDataOutputStream.writeInt(localProductDataVO1.sellPrice.length);
				for (int k = 0; k < localProductDataVO1.sellPrice.length; k++)
					localDataOutputStream.writeFloat(localProductDataVO1.sellPrice[k]);
			}
		}
		localDataOutputStream.writeUTF("");
		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.close();
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getAddrDictionary() throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		try {
			AddrDictionary localAddrDictionary = this.dictionaryServer.getAddrDictionary(null);
			localDataOutputStream.writeByte(16);
			Map localObject1 = localAddrDictionary.getAddrDic();
			localDataOutputStream.writeInt(((Map) localObject1).size());
			Set localSet = ((Map) localObject1).entrySet();
			Iterator localIterator = localSet.iterator();
			while (localIterator.hasNext()) {
				Map.Entry localEntry = (Map.Entry) localIterator.next();
				AddrVO localAddrVO = (AddrVO) localEntry.getValue();
				localDataOutputStream.writeUTF(localAddrVO.getAddrCode());
				localDataOutputStream.writeUTF(localAddrVO.getAddrName());
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			return new byte[0];
		} finally {
			localDataOutputStream.flush();
			localDataOutputStream.close();
			localByteArrayOutputStream.close();
		}
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getIndustryDictionary() throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		try {
			IndustryDictionary localIndustryDictionary = this.dictionaryServer.getIndustryDictionary(null);
			localDataOutputStream.writeByte(15);
			Map localObject1 = localIndustryDictionary.getIndustryDic();
			localDataOutputStream.writeInt(localObject1.size());
			Set localSet = localObject1.entrySet();
			Iterator localIterator = localSet.iterator();
			while (localIterator.hasNext()) {
				Map.Entry localEntry = (Map.Entry) localIterator.next();
				IndustryVO localIndustryVO = (IndustryVO) localEntry.getValue();
				localDataOutputStream.writeUTF(localIndustryVO.getIndustryCode());
				localDataOutputStream.writeUTF(localIndustryVO.getIndustryName());
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			return new byte[0];
		} finally {
			localDataOutputStream.flush();
			localDataOutputStream.close();
			localByteArrayOutputStream.close();
		}
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] getSystemMessage(ClientSocket paramClientSocket) throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		try {
			localDataOutputStream.writeByte(18);
		} catch (Exception localException) {
		} finally {
			localDataOutputStream.flush();
			localDataOutputStream.close();
			localByteArrayOutputStream.close();
		}
		return localByteArrayOutputStream.toByteArray();
	}

	public byte[] logon() throws IOException {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		try {
			localDataOutputStream.writeByte(1);
		} catch (Exception localException) {
		} finally {
			localDataOutputStream.flush();
			localDataOutputStream.close();
			localByteArrayOutputStream.close();
		}
		return localByteArrayOutputStream.toByteArray();
	}
}