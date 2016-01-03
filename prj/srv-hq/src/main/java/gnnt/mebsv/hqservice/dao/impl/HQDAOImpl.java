package gnnt.mebsv.hqservice.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.mebsv.hqservice.dao.HQDAO;
import gnnt.mebsv.hqservice.model.BillDataVO;
import gnnt.mebsv.hqservice.model.DayDataVO;
import gnnt.mebsv.hqservice.model.MarketInfoVO;
import gnnt.mebsv.hqservice.model.ProductDataVO;
import gnnt.mebsv.hqservice.model.ProductInfoVO;
import gnnt.mebsv.hqservice.model.TradeTimeVO;
import gnnt.mebsv.hqservice.model.dictionary.AddrDictionary;
import gnnt.mebsv.hqservice.model.dictionary.IndustryDictionary;
import gnnt.mebsv.hqservice.tools.Util;

public class HQDAOImpl extends HQDAO {
	private Log log = LogFactory.getLog(HQDAOImpl.class);

	public HashMap queryTradeTimes() throws SQLException {
		HashMap localHashMap1 = new HashMap();
		HashMap localHashMap2 = new HashMap();
		ArrayList localArrayList1 = null;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		try {
			this.conn = getConn();
			localPreparedStatement = this.conn.prepareStatement("SELECT * FROM TradeTime WHERE Status = 1 ORDER BY TradeSection");
			localResultSet = localPreparedStatement.executeQuery();
			Object localObject1;
			while (localResultSet.next()) {
				TradeTimeVO localTradeTimeVO = new TradeTimeVO();
				localTradeTimeVO.marektID = localResultSet.getString("MarketID");
				localTradeTimeVO.orderID = localResultSet.getInt("TradeSection");
				localArrayList1 = (ArrayList) localHashMap1.get(localTradeTimeVO.marektID);
				if (localArrayList1 == null) {
					localArrayList1 = new ArrayList();
				}
				localTradeTimeVO.beginDate = Integer
						.parseInt(localResultSet.getString("BeginDate") == null ? "0" : localResultSet.getString("BeginDate"));
				localTradeTimeVO.beginTime = (Integer.parseInt(localResultSet.getString("BeginTime")) / 100);
				localTradeTimeVO.endDate = Integer.parseInt(localResultSet.getString("EndDate") == null ? "0" : localResultSet.getString("EndDate"));
				localTradeTimeVO.endTime = (Integer.parseInt(localResultSet.getString("EndTime")) / 100);
				localObject1 = localResultSet.getString("tradedate");
				localTradeTimeVO.tradeDate = Integer.parseInt((String) localObject1);
				localTradeTimeVO.status = localResultSet.getInt("Status");
				localTradeTimeVO.modifytime = localResultSet.getTimestamp("Modifytime");
				localArrayList1.add(localTradeTimeVO);
				localHashMap1.put(localTradeTimeVO.marektID, localArrayList1);
			}
			if (localHashMap1.size() > 0) {
				localObject1 = localHashMap1.keySet();
				Iterator localIterator = ((Set) localObject1).iterator();
				localHashMap2 = new HashMap();
				while (localIterator.hasNext()) {
					String str = (String) localIterator.next();
					ArrayList localArrayList2 = (ArrayList) localHashMap1.get(str);
					if (localArrayList2.size() > 0) {
						localHashMap2.put(str, (TradeTimeVO[]) localArrayList2.toArray(new TradeTimeVO[localArrayList2.size()]));
					}
				}
			}
		} finally {
			closeStatement(localResultSet, localPreparedStatement, this.conn);
		}
		return localHashMap2;
	}

	public void insertTradeTime(TradeTimeVO paramTradeTimeVO) throws SQLException {
		PreparedStatement localPreparedStatement = null;
		try {
			this.conn = getConn();
			localPreparedStatement = this.conn.prepareStatement("insert into TradeTime values(?,?,?,?,?)");
			localPreparedStatement.setInt(1, paramTradeTimeVO.orderID);
			localPreparedStatement.setString(2, TradeTimeVO.timeIntToString(paramTradeTimeVO.beginTime));
			localPreparedStatement.setString(3, TradeTimeVO.timeIntToString(paramTradeTimeVO.endTime));
			localPreparedStatement.setInt(4, paramTradeTimeVO.status);
			localPreparedStatement.setTimestamp(5, new Timestamp(paramTradeTimeVO.modifytime.getTime()));
			localPreparedStatement.executeUpdate();
		} finally {
			closeStatement(null, localPreparedStatement, this.conn);
		}
	}

	public void updateTradeTime(int paramInt, TradeTimeVO paramTradeTimeVO) throws SQLException {
		PreparedStatement localPreparedStatement = null;
		try {
			this.conn = getConn();
			localPreparedStatement = this.conn
					.prepareStatement("update TradeTime set OrderID=?,BeginTime=?,EndTime=?,Status=?,Modifytime=? where OrderID=?");
			localPreparedStatement.setInt(1, paramTradeTimeVO.orderID);
			localPreparedStatement.setString(2, TradeTimeVO.timeIntToString(paramTradeTimeVO.beginTime));
			localPreparedStatement.setString(3, TradeTimeVO.timeIntToString(paramTradeTimeVO.endTime));
			localPreparedStatement.setInt(4, paramTradeTimeVO.status);
			localPreparedStatement.setTimestamp(5, new Timestamp(paramTradeTimeVO.modifytime.getTime()));
			localPreparedStatement.setInt(6, paramInt);
			localPreparedStatement.executeUpdate();
		} finally {
			closeStatement(null, localPreparedStatement, this.conn);
		}
	}

	public void delTradeTime(int paramInt) throws SQLException {
		PreparedStatement localPreparedStatement = null;
		try {
			this.conn = getConn();
			localPreparedStatement = this.conn.prepareStatement("delete from TradeTime where OrderID=?");
			localPreparedStatement.setInt(1, paramInt);
			localPreparedStatement.executeUpdate();
		} finally {
			closeStatement(null, localPreparedStatement, this.conn);
		}
	}

	public ProductInfoVO[] queryProductInfo(Date paramDate) throws SQLException {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		Vector localVector = new Vector();
		try {
			this.conn = getConn();
			localPreparedStatement = this.conn.prepareStatement("SELECT * From Commodity WHERE ModifyTime > ?");
			localPreparedStatement.setTimestamp(1, new Timestamp(paramDate.getTime()));
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				ProductInfoVO localProductInfoVO = new ProductInfoVO();
				localProductInfoVO.marketID = localResultSet.getString("MarketID");
				localProductInfoVO.code = localResultSet.getString("CommodityID");
				localProductInfoVO.name = Util.convertDBStr(localResultSet.getString("FullName"));
				localProductInfoVO.type = localResultSet.getInt("Type");
				localProductInfoVO.spread = localResultSet.getFloat("spread");
				localProductInfoVO.addrCode = localResultSet.getString("AddrCode");
				localProductInfoVO.industryCode = localResultSet.getString("IndustryCode");
				String str1 = localResultSet.getString("listdate");
				localProductInfoVO.openTime = Integer.parseInt((str1 != null) && (!"null".equals(str1)) ? str1 : "00000000");
				String str2 = localResultSet.getString("delistdate");
				localProductInfoVO.closeTime = Integer.parseInt(str2 == null ? "99999999" : str2);
				localProductInfoVO.fUnit = localResultSet.getFloat("ctrtsize");
				String str3 = localResultSet.getString("tradingsec");
				if (str3 != null) {
					System.out.println("code: " + localProductInfoVO.code + "strSec: " + str3);
					String[] localObject1 = str3.split(",");
					localProductInfoVO.tradeSecNo = new int[localObject1.length];
					for (int i = 0; i < localObject1.length; i++) {
						localProductInfoVO.tradeSecNo[i] = Integer.parseInt(localObject1[i]);
					}
				}
				localProductInfoVO.modifyTime = localResultSet.getTimestamp("ModifyTime");
				Object localObject1 = localResultSet.getString("Status");
				if (((String) localObject1).equals("N")) {
					if (localProductInfoVO.spread < 1.0F) {
						localProductInfoVO.status = 10;
					} else {
						localProductInfoVO.status = 0;
					}
				} else if (((String) localObject1).equals("S")) {
					localProductInfoVO.status = 5;
				} else if (((String) localObject1).equals("T")) {
					localProductInfoVO.status = 6;
				} else if (((String) localObject1).equals("D")) {
					localProductInfoVO.status = 1;
				} else {
					localProductInfoVO.status = -1;
				}
				String str4 = localResultSet.getString("REMARKS");
				if ((str4 != null) && (str4.equalsIgnoreCase("index"))) {
					localProductInfoVO.status = 2;
				}
				localVector.add(localProductInfoVO);
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			closeStatement(localResultSet, localPreparedStatement, this.conn);
		}
		return (ProductInfoVO[]) localVector.toArray(new ProductInfoVO[localVector.size()]);
	}

	public int getAllCurData(Hashtable paramHashtable, String paramString1, String paramString2, int paramInt) throws SQLException {
		int i = 0;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		try {
			this.conn = getConn();
			localPreparedStatement = this.conn.prepareStatement("SELECT * FROM CurrentData WHERE CommodityID <> 'SYS'");
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				String str1 = localResultSet.getString("CommodityID");
				Timestamp localTimestamp = localResultSet.getTimestamp("Time");
				String str2 = localResultSet.getString("MarketID");
				String str3 = str2 + str1;
				ProductDataVO localProductDataVO1 = (ProductDataVO) paramHashtable.get(str3);
				ProductDataVO localProductDataVO2 = null;
				if (localProductDataVO1 == null) {
					Date localDate = new Date(System.currentTimeMillis());
					System.out.println(localDate.toLocaleString() + " Init Data " + str1 + "..." + "UniCode: " + str3 + ".....");
					localProductDataVO1 = new ProductDataVO();
					localProductDataVO1.code = str1;
					localProductDataVO1.marketID = str2;
					localProductDataVO1.name = getProductName(str1);
					localProductDataVO1.averAmount5 = getAverAmount5(str1);
					getBillData(str1, localProductDataVO1.billData, localTimestamp, str2);
					paramHashtable.put(str3, localProductDataVO1);
				}
				if (localProductDataVO1 != null) {
					localProductDataVO2 = (ProductDataVO) localProductDataVO1.clone();
				}
				if (i != 2) {
					i = 1;
				}
				localProductDataVO1.time = localTimestamp;
				localProductDataVO1.yesterBalancePrice = localResultSet.getFloat("YesterBalancePrice");
				localProductDataVO1.closePrice = localResultSet.getFloat("ClosePrice");
				localProductDataVO1.openPrice = localResultSet.getFloat("OpenPrice");
				if (localProductDataVO1.closePrice == 0.0F) {
					localProductDataVO1.closePrice = localProductDataVO1.openPrice;
				}
				localProductDataVO1.highPrice = localResultSet.getFloat("HighPrice");
				localProductDataVO1.lowPrice = localResultSet.getFloat("LowPrice");
				localProductDataVO1.curPrice = localResultSet.getFloat("CurPrice");
				localProductDataVO1.curAmount = localResultSet.getInt("CurAmount");
				localProductDataVO1.openAmount = localResultSet.getInt("OpenAmount");
				localProductDataVO1.closeAmount = localResultSet.getInt("CloseAmount");
				localProductDataVO1.reserveCount = localResultSet.getInt("ReserveCount");
				localProductDataVO1.reserveChange = localResultSet.getInt("ReserveChange");
				localProductDataVO1.balancePrice = localResultSet.getFloat("BalancePrice");
				localProductDataVO1.totalMoney = localResultSet.getFloat("TotalMoney");
				localProductDataVO1.totalAmount = localResultSet.getInt("TotalAmount");
				for (int j = 0; j < paramInt; j++) {
					localProductDataVO1.buyPrice[j] = localResultSet.getFloat("BuyPrice" + (j + 1));
					localProductDataVO1.sellPrice[j] = localResultSet.getFloat("SellPrice" + (j + 1));
					localProductDataVO1.buyAmount[j] = localResultSet.getInt("BuyAmount" + (j + 1));
					localProductDataVO1.sellAmount[j] = localResultSet.getInt("SellAmount" + (j + 1));
				}
				localProductDataVO1.outAmount = localResultSet.getInt("OutAmount");
				localProductDataVO1.inAmount = localResultSet.getInt("InAmount");
				localProductDataVO1.tradeCue = localResultSet.getInt("TradeCue");
				localProductDataVO1.no = localResultSet.getInt("NO");
				long l = 0L;
				BillDataVO localBillDataVO;
				if ((localProductDataVO2 != null) && (localProductDataVO2.billData != null) && (localProductDataVO2.billData.size() > 0)) {
					localBillDataVO = (BillDataVO) localProductDataVO2.billData.lastElement();
					l = localBillDataVO.totalAmount;
				}
				if ((localProductDataVO2 == null) || (l < localProductDataVO1.totalAmount)) {
					Vector localVector = getLastBillData(str1, str2, null, l);
					for (int k = 0; k < localVector.size(); k++) {
						localProductDataVO1.billData.add(localVector.elementAt(k));
					}
				}
				if ((localProductDataVO2 == null) || (!localProductDataVO2.equals(localProductDataVO1))) {
					localProductDataVO1.bUpdated = true;
					localProductDataVO1.lUpdateTime = System.currentTimeMillis();
				}
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			closeStatement(localResultSet, localPreparedStatement, null);
		}
		return i;
	}

	String getProductName(String paramString) {
		String str = "";
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		try {
			this.conn = getConn();
			localPreparedStatement = this.conn.prepareStatement("SELECT FullName FROM Commodity");
			localResultSet = localPreparedStatement.executeQuery();
			if (localResultSet.next()) {
				str = localResultSet.getString("FullName");
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
		} finally {
			closeStatement(localResultSet, localPreparedStatement, null);
		}
		return str;
	}

	protected long getAverAmount5(String paramString) throws SQLException {
		long l = 0L;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		try {
			this.conn = getConn();
			String str = "SELECT MIN(TradeDate) FROM (SELECT TOP 5 TradeDate FROM HistoryDayData WHERE CommodityID = ? ORDER BY TradeDate DESC)";
			localPreparedStatement = this.conn.prepareStatement(str);
			localPreparedStatement.setString(1, paramString);
			localResultSet = localPreparedStatement.executeQuery();
			Timestamp localTimestamp = null;
			if (localResultSet.next()) {
				localTimestamp = localResultSet.getTimestamp(1);
			}
			localResultSet.close();
			localPreparedStatement.close();
			str = "SELECT AVG(TotalAmount) FROM HistoryDayData WHERE CommodityID = ? AND TradeDate >= ?";
			localPreparedStatement = this.conn.prepareStatement(str);
			localPreparedStatement.setString(1, paramString);
			localPreparedStatement.setTimestamp(2, localTimestamp);
			localResultSet = localPreparedStatement.executeQuery();
			if (localResultSet.next()) {
				l = localResultSet.getLong(1);
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
		} finally {
			closeStatement(localResultSet, localPreparedStatement, null);
		}
		return l;
	}

	private void getBillData(String paramString1, Vector paramVector, Date paramDate, String paramString2) {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		try {
			this.conn = getConn();
			String str = "SELECT Time,CurPrice,ReserveCount,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,OpenAmount,CloseAmount,TradeCue,BalancePrice FROM TodayBillData WHERE CommodityID = ?  AND MARKETID = ? ORDER BY Time,TotalAmount";
			localPreparedStatement = this.conn.prepareStatement(str);
			localPreparedStatement.setString(1, paramString1);
			localPreparedStatement.setString(2, paramString2);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				BillDataVO localBillDataVO = new BillDataVO();
				Timestamp localTimestamp = localResultSet.getTimestamp(1);
				localBillDataVO.commodityID = paramString1;
				localBillDataVO.marketID = paramString2;
				localBillDataVO.tradeDate = ((localTimestamp.getYear() + 1900) * 10000 + (localTimestamp.getMonth() + 1) * 100
						+ localTimestamp.getDate());
				localBillDataVO.time = (localTimestamp.getHours() * 10000 + localTimestamp.getMinutes() * 100 + localTimestamp.getSeconds());
				localBillDataVO.curPrice = localResultSet.getFloat(2);
				localBillDataVO.reserveCount = localResultSet.getInt(3);
				localBillDataVO.totalMoney = localResultSet.getFloat(4);
				localBillDataVO.totalAmount = localResultSet.getInt(5);
				localBillDataVO.buyPrice = localResultSet.getFloat(6);
				localBillDataVO.sellPrice = localResultSet.getFloat(7);
				localBillDataVO.openAmount = localResultSet.getInt(8);
				localBillDataVO.closeAmount = localResultSet.getInt(9);
				localBillDataVO.tradeCue = localResultSet.getInt(10);
				localBillDataVO.balancePrice = localResultSet.getFloat(11);
				paramVector.add(localBillDataVO);
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
		} finally {
			closeStatement(localResultSet, localPreparedStatement, null);
		}
	}

	private Vector getLastBillData(String paramString1, String paramString2, Timestamp paramTimestamp, long paramLong) {
		Vector localVector = new Vector();
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		try {
			this.conn = getConn();
			StringBuffer localStringBuffer = new StringBuffer();
			paramTimestamp = null;
			localStringBuffer
					.append("SELECT Time,CurPrice,ReserveCount,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,OpenAmount,CloseAmount,TradeCue,BalancePrice FROM TodayBillData ")
					.append(" WHERE CommodityID=? AND marketID = ?  AND TotalAmount>? ");
			if (paramTimestamp != null) {
				localStringBuffer.append(" AND Time>=?  ");
			}
			localStringBuffer.append(" ORDER BY Time,TotalAmount ");
			localPreparedStatement = this.conn.prepareStatement(localStringBuffer.toString());
			localPreparedStatement.setString(1, paramString1);
			localPreparedStatement.setString(2, paramString2);
			if (paramTimestamp != null) {
				localPreparedStatement.setLong(3, paramLong);
				localPreparedStatement.setTimestamp(4, paramTimestamp);
			} else {
				localPreparedStatement.setLong(3, paramLong);
			}
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				BillDataVO localBillDataVO = new BillDataVO();
				localBillDataVO.commodityID = paramString1;
				localBillDataVO.marketID = paramString2;
				Timestamp localTimestamp = localResultSet.getTimestamp(1);
				localBillDataVO.tradeDate = ((localTimestamp.getYear() + 1900) * 10000 + (localTimestamp.getMonth() + 1) * 100
						+ localTimestamp.getDate());
				localBillDataVO.time = (localTimestamp.getHours() * 10000 + localTimestamp.getMinutes() * 100 + localTimestamp.getSeconds());
				localBillDataVO.curPrice = localResultSet.getFloat(2);
				localBillDataVO.reserveCount = localResultSet.getInt(3);
				localBillDataVO.totalMoney = localResultSet.getFloat(4);
				localBillDataVO.totalAmount = localResultSet.getInt(5);
				localBillDataVO.buyPrice = localResultSet.getFloat(6);
				localBillDataVO.sellPrice = localResultSet.getFloat(7);
				localBillDataVO.openAmount = localResultSet.getInt(8);
				localBillDataVO.closeAmount = localResultSet.getInt(9);
				localBillDataVO.tradeCue = localResultSet.getInt(10);
				localBillDataVO.balancePrice = localResultSet.getFloat(11);
				localVector.add(localBillDataVO);
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
		} finally {
			closeStatement(localResultSet, localPreparedStatement, null);
		}
		return localVector;
	}

	public Map getHqTime() throws SQLException {
		HashMap localHashMap = null;
		Object localObject1 = null;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		try {
			this.conn = getConn();
			localHashMap = new HashMap();
			String str = "SELECT Time, marketID FROM CurrentData WHERE CommodityID = ?  ";
			localPreparedStatement = this.conn.prepareStatement(str);
			localPreparedStatement.setString(1, "SYS");
			Object localObject2 = null;
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				localHashMap.put(localResultSet.getString(2), localResultSet.getTimestamp(1));
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
		} finally {
			closeStatement(localResultSet, localPreparedStatement, null);
		}
		return localHashMap;
	}

	public DayDataVO getPreDayData(String paramString, Date paramDate) throws SQLException {
		return null;
	}

	public void updateRealData(ProductDataVO paramProductDataVO) {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		try {
			localPreparedStatement = this.conn.prepareStatement("SELECT CommodityID FROM CurrentData WHERE CommodityID = ?");
			localPreparedStatement.setString(1, paramProductDataVO.code);
			localResultSet = localPreparedStatement.executeQuery();
			int i;
			if (localResultSet.next()) {
				i = 1;
			} else {
				i = 0;
			}
			closeStatement(localResultSet, localPreparedStatement, null);
			if (i != 0) {
				localPreparedStatement = this.conn.prepareStatement(
						"UPDATE CurrentData SET Time=?,HighPrice=?,LowPrice=?,CurPrice=?,CurAmount=?,ReserveCount=?,ReserveChange=?,BalancePrice=?,TotalMoney=?,TotalAmount=? WHERE CommodityID=?");
				int j = 1;
				localPreparedStatement.setTimestamp(j++, new Timestamp(paramProductDataVO.time.getTime()));
				localPreparedStatement.setFloat(j++, paramProductDataVO.highPrice);
				localPreparedStatement.setFloat(j++, paramProductDataVO.lowPrice);
				localPreparedStatement.setFloat(j++, paramProductDataVO.curPrice);
				localPreparedStatement.setInt(j++, paramProductDataVO.curAmount);
				localPreparedStatement.setInt(j++, paramProductDataVO.reserveCount);
				localPreparedStatement.setInt(j++, paramProductDataVO.reserveChange);
				localPreparedStatement.setFloat(j++, paramProductDataVO.balancePrice);
				localPreparedStatement.setDouble(j++, paramProductDataVO.totalMoney);
				localPreparedStatement.setLong(j++, paramProductDataVO.totalAmount);
				localPreparedStatement.setString(j++, paramProductDataVO.code);
			} else {
				insertIndexComm(paramProductDataVO);
				String str = "INSERT INTO CurrentData ( Time,CommodityID,YesterBalancePrice,ClosePrice,OpenPrice,HighPrice,LowPrice,CurPrice,CurAmount,OpenAmount,closeAmount,ReserveCount,ReserveChange,BalancePrice,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,BuyAmount1,SellAmount1,BuyPrice2,SellPrice2,BuyAmount2,SellAmount2,BuyPrice3,SellPrice3,BuyAmount3,SellAmount3,BuyPrice4,SellPrice4,BuyAmount4,SellAmount4,BuyPrice5,SellPrice5,BuyAmount5,SellAmount5,OutAmount,InAmount,TradeCue,TradeNo) VALUES (?";
				int k = 0;
				for (k = 0; k < 39; k++) {
					str = str + ",?";
				}
				str = str + ")";
				localPreparedStatement = this.conn.prepareStatement(str);
				k = 1;
				localPreparedStatement.setTimestamp(k++, new Timestamp(paramProductDataVO.time.getTime()));
				localPreparedStatement.setString(k++, paramProductDataVO.code);
				localPreparedStatement.setFloat(k++, paramProductDataVO.yesterBalancePrice);
				localPreparedStatement.setFloat(k++, paramProductDataVO.closePrice);
				try {
					double d1 = Double.parseDouble(paramProductDataVO.openPrice + "");
					paramProductDataVO.openPrice = ((float) d1);
				} catch (Exception localException2) {
					paramProductDataVO.openPrice = paramProductDataVO.yesterBalancePrice;
					localException2.printStackTrace();
				}
				localPreparedStatement.setFloat(k++, paramProductDataVO.openPrice);
				localPreparedStatement.setFloat(k++, paramProductDataVO.highPrice);
				localPreparedStatement.setFloat(k++, paramProductDataVO.lowPrice);
				try {
					double d2 = Double.parseDouble(paramProductDataVO.curPrice + "");
					paramProductDataVO.curPrice = ((float) d2);
				} catch (Exception localException3) {
					paramProductDataVO.curPrice = paramProductDataVO.yesterBalancePrice;
					localException3.printStackTrace();
				}
				localPreparedStatement.setFloat(k++, paramProductDataVO.curPrice);
				localPreparedStatement.setInt(k++, paramProductDataVO.curAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.openAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.closeAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.reserveCount);
				localPreparedStatement.setInt(k++, paramProductDataVO.reserveChange);
				localPreparedStatement.setFloat(k++, paramProductDataVO.balancePrice);
				localPreparedStatement.setDouble(k++, paramProductDataVO.totalMoney);
				localPreparedStatement.setLong(k++, paramProductDataVO.totalAmount);
				for (int m = 0; m < 5; m++) {
					localPreparedStatement.setFloat(k++, paramProductDataVO.buyPrice[m]);
					localPreparedStatement.setFloat(k++, paramProductDataVO.sellPrice[m]);
					localPreparedStatement.setInt(k++, paramProductDataVO.buyAmount[m]);
					localPreparedStatement.setInt(k++, paramProductDataVO.sellAmount[m]);
				}
				localPreparedStatement.setInt(k++, paramProductDataVO.outAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.inAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.tradeCue);
				localPreparedStatement.setInt(k++, 99);
			}
			localPreparedStatement.executeUpdate();
			localPreparedStatement.close();
			String str = "INSERT INTO TodayBillData ( Time,CommodityID,CurPrice,OpenAmount,closeAmount,ReserveCount,BalancePrice,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,BuyAmount1,SellAmount1,BuyPrice2,SellPrice2,BuyAmount2,SellAmount2,BuyPrice3,SellPrice3,BuyAmount3,SellAmount3,TradeCue) VALUES (?";
			for (int k = 0; k < 21; k++) {
				str = str + ",?";
			}
			str = str + ")";
			localPreparedStatement = this.conn.prepareStatement(str);
			int k = 1;
			localPreparedStatement.setTimestamp(k++, new Timestamp(paramProductDataVO.time.getTime()));
			localPreparedStatement.setString(k++, paramProductDataVO.code);
			localPreparedStatement.setFloat(k++, paramProductDataVO.curPrice);
			localPreparedStatement.setInt(k++, paramProductDataVO.openAmount);
			localPreparedStatement.setInt(k++, paramProductDataVO.closeAmount);
			localPreparedStatement.setInt(k++, paramProductDataVO.reserveCount);
			localPreparedStatement.setFloat(k++, paramProductDataVO.balancePrice);
			localPreparedStatement.setDouble(k++, paramProductDataVO.totalMoney);
			localPreparedStatement.setLong(k++, paramProductDataVO.totalAmount);
			for (int m = 0; m < 3; m++) {
				localPreparedStatement.setFloat(k++, paramProductDataVO.buyPrice[m]);
				localPreparedStatement.setFloat(k++, paramProductDataVO.sellPrice[m]);
				localPreparedStatement.setInt(k++, paramProductDataVO.buyAmount[m]);
				localPreparedStatement.setInt(k++, paramProductDataVO.sellAmount[m]);
			}
			localPreparedStatement.setInt(k++, paramProductDataVO.tradeCue);
			localPreparedStatement.executeUpdate();
		} catch (Exception localException1) {
			System.out.println(paramProductDataVO);
			localException1.printStackTrace();
		} finally {
			closeStatement(null, localPreparedStatement, null);
		}
	}

	private void insertIndexComm(ProductDataVO paramProductDataVO) throws SQLException {
		PreparedStatement localPreparedStatement = null;
		String str = "insert into Commodity (CommodityID,fullname,status,TRADINGSEC,REMARKS,modifytime) values (?,?,'N',1,'index',sysdate)";
		localPreparedStatement = this.conn.prepareStatement(str);
		localPreparedStatement.setString(1, paramProductDataVO.code);
		localPreparedStatement.setString(2, paramProductDataVO.name);
		localPreparedStatement.executeUpdate();
		closeStatement(null, localPreparedStatement, null);
	}

	public void updateSeriesData(ProductDataVO paramProductDataVO) {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		try {
			localPreparedStatement = this.conn.prepareStatement("SELECT CommodityID FROM CurrentData WHERE CommodityID = ?");
			localPreparedStatement.setString(1, paramProductDataVO.code);
			localResultSet = localPreparedStatement.executeQuery();
			int i;
			if (localResultSet.next()) {
				i = 1;
			} else {
				i = 0;
			}
			closeStatement(localResultSet, localPreparedStatement, null);
			int k;
			if (i != 0) {
				localPreparedStatement = this.conn.prepareStatement(
						"UPDATE CurrentData SET Time=?,HighPrice=?,LowPrice=?,CurPrice=?,CurAmount=?,OpenPrice=?,ReserveCount=?,ReserveChange=?,BalancePrice=?,TotalMoney=?,TotalAmount=?,OpenAmount=?,BuyPrice1=?,SellPrice1=?,BuyAmount1=?,SellAmount1=?,BuyPrice2=?,SellPrice2=?,BuyAmount2=?,SellAmount2=?,BuyPrice3=?,SellPrice3=?,BuyAmount3=?,SellAmount3=?,BuyPrice4=?,SellPrice4=?,BuyAmount4=?,SellAmount4=?,BuyPrice5=?,SellPrice5=?,BuyAmount5=?,SellAmount5=?,OutAmount=?,InAmount=? WHERE CommodityID=?");
				int j = 1;
				localPreparedStatement.setTimestamp(j++, new Timestamp(paramProductDataVO.time.getTime()));
				localPreparedStatement.setFloat(j++, paramProductDataVO.highPrice);
				localPreparedStatement.setFloat(j++, paramProductDataVO.lowPrice);
				localPreparedStatement.setFloat(j++, paramProductDataVO.curPrice);
				localPreparedStatement.setInt(j++, paramProductDataVO.curAmount);
				localPreparedStatement.setFloat(j++, paramProductDataVO.openPrice);
				localPreparedStatement.setInt(j++, paramProductDataVO.reserveCount);
				localPreparedStatement.setInt(j++, paramProductDataVO.reserveChange);
				localPreparedStatement.setFloat(j++, paramProductDataVO.balancePrice);
				localPreparedStatement.setDouble(j++, paramProductDataVO.totalMoney);
				localPreparedStatement.setLong(j++, paramProductDataVO.totalAmount);
				localPreparedStatement.setInt(j++, paramProductDataVO.openAmount);
				for (k = 0; k < 5; k++) {
					localPreparedStatement.setFloat(j++, paramProductDataVO.buyPrice[k]);
					localPreparedStatement.setFloat(j++, paramProductDataVO.sellPrice[k]);
					localPreparedStatement.setInt(j++, paramProductDataVO.buyAmount[k]);
					localPreparedStatement.setInt(j++, paramProductDataVO.sellAmount[k]);
				}
				localPreparedStatement.setInt(j++, paramProductDataVO.outAmount);
				localPreparedStatement.setInt(j++, paramProductDataVO.inAmount);
				localPreparedStatement.setString(j++, paramProductDataVO.code);
			} else {
				String str = "INSERT INTO CurrentData ( Time,CommodityID,YesterBalancePrice,ClosePrice,OpenPrice,HighPrice,LowPrice,CurPrice,CurAmount,OpenAmount,closeAmount,ReserveCount,ReserveChange,BalancePrice,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,BuyAmount1,SellAmount1,BuyPrice2,SellPrice2,BuyAmount2,SellAmount2,BuyPrice3,SellPrice3,BuyAmount3,SellAmount3,BuyPrice4,SellPrice4,BuyAmount4,SellAmount4,BuyPrice5,SellPrice5,BuyAmount5,SellAmount5,OutAmount,InAmount,TradeCue,TradeNo) VALUES (?";
				for (k = 0; k < 39; k++) {
					str = str + ",?";
				}
				str = str + ")";
				localPreparedStatement = this.conn.prepareStatement(str);
				k = 1;
				localPreparedStatement.setTimestamp(k++, new Timestamp(paramProductDataVO.time.getTime()));
				localPreparedStatement.setString(k++, paramProductDataVO.code);
				localPreparedStatement.setFloat(k++, paramProductDataVO.yesterBalancePrice);
				localPreparedStatement.setFloat(k++, paramProductDataVO.closePrice);
				try {
					double d1 = Double.parseDouble(paramProductDataVO.openPrice + "");
					paramProductDataVO.openPrice = ((float) d1);
				} catch (Exception localException2) {
					paramProductDataVO.openPrice = paramProductDataVO.yesterBalancePrice;
					localException2.printStackTrace();
				}
				localPreparedStatement.setFloat(k++, paramProductDataVO.openPrice);
				localPreparedStatement.setFloat(k++, paramProductDataVO.highPrice);
				localPreparedStatement.setFloat(k++, paramProductDataVO.lowPrice);
				try {
					double d2 = Double.parseDouble(paramProductDataVO.curPrice + "");
					paramProductDataVO.curPrice = ((float) d2);
				} catch (Exception localException3) {
					paramProductDataVO.curPrice = paramProductDataVO.yesterBalancePrice;
					localException3.printStackTrace();
				}
				localPreparedStatement.setFloat(k++, paramProductDataVO.curPrice);
				localPreparedStatement.setInt(k++, paramProductDataVO.curAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.openAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.closeAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.reserveCount);
				localPreparedStatement.setInt(k++, paramProductDataVO.reserveChange);
				localPreparedStatement.setFloat(k++, paramProductDataVO.balancePrice);
				localPreparedStatement.setDouble(k++, paramProductDataVO.totalMoney);
				localPreparedStatement.setLong(k++, paramProductDataVO.totalAmount);
				for (int m = 0; m < 5; m++) {
					localPreparedStatement.setFloat(k++, paramProductDataVO.buyPrice[m]);
					localPreparedStatement.setFloat(k++, paramProductDataVO.sellPrice[m]);
					localPreparedStatement.setInt(k++, paramProductDataVO.buyAmount[m]);
					localPreparedStatement.setInt(k++, paramProductDataVO.sellAmount[m]);
				}
				localPreparedStatement.setInt(k++, paramProductDataVO.outAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.inAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.tradeCue);
				localPreparedStatement.setInt(k++, -1);
			}
			localPreparedStatement.executeUpdate();
			localPreparedStatement.close();
		} catch (Exception localException1) {
			System.out.println(paramProductDataVO);
			localException1.printStackTrace();
		} finally {
			closeStatement(null, localPreparedStatement, null);
		}
	}

	public void insertSeriesData(ProductDataVO paramProductDataVO) {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		try {
			localPreparedStatement = this.conn.prepareStatement("SELECT CommodityID FROM CurrentData WHERE CommodityID = ?");
			localPreparedStatement.setString(1, paramProductDataVO.code);
			localResultSet = localPreparedStatement.executeQuery();
			int i;
			if (localResultSet.next()) {
				i = 1;
			} else {
				i = 0;
			}
			closeStatement(localResultSet, localPreparedStatement, null);
			int k;
			String str;
			int m;
			if (i != 0) {
				localPreparedStatement = this.conn.prepareStatement(
						"UPDATE CurrentData SET Time=?,HighPrice=?,LowPrice=?,CurPrice=?,CurAmount=?,OpenPrice=?,ReserveCount=?,ReserveChange=?,BalancePrice=?,TotalMoney=?,TotalAmount=?,OpenAmount=?,BuyPrice1=?,SellPrice1=?,BuyAmount1=?,SellAmount1=?,BuyPrice2=?,SellPrice2=?,BuyAmount2=?,SellAmount2=?,BuyPrice3=?,SellPrice3=?,BuyAmount3=?,SellAmount3=?,BuyPrice4=?,SellPrice4=?,BuyAmount4=?,SellAmount4=?,BuyPrice5=?,SellPrice5=?,BuyAmount5=?,SellAmount5=?,OutAmount=?,InAmount=? WHERE CommodityID=?");
				int j = 1;
				localPreparedStatement.setTimestamp(j++, new Timestamp(paramProductDataVO.time.getTime()));
				localPreparedStatement.setFloat(j++, paramProductDataVO.highPrice);
				localPreparedStatement.setFloat(j++, paramProductDataVO.lowPrice);
				localPreparedStatement.setFloat(j++, paramProductDataVO.curPrice);
				localPreparedStatement.setInt(j++, paramProductDataVO.curAmount);
				localPreparedStatement.setFloat(j++, paramProductDataVO.openPrice);
				localPreparedStatement.setInt(j++, paramProductDataVO.reserveCount);
				localPreparedStatement.setInt(j++, paramProductDataVO.reserveChange);
				localPreparedStatement.setFloat(j++, paramProductDataVO.balancePrice);
				localPreparedStatement.setDouble(j++, paramProductDataVO.totalMoney);
				localPreparedStatement.setLong(j++, paramProductDataVO.totalAmount);
				localPreparedStatement.setInt(j++, paramProductDataVO.openAmount);
				for (k = 0; k < 5; k++) {
					localPreparedStatement.setFloat(j++, paramProductDataVO.buyPrice[k]);
					localPreparedStatement.setFloat(j++, paramProductDataVO.sellPrice[k]);
					localPreparedStatement.setInt(j++, paramProductDataVO.buyAmount[k]);
					localPreparedStatement.setInt(j++, paramProductDataVO.sellAmount[k]);
				}
				localPreparedStatement.setInt(j++, paramProductDataVO.outAmount);
				localPreparedStatement.setInt(j++, paramProductDataVO.inAmount);
				localPreparedStatement.setString(j++, paramProductDataVO.code);
			} else {
				str = "INSERT INTO CurrentData ( Time,CommodityID,YesterBalancePrice,ClosePrice,OpenPrice,HighPrice,LowPrice,CurPrice,CurAmount,OpenAmount,closeAmount,ReserveCount,ReserveChange,BalancePrice,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,BuyAmount1,SellAmount1,BuyPrice2,SellPrice2,BuyAmount2,SellAmount2,BuyPrice3,SellPrice3,BuyAmount3,SellAmount3,BuyPrice4,SellPrice4,BuyAmount4,SellAmount4,BuyPrice5,SellPrice5,BuyAmount5,SellAmount5,OutAmount,InAmount,TradeCue,TradeNo) VALUES (?";
				for (k = 0; k < 39; k++) {
					str = str + ",?";
				}
				str = str + ")";
				localPreparedStatement = this.conn.prepareStatement(str);
				k = 1;
				localPreparedStatement.setTimestamp(k++, new Timestamp(paramProductDataVO.time.getTime()));
				localPreparedStatement.setString(k++, paramProductDataVO.code);
				localPreparedStatement.setFloat(k++, paramProductDataVO.yesterBalancePrice);
				localPreparedStatement.setFloat(k++, paramProductDataVO.closePrice);
				try {
					double d1 = Double.parseDouble(paramProductDataVO.openPrice + "");
					paramProductDataVO.openPrice = ((float) d1);
				} catch (Exception localException2) {
					paramProductDataVO.openPrice = paramProductDataVO.yesterBalancePrice;
					localException2.printStackTrace();
				}
				localPreparedStatement.setFloat(k++, paramProductDataVO.openPrice);
				localPreparedStatement.setFloat(k++, paramProductDataVO.highPrice);
				localPreparedStatement.setFloat(k++, paramProductDataVO.lowPrice);
				try {
					double d2 = Double.parseDouble(paramProductDataVO.curPrice + "");
					paramProductDataVO.curPrice = ((float) d2);
				} catch (Exception localException3) {
					paramProductDataVO.curPrice = paramProductDataVO.yesterBalancePrice;
					localException3.printStackTrace();
				}
				localPreparedStatement.setFloat(k++, paramProductDataVO.curPrice);
				localPreparedStatement.setInt(k++, paramProductDataVO.curAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.openAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.closeAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.reserveCount);
				localPreparedStatement.setInt(k++, paramProductDataVO.reserveChange);
				localPreparedStatement.setFloat(k++, paramProductDataVO.balancePrice);
				localPreparedStatement.setDouble(k++, paramProductDataVO.totalMoney);
				localPreparedStatement.setLong(k++, paramProductDataVO.totalAmount);
				for (m = 0; m < 5; m++) {
					localPreparedStatement.setFloat(k++, paramProductDataVO.buyPrice[m]);
					localPreparedStatement.setFloat(k++, paramProductDataVO.sellPrice[m]);
					localPreparedStatement.setInt(k++, paramProductDataVO.buyAmount[m]);
					localPreparedStatement.setInt(k++, paramProductDataVO.sellAmount[m]);
				}
				localPreparedStatement.setInt(k++, paramProductDataVO.outAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.inAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.tradeCue);
				localPreparedStatement.setInt(k++, -1);
			}
			localPreparedStatement.executeUpdate();
			localPreparedStatement.close();
			if (paramProductDataVO.curPrice > 0.01F) {
				str = "INSERT INTO TodayBillData ( Time,CommodityID,CurPrice,OpenAmount,closeAmount,ReserveCount,BalancePrice,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,BuyAmount1,SellAmount1,BuyPrice2,SellPrice2,BuyAmount2,SellAmount2,BuyPrice3,SellPrice3,BuyAmount3,SellAmount3,TradeCue) VALUES (?";
				for (k = 0; k < 21; k++) {
					str = str + ",?";
				}
				str = str + ")";
				localPreparedStatement = this.conn.prepareStatement(str);
				k = 1;
				localPreparedStatement.setTimestamp(k++, new Timestamp(paramProductDataVO.time.getTime()));
				localPreparedStatement.setString(k++, paramProductDataVO.code);
				localPreparedStatement.setFloat(k++, paramProductDataVO.curPrice);
				localPreparedStatement.setInt(k++, paramProductDataVO.openAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.closeAmount);
				localPreparedStatement.setInt(k++, paramProductDataVO.reserveCount);
				localPreparedStatement.setFloat(k++, paramProductDataVO.balancePrice);
				localPreparedStatement.setDouble(k++, paramProductDataVO.totalMoney);
				localPreparedStatement.setLong(k++, paramProductDataVO.totalAmount);
				for (m = 0; m < 3; m++) {
					localPreparedStatement.setFloat(k++, paramProductDataVO.buyPrice[m]);
					localPreparedStatement.setFloat(k++, paramProductDataVO.sellPrice[m]);
					localPreparedStatement.setInt(k++, paramProductDataVO.buyAmount[m]);
					localPreparedStatement.setInt(k++, paramProductDataVO.sellAmount[m]);
				}
				localPreparedStatement.setInt(k++, paramProductDataVO.tradeCue);
				localPreparedStatement.executeUpdate();
			}
		} catch (Exception localException1) {
			System.out.println(paramProductDataVO);
			localException1.printStackTrace();
		} finally {
			closeStatement(null, localPreparedStatement, null);
		}
	}

	public DayDataVO getBaseDayData(String paramString, Date paramDate) throws SQLException {
		return null;
	}

	public ArrayList getCo_class() throws SQLException {
		return null;
	}

	public boolean isDataExist(String paramString, long paramLong, double paramDouble, int paramInt) throws SQLException {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		boolean bool = false;
		try {
			this.conn = getConn();
			localPreparedStatement = this.conn
					.prepareStatement("select count(*) from currentdata where CommodityID=? and totalamount=? and totalmoney=? and reserveCount=?");
			localPreparedStatement.setString(1, paramString);
			localPreparedStatement.setLong(2, paramLong);
			localPreparedStatement.setDouble(3, paramDouble);
			localPreparedStatement.setInt(4, paramInt);
			localResultSet = localPreparedStatement.executeQuery();
			if (localResultSet.next()) {
				int i = localResultSet.getInt(1);
				System.out.println("================" + i + "===================");
				if (i > 0) {
					bool = true;
				} else {
					bool = false;
				}
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			closeStatement(localResultSet, localPreparedStatement, null);
		}
		return bool;
	}

	public void initIndexData(ProductDataVO paramProductDataVO) throws SQLException {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		try {
			localPreparedStatement = this.conn.prepareStatement("SELECT CommodityID FROM CurrentData WHERE CommodityID = ?");
			localPreparedStatement.setString(1, paramProductDataVO.code);
			localResultSet = localPreparedStatement.executeQuery();
			int i;
			if (localResultSet.next()) {
				i = 1;
			} else {
				i = 0;
			}
			closeStatement(localResultSet, localPreparedStatement, null);
			String str;
			if (i != 0) {
				if (paramProductDataVO.code != "") {
					str = "delete from currentdata where CommodityID=?";
					System.out.println("sql:" + str);
					localPreparedStatement = this.conn.prepareStatement(str);
					localPreparedStatement.setString(1, paramProductDataVO.code);
					localPreparedStatement.execute();
					localPreparedStatement.close();
					localPreparedStatement = null;
				}
			} else {
				str = "INSERT INTO CurrentData ( Time,CommodityID,YesterBalancePrice,ClosePrice,OpenPrice,HighPrice,LowPrice,CurPrice,CurAmount,OpenAmount,closeAmount,ReserveCount,ReserveChange,BalancePrice,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,BuyAmount1,SellAmount1,BuyPrice2,SellPrice2,BuyAmount2,SellAmount2,BuyPrice3,SellPrice3,BuyAmount3,SellAmount3,BuyPrice4,SellPrice4,BuyAmount4,SellAmount4,BuyPrice5,SellPrice5,BuyAmount5,SellAmount5,OutAmount,InAmount,TradeCue,TradeNo) VALUES (?";
				for (int j = 0; j < 39; j++) {
					str = str + ",?";
				}
				str = str + ")";
				localPreparedStatement = this.conn.prepareStatement(str);
				int j = 1;
				localPreparedStatement.setTimestamp(j++, new Timestamp(paramProductDataVO.time.getTime()));
				localPreparedStatement.setString(j++, paramProductDataVO.code);
				localPreparedStatement.setFloat(j++, paramProductDataVO.yesterBalancePrice);
				localPreparedStatement.setFloat(j++, paramProductDataVO.closePrice);
				localPreparedStatement.setFloat(j++, paramProductDataVO.openPrice);
				localPreparedStatement.setFloat(j++, paramProductDataVO.highPrice);
				localPreparedStatement.setFloat(j++, paramProductDataVO.lowPrice);
				localPreparedStatement.setFloat(j++, paramProductDataVO.curPrice);
				localPreparedStatement.setInt(j++, paramProductDataVO.curAmount);
				localPreparedStatement.setInt(j++, paramProductDataVO.openAmount);
				localPreparedStatement.setInt(j++, paramProductDataVO.closeAmount);
				localPreparedStatement.setInt(j++, paramProductDataVO.reserveCount);
				localPreparedStatement.setInt(j++, paramProductDataVO.reserveChange);
				localPreparedStatement.setFloat(j++, paramProductDataVO.balancePrice);
				localPreparedStatement.setDouble(j++, paramProductDataVO.totalMoney);
				localPreparedStatement.setLong(j++, paramProductDataVO.totalAmount);
				for (int k = 0; k < 5; k++) {
					localPreparedStatement.setFloat(j++, paramProductDataVO.buyPrice[k]);
					localPreparedStatement.setFloat(j++, paramProductDataVO.sellPrice[k]);
					localPreparedStatement.setInt(j++, paramProductDataVO.buyAmount[k]);
					localPreparedStatement.setInt(j++, paramProductDataVO.sellAmount[k]);
				}
				localPreparedStatement.setInt(j++, paramProductDataVO.outAmount);
				localPreparedStatement.setInt(j++, paramProductDataVO.inAmount);
				localPreparedStatement.setInt(j++, paramProductDataVO.tradeCue);
				localPreparedStatement.setInt(j++, -1);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			if (localPreparedStatement != null) {
				closeStatement(null, localPreparedStatement, null);
			}
		}
	}

	public List getAllCommID() throws SQLException {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		ArrayList localArrayList = new ArrayList();
		try {
			String str = "select CommodityID from Commodity";
			localPreparedStatement = this.conn.prepareStatement(str);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				localArrayList.add(localResultSet.getString(1));
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			closeStatement(localResultSet, localPreparedStatement, null);
		}
		return localArrayList;
	}

	public float getctrtsize(String paramString) {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		float f = 1.0F;
		try {
			String str = "select max(CTRTSIZE) from Commodity where CommodityID like ?";
			localPreparedStatement = this.conn.prepareStatement(str);
			localPreparedStatement.setString(1, paramString);
			localResultSet = localPreparedStatement.executeQuery();
			if (localResultSet.next()) {
				f = localResultSet.getFloat("CTRTSIZE");
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			closeStatement(localResultSet, localPreparedStatement, null);
		}
		return f;
	}

	public Date getHQDate() {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		String str1 = null;
		Date localDate = null;
		try {
			String str2 = "select max(to_char(time,'YYYY-MM-DD')) hqDate from CurrentData where CommodityID <> 'SYS'";
			this.conn = getConn();
			localPreparedStatement = this.conn.prepareStatement(str2);
			localResultSet = localPreparedStatement.executeQuery();
			if (localResultSet.next()) {
				str1 = localResultSet.getString("hqDate");
				localDate = new SimpleDateFormat("yyyy-MM-dd").parse(str1);
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			closeStatement(localResultSet, localPreparedStatement, null);
		}
		return localDate;
	}

	public AddrDictionary getAddrDictionary(Date paramDate) {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		AddrDictionary localAddrDictionary = null;
		StringBuffer localStringBuffer = new StringBuffer();
		try {
			this.conn = getConn();
			localAddrDictionary = new AddrDictionary();
			localStringBuffer.append("select * from AddrDic ");
			if (paramDate != null) {
				localStringBuffer.append(" where UpdateTime>? ");
			}
			localPreparedStatement = this.conn.prepareStatement(localStringBuffer.toString());
			if (paramDate != null) {
				localPreparedStatement.setTimestamp(1, new Timestamp(paramDate.getTime()));
			}
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				localAddrDictionary.putAddr(localResultSet.getString("AddrCode"), localResultSet.getString("AddrName"),
						localResultSet.getDate("UpdateTime"));
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			closeStatement(localResultSet, localPreparedStatement, null);
		}
		return localAddrDictionary;
	}

	public IndustryDictionary getIndustryDictionary(Date paramDate) {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		IndustryDictionary localIndustryDictionary = null;
		StringBuffer localStringBuffer = new StringBuffer();
		try {
			this.conn = getConn();
			localIndustryDictionary = new IndustryDictionary();
			localStringBuffer.append("select * from IndustryDic ");
			if (paramDate != null) {
				localStringBuffer.append(" where UpdateTime>? ");
			}
			localPreparedStatement = this.conn.prepareStatement(localStringBuffer.toString());
			if (paramDate != null) {
				localPreparedStatement.setTimestamp(1, new Timestamp(paramDate.getTime()));
			}
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				localIndustryDictionary.putIndustry(localResultSet.getString("IndustryCode"), localResultSet.getString("IndustryName"),
						localResultSet.getDate("UpdateTime"));
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			closeStatement(localResultSet, localPreparedStatement, null);
		}
		return localIndustryDictionary;
	}

	public MarketInfoVO getMarketInfo(String paramString) {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		MarketInfoVO localMarketInfoVO = null;
		StringBuffer localStringBuffer = new StringBuffer();
		try {
			this.conn = getConn();
			localMarketInfoVO = new MarketInfoVO();
			localStringBuffer.append("select * from MarketInFo ");
			if (paramString != null) {
				localStringBuffer.append(" where marketID=? ");
			}
			localPreparedStatement = this.conn.prepareStatement(localStringBuffer.toString());
			if (paramString != null) {
				localPreparedStatement.setString(1, paramString);
			}
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				localMarketInfoVO.putMarket(localResultSet.getString("marketID"), localResultSet.getString("marketName"),
						localResultSet.getString("direction"), localResultSet.getString("description"));
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			closeStatement(localResultSet, localPreparedStatement, null);
		}
		return localMarketInfoVO;
	}

	public long queryQuotationNO() throws SQLException {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		long l = 0L;
		try {
			String str = " select max(NO) no from currentdata ";
			localPreparedStatement = this.conn.prepareStatement(str);
			localResultSet = localPreparedStatement.executeQuery();
			if (localResultSet.next()) {
				l = localResultSet.getLong("no");
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			closeStatement(localResultSet, localPreparedStatement, null);
		}
		return l;
	}
}
