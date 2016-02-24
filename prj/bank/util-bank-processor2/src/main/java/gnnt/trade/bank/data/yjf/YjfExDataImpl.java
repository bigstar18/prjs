package gnnt.trade.bank.data.yjf;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.CapitalProcessor;
import gnnt.trade.bank.dao.BankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.RZQSValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.FirmDZValue;
import gnnt.trade.bank.vo.bankdz.xy.sent.child.MarketRightValue;

public class YjfExDataImpl {
	private BankDAO DAO;

	public YjfExDataImpl() {
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

	private String fmtDate(Date time) {
		String result = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			result = sdf.format(time);
		} catch (Exception e) {
			Tool.getExceptionTrace(e);
		}
		return result;
	}

	private BankAdapterRMI getAdapter() {
		return new CapitalProcessor().getAdapter(YjfConstant.bankID);
	}

	public ReturnValue send(Date tradeDate) {
		ReturnValue result = new ReturnValue();
		List<FirmBalance> list = getFirmBalance(tradeDate);
		RZQSValue rzqs = new RZQSValue();
		RZDZValue rzdz = new RZDZValue();

		Vector<FirmDZValue> firmList = new Vector();
		MarketRightValue marketRV = new MarketRightValue();
		marketRV.bankErrorMoney = 0.0D;
		marketRV.maketMoney = new BigDecimal(0);
		for (FirmBalance fbalance : list) {
			FirmDZValue firmDV = new FirmDZValue();

			firmDV.firmID = fbalance.firmID;
			firmDV.account = fbalance.account;
			firmDV.firmRights = fbalance.QYMoney;
			firmDV.availableBalance = fbalance.QYMoney;
			firmDV.butfunds = fbalance.QYMoney;

			firmDV.quanyibh = fbalance.MQYChangeMoney;

			marketRV.bankErrorMoney += fbalance.QYChangeMoney;
			if ("C".equalsIgnoreCase(fbalance.firmtype)) {
				marketRV.maketMoney = marketRV.maketMoney.add(new BigDecimal(fbalance.FeeMoney));
			}
			firmList.add(firmDV);
		}
		marketRV.bankErrorMoney = (0.0D - marketRV.bankErrorMoney);
		rzdz.tradeDate = tradeDate;
		rzdz.setFdv(firmList);

		rzqs.tradeDate = tradeDate;
		rzqs.setMarketRight(marketRV);

		BankAdapterRMI bankAdapter = getAdapter();
		try {
			result = bankAdapter.setRZ(rzqs, rzdz, tradeDate);
			log("易极付发送清算的结果:[" + result.toString() + "]");
		} catch (RemoteException e) {
			log("调用易极付适配器发送清算异常：" + Common.getExceptionTrace(e));
			e.printStackTrace();
		}
		return result;
	}

	private List<FirmBalance> getFirmBalance(Date tradeDate) {
		List<FirmBalance> result = new ArrayList();
		Connection conn = null;
		PreparedStatement state = null;
		ResultSet rs = null;

		String sql = "select seq_F_B_action.nextval actionid,tf.firmtype firmtype, fbf.account account,fbf.account1 account1,fbf.contact contact,ffb.capital capital,fbf.accountName accountName,(ffb.capital-ffb.lastcapital-ffb.fundio-ffb.tradefee) qyChange,(ffb.capital-ffb.lastcapital-ffb.fundio) mqyChange,ffb.tradefee fee,ffb.capital,ffb.fundio from  (select * from F_FIRMBALANCE_BANK f where f.bankcode = '"
				+

		YjfConstant.bankID + "' and f.b_date=to_date('" + Tool.fmtDate(tradeDate) + "','yyyy-MM-dd')) ffb,"
				+ " (select * from F_B_FIRMIDANDACCOUNT f where f.bankID='" + YjfConstant.bankID + "' and f.isOpen=1 ) fbf ,"
				+ " (select * from t_firm) tf" + " where fbf.firmID=ffb.firmID(+)" + " and fbf.firmID=tf.firmid(+)";
		try {
			log("易极付清算sql:" + sql);
			conn = this.DAO.getConnection();
			state = conn.prepareStatement(sql);
			rs = state.executeQuery();
			FirmBalance value = null;
			while (rs.next()) {
				value = new FirmBalance();
				value.firmID = rs.getString("contact");
				value.bankID = YjfConstant.bankID;
				value.account = rs.getString("account");
				value.accountName = rs.getString("accountName");
				value.FeeMoney = rs.getDouble("fee");
				value.QYChangeMoney = rs.getDouble("qyChange");
				value.QYMoney = rs.getDouble("capital");
				value.date = tradeDate;
				value.fundio = rs.getDouble("fundio");
				value.firmtype = rs.getString("firmtype");
				value.MQYChangeMoney = rs.getDouble("mqyChange");
				result.add(value);
			}
			log(result.size() + "");
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			this.DAO.closeStatement(rs, state, conn);
		}
		return result;
	}

	public void log(String content) {
		Logger plog = Logger.getLogger("Processorlog");
		plog.debug(content);
	}
}
