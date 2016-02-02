package gnnt.trade.bank;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;

import gnnt.bank.adapter.rmi.BankAdapterRMI;
import gnnt.trade.bank.util.ErrorCode;
import gnnt.trade.bank.util.ProcConstants;
import gnnt.trade.bank.util.StartupRmiserver;
import gnnt.trade.bank.util.Tool;
import gnnt.trade.bank.vo.BankValue;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.InterfaceLog;
import gnnt.trade.bank.vo.LogEndmsg;
import gnnt.trade.bank.vo.ReturnValue;

/**
 * <p>
 * Title: 银行接口处理器
 * </p>
 * <p>
 * Description:处理基本不变的核心业务
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: gnnt
 * </p>
 */
public class CapitalProcessorHX extends CapitalProcessor {
	/******************** 常量定义 ***********************/
	/** 与远期系统有关的数据处理对象 */
	// protected static DataProcess dataProcess;
	// /**是否已加载配置信息*/
	// private static boolean ISLOADED = false;
	// /**远程访问适配器对象url队列, key:银行代码,value:remoteUrl*/
	// private static Hashtable<String,String> ADAPTERLIST;
	// /**数据库访问对象*/
	// private static BankDAO DAO;
	// /**
	// * 构造函数
	// */
	// public CapitalProcessorHX() {
	// try {
	// DAO = BankDAOFactory.getDAO();//取得数据库访问对象
	// if(!ISLOADED) {
	// ErrorCode errorCode=new ErrorCode();
	// errorCode.load();//加载错误码对应的信息
	// //new FileThread().start();
	// dataProcess=new DataProcess();
	// if(loadAdapter()==0) {//加载适配器对象
	// ISLOADED = true;
	// } else {
	// log("初始化处理器失败：加载适配器对象错误");
	// }
	// }
	// } catch(Exception e) {
	// log("初始化处理器失败："+Tool.getExceptionTrace(e));
	// }
	// }
	/**
	 * 加载适配器
	 * 
	 * @return int 0:加载成功；-1：加载失败
	 */
	private int loadAdapter() {
		int result = 0;
		try {
			Vector<BankValue> bankList = DAO.getBankList("");
			log("开始加载适配器远程访问对象url");
			for (int i = 0; i < bankList.size(); i++) {
				BankValue bVal = (BankValue) bankList.get(i);
				String abClassName = bVal.adapterClassname;
				addAdapter(bVal.bankID, abClassName);
				log(bVal.bankName + "[" + bVal.bankID + "][" + abClassName + "]适配器远程访问对象url加载成功");
			}
			log("结束加载适配器远程访问对象url");
		} catch (Exception e) {
			result = -1;
			log("加载适配器远程访问对象url异常：" + Tool.getExceptionTrace(e));
		} finally {
		}
		return result;
	}

	/**
	 * 将适配器放入内存队列
	 * 
	 * @param bankID
	 *            银行代码
	 * @param adapter
	 *            适配器对象
	 */
	private void addAdapter(String bankID, String adapterClassname) {
		if (ADAPTERLIST == null)
			ADAPTERLIST = new Hashtable<String, String>();
		ADAPTERLIST.put(bankID, adapterClassname);
	}

	/**
	 * 取得适配器对象
	 * 
	 * @param bankID
	 *            银行代码
	 * @param adapter
	 *            适配器对象
	 * @return BankAdapter
	 */
	public BankAdapterRMI getAdapter(String bankID) {
		log("取得适配器对象 bankID[" + bankID + "]");
		BankAdapterRMI bankadapter = null;
		try {
			String remoteUrl = ADAPTERLIST.get(bankID);
			log("bankID[" + bankID + "]地址[" + remoteUrl + "]");
			bankadapter = (BankAdapterRMI) Naming.lookup(remoteUrl);
		} catch (MalformedURLException e) {
			this.log("MalformedURLException:" + Tool.getExceptionTrace(e));
		} catch (RemoteException e) {
			this.log("RemoteException:" + Tool.getExceptionTrace(e));
		} catch (NotBoundException e) {
			this.log("NotBoundException:" + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			this.log("Exception" + Tool.getExceptionTrace(e));
		}
		this.log("返回银行适配器对象信息：" + bankadapter);
		return bankadapter;
	}

	/**
	 * 银行发起绑定，市场将银行子账号和子账号名称等添加进去
	 * 
	 * @param cv
	 * @return
	 */
	public ReturnValue relevanceAccount(CorrespondValue cv) {
		System.out.println("{{{{{{{{{{{{{{{{{{{{{银行发起绑定，市场将银行子账号和子账号名称等添加进去{{{{{{{{{{{{{{{{{{{{{{{{");

		System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
		ReturnValue rv = new ReturnValue();
		if (cv == null) {
			rv.result = ErrorCode.rgstAccount_InfoHalfbaked;
			rv.remark = "银行发起绑定，传入的信息包为空";
			LogEndmsg endmsg = new LogEndmsg();
			InterfaceLog log = new InterfaceLog();
			log.account = "";
			log.launcher = 1;
			log.bankID = cv.bankID;
			log.contact = cv.contact;
			endmsg.code = "";
			log.type = 14;// 发送出金审核结果
			endmsg.note = rv.remark;
			if (!"0000".equalsIgnoreCase(endmsg.code)) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			this.interfaceLog(log);
			return rv;
		} else {
			if (cv.account1 == null) {
				rv.result = ErrorCode.rgstAccount_InfoHalfbaked;
				rv.remark = ErrorCode.error.get(ErrorCode.rgstAccount_InfoHalfbaked);
				LogEndmsg endmsg = new LogEndmsg();
				InterfaceLog log = new InterfaceLog();
				log.account = "";
				log.launcher = 1;
				log.bankID = cv.bankID;
				log.contact = cv.contact;
				endmsg.code = "";
				log.type = 14;// 发送出金审核结果
				endmsg.note = rv.remark;
				if (!"0000".equalsIgnoreCase(endmsg.code)) {
					log.result = 1;
				}
				log.endMsg = endmsg.toString();
				this.interfaceLog(log);
				return rv;
			} else if (cv.firmID == null) {
				rv.result = ErrorCode.rgstAccount_NOFirm;
				rv.remark = ErrorCode.error.get(ErrorCode.rgstAccount_NOFirm);
				LogEndmsg endmsg = new LogEndmsg();
				InterfaceLog log = new InterfaceLog();
				log.account = "";
				log.launcher = 1;
				log.bankID = cv.bankID;
				log.contact = cv.contact;
				endmsg.code = "";
				log.type = 14;// 发送出金审核结果
				endmsg.note = rv.remark;
				if (!"0000".equalsIgnoreCase(endmsg.code)) {
					log.result = 1;
				}
				log.endMsg = endmsg.toString();
				this.interfaceLog(log);
				return rv;
			}
		}
		try {
			// Connection conn = DAO.getConnection();
			// conn.setAutoCommit(false);
			// dataProcess.changeFirmIsOpen(cv.firmID,1,cv.bankID,conn);
			// rv.result = DAO.openCorrespond(cv);
			// conn.commit();
			synchronized (cv.firmID) {
				Connection conn = null;
				try {
					if (cv.contact == null || cv.contact.trim().length() <= 0) {
						cv.contact = cv.firmID;
					}
					List<CorrespondValue> cvresult = DAO.getCorrespondList(" and bankID='" + cv.bankID + "' and CONTACT='" + cv.contact + "'");
					if (cvresult == null || cvresult.size() <= 0) {
						cvresult = DAO.getCorrespondList(" and trim(contact)='" + cv.contact.trim() + "' and trim(bankID) is null ");
						if (cvresult != null && cvresult.size() > 0) {
							CorrespondValue corr = cvresult.get(0);
							corr.account = cv.account;
							corr.isOpen = ProcConstants.firmTypeOpen;
							corr.status = ProcConstants.firmStatusTrue;
							corr.accountName = cv.accountName;
							corr.card = cv.card;
							corr.cardType = cv.cardType;
							corr.account1 = cv.account1;
							corr.bankID = cv.bankID;
							rv.actionId = getMktActionID();
							conn = DAO.getConnection();
							try {
								conn.setAutoCommit(false);
								rv = dataProcess.changeFirmIsOpen(corr.firmID, 1, corr.bankID, conn);
								if (rv.result < 0) {
									conn.rollback();
									return rv;
								}
								DAO.openCorrespond(corr, conn);
								conn.commit();
							} catch (SQLException er) {
								conn.rollback();
								throw er;
							} finally {
								conn.setAutoCommit(true);
							}
						} else {
							Vector<FirmValue> vfv = DAO.getFirmList(" and CONTACT='" + cv.contact + "'");
							if (vfv == null || vfv.size() != 1) {
								rv.result = ErrorCode.rgstAccount_NOFirm;
								rv.remark = "通过签约号查询客户信息异常";
								return rv;
							}
							FirmValue fv = vfv.get(0);
							if (cv.card == null || !cv.card.equals(fv.card)) {
								rv.result = ErrorCode.outMoney_CardNo;
								rv.remark = "客户证件号码错误";
								return rv;
							}
							cv.firmID = fv.firmID;
							cv.contact = fv.contact;
							cv.frozenFuns = 0;
							cv.isOpen = ProcConstants.firmTypeOpen;
							cv.status = ProcConstants.firmStatusTrue;
							;
							cv.opentime = new Date();
							rv.actionId = getMktActionID();
							conn = DAO.getConnection();
							try {
								conn.setAutoCommit(false);
								rv = dataProcess.changeFirmIsOpen(fv.firmID, 1, cv.bankID, conn);
								if (rv.result < 0) {
									conn.rollback();
									return rv;
								}
								DAO.addCorrespond(cv, conn);
								conn.commit();
							} catch (SQLException er) {
								conn.rollback();
								throw er;
							} finally {
								conn.setAutoCommit(true);
							}
						}
					} else if (cvresult.size() != 1) {
						rv.result = ErrorCode.FindFirmIDAndAccountMore;
						rv.remark = "通过银行编号、签约号查询出信息重复";
						return rv;
					} else {
						CorrespondValue cv2 = cvresult.get(0);
						if (cv.card == null || !cv.card.equals(cv2.card)) {
							rv.result = ErrorCode.outMoney_CardNo;
							rv.remark = "客户证件号码错误";
							return rv;
						}
						rv.actionId = getMktActionID();
						conn = DAO.getConnection();
						try {
							conn.setAutoCommit(false);
							rv = dataProcess.changeFirmIsOpen(cv2.firmID, 1, cv2.bankID, conn);
							if (rv.result < 0) {
								conn.rollback();
								return rv;
							}
							cv.contact = cv2.contact;
							cv.id = cv2.id;
							DAO.openCorrespond(cv, conn);
							conn.commit();
						} catch (SQLException er) {
							conn.rollback();
							throw er;
						} finally {
							conn.setAutoCommit(true);
						}
					}
				} catch (SQLException e) {
					rv.result = (int) ErrorCode.rgstAccount_DatabaseException;
					rv.remark = "签约时数据库异常";
					log("银行开户 交易账号代码与银行账号对应SQLException," + Tool.getExceptionTrace(e));
				} catch (Exception e) {
					rv.result = (int) ErrorCode.rgstAccount_SysException;
					rv.remark = "签约时系统异常";
					log("银行开户 交易账号代码与银行账号对应SQLException," + Tool.getExceptionTrace(e));
				} finally {
					DAO.closeStatement(null, null, conn);
				}
			}
		} catch (Exception e) {
			rv.result = ErrorCode.rgstAccount_SysException;
			rv.remark = "处理器修改交易商信息异常";
			e.printStackTrace();
			LogEndmsg endmsg = new LogEndmsg();
			InterfaceLog log = new InterfaceLog();
			log.account = "";
			log.launcher = 1;
			log.bankID = cv.bankID;
			log.contact = cv.contact;
			endmsg.code = "";
			log.type = 14;// 发送出金审核结果
			endmsg.note = e.getMessage();
			if (!"0000".equalsIgnoreCase(endmsg.code)) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			this.interfaceLog(log);
		}

		return rv;
	}

	/**
	 * 华夏预签约
	 * 
	 * @author tongyu
	 * @param correspondValue
	 *            交易账号和银行对应关系
	 * @return long 操作结果：0 成功 非0 失败(-640000:信息不完整 -640002：交易账号不存在 --640003：银行不存在 -640004：账号已注册 -40005:银行签约失败 -640005：数据库操作失败 -640006：系统异常
	 */
	public ReturnValue synchroAccount(CorrespondValue correspondValue) {
		log("华夏预签约华夏银行 synchroAccount correspondValue:" + (correspondValue == null ? "为 null" : "\n" + correspondValue.toString() + "\n"));
		ReturnValue result = new ReturnValue();// ifbankTrade(correspondValue.bankID,correspondValue.firmID,null,3,ProcConstants.marketLaunch).result;

		String defaultAccount = Tool.getConfig(ProcConstants.defaultAccount);// 特殊定制的银行账号
		Connection conn = null;
		if (correspondValue.bankID == null || correspondValue.bankID.length() == 0 || correspondValue.firmID == null
				|| correspondValue.firmID.length() == 0
		// || correspondValue.account==null || correspondValue.account.length()==0
		) {
			result.result = ErrorCode.synchroAccount_InfoHalfbaked;
			result.remark = "华夏预签约失败，信息不完整";
			LogEndmsg endmsg = new LogEndmsg();
			InterfaceLog log = new InterfaceLog();
			log.account = "";
			log.bankID = correspondValue.bankID;
			log.contact = correspondValue.contact;
			endmsg.code = "";
			log.type = 12;// 发送出金审核结果
			endmsg.note = result.remark;
			if (!"0000".equalsIgnoreCase(endmsg.code)) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			this.interfaceLog(log);
			return result;
		}
		try {
			conn = DAO.getConnection();
			BankValue bv = DAO.getBank(correspondValue.bankID);
			if (bv.validFlag != 0) {
				result.result = ErrorCode.BankDisable;
				result.remark = "华夏预签约失败，银行被禁用";
				log("华夏预签约，银行被禁用");
				LogEndmsg endmsg = new LogEndmsg();
				InterfaceLog log = new InterfaceLog();
				log.account = "";
				log.bankID = correspondValue.bankID;
				log.contact = correspondValue.contact;
				endmsg.code = "";
				log.type = 12;// 发送出金审核结果
				endmsg.note = result.remark;
				if (!"0000".equalsIgnoreCase(endmsg.code)) {
					log.result = 1;
				}
				log.endMsg = endmsg.toString();
				this.interfaceLog(log);
				return result;
			}
			int tradeFlag = getTradeTime(bv.beginTime, bv.endTime);
			if (tradeFlag == 1) {
				result.result = ErrorCode.Be_TradeTime;
				result.remark = "华夏预签约失败，交易时间未到";
				log("华夏预签约失败，交易时间未到");
				LogEndmsg endmsg = new LogEndmsg();
				InterfaceLog log = new InterfaceLog();
				log.account = "";
				log.bankID = correspondValue.bankID;
				log.contact = correspondValue.contact;
				endmsg.code = "";
				log.type = 12;// 发送出金审核结果
				endmsg.note = result.remark;
				if (!"0000".equalsIgnoreCase(endmsg.code)) {
					log.result = 1;
				}
				log.endMsg = endmsg.toString();
				this.interfaceLog(log);
				return result;
			} else if (tradeFlag == 2) {
				result.result = ErrorCode.Af_TradeTime;
				result.remark = "华夏预签约失败，交易时间已过";
				log("华夏预签约失败，交易时间已过");
				LogEndmsg endmsg = new LogEndmsg();
				InterfaceLog log = new InterfaceLog();
				log.account = "";
				log.bankID = correspondValue.bankID;
				log.contact = correspondValue.contact;
				endmsg.code = "";
				log.type = 12;// 发送出金审核结果
				endmsg.note = result.remark;
				if (!"0000".equalsIgnoreCase(endmsg.code)) {
					log.result = 1;
				}
				log.endMsg = endmsg.toString();
				this.interfaceLog(log);
				return result;
			}
			if (DAO.getFirm(correspondValue.firmID, conn) == null) {// 交易账号不存在
				result.result = ErrorCode.synchroAccount_NOFirm;
				result.remark = "华夏预签约失败，交易商不存在";
				log("华夏预签约失败，交易账号不存在，错误码=" + result.result);
				LogEndmsg endmsg = new LogEndmsg();
				InterfaceLog log = new InterfaceLog();
				log.account = "";
				log.bankID = correspondValue.bankID;
				log.contact = correspondValue.contact;
				endmsg.code = "";
				log.type = 12;// 发送出金审核结果
				endmsg.note = result.remark;
				if (!"0000".equalsIgnoreCase(endmsg.code)) {
					log.result = 1;
				}
				log.endMsg = endmsg.toString();
				this.interfaceLog(log);
				return result;
			} else if (DAO.getCorrespondList(" and bankID='" + correspondValue.bankID + "' and firmID='" + correspondValue.firmID + "' and (account='"
					+ correspondValue.account + "' or account='" + defaultAccount + "') and isopen=1", conn).size() > 0) {// 账号已签约
				result.result = ErrorCode.synchroAccount_GRSAcount;
				result.remark = "华夏预签约失败，账号已注册";
				log("华夏预签约失败，账号已注册，错误码=" + result.result);
				LogEndmsg endmsg = new LogEndmsg();
				InterfaceLog log = new InterfaceLog();
				log.account = "";
				log.bankID = correspondValue.bankID;
				log.contact = correspondValue.contact;
				endmsg.code = "";
				log.type = 12;//
				endmsg.note = result.remark;
				if (!"0000".equalsIgnoreCase(endmsg.code)) {
					log.result = 1;
				}
				log.endMsg = endmsg.toString();
				this.interfaceLog(log);
				return result;
				// } else if(DAO.getCorrespondList(" and firmID='"+correspondValue.firmID+"' and (account='"+correspondValue.account+"' or
				// account='"+defaultAccount+"')",conn).size()>0){
			} else if (DAO.getCorrespondList(" and firmID='" + correspondValue.firmID + "' ", conn).size() > 0) {
				BankAdapterRMI bankadapter = getAdapter(correspondValue.bankID);
				// TODO ReturnValue returnValue=bankadapter.synchroAccount(correspondValue);//调用适配器华夏预签约方法
				ReturnValue returnValue = null;
				if (returnValue != null && returnValue.result == 0) {// 华夏预签约成功
					log("华夏预签约状态" + result);
				} else {
					log(returnValue.remark);// 华夏预签约失败
					result.result = ErrorCode.synchroAccount_BankRgsFail;
					result.remark = returnValue.remark;// "华夏预签约失败";
					log("华夏预签约失败，错误码=" + result);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result.result = ErrorCode.synchroAccount_DatabaseException;
			result.remark = "华夏预签约错误，数据库操作失败";
			log("华夏预签约异常，错误码=" + Tool.getExceptionTrace(e));
			LogEndmsg endmsg = new LogEndmsg();
			InterfaceLog log = new InterfaceLog();
			log.account = "";
			log.bankID = correspondValue.bankID;
			log.contact = correspondValue.contact;
			endmsg.code = "";
			log.type = 12;// 发送出金审核结果
			endmsg.note = e.getMessage();
			if (!"0000".equalsIgnoreCase(endmsg.code)) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			this.interfaceLog(log);
		} catch (Exception e) {
			e.printStackTrace();
			result.result = ErrorCode.synchroAccount_SysException;
			result.remark = "华夏预签约错误，系统异常";
			log("华夏预签约Exception，错误码" + Tool.getExceptionTrace(e));
			LogEndmsg endmsg = new LogEndmsg();
			InterfaceLog log = new InterfaceLog();
			log.account = "";
			log.bankID = correspondValue.bankID;
			log.contact = correspondValue.contact;
			endmsg.code = "";
			log.type = 12;// 发送出金审核结果
			endmsg.note = e.getMessage();
			if (!"0000".equalsIgnoreCase(endmsg.code)) {
				log.result = 1;
			}
			log.endMsg = endmsg.toString();
			this.interfaceLog(log);
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	/**
	 * 判断是否超出了转账时间段限制
	 * 
	 * @param startTime
	 *            起始时间
	 * @param endTime
	 *            结束时间
	 * @return int 0 可以交易,1 未到交易时间,2 交易时间已过
	 */
	private int getTradeTime(String startTime, String endTime) {
		this.log("判断是否超出了交易时间范围");
		int result = 1;
		if (startTime == null || startTime.trim().length() <= 0 || endTime == null || endTime.trim().length() <= 0) {
			return 0;
		} else {
			startTime = startTime.trim();
			endTime = endTime.trim();
			if (startTime.length() < 6) {
				for (int i = 0; i < 6 - startTime.length(); i++) {
					startTime += "0";
				}
			}
			if (endTime.length() < 6) {
				for (int i = 0; i < 6 - startTime.length(); i++) {
					endTime += "0";
				}
			}
		}
		java.util.Date now = new java.util.Date();
		java.util.Date start = Tool.getDate(startTime);
		java.util.Date end = Tool.getDate(endTime);
		if (now.getTime() <= start.getTime()) {
			result = 1;
		} else if (now.getTime() >= end.getTime()) {
			result = 2;
		} else {
			result = 0;
		}
		return result;
	}

	/**
	 * 修改银行流水号
	 * id 序列号
	 * funID 银行流水号
	 * status 状态
	 * bankTime 银行事件
	 */
	public ReturnValue modCapitalInfoStatus(long id, String funID, int status, Timestamp bankTime) {
		ReturnValue rv = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			if (bankTime == null) {
				bankTime = new Timestamp(System.currentTimeMillis());// 当前系统时间
			}
			DAO.modCapitalInfoStatus(id, funID, ProcConstants.statusFailure, bankTime, conn);
		} catch (SQLException e) {
			e.printStackTrace();
			rv.result = -1;
			rv.remark = "修改 " + id + " 银行流水号 " + funID + " 数据库异常";
		} catch (Exception e) {
			e.printStackTrace();
			rv.result = -1;
			rv.remark = "修改 " + id + " 银行流水号 " + funID + " 系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return rv;
	}

	/**
	 * 判断是否可以解约
	 */
	public ReturnValue ifQuitFirm(String firmID, String bankID) {
		ReturnValue rv = new ReturnValue();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			rv.result = dataProcess.ifFirmDelAccount(firmID, bankID, conn);
		} catch (SQLException e) {
			e.printStackTrace();
			rv.result = -1;
			log("判断账号解约异常，错误码=" + Tool.getExceptionTrace(e));
		} catch (Exception e) {
			e.printStackTrace();
			rv.result = -1;
			log("判断账号解约异常，错误码=" + Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return rv;
	}

	/**
	 * 获取出入金流水信息
	 * 
	 * @param contacts
	 *            交易账号和银行绑定号
	 * @param bankID
	 *            银行编号
	 * @return
	 */
	public Map<String, CapitalValue> getCapitalValue(Vector<String> funid, String bankID) {
		Map<String, CapitalValue> result = new HashMap<String, CapitalValue>();
		Connection conn = null;// 数据连接
		try {
			conn = DAO.getConnection();
			if (funid != null && funid.size() > 0) {
				for (String contact : funid) {
					Vector<CapitalValue> capitalList = DAO.getCapitalInfoList(" and funid='" + contact + "' and bankid='" + bankID + "' ", conn);
					if (capitalList != null && capitalList.size() > 0) {
						CapitalValue cv = capitalList.get(0);
						result.put(contact, cv);
					}
				}
			}
		} catch (Exception e) {
			log(Tool.getExceptionTrace(e));
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public static void main(String args[]) {
		StartupRmiserver sr = new StartupRmiserver();
		try {
			sr.init();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

}
