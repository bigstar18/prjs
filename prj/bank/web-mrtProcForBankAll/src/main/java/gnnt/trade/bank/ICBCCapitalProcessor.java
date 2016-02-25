package gnnt.trade.bank;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.dao.icbc.ICBCBankDAO;
import gnnt.trade.bank.data.icbc.vo.BankFirmRightValue;
import gnnt.trade.bank.data.icbc.vo.FirmRights;
import gnnt.trade.bank.data.icbc.vo.OpenOrDelFirmValue;
import gnnt.trade.bank.data.icbc.vo.ProperBalanceValue;
import gnnt.trade.bank.data.icbc.vo.TradingDetailsValue;
import gnnt.trade.bank.data.jsb.JsExDataImpl;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.BankTransferValue;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmInfo;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.MarketAcount;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfo;
import gnnt.trade.bank.vo.bankdz.jh.sent.FirmBalance;

public class ICBCCapitalProcessor extends CapitalProcessor {
	private static final int INMONEY = 0;
	private static final int OUTMONEY = 1;
	private static final int RATE = 2;
	private static ICBCBankDAO DAO;

	public ICBCCapitalProcessor() {
		try {
			DAO = BankDAOFactory.getICBCBDAO();
		} catch (Exception e) {
			log("初始化华夏DAO对象处理器失败：" + Tool.getExceptionTrace(e));
		}
	}

	public String getConfig(String key) {
		return Tool.getConfig(key);
	}

	public List<TradingDetailsValue> getChangeBalance(Vector<FirmBalance> list) {
		List<TradingDetailsValue> result = new ArrayList();
		log("----------开始组建交收数据文件-------------");
		long a = 0L;
		for (int i = 0; i < list.size(); i++) {
			TradingDetailsValue tdv = new TradingDetailsValue();
			tdv.maketNum = (a++);
			tdv.account = ((FirmBalance) list.get(i)).account;
			tdv.firmId = ((FirmBalance) list.get(i)).firmID;
			tdv.firmName = ((FirmBalance) list.get(i)).accountName;
			if (((FirmBalance) list.get(i)).MQYChangeMoney > 0.0D) {
				tdv.money = ((FirmBalance) list.get(i)).MQYChangeMoney;
				tdv.updown = "2";
			} else {
				tdv.money = (-((FirmBalance) list.get(i)).MQYChangeMoney);
				tdv.updown = "1";
			}
			result.add(tdv);
		}
		log("----------结束组建交收数据文件-------------");

		System.out.println("交收数据总共： " + result.size() + " 条");
		return result;
	}

	public List<FirmRights> getRightsList(Vector<FirmBalance> list) {
		List<FirmRights> rights = new ArrayList();
		log("----------开始组建交易商权益文件-------------");
		for (int i = 0; i < list.size(); i++) {
			FirmRights right = new FirmRights();
			right.firmId = ((FirmBalance) list.get(i)).firmID;
			right.firmName = ((FirmBalance) list.get(i)).accountName;
			right.account = ((FirmBalance) list.get(i)).account;
			right.money = ((FirmBalance) list.get(i)).QYMoney;
			rights.add(right);
		}
		log("----------结束组建交易商权益文件-------------");

		return rights;
	}

	public List<OpenOrDelFirmValue> getOpenOrDropMaket(String bankId, Date qdate) {
		List<OpenOrDelFirmValue> result = null;
		String openFilter = "  and isopen='1' ";
		String delFilter = "   and isopen='2' ";
		if ((bankId != null) && (!bankId.trim().equals(""))) {
			openFilter = openFilter + "and bankId='" + bankId + "' ";
			delFilter = delFilter + " and bankId='" + bankId + "' ";
		}
		if (qdate != null) {
			openFilter = openFilter + "and trunc(openTime)=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') ";
			delFilter = delFilter + "and trunc(delTime)=to_date('" + Tool.fmtDate(qdate) + "','yyyy-MM-dd') ";
		}
		log("----------是：开户销户信息-------------");

		Vector<CorrespondValue> openFirmVector = null;

		Vector<CorrespondValue> delFirmVector = null;
		try {
			openFirmVector = DAO.getCorrespondList(openFilter);

			delFirmVector = DAO.getCorrespondList(delFilter);
			if (openFirmVector == null) {
				openFirmVector = new Vector();
			}
			if (delFirmVector == null) {
				delFirmVector = new Vector();
			}
			int i = 1;
			for (CorrespondValue cv : openFirmVector) {
				OpenOrDelFirmValue odf = new OpenOrDelFirmValue();
				if (result == null) {
					result = new ArrayList();
				}
				odf.firmId = cv.contact;
				odf.firmName = cv.accountName;
				odf.maketNum = i;
				odf.openordel = "0";
				i++;
				result.add(odf);
			}
			for (CorrespondValue cv : delFirmVector) {
				OpenOrDelFirmValue odf = new OpenOrDelFirmValue();
				if (result == null) {
					result = new ArrayList();
				}
				odf.firmId = cv.contact;
				odf.firmName = cv.accountName;
				odf.maketNum = i;
				odf.openordel = "1";
				result.add(odf);
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (result == null) {
			result = new ArrayList();
		}
		return result;
	}

	public long getBankFirmRightValue(List<BankFirmRightValue> list) {
		long result = 0L;
		for (BankFirmRightValue bfrv : list) {
			List<BankFirmRightValue> bfrlist = DAO.getBankCapital(bfrv);
			if ((bfrlist != null) && (bfrlist.size() > 0)) {
				result = DAO.updateBankCapital(bfrv);
			} else {
				result = DAO.addBankCapital(bfrv);
			}
		}
		return result;
	}

	public long getProperBalanceValue(ProperBalanceValue pbv) {
		long result = 0L;
		List<ProperBalanceValue> list = DAO.getProperBalance(pbv);
		if ((list != null) && (list.size() > 0)) {
			result = DAO.updateProperBalance(pbv);
		} else {
			result = DAO.addProperBalance(pbv);
		}
		return result;
	}

	public int modfirmuser(FirmValue value, String bankid) {
		log("修改客户信息表以及客户和银行对应表的数据库\n" + value.toString());
		int result = -1;
		try {
			Vector<FirmInfo> finfos = DAO.getFirmInfo(value.firmID, value.firminfo.bankid, "ICBCAc");
			if (finfos.size() <= 0) {
				log("字段扩展表内不存在该交易商或银行的数据：新增一条数据\n");
				result = DAO.insertFirmInfo(value, "ICBCAc");
			} else {
				log("字段扩展表内存在该交易商或银行的数据：修改本条数据\n");
				result = DAO.modfirmuser(value, "ICBCAc");
			}
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}

	public ReturnValue bankTransfer(BankTransferValue bankTransferValue) {
		log("跨行汇拨");
		Connection conn = null;
		ReturnValue returnValue = new ReturnValue();
		TransferInfo trans = new TransferInfo();
		try {
			conn = DAO.getConnection();

			trans.status = 3;
			trans.bankid = bankTransferValue.payBankId;
			trans.money = bankTransferValue.money;
			trans.type = 0;
			trans.outAccount = bankTransferValue.payAcc;
			trans.inAccount = bankTransferValue.account;
			trans.outAccountName = bankTransferValue.payAccName;
			trans.inAccountName = bankTransferValue.Name;
			trans.funId = returnValue.funID;
			trans.actionId = DAO.getActionID() + "";
			DAO.addTransfer(trans);
		} catch (SQLException e) {
			e.printStackTrace();
			returnValue.result = -990001L;
			log("数据库异常");
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.result = -990001L;
			log("系统异常");
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return returnValue;
	}

	public ReturnValue auditBankTransfer(String actionId, String bankId, Boolean flag) {
		System.out.println(">>>>跨行汇拨审核>>>>时间：" + Tool.fmtTime(new Date()) + ">>>>");
		ReturnValue rv = new ReturnValue();
		TransferInfo transferInfo = null;
		BankTransferValue bankTransferValue = new BankTransferValue();
		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		Connection conn = null;
		try {
			Vector<TransferInfo> list = DAO.getTransferList(" and actionid=" + actionId);
			ReturnValue localReturnValue1;
			for (int i = 0; i < list.size(); i++) {
				TransferInfo val = (TransferInfo) list.get(i);

				transferInfo = val;

				bankTransferValue.payBankId = transferInfo.bankid;
				bankTransferValue.payAcc = transferInfo.outAccount;
				bankTransferValue.payAccName = transferInfo.outAccountName;
				bankTransferValue.account = transferInfo.inAccount;
				bankTransferValue.Name = transferInfo.inAccountName;
				bankTransferValue.VldDt = "";
				bankTransferValue.Pwd = "";
				bankTransferValue.RegDt = "";
				bankTransferValue.St = 0;
				bankTransferValue.money = transferInfo.money;
				bankTransferValue.actionId = Long.valueOf(actionId).longValue();
				Vector<MarketAcount> market = DAO.getMarketAcount(" and account='" + bankTransferValue.payAcc + "' and bankId='" + bankId + "'");
				bankTransferValue.bankNum = ((MarketAcount) market.get(0)).bankCode;
				rv.actionId = Long.valueOf(transferInfo.actionId).longValue();
				rv.bankTime = Tool.fmtTime(curTime);
				rv.funID = transferInfo.funId;
				if ((transferInfo.status == 0) || (transferInfo.status == 1)) {
					rv.result = -20042L;
					rv.remark = ("本条记录[" + actionId + "]已经有人处理");
					log(rv.remark);
					localReturnValue1 = rv;
					return localReturnValue1;
				}
			}
			if (transferInfo == null) {
				rv.result = -930000L;
				rv.remark = ("未查询到流水[" + actionId + "]信息");
				localReturnValue1 = rv;
				return localReturnValue1;
			}
			if (flag.booleanValue()) {
				BankAdapterRMI bankadapter = getAdapter(bankId);
				if (bankadapter == null) {
					rv.result = -920000L;
				} else {
					try {
						rv = bankadapter.bankTransfer(bankTransferValue);

						log("返回结果：" + rv.result);
						if (0L == rv.result) {
							transferInfo.status = 0;
						} else if (5L == rv.result) {
							transferInfo.status = 2;
						} else {
							transferInfo.status = 1;
						}
						conn = DAO.getConnection();
						conn.setAutoCommit(false);
						DAO.modTransfer(actionId, rv.funID, transferInfo.status, curTime, conn);
						log(actionId + "跨行汇拨流水银行确认审核通过");
						conn.commit();
					} catch (RemoteException e) {
						log(Tool.getExceptionTrace(e));
					} catch (SQLException e) {
						conn.rollback();
						log(Tool.getExceptionTrace(e));
					} catch (ClassNotFoundException e) {
						log(Tool.getExceptionTrace(e));
					} finally {
						conn.setAutoCommit(true);
					}
				}
			} else {
				try {
					conn = DAO.getConnection();
					conn.setAutoCommit(false);
					DAO.modTransfer(actionId, "", 1, curTime, conn);

					log(actionId + "跨行汇拨银行确认审核拒绝");
					conn.commit();
				} catch (SQLException e) {
					conn.rollback();
					log(actionId + "跨行汇拨审核转账SQLException，错误码=" + rv + "  " + Tool.getExceptionTrace(e));
					throw e;
				} finally {
					conn.setAutoCommit(true);
				}
			}
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return rv;
	}

	public ReturnValue send(Date tradeDate) {
		return new JsExDataImpl().send(tradeDate);
	}

	public Vector<CorrespondValue> getCorrespondList(String filter) {
		Vector<CorrespondValue> result = new Vector();
		try {
			result = DAO.getCorrespondList(filter);
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		return result;
	}

	public FirmValue getFirmValue(FirmValue value) {
		FirmValue firmers = new FirmValue();
		try {
			firmers = DAO.getFirmValue(value);
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		return firmers;
	}

	public ReturnValue ifQuitFirm(String firmID, String bankID) {
		ReturnValue rv = new ReturnValue();
		String truefirmis = getfirmid(firmID);
		if (truefirmis == null) {
			rv.result = -920013L;
			return rv;
		}
		rv = ifbankTrade(bankID, truefirmis, null, 3, 0);
		if (rv.result != 0L) {
			log("验证失败，失败原因：" + rv.result + "失败说明：" + rv.remark);
			return rv;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			Vector<CapitalValue> capitals = DAO.getCapitalInfoList(
					" and bankID='" + bankID + "' and contact='" + firmID + "' and createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1  ",
					conn);
			if ((capitals != null) && (capitals.size() > 0)) {
				rv.result = -941002L;
				log("验证失败，失败原因：当日有转账交易不能解约");
				return rv;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			log("验证失败，失败原因：SQLException异常");
			rv.result = -1L;
			return rv;
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			log("验证失败，失败原因：ClassNotFoundException异常");
			rv.result = -1L;
			return rv;
		}
		try {
			conn.setAutoCommit(false);
			rv.result = dataProcess.ifFirmDelAccount(truefirmis, bankID, conn);
			ReturnValue localReturnValue1;
			if (rv.result < 0L) {
				rv.remark = "账户未终止";
				return rv;
			}
			rv = dataProcess.changeFirmIsOpen(truefirmis, 2, bankID, conn);
			if (rv.result < 0L) {
				return rv;
			}
		} catch (Exception e1) {
			log("判断账号解约异常=" + Tool.getExceptionTrace(e1));
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				log("判断账号解约,数据回滚异常=" + Tool.getExceptionTrace(e));
			}
			DAO.closeStatement(null, null, conn);
		}
		try {
			conn.rollback();
		} catch (SQLException e) {
			log("判断账号解约,数据回滚异常=" + Tool.getExceptionTrace(e));
		}
		DAO.closeStatement(null, null, conn);

		return rv;
	}

	public String getfirmid(String contact) {
		log("通过contact[" + contact + "]获取firmid");
		String firmids = null;
		Vector<FirmValue> firmivalues = null;
		try {
			firmivalues = DAO.getFirmList("and contact ='" + contact + "'");
		} catch (SQLException e) {
			log(Tool.getExceptionTrace(e));
		} catch (ClassNotFoundException e) {
			log(Tool.getExceptionTrace(e));
		}
		if ((firmivalues != null) && (firmivalues.size() > 0)) {
			firmids = ((FirmValue) firmivalues.get(0)).firmID;
		}
		log("获得的Firmid[" + firmids + "]");
		return firmids;
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
			conn = DAO.getConnection();
			FirmValue fv = DAO.getFirm(cv.firmID, conn);
			if (fv == null) {
				returnValue.result = -40002L;
				log("银行账号注册，交易账号不存在，错误码=" + returnValue.result);
			} else if (DAO.getBank(cv.bankID, conn) == null) {
				returnValue.result = -40003L;
				log("银行账号注册，银行不存在，错误码=" + returnValue.result);
			} else if (DAO.getCorrespondList(" and bankID='" + cv.bankID + "' and firmID='" + cv.firmID + "' and isopen=1", conn).size() > 0) {
				returnValue.result = -40004L;
				log("银行账号注册，账号已注册，错误码=" + returnValue.result);
			} else {
				conn.setAutoCommit(false);
				returnValue = dataProcess.changeFirmIsOpen(cv.firmID, 1, cv.bankID, conn);
				ReturnValue localReturnValue1;
				if (returnValue.result < 0L) {
					return returnValue;
				}
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
			DAO.closeStatement(null, null, conn);
		}
		try {
			conn.rollback();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		}
		DAO.closeStatement(null, null, conn);

		return returnValue;
	}
}
