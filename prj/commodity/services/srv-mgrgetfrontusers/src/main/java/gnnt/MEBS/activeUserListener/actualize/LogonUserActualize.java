package gnnt.MEBS.activeUserListener.actualize;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.activeUserListener.dao.SelfLogonManagerDAO;
import gnnt.MEBS.activeUserListener.thread.GetAllLogonUserThread;
import gnnt.MEBS.activeUserListener.thread.GetLogonOrLogoffUserThread;
import gnnt.MEBS.logonService.dao.LogonManagerDAO;
import gnnt.MEBS.logonService.po.LogonConfigPO;
import gnnt.MEBS.logonService.util.GnntBeanFactory;
import gnnt.MEBS.logonService.util.Tool;
import gnnt.MEBS.logonService.vo.CompulsoryLogoffVO;
import gnnt.MEBS.logonService.vo.RemoteLogonServerVO;
import gnnt.MEBS.logonService.vo.UserManageVO;

public class LogonUserActualize {
	private final transient Log logger = LogFactory.getLog(getClass());
	private static transient Map<String, LogonUserActualize> instatceMap = new HashMap();
	private Map<Integer, Map<String, Map<String, List<UserManageVO>>>> logonUserMap = new HashMap();
	private LogonManagerDAO logonManagerDAO;
	private SelfLogonManagerDAO selfLogonManagerDAO;
	private Map<Integer, RemoteLogonServerVO> logonManagerMap = new HashMap();
	private int clearRMITimes;
	private GetAllLogonUserThread getAllLogonUserThread;
	private GetLogonOrLogoffUserThread getLogonOrLogoffUserThread;
	private String sysname;

	private LogonUserActualize(DataSource paramDataSource, String paramString, int paramInt) {
		this.sysname = paramString;
		this.clearRMITimes = paramInt;
		this.logonManagerDAO = ((LogonManagerDAO) GnntBeanFactory.getBean("logonManagerDAO"));
		this.selfLogonManagerDAO = ((SelfLogonManagerDAO) GnntBeanFactory.getBean("selfLogonManagerDAO"));
		this.logger.info("获取数据库中配置的[" + paramString + "] AU 配置信息集合");
		List localList = this.logonManagerDAO.getLogonConfigList(paramString);
		if ((localList == null) || (localList.size() <= 0)) {
			throw new IllegalArgumentException("查询系统[" + paramString + "] AU 配置信息失败");
		}
		Iterator localIterator = localList.iterator();
		while (localIterator.hasNext()) {
			LogonConfigPO localLogonConfigPO = (LogonConfigPO) localIterator.next();
			RemoteLogonServerVO localRemoteLogonServerVO = new RemoteLogonServerVO();
			localRemoteLogonServerVO.setLogonConfigPO(localLogonConfigPO);
			this.logger.info("记录连接[" + localLogonConfigPO.getConfigID() + "]AU 连接：rim:\\" + localLogonConfigPO.getHostIP() + ":"
					+ localLogonConfigPO.getPort() + "/" + localLogonConfigPO.getServiceName() + "LogonService");
			this.logonManagerMap.put(localLogonConfigPO.getConfigID(), localRemoteLogonServerVO);
		}
	}

	private void startThread() {
		this.getAllLogonUserThread = new GetAllLogonUserThread(this.sysname);
		this.getAllLogonUserThread.start();
		this.getLogonOrLogoffUserThread = new GetLogonOrLogoffUserThread(this.sysname);
		this.getLogonOrLogoffUserThread.start();
	}

	public static LogonUserActualize createInstance(DataSource paramDataSource, String paramString, int paramInt) {
		if (instatceMap.get(paramString) == null) {
			synchronized (LogonUserActualize.class) {
				if (instatceMap.get(paramString) == null) {
					LogonUserActualize localLogonUserActualize = new LogonUserActualize(paramDataSource, paramString, paramInt);
					instatceMap.put(paramString, localLogonUserActualize);
					localLogonUserActualize.startThread();
				}
			}
		}
		return (LogonUserActualize) instatceMap.get(paramString);
	}

	public static LogonUserActualize getInstance(String paramString) {
		return (LogonUserActualize) instatceMap.get(paramString);
	}

	public Map<Integer, Map<String, Map<String, List<UserManageVO>>>> getLogonUserMap() {
		return this.logonUserMap;
	}

	public int getClearRMITimes() {
		return this.clearRMITimes;
	}

	public Map<Integer, RemoteLogonServerVO> getLogonManagerMap() {
		return this.logonManagerMap;
	}

	public LogonManagerDAO getLogonManagerDAO() {
		return this.logonManagerDAO;
	}

	public SelfLogonManagerDAO getSelfLogonManagerDAO() {
		return this.selfLogonManagerDAO;
	}

	public List<String> getOnLineUserList(Integer paramInteger, String paramString1, String paramString2) {
		ArrayList localArrayList = new ArrayList();
		HashMap localHashMap = new HashMap();
		Object localObject1;
		synchronized (this.logonUserMap) {
			localObject1 = this.logonUserMap.keySet().iterator();
			while (((Iterator) localObject1).hasNext()) {
				Integer localInteger = (Integer) ((Iterator) localObject1).next();
				if ((paramInteger == null) || (paramInteger.intValue() == localInteger.intValue())) {
					Map localMap1 = (Map) this.logonUserMap.get(localInteger);
					Iterator localIterator1 = localMap1.keySet().iterator();
					while (localIterator1.hasNext()) {
						String str1 = (String) localIterator1.next();
						if ((paramString1 == null) || (paramString1.trim().length() <= 0) || (paramString1.equals(str1))) {
							Map localMap2 = (Map) localMap1.get(str1);
							Iterator localIterator2 = localMap2.keySet().iterator();
							while (localIterator2.hasNext()) {
								String str2 = (String) localIterator2.next();
								if ((paramString2 == null) || (paramString2.trim().length() <= 0) || (paramString2.equals(str2))) {
									List localList = (List) localMap2.get(str2);
									UserManageVO localUserManageVO = (UserManageVO) localList.get(0);
									localHashMap.put(localUserManageVO.getUserID(), localUserManageVO);
								}
							}
						}
					}
				}
			}
		}
		Iterator it = localHashMap.values().iterator();
		while (((Iterator) it).hasNext()) {
			localObject1 = (UserManageVO) ((Iterator) it).next();
			localArrayList.add(((UserManageVO) localObject1).getUserID() + "," + ((UserManageVO) localObject1).getLogonIp() + ","
					+ Tool.fmtTime(((UserManageVO) localObject1).getLogonTime()));
		}
		System.out.println("result: " + localArrayList);
		return localArrayList;
	}

	public int compulsoryLogoff(CompulsoryLogoffVO paramCompulsoryLogoffVO) {
		int i = 1;
		Object localObject1;
		Object localObject2;
		Object localObject3;
		synchronized (this.logonUserMap) {
			localObject1 = this.logonUserMap.keySet().iterator();
			while (((Iterator) localObject1).hasNext()) {
				localObject2 = (Integer) ((Iterator) localObject1).next();
				Map localMap = (Map) this.logonUserMap.get(localObject2);
				Iterator localIterator1 = localMap.keySet().iterator();
				while (localIterator1.hasNext()) {
					String str1 = (String) localIterator1.next();
					localObject3 = (Map) localMap.get(str1);
					Iterator localIterator2 = paramCompulsoryLogoffVO.getUserIDList().iterator();
					while (localIterator2.hasNext()) {
						String str2 = (String) localIterator2.next();
						((Map) localObject3).remove(str2);
					}
				}
			}
		}
		Iterator it = this.logonManagerMap.keySet().iterator();
		while (((Iterator) it).hasNext()) {
			localObject1 = (Integer) ((Iterator) it).next();
			localObject2 = (RemoteLogonServerVO) this.logonManagerMap.get(localObject1);
			System.out.println(localObject1);
			try {
				((RemoteLogonServerVO) localObject2).getRmiService().compulsoryLogoff(paramCompulsoryLogoffVO);
			} catch (RemoteException localRemoteException1) {
				int j = ((RemoteLogonServerVO) localObject2).clearRMI();
				try {
					((RemoteLogonServerVO) localObject2).getRmiService().compulsoryLogoff(paramCompulsoryLogoffVO);
				} catch (RemoteException localRemoteException2) {
					this.logger.error(
							"连接 RMI 服务：rmi:\\" + ((RemoteLogonServerVO) localObject2).getLogonConfigPO().getHostIP() + ":"
									+ ((RemoteLogonServerVO) localObject2).getLogonConfigPO().getPort() + "/"
									+ ((RemoteLogonServerVO) localObject2).getLogonConfigPO().getServiceName() + "LogonService" + " 异常",
							localRemoteException1);
					if ((this.clearRMITimes > 0) && (j > this.clearRMITimes)) {
						localObject3 = this.logonManagerDAO
								.getLogonConfigByID(((RemoteLogonServerVO) localObject2).getLogonConfigPO().getConfigID().intValue());
						if (localObject3 != null) {
							((RemoteLogonServerVO) localObject2).setLogonConfigPO((LogonConfigPO) localObject3);
						}
					}
					if (i == 1) {
						i = -1;
					}
				} catch (Exception localException2) {
					this.logger.error("调用 RMI[" + localObject1 + "]强制退出用户异常", localRemoteException1);
					if (i != -2) {
						i = -2;
					}
				}
			} catch (Exception localException1) {
				this.logger.error("调用 RMI[" + localObject1 + "]强制退出用户异常", localException1);
				if (i != -2) {
					i = -2;
				}
			}
		}
		return i;
	}
}
