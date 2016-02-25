package gnnt.trade.bank;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.CMBBankDAO;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmTradeStatus;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TradeDetailAccount;

public class CMBCapitalProcessor extends CapitalProcessor {
	CMBBankDAO DAO = null;

	public CMBCapitalProcessor() {
		try {
			this.DAO = BankDAOFactory.getCMBDAO();
		} catch (Exception e) {
			log("加载中行扩展DAO类失败\n" + Tool.getExceptionTrace(e));
		}
	}

	public ReturnValue checkSigning(CorrespondValue cv) {
		ReturnValue returnValue = new ReturnValue();
		returnValue = ifbankTrade(cv.bankID, cv.firmID, cv.contact, 3, 1);
		if (returnValue.result < 0L) {
			return returnValue;
		}
		Connection conn = null;
		if ((cv.bankID == null) || (cv.bankID.length() == 0) || (cv.contact == null) || (cv.contact.length() == 0)) {
			returnValue.result = -40001L;
			return returnValue;
		}
		if ((cv.firmID == null) || (cv.firmID.trim().length() <= 0)) {
			cv.firmID = getfirmid(cv.contact);
		}
		try {
			conn = this.DAO.getConnection();
			FirmValue fv = this.DAO.getFirm(cv.firmID, conn);
			if (fv == null) {
				returnValue.result = -40002L;
				log("银行账号注册，交易账号不存在，错误码=" + returnValue.result);
			} else if (this.DAO.getBank(cv.bankID, conn) == null) {
				returnValue.result = -40003L;
				log("银行账号注册，银行不存在，错误码=" + returnValue.result);
			} else if (this.DAO.getCorrespondList(" and bankID='" + cv.bankID + "' and firmID='" + cv.firmID + "' and isopen=1", conn).size() > 0) {
				returnValue.result = -40004L;
				log("银行账号注册，账号已注册，错误码=" + returnValue.result);
			} else {
				conn.setAutoCommit(false);
				returnValue = dataProcess.changeFirmIsOpen(cv.firmID, 1, cv.bankID, conn);
				ReturnValue localReturnValue1;
				if (returnValue.result < 0L) {
					return returnValue;
				}
				log("===========================>fv.firmName=[" + fv.firmName + "];cv.accountName=[" + cv.accountName + "]");
				log(fv.firmName.equals(cv.accountName) + "");
				if (!fv.contact.equals(cv.contact)) {
					returnValue.result = -40002L;
				} else if (!fv.firmName.equals(cv.accountName)) {
					log(fv.firmName + cv.accountName);
					returnValue.result = -40001L;
					returnValue.remark = "开户名称不一致";
				} else if (!fv.cardType.equals(cv.cardType)) {
					log(fv.cardType + cv.cardType);
					returnValue.result = -40001L;
					returnValue.remark = "证件类型不一致";
				} else if (!fv.card.equals(cv.card)) {
					log(fv.card + cv.card);
					returnValue.result = -40001L;
					returnValue.remark = "证件号码不一致";
				} else {
					returnValue.result = 0L;
				}
				return returnValue;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			returnValue.result = -40006L;
			log("银行账号注册SQLException，错误码=" + returnValue.result + "  " + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.result = -40007L;
			log("银行账号注册Exception，错误码=" + returnValue.result + "  " + Tool.getExceptionTrace(e));
		} finally {
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (Exception e) {
				log(Tool.getExceptionTrace(e));
			}
			this.DAO.closeStatement(null, null, conn);
		}
		try {
			conn.rollback();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		this.DAO.closeStatement(null, null, conn);

		return returnValue;
	}

	public long insertFirmTradeStatus(Vector<FirmTradeStatus> veFirmStatus, int ready) {
		long result = 0L;
		if ((ready == 1) || (ready == 2)) {
			if (veFirmStatus != null) {
				try {
					int size = veFirmStatus.size();
					for (int i = 0; i < size; i++) {
						FirmTradeStatus val = (FirmTradeStatus) veFirmStatus.get(i);
						System.out.println("客户协议状态（增量）对账文件B02：" + val.toString());

						String sql = "where BANKID='" + val.BankId + "' and trunc(COMPAREDATE)=to_date('" + val.compareDate
								+ "','yyyy-MM-dd') and BANKACC='" + val.BankAcc + "'";
						log("SQL语句：【" + sql + "】");
						Vector<FirmTradeStatus> veVal = this.DAO.getFirmTradeStatusList(sql);
						if ((veVal == null) || (veVal.size() == 0)) {
							if (ready == 2) {
								val.Status = "0";
							}
							this.DAO.addFirmTradeStatus(val);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
					result = -1L;
				} catch (Exception e) {
					e.printStackTrace();
					result = -1L;
				}
			} else {
				result = -1L;
			}
		}
		return result;
	}

	public long insertTradeDetailAccount(Vector<TradeDetailAccount> veDetail, int ready) {
		long result = 0L;
		if ((ready == 1) || (ready == 2)) {
			try {
				if (veDetail != null) {
					int size = veDetail.size();
					for (int i = 0; i < size; i++) {
						TradeDetailAccount val = (TradeDetailAccount) veDetail.get(i);

						String sql = "where BANKID='" + val.BankId + "' and BKSERIAL='" + val.BkSerial + "' and trunc(COMPAREDATE)=to_date('"
								+ val.compareDate + "','yyyy-MM-dd')";
						log("SQL语句：【" + sql + "】");
						Vector<TradeDetailAccount> vec = this.DAO.getTradeDetailAccountList(sql);
						if ((vec == null) || (vec.size() == 0)) {
							this.DAO.addTradeDetailAccount(val);
						}
					}
				} else {
					result = -1L;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				result = -1L;
			} catch (Exception e) {
				e.printStackTrace();
				result = -1L;
			}
		}
		return result;
	}
}
