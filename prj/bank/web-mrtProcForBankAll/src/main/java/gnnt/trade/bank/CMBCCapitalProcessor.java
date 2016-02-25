package gnnt.trade.bank;

import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.CMBCBankDAO;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;

public class CMBCCapitalProcessor extends CapitalProcessor {
	private static CMBCBankDAO DAO;

	public CMBCCapitalProcessor() {
		try {
			DAO = BankDAOFactory.getCMBCDAO();
		} catch (Exception e) {
			log("初始化建行DAO对象处理器失败：" + Tool.getExceptionTrace(e));
		}
	}

	public Vector<FirmBalance> sendCMBCQS(String bankID, Date tradeDate) {
		log("民生清算 适配器调用");
		Date qsDate = tradeDate;
		do {
			qsDate = Common.getLastDay(qsDate);
			try {
				boolean flag = DAO.getTradeDate(qsDate);
				if (flag)
					break;
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} while (true);
		Vector vfb = DAO.getFirmBalanceValue(tradeDate, qsDate, bankID);
		return vfb;
	}
}
