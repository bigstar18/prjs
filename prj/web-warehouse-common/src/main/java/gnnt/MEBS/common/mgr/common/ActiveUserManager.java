
package gnnt.MEBS.common.mgr.common;

import gnnt.MEBS.checkLogon.check.warehouse.WarehouseCheck;
import gnnt.MEBS.checkLogon.vo.warehouse.UserLogonResultVO;
import gnnt.MEBS.common.mgr.callbak.LogonCallbak;
import gnnt.MEBS.common.mgr.exception.CallbakErrorException;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.RightService;
import gnnt.MEBS.common.mgr.service.RoleService;
import gnnt.MEBS.common.mgr.service.UserService;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.logonServerUtil.au.LogonActualize;
import gnnt.MEBS.logonService.vo.CheckUserResultVO;
import gnnt.MEBS.logonService.vo.CheckUserVO;
import gnnt.MEBS.logonService.vo.GetUserResultVO;
import gnnt.MEBS.logonService.vo.GetUserVO;
import gnnt.MEBS.logonService.vo.ISLogonResultVO;
import gnnt.MEBS.logonService.vo.ISLogonVO;
import gnnt.MEBS.logonService.vo.LogoffVO;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <P>类说明：执行登录相关的公用方法
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2013-12-18上午09:43:14|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class ActiveUserManager {
	private static transient final Log logger = LogFactory.getLog(ActiveUserManager.class);

	/**
	 * 
	 * 验证传入的 sessionID 在 AU 中是否已经登录了
	 * <br/><br/>
	 * @param sessionID AU 生成的登录后的唯一标识
	 * @return ActiveUser
	 */
	public static GetUserResultVO getUser(long sessionID,String logonType, int selfModuleID){
		GetUserResultVO result = null;
		GetUserVO userVO = new GetUserVO();
		userVO.setLogonType(logonType);
		userVO.setModuleID(selfModuleID);
		userVO.setSessionID(sessionID);
		try{
			result = LogonActualize.getInstance().getUserBySessionID(userVO);
		}catch(Exception e){
			logger.error("验证SessionID在AU中是否已经登录异常",e);
		}
		return result;
	}

	/**
	 * 
	 * 用户登录
	 * <br/><br/>
	 * @param userID 登录用户名
	 * @param password 用户密码
	 * @param key key盘代码
	 * @param logonIP 登录 IP
	 * @param moduleID 登录模块
	 * @param logonType 登录类型(web 网页、pc 客户端、mobile 手机)
	 * @return UserLogonResultVO
	 * 
	 * @throws Exception 
	 */
	public static UserLogonResultVO logon(String userID,String password,String keyCode,String logonIP,int selfModuleID ,String logonType) throws Exception{
		
		return WarehouseCheck.getInstance().logon(userID, password, keyCode, logonIP,selfModuleID, logonType);
	}


	/**
	 * 
	 * 用户登录成功后进行的 Session 数据写入
	 * <br/><br/>
	 * @param userID 用户编号
	 * @param request 访问信息
	 * @param sessionID AU 生成的登录后的唯一标识
	 * @param logonType 登录类型
	 * @param selfModuleID 指定登录模块
	 * @return boolean true 登录成功 false 登录失败
	 */
	public static boolean logon(String userID,HttpServletRequest request,long sessionID,String logonType,int selfModuleID){
		boolean result = false;
		UserService userService = (UserService) ApplicationContextInit.getBean("com_userService");
		//获取登录用户信息
		User user = (User)userService.getUserByID(userID).clone();
		if(user == null){
			return result;
		}
		//记录 AU Session
		user.setSessionId(sessionID);
		//记录登录地址
		user.setIpAddress(request.getRemoteAddr());
		user.setLogonType(logonType);
		user.setModuleID(selfModuleID);
		//是否超级/高级管理员
		boolean isSuperAdminRole = false;
		if("DEFAULT_SUPER_ADMIN".equals(user.getType())//超级管理员
				|| "DEFAULT_ADMIN".equals(user.getType())){//高级管理员
			isSuperAdminRole = true;
			RoleService roleService = (RoleService) ApplicationContextInit.getBean("com_roleService");
			RightService rightService = (RightService) ApplicationContextInit.getBean("com_rightService");
			user.setRoleSet(roleService.getAllRole());
			user.setRightSet(rightService.getAllRight());
		}
		//重置 Session
		request.getSession().invalidate();
		//Session 中记录是否为超级/高级管理员
		request.getSession().setAttribute(Global.ISSUPERADMIN, isSuperAdminRole);
		//将用户信息记录到 Session 中
		request.getSession().setAttribute(Global.CURRENTUSERSTR, user);
		//记录 Au 中的 SessionID 到 web Session 中
		request.getSession().setAttribute(Global.SESSIONID, sessionID);
		//将登录类型记录到 SESSION 中
		request.getSession().setAttribute(Global.SELFLOGONTYPE, logonType);
		//将登录IP记录动session中
		request.getSession().setAttribute("LOGONIP", user.getIpAddress());

		//设置本地 Session 超时时间
		String sessionExpireTime = ApplicationContextInit.getConfig("SessionExpireTime");
		request.getSession().setMaxInactiveInterval(Tools.strToInt(sessionExpireTime,120)*60);

		//执行各个系统登录成功后的回调函数 
		try{
			LogonCallbak logonCallbak = null;
			try{
				logonCallbak = (LogonCallbak) ApplicationContextInit.getBean("logonCallbak");
			}catch(Exception e){
				System.out.println("系统中没有配置登录回调函数。");
			}
			if(logonCallbak != null){
				logonCallbak.logonSuccessCallbak(user, request);
			}
		}catch(CallbakErrorException e){
			request.getSession().invalidate();
			result = false;
			logger.error("Action logon 方法进行登录后，执行登录回调，抛出自定义异常：",e);
		}catch(Exception e){
			request.getSession().invalidate();
			result = false;
			logger.error("Action logon 方法进行登录后，执行登录回调，异常：",e);
		}


		result = true;

		return result;
	}


	/**
	 * 
	 * 修改自己密码
	 * <br/><br/>
	 * @param userID 登录用户
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @param logonIP 本次登录 IP 地址
	 * @return int 1 成功、-1 原密码错误、-2 管理员不存在
	 */
	public static int changePassword(String userID,String oldPassword,String newPassword,String logonIP){
		return WarehouseCheck.getInstance().changePassword(
				userID, oldPassword, newPassword,logonIP);
	}

	/**
	 * 
	 * 用户退出登录
	 * <br/><br/>
	 * @param sessionID
	 * @throws Exception 
	 */
	public static void logoff(HttpServletRequest request) throws Exception{
		// 从session中获取用户信息
		User user = (User) request.getSession().getAttribute(Global.CURRENTUSERSTR);
		//清空 Session
		request.getSession().invalidate();
		//如果用户不为空，则AU退出
		if (user != null) {
			LogoffVO vo = new LogoffVO();
			vo.setUserID(user.getUserId());
			vo.setSessionID(user.getSessionId());
			vo.setLogonType(user.getLogonType());//登录类型
			vo.setModuleID(user.getModuleID());
			LogonActualize.getInstance().logoff(vo);
			
		}
	}

	/**
	 * 
	 * 判断用户是否可以在模块登录
	 * <br/><br/>
	 * @param userID 用户编号
	 * @param sessionID AU生成的 SessionID
	 * @param selfAuConfigID
	 * @param moduleID 本模块编号
	 * @param logonType 登录类型(web 网页、pc 客户端、mobile 手机)
	 * @return boolean true 可以在模块登录 false 不可以在模块登录
	 * @throws Exception 
	 */
	public static ISLogonResultVO isLogon(String userID,long sessionID,int moduleID,String logonType) throws Exception{
		ISLogonVO isLogonVO = new ISLogonVO();
		isLogonVO.setUserID(userID);
		isLogonVO.setModuleID(moduleID);
		isLogonVO.setSessionID(sessionID);
		isLogonVO.setLogonType(logonType);
		return LogonActualize.getInstance().isLogon(isLogonVO);
	}

	/**
	 * 
	 * 用户跳转
	 * <br/><br/>
	 * @param checkUserVO 登录 RMI 信息
	 * @param fromModuleID 来源模块编号
	 * @param fromLogonType 来源登录类型 (web web服务登录、pc 电脑客户端登录、mobile 手机客户端登录)
	 * @return
	 */
	public static CheckUserResultVO checkUser(CheckUserVO checkUserVO,int fromModuleID,String fromLogonType){
		
		return LogonActualize.getInstance().checkUser(checkUserVO, fromModuleID, fromLogonType);
	}
}

