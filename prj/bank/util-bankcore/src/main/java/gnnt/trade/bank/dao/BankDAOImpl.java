package gnnt.trade.bank.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import gnnt.trade.bank.util.Encryption;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.Account;
import gnnt.trade.bank.vo.BankCompareInfoValue;
import gnnt.trade.bank.vo.BankQSNetChild;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.BanksInfoValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CitysValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.DicValue;
import gnnt.trade.bank.vo.FeeInfoVO;
import gnnt.trade.bank.vo.Firm;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmFundsValue;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmOpenCloseBank;
import gnnt.trade.bank.vo.FirmTradeStatus;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.FrozenBalanceVO;
import gnnt.trade.bank.vo.FundsAndInterests;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.LogValue;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.QSChangeResult;
import gnnt.trade.bank.vo.QSRresult;
import gnnt.trade.bank.vo.RgstCapitalValue;
import gnnt.trade.bank.vo.TradeDetailAccount;
import gnnt.trade.bank.vo.TradeResultValue;
import gnnt.trade.bank.vo.Trademodule;
import gnnt.trade.bank.vo.TransMnyObjValue;
import gnnt.trade.bank.vo.bankdz.BankQSVO;
import gnnt.trade.bank.vo.bankdz.TransferBank;
import gnnt.trade.bank.vo.bankdz.boc.AccountStatusReconciliation;
import gnnt.trade.bank.vo.bankdz.boc.ClientState;
import gnnt.trade.bank.vo.bankdz.boc.StorageMoneyLedgerBalanceList;
import gnnt.trade.bank.vo.bankdz.boc.StorageMoneySettlementList;
import gnnt.trade.bank.vo.bankdz.boc.TransferAccountsTransactionDetailed;
import gnnt.trade.bank.vo.bankdz.gs.sent.BankFirmRightValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.FirmRightsValue;
import gnnt.trade.bank.vo.bankdz.gs.sent.ProperBalanceValue;
import gnnt.trade.bank.vo.bankdz.hx.sent.HXSentQSMsgValue;
import gnnt.trade.bank.vo.bankdz.hx.sent.child.DZMessage;
import gnnt.trade.bank.vo.bankdz.hx.sent.child.QSMessage;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import gnnt.trade.bank.vo.bankdz.pf.sent.Margins;
import gnnt.trade.bank.vo.bankdz.pf.sent.TradeList;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzBChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzFailChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatFailResultChild;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.KXHfailChild;
import gnnt.trade.bank.vo.bankdz.sfz.sent.child.BatQsChild;
import gnnt.trade.bank.vo.bankdz.xy.XYMarketMoney;
import gnnt.trade.bank.vo.bankdz.xy.resave.FFHDValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.ZFPHValue;
import gnnt.trade.bank.vo.bankdz.xy.resave.child.FirmDateValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmRightValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.MarketRightValue;

/**
 * <p>
 * Title: 数据库访问对象实现类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: gnnt
 * </p>
 */
public class BankDAOImpl extends BankDAO {

	// private transient final Log logger =
	// LogFactory.getLog(BankDAOImpl.class);
	private transient final Log logger = LogFactory.getLog("Processorlog");

	/**
	 * 构造函数
	 */
	public BankDAOImpl() throws Exception {
		super();
	}

	private void log(String string) {
		logger.debug(string);
	}

	public Trademodule getTrademodule(int moduleID) {
		Trademodule result = null;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "select * from c_trademodule where moduleid=" + moduleID;
		try {
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if (rs.next()) {
				result = new Trademodule();
				result.moduleID = rs.getInt("moduleID");
				result.cnName = rs.getString("cnName");
				result.enName = rs.getString("enName");
				result.shortname = rs.getString("shortname");
				result.addFirmFN = rs.getString("addFirmFN");
				result.updateFirmStatusFN = rs.getString("updateFirmStatusFN");
				result.delFirmFN = rs.getString("delFirmFN");
				result.isFirmset = rs.getString("isFirmset");
				result.hostip = rs.getString("hostip");
				result.port = rs.getInt("port");
				result.rmiDataport = rs.getInt("rmiDataport");
				result.isBalanceCheck = rs.getString("isBalanceCheck");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	/**
	 * 修改资金流水记录状态
	 * 
	 * @param id
	 *            资金流水号
	 * @param funID
	 *            银行业务流水号
	 * @param status
	 *            状态 0：成功 1：失败
	 * @param bankTime
	 *            银行端操作时间
	 * @param conn
	 *            数据库联接
	 * @return int 0：成功 非0：失败
	 * @throws SQLException
	 */
	public int modCapitalInfoStatus(long id, String funID, int status, Timestamp bankTime, Connection conn) throws SQLException {
		log("===>>>修改资金流水记录状态   modCapitalInfoStatus  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			String funidf = "";
			int n = 1;
			if (funID == null || funID.trim().length() <= 0) {
				funID = "";
			} else {
				funidf = ",funid2=?";
			}
			sql = "update F_B_capitalInfo set status=?,FUNID=?,bankTime=?" + funidf + " where id=?";

			state = conn.prepareStatement(sql);
			state.setInt(n++, status);
			state.setString(n++, funID.trim());
			state.setTimestamp(n++, bankTime);
			if (funidf != null && funidf.trim().length() > 0) {
				state.setString(n++, funID);
			}
			state.setLong(n++, id);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 修改资金流水记录状态
	 * 
	 * @param actionid
	 *            市场流水号
	 * @param funID
	 *            银行业务流水号
	 * @param conn
	 *            数据库联接
	 * @return int 0：成功 非0：失败
	 * @throws SQLException
	 */
	public int modCapitalInfoStatus(long actionid, String funID, Connection conn) throws SQLException {
		log("===>>>修改资金流水记录状态   modCapitalInfoStatus  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			if (funID == null)
				funID = "";
			sql = "update F_B_capitalInfo set FUNID=? where actionid=?";

			state = conn.prepareStatement(sql);
			state.setString(1, funID.trim());
			state.setLong(2, actionid);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 判断现在是否可以转账
	 * 
	 * @param String
	 *            bankID
	 * @return int n (0 可以转账,1 非交易日,2 未到交易时间,3 交易时间已过,4 禁止交易)
	 */
	public int useBank(String bankID) throws SQLException, ClassNotFoundException {
		this.log("判断现在是否可以转账");
		int result = 4;
		BankValue bv = this.getBank(bankID);
		if (bv == null) {
			return result;
		} else {
			if (bv.control == 1) {
				result = 0;
			} else if (bv.control == 2) {
				if (this.getTradeDate(new java.util.Date())) {
					result = 0;
				} else {
					result = 1;
				}
			} else if (bv.control == 3) {
				int n = this.getTradeTime(bv.beginTime, bv.endTime);
				switch (n) {
				case 0:
					result = 0;
					break;
				case 1:
					result = 2;
					break;
				case 2:
					result = 3;
					break;
				}
			} else if (bv.control == 0) {
				if (this.getTradeDate(new java.util.Date())) {
					int n = this.getTradeTime(bv.beginTime, bv.endTime);
					switch (n) {
					case 0:
						result = 0;
						break;
					case 1:
						result = 2;
						break;
					case 2:
						result = 3;
						break;
					}
				} else {
					result = 1;
				}
			}
		}
		return result;
	}

	/**
	 * 判断是否超出了转账时间段限制 0 可以交易,1 未到交易时间,2 交易时间已过
	 */
	private int getTradeTime(String startTime, String endTime) {
		this.log("判断是否超出了交易时间范围");
		int result = 1;
		if (startTime == null || startTime.trim().length() <= 0 || endTime == null || endTime.trim().length() <= 0) {
			return 0;
		} else {
			startTime = startTime.trim();
			endTime = endTime.trim();
			if (startTime.length() < 6) {
				for (int i = 0; i < 6 - startTime.length(); i++) {
					startTime += "0";
				}
			}
			if (endTime.length() < 6) {
				for (int i = 0; i < 6 - startTime.length(); i++) {
					endTime += "0";
				}
			}
		}
		java.util.Date now = new java.util.Date();
		java.util.Date start = Tool.getDate(startTime);
		java.util.Date end = Tool.getDate(endTime);
		if (now.getTime() <= start.getTime()) {
			result = 1;
		} else if (now.getTime() >= end.getTime()) {
			result = 2;
		} else {
			result = 0;
		}
		return result;
	}

	/**
	 * 判断当天是否是交易日 true 可以交易,false 不可以交易
	 */
	public boolean getTradeDate(java.util.Date tradeDate) throws SQLException, ClassNotFoundException {
		this.log("判断是否为交易日");
		boolean flag = true;
		Calendar c = Calendar.getInstance();
		c.setTime(tradeDate);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);// 取得星期
		String date = Tool.fmtDate(tradeDate);
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		// String sql = "select * from t_a_nottradeday";
		// TODO 现货和订单中的表不一致
		String sql = "select * from e_tradetime_rt";
		try {
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				// String nweeks = rs.getString("week");
				// String ndates = rs.getString("day");
				// TODO 现货订单中的字段名不一致
				String nweeks = rs.getString("restweekday");
				String ndates = rs.getString("holiday");
				if (nweeks == null) {
					nweeks = "";
				}
				if (ndates == null) {
					ndates = "";
				}
				String[] weeks = nweeks.split(",");
				String[] dates = ndates.split(",");
				for (String week : weeks) {
					if ((dayOfWeek + "").equalsIgnoreCase(week)) {
						flag = false;
					}
				}
				for (String ndate : dates) {
					if (date.equals(ndate)) {
						flag = false;
					}
				}
			}
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			flag = false;
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return flag;
	}

	/**
	 * 取得交易商浮动盈亏数据
	 * 
	 * @param firmIDs
	 *            交易商代码集
	 * @param conn
	 *            数据库连接
	 * @return Vector<TholdPositionValue>
	 * @throws SQLException
	 */
	public Vector<FirmBalanceValue> getFlote(String[] firmIDs) throws SQLException, ClassNotFoundException {
		Vector<FirmBalanceValue> result = new Vector<FirmBalanceValue>();
		// TODO 远期中有浮动盈亏，现货中没有浮动盈亏
		for (String firmID : firmIDs) {
			FirmBalanceValue value = new FirmBalanceValue();
			value.firmId = firmID;
			value.floatingloss = 0;
			result.add(value);
		}

		// Connection conn = null;
		// PreparedStatement state = null;
		// ResultSet rs = null;
		// String sql = null;
		// String filter = "";
		// if (firmIDs != null && firmIDs.length > 0)
		// {
		// String firms = "";
		// for (String firmID : firmIDs)
		// {
		// if (firmID != null && firmID.trim().length() > 0)
		// {
		// firms += "'" + firmID.trim() + "',";
		// }
		// }
		// if (firms != null && firms.trim().length() > 0)
		// {
		// filter += " and firmid in (" + firms.substring(0, firms.length() - 1) + ") ";
		// }
		// }
		// try
		// {
		// sql = "select sum(floatingloss) floatingloss,firmID from t_holdposition where 1=1 " + filter + " group by firmid order by firmid";
		// this.log(sql);
		// conn = this.getConnection();
		// state = conn.prepareStatement(sql);
		// rs = state.executeQuery();
		// while (rs.next())
		// {
		// FirmBalanceValue value = new FirmBalanceValue();
		// value.firmId = rs.getString("firmID");
		// value.floatingloss = rs.getDouble("floatingloss");
		// result.add(value);
		// }
		// } catch (SQLException e)
		// {
		// throw e;
		// } catch (ClassNotFoundException e)
		// {
		// throw e;
		// } finally
		// {
		// this.closeStatement(rs, state, conn);
		// }
		return result;
	}

	/**
	 * 修改资金流水记录描述
	 * 
	 * @param id
	 * @param describe
	 *            描述信息
	 * @param conn
	 * @return int 0：成功 非0：失败
	 * @throws SQLException
	 */
	public int modCapitalInfoNote(long id, String note, Connection conn) throws SQLException {
		log("===>>>修改资金流水记录描述   modCapitalInfoNote  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "update F_B_capitalInfo set note='" + note.trim() + "' where id=" + id + " ";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 取得市场业务流水号
	 * 
	 * @param conn
	 *            数据库联接
	 * @return long 市场业务流水号
	 * @throws SQLException
	 */
	public long getActionID(Connection conn) throws SQLException {
		log("===>>>取得市场业务流水号   getActionID  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1;
		try {
			sql = "select seq_F_B_action.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			id = -1;
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return id;
	}

	/**
	 * 取得市场业务流水号
	 * 
	 * @return long 市场业务流水号
	 */
	public long getActionID() {
		log("===>>>取得市场业务流水号   getActionID  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = 0;
		try {
			conn = getConnection();

			sql = "select seq_F_B_action.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStatement(rs, state, conn);
		}
		return id;
	}

	/**
	 * 增加资金流水记录
	 * 
	 * @param val
	 *            CapitalValue
	 * @param conn
	 *            数据库联接
	 * @return long 资金流水号
	 * @throws SQLException
	 */
	public long addCapitalInfo(CapitalValue val, Connection conn) throws SQLException {
		log("===>>>增加资金流水记录   addCapitalInfo  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1;
		try {
			sql = "select seq_F_B_capitalInfo.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			sql = "insert into F_B_capitalInfo(ID, FIRMID, FUNID, BANKID, DEBITID, CREDITID, TYPE,"
					+ " MONEY, OPERATOR, CREATETIME, BANKTIME, STATUS, NOTE, ACTIONID,EXPRESS,bankName,account,createdate,funid2) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?,?,?,?,to_char(sysdate,'yyyy-MM-dd'),?)";

			state = conn.prepareStatement(sql);
			state.setLong(1, id);
			state.setString(2, val.firmID == null ? "" : val.firmID.trim());
			state.setString(3, val.funID == null ? "" : val.funID.trim());
			state.setString(4, val.bankID == null ? "" : val.bankID.trim());
			state.setString(5, val.debitID == null ? "" : val.debitID.trim());
			state.setString(6, val.creditID == null ? "" : val.creditID.trim());
			state.setInt(7, val.type);
			state.setDouble(8, val.money);
			state.setString(9, val.oprcode == null ? "" : val.oprcode.trim());
			state.setTimestamp(10, val.bankTime);
			state.setInt(11, val.status);
			state.setString(12, val.note == null ? "" : val.note.trim());
			state.setLong(13, val.actionID);
			state.setInt(14, val.express);
			state.setString(15, val.bankName);
			state.setString(16, val.account);
			state.setString(17, (val.funID == null || val.funID.trim().length() <= 0) ? "gnnt" + id : val.funID);
			state.executeUpdate();
			System.out.println("【新增流水】:流水号[" + id + "]" + "银行流水号[" + val.funID + "]" + "市场流水号[" + val.actionID + "]" + "金额[" + val.money + "]");
		} catch (SQLException e) {
			e.printStackTrace();
			id = -1;
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return id;
	}

	/**
	 * 取得市场资金流水记录列表
	 * 
	 * @param filter
	 * @param conn
	 *            数据库联接
	 * @return Vector 每项为CapitalInfo
	 * @throws SQLException
	 */
	public Vector<CapitalValue> getCapitalInfoList(String filter, Connection conn) throws SQLException {
		log("===>>>取得资金流水记录列表   getCapitalInfoList  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalValue value = null;
		Vector<CapitalValue> list = new Vector<CapitalValue>();
		try {
			sql = "select * from F_B_capitalInfo " + filter;
			System.out.println("取得资金流水记录列表:sql=" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new CapitalValue();
				value.actionID = rs.getLong("actionID");
				value.bankID = rs.getString("bankID");
				value.bankTime = rs.getTimestamp("bankTime");
				value.createtime = rs.getTimestamp("createtime");
				value.creditID = rs.getString("creditID");
				value.debitID = rs.getString("debitID");
				value.firmID = rs.getString("firmID");
				value.funID = rs.getString("funID");
				value.iD = rs.getLong("iD");
				value.money = rs.getDouble("money");
				value.note = rs.getString("note");
				value.oprcode = rs.getString("operator");
				value.status = rs.getInt("status");
				value.type = rs.getInt("type");
				value.express = rs.getInt("express");
				value.bankName = rs.getString("bankName");
				value.account = rs.getString("account");
				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return list;
	}

	/**
	 * 取得市场资金流水记录列表
	 * 
	 * @param filter
	 * @return Vector 每项为CapitalInfo
	 * @throws SQLException
	 */
	public Vector<CapitalValue> getCapitalInfoList(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>取得资金流水记录列表   getCapitalInfoList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalValue value = null;
		Vector<CapitalValue> list = new Vector<CapitalValue>();
		try {
			conn = getConnection();

			sql = "select * from F_B_capitalInfo " + filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new CapitalValue();
				value.actionID = rs.getLong("actionID");
				value.bankID = rs.getString("bankID");
				value.bankTime = rs.getTimestamp("bankTime");
				value.createtime = rs.getTimestamp("createtime");
				value.creditID = rs.getString("creditID");
				value.debitID = rs.getString("debitID");
				value.firmID = rs.getString("firmID");
				value.funID = rs.getString("funID");
				value.iD = rs.getLong("iD");
				value.money = rs.getDouble("money");
				value.note = rs.getString("note");
				value.oprcode = rs.getString("operator");
				value.status = rs.getInt("status");
				value.type = rs.getInt("type");
				value.express = rs.getInt("express");
				value.bankName = rs.getString("account");
				value.account = rs.getString("account");
				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return list;
	}

	/**
	 * 取得市场资金流水记录列表
	 * 
	 * @param filter
	 * @return Vector 每项为CapitalInfo
	 * @throws SQLException
	 */
	public Vector<CapitalValue> getCapitalInfoList2(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>取得资金流水记录列表   getCapitalInfoList2  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalValue value = null;
		Vector<CapitalValue> list = new Vector<CapitalValue>();
		try {
			conn = getConnection();

			sql = "select c.*,f.accountname firmName,b.bankName bbankName from f_b_capitalinfo c,f_b_firmidandaccount f,f_b_banks b where f.firmid(+)=c.firmid and c.bankid=b.bankid(+) "
					+ filter;
			log("sql:" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new CapitalValue();
				value.actionID = rs.getLong("actionID");
				value.bankID = rs.getString("bankID");
				value.bankTime = rs.getTimestamp("bankTime");
				value.createtime = rs.getTimestamp("createtime");
				value.creditID = rs.getString("creditID");
				value.debitID = rs.getString("debitID");
				value.firmID = rs.getString("firmID");
				value.funID = rs.getString("funID");
				value.iD = rs.getLong("iD");
				value.money = rs.getDouble("money");
				value.note = rs.getString("note");
				value.oprcode = rs.getString("operator");
				value.status = rs.getInt("status");
				value.type = rs.getInt("type");
				value.express = rs.getInt("express");
				value.bankName = rs.getString("bbankName");
				value.account = rs.getString("account");
				value.firmName = rs.getString("firmName");
				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return list;
	}

	/**
	 * 取得银行资金流水记录列表
	 * 
	 * @param filter
	 * @return Vector 每项为CapitalInfo
	 * @throws SQLException
	 */
	public Vector<BankCompareInfoValue> getBankCapInfoList(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>取得银行资金流水记录列表   getBankCapInfoList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		BankCompareInfoValue value = null;
		Vector<BankCompareInfoValue> list = new Vector<BankCompareInfoValue>();
		try {
			conn = getConnection();

			sql = "select id,funid,firmid,bankid,account,type,money," + "nvl(to_char(compareDate,'yyyy-MM-dd'),'') cDate,nvl(Note,'') note,"
					+ "nvl(to_char(createtime,'yyyy-MM-dd'),'') cTime,status from f_b_bankcompareinfo " + filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new BankCompareInfoValue();
				value.setId(rs.getLong("id"));
				value.setFunid(rs.getString("funid"));
				value.setFirmid(rs.getString("firmid"));
				value.setBankid(rs.getString("bankid"));
				value.setAccount(rs.getLong("account"));
				value.setType(rs.getInt("type"));
				value.setMoney(rs.getDouble("money"));
				value.setCompareDate(rs.getString("cDate"));
				value.setNote(rs.getString("note"));
				value.setCreateTime(rs.getString("cTime"));
				value.setStatus(rs.getInt("status"));

				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return list;
	}

	/**
	 * 合计资金流水金额
	 * 
	 * @param filter
	 * @param conn
	 *            数据库联接
	 * @return double
	 * @throws SQLException
	 */
	public double sumCapitalInfo(String filter, Connection conn) throws SQLException {
		log("===>>>合计资金流水金额   sumCapitalInfo  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		double result = 0;
		try {
			sql = "select nvl(sum(value),0) money from f_clientledger " + filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				result = rs.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	/**
	 * 每添加一个银行，就需要在系统字典表中对应添加一组给银行ID对应的帐户信息 或者有其他更好的办法处理。
	 * 不然会因为没有对应的数据而在计算费用的时候出现异常
	 */
	public void addBank(BankValue val) throws SQLException, ClassNotFoundException {
		log("===>>>增加银行   addBank  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql;
		try {
			conn = getConnection();
			sql = " insert into F_B_banks " + " (bankid, bankname, maxpertransmoney, maxpertranscount,"
					+ " adapterclassname, validflag, maxPerSglTransMoney,maxAuditMoney,beginTime,endTime,control) "
					+ " values(?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

			state = conn.prepareStatement(sql);
			state.setString(1, val.bankID);
			state.setString(2, val.bankName);
			state.setDouble(3, val.maxPerTransMoney);
			state.setInt(4, val.maxPerTransCount);
			state.setString(5, val.adapterClassname);
			state.setInt(6, val.validFlag);
			state.setDouble(7, val.maxPerSglTransMoney);
			state.setDouble(8, val.maxAuditMoney);
			state.setString(9, val.beginTime);
			state.setString(10, val.endTime);
			state.setInt(11, val.control);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	/**
	 * 修改银行
	 * 
	 * @param val
	 *            BankValue
	 * @return void
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public void modBank(BankValue val) throws SQLException, ClassNotFoundException {
		log("===>>>修改银行   modBank  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql;
		try {
			conn = getConnection();
			sql = "update F_B_banks set bankname = ?,maxpertransmoney = ?,maxpertranscount = ?,"
					+ "adapterclassname = ?,validflag = ?,maxPerSglTransMoney=?,maxAuditMoney=?,beginTime=?,endTime=?,control=? where bankid = ?";
			state = conn.prepareStatement(sql);
			state.setString(1, val.bankName);
			state.setDouble(2, val.maxPerTransMoney);
			state.setInt(3, val.maxPerTransCount);
			state.setString(4, val.adapterClassname);
			state.setInt(5, val.validFlag);
			state.setDouble(6, val.maxPerSglTransMoney);
			state.setDouble(7, val.maxAuditMoney);
			state.setString(8, val.beginTime);
			state.setString(9, val.endTime);
			state.setInt(10, val.control);
			state.setString(11, val.bankID);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	/**
	 * 删除银行
	 * 
	 * @param bankID
	 *            String
	 * @return void
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public void delBank(String bankID) throws SQLException, ClassNotFoundException {
		log("===>>>删除银行   delBank  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql;
		try {
			conn = getConnection();
			sql = "delete from F_B_banks where bankid='" + bankID + "'";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
			// boolean flag=this.delBankTime(bankID, conn);
			// if(flag){
			// conn.commit();
			// }else{
			// conn.rollback();
			// }
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	/**
	 * 取得银行信息
	 * 
	 * @param bankID
	 *            银行代码
	 * @param conn
	 *            数据库联接
	 * @return BankValue
	 * @throws SQLException
	 */
	public BankValue getBank(String bankID, Connection conn) throws SQLException {
		log("===>>>取得银行信息   getBank  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		BankValue value = null;
		try {
			sql = "select * from F_B_banks where bankid='" + bankID + "'";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new BankValue();
				value.adapterClassname = rs.getString("adapterClassname");
				value.bankID = rs.getString("bankID");
				value.bankName = rs.getString("bankName");
				value.maxPerTransCount = rs.getInt("maxPerTransCount");
				value.maxPerTransMoney = rs.getDouble("maxPerTransMoney");
				value.maxAuditMoney = rs.getDouble("maxAuditMoney");
				value.validFlag = rs.getInt("validFlag");
				value.maxPerSglTransMoney = rs.getDouble("maxPerSglTransMoney");
				value.beginTime = rs.getString("beginTime");
				value.endTime = rs.getString("endTime");
				value.control = rs.getInt("control");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return value;
	}

	/**
	 * 取得银行信息
	 * 
	 * @param bankID
	 *            银行代码
	 * @return BankValue
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public BankValue getBank(String bankID) throws SQLException, ClassNotFoundException {
		log("===>>>取得银行信息   getBank  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		BankValue value = null;
		try {
			conn = getConnection();

			sql = "select * from F_B_banks where bankid='" + bankID + "'";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new BankValue();
				value.adapterClassname = rs.getString("adapterClassname");
				value.bankID = rs.getString("bankID");
				value.bankName = rs.getString("bankName");
				value.maxPerTransCount = rs.getInt("maxPerTransCount");
				value.maxPerTransMoney = rs.getDouble("maxPerTransMoney");
				value.maxAuditMoney = rs.getDouble("maxAuditMoney");
				value.validFlag = rs.getInt("validFlag");
				value.maxPerSglTransMoney = rs.getDouble("maxPerSglTransMoney");
				value.beginTime = rs.getString("beginTime");
				value.endTime = rs.getString("endTime");
				value.control = rs.getInt("control");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return value;
	}

	/**
	 * 取得银行信息列表
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库联接
	 * @return Vector 每一项为BankValue
	 * @throws SQLException
	 */
	public Vector<BankValue> getBankList(String filter, Connection conn) throws SQLException {
		log("===>>>取得银行信息列表   getBankList  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		BankValue value = null;
		Vector<BankValue> list = new Vector<BankValue>();
		try {
			sql = "select * from F_B_banks " + filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new BankValue();
				value.adapterClassname = rs.getString("adapterClassname");
				value.bankID = rs.getString("bankID");
				value.bankName = rs.getString("bankName");
				value.maxPerTransCount = rs.getInt("maxPerTransCount");
				value.maxPerTransMoney = rs.getDouble("maxPerTransMoney");
				value.maxAuditMoney = rs.getDouble("maxAuditMoney");
				value.validFlag = rs.getInt("validFlag");
				value.maxPerSglTransMoney = rs.getDouble("maxPerSglTransMoney");
				value.beginTime = rs.getString("beginTime");
				value.endTime = rs.getString("endTime");
				value.control = rs.getInt("control");

				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return list;
	}

	/**
	 * 取得银行信息列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每一项为BankValue
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public Vector<BankValue> getBankList(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>取得银行信息列表   getBankList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		BankValue value = null;
		Vector<BankValue> list = new Vector<BankValue>();
		try {
			conn = getConnection();

			sql = "select * from F_B_banks " + filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new BankValue();
				value.adapterClassname = rs.getString("adapterClassname");
				value.bankID = rs.getString("bankID");
				value.bankName = rs.getString("bankName");
				value.maxPerTransCount = rs.getInt("maxPerTransCount");
				value.maxPerTransMoney = rs.getDouble("maxPerTransMoney");
				value.maxAuditMoney = rs.getDouble("maxAuditMoney");
				value.validFlag = rs.getInt("validFlag");
				value.maxPerSglTransMoney = rs.getDouble("maxPerSglTransMoney");
				value.beginTime = rs.getString("beginTime");
				value.endTime = rs.getString("endTime");
				value.control = rs.getInt("control");

				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return list;
	}

	/** 根据交易商Id查询交易商信息 */
	public Firm getMFirmByFirmId(String firmId) throws SQLException, ClassNotFoundException {
		log("===>>>查询市场交易商信息  " + new java.util.Date());
		Firm firm = null;
		String sql = "select * from M_Firm where firmId='" + firmId + "'";
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if (rs.next()) {
				firm = new Firm();
				firm.firmId = rs.getString("FIRMID");
				firm.name = rs.getString("NAME");
				firm.fullName = rs.getString("FULLNAME");
				firm.type = rs.getInt("TYPE");
				firm.bank = rs.getString("BANK");
				firm.status = rs.getString("STATUS");
				firm.bankAccount = rs.getString("BANKACCOUNT");
				firm.address = rs.getString("ADDRESS");
				firm.contactMan = rs.getString("CONTACTMAN");
				firm.phone = rs.getString("PHONE");
				firm.fax = rs.getString("FAX");
				firm.postCode = rs.getString("POSTCODE");
				firm.email = rs.getString("EMAIL");
				firm.expiryDate = rs.getDate("EXPIRYDATE");
				// firm.note=rs.getString("NOTE");
				firm.zoneCode = rs.getString("ZONECODE");
				firm.industryCode = rs.getString("INDUSTRYCODE");
				firm.extendTata = rs.getString("EXTENDDATA");
				firm.createTime = rs.getDate("CREATETIME");
				firm.modifyTime = rs.getDate("MODIFYTIME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return firm;
	}

	/**
	 * 增加交易商
	 * 
	 * @param val
	 *            交易商对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public int addFirm(FirmValue val, Connection conn) throws SQLException {
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "insert into F_B_FirmUser(firmid, name, maxpertransmoney, maxpertranscount, "
					+ " status, registerdate, logoutdate,maxPerSglTransMoney,maxAuditMoney,password) " + "values(?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?)";

			state = conn.prepareStatement(sql);
			state.setString(1, val.firmID);
			state.setString(2, val.name);
			state.setDouble(3, val.maxPerTransMoney);
			state.setInt(4, val.maxPerTransCount);
			state.setInt(5, val.status);
			state.setTimestamp(6, val.logoutDate);
			state.setDouble(7, val.maxPerSglTransMoney);
			state.setDouble(8, val.maxAuditMoney);
			state.setString(9, Encryption.encryption(val.firmID, val.password, null));

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 增加交易商
	 * 
	 * @param val
	 *            交易商对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public int addFirm(FirmValue val) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			conn = getConnection();

			sql = "insert into F_B_FirmUser" + "(firmid, name, maxpertransmoney, maxpertranscount,  "
					+ " status, registerdate, logoutdate, maxPerSglTransMoney, maxAuditMoney, password) "
					+ "values(?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?)";

			state = conn.prepareStatement(sql);
			state.setString(1, val.firmID);
			state.setString(2, val.name);
			state.setDouble(3, val.maxPerTransMoney);
			state.setInt(4, val.maxPerTransCount);
			state.setInt(5, val.status);
			state.setTimestamp(6, val.logoutDate);
			state.setDouble(7, val.maxPerSglTransMoney);
			state.setDouble(8, val.maxAuditMoney);
			state.setString(9, Encryption.encryption(val.firmID, val.password, null));

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	/**
	 * 删除交易商
	 * 
	 * @param firmID
	 *            交易商代码
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public int delFirm(String firmID) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			conn = getConnection();

			sql = "delete from F_B_FirmUser where firmid='" + firmID + "'";

			state = conn.prepareStatement(sql);

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	/**
	 * 修改交易商
	 * 
	 * @param val
	 *            交易商对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public int modFirm(FirmValue val, Connection conn) throws SQLException {
		log("===>>>修改交易商   modFirm  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "update F_B_FirmUser  " + "set name = ?,maxpertransmoney = " + "?,maxpertranscount = ?,status = ?,"
					+ "logoutdate = ?,maxPerSglTransMoney=?,maxAuditMoney=?,password=? where firmid = '" + val.firmID + "'";

			state = conn.prepareStatement(sql);
			state.setString(1, val.name);
			state.setDouble(2, val.maxPerTransMoney);
			state.setInt(3, val.maxPerTransCount);
			state.setInt(4, val.status);
			state.setTimestamp(5, val.logoutDate);
			state.setDouble(6, val.maxPerSglTransMoney);
			state.setDouble(7, val.maxAuditMoney);
			state.setString(8, val.password);

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 修改交易商
	 * 
	 * @param val
	 *            交易商对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public int modFirm(FirmValue val) throws SQLException, ClassNotFoundException {
		log("===>>>修改交易商   modFirm  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			conn = getConnection();

			sql = "update F_B_FirmUser  " + "set name = ?,maxpertransmoney = " + "?,maxpertranscount = ?,status = ?,"
					+ "logoutdate = ?,maxPerSglTransMoney=?,maxAuditMoney=?,password=? where firmid = '" + val.firmID + "'";

			state = conn.prepareStatement(sql);
			state.setString(1, val.name);
			state.setDouble(2, val.maxPerTransMoney);
			state.setInt(3, val.maxPerTransCount);
			state.setInt(4, val.status);
			state.setTimestamp(5, val.logoutDate);
			state.setDouble(6, val.maxPerSglTransMoney);
			state.setDouble(7, val.maxAuditMoney);
			state.setString(8, val.password);

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	/**
	 * 取得交易商
	 * 
	 * @param firmID
	 *            交易商代码
	 * @param conn
	 *            数据库联接
	 * @return FirmValue
	 * @throws SQLException
	 */
	public FirmValue getFirm(String firmID, Connection conn) throws SQLException {
		log("===>>>取得交易商   getFirm  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FirmValue value = null;
		try {
			sql = "select * from F_B_FirmUser where firmid='" + firmID + "'";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new FirmValue();
				value.firmID = rs.getString("firmID");
				value.maxPerTransCount = rs.getInt("maxPerTransCount");
				value.maxPerTransMoney = rs.getDouble("maxPerTransMoney");
				value.maxAuditMoney = rs.getDouble("maxAuditMoney");
				value.name = rs.getString("name");
				value.status = rs.getInt("status");
				value.logoutDate = rs.getTimestamp("logoutDate");
				value.registerDate = rs.getTimestamp("registerDate");
				value.maxPerSglTransMoney = rs.getDouble("maxPerSglTransMoney");
				value.password = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return value;
	}

	/**
	 * 取得交易商
	 * 
	 * @param firmID
	 *            交易商代码
	 * @return FirmValue
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public FirmValue getFirm(String firmID) throws SQLException, ClassNotFoundException {
		log("===>>>取得交易商   getFirm  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FirmValue value = null;
		try {
			conn = getConnection();

			sql = "select * from F_B_FirmUser where firmid='" + firmID + "'";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new FirmValue();
				value.firmID = rs.getString("firmID");
				value.maxPerTransCount = rs.getInt("maxPerTransCount");
				value.maxPerTransMoney = rs.getDouble("maxPerTransMoney");
				value.maxAuditMoney = rs.getDouble("maxAuditMoney");
				value.name = rs.getString("name");
				value.status = rs.getInt("status");
				value.logoutDate = rs.getTimestamp("logoutDate");
				value.registerDate = rs.getTimestamp("registerDate");
				value.maxPerSglTransMoney = rs.getDouble("maxPerSglTransMoney");
				value.password = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return value;
	}

	/**
	 * 取得交易商信息列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每一项为FirmValue
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public Vector<FirmValue> getFirmList(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>取得交易商信息列表   getFirmList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FirmValue value = null;
		Vector<FirmValue> list = new Vector<FirmValue>();
		try {
			conn = getConnection();

			sql = "select * from F_B_FirmUser " + filter;

			// System.out.println("----------sql---------:"+sql);

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new FirmValue();
				value.firmID = rs.getString("firmID");
				value.maxPerTransCount = rs.getInt("maxPerTransCount");
				value.maxPerTransMoney = rs.getDouble("maxPerTransMoney");
				value.maxAuditMoney = rs.getDouble("maxAuditMoney");
				value.name = rs.getString("name");
				value.status = rs.getInt("status");
				value.logoutDate = rs.getTimestamp("logoutDate");
				value.registerDate = rs.getTimestamp("registerDate");
				value.maxPerSglTransMoney = rs.getDouble("maxPerSglTransMoney");
				value.password = rs.getString("password");
				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return list;
	}

	/**
	 * 添加对账信息
	 * 
	 * @param val
	 *            对账信息
	 * @param conn
	 *            数据库联接
	 * @return long 对账信息流水号,返回<0的值表示添加失败
	 * @throws SQLException
	 */
	public void addMoneyInfo(MoneyInfoValue val, Connection conn) throws SQLException {
		log("===>>>添加对账信息   addMoneyInfo  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1;
		try {
			sql = "select seq_F_B_bankCompareInfo.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
		} catch (SQLException e) {
			this.log("取得id失败");
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		try {
			sql = "insert into F_B_BANKCOMPAREINFO(id,funid, firmid, account, type, money, comparedate, note, status,createtime,bankid) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?,sysdate,?)";
			state = conn.prepareStatement(sql);
			state.setLong(1, id);
			state.setString(2, val.id);
			state.setString(3, val.firmID == null ? val.firmID : val.firmID.trim());
			state.setString(4, val.account);
			state.setInt(5, val.type);
			state.setDouble(6, val.money);
			state.setDate(7, val.compareDate);
			state.setString(8, val.note);
			state.setInt(9, val.status);
			state.setString(10, val.bankID);

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
	}

	/**
	 * 添加对账信息
	 * 
	 * @param val
	 *            对账信息
	 * @return long 对账信息流水号,返回<0的值表示添加失败
	 * @throws SQLException
	 */
	public long addMoneyInfo(MoneyInfoValue val) throws SQLException {
		log("===>>>添加对账信息   addMoneyInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1;
		try {
			conn = getConnection();

			sql = "select seq_F_B_bankCompareInfo.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}

			sql = "insert into F_B_BANKCOMPAREINFO(id, funid, firmid, account, type, money, comparedate, note, status,createtime,bankid) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?,sysdate,?)";

			state = conn.prepareStatement(sql);
			state.setLong(1, id);
			state.setString(2, val.id);
			state.setString(3, val.firmID == null ? val.firmID : val.firmID.trim());
			state.setString(4, val.account);
			state.setInt(5, val.type);
			state.setDouble(6, val.money);
			state.setDate(7, val.compareDate);
			state.setString(8, val.note);
			state.setInt(9, val.status);
			state.setString(10, val.bankID);

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			id = -1;
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			id = -1;
		} finally {
			closeStatement(rs, state, conn);
		}
		return id;
	}

	/**
	 * 删除对账信息，不从数据库删除数据而是修改记录状态为已删除
	 * 
	 * @param id
	 *            对账信息流水号
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public int delMoneyInfo(String id, Connection conn) throws SQLException {
		log("===>>>删除对账信息，不从数据库删除数据而是修改记录状态为已删除   delMoneyInfo  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "update F_B_BANKCOMPAREINFO set status=1 where funid=" + id;

			state = conn.prepareStatement(sql);

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 取得对账信息列表
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库联接
	 * @return Vector 每项为 MoneyInfoValue
	 * @throws SQLException
	 */
	public Vector<MoneyInfoValue> getMoneyInfoList(String filter, Connection conn) throws SQLException {
		log("===>>>取得对账信息列表   getMoneyInfoList  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		MoneyInfoValue value = null;
		Vector<MoneyInfoValue> list = new Vector<MoneyInfoValue>();
		try {
			sql = "select * from F_B_BANKCOMPAREINFO " + filter;
			log("sql:" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new MoneyInfoValue();
				value.account = rs.getString("account");
				value.compareDate = rs.getDate("compareDate");
				value.createtime = rs.getTimestamp("createtime");
				value.firmID = rs.getString("firmID");
				value.id = rs.getString("funid");
				value.money = rs.getDouble("money");
				value.note = rs.getString("note");
				value.status = rs.getInt("status");
				value.type = rs.getInt("type");
				value.bankID = rs.getString("bankID");

				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return list;
	}

	/**
	 * 取得对账信息列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每项为 MoneyInfoValue
	 * @throws SQLException
	 */
	public Vector<MoneyInfoValue> getMoneyInfoList(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>取得对账信息列表   getMoneyInfoList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		MoneyInfoValue value = null;
		Vector<MoneyInfoValue> list = new Vector<MoneyInfoValue>();
		try {
			conn = getConnection();

			sql = "select * from F_B_BANKCOMPAREINFO " + filter;
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new MoneyInfoValue();
				value.account = rs.getString("account");
				value.compareDate = rs.getDate("compareDate");
				value.createtime = rs.getTimestamp("createtime");
				value.firmID = rs.getString("firmID");
				value.id = rs.getString("funid");
				value.money = rs.getDouble("money");
				value.note = rs.getString("note");
				value.status = rs.getInt("status");
				value.type = rs.getInt("type");
				value.bankID = rs.getString("bankID");

				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return list;
	}

	/**
	 * 取得对账信息列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每项为 MoneyInfoValue
	 * @throws SQLException
	 */
	public Vector<MoneyInfoValue> getUnionMoneyInfoList(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>取得对账信息列表   getUnionMoneyInfoList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		MoneyInfoValue value = null;
		Vector<MoneyInfoValue> list = new Vector<MoneyInfoValue>();
		try {
			conn = getConnection();

			sql = "select i.funid,c.id,i.bankid,i.type,i.money,c.money,i.account,i.firmID,i.comparedate  "
					+ "from F_B_capitalInfo c full join F_B_BANKCOMPAREINFO i on c.funid=i.funid " + filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new MoneyInfoValue();
				value.id = rs.getString(1);
				value.m_Id = rs.getLong(2);
				value.bankID = rs.getString(3);
				value.type = rs.getInt(4);
				value.money = rs.getDouble(5);
				value.m_money = rs.getDouble(6);
				value.account = rs.getString(7);
				value.firmID = rs.getString(8);
				value.compareDate = rs.getDate(9);

				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return list;
	}

	/**
	 * 修改交易商银行冻结资金 filter 查询条件， money 修改金额， conn 数据库连接对象
	 */
	public int modBankFrozenFuns(String filter, double money, Connection conn) throws SQLException {
		log("修改交易商银行接口冻结资金 " + filter + " " + money + new java.util.Date());
		PreparedStatement state = null;
		int result = 0;
		String sql = null;
		try {
			sql = "update F_B_firmidandaccount set frozenFuns=frozenFuns+" + money + " " + filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 添加帐号对应关系
	 * 
	 * @param CorrespondValue
	 *            对应关系对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public int addCorrespond(CorrespondValue val, Connection conn) throws SQLException {
		log("===>>>添加帐号对应关系   addCorrespond  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "insert into F_B_firmidandaccount(bankid, firmid, account, status"
					+ ",accountname, bankname, bankprovince, bankcity, mobile, email,account1,isopen,cardtype,card,accountName1,inMarketCode) "
					+ "values(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, val.bankID);
			state.setString(2, val.firmID);
			state.setString(3, val.account);
			state.setInt(4, val.status);
			state.setString(5, val.accountName);
			state.setString(6, val.bankName);
			state.setString(7, val.bankProvince);
			state.setString(8, val.bankCity);
			state.setString(9, val.mobile);
			state.setString(10, val.email);
			state.setString(11, val.account1);
			state.setInt(12, val.isOpen);
			state.setString(13, Tool.handleNull(val.cardType));
			state.setString(14, val.card);
			state.setString(15, val.accountName1);
			state.setString(16, val.inMarketCode);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 删除帐号对应关系
	 * 
	 * @param CorrespondValue
	 *            对应关系对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public int delCorrespond(CorrespondValue val, Connection conn) throws SQLException {
		log("===>>>删除帐号对应关系   delCorrespond  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "delete F_B_firmidandaccount where bankid = '" + val.bankID + "'  " + "and firmid = '" + val.firmID + "'  and account = '"
					+ val.account + "'";

			state = conn.prepareStatement(sql);

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 修改帐号对应关系
	 * 
	 * @param CorrespondValue
	 *            对应关系对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public int modCorrespond(CorrespondValue val, Connection conn) throws SQLException {
		log("===>>>修改帐号对应关系   modCorrespond conn  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "update f_b_firmidandaccount set status=" + val.status + ",accountname = '" + val.accountName + "'" + ",bankname = '" + val.bankName
					+ "',bankprovince = '" + val.bankProvince + "',bankcity = '" + val.bankCity + "'," + "mobile = '" + val.mobile + "',email = '"
					+ val.email + "',isopen=" + val.isOpen + ",cardtype = '" + val.cardType + "'" + ",card='" + val.card + "',account1='"
					+ val.account1 + "',account='" + val.account + "' ,accountName1 = '" + val.accountName1 + "'" + ",inMarketCode='"
					+ val.inMarketCode + "'" + " where bankid = '" + val.bankID + "'  " + "and firmid = '" + val.firmID + "'";
			state = conn.prepareStatement(sql);

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 修改帐号对应关系
	 * 
	 * @param CorrespondValue
	 *            对应关系对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public int modCorrespond(CorrespondValue val) throws SQLException, ClassNotFoundException {
		log("===>>>修改帐号对应关系   modCorrespond  " + new java.util.Date());
		Connection conn = null;
		int result = 0;
		try {
			conn = getConnection();
			result = modCorrespond(val, conn);
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, null, conn);
		}
		return result;
	}

	public int destroyAccount(CorrespondValue val, Connection conn) throws SQLException {
		log("注销账号对应关系   destroyAccount  时间：" + Tool.fmtTime(new java.util.Date()));
		log("参数：\nval:" + val.toString() + "\nconn[" + (conn == null ? "为空" : "不为空") + "]");
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "update f_b_firmidandaccount set firmID='D_'||firmID,bankID='D_'||bankID,deltime=sysdate where firmID=? and bankID=? and account=? ";
			state = conn.prepareStatement(sql);
			state.setString(1, val.firmID);
			state.setString(2, val.bankID);
			state.setString(3, val.account);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	public int destroyAccount(CorrespondValue val) throws SQLException, ClassNotFoundException {
		log("注销账号对应关系   destroyAccount  时间：" + Tool.fmtTime(new java.util.Date()));
		log("参数：\nval:" + val.toString() + "\n");
		Connection conn = null;
		int result = 0;
		try {
			conn = this.getConnection();
			result = destroyAccount(val, conn);
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(null, null, conn);
		}
		return result;
	}

	/** 获取交易商和银行绑定的条件语句 */
	public String FirmAccountFile(String bankId, String firmId, String cardType, String isOpen, String status) {
		String file = " where 1=1 ";
		if (bankId != null && bankId.trim().length() > 0) {
			file = file + "and bankId='" + bankId.trim() + "' ";
		}
		if (firmId != null && firmId.trim().length() > 0) {
			file = file + "and firmId='" + firmId.trim() + "' ";
		}
		if (cardType != null && cardType.trim().length() > 0) {
			if (cardType.trim().equals("1")) {
				file = file + "and cardType='1' ";
			} else {
				file = file + "and (cardType='8' or cardType='9') ";
			}
		}
		if (isOpen != null && isOpen.trim().length() > 0) {
			if (isOpen.trim().equals("1")) {
				file = file + "and isOpen=1 ";
			} else {
				file = file + "and isOpen=0 ";
			}
		}
		if (status != null && status.trim().length() > 0) {
			if (status.trim().equals("0")) {
				file = file + "and status=0 ";
			} else {
				file = file + "and status=1 ";
			}
		}
		file = file + "order by bankId,firmId ";
		return file;
	}

	/**
	 * 查询交易商和银行绑定的个数 bankId 银行代码， firmId 交易商代码， cardType交易商证件类型(1是个人,8是企业)，
	 * isOpen 是否签约(1是已签约,0是未签约)， status 是否可用(0是可用,1是不可用)，
	 */
	public int countFirmAccount(String bankId, String firmId, String cardType, String isOpen, String status) {
		return countFirmAccount(FirmAccountFile(bankId, firmId, cardType, isOpen, status));
	}

	/** 查询交易商和银行绑定的个数 */
	public int countFirmAccount(String file) {
		String sql = "select nvl(count(*),0) countnum from F_B_firmidandaccount " + file;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		int result = 0;
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if (rs.next()) {
				result = rs.getInt("countnum");
			}
		} catch (SQLException e) {
			result = -1;
			System.out.println("查询满足条件的交易商银行账号信息个数，数据库异常");
		} catch (ClassNotFoundException e) {
			result = -2;
			System.out.println("查询满足条件的交易商银行账号信息个数，找不到类异常");
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	/**
	 * 添加帐号对应关系
	 * 
	 * @param CorrespondValue
	 *            对应关系对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public int addCorrespond(CorrespondValue val) throws SQLException, ClassNotFoundException {
		log("===>>>添加帐号对应关系   addCorrespond  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			conn = getConnection();

			sql = "insert into F_B_firmidandaccount(bankid, firmid, account, status"
					+ ",accountname, bankname, bankprovince, bankcity, mobile, email,account1,isopen,cardtype,card,accountName1,inMarketCode) values(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";

			state = conn.prepareStatement(sql);
			state.setString(1, val.bankID);
			state.setString(2, val.firmID);
			state.setString(3, val.account);
			state.setInt(4, val.status);
			state.setString(5, val.accountName);
			state.setString(6, val.bankName);
			state.setString(7, val.bankProvince);
			state.setString(8, val.bankCity);
			state.setString(9, val.mobile);
			state.setString(10, val.email);
			state.setString(11, val.account1);
			state.setInt(12, val.isOpen);
			state.setString(13, Tool.handleNull(val.cardType));
			state.setString(14, val.card);
			state.setString(15, val.accountName1);
			state.setString(16, val.inMarketCode);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	/**
	 * 交易商签约
	 */
	public int openCorrespond(CorrespondValue val) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			conn = this.getConnection();
			sql = "update f_b_firmidandaccount set status=" + val.status + ",accountname = '" + val.accountName + "'" + ",bankname = '" + val.bankName
					+ "',bankprovince = '" + val.bankProvince + "',bankcity = '" + val.bankCity + "'" + ",isopen=" + val.isOpen + ",cardtype = '"
					+ val.cardType + "',openTime=sysdate" + ",card='" + val.card + "',account1='" + val.account1 + "',account='" + val.account
					+ "' ,accountName1 = '" + val.accountName1 + "'" + ",inMarketCode='" + val.inMarketCode + "'" + " where bankid = '" + val.bankID
					+ "'  " + "and firmid = '" + val.firmID + "'";
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	/**
	 * 删除帐号对应关系
	 * 
	 * @param CorrespondValue
	 *            对应关系对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public int delCorrespond(CorrespondValue val) throws SQLException, ClassNotFoundException {
		log("===>>>删除帐号对应关系   delCorrespond  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			conn = getConnection();

			sql = "delete F_B_firmidandaccount where bankid = '" + val.bankID + "'  " + "and firmid = '" + val.firmID + "'  and account = '"
					+ val.account + "'";

			state = conn.prepareStatement(sql);

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	/**
	 * 交易商解约
	 */
	public int closeCorrespond(String bankID, String firmID, String card, Connection conn) throws SQLException {
		log("交易商解约  modBankFrozenFuns bankID[" + bankID + "]firmID[" + firmID + "]card[" + card + "]conn[" + (conn == null ? "为空" : "不为空") + "]");
		PreparedStatement state = null;
		int result = 0;
		String sql = null;
		String filter = " where bankID='" + bankID + "' and firmID='" + firmID + "' ";
		String filter1 = " where bankID='D_" + bankID + "' and firmID='D_" + firmID + "' ";
		if (card != null && card.trim().length() > 0) {
			filter += " and card='" + card + "'";
		}
		try {
			sql = "delete from f_b_firmidandaccount " + filter1;
			log("sql:" + sql);
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
			sql = "update f_b_firmidandaccount set firmID='D_'||firmID,bankID='D_'||bankID,deltime=sysdate " + filter;
			log("sql:" + sql);
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 查询帐号对应关系列表
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库联接
	 * @return Vector 每项为CorrespondValue
	 * @throws SQLException
	 */
	public Vector<CorrespondValue> getCorrespondList(String filter, Connection conn) throws SQLException {
		log("===>>>查询帐号对应关系列表   getCorrespondList  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CorrespondValue value = null;
		Vector<CorrespondValue> list = new Vector<CorrespondValue>();
		try {
			sql = "select * from F_B_firmidandaccount " + filter;
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new CorrespondValue();
				value.account = rs.getString("account");
				value.bankID = rs.getString("bankID");
				value.firmID = rs.getString("firmID");
				value.status = rs.getInt("status");
				value.accountName = rs.getString("accountName");
				value.bankCity = rs.getString("bankCity");
				value.bankName = rs.getString("bankName");
				value.bankProvince = rs.getString("bankProvince");
				value.email = rs.getString("email");
				value.account1 = rs.getString("account1");
				value.mobile = rs.getString("mobile");
				value.isOpen = rs.getInt("IsOpen");
				value.cardType = rs.getString("CardType");
				value.card = rs.getString("Card");
				value.frozenFuns = rs.getDouble("frozenFuns");
				value.accountName1 = rs.getString("accountName1");
				value.inMarketCode = rs.getString("inMarketCode");
				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return list;
	}

	/**
	 * 查询帐号对应关系列表 bankId 银行代码， firmId 交易商代码， cardType交易商证件类型(1是个人,8是企业)， isOpen
	 * 是否签约(1是已签约,0是未签约)， status 是否可用(0是可用,1是不可用)，
	 */
	public Vector<CorrespondValue> getCorrespondList(String bankId, String firmId, String cardType, String isOpen, String status) {
		try {
			return getCorrespondList(FirmAccountFile(bankId, firmId, cardType, isOpen, status));
		} catch (SQLException e) {
			System.out.println("查询交易商和银行的绑定关系，数据库异常" + e);
		} catch (ClassNotFoundException e) {
			System.out.println("查询交易商和银行的绑定关系，找不到类异常" + e);
		}
		return new Vector<CorrespondValue>();
	}

	/**
	 * 查询帐号对应关系列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每项为CorrespondValue
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public Vector<CorrespondValue> getCorrespondList(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>查询帐号对应关系列表   getCorrespondList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CorrespondValue value = null;
		Vector<CorrespondValue> list = new Vector<CorrespondValue>();
		try {
			conn = getConnection();
			sql = "select * from F_B_firmidandaccount " + filter;
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new CorrespondValue();
				value.account = rs.getString("account");
				value.bankID = rs.getString("bankID");
				value.firmID = rs.getString("firmID");
				value.status = rs.getInt("status");
				value.accountName = rs.getString("accountName");
				value.bankCity = rs.getString("bankCity");
				value.bankName = rs.getString("bankName");
				value.bankProvince = rs.getString("bankProvince");
				value.email = rs.getString("email");
				value.mobile = rs.getString("mobile");
				value.account1 = rs.getString("account1");
				value.isOpen = rs.getInt("IsOpen");
				value.cardType = rs.getString("CardType");
				value.card = rs.getString("Card");
				value.frozenFuns = rs.getDouble("frozenFuns");
				value.accountName1 = rs.getString("accountName1");
				value.inMarketCode = rs.getString("inMarketCode");
				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return list;
	}

	/**
	 * 查询帐号对应关系
	 * 
	 * @param bankID
	 *            银行代码
	 * @param firmID
	 *            交易商代码
	 * @param account
	 *            交易商银行帐号
	 * @param conn
	 *            数据库联接
	 * @return CorrespondValue
	 * @throws SQLException
	 */
	public CorrespondValue getCorrespond(String bankID, String firmID, String account, Connection conn) throws SQLException {
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CorrespondValue value = null;
		try {
			sql = "select * from f_b_FirmidAndAccount where bankID='" + bankID + "' and firmID='" + firmID + "' and account='" + account
					+ "' and status=0 and isopen=1";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if (rs.next()) {
				value = new CorrespondValue();
				value.account = rs.getString("account");
				value.bankID = rs.getString("bankID");
				value.firmID = rs.getString("firmID");
				value.status = rs.getInt("status");
				value.accountName = rs.getString("accountName");
				value.bankCity = rs.getString("bankCity");
				value.bankName = rs.getString("bankName");
				value.bankProvince = rs.getString("bankProvince");
				value.email = rs.getString("email");
				value.mobile = rs.getString("mobile");
				value.account1 = rs.getString("account1");
				value.isOpen = rs.getInt("IsOpen");
				value.cardType = rs.getString("CardType");
				value.card = rs.getString("Card");
				value.frozenFuns = rs.getDouble("frozenFuns");
				value.accountName1 = rs.getString("accountName1");
				value.inMarketCode = rs.getString("inMarketCode");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return value;
	}

	/**
	 * 查询帐号对应关系
	 * 
	 * @param bankID
	 *            银行代码
	 * @param firmID
	 *            交易商代码
	 * @param account
	 *            交易商银行帐号
	 * @return CorrespondValue
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public CorrespondValue getCorrespond(String bankID, String firmID, String account) throws SQLException, ClassNotFoundException {
		log("===>>>查询帐号对应关系   getCorrespond  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CorrespondValue value = null;
		try {
			conn = getConnection();

			sql = "select * from F_B_firmidandaccount where bankID='" + bankID + "' and firmID='" + firmID + "' and account='" + account + "'";
			// sql =
			// "select t.*,(select b.bankname from f_b_banks b where b.bankid=t.bankid) headOffice from F_B_firmidandaccount t where
			// t.bankID='"+bankID+"' and t.firmID='"+firmID+"' and t.account='"+account+"' and t.status=0";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new CorrespondValue();
				value.account = rs.getString("account");
				value.account1 = rs.getString("account1");
				value.bankID = rs.getString("bankID");
				value.firmID = rs.getString("firmID");
				value.status = rs.getInt("status");
				value.accountName = rs.getString("accountName");
				value.bankCity = rs.getString("bankCity");
				value.bankName = rs.getString("bankName");
				value.bankProvince = rs.getString("bankProvince");
				value.email = rs.getString("email");
				value.mobile = rs.getString("mobile");
				value.isOpen = rs.getInt("IsOpen");
				value.cardType = rs.getString("CardType");
				value.card = rs.getString("Card");
				value.frozenFuns = rs.getDouble("frozenFuns");
				value.accountName1 = rs.getString("accountName1");
				value.inMarketCode = rs.getString("inMarketCode");
				value.opentime = rs.getDate("opentime");
				value.deltime = rs.getDate("deltime");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return value;
	}

	/**
	 * 查询交易商对应的银行帐号
	 * 
	 * @param firmID
	 *            交易商代码
	 * @return LIST
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public List<String> getFirmBankList(String firmID) throws SQLException, ClassNotFoundException {
		log("===>>>查询交易商对应的银行帐号   getFirmBankList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		List<String> bankidList = new ArrayList<String>();
		try {
			conn = getConnection();

			sql = " select bankid from f_b_firmidandaccount where firmid='" + firmID + "' ";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				bankidList.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return bankidList;
	}

	/**
	 * 查询表
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库联接
	 * @return Vector 每项为SubjectValue
	 * @throws SQLException
	 */
	public Vector<DicValue> getDicList(String filter, Connection conn) throws SQLException {
		log("===>>>查询表   getDicList  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		DicValue value = null;
		Vector<DicValue> list = new Vector<DicValue>();
		try {
			sql = "select * from F_B_dictionary " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new DicValue();
				value.bankID = rs.getString("bankID");
				value.id = rs.getLong("id");
				value.name = rs.getString("name");
				value.note = rs.getString("note");
				value.type = rs.getInt("type");
				value.value = rs.getString("value");
				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return list;
	}

	/**
	 * 查询表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每项为SubjectValue
	 * @throws SQLException
	 */
	public Vector<DicValue> getDicList(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>查询表   getDicList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		DicValue value = null;
		Vector<DicValue> list = new Vector<DicValue>();
		try {
			conn = getConnection();

			sql = "select * from F_B_dictionary " + filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new DicValue();
				value.bankID = rs.getString("bankID");
				value.id = rs.getLong("id");
				value.name = rs.getString("name");
				value.note = rs.getString("note");
				value.type = rs.getInt("type");
				value.value = rs.getString("value");

				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return list;
	}

	/**
	 * 查询资金划转对象列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每项为TransMnyObjValue
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public Vector<TransMnyObjValue> getTransMnyObjList(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>查询资金划转对象列表   getTransMnyObjList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		TransMnyObjValue value = null;
		Vector<TransMnyObjValue> list = new Vector<TransMnyObjValue>();
		try {
			conn = getConnection();

			sql = "select * from F_B_transMoneyObj " + filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new TransMnyObjValue();
				value.id = rs.getInt("id");
				value.className = rs.getString("classname");
				value.note = rs.getString("note");

				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return list;
	}

	/**
	 * 查询资金划转对象
	 * 
	 * @param id
	 *            业务序号
	 * @return TransMnyObjValue
	 * @throws SQLException
	 */
	public TransMnyObjValue getTransMnyObj(int id) throws SQLException, ClassNotFoundException {
		log("===>>>查询资金划转对象   getTransMnyObj  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		TransMnyObjValue value = null;
		try {
			conn = getConnection();

			sql = "select * from F_B_transMoneyObj where id=" + id;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new TransMnyObjValue();
				value.id = rs.getInt("id");
				value.className = rs.getString("classname");
				value.note = rs.getString("note");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return value;
	}

	public Vector<MoneyInfoValue> qureyBankCompareInfo(String date) throws SQLException, ClassNotFoundException {

		log("===>>>----  qureyBankCompareInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		Vector<MoneyInfoValue> ve = new Vector<MoneyInfoValue>();
		try {
			conn = getConnection();

			sql = "select * from F_B_BANKCOMPAREINFO where trunc(COMPAREDATE)=to_date('" + date + "','yyyy-MM-dd')";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				MoneyInfoValue value = new MoneyInfoValue();
				value.id = rs.getString("FUNID");
				value.firmID = rs.getString("FIRMID");
				value.bankID = rs.getString("BANKID");
				value.account = rs.getString("ACCOUNT");
				value.type = rs.getInt("TYPE");
				value.money = rs.getDouble("MONEY");
				value.compareDate = rs.getDate("COMPAREDate");
				value.createtime = rs.getTimestamp("CREATETIME");
				value.status = rs.getInt("STATUS");
				value.note = rs.getString("NOTE");
				ve.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return ve;
	}

	public Vector<CompareResult> compareResultInfo(String bankID, String date) throws SQLException, ClassNotFoundException {

		log("===>>>----  compareResultInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		Vector<CompareResult> ve = new Vector<CompareResult>();
		String fundflow = " and exists (select f.contractno from f_h_fundflow f where f.contractno = c.actionid and f.b_date = to_date('" + date
				+ "', 'yyyy-MM-dd')) ";
		try {
			conn = getConnection();
			// 账面不平的数据
			if ("true".equals(Tool.getConfig("CompareWithFundflow"))) {
				sql = "select c.firmID || '/' || b.firmID as firmID, c.bankID as bankID, b.account as account,"
						+ " c.funID as id , c.actionID as m_Id , " + " b.type as type, c.type as m_type, b.money as money ,"
						+ "c.money as m_money, b.comparedate as comparedate " + " from F_B_BANKCOMPAREINFO b,F_B_capitalInfo c "
						+ " where c.status='0' and (c.type='0' or c.type='1') and c.bankid='" + bankID + "' and b.bankid='" + bankID + "' " +
						// " and trunc(b.COMPAREDATE)=to_date('"+ date
						// +"','yyyy-MM-dd') and trunc(c.bankTime)=to_date('"+ date
						// +"','yyyy-MM-dd')" +20110908 lipj
						// 修改对账条件，市场流水表使用createdate字段比较
				" and trunc(b.COMPAREDATE)=to_date('" + date + "','yyyy-MM-dd') "/* and c.createdate='" + date + "' */
						+ " and b.funid=c.funid  and (b.money<>c.money or (trim(b.firmID)<>trim(c.firmID) and b.firmID!='GSYTfirm')) " + fundflow;
			} else {
				sql = "select c.firmID || '/' || b.firmID as firmID, c.bankID as bankID, b.account as account, c.funID as id , c.actionID as m_Id , "
						+ " b.type as type, c.type as m_type, b.money as money ,c.money as m_money, b.comparedate as comparedate "
						+ " from F_B_BANKCOMPAREINFO b,F_B_capitalInfo c " + " where c.status='0' and (c.type='0' or c.type='1') and c.bankid='"
						+ bankID + "' and b.bankid='" + bankID + "' " +
						// " and trunc(b.COMPAREDATE)=to_date('"+ date
						// +"','yyyy-MM-dd') and trunc(c.bankTime)=to_date('"+ date
						// +"','yyyy-MM-dd')" +20110908 lipj
						// 修改对账条件，市场流水表使用createdate字段比较
				" and trunc(b.COMPAREDATE)=to_date('" + date + "','yyyy-MM-dd') and c.createdate='" + date + "'"
						+ " and b.funid=c.funid  and (b.money<>c.money or (trim(b.firmID)<>trim(c.firmID) and b.firmID!='GSYTfirm'))";

			}

			System.out.println("账面不平的数据>" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				CompareResult value = new CompareResult();
				value.errorType = 0;
				value.firmID = rs.getString("firmID");
				value.bankID = rs.getString("bankID");
				value.account = rs.getString("account");
				value.id = rs.getString("id");
				value.m_Id = rs.getLong("m_Id");
				value.type = rs.getInt("TYPE");
				value.m_type = rs.getInt("m_type");
				value.money = rs.getDouble("MONEY");
				value.m_money = rs.getDouble("m_money");
				value.compareDate = rs.getDate("compareDate");
				ve.add(value);
			}

			// 出入金类型不匹配的数据
			if ("true".equals(Tool.getConfig("CompareWithFundflow"))) {
				sql = "select c.firmID as firmID, c.bankID as bankID, b.account as account, c.funID as id, c.actionID as m_Id,  "
						+ " b.type as type ,c.type as m_type, b.money as money ,c.money as m_money ,b.comparedate as comparedate "
						+ " from F_B_BANKCOMPAREINFO b,F_B_capitalInfo c " + " where (c.status='0' and (c.type='0' or c.type='1') and c.bankid='"
						+ bankID + "' and b.bankid='" + bankID + "' " +
						// " and trunc(b.COMPAREDATE)=to_date('"+ date
						// +"','yyyy-MM-dd') and trunc(c.bankTime)=to_date('"+ date
						// +"','yyyy-MM-dd')" +20110908 lipj
						// 修改对账条件，市场流水表使用createdate字段比较
				" and trunc(b.COMPAREDATE)=to_date('" + date + "','yyyy-MM-dd') "/* and c.createdate='" + date + "'" + " */
						+ " and b.funid=c.funid  and b.type<>c.type " + fundflow + ")";
			} else {
				sql = "select c.firmID as firmID, c.bankID as bankID, b.account as account, c.funID as id, c.actionID as m_Id,  "
						+ " b.type as type ,c.type as m_type, b.money as money ,c.money as m_money ,b.comparedate as comparedate "
						+ " from F_B_BANKCOMPAREINFO b,F_B_capitalInfo c " + " where (c.status='0' and (c.type='0' or c.type='1') and c.bankid='"
						+ bankID + "' and b.bankid='" + bankID + "' " +
						// " and trunc(b.COMPAREDATE)=to_date('"+ date
						// +"','yyyy-MM-dd') and trunc(c.bankTime)=to_date('"+ date
						// +"','yyyy-MM-dd')" +20110908 lipj
						// 修改对账条件，市场流水表使用createdate字段比较
				" and trunc(b.COMPAREDATE)=to_date('" + date + "','yyyy-MM-dd') and c.createdate='" + date + "'"
						+ " and b.funid=c.funid  and b.type<>c.type )";

			}
			System.out.println("出入金类型不匹配的数据>" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				CompareResult value = new CompareResult();
				value.errorType = 1;
				value.firmID = rs.getString("firmID");
				value.bankID = rs.getString("bankID");
				value.account = rs.getString("account");
				value.id = rs.getString("id");
				value.m_Id = rs.getLong("m_Id");
				value.type = rs.getInt("TYPE");
				value.m_type = rs.getInt("m_type");
				value.money = rs.getDouble("MONEY");
				value.m_money = rs.getDouble("m_money");
				value.compareDate = rs.getDate("compareDate");
				ve.add(value);
			}

			// 银行有市场没有的数据
			// sql="select * from F_B_BANKCOMPAREINFO b where trunc(b.comparedate)=to_date('"+date+"','yyyy-mm-dd') and b.bankID='"+bankID+"' "
			// +
			// " and b.funid<>'-1' and b.funid not in(select funid from F_B_capitalInfo c where c.status='0' and (c.type='0' or c.type='1') "
			// +
			// " and c.bankID='"+bankID+"' and trunc(c.bankTime)=to_date('"+date+"','yyyy-mm-dd') and funid is not null) ";

			if ("true".equals(Tool.getConfig("CompareWithFundflow"))) {
				sql = "select * from F_B_BANKCOMPAREINFO b where trunc(b.comparedate)=to_date('" + date + "','yyyy-mm-dd') and b.bankID='" + bankID
						+ "' "
						+ " and b.funid<>'-1' and not exists(select funid from F_B_capitalInfo c ,f_h_fundflow f where b.funid=c.funid  and  c.actionid =f.contractno  and f.b_date = to_date('"
						+ date + "', 'yyyy-MM-dd') and c.status='0' and (c.type='0' or c.type='1') " +
						// " and c.bankID='"+bankID+"' and trunc(c.bankTime)=to_date('"+date+"','yyyy-mm-dd') and funid is not null) ";//20110908
						// lipj 修改对账条件，市场流水表使用createdate字段比较
				" and c.bankID='" + bankID + "' " + /* and c.createdate='" + date + "' */" and funid is not null ) ";
			} else {
				sql = "select * from F_B_BANKCOMPAREINFO b where trunc(b.comparedate)=to_date('" + date + "','yyyy-mm-dd') and b.bankID='" + bankID
						+ "' "
						+ " and b.funid<>'-1' and not exists(select funid from F_B_capitalInfo c where b.funid=c.funid and c.status='0' and (c.type='0' or c.type='1') "
						+
						// " and c.bankID='"+bankID+"' and trunc(c.bankTime)=to_date('"+date+"','yyyy-mm-dd') and funid is not null) ";//20110908
						// lipj 修改对账条件，市场流水表使用createdate字段比较
				" and c.bankID='" + bankID + "' and c.createdate='" + date + "' and funid is not null) ";
			}
			System.out.println("银行有市场没有的数据>" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				CompareResult value = new CompareResult();
				value.errorType = 2;
				value.firmID = rs.getString("firmID");
				value.bankID = rs.getString("bankID");
				value.account = rs.getString("account");
				value.id = rs.getString("funID");
				value.m_Id = 0;
				value.type = rs.getInt("TYPE");
				value.m_type = 0;
				value.money = rs.getDouble("MONEY");
				value.m_money = 0;
				value.compareDate = rs.getDate("compareDate");
				ve.add(value);
			}

			// 市场有银行没有的数据
			// sql="select * from F_B_capitalInfo c where trunc(c.bankTime)=to_date('"+date+"','yyyy-mm-dd') and c.bankID='"+bankID+"' "+
			// " and c.status='0' and (c.type='0' or c.type='1') and c.funid not in "
			// +
			// " (select funid from F_B_BANKCOMPAREINFO b where b.bankid='"+bankID+"' and trunc(b.comparedate)=to_date('"+date+"','yyyy-mm-dd')) ";

			// sql="select * from F_B_capitalInfo c where trunc(c.bankTime)=to_date('"+date+"','yyyy-mm-dd') and c.bankID='"+bankID+"' "+//20110908
			// lipj 修改对账条件，市场流水表使用createdate字段比较
			if ("true".equals(Tool.getConfig("CompareWithFundflow"))) {
				sql = "select * from F_B_capitalInfo c where " + /* c.createdate='" + date + "' and */" c.bankID='" + bankID + "' "
						+ " and c.status='0' and (c.type='0' or c.type='1') and not exists "
						+ " (select funid from F_B_BANKCOMPAREINFO b where b.funid=c.funid and b.bankid='" + bankID
						+ "' and trunc(b.comparedate)=to_date('" + date + "','yyyy-mm-dd'))  " + fundflow;
			} else {
				sql = "select * from F_B_capitalInfo c where c.createdate='" + date + "' and c.bankID='" + bankID + "' "
						+ " and c.status='0' and (c.type='0' or c.type='1') and not exists "
						+ " (select funid from F_B_BANKCOMPAREINFO b where b.funid=c.funid and b.bankid='" + bankID
						+ "' and trunc(b.comparedate)=to_date('" + date + "','yyyy-mm-dd'))  ";

			}
			System.out.println("市场有银行没有的数据>" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				CompareResult value = new CompareResult();
				value.errorType = 3;
				value.firmID = rs.getString("firmID");
				value.bankID = rs.getString("bankID");
				value.account = "";
				value.id = rs.getString("funID");
				value.m_Id = rs.getLong("actionID");
				value.type = 0;
				value.m_type = rs.getInt("TYPE");
				value.money = 0;
				value.m_money = rs.getDouble("MONEY");
				value.compareDate = rs.getDate("bankTime");
				ve.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return ve;
	}

	/**
	 * 查询交易商当天出入金求和数据
	 * 
	 * @param bankID
	 *            银行编号
	 * @param firmIDs
	 *            交易商代码集
	 * @param date
	 *            转账日期
	 * @return Vector<CapitalCompare>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Vector<CapitalCompare> sumResultInfo(String bankID, String[] firmIDs, java.util.Date date) throws SQLException, ClassNotFoundException {
		log("查询交易商当天出入金求和数据，bankID[" + bankID + "]firmIDs[" + firmIDs + "]date[" + date + "]");
		Vector<CapitalCompare> result = new Vector<CapitalCompare>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = "";
		String filter2 = null;
		String filter3 = null;
		if (bankID != null && bankID.trim().length() > 0) {
			filter += " and bankID='" + bankID.trim() + "' ";
		}
		if (firmIDs != null && firmIDs.length > 0) {
			String firms = "";
			for (String firmID : firmIDs) {
				if (firmID != null && firmID.trim().length() > 0) {
					firms += "'" + firmID.trim() + "',";
				}
			}
			if (firms != null && firms.trim().length() > 0) {
				filter += " and firmID in (" + firms.substring(0, firms.lastIndexOf(',')) + ") ";
			}
		}
		filter2 = filter;
		filter3 = filter;
		if (date != null) {
			filter2 += " and trunc(banktime)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd') ";
			filter3 += " and trunc(comparedate)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd') ";
		}
		try {
			sql = "select nvl(a.inmoney,0) minmoney,nvl(a.outmoney,0) moutmoney,nvl(b.inmoney,0) binmoney,nvl(b.outmoney,0) boutmoney,c.firmid firmid,case when a.tradedate is null then b.tradedate else a.tradedate end tradedate,case when a.bankid is null then b.bankid else a.bankid end bankid from "
					+ "(select nvl(sum(case when type=0 then money else 0 end),0) inmoney,nvl(sum(case when type=1 then money else 0 end),0) outmoney,bankid,trim(firmid) firmid,trunc(banktime) tradedate from f_b_capitalinfo t where banktime is not null and status=0 "
					+ filter2 + " group by trunc(banktime),bankid,firmid order by trunc(banktime) desc,bankid,firmid) a, "
					+ "(select nvl(sum(case when type=0 then money else 0 end),0) inmoney,nvl(sum(case when type=1 then money else 0 end),0) outmoney,trunc(comparedate) tradedate,trim(firmid) firmid,bankid from f_b_bankcompareinfo t where status=0 "
					+ filter3 + " group by trunc(comparedate),bankid,firmid order by trunc(comparedate) desc,bankid,firmid) b, "
					+ "(select distinct firmid,tradedate from "
					+ "(select distinct trim(firmid) firmid,trunc(banktime) tradedate from f_b_capitalinfo where 1=1 and status=0 " + filter2
					+ "union  " + "select distinct trim(firmid) firmid,trunc(comparedate) tradedate from f_b_bankcompareinfo where 1=1 and status=0 "
					+ filter3 + ")where firmid is not null " + "order by firmid) c "
					+ "where c.firmid=a.firmid(+) and c.firmid=b.firmid(+) and c.tradedate=a.tradedate(+) and c.tradedate=b.tradedate(+) order by case when a.tradedate is null then b.tradedate else a.tradedate end desc,firmid";
			log("sql:" + sql);
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				CapitalCompare value = new CapitalCompare();
				value.firmID = rs.getString("firmid");
				value.bankID = rs.getString("bankid");
				value.bInmoney = rs.getDouble("binmoney");
				value.bOutmoney = rs.getDouble("boutmoney");
				value.mInmoney = rs.getDouble("minmoney");
				value.mOutmoney = rs.getDouble("moutmoney");
				value.tradeDate = rs.getDate("tradedate");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	/**
	 * 取得单个资金流水记录
	 * 
	 * @param filter
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public CapitalValue getCapitalInfo(String filter) throws SQLException, ClassNotFoundException {

		log("===>>>取得单个资金流水记录  getCapitalInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalValue value = null;
		try {
			conn = getConnection();

			sql = "select * from F_B_capitalInfo " + filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new CapitalValue();
				value.actionID = rs.getLong("actionID");
				value.bankID = rs.getString("bankID");
				value.bankTime = rs.getTimestamp("bankTime");
				value.createtime = rs.getTimestamp("createtime");
				value.creditID = rs.getString("creditID");
				value.debitID = rs.getString("debitID");
				value.firmID = rs.getString("firmID");
				value.funID = rs.getString("funID");
				value.iD = rs.getLong("iD");
				value.money = rs.getDouble("money");
				value.note = rs.getString("note");
				value.oprcode = rs.getString("operator");
				value.status = rs.getInt("status");
				value.type = rs.getInt("type");
				value.bankName = rs.getString("bankName");
				value.account = rs.getString("account");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return value;
	}

	public long updateOutMoneyCapitalInfo(CapitalValue val, Connection conn) throws SQLException, ClassNotFoundException {

		log("===>>>更新出金资金流水  updateOutMoneyCapitalInfo  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			int n = 1;
			String funidf = "";
			if (val.funID != null && val.funID.trim().length() > 0) {
				funidf = ",funid2=?";
			}
			sql = "update F_B_capitalInfo set status=?,FUNID=?,bankTime=?" + funidf + " where id=?";

			state = conn.prepareStatement(sql);
			state.setInt(n++, val.status);
			state.setString(n++, val.funID == null ? "" : val.funID.trim());
			state.setTimestamp(n++, val.bankTime);
			if (funidf != null && funidf.trim().length() > 0) {
				state.setString(n++, val.funID);
			}
			state.setLong(n++, val.iD);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 添加收费标准
	 * 
	 * @param CFeeInfoVO
	 *            收费标准对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public int addFeeInfo(FeeInfoVO feeInfoVO, Connection conn) throws SQLException, ClassNotFoundException {
		log("===>>>添加收费标准  addFeeInfo  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		int result = 0;
		long id = -1;

		try {
			sql = "select seq_F_B_feeInfo.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}

			sql = "insert into  F_B_feeInfo(id, upLimit, downLimit, tMode, rate, maxRateValue,minRateValue,type, " + "createTime,userID) "
					+ "values(?, ?, ?, ?, ?, ?,?,?," + "sysdate, ?)";

			state = conn.prepareStatement(sql);
			state.setLong(1, id);
			state.setInt(2, feeInfoVO.upLimit);
			state.setInt(3, feeInfoVO.downLimit);
			state.setInt(4, feeInfoVO.tMode);
			state.setDouble(5, feeInfoVO.rate);
			state.setDouble(6, feeInfoVO.maxRateValue);
			state.setDouble(7, feeInfoVO.minRateValue);
			state.setString(8, feeInfoVO.type);
			state.setString(9, feeInfoVO.userID);

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	/**
	 * 添加收费标准
	 * 
	 * @param CFeeInfoVO
	 *            收费标准对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public int addFeeInfo(FeeInfoVO feeInfoVO) throws SQLException, ClassNotFoundException {
		log("===>>>添加收费标准  addFeeInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		int result = 0;
		long id = -1;

		try {
			conn = getConnection();
			sql = "select seq_F_B_feeInfo.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}

			sql = "insert into  F_B_feeInfo(id, upLimit, downLimit, tMode, rate, maxRateValue,minRateValue,type, " + "createTime,userID) "
					+ "values(?, ?, ?, ?, ?, ?,?,?," + "sysdate, ?)";

			state = conn.prepareStatement(sql);
			state.setLong(1, id);
			state.setInt(2, feeInfoVO.upLimit);
			state.setInt(3, feeInfoVO.downLimit);
			state.setInt(4, feeInfoVO.tMode);
			state.setDouble(5, feeInfoVO.rate);
			state.setDouble(6, feeInfoVO.maxRateValue);
			state.setDouble(7, feeInfoVO.minRateValue);
			state.setString(8, feeInfoVO.type);
			state.setString(9, feeInfoVO.userID);

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	/**
	 * 修改收费标准
	 * 
	 * @param FeeInfoVO
	 *            收费标准对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public int modFeeInfo(FeeInfoVO feeInfoVO, Connection conn) throws SQLException, ClassNotFoundException {
		log("===>>>修改收费标准  modFeeInfo  " + new java.util.Date());
		PreparedStatement state = null;
		String sql;
		int result = 0;

		try {
			sql = "update  F_B_feeInfo set upLimit=?, downLimit=?, tMode=?, rate=?, maxRateValue=?,minRateValue=?,type=?, "
					+ "uptateTime=sysdate,userID=?) where id = ?";

			state = conn.prepareStatement(sql);

			state.setInt(1, feeInfoVO.upLimit);
			state.setInt(2, feeInfoVO.downLimit);
			state.setInt(3, feeInfoVO.tMode);
			state.setDouble(4, feeInfoVO.rate);
			state.setDouble(5, feeInfoVO.maxRateValue);
			state.setDouble(6, feeInfoVO.minRateValue);
			state.setString(7, feeInfoVO.type);
			state.setString(8, feeInfoVO.userID);
			state.setLong(9, feeInfoVO.id);

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 修改收费标准
	 * 
	 * @param FeeInfoVO
	 *            收费标准对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public int modFeeInfo(FeeInfoVO feeInfoVO) throws SQLException, ClassNotFoundException {
		log("===>>>修改收费标准  modFeeInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql;
		int result = 0;

		try {
			conn = getConnection();
			sql = "update  F_B_feeInfo set upLimit=?, downLimit=?, tMode=?, rate=?, maxRateValue=?,minRateValue=?,type=?, "
					+ "uptateTime=sysdate,userID=?) where id = ?";

			state = conn.prepareStatement(sql);

			state.setInt(1, feeInfoVO.upLimit);
			state.setInt(2, feeInfoVO.downLimit);
			state.setInt(3, feeInfoVO.tMode);
			state.setDouble(4, feeInfoVO.rate);
			state.setDouble(5, feeInfoVO.maxRateValue);
			state.setDouble(6, feeInfoVO.minRateValue);
			state.setString(7, feeInfoVO.type);
			state.setString(8, feeInfoVO.userID);
			state.setLong(9, feeInfoVO.id);

			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	/**
	 * 删除收费标准
	 * 
	 * @param FeeInfoVO
	 *            收费标准对象
	 * @param conn
	 *            数据库联接
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public int delFeeInfo(FeeInfoVO feeInfoVO, Connection conn) throws SQLException, ClassNotFoundException {
		log("===>>>删除收费标准  delFeeInfo  " + new java.util.Date());
		PreparedStatement state = null;
		String sql;
		int result = 0;
		try {
			sql = "delete from F_B_feeInfo where id=" + feeInfoVO.id;
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 删除收费标准
	 * 
	 * @param FeeInfoVO
	 *            收费标准对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public int delFeeInfo(FeeInfoVO feeInfoVO) throws SQLException, ClassNotFoundException {
		log("===>>>删除收费标准  delFeeInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql;
		int result = 0;
		try {
			conn = getConnection();
			sql = "delete from F_B_feeInfo where id=" + feeInfoVO.id;
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	/**
	 * 删除收费标准
	 * 
	 * @param FeeInfoVO
	 *            收费标准对象
	 * @return int 操作结果 0：成功 非0:失败
	 * @throws SQLException
	 */
	public int delFeeInfo(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>删除收费标准  delFeeInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql;
		int result = 0;
		try {
			conn = getConnection();
			sql = "delete from F_B_feeInfo " + filter;
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	/**
	 * 查询收费标准列表
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库联接
	 * @return Vector 每项为FeeInfoVO
	 * @throws SQLException
	 */
	public Vector<FeeInfoVO> getFeeInfoList(String filter, Connection conn) throws SQLException {
		log("===>>>查询收费标准列表  getFeeInfoList  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FeeInfoVO value = null;
		Vector<FeeInfoVO> list = new Vector<FeeInfoVO>();
		try {
			sql = "select * from F_B_feeInfo " + filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new FeeInfoVO();
				value.id = rs.getLong("id");
				value.upLimit = rs.getInt("upLimit");
				value.downLimit = rs.getInt("downLimit");
				value.tMode = rs.getInt("tMode");
				value.rate = rs.getDouble("rate");
				value.maxRateValue = rs.getDouble("maxRateValue");
				value.minRateValue = rs.getDouble("minRateValue");
				value.type = rs.getString("type");
				value.createTime = rs.getTimestamp("createTime");
				value.updateTime = rs.getTimestamp("updateTime");
				value.userID = rs.getString("userID");
				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return list;
	}

	/**
	 * 查询收费标准列表
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector 每项为FeeInfoVO
	 * @throws SQLException
	 */
	public Vector<FeeInfoVO> getFeeInfoList(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>查询收费标准列表  getFeeInfoList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FeeInfoVO value = null;
		Vector<FeeInfoVO> list = new Vector<FeeInfoVO>();
		try {
			conn = getConnection();
			sql = "select * from F_B_feeInfo " + filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new FeeInfoVO();
				value.id = rs.getLong("id");
				value.upLimit = rs.getInt("upLimit");
				value.downLimit = rs.getInt("downLimit");
				value.tMode = rs.getInt("tMode");
				value.rate = rs.getDouble("rate");
				value.maxRateValue = rs.getDouble("maxRateValue");
				value.minRateValue = rs.getDouble("minRateValue");
				value.type = rs.getString("type");
				value.createTime = rs.getTimestamp("createTime");
				value.updateTime = rs.getTimestamp("updateTime");
				value.userID = rs.getString("userID");
				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return list;
	}

	/**
	 * 查询资金流水总笔数
	 * 
	 * @param filter
	 *            查询条件
	 * @param status
	 *            -1：总笔数 1：查询成功笔数 2:失败笔数 3：处理中笔数 4：待审核笔数
	 * @return int 笔数
	 * @throws SQLException
	 */
	public int countCapitalInfo(String filter, int status) throws SQLException, ClassNotFoundException {

		log("===>>>查询资金流水总笔数  countCapitalInfo  " + new java.util.Date());
		int countNum = 0;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		if (filter == null) {
			filter = " 1=1 ";
		}
		try {
			conn = getConnection();
			if (status == -1) {
				sql = " select count(*) from f_b_capitalinfo t where " + filter;
				state = conn.prepareStatement(sql);
			} else {
				sql = " select count(*) from f_b_capitalinfo t where t.status=? and " + filter;
				state = conn.prepareStatement(sql);
				state.setInt(1, status);
			}

			rs = state.executeQuery();
			while (rs.next()) {
				countNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return countNum;
	}

	/**
	 * 查询资金流水总金额
	 * 
	 * @param filter
	 *            查询条件
	 * @param status
	 *            -1：总金额 1：查询成功金额 2:失败金额 3：处理中金额 4：待审核金额
	 * @return double 金额
	 * @throws SQLException
	 */
	public double countCapitalInfoTotalMoney(String filter, int status) throws SQLException, ClassNotFoundException {
		log("===>>>查询资金流水总金额  countCapitalInfoTotalMoney  " + new java.util.Date());
		double totalMoney = 0.00;

		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		if (filter == null) {
			filter = " 1=1 ";
		}
		try {
			conn = getConnection();
			if (status == -1) {
				sql = " select sum(t.money) from f_b_capitalinfo t where " + filter;
				state = conn.prepareStatement(sql);
			} else {
				sql = " select sum(t.money) from f_b_capitalinfo t where t.status=? and " + filter;
				state = conn.prepareStatement(sql);
				state.setInt(1, status);
			}

			rs = state.executeQuery();
			while (rs.next()) {
				totalMoney = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}

		return totalMoney;
	}

	/**
	 * 查询银行对账信息总笔数
	 * 
	 * @param filter
	 *            查询条件
	 * @param status
	 *            -1：总笔数 1：查询成功笔数 2:失败笔数 3：处理中笔数 4：待审核笔数
	 * @return int 笔数
	 * @throws SQLException
	 */
	public int countBankCompareInfo(String filter, int status) throws SQLException, ClassNotFoundException {

		log("===>>>查询银行对账信息总笔数  countBankCompareInfo  " + new java.util.Date());
		int countNum = 0;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		if (filter == null) {
			filter = " 1=1 ";
		}
		try {
			conn = getConnection();
			if (status == -1) {
				sql = " select count(*) from f_b_bankcompareinfo t where " + filter;
				state = conn.prepareStatement(sql);
			} else {
				sql = " select count(*) from f_b_bankcompareinfo t where t.status=? and " + filter;
				state = conn.prepareStatement(sql);
				state.setInt(1, status);
			}

			rs = state.executeQuery();
			while (rs.next()) {
				countNum = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return countNum;

	}

	/**
	 * 查询银行对账信息总金额
	 * 
	 * @param filter
	 *            查询条件
	 * @param status
	 *            -1：总金额 1：查询成功金额 2:失败金额 3：处理中金额 4：待审核金额
	 * @return double 金额
	 * @throws SQLException
	 */
	public double countBankCompareInfoTotalMoney(String filter, int status) throws SQLException, ClassNotFoundException {
		log("===>>>查询银行对账信息总金额  countBankCompareInfoTotalMoney  " + new java.util.Date());
		double totalMoney = 0.00;

		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		if (filter == null) {
			filter = " 1=1 ";
		}
		try {
			conn = getConnection();
			if (status == -1) {
				sql = " select sum(t.money) from f_b_bankcompareinfo t where " + filter;
				state = conn.prepareStatement(sql);
			} else {
				sql = " select sum(t.money) from f_b_bankcompareinfo t where t.status=? and " + filter;
				state = conn.prepareStatement(sql);
				state.setInt(1, status);
			}

			rs = state.executeQuery();
			while (rs.next()) {
				totalMoney = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}

		return totalMoney;

	}

	/**
	 * 查询资金流水总金额
	 * 
	 * @param id
	 *            资金流水id
	 * @return CapitalValue 资金流水对象
	 * @throws SQLException
	 */
	public CapitalValue handleOutmoenyFromBank(long id) throws SQLException, ClassNotFoundException {
		log("===>>>查询资金流水总金额  handleOutmoenyFromBank  " + new java.util.Date());
		CapitalValue cv = null;

		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		try {
			conn = getConnection();
			sql = " select t.firmid, t.funid, t.bankid, t.debitid, t.creditid, t.type, t.money, t.operator, t.createtime,"
					+ " t.banktime, t.status, t.note, t.actionid, t.express,t.bankName,t.account from f_b_capitalinfo t where t.id = ? ";
			state = conn.prepareStatement(sql);
			state.setLong(1, id);
			rs = state.executeQuery();
			while (rs.next()) {
				cv = new CapitalValue();
				cv.iD = id;
				cv.firmID = rs.getString(1);
				cv.funID = rs.getString(2);
				cv.bankID = rs.getString(3);
				cv.debitID = rs.getString(4);
				cv.creditID = rs.getString(5);
				cv.type = rs.getInt(6);
				cv.money = rs.getDouble(7);
				cv.oprcode = rs.getString(8);
				cv.createtime = rs.getTimestamp(9);
				cv.bankTime = rs.getTimestamp(10);
				cv.status = 3;
				cv.express = rs.getInt(12);
				cv.actionID = rs.getLong(13);
				cv.note = rs.getString(14);
				cv.bankName = rs.getString(15);
				cv.account = rs.getString(16);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}

		return cv;
	}

	/**
	 * 取交易系统结算状态
	 * 
	 * @return int 系统状态 整数：状态 -2异常
	 * @throws SQLException
	 */
	public boolean getTraderStatus() throws SQLException, ClassNotFoundException {
		log("===>>>取交易系统结算状态  getTraderStatus  " + new java.util.Date());
		System.out.println("getTraderStatus");
		boolean traderStatus = false;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		try {
			conn = getConnection();
			sql = " select to_char(max(B_Date),'yyyy-MM-dd') maxDate from f_DailyBalance ";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				String settlementDate = rs.getString(1);
				String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				// System.out.println("settlementDate="+settlementDate);
				// System.out.println("now="+now);
				if (settlementDate != null && settlementDate.indexOf(now) == 0) {
					traderStatus = true;
				}
				System.out.println("traderStatus=" + traderStatus);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		System.out.println("返回=" + traderStatus);

		return traderStatus;
	}

	/**
	 * 取交易系统结算后的数据
	 * 
	 * @param filter
	 *            [日期，交易商等查询条件]
	 * @param moduleid
	 *            板块号 -1全部 2中远期 3现货 4竞价
	 * @return List
	 * @throws SQLException
	 */
	public List<TradeResultValue> getTradeDataInList(String filter, int moduleid) throws SQLException, ClassNotFoundException {
		log("===>>>取交易系统结算后的数据  getTradeDataInList  " + new java.util.Date());
		List<TradeResultValue> resultList = new ArrayList<TradeResultValue>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		try {
			conn = getConnection();
			if (moduleid == 2) {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account "
						+ " from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and to_char(f.oprcode) like '2%' and " + filter
						+ " order by f.fundflowid ";
			} else if (moduleid == 3) {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account "
						+ " from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and to_char(f.oprcode) like '3%' and " + filter
						+ " order by f.fundflowid ";
			} else if (moduleid == 4) {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account "
						+ " from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and to_char(f.oprcode) like '4%' and " + filter
						+ " order by f.fundflowid ";
			} else {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account "
						+ " from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and " + filter + " order by f.fundflowid ";
			}
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				TradeResultValue trv = new TradeResultValue();
				trv.fundFlowId = rs.getLong(1);
				trv.firmid = rs.getString(2);
				trv.oprCode = rs.getInt(3);
				trv.amount = rs.getDouble(4);
				trv.balance = rs.getDouble(5);
				trv.appendAmount = rs.getDouble(6);
				trv.date = rs.getDate(7);
				trv.bankid = rs.getString(8);
				trv.account = rs.getString(9);

				resultList.add(trv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return resultList;
	}

	/**
	 * 取交易系统结算后的数据
	 * 
	 * @param filter
	 * @param moduleid
	 *            板块号 -1全部 2中远期 3现货 4竞价
	 * @return hashtable
	 * @throws SQLException
	 */
	public Hashtable<String, TradeResultValue> getTradeDataInHashTable(String filter, int moduleid) throws SQLException, ClassNotFoundException {
		log("===>>>取交易系统结算后的数据  getTradeDataInHashTable  " + new java.util.Date());
		Hashtable<String, TradeResultValue> resultList = new Hashtable<String, TradeResultValue>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		try {
			conn = getConnection();
			if (moduleid == 2) {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account "
						+ " from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and to_char(f.oprcode) like '2%' and " + filter
						+ " order by f.fundflowid ";
			} else if (moduleid == 3) {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account "
						+ " from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and to_char(f.oprcode) like '3%' and " + filter
						+ " order by f.fundflowid ";
			} else if (moduleid == 4) {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account "
						+ " from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and to_char(f.oprcode) like '4%' and " + filter
						+ " order by f.fundflowid ";
			} else {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account "
						+ " from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and " + filter + " order by f.fundflowid ";
			}
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				TradeResultValue trv = new TradeResultValue();
				trv.fundFlowId = rs.getLong(1);
				trv.firmid = rs.getString(2);
				trv.oprCode = rs.getInt(3);
				trv.amount = rs.getDouble(4);
				trv.balance = rs.getDouble(5);
				trv.appendAmount = rs.getDouble(6);
				trv.date = rs.getDate(7);
				trv.bankid = rs.getString(8);
				trv.account = rs.getString(9);

				resultList.put(trv.firmid, trv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return resultList;
	}

	/**
	 * 市场总额[可用资金和所有权益] 目前只中远期
	 * 
	 * @param filter
	 * @return double
	 * @throws SQLException
	 */
	public Hashtable<String, Double> getFundsAndInterests(java.util.Date date, int moduleid) throws SQLException, ClassNotFoundException {
		log("===>>>市场总额[可用资金和所有权益]  getFundsAndInterests  " + new java.util.Date());
		Hashtable<String, Double> ht = new Hashtable<String, Double>();
		double fundsAndInterests = 0;
		String firmid = "";
		// 从数据库中取出交易商的可用资金和权益并返回
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		try {
			conn = getConnection();
			sql = " select a.firmid,a.todaybalance,b.RuntimeMargin,b.RuntimeFL,b.RuntimeSettleMargin,nvl(c.floatingloss,0) floatingloss from "
					+ " (select firmid, todaybalance from f_firmbalance where b_date=to_date('" + new java.sql.Date(date.getTime())
					+ "','yyyy-MM-dd')) a,"
					+ " (select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where cleardate=to_date('"
					+ date + "','yyyy-MM-dd')) b,"
					+ " (select firmid,nvl(sum(floatingloss),0) floatingloss from t_h_firmholdsum where cleardate=to_date('" + date
					+ "','yyyy-MM-dd') group by firmid) c" + " where a.firmid=b.firmid(+) and a.firmid=c.firmid(+) order by a.firmid ";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				firmid = rs.getString(1);
				fundsAndInterests = rs.getDouble(2) + rs.getDouble(3) + rs.getDouble(4) + rs.getDouble(5) + rs.getDouble(6);
				ht.put(firmid, fundsAndInterests);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return ht;
	}

	/**
	 * 市场总额[可用资金和所有权益] 目前只中远期
	 * 
	 * @param filter
	 * @return double
	 * @throws SQLException
	 */
	public Vector<FundsAndInterests> getFundsAndInterestsInVector(java.util.Date date, int moduleid) throws SQLException, ClassNotFoundException {
		log("===>>>市场总额[可用资金和所有权益]  getFundsAndInterestsInVector  " + new java.util.Date());
		Vector<FundsAndInterests> v = new Vector<FundsAndInterests>();
		double fundsAndInterests = 0;
		// 从数据库中取出交易商的可用资金和权益并返回
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		try {
			conn = getConnection();
			sql = " select a.firmid,a.todaybalance,b.RuntimeMargin,b.RuntimeFL,b.RuntimeSettleMargin,nvl(c.floatingloss,0) floatingloss from "
					+ " (select firmid, todaybalance from f_firmbalance where b_date=to_date('" + new java.sql.Date(date.getTime())
					+ "','yyyy-MM-dd')) a,"
					+ " (select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where cleardate=to_date('"
					+ new java.sql.Date(date.getTime()) + "','yyyy-MM-dd')) b,"
					+ " (select firmid,nvl(sum(floatingloss),0) floatingloss from t_h_firmholdsum where cleardate=to_date('"
					+ new java.sql.Date(date.getTime()) + "','yyyy-MM-dd') group by firmid) c"
					+ " where a.firmid=b.firmid(+) and a.firmid=c.firmid(+) order by a.firmid ";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FundsAndInterests fai = new FundsAndInterests();
				fai.setFirmid(rs.getString(1));
				fai.setBalance(rs.getDouble(2));
				fai.setRuntimeMargin(rs.getDouble(3));
				fai.setRuntimeFL(rs.getDouble(4));
				fai.setRuntimeSettleMargin(rs.getDouble(5));
				fai.setFloatingloss(rs.getDouble(6));
				fundsAndInterests = rs.getDouble(2) + rs.getDouble(3) + rs.getDouble(4) + rs.getDouble(5) + rs.getDouble(6);
				fai.setFundsInterests(fundsAndInterests);
				v.add(fai);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return v;
	}

	public void log(LogValue lv) {

		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = " insert into f_b_log (logid,logopr,logcontent,logdate,logIp)" + " values (SEQ_F_B_LOG.NEXTVAL,?,?,sysdate,?) ";
			state = conn.prepareStatement(sql);
			state.setString(1, lv.getLogopr());
			state.setString(2, lv.getLogcontent());
			state.setString(3, lv.getIp());
			state.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public Vector<LogValue> logList(String filter) {

		Vector<LogValue> v = new Vector<LogValue>();

		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		try {
			conn = getConnection();
			sql = " select logid,logopr,logcontent,to_date(to_char(logdate,'yyyy-MM-dd hh24:mi:ss'),'yyyy-MM-dd hh24:mi:ss') from f_b_log " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();

			while (rs.next()) {
				LogValue lv = new LogValue();
				lv.setLogid(rs.getLong(1));
				lv.setLogopr(rs.getString(2));
				lv.setLogcontent(rs.getString(3));
				lv.setLogdate(rs.getDate(4));

				v.add(lv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStatement(rs, state, conn);
		}
		return v;
	}

	/**
	 * 查询交易商市场可用资金
	 */
	public FirmBalanceValue availableBalance(String filter) {
		log("===>>>查询交易商市场可用资金  availableBalance  " + new java.util.Date());

		FirmBalanceValue fbv = new FirmBalanceValue();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		try {
			conn = getConnection();
			sql = " select firmid,balance,FROZENFUNDS,lastbalance from f_firmfunds " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();

			while (rs.next()) {
				fbv.setFirmId(rs.getString(1));
				fbv.setAvilableBalance(rs.getDouble(2) - rs.getDouble(3));
				fbv.setMarketBalance(rs.getDouble(2));
				fbv.setFrozenBalance(rs.getDouble(3));
				fbv.setLastBalance(rs.getDouble(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStatement(null, state, conn);
		}
		return fbv;
	}

	/**
	 * 查询交易商市场可用资金
	 */
	public FirmBalanceValue availableBalance(String filter, Connection conn) throws SQLException {
		log("===>>>查询交易商市场可用资金  availableBalance  " + new java.util.Date());
		FirmBalanceValue fbv = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = " select firmid,balance,FROZENFUNDS,lastbalance from f_firmfunds " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if (rs.next()) {
				fbv = new FirmBalanceValue();
				fbv.setFirmId(rs.getString(1));
				fbv.setAvilableBalance(rs.getDouble(2) - rs.getDouble(3));
				fbv.setMarketBalance(rs.getDouble(2));
				fbv.setFrozenBalance(rs.getDouble(3));
				fbv.setLastBalance(rs.getDouble(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return fbv;
	}

	/**
	 * 查询交易商 模块里的冻结资金
	 */
	public List<FrozenBalanceVO> frozenBalance(String firmid, String moduleid) {
		List<FrozenBalanceVO> list = new ArrayList<FrozenBalanceVO>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = " where 1=1 ";
		if (!"".equals(firmid) && firmid != null) {
			filter = filter + " and firmid='" + firmid + "'";
		}
		if ("1".equals(moduleid) || "2".equals(moduleid) || "3".equals(moduleid) || "4".equals(moduleid)) {
			filter = filter + " and moduleid=" + firmid;
		}
		try {
			conn = getConnection();
			sql = " select moduleid,firmid,frozenfunds from f_frozenfunds " + filter + " order by firmid,moduleid";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();

			while (rs.next()) {
				FrozenBalanceVO vo = new FrozenBalanceVO();
				vo.firmid = rs.getString("firmid");
				vo.moduleid = rs.getInt("moduleid");
				vo.frozenBalance = rs.getDouble("frozenfunds");
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStatement(null, state, conn);
		}
		return list;
	}

	/**
	 * 查询银行帐号的签约状态
	 */
	public int bankAccountIsOpen(String filter) {
		log("===>>>查询银行帐号的签约状态  bankAccountIsOpen  " + new java.util.Date());
		int isopen = 0;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		try {
			conn = getConnection();
			sql = " select isopen from f_b_firmidandaccount " + filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();

			while (rs.next()) {
				isopen = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStatement(rs, state, conn);
		}
		return isopen;
	}

	/**
	 * 查询交易商名下交易员信息
	 * 
	 * @param firmid
	 *            交易员id
	 * @return string 密码
	 */
	public FirmMessageVo getFirmMSG(String firmid) {
		log("===>>>查询交易商名下交易员的密码  getFirmPwd  " + new java.util.Date());
		FirmMessageVo fmv = null;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		try {
			conn = getConnection();
			sql = " select t.password,t.status from m_trader t where t.traderid=? ";

			state = conn.prepareStatement(sql);
			state.setString(1, firmid);
			rs = state.executeQuery();

			while (rs.next()) {
				fmv = new FirmMessageVo();
				fmv.setFirmid(firmid);
				fmv.setPassword(rs.getString(1));
				fmv.setFirmStatus(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStatement(rs, state, conn);
		}
		return fmv;
	}

	/**
	 * 查询交易系统中交易商某天资金情况
	 * 
	 * @param firmID
	 *            交易商代码
	 * @param tradeDate
	 *            交易日期
	 * @return Vector<FirmFundsValue>
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public Vector<FirmFundsValue> getFrimFunds(String firmID, java.util.Date tradeDate) throws SQLException, ClassNotFoundException {
		this.log("查询交易系统中交易商某天资金情况");
		Vector<FirmFundsValue> result = new Vector<FirmFundsValue>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = "";
		if (firmID != null && firmID.trim().length() > 0) {
			filter += " and firmid='" + firmID.trim() + "'";
		}
		try {
			conn = this.getConnection();
			java.util.Date yesDate = this.getlastDate(tradeDate, conn);
			// sql = "select d.*,a.balance todayFunds,a.frozenfunds frozenfunds,a.lastbalance yesFunds,b.zvmargin zvMargin,c.rsm settleMargin,c.rma
			// runtimeMargin,c.fl,c.cleardate from " + "(select firmid,balance,frozenfunds,lastbalance from f_firmfunds where 1=1 " + filter + ") a, "
			// + "(select firmID,sum(case when code in ('Margin_V','Margin_Z') then value else -value end) zvmargin from f_clientledger where code in
			// ('MarginBack_V','Margin_V','MarginBack_Z','Margin_Z') and trunc(b_date)<to_date('" + Tool.fmtDate(yesDate) + "','yyyy-MM-dd') " +
			// filter + " group by firmid) b, " + "(select firmID,RuntimeSettleMargin rsm,(RuntimeMargin-RuntimeAssure) rma,RuntimeFL fl,cleardate
			// from T_h_firm where 1=1 and trunc(cleardate)=to_date('" + Tool.fmtDate(yesDate) + "','yyyy-MM-dd') " + filter + ") c, " + "(select *
			// from f_b_firmidandaccount where 1=1 " + filter + ") d " + "where d.firmid=a.firmid(+) and d.firmid=b.firmid(+) and
			// d.firmid=c.firmid(+)";
			// TODO 交易商资金情况中用到了远期的数据
			sql = "select d.*,a.balance todayFunds,a.frozenfunds frozenfunds,a.lastbalance yesFunds,b.zvmargin zvMargin from "
					+ "(select firmid,balance,frozenfunds,lastbalance from f_firmfunds where 1=1 " + filter + ") a, "
					+ "(select firmID,sum(case when code in ('Margin_V','Margin_Z') then value else -value end) zvmargin from f_clientledger where code in ('MarginBack_V','Margin_V','MarginBack_Z','Margin_Z') and trunc(b_date)<to_date('"
					+ Tool.fmtDate(yesDate) + "','yyyy-MM-dd') " + filter + " group by firmid) b, " + "(select * from f_b_firmidandaccount where 1=1 "
					+ filter + ") d  " + "where d.firmid=a.firmid(+) and d.firmid=b.firmid(+)";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmFundsValue value = new FirmFundsValue();
				value.firmID = rs.getString("firmID");
				value.yesFunds = rs.getDouble("yesFunds");
				value.todayFunds = rs.getDouble("todayFunds");
				value.margin = 0;
				value.zvMargin = rs.getDouble("zvMargin");
				value.runtimeMargin = 0;// rs.getDouble("runtimeMargin");
				value.settleMargin = 0;// rs.getDouble("settleMargin");
				value.inMoney = 0;
				value.outMoney = 0;
				value.tradeDate = tradeDate;
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	// *******************************深发展银行的对账信息****************************************
	/**
	 * 添加清算日志表
	 */
	public int addBankQS(BankQSVO bq, Connection conn) throws SQLException {
		log("添加清算日期表");
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_BankQSDate (BANKID,TRADEDATE,TRADETYPE,TRADESTATUS,NOTE) values (?,?,?,?,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, bq.bankID);
			state.setDate(2, new java.sql.Date(bq.tradeDate.getTime()));
			state.setInt(3, bq.tradeType);
			state.setInt(4, bq.tradeStatus);
			state.setString(5, bq.note);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 查询清算日期表
	 * 
	 * @param filter
	 * @return Vector<BankQSVO>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public java.util.Date getMaxBankQSList(String bankID, java.util.Date date, Connection conn) throws SQLException {
		log("查询清算日期表");
		java.util.Date result = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select max(tradeDate) tradeDate from F_B_BankQSDATE where bankid='" + bankID + "' and trunc(tradeDate)<to_date('"
					+ Tool.fmtDate(date) + "','yyyy-MM-dd')";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if (rs.next()) {
				result = rs.getDate("tradeDate");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		return result;
	}

	/**
	 * 添加会员的签解约信息
	 */
	public int addFirmKXH(KXHfailChild child, String bankID) throws SQLException, ClassNotFoundException {
		log("添加会员的签解约信息  addFirmKXH  " + Tool.fmtTime(new java.util.Date()));
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "insert into F_B_FIRMKXH (FUNID,STATUS,ACCOUNT1,TYPE,ACCOUNT1NAME,FIRMID,TRADEDATE,COUNTERID,BANKID,CREATEDATE) values (?,?,?,?,?,?,?,?,?,sysdate)";
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			state.setString(1, child.funID);
			state.setInt(2, child.status);
			state.setString(3, child.account1);
			state.setInt(4, child.type);
			state.setString(5, child.account1Name);
			state.setString(6, child.firmID);
			state.setDate(7, new java.sql.Date(child.tradeDate.getTime()));
			state.setString(8, child.counterId);
			state.setString(9, bankID);
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	/**
	 * 根据银行流水号查询签解约信息
	 */
	public KXHfailChild getFirmKXH(String funID) throws SQLException, ClassNotFoundException {
		log("根据银行流水号查询签解约信息  getFirmKXH  " + Tool.fmtTime(new java.util.Date()));
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		KXHfailChild result = null;
		try {
			sql = "select * from F_B_FIRMKXH where funID='" + funID + "'";
			System.out.println("sql:" + sql);
			log("sql:" + sql);
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if (rs.next()) {
				result = new KXHfailChild();
				result.account1 = rs.getString("account1");
				result.account1Name = rs.getString("account1Name");
				result.counterId = rs.getString("counterId");
				result.firmID = rs.getString("firmID");
				result.funID = rs.getString("funID");
				result.status = rs.getInt("status");
				result.tradeDate = rs.getDate("tradeDate");
				result.type = rs.getInt("type");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	/**
	 * 添加银行对账不平记录
	 */
	public int addBatCustDz(BatCustDzFailChild child, java.util.Date date, String bankID) throws SQLException, ClassNotFoundException {
		log("添加银行对账不平记录  addBatCustDz  时间：" + Tool.fmtTime(new java.util.Date()));
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_BATCUSTFILE (CUSTACCTID,CUSTNAME,THIRDCUSTID,BANKBALANCE,BANKFROZEN,MAKETBALANCE,MAKETFROZEN,BALANCEERROR,FROZENERROR,NOTE,TRADEDATE,BANKID,CREATEDATE) values (?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			state.setString(1, child.CustAcctId);
			state.setString(2, child.CustName);
			state.setString(3, child.ThirdCustId);
			state.setDouble(4, child.BankBalance);
			state.setDouble(5, child.BankFrozen);
			state.setDouble(6, child.MaketBalance);
			state.setDouble(7, child.MaketFrozen);
			state.setDouble(8, child.BalanceError);
			state.setDouble(9, child.FrozenError);
			state.setString(10, child.Note);
			state.setDate(11, new java.sql.Date(date.getTime()));
			state.setString(12, bankID);
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	/** 查询银行对账不平文件 */
	public Vector<BatCustDzFailChild> getBatCustDz(String[] firmIDs, String bankID, java.util.Date date) throws SQLException, ClassNotFoundException {
		log("查询银行对账不平文件  getBatCustDz  时间：" + Tool.fmtTime(new java.util.Date()));
		Vector<BatCustDzFailChild> result = new Vector<BatCustDzFailChild>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = " where 1=1 ";
		if (firmIDs != null && firmIDs.length > 0) {
			String str = " and ThirdCustId in ('aa'";
			for (int i = 0; i < firmIDs.length; i++) {
				str += ",'" + firmIDs[i] + "'";
			}
			filter += str + ") ";
		}
		if (bankID != null && bankID.trim().length() > 0) {
			filter += " and BANKID='" + bankID.trim() + "'";
		}
		if (date != null) {
			filter += " and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
		}
		try {
			sql = "select * from F_B_BATCUSTFILE " + filter;
			System.out.println("sql:" + sql);
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				BatCustDzFailChild value = new BatCustDzFailChild();
				value.CustAcctId = rs.getString("CustAcctId");
				value.CustName = rs.getString("CustName");
				value.ThirdCustId = rs.getString("ThirdCustId");
				value.BankBalance = rs.getDouble("BankBalance");
				value.BankFrozen = rs.getDouble("BankFrozen");
				value.MaketBalance = rs.getDouble("MaketBalance");
				value.MaketFrozen = rs.getDouble("MaketFrozen");
				value.BalanceError = rs.getDouble("BalanceError");
				value.FrozenError = rs.getDouble("FrozenError");
				value.Note = rs.getString("Note");
				result.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	/** 查询银行对账不平文件 */
	public Vector<BatCustDzFailChild> getBatCustDz(java.util.Date date, String bankID) throws SQLException, ClassNotFoundException {
		log("查询银行对账不平文件信息  getBatCustDz  时间：" + Tool.fmtTime(new java.util.Date()));
		Vector<BatCustDzFailChild> result = new Vector<BatCustDzFailChild>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = " where 1=1 ";
		filter += " and bankid='" + bankID + "' and trunc(tradedate)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
		try {
			sql = "select * from F_B_BATCUSTFILE " + filter;
			System.out.println("sql:" + sql);
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				BatCustDzFailChild value = new BatCustDzFailChild();
				value.CustAcctId = rs.getString("CustAcctId");
				value.CustName = rs.getString("CustName");
				value.ThirdCustId = rs.getString("ThirdCustId");
				value.BankBalance = rs.getDouble("BankBalance");
				value.BankFrozen = rs.getDouble("BankFrozen");
				value.MaketBalance = rs.getDouble("MaketBalance");
				value.MaketFrozen = rs.getDouble("MaketFrozen");
				value.BalanceError = rs.getDouble("BalanceError");
				value.FrozenError = rs.getDouble("FrozenError");
				value.Note = rs.getString("Note");
				result.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	/**
	 * 删除对账不平记录
	 */
	public int delBatCustDz(java.util.Date date, String bankID) throws SQLException, ClassNotFoundException {
		log("删除对账不平记录  delBatCustDz  时间：" + Tool.fmtTime(new java.util.Date()));
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		String filter = " where 1=1 ";
		if (date != null) {
			filter += " and trunc(tradedate)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
		}
		if (bankID != null && bankID.trim().length() > 0) {
			filter += " and bankid='" + bankID + "'";
		}
		try {
			sql = "delete F_B_BATCUSTFILE " + filter;
			log("sql:" + sql);
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	/** 添加交易商余额文件 */
	public int addFirmBalanceFile(BatCustDzBChild child, java.util.Date date, String bankID) throws SQLException, ClassNotFoundException {
		log("添加交易商余额文件  addFirmBalanceFile  时间：" + Tool.fmtTime(new java.util.Date()));
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_FIRMBALANCE (CUSTACCTID,CUSTNAME,THIRDCUSTID,STATUS,TYPE,ISTRUECONT,BALANCE,USRBALANCE,FROZENBALANCE,INTEREST,BANKID,TRADEDATE,CREATEDATE) values (?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			state.setString(1, child.CustAcctId);
			state.setString(2, child.CustName);
			state.setString(3, child.ThirdCustId);
			state.setInt(4, child.Status);
			state.setInt(5, child.Type);
			state.setInt(6, child.IsTrueCont);
			state.setDouble(7, child.Balance);
			state.setDouble(8, child.UsrBalance);
			state.setDouble(9, child.FrozenBalance);
			state.setDouble(10, child.Interest);
			state.setString(11, bankID);
			state.setDate(12, new java.sql.Date(date.getTime()));
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	/** 修改交易商子账号信息 */
	public int modFirmBalanceFile(BatCustDzBChild child, java.util.Date date, String bankID) throws SQLException, ClassNotFoundException {
		log("修改交易商子账号信息  modFirmBalanceFile  时间：" + Tool.fmtTime(new java.util.Date()));
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "update F_B_FIRMBALANCE set CUSTACCTID=?,CUSTNAME=?,STATUS=?,TYPE=?,ISTRUECONT=?,BALANCE=?,USRBALANCE=?,FROZENBALANCE=?,INTEREST=? where BANKID=? and THIRDCUSTID=? and trunc(TRADEDATE)=to_date('"
					+ Tool.fmtDate(date) + "','yyyy-MM-dd')";
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			state.setString(1, child.CustAcctId);
			state.setString(2, child.CustName);
			state.setInt(3, child.Status);
			state.setInt(4, child.Type);
			state.setInt(5, child.IsTrueCont);
			state.setDouble(6, child.Balance);
			state.setDouble(7, child.UsrBalance);
			state.setDouble(8, child.FrozenBalance);
			state.setDouble(9, child.Interest);
			state.setString(10, bankID);
			state.setString(11, child.ThirdCustId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	/** 查询会员签解约不对应信息 */
	public Vector<FirmOpenCloseBank> getFirmBank(String bankID, java.util.Date date) throws SQLException, ClassNotFoundException {
		log("查询会员签解约不平文件  getFirmBank");
		Vector<FirmOpenCloseBank> result = new Vector<FirmOpenCloseBank>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {// 查询市场没有的签约
			sql = "select FIRMID from f_b_firmkxh a where bankid='" + bankID + "' and status=1 and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date)
					+ "','yyyy-MM-dd') and not exists(select FIRMID from f_b_firmidandaccount b where bankid='" + bankID
					+ "' and trunc(opentime)=to_date('" + Tool.fmtDate(date)
					+ "','yyyy-MM-dd') and b.firmid=a.firmid and b.bankid=a.bankid or b.firmid='D_'||a.firmid and b.bankid='D_'||a.bankid)";
			log("sql:" + sql);
			System.out.println("sql:" + sql);
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmOpenCloseBank value = new FirmOpenCloseBank();
				value.firmID = rs.getString("firmId");
				value.type = 1;
				value.note = "市场无签约信息";
				value.tradeDate = date;
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		try {// 查询市场没有的解约
			sql = "select FIRMID from f_b_firmkxh a where bankid='" + bankID + "' and status=2 and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date)
					+ "','yyyy-MM-dd') and not exists(select FIRMID from f_b_firmidandaccount b where (bankid='" + bankID + "' or bankid='D_" + bankID
					+ "') and trunc(deltime)=to_date('" + Tool.fmtDate(date)
					+ "','yyyy-MM-dd') and b.firmid='D_'||a.firmid and b.bankid='D_'||a.bankid)";
			log("sql:" + sql);
			System.out.println("sql:" + sql);
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmOpenCloseBank value = new FirmOpenCloseBank();
				value.firmID = rs.getString("firmId");
				value.type = 2;
				value.note = "市场无解约信息";
				value.tradeDate = date;
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		try {// 查询银行没有的签约
			sql = "select firmid from f_b_firmidandaccount a where (bankid='" + bankID + "' or bankid='D_" + bankID
					+ "') and trunc(opentime)=to_date('" + Tool.fmtDate(date)
					+ "', 'yyyy-MM-dd') and not exists (select firmid from f_b_firmkxh b where bankid='" + bankID
					+ "' and status=1 and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date)
					+ "', 'yyyy-MM-dd') and (b.firmid=a.firmid or 'D_'||b.firmid=a.firmid))";
			log("sql:" + sql);
			System.out.println("sql:" + sql);
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmOpenCloseBank value = new FirmOpenCloseBank();
				value.firmID = rs.getString("firmId");
				value.type = 1;
				value.note = "银行无签约信息";
				value.tradeDate = date;
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		try {// 查询银行没有的解约
			sql = "select firmid from f_b_firmidandaccount a where bankid='D_" + bankID + "' and trunc(deltime)=to_date('" + Tool.fmtDate(date)
					+ "', 'yyyy-MM-dd') and not exists (select firmid from f_b_firmkxh b where bankid='" + bankID
					+ "' and status=2 and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date) + "', 'yyyy-MM-dd') and 'D_'||b.firmid=a.firmid)";
			log("sql:" + sql);
			System.out.println("sql:" + sql);
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmOpenCloseBank value = new FirmOpenCloseBank();
				value.firmID = rs.getString("firmId");
				value.type = 2;
				value.note = "银行无解约信息";
				value.tradeDate = date;
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	/** 查询交易商余额信息 */
	public Vector<BatCustDzBChild> getFirmBalanceFile(String ThirdCustId, String bankID, java.util.Date date)
			throws SQLException, ClassNotFoundException {
		log("查询交易商银行余额信息  getFirmBalanceFile  时间：" + Tool.fmtTime(new java.util.Date()));
		Vector<BatCustDzBChild> result = new Vector<BatCustDzBChild>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = " where 1=1 ";
		if (ThirdCustId != null && ThirdCustId.trim().length() > 0) {
			filter = filter + " and THIRDCUSTID='" + ThirdCustId.trim() + "'";
		}
		if (bankID != null && bankID.trim().length() > 0) {
			filter = filter + " and BANKID='" + bankID.trim() + "'";
		}
		if (date != null) {
			filter = filter + " and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
		}
		try {
			sql = "select * from F_B_FIRMBALANCE " + filter;
			System.out.println("sql:" + sql);
			log("sql:" + sql);
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				BatCustDzBChild bcd = new BatCustDzBChild();
				bcd.CustAcctId = rs.getString("CUSTACCTID");
				bcd.CustName = rs.getString("CUSTNAME");
				bcd.ThirdCustId = rs.getString("THIRDCUSTID");
				bcd.Status = rs.getInt("STATUS");
				bcd.Type = rs.getInt("TYPE");
				bcd.IsTrueCont = rs.getInt("ISTRUECONT");
				bcd.Balance = rs.getDouble("BALANCE");
				bcd.UsrBalance = rs.getDouble("USRBALANCE");
				bcd.FrozenBalance = rs.getDouble("FROZENBALANCE");
				bcd.Interest = rs.getDouble("INTEREST");
				result.add(bcd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	/** 添加银行对账失败文件 */
	public int addFirmBalanceError(BatFailResultChild fbe, String bankID) throws SQLException, ClassNotFoundException {
		log("添加银行对账失败文件  addFirmBalanceError  时间：" + Tool.fmtTime(new java.util.Date()));
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_FIRMBALANCEERROR "
					+ "(TRANDATETIME,COUNTERID,SUPACCTID,FUNCCODE,CUSTACCTID,CUSTNAME,THIRDCUSTID,THIRDLOGNO,CCYCODE,FREEZEAMOUNT,UNFREEZEAMOUNT,ADDTRANAMOUNT,CUTTRANAMOUNT,PROFITAMOUNT,LOSSAMOUNT,TRANHANDFEE,TRANCOUNT,NEWBALANCE,NEWFREEZEAMOUNT,NOTE,RESERVE,RSPCODE,RSPMSG,BANKID,CREATETIME) "
					+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			state.setDate(1, new java.sql.Date(Tool.getDateTime(fbe.TranDateTime).getTime()));
			state.setString(2, fbe.CounterId);
			state.setString(3, fbe.SupAcctId);
			state.setString(4, fbe.FuncCode);
			state.setString(5, fbe.CustAcctId);
			state.setString(6, fbe.CustName);
			state.setString(7, fbe.ThirdCustId);
			state.setString(8, fbe.ThirdLogNo);
			state.setString(9, fbe.CcyCode);
			state.setDouble(10, fbe.FreezeAmount);
			state.setDouble(11, fbe.UnfreezeAmount);
			state.setDouble(12, fbe.AddTranAmount);
			state.setDouble(13, fbe.CutTranAmount);
			state.setDouble(14, fbe.ProfitAmount);
			state.setDouble(15, fbe.LossAmount);
			state.setDouble(16, fbe.TranHandFee);
			state.setInt(17, fbe.TranCount);
			state.setDouble(18, fbe.NewBalance);
			state.setDouble(19, fbe.NewFreezeAmount);
			state.setString(20, fbe.Note);
			state.setString(21, fbe.Reserve);
			state.setString(22, fbe.RspCode);
			state.setString(23, fbe.RspMsg);
			state.setString(24, bankID);
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	/** 查询银行对账失败信息 */
	public Vector<BatFailResultChild> getFirmBalanceError(String[] firmIDs, String bankID, java.util.Date date)
			throws SQLException, ClassNotFoundException {
		log("查询银行对账失败信息  getFirmBalanceError  时间：" + Tool.fmtDate(new java.util.Date()));
		Vector<BatFailResultChild> result = new Vector<BatFailResultChild>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = " where 1=1 ";
		if (firmIDs != null && firmIDs.length > 0) {
			String str = "('aa'";
			for (int i = 0; i < firmIDs.length; i++) {
				str += ",'" + firmIDs[i] + "'";
			}
			str += ") ";
			filter += " and ThirdCustId in " + str;
		}
		if (bankID != null && bankID.trim().length() > 0) {
			filter += " and bankid='" + bankID.trim() + "' ";
		}
		if (date != null) {
			filter += " and trunc(TranDateTime)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd') ";
		}
		try {
			sql = "select * from f_b_firmbalanceerror " + filter;
			System.out.println("sql:" + sql);
			log("sql:" + sql);
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				BatFailResultChild value = new BatFailResultChild();
				value.TranDateTime = Tool.fmtTime(rs.getDate("TRANDATETIME"));
				value.CounterId = rs.getString("COUNTERID");
				value.SupAcctId = rs.getString("SUPACCTID");
				value.FuncCode = rs.getString("FUNCCODE");
				value.CustAcctId = rs.getString("CUSTACCTID");
				value.CustName = rs.getString("CUSTNAME");
				value.ThirdCustId = rs.getString("THIRDCUSTID");
				value.ThirdLogNo = rs.getString("THIRDLOGNO");
				value.CcyCode = rs.getString("CCYCODE");
				value.FreezeAmount = rs.getDouble("FREEZEAMOUNT");
				value.UnfreezeAmount = rs.getDouble("UNFREEZEAMOUNT");
				value.AddTranAmount = rs.getDouble("ADDTRANAMOUNT");
				value.CutTranAmount = rs.getDouble("CUTTRANAMOUNT");
				value.ProfitAmount = rs.getDouble("PROFITAMOUNT");
				value.LossAmount = rs.getDouble("LOSSAMOUNT");
				value.TranHandFee = rs.getDouble("TRANHANDFEE");
				value.TranCount = rs.getInt("TRANCOUNT");
				value.NewBalance = rs.getDouble("NEWBALANCE");
				value.NewFreezeAmount = rs.getDouble("NEWFREEZEAMOUNT");
				value.Note = rs.getString("NOTE");
				value.Reserve = rs.getString("RESERVE");
				value.RspCode = rs.getString("RSPCODE");
				value.RspMsg = rs.getString("RSPMSG");
				result.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	/** 获取某日的清算信息 */
	public Map<String, BatQsChild> getQSChild(String bankID, Set<String> firmIDs, Set<String> notFirmIDs, java.util.Date qdate, Connection conn)
			throws SQLException {
		log("获取某日的清算信息  getQSChild 时间：" + Tool.fmtDate(new java.util.Date()));
		Map<String, BatQsChild> result = new HashMap<String, BatQsChild>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = "";
		if (firmIDs != null && firmIDs.size() > 0) {
			Iterator<String> it = firmIDs.iterator();
			String firms = "('aa'";
			while (it.hasNext()) {
				firms += ",'" + it.next() + "'";
			}
			firms += ")";
			filter += " and firmid in " + firms;
		}
		if (notFirmIDs != null && notFirmIDs.size() > 0) {
			Iterator<String> it = notFirmIDs.iterator();
			String firms = "('aa'";
			while (it.hasNext()) {
				firms += ",'" + it.next() + "'";
			}
			firms += ")";
			filter += " and firmid not in " + firms;
		}
		String filter2 = filter + " and isopen=1 and firmid not like '%/_D%' escape '/' and firmid not like '%D/_%' escape '/' ";
		if (bankID != null && bankID.trim().length() > 0) {
			filter2 += " and bankID='" + bankID + "' ";
		}
		try {
			java.util.Date yestody = getlastDate(qdate, conn);

			// sql =
			// "select nvl(a.value,0)+(case when (nvl(j.valued,0)-nvl(j.valuez,0))>0 then (nvl(j.valued,0)-nvl(j.valuez,0)) else 0 end)+(case when
			// nvl(k.value,0)>0 then nvl(k.value,0) else 0 end) AddTranAmount,"
			// +// --销售收入
			// "nvl(b.value,0)+(case when (nvl(j.valuez,0)-nvl(j.valued,0))>0 then (nvl(j.valuez,0)-nvl(j.valued,0)) else 0 end)+(case when
			// nvl(k.value,0)<0 then nvl(-k.value,0) else 0 end) CutTranAmount,"
			// +// --购货支出
			// "nvl(c.value,0) ProfitAmount," +// --盈利
			// "nvl(d.value,0) LossAmount," +// --亏损
			// "nvl(e.value,0) TranHandFee," +// --手续费
			// "nvl(f.valued,0)+(case when (nvl(j.valued,0)-nvl(j.valuez,0))>0 then (nvl(j.valued,0)-nvl(j.valuez,0)) else 0 end) FreezeAmount,"
			// +// --冻结资金
			// "nvl(f.valuez,0) UnfreezeAmount," +// --上日冻结资金
			// "nvl(i.valued,0) toDhykAmount," +// --当天订货盈亏
			// "nvl(i.valuez,0) yeDhykAmount," +// --上一个交易日的订货盈亏
			// "nvl(j.valued,0) toQianAmount," +//当日欠款
			// "nvl(j.valuez,0) yeQianAmount," +//上日欠款
			// "nvl(g.value,0) NewBalance," +// --市场资金
			// "nvl(f.valued,0) NewFreezeAmount," +// --当前冻结资金
			// "h.firmid ThirdCustId," +// --交易商代码
			// "h.account1 CustAcctId," +// --交易商子账户
			// "h.ACCOUNTNAME1 CustName " +// --子账户名称
			// "from " +
			// "(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and
			// fc.b_date=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') and fl.code in ('Income','Income_Z','Income_V') "+filter+" group by
			// fc.firmid) a, "
			// +// --销售收入
			// "(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and
			// fc.b_date=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') and fl.code in ('Payout','Payout_Z','Payout_V') "+filter+" group by
			// fc.firmid) b, "
			// +// --购货支出
			// "(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and
			// fc.b_date=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') and fl.code in ('TradePL','SettlePL') and value>=0 "+filter+" group by
			// fc.firmid) c, "
			// +// --盈利
			// "(select fc.firmid,sum(nvl(-fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and
			// fc.b_date=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') and fl.code in ('TradePL','SettlePL') and value<0 "+filter+" group by
			// fc.firmid) d, "
			// +// --亏损
			// "(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and
			// fc.b_date=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') and fl.code in ('BankFee','TradeFee','SettleFee','TradeFee_Z','TradeFee_V')
			// "+filter+" group by fc.firmid) e, "
			// +// --手续费
			// "( " +
			// "select nvl(fb.value,0)+nvl(fc.value,0)+nvl(fd.value,0)+nvl(ff.value,0)+nvl(fg.value,0)
			// valued,nvl(fb1.value,0)+nvl(fc1.value,0)+nvl(fd1.value,0)+nvl(ff1.value,0)+nvl(fg1.value,0) valuez, fe.firmid from "
			// +
			// "(select nvl(RuntimeSettleMargin,0) value,firmid from t_h_firm where cleardate=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd')
			// "+filter+") fb, "
			// +// --当日交收保证金
			// "(select nvl(RuntimeFL,0) value,firmid from t_h_firm where cleardate=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') "+filter+") fc, "
			// +// --当日浮亏
			// "(select nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0) value,firmid from t_h_firm where
			// cleardate=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') "+filter+") fd, "
			// +// --当日远期保证金
			// "(select nvl(payout_Z,0)+nvl(Payout_V,0)+nvl(payOut_T,0) value,firmid from F_firmbalance where
			// trunc(B_Date)=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') "+filter+") ff, "
			// + //当日待付货款
			// "(select nvl(sum(case when code = 'Margin_V' then value when code = 'Margin_Z' then value when code = 'MarginBack_V' then -value when
			// code = 'MarginBack_Z' then -value else 0 end), 0) value,firmid from f_clientledger where code in ('MarginBack_V',
			// 'Margin_V','MarginBack_Z', 'Margin_Z') and to_char(b_date,'yyyy-MM-dd')<='"+Tool.fmtDate(qdate)+"' "+filter+" group by firmid) fg, "
			// +//当日现货竞价保证金
			//
			// "(select nvl(RuntimeSettleMargin,0) value,firmid from t_h_firm where cleardate=to_date('"+Tool.fmtDate(yestody)+"','yyyy-MM-dd')
			// "+filter+") fb1, "
			// +// --上日交收保证金
			// "(select nvl(RuntimeFL,0) value,firmid from t_h_firm where cleardate=to_date('"+Tool.fmtDate(yestody)+"','yyyy-MM-dd') "+filter+") fc1,
			// "
			// +// --上日浮亏
			// "(select nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0) value,firmid from t_h_firm where
			// cleardate=to_date('"+Tool.fmtDate(yestody)+"','yyyy-MM-dd') "+filter+") fd1, "
			// +// --上日远期保证金
			// "(select nvl(payout_Z,0)+nvl(Payout_V,0)+nvl(payOut_T,0) value,firmid from F_firmbalance where
			// trunc(B_Date)=to_date('"+Tool.fmtDate(yestody)+"','yyyy-MM-dd') "+filter+") ff1, "
			// + //上日待付货款
			// "(select nvl(sum(case when code = 'Margin_V' then value when code = 'Margin_Z' then value when code = 'MarginBack_V' then -value when
			// code = 'MarginBack_Z' then -value else 0 end), 0) value,firmid from f_clientledger where code in ('MarginBack_V',
			// 'Margin_V','MarginBack_Z', 'Margin_Z') and to_char(b_date,'yyyy-MM-dd')<='"+Tool.fmtDate(yestody)+"' "+filter+" group by firmid) fg1, "
			// +//上日现货竞价保证金
			//
			// "(select firmid from f_b_firmidandaccount where 1=1 and (account1 is not null) "+filter2+") fe "
			// +//--和银行接口连接
			// "where fe.firmid=fb.firmid(+) and fe.firmid=fc.firmid(+) and fe.firmid=fd.firmid(+) and fe.firmid=ff.firmid(+) and
			// fe.firmid=fg.firmid(+) and fe.firmid=fb1.firmid(+) and fe.firmid=fc1.firmid(+) and fe.firmid=fd1.firmid(+) and fe.firmid=ff1.firmid(+)
			// and fe.firmid=fg1.firmid(+) "
			// +
			// ") f, " +// --冻结解冻资金
			// "(" +
			// "select nvl(ia.value,0) valued,nvl(ib.value,0) valuez,ic.firmid from "
			// +
			// "(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where
			// trunc(cleardate)=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') "+filter+" group by firmid) ia,"
			// + //当日浮动盈亏
			// "(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where
			// trunc(cleardate)=to_date('"+Tool.fmtDate(yestody)+"','yyyy-MM-dd') "+filter+" group by firmid) ib,"
			// + //上日浮动盈亏
			// "(select firmid from f_b_firmidandaccount where 1=1 and (account1 is not null) and account1<>'null' "+filter2+") ic "
			// + //和银行接口表挂钩
			// "where ic.firmid=ia.firmid(+) and ic.firmid=ib.firmid(+)" +
			// ") i," + //浮动盈亏
			// "(" +
			// "select nvl(ja.value,0) valued,nvl(ja1.value,0) valuez,jb.firmid firmid from"
			// +
			// "(select nvl(Overdraft,0) value,firmid from F_firmbalance where trunc(B_Date)=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd')
			// "+filter+") ja,"
			// +//当日欠款
			// "(select nvl(Overdraft,0) value,firmid from F_firmbalance where trunc(B_Date)=to_date('"+Tool.fmtDate(yestody)+"','yyyy-MM-dd')
			// "+filter+") ja1,"
			// +//上日欠款
			// "(select firmid from f_b_firmidandaccount where 1=1 and (account1 is not null) and account1<>'null' "+filter2+") jb "
			// + //和银行接口表挂钩
			// "where jb.firmid=ja.firmid(+) and jb.firmid=ja1.firmid(+)" +
			// ") j," +
			// "(select nvl(value,0) value,firmid from f_clientledger where code='OtherItem_Z' and
			// trunc(B_Date)=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') "
			// + filter + ") k, " +
			// "(select todaybalance value,firmid from f_firmbalance where b_date=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') "+filter+") g,"
			// +// --当日余额
			// "(select account1,ACCOUNTNAME1,firmid from f_b_firmidandaccount where 1=1 and (account1 is not null) and account1<>'null' "+filter2+")
			// h "
			// +// --和银行接口连接
			// "where h.firmid=a.firmid(+) and h.firmid=b.firmid(+) and h.firmid=c.firmid(+) and h.firmid=d.firmid(+) and h.firmid=e.firmid(+) and
			// h.firmid=f.firmid(+) and h.firmid=g.firmid(+) and h.firmid=i.firmid(+) and h.firmid=j.firmid(+) and h.firmid=k.firmid(+)";

			sql = "select nvl(a.value,0)" + /*
											 * +(case when
											 * (nvl(j.valued,0)-nvl(j
											 * .valuez,0))>0 then
											 * (nvl(j.valued,0)-nvl(j.valuez,0))
											 * else 0 end)
											 */"+(case when nvl(k.value,0)>0 then nvl(k.value,0) else 0 end)" + /*
																												 * +(
																												 * case
																												 * when
																												 * f
																												 * .
																												 * ffvalue
																												 * >
																												 * 0
																												 * then
																												 * f
																												 * .
																												 * ffvalue
																												 * else
																												 * 0
																												 * end
																												 * )
																												 */" AddTranAmount," + // --销售收入
					"nvl(b.value,0)" + /*
										 * +(case when
										 * (nvl(j.valuez,0)-nvl(j.valued,0))>0
										 * then
										 * (nvl(j.valuez,0)-nvl(j.valued,0))
										 * else 0 end)
										 */"+(case when nvl(k.value,0)<0 then nvl(-k.value,0) else 0 end)" + /*
																											 * +(
																											 * case
																											 * when
																											 * f
																											 * .
																											 * ffvalue
																											 * <
																											 * 0
																											 * then
																											 * -
																											 * f
																											 * .
																											 * ffvalue
																											 * else
																											 * 0
																											 * end
																											 * )
																											 */" CutTranAmount," + // --购货支出
					"nvl(c.value,0) ProfitAmount," + // --盈利
					"nvl(d.value,0) LossAmount," + // --亏损
					"nvl(e.value,0) TranHandFee," + // --手续费
					"nvl(f.valued,0)" + /*
										 * +(case when
										 * (nvl(j.valued,0)-nvl(j.valuez,0))>0
										 * then
										 * (nvl(j.valued,0)-nvl(j.valuez,0))
										 * else 0 end)
										 */" FreezeAmount," + // --冻结资金
					"nvl(f.valuez,0) UnfreezeAmount," + // --上日冻结资金
					"nvl(i.valued,0) toDhykAmount," + // --当天订货盈亏
					"nvl(i.valuez,0) yeDhykAmount," + // --上一个交易日的订货盈亏
			/*
			 * "nvl(j.valued,0) toQianAmount," +//当日欠款
			 * "nvl(j.valuez,0) yeQianAmount," +//上日欠款
			 */
			"nvl(g.value,0) NewBalance," + // --市场资金
					"nvl(f.valued,0) NewFreezeAmount," + // --当前冻结资金
					"h.firmid ThirdCustId," + // --交易商代码
					"h.account1 CustAcctId," + // --交易商子账户
					"h.ACCOUNTNAME1 CustName " + // --子账户名称
					"from "
					+ "(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('Income','Income_Z','Income_V') " + filter + " group by fc.firmid) a, " + // --销售收入
					"(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('Payout','Payout_Z','Payout_V') " + filter + " group by fc.firmid) b, " + // --购货支出
					"(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('TradePL','SettlePL') and value>=0 " + filter
					+ " group by fc.firmid) c, " + // --盈利
					"(select fc.firmid,sum(nvl(-fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('TradePL','SettlePL') and value<0 " + filter + " group by fc.firmid) d, "
					+ // --亏损
					"(select fc.firmid,sum(nvl(case when fl.code='SettleCompens' then -fc.value else fc.value end,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') and fl.code in ('BankFee','TradeFee','SettleFee','TradeFee_Z','TradeFee_V','SettleFee_Z','SettleCompens') "
					+ filter + " group by fc.firmid) e, " + // --手续费
					"( " + "select nvl(fb.value,0)+nvl(fc.value,0)+nvl(fd.value,0)"
					+ /*
						 * +nvl
						 * (
						 * ff
						 * .
						 * value
						 * ,
						 * 0
						 * )
						 */"+nvl(fg.value,0) valued,nvl(fb1.value,0)+nvl(fc1.value,0)+nvl(fd1.value,0)" + /*
																										 * +nvl
																										 * (
																										 * ff1
																										 * .
																										 * value
																										 * ,
																										 * 0
																										 * )
																										 */"+nvl(fg1.value,0) valuez"
					+ /*
						 * ,
						 * nvl
						 * (
						 * ff
						 * .
						 * value
						 * ,
						 * 0
						 * )
						 * -
						 * nvl
						 * (
						 * ff1
						 * .
						 * value
						 * ,
						 * 0
						 * )
						 * ffvalue
						 */", fe.firmid from " + "(select nvl(RuntimeSettleMargin,0) value,firmid from t_h_firm where cleardate=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') " + filter + ") fb, " + // --当日交收保证金
					"(select nvl(RuntimeFL,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') "
					+ filter + ") fc, " + // --当日浮亏
					"(select nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') " + filter + ") fd, " + // --当日远期保证金
			// "(select nvl(payout_Z,0)+nvl(Payout_V,0)+nvl(payOut_T,0) value,firmid from F_firmbalance where
			// trunc(B_Date)=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd') "+filter+") ff, "
			// + //当日待付货款
			"(select nvl(sum(case when code = 'Margin_V' then value when code = 'Margin_Z' then value when code = 'MarginBack_V' then -value when code = 'MarginBack_Z' then -value else 0 end), 0) value,firmid from f_clientledger where code in ('MarginBack_V', 'Margin_V','MarginBack_Z', 'Margin_Z') and to_char(b_date,'yyyy-MM-dd')<='"
					+ Tool.fmtDate(qdate) + "' " + filter + " group by firmid) fg, " + // 当日现货竞价保证金

			"(select nvl(RuntimeSettleMargin,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody) + "','yyyy-MM-dd') "
					+ filter + ") fb1, " + // --上日交收保证金
					"(select nvl(RuntimeFL,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody) + "','yyyy-MM-dd') "
					+ filter + ") fc1, " + // --上日浮亏
					"(select nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody)
					+ "','yyyy-MM-dd') " + filter + ") fd1, " + // --上日远期保证金
			// "(select nvl(payout_Z,0)+nvl(Payout_V,0)+nvl(payOut_T,0) value,firmid from F_firmbalance where
			// trunc(B_Date)=to_date('"+Tool.fmtDate(yestody)+"','yyyy-MM-dd') "+filter+") ff1, "
			// + //上日待付货款
			"(select nvl(sum(case when code = 'Margin_V' then value when code = 'Margin_Z' then value when code = 'MarginBack_V' then -value when code = 'MarginBack_Z' then -value else 0 end), 0) value,firmid from f_clientledger where code in ('MarginBack_V', 'Margin_V','MarginBack_Z', 'Margin_Z') and to_char(b_date,'yyyy-MM-dd')<='"
					+ Tool.fmtDate(yestody) + "' " + filter + " group by firmid) fg1, " + // 上日现货竞价保证金

			"(select firmid from f_b_firmidandaccount where 1=1 and (account1 is not null) " + filter2 + ") fe " + // --和银行接口连接
					"where fe.firmid=fb.firmid(+) and fe.firmid=fc.firmid(+) and fe.firmid=fd.firmid(+)"
					+ /*
						 * and
						 * fe
						 * .
						 * firmid
						 * =
						 * ff
						 * .
						 * firmid
						 * (
						 * +
						 * )
						 */" and fe.firmid=fg.firmid(+) and fe.firmid=fb1.firmid(+) and fe.firmid=fc1.firmid(+) and fe.firmid=fd1.firmid(+)"
					+ /*
						 * and
						 * fe
						 * .
						 * firmid
						 * =
						 * ff1
						 * .
						 * firmid
						 * (
						 * +
						 * )
						 */" and fe.firmid=fg1.firmid(+) " + ") f, " + // --冻结解冻资金
					"(" + "select nvl(ia.value,0) valued,nvl(ib.value,0) valuez,ic.firmid from "
					+ "(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where trunc(cleardate)=to_date('" + Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') " + filter + " group by firmid) ia," + // 当日浮动盈亏
					"(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where trunc(cleardate)=to_date('" + Tool.fmtDate(yestody)
					+ "','yyyy-MM-dd') " + filter + " group by firmid) ib," + // 上日浮动盈亏
					"(select firmid from f_b_firmidandaccount where 1=1 and (account1 is not null) and account1<>'null' " + filter2 + ") ic " + // 和银行接口表挂钩
					"where ic.firmid=ia.firmid(+) and ic.firmid=ib.firmid(+)" + ") i," + // 浮动盈亏
			// "(" +
			// "select nvl(ja.value,0) valued,nvl(ja1.value,0) valuez,jb.firmid firmid from"
			// +
			// "(select nvl(Overdraft,0) value,firmid from F_firmbalance where trunc(B_Date)=to_date('"+Tool.fmtDate(qdate)+"','yyyy-MM-dd')
			// "+filter+") ja,"
			// +//当日欠款
			// "(select nvl(Overdraft,0) value,firmid from F_firmbalance where trunc(B_Date)=to_date('"+Tool.fmtDate(yestody)+"','yyyy-MM-dd')
			// "+filter+") ja1,"
			// +//上日欠款
			// "(select firmid from f_b_firmidandaccount where 1=1 and (account1 is not null) and account1<>'null' "+filter2+") jb "
			// + //和银行接口表挂钩
			// "where jb.firmid=ja.firmid(+) and jb.firmid=ja1.firmid(+)"
			// +
			// ") j," +
			"(select nvl(sum(nvl(value,0)),0) value,firmid  from f_clientledger where code in ('OtherItem','OtherItem_Z','OtherItem_V') and trunc(B_Date)=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') " + filter + " group by firmid) k, "
					+ "(select todaybalance value,firmid from f_firmbalance where b_date=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') "
					+ filter + ") g," + // --当日余额
					"(select account1,ACCOUNTNAME1,firmid from f_b_firmidandaccount where 1=1 and (account1 is not null) and account1<>'null' "
					+ filter2 + ") h " + // --和银行接口连接
					"where h.firmid=a.firmid(+) and h.firmid=b.firmid(+) and h.firmid=c.firmid(+) and h.firmid=d.firmid(+) and h.firmid=e.firmid(+) and h.firmid=f.firmid(+) and h.firmid=g.firmid(+) and h.firmid=i.firmid(+)"
					+ /*
						 * and
						 * h
						 * .
						 * firmid
						 * =
						 * j
						 * .
						 * firmid
						 * (
						 * +
						 * )
						 */" and h.firmid=k.firmid(+)";

			log("sql:" + sql);
			System.out.println("sql:" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				BatQsChild value = new BatQsChild();
				value.AddTranAmount = rs.getDouble("AddTranAmount");
				value.CutTranAmount = rs.getDouble("CutTranAmount");
				value.ProfitAmount = rs.getDouble("ProfitAmount");
				value.LossAmount = rs.getDouble("LossAmount");
				value.TranHandFee = rs.getDouble("TranHandFee");
				value.FreezeAmount = rs.getDouble("FreezeAmount");
				value.UnfreezeAmount = rs.getDouble("UnfreezeAmount");
				value.NewBalance = rs.getDouble("NewBalance");
				value.NewFreezeAmount = rs.getDouble("NewFreezeAmount");
				value.ThirdCustId = rs.getString("ThirdCustId");
				value.CustAcctId = rs.getString("CustAcctId");
				value.CustName = rs.getString("CustName");
				value.ThirdLogNo = this.getActionID() + "";
				value.TranDateTime = Tool.fmtDateTime(qdate);
				value.toDhykAmount = rs.getDouble("toDhykAmount");
				value.yeDhykAmount = rs.getDouble("yeDhykAmount");
				value.toQianAmount = 0;// rs.getDouble("toQianAmount");
				value.yeQianAmount = 0;// rs.getDouble("yeQianAmount");
				result.put(value.ThirdCustId, value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		return result;
	}

	/** 取某一天之前的最后一天 */
	public java.util.Date getlastDate(java.util.Date date, Connection conn) throws SQLException {
		Date result = null;
		String sql = "select max(B_Date) bdate from f_firmbalance t where to_char(B_Date,'yyyy-MM-dd')<'" + Tool.fmtDate(date) + "'";
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if (rs.next()) {
				result = rs.getDate("bdate");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		if (result == null) {
			result = Tool.strToDate("1900-01-01");
		}
		return result;
	}

	/**
	 * 查询某一天的签约信息 status 交易状态(1:开户 2:销户 3:待确认)
	 */
	public Vector<KXHfailChild> getBankOpen(String bankID, String[] firmIDs, int status, java.util.Date tradeDate)
			throws SQLException, ClassNotFoundException {
		log("查询某一天的签约信息  getBankOpen   时间：" + Tool.fmtDate(new java.util.Date()));
		Vector<KXHfailChild> result = new Vector<KXHfailChild>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = " where 1=1 ";
		if (bankID != null && bankID.trim().length() > 0) {
			filter += " and bankID='" + bankID.trim() + "' ";
		}
		if (firmIDs != null && firmIDs.length > 0) {
			String str = "('aa'";
			for (String firmID : firmIDs) {
				str += ",'" + firmID + "' ";
			}
			filter += " and firmID in" + str + ") ";
		}
		if (status > 0) {
			filter += " and status=" + status + " ";
		}
		if (tradeDate != null) {
			filter += " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd') ";
		}
		try {
			sql = "select * from f_b_firmkxh " + filter;
			System.out.println("sql:" + sql);
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				KXHfailChild value = new KXHfailChild();
				value.funID = rs.getString("funID");
				value.status = rs.getInt("status");
				value.account1 = rs.getString("account1");
				value.type = rs.getInt("type");
				value.account1Name = rs.getString("account1Name");
				value.firmID = rs.getString("firmID");
				value.tradeDate = rs.getDate("tradeDate");
				value.counterId = rs.getString("counterId");
				result.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	/** 删除相应的银行对账失败文件信息 */
	public int delFirmBalanceError(String bankID, String date) throws SQLException, ClassNotFoundException {
		log("删除相应的银行对账失败文件信息  delFirmBalanceError  时间：" + Tool.fmtDate(new java.util.Date()));
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		String filter = " where 1=1 ";
		if (bankID != null && bankID.trim().length() > 0) {
			filter += " and bankID='" + bankID.trim() + "'";
		}
		if (date != null && date.trim().length() > 0) {
			java.util.Date dd = Tool.getDateTime(date);
			filter += " and trunc(TRANDATETIME)=to_date('" + Tool.fmtDate(dd) + "','yyyy-MM-dd')";
		}
		try {
			sql = "delete F_B_FIRMBALANCEERROR " + filter;
			System.out.println("sql:" + sql);
			log("sql:" + sql);
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	// 建设银行日终对账信息
	/**
	 * 建设银行发送市场交易商资金变化量信息
	 * 
	 * @param bankID
	 *            银行编号
	 * @param firmIDs
	 *            要查询的交易商编号
	 * @param qdate
	 *            查询的日期
	 * @return Vector<FirmBalance>
	 */
	public Vector<FirmBalance> getFirmBalance(String bankID, String[] firmIDs, java.util.Date qdate) throws SQLException, Exception {
		Vector<FirmBalance> result = new Vector<FirmBalance>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = this.getConnection();
			java.util.Date yestody = getlastDate(qdate, conn);
			String filter_Fee = " and b_date=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') ";
			String filterd = " and cleardate=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd')";
			String filtery = " and cleardate=to_date('" + Tool.fmtDate(yestody) + "','yyyy-MM-dd')";
			String Bank_filter = "";
			if (bankID != null && bankID.trim().length() > 0) {
				Bank_filter += " and bankid='" + bankID.trim() + "' ";
			}
			if (firmIDs != null && firmIDs.length > 0) {
				String str = "0a0";
				for (String firmid : firmIDs) {
					if (firmid != null && firmid.trim().length() > 0) {
						str += "," + firmid;
					}
				}
				filter_Fee += " and firmid in (" + str + ") ";
				filterd += " and firmid in (" + str + ") ";
				filtery += " and firmid in (" + str + ") ";
				Bank_filter += " and firmid in (" + str + ") ";
			}
			String Fee_filter = filter_Fee;// 手续费条件
			// String Income_filter = filter;//销售收入条件
			// String Payout_filter = filter;//购货支出条件
			// String PLY_filter = filter;//盈利
			// String PLK_filter = filter;//亏损
			// String Margin_filter = filter;//保证金变化
			// String Balance_filter = filter;//可用资金
			// String InMoney_filter = filter;//交易商入金
			// String OutMoney_filter = filter;//交易商出金
			// sql =
			// "select Bank.firmid firmid,Bank.bankID bankid,Bank.account account,Bank.accountName accountName,Bank.cardType cardType,Bank.card
			// card,nvl(Fee.value,0) Fee,"
			// +
			// "nvl(Fee.value,0)+nvl(Income.value,0)-nvl(Payout.value,0)+nvl(PLY.value,0)-nvl(PLK.value,0)+nvl(Margin.value,0)+nvl(Balance.value,0)-nvl(InMoney.value,0)+nvl(OutMoney.value,0)+nvl(Other.value,0)
			// QYChangeMoney from "
			// +
			// "(" +
			// "select fc.firmid firmid,nvl(sum(nvl(fc.value,0)),0) value from f_clientledger fc where exists (select code from f_ledgerfield fl where
			// code like '%Fee%' and fc.code=fl.code ) "+Fee_filter+" group by firmid"
			// +
			// ") Fee," +//交易商手续费
			// "(" +
			// "select fc.firmid firmid,nvl(sum(nvl(fc.value,0)),0) value from f_clientledger fc where exists (select code from f_ledgerfield fl where
			// code like '%Income%' and fc.code=fl.code ) "+Income_filter+" group by firmid"
			// +
			// ") Income," +//销售收入
			// "(" +
			// "select fc.firmid firmid,nvl(sum(nvl(fc.value,0)),0) value from f_clientledger fc where exists (select code from f_ledgerfield fl where
			// code like '%Payout%' and fc.code=fl.code ) "+Payout_filter+" group by firmid"
			// +
			// ") Payout," +//购货支出
			// "(" +
			// "select fc.firmid firmid,nvl(sum(nvl(fc.value,0)),0) value from f_clientledger fc where value>0 and exists (select code from
			// f_ledgerfield fl where code like '%PL%' and fc.code=fl.code ) "+PLY_filter+" group by firmid"
			// +
			// ") PLY," +//盈利金额
			// "(" +
			// "select fc.firmid firmid,nvl(sum(nvl(fc.value,0)),0) value from f_clientledger fc where value<0 and exists (select code from
			// f_ledgerfield fl where code like '%PL%' and fc.code=fl.code ) "+PLK_filter+" group by firmid"
			// +
			// ") PLK," +//亏损金额
			// "(" +
			// "select fc.firmid firmid,sum(case when (code='MarginBack_Z' or code='MarginBack_V') then -fc.value else fc.value end) value from
			// f_clientledger fc where exists (select fl.code from f_ledgerfield fl where code like '%Margin%' and fc.code=fl.code) "+Margin_filter+"
			// group by fc.firmid"
			// +
			// ") Margin," +//保证金变化
			// "(" +
			// "select firmid,nvl(todaybalance-lastbalance,0) value from f_firmbalance where 1=1 "+Balance_filter+""
			// +
			// ") Balance," +//交易商余额
			// "(" +
			// "select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Deposit' "+InMoney_filter+" group by firmid "
			// +
			// ") InMoney," +//交易商入金
			// "(" +
			// "select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Fetch' "+OutMoney_filter+" group by firmid "
			// +
			// ") OutMoney," +//交易商出金
			// "(" +
			// "select Bank.firmid
			// firmid,nvl(JSD.value,0)-nvl(JSY.value,0)+nvl(YQD.value,0)-nvl(YQY.value,0)+nvl(FLD.value,0)-nvl(FLY.value,0)+nvl(FTD.value,0)-nvl(FTY.value,0)
			// value from "
			// +
			// "(" +
			// "select firmid,nvl(RuntimeSettleMargin,0) value from t_h_firm where 1=1 "+filterd+""
			// +
			// ") JSD," +//当日交收保证金
			// "(" +
			// "select firmid,nvl(RuntimeSettleMargin,0) value from t_h_firm where 1=1 "+filtery+""
			// +
			// ") JSY," +//上日交收保证金
			// "(" +
			// "select firmid,nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0) value from t_h_firm where 1=1 "+filterd+""
			// +
			// ") YQD," +//当日远期保证金
			// "(" +
			// "select firmid,nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0) value from t_h_firm where 1=1 "+filtery+""
			// +
			// ") YQY," +//上日远期保证金
			// "(" +
			// "select firmid,RuntimeFL value from t_h_firm where 1=1 "+filterd+""
			// +
			// ") FLD," +//当日浮亏
			// "(" +
			// "select firmid,RuntimeFL value from t_h_firm where 1=1 "+filtery+""
			// +
			// ") FLY," +//上日浮亏
			// "(" +
			// "select firmid,sum(nvl(floatingloss,0)) value from t_h_firmholdsum where 1=1 "+filterd+" group by firmid"
			// +
			// ") FTD," +//当日浮动盈亏
			// "(" +
			// "select firmid,sum(nvl(floatingloss,0)) value from t_h_firmholdsum where 1=1 "+filtery+" group by firmid"
			// +
			// ") FTY," +//上日浮动盈亏
			// "(" +
			// "select firmid,bankID,isopen from f_b_firmidandaccount where 1=1 and isopen=1 "
			// + Bank_filter +
			// ") Bank " +
			// " where Bank.firmid=JSD.firmid(+) and Bank.firmid=JSY.firmid(+) and Bank.firmid=YQD.firmid(+) and Bank.firmid=YQY.firmid(+) "
			// +
			// "and Bank.firmid=FLD.firmid(+) and Bank.firmid=FLY.firmid(+) and Bank.firmid=FTD.firmid(+) and Bank.firmid=FTY.firmid(+) "
			// +
			// ") Other," +
			// "(" +
			// "select firmid,bankID,account,accountName,cardType,card,isopen,status from f_b_firmidandaccount where 1=1 and isopen=1 "
			// + Bank_filter +
			// ") Bank " +
			// " where Bank.firmid=Fee.firmid(+) and Bank.firmid=Income.firmid(+) and Bank.firmid=Payout.firmid(+) "
			// +
			// "and Bank.firmid=PLY.firmid(+) and Bank.firmid=PLK.firmid(+) and Bank.firmid=Margin.firmid(+) "
			// +
			// "and Bank.firmid=Balance.firmid(+) and Bank.firmid=InMoney.firmid(+) and Bank.firmid=OutMoney.firmid(+) "
			// +
			// "and Bank.firmid=Other.firmid(+) ";

			/***************
			 * 新清算公式规则 lipj 2011.09.13********* // * 当日可用资金变化量 = 当日可用 + 手续费 + 出金
			 * - 入金 - 上日可用
			 *****/
			String filter = " ";

			String filter_f_fb = " ";
			String filter_z_v = " ";
			String filter_t_h_f = " ";

			String filter_f_fb_today = " ";
			String filter_z_v_today = " ";
			String filter_t_h_f_today = " ";

			if (bankID != null && !"".equals(bankID.trim())) {
				filter = filter + " and i.bankId='" + bankID.trim() + "' and i.firmid not like '%D'";
			}
			String firmid = null;
			if (firmid != null && !"".equals(firmid.trim())) {
				filter = filter + " and i.firmid='" + firmid + "' ";
				filter_f_fb = filter_f_fb + " and firmid='" + firmid + "' ";
				filter_t_h_f = filter_t_h_f + " and firmid='" + firmid + "' ";
				filter_z_v = filter_z_v + " and firmid='" + firmid + "' ";

				filter_f_fb_today = filter_f_fb_today + " and i.firmid='" + firmid + "' ";
				filter_z_v_today = filter_z_v_today + " and i.firmid='" + firmid + "' ";
				filter_t_h_f_today = filter_t_h_f_today + " and i.firmid='" + firmid + "' ";
			}
			java.util.Date yes_qdate = getlastDate(qdate, conn);
			if (qdate != null) {
				filter_f_fb = filter_f_fb + " and b_date = to_date('" + Tool.fmtDate(yes_qdate) + "', 'yyyy-MM-dd') ";
				filter_t_h_f = filter_t_h_f + " and cleardate = to_date('" + Tool.fmtDate(yes_qdate) + "', 'yyyy-MM-dd') ";
				filter_z_v = filter_z_v + " and b_date = to_date('" + Tool.fmtDate(yes_qdate) + "', 'yyyy-MM-dd') ";

				filter_f_fb_today = filter_f_fb_today + " and b_date = to_date('" + Tool.fmtDate(qdate) + "', 'yyyy-MM-dd') ";
				filter_t_h_f_today = filter_t_h_f_today + " and cleardate = to_date('" + Tool.fmtDate(qdate) + "', 'yyyy-MM-dd') ";
				filter_z_v_today = filter_z_v_today + " and b_date = to_date('" + Tool.fmtDate(qdate) + "', 'yyyy-MM-dd') ";
			}

			if ("qy".equalsIgnoreCase(Tool.getConfig("QSMode"))) {
				sql = "select i.firmid , " + "i.bankid, " + "i.account, " + "i.accountname, " + "i.cardtype, " + "i.card, "
						+ "nvl(a.todaybalance, 0) + nvl(b.RuntimeMargin, 0) + nvl(b.RuntimeFL, 0) + nvl(b.RuntimeSettleMargin, 0) + nvl(c.floatingloss, 0) + nvl(d.money, 0)+ "
						+ "nvl(e.money, 0) money ," + "nvl(Fee.value,0) Fee ," + "nvl(InMoney.value, 0) - nvl(OutMoney.value, 0) crjsum,"
						+ "nvl(todayb.todaybalance, 0) qymoney," + "nvl(a.todaybalance, 0) yesqymoney,"
						+ "nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + nvl(d1.money, 0)+ "
						+ "nvl(e1.money, 0) + nvl(Fee.value,0) + nvl(OutMoney.value, 0) - nvl(InMoney.value, 0) - (nvl(a.todaybalance, 0) + nvl(b.RuntimeMargin, 0) + nvl(b.RuntimeFL, 0) + nvl(b.RuntimeSettleMargin, 0) + nvl(c.floatingloss, 0) + nvl(d.money, 0)+ "
						+ "nvl(e.money, 0)) QYChangeMoney " + "from " + "(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb
						+ ") a,"
						+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
						+ filter_t_h_f + ") b," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 "
						+ filter_t_h_f + " group by firmid) c,"
						+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
						+ filter_z_v + " group by firmid) d,"
						+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
						+ filter_z_v + " group by firmid) e,"
						+ "(select firmid, nvl(sum(value), 0) money from f_clientledger where code like 'Payout%' " + filter_f_fb
						+ " group by firmid) f," + "(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Deposit' "
						+ filter_Fee + " group by firmid " + ") InMoney," + // 交易商入金
						"(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Fetch' " + filter_Fee + " group by firmid "
						+ ") OutMoney," + // 交易商出金
						"(select firmid,bankid,accountname,account,isopen,cardType, card from f_b_Firmidandaccount) i ,"
						+ "(select fc.firmid firmid,nvl(sum(nvl((case when value <0 then 0 else value end), 0)), 0) value from f_clientledger fc where exists (select code from f_ledgerfield fl where code like '%Fee%' and fc.code=fl.code ) "
						+ Fee_filter + " group by firmid" + ") Fee," + // 交易商手续费
						"(select firmid, todaybalance from f_firmbalance where 1 = 1 and b_date = to_date('" + Tool.fmtDate(qdate)
						+ "', 'yyyy-MM-dd')) todayb ," + // 当日可用
						"(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb_today + ") a1,"
						+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
						+ filter_t_h_f_today + ") b1," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 "
						+ filter_t_h_f_today + " group by firmid) c1,"
						+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
						+ filter_z_v_today + " group by firmid) d1,"
						+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
						+ filter_z_v_today + " group by firmid) e1 " + "where " + "i.firmid = a.firmid(+) " + "and i.firmid = b.firmid(+) "
						+ "and i.firmid = c.firmid(+) " + "and i.firmid = d.firmid(+) " + "and i.firmid = e.firmid(+) "
						+ "and i.firmid = f.firmid(+) " + "and i.firmid = OutMoney.firmid(+) " + "and i.firmid = InMoney.firmid(+) "
						+ "and i.firmid = FEE.firmid(+) " + "and i.firmid = a1.firmid(+) " + "and i.firmid = b1.firmid(+) "
						+ "and i.firmid = c1.firmid(+) " + "and i.firmid = d1.firmid(+) " + "and i.firmid = e1.firmid(+) "
						+ "and i.firmid = todayb.firmid(+) " + "and i.isopen = '1' " + filter + " order by a.firmid";
			} else {
				sql = "select i.firmid , " + "i.bankid, " + "i.account, " + "i.accountname, " + "i.cardtype, " + "i.card, "
						+ "nvl(a.todaybalance, 0) + nvl(b.RuntimeMargin, 0) + nvl(b.RuntimeFL, 0) + nvl(b.RuntimeSettleMargin, 0) + nvl(c.floatingloss, 0) + nvl(d.money, 0)+ nvl(e.money, 0) money ,"
						+ "nvl(Fee.value,0) Fee ," + "nvl(InMoney.value, 0) - nvl(OutMoney.value, 0) crjsum," + "nvl(todayb.todaybalance, 0) qymoney,"
						+ "nvl(a.todaybalance, 0) yesqymoney,"
						+ "nvl(todayb.todaybalance, 0) + nvl(Fee.value,0) + nvl(OutMoney.value, 0) - nvl(InMoney.value, 0) - nvl(a.todaybalance, 0) QYChangeMoney "
						+ "from " + "(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb + ") a,"
						+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
						+ filter_t_h_f + ") b," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 "
						+ filter_t_h_f + " group by firmid) c,"
						+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
						+ filter_z_v + " group by firmid) d,"
						+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
						+ filter_z_v + " group by firmid) e,"
						+ "(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Deposit' " + filter_Fee
						+ " group by firmid " + ") InMoney," + // 交易商入金
						"(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Fetch' " + filter_Fee + " group by firmid "
						+ ") OutMoney," + // 交易商出金
						"(select firmid,bankid,accountname,account,isopen,cardType, card from f_b_Firmidandaccount) i ," +
						// "(select fc.firmid firmid,nvl(sum(nvl(fc.value,0)),0) value from f_clientledger fc where exists (select code from
						// f_ledgerfield fl where code like '%Fee%' and fc.code=fl.code ) " + Fee_filter + " group by firmid" + ") Fee," + // 交易商手续费
						"(select fc.firmid firmid,nvl(sum(nvl((case when value <0 then 0 else value end), 0)), 0) value from f_clientledger fc where exists (select code from f_ledgerfield fl where code like '%Fee%' and fc.code=fl.code ) "
						+ Fee_filter + " group by firmid" + ") Fee," + // 交易商手续费
						"(select firmid, todaybalance from f_firmbalance where 1 = 1 and b_date = to_date('" + Tool.fmtDate(qdate)
						+ "', 'yyyy-MM-dd')) todayb " + // 当日可用
						"where " + "i.firmid = a.firmid(+) " + "and i.firmid = b.firmid(+) " + "and i.firmid = c.firmid(+) "
						+ "and i.firmid = d.firmid(+) " + "and i.firmid = e.firmid(+) " + "and i.firmid = OutMoney.firmid(+) "
						+ "and i.firmid = InMoney.firmid(+) " + "and i.firmid = FEE.firmid(+) " + "and i.firmid = todayb.firmid(+) "
						+ "and i.isopen = '1' " + filter + " order by a.firmid";
			}
			/****************************
			 * 当日资金变化量 = 当日权益 + 手续费 + 出金 - 入金 - 上日权益
			 *****/
			/**
			 * 2012.02.01 因为宁波大宗问题（由于给宁波大宗上的处理器版本是发送可用资金变化量的版本，
			 * 市场结算人员，每日用交易商当前资金和银行网银上显示的余额对比，都能对比的上，
			 * 2012.01.31按照权益调账之后，市场反应说帐不对，要按照可用资金变化量调）银行确认说，
			 * 之后要把市场日终发送交易商权益变化量改成发送可用资金变化量。 所以恢复上一版本清算公式，注释掉此版本。
			 */
			// String filter = " ";
			// String filter_f_fb = " ";
			// String filter_z_v = " ";
			// String filter_t_h_f = " ";
			//
			// String filter_f_fb_today = " ";
			// String filter_z_v_today = " ";
			// String filter_t_h_f_today = " ";
			// if(bankID != null && !"".equals(bankID.trim())){
			// filter = filter +
			// " and i.bankId='"+bankID.trim()+"' and i.firmid not like '%D'";
			// }
			// String firmid = null;
			// if(firmid!=null && !"".equals(firmid.trim())) {
			// filter = filter + " and i.firmid='"+firmid+"' ";
			// filter_f_fb = filter_f_fb + " and firmid='"+firmid+"' ";
			// filter_t_h_f = filter_t_h_f + " and firmid='"+firmid+"' ";
			// filter_z_v =filter_z_v + " and firmid='"+firmid+"' ";
			//
			// filter_f_fb_today = filter_f_fb + " and firmid='"+firmid+"' ";
			// filter_t_h_f_today = filter_t_h_f + " and firmid='"+firmid+"' ";
			// filter_z_v_today =filter_z_v + " and firmid='"+firmid+"' ";
			// }
			// java.util.Date yes_qdate = getlastDate(qdate,conn);
			// if(qdate!=null) {
			// filter_f_fb = filter_f_fb +
			// " and b_date = to_date('"+Tool.fmtDate(yes_qdate)+"', 'yyyy-MM-dd') ";
			// filter_t_h_f = filter_t_h_f +
			// " and cleardate = to_date('"+Tool.fmtDate(yes_qdate)+"', 'yyyy-MM-dd') ";
			// filter_z_v = filter_z_v +
			// " and b_date <= to_date('"+Tool.fmtDate(yes_qdate)+"', 'yyyy-MM-dd') ";
			//
			// filter_f_fb_today = filter_f_fb_today +
			// " and b_date = to_date('"+Tool.fmtDate(qdate)+"', 'yyyy-MM-dd') ";
			// filter_t_h_f_today = filter_t_h_f_today +
			// " and cleardate = to_date('"+Tool.fmtDate(qdate)+"', 'yyyy-MM-dd') ";
			// filter_z_v_today = filter_z_v_today +
			// " and b_date <= to_date('"+Tool.fmtDate(qdate)+"', 'yyyy-MM-dd') ";
			// }
			//
			//
			// sql = "select i.firmid , "+
			// "i.bankid, "+
			// "i.account, "+
			// "i.accountname, "+
			// "i.cardtype, "+
			// "i.card, "+
			// "nvl(a.todaybalance, 0) + "+
			// "nvl(b.RuntimeMargin, 0) + nvl(b.RuntimeFL, 0) + "+
			// "nvl(b.RuntimeSettleMargin, 0) + nvl(c.floatingloss, 0) + "+
			// "nvl(d.money, 0)+ nvl(e.money, 0) money ,"+
			// "nvl(Fee.value,0) Fee ,"+
			// "nvl(OutMoney.value, 0) ,"+
			// "nvl(InMoney.value, 0) ,"+
			// "nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) +
			// nvl(d1.money, 0)+ nvl(e1.money, 0) -"
			// +
			// "nvl(a.todaybalance, 0) - nvl(b.RuntimeMargin, 0) - nvl(b.RuntimeFL, 0) - nvl(b.RuntimeSettleMargin, 0) - nvl(c.floatingloss, 0) -
			// nvl(d.money, 0)- nvl(e.money, 0) +"
			// +
			// "nvl(Fee.value,0) + nvl(OutMoney.value, 0) - nvl(InMoney.value, 0) QYChangeMoney "+
			// "from "+
			// "(select firmid, todaybalance from f_firmbalance where 1 = 1 "+filter_f_fb+") a,"+
			// "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1
			// "+filter_t_h_f+") b,"+
			// "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 "+filter_t_h_f+" group by firmid) c,"+
			// "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from
			// f_clientledger where code in ('MarginBack_V', 'Margin_V') "+filter_z_v+" group by firmid) d,"+
			// "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from
			// f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "+filter_z_v+" group by firmid) e,"+
			//
			// "(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Deposit' "+filter_Fee+" group by firmid "
			// +
			// ") InMoney," +//交易商入金
			// "(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Fetch' "+filter_Fee+" group by firmid "
			// +
			// ") OutMoney," +//交易商出金
			// "(select firmid,bankid,accountname,account,isopen,cardType, card from f_b_Firmidandaccount) i ,"+//交易商信息
			// "(select fc.firmid firmid,nvl(sum(nvl(fc.value,0)),0) value from f_clientledger fc where exists (select code from f_ledgerfield fl
			// where code like '%Fee%' and fc.code=fl.code ) "+Fee_filter+" group by firmid"
			// +
			// ") Fee," +//交易商手续费
			//
			// "(select firmid, todaybalance from f_firmbalance where 1 = 1 "+filter_f_fb_today+") a1,"+
			// "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1
			// "+filter_t_h_f_today+") b1,"+
			// "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 "+filter_t_h_f_today+" group by firmid) c1,"+
			// "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from
			// f_clientledger where code in ('MarginBack_V', 'Margin_V') "+filter_z_v_today+" group by firmid) d1,"+
			// "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from
			// f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "+filter_z_v_today+" group by firmid) e1 "+
			// "where "+
			// "i.firmid = a.firmid(+) "+
			// "and i.firmid = b.firmid(+) "+
			// "and i.firmid = c.firmid(+) "+
			// "and i.firmid = d.firmid(+) "+
			// "and i.firmid = e.firmid(+) "+
			// "and i.firmid = OutMoney.firmid(+) "+
			// "and i.firmid = InMoney.firmid(+) "+
			// "and i.firmid = FEE.firmid(+) "+
			// "and i.firmid = a1.firmid(+) "+
			// "and i.firmid = b1.firmid(+) "+
			// "and i.firmid = c1.firmid(+) "+
			// "and i.firmid = d1.firmid(+) "+
			// "and i.firmid = e1.firmid(+) "+
			// "and i.isopen = '1' "+
			// filter+
			// " order by a.firmid";
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmBalance fb = new FirmBalance();
				fb.firmID = rs.getString("firmid");
				fb.bankID = rs.getString("bankid");
				fb.account = rs.getString("account");
				fb.accountName = rs.getString("accountName");
				fb.cardType = rs.getString("cardType");
				fb.card = rs.getString("card");
				fb.FeeMoney = rs.getDouble("Fee");
				fb.QYChangeMoney = rs.getDouble("QYChangeMoney");
				fb.date = qdate;
				fb.QYMoney = rs.getDouble("qymoney");
				fb.yesQYMoney = rs.getDouble("yesqymoney");
				fb.CRJSum = rs.getDouble("crjsum");
				result.add(fb);
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	/**
	 * 保存银行返回的错误信息到数据库中
	 */
	public long addQSResult(QSRresult qsResult, Connection conn) throws SQLException {
		long result = 0;
		String sql = null;
		PreparedStatement state = null;
		try {
			sql = "insert into F_B_QSResult (resultID,bankID,bankNAme,firmID,firmName,account,account1,kyMoneyM,kyMoneyB,djMoneyM,djMoneyB,zckyMoney,zcdjMoney,moneyM,moneyB,zcMoney,createDate,tradeDate,status,note) "
					+ "values(SEQ_F_B_QSRESULT.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, qsResult.bankID);
			state.setString(2, qsResult.bankName);
			state.setString(3, qsResult.firmID);
			state.setString(4, qsResult.firmName);
			state.setString(5, qsResult.account);
			state.setString(6, qsResult.account1);
			state.setDouble(7, qsResult.kyMoneyM);
			state.setDouble(8, qsResult.kyMoneyB);
			state.setDouble(9, qsResult.djMoneyM);
			state.setDouble(10, qsResult.djMoneyB);
			state.setDouble(11, qsResult.zckyMoney);
			state.setDouble(12, qsResult.zcdjMoney);
			state.setDouble(13, qsResult.moneyM);
			state.setDouble(14, qsResult.moneyB);
			state.setDouble(15, qsResult.zcMoney);
			state.setDate(16, new java.sql.Date(qsResult.tradeDate.getTime()));
			state.setInt(17, qsResult.status);
			state.setString(18, qsResult.note);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 删除银行错误信息
	 */
	public long delQSResult(String bankID, java.util.Date tradeDate, Connection conn) throws SQLException {
		long result = 0;
		String sql = null;
		PreparedStatement state = null;
		String filter = "";
		if (bankID != null && bankID.trim().length() > 0) {
			filter += " and bankID='" + bankID.trim() + "' ";
		}
		if (tradeDate != null) {
			filter += " and to_char(tradeDate,'yyyy-MM-dd')='" + Tool.fmtDate(tradeDate) + "'";
		}
		try {
			sql = "delete F_B_QSResult where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 查询银行错误信息表
	 */
	public Vector<QSRresult> getQSList(String bankID, String[] firmIDs, int status, java.util.Date tradeDate)
			throws SQLException, ClassNotFoundException {
		Vector<QSRresult> result = new Vector<QSRresult>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = "";
		if (bankID != null && bankID.trim().length() > 0) {
			filter += " and bankID='" + bankID.trim() + "'";
		}
		if (firmIDs != null && firmIDs.length > 0) {
			String firms = "";
			for (String firmID : firmIDs) {
				if (firmID != null && firmID.trim().length() > 0) {
					if (firms.trim().length() == 0) {
						firms += firmID;
					} else {
						firms += "," + firmID;
					}
				}
			}
			if (firms.trim().length() > 0) {
				firms = " (" + firms + ") ";
				filter += " and firmID in " + firms;
			}
		}
		if (status >= 0) {
			filter += " and status=" + status + " ";
		}
		if (tradeDate != null) {
			filter += " and to_char(tradeDate,'yyyy-MM-dd')='" + Tool.fmtDate(tradeDate) + "' ";
		}
		try {
			conn = this.getConnection();
			sql = "select * from F_B_QSResult where 1=1 " + filter;
			this.log("sql: " + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				QSRresult value = new QSRresult();
				value.bankID = rs.getString("bankID");
				value.bankName = rs.getString("bankName");
				value.firmID = rs.getString("firmID");
				value.firmName = rs.getString("firmName");
				value.account = rs.getString("account");
				value.account1 = rs.getString("account1");
				value.kyMoneyM = rs.getDouble("kyMoneyM");
				value.kyMoneyB = rs.getDouble("kyMoneyB");
				value.djMoneyM = rs.getDouble("djMoneyM");
				value.djMoneyB = rs.getDouble("djMoneyB");
				value.zckyMoney = rs.getDouble("zckyMoney");
				value.zcdjMoney = rs.getDouble("zcdjMoney");
				value.moneyM = rs.getDouble("moneyM");
				value.moneyB = rs.getDouble("moneyB");
				value.zcMoney = rs.getDouble("zcMoney");
				value.createDate = rs.getDate("createDate");
				value.tradeDate = rs.getDate("tradeDate");
				value.status = rs.getInt("status");
				value.note = rs.getString("note");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	// ******************************************华夏银行订制信息************************************************************
	/**
	 * 华夏银行清算对账信息查询
	 * 
	 * @param bankID
	 *            交易商代码
	 * @param firmIDs
	 *            交易商代码集合
	 * @param tradeDate
	 *            交易日期
	 * @return Vector<HXSentQSMsgValue>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Vector<HXSentQSMsgValue> getHXQSMsg(String bankID, String[] firmIDs, java.util.Date tradeDate)
			throws SQLException, ClassNotFoundException {
		Vector<HXSentQSMsgValue> result = new Vector<HXSentQSMsgValue>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String bankfilter = "";
		String bhfilter = "";
		String djfilter = "";
		String djfiltery = "";
		String marginfilter = "";
		conn = this.getConnection();
		Date yestoday = this.getMaxBankQSList(bankID, tradeDate, conn);
		if (bankID != null && bankID.trim().length() > 0) {
			bankfilter += " and bankid='" + bankID.trim() + "'";
		}
		if (firmIDs != null && firmIDs.length > 0) {
			String firms = "'aa'";
			for (String firmID : firmIDs) {
				if (firmID != null && firmID.trim().length() > 0) {
					firms += ",'" + firmID.trim() + "'";
				}
			}
			firms = "(" + firms + ")";
			bankfilter += " and firmid in " + firms;
			bhfilter += " and firmid in " + firms;
			djfilter += " and firmid in " + firms;
			djfiltery += " and firmid in " + firms;
			marginfilter += " and firmid in " + firms;
		}
		if (tradeDate != null) {
			bhfilter += " and trunc(b_date)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
			djfilter += " and trunc(cleardate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
			djfiltery += " and trunc(cleardate)=to_date('" + Tool.fmtDate(yestoday) + "','yyyy-MM-dd')";
			marginfilter += " and trunc(b_date)<=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
		}
		try {
			conn = this.getConnection();
			sql = "select bank.*,nvl(rsm.value,0) rsm,nvl(rm.value,0) rm,nvl(Margin.value,0)-nvl(MarginBack.value,0) Margin,nvl(rfl.value,0) rfl,nvl(fl.value,0) fl,"
					+ "(nvl(rsm.value,0)-nvl(rsmy.value,0)+nvl(rm.value,0)-nvl(rmy.value,0)+nvl(Marginch.value,0)-nvl(MarginBackch.value,0)+nvl(rfl.value,0)-nvl(rfly.value,0)"
					+ "+nvl(fl.value,0)-nvl(fly.value,0)+nvl(balance.todaybalance,0)-nvl(balance.lastbalance,0)-nvl(inmoney.value,0)+nvl(outmoney.value,0)) balanceChange,"
					+ "(nvl(rsm.value,0)+nvl(rm.value,0)+nvl(Margin.value,0)-nvl(MarginBack.value,0)+nvl(rfl.value,0)+nvl(fl.value,0)) frozen,"
					+ "nvl(balance.todaybalance,0) balance,nvl(balance.lastbalance,0) lastbalance,nvl(inmoney.value,0) inmoney,nvl(outmoney.value,0) outmoney,"
					+ "(nvl(rsm.value,0)+nvl(rm.value,0)+nvl(Margin.value,0)-nvl(MarginBack.value,0)+nvl(rfl.value,0)+nvl(fl.value,0)+nvl(balance.todaybalance,0)) quanyi "
					+ "from " + "(select sum(nvl(RuntimeSettleMargin,0)) value,firmid from t_h_firm where 1=1 " + djfilter + " group by firmid) rsm, "
					+ // --当日交收保证金
					"(select sum(nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0)) value,firmid from t_h_firm where 1=1 " + djfilter
					+ " group by firmid) rm, " + // --当日远期保证金

			"(select sum(nvl(RuntimeSettleMargin,0)) value,firmid from t_h_firm where 1=1 " + djfiltery + " group by firmid) rsmy, " + // --上日交收保证金
					"(select sum(nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0)) value,firmid from t_h_firm where 1=1 " + djfiltery
					+ " group by firmid) rmy, " + // --上日远期保证金
					"(select sum(value) value,firmid from f_clientledger where code like '%Income%' " + bhfilter + " group by firmid) income," + // 收货款
					"(select sum(value) value,firmid from f_clientledger where code like '%Payout%' " + bhfilter + " group by firmid) payout," + // 付货款
					"(select sum(nvl(value,0)) value,firmid from f_clientledger fc where code in ('Margin_V','Margin_Z') " + marginfilter
					+ " group by firmid) Margin, " + // --收取现货竞价保证金
					"(select sum(nvl(value,0)) value,firmid from f_clientledger fc where code in ('MarginBack_V','MarginBack_Z') " + marginfilter
					+ " group by firmid) MarginBack, " + // --返还现货竞价保证金
					"(select sum(nvl(value,0)) value,firmid from f_clientledger fc where code in ('Margin_V','Margin_Z') " + bhfilter
					+ " group by firmid) Marginch, " + // --收取现货竞价保证金
					"(select sum(nvl(value,0)) value,firmid from f_clientledger fc where code in ('MarginBack_V','MarginBack_Z') " + bhfilter
					+ " group by firmid) MarginBackch, " + // --返还现货竞价保证金
					"(select sum(nvl(RuntimeFL,0)) value,firmid from t_h_firm where 1=1 " + djfilter + " group by firmid) rfl, " + // --当日浮亏
					"(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where 1=1 " + djfilter + " group by firmid) fl, " + // --浮动盈亏
					"(select sum(nvl(RuntimeFL,0)) value,firmid from t_h_firm where 1=1 " + djfiltery + " group by firmid) rfly, " + // --上日浮亏
					"(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where 1=1 " + djfiltery + " group by firmid) fly, " + // --上日浮动盈亏
					"(select lastbalance,todaybalance,firmid from f_firmbalance ff where 1=1 " + bhfilter + ") balance, " + // --可用资金
					"(select sum(nvl(value,0)) value,firmid from f_clientledger fc where code='Deposit' " + bhfilter + " group by firmid) inmoney, " + // --入金
					"(select sum(nvl(value,0)) value,firmid from f_clientledger fc where code='Fetch' " + bhfilter + " group by firmid) outmoney, " + // --出金
					"(select * from f_b_firmidandaccount where isopen=1 " + bankfilter + ") bank "
					+ "where bank.firmid=rsm.firmid(+) and bank.firmid=rm.firmid(+) and bank.firmid=Margin.firmid(+) and bank.firmid=MarginBack.firmid(+) "
					+ " and bank.firmid=income.firmid(+) and bank.firmid=payout.firmid(+) and bank.firmid=rsmy.firmid(+) and bank.firmid=rmy.firmid(+)  "
					+ "and bank.firmid=rfl.firmid(+) and bank.firmid=fl.firmid(+) and bank.firmid=balance.firmid(+) and bank.firmid=inmoney.firmid(+) "
					+ "and bank.firmid=outmoney.firmid(+) and bank.firmid=Marginch.firmid(+) and bank.firmid=MarginBackch.firmid(+) and bank.firmid=rfly.firmid(+) "
					+ " and bank.firmid=fly.firmid(+) ";
			System.out.println("sql:" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				HXSentQSMsgValue value = new HXSentQSMsgValue();
				value.firmID = rs.getString("firmID");
				value.bankID = rs.getString("bankID");
				value.bankName = rs.getString("bankName");
				value.account = rs.getString("account");
				value.accountName = rs.getString("accountName");
				value.account1 = rs.getString("account1");
				value.accountName1 = rs.getString("accountName1");
				value.card = rs.getString("card");
				value.cardType = rs.getString("cardType");
				value.isopen = rs.getInt("isopen");
				value.status = rs.getInt("status");

				value.rsm = rs.getDouble("rsm");
				value.rm = rs.getDouble("rm");
				value.margin = rs.getDouble("margin");
				value.rfl = rs.getDouble("rfl");
				value.fl = rs.getDouble("fl");
				value.todaybalance = rs.getDouble("balance");
				value.lastbalance = rs.getDouble("lastbalance");
				value.inmoney = rs.getDouble("inmoney");
				value.outmoney = rs.getDouble("outmoney");
				value.qs = new QSMessage();
				value.qs.balanceChanges = rs.getDouble("balanceChange");
				value.qs.frozenFunds = rs.getDouble("frozen");
				value.dz = new DZMessage();
				value.dz.todaybalance = rs.getDouble("balance");
				value.dz.rights = rs.getDouble("quanyi");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	/**
	 * 保存银行清算失败信息
	 * 
	 * @param qs
	 *            清算信息
	 * @param conn
	 *            数据库连接
	 * @return int 插入信息条数
	 * @throws SQLException
	 */
	public int addQSError(QSChangeResult qs, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_HXQS (BANKID,FIRMID,TRADEDATE,MONEY,TYPE,TRADETYPE,NOTE,CREATEDATE,STATUS) values (?,?,?,?,?,?,?,sysdate,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, qs.bankID);
			state.setString(2, qs.firmID);
			state.setDate(3, (qs.tradeDate == null ? null : new java.sql.Date(qs.tradeDate.getTime())));
			state.setDouble(4, qs.money);
			state.setInt(5, qs.type);
			state.setInt(6, qs.tradeType);
			state.setString(7, qs.note);
			state.setInt(8, qs.status);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 查询银行清算失败信息
	 * 
	 * @parm filter 查询条件
	 * @parm conn 数据库连接
	 * @return Vector<QSChangeResult>
	 * @throws SQLException
	 */
	public Vector<QSChangeResult> getQSError(String filter, Connection conn) throws SQLException {
		Vector<QSChangeResult> result = new Vector<QSChangeResult>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select * from F_B_HXQS where 1=1 " + filter;
			System.out.println("sql:" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				QSChangeResult value = new QSChangeResult();
				value.bankID = rs.getString("bankID");
				value.firmID = rs.getString("firmID");
				value.tradeDate = rs.getDate("tradeDate");
				value.money = rs.getDouble("money");
				value.type = rs.getInt("type");
				value.tradeType = rs.getInt("tradeType");
				value.note = rs.getString("note");
				value.createDate = rs.getDate("createDate");
				value.status = rs.getInt("status");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		return result;
	}

	/**
	 * 修改银行清算失败信息
	 * 
	 * @parm qs 清算信息
	 * @param conn
	 *            数据库连接
	 * @return int 修改条数
	 * @throws SQLException
	 */
	public int modQSError(QSChangeResult qs, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter = " and money=" + Tool.fmtDouble2(qs.money) + " ";
		if (qs.type != 0) {
			filter += " and type=" + qs.type + " ";
		}
		if (qs.tradeType != 0) {
			filter += " and tradeType=" + qs.tradeType + " ";
		}
		if (qs.bankID != null && qs.bankID.trim().length() > 0) {
			filter += " and bankID='" + qs.bankID.trim() + "' ";
		}
		if (qs.firmID != null && qs.firmID.trim().length() > 0) {
			filter += " and firmID='" + qs.firmID.trim() + "' ";
		}
		if (qs.tradeDate != null) {
			filter += " and trunc(tradeDate)=to_date('" + Tool.fmtDate(qs.tradeDate) + "','yyyy-MM-dd') ";
		}
		try {
			sql = "update F_B_HXQS set note=" + (qs.note == null ? "'note'" : qs.note) + ",status=" + qs.status + " where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 删除银行清算失败信息
	 * 
	 * @parm qs 清算信息
	 */
	public int delQSError(QSChangeResult qs, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter = " ";
		if (qs.type != 0) {
			filter += " and type=" + qs.type + " ";
		}
		if (qs.tradeType != 0) {
			filter += " and tradeType=" + qs.tradeType + " ";
		}
		if (qs.bankID != null && qs.bankID.trim().length() > 0) {
			filter += " and bankID='" + qs.bankID.trim() + "' ";
		}
		if (qs.firmID != null && qs.firmID.trim().length() > 0) {
			filter += " and firmID='" + qs.firmID.trim() + "' ";
		}
		if (qs.tradeDate != null) {
			filter += " and trunc(tradeDate)=to_date('" + Tool.fmtDate(qs.tradeDate) + "','yyyy-MM-dd') ";
		}
		try {
			sql = "delete F_B_HXQS where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	// **********************************浦发银行清算方式**********************************************************
	/**
	 * 提取保存改变交易商权益方法
	 * 
	 * @param bankID
	 *            银行编号
	 * @param firmIDs
	 *            交易商代码集合
	 * @param tradeDate
	 *            交易日期
	 * @param conn
	 *            数据库连接
	 * @return int 添加条数
	 * @throws SQLException
	 */
	public int addChangeFirmRights(String bankID, String[] firmIDs, java.util.Date tradeDate, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter1 = "";
		String filter2 = "";
		String filter21 = "";
		String bankfilter = "";
		if (bankID != null && bankID.trim().length() > 0) {// 如果有银行编号限制
			bankfilter += " and bankid='" + bankID.trim() + "'";
		}
		if (firmIDs != null && firmIDs.length > 0) {// 如果有交易商代码限制
			String firms = "'aa'";
			for (String firmID : firmIDs) {
				if (firmID != null && firmID.trim().length() > 0) {
					firms += ",'" + firmID.trim() + "'";
				}
			}
			firms = "(" + firms + ")";
			filter1 += " and firmid in " + firms;
			filter2 += " and firmid in " + firms;
			filter21 += " and firmid in " + firms;
			bankfilter += " and firmid in " + firms;
		}
		if (tradeDate == null) {// 如果有交易日期限制
			tradeDate = new java.util.Date();
		}
		try {
			filter1 += " and to_char(b_date,'yyyy-MM-dd')='" + Tool.fmtDate(tradeDate) + "'";
			filter2 += " and to_char(cleardate,'yyyy-MM-dd')='" + Tool.fmtDate(tradeDate) + "'";
			java.util.Date yesdate = getlastDate(tradeDate, conn);
			filter21 += " and to_char(cleardate,'yyyy-MM-dd')='" + Tool.fmtDate(yesdate) + "'";
			sql = "insert into F_B_Tradelist (TRADE_MONEY,TRADE_TYPE,B_MEMBER_CODE,B_MEMBER_NAME,S_MEMBER_CODE,S_MEMBER_NAME,TRADE_DATE,BARGAIN_CODE,SERIAL_ID,GOOD_CODE,GOOD_NAME,GOOD_QUANTITY,FLAG,BANKID,CREATEDATE) "
					+ "select TRADE_MONEY,TRADE_TYPE,B_MEMBER_CODE,B_MEMBER_NAME,S_MEMBER_CODE,S_MEMBER_NAME,to_date('" + Tool.fmtTime(tradeDate)
					+ "','yyyy-MM-dd hh24:mi:ss') TRADE_DATE,'' BARGAIN_CODE,'' || SEQ_F_B_TRADELIST.nextval SERIAL_ID,'' GOOD_CODE,'' GOOD_NAME,0 GOOD_QUANTITY,'N' FLAG,BANKID,sysdate CREATEDATE from "
					+ "("
					+ "select nvl(SettleCompens.value,0)+nvl(Income.value,0)-nvl(Payout.value,0)+nvl(PL1.value,0)-nvl(PL2.value,0) TRADE_MONEY,1 TRADE_TYPE,'666666' B_MEMBER_CODE,'市场' B_MEMBER_NAME "
					+ ",bank.firmid S_MEMBER_CODE,bank.accountName S_MEMBER_NAME,bank.bankid BANKID from "
					+ "(select firmid,sum(nvl(value,0)) value from f_clientledger fc where fc.code='SettleCompens' " + filter1
					+ " group by firmid) SettleCompens," + // --
			// 交割补偿费
			"(select firmid,sum(nvl(value,0)) value from f_clientledger fc where exists(select code from f_ledgerfield fl where fc.code=fl.code and fl.code like 'Income%') "
					+ filter1 + " group by firmid) Income," + // --
			// 交易商收货款
			"(select firmid,sum(nvl(value,0)) value from f_clientledger fc where exists(select code from f_ledgerfield fl where fc.code=fl.code and fl.code like 'Payout%') "
					+ filter1 + " group by firmid) Payout," + // --
			// 交易商付货款
			"(select firmid,sum(nvl(value,0)) value from f_clientledger fc where exists(select code from f_ledgerfield fl where fc.code=fl.code and fl.code like '%PL') and value>=0 "
					+ filter1 + " group by firmid) PL1," + // --
			// 盈利
			"(select firmid,-sum(nvl(value,0)) value from f_clientledger fc where exists(select code from f_ledgerfield fl where fc.code=fl.code and fl.code like '%PL') and value<0 "
					+ filter1 + " group by firmid) PL2," + // --
			// 亏损
			"(select * from f_b_firmidandaccount where 1=1 and firmid not like 'D_%' " + bankfilter + ") bank "
					+ "where bank.firmid=SettleCompens.firmid(+) and bank.firmid=Income.firmid(+) and bank.firmid=Payout.firmid(+) and bank.firmid=PL1.firmid(+) and bank.firmid=PL2.firmid(+) "
					+ // --
			// 交易变化量
			"union all "
					+ "select nvl(rfl.value,0)-nvl(rfl1.value,0) TRADE_MONEY,4 TRADE_TYPE,bank.firmid B_MEMBER_CODE,bank.accountName B_MEMBER_NAME,'555555' S_MEMBER_CODE,'市场' S_MEMBER_NAME,bank.bankid BANKID from "
					+ "(select sum(nvl(RuntimeFL,0)) value,firmid from t_h_firm where 1=1 " + filter2 + " group by firmid) rfl," + // --当日浮亏
					"(select sum(nvl(RuntimeFL,0)) value,firmid from t_h_firm where 1=1 " + filter21 + " group by firmid) rfl1," + // --上日浮亏
					"(select * from f_b_firmidandaccount where 1=1 and firmid not like 'D_%' " + bankfilter + ") bank "
					+ "where bank.firmid=rfl.firmid(+) and bank.firmid=rfl1.firmid(+)" + // --
			// 浮亏变化量
			"union all "
					+ "select nvl(tf.value,0)+nvl(bf.value,0) TRADE_MONEY,2 TRADE_TYPE,bank.firmid B_MEMBER_CODE,bank.accountName B_MEMBER_NAME,'888888' S_MEMBER_CODE,'市场' S_MEMBER_NAME,bank.bankid BANKID from "
					+ "(select firmid,sum(nvl(value,0)) value from f_clientledger fc where exists(select code from f_ledgerfield fl where fc.code=fl.code and fl.code like 'TradeFee%') "
					+ filter1 + " group by firmid) tf," + // --
			// 交易手续费
			"(select firmid,sum(nvl(value,0)) value from f_clientledger fc where fc.code='BankFee' group by firmid) bf," + // --
			// 银行接口手续费
			"(select * from f_b_firmidandaccount where 1=1 and firmid not like 'D_%' " + bankfilter + ") bank "
					+ "where bank.firmid=tf.firmid(+) and bank.firmid=bf.firmid(+) " + // --
			// 交易手续费
			"union all "
					+ "select nvl(sf.value,0) TRADE_MONEY,3 TRADE_TYPE,bank.firmid B_MEMBER_CODE,bank.accountName B_MEMBER_NAME,'101011' S_MEMBER_CODE,'市场' S_MEMBER_NAME,bank.bankid BANKID from "
					+ "(select firmid,sum(nvl(value,0)) value from f_clientledger fc where exists(select code from f_ledgerfield fl where fc.code=fl.code and fl.code like 'SettleFee%') "
					+ filter1 + " group by firmid) sf," + "(select * from f_b_firmidandaccount where 1=1 and firmid not like 'D_%' " + bankfilter
					+ ") bank " + "where bank.firmid=sf.firmid(+) " + // --
			// 交收手续费
			"union all "
					+ "select nvl(OtherItem.value,0) TRADE_MONEY,5 TRADE_TYPE,'444444' B_MEMBER_CODE,'市场' B_MEMBER_NAME,bank.firmid S_MEMBER_CODE,bank.accountName S_MEMBER_NAME,bank.bankid BANKID from "
					+ "(select firmid,sum(nvl(value,0)) value from f_clientledger fc where exists(select code from f_ledgerfield fl where fc.code=fl.code and fl.code like 'OtherItem%') "
					+ filter1 + " group by firmid) OtherItem," + // --
			// 当日其他项
			"(select * from f_b_firmidandaccount where 1=1 and firmid not like 'D_%' " + bankfilter + ") bank "
					+ "where bank.firmid=OtherItem.firmid(+)" + // --
			// 当日其他项
			")";// +
			// "where TRADE_MONEY<>0";
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 提取保存改变交易商冻结资金方法
	 * 
	 * @param bankID
	 *            银行编号
	 * @param firmIDs
	 *            交易商代码集合
	 * @param qdate
	 *            交易日期
	 * @param conn
	 *            数据库连接
	 * @return int 添加条数
	 * @throws SQLException
	 */
	public int addChangeFirmFrozen(String bankID, String[] firmIDs, java.util.Date qdate, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter1 = "";
		String filter11 = "";
		String filter2 = "";
		String bankfilter = "";
		if (bankID != null && bankID.trim().length() > 0) {
			bankfilter += " and bankid='" + bankID.trim() + "'";
		}
		if (firmIDs != null) {
			String firms = "('aa'";
			for (int i = 0; i < firmIDs.length; i++) {
				firms += ",'" + firmIDs[i] + "'";
			}
			firms += ")";
			bankfilter += " and firmid in " + firms;
			filter1 += " and firmid in " + firms;
			filter11 += " and firmid in " + firms;
			filter2 += " and firmid in " + firms;
		}
		if (qdate == null) {
			qdate = new java.util.Date();
		}
		try {
			java.util.Date yestody = getlastDate(qdate, conn);
			filter1 += " and to_char(cleardate,'yyyy-MM-dd')='" + Tool.fmtDate(qdate) + "'";
			filter11 += " and to_char(cleardate,'yyyy-MM-dd')='" + Tool.fmtDate(yestody) + "'";
			filter2 += " and to_char(b_date,'yyyy-MM-dd')='" + Tool.fmtDate(qdate) + "'";
			sql = "insert into F_B_MARGINS (SERIAL_ID,BARGAIN_CODE,TRADE_TYPE,TRADE_MONEY,MEMBER_CODE,MEMBER_NAME,TRADE_DATE,GOOD_CODE,GOOD_NAME,GOOD_QUANTITY,FLAG,BANKID,CREATEDATE) "
					+ "select '' || SEQ_F_B_MARGINS.nextval SERIAL_ID,'' BARGAIN_CODE,case when TRADE_MONEY>0 then 1 else 2 end TRADE_TYPE, abs(TRADE_MONEY) TRADE_MONEY,MEMBER_CODE,MEMBER_NAME,to_date('"
					+ Tool.fmtTime(qdate)
					+ "','yyyy-MM-dd hh24:mi:ss') TRADE_DATE,'' GOOD_CODE,'' GOOD_NAME,0 GOOD_QUANTITY,'N' FLAG,BANKID,sysdate CREATEDATE from " + "("
					+ "select nvl(rsm.value,0)+nvl(rm.value,0)+nvl(Margin.value,0)-nvl(rm2.value,0) TRADE_MONEY,bank.firmid MEMBER_CODE,bank.accountName MEMBER_NAME,bank.bankid bankid from "
					+ "(select sum(nvl(RuntimeSettleMargin,0)-nvl(ClearSettleMargin,0)) value,firmid from t_h_firm where 1=1 " + filter1
					+ " group by firmid) rsm, " + // --
			// 当日交收保证金
			"(select sum(nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0)) value,firmid from t_h_firm where 1=1 " + filter1 + " group by firmid) rm, " + // --
			// 当日远期保证金
			"(select nvl(sum(case when code = 'Margin_V' then value when code = 'Margin_Z' then value when code = 'MarginBack_V' then -value when code = 'MarginBack_Z' then -value else 0 end), 0) value,firmid from f_clientledger fc where exists (select code from f_ledgerfield fl where fc.code=fl.code and fl.code like 'Margin%') "
					+ filter2 + " group by firmid) Margin, " + // --
			// 当日现货竞价保证金
			"(select sum(nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0)) value,firmid from t_h_firm where 1=1 " + filter11 + " group by firmid) rm2, " + // --
			// 上日远期保证金
			"(select * from f_b_firmidandaccount where 1=1 and firmid not like 'D_%' " + bankfilter + ") bank "
					+ "where bank.firmid=rsm.firmid(+) and bank.firmid=rm.firmid(+) and bank.firmid=Margin.firmid(+) and bank.firmid=rm2.firmid(+) "
					+ ")";// +
			// " where TRADE_MONEY<>0";
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 删除交易商权益变化量表信息信息
	 * 
	 * @param bankID
	 *            银行编号
	 * @param qdate
	 *            清算日期
	 * @param SERIAL_IDs
	 *            流水编号
	 * @param conn
	 *            数据库连接
	 * @param int
	 *            删除条数
	 * @throws SQLException
	 */
	public int delChangeFirmRights(String bankID, java.util.Date qdate, String[] SERIAL_IDs, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter = "";
		if (bankID != null && bankID.trim().length() > 0) {
			filter += " and bankid='" + bankID.trim() + "'";
		}
		if (qdate != null) {
			filter += " and to_char(TRADE_DATE,'yyyy-MM-dd')='" + Tool.fmtDate(qdate) + "'";
		}
		if (SERIAL_IDs != null && SERIAL_IDs.length > 0) {
			String SERIAL_ID = "'aa'";
			for (String SERIAL : SERIAL_IDs) {
				if (SERIAL != null && SERIAL.trim().length() > 0) {
					SERIAL_ID += ",'" + SERIAL.trim() + "'";
				}
			}
			filter += " and SERIAL_ID in(" + SERIAL_ID + ")";
		}
		try {
			sql = "update F_B_Tradelist set bankid='D_' || bankid where FLAG<>'Y' " + filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 删除交易商冻结资金变化量表信息信息
	 * 
	 * @param bankID
	 *            银行编号
	 * @param qdate
	 *            清算日期
	 * @param SERIAL_IDs
	 *            流水编号
	 * @param conn
	 *            数据库连接
	 * @throws SQLException
	 */
	public int delChangeFirmFrozen(String bankID, java.util.Date qdate, String[] SERIAL_IDs, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter = "";
		if (bankID != null && bankID.trim().length() > 0) {
			filter += " and bankid='" + bankID.trim() + "'";
		}
		if (qdate != null) {
			filter += " and to_char(TRADE_DATE,'yyyy-MM-dd')='" + Tool.fmtDate(qdate) + "'";
		}
		if (SERIAL_IDs != null && SERIAL_IDs.length > 0) {
			String SERIAL_ID = "'aa'";
			for (String SERIAL : SERIAL_IDs) {
				if (SERIAL != null && SERIAL.trim().length() > 0) {
					SERIAL_ID += ",'" + SERIAL.trim() + "'";
				}
			}
			filter += " and SERIAL_ID in(" + SERIAL_ID + ")";
		}
		try {
			sql = "update F_B_MARGINS set bankid='D_' || bankid where FLAG<>'Y' " + filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 修改交易商权益变化量表信息状态
	 * 
	 * @param SERIAL_ID
	 *            流水编号
	 * @param flag
	 *            修改状态
	 * @param conn
	 *            数据库连接
	 * @return int 修改条数
	 * @throws SQLException
	 */
	public int modChangeFirmRights(String SERIAL_ID, String flag, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "update F_B_Tradelist set flag='" + flag + "' where SERIAL_ID='" + SERIAL_ID + "'";
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 修改交易商冻结资金变化量表信息信息
	 * 
	 * @param SERIAL_ID
	 *            流水编号
	 * @param flag
	 *            修改状态
	 * @param conn
	 *            数据库连接
	 * @return int 修改条数
	 * @throws SQLException
	 */
	public int modChangeFirmFrozen(String SERIAL_ID, String flag, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "update F_B_MARGINS set flag='" + flag + "' where SERIAL_ID='" + SERIAL_ID + "'";
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 查询交易商权益变化量表信息信息
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库连接
	 * @return Vector<TradeList>
	 * @throws SQLException
	 */
	public Vector<TradeList> getChangeFirmRights(String filter, Connection conn) throws SQLException {
		Vector<TradeList> result = new Vector<TradeList>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select * from F_B_Tradelist where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				TradeList value = new TradeList();
				value.B_Member_Code = rs.getString("B_Member_Code");
				value.B_Member_Name = rs.getString("B_Member_Name");
				value.Bargain_Code = rs.getString("Bargain_Code");
				value.flag = rs.getString("flag");
				value.Good_Code = rs.getString("Good_Code");
				value.Good_Name = rs.getString("Good_Name");
				value.Good_Quantity = rs.getInt("Good_Quantity");
				value.S_Member_Code = rs.getString("S_Member_Code");
				value.S_Member_Name = rs.getString("S_Member_Name");
				value.Serial_Id = rs.getString("Serial_Id");
				value.Trade_Date = rs.getDate("Trade_Date");
				value.Trade_Money = rs.getDouble("Trade_Money");
				value.Trade_Type = rs.getString("Trade_Type");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		return result;
	}

	/**
	 * 查询交易商冻结资金变化量表信息信息
	 * 
	 * @param filter
	 *            查询条件
	 * @param conn
	 *            数据库连接
	 * @return Vector<Margins>
	 * @throws SQLException
	 */
	public Vector<Margins> getChangeFirmFrozen(String filter, Connection conn) throws SQLException {
		Vector<Margins> result = new Vector<Margins>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select * from F_B_MARGINS where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				Margins value = new Margins();
				value.Bargain_Code = rs.getString("Bargain_Code");
				value.flag = rs.getString("flag");
				value.Good_Code = rs.getString("Good_Code");
				value.Good_Name = rs.getString("Good_Name");
				value.Good_Quantity = rs.getInt("Good_Quantity");
				value.Member_Code = rs.getString("Member_Code");
				value.Member_Name = rs.getString("Member_Name");
				value.Serial_Id = rs.getString("Serial_Id");
				value.Trade_Date = rs.getDate("Trade_Date");
				value.Trade_Money = rs.getDouble("Trade_Money");
				value.Trade_Type = rs.getString("Trade_Type");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		return result;
	}

	/**
	 * 查询交易商权益变化量表信息信息
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector<TradeList>
	 * @throws SQLException
	 * @throws ClassNotFountException
	 */
	public Vector<TradeList> getChangeFirmRights(String filter) throws SQLException, ClassNotFoundException {
		Vector<TradeList> result = new Vector<TradeList>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = this.getConnection();
			sql = "select * from F_B_Tradelist where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				TradeList value = new TradeList();
				value.B_Member_Code = rs.getString("B_Member_Code");
				value.B_Member_Name = rs.getString("B_Member_Name");
				value.Bargain_Code = rs.getString("Bargain_Code");
				value.flag = rs.getString("flag");
				value.Good_Code = rs.getString("Good_Code");
				value.Good_Name = rs.getString("Good_Name");
				value.Good_Quantity = rs.getInt("Good_Quantity");
				value.S_Member_Code = rs.getString("S_Member_Code");
				value.S_Member_Name = rs.getString("S_Member_Name");
				value.Serial_Id = rs.getString("Serial_Id");
				value.Trade_Date = rs.getDate("Trade_Date");
				value.Trade_Money = rs.getDouble("Trade_Money");
				value.Trade_Type = rs.getString("Trade_Type");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	/**
	 * 查询交易商冻结资金变化量表信息信息
	 * 
	 * @param filter
	 *            查询条件
	 * @return Vector<Margins>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Vector<Margins> getChangeFirmFrozen(String filter) throws SQLException, ClassNotFoundException {
		Vector<Margins> result = new Vector<Margins>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = this.getConnection();
			sql = "select * from F_B_MARGINS where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				Margins value = new Margins();
				value.Bargain_Code = rs.getString("Bargain_Code");
				value.flag = rs.getString("flag");
				value.Good_Code = rs.getString("Good_Code");
				value.Good_Name = rs.getString("Good_Name");
				value.Good_Quantity = rs.getInt("Good_Quantity");
				value.Member_Code = rs.getString("Member_Code");
				value.Member_Name = rs.getString("Member_Name");
				value.Serial_Id = rs.getString("Serial_Id");
				value.Trade_Date = rs.getDate("Trade_Date");
				value.Trade_Money = rs.getDouble("Trade_Money");
				value.Trade_Type = rs.getString("Trade_Type");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	// 工商银行清算信息
	/**
	 * 从财务和交易系统获取交易商资金余额及各交易板块权益
	 * 
	 * @param String
	 *            bankId String firmid,String qdate
	 * @return HashMap<String, TradeDataValue>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Vector<FirmRightsValue> getTradeDataMsg(String bankId, String firmid, java.util.Date qdate) throws SQLException, ClassNotFoundException {
		log("===>>>从财务和交易系统获取交易商资金余额及各交易板块权益  getTradeDataMsg  " + new java.util.Date());
		Vector<FirmRightsValue> hmfai = new Vector<FirmRightsValue>();
		// 从数据库中取出交易商权益并返回
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		String filter = " ";
		String filter_f_fb = " ";
		String filter_z_v = " ";
		String filter_t_h_f = " ";
		if (bankId != null && !"".equals(bankId.trim())) {
			filter = filter + " and i.bankId='" + bankId.trim() + "' and i.firmid not like '%D'";
		}
		if (firmid != null && !"".equals(firmid.trim())) {
			filter = filter + " and i.firmid='" + firmid + "' ";
			filter_f_fb = filter_f_fb + " and firmid='" + firmid + "' ";
			filter_t_h_f = filter_t_h_f + " and firmid='" + firmid + "' ";
			filter_z_v = filter_z_v + " and firmid='" + firmid + "' ";
		}
		if (qdate != null) {
			filter_f_fb = filter_f_fb + " and to_char(b_date,'yyyy-MM-dd')='" + Tool.fmtDate(qdate) + "' ";
			filter_t_h_f = filter_t_h_f + " and to_char(cleardate,'yyyy-MM-dd')='" + Tool.fmtDate(qdate) + "' ";
			filter_z_v = filter_z_v + " and to_char(b_date,'yyyy-MM-dd')<='" + Tool.fmtDate(qdate) + "'";
		}

		try {
			conn = getConnection();
			sql = "select i.firmid qy_firmid,i.accountname accountname,i.account account, " + "nvl(a.todaybalance, 0) f_balance, "
					+ "nvl(b.RuntimeMargin, 0) + nvl(b.RuntimeFL, 0) + " + "nvl(b.RuntimeSettleMargin, 0) + nvl(c.floatingloss, 0) t_money, "
					+ "nvl(d.money, 0) v_money, " + "nvl(e.money, 0) z_money, " + "nvl(f.money, 0) pay_money, " + "nvl(g.money, 0) in_money, "
					+ "nvl(h.money, 0) fee_money, " + "nvl(jie.money, 0) jie_money, " + "nvl(dai.money, 0) dai_money " + "from "
					+ "(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb + ") a,"
					+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
					+ filter_t_h_f + ") b," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 " + filter_t_h_f
					+ " group by firmid) c,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
					+ filter_z_v + " group by firmid) d,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
					+ filter_z_v + " group by firmid) e," + "(select firmid, nvl(sum(value), 0) money from f_clientledger where code like 'Payout%' "
					+ filter_f_fb + " group by firmid) f," + "(select firmid, nvl(sum(value), 0) money from f_clientledger where code like 'Income%' "
					+ filter_f_fb + " group by firmid) g,"
					+ "(select firmid, sum(value) money from f_clientledger where (code like 'TradeFee%' or code like 'SettleFee%' or code = 'BankFee') "
					+ filter_f_fb + " group by firmid) h," + "(select firmid, sum(appeNdamount) money from F_H_FUNDFLOW where oprcode = 207 "
					+ filter_f_fb + " group by firmid) jie," + "(select firmid, sum(appeNdamount) money from F_H_FUNDFLOW where oprcode = 206 "
					+ filter_f_fb + " group by firmid) dai," + "(select firmid,bankid,accountname,account from f_b_Firmidandaccount) i " + "where "
					+ "i.firmid = a.firmid(+) " + "and i.firmid = b.firmid(+) " + "and i.firmid = c.firmid(+) " + "and i.firmid = d.firmid(+) "
					+ "and i.firmid = e.firmid(+) " + "and i.firmid = f.firmid(+) " + "and i.firmid = g.firmid(+) " + "and i.firmid = h.firmid(+) "
					+ "and i.firmid = jie.firmid(+) " + "and i.firmid = dai.firmid(+) " + filter + " order by a.firmid";
			state = conn.prepareStatement(sql);
			System.out.println("----从财务和交易系统获取交易商资金余额及各交易板块权益  getTradeDataMsg---->sql:\n" + sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmRightsValue fai = new FirmRightsValue();
				String qy_firmid = rs.getString("qy_firmid");
				String account = rs.getString("account");
				String accountname = rs.getString("accountname");
				double f_balance = rs.getDouble("f_balance");// 财务余额
				double t_money = rs.getDouble("t_money");// 中远期扣除财务余额后的权益
				double v_money = rs.getDouble("v_money");// 竞价保证金
				double z_money = rs.getDouble("z_money");// 挂牌保证金
				double pay_money = rs.getDouble("pay_money");// 付货款
				double in_money = rs.getDouble("in_money");// 收货款
				double fee_money = rs.getDouble("fee_money");// 手续费
				double jie_money = rs.getDouble("jie_money");// 借出金额
				double dai_money = rs.getDouble("dai_money");// 贷入金额

				fai.setFirmid(qy_firmid);
				fai.setAccount(account);
				fai.setAccountname(accountname);
				fai.setAvilableBlance(f_balance);
				fai.setTimebargainBalance(t_money);
				fai.setVendueBalance(v_money);
				fai.setZcjsBalance(z_money);
				fai.setPayment(pay_money);
				fai.setIncome(in_money);
				fai.setFee(fee_money);
				fai.setJie(jie_money);
				fai.setDai(dai_money);

				hmfai.add(fai);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return hmfai;
	}

	/**
	 * 从财务和交易系统获取交易商资金余额及各交易板块权益
	 * 
	 * @param String
	 *            bankId String firmid,java.util.Date date
	 * @return HashMap<String, TradeDataValue>
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Vector<FirmRightsValue> getTradeDataMsg(String bankId, String firmid, String qdate) throws SQLException, ClassNotFoundException {
		log("===>>>从财务和交易系统获取交易商资金余额及各交易板块权益  getTradeDataMsg  " + new java.util.Date());
		Vector<FirmRightsValue> hmfai = new Vector<FirmRightsValue>();

		// 从数据库中取出交易商权益并返回
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		String filter = " ";
		String filter_f_fb = " ";
		String filter_z_v = " ";
		String filter_t_h_f = " ";
		if (bankId != null && !"".equals(bankId.trim())) {
			filter = filter + " and i.bankId='" + bankId.trim() + "' ";
		}
		if (firmid != null && !"".equals(firmid.trim())) {
			filter = filter + " and i.firmid='" + firmid + "' ";
			filter_f_fb = filter_f_fb + " and firmid='" + firmid + "' ";
			filter_t_h_f = filter_t_h_f + " and firmid='" + firmid + "' ";
			filter_z_v = filter_z_v + " and firmid='" + firmid + "' ";
		}
		if (qdate != null) {
			filter_f_fb = filter_f_fb + " and to_char(b_date,'yyyy-MM-dd')='" + qdate + "' ";
			filter_t_h_f = filter_t_h_f + " and to_char(cleardate,'yyyy-MM-dd')='" + qdate + "' ";
			filter_z_v = filter_z_v + " and to_char(b_date,'yyyy-MM-dd')<='" + qdate + "' ";
		}
		try {
			conn = getConnection();
			sql = "select i.firmid qy_firmid,i.accountname accountname,i.account account, " + "nvl(a.todaybalance, 0) f_balance, "
					+ "nvl(b.RuntimeMargin, 0) + nvl(b.RuntimeFL, 0) + " + "nvl(b.RuntimeSettleMargin, 0) + nvl(c.floatingloss, 0) t_money, "
					+ "nvl(d.money, 0) v_money, " + "nvl(e.money, 0) z_money, " + "nvl(f.money, 0) pay_money, " + "nvl(g.money, 0) in_money, "
					+ "nvl(h.money, 0) fee_money, " + "nvl(jie.money, 0) jie_money, " + "nvl(dai.money, 0) dai_money " + "from "
					+ "(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb + ") a,"
					+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
					+ filter_t_h_f + ") b," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 " + filter_t_h_f
					+ " group by firmid) c,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
					+ filter_z_v + " group by firmid) d,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
					+ filter_z_v + " group by firmid) e," + "(select firmid, nvl(sum(value), 0) money from f_clientledger where code like 'Payout%' "
					+ filter_f_fb + " group by firmid) f," + "(select firmid, nvl(sum(value), 0) money from f_clientledger where code like 'Income%' "
					+ filter_f_fb + " group by firmid) g,"
					+ "(select firmid, sum(value) money from f_clientledger where (code like 'TradeFee%' or code like 'SettleFee%' or code = 'BankFee') "
					+ filter_f_fb + " group by firmid) h," + "(select firmid, sum(appeNdamount) money from F_H_FUNDFLOW where oprcode = 207 "
					+ filter_f_fb + " group by firmid) jie," + "(select firmid, sum(appeNdamount) money from F_H_FUNDFLOW where oprcode = 206 "
					+ filter_f_fb + " group by firmid) dai," + "(select firmid,bankid,accountname,account from f_b_Firmidandaccount) i " + "where "
					+ "i.firmid = a.firmid(+) " + "and i.firmid = b.firmid(+) " + "and i.firmid = c.firmid(+) " + "and i.firmid = d.firmid(+) "
					+ "and i.firmid = e.firmid(+) " + "and i.firmid = f.firmid(+) " + "and i.firmid = g.firmid(+) " + "and i.firmid = h.firmid(+) "
					+ "and i.firmid = jie.firmid(+) " + "and i.firmid = dai.firmid(+) " + filter + " order by a.firmid";
			state = conn.prepareStatement(sql);
			System.out.println("----从财务和交易系统获取交易商资金余额及各交易板块权益  getTradeDataMsg---->sql:\n" + sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmRightsValue fai = new FirmRightsValue();
				String qy_firmid = rs.getString("qy_firmid");// 交易商代码
				String account = rs.getString("account");// 交易商银行账号
				String accountName = rs.getString("accountname");// 交易商名称
				double f_balance = rs.getDouble("f_balance");// 财务余额
				double t_money = rs.getDouble("t_money");// 中远期扣除财务余额后的权益
				double v_money = rs.getDouble("v_money");// 竞价保证金
				double z_money = rs.getDouble("z_money");// 挂牌保证金
				double pay_money = rs.getDouble("pay_money");// 付货款
				double in_money = rs.getDouble("in_money");// 收货款
				double fee_money = rs.getDouble("fee_money");// 手续费
				double jie_money = rs.getDouble("jie_money");// 借出金额
				double dai_money = rs.getDouble("dai_money");// 贷入金额

				fai.setFirmid(qy_firmid);
				fai.setAccount(account);
				fai.setAccountname(accountName);
				fai.setAvilableBlance(f_balance);
				fai.setTimebargainBalance(t_money);
				fai.setVendueBalance(v_money);
				fai.setZcjsBalance(z_money);
				fai.setPayment(pay_money);
				fai.setIncome(in_money);
				fai.setFee(fee_money);
				fai.setJie(jie_money);
				fai.setDai(dai_money);

				hmfai.add(fai);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return hmfai;
	}

	/** 查询银行传来的对账结果 */
	public Vector<BankFirmRightValue> getBankCapital(BankFirmRightValue bfrv) {
		Vector<BankFirmRightValue> list = new Vector<BankFirmRightValue>();
		String sql = "select * from F_B_BankCapitalResult where 1=1 ";
		if (bfrv.bankId != null && !bfrv.bankId.trim().equals("")) {
			sql = sql + "and bankId='" + bfrv.bankId.trim() + "' ";
		}
		if (bfrv.firmId != null && !bfrv.firmId.trim().equals("")) {
			sql = sql + "and firmId='" + bfrv.firmId.trim() + "' ";
		}
		if (bfrv.bdate != null) {
			sql = sql + " and trunc(bdate)=to_date('" + Tool.fmtDate(bfrv.bdate) + "','yyyy-MM-dd')";
		}
		System.out.println("银行传来的对账结果：" + sql);
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				BankFirmRightValue bfr = new BankFirmRightValue();
				bfr.bankId = rs.getString("bankId");
				bfr.firmId = rs.getString("firmId");
				bfr.bankRight = rs.getDouble("bankRight");
				bfr.maketRight = rs.getDouble("maketRight");
				bfr.reason = rs.getInt("reason");
				bfr.bdate = rs.getTimestamp("bdate");
				list.add(bfr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return list;
	}

	/** 查询银行传来的对账结果 */
	public Vector<BankFirmRightValue> getBankCapital(String filter) {
		Vector<BankFirmRightValue> list = new Vector<BankFirmRightValue>();
		String sql = "select * from F_B_BankCapitalResult " + filter;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			System.out.println("银行传来的对账结果：" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				BankFirmRightValue bfr = new BankFirmRightValue();
				bfr.bankId = rs.getString("bankId");
				bfr.firmId = rs.getString("firmId");
				bfr.bankRight = rs.getDouble("bankRight");
				bfr.maketRight = rs.getDouble("maketRight");
				bfr.reason = rs.getInt("reason");
				bfr.bdate = rs.getTimestamp("bdate");
				list.add(bfr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return list;
	}

	/** 添加总分平衡监管 */
	public long addProperBalance(ProperBalanceValue pbv) {
		long result = 0;
		String sql = "insert into F_B_ProperBalance (bankId,allMoney,gongMoney,otherMoney,bdate) values(?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement state = null;
		try {
			if (pbv == null) {
				return -2;
			}
			conn = getConnection();
			state = conn.prepareStatement(sql);
			state.setString(1, pbv.bankId);
			state.setDouble(2, pbv.allMoney);
			state.setDouble(3, pbv.gongMoney);
			state.setDouble(4, pbv.otherMoney);
			state.setTimestamp(5, pbv.bdate);
			state.executeUpdate();
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			result = -1;
			e.printStackTrace();
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	/** 修改总分平衡监管 */
	public long updateProperBalance(ProperBalanceValue pbv) {
		long result = 0;
		String sql = "update F_B_ProperBalance set allMoney=?,gongMoney=?,otherMoney=? where trunc(bdate)=to_date(?,'yyyy-MM-dd') and bankId=?";
		Connection conn = null;
		PreparedStatement state = null;
		try {
			if (pbv == null) {
				return -2;
			}
			System.out.println("修改总分平衡监管:" + sql);
			conn = getConnection();
			state = conn.prepareStatement(sql);
			state.setDouble(1, pbv.allMoney);
			state.setDouble(2, pbv.gongMoney);
			state.setDouble(3, pbv.otherMoney);
			state.setString(4, Tool.fmtDate(pbv.bdate));
			state.setString(5, pbv.bankId);
			state.executeUpdate();
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			result = -1;
			e.printStackTrace();
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	/** 删除总分平衡监管 */
	public long delProperBalance(ProperBalanceValue pbv) {
		long result = 0;
		String sql = "delete F_B_ProperBalance where 1=1 ";
		Connection conn = null;
		PreparedStatement state = null;
		try {
			if (pbv != null) {
				if (pbv.bankId != null && !pbv.bankId.trim().equals("")) {
					sql = sql + " and bankId='" + pbv.bankId.trim() + "' ";
				}
				if (pbv.bdate != null) {
					sql = sql + " and trunc(bdate)=to_date('" + Tool.fmtDate(pbv.bdate) + "','yyyy-MM-dd')";
				}
			}
			System.out.println("删除总分平衡监管：" + sql);
			conn = getConnection();
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			result = -1;
			e.printStackTrace();
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	/** 查询总分平衡监管 */
	public Vector<ProperBalanceValue> getProperBalance(ProperBalanceValue pbv) {
		Vector<ProperBalanceValue> list = new Vector<ProperBalanceValue>();
		String sql = "select * from F_B_ProperBalance where 1=1 ";
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			if (pbv != null) {
				if (pbv.bankId != null && !pbv.bankId.trim().equals("")) {
					sql = sql + "and bankId='" + pbv.bankId.trim() + "' ";
				}
				if (pbv.bdate != null) {
					sql = sql + "and trunc(bdate)=to_date('" + Tool.fmtDate(pbv.bdate) + "','yyyy-MM-dd')";
				}
			}
			System.out.println("查询总分平衡监管：" + sql);
			conn = getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				ProperBalanceValue pb = new ProperBalanceValue();
				pb.bankId = rs.getString("bankId");
				pb.allMoney = rs.getDouble("allMoney");
				pb.gongMoney = rs.getDouble("gongMoney");
				pb.otherMoney = rs.getDouble("otherMoney");
				pb.bdate = rs.getTimestamp("bdate");
				list.add(pb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return list;
	}

	/** 查询总分平衡监管 */
	public Vector<ProperBalanceValue> getProperBalance(String filter) {
		Vector<ProperBalanceValue> list = new Vector<ProperBalanceValue>();
		String sql = "select * from F_B_ProperBalance " + filter;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				ProperBalanceValue pb = new ProperBalanceValue();
				pb.bankId = rs.getString("bankId");
				pb.allMoney = rs.getDouble("allMoney");
				pb.gongMoney = rs.getDouble("gongMoney");
				pb.otherMoney = rs.getDouble("otherMoney");
				pb.bdate = rs.getTimestamp("bdate");
				list.add(pb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return list;
	}

	/** 修改银行传来的对账结果 */
	public long updateBankCapital(BankFirmRightValue bfrv) {
		long result = 0;
		String sql = "update F_B_BankCapitalResult set bankRight=?,maketRight=?,reason=? where trunc(bdate)=to_date(?,'yyyy-MM-dd') and bankId=? and firmId=?";
		Connection conn = null;
		PreparedStatement state = null;
		try {
			if (bfrv != null) {
				conn = getConnection();
				state = conn.prepareStatement(sql);
				state.setDouble(1, bfrv.bankRight);
				state.setDouble(2, bfrv.maketRight);
				state.setInt(3, bfrv.reason);
				state.setString(4, Tool.fmtDate(bfrv.bdate));
				state.setString(5, bfrv.bankId);
				state.setString(6, bfrv.firmId);
				state.executeUpdate();
			}
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			result = -1;
			e.printStackTrace();
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	/** 添加银行传来的对账结果 */
	public long addBankCapital(BankFirmRightValue bfrv) {
		long result = 0;
		String sql = "insert into F_B_BankCapitalResult (bankId,firmId,bankRight,maketRight,reason,bdate) values(?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement state = null;
		try {
			if (bfrv == null) {
				return -2;
			}
			System.out.println(bfrv.toString());
			conn = getConnection();
			state = conn.prepareStatement(sql);
			state.setString(1, bfrv.bankId);
			state.setString(2, bfrv.firmId);
			state.setDouble(3, bfrv.bankRight);
			state.setDouble(4, bfrv.maketRight);
			state.setInt(5, bfrv.reason);
			state.setTimestamp(6, bfrv.bdate);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	// 兴业银行
	public RZQSValue getXYQSValue(String bankID, String[] firmIDs, java.util.Date tradeDate) throws SQLException, ClassNotFoundException {
		RZQSValue result = new RZQSValue();
		result.bankID = bankID;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = " ";
		String fcfilter1 = " ";
		String fcfilter2 = " ";
		String fcfilter2y = " ";
		String thfilter = " ";
		String thfiltery = " ";
		try {
			conn = this.getConnection();
			if (bankID != null && bankID.trim().length() > 0) {
				filter += " and bankID='" + bankID.trim() + "' ";
			}
			if (firmIDs != null && firmIDs.length > 0) {
				String firms = "";
				for (String firmID : firmIDs) {
					if (firmID != null && firmID.trim().length() > 0) {
						firms += "'" + firmID.trim() + "',";
					}
				}
				if (firms != null && firms.trim().length() > 0) {
					firms = "and firmID in (" + firms.substring(0, firms.length() - 1) + ")";
					filter += firms;
					fcfilter1 += firms;
					fcfilter2 += firms;
					fcfilter2y += firms;
					thfilter += firms;
					thfiltery += firms;
				}
			}
			if (tradeDate != null) {
				result.tradeDate = tradeDate;
				Date yestoday = this.getlastDate(tradeDate, conn);
				fcfilter1 += " and trunc(b_date)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
				fcfilter2 += " and trunc(b_date)<=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
				thfilter += " and trunc(cleardate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
				if (yestoday != null) {
					fcfilter2y += " and trunc(b_date)<=to_date('" + Tool.fmtDate(yestoday) + "','yyyy-MM-dd')";
					thfiltery += " and trunc(cleardate)=to_date('" + Tool.fmtDate(yestoday) + "','yyyy-MM-dd')";
				}
			}
			sql = "select " + " bank.*" + ",nvl(Fee.value,0) maketMoney" + // 自有资金
					",0-(nvl(Income.value,0)-nvl(Payout.value,0)+nvl(PLY.value,0)-nvl(PLK.value,0)+nvl(OtherItem.value,0)+nvl(thf.value,0)-nvl(thfy.value,0)) bankErrorMoney"
					+ // 银行间扎差
					",nvl(Income.value,0)-nvl(Payout.value,0)+nvl(PLY.value,0)-nvl(PLK.value,0)-nvl(Fee.value,0)+nvl(OtherItem.value,0)+nvl(thf.value,0)-nvl(thfy.value,0) firmErrorMoney"
					+ // 权益变化量
					",nvl(hmsg.rsm,0)-nvl(hmsgy.rsm,0)+nvl(hmsg.rma,0)-nvl(hmsgy.rma,0)+nvl(hmsg.fl,0)-nvl(hmsgy.fl,0)+nvl(zvm.value,0)-nvl(zvmy.value,0)+nvl(thf.value,0)-nvl(thfy.value,0) cash"
					+ // 冻结资金变化量
					",nvl(balance.todaybalance,0)-nvl(balance.lastbalance,0)-nvl(inMoney.value,0)+nvl(outMoney.value,0) availableBalance" + // 可用资金变化量
					",nvl(hmsg.rsm,0)+nvl(hmsg.rma,0)+nvl(hmsg.fl,0)+nvl(zvm.value,0)+nvl(thf.value,0) frozen" + // 冻结资金
					",nvl(balance.todaybalance,0) balance" + // 可用资金
					",nvl(hmsg.rsm,0)+nvl(hmsg.rma,0)+nvl(hmsg.fl,0)+nvl(zvm.value,0)+nvl(thf.value,0)+nvl(balance.todaybalance,0) firmRights " + // 权益
					",balance.b_date tradeDate " + // 交易日期
					" from " + "(select firmID,sum(value) value from f_clientledger where code like 'Income%' " + fcfilter1
					+ " group by firmID) Income," + // 销售收入
					"(select firmID,sum(value) value from f_clientledger where code like 'Payout%' " + fcfilter1 + " group by firmID) Payout," + // 购货支出
					"(select firmID,sum(value) value from f_clientledger where code like '%PL' and value>0 " + fcfilter1 + " group by firmID) PLY," + // 盈利
					"(select firmID,sum(value) value from f_clientledger where code like '%PL' and value<0 " + fcfilter1 + " group by firmID) PLK," + // 亏损
					"(select firmID,sum(value) value from f_clientledger where code like '%Fee%' " + fcfilter1 + " group by firmID) Fee," + // 当天手续费
					"(select firmID,sum(value) value from f_clientledger where code like 'OtherItem%' " + fcfilter1 + "  group by firmID) OtherItem,"
					+ // 当日其他项
					"(select firmID,sum(case when code in ('Margin_V','Margin_Z') then value else -value end) value from f_clientledger where code in ('MarginBack_V','Margin_V','MarginBack_Z','Margin_Z') "
					+ fcfilter2 + " group by firmID) zvm," + // 当日现货竞价保证金
					"(select firmID,sum(case when code in ('Margin_V','Margin_Z') then value else -value end) value from f_clientledger where code in ('MarginBack_V','Margin_V','MarginBack_Z','Margin_Z') "
					+ fcfilter2y + " group by firmID) zvmy," + // 上日现货竞价保证金
					"(select firmID,sum(value) value from f_clientledger where code='Deposit' " + fcfilter1 + " group by firmID) inMoney," + // 入金
					"(select firmID,sum(value) value from f_clientledger where code='Fetch' " + fcfilter1 + " group by firmID) outMoney," + // 出金
					"(select firmID,RuntimeSettleMargin rsm,(RuntimeMargin-RuntimeAssure) rma,RuntimeFL fl from T_h_firm where 1=1 " + thfilter
					+ ") hmsg," + // 当日交收、远期保证金，浮亏
					"(select firmID,RuntimeSettleMargin rsm,(RuntimeMargin-RuntimeAssure) rma,RuntimeFL fl from T_h_firm where 1=1 " + thfiltery
					+ ") hmsgy," + // 上日交收、远期保证金，浮亏
					"(select firmID,sum(nvl(Floatingloss,0)) value from T_h_firmholdsum where 1=1 " + thfilter + " group by firmid) thf," + // 当日浮动盈亏
					"(select firmID,sum(nvl(Floatingloss,0)) value from T_h_firmholdsum where 1=1 " + thfiltery + " group by firmid) thfy," + // 上日浮动盈亏
					"(select firmID,todaybalance,lastbalance,b_date from f_firmbalance where 1=1 " + fcfilter1 + ") balance," + // 交易商余额
					"(select * from f_b_firmIDandaccount where isopen=1 and firmID not like '%D%' " + filter + ") bank " + // 银行表信息
					" where bank.firmID=Income.firmID(+) and bank.firmID=Payout.firmID(+) and bank.firmID=PLY.firmID(+) and bank.firmID=PLK.firmID(+) "
					+ " and bank.firmID=Fee.firmID(+) and bank.firmID=OtherItem.firmID(+) and bank.firmID=zvm.firmID(+) and bank.firmID=zvmy.firmID(+) "
					+ " and bank.firmID=inMoney.firmID(+) and bank.firmID=outMoney.firmID(+) and bank.firmID=hmsg.firmID(+) and bank.firmID=hmsgy.firmID(+) "
					+ " and bank.firmID=balance.firmID(+) and bank.firmID=thf.firmID(+) and bank.firmID=thfy.firmID(+) order by bank.firmID";
			System.out.println("清算sql：" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			MarketRightValue mrv = new MarketRightValue();
			mrv.maketMoney = new BigDecimal("0");

			// Vector<KXHfailChild> toBank = getBankOpen(bankID, firmIDs, 1,
			// tradeDate);//获取某一天的签约会员信息
			// RZDZValue lastdz = this.getXYDZValue(bankID, firmIDs,
			// this.getlastDate(tradeDate,conn));//获取某一天的上一个结算日的对账信息
			while (rs.next()) {
				FirmRightValue value = new FirmRightValue();
				value.actionID = String.valueOf(this.getActionID());
				value.firmID = rs.getString("firmID");
				value.account = rs.getString("account");
				value.availableBalance = rs.getDouble("availableBalance");
				value.billMoney = 0;
				value.cash = rs.getDouble("cash");
				value.cashMoney = 0;
				value.credit = 0;
				value.firmErrorMoney = rs.getDouble("firmErrorMoney");
				mrv.bankErrorMoney += rs.getDouble("bankErrorMoney");
				mrv.maketMoney = mrv.maketMoney.add(rs.getBigDecimal("maketMoney"));
				System.out.println("交易商[" + rs.getString("firmID") + "]的自有资金[" + rs.getBigDecimal("maketMoney") + "]总自有资金[" + mrv.maketMoney + "]");
				this.log("交易商[" + rs.getString("firmID") + "]的自有资金[" + rs.getBigDecimal("maketMoney") + "]总自有资金[" + mrv.maketMoney + "]");
				result.putFrv(value);
			}
			result.setMarketRight(mrv);
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	public RZDZValue getXYDZValue(String bankID, String[] firmIDs, java.util.Date tradeDate) throws SQLException, ClassNotFoundException {
		RZDZValue result = new RZDZValue();
		result.bankID = bankID;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = " ";
		String fcfilter1 = " ";
		String fcfilter2 = " ";
		String fcfilter2y = " ";
		String thfilter = " ";
		String thfiltery = " ";
		try {
			conn = this.getConnection();
			if (bankID != null && bankID.trim().length() > 0) {
				filter += " and bankID='" + bankID.trim() + "' ";
			}
			if (firmIDs != null && firmIDs.length > 0) {
				String firms = "";
				for (String firmID : firmIDs) {
					if (firmID != null && firmID.trim().length() > 0) {
						firms += "'" + firmID.trim() + "',";
					}
				}
				if (firms != null && firms.trim().length() > 0) {
					firms = "and firmID in (" + firms.substring(0, firms.length() - 1) + ")";
					filter += firms;
					fcfilter1 += firms;
					fcfilter2 += firms;
					fcfilter2y += firms;
					thfilter += firms;
					thfiltery += firms;
				}
			}
			if (tradeDate != null) {
				result.tradeDate = tradeDate;
				Date yestoday = this.getlastDate(tradeDate, conn);
				fcfilter1 += " and trunc(b_date)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
				fcfilter2 += " and trunc(b_date)<=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
				thfilter += " and trunc(cleardate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
				if (yestoday != null) {
					fcfilter2y += " and trunc(b_date)<=to_date('" + Tool.fmtDate(yestoday) + "','yyyy-MM-dd')";
					thfiltery += " and trunc(cleardate)=to_date('" + Tool.fmtDate(yestoday) + "','yyyy-MM-dd')";
				}
			}
			sql = "select " + " bank.*" + ",nvl(Fee.value,0) marketMoney" + // 自有资金
					",nvl(Income.value,0)-nvl(Payout.value,0)+nvl(PLY.value,0)-nvl(PLK.value,0)+nvl(OtherItem.value,0)+nvl(thf.value,0)-nvl(thfy.value,0) bankErrorMoney"
					+ // 银行间扎差
					",nvl(Income.value,0)-nvl(Payout.value,0)+nvl(PLY.value,0)-nvl(PLK.value,0)-nvl(Fee.value,0)+nvl(OtherItem.value,0)+nvl(thf.value,0)-nvl(thfy.value,0) firmErrorMoney"
					+ // 权益变化量
					",nvl(hmsg.rsm,0)-nvl(hmsgy.rsm,0)+nvl(hmsg.rma,0)-nvl(hmsgy.rma,0)+nvl(hmsg.fl,0)-nvl(hmsgy.fl,0)+nvl(zvm.value,0)-nvl(zvmy.value,0)+nvl(thf.value,0)-nvl(thfy.value,0) cash"
					+ // 冻结资金变化量
					",nvl(balance.todaybalance,0)-nvl(balance.lastbalance,0)-nvl(inMoney.value,0)+nvl(outMoney.value,0) availableBalance" + // 可用资金变化量
					",nvl(hmsg.rsm,0)+nvl(hmsg.rma,0)+nvl(hmsg.fl,0)+nvl(zvm.value,0)+nvl(thf.value,0) frozen" + // 冻结资金
					",nvl(balance.todaybalance,0) balance" + // 可用资金
					",nvl(hmsg.rsm,0)+nvl(hmsg.rma,0)+nvl(hmsg.fl,0)+nvl(zvm.value,0)+nvl(thf.value,0)+nvl(balance.todaybalance,0) firmRights " + // 权益
					",balance.b_date tradeDate " + // 交易日期
					" from " + "(select firmID,sum(value) value from f_clientledger where code like 'Income%' " + fcfilter1
					+ " group by firmID) Income," + // 销售收入
					"(select firmID,sum(value) value from f_clientledger where code like 'Payout%' " + fcfilter1 + " group by firmID) Payout," + // 购货支出
					"(select firmID,sum(value) value from f_clientledger where code like '%PL' and value>0 " + fcfilter1 + " group by firmID) PLY," + // 盈利
					"(select firmID,sum(value) value from f_clientledger where code like '%PL' and value<0 " + fcfilter1 + " group by firmID) PLK," + // 亏损
					"(select firmID,sum(value) value from f_clientledger where code like '%Fee%' " + fcfilter1 + " group by firmID) Fee," + // 手续费
					"(select firmID,sum(value) value from f_clientledger where code like 'OtherItem%' " + fcfilter1 + "  group by firmID) OtherItem,"
					+ // 当日其他项
					"(select firmID,sum(case when code in ('Margin_V','Margin_Z') then value else -value end) value from f_clientledger where code in ('MarginBack_V','Margin_V','MarginBack_Z','Margin_Z') "
					+ fcfilter2 + " group by firmID) zvm," + // 当日现货竞价保证金
					"(select firmID,sum(case when code in ('Margin_V','Margin_Z') then value else -value end) value from f_clientledger where code in ('MarginBack_V','Margin_V','MarginBack_Z','Margin_Z') "
					+ fcfilter2y + " group by firmID) zvmy," + // 上日现货竞价保证金
					"(select firmID,sum(value) value from f_clientledger where code='Deposit' " + fcfilter1 + " group by firmID) inMoney," + // 入金
					"(select firmID,sum(value) value from f_clientledger where code='Fetch' " + fcfilter1 + " group by firmID) outMoney," + // 出金
					"(select firmID,RuntimeSettleMargin rsm,(RuntimeMargin-RuntimeAssure) rma,RuntimeFL fl from T_h_firm where 1=1 " + thfilter
					+ ") hmsg," + // 当日交收、远期保证金，浮亏
					"(select firmID,RuntimeSettleMargin rsm,(RuntimeMargin-RuntimeAssure) rma,RuntimeFL fl from T_h_firm where 1=1 " + thfiltery
					+ ") hmsgy," + // 上日交收、远期保证金，浮亏
					"(select firmID,sum(nvl(Floatingloss,0)) value from T_h_firmholdsum where 1=1 " + thfilter + " group by firmid) thf," + // 当日浮动盈亏
					"(select firmID,sum(nvl(Floatingloss,0)) value from T_h_firmholdsum where 1=1 " + thfiltery + " group by firmid) thfy," + // 上日浮动盈亏
					"(select firmID,todaybalance,lastbalance,b_date from f_firmbalance where 1=1 " + fcfilter1 + ") balance," + // 交易商余额
					"(select * from f_b_firmIDandaccount where isopen=1 and firmID not like '%D%' " + filter + ") bank " + // 银行表信息
					" where bank.firmID=Income.firmID(+) and bank.firmID=Payout.firmID(+) and bank.firmID=PLY.firmID(+) and bank.firmID=PLK.firmID(+) "
					+ " and bank.firmID=Fee.firmID(+) and bank.firmID=OtherItem.firmID(+) and bank.firmID=zvm.firmID(+) and bank.firmID=zvmy.firmID(+) "
					+ " and bank.firmID=inMoney.firmID(+) and bank.firmID=outMoney.firmID(+) and bank.firmID=hmsg.firmID(+) and bank.firmID=hmsgy.firmID(+) "
					+ " and bank.firmID=balance.firmID(+) and bank.firmID=thf.firmID(+) and bank.firmID=thfy.firmID(+) order by bank.firmID";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmDZValue value = new FirmDZValue();
				value.firmID = rs.getString("firmID");
				value.account = rs.getString("account");
				value.firmRights = rs.getDouble("firmRights");
				value.cashRights = 0;
				value.billRights = 0;
				value.availableBalance = rs.getDouble("balance");
				value.cash = rs.getDouble("frozen");
				value.credit = 0;
				result.putFdv(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	public int addZFPH(ZFPHValue zfph, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_ZFPH " + "(BANKID,TRADEDATE,CURRENCY,TYPE,LASTACCOUNTBALANCE,ACCOUNTBALANCE,RESULT,CREATEDATE) "
					+ "values (?,?,?,?,?,?,?,sysdate)";
			state = conn.prepareStatement(sql);
			state.setString(1, zfph.bankID);
			state.setDate(2, new java.sql.Date(zfph.tradeDate == null ? 0 : zfph.tradeDate.getTime()));
			state.setString(3, zfph.currency);
			state.setInt(4, zfph.type);
			state.setBigDecimal(5, zfph.lastAccountBalance);
			state.setBigDecimal(6, zfph.accountBalance);
			state.setInt(7, zfph.result);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	public Vector<ZFPHValue> getZFPH(String bankID, java.util.Date tradeDate, int result) throws SQLException, ClassNotFoundException {
		Vector<ZFPHValue> resultbak = null;
		Connection conn = null;
		try {
			conn = this.getConnection();
			resultbak = this.getZFPH(bankID, tradeDate, result, conn);
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(null, null, conn);
		}
		return resultbak;
	}

	public Vector<ZFPHValue> getZFPH(String bankID, java.util.Date tradeDate, int result, Connection conn) throws SQLException {
		Vector<ZFPHValue> vector = new Vector<ZFPHValue>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = "";
		if (bankID != null && bankID.trim().length() > 0) {
			filter += " and bankID='" + bankID.trim() + "' ";
		}
		if (tradeDate != null) {
			filter += " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd') ";
		}
		if (result >= 0) {
			filter += " and result=" + result + " ";
		}
		try {
			sql = "select * from F_B_ZFPH where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				ZFPHValue value = new ZFPHValue();
				value.accountBalance = rs.getBigDecimal("accountBalance");
				value.bankID = rs.getString("bankID");
				value.currency = rs.getString("currency");
				value.lastAccountBalance = rs.getBigDecimal("lastAccountBalance");
				value.result = rs.getInt("result");
				value.tradeDate = rs.getDate("tradeDate");
				value.type = rs.getInt("type");
				vector.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		return vector;
	}

	/**
	 * 删除总分平衡监管
	 * 
	 * @param bankID
	 *            交易商代码
	 * @param tradeDate
	 *            交易日期
	 * @param result
	 *            监管结果
	 * @param conn
	 *            数据库连接
	 * @return int
	 * @throws SQLException
	 */
	public int delZFPH(String bankID, java.util.Date tradeDate, int result, Connection conn) throws SQLException {
		int rst = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter = "";
		if (bankID != null && bankID.trim().length() > 0) {
			filter += " and bankID='" + bankID.trim() + "' ";
		}
		if (tradeDate != null) {
			filter += " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd') ";
		}
		if (result >= 0) {
			filter += " and result=" + result + " ";
		}
		try {
			sql = "delete F_B_ZFPH where 1=1 " + filter;

			System.out.println("DEL_SQL:" + sql);

			state = conn.prepareStatement(sql);
			rst = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return rst;
	}

	/**
	 * 添加分分核对监管
	 */
	public int addFFHD(FFHDValue ffhd, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_FFHD " + "(BANKID,TRADEDATE,FIRMID,ACCOUNT,CURRENCY,TYPE,REASION,CREATEDATE"
					+ ",balanceM,cashM,billM,useBalanceM,frozenCashM,frozenLoanM" + ",balanceB,cashB,billB,useBalanceB,frozenCashB,frozenLoanB) "
					+ "values (?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?)";
			state = conn.prepareStatement(sql);
			if (ffhd != null && ffhd.getFdv() != null) {
				Vector<FirmDateValue> vv = ffhd.getFdv();
				for (FirmDateValue ff : vv) {
					state.setString(1, ff.bankID);
					state.setDate(2, new java.sql.Date(ff.tradeDate.getTime()));
					state.setString(3, ff.firmID);
					state.setString(4, ff.account);
					state.setString(5, ff.currency);
					state.setInt(6, ff.type);
					state.setInt(7, ff.reason);
					state.setDouble(8, ff.balanceM);
					state.setDouble(9, ff.cashM);
					state.setDouble(10, ff.billM);
					state.setDouble(11, ff.useBalanceM);
					state.setDouble(12, ff.frozenCashM);
					state.setDouble(13, ff.frozenLoanM);
					state.setDouble(14, ff.balanceB);
					state.setDouble(15, ff.cashB);
					state.setDouble(16, ff.billB);
					state.setDouble(17, ff.useBalanceB);
					state.setDouble(18, ff.frozenCashB);
					state.setDouble(19, ff.frozenLoanB);
					result = state.executeUpdate();
				}
			}
			System.out.println("ADD_SQL:" + sql);
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	public Vector<FirmDateValue> getFFHD(String firmID, String bankID, java.util.Date tradeDate) throws SQLException, ClassNotFoundException {
		Vector<FirmDateValue> result = null;
		String filter = "";
		if (firmID != null && firmID.trim().length() > 0) {
			filter += " and firmID='" + firmID.trim() + "'";
		}
		if (bankID != null && bankID.trim().length() > 0) {
			filter += " and bankID = '" + bankID.trim() + "'";
		}
		if (tradeDate != null) {
			filter += " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
		}
		Connection conn = null;
		try {
			conn = this.getConnection();
			result = this.getFFHD(filter, conn);
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(null, null, conn);
		}
		return result;
	}

	public Vector<FirmDateValue> getFFHD(String filter, Connection conn) throws SQLException {
		Vector<FirmDateValue> result = new Vector<FirmDateValue>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select * from F_B_FFHD where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmDateValue fd = new FirmDateValue();
				fd.bankID = rs.getString("bankid");
				fd.tradeDate = rs.getDate("tradeDate");
				fd.firmID = rs.getString("firmID");
				fd.account = rs.getString("account");
				fd.currency = rs.getString("currency");
				fd.type = rs.getInt("type");
				fd.reason = rs.getInt("reasion");
				fd.balanceM = rs.getDouble("balanceM");
				fd.cashM = rs.getDouble("cashM");
				fd.billM = rs.getDouble("billM");
				fd.useBalanceM = rs.getDouble("useBalanceM");
				fd.frozenCashM = rs.getDouble("frozenCashM");
				fd.frozenLoanM = rs.getDouble("frozenLoanM");
				fd.balanceB = rs.getDouble("balanceB");
				fd.cashB = rs.getDouble("cashB");
				fd.billB = rs.getDouble("billB");
				fd.useBalanceB = rs.getDouble("useBalanceB");
				fd.frozenCashB = rs.getDouble("frozenCashB");
				fd.frozenLoanB = rs.getDouble("frozenLoanB");
				result.add(fd);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		return result;
	}

	/**
	 * 删除分分核对监管
	 * 
	 * @param bankID
	 *            银行编号
	 * @param firmIDs
	 *            交易商代码集
	 * @param tradeDate
	 *            交易日期
	 * @param conn
	 *            数据库连接
	 * @return int 删除数量
	 * @throws SQLException
	 */
	public int delFFHD(String bankID, String[] firmIDs, java.util.Date tradeDate, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter = "";
		if (bankID != null && bankID.trim().length() > 0) {
			filter += " and bankID='" + bankID.trim() + "' ";
		}
		if (firmIDs != null && firmIDs.length > 0) {
			String firms = "";
			for (String firmID : firmIDs) {
				if (firmID != null && firmID.trim().length() > 0) {
					firms += "'" + firmID.trim() + "',";
				}
			}
			if (firms != null && firms.trim().length() > 0) {
				filter += " and firmID in (" + firms.trim().substring(0, firms.trim().length() - 1) + ")";
			}
		}
		if (tradeDate != null) {
			filter += " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
		}
		try {
			sql = "delete F_B_FFHD where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, null);
		}
		return result;
	}

	/**
	 * 添加市场自有资金变动表
	 * 
	 * @param xymm
	 *            银行自有金额变动类
	 * @return int 添加条数
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public int addMarketMoney(XYMarketMoney xymm) throws SQLException, ClassNotFoundException {
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_MAKETMONEY (ID,BANKID,BANKNUMBER,TYPE,ADDDELT,MONEY,NOTE) values (SEQ_NH_F_B_MAKETMONEY.nextval,?,?,?,?,?,?)";
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			state.setString(1, xymm.bankID);
			state.setString(2, xymm.bankNumber);
			state.setInt(3, xymm.type);
			state.setInt(4, xymm.addDelt);
			state.setDouble(5, xymm.money);
			state.setString(6, xymm.note);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	/**
	 * 修改市场自有资金变动表
	 * 
	 * @param xymm
	 *            银行自有金额变动类
	 * @return int 修改条数
	 * @throws SQLException
	 *             ,ClassNotFoundException
	 */
	public int modMarketMoney(XYMarketMoney xymm) throws SQLException, ClassNotFoundException {
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "update F_B_MAKETMONEY set BANKID=?,BANKNUMBER=?,TYPE=?,ADDDELT=?,MONEY=?,NOTE=? where ID=?";
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			state.setString(1, xymm.bankID);
			state.setString(2, xymm.bankNumber);
			state.setInt(3, xymm.type);
			state.setInt(4, xymm.addDelt);
			state.setDouble(5, xymm.money);
			state.setString(6, xymm.note);
			state.setInt(7, xymm.id);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	/**
	 * 查询市场自有资金变动表
	 */
	public Vector<XYMarketMoney> getMarketMoney(String filter) throws SQLException, ClassNotFoundException {
		Vector<XYMarketMoney> result = new Vector<XYMarketMoney>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "select * from F_B_MAKETMONEY where 1=1 ";
		try {
			sql = sql + filter;
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				XYMarketMoney value = new XYMarketMoney();
				value.id = rs.getInt("ID");
				value.addDelt = rs.getInt("addDelt");
				value.bankID = rs.getString("bankID");
				value.bankNumber = rs.getString("bankNumber");
				value.createTime = rs.getDate("createTime");
				value.money = rs.getDouble("money");
				value.note = rs.getString("note");
				value.type = rs.getInt("type");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	public long addFirmTradeStatus(FirmTradeStatus val) throws SQLException {
		// TODO Auto-generated method stub
		log("===>>>添加客户协议状态   addFirmTradeStatus" + new java.util.Date());
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		String sql = null;
		long id = 0;
		try {
			conn = getConnection();
			sql = "insert into F_B_FIRMTRADESTATUS(BANKID,DEALID,COBRN,TXDATE,BANKACC,FUNDACC,CUSTNAME,CURCODE,STATUS,COMPAREDATE) "
					+ "values(?,?,?,?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, val.BankId);
			stmt.setString(2, val.DealId);
			stmt.setString(3, val.CoBrn);
			stmt.setString(4, val.TxDate);
			stmt.setString(5, val.BankAcc);
			stmt.setString(6, val.FundAcc);
			stmt.setString(7, val.CustName);
			stmt.setString(8, val.CurCode);
			stmt.setString(9, val.Status);
			stmt.setDate(10, val.compareDate);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			id = -1;
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			id = -1;
		} finally {
			closeStatement(rst, stmt, conn);
		}
		return id;
	}

	public long addTradeDetailAccount(TradeDetailAccount val) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		String sql = null;
		long id = 0;
		try {
			conn = getConnection();
			sql = "insert into F_B_TRADEDETAILACC(BATCHNO,BANKID,DEALID,COBRN,TXDATE,TXTIME,BKSERIAL,COSERIAL,BANKACC,FUNDACC,CUSTNAME,TXOPT,TXCODE,CURCODE,COMPAREDATE) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, val.BatchNo);
			stmt.setString(2, val.BankId);
			stmt.setString(3, val.DealId);
			stmt.setString(4, val.CoBrn);
			stmt.setString(5, val.TxDate);
			stmt.setString(6, val.TxTime);
			stmt.setString(7, val.BkSerial);
			stmt.setString(8, val.CoSerial);
			stmt.setString(9, val.BankAcc);
			stmt.setString(10, val.FundAcc);
			stmt.setString(11, val.CustName);
			stmt.setString(12, val.TradeSource);
			stmt.setString(13, val.BusinessCode);
			stmt.setString(14, val.CurCode);
			stmt.setDate(15, val.compareDate);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			id = -1;
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			id = -1;
		} finally {
			this.closeStatement(rst, stmt, conn);
		}
		return id;
	}

	public Vector<FirmTradeStatus> getFirmTradeStatusList(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>取得客户协议状态信息列表   getFirmTradeStatusList  " + new java.util.Date());
		System.out.println("===>>>取得客户协议状态信息列表   getFirmTradeStatusList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;
		FirmTradeStatus val = null;
		Vector<FirmTradeStatus> veVal = new Vector();
		try {
			conn = getConnection();
			System.out.println("链接数据库成功");
			sql = "select * from F_B_FIRMTRADESTATUS " + filter;
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				val = new FirmTradeStatus();
				val.setBankId(rs.getString(1));
				val.setDealId(rs.getString(2));
				val.setCoBrn(rs.getString(3));
				val.setTxDate(rs.getString(4));
				val.setBankAcc(rs.getString(5));
				val.setFundAcc(rs.getString(6));
				val.setCustName(rs.getString(7));
				val.setCurCode(rs.getString(8));
				val.setStatus(rs.getString(9));
				veVal.add(val);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return veVal;
	}

	public Vector<TradeDetailAccount> getTradeDetailAccountList(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>取得账户类交易对账信息列表   getTradeDetailAccountList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;
		TradeDetailAccount val = null;
		Vector<TradeDetailAccount> veVal = new Vector();
		try {
			conn = getConnection();
			sql = "select * from F_B_TRADEDETAILACC " + filter;
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				val = new TradeDetailAccount();
				val.setBatchNo(rs.getString(1));
				val.setBankId(rs.getString(2));
				val.setDealId(rs.getString(3));
				val.setCoBrn(rs.getString(4));
				val.setTxDate(rs.getString(5));
				val.setTxTime(rs.getString(6));
				val.setBkSerial(rs.getString(7));
				val.setCoSerial(rs.getString(8));
				val.setBankAcc(rs.getString(9));
				val.setFundAcc(rs.getString(10));
				val.setCustName(rs.getString(11));
				val.setTradeSource(rs.getString(12));
				val.setBusinessCode(rs.getString(13));
				val.setCurCode(rs.getString(14));
				veVal.add(val);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return veVal;
	}

	public Vector<FirmBalance> getFirmBalance(String bankID, java.util.Date qdate) throws SQLException, Exception {
		Vector<FirmBalance> result = new Vector<FirmBalance>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = this.getConnection();
			java.util.Date yestody = getlastDate(qdate, conn);
			String filter_Fee = " and b_date=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') ";
			String Bank_filter = "";
			if (bankID != null && bankID.trim().length() > 0) {
				Bank_filter += " and bankid='" + bankID.trim() + "' ";
			}

			String Fee_filter = filter_Fee;// 手续费条件
			// 权益版
			String filter = " ";
			String filter_f_fb = " ";
			String filter_z_v = " ";
			String filter_t_h_f = " ";

			String filter_f_fb_today = " ";
			String filter_z_v_today = " ";
			String filter_t_h_f_today = " ";
			if (bankID != null && !"".equals(bankID.trim())) {
				filter = filter + " and i.bankId='" + bankID.trim() + "' and i.firmid not like '%D'";
			}
			String firmid = null;
			if (firmid != null && !"".equals(firmid.trim())) {
				filter = filter + " and i.firmid='" + firmid + "' ";
				filter_f_fb = filter_f_fb + " and firmid='" + firmid + "' ";
				filter_t_h_f = filter_t_h_f + " and firmid='" + firmid + "' ";
				filter_z_v = filter_z_v + " and firmid='" + firmid + "' ";

				filter_f_fb_today = filter_f_fb + " and firmid='" + firmid + "' ";
				filter_t_h_f_today = filter_t_h_f + " and firmid='" + firmid + "' ";
				filter_z_v_today = filter_z_v + " and firmid='" + firmid + "' ";
			}
			java.util.Date yes_qdate = getlastDate(qdate, conn);
			if (qdate != null) {
				filter_f_fb = filter_f_fb + " and b_date = to_date('" + Tool.fmtDate(yes_qdate) + "', 'yyyy-MM-dd') ";
				filter_t_h_f = filter_t_h_f + " and cleardate = to_date('" + Tool.fmtDate(yes_qdate) + "', 'yyyy-MM-dd') ";
				filter_z_v = filter_z_v + " and b_date <= to_date('" + Tool.fmtDate(yes_qdate) + "', 'yyyy-MM-dd') ";

				filter_f_fb_today = filter_f_fb_today + " and b_date = to_date('" + Tool.fmtDate(qdate) + "', 'yyyy-MM-dd') ";
				filter_t_h_f_today = filter_t_h_f_today + " and cleardate = to_date('" + Tool.fmtDate(qdate) + "', 'yyyy-MM-dd') ";
				filter_z_v_today = filter_z_v_today + " and b_date <= to_date('" + Tool.fmtDate(qdate) + "', 'yyyy-MM-dd') ";
			}

			sql = "select i.firmid , " + "i.bankid, " + "i.account, " + "i.accountname, " + "i.cardtype, " + "i.card, " + "nvl(a1.todaybalance, 0) + "
					+ "nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + " + "nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + "
					+ "nvl(d1.money, 0)+ nvl(e1.money, 0) money ," + "nvl(Fee.value,0) Fee ," + "nvl(OutMoney.value, 0) ," + "nvl(InMoney.value, 0) ,"
					+ "nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + nvl(d1.money, 0)+ nvl(e1.money, 0) -"
					+ "nvl(a.todaybalance, 0) - nvl(b.RuntimeMargin, 0) - nvl(b.RuntimeFL, 0) - nvl(b.RuntimeSettleMargin, 0) - nvl(c.floatingloss, 0) - nvl(d.money, 0)- nvl(e.money, 0) +"
					+ "nvl(Fee.value,0) + nvl(OutMoney.value, 0) - nvl(InMoney.value, 0) QYChangeMoney " + "from "
					+ "(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb + ") a,"
					+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
					+ filter_t_h_f + ") b," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 " + filter_t_h_f
					+ " group by firmid) c,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
					+ filter_z_v + " group by firmid) d,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
					+ filter_z_v + " group by firmid) e," +

			"(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Deposit' " + filter_Fee + " group by firmid "
					+ ") InMoney," + // 交易商入金
					"(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Fetch' " + filter_Fee + " group by firmid "
					+ ") OutMoney," + // 交易商出金
					"(select firmid,bankid,accountname,account,isopen,cardType, card from f_b_Firmidandaccount) i ," + // 交易商信息
					"(select fc.firmid firmid,nvl(sum(nvl(fc.value,0)),0) value from f_clientledger fc where exists (select code from f_ledgerfield fl where code like '%Fee%' and fc.code=fl.code ) "
					+ Fee_filter + " group by firmid" + ") Fee," + // 交易商手续费

			"(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb_today + ") a1,"
					+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
					+ filter_t_h_f_today + ") b1," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 "
					+ filter_t_h_f_today + " group by firmid) c1,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
					+ filter_z_v_today + " group by firmid) d1,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
					+ filter_z_v_today + " group by firmid) e1 " + "where " + "i.firmid = a.firmid(+) " + "and i.firmid = b.firmid(+) "
					+ "and i.firmid = c.firmid(+) " + "and i.firmid = d.firmid(+) " + "and i.firmid = e.firmid(+) "
					+ "and i.firmid = OutMoney.firmid(+) " + "and i.firmid = InMoney.firmid(+) " + "and i.firmid = FEE.firmid(+) "
					+ "and i.firmid = a1.firmid(+) " + "and i.firmid = b1.firmid(+) " + "and i.firmid = c1.firmid(+) "
					+ "and i.firmid = d1.firmid(+) " + "and i.firmid = e1.firmid(+) " + "and i.isopen = '1' " + filter + " order by a.firmid";
			System.out.println("民生清算sql:" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmBalance fb = new FirmBalance();
				fb.firmID = rs.getString("firmid");
				fb.bankID = rs.getString("bankid");
				fb.account = rs.getString("account");
				fb.accountName = rs.getString("accountName");
				fb.cardType = rs.getString("cardType");
				fb.card = rs.getString("card");
				fb.FeeMoney = rs.getDouble("Fee");
				fb.QYChangeMoney = rs.getDouble("QYChangeMoney");
				fb.QYMoney = rs.getDouble("money");
				fb.date = qdate;
				result.add(fb);
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}

	/**
	 * 取交易系统状态
	 * 
	 * @return int 系统状态 整数：状态 -2异常
	 * @throws SQLException
	 */
	public boolean getSystemStatus() throws SQLException, ClassNotFoundException {

		log("获取交易系统结算状态");
		boolean flag = false;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		conn = this.getConnection();
		String sql = "select * from t_systemstatus ";
		System.out.println("获取交易系统结算状态sql:" + sql);
		state = conn.prepareStatement(sql);
		rs = state.executeQuery();
		while (rs.next()) {
			int status = rs.getInt("status");
			if (status == 3) {
				flag = true;
			}
		}
		return flag;
	}

	public Vector<BankQSNetChild> getQSBankDate(String bankID, java.util.Date qdate) throws Exception {
		log("获取某日的清算信息  getQSChild 时间：" + Tool.fmtDate(new java.util.Date()));
		Vector<BankQSNetChild> vec = new Vector<BankQSNetChild>();
		Connection conn = null;

		conn = this.getConnection();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = "";

		String filter_f_fb = " ";
		String filter_z_v = " ";
		String filter_t_h_f = " ";

		String filter_f_fb_today = " ";
		String filter_z_v_today = " ";
		String filter_t_h_f_today = " ";

		java.util.Date yes_qdate = getlastDate(qdate, conn);
		String filter_Fee = " and b_date=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') ";
		String Fee_filter = filter_Fee;
		if (qdate != null) {
			filter_f_fb = filter_f_fb + " and b_date = to_date('" + Tool.fmtDate(yes_qdate) + "', 'yyyy-MM-dd') ";
			filter_t_h_f = filter_t_h_f + " and cleardate = to_date('" + Tool.fmtDate(yes_qdate) + "', 'yyyy-MM-dd') ";
			filter_z_v = filter_z_v + " and b_date <= to_date('" + Tool.fmtDate(yes_qdate) + "', 'yyyy-MM-dd') ";

			filter_f_fb_today = filter_f_fb_today + " and b_date = to_date('" + Tool.fmtDate(qdate) + "', 'yyyy-MM-dd') ";
			filter_t_h_f_today = filter_t_h_f_today + " and cleardate = to_date('" + Tool.fmtDate(qdate) + "', 'yyyy-MM-dd') ";
			filter_z_v_today = filter_z_v_today + " and b_date <= to_date('" + Tool.fmtDate(qdate) + "', 'yyyy-MM-dd') ";
		}
		String filter2 = filter + " and isopen=1 and firmid not like '%/_D%' escape '/' and firmid not like '%D/_%' escape '/' ";
		if (bankID != null && bankID.trim().length() > 0 && !bankID.trim().equals("-1")) {
			filter2 += " and bankID='" + bankID + "' ";
		}
		try {
			java.util.Date yestody = getlastDate(qdate, conn);
			sql = "select h.firmid," + "nvl(a.value,0)" + "+(case when nvl(k.value,0)>0 then nvl(k.value,0) else 0 end)" + " AddTranAmount," + // --销售收入
					"nvl(b.value,0)" + "+(case when nvl(k.value,0)<0 then nvl(-k.value,0) else 0 end)" + " CutTranAmount," + // --购货支出
					"nvl(c.value,0) ProfitAmount," + // --盈利
					"nvl(d.value,0) LossAmount," + // --亏损

			// st
			"j.bankid bankid , " + "j.bankname bankName ," + "nvl(Fee.value,0) Fee ," + "nvl(OutMoney.value, 0) OutMoney," + // 当日出金
					"nvl(InMoney.value, 0) InMoney," + // 当日入金
					"nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + nvl(d1.money, 0)+ nvl(e1.money, 0) todayQY,"
					+ // 当日权益
					"nvl(a2.todaybalance, 0) + nvl(b2.RuntimeMargin, 0) + nvl(b2.RuntimeFL, 0) + nvl(b2.RuntimeSettleMargin, 0) + nvl(c2.floatingloss, 0) + nvl(d2.money, 0)+ nvl(e2.money, 0) lastDayQY,"
					+ // 上日权益
					"nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + nvl(d1.money, 0)+ nvl(e1.money, 0) -"
					+ "(nvl(a2.todaybalance, 0) + nvl(b2.RuntimeMargin, 0) + nvl(b2.RuntimeFL, 0) + nvl(b2.RuntimeSettleMargin, 0) + nvl(c2.floatingloss, 0) + nvl(d2.money, 0)+ nvl(e2.money, 0)) +"
					+ "nvl(Fee.value,0) + nvl(OutMoney.value, 0) - nvl(InMoney.value, 0) QYChangeMoney " + // 当日交易盈亏变化量
			// en
			"from " + "(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('Income','Income_Z','Income_V') " + filter + " group by fc.firmid) a, " + // --销售收入
					"(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('Payout','Payout_Z','Payout_V') " + filter + " group by fc.firmid) b, " + // --购货支出
					"(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('TradePL','SettlePL') and value>=0 " + filter
					+ " group by fc.firmid) c, " + // --盈利
					"(select fc.firmid,sum(nvl(-fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('TradePL','SettlePL') and value<0 " + filter + " group by fc.firmid) d, "
					+ // --亏损
					"(select fc.firmid,sum(nvl(case when fl.code='SettleCompens' then -fc.value else fc.value end,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') and fl.code in ('BankFee','TradeFee','SettleFee','TradeFee_Z','TradeFee_V','SettleFee_Z','SettleCompens') "
					+ filter + " group by fc.firmid) e, " + // --手续费
					"( " + "select nvl(fb.value,0)+nvl(fc.value,0)+nvl(fd.value,0)" +
					// +nvl(ff.value,0)
					"+nvl(fg.value,0) valued,nvl(fb1.value,0)+nvl(fc1.value,0)+nvl(fd1.value,0)" +
					// +nvl(ff1.value,0)
					"+nvl(fg1.value,0) valuez" + /*
													 * ,
													 * nvl(ff.value,0)-nvl(ff1.value
													 * ,0) ffvalue
													 */", fe.firmid from "
					+ "(select nvl(RuntimeSettleMargin,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') " + filter + ") fb, " + // --当日交收保证金
					"(select nvl(RuntimeFL,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') "
					+ filter + ") fc, " + // --当日浮亏
					"(select nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') " + filter + ") fd, " + // --当日远期保证金
					"(select nvl(sum(case when code = 'Margin_V' then value when code = 'Margin_Z' then value when code = 'MarginBack_V' then -value when code = 'MarginBack_Z' then -value else 0 end), 0) value,firmid from f_clientledger where code in ('MarginBack_V', 'Margin_V','MarginBack_Z', 'Margin_Z') and to_char(b_date,'yyyy-MM-dd')<='"
					+ Tool.fmtDate(qdate) + "' " + filter + " group by firmid) fg, " + // 当日现货竞价保证金

			"(select nvl(RuntimeSettleMargin,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody) + "','yyyy-MM-dd') "
					+ filter + ") fb1, " + // --上日交收保证金
					"(select nvl(RuntimeFL,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody) + "','yyyy-MM-dd') "
					+ filter + ") fc1, " + // --上日浮亏
					"(select nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody)
					+ "','yyyy-MM-dd') " + filter + ") fd1, " + // --上日远期保证金
					"(select nvl(sum(case when code = 'Margin_V' then value when code = 'Margin_Z' then value when code = 'MarginBack_V' then -value when code = 'MarginBack_Z' then -value else 0 end), 0) value,firmid from f_clientledger where code in ('MarginBack_V', 'Margin_V','MarginBack_Z', 'Margin_Z') and to_char(b_date,'yyyy-MM-dd')<='"
					+ Tool.fmtDate(yestody) + "' " + filter + " group by firmid) fg1, " + // 上日现货竞价保证金

			"(select firmid from f_b_firmidandaccount where 1=1 " + filter2 + ") fe " + // --和银行接口连接
					"where fe.firmid=fb.firmid(+) and fe.firmid=fc.firmid(+) and fe.firmid=fd.firmid(+)" +
					// and fe.firmid=ff.firmid(+)
					" and fe.firmid=fg.firmid(+) and fe.firmid=fb1.firmid(+) and fe.firmid=fc1.firmid(+) and fe.firmid=fd1.firmid(+)" +
					// and fe.firmid=ff1.firmid(+)
					" and fe.firmid=fg1.firmid(+) " + ") f, " + // --冻结解冻资金
					"(" + "select nvl(ia.value,0) valued,nvl(ib.value,0) valuez,ic.firmid from "
					+ "(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where trunc(cleardate)=to_date('" + Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') " + filter + " group by firmid) ia," + // 当日浮动盈亏
					"(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where trunc(cleardate)=to_date('" + Tool.fmtDate(yestody)
					+ "','yyyy-MM-dd') " + filter + " group by firmid) ib," + // 上日浮动盈亏
					"(select firmid from f_b_firmidandaccount where 1=1 " + filter2 + ") ic " + // 和银行接口表挂钩
					"where ic.firmid=ia.firmid(+) and ic.firmid=ib.firmid(+)" + ") i," + // 浮动盈亏
					"(select nvl(sum(nvl(value,0)),0) value,firmid  from f_clientledger where code in ('OtherItem','OtherItem_Z','OtherItem_V') and trunc(B_Date)=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') " + filter + " group by firmid) k, "
					+ "(select todaybalance value,firmid from f_firmbalance where b_date=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') "
					+ filter + ") g," + // --当日余额
					"(select account1,ACCOUNTNAME1,firmid,bankid from f_b_firmidandaccount where 1=1 " + filter2 + ") h ," + // --和银行接口连接

			// st
			"(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb + ") a2,"
					+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
					+ filter_t_h_f + ") b2," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 "
					+ filter_t_h_f + " group by firmid) c2,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
					+ filter_z_v + " group by firmid) d2,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
					+ filter_z_v + " group by firmid) e2," +

			"(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Deposit' " + filter_Fee + " group by firmid "
					+ ") InMoney," + // 交易商入金
					"(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Fetch' " + filter_Fee + " group by firmid "
					+ ") OutMoney," + // 交易商出金
			// "(select firmid,bankid,accountname,account,isopen,cardType, card from f_b_Firmidandaccount) i ,"+//交易商信息
			"(select fc.firmid firmid,nvl(sum(nvl(fc.value,0)),0) value from f_clientledger fc where exists (select code from f_ledgerfield fl where code like '%Fee%' and fc.code=fl.code ) "
					+ Fee_filter + " group by firmid" + ") Fee," + // 交易商手续费

			"(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb_today + ") a1,"
					+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
					+ filter_t_h_f_today + ") b1," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 "
					+ filter_t_h_f_today + " group by firmid) c1,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
					+ filter_z_v_today + " group by firmid) d1,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
					+ filter_z_v_today + " group by firmid) e1 ," + "(select bankid,bankName from f_b_banks where validflag = 0 ) j " +
					// en

			" where h.firmid=a.firmid(+) " + "and h.firmid=b.firmid(+) " + "and h.firmid=c.firmid(+) " + "and h.firmid=d.firmid(+) "
					+ "and h.firmid=e.firmid(+) " + "and h.firmid=f.firmid(+) " + "and h.firmid=g.firmid(+) " + "and h.firmid=i.firmid(+)" +
					// and h.firmid=j.firmid(+)
					" " + "and h.firmid=k.firmid(+) " +
					// st
					"and h.firmid = a2.firmid(+) " + "and h.firmid = b2.firmid(+) " + "and h.firmid = c2.firmid(+) " + "and h.firmid = d2.firmid(+) "
					+ "and h.firmid = e2.firmid(+) " + "and h.firmid = OutMoney.firmid(+) " + "and h.firmid = InMoney.firmid(+) "
					+ "and h.firmid = FEE.firmid(+) " + "and h.firmid = a1.firmid(+) " + "and h.firmid = b1.firmid(+) "
					+ "and h.firmid = c1.firmid(+) " + "and h.firmid = d1.firmid(+) " + "and h.firmid = e1.firmid(+) " + "and h.bankid = j.bankid";
			// en
			log("sql:" + sql);
			System.out.println("sql:" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			// Vector<BankValue> banksValue=getBankList(" where validflag = 0");
			Map<String, BankQSNetChild> map = new HashMap();
			while (rs.next()) {
				if ((BankQSNetChild) (map.get(rs.getString("bankid"))) != null) {
					BankQSNetChild value = (BankQSNetChild) (map.get(rs.getString("bankid")));
					value.bankName = rs.getString("bankname");
					value.bankId = rs.getString("bankid");
					value.BuyerPayment += rs.getDouble("CutTranAmount");
					value.InMoney += rs.getDouble("InMoney");
					value.lastDayQY += rs.getDouble("lastDayQY");
					value.Loss += rs.getDouble("LossAmount");
					value.OutMoney += rs.getDouble("OutMoney");
					value.Profit += rs.getDouble("ProfitAmount");
					value.QYchange += rs.getDouble("QYChangeMoney");
					value.sellerPayment += rs.getDouble("AddTranAmount");
					value.todayFee += rs.getDouble("Fee");
					value.toDayQY += rs.getDouble("toDayQY");
					map.put(value.bankId, value);
				} else {
					BankQSNetChild value = new BankQSNetChild();
					value.bankName = rs.getString("bankname");
					value.bankId = rs.getString("bankid");
					value.BuyerPayment = rs.getDouble("CutTranAmount");
					value.InMoney = rs.getDouble("InMoney");
					value.lastDayQY = rs.getDouble("lastDayQY");
					value.Loss = rs.getDouble("LossAmount");
					value.OutMoney = rs.getDouble("OutMoney");
					value.Profit = rs.getDouble("ProfitAmount");
					value.QYchange = rs.getDouble("QYChangeMoney");
					value.sellerPayment = rs.getDouble("AddTranAmount");
					value.todayFee = rs.getDouble("Fee");
					value.toDayQY = rs.getDouble("toDayQY");
					map.put(value.bankId, value);
				}
			}
			for (BankQSNetChild bc : map.values()) {
				vec.add(bc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		return vec;
	}

	// ---------------------------------------------国付宝 G商贸通 定制 begin
	// ---------------------------

	/**
	 * 取得市场签约流水记录列表
	 * 
	 * @param filter
	 * @param conn
	 *            数据库联接
	 * @return Vector 每项为CapitalInfo
	 * @throws SQLException
	 */
	public Vector<RgstCapitalValue> getRgstCapitalValue(String filter, Connection conn) throws SQLException {
		log("===>>>取得签约流水记录列表   getRgstCapitalValue  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		RgstCapitalValue value = null;
		Vector<RgstCapitalValue> list = new Vector<RgstCapitalValue>();
		try {
			if (conn == null) {
				conn = getConnection();
			}
			sql = "select * from F_B_RgstCapitalValue " + filter;
			System.out.println("取得签约流水记录列表:sql=" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new RgstCapitalValue();
				value.iD = rs.getLong("iD");
				value.actionID = rs.getLong("actionID");
				value.bankID = rs.getString("bankID");
				value.firmID = rs.getString("firmID");
				value.status = rs.getInt("status");
				value.type = rs.getInt("type");
				value.account = rs.getString("account");
				value.accountName = rs.getString("accountname");
				value.createTime = rs.getTimestamp("createtime");
				value.note = rs.getString("note");
				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeStatement(rs, state, null);
		}
		return list;
	}

	/**
	 * 修改市场签约流水记录列表
	 * 
	 * @param bankid
	 *            银行代码
	 * @param firmid
	 *            交易商代码
	 * @param status
	 *            银行流水状态
	 * @param actionid
	 *            市场流水号
	 * @param funid
	 *            银行流水号
	 * @param type
	 *            交易类型
	 * @param conn
	 *            数据库联接
	 * @return Vector 每项为CapitalInfo
	 * @throws SQLException
	 */
	public int modRgstCapitalValue(String bankid, String firmid, String account, Timestamp banktime, int status, long actionid, int type,
			Connection conn) throws SQLException {
		log("===>>>修改签约流水记录表和交易商银行对应关系表   modRgstCapitalValue  " + new java.util.Date());

		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "update F_B_RgstCapitalValue  set account='" + account + "',status='" + status + "' , banktime=sysdate where actionid='" + actionid
					+ "' ";
			log("modRgstCapitalValue1>>>>>>" + sql);

			state = conn.prepareStatement(sql);

			state.executeUpdate();
			if (status == 0) {
				log("银行处理成功>>>>");
				if (type == 1) {// 签约
					sql = " update f_b_firmidandaccount  set status='0' , isopen='1' , " + " opentime=sysdate ,account='" + account + "' "
							+ "where firmid='" + firmid + "' and bankid='" + bankid + "'";
				} else if (type == 2) {// 解约
					sql = " delete from f_b_firmidandaccount  where firmid='D_" + firmid + "'" + " and bankid='D_" + bankid + "' ";
					log("modRgstCapitalValue2>>>>>>" + sql);
					state = conn.prepareStatement(sql);
					state.executeUpdate();
					sql = " update f_b_firmidandaccount " + " set firmID='D_'||firmID,bankID='D_'||bankID," + " deltime=sysdate  where firmid='"
							+ firmid + "' and bankid='" + bankid + "' ";
				}
				log("modRgstCapitalValue3>>>>>>" + sql);
				state = conn.prepareStatement(sql);
				state.executeUpdate();
			} else {
				log("银行处理失败>>>>");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public int addRgstCapitalValue(RgstCapitalValue rc, Connection conn) throws SQLException {

		log("===>>>修改签约流水记录表和交易商银行对应关系表   modRgstCapitalValue  " + new java.util.Date());
		ResultSet rs = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		long id = -1;
		try {
			sql = "select seq_F_B_RgstCapitalValue.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			sql = "insert into F_B_RgstCapitalValue (id,ACTIONID,FIRMID,ACCOUNT,BANKID,"
					+ "TYPE,CREATETIME,BANKTIME,STATUS,ACCOUNTNAME,CARDTYPE,CARD,NOTE)" + "values(?,?,?,?,?,?,sysdate,null,?,?,?,?,?) ";
			log("addRgstCapitalValue>>>>>>" + sql);
			state = conn.prepareStatement(sql);
			state.setLong(1, id);
			state.setLong(2, rc.actionID);
			state.setString(3, rc.firmID);
			state.setString(4, rc.account);
			state.setString(5, rc.bankID);
			state.setInt(6, rc.type);
			state.setInt(7, rc.status);
			state.setString(8, rc.accountName);
			state.setString(9, rc.cardType);
			state.setString(10, rc.card);
			state.setString(11, rc.note);

			state.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	// -------------------------国付宝定制 end------------------------------

	// ----------------------------中行定制 begin---------------------------

	/**
	 * 查询客户账户状态对账信息
	 * 
	 * @throws ClassNotFoundException
	 */
	public Vector<ClientState> getClientState(String filter) throws SQLException, ClassNotFoundException {
		Vector<ClientState> states = new Vector<ClientState>();
		String sql = "";
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			sql = "select * from F_B_ACCOUNTSTATUS " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();

			while (rs.next()) {
				ClientState s = new ClientState();
				s.setBankNo(rs.getString("BANK_ID"));
				s.setStrandsNo(rs.getString("EXCHANGE_CODE"));
				s.setTransAddressCode(rs.getString("AREA_CODE"));
				s.setTransDate(new java.sql.Date(Tool.strToDate((rs.getString("TRADE_DATE"))).getTime()));
				s.setBankAccount(rs.getString("ACCOUNT"));
				s.setTransFundAcc(rs.getString("FUNDS_ACCOUNT"));
				s.setClientName(rs.getString("FIRM_NAME"));
				s.setMoneyType(rs.getString("CCYCODE"));
				s.setRemitMark(rs.getString("FLAG"));
				s.setSaveState(rs.getString("STATUS"));
				states.add(s);
			}

		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return states;
	}

	/**
	 * 添加客户账户状态对账信息
	 * 
	 * @throws ClassNotFoundException
	 */
	public int addClientState(ClientState state) throws SQLException, ClassNotFoundException {
		int i = 0;
		Connection conn = null;
		PreparedStatement st = null;
		String sql = "";
		try {
			conn = this.getConnection();
			sql = "INSERT INTO F_B_ACCOUNTSTATUS(BANK_ID,EXCHANGE_CODE,AREA_CODE,"
					+ "TRADE_DATE,ACCOUNT,FUNDS_ACCOUNT,FIRM_NAME,CCYCODE,FLAG,STATUS) " + " VALUES(?,?,?,?,?,?,?,?,?,?)";
			st = conn.prepareStatement(sql);
			st.setString(1, state.getBankNo());
			st.setString(2, state.getStrandsNo());
			st.setString(3, state.getTransAddressCode());
			st.setDate(4, state.getTransDate());
			st.setString(5, state.getBankAccount());
			st.setString(6, state.getTransFundAcc());
			st.setString(7, state.getClientName());
			st.setString(8, state.getMoneyType());
			st.setString(9, state.getRemitMark());
			st.setString(10, state.getSaveState());
			i = st.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(null, st, conn);
		}

		return i;
	}

	/** 转账交易明细信息 */
	public List<TransferAccountsTransactionDetailed> getZZJYMX(String bankID, String[] firmIDs, Date tradeDate, Connection conn) throws SQLException {
		// BankAdapter.log("查询转账交易明细信息");
		List<TransferAccountsTransactionDetailed> result = new ArrayList<TransferAccountsTransactionDetailed>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String filter1 = "";
		if (bankID != null && !"".equals(bankID)) {
			filter1 += "  AND a.bankid='" + bankID + "'";
		}
		String filter2 = "";
		if (tradeDate != null) {
			filter2 += " AND trunc(a.createtime) = to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
		}
		String filter = "";
		if (firmIDs != null) {
			String firms = "('aa'";
			for (int i = 0; i < firmIDs.length; i++) {
				firms += ",'" + firmIDs[i] + "'";
			}
			firms += ")";
			filter += " and a.firmid in " + firms;
		}
		String sql = "SELECT" + " a.createtime TransDateTime," + // 交易日期时间 yyyyMMDD hhmmss
				" a.funid BankSerialNumber," + // 银行流水号
				" a.actionid LaunchSerialNumber," + // 市场流水号
				" b.account BankAccount," + // 银行账号
				" a.firmid bondAcc," + // 资金账号
				" a.money money," + // 流水金额
				" (CASE a.type WHEN  0 THEN 1 WHEN 1 THEN 2 END) TransferDirection" + // 流水方向
				" FROM F_B_capitalInfo a, F_B_FIRMIDANDACCOUNT b" + " WHERE 1=1 " + filter2 + filter1 + filter
				+ " AND a.status= 0 AND a.firmid = b.firmid AND a.type <> 2";
		System.out.println(sql);
		try {
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				TransferAccountsTransactionDetailed tatd = new TransferAccountsTransactionDetailed();
				tatd.setBankCode("");// 银行代码
				tatd.setMarketCode("");
				tatd.setTransAddressCode("");
				tatd.setTransDateTime(Tool.fmtDate(rs.getDate("TransDateTime")));// 交易日期
				tatd.setTransTime(Tool.fmtOnlyTime(rs.getDate("TransDateTime")));// 交易时间
				tatd.setBankSerialNumber(rs.getString("BankSerialNumber"));// 银行流水号
				tatd.setLaunchSerialNumber(rs.getString("LaunchSerialNumber"));// 发起方流水号
				tatd.setBankAccount(rs.getString("BankAccount"));// 银行账号
				tatd.setBondAcc(rs.getString("bondAcc"));// 保证金账号
				tatd.setCertificationName("");// 客户名称
				tatd.setTransferDirection(rs.getString("TransferDirection"));// 流水方向
				tatd.setMoneyType("001");// 币种
				tatd.setCashExCode("0");// 钞汇标志
				tatd.setMoney(rs.getDouble("money"));
				result.add(tatd);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		return result;
	}

	/** 客户账户状态信息 */
	public List<AccountStatusReconciliation> getKHZHZT(String bankID, String[] firmIDs, Date tradeDate, Connection conn)
			throws SQLException, ClassNotFoundException {
		List<AccountStatusReconciliation> result = new ArrayList<AccountStatusReconciliation>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String filter = "";
		if (bankID != null && !"".equals(bankID)) {
			filter += "  AND b.bankid='" + bankID + "'";
		}

		String sql = "";

		try {
			sql = "SELECT" + " b.firmid bondAcc," + " b.account BankAccount," + " b.accountname certificationName,"
					+ " (CASE b.isopen WHEN  0 THEN 1 WHEN 1 THEN 0 END) CashExCode" + " FROM  f_b_firmidandaccount b" + " WHERE 1=1 "
					+ " AND (trunc(b.opentime) = to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')" + " OR trunc(b.deltime) = to_date('"
					+ Tool.fmtDate(tradeDate) + "','yyyy-MM-dd'))" + filter;
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				AccountStatusReconciliation asr = new AccountStatusReconciliation();
				asr.setBankCode(""); // 银行代码
				asr.setMarketCode(""); // 市场交易码
				asr.setTransAddressCode(""); // 证券地区码
				asr.setTransDateTime(Tool.fmtDate(tradeDate));// 交易日期
				asr.setBankAccount(rs.getString("BankAccount"));// 银行账号
				asr.setBondAcc(rs.getString("bondAcc"));// 证券资金账号
				asr.setCertificationName(rs.getString("certificationName"));// 客户姓名
				asr.setMoneyType("001"); // 币别
				asr.setCashExCode("0"); // 钞汇标志
				asr.setStatus(rs.getString("CashExCode")); // 存管状态
				result.add(asr);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		return result;
	}

	/** 存管客户资金交收明细 */
	public List<StorageMoneySettlementList> getCGKHZJJSMX(String bankID, Date tradeDate, Connection conn)
			throws SQLException, ClassNotFoundException {
		List<StorageMoneySettlementList> result = new ArrayList<StorageMoneySettlementList>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String filter = "";
		if (bankID != null && !"".equals(bankID)) {
			filter = " and a.bankid='" + bankID.trim() + "'";
		}
		String sql = "";
		try {
			sql = "SELECT" + " a.firmid bondAcc," + // 资金账号
					" a.accountname certificationName," + // 客户姓名
					" b.value money," + // 交收金额
					" (CASE WHEN b.value >= 0 THEN 0 ELSE 1 END) TradeDifference" + // 买卖差标志
					" FROM f_b_firmidandaccount a," + "(" + " SELECT c.firmid,nvl(sum(d.fieldsign*c.value),0) VALUE" + " FROM"
					+ " F_LEDGERFIELD d, F_CLIENTLEDGER c"
					+ " WHERE d.code = c.code and  c.code not in('Deposit','Fetch') and trunc(c.b_date) = to_date('" + Tool.fmtDate(tradeDate)
					+ "','yyyy-MM-dd') group by c.firmid" + ") b" + " WHERE a.firmid = b.firmid and a.firmid not like '%D%' " + filter;
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				StorageMoneySettlementList smsl = new StorageMoneySettlementList();
				smsl.setBankCode("");// 银行代码
				smsl.setMarketCode("");// 股商代码
				smsl.setTransAddressCode("");// 地区交易码
				smsl.setTransDateTime(Tool.fmtDate(tradeDate));// 交易日期
				smsl.setTaiZhangZhangHao("333");// 台账账号
				smsl.setBondAcc(rs.getString("bondAcc"));// 资金账号
				smsl.setCertificationName(rs.getString("certificationName"));// 客户姓名
				smsl.setTradeDifference(rs.getString("TradeDifference"));// 买卖差标志
				smsl.setMoneyType("001");
				smsl.setCashExCode("0");
				smsl.setMoney(rs.getDouble("money"));// 交收金额
				result.add(smsl);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		return result;
	}

	/** 存管客户资金台账余额明细 */
	public List<StorageMoneyLedgerBalanceList> getCGKHZJTZYEMX(String bankID, Date tradeDate, Connection conn)
			throws SQLException, ClassNotFoundException {
		List<StorageMoneyLedgerBalanceList> result = new ArrayList<StorageMoneyLedgerBalanceList>();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		String filter = "";
		if (bankID != null) {
			filter += " and bankid = '" + bankID + "'";
		}
		try {
			sql = " SELECT" + " a.firmid bondAcc," + " a.accountname certificationName," + " b.todaybalance money"
					+ " FROM f_b_firmidandaccount a,f_firmbalance b" + " WHERE a.firmid = b.firmid" + " AND a.isopen=1 and a.firmid not like '%D%'"
					+ " AND trunc(b.b_date) = to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')" + filter;
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				StorageMoneyLedgerBalanceList smlb = new StorageMoneyLedgerBalanceList();
				smlb.setBankCode("");
				smlb.setMarketCode("");
				smlb.setTransAddressCode("");
				smlb.setTransDateTime(Tool.fmtDate(tradeDate));
				smlb.setTaiZhangZhangHao("");
				smlb.setBondAcc(rs.getString("bondAcc"));
				smlb.setCertificationName(rs.getString("certificationName"));
				smlb.setMoneyType("001");
				smlb.setCashExCode("0");
				smlb.setMoney(rs.getDouble("money"));
				result.add(smlb);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		return result;
	}

	// --------------------------中行定制end-----------------------------
	// -------------------------通讯日志 start -----------------
	public int interfaceLog(InterfaceLog log) throws SQLException, ClassNotFoundException {
		log("插入银行接口和银行通讯信息  interfaceLog log[" + log.toString() + "]");
		Connection conn = null;
		PreparedStatement state = null;
		int result = 0;
		String sql = "insert into F_B_INTERFACELOG (LOGID,BANKID,LAUNCHER,TYPE,FIRMID,ACCOUNT,BEGINMSG,ENDMSG,RESULT,CREATETIME) values (SEQ_F_B_INTERFACELOG.NEXTVAL,?,?,?,?,?,?,?,?,sysdate)";
		try {
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			int n = 1;
			state.setString(n++, Tool.trim(log.bankID));
			state.setInt(n++, log.launcher);
			state.setInt(n++, log.type);
			state.setString(n++, Tool.trim(log.firmID));
			state.setString(n++, Tool.trim(log.account));
			state.setString(n++, Tool.trim(log.beginMsg));
			state.setString(n++, Tool.trim(log.endMsg));
			state.setInt(n++, log.result);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, conn);
		}
		return result;
	}

	/**
	 * 查询银行通讯日志信息
	 * 
	 * @param CorrespondValue
	 *            对应关系对象
	 * @param filter
	 *            SQL语句
	 * @return Vector<InterfaceLog> 日志信息列表对象
	 * @throws SQLException
	 *             ClassNotFoundException
	 */
	public Vector<InterfaceLog> interfaceLogList(String filter) throws SQLException, ClassNotFoundException {
		log("查询银行接口和银行通讯信息  interfaceLogList filter[" + filter + "]");
		Vector<InterfaceLog> result = new Vector<InterfaceLog>();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		InterfaceLog value = null;

		String sql = " select fbi.*,fbb.bankname from F_B_INTERFACELOG fbi inner join  F_B_BANKS fbb on fbi.bankid = fbb.bankid where 1=1 " + filter;
		try {
			conn = this.getConnection();
			// rs = PageQuery.executeQuery(conn, state, rs, sql, pageinfo);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new InterfaceLog();
				value.bankID = Tool.trim(rs.getString("bankID"));
				value.account = Tool.trim(rs.getString("account"));
				value.beginMsg = Tool.trim(rs.getString("beginMsg"));
				value.contact = Tool.trim(rs.getString("firmid"));
				value.createtime = rs.getTimestamp("createtime");
				value.endMsg = Tool.trim(rs.getString("endMsg"));
				value.launcher = rs.getInt("launcher");
				value.logID = rs.getLong("logID");
				value.type = rs.getInt("type");
				value.firmID = Tool.trim(rs.getString("firmID"));
				value.bankName = Tool.trim(rs.getString("bankName"));
				value.result = rs.getInt("result");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(rs, state, conn);
		}
		return result;
	}
	// -------------------------通讯日志 end -----------------

	/** 查询交易商扎差数据 */
	public List<BankQSNetChild> getQSFirmDate(String bankID, String firmID, java.util.Date qdate) throws Exception {
		log("获取某日的清算信息  getQSChild 时间：" + Tool.fmtDate(new java.util.Date()));
		List<BankQSNetChild> vec = new ArrayList<BankQSNetChild>();
		Connection conn = null;

		conn = this.getConnection();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = "";

		String filter_f_fb = " ";
		String filter_z_v = " ";
		String filter_t_h_f = " ";

		String filter_f_fb_today = " ";
		String filter_z_v_today = " ";
		String filter_t_h_f_today = " ";

		java.util.Date yes_qdate = getlastDate(qdate, conn);
		String filter_Fee = " and b_date=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') ";
		String Fee_filter = filter_Fee;
		if (qdate != null) {
			filter_f_fb = filter_f_fb + " and b_date = to_date('" + Tool.fmtDate(yes_qdate) + "', 'yyyy-MM-dd') ";
			filter_t_h_f = filter_t_h_f + " and cleardate = to_date('" + Tool.fmtDate(yes_qdate) + "', 'yyyy-MM-dd') ";
			filter_z_v = filter_z_v + " and b_date <= to_date('" + Tool.fmtDate(yes_qdate) + "', 'yyyy-MM-dd') ";

			filter_f_fb_today = filter_f_fb_today + " and b_date = to_date('" + Tool.fmtDate(qdate) + "', 'yyyy-MM-dd') ";
			filter_t_h_f_today = filter_t_h_f_today + " and cleardate = to_date('" + Tool.fmtDate(qdate) + "', 'yyyy-MM-dd') ";
			filter_z_v_today = filter_z_v_today + " and b_date <= to_date('" + Tool.fmtDate(qdate) + "', 'yyyy-MM-dd') ";
		}
		String filter2 = filter + " and isopen=1 and firmid not like '%/_D%' escape '/' and firmid not like '%D/_%' escape '/' ";
		if (bankID != null && bankID.trim().length() > 0 && !bankID.trim().equals("-1")) {
			filter2 += " and bankID='" + bankID + "' ";
		}
		if (firmID != null && firmID.trim().length() > 0 && !firmID.trim().equals("-1")) {
			filter2 += " and firmID='" + firmID + "' ";
		}
		try {
			java.util.Date yestody = getlastDate(qdate, conn);
			sql = "select h.firmid firmID ," + "nvl(a.value,0)" + "+(case when nvl(k.value,0)>0 then nvl(k.value,0) else 0 end)" + " AddTranAmount," + // --销售收入
					"nvl(b.value,0)" + "+(case when nvl(k.value,0)<0 then nvl(-k.value,0) else 0 end)" + " CutTranAmount," + // --购货支出
					"nvl(c.value,0) ProfitAmount," + // --盈利
					"nvl(d.value,0) LossAmount," + // --亏损

			// st
			"j.bankid bankid , " + "j.bankname bankName ," + "nvl(Fee.value,0) Fee ," + "nvl(OutMoney.value, 0) OutMoney," + // 当日出金
					"nvl(InMoney.value, 0) InMoney," + // 当日入金
					"nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + nvl(d1.money, 0)+ nvl(e1.money, 0) todayQY,"
					+ // 当日权益
					"nvl(a2.todaybalance, 0) + nvl(b2.RuntimeMargin, 0) + nvl(b2.RuntimeFL, 0) + nvl(b2.RuntimeSettleMargin, 0) + nvl(c2.floatingloss, 0) + nvl(d2.money, 0)+ nvl(e2.money, 0) lastDayQY,"
					+ // 上日权益
					"nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + nvl(d1.money, 0)+ nvl(e1.money, 0) -"
					+ "(nvl(a2.todaybalance, 0) + nvl(b2.RuntimeMargin, 0) + nvl(b2.RuntimeFL, 0) + nvl(b2.RuntimeSettleMargin, 0) + nvl(c2.floatingloss, 0) + nvl(d2.money, 0)+ nvl(e2.money, 0)) +"
					+ "nvl(Fee.value,0) + nvl(OutMoney.value, 0) - nvl(InMoney.value, 0) QYChangeMoney " + // 当日交易盈亏变化量
																											// en

			"from " + "(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('Income','Income_Z','Income_V') " + filter + " group by fc.firmid) a, " + // --销售收入
					"(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('Payout','Payout_Z','Payout_V') " + filter + " group by fc.firmid) b, " + // --购货支出
					"(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('TradePL','SettlePL') and value>=0 " + filter
					+ " group by fc.firmid) c, " + // --盈利
					"(select fc.firmid,sum(nvl(-fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('TradePL','SettlePL') and value<0 " + filter + " group by fc.firmid) d, "
					+ // --亏损
					"(select fc.firmid,sum(nvl(case when fl.code='SettleCompens' then -fc.value else fc.value end,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') and fl.code in ('BankFee','TradeFee','SettleFee','TradeFee_Z','TradeFee_V','SettleFee_Z','SettleCompens') "
					+ filter + " group by fc.firmid) e, " + // --手续费
					"( " + "select nvl(fb.value,0)+nvl(fc.value,0)+nvl(fd.value,0)"
					+ /* +nvl(ff.value,0) */"+nvl(fg.value,0) valued,nvl(fb1.value,0)+nvl(fc1.value,0)+nvl(fd1.value,0)"
					+ /* +nvl(ff1.value,0) */"+nvl(fg1.value,0) valuez" + /* , nvl(ff.value,0)-nvl(ff1.value,0) ffvalue */", fe.firmid from "
					+ "(select nvl(RuntimeSettleMargin,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') " + filter + ") fb, " + // --当日交收保证金
					"(select nvl(RuntimeFL,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') "
					+ filter + ") fc, " + // --当日浮亏
					"(select nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') " + filter + ") fd, " + // --当日远期保证金
					"(select nvl(sum(case when code = 'Margin_V' then value when code = 'Margin_Z' then value when code = 'MarginBack_V' then -value when code = 'MarginBack_Z' then -value else 0 end), 0) value,firmid from f_clientledger where code in ('MarginBack_V', 'Margin_V','MarginBack_Z', 'Margin_Z') and to_char(b_date,'yyyy-MM-dd')<='"
					+ Tool.fmtDate(qdate) + "' " + filter + " group by firmid) fg, " + // 当日现货竞价保证金

			"(select nvl(RuntimeSettleMargin,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody) + "','yyyy-MM-dd') "
					+ filter + ") fb1, " + // --上日交收保证金
					"(select nvl(RuntimeFL,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody) + "','yyyy-MM-dd') "
					+ filter + ") fc1, " + // --上日浮亏
					"(select nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody)
					+ "','yyyy-MM-dd') " + filter + ") fd1, " + // --上日远期保证金
					"(select nvl(sum(case when code = 'Margin_V' then value when code = 'Margin_Z' then value when code = 'MarginBack_V' then -value when code = 'MarginBack_Z' then -value else 0 end), 0) value,firmid from f_clientledger where code in ('MarginBack_V', 'Margin_V','MarginBack_Z', 'Margin_Z') and to_char(b_date,'yyyy-MM-dd')<='"
					+ Tool.fmtDate(yestody) + "' " + filter + " group by firmid) fg1, " + // 上日现货竞价保证金

			"(select firmid from f_b_firmidandaccount where 1=1 " + filter2 + ") fe " + // --和银行接口连接
					"where fe.firmid=fb.firmid(+) and fe.firmid=fc.firmid(+) and fe.firmid=fd.firmid(+)"
					+ /* and fe.firmid=ff.firmid(+) */" and fe.firmid=fg.firmid(+) and fe.firmid=fb1.firmid(+) and fe.firmid=fc1.firmid(+) and fe.firmid=fd1.firmid(+)"
					+ /* and fe.firmid=ff1.firmid(+) */" and fe.firmid=fg1.firmid(+) " + ") f, " + // --冻结解冻资金
					"(" + "select nvl(ia.value,0) valued,nvl(ib.value,0) valuez,ic.firmid from "
					+ "(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where trunc(cleardate)=to_date('" + Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') " + filter + " group by firmid) ia," + // 当日浮动盈亏
					"(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where trunc(cleardate)=to_date('" + Tool.fmtDate(yestody)
					+ "','yyyy-MM-dd') " + filter + " group by firmid) ib," + // 上日浮动盈亏
					"(select firmid from f_b_firmidandaccount where 1=1 " + filter2 + ") ic " + // 和银行接口表挂钩
					"where ic.firmid=ia.firmid(+) and ic.firmid=ib.firmid(+)" + ") i," + // 浮动盈亏
					"(select nvl(sum(nvl(value,0)),0) value,firmid  from f_clientledger where code in ('OtherItem','OtherItem_Z','OtherItem_V') and trunc(B_Date)=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') " + filter + " group by firmid) k, "
					+ "(select todaybalance value,firmid from f_firmbalance where b_date=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') "
					+ filter + ") g," + // --当日余额
					"(select account1,ACCOUNTNAME1,firmid,bankid from f_b_firmidandaccount where 1=1 " + filter2 + ") h ," + // --和银行接口连接

			// st
			"(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb + ") a2,"
					+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
					+ filter_t_h_f + ") b2," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 "
					+ filter_t_h_f + " group by firmid) c2,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
					+ filter_z_v + " group by firmid) d2,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
					+ filter_z_v + " group by firmid) e2," +

			"(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Deposit' " + filter_Fee + " group by firmid "
					+ ") InMoney," + // 交易商入金
					"(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Fetch' " + filter_Fee + " group by firmid "
					+ ") OutMoney," + // 交易商出金
										// "(select firmid,bankid,accountname,account,isopen,cardType, card from f_b_Firmidandaccount) i ,"+//交易商信息
			"(select fc.firmid firmid,nvl(sum(nvl(fc.value,0)),0) value from f_clientledger fc where exists (select code from f_ledgerfield fl where code like '%Fee%' and fc.code=fl.code ) "
					+ Fee_filter + " group by firmid" + ") Fee," + // 交易商手续费

			"(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb_today + ") a1,"
					+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
					+ filter_t_h_f_today + ") b1," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 "
					+ filter_t_h_f_today + " group by firmid) c1,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
					+ filter_z_v_today + " group by firmid) d1,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
					+ filter_z_v_today + " group by firmid) e1 ," + "(select bankid,bankName from f_b_banks where validflag = 0 ) j " +
					// en

			" where h.firmid=a.firmid(+) " + "and h.firmid=b.firmid(+) " + "and h.firmid=c.firmid(+) " + "and h.firmid=d.firmid(+) "
					+ "and h.firmid=e.firmid(+) " + "and h.firmid=f.firmid(+) " + "and h.firmid=g.firmid(+) " + "and h.firmid=i.firmid(+)"
					+ /* and h.firmid=j.firmid(+) */" " + "and h.firmid=k.firmid(+) " +
					// st
					"and h.firmid = a2.firmid(+) " + "and h.firmid = b2.firmid(+) " + "and h.firmid = c2.firmid(+) " + "and h.firmid = d2.firmid(+) "
					+ "and h.firmid = e2.firmid(+) " + "and h.firmid = OutMoney.firmid(+) " + "and h.firmid = InMoney.firmid(+) "
					+ "and h.firmid = FEE.firmid(+) " + "and h.firmid = a1.firmid(+) " + "and h.firmid = b1.firmid(+) "
					+ "and h.firmid = c1.firmid(+) " + "and h.firmid = d1.firmid(+) " + "and h.firmid = e1.firmid(+) " + "and h.bankid = j.bankid";
			// en
			log("sql:" + sql);
			System.out.println("sql:" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			// Vector<BankValue> banksValue=getBankList(" where validflag = 0");
			// Map <String,BankQSNetChild>map=new HashMap();
			while (rs.next()) {

				BankQSNetChild value = new BankQSNetChild();
				value.firmID = rs.getString("firmID");
				value.BuyerPayment = rs.getDouble("CutTranAmount");
				value.InMoney = rs.getDouble("InMoney");
				value.lastDayQY = rs.getDouble("lastDayQY");
				value.Loss = rs.getDouble("LossAmount");
				value.OutMoney = rs.getDouble("OutMoney");
				value.Profit = rs.getDouble("ProfitAmount");
				value.QYchange = rs.getDouble("QYChangeMoney");
				value.sellerPayment = rs.getDouble("AddTranAmount");
				value.todayFee = rs.getDouble("Fee");
				value.toDayQY = rs.getDouble("toDayQY");
				vec.add(value);

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeStatement(rs, state, null);
		}
		return vec;
	}

	// -------------------------------跨行定制 2013.03.21 start------------------------------------
	public Vector<BankTransferValue> getBankTransferList(String filter) throws SQLException, ClassNotFoundException {
		System.out.println("===>>>获得待审核银行间资金划转流水列表   getBankTransferList " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		BankTransferValue value = null;
		Vector<BankTransferValue> list = new Vector<BankTransferValue>();
		try {
			conn = getConnection();
			sql = "select bt.id,bt.actionid,bt.funid,bt.paybankid,bt.recbankid,bt.payacc,bt.recacc,bt.money,"
					+ "bt.createtime,bt.updatetime,bt.status,bt.note,bt.recFirmId,bt.capitalActionId,bt.type,bt.info,"
					+ "b1.bankname paybankname,b2.bankname recbankname,a1.info payaccname,a2.info recaccname "
					+ "from f_b_banktransfer bt,f_b_banks b1,f_b_banks b2,f_b_account a1,f_b_account a2 "
					+ "where bt.paybankid = b1.bankid and bt.recbankid = b2.bankid " + "and bt.payacc = a1.code and bt.recacc = a2.code " + filter;

			System.out.println("sql = " + sql);

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new BankTransferValue();
				value.actionId = rs.getLong("actionId");
				value.createTime = rs.getTimestamp("createTime");
				value.funId = rs.getString("funId");
				value.id = rs.getLong("id");
				value.money = rs.getDouble("money");
				value.note = rs.getString("note");
				value.payAcc = rs.getString("payAcc");
				value.payBankId = rs.getString("payBankId");
				value.recAcc = rs.getString("recAcc");
				value.recBankId = rs.getString("recBankId");
				value.status = rs.getInt("status");
				value.updateTime = rs.getTimestamp("updateTime");

				value.payBankName = rs.getString("paybankname");
				value.recBankName = rs.getString("recbankname");
				value.payAccName = rs.getString("payaccname");
				value.recAccName = rs.getString("recAccName");
				value.recFirmId = rs.getString("recFirmId");

				value.capitalActionId = rs.getLong("capitalActionId");
				value.type = rs.getInt("type");
				value.info = rs.getString("info");

				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return list;
	}

	@Override
	public Vector<BankTransferValue> getBankTransferList(String filter, Connection conn) throws SQLException, ClassNotFoundException {
		System.out.println("===>>>获得待审核银行间资金划转流水列表   getBankTransferList " + new java.util.Date());

		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		BankTransferValue value = null;
		Vector<BankTransferValue> list = new Vector<BankTransferValue>();
		try {

			sql = "select bt.id,bt.actionid,bt.funid,bt.paybankid,bt.recbankid,bt.payacc,bt.recacc,bt.money,"
					+ "bt.createtime,bt.updatetime,bt.status,bt.note,bt.recFirmId,bt.capitalActionId,bt.type,bt.info,"
					+ "b1.bankname paybankname,b2.bankname recbankname,a1.info payaccname,a2.info recaccname "
					+ "from f_b_banktransfer bt,f_b_banks b1,f_b_banks b2,f_b_account a1,f_b_account a2 "
					+ "where bt.paybankid = b1.bankid and bt.recbankid = b2.bankid " + "and bt.payacc = a1.code and bt.recacc = a2.code " + filter;

			System.out.println("sql = " + sql);

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new BankTransferValue();
				value.actionId = rs.getLong("actionId");
				value.createTime = rs.getTimestamp("createTime");
				value.funId = rs.getString("funId");
				value.id = rs.getLong("id");
				value.money = rs.getDouble("money");
				value.note = rs.getString("note");
				value.payAcc = rs.getString("payAcc");
				value.payBankId = rs.getString("payBankId");
				value.recAcc = rs.getString("recAcc");
				value.recBankId = rs.getString("recBankId");
				value.status = rs.getInt("status");
				value.updateTime = rs.getTimestamp("updateTime");

				value.payBankName = rs.getString("paybankname");
				value.recBankName = rs.getString("recbankname");
				value.payAccName = rs.getString("payaccname");
				value.recAccName = rs.getString("recAccName");
				value.recFirmId = rs.getString("recFirmId");

				value.capitalActionId = rs.getLong("capitalActionId");
				value.type = rs.getInt("type");
				value.info = rs.getString("info");

				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return list;
	}

	@Override
	public long modBankTransfer(long id, int status, Connection conn) throws SQLException, ClassNotFoundException {
		log("===>>>修改银行间资金划转流水记录状态   modBankTransfer  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {

			sql = "update F_B_BankTransfer set status=?,updateTime=sysdate where id=?";

			state = conn.prepareStatement(sql);
			state.setInt(1, status);
			state.setLong(2, id);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	@Override
	public Vector<Account> getAccList(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>获得银行端科目列表   getAccList " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		Account value = null;
		Vector<Account> list = new Vector<Account>();
		try {
			conn = getConnection();
			sql = "select * from F_B_Account " + filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new Account();
				value.code = rs.getString("code");
				value.info = rs.getString("info");
				list.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return list;
	}

	public long addBankTransfer(BankTransferValue val, Connection conn) throws SQLException, ClassNotFoundException {
		log("===>>>增加银行间资金划转流水记录   addBankTransfer  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1;
		try {
			val.actionId = getActionID(conn);
			sql = "select seq_F_B_bankTransfer.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			sql = "insert into F_B_bankTransfer(id, actionId, funId, payBankId, recBankId, payAcc, recAcc,"
					+ " money, createTime, updateTime, status, note ,recFirmId,capitalActionId,type,info) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?,sysdate,sysdate, ?, ?, ?, ?, ?, ?)";

			state = conn.prepareStatement(sql);
			state.setLong(1, id);
			state.setLong(2, val.actionId);
			state.setString(3, val.funId);
			state.setString(4, val.payBankId);
			state.setString(5, val.recBankId);
			state.setString(6, val.payAcc);
			state.setString(7, val.recAcc);
			state.setDouble(8, val.money);
			state.setInt(9, val.status);
			state.setString(10, val.note);
			state.setString(11, val.recFirmId);
			state.setLong(12, val.capitalActionId);
			state.setInt(13, val.type);
			state.setString(14, val.info);

			state.executeUpdate();
			System.out
					.println("【新增银行间资金划转流水】:流水号[" + id + "]" + "银行流水号[" + val.funId + "]" + "市场流水号[" + val.actionId + "]" + "金额[" + val.money + "]");
		} catch (SQLException e) {
			e.printStackTrace();
			id = -1;
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return id;
	}

	@Override
	public long addBankTransfer(BankTransferValue val) throws SQLException, ClassNotFoundException {
		log("===>>>增加银行间资金划转流水记录   addBankTransfer  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1;
		long actionId;
		Connection conn = null;
		try {
			conn = getConnection();

			val.actionId = getActionID(conn);
			actionId = val.actionId;
			sql = "select seq_F_B_bankTransfer.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			sql = "insert into F_B_bankTransfer(id, actionId, funId, payBankId, recBankId, payAcc, recAcc,"
					+ " money, createTime, updateTime, status, note ,recFirmId,capitalActionId,type,info) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?,sysdate,sysdate, ?, ?, ?, ?, ?, ?)";

			state = conn.prepareStatement(sql);
			state.setLong(1, id);
			state.setLong(2, val.actionId);
			state.setString(3, val.funId);
			state.setString(4, val.payBankId);
			state.setString(5, val.recBankId);
			state.setString(6, val.payAcc);
			state.setString(7, val.recAcc);
			state.setDouble(8, val.money);
			state.setInt(9, val.status);
			state.setString(10, val.note);
			state.setString(11, val.recFirmId);
			state.setLong(12, val.capitalActionId);
			state.setInt(13, val.type);
			state.setString(14, val.info);

			state.executeUpdate();
			System.out
					.println("【新增银行间资金划转流水】:流水号[" + id + "]" + "银行流水号[" + val.funId + "]" + "市场流水号[" + val.actionId + "]" + "金额[" + val.money + "]");
		} catch (SQLException e) {
			e.printStackTrace();
			id = -1;
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return actionId;
	}

	/** ------工行跨行汇拨 start zjj 2012.09.27 ------- */
	public TransferBank getTransferBank(String id) throws SQLException, ClassNotFoundException {
		TransferBank transferBank = new TransferBank();
		String sql = "SELECT * FROM F_B_BANKACCOUNT WHERE　BANKID='" + id + "'";
		System.out.println("查询转账银行帐户SQL" + sql);
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				transferBank = getTransferBank(rs);
			}

		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			this.closeStatement(null, state, conn);
		}
		return transferBank;
	}

	public TransferBank getTransferBank(ResultSet rs) throws SQLException {
		TransferBank transferBank = new TransferBank();
		transferBank.bankID = rs.getString("BANKID");
		transferBank.bankName = rs.getString("BANKNAME");
		transferBank.Id = rs.getString("ACCOUNT");
		transferBank.Name = rs.getString("ACCOUNTNAME");
		transferBank.OpFlg = rs.getString("OPENFLAG");
		transferBank.Pwd = rs.getString("PASSWORD");
		transferBank.RegDt = rs.getString("REGISTERDATE");
		transferBank.status = rs.getString("STATUS");
		transferBank.VldDt = rs.getString("VALIDDATE");
		transferBank.accountType = rs.getInt("TYPE");
		transferBank.bankNum = rs.getString("RCVBNKCODE");
		return transferBank;

	}

	/** ------工行跨行汇拨 zjj 2012.09.27 ------- */
	// -------------------------------跨行定制 2013.03.21 end------------------------------------
	@Override
	public Vector<CitysValue> getCityOfBank(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>取得他行签约所需的开户行所在地信息  getCityOfBank  ");
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		Vector<CitysValue> result = new Vector<CitysValue>();
		CitysValue value = null;
		try {
			conn = getConnection();
			sql = "select * from f_b_citys " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new CitysValue();
				value.ID = rs.getString("ID");
				value.cityName = rs.getString("cityName");
				value.parentID = rs.getString("parentID");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	@Override
	public Vector<BanksInfoValue> getBanksInfo(String filter) throws SQLException, ClassNotFoundException {
		log("===>>>取得他行签约所需他行信息  getBanksInfo  ");
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		Vector<BanksInfoValue> result = new Vector<BanksInfoValue>();
		BanksInfoValue value = null;
		try {
			conn = getConnection();
			sql = "select * from F_B_mbfenetbank " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new BanksInfoValue();
				value.nbkcode = rs.getString("nbkcode");
				value.sabkcode = rs.getString("sabkcode");
				value.bnkcity = rs.getString("bnkcity");
				value.nbkname = rs.getString("nbkname");
				value.nbksname = rs.getString("nbksname");
				value.nbkaddrss = rs.getString("nbkaddrss");
				value.cnttel = rs.getString("cnttel");
				value.cntper = rs.getString("cntper");
				value.postcode = rs.getString("postcode");
				value.nbkstate = rs.getString("nbkstate");
				value.bkemail = rs.getString("bkemail");
				value.content = rs.getString("content");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

}
