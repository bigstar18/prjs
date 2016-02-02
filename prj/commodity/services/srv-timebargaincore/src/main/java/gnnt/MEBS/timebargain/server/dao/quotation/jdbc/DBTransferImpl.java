package gnnt.MEBS.timebargain.server.dao.quotation.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.dao.quotation.IDBTransfer;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.quotation.BillDataVO;
import gnnt.MEBS.timebargain.server.model.quotation.KlineVO;
import gnnt.MEBS.timebargain.server.model.quotation.PreData;
import gnnt.MEBS.timebargain.server.model.quotation.TradeTimeVO;
import gnnt.MEBS.timebargain.server.quotation.HQEngine;
import gnnt.MEBS.timebargain.server.quotation.config.Config;
import oracle.sql.TIMESTAMP;

public class DBTransferImpl implements IDBTransfer {
	private final transient Log log = LogFactory.getLog(DBTransferImpl.class);
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Map<String, PreData> amountMap = new HashMap();

	public DBTransferImpl(Config paramConfig) {
		getConnection(HQEngine.config);
	}

	public void getConnection() throws SQLException {
		if ((this.conn == null) || (this.conn.isClosed())) {
			getConnection(HQEngine.config);
		}
	}

	public void getConnection(Config paramConfig) {
		try {
			String str1 = (String) paramConfig.targetMap.get("DBURL");
			String str2 = (String) paramConfig.targetMap.get("DBDriver");
			Class.forName(str2);
			this.conn = DriverManager.getConnection(str1);
			this.conn.setAutoCommit(true);
		} catch (Exception localException) {
			this.log.error("DB error!");
			throw new RuntimeException(localException);
		}
	}

	public void transferComodity(List paramList, long paramLong, HashMap<String, String> paramHashMap) {
		this.log.debug("enter into the transferCommodity Method");
		if ((paramList != null) && (paramList.size() > 0)) {
			StringBuilder localStringBuilder1 = new StringBuilder();
			localStringBuilder1.append("insert into Commodity (commodityid,fullname,status,tradingsec,type,ctrtsize,")
					.append("spread, maxspread,tradecomm,settlecomm,cashcomm,margin,ttlopen,BalancePrice,listdate,")
					.append("delistdate,curchange,maxholding,tradeno,modifytime) values (?");
			for (int i = 0; i < 19; i++) {
				localStringBuilder1.append(",?");
			}
			localStringBuilder1.append(")");
			this.log.debug("insert commodity sql:" + localStringBuilder1.toString());
			StringBuilder localStringBuilder2 = new StringBuilder();
			localStringBuilder2.append("insert into co_class (cc_classid,Market,cc_name,Cc_commodity_id) values ").append("(?,?,?,?)");
			this.log.debug("insert co_class sql : " + localStringBuilder2.toString());
			try {
				getConnection();
				for (int j = 0; j < paramList.size(); j++) {
					this.ps = this.conn.prepareStatement(localStringBuilder1.toString());
					Map localMap = (Map) paramList.get(j);
					Set localSet = localMap.keySet();
					Iterator localIterator = localSet.iterator();
					while (localIterator.hasNext()) {
						Object localObject1 = localIterator.next();
						System.out.println(localObject1 + "=" + localMap.get(localObject1) + ",");
					}
					int k = 0;
					String str1 = (String) localMap.get("COMMODITYID");
					this.ps.setString(++k, str1);
					this.ps.setString(++k, (String) localMap.get("NAME"));
					this.ps.setString(++k, (String) localMap.get("STATUS"));
					String str2 = paramHashMap.get(str1) == null ? "1" : (String) paramHashMap.get(str1);
					this.ps.setString(++k, str2);
					this.ps.setInt(++k, 0);
					this.ps.setDouble(++k, ((BigDecimal) localMap.get("CONTRACTFACTOR")).doubleValue());
					this.ps.setDouble(++k, ((BigDecimal) localMap.get("MINPRICEMOVE")).doubleValue());
					this.ps.setDouble(++k, ((BigDecimal) localMap.get("MAXSPREAD")).doubleValue());
					this.ps.setDouble(++k, ((BigDecimal) localMap.get("FEERATE_B")).doubleValue());
					this.ps.setDouble(++k, ((BigDecimal) localMap.get("SETTLEFEERATE_B")).doubleValue());
					this.ps.setDouble(++k, ((BigDecimal) localMap.get("FORCECLOSEFEERATE_B")).doubleValue());
					this.ps.setDouble(++k, ((BigDecimal) localMap.get("MARGINRATE_B")).doubleValue());
					this.ps.setInt(++k, ((BigDecimal) localMap.get("RESERVECOUNT")).intValue());
					this.ps.setDouble(++k, ((BigDecimal) localMap.get("LASTPRICE")).doubleValue());
					this.ps.setString(++k, (String) localMap.get("MARKETDATE"));
					this.ps.setString(++k, (String) localMap.get("SETTLEDATE"));
					this.ps.setInt(++k, 0);
					this.ps.setInt(++k, ((BigDecimal) localMap.get("FIRMMAXHOLDQTY")).intValue());
					this.ps.setInt(++k, 0);
					this.ps.setTimestamp(++k, new Timestamp(paramLong));
					this.ps.execute();
					this.ps.close();
					this.ps = null;
					if (localMap.get("BREEDID") != null) {
						this.ps = this.conn.prepareStatement(localStringBuilder2.toString());
						this.ps.setString(1, ((BigDecimal) localMap.get("BREEDID")).toString());
						this.ps.setString(2, "null");
						this.ps.setString(3, (String) localMap.get("BREEDNAME"));
						this.ps.setString(4, str1);
						this.ps.execute();
						this.ps.close();
						this.ps = null;
					}
				}
				return;
			} catch (SQLException localSQLException2) {
				localSQLException2.printStackTrace();
			} finally {
				try {
					if (this.ps != null) {
						this.ps.close();
					}
				} catch (SQLException localSQLException4) {
					localSQLException4.printStackTrace();
				}
			}
		}
	}

	public void transferProductData(List paramList) {
		this.log.debug("enter into the transferProductData Method");
		if ((paramList != null) && (paramList.size() > 0)) {
			try {
				StringBuilder localStringBuilder = new StringBuilder();
				localStringBuilder
						.append("UPDATE CurrentData SET Time=?,YESTERBALANCEPRICE=?,CLOSEPRICE=?,OPENPRICE=?,HighPrice=?,LowPrice=?,CurPrice=?,CurAmount=?, ")
						.append("OpenAmount=?,CLOSEAMOUNT=?,ReserveCount=?,ReserveChange=?,BalancePrice=?,TotalMoney=?,TotalAmount=?, ")
						.append("BuyPrice1=?,SellPrice1=?,BuyAmount1=?,SellAmount1=?,BuyPrice2=?,SellPrice2=?,BuyAmount2=?,SellAmount2=?, ")
						.append("BuyPrice3=?,SellPrice3=?,BuyAmount3=?,SellAmount3=?,BuyPrice4=?,SellPrice4=?,BuyAmount4=?,SellAmount4=?, ")
						.append("BuyPrice5=?,SellPrice5=?,BuyAmount5=?,SellAmount5=?,OutAmount=?,InAmount=?,no=? ").append("WHERE commodityid=?");
				this.log.debug(localStringBuilder.toString());
				getConnection();
				for (int i = 0; i < paramList.size(); i++) {
					this.ps = this.conn.prepareStatement(localStringBuilder.toString());
					int j = 0;
					Quotation localQuotation = (Quotation) paramList.get(i);
					this.ps.setTimestamp(++j,
							localQuotation.getCreateTime() == null ? new Timestamp(System.currentTimeMillis()) : localQuotation.getCreateTime());
					this.ps.setDouble(++j, localQuotation.getYesterBalancePrice().doubleValue());
					this.ps.setDouble(++j, localQuotation.getClosePrice().doubleValue());
					this.ps.setDouble(++j, localQuotation.getOpenPrice().doubleValue());
					this.ps.setDouble(++j, localQuotation.getHighPrice().doubleValue());
					this.ps.setDouble(++j, localQuotation.getLowPrice().doubleValue());
					this.ps.setDouble(++j, localQuotation.getCurPrice().doubleValue());
					this.ps.setLong(++j, localQuotation.getCurAmount().longValue());
					this.ps.setLong(++j, localQuotation.getOpenAmount().longValue());
					this.ps.setLong(++j, localQuotation.getCloseAmount().longValue());
					this.ps.setLong(++j, localQuotation.getReserveCount().longValue());
					this.ps.setLong(++j, localQuotation.getReserveChange().longValue());
					this.ps.setDouble(++j, localQuotation.getPrice().doubleValue());
					this.ps.setDouble(++j, localQuotation.getTotalMoney().doubleValue());
					this.ps.setLong(++j, localQuotation.getTotalAmount().longValue());
					this.ps.setDouble(++j, localQuotation.buy[0]);
					this.ps.setDouble(++j, localQuotation.sell[0]);
					this.ps.setLong(++j, localQuotation.buyqty[0]);
					this.ps.setLong(++j, localQuotation.sellqty[0]);
					this.ps.setDouble(++j, localQuotation.buy[1]);
					this.ps.setDouble(++j, localQuotation.sell[1]);
					this.ps.setLong(++j, localQuotation.buyqty[1]);
					this.ps.setLong(++j, localQuotation.sellqty[1]);
					this.ps.setDouble(++j, localQuotation.buy[2]);
					this.ps.setDouble(++j, localQuotation.sell[2]);
					this.ps.setLong(++j, localQuotation.buyqty[2]);
					this.ps.setLong(++j, localQuotation.sellqty[2]);
					this.ps.setDouble(++j, localQuotation.buy[3]);
					this.ps.setDouble(++j, localQuotation.sell[3]);
					this.ps.setLong(++j, localQuotation.buyqty[3]);
					this.ps.setLong(++j, localQuotation.sellqty[3]);
					this.ps.setDouble(++j, localQuotation.buy[4]);
					this.ps.setDouble(++j, localQuotation.sell[4]);
					this.ps.setLong(++j, localQuotation.buyqty[4]);
					this.ps.setLong(++j, localQuotation.sellqty[4]);
					this.ps.setLong(++j, localQuotation.getOutAmount().longValue());
					this.ps.setLong(++j, localQuotation.getInAmount().longValue());
					this.ps.setLong(++j, localQuotation.getNo().longValue());
					String str = localQuotation.getCommodityID();
					this.ps.setString(++j, str);
					this.ps.execute();
					this.ps.close();
					PreData localPreData = (PreData) this.amountMap.get(str);
					Object localObject1;
					if (localPreData == null) {
						localPreData = new PreData();
						localObject1 = "Select MAX(Totalamount),SUM(OpenAmount),SUM(CloseAmount) from todaybilldata where commodityid=? ";
						this.ps = this.conn.prepareStatement((String) localObject1);
						this.ps.setString(1, str);
						this.rs = this.ps.executeQuery();
						if (this.rs.next()) {
							localPreData.preTotalAmount = this.rs.getLong(1);
							localPreData.preOpenAmount = this.rs.getLong(2);
							localPreData.preCloseAmount = this.rs.getLong(3);
						}
						this.rs.close();
						this.ps.close();
					}
					this.log.debug("curTotalamout=" + localQuotation.getTotalAmount() + ",preTotalAmount=" + localPreData.preTotalAmount
							+ "curOpenAmount=" + localQuotation.getOpenAmount() + ",preOpenAmount=" + localPreData.preOpenAmount + "curCloseAmount="
							+ localQuotation.getCloseAmount() + ",preCloseAmount=" + localPreData.preCloseAmount);
					if (localQuotation.getTotalAmount().longValue() > localPreData.preTotalAmount) {
						localObject1 = new StringBuilder();
						((StringBuilder) localObject1).append("INSERT INTO TodayBillData (Time,commodityid,CurPrice,OpenAmount,closeAmount,")
								.append("ReserveCount,BalancePrice,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,BuyAmount1,SellAmount1,BuyPrice2,SellPrice2,")
								.append("BuyAmount2,SellAmount2,BuyPrice3,SellPrice3,BuyAmount3,SellAmount3,TradeCue) VALUES (?");
						for (int k = 0; k < 21; k++) {
							((StringBuilder) localObject1).append(",?");
						}
						((StringBuilder) localObject1).append(")");
						this.log.debug("sql:" + localObject1);
						this.ps = this.conn.prepareStatement(((StringBuilder) localObject1).toString());
						this.ps.setTimestamp(1,
								localQuotation.getCreateTime() == null ? new Timestamp(System.currentTimeMillis()) : localQuotation.getCreateTime());
						this.ps.setString(2, str);
						this.ps.setDouble(3, localQuotation.getCurPrice().doubleValue());
						this.ps.setLong(4, localQuotation.getOpenAmount().longValue() - localPreData.preOpenAmount);
						this.ps.setLong(5, localQuotation.getCloseAmount().longValue() - localPreData.preCloseAmount);
						this.ps.setLong(6, localQuotation.getReserveCount().longValue());
						this.ps.setDouble(7, localQuotation.getPrice().doubleValue());
						this.ps.setDouble(8, localQuotation.getTotalMoney().doubleValue());
						this.ps.setLong(9, localQuotation.getTotalAmount().longValue());
						this.ps.setDouble(10, localQuotation.buy[0]);
						this.ps.setDouble(11, localQuotation.sell[0]);
						this.ps.setLong(12, localQuotation.buyqty[0]);
						this.ps.setLong(13, localQuotation.sellqty[0]);
						this.ps.setDouble(14, localQuotation.buy[1]);
						this.ps.setDouble(15, localQuotation.sell[1]);
						this.ps.setLong(16, localQuotation.buyqty[1]);
						this.ps.setLong(17, localQuotation.sellqty[1]);
						this.ps.setDouble(18, localQuotation.buy[2]);
						this.ps.setDouble(19, localQuotation.sell[2]);
						this.ps.setLong(20, localQuotation.buyqty[2]);
						this.ps.setLong(21, localQuotation.sellqty[2]);
						this.ps.setLong(22, localQuotation.getTradeCue().longValue());
						this.ps.executeUpdate();
						this.ps.close();
						localPreData.preTotalAmount = localQuotation.getTotalAmount().longValue();
						localPreData.preOpenAmount = localQuotation.getOpenAmount().longValue();
						localPreData.preCloseAmount = localQuotation.getCloseAmount().longValue();
						this.amountMap.put(str, localPreData);
					}
				}
			} catch (SQLException localSQLException2) {
				localSQLException2.printStackTrace();
			} finally {
				try {
					if (this.rs != null) {
						this.rs.close();
					}
					if (this.ps != null) {
						this.ps.close();
					}
				} catch (SQLException localSQLException4) {
					localSQLException4.printStackTrace();
				}
			}
		} else {
			this.log.info("ProductData List is empty,Please check data load...");
		}
	}

	public void transferProductData(List paramList, int paramInt) {
		this.log.debug("enter into the transferProductData Method");
		if ((paramList != null) && (paramList.size() > 0)) {
			try {
				StringBuilder localStringBuilder = new StringBuilder();
				localStringBuilder
						.append("UPDATE CurrentData SET Time=?,YESTERBALANCEPRICE=?,CLOSEPRICE=?,OPENPRICE=?,HighPrice=?,LowPrice=?,CurPrice=?,CurAmount=?, ")
						.append("OpenAmount=?,CLOSEAMOUNT=?,ReserveCount=?,ReserveChange=?,BalancePrice=?,TotalMoney=?,TotalAmount=?, ")
						.append("BuyPrice1=?,SellPrice1=?,BuyAmount1=?,SellAmount1=?,BuyPrice2=?,SellPrice2=?,BuyAmount2=?,SellAmount2=?, ")
						.append("BuyPrice3=?,SellPrice3=?,BuyAmount3=?,SellAmount3=?,BuyPrice4=?,SellPrice4=?,BuyAmount4=?,SellAmount4=?, ")
						.append("BuyPrice5=?,SellPrice5=?,BuyAmount5=?,SellAmount5=?,OutAmount=?,InAmount=?,no=? ").append("WHERE commodityid=?");
				this.log.debug(localStringBuilder.toString());
				getConnection();
				for (int i = 0; i < paramList.size(); i++) {
					this.ps = this.conn.prepareStatement(localStringBuilder.toString());
					Map localMap = (Map) paramList.get(i);
					this.ps.setTimestamp(1, ((TIMESTAMP) localMap.get("CREATETIME")).timestampValue());
					this.ps.setFloat(2, ((BigDecimal) localMap.get("YESTERBALANCEPRICE")).floatValue());
					this.ps.setFloat(3, ((BigDecimal) localMap.get("CLOSEPRICE")).floatValue());
					this.ps.setFloat(4, ((BigDecimal) localMap.get("OPENPRICE")).floatValue());
					this.ps.setFloat(5, ((BigDecimal) localMap.get("HIGHPRICE")).floatValue());
					this.ps.setFloat(6, ((BigDecimal) localMap.get("LOWPRICE")).floatValue());
					this.ps.setFloat(7, ((BigDecimal) localMap.get("CURPRICE")).floatValue());
					this.ps.setInt(8, ((BigDecimal) localMap.get("CURAMOUNT")).intValue());
					long l1 = ((BigDecimal) localMap.get("OPENAMOUNT")).intValue();
					this.ps.setLong(9, l1);
					long l2 = ((BigDecimal) localMap.get("CLOSEAMOUNT")).intValue();
					this.ps.setLong(10, l2);
					this.ps.setInt(11, ((BigDecimal) localMap.get("RESERVECOUNT")).intValue());
					this.ps.setInt(12, ((BigDecimal) localMap.get("RESERVECHANGE")).intValue());
					this.ps.setFloat(13, ((BigDecimal) localMap.get("PRICE")).floatValue());
					this.ps.setDouble(14, ((BigDecimal) localMap.get("TOTALMONEY")).doubleValue());
					long l3 = ((BigDecimal) localMap.get("TOTALAMOUNT")).longValue();
					this.ps.setLong(15, l3);
					this.ps.setFloat(16, ((BigDecimal) localMap.get("BUYPRICE1")).floatValue());
					this.ps.setFloat(17, ((BigDecimal) localMap.get("SELLPRICE1")).floatValue());
					this.ps.setInt(18, ((BigDecimal) localMap.get("BUYAMOUNT1")).intValue());
					this.ps.setInt(19, ((BigDecimal) localMap.get("SELLAMOUNT1")).intValue());
					this.ps.setFloat(20, ((BigDecimal) localMap.get("BUYPRICE2")).floatValue());
					this.ps.setFloat(21, ((BigDecimal) localMap.get("SELLPRICE2")).floatValue());
					this.ps.setInt(22, ((BigDecimal) localMap.get("BUYAMOUNT2")).intValue());
					this.ps.setInt(23, ((BigDecimal) localMap.get("SELLAMOUNT2")).intValue());
					this.ps.setFloat(24, ((BigDecimal) localMap.get("BUYPRICE3")).floatValue());
					this.ps.setFloat(25, ((BigDecimal) localMap.get("SELLPRICE3")).floatValue());
					this.ps.setInt(26, ((BigDecimal) localMap.get("BUYAMOUNT3")).intValue());
					this.ps.setInt(27, ((BigDecimal) localMap.get("SELLAMOUNT3")).intValue());
					this.ps.setFloat(28, ((BigDecimal) localMap.get("BUYPRICE4")).floatValue());
					this.ps.setFloat(29, ((BigDecimal) localMap.get("SELLPRICE4")).floatValue());
					this.ps.setInt(30, ((BigDecimal) localMap.get("BUYAMOUNT4")).intValue());
					this.ps.setInt(31, ((BigDecimal) localMap.get("SELLAMOUNT4")).intValue());
					this.ps.setFloat(32, ((BigDecimal) localMap.get("BUYPRICE5")).floatValue());
					this.ps.setFloat(33, ((BigDecimal) localMap.get("SELLPRICE5")).floatValue());
					this.ps.setInt(34, ((BigDecimal) localMap.get("BUYAMOUNT5")).intValue());
					this.ps.setInt(35, ((BigDecimal) localMap.get("SELLAMOUNT5")).intValue());
					this.ps.setInt(36, ((BigDecimal) localMap.get("OUTAMOUNT")).intValue());
					this.ps.setInt(37, ((BigDecimal) localMap.get("INAMOUNT")).intValue());
					this.ps.setInt(38, ((BigDecimal) localMap.get("NO")).intValue());
					String str = (String) localMap.get("COMMODITYID");
					this.ps.setString(39, str);
					this.ps.execute();
					this.ps.close();
					PreData localPreData = (PreData) this.amountMap.get(str);
					Object localObject1;
					if (localPreData == null) {
						localPreData = new PreData();
						localObject1 = "Select MAX(Totalamount),SUM(OpenAmount),SUM(CloseAmount) from todaybilldata where commodityid=? ";
						this.ps = this.conn.prepareStatement((String) localObject1);
						this.ps.setString(1, str);
						this.rs = this.ps.executeQuery();
						if (this.rs.next()) {
							localPreData.preTotalAmount = this.rs.getLong(1);
							localPreData.preOpenAmount = this.rs.getLong(2);
							localPreData.preCloseAmount = this.rs.getLong(3);
						}
						this.rs.close();
						this.ps.close();
					}
					this.log.debug(
							"curTotalamout=" + l3 + ",preTotalAmount=" + localPreData.preTotalAmount + "curOpenAmount=" + l1 + ",preOpenAmount="
									+ localPreData.preOpenAmount + "curCloseAmount=" + l2 + ",preCloseAmount=" + localPreData.preCloseAmount);
					if (l3 > localPreData.preTotalAmount) {
						localObject1 = new StringBuilder();
						((StringBuilder) localObject1).append("INSERT INTO TodayBillData (Time,commodityid,CurPrice,OpenAmount,closeAmount,")
								.append("ReserveCount,BalancePrice,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,BuyAmount1,SellAmount1,BuyPrice2,SellPrice2,")
								.append("BuyAmount2,SellAmount2,BuyPrice3,SellPrice3,BuyAmount3,SellAmount3,TradeCue) VALUES (?");
						for (int j = 0; j < 21; j++) {
							((StringBuilder) localObject1).append(",?");
						}
						((StringBuilder) localObject1).append(")");
						this.log.debug("sql:" + localObject1);
						this.ps = this.conn.prepareStatement(((StringBuilder) localObject1).toString());
						this.ps.setTimestamp(1, ((TIMESTAMP) localMap.get("CREATETIME")).timestampValue());
						this.ps.setString(2, str);
						this.ps.setFloat(3, ((BigDecimal) localMap.get("CURPRICE")).floatValue());
						this.ps.setLong(4, l1 - localPreData.preOpenAmount);
						this.ps.setLong(5, l2 - localPreData.preCloseAmount);
						this.ps.setInt(6, ((BigDecimal) localMap.get("RESERVECOUNT")).intValue());
						this.ps.setFloat(7, ((BigDecimal) localMap.get("PRICE")).floatValue());
						this.ps.setDouble(8, ((BigDecimal) localMap.get("TOTALMONEY")).doubleValue());
						this.ps.setLong(9, l3);
						this.ps.setFloat(10, ((BigDecimal) localMap.get("BUYPRICE1")).floatValue());
						this.ps.setFloat(11, ((BigDecimal) localMap.get("SELLPRICE1")).floatValue());
						this.ps.setInt(12, ((BigDecimal) localMap.get("BUYAMOUNT1")).intValue());
						this.ps.setInt(13, ((BigDecimal) localMap.get("SELLAMOUNT1")).intValue());
						this.ps.setFloat(14, ((BigDecimal) localMap.get("BUYPRICE2")).floatValue());
						this.ps.setFloat(15, ((BigDecimal) localMap.get("SELLPRICE2")).floatValue());
						this.ps.setInt(16, ((BigDecimal) localMap.get("BUYAMOUNT2")).intValue());
						this.ps.setInt(17, ((BigDecimal) localMap.get("SELLAMOUNT2")).intValue());
						this.ps.setFloat(18, ((BigDecimal) localMap.get("BUYPRICE3")).floatValue());
						this.ps.setFloat(19, ((BigDecimal) localMap.get("SELLPRICE3")).floatValue());
						this.ps.setInt(20, ((BigDecimal) localMap.get("BUYAMOUNT3")).intValue());
						this.ps.setInt(21, ((BigDecimal) localMap.get("SELLAMOUNT3")).intValue());
						this.ps.setInt(22, ((BigDecimal) localMap.get("TRADECUE")).intValue());
						this.ps.executeUpdate();
						this.ps.close();
						localPreData.preTotalAmount = l3;
						localPreData.preOpenAmount = l1;
						localPreData.preCloseAmount = l2;
						this.amountMap.put(str, localPreData);
					}
				}
			} catch (SQLException localSQLException2) {
				localSQLException2.printStackTrace();
			} finally {
				try {
					if (this.rs != null) {
						this.rs.close();
					}
					if (this.ps != null) {
						this.ps.close();
					}
				} catch (SQLException localSQLException4) {
					localSQLException4.printStackTrace();
				}
			}
		} else {
			this.log.info("ProductData List is empty,Please check data load...");
		}
	}

	public void transferTradeTime(List<TradeTimeVO> paramList, String paramString) {
		this.log.debug("enter into the transferTradeTime Method");
		if ((paramList != null) && (paramList.size() > 0)) {
			try {
				StringBuilder localStringBuilder = new StringBuilder();
				localStringBuilder.append("INSERT INTO TRADETIME (TRADESECTION,BEGINDATE,BEGINTIME,ENDDATE,ENDTIME,STATUS,MODIFYTIME,TradeDate) ")
						.append(" VALUES (?,?,?,?,?,?,?,?)");
				getConnection();
				this.ps = this.conn.prepareStatement(localStringBuilder.toString());
				for (int i = 0; i < paramList.size(); i++) {
					this.ps.setInt(1, ((TradeTimeVO) paramList.get(i)).orderID);
					this.ps.setString(2, ((TradeTimeVO) paramList.get(i)).beginDate == 0 ? paramString
							: String.valueOf(((TradeTimeVO) paramList.get(i)).beginDate));
					this.ps.setString(3, String.valueOf(((TradeTimeVO) paramList.get(i)).beginTime * 100));
					this.ps.setString(4,
							((TradeTimeVO) paramList.get(i)).endDate == 0 ? paramString : String.valueOf(((TradeTimeVO) paramList.get(i)).endDate));
					this.ps.setString(5, String.valueOf(((TradeTimeVO) paramList.get(i)).endTime * 100));
					this.ps.setInt(6, ((TradeTimeVO) paramList.get(i)).status);
					this.ps.setTimestamp(7, (Timestamp) ((TradeTimeVO) paramList.get(i)).modifytime);
					this.ps.setString(8, paramString);
					this.ps.execute();
				}
				return;
			} catch (SQLException localSQLException2) {
				localSQLException2.printStackTrace();
			} finally {
				try {
					if (this.ps != null) {
						this.ps.close();
					}
				} catch (SQLException localSQLException4) {
					localSQLException4.printStackTrace();
				}
			}
		}
	}

	public boolean clear() {
		this.log.debug("clear yesterday quotation..");
		String str1 = "delete from COMMODITY";
		String str2 = "delete from CURRENTDATA";
		String str3 = "delete from TODAYBILLDATA";
		String str4 = "delete from TRADETIME";
		String str5 = "delete from co_class";
		try {
			getConnection();
			this.amountMap.clear();
			this.ps = this.conn.prepareStatement(str1);
			this.ps.execute();
			this.log.info("clear commodity:" + str1);
			this.ps.close();
			this.ps = this.conn.prepareStatement(str2);
			this.ps.execute();
			this.log.info("clear currentdata:" + str2);
			this.ps.close();
			this.ps = this.conn.prepareStatement(str3);
			this.ps.execute();
			this.log.info("clear todaybilldata:" + str3);
			this.ps.close();
			this.ps = this.conn.prepareStatement(str4);
			this.ps.execute();
			this.log.info("clear tradetime:" + str4);
			this.ps.close();
			this.ps = this.conn.prepareStatement(str5);
			this.ps.execute();
			this.log.info("clear co_class:" + str5);
			this.ps.close();
			boolean bool1 = true;
			return bool1;
		} catch (SQLException localSQLException1) {
			localSQLException1.printStackTrace();
			boolean bool2 = false;
			return bool2;
		} finally {
			try {
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException4) {
				localSQLException4.printStackTrace();
			}
		}
	}

	public void updateMaketStatus(int paramInt1, int paramInt2) {
		this.log.debug("update maket status...");
		String str = "Update Currentdata set tradeno=?,totalamount=? where commodityid = 'SYS'";
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.ps.setInt(1, paramInt1);
			this.ps.setInt(2, paramInt2);
			this.ps.execute();
			return;
		} catch (SQLException localSQLException2) {
			localSQLException2.printStackTrace();
		} finally {
			try {
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException4) {
				localSQLException4.printStackTrace();
			}
		}
	}

	public void initCurrentdata(List paramList, long paramLong) {
		this.log.debug("init currentdata..");
		System.out.println("初始化行情数据。。。。。。。。。。。。。。。" + paramList == null ? 123 : paramList.size());
		try {
			if ((paramList != null) && (paramList.size() > 0)) {
				StringBuilder localStringBuilder = new StringBuilder();
				localStringBuilder.append("INSERT INTO CurrentData (Time,commodityid,YesterBalancePrice,ClosePrice,OpenPrice,")
						.append("HighPrice,LowPrice,CurPrice,CurAmount,OpenAmount,closeAmount,ReserveCount,ReserveChange,")
						.append("BalancePrice,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,BuyAmount1,SellAmount1,")
						.append("BuyPrice2,SellPrice2,BuyAmount2,SellAmount2,BuyPrice3,")
						.append("SellPrice3,BuyAmount3,SellAmount3,BuyPrice4,SellPrice4,")
						.append("BuyAmount4,SellAmount4,BuyPrice5,SellPrice5,BuyAmount5,")
						.append("SellAmount5,OutAmount,InAmount,TradeCue,TradeNo) VALUES (?");
				for (int i = 0; i < 39; i++) {
					localStringBuilder.append(",?");
				}
				localStringBuilder.append(")");
				getConnection();
				this.ps = this.conn.prepareStatement(localStringBuilder.toString());
				for (int i = 0; i < paramList.size(); i++) {
					int j = 0;
					Map localMap = (Map) paramList.get(i);
					this.ps.setTimestamp(++j, ((TIMESTAMP) localMap.get("CREATETIME")).timestampValue());
					this.ps.setString(++j, (String) localMap.get("COMMODITYID"));
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("YESTERBALANCEPRICE")).floatValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("CLOSEPRICE")).floatValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("OPENPRICE")).floatValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("HIGHPRICE")).floatValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("LOWPRICE")).floatValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("CURPRICE")).floatValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("CURAMOUNT")).intValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("OPENAMOUNT")).intValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("CLOSEAMOUNT")).intValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("RESERVECOUNT")).intValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("RESERVECHANGE")).intValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("PRICE")).floatValue());
					this.ps.setDouble(++j, ((BigDecimal) localMap.get("TOTALMONEY")).doubleValue());
					this.ps.setLong(++j, ((BigDecimal) localMap.get("TOTALAMOUNT")).longValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("BUYPRICE1")).floatValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("SELLPRICE1")).floatValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("BUYAMOUNT1")).intValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("SELLAMOUNT1")).intValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("BUYPRICE2")).floatValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("SELLPRICE2")).floatValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("BUYAMOUNT2")).intValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("SELLAMOUNT2")).intValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("BUYPRICE3")).floatValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("SELLPRICE3")).floatValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("BUYAMOUNT3")).intValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("SELLAMOUNT3")).intValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("BUYPRICE4")).floatValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("SELLPRICE4")).floatValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("BUYAMOUNT4")).intValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("SELLAMOUNT4")).intValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("BUYPRICE5")).floatValue());
					this.ps.setFloat(++j, ((BigDecimal) localMap.get("SELLPRICE5")).floatValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("BUYAMOUNT5")).intValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("SELLAMOUNT5")).intValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("OUTAMOUNT")).intValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("INAMOUNT")).intValue());
					this.ps.setInt(++j, ((BigDecimal) localMap.get("TRADECUE")).intValue());
					this.ps.setInt(++j, -1);
					this.ps.addBatch();
				}
				this.ps.executeBatch();
				this.ps.close();
			}
			StringBuilder localStringBuilder = new StringBuilder();
			localStringBuilder.append("INSERT INTO CurrentData (Time,commodityid,YesterBalancePrice,ClosePrice,OpenPrice,")
					.append("HighPrice,LowPrice,CurPrice,CurAmount,OpenAmount,closeAmount,ReserveCount,ReserveChange,")
					.append("BalancePrice,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,BuyAmount1,SellAmount1,")
					.append("BuyPrice2,SellPrice2,BuyAmount2,SellAmount2,BuyPrice3,")
					.append("SellPrice3,BuyAmount3,SellAmount3,BuyPrice4,SellPrice4,")
					.append("BuyAmount4,SellAmount4,BuyPrice5,SellPrice5,BuyAmount5,")
					.append("SellAmount5,OutAmount,InAmount,TradeCue,TradeNo) VALUES (?,'SYS',0,0,0,")
					.append("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1)");
			this.log.debug(localStringBuilder.toString());
			this.ps = this.conn.prepareStatement(localStringBuilder.toString());
			this.ps.setTimestamp(1, new Timestamp(paramLong));
			this.ps.execute();
			this.ps.close();
			return;
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
		} finally {
			try {
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (Exception localException3) {
			}
		}
	}

	public void updateSysdate(long paramLong) {
		String str = "Update Currentdata set time=? where commodityid = 'SYS' ";
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.ps.setTimestamp(1, new Timestamp(paramLong));
			this.ps.execute();
			return;
		} catch (SQLException localSQLException2) {
			localSQLException2.printStackTrace();
		} finally {
			try {
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException4) {
				localSQLException4.printStackTrace();
			}
		}
	}

	public void transBidData(List paramList, String paramString) {
		if ((paramList != null) && (paramList.size() > 0)) {
			try {
				StringBuilder localStringBuilder1 = new StringBuilder();
				localStringBuilder1
						.append("UPDATE CurrentData SET Time=?,YESTERBALANCEPRICE=?,CLOSEPRICE=?,OPENPRICE=?,HighPrice=?,LowPrice=?,CurPrice=?,CurAmount=?, ")
						.append("OpenAmount=?,CLOSEAMOUNT=?,ReserveCount=?,ReserveChange=?,BalancePrice=?,TotalMoney=?,TotalAmount=?, ")
						.append("BuyPrice1=?,SellPrice1=?,BuyAmount1=?,SellAmount1=?,BuyPrice2=?,SellPrice2=?,BuyAmount2=?,SellAmount2=?, ")
						.append("BuyPrice3=?,SellPrice3=?,BuyAmount3=?,SellAmount3=?,BuyPrice4=?,SellPrice4=?,BuyAmount4=?,SellAmount4=?, ")
						.append("BuyPrice5=?,SellPrice5=?,BuyAmount5=?,SellAmount5=?,OutAmount=?,InAmount=? ").append("WHERE commodityid=?");
				this.log.debug(localStringBuilder1.toString());
				getConnection();
				for (int i = 0; i < paramList.size(); i++) {
					this.ps = this.conn.prepareStatement(localStringBuilder1.toString());
					Map localMap = (Map) paramList.get(i);
					Calendar localCalendar = Calendar.getInstance();
					localCalendar.setTime(((TIMESTAMP) localMap.get("CREATETIME")).timestampValue());
					int[] arrayOfInt = getTimeFile(paramString);
					localCalendar.set(11, arrayOfInt[0]);
					localCalendar.set(12, arrayOfInt[1]);
					localCalendar.set(13, arrayOfInt[2]);
					this.log.debug("集合竞价产生时间: " + localCalendar.getTime() + ",生成价格：" + ((BigDecimal) localMap.get("CURPRICE")).floatValue());
					this.ps.setTimestamp(1, new Timestamp(localCalendar.getTimeInMillis()));
					this.ps.setFloat(2, ((BigDecimal) localMap.get("YESTERBALANCEPRICE")).floatValue());
					this.ps.setFloat(3, ((BigDecimal) localMap.get("CLOSEPRICE")).floatValue());
					this.ps.setFloat(4, ((BigDecimal) localMap.get("OPENPRICE")).floatValue());
					this.ps.setFloat(5, ((BigDecimal) localMap.get("HIGHPRICE")).floatValue());
					this.ps.setFloat(6, ((BigDecimal) localMap.get("LOWPRICE")).floatValue());
					this.ps.setFloat(7, ((BigDecimal) localMap.get("CURPRICE")).floatValue());
					this.ps.setInt(8, ((BigDecimal) localMap.get("CURAMOUNT")).intValue());
					this.ps.setInt(9, ((BigDecimal) localMap.get("OPENAMOUNT")).intValue());
					this.ps.setInt(10, ((BigDecimal) localMap.get("CLOSEAMOUNT")).intValue());
					this.ps.setInt(11, ((BigDecimal) localMap.get("RESERVECOUNT")).intValue());
					this.ps.setInt(12, ((BigDecimal) localMap.get("RESERVECHANGE")).intValue());
					this.ps.setFloat(13, ((BigDecimal) localMap.get("PRICE")).floatValue());
					this.ps.setDouble(14, ((BigDecimal) localMap.get("TOTALMONEY")).doubleValue());
					this.ps.setLong(15, ((BigDecimal) localMap.get("TOTALAMOUNT")).longValue());
					this.ps.setFloat(16, ((BigDecimal) localMap.get("BUYPRICE1")).floatValue());
					this.ps.setFloat(17, ((BigDecimal) localMap.get("SELLPRICE1")).floatValue());
					this.ps.setInt(18, ((BigDecimal) localMap.get("BUYAMOUNT1")).intValue());
					this.ps.setInt(19, ((BigDecimal) localMap.get("SELLAMOUNT1")).intValue());
					this.ps.setFloat(20, ((BigDecimal) localMap.get("BUYPRICE2")).floatValue());
					this.ps.setFloat(21, ((BigDecimal) localMap.get("SELLPRICE2")).floatValue());
					this.ps.setInt(22, ((BigDecimal) localMap.get("BUYAMOUNT2")).intValue());
					this.ps.setInt(23, ((BigDecimal) localMap.get("SELLAMOUNT2")).intValue());
					this.ps.setFloat(24, ((BigDecimal) localMap.get("BUYPRICE3")).floatValue());
					this.ps.setFloat(25, ((BigDecimal) localMap.get("SELLPRICE3")).floatValue());
					this.ps.setInt(26, ((BigDecimal) localMap.get("BUYAMOUNT3")).intValue());
					this.ps.setInt(27, ((BigDecimal) localMap.get("SELLAMOUNT3")).intValue());
					this.ps.setFloat(28, ((BigDecimal) localMap.get("BUYPRICE4")).floatValue());
					this.ps.setFloat(29, ((BigDecimal) localMap.get("SELLPRICE4")).floatValue());
					this.ps.setInt(30, ((BigDecimal) localMap.get("BUYAMOUNT4")).intValue());
					this.ps.setInt(31, ((BigDecimal) localMap.get("SELLAMOUNT4")).intValue());
					this.ps.setFloat(32, ((BigDecimal) localMap.get("BUYPRICE5")).floatValue());
					this.ps.setFloat(33, ((BigDecimal) localMap.get("SELLPRICE5")).floatValue());
					this.ps.setInt(34, ((BigDecimal) localMap.get("BUYAMOUNT5")).intValue());
					this.ps.setInt(35, ((BigDecimal) localMap.get("SELLAMOUNT5")).intValue());
					this.ps.setInt(36, ((BigDecimal) localMap.get("OUTAMOUNT")).intValue());
					this.ps.setInt(37, ((BigDecimal) localMap.get("INAMOUNT")).intValue());
					String str = (String) localMap.get("COMMODITYID");
					this.ps.setString(38, str);
					this.ps.execute();
					this.ps.close();
					if (((BigDecimal) localMap.get("CURPRICE")).floatValue() > 0.0F) {
						StringBuilder localStringBuilder2 = new StringBuilder();
						localStringBuilder2.append("INSERT INTO TodayBillData (Time,commodityid,CurPrice,OpenAmount,closeAmount,")
								.append("ReserveCount,BalancePrice,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,BuyAmount1,SellAmount1,BuyPrice2,SellPrice2,")
								.append("BuyAmount2,SellAmount2,BuyPrice3,SellPrice3,BuyAmount3,SellAmount3,TradeCue) VALUES (?");
						for (int j = 0; j < 21; j++) {
							localStringBuilder2.append(",?");
						}
						localStringBuilder2.append(")");
						this.log.debug("写入集合竞价产生成交 Time：" + localCalendar.getTime() + ",sql:" + localStringBuilder2.toString());
						this.ps = this.conn.prepareStatement(localStringBuilder2.toString());
						this.ps.setTimestamp(1, new Timestamp(localCalendar.getTimeInMillis()));
						this.ps.setString(2, str);
						this.ps.setFloat(3, ((BigDecimal) localMap.get("CURPRICE")).floatValue());
						this.ps.setInt(4, ((BigDecimal) localMap.get("OPENAMOUNT")).intValue());
						this.ps.setInt(5, ((BigDecimal) localMap.get("CLOSEAMOUNT")).intValue());
						this.ps.setInt(6, ((BigDecimal) localMap.get("RESERVECOUNT")).intValue());
						this.ps.setFloat(7, ((BigDecimal) localMap.get("PRICE")).floatValue());
						this.ps.setDouble(8, ((BigDecimal) localMap.get("TOTALMONEY")).doubleValue());
						this.ps.setLong(9, ((BigDecimal) localMap.get("TOTALAMOUNT")).longValue());
						this.ps.setFloat(10, ((BigDecimal) localMap.get("BUYPRICE1")).floatValue());
						this.ps.setFloat(11, ((BigDecimal) localMap.get("SELLPRICE1")).floatValue());
						this.ps.setInt(12, ((BigDecimal) localMap.get("BUYAMOUNT1")).intValue());
						this.ps.setInt(13, ((BigDecimal) localMap.get("SELLAMOUNT1")).intValue());
						this.ps.setFloat(14, ((BigDecimal) localMap.get("BUYPRICE2")).floatValue());
						this.ps.setFloat(15, ((BigDecimal) localMap.get("SELLPRICE2")).floatValue());
						this.ps.setInt(16, ((BigDecimal) localMap.get("BUYAMOUNT2")).intValue());
						this.ps.setInt(17, ((BigDecimal) localMap.get("SELLAMOUNT2")).intValue());
						this.ps.setFloat(18, ((BigDecimal) localMap.get("BUYPRICE3")).floatValue());
						this.ps.setFloat(19, ((BigDecimal) localMap.get("SELLPRICE3")).floatValue());
						this.ps.setInt(20, ((BigDecimal) localMap.get("BUYAMOUNT3")).intValue());
						this.ps.setInt(21, ((BigDecimal) localMap.get("SELLAMOUNT3")).intValue());
						this.ps.setInt(22, ((BigDecimal) localMap.get("TRADECUE")).intValue());
						this.ps.execute();
						this.ps.close();
					}
				}
			} catch (SQLException localSQLException2) {
				localSQLException2.printStackTrace();
			} finally {
				try {
					if (this.ps != null) {
						this.ps.close();
					}
				} catch (SQLException localSQLException4) {
					localSQLException4.printStackTrace();
				}
			}
		} else {
			this.log.info("ProductData List is empty,Please check data load...");
		}
	}

	public static String formatStr2(String paramString) {
		String str = paramString.replaceAll("-", "");
		return str;
	}

	public static String formatStr(String paramString) {
		if ((paramString != null) && (paramString.length() > 0)) {
			String str = "";
			if (paramString.indexOf(":") != -1) {
				String[] arrayOfString = paramString.split(":");
				for (int i = 0; i < arrayOfString.length; i++) {
					str = str + arrayOfString[i];
				}
			}
			return str;
		}
		return null;
	}

	public static int[] getTimeFile(String paramString) {
		int[] arrayOfInt = new int[3];
		if (paramString.indexOf(":") != 1) {
			String[] arrayOfString = paramString.split(":");
			for (int i = 0; i < arrayOfString.length; i++) {
				arrayOfInt[i] = Integer.valueOf(arrayOfString[i]).intValue();
			}
		}
		return arrayOfInt;
	}

	protected void finalize() throws Throwable {
		try {
			this.rs.close();
		} catch (Exception localException1) {
		}
		try {
			this.ps.close();
		} catch (Exception localException2) {
		}
		try {
			this.conn.close();
		} catch (Exception localException3) {
		}
		super.finalize();
	}

	public Date getHQSysTime() {
		String str = "select time from currentdata where commodityid='SYS'";
		Date localObject1 = new Date();
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.rs = this.ps.executeQuery();
			if (this.rs.next()) {
				localObject1 = this.rs.getTimestamp(1);
			} else {
				localObject1 = new Date();
			}
			this.rs.close();
			this.ps.close();
		} catch (SQLException localSQLException2) {
			localSQLException2.printStackTrace();
		} finally {
			try {
				if (this.rs != null) {
					this.rs.close();
				}
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException4) {
				localSQLException4.printStackTrace();
			}
		}
		return (Date) localObject1;
	}

	public String getHQDate() {
		String str1 = "select MAX(TradeDate) from TradeTime";
		String str2 = null;
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str1);
			this.rs = this.ps.executeQuery();
			if (this.rs.next()) {
				str2 = this.rs.getString(1);
			}
			this.rs.close();
			this.ps.close();
		} catch (SQLException localSQLException2) {
			localSQLException2.printStackTrace();
		} finally {
			try {
				if (this.rs != null) {
					this.rs.close();
				}
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException4) {
				localSQLException4.printStackTrace();
			}
		}
		return str2;
	}

	public void addOneComty(Map paramMap, long paramLong, String paramString) {
		StringBuilder localStringBuilder1 = new StringBuilder();
		localStringBuilder1.append("insert into Commodity (commodityid,fullname,status,tradingsec,type,ctrtsize,")
				.append("spread, maxspread,tradecomm,settlecomm,cashcomm,margin,ttlopen,BalancePrice,listdate,")
				.append("delistdate,curchange,maxholding,tradeno,modifytime) values (?");
		for (int i = 0; i < 19; i++) {
			localStringBuilder1.append(",?");
		}
		localStringBuilder1.append(")");
		this.log.debug("insert one commodity sql:" + localStringBuilder1.toString());
		StringBuilder localStringBuilder2 = new StringBuilder();
		localStringBuilder2.append("insert into co_class (cc_classid,Market,cc_name,Cc_commodity_id) values ").append("(?,?,?,?)");
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(localStringBuilder1.toString());
			int j = 0;
			String str = (String) paramMap.get("COMMODITYID");
			this.ps.setString(++j, str);
			this.ps.setString(++j, (String) paramMap.get("NAME"));
			this.ps.setString(++j, (String) paramMap.get("STATUS"));
			this.ps.setString(++j, paramString);
			this.ps.setInt(++j, 0);
			this.ps.setDouble(++j, ((BigDecimal) paramMap.get("CONTRACTFACTOR")).doubleValue());
			this.ps.setDouble(++j, ((BigDecimal) paramMap.get("MINPRICEMOVE")).doubleValue());
			this.ps.setDouble(++j, ((BigDecimal) paramMap.get("MAXSPREAD")).doubleValue());
			this.ps.setDouble(++j, ((BigDecimal) paramMap.get("FEERATE_B")).doubleValue());
			this.ps.setDouble(++j, ((BigDecimal) paramMap.get("SETTLEFEERATE_B")).doubleValue());
			this.ps.setDouble(++j, ((BigDecimal) paramMap.get("FORCECLOSEFEERATE_B")).doubleValue());
			this.ps.setDouble(++j, ((BigDecimal) paramMap.get("MARGINRATE_B")).doubleValue());
			this.ps.setInt(++j, ((BigDecimal) paramMap.get("RESERVECOUNT")).intValue());
			this.ps.setDouble(++j, ((BigDecimal) paramMap.get("LASTPRICE")).doubleValue());
			this.ps.setString(++j, (String) paramMap.get("MARKETDATE"));
			this.ps.setString(++j, (String) paramMap.get("SETTLEDATE"));
			this.ps.setInt(++j, 0);
			this.ps.setInt(++j, ((BigDecimal) paramMap.get("FIRMMAXHOLDQTY")).intValue());
			this.ps.setInt(++j, 0);
			this.ps.setTimestamp(++j, new Timestamp(paramLong));
			this.ps.execute();
			if (paramMap.get("BREEDID") != null) {
				this.log.debug("insert co_class sql : " + localStringBuilder2.toString());
				this.ps = this.conn.prepareStatement(localStringBuilder2.toString());
				this.ps.setString(1, ((BigDecimal) paramMap.get("BREEDID")).toString());
				this.ps.setString(2, "null");
				this.ps.setString(3, (String) paramMap.get("BREEDNAME"));
				this.ps.setString(4, str);
				this.ps.execute();
				this.ps.close();
				this.ps = null;
			}
			return;
		} catch (SQLException localSQLException2) {
			localSQLException2.printStackTrace();
		} finally {
			try {
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException4) {
				localSQLException4.printStackTrace();
			}
		}
	}

	public void addOneData(Quotation paramQuotation) {
		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder.append("INSERT INTO CurrentData (Time,commodityid,YesterBalancePrice,ClosePrice,OpenPrice,")
				.append("HighPrice,LowPrice,CurPrice,CurAmount,OpenAmount,closeAmount,ReserveCount,ReserveChange,")
				.append("BalancePrice,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,BuyAmount1,SellAmount1,")
				.append("BuyPrice2,SellPrice2,BuyAmount2,SellAmount2,BuyPrice3,").append("SellPrice3,BuyAmount3,SellAmount3,BuyPrice4,SellPrice4,")
				.append("BuyAmount4,SellAmount4,BuyPrice5,SellPrice5,BuyAmount5,")
				.append("SellAmount5,OutAmount,InAmount,TradeCue,TradeNo,no) VALUES (?");
		for (int i = 0; i < 40; i++) {
			localStringBuilder.append(",?");
		}
		localStringBuilder.append(")");
		this.log.debug("addOneData" + localStringBuilder.toString());
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(localStringBuilder.toString());
			int i = 0;
			this.ps.setTimestamp(++i, paramQuotation.getCreateTime());
			this.ps.setString(++i, paramQuotation.getCommodityID());
			this.ps.setDouble(++i, paramQuotation.getYesterBalancePrice().doubleValue());
			this.ps.setDouble(++i, paramQuotation.getClosePrice().doubleValue());
			this.ps.setDouble(++i, paramQuotation.getOpenPrice().doubleValue());
			this.ps.setDouble(++i, paramQuotation.getHighPrice().doubleValue());
			this.ps.setDouble(++i, paramQuotation.getLowPrice().doubleValue());
			this.ps.setDouble(++i, paramQuotation.getCurPrice().doubleValue());
			this.ps.setLong(++i, paramQuotation.getCurAmount().longValue());
			this.ps.setLong(++i, paramQuotation.getOpenAmount().longValue());
			this.ps.setLong(++i, paramQuotation.getCloseAmount().longValue());
			this.ps.setLong(++i, paramQuotation.getReserveCount().longValue());
			this.ps.setLong(++i, paramQuotation.getReserveChange().longValue());
			this.ps.setDouble(++i, paramQuotation.getPrice().doubleValue());
			this.ps.setDouble(++i, paramQuotation.getTotalMoney().doubleValue());
			this.ps.setLong(++i, paramQuotation.getTotalAmount().longValue());
			this.ps.setDouble(++i, paramQuotation.buy[0]);
			this.ps.setDouble(++i, paramQuotation.sell[0]);
			this.ps.setLong(++i, paramQuotation.buyqty[0]);
			this.ps.setLong(++i, paramQuotation.sellqty[0]);
			this.ps.setDouble(++i, paramQuotation.buy[1]);
			this.ps.setDouble(++i, paramQuotation.sell[1]);
			this.ps.setLong(++i, paramQuotation.buyqty[1]);
			this.ps.setLong(++i, paramQuotation.sellqty[1]);
			this.ps.setDouble(++i, paramQuotation.buy[2]);
			this.ps.setDouble(++i, paramQuotation.sell[2]);
			this.ps.setLong(++i, paramQuotation.buyqty[2]);
			this.ps.setLong(++i, paramQuotation.sellqty[2]);
			this.ps.setDouble(++i, paramQuotation.buy[3]);
			this.ps.setDouble(++i, paramQuotation.sell[3]);
			this.ps.setLong(++i, paramQuotation.buyqty[3]);
			this.ps.setLong(++i, paramQuotation.sellqty[3]);
			this.ps.setDouble(++i, paramQuotation.buy[4]);
			this.ps.setDouble(++i, paramQuotation.sell[4]);
			this.ps.setLong(++i, paramQuotation.buyqty[4]);
			this.ps.setLong(++i, paramQuotation.sellqty[4]);
			this.ps.setLong(++i, paramQuotation.getOutAmount().longValue());
			this.ps.setLong(++i, paramQuotation.getInAmount().longValue());
			this.ps.setLong(++i, paramQuotation.getTradeCue().longValue());
			this.ps.setInt(++i, -1);
			this.ps.setLong(++i, paramQuotation.getNo().longValue());
			this.ps.execute();
			this.ps.close();
			return;
		} catch (SQLException localSQLException2) {
			localSQLException2.printStackTrace();
		} finally {
			try {
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException4) {
				localSQLException4.printStackTrace();
			}
		}
	}

	/**
	 * @deprecated
	 */
	public void addOneData(Map paramMap) {
		StringBuilder localStringBuilder = new StringBuilder();
		localStringBuilder.append("INSERT INTO CurrentData (Time,commodityid,YesterBalancePrice,ClosePrice,OpenPrice,")
				.append("HighPrice,LowPrice,CurPrice,CurAmount,OpenAmount,closeAmount,ReserveCount,ReserveChange,")
				.append("BalancePrice,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,BuyAmount1,SellAmount1,")
				.append("BuyPrice2,SellPrice2,BuyAmount2,SellAmount2,BuyPrice3,").append("SellPrice3,BuyAmount3,SellAmount3,BuyPrice4,SellPrice4,")
				.append("BuyAmount4,SellAmount4,BuyPrice5,SellPrice5,BuyAmount5,")
				.append("SellAmount5,OutAmount,InAmount,TradeCue,TradeNo) VALUES (?");
		for (int i = 0; i < 39; i++) {
			localStringBuilder.append(",?");
		}
		localStringBuilder.append(")");
		this.log.debug("addOneData" + localStringBuilder.toString());
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(localStringBuilder.toString());
			int i = 0;
			this.ps.setTimestamp(++i, ((TIMESTAMP) paramMap.get("CREATETIME")).timestampValue());
			this.ps.setString(++i, (String) paramMap.get("COMMODITYID"));
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("YESTERBALANCEPRICE")).floatValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("CLOSEPRICE")).floatValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("OPENPRICE")).floatValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("HIGHPRICE")).floatValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("LOWPRICE")).floatValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("CURPRICE")).floatValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("CURAMOUNT")).intValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("OPENAMOUNT")).intValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("CLOSEAMOUNT")).intValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("RESERVECOUNT")).intValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("RESERVECHANGE")).intValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("PRICE")).floatValue());
			this.ps.setDouble(++i, ((BigDecimal) paramMap.get("TOTALMONEY")).doubleValue());
			this.ps.setLong(++i, ((BigDecimal) paramMap.get("TOTALAMOUNT")).longValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("BUYPRICE1")).floatValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("SELLPRICE1")).floatValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("BUYAMOUNT1")).intValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("SELLAMOUNT1")).intValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("BUYPRICE2")).floatValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("SELLPRICE2")).floatValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("BUYAMOUNT2")).intValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("SELLAMOUNT2")).intValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("BUYPRICE3")).floatValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("SELLPRICE3")).floatValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("BUYAMOUNT3")).intValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("SELLAMOUNT3")).intValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("BUYPRICE4")).floatValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("SELLPRICE4")).floatValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("BUYAMOUNT4")).intValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("SELLAMOUNT4")).intValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("BUYPRICE5")).floatValue());
			this.ps.setFloat(++i, ((BigDecimal) paramMap.get("SELLPRICE5")).floatValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("BUYAMOUNT5")).intValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("SELLAMOUNT5")).intValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("OUTAMOUNT")).intValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("INAMOUNT")).intValue());
			this.ps.setInt(++i, ((BigDecimal) paramMap.get("TRADECUE")).intValue());
			this.ps.setInt(++i, -1);
			this.ps.execute();
			this.ps.close();
			return;
		} catch (SQLException localSQLException2) {
			localSQLException2.printStackTrace();
		} finally {
			try {
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException4) {
				localSQLException4.printStackTrace();
			}
		}
	}

	public void loadCodeSet(HashSet paramHashSet) {
		String str = "select commodityid from commodity ";
		this.log.debug("loadCodeSet sql" + str);
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.rs = this.ps.executeQuery();
			while (this.rs.next()) {
				paramHashSet.add(this.rs.getString("commodityid"));
			}
			return;
		} catch (SQLException localSQLException2) {
			localSQLException2.printStackTrace();
		} finally {
			try {
				if (this.rs != null) {
					this.rs.close();
				}
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException4) {
				localSQLException4.printStackTrace();
			}
		}
	}

	public boolean checkTradeSec(List paramList, String paramString) {
		this.log.debug("初始化交易节...");
		String str = "select count(*) from TradeTime";
		boolean bool = false;
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.rs = this.ps.executeQuery();
			if (this.rs.next()) {
				bool = this.rs.getInt(1) > 0;
			}
			this.ps = null;
			this.rs = null;
			if (!bool) {
				transferTradeTime(paramList, paramString);
			} else {
				this.ps = this.conn.prepareStatement("delete from TradeTime");
				this.ps.execute();
				this.ps = null;
				transferTradeTime(paramList, paramString);
			}
		} catch (SQLException localSQLException2) {
			localSQLException2.printStackTrace();
		} finally {
			try {
				if (this.rs != null) {
					this.rs.close();
				}
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException4) {
				localSQLException4.printStackTrace();
			}
		}
		return bool;
	}

	public boolean checkSYSInfo(long paramLong) {
		this.log.debug("check SYS info...");
		String str = "select count(*) from currentdata where commodityid='SYS' ";
		boolean bool = false;
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.rs = this.ps.executeQuery();
			if (this.rs.next()) {
				bool = this.rs.getInt(1) > 0;
			}
			this.ps = null;
			this.rs = null;
			if (!bool) {
				StringBuilder localStringBuilder = new StringBuilder();
				localStringBuilder.append("INSERT INTO CurrentData (Time,commodityid,YesterBalancePrice,ClosePrice,OpenPrice,")
						.append("HighPrice,LowPrice,CurPrice,CurAmount,OpenAmount,closeAmount,ReserveCount,ReserveChange,")
						.append("BalancePrice,TotalMoney,TotalAmount,BuyPrice1,SellPrice1,BuyAmount1,SellAmount1,")
						.append("BuyPrice2,SellPrice2,BuyAmount2,SellAmount2,BuyPrice3,")
						.append("SellPrice3,BuyAmount3,SellAmount3,BuyPrice4,SellPrice4,")
						.append("BuyAmount4,SellAmount4,BuyPrice5,SellPrice5,BuyAmount5,")
						.append("SellAmount5,OutAmount,InAmount,TradeCue,TradeNo) VALUES (?,'SYS',0,0,0,")
						.append("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1)");
				this.ps.setTimestamp(1, new Timestamp(paramLong));
				this.ps.execute();
				this.ps.close();
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return bool;
	}

	public void updateCommoditySec(HashMap<String, String> paramHashMap) {
		String str1 = "update commodity set tradingsec = ? where commodityid = ?";
		this.log.debug("updateCommoditySec sql :" + str1);
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str1);
			Set localSet = paramHashMap.keySet();
			Iterator localIterator = localSet.iterator();
			while (localIterator.hasNext()) {
				String str2 = (String) localIterator.next();
				String str3 = (String) paramHashMap.get(str2);
				this.ps.setString(1, str3);
				this.ps.setString(2, str2);
				this.ps.executeUpdate();
			}
			return;
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			try {
				this.ps.close();
			} catch (SQLException localSQLException3) {
				localSQLException3.printStackTrace();
			}
		}
	}

	public void insertHisDayData(String paramString, Map paramMap) {
		String str = "INSERT INTO HistoryDayData (TradeDate,commodityid,OpenPrice,HighPrice,LowPrice,ClosePrice,BalancePrice,TotalAmount,TotalMoney,ReserveCount) VALUES(?,?,?,?,?,?,?,?,?,?)";
		this.log.debug("insertHisDayData sql :" + str);
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			Date localDate = new SimpleDateFormat("yyyyMMdd").parse(paramString);
			this.ps.setTimestamp(1, new Timestamp(localDate.getTime()));
			this.ps.setString(2, (String) paramMap.get("COMMODITYID"));
			if (((BigDecimal) paramMap.get("TOTALAMOUNT")).longValue() > 0L) {
				this.ps.setFloat(3, ((BigDecimal) paramMap.get("OPENPRICE")).floatValue());
				this.ps.setFloat(4, ((BigDecimal) paramMap.get("HIGHPRICE")).floatValue());
				this.ps.setFloat(5, ((BigDecimal) paramMap.get("LOWPRICE")).floatValue());
				this.ps.setFloat(6, ((BigDecimal) paramMap.get("CURPRICE")).floatValue());
				this.ps.setFloat(7, ((BigDecimal) paramMap.get("PRICE")).floatValue());
			} else {
				this.ps.setFloat(3, ((BigDecimal) paramMap.get("YESTERBALANCEPRICE")).floatValue());
				this.ps.setFloat(4, ((BigDecimal) paramMap.get("YESTERBALANCEPRICE")).floatValue());
				this.ps.setFloat(5, ((BigDecimal) paramMap.get("YESTERBALANCEPRICE")).floatValue());
				this.ps.setFloat(6, ((BigDecimal) paramMap.get("YESTERBALANCEPRICE")).floatValue());
				this.ps.setFloat(7, ((BigDecimal) paramMap.get("YESTERBALANCEPRICE")).floatValue());
			}
			this.ps.setLong(8, ((BigDecimal) paramMap.get("TOTALAMOUNT")).longValue());
			this.ps.setFloat(9, ((BigDecimal) paramMap.get("TOTALMONEY")).floatValue());
			this.ps.setInt(10, ((BigDecimal) paramMap.get("RESERVECOUNT")).intValue());
			this.ps.executeUpdate();
			return;
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			try {
				this.ps.close();
			} catch (SQLException localSQLException3) {
				localSQLException3.printStackTrace();
			}
		}
	}

	public void insertHisBillData() {
		String str = "INSERT INTO HistoryBillData ( SELECT * FROM TodayBillData )";
		this.log.debug("insertHisBillData sql :" + str);
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.ps.executeUpdate();
			return;
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			try {
				this.ps.close();
			} catch (SQLException localSQLException3) {
				localSQLException3.printStackTrace();
			}
		}
	}

	public boolean isBackUp() {
		String str = "Select totalAmount From CurrentData Where commodityid='SYS' ";
		this.log.debug("isBackUp sql :" + str);
		boolean bool = false;
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.rs = this.ps.executeQuery();
			if (this.rs.next()) {
				bool = this.rs.getInt(1) > 0;
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			try {
				if (this.ps != null) {
					this.ps.close();
				}
				if (this.rs != null) {
					this.rs.close();
				}
			} catch (SQLException localSQLException3) {
				localSQLException3.printStackTrace();
			}
		}
		return bool;
	}

	public float queryProductUnit(String paramString) {
		float f = 1.0F;
		try {
			getConnection();
			this.ps = this.conn.prepareStatement("SELECT CtrtSize FROM Commodity WHERE commodityid =  ? ");
			this.ps.setString(1, paramString);
			this.rs = this.ps.executeQuery();
			if (this.rs.next()) {
				f = this.rs.getFloat("CtrtSize");
			}
		} catch (Exception localException) {
			System.out.println("Get Product CtrtSize Error: ");
			localException.printStackTrace();
		} finally {
			try {
				if (this.ps != null) {
					this.ps.close();
				}
				if (this.rs != null) {
					this.rs.close();
				}
			} catch (SQLException localSQLException3) {
				localSQLException3.printStackTrace();
			}
		}
		return f;
	}

	public ArrayList<BillDataVO> queryBillData(String paramString) {
		ArrayList localArrayList = new ArrayList();
		try {
			getConnection();
			this.ps = this.conn.prepareStatement("SELECT * FROM TodayBillData WHERE commodityid =  ? order by time");
			this.ps.setString(1, paramString);
			this.rs = this.ps.executeQuery();
			while (this.rs.next()) {
				BillDataVO localBillDataVO = new BillDataVO();
				Timestamp localTimestamp = this.rs.getTimestamp("TIME");
				localBillDataVO.time = (localTimestamp.getHours() * 10000 + localTimestamp.getMinutes() * 100 + localTimestamp.getSeconds());
				localBillDataVO.date = ((localTimestamp.getYear() + 1900) * 10000 + (localTimestamp.getMonth() + 1) * 100 + localTimestamp.getDate());
				localBillDataVO.curPrice = this.rs.getFloat("CurPrice");
				localBillDataVO.balancePrice = this.rs.getFloat("BalancePrice");
				localBillDataVO.totalAmount = this.rs.getInt("TotalAmount");
				localBillDataVO.totalMoney = this.rs.getFloat("TotalMoney");
				localBillDataVO.reserveCount = this.rs.getInt("ReserveCount");
				localBillDataVO.buyPrice = this.rs.getFloat("BuyPrice1");
				localBillDataVO.sellPrice = this.rs.getFloat("SellPrice1");
				localArrayList.add(localBillDataVO);
			}
		} catch (Exception localException) {
			System.out.println("Get Product CtrtSize Error: ");
			localException.printStackTrace();
		} finally {
			try {
				if (this.ps != null) {
					this.ps.close();
				}
				if (this.rs != null) {
					this.rs.close();
				}
			} catch (SQLException localSQLException3) {
				localSQLException3.printStackTrace();
			}
		}
		return localArrayList;
	}

	public void insertHisMinKLine(String paramString, KlineVO paramKlineVO) {
		if (paramKlineVO.getMinKLineList().length == 0) {
			return;
		}
		try {
			getConnection();
			String str1 = paramKlineVO.getHisMinTable();
			String str2 = "INSERT INTO  " + str1 + "(TradeDate,CommodityID,OpenPrice,HighPrice,LowPrice,"
					+ "ClosePrice,BalancePrice,TotalAmount,TotalMoney,ReserveCount) " + "VALUES(?,?,?,?,?,?,?,?,?,?)";
			this.ps = this.conn.prepareStatement(str2);
			System.out.println("sql:" + str2);
			for (int i = 0; i < paramKlineVO.getMinKLineList().length; i++) {
				System.out.println("time: " + paramKlineVO.getMinKLineList()[i].getTime().toLocaleString() + "code: " + paramString + "totalAmount: "
						+ paramKlineVO.getMinKLineList()[i].getTotalAmount());
				this.ps.setTimestamp(1, new Timestamp(paramKlineVO.getMinKLineList()[i].getTime().getTime()));
				this.ps.setString(2, paramString);
				this.ps.setFloat(3, paramKlineVO.getMinKLineList()[i].getOpenPrice());
				this.ps.setFloat(4, paramKlineVO.getMinKLineList()[i].getHighPrice());
				this.ps.setFloat(5, paramKlineVO.getMinKLineList()[i].getLowPrice());
				this.ps.setFloat(6, paramKlineVO.getMinKLineList()[i].getClosePrice());
				this.ps.setFloat(7, paramKlineVO.getMinKLineList()[i].getBalancePrice());
				this.ps.setLong(8, paramKlineVO.getMinKLineList()[i].getTotalAmount());
				this.ps.setDouble(9, paramKlineVO.getMinKLineList()[i].getTotalMoney());
				this.ps.setLong(10, paramKlineVO.getMinKLineList()[i].getReserveCount());
				this.ps.executeUpdate();
			}
			return;
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			try {
				if (this.ps != null) {
					this.ps.close();
				}
			} catch (SQLException localSQLException3) {
				localSQLException3.printStackTrace();
			}
		}
	}

	public long getPreReserveCount(String paramString) {
		long l = 0L;
		String str = "select ReserveCount from (select ReserveCount from historyDayData where commodityid=? order by tradeDate desc) where rownum<2 ";
		try {
			getConnection();
			this.ps = this.conn.prepareStatement(str);
			this.ps.setString(1, paramString);
			System.out.println("sql:" + str);
			this.rs = this.ps.executeQuery();
			if (this.rs.next()) {
				l = this.rs.getLong("ReserveCount");
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			try {
				if (this.ps != null) {
					this.ps.close();
				}
				if (this.rs != null) {
					this.rs.close();
				}
			} catch (SQLException localSQLException3) {
				localSQLException3.printStackTrace();
			}
		}
		return l;
	}

	public static void main(String[] paramArrayOfString) {
		String str = "08:50:00";
		System.out.println(formatStr(str));
		Config localConfig = new Config();
		DBTransferImpl localDBTransferImpl = new DBTransferImpl(localConfig);
		int[] arrayOfInt = getTimeFile(str);
		System.out.println(arrayOfInt[0]);
		System.out.println(arrayOfInt[1]);
		System.out.println(arrayOfInt[2]);
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(11, arrayOfInt[0]);
		localCalendar.set(12, arrayOfInt[1]);
		localCalendar.set(13, arrayOfInt[2]);
		System.out.println(localCalendar.getTime());
		System.out.println(localCalendar.get(11) * 10000 + localCalendar.get(12) * 100 + localCalendar.get(13));
		System.out.println(Integer.parseInt(formatStr(str)));
	}

	public void transBreadData() {
	}
}
