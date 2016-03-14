package gnnt.MEBS.vendue.server;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.NamingException;

import gnnt.MEBS.vendue.server.dao.TradeDAO;
import gnnt.MEBS.vendue.server.vo.BargainVO;
import gnnt.MEBS.vendue.server.vo.CommodityVO;
import gnnt.MEBS.vendue.server.vo.CurSubmitVO;
import gnnt.MEBS.vendue.server.vo.FlowControlVO;
import gnnt.MEBS.vendue.server.vo.MoneyVO;
import gnnt.MEBS.vendue.server.vo.SysCurStatusVO;
import gnnt.MEBS.vendue.server.vo.SysPartitionVO;
import gnnt.MEBS.vendue.server.vo.SysPropertyVO;
import gnnt.MEBS.vendue.server.vo.TradeQuotationVO;
import gnnt.MEBS.vendue.server.vo.TradeStatusValue;
import gnnt.MEBS.vendue.server.vo.TradeStatusValueImpl;
import gnnt.MEBS.vendue.server.vo.TradeUserVO;
import gnnt.MEBS.vendue.util.Arith;
import gnnt.MEBS.vendue.util.Configuration;
import gnnt.MEBS.vendue.util.Tool;

public class KernelEngineImpl extends KernelEngine {
	public KernelEngineImpl() throws NamingException {
	}

	public int closeTrade() {
		int i = 2;
		int j = this.TRADESTATUS.getCurStatus();
		if ((j == 4) || (j == 3) || (j == 1)) {
			try {
				modSysStatusDB(null, 0, null, 5);
				modSysStatus(null, 0, null, 5, null);
				this.COMMODITYTABLE = new Hashtable();
			} catch (SQLException localSQLException) {
				localSQLException.printStackTrace();
				i = 1;
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

	protected TradeDAO getTradeDAO() {
		return null;
	}

	public TradeStatusValue getTradeStatus() throws SQLException {
		return this.TRADESTATUS;
	}

	public void loadCommodity() throws SQLException {
		CommodityVO[] arrayOfCommodityVO = this.TRADEDAO.getCurCommodityList(this.PARTITIONID);
		if (this.COMMODITYTABLE == null) {
			this.COMMODITYTABLE = new Hashtable();
		}
		if (arrayOfCommodityVO != null) {
			for (int i = 0; i < arrayOfCommodityVO.length; i++) {
				CommodityVO localCommodityVO = arrayOfCommodityVO[i];
				if ((localCommodityVO == null)
						|| ((localCommodityVO.bargainFlag == 0) && (((this.TRADESTATUS.getCurStatus() == 2) && (localCommodityVO.lastPrice <= 0.0D))
								|| (this.TRADESTATUS.getCurStatus() != 2)))) {
					if (localCommodityVO.amount <= 0L) {
						localCommodityVO.lastPrice = 0.0D;
					}
					this.COMMODITYTABLE.put(localCommodityVO.code, localCommodityVO);
					if ((this.TRADESTATUS.getCurStatus() == 2) && (localCommodityVO.section == this.TRADESTATUS.getLastPartID())) {
						addQuotation(localCommodityVO.code, -1, new Timestamp(System.currentTimeMillis()), 0.0D, localCommodityVO.section, -1L,
								this.PARTITIONID, " ", 0L);
					}
				}
			}
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

	public int addCommodityCharge(String paramString, long paramLong, Connection paramConnection) throws SQLException {
		TradeUserVO localTradeUserVO = this.TRADEDAO.getTradeUser(paramString, paramConnection);
		CommodityVO localCommodityVO = this.TRADEDAO.getCommodity(paramLong, paramConnection);
		int i = 0;
		double d1 = 0.0D;
		double d2 = 0.0D;
		double d3 = 0.0D;
		double d4 = 0.0D;
		if (localCommodityVO.tradeMode == 0) {
			d4 = localCommodityVO.alertPrice;
		} else if (localCommodityVO.tradeMode == 1) {
			d4 = localCommodityVO.beginPrice;
		}
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
		if (localCommodityVO.tradeMode == 0) {
			System.out.println("======保证金系数是 " + localCommodityVO.s_security + "  交易数量是 " + localCommodityVO.totalAmount + "  费用折扣是 "
					+ localTradeUserVO.feecut + "  手续费系数是 " + localCommodityVO.s_fee);
		} else {
			System.out.println("======保证金系数是 " + localCommodityVO.b_security + "  交易数量是 " + localCommodityVO.totalAmount + "  费用折扣是 "
					+ localTradeUserVO.feecut + "  手续费系数是 " + localCommodityVO.b_fee);
		}
		if (i1 == 0) {
			System.out.println("保证金====  绝对值");
			if (localCommodityVO.tradeMode == 0) {
				d1 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.s_security, d6), localCommodityVO.tradeUnit) * localCommodityVO.totalAmount,
						2);
			} else {
				d1 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.b_security, d6), localCommodityVO.tradeUnit) * localCommodityVO.totalAmount,
						2);
			}
		} else if (i1 == 1) {
			System.out.println("保证金====  百分比");
			if (localCommodityVO.tradeMode == 0) {
				d1 = Arith.format(Arith.mul(Arith.mul(Arith.mul(d4, localCommodityVO.s_security), d6), localCommodityVO.tradeUnit)
						* localCommodityVO.totalAmount, 2);
			} else {
				d1 = Arith.format(Arith.mul(Arith.mul(Arith.mul(d4, localCommodityVO.b_security), d6), localCommodityVO.tradeUnit)
						* localCommodityVO.totalAmount, 2);
			}
		}
		if (j == 1) {
			if (i3 == 0) {
				System.out.println("手续费====  绝对值");
				if (localCommodityVO.tradeMode == 0) {
					d2 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.s_fee, d5), localCommodityVO.tradeUnit) * localCommodityVO.totalAmount, 2);
				} else {
					d2 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.b_fee, d5), localCommodityVO.tradeUnit) * localCommodityVO.totalAmount, 2);
				}
			} else if (i3 == 1) {
				System.out.println("手续费====  百分比");
				if (localCommodityVO.tradeMode == 0) {
					d2 = Arith.format(Arith.mul(Arith.mul(Arith.mul(d4, localCommodityVO.s_fee), d5), localCommodityVO.tradeUnit)
							* localCommodityVO.totalAmount, 2);
				} else {
					d2 = Arith.format(Arith.mul(Arith.mul(Arith.mul(d4, localCommodityVO.b_fee), d5), localCommodityVO.tradeUnit)
							* localCommodityVO.totalAmount, 2);
				}
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
			d3 = localCallableStatement.getDouble(1);
			addDailyMoney(d3, localTradeUserVO.paymentForGoods, -1L, localTradeUserVO.userCode, d1, 404, paramLong + "", paramConnection);
			localCallableStatement = paramConnection.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
			localCallableStatement.setString(2, localTradeUserVO.userCode);
			localCallableStatement.setDouble(3, d2);
			localCallableStatement.setString(4, "4");
			localCallableStatement.registerOutParameter(1, 8);
			localCallableStatement.executeUpdate();
			addDailyMoney(d3, localTradeUserVO.paymentForGoods, -1L, localTradeUserVO.userCode, d2, 403, paramLong + "", paramConnection);
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

	public void reLoadCommodity() throws SQLException {
		CommodityVO[] arrayOfCommodityVO = this.TRADEDAO.getCurCommodityList(this.PARTITIONID);
		this.COMMODITYTABLE = new Hashtable();
		if (arrayOfCommodityVO != null) {
			for (int i = 0; i < arrayOfCommodityVO.length; i++) {
				CommodityVO localCommodityVO = arrayOfCommodityVO[i];
				if (localCommodityVO.amount <= 0L) {
					localCommodityVO.lastPrice = 0.0D;
				}
				this.COMMODITYTABLE.put(localCommodityVO.code, localCommodityVO);
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

	public int startTrade() {
		int i = 2;
		int j = this.TRADESTATUS.getCurStatus();
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

	public String getPartitionName() throws SQLException {
		String str = "";
		SysPartitionVO[] arrayOfSysPartitionVO = this.TRADEDAO.getSysPartitionList("where partitionID=" + this.PARTITIONID);
		if (arrayOfSysPartitionVO != null) {
			str = arrayOfSysPartitionVO[0].description;
		}
		return str;
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

	public void run() {
		long l1 = 0L;
		long l2 = 0L;
		long l4 = 0L;
		long l5 = 0L;
		int m = 0;
		int n = -1;
		int i1 = -1;
		int i2 = -1;
		Connection localConnection = null;
		CallableStatement localCallableStatement = null;
		Properties localProperties = new Configuration().getSection("MEBS.TradeParams");
		int i4 = Integer.parseInt(localProperties.getProperty("Interval"));
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
					n = this.TRADESTATUS.getCurStatus();
					i1 = this.TRADESTATUS.getLastPartID();
					i2 = getNextSection();
					long l6 = 0L;
					int i = 0;
					long l3 = System.currentTimeMillis();
					long l7 = this.TRADESTATUS.getPartLastEndTime().getTime();
					long l8 = this.TRADESTATUS.getPartEndTime().getTime();
					l5 = this.TRADESTATUS.getDurativeTime();
					l4 = this.TRADESTATUS.getSpaceTime();
					int j = this.TRADESTATUS.getCountdownStart();
					int k = this.TRADESTATUS.getCountdownTime();
					if (((n != 4) && (n != 5) && (n != 6) && (this.COMMODITYTABLE != null) && (this.COMMODITYTABLE.size() > 0) && (n != 1))
							|| ((n == 1) && (i2 > 0))) {
						if ((n != 8) && (n != 2)) {
							FlowControlVO[] localObject1 = this.TRADEDAO.getFlowControlList(
									"where unitType=2 and tradePartition=" + this.PARTITIONID + " and unitID=" + (i2 - 1), localConnection);
							int i5;
							FlowControlVO localObject2;
							if (localObject1 != null) {
								i5 = 0;
								if (i5 < localObject1.length) {
									localObject2 = localObject1[i5];
									l6 = localObject2.durativeTime;
									i5++;
									continue;
								}
							}
							localObject1 = this.TRADEDAO.getFlowControlList(
									"where unitType=1 and tradePartition=" + this.PARTITIONID + " and unitID=" + i2, localConnection);
							if (localObject1 != null) {
								i5 = 0;
								if (i5 < localObject1.length) {
									localObject2 = localObject1[i5];
									i = 1;
									int i3 = localObject2.startMode;
									l5 = localObject2.durativeTime;
									if (i3 == 1) {
										l1 = Tool.getTime(localObject2.startTime);
										if ((n == 3) || (n == 1)) {
											l2 = l1 + l5 * 1000L;
										}
									} else if (i3 == 2) {
										if ((n != 7) && (n != 2)) {
											modSysStatus(null, -1, null, 9, null);
											Thread.sleep(i4);
											n = this.TRADESTATUS.getCurStatus();
											l3 = System.currentTimeMillis();
											continue;
										}
										if (n == 7) {
											l1 = l3;
											l2 = l1 + l5 * 1000L;
										}
									} else if (i3 == 3) {
										if ((n == 1) || (n == 10)) {
											l1 = l3;
											l2 = l1 + l5 * 1000L;
										} else if (n == 3) {
											l1 = l7 + l4 * 1000L + l6 * 1000L;
											l2 = l1 + l5 * 1000L;
										}
									}
									i5++;
									continue;
								}
							}
							if (i == 0) {
								if ((n == 1) || (n == 10)) {
									l1 = l3;
									l2 = l1 + l5 * 1000L;
								} else if (n == 3) {
									l1 = l7 + l4 * 1000L + l6 * 1000L;
									l2 = l1 + l5 * 1000L;
								}
							}
						}
						if (l3 < l1) {
							if (n != 3) {
								modSysStatusDB(null, -1, null, 3, localConnection);
								modSysStatus(null, -1, null, 3, null);
							}
						} else {
							Object localObject4;
							if ((l3 >= l1) && (((n == 2) && (l3 <= l7)) || (l3 <= l2))) {
								CommodityVO localCommodityVO1;
								if (n != 2) {
									modSysStatusDB(new Timestamp(l2), i2, new Timestamp(l1), 2, localConnection);
									Enumeration localObject1 = this.COMMODITYTABLE.elements();
									if (((Enumeration) localObject1).hasMoreElements()) {
										localCommodityVO1 = (CommodityVO) ((Enumeration) localObject1).nextElement();
										synchronized (localCommodityVO1) {
											if ((localCommodityVO1.section == i2) && (localCommodityVO1.bargainFlag == 0)) {
												addQuotation(localCommodityVO1.code, -1, new Timestamp(l3), 0.0D, i2, -1L, this.PARTITIONID, " ", 0L,
														localConnection);
											}
										}
										continue;
									}
									modSysStatus(new Timestamp(l2), i2, new Timestamp(l1), 2, new Timestamp(l2));
								} else {
									l2 = l7;
									if (m == 0) {
										TradeQuotationVO[] localObject1 = this.TRADEDAO.getTradeQuotationList("where countdownTime>0",
												localConnection);
										if ((localObject1 != null) && (localObject1.length > 0)) {
											m = 1;
										}
									}
									if ((l3 >= l8 - j * 1000) && (m == 0)) {
										Enumeration localObject1 = this.COMMODITYTABLE.elements();
										if (((Enumeration) localObject1).hasMoreElements()) {
											localCommodityVO1 = (CommodityVO) ((Enumeration) localObject1).nextElement();
											synchronized (localCommodityVO1) {
												if ((localCommodityVO1.section == i1) && (localCommodityVO1.bargainFlag == 0)) {
													this.TRADEDAO.delTradeQuotation(
															"where tradePartition=" + this.PARTITIONID + " and code='" + localCommodityVO1.code + "'",
															localConnection);
													localObject4 = " ";
													if ((localCommodityVO1.lastUserID != null) && (!localCommodityVO1.lastUserID.equals(""))) {
														localObject4 = localCommodityVO1.lastUserID;
													}
													Timestamp localTimestamp = new Timestamp(l3);
													if (localCommodityVO1.lastTime != null) {
														localTimestamp = localCommodityVO1.lastTime;
													}
													addQuotation(localCommodityVO1.code, k, localTimestamp, localCommodityVO1.lastPrice, i1,
															localCommodityVO1.lastSubmitID, this.PARTITIONID, (String) localObject4,
															localCommodityVO1.amount, localConnection);
												}
											}
											continue;
										}
										m = 1;
									}
								}
							} else if (checkCommodityFinished()) {
								m = 0;
								if (k != 3) {
									BargainVO abargainvo[] = TRADEDAO.getCurBargainList(PARTITIONID, i1, localConnection);
									if (abargainvo != null) {
										for (int i3 = 0; i3 < abargainvo.length; i3++) {
											System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
											System.out.println("-------------------------生成成交纪录-----------------------------------");
											System.out.println();
											BargainVO bargainvo = abargainvo[i3];
											CommodityVO commodityvo5 = (CommodityVO) COMMODITYTABLE.get(bargainvo.code);
											if (commodityvo5.tradeMode == 0) {
												System.out.println();
												System.out.println("==========竞买");
												System.out.println();
												bargainvo.b_bail = getBail(bargainvo.code, bargainvo.price, bargainvo.amount, bargainvo.userID,
														localConnection);
												bargainvo.s_bail = getBail(bargainvo.code, bargainvo.price, bargainvo.amount, commodityvo5.userID,
														localConnection);
											} else {
												System.out.println();
												System.out.println("==========竞卖");
												System.out.println();
												bargainvo.s_bail = getBail(bargainvo.code, bargainvo.price, bargainvo.amount, bargainvo.userID,
														localConnection);
												bargainvo.b_bail = getBail(bargainvo.code, bargainvo.price, bargainvo.amount, commodityvo5.userID,
														localConnection);
											}
											if (commodityvo5.tradeMode == 0) {
												bargainvo.b_poundage = getFee(bargainvo.code, bargainvo.price, bargainvo.amount, bargainvo.userID,
														localConnection);
												bargainvo.s_poundage = getFee(bargainvo.code, bargainvo.price, bargainvo.amount, commodityvo5.userID,
														localConnection);
											} else {
												bargainvo.s_poundage = getFee(bargainvo.code, bargainvo.price, bargainvo.amount, bargainvo.userID,
														localConnection);
												bargainvo.b_poundage = getFee(bargainvo.code, bargainvo.price, bargainvo.amount, commodityvo5.userID,
														localConnection);
											}
											long l10 = TRADEDAO.addBargain(bargainvo, localConnection);
											bargainvo.contractID = l10;
											setContractContent(bargainvo, localConnection);
											TRADEDAO.modBargainContent(bargainvo, localConnection);
											TradeUserVO tradeuservo = TRADEDAO.getTradeUserForUpdate(bargainvo.userID, localConnection);
											double d = tradeuservo.balance;
											if (commodityvo5.tradeMode == 0) {
												if (bargainvo.userID.equals(commodityvo5.userID)) {
													tradeuservo.totalSecurity += bargainvo.s_bail + bargainvo.s_poundage;
													tradeuservo.tradeCount++;
													TRADEDAO.modifyTradeUser(tradeuservo, localConnection);
													CallableStatement callablestatement = localConnection
															.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
													callablestatement.setString(2, tradeuservo.userCode);
													callablestatement.setDouble(3, -1D * (bargainvo.s_bail + bargainvo.s_poundage));
													callablestatement.setString(4, "4");
													callablestatement.registerOutParameter(1, 8);
													callablestatement.executeUpdate();
													callablestatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													callablestatement.setString(2, tradeuservo.userCode);
													callablestatement.setInt(3, 404);
													callablestatement.setDouble(4, bargainvo.s_bail);
													callablestatement.setLong(5, bargainvo.contractID);
													callablestatement.registerOutParameter(1, 8);
													callablestatement.executeUpdate();
													double d1 = callablestatement.getDouble(1);
													addDailyMoney(d1, tradeuservo.paymentForGoods, l10, bargainvo.userID, bargainvo.s_bail, 404, "",
															localConnection);
													callablestatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													callablestatement.setString(2, tradeuservo.userCode);
													callablestatement.setInt(3, 403);
													callablestatement.setDouble(4, bargainvo.s_poundage);
													callablestatement.setLong(5, bargainvo.contractID);
													callablestatement.registerOutParameter(1, 8);
													callablestatement.executeUpdate();
													d1 = callablestatement.getDouble(1);
													addDailyMoney(d1, tradeuservo.paymentForGoods, l10, bargainvo.userID, bargainvo.s_poundage, 403,
															"", localConnection);
													System.out.println();
													System.out.println((new StringBuilder()).append("<<<<<<<<<<<<<<<<<<<  生成卖方流水 ")
															.append(bargainvo.userID).append(" 保证金为 ").append(bargainvo.s_bail).append("  手续费为 ")
															.append(bargainvo.s_poundage).append(">>>>>>>>>>").toString());
													System.out.println();
													CommodityVO commodityvo6 = TRADEDAO.getCommodity(bargainvo.commodityID, localConnection);
													delCommodityCharge(bargainvo.commodityID, localConnection);
													tradeuservo = TRADEDAO.getTradeUserForUpdate(commodityvo6.userID, localConnection);
													tradeuservo.tradeCount++;
													TRADEDAO.modifyTradeUser(tradeuservo, localConnection);
													callablestatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													callablestatement.setString(2, tradeuservo.userCode);
													callablestatement.setInt(3, 404);
													callablestatement.setDouble(4, bargainvo.b_bail);
													callablestatement.setLong(5, bargainvo.contractID);
													callablestatement.registerOutParameter(1, 8);
													callablestatement.executeUpdate();
													d1 = callablestatement.getDouble(1);
													addDailyMoney(d1, tradeuservo.paymentForGoods, l10, commodityvo6.userID, bargainvo.b_bail, 404,
															"", localConnection);
													callablestatement = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													callablestatement.setString(2, tradeuservo.userCode);
													callablestatement.setInt(3, 403);
													callablestatement.setDouble(4, bargainvo.b_poundage);
													callablestatement.setLong(5, bargainvo.contractID);
													callablestatement.registerOutParameter(1, 8);
													callablestatement.executeUpdate();
													d1 = callablestatement.getDouble(1);
													addDailyMoney(d1, tradeuservo.paymentForGoods, l10, commodityvo6.userID, bargainvo.b_poundage,
															403, "", localConnection);
													System.out.println();
													System.out.println((new StringBuilder()).append("<<<<<<<<<<<<<<<<<<<  生成买方方流水")
															.append(commodityvo6.userID).append(" 保证金为 ").append(bargainvo.b_bail).append("  手续费为 ")
															.append(bargainvo.b_poundage).append(">>>>>>>>>>").toString());
													System.out.println();
													TRADEDAO.dealCommodity(bargainvo.commodityID, localConnection);
													CurSubmitVO cursubmitvo = TRADEDAO.getCurSubmit(bargainvo.submitID);
													cursubmitvo.tradeFlag = 1;
													TRADEDAO.modifyCurSubmit(cursubmitvo, localConnection);
												} else {
													tradeuservo.totalSecurity += bargainvo.b_bail + bargainvo.b_poundage;
													tradeuservo.tradeCount++;
													CallableStatement callablestatement1 = localConnection
															.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
													callablestatement1.setString(2, tradeuservo.userCode);
													callablestatement1.setDouble(3, -1D * (bargainvo.b_bail + bargainvo.b_poundage));
													callablestatement1.setString(4, "4");
													callablestatement1.registerOutParameter(1, 8);
													callablestatement1.executeUpdate();
													TRADEDAO.modifyTradeUser(tradeuservo, localConnection);
													callablestatement1 = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													callablestatement1.setString(2, tradeuservo.userCode);
													callablestatement1.setInt(3, 404);
													callablestatement1.setDouble(4, bargainvo.b_bail);
													callablestatement1.setLong(5, bargainvo.contractID);
													callablestatement1.registerOutParameter(1, 8);
													callablestatement1.executeUpdate();
													double d2 = callablestatement1.getDouble(1);
													addDailyMoney(d2, tradeuservo.paymentForGoods, l10, bargainvo.userID, bargainvo.b_bail, 404, "",
															localConnection);
													callablestatement1 = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													callablestatement1.setString(2, tradeuservo.userCode);
													callablestatement1.setInt(3, 403);
													callablestatement1.setDouble(4, bargainvo.b_poundage);
													callablestatement1.setLong(5, bargainvo.contractID);
													callablestatement1.registerOutParameter(1, 8);
													callablestatement1.executeUpdate();
													d2 = callablestatement1.getDouble(1);
													addDailyMoney(d2, tradeuservo.paymentForGoods, l10, bargainvo.userID, bargainvo.b_poundage, 403,
															"", localConnection);
													System.out.println();
													System.out.println((new StringBuilder()).append("<<<<<<<<<<<<<<<<<<<  生成买方方流水")
															.append(bargainvo.userID).append(" 保证金为 ").append(bargainvo.b_bail).append("  手续费为 ")
															.append(bargainvo.b_poundage).append(">>>>>>>>>>").toString());
													System.out.println();
													CommodityVO commodityvo7 = TRADEDAO.getCommodity(bargainvo.commodityID, localConnection);
													delCommodityCharge(bargainvo.commodityID, localConnection);
													tradeuservo = TRADEDAO.getTradeUserForUpdate(commodityvo7.userID, localConnection);
													tradeuservo.tradeCount++;
													TRADEDAO.modifyTradeUser(tradeuservo, localConnection);
													callablestatement1 = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													callablestatement1.setString(2, tradeuservo.userCode);
													callablestatement1.setInt(3, 404);
													callablestatement1.setDouble(4, bargainvo.s_bail);
													callablestatement1.setLong(5, bargainvo.contractID);
													callablestatement1.registerOutParameter(1, 8);
													callablestatement1.executeUpdate();
													d2 = callablestatement1.getDouble(1);
													addDailyMoney(d2, tradeuservo.paymentForGoods, l10, commodityvo7.userID, bargainvo.s_bail, 404,
															"", localConnection);
													callablestatement1 = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
													callablestatement1.setString(2, tradeuservo.userCode);
													callablestatement1.setInt(3, 403);
													callablestatement1.setDouble(4, bargainvo.s_poundage);
													callablestatement1.setLong(5, bargainvo.contractID);
													callablestatement1.registerOutParameter(1, 8);
													callablestatement1.executeUpdate();
													d2 = callablestatement1.getDouble(1);
													addDailyMoney(d2, tradeuservo.paymentForGoods, l10, commodityvo7.userID, bargainvo.s_poundage,
															403, "", localConnection);
													System.out.println();
													System.out.println((new StringBuilder()).append("<<<<<<<<<<<<<<<<<<<  生成卖方方流水")
															.append(commodityvo7.userID).append(" 保证金为 ").append(bargainvo.s_bail).append("  手续费为 ")
															.append(bargainvo.s_poundage).append(">>>>>>>>>>").toString());
													System.out.println();
													TRADEDAO.dealCommodity(bargainvo.commodityID, localConnection);
													CurSubmitVO cursubmitvo1 = TRADEDAO.getCurSubmit(bargainvo.submitID);
													cursubmitvo1.tradeFlag = 1;
													TRADEDAO.modifyCurSubmit(cursubmitvo1, localConnection);
												}
												continue;
											}
											if (bargainvo.userID.equals(commodityvo5.userID)) {
												tradeuservo.totalSecurity += bargainvo.b_bail + bargainvo.b_poundage;
												tradeuservo.tradeCount++;
												CallableStatement callablestatement2 = localConnection
														.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
												callablestatement2.setString(2, tradeuservo.userCode);
												callablestatement2.setDouble(3, -1D * (bargainvo.b_bail + bargainvo.b_poundage));
												callablestatement2.setString(4, "4");
												callablestatement2.registerOutParameter(1, 8);
												callablestatement2.executeUpdate();
												TRADEDAO.modifyTradeUser(tradeuservo, localConnection);
												callablestatement2 = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
												callablestatement2.setString(2, tradeuservo.userCode);
												callablestatement2.setInt(3, 404);
												callablestatement2.setDouble(4, bargainvo.b_bail);
												callablestatement2.setLong(5, bargainvo.contractID);
												callablestatement2.registerOutParameter(1, 8);
												callablestatement2.executeUpdate();
												double d3 = callablestatement2.getDouble(1);
												addDailyMoney(d3, tradeuservo.paymentForGoods, l10, bargainvo.userID, bargainvo.b_bail, 404, "",
														localConnection);
												callablestatement2 = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
												callablestatement2.setString(2, tradeuservo.userCode);
												callablestatement2.setInt(3, 403);
												callablestatement2.setDouble(4, bargainvo.b_poundage);
												callablestatement2.setLong(5, bargainvo.contractID);
												callablestatement2.registerOutParameter(1, 8);
												callablestatement2.executeUpdate();
												d3 = callablestatement2.getDouble(1);
												addDailyMoney(d3, tradeuservo.paymentForGoods, l10, bargainvo.userID, bargainvo.b_poundage, 403, "",
														localConnection);
												System.out.println();
												System.out.println((new StringBuilder()).append("<<<<<<<<<<<<<<<<<<<  生成买方方流水")
														.append(bargainvo.userID).append(" 保证金为 ").append(bargainvo.b_bail).append("  手续费为 ")
														.append(bargainvo.b_poundage).append(">>>>>>>>>>").toString());
												System.out.println();
												CommodityVO commodityvo8 = TRADEDAO.getCommodity(bargainvo.commodityID, localConnection);
												delCommodityCharge(bargainvo.commodityID, localConnection);
												tradeuservo = TRADEDAO.getTradeUserForUpdate(commodityvo8.userID, localConnection);
												tradeuservo.tradeCount++;
												TRADEDAO.modifyTradeUser(tradeuservo, localConnection);
												callablestatement2 = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
												callablestatement2.setString(2, tradeuservo.userCode);
												callablestatement2.setInt(3, 404);
												callablestatement2.setDouble(4, bargainvo.s_bail);
												callablestatement2.setLong(5, bargainvo.contractID);
												callablestatement2.registerOutParameter(1, 8);
												callablestatement2.executeUpdate();
												d3 = callablestatement2.getDouble(1);
												addDailyMoney(d3, tradeuservo.paymentForGoods, l10, commodityvo8.userID, bargainvo.s_bail, 404, "",
														localConnection);
												callablestatement2 = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
												callablestatement2.setString(2, tradeuservo.userCode);
												callablestatement2.setInt(3, 403);
												callablestatement2.setDouble(4, bargainvo.s_poundage);
												callablestatement2.setLong(5, bargainvo.contractID);
												callablestatement2.registerOutParameter(1, 8);
												callablestatement2.executeUpdate();
												d3 = callablestatement2.getDouble(1);
												addDailyMoney(d3, tradeuservo.paymentForGoods, l10, commodityvo8.userID, bargainvo.s_poundage, 403,
														"", localConnection);
												System.out.println();
												System.out.println((new StringBuilder()).append("<<<<<<<<<<<<<<<<<<<  生成卖方方流水")
														.append(commodityvo8.userID).append(" 保证金为 ").append(bargainvo.s_bail).append("  手续费为 ")
														.append(bargainvo.s_poundage).append(">>>>>>>>>>").toString());
												System.out.println();
												TRADEDAO.dealCommodity(bargainvo.commodityID, localConnection);
												CurSubmitVO cursubmitvo2 = TRADEDAO.getCurSubmit(bargainvo.submitID);
												cursubmitvo2.tradeFlag = 1;
												TRADEDAO.modifyCurSubmit(cursubmitvo2, localConnection);
											} else {
												tradeuservo.totalSecurity += bargainvo.s_bail + bargainvo.s_poundage;
												tradeuservo.tradeCount++;
												CallableStatement callablestatement3 = localConnection
														.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");
												callablestatement3.setString(2, tradeuservo.userCode);
												callablestatement3.setDouble(3, -1D * (bargainvo.s_bail + bargainvo.s_poundage));
												callablestatement3.setString(4, "4");
												callablestatement3.registerOutParameter(1, 8);
												callablestatement3.executeUpdate();
												TRADEDAO.modifyTradeUser(tradeuservo, localConnection);
												callablestatement3 = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
												callablestatement3.setString(2, tradeuservo.userCode);
												callablestatement3.setInt(3, 404);
												callablestatement3.setDouble(4, bargainvo.s_bail);
												callablestatement3.setLong(5, bargainvo.contractID);
												callablestatement3.registerOutParameter(1, 8);
												callablestatement3.executeUpdate();
												double d4 = callablestatement3.getDouble(1);
												addDailyMoney(d4, tradeuservo.paymentForGoods, l10, bargainvo.userID, bargainvo.s_bail, 404, "",
														localConnection);
												callablestatement3 = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
												callablestatement3.setString(2, tradeuservo.userCode);
												callablestatement3.setInt(3, 403);
												callablestatement3.setDouble(4, bargainvo.s_poundage);
												callablestatement3.setLong(5, bargainvo.contractID);
												callablestatement3.registerOutParameter(1, 8);
												callablestatement3.executeUpdate();
												d4 = callablestatement3.getDouble(1);
												addDailyMoney(d4, tradeuservo.paymentForGoods, l10, bargainvo.userID, bargainvo.s_poundage, 403, "",
														localConnection);
												CommodityVO commodityvo9 = TRADEDAO.getCommodity(bargainvo.commodityID, localConnection);
												delCommodityCharge(bargainvo.commodityID, localConnection);
												tradeuservo = TRADEDAO.getTradeUserForUpdate(commodityvo9.userID, localConnection);
												tradeuservo.tradeCount++;
												TRADEDAO.modifyTradeUser(tradeuservo, localConnection);
												callablestatement3 = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
												callablestatement3.setString(2, tradeuservo.userCode);
												callablestatement3.setInt(3, 404);
												callablestatement3.setDouble(4, bargainvo.b_bail);
												callablestatement3.setLong(5, bargainvo.contractID);
												callablestatement3.registerOutParameter(1, 8);
												callablestatement3.executeUpdate();
												d4 = callablestatement3.getDouble(1);
												addDailyMoney(d4, tradeuservo.paymentForGoods, l10, commodityvo9.userID, bargainvo.b_bail, 404, "",
														localConnection);
												callablestatement3 = localConnection.prepareCall("{ ?=call FN_F_UpdateFunds(?,?,?,?) }");
												callablestatement3.setString(2, tradeuservo.userCode);
												callablestatement3.setInt(3, 403);
												callablestatement3.setDouble(4, bargainvo.b_poundage);
												callablestatement3.setLong(5, bargainvo.contractID);
												callablestatement3.registerOutParameter(1, 8);
												callablestatement3.executeUpdate();
												d4 = callablestatement3.getDouble(1);
												addDailyMoney(d4, tradeuservo.paymentForGoods, l10, commodityvo9.userID, bargainvo.b_poundage, 403,
														"", localConnection);
												TRADEDAO.dealCommodity(bargainvo.commodityID, localConnection);
												CurSubmitVO cursubmitvo3 = TRADEDAO.getCurSubmit(bargainvo.submitID);
												cursubmitvo3.tradeFlag = 1;
												TRADEDAO.modifyCurSubmit(cursubmitvo3, localConnection);
											}
										}

									}
									TRADEDAO.delTradeQuotation((new StringBuilder()).append("where tradePartition=").append(PARTITIONID).toString(),
											localConnection);
									if (abargainvo != null) {
										for (int j3 = 0; j3 < abargainvo.length; j3++) {
											CommodityVO commodityvo4 = getCurCommodity(abargainvo[j3].code);
											commodityvo4.bargainFlag = 1;
										}

									}
									modSysStatusDB(null, -1, null, 3, localConnection);
									modSysStatus(null, -1, null, 3, new Timestamp(l2));
								}
							}
							localConnection.commit();
						}
					}
				} catch (Exception localException1) {
					localException1 = localException1;
					localConnection.rollback();
					localException1.printStackTrace();
				} finally {
				}
				if ((i2 < 0) && (this.TRADESTATUS.getCurStatus() == 3)) {
					i2 = getNextSection();
					this.TRADESTATUS.setIsMaxTradeOrder(true);
					this.TRADESTATUS.setPartLastEndTime(new Timestamp(System.currentTimeMillis()));
					Thread.sleep(i4);
				} else {
					this.TRADESTATUS.setIsMaxTradeOrder(false);
					Thread.sleep(i4);
				}
			} catch (NullPointerException localNullPointerException) {
				localNullPointerException = localNullPointerException;
				try {
					localConnection = this.TRADEDAO.getJDBCConnection();
					localConnection.setAutoCommit(false);
				} catch (SQLException localSQLException2) {
					localSQLException2.printStackTrace();
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
			if (localCommodityVO.tradeMode == 0) {
				System.out.println("－－－－－－－－－－合同为竞买 ");
				System.out.println();
				if (localCommodityVO.userID.equals(paramString2)) {
					d1 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.s_security, d2), localCommodityVO.tradeUnit) * paramLong, 2);
					System.out.println("取卖方" + paramString2 + "保证金 =" + d1 + "  保证金系数是 " + localCommodityVO.s_security);
					System.out.println();
				} else {
					d1 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.b_security, d2), localCommodityVO.tradeUnit) * paramLong, 2);
					System.out.println("取买方" + paramString2 + "保证金 =" + d1 + "  保证金系数是 " + localCommodityVO.b_security);
					System.out.println();
				}
			} else {
				System.out.println("－－－－－－－－－－合同为竞卖 ");
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
			if (localCommodityVO.tradeMode == 0) {
				System.out.println("－－－－－－－－－－合同为竞买 ");
				if (localCommodityVO.userID.equals(paramString2)) {
					d1 = Arith.format(
							Arith.mul(Arith.mul(Arith.mul(paramDouble, localCommodityVO.s_security), d2), localCommodityVO.tradeUnit) * paramLong, 2);
					System.out.println("取卖方" + paramString2 + "保证金 =" + d1 + "  保证金系数是 " + localCommodityVO.s_security);
					System.out.println();
				} else {
					d1 = Arith.format(
							Arith.mul(Arith.mul(Arith.mul(paramDouble, localCommodityVO.b_security), d2), localCommodityVO.tradeUnit) * paramLong, 2);
					System.out.println("取买方" + paramString2 + "保证金 =" + d1 + "  保证金系数是 " + localCommodityVO.b_security);
					System.out.println();
				}
			} else if (localCommodityVO.userID.equals(paramString2)) {
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
		return d1;
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
			System.out.println("收费费折扣率为-------------- " + d2);
		}
		if (i == 1) {
			if (k == 0) {
				System.out.println();
				System.out.println("======================== 手续费为绝对值 =====================");
				System.out.println();
				if (localCommodityVO.tradeMode == 0) {
					System.out.println();
					System.out.println("======================== 竞买 =====================");
					System.out.println();
					if (localCommodityVO.userID.equals(paramString2)) {
						d1 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.s_fee, d2), localCommodityVO.tradeUnit) * paramLong, 2);
						System.out.println();
						System.out.println("卖方 " + paramString2 + " 手续费为 " + d1 + "手续费系数为 " + localCommodityVO.s_fee);
					} else {
						d1 = Arith.format(Arith.mul(Arith.mul(localCommodityVO.b_fee, d2), localCommodityVO.tradeUnit) * paramLong, 2);
						System.out.println();
						System.out.println("买方 " + paramString2 + " 手续费为 " + d1 + "手续费系数为 " + localCommodityVO.b_fee);
					}
				} else {
					System.out.println();
					System.out.println("======================== 竞卖 =====================");
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
				if (localCommodityVO.tradeMode == 0) {
					System.out.println();
					System.out.println("======================== 竞买 =====================");
					System.out.println();
					if (localCommodityVO.userID.equals(paramString2)) {
						d1 = Arith.format(
								Arith.mul(Arith.mul(Arith.mul(paramDouble, localCommodityVO.s_fee), d2), localCommodityVO.tradeUnit) * paramLong, 2);
						System.out.println();
						System.out.println("卖方 " + paramString2 + " 手续费为 " + d1 + "手续费系数为 " + localCommodityVO.s_fee);
					} else {
						d1 = Arith.format(
								Arith.mul(Arith.mul(Arith.mul(paramDouble, localCommodityVO.b_fee), d2), localCommodityVO.tradeUnit) * paramLong, 2);
						System.out.println();
						System.out.println("买方 " + paramString2 + " 手续费为 " + d1 + "手续费系数为 " + localCommodityVO.b_fee);
					}
				} else if (localCommodityVO.userID.equals(paramString2)) {
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
		return d1;
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

	private void setContractContent(BargainVO paramBargainVO, Connection paramConnection) {
		try {
			String str1 = this.TRADEDAO.getContractTemplet(paramBargainVO.tradePartition, paramConnection);
			if ((str1 == null) || (str1.equals(""))) {
				str1 = "<html><head><metacontent='text/html;charset=gb2312'http-equiv='Content-Type'/><style type='text/css'>.top_txt{\tfont-size:18px;\tfont-weight:bold;\tcolor:#FFFF00;}body{\tmargin-left:0px;\tmargin-top:0px;\tmargin-right:0px;\tmargin-bottom:0px;}.txt{\tline-height:18px;\tfont-size:12px;\tfont-weight:normal;\tcolor:#175768;\ttext-decoration:none;}.xbt{\tfont-size:12px;\tfont-weight:bold;\tcolor:#175768;\ttext-decoration:none;}.imp{\tfont-size:12px;\tfont-weight:normal;\tcolor:#FF3300;\ttext-decoration:none;}.td_nr{\tfont-size:12px;\tfont-weight:normal;\tcolor:#175768;\ttext-decoration:none;\ttext-align:left;}td{\tfont-size:12px;\tfont-weight:normal;\tcolor:#175768;}.k{\theight:16px;\tborder-top-width:1px;border-right-width:1px;\tborder-bottom-width:1px;border-left-width:1px;\tborder-top-style:dotted;border-right-style:dotted;\tborder-bottom-style:solid;border-left-style:dotted;\tborder-top-color:#FFFFFF;border-right-color:#FFFFFF;\tborder-bottom-color:#175768;border-left-color:#FFFFFF;\tfont-size:12px;\tfont-weight:normal;color:#175768;\ttext-decoration:none;}</style><metacontent='MSHTML6.00.2900.2802'name='GENERATOR'/><title>合同</title></head><body><table style='width:15.6cm'height='340'cellspacing='0'cellpadding='0'align='center'border='0'><tbody>\t<tr><td valign='top'><table height='35'cellspacing='0'cellpadding='0'width='100%'border='0'><tbody><tr><td bgcolor='#175768'><div class='top_txt'align='center'>竞价交易合同</div></td></tr></tbody></table><table height='270'cellspacing='0'cellpadding='0'width='100%'align='center'border='0'><tbody><tr><td valign='top'bgcolor='#ffffff'><table height='761'cellspacing='0'cellpadding='0'width='98%'align='center'border='0'><tbody><tr><td class='txt'valign='top'><br/><table cellspacing='0'cellpadding='0'width='100%'border='0'><tbody><tr><td width='10%'><div class='xbt'align='right'>卖方：</div></td><td width='55%'>buy_no</td><td width='15%'height='24'><div class='xbt'align='right'>合同编号：</div></td><td class='td_nr'>t_contractid号</td></tr><tr><td height='24'><div class='xbt'align='right'>买方：</div></td><td class='td_nr'>sell_no</td><td height='24'><divclass='xbt'align='right'>&nbsp;</div></td><td class='td_nr'>&nbsp;</td></tr></tbody></table>\t<p><strong>第一条</strong>&nbsp;&nbsp;中标标的号、品种、数量、单价、价款：</p><div align='center'><table style='border-collapse:collapse'bordercolor='#175768'cellspacing='0'cellpadding='0'bgcolor='#175768'border='1'><tbody><tr><td width='91'bgcolor='#ffffff'height='24'><p class='xbt'align='center'>标的号</p></td><td width='98'bgcolor='#ffffff'><p class='xbt'align='center'>品种</p></td><td width='112'bgcolor='#ffffff'><p class='xbt'align='center'>数量（units）</p></td><td width='140'bgcolor='#ffffff'><p class='xbt'align='center'>单价（元/units）</p></td><td width='147'bgcolor='#ffffff'><p class='xbt'align='center'>金额（元）</p></td></tr><tr><td width='91'bgcolor='#ffffff'height='24'><p align='center'>&nbsp;t_code</p></td><td width='98'bgcolor='#ffffff'><p align='center'>&nbsp;t_type</p></td><td width='112'bgcolor='#ffffff'><p align='center'>&nbsp;t_amount</p></td><td width='140'bgcolor='#ffffff'><p align='center'>&nbsp;t_price</p></td><td width='147'bgcolor='#ffffff'><p align='center'>&nbsp;t_money</p></td></tr><tr><td width='588'bgcolor='#ffffff'colspan='6'height='24'><p>&nbsp;&nbsp;<span class='xbt'>合计人民币（大写金额）：</span>&nbsp;t_bmoney</p></td></tr></tbody></table></div><span class='xbt'>第二条</span>&nbsp;&nbsp;质量标准、用途：中标标的号对应的质量等级。<br/><span class='xbt'>第三条</span>&nbsp;&nbsp;交（提）货期限：见《竞价销售交易细则》第二十七条之规定。<br/><span class='xbt'>第四条</span>&nbsp;&nbsp;交（提）货地点：中标标的号对应的存储地点。<br/><span class='xbt'>第五条</span>&nbsp;&nbsp;包装物标准及费用负担（含打包、上汽车费用）：见《竞价销售交易细则》第二十九条。<br/><span class='xbt'>第六条</span>&nbsp;&nbsp;交货方式：卖方承储库仓内交货。<br/><span class='xbt'>第七条</span>&nbsp;&nbsp;运输方式及到达站（港）和费用负担：买方自负。<br/><span class='xbt'>第八条</span>&nbsp;&nbsp;货物验收办法：买方提货时验收，数量以卖方交货仓库计量衡器为准。<br/>\t<span class='xbt'>第九条</span>&nbsp;&nbsp;结算方式：委托交易市场结算。<br/><span class='xbt'>第十条</span>&nbsp;&nbsp;违约责任：按照《竞价销售交易细则》第三十八、三十九、四十、四十一条执行。<br/><span class='xbt'>第十一条</span>&nbsp;&nbsp;合同争议的解决方式：本合同在履行过程中发生的争议由当事人协商解决，交易市场负责调解；当事人协商调解不成的，提交行业争议仲裁中心仲裁。<br/>&nbsp;&nbsp;&nbsp;附则：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（1）本合同签订双方承认并遵守《竞价销售交易细则》及《竞价销售清单》。<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（2）本合同签订双方需按成交金额的一定比例分别向交易市场交纳手续费。<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（3）卖方、买方签订合同前按一定比例交纳履约保证金交交易市场。<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（4）本合同经双方交易代表签字盖章后生效。<br/><span class='xbt'>第十二条</span>&nbsp;&nbsp;其他约定事项：<br/><span class='xbt'>第十三条</span>&nbsp;&nbsp;本合同一式三份，买卖双方各执一份，交易市场留存一份。<p>&nbsp;</p><table cellspacing='0'cellpadding='0'width='90%'align='center'border='0'><tbody><tr><td width='24%'height='29%'><div align='right'>卖方（代章）：</div></td><td width='27%'>&nbsp;</td><td width='24%'height='24%'><div align='right'>买方名称：</div></td><td width='21%'>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>承储库点名称：</div></td><td>&nbsp;</td><td height='24%'><div align='right'>地&nbsp;&nbsp;址：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='29%'height='24%'><div align='right'>法定代表人（签字）：</div></td><td>&nbsp;</td><td height='29%'height='24%'><div align='right'>委托代理人（签字）：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>电&nbsp;&nbsp;话：</div></td><td><span class='td_nr'>&nbsp;</span></td><td height='24%'><div align='right'>电&nbsp;&nbsp;话：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>开户银行：</div></td><td><span class='td_nr'>&nbsp;</span></td><td height='24%'><div align='right'>开户银行：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>帐&nbsp;&nbsp;号：</div></td><td><span class='td_nr'>&nbsp;</span></td><td height='24%'><div align='right'>帐&nbsp;&nbsp;号：</div></td><td>&nbsp;</td></tr><tr><td colspan='4'>&nbsp;</td></tr><tr><td height='24%'><div align='right'>邮政编码：</div></td><td><span class='td_nr'>&nbsp;</span></td><td height='24%'><div align='right'>邮政编码：</div></td><td>&nbsp;</td></tr></tbody></table><table height='43'cellspacing='0'cellpadding='0'width='94%'align='center'border='0'><tbody><tr><td>&nbsp;</td><td width='25%'><span class='td_nr'>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class='xbt'>年</span><span class='td_nr'>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class='xbt'>月</span><span class='td_nr'>&nbsp;&nbsp;&nbsp;&nbsp;</span><span class='xbt'>日</span></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></body></html>";
			}
			CommodityVO localCommodityVO = getCurCommodity(paramBargainVO.code);
			TradeUserVO localTradeUserVO1 = null;
			TradeUserVO localTradeUserVO2 = null;
			if (localCommodityVO.tradeMode == 0) {
				localTradeUserVO1 = this.TRADEDAO.getTradeUser(paramBargainVO.userID);
				localTradeUserVO2 = this.TRADEDAO.getTradeUser(localCommodityVO.userID);
			} else if (localCommodityVO.tradeMode == 1) {
				localTradeUserVO1 = this.TRADEDAO.getTradeUser(localCommodityVO.userID);
				localTradeUserVO2 = this.TRADEDAO.getTradeUser(paramBargainVO.userID);
			}
			if (localCommodityVO.tradeMode == 0) {
				str1 = str1.replaceAll("t_buyername", paramBargainVO.userID);
				str1 = str1.replaceAll("t_sellername", localCommodityVO.userID);
			} else if (localCommodityVO.tradeMode == 1) {
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
}
