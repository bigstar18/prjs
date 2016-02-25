package gnnt.bank.otc;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import gnnt.bank.otc.dao.BankDAO;
import gnnt.bank.otc.dao.BankDAOFactory;
import gnnt.bank.otc.util.Util;
import gnnt.bank.otc.vo.FirmFundsBankValue;
import gnnt.bank.otc.vo.FirmFundsValue;
import gnnt.bank.otc.vo.FirmPlatformMsg;
import gnnt.bank.platform.rmi.PlatformProcessorRMI;
import gnnt.bank.platform.util.Tool;
import gnnt.bank.platform.vo.RequestMsg;
import gnnt.trade.bank.vo.CapitalValue;
import gnnt.trade.bank.vo.CorrespondValue;
import gnnt.trade.bank.vo.FirmValue;
import gnnt.trade.bank.vo.InOutMoney;
import gnnt.trade.bank.vo.RZQS;
import gnnt.trade.bank.vo.ReturnValue;

public class OtcProcessor {
	private static final int INMONEY = 0;
	private static final int OUTMONEY = 1;
	private static final int TRANS = 3;
	private static final int statusSuccess = 0;
	private static final int statusFailure = 1;
	private static final int statusProcessing = 2;
	protected static DataProcess dataProcess;
	private static BankDAO DAO;

	public OtcProcessor() {
		try {
			DAO = BankDAOFactory.getDAO();
			dataProcess = new DataProcess();
		} catch (Exception e) {
			e.printStackTrace();
			Util.log("初始化处理器失败：" + Util.getExceptionTrace(e));
		}
	}

	public ReturnValue testRMI() {
		ReturnValue result = new ReturnValue();
		result.result = 0L;
		result.remark = "处理器收到了请求";
		return result;
	}

	public ReturnValue testRMI(String msg) {
		ReturnValue result = new ReturnValue();
		result.result = 0L;
		result.remark = ("处理器收到了请求信息[" + msg + "]");
		return result;
	}

	private PlatformProcessorRMI getPlatform() {
		PlatformProcessorRMI result = null;
		String url = Tool.getConfig("platformURL");
		try {
			result = (PlatformProcessorRMI) Naming.lookup(url);
		} catch (MalformedURLException e) {
			Tool.log("获取平台处理器RMI发生MalformedURLException" + Tool.getExceptionTrace(e));
		} catch (RemoteException e) {
			Tool.log("获取平台处理器RMI发生RemoteException" + Tool.getExceptionTrace(e));
		} catch (NotBoundException e) {
			Tool.log("获取平台处理器RMI发生NotBoundException" + Tool.getExceptionTrace(e));
		}
		return result;
	}

	public ReturnValue getIsOpenBanks(String firmID) {
		Tool.log("查询交易商[" + firmID + "]对应的平台号已经签约的银行");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", firmID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm == null) || (vecPfm.size() <= 0)) {
			Tool.log("交易系统交易商[" + firmID + "]还没有和平台建立关联关系");
			result.result = -1L;
			result.remark = "没有对应的关系";
			return result;
		}
		PlatformProcessorRMI plat = getPlatform();
		if (plat == null) {
			Tool.log("获取的平台处理器RMI为空");
			result.result = -1L;
			result.remark = "获取平台处理器失败";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getIsOpenBanks");
		req.setParams(new Object[] { ((FirmPlatformMsg) vecPfm.get(0)).platFirmID });
		try {
			result = plat.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器查询对应关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		}
		return result;
	}

	public ReturnValue getCorrespondValue(String firmID) {
		Tool.log("查询交易商[" + firmID + "]对应的平台信息");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", firmID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm == null) || (vecPfm.size() <= 0)) {
			Tool.log("交易系统交易商[" + firmID + "]还没有和平台建立关联关系");
			result.result = -1L;
			result.remark = "没有对应的关系";
			return result;
		}
		PlatformProcessorRMI plat = getPlatform();
		if (plat == null) {
			Tool.log("获取的平台处理器RMI为空");
			result.result = -1L;
			result.remark = "获取平台处理器失败";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getCorrespondValues");
		req.setParams(new Object[] { " where firmID='" + ((FirmPlatformMsg) vecPfm.get(0)).platFirmID + "'" });
		try {
			result = plat.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器查询对应关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		}
		return result;
	}

	public ReturnValue getBanks(String filter) {
		Tool.log("获取平台接入的银行列表信息，查询条件[" + filter + "]");
		ReturnValue result = new ReturnValue();

		PlatformProcessorRMI plat = getPlatform();
		if (plat == null) {
			Tool.log("获取平台处理器失败");
			result.result = -1L;
			result.remark = "获取平台处理器失败";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getBankList");
		req.setParams(new Object[] { filter });
		try {
			result = plat.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "调用平台处理器异常";
		}
		return result;
	}

	public ReturnValue getSystemMessage(String filter) {
		Tool.log("查询平台接入的交易系统信息");
		ReturnValue result = new ReturnValue();

		PlatformProcessorRMI plat = getPlatform();
		if (plat == null) {
			Tool.log("获取平台处理器RMI链接失败");
			result.result = -1L;
			result.remark = "获取平台处理器失败";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getSystemMessage");
		req.setParams(new Object[] { filter });
		try {
			result = plat.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器方法异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		}
		return result;
	}

	public ReturnValue signedContract(CorrespondValue corr) {
		Tool.log("交易商[" + corr.sysFirmID + "]发起签约,银行账号[" + corr.account + "]账户名[" + corr.accountName + "]证件类型[" + corr.cardType + "]证件号[" + corr.card
				+ "]");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecFPF = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", corr.sysFirmID);
			vecFPF = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询签约对应关系异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		PlatformProcessorRMI plat = getPlatform();
		if (plat == null) {
			Tool.log("获取平台处理器RMI失败");
			result.result = -1L;
			result.remark = "链接平台处理器失败";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID(corr.bankID);
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			conn.setAutoCommit(false);
			if ((vecFPF == null) || (vecFPF.size() <= 0)) {
				req.setMethodName("getPlatformMsg");
				req.setParams(new Object[] { Tool.getConfig("SystemID"), corr.sysFirmID });
				try {
					result = plat.doWork(req);
				} catch (RemoteException e) {
					Tool.log("调用平台处理器方法异常：" + Tool.getExceptionTrace(e));
				}
				if ((result == null) || (result.result != 0L)) {
					Tool.log("调用平台处理器查询交易商对应的平台信息失败");
					result.result = -1L;
					result.remark = "查询信息失败";
					conn.rollback();
					ReturnValue localReturnValue1 = result;
					return localReturnValue1;
				}
				String platFirmID = (String) result.msg[0];

				corr.firmID = platFirmID;
				corr.systemID = Tool.getConfig("SystemID");

				req.setMethodName("signedContract");
				req.setParams(new Object[] { corr });
				result = plat.doWork(req);
			} else {
				corr.firmID = ((FirmPlatformMsg) vecFPF.get(0)).platFirmID;
				corr.systemID = Tool.getConfig("SystemID");

				req.setMethodName("signedContract");
				req.setParams(new Object[] { corr });
				result = plat.doWork(req);
			}
			if ((result.result == 0L) || (result.result == 5L)) {
				Tool.log("平台处理器处理成功，" + (result.result == 0L ? "银行处理器成功" : "该银行不支持市场端签约"));
				conn.commit();
			} else {
				Tool.log("平台处理失败，失败原因：" + result.remark);
				conn.rollback();
			}
		} catch (Exception e1) {
			Tool.log("系统异常：" + Tool.getExceptionTrace(e1));
			result.result = -1L;
			result.remark = "系统异常";
			try {
				conn.rollback();
			} catch (SQLException e) {
				Tool.log("回滚数据异常：" + Tool.getExceptionTrace(e));
				e.printStackTrace();
			}
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue noticeDelPlatFirmID(String platfirmID, String firmID) {
		Tool.log("平台通知交易系统维护交易商和平台号的对应关系");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", firmID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm == null) || (vecPfm.size() <= 0)) {
			Tool.log("交易商[" + firmID + "]不经存在和平台号的对应关系");
			result.result = -1L;
			result.remark = "对应关系不存在";
			return result;
		}
		FirmPlatformMsg fpfm = (FirmPlatformMsg) vecPfm.get(0);
		if (!fpfm.platFirmID.equals(platfirmID)) {
			Tool.log("交易系统记录的平台号[" + fpfm.platFirmID + "]和传入的平台号[" + platfirmID + "]不相符");
			result.result = -1L;
			result.remark = "平台号不符";
			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				DAO.delPlatformMsg(fpfm, conn);
				dataProcess.changeFirmIsOpen(firmID, 2, "01", conn);
				conn.commit();
				result.result = 0L;
				result.remark = "删除并解约成功";
			} catch (Exception e) {
				Tool.log("删除对应关系解约异常：" + Tool.getExceptionTrace(e));
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
		if (result.result == 1L) {
			Tool.log("删除交易商[" + firmID + "]和平台号[" + platfirmID + "]的对应关系成功");
			result.result = 0L;
			result.remark = "删除成功";
		} else {
			Tool.log("删除交易商[" + firmID + "]和平台号[" + platfirmID + "]的对应关系失败");
			result.result = -1L;
			result.remark = "删除失败";
		}
		return result;
	}

	public ReturnValue noticePlatFirmID(String platfirmID, String firmID) {
		Tool.log("平台通知交易系统维护交易商和平台号的对应关系");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", firmID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm != null) && (vecPfm.size() > 0)) {
			Tool.log("交易商[" + firmID + "]已经存在和平台号的对应关系，对应的平台号为[" + ((FirmPlatformMsg) vecPfm.get(0)).platFirmID + "]");
			if (platfirmID.equals(((FirmPlatformMsg) vecPfm.get(0)).platFirmID)) {
				Tool.log("已经存在的平台号和传入的平台号对应");
				result.result = 0L;
				result.remark = "对应关系已经存在";
			} else {
				Tool.log("已经存在的平台号和传入的平台号不相同");
				result.result = -1L;
				result.remark = ("对应关系已经存在，但交易系统保存的平台号[" + ((FirmPlatformMsg) vecPfm.get(0)).platFirmID + "]和通知的平台号[" + platfirmID + "]不相符");
			}
			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);

				FirmPlatformMsg fpfm = new FirmPlatformMsg();
				fpfm.firmID = firmID;
				fpfm.platFirmID = platfirmID;
				DAO.addPlatformMsg(fpfm, conn);
				dataProcess.changeFirmIsOpen(firmID, 1, "01", conn);
				conn.commit();
				result.result = 0L;
				result.remark = "处理成功";
			} catch (Exception e) {
				Tool.log("记录对应关系，调用交易系统签约异常：" + Tool.getExceptionTrace(e));
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

	public ReturnValue modCorrespondValue(CorrespondValue corrLast, CorrespondValue corr) {
		Tool.log("查询交易商[" + corr.sysFirmID + "]对应的平台信息");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", corr.sysFirmID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm == null) || (vecPfm.size() <= 0)) {
			Tool.log("交易系统交易商[" + corr.sysFirmID + "]还没有和平台建立关联关系");
			result.result = -1L;
			result.remark = "没有关联的银行无法修改";
			return result;
		}
		corrLast.firmID = ((FirmPlatformMsg) vecPfm.get(0)).platFirmID;
		corrLast.systemID = Tool.getConfig("SystemID");
		corr.firmID = ((FirmPlatformMsg) vecPfm.get(0)).platFirmID;
		corr.systemID = Tool.getConfig("SystemID");

		PlatformProcessorRMI plat = getPlatform();
		if (plat == null) {
			Tool.log("获取的平台处理器RMI为空");
			result.result = -1L;
			result.remark = "获取平台处理器失败";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID(corr.bankID);
		req.setMethodName("modCorrespondValue");
		req.setParams(new Object[] { corrLast, corr });
		try {
			result = plat.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器查询对应关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		}
		return result;
	}

	public ReturnValue modFundsPwd(String firmID, String oldPwd, String newPwd) {
		Tool.log("交易商[" + firmID + "]修改资金密码");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", firmID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm == null) || (vecPfm.size() <= 0)) {
			Tool.log("交易系统交易商[" + firmID + "]还没有和平台建立关联关系");
			result.result = -1L;
			result.remark = "没有关联的银行无法修改";
			return result;
		}
		PlatformProcessorRMI plat = getPlatform();
		if (plat == null) {
			Tool.log("获取的平台处理器RMI为空");
			result.result = -1L;
			result.remark = "获取平台处理器失败";
			return result;
		}
		if ((oldPwd == null) || ("".equals(oldPwd))) {
			Tool.log("旧密码传入为空");
			result.result = -1L;
			result.remark = "旧密码为空";
			return result;
		}
		if ((newPwd == null) || ("".equals(newPwd))) {
			Tool.log("旧密码传入为空");
			result.result = -1L;
			result.remark = "旧密码为空";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("modPwd");
		req.setParams(new Object[] { ((FirmPlatformMsg) vecPfm.get(0)).platFirmID, oldPwd, newPwd });
		try {
			result = plat.doWork(req);
			if (result.result == -1L) {
				result.remark = "原密码输入错误";
			}
		} catch (RemoteException e) {
			Tool.log("调用平台处理器查询对应关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		}
		return result;
	}

	public ReturnValue removeContract(CorrespondValue corr) {
		Tool.log("交易商[" + corr.sysFirmID + "]发起银行[" + corr.bankID + "]的解约");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", corr.sysFirmID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm == null) || (vecPfm.size() <= 0)) {
			Tool.log("交易系统交易商[" + corr.firmID + "]还没有和平台建立关联关系");
			result.result = -1L;
			result.remark = "没有关联的银行无法修改";
			return result;
		}
		Vector<String> checkFirmStatus = null;
		try {
			checkFirmStatus = DAO.checkFirmEnabled(corr.sysFirmID);
		} catch (SQLException e1) {
			Tool.log("查询交易商状态异常：" + Tool.getExceptionTrace(e1));
		}
		if ((checkFirmStatus == null) || (checkFirmStatus.size() <= 0)) {
			Tool.log("交易系统交易商[" + corr.sysFirmID + "]不存在或交易商代码为空");
			result.result = -1L;
			result.remark = "交易系统端不存在或交易商代码为空";
			return result;
		}
		if ((!"N".equals(((String) checkFirmStatus.get(0)).trim())) && (!"U".equals(((String) checkFirmStatus.get(0)).trim()))) {
			Tool.log("交易系统交易商[" + corr.sysFirmID + "]状态不是已签约或正常状态");
			result.result = -2L;
			result.remark = "交易商状态不是已签约或正常状态";
			return result;
		}
		Vector<CapitalValue> vecCap = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", corr.sysFirmID);
			map.put(" and bankID=? ", corr.bankID);
			map.put(" and createdate=? ", Tool.fmtDate(new Date()));
			vecCap = DAO.getCapitalList(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的转账流水信息异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		if ((vecCap != null) && (vecCap.size() > 0)) {
			Tool.log("交易商[" + corr.sysFirmID + "]当日在银行[" + corr.bankID + "]有转账流水，不允许解约");
			result.result = -1L;
			result.remark = "该银行当日有转账流水，不允许解约";
			return result;
		}
		PlatformProcessorRMI plat = getPlatform();
		if (plat == null) {
			Tool.log("获取的平台处理器RMI为空");
			result.result = -1L;
			result.remark = "获取平台处理器失败";
			return result;
		}
		corr.systemID = Tool.getConfig("SystemID");
		RequestMsg req = new RequestMsg();
		req.setBankID(corr.bankID);

		req.setMethodName("delCorrMarket");
		req.setParams(new Object[] { corr });
		try {
			result = plat.doWork(req);
		} catch (RemoteException e1) {
			Tool.log("获取平台号签约的银行异常：" + Tool.getExceptionTrace(e1));
			result.result = -1L;
			result.remark = "系统异常";
		}
		if (result.result == 0L) {
			Tool.log("平台处理解约成功，提交数据");
		} else {
			Tool.log("平台处理解约失败，回滚数据");
		}
		return result;
	}

	public ReturnValue getFirmBalance(String FIRMID, String bankid, String pwd, String bankPwd) {
		Tool.log("查询交易商[" + FIRMID + "]的资金余额");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", FIRMID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm == null) || (vecPfm.size() <= 0)) {
			Tool.log("交易系统交易商[" + FIRMID + "]还没有和平台建立关联关系");
			result.result = -1L;
			result.remark = "没有签约的银行";
			return result;
		}
		PlatformProcessorRMI plat = getPlatform();
		if (plat == null) {
			Tool.log("获取的平台处理器RMI为空");
			result.result = -1L;
			result.remark = "获取平台处理器失败";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID(bankid);
		req.setMethodName("getFirmbalancePlat");
		req.setParams(new Object[] { ((FirmPlatformMsg) vecPfm.get(0)).platFirmID, bankid, pwd, bankPwd });
		try {
			result = plat.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器查询对应关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		}
		return result;
	}

	public ReturnValue getCapitalList(String firmID, String startDate, String endDate) {
		Tool.log("查询交易商[" + firmID + "]在日期[" + startDate + "--" + endDate + "]内的转账流水记录");
		ReturnValue result = new ReturnValue();
		Vector<CapitalValue> vecCapital = null;
		Map<String, Object> params = new HashMap();
		if (firmID != null) {
			params.put(" and firmID=? ", firmID);
		}
		if (startDate != null) {
			params.put(" and trunc(createTime)>=to_date(?,'yyyy-MM-dd') ", startDate);
		}
		if (endDate != null) {
			params.put(" and trunc(createTime)<=to_date(?,'yyyy-MM-dd') ", endDate);
		}
		try {
			vecCapital = DAO.getCapitalList(params);
		} catch (SQLException e) {
			Tool.log("查询流水信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCapital == null) || (vecCapital.size() < 0)) {
			Tool.log("查询流水信息失败");
			vecCapital = new Vector();
		}
		result.result = 0L;
		result.msg = new Object[] { vecCapital };
		return result;
	}

	public ReturnValue getAllCapitalList(String firmID, String startDate, String endDate) {
		Tool.log("查询交易商[" + firmID + "]对应的平台号在日期[" + startDate + "--" + endDate + "]内的所有转账流水记录");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", firmID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm == null) || (vecPfm.size() <= 0)) {
			Tool.log("交易系统交易商[" + firmID + "]还没有和平台建立关联关系");
			result.remark = "没有签约的银行";
			result.msg = new Object[] { new Vector() };
			return result;
		}
		PlatformProcessorRMI platform = getPlatform();
		if (platform == null) {
			Tool.log("获取平台处理器RMI链接失败");
			result.remark = "获取RMI链接失败";
			result.msg = new Object[] { new Vector() };
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getAllCapitalList");
		req.setParams(new Object[] { ((FirmPlatformMsg) vecPfm.get(0)).platFirmID, startDate, endDate });
		try {
			result = platform.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器方法异常：" + Tool.getExceptionTrace(e));
			result.remark = "调用平台处理器方法异常";
			result.msg = new Object[] { new Vector() };
		}
		return result;
	}

	public ReturnValue getRelationSystem(String firmID) {
		Tool.log("查询交易商[" + firmID + "]对应的平台号关联的交易系统信息");
		ReturnValue result = new ReturnValue();
		result.result = 0L;

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", firmID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm == null) || (vecPfm.size() <= 0)) {
			Tool.log("交易系统交易商[" + firmID + "]还没有和平台建立关联关系");
			result.remark = "没有签约的银行";
			result.msg = new Object[] { new Vector() };
			return result;
		}
		PlatformProcessorRMI platform = getPlatform();
		if (platform == null) {
			Tool.log("获取平台处理器RMI链接失败");
			result.remark = "获取RMI链接失败";
			result.msg = new Object[] { new Vector() };
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getRelationSystem");
		req.setParams(new Object[] { ((FirmPlatformMsg) vecPfm.get(0)).platFirmID });
		try {
			result = platform.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器方法异常：" + Tool.getExceptionTrace(e));
			result.remark = "调用平台处理器方法异常";
			result.msg = new Object[] { new Vector() };
		}
		return result;
	}

	public ReturnValue inOutMoneyRequest(InOutMoney inOutMoney) {
		Tool.log("交易商[" + inOutMoney.sysFirmID + "]向平台发起了针对交易系统[" + inOutMoney.systemID + "]银行[" + inOutMoney.bankID + "]的"
				+ (String.valueOf(0).equals(inOutMoney.inOutMoneyFlag) ? "入金" : "出金") + ",金额[" + inOutMoney.money + "]");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", inOutMoney.sysFirmID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm == null) || (vecPfm.size() <= 0)) {
			Tool.log("交易系统交易商[" + inOutMoney.sysFirmID + "]还没有和平台建立关联关系");
			result.result = -1L;
			result.remark = "没有签约的银行";
			return result;
		}
		inOutMoney.firmID = ((FirmPlatformMsg) vecPfm.get(0)).platFirmID;

		PlatformProcessorRMI platform = getPlatform();
		if (platform == null) {
			Tool.log("获取平台处理器RMI失败");
			result.result = -1L;
			result.remark = "获取平台处理器失败";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID(inOutMoney.bankID);
		req.setMethodName("inOutMoneyReqDo");
		req.setParams(new Object[] { inOutMoney });
		try {
			result = platform.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台异常" + result);
			Tool.getExceptionTrace(e);
			result.remark = "系统异常";
			result.result = -1L;
		}
		return result;
	}

	public ReturnValue inMoney(InOutMoney inMoney, int inMoneyType) {
		Tool.log("交易商[" + inMoney.sysFirmID + "]做银行[" + inMoney.bankID + "]的" + (inMoneyType == 1 ? "市场端" : "银行端") + "入金[" + inMoney.money + "]元");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", inMoney.sysFirmID);
			map.put(" and platfirmid=? ", inMoney.firmID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm == null) || (vecPfm.size() <= 0)) {
			Tool.log("交易系统交易商[" + inMoney.sysFirmID + "]还没有和平台号[" + inMoney.firmID + "]建立关联关系");
			result.result = -1L;
			result.remark = "交易系统端不存在交易商和平台号的对应关系";
			return result;
		}
		inMoney.sysAciontID = DAO.getActionID();

		long capitalID = 0L;
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);

				CapitalValue cVal = new CapitalValue();
				cVal.actionID = inMoney.sysAciontID;
				cVal.bankID = inMoney.bankID;
				cVal.firmID = inMoney.firmID;
				cVal.sysFirmID = inMoney.sysFirmID;
				cVal.money = inMoney.money;
				cVal.note = (inMoneyType == 1 ? "市场端入金" : "银行端入金");
				cVal.status = 2;
				cVal.type = 0;
				capitalID = DAO.addCapitalInfo(cVal, conn);
				if (capitalID <= 0L) {
					Tool.log("增加出入金流水失败");
					result.result = -1L;
					result.remark = "记录入金流水失败";
					conn.rollback();
					ReturnValue localReturnValue1 = result;

					conn.setAutoCommit(true);

					DAO.closeStatement(null, null, conn);
					if (inMoneyType == 2) {
						if (inMoney.sysAciontID == 0L) {
							result.funID = "";
						} else {
							result.funID = String.valueOf(inMoney.sysAciontID);
						}
					}
					return localReturnValue1;
				}
				result.result = 0L;
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				result.result = -1L;
				result.remark = "记录入金流水异常";
				Tool.log("市场端发起入金写资金流水SQLException,数据回滚:" + Tool.getExceptionTrace(e));
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
			conn.setAutoCommit(true);
			if (inMoneyType == 1) {
				PlatformProcessorRMI plat = getPlatform();
				if (plat == null) {
					Tool.log("获取平台处理器RMI失败，入金失败，将入金流水置为失败");
					result.result = -1L;
					result.remark = "获取平台处理器失败";
					result.result = DAO.modCapitalInfoStatus(capitalID, "", 1, new Timestamp(System.currentTimeMillis()), conn);
					DAO.modCapitalInfoNote(capitalID, "获取平台处理器失败，入金失败", conn);
					if (result.result != 1L) {
						Tool.log("将该入金流水状态修改成失败的操作失败，流水扔是处理中状态");
						result.remark = "获取平台处理器失败，修改流水状态失败";
					}
					ReturnValue localReturnValue2 = result;
					return localReturnValue2;
				}
				RequestMsg req = new RequestMsg();
				req.setBankID(inMoney.bankID);
				req.setMethodName("inMoneyMarket");
				req.setParams(new Object[] { inMoney });
				result = plat.doWork(req);
			}
			try {
				conn.setAutoCommit(false);
				if (result.result == 0L) {
					Tool.log(inMoneyType == 1 ? "市场端入金，银行处理成功" : "银行端入金，市场处理入金成功");

					DAO.modCapitalInfoStatus(capitalID, inMoneyType == 1 ? result.funID : String.valueOf(inMoney.actionID), 0,
							new Timestamp(System.currentTimeMillis()), conn);
					DAO.modCapitalInfoNote(capitalID, inMoneyType == 1 ? "银行处理成功" : "入金成功", conn);

					dataProcess.updateFundsFull("01", inMoney.sysFirmID, inMoney.sysAciontID + "", inMoney.money, 0, conn);
					conn.commit();
				} else if (result.result == 5L) {
					Tool.log(inMoneyType == 1 ? "市场端入金，银行返回处理中" : "银行端入金，市场处理中");
					result.remark = "处理中";
					DAO.modCapitalInfoNote(capitalID, "处理中", conn);
					conn.commit();
				} else {
					Tool.log(inMoneyType == 1 ? "市场端入金，银行处理失败" : "银行端入金，市场处理失败");

					DAO.modCapitalInfoStatus(capitalID, inMoneyType == 1 ? result.funID : String.valueOf(inMoney.actionID), 1,
							new Timestamp(System.currentTimeMillis()), conn);
					DAO.modCapitalInfoNote(capitalID, (inMoneyType == 1 ? "银行处理失败" : "入金失败") + result.remark, conn);
					conn.commit();
				}
			} catch (SQLException e) {
				conn.rollback();
				result.result = -1L;
				result.remark = "处理入金流水异常";
				Tool.log("市场端发起入金处理资金流水SQLException,数据回滚:" + Tool.getExceptionTrace(e));
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("系统异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "处理器异常";
		} finally {
			DAO.closeStatement(null, null, conn);
			if (inMoneyType == 2) {
				if (inMoney.sysAciontID == 0L) {
					result.funID = "";
				} else {
					result.funID = String.valueOf(inMoney.sysAciontID);
				}
			}
		}
		return result;
	}

	public ReturnValue outMoney(InOutMoney outMoney, int outMoneyType) {
		Tool.log(
				"交易商[" + outMoney.sysFirmID + "]做银行[" + outMoney.bankID + "]的" + (outMoneyType == 1 ? "市场端" : "银行端") + "出金[" + outMoney.money + "]元");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", outMoney.sysFirmID);
			map.put(" and platfirmid=? ", outMoney.firmID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm == null) || (vecPfm.size() <= 0)) {
			Tool.log("交易系统交易商[" + outMoney.sysFirmID + "]还没有和平台号[" + outMoney.firmID + "]建立关联关系");
			result.result = -1L;
			result.remark = "交易系统端不存在交易商和平台号的对应关系";
			return result;
		}
		Vector<String> checkFirmStatus = null;
		try {
			checkFirmStatus = DAO.checkFirmEnabled(outMoney.sysFirmID);
		} catch (SQLException e1) {
			Tool.log("查询交易商状态异常：" + Tool.getExceptionTrace(e1));
		}
		if ((checkFirmStatus == null) || (checkFirmStatus.size() <= 0)) {
			Tool.log("交易系统交易商[" + outMoney.sysFirmID + "]不存在或交易商代码为空");
			result.result = -1L;
			result.remark = "交易系统端不存在或交易商代码为空";
			return result;
		}
		if ((!"N".equals(((String) checkFirmStatus.get(0)).trim())) && (!"U".equals(((String) checkFirmStatus.get(0)).trim()))) {
			Tool.log("交易系统交易商[" + outMoney.sysFirmID + "]状态不是已签约或正常状态");
			result.result = -2L;
			result.remark = "交易商状态不是已签约或正常状态";
			return result;
		}
		long capitalID = 0L;
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);

				double realFunds = dataProcess.getRealFunds("01", outMoney.sysFirmID, 1, conn);
				ReturnValue localReturnValue1;
				if (realFunds < outMoney.money) {
					Tool.log("交易商[" + outMoney.sysFirmID + "]的可用余额[" + realFunds + "]小于出金金额[" + outMoney.money + "]，不允许出金");
					result.result = -1L;
					result.remark = "可用余额小于出金金额";

					conn.rollback();
					localReturnValue1 = result;

					conn.setAutoCommit(true);

					DAO.closeStatement(null, null, conn);
					if (outMoneyType == 2) {
						if (outMoney.sysAciontID == 0L) {
							result.funID = "";
						} else {
							result.funID = String.valueOf(outMoney.sysAciontID);
						}
					}
					return localReturnValue1;
				}
				outMoney.sysAciontID = DAO.getActionID();

				CapitalValue cVal = new CapitalValue();
				cVal.actionID = outMoney.sysAciontID;
				cVal.bankID = outMoney.bankID;
				cVal.firmID = outMoney.firmID;
				cVal.sysFirmID = outMoney.sysFirmID;
				cVal.money = outMoney.money;
				cVal.note = (outMoneyType == 1 ? "市场端出金" : "银行端出金");
				cVal.status = 2;
				cVal.type = 1;
				capitalID = DAO.addCapitalInfo(cVal, conn);
				if (capitalID <= 0L) {
					Tool.log("增加出出金流水失败");
					result.result = -1L;
					result.remark = "记录出金流水失败";
					conn.rollback();
					localReturnValue1 = result;

					conn.setAutoCommit(true);

					DAO.closeStatement(null, null, conn);
					if (outMoneyType == 2) {
						if (outMoney.sysAciontID == 0L) {
							result.funID = "";
						} else {
							result.funID = String.valueOf(outMoney.sysAciontID);
						}
					}
					return localReturnValue1;
				}
				dataProcess.updateFrozenFunds(outMoney.sysFirmID, outMoney.money, conn);

				result.result = 0L;
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				result.result = -1L;
				result.remark = "记录出金流水异常";
				Tool.log("市场端发起出金写资金流水SQLException,数据回滚:" + Tool.getExceptionTrace(e));
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
			conn.setAutoCommit(true);
			if (outMoneyType == 1) {
				PlatformProcessorRMI plat = getPlatform();
				if (plat == null) {
					Tool.log("获取平台处理器RMI失败，出金失败，将出金流水置为失败");
					result.result = -1L;
					result.remark = "获取平台处理器失败";
					try {
						conn.setAutoCommit(false);
						DAO.modCapitalInfoStatus(capitalID, "", 1, new Timestamp(System.currentTimeMillis()), conn);
						DAO.modCapitalInfoNote(capitalID, "获取平台处理器失败，出金失败", conn);
						dataProcess.updateFrozenFunds(outMoney.sysFirmID, -1.0D * outMoney.money, conn);
						conn.commit();
					} catch (Exception e) {
						Tool.log("获取平台处理器失败，处理流水失败、释放资金过程异常，数据回滚：" + Tool.getExceptionTrace(e));
						conn.rollback();
					} finally {
						conn.setAutoCommit(true);
					}
					ReturnValue localReturnValue2 = result;
					return localReturnValue2;
				}
				RequestMsg req = new RequestMsg();
				req.setBankID(outMoney.bankID);
				req.setMethodName("outMoneyMarket");
				req.setParams(new Object[] { outMoney });
				result = plat.doWork(req);
			}
			try {
				conn.setAutoCommit(false);
				if (result.result == 0L) {
					Tool.log(outMoneyType == 1 ? "市场端出金，银行处理成功" : "银行端出金，市场处理出金成功");

					DAO.modCapitalInfoStatus(capitalID, outMoneyType == 1 ? result.funID : String.valueOf(outMoney.actionID), 0,
							new Timestamp(System.currentTimeMillis()), conn);
					DAO.modCapitalInfoNote(capitalID, outMoneyType == 1 ? "银行处理成功" : "出金成功", conn);

					dataProcess.updateFrozenFunds(outMoney.sysFirmID, -1.0D * outMoney.money, conn);

					dataProcess.updateFundsFull("01", outMoney.sysFirmID, outMoney.sysAciontID + "", outMoney.money, 1, conn);
				} else if (result.result == 5L) {
					Tool.log(outMoneyType == 1 ? "市场端出金，银行返回处理中" : "银行端出金，市场处理中");
					result.remark = "处理中";
					DAO.modCapitalInfoNote(capitalID, "处理中", conn);
				} else {
					Tool.log(outMoneyType == 1 ? "市场端出金，银行处理失败" : "银行端出金，市场处理失败");

					DAO.modCapitalInfoStatus(capitalID, outMoneyType == 1 ? result.funID : String.valueOf(outMoney.actionID), 1,
							new Timestamp(System.currentTimeMillis()), conn);
					DAO.modCapitalInfoNote(capitalID, (outMoneyType == 1 ? "银行处理失败" : "出金失败") + result.remark, conn);

					dataProcess.updateFrozenFunds(outMoney.sysFirmID, -1.0D * outMoney.money, conn);
				}
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				result.result = -1L;
				result.remark = "处理出金流水异常";
				Tool.log("市场端发起出金处理资金流水SQLException,数据回滚:" + Tool.getExceptionTrace(e));
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("系统异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "处理器异常";
		} finally {
			DAO.closeStatement(null, null, conn);
			if (outMoneyType == 2) {
				if (outMoney.sysAciontID == 0L) {
					result.funID = "";
				} else {
					result.funID = String.valueOf(outMoney.sysAciontID);
				}
			}
		}
		return result;
	}

	public ReturnValue transferFunds(String outSystemID, String inSystemID, String firmID, double money, String fundsPwd) {
		Tool.log("本系统交易商[" + firmID + "]从交易系统[" + outSystemID + "]向交易系统[" + inSystemID + "]划转资金[" + money + "]");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", firmID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm == null) || (vecPfm.size() <= 0)) {
			Tool.log("交易系统交易商[" + firmID + "]还没有和平台号建立关联关系");
			result.result = -1L;
			result.remark = "交易系统端不存在交易商和平台号的对应关系";
			return result;
		}
		String platFirmID = ((FirmPlatformMsg) vecPfm.get(0)).platFirmID;

		PlatformProcessorRMI platform = getPlatform();
		if (platform == null) {
			Tool.log("获取平台处理器RMI链接失败");
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("transferFunds");
		req.setParams(new Object[] { outSystemID, inSystemID, platFirmID, Double.valueOf(money), fundsPwd });
		try {
			result = platform.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器方法异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		}
		return result;
	}

	public ReturnValue doFundsTransfer(String platFirmID, String firmID, double money, int inOrOut, String platformActionID) {
		Tool.log("资金划转==>交易商[" + firmID + "]" + (inOrOut == 0 ? "转入" : "转出") + "资金[" + money + "],平台流水号[" + platformActionID + "]");
		ReturnValue result = new ReturnValue();

		long actionID = 0L;
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				if (inOrOut == 1) {
					double realFunds = dataProcess.getRealFunds("01", firmID, 1, conn);
					if (realFunds < money) {
						Tool.log("交易商[" + firmID + "]的可用余额[" + realFunds + "]小于划转资金[" + money + "]，资金划转失败");
						result.result = -1L;
						result.remark = "转出系统可用余额不足";

						conn.rollback();
						ReturnValue localReturnValue1 = result;

						conn.setAutoCommit(true);
						return localReturnValue1;
					}
				}
				actionID = DAO.getActionID();
				result.funID = String.valueOf(actionID);

				CapitalValue cVal = new CapitalValue();
				cVal.actionID = actionID;
				cVal.bankID = "";
				cVal.firmID = platFirmID;
				cVal.sysFirmID = firmID;
				cVal.money = money;
				cVal.note = (inOrOut == 0 ? "划入资金" : "划出资金");
				cVal.status = 0;
				cVal.type = 3;
				cVal.funID = platformActionID;
				DAO.addCapitalInfo(cVal, conn);

				dataProcess.updateFundsFull("01", firmID, actionID + "", money, inOrOut == 0 ? 0 : 1, conn);

				result.result = 0L;
				result.remark = (inOrOut == 0 ? "划入成功" : "划出成功");
				conn.commit();
			} catch (Exception e) {
				Tool.log("插入流水操作资金异常，数据库回滚：" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "记录资金流水失败";
				conn.rollback();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("资金划转，系统异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		} finally {
			DAO.closeStatement(null, null, conn);
		}
		return result;
	}

	public ReturnValue noticeOutMoneyResult(String sysActionID, String actionID, boolean flag) {
		Tool.log("平台通知市场流水号[" + sysActionID + "]平台流水号[" + actionID + "]的出金结果是[" + (flag ? "成功" : "失败") + "]");
		ReturnValue result = new ReturnValue();

		Map<String, Object> params = new HashMap();
		params.put(" and actionID=? ", Long.valueOf(Long.parseLong(sysActionID)));
		Vector<CapitalValue> vecCap = null;
		try {
			vecCap = DAO.getCapitalList(params);
		} catch (SQLException e) {
			Tool.log("查询流水信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCap == null) || (vecCap.size() <= 0)) {
			Tool.log("没有查询到市场流水号[" + sysActionID + "]的流水信息");
			result.result = -1L;
			result.remark = "没有查询到流水信息";
			return result;
		}
		if (vecCap.size() > 1) {
			Tool.log("查询到多条市场端流水号[" + sysActionID + "]的流水信息");
			result.result = -1L;
			result.remark = ("市场端流水号为[" + sysActionID + "]的流水有多条");
			return result;
		}
		CapitalValue cv = (CapitalValue) vecCap.get(0);
		if (cv.status != 2) {
			Tool.log("该笔流水已经被处理");
			result.result = -1L;
			result.remark = "该笔流水已经被处理";
			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				Timestamp bankTime = new Timestamp(System.currentTimeMillis());
				if (flag) {
					DAO.modCapitalInfoStatus(cv.iD, sysActionID, 0, bankTime, conn);
					DAO.modCapitalInfoNote(cv.iD, cv.note + "平台通知成功，出金成功", conn);

					dataProcess.updateFrozenFunds(cv.sysFirmID, -1.0D * cv.money, conn);

					dataProcess.updateFundsFull("01", cv.sysFirmID, actionID, cv.money, 1, conn);
				} else {
					DAO.modCapitalInfoStatus(cv.iD, sysActionID, 1, bankTime, conn);
					DAO.modCapitalInfoNote(cv.iD, cv.note + "平台通知失败，出金失败", conn);

					dataProcess.updateFrozenFunds(cv.sysFirmID, -1.0D * cv.money, conn);
				}
				result.result = 0L;
				result.remark = "处理成功";
				conn.commit();
			} catch (Exception e) {
				Tool.log("修改流水状态，处理资金异常：" + Tool.getExceptionTrace(e));
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

	public ReturnValue noticeInMoneyResult(String sysActionID, String actionID, boolean flag) {
		Tool.log("平台通知市场流水号[" + sysActionID + "]平台流水号[" + actionID + "]的入金结果是[" + (flag ? "成功" : "失败") + "]");
		ReturnValue result = new ReturnValue();

		Map<String, Object> params = new HashMap();
		params.put(" and actionID=? ", Long.valueOf(Long.parseLong(sysActionID)));
		Vector<CapitalValue> vecCap = null;
		try {
			vecCap = DAO.getCapitalList(params);
		} catch (SQLException e) {
			Tool.log("查询流水信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecCap == null) || (vecCap.size() <= 0)) {
			Tool.log("没有查询到市场流水号[" + sysActionID + "]的流水信息");
			result.result = -1L;
			result.remark = "没有查询到流水信息";
			return result;
		}
		if (vecCap.size() > 1) {
			Tool.log("查询到多条市场端流水号[" + sysActionID + "]的流水信息");
			result.result = -1L;
			result.remark = ("市场端流水号为[" + sysActionID + "]的流水有多条");
			return result;
		}
		CapitalValue cv = (CapitalValue) vecCap.get(0);
		if (cv.status != 2) {
			Tool.log("该笔流水已经被处理");
			result.result = -1L;
			result.remark = "该笔流水已经被处理";
			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);
				Timestamp bankTime = new Timestamp(System.currentTimeMillis());
				if (flag) {
					DAO.modCapitalInfoStatus(cv.iD, sysActionID, 0, bankTime, conn);
					DAO.modCapitalInfoNote(cv.iD, cv.note + "平台通知成功，入金成功", conn);

					dataProcess.updateFundsFull("01", cv.sysFirmID, actionID, cv.money, 0, conn);
				} else {
					DAO.modCapitalInfoStatus(cv.iD, sysActionID, 1, bankTime, conn);
					DAO.modCapitalInfoNote(cv.iD, cv.note + "平台通知失败，入金失败", conn);
				}
				result.result = 0L;
				result.remark = "处理成功";
				conn.commit();
			} catch (Exception e) {
				Tool.log("修改流水状态，处理资金异常：" + Tool.getExceptionTrace(e));
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

	public ReturnValue checkORGANIZATIONID(String ORGANIZATIONID) {
		Tool.log("检查机构代码[" + ORGANIZATIONID + "]是否存在");
		ReturnValue result = new ReturnValue();
		try {
			boolean check = DAO.checkORGANIZATIONID(ORGANIZATIONID);
			if (check) {
				result.result = 0L;
				result.remark = ("机构[" + ORGANIZATIONID + "]存在");
			}
		} catch (SQLException e) {
			Tool.log("查询异常：" + Tool.getExceptionTrace(e));
		}
		return result;
	}

	public ReturnValue getCapitalByMember(String memberId, String ORGANIZATIONID, String firmID, String startDate, String endDate) {
		String logString = "查询"
				+ ((ORGANIZATIONID == null) || ("".equals(ORGANIZATIONID)) ? "会员[" + memberId + "]"
						: new StringBuilder("机构[").append(ORGANIZATIONID).append("]").toString())
				+ "下面的" + ((firmID == null) || ("".equals(firmID)) ? "所有交易商" : new StringBuilder("交易商[").append(firmID).append("]").toString())
				+ "在日期[" + startDate + "--" + endDate + "]内的转账流水";
		Tool.log(logString);

		ReturnValue result = new ReturnValue();

		String filter = " where firmID in (";
		if ((firmID != null) && (!"".equals(firmID))) {
			Vector<FirmPlatformMsg> vecPfm = null;
			try {
				Map<String, Object> map = new HashMap();
				map.put(" and firmID=? ", firmID);
				vecPfm = DAO.getPlatformMsg(map);
			} catch (SQLException e) {
				Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
			}
			if ((vecPfm == null) || (vecPfm.size() <= 0)) {
				Tool.log("交易系统交易商[" + firmID + "]还没有和平台号建立关联关系");
				result.result = -1L;
				result.remark = "交易系统端不存在交易商和平台号的对应关系";
				result.msg = new Object[] { new Vector() };
				return result;
			}
			filter = filter + "'" + ((FirmPlatformMsg) vecPfm.get(0)).platFirmID + "')";
		} else {
			boolean check = false;
			if ((ORGANIZATIONID != null) && (!"".equals(ORGANIZATIONID))) {
				result = checkORGANIZATIONID(ORGANIZATIONID);
				if ((result != null) && (result.result == 0L)) {
					check = true;
				}
			}
			Vector<String> vecPlatFirm = null;
			try {
				if (check) {
					vecPlatFirm = DAO.getPlatFirmIDByORGANIZATIONID(ORGANIZATIONID);
				} else {
					vecPlatFirm = DAO.getPlatFirmIDByMemberID(memberId);
				}
			} catch (SQLException e) {
				Tool.log("查询该会员下面所有交易商对应的平台号异常：" + Tool.getExceptionTrace(e));
			}
			if ((vecPlatFirm == null) || (vecPlatFirm.size() <= 0)) {
				Tool.log("查询该会员下面所有交易商对应的平台号失败");
				result.result = -1L;
				result.remark = "查询失败";
				result.msg = new Object[] { new Vector() };
				return result;
			}
			for (String str : vecPlatFirm) {
				filter = filter + "'" + str + "',";
			}
			filter = filter.substring(0, filter.length() - 1) + ")";
		}
		filter = filter + " and trunc(createTime)>=to_date('" + startDate + "','yyyy-MM-dd') and trunc(createTime)<=to_date('" + endDate
				+ "','yyyy-MM-dd') and systemID='" + Tool.getConfig("SystemID") + "'";

		PlatformProcessorRMI platform = getPlatform();
		if (platform == null) {
			Tool.log("获取平台处理器失败");
			result.result = -1L;
			result.remark = "查询失败";
			result.msg = new Object[] { new Vector() };
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getAllCapitalList");
		req.setParams(new Object[] { filter });
		try {
			result = platform.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			result.msg = new Object[] { new Vector() };
		}
		return result;
	}

	public ReturnValue getOutMoneyAduit(String memberId, String ORGANIZATIONID, String startDate, String endDate) {
		Tool.log("机构[" + ORGANIZATIONID + "]" + "下面交易商的待审核流水");
		ReturnValue result = new ReturnValue();

		Vector<String> vecPlatFirm = null;
		try {
			boolean check = false;
			if ((ORGANIZATIONID != null) && (!"".equals(ORGANIZATIONID))) {
				result = checkORGANIZATIONID(ORGANIZATIONID);
				if ((result != null) && (result.result == 0L)) {
					check = true;
				}
			}
			if (check) {
				vecPlatFirm = DAO.getPlatFirmIDByORGANIZATIONID(ORGANIZATIONID);
			} else {
				vecPlatFirm = DAO.getPlatFirmIDByMemberID(memberId);
			}
		} catch (SQLException e) {
			Tool.log("查询该会员下面所有交易商对应的平台号异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPlatFirm == null) || (vecPlatFirm.size() <= 0)) {
			Tool.log("查询该会员下面所有交易商对应的平台号失败");
			result.result = -1L;
			result.remark = "查询失败";
			result.msg = new Object[] { new Vector() };
			return result;
		}
		String filter = " where firmID in (";
		for (String str : vecPlatFirm) {
			filter = filter + "'" + str + "',";
		}
		filter = filter.substring(0, filter.length() - 1) + ")";
		filter = filter + " and trunc(createTime)>=to_date(" + startDate + ",'yyyy-MM-dd') and trunc(createTime)<=to_date(" + endDate
				+ ",'yyyy-MM-dd') and type=1 and status=3 and systemID='" + Tool.getConfig("SystemID") + "'";

		PlatformProcessorRMI platform = getPlatform();
		if (platform == null) {
			Tool.log("获取平台处理器失败");
			result.result = -1L;
			result.remark = "查询失败";
			result.msg = new Object[] { new Vector() };
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getAllCapitalList");
		req.setParams(new Object[] { filter });
		try {
			result = platform.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			result.msg = new Object[] { new Vector() };
		}
		return result;
	}

	public ReturnValue outMoneyAudit(long actionID, boolean flag) {
		Tool.log("流水号[" + actionID + "]的出金被审核" + (flag ? "通过" : "拒绝"));
		ReturnValue result = new ReturnValue();

		PlatformProcessorRMI platform = getPlatform();
		if (platform == null) {
			Tool.log("获取平台处理器失败");
			result.result = -1L;
			result.remark = "获取平台处理器失败";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("outMoneyAudit");
		req.setParams(new Object[] { Long.valueOf(actionID), Boolean.valueOf(flag) });
		try {
			result = platform.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		}
		return result;
	}

	public ReturnValue getRZData(Date date) {
		Tool.log("查询[" + Tool.fmtDate(date) + "]的日终数据");
		ReturnValue result = new ReturnValue();

		result = checkRZData(date);
		if (result.result != 0L) {
			result.result = -1L;
			result.remark = "该日还没有结算数据";
			return result;
		}
		Vector<RZQS> vecRZData = null;
		try {
			vecRZData = DAO.getRZData(date);
		} catch (SQLException e) {
			Tool.log("获取日终数据异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecRZData == null) || (vecRZData.size() < 0)) {
			Tool.log("查询日终数据失败");
			result.result = -1L;
			result.remark = "查询失败";
			return result;
		}
		if (vecRZData.size() == 0) {
			Tool.log("查询[" + Tool.fmtDate(date) + "]结算数据，没有该日结算数据");
			result.result = -1L;
			result.remark = "当日还没有结算数据";
			return result;
		}
		result.result = 0L;
		result.remark = "查询成功";
		result.msg = new Object[] { vecRZData };
		return result;
	}

	public ReturnValue checkRZData(Date date) {
		Tool.log("检查日期[" + Tool.fmtDate(date) + "]是否有结算数据");
		ReturnValue result = new ReturnValue();
		boolean check = false;
		try {
			check = DAO.checkRZData(date);
		} catch (SQLException e) {
			Tool.log("检查是否有结算数据异常：" + Tool.getExceptionTrace(e));
			check = false;
		}
		if (check) {
			result.result = 0L;
			result.remark = "有结算数据";
		} else {
			result.result = -1L;
			result.remark = "没有结算数据";
		}
		return result;
	}

	public ReturnValue isTradeDate(Date tradeDate) {
		ReturnValue result = new ReturnValue();
		try {
			if (tradeDate != null) {
				boolean flag = DAO.isTradeDate(tradeDate);
				if (flag) {
					result.result = 0L;
					result.remark = ("日期[" + Tool.fmtDate(tradeDate) + "]是交易日");
				} else {
					result.result = -3L;
					result.remark = ("日期[" + Tool.fmtDate(tradeDate) + "]不是交易日");
				}
			} else {
				result.result = -4L;
				result.remark = "传入的日期为空，不合法";
			}
		} catch (SQLException e) {
			result.result = -1L;
			result.remark = ("查询日期[" + Tool.fmtDate(tradeDate) + "]是否为交易日时数据库异常");
			e.printStackTrace();
		} catch (Exception e) {
			result.result = -2L;
			result.remark = ("查询日期[" + Tool.fmtDate(tradeDate) + "]是否为交易日时系统异常");
			e.printStackTrace();
		}
		return result;
	}

	public ReturnValue getCheckMsg(String bankid, String fid) {
		ReturnValue result = new ReturnValue();
		PlatformProcessorRMI platform = getPlatform();
		if (platform == null) {
			result.result = -1L;
			result.remark = "获取处理器异常";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID(bankid);
		req.setMethodName("getCheckMsg");
		req.setParams(new Object[] { bankid, fid });
		try {
			result = platform.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器方法异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "调用平台处理器异常";
		}
		return result;
	}

	public ReturnValue getActionID() {
		ReturnValue result = new ReturnValue();
		PlatformProcessorRMI platform = getPlatform();
		if (platform == null) {
			result.result = -1L;
			result.remark = "获取平台处理器失败";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getActionID");
		req.setParams(null);
		try {
			result = platform.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器方法异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "调用平台处理器方法异常";
		}
		return result;
	}

	public ReturnValue getCorrespondValueInfo(String firmID, String bankID) {
		Tool.log("查询交易商[" + firmID + "]对应的平台信息");
		ReturnValue result = new ReturnValue();

		Vector<FirmPlatformMsg> vecPfm = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmID=? ", firmID);
			vecPfm = DAO.getPlatformMsg(map);
		} catch (SQLException e) {
			Tool.log("查询交易系统端记录的交易商代码对应的平台信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vecPfm == null) || (vecPfm.size() <= 0)) {
			Tool.log("交易系统交易商[" + firmID + "]还没有和平台建立关联关系");
			result.result = -1L;
			result.remark = "没有对应的关系";
			return result;
		}
		PlatformProcessorRMI plat = getPlatform();
		if (plat == null) {
			Tool.log("获取的平台处理器RMI为空");
			result.result = -1L;
			result.remark = "获取平台处理器失败";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("getCorrespondValues");
		req.setParams(new Object[] { " where firmID='" + ((FirmPlatformMsg) vecPfm.get(0)).platFirmID + "' and bankID='" + bankID + "'" });
		try {
			result = plat.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用平台处理器查询对应关系异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
		}
		return result;
	}

	public ReturnValue getCapitals(Date date) {
		Tool.log("查询日期[" + Tool.fmtDate(date) + "]内所有成功的流水");
		ReturnValue result = new ReturnValue();
		Vector<CapitalValue> vecCap = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and type<>?", Long.valueOf(2L));
			map.put(" and status=?", Long.valueOf(0L));
			map.put(" and createdate=?", Tool.fmtDate(date));
			vecCap = DAO.getCapitalList(map);
		} catch (SQLException e) {
			Tool.log("查询流水信息异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		if ((vecCap != null) && (vecCap.size() == 0)) {
			Tool.log("当日没有成功的流水信息");
			vecCap = new Vector();
		}
		result.result = 0L;
		result.remark = "查询成功";
		result.msg = new Object[] { vecCap };
		return result;
	}

	public ReturnValue checkAccount(CorrespondValue corr) {
		Tool.log("银行[" + corr.bankID + "]账户[" + corr.account + "|" + corr.accountName + "]证件[" + corr.card + "|" + corr.cardType + "]的信息验证");
		ReturnValue result = new ReturnValue();

		PlatformProcessorRMI platform = getPlatform();
		if (platform == null) {
			Tool.log("获取资金平台处理器失败");
			result.result = -1L;
			result.remark = "获取处理核心失败";
			return result;
		}
		RequestMsg req = new RequestMsg();
		req.setBankID(corr.bankID);
		req.setMethodName("checkAccount");
		req.setParams(new Object[] { corr });
		try {
			result = platform.doWork(req);
		} catch (RemoteException e) {
			Tool.log("调用资金平台处理器方法异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "调用处理核心异常";
		}
		return result;
	}

	public ReturnValue getAllFirm() {
		ReturnValue result = new ReturnValue();
		Vector<FirmValue> vec = null;
		try {
			vec = DAO.getAllFirm();
		} catch (SQLException e) {
			Tool.log("获取所有交易商信息异常：" + Tool.getExceptionTrace(e));
		}
		if ((vec == null) || (vec.size() <= 0)) {
			Tool.log("获取所有交易商信息失败");
			result.result = -1L;
			result.remark = "失败";
			return result;
		}
		Map<String, FirmValue> map = new HashMap();
		for (FirmValue fv : vec) {
			map.put(fv.firmID, fv);
		}
		result.result = 0L;
		result.remark = "查询成功";
		result.msg = new Object[] { map };
		return result;
	}

	public ReturnValue getFirmFunds(String firmID) {
		Tool.log("查询交易商[" + firmID + "]总资金信息");
		ReturnValue result = new ReturnValue();
		FirmFundsValue ffv = null;
		try {
			ffv = DAO.getFirmFunds(firmID);
		} catch (SQLException e) {
			Tool.log("查询交易商资金账户信息异常：" + Tool.getExceptionTrace(e));
		}
		result.result = 0L;
		result.remark = "查询成功";
		result.msg = new Object[] { ffv };
		return result;
	}

	public ReturnValue getFirmFundsBank(String firmID) {
		Tool.log("查询交易商[" + firmID + "]在每个银行的资金信息");
		ReturnValue result = new ReturnValue();
		Vector<FirmFundsBankValue> vecFFBV = null;

		return result;
	}

	public ReturnValue chargeAgainst(CapitalValue cap) {
		Tool.log("冲正，交易商[" + cap.sysFirmID + "]的" + (cap.type == 0 ? "入金" : "出金") + "，金额[" + cap.money + "]平台流水号[" + cap.actionID + "]系统流水号["
				+ cap.funID + "]");
		ReturnValue result = new ReturnValue();

		Vector<CapitalValue> vec = null;
		try {
			Map<String, Object> map = new HashMap();
			map.put(" and firmid=?", cap.sysFirmID);
			if (cap.actionID > 0L) {
				map.put(" and funid=?", cap.actionID);
			}
			vec = DAO.getCapitalList(map);
			if (((vec == null) || (vec.size() <= 0)) && (cap.funID != null) && (!"".equals(cap.funID))) {
				map.remove("  and funid=?");
				map.put(" and actionID=?", Long.valueOf(Long.parseLong(cap.funID)));
				vec = DAO.getCapitalList(map);
			}
		} catch (SQLException e) {
			Tool.log("查询流水信息异常：" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "系统异常";
			return result;
		}
		if ((vec == null) || (vec.size() <= 0)) {
			result.result = -1L;
			result.remark = "流水不存在";
			return result;
		}
		if (((CapitalValue) vec.get(0)).status != 0) {
			Tool.log("交易系统端流水未成功，不能冲正");
			result.result = -1L;
			result.remark = "未成功的流水不能冲正";
			return result;
		}
		if (cap.type != ((CapitalValue) vec.get(0)).type) {
			Tool.log("冲正流水类型[" + cap.type + "]与已经记录流水类型[" + ((CapitalValue) vec.get(0)).type + "]不符");
			result.result = -1L;
			result.remark = "流水类型不符";
			return result;
		}
		if (cap.money != ((CapitalValue) vec.get(0)).money) {
			Tool.log("冲正流水金额[" + cap.money + "]与记录流水金额[" + ((CapitalValue) vec.get(0)).money + "]不符");
			result.result = -1L;
			result.remark = "流水金额不符";
			return result;
		}
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			if (cap.type == 0) {
				double realFunds = dataProcess.getRealFunds("01", cap.sysFirmID, 1, conn);
				if (realFunds < cap.money) {
					Tool.log("交易商[" + cap.sysFirmID + "]的可用余额[" + realFunds + "]小于冲正金额[" + cap.money + "]，冲正失败");
					result.result = -1L;
					result.remark = "可用余额小于冲正金额";
					ReturnValue localReturnValue2 = result;
					return localReturnValue2;
				}
			}
			try {
				conn.setAutoCommit(false);

				DAO.modCapitalInfoStatus(((CapitalValue) vec.get(0)).iD, ((CapitalValue) vec.get(0)).funID, 9,
						new Timestamp(System.currentTimeMillis()), conn);
				DAO.modCapitalInfoNote(((CapitalValue) vec.get(0)).iD, ((CapitalValue) vec.get(0)).note + ",被冲正", conn);
				if (cap.type == 0) {
					dataProcess.updateFundsFull("01", cap.sysFirmID, ((CapitalValue) vec.get(0)).actionID + "", cap.money, 1, conn);
				} else if (cap.type == 1) {
					dataProcess.updateFundsFull("01", cap.sysFirmID, ((CapitalValue) vec.get(0)).actionID + "", cap.money, 0, conn);
				} else {
					Tool.log("冲正流水类型异常");
					result.result = -1L;
					result.remark = "流水类型异常";
					conn.rollback();
					ReturnValue localReturnValue1 = result;

					conn.setAutoCommit(true);
					return localReturnValue1;
				}
				result.result = 0L;
				result.remark = "冲正成功";
				conn.commit();
			} catch (SQLException e) {
				Tool.log("数据库异常" + Tool.getExceptionTrace(e));
				result.result = -1L;
				result.remark = "数据库异常";
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

	public ReturnValue inOutMoneyByHand(InOutMoney inOutMoney) {
		Tool.log("交易商[" + inOutMoney.sysFirmID + "]做银行[" + inOutMoney.bankID + "]的手工" + ("1".equals(inOutMoney.inOutMoneyFlag) ? "出金" : "入金") + "["
				+ inOutMoney.money + "]元");
		ReturnValue result = new ReturnValue();

		inOutMoney.sysAciontID = DAO.getActionID();
		Connection conn = null;
		try {
			conn = DAO.getConnection();
			try {
				conn.setAutoCommit(false);

				CapitalValue cVal = new CapitalValue();
				cVal.actionID = inOutMoney.sysAciontID;
				cVal.bankID = inOutMoney.bankID;
				cVal.firmID = inOutMoney.firmID;
				cVal.sysFirmID = inOutMoney.sysFirmID;
				cVal.money = inOutMoney.money;
				cVal.note = inOutMoney.remark;
				cVal.status = 0;
				cVal.type = (String.valueOf(0).equals(inOutMoney.inOutMoneyFlag) ? 0 : 1);
				cVal.funID = String.valueOf(inOutMoney.actionID);
				cVal.bankTime = new Timestamp(System.currentTimeMillis());
				DAO.addCapitalInfo(cVal, conn);

				dataProcess.updateFundsFull("01", inOutMoney.sysFirmID, String.valueOf(inOutMoney.sysAciontID), inOutMoney.money,
						Integer.parseInt(inOutMoney.inOutMoneyFlag), conn);

				result.result = 0L;
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				result.result = -1L;
				result.remark = "记录入金流水异常";
				Tool.log("市场端发起入金写资金流水SQLException,数据回滚:" + Tool.getExceptionTrace(e));
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			Tool.log("系统异常:" + Tool.getExceptionTrace(e));
			result.result = -1L;
			result.remark = "处理器异常";
		} finally {
			DAO.closeStatement(null, null, conn);
			result.funID = String.valueOf(inOutMoney.sysAciontID);
		}
		return result;
	}
}
