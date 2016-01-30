
package gnnt.MEBS.checkLogon.check.warehouse;

import gnnt.MEBS.checkLogon.check.BaseCheck;
import gnnt.MEBS.checkLogon.dao.warehouse.WarehouseCheckDAO;
import gnnt.MEBS.checkLogon.po.warehouse.DictionaryPO;
import gnnt.MEBS.checkLogon.po.warehouse.User;
import gnnt.MEBS.checkLogon.util.MD5;
import gnnt.MEBS.checkLogon.util.Tool;
import gnnt.MEBS.checkLogon.vo.warehouse.UserLogonResultVO;
import gnnt.MEBS.logonServerUtil.au.LogonActualize;
import gnnt.MEBS.logonService.vo.LogonResultVO;
import gnnt.MEBS.logonService.vo.LogonVO;

import javax.sql.DataSource;

/**
 * <P>类说明：仓库系统登录验证主类
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-29下午01:19:30|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class WarehouseCheck extends BaseCheck{
	/** 本身实例 */
	private volatile static WarehouseCheck instance;

	/** 32 模块号  3203 登录 退出 */
	private int LOG_MANAGE_OPERATORTYPR = 3203;

	/** 32 模块号 3204 修改密码 */
	private int PWD_MANAGE_OPERATORTYPR = 3204;

	/** 验证用到的 DAO */
	private WarehouseCheckDAO warehouseCheckDAO;

	/** 错误登录次数 */
	private int maxErrorLogonTimes = -1;

	/**
	 * 
	 * 构造方法
	 * <br/><br/>
	 * @param dataSource 数据源
	 */
	public WarehouseCheck(DataSource dataSource){
		warehouseCheckDAO = new WarehouseCheckDAO();
		warehouseCheckDAO.setDataSource(dataSource);

		DictionaryPO maxErrorLogonTimesPO = warehouseCheckDAO.getDictionaryByKey("WarehouseMaxErrorLogonTimes");

		if(maxErrorLogonTimesPO == null){
			logger.info("字典表中没有配置最大错误登录次数，默认为不限错误登录次数");
		}else{
			maxErrorLogonTimes = Tool.strToInt(maxErrorLogonTimesPO.getValue(),-1);
		}
	}

	/**
	 * 
	 * 创建自身实例对象
	 * <br/><br/>
	 * @param dataSource 数据源
	 * @return WarehouseCheck
	 */
	public static WarehouseCheck createInstance(DataSource dataSource){
		if(instance == null){
			synchronized(WarehouseCheck.class){
				if(instance == null){
					instance = new WarehouseCheck(dataSource);
				}
			}
		}
		return instance;
	}

	/**
	 * 
	 * 获取自身实例
	 * <br/><br/>
	 * @return
	 */
	public static WarehouseCheck getInstance(){
		return instance;
	}

	/**
	 * 
	 * 用户登录
	 * <br/><br/>
	 * @param userID 用户名
	 * @param password 密码
	 * @param key key盘中的代码
	 * @param logonIP 用户登录 IP
	 * @param moduleID 登录模块编号
	 * @param logonType 登录类型 (web 网页登录、pc 客户端登录、mobile 手机登录)
	 * @return UserLogonResultVO
	 */
	public UserLogonResultVO logon(String userID,String password,String key,String logonIP,int moduleID,String logonType){
		logger.info("用户["+userID+"]登录，登录 IP 为："+logonIP);
		UserLogonResultVO result = new UserLogonResultVO();
		result.setResult(-1);

		//通过用户编号查询用户信息
		User user = warehouseCheckDAO.getUserByID(userID);

		//验证用户是否存在
		if(user == null){
			result.setRecode("-1");
			result.setMessage("用户代码["+userID+"]不存在");
			logger.debug("用户登录，返回信息："+result.getMessage());
			return result;
		}

		//验证 key 盘
		if(user.getKeyCode() != null && !"0123456789ABCDE".equals(user.getKeyCode())
				&& !user.getKeyCode().equals(key)){
			result.setRecode("-3");
			result.setMessage("key盘验证错误");
			warehouseCheckDAO.addGlobalLog(user.getUserID(), user.getWarehouseID(), LOG_MANAGE_OPERATORTYPR
					, logonIP, "用户登录："+result.getMessage(), 0, user.getType());
			logger.debug("用户登录，返回信息："+result.getMessage());
			return result;
		}

		//验证错误登录次数
		int errorLogonNum = warehouseCheckDAO.getErrorLoginErrorNum(userID);
		if(maxErrorLogonTimes > 0 && errorLogonNum > maxErrorLogonTimes){
			result.setRecode("-6");
			result.setMessage("密码输入错误次数过多");
			warehouseCheckDAO.addGlobalLog(user.getUserID(), user.getWarehouseID(), LOG_MANAGE_OPERATORTYPR
					, logonIP, "用户登录："+result.getMessage(), 0, user.getType());
			logger.debug("用户登录，返回信息："+result.getMessage());
			return result;
		}

		//验证用户密码
		String inPassword = MD5.getMD5(userID, password);
		if(!user.getPassword().equals(inPassword)){
			result.setRecode("-2");
			result.setMessage("密码不正确");
			warehouseCheckDAO.insertErrorLoginlog(userID, user.getWarehouseID(), logonIP);
			warehouseCheckDAO.addGlobalLog(user.getUserID(), user.getWarehouseID(), LOG_MANAGE_OPERATORTYPR
					, logonIP, "用户登录："+result.getMessage(), 0, user.getType());
			logger.debug("用户登录，返回信息："+result.getMessage());
			return result;
		}

		//验证管理员状态
		if(!"N".equalsIgnoreCase(user.getIsforbid())){
			result.setRecode("-4");
			result.setMessage("管理员被禁用多");
			warehouseCheckDAO.addGlobalLog(user.getUserID(), user.getWarehouseID(), LOG_MANAGE_OPERATORTYPR
					, logonIP, "用户登录："+result.getMessage(), 0, user.getType());
			logger.debug("用户登录，返回信息："+result.getMessage());
			return result;
		}

		LogonVO logonVO = new LogonVO();
		logonVO.setLogonType(logonType);
		logonVO.setModuleID(moduleID);
		logonVO.setUserID(userID);
		logonVO.setLogonIp(logonIP);

		//执行 AU 登录
		LogonResultVO rv = LogonActualize.getInstance().logon(logonVO);

		if(rv.getResult() != 1){
			result.setRecode(rv.getRecode());
			result.setMessage(rv.getMessage());
			warehouseCheckDAO.addGlobalLog(user.getUserID(), user.getWarehouseID(), LOG_MANAGE_OPERATORTYPR
					, logonIP, "用户登录："+result.getMessage(), 0, user.getType());
			logger.debug("用户登录，返回信息："+result.getMessage());
			return result;
		}

		//如果存在登录失败记录，则清除本用户和今天以前的所有错误登录记录
		if(errorLogonNum > 0){
			warehouseCheckDAO.clearErrorLogonLog(userID);
		}

		warehouseCheckDAO.addGlobalLog(user.getUserID(), user.getWarehouseID(), LOG_MANAGE_OPERATORTYPR
				, logonIP, "用户登录成功", 1, user.getType());
		logger.debug("用户登录成功");

		result.setResult(1);
		result.setSessionID(rv.getUserManageVO().getSessionID());

		return result;
	}

	/**
	 * 
	 * 修改用户密码
	 * <br/><br/>
	 * @param userID 用户编号
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @param logonIP 登录 IP 地址
	 * @return int 1 成功、-1 原密码错误、-2 用户不存在
	 */
	public int changePassword(String userID,String oldPassword,String newPassword,String logonIP){
		logger.info("用户["+userID+"]在["+logonIP+"]修改密码");

		//通过编号获取用户
		User user = warehouseCheckDAO.getUserByID(userID);

		if(user == null){
			logger.info("用户["+userID+"]在["+logonIP+"]修改密码，管理员不存在");
			return -2;
		}

		String inPassword = MD5.getMD5(userID, oldPassword);
		if(!user.getPassword().equals(inPassword)){
			warehouseCheckDAO.addGlobalLog(user.getUserID(), user.getWarehouseID(), PWD_MANAGE_OPERATORTYPR
					, logonIP, "修改密码，返回信息：原密码不正确", 0, user.getType());
			logger.info("修改密码失败：用户["+userID+"]在["+logonIP+"]输入原密码不正确");
			return -1;
		}

		//修改密码
		warehouseCheckDAO.updateUserPassword(userID,MD5.getMD5(userID, newPassword));

		warehouseCheckDAO.addGlobalLog(user.getUserID(), user.getWarehouseID(), PWD_MANAGE_OPERATORTYPR
				, logonIP, "修改密码成功", 1, user.getType());
		logger.info("用户["+userID+"]在["+logonIP+"]修改密码成功");

		return 1;
	}
}

