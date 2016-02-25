package gnnt.bank.platform.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.transaction.Transaction;

import gnnt.bank.platform.util.Tool;
import gnnt.bank.platform.vo.BankSetBalance;

public class BankFeeAndOffsetDaoImpl extends BankFeeAndOffsetDao {
	public BankFeeAndOffsetDaoImpl() throws Exception {
	}

	public Set<List<Object>> userQuery() {
		Set<List<Object>> set = new LinkedHashSet();
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Object> list = null;
		try {
			conn = getConnection();
			String sql = "select u.id,u.name from c_user u ";
			state = conn.prepareStatement(sql);
			System.out.println("用户信息记录 sql================" + sql);
			rs = state.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();

			int mdCount = rsmd.getColumnCount();
			System.out.println(mdCount);
			while (rs.next()) {
				list = null;
				list = new ArrayList();
				for (int i = 0; i < mdCount; i++) {
					list.add(rs.getObject(i + 1));
				}
				set.add(list);
			}
			return set;
		} catch (Exception e) {
			Tool.log("查询数据异常：" + Tool.getExceptionTrace(e));
			System.out.println("Tool.getExceptionTrace(e)==========" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return null;
	}

	public boolean insertTransferRights(String sql, String bankCode, String B_Date, double Amount) {
		PreparedStatement state = null;
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Transaction tx = null;
		boolean flag = false;
		int c = 0;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			String sqlJC = "select TodayFee from F_H_MarketFeeOnBank where BANKCODE = '" + bankCode + "' and B_Date = to_date('"
					+ B_Date.substring(0, 10) + "','yyyy-MM-dd')";
			System.out.print(sqlJC);
			state = conn.prepareStatement(sqlJC);
			rs = state.executeQuery();
			boolean bool1;
			if (rs.next()) {
				c++;
				if ((rs.getDouble(1) < Amount) || (Amount <= 0.0D)) {
					flag = true;
					System.out.println(rs.getDouble(1));
					return flag;
				}
			}
			if (c == 0) {
				flag = true;
				return flag;
			}
			state = null;
			stmt = conn.createStatement();
			stmt.execute(sql);
			conn.commit();
			return flag;
		} catch (Exception e) {
			Tool.log("查询数据异常：" + Tool.getExceptionTrace(e));
			System.out.println("Tool.getExceptionTrace(e)==========" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return true;
	}

	public Set<List<Object>> TransferRightsQuery(String date, String filter) {
		Set<List<Object>> set = new LinkedHashSet();
		System.out.println("date==============================" + date);
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Object> list = null;
		try {
			conn = getConnection();
			String sql = "select t.ID,t.BankCode,b.bankname,t.Amount,t.OperatorID,t.TransferTime,t.Remark from F_TransferRights t,f_b_banks b where b.bankid = t.bankcode "
					+ filter;
			state = conn.prepareStatement(sql);
			System.out.println("分银行市场收益查询 sql================" + sql);
			rs = state.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();

			int mdCount = rsmd.getColumnCount();
			System.out.println(mdCount);
			while (rs.next()) {
				list = null;
				list = new ArrayList();
				for (int i = 0; i < mdCount; i++) {
					list.add(rs.getObject(i + 1));
				}
				set.add(list);
			}
			return set;
		} catch (Exception e) {
			Tool.log("查询数据异常：" + Tool.getExceptionTrace(e));
			System.out.println("Tool.getExceptionTrace(e)==========" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return null;
	}

	public Set<List<Object>> MarketFeeOnBankQuery(String date, String filter) {
		Set<List<Object>> set = new LinkedHashSet();
		System.out.println("date==============================" + date);
		PreparedStatement state = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Object> list = null;
		try {
			conn = getConnection();

			String sql = "select m.B_Date,m.BankCode,b.BANKNAME,m.LastFee,m.NewFee,m.TransferFee,m.TodayFee from F_H_MarketFeeOnBank m,(select max( b_date)  b_date ,bankcode from F_H_MarketFeeOnBank group by bankcode) n ,f_b_banks b  where 1 = 1 and m.BankCode = b.bankid and n.b_date = m.b_date and m.bankcode  = n.bankcode"
					+

			filter;
			state = conn.prepareStatement(sql);
			System.out.println("分银行市场收益查询 sql================" + sql);
			rs = state.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();

			int mdCount = rsmd.getColumnCount();
			System.out.println(mdCount);
			while (rs.next()) {
				list = null;
				list = new ArrayList();
				for (int i = 0; i < mdCount; i++) {
					list.add(rs.getObject(i + 1));
				}
				set.add(list);
			}
			return set;
		} catch (Exception e) {
			Tool.log("查询数据异常：" + Tool.getExceptionTrace(e));
			System.out.println("Tool.getExceptionTrace(e)==========" + Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, conn);
		}
		return null;
	}

	public Vector<BankSetBalance> getSetBalance(String where) throws SQLException, ClassNotFoundException {
		Vector<BankSetBalance> result = new Vector();

		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "select o.*,b.bankname from f_h_bankoffsetbalance o,f_b_banks b where o.bankcode = b.bankid " + where;
		try {
			conn = getConnection();
			System.out.println("==>执行SQL:[" + sql + "]");
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				BankSetBalance balance = new BankSetBalance();
				balance.setB_date(rs.getDate("b_date"));
				balance.setBankCode(rs.getString("bankCode"));
				balance.setLastRights(rs.getBigDecimal("lastrights"));
				balance.setOutinMoney(rs.getBigDecimal("outinmoney"));
				balance.setOffsetBalance(rs.getBigDecimal("offsetbalance"));
				balance.setTodayRights(rs.getBigDecimal("todayrights"));
				balance.setBankName(rs.getString("bankname"));
				result.add(balance);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStatement(rs, state, conn);
		}
		return result;
	}
}
