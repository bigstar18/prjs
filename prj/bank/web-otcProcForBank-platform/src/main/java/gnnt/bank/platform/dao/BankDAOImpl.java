package gnnt.bank.platform.dao;

import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import gnnt.bank.adapter.bankBusiness.dayData.FCS_10_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_11_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_13_Result;
import gnnt.bank.adapter.bankBusiness.dayData.FCS_99;
import gnnt.bank.adapter.bankBusiness.message.CEB_param;
import gnnt.bank.platform.util.Arith;
import gnnt.bank.platform.util.Encryption;
import gnnt.bank.platform.util.Tool;
import gnnt.bank.platform.vo.CapitalValueMoney;
import gnnt.bank.platform.vo.CheckMessage;
import gnnt.trade.bank.vo.AbcInfoValue;
import gnnt.trade.bank.vo.Account;
import gnnt.trade.bank.vo.BankCode;
import gnnt.trade.bank.vo.BankCompareInfoValue;
import gnnt.trade.bank.vo.BankQSNetChild;
import gnnt.trade.bank.vo.BankSumDate;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalCompare;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CityValue;
import gnnt.trade.bank.vo.CompareResult;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.DicValue;
import gnnt.trade.bank.vo.FeeInfoVO;
import gnnt.trade.bank.vo.Firm;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.FirmBalance_CEB;
import gnnt.trade.bank.vo.FirmBankFunds;
import gnnt.trade.bank.vo.FirmFundsValue;
import gnnt.trade.bank.vo.FirmID2SysFirmID;
import gnnt.trade.bank.vo.FirmMessageVo;
import gnnt.trade.bank.vo.FirmOpenCloseBank;
import gnnt.trade.bank.vo.FirmTradeStatus;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.FrozenBalanceVO;
import gnnt.trade.bank.vo.FundsAndInterests;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.LogValue;
import gnnt.trade.bank.vo.MFirmValue;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.QSChangeResult;
import gnnt.trade.bank.vo.QSRresult;
import gnnt.trade.bank.vo.QueryTradeData;
import gnnt.trade.bank.vo.RZQS;
import gnnt.trade.bank.vo.RgstCapitalValue;
import gnnt.trade.bank.vo.SystemMessage;
import gnnt.trade.bank.vo.SystemQSValue;
import gnnt.trade.bank.vo.TradeDetailAccount;
import gnnt.trade.bank.vo.TradeResultValue;
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

public class BankDAOImpl extends BankDAO {
	public BankDAOImpl() throws Exception {
	}

	private String getSQL(String sql, Set<String> keys) {
		StringBuffer sbf = new StringBuffer(sql);
		for (String str : keys) {
			sbf.append(" " + str);
		}
		return sbf.toString();
	}

	private int setStatementValues(PreparedStatement st, Map<String, Object> map) throws SQLException {
		Set<String> keys = map.keySet();
		int result = 0;
		int i = 0;
		for (String str : keys) {
			i++;
			if ("String".equalsIgnoreCase(map.get(str).getClass().getSimpleName())) {
				st.setString(i, (String) map.get(str));
			} else if ("Long".equalsIgnoreCase(map.get(str).getClass().getSimpleName())) {
				st.setLong(i, ((Long) map.get(str)).longValue());
			} else if ("Double".equalsIgnoreCase(map.get(str).getClass().getSimpleName())) {
				st.setDouble(i, ((Double) map.get(str)).doubleValue());
			} else if ("Date".equalsIgnoreCase(map.get(str).getClass().getSimpleName())) {
				st.setDate(i, (java.sql.Date) map.get(str));
			} else if ("Timestamp".equalsIgnoreCase(map.get(str).getClass().getSimpleName())) {
				st.setTimestamp(i, (Timestamp) map.get(str));
			} else {
				result = -1;
				return result;
			}
		}
		return result;
	}

	public int modCapitalInfoStatus(long id, String funID, int status, Timestamp bankTime, Connection conn) throws SQLException {
		Tool.log("===>>>修改资金流水记录状态   modCapitalInfoStatus  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			String funidf = "";
			int n = 1;
			if ((funID == null) || (funID.trim().length() <= 0)) {
				funID = "";
			} else {
				funidf = ",funid2=?";
			}
			sql = "update F_B_capitalInfo set status=?,FUNID=?,bankTime=?" + funidf + " where id=?";

			state = conn.prepareStatement(sql);
			state.setInt(n++, status);
			state.setString(n++, funID.trim());
			state.setTimestamp(n++, bankTime);
			if ((funidf != null) && (funidf.trim().length() > 0)) {
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

	public int modCapitalInfoStatusPT(long id, String funID, int status, Timestamp bankTime, Connection conn) throws SQLException {
		Tool.log("===>>>修改资金流水记录状态   modCapitalInfoStatus  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			String funidf = "";
			int n = 1;
			if ((funID == null) || (funID.trim().length() <= 0)) {
				funID = "";
			} else {
				funidf = ",funid2=?";
			}
			sql = "update F_B_capitalInfo_system set status=?,FUNID=?,bankTime=?" + funidf + " where id=?";

			state = conn.prepareStatement(sql);
			state.setInt(n++, status);
			state.setString(n++, funID.trim());
			state.setTimestamp(n++, bankTime);
			if ((funidf != null) && (funidf.trim().length() > 0)) {
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

	public int modCapitalInfoStatus(long actionid, String funID, Connection conn) throws SQLException {
		Tool.log("===>>>修改资金流水记录状态   modCapitalInfoStatus  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			if (funID == null) {
				funID = "";
			}
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

	public int useBank(String bankID) throws SQLException, ClassNotFoundException {
		Tool.log("判断现在是否可以转账");
		int result = 4;
		BankValue bv = getBank(bankID);
		if (bv == null) {
			return result;
		}
		if (bv.control == 1) {
			result = 0;
		} else if (bv.control == 2) {
			if (getTradeDate(new java.util.Date())) {
				result = 0;
			} else {
				result = 1;
			}
		} else if (bv.control == 3) {
			int n = getTradeTime(bv.beginTime, bv.endTime);
			switch (n) {
			case 0:
				result = 0;
				break;
			case 1:
				result = 2;
				break;
			case 2:
				result = 3;
			}
		} else if (bv.control == 0) {
			if (getTradeDate(new java.util.Date())) {
				int n = getTradeTime(bv.beginTime, bv.endTime);
				switch (n) {
				case 0:
					result = 0;
					break;
				case 1:
					result = 2;
					break;
				case 2:
					result = 3;
				}
			} else {
				result = 1;
			}
		}
		return result;
	}

	private int getTradeTime(String startTime, String endTime) {
		Tool.log("判断是否超出了交易时间范围");
		int result = 1;
		if ((startTime == null) || (startTime.trim().length() <= 0) || (endTime == null) || (endTime.trim().length() <= 0)) {
			return 0;
		}
		startTime = startTime.trim();
		endTime = endTime.trim();
		if (startTime.length() < 6) {
			for (int i = 0; i < 6 - startTime.length(); i++) {
				startTime = startTime + "0";
			}
		}
		if (endTime.length() < 6) {
			for (int i = 0; i < 6 - startTime.length(); i++) {
				endTime = endTime + "0";
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

	public boolean getTradeDate(java.util.Date tradeDate) throws SQLException, ClassNotFoundException {
		Tool.log("判断是否为交易日");
		boolean flag = true;
		Calendar c = Calendar.getInstance();
		c.setTime(tradeDate);
		int dayOfWeek = c.get(7);
		String date = Tool.fmtDate(tradeDate);
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "select * from t_a_nottradeday";
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {

				String nweeks = rs.getString("week");
				String ndates = rs.getString("day");
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
			closeStatement(rs, state, conn);
		}
		return flag;
	}

	public Vector<FirmBalanceValue> getFlote(String[] firmIDs, Connection conn) throws SQLException, ClassNotFoundException {
		Vector<FirmBalanceValue> result = new Vector();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = "";
		if ((firmIDs != null) && (firmIDs.length > 0)) {
			String firms = "";
			for (String firmID : firmIDs) {
				if ((firmID != null) && (firmID.trim().length() > 0)) {
					firms = firms + "'" + firmID.trim() + "',";
				}
			}
			if ((firms != null) && (firms.trim().length() > 0)) {
				filter = filter + " and firmid in (" + firms.substring(0, firms.length() - 1) + ") ";
			}
		}
		try {
			sql = "select sum(floatingloss) floatingloss,firmID from t_holdposition where 1=1 " + filter + " group by firmid order by firmid";
			Tool.log(sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmBalanceValue value = new FirmBalanceValue();
				value.firmId = rs.getString("firmID");
				value.floatingloss = rs.getDouble("floatingloss");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public Vector<FirmBalanceValue> getFlote(String[] firmIDs) throws SQLException, ClassNotFoundException {
		Vector<FirmBalanceValue> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = "";
		if ((firmIDs != null) && (firmIDs.length > 0)) {
			String firms = "";
			for (String firmID : firmIDs) {
				if ((firmID != null) && (firmID.trim().length() > 0)) {
					firms = firms + "'" + firmID.trim() + "',";
				}
			}
			if ((firms != null) && (firms.trim().length() > 0)) {
				filter = filter + " and firmid in (" + firms.substring(0, firms.length() - 1) + ") ";
			}
		}
		try {
			sql = "select sum(floatingloss) floatingloss,firmID from t_holdposition where 1=1 " + filter + " group by firmid order by firmid";
			Tool.log(sql);
			conn = getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmBalanceValue value = new FirmBalanceValue();
				value.firmId = rs.getString("firmID");
				value.floatingloss = rs.getDouble("floatingloss");
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

	public int modCapitalInfoNote(long id, String note, Connection conn) throws SQLException {
		Tool.log("===>>>修改资金流水记录描述   modCapitalInfoNote  " + new java.util.Date());
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

	public int modCapitalInfoNotePT(long id, String note, Connection conn) throws SQLException {
		Tool.log("===>>>修改资金流水记录描述   modCapitalInfoNote  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "update F_B_capitalInfo_system set note='" + note.trim() + "' where id=" + id + " ";
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

	public static void main(String[] args) {
		double d = 88.879999999999995D;
		long l = Math.round(d * 100.0D);
		System.out.println(l);

		long ll = 100L;
		double dd = ll;
		System.out.println(dd);
	}

	public long getActionID(Connection conn) throws SQLException {
		Tool.log("===>>>取得市场业务流水号   getActionID  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1L;
		try {
			sql = "select seq_F_B_action.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			id = -1L;
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return id;
	}

	public long getActionID() {
		Tool.log("===>>>取得市场业务流水号   getActionID  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = 0L;
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

	public long addCapitalInfo(CapitalValue val, Connection conn) throws SQLException {
		Tool.log("===>>>增加资金流水记录   addCapitalInfo  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1L;
		try {
			sql = "select seq_F_B_capitalInfo.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			sql = "insert into F_B_capitalInfo(ID, FIRMID, FUNID, BANKID, DEBITID, CREDITID, TYPE, MONEY, OPERATOR, CREATETIME, BANKTIME, STATUS, NOTE, ACTIONID,EXPRESS,bankName,account,createdate,funid2,systemID,sysFirmID,tradeSource) values(?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?,?,?,?,to_char(sysdate,'yyyy-MM-dd'),?,?,?,?)";

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
			state.setString(17, (val.funID == null) || (val.funID.trim().length() <= 0) ? "gnnt" + id : val.funID);
			state.setString(18, val.systemID);
			state.setString(19, val.sysFirmID);
			state.setString(20, val.tradeSource);
			state.executeUpdate();
			System.out
					.println("【平台<-->银行新增流水】:流水号[" + id + "]" + "银行流水号[" + val.funID + "]" + "市场流水号[" + val.actionID + "]" + "金额[" + val.money + "]");
		} catch (SQLException e) {
			e.printStackTrace();
			id = -1L;
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return id;
	}

	public Vector<CapitalValue> getCapitalInfoList(String filter, Connection conn) throws SQLException {
		Tool.log("===>>>取得资金流水记录列表   getCapitalInfoList  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalValue value = null;
		Vector<CapitalValue> list = new Vector();
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
				value.sysFirmID = rs.getString("sysfirmID");
				value.systemID = rs.getString("systemID");
				value.tradeSource = rs.getString("tradeSource");
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

	public Vector<CapitalValue> getCapitalInfoList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得资金流水记录列表   getCapitalInfoList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalValue value = null;
		Vector<CapitalValue> list = new Vector();
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
				value.sysFirmID = rs.getString("sysfirmID");
				value.systemID = rs.getString("systemID");
				value.tradeSource = rs.getString("tradeSource");
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

	public Vector<CapitalValue> getCapitalInfoListPT(String filter) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		Vector<CapitalValue> list = new Vector();
		try {
			conn = getConnection();
			list = getCapitalInfoListPT(filter, conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, null, conn);
		}
		return list;
	}

	public Vector<CapitalValue> getCapitalInfoListPT(String filter, Connection conn) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得资金流水记录列表   getCapitalInfoList  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalValue value = null;
		Vector<CapitalValue> list = new Vector();
		try {
			sql = "select * from F_B_capitalInfo_system " + filter;
			System.out.println("查询SQL=[" + sql + "]");
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
				value.sysFirmID = rs.getString("sysfirmID");
				value.systemID = rs.getString("systemID");
				value.tradeSource = rs.getString("tradeSource");
				value.feeMoney = rs.getDouble("feemoney");
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

	public Vector<CapitalValue> getCapitalInfoListPT(int pageSize, int pageIndex, String filter, String orderBy)
			throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得资金流水记录列表   getCapitalInfoList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalValue value = null;
		Vector<CapitalValue> list = new Vector();
		try {
			conn = getConnection();

			sql = "select * from F_B_capitalInfo_system ";
			if ((filter != null) && (!filter.trim().equals(""))) {
				sql = sql + filter;
			}
			if ((orderBy != null) && (!orderBy.trim().equals(""))) {
				sql = sql + " order by " + orderBy;
			}
			int startCount = (pageIndex - 1) * pageSize + 1;
			int endCount = pageIndex * pageSize;

			sql = "select * from (select rownum num,t.* from ( " + sql + ") t ) " + "where num between " + startCount + " and " + endCount;
			System.out.println("查询SQL=[" + sql + "]");
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
				value.sysFirmID = rs.getString("sysfirmID");
				value.systemID = rs.getString("systemID");
				value.tradeSource = rs.getString("tradeSource");
				value.feeMoney = rs.getDouble("feemoney");
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

	public Vector<CapitalValue> getCapitalInfoListNotice(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得资金流水记录列表   getCapitalInfoList  " + new java.util.Date());
		Connection conn = null;
		Vector<CapitalValue> list = new Vector();
		try {
			conn = getConnection();
			list = getCapitalInfoListNotice(filter, conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, null, conn);
		}
		return list;
	}

	public Vector<CapitalValue> getCapitalInfoListNotice(String filter, Connection conn) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得资金流水记录列表   getCapitalInfoList  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalValue value = null;
		Vector<CapitalValue> list = new Vector();
		try {
			sql = "select * from F_B_capitalInfo_notice " + filter;

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
				value.sysFirmID = rs.getString("sysfirmID");
				value.systemID = rs.getString("systemID");
				value.tradeSource = rs.getString("tradeSource");
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

	public Vector<CapitalValue> getCapitalInfoList2(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得资金流水记录列表   getCapitalInfoList2  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CapitalValue value = null;
		Vector<CapitalValue> list = new Vector();
		try {
			conn = getConnection();

			sql = "select c.*,f.accountname firmName,b.bankName bbankName from f_b_capitalinfo c,f_b_firmidandaccount f,f_b_banks b where f.firmid(+)=c.firmid and c.bankid=b.bankid(+) "
					+ filter;
			Tool.log("sql:" + sql);
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
				value.contact = rs.getString("contact");
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

	public Vector<BankCompareInfoValue> getBankCapInfoList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得银行资金流水记录列表   getBankCapInfoList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		BankCompareInfoValue value = null;
		Vector<BankCompareInfoValue> list = new Vector();
		try {
			conn = getConnection();

			sql = "select id,funid,firmid,bankid,account,type,money,nvl(to_char(compareDate,'yyyy-MM-dd'),'') cDate,nvl(Note,'') note,nvl(to_char(createtime,'yyyy-MM-dd'),'') cTime,status from f_b_bankcompareinfo "
					+ filter;

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

	public double sumCapitalInfo(String filter, Connection conn) throws SQLException {
		Tool.log("===>>>合计资金流水金额   sumCapitalInfo  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		double result = 0.0D;
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

	public void addBank(BankValue val) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>增加银行   addBank  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = getConnection();
			String sql = " insert into F_B_banks  (bankid, bankname, maxpertransmoney, maxpertranscount, adapterclassname, validflag, maxPerSglTransMoney,maxAuditMoney,beginTime,endTime,control)  values(?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

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

	public void modBank(BankValue val) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>修改银行   modBank  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = getConnection();
			String sql = "update F_B_banks set bankname = ?,maxpertransmoney = ?,maxpertranscount = ?,adapterclassname = ?,validflag = ?,maxPerSglTransMoney=?,maxAuditMoney=?,beginTime=?,endTime=?,control=? where bankid = ?";
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

	public void delBank(String bankID) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>删除银行   delBank  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = getConnection();
			String sql = "delete from F_B_banks where bankid='" + bankID + "'";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
	}

	public BankValue getBank(String bankID, Connection conn) throws SQLException {
		Tool.log("===>>>取得银行信息   getBank  " + new java.util.Date());
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

	public BankValue getBank(String bankID) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得银行信息   getBank  " + new java.util.Date());
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

	public Vector<BankValue> getBankList(String filter, Connection conn) throws SQLException {
		Tool.log("===>>>取得银行信息列表   getBankList  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		BankValue value = null;
		Vector<BankValue> list = new Vector();
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

	public Vector<BankValue> getBankList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得银行信息列表   getBankList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		BankValue value = null;
		Vector<BankValue> list = new Vector();
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

	public Firm getMFirmByFirmId(String firmId) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询市场交易商信息  " + new java.util.Date());
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

				firm.note = rs.getString("NOTE");
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

	public int addFirm(FirmValue val, Connection conn) throws SQLException {
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "insert into F_B_FirmUser(firmid, name, maxpertransmoney, maxpertranscount,  status, registerdate, logoutdate,maxPerSglTransMoney,maxAuditMoney,password) values(?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?)";

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

	public int addFirm(FirmValue val) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			conn = getConnection();

			sql = "insert into F_B_FirmUser(firmid, name, maxpertransmoney, maxpertranscount,   status, registerdate, logoutdate, maxPerSglTransMoney, maxAuditMoney, password) values(?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?)";

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

	public int modFirm(FirmValue val, Connection conn) throws SQLException {
		Tool.log("===>>>修改交易商   modFirm  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "update F_B_FirmUser  set name=?,maxpertransmoney=?,maxpertranscount=?,status =?,logoutdate=?,maxPerSglTransMoney=?,maxAuditMoney=?,password=? where firmid=?";

			state = conn.prepareStatement(sql);
			state.setString(1, val.name);
			state.setDouble(2, val.maxPerTransMoney);
			state.setInt(3, val.maxPerTransCount);
			state.setInt(4, val.status);
			state.setTimestamp(5, val.logoutDate);
			state.setDouble(6, val.maxPerSglTransMoney);
			state.setDouble(7, val.maxAuditMoney);
			state.setString(8, val.password);
			state.setString(9, val.firmID);
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

	public int modFirm(FirmValue val) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>修改交易商   modFirm  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			conn = getConnection();

			sql = "update F_B_FirmUser  set name = ?,maxpertransmoney = ?,maxpertranscount = ?,status = ?,logoutdate = ?,maxPerSglTransMoney=?,maxAuditMoney=?,password=?,sysToSysBalance=? where firmid = '"
					+ val.firmID + "'";

			state = conn.prepareStatement(sql);
			state.setString(1, val.name);
			state.setDouble(2, val.maxPerTransMoney);
			state.setInt(3, val.maxPerTransCount);
			state.setInt(4, val.status);
			state.setTimestamp(5, val.logoutDate);
			state.setDouble(6, val.maxPerSglTransMoney);
			state.setDouble(7, val.maxAuditMoney);
			state.setString(8, val.password);
			state.setDouble(9, val.sysToSysBalnce);

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

	public FirmValue getFirm(String firmID, Connection conn) throws SQLException {
		Tool.log("===>>>取得交易商   getFirm  " + new java.util.Date());
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

	public FirmValue getFirm(String firmID) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得交易商   getFirm  " + new java.util.Date());
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
				value.sysToSysBalnce = rs.getDouble("sysToSysBalance");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return value;
	}

	public Vector<FirmValue> getFirmList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得交易商信息列表   getFirmList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FirmValue value = null;
		Vector<FirmValue> list = new Vector();
		try {
			conn = getConnection();

			sql = "select * from F_B_FirmUser " + filter;

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

	public void addMoneyInfo(MoneyInfoValue val, Connection conn) throws SQLException {
		Tool.log("===>>>添加对账信息   addMoneyInfo  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1L;
		try {
			sql = "select seq_F_B_bankCompareInfo.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
		} catch (SQLException e) {
			Tool.log("取得id失败");
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		try {
			sql = "insert into F_B_BANKCOMPAREINFO(id,funid, firmid, account, type, money, comparedate, note, status,createtime,bankid) values(?, ?, ?, ?, ?, ?, ?, ?, ?,sysdate,?)";
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

	public long addMoneyInfo(MoneyInfoValue val) throws SQLException {
		Tool.log("===>>>添加对账信息   addMoneyInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1L;
		try {
			conn = getConnection();

			sql = "select seq_F_B_bankCompareInfo.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			sql = "insert into F_B_BANKCOMPAREINFO(id, funid, firmid, account, type, money, comparedate, note, status,createtime,bankid) values(?, ?, ?, ?, ?, ?, ?, ?, ?,sysdate,?)";

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
			id = -1L;
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			id = -1L;
		} finally {
			closeStatement(rs, state, conn);
		}
		return id;
	}

	public int delMoneyInfo(String id, Connection conn) throws SQLException {
		Tool.log("===>>>删除对账信息，不从数据库删除数据而是修改记录状态为已删除   delMoneyInfo  " + new java.util.Date());
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

	public Vector<MoneyInfoValue> getMoneyInfoList(String filter, Connection conn) throws SQLException {
		Tool.log("===>>>取得对账信息列表   getMoneyInfoList  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		MoneyInfoValue value = null;
		Vector<MoneyInfoValue> list = new Vector();
		try {
			sql = "select * from F_B_BANKCOMPAREINFO " + filter;
			Tool.log("sql:" + sql);
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

	public Vector<MoneyInfoValue> getMoneyInfoList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得对账信息列表   getMoneyInfoList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		MoneyInfoValue value = null;
		Vector<MoneyInfoValue> list = new Vector();
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

	public Vector<MoneyInfoValue> getUnionMoneyInfoList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得对账信息列表   getUnionMoneyInfoList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		MoneyInfoValue value = null;
		Vector<MoneyInfoValue> list = new Vector();
		try {
			conn = getConnection();

			sql = "select i.funid,c.id,i.bankid,i.type,i.money,c.money,i.account,i.firmID,i.comparedate  from F_B_capitalInfo c full join F_B_BANKCOMPAREINFO i on c.funid=i.funid "
					+ filter;

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

	public int modBankFrozenFuns(String filter, double money, Connection conn) throws SQLException {
		Tool.log("修改交易商银行接口冻结资金 " + filter + " " + money + new java.util.Date());
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
			closeStatement(null, state, null);
		}
		return result;
	}

	public int addCorrespond(CorrespondValue val, Connection conn) throws SQLException {
		Tool.log("===>>>添加帐号对应关系   addCorrespond  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "insert into F_B_firmidandaccount(bankid, firmid, account, status,accountname, bankname, bankprovince, bankcity, mobile, email,account1,isopen,cardtype,card,accountName1,inMarketCode,isCrossLine,OpenBankCode, contact, opentime) values(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,sysdate)";
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
			state.setString(17, val.isCrossLine);
			state.setString(18, val.OpenBankCode);
			state.setString(19, val.contact);
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

	public int delCorrespond(CorrespondValue val, Connection conn) throws SQLException {
		Tool.log("===>>>删除帐号对应关系   delCorrespond  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "delete F_B_firmidandaccount where bankid = '" + val.bankID + "'  " + "and firmid = '" + val.firmID + "'  and account = '"
					+ val.account + "'";
			System.out.println("执行SQL:[" + sql + "]");

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

	public int modCorrespond(CorrespondValue val, Connection conn) throws SQLException {
		Tool.log("===>>>修改帐号对应关系   modCorrespond conn  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "update f_b_firmidandaccount set status=" + val.status + ",accountname = '" + val.accountName + "'" + ",bankname = '" + val.bankName
					+ "',bankprovince = '" + val.bankProvince + "',bankcity = '" + val.bankCity + "'," + "mobile = '" + val.mobile + "',email = '"
					+ val.email + "',isopen=" + val.isOpen + ",cardtype = '" + val.cardType + "'" + ",card='" + val.card + "',account1='"
					+ val.account1 + "',account='" + val.account + "' ,accountName1 = '" + val.accountName1 + "'" + ",inMarketCode='"
					+ val.inMarketCode + "'" + ",isCrossLine='" + val.isCrossLine + "'" + ",OpenBankCode='" + val.OpenBankCode + "'"
					+ " where bankid = '" + val.bankID + "'  " + "and firmid = '" + val.firmID + "'";
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

	public int modCorrespond(CorrespondValue val, CorrespondValue valNew, Connection conn) throws SQLException {
		Tool.log("===>>>修改帐号对应关系   modCorrespond conn  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "update f_b_firmidandaccount set firmID='" + valNew.firmID + "', status=" + val.status + ",accountname = '" + val.accountName + "'"
					+ ",bankname = '" + val.bankName + "',bankprovince = '" + val.bankProvince + "',bankcity = '" + val.bankCity + "'," + "mobile = '"
					+ val.mobile + "',email = '" + val.email + "',isopen=" + val.isOpen + ",cardtype = '" + val.cardType + "'" + ",card='" + val.card
					+ "',account1='" + val.account1 + "',account='" + val.account + "' ,accountName1 = '" + val.accountName1 + "'" + ",inMarketCode='"
					+ val.inMarketCode + "'" + ",isCrossLine='" + val.isCrossLine + "'" + ",OpenBankCode='" + val.OpenBankCode + "'"
					+ " where bankid = '" + val.bankID + "'  " + "and firmid = '" + val.firmID + "'";
			state = conn.prepareStatement(sql);

			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public int modCorrespond(CorrespondValue val) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>修改帐号对应关系   modCorrespond  " + new java.util.Date());
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
		Tool.log("注销账号对应关系   destroyAccount  时间：" + Tool.fmtTime(new java.util.Date()));
		Tool.log("参数：\nval:" + val.toString() + "\nconn[" + (conn == null ? "为空" : "不为空") + "]");
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
			closeStatement(null, state, null);
		}
		return result;
	}

	public int destroyAccount(CorrespondValue val) throws SQLException, ClassNotFoundException {
		Tool.log("注销账号对应关系   destroyAccount  时间：" + Tool.fmtTime(new java.util.Date()));
		Tool.log("参数：\nval:" + val.toString() + "\n");
		Connection conn = null;
		int result = 0;
		try {
			conn = getConnection();
			result = destroyAccount(val, conn);
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeStatement(null, null, conn);
		}
		return result;
	}

	public String FirmAccountFile(String bankId, String firmId, String cardType, String isOpen, String status) {
		String file = " where 1=1 ";
		if ((bankId != null) && (bankId.trim().length() > 0)) {
			file = file + "and bankId='" + bankId.trim() + "' ";
		}
		if ((firmId != null) && (firmId.trim().length() > 0)) {
			file = file + "and firmId='" + firmId.trim() + "' ";
		}
		if ((cardType != null) && (cardType.trim().length() > 0)) {
			if (cardType.trim().equals("1")) {
				file = file + "and cardType='1' ";
			} else {
				file = file + "and (cardType='8' or cardType='9') ";
			}
		}
		if ((isOpen != null) && (isOpen.trim().length() > 0)) {
			if (isOpen.trim().equals("1")) {
				file = file + "and isOpen=1 ";
			} else if (isOpen.trim().equals("2")) {
				file = file + "and isOpen=2 ";
			} else {
				file = file + "and isOpen=0 ";
			}
		}
		if ((status != null) && (status.trim().length() > 0)) {
			if (status.trim().equals("0")) {
				file = file + "and status=0 ";
			} else {
				file = file + "and status=1 ";
			}
		}
		file = file + "order by bankId,firmId ";
		return file;
	}

	public int countFirmAccount(String bankId, String firmId, String cardType, String isOpen, String status) {
		return countFirmAccount(FirmAccountFile(bankId, firmId, cardType, isOpen, status));
	}

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
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public int addCorrespond(CorrespondValue val) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>添加帐号对应关系   addCorrespond  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			conn = getConnection();

			sql = "insert into F_B_firmidandaccount(bankid, firmid, account, status,accountname, bankname, bankprovince, bankcity, mobile, email,account1,isopen,cardtype,card,accountName1,inMarketCode,isCrossLine,OpenBankCode,contact) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
			state.setString(17, val.isCrossLine);
			state.setString(18, val.OpenBankCode);
			state.setString(19, val.contact);
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

	public int openCorrespond(CorrespondValue val) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			conn = getConnection();
			sql = "update f_b_firmidandaccount set status=" + val.status + ",accountname = '" + val.accountName + "'" + ",bankname = '" + val.bankName
					+ "',bankprovince = '" + val.bankProvince + "',bankcity = '" + val.bankCity + "'" + ",isopen=" + val.isOpen + ",cardtype = '"
					+ val.cardType + "',openTime=sysdate" + ",card='" + val.card + "',account1='" + val.account1 + "',account='" + val.account
					+ "' ,accountName1 = '" + val.accountName1 + "'" + ",inMarketCode='" + val.inMarketCode + "'" + " where bankid = '" + val.bankID
					+ "'  " + "and firmid = '" + val.firmID + "' and contact='" + val.contact + "'";
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

	public int delCorrespond(CorrespondValue val) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>删除帐号对应关系   delCorrespond  " + new java.util.Date());
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

	public int closeCorrespond(String bankID, String firmID, String card, Connection conn) throws SQLException {
		Tool.log(
				"交易商解约  modBankFrozenFuns bankID[" + bankID + "]firmID[" + firmID + "]card[" + card + "]conn[" + (conn == null ? "为空" : "不为空") + "]");
		PreparedStatement state = null;
		int result = 0;
		String sql = null;
		String filter = " where bankID='" + bankID + "' and firmID='" + firmID + "' ";
		String filter1 = " where bankID='D_" + bankID + "' and firmID='D_" + firmID + "' ";
		if ((card != null) && (card.trim().length() > 0)) {
			filter = filter + " and card='" + card + "'";
		}
		try {
			sql = "delete from f_b_firmidandaccount " + filter1;
			Tool.log("sql:" + sql);
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
			sql = "update f_b_firmidandaccount set firmID='D_'||firmID,bankID='D_'||bankID,deltime=sysdate " + filter;
			Tool.log("sql:" + sql);
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public Vector<CorrespondValue> getCorrespondList(String filter, Connection conn) throws SQLException {
		Tool.log("===>>>查询帐号对应关系列表   getCorrespondList  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CorrespondValue value = null;
		Vector<CorrespondValue> list = new Vector();
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
				value.contact = rs.getString("contact");
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
				value.isCrossLine = rs.getString("isCrossLine");
				value.OpenBankCode = rs.getString("OpenBankCode");
				value.accountType = rs.getString("accountType");
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

	public Vector<CorrespondValue> getCorrespondList(String bankId, String firmId, String cardType, String isOpen, String status) {
		try {
			return getCorrespondList(FirmAccountFile(bankId, firmId, cardType, isOpen, status));
		} catch (SQLException e) {
			System.out.println("查询交易商和银行的绑定关系，数据库异常" + e);
		} catch (ClassNotFoundException e) {
			System.out.println("查询交易商和银行的绑定关系，找不到类异常" + e);
		}
		return new Vector();
	}

	public Vector<CorrespondValue> getCorrespondList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询帐号对应关系列表   getCorrespondList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CorrespondValue value = null;
		Vector<CorrespondValue> list = new Vector();
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
				value.contact = rs.getString("contact");
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
				value.isCrossLine = rs.getString("isCrossLine");
				value.OpenBankCode = rs.getString("OpenBankCode");
				value.accountType = rs.getString("accountType");
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
				value.isCrossLine = rs.getString("isCrossLine");
				value.OpenBankCode = rs.getString("OpenBankCode");
				value.accountType = rs.getString("accountType");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return value;
	}

	public CorrespondValue getCorrespond(String bankID, String firmID, String account) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询帐号对应关系   getCorrespond  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CorrespondValue value = null;
		try {
			conn = getConnection();

			sql = "select * from F_B_firmidandaccount where bankID='" + bankID + "' and firmID='" + firmID + "' and account='" + account + "'";

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
				value.isCrossLine = rs.getString("isCrossLine");
				value.OpenBankCode = rs.getString("OpenBankCode");
				value.accountType = rs.getString("accountType");
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

	public List<String> getFirmBankList(String firmID) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询交易商对应的银行帐号   getFirmBankList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		List<String> bankidList = new ArrayList();
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

	public Vector<DicValue> getDicList(String filter, Connection conn) throws SQLException {
		Tool.log("===>>>查询表   getDicList  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		DicValue value = null;
		Vector<DicValue> list = new Vector();
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

	public Vector<DicValue> getDicList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询表   getDicList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		DicValue value = null;
		Vector<DicValue> list = new Vector();
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

	public Vector<TransMnyObjValue> getTransMnyObjList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询资金划转对象列表   getTransMnyObjList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		TransMnyObjValue value = null;
		Vector<TransMnyObjValue> list = new Vector();
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

	public TransMnyObjValue getTransMnyObj(int id) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询资金划转对象   getTransMnyObj  " + new java.util.Date());
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
		Tool.log("===>>>----  qureyBankCompareInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		Vector<MoneyInfoValue> ve = new Vector();
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
		Tool.log("===>>>----  compareResultInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		Vector<CompareResult> ve = new Vector();
		String fundflow = " and exists (select f.contractno from f_h_fundflow f where f.oprcode in('103','104') and f.contractno = c.actionid and f.b_date = to_date('"
				+ date + "', 'yyyy-MM-dd')) ";
		try {
			conn = getConnection();
			if ("true".equals(Tool.getConfig("CompareWithFundflow"))) {
				sql =

				"select c.firmID || '/' || b.firmID as firmID, c.bankID as bankID, b.account as account, c.funID as id , c.actionID as m_Id ,  b.type as type, c.type as m_type, b.money as money ,c.money as m_money, b.comparedate as comparedate  from F_B_BANKCOMPAREINFO b,F_B_capitalInfo c  where c.status='0' and (c.type='0' or c.type='1') and c.bankid='"
						+ bankID + "' and b.bankid='" + bankID + "' " + " and trunc(b.COMPAREDATE)=to_date('" + date + "','yyyy-MM-dd') "
						+ " and b.funid=c.funid  and (b.money<>c.money or (trim(b.firmID)<>trim(c.firmID) and b.firmID!='GSYTfirm')) " + fundflow;
			} else {
				sql =

				"select c.firmID || '/' || b.firmID as firmID, c.bankID as bankID, b.account as account, c.funID as id , c.actionID as m_Id ,  b.type as type, c.type as m_type, b.money as money ,c.money as m_money, b.comparedate as comparedate  from F_B_BANKCOMPAREINFO b,F_B_capitalInfo c  where c.status='0' and (c.type='0' or c.type='1') and c.bankid='"
						+ bankID + "' and b.bankid='" + bankID + "' " + " and trunc(b.COMPAREDATE)=to_date('" + date
						+ "','yyyy-MM-dd') and c.createdate='" + date + "'"
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
			if ("true".equals(Tool.getConfig("CompareWithFundflow"))) {
				sql =

				"select c.firmID as firmID, c.bankID as bankID, b.account as account, c.funID as id, c.actionID as m_Id,   b.type as type ,c.type as m_type, b.money as money ,c.money as m_money ,b.comparedate as comparedate  from F_B_BANKCOMPAREINFO b,F_B_capitalInfo c  where (c.status='0' and (c.type='0' or c.type='1') and c.bankid='"
						+ bankID + "' and b.bankid='" + bankID + "' " + " and trunc(b.COMPAREDATE)=to_date('" + date + "','yyyy-MM-dd') "
						+ " and b.funid=c.funid  and b.type<>c.type " + fundflow + ")";
			} else {
				sql =

				"select c.firmID as firmID, c.bankID as bankID, b.account as account, c.funID as id, c.actionID as m_Id,   b.type as type ,c.type as m_type, b.money as money ,c.money as m_money ,b.comparedate as comparedate  from F_B_BANKCOMPAREINFO b,F_B_capitalInfo c  where (c.status='0' and (c.type='0' or c.type='1') and c.bankid='"
						+ bankID + "' and b.bankid='" + bankID + "' " + " and trunc(b.COMPAREDATE)=to_date('" + date
						+ "','yyyy-MM-dd') and c.createdate='" + date + "'" + " and b.funid=c.funid  and b.type<>c.type )";
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
			if ("true".equals(Tool.getConfig("CompareWithFundflow"))) {
				sql =

				"select * from F_B_BANKCOMPAREINFO b where trunc(b.comparedate)=to_date('" + date + "','yyyy-mm-dd') and b.bankID='" + bankID + "' "
						+ " and b.funid<>'-1' and not exists(select funid from F_B_capitalInfo c ,f_h_fundflow f where f.oprcode in('103','104') and  b.funid=c.funid  and  c.actionid =f.contractno  and f.b_date = to_date('"
						+ date + "', 'yyyy-MM-dd') and c.status='0' and (c.type='0' or c.type='1') " + " and c.bankID='" + bankID + "' "
						+ " and funid is not null ) ";
			} else {
				sql =

				"select * from F_B_BANKCOMPAREINFO b where trunc(b.comparedate)=to_date('" + date + "','yyyy-mm-dd') and b.bankID='" + bankID + "' "
						+ " and b.funid<>'-1' and not exists(select funid from F_B_capitalInfo c where b.funid=c.funid and c.status='0' and (c.type='0' or c.type='1') "
						+ " and c.bankID='" + bankID + "' and c.createdate='" + date + "' and funid is not null) ";
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
				value.m_Id = 0L;
				value.type = rs.getInt("TYPE");
				value.m_type = 0;
				value.money = rs.getDouble("MONEY");
				value.m_money = 0.0D;
				value.compareDate = rs.getDate("compareDate");
				ve.add(value);
			}
			if ("true".equals(Tool.getConfig("CompareWithFundflow"))) {
				sql =

				"select * from F_B_capitalInfo c where  c.bankID='" + bankID + "' "
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
				value.money = 0.0D;
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

	public Vector<CapitalCompare> sumResultInfo(String bankID, String[] firmIDs, java.util.Date date) throws SQLException, ClassNotFoundException {
		Tool.log("查询交易商当天出入金求和数据，bankID[" + bankID + "]firmIDs[" + firmIDs + "]date[" + date + "]");
		Vector<CapitalCompare> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = "";
		String filter2 = null;
		String filter3 = null;
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and bankID='" + bankID.trim() + "' ";
		}
		if ((firmIDs != null) && (firmIDs.length > 0)) {
			String firms = "";
			for (String firmID : firmIDs) {
				if ((firmID != null) && (firmID.trim().length() > 0)) {
					firms = firms + "'" + firmID.trim() + "',";
				}
			}
			if ((firms != null) && (firms.trim().length() > 0)) {
				filter = filter + " and firmID in (" + firms.substring(0, firms.lastIndexOf(',')) + ") ";
			}
		}
		filter2 = filter;
		filter3 = filter;
		if (date != null) {
			filter2 = filter2 + " and trunc(banktime)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd') ";
			filter3 = filter3 + " and trunc(comparedate)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd') ";
		}
		try {
			sql = "select nvl(a.inmoney,0) minmoney,nvl(a.outmoney,0) moutmoney,nvl(b.inmoney,0) binmoney,nvl(b.outmoney,0) boutmoney,c.firmid firmid,case when a.tradedate is null then b.tradedate else a.tradedate end tradedate,case when a.bankid is null then b.bankid else a.bankid end bankid from (select nvl(sum(case when type=0 then money else 0 end),0) inmoney,nvl(sum(case when type=1 then money else 0 end),0) outmoney,bankid,trim(firmid) firmid,trunc(banktime) tradedate from f_b_capitalinfo t where banktime is not null and status=0 "
					+ filter2 + " group by trunc(banktime),bankid,firmid order by trunc(banktime) desc,bankid,firmid) a, "
					+ "(select nvl(sum(case when type=0 then money else 0 end),0) inmoney,nvl(sum(case when type=1 then money else 0 end),0) outmoney,trunc(comparedate) tradedate,trim(firmid) firmid,bankid from f_b_bankcompareinfo t where status=0 "
					+ filter3 + " group by trunc(comparedate),bankid,firmid order by trunc(comparedate) desc,bankid,firmid) b, "
					+ "(select distinct firmid,tradedate from "
					+ "(select distinct trim(firmid) firmid,trunc(banktime) tradedate from f_b_capitalinfo where 1=1 and status=0 " + filter2
					+ "union  " + "select distinct trim(firmid) firmid,trunc(comparedate) tradedate from f_b_bankcompareinfo where 1=1 and status=0 "
					+ filter3 + ")where firmid is not null " + "order by firmid) c "
					+ "where c.firmid=a.firmid(+) and c.firmid=b.firmid(+) and c.tradedate=a.tradedate(+) and c.tradedate=b.tradedate(+) order by case when a.tradedate is null then b.tradedate else a.tradedate end desc,firmid";
			Tool.log("sql:" + sql);
			conn = getConnection();
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
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public CapitalValue getCapitalInfo(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得单个资金流水记录  getCapitalInfo  " + new java.util.Date());
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
		Tool.log("===>>>更新出金资金流水  updateOutMoneyCapitalInfo  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			int n = 1;
			String funidf = "";
			if ((val.funID != null) && (val.funID.trim().length() > 0)) {
				funidf = ",funid2=?";
			}
			sql = "update F_B_capitalInfo set status=?,FUNID=?,bankTime=?" + funidf + " where id=?";

			state = conn.prepareStatement(sql);
			state.setInt(n++, val.status);
			state.setString(n++, val.funID == null ? "" : val.funID.trim());
			state.setTimestamp(n++, val.bankTime);
			if ((funidf != null) && (funidf.trim().length() > 0)) {
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

	public int addFeeInfo(FeeInfoVO feeInfoVO, Connection conn) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>添加收费标准  addFeeInfo  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		int result = 0;
		long id = -1L;
		try {
			sql = "select seq_F_B_feeInfo.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			sql = "insert into  F_B_feeInfo(id, upLimit, downLimit, tMode, rate, maxRateValue,minRateValue,type, createTime,userID) values(?, ?, ?, ?, ?, ?,?,?,sysdate, ?)";

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

	public int addFeeInfo(FeeInfoVO feeInfoVO) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>添加收费标准  addFeeInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		int result = 0;
		long id = -1L;
		try {
			conn = getConnection();
			sql = "select seq_F_B_feeInfo.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			sql = "insert into  F_B_feeInfo(id, upLimit, downLimit, tMode, rate, maxRateValue,minRateValue,type, createTime,userID) values(?, ?, ?, ?, ?, ?,?,?,sysdate, ?)";

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

	public int modFeeInfo(FeeInfoVO feeInfoVO, Connection conn) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>修改收费标准  modFeeInfo  " + new java.util.Date());
		PreparedStatement state = null;

		int result = 0;
		try {
			String sql = "update  F_B_feeInfo set upLimit=?, downLimit=?, tMode=?, rate=?, maxRateValue=?,minRateValue=?,type=?, uptateTime=sysdate,userID=?) where id = ?";

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

	public int modFeeInfo(FeeInfoVO feeInfoVO) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>修改收费标准  modFeeInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;

		int result = 0;
		try {
			conn = getConnection();
			String sql = "update  F_B_feeInfo set upLimit=?, downLimit=?, tMode=?, rate=?, maxRateValue=?,minRateValue=?,type=?, uptateTime=sysdate,userID=?) where id = ?";

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

	public int delFeeInfo(FeeInfoVO feeInfoVO, Connection conn) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>删除收费标准  delFeeInfo  " + new java.util.Date());
		PreparedStatement state = null;

		int result = 0;
		try {
			String sql = "delete from F_B_feeInfo where id=" + feeInfoVO.id;
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

	public int delFeeInfo(FeeInfoVO feeInfoVO) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>删除收费标准  delFeeInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;

		int result = 0;
		try {
			conn = getConnection();
			String sql = "delete from F_B_feeInfo where id=" + feeInfoVO.id;
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

	public int delFeeInfo(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>删除收费标准  delFeeInfo  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;

		int result = 0;
		try {
			conn = getConnection();
			String sql = "delete from F_B_feeInfo " + filter;
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

	public Vector<FeeInfoVO> getFeeInfoList(String filter, Connection conn) throws SQLException {
		Tool.log("===>>>查询收费标准列表  getFeeInfoList  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FeeInfoVO value = null;
		Vector<FeeInfoVO> list = new Vector();
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

	public Vector<FeeInfoVO> getFeeInfoList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询收费标准列表  getFeeInfoList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FeeInfoVO value = null;
		Vector<FeeInfoVO> list = new Vector();
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

	public int countCapitalInfo(String filter, int status) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询资金流水总笔数  countCapitalInfo  " + new java.util.Date());
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

	public double countCapitalInfoTotalMoney(String filter, int status) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询资金流水总金额  countCapitalInfoTotalMoney  " + new java.util.Date());
		double totalMoney = 0.0D;

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

	public int countBank(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询银行总笔数  countBank  " + new java.util.Date());
		int countNum = 0;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();

			sql = " select count(*) from pxdzf t  where 1=1 " + filter;
			state = conn.prepareStatement(sql);

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

	public int countBankCompareInfo(String filter, int status) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询银行对账信息总笔数  countBankCompareInfo  " + new java.util.Date());
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

	public double countBankCompareInfoTotalMoney(String filter, int status) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询银行对账信息总金额  countBankCompareInfoTotalMoney  " + new java.util.Date());
		double totalMoney = 0.0D;

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

	public CapitalValue handleOutmoenyFromBank(long id) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询资金流水总金额  handleOutmoenyFromBank  " + new java.util.Date());
		CapitalValue cv = null;

		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = " select t.firmid, t.funid, t.bankid, t.debitid, t.creditid, t.type, t.money, t.operator, t.createtime, t.banktime, t.status, t.note, t.actionid, t.express,t.bankName,t.account from f_b_capitalinfo t where t.id = ? ";
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

	public boolean getTraderStatus() throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取交易系统结算状态  getTraderStatus  " + new java.util.Date());
		System.out.println("getTraderStatus");
		boolean traderStatus = false;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = " select to_char(max(B_Date),'yyyy-MM-dd') maxDate from f_h_firmbankfunds ";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				String settlementDate = rs.getString(1);
				String now = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
				if ((settlementDate != null) && (settlementDate.indexOf(now) == 0)) {
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

	public List<TradeResultValue> getTradeDataInList(String filter, int moduleid) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取交易系统结算后的数据  getTradeDataInList  " + new java.util.Date());
		List<TradeResultValue> resultList = new ArrayList();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			if (moduleid == 2) {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account  from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and to_char(f.oprcode) like '2%' and "
						+ filter + " order by f.fundflowid ";
			} else if (moduleid == 3) {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account  from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and to_char(f.oprcode) like '3%' and "
						+ filter + " order by f.fundflowid ";
			} else if (moduleid == 4) {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account  from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and to_char(f.oprcode) like '4%' and "
						+ filter + " order by f.fundflowid ";
			} else {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account  from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and "
						+ filter + " order by f.fundflowid ";
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

	public Hashtable<String, TradeResultValue> getTradeDataInHashTable(String filter, int moduleid) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取交易系统结算后的数据  getTradeDataInHashTable  " + new java.util.Date());
		Hashtable<String, TradeResultValue> resultList = new Hashtable();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			if (moduleid == 2) {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account  from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and to_char(f.oprcode) like '2%' and "
						+ filter + " order by f.fundflowid ";
			} else if (moduleid == 3) {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account  from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and to_char(f.oprcode) like '3%' and "
						+ filter + " order by f.fundflowid ";
			} else if (moduleid == 4) {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account  from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and to_char(f.oprcode) like '4%' and "
						+ filter + " order by f.fundflowid ";
			} else {
				sql = " select f.fundflowid,f.firmid,f.oprcode,f.amount,f.balance,f.appendamount,f.b_date,b.bankid,b.account  from f_h_fundflow f, f_b_firmidandaccount b where f.firmid = b.firmid(+) and "
						+ filter + " order by f.fundflowid ";
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

	public Hashtable<String, Double> getFundsAndInterests(java.util.Date date, int moduleid) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>市场总额[可用资金和所有权益]  getFundsAndInterests  " + new java.util.Date());
		Hashtable<String, Double> ht = new Hashtable();
		double fundsAndInterests = 0.0D;
		String firmid = "";

		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = " select a.firmid,a.todaybalance,b.RuntimeMargin,b.RuntimeFL,b.RuntimeSettleMargin,nvl(c.floatingloss,0) floatingloss from  (select firmid, todaybalance from f_firmbalance where b_date=to_date('"
					+ new java.sql.Date(date.getTime()) + "','yyyy-MM-dd')) a,"
					+ " (select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where cleardate=to_date('"
					+ date + "','yyyy-MM-dd')) b,"
					+ " (select firmid,nvl(sum(floatingloss),0) floatingloss from t_h_firmholdsum where cleardate=to_date('" + date
					+ "','yyyy-MM-dd') group by firmid) c" + " where a.firmid=b.firmid(+) and a.firmid=c.firmid(+) order by a.firmid ";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				firmid = rs.getString(1);
				fundsAndInterests = rs.getDouble(2) + rs.getDouble(3) + rs.getDouble(4) + rs.getDouble(5) + rs.getDouble(6);
				ht.put(firmid, Double.valueOf(fundsAndInterests));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return ht;
	}

	public Vector<FundsAndInterests> getFundsAndInterestsInVector(java.util.Date date, int moduleid) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>市场总额[可用资金和所有权益]  getFundsAndInterestsInVector  " + new java.util.Date());
		Vector<FundsAndInterests> v = new Vector();
		double fundsAndInterests = 0.0D;

		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = " select a.firmid,a.todaybalance,b.RuntimeMargin,b.RuntimeFL,b.RuntimeSettleMargin,nvl(c.floatingloss,0) floatingloss from  (select firmid, todaybalance from f_firmbalance where b_date=to_date('"
					+ new java.sql.Date(date.getTime()) + "','yyyy-MM-dd')) a,"
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
			sql = " insert into f_b_log (logid,logopr,logcontent,logdate,logIp) values (SEQ_F_B_LOG.NEXTVAL,?,?,sysdate,?) ";
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
		Vector<LogValue> v = new Vector();

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

	public FirmBalanceValue availableBalance(String filter) {
		Tool.log("===>>>查询交易商市场可用资金  availableBalance  " + new java.util.Date());

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

	public FirmBalanceValue availableBalance(String filter, Connection conn) throws SQLException {
		Tool.log("===>>>查询交易商市场可用资金  availableBalance  " + new java.util.Date());
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

	public List<FrozenBalanceVO> frozenBalance(String firmid, String moduleid) {
		List<FrozenBalanceVO> list = new ArrayList();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = " where 1=1 ";
		if ((!"".equals(firmid)) && (firmid != null)) {
			filter = filter + " and firmid='" + firmid + "'";
		}
		if (("1".equals(moduleid)) || ("2".equals(moduleid)) || ("3".equals(moduleid)) || ("4".equals(moduleid))) {
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

	public int bankAccountIsOpen(String filter) {
		Tool.log("===>>>查询银行帐号的签约状态  bankAccountIsOpen  " + new java.util.Date());
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

	public FirmMessageVo getFirmMSG(String firmid) {
		Tool.log("===>>>查询交易商名下交易员的密码  getFirmPwd  " + new java.util.Date());
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

	public Vector<FirmFundsValue> getFrimFunds(String firmID, java.util.Date tradeDate) throws SQLException, ClassNotFoundException {
		Tool.log("查询交易系统中交易商某天资金情况");
		Vector<FirmFundsValue> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = "";
		if ((firmID != null) && (firmID.trim().length() > 0)) {
			filter = filter + " and firmid='" + firmID.trim() + "'";
		}
		try {
			conn = getConnection();
			java.util.Date yesDate = getlastDate(tradeDate, conn);
			sql = "select d.*,a.balance todayFunds,a.frozenfunds frozenfunds,a.lastbalance yesFunds,b.zvmargin zvMargin,c.rsm settleMargin,c.rma runtimeMargin,c.fl,c.cleardate from (select firmid,balance,frozenfunds,lastbalance from f_firmfunds where 1=1 "
					+ filter + ") a, "
					+ "(select firmID,sum(case when code in ('Margin_V','Margin_Z') then value else -value end) zvmargin from f_clientledger where code in ('MarginBack_V','Margin_V','MarginBack_Z','Margin_Z') and trunc(b_date)<to_date('"
					+ Tool.fmtDate(yesDate) + "','yyyy-MM-dd') " + filter + " group by firmid) b, "
					+ "(select firmID,RuntimeSettleMargin rsm,(RuntimeMargin-RuntimeAssure) rma,RuntimeFL fl,cleardate from T_h_firm where 1=1 and trunc(cleardate)=to_date('"
					+ Tool.fmtDate(yesDate) + "','yyyy-MM-dd') " + filter + ") c, " + "(select * from f_b_firmidandaccount where 1=1 " + filter
					+ ") d  " + "where d.firmid=a.firmid(+) and d.firmid=b.firmid(+) and d.firmid=c.firmid(+)";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmFundsValue value = new FirmFundsValue();
				value.firmID = rs.getString("firmID");
				value.yesFunds = rs.getDouble("yesFunds");
				value.todayFunds = rs.getDouble("todayFunds");
				value.margin = 0.0D;
				value.zvMargin = rs.getDouble("zvMargin");
				value.runtimeMargin = rs.getDouble("runtimeMargin");
				value.settleMargin = rs.getDouble("settleMargin");
				value.inMoney = 0.0D;
				value.outMoney = 0.0D;
				value.tradeDate = tradeDate;
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

	public int addBankQS(BankQSVO bq, Connection conn) throws SQLException {
		Tool.log("添加清算日期表");
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
			closeStatement(null, state, null);
		}
		return result;
	}

	public java.util.Date getMaxBankQSList(String bankID, java.util.Date date, Connection conn) throws SQLException {
		Tool.log("查询清算日期表");
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
			closeStatement(rs, state, null);
		}
		return result;
	}

	public int addFirmKXH(KXHfailChild child, String bankID) throws SQLException, ClassNotFoundException {
		Tool.log("添加会员的签解约信息  addFirmKXH  " + Tool.fmtTime(new java.util.Date()));
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "insert into F_B_FIRMKXH (FUNID,STATUS,ACCOUNT1,TYPE,ACCOUNT1NAME,FIRMID,TRADEDATE,COUNTERID,BANKID,CREATEDATE) values (?,?,?,?,?,?,?,?,?,sysdate)";
			conn = getConnection();
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
			closeStatement(null, state, conn);
		}
		return result;
	}

	public KXHfailChild getFirmKXH(String funID) throws SQLException, ClassNotFoundException {
		Tool.log("根据银行流水号查询签解约信息  getFirmKXH  " + Tool.fmtTime(new java.util.Date()));
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		KXHfailChild result = null;
		try {
			sql = "select * from F_B_FIRMKXH where funID='" + funID + "'";
			System.out.println("sql:" + sql);
			Tool.log("sql:" + sql);
			conn = getConnection();
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
			closeStatement(null, state, conn);
		}
		return result;
	}

	public int addBatCustDz(BatCustDzFailChild child, java.util.Date date, String bankID) throws SQLException, ClassNotFoundException {
		Tool.log("添加银行对账不平记录  addBatCustDz  时间：" + Tool.fmtTime(new java.util.Date()));
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_BATCUSTFILE (CUSTACCTID,CUSTNAME,THIRDCUSTID,BANKBALANCE,BANKFROZEN,MAKETBALANCE,MAKETFROZEN,BALANCEERROR,FROZENERROR,NOTE,TRADEDATE,BANKID,CREATEDATE) values (?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
			conn = getConnection();
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
			closeStatement(null, state, conn);
		}
		return result;
	}

	public Vector<BatCustDzFailChild> getBatCustDz(String[] firmIDs, String bankID, java.util.Date date) throws SQLException, ClassNotFoundException {
		Tool.log("查询银行对账不平文件  getBatCustDz  时间：" + Tool.fmtTime(new java.util.Date()));
		Vector<BatCustDzFailChild> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = " where 1=1 ";
		if ((firmIDs != null) && (firmIDs.length > 0)) {
			String str = " and ThirdCustId in ('aa'";
			for (int i = 0; i < firmIDs.length; i++) {
				str = str + ",'" + firmIDs[i] + "'";
			}
			filter = filter + str + ") ";
		}
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and BANKID='" + bankID.trim() + "'";
		}
		if (date != null) {
			filter = filter + " and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
		}
		try {
			sql = "select * from F_B_BATCUSTFILE " + filter;
			System.out.println("sql:" + sql);
			conn = getConnection();
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
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public Vector<BatCustDzFailChild> getBatCustDz(java.util.Date date, String bankID) throws SQLException, ClassNotFoundException {
		Tool.log("查询银行对账不平文件信息  getBatCustDz  时间：" + Tool.fmtTime(new java.util.Date()));
		Vector<BatCustDzFailChild> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = " where 1=1 ";
		filter = filter + " and bankid='" + bankID + "' and trunc(tradedate)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
		try {
			sql = "select * from F_B_BATCUSTFILE " + filter;
			System.out.println("sql:" + sql);
			conn = getConnection();
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
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public int delBatCustDz(java.util.Date date, String bankID) throws SQLException, ClassNotFoundException {
		Tool.log("删除对账不平记录  delBatCustDz  时间：" + Tool.fmtTime(new java.util.Date()));
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		String filter = " where 1=1 ";
		if (date != null) {
			filter = filter + " and trunc(tradedate)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
		}
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and bankid='" + bankID + "'";
		}
		try {
			sql = "delete F_B_BATCUSTFILE " + filter;
			Tool.log("sql:" + sql);
			conn = getConnection();
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	public int addFirmBalanceFile(BatCustDzBChild child, java.util.Date date, String bankID) throws SQLException, ClassNotFoundException {
		Tool.log("添加交易商余额文件  addFirmBalanceFile  时间：" + Tool.fmtTime(new java.util.Date()));
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_FIRMBALANCE (CUSTACCTID,CUSTNAME,THIRDCUSTID,STATUS,TYPE,ISTRUECONT,BALANCE,USRBALANCE,FROZENBALANCE,INTEREST,BANKID,TRADEDATE,CREATEDATE) values (?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
			conn = getConnection();
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
			closeStatement(null, state, conn);
		}
		return result;
	}

	public int modFirmBalanceFile(BatCustDzBChild child, java.util.Date date, String bankID) throws SQLException, ClassNotFoundException {
		Tool.log("修改交易商子账号信息  modFirmBalanceFile  时间：" + Tool.fmtTime(new java.util.Date()));
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "update F_B_FIRMBALANCE set CUSTACCTID=?,CUSTNAME=?,STATUS=?,TYPE=?,ISTRUECONT=?,BALANCE=?,USRBALANCE=?,FROZENBALANCE=?,INTEREST=? where BANKID=? and THIRDCUSTID=? and trunc(TRADEDATE)=to_date('"
					+ Tool.fmtDate(date) + "','yyyy-MM-dd')";
			conn = getConnection();
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
			closeStatement(null, state, conn);
		}
		return result;
	}

	public Vector<FirmOpenCloseBank> getFirmBank(String bankID, java.util.Date date) throws SQLException, ClassNotFoundException {
		Tool.log("查询会员签解约不平文件  getFirmBank");
		Vector<FirmOpenCloseBank> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select FIRMID from f_b_firmkxh a where bankid='" + bankID + "' and status=1 and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date)
					+ "','yyyy-MM-dd') and not exists(select FIRMID from f_b_firmidandaccount b where bankid='" + bankID
					+ "' and trunc(opentime)=to_date('" + Tool.fmtDate(date)
					+ "','yyyy-MM-dd') and b.firmid=a.firmid and b.bankid=a.bankid or b.firmid='D_'||a.firmid and b.bankid='D_'||a.bankid)";
			Tool.log("sql:" + sql);
			System.out.println("sql:" + sql);
			conn = getConnection();
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
			closeStatement(rs, state, conn);
		}
		try {
			sql = "select FIRMID from f_b_firmkxh a where bankid='" + bankID + "' and status=2 and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date)
					+ "','yyyy-MM-dd') and not exists(select FIRMID from f_b_firmidandaccount b where (bankid='" + bankID + "' or bankid='D_" + bankID
					+ "') and trunc(deltime)=to_date('" + Tool.fmtDate(date)
					+ "','yyyy-MM-dd') and b.firmid='D_'||a.firmid and b.bankid='D_'||a.bankid)";
			Tool.log("sql:" + sql);
			System.out.println("sql:" + sql);
			conn = getConnection();
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
			closeStatement(rs, state, conn);
		}
		try {
			sql = "select firmid from f_b_firmidandaccount a where (bankid='" + bankID + "' or bankid='D_" + bankID
					+ "') and trunc(opentime)=to_date('" + Tool.fmtDate(date)
					+ "', 'yyyy-MM-dd') and not exists (select firmid from f_b_firmkxh b where bankid='" + bankID
					+ "' and status=1 and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date)
					+ "', 'yyyy-MM-dd') and (b.firmid=a.firmid or 'D_'||b.firmid=a.firmid))";
			Tool.log("sql:" + sql);
			System.out.println("sql:" + sql);
			conn = getConnection();
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
			closeStatement(rs, state, conn);
		}
		try {
			sql = "select firmid from f_b_firmidandaccount a where bankid='D_" + bankID + "' and trunc(deltime)=to_date('" + Tool.fmtDate(date)
					+ "', 'yyyy-MM-dd') and not exists (select firmid from f_b_firmkxh b where bankid='" + bankID
					+ "' and status=2 and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date) + "', 'yyyy-MM-dd') and 'D_'||b.firmid=a.firmid)";
			Tool.log("sql:" + sql);
			System.out.println("sql:" + sql);
			conn = getConnection();
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
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public Vector<BatCustDzBChild> getFirmBalanceFile(String ThirdCustId, String bankID, java.util.Date date)
			throws SQLException, ClassNotFoundException {
		Tool.log("查询交易商银行余额信息  getFirmBalanceFile  时间：" + Tool.fmtTime(new java.util.Date()));
		Vector<BatCustDzBChild> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = " where 1=1 ";
		if ((ThirdCustId != null) && (ThirdCustId.trim().length() > 0)) {
			filter = filter + " and THIRDCUSTID='" + ThirdCustId.trim() + "'";
		}
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and BANKID='" + bankID.trim() + "'";
		}
		if (date != null) {
			filter = filter + " and trunc(TRADEDATE)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
		}
		try {
			sql = "select * from F_B_FIRMBALANCE " + filter;
			System.out.println("sql:" + sql);
			Tool.log("sql:" + sql);
			conn = getConnection();
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
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public int addFirmBalanceError(BatFailResultChild fbe, String bankID) throws SQLException, ClassNotFoundException {
		Tool.log("添加银行对账失败文件  addFirmBalanceError  时间：" + Tool.fmtTime(new java.util.Date()));
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_FIRMBALANCEERROR (TRANDATETIME,COUNTERID,SUPACCTID,FUNCCODE,CUSTACCTID,CUSTNAME,THIRDCUSTID,THIRDLOGNO,CCYCODE,FREEZEAMOUNT,UNFREEZEAMOUNT,ADDTRANAMOUNT,CUTTRANAMOUNT,PROFITAMOUNT,LOSSAMOUNT,TRANHANDFEE,TRANCOUNT,NEWBALANCE,NEWFREEZEAMOUNT,NOTE,RESERVE,RSPCODE,RSPMSG,BANKID,CREATETIME) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
			conn = getConnection();
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
			closeStatement(null, state, conn);
		}
		return result;
	}

	public Vector<BatFailResultChild> getFirmBalanceError(String[] firmIDs, String bankID, java.util.Date date)
			throws SQLException, ClassNotFoundException {
		Tool.log("查询银行对账失败信息  getFirmBalanceError  时间：" + Tool.fmtDate(new java.util.Date()));
		Vector<BatFailResultChild> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = " where 1=1 ";
		if ((firmIDs != null) && (firmIDs.length > 0)) {
			String str = "('aa'";
			for (int i = 0; i < firmIDs.length; i++) {
				str = str + ",'" + firmIDs[i] + "'";
			}
			str = str + ") ";
			filter = filter + " and ThirdCustId in " + str;
		}
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and bankid='" + bankID.trim() + "' ";
		}
		if (date != null) {
			filter = filter + " and trunc(TranDateTime)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd') ";
		}
		try {
			sql = "select * from f_b_firmbalanceerror " + filter;
			System.out.println("sql:" + sql);
			Tool.log("sql:" + sql);
			conn = getConnection();
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
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public Map<String, BatQsChild> getQSChild(String bankID, Set<String> firmIDs, Set<String> notFirmIDs, java.util.Date qdate, Connection conn)
			throws SQLException {
		Tool.log("获取某日的清算信息  getQSChild 时间：" + Tool.fmtDate(new java.util.Date()));
		Map<String, BatQsChild> result = new HashMap();
		java.sql.Date lastDay = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			String sql0 = "select max(B_DATE) lastDate from f_h_firmbankfunds t where t.b_date < to_date(?,'yyyy-MM-dd')";
			state = conn.prepareStatement(sql0);
			state.setString(1, Tool.fmtDate(qdate));
			rs = state.executeQuery();
			while (rs.next()) {
				lastDay = rs.getDate("lastDate");
			}
			if (lastDay == null) {
				lastDay = new java.sql.Date(0, 0, 1);
			}
			StringBuffer sql = new StringBuffer("");
			sql.append("select f.firmid, ");
			sql.append("f.contact, ");
			sql.append("f.bankid, ");
			sql.append("f.account, ");
			sql.append("f.account1, ");
			sql.append("f.accountname, ");
			sql.append("f.accountname1, ");
			sql.append("f.cardtype, ");
			sql.append("f.card, ");
			sql.append("nvl(today.firmfee, 0) Fee, ");
			sql.append("nvl(today.rights, 0) rights, ");
			sql.append("nvl(today.balance, 0) balance, ");
			sql.append("nvl(today.rightsfrozenfunds, 0) frozen,");
			sql.append("nvl(yestoday.rights, 0) lastRights, ");
			sql.append("nvl(yestoday.balance, 0) lastBalance, ");
			sql.append("nvl(yestoday.rightsfrozenfunds, 0) lastFrozen,");
			sql.append("nvl(today.outInMoney,0) crjsum ");
			sql.append("from ");
			sql.append("(select * from f_h_firmbankfunds where b_date=to_date(?,'yyyy-MM-dd')) today, ");
			sql.append("(select * from f_h_firmbankfunds where b_date=to_date(?,'yyyy-MM-dd')) yestoday, ");
			sql.append("(select * from f_b_firmidandaccount where isopen=1) f  ");
			sql.append("where f.firmid=today.firmid(+) ");
			sql.append(" and f.firmid=yestoday.firmid(+) ");
			sql.append(" and f.bankid=today.bankcode(+) ");
			sql.append(" and f.bankid=yestoday.bankcode(+) ");
			sql.append(" and f.bankid=? ");
			System.out.println("平安清算SQL：" + sql.toString());
			state = conn.prepareStatement(sql.toString());
			state.setString(1, Tool.fmtDate(qdate));
			state.setString(2, Tool.fmtDate(lastDay));
			state.setString(3, bankID);
			rs = state.executeQuery();
			while (rs.next()) {
				BatQsChild value = new BatQsChild();
				value.AddTranAmount = 0.0D;
				value.CutTranAmount = 0.0D;
				if (rs.getDouble("rights") - rs.getDouble("lastRights") - rs.getDouble("crjsum") + rs.getDouble("Fee") >= 0.0D) {
					value.ProfitAmount = (rs.getDouble("rights") - rs.getDouble("lastRights") - rs.getDouble("crjsum") + rs.getDouble("Fee"));
					value.LossAmount = 0.0D;
				} else {
					value.LossAmount = (-1.0D * (rs.getDouble("rights") - rs.getDouble("lastRights") - rs.getDouble("crjsum") + rs.getDouble("Fee")));
					value.ProfitAmount = 0.0D;
				}
				value.TranHandFee = rs.getDouble("Fee");
				if (rs.getDouble("frozen") - rs.getDouble("lastFrozen") >= 0.0D) {
					value.FreezeAmount = (rs.getDouble("frozen") - rs.getDouble("lastFrozen"));
					value.UnfreezeAmount = 0.0D;
				} else {
					value.UnfreezeAmount = (rs.getDouble("lastFrozen") - rs.getDouble("frozen"));
					value.FreezeAmount = 0.0D;
				}
				value.NewBalance = rs.getDouble("balance");
				value.NewFreezeAmount = rs.getDouble("frozen");
				value.ThirdCustId = rs.getString("contact");
				value.CustAcctId = rs.getString("account1");
				value.CustName = rs.getString("accountName1");
				value.ThirdLogNo = getActionID() + "";
				value.TranDateTime = Tool.fmtDateTime(qdate);
				value.toDhykAmount = 0.0D;
				value.yeDhykAmount = 0.0D;
				value.toQianAmount = 0.0D;
				value.yeQianAmount = 0.0D;
				result.put(value.ThirdCustId, value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public java.util.Date getlastDate(java.util.Date date, Connection conn) throws SQLException {
		java.util.Date result = null;
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
			closeStatement(rs, state, null);
		}
		if (result == null) {
			result = Tool.strToDate("1900-01-01");
		}
		return result;
	}

	public Vector<KXHfailChild> getBankOpen(String bankID, String[] firmIDs, int status, java.util.Date tradeDate)
			throws SQLException, ClassNotFoundException {
		Tool.log("查询某一天的签约信息  getBankOpen   时间：" + Tool.fmtDate(new java.util.Date()));
		Vector<KXHfailChild> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = " where 1=1 ";
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and bankID='" + bankID.trim() + "' ";
		}
		if ((firmIDs != null) && (firmIDs.length > 0)) {
			String str = "('aa'";
			for (String firmID : firmIDs) {
				str = str + ",'" + firmID + "' ";
			}
			filter = filter + " and firmID in" + str + ") ";
		}
		if (status > 0) {
			filter = filter + " and status=" + status + " ";
		}
		if (tradeDate != null) {
			filter = filter + " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd') ";
		}
		try {
			sql = "select * from f_b_firmkxh " + filter;
			System.out.println("sql:" + sql);
			conn = getConnection();
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
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public int delFirmBalanceError(String bankID, String date) throws SQLException, ClassNotFoundException {
		Tool.log("删除相应的银行对账失败文件信息  delFirmBalanceError  时间：" + Tool.fmtDate(new java.util.Date()));
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		String filter = " where 1=1 ";
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and bankID='" + bankID.trim() + "'";
		}
		if ((date != null) && (date.trim().length() > 0)) {
			java.util.Date dd = Tool.getDateTime(date);
			filter = filter + " and trunc(TRANDATETIME)=to_date('" + Tool.fmtDate(dd) + "','yyyy-MM-dd')";
		}
		try {
			sql = "delete F_B_FIRMBALANCEERROR " + filter;
			System.out.println("sql:" + sql);
			Tool.log("sql:" + sql);
			conn = getConnection();
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	public Vector<FirmBalance> getFirmBalance(String bankID, String[] firmIDs, java.util.Date qdate) throws SQLException, Exception {
		Vector<FirmBalance> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql0 = "select max(B_DATE) lastDate from f_h_firmbankfunds t where t.b_date < to_date(?,'yyyy-MM-dd')";
		java.sql.Date lastDay = null;
		StringBuffer sql = new StringBuffer(" ");
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql0);
			state.setString(1, Tool.fmtDate(qdate));
			rs = state.executeQuery();
			while (rs.next()) {
				lastDay = rs.getDate("lastDate");
			}
			if (lastDay == null) {
				lastDay = new java.sql.Date(0, 0, 1);
			}
			sql.append("select f.firmid, ");
			sql.append("f.contact, ");
			sql.append("f.bankid, ");
			sql.append("f.account, ");
			sql.append("f.accountname, ");
			sql.append("f.cardtype, ");
			sql.append("f.card, ");
			sql.append("nvl(today.firmfee, 0) Fee, ");
			sql.append("nvl(today.rights, 0) rights, ");
			sql.append("nvl(today.balance, 0) balance, ");
			sql.append("nvl(yestoday.rights, 0) lastRights, ");
			sql.append("nvl(yestoday.balance, 0) lastBalance, ");
			sql.append("nvl(today.outInMoney,0) crjsum ");
			sql.append("from ");
			sql.append("(select * from f_h_firmbankfunds where b_date=to_date(?,'yyyy-MM-dd')) today, ");
			sql.append("(select * from f_h_firmbankfunds where b_date=to_date(?,'yyyy-MM-dd')) yestoday, ");
			sql.append("(select * from f_b_firmidandaccount where isopen=1) f  ");
			sql.append("where f.firmid=today.firmid(+) ");
			sql.append(" and f.firmid=yestoday.firmid(+) ");
			sql.append(" and f.bankid=today.bankcode(+) ");
			sql.append(" and f.bankid=yestoday.bankcode(+) ");
			sql.append(" and f.bankid=? ");
			System.out.println("建行清算SQL：" + sql.toString());
			state = conn.prepareStatement(sql.toString());
			state.setString(1, Tool.fmtDate(qdate));
			state.setString(2, Tool.fmtDate(lastDay));
			state.setString(3, bankID);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmBalance fb = new FirmBalance();
				fb.firmID = rs.getString("contact");
				fb.bankID = rs.getString("bankid");
				fb.account = rs.getString("account");
				fb.accountName = rs.getString("accountName");
				fb.cardType = rs.getString("cardType");
				fb.card = rs.getString("card");
				fb.FeeMoney = rs.getDouble("Fee");
				if ("qy".equals(Tool.getConfig("QSMode"))) {
					fb.QYChangeMoney = Arith.sub(Arith.add(Arith.sub(rs.getDouble("rights"), rs.getDouble("lastRights")), rs.getDouble("Fee")),
							rs.getDouble("crjsum"));
					fb.QYMoney = rs.getDouble("rights");
					fb.yesQYMoney = rs.getDouble("lastRights");
				} else {
					fb.QYChangeMoney = Arith.sub(Arith.add(Arith.sub(rs.getDouble("balance"), rs.getDouble("lastBalance")), rs.getDouble("Fee")),
							rs.getDouble("crjsum"));
					fb.QYMoney = rs.getDouble("balance");
					fb.yesQYMoney = rs.getDouble("lastBalance");
				}
				fb.date = qdate;
				fb.CRJSum = rs.getDouble("crjsum");
				result.add(fb);
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public long addQSResult(QSRresult qsResult, Connection conn) throws SQLException {
		long result = 0L;
		String sql = null;
		PreparedStatement state = null;
		try {
			sql = "insert into F_B_QSResult (resultID,bankID,bankNAme,firmID,firmName,account,account1,kyMoneyM,kyMoneyB,djMoneyM,djMoneyB,zckyMoney,zcdjMoney,moneyM,moneyB,zcMoney,createDate,tradeDate,status,note) values(SEQ_F_B_QSRESULT.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?)";
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
			closeStatement(null, state, null);
		}
		return result;
	}

	public long delQSResult(String bankID, java.util.Date tradeDate, Connection conn) throws SQLException {
		long result = 0L;
		String sql = null;
		PreparedStatement state = null;
		String filter = "";
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and bankID='" + bankID.trim() + "' ";
		}
		if (tradeDate != null) {
			filter = filter + " and to_char(tradeDate,'yyyy-MM-dd')='" + Tool.fmtDate(tradeDate) + "'";
		}
		try {
			sql = "delete F_B_QSResult where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public Vector<QSRresult> getQSList(String bankID, String[] firmIDs, int status, java.util.Date tradeDate)
			throws SQLException, ClassNotFoundException {
		Vector<QSRresult> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = "";
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and bankID='" + bankID.trim() + "'";
		}
		if ((firmIDs != null) && (firmIDs.length > 0)) {
			String firms = "";
			for (String firmID : firmIDs) {
				if ((firmID != null) && (firmID.trim().length() > 0)) {
					if (firms.trim().length() == 0) {
						firms = firms + firmID;
					} else {
						firms = firms + "," + firmID;
					}
				}
			}
			if (firms.trim().length() > 0) {
				firms = " (" + firms + ") ";
				filter = filter + " and firmID in " + firms;
			}
		}
		if (status >= 0) {
			filter = filter + " and status=" + status + " ";
		}
		if (tradeDate != null) {
			filter = filter + " and to_char(tradeDate,'yyyy-MM-dd')='" + Tool.fmtDate(tradeDate) + "' ";
		}
		try {
			conn = getConnection();
			sql = "select * from F_B_QSResult where 1=1 " + filter;
			Tool.log("sql: " + sql);
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
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public Vector<HXSentQSMsgValue> getHXQSMsg(String bankID, String[] firmIDs, java.util.Date tradeDate)
			throws SQLException, ClassNotFoundException {
		Vector<HXSentQSMsgValue> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String bankfilter = "";
		String bhfilter = "";
		String djfilter = "";
		String djfiltery = "";
		String marginfilter = "";
		conn = getConnection();
		java.util.Date yestoday = getMaxBankQSList(bankID, tradeDate, conn);
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			bankfilter = bankfilter + " and bankid='" + bankID.trim() + "'";
		}
		if ((firmIDs != null) && (firmIDs.length > 0)) {
			String firms = "'aa'";
			for (String firmID : firmIDs) {
				if ((firmID != null) && (firmID.trim().length() > 0)) {
					firms = firms + ",'" + firmID.trim() + "'";
				}
			}
			firms = "(" + firms + ")";
			bankfilter = bankfilter + " and firmid in " + firms;
			bhfilter = bhfilter + " and firmid in " + firms;
			djfilter = djfilter + " and firmid in " + firms;
			djfiltery = djfiltery + " and firmid in " + firms;
			marginfilter = marginfilter + " and firmid in " + firms;
		}
		if (tradeDate != null) {
			bhfilter = bhfilter + " and trunc(b_date)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
			djfilter = djfilter + " and trunc(cleardate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
			djfiltery = djfiltery + " and trunc(cleardate)=to_date('" + Tool.fmtDate(yestoday) + "','yyyy-MM-dd')";
			marginfilter = marginfilter + " and trunc(b_date)<=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
		}
		try {
			conn = getConnection();
			sql = "select bank.*,nvl(rsm.value,0) rsm,nvl(rm.value,0) rm,nvl(Margin.value,0)-nvl(MarginBack.value,0) Margin,nvl(rfl.value,0) rfl,nvl(fl.value,0) fl,(nvl(rsm.value,0)-nvl(rsmy.value,0)+nvl(rm.value,0)-nvl(rmy.value,0)+nvl(Marginch.value,0)-nvl(MarginBackch.value,0)+nvl(rfl.value,0)-nvl(rfly.value,0)+nvl(fl.value,0)-nvl(fly.value,0)+nvl(balance.todaybalance,0)-nvl(balance.lastbalance,0)-nvl(inmoney.value,0)+nvl(outmoney.value,0)) balanceChange,(nvl(rsm.value,0)+nvl(rm.value,0)+nvl(Margin.value,0)-nvl(MarginBack.value,0)+nvl(rfl.value,0)+nvl(fl.value,0)) frozen,nvl(balance.todaybalance,0) balance,nvl(balance.lastbalance,0) lastbalance,nvl(inmoney.value,0) inmoney,nvl(outmoney.value,0) outmoney,(nvl(rsm.value,0)+nvl(rm.value,0)+nvl(Margin.value,0)-nvl(MarginBack.value,0)+nvl(rfl.value,0)+nvl(fl.value,0)+nvl(balance.todaybalance,0)) quanyi from (select sum(nvl(RuntimeSettleMargin,0)) value,firmid from t_h_firm where 1=1 "
					+ djfilter + " group by firmid) rsm, "
					+ "(select sum(nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0)) value,firmid from t_h_firm where 1=1 " + djfilter
					+ " group by firmid) rm, " +

			"(select sum(nvl(RuntimeSettleMargin,0)) value,firmid from t_h_firm where 1=1 " + djfiltery + " group by firmid) rsmy, "
					+ "(select sum(nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0)) value,firmid from t_h_firm where 1=1 " + djfiltery
					+ " group by firmid) rmy, " + "(select sum(value) value,firmid from f_clientledger where code like '%Income%' " + bhfilter
					+ " group by firmid) income," + "(select sum(value) value,firmid from f_clientledger where code like '%Payout%' " + bhfilter
					+ " group by firmid) payout,"
					+ "(select sum(nvl(value,0)) value,firmid from f_clientledger fc where code in ('Margin_V','Margin_Z') " + marginfilter
					+ " group by firmid) Margin, "
					+ "(select sum(nvl(value,0)) value,firmid from f_clientledger fc where code in ('MarginBack_V','MarginBack_Z') " + marginfilter
					+ " group by firmid) MarginBack, "
					+ "(select sum(nvl(value,0)) value,firmid from f_clientledger fc where code in ('Margin_V','Margin_Z') " + bhfilter
					+ " group by firmid) Marginch, "
					+ "(select sum(nvl(value,0)) value,firmid from f_clientledger fc where code in ('MarginBack_V','MarginBack_Z') " + bhfilter
					+ " group by firmid) MarginBackch, " + "(select sum(nvl(RuntimeFL,0)) value,firmid from t_h_firm where 1=1 " + djfilter
					+ " group by firmid) rfl, " + "(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where 1=1 " + djfilter
					+ " group by firmid) fl, " + "(select sum(nvl(RuntimeFL,0)) value,firmid from t_h_firm where 1=1 " + djfiltery
					+ " group by firmid) rfly, " + "(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where 1=1 " + djfiltery
					+ " group by firmid) fly, " + "(select lastbalance,todaybalance,firmid from f_firmbalance ff where 1=1 " + bhfilter
					+ ") balance, " + "(select sum(nvl(value,0)) value,firmid from f_clientledger fc where code='Deposit' " + bhfilter
					+ " group by firmid) inmoney, " + "(select sum(nvl(value,0)) value,firmid from f_clientledger fc where code='Fetch' " + bhfilter
					+ " group by firmid) outmoney, " + "(select * from f_b_firmidandaccount where isopen=1 " + bankfilter + ") bank "
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
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public int addQSError(QSChangeResult qs, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_HXQS (BANKID,FIRMID,TRADEDATE,MONEY,TYPE,TRADETYPE,NOTE,CREATEDATE,STATUS) values (?,?,?,?,?,?,?,sysdate,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, qs.bankID);
			state.setString(2, qs.firmID);
			state.setDate(3, qs.tradeDate == null ? null : new java.sql.Date(qs.tradeDate.getTime()));
			state.setDouble(4, qs.money);
			state.setInt(5, qs.type);
			state.setInt(6, qs.tradeType);
			state.setString(7, qs.note);
			state.setInt(8, qs.status);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public Vector<QSChangeResult> getQSError(String filter, Connection conn) throws SQLException {
		Vector<QSChangeResult> result = new Vector();
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
			closeStatement(rs, state, null);
		}
		return result;
	}

	public int modQSError(QSChangeResult qs, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter = " and money=" + Tool.fmtDouble2(qs.money) + " ";
		if (qs.type != 0) {
			filter = filter + " and type=" + qs.type + " ";
		}
		if (qs.tradeType != 0) {
			filter = filter + " and tradeType=" + qs.tradeType + " ";
		}
		if ((qs.bankID != null) && (qs.bankID.trim().length() > 0)) {
			filter = filter + " and bankID='" + qs.bankID.trim() + "' ";
		}
		if ((qs.firmID != null) && (qs.firmID.trim().length() > 0)) {
			filter = filter + " and firmID='" + qs.firmID.trim() + "' ";
		}
		if (qs.tradeDate != null) {
			filter = filter + " and trunc(tradeDate)=to_date('" + Tool.fmtDate(qs.tradeDate) + "','yyyy-MM-dd') ";
		}
		try {
			sql = "update F_B_HXQS set note=" + (qs.note == null ? "'note'" : qs.note) + ",status=" + qs.status + " where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public int delQSError(QSChangeResult qs, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter = " ";
		if (qs.type != 0) {
			filter = filter + " and type=" + qs.type + " ";
		}
		if (qs.tradeType != 0) {
			filter = filter + " and tradeType=" + qs.tradeType + " ";
		}
		if ((qs.bankID != null) && (qs.bankID.trim().length() > 0)) {
			filter = filter + " and bankID='" + qs.bankID.trim() + "' ";
		}
		if ((qs.firmID != null) && (qs.firmID.trim().length() > 0)) {
			filter = filter + " and firmID='" + qs.firmID.trim() + "' ";
		}
		if (qs.tradeDate != null) {
			filter = filter + " and trunc(tradeDate)=to_date('" + Tool.fmtDate(qs.tradeDate) + "','yyyy-MM-dd') ";
		}
		try {
			sql = "delete F_B_HXQS where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public int addChangeFirmRights(String bankID, String[] firmIDs, java.util.Date tradeDate, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter1 = "";
		String filter2 = "";
		String filter21 = "";
		String bankfilter = "";
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			bankfilter = bankfilter + " and bankid='" + bankID.trim() + "'";
		}
		if ((firmIDs != null) && (firmIDs.length > 0)) {
			String firms = "'aa'";
			for (String firmID : firmIDs) {
				if ((firmID != null) && (firmID.trim().length() > 0)) {
					firms = firms + ",'" + firmID.trim() + "'";
				}
			}
			firms = "(" + firms + ")";
			filter1 = filter1 + " and firmid in " + firms;
			filter2 = filter2 + " and firmid in " + firms;
			filter21 = filter21 + " and firmid in " + firms;
			bankfilter = bankfilter + " and firmid in " + firms;
		}
		if (tradeDate == null) {
			tradeDate = new java.util.Date();
		}
		try {
			filter1 = filter1 + " and to_char(b_date,'yyyy-MM-dd')='" + Tool.fmtDate(tradeDate) + "'";
			filter2 = filter2 + " and to_char(cleardate,'yyyy-MM-dd')='" + Tool.fmtDate(tradeDate) + "'";
			java.util.Date yesdate = getlastDate(tradeDate, conn);
			filter21 = filter21 + " and to_char(cleardate,'yyyy-MM-dd')='" + Tool.fmtDate(yesdate) + "'";
			sql = "insert into F_B_Tradelist (TRADE_MONEY,TRADE_TYPE,B_MEMBER_CODE,B_MEMBER_NAME,S_MEMBER_CODE,S_MEMBER_NAME,TRADE_DATE,BARGAIN_CODE,SERIAL_ID,GOOD_CODE,GOOD_NAME,GOOD_QUANTITY,FLAG,BANKID,CREATEDATE) select TRADE_MONEY,TRADE_TYPE,B_MEMBER_CODE,B_MEMBER_NAME,S_MEMBER_CODE,S_MEMBER_NAME,to_date('"
					+ Tool.fmtTime(tradeDate)
					+ "','yyyy-MM-dd hh24:mi:ss') TRADE_DATE,'' BARGAIN_CODE,'' || SEQ_F_B_TRADELIST.nextval SERIAL_ID,'' GOOD_CODE,'' GOOD_NAME,0 GOOD_QUANTITY,'N' FLAG,BANKID,sysdate CREATEDATE from "
					+ "("
					+ "select nvl(SettleCompens.value,0)+nvl(Income.value,0)-nvl(Payout.value,0)+nvl(PL1.value,0)-nvl(PL2.value,0) TRADE_MONEY,1 TRADE_TYPE,'666666' B_MEMBER_CODE,'市场' B_MEMBER_NAME "
					+ ",bank.firmid S_MEMBER_CODE,bank.accountName S_MEMBER_NAME,bank.bankid BANKID from "
					+ "(select firmid,sum(nvl(value,0)) value from f_clientledger fc where fc.code='SettleCompens' " + filter1
					+ " group by firmid) SettleCompens," +

			"(select firmid,sum(nvl(value,0)) value from f_clientledger fc where exists(select code from f_ledgerfield fl where fc.code=fl.code and fl.code like 'Income%') "
					+ filter1 + " group by firmid) Income," +

			"(select firmid,sum(nvl(value,0)) value from f_clientledger fc where exists(select code from f_ledgerfield fl where fc.code=fl.code and fl.code like 'Payout%') "
					+ filter1 + " group by firmid) Payout," +

			"(select firmid,sum(nvl(value,0)) value from f_clientledger fc where exists(select code from f_ledgerfield fl where fc.code=fl.code and fl.code like '%PL') and value>=0 "
					+ filter1 + " group by firmid) PL1," +

			"(select firmid,-sum(nvl(value,0)) value from f_clientledger fc where exists(select code from f_ledgerfield fl where fc.code=fl.code and fl.code like '%PL') and value<0 "
					+ filter1 + " group by firmid) PL2," +

			"(select * from f_b_firmidandaccount where 1=1 and firmid not like 'D_%' " + bankfilter + ") bank "
					+ "where bank.firmid=SettleCompens.firmid(+) and bank.firmid=Income.firmid(+) and bank.firmid=Payout.firmid(+) and bank.firmid=PL1.firmid(+) and bank.firmid=PL2.firmid(+) "
					+

			"union all "
					+ "select nvl(rfl.value,0)-nvl(rfl1.value,0) TRADE_MONEY,4 TRADE_TYPE,bank.firmid B_MEMBER_CODE,bank.accountName B_MEMBER_NAME,'555555' S_MEMBER_CODE,'市场' S_MEMBER_NAME,bank.bankid BANKID from "
					+ "(select sum(nvl(RuntimeFL,0)) value,firmid from t_h_firm where 1=1 " + filter2 + " group by firmid) rfl,"
					+ "(select sum(nvl(RuntimeFL,0)) value,firmid from t_h_firm where 1=1 " + filter21 + " group by firmid) rfl1,"
					+ "(select * from f_b_firmidandaccount where 1=1 and firmid not like 'D_%' " + bankfilter + ") bank "
					+ "where bank.firmid=rfl.firmid(+) and bank.firmid=rfl1.firmid(+)" +

			"union all "
					+ "select nvl(tf.value,0)+nvl(bf.value,0) TRADE_MONEY,2 TRADE_TYPE,bank.firmid B_MEMBER_CODE,bank.accountName B_MEMBER_NAME,'888888' S_MEMBER_CODE,'市场' S_MEMBER_NAME,bank.bankid BANKID from "
					+ "(select firmid,sum(nvl(value,0)) value from f_clientledger fc where exists(select code from f_ledgerfield fl where fc.code=fl.code and fl.code like 'TradeFee%') "
					+ filter1 + " group by firmid) tf," +

			"(select firmid,sum(nvl(value,0)) value from f_clientledger fc where fc.code='BankFee' group by firmid) bf," +

			"(select * from f_b_firmidandaccount where 1=1 and firmid not like 'D_%' " + bankfilter + ") bank "
					+ "where bank.firmid=tf.firmid(+) and bank.firmid=bf.firmid(+) " +

			"union all "
					+ "select nvl(sf.value,0) TRADE_MONEY,3 TRADE_TYPE,bank.firmid B_MEMBER_CODE,bank.accountName B_MEMBER_NAME,'101011' S_MEMBER_CODE,'市场' S_MEMBER_NAME,bank.bankid BANKID from "
					+ "(select firmid,sum(nvl(value,0)) value from f_clientledger fc where exists(select code from f_ledgerfield fl where fc.code=fl.code and fl.code like 'SettleFee%') "
					+ filter1 + " group by firmid) sf," + "(select * from f_b_firmidandaccount where 1=1 and firmid not like 'D_%' " + bankfilter
					+ ") bank " + "where bank.firmid=sf.firmid(+) " +

			"union all "
					+ "select nvl(OtherItem.value,0) TRADE_MONEY,5 TRADE_TYPE,'444444' B_MEMBER_CODE,'市场' B_MEMBER_NAME,bank.firmid S_MEMBER_CODE,bank.accountName S_MEMBER_NAME,bank.bankid BANKID from "
					+ "(select firmid,sum(nvl(value,0)) value from f_clientledger fc where exists(select code from f_ledgerfield fl where fc.code=fl.code and fl.code like 'OtherItem%') "
					+ filter1 + " group by firmid) OtherItem," +

			"(select * from f_b_firmidandaccount where 1=1 and firmid not like 'D_%' " + bankfilter + ") bank "
					+ "where bank.firmid=OtherItem.firmid(+)" +

			")";

			System.out.println(sql);
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public int addChangeFirmFrozen(String bankID, String[] firmIDs, java.util.Date qdate, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter1 = "";
		String filter11 = "";
		String filter2 = "";
		String bankfilter = "";
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			bankfilter = bankfilter + " and bankid='" + bankID.trim() + "'";
		}
		if (firmIDs != null) {
			String firms = "('aa'";
			for (int i = 0; i < firmIDs.length; i++) {
				firms = firms + ",'" + firmIDs[i] + "'";
			}
			firms = firms + ")";
			bankfilter = bankfilter + " and firmid in " + firms;
			filter1 = filter1 + " and firmid in " + firms;
			filter11 = filter11 + " and firmid in " + firms;
			filter2 = filter2 + " and firmid in " + firms;
		}
		if (qdate == null) {
			qdate = new java.util.Date();
		}
		try {
			java.util.Date yestody = getlastDate(qdate, conn);
			filter1 = filter1 + " and to_char(cleardate,'yyyy-MM-dd')='" + Tool.fmtDate(qdate) + "'";
			filter11 = filter11 + " and to_char(cleardate,'yyyy-MM-dd')='" + Tool.fmtDate(yestody) + "'";
			filter2 = filter2 + " and to_char(b_date,'yyyy-MM-dd')='" + Tool.fmtDate(qdate) + "'";
			sql = "insert into F_B_MARGINS (SERIAL_ID,BARGAIN_CODE,TRADE_TYPE,TRADE_MONEY,MEMBER_CODE,MEMBER_NAME,TRADE_DATE,GOOD_CODE,GOOD_NAME,GOOD_QUANTITY,FLAG,BANKID,CREATEDATE) select '' || SEQ_F_B_MARGINS.nextval SERIAL_ID,'' BARGAIN_CODE,case when TRADE_MONEY>0 then 1 else 2 end TRADE_TYPE, abs(TRADE_MONEY) TRADE_MONEY,MEMBER_CODE,MEMBER_NAME,to_date('"
					+ Tool.fmtTime(qdate)
					+ "','yyyy-MM-dd hh24:mi:ss') TRADE_DATE,'' GOOD_CODE,'' GOOD_NAME,0 GOOD_QUANTITY,'N' FLAG,BANKID,sysdate CREATEDATE from " + "("
					+ "select nvl(rsm.value,0)+nvl(rm.value,0)+nvl(Margin.value,0)-nvl(rm2.value,0) TRADE_MONEY,bank.firmid MEMBER_CODE,bank.accountName MEMBER_NAME,bank.bankid bankid from "
					+ "(select sum(nvl(RuntimeSettleMargin,0)-nvl(ClearSettleMargin,0)) value,firmid from t_h_firm where 1=1 " + filter1
					+ " group by firmid) rsm, " +

			"(select sum(nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0)) value,firmid from t_h_firm where 1=1 " + filter1 + " group by firmid) rm, " +

			"(select nvl(sum(case when code = 'Margin_V' then value when code = 'Margin_Z' then value when code = 'MarginBack_V' then -value when code = 'MarginBack_Z' then -value else 0 end), 0) value,firmid from f_clientledger fc where exists (select code from f_ledgerfield fl where fc.code=fl.code and fl.code like 'Margin%') "
					+ filter2 + " group by firmid) Margin, " +

			"(select sum(nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0)) value,firmid from t_h_firm where 1=1 " + filter11 + " group by firmid) rm2, " +

			"(select * from f_b_firmidandaccount where 1=1 and firmid not like 'D_%' " + bankfilter + ") bank "
					+ "where bank.firmid=rsm.firmid(+) and bank.firmid=rm.firmid(+) and bank.firmid=Margin.firmid(+) and bank.firmid=rm2.firmid(+) "
					+ ")";

			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public int delChangeFirmRights(String bankID, java.util.Date qdate, String[] SERIAL_IDs, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter = "";
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and bankid='" + bankID.trim() + "'";
		}
		if (qdate != null) {
			filter = filter + " and to_char(TRADE_DATE,'yyyy-MM-dd')='" + Tool.fmtDate(qdate) + "'";
		}
		if ((SERIAL_IDs != null) && (SERIAL_IDs.length > 0)) {
			String SERIAL_ID = "'aa'";
			for (String SERIAL : SERIAL_IDs) {
				if ((SERIAL != null) && (SERIAL.trim().length() > 0)) {
					SERIAL_ID = SERIAL_ID + ",'" + SERIAL.trim() + "'";
				}
			}
			filter = filter + " and SERIAL_ID in(" + SERIAL_ID + ")";
		}
		try {
			sql = "update F_B_Tradelist set bankid='D_' || bankid where FLAG<>'Y' " + filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public int delChangeFirmFrozen(String bankID, java.util.Date qdate, String[] SERIAL_IDs, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter = "";
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and bankid='" + bankID.trim() + "'";
		}
		if (qdate != null) {
			filter = filter + " and to_char(TRADE_DATE,'yyyy-MM-dd')='" + Tool.fmtDate(qdate) + "'";
		}
		if ((SERIAL_IDs != null) && (SERIAL_IDs.length > 0)) {
			String SERIAL_ID = "'aa'";
			for (String SERIAL : SERIAL_IDs) {
				if ((SERIAL != null) && (SERIAL.trim().length() > 0)) {
					SERIAL_ID = SERIAL_ID + ",'" + SERIAL.trim() + "'";
				}
			}
			filter = filter + " and SERIAL_ID in(" + SERIAL_ID + ")";
		}
		try {
			sql = "update F_B_MARGINS set bankid='D_' || bankid where FLAG<>'Y' " + filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

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
			closeStatement(null, state, null);
		}
		return result;
	}

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
			closeStatement(null, state, null);
		}
		return result;
	}

	public Vector<TradeList> getChangeFirmRights(String filter, Connection conn) throws SQLException {
		Vector<TradeList> result = new Vector();
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
			closeStatement(rs, state, null);
		}
		return result;
	}

	public Vector<Margins> getChangeFirmFrozen(String filter, Connection conn) throws SQLException {
		Vector<Margins> result = new Vector();
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
			closeStatement(rs, state, null);
		}
		return result;
	}

	public Vector<TradeList> getChangeFirmRights(String filter) throws SQLException, ClassNotFoundException {
		Vector<TradeList> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
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
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public Vector<Margins> getChangeFirmFrozen(String filter) throws SQLException, ClassNotFoundException {
		Vector<Margins> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
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
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public Vector<FirmRightsValue> getTradeDataMsg(String bankId, String firmid, java.util.Date qdate) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>从财务和交易系统获取交易商资金余额及各交易板块权益  getTradeDataMsg  " + new java.util.Date());
		Vector<FirmRightsValue> hmfai = new Vector();

		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		java.sql.Date lastDay = null;
		try {
			conn = getConnection();
			String sql0 = "select max(B_DATE) lastDate from f_h_firmbankfunds t where t.b_date < to_date(?,'yyyy-MM-dd')";
			state = conn.prepareStatement(sql0);
			state.setString(1, Tool.fmtDate(qdate));
			rs = state.executeQuery();
			while (rs.next()) {
				lastDay = rs.getDate("lastDate");
			}
			if (lastDay == null) {
				lastDay = new java.sql.Date(0, 0, 1);
			}
			StringBuffer sql = new StringBuffer(" ");
			sql.append("select f.firmid, ");
			sql.append("f.contact, ");
			sql.append("f.bankid, ");
			sql.append("f.account, ");
			sql.append("f.accountname, ");
			sql.append("f.cardtype, ");
			sql.append("f.card, ");
			sql.append("nvl(today.firmfee, 0) Fee, ");
			sql.append("nvl(today.rights, 0) rights, ");
			sql.append("nvl(yestoday.rights, 0) lastrights, ");
			sql.append("nvl(today.balance, 0) balance, ");
			sql.append("nvl(today.outInMoney,0) crjsum ");
			sql.append("from ");
			sql.append("(select * from f_h_firmbankfunds where b_date=to_date(?,'yyyy-MM-dd')) today, ");
			sql.append("(select * from f_h_firmbankfunds where b_date=to_date(?,'yyyy-MM-dd')) yestoday, ");
			sql.append("(select * from f_b_firmidandaccount where isopen=1) f  ");
			sql.append("where f.firmid=today.firmid(+) ");
			sql.append(" and f.firmid=yestoday.firmid(+) ");
			sql.append(" and f.bankid=today.bankcode(+) ");
			sql.append(" and f.bankid=yestoday.bankcode(+) ");
			sql.append(" and f.bankid=? ");
			System.out.println("查询权益SQL[" + sql.toString() + "]");
			state = conn.prepareStatement(sql.toString());
			state.setString(1, Tool.fmtDate(qdate));
			state.setString(2, Tool.fmtDate(lastDay));
			state.setString(3, bankId);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmRightsValue fai = new FirmRightsValue();
				fai.setFirmid(rs.getString("contact"));
				fai.setAccount(rs.getString("account"));
				fai.setAccountname(rs.getString("accountName"));
				fai.setAvilableBlance(rs.getDouble("rights"));
				fai.setTimebargainBalance(0.0D);
				fai.setVendueBalance(0.0D);
				fai.setZcjsBalance(0.0D);
				fai.setPayment(0.0D);
				fai.setIncome(0.0D);
				fai.setFee(rs.getDouble("fee"));
				if (rs.getDouble("rights") - rs.getDouble("lastrights") - rs.getDouble("crjsum") + rs.getDouble("fee") > 0.0D) {
					fai.setDai(rs.getDouble("rights") - rs.getDouble("lastrights") - rs.getDouble("crjsum") + rs.getDouble("fee"));
					fai.setJie(0.0D);
				} else {
					fai.setJie(rs.getDouble("rights") - rs.getDouble("lastrights") - rs.getDouble("crjsum") + rs.getDouble("fee"));
					fai.setDai(0.0D);
				}
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

	public Vector<FirmRightsValue> getTradeDataMsg(String bankId, String firmid, String qdate) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>从财务和交易系统获取交易商资金余额及各交易板块权益  getTradeDataMsg  " + new java.util.Date());
		Vector<FirmRightsValue> hmfai = new Vector();

		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		String filter = " ";
		String filter_f_fb = " ";
		String filter_z_v = " ";
		String filter_t_h_f = " ";
		if ((bankId != null) && (!"".equals(bankId.trim()))) {
			filter = filter + " and i.bankId='" + bankId.trim() + "' ";
		}
		if ((firmid != null) && (!"".equals(firmid.trim()))) {
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
			sql = "select i.firmid qy_firmid,i.accountname accountname,i.account account, nvl(a.todaybalance, 0) f_balance, nvl(b.RuntimeMargin, 0) + nvl(b.RuntimeFL, 0) + nvl(b.RuntimeSettleMargin, 0) + nvl(c.floatingloss, 0) t_money, nvl(d.money, 0) v_money, nvl(e.money, 0) z_money, nvl(f.money, 0) pay_money, nvl(g.money, 0) in_money, nvl(h.money, 0) fee_money, nvl(jie.money, 0) jie_money, nvl(dai.money, 0) dai_money from (select firmid, todaybalance from f_firmbalance where 1 = 1 "
					+ filter_f_fb + ") a,"
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
				String accountName = rs.getString("accountname");
				double f_balance = rs.getDouble("f_balance");
				double t_money = rs.getDouble("t_money");
				double v_money = rs.getDouble("v_money");
				double z_money = rs.getDouble("z_money");
				double pay_money = rs.getDouble("pay_money");
				double in_money = rs.getDouble("in_money");
				double fee_money = rs.getDouble("fee_money");
				double jie_money = rs.getDouble("jie_money");
				double dai_money = rs.getDouble("dai_money");

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

	public Vector<BankFirmRightValue> getBankCapital(BankFirmRightValue bfrv) {
		Vector<BankFirmRightValue> list = new Vector();
		String sql = "select * from F_B_BankCapitalResult where 1=1 ";
		if ((bfrv.bankId != null) && (!bfrv.bankId.trim().equals(""))) {
			sql = sql + "and bankId='" + bfrv.bankId.trim() + "' ";
		}
		if ((bfrv.firmId != null) && (!bfrv.firmId.trim().equals(""))) {
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
			closeStatement(rs, state, conn);
		}
		return list;
	}

	public Vector<BankFirmRightValue> getBankCapital(String filter) {
		Vector<BankFirmRightValue> list = new Vector();
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
			closeStatement(rs, state, conn);
		}
		return list;
	}

	public long addProperBalance(ProperBalanceValue pbv) {
		long result = 0L;
		String sql = "insert into F_B_ProperBalance (bankId,allMoney,gongMoney,otherMoney,bdate) values(?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement state = null;
		try {
			if (pbv == null) {
				return -2L;
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
			result = -1L;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			result = -1L;
			e.printStackTrace();
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	public long updateProperBalance(ProperBalanceValue pbv) {
		long result = 0L;
		String sql = "update F_B_ProperBalance set allMoney=?,gongMoney=?,otherMoney=? where trunc(bdate)=to_date(?,'yyyy-MM-dd') and bankId=?";
		Connection conn = null;
		PreparedStatement state = null;
		try {
			if (pbv == null) {
				return -2L;
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
			result = -1L;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			result = -1L;
			e.printStackTrace();
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	public long delProperBalance(ProperBalanceValue pbv) {
		long result = 0L;
		String sql = "delete F_B_ProperBalance where 1=1 ";
		Connection conn = null;
		PreparedStatement state = null;
		try {
			if (pbv != null) {
				if ((pbv.bankId != null) && (!pbv.bankId.trim().equals(""))) {
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
			result = -1L;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			result = -1L;
			e.printStackTrace();
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	public Vector<ProperBalanceValue> getProperBalance(ProperBalanceValue pbv) {
		Vector<ProperBalanceValue> list = new Vector();
		String sql = "select * from F_B_ProperBalance where 1=1 ";
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			if (pbv != null) {
				if ((pbv.bankId != null) && (!pbv.bankId.trim().equals(""))) {
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
			closeStatement(rs, state, conn);
		}
		return list;
	}

	public Vector<ProperBalanceValue> getProperBalance(String filter) {
		Vector<ProperBalanceValue> list = new Vector();
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
			closeStatement(rs, state, conn);
		}
		return list;
	}

	public long updateBankCapital(BankFirmRightValue bfrv) {
		long result = 0L;
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
			result = -1L;
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			result = -1L;
			e.printStackTrace();
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	public long addBankCapital(BankFirmRightValue bfrv) {
		long result = 0L;
		String sql = "insert into F_B_BankCapitalResult (bankId,firmId,bankRight,maketRight,reason,bdate) values(?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement state = null;
		try {
			if (bfrv == null) {
				return -2L;
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
			closeStatement(null, state, conn);
		}
		return result;
	}

	public RZQSValue getXYQSValue(String bankID, String[] firmIDs, java.util.Date tradeDate) throws SQLException, ClassNotFoundException {
		RZQSValue result = new RZQSValue();
		result.bankID = bankID;
		result.tradeDate = tradeDate;
		java.sql.Date lastDay = null;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuffer sbfSQL = new StringBuffer(" ");
			String sql = "select max(B_DATE) lastDate from f_h_firmbankfunds t where t.b_date < to_date(?,'yyyy-MM-dd')";
			state = conn.prepareStatement(sql);
			state.setString(1, Tool.fmtDate(tradeDate));
			rs = state.executeQuery();
			while (rs.next()) {
				lastDay = rs.getDate("lastDate");
			}
			if (lastDay == null) {
				lastDay = new java.sql.Date(0, 0, 1);
			}
			sbfSQL.append("select f.firmid, ");
			sbfSQL.append("f.contact, ");
			sbfSQL.append("f.bankid, ");
			sbfSQL.append("f.account, ");
			sbfSQL.append("f.accountname, ");
			sbfSQL.append("f.cardtype, ");
			sbfSQL.append("f.card, ");
			sbfSQL.append("nvl(today.firmfee, 0) Fee, ");
			sbfSQL.append("nvl(today.rights, 0) rights, ");
			sbfSQL.append("nvl(today.balance, 0) balance, ");
			sbfSQL.append("nvl(today.RIGHTSFROZENFUNDS, 0) frozen, ");
			sbfSQL.append("nvl(yestoday.rights, 0) lastRights, ");
			sbfSQL.append("nvl(yestoday.balance, 0) lastBalance, ");
			sbfSQL.append("nvl(yestoday.RIGHTSFROZENFUNDS, 0) lastFrozen, ");
			sbfSQL.append("nvl(today.outInMoney,0) crjsum ");
			sbfSQL.append("from ");
			sbfSQL.append("(select * from f_h_firmbankfunds where b_date=to_date(?,'yyyy-MM-dd')) today, ");
			sbfSQL.append("(select * from f_h_firmbankfunds where b_date=to_date(?,'yyyy-MM-dd')) yestoday, ");
			sbfSQL.append("(select * from f_b_firmidandaccount where isopen=1) f  ");
			sbfSQL.append("where f.firmid=today.firmid(+) ");
			sbfSQL.append(" and f.firmid=yestoday.firmid(+) ");
			sbfSQL.append(" and f.bankid=today.bankcode(+) ");
			sbfSQL.append(" and f.bankid=yestoday.bankcode(+) ");
			sbfSQL.append(" and f.bankid=? ");
			sql = sbfSQL.toString();
			System.out.println("兴业清算sql：" + sql);
			state = conn.prepareStatement(sql);
			state.setString(1, Tool.fmtDate(tradeDate));
			state.setString(2, Tool.fmtDate(lastDay));
			state.setString(3, bankID);
			rs = state.executeQuery();
			MarketRightValue mrv = new MarketRightValue();
			mrv.maketMoney = new BigDecimal("0");
			while (rs.next()) {
				FirmRightValue value = new FirmRightValue();
				value.actionID = String.valueOf(getActionID());
				value.firmID = rs.getString("contact");
				value.account = rs.getString("account");

				value.availableBalance = Arith.sub(Arith.sub(rs.getDouble("balance"), rs.getDouble("lastBalance")), rs.getDouble("crjsum"));
				value.billMoney = 0.0D;

				value.cash = Arith.sub(rs.getDouble("frozen"), rs.getDouble("lastFrozen"));
				value.cashMoney = 0.0D;
				value.credit = 0.0D;

				value.firmErrorMoney = Arith.sub(Arith.sub(rs.getDouble("rights"), rs.getDouble("lastRights")), rs.getDouble("crjsum"));

				mrv.bankErrorMoney = Arith.add(mrv.bankErrorMoney, Arith.add(value.firmErrorMoney, rs.getDouble("fee")));
				mrv.maketMoney = mrv.maketMoney.add(rs.getBigDecimal("fee"));
				System.out.println("交易商[" + rs.getString("firmID") + "]的自有资金[" + rs.getBigDecimal("fee") + "]总自有资金[" + mrv.maketMoney + "]");
				Tool.log("交易商[" + rs.getString("firmID") + "]的自有资金[" + rs.getBigDecimal("fee") + "]总自有资金[" + mrv.maketMoney + "]");
				result.putFrv(value);
			}
			mrv.bankErrorMoney = Arith.sub(0.0F, mrv.bankErrorMoney);
			result.setMarketRight(mrv);
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public RZDZValue getXYDZValue(String bankID, String[] firmIDs, java.util.Date tradeDate) throws SQLException, ClassNotFoundException {
		RZDZValue result = new RZDZValue();
		result.bankID = bankID;
		result.tradeDate = tradeDate;
		java.sql.Date lastDay = null;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuffer sbfSQL = new StringBuffer(" ");
			String sql = "select max(B_DATE) lastDate from f_h_firmbankfunds t where t.b_date < to_date(?,'yyyy-MM-dd')";
			state = conn.prepareStatement(sql);
			state.setString(1, Tool.fmtDate(tradeDate));
			rs = state.executeQuery();
			while (rs.next()) {
				lastDay = rs.getDate("lastDate");
			}
			if (lastDay == null) {
				lastDay = new java.sql.Date(0, 0, 1);
			}
			sbfSQL.append("select f.firmid, ");
			sbfSQL.append("f.contact, ");
			sbfSQL.append("f.bankid, ");
			sbfSQL.append("f.account, ");
			sbfSQL.append("f.accountname, ");
			sbfSQL.append("f.cardtype, ");
			sbfSQL.append("f.card, ");
			sbfSQL.append("nvl(today.firmfee, 0) Fee, ");
			sbfSQL.append("nvl(today.rights, 0) rights, ");
			sbfSQL.append("nvl(today.balance, 0) balance, ");
			sbfSQL.append("nvl(today.RIGHTSFROZENFUNDS, 0) frozen, ");
			sbfSQL.append("nvl(yestoday.rights, 0) lastRights, ");
			sbfSQL.append("nvl(yestoday.balance, 0) lastBalance, ");
			sbfSQL.append("nvl(yestoday.RIGHTSFROZENFUNDS, 0) lastFrozen, ");
			sbfSQL.append("nvl(today.outInMoney,0) crjsum ");
			sbfSQL.append("from ");
			sbfSQL.append("(select * from f_h_firmbankfunds where b_date=to_date(?,'yyyy-MM-dd')) today, ");
			sbfSQL.append("(select * from f_h_firmbankfunds where b_date=to_date(?,'yyyy-MM-dd')) yestoday, ");
			sbfSQL.append("(select * from f_b_firmidandaccount where isopen=1) f  ");
			sbfSQL.append("where f.firmid=today.firmid(+) ");
			sbfSQL.append(" and f.firmid=yestoday.firmid(+) ");
			sbfSQL.append(" and f.bankid=today.bankcode(+) ");
			sbfSQL.append(" and f.bankid=yestoday.bankcode(+) ");
			sbfSQL.append(" and f.bankid=? ");
			sql = sbfSQL.toString();
			System.out.println("兴业清算sql：" + sql);
			state = conn.prepareStatement(sql);
			state.setString(1, Tool.fmtDate(tradeDate));
			state.setString(2, Tool.fmtDate(lastDay));
			state.setString(3, bankID);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmDZValue value = new FirmDZValue();
				value.firmID = rs.getString("contact");
				value.account = rs.getString("account");
				value.firmRights = rs.getDouble("rights");
				value.cashRights = 0.0D;
				value.billRights = 0.0D;
				value.availableBalance = rs.getDouble("balance");
				value.butfunds = rs.getDouble("balance");
				value.cash = rs.getDouble("frozen");
				value.credit = 0.0D;

				value.quanyibh = Arith.sub(Arith.sub(rs.getDouble("rights"), rs.getDouble("lastRights")), rs.getDouble("crjsum"));
				result.putFdv(value);
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

	public int addZFPH(ZFPHValue zfph, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_ZFPH (BANKID,TRADEDATE,CURRENCY,TYPE,LASTACCOUNTBALANCE,ACCOUNTBALANCE,RESULT,CREATEDATE) values (?,?,?,?,?,?,?,sysdate)";
			state = conn.prepareStatement(sql);
			state.setString(1, zfph.bankID);
			state.setDate(2, new java.sql.Date(zfph.tradeDate == null ? 0L : zfph.tradeDate.getTime()));
			state.setString(3, zfph.currency);
			state.setInt(4, zfph.type);
			state.setBigDecimal(5, zfph.lastAccountBalance);
			state.setBigDecimal(6, zfph.accountBalance);
			state.setInt(7, zfph.result);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public Vector<ZFPHValue> getZFPH(String bankID, java.util.Date tradeDate, int result) throws SQLException, ClassNotFoundException {
		Vector<ZFPHValue> resultbak = null;
		Connection conn = null;
		try {
			conn = getConnection();
			resultbak = getZFPH(bankID, tradeDate, result, conn);
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeStatement(null, null, conn);
		}
		return resultbak;
	}

	public Vector<ZFPHValue> getZFPH(String bankID, java.util.Date tradeDate, int result, Connection conn) throws SQLException {
		Vector<ZFPHValue> vector = new Vector();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		String filter = "";
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and bankID='" + bankID.trim() + "' ";
		}
		if (tradeDate != null) {
			filter = filter + " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd') ";
		}
		if (result >= 0) {
			filter = filter + " and result=" + result + " ";
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
			closeStatement(rs, state, null);
		}
		return vector;
	}

	public int delZFPH(String bankID, java.util.Date tradeDate, int result, Connection conn) throws SQLException {
		int rst = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter = "";
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and bankID='" + bankID.trim() + "' ";
		}
		if (tradeDate != null) {
			filter = filter + " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd') ";
		}
		if (result >= 0) {
			filter = filter + " and result=" + result + " ";
		}
		try {
			sql = "delete F_B_ZFPH where 1=1 " + filter;

			System.out.println("DEL_SQL:" + sql);

			state = conn.prepareStatement(sql);
			rst = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return rst;
	}

	public int addFFHD(FFHDValue ffhd, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_FFHD (BANKID,TRADEDATE,FIRMID,ACCOUNT,CURRENCY,TYPE,REASION,CREATEDATE,balanceM,cashM,billM,useBalanceM,frozenCashM,frozenLoanM,balanceB,cashB,billB,useBalanceB,frozenCashB,frozenLoanB) values (?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?)";
			state = conn.prepareStatement(sql);
			if ((ffhd != null) && (ffhd.getFdv() != null)) {
				Vector<FirmDateValue> vv = ffhd.getFdv();
				for (FirmDateValue ff : vv) {
					try {
						ff.firmID = ((CorrespondValue) getCorrespondList(" where contact='" + ff.firmID.trim() + "' and bankid= '" + ff.bankID + "'")
								.get(0)).firmID;
					} catch (ClassNotFoundException e) {
						Tool.log("获取firmiD异常： " + Tool.getExceptionTrace(e));
					}
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
			closeStatement(null, state, null);
		}
		return result;
	}

	public Vector<FirmDateValue> getFFHD(String firmID, String bankID, java.util.Date tradeDate) throws SQLException, ClassNotFoundException {
		Vector<FirmDateValue> result = null;
		String filter = "";
		if ((firmID != null) && (firmID.trim().length() > 0)) {
			filter = filter + " and firmID='" + firmID.trim() + "'";
		}
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and bankID = '" + bankID.trim() + "'";
		}
		if (tradeDate != null) {
			filter = filter + " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
		}
		Connection conn = null;
		try {
			conn = getConnection();
			result = getFFHD(filter, conn);
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeStatement(null, null, conn);
		}
		return result;
	}

	public Vector<FirmDateValue> getFFHD(String filter, Connection conn) throws SQLException {
		Vector<FirmDateValue> result = new Vector();
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
			closeStatement(rs, state, null);
		}
		return result;
	}

	public int delFFHD(String bankID, String[] firmIDs, java.util.Date tradeDate, Connection conn) throws SQLException {
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		String filter = "";
		if ((bankID != null) && (bankID.trim().length() > 0)) {
			filter = filter + " and bankID='" + bankID.trim() + "' ";
		}
		if ((firmIDs != null) && (firmIDs.length > 0)) {
			String firms = "";
			for (String firmID : firmIDs) {
				if ((firmID != null) && (firmID.trim().length() > 0)) {
					firms = firms + "'" + firmID.trim() + "',";
				}
			}
			if ((firms != null) && (firms.trim().length() > 0)) {
				filter = filter + " and firmID in (" + firms.trim().substring(0, firms.trim().length() - 1) + ")";
			}
		}
		if (tradeDate != null) {
			filter = filter + " and trunc(tradeDate)=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
		}
		try {
			sql = "delete F_B_FFHD where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public int addMarketMoney(XYMarketMoney xymm) throws SQLException, ClassNotFoundException {
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "insert into F_B_MAKETMONEY (ID,BANKID,BANKNUMBER,TYPE,ADDDELT,MONEY,NOTE) values (SEQ_NH_F_B_MAKETMONEY.nextval,?,?,?,?,?,?)";
			conn = getConnection();
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
			closeStatement(null, state, conn);
		}
		return result;
	}

	public int modMarketMoney(XYMarketMoney xymm) throws SQLException, ClassNotFoundException {
		int result = 0;
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "update F_B_MAKETMONEY set BANKID=?,BANKNUMBER=?,TYPE=?,ADDDELT=?,MONEY=?,NOTE=? where ID=?";
			conn = getConnection();
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
			closeStatement(null, state, conn);
		}
		return result;
	}

	public Vector<XYMarketMoney> getMarketMoney(String filter) throws SQLException, ClassNotFoundException {
		Vector<XYMarketMoney> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "select * from F_B_MAKETMONEY where 1=1 ";
		try {
			sql = sql + filter;
			conn = getConnection();
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
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public long addFirmTradeStatus(FirmTradeStatus val) throws SQLException {
		Tool.log("===>>>添加客户协议状态   addFirmTradeStatus" + new java.util.Date());
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rst = null;
		String sql = null;
		long id = 0L;
		try {
			conn = getConnection();
			sql = "insert into F_B_FIRMTRADESTATUS(BANKID,DEALID,COBRN,TXDATE,BANKACC,FUNDACC,CUSTNAME,CURCODE,STATUS,COMPAREDATE) values(?,?,?,?,?,?,?,?,?,?)";
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
			id = -1L;
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			id = -1L;
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
		long id = 0L;
		try {
			conn = getConnection();
			sql = "insert into F_B_TRADEDETAILACC(BATCHNO,BANKID,DEALID,COBRN,TXDATE,TXTIME,BKSERIAL,COSERIAL,BANKACC,FUNDACC,CUSTNAME,TXOPT,TXCODE,CURCODE,COMPAREDATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			id = -1L;
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			id = -1L;
		} finally {
			closeStatement(rst, stmt, conn);
		}
		return id;
	}

	public Vector<FirmTradeStatus> getFirmTradeStatusList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得客户协议状态信息列表   getFirmTradeStatusList  " + new java.util.Date());
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
		} finally {
			closeStatement(rs, stmt, conn);
		}
		return veVal;
	}

	public Vector<TradeDetailAccount> getTradeDetailAccountList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得账户类交易对账信息列表   getTradeDetailAccountList  " + new java.util.Date());
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
		} finally {
			closeStatement(rs, stmt, conn);
		}
		return veVal;
	}

	public Vector<FirmBalance> getFirmBalance(String bankID, java.util.Date qdate) throws SQLException, Exception {
		Vector<FirmBalance> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			String filter_Fee = " and b_date=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') ";
			String Bank_filter = "";
			if ((bankID != null) && (bankID.trim().length() > 0)) {
				Bank_filter = Bank_filter + " and bankid='" + bankID.trim() + "' ";
			}
			String Fee_filter = filter_Fee;

			String filter = " ";
			String filter_f_fb = " ";
			String filter_z_v = " ";
			String filter_t_h_f = " ";

			String filter_f_fb_today = " ";
			String filter_z_v_today = " ";
			String filter_t_h_f_today = " ";
			if ((bankID != null) && (!"".equals(bankID.trim()))) {
				filter = filter + " and i.bankId='" + bankID.trim() + "' and i.firmid not like '%D'";
			}
			String firmid = null;
			if ((firmid != null) && (!"".equals(firmid.trim()))) {
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
			sql =

			"select i.firmid , i.bankid, i.account, i.accountname, i.cardtype, i.card, nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + nvl(d1.money, 0)+ nvl(e1.money, 0) money ,nvl(a.todaybalance, 0) + nvl(b.RuntimeMargin, 0) + nvl(b.RuntimeFL, 0) + nvl(b.RuntimeSettleMargin, 0) + nvl(c.floatingloss, 0) + nvl(d.money, 0)+ nvl(e.money, 0) lastMoney ,nvl(Fee.value,0) Fee ,nvl(OutMoney.value, 0) ,nvl(InMoney.value, 0) ,nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + nvl(d1.money, 0)+ nvl(e1.money, 0) - nvl(a.todaybalance, 0) - nvl(b.RuntimeMargin, 0) - nvl(b.RuntimeFL, 0) - nvl(b.RuntimeSettleMargin, 0) - nvl(c.floatingloss, 0) - nvl(d.money, 0)- nvl(e.money, 0) + nvl(Fee.value,0) + nvl(OutMoney.value, 0) - nvl(InMoney.value, 0) QYChangeMoney from (select firmid, todaybalance from f_firmbalance where 1 = 1 "
					+ filter_f_fb + ") a,"
					+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
					+ filter_t_h_f + ") b," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 " + filter_t_h_f
					+ " group by firmid) c,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
					+ filter_z_v + " group by firmid) d,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
					+ filter_z_v + " group by firmid) e," + "(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Deposit' "
					+ filter_Fee + " group by firmid ) InMoney,"
					+ "(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Fetch' " + filter_Fee
					+ " group by firmid ) OutMoney,"
					+ "(select firmid,bankid,accountname,account,isopen,cardType, card from f_b_Firmidandaccount) i ,"
					+ "(select fc.firmid firmid,nvl(sum(nvl(fc.value,0)),0) value from f_clientledger fc where exists (select code from f_ledgerfield fl where code like '%Fee%' and fc.code=fl.code ) "
					+ Fee_filter + " group by firmid) Fee," + "(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb_today
					+ ") a1,"
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
				fb.yesQYMoney = rs.getDouble("lastMoney");
				fb.date = qdate;
				result.add(fb);
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public boolean getSystemStatus() throws SQLException, ClassNotFoundException {
		Tool.log("获取交易系统结算状态");
		boolean flag = false;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		conn = getConnection();
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
		closeStatement(rs, state, conn);
		return flag;
	}

	public Vector<BankQSNetChild> getQSBankDate(String bankID, java.util.Date qdate) throws Exception {
		Tool.log("获取某日的清算信息  getQSChild 时间：" + Tool.fmtDate(new java.util.Date()));
		Vector<BankQSNetChild> vec = new Vector();
		Connection conn = null;

		conn = getConnection();
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
		if ((bankID != null) && (bankID.trim().length() > 0) && (!bankID.trim().equals("-1"))) {
			filter2 = filter2 + " and bankID='" + bankID + "' ";
		}
		try {
			java.util.Date yestody = getlastDate(qdate, conn);
			sql = "select h.firmid,nvl(a.value,0)+(case when nvl(k.value,0)>0 then nvl(k.value,0) else 0 end) AddTranAmount,nvl(b.value,0)+(case when nvl(k.value,0)<0 then nvl(-k.value,0) else 0 end) CutTranAmount,nvl(c.value,0) ProfitAmount,nvl(d.value,0) LossAmount,j.bankid bankid , j.bankname bankName ,nvl(Fee.value,0) Fee ,nvl(OutMoney.value, 0) OutMoney,nvl(InMoney.value, 0) InMoney,nvl(a1.todaybalance, 0) todayKy,nvl(a2.todaybalance, 0) lastKy,nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + nvl(d1.money, 0)+ nvl(e1.money, 0) todayQY,nvl(a2.todaybalance, 0) + nvl(b2.RuntimeMargin, 0) + nvl(b2.RuntimeFL, 0) + nvl(b2.RuntimeSettleMargin, 0) + nvl(c2.floatingloss, 0) + nvl(d2.money, 0)+ nvl(e2.money, 0) lastDayQY,";

			sql = sql
					+ "nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + nvl(d1.money, 0)+ nvl(e1.money, 0) -(nvl(a2.todaybalance, 0) + nvl(b2.RuntimeMargin, 0) + nvl(b2.RuntimeFL, 0) + nvl(b2.RuntimeSettleMargin, 0) + nvl(c2.floatingloss, 0) + nvl(d2.money, 0)+ nvl(e2.money, 0)) +nvl(Fee.value,0) + nvl(OutMoney.value, 0) - nvl(InMoney.value, 0) QYChangeMoney, ";

			sql = sql
					+ "nvl(a1.todaybalance, 0) - nvl(a2.todaybalance, 0) + nvl(Fee.value,0) + nvl(OutMoney.value, 0) - nvl(InMoney.value, 0) kyChangeMoney ";

			sql = sql
					+ "from (select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('Income','Income_Z','Income_V') " + filter + " group by fc.firmid) a, "
					+ "(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('Payout','Payout_Z','Payout_V') " + filter + " group by fc.firmid) b, "
					+ "(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('TradePL','SettlePL') and value>=0 " + filter
					+ " group by fc.firmid) c, "
					+ "(select fc.firmid,sum(nvl(-fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('TradePL','SettlePL') and value<0 " + filter + " group by fc.firmid) d, "
					+ "(select fc.firmid,sum(nvl(case when fl.code='SettleCompens' then -fc.value else fc.value end,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') and fl.code in ('BankFee','TradeFee','SettleFee','TradeFee_Z','TradeFee_V','SettleFee_Z','SettleCompens') "
					+ filter + " group by fc.firmid) e, " + "( " + "select nvl(fb.value,0)+nvl(fc.value,0)+nvl(fd.value,0)" +

			"+nvl(fg.value,0) valued,nvl(fb1.value,0)+nvl(fc1.value,0)+nvl(fd1.value,0)" +

			"+nvl(fg1.value,0) valuez" +

			", fe.firmid from " + "(select nvl(RuntimeSettleMargin,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') " + filter + ") fb, " + "(select nvl(RuntimeFL,0) value,firmid from t_h_firm where cleardate=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') " + filter + ") fc, "
					+ "(select nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') " + filter + ") fd, "
					+ "(select nvl(sum(case when code = 'Margin_V' then value when code = 'Margin_Z' then value when code = 'MarginBack_V' then -value when code = 'MarginBack_Z' then -value else 0 end), 0) value,firmid from f_clientledger where code in ('MarginBack_V', 'Margin_V','MarginBack_Z', 'Margin_Z') and to_char(b_date,'yyyy-MM-dd')<='"
					+ Tool.fmtDate(qdate) + "' " + filter + " group by firmid) fg, " +

			"(select nvl(RuntimeSettleMargin,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody) + "','yyyy-MM-dd') "
					+ filter + ") fb1, " + "(select nvl(RuntimeFL,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody)
					+ "','yyyy-MM-dd') " + filter + ") fc1, "
					+ "(select nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody)
					+ "','yyyy-MM-dd') " + filter + ") fd1, "
					+ "(select nvl(sum(case when code = 'Margin_V' then value when code = 'Margin_Z' then value when code = 'MarginBack_V' then -value when code = 'MarginBack_Z' then -value else 0 end), 0) value,firmid from f_clientledger where code in ('MarginBack_V', 'Margin_V','MarginBack_Z', 'Margin_Z') and to_char(b_date,'yyyy-MM-dd')<='"
					+ Tool.fmtDate(yestody) + "' " + filter + " group by firmid) fg1, " +

			"(select firmid from f_b_firmidandaccount where 1=1 " + filter2 + ") fe "
					+ "where fe.firmid=fb.firmid(+) and fe.firmid=fc.firmid(+) and fe.firmid=fd.firmid(+)" +

			" and fe.firmid=fg.firmid(+) and fe.firmid=fb1.firmid(+) and fe.firmid=fc1.firmid(+) and fe.firmid=fd1.firmid(+)" +

			" and fe.firmid=fg1.firmid(+) " + ") f, " + "(" + "select nvl(ia.value,0) valued,nvl(ib.value,0) valuez,ic.firmid from "
					+ "(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where trunc(cleardate)=to_date('" + Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') " + filter + " group by firmid) ia,"
					+ "(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where trunc(cleardate)=to_date('" + Tool.fmtDate(yestody)
					+ "','yyyy-MM-dd') " + filter + " group by firmid) ib," + "(select firmid from f_b_firmidandaccount where 1=1 " + filter2
					+ ") ic " + "where ic.firmid=ia.firmid(+) and ic.firmid=ib.firmid(+)" + ") i,"
					+ "(select nvl(sum(nvl(value,0)),0) value,firmid  from f_clientledger where code in ('OtherItem','OtherItem_Z','OtherItem_V') and trunc(B_Date)=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') " + filter + " group by firmid) k, "
					+ "(select todaybalance value,firmid from f_firmbalance where b_date=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') "
					+ filter + ") g," + "(select account1,ACCOUNTNAME1,firmid,bankid from f_b_firmidandaccount where 1=1 " + filter2 + ") h ," +

			"(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb + ") a2,"
					+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
					+ filter_t_h_f + ") b2," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 "
					+ filter_t_h_f + " group by firmid) c2,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
					+ filter_z_v + " group by firmid) d2,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
					+ filter_z_v + " group by firmid) e2," +

			"(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Deposit' " + filter_Fee + " group by firmid "
					+ ") InMoney," + "(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Fetch' " + filter_Fee
					+ " group by firmid " + ") OutMoney," +

			"(select fc.firmid firmid,nvl(sum(nvl(fc.value,0)),0) value from f_clientledger fc where exists (select code from f_ledgerfield fl where code like '%Fee%' and fc.code=fl.code ) "
					+ Fee_filter + " group by firmid" + ") Fee," +

			"(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb_today + ") a1,"
					+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
					+ filter_t_h_f_today + ") b1," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 "
					+ filter_t_h_f_today + " group by firmid) c1,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
					+ filter_z_v_today + " group by firmid) d1,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
					+ filter_z_v_today + " group by firmid) e1 ," + "(select bankid,bankName from f_b_banks where validflag = 0 ) j " +

			" where h.firmid=a.firmid(+) " + "and h.firmid=b.firmid(+) " + "and h.firmid=c.firmid(+) " + "and h.firmid=d.firmid(+) "
					+ "and h.firmid=e.firmid(+) " + "and h.firmid=f.firmid(+) " + "and h.firmid=g.firmid(+) " + "and h.firmid=i.firmid(+)" +

			" " + "and h.firmid=k.firmid(+) " +

			"and h.firmid = a2.firmid(+) " + "and h.firmid = b2.firmid(+) " + "and h.firmid = c2.firmid(+) " + "and h.firmid = d2.firmid(+) "
					+ "and h.firmid = e2.firmid(+) " + "and h.firmid = OutMoney.firmid(+) " + "and h.firmid = InMoney.firmid(+) "
					+ "and h.firmid = FEE.firmid(+) " + "and h.firmid = a1.firmid(+) " + "and h.firmid = b1.firmid(+) "
					+ "and h.firmid = c1.firmid(+) " + "and h.firmid = d1.firmid(+) " + "and h.firmid = e1.firmid(+) " + "and h.bankid = j.bankid";

			Tool.log("sql:" + sql);
			System.out.println("sql:" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();

			Map<String, BankQSNetChild> map = new HashMap();
			while (rs.next()) {
				if ((BankQSNetChild) map.get(rs.getString("bankid")) != null) {
					BankQSNetChild value = (BankQSNetChild) map.get(rs.getString("bankid"));
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
					value.lastKY += rs.getDouble("lastKy");
					value.todayKY += rs.getDouble("todayKy");
					value.KYchange += rs.getDouble("kyChangeMoney");
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
					value.lastKY = rs.getDouble("lastKy");
					value.todayKY = rs.getDouble("todayKy");
					value.KYchange = rs.getDouble("kyChangeMoney");
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
			closeStatement(rs, state, conn);
		}
		return vec;
	}

	public Vector<RgstCapitalValue> getRgstCapitalValue(String filter, Connection conn) throws SQLException {
		Tool.log("===>>>取得签约流水记录列表   getRgstCapitalValue  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		RgstCapitalValue value = null;
		Vector<RgstCapitalValue> list = new Vector();
		try {
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
		} finally {
			closeStatement(rs, state, null);
		}
		return list;
	}

	public int modRgstCapitalValue(String bankid, String firmid, String account, Timestamp banktime, int status, long actionid, int type,
			Connection conn) throws SQLException {
		Tool.log("===>>>修改签约流水记录表和交易商银行对应关系表   modRgstCapitalValue  " + new java.util.Date());

		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "update F_B_RgstCapitalValue  set account='" + account + "',status='" + status + "' , banktime=sysdate where actionid='" + actionid
					+ "' ";
			Tool.log("modRgstCapitalValue1>>>>>>" + sql);

			state = conn.prepareStatement(sql);

			state.executeUpdate();
			if (status == 0) {
				Tool.log("银行处理成功>>>>");
				if (type == 1) {
					sql = " update f_b_firmidandaccount  set status='0' , isopen='1' ,  opentime=sysdate ,account='" + account + "' "
							+ "where firmid='" + firmid + "' and bankid='" + bankid + "'";
				} else if (type == 2) {
					sql = " delete from f_b_firmidandaccount  where firmid='D_" + firmid + "'" + " and bankid='D_" + bankid + "' ";
					Tool.log("modRgstCapitalValue2>>>>>>" + sql);
					state = conn.prepareStatement(sql);
					state.executeUpdate();
					sql = " update f_b_firmidandaccount  set firmID='D_'||firmID,bankID='D_'||bankID, deltime=sysdate  where firmid='" + firmid
							+ "' and bankid='" + bankid + "' ";
				}
				Tool.log("modRgstCapitalValue3>>>>>>" + sql);
				state = conn.prepareStatement(sql);
				state.executeUpdate();
			} else {
				Tool.log("银行处理失败>>>>");
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
		Tool.log("===>>>修改签约流水记录表和交易商银行对应关系表   modRgstCapitalValue  " + new java.util.Date());
		ResultSet rs = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		long id = -1L;
		try {
			sql = "select seq_F_B_RgstCapitalValue.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			sql = "insert into F_B_RgstCapitalValue (id,ACTIONID,FIRMID,ACCOUNT,BANKID,TYPE,CREATETIME,BANKTIME,STATUS,ACCOUNTNAME,CARDTYPE,CARD,NOTE)values(?,?,?,?,?,?,sysdate,null,?,?,?,?,?) ";
			Tool.log("addRgstCapitalValue>>>>>>" + sql);
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

	public Vector<ClientState> getClientState(String filter) throws SQLException, ClassNotFoundException {
		Vector<ClientState> states = new Vector();
		String sql = "";
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			sql = "select * from F_B_ACCOUNTSTATUS " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				ClientState s = new ClientState();
				s.setBankNo(rs.getString("BANK_ID"));
				s.setStrandsNo(rs.getString("EXCHANGE_CODE"));
				s.setTransAddressCode(rs.getString("AREA_CODE"));
				s.setTransDate(new java.sql.Date(Tool.strToDate(rs.getString("TRADE_DATE")).getTime()));
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
			closeStatement(rs, state, conn);
		}
		return states;
	}

	public int addClientState(ClientState state) throws SQLException, ClassNotFoundException {
		int i = 0;
		Connection conn = null;
		PreparedStatement st = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "INSERT INTO F_B_ACCOUNTSTATUS(BANK_ID,EXCHANGE_CODE,AREA_CODE,TRADE_DATE,ACCOUNT,FUNDS_ACCOUNT,FIRM_NAME,CCYCODE,FLAG,STATUS)  VALUES(?,?,?,?,?,?,?,?,?,?)";

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
			closeStatement(null, st, conn);
		}
		return i;
	}

	public List<TransferAccountsTransactionDetailed> getZZJYMX(String bankID, String[] firmIDs, java.util.Date tradeDate, Connection conn)
			throws SQLException {
		Tool.log("查询转账交易明细信息");
		List<TransferAccountsTransactionDetailed> result = new ArrayList();
		PreparedStatement state = null;
		ResultSet rs = null;
		String filter1 = "";
		if ((bankID != null) && (!"".equals(bankID))) {
			filter1 = filter1 + "  AND a.bankid='" + bankID + "'";
		}
		String filter2 = "";
		if (tradeDate != null) {
			filter2 = filter2 + " AND trunc(a.createtime) = to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
		}
		String filter = "";
		if (firmIDs != null) {
			String firms = "('aa'";
			for (int i = 0; i < firmIDs.length; i++) {
				firms = firms + ",'" + firmIDs[i] + "'";
			}
			firms = firms + ")";
			filter = filter + " and a.firmid in " + firms;
		}
		String sql = "SELECT a.createtime TransDateTime, a.funid BankSerialNumber, a.actionid LaunchSerialNumber, b.account BankAccount, a.firmid bondAcc, a.money money, (CASE a.type WHEN  0 THEN 1 WHEN 1 THEN 2 END) TransferDirection FROM F_B_capitalInfo a, F_B_FIRMIDANDACCOUNT b WHERE 1=1 "
				+

		filter2 + filter1 + filter + " AND a.status= 0 AND a.firmid = b.firmid AND a.type <> 2";
		System.out.println(sql);
		try {
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				TransferAccountsTransactionDetailed tatd = new TransferAccountsTransactionDetailed();
				tatd.setBankCode("");
				tatd.setMarketCode("");
				tatd.setTransAddressCode("");
				tatd.setTransDateTime(Tool.fmtDate(rs.getDate("TransDateTime")));
				tatd.setTransTime(Tool.fmtOnlyTime(rs.getDate("TransDateTime")));
				tatd.setBankSerialNumber(rs.getString("BankSerialNumber"));
				tatd.setLaunchSerialNumber(rs.getString("LaunchSerialNumber"));
				tatd.setBankAccount(rs.getString("BankAccount"));
				tatd.setBondAcc(rs.getString("bondAcc"));
				tatd.setCertificationName("");
				tatd.setTransferDirection(rs.getString("TransferDirection"));
				tatd.setMoneyType("001");
				tatd.setCashExCode("0");
				tatd.setMoney(rs.getDouble("money"));
				result.add(tatd);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public List<AccountStatusReconciliation> getKHZHZT(String bankID, String[] firmIDs, java.util.Date tradeDate, Connection conn)
			throws SQLException, ClassNotFoundException {
		List<AccountStatusReconciliation> result = new ArrayList();
		PreparedStatement state = null;
		ResultSet rs = null;
		String filter = "";
		if ((bankID != null) && (!"".equals(bankID))) {
			filter = filter + "  AND (b.bankid='" + bankID + "' or b.bankid='" + bankID + "_D' or b.bankid='D_" + bankID + "') AND isopen=1 ";
		}
		String sql = "";
		try {
			sql =

			"SELECT b.firmid bondAcc, b.account BankAccount, b.accountname certificationName, (CASE b.isopen WHEN  0 THEN 1 WHEN 1 THEN 0 END) CashExCode FROM  f_b_firmidandaccount b WHERE 1=1  AND (trunc(b.opentime) = to_date('"
					+ Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')" + " OR trunc(b.deltime) = to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd'))"
					+ filter;
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				AccountStatusReconciliation asr = new AccountStatusReconciliation();
				asr.setBankCode("");
				asr.setMarketCode("");
				asr.setTransAddressCode("");
				asr.setTransDateTime(Tool.fmtDate(tradeDate));
				asr.setBankAccount(rs.getString("BankAccount"));

				String firmid = rs.getString("bondAcc").trim();
				String status = "0";
				if ((firmid != null) && (firmid.indexOf("D") >= 0)) {
					status = "1";
					firmid = firmid.replaceAll("D_", "");
					firmid = firmid.replaceAll("_D", "");
				}
				asr.setBondAcc(firmid);

				asr.setCertificationName(rs.getString("certificationName"));
				asr.setMoneyType("001");
				asr.setCashExCode("0");

				asr.setStatus(status);
				result.add(asr);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public List<StorageMoneySettlementList> getCGKHZJJSMX(String bankID, java.util.Date tradeDate, Connection conn)
			throws SQLException, ClassNotFoundException {
		List<StorageMoneySettlementList> result = new ArrayList();
		PreparedStatement state = null;
		ResultSet rs = null;
		String filter = "";
		if ((bankID != null) && (!"".equals(bankID))) {
			filter = " and a.bankid='" + bankID.trim() + "'";
		}
		String sql = "";
		try {
			sql =

			"SELECT a.firmid bondAcc, a.accountname certificationName, b.value money, (CASE WHEN b.value >= 0 THEN 0 ELSE 1 END) TradeDifference FROM f_b_firmidandaccount a,( SELECT c.firmid,nvl(sum(d.fieldsign*c.value),0) VALUE FROM F_LEDGERFIELD d, F_CLIENTLEDGER c WHERE d.code = c.code and  c.code not in('Deposit','Fetch') and trunc(c.b_date) = to_date('"
					+ Tool.fmtDate(tradeDate) + "','yyyy-MM-dd') group by c.firmid" + ") b"
					+ " WHERE a.firmid = b.firmid and a.firmid not like '%D%' " + filter;
			System.out.println(sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				StorageMoneySettlementList smsl = new StorageMoneySettlementList();
				smsl.setBankCode("");
				smsl.setMarketCode("");
				smsl.setTransAddressCode("");
				smsl.setTransDateTime(Tool.fmtDate(tradeDate));
				smsl.setTaiZhangZhangHao("333");
				smsl.setBondAcc(rs.getString("bondAcc"));
				smsl.setCertificationName(rs.getString("certificationName"));
				smsl.setTradeDifference(rs.getString("TradeDifference"));
				smsl.setMoneyType("001");
				smsl.setCashExCode("0");
				smsl.setMoney(rs.getDouble("money"));
				result.add(smsl);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public List<StorageMoneyLedgerBalanceList> getCGKHZJTZYEMX(String bankID, java.util.Date tradeDate, Connection conn)
			throws SQLException, ClassNotFoundException {
		List<StorageMoneyLedgerBalanceList> result = new ArrayList();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		String filter = "";
		if (bankID != null) {
			filter = filter + " and bankid = '" + bankID + "'";
		}
		try {
			sql =

			" SELECT a.firmid bondAcc, a.accountname certificationName, b.todaybalance money, b.lastbalance lastMoney FROM f_b_firmidandaccount a,f_firmbalance b WHERE a.firmid = b.firmid AND a.isopen=1 and a.firmid not like '%D%' AND trunc(b.b_date) = to_date('"
					+ Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')" + filter;
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
				smlb.setLastMoney(rs.getDouble("lastMoney"));
				result.add(smlb);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public int interfaceLog(InterfaceLog log) throws SQLException, ClassNotFoundException {
		Tool.log("插入银行接口和银行通讯信息  interfaceLog log[" + log.toString() + "]");
		Connection conn = null;
		PreparedStatement state = null;
		int result = 0;
		String sql = "insert into F_B_INTERFACELOG (LOGID,BANKID,LAUNCHER,TYPE,FIRMID,ACCOUNT,BEGINMSG,ENDMSG,RESULT,CREATETIME) values (SEQ_F_B_INTERFACELOG.NEXTVAL,?,?,?,?,?,?,?,?,sysdate)";
		try {
			conn = getConnection();
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
			closeStatement(null, state, conn);
		}
		return result;
	}

	public Vector<InterfaceLog> interfaceLogList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("查询银行接口和银行通讯信息  interfaceLogList filter[" + filter + "]");
		Vector<InterfaceLog> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		InterfaceLog value = null;

		String sql = " select fbi.*,fbb.bankname from F_B_INTERFACELOG fbi inner join  F_B_BANKS fbb on fbi.bankid = fbb.bankid where 1=1 " + filter;
		try {
			conn = getConnection();

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
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public List<BankQSNetChild> getQSFirmDate(String bankID, String firmID, java.util.Date qdate) throws Exception {
		Tool.log("获取某日的清算信息  getQSChild 时间：" + Tool.fmtDate(new java.util.Date()));
		List<BankQSNetChild> vec = new ArrayList();
		Connection conn = null;

		conn = getConnection();
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
		if ((bankID != null) && (bankID.trim().length() > 0) && (!bankID.trim().equals("-1"))) {
			filter2 = filter2 + " and bankID='" + bankID + "' ";
		}
		if ((firmID != null) && (firmID.trim().length() > 0) && (!firmID.trim().equals("-1"))) {
			filter2 = filter2 + " and firmID='" + firmID + "' ";
		}
		try {
			java.util.Date yestody = getlastDate(qdate, conn);
			sql = "select h.firmid firmID ,nvl(a.value,0)+(case when nvl(k.value,0)>0 then nvl(k.value,0) else 0 end) AddTranAmount,nvl(b.value,0)+(case when nvl(k.value,0)<0 then nvl(-k.value,0) else 0 end) CutTranAmount,nvl(c.value,0) ProfitAmount,nvl(d.value,0) LossAmount,j.bankid bankid , j.bankname bankName ,nvl(Fee.value,0) Fee ,nvl(OutMoney.value, 0) OutMoney,nvl(InMoney.value, 0) InMoney,nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + nvl(d1.money, 0)+ nvl(e1.money, 0) todayQY,nvl(a2.todaybalance, 0) + nvl(b2.RuntimeMargin, 0) + nvl(b2.RuntimeFL, 0) + nvl(b2.RuntimeSettleMargin, 0) + nvl(c2.floatingloss, 0) + nvl(d2.money, 0)+ nvl(e2.money, 0) lastDayQY,nvl(a1.todaybalance, 0) todayKY,nvl(a2.todaybalance, 0) lastKY,nvl(a1.todaybalance, 0)-nvl(a2.todaybalance, 0)+nvl(OutMoney.value, 0)-nvl(InMoney.value, 0)+nvl(Fee.value,0) kyChangeMoney,nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + nvl(d1.money, 0)+ nvl(e1.money, 0) -(nvl(a2.todaybalance, 0) + nvl(b2.RuntimeMargin, 0) + nvl(b2.RuntimeFL, 0) + nvl(b2.RuntimeSettleMargin, 0) + nvl(c2.floatingloss, 0) + nvl(d2.money, 0)+ nvl(e2.money, 0)) +nvl(Fee.value,0) + nvl(OutMoney.value, 0) - nvl(InMoney.value, 0) QYChangeMoney from (select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+

			Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('Income','Income_Z','Income_V') " + filter + " group by fc.firmid) a, " +

			"(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('Payout','Payout_Z','Payout_V') " + filter + " group by fc.firmid) b, " +

			"(select fc.firmid,sum(nvl(fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('TradePL','SettlePL') and value>=0 " + filter
					+ " group by fc.firmid) c, " +

			"(select fc.firmid,sum(nvl(-fc.value,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') and fl.code in ('TradePL','SettlePL') and value<0 " + filter + " group by fc.firmid) d, "
					+

			"(select fc.firmid,sum(nvl(case when fl.code='SettleCompens' then -fc.value else fc.value end,0)) value from f_ledgerfield fl,f_clientledger fc where fl.code = fc.code and fc.b_date=to_date('"
					+ Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') and fl.code in ('BankFee','TradeFee','SettleFee','TradeFee_Z','TradeFee_V','SettleFee_Z','SettleCompens') "
					+ filter + " group by fc.firmid) e, " +

			"( " + "select nvl(fb.value,0)+nvl(fc.value,0)+nvl(fd.value,0)"
					+ "+nvl(fg.value,0) valued,nvl(fb1.value,0)+nvl(fc1.value,0)+nvl(fd1.value,0)" + "+nvl(fg1.value,0) valuez" + ", fe.firmid from "
					+ "(select nvl(RuntimeSettleMargin,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') " + filter + ") fb, " +

			"(select nvl(RuntimeFL,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') " + filter
					+ ") fc, " +

			"(select nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') " + filter + ") fd, " +

			"(select nvl(sum(case when code = 'Margin_V' then value when code = 'Margin_Z' then value when code = 'MarginBack_V' then -value when code = 'MarginBack_Z' then -value else 0 end), 0) value,firmid from f_clientledger where code in ('MarginBack_V', 'Margin_V','MarginBack_Z', 'Margin_Z') and to_char(b_date,'yyyy-MM-dd')<='"
					+ Tool.fmtDate(qdate) + "' " + filter + " group by firmid) fg, " +

			"(select nvl(RuntimeSettleMargin,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody) + "','yyyy-MM-dd') "
					+ filter + ") fb1, " +

			"(select nvl(RuntimeFL,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody) + "','yyyy-MM-dd') " + filter
					+ ") fc1, " +

			"(select nvl(RuntimeMargin,0)-nvl(RuntimeAssure,0) value,firmid from t_h_firm where cleardate=to_date('" + Tool.fmtDate(yestody)
					+ "','yyyy-MM-dd') " + filter + ") fd1, " +

			"(select nvl(sum(case when code = 'Margin_V' then value when code = 'Margin_Z' then value when code = 'MarginBack_V' then -value when code = 'MarginBack_Z' then -value else 0 end), 0) value,firmid from f_clientledger where code in ('MarginBack_V', 'Margin_V','MarginBack_Z', 'Margin_Z') and to_char(b_date,'yyyy-MM-dd')<='"
					+ Tool.fmtDate(yestody) + "' " + filter + " group by firmid) fg1, " +

			"(select firmid from f_b_firmidandaccount where 1=1 " + filter2 + ") fe " +

			"where fe.firmid=fb.firmid(+) and fe.firmid=fc.firmid(+) and fe.firmid=fd.firmid(+)"
					+ " and fe.firmid=fg.firmid(+) and fe.firmid=fb1.firmid(+) and fe.firmid=fc1.firmid(+) and fe.firmid=fd1.firmid(+)"
					+ " and fe.firmid=fg1.firmid(+) " + ") f, " +

			"(" + "select nvl(ia.value,0) valued,nvl(ib.value,0) valuez,ic.firmid from "
					+ "(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where trunc(cleardate)=to_date('" + Tool.fmtDate(qdate)
					+ "','yyyy-MM-dd') " + filter + " group by firmid) ia," +

			"(select sum(nvl(floatingloss,0)) value,firmid from t_h_firmholdsum where trunc(cleardate)=to_date('" + Tool.fmtDate(yestody)
					+ "','yyyy-MM-dd') " + filter + " group by firmid) ib," +

			"(select firmid from f_b_firmidandaccount where 1=1 " + filter2 + ") ic " +

			"where ic.firmid=ia.firmid(+) and ic.firmid=ib.firmid(+)" + ") i," +

			"(select nvl(sum(nvl(value,0)),0) value,firmid  from f_clientledger where code in ('OtherItem','OtherItem_Z','OtherItem_V') and trunc(B_Date)=to_date('"
					+ Tool.fmtDate(qdate) + "','yyyy-MM-dd') " + filter + " group by firmid) k, "
					+ "(select todaybalance value,firmid from f_firmbalance where b_date=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') "
					+ filter + ") g," +

			"(select account1,ACCOUNTNAME1,firmid,bankid from f_b_firmidandaccount where 1=1 " + filter2 + ") h ," +

			"(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb + ") a2,"
					+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
					+ filter_t_h_f + ") b2," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 "
					+ filter_t_h_f + " group by firmid) c2,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
					+ filter_z_v + " group by firmid) d2,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
					+ filter_z_v + " group by firmid) e2," +

			"(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Deposit' " + filter_Fee + " group by firmid "
					+ ") InMoney," +

			"(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Fetch' " + filter_Fee + " group by firmid "
					+ ") OutMoney," +

			"(select fc.firmid firmid,nvl(sum(nvl(fc.value,0)),0) value from f_clientledger fc where exists (select code from f_ledgerfield fl where code like '%Fee%' and fc.code=fl.code ) "
					+ Fee_filter + " group by firmid" + ") Fee," +

			"(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb_today + ") a1,"
					+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
					+ filter_t_h_f_today + ") b1," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 "
					+ filter_t_h_f_today + " group by firmid) c1,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
					+ filter_z_v_today + " group by firmid) d1,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
					+ filter_z_v_today + " group by firmid) e1 ," + "(select bankid,bankName from f_b_banks where validflag = 0 ) j " +

			" where h.firmid=a.firmid(+) " + "and h.firmid=b.firmid(+) " + "and h.firmid=c.firmid(+) " + "and h.firmid=d.firmid(+) "
					+ "and h.firmid=e.firmid(+) " + "and h.firmid=f.firmid(+) " + "and h.firmid=g.firmid(+) " + "and h.firmid=i.firmid(+)" + " "
					+ "and h.firmid=k.firmid(+) " +

			"and h.firmid = a2.firmid(+) " + "and h.firmid = b2.firmid(+) " + "and h.firmid = c2.firmid(+) " + "and h.firmid = d2.firmid(+) "
					+ "and h.firmid = e2.firmid(+) " + "and h.firmid = OutMoney.firmid(+) " + "and h.firmid = InMoney.firmid(+) "
					+ "and h.firmid = FEE.firmid(+) " + "and h.firmid = a1.firmid(+) " + "and h.firmid = b1.firmid(+) "
					+ "and h.firmid = c1.firmid(+) " + "and h.firmid = d1.firmid(+) " + "and h.firmid = e1.firmid(+) " + "and h.bankid = j.bankid";

			Tool.log("sql:" + sql);
			System.out.println("sql:" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
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
				value.lastKY = rs.getDouble("lastKY");
				value.todayKY = rs.getDouble("todayKY");
				value.KYchange = rs.getDouble("kyChangeMoney");
				vec.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return vec;
	}

	public Vector<BankTransferValue> getBankTransferList(String filter) throws SQLException, ClassNotFoundException {
		System.out.println("===>>>获得待审核银行间资金划转流水列表   getBankTransferList " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		BankTransferValue value = null;
		Vector<BankTransferValue> list = new Vector();
		try {
			conn = getConnection();
			sql = "select bt.id,bt.actionid,bt.funid,bt.paybankid,bt.recbankid,bt.payacc,bt.recacc,bt.money,bt.createtime,bt.updatetime,bt.status,bt.note,bt.recFirmId,bt.capitalActionId,bt.type,bt.info,b1.bankname paybankname,b2.bankname recbankname,a1.info payaccname,a2.info recaccname from f_b_banktransfer bt,f_b_banks b1,f_b_banks b2,f_b_account a1,f_b_account a2 where bt.paybankid = b1.bankid and bt.recbankid = b2.bankid and bt.payacc = a1.code and bt.recacc = a2.code "
					+

			filter;

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

	public Vector<BankTransferValue> getBankTransferList(String filter, Connection conn) throws SQLException, ClassNotFoundException {
		System.out.println("===>>>获得待审核银行间资金划转流水列表   getBankTransferList " + new java.util.Date());

		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		BankTransferValue value = null;
		Vector<BankTransferValue> list = new Vector();
		try {
			sql =

			"select bt.id,bt.actionid,bt.funid,bt.paybankid,bt.recbankid,bt.payacc,bt.recacc,bt.money,bt.createtime,bt.updatetime,bt.status,bt.note,bt.recFirmId,bt.capitalActionId,bt.type,bt.info,b1.bankname paybankname,b2.bankname recbankname,a1.info payaccname,a2.info recaccname from f_b_banktransfer bt,f_b_banks b1,f_b_banks b2,f_b_account a1,f_b_account a2 where bt.paybankid = b1.bankid and bt.recbankid = b2.bankid and bt.payacc = a1.code and bt.recacc = a2.code "
					+ filter;

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

	public long modBankTransfer(long id, int status, Connection conn) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>修改银行间资金划转流水记录状态   modBankTransfer  " + new java.util.Date());
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

	public Vector<Account> getAccList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>获得银行端科目列表   getAccList " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		Account value = null;
		Vector<Account> list = new Vector();
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
		Tool.log("===>>>增加银行间资金划转流水记录   addBankTransfer  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1L;
		try {
			val.actionId = getActionID(conn);
			sql = "select seq_F_B_bankTransfer.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			sql = "insert into F_B_bankTransfer(id, actionId, funId, payBankId, recBankId, payAcc, recAcc, money, createTime, updateTime, status, note ,recFirmId,capitalActionId,type,info) values(?, ?, ?, ?, ?, ?, ?, ?,sysdate,sysdate, ?, ?, ?, ?, ?, ?)";

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
			id = -1L;
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return id;
	}

	public long addBankTransfer(BankTransferValue val) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>增加银行间资金划转流水记录   addBankTransfer  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1L;

		Connection conn = null;
		long actionId;
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
			sql = "insert into F_B_bankTransfer(id, actionId, funId, payBankId, recBankId, payAcc, recAcc, money, createTime, updateTime, status, note ,recFirmId,capitalActionId,type,info) values(?, ?, ?, ?, ?, ?, ?, ?,sysdate,sysdate, ?, ?, ?, ?, ?, ?)";

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
			id = -1L;
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return actionId;
	}

	public TransferBank getTransferBank(String id) throws SQLException, ClassNotFoundException {
		TransferBank transferBank = new TransferBank();
		String sql = "SELECT * FROM F_B_BANKACCOUNT WHERE　BANKID='" + id + "'";
		System.out.println("查询转账银行帐户SQL" + sql);
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
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
			closeStatement(null, state, conn);
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

	public Vector<CorrespondValue> getMarketAcount(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>取得市场账户列表   getMarketAcount  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CorrespondValue value = null;
		Vector<CorrespondValue> list = new Vector();
		try {
			conn = getConnection();

			sql = "select * from F_B_MARKETACOUNT where 1 = 1 " + filter;

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new CorrespondValue();
				value.account = rs.getString("account");
				value.notes = rs.getString("notes");
				value.accountName = rs.getString("accountName");
				value.accountBank = rs.getString("accountBank");
				value.bankName = rs.getString("accountBankName");
				value.isCrossLine = rs.getString("isCrossLine");
				value.accountBankNum = rs.getString("accountBankNum");
				value.type = rs.getString("type");

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

	public int modCapitalInfoStatus_ceb_f623(String funID, int status, Timestamp bankTime, Connection conn) throws SQLException {
		Tool.log("===>>>修改资金流水记录状态   modCapitalInfoStatus  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			int n = 1;

			sql = "update F_B_capitalInfo set status=?,FUNID=?||id,bankTime=?,funid2=?||id where FUNID=?";

			state = conn.prepareStatement(sql);
			state.setInt(n++, status);
			state.setString(n++, funID.trim() + "|");
			state.setTimestamp(n++, bankTime);

			state.setString(n++, funID + "|");

			state.setString(n++, funID.trim());
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

	public int addTransfer(CEB_param param) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>添加转账流水   addTransfer  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			conn = getConnection();

			sql = "insert into F_B_TRANSFER(BANKID,FLAG,BUSISERIALNUM,AMOUNT,TRADETIME,ATONCE,ISCROSSLINE,TARGETACCOUNTBANKNUM,TARGETACCOUNTBANK,TARGETACCOUNTBANKNAME,TARGETACCOUNTNAME,TARGETACCOUNT,SOURCEACCOUNTNAME,SOURCEACCOUNT,FCSSERIALNUM,RESULTCODE) values('"
					+ param.bankId + "','" + param.flag + "','" + param.busiSerialNum + "'," + Double.parseDouble(param.amount) + ",'"
					+ param.tradeTime + "','" + param.atOnce + "','" + param.isCrossLine + "','" + param.targetAccountBankNum + "','"
					+ param.targetAccountBank + "','" + param.targetAccountBankName + "','" + param.targetAccountName + "','" + param.targetAccount
					+ "','" + param.sourceAccountName + "','" + param.sourceAccount + "','" + param.fcsSerialNum + "','" + param.resultCode + "')";

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

	public Vector<FirmBalance_CEB> getFirmBalance_CEB(String bankID, java.util.Date qdate) throws SQLException, Exception {
		Vector<FirmBalance_CEB> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			String filter_Fee = " and b_date=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') ";
			String Bank_filter = "";
			if ((bankID != null) && (bankID.trim().length() > 0)) {
				Bank_filter = Bank_filter + " and bankid='" + bankID.trim() + "' ";
			}
			String Fee_filter = filter_Fee;

			String filter = " ";
			String filter_f_fb = " ";
			String filter_z_v = " ";
			String filter_t_h_f = " ";

			String filter_f_fb_today = " ";
			String filter_z_v_today = " ";
			String filter_t_h_f_today = " ";
			if ((bankID != null) && (!"".equals(bankID.trim()))) {
				filter = filter + " and i.bankId='" + bankID.trim() + "' and i.firmid not like '%D'";
			}
			String firmid = null;
			if ((firmid != null) && (!"".equals(firmid.trim()))) {
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
			sql =

			"select i.firmid , i.bankid, i.account, i.account1, i.accountname, i.cardtype, i.card, nvl(a1.todaybalance, 0) useableBalance, nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + nvl(d1.money, 0)+ nvl(e1.money, 0) money ,nvl(Fee.value,0) Fee ,nvl(OutMoney.value, 0) ,nvl(InMoney.value, 0) ,nvl(a1.todaybalance, 0) + nvl(b1.RuntimeMargin, 0) + nvl(b1.RuntimeFL, 0) + nvl(b1.RuntimeSettleMargin, 0) + nvl(c1.floatingloss, 0) + nvl(d1.money, 0)+ nvl(e1.money, 0) -nvl(a.todaybalance, 0) - nvl(b.RuntimeMargin, 0) - nvl(b.RuntimeFL, 0) - nvl(b.RuntimeSettleMargin, 0) - nvl(c.floatingloss, 0) - nvl(d.money, 0)- nvl(e.money, 0) +nvl(Fee.value,0) + nvl(OutMoney.value, 0) - nvl(InMoney.value, 0) QYChangeMoney from (select firmid, todaybalance from f_firmbalance where 1 = 1 "
					+ filter_f_fb + ") a,"
					+ "(select firmid,(RuntimeMargin - RuntimeAssure) RuntimeMargin, RuntimeFL, RuntimeSettleMargin from t_h_firm where 1 = 1 "
					+ filter_t_h_f + ") b," + "(select firmid,nvl(sum(floatingloss), 0) floatingloss from t_h_firmholdsum where 1 = 1 " + filter_t_h_f
					+ " group by firmid) c,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_V' then value when code = 'MarginBack_V' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_V', 'Margin_V') "
					+ filter_z_v + " group by firmid) d,"
					+ "(select firmid,nvl(sum(case when code = 'Margin_Z' then value when code = 'MarginBack_Z' then -value else 0 end), 0) money from f_clientledger where code in ('MarginBack_Z', 'Margin_Z') "
					+ filter_z_v + " group by firmid) e," + "(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Deposit' "
					+ filter_Fee + " group by firmid " + ") InMoney,"
					+ "(select firmid,nvl(sum(value),0) value from f_clientledger where 1=1 and code='Fetch' " + filter_Fee + " group by firmid "
					+ ") OutMoney," + "(select firmid,bankid,accountname,account,account1,isopen,cardType, card from f_b_Firmidandaccount) i ,"
					+ "(select fc.firmid firmid,nvl(sum(nvl(fc.value,0)),0) value from f_clientledger fc where exists (select code from f_ledgerfield fl where code like '%Fee%' and fc.code=fl.code ) "
					+ Fee_filter + " group by firmid" + ") Fee," + "(select firmid, todaybalance from f_firmbalance where 1 = 1 " + filter_f_fb_today
					+ ") a1,"
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
			System.out.println("光大清算sql:" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmBalance_CEB fb = new FirmBalance_CEB();
				fb.firmID = rs.getString("firmid");
				fb.bankID = rs.getString("bankid");
				fb.account = rs.getString("account");
				fb.account1 = rs.getString("account1");
				fb.accountName = rs.getString("accountName");
				fb.cardType = rs.getString("cardType");
				fb.card = rs.getString("card");
				fb.FeeMoney = rs.getDouble("Fee");
				fb.QYChangeMoney = rs.getDouble("QYChangeMoney");
				fb.QYMoney = rs.getDouble("money");
				fb.useableBalance = rs.getDouble("useableBalance");
				fb.date = qdate;
				result.add(fb);
			}
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public int addFCS_10(FCS_10_Result result) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>添加总账对账结果   addFCS_10  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result1 = 0;
		try {
			conn = getConnection();
			sql = "insert into f_b_fcs_10(busiNum,busiName,currency,acct,amount,memBalance,status,busiDate) values('" + result.busiNum + "','"
					+ result.busiName + "','" + result.currency + "','" + result.acct + "','" + result.amount + "','" + result.memBalance + "','"
					+ result.status + "','" + result.busiDate + "')";
			System.out.println("sql10=" + sql);
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result1 = -1;
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result1;
	}

	public int addFCS_11(FCS_11_Result result) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>添加分户账结果   addFCS_11  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result1 = 0;
		try {
			conn = getConnection();

			sql = "insert into f_b_fcs_11(busiNum,memNum,currency,acct,balance,memBalance,status,busiDate) values('" + result.busiNum + "','"
					+ result.memNum + "','" + result.currency + "','" + result.acct + "','" + result.balance + "','" + result.memBalance + "','"
					+ result.status + "','" + result.busiDate + "')";
			System.out.println("sql11=" + sql);
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result1 = -1;
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result1;
	}

	public int addFCS_13(FCS_13_Result result) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>添加交易明细对账结果   addFCS_13  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result1 = 0;
		try {
			conn = getConnection();

			sql = "insert into f_b_fcs_13(busiNum,tradeTime,busiSerialNum,fcsSerialNum,orderNum,fromAccount,otherNum,amount,fee,otherFee,currency,busiDate,checkDate,adjustStatus,orderStatus,errorDescription ) values('"
					+ result.busiNum + "','" + result.tradeTime + "','" + result.busiSerialNum + "','" + result.fcsSerialNum + "','" + result.orderNum
					+ "','" + result.fromAccount + "','" + result.otherNum + "','" + result.amount + "','" + result.fee + "','" + result.otherFee
					+ "','" + result.currency + "','" + result.busiDate + "','" + result.checkDate + "','" + result.adjustStatus + "','"
					+ result.orderStatus + "','" + result.errorDescription + "')";
			System.out.println("sql13=" + sql);
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result1 = -1;
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result1;
	}

	public int addFCS_99(FCS_99 result) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>添加日终总结果   addFCS_99  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result1 = 0;
		try {
			conn = getConnection();

			sql = "insert into f_b_fcs_99(tradeTime,busiNum,batchNum,fileName,rspCode,errorDescription) values('" + result.tradeTime + "','"
					+ result.busiNum + "','" + result.batchNum + "','" + result.fileName + "','" + result.rspCode + "','" + result.errorDescription
					+ "')";
			System.out.println("sql99=" + sql);
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result1 = -1;
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result1;
	}

	public int modCapitalInfoStatus_ceb(long id, String funID, int status, int type, Timestamp bankTime, Connection conn) throws SQLException {
		Tool.log("===>>>修改资金流水记录状态   modCapitalInfoStatus  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			String funidf = "";
			int n = 1;
			if ((funID == null) || (funID.trim().length() <= 0)) {
				funID = "";
			} else {
				funidf = ",funid2=?";
			}
			sql = "update F_B_capitalInfo set status=?,FUNID=?,type=?,bankTime=?" + funidf + " where id=?";

			state = conn.prepareStatement(sql);
			state.setInt(n++, status);
			state.setString(n++, funID.trim());
			state.setInt(n++, type);
			state.setTimestamp(n++, bankTime);
			if ((funidf != null) && (funidf.trim().length() > 0)) {
				state.setString(n++, funID + "|" + id);
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

	public Vector<CorrespondValue> getOpenBankList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>查询支付行号列表   getOpenBankList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CorrespondValue value = null;
		Vector<CorrespondValue> list = new Vector();
		try {
			conn = getConnection();
			sql = "select * from (select rownum no,t.* from pxdzf t where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new CorrespondValue();
				value.OpenBankCode = rs.getString("FQHHAO");
				value.bankName = rs.getString("JIGOMC");

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

	public int delMarketAcount(String account) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>删除市场账号   delMarketAcount  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;

		int result = 0;
		try {
			conn = getConnection();
			String sql = "delete from f_b_marketacount where account='" + account + "'";
			state = conn.prepareStatement(sql);
			state.executeUpdate();
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	public Vector<CEB_param> getTransferList(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>转账流水查询   getTransferList  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		CEB_param value = null;
		Vector<CEB_param> list = new Vector();
		try {
			conn = getConnection();
			sql = "select * from F_B_TRANSFER t where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new CEB_param();
				value.fcsSerialNum = rs.getString("fcsSerialNum");
				value.busiSerialNum = rs.getString("busiSerialNum");
				value.amount = rs.getString("amount");
				value.flag = rs.getString("flag");
				value.tradeTime = rs.getString("tradeTime");
				value.sourceAccountName = rs.getString("sourceAccountName");
				value.sourceAccount = rs.getString("sourceAccount");
				value.targetAccountName = rs.getString("targetAccountName");
				value.targetAccount = rs.getString("targetAccount");

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

	public Vector<FCS_10_Result> getFCS_10_List(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>总账对账结果查询   getFCS_10_List  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FCS_10_Result value = null;
		Vector<FCS_10_Result> list = new Vector();
		try {
			conn = getConnection();
			sql = "select * from f_b_fcs_10 t where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new FCS_10_Result();
				value.busiNum = rs.getString("busiNum");
				value.busiName = rs.getString("busiName");
				value.currency = rs.getString("currency");
				value.acct = rs.getString("acct");
				value.amount = rs.getString("amount");
				value.memBalance = rs.getString("memBalance");
				value.status = rs.getString("status");
				value.busiDate = rs.getString("busiDate");

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

	public Vector<FCS_11_Result> getFCS_11_List(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>分户账对账结果查询   getFCS_11_List  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FCS_11_Result value = null;
		Vector<FCS_11_Result> list = new Vector();
		try {
			conn = getConnection();
			sql = "select * from f_b_fcs_11 t where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new FCS_11_Result();
				value.busiNum = rs.getString("busiNum");
				value.memNum = rs.getString("memNum");
				value.currency = rs.getString("currency");
				value.acct = rs.getString("acct");
				value.balance = rs.getString("balance");
				value.memBalance = rs.getString("memBalance");
				value.status = rs.getString("status");
				value.busiDate = rs.getString("busiDate");

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

	public Vector<FCS_13_Result> getFCS_13_List(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>交易明细对账结果查询   getFCS_13_List  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FCS_13_Result value = null;
		Vector<FCS_13_Result> list = new Vector();
		try {
			conn = getConnection();
			sql = "select * from f_b_fcs_13 t where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new FCS_13_Result();
				value.busiNum = rs.getString("busiNum");
				value.tradeTime = rs.getString("tradeTime");
				value.busiSerialNum = rs.getString("busiSerialNum");
				value.fcsSerialNum = rs.getString("fcsSerialNum");
				value.orderNum = rs.getString("orderNum");
				value.fromAccount = rs.getString("fromAccount");
				value.otherNum = rs.getString("otherNum");
				value.amount = rs.getString("amount");
				value.fee = rs.getString("fee");
				value.otherFee = rs.getString("otherFee");
				value.currency = rs.getString("currency");
				value.busiDate = rs.getString("busiDate");
				value.checkDate = rs.getString("checkDate");
				value.adjustStatus = rs.getString("adjustStatus");
				value.orderStatus = rs.getString("orderStatus");
				value.errorDescription = rs.getString("errorDescription");

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

	public Vector<FCS_99> getFCS_99_List(String filter) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>日终总结果查询   getFCS_99_List  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		FCS_99 value = null;
		Vector<FCS_99> list = new Vector();
		try {
			conn = getConnection();
			sql = "select * from f_b_fcs_99 t where 1=1 " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new FCS_99();
				value.tradeTime = rs.getString("tradeTime");
				value.busiNum = rs.getString("busiNum");
				value.batchNum = rs.getString("batchNum");
				value.fileName = rs.getString("fileName");
				value.rspCode = rs.getString("rspCode");
				value.errorDescription = rs.getString("errorDescription");

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

	public int addMarketAcount(CorrespondValue val) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>添加市场账号   addMarketAcount  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			conn = getConnection();
			sql = "insert into f_b_marketacount (ACCOUNT, ACCOUNTNAME, ACCOUNTBANK, ACCOUNTBANKNAME, ISCROSSLINE, TYPE, ACCOUNTBANKNUM, NOTES)values ('"
					+ val.account + "','" + val.accountName + "','" + val.accountBank + "','" + val.bankName + "','" + val.isCrossLine + "','"
					+ val.type + "','" + val.accountBankNum + "','" + val.notes + "')";
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

	public int modMarketAcount(CorrespondValue value) throws SQLException, ClassNotFoundException {
		Tool.log("===>>>修改市场账号   modMarketAcount  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement state = null;

		int result = 0;
		try {
			conn = getConnection();
			String sql = "update F_B_MARKETACOUNT set account = '" + value.account + "'," + " notes = '" + value.notes + "'," + " accountName = '"
					+ value.accountName + "'," + " accountBank = '" + value.accountBank + "'," + " accountbankname = '" + value.bankName + "',"
					+ " isCrossLine = '" + value.isCrossLine + "'," + " accountBankNum = '" + value.accountBankNum + "'," + " type = '" + value.type
					+ "' where account = '" + value.account + "'";
			state = conn.prepareStatement(sql);

			state.executeUpdate();
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	public Vector<CityValue> getCityNames(String filter) throws SQLException, Exception {
		Tool.log("===>>>取得开户行城市对照表 信息  " + new java.util.Date());
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = null;
		CityValue city = null;
		Vector<CityValue> citys = new Vector();
		try {
			conn = getConnection();
			sql = "select * from F_B_CITY " + filter;
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				city = new CityValue();
				city.setCityID(rs.getString(1));
				city.setCityName(rs.getString(2));
				city.setCityCode(rs.getString(3));
				city.setParentID(rs.getString(4));
				citys.add(city);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeStatement(rs, stmt, conn);
		}
		return citys;
	}

	public int modBankQS(BankQSVO bq, Connection conn) throws SQLException {
		Tool.log("修改清算日期表");
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "update F_B_BankQSDate set TRADESTATUS=? where BANKID=? and TRADEDATE=?";
			state = conn.prepareStatement(sql);
			state.setInt(1, bq.tradeStatus);

			state.setString(2, bq.bankID);
			state.setDate(3, new java.sql.Date(bq.tradeDate.getTime()));
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		System.out.println("修改清算日期表的结果：" + result);
		return result;
	}

	public Vector<BankQSVO> getBankQSDate(String filter) throws SQLException {
		Vector<BankQSVO> list = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = getConnection();
			sql = "select * from f_b_bankqsdate " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				BankQSVO vo = new BankQSVO();
				vo.bankID = rs.getString("bankid");
				vo.tradeDate = rs.getDate("tradedate");
				vo.tradeType = rs.getInt("tradetype");
				vo.tradeStatus = rs.getInt("tradestatus");
				vo.note = rs.getString("note");
				vo.createDate = rs.getDate("createdate");
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStatement(null, state, conn);
		}
		return list;
	}

	public java.util.Date getMaxBankQSSuccessDate(String bankID, java.util.Date date, Connection conn) throws SQLException {
		java.util.Date result = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select max(tradeDate) tradeDate from F_B_BankQSDATE where bankid='" + bankID + "' and tradestatus=0 and trunc(tradeDate)<to_date('"
					+ Tool.fmtDate(date) + "','yyyy-MM-dd')";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if (rs.next()) {
				result = rs.getDate("tradeDate");
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public double sumAmount(String filter, Connection conn) throws SQLException {
		Tool.log("===>>>sumAmount  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		double result = 0.0D;
		try {
			sql = "select nvl(sum(amount),0) amount from f_b_firmidandaccount " + filter;

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

	public AbcInfoValue getAbcInfo(String firmID, long orderNo, int type, Connection conn) throws SQLException {
		Tool.log("===>>>取得农行交易相关信息   getBank  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		AbcInfoValue value = null;
		try {
			sql = "select * from F_B_AbcInfo where firmID='" + firmID + "' and orderno='" + orderNo + "' and type='" + type + "'";

			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new AbcInfoValue();
				value.actionID = rs.getLong("actionID");
				value.signInfo = rs.getString("signInfo");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return value;
	}

	public void addAbcInfo(AbcInfoValue val, Connection conn) throws SQLException {
		Tool.log("===>>>增加农行交易相关信息  " + new java.util.Date());

		PreparedStatement state = null;
		try {
			String sql = " insert into F_B_AbcInfo values(?, ?, ?, ?, ?, ?,sysdate)";

			state = conn.prepareStatement(sql);
			state.setString(1, val.firmID);
			state.setString(2, val.account1);
			state.setString(3, val.orderNo);
			state.setLong(4, val.actionID);
			state.setInt(5, val.type);

			StringReader sr = new StringReader(val.signInfo);
			state.setCharacterStream(6, sr, val.signInfo.length());
			state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
	}

	public FirmBalance getFirmBala(String firmid, java.util.Date date, Connection conn) {
		FirmBalance fb = new FirmBalance();
		java.util.Date lastDate = null;
		try {
			lastDate = getlastDate(date, conn);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "select nvl(t0.todaybalance,0) lastBalance,nvl(t1.inmoneySum,0) inSum,nvl(t2.outmoneySum,0) outSum from (select todaybalance,firmid from f_firmbalance where b_date=to_date('"
				+ Tool.fmtDate(lastDate) + "','yyyy-MM-dd')) t0,"
				+ "(select sum(money) inmoneySum,firmid from f_b_capitalinfo where status=0 and type=0 and to_char(bankTime,'yyyy-MM-dd')='"
				+ Tool.fmtDate(date) + "'  group by firmid) t1,"
				+ "(select sum(money) outmoneySum,firmid from f_b_capitalinfo where status=0 and type=1 and to_char(bankTime,'yyyy-MM-dd')='"
				+ Tool.fmtDate(date) + "'  group by firmid) t2," + "(select firmid from f_b_firmidandaccount  where isopen=1 and firmid='" + firmid
				+ "') t3 " + "where t3.firmid=t0.firmid(+) and t3.firmid = t1.firmid(+) and t3.firmid = t2.firmid(+) ";
		Tool.log("查询SQL：" + sql);
		try {
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				fb.firmID = firmid;
				fb.yesQYMoney = rs.getDouble("lastBalance");
				fb.inMoneySum = rs.getDouble("inSum");
				fb.outMoneySum = rs.getDouble("outSum");
			}
		} catch (Exception localException) {
		} finally {
			closeStatement(rs, state, null);
		}
		return fb;
	}

	public Vector<SystemMessage> getSystemMessages(String filter) {
		Tool.log("====>查询交易系统信息");
		Vector<SystemMessage> vec = new Vector();
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "select * from f_b_system " + filter;

		Tool.log("====>查询语句[" + sql + "]");
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				SystemMessage sm = new SystemMessage();
				sm.systemID = rs.getString("systemID");
				sm.systemName = rs.getString("systemName");
				sm.rmiURL = rs.getString("RMIURL");
				sm.startTime = rs.getString("tradeStartTime");
				sm.endTime = rs.getString("tradeEndTime");
				vec.add(sm);
			}
		} catch (Exception e) {
			Tool.log("查询出现异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return vec;
	}

	public long modSystemMessage(SystemMessage sysMsg) {
		Tool.log("====>修改交易系统信息");
		long result = 0L;
		PreparedStatement state = null;
		Connection conn = null;
		try {
			conn = getConnection();
			String sql = "update F_B_system set systemName=? where systemID=?";
			state = conn.prepareStatement(sql);
			state.setString(1, sysMsg.systemName);
			state.setString(2, sysMsg.systemID);
			result = state.executeUpdate();
		} catch (Exception e) {
			Tool.log("修改信息出现异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	public long modSystemMessage(String systemID, String flag, String key) {
		Tool.log("====>修改交易系统信息");
		long result = 0L;
		PreparedStatement state = null;
		Connection conn = null;
		try {
			conn = getConnection();
			String sql = "";
			if ("Y".equals(flag)) {
				sql = "update F_B_system set tradeStatus=?,loginTime=sysdate,key=? where systemID=?";
				state = conn.prepareStatement(sql);
				state.setString(1, flag);
				state.setString(2, key);
				state.setString(3, systemID);
			} else {
				sql = "update F_B_system set tradeStatus=?,quitTime=sysdate where systemID=?";
				state = conn.prepareStatement(sql);
				state.setString(1, flag);
				state.setString(2, systemID);
			}
			result = state.executeUpdate();
		} catch (Exception e) {
			Tool.log("修改信息出现异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	public Vector<FirmID2SysFirmID> getFirmID2SysFirmID(String filter) {
		Tool.log("====>查询平台交易商编号和子系统交易商编号对应关系");
		Vector<FirmID2SysFirmID> vec = new Vector();
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "select * from f_b_firmid_sysFirmID " + filter;

		Tool.log("====>查询语句[" + sql + "]");
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmID2SysFirmID f2f = new FirmID2SysFirmID();
				f2f.firmID = rs.getString("firmID");
				f2f.sysFirmID = rs.getString("sysFirmID");
				f2f.systemID = rs.getString("systemID");
				f2f.bankID = rs.getString("bankID");
				f2f.defaultSystem = rs.getString("defaultSystem");
				vec.add(f2f);
			}
		} catch (Exception e) {
			Tool.log("查询出现异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return vec;
	}

	public Vector<FirmID2SysFirmID> getFirmID2SysFirmID(String filter, Connection conn) {
		Tool.log("====>查询平台交易商编号和子系统交易商编号对应关系");
		Vector<FirmID2SysFirmID> vec = new Vector();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "select * from f_b_firmid_sysFirmID " + filter;

		Tool.log("====>查询语句[" + sql + "]");
		try {
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmID2SysFirmID f2f = new FirmID2SysFirmID();
				f2f.firmID = rs.getString("firmID");
				f2f.sysFirmID = rs.getString("sysFirmID");
				f2f.systemID = rs.getString("systemID");
				f2f.bankID = rs.getString("bankID");
				f2f.defaultSystem = rs.getString("defaultSystem");
				vec.add(f2f);
			}
		} catch (Exception e) {
			Tool.log("查询出现异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, null);
		}
		return vec;
	}

	public long addFirmID2SysFirmID(FirmID2SysFirmID f2f) throws SQLException {
		Tool.log("===>>>增加子系统交易商编号和平台交易商代码的对应关系");
		long result = 0L;
		Connection conn = null;
		try {
			conn = getConnection();
			result = addFirmID2SysFirmID(f2f, conn);
		} catch (Exception e) {
			Tool.log("插入对应关系异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(null, null, conn);
		}
		return result;
	}

	public int addFirmMapping(FirmID2SysFirmID f2f, Connection conn) throws SQLException {
		PreparedStatement state = null;
		int result = 0;
		try {
			String sql = "insert into P_MAPPINGFIRM (PLATFORMID,FIRMID,MODULEID) values (?,?,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, f2f.firmID);
			state.setString(2, f2f.sysFirmID);
			state.setInt(3, Integer.parseInt(f2f.systemID));
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public long addFirmID2SysFirmID(FirmID2SysFirmID f2f, Connection conn) throws SQLException {
		Tool.log("===>>>增加子系统交易商编号和平台交易商代码的对应关系");
		long result = 0L;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "insert into f_b_firmid_sysFirmID(FIRMID, SYSFIRMID, SYSTEMID, BANKID, DEFAULTSYSTEM) values (?, ?, ?, ?, ?)";
			state = conn.prepareStatement(sql);
			state.setString(1, f2f.firmID);
			state.setString(2, f2f.sysFirmID);
			state.setString(3, f2f.systemID);
			state.setString(4, f2f.bankID);
			state.setString(5, f2f.defaultSystem);
			result = state.executeUpdate();
		} catch (Exception e) {
			Tool.log("插入对应关系异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public long addFirmID2SysFirmIDHis(FirmID2SysFirmID f2f, Connection conn) throws SQLException {
		Tool.log("===>>>增加子系统交易商编号和平台交易商代码的历史对应关系");
		long result = 0L;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "insert into f_b_firmid_sysFirmID_His(FIRMID, SYSFIRMID, SYSTEMID, BANKID, DEFAULTSYSTEM, NOTE) values (?, ?, ?, ?, ?, ?)";
			state = conn.prepareStatement(sql);
			state.setString(1, f2f.firmID);
			state.setString(2, f2f.sysFirmID);
			state.setString(3, f2f.systemID);
			state.setString(4, f2f.bankID);
			state.setString(5, f2f.defaultSystem);
			state.setString(6, f2f.note);
			result = state.executeUpdate();
		} catch (Exception e) {
			Tool.log("插入对应关系异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public long delFirmID2SysFirmID(FirmID2SysFirmID f2f) throws SQLException {
		Tool.log("===>>>删除子系统交易商编号和平台交易商代码的对应关系");
		long result = 0L;
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = "delete from f_b_firmid_sysFirmID where systemID=? and sysFirmID=? and bankID=?";
			state = conn.prepareStatement(sql);
			state.setString(1, f2f.systemID);
			state.setString(2, f2f.sysFirmID);
			state.setString(3, f2f.bankID);
			result = state.executeUpdate();
		} catch (Exception e) {
			Tool.log("删除对应关系异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public long delFirmID2SysFirmID(FirmID2SysFirmID f2f, Connection conn) throws SQLException {
		Tool.log("===>>>删除子系统交易商编号和平台交易商代码的对应关系");
		long result = 0L;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "delete from f_b_firmid_sysFirmID where systemID=? and sysFirmID=? and bankID=?";
			state = conn.prepareStatement(sql);
			state.setString(1, f2f.systemID);
			state.setString(2, f2f.sysFirmID);
			state.setString(3, f2f.bankID);
			result = state.executeUpdate();
		} catch (Exception e) {
			Tool.log("删除对应关系异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public long modFirmID2SysFirmID(FirmID2SysFirmID f2f) throws SQLException {
		Tool.log("===>>>修改子系统交易商编号和平台交易商代码的默认系统");
		long result = 0L;
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = "update f_b_firmid_sysFirmID set defaultSystem=? where firmID=? systemID=? and sysFirmID=? and bankID=?";
			state = conn.prepareStatement(sql);
			state.setString(1, f2f.defaultSystem);
			state.setString(2, f2f.firmID);
			state.setString(3, f2f.systemID);
			state.setString(4, f2f.sysFirmID);
			state.setString(5, f2f.bankID);
			result = state.executeUpdate();
		} catch (Exception e) {
			Tool.log("修改对应关系异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public long modFirmID2SysFirmID2(FirmID2SysFirmID f2fOld, FirmID2SysFirmID f2fNew, Connection conn) throws SQLException {
		Tool.log("===>>>修改子系统交易商编号和平台交易商代码的默认系统");
		long result = 0L;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "update f_b_firmid_sysFirmID set bankID=?,firmID=?,sysfirmid=?,systemid=?,defaultsystem=? where firmID=?  and bankID=?";
			state = conn.prepareStatement(sql);
			state.setString(1, f2fNew.bankID);
			state.setString(2, f2fNew.firmID);
			state.setString(3, f2fNew.sysFirmID);
			state.setString(4, f2fNew.systemID);
			state.setString(5, f2fNew.defaultSystem);
			state.setString(6, f2fOld.firmID);
			state.setString(7, f2fOld.bankID);
			result = state.executeUpdate();
		} catch (Exception e) {
			Tool.log("修改对应关系异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public long modFirmID2SysFirmID(FirmID2SysFirmID f2f, FirmID2SysFirmID newf2f, Connection conn) throws SQLException {
		Tool.log("===>>>修改系统与平台对应关系表的绑定关系");
		long result = 0L;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "update f_b_firmid_sysFirmID set firmID=? where firmID=? systemID=? and sysFirmID=? and bankID=?";
			state = conn.prepareStatement(sql);
			state.setString(1, newf2f.firmID);
			state.setString(2, f2f.firmID);
			state.setString(3, f2f.systemID);
			state.setString(4, f2f.sysFirmID);
			state.setString(5, f2f.bankID);
			result = state.executeUpdate();
		} catch (Exception e) {
			Tool.log("修改对应关系异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public long getNewFirmID() throws SQLException {
		long result = 0L;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select SEQ_F_B_Firm_PT.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (Exception e) {
			Tool.log("获取平台交易商最新编号异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public long addCapitalInfoPT(CapitalValue val, Connection conn) throws SQLException {
		Tool.log("===>>>[平台<-->子系统]增加资金流水记录   addCapitalInfo  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1L;
		try {
			sql = "select seq_F_B_capitalInfo.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			sql = "insert into F_B_capitalInfo_system(ID, FIRMID, FUNID, BANKID, DEBITID, CREDITID, TYPE, MONEY, OPERATOR, CREATETIME, BANKTIME, STATUS, NOTE, ACTIONID,EXPRESS,bankName,account,createdate,funid2,systemID,sysFirmID,tradeSource,feeMoney) values(?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?,?,?,?,to_char(sysdate,'yyyy-MM-dd'),?,?,?,?,?)";

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
			state.setString(17, (val.funID == null) || (val.funID.trim().length() <= 0) ? "gnnt" + id : val.funID);
			state.setString(18, val.systemID);
			state.setString(19, val.sysFirmID);
			state.setString(20, val.tradeSource);
			state.setDouble(21, val.feeMoney);
			state.executeUpdate();
			System.out.println(
					"【平台<-->子系统新增流水】:流水号[" + id + "]" + "银行流水号[" + val.funID + "]" + "市场流水号[" + val.actionID + "]" + "金额[" + val.money + "]");
		} catch (SQLException e) {
			e.printStackTrace();
			id = -1L;
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return id;
	}

	public long addCapitalInfoNotice(CapitalValue val, Connection conn) throws SQLException {
		Tool.log("===>>>[记录未推送的流水]增加资金流水记录   addCapitalInfo  " + new java.util.Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long id = -1L;
		try {
			sql = "select seq_F_B_capitalInfo.nextval from dual";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				id = rs.getLong(1);
			}
			sql = "insert into F_B_capitalInfo_notice(ID, FIRMID, FUNID, BANKID, DEBITID, CREDITID, TYPE, MONEY, OPERATOR, CREATETIME, BANKTIME, STATUS, NOTE, ACTIONID,EXPRESS,bankName,account,createdate,funid2,systemID,sysFirmID,tradeSource) values(?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?,?,?,?,to_char(sysdate,'yyyy-MM-dd'),?,?,?,?)";

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
			state.setString(17, (val.funID == null) || (val.funID.trim().length() <= 0) ? "gnnt" + id : val.funID);
			state.setString(18, val.systemID);
			state.setString(19, val.sysFirmID);
			state.setString(20, val.tradeSource);
			state.executeUpdate();
			System.out.println("【记录未推送的流水】:流水号[" + id + "]" + "银行流水号[" + val.funID + "]" + "市场流水号[" + val.actionID + "]" + "金额[" + val.money + "]");
		} catch (SQLException e) {
			e.printStackTrace();
			id = -1L;
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return id;
	}

	public long addMFirm(MFirmValue mFirm, Connection conn) throws SQLException {
		Tool.log("向交易系统表添加交易商");
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long result = 0L;
		try {
			sql = "insert into M_firm(firmid,name,type,bank,bankaccount) values (?,?,?,?,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, mFirm.firmID);
			state.setString(2, mFirm.name == null ? "" : mFirm.name);
			state.setLong(3, mFirm.type);
			state.setString(4, mFirm.bankName == null ? "" : mFirm.bankName);
			state.setString(5, mFirm.bankAccount == null ? "" : mFirm.bankAccount);
			result = state.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			result = -1L;
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public long addSystemQSData(SystemQSValue sqv, Connection conn) throws SQLException {
		Tool.log("将子系统发送的清算数据保存到数据库");
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long result = 0L;
		try {
			sql = "insert into f_b_qsDate_bank_system values (?,?,?,?,?,?,?,?,?,?,?,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, sqv.firmID);
			state.setString(2, sqv.sysFirmID);
			state.setString(3, sqv.systemID);
			state.setString(4, sqv.bankCode);
			state.setDate(5, new java.sql.Date(sqv.tradeDate.getTime()));
			state.setDouble(6, sqv.rights);
			state.setDouble(7, sqv.freeze);
			state.setDouble(8, sqv.available);
			state.setDouble(9, sqv.rightsChange);
			state.setDouble(10, sqv.freezeChange);
			state.setDouble(11, sqv.availableChange);
			state.setDouble(12, sqv.Fee);
			result = state.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			result = -1L;
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public long dellSystemQSData(SystemQSValue sqv, Connection conn) throws SQLException {
		Tool.log("删除清算数据");
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		long result = 0L;
		try {
			sql = "delete from f_b_qsDate_bank_system where systemid=? and firmid=? and sysfirmid=? and bankcode=? and tradedate=?";
			state = conn.prepareStatement(sql);
			state.setString(1, sqv.systemID);
			state.setString(2, sqv.firmID);
			state.setString(3, sqv.sysFirmID);
			state.setString(4, sqv.bankCode);
			state.setDate(5, new java.sql.Date(sqv.tradeDate.getTime()));
			result = state.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			result = -1L;
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public Vector<CapitalValue> getInMoneyCapitalNeedNotice(Connection conn) throws SQLException {
		Vector<CapitalValue> result = new Vector();
		CapitalValue value = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			String sql = "select fbcs.* from f_b_capitalinfo fbc, f_b_capitalinfo_system fbcs where fbc.actionid = fbcs.actionid and fbc.type = 0 and fbc.type = fbcs.type and fbcs.status in (2,7) and fbc.status in (0, 1) order by fbcs.createtime";

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
				value.sysFirmID = rs.getString("sysfirmID");
				value.systemID = rs.getString("systemID");
				value.tradeSource = rs.getString("tradeSource");
				result.add(value);
			}
		} catch (Exception e) {
			Tool.log("查询数据异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public Vector<SystemQSValue> getSystemQSData(String filter, Connection conn) throws SQLException {
		Vector<SystemQSValue> result = new Vector();
		SystemQSValue value = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			String sql = "select * from f_b_qsDate_bank_system " + filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new SystemQSValue();
				value.firmID = rs.getString("firmid");
				value.sysFirmID = rs.getString("sysfirmid");
				value.systemID = rs.getString("systemid");
				value.bankCode = rs.getString("bankcode");
				value.tradeDate = rs.getDate("tradedate");
				value.rights = rs.getDouble("rights");
				value.rightsChange = rs.getDouble("rightschange");
				value.freeze = rs.getDouble("freeze");
				value.freezeChange = rs.getDouble("freezechange");
				value.available = rs.getDouble("available");
				value.availableChange = rs.getDouble("availablechange");
				value.Fee = rs.getDouble("fee");
				result.add(value);
			}
		} catch (Exception e) {
			Tool.log("查询数据异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public boolean cardOpened(String account, String systemID, String bankCode) throws SQLException {
		boolean result = true;
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select * from f_b_firmidandaccount fbf,f_b_firmid_sysfirmid fbfs where fbf.firmid=fbfs.firmid and fbf.account=? and fbfs.systemid=? and fbfs.bankcode=?";
			state = conn.prepareStatement(sql);
			state.setString(1, account);
			state.setString(2, systemID);
			state.setString(3, bankCode);
			rs = state.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
			}
			if (i == 0) {
				result = false;
			}
		} catch (Exception e) {
			Tool.log("查询数据异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public Vector<QueryTradeData> marketTradeQuery(String date, String filter) throws SQLException {
		System.out.println("date==============================" + date);
		Vector<QueryTradeData> result = new Vector();
		QueryTradeData value = null;
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = "select sys.systemname, t.* from f_b_firmrzqs t,f_b_system sys where t.systemid=sys.systemid(+) " + filter;
			state = conn.prepareStatement(sql);
			System.out.println("分交易市场查询结算数据 sql================" + sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new QueryTradeData();

				value.tradeID = rs.getString("firmid");
				value.systemID = rs.getString("systemID");
				value.systemName = rs.getString("systemName");
				value.balance = rs.getDouble("balance");
				value.lastBalance = rs.getDouble("lastBalance");
				value.rights = rs.getDouble("rights");
				value.lastRights = rs.getDouble("lastRights");
				value.fee = rs.getDouble("fee");
				value.fundIO = rs.getDouble("fundIO");
				value.tradeDate = rs.getDate("b_date");
				result.add(value);
			}
		} catch (Exception e) {
			Tool.log("查询数据异常：" + Tool.getExceptionTrace(e));
			System.out.println("Tool.getExceptionTrace(e)==========" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public Vector<BankSumDate> bankTradeQuery(String date, String filter) throws SQLException {
		System.out.println("date==============================" + date);
		Vector<BankSumDate> result = new Vector();
		BankSumDate value = null;
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select b.bankname,bankcode,b_date,sum(t.balance) balance,sum(t.outinmoney) outinmoney,sum(t.rightsfrozenfunds) rightsfrozenfunds,sum(t.rights) rights,sum(t.firmfee) firmfee from f_h_firmbankfunds t,f_b_banks b where 1=1 and t.bankcode=b.bankid "
					+ filter + " group by bankcode,b_date,bankname order by b_date desc";
			state = conn.prepareStatement(sql);
			System.out.println("分银行清算汇总数据数据 sql================" + sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new BankSumDate();
				value.tradeDate = rs.getDate("b_date");
				value.bankCode = rs.getString("bankcode");
				value.bankName = rs.getString("bankname");
				value.balacne = rs.getDouble("balance");
				value.rightsfrozenfunds = rs.getDouble("rightsfrozenfunds");
				value.rights = rs.getDouble("rights");
				value.firmfee = rs.getDouble("firmfee");
				value.fundIO = rs.getDouble("outinmoney");
				result.add(value);
			}
		} catch (Exception e) {
			Tool.log("查询数据异常：" + Tool.getExceptionTrace(e));
			System.out.println("Tool.getExceptionTrace(e)==========" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public java.util.Date getCurrlastDate(String date, Connection conn) throws SQLException {
		java.util.Date result = null;
		String sql = "select to_date('" + date + "','yyyy-MM-dd')-1 lastdate from dual";
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if (rs.next()) {
				result = rs.getDate("lastdate");
			}
		} catch (SQLException e) {
			Tool.log("查询当前日期的上一个日期失败：" + Tool.getExceptionTrace(e));
		}
		return result;
	}

	public int addRZQS(Vector<RZQS> rzqs, String systemID) throws SQLException, ClassNotFoundException {
		Tool.log("添加交易系统[" + systemID + "]清算数据[" + rzqs.size() + "]  addRZQS  " + Tool.fmtTime(new java.util.Date()));
		Connection conn = null;
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			conn = getConnection();
			sql = "delete F_B_FirmRZQS where B_date=?";
			state = conn.prepareStatement(sql);
			state.setDate(1, new java.sql.Date(((RZQS) rzqs.get(0)).date.getTime()));
			state.executeUpdate();
			for (int i = 0; i < rzqs.size(); i++) {
				RZQS vo = (RZQS) rzqs.get(i);
				sql = "insert into F_B_FirmRZQS (B_Date,FirmID,systemID,Balance,lastBalance,Rights,lastRights,Fee,fundio,CREATEDATE) values (?,?,?,?,?,?,?,?,?,sysdate)";
				state = conn.prepareStatement(sql);
				state.setDate(1, new java.sql.Date(vo.date.getTime()));
				state.setString(2, vo.firmID);
				state.setString(3, systemID);
				state.setDouble(4, vo.balance);
				state.setDouble(5, vo.lastBalance);
				state.setDouble(6, vo.rights);
				state.setDouble(7, vo.lastRights);
				state.setDouble(8, vo.fee);
				state.setDouble(9, vo.rights);
				if (state.executeUpdate() == 1) {
					result++;
				} else {
					Tool.log("交易系统[" + systemID + "]交易商[" + vo.firmID + "]保存失败");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, conn);
		}
		return result;
	}

	public int addRZQS(Vector<RZQS> rzqs, String systemID, Connection conn) throws SQLException {
		Tool.log("添加交易系统[" + systemID + "]清算数据[" + rzqs.size() + "]  addRZQS  " + Tool.fmtTime(new java.util.Date()));
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "delete F_B_FirmRZQS where B_date=? and systemID=?";
			state = conn.prepareStatement(sql);
			state.setDate(1, new java.sql.Date(((RZQS) rzqs.get(0)).date.getTime()));
			state.setString(2, systemID);
			state.executeUpdate();
			sql = "insert into F_B_FirmRZQS (B_Date,FirmID,systemID,Balance,lastBalance,Rights,lastRights,Fee,fundio,CREATEDATE) values (?,?,?,?,?,?,?,?,?,sysdate)";
			state = conn.prepareStatement(sql);
			for (int i = 0; i < rzqs.size(); i++) {
				RZQS vo = (RZQS) rzqs.get(i);
				state.setDate(1, new java.sql.Date(vo.date.getTime()));
				state.setString(2, vo.firmID);
				state.setString(3, systemID);
				state.setDouble(4, vo.balance);
				state.setDouble(5, vo.lastBalance);
				state.setDouble(6, vo.rights);
				state.setDouble(7, vo.lastRights);
				state.setDouble(8, vo.fee);
				state.setDouble(9, vo.fundIO);
				if (state.executeUpdate() == 1) {
					result++;
				} else {
					Tool.log("交易系统[" + systemID + "]交易商[" + vo.firmID + "]保存失败");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public int delPlatRzqs(java.util.Date date, Connection conn) throws SQLException {
		Tool.log("删除平台待结算数据 delPlatRzqs[" + date + "]" + Tool.fmtTime(new java.util.Date()));
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "delete from f_firmclearfunds where b_date=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
			state = conn.prepareStatement(sql);
			System.out.println("删除平台待结算数据 sql================" + sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public int addPlatRzqs(java.util.Date date, Connection conn) throws SQLException {
		Tool.log("添加平台数据 addPlatRzqs[" + date + "]" + Tool.fmtTime(new java.util.Date()));
		PreparedStatement state = null;
		String sql = null;
		ResultSet rs = null;
		int result = 0;
		try {
			String sql1 = "select max(b_date) b_date from f_b_firmrzqs";
			state = conn.prepareStatement(sql1);
			rs = state.executeQuery();
			java.sql.Date lastDate = null;
			while (rs.next()) {
				lastDate = rs.getDate("b_date");
			}
			if (lastDate == null) {
				lastDate = new java.sql.Date(0, 0, 1);
			}
			StringBuffer bf = new StringBuffer("insert into f_firmclearfunds ");
			bf.append("(b_date, firmid, balance, rightsfrozenfunds, rights, firmfee, marketfee) ");
			bf.append("(select ");
			bf.append("to_date(?, 'yyyy-MM-dd') b_date, ");
			bf.append("t2.firmid, ");
			bf.append("nvl(t2.balance,0) + nvl(t1.qychange,0) - nvl(t3.frozen, 0) balance, ");
			bf.append("nvl(t3.frozen, 0) rightsfrozenfunds, ");
			bf.append("nvl(t2.balance, 0) + nvl(t1.qychange, 0) rights, ");
			bf.append("nvl(t1.fee, 0) fee, ");
			bf.append("0 marketfee ");
			bf.append("from  ");
			bf.append("(select firmid,sum(rights) - sum(lastrights) - sum(fundio) qychange,sum(fee) fee from ");
			bf.append(
					"(select f.firmid,t.systemid, t.balance, t.lastbalance, t.rights, t.lastrights, t.fee, t.fundio, t.createdate,t.doflag,t.a_date from f_b_firmrzqs t, ");
			bf.append("(select distinct sysfirmid, firmid, systemid from f_b_firmid_sysfirmid) f ");
			bf.append("where t.firmid = f.sysfirmid and t.systemid = f.systemid and t.doflag = 0) group by firmid) t1, ");
			bf.append("(select firmid, sum(rights)-sum(balance) frozen from f_b_firmrzqs where b_date=to_date(?,'yyyy-MM-dd') group by firmid) t3,");
			bf.append("(select firmid, sum(balance) balance from f_firmbankfunds group by firmid) t2 ");
			bf.append("where ");
			bf.append("t2.firmid = t1.firmid(+) ");
			bf.append("and t2.firmid = t3.firmid(+) )");
			sql = bf.toString();
			state = conn.prepareStatement(sql);
			state.setString(1, Tool.fmtDate(date));
			state.setString(2, Tool.fmtDate(lastDate));
			System.out.println("添加平台清算数据 sql================" + sql);
			result = state.executeUpdate();

			sql = "update f_b_firmrzqs set doFlag=1,A_Date=to_date(?,'yyyy-MM-dd') where doFlag=0";
			state = conn.prepareStatement(sql);
			state.setString(1, Tool.fmtDate(date));
			state.executeUpdate();
		} catch (SQLException e) {
			result = -1;
			e.printStackTrace();
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public Vector<FirmID2SysFirmID> getFirmMapping(String filter) throws SQLException {
		Vector<FirmID2SysFirmID> result = new Vector();
		FirmID2SysFirmID value = null;
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select * from P_MAPPINGFIRM " + filter;
			Tool.log("查询SQL[" + sql + "]");
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new FirmID2SysFirmID();
				value.firmID = rs.getString("PLATFORMID");
				value.sysFirmID = rs.getString("FIRMID");
				value.systemID = String.valueOf(rs.getLong("MODULEID"));
				result.add(value);
			}
		} catch (Exception e) {
			Tool.log("查询数据异常：" + Tool.getExceptionTrace(e));
			System.out.println("Tool.getExceptionTrace(e)==========" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public int addFirmBankFunds(FirmBankFunds fbf, Connection conn) throws SQLException {
		PreparedStatement state = null;
		int result = 0;
		try {
			String sql = "insert into f_firmbankfunds (FirmID,bankcode,balance,outMoneyFrozenFunds,OutInMoney,clearOutInMoney,primaryBankFlag) values (?,?,?,?,?,?,?)";
			state = conn.prepareStatement(sql);
			state.setString(1, fbf.firmID);
			state.setString(2, fbf.bankID);
			state.setDouble(3, fbf.balance);
			state.setDouble(4, fbf.outMoneyFrozenFunds);
			state.setDouble(5, fbf.OutInMoney);
			state.setDouble(6, fbf.clearOutInMoney);
			state.setDouble(7, fbf.primaryBankFlag);
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public int delFirmBankFunds(FirmBankFunds fbf, Connection conn) throws SQLException {
		PreparedStatement state = null;
		int result = 0;
		try {
			String sql = "delete from f_firmbankfunds where firmid=? and bankcode=?";
			state = conn.prepareStatement(sql);
			state.setString(1, fbf.firmID);
			state.setString(2, fbf.bankID);
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public int modFirmBankFunds(FirmBankFunds fbf, Connection conn) throws SQLException {
		PreparedStatement state = null;
		int result = 0;
		try {
			String sql = "update f_firmbankfunds set balance=?,OUTMONEYFROZENFUNDS=?,OUTINMONEY=?,CLEAROUTINMONEY=?,PRIMARYBANKFLAG=? where FIRMID=? and BANKCODE=?";
			state = conn.prepareStatement(sql);
			state.setDouble(1, fbf.balance);
			state.setDouble(2, fbf.outMoneyFrozenFunds);
			state.setDouble(3, fbf.OutInMoney);
			state.setDouble(4, fbf.clearOutInMoney);
			state.setDouble(5, fbf.primaryBankFlag);
			state.setString(6, fbf.firmID);
			state.setString(7, fbf.bankID);
			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public Vector<FirmBankFunds> getFirmBankFunds(String filter) throws SQLException {
		Vector<FirmBankFunds> result = new Vector();
		Connection conn = null;
		try {
			conn = getConnection();
			result = getFirmBankFunds(filter, conn);
		} catch (ClassNotFoundException e) {
			System.out.println("查询异常：+ " + Tool.getExceptionTrace(e));
			e.printStackTrace();
		} finally {
			closeStatement(null, null, conn);
		}
		return result;
	}

	public Vector<FirmBankFunds> getFirmBankFunds(String filter, Connection conn) throws SQLException {
		Vector<FirmBankFunds> result = new Vector();
		FirmBankFunds fbf = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			String sql = "select b.bankname,f.* from f_firmbankfunds f,f_b_banks b where f.bankcode=b.bankid " + filter;
			System.out.println("sql[" + sql + "]");
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				fbf = new FirmBankFunds();
				fbf.firmID = rs.getString("firmiD");
				fbf.bankID = rs.getString("bankcode");
				fbf.bankName = rs.getString("bankname");
				fbf.balance = rs.getDouble("balance");
				fbf.OutInMoney = rs.getDouble("outinmoney");
				fbf.clearOutInMoney = rs.getDouble("clearoutinmoney");
				fbf.outMoneyFrozenFunds = rs.getDouble("outMoneyFrozenFunds");
				fbf.primaryBankFlag = rs.getInt("primaryBankFlag");
				result.add(fbf);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public Vector<FirmBankFunds> getFirmBankFundsHis(String filter) throws SQLException {
		Vector<FirmBankFunds> result = new Vector();
		FirmBankFunds fb = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			String sql = "select b.bankname,f.* from f_h_firmbankfunds f,f_b_banks b where f.bankcode=b.bankid " + filter;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				fb = new FirmBankFunds();
				fb.b_date = rs.getDate("B_DATE");
				fb.firmID = rs.getString("FIRMID");
				fb.bankID = rs.getString("BANKCODE");
				fb.bankName = rs.getString("bankname");
				fb.balance = rs.getDouble("BALANCE");
				fb.OutInMoney = rs.getDouble("OUTINMONEY");
				fb.outMoneyFrozenFunds = rs.getDouble("OUTMONEYFROZENFUNDS");
				fb.primaryBankFlag = rs.getInt("PRIMARYBANKFLAG");
				fb.RIGHTS = rs.getDouble("RIGHTS");
				fb.RIGHTSFROZENFUNDS = rs.getDouble("RIGHTSFROZENFUNDS");
				fb.FIRMFEE = rs.getDouble("FIRMFEE");
				fb.MARKETFEE = rs.getDouble("MARKETFEE");
				result.add(fb);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("查询交易商历史资金账户信息异常：" + Tool.getExceptionTrace(e));
			e.printStackTrace();
		} finally {
			closeStatement(rs, ps, conn);
		}
		return result;
	}

	public Vector<FirmID2SysFirmID> getfirmIDMgs(String filter) throws SQLException {
		Vector<FirmID2SysFirmID> result = new Vector();
		FirmID2SysFirmID value = null;
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select fbfs.*,fbf.account account,fbf.accountName accountName,fbf.card card,fbf.cardtype cardtype from f_b_firmid_sysfirmid fbfs,f_b_firmidandaccount fbf where fbfs.firmid=fbf.firmid and fbfs.bankid = fbf.bankid "
					+ filter;
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				value = new FirmID2SysFirmID();
				value.firmID = rs.getString("firmid");
				value.sysFirmID = rs.getString("sysfirmid");
				value.systemID = rs.getString("systemid");
				value.account = rs.getString("account");
				value.accountName = rs.getString("accountName");
				value.card = rs.getString("card");
				value.cardType = rs.getString("cardType");
				value.bankID = rs.getString("bankid");
				result.add(value);
			}
		} catch (Exception e) {
			Tool.log("查询数据异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public Vector<BankCode> getBankCode(String filter) {
		Tool.log("====>查询银行编号和银行编码对照信息");
		Vector<BankCode> vec = new Vector();
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "select * from f_b_banksCode " + filter;

		Tool.log("====>查询语句[" + sql + "]");
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				BankCode bc = new BankCode();
				bc.bankID = rs.getString("bankID");
				bc.bankCode = rs.getString("bankCode");
				bc.marketOpen = rs.getString("marketOpen");
				vec.add(bc);
			}
		} catch (Exception e) {
			Tool.log("查询出现异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return vec;
	}

	public java.util.Date getMaxDate() {
		Tool.log("====>获取最近一次资金结算日期" + new java.util.Date().toLocaleString());
		java.util.Date date = null;
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "select max(b_date) b_date from f_h_firmbankfunds ";

		Tool.log("====>查询语句[" + sql + "]");
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if (rs.next()) {
				date = new java.util.Date(rs.getDate("b_date").getTime());
			}
		} catch (Exception e) {
			Tool.log("查询出现异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return date;
	}

	public boolean getSysStatus(java.util.Date date) {
		Tool.log("====>获取日期[" + date + "]财务结算状态" + new java.util.Date().toLocaleString());
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "select * from f_log where description like '结算完成%' and trunc(occurtime) = to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";

		Tool.log("====>查询语句[" + sql + "]");
		System.out.println("====>查询语句[" + sql + "]");
		boolean falg = false;
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			if (rs.next()) {
				falg = true;
			}
		} catch (Exception e) {
			Tool.log("查询出现异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return falg;
	}

	public int modBankAccount(CorrespondValue corrOld, CorrespondValue val, Connection conn) throws SQLException {
		Tool.log("===>>>修改帐号对应关系   modCorrespond conn  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "update f_b_firmidandaccount set bankID='" + val.bankID + "',status=" + val.status + ",accountname = '" + val.accountName + "'"
					+ ",bankname = '" + val.bankName + "',bankprovince = '" + val.bankProvince + "',bankcity = '" + val.bankCity + "'," + "mobile = '"
					+ val.mobile + "',email = '" + val.email + "',isopen=" + val.isOpen + ",cardtype = '" + val.cardType + "'" + ",card='" + val.card
					+ "',account1='" + val.account1 + "',account='" + val.account + "' ,accountName1 = '" + val.accountName1 + "'" + ",inMarketCode='"
					+ val.inMarketCode + "'" + ",isCrossLine='" + val.isCrossLine + "'" + ",OpenBankCode='" + val.OpenBankCode + "'"
					+ " where bankID='" + corrOld.bankID + "' and firmid = '" + corrOld.firmID + "'";
			System.out.println("sql=[" + sql + "]");
			state = conn.prepareStatement(sql);

			result = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			result = -1;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public Vector<RZQS> getRZQSData(java.util.Date date, String systemID) throws SQLException, ClassNotFoundException {
		Tool.log("查询[" + Tool.fmtDate(date) + "]的清算数据");
		Vector<RZQS> result = new Vector();
		RZQS value = null;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select * from f_b_firmrzqs where b_date=to_date(?,'yyyy-MM-dd') and systemID=?";
			st = conn.prepareStatement(sql);
			st.setString(1, Tool.fmtDate(date));
			st.setString(2, systemID);
			rs = st.executeQuery();
			while (rs.next()) {
				value = new RZQS();
				value.date = rs.getDate("b_date");
				value.firmID = rs.getString("firmid");
				value.systemID = rs.getString("systemid");
				value.balance = rs.getDouble("balance");
				value.lastBalance = rs.getDouble("lastBalance");
				value.rights = rs.getDouble("rights");
				value.lastRights = rs.getDouble("lastRights");
				value.fee = rs.getDouble("fee");
				value.fundIO = rs.getDouble("fundio");
				value.createDate = rs.getDate("createdate");
				value.doFlag = rs.getInt("doFlag");
				result.add(value);
			}
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeStatement(rs, st, conn);
		}
		return result;
	}

	public Vector<RZQS> checkRZQSData(java.util.Date date) throws SQLException, ClassNotFoundException {
		Tool.log("查询[" + Tool.fmtDate(date) + "]的清算数据");
		Vector<RZQS> result = new Vector();
		RZQS value = null;
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = "select * from f_b_firmrzqs where doflag=0";
			st = conn.prepareStatement(sql);

			rs = st.executeQuery();
			while (rs.next()) {
				value = new RZQS();
				value.date = rs.getDate("b_date");
				value.firmID = rs.getString("firmid");
				value.systemID = rs.getString("systemid");
				value.balance = rs.getDouble("balance");
				value.lastBalance = rs.getDouble("lastBalance");
				value.rights = rs.getDouble("rights");
				value.lastRights = rs.getDouble("lastRights");
				value.fee = rs.getDouble("fee");
				value.fundIO = rs.getDouble("fundio");
				value.createDate = rs.getDate("createdate");
				value.doFlag = rs.getInt("doFlag");
				result.add(value);
			}
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeStatement(rs, st, conn);
		}
		return result;
	}

	public int delCapitalByDateSystem(String systemID, java.util.Date date, Connection conn) throws SQLException {
		Tool.log("删除记录的交易系统[" + systemID + "]在[" + Tool.fmtDate(date) + "]的成功的流水信息");
		int result = 0;
		PreparedStatement st = null;
		try {
			String sql = "delete from  f_b_capitalinfo_system2 where systemid=? and createdate=?";
			st = conn.prepareStatement(sql);
			st.setString(1, systemID);
			st.setString(2, Tool.fmtDate(date));
			result = st.executeUpdate();
		} catch (SQLException e) {
			Tool.log("SQL异常:" + Tool.getExceptionTrace(e));
			result = -1;
		} finally {
			closeStatement(null, st, null);
		}
		return result;
	}

	public int addCapitalByDateSystem(String systemID, Vector<CapitalValue> vec, Connection conn) throws SQLException {
		Tool.log("增加交易系统[" + systemID + "]的成功的流水信息，共[" + vec.size() + "]条");
		int result = 0;
		PreparedStatement state = null;
		try {
			String sql = "insert into f_b_capitalinfo_system2(SYSTEMID, FIRMID, FUNID, BANKID, TYPE, MONEY, CREATETIME, BANKTIME, STATUS, NOTE, ACTIONID,createdate,platfirmID) values(?, ?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?, to_char(sysdate,'yyyy-MM-dd'), ?)";
			state = conn.prepareStatement(sql);
			state.setString(1, systemID);
			for (CapitalValue val : vec) {
				state.setString(2, val.sysFirmID == null ? "" : val.sysFirmID.trim());
				state.setString(3, val.funID == null ? "" : val.funID.trim());
				state.setString(4, val.bankID == null ? "" : val.bankID.trim());
				state.setInt(5, val.type);
				state.setDouble(6, val.money);
				state.setTimestamp(7, val.bankTime);
				state.setInt(8, val.status);
				state.setString(9, val.note == null ? "" : val.note.trim());
				state.setLong(10, val.actionID);
				state.setString(11, val.firmID == null ? "" : val.firmID.trim());
				result += state.executeUpdate();
			}
		} catch (SQLException e) {
			Tool.log("SQL异常:" + Tool.getExceptionTrace(e));
			result = -1;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public Vector<CapitalValueMoney> getMoneyErrorCapital(String systemID, java.util.Date date) throws SQLException, ClassNotFoundException {
		Tool.log("查询[" + Tool.fmtDate(date) + "]平台和交易系统[" + systemID + "]账面不平流水");
		Vector<CapitalValueMoney> result = new Vector();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("select ");
			sql.append("c.createdate as createdate, ");
			sql.append("c.firmid as firmid, ");
			sql.append("c.systemid as systemid, ");
			sql.append("c.bankid as bankid, ");
			sql.append("c.funid as sys_actionID, ");
			sql.append("b.funid as pt_actionID, ");
			sql.append("b.type as sys_type, ");
			sql.append("c.type as pt_type, ");
			sql.append("b.money as sys_money, ");
			sql.append("c.money as pt_money ");
			sql.append("from f_b_Capitalinfo_System2 b, f_b_Capitalinfo_System c ");
			sql.append("where c.status = 0 ");
			sql.append(" and (c.type = 0 or c.type = 1 or c.type = 3) ");
			sql.append(" and b.createdate = ? ");
			sql.append(" and c.createdate = ? ");
			sql.append(" and b.funid = to_char(c.actionid) ");
			sql.append(" and c.funid = to_char(b.actionid) ");
			sql.append(" and c.systemid = ? ");
			sql.append(" and b.systemid = ? ");
			sql.append(" and (b.money <> c.money or b.type <> c.type) ");
			st = conn.prepareStatement(sql.toString());
			st.setString(1, Tool.fmtDate(date));
			st.setString(2, Tool.fmtDate(date));
			st.setString(3, systemID);
			st.setString(4, systemID);
			rs = st.executeQuery();
			CapitalValueMoney value = null;
			while (rs.next()) {
				value = new CapitalValueMoney();
				value.createDate = rs.getString("createdate");
				value.firmID = rs.getString("firmid");
				value.systemID = rs.getString("systemid");
				value.bankID = rs.getString("bankid");
				value.sysActionID = rs.getString("sys_actionID");
				value.platformActionID = rs.getString("pt_actionID");
				value.sysType = rs.getInt("sys_type");
				value.platformType = rs.getInt("pt_type");
				value.sysMoney = rs.getDouble("sys_money");
				value.money = rs.getDouble("pt_money");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(rs, st, conn);
		}
		return result;
	}

	public Vector<CapitalValue> getPlatNoCapital(String systemID, java.util.Date date) throws SQLException, ClassNotFoundException {
		Tool.log("查询[" + Tool.fmtDate(date) + "]交易系统[" + systemID + "]有，平台没有的流水");
		Vector<CapitalValue> result = new Vector();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select * from f_b_capitalinfo_system2 b where b.createdate=? and systemid=? and funid not in (select to_char(c.actionid) from f_b_capitalinfo_system c where c.createdate=? and systemid=? and c.status=0 and (c.type =0 or c.type=1 or c.type=3))";
			st = conn.prepareStatement(sql.toString());
			st.setString(1, Tool.fmtDate(date));
			st.setString(2, systemID);
			st.setString(3, Tool.fmtDate(date));
			st.setString(4, systemID);
			rs = st.executeQuery();
			CapitalValue value = null;
			while (rs.next()) {
				value = new CapitalValue();
				value.bankID = rs.getString("bankid");
				value.funID = rs.getString("funid");
				value.actionID = rs.getLong("actionID");
				value.firmID = rs.getString("platfirmid");
				value.sysFirmID = rs.getString("firmid");
				value.type = rs.getInt("type");
				value.money = rs.getDouble("money");
				value.status = rs.getInt("status");
				value.bankTime = rs.getTimestamp("banktime");
				value.createtime = rs.getTimestamp("createtime");
				value.note = rs.getString("note");
				value.systemID = rs.getString("systemid");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(rs, st, conn);
		}
		return result;
	}

	public Vector<CapitalValue> getSysNoCapital(String systemID, java.util.Date date) throws SQLException, ClassNotFoundException {
		Tool.log("查询[" + Tool.fmtDate(date) + "]交易系统[" + systemID + "]没有有，平台有的流水");
		Vector<CapitalValue> result = new Vector();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select * from f_b_capitalinfo_system b where b.status=0 and (b.type=0 or b.type=1 or b.type=3) and b.createdate=? and b.systemid=? and b.funid not in (select to_char(actionid) from f_b_capitalinfo_system2 where createdate=? and systemid=?)";
			st = conn.prepareStatement(sql.toString());
			st.setString(1, Tool.fmtDate(date));
			st.setString(2, systemID);
			st.setString(3, Tool.fmtDate(date));
			st.setString(4, systemID);
			rs = st.executeQuery();
			CapitalValue value = null;
			while (rs.next()) {
				value = new CapitalValue();
				value.bankID = rs.getString("bankid");
				value.funID = rs.getString("funid");
				value.actionID = rs.getLong("actionID");
				value.firmID = rs.getString("firmid");
				value.sysFirmID = rs.getString("sysfirmid");
				value.type = rs.getInt("type");
				value.money = rs.getDouble("money");
				value.status = rs.getInt("status");
				value.bankTime = rs.getTimestamp("banktime");
				value.createtime = rs.getTimestamp("createtime");
				value.note = rs.getString("note");
				value.systemID = rs.getString("systemid");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(rs, st, conn);
		}
		return result;
	}

	public Vector<CapitalValueMoney> getMoneyErrorCapital2(java.util.Date date) throws SQLException, ClassNotFoundException {
		Tool.log("查询[" + Tool.fmtDate(date) + "]平台到交易系统和平台到银行账面不平流水");
		Vector<CapitalValueMoney> result = new Vector();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("select ");
			sql.append("c.createdate as createdate, ");
			sql.append("c.firmid as firmid, ");
			sql.append("c.systemid as systemid, ");
			sql.append("c.bankid as bankid, ");
			sql.append("c.actionID as sys_actionID, ");
			sql.append("b.actionID as pt_actionID, ");
			sql.append("b.type as sys_type, ");
			sql.append("c.type as pt_type, ");
			sql.append("b.money as sys_money, ");
			sql.append("c.money as pt_money ");
			sql.append("from f_b_Capitalinfo b, f_b_Capitalinfo_System c ");
			sql.append("where c.status = 0 ");
			sql.append(" and (c.type = 0 or c.type = 1) ");
			sql.append(" and (b.type = 0 or b.type = 1) ");
			sql.append(" and b.createdate = ? ");
			sql.append(" and c.createdate = ? ");
			sql.append(" and b.actionid = c.actionid ");
			sql.append(" and (b.money <> c.money or b.type <> c.type) ");
			st = conn.prepareStatement(sql.toString());
			st.setString(1, Tool.fmtDate(date));
			st.setString(2, Tool.fmtDate(date));
			rs = st.executeQuery();
			CapitalValueMoney value = null;
			while (rs.next()) {
				value = new CapitalValueMoney();
				value.createDate = rs.getString("createdate");
				value.firmID = rs.getString("firmid");
				value.systemID = rs.getString("systemid");
				value.bankID = rs.getString("bankid");
				value.sysActionID = rs.getString("sys_actionID");
				value.platformActionID = rs.getString("pt_actionID");
				value.sysType = rs.getInt("sys_type");
				value.platformType = rs.getInt("pt_type");
				value.sysMoney = rs.getDouble("sys_money");
				value.money = rs.getDouble("pt_money");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(rs, st, conn);
		}
		return result;
	}

	public Vector<CapitalValue> getToBankNoCapital(java.util.Date date) throws SQLException, ClassNotFoundException {
		Tool.log("查询[" + Tool.fmtDate(date) + "]平台到交易系统有，平台到银行没有的流水");
		Vector<CapitalValue> result = new Vector();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select * from f_b_capitalinfo_system b where b.createdate=? and b.status=0 and (b.type=0 or b.type=1) and b.actionid not in (select c.actionid from f_b_capitalinfo c where c.createdate=? and c.status=0 and (c.type =0 or c.type=1))";
			st = conn.prepareStatement(sql.toString());
			st.setString(1, Tool.fmtDate(date));
			st.setString(2, Tool.fmtDate(date));
			rs = st.executeQuery();
			CapitalValue value = null;
			while (rs.next()) {
				value = new CapitalValue();
				value.bankID = rs.getString("bankid");
				value.funID = rs.getString("funid");
				value.actionID = rs.getLong("actionID");
				value.firmID = rs.getString("platfirmid");
				value.sysFirmID = rs.getString("firmid");
				value.type = rs.getInt("type");
				value.money = rs.getDouble("money");
				value.status = rs.getInt("status");
				value.bankTime = rs.getTimestamp("banktime");
				value.createtime = rs.getTimestamp("createtime");
				value.note = rs.getString("note");
				value.systemID = rs.getString("systemid");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(rs, st, conn);
		}
		return result;
	}

	public Vector<CapitalValue> getToSysNoCapital(java.util.Date date) throws SQLException, ClassNotFoundException {
		Tool.log("查询[" + Tool.fmtDate(date) + "]平台到交易系统没有，平台到银行有的流水");
		Vector<CapitalValue> result = new Vector();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			String sql = "select * from f_b_capitalinfo b where b.status=0 and (b.type=0 or b.type=1) and b.createdate=? and b.actionid not in (select actionid from f_b_capitalinfo_system where createdate=? and status=0 and (type=0 or type=1))";
			st = conn.prepareStatement(sql.toString());
			st.setString(1, Tool.fmtDate(date));
			st.setString(2, Tool.fmtDate(date));
			rs = st.executeQuery();
			CapitalValue value = null;
			while (rs.next()) {
				value = new CapitalValue();
				value.bankID = rs.getString("bankid");
				value.funID = rs.getString("funid");
				value.actionID = rs.getLong("actionID");
				value.firmID = rs.getString("firmid");
				value.sysFirmID = rs.getString("sysfirmid");
				value.type = rs.getInt("type");
				value.money = rs.getDouble("money");
				value.status = rs.getInt("status");
				value.bankTime = rs.getTimestamp("banktime");
				value.createtime = rs.getTimestamp("createtime");
				value.note = rs.getString("note");
				value.systemID = rs.getString("systemid");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(rs, st, conn);
		}
		return result;
	}

	public Vector<CheckMessage> getCheckMsg(Map<String, Object> params) throws SQLException {
		Tool.log("==>查询特殊值");
		Vector<CheckMessage> result = new Vector();
		CheckMessage msg = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection conn = null;
		try {
			conn = getConnection();
			String sql = getSQL("select * from f_b_function_bank where 1=1 ", params.keySet());
			System.out.println("==>执行SQL:[" + sql + "]");
			st = conn.prepareStatement(sql);
			if (setStatementValues(st, params) != 0) {
				Tool.log("转入参数的类型可能有误");
				Vector<CheckMessage> localVector1 = result;
				return localVector1;
			}
			rs = st.executeQuery();
			while (rs.next()) {
				msg = new CheckMessage();
				msg.bankID = rs.getString("bankid");
				msg.functionID = rs.getString("fid");
				msg.fName = rs.getString("fname");
				msg.value = rs.getString("value");
				result.add(msg);
			}
		} catch (ClassNotFoundException e) {
			Tool.log("查询异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, st, conn);
		}
		return result;
	}

	public int saveAccount1(String firmid, String bankID, String account1, Connection conn) throws SQLException {
		Tool.log("==>保存交易商[" + firmid + "]签约银行[" + bankID + "]的银行子账号[" + account1 + "]");
		int result = 0;
		PreparedStatement st = null;
		try {
			String sql = "update f_b_firmidandaccount set account1=? where firmid=? and bankid=?";
			st = conn.prepareStatement(sql);
			st.setString(1, account1);
			st.setString(2, firmid);
			st.setString(3, bankID);
			result = st.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(null, st, null);
		}
		return result;
	}

	public Vector<FirmBalance> getRZDateByBank(String bankID, java.util.Date qdate) throws SQLException, ClassNotFoundException {
		Vector<FirmBalance> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql0 = "select max(B_DATE) lastDate from f_h_firmbankfunds t where t.b_date < to_date(?,'yyyy-MM-dd')";
		java.sql.Date lastDay = null;
		StringBuffer sql = new StringBuffer(" ");
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql0);
			state.setString(1, Tool.fmtDate(qdate));
			rs = state.executeQuery();
			while (rs.next()) {
				lastDay = rs.getDate("lastDate");
			}
			if (lastDay == null) {
				lastDay = new java.sql.Date(0, 0, 1);
			}
			sql.append("select f.firmid, ");
			sql.append("f.contact, ");
			sql.append("f.bankid, ");
			sql.append("f.account, ");
			sql.append("f.accountname, ");
			sql.append("f.cardtype, ");
			sql.append("f.card, ");
			sql.append("nvl(today.firmfee, 0) Fee, ");
			sql.append("nvl(today.rights, 0) rights, ");
			sql.append("nvl(today.balance, 0) balance, ");
			sql.append("nvl(yestoday.rights, 0) lastRights, ");
			sql.append("nvl(yestoday.balance, 0) lastBalance, ");
			sql.append("nvl(today.outInMoney,0) crjsum ");
			sql.append("from ");
			sql.append("(select * from f_h_firmbankfunds where b_date=to_date(?,'yyyy-MM-dd')) today, ");
			sql.append("(select * from f_h_firmbankfunds where b_date=to_date(?,'yyyy-MM-dd')) yestoday, ");
			sql.append("(select * from f_b_firmidandaccount where isopen=1) f  ");
			sql.append("where f.firmid=today.firmid(+) ");
			sql.append(" and f.firmid=yestoday.firmid(+) ");
			sql.append(" and f.bankid=today.bankcode(+) ");
			sql.append(" and f.bankid=yestoday.bankcode(+) ");
			sql.append(" and f.bankid=? ");
			System.out.println("建行清算SQL：" + sql.toString());
			state = conn.prepareStatement(sql.toString());
			state.setString(1, Tool.fmtDate(qdate));
			state.setString(2, Tool.fmtDate(lastDay));
			state.setString(3, bankID);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmBalance fb = new FirmBalance();
				fb.firmID = rs.getString("contact");
				fb.bankID = rs.getString("bankid");
				fb.account = rs.getString("account");
				fb.accountName = rs.getString("accountName");
				fb.cardType = rs.getString("cardType");
				fb.card = rs.getString("card");
				fb.FeeMoney = rs.getDouble("Fee");
				fb.QYChangeMoney = Arith.sub(rs.getDouble("rights"), rs.getDouble("lastRights"));
				fb.QYMoney = rs.getDouble("rights");
				fb.yesQYMoney = rs.getDouble("lastRights");
				fb.kyChangeMoney = Arith.sub(rs.getDouble("balance"), rs.getDouble("lastBalance"));
				fb.kyMoney = rs.getDouble("balance");
				fb.yesKyMoney = rs.getDouble("lastBalance");
				fb.date = qdate;
				fb.CRJSum = rs.getDouble("crjsum");
				result.add(fb);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public long addCCBQS(Vector<FirmBalance> vector, Connection conn) throws SQLException {
		long result = 0L;
		String sql = null;
		PreparedStatement state = null;
		try {
			sql = "insert into F_B_CCBQS (actionId,bankid,firmId,fee,QYChangeMoney,money,CRJSum,lastMoney,CREATEDATE,flag) values(seq_F_B_action.nextval,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd'),?)";

			state = conn.prepareStatement(sql);
			for (FirmBalance fb : vector) {
				System.out.println("addCCBQS--FeeMoney:" + fb.FeeMoney + "--QYChangeMoney:" + fb.QYChangeMoney);

				state.setString(1, fb.bankID);
				state.setString(2, fb.firmID);
				state.setDouble(3, fb.FeeMoney);
				state.setDouble(4, fb.QYChangeMoney);
				state.setDouble(5, fb.QYMoney);
				state.setDouble(6, fb.CRJSum);
				state.setDouble(7, fb.yesQYMoney);
				state.setString(8, Tool.fmtDate(fb.date));
				state.setString(9, "0");
				result += state.executeUpdate();
			}
		} catch (SQLException e) {
			result = -1L;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public long delCCBQS(java.util.Date date, Connection conn) throws SQLException {
		long result = 0L;
		String sql = null;
		PreparedStatement state = null;
		try {
			sql = "delete from F_B_CCBQS where trunc(CREATEDATE)=to_date('" + Tool.fmtDate(date) + "','yyyy-MM-dd')";
			System.out.println("delCCBQS：" + sql);
			state = conn.prepareStatement(sql);

			result = state.executeUpdate();
		} catch (SQLException e) {
			result = -1L;
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public Vector<FirmBalance> getCCBQS(Connection conn, String filter) throws SQLException {
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;

		Vector<FirmBalance> result = new Vector();
		try {
			sql = "select * from F_B_CCBQS where 1=1  " + filter;

			System.out.println("getCCBQS：" + sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				FirmBalance fb = new FirmBalance();
				fb.firmID = rs.getString("firmid");
				fb.bankID = rs.getString("bankid");
				fb.FeeMoney = rs.getDouble("Fee");
				fb.QYChangeMoney = rs.getDouble("QYChangeMoney");
				fb.QYMoney = rs.getDouble("money");
				fb.yesQYMoney = rs.getDouble("lastMoney");
				fb.date = rs.getDate("CREATEDATE");
				fb.actionId = rs.getString("actionId");
				fb.falg = rs.getString("flag");
				result.add(fb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public long updateCCBQS(String actionId, String falg, Connection conn) throws SQLException {
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			sql = "update F_B_CCBQS set flag='" + falg + "' where actionId='" + actionId + "'";
			System.out.println("updateCCBQS：" + sql);
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
}
