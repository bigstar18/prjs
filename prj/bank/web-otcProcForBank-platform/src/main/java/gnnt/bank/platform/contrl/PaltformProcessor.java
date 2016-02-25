package gnnt.bank.platform.contrl;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.bank.platform.CapitalProcessor;
import gnnt.bank.platform.util.Arith;
import gnnt.bank.platform.util.Tool;
import gnnt.bank.platform.vo.CapitalValueMoney;
import gnnt.bank.platform.vo.CheckMessage;
import gnnt.bank.platform.vo.RequestMsg;
import gnnt.bank.trade.rmi.TradeProcessorRMI;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.Firm;
import gnnt.trade.bank.vo.FirmBankFunds;
import gnnt.trade.bank.vo.FirmID2SysFirmID;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.InOutMoney;
import gnnt.trade.bank.vo.OutMoneyVO;
import gnnt.trade.bank.vo.QueryTradeData;
import gnnt.trade.bank.vo.RZQS;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.SystemMessage;
import gnnt.trade.bank.vo.TransferInfoValue;

public class PaltformProcessor extends CapitalProcessor {
	public ReturnValue signedContract(CorrespondValue corr) {
		Tool.log("处理交易系统[" + corr.systemID + "]的交易商[" + corr.sysFirmID + "]和银行[" + corr.bankID + "]的签约");
		ReturnValue result = new ReturnValue();

		Vector<FirmID2SysFirmID> vecF2f = null;
		try {
			vecF2f = DAO.getFirmID2SysFirmID(
					" where sysFirmID='" + corr.sysFirmID + "' and systemID='" + corr.systemID + "' and bankID='" + corr.bankID + "'");
		} catch (SQLException e) {
			Tool.log("查询交易商对应关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "平台系统异常";
			return result;
		}
		Connection conn = null;
		label3553: try {
			conn = DAO.getConnection();
			conn.setAutoCommit(false);

			Firm fm = DAO.getMFirmByFirmId(corr.firmID);
			ReturnValue localReturnValue1;
			if (fm == null) {
				result = synchroFirmToFinance(corr.firmID, conn);
				if (result.result != 0L) {
					Tool.log("向财务模块同步平台号[" + corr.firmID + "]的信息失败");
					result.result = -1L;
					result.remark = "同步信息到财务模块失败";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
			}
			if ((corr.fundsPwd == null) || ("".equals(corr.fundsPwd))) {
				Tool.log("传入的资金密码为空");
				result.result = -1L;
				result.remark = "资金密码为空";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}
			result = isPassword(corr.firmID, corr.fundsPwd, conn);
			if (result.result == 1L) {
				Tool.log("写入平台号[" + corr.firmID + "]的资金密码");
				result = modPwd(corr.firmID, null, corr.fundsPwd, conn);
			}
			if (result.result != 0L) {
				Tool.log("平台号[" + corr.firmID + "]在财务模块已经存在，校验资金密码失败");
				result.result = -1L;
				result.remark = "资金密码校验错";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if ((vecF2f != null) && (vecF2f.size() > 0)) {
				if (!((FirmID2SysFirmID) vecF2f.get(0)).firmID.equals(corr.firmID)) {
					Tool.log("平台记录的该交易商对应的平台号[" + ((FirmID2SysFirmID) vecF2f.get(0)).firmID + "]和请求传入的平台号[" + corr.firmID + "]不相符");
					result.result = -1L;
					result.remark = "平台号不符";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
			} else {
				Vector<FirmID2SysFirmID> vecf2f2 = DAO.getFirmMapping(" where platformID='" + corr.firmID + "'");
				if ((vecf2f2 == null) || (vecf2f2.size() <= 0)) {
					Tool.log("P_MAPPINGFIRM表中没有相关数据，只插入一条数据");
					FirmID2SysFirmID f2f = new FirmID2SysFirmID();
					f2f.firmID = corr.firmID;
					f2f.sysFirmID = corr.sysFirmID;
					f2f.systemID = corr.systemID;
					f2f.bankID = corr.bankID;
					f2f.defaultSystem = corr.systemID;

					DAO.addFirmMapping(f2f, conn);
					result.result = DAO.addFirmID2SysFirmID(f2f, conn);
					if (result.result != 1L) {
						Tool.log("添加交易系统交易商信息和平台号、银行的对应关系失败");
						result.result = -1L;
						result.remark = "增加交易商和平台号、银行的对应关系失败";
						conn.rollback();
						localReturnValue1 = result;
						return localReturnValue1;
					}
				} else {
					for (FirmID2SysFirmID f2sf : vecf2f2) {
						FirmID2SysFirmID f2f = new FirmID2SysFirmID();
						f2f.firmID = corr.firmID;
						f2f.sysFirmID = f2sf.sysFirmID;
						f2f.systemID = f2sf.systemID;
						f2f.bankID = corr.bankID;
						f2f.defaultSystem = corr.systemID;
						result.result = DAO.addFirmID2SysFirmID(f2f, conn);
						if (result.result != 1L) {
							Tool.log("添加交易系统交易商信息和平台号、银行的对应关系失败");
							result.result = -1L;
							result.remark = "增加交易商和平台号、银行的对应关系失败";
							conn.rollback();
							localReturnValue1 = result;
							return localReturnValue1;
						}
					}
				}
			}
			Vector<CorrespondValue> vecCorrFirmID = DAO.getCorrespondList(" where firmID='" + corr.firmID + "' and bankID='" + corr.bankID + "'");
			if ((vecCorrFirmID != null) && (vecCorrFirmID.size() > 0)) {
				Tool.log("已经存在平台号[" + corr.firmID + "]和银行[" + corr.bankID + "]的对应关系");
				CorrespondValue corrsv = (CorrespondValue) vecCorrFirmID.get(0);
				if ((!corrsv.account.equals(corr.account)) || (!corrsv.accountName.equals(corr.accountName))
						|| (!corrsv.cardType.equals(corr.cardType)) || (!corrsv.card.equals(corr.card))) {
					Tool.log("平台已经存在的对应关系中的银行账号[" + corrsv.account + "]银行账户名[" + corrsv.accountName + "]证件类型[" + corrsv.cardType + "]证件号["
							+ corrsv.card + "]和传入的银行账号[" + corrsv.account + "]银行账户名[" + corrsv.accountName + "]证件类型[" + corrsv.cardType + "]证件号["
							+ corrsv.card + "]不完全相符");
					result.result = -1L;
					result.remark = "银行账户信息不相符";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
				if (corrsv.isOpen == 1) {
					Tool.log("平台号[" + corr.firmID + "]签约号[" + corrsv.contact + "]和银行[" + corr.bankID + "]的对应关系已经是签约状态");
					result.result = 0L;
					result.remark = ("平台号[" + corr.firmID + "]在该银行已经用签约号[" + corrsv.contact + "]签约成功");
					conn.commit();
					localReturnValue1 = result;
					return localReturnValue1;
				}
			} else {
				Vector<CorrespondValue> vecCorrCard = DAO.getCorrespondList(" where account='" + corr.account + "' and bankID='" + corr.bankID + "'");
				if ((vecCorrCard != null) && (vecCorrCard.size() > 0)) {
					Tool.log("银行[" + corr.bankID + "]的银行卡[" + corr.account + "]已经存在签约关系记录中");
					result.result = -1L;
					result.remark = "该银行卡已经签约过";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
				corr.status = 1;
				corr.isOpen = 0;

				Object vecCorr2 = DAO.getCorrespondList(" where contact='" + corr.firmID + "' and bankID='" + corr.bankID + "'");
				if ((vecCorr2 == null) || (((Vector) vecCorr2).size() <= 0)) {
					Tool.log("和平台号[" + corr.firmID + "]相同的签约号在银行[" + corr.bankID + "]不存在签约关系，签约号可以使用[" + corr.firmID + "]");
					corr.contact = corr.firmID;
				} else {
					Tool.log("和平台号[" + corr.firmID + "]相同的签约号在银行[" + corr.bankID + "]已经存在签约关系，签约号使用[" + corr.firmID + "QYN" + corr.bankID + "]");
					corr.contact = (corr.firmID + "QYN" + corr.bankID);
				}
				result.result = DAO.addCorrespond(corr, conn);
				if (result.result != 0L) {
					Tool.log("添加平台号和银行的签解约关系失败");
					result.result = -1L;
					result.remark = "添加平台号和银行的对应关系失败";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
			}
			Vector<FirmBankFunds> vecFBF = DAO.getFirmBankFunds(" and f.firmID='" + corr.firmID + "' and f.bankcode='" + corr.bankID + "'", conn);
			Vector vecFBF2 = DAO.getFirmBankFunds(" and f.firmID='" + corr.firmID + "'", conn);
			if ((vecFBF == null) || (vecFBF.size() <= 0)) {
				Tool.log("平台号[" + corr.firmID + "]还没有签约银行[" + corr.bankID + "]");

				FirmBankFunds firmBankFunds = new FirmBankFunds();
				firmBankFunds.firmID = corr.firmID;
				firmBankFunds.bankID = corr.bankID;
				if ((vecFBF2 != null) && (((Vector) vecFBF2).size() > 0)) {
					firmBankFunds.primaryBankFlag = 2;
				} else {
					firmBankFunds.primaryBankFlag = 1;
				}
				result.result = DAO.addFirmBankFunds(firmBankFunds, conn);
				if (result.result != 1L) {
					Tool.log("添加平台号[" + corr.firmID + "]对应的银行为辅银行标志数据失败");
					result.result = -1L;
					result.remark = "记录主辅银行失败";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
			}
			BankAdapterRMI bankadapter = getAdapter(corr.bankID);
			if (bankadapter == null) {
				Tool.log("获取银行[" + corr.bankID + "]的适配器RMI失败");
				result.result = -1L;
				result.remark = "链接适配器失败";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}
			result = bankadapter.rgstAccountQuery(corr);
			Object vecOpen;
			if (result.result == 0L) {
				Tool.log("平台号[" + corr.firmID + "]签约号[" + corr.contact + "]和银行[" + corr.bankID + "]签约成功");
				result.remark = "签约成功";
				conn.commit();

				Vector<FirmID2SysFirmID> vecFirmID2FirmID = DAO
						.getFirmID2SysFirmID(" where firmID='" + corr.firmID + "' and bankID='" + corr.bankID + "'", conn);
				if ((vecFirmID2FirmID == null) || (vecFirmID2FirmID.size() <= 0)) {
					Tool.log("平台号和交易商的对应关系表中没有找到平台号[" + corr.firmID + "]有关银行[" + corr.bankID + "]的记录，不能通知交易系统保存平台号");
				} else {
					for (FirmID2SysFirmID ff : vecFirmID2FirmID) {
						noticePlatFirmID(ff.systemID, ff.firmID, ff.sysFirmID);
					}
				}
				corr.isOpen = 1;
				corr.status = 0;
				if (DAO.modCorrespond(corr, conn) == 0) {
					conn.commit();
				} else {
					Tool.log("签约成功后，修改该条记录的签约状态为成功状态失败");
					result.remark = "银行签约成功，系统修改签约状态为成功失败，请联系客服人员";
				}
				if ((vecFBF2 != null) && (vecFBF2.size() > 0)) {
					Vector<FirmBankFunds> vecFBF3 = DAO.getFirmBankFunds(" and f.firmID='" + corr.firmID + "' and f.primaryBankFlag=1", conn);
					if ((vecFBF3 == null) || (vecFBF3.size() <= 0) || (vecFBF3.size() > 1)) {
						Tool.log("没有找到平台号[" + corr.firmID + "]签约主银行的记录，或者找到多条记录，请注意！！！！！！！！！！！！");
						result.result = 0L;
						result.remark = "银行签约成功，平台处理失败";
					} else {
						vecOpen = DAO.getCorrespondList(
								" where firmid='" + corr.firmID + "' and bankid='" + ((FirmBankFunds) vecFBF3.get(0)).bankID + "'", conn);
						if ((vecOpen == null) || (((Vector) vecOpen).size() != 1)) {
							Tool.log("银行账户绑定关系表存在多条相同记录或缺失记录");
							result.result = 0L;
							result.remark = "银行签约成功，平台处理失败";
						} else if (((CorrespondValue) ((Vector) vecOpen).get(0)).isOpen == 1) {
							Tool.log("平台号[" + corr.firmID + "]签约的银行[" + corr.bankID + "]为次银行，其已经签约了主银行[" + ((FirmBankFunds) vecFBF3.get(0)).bankID
									+ "]");
							result.result = 0L;
							result.remark = "签约成功";
						} else {
							Tool.log("平台号[" + corr.firmID + "]之前签约的主银行[" + ((FirmBankFunds) vecFBF3.get(0)).bankID + "]没有最终签约成功，将本次签约的银行["
									+ corr.bankID + "]设置为主银行，原有主银行修改为次银行");

							FirmBankFunds oldMainBank = (FirmBankFunds) vecFBF3.get(0);
							oldMainBank.primaryBankFlag = 2;
							if (DAO.modFirmBankFunds(oldMainBank, conn) != 1) {
								Tool.log("将原有主银行[" + oldMainBank.bankID + "]修改为次银行失败");
								conn.rollback();
								result.result = 0L;
								result.remark = "银行签约成功，平台处理失败";
							} else {
								Vector<FirmBankFunds> vecFBF4 = DAO
										.getFirmBankFunds(" and f.firmID='" + corr.firmID + "' and f.bankcode='" + corr.bankID + "'", conn);
								FirmBankFunds newMainBank = (FirmBankFunds) vecFBF4.get(0);
								newMainBank.primaryBankFlag = 1;
								if (DAO.modFirmBankFunds(newMainBank, conn) != 1) {
									Tool.log("将新签约成功银行[" + newMainBank.bankID + "]修改为主银行失败");
									conn.rollback();
									result.result = 0L;
									result.remark = "银行签约成功，平台处理失败";
								} else {
									Tool.log("将新签约成功银行[" + newMainBank.bankID + "]修改为主银行成功");
									conn.commit();
									result.result = 0L;
									result.remark = "签约成功";
								}
							}
						}
					}
				}
				if ((result.funID != null) && (!"".equals(result.funID))) {
					DAO.saveAccount1(corr.firmID, corr.bankID, result.funID, conn);
					conn.commit();
					break label3553;
				}
			} else if (result.result == 5L) {
				Tool.log("银行[" + corr.bankID + "]不支持市场端签约");
				result.remark = "该银行只支持从银行端发起签约，请从银行端发起";
				conn.commit();

				Vector<FirmID2SysFirmID> vecFirmID2FirmID = DAO
						.getFirmID2SysFirmID(" where firmID='" + corr.firmID + "' and bankID='" + corr.bankID + "'", conn);
				if ((vecFirmID2FirmID == null) || (vecFirmID2FirmID.size() <= 0)) {
					Tool.log("平台号和交易商的对应关系表中没有找到平台号[" + corr.firmID + "]有关银行[" + corr.bankID + "]的记录，不能通知交易系统保存平台号");
				} else {
					for (vecOpen = vecFirmID2FirmID.iterator(); ((Iterator) vecOpen).hasNext();) {
						FirmID2SysFirmID ff = (FirmID2SysFirmID) ((Iterator) vecOpen).next();
						noticePlatFirmID(ff.systemID, ff.firmID, ff.sysFirmID);
					}
				}
			} else {
				Tool.log("平台号[" + corr.firmID + "]签约号[" + corr.contact + "]和银行[" + corr.bankID + "]签约失败，失败原因：" + result.remark);
				if ((result.remark == null) || ("".equals(result.remark))) {
					result.remark = "签约失败，银行处理失败";
				}
				conn.rollback();
			}
		} catch (Exception e) {
			Tool.log("系统异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常,数据回滚";
			try {
				conn.rollback();
			} catch (SQLException e1) {
				Tool.log("回滚数据异常：" + Tool.getExceptionTrace(e1));
			}
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue noticePlatFirmID(String systemID, String platFirmID, String firmID) {
		Tool.log("通知交易系统[" + systemID + "]维护该系统交易商[" + firmID + "]和平台号[" + platFirmID + "]的对应关系");
		ReturnValue result = new ReturnValue();

		TradeProcessorRMI tradeProc = getTradeCaptialProcessor(systemID);
		if (tradeProc == null) {
			Tool.log("获取交易系统[" + systemID + "]的处理器RMI链接失败");
			result.result = -1L;
			result.remark = "获取交易系统处理器失败";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("noticePlatFirmID");
		req.setParams(new Object[] { platFirmID, firmID });
		try {
			result = tradeProc.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用交易系统处理器方法异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		}
		return result;
	}

	public ReturnValue getCorrespondValues(String filter) {
		ReturnValue result = new ReturnValue();
		Vector<CorrespondValue> vec = getCorrespondValue(filter);
		if (vec == null) {
			result.result = -1L;
			result.remark = "查询失败";
			return result;
		}
		result.result = 0L;
		result.remark = "查询成功";
		result.msg = new Object[] { vec };
		return result;
	}

	public ReturnValue getPlatformMsg(String systemID, String sysFirmID) {
		Tool.log("查询交易系统[" + systemID + "]的交易商[" + sysFirmID + "]的在平台的记录");
		ReturnValue result = new ReturnValue();
		Vector<FirmID2SysFirmID> vecF2f = null;
		try {
			vecF2f = DAO.getFirmMapping(" where firmID='" + sysFirmID + "' and moduleID=" + systemID);
		} catch (SQLException e) {
			Tool.log("查询交易商和平台号对应关系异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecF2f != null) && (vecF2f.size() > 0)) {
			String firmID = ((FirmID2SysFirmID) vecF2f.get(0)).firmID;
			Tool.log("查询到交易系统[" + systemID + "]的交易商[" + sysFirmID + "]对应的平台号是[" + firmID + "]");
			result.result = 0L;
			result.remark = "查询成功";
			result.msg = new Object[] { firmID };
			return result;
		}
		Tool.log("没有查询到P_MAPPINGFIRM表中有对应的关系，系统自动生成一个平台号");
		String firmID = getNewFirmID();
		result.result = 0L;
		result.remark = "生成平台号成功";
		result.msg = new Object[] { firmID };
		return result;
	}

	public ReturnValue modCorrespondValue(CorrespondValue corrLast, CorrespondValue corr) {
		Tool.log("交易系统[" + corr.systemID + "]交易商[" + corr.sysFirmID + "]修改银行账户信息");
		ReturnValue result = new ReturnValue();

		result = isPassword(corr.firmID, corr.fundsPwd);
		if (result.result != 0L) {
			Tool.log("资金密码校验失败");
			result.result = -1L;
			result.remark = "资金密码不正确";
			return result;
		}
		Vector<CorrespondValue> vecOld = null;
		try {
			vecOld = DAO.getCorrespondList(" where firmID='" + corrLast.firmID + "' and bankID='" + corrLast.bankID + "'");
		} catch (Exception e) {
			Tool.log("查询平台号和银行的绑定关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "平台系统异常";
			return result;
		}
		if ((vecOld == null) || (vecOld.size() <= 0)) {
			Tool.log("没有查询到平台号[" + corrLast.firmID + "]和银行[" + corrLast.bankID + "]的绑定关系");
			result.result = -1L;
			result.remark = "平台号和银行的绑定关系不存在";
			return result;
		}
		CorrespondValue corrOld = (CorrespondValue) vecOld.get(0);
		if ((corrOld.bankID.equals(corr.bankID)) && (corr.accountName.equals(corrOld.accountName)) && (corr.account.equals(corrOld.account))
				&& (corr.cardType.equals(corrOld.cardType)) && (corr.card.equals(corrOld.card))) {
			Tool.log("修改后的银行账户数据和修改前的银行账户数据全部一致，未执行修改");
			result.result = -1L;
			result.remark = "修改后的银行账户信息和原有银行账户信息完全一致，未执行修改操作";
			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			conn.setAutoCommit(false);
			CorrespondValue corrNew = (CorrespondValue) DAO
					.getCorrespondList(" where firmID='" + corrLast.firmID + "' and bankID='" + corrLast.bankID + "'").get(0);
			corrNew.bankID = corr.bankID;
			corrNew.accountName = corr.accountName;
			corrNew.account = corr.account;
			corrNew.cardType = corr.cardType;
			corrNew.card = corr.card;
			ReturnValue localReturnValue1;
			if (!corrOld.bankID.equals(corrNew.bankID)) {
				Vector<CorrespondValue> corrCheck = DAO.getCorrespondList(
						" where firmID='" + corr.firmID + "' and contact='" + corrOld.contact + "' and bankID='" + corr.bankID + "'", conn);
				if ((corrCheck != null) && (corrCheck.size() > 0)) {
					corrNew.contact = (corrNew.firmID + "QYN" + corr.bankID);
				}
				Vector<FirmID2SysFirmID> vecF2f = DAO.getFirmID2SysFirmID(" where firmID='" + corrLast.firmID + "' and sysfirmid='"
						+ corrLast.sysFirmID + "' and systemID='" + corrLast.systemID + "' and bankID='" + corrLast.bankID + "'", conn);
				if ((vecF2f == null) || (vecF2f.size() <= 0)) {
					Tool.log("在F_B_FIRMID_SYSFIRMID表中没有找到交易系统[" + corrLast.systemID + "]的交易商[" + corrLast.sysFirmID + "]和平台号[" + corrLast.firmID
							+ "]有关银行[" + corrLast.bankID + "]的记录");
					result.result = -1L;
					result.remark = "没有找到旧记录";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
				FirmID2SysFirmID f2fOld = (FirmID2SysFirmID) vecF2f.get(0);
				FirmID2SysFirmID f2f = new FirmID2SysFirmID();
				f2f.firmID = f2fOld.firmID;
				f2f.systemID = f2fOld.systemID;
				f2f.sysFirmID = f2fOld.sysFirmID;
				f2f.bankID = corr.bankID;
				f2f.defaultSystem = f2fOld.defaultSystem;
				result.result = DAO.modFirmID2SysFirmID2(f2fOld, f2f, conn);
				if (result.result != 1L) {
					Tool.log("修改对应关系中的银行编号为新的银行代码失败");
					result.result = -1L;
					result.remark = "更新对应关系中的银行编号失败";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
			}
			result.result = DAO.modBankAccount(corrOld, corrNew, conn);
			if (result.result != 1L) {
				Tool.log("修改银行账户信息失败");
				result.result = -1L;
				result.remark = "修改银行账户信息失败";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (corrOld.isOpen == 1) {
				if (!corrOld.bankID.equals(corrNew.bankID)) {
					Tool.log("该平台号已经和原有银行[" + corrOld.bankID + "]签约，不允许更换成银行[" + corrNew.bankID + "]");
					result.result = -1L;
					result.remark = "原有银行已经签约，不允许更换银行";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
				BankAdapterRMI bankadapter = getAdapter(corr.bankID);
				if (bankadapter == null) {
					Tool.log("获取银行[" + corr.bankID + "]的适配器RMI链接失败");
					result.result = -1L;
					result.remark = "链接该银行的适配器失败";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
				result = bankadapter.modAccount(corrOld, corrNew);
				if (result.result == 5L) {
					Tool.log("银行[" + corrNew.bankID + "]不支持市场端改约");
					result.result = -1L;
					result.remark = "该银行已经签约的平台号不允许修改银行账户信息";
				}
			} else {
				BankAdapterRMI bankadapter = getAdapter(corrNew.bankID);
				if (bankadapter == null) {
					Tool.log("获取银行[" + corrNew.bankID + "]的适配器RMI失败");
					result.result = -1L;
					result.remark = "链接适配器失败";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
				Tool.log("修改信息后直接向银行发起签约");
				result = bankadapter.rgstAccountQuery(corrNew);
				if (result.result == 0L) {
					Tool.log("平台号[" + corrNew.firmID + "]签约号[" + corrNew.contact + "]和银行[" + corrNew.bankID + "]签约成功");
					result.remark = "修改成功，修改后自动向该银行发起签约成功";
					corr.isOpen = 1;
					corr.status = 0;
					DAO.modCorrespond(corr, conn);
				} else if (result.result == 5L) {
					Tool.log("银行[" + corr.bankID + "]不支持市场端签约");
					result.result = 0L;
					result.remark = "修改信息成功，修改后没有自动发起签约，该银行不支持市场端签约，请从银行端发起签约";
				} else {
					Tool.log("平台号[" + corr.firmID + "]签约号[" + corr.contact + "]和银行[" + corr.bankID + "]签约失败，失败原因：" + result.remark);
					if ((result.remark == null) || ("".equals(result.remark))) {
						result.remark = "签约失败，银行处理失败";
					}
				}
			}
			if (result.result == 0L) {
				conn.commit();
			} else {
				result.result = -1L;
				if ((result.remark == null) || ("".equals(result.remark))) {
					result.remark = "修改失败";
				}
				conn.rollback();
			}
		} catch (Exception e) {
			Tool.log("修改账户信息，平台处理器异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			try {
				conn.rollback();
			} catch (SQLException e1) {
				Tool.log("系统异常，回滚数据异常：" + Tool.getExceptionTrace(e1));
			}
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue delCorrMarket(CorrespondValue corr) {
		Tool.log("市场端解约，平台号[" + corr.firmID + "]、交易系统交易商[" + corr.sysFirmID + "]、交易系统编号[" + corr.systemID + "]、银行[" + corr.bankID + "]");
		ReturnValue result = new ReturnValue();

		result = isPassword(corr.firmID, corr.fundsPwd);
		if (result.result != 0L) {
			Tool.log("资金密码错误");
			result.result = -1L;
			result.remark = "资金密码错误";
			return result;
		}
		if (!canDelCorr(corr)) {
			Tool.log("不满足解约条件，余额不为零或者当日有转账记录");
			result.result = -1L;
			result.remark = "余额不为零，或者当日有转账不允许解约";
			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			conn.setAutoCommit(false);

			Vector<CorrespondValue> vecOpen = DAO.getCorrespondList(" where firmID='" + corr.firmID + "'", conn);
			ReturnValue localReturnValue1;
			if ((vecOpen == null) || (vecOpen.size() <= 0)) {
				Tool.log("没有查询到平台号[" + corr.firmID + "]签约的银行信息");
				result.result = -1L;
				result.remark = "没有查询到签约信息";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (vecOpen.size() == 1) {
				Tool.log("平台号[" + corr.firmID + "]只签约了该银行一个银行，删除其资金密码");
				FirmValue fv = DAO.getFirm(corr.firmID, conn);
				if (fv == null) {
					Tool.log("没有查询到平台号[" + corr.firmID + "]的相关信息");
					result.result = -1L;
					result.remark = "验证信息失败";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
				fv.password = "";
				result.result = DAO.modFirm(fv, conn);
				if (result.result != 0L) {
					Tool.log("删除资金密码失败");
					result.result = -1L;
					result.remark = "处理资金密码失败";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
			}
			Vector<FirmID2SysFirmID> vecF2f = DAO.getFirmID2SysFirmID(" where firmID='" + corr.firmID + "' and bankID='" + corr.bankID + "'", conn);
			if ((vecF2f == null) || (vecF2f.size() <= 0)) {
				Tool.log("没有查询到平台号[" + corr.firmID + "]和交易商有关银行[" + corr.bankID + "]的对应关系");
				result.result = -1L;
				result.remark = "没有查询到对应关系";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}
			for (FirmID2SysFirmID f2f : vecF2f) {
				result.result = DAO.delFirmID2SysFirmID(f2f, conn);
				if (result.result != 1L) {
					Tool.log("删除平台交易商[" + f2f.firmID + "]和子系统[" + f2f.systemID + "]、子系统交易商[" + f2f.sysFirmID + "]、银行[" + f2f.bankID + "]的绑定关系失败");
					result.result = -1L;
					result.remark = "解约失败";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
			}
			Vector<FirmBankFunds> vecFFV = DAO.getFirmBankFunds(" and f.firmid='" + corr.firmID + "' and f.bankcode='" + corr.bankID + "'", conn);
			if ((vecFFV == null) || (vecFFV.size() <= 0)) {
				Tool.log("平台号银行资金表F_FIRMBANKFUNDS中没有找到平台号[" + corr.firmID + "]和银行[" + corr.bankID + "]的签约关系");
				result.result = -1L;
				result.remark = "资金平台签约信息不合法";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}
			Vector<FirmBankFunds> vecFFV2 = DAO.getFirmBankFunds(" and f.firmid='" + corr.firmID + "' and f.primarybankflag=2", conn);
			if (DAO.delFirmBankFunds((FirmBankFunds) vecFFV.get(0), conn) != 1) {
				Tool.log("删除平台号银行资金表F_FIRMBANKFUNDS中平台号[" + corr.firmID + "]和银行[" + corr.bankID + "]的签约关系失败");
				result.result = -1L;
				result.remark = "删除签约关系失败";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if ((((FirmBankFunds) vecFFV.get(0)).primaryBankFlag == 1) && (vecFFV2 != null) && (vecFFV2.size() > 0)) {
				FirmBankFunds fbf = (FirmBankFunds) vecFFV2.get(0);
				fbf.primaryBankFlag = 1;
				if (DAO.modFirmBankFunds(fbf, conn) != 1) {
					Tool.log("将原有次银行银行[" + fbf.bankID + "]修改为主银行失败");
					conn.rollback();
					result.result = -1L;
					result.remark = "解约失败";
					localReturnValue1 = result;
					return localReturnValue1;
				}
			}
			result = delCorrPT2Bank(corr);
			if (result.result == 0L) {
				conn.commit();
				result.remark = "解约成功";
				if (vecOpen.size() == 1) {
					for (FirmID2SysFirmID f2f : vecF2f) {
						noticeDelPlatFirmID(f2f.systemID, f2f.firmID, f2f.sysFirmID);
					}
				}
			} else if (result.result == 5L) {
				Tool.log("银行[" + corr.bankID + "]不支持市场端解约，对应关系删除成功，返回交易系统成功，还需要从银行端发起解约");
				result.remark = "市场端解约成功，请从银行端再次发起银行端解约";
				conn.commit();
				if (vecOpen.size() == 1) {
					for (FirmID2SysFirmID f2f : vecF2f) {
						noticeDelPlatFirmID(f2f.systemID, f2f.firmID, f2f.sysFirmID);
					}
				}
			} else {
				conn.rollback();
				result.result = -1L;
				if ((result.remark == null) || ("".equals(result.remark))) {
					result.remark = "解约失败";
				}
			}
		} catch (Exception e) {
			Tool.log("平台处理器直接解约异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "平台处理器异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue delCorrPT2Bank(CorrespondValue corr) {
		Tool.log("平台向银行发起解约");
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			conn.setAutoCommit(false);

			Vector<CorrespondValue> vecC = DAO.getCorrespondList(" where firmid='" + corr.firmID + "' and bankid='" + corr.bankID + "'", conn);
			ReturnValue localReturnValue1;
			if ((vecC == null) || (vecC.size() != 1)) {
				Tool.log("没有找到签约关系，或者找到多条签约关系");
				result.result = -1L;
				result.remark = "没有找到签约关系或存在多条签约关系";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			corr.account1 = ((CorrespondValue) vecC.get(0)).account1;

			result.result = DAO.closeCorrespond(corr.bankID, corr.firmID, corr.card, conn);
			if (result.result != 1L) {
				Tool.log("删除平台号和银行的对应关系失败");
				result.result = -1L;
				result.remark = "删除和银行账户的对应关系失败";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}
			BankAdapterRMI adapter = getAdapter(corr.bankID);
			if (adapter == null) {
				Tool.log("平台处理器获取银行[" + corr.bankID + "]适配器失败");
				result.result = -1L;
				result.remark = "解约失败";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			try {
				corr.accountName = ((CorrespondValue) vecC.get(0)).accountName;
				corr.mobile = ((CorrespondValue) vecC.get(0)).mobile;
				corr.email = ((CorrespondValue) vecC.get(0)).email;
				corr.accountType = ((CorrespondValue) vecC.get(0)).accountType;
				corr.bankCity = ((CorrespondValue) vecC.get(0)).bankCity;
				corr.bankName = ((CorrespondValue) vecC.get(0)).bankName;
				corr.bankProvince = ((CorrespondValue) vecC.get(0)).bankProvince;
				corr.inMarketCode = ((CorrespondValue) vecC.get(0)).inMarketCode;
				corr.isCrossLine = ((CorrespondValue) vecC.get(0)).isCrossLine;
				corr.OpenBankCode = ((CorrespondValue) vecC.get(0)).OpenBankCode;
				result = adapter.delAccountQuery(corr);
			} catch (RemoteException e) {
				Tool.log("调用银行[" + corr.bankID + "]的适配器解约方法异常");
				result.result = -1L;
				result.remark = "处理失败";
			}
			if (result.result == 0L) {
				Tool.log("银行处理解约成功");
				result.remark = "解约成功";
				conn.commit();
			} else if (result.result == 5L) {
				Tool.log("该银行不支持市场端解约");
				result.remark = "该银行不支持市场端解约";
				conn.rollback();
			} else {
				Tool.log("银行处理解约失败");
				result.result = -1L;
				if ((result.remark == null) || ("".equals(result.remark))) {
					result.remark = "银行处理失败";
				}
			}
		} catch (Exception e) {
			Tool.log("系统异常" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue getIsOpenBanks(String firmID) {
		Tool.log("查询平台号[" + firmID + "]已经签约的银行");
		ReturnValue result = new ReturnValue();
		result.result = 0L;

		Vector<CorrespondValue> vecCorr = null;
		try {
			vecCorr = DAO.getCorrespondList(" where firmID='" + firmID + "' and isOpen=1");
		} catch (Exception e) {
			Tool.log("查询平台号和银行账户绑定关系异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCorr == null) || (vecCorr.size() <= 0)) {
			Tool.log("没有找到平台号[" + firmID + "]和银行已经签约的记录");
			result.remark = "还没有签约的银行";
			result.msg = new Object[] { new Vector() };
			return result;
		}
		String filter = " where bankID in (";
		for (CorrespondValue cv : vecCorr) {
			filter = filter + "'" + cv.bankID + "',";
		}
		Vector<BankValue> vecBanks = null;
		try {
			vecBanks = DAO.getBankList(filter.substring(0, filter.length() - 1) + ")");
		} catch (Exception e) {
			Tool.log("查询银行信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecBanks == null) || (vecBanks.size() <= 0)) {
			Tool.log("没有找到有关银行的记录");
			result.remark = "没有找到已经签约的银行信息";
			result.msg = new Object[] { new Vector() };
			return result;
		}
		result.remark = "查询到了已经签约的银行";
		result.msg = new Object[] { vecBanks };
		return result;
	}

	public ReturnValue getFirmbalancePlat(String firmID, String bankID, String pwd, String bankPwd) {
		Tool.log("查询平台号[" + firmID + "]有关银行[" + bankID + "]的余额信息");
		ReturnValue result = new ReturnValue();

		result = isPassword(firmID, pwd);
		if (result.result != 0L) {
			Tool.log("资金密码错误");
			result.result = -1L;
			result.remark = "资金密码错误";
			return result;
		}
		Vector<CorrespondValue> vecCorr = null;
		try {
			vecCorr = DAO.getCorrespondList(" where firmID='" + firmID + "' and bankID='" + bankID + "'");
		} catch (Exception e) {
			Tool.log("查询平台号和银行账号对应关系异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCorr == null) || (vecCorr.size() <= 0)) {
			Tool.log("没有查询到平台号[" + firmID + "]和银行[" + bankID + "]的绑定记录");
			result.result = -1L;
			result.remark = "没有找到签约记录";
			return result;
		}
		if (((CorrespondValue) vecCorr.get(0)).isOpen != 1) {
			Tool.log("平台号[" + firmID + "]和银行[" + bankID + "]还没有签约");
			result.result = -1L;
			result.remark = "该银行没有签约成功";
			return result;
		}
		double platBalnace = 0.0D;
		double canOutBalance = 0.0D;
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			platBalnace = dataProcess.getRealFunds(firmID, conn);
			canOutBalance = getCanOutBalance(firmID, bankID, conn);
		} catch (Exception e) {
			Tool.log("系统异常：" + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		double bankBalance = 0.0D;
		try {
			BankAdapterRMI bankadapter = getAdapter(bankID);
			bankBalance = bankadapter.accountQuery((CorrespondValue) vecCorr.get(0), bankPwd);
		} catch (RemoteException e) {
			Tool.log("查询银行余额异常：" + Tool.getExceptionTrace(e));
		}
		result.result = 0L;
		result.remark = "查询成功";
		result.msg = new Object[] { ((CorrespondValue) vecCorr.get(0)).account, Double.valueOf(bankBalance), Double.valueOf(platBalnace),
				Double.valueOf(canOutBalance) };
		return result;
	}

	public ReturnValue getRelationSystem(String firmID) {
		Tool.log("查询平台号关联的交易系统");
		ReturnValue result = new ReturnValue();
		result.result = 0L;
		Vector<SystemMessage> vecSysm = null;
		try {
			vecSysm = DAO
					.getSystemMessages(" where systemID in (select distinct systemid from f_b_firmid_sysfirmid t where firmid='" + firmID + "')");
		} catch (SQLException e) {
			Tool.log("查询交易系统异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecSysm == null) || (vecSysm.size() <= 0)) {
			Tool.log("平台号[" + firmID + "]没有对应的交易系统信息");
			result.remark = "没有查询到相关信息";
			result.msg = new Object[] { new Vector() };
			return result;
		}
		result.remark = "查询成功";
		result.msg = new Object[] { vecSysm };
		return result;
	}

	public ReturnValue inOutMoneyReqDo(InOutMoney inOutMoney) {
		Tool.log("平台处理交易系统的市场端" + (String.valueOf(0).equals(inOutMoney.inOutMoneyFlag) ? "入金" : "出金") + "请求");
		ReturnValue result = new ReturnValue();

		Vector<CorrespondValue> vecCorr = null;
		try {
			vecCorr = DAO.getCorrespondList(" where firmID='" + inOutMoney.firmID + "' and bankID='" + inOutMoney.bankID + "' and isOpen=1");
		} catch (Exception e) {
			Tool.log("查询签约关系异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCorr == null) || (vecCorr.size() <= 0)) {
			Tool.log("没有查询到平台号[" + inOutMoney.firmID + "]签约银行[" + inOutMoney.bankID + "]的记录");
			result.result = -1L;
			result.remark = "没有找到签约记录";
			return result;
		}
		Vector<FirmID2SysFirmID> vecF2f = null;
		try {
			vecF2f = DAO.getFirmID2SysFirmID(
					" where firmID='" + inOutMoney.firmID + "' and systemid='" + inOutMoney.systemID + "' and bankID='" + inOutMoney.bankID + "'");
		} catch (SQLException e) {
			Tool.log("查询交易商和平台号的对应关系异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecF2f == null) || (vecF2f.size() <= 0)) {
			Tool.log("没有找到平台号[" + inOutMoney.firmID + "]交易系统[" + inOutMoney.systemID + "]有关银行[" + inOutMoney.bankID + "]的对应关系");
			result.result = -1L;
			result.remark = "交易商和平台号的对应关系不存在";
			return result;
		}
		result = isPassword(inOutMoney.firmID, inOutMoney.pwd);
		if (result.result != 0L) {
			Tool.log("资金密码校验错误");
			result.result = -1L;
			result.remark = "资金密码错误";
			return result;
		}
		TradeProcessorRMI trade = getTradeCaptialProcessor(inOutMoney.systemID);
		if (trade == null) {
			Tool.log("获取交易系统[" + inOutMoney.systemID + "]的处理器RMI链接失败");
			result.result = -1L;
			result.remark = "获取交易系统处理器RMI失败";
			return result;
		}
		inOutMoney.sysFirmID = ((FirmID2SysFirmID) vecF2f.get(0)).sysFirmID;

		RequestMsg req = new RequestMsg();
		req.setBankID(inOutMoney.bankID);
		req.setParams(new Object[] { inOutMoney, Integer.valueOf(1) });
		if (String.valueOf(0).equals(inOutMoney.inOutMoneyFlag)) {
			req.setMethodName("inMoney");
		} else {
			req.setMethodName("outMoney");
		}
		try {
			result = trade.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用交易系统处理器对应的入金或出金方法异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "平台处理器调用交易系统处理器异常";
		}
		return result;
	}

	public ReturnValue inMoneyPtToBank(InOutMoney inMoney) {
		Tool.log("平台号[" + inMoney.firmID + "]发起入金到银行[" + inMoney.bankID + "]");
		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		double inRate = 0.0D;
		long capitalID = 0L;

		TransferInfoValue payInfo = null;

		TransferInfoValue receiveInfo = null;
		ReturnValue result = new ReturnValue();
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);

				CapitalValue cVal = new CapitalValue();
				cVal.actionID = inMoney.actionID;
				cVal.bankID = inMoney.bankID;
				cVal.creditID = inMoney.firmID;
				cVal.debitID = ((String) dataProcess.getBANKSUB().get(inMoney.bankID));
				cVal.firmID = inMoney.firmID;
				cVal.money = inMoney.money;
				cVal.note = inMoney.remark;
				cVal.oprcode = dataProcess.getINSUMMARY() + "";
				cVal.status = 2;
				cVal.type = 0;

				cVal.systemID = inMoney.systemID;
				cVal.sysFirmID = inMoney.sysFirmID;
				cVal.tradeSource = "1";

				capitalID = DAO.addCapitalInfo(cVal, conn);

				inRate = getTransRate(inMoney.bankID, inMoney.firmID, inMoney.money, 0, 0, inMoney.account, conn);

				cVal.creditID = "Market";
				cVal.debitID = inMoney.firmID;
				cVal.money = inRate;
				cVal.oprcode = dataProcess.getFEESUMMARY() + "";
				cVal.type = 2;

				result.result = 0L;
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				result.result = -10026L;
				result.remark = "写入资金流水异常";
				Tool.log("市场端发起入金写资金流水SQLException,数据回滚:" + Tool.getExceptionTrace(e));
			} finally {
				conn.setAutoCommit(true);
			}
			ReturnValue localReturnValue1;
			if (result.result != 0L) {
				Tool.log("插入流水失败");
				result.result = -1L;
				result.remark = "系统记录出入金流水失败";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			payInfo = getPayInfo(inMoney.bankID, inMoney.firmID, 0, conn);

			receiveInfo = getReceiveInfo(inMoney.bankID, inMoney.firmID, 0, conn);

			BankValue bv = null;
			try {
				bv = DAO.getBank(inMoney.bankID);
			} catch (Exception e3) {
				e3.printStackTrace();
			}
			if ((inMoney.contact == null) || ("".equals(inMoney.contact))) {
				Vector<CorrespondValue> vecCorr = DAO.getCorrespondList(" where firmID='" + inMoney.firmID + "' and bankID='" + inMoney.bankID + "'",
						conn);
				if ((vecCorr == null) || (((Vector) vecCorr).size() != 1)) {
					Tool.log("没有找到平台号[" + inMoney.firmID + "]和银行[" + inMoney.bankID + "]的签约记录");
					result.result = -1L;
					result.remark = "没有找到签约关系";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
				inMoney.contact = ((CorrespondValue) vecCorr.get(0)).contact;
			}
			InMoneyVO inMoneyInfo = new InMoneyVO(inMoney.bankID, bv.bankName, inMoney.money, inMoney.firmID, inMoney.contact, payInfo,
					inMoney.accountPwd, receiveInfo, inMoney.remark, inMoney.actionID, inMoney.payChannel);

			BankAdapterRMI bankadapter = getAdapter(inMoney.bankID);
			System.out.println(bankadapter);
			if (bankadapter == null) {
				Tool.log("处理器连接适配器[" + inMoney.bankID + "]失败");
				result.result = -1L;
				result.remark = ("平台处理器提交银行[" + inMoney.bankID + "]的适配器失败");
				localReturnValue1 = result;
				return localReturnValue1;
			}
			try {
				result = bankadapter.inMoneyQueryBank(inMoneyInfo);
			} catch (RemoteException e2) {
				Tool.log("处理器提交适配器异常" + Tool.getExceptionTrace(e2));
				result.result = -1L;
				result.remark = "处理器提交适配器异常";
			}
			try {
				conn.setAutoCommit(false);
				if (result.result == 0L) {
					Tool.log("适配器处理成功，业务流水处理成功,入金成功。");
					result.remark = "成功";
					DAO.modCapitalInfoStatus(capitalID, result.funID, 0, curTime, conn);
					DAO.modCapitalInfoNote(capitalID, "市场端入金,银行处理成功,入金成功", conn);

					dataProcess.updateFundsFull(inMoney.firmID, dataProcess.getINSUMMARY() + "",
							(String) dataProcess.getBANKSUB().get(inMoney.bankID), inMoney.money, inMoney.actionID, conn);
				} else if (result.result == 5L) {
					DAO.modCapitalInfoStatus(capitalID, result.funID, 2, curTime, conn);
					DAO.modCapitalInfoNote(capitalID, "市场端入金，银行处理中", conn);
					Tool.log("适配器处理成功，业务流水处理中。");
					DAO.modCapitalInfoNote(capitalID, "市场端入金,银行处理中", conn);
				} else {
					DAO.modCapitalInfoStatus(capitalID, result.funID, 1, curTime, conn);
					DAO.modCapitalInfoNote(capitalID, "市场端入金,入金失败", conn);

					Tool.log("请求银行入金失败");
				}
				conn.commit();
			} catch (Exception e) {
				result.result = -10015L;
				result.remark = "系统异常";
				conn.rollback();
				Tool.log("市场端发起入金,系统异常Exception,数据回滚:" + Tool.getExceptionTrace(e));
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("系统处理异常");
			result.result = -1L;
			result.remark = "系统处理异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue inMoneyMarket(InOutMoney inMoney) {
		Tool.log("入金，交易系统发起");
		Tool.log("bankID[" + inMoney.bankID + "]firmID[" + inMoney.firmID + "]systemID[" + inMoney.systemID + "]sysFirmID[" + inMoney.sysFirmID
				+ "]account[" + inMoney.account + "]accountPwd[" + inMoney.accountPwd + "]money[" + inMoney.money + "]remark[" + inMoney.remark
				+ "]paychannel[" + inMoney.payChannel + "]");

		ReturnValue result = new ReturnValue();
		if (!isTradeDate(inMoney.systemID)) {
			Tool.log("交易系统[" + inMoney.systemID + "]当前不在转账时间内");
			result.result = -1L;
			result.remark = "交易系统不在转账时间内";
			return result;
		}
		if (!bankClose(inMoney.bankID)) {
			Tool.log("银行[" + inMoney.bankID + "]被禁用");
			result.result = -1L;
			result.remark = "该银行被禁用";
			return result;
		}
		result = checkFirmStats(inMoney.firmID, inMoney.bankID);
		if (result.result != 0L) {
			return result;
		}
		if ((inMoney.actionID > 0L) && (inMoney.abcInfo != null)) {
			result = insertAbcInfo(inMoney.abcInfo);
			if (result.result != 0L) {
				Tool.log("写入农行信息失败");
				result.result = -1L;
				return result;
			}
		} else {
			inMoney.actionID = getMktActionID();
		}
		long capitalID = 0L;

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		Vector<CorrespondValue> vecCorr = null;
		try {
			vecCorr = DAO.getCorrespondList(" where firmid='" + inMoney.firmID + "' and bankid='" + inMoney.bankID + "' and isopen=1 and status=0");
		} catch (Exception e) {
			Tool.log("查询平台号[" + inMoney.firmID + "]和银行[" + inMoney.bankID + "]的签约关系异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCorr == null) || (vecCorr.size() <= 0)) {
			result.result = -1L;
			result.remark = "没有查询到签约关系";
			Tool.log("没有找到平台号[" + inMoney.firmID + "]和银行[" + inMoney.bankID + "]的正常可用签约关系");
			return result;
		}
		try {
			conn = DAO.getConnection();

			Vector<FirmID2SysFirmID> vecf2f = DAO.getFirmID2SysFirmID(
					" where bankID='" + inMoney.bankID + "' and sysFirmID='" + inMoney.sysFirmID + "' and systemID='" + inMoney.systemID + "'");
			ReturnValue localReturnValue1;
			if ((vecf2f == null) || (vecf2f.size() <= 0)) {
				Tool.log("没有找到平台号[" + inMoney.firmID + "]和交易系统[" + inMoney.systemID + "]的交易商[" + inMoney.sysFirmID + "]关于银行[" + inMoney.bankID
						+ "]的签约关系");
				result.result = -1L;
				result.remark = "签约关系不存在";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			Vector<CapitalValue> capitalVec = DAO
					.getCapitalInfoListPT(" where funid='" + inMoney.sysAciontID + "' and systemid='" + inMoney.systemID + "'");
			if ((capitalVec != null) && (capitalVec.size() > 0)) {
				Tool.log("交易系统[" + inMoney.systemID + "]的流水号[" + inMoney.sysAciontID + "]在平台已经存在");
				result.result = -1L;
				result.remark = "流水号重复";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			try {
				conn.setAutoCommit(false);

				CapitalValue cVal = new CapitalValue();
				cVal.actionID = inMoney.actionID;
				cVal.bankID = inMoney.bankID;
				cVal.creditID = inMoney.firmID;
				cVal.debitID = ((String) dataProcess.getBANKSUB().get(inMoney.bankID));
				cVal.firmID = inMoney.firmID;
				cVal.money = inMoney.money;
				cVal.note = inMoney.remark;
				cVal.oprcode = dataProcess.getINSUMMARY() + "";
				cVal.status = 2;
				cVal.type = 0;
				cVal.funID = inMoney.sysAciontID + "";
				cVal.systemID = inMoney.systemID;
				cVal.sysFirmID = inMoney.sysFirmID;
				cVal.tradeSource = "0";
				System.out.println("[" + cVal.toString() + "]");
				capitalID = DAO.addCapitalInfoPT(cVal, conn);

				result.result = 0L;
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				result.result = -10026L;
				result.remark = "写入资金流水异常";
				Tool.log("市场端发起入金写资金流水SQLException,数据回滚:" + Tool.getExceptionTrace(e));
			} finally {
				conn.setAutoCommit(true);
			}
			if (result.result != 0L) {
				result.result = -1L;
				result.remark = "记录流水失败";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			result = inMoneyPtToBank(inMoney);
			try {
				conn.setAutoCommit(false);
				if (result.result == 0L) {
					Tool.log("业务流水处理成功,入金成功。");
					result.remark = "成功";
					DAO.modCapitalInfoStatusPT(capitalID, inMoney.sysAciontID + "", 0, curTime, conn);
					DAO.modCapitalInfoNotePT(capitalID, "市场端入金，入金成功", conn);
				} else if (result.result == 5L) {
					Tool.log("业务流水处理中。");
					result.remark = "处理中";
				} else {
					result.remark = "处理失败";
					DAO.modCapitalInfoStatusPT(capitalID, inMoney.sysAciontID + "", 1, curTime, conn);
					DAO.modCapitalInfoNotePT(capitalID, "市场端入金，入金失败", conn);
					Tool.log("平台处理流水失败");
				}
				conn.commit();
			} catch (Exception e) {
				result.result = -10015L;
				result.remark = "系统异常";
				conn.rollback();
				Tool.log("市场端发起入金,系统异常Exception,数据回滚:" + Tool.getExceptionTrace(e));
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("平台处理入金异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "平台处理入金发生异常";
		} finally {
			result.funID = String.valueOf(inMoney.actionID);
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue outMoneyPtToBank(InOutMoney outMoney, double outRate) {
		Tool.log("平台交易商[" + outMoney.firmID + "]发起出金到到银行[" + outMoney.bankID + "]");
		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		long capitalID = 0L;

		TransferInfoValue payInfo = null;

		TransferInfoValue receiveInfo = null;
		ReturnValue result = new ReturnValue();
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);

				CapitalValue cVal = new CapitalValue();
				cVal.actionID = outMoney.actionID;
				cVal.bankID = outMoney.bankID;
				cVal.creditID = outMoney.firmID;
				cVal.debitID = ((String) dataProcess.getBANKSUB().get(outMoney.bankID));
				cVal.firmID = outMoney.firmID;
				cVal.money = outMoney.money;
				cVal.note = outMoney.remark;
				cVal.oprcode = dataProcess.getOUTSUMMARY() + "";
				cVal.status = 2;
				cVal.type = 1;

				cVal.systemID = outMoney.systemID;
				cVal.sysFirmID = outMoney.sysFirmID;
				cVal.tradeSource = "1";

				capitalID = DAO.addCapitalInfo(cVal, conn);

				cVal.creditID = "Market";
				cVal.debitID = outMoney.firmID;
				cVal.money = outRate;
				cVal.oprcode = dataProcess.getFEESUMMARY() + "";
				cVal.type = 2;

				conn.commit();
				result.result = 0L;
			} catch (Exception e) {
				conn.rollback();
				result.result = -10026L;
				result.remark = "写入资金流水异常";
				Tool.log("平台到银行出金写流水SQLException,数据回滚:" + e);
			} finally {
				conn.setAutoCommit(true);
			}
			ReturnValue localReturnValue1;
			if (result.result != 0L) {
				Tool.log("记录流水失败");
				result.result = -1L;
				result.remark = "记录流水失败";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			payInfo = getPayInfo(outMoney.bankID, outMoney.firmID, 1, conn);

			receiveInfo = getReceiveInfo(outMoney.bankID, outMoney.firmID, 1, conn);
			BankValue bv = DAO.getBank(outMoney.bankID);
			if ((outMoney.contact == null) || ("".equals(outMoney.contact))) {
				Vector<CorrespondValue> vecCorr = DAO
						.getCorrespondList(" where firmID='" + outMoney.firmID + "' and bankID='" + outMoney.bankID + "'", conn);
				if ((vecCorr == null) || (((Vector) vecCorr).size() != 1)) {
					Tool.log("没有找到平台号[" + outMoney.firmID + "]和银行[" + outMoney.bankID + "]的签约记录");
					result.result = -1L;
					result.remark = "没有找到签约关系";
					conn.rollback();
					localReturnValue1 = result;
					return localReturnValue1;
				}
				outMoney.contact = ((CorrespondValue) vecCorr.get(0)).contact;
			}
			OutMoneyVO outMoneyInfo = new OutMoneyVO(outMoney.bankID, bv.bankName, outMoney.money, outMoney.firmID, outMoney.contact, payInfo,
					receiveInfo, outMoney.actionID, 0, null);

			BankAdapterRMI bankadapter = getAdapter(outMoney.bankID);
			result = bankadapter.outMoneyMarketDone(outMoneyInfo);
			try {
				conn.setAutoCommit(false);
				if (result.result == 0L) {
					Tool.log("适配器处理成功，业务流水处理成功,出金成功。");
					result.remark = "成功";
					DAO.modCapitalInfoStatus(capitalID, result.funID, 0, curTime, conn);
					DAO.modCapitalInfoNote(capitalID, "市场端出金，出金成功", conn);
				} else if ((result.result == 5L) || (result.result == -50010L) || (result.result == -920008L)) {
					result.result = 5L;
					DAO.modCapitalInfoStatus(capitalID, result.funID, 2, curTime, conn);
					DAO.modCapitalInfoNote(capitalID, "市场端出金，银行处理中", conn);

					Tool.log("适配器处理成功，业务流水处理中。");
				} else {
					DAO.modCapitalInfoStatus(capitalID, result.funID, 1, curTime, conn);
					DAO.modCapitalInfoNote(capitalID, "市场端出金，出金失败", conn);

					Tool.log("请求银行出金失败");
				}
				conn.commit();
			} catch (Exception e) {
				result.result = -10015L;
				result.remark = "系统异常";
				conn.rollback();
				Tool.log("市场端发起出金,系统异常Exception,数据回滚:" + result);
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("处理平台到银行端的出金异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "平台到银行出金，系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue outMoneyMarket(InOutMoney outMoney) {
		Tool.log("交易系统[" + outMoney.systemID + "]发起交易商[" + outMoney.sysFirmID + "]对应平台号[" + outMoney.firmID + "]关于银行[" + outMoney.bankID + "]的出金");
		ReturnValue result = new ReturnValue();
		if (!isTradeDate(outMoney.systemID)) {
			Tool.log("交易系统[" + outMoney.systemID + "]当前不在转账时间内");
			result.result = -1L;
			result.remark = "不在转账时间内";
			return result;
		}
		if (!bankClose(outMoney.bankID)) {
			Tool.log("银行[" + outMoney.bankID + "]被禁用");
			result.result = -1L;
			result.remark = "该银行被禁用";
			return result;
		}
		result = outMoneyCheck(outMoney.bankID, outMoney.firmID, outMoney.money);
		if (result.result != 0L) {
			Tool.log("不符合和出金条件");
			return result;
		}
		result = checkFirmStats(outMoney.firmID, outMoney.bankID);
		if (result.result != 0L) {
			return result;
		}
		if ((outMoney.actionID > 0L) && (outMoney.abcInfo != null)) {
			result = insertAbcInfo(outMoney.abcInfo);
			if (result.result != 0L) {
				Tool.log("写入农行信息失败");
				result.result = -1L;
				return result;
			}
		} else {
			outMoney.actionID = getMktActionID();
		}
		long capitalID = 0L;

		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());

		Vector<CorrespondValue> vecCorr = null;
		try {
			vecCorr = DAO.getCorrespondList(" where firmid='" + outMoney.firmID + "' and bankid='" + outMoney.bankID + "' and isopen=1 and status=0");
		} catch (Exception e) {
			Tool.log("查询平台号[" + outMoney.firmID + "]和银行[" + outMoney.bankID + "]的签约关系异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCorr == null) || (vecCorr.size() <= 0)) {
			result.result = -1L;
			result.remark = "没有查询到签约关系";
			Tool.log("没有找到平台号[" + outMoney.firmID + "]和银行[" + outMoney.bankID + "]的正常可用签约关系");
			return result;
		}
		try {
			conn = DAO.getConnection();

			Vector<FirmID2SysFirmID> vecf2f = DAO.getFirmID2SysFirmID(
					" where bankID='" + outMoney.bankID + "' and sysFirmID='" + outMoney.sysFirmID + "' and systemID='" + outMoney.systemID + "'");
			ReturnValue localReturnValue1;
			if ((vecf2f == null) || (vecf2f.size() <= 0)) {
				Tool.log("没有找到平台号[" + outMoney.firmID + "]和交易系统[" + outMoney.systemID + "]的交易商[" + outMoney.sysFirmID + "]关于银行[" + outMoney.bankID
						+ "]的签约关系");
				result.result = -1L;
				result.remark = "签约关系不存在";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			Vector<CapitalValue> capitalVec = DAO
					.getCapitalInfoListPT(" where funid='" + outMoney.sysAciontID + "' and systemid='" + outMoney.systemID + "'");
			if ((capitalVec != null) && (capitalVec.size() > 0)) {
				Tool.log("交易系统[" + outMoney.systemID + "]的流水号[" + outMoney.sysAciontID + "]在平台已经存在");
				result.result = -1L;
				result.remark = "流水号重复";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			double realFunds = getCanOutBalance(outMoney.firmID, outMoney.bankID, conn);

			double outRate = getTransRate(outMoney.bankID, outMoney.firmID, outMoney.money, 1, 0, null, conn);
			if (realFunds < Arith.sub(outMoney.money, outRate)) {
				Tool.log("平台交易商[" + outMoney.firmID + "]的可用金额为[" + realFunds + "],小于出金金额[" + outMoney.money + "]和手续费[" + outRate + "]的和");
				result.result = -1L;
				result.remark = "交易商余额不足";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			try {
				conn.setAutoCommit(false);

				CapitalValue cVal = new CapitalValue();
				cVal.actionID = outMoney.actionID;
				cVal.bankID = outMoney.bankID;
				cVal.creditID = outMoney.firmID;
				cVal.debitID = ((String) dataProcess.getBANKSUB().get(outMoney.bankID));
				cVal.firmID = outMoney.firmID;
				cVal.money = outMoney.money;
				cVal.note = outMoney.remark;
				cVal.oprcode = dataProcess.getOUTSUMMARY() + "";
				cVal.status = 2;
				cVal.type = 1;
				cVal.feeMoney = outRate;
				cVal.funID = outMoney.sysAciontID + "";
				cVal.systemID = outMoney.systemID;
				cVal.sysFirmID = outMoney.sysFirmID;
				cVal.tradeSource = "0";

				capitalID = DAO.addCapitalInfoPT(cVal, conn);

				dataProcess.updateFrozenFunds(outMoney.firmID, outMoney.bankID, Arith.sub(outMoney.money, outRate), conn);

				bankFrozenFuns(outMoney.firmID, outMoney.bankID, outMoney.account, Arith.sub(outMoney.money, outRate), conn);
				conn.commit();
				result.result = 0L;
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
				result.result = -10026L;
				result.remark = "写入资金流水异常";
				Tool.log("市场端发起出金写资金流水SQLException,数据回滚:" + Tool.getExceptionTrace(e));
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
			if (result.result != 0L) {
				Tool.log("记录流水失败");
				result.result = -1L;
				result.remark = "记录流失失败";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			Vector<BankValue> vecBank = DAO.getBankList(" where bankID='" + outMoney.bankID + "'");
			if ((vecBank == null) || (vecBank.size() <= 0)) {
				Tool.log("没有找到银行[" + outMoney.bankID + "]的信息");
				result.result = -1L;
				result.remark = "没有找到该银行的信息";
			}
			double checkOutMoney = ((BankValue) vecBank.get(0)).maxAuditMoney;

			FirmValue fv = DAO.getFirm(outMoney.firmID, conn);
			if (fv == null) {
				Tool.log("没有找到平台号[" + outMoney.firmID + "]的信息");
				result.result = -1L;
				result.remark = ("没有平台号[" + outMoney.firmID + "]的信息");
			}
			if (result.result == 0L) {
				if ((checkOutMoney > fv.maxAuditMoney) && (fv.maxAuditMoney != 0.0D)) {
					checkOutMoney = fv.maxAuditMoney;
				}
				if (checkOutMoney <= outMoney.money) {
					Tool.log("平台号[" + outMoney.firmID + "]出金[" + outMoney.money + "]到银行[" + outMoney.bankID + "]，超过了审核额度[" + checkOutMoney
							+ "]，进入审核队列");
					result.result = 5L;
					result.remark = "超出审核额度，等待审核";
					try {
						conn.setAutoCommit(false);
						DAO.modCapitalInfoStatusPT(capitalID, outMoney.sysAciontID + "", 3, curTime, conn);
						DAO.modCapitalInfoNotePT(capitalID, "市场端出金，等待审核", conn);
						conn.commit();
					} catch (Exception e) {
						Tool.log("修改出金流水[" + outMoney.actionID + "]的状态为待审核状态异常，数据回滚：" + Tool.getExceptionTrace(e));
						result.result = -1L;
						result.remark = "出金进入审核队列异常";
						conn.rollback();
					} finally {
						conn.setAutoCommit(true);
					}
				} else {
					result = outMoneyPtToBank(outMoney, outRate);
				}
			}
			try {
				conn.setAutoCommit(false);
				if (result.result == 0L) {
					Tool.log("业务流水处理成功,出金成功。");
					result.remark = "出金成功";
					DAO.modCapitalInfoStatusPT(capitalID, outMoney.sysAciontID + "", 0, curTime, conn);
					DAO.modCapitalInfoNotePT(capitalID, "市场端出金，出金成功", conn);

					dataProcess.updateFrozenFunds(outMoney.firmID, outMoney.bankID, -1.0D * Arith.sub(outMoney.money, outRate), conn);
					dataProcess.updateFundsFull(outMoney.firmID, dataProcess.getOUTSUMMARY() + "",
							(String) dataProcess.getBANKSUB().get(outMoney.bankID), outMoney.money, outMoney.actionID, conn);

					bankFrozenFuns(outMoney.firmID, outMoney.bankID, null, -1.0D * Arith.sub(outMoney.money, outRate), conn);
				} else if (result.result == 5L) {
					Tool.log("业务流水处理中。");
					result.remark = "处理中";
				} else {
					DAO.modCapitalInfoStatusPT(capitalID, outMoney.sysAciontID + "", 1, curTime, conn);
					DAO.modCapitalInfoNotePT(capitalID, "市场端出金，出金失败", conn);

					dataProcess.updateFrozenFunds(outMoney.firmID, outMoney.bankID, -1.0D * Arith.sub(outMoney.money, outRate), conn);

					bankFrozenFuns(outMoney.firmID, outMoney.bankID, null, -1.0D * Arith.sub(outMoney.money, outRate), conn);
					Tool.log("平台处理流水失败，冻结资金被释放");
				}
				conn.commit();
			} catch (Exception e) {
				result.result = -10015L;
				result.remark = "系统异常";
				e.printStackTrace();
				conn.rollback();
				Tool.log("市场端发起出金,系统异常Exception,数据回滚:" + Tool.getExceptionTrace(e));
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("平台处理出金异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "平台处理出金发生异常";
		} finally {
			result.funID = String.valueOf(outMoney.actionID);
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue transferFunds(String outSystemID, String inSystemID, String firmID, double money, String fundsPwd) {
		Tool.log("平台号[" + firmID + "]从系统[" + outSystemID + "]向系统[" + inSystemID + "]划入资金[" + money + "]");
		ReturnValue result = new ReturnValue();

		long outActionID = 0L;

		long inActionID = 0L;

		long outID = 0L;

		long inID = 0L;

		Vector<FirmID2SysFirmID> vecF2fOut = null;
		try {
			vecF2fOut = DAO.getFirmID2SysFirmID(" where firmID='" + firmID + "' and systemID='" + outSystemID + "'");
		} catch (SQLException e) {
			Tool.log("查询平台号和转出系统的对应关系异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecF2fOut == null) || (vecF2fOut.size() <= 0)) {
			Tool.log("没有查询到平台号[" + firmID + "]和付款系统[" + outSystemID + "]有对应关系");
			result.result = -1L;
			result.remark = "没有查询到该平台号和付款系统有对应关系";
			return result;
		}
		Vector<FirmID2SysFirmID> vecF2fIn = null;
		try {
			vecF2fIn = DAO.getFirmID2SysFirmID(" where firmID='" + firmID + "' and systemID='" + inSystemID + "'");
		} catch (SQLException e) {
			Tool.log("查询平台号和转入系统的对应关系异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecF2fIn == null) || (vecF2fIn.size() <= 0)) {
			Tool.log("没有查询到平台号[" + firmID + "]和收款系统[" + inSystemID + "]有对应关系");
			result.result = -1L;
			result.remark = "没有查询到该平台号和收款系统有对应关系";
			return result;
		}
		result = isPassword(firmID, fundsPwd);
		if (result.result != 0L) {
			Tool.log("资金密码校验错误");
			result.result = -1L;
			result.remark = "资金密码错误";
			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			outActionID = getMktActionID();
			inActionID = getMktActionID();
			try {
				conn.setAutoCommit(false);

				CapitalValue cVal = new CapitalValue();
				cVal.actionID = outActionID;
				cVal.bankID = "00";
				cVal.creditID = firmID;
				cVal.debitID = "10200";
				cVal.firmID = firmID;
				cVal.money = money;
				cVal.note = ("从系统[" + outSystemID + "]转出资金[" + money + "],转出流水号[" + outActionID + "]");
				cVal.oprcode = "104";
				cVal.status = 0;
				cVal.type = 3;
				cVal.systemID = outSystemID;
				cVal.sysFirmID = ((FirmID2SysFirmID) vecF2fOut.get(0)).sysFirmID;
				cVal.tradeSource = "1";
				outID = DAO.addCapitalInfoPT(cVal, conn);

				cVal.actionID = inActionID;
				cVal.note = ("转入资金[" + money + "]到系统[" + inSystemID + "],转入流水号[" + inActionID + "]");
				cVal.oprcode = "103";
				cVal.systemID = inSystemID;
				cVal.sysFirmID = ((FirmID2SysFirmID) vecF2fIn.get(0)).sysFirmID;
				inID = DAO.addCapitalInfoPT(cVal, conn);

				result.result = 0L;
			} catch (Exception e) {
				Tool.log("平台记录资金划转流水异常，数据回滚：" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "记录资金划转流水、系统异常";
				conn.rollback();
			}
			ReturnValue localReturnValue1;
			if (result.result != 0L) {
				Tool.log("平台记录资金划转的流水失败");
				result.result = -1L;
				result.remark = "平台记录流水失败";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}
			TradeProcessorRMI tradeProc = getTradeCaptialProcessor(outSystemID);
			if (tradeProc == null) {
				Tool.log("获取转出系统[" + outSystemID + "]的处理器RMI链接失败");
				result.result = -1L;
				result.remark = "获取转出系统RMI链接失败";
				conn.rollback();
				localReturnValue1 = result;
				return localReturnValue1;
			}
			RequestMsg req = new RequestMsg();
			req.setBankID("");
			req.setMethodName("doFundsTransfer");
			req.setParams(new Object[] { firmID, ((FirmID2SysFirmID) vecF2fOut.get(0)).sysFirmID, Double.valueOf(money), Integer.valueOf(1),
					String.valueOf(outActionID) });
			result = tradeProc.doWork(req);
			if (result.result == 0L) {
				Tool.log("划出系统[" + outSystemID + "]已经完成资金扣除,记录交易系统流水号，平台提交数据");
				DAO.modCapitalInfoStatusPT(outID, result.funID, 0, new Timestamp(System.currentTimeMillis()), conn);
				conn.commit();

				tradeProc = getTradeCaptialProcessor(inSystemID);
				if (tradeProc == null) {
					Tool.log("获取转入系统[" + inSystemID + "]的处理器RMI链接失败");
					result.remark = ("付款系统[" + outSystemID + "]扣款成功，收款系统[" + inSystemID + "]收款失败[原因：获取收款系统处理器失败]");
					localReturnValue1 = result;
					return localReturnValue1;
				}
				req.setParams(new Object[] { firmID, ((FirmID2SysFirmID) vecF2fIn.get(0)).sysFirmID, Double.valueOf(money), Integer.valueOf(0),
						String.valueOf(inActionID) });
				result = tradeProc.doWork(req);
				if (result.result != 0L) {
					Tool.log("收款系统[" + inSystemID + "]收款失败，失败原因[" + result.remark + "]");
					result.result = 0L;
					result.remark = ("付款系统[" + outSystemID + "]扣款成功，收款系统[" + inSystemID + "]收款失败[原因：" + result.remark + "]");
				} else if (result.result == 5L) {
					DAO.modCapitalInfoStatusPT(inID, result.funID, 0, new Timestamp(System.currentTimeMillis()), conn);
					conn.commit();
					Tool.log("收款系统[" + inSystemID + "]返回收款操作处理中");
					result.result = 0L;
					result.remark = ("付款系统[" + outSystemID + "]扣款成功，收款系统[" + inSystemID + "]收款返回处理中");
				} else {
					DAO.modCapitalInfoStatusPT(inID, result.funID, 0, new Timestamp(System.currentTimeMillis()), conn);
					conn.commit();
					Tool.log("收款系统[" + inSystemID + "]收款成功");
					result.remark = ("付款系统[" + outSystemID + "]扣款成功，收款系统[" + inSystemID + "]收款成功");
				}
			} else {
				Tool.log("划出系统[" + outSystemID + "]返回" + (result.result == 5L ? "处理中" : "失败") + "，平台强制回滚数据");
				conn.rollback();
				result.result = -1L;
				result.remark = ("付款系统处理失败，失败原因：" + result.remark);
			}
		} catch (Exception e) {
			Tool.log("资金划转系统异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue outMoneyFistAudit(long actionID, boolean flag) {
		Tool.log("流水号[" + actionID + "]的出金被一次审核" + (flag ? "通过" : "拒绝"));
		ReturnValue result = new ReturnValue();

		Vector<CapitalValue> vecCap = null;
		try {
			vecCap = DAO.getCapitalInfoListPT(" where actionID=" + actionID);
		} catch (Exception e) {
			Tool.log("按照流水号查询流水异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCap == null) || (vecCap.size() <= 0)) {
			Tool.log("没有查询到流水号[" + actionID + "]的流水信息");
			result.result = -1L;
			result.remark = ("没有查询到流水号[" + actionID + "]的流水信息");
			return result;
		}
		if (vecCap.size() > 1) {
			Tool.log("查询到多条流水号[" + actionID + "]的流水信息");
			result.result = -1L;
			result.remark = "存在相同流水号的流水信息";
			return result;
		}
		CapitalValue cv = (CapitalValue) vecCap.get(0);
		if (cv.status != 3) {
			Tool.log("流水号[" + actionID + "]的流水已经被处理");
			result.result = -1L;
			result.remark = "该笔流水已经被处理";
			return result;
		}
		if (cv.type != 1) {
			Tool.log("流水号[" + actionID + "]的流水不是出金流水");
			result.result = -1L;
			result.remark = "该笔流水不是出金流水";
			return result;
		}
		if (flag) {
			Connection conn = null;
			try {
				conn = DAO.getConnection();
				conn.setAutoCommit(false);
				DAO.modCapitalInfoStatusPT(cv.iD, cv.funID, 4, null, conn);
				DAO.modCapitalInfoNotePT(cv.iD, cv.note + "一次审核成功，等待二次审核", conn);
				conn.commit();
				result.result = 0L;
				result.remark = "审核成功";
			} catch (Exception e) {
				Tool.log("处理流水状态异常：" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "处理流水状态异常 ";
				try {
					conn.rollback();
				} catch (SQLException e1) {
					Tool.log("系统异常，回滚数据异常：" + Tool.getExceptionTrace(e1));
					result.result = -1L;
					result.remark = "系统异常，数据回滚异常";
				}
			} finally {
				DAO.closeStatement(null, null, conn);
			}
		} else if ("1".equals(cv.tradeSource)) {
			result = outMoneyBankAudit(cv, flag);
		} else if ("0".equals(cv.tradeSource)) {
			Connection conn = null;
			try {
				conn = DAO.getConnection();
				conn.setAutoCommit(false);
				DAO.modCapitalInfoStatusPT(cv.iD, cv.funID, 1, null, conn);
				DAO.modCapitalInfoNotePT(cv.iD, cv.note + "一次审核拒绝", conn);

				dataProcess.updateFrozenFunds(cv.firmID, cv.bankID, -1.0D * Arith.sub(cv.money, cv.feeMoney), conn);

				bankFrozenFuns(cv.firmID, cv.bankID, cv.account, -1.0D * Arith.sub(cv.money, cv.feeMoney), conn);
				conn.commit();
				result.result = 0L;
				result.remark = "审核成功";
			} catch (Exception e) {
				Tool.log("处理流水状态异常：" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "处理流水状态异常 ";
				try {
					conn.rollback();
				} catch (SQLException e1) {
					Tool.log("系统异常，回滚数据异常：" + Tool.getExceptionTrace(e1));
					result.result = -1L;
					result.remark = "系统异常，数据回滚异常";
				}
			} finally {
				DAO.closeStatement(null, null, conn);
			}
			TradeProcessorRMI tradeProc = getTradeCaptialProcessor(cv.systemID);
			if (tradeProc == null) {
				Tool.log("获取交易系统[" + cv.systemID + "]的处理器RMI链接失败");
				result.result = 0L;
				result.remark = "获取交易系统处理器失败";
				return result;
			}
			RequestMsg req = new RequestMsg();
			req.setBankID(cv.bankID);
			req.setMethodName("noticeOutMoneyResult");
			req.setParams(new Object[] { cv.funID, String.valueOf(cv.actionID), Boolean.valueOf(flag) });
			try {
				result = tradeProc.doWork(req);
			} catch (RemoteException e) {
				Tool.log("通知交易系统处理资金异常：" + Tool.getExceptionTrace(e));
			}
			if (result.result != 0L) {
				Tool.log("交易系统处理失败");
				result.result = 0L;
				return result;
			}
			Tool.log("交易系统处理流水失败状态操作成功");
		}
		return result;
	}

	public ReturnValue outMoneyAudit(long actionID, boolean flag) {
		Tool.log("流水号[" + actionID + "]的出金被审核" + (flag ? "通过" : "拒绝"));
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			conn.setAutoCommit(false);
			DAO.getBankList(" for update", conn);

			Vector<CapitalValue> vecCap = null;
			try {
				vecCap = DAO.getCapitalInfoListPT(" where actionID=" + actionID);
			} catch (Exception e) {
				Tool.log("查询流水异常：" + Tool.getExceptionTrace(e));
			}
			ReturnValue localReturnValue1;
			if ((vecCap == null) || (vecCap.size() <= 0)) {
				Tool.log("没有查询到流水号[" + actionID + "]的流水信息");
				result.result = -1L;
				result.remark = ("没有查询到流水号[" + actionID + "]的流水信息");
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (vecCap.size() > 1) {
				Tool.log("查询到多条流水号[" + actionID + "]的流水信息");
				result.result = -1L;
				result.remark = "存在相同流水号的流水信息";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			CapitalValue cv = (CapitalValue) vecCap.get(0);
			if (cv.status != 4) {
				Tool.log("流水号[" + actionID + "]的流水已经被处理");
				result.result = -1L;
				result.remark = "该笔流水已经被处理";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (cv.type != 1) {
				Tool.log("流水号[" + actionID + "]的流水不是出金流水");
				result.result = -1L;
				result.remark = "该笔流水不是出金流水";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if ("1".equals(cv.tradeSource)) {
				result = outMoneyBankAudit(cv, flag);
			} else if ("0".equals(cv.tradeSource)) {
				result = outMoneyMarketAudit(cv, flag);
			} else {
				Tool.log("该笔出金的发起发是[" + cv.tradeSource + "]，不是市场端出金，也不是银行端出金");
				result.result = -1L;
				result.remark = "该笔出金流水信息异常";
			}
		} catch (Exception e1) {
			Tool.log("系统异常：" + Tool.getExceptionTrace(e1));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue outMoneyMarketAudit(CapitalValue cv, boolean flag) {
		Tool.log("市场端出金审核,流水号[" + cv.actionID + "]的出金被审核" + (flag ? "通过" : "拒绝"));
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			if (flag) {
				InOutMoney outMoney = new InOutMoney();
				outMoney.firmID = cv.firmID;
				outMoney.actionID = cv.actionID;
				outMoney.bankID = cv.bankID;
				outMoney.remark = cv.note;
				outMoney.money = cv.money;
				outMoney.systemID = cv.systemID;
				outMoney.sysFirmID = cv.sysFirmID;
				result = outMoneyPtToBank(outMoney, cv.feeMoney);
				if (result.result == 5L) {
					Tool.log("出金请求到银行，银行返回处理中");
					try {
						conn.setAutoCommit(false);

						DAO.modCapitalInfoStatusPT(cv.iD, cv.funID, 2, null, conn);
						DAO.modCapitalInfoNotePT(cv.iD, cv.note + "审核通过，银行返回处理中", conn);
					} catch (Exception e) {
						Tool.log("修改流水状态为处理中异常：" + Tool.getExceptionTrace(e));
						result.result = -1L;
						result.remark = "出金审核通过，银行返回处理中，市场端修改流水状态异常";
						conn.rollback();
					} finally {
						conn.setAutoCommit(true);
					}
					conn.commit();
					ReturnValue localReturnValue2 = result;
					return localReturnValue2;
				}
				if (result.result == 0L) {
					Tool.log("出金到银行，银行返回出金成功");
				} else {
					Tool.log("出金到银行，银行返回失败");
					flag = false;
				}
			}
			try {
				conn.setAutoCommit(false);

				DAO.modCapitalInfoStatusPT(cv.iD, cv.funID, flag ? 0 : 1, null, conn);
				DAO.modCapitalInfoNotePT(cv.iD, cv.note + (flag ? "审核成功，出金成功" : "审核拒绝、出金失败"), conn);

				dataProcess.updateFrozenFunds(cv.firmID, cv.bankID, -1.0D * Arith.sub(cv.money, cv.feeMoney), conn);

				bankFrozenFuns(cv.firmID, cv.bankID, cv.account, -1.0D * Arith.sub(cv.money, cv.feeMoney), conn);
				if (flag) {
					dataProcess.updateFundsFull(cv.firmID, dataProcess.getOUTSUMMARY() + "", (String) dataProcess.getBANKSUB().get(cv.bankID),
							cv.money, cv.actionID, conn);
				}
				conn.commit();

				TradeProcessorRMI tradeProc = getTradeCaptialProcessor(cv.systemID);
				ReturnValue localReturnValue1;
				if (tradeProc == null) {
					Tool.log("获取交易系统[" + cv.systemID + "]的处理器RMI链接失败");
					result.result = 0L;
					result.remark = "获取交易系统处理器失败";
					localReturnValue1 = result;

					conn.setAutoCommit(true);
					return localReturnValue1;
				}
				RequestMsg req = new RequestMsg();
				req.setBankID(cv.bankID);
				req.setMethodName("noticeOutMoneyResult");
				req.setParams(new Object[] { cv.funID, String.valueOf(cv.actionID), Boolean.valueOf(flag) });
				result = tradeProc.doWork(req);
				if (result.result != 0L) {
					Tool.log("交易系统处理失败");
					result.result = 0L;
					localReturnValue1 = result;

					conn.setAutoCommit(true);
					return localReturnValue1;
				}
				Tool.log("交易系统处理流水状态为[" + (flag ? "成功" : "失败") + "]操作成功");
			} catch (Exception e) {
				Tool.log("操作拒绝出金异常：" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "操作拒绝出金系统异常";
				conn.rollback();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("系统异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue outMoneyBankAudit(CapitalValue cv, boolean flag) {
		Tool.log("银行端出金审核,流水号[" + cv.actionID + "]的出金被审核" + (flag ? "通过" : "拒绝"));
		ReturnValue result = new ReturnValue();

		Vector<CapitalValue> vecCapitalBank = null;
		try {
			vecCapitalBank = DAO.getCapitalInfoList(" where actionID=" + cv.actionID);
		} catch (Exception e1) {
			Tool.log("查询平台到银行流水信息异常：" + Tool.getExceptionTrace(e1));
		}
		if ((vecCapitalBank == null) || (vecCapitalBank.size() <= 0)) {
			Tool.log("平台到银行端流水信息中没有查到平台流水号[" + cv.actionID + "]的流水");
			result.result = -1L;
			result.remark = "流水信息异常";
			return result;
		}
		CapitalValue cvOut = null;
		for (CapitalValue c : vecCapitalBank) {
			if (c.type != 2) {
				cvOut = c;
			}
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();

			BankAdapterRMI bankadapter = getAdapter(cv.bankID);
			ReturnValue localReturnValue2;
			if (bankadapter == null) {
				result.result = -1L;
				result.remark = "审核完毕，通知银行结果失败、原因[处理器连接适配器失败]";
				Tool.log("处理器连接适配器[" + cv.bankID + "]失败");
				localReturnValue2 = result;
				return localReturnValue2;
			}
			TransferInfoValue payInfo = getPayInfo(cv.bankID, cv.firmID, 1, conn);

			TransferInfoValue receiveInfo = getReceiveInfo(cv.bankID, cv.firmID, 1, conn);
			BankValue bv = DAO.getBank(cv.bankID);

			Vector<CorrespondValue> vecCorr = DAO.getCorrespondList(" where firmID='" + cv.firmID + "' and bankID='" + cv.bankID + "'", conn);
			if ((vecCorr == null) || (vecCorr.size() != 1)) {
				Tool.log("没有找到平台号[" + cv.firmID + "]和银行[" + cv.bankID + "]的签约记录");
				result.result = -1L;
				result.remark = "没有找到签约关系";
				conn.rollback();
				localReturnValue2 = result;
				return localReturnValue2;
			}
			OutMoneyVO outMoneyInfo = new OutMoneyVO(cv.bankID, bv.bankName, cv.money, cv.firmID, ((CorrespondValue) vecCorr.get(0)).contact, payInfo,
					receiveInfo, cv.actionID, 0, cvOut.funID);
			if (flag) {
				try {
					conn.setAutoCommit(false);

					DAO.modCapitalInfoStatusPT(cv.iD, cv.funID, 0, null, conn);
					DAO.modCapitalInfoNotePT(cv.iD, cv.note + "审核成功", conn);
					DAO.modCapitalInfoStatus(cvOut.iD, cvOut.funID, 0, null, conn);
					DAO.modCapitalInfoNote(cvOut.iD, cvOut.note + "审核成功", conn);

					dataProcess.updateFrozenFunds(cv.firmID, cv.bankID, -1.0D * Arith.sub(cv.money, cv.feeMoney), conn);

					bankFrozenFuns(cv.firmID, cv.bankID, cv.account, -1.0D * Arith.sub(cv.money, cv.feeMoney), conn);

					dataProcess.updateFundsFull(cv.firmID, dataProcess.getOUTSUMMARY() + "", (String) dataProcess.getBANKSUB().get(cv.bankID),
							cv.money, cv.actionID, conn);

					InOutMoney outMoney = new InOutMoney();
					outMoney.firmID = cv.firmID;
					outMoney.actionID = cv.actionID;
					outMoney.bankID = cv.bankID;
					outMoney.remark = cv.note;
					outMoney.money = cv.money;
					outMoney.systemID = cv.systemID;
					outMoney.sysFirmID = cv.sysFirmID;
					TradeProcessorRMI tradeProc = getTradeCaptialProcessor(cv.systemID);
					if (tradeProc == null) {
						Tool.log("获取交易系统[" + cv.systemID + "]的处理器RMI链接失败");
						result.result = -1L;
						result.remark = "获取交易系统处理器失败";
						conn.rollback();
						ReturnValue localReturnValue1 = result;

						conn.setAutoCommit(true);
						return localReturnValue1;
					}
					RequestMsg req = new RequestMsg();
					req.setBankID("");
					req.setMethodName("outMoney");
					req.setParams(new Object[] { outMoney, Integer.valueOf(2) });
					result = tradeProc.doWork(req);
					if (result.result == 0L) {
						Tool.log("交易系统端处理器出金成功");
						DAO.modCapitalInfoStatusPT(cv.iD, result.funID, 0, null, conn);
						conn.commit();
					} else if (result.result == 5L) {
						Tool.log("交易系统端处理器出金结果成功的操作返回处理中，平台处理器提交数据");
						DAO.modCapitalInfoStatusPT(cv.iD, result.funID, 0, null, conn);
						conn.commit();
					} else {
						Tool.log("交易系统端处理失败，直接执行审核拒绝操作");
						conn.rollback();
					}
					if ((result.result == 0L) || (result.result == 5L)) {
						result = bankadapter.outMoneyBackBank(outMoneyInfo, true);
						if (result.result != 0L) {
							result.result = 0L;
							result.remark = ("市场端操作成功，银行处理失败[" + result.remark + "]");
						}
					} else {
						result.result = -1L;
						result.remark = "审核通过操作失败";
					}
				} catch (Exception e) {
					Tool.log("修改流水状态异常：" + Tool.getExceptionTrace(e));
					result.result = -1L;
					result.remark = "系统异常";
					conn.rollback();
				} finally {
					conn.setAutoCommit(true);
				}
			}
			if (!flag) {
				try {
					conn.setAutoCommit(false);

					DAO.modCapitalInfoStatusPT(cv.iD, cv.funID, 1, null, conn);
					DAO.modCapitalInfoNotePT(cv.iD, cv.note + "审核拒绝", conn);
					DAO.modCapitalInfoStatus(cvOut.iD, cvOut.funID, 1, null, conn);
					DAO.modCapitalInfoNote(cvOut.iD, cvOut.note + "审核拒绝", conn);

					dataProcess.updateFrozenFunds(cv.firmID, cv.bankID, -1.0D * Arith.sub(cv.money, cv.feeMoney), conn);

					bankFrozenFuns(cv.firmID, cv.bankID, cv.account, -1.0D * Arith.sub(cv.money, cv.feeMoney), conn);
					conn.commit();
					result = bankadapter.outMoneyBackBank(outMoneyInfo, false);
					if (result.result != 0L) {
						result.result = 0L;
						result.remark = ("市场端操作成功，银行处理失败[" + result.remark + "]");
					}
				} catch (Exception e) {
					Tool.log("修改流水状态异常：" + Tool.getExceptionTrace(e));
					result.result = -1L;
					result.remark = "系统异常";
					conn.rollback();
				} finally {
					conn.setAutoCommit(true);
				}
			}
		} catch (Exception e) {
			Tool.log("系统异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue getAllCapitalList(String firmID, String startDate, String endDate) {
		Tool.log("查询平台号[" + firmID + "]在日期[" + startDate + "--" + endDate + "]内的所有转账流水");
		ReturnValue result = new ReturnValue();
		Vector<CapitalValue> vecCap = null;
		try {
			vecCap = DAO.getCapitalInfoListPT(" where firmID='" + firmID + "' and trunc(createTime)>=to_date('" + startDate
					+ "','yyyy-MM-dd') and trunc(createTime)<=to_date('" + endDate + "','yyyy-MM-dd')");
		} catch (Exception e) {
			Tool.log("查询流水信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCap == null) || (vecCap.size() < 0)) {
			Tool.log("查询流水失败");
			result.result = -1L;
			result.remark = "查询失败";
			result.msg = new Object[] { new Vector() };
			return result;
		}
		result.result = 0L;
		result.remark = "查询成功";
		result.msg = new Object[] { vecCap };
		return result;
	}

	public ReturnValue getAllCapitalList(int pageSize, int pageIndex, String firmID, String startDate, String endDate, String orderBy) {
		Tool.log("查询平台号[" + firmID + "]在日期[" + startDate + "--" + endDate + "]内的所有转账流水");
		ReturnValue result = new ReturnValue();
		Vector<CapitalValue> vecCap = null;
		try {
			vecCap = DAO.getCapitalInfoListPT(pageSize, pageIndex, " where firmID='" + firmID + "' and trunc(createTime)>=to_date('" + startDate
					+ "','yyyy-MM-dd') and trunc(createTime)<=to_date('" + endDate + "','yyyy-MM-dd')", orderBy);
		} catch (Exception e) {
			Tool.log("查询流水信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCap == null) || (vecCap.size() < 0)) {
			Tool.log("查询流水失败");
			result.result = -1L;
			result.remark = "查询失败";
			result.msg = new Object[] { new Vector() };
			return result;
		}
		result.result = 0L;
		result.remark = "查询成功";
		result.msg = new Object[] { vecCap };
		return result;
	}

	public ReturnValue getAllCapitalList(String filter) {
		Tool.log("查询固定条件的转账流水");
		ReturnValue result = new ReturnValue();
		Vector<CapitalValue> vecCap = null;
		try {
			vecCap = DAO.getCapitalInfoListPT(filter);
		} catch (Exception e) {
			Tool.log("查询流水信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCap == null) || (vecCap.size() < 0)) {
			Tool.log("查询流水失败");
			result.result = -1L;
			result.remark = "查询失败";
			result.msg = new Object[] { new Vector() };
			return result;
		}
		result.result = 0L;
		result.remark = "查询成功";
		result.msg = new Object[] { vecCap };
		return result;
	}

	public ReturnValue getRZData(java.util.Date date) {
		Tool.log("获取所有交易系统[" + Tool.fmtDate(date) + "]的日终数据");
		ReturnValue result = new ReturnValue();
		Vector<SystemMessage> vecSystem = null;
		try {
			vecSystem = DAO.getSystemMessages("");
		} catch (SQLException e) {
			Tool.log("获取交易系统列表信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecSystem == null) || (vecSystem.size() <= 0)) {
			Tool.log("查询交易系统列表信息失败");
			result.result = -1L;
			result.remark = "位查询到交易系统列表信息";
			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				for (SystemMessage sm : vecSystem) {
					Vector<RZQS> vec = DAO.getRZQSData(date, sm.systemID);
					if ((vec != null) && (vec.size() > 0) && (((RZQS) vec.get(0)).doFlag != 0)) {
						Tool.log("平台已经存在交易系统[" + sm.systemID + "][" + Tool.fmtDate(date) + "]的日终数据，并且数据已经被结算");
						result.result = -1L;
						result.remark = ("系统[" + sm.systemID + "]在[" + Tool.fmtDate(date) + "]的日终数据已经被结算，不允许被覆盖");
						conn.rollback();
						ReturnValue localReturnValue1 = result;

						conn.setAutoCommit(true);
						return localReturnValue1;
					}
					result = getRZDataBySystemID(date, sm.systemID, conn);
					if (result.result != 0L) {
						Tool.log("对交易系统[" + sm.systemID + "]的日终数据进行操作失败，数据回滚");
						conn.rollback();
						result.result = -1L;
					}
				}
				if (result.result == 0L) {
					conn.commit();
				} else {
					conn.rollback();
				}
			} catch (Exception e) {
				Tool.log("查询各系统日终数据并保持异常：" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "系统异常";
				conn.rollback();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("系统异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue getRZDataBySystemID(java.util.Date date, String systemID, Connection conn) {
		Tool.log("获取交易系统[" + systemID + "][" + Tool.fmtDate(date) + "]的日终数据");
		ReturnValue result = new ReturnValue();
		TradeProcessorRMI tradeProc = getTradeCaptialProcessor(systemID);
		if (tradeProc == null) {
			Tool.log("获取交易系统[" + systemID + "]的处理器RMI链接失败");
			result.result = -1L;
			result.remark = ("连接交易系统[" + systemID + "]的处理器失败");
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getRZData");
		req.setParams(new Object[] { date });
		try {
			result = tradeProc.doWork(req);
		} catch (RemoteException e1) {
			Tool.log("调用交易系统[" + systemID + "]的处理器方法异常：" + Tool.getExceptionTrace(e1));
			result.result = -1L;
			result.remark = ("调用系统[" + systemID + "]的处理器异常");
			return result;
		}
		if (result.result != 0L) {
			Tool.log("查询交易系统[" + systemID + "]的日终数据失败，已经插入的数据进行回滚");
			result.result = -1L;
			if ((result.remark == null) || ("".equals(result.remark))) {
				result.remark = ("查询系统[" + systemID + "]的日终数据失败");
			}
			return result;
		}
		Tool.log("查询交易系统[" + systemID + "]的日终数据成功，开始讲查询到的数据插入数据库");
		Vector<RZQS> vecRZData = (Vector) result.msg[0];
		try {
			result.result = DAO.addRZQS(vecRZData, systemID, conn);
		} catch (SQLException e) {
			Tool.log("保存数据异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "插入数据异常";
			return result;
		}
		if (result.result != vecRZData.size()) {
			Tool.log("插入的数据条数[" + result.result + "]和查询到的数据条数[" + vecRZData.size() + "]不相符，保存数据部分失败，数据回滚");
			result.result = -1L;
			result.remark = "部分数据保存失败，数据回滚";
			return result;
		}
		result.result = 0L;
		result.remark = "保存数据成功";
		return result;
	}

	public ReturnValue getRZDataBySystemID(java.util.Date date, String systemID) {
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				Vector<RZQS> vec = DAO.getRZQSData(date, systemID);
				if ((vec != null) && (vec.size() > 0) && (((RZQS) vec.get(0)).doFlag != 0)) {
					Tool.log("平台已经存在交易系统[" + systemID + "][" + Tool.fmtDate(date) + "]的日终数据，并且数据已经被结算");
					result.result = -1L;
					result.remark = ("系统[" + systemID + "]在[" + Tool.fmtDate(date) + "]的日终数据已经被结算，不允许被覆盖");
					conn.rollback();
					ReturnValue localReturnValue1 = result;

					conn.setAutoCommit(true);
					return localReturnValue1;
				}
				conn.setAutoCommit(false);
				result = getRZDataBySystemID(date, systemID, conn);
				if (result.result != 0L) {
					conn.rollback();
				} else {
					conn.commit();
				}
			} catch (Exception e) {
				Tool.log("系统异常，数据回滚:" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "系统异常";
				conn.rollback();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("系统异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue moneyAduitToBank(long actionID, boolean flag) {
		Tool.log("审核平台到银行的单边帐[" + actionID + "]" + (flag ? "通过" : "拒绝"));
		ReturnValue result = new ReturnValue();

		Vector<CapitalValue> vecCapBank = null;
		try {
			vecCapBank = DAO.getCapitalInfoList(" where actionID=" + actionID + " and type<>2");
		} catch (Exception e) {
			Tool.log("按平台流水号查询平台到银行流水信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCapBank == null) || (vecCapBank.size() <= 0)) {
			Tool.log("按平台流水号[" + actionID + "]查询平台到银行流水信息失败");
			result.result = -1L;
			result.remark = "未查询到流水信息";
			return result;
		}
		CapitalValue cvBank = (CapitalValue) vecCapBank.get(0);

		Vector<CapitalValue> vecCapSystem = null;
		try {
			vecCapSystem = DAO.getCapitalInfoListPT(" where actionID=" + actionID + " and type<>2");
		} catch (Exception e) {
			Tool.log("按平台流水号查询平台到交易系统流水信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCapSystem == null) || (vecCapSystem.size() <= 0)) {
			Tool.log("按平台流水号[" + actionID + "]查询平台到交易系统流水信息失败");
			result.result = -1L;
			result.remark = "平台到交易系统流水信息中没有对应的流水信息";
			return result;
		}
		CapitalValue cvSystem = (CapitalValue) vecCapSystem.get(0);
		if (cvBank.type == 0) {
			result = inMoneyAduitToBank(cvBank, cvSystem, flag);
		} else if (cvBank.type == 1) {
			result = outMoneyAduitToBank(cvBank, cvSystem, flag);
		} else {
			Tool.log("平台到银行的流水[" + actionID + "]类型为[" + cvBank.type + "]，不是出入金流水");
			result.result = -1L;
			result.remark = "流水类型错误";
			return result;
		}
		return result;
	}

	public ReturnValue inMoneyAduitToBank(CapitalValue cvBank, CapitalValue cvSystem, boolean flag) {
		Tool.log("审核平台到银行的入金流水" + (flag ? "通过" : "拒绝"));
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				if (cvSystem.status == 2) {
					Tool.log("平台到交易系统流水信息中对应的流水为处理中状态，一并将该流水审核" + (flag ? "通过" : "拒绝"));
					DAO.modCapitalInfoStatusPT(cvSystem.iD, cvSystem.funID, flag ? 0 : 1, new Timestamp(System.currentTimeMillis()), conn);
					DAO.modCapitalInfoNotePT(cvSystem.iD, cvSystem.note + "单边帐审核" + (flag ? "通过" : "拒绝"), conn);

					TradeProcessorRMI tradeSystem = getTradeCaptialProcessor(cvSystem.systemID);
					if (tradeSystem == null) {
						Tool.log("获取交易系统[" + cvSystem.systemID + "]的处理器链接失败");
					} else {
						RequestMsg req = new RequestMsg();
						req.setBankID(cvSystem.bankID);
						req.setMethodName("noticeInMoneyResult");
						req.setParams(new Object[] { cvSystem.funID, String.valueOf(cvSystem.actionID), Boolean.valueOf(flag) });
						try {
							ReturnValue rv = tradeSystem.doWork(req);
							Tool.log("通知交易系统[" + cvSystem.systemID + "]处理流水结果[" + rv.remark + "]");
						} catch (RemoteException e) {
							Tool.log("通知交易系统[" + cvSystem.systemID + "]处理流水异常：" + Tool.getExceptionTrace(e));
						}
					}
				}
				DAO.modCapitalInfoStatus(cvBank.iD, cvBank.funID, flag ? 0 : 1, new Timestamp(System.currentTimeMillis()), conn);
				DAO.modCapitalInfoNote(cvBank.iD, cvBank.note + "单边帐审核" + (flag ? "通过" : "拒绝"), conn);
				if (flag) {
					Tool.log("审核通过，增加资金");
					dataProcess.updateFundsFull(cvBank.firmID, dataProcess.getINSUMMARY() + "", (String) dataProcess.getBANKSUB().get(cvBank.bankID),
							cvBank.money, cvBank.actionID, conn);
				}
				conn.commit();
				result.result = 0L;
				result.remark = ("审核" + (flag ? "通过" : "拒绝") + "操作成功");
			} catch (Exception e) {
				Tool.log("系统异常：" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "系统异常";
				conn.rollback();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("系统异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue outMoneyAduitToBank(CapitalValue cvBank, CapitalValue cvSystem, boolean flag) {
		Tool.log("审核平台到银行的出金流水" + (flag ? "通过" : "拒绝"));
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				if ("1".equals(cvBank.tradeSource)) {
					if (cvSystem.status == 2) {
						DAO.modCapitalInfoStatusPT(cvSystem.iD, cvSystem.funID, flag ? 0 : 1, new Timestamp(System.currentTimeMillis()), conn);
						DAO.modCapitalInfoNotePT(cvSystem.iD, cvSystem.note + "单边帐审核" + (flag ? "通过" : "拒绝"), conn);

						dataProcess.updateFrozenFunds(cvSystem.firmID, cvSystem.bankID, -1.0D * cvSystem.money, conn);
						bankFrozenFuns(cvSystem.firmID, cvSystem.bankID, null, -1.0D * cvSystem.money, conn);
						if (flag) {
							dataProcess.updateFundsFull(cvSystem.firmID, dataProcess.getOUTSUMMARY() + "",
									(String) dataProcess.getBANKSUB().get(cvSystem.bankID), cvSystem.money, cvSystem.actionID, conn);
						}
						TradeProcessorRMI tradeSystem = getTradeCaptialProcessor(cvSystem.systemID);
						if (tradeSystem == null) {
							Tool.log("获取交易系统[" + cvSystem.systemID + "]的处理器链接失败");
						} else {
							RequestMsg req = new RequestMsg();
							req.setBankID(cvSystem.bankID);
							req.setMethodName("noticeOutMoneyResult");
							req.setParams(new Object[] { cvSystem.funID, String.valueOf(cvSystem.actionID), Boolean.valueOf(flag) });
							try {
								ReturnValue rv = tradeSystem.doWork(req);
								Tool.log("通知交易系统[" + cvSystem.systemID + "]处理流水结果[" + rv.remark + "]");
							} catch (RemoteException e) {
								Tool.log("通知交易系统[" + cvSystem.systemID + "]处理流水异常：" + Tool.getExceptionTrace(e));
							}
						}
					}
					DAO.modCapitalInfoStatus(cvBank.iD, cvBank.funID, flag ? 0 : 1, new Timestamp(System.currentTimeMillis()), conn);
					DAO.modCapitalInfoNote(cvBank.iD, cvBank.note + "单边帐审核" + (flag ? "通过" : "拒绝"), conn);
				} else if ("2".equals(cvBank.tradeSource)) {
					if (cvSystem.status == 2) {
						DAO.modCapitalInfoStatusPT(cvSystem.iD, cvSystem.funID, flag ? 0 : 1, new Timestamp(System.currentTimeMillis()), conn);
						DAO.modCapitalInfoNotePT(cvSystem.iD, cvSystem.note + "单边帐审核" + (flag ? "通过" : "拒绝"), conn);

						TradeProcessorRMI tradeSystem = getTradeCaptialProcessor(cvSystem.systemID);
						if (tradeSystem == null) {
							Tool.log("获取交易系统[" + cvSystem.systemID + "]的处理器链接失败");
						} else {
							RequestMsg req = new RequestMsg();
							req.setBankID(cvSystem.bankID);
							req.setMethodName("noticeOutMoneyResult");
							req.setParams(new Object[] { cvSystem.funID, String.valueOf(cvSystem.actionID), Boolean.valueOf(flag) });
							try {
								ReturnValue rv = tradeSystem.doWork(req);
								Tool.log("通知交易系统[" + cvSystem.systemID + "]处理流水结果[" + rv.remark + "]");
							} catch (RemoteException e) {
								Tool.log("通知交易系统[" + cvSystem.systemID + "]处理流水异常：" + Tool.getExceptionTrace(e));
							}
						}
					}
					dataProcess.updateFrozenFunds(cvSystem.firmID, cvSystem.bankID, -1.0D * cvSystem.money, conn);
					bankFrozenFuns(cvSystem.firmID, cvSystem.bankID, null, -1.0D * cvSystem.money, conn);
					if (flag) {
						dataProcess.updateFundsFull(cvSystem.firmID, dataProcess.getOUTSUMMARY() + "",
								(String) dataProcess.getBANKSUB().get(cvSystem.bankID), cvSystem.money, cvSystem.actionID, conn);
					}
					DAO.modCapitalInfoStatus(cvBank.iD, cvBank.funID, flag ? 0 : 1, new Timestamp(System.currentTimeMillis()), conn);
					DAO.modCapitalInfoNote(cvBank.iD, cvBank.note + "单边帐审核" + (flag ? "通过" : "拒绝"), conn);
				} else {
					Tool.log("该笔出金流水的发起方是[" + cvBank.tradeSource + "]信息异常");
					result.result = -1L;
					result.remark = "出金流水信息异常";
					conn.rollback();
					ReturnValue localReturnValue1 = result;

					conn.setAutoCommit(true);
					return localReturnValue1;
				}
				conn.commit();
				result.result = 0L;
				result.remark = ("审核" + (flag ? "通过" : "拒绝") + "操作成功");
			} catch (Exception e) {
				Tool.log("系统异常：" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "系统异常";
				conn.rollback();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("系统异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue moneyAduitToSystem(long actionID, boolean flag) {
		Tool.log("审核平台到交易系统的单边帐[" + actionID + "]" + (flag ? "通过" : "拒绝"));
		ReturnValue result = new ReturnValue();

		Vector<CapitalValue> vecCapSystem = null;
		try {
			vecCapSystem = DAO.getCapitalInfoListPT(" where actionID=" + actionID + " and type<>2");
		} catch (Exception e) {
			Tool.log("按平台流水号查询平台到交易系统流水信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCapSystem == null) || (vecCapSystem.size() <= 0)) {
			Tool.log("按平台流水号[" + actionID + "]查询平台到交易系统流水信息失败");
			result.result = -1L;
			result.remark = "未查询到流水信息";
			return result;
		}
		CapitalValue cvSystem = (CapitalValue) vecCapSystem.get(0);

		Vector<CapitalValue> vecCapBank = null;
		try {
			vecCapBank = DAO.getCapitalInfoList(" where actionID=" + actionID + " and type<>2");
		} catch (Exception e) {
			Tool.log("按平台流水号查询平台到银行流水信息异常：" + Tool.getExceptionTrace(e));
		}
		CapitalValue cvBank = null;
		if ((vecCapBank != null) && (vecCapBank.size() > 0)) {
			cvBank = (CapitalValue) vecCapBank.get(0);
		}
		if (cvSystem.type == 0) {
			result = inMoneyAduitToSystem(cvBank, cvSystem, flag);
		} else if (cvSystem.type == 1) {
			result = outMoneyAduitToSystem(cvBank, cvSystem, flag);
		} else {
			Tool.log("平台到交易系统的流水[" + actionID + "]类型为[" + cvBank.type + "]，不是出入金流水");
			result.result = -1L;
			result.remark = "流水类型错误";
			return result;
		}
		TradeProcessorRMI tradeSystem = getTradeCaptialProcessor(cvSystem.systemID);
		if (tradeSystem == null) {
			Tool.log("获取交易系统[" + cvSystem.systemID + "]的处理器链接失败");
		} else {
			RequestMsg req = new RequestMsg();
			req.setBankID(cvSystem.bankID);
			if (cvSystem.type == 0) {
				req.setMethodName("noticeInMoneyResult");
			} else if (cvSystem.type == 1) {
				req.setMethodName("noticeOutMoneyResult");
			}
			req.setParams(new Object[] { cvSystem.funID, String.valueOf(cvSystem.actionID), Boolean.valueOf(flag) });
			try {
				ReturnValue rv = tradeSystem.doWork(req);
				Tool.log("通知交易系统[" + cvSystem.systemID + "]处理流水结果[" + rv.remark + "]");
			} catch (RemoteException e) {
				Tool.log("通知交易系统[" + cvSystem.systemID + "]处理流水异常：" + Tool.getExceptionTrace(e));
			}
		}
		return result;
	}

	public ReturnValue inMoneyAduitToSystem(CapitalValue cvBank, CapitalValue cvSystem, boolean flag) {
		Tool.log("审核平台到银行的入金流水" + (flag ? "通过" : "拒绝"));
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				if ((cvBank != null) && (cvBank.status == 2)) {
					Tool.log("平台到银行流水信息中对应的流水为处理中状态，一并将该流水审核" + (flag ? "通过" : "拒绝"));
					DAO.modCapitalInfoStatus(cvBank.iD, cvSystem.funID, flag ? 0 : 1, new Timestamp(System.currentTimeMillis()), conn);
					DAO.modCapitalInfoNote(cvBank.iD, cvSystem.note + "单边帐审核" + (flag ? "通过" : "拒绝"), conn);
					if (flag) {
						Tool.log("审核通过，增加资金");
						dataProcess.updateFundsFull(cvBank.firmID, dataProcess.getINSUMMARY() + "",
								(String) dataProcess.getBANKSUB().get(cvBank.bankID), cvBank.money, cvBank.actionID, conn);
					}
				}
				DAO.modCapitalInfoStatusPT(cvSystem.iD, cvSystem.funID, flag ? 0 : 1, new Timestamp(System.currentTimeMillis()), conn);
				DAO.modCapitalInfoNotePT(cvSystem.iD, cvSystem.note + "单边帐审核" + (flag ? "通过" : "拒绝"), conn);

				conn.commit();
				result.result = 0L;
				result.remark = ("审核" + (flag ? "通过" : "拒绝") + "操作成功");
			} catch (Exception e) {
				Tool.log("系统异常：" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "系统异常";
				conn.rollback();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("系统异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue outMoneyAduitToSystem(CapitalValue cvBank, CapitalValue cvSystem, boolean flag) {
		Tool.log("审核平台到交易系统的出金流水" + (flag ? "通过" : "拒绝"));
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				if ("0".equals(cvSystem.tradeSource)) {
					if ((cvBank != null) && (cvBank.status == 2)) {
						DAO.modCapitalInfoStatus(cvBank.iD, cvBank.funID, flag ? 0 : 1, new Timestamp(System.currentTimeMillis()), conn);
						DAO.modCapitalInfoNote(cvBank.iD, cvBank.note + "单边帐审核" + (flag ? "通过" : "拒绝"), conn);
					}
					dataProcess.updateFrozenFunds(cvSystem.firmID, cvSystem.bankID, -1.0D * cvSystem.money, conn);
					bankFrozenFuns(cvSystem.firmID, cvSystem.bankID, null, -1.0D * cvSystem.money, conn);
					if (flag) {
						dataProcess.updateFundsFull(cvSystem.firmID, dataProcess.getOUTSUMMARY() + "",
								(String) dataProcess.getBANKSUB().get(cvSystem.bankID), cvSystem.money, cvSystem.actionID, conn);
					}
					DAO.modCapitalInfoStatusPT(cvSystem.iD, cvSystem.funID, flag ? 0 : 1, new Timestamp(System.currentTimeMillis()), conn);
					DAO.modCapitalInfoNotePT(cvSystem.iD, cvSystem.note + "单边帐审核" + (flag ? "通过" : "拒绝"), conn);
				} else if ("1".equals(cvSystem.tradeSource)) {
					if ((cvBank != null) && (cvBank.status == 2)) {
						DAO.modCapitalInfoStatus(cvBank.iD, cvBank.funID, flag ? 0 : 1, new Timestamp(System.currentTimeMillis()), conn);
						DAO.modCapitalInfoNote(cvBank.iD, cvBank.note + "单边帐审核" + (flag ? "通过" : "拒绝"), conn);

						dataProcess.updateFrozenFunds(cvSystem.firmID, cvSystem.bankID, -1.0D * cvSystem.money, conn);
						bankFrozenFuns(cvSystem.firmID, cvSystem.bankID, null, -1.0D * cvSystem.money, conn);
					}
					if (flag) {
						dataProcess.updateFundsFull(cvSystem.firmID, dataProcess.getOUTSUMMARY() + "",
								(String) dataProcess.getBANKSUB().get(cvSystem.bankID), cvSystem.money, cvSystem.actionID, conn);
					}
					DAO.modCapitalInfoStatusPT(cvSystem.iD, cvSystem.funID, flag ? 0 : 1, new Timestamp(System.currentTimeMillis()), conn);
					DAO.modCapitalInfoNotePT(cvSystem.iD, cvSystem.note + "单边帐审核" + (flag ? "通过" : "拒绝"), conn);
				} else {
					Tool.log("该笔出金流水的发起方是[" + cvSystem.tradeSource + "]信息异常");
					result.result = -1L;
					result.remark = "出金流水信息异常";
					conn.rollback();
					ReturnValue localReturnValue1 = result;

					conn.setAutoCommit(true);
					return localReturnValue1;
				}
				conn.commit();
				result.result = 0L;
				result.remark = ("审核" + (flag ? "通过" : "拒绝") + "操作成功");
			} catch (Exception e) {
				Tool.log("系统异常：" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "系统异常";
				conn.rollback();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("系统异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue addOtherFirmMsg(String platformID, String sysFirmID, String systemID) {
		Tool.log("统一开户平台修改账户信息，增加交易系统[" + systemID + "]的交易商[" + sysFirmID + "]对应平台号[" + platformID + "]");
		ReturnValue result = new ReturnValue();

		FirmValue fv = null;
		try {
			fv = DAO.getFirm(platformID);
		} catch (Exception e) {
			Tool.log("查询平台号是否存在异常：" + Tool.getExceptionTrace(e));
		}
		if (fv == null) {
			Tool.log("平台号[" + platformID + "]不存在");
			result.result = -1L;
			result.remark = "平台账号不存在";
			return result;
		}
		Vector<FirmID2SysFirmID> vecFirmMapping = null;
		try {
			vecFirmMapping = DAO
					.getFirmMapping(" where platfirmid='" + platformID + "' and moduleid='" + sysFirmID + "' and firmid='" + systemID + "'");
		} catch (SQLException e) {
			Tool.log("查询交易商映射关系表异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecFirmMapping == null) || (vecFirmMapping.size() <= 0)) {
			Tool.log("查询交易商映射关系失败");
			result.result = -1L;
			result.remark = "不存在该交易商和平台号的映射关系";
			return result;
		}
		Vector<FirmID2SysFirmID> vecF2f = null;
		try {
			vecF2f = DAO.getFirmID2SysFirmID(" where firmID='" + platformID + "'");
		} catch (SQLException e) {
			Tool.log("查询对应关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		Vector<CorrespondValue> vecCorr = null;
		try {
			vecCorr = DAO.getCorrespondList(" where firmID='" + platformID + "'");
		} catch (Exception e) {
			Tool.log("查询平台号是否签约异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		if ((vecF2f == null) || (vecF2f.size() <= 0)) {
			if ((vecCorr == null) || (vecCorr.size() <= 0)) {
				Tool.log("平台号[" + platformID + "]还没有签约银行，不用增加数据，直接返回成功");
				result.result = 0L;
				result.remark = "处理成功";
			} else {
				Tool.log("平台号[" + platformID + "]的没有对应关系但有签约信息，信息异常");
				result.result = -1L;
				result.remark = "平台账户信息异常";
			}
		} else if ((vecCorr == null) || (vecCorr.size() <= 0)) {
			Tool.log("平台号[" + platformID + "]的有对应关系但没有签约信息，信息异常");
			result.result = -1L;
			result.remark = "平台账户信息异常";
		} else {
			Connection conn = null;
			try {
				conn = DAO.getConnection();
				try {
					conn.setAutoCommit(false);
					FirmID2SysFirmID f2f = new FirmID2SysFirmID();
					f2f.firmID = platformID;
					f2f.sysFirmID = sysFirmID;
					f2f.systemID = systemID;
					for (CorrespondValue cv : vecCorr) {
						f2f.bankID = cv.bankID;
						if (DAO.addFirmID2SysFirmID(f2f, conn) != 1L) {
							Tool.log("添加平台号[" + platformID + "]交易系统[" + systemID + "]交易商[" + sysFirmID + "]银行[" + cv.bankID + "]的对应关系失败，回滚数据");
							conn.rollback();
							result.result = -1L;
							result.remark = "操作失败";
							ReturnValue localReturnValue1 = result;

							conn.setAutoCommit(true);
							return localReturnValue1;
						}
					}
					conn.commit();
					result.result = 0L;
					result.remark = "处理成功";

					noticePlatFirmID(systemID, platformID, sysFirmID);
				} catch (SQLException e1) {
					Tool.log("数据库异常：" + Tool.getExceptionTrace(e1));
					conn.rollback();
					result.result = -1L;
					result.remark = "数据库异常";
				} finally {
					conn.setAutoCommit(true);
				}
			} catch (Exception e) {
				Tool.log("系统异常：" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "系统异常";
			} finally {
				DAO.closeStatement(null, null, conn);
			}
		}
		return result;
	}

	public ReturnValue checkCapitalInfo(String systemID, java.util.Date date) {
		Tool.log("核对交易系统[" + systemID + "]和平台[" + Tool.fmtDate(date) + "]的转账流水");
		ReturnValue result = new ReturnValue();

		result = readCapitalInfos(systemID, date);
		if (result.result != 0L) {
			Tool.log("保存交易系统成功流水信息失败");
			return result;
		}
		Object[] obj = new Object[3];

		Vector<CapitalValueMoney> vecError = null;
		try {
			vecError = DAO.getMoneyErrorCapital(systemID, date);
		} catch (Exception e) {
			Tool.log("检查交易系统[" + systemID + "]和平台账面不平流水异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		obj[0] = vecError;

		Vector<CapitalValue> vecSysNo = null;
		try {
			vecSysNo = DAO.getSysNoCapital(systemID, date);
		} catch (Exception e) {
			Tool.log("检查交易系统[" + systemID + "]没有平台有流水异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		obj[1] = vecSysNo;

		Vector<CapitalValue> vecPlatNo = null;
		try {
			vecPlatNo = DAO.getPlatNoCapital(systemID, date);
		} catch (Exception e) {
			Tool.log("检查交易系统[" + systemID + "]有平台没有流水异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		obj[2] = vecPlatNo;

		result.result = 0L;
		result.remark = "核对成功";
		result.msg = obj;
		return result;
	}

	public ReturnValue readCapitalInfos(String systemID, java.util.Date date) {
		Tool.log("获取交易系统[" + systemID + "]在[" + Tool.fmtDate(date) + "]的流水信息");
		ReturnValue result = new ReturnValue();
		TradeProcessorRMI tradeProc = getTradeCaptialProcessor(systemID);
		if (tradeProc == null) {
			Tool.log("获取交易系统[" + systemID + "]的处理器RMI链接失败");
			result.result = -1L;
			result.remark = ("连接交易系统[" + systemID + "]的处理器失败");
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getCapitals");
		req.setParams(new Object[] { date });
		try {
			result = tradeProc.doWork(req);
		} catch (RemoteException e1) {
			Tool.log("调用交易系统[" + systemID + "]的处理器方法异常：" + Tool.getExceptionTrace(e1));
			result.result = -1L;
			result.remark = ("调用系统[" + systemID + "]的处理器异常");
			return result;
		}
		if (result.result != 0L) {
			Tool.log("交易系统[" + systemID + "]处理核心查询失败");
			return result;
		}
		Vector<CapitalValue> vecCap = (Vector) result.msg[0];
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				ReturnValue localReturnValue1;
				if (DAO.delCapitalByDateSystem(systemID, date, conn) < 0) {
					Tool.log("删除数据失败，数据回滚");
					result.result = -1L;
					result.remark = "删除旧数据失败";
					conn.rollback();
					localReturnValue1 = result;

					conn.setAutoCommit(true);
					return localReturnValue1;
				}
				if ((vecCap == null) || (vecCap.size() == 0)) {
					Tool.log("交易系统没有成功的流水，不执行插入新数据，直接提交数据");
					conn.commit();
					result.result = 0L;
					result.remark = ("交易系统[" + systemID + "]在[" + Tool.fmtDate(date) + "]没有成功的数据");
					localReturnValue1 = result;

					conn.setAutoCommit(true);
					return localReturnValue1;
				}
				result.result = DAO.addCapitalByDateSystem(systemID, vecCap, conn);
				if (result.result < 0L) {
					Tool.log("添加新数据失败，数据回滚");
					result.result = -1L;
					result.remark = "保存新读取数据失败";
					conn.rollback();
					localReturnValue1 = result;

					conn.setAutoCommit(true);
					return localReturnValue1;
				}
				if (result.result != vecCap.size()) {
					Tool.log("保存了[" + result.result + "]条成功的流水和查询到的[" + vecCap.size() + "]条成功流水数量不匹配，数据回滚");
					result.result = -1L;
					result.remark = "新读取成功流水，部分保存成功，数据回滚";
					conn.rollback();
					localReturnValue1 = result;

					conn.setAutoCommit(true);
					return localReturnValue1;
				}
				result.result = 0L;
				result.remark = "保存数据成功";
			} catch (SQLException e1) {
				Tool.log("数据库异常:" + Tool.getExceptionTrace(e1));
				result.result = -1L;
				result.remark = "处理异常";
				conn.rollback();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("系统异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "平台数据库异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue checkCapitalInfoPT(java.util.Date date) {
		Tool.log("核对交易系统和平台[" + Tool.fmtDate(date) + "]的转账流水");
		ReturnValue result = new ReturnValue();

		Object[] obj = new Object[3];

		Vector<CapitalValueMoney> vecError = null;
		try {
			vecError = DAO.getMoneyErrorCapital2(date);
		} catch (Exception e) {
			Tool.log("检查交易系统和平台账面不平流水异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		obj[0] = vecError;

		Vector<CapitalValue> vecSysNo = null;
		try {
			vecSysNo = DAO.getToSysNoCapital(date);
		} catch (Exception e) {
			Tool.log("检查平台到交易系统没有平台到银行有流水异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		obj[1] = vecSysNo;

		Vector<CapitalValue> vecPlatNo = null;
		try {
			vecPlatNo = DAO.getToBankNoCapital(date);
		} catch (Exception e) {
			Tool.log("检查平台到交易系统有平台到银行没有流水异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		obj[2] = vecPlatNo;

		result.result = 0L;
		result.remark = "核对成功";
		result.msg = obj;
		return result;
	}

	public ReturnValue getCheckMsg(String bankid, String fid) {
		ReturnValue result = new ReturnValue();
		Map<String, Object> params = new HashMap();
		params.put(" and bankid=? ", bankid);
		params.put(" and fid=? ", fid);
		Vector<CheckMessage> vec = null;
		try {
			vec = DAO.getCheckMsg(params);
			result.result = 0L;
			result.remark = "获取成功";
			result.msg = new Object[] { vec };
		} catch (SQLException e) {
			Tool.log("获取特殊值异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "获取失败";
		}
		return result;
	}

	public ReturnValue getActionID() {
		ReturnValue result = new ReturnValue();
		long ActionID = getMktActionID();
		result.result = 0L;
		result.remark = "获取成功";
		result.actionId = ActionID;
		return result;
	}

	public ReturnValue checkAccount(CorrespondValue cv) {
		Tool.log("银行[" + cv.bankID + "]账户[" + cv.account + "|" + cv.accountName + "]证件[" + cv.card + "|" + cv.cardType + "]的信息验证");
		ReturnValue result = new ReturnValue();

		BankAdapterRMI adapter = getAdapter(cv.bankID);
		if (adapter == null) {
			Tool.log("获取银行[" + cv.bankID + "]适配器失败");
			result.result = -1L;
			result.remark = "获取适配器失败";
			return result;
		}
		try {
			result = adapter.checkAccount(cv);
		} catch (RemoteException e) {
			Tool.log("调用适配器方法异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "调用适配器失败";
		}
		return result;
	}

	public ReturnValue autoSettlement(java.util.Date date) {
		Tool.log("触发系统自动结算，结算归属日期[" + date.toLocaleString() + "]");
		ReturnValue result = new ReturnValue();

		Vector<SystemMessage> vecSystem = null;
		try {
			vecSystem = DAO.getSystemMessages("");
		} catch (SQLException e) {
			Tool.log("获取交易系统列表信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecSystem == null) || (vecSystem.size() <= 0)) {
			Tool.log("查询交易系统列表信息失败");
			result.result = -1L;
			result.remark = "位查询到交易系统列表信息";
			return result;
		}
		for (int i = 0; i < vecSystem.size(); i++) {
			String systemID = ((SystemMessage) vecSystem.get(i)).systemID.trim();
			String filter = " and t.systemid ='" + systemID + "' and doflag=1 order by b_date desc";
			Vector<QueryTradeData> qt = null;
			try {
				qt = DAO.marketTradeQuery(Tool.fmtDate(date), filter);
			} catch (SQLException e) {
				Tool.log("获取最大清算日期时异常");
				Tool.log(Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "获取最大清算日期时异常";
			}
			java.util.Date maxQS = new java.util.Date();
			if ((qt != null) && (qt.size() > 0)) {
				maxQS.setTime(((QueryTradeData) qt.get(0)).tradeDate.getTime());
				maxQS.setDate(maxQS.getDate() + 1);
			}
			while (maxQS.before(date)) {
				result = getRZDataBySystemID(maxQS, systemID);
				Tool.log(result.toString());
				maxQS.setDate(maxQS.getDate() + 1);
			}
		}
		CapitalProcessor cp = new CapitalProcessor();
		result = cp.allotbankfunds(date);

		Tool.log("平台自动结算完成：\n" + result.toString());
		return result;
	}

	public ReturnValue inOutMoneyByHand(InOutMoney inOutMoney, boolean platform, boolean tradeSys) {
		Tool.log("平台号[" + inOutMoney.firmID + "]银行[" + inOutMoney.bankID + "]手工" + (String.valueOf(0).equals(inOutMoney.inOutMoneyFlag) ? "入金" : "出金")
				+ "[" + inOutMoney.money + "]元");
		ReturnValue result = new ReturnValue();
		if ((!platform) && (!tradeSys)) {
			Tool.log("没有选中系统");
			result.result = -1L;
			result.remark = "没有要操作的系统";
		}
		Vector<FirmBankFunds> vec = null;
		try {
			vec = DAO.getFirmBankFunds("and  f.firmid='" + inOutMoney.firmID + "' and f.bankcode='" + inOutMoney.bankID + "'");
		} catch (SQLException e) {
			Tool.log("查询异常：" + Tool.getExceptionTrace(e));
		}
		if ((vec == null) || (vec.size() <= 0)) {
			Tool.log("平台号和银行的签约关系不存在F_FIRMBANKFUNDS");
			result.result = -1L;
			result.remark = "没有找到签约记录";
			return result;
		}
		long capID = 0L;
		if ((inOutMoney.funID != null) && (!"".equals(inOutMoney.funID))) {
			Vector<CapitalValue> vecC = null;
			try {
				vecC = DAO.getCapitalInfoList(" where bankid='" + inOutMoney.bankID + "' and funid='" + inOutMoney.funID + "'");
			} catch (Exception e) {
				Tool.log("系统异常：" + Tool.getExceptionTrace(e));
			}
			if ((vecC != null) && (vecC.size() > 0)) {
				Tool.log("银行流水号[" + inOutMoney.funID + "]已经在银行[" + inOutMoney.funID + "]的流水中存在");
				result.result = -1L;
				result.remark = "银行流水号已经存在";
				return result;
			}
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			if (String.valueOf(1).equals(inOutMoney.inOutMoneyFlag)) {
				double realFunds = getCanOutBalance(inOutMoney.firmID, inOutMoney.bankID, conn);
				if (realFunds < inOutMoney.money) {
					Tool.log("可用余额[" + realFunds + "]小于出金金额[" + inOutMoney.money + "]");
					result.result = -1L;
					result.remark = "余额不足";
					DAO.closeStatement(null, null, conn);
					ReturnValue localReturnValue1 = result;
					return localReturnValue1;
				}
			}
			try {
				conn.setAutoCommit(false);

				inOutMoney.actionID = getMktActionID();
				if (String.valueOf(1).equals(inOutMoney.inOutMoneyFlag)) {
					inOutMoney.remark = "手工出金流水";
				} else {
					inOutMoney.remark = "手工入金流水";
				}
				if (platform) {
					CapitalValue cVal = new CapitalValue();
					cVal.actionID = inOutMoney.actionID;
					cVal.bankID = inOutMoney.bankID;
					cVal.creditID = inOutMoney.firmID;
					cVal.debitID = ((String) dataProcess.getBANKSUB().get(inOutMoney.bankID));
					cVal.firmID = inOutMoney.firmID;
					cVal.systemID = inOutMoney.systemID;
					cVal.sysFirmID = inOutMoney.sysFirmID;
					cVal.money = inOutMoney.money;
					cVal.note = inOutMoney.remark;
					if (String.valueOf(1).equals(inOutMoney.inOutMoneyFlag)) {
						cVal.oprcode = dataProcess.getOUTSUMMARY() + "";
						cVal.type = 1;
					} else {
						cVal.oprcode = dataProcess.getINSUMMARY() + "";
						cVal.type = 0;
					}
					cVal.status = 0;
					cVal.funID = inOutMoney.funID;
					cVal.tradeSource = "3";
					cVal.bankTime = new Timestamp(System.currentTimeMillis());
					if ((cVal.systemID == null) || ("".equals(cVal.systemID)) || ("null".equals(cVal.systemID))) {
						cVal.systemID = "--";
					}
					if ((cVal.sysFirmID == null) || ("".equals(cVal.sysFirmID)) || ("null".equals(cVal.sysFirmID))) {
						cVal.sysFirmID = "--";
					}
					DAO.addCapitalInfo(cVal, conn);
					if (String.valueOf(1).equals(inOutMoney.inOutMoneyFlag)) {
						dataProcess.updateFundsFull(inOutMoney.firmID, dataProcess.getOUTSUMMARY() + "",
								(String) dataProcess.getBANKSUB().get(inOutMoney.bankID), inOutMoney.money, inOutMoney.actionID, conn);
					} else {
						dataProcess.updateFundsFull(inOutMoney.firmID, dataProcess.getINSUMMARY() + "",
								(String) dataProcess.getBANKSUB().get(inOutMoney.bankID), inOutMoney.money, inOutMoney.actionID, conn);
					}
				}
				if (tradeSys) {
					CapitalValue cVal = new CapitalValue();
					cVal.actionID = inOutMoney.actionID;
					cVal.bankID = inOutMoney.bankID;
					cVal.creditID = inOutMoney.firmID;
					cVal.debitID = ((String) dataProcess.getBANKSUB().get(inOutMoney.bankID));
					cVal.firmID = inOutMoney.firmID;
					cVal.systemID = inOutMoney.systemID;
					cVal.sysFirmID = inOutMoney.sysFirmID;
					cVal.money = inOutMoney.money;
					cVal.note = inOutMoney.remark;
					cVal.bankTime = new Timestamp(System.currentTimeMillis());
					if (String.valueOf(1).equals(inOutMoney.inOutMoneyFlag)) {
						cVal.oprcode = dataProcess.getOUTSUMMARY() + "";
						cVal.type = 1;
					} else {
						cVal.oprcode = dataProcess.getINSUMMARY() + "";
						cVal.type = 0;
					}
					cVal.status = 0;
					cVal.tradeSource = "3";
					if ((cVal.systemID == null) || ("".equals(cVal.systemID)) || ("null".equals(cVal.systemID))) {
						cVal.systemID = "--";
					}
					if ((cVal.sysFirmID == null) || ("".equals(cVal.sysFirmID)) || ("null".equals(cVal.sysFirmID))) {
						cVal.sysFirmID = "--";
					}
					capID = DAO.addCapitalInfoPT(cVal, conn);

					TradeProcessorRMI trade = getTradeCaptialProcessor(inOutMoney.systemID);
					if (trade == null) {
						Tool.log("获取交易系统[" + inOutMoney.systemID + "]处理器失败");
						result.result = -1L;
						result.remark = ("获取交易系统[" + inOutMoney.systemID + "]处理器失败");
					} else {
						RequestMsg req = new RequestMsg();
						req.setBankID("");
						req.setMethodName("inOutMoneyByHand");
						req.setParams(new Object[] { inOutMoney });
						result = trade.doWork(req);
					}
				} else {
					result.result = 0L;
				}
				if (result.result == 0L) {
					DAO.modCapitalInfoStatusPT(capID, result.funID, 0, new Timestamp(System.currentTimeMillis()), conn);
					conn.commit();
					result.remark = "操作成功";
				} else {
					conn.rollback();
				}
			} catch (SQLException e1) {
				Tool.log("数据回滚因为发生异常：" + Tool.getExceptionTrace(e1));
				conn.rollback();
				result.result = -1L;
				result.remark = "系统异常";
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("系统异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue addFirmMapping(FirmID2SysFirmID f2f) {
		Tool.log("平台号[" + f2f.firmID + "]增加和交易系统[" + f2f.systemID + "]交易商[" + f2f.sysFirmID + "]的对应关系");
		ReturnValue result = new ReturnValue();

		Vector<FirmBankFunds> vecFBF = null;
		try {
			vecFBF = DAO.getFirmBankFunds(" and f.firmid='" + f2f.firmID + "'");
		} catch (SQLException e) {
			Tool.log("查询平台账号信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecFBF == null) || (vecFBF.size() <= 0)) {
			Tool.log("平台账号[" + f2f.firmID + "]不存在或还没有签约过银行");
			result.result = -1L;
			result.remark = "平台账号不存在";
			return result;
		}
		Vector<CorrespondValue> vecCV = null;
		try {
			vecCV = DAO.getCorrespondList(" where firmid='" + f2f.firmID + "'");
		} catch (Exception e) {
			Tool.log("查询签约记录信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCV == null) || (vecCV.size() <= 0)) {
			Tool.log("平台账号[" + f2f.firmID + "]还未签约银行");
			result.result = -1L;
			result.remark = "平台账号还没有签约的银行";
			return result;
		}
		Vector<FirmID2SysFirmID> vecF2F = null;
		try {
			vecF2F = DAO.getFirmID2SysFirmID(" where firmid='" + f2f.firmID + "'");
		} catch (SQLException e) {
			Tool.log("查询平台号和交易商对应关系异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecF2F == null) || (vecF2F.size() <= 0)) {
			Tool.log("平台账号[" + f2f.firmID + "]还没有对应的交易商信息");
			result.result = -1L;
			result.remark = "平台账号还没有对应的交易商";
			return result;
		}
		Vector<FirmID2SysFirmID> vecF2F1 = null;
		try {
			vecF2F1 = DAO.getFirmID2SysFirmID(
					" where firmid='" + f2f.firmID + "' and sysfirmid='" + f2f.sysFirmID + "' and systemid='" + f2f.systemID + "'");
		} catch (SQLException e) {
			Tool.log("查询平台号和交易商对应关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "数据库异常";
			return result;
		}
		if ((vecF2F1 != null) && (vecF2F1.size() > 0)) {
			Tool.log("平台账号[" + f2f.firmID + "]和交易系统[" + f2f.systemID + "]的交易商[" + f2f.sysFirmID + "]已经存在对应关系");
			result.result = -1L;
			result.remark = "对应关系已存在";
			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				ReturnValue localReturnValue1;
				for (FirmID2SysFirmID ff : vecF2F) {
					FirmID2SysFirmID FFNew = new FirmID2SysFirmID();
					FFNew.firmID = f2f.firmID;
					FFNew.sysFirmID = f2f.sysFirmID;
					FFNew.systemID = f2f.systemID;
					FFNew.bankID = ff.bankID;
					FFNew.defaultSystem = ff.defaultSystem;
					if (DAO.addFirmID2SysFirmID(FFNew, conn) != 1L) {
						Tool.log("添加平台账号[" + f2f.firmID + "]和交易系统[" + f2f.systemID + "]的交易商[" + f2f.sysFirmID + "]有关银行[" + ff.bankID + "]的记录失败，数据回滚");
						conn.rollback();
						result.result = -1L;
						result.remark = "添加新纪录失败";
						localReturnValue1 = result;

						conn.setAutoCommit(true);
						return localReturnValue1;
					}
				}
				TradeProcessorRMI tradeProc = getTradeCaptialProcessor(f2f.systemID);
				if (tradeProc == null) {
					Tool.log("获取交易系统[" + f2f.systemID + "]处理器RMI连接失败，数据回滚");
					conn.rollback();
					result.result = -1L;
					result.remark = "获取交易系统处理器失败";
					localReturnValue1 = result;

					conn.setAutoCommit(true);
					return localReturnValue1;
				}
				RequestMsg req = new RequestMsg();
				req.setBankID(f2f.bankID);
				req.setMethodName("noticePlatFirmID");
				req.setParams(new Object[] { f2f.firmID, f2f.sysFirmID });
				result = tradeProc.doWork(req);
				if ((result == null) || (result.result != 0L)) {
					Tool.log("交易系统[" + f2f.systemID + "]保存对应关系失败，数据回滚");
					conn.rollback();
					result.result = -1L;
					result.remark = "交易系统保存信息失败";
					localReturnValue1 = result;

					conn.setAutoCommit(true);
					return localReturnValue1;
				}
				conn.commit();
				Tool.log("交易系统[" + f2f.systemID + "]保存对应关系成功");
				result.remark = "操作成功";
			} catch (SQLException e1) {
				conn.rollback();
				Tool.log("数据库异常：" + Tool.getExceptionTrace(e1));
				result.result = -1L;
				result.remark = "数据库异常";
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("系统异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
}
