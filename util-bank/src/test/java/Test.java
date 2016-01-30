
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import gnnt.bank.platform.util.Encryption;
import gnnt.trade.bank.vo.FirmValue;

public class Test {
	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String url = "jdbc:oracle:thin:@192.168.0.169:1521:gnnt";

		String user = "trade_bank";
		String password = "password";
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		Vector<FirmValue> vec = new Vector();
		FirmValue value = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			System.out.println(conn);
			String sql = "select * from f_b_firmuser";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
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
				vec.add(value);
			}
			if (vec != null) {
				System.out.println("交易商的个数[" + vec.size() + "]");
				int i = 1;
				for (FirmValue fv : vec) {
					System.out.println("重置第[" + i + "]个交易商[" + fv.firmID + "]的密码");
					fv.password = Encryption.encryption(fv.firmID, "111111", null);
					String sql1 = "update f_b_firmuser set password=? where firmid=?";
					st = conn.prepareStatement(sql1);
					st.setString(1, fv.password);
					st.setString(2, fv.firmID);
					st.executeUpdate();
					i++;
				}
				conn.commit();
				System.out.println("数据已经提交");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
				System.out.println("数据已经回滚");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
}