
package gnnt.MEBS.checkLogon.check.mgr;

import gnnt.MEBS.checkLogon.check.BaseCheck;
import gnnt.MEBS.checkLogon.dao.mgr.MgrCheckDAO;
import gnnt.MEBS.checkLogon.po.mgr.User;
import gnnt.MEBS.checkLogon.util.MD5;
import gnnt.MEBS.checkLogon.vo.mgr.UserLogonResultVO;
import gnnt.MEBS.logonServerUtil.au.LogonActualize;
import gnnt.MEBS.logonService.vo.LogonResultVO;
import gnnt.MEBS.logonService.vo.LogonVO;

import javax.sql.DataSource;

/**
 * <P>类说明：后台登录验证主类
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-28下午08:27:24|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class MgrCheck extends BaseCheck{

	/** 本身实例 */
	private volatile static MgrCheck instance;

	/** 32 模块号  3203 登录 退出 */
	private int LOG_MANAGE_OPERATORTYPR = 3203;

	/** 32 模块号 3204 修改密码 */
	private int PWD_MANAGE_OPERATORTYPR = 3204;

	/** 验证用到的 DAO */
	private MgrCheckDAO mgrCheckDAO;

	/**
	 * 
	 * 构造方法
	 * <br/><br/>
	 * @param dataSource 数据源
	 */
	public MgrCheck(DataSource dataSource){
		mgrCheckDAO = new MgrCheckDAO();
		mgrCheckDAO.setDataSource(dataSource);
	}

	/**
	 * 
	 * 创建自身实例对象
	 * <br/><br/>
	 * @param dataSource 数据源
	 * @return MgrCheck
	 */
	public static MgrCheck createInstance(DataSource dataSource){
		if(instance == null){
			synchronized(MgrCheck.class){
				if(instance == null){
					instance = new MgrCheck(dataSource);
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
	public static MgrCheck getInstance(){
		return instance;
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
	 */
	public UserLogonResultVO logon(String userID,String password,String key,String logonIP,int moduleID,String logonType){
		logger.info("用户["+userID+"]从 IP 地址["+logonIP+"]登录");
		UserLogonResultVO result = new UserLogonResultVO();
		result.setResult(-1);

		//用户信息
		User user = mgrCheckDAO.getUserByID(userID);

		//验证用户存在性
		if(user == null){
			result.setRecode("-1");
			result.setMessage("管理员不存在");
			logger.info("管理员["+userID+"]在["+logonIP+"]登录，返回信息："+result.getMessage());
			return result;
		}

		//验证用户 key 盘
		if(user.getKeyCode() != null && !"0123456789ABCDE".equals(user.getKeyCode()) 
				&& !user.getKeyCode().equals(key)){
			result.setRecode("-3");
			result.setMessage("key 盘数据不正确");
			mgrCheckDAO.addGlobalLog(userID, logonIP, LOG_MANAGE_OPERATORTYPR, "管理员登录，"+result.getMessage(), 0);
			logger.info("管理员["+userID+"]在["+logonIP+"]登录，返回信息："+result.getMessage());
			return result;
		}

		//验证用户密码
		String inPassword = MD5.getMD5(userID, password);

		if(!user.getPassword().equals(inPassword)){
			result.setRecode("-2");
			result.setMessage("密码不正确");
			mgrCheckDAO.addGlobalLog(userID, logonIP, LOG_MANAGE_OPERATORTYPR, "管理员登录，"+result.getMessage(), 0);
			logger.info("管理员["+userID+"]在["+logonIP+"]登录，返回信息："+result.getMessage());
			return result;
		}

		//验证用户当前是否可用
		if(!"N".equalsIgnoreCase(user.getIsForbid())){
			result.setRecode("-4");
			result.setMessage("禁止登录");
			mgrCheckDAO.addGlobalLog(userID, logonIP, LOG_MANAGE_OPERATORTYPR, "管理员登录，"+result.getMessage(), 0);
			logger.info("管理员["+userID+"]在["+logonIP+"]登录，返回信息："+result.getMessage());
			return result;
		}

		LogonVO logonVO = new LogonVO();
		logonVO.setLogonType(logonType);
		logonVO.setModuleID(moduleID);
		logonVO.setUserID(userID);
		logonVO.setLogonIp(logonIP);

		//调用 AU 进行登录
		LogonResultVO rv = LogonActualize.getInstance().logon(logonVO);

		if(rv.getResult() != 1){
			result.setRecode(rv.getRecode());
			result.setMessage(rv.getMessage());
			mgrCheckDAO.addGlobalLog(userID, logonIP, LOG_MANAGE_OPERATORTYPR, "管理员登录，"+result.getMessage(), 0);
			logger.info("管理员["+userID+"]在["+logonIP+"]登录，返回信息："+result.getMessage());
			return result;
		}

		result.setResult(1);
		result.setSessionID(rv.getUserManageVO().getSessionID());

		mgrCheckDAO.addGlobalLog(userID, logonIP, LOG_MANAGE_OPERATORTYPR, "管理员登录成功", 1);
		logger.info("管理员["+userID+"]在["+logonIP+"]登录成功");

		return result;
	}

	/**
	 * 
	 * 管理员修改密码
	 * <br/><br/>
	 * @param userID 管理员代码
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @param logonIP 登录 IP 地址
	 * @return int 1 成功、-1 原密码错误、-2 管理员不存在
	 */
	public int changePassword(String userID,String oldPassword,String newPassword,String logonIP){
		logger.info("管理员["+userID+"]在["+logonIP+"]修改密码");

		//用户信息
		User user = mgrCheckDAO.getUserByID(userID);

		if(user == null){
			logger.info("管理员["+userID+"]在["+logonIP+"]修改密码，用户不存在");
			return -2;
		}

		String inPassword = MD5.getMD5(userID, oldPassword);
		if(!user.getPassword().equals(inPassword)){
			logger.info("管理员["+userID+"]在["+logonIP+"]修改密码，原密码错误");
			mgrCheckDAO.addGlobalLog(userID, logonIP, PWD_MANAGE_OPERATORTYPR, "修改密码，原密码错误", 0);
			return -1;
		}

		//修改管理员密码
		mgrCheckDAO.changePassword(userID, MD5.getMD5(userID, newPassword));

		logger.info("管理员["+userID+"]在["+logonIP+"]修改密码成功");
		//添加日志
		mgrCheckDAO.addGlobalLog(userID, logonIP, PWD_MANAGE_OPERATORTYPR, "修改密码成功", 1);

		return 1;
	}
}

