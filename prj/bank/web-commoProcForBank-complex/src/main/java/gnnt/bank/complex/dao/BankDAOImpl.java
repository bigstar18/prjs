package gnnt.bank.complex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import gnnt.bank.complex.vo.FirmFundsBankValue;
import gnnt.bank.complex.vo.FirmFundsValue;
import gnnt.bank.complex.vo.FirmPlatformMsg;
import gnnt.bank.platform.util.Tool;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.RZQS;

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

	public Vector<FirmPlatformMsg> getPlatformMsg(Map<String, Object> params) throws SQLException {
		Tool.log("==>查询交易系统端交易商对应的平台号和签约号");
		Vector<FirmPlatformMsg> result = new Vector();
		FirmPlatformMsg fpf = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection conn = null;
		try {
			conn = getConnection();
			String sql = getSQL("select * from f_b_platformMsg where 1=1 ", params.keySet());
			System.out.println("==>执行SQL:[" + sql + "]");
			st = conn.prepareStatement(sql);
			if (setStatementValues(st, params) != 0) {
				Tool.log("转入参数的类型可能有误");
				return result;
			}
			rs = st.executeQuery();
			while (rs.next()) {
				fpf = new FirmPlatformMsg();
				fpf.firmID = rs.getString("firmID");
				fpf.platFirmID = rs.getString("platfirmid");
				result.add(fpf);
			}
		} catch (ClassNotFoundException e) {
			Tool.log("查询异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, st, conn);
		}
		return result;
	}

	public int addPlatformMsg(FirmPlatformMsg fpfm, Connection conn) throws SQLException {
		Tool.log("==>添加交易系统交易商代码和平台号、签约号的对应关系");
		int result = 0;
		PreparedStatement st = null;
		String sql = "insert into f_b_platformMsg(firmID, platfirmID) values (?,?)";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, fpfm.firmID);
			st.setString(2, fpfm.platFirmID);
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, st, null);
		}
		return result;
	}

	public int delPlatformMsg(FirmPlatformMsg fpfm) throws SQLException {
		Tool.log("==>添加交易系统交易商代码和平台号、签约号的对应关系");
		int result = 0;
		PreparedStatement st = null;
		Connection conn = null;
		try {
			conn = getConnection();
			result = delPlatformMsg(fpfm, conn);
		} catch (Exception e) {
			Tool.log("删除异常：" + Tool.getExceptionTrace(e));
			result = -1;
		} finally {
			closeStatement(null, st, conn);
		}
		return result;
	}

	public int delPlatformMsg(FirmPlatformMsg fpfm, Connection conn) throws SQLException {
		Tool.log("==>添加交易系统交易商代码和平台号、签约号的对应关系");
		int result = 0;
		PreparedStatement st = null;
		String sql = "delete from f_b_platformMsg where firmid=? and platfirmID=?";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, fpfm.firmID);
			st.setString(2, fpfm.platFirmID);
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, st, null);
		}
		return result;
	}

	public Vector<CapitalValue> getCapitalList(Map<String, Object> map) throws SQLException {
		Tool.log("==>查询转账流水信息");
		Vector<CapitalValue> result = new Vector();
		Connection conn = null;
		try {
			conn = getConnection();
			result = getCapitalList(map, conn);
		} catch (ClassNotFoundException e) {
			Tool.log("查询异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(null, null, conn);
		}
		return result;
	}

	public Vector<CapitalValue> getCapitalList(Map<String, Object> map, Connection conn) throws SQLException {
		Tool.log("==>查询转账流水信息");
		Vector<CapitalValue> result = new Vector();
		CapitalValue cv = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String sql = getSQL("select * from f_b_capitalinfo where 1=1 ", map.keySet());
			st = conn.prepareStatement(sql);
			if (setStatementValues(st, map) != 0) {
				Tool.log("转入参数的类型可能有误");
				return result;
			}
			rs = st.executeQuery();
			while (rs.next()) {
				cv = new CapitalValue();
				cv.actionID = rs.getLong("actionID");
				cv.bankID = rs.getString("bankID");
				cv.bankTime = rs.getTimestamp("bankTime");
				cv.createtime = rs.getTimestamp("createtime");
				cv.firmID = rs.getString("PLATFIRMID");
				cv.funID = rs.getString("funID");
				cv.iD = rs.getLong("iD");
				cv.money = rs.getDouble("money");
				cv.note = rs.getString("note");
				cv.status = rs.getInt("status");
				cv.type = rs.getInt("type");
				cv.sysFirmID = rs.getString("firmID");
				result.add(cv);
			}
		} catch (SQLException e) {
			Tool.log("查询异常：" + Tool.getExceptionTrace(e));
			throw e;
		} finally {
			closeStatement(rs, st, null);
		}
		closeStatement(rs, st, null);

		return result;
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
			sql = "insert into F_B_capitalInfo(ID, FIRMID, FUNID, BANKID, TYPE, MONEY, CREATETIME, BANKTIME, STATUS, NOTE, ACTIONID,createdate,platfirmID) values(?, ?, ?, ?, ?, ?, sysdate, ?, ?, ?, ?, to_char(sysdate,'yyyy-MM-dd'), ?)";

			state = conn.prepareStatement(sql);
			state.setLong(1, id);
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
			int m = state.executeUpdate();
			System.out.println("【新增流水】:流水号[" + id + "]" + "银行流水号[" + val.funID + "]" + "市场流水号[" + val.actionID + "]" + "金额[" + val.money + "]");
			if (m != 1) {
				Tool.log("新增流水失败");
				id = -1L;
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

	public int modCapitalInfoStatus(long id, String funID, int status, Timestamp bankTime, Connection conn) throws SQLException {
		Tool.log("===>>>修改资金流水记录状态   modCapitalInfoStatus  " + new java.util.Date());
		PreparedStatement state = null;
		String sql = null;
		int result = 0;
		try {
			if ((funID == null) || (funID.trim().length() <= 0)) {
				funID = "";
			}
			sql = "update F_B_capitalInfo set status=?,FUNID=?,bankTime=? where id=?";

			state = conn.prepareStatement(sql);
			state.setInt(1, status);
			state.setString(2, funID.trim());
			state.setTimestamp(3, bankTime);
			state.setLong(4, id);
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

	public int modCapitalInfoNote(long id, String note, Connection conn) throws SQLException {
		Tool.log("===>>>修改资金流水记录描述   modCapitalInfoNote  " + new java.util.Date());
		if (note == null) {
			note = "";
		}
		int result = 0;
		PreparedStatement state = null;
		String sql = null;
		try {
			sql = "update F_B_capitalInfo set note=note || '，' || '" + note.trim() + "' where id=" + id + " ";
			state = conn.prepareStatement(sql);
			result = state.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
		return result;
	}

	public FirmFundsValue getFirmFunds(String firmID) throws SQLException {
		Tool.log("查询客户总资金信息  getFirmFunds  firmID[" + firmID + "]");
		FirmFundsValue result = null;
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = "select ff.LastCapital lastBalance,ff.balance - ff.frozenfunds as firmFunds ,nvl(ff.runtimefundio,0) as inOutFunds,FN_F_CanUseFunds(ff.firmid,0) canOutFunds  from f_firmfunds ff where ff.firmid=?";

			state = conn.prepareStatement(sql);
			state.setString(1, firmID);
			rs = state.executeQuery();
			if (rs.next()) {
				result = new FirmFundsValue();
				result.firmID = firmID;
				result.firmFunds = rs.getDouble("firmFunds");
				result.lastBalance = rs.getDouble("lastBalance");
				result.inOutFunds = rs.getDouble("inOutFunds");
				result.canOutFunds = rs.getDouble("canOutFunds");
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			Tool.log("查询异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public Vector<FirmFundsBankValue> getFirmFundsBank(String firmID) throws SQLException {
		Tool.log("查询客户在每个银行的分资金信息  getFirmFundsBank  firmID[" + firmID + "]");
		Vector<FirmFundsBankValue> result = new Vector();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = "select fbf.bankid bankID,fbb.bankname bankName,fbf.firmid firmID,fbf.contact contact,fbf.account account ,fbf.account1 account1,ff.bankcode mainBank,ffb.lastbalance lastBalance,ffb.runtimefundio inOutMoney ,FN_F_CanOutFunds(fbf.firmid,0,fbf.bankid) canOutFunds,fbf.frozenfuns frozenFunds  from f_firmfunds_bank ffb,f_firmfunds ff,f_b_firmidandaccount fbf,f_b_banks fbb  where fbf.bankid=ffb.bankcode(+) and fbf.isopen=1 and fbf.bankid=fbb.bankid(+)  and ffb.firmid=? and ff.firmid=? and fbf.firmid=? order by fbf.opentime ";

			state = conn.prepareStatement(sql);
			state.setString(1, firmID);
			state.setString(2, firmID);
			state.setString(3, firmID);
			rs = state.executeQuery();
			FirmFundsBankValue value = null;
			while (rs.next()) {
				value = new FirmFundsBankValue();
				value.bankID = rs.getString("bankID");
				value.bankName = rs.getString("bankName");
				value.firmID = rs.getString("firmID");
				value.contact = rs.getString("contact");
				value.account = rs.getString("account");
				value.account1 = rs.getString("account1");
				value.mainBank = rs.getString("mainBank");
				value.lastBalance = rs.getDouble("lastBalance");
				value.inOutMoney = rs.getDouble("inOutMoney");
				value.canOutFunds = rs.getDouble("canOutFunds");
				value.frozenFunds = rs.getDouble("frozenFunds");
				result.add(value);
			}
		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			Tool.log("查询异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}

	public int addPlatformMsg(FirmPlatformMsg fpfm) throws SQLException {
		Tool.log("==>添加交易系统交易商代码和平台号、签约号的对应关系");
		int result = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			result = addPlatformMsg(fpfm, conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStatement(null, null, conn);
		}
		return result;
	}

	public java.util.Date getlastDate(java.util.Date date, Connection conn) throws SQLException {
		java.util.Date result = null;
		String sql = "select max(B_Date) b_date from f_firmclearfunds t where t.b_date < to_date(?,'yyyy-MM-dd')";
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, Tool.fmtDate(date));
			rs = state.executeQuery();
			if (rs.next()) {
				result = rs.getDate("b_date");
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

	public Vector<RZQS> getRZData(java.util.Date date) throws SQLException {
		Tool.log("==>查询[" + Tool.fmtDate(date) + "]的日终数据");
		Vector<RZQS> result = new Vector();
		RZQS rzData = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection conn = null;
		try {
			conn = getConnection();
			java.util.Date lastDate = getlastDate(date, conn);
			StringBuffer sql = new StringBuffer("select ");
			sql.append("fbf.firmid firmid,");
			sql.append("nvl(fff.balance, 0) balance,");
			sql.append("nvl(fff.rightsfrozenfunds, 0) frozen,");
			sql.append("nvl(fff.rights, 0) rights,");
			sql.append("nvl(fff.firmfee, 0) firmfee,");
			sql.append("nvl(fff.marketfee, 0) marketfee,");
			sql.append("nvl(fffLast.balance, 0) lastBalance,");
			sql.append("nvl(fffLast.rights, 0) lastRights, ");
			sql.append("nvl(inMoney.amount, 0) - nvl(outMoney.amount, 0) fundio ");
			sql.append("from ");
			sql.append("(select * from f_firmclearfunds where b_date = to_date(?, 'yyyy-MM-dd')) fff,");
			sql.append("(select * from f_firmclearfunds where b_date = to_date(?, 'yyyy-MM-dd')) fffLast,");
			sql.append(
					"(select firmid, sum(amount) amount from f_h_fundflow t where t.oprcode in ('11001','11003') and b_date = to_date(?, 'yyyy-MM-dd') group by firmid) inMoney,");
			sql.append(
					"(select firmid, sum(amount) amount from f_h_fundflow t where t.oprcode in ('11002','11004') and b_date = to_date(?, 'yyyy-MM-dd') group by firmid) outMoney,");
			sql.append("f_b_platformmsg fbf ");
			sql.append("where ");
			sql.append("fbf.firmid = fff.firmid(+) ");
			sql.append("and fbf.firmid = fffLast.firmid(+) ");
			sql.append("and fbf.firmid = inMoney.firmid(+) ");
			sql.append("and fbf.firmid = outMoney.firmid(+) ");
			Tool.log("查询SQL[" + sql.toString() + "]");
			st = conn.prepareStatement(sql.toString());
			st.setString(1, Tool.fmtDate(date));
			st.setString(2, Tool.fmtDate(lastDate));
			st.setString(3, Tool.fmtDate(date));
			st.setString(4, Tool.fmtDate(date));
			rs = st.executeQuery();
			while (rs.next()) {
				rzData = new RZQS();
				rzData.firmID = rs.getString("firmid");
				rzData.rights = rs.getDouble("rights");
				rzData.lastRights = rs.getDouble("lastRights");
				rzData.balance = rs.getDouble("balance");
				rzData.lastBalance = rs.getDouble("lastBalance");
				rzData.fee = rs.getDouble("firmfee");
				rzData.date = date;
				rzData.fundIO = rs.getDouble("fundio");
				rzData.systemID = Tool.getConfig("SystemID");
				result.add(rzData);
			}
		} catch (Exception e) {
			Tool.log("查询数据异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, st, conn);
		}
		return result;
	}

	public boolean checkRZData(java.util.Date date) throws SQLException {
		Tool.log("==>检查[" + Tool.fmtDate(date) + "]是否有日终数据");
		boolean result = false;
		ResultSet rs = null;
		PreparedStatement st = null;
		Connection conn = null;
		try {
			conn = getConnection();
			String sql = "select * from f_firmclearfunds where b_date=to_date(?,'yyyy-MM-dd')";
			st = conn.prepareStatement(sql);
			st.setString(1, Tool.fmtDate(date));
			rs = st.executeQuery();
			while (rs.next()) {
				result = true;
			}
		} catch (Exception e) {
			Tool.log("查询数据异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, st, conn);
		}
		return result;
	}

	public boolean isTradeDate(java.util.Date tradeDate) throws SQLException {
		Tool.log("判断是否为交易日");
		boolean flag = true;
		Calendar c = Calendar.getInstance();
		c.setTime(tradeDate);
		int dayOfWeek = c.get(7);
		String date = Tool.fmtDate(tradeDate);
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;

		String sql = "select * from e_tradetime_rt";
		try {
			conn = getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
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
			Tool.log("系统异常：" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return flag;
	}
}
