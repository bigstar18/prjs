package gnnt.MEBS.logonService.kernel.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gnnt.MEBS.activeUser.operation.ActiveUserManage;
import gnnt.MEBS.activeUser.vo.AUCheckUserResultVO;
import gnnt.MEBS.activeUser.vo.AUCheckUserVO;
import gnnt.MEBS.activeUser.vo.AUCompulsoryLogoffResultVO;
import gnnt.MEBS.activeUser.vo.AUCompulsoryLogoffVO;
import gnnt.MEBS.activeUser.vo.AUGetUserResultVO;
import gnnt.MEBS.activeUser.vo.AUGetUserVO;
import gnnt.MEBS.activeUser.vo.AUISLogonResultVO;
import gnnt.MEBS.activeUser.vo.AUISLogonVO;
import gnnt.MEBS.activeUser.vo.AULogoffResultVO;
import gnnt.MEBS.activeUser.vo.AULogoffVO;
import gnnt.MEBS.activeUser.vo.AULogonOrLogoffUserVO;
import gnnt.MEBS.activeUser.vo.AULogonResultVO;
import gnnt.MEBS.activeUser.vo.AULogonVO;
import gnnt.MEBS.activeUser.vo.AUUserManageVO;
import gnnt.MEBS.logonService.kernel.ILogonService;
import gnnt.MEBS.logonService.po.LogonConfigPO;
import gnnt.MEBS.logonService.vo.CheckUserResultVO;
import gnnt.MEBS.logonService.vo.CheckUserVO;
import gnnt.MEBS.logonService.vo.CompulsoryLogoffResultVO;
import gnnt.MEBS.logonService.vo.CompulsoryLogoffVO;
import gnnt.MEBS.logonService.vo.GetUserResultVO;
import gnnt.MEBS.logonService.vo.GetUserVO;
import gnnt.MEBS.logonService.vo.ISLogonResultVO;
import gnnt.MEBS.logonService.vo.ISLogonVO;
import gnnt.MEBS.logonService.vo.LogoffResultVO;
import gnnt.MEBS.logonService.vo.LogoffVO;
import gnnt.MEBS.logonService.vo.LogonOrLogoffUserVO;
import gnnt.MEBS.logonService.vo.LogonResultVO;
import gnnt.MEBS.logonService.vo.LogonVO;
import gnnt.MEBS.logonService.vo.RemoteLogonServerVO;
import gnnt.MEBS.logonService.vo.UserManageVO;

public class LogonService_Standard extends LogonServiceBase implements ILogonService {
	private static final long serialVersionUID = 7554525770181024510L;

	public LogonService_Standard() throws RemoteException {
	}

	public LogonResultVO logon(LogonVO paramLogonVO) {
		this.logger.debug("登录：用户[" + paramLogonVO.getUserID() + "]模块编号[" + paramLogonVO.getModuleID() + "]登录类型[" + paramLogonVO.getLogonType()
				+ "]登录地址[" + paramLogonVO.getLogonIp() + "]");
		AULogonVO localAULogonVO = new AULogonVO();
		localAULogonVO.setLogonType(paramLogonVO.getLogonType());
		localAULogonVO.setModuleID(paramLogonVO.getModuleID());
		localAULogonVO.setUserID(paramLogonVO.getUserID());
		localAULogonVO.setLogonTime(paramLogonVO.getLogonTime());
		localAULogonVO.setLogonIp(paramLogonVO.getLogonIp());
		localAULogonVO.setLastLogonIp(paramLogonVO.getLastLogonIp());
		localAULogonVO.setLastLogonTime(paramLogonVO.getLastLogonTime());
		AULogonResultVO localAULogonResultVO = ActiveUserManage.getInstance().logon(localAULogonVO);
		LogonResultVO localLogonResultVO = new LogonResultVO();
		localLogonResultVO.setMessage(localAULogonResultVO.getMessage());
		localLogonResultVO.setRecode(localAULogonResultVO.getRecode());
		localLogonResultVO.setResult(localAULogonResultVO.getResult());
		localLogonResultVO.setUserManageVO(getUserManageVO(localAULogonResultVO.getUserManageVO()));
		this.logger.debug("登录结果：用户[" + paramLogonVO.getUserID() + "]模块编号[" + paramLogonVO.getModuleID() + "]登录类型[" + paramLogonVO.getLogonType()
				+ "]登录地址[" + paramLogonVO.getLogonIp() + "]结果[" + localLogonResultVO.getResult() + "]返回码[" + localLogonResultVO.getRecode() + "]返回信息["
				+ localLogonResultVO.getMessage() + "]");
		return localLogonResultVO;
	}

	public LogoffResultVO logoff(LogoffVO paramLogoffVO) {
		this.logger.debug(
				"退出：用户[" + paramLogoffVO.getUserID() + "]SessionID[" + paramLogoffVO.getSessionID() + "]退出类型[" + paramLogoffVO.getLogonType() + "]");
		AULogoffVO localAULogoffVO = new AULogoffVO();
		localAULogoffVO.setLogonType(paramLogoffVO.getLogonType());
		localAULogoffVO.setSessionID(paramLogoffVO.getSessionID());
		localAULogoffVO.setUserID(paramLogoffVO.getUserID());
		AULogoffResultVO localAULogoffResultVO = ActiveUserManage.getInstance().logoff(localAULogoffVO);
		LogoffResultVO localLogoffResultVO = new LogoffResultVO();
		localLogoffResultVO.setMessage(localAULogoffResultVO.getMessage());
		localLogoffResultVO.setRecode(localAULogoffResultVO.getRecode());
		localLogoffResultVO.setResult(localAULogoffResultVO.getResult());
		this.logger.debug("退出结果：用户[" + paramLogoffVO.getUserID() + "]SessionID[" + paramLogoffVO.getSessionID() + "]退出类型["
				+ paramLogoffVO.getLogonType() + "]结果[" + localLogoffResultVO.getResult() + "]返回码[" + localLogoffResultVO.getRecode() + "]返回信息["
				+ localLogoffResultVO.getMessage() + "]");
		return localLogoffResultVO;
	}

	public CheckUserResultVO checkUser(CheckUserVO paramCheckUserVO, int paramInt, String paramString) {
		this.logger.debug("用户跳转：用户[" + paramCheckUserVO.getUserID() + "]SessionID[" + paramCheckUserVO.getSessionID() + "]来源模块[" + paramInt + "]来源类型["
				+ paramString + "]转到模块[" + paramCheckUserVO.getToModuleID() + "]转到类型[" + paramCheckUserVO.getLogonType() + "]");
		CheckUserResultVO localCheckUserResultVO = new CheckUserResultVO();
		localCheckUserResultVO.setResult(-1);
		ISLogonVO localISLogonVO = new ISLogonVO();
		localISLogonVO.setLogonType(paramCheckUserVO.getLogonType());
		localISLogonVO.setModuleID(Integer.valueOf(paramCheckUserVO.getToModuleID()));
		localISLogonVO.setSessionID(paramCheckUserVO.getSessionID());
		localISLogonVO.setUserID(paramCheckUserVO.getUserID());
		ISLogonResultVO localISLogonResultVO = isLogon(localISLogonVO);
		if (localISLogonResultVO.getResult() == 1) {
			localCheckUserResultVO.setResult(1);
			localCheckUserResultVO.setUserManageVO(localISLogonResultVO.getUserManageVO());
			this.logger.debug("用户跳转结果：用户[" + paramCheckUserVO.getUserID() + "]SessionID[" + paramCheckUserVO.getSessionID() + "]来源模块[" + paramInt
					+ "]来源类型[" + paramString + "]转到模块[" + paramCheckUserVO.getToModuleID() + "]转到类型[" + paramCheckUserVO.getLogonType() + "]结果["
					+ localCheckUserResultVO.getResult() + "]返回码[" + localCheckUserResultVO.getRecode() + "]返回信息["
					+ localCheckUserResultVO.getMessage() + "]");
			return localCheckUserResultVO;
		}
		int i = getConfigID(paramInt);
		if (i < 0) {
			localCheckUserResultVO.setRecode("-1202");
			localCheckUserResultVO.setMessage("通过模块编号没有找到对应au编号");
			this.logger.debug("用户跳转结果：用户[" + paramCheckUserVO.getUserID() + "]SessionID[" + paramCheckUserVO.getSessionID() + "]来源模块[" + paramInt
					+ "]来源类型[" + paramString + "]转到模块[" + paramCheckUserVO.getToModuleID() + "]转到类型[" + paramCheckUserVO.getLogonType() + "]结果["
					+ localCheckUserResultVO.getResult() + "]返回码[" + localCheckUserResultVO.getRecode() + "]返回信息["
					+ localCheckUserResultVO.getMessage() + "]");
			return localCheckUserResultVO;
		}
		if (i == getSelfLogonConfigPO().getConfigID().intValue()) {
			localISLogonVO.setLogonType(paramString);
			localISLogonResultVO = isLogon(localISLogonVO);
		} else {
			localISLogonVO.setLogonType(paramString);
			localISLogonVO.setModuleID(Integer.valueOf(paramInt));
			RemoteLogonServerVO localObject = (RemoteLogonServerVO) this.logonManagerMap.get(Integer.valueOf(i));
			if (localObject == null) {
				LogonConfigPO localLogonConfigPO1 = getLogonConfigByID(i);
				if (getSelfLogonConfigPO().getSysname().equals(localLogonConfigPO1.getSysname())) {
					localObject = new RemoteLogonServerVO();
					((RemoteLogonServerVO) localObject).setLogonConfigPO(localLogonConfigPO1);
					this.logonManagerMap.put(localLogonConfigPO1.getConfigID(), localObject);
				}
			}
			try {
				localISLogonResultVO = ((RemoteLogonServerVO) localObject).getRmiService().isLogon(localISLogonVO);
			} catch (RemoteException localRemoteException1) {
				int j = ((RemoteLogonServerVO) localObject).clearRMI();
				try {
					localISLogonResultVO = ((RemoteLogonServerVO) localObject).getRmiService().isLogon(localISLogonVO);
				} catch (RemoteException localRemoteException2) {
					if ((this.clearRMITimes > 0) && (j > this.clearRMITimes)) {
						LogonConfigPO localLogonConfigPO2 = getLogonConfigByID(i);
						if (localLogonConfigPO2 != null)
							((RemoteLogonServerVO) localObject).setLogonConfigPO(localLogonConfigPO2);
					}
					localCheckUserResultVO.setRecode("-1");
					localCheckUserResultVO.setMessage("连接来源 RMI 失败");
					this.logger.error("用户验证调用来源 RMI 异常", localRemoteException2);
					return localCheckUserResultVO;
				} catch (Exception localException2) {
					localCheckUserResultVO.setRecode("-1");
					localCheckUserResultVO.setMessage("连接来源 RMI 失败");
					this.logger.error("用户验证调用来源 RMI 异常", localException2);
					return localCheckUserResultVO;
				}
			} catch (Exception localException1) {
				localCheckUserResultVO.setRecode("-1");
				localCheckUserResultVO.setMessage("从来源 RMI 获取用户登录信息异常");
				this.logger.error("从来源 RMI 获取用户登录信息异常", localException1);
				return localCheckUserResultVO;
			}
		}
		if (localISLogonResultVO.getResult() == -1) {
			localCheckUserResultVO.setRecode(localISLogonResultVO.getRecode());
			localCheckUserResultVO.setMessage(localISLogonResultVO.getMessage());
			this.logger.info(localISLogonResultVO.getMessage());
			this.logger.debug("用户跳转结果：用户[" + paramCheckUserVO.getUserID() + "]SessionID[" + paramCheckUserVO.getSessionID() + "]来源模块[" + paramInt
					+ "]来源类型[" + paramString + "]转到模块[" + paramCheckUserVO.getToModuleID() + "]转到类型[" + paramCheckUserVO.getLogonType() + "]结果["
					+ localCheckUserResultVO.getResult() + "]返回码[" + localCheckUserResultVO.getRecode() + "]返回信息["
					+ localCheckUserResultVO.getMessage() + "]");
			return localCheckUserResultVO;
		}
		Object localObject = new AUCheckUserVO();
		((AUCheckUserVO) localObject).setLogonType(paramCheckUserVO.getLogonType());
		((AUCheckUserVO) localObject).setSessionID(paramCheckUserVO.getSessionID());
		((AUCheckUserVO) localObject).setToModuleID(paramCheckUserVO.getToModuleID());
		((AUCheckUserVO) localObject).setUserID(paramCheckUserVO.getUserID());
		((AUCheckUserVO) localObject).setLogonTime(localISLogonResultVO.getUserManageVO().getLogonTime());
		((AUCheckUserVO) localObject).setLogonIp(localISLogonResultVO.getUserManageVO().getLogonIp());
		((AUCheckUserVO) localObject).setLastLogonIp(localISLogonResultVO.getUserManageVO().getLastLogonIp());
		((AUCheckUserVO) localObject).setLastLogonTime(localISLogonResultVO.getUserManageVO().getLastLogonTime());
		AUCheckUserResultVO localAUCheckUserResultVO = ActiveUserManage.getInstance().checkUser((AUCheckUserVO) localObject);
		localCheckUserResultVO.setMessage(localAUCheckUserResultVO.getMessage());
		localCheckUserResultVO.setRecode(localAUCheckUserResultVO.getRecode());
		localCheckUserResultVO.setResult(localAUCheckUserResultVO.getResult());
		localCheckUserResultVO.setUserManageVO(getUserManageVO(localAUCheckUserResultVO.getUserManageVO()));
		this.logger.debug("用户跳转结果：用户[" + paramCheckUserVO.getUserID() + "]SessionID[" + paramCheckUserVO.getSessionID() + "]来源模块[" + paramInt
				+ "]来源类型[" + paramString + "]转到模块[" + paramCheckUserVO.getToModuleID() + "]转到类型[" + paramCheckUserVO.getLogonType() + "]结果["
				+ localCheckUserResultVO.getResult() + "]返回码[" + localCheckUserResultVO.getRecode() + "]返回信息[" + localCheckUserResultVO.getMessage()
				+ "]");
		return localCheckUserResultVO;
	}

	public GetUserResultVO getUserBySessionID(GetUserVO paramGetUserVO) {
		this.logger.debug("通过 SessionID 获取登录用户信息：SessionID[" + paramGetUserVO.getSessionID() + "]模块[" + paramGetUserVO.getModuleID() + "]类型["
				+ paramGetUserVO.getLogonType() + "]");
		AUGetUserVO localAUGetUserVO = new AUGetUserVO();
		localAUGetUserVO.setLogonType(paramGetUserVO.getLogonType());
		localAUGetUserVO.setModuleID(paramGetUserVO.getModuleID());
		localAUGetUserVO.setSessionID(paramGetUserVO.getSessionID());
		AUGetUserResultVO localAUGetUserResultVO = ActiveUserManage.getInstance().getUserBySessionID(localAUGetUserVO);
		GetUserResultVO localGetUserResultVO = new GetUserResultVO();
		localGetUserResultVO.setMessage(localAUGetUserResultVO.getMessage());
		localGetUserResultVO.setRecode(localAUGetUserResultVO.getRecode());
		localGetUserResultVO.setResult(localAUGetUserResultVO.getResult());
		localGetUserResultVO.setUserManageVO(getUserManageVO(localAUGetUserResultVO.getUserManageVO()));
		this.logger.debug("通过 SessionID 获取登录用户信息：SessionID[" + paramGetUserVO.getSessionID() + "]模块[" + paramGetUserVO.getModuleID() + "]类型["
				+ paramGetUserVO.getLogonType() + "]结果[" + localGetUserResultVO.getResult() + "]返回码[" + localGetUserResultVO.getRecode() + "]返回信息["
				+ localGetUserResultVO.getMessage() + "]");
		return localGetUserResultVO;
	}

	public ISLogonResultVO isLogon(ISLogonVO paramISLogonVO) {
		this.logger.debug("判断用户是否已经登录：用户[" + paramISLogonVO.getUserID() + "]SessionID[" + paramISLogonVO.getSessionID() + "]模块["
				+ paramISLogonVO.getModuleID() + "]类型[" + paramISLogonVO.getLogonType() + "]");
		AUISLogonVO localAUISLogonVO = new AUISLogonVO();
		localAUISLogonVO.setLogonType(paramISLogonVO.getLogonType());
		localAUISLogonVO.setModuleID(paramISLogonVO.getModuleID());
		localAUISLogonVO.setSessionID(paramISLogonVO.getSessionID());
		localAUISLogonVO.setUserID(paramISLogonVO.getUserID());
		AUISLogonResultVO localAUISLogonResultVO = ActiveUserManage.getInstance().isLogon(localAUISLogonVO);
		ISLogonResultVO localISLogonResultVO = new ISLogonResultVO();
		localISLogonResultVO.setMessage(localAUISLogonResultVO.getMessage());
		localISLogonResultVO.setRecode(localAUISLogonResultVO.getRecode());
		localISLogonResultVO.setResult(localAUISLogonResultVO.getResult());
		localISLogonResultVO.setUserManageVO(getUserManageVO(localAUISLogonResultVO.getUserManageVO()));
		this.logger.debug("判断用户是否已经登录结果：用户[" + paramISLogonVO.getUserID() + "]SessionID[" + paramISLogonVO.getSessionID() + "]模块["
				+ paramISLogonVO.getModuleID() + "]类型[" + paramISLogonVO.getLogonType() + "]结果[" + localISLogonResultVO.getResult() + "]返回码["
				+ localISLogonResultVO.getRecode() + "]返回信息[" + localISLogonResultVO.getMessage() + "]");
		return localISLogonResultVO;
	}

	public CompulsoryLogoffResultVO compulsoryLogoff(CompulsoryLogoffVO paramCompulsoryLogoffVO) {
		this.logger.debug("强退用户：操作人[" + paramCompulsoryLogoffVO.getOperator() + "]操作 IP[" + paramCompulsoryLogoffVO.getLogonIP() + "]用户编号集合["
				+ paramCompulsoryLogoffVO.getUserIDList() + "]");
		AUCompulsoryLogoffVO localAUCompulsoryLogoffVO = new AUCompulsoryLogoffVO();
		localAUCompulsoryLogoffVO.setLogonIP(paramCompulsoryLogoffVO.getLogonIP());
		localAUCompulsoryLogoffVO.setOperator(paramCompulsoryLogoffVO.getOperator());
		localAUCompulsoryLogoffVO.setUserIDList(paramCompulsoryLogoffVO.getUserIDList());
		AUCompulsoryLogoffResultVO localAUCompulsoryLogoffResultVO = ActiveUserManage.getInstance().compulsoryLogoff(localAUCompulsoryLogoffVO);
		CompulsoryLogoffResultVO localCompulsoryLogoffResultVO = new CompulsoryLogoffResultVO();
		localCompulsoryLogoffResultVO.setMessage(localAUCompulsoryLogoffResultVO.getMessage());
		localCompulsoryLogoffResultVO.setRecode(localAUCompulsoryLogoffResultVO.getRecode());
		localCompulsoryLogoffResultVO.setResult(localAUCompulsoryLogoffResultVO.getResult());
		this.logger.debug("强退用户结果：操作人[" + paramCompulsoryLogoffVO.getOperator() + "]操作 IP[" + paramCompulsoryLogoffVO.getLogonIP() + "]用户编号集合["
				+ paramCompulsoryLogoffVO.getUserIDList() + "]结果[" + localCompulsoryLogoffResultVO.getResult() + "]返回码["
				+ localCompulsoryLogoffResultVO.getRecode() + "]返回信息[" + localCompulsoryLogoffResultVO.getMessage() + "]");
		return localCompulsoryLogoffResultVO;
	}

	public List<LogonOrLogoffUserVO> getLogonOrLogoffUserList() {
		List localList = ActiveUserManage.getInstance().getLogonOrLogoffUserList();
		ArrayList localArrayList = new ArrayList();
		if (localList != null) {
			Iterator localIterator = localList.iterator();
			while (localIterator.hasNext()) {
				AULogonOrLogoffUserVO localAULogonOrLogoffUserVO = (AULogonOrLogoffUserVO) localIterator.next();
				LogonOrLogoffUserVO localLogonOrLogoffUserVO = new LogonOrLogoffUserVO();
				localLogonOrLogoffUserVO.setLogonOrlogOff(localAULogonOrLogoffUserVO.getLogonOrlogOff());
				localLogonOrLogoffUserVO.setUserManageVO(getUserManageVO(localAULogonOrLogoffUserVO.getUserManageVO()));
				localArrayList.add(localLogonOrLogoffUserVO);
			}
		}
		return localArrayList;
	}

	public List<LogonOrLogoffUserVO> getAllLogonUserList() {
		List localList = ActiveUserManage.getInstance().getAllLogonUserList();
		ArrayList localArrayList = new ArrayList();
		if (localList != null) {
			Iterator localIterator = localList.iterator();
			while (localIterator.hasNext()) {
				AULogonOrLogoffUserVO localAULogonOrLogoffUserVO = (AULogonOrLogoffUserVO) localIterator.next();
				LogonOrLogoffUserVO localLogonOrLogoffUserVO = new LogonOrLogoffUserVO();
				localLogonOrLogoffUserVO.setLogonOrlogOff(localAULogonOrLogoffUserVO.getLogonOrlogOff());
				localLogonOrLogoffUserVO.setUserManageVO(getUserManageVO(localAULogonOrLogoffUserVO.getUserManageVO()));
				localArrayList.add(localLogonOrLogoffUserVO);
			}
		}
		return localArrayList;
	}

	public List<LogonOrLogoffUserVO> onlyGetAllLogonUserList() {
		List localList = ActiveUserManage.getInstance().onlyGetAllLogonUserList();
		ArrayList localArrayList = new ArrayList();
		if (localList != null) {
			Iterator localIterator = localList.iterator();
			while (localIterator.hasNext()) {
				AULogonOrLogoffUserVO localAULogonOrLogoffUserVO = (AULogonOrLogoffUserVO) localIterator.next();
				LogonOrLogoffUserVO localLogonOrLogoffUserVO = new LogonOrLogoffUserVO();
				localLogonOrLogoffUserVO.setLogonOrlogOff(localAULogonOrLogoffUserVO.getLogonOrlogOff());
				localLogonOrLogoffUserVO.setUserManageVO(getUserManageVO(localAULogonOrLogoffUserVO.getUserManageVO()));
				localArrayList.add(localLogonOrLogoffUserVO);
			}
		}
		return localArrayList;
	}

	public int isRestartStartRMI() {
		return ActiveUserManage.getInstance().isRestartStartRMI();
	}

	private UserManageVO getUserManageVO(AUUserManageVO paramAUUserManageVO) {
		UserManageVO localUserManageVO = null;
		if (paramAUUserManageVO != null) {
			localUserManageVO = new UserManageVO();
			localUserManageVO.setLastTime(paramAUUserManageVO.getLastTime());
			localUserManageVO.setLogonType(paramAUUserManageVO.getLogonType());
			localUserManageVO.setModuleIDList(paramAUUserManageVO.getModuleIDList());
			localUserManageVO.setSessionID(paramAUUserManageVO.getSessionID());
			localUserManageVO.setUserID(paramAUUserManageVO.getUserID());
			localUserManageVO.setLogonTime(paramAUUserManageVO.getLogonTime());
			localUserManageVO.setLogonIp(paramAUUserManageVO.getLogonIp());
			localUserManageVO.setLastLogonIp(paramAUUserManageVO.getLastLogonIp());
			localUserManageVO.setLastLogonTime(paramAUUserManageVO.getLastLogonTime());
		}
		return localUserManageVO;
	}
}