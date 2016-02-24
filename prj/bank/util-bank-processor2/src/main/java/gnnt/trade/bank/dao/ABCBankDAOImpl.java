package gnnt.trade.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.trade.bank.vo.AbcInfoValue;

public class ABCBankDAOImpl extends ABCBankDAO {
	private final transient Log logger = LogFactory.getLog("Processorlog");

	public ABCBankDAOImpl() throws Exception {
	}

	public AbcInfoValue getAbcInfo(String contact, long orderNo, int type, Connection conn) throws SQLException {
		log("===>>>取得农行交易相关信息   getBank  " + new Date());
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = null;
		AbcInfoValue value = null;
		try {
			sql = "select * from F_B_AbcInfo where contact='" + contact + "' and orderno='" + orderNo + "' and type='" + type + "'";

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
		log("===>>>增加农行交易相关信息  " + new Date());

		Statement state = null;
		try {
			String sql = " insert into F_B_AbcInfo values('" + val.firmID + "', '" + val.account1 + "', '" + val.orderNo + "', '" + val.actionID
					+ "', '" + val.type + "', '" + val.signInfo + "',sysdate)";

			state = conn.createStatement();
			state.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeStatement(null, state, null);
		}
	}
}
