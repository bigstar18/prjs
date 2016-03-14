package gnnt.MEBS.vendue.server;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import gnnt.MEBS.vendue.server.dao.TradeDAO;
import gnnt.MEBS.vendue.server.vo.BargainVO;
import gnnt.MEBS.vendue.server.vo.CommodityVO;
import gnnt.MEBS.vendue.server.vo.CurSubmitVO;
import gnnt.MEBS.vendue.server.vo.FlowControlVO;
import gnnt.MEBS.vendue.server.vo.FundInfoList;
import gnnt.MEBS.vendue.server.vo.FundInfoValue;
import gnnt.MEBS.vendue.server.vo.MoneyVO;
import gnnt.MEBS.vendue.server.vo.SysCurStatusVO;
import gnnt.MEBS.vendue.server.vo.SysPartitionVO;
import gnnt.MEBS.vendue.server.vo.SysPropertyVO;
import gnnt.MEBS.vendue.server.vo.TradeCommodityList1;
import gnnt.MEBS.vendue.server.vo.TradeCommodityM1;
import gnnt.MEBS.vendue.server.vo.TradeQuotationVO;
import gnnt.MEBS.vendue.server.vo.TradeResuleValue;
import gnnt.MEBS.vendue.server.vo.TradeStatusValue;
import gnnt.MEBS.vendue.server.vo.TradeStatusValueImpl;
import gnnt.MEBS.vendue.server.vo.TradeUserVO;
import gnnt.MEBS.vendue.util.Arith;
import gnnt.MEBS.vendue.util.Configuration;
import gnnt.MEBS.vendue.util.ObjSet;
import gnnt.MEBS.vendue.util.Tool;

public class KernelEngineBID extends KernelEngine {
	public int addCommodityCharge(String paramString, long paramLong, Connection paramConnection) throws SQLException {
		TradeUserVO localTradeUserVO = this.TRADEDAO.getTradeUser(paramString, paramConnection);
		CommodityVO localCommodityVO = this.TRADEDAO.getCommodity(paramLong, paramConnection);
		int i = 0;
		double d1 = 0.0D;
		double d2 = 0.0D;
		double d3 = 0.0D;
		double d4 = 0.0D;
		d3 = localCommodityVO.beginPrice;
		System.out.println("------------" + localCommodityVO.toString());
		Configuration localConfiguration = new Configuration();
		int j = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("PayType"));
		int k = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("FeeSiscount"));
		int m = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("SecuritySiscount"));
		int n = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("SecurityBySelf"));
		int i1 = 1;
		if (n == 0) {
			i1 = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("SecurityType"));
		} else {
			i1 = Integer.parseInt(localCommodityVO.str7);
		}
		int i2 = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("FeeBySelf"));
		int i3 = 1;
		if (i2 == 0) {
			i3 = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("FeeType"));
		} else {
			i3 = Integer.parseInt(localCommodityVO.str8);
		}
		double d5 = 1.0D;
		if (k == 1) {
			System.out.println("=======手续费计算折扣=============");
			d5 = localTradeUserVO.feecutfee;
		}
		double d6 = 1.0D;
		if (m == 1) {
			System.out.println("=======保证金计算折扣=============");
			d6 = localTradeUserVO.feecut;
		}
		System.out.println("======保证金系数是 " + localCommodityVO.b_security + "  交易数量是 " + localCommodityVO.totalAmount + "  保证金费用折扣是 "
				+ localTradeUserVO.feecut + "  手续费系数是 " + localCommodityVO.b_fee + " 手续费折扣是 " + localTradeUserVO.feecutfee);
		if (i1 == 0) {
			System.out.println("保证金====  绝对值");
			d1 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.b_security, d6), localCommodityVO.tradeUnit) * localCommodityVO.totalAmount, 2);
		} else if (i1 == 1) {
			System.out.println("保证金====  百分比");
			d1 = Arith.format(
					Arith.mul(Arith.mul(Arith.mul(d3, localCommodityVO.b_security), d6), localCommodityVO.tradeUnit) * localCommodityVO.totalAmount,
					2);
		}
		if (j == 1) {
			if (i3 == 0) {
				System.out.println("手续费====  绝对值");
				d2 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.b_fee, d5), localCommodityVO.tradeUnit) * localCommodityVO.totalAmount, 2);
			} else if (i3 == 1) {
				System.out.println("手续费====  百分比");
				d2 = Arith.format(
						Arith.mul(Arith.mul(Arith.mul(d3, localCommodityVO.b_fee), d5), localCommodityVO.tradeUnit) * localCommodityVO.totalAmount,
						2);
			}
		}
		this.TRADEDAO.getTradeUserForUpdate(paramString, paramConnection);
		System.out.println("====================加入到当前交易商品 判断交易商资金是否足够==========");
		System.out.println("保证金 bailMoney is " + d1);
		System.out.println("手续费 feeMoney is " + d2);
		System.out.println("交易商 " + localTradeUserVO.userCode + " 资金是 " + localTradeUserVO.balance);
		if ((Arith.compareTo(localTradeUserVO.balance, d1 + d2) == -1) || (localTradeUserVO.balance == 0.0D)) {
			System.out.println("交易商 " + localTradeUserVO.userCode + " 资金是 " + localTradeUserVO.balance + "  保证金和手续费是 " + (d1 + d2));
			i = 1;
		} else {
			CallableStatement localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
			localCallableStatement.setString(2, localTradeUserVO.userCode);
			localCallableStatement.setDouble(3, d1);
			localCallableStatement.setString(4, "4");
			localCallableStatement.registerOutParameter(1, 8);
			localCallableStatement.executeUpdate();
			localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_GetRealFunds(?,?) }");
			localCallableStatement.setString(2, localTradeUserVO.userCode);
			localCallableStatement.setInt(3, 0);
			localCallableStatement.registerOutParameter(1, 8);
			localCallableStatement.executeQuery();
			d4 = localCallableStatement.getDouble(1);
			addDailyMoney(d4, localTradeUserVO.paymentForGoods, -1L, localTradeUserVO.userCode, d1, 404, paramLong + "", paramConnection);
			localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
			localCallableStatement.setString(2, localTradeUserVO.userCode);
			localCallableStatement.setDouble(3, d2);
			localCallableStatement.setString(4, "4");
			localCallableStatement.registerOutParameter(1, 8);
			localCallableStatement.executeUpdate();
			localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_GetRealFunds(?,?) }");
			localCallableStatement.setString(2, localTradeUserVO.userCode);
			localCallableStatement.setInt(3, 0);
			localCallableStatement.registerOutParameter(1, 8);
			localCallableStatement.executeQuery();
			d4 = localCallableStatement.getDouble(1);
			addDailyMoney(d4, localTradeUserVO.paymentForGoods, -1L, localTradeUserVO.userCode, d2, 403, paramLong + "", paramConnection);
		}
		return i;
	}

	public int closeTrade() {
		int i = 2;
		int j = this.TRADESTATUS.getCurStatus();
		Connection localConnection = null;
		if ((j == 4) || (j == 3) || (j == 1)) {
			try {
				modSysStatusDB(null, 0, null, 5);
				modSysStatus(null, 0, null, 5, null);
				this.COMMODITYTABLE = new Hashtable();
				localConnection = this.TRADEDAO.getJDBCConnection();
				localConnection.setAutoCommit(false);
				String str = "where tradePartition=" + this.PARTITIONID;
				this.TRADEDAO.delTradeQuotation(str, localConnection);
				localConnection.commit();
			} catch (Exception localException) {
				localException.printStackTrace();
				i = 1;
			} finally {
				this.TRADEDAO.closeStatement(null, null, localConnection);
			}
		} else {
			i = 0;
		}
		return i;
	}

	public int continueTrade() {
		int i = 2;
		int j = this.TRADESTATUS.getCurStatus();
		if (j == 4) {
			try {
				modSysStatusDB(null, -1, null, 8);
				modSysStatus(null, -1, null, 8, null);
				resumeTradeStatus();
			} catch (SQLException localSQLException) {
				localSQLException.printStackTrace();
				i = 1;
			}
		} else {
			i = 0;
		}
		return i;
	}

	public int delCommodityCharge(long paramLong, Connection paramConnection) throws SQLException {
		int i = 0;
		double d1 = 0.0D;
		CommodityVO localCommodityVO = this.TRADEDAO.getCommodity(paramLong, paramConnection);
		String str = localCommodityVO.userID;
		if (localCommodityVO.bargainFlag == 1) {
			i = 1;
		} else if (localCommodityVO.bargainFlag == 0) {
			double d2 = 0.0D;
			MoneyVO[] arrayOfMoneyVO = this.TRADEDAO
					.getMoneyList("where (operation=404 or operation=407) and firmid='" + str + "' and note='" + paramLong + "'", paramConnection);
			if (arrayOfMoneyVO != null) {
				for (int j = 0; j < arrayOfMoneyVO.length; j++) {
					if (arrayOfMoneyVO[j].operation == 404) {
						d2 += arrayOfMoneyVO[j].money;
					} else if (arrayOfMoneyVO[j].operation == 407) {
						d2 -= arrayOfMoneyVO[j].money;
					}
				}
			}
			TradeUserVO localTradeUserVO = this.TRADEDAO.getTradeUser(str, paramConnection);
			CallableStatement localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
			localCallableStatement.setString(2, localTradeUserVO.userCode);
			localCallableStatement.setDouble(3, -1.0D * d2);
			localCallableStatement.setString(4, "4");
			localCallableStatement.registerOutParameter(1, 8);
			localCallableStatement.executeUpdate();
			localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_GetRealFunds(?,?) }");
			localCallableStatement.setString(2, localTradeUserVO.userCode);
			localCallableStatement.setInt(3, 0);
			localCallableStatement.registerOutParameter(1, 8);
			localCallableStatement.executeQuery();
			d1 = localCallableStatement.getDouble(1);
			addDailyMoney(d1, localTradeUserVO.paymentForGoods, -1L, str, d2, 407, paramLong + "", paramConnection);
			d2 = 0.0D;
			arrayOfMoneyVO = this.TRADEDAO
					.getMoneyList("where (operation=403 or operation=413) and firmid='" + str + "' and note='" + paramLong + "'", paramConnection);
			if (arrayOfMoneyVO != null) {
				for (int k = 0; k < arrayOfMoneyVO.length; k++) {
					if (arrayOfMoneyVO[k].operation == 403) {
						d2 += arrayOfMoneyVO[k].money;
					} else if (arrayOfMoneyVO[k].operation == 413) {
						d2 -= arrayOfMoneyVO[k].money;
					}
				}
			}
			localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
			localCallableStatement.setString(2, localTradeUserVO.userCode);
			localCallableStatement.setDouble(3, -1.0D * d2);
			localCallableStatement.setString(4, "4");
			localCallableStatement.registerOutParameter(1, 8);
			localCallableStatement.executeUpdate();
			localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_GetRealFunds(?,?) }");
			localCallableStatement.setString(2, localTradeUserVO.userCode);
			localCallableStatement.setInt(3, 0);
			localCallableStatement.registerOutParameter(1, 8);
			localCallableStatement.executeQuery();
			d1 = localCallableStatement.getDouble(1);
			addDailyMoney(d1, localTradeUserVO.paymentForGoods, -1L, str, d2, 413, paramLong + "", paramConnection);
		}
		return i;
	}

	public int forceStartTrade() {
		int i = 2;
		int j = this.TRADESTATUS.getCurStatus();
		boolean bool = this.TRADESTATUS.getIsMaxTradeOrder();
		if ((j == 3) && (!bool)) {
			try {
				modSysStatusDB(null, -1, null, 10);
				modSysStatus(null, -1, null, 10, null);
			} catch (SQLException localSQLException) {
				localSQLException.printStackTrace();
				i = 1;
			}
		} else {
			i = 0;
		}
		return i;
	}

	public double getBail(String paramString1, double paramDouble, long paramLong, String paramString2, Connection paramConnection)
			throws SQLException {
		double d1 = 0.0D;
		CommodityVO localCommodityVO = (CommodityVO) this.COMMODITYTABLE.get(paramString1);
		Configuration localConfiguration = new Configuration();
		int i = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("SecurityBySelf"));
		int j = 1;
		if (i == 0) {
			j = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("SecurityType"));
		} else {
			j = Integer.parseInt(localCommodityVO.str7);
		}
		int k = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("SecuritySiscount"));
		double d2 = 1.0D;
		if (k == 1) {
			System.out.println("=======保证金计算折扣率=============");
			d2 = this.TRADEDAO.getTradeUser(paramString2, paramConnection).feecut;
		}
		if (j == 0) {
			System.out.println();
			System.out.println("===========================  保证金取绝对值 ===========================");
			System.out.println();
			if (localCommodityVO.tradeMode == 2) {
				System.out.println("－－－－－－－－－－合同为招标 ");
				if (localCommodityVO.userID.equals(paramString2)) {
					d1 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.b_security, d2), localCommodityVO.tradeUnit) * paramLong, 2);
					System.out.println("取买方" + paramString2 + "保证金 =" + d1 + "  保证金系数是 " + localCommodityVO.b_security);
					System.out.println();
				} else {
					d1 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.s_security, d2), localCommodityVO.tradeUnit) * paramLong, 2);
					System.out.println("取卖方" + paramString2 + "保证金 =" + d1 + "  保证金系数是 " + localCommodityVO.s_security);
					System.out.println();
				}
			}
		} else if (j == 1) {
			System.out.println();
			System.out.println("===========================  保证金取百分比 ===========================");
			System.out.println();
			if (localCommodityVO.tradeMode == 2) {
				System.out.println("－－－－－－－－－－合同为招标 ");
				if (localCommodityVO.userID.equals(paramString2)) {
					d1 = Arith.format(
							Arith.mul(Arith.mul(Arith.mul(paramDouble, localCommodityVO.b_security), d2), localCommodityVO.tradeUnit) * paramLong, 2);
					System.out.println("取买方" + paramString2 + "保证金 =" + d1 + "  保证金系数是 " + localCommodityVO.b_security);
					System.out.println();
				} else {
					d1 = Arith.format(
							Arith.mul(Arith.mul(Arith.mul(paramDouble, localCommodityVO.s_security), d2), localCommodityVO.tradeUnit) * paramLong, 2);
					System.out.println("取卖方" + paramString2 + "保证金 =" + d1 + "  保证金系数是 " + localCommodityVO.s_security);
					System.out.println();
				}
			}
		}
		return d1;
	}

	public CommodityVO getCurCommodity(String paramString) {
		return (CommodityVO) this.COMMODITYTABLE.get(paramString);
	}

	public CommodityVO[] getCurCommodityList() {
		CommodityVO[] arrayOfCommodityVO = new CommodityVO[this.COMMODITYTABLE.size()];
		Enumeration localEnumeration = this.COMMODITYTABLE.elements();
		for (int i = 0; localEnumeration.hasMoreElements(); i++) {
			arrayOfCommodityVO[i] = ((CommodityVO) localEnumeration.nextElement());
		}
		for (int i = 0; i < arrayOfCommodityVO.length; i++) {
			for (int j = 0; j < arrayOfCommodityVO.length - i - 1; j++) {
				if (arrayOfCommodityVO[j].section > arrayOfCommodityVO[(j + 1)].section) {
					CommodityVO localCommodityVO = arrayOfCommodityVO[j];
					arrayOfCommodityVO[j] = arrayOfCommodityVO[(j + 1)];
					arrayOfCommodityVO[(j + 1)] = localCommodityVO;
				}
			}
		}
		return arrayOfCommodityVO;
	}

	public double getFee(String paramString1, double paramDouble, long paramLong, String paramString2, Connection paramConnection)
			throws SQLException {
		double d1 = 0.0D;
		CommodityVO localCommodityVO = (CommodityVO) this.COMMODITYTABLE.get(paramString1);
		Configuration localConfiguration = new Configuration();
		int i = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("PayType"));
		int j = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("FeeBySelf"));
		int k = 1;
		if (j == 0) {
			k = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("FeeType"));
		} else {
			k = Integer.parseInt(localCommodityVO.str8);
		}
		int m = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("FeeSiscount"));
		double d2 = 1.0D;
		if (m == 1) {
			System.out.println("=======手续费计算折扣率=============");
			d2 = this.TRADEDAO.getTradeUser(paramString2, paramConnection).feecutfee;
		}
		if (i == 1) {
			if (k == 0) {
				System.out.println();
				System.out.println("======================== 手续费为绝对值 =====================");
				System.out.println();
				if (localCommodityVO.tradeMode == 2) {
					System.out.println();
					System.out.println("======================== 招标 =====================");
					System.out.println();
					if (localCommodityVO.userID.equals(paramString2)) {
						d1 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.b_fee, d2), localCommodityVO.tradeUnit) * paramLong, 2);
						System.out.println();
						System.out.println("买方 " + paramString2 + " 手续费为 " + d1 + "手续费系数为 " + localCommodityVO.b_fee);
					} else {
						d1 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.s_fee, d2), localCommodityVO.tradeUnit) * paramLong, 2);
						System.out.println();
						System.out.println("卖方 " + paramString2 + " 手续费为 " + d1 + "手续费系数为 " + localCommodityVO.s_fee);
					}
				}
			} else if (k == 1) {
				System.out.println();
				System.out.println("======================== 手续费为百分比 =====================");
				System.out.println();
				if (localCommodityVO.tradeMode == 2) {
					System.out.println();
					System.out.println("======================== 招标 =====================");
					System.out.println();
					if (localCommodityVO.userID.equals(paramString2)) {
						d1 = Arith.format(
								Arith.mul(Arith.mul(Arith.mul(paramDouble, localCommodityVO.b_fee), d2), localCommodityVO.tradeUnit) * paramLong, 2);
						System.out.println();
						System.out.println("买方 " + paramString2 + " 手续费为 " + d1 + "手续费系数为 " + localCommodityVO.b_fee);
					} else {
						d1 = Arith.format(
								Arith.mul(Arith.mul(Arith.mul(paramDouble, localCommodityVO.s_fee), d2), localCommodityVO.tradeUnit) * paramLong, 2);
						System.out.println();
						System.out.println("卖方 " + paramString2 + " 手续费为 " + d1 + "手续费系数为 " + localCommodityVO.s_fee);
					}
				}
			}
		}
		return d1;
	}

	public String getPartitionName() throws SQLException {
		String str = "";
		SysPartitionVO[] arrayOfSysPartitionVO = this.TRADEDAO.getSysPartitionList("where partitionID=" + this.PARTITIONID);
		if (arrayOfSysPartitionVO != null) {
			str = arrayOfSysPartitionVO[0].description;
		}
		return str;
	}

	protected TradeDAO getTradeDAO() {
		return null;
	}

	public TradeStatusValue getTradeStatus() throws SQLException {
		return this.TRADESTATUS;
	}

	public void loadCommodity() throws SQLException {
		Connection localConnection = null;
		try {
			CommodityVO[] arrayOfCommodityVO = this.TRADEDAO.getCurCommodityList(this.PARTITIONID);
			localConnection = this.TRADEDAO.getJDBCConnection();
			if (this.COMMODITYTABLE == null) {
				this.COMMODITYTABLE = new Hashtable();
			}
			if (arrayOfCommodityVO != null) {
				for (int i = 0; i < arrayOfCommodityVO.length; i++) {
					CommodityVO localCommodityVO = arrayOfCommodityVO[i];
					if ((localCommodityVO == null) || ((localCommodityVO.bargainFlag == 0)
							&& (((this.TRADESTATUS.getCurStatus() == 2) && (localCommodityVO.lastPrice <= 0.0D))
									|| (this.TRADESTATUS.getCurStatus() != 2)))) {
						if (localCommodityVO.amount <= 0L) {
							localCommodityVO.lastPrice = 0.0D;
						}
						this.COMMODITYTABLE.put(localCommodityVO.code, localCommodityVO);
						if ((this.TRADESTATUS.getCurStatus() == 2) && (localCommodityVO.section == this.TRADESTATUS.getLastPartID())) {
							FlowControlVO[] arrayOfFlowControlVO = this.TRADEDAO.getFlowControlList(
									"where unitType=1 and tradePartition=" + this.PARTITIONID + " and unitID=" + getNextSection(), localConnection);
							if (arrayOfFlowControlVO != null) {
								addQuotation(localCommodityVO.code, (int) arrayOfFlowControlVO[0].durativeTime,
										new Timestamp(System.currentTimeMillis()), 0.0D, localCommodityVO.section, -1L, this.PARTITIONID, " ", 0L);
							} else {
								addQuotation(localCommodityVO.code, this.TRADESTATUS.getCountdownTime(), new Timestamp(System.currentTimeMillis()),
										0.0D, localCommodityVO.section, -1L, this.PARTITIONID, " ", 0L);
							}
						}
					}
				}
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			this.TRADEDAO.closeStatement(null, null, localConnection);
		}
	}

	public int manualStartTrade() {
		int i = 2;
		int j = this.TRADESTATUS.getCurStatus();
		if (j == 9) {
			try {
				modSysStatusDB(null, -1, null, 7);
				modSysStatus(null, -1, null, 7, null);
			} catch (SQLException localSQLException) {
				localSQLException.printStackTrace();
				i = 1;
			}
		} else {
			i = 0;
		}
		return i;
	}

	public int pauseTrade() {
		int i = 2;
		int j = this.TRADESTATUS.getCurStatus();
		if (j == 2) {
			try {
				modSysStatusDB(null, -1, null, 4);
				modSysStatus(null, -1, null, 4, null);
			} catch (SQLException localSQLException) {
				localSQLException.printStackTrace();
				i = 1;
			}
		} else {
			i = 0;
		}
		return i;
	}

	protected void resumeTradeStatus() throws SQLException {
		int i = this.TRADESTATUS.getCurStatus();
		int j = this.TRADESTATUS.getLastPartID();
		Timestamp localTimestamp1 = this.TRADESTATUS.getPartEndTime();
		int k = this.TRADESTATUS.getCountdownStart();
		int m = this.TRADESTATUS.getCountdownTime();
		Timestamp localTimestamp2 = new Timestamp(System.currentTimeMillis());
		CommodityVO[] arrayOfCommodityVO = getCurCommodityList();
		int n;
		if (i == 2) {
			long l = 0L;
			if (arrayOfCommodityVO != null) {
				for (n = 0; n < arrayOfCommodityVO.length; n++) {
					if ((arrayOfCommodityVO[n].section == j) && (arrayOfCommodityVO[n].lastTime != null)
							&& (arrayOfCommodityVO[n].lastTime.getTime() > l)) {
						l = arrayOfCommodityVO[n].lastTime.getTime();
					}
				}
			}
			if ((localTimestamp2.getTime() > localTimestamp1.getTime() - k * 1000)
					&& (l + m * 1000 > this.TRADESTATUS.getPartLastEndTime().getTime())) {
				this.TRADESTATUS.setPartLastEndTime(new Timestamp(l + m * 1000));
			}
		}
		Timestamp localTimestamp3 = this.TRADESTATUS.getPartLastEndTime();
		if ((i == 2) && (localTimestamp2.getTime() > localTimestamp3.getTime())) {
			Connection localConnection = null;
			try {
				localConnection = this.TRADEDAO.getConnection();
				localConnection.setAutoCommit(false);
				if (arrayOfCommodityVO != null) {
					for (n = 0; n < arrayOfCommodityVO.length; n++) {
						if (arrayOfCommodityVO[n].section == j) {
							this.TRADEDAO.delCurSubmit("where code='" + arrayOfCommodityVO[n].code + "'", localConnection);
						}
					}
				}
				this.TRADEDAO.delTradeQuotation("where tradePartition=" + this.PARTITIONID, localConnection);
				n = 0;
				int i1;
				if ((arrayOfCommodityVO != null) && (arrayOfCommodityVO.length > 0)) {
					i1 = arrayOfCommodityVO.length - 1;
					if ((i1 <= 0) && (arrayOfCommodityVO[i1].section < j)) {
						n = arrayOfCommodityVO[i1].section;
					}
				}
				modSysStatusDB(localTimestamp2, n, null, 3, localConnection);
				localConnection.commit();
				if (arrayOfCommodityVO != null) {
					for (i1 = 0; i1 < arrayOfCommodityVO.length; i1++) {
						if (arrayOfCommodityVO[i1].section == j) {
							arrayOfCommodityVO[i1].lastSubmitID = -1L;
							arrayOfCommodityVO[i1].lastTime = null;
							arrayOfCommodityVO[i1].amount = 0L;
							arrayOfCommodityVO[i1].lastPrice = 0.0D;
							arrayOfCommodityVO[i1].lastUserID = " ";
						}
					}
				}
				this.TRADESTATUS.setCurStatus(3);
				this.TRADESTATUS.setLastPartID(n);
				this.TRADESTATUS.setPartEndTime(localTimestamp2);
				this.TRADESTATUS.setPartLastEndTime(localTimestamp2);
			} catch (SQLException localSQLException) {
				localSQLException.printStackTrace();
				localConnection.rollback();
				throw localSQLException;
			} finally {
				this.TRADEDAO.closeStatement(null, null, localConnection);
			}
		}
	}

	public void setPartition(int paramInt) {
		this.PARTITIONID = paramInt;
	}

	public void setTradeDAO(TradeDAO paramTradeDAO) {
		this.TRADEDAO = paramTradeDAO;
	}

	protected void setTradeStatus() throws SQLException {
		SysCurStatusVO localSysCurStatusVO = this.TRADEDAO.getSysCurStatus(this.PARTITIONID);
		SysPropertyVO localSysPropertyVO = this.TRADEDAO.getSysProperty(this.PARTITIONID);
		TradeStatusValueImpl localTradeStatusValueImpl = new TradeStatusValueImpl();
		localTradeStatusValueImpl.setCurStatus(localSysCurStatusVO.status);
		localTradeStatusValueImpl.setLastPartID(localSysCurStatusVO.section);
		localTradeStatusValueImpl.setPartStartTime(localSysCurStatusVO.starttime);
		localTradeStatusValueImpl.setPartEndTime(localSysCurStatusVO.endtime);
		localTradeStatusValueImpl.setPartLastEndTime(localSysCurStatusVO.endtime);
		localTradeStatusValueImpl.setIsMaxTradeOrder(false);
		localTradeStatusValueImpl.setCountdownStart(Integer.parseInt(localSysPropertyVO.countdownStart));
		localTradeStatusValueImpl.setCountdownTime(Integer.parseInt(localSysPropertyVO.countdownTime));
		localTradeStatusValueImpl.setSpaceTime(localSysPropertyVO.spaceTime);
		localTradeStatusValueImpl.setDurativeTime(localSysPropertyVO.durativeTime);
		this.TRADESTATUS = localTradeStatusValueImpl;
	}

	public int startTrade() {
		int i = 2;
		int j = this.TRADESTATUS.getCurStatus();
		System.out.println(j);
		if ((j == 5) || (j == 6)) {
			try {
				modSysStatusDB(null, -1, null, 1);
				modSysStatus(null, -1, null, 1, null);
				reLoadCommodity();
			} catch (SQLException localSQLException) {
				localSQLException.printStackTrace();
				i = 1;
			}
		} else {
			i = 0;
		}
		return i;
	}

	public void touch(String paramString1, long paramLong1, double paramDouble, String paramString2, Timestamp paramTimestamp, long paramLong2) {
		CommodityVO localCommodityVO = getCurCommodity(paramString1);
		localCommodityVO.setValues(paramLong1, paramDouble, paramString2, paramTimestamp, paramLong2);
	}

	public void run() {
		long l1 = 0L;
		long l2 = 0L;
		long l3 = 0L;
		long l4 = 0L;
		int j = 0;
		int k = -1;
		int m = -1;
		int n = -1;
		Connection localConnection = null;
		Properties localProperties = new Configuration().getSection("MEBS.TradeParams");
		int i = Integer.parseInt(localProperties.getProperty("Interval"));
		CallableStatement localCallableStatement = null;
		try {
			localConnection = this.TRADEDAO.getJDBCConnection();
			localConnection.setAutoCommit(false);
		} catch (SQLException localSQLException1) {
			localSQLException1.printStackTrace();
		} catch (ClassNotFoundException localClassNotFoundException1) {
			localClassNotFoundException1.printStackTrace();
		}
		for (;;) {
			try {
				try {
					k = this.TRADESTATUS.getCurStatus();
					m = this.TRADESTATUS.getLastPartID();
					n = getNextSection();
					long l5 = 0L;
					int i1 = 0;
					long l6 = System.currentTimeMillis();
					long l7 = this.TRADESTATUS.getPartLastEndTime().getTime();
					long l8 = this.TRADESTATUS.getPartEndTime().getTime();
					l4 = this.TRADESTATUS.getDurativeTime();
					l3 = this.TRADESTATUS.getSpaceTime();
					int i2 = this.TRADESTATUS.getCountdownStart();
					int i3 = this.TRADESTATUS.getCountdownTime();
					if (((k != 4) && (k != 5) && (k != 6) && (this.COMMODITYTABLE != null) && (this.COMMODITYTABLE.size() > 0) && (k != 1))
							|| ((k == 1) && (n > 0))) {
						if ((k != 8) && (k != 2)) {
							FlowControlVO[] localObject1 = this.TRADEDAO.getFlowControlList(
									"where unitType=2 and tradePartition=" + this.PARTITIONID + " and unitID=" + (n - 1), localConnection);
							int i5;
							if (localObject1 != null) {
								i5 = 0;
								if (i5 < localObject1.length) {
									FlowControlVO localObject3 = localObject1[i5];
									l5 = localObject3.durativeTime;
									i5++;
									continue;
								}
							}
							localObject1 = this.TRADEDAO.getFlowControlList(
									"where unitType=1 and tradePartition=" + this.PARTITIONID + " and unitID=" + n, localConnection);
							if (localObject1 != null) {
								i5 = 0;
								if (i5 < localObject1.length) {
									FlowControlVO localObject3 = localObject1[i5];
									i1 = 1;
									int i6 = localObject3.startMode;
									l4 = localObject3.durativeTime;
									i3 = (int) localObject3.durativeTime;
									if (i6 == 1) {
										l1 = Tool.getTime(localObject3.startTime);
										if ((k == 3) || (k == 1)) {
											l2 = l1 + l4 * 1000L;
										}
									} else if (i6 == 2) {
										if ((k != 7) && (k != 2)) {
											modSysStatus(null, -1, null, 9, null);
											Thread.sleep(i);
											k = this.TRADESTATUS.getCurStatus();
											l6 = System.currentTimeMillis();
											continue;
										}
										if (k == 7) {
											l1 = l6;
											l2 = l1 + l4 * 1000L;
										}
									} else if (i6 == 3) {
										if ((k == 1) || (k == 10)) {
											l1 = l6;
											l2 = l1 + l4 * 1000L;
										} else if (k == 3) {
											l1 = l7 + l3 * 1000L + l5 * 1000L;
											l2 = l1 + l4 * 1000L;
										}
									}
									i5++;
									continue;
								}
							}
							if (i1 == 0) {
								if ((k == 1) || (k == 10)) {
									l1 = l6;
									l2 = l1 + l4 * 1000L;
								} else if (k == 3) {
									l1 = l7 + l3 * 1000L + l5 * 1000L;
									l2 = l1 + l4 * 1000L;
								}
							}
						}
						if (l6 < l1) {
							if (k != 3) {
								modSysStatusDB(null, -1, null, 3, localConnection);
								modSysStatus(null, -1, null, 3, null);
							}
						} else {
							Object localObject2;
							if ((l6 >= l1) && (((k == 2) && (l6 <= l7)) || (l6 <= l2))) {
								if (k != 2) {
									modSysStatusDB(new Timestamp(l2), n, new Timestamp(l1), 2, localConnection);
									Enumeration localObject1 = this.COMMODITYTABLE.elements();
									if (((Enumeration) localObject1).hasMoreElements()) {
										localObject2 = (CommodityVO) ((Enumeration) localObject1).nextElement();
										synchronized (localObject2) {
											if ((((CommodityVO) localObject2).section == n) && (((CommodityVO) localObject2).bargainFlag == 0)) {
												addQuotation(((CommodityVO) localObject2).code, i3, new Timestamp(l6), 0.0D, n, -1L, this.PARTITIONID,
														" ", ((CommodityVO) localObject2).amount, localConnection);
											}
										}
										continue;
									}
									modSysStatus(new Timestamp(l2), n, new Timestamp(l1), 2, new Timestamp(l2));
								} else {
									l2 = l7;
									if (j == 0) {
										TradeQuotationVO[] localObject1 = this.TRADEDAO.getTradeQuotationList("where countdownTime>0",
												localConnection);
										if ((localObject1 != null) && (localObject1.length > 0)) {
											j = 1;
										}
									}
									if ((l6 >= l8 - i2 * 1000) && (j == 0)) {
										Enumeration localObject1 = this.COMMODITYTABLE.elements();
										if (((Enumeration) localObject1).hasMoreElements()) {
											localObject2 = (CommodityVO) ((Enumeration) localObject1).nextElement();
											synchronized (localObject2) {
												if ((((CommodityVO) localObject2).section == m) && (((CommodityVO) localObject2).bargainFlag == 0)) {
													this.TRADEDAO.delTradeQuotation("where tradePartition=" + this.PARTITIONID + " and code='"
															+ ((CommodityVO) localObject2).code + "'", localConnection);
													String str = " ";
													if ((((CommodityVO) localObject2).lastUserID != null)
															&& (!((CommodityVO) localObject2).lastUserID.equals(""))) {
														str = ((CommodityVO) localObject2).lastUserID;
													}
													Timestamp localTimestamp = new Timestamp(l6);
													if (((CommodityVO) localObject2).lastTime != null) {
														localTimestamp = ((CommodityVO) localObject2).lastTime;
													}
													addQuotation(((CommodityVO) localObject2).code, i3, localTimestamp,
															((CommodityVO) localObject2).lastPrice, m, ((CommodityVO) localObject2).lastSubmitID,
															this.PARTITIONID, str, ((CommodityVO) localObject2).amount, localConnection);
												}
											}
											j = 1;
											continue;
										}
									}
								}
							} else if (checkCommodityFinished()) {
								j = 0;
								int i4 = this.TRADESTATUS.getLastPartID();
								localObject2 = null;
								double d1 = 0.0D;
								double d2 = 0.0D;
								double d3 = 0.0D;
								double d4 = 0.0D;
								long l9 = 0L;
								if (k != 3) {
									int i7 = -1;
									try {
										i7 = Integer.parseInt(new Configuration().getSection("MEBS.TradeParams").getProperty("IsAuto"));
									} catch (Exception localException3) {
										localException3.printStackTrace();
									}
									if (i7 == 1) {
										localObject2 = this.TRADEDAO.makeTradeBID(i4, localConnection);
									}
									int i8;
									Object localObject6;
									if (localObject2 != null) {
										i8 = 0;
										if (i8 < ((List) localObject2).size()) {
											System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
											System.out.println("/************************生成成交纪录**********************************/");
											System.out.println();
											localObject6 = new BargainVO();
											TradeResuleValue localTradeResuleValue = (TradeResuleValue) ((List) localObject2).get(i8);
											((BargainVO) localObject6).tradePartition = this.PARTITIONID;
											((BargainVO) localObject6).amount = localTradeResuleValue.getAmount();
											((BargainVO) localObject6).price = localTradeResuleValue.getPrice();
											((BargainVO) localObject6).code = localTradeResuleValue.getCode();
											((BargainVO) localObject6).userID = localTradeResuleValue.getUserId();
											((BargainVO) localObject6).section = m;
											((BargainVO) localObject6).status = 0;
											((BargainVO) localObject6).submitID = localTradeResuleValue.getSubmitId();
											((BargainVO) localObject6).tradeDate = new Timestamp(System.currentTimeMillis());
											((BargainVO) localObject6).commodityID = localTradeResuleValue.getCommodityID();
											l9 = localTradeResuleValue.getOldamount();
											CommodityVO localCommodityVO1 = (CommodityVO) this.COMMODITYTABLE.get(((BargainVO) localObject6).code);
											if (localCommodityVO1.tradeMode == 2) {
												System.out.println();
												System.out.println("==========招标");
												System.out.println();
												((BargainVO) localObject6).s_bail = getBail(((BargainVO) localObject6).code,
														((BargainVO) localObject6).price, ((BargainVO) localObject6).amount,
														((BargainVO) localObject6).userID, localConnection);
												((BargainVO) localObject6).b_bail = getBail(((BargainVO) localObject6).code,
														((BargainVO) localObject6).price, ((BargainVO) localObject6).amount, localCommodityVO1.userID,
														localConnection);
												d1 = getBail(((BargainVO) localObject6).code, ((BargainVO) localObject6).price, l9,
														((BargainVO) localObject6).userID, localConnection);
												d2 = getBail(((BargainVO) localObject6).code, ((BargainVO) localObject6).price, l9,
														localCommodityVO1.userID, localConnection);
											}
											if (localCommodityVO1.tradeMode == 2) {
												((BargainVO) localObject6).s_poundage = getFee(((BargainVO) localObject6).code,
														((BargainVO) localObject6).price, ((BargainVO) localObject6).amount,
														((BargainVO) localObject6).userID, localConnection);
												((BargainVO) localObject6).b_poundage = getFee(((BargainVO) localObject6).code,
														((BargainVO) localObject6).price, ((BargainVO) localObject6).amount, localCommodityVO1.userID,
														localConnection);
												d3 = getFee(((BargainVO) localObject6).code, ((BargainVO) localObject6).price, l9,
														((BargainVO) localObject6).userID, localConnection);
												d4 = getFee(((BargainVO) localObject6).code, ((BargainVO) localObject6).price, l9,
														localCommodityVO1.userID, localConnection);
											}
											long l10 = this.TRADEDAO.addBargain((BargainVO) localObject6, localConnection);
											((BargainVO) localObject6).contractID = l10;
											setContractContent((BargainVO) localObject6, localConnection);
											this.TRADEDAO.modBargainContent((BargainVO) localObject6, localConnection);
											TradeUserVO localTradeUserVO = this.TRADEDAO.getTradeUserForUpdate(((BargainVO) localObject6).userID,
													localConnection);
											double d5 = 0.0D;
											if (localCommodityVO1.tradeMode == 2) {
												CommodityVO localCommodityVO2;
												CurSubmitVO localCurSubmitVO;
												if (((BargainVO) localObject6).userID.equals(localCommodityVO1.userID)) {
													localTradeUserVO.totalSecurity += ((BargainVO) localObject6).b_bail
															+ ((BargainVO) localObject6).b_poundage;
													localTradeUserVO.tradeCount += 1;
													localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
													localCallableStatement.setString(2, localTradeUserVO.userCode);
													localCallableStatement.setDouble(3,
															-1.0D * (((BargainVO) localObject6).b_bail + ((BargainVO) localObject6).b_poundage));
													localCallableStatement.setString(4, "4");
													localCallableStatement.registerOutParameter(1, 8);
													localCallableStatement.executeUpdate();
													this.TRADEDAO.modifyTradeUser(localTradeUserVO, localConnection);
													localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													localCallableStatement.setString(2, localTradeUserVO.userCode);
													localCallableStatement.setInt(3, 404);
													localCallableStatement.setDouble(4, ((BargainVO) localObject6).b_bail);
													localCallableStatement.setLong(5, ((BargainVO) localObject6).contractID);
													localCallableStatement.registerOutParameter(1, 8);
													localCallableStatement.executeUpdate();
													d5 = localCallableStatement.getDouble(1);
													addDailyMoney(d5, localTradeUserVO.paymentForGoods, l10, ((BargainVO) localObject6).userID,
															((BargainVO) localObject6).b_bail, 404, "", localConnection);
													localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													localCallableStatement.setString(2, localTradeUserVO.userCode);
													localCallableStatement.setInt(3, 403);
													localCallableStatement.setDouble(4, ((BargainVO) localObject6).b_poundage);
													localCallableStatement.setLong(5, ((BargainVO) localObject6).contractID);
													localCallableStatement.registerOutParameter(1, 8);
													localCallableStatement.executeUpdate();
													d5 = localCallableStatement.getDouble(1);
													addDailyMoney(d5, localTradeUserVO.paymentForGoods, l10, ((BargainVO) localObject6).userID,
															((BargainVO) localObject6).b_poundage, 403, "", localConnection);
													System.out.println();
													System.out.println("<<<<<<<<<<<<<<<<<<<  生成买方方流水" + ((BargainVO) localObject6).userID + " 保证金为 "
															+ ((BargainVO) localObject6).b_bail + "  手续费为 " + ((BargainVO) localObject6).b_poundage
															+ ">>>>>>>>>>");
													System.out.println();
													localCommodityVO2 = this.TRADEDAO.getCommodity(((BargainVO) localObject6).commodityID,
															localConnection);
													delCommodityCharge(((BargainVO) localObject6).commodityID, localConnection);
													localTradeUserVO = this.TRADEDAO.getTradeUserForUpdate(localCommodityVO2.userID, localConnection);
													localTradeUserVO.tradeCount += 1;
													this.TRADEDAO.modifyTradeUser(localTradeUserVO, localConnection);
													localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													localCallableStatement.setString(2, localTradeUserVO.userCode);
													localCallableStatement.setInt(3, 404);
													localCallableStatement.setDouble(4, ((BargainVO) localObject6).s_bail);
													localCallableStatement.setLong(5, ((BargainVO) localObject6).contractID);
													localCallableStatement.registerOutParameter(1, 8);
													localCallableStatement.executeUpdate();
													d5 = localCallableStatement.getDouble(1);
													addDailyMoney(d5, localTradeUserVO.paymentForGoods, l10, localCommodityVO2.userID,
															((BargainVO) localObject6).s_bail, 404, "", localConnection);
													localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													localCallableStatement.setString(2, localTradeUserVO.userCode);
													localCallableStatement.setInt(3, 403);
													localCallableStatement.setDouble(4, ((BargainVO) localObject6).s_poundage);
													localCallableStatement.setLong(5, ((BargainVO) localObject6).contractID);
													localCallableStatement.registerOutParameter(1, 8);
													localCallableStatement.executeUpdate();
													d5 = localCallableStatement.getDouble(1);
													addDailyMoney(d5, localTradeUserVO.paymentForGoods, l10, localCommodityVO2.userID,
															((BargainVO) localObject6).s_poundage, 403, "", localConnection);
													System.out.println();
													System.out.println("<<<<<<<<<<<<<<<<<<<  生成卖方方流水" + localCommodityVO2.userID + " 保证金为 "
															+ ((BargainVO) localObject6).s_bail + "  手续费为 " + ((BargainVO) localObject6).s_poundage
															+ ">>>>>>>>>>");
													System.out.println();
													this.TRADEDAO.dealCommodity(((BargainVO) localObject6).commodityID, localConnection);
													localCurSubmitVO = this.TRADEDAO.getCurSubmit(((BargainVO) localObject6).submitID);
													localCurSubmitVO.tradeFlag = 1;
													System.out.println("----------===========================  没用===================");
												} else {
													localTradeUserVO.totalSecurity += ((BargainVO) localObject6).s_bail
															+ ((BargainVO) localObject6).s_poundage;
													localTradeUserVO.tradeCount += 1;
													localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
													localCallableStatement.setString(2, localTradeUserVO.userCode);
													localCallableStatement.setDouble(3, -1.0D * (d1 + d3));
													localCallableStatement.setString(4, "4");
													localCallableStatement.registerOutParameter(1, 8);
													localCallableStatement.executeUpdate();
													this.TRADEDAO.modifyTradeUser(localTradeUserVO, localConnection);
													localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													localCallableStatement.setString(2, localTradeUserVO.userCode);
													localCallableStatement.setInt(3, 404);
													localCallableStatement.setDouble(4, ((BargainVO) localObject6).s_bail);
													localCallableStatement.setLong(5, ((BargainVO) localObject6).contractID);
													localCallableStatement.registerOutParameter(1, 8);
													localCallableStatement.executeUpdate();
													d5 = localCallableStatement.getDouble(1);
													addDailyMoney(d5, localTradeUserVO.paymentForGoods, l10, ((BargainVO) localObject6).userID,
															((BargainVO) localObject6).s_bail, 404, "", localConnection);
													localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													localCallableStatement.setString(2, localTradeUserVO.userCode);
													localCallableStatement.setInt(3, 403);
													localCallableStatement.setDouble(4, ((BargainVO) localObject6).s_poundage);
													localCallableStatement.setLong(5, ((BargainVO) localObject6).contractID);
													localCallableStatement.registerOutParameter(1, 8);
													localCallableStatement.executeUpdate();
													d5 = localCallableStatement.getDouble(1);
													addDailyMoney(d5, localTradeUserVO.paymentForGoods, l10, ((BargainVO) localObject6).userID,
															((BargainVO) localObject6).s_poundage, 403, "", localConnection);
													localCommodityVO2 = this.TRADEDAO.getCommodity(((BargainVO) localObject6).commodityID,
															localConnection);
													delCommodityCharge(((BargainVO) localObject6).commodityID, localConnection);
													localTradeUserVO = this.TRADEDAO.getTradeUserForUpdate(localCommodityVO2.userID, localConnection);
													localTradeUserVO.tradeCount += 1;
													this.TRADEDAO.modifyTradeUser(localTradeUserVO, localConnection);
													localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													localCallableStatement.setString(2, localTradeUserVO.userCode);
													localCallableStatement.setInt(3, 404);
													localCallableStatement.setDouble(4, ((BargainVO) localObject6).b_bail);
													localCallableStatement.setLong(5, ((BargainVO) localObject6).contractID);
													localCallableStatement.registerOutParameter(1, 8);
													localCallableStatement.executeUpdate();
													d5 = localCallableStatement.getDouble(1);
													addDailyMoney(d5, localTradeUserVO.paymentForGoods, l10, localCommodityVO2.userID,
															((BargainVO) localObject6).b_bail, 404, "", localConnection);
													localCallableStatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													localCallableStatement.setString(2, localTradeUserVO.userCode);
													localCallableStatement.setInt(3, 403);
													localCallableStatement.setDouble(4, ((BargainVO) localObject6).b_poundage);
													localCallableStatement.setLong(5, ((BargainVO) localObject6).contractID);
													localCallableStatement.registerOutParameter(1, 8);
													localCallableStatement.executeUpdate();
													d5 = localCallableStatement.getDouble(1);
													addDailyMoney(d5, localTradeUserVO.paymentForGoods, l10, localCommodityVO2.userID,
															((BargainVO) localObject6).b_poundage, 403, "", localConnection);
													this.TRADEDAO.dealCommodity(((BargainVO) localObject6).commodityID, localConnection);
													localCurSubmitVO = this.TRADEDAO.getCurSubmit(((BargainVO) localObject6).submitID);
													localCurSubmitVO.tradeFlag = 1;
												}
											}
											i8++;
											continue;
										}
									}
									if (localObject2 != null) {
										i8 = 0;
										if (i8 < ((List) localObject2).size()) {
											localObject6 = getCurCommodity(((TradeResuleValue) ((List) localObject2).get(i8)).getCode());
											((CommodityVO) localObject6).bargainFlag = 1;
											i8++;
											continue;
										}
									}
									modSysStatusDB(null, -1, null, 3, localConnection);
									modSysStatus(null, -1, null, 3, new Timestamp(l6));
								}
							}
						}
						localConnection.commit();
					}
				} catch (Exception localException1) {
					localException1 = localException1;
					localConnection.rollback();
					localException1.printStackTrace();
				} finally {
				}
				if ((n < 0) && (this.TRADESTATUS.getCurStatus() == 3)) {
					n = getNextSection();
					this.TRADESTATUS.setIsMaxTradeOrder(true);
					this.TRADESTATUS.setPartLastEndTime(new Timestamp(System.currentTimeMillis()));
					Thread.sleep(i);
				} else {
					this.TRADESTATUS.setIsMaxTradeOrder(false);
					Thread.sleep(i);
				}
			} catch (SQLException localSQLException2) {
				localSQLException2 = localSQLException2;
				try {
					localConnection = this.TRADEDAO.getJDBCConnection();
					localConnection.setAutoCommit(false);
				} catch (SQLException localSQLException3) {
					localSQLException3.printStackTrace();
				} catch (ClassNotFoundException localClassNotFoundException2) {
					localClassNotFoundException2.printStackTrace();
				}
			} catch (Exception localException2) {
				localException2 = localException2;
				localException2.printStackTrace();
			} finally {
			}
		}
	}

	public void recoverSysStatus() throws SQLException, Exception {
		Connection localConnection = null;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		String str = "";
		TradeStatusValue localTradeStatusValue = getTradeStatus();
		try {
			localConnection = this.TRADEDAO.getConnection();
			localConnection.setAutoCommit(false);
			str = "select * from v_sysCurStatus where tradePartition=" + this.PARTITIONID;
			localPreparedStatement = localConnection.prepareStatement(str);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				localTradeStatusValue.setCurStatus(localResultSet.getInt("status"));
				localTradeStatusValue.setLastPartID(localResultSet.getInt("section"));
				localTradeStatusValue.setPartStartTime(localResultSet.getTimestamp("starttime"));
				localTradeStatusValue.setPartEndTime(localResultSet.getTimestamp("endtime"));
				localTradeStatusValue.setPartLastEndTime(localResultSet.getTimestamp("endtime"));
			}
			reLoadCommodity(localConnection);
			reLoadFundInfo(localConnection);
			if (!localTradeStatusValue.getLoaded()) {
				localTradeStatusValue.setLoaded(true);
			}
			localConnection.commit();
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			localConnection.rollback();
			throw localSQLException;
		} catch (Exception localException) {
			localException.printStackTrace();
			localConnection.rollback();
			throw localException;
		} finally {
			localConnection.setAutoCommit(true);
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement, localConnection);
		}
	}

	public int optTrade() throws SQLException {
		Calendar localCalendar = Calendar.getInstance();
		String str1 = localCalendar.get(1) + "-" + localCalendar.get(2) + "-" + localCalendar.get(5) + "  " + localCalendar.get(11) + " : "
				+ localCalendar.get(12);
		int i = 2;
		int j = this.TRADESTATUS.getCurStatus();
		Timestamp localTimestamp = new Timestamp(System.currentTimeMillis());
		Connection localConnection = null;
		String str2 = "where tradePartition=" + this.PARTITIONID;
		System.out.println("===========================闭市处理开始=====================" + str1);
		System.out.println();
		System.out.println();
		if (j == 5) {
			System.out.println("===========================系统为闭市状态可进行结算操作=====================" + str1);
			System.out.println();
			System.out.println();
			try {
				localConnection = this.TRADEDAO.getJDBCConnection();
				localConnection.setAutoCommit(false);
				CurSubmitVO[] arrayOfCurSubmitVO = this.TRADEDAO.getCurSubmitList(str2, localConnection);
				if (arrayOfCurSubmitVO != null) {
					for (int k = 0; k < arrayOfCurSubmitVO.length; k++) {
						CurSubmitVO localCurSubmitVO = arrayOfCurSubmitVO[k];
						this.TRADEDAO.addHisSubmit(localTimestamp, localCurSubmitVO, localConnection);
					}
				}
				System.out.println("===========================导入历史委托=====================" + str1);
				System.out.println();
				System.out.println();
				this.TRADEDAO.delCurSubmit(str2, localConnection);
				System.out.println("===========================清空当日委托=====================" + str1);
				System.out.println();
				System.out.println();
				this.TRADEDAO.delTradeQuotation(str2, localConnection);
				System.out.println("===========================清空当日行情=====================" + str1);
				System.out.println();
				System.out.println();
				BargainVO[] arrayOfBargainVO = this.TRADEDAO.getBargainList(str2, localConnection);
				if (arrayOfBargainVO != null) {
					for (int m = 0; m < arrayOfBargainVO.length; m++) {
						BargainVO localBargainVO = arrayOfBargainVO[m];
						this.TRADEDAO.addHisBargain(localBargainVO, localConnection);
					}
				}
				System.out.println("===========================导入历史成交=====================" + str1);
				System.out.println();
				System.out.println();
				this.TRADEDAO.delBargain(str2, localConnection);
				System.out.println("===========================清空当日成交=====================" + str1);
				System.out.println();
				System.out.println();
				CommodityVO[] arrayOfCommodityVO = this.TRADEDAO.getCurCommodityList(str2, localConnection);
				if (arrayOfCommodityVO != null) {
					for (int n = 0; n < arrayOfCommodityVO.length; n++) {
						if (arrayOfCommodityVO[n].bargainFlag == 0) {
							delCommodityCharge(arrayOfCommodityVO[n].commodityid, localConnection);
						}
						System.out.println("===========================清退未成交标的的费用=====================" + str1);
						System.out.println();
						System.out.println();
						CommodityVO localCommodityVO = arrayOfCommodityVO[n];
						this.TRADEDAO.addHisCommodity(localTimestamp, localCommodityVO, localConnection);
						System.out.println("===========================添加历史商品=====================" + str1);
						System.out.println();
						System.out.println();
					}
				}
				System.out.println("===========================导入历史交易商品=====================" + str1);
				System.out.println();
				System.out.println();
				this.TRADEDAO.delCurCommodity(str2, localConnection);
				System.out.println("===========================清空当日交易商品=====================" + str1);
				System.out.println();
				System.out.println();
				this.TRADEDAO.selectIntoHismoney(localConnection);
				System.out.println("===========================导入历史资金流水=====================" + str1);
				System.out.println();
				System.out.println();
				this.TRADEDAO.truncateDailymoney(localConnection);
				System.out.println("===========================清空当日资金流水=====================" + str1);
				System.out.println();
				System.out.println();
				this.TRADEDAO.delTotalSecurity(localConnection);
				System.out.println("===========================清空交易商累计扣除保证金=====================" + str1);
				System.out.println();
				System.out.println();
				localConnection.commit();
			} catch (SQLException localSQLException) {
				localConnection.rollback();
				localSQLException.printStackTrace();
				i = 1;
			} catch (ClassNotFoundException localClassNotFoundException) {
				localClassNotFoundException.printStackTrace();
				i = 1;
			} finally {
				localConnection.setAutoCommit(true);
				this.TRADEDAO.closeStatement(null, null, localConnection);
			}
		} else {
			i = 0;
		}
		return i;
	}

	public int getFirmSubmitCount(String paramString1, String paramString2) throws SQLException {
		Connection localConnection = null;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		int i = 0;
		try {
			localConnection = this.TRADEDAO.getConnection();
			String str = "select count(id) from v_curSubmit where code='" + paramString2 + "' and userID='" + paramString1 + "' and tradeFlag!=3";
			localPreparedStatement = localConnection.prepareStatement(str);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				i = localResultSet.getInt(1);
			}
			localResultSet.close();
			localResultSet = null;
			localPreparedStatement.close();
			localPreparedStatement = null;
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement, localConnection);
		}
		return i;
	}

	public Map judgeCommodity(long paramLong1, String paramString, double paramDouble, long paramLong2) {
		Connection localConnection = null;
		try {
			localConnection = this.TRADEDAO.getConnection();
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
		}
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		ArrayList localArrayList = new ArrayList();
		HashMap localHashMap = new HashMap();
		long l1 = paramLong2;
		int i = 0;
		String str1 = "";
		try {
			String str2 = "select result from (select distinct(trunc((price+1000/amount),10)) result from v_curSubmit where code='" + paramString
					+ "' and tradePARTITIONID=" + this.PARTITIONID + " and tradeFlag=2) a order by result asc";
			localPreparedStatement = localConnection.prepareStatement(str2);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				localArrayList.add(localResultSet.getString("result"));
				System.out.println("result:" + localResultSet.getString("result"));
			}
			localPreparedStatement.close();
			localPreparedStatement = null;
			localResultSet.close();
			localResultSet = null;
			System.out.println("list.size():" + localArrayList.size());
			int j = 0;
			System.out.println("sign:" + l1);
			while (l1 > 0L) {
				System.out.println("sign:" + l1);
				String str3 = (String) localArrayList.get(i);
				System.out.println("sign1:" + i);
				str2 = "select count(*) count from (select userID,price,amount from v_curSubmit where code='" + paramString
						+ "' and trunc((price+1000/amount),10)=" + str3 + " and tradePARTITIONID=" + this.PARTITIONID + " and tradeFlag=2)";
				localPreparedStatement = localConnection.prepareStatement(str2);
				localResultSet = localPreparedStatement.executeQuery();
				if (localResultSet.next()) {
					j = localResultSet.getInt("count");
					System.out.println(paramString + "count:" + localResultSet.getInt("count"));
				}
				localPreparedStatement.close();
				localPreparedStatement = null;
				localResultSet.close();
				localResultSet = null;
				if (j == 1) {
					str2 = "select * from (select id,userID,price,amount from v_curSubmit where code='" + paramString
							+ "' and trunc((price+1000/amount),10)=" + str3 + " and tradePARTITIONID=" + this.PARTITIONID + " and tradeFlag=2 )";
					localPreparedStatement = localConnection.prepareStatement(str2);
					localResultSet = localPreparedStatement.executeQuery();
					if (localResultSet.next()) {
						TradeResuleValue localTradeResuleValue1 = new TradeResuleValue();
						localTradeResuleValue1.setCode(paramString);
						localTradeResuleValue1.setCommodityID(paramLong1);
						localTradeResuleValue1.setPrice(localResultSet.getDouble("price"));
						if (localResultSet.getLong("amount") < l1) {
							localTradeResuleValue1.setAmount(localResultSet.getLong("amount"));
							l1 -= localResultSet.getLong("amount");
							str1 = localResultSet.getString("amount");
						} else {
							str1 = l1 + "";
							localTradeResuleValue1.setAmount(l1);
							l1 = 0L;
						}
						localTradeResuleValue1.setUserId(localResultSet.getString("userID"));
						localTradeResuleValue1.setSubmitId(localResultSet.getLong("id"));
						localHashMap.put(localResultSet.getString("id"), str1);
						System.out.println("33333:" + localResultSet.getString("id") + ":" + str1);
					}
					localPreparedStatement.close();
					localPreparedStatement = null;
					localResultSet.close();
					localResultSet = null;
				} else if (j > 1) {
					int k = Integer.parseInt(new Configuration().getSection("MEBS.TradeParams").getProperty("equalityRule"));
					long l2;
					TradeResuleValue localTradeResuleValue2;
					if (k == 0) {
						str2 = "select * from (select id,userID,price,amount from v_curSubmit where code='" + paramString
								+ "'  and trunc((price+1000/amount),10)=" + str3 + " and tradePARTITIONID=" + this.PARTITIONID + " and tradeFlag=2)";
						localPreparedStatement = localConnection.prepareStatement(str2);
						localResultSet = localPreparedStatement.executeQuery();
						l2 = 0L;
						while (localResultSet.next()) {
							localTradeResuleValue2 = new TradeResuleValue();
							localTradeResuleValue2.setCode(paramString);
							localTradeResuleValue2.setCommodityID(paramLong1);
							localTradeResuleValue2.setPrice(localResultSet.getDouble("price"));
							if (localResultSet.getDouble("amount") * j < l1) {
								str1 = localResultSet.getLong("amount") + "";
								localTradeResuleValue2.setAmount(localResultSet.getLong("amount"));
								l2 = localResultSet.getLong("amount") * j;
							} else {
								str1 = l1 / j + "";
								localTradeResuleValue2.setAmount(l1 / j);
								l2 = l1;
							}
							localTradeResuleValue2.setUserId(localResultSet.getString("userID"));
							localTradeResuleValue2.setSubmitId(localResultSet.getLong("id"));
							localHashMap.put(localResultSet.getString("id"), str1);
						}
						l1 -= l2;
						System.out.println("shangxia:" + l1);
						localPreparedStatement.close();
						localPreparedStatement = null;
						localResultSet.close();
						localResultSet = null;
					} else if (k == 1) {
						str2 = "select * from (select id,userID,price,amount,submitTime from v_curSubmit where code='" + paramString
								+ "' and trunc((price+1000/amount),10)=" + str3 + " and tradePARTITIONID=" + this.PARTITIONID
								+ " and tradeFlag=2) order by submitTime";
						localPreparedStatement = localConnection.prepareStatement(str2);
						localResultSet = localPreparedStatement.executeQuery();
						l2 = 0L;
						while ((localResultSet.next()) && (l1 != 0L)) {
							localTradeResuleValue2 = new TradeResuleValue();
							localTradeResuleValue2.setCode(paramString);
							localTradeResuleValue2.setCommodityID(paramLong1);
							localTradeResuleValue2.setPrice(localResultSet.getDouble("price"));
							if (localResultSet.getDouble("amount") < l1) {
								localTradeResuleValue2.setAmount(localResultSet.getLong("amount"));
								l2 = localResultSet.getLong("amount");
							} else {
								localTradeResuleValue2.setAmount(l1);
								l2 = l1;
							}
							l1 -= l2;
							localTradeResuleValue2.setUserId(localResultSet.getString("userID"));
							localTradeResuleValue2.setSubmitId(localResultSet.getLong("id"));
							localHashMap.put(localResultSet.getString("id"), l2 + "");
						}
						localPreparedStatement.close();
						localPreparedStatement = null;
						localResultSet.close();
						localResultSet = null;
					}
				}
				j++;
				i++;
				System.out.println("count" + j);
				if ((localArrayList == null) || (localArrayList.size() <= i)) {
					break;
				}
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement, localConnection);
		}
		return localHashMap;
	}

	public int abandonBargain(long paramLong, String paramString) throws Exception {
		Connection localConnection = null;
		PreparedStatement localPreparedStatement1 = null;
		ResultSet localResultSet1 = null;
		int i = 0;
		try {
			localConnection = this.TRADEDAO.getConnection();
			localConnection.setAutoCommit(false);
			String str1 = "select price,amount,userID from v_cursubmit where code='" + paramString + "' and tradeFlag=2 and  tradePARTITIONID="
					+ this.PARTITIONID;
			localPreparedStatement1 = localConnection.prepareStatement(str1);
			localResultSet1 = localPreparedStatement1.executeQuery();
			while (localResultSet1.next()) {
				double d1 = 0.0D;
				long l = 0L;
				String str2 = "";
				double d2 = 0.0D;
				double d3 = 0.0D;
				d1 = localResultSet1.getDouble(1);
				l = localResultSet1.getLong(2);
				str2 = localResultSet1.getString(3);
				str1 = "select feecut,feecutfee from v_tradeUser where usercode='" + str2 + "'";
				PreparedStatement localPreparedStatement2 = localConnection.prepareStatement(str1);
				ResultSet localResultSet2 = localPreparedStatement2.executeQuery();
				if (localResultSet2.next()) {
					d2 = getCommodityBail(paramString, d1, l, localResultSet2.getDouble("feecut"));
					d3 = getCommodityFee(paramString, d1, l, localResultSet2.getDouble("feecutfee"));
				}
				localPreparedStatement2.close();
				localPreparedStatement2 = null;
				localResultSet2.close();
				localResultSet2 = null;
				int j = 0;
				str1 = "select category from v_commodity where id=" + paramLong;
				localPreparedStatement2 = localConnection.prepareStatement(str1);
				localResultSet2 = localPreparedStatement2.executeQuery();
				if (localResultSet2.next()) {
					j = localResultSet2.getInt("category");
				}
				localPreparedStatement2.close();
				localPreparedStatement2 = null;
				localResultSet2.close();
				localResultSet2 = null;
				double d4 = 0.0D;
				str1 = "select deductedRatingBail from v_tradeuser where usercode='" + str2 + "'";
				localPreparedStatement2 = localConnection.prepareStatement(str1);
				localResultSet2 = localPreparedStatement2.executeQuery();
				while (localResultSet2.next()) {
					d4 = localResultSet2.getDouble(1);
				}
				localPreparedStatement2.close();
				localPreparedStatement2 = null;
				localResultSet2.close();
				localResultSet2 = null;
				if ((j != 1) && (j != 2) && (j != 3) && (j != 5) && (j == 4)) {
					str1 = "update v_tradeUser set frozenCapital=frozenCapital-" + d3 + " where userCode='" + str2 + "'";
					localPreparedStatement2 = localConnection.prepareStatement(str1);
					localPreparedStatement2.executeUpdate();
					localPreparedStatement2.close();
					localPreparedStatement2 = null;
				}
				double d5 = d2 + d3;
				str1 = "update v_tradeUser set frozenCapital=frozenCapital-" + d5 + " where userCode='" + str2 + "'";
				localPreparedStatement2 = localConnection.prepareStatement(str1);
				localPreparedStatement2.executeUpdate();
				localPreparedStatement2.close();
				localPreparedStatement2 = null;
			}
			localPreparedStatement1.close();
			localPreparedStatement1 = null;
			localResultSet1.close();
			localResultSet1 = null;
			str1 = "update v_cursubmit set tradeFlag=3 where code='" + paramString + "' and tradePARTITIONID=" + this.PARTITIONID;
			localPreparedStatement1 = localConnection.prepareStatement(str1);
			localPreparedStatement1.executeUpdate();
			localPreparedStatement1.close();
			localPreparedStatement1 = null;
			str1 = "update v_commodity set status=2 where id=" + paramLong;
			localPreparedStatement1 = localConnection.prepareStatement(str1);
			localPreparedStatement1.executeUpdate();
			localPreparedStatement1.close();
			localPreparedStatement1 = null;
			CommodityVO localCommodityVO = getCurCommodity(paramString);
			if (localCommodityVO != null) {
				localCommodityVO.bargainFlag = 0;
			}
			str1 = "update v_curCommodity set bargainFlag=0 where code='" + paramString + "' and tradePARTITIONID=" + this.PARTITIONID;
			localPreparedStatement1 = localConnection.prepareStatement(str1);
			localPreparedStatement1.executeUpdate();
			localPreparedStatement1.close();
			localPreparedStatement1 = null;
			localConnection.commit();
			i = 1;
		} catch (Exception localException) {
			localConnection.rollback();
			localException.printStackTrace();
			i = -1;
			throw localException;
		} finally {
			localConnection.setAutoCommit(true);
			this.TRADEDAO.closeStatement(localResultSet1, localPreparedStatement1, localConnection);
		}
		return i;
	}

	public Map getCommodity(long paramLong, String paramString) throws Exception {
		Connection localConnection = null;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		HashMap localHashMap = new HashMap();
		try {
			localConnection = this.TRADEDAO.getConnection();
			String str1 = "select id,validAmount from v_curSubmit where code='" + paramString;
			localPreparedStatement = localConnection.prepareStatement(str1);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				String str2 = localResultSet.getString(1);
				String str3 = localResultSet.getString(2);
				localHashMap.put(str2, str3);
			}
			localResultSet.close();
			localResultSet = null;
			localPreparedStatement.close();
			localPreparedStatement = null;
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement, localConnection);
		}
		return localHashMap;
	}

	public void finishContract(Connection paramConnection, long paramLong, int paramInt, double paramDouble1, double paramDouble2,
			double paramDouble3) throws SQLException, Exception {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		String str1 = "";
		try {
			double d1 = 0.0D;
			double d2 = 0.0D;
			double d3 = 0.0D;
			String str2 = "";
			long l1 = 0L;
			int i = 1;
			long l2 = 0L;
			FundInfoValue localFundInfoValue = getFundInfo(str2);
			double d4 = localFundInfoValue.balance;
			double d5 = 0.0D;
			double d6 = 0.0D;
			double d7 = 0.0D;
			double d8 = 0.0D;
			double d9 = 0.0D;
			double d10 = 0.0D;
			double d11 = 0.0D;
			double d12 = 0.0D;
			double d13 = 0.0D;
			int j = -1;
			int k = -1;
			double d14 = 0.0D;
			double d15 = 0.0D;
			double d16 = 0.0D;
			double d17 = 0.0D;
			double d18 = 0.0D;
			String str3 = "";
			d9 = Double.parseDouble(new Configuration().getSection("MEBS.TradeParams").getProperty("PerformanceBond1"));
			str1 = "select h.price,h.userid,h.commodityID,h.amount,h.lastBail,h.poundage,c.str7,d.security,d.fee,h.bail,h.status,c.num1,d.category,h.lastBail,d.financescale,d.LKuser from v_hisBargain h,v_commExt c,v_commodity d where h.commodityID=c.commid and h.commodityid=d.id and h.contractID="
					+ paramLong;
			localPreparedStatement = paramConnection.prepareStatement(str1);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				d2 = localResultSet.getDouble(1);
				str2 = localResultSet.getString(2);
				l1 = localResultSet.getLong(3);
				l2 = localResultSet.getLong(4);
				d5 = localResultSet.getDouble(5);
				d6 = localResultSet.getDouble(6);
				try {
					i = Integer.parseInt(localResultSet.getString(7));
				} catch (Exception localException2) {
					localException2.printStackTrace();
				}
				d11 = localResultSet.getDouble(8);
				d10 = localResultSet.getDouble(9);
				d12 = localResultSet.getDouble(10);
				j = localResultSet.getInt(11);
				d8 = localResultSet.getDouble(12);
				k = localResultSet.getInt(13);
				d16 = localResultSet.getDouble(14);
				d18 = localResultSet.getDouble(15);
				str3 = localResultSet.getString(16);
			}
			localResultSet.close();
			localResultSet = null;
			localPreparedStatement.close();
			localPreparedStatement = null;
			if ((str3 == null) || ("".equals(str3))) {
				throw new Exception("interface finishContract error: invalid LKuser.");
			}
			int m = 0;
			int n = Integer.parseInt(new Configuration().getSection("MEBS.TradeParams").getProperty("ScaleFee"));
			if (n == 1) {
				double d19 = 0.0D;
				str1 = "select sum(money) from v_hisMoney where ContractNo=" + paramLong + " and Operation=417 and FirmID='" + str3 + "' and Market2="
						+ i;
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localResultSet = localPreparedStatement.executeQuery();
				if (localResultSet.next()) {
					d19 = localResultSet.getDouble(1);
				}
				localResultSet.close();
				localResultSet = null;
				localPreparedStatement.close();
				localPreparedStatement = null;
				d7 = Arith.format(Arith.sub(d6, d19), 2);
			} else if (n == 0) {
				try {
					int i1 = 1;
					if (i1 == 1) {
						d7 = 0.0D;
						m = 1;
					} else {
						d7 = d6;
					}
				} catch (Exception localException3) {
					localException3.printStackTrace();
				}
			}
			double d20 = 0.0D;
			double d21 = 0.0D;
			str1 = "select operation,sum(Money) from v_hisMoney where (operation=419 or operation=421) and contractno=" + paramLong + " and Market2="
					+ i + " group by operation";
			localPreparedStatement = paramConnection.prepareStatement(str1);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				if (localResultSet.getInt(1) == 419) {
					d20 = localResultSet.getDouble(2);
				} else if (localResultSet.getInt(1) == 421) {
					d21 = localResultSet.getDouble(2);
				}
			}
			localResultSet.close();
			localResultSet = null;
			localPreparedStatement.close();
			localPreparedStatement = null;
			d13 = Arith.format(Arith.sub(d20, d21), 2);
			double d22 = Arith.format(Arith.mul(paramDouble1, d2), 2);
			d13 = Arith.format(Arith.sub(d13, d22), 2);
			double d23 = Arith.format(Arith.sub(1.0F, d18), 4);
			d22 = Arith.format(Arith.mul(d13, d23), 2);
			double d24 = Arith.format(Arith.mul(d13, d18), 2);
			if (j != 0) {
				throw new Exception("interface finishContract error: invalid hiscontract status.");
			}
			double d25 = Arith.format(Arith.mul(paramDouble2, d2), 2);
			str1 = "update v_hisBargain set status=1,actualAmount=?,fellBackAmount=?,result=?,hangAmount=? where contractID=" + paramLong;
			localPreparedStatement = paramConnection.prepareStatement(str1);
			localPreparedStatement.setDouble(1, paramDouble1);
			localPreparedStatement.setDouble(2, paramDouble2);
			localPreparedStatement.setInt(3, paramInt);
			localPreparedStatement.setDouble(4, paramDouble3);
			localPreparedStatement.executeUpdate();
			localPreparedStatement.close();
			localPreparedStatement = null;
			double d26 = Arith.format(d9 * paramDouble3 - Arith.format(Arith.mul(Arith.mul(d2, d10), paramDouble3), 2), 2);
			if (k == 4) {
				d26 = 0.0D;
			}
			double d27 = Arith.format(Arith.div(Arith.mul(d6, paramDouble3), (float) l2), 2);
			System.out.println("hangFee:" + d27);
			d17 = d16;
			double d28 = 0.0D;
			double d29;
			if (paramInt == 0) {
				str1 = "update v_hisbargain set lastBail=0 where contractID=" + paramLong;
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, str3);
				localPreparedStatement.setInt(3, 421);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, d22);
				localPreparedStatement.setDouble(6, 0.0D);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				d28 += d22;
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, "CZ");
				localPreparedStatement.setInt(3, 421);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, d24);
				localPreparedStatement.setDouble(6, 0.0D);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				if (m == 0) {
					if (l2 == paramDouble1) {
						d29 = 0.0D;
						str1 = "select sum(Money) from v_hisMoney where operation=417 and contractno=" + paramLong + " and firmid='" + str3
								+ "' and  Market2=" + i + " group by operation";
						localPreparedStatement = paramConnection.prepareStatement(str1);
						localResultSet = localPreparedStatement.executeQuery();
						while (localResultSet.next()) {
							d29 = localResultSet.getDouble(1);
						}
						localResultSet.close();
						localResultSet = null;
						localPreparedStatement.close();
						localPreparedStatement = null;
						d7 = Arith.format(Arith.sub(d6, d29), 2);
					}
					str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
					localPreparedStatement = paramConnection.prepareStatement(str1);
					localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
					localPreparedStatement.setString(2, str3);
					localPreparedStatement.setInt(3, 417);
					localPreparedStatement.setLong(4, paramLong);
					localPreparedStatement.setDouble(5, d7 - d27);
					localPreparedStatement.setDouble(6, d4);
					localPreparedStatement.setInt(7, i);
					localPreparedStatement.setInt(8, i);
					localPreparedStatement.executeUpdate();
					localPreparedStatement.close();
					localPreparedStatement = null;
					d28 = Arith.sub(d28, d7 - d27);
				} else {
					str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
					localPreparedStatement = paramConnection.prepareStatement(str1);
					localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
					localPreparedStatement.setString(2, str3);
					localPreparedStatement.setInt(3, 417);
					localPreparedStatement.setLong(4, paramLong);
					localPreparedStatement.setDouble(5, -d27);
					localPreparedStatement.setDouble(6, d4);
					localPreparedStatement.setInt(7, i);
					localPreparedStatement.setInt(8, i);
					localPreparedStatement.executeUpdate();
					localPreparedStatement.close();
					localPreparedStatement = null;
					d28 = Arith.sub(d28, -d27);
				}
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, str2);
				localPreparedStatement.setInt(3, 423);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, d26);
				localPreparedStatement.setDouble(6, d4);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				d14 = Arith.format(Arith.sub(d14, d26), 2);
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, str2);
				localPreparedStatement.setInt(3, 420);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, d17 - d26);
				localPreparedStatement.setDouble(6, d4);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				d14 = Arith.format(Arith.add(d14, d17), 2);
			} else if (paramInt == 1) {
				d29 = Arith.format(d9 * paramDouble2 - Arith.format(Arith.mul(Arith.mul(d2, d10), paramDouble2), 2), 2);
				if (k == 4) {
					d29 = 0.0D;
				}
				d1 = Arith.format(Arith.sub(d22, d29), 2);
				d1 = Arith.format(Arith.sub(d22, Arith.format((d11 - d9) * paramDouble2, 2)), 2);
				double d30 = Arith.format(Arith.div(d1, d2), 2);
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, str3);
				localPreparedStatement.setInt(3, 423);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, d29);
				localPreparedStatement.setDouble(6, d4);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				d28 -= d29;
				str1 = "update v_hisbargain set lastBail=lastBail-? where contractID=" + paramLong;
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setDouble(1, d29);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, str2);
				localPreparedStatement.setInt(3, 422);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, Arith.format((d11 - d9) * paramDouble2, 2));
				localPreparedStatement.setDouble(6, d4);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, str3);
				localPreparedStatement.setInt(3, 425);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, Arith.format((d11 - d9) * paramDouble2, 2));
				localPreparedStatement.setDouble(6, d4);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				d15 = Arith.format(Arith.add(d14, Arith.format((d11 - d9) * paramDouble2, 2)), 2);
				d28 = Arith.sub(d28, Arith.format((d11 - d9) * paramDouble2, 2));
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, str3);
				localPreparedStatement.setInt(3, 421);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, d22);
				localPreparedStatement.setDouble(6, 0.0D);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				d28 = Arith.add(d28, d22);
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, "CZ");
				localPreparedStatement.setInt(3, 421);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, d24);
				localPreparedStatement.setDouble(6, 0.0D);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				if (m == 0) {
					str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
					localPreparedStatement = paramConnection.prepareStatement(str1);
					localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
					localPreparedStatement.setString(2, str3);
					localPreparedStatement.setInt(3, 417);
					localPreparedStatement.setLong(4, paramLong);
					localPreparedStatement.setDouble(5, d7 - d27);
					localPreparedStatement.setDouble(6, d4);
					localPreparedStatement.setInt(7, i);
					localPreparedStatement.setInt(8, i);
					localPreparedStatement.executeUpdate();
					localPreparedStatement.close();
					localPreparedStatement = null;
					d28 = Arith.sub(d28, d7 - d27);
				} else {
					str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
					localPreparedStatement = paramConnection.prepareStatement(str1);
					localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
					localPreparedStatement.setString(2, str3);
					localPreparedStatement.setInt(3, 417);
					localPreparedStatement.setLong(4, paramLong);
					localPreparedStatement.setDouble(5, -d27);
					localPreparedStatement.setDouble(6, d4);
					localPreparedStatement.setInt(7, i);
					localPreparedStatement.setInt(8, i);
					localPreparedStatement.executeUpdate();
					localPreparedStatement.close();
					localPreparedStatement = null;
					d28 = Arith.sub(d28, -d27);
				}
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, str2);
				localPreparedStatement.setInt(3, 423);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, d26);
				localPreparedStatement.setDouble(6, d4);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				d14 = Arith.format(Arith.sub(d14, d26), 2);
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, str2);
				localPreparedStatement.setInt(3, 420);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, d17 - d26);
				localPreparedStatement.setDouble(6, d4);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				d14 = Arith.format(Arith.add(d14, d17), 2);
				str1 = "update v_hisbargain set lastBail=0 where contractID=" + paramLong;
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
			} else if (paramInt == 2) {
				str1 = "updatev_ hisbargain set lastBail=0 where contractID=" + paramLong;
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				if (m == 0) {
					str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
					localPreparedStatement = paramConnection.prepareStatement(str1);
					localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
					localPreparedStatement.setString(2, str3);
					localPreparedStatement.setInt(3, 417);
					localPreparedStatement.setLong(4, paramLong);
					localPreparedStatement.setDouble(5, d7 - d27);
					localPreparedStatement.setDouble(6, d4);
					localPreparedStatement.setInt(7, i);
					localPreparedStatement.setInt(8, i);
					localPreparedStatement.executeUpdate();
					localPreparedStatement.close();
					localPreparedStatement = null;
					d28 -= d7 - d27;
				} else {
					str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
					localPreparedStatement = paramConnection.prepareStatement(str1);
					localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
					localPreparedStatement.setString(2, str3);
					localPreparedStatement.setInt(3, 417);
					localPreparedStatement.setLong(4, paramLong);
					localPreparedStatement.setDouble(5, -d27);
					localPreparedStatement.setDouble(6, d4);
					localPreparedStatement.setInt(7, i);
					localPreparedStatement.setInt(8, i);
					localPreparedStatement.executeUpdate();
					localPreparedStatement.close();
					localPreparedStatement = null;
					d28 -= -d27;
				}
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				d4 += d3;
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, str3);
				localPreparedStatement.setInt(3, 421);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, d22);
				localPreparedStatement.setDouble(6, d4);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				d28 = Arith.add(d28, d22);
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				d4 += d3;
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, "CZ");
				localPreparedStatement.setInt(3, 421);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, d24);
				localPreparedStatement.setDouble(6, d4);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, str3);
				localPreparedStatement.setInt(3, 422);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, Arith.format((d11 - d9) * paramDouble2, 2));
				localPreparedStatement.setDouble(6, d4);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				d14 = Arith.format(Arith.sub(d14, Arith.format((d11 - d9) * paramDouble2, 2)), 2);
				d28 = Arith.format(Arith.add(d28, Arith.format((d11 - d9) * paramDouble2, 2)), 2);
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, str2);
				localPreparedStatement.setInt(3, 423);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, d26);
				localPreparedStatement.setDouble(6, d4);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				d14 = Arith.format(Arith.sub(d14, d26), 2);
				d29 = Arith.format(d9 * paramDouble2 - Arith.format(Arith.mul(Arith.mul(d2, d10), paramDouble2), 2), 2);
				if (k == 4) {
					d29 = 0.0D;
				}
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, str2);
				localPreparedStatement.setInt(3, 423);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, d29);
				localPreparedStatement.setDouble(6, d4);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				d14 = Arith.format(Arith.sub(d14, d29), 2);
				str1 = "insert into v_hisMoney(ID,InfoDate,FirmID,Operation,ContractNo,Money,Balance,Overdraft,frozenCapital,Market1,Market2) values(sp_v_dailyMoney.Nextval,?,?,?,?,?,?,0,0,?,?)";
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				localPreparedStatement.setString(2, str2);
				localPreparedStatement.setInt(3, 420);
				localPreparedStatement.setLong(4, paramLong);
				localPreparedStatement.setDouble(5, d17 - d26 - d29 - Arith.format((d11 - d9) * paramDouble2, 2));
				localPreparedStatement.setDouble(6, d4);
				localPreparedStatement.setInt(7, i);
				localPreparedStatement.setInt(8, i);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				str1 = "update v_hisbargain set lastBail=0 where contractID=" + paramLong;
				localPreparedStatement = paramConnection.prepareStatement(str1);
				localPreparedStatement.executeUpdate();
				localPreparedStatement.close();
				localPreparedStatement = null;
				d14 = Arith.format(Arith.add(d14, d17), 2);
			}
			str1 = "update v_tradeuser set balance=balance+" + d14 + ",paymentForUser=paymentForUser+" + d15 + " where usercode='" + str2 + "'";
			localPreparedStatement = paramConnection.prepareStatement(str1);
			localPreparedStatement.executeUpdate();
			localPreparedStatement.close();
			localPreparedStatement = null;
			str1 = "update v_tradeusermarket set balance=balance+" + d14 + ",paymentForUser=paymentForUser+" + d15 + " where usercode='" + str2
					+ "' and marketid=" + i;
			localPreparedStatement = paramConnection.prepareStatement(str1);
			localPreparedStatement.executeUpdate();
			localPreparedStatement.close();
			localPreparedStatement = null;
			str1 = "update v_tradeuser set balance=balance+" + d28 + " where usercode='" + str3 + "'";
			localPreparedStatement = paramConnection.prepareStatement(str1);
			localPreparedStatement.executeUpdate();
			localPreparedStatement.close();
			localPreparedStatement = null;
			str1 = "update v_tradeusermarket set balance=balance+" + d28 + " where usercode='" + str3 + "' and marketid=" + i;
			localPreparedStatement = paramConnection.prepareStatement(str1);
			localPreparedStatement.executeUpdate();
			localPreparedStatement.close();
			localPreparedStatement = null;
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} catch (Exception localException1) {
			localException1.printStackTrace();
			throw localException1;
		} finally {
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement, null);
		}
	}

	public double getCommodityBail(String paramString, double paramDouble1, long paramLong, double paramDouble2) throws Exception {
		try {
			double d1 = 0.0D;
			Configuration localConfiguration = new Configuration();
			CommodityVO localCommodityVO = getCurCommodity(paramString);
			int i = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("PayType"));
			int j = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("SecurityBySelf"));
			int k = 1;
			if (j == 0) {
				k = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("SecurityType"));
			} else {
				k = Integer.parseInt(localCommodityVO.str7);
			}
			double d2 = localCommodityVO.tradeUnit;
			int m = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("SecuritySiscount"));
			double d3 = 1.0D;
			if (m == 1) {
				System.out.println("=======保证金计算折扣=============");
				d3 = paramDouble2;
			}
			if ((i == 0) || (i == 1)) {
				if (k == 0) {
					if (localCommodityVO.tradeMode == 2) {
						d1 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.s_security, d3), localCommodityVO.tradeUnit) * paramLong, 2);
					}
				} else if ((k == 1) && (localCommodityVO.tradeMode == 2)) {
					d1 = Arith.format(
							Arith.mul(Arith.mul(Arith.mul(paramDouble1, localCommodityVO.s_security), d3), localCommodityVO.tradeUnit) * paramLong,
							2);
				}
			}
			double d4 = d1;
			return d4;
		} catch (Exception localException) {
			localException = localException;
			localException.printStackTrace();
			throw localException;
		} finally {
		}
	}

	public double getCommodityFee(String paramString, double paramDouble1, long paramLong, double paramDouble2) throws Exception {
		try {
			double d1 = 0.0D;
			Configuration localConfiguration = new Configuration();
			CommodityVO localCommodityVO = getCurCommodity(paramString);
			int i = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("PayType"));
			int j = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("FeeBySelf"));
			int k = 1;
			if (j == 0) {
				k = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("FeeType"));
			} else {
				k = Integer.parseInt(localCommodityVO.str8);
			}
			int m = Integer.parseInt(localConfiguration.getSection("MEBS.TradeParams").getProperty("FeeSiscount"));
			double d2 = localCommodityVO.tradeUnit;
			double d3 = 1.0D;
			if (m == 1) {
				System.out.println("=======手续费计算折扣=============");
				d3 = paramDouble2;
			}
			if (i == 1) {
				if (k == 0) {
					if (localCommodityVO.tradeMode == 2) {
						d1 += Arith.format(Arith.mul(Arith.mul(localCommodityVO.s_fee, d3), localCommodityVO.tradeUnit) * paramLong, 2);
					}
				} else if ((k == 1) && (localCommodityVO.tradeMode == 2)) {
					d1 += Arith.format(
							Arith.mul(Arith.mul(Arith.mul(paramDouble1, localCommodityVO.s_fee), d3), localCommodityVO.tradeUnit) * paramLong, 2);
				}
			}
			double d4 = d1;
			return d4;
		} catch (Exception localException) {
			localException = localException;
			localException.printStackTrace();
			throw localException;
		} finally {
		}
	}

	public TradeCommodityM1 getCommodity(String paramString) {
		TradeCommodityList1 localTradeCommodityList1 = new TradeCommodityList1();
		Hashtable localHashtable = (Hashtable) TradeCommodityList1.list.get(this.PARTITIONID + "");
		if (localHashtable == null) {
			localHashtable = new Hashtable();
		}
		TradeCommodityM1 localTradeCommodityM1 = (TradeCommodityM1) localHashtable.get(paramString);
		return localTradeCommodityM1;
	}

	public FundInfoValue getFundInfo(String paramString) {
		FundInfoList localFundInfoList = new FundInfoList();
		FundInfoValue localFundInfoValue = (FundInfoValue) FundInfoList.list.get(paramString);
		if (localFundInfoValue == null) {
			localFundInfoValue = new FundInfoValue();
		}
		FundInfoList.list.put(paramString, localFundInfoValue);
		Connection localConnection = null;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		String str = "";
		try {
			localConnection = this.TRADEDAO.getConnection();
			str = "select t3.balance,t1.feecut,t3.FROZENFUNDS,t1.overdraft,t1.totalSecurity,t2.name,t1.paymentForGoods,t1.deductedRatingBail,t1.curTradeAmount from v_tradeUser t1,f_firm t2,f_firmfunds t3 where t1.userCode=t2.firmid and t1.usercode=t3.firmid and t1.usercode='"
					+ paramString + "'";
			localPreparedStatement = localConnection.prepareStatement(str);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				localFundInfoValue.balance = localResultSet.getDouble(1);
				System.out.println("fiv.balance:" + localFundInfoValue.balance);
				localFundInfoValue.feecut = localResultSet.getDouble(2);
				localFundInfoValue.frozenCapital = localResultSet.getDouble(3);
				localFundInfoValue.overdraft = localResultSet.getDouble(4);
				localFundInfoValue.totalSecurity = localResultSet.getDouble(5);
				localFundInfoValue.userName = localResultSet.getString(6);
				localFundInfoValue.paymentForGoods = localResultSet.getDouble(7);
				localFundInfoValue.deductedRatingBail = localResultSet.getDouble(8);
				localFundInfoValue.curTradeAmount = localResultSet.getLong(9);
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
		} finally {
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement, localConnection);
		}
		return localFundInfoValue;
	}

	public double getCommodityCharge(String paramString, double paramDouble1, long paramLong, double paramDouble2, double paramDouble3)
			throws Exception {
		try {
			double d1 = 0.0D;
			d1 = getCommodityBail(paramString, paramDouble1, paramLong, paramDouble2)
					+ getCommodityFee(paramString, paramDouble1, paramLong, paramDouble3);
			double d2 = d1;
			return d2;
		} catch (Exception localException) {
			localException = localException;
			localException.printStackTrace();
			throw localException;
		} finally {
		}
	}

	public ObjSet getCommodityList(int paramInt1, int paramInt2) {
		TradeCommodityList1 localTradeCommodityList1 = new TradeCommodityList1();
		Hashtable localHashtable = (Hashtable) TradeCommodityList1.list.get(this.PARTITIONID + "");
		if (localHashtable != null) {
			CommodityVO[] arrayOfCommodityVO = new CommodityVO[localHashtable.size()];
			Enumeration localEnumeration = localHashtable.elements();
			for (int i = 0; localEnumeration.hasMoreElements(); i++) {
				CommodityVO localCommodityVO1 = (CommodityVO) localEnumeration.nextElement();
				arrayOfCommodityVO[i] = localCommodityVO1;
			}
			for (int i = 0; i < arrayOfCommodityVO.length; i++) {
				for (int j = 0; j < arrayOfCommodityVO.length - i - 1; j++) {
					if (arrayOfCommodityVO[j].section > arrayOfCommodityVO[(j + 1)].section) {
						CommodityVO localCommodityVO2 = arrayOfCommodityVO[j];
						arrayOfCommodityVO[j] = arrayOfCommodityVO[(j + 1)];
						arrayOfCommodityVO[(j + 1)] = localCommodityVO2;
					}
				}
			}
			return ObjSet.getInstance(arrayOfCommodityVO, localHashtable.size(), paramInt2, paramInt1);
		}
		return ObjSet.getInstance(null, 0, 0, 0);
	}

	public ObjSet getCurCommodityList(int paramInt1, int paramInt2) throws SQLException {
		TradeStatusValue localTradeStatusValue = getTradeStatus();
		TradeCommodityList1 localTradeCommodityList1 = new TradeCommodityList1();
		Hashtable localHashtable = (Hashtable) TradeCommodityList1.list.get(this.PARTITIONID + "");
		if ((localHashtable != null) && (localTradeStatusValue.getCurStatus() == 2)) {
			Vector localVector = new Vector();
			Enumeration localEnumeration = localHashtable.elements();
			for (int i = 0; localEnumeration.hasMoreElements(); i++) {
				CommodityVO localCommodityVO = (CommodityVO) localEnumeration.nextElement();
				if (localCommodityVO.section == localTradeStatusValue.getLastPartID()) {
					localVector.add(localCommodityVO);
				}
			}
			CommodityVO[] arrayOfCommodityVO = new CommodityVO[localVector.size()];
			for (int j = 0; j < localVector.size(); j++) {
				arrayOfCommodityVO[j] = ((CommodityVO) localVector.elementAt(j));
			}
			return ObjSet.getInstance(arrayOfCommodityVO, localVector.size(), paramInt2, paramInt1);
		}
		return ObjSet.getInstance(null, 0, 0, 0);
	}

	public void renewFundInfo(String paramString, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
		FundInfoValue localFundInfoValue = getFundInfo(paramString);
		if (localFundInfoValue != null) {
			synchronized (localFundInfoValue) {
				localFundInfoValue.balance += paramDouble1;
				localFundInfoValue.frozenCapital += paramDouble2;
				localFundInfoValue.overdraft += paramDouble3;
				localFundInfoValue.totalSecurity += paramDouble4;
			}
		}
	}

	public Hashtable getCommodity() {
		TradeCommodityList1 localTradeCommodityList1 = new TradeCommodityList1();
		Hashtable localHashtable = (Hashtable) TradeCommodityList1.list.get(this.PARTITIONID);
		return localHashtable;
	}

	public void reLoadCommodity() throws SQLException {
		Connection localConnection = null;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		try {
			localConnection = this.TRADEDAO.getConnection();
			String str1 = "";
			double d1 = 0.0D;
			String str2 = "";
			long l1 = 0L;
			Timestamp localTimestamp = null;
			double d2 = 0.0D;
			double d3 = 0.0D;
			double d4 = 0.0D;
			double d5 = 0.0D;
			double d6 = 0.0D;
			int j = 0;
			String str3 = "";
			String str4 = "";
			String str5 = "";
			String str6 = "";
			String str7 = "";
			String str8 = "";
			String str9 = "";
			String str10 = "";
			String str11 = "";
			String str12 = "";
			long l2 = 0L;
			double d7 = 0.0D;
			double d8 = 0.0D;
			int k = 0;
			int m = -1;
			double d9 = 0.0D;
			long l3 = 0L;
			int n = 0;
			double d10 = 0.0D;
			long l4 = 0L;
			CommodityVO localCommodityVO2 = null;
			Hashtable localHashtable = new Hashtable();
			String str13 = "select c.code,t.price,t.userID,t.VAIDAMOUNT,t.lastTime,d.tradeUnit,d.b_security,d.s_security,d.b_fee,d.s_fee,c.section,e.str1,e.str2,e.str3,e.str4,d.amount,d.beginPrice,d.stepPrice,e.str5,e.str11,e.str12,e.str16,e.str17,c.bargainFlag,e.str7,d.alertPrice from v_curCommodity c,v_tradeQuotation t,v_commodity d,v_commExt e where e.commID(+)=d.ID and d.id=c.commodityID and t.tradePartition(+)="
					+ this.PARTITIONID + " and c.tradePartition=" + this.PARTITIONID + " and c.code=t.code(+)";
			localPreparedStatement = localConnection.prepareStatement(str13);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				str1 = localResultSet.getString(1);
				d1 = localResultSet.getDouble(2);
				str2 = localResultSet.getString(3);
				l1 = localResultSet.getLong(4);
				localTimestamp = localResultSet.getTimestamp(5);
				d2 = localResultSet.getDouble(6);
				d3 = localResultSet.getDouble(7);
				d4 = localResultSet.getDouble(8);
				d5 = localResultSet.getDouble(9);
				d6 = localResultSet.getDouble(10);
				j = localResultSet.getInt(11);
				str3 = localResultSet.getString(12);
				str4 = localResultSet.getString(13);
				str5 = localResultSet.getString(14);
				str6 = localResultSet.getString(15);
				l2 = localResultSet.getLong(16);
				d7 = localResultSet.getDouble(17);
				d8 = localResultSet.getDouble(18);
				str7 = localResultSet.getString(19);
				str8 = localResultSet.getString(20);
				str9 = localResultSet.getString(21);
				str10 = localResultSet.getString(22);
				str11 = localResultSet.getString(23);
				k = localResultSet.getInt(24);
				str12 = localResultSet.getString(25);
				d9 = localResultSet.getDouble(26);
				localCommodityVO2 = new CommodityVO();
				localCommodityVO2.amount = l1;
				localCommodityVO2.code = str1;
				localCommodityVO2.b_fee = d5;
				localCommodityVO2.s_fee = d6;
				if (l1 > 0L) {
					localCommodityVO2.lastPrice = d1;
				}
				localCommodityVO2.lastTime = localTimestamp;
				localCommodityVO2.lastUserID = str2;
				localCommodityVO2.b_security = d3;
				localCommodityVO2.s_security = d4;
				localCommodityVO2.tradeUnit = d2;
				localCommodityVO2.section = j;
				localCommodityVO2.str1 = str3;
				localCommodityVO2.str2 = str4;
				localCommodityVO2.str3 = str5;
				localCommodityVO2.str4 = str6;
				localCommodityVO2.str7 = str12;
				localCommodityVO2.totalAmount = l2;
				localCommodityVO2.beginPrice = d7;
				localCommodityVO2.stepPrice = d8;
				localCommodityVO2.str5 = str7;
				localCommodityVO2.str11 = str8;
				localCommodityVO2.str12 = str9;
				localCommodityVO2.str16 = str10;
				localCommodityVO2.str17 = str11;
				localCommodityVO2.bargainFlag = k;
				localCommodityVO2.alertPrice = d9;
				localHashtable.put(str1, localCommodityVO2);
			}
			TradeCommodityList1 localTradeCommodityList1 = new TradeCommodityList1();
			TradeCommodityList1.list.remove(this.PARTITIONID);
			TradeCommodityList1.list.put(this.PARTITIONID, localHashtable);
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement, localConnection);
		}
		CommodityVO[] arrayOfCommodityVO = this.TRADEDAO.getCurCommodityList(this.PARTITIONID);
		this.COMMODITYTABLE = new Hashtable();
		if (arrayOfCommodityVO != null) {
			for (int i = 0; i < arrayOfCommodityVO.length; i++) {
				CommodityVO localCommodityVO1 = arrayOfCommodityVO[i];
				if (localCommodityVO1.amount <= 0L) {
					localCommodityVO1.lastPrice = 0.0D;
				}
				this.COMMODITYTABLE.put(localCommodityVO1.code, localCommodityVO1);
			}
		}
	}

	public void reLoadCommodity(Connection paramConnection) throws SQLException {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		try {
			String str1 = "";
			double d1 = 0.0D;
			String str2 = "";
			long l1 = 0L;
			Timestamp localTimestamp = null;
			double d2 = 0.0D;
			double d3 = 0.0D;
			double d4 = 0.0D;
			double d5 = 0.0D;
			double d6 = 0.0D;
			int i = 0;
			String str3 = "";
			String str4 = "";
			String str5 = "";
			String str6 = "";
			String str7 = "";
			String str8 = "";
			String str9 = "";
			String str10 = "";
			String str11 = "";
			String str12 = "";
			long l2 = 0L;
			double d7 = 0.0D;
			double d8 = 0.0D;
			int j = 0;
			int k = -1;
			double d9 = 0.0D;
			long l3 = 0L;
			int m = 0;
			double d10 = 0.0D;
			long l4 = 0L;
			CommodityVO localCommodityVO = null;
			Hashtable localHashtable = new Hashtable();
			String str13 = "select c.code,t.price,t.userID,t.VAIDAMOUNT,t.lastTime,d.tradeUnit,d.b_security,d.s_security,d.b_fee,d.s_fee,c.section,e.str1,e.str2,e.str3,e.str4,d.amount,d.beginPrice,d.stepPrice,e.str5,e.str11,e.str12,e.str16,e.str17,c.bargainFlag,e.str7,d.category,d.alertPrice,d.minAmount,d.manner,d.financeScale,d.submitUnit from v_curCommodity c,v_tradeQuotation t,v_commodity d,v_commExt e where e.commID(+)=d.ID and d.id=c.commodityID and t.tradePartition(+)="
					+ this.PARTITIONID + " and c.tradePartition=" + this.PARTITIONID + " and c.code=t.code(+)";
			localPreparedStatement = paramConnection.prepareStatement(str13);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				str1 = localResultSet.getString(1);
				d1 = localResultSet.getDouble(2);
				str2 = localResultSet.getString(3);
				l1 = localResultSet.getLong(4);
				localTimestamp = localResultSet.getTimestamp(5);
				d2 = localResultSet.getDouble(6);
				d3 = localResultSet.getDouble(7);
				d4 = localResultSet.getDouble(8);
				d5 = localResultSet.getDouble(9);
				d6 = localResultSet.getDouble(10);
				i = localResultSet.getInt(11);
				str3 = localResultSet.getString(12);
				str4 = localResultSet.getString(13);
				str5 = localResultSet.getString(14);
				str6 = localResultSet.getString(15);
				l2 = localResultSet.getLong(16);
				d7 = localResultSet.getDouble(17);
				d8 = localResultSet.getDouble(18);
				str7 = localResultSet.getString(19);
				str8 = localResultSet.getString(20);
				str9 = localResultSet.getString(21);
				str10 = localResultSet.getString(22);
				str11 = localResultSet.getString(23);
				j = localResultSet.getInt(24);
				str12 = localResultSet.getString(25);
				k = localResultSet.getInt(26);
				d9 = localResultSet.getDouble(27);
				l3 = localResultSet.getLong(28);
				m = localResultSet.getInt(29);
				d10 = localResultSet.getDouble(30);
				l4 = localResultSet.getLong(31);
				localCommodityVO = new CommodityVO();
				localCommodityVO.amount = l1;
				localCommodityVO.code = str1;
				localCommodityVO.b_fee = d5;
				localCommodityVO.s_fee = d6;
				if (l1 > 0L) {
					localCommodityVO.lastPrice = d1;
				}
				localCommodityVO.lastTime = localTimestamp;
				localCommodityVO.lastUserID = str2;
				localCommodityVO.b_security = d3;
				localCommodityVO.s_security = d4;
				localCommodityVO.tradeUnit = d2;
				localCommodityVO.section = i;
				localCommodityVO.str1 = str3;
				localCommodityVO.str2 = str4;
				localCommodityVO.str3 = str5;
				localCommodityVO.str4 = str6;
				localCommodityVO.str7 = str12;
				localCommodityVO.totalAmount = l2;
				localCommodityVO.beginPrice = d7;
				localCommodityVO.stepPrice = d8;
				localCommodityVO.str5 = str7;
				localCommodityVO.str11 = str8;
				localCommodityVO.str12 = str9;
				localCommodityVO.str16 = str10;
				localCommodityVO.str17 = str11;
				localCommodityVO.bargainFlag = j;
				localCommodityVO.alertPrice = d9;
				localHashtable.put(str1, localCommodityVO);
			}
			TradeCommodityList1 localTradeCommodityList1 = new TradeCommodityList1();
			TradeCommodityList1.list.remove(this.PARTITIONID);
			TradeCommodityList1.list.put(this.PARTITIONID, localHashtable);
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement, paramConnection);
		}
	}

	public void reLoadFundInfo(Connection paramConnection) throws SQLException {
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		FundInfoValue localFundInfoValue = null;
		FundInfoList localFundInfoList = new FundInfoList();
		FundInfoList.list = new Hashtable();
		try {
			String str = "select t1.overdraft,t1.feecut,t1.balance,t1.frozenCapital,t1.totalSecurity,t2.name,t1.userCode from v_tradeUser t1,v_tradeUserExt t2 where t1.userCode=t2.userCode(+)";
			localPreparedStatement = paramConnection.prepareStatement(str);
			while (localResultSet.next()) {
				localFundInfoValue = new FundInfoValue();
				localFundInfoValue.balance = localResultSet.getDouble(3);
				localFundInfoValue.feecut = localResultSet.getDouble(2);
				localFundInfoValue.frozenCapital = localResultSet.getDouble(4);
				localFundInfoValue.overdraft = localResultSet.getDouble(1);
				localFundInfoValue.totalSecurity = localResultSet.getDouble(5);
				localFundInfoValue.userCode = localResultSet.getString(7);
				localFundInfoValue.userName = localResultSet.getString(6);
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement, null);
		}
	}

	public void reLoadFundInfo() throws SQLException {
		Connection localConnection = null;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		FundInfoValue localFundInfoValue = null;
		FundInfoList localFundInfoList = new FundInfoList();
		FundInfoList.list = new Hashtable();
		try {
			localConnection = this.TRADEDAO.getConnection();
			String str = "select t1.overdraft,t1.feecut,t1.balance,t1.frozenCapital,t1.totalSecurity,t2.name,t1.userCode from v_tradeUser t1,v_tradeUserExt t2 where t1.userCode=t2.userCode(+)";
			localPreparedStatement = localConnection.prepareStatement(str);
			localResultSet = localPreparedStatement.executeQuery();
			while (localResultSet.next()) {
				localFundInfoValue = new FundInfoValue();
				localFundInfoValue.balance = localResultSet.getDouble(3);
				localFundInfoValue.feecut = localResultSet.getDouble(2);
				localFundInfoValue.frozenCapital = localResultSet.getDouble(4);
				localFundInfoValue.overdraft = localResultSet.getDouble(1);
				localFundInfoValue.totalSecurity = localResultSet.getDouble(5);
				localFundInfoValue.userCode = localResultSet.getString(7);
				localFundInfoValue.userName = localResultSet.getString(6);
				FundInfoList.list.put(localResultSet.getString(7), localFundInfoValue);
			}
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			throw localSQLException;
		} finally {
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement, null);
		}
	}

	private void modSysStatusDB(Timestamp paramTimestamp1, int paramInt1, Timestamp paramTimestamp2, int paramInt2) throws SQLException {
		SysCurStatusVO localSysCurStatusVO = new SysCurStatusVO();
		if (paramTimestamp1 == null) {
			localSysCurStatusVO.endtime = this.TRADESTATUS.getPartEndTime();
		} else {
			localSysCurStatusVO.endtime = paramTimestamp1;
		}
		if (paramInt1 == -1) {
			localSysCurStatusVO.section = this.TRADESTATUS.getLastPartID();
		} else {
			localSysCurStatusVO.section = paramInt1;
		}
		if (paramTimestamp2 == null) {
			localSysCurStatusVO.starttime = this.TRADESTATUS.getPartStartTime();
		} else {
			localSysCurStatusVO.starttime = paramTimestamp2;
		}
		if (paramInt2 == -1) {
			localSysCurStatusVO.status = this.TRADESTATUS.getCurStatus();
		} else {
			localSysCurStatusVO.status = paramInt2;
		}
		localSysCurStatusVO.tradePartition = this.PARTITIONID;
		this.TRADEDAO.modifySysCurStatus(localSysCurStatusVO);
	}

	private void addDailyMoney(double paramDouble1, double paramDouble2, long paramLong, String paramString1, double paramDouble3, int paramInt,
			String paramString2, Connection paramConnection) throws SQLException {
		MoneyVO localMoneyVO = new MoneyVO();
		localMoneyVO.balance = Arith.format(paramDouble1, 2);
		localMoneyVO.contractNo = paramLong;
		localMoneyVO.firmID = paramString1;
		localMoneyVO.infoDate = new Timestamp(System.currentTimeMillis());
		localMoneyVO.money = Arith.format(paramDouble3, 2);
		localMoneyVO.operation = paramInt;
		localMoneyVO.paymentForGoods = Arith.format(paramDouble2, 2);
		localMoneyVO.note = paramString2;
		this.TRADEDAO.addDailymoney(localMoneyVO, paramConnection);
	}

	private void modSysStatusDB(Timestamp paramTimestamp1, int paramInt1, Timestamp paramTimestamp2, int paramInt2, Connection paramConnection)
			throws SQLException {
		SysCurStatusVO localSysCurStatusVO = new SysCurStatusVO();
		if (paramTimestamp1 == null) {
			localSysCurStatusVO.endtime = this.TRADESTATUS.getPartEndTime();
		} else {
			localSysCurStatusVO.endtime = paramTimestamp1;
		}
		if (paramInt1 == -1) {
			localSysCurStatusVO.section = this.TRADESTATUS.getLastPartID();
		} else {
			localSysCurStatusVO.section = paramInt1;
		}
		if (paramTimestamp2 == null) {
			localSysCurStatusVO.starttime = this.TRADESTATUS.getPartStartTime();
		} else {
			localSysCurStatusVO.starttime = paramTimestamp2;
		}
		if (paramInt2 == -1) {
			localSysCurStatusVO.status = this.TRADESTATUS.getCurStatus();
		} else {
			localSysCurStatusVO.status = paramInt2;
		}
		localSysCurStatusVO.tradePartition = this.PARTITIONID;
		this.TRADEDAO.modifySysCurStatus(localSysCurStatusVO, paramConnection);
	}

	private void modSysStatus(Timestamp paramTimestamp1, int paramInt1, Timestamp paramTimestamp2, int paramInt2, Timestamp paramTimestamp3) {
		if (paramTimestamp1 != null) {
			this.TRADESTATUS.setPartEndTime(paramTimestamp1);
		}
		if (paramInt1 >= 0) {
			this.TRADESTATUS.setLastPartID(paramInt1);
		}
		if (paramTimestamp2 != null) {
			this.TRADESTATUS.setPartStartTime(paramTimestamp2);
		}
		if (paramInt2 > 0) {
			this.TRADESTATUS.setCurStatus(paramInt2);
		}
		if (paramTimestamp3 != null) {
			this.TRADESTATUS.setPartLastEndTime(paramTimestamp3);
		}
	}

	private int getNextSection() {
		int i = -1;
		CommodityVO[] arrayOfCommodityVO = getCurCommodityList();
		for (int j = 0; j < arrayOfCommodityVO.length; j++) {
			CommodityVO localCommodityVO = arrayOfCommodityVO[j];
			if (localCommodityVO.section > this.TRADESTATUS.getLastPartID()) {
				i = localCommodityVO.section;
				break;
			}
		}
		return i;
	}

	private void addQuotation(String paramString1, int paramInt1, Timestamp paramTimestamp, double paramDouble, int paramInt2, long paramLong1,
			int paramInt3, String paramString2, long paramLong2) throws SQLException {
		TradeQuotationVO localTradeQuotationVO = new TradeQuotationVO();
		localTradeQuotationVO.code = paramString1;
		localTradeQuotationVO.countdownTime = paramInt1;
		localTradeQuotationVO.lastTime = paramTimestamp;
		localTradeQuotationVO.price = paramDouble;
		localTradeQuotationVO.section = paramInt2;
		localTradeQuotationVO.submitID = paramLong1;
		localTradeQuotationVO.tradePartition = paramInt3;
		localTradeQuotationVO.userID = paramString2;
		localTradeQuotationVO.vaidAmount = paramLong2;
		this.TRADEDAO.addTradeQuotation(localTradeQuotationVO);
	}

	private boolean checkCommodityFinished() {
		Enumeration localEnumeration = this.COMMODITYTABLE.elements();
		while (localEnumeration.hasMoreElements()) {
			CommodityVO localCommodityVO = (CommodityVO) localEnumeration.nextElement();
			synchronized (localCommodityVO) {
				if ((this.TRADESTATUS.getCurStatus() == 2) && (localCommodityVO.section == this.TRADESTATUS.getLastPartID())
						&& (getNewCountdown(localCommodityVO.code) != 0)) {
					return false;
				}
			}
		}
		return true;
	}

	private void setContractContent(BargainVO paramBargainVO, Connection paramConnection) {
		try {
			String str1 = this.TRADEDAO.getContractTemplet(paramBargainVO.tradePartition, paramConnection);
			if ((str1 == null) || (str1.equals(""))) {
				str1 = "<html><head><metacontent='text/html;charset=gb2312'http-equiv='Content-Type'/><style type='text/css'>.top_txt{\tfont-size:18px;\tfont-weight:bold;\tcolor:#FFFF00;}body{\tmargin-left:0px;\tmargin-top:0px;\tmargin-right:0px;\tmargin-bottom:0px;}.txt{\tline-height:18px;\tfont-size:12px;\tfont-weight:normal;\tcolor:#175768;\ttext-decoration:none;}.xbt{\tfont-size:12px;\tfont-weight:bold;\tcolor:#175768;\ttext-decoration:none;}.imp{\tfont-size:12px;\tfont-weight:normal;\tcolor:#FF3300;\ttext-decoration:none;}.td_nr{\tfont-size:12px;\tfont-weight:normal;\tcolor:#175768;\ttext-decoration:none;\ttext-align:left;}td{\tfont-size:12px;\tfont-weight:normal;\tcolor:#175768;}.k{\theight:16px;\tborder-top-width:1px;border-right-width:1px;\tborder-bottom-width:1px;border-left-width:1px;\tborder-top-style:dotted;border-right-style:dotted;\tborder-bottom-style:solid;border-left-style:dotted;\tborder-top-color:#FFFFFF;border-right-color:#FFFFFF;\tborder-bottom-color:#175768;border-left-color:#FFFFFF;\tfont-size:12px;\tfont-weight:normal;color:#175768;\ttext-decoration:none;}</style><metacontent='MSHTML6.00.2900.2802'name='GENERATOR'/><title>合同</title></head><body><table style='width:15.6cm'height='340'cellspacing='0'cellpadding='0'align='center'border='0'><tbody>\t<tr><td valign='top'><table height='35'cellspacing='0'cellpadding='0'width='100%'border='0'><tbody><tr><td bgcolor='#175768'><div class='top_txt'align='center'>竞价交易合同</div></td></tr></tbody></table><table height='270'cellspacing='0'cellpadding='0'width='100%'align='center'border='0'><tbody><tr><td valign='top'bgcolor='#ffffff'><table height='761'cellspacing='0'cellpadding='0'width='98%'align='center'border='0'><tbody><tr><td class='txt'valign='top'><br/><table cellspacing='0'cellpadding='0'width='100%'border='0'><tbody><tr><td width='10%'><div class='xbt'align='right'>卖方：</div></td><td width='55%'>buy_no</td><td width='15%'height='24'><div class='xbt'align='right'>合同编号：</div></td><td class='td_nr'>t_contractid号</td></tr><tr><td height='24'><div class='xbt'align='right'>买方：</div></td><td class='td_nr'>sell_no</td><td height='24'><divclass='xbt'align='right'>&nbsp;</div></td><td class='td_nr'>&nbsp;</td></tr></tbody></table>\t<p><strong>第一条</strong>&nbsp;&nbsp;中标标的号、品种、数量、单价、价款：</p><div align='center'><table style='border-collapse:collapse'bordercolor='#175768'cellspacing='0'cellpadding='0'bgcolor='#175768'border='1'><tbody><tr><td width='91'bgcolor='#ffffff'height='24'><p class='xbt'align='center'>标的号</p></td><td width='98'bgcolor='#ffffff'><p class='xbt'align='center'>品种</p></td><td width='112'bgcolor='#ffffff'><p class='xbt'align='center'>数量（units）</p></td><td width='140'bgcolor='#ffffff'><p class='xbt'align='center'>单价（元/units）</p></td><td width='147'bgcolor='#ffffff'><p class='xbt'align='center'>金额（元）</p></td></tr><tr><td width='91'bgcolor='#ffffff'height='24'><p align='center'>&nbsp;t_code</p></td><td width='98'bgcolor='#ffffff'><p align='center'>&nbsp;t_type</p></td><td width='112'bgcolor='#ffffff'><p align='center'>&nbsp;t_amount</p></td><td width='140'bgcolor='#ffffff'><p align='center'>&nbsp;t_price</p></td><td width='147'bgcolor='#ffffff'><p align='center'>&nbsp;t_money</p></td></tr><tr><td width='588'bgcolor='#ffffff'colspan='6'height='24'><p>&nbsp;&nbsp;<span class='xbt'>合计人民币（大写金额）：</span>&nbsp;t_bmoney</p></td></tr></tbody></table></div><span class='xbt'>第二条</span>&nbsp;&nbsp;质量标准、用途：中标标的号对应的质量等级。<br/><span class='xbt'>第三条</span>&nbsp;&nbsp;交（提）货期限：见《竞价销售交易细则》第二十七条之规定。<br/><span class='xbt'>第四条</span>&nbsp;&nbsp;交（提）货地点：中标标的号对应的存储地点。<br/><span class='xbt'>第五条</span>&nbsp;&nbsp;包装物标准及费用负担（含打包、上汽车费用）：见《竞价销售交易细则》第二十九条。<br/><span class='xbt'>第六条</span>&nbsp;&nbsp;交货方式：卖方承储库仓内交货。<br/><span class='xbt'>第七条</span>&nbsp;&nbsp;运输方式及到达站（港）和费用负担：买方自负。<br/><span class='xbt'>第八条</span>&nbsp;&nbsp;货物验收办法：买方提货时验收，数量以卖方交货仓库计量衡器为准。<br/>\t<span class='xbt'>第九条</span>&nbsp;&nbsp;结算方式：委托交易市场结算。<br/><span class='xbt'>第十条</span>&nbsp;&nbsp;违约责任：按照《竞价销售交易细则》第三十八、三十九、四十、四十一条执行。<br/><span class='xbt'>第十一条</span>&nbsp;&nbsp;合同争议的解决方式：本合同在履行过程中发生的争议由当事人协商解决，交易市场负责调解；当事人协商调解不成的，提交行业争议仲裁中心仲裁。<br/>&nbsp;&nbsp;&nbsp;附则：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（1）本合同签订双方承认并遵守《竞价销售交易细则》及《竞价销售清单》。<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（2）本合同签订双方需按成交金额的一定比例分别向交易市场交纳手续费。<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（3）卖方、买方签订合同前按一定比例交纳履约保证金交交易市场。<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（4）本合同经双方交易代表签字盖章后生效。<br/><span class='xbt'>第十二条</span>&nbsp;&nbsp;其他约定事项：<br/><span class='xbt'>第十三条</span>&nbsp;&nbsp;本合同一式三份，买卖双方各执一份，交易市场留存一份。<p>&nbsp;</p><table cellspacing='0'cellpadding='0'width='90%'align='center'border='0'><tbody><tr><td width='24%'height='29%'><div align='right'>卖方（代章）：</div></td><td width='27%'>&nbsp;</td><td width='24%'height='24%'><div align='right'>买方名称：</div></td><td width='21%'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>承储库点名称：</div></td><td>&nbsp;</td><td height='24%'><div align='right'>地&nbsp;&nbsp;址：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='29%'height='24%'><div align='right'>法定代表人（签字）：</div></td><td>&nbsp;</td><td height='29%'height='24%'><div align='right'>委托代理人（签字）：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>电&nbsp;&nbsp;话：</div></td><td><span class='td_nr'>&nbsp;</span></td><td height='24%'><div align='right'>电&nbsp;&nbsp;话：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>开户银行：</div></td><td><span class='td_nr'>&nbsp;</span></td><td height='24%'><div align='right'>开户银行：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>帐&nbsp;&nbsp;号：</div></td><td><span class='td_nr'>&nbsp;</span></td><td height='24%'><div align='right'>帐&nbsp;&nbsp;号：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>邮政编码：</div></td><td><span class='td_nr'>&nbsp;</span></td><td height='24%'><div align='right'>邮政编码：</div></td><td>&nbsp;</td></tr></tbody></table><table height='43'cellspacing='0'cellpadding='0'width='94%'align='center'border='0'><tbody><tr><td>&nbsp;</td><td width='25%'><span class='td_nr'>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class='xbt'>年</span><span class='td_nr'>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class='xbt'>月</span><span class='td_nr'>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class='xbt'>日</span></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></body></html>";
			}
			CommodityVO localCommodityVO = getCurCommodity(paramBargainVO.code);
			TradeUserVO localTradeUserVO1 = null;
			TradeUserVO localTradeUserVO2 = null;
			localTradeUserVO1 = this.TRADEDAO.getTradeUser(localCommodityVO.userID);
			localTradeUserVO2 = this.TRADEDAO.getTradeUser(paramBargainVO.userID);
			if (localCommodityVO.tradeMode == 2) {
				str1 = str1.replaceAll("t_buyername", localCommodityVO.userID);
				str1 = str1.replaceAll("t_sellername", paramBargainVO.userID);
			}
			String str2 = Tool.delNull(localCommodityVO.str3);
			String[] arrayOfString = str2.split(",");
			for (int i = 0; i < arrayOfString.length - 1; i++) {
				str1 = str1.replaceAll("str3_" + i, Tool.delNull(arrayOfString[i]));
			}
			str1 = str1.replaceAll("t_contractid", Tool.delNull(paramBargainVO.contractID + ""));
			str1 = str1.replaceAll("t_code", Tool.delNull(localCommodityVO.code));
			str1 = str1.replaceAll("t_type", Tool.delNull(localCommodityVO.str4));
			str1 = str1.replaceAll("units", Tool.delNull(localCommodityVO.str6));
			str1 = str1.replaceAll("str1k", Tool.delNull(localCommodityVO.str1));
			str1 = str1.replaceAll("str12", Tool.delNull(localCommodityVO.str12));
			str1 = str1.replaceAll("str13", Tool.delNull(localCommodityVO.str13));
			str1 = str1.replaceAll("str14", Tool.delNull(localCommodityVO.str14));
			str1 = str1.replaceAll("str15", Tool.delNull(localCommodityVO.str15));
			str1 = str1.replaceAll("str16", Tool.delNull(localCommodityVO.str16));
			str1 = str1.replaceAll("b_fee", Tool.fmtDouble2(localCommodityVO.b_fee));
			str1 = str1.replaceAll("s_fee", Tool.fmtDouble2(localCommodityVO.s_fee));
			str1 = str1.replaceAll("b_security", Tool.fmtDouble2(localCommodityVO.b_security));
			str1 = str1.replaceAll("s_security", Tool.fmtDouble2(localCommodityVO.s_security));
			str1 = str1.replaceAll("buy_no", Tool.delNull(localTradeUserVO1.userCode));
			str1 = str1.replaceAll("sell_no", Tool.delNull(localTradeUserVO2.userCode));
			str1 = str1.replaceAll("buy_name", Tool.delNull(localTradeUserVO1.name));
			str1 = str1.replaceAll("sell_name", Tool.delNull(localTradeUserVO2.name));
			str1 = str1.replaceAll("buy_address", Tool.delNull(localTradeUserVO1.address));
			str1 = str1.replaceAll("sell_address", Tool.delNull(localTradeUserVO2.address));
			str1 = str1.replaceAll("buy_phone", Tool.delNull(localTradeUserVO1.tdtele));
			str1 = str1.replaceAll("sell_phone", Tool.delNull(localTradeUserVO2.tdtele));
			str1 = str1.replaceAll("buy_bank", Tool.delNull(localTradeUserVO1.bank));
			str1 = str1.replaceAll("sell_bank", Tool.delNull(localTradeUserVO2.bank));
			str1 = str1.replaceAll("buy_account", Tool.delNull(localTradeUserVO1.account));
			str1 = str1.replaceAll("sell_account", Tool.delNull(localTradeUserVO2.account));
			str1 = str1.replaceAll("buy_postid", Tool.delNull(localTradeUserVO1.postid));
			str1 = str1.replaceAll("sell_postid", Tool.delNull(localTradeUserVO2.postid));
			str1 = str1.replaceAll("buy_tdfax", Tool.delNull(localTradeUserVO1.tdfax));
			str1 = str1.replaceAll("sell_tdfax", Tool.delNull(localTradeUserVO2.tdfax));
			str1 = str1.replaceAll("buy_tdmobile", Tool.delNull(localTradeUserVO1.tdmobile));
			str1 = str1.replaceAll("sell_tdmobile", Tool.delNull(localTradeUserVO2.tdmobile));
			str1 = str1.replaceAll("buy_traderemail", Tool.delNull(localTradeUserVO1.traderemail));
			str1 = str1.replaceAll("sell_traderemail", Tool.delNull(localTradeUserVO2.traderemail));
			str1 = str1.replaceAll("buy_tradername", Tool.delNull(localTradeUserVO1.tradername));
			str1 = str1.replaceAll("sell_tradername", Tool.delNull(localTradeUserVO2.tradername));
			if (this.TRADEDAO.getPD().equals("true")) {
				str1 = str1.replaceAll("数量（吨）", "数量(批)");
				str1 = str1.replaceAll("t_amount", paramBargainVO.amount + "");
			} else {
				str1 = str1.replaceAll("t_amount", paramBargainVO.amount * localCommodityVO.tradeUnit + "");
			}
			str1 = str1.replaceAll("t_price", Tool.fmtDouble2(paramBargainVO.price) + "");
			str1 = str1.replaceAll("t_money",
					Tool.fmtDouble2(Arith.mul(paramBargainVO.price, localCommodityVO.tradeUnit) * paramBargainVO.amount) + "");
			str1 = str1.replaceAll("t_bmoney",
					Tool.fmtCapitalization(Arith.mul(paramBargainVO.price, localCommodityVO.tradeUnit) * paramBargainVO.amount));
			paramBargainVO.contractContent = str1;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	private void addQuotation(String paramString1, int paramInt1, Timestamp paramTimestamp, double paramDouble, int paramInt2, long paramLong1,
			int paramInt3, String paramString2, long paramLong2, Connection paramConnection) throws SQLException {
		TradeQuotationVO localTradeQuotationVO = new TradeQuotationVO();
		localTradeQuotationVO.code = paramString1;
		localTradeQuotationVO.countdownTime = paramInt1;
		localTradeQuotationVO.lastTime = paramTimestamp;
		localTradeQuotationVO.price = paramDouble;
		localTradeQuotationVO.section = paramInt2;
		localTradeQuotationVO.submitID = paramLong1;
		localTradeQuotationVO.tradePartition = paramInt3;
		localTradeQuotationVO.userID = paramString2;
		localTradeQuotationVO.vaidAmount = paramLong2;
		this.TRADEDAO.addTradeQuotation(localTradeQuotationVO, paramConnection);
	}

	public void removeCommodityFromMem(String paramString) throws Exception {
		TradeCommodityList1 localTradeCommodityList1 = new TradeCommodityList1();
		if (TradeCommodityList1.list.get(this.PARTITIONID + "") != null) {
			Hashtable localHashtable = (Hashtable) TradeCommodityList1.list.get(this.PARTITIONID + "");
			localHashtable.remove(paramString);
		}
	}

	public void endTrade() throws SQLException {
		TradeStatusValue localTradeStatusValue = getTradeStatus();
		if (localTradeStatusValue.getCurStatus() == 2) {
			Connection localConnection = null;
			PreparedStatement localPreparedStatement = null;
			try {
				localConnection = this.TRADEDAO.getConnection();
				String str = "update v_sysCurStatus set status=4 where tradePartition=" + this.PARTITIONID;
				localPreparedStatement = localConnection.prepareStatement(str);
				localPreparedStatement.executeUpdate();
				localTradeStatusValue.setCurStatus(4);
			} catch (SQLException localSQLException) {
				localSQLException.printStackTrace();
				throw localSQLException;
			} finally {
				this.TRADEDAO.closeStatement(null, localPreparedStatement, localConnection);
			}
		}
	}

	public int withdrawContract(long paramLong) throws SQLException, Exception {
		Connection localConnection = null;
		PreparedStatement localPreparedStatement1 = null;
		ResultSet localResultSet = null;
		int i = 0;
		try {
			localConnection = this.TRADEDAO.getConnection();
			localConnection.setAutoCommit(false);
			long l1 = -1L;
			long l2 = -1L;
			String str1 = null;
			String str2 = null;
			double d = 0.0D;
			String str3 = "select submitID,commodityID,userID,s_bail+s_poundage,code from v_bargain t1,v_commext t2 where t1.commodityid=t2.commid and t1.contractID="
					+ paramLong;
			localPreparedStatement1 = localConnection.prepareStatement(str3);
			localResultSet = localPreparedStatement1.executeQuery();
			while (localResultSet.next()) {
				l1 = localResultSet.getLong(1);
				l2 = localResultSet.getLong(2);
				str1 = localResultSet.getString(3);
				d = localResultSet.getDouble(4);
				str2 = localResultSet.getString(5);
			}
			localResultSet.close();
			localResultSet = null;
			localPreparedStatement1.close();
			localPreparedStatement1 = null;
			str3 = "delete from v_curSubmit where code='" + str2 + "'";
			localPreparedStatement1 = localConnection.prepareStatement(str3);
			int j = localPreparedStatement1.executeUpdate();
			localPreparedStatement1.close();
			localPreparedStatement1 = null;
			if (j <= 0) {
				i = 1;
				throw new Exception("删除委托记录失败");
			}
			str3 = "delete from v_bargain where Contractid=" + paramLong;
			localPreparedStatement1 = localConnection.prepareStatement(str3);
			j = localPreparedStatement1.executeUpdate();
			localPreparedStatement1.close();
			localPreparedStatement1 = null;
			if (j <= 0) {
				i = 6;
				throw new Exception("删除合约失败");
			}
			str3 = "update v_curCommodity set bargainFlag=0 where commodityID=" + l2;
			localPreparedStatement1 = localConnection.prepareStatement(str3);
			j = localPreparedStatement1.executeUpdate();
			localPreparedStatement1.close();
			localPreparedStatement1 = null;
			if (j <= 0) {
				i = 2;
				throw new Exception("设置当前交易商品状态失败");
			}
			TradeCommodityM1 localTradeCommodityM1 = getCommodity(str2);
			if (localTradeCommodityM1 != null) {
				localTradeCommodityM1.bargainFlag = 0;
			} else {
				i = 2;
				throw new Exception("设置当前交易商品状态失败");
			}
			str3 = "update v_commodity set status=2 where ID=" + l2;
			localPreparedStatement1 = localConnection.prepareStatement(str3);
			j = localPreparedStatement1.executeUpdate();
			localPreparedStatement1.close();
			localPreparedStatement1 = null;
			if (j <= 0) {
				i = 3;
				throw new Exception("设置商品状态失败");
			}
			str3 = "update v_tradeUser set balance=balance+" + d + ",tradeCount=tradeCount-1,totalSecurity=totalSecurity-" + d + " where userCode='"
					+ str1 + "'";
			localPreparedStatement1 = localConnection.prepareStatement(str3);
			j = localPreparedStatement1.executeUpdate();
			localPreparedStatement1.close();
			localPreparedStatement1 = null;
			if (j <= 0) {
				i = 4;
				throw new Exception("设置交易商资金失败");
			}
			str3 = "select * from v_dailymoney where contractno=" + paramLong;
			localPreparedStatement1 = localConnection.prepareStatement(str3);
			localResultSet = localPreparedStatement1.executeQuery();
			while (localResultSet.next()) {
				if ((localResultSet.getInt("operation") == 403) || (localResultSet.getInt("operation") == 404)) {
					str3 = "update v_tradeUsermarket set balance=balance+" + localResultSet.getDouble("money") + " where marketid="
							+ localResultSet.getInt("market2") + " and userCode='" + str1 + "'";
					PreparedStatement localPreparedStatement2 = localConnection.prepareStatement(str3);
					localPreparedStatement2.executeUpdate();
					localPreparedStatement2.close();
					localPreparedStatement2 = null;
				}
			}
			str3 = "delete from v_DailyMoney where ContractNo=" + paramLong;
			localPreparedStatement1 = localConnection.prepareStatement(str3);
			j = localPreparedStatement1.executeUpdate();
			localPreparedStatement1.close();
			localPreparedStatement1 = null;
			if (j <= 0) {
				i = 5;
				throw new Exception("删除当日资金流水失败");
			}
			localConnection.commit();
		} catch (SQLException localSQLException) {
			localSQLException.printStackTrace();
			localConnection.rollback();
			throw localSQLException;
		} catch (Exception localException) {
			localException.printStackTrace();
			localConnection.rollback();
		} finally {
			localConnection.setAutoCommit(true);
			this.TRADEDAO.closeStatement(localResultSet, localPreparedStatement1, localConnection);
		}
		return i;
	}

	public int getNewCountdown(String paramString) {
		int i = -2;
		long l1 = 0L;
		long l2 = 0L;
		int j = 0;
		int k = 0;
		try {
			l2 = getCurCommodity(paramString).lastTime.getTime();
		} catch (Exception localException) {
		}
		j = this.TRADESTATUS.getCountdownStart();
		k = this.TRADESTATUS.getCountdownTime();
		long l3 = System.currentTimeMillis();
		l1 = this.TRADESTATUS.getPartEndTime().getTime();
		int m = this.TRADESTATUS.getCurStatus();
		if (m == 2) {
			if (l3 < l1 - j * 1000) {
				i = -1;
			} else if ((l3 > l1 - j * 1000 + k * 1000) && (l3 > l2 + k * 1000)) {
				i = 0;
			} else {
				i = k;
			}
		} else {
			i = 0;
		}
		return i;
	}
}
