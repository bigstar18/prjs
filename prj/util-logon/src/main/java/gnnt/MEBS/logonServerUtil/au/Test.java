
package gnnt.MEBS.logonServerUtil.au;

import gnnt.MEBS.logonService.util.GnntBeanFactory;
import gnnt.MEBS.logonService.util.Tool;
import gnnt.MEBS.logonService.vo.ISLogonResultVO;
import gnnt.MEBS.logonService.vo.ISLogonVO;
import gnnt.MEBS.logonService.vo.LogoffResultVO;
import gnnt.MEBS.logonService.vo.LogoffVO;
import gnnt.MEBS.logonService.vo.LogonResultVO;
import gnnt.MEBS.logonService.vo.LogonVO;

import javax.sql.DataSource;

/**
 * <P>类说明：
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-22下午12:00:37|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class Test {
	private String userID = "liuzx";
	private long sessionID = 584264649135482396L;
	private String logonType = "pc";
	public static void main(String args[]) throws Exception{
		Test t = new Test();
		t.init();
		long start = System.currentTimeMillis();
		for(int i=0;i<10;i++){
			t.userID = "user"+i;
			t.logon();
			t.islogon();
			//t.logoff();
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		
	}

	public void init() throws Exception{
		int selfConfigID = Tool.strToInt(GnntBeanFactory.getConfig("selfConfigID"),-1000);
		int clearRMITimes = Tool.strToInt(GnntBeanFactory.getConfig("clearRMITimes"),-1);
		DataSource dataSource = (DataSource) GnntBeanFactory.getBean("dataSource");
		LogonActualize.createInstance(selfConfigID, dataSource, clearRMITimes,"Front");

//		Map<String,Long> auExpireTimeMap = null;
//		long timeSpace = Tool.strToLong(GnntBeanFactory.getConfig("timeSpace"),200);
//		LogonActualize.createInstance(selfConfigID,0,dataSource,auExpireTimeMap,timeSpace,clearRMITimes);
	}

	/**
	 * 测试登录
	 */
	public void logon(){
		LogonVO logonVO = new LogonVO();
		logonVO.setLogonType(logonType);
		logonVO.setModuleID(2);
		logonVO.setUserID(userID);
		LogonResultVO r = LogonActualize.getInstance().logon(logonVO);
		if(r.getResult() == 1){
			this.sessionID = r.getUserManageVO().getSessionID();
			System.out.println("用户登录结果：result["+r.getResult()+"]sessionID["+r.getUserManageVO().getSessionID()+"]"+r.getUserManageVO().getModuleIDList());
		}else{
			System.out.println("用户登录结果：result["+r.getResult()+"]recode["+r.getRecode()+"]message["+r.getMessage()+"]");
		}
	}

	/**
	 * 验证用户是否已经登录
	 */
	public void islogon(){
		ISLogonVO isLogonVO = new ISLogonVO();
		isLogonVO.setLogonType(logonType);
		isLogonVO.setModuleID(3);
		isLogonVO.setSessionID(sessionID);
		isLogonVO.setUserID(userID);
		ISLogonResultVO r = LogonActualize.getInstance().isLogon(isLogonVO);
		if(r.getResult() == 1){
			System.out.println("验证用户是否已经登录：result["+r.getResult()+"]sessionID["+r.getUserManageVO().getSessionID()+"]"+r.getUserManageVO().getModuleIDList());
		}else{
			System.out.println("验证用户是否已经登录：result["+r.getResult()+"]recode["+r.getRecode()+"]message["+r.getMessage()+"]");
		}
	}

	/**
	 * 
	 * 用户退出
	 * <br/><br/>
	 */
	public void logoff(){
		LogoffVO logoffVO = new LogoffVO();
		logoffVO.setLogonType(logonType);
		logoffVO.setSessionID(sessionID);
		logoffVO.setUserID(userID);

		LogoffResultVO r = LogonActualize.getInstance().logoff(logoffVO);
		System.out.println("验证用户是否已经登录：result["+r.getResult()+"]recode["+r.getRecode()+"]message["+r.getMessage()+"]");
	}
}

