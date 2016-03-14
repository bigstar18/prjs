package gnnt.MEBS.vendue.server;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import gnnt.MEBS.vendue.server.vo.BargainVO;
import gnnt.MEBS.vendue.server.vo.BroadcastVO;
import gnnt.MEBS.vendue.server.vo.CommodityVO;
import gnnt.MEBS.vendue.server.vo.FundInfoValue;
import gnnt.MEBS.vendue.server.vo.OrderValue;
import gnnt.MEBS.vendue.server.vo.TradeStatusValue;
import gnnt.MEBS.vendue.server.vo.TradeUserVO;
import gnnt.MEBS.vendue.server.vo.TradeValue;
import gnnt.MEBS.vendue.util.Configuration;
import gnnt.MEBS.vendue.util.TranPass;
import oracle.jdbc.OracleResultSet;
import oracle.sql.CLOB;

public class SubmitActionImplBID extends SubmitActionImpl {
	public Vector getSubmit_v(String paramString) throws SQLException {
		Vector localVector = new Vector();
		OrderValue localOrderValue = null;
		Connection localConnection = null;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		String str = "select id, code, price, amount, userid, submittime,validAmount,modifytime,totalAmount from v_cursubmit where traderID='"
				+ paramString + "' and tradepartition=" + this.PARTITIONID + " order by modifytime desc";
		try {
			localConnection = this.TRADEDAO.getConnection();
			System.out.println(str);
			localPreparedStatement = localConnection.prepareStatement(str);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				localOrderValue = new OrderValue();
				localOrderValue.amount = localResultSet.getLong("amount");
				localOrderValue.code = localResultSet.getString("code");
				localOrderValue.id = localResultSet.getLong("id");
				localOrderValue.price = localResultSet.getDouble("price");
				localOrderValue.submitTime = localResultSet.getTimestamp("submitTime");
				localOrderValue.userID = localResultSet.getString("userID");
				localOrderValue.validAmount = localResultSet.getLong("validAmount");
				localOrderValue.modifyTime = localResultSet.getTimestamp("modifytime");
				localOrderValue.totalAmount = localResultSet.getLong("totalAmount");
				localVector.add(localOrderValue);
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement, localConnection);
		}
		if (localVector == null) {
			localVector = new Vector();
		}
		return localVector;
	}

	public String getLastXML(String paramString1, String paramString2) throws SQLException {
		String str1 = "";
		String str2 = "";
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
		DecimalFormat localDecimalFormat = (DecimalFormat) NumberFormat.getNumberInstance();
		localDecimalFormat.applyPattern("###0.00");
		Connection localConnection = null;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		String str3 = "select * from v_cursubmit where To_date('" + paramString2 + "','yyyy-MM-dd/HH24:mi:ss')<modifytime and traderid='"
				+ paramString1 + "' and tradepartition=" + this.PARTITIONID + " order by modifytime desc";
		try {
			localConnection = this.TRADEDAO.getConnection();
			System.out.println(str3);
			localPreparedStatement = localConnection.prepareStatement(str3);
			localResultSet = localPreparedStatement.executeQuery();
			StringBuffer localStringBuffer = new StringBuffer();
			localStringBuffer.append("<R>");
			while (localResultSet.next()) {
				if (str2.equals("")) {
					str2 = localSimpleDateFormat.format(localResultSet.getTimestamp("modifytime"));
				}
				localStringBuffer.append("<S>");
				localStringBuffer.append("<SID>" + localResultSet.getLong("id") + "</SID>");
				localStringBuffer.append("<C>" + localResultSet.getString("code") + "</C>");
				localStringBuffer.append("<P>" + localDecimalFormat.format(localResultSet.getDouble("price")) + "</P>");
				localStringBuffer.append("<A>" + localResultSet.getLong("amount") + "</A>");
				localStringBuffer.append("<VA>" + localResultSet.getLong("validAmount") + "</VA>");
				localStringBuffer.append("<ST>" + localSimpleDateFormat.format(localResultSet.getTimestamp("submittime")) + "</ST>");
				localStringBuffer.append("<MT>" + localSimpleDateFormat.format(localResultSet.getTimestamp("modifytime")) + "</MT>");
				localStringBuffer.append("<Z>" + "0" + "</Z>");
				localStringBuffer.append("</S>");
			}
			if (str2.equals("")) {
				str2 = paramString2;
			}
			localStringBuffer.append("<T>" + str2 + "</T>");
			localStringBuffer.append("</R>");
			str1 = localStringBuffer.toString();
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement, localConnection);
		}
		return str1;
	}

	public Vector getResult(String paramString) throws SQLException {
		Vector localVector = new Vector();
		TradeValue localTradeValue = null;
		Connection localConnection = null;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		String str = "select b.submitid, b.code, b.contractid, b.price, b.amount amount, b.userid, b.tradedate, b.section,b.b_bail,b.s_bail,b.b_poundage,b.s_poundage,b.status,c.amount amountTotal from v_bargain b,v_commodity c where submitID in (select id from v_curSubmit where traderId='"
				+ paramString + "') and b.tradepartition=" + this.PARTITIONID + " and b.commodityID=c.id order by contractID desc ";
		try {
			localConnection = this.TRADEDAO.getConnection();
			localPreparedStatement = localConnection.prepareStatement(str);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				localTradeValue = new TradeValue();
				localTradeValue.amount = localResultSet.getLong("amount");
				localTradeValue.b_bail = localResultSet.getDouble("b_bail");
				localTradeValue.s_bail = localResultSet.getDouble("s_bail");
				localTradeValue.code = localResultSet.getString("code");
				localTradeValue.contractID = localResultSet.getLong("contractid");
				localTradeValue.b_poundage = localResultSet.getDouble("b_poundage");
				localTradeValue.s_poundage = localResultSet.getDouble("s_poundage");
				localTradeValue.price = localResultSet.getDouble("price");
				localTradeValue.section = localResultSet.getInt("section");
				localTradeValue.submitID = localResultSet.getLong("submitid");
				localTradeValue.tradeDate = localResultSet.getTimestamp("tradedate");
				localTradeValue.userID = localResultSet.getString("userid");
				localTradeValue.status = localResultSet.getInt("status");
				localTradeValue.amountTotal = localResultSet.getLong("amountTotal");
				localVector.add(localTradeValue);
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement, localConnection);
		}
		return localVector;
	}

	public int submit(String paramString1, String paramString2, String paramString3, double paramDouble, long paramLong, String paramString4)
			throws Exception, SQLException, ClassNotFoundException, IllegalAccessException, NamingException, InstantiationException {
		KernelEngineBID localKernelEngineBID = (KernelEngineBID) GlobalContainer.getEngine(this.PARTITIONID);
		Logger localLogger = Logger.getLogger("Submitlog");
		int i = 1;
		int j = 1;
		try {
			i = Integer.parseInt(new Configuration().getSection("MEBS.TradeParams").getProperty("IsShowPrice"));
			j = Integer.parseInt(new Configuration().getSection("MEBS.TradeParams").getProperty("IsClew"));
		} catch (Exception localException1) {
			localException1.printStackTrace();
		}
		localLogger.debug("order start");
		CommodityVO localCommodityVO1 = localKernelEngineBID.getCurCommodity(paramString3);
		CallableStatement localCallableStatement = null;
		if ((localCommodityVO1 == null) || (localCommodityVO1.bargainFlag == 1) || (localCommodityVO1.bargainFlag == 2)
				|| (localCommodityVO1.section != localKernelEngineBID.getTradeStatus().getLastPartID())) {
			return 5;
		}
		Configuration localConfiguration = new Configuration();
		int k = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("Myself"));
		if ((k == 0) && (localCommodityVO1.userID.equals(paramString1))) {
			return 10;
		}
		int m = (int) localCommodityVO1.stepPrice;
		System.out.println("IsShowPrice:" + i);
		System.out.println("IsClew:" + j);
		BigDecimal localBigDecimal1 = new BigDecimal(paramDouble).setScale(10, 4);
		BigDecimal localBigDecimal2 = new BigDecimal(localCommodityVO1.beginPrice).setScale(10, 4);
		BigDecimal localBigDecimal3 = new BigDecimal(m).setScale(10, 4);
		if ((i != 0) || (j != 0)) {
			System.out.println("进入");
			if (((int) paramDouble > (int) localCommodityVO1.beginPrice) || ((int) paramDouble < (int) localCommodityVO1.alertPrice)
					|| (localBigDecimal1.subtract(localBigDecimal2).remainder(localBigDecimal3).doubleValue() > 0.0D)) {
				System.out.println("有误");
				return 1;
			}
		}
		if ((paramLong < 1L) || (paramLong > localCommodityVO1.totalAmount)) {
			return 7;
		}
		if (localKernelEngineBID.getNewCountdown(paramString3) == 0) {
			return 3;
		}
		if (localKernelEngineBID.getFirmSubmitCount(paramString1, paramString3) != 0) {
			return 4;
		}
		int n = -1;
		CommodityVO localCommodityVO2 = localCommodityVO1;
		synchronized (localCommodityVO1) {
			localLogger.debug("lock tcm:" + localCommodityVO1.code + "_" + paramString1);
			int i1 = 0;
			int i2 = localKernelEngineBID.getNewCountdown(paramString3);
			try {
				FundInfoValue localFundInfoValue = localKernelEngineBID.getFundInfo(paramString1);
				if ((paramLong > localCommodityVO1.totalAmount) || (paramLong < 1L)) {
					n = 7;
				} else if ((localFundInfoValue == null) || (localKernelEngineBID.getFirmSubmitCount(paramString1, paramString3) != 0)) {
					n = 4;
				} else if (i2 == 0) {
					n = 3;
				} else {
					double d1 = 0.0D;
					double d2 = 1.0D;
					double d3 = 1.0D;
					double d4 = 0.0D;
					double d5 = 0.0D;
					double d6 = 0.0D;
					double d7 = 0.0D;
					long l1 = 0L;
					double d8 = 0.0D;
					double d9 = 0.0D;
					long l2 = 0L;
					Connection localConnection = null;
					PreparedStatement localPreparedStatement = null;
					ResultSet localResultSet = null;
					String str = "";
					try {
						synchronized (this.TRADELOCK) {
							localConnection = this.TRADEDAO.getConnection();
							localConnection.setAutoCommit(false);
							localLogger.debug("lock TRADELOCK : " + paramString1 + "_" + localCommodityVO1.code);
							TradeUserVO localTradeUserVO = this.TRADEDAO.getTradeUserForUpdate(paramString1, localConnection);
							d6 = localKernelEngineBID.getCommodityCharge(paramString3, paramDouble, paramLong, d2, d3);
							d9 = d6;
							if (localTradeUserVO.balance - localTradeUserVO.frozenCapital + localTradeUserVO.overdraft - d9 < 0.0D) {
								localConnection.rollback();
								n = 2;
							} else {
								localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_GetRealFunds(?,?) }");
								localCallableStatement.setString(2, paramString1);
								localCallableStatement.setInt(3, 1);
								localCallableStatement.registerOutParameter(1, 8);
								localCallableStatement.executeQuery();
								localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
								localCallableStatement.setString(2, paramString1);
								localCallableStatement.setDouble(3, d6);
								localCallableStatement.setString(4, "4");
								localCallableStatement.registerOutParameter(1, 8);
								localCallableStatement.executeUpdate();
								str = "select sp_v_cursubmit.Nextval from dual";
								localPreparedStatement = localConnection.prepareStatement(str);
								localResultSet = localPreparedStatement.executeQuery();
								long l3 = 0L;
								for (l3 = 0L; localResultSet.next(); l3 = localResultSet.getLong(1)) {
								}
								localResultSet.close();
								localPreparedStatement.close();
								localResultSet = null;
								localPreparedStatement = null;
								str = "insert into v_curSubmit(tradepartition, id, code, price, amount, userid, submittime,tradeFlag,validAmount,modifytime,totalamount,traderId) values(?,?,?,?,?,?,?,2,0,?,?,?)";
								localPreparedStatement = localConnection.prepareStatement(str);
								localPreparedStatement.setInt(1, this.PARTITIONID);
								localPreparedStatement.setLong(2, l3);
								localPreparedStatement.setString(3, paramString3);
								localPreparedStatement.setDouble(4, paramDouble);
								localPreparedStatement.setLong(5, paramLong);
								localPreparedStatement.setString(6, paramString1);
								Timestamp localTimestamp = new Timestamp(System.currentTimeMillis());
								localPreparedStatement.setTimestamp(7, localTimestamp);
								localPreparedStatement.setTimestamp(8, localTimestamp);
								localPreparedStatement.setLong(9, localCommodityVO1.totalAmount);
								localPreparedStatement.setString(10, paramString2);
								localPreparedStatement.executeUpdate();
								localPreparedStatement.close();
								localPreparedStatement = null;
								str = "select commodityID from v_curCommodity where code='" + paramString3 + "'";
								localPreparedStatement = localConnection.prepareStatement(str);
								localResultSet = localPreparedStatement.executeQuery();
								int i3 = 0;
								for (i3 = 0; localResultSet.next(); i3 = localResultSet.getInt(1)) {
								}
								localResultSet.close();
								localPreparedStatement.close();
								localResultSet = null;
								localPreparedStatement = null;
								if (i3 != 6) {
									str = "update v_commodity set status=6 where id =(select commodityID from v_curCommodity where code='"
											+ paramString3 + "')";
									localPreparedStatement = localConnection.prepareStatement(str);
									localPreparedStatement.executeUpdate();
									localPreparedStatement.close();
									localPreparedStatement = null;
								}
								localConnection.commit();
								System.out.println("submit's conn is committed.");
								n = 0;
							}
							localLogger.debug("unlock TRADELOCK : " + paramString1 + "_" + localCommodityVO1.code);
						}
					} catch (Exception localException3) {
						i1 = 1;
						localConnection.rollback();
						localException3.printStackTrace();
					} finally {
						localConnection.setAutoCommit(true);
						this.TRADEDAO.closeStatement(localResultSet, localCallableStatement, localConnection);
					}
				}
			} catch (Exception localException2) {
				i1 = 1;
				localException2.printStackTrace();
				throw localException2;
			}
			localLogger.debug("unlock tcm:" + localCommodityVO1.code + "_" + paramString1);
			if (i1 != 0) {
				n = 6;
			}
		}
		localLogger.debug("order end");
		return n;
	}

	public String getContractContent(long paramLong) throws Exception {
		Connection localConnection = null;
		String str2 = "";
		String str1 = "";
		try {
			localConnection = this.TRADEDAO.getJDBCConnection();
			Statement localStatement = null;
			CLOB localCLOB = null;
			Object localObject1 = null;
			str2 = "select contractContent from v_bargain where contractID=" + paramLong + " and tradePartition=" + this.PARTITIONID;
			localStatement = localConnection.createStatement();
			OracleResultSet localOracleResultSet = (OracleResultSet) localStatement.executeQuery(str2);
			while (localOracleResultSet.next()) {
				localCLOB = localOracleResultSet.getCLOB(1);
			}
			if (localCLOB != null) {
				Reader localReader = localCLOB.getCharacterStream();
				int i = 0;
				char[] arrayOfChar = new char[10000];
				StringBuffer localStringBuffer = null;
				while ((i = localReader.read(arrayOfChar)) != -1) {
					localStringBuffer = new StringBuffer();
					localStringBuffer.append(arrayOfChar, 0, i);
					str1 = str1 + localStringBuffer.toString();
				}
			}
			localOracleResultSet.close();
			localOracleResultSet = null;
			localStatement.close();
			localStatement = null;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			this.TRADEDAO.closeStatement(null, null, localConnection);
		}
		return str1;
	}

	public String getHisContractContent(long paramLong) throws Exception {
		Connection localConnection = null;
		String str2 = "";
		String str1 = "";
		try {
			localConnection = this.TRADEDAO.getJDBCConnection();
			Statement localStatement = null;
			CLOB localCLOB = null;
			Object localObject1 = null;
			str2 = "select contractContent from v_hisbargain where contractID=" + paramLong + " and tradePartition=" + this.PARTITIONID;
			localStatement = localConnection.createStatement();
			OracleResultSet localOracleResultSet = (OracleResultSet) localStatement.executeQuery(str2);
			while (localOracleResultSet.next()) {
				localCLOB = localOracleResultSet.getCLOB(1);
			}
			if (localCLOB != null) {
				Reader localReader = localCLOB.getCharacterStream();
				int i = 0;
				char[] arrayOfChar = new char[10000];
				StringBuffer localStringBuffer = null;
				while ((i = localReader.read(arrayOfChar)) != -1) {
					localStringBuffer = new StringBuffer();
					localStringBuffer.append(arrayOfChar, 0, i);
					str1 = str1 + localStringBuffer.toString();
				}
			}
			localOracleResultSet.close();
			localOracleResultSet = null;
			localStatement.close();
			localStatement = null;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			this.TRADEDAO.closeStatement(null, null, localConnection);
		}
		return str1;
	}

	public Vector getHisResult(String paramString1, String paramString2, String paramString3) throws SQLException {
		Vector localVector = new Vector();
		TradeValue localTradeValue = null;
		Connection localConnection = null;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		String str = "select submitid, code, contractid, price, amount, userid, tradedate, section,b_bail,b_poundage,s_bail,s_poundage,status from v_hisBargain where to_date(to_char(tradeDate,'yyyy-mm-dd'),'yyyy-mm-dd')>=to_date('"
				+ paramString2 + "','yyyy-mm-dd')" + " and to_date(to_char(tradeDate,'yyyy-mm-dd'),'yyyy-mm-dd')<=to_date('" + paramString3
				+ "','yyyy-mm-dd') and userid='" + paramString1 + "' and tradepartition=" + this.PARTITIONID + " order by contractid";
		try {
			localConnection = this.TRADEDAO.getConnection();
			localPreparedStatement = localConnection.prepareStatement(str);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				localTradeValue = new TradeValue();
				localTradeValue.amount = localResultSet.getLong("amount");
				localTradeValue.b_bail = localResultSet.getDouble("b_bail");
				localTradeValue.s_bail = localResultSet.getDouble("s_bail");
				localTradeValue.code = localResultSet.getString("code");
				localTradeValue.contractID = localResultSet.getLong("contractid");
				localTradeValue.b_poundage = localResultSet.getDouble("b_poundage");
				localTradeValue.s_poundage = localResultSet.getDouble("s_poundage");
				localTradeValue.price = localResultSet.getDouble("price");
				localTradeValue.section = localResultSet.getInt("section");
				localTradeValue.submitID = localResultSet.getLong("submitid");
				localTradeValue.tradeDate = localResultSet.getTimestamp("tradedate");
				localTradeValue.userID = localResultSet.getString("userid");
				localTradeValue.status = localResultSet.getInt("status");
				localVector.add(localTradeValue);
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement, localConnection);
		}
		return localVector;
	}

	public TradeUserVO getTradeUser(String paramString) throws SQLException {
		TradeUserVO localTradeUserVO = this.TRADEDAO.getTradeUser(paramString);
		return localTradeUserVO;
	}

	public BargainVO getBargain(long paramLong) throws SQLException {
		return this.TRADEDAO.getCurBargain(paramLong);
	}

	public BroadcastVO getBroadcast(long paramLong) throws SQLException {
		return this.TRADEDAO.getBroadcast(paramLong);
	}

	public BargainVO[] getMarketBargain() throws SQLException {
		BargainVO[] arrayOfBargainVO = this.TRADEDAO.getBargainList("where tradepartition=" + this.PARTITIONID + " order by contractID desc");
		return arrayOfBargainVO;
	}

	public void chgTraderPwd(String paramString1, String paramString2) throws SQLException {
		TradeUserVO localTradeUserVO = this.TRADEDAO.getTradeUser(paramString1);
		localTradeUserVO.password = TranPass.MD5(paramString1, paramString2);
		this.TRADEDAO.modifyTradeUser(localTradeUserVO);
	}

	public CommodityVO getCurCommodity(String paramString) {
		CommodityVO localCommodityVO = null;
		try {
			KernelEngine localKernelEngine = GlobalContainer.getEngine(this.PARTITIONID);
			localCommodityVO = localKernelEngine.getCurCommodity(paramString);
			TradeStatusValue localTradeStatusValue = localKernelEngine.getTradeStatus();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return localCommodityVO;
	}
}
