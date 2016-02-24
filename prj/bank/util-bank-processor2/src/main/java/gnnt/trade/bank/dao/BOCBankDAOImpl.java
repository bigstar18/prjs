package gnnt.trade.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import gnnt.trade.bank.data.boc.BOCExDataImpl;
import gnnt.trade.bank.data.boc.vo.AccountStatusReconciliation;
import gnnt.trade.bank.data.boc.vo.StorageMoneyLedgerBalanceList;
import gnnt.trade.bank.data.boc.vo.StorageMoneySettlementList;
import gnnt.trade.bank.data.boc.vo.TransferAccountsTransactionDetailed;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.Tool;

public class BOCBankDAOImpl extends BOCBankDAO {
	private BankDAO DAO;

	public BOCBankDAOImpl() throws Exception {
		try {
			this.DAO = BankDAOFactory.getDAO();
		} catch (ClassNotFoundException e) {
			log(Tool.getExceptionTrace(e));
		} catch (IllegalAccessException e) {
			log(Tool.getExceptionTrace(e));
		} catch (InstantiationException e) {
			log(Tool.getExceptionTrace(e));
		}
	}

	public List<TransferAccountsTransactionDetailed> getZZJYMX(String bankID, Date tradeDate, Connection conn) throws SQLException {
		BOCExDataImpl.log("查询转账交易明细信息");
		List<TransferAccountsTransactionDetailed> result = new ArrayList();
		PreparedStatement state = null;
		ResultSet rs = null;
		String filter1 = "";
		if ((bankID != null) && (!"".equals(bankID))) {
			filter1 = filter1 + "  AND fbc.bankid='" + bankID + "'";
		}
		String filter2 = "";
		if (tradeDate != null) {
			filter2 = filter2 + " AND trunc(fbc.createtime) = to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')";
		}
		String filter = "";

		String sql = "SELECT fbc.createtime TransDateTime, fbc.funid BankSerialNumber, fbc.actionid LaunchSerialNumber, fbf.account BankAccount, fbc.contact bondAcc, fbc.money money, (CASE fbc.type WHEN  0 THEN 1 WHEN 1 THEN 2 END) TransferDirection FROM F_B_capitalInfo fbc, F_B_FIRMIDANDACCOUNT fbf WHERE 1=1 "
				+

		filter2 + filter1 + filter + " AND fbc.bankID=fbf.bankID AND fbc.status= 0 AND fbc.firmid = fbf.firmid AND fbc.type <> 2";
		BOCExDataImpl.log(sql);
		try {
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				TransferAccountsTransactionDetailed tatd = new TransferAccountsTransactionDetailed();
				tatd.setBankCode("");
				tatd.setMarketCode("");
				tatd.setTransAddressCode("");
				tatd.setTransDateTime(Tool.fmtDate(rs.getDate("TransDateTime")));
				tatd.setTransTime(Common.df4.format(rs.getDate("TransDateTime")));
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
			BOCExDataImpl.log("转账交易明细信息获取失败");
			BOCExDataImpl.log(Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public List<AccountStatusReconciliation> getKHZHZT(String bankID, Date tradeDate, Connection conn) throws SQLException, ClassNotFoundException {
		List<AccountStatusReconciliation> result = new ArrayList();
		PreparedStatement state = null;
		ResultSet rs = null;
		String filter = "";
		if ((bankID != null) && (!"".equals(bankID))) {
			filter = filter + "  AND fbf.bankid='" + bankID + "'";
		}
		String sql = "";
		try {
			sql =

			"SELECT fbf.contact bondAcc, fbf.account BankAccount, fbf.accountname certificationName, (CASE fbf.isopen WHEN  0 THEN 1 WHEN 1 THEN 0 END) CashExCode FROM  f_b_firmidandaccount fbf WHERE 1=1  AND (trunc(fbf.opentime) = to_date('"
					+ Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')" + " OR trunc(fbf.deltime) = to_date('" + Tool.fmtDate(tradeDate)
					+ "','yyyy-MM-dd'))" + filter;
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
				asr.setBondAcc(rs.getString("bondAcc"));
				asr.setCertificationName(rs.getString("certificationName"));
				asr.setMoneyType("001");
				asr.setCashExCode("0");
				asr.setStatus(rs.getString("CashExCode"));
				result.add(asr);
			}
		} catch (SQLException e) {
			BOCExDataImpl.log("获取客户状态明细信息失败");
			BOCExDataImpl.log(Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public List<StorageMoneySettlementList> getCGKHZJJSMX(String bankID, Date tradeDate, Connection conn)
			throws SQLException, ClassNotFoundException {
		List<StorageMoneySettlementList> result = new ArrayList();
		PreparedStatement state = null;
		ResultSet rs = null;
		try {
			String sql = "select seq_F_B_action.nextval actionid,fbf.account account,fbf.contact contact,fbf.accountName accountName,(ffb.capital-ffb.lastcapital-ffb.fundio) qyChange from F_FIRMBALANCE_BANK ffb,F_B_FIRMIDANDACCOUNT fbf  where fbf.firmID=ffb.firmID(+) and fbf.bankID='"
					+

			bankID + "' and fbf.isOpen=1 " + " and ffb.bankcode = '" + bankID + "' and ffb.b_date=to_date('" + Tool.fmtDate(tradeDate)
					+ "','yyyy-MM-dd')";
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				StorageMoneySettlementList smsl = new StorageMoneySettlementList();
				smsl.setBankCode("");
				smsl.setMarketCode("");
				smsl.setTransAddressCode("");
				smsl.setTransDateTime(Tool.fmtDate(tradeDate));
				smsl.setTaiZhangZhangHao("");
				smsl.setBondAcc(rs.getString("contact"));
				smsl.setCertificationName(rs.getString("accountName"));

				smsl.setTradeDifference(rs.getDouble("qyChange") >= 0.0D ? "0" : "1");
				smsl.setMoneyType("001");
				smsl.setCashExCode("0");
				smsl.setMoney(rs.getDouble("qyChange"));
				result.add(smsl);
			}
			if ("true".equals(Tool.getConfig("out"))) {
				Map<String, Double> map1 = this.DAO.getAllVirtualFunds(null, conn);
				Date lastQSDate = this.DAO.getMaxvirtualfunds(bankID, tradeDate, conn);

				Map<String, Double> map = this.DAO.getQsdateVirtualFunds(lastQSDate, conn);
				log(map.toString());
				for (int i = 0; i < result.size(); i++) {
					String firmID = ((StorageMoneySettlementList) result.get(i)).bondAcc;

					((StorageMoneySettlementList) result.get(i)).money =

					(((StorageMoneySettlementList) result.get(i)).money + (map.get(firmID) == null ? 0.0D : ((Double) map.get(firmID)).doubleValue())
							- (map1.get(firmID) == null ? 0.0D : ((Double) map1.get(firmID)).doubleValue()));

					log(((StorageMoneySettlementList) result.get(i)).money + "");
				}
			}
		} catch (SQLException e) {
			BOCExDataImpl.log("获取客户交收明细信息失败");
			BOCExDataImpl.log(Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public List<StorageMoneyLedgerBalanceList> getCGKHZJTZYEMX(String bankID, Date tradeDate, Connection conn)
			throws SQLException, ClassNotFoundException {
		List<StorageMoneyLedgerBalanceList> result = new ArrayList();
		PreparedStatement state = null;
		ResultSet rs = null;
		String sql = "select seq_F_B_action.nextval actionid,fbf.account account,fbf.contact contact,fbf.accountName accountName,ffb.capital capital from F_FIRMBALANCE_BANK ffb,F_B_FIRMIDANDACCOUNT fbf  where fbf.firmID=ffb.firmID(+) and fbf.bankID='"
				+

		bankID + "' and fbf.isOpen=1 " + " and ffb.bankcode = '" + bankID + "' and ffb.b_date=to_date('" + Tool.fmtDate(tradeDate)
				+ "','yyyy-MM-dd')";
		try {
			BOCExDataImpl.log(sql);
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			while (rs.next()) {
				StorageMoneyLedgerBalanceList smlb = new StorageMoneyLedgerBalanceList();
				smlb.setBankCode("");
				smlb.setMarketCode("");
				smlb.setTransAddressCode("");
				smlb.setTransDateTime(Tool.fmtDate(tradeDate));
				smlb.setTaiZhangZhangHao("");
				smlb.setBondAcc(rs.getString("contact"));
				smlb.setCertificationName(rs.getString("accountName"));
				smlb.setMoneyType("001");
				smlb.setCashExCode("0");
				smlb.setMoney(rs.getDouble("capital"));
				result.add(smlb);
			}
			if ("true".equals(Tool.getConfig("out"))) {
				Map<String, Double> map1 = this.DAO.getAllVirtualFunds(null, conn);
				for (int i = 0; i < result.size(); i++) {
					String firmID = ((StorageMoneyLedgerBalanceList) result.get(i)).bondAcc;
					((StorageMoneyLedgerBalanceList) result.get(i)).money -= (map1.get(firmID) == null ? 0.0D
							: ((Double) map1.get(firmID)).doubleValue());

					log(((StorageMoneyLedgerBalanceList) result.get(i)).money + "");
				}
			}
		} catch (SQLException e) {
			BOCExDataImpl.log("获取资金台账余额信息失败");
			BOCExDataImpl.log(Tool.getExceptionTrace(e));
		} finally {
			closeStatement(rs, state, null);
		}
		return result;
	}

	public void log(String content) {
		Logger plog = Logger.getLogger("Processorlog");
		plog.debug(content);
	}
}
