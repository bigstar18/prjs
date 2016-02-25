package gnnt.trade.bank;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.dao.ABCBankDAO;
import gnnt.trade.bank.dao.BankDAOFactory;
import gnnt.trade.bank.util.ErrorCode;
import gnnt.trade.bank.util.FtpUtil;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.AbcInfoValue;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmAuditValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.InMoneyVO;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.LogEndmsg;
import gnnt.trade.bank.vo.MoneyInfoValue;
import gnnt.trade.bank.vo.OutMoneyMarket;
import gnnt.trade.bank.vo.ReturnValue;
import gnnt.trade.bank.vo.TransferInfoValue;

public class ABCCapitalProcessor extends CapitalProcessor {
	private static ABCBankDAO DAO;

	public ABCCapitalProcessor() {
		try {
			DAO = BankDAOFactory.getABCDAO();
		} catch (Exception e) {
			log("初始化农行DAO对象处理器失败：" + Tool.getExceptionTrace(e));
		}
	}

	private long handleOUtMoney(Connection conn, String bankID, String trader, double money, String firmID, String contact, String account,
			String funID, int type, long actionid) throws SQLException {
		log("检查交易账号是否符合出金条件,符合条件进行出金处理,记录流水 handleOUtMoney bankID[" + bankID + "]trader[" + trader + "]money[" + money + "]firmID[" + firmID
				+ "]contact[" + contact + "]account[" + account + "]funID[" + funID + "]type[" + type + "]");
		long result = -20042L;
		try {
			conn.setAutoCommit(false);
			long l1;
			if ((funID != null) && (funID.trim().length() > 0)) {
				Vector<CapitalValue> cv = DAO.getCapitalInfoList(
						" and bankID='" + bankID + "' and createtime>=trunc(sysdate) and createtime<trunc(sysdate)+1 and funID='" + funID + "'",
						conn);
				if ((cv != null) && (cv.size() > 0)) {
					result = -20042L;
					log("银行出金，银行流水号[" + funID + "]已经存在");
					l1 = result;
					return l1;
				}
			}
			result = checkArgs(conn, money, firmID, bankID).result;
			if (result < 0L) {
				l1 = result;
				return l1;
			}
			result = actionid;
			CapitalValue cVal = new CapitalValue();
			cVal.trader = trader;
			cVal.funID = String.valueOf(result);
			cVal.actionID = result;
			cVal.firmID = firmID;
			cVal.contact = contact;
			cVal.bankID = bankID;
			cVal.type = 1;
			cVal.money = money;
			cVal.status = 7;
			if (type == 0) {
				cVal.launcher = 0;
				cVal.note = "市场端出金";
			} else {
				cVal.launcher = 1;
				cVal.note = "银行端出金";
			}
			DAO.addCapitalInfo(cVal, conn);
			dataProcess.updateFrozenFunds(firmID, money, conn);
			bankFrozenFuns(firmID, contact, bankID, account, money, conn);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			result = -20038L;
			log("出金Exception,数据回滚" + Tool.getExceptionTrace(e));
		} finally {
			conn.setAutoCommit(true);
		}
		return result;
	}

	public ReturnValue outMoneyMarket(OutMoneyMarket omm, long actionID) {
		log("出金: outMoney omm:" + (omm == null ? "为空" : new StringBuilder("\n").append(omm.toString()).append("\n").toString()));
		ReturnValue result = new ReturnValue();
		Connection conn = null;

		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		try {
			CorrespondValue cv = getCorrespond(omm.bankID, omm.firmID, omm.contact, omm.account);
			ReturnValue localReturnValue1;
			if (cv == null) {
				result.result = -920013L;
				result.remark = "出金，未查询到交易账号绑定信息";
				log(result.remark);
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (cv.isOpen != 1) {
				result.result = -20008L;
				result.remark = "交易账号未签约。";
				log(result.remark);
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (cv.status != 0) {
				result.result = -20009L;
				result.remark = "交易账号不可用";
				log(result.remark);
				localReturnValue1 = result;
				return localReturnValue1;
			}
			omm.firmID = cv.firmID;
			omm.contact = cv.contact;
			omm.account = cv.account;
			result = ifbankTrade(omm.bankID, omm.firmID, omm.contact, 1, 0);
			if (result.result < 0L) {
				localReturnValue1 = result;
				return localReturnValue1;
			}
			conn = DAO.getConnection();
			result = checkArgs(conn, omm.money, omm.firmID, omm.bankID);
			if (result.result != 0L) {
				localReturnValue1 = result;
				return localReturnValue1;
			}
			long actionId = handleOUtMoney(conn, omm.bankID, "", omm.money, omm.firmID, omm.contact, omm.account, null, omm.type, actionID);
			result.result = actionId;
			if (result.result <= 0L) {
				result.remark = ((String) ErrorCode.error.get(Long.valueOf(result.result)));
			}
			if ("true".equalsIgnoreCase(Tool.getConfig("OutMoneyAudit"))) {
				log("出金审核功能开启状态");

				boolean notBeyond = true;
				BankValue bVal = DAO.getBank(omm.bankID, conn);
				FirmAuditValue fVal = DAO.getFirmAuditValue(omm.contact, conn);
				log("BankValue" + bVal.toString());
				log("交易商的审核额度：" + fVal.toString());
				String firmType = DAO.getFirmType(omm.firmID, conn);
				double auditBalance = bVal.cmaxOutMoney;
				double mauditBalance = bVal.mmaxOutMoney;
				double dayTransCount = getDayTransMoney(omm.bankID, omm.contact, 1, curTime, conn);
				if (fVal.maxAuditMoney > 0.0D) {
					auditBalance = fVal.maxAuditMoney;
					mauditBalance = fVal.maxAuditMoney;
				}
				log("客户的审核金额为:  " + auditBalance);
				log("会员的审核金额为:  " + mauditBalance);
				if ("C".equals(firmType)) {
					log("客户出金：" + omm.money + "最大出金" + auditBalance);
					if ("1".equals(bVal.cOutMoney)) {
						if ((omm.money > auditBalance) && (auditBalance >= 0.0D)) {
							notBeyond = false;
						}
					} else if ((dayTransCount > auditBalance) && (auditBalance >= 0.0D)) {
						notBeyond = false;
					}
				} else {
					log("会员出金：" + omm.money + "最大出金" + mauditBalance);
					if ("1".equals(bVal.mOutMoney)) {
						if ((omm.money > mauditBalance) && (mauditBalance >= 0.0D)) {
							notBeyond = false;
						}
					} else if ((dayTransCount > mauditBalance) && (mauditBalance >= 0.0D)) {
						notBeyond = false;
					}
				}
				if ((Tool.getConfig("AutoAudit") != null) && (Tool.getConfig("AutoAudit").equalsIgnoreCase("True"))) {
					result = outMoneyAutoAudit(result.result);
					result.actionId = actionId;
				} else if (notBeyond) {
					result = outMoneyAutoAudit(result.result);
					result.actionId = actionId;
				} else {
					CapitalValue vapv1 = DAO.getCapitalInfo(" and actionid=" + actionId);
					if (vapv1 == null) {
						log("将流水加入审核队列前未找到对应的流水");
						result.result = -20004L;
						result.remark = "将流水加入审核队列前未找到对应的流水";
					} else {
						DAO.modCapitalInfoStatus(vapv1.iD, null, 3, null, conn);
						DAO.modCapitalInfoNote(vapv1.iD, "等待市场审核", conn);
						result.result = -30006L;
						result.remark = "超出审核额度，需要市场审核";
					}
				}
			} else {
				log("出金审核功能不开启状态，直接进入自动审核");
				result = outMoneyAutoAudit(result.result);
			}
		} catch (SQLException e) {
			result.result = -20004L;
			result.remark = "数据库发生异常，请联系交易所";
			log("市场发起出金SQLException，错误码=" + result.result + "  " + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -20005L;
			result.remark = "转账系统异常";
			log("市场发起出金Exception，错误码=" + result.result + "  " + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue outMoneyMarket_bak(OutMoneyMarket omm, long actionID) {
		log("出金: outMoney omm:" + (omm == null ? "为空" : new StringBuilder("\n").append(omm.toString()).append("\n").toString()));
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			CorrespondValue cv = getCorrespond(omm.bankID, omm.firmID, omm.contact, omm.account);
			ReturnValue localReturnValue1;
			if (cv == null) {
				result.result = -920013L;
				result.remark = "出金，未查询到交易账号绑定信息";
				log(result.remark);
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (cv.isOpen != 1) {
				result.result = -20008L;
				result.remark = "交易账号未签约。";
				log(result.remark);
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (cv.status != 0) {
				result.result = -20009L;
				result.remark = "交易账号不可用";
				log(result.remark);
				localReturnValue1 = result;
				return localReturnValue1;
			}
			omm.firmID = cv.firmID;
			omm.contact = cv.contact;
			omm.account = cv.account;
			result = ifbankTrade(omm.bankID, omm.firmID, omm.contact, 1, 0);
			if (result.result < 0L) {
				localReturnValue1 = result;
				return localReturnValue1;
			}
			conn = DAO.getConnection();
			result = checkArgs(conn, omm.money, omm.firmID, omm.bankID);
			if (result.result != 0L) {
				localReturnValue1 = result;
				return localReturnValue1;
			}
			long actionId = handleOUtMoney(conn, omm.bankID, "", omm.money, omm.firmID, omm.contact, omm.account, null, omm.type, actionID);
			result.result = actionId;
			if (result.result <= 0L) {
				result.remark = ((String) ErrorCode.error.get(Long.valueOf(result.result)));
			}
			boolean notBeyond = true;
			BankValue bVal = DAO.getBank(omm.bankID, conn);
			double auditBalance = bVal.maxOutMoney;
			if ((omm.money > auditBalance) && (auditBalance >= 0.0D)) {
				notBeyond = false;
			}
			if ((Tool.getConfig("AutoAudit") != null) && (Tool.getConfig("AutoAudit").equalsIgnoreCase("True"))) {
				result = outMoneyAutoAudit(result.result);
				result.actionId = actionId;
			} else if (notBeyond) {
				result = outMoneyAutoAudit(result.result);
				result.actionId = actionId;
			} else {
				CapitalValue vapv1 = DAO.getCapitalInfo(" and actionid=" + actionId);
				if (vapv1 == null) {
					log("将流水加入审核队列前未找到对应的流水");
					result.result = -20004L;
					result.remark = "将流水加入审核队列前未找到对应的流水";
				} else {
					DAO.modCapitalInfoStatus(vapv1.iD, null, 3, null, conn);
					DAO.modCapitalInfoNote(vapv1.iD, "等待市场审核", conn);
					result.result = -30006L;
					result.remark = "超出审核额度，需要市场审核";
				}
			}
		} catch (SQLException e) {
			result.result = -20004L;
			result.remark = "数据库发生异常，请联系交易所";
			log("市场发起出金SQLException，错误码=" + result.result + "  " + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -20005L;
			result.remark = "转账系统异常";
			log("市场发起出金Exception，错误码=" + result.result + "  " + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue inMoneyMarket(InMoneyMarket imm, long actionid) {
		log("市场入金 inMoneyMarket imm:" + (imm == null ? "为 null" : new StringBuilder("\n").append(imm.toString()).append("\n").toString()));
		ReturnValue result = new ReturnValue();
		long capitalID = 0L;
		Connection conn = null;
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		try {
			ReturnValue localReturnValue1;
			if (imm.money <= 0.0D) {
				result.result = -920019L;
				result.remark = "入金金额必须为正数";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			conn = DAO.getConnection();
			CorrespondValue cv = getCorrespond(imm.bankID, imm.firmID, imm.contact, imm.account);
			if (cv == null) {
				result.result = -920013L;
				result.remark = "未查询到交易账号信息";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			imm.contact = cv.contact;
			imm.account = cv.account;
			imm.firmID = cv.firmID;
			result = ifbankTrade(imm.bankID, imm.firmID, imm.contact, 0, 0);
			if (result.result != 0L) {
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (cv.isOpen != 1) {
				result.result = -10019L;
				result.remark = "交易账号未签约";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			if (cv.status != 0) {
				result.result = -10020L;
				result.remark = "交易账号不可用";
				localReturnValue1 = result;
				return localReturnValue1;
			}
			log(imm.toString());
			result.actionId = actionid;
			result.result = result.actionId;
			try {
				CapitalValue cVal = new CapitalValue();
				cVal.trader = "";
				cVal.actionID = result.actionId;
				cVal.firmID = imm.firmID;
				cVal.contact = imm.contact;
				cVal.bankID = imm.bankID;
				cVal.type = 0;
				cVal.launcher = 0;
				cVal.money = imm.money;
				cVal.status = 7;
				cVal.note = "市场端入金";
				log(cVal.toString());
				capitalID = DAO.addCapitalInfo(cVal, conn);
			} catch (SQLException e) {
				e.printStackTrace();
				result.result = -10026L;
				result.remark = "添加流水信息时数据库异常";
				log("市场端发起入金写资金流水SQLException,数据回滚:" + e);
				localReturnValue1 = result;

				DAO.closeStatement(null, null, conn);
				return localReturnValue1;
			}
			if (result.result <= 0L) {
				localReturnValue1 = result;
				return localReturnValue1;
			}
			TransferInfoValue payInfo = getPayInfo(imm.bankID, imm.firmID, 0, conn);
			TransferInfoValue receiveInfo = getReceiveInfo(imm.bankID, imm.firmID, 0, conn);
			InMoneyVO inMoneyInfo = new InMoneyVO(imm.bankID, null, imm.money, imm.contact, payInfo, imm.accountPwd, receiveInfo, imm.remark,
					result.actionId);
			BankAdapterRMI bankadapter = getAdapter(imm.bankID);
			if (bankadapter == null) {
				result.result = -920000L;
				result.remark = "网络异常，处理器无法连接适配器";
				log(result.remark);
			} else {
				result = bankadapter.inMoneyQueryBank(inMoneyInfo);
			}
			log("市场端调用适配器入金，市场返回信息：" + result.toString());

		} catch (SQLException e) {
			result.result = -10014L;
			result.remark = "数据库连接异常";
			log("市场端发起入金SQLException:" + result + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			result.result = -10015L;
			result.remark = "系统异常";
			log("市场端发起入金Exception:" + result + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public int getBankCompareInfoAbc(String bankID, java.util.Date date) {
		log("获取银行对账信息 getBankCompareInfo bankID[" + bankID + "]date[" + date + "]");
		int result = 0;
		Vector<MoneyInfoValue> moneyInfoList = new Vector();
		try {
			if ("1".equals(Tool.getConfig("ifFtp"))) {
				String basePath = Tool.getConfig("basePath");
				FtpUtil ftp = new FtpUtil();

				String filename = "MerchantSettleFile-" + Tool.fmtDate(date) + ".xls";
				String host = Tool.getConfig("ftpIp");
				String ftpName = Tool.getConfig("ftpName");
				String ftpPassword = Tool.getConfig("ftpPassword");
				String ftpPath = Tool.getConfig("ftpPath");
				try {
					ftp.connectServer(host, ftpName, ftpPassword, ftpPath);
					ftp.download(filename, basePath + filename);
				} catch (IOException e) {
					result = -30013;
					log("下载日终对账文件失败=" + result);
					log(Tool.getExceptionTrace(e));
					return result;
				} catch (Exception e) {
					log(Tool.getExceptionTrace(e));
				}
			}
			String files = Tool.getConfig("basePath") + "MerchantSettleFile-" + Tool.fmtDate(date) + ".xls";

			HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(files));

			HSSFSheet sheet = wookbook.getSheetAt(0);

			int rows = sheet.getPhysicalNumberOfRows();
			log("Excel表行数：" + rows);
			if (rows > 2) {
				for (int i = 1; i < rows - 1; i++) {
					HSSFRow row = sheet.getRow(i);

					int cells = row.getPhysicalNumberOfCells();
					String value = "";
					for (int j = 0; j < cells; j++) {
						HSSFCell cell = row.getCell((short) j);
						if (cell != null) {
							switch (cell.getCellType()) {
							case 2:
								break;
							case 0:
								value = value + cell.getNumericCellValue() + ",";
								break;
							case 1:
								value = value + cell.getStringCellValue() + ",";
								break;
							default:
								value = value + "0";
							}
						}
					}
					String[] val = value.split(",");

					Connection conn = null;
					MoneyInfoValue moneyInfoValue = new MoneyInfoValue();
					try {
						conn = DAO.getConnection();
						Vector<CapitalValue> list = DAO.getCapitalInfoList(" and bankID='" + bankID.trim() + "' and funID='" + val[1] + "' ", conn);
						for (int a = 0; a < list.size(); a++) {
							CapitalValue cv = (CapitalValue) list.get(a);
							moneyInfoValue.firmID = cv.contact;
							Vector<CorrespondValue> vcv = DAO.getCorrespondList(" and firmID='" + cv.firmID + "' and bankID='" + bankID.trim() + "'",
									conn);
							if ((vcv != null) || (vcv.size() >= 0)) {
								for (int b = 0; b < vcv.size(); b++) {
									CorrespondValue cp = (CorrespondValue) vcv.get(b);
									moneyInfoValue.account = cp.account;
								}
							}
						}
					} catch (SQLException e) {
						log(Tool.getExceptionTrace(e));
					} catch (Exception e) {
						log(Tool.getExceptionTrace(e));
					} finally {
						DAO.closeStatement(null, null, conn);
					}
					moneyInfoValue.status = 0;
					moneyInfoValue.bankID = bankID;
					moneyInfoValue.id = val[1];
					moneyInfoValue.m_Id = Tool.toLong(val[1]);
					if (val[2].equals("客户转市场")) {
						moneyInfoValue.money = Tool.strToDouble(val[3]);

						moneyInfoValue.type = 0;
					} else if (val[2].equals("市场转客户")) {
						moneyInfoValue.money = Tool.strToDouble(val[4]);

						moneyInfoValue.type = 1;
					}
					moneyInfoValue.compareDate = new java.sql.Date(Tool.strToDate1(val[0]).getTime());
					if (moneyInfoValue.compareDate == null) {
						moneyInfoValue.compareDate = new java.sql.Date(date.getTime());
					}
					if ((val[2].equals("客户转市场")) || (val[2].equals("市场转客户"))) {
						moneyInfoList.add(moneyInfoValue);
					}
					moneyInfoValue.toString();
				}
			} else {
				result = -30016;
				log("对账文件为空,错误码=" + result);
			}
			if (moneyInfoList == null) {
				result = -30013;
				log("获取银行对账信息,错误码=" + result);
				LogEndmsg endmsg = new LogEndmsg();
				InterfaceLog log = new InterfaceLog();
				log.account = "";
				log.bankID = bankID;
				log.contact = "";
				log.type = 20;
				endmsg.note = "取银行出入金流水明细失败:获取流水文件内容失败";
				log.result = 1;
				log.endMsg = endmsg.toString();
				interfaceLog(log);
				return result;
			}
			result = insertBankCompareInfo(moneyInfoList);
			if (result < 0) {
				result = -30013;
				log("插入银行转账信息失败=" + result);
				LogEndmsg endmsg = new LogEndmsg();
				InterfaceLog log = new InterfaceLog();
				log.account = "";
				log.bankID = bankID;
				log.contact = "";
				endmsg.code = "";
				log.type = 20;
				endmsg.note = "取银行出入金流水明细失败:添加到数据库失败";
				log.result = 1;
				log.endMsg = endmsg.toString();
				interfaceLog(log);
				return result;
			}
		} catch (IOException e) {
			result = -30014;
			log("读取日终对账文件失败=" + result);
			log(Tool.getExceptionTrace(e));
			return result;
		}
		return result;
	}

	public AbcInfoValue getAbcInfo(String contact, long orderNo, int type) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("农行交易相关信息");
		System.out.println("contact[" + contact + "]");
		System.out.println("}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}");
		AbcInfoValue result = new AbcInfoValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();

			result = DAO.getAbcInfo(contact, orderNo, type, conn);
		} catch (Exception e) {
			log("农行交易相关信息Exception:" + e);
			e.printStackTrace();
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue insertAbcInfo(AbcInfoValue value) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println("写入农行交易相关信息");

		System.out.println("}}}}}}}}}}}}}}}}}}}}}时间：" + Tool.fmtTime(new java.util.Date()) + "}}}}}}}}}}}}}}}}}}}}}}}}}");
		ReturnValue result = new ReturnValue();
		Connection conn = null;
		try {
			if (value == null) {
				result.result = -1L;
				result.remark = "传入信息为空";
			}
			if ((value.actionID < 0L) || (value.signInfo == null) || (value.signInfo.length() < 1600)) {
				result.result = -1L;
				result.remark = "签名为空";
			}
			try {
				conn = DAO.getConnection();
				conn.setAutoCommit(false);
				DAO.addAbcInfo(value, conn);
				result.result = 0L;
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				result.result = -1L;
				result.remark = "写入农行交易相关信息异常";
				log("写入农行交易相关信息异常，数据回滚");
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.result = -1L;
			result.remark = "写入农行交易相关信息异常";
			log("写入农行交易相关信息异常，错误码=" + result);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}
}
