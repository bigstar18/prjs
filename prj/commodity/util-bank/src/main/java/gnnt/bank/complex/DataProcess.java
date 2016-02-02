package gnnt.bank.complex;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;

import gnnt.bank.complex.dao.BankDAO;
import gnnt.bank.complex.dao.BankDAOFactory;
import gnnt.bank.platform.util.Tool;

public class DataProcess {
	private int INSUMMARY;
	private int OUTSUMMARY;
	private int FEESUMMARY;
	private int CHSUMMARY;
	private Hashtable<String, String> BANKSUB = new Hashtable();

	protected Hashtable<String, String> getBANKSUB() {
		return this.BANKSUB;
	}

	protected int getFEESUMMARY() {
		return this.FEESUMMARY;
	}

	protected int getINSUMMARY() {
		return this.INSUMMARY;
	}

	protected int getOUTSUMMARY() {
		return this.OUTSUMMARY;
	}

	protected int getCHSUMMARY() {
		return this.CHSUMMARY;
	}

	protected double getRealFunds(String firmID, Connection conn) throws SQLException {
		double result = 0.0D;
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{ ?=call FN_F_GetRealFunds(?,?) }");

			proc.setString(2, firmID);

			proc.setInt(3, 1);

			proc.registerOutParameter(1, 8);

			proc.executeQuery();

			result = proc.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		Tool.log("交易商[" + firmID + "]可用资金[" + result + "]");

		return result;
	}

	protected double updateFrozenFunds(String firmID, double money, Connection conn) throws SQLException {
		double result = 0.0D;
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{ ?=call FN_F_UpdateFrozenFunds(?,?,?) }");

			proc.setString(2, firmID);

			proc.setDouble(3, money);

			proc.setString(4, "28");

			proc.registerOutParameter(1, 8);

			proc.executeUpdate();

			result = proc.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	protected double updateFundsFull(String firmID, String summary, String subject, double money, long actionID, Connection conn)
			throws SQLException {
		System.out.println("firmID[" + firmID + "]summary[" + summary + "]subject[" + subject + "]money[" + money + "]actionID[" + actionID + "]");
		double result = 0.0D;
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{ ?=call  FN_F_UpdateFundsFull(?,?,?,?,?,null,null) }");

			proc.setString(2, firmID);

			proc.setString(3, summary);

			proc.setDouble(4, money);

			proc.setString(5, actionID + "");

			proc.setString(6, subject);

			proc.registerOutParameter(1, 8);

			proc.executeQuery();

			result = proc.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	protected Hashtable<String, Hashtable<String, String>> getFirmTradeBal(Date b_date) {
		BankDAO DAO = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement state = null;
		ResultSet rs1 = null;
		PreparedStatement state1 = null;
		Hashtable<String, Hashtable<String, String>> result = new Hashtable();
		try {
			DAO = BankDAOFactory.getDAO();
			conn = DAO.getConnection();

			String sql = "select distinct(firmid) from  F_FirmBalance where b_date=to_date('" + b_date + "','yyyy-mm-dd')";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				String firmid = rs.getString(1);
				sql = "select t1.code, t2.name ,t1.value from  F_ClientLedger t1,f_ledgerfield t2 where t1.b_date=to_date('" + b_date
						+ "','yyyy-mm-dd')" + " and t1.code=t2.code and firmid='" + firmid + "'";
				state1 = conn.prepareStatement(sql);
				rs1 = state1.executeQuery();
				Hashtable<String, String> values = new Hashtable();
				while (rs1.next()) {
					values.put(rs1.getString(1), rs1.getString(3));
				}
				result.put(firmid, values);
				rs1.close();
				rs1 = null;
				state1.close();
				state1 = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(rs, state, conn);
		}
		return result;
	}

	protected void synchronizedFirm() {
		BankDAO DAO = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement state = null;
		ResultSet rs1 = null;
		PreparedStatement state1 = null;
		try {
			DAO = BankDAOFactory.getDAO();
			conn = DAO.getConnection();

			String sql = "select firmid,name from m_firm";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				String firmid = rs.getString(1);
				String name = rs.getString(2);

				sql = "select count(firmid) from f_b_firmuser where firmid='" + firmid + "'";
				state1 = conn.prepareStatement(sql);
				rs1 = state1.executeQuery();
				int cnt = 0;
				while (rs1.next()) {
					cnt = rs1.getInt(1);
				}
				rs1.close();
				rs1 = null;
				state1.close();
				state1 = null;
				if (cnt == 0) {
					sql = "insert into f_b_firmuser(firmid, name, status, registerdate)values(?, ?, 1, sysdate)";

					state1 = conn.prepareStatement(sql);
					state1.setString(1, firmid);
					state1.setString(2, name);
					state1.executeUpdate();

					state1.close();
					state1 = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAO.closeStatement(rs, state, conn);
		}
	}

	public long updateMarketMoney(String auditor, double money, Connection conn) throws SQLException {
		Tool.log("跨行汇拨更新市场自有资金，操作人员[" + auditor + "],金额[" + money + "] " + new Date());
		CallableStatement proc = null;
		PreparedStatement state = null;
		long result = 0L;
		try {
			proc = conn.prepareCall("{ ?=call  FN_F_CreateVoucher('106','结转交易手续费','20302','204',?,null,?) }");
			proc.setDouble(2, money);
			proc.setString(3, auditor);
			proc.registerOutParameter(1, 4);
			proc.executeQuery();
			result = proc.getLong(1);
			if (result > 0L) {
				Tool.log("跨行汇拨更新市场自有资金-添加凭证成功");
				String sql = " update F_Voucher  set auditor=?,audittime=sysdate,status='audited' where voucherno=? ";

				state = conn.prepareStatement(sql);
				state.setString(1, auditor);
				state.setLong(2, result);
				state.executeUpdate();
				Tool.log("跨行汇拨更新市场自有资金-更新资金成功");
			} else {
				Tool.log("跨行汇拨更新市场自有资金-添加凭证失败");
			}
		} catch (SQLException e) {
			Tool.log("跨行汇拨更新市场自有资金-sql异常");
			result = -1L;
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			Tool.log("跨行汇拨更新市场自有资金-异常");
			result = -1L;
			e.printStackTrace();
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (state != null) {
				try {
					state.close();
					state = null;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (proc != null) {
				try {
					proc.close();
					proc = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (state != null) {
				try {
					state.close();
					state = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
