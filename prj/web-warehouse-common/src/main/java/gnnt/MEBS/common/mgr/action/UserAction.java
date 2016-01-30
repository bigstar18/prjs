package gnnt.MEBS.common.mgr.action;

import gnnt.MEBS.checkLogon.vo.warehouse.UserLogonResultVO;
import gnnt.MEBS.common.mgr.common.ActiveUserManager;
import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.common.OperateLogConstants;
import gnnt.MEBS.common.mgr.common.ReturnValue;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.service.UserService;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.common.mgr.statictools.filetools.XMLWork;
import gnnt.MEBS.common.mgr.vo.LogonXML;
import gnnt.MEBS.logonService.vo.ISLogonResultVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 用户Action
 * 
 * @author xuejt
 * 
 */
@Controller("com_userAction")
@Scope("request")
public class UserAction extends EcsideAction {

	private static final long serialVersionUID = -5214089717619011935L;

	public UserAction() {
		super.setEntityName(User.class.getName());
	}

	/**
	 * 在构造中强制设置为角色类型，重写设置实体名让重写实体名方法不做任何事
	 */
	public void setEntityName(String entityName) {

	}

	/**
	 * 注入标准service实例
	 */
	@Autowired
	@Qualifier("com_userService")
	private UserService userService;

	/**
	 * 获取Action使用的service实例
	 * 
	 * @return service对象
	 */
	public StandardService getService() {
		return this.userService;
	}

	@Resource(name = "com_isForbidMap")
	Map<String, String> com_isForbidMap;

	/**
	 * 用户是否禁用Map 从spring中注入 <!-- 用户是否禁止页面展示 --> <br>
	 * <util:map id="com_isForbidMap" map-class="java.util.HashMap"
	 * key-type="java.lang.String" value-type="java.lang.String"><br>
	 * <entry key="Y" value="禁用" /><br>
	 * <entry key="N" value="不禁用" /><br>
	 * </util:map>
	 * 
	 * @return
	 */
	public Map<String, String> getCom_isForbidMap() {
		return com_isForbidMap;
	}

	/**
	 * 
	 * 通过 SessionID 和 UserID 登录
	 * <br/><br/>
	 * @return
	 */
	public String checkLogon(){
		long sessionID = 0;
		String userID = null;
		String logonType = null;
		int selfModuleID = 0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String re=br.readLine();
			StringBuffer strXML=new StringBuffer();
			while(re!=null ){
				strXML.append(re);
				re=br.readLine();
			}
			String loginxml = strXML.toString();
			//loginxml = "<?xml version=\"1.0\" encoding=\"gb2312\"?><GNNT><REQ name=\"check_user\"><USER_ID>xie</USER_ID><SESSION_ID>9137071206884378519</SESSION_ID><MODULE_ID>2</MODULE_ID></REQ></GNNT>";
			LogonXML x = (LogonXML)XMLWork.reader(LogonXML.class, loginxml); 
			if(x != null && x.req != null && "check_user".equals(x.req.name)){
				sessionID = Tools.strToLong(x.req.sessionID,sessionID);
				userID = x.req.userID;
				selfModuleID = Tools.strToInt(x.req.moduleID,Global.getSelfModuleID());
				logonType = x.req.logonType;
			}
			if("".equals(logonType)){
				logonType = Global.GetSelfLogonType();
			}
			request.setAttribute("LOGONTYPE", logonType);
		} catch (IOException e) {
			logger.error("通过 SessionID 登录系统异常",e);
		}

		int code = 0;
		String msg = "";
		ISLogonResultVO au;
		try {
			au = ActiveUserManager.isLogon(userID, sessionID, selfModuleID,logonType);
			if(au == null || au.getResult() == -1){
				logger.debug("通过 sessionID 到 AU 中未获取到信息");
				// 传入的 SessionID不存在
				addReturnValue(-1, 9930101);
				code = -1;
				msg = "传入的 SessionID不存在";
			}else if(!au.getUserManageVO().getUserID().equals(userID)){
				//重置 Session 信息
				request.getSession().invalidate();
				logger.debug("通过 sessionID 到 AU 中未获取到信息");
				// 用户信息与 SessionID信息不一致
				addReturnValue(-1, 9930103);
				code = -1;
				msg = "用户信息与 SessionID信息不一致";
			}else{//这里只进行判断，不再进行写入了
				boolean logonSuccess = ActiveUserManager.logon(au.getUserManageVO().getUserID(), request, au.getUserManageVO().getSessionID(),logonType,selfModuleID);
				if(!logonSuccess){
					logger.error("登录时，向 Session 中写入信息失败");
					// 通过 SessionID信息向管理端写入信息失败
					addReturnValue(-1, 9930102);
					code = -1;
					msg = "通过 SessionID信息向管理端写入信息失败";
				}
			}
		} catch (Exception ec) {
			ec.printStackTrace();
			logger.error("返回登录信息时异常:",ec);
		}
		

		String result = "<?xml version=\"1.0\" encoding=\"GBK\"?><GNNT><REP name=\"check_user\"><RESULT><RETCODE>"+code+"</RETCODE><MESSAGE>"+msg+"</MESSAGE><MODULE_ID>"+Global.getSelfModuleID()+"</MODULE_ID></RESULT></REP></GNNT>";
		try {
			response.setContentType("text/html;charset=GBK");
			this.response.getWriter().print(result);
			this.response.getWriter().close();
		} catch (IOException e) {
			logger.error("返回登录信息时异常",e);
		}

		return null;
	}
	
	/**
	 * 用户登录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String logon() throws Exception {
		User user = (User) entity;
		// 服务端session中存放的验证码
		String randNumSys = (String) request.getSession().getAttribute(
				"RANDOMICITYNUM");
		// 客户端传入的验证码
		String randNumInput = request.getParameter("randNumInput");
		if(randNumInput==null){
			//验证码为空，请重新登录
			this.addReturnValue(-1, 131201);
			return ERROR;
		}
		/**
		 * 比对验证码 if (randNumSys == null || (randNumSys != null &&
		 * !randNumSys.toUpperCase().equals( randNumInput.toUpperCase()))) { //
		 * 验证码错误,请重新登录! addReturnValue(-1, -1001); return ERROR; }
		 */
		// 比对验证码
		if (randNumSys == null
				|| (randNumSys != null && !randNumSys.toUpperCase().equals(
						randNumInput.toUpperCase()))) { // 验证码错误,请重新登录!
			entity=user;
			//验证码错误，请重新登录
			addReturnValue(-1, 131202);
			return ERROR;
		}
		String logonType = request.getParameter(Global.SELFLOGONTYPE);
		if(logonType == null || logonType.length()<=0){
			logonType = Global.GetSelfLogonType();
		}

		int selfModuleID = Tools.strToInt(request.getParameter(Global.SELFMODULEID),Global.getSelfModuleID());

		UserLogonResultVO vo = ActiveUserManager.logon(
				user.getUserId(), user.getPassword(), user.getKeyCode(),request.getRemoteAddr(), selfModuleID,logonType);

		long sessionId = vo.getSessionID();
		/**
		 * 登录结果：
		 * 1 成功、-1 失败 
		 */
		int result= vo.getResult();
		if(result== -1){
			/**
			 *  -1：管理代码不存在；-2：口令不正确；-3：Key盘验证错误； -4：禁止登录；-6 ： 用户密码输入错误次数过多，esle：其他异常
			 */
			String recode = vo.getRecode().trim();
			if("-1".equals(recode) || "-2".equals(recode)){
				// 用户名或密码错误，请重新登录
				addReturnValue(-1, 131203);
			}else if ("-3".equals(recode)) {
				// key盘身份验证失败，请确认
				addReturnValue(-1, 131204);
			}else if ("-4".equals(recode) || "-6".equals(recode)) {
				// 用户被禁用，请联系系统管理员
				addReturnValue(-1, 131205);
			}else {
				// 系统返回未知错误，错误码%s，请联系系统管理员
				addReturnValue(-1, 131206, new Object[]{sessionId});
			}
			return ERROR;
		}
		
		//用户登录成功后进行的 Session 数据写入 
		ActiveUserManager.logon(user.getUserId(), request, sessionId,logonType, selfModuleID);

		//管理员登录成功
		addReturnValue(1, 111201);
		return SUCCESS;

	}

	/**
	 * 修改自己的登陆密码登录密码
	 * 
	 * @return
	 */
	public String updatePasswordSelfSave() {
		logger.debug("enter passwordSelfSave");

		User loginUser = (User) request.getSession().getAttribute(
				Global.CURRENTUSERSTR);
		String oldPassword = request.getParameter("oldPassword");
		User user = (User) entity;
		if(user.getPassword().equals(oldPassword)){
			// 修改密码失败，新密码与原密码一致
			addReturnValue(-1, 131301);
			return SUCCESS;
		}
		int result = ActiveUserManager.changePassword(
				loginUser.getUserId(), oldPassword, user.getPassword(),
				request.getRemoteAddr());

		if (result != 0) {
			// 修改密码失败，旧密码错误
			addReturnValue(-1, 131302);
		}else{
			// 修改密码成功
			addReturnValue(1, 111301);
		}

		ReturnValue returnValue = (ReturnValue)request.getAttribute(Global.RETURNVALUE);
		if(returnValue != null){
			if(returnValue.getResult()==1){
				writeOperateLog(OperateLogConstants.COMMONLOGTYPE, "管理员"
						+ user.getUserId() + "修改密码", 1, "");
			}else{
				writeOperateLog(OperateLogConstants.COMMONLOGTYPE, "管理员"
						+ user.getUserId() + "修改密码", 0, returnValue.getInfo());
			}
		}
		
		return SUCCESS;
	}

	/**
	 * 用户退出系统
	 * 
	 * @return
	 * @throws Exception
	 */
	public String logout() throws Exception {
		// 从session中获取用户信息
		User user = (User) request.getSession().getAttribute(Global.CURRENTUSERSTR);

		if (user != null) {
			writeOperateLog(OperateLogConstants.COMMONLOGTYPE, "管理员"
					+ user.getUserId() + "退出", 1, "");
		}

		ActiveUserManager.logoff(request);
		return SUCCESS;
	}

	/**
	 * 修改风格
	 * 
	 * @return
	 */
	public String saveShinStyle() {
		logger.debug("enter modShinStyle");
		// 先从session中获取user对象 将皮肤属性改变；然后写入数据库；目的拷贝sessionID，Ip地址等
		User currentUser = (User) request.getSession().getAttribute(
				Global.CURRENTUSERSTR);
		User user = (User) entity;
		currentUser.setSkin(user.getSkin());
		getService().update(currentUser);
		//修改风格成功
		addReturnValue(1, 111303);
		writeOperateLog(OperateLogConstants.COMMONLOGTYPE, "修改管理员"
				+ user.getUserId() + "的风格", 1, "");
		return SUCCESS;
	}

}