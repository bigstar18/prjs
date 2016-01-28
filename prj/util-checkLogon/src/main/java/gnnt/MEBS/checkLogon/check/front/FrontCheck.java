
package gnnt.MEBS.checkLogon.check.front;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import gnnt.MEBS.checkLogon.check.BaseCheck;
import gnnt.MEBS.checkLogon.dao.front.FrontCheckDAO;
import gnnt.MEBS.checkLogon.po.front.DictionaryPO;
import gnnt.MEBS.checkLogon.po.front.TraderPO;
import gnnt.MEBS.checkLogon.util.MD5;
import gnnt.MEBS.checkLogon.util.Tool;
import gnnt.MEBS.checkLogon.vo.front.TraderLogonInfo;
import gnnt.MEBS.logonServerUtil.au.LogonActualize;
import gnnt.MEBS.logonService.vo.LogonResultVO;
import gnnt.MEBS.logonService.vo.LogonVO;

/**
 * <P>
 * 类说明：
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li>创建类 |2014-4-24下午08:16:31|金网安泰</li>
 * 
 * </ul>
 * 
 * @author liuzx
 */
public class FrontCheck extends BaseCheck {

	/** 公用系统编号 */
	private int COMMONMODULEID = 99;

	/** 32 模块号 3201 登录 退出 */
	private int LOG_MANAGE_OPERATORTYPR = 3201;

	/** 32 模块号 3202 修改密码 */
	private int PWD_MANAGE_OPERATORTYPR = 3202;

	/** 自身实例 */
	private static volatile FrontCheck instance;

	/** 验证用到的 DAO */
	private FrontCheckDAO frontCheckDAO;

	/** 错误登录次数 */
	private int maxErrorLogonTimes = -1;

	/**
	 * 
	 * 构造方法
	 * <br/>
	 * <br/>
	 */
	private FrontCheck(DataSource dataSource) {
		frontCheckDAO = new FrontCheckDAO();
		frontCheckDAO.setDataSource(dataSource);

		DictionaryPO maxErrorLogonTimesPO = frontCheckDAO.getDictionaryByKey("FrontMaxErrorLogonTimes");
		if (maxErrorLogonTimesPO == null) {
			logger.info("字典表中没有配置最大错误登录次数，默认为不限错误登录次数");
		} else {
			maxErrorLogonTimes = Tool.strToInt(maxErrorLogonTimesPO.getValue(), -1);
		}

	}

	/**
	 * 
	 * 创建自身实例对象
	 * <br/>
	 * <br/>
	 * 
	 * @param dataSource
	 * @return
	 */
	public static FrontCheck createInstance(DataSource dataSource) {
		if (instance == null) {
			synchronized (FrontCheck.class) {
				if (instance == null) {
					instance = new FrontCheck(dataSource);
				}
			}
		}
		return instance;
	}

	/**
	 * 
	 * 获取自身实例
	 * <br/>
	 * <br/>
	 * 
	 * @return
	 */
	public static FrontCheck getInstance() {
		return instance;
	}

	/**
	 * 
	 * 用户登录
	 * <br/>
	 * <br/>
	 * 
	 * @param userID
	 *            用户名
	 * @param password
	 *            密码
	 * @param moduleID
	 *            模块
	 * @param key
	 *            key盘密钥
	 * @param trustKey
	 *            信任密钥
	 * @param logonIP
	 *            登录 IP
	 * @param userIDType
	 *            登录类型 (0 交易员代码登录，1 用户名登录)
	 * @param logonType
	 *            登录类型 (web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 */
	public TraderLogonInfo logon(String userID, String password, int moduleID, String key, String trustKey, String logonIP, int userIDType,
			String logonType) {
		TraderLogonInfo result = new TraderLogonInfo();
		result.setResult(-1);

		try {
			// 交易员代码
			String traderID = userID;

			// 如果是用户名登录，则通过用户名去查找交易员代码
			if (userIDType == 1) {
				traderID = frontCheckDAO.getTraderIDByUserID(userID);
			}

			// 通过交易员编号获取交易员信息
			TraderPO trader = frontCheckDAO.getTraderByID(traderID);

			// 验证用户是否存在
			if (trader == null) {
				result.setRecode("-1");
				result.setMessage("交易员不存在");
				logger.info("用户[" + userID + "]交易员代码[" + traderID + "]登录失败，返回信息：" + result.getMessage());
				return result;
			}

			// 验证用户 key 盘
			if ("Y".equalsIgnoreCase(trader.getEnableKey()) && !trader.getKeyCode().equals(key)) {
				result.setRecode("-4");
				result.setMessage("key 盘数据不正确");
				frontCheckDAO.addGlobalLog(traderID, logonIP, LOG_MANAGE_OPERATORTYPR, "登录失败：" + result.getMessage(), 0);
				logger.info("用户[" + userID + "]交易员代码[" + traderID + "]登录失败，返回信息：" + result.getMessage());
				return result;
			}

			// 判断交易员错误登录次数
			int errorLogonNum = frontCheckDAO.getErrorLoginErrorNum(traderID);

			/*
			 * 如果数据库中尚未设置信任 key 或者传入的信任 key 不为数据库中的信任 key，
			 * 则需要验证用户密码输入错误次数。
			 * 即：如果客户端输入的信任 key 正确，则登录不受当日错误次数的限制
			 */
			if (trader.getTrustKey() == null || trader.getTrustKey().trim().length() <= 0 || !trader.getTrustKey().equals(trustKey)) {
				// 如果超出了最大错误次数
				if (maxErrorLogonTimes > 0 && errorLogonNum > maxErrorLogonTimes) {
					result.setRecode("-3");
					result.setMessage("密码输入错误次数过多");
					frontCheckDAO.addGlobalLog(traderID, logonIP, LOG_MANAGE_OPERATORTYPR, "登录失败：" + result.getMessage(), 0);
					logger.info("用户[" + userID + "]交易员代码[" + traderID + "]登录失败，返回信息：" + result.getMessage());
					return result;
				}
			}

			String inputPwd = MD5.getMD5(traderID, password);
			if (!inputPwd.equals(trader.getPassword())) {
				result.setRecode("-2");
				result.setMessage("密码不正确");
				frontCheckDAO.insertErrorLoginlog(traderID, moduleID, logonIP);
				frontCheckDAO.addGlobalLog(traderID, logonIP, LOG_MANAGE_OPERATORTYPR, "登录失败：" + result.getMessage(), 0);
				logger.info("用户[" + userID + "]交易员代码[" + traderID + "]登录失败，返回信息：" + result.getMessage());
				return result;
			}

			// 验证用户是否可用
			if (!"N".equalsIgnoreCase(trader.getStatus())) {
				result.setRecode("-2");
				result.setMessage("交易员当前不可用");
				frontCheckDAO.addGlobalLog(traderID, logonIP, LOG_MANAGE_OPERATORTYPR, "登录失败：" + result.getMessage(), 0);
				logger.info("用户[" + userID + "]交易员代码[" + traderID + "]登录失败，返回信息：" + result.getMessage());
				return result;
			}

			// 验证交易商状态
			String firmStatus = frontCheckDAO.getFirmStatus(trader.getFirmID());
			if (!"N".equalsIgnoreCase(firmStatus)) {
				result.setRecode("-3");
				result.setMessage("交易商当前不可用");
				frontCheckDAO.addGlobalLog(traderID, logonIP, LOG_MANAGE_OPERATORTYPR, "登录失败：" + result.getMessage(), 0);
				logger.info("用户[" + userID + "]交易员代码[" + traderID + "]登录失败，返回信息：" + result.getMessage());
				return result;
			}

			if (moduleID != COMMONMODULEID) {// 如果非公用系统，则检查用户是否有本系统权限
				String traderModule = frontCheckDAO.getTraderModuleEnable(traderID, moduleID);
				if (!"Y".equalsIgnoreCase(traderModule)) {
					result.setRecode("-5");
					result.setMessage("交易员没有模块" + moduleID + "权限");
					frontCheckDAO.addGlobalLog(traderID, logonIP, LOG_MANAGE_OPERATORTYPR, "登录失败：" + result.getMessage(), 0);
					logger.info("用户[" + userID + "]交易员代码[" + traderID + "]登录失败，返回信息：" + result.getMessage());
					return result;
				}
			}

			LogonVO logonVO = new LogonVO();
			logonVO.setLogonType(logonType);
			logonVO.setModuleID(moduleID);
			logonVO.setUserID(traderID);
			logonVO.setLogonIp(logonIP);

			// 执行登录
			LogonResultVO rv = LogonActualize.getInstance().logon(logonVO);
			if (rv.getResult() != 1) {
				result.setRecode(rv.getRecode());
				result.setMessage(rv.getMessage());
				frontCheckDAO.addGlobalLog(traderID, logonIP, LOG_MANAGE_OPERATORTYPR, "登录失败：" + result.getMessage(), 0);
				logger.info("用户[" + userID + "]交易员代码[" + traderID + "]登录失败，返回信息：" + result.getMessage());
				return result;
			}

			result.setFirmId(trader.getFirmID());
			result.setFirmName(trader.getFirmName());
			result.setForceChangePwd(trader.getForceChangePwd());
			result.setLastIP(trader.getLastIP());
			result.setLastTime(trader.getLastTime());
			result.setMessage(null);
			result.setResult(1);
			result.setSessionID(rv.getUserManageVO().getSessionID());
			result.setTraderId(trader.getTraderID());
			result.setTraderName(trader.getName());
			result.setType(trader.getType());

			SimpleDateFormat spdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
			String date = spdf.format(new Date());
			String randomKey = date + traderID + String.valueOf((Math.random() * 100000.0D));
			result.setTrustKey(randomKey);

			// 修改交易员最后登录信息
			frontCheckDAO.updateTraderLogonInfo(randomKey, logonIP, traderID);

			if (errorLogonNum > 0) {// 如果错误登录次数大于 0 ，则清空错误次数。注：这里也会清空今天以前的所有人的错误信息
				frontCheckDAO.clearErrorLogonLog(traderID);
			}

			frontCheckDAO.addGlobalLog(traderID, logonIP, LOG_MANAGE_OPERATORTYPR, "登录成功", 1);

		} catch (Exception e) {
			result.setRecode("-1");
			result.setMessage("登录失败，请联系管理员");
			logger.error("用户[" + userID + "]登录异常：", e);
		}

		return result;
	}

	/**
	 * 
	 * 验证用户是否有模块权限
	 * <br/>
	 * <br/>
	 * 
	 * @param traderID
	 *            交易员代码
	 * @param moduleID
	 *            模块编号
	 * @return boolean true 有权限 false 没有权限
	 */
	public boolean checkModuleRight(String traderID, int moduleID) {
		TraderLogonInfo result = new TraderLogonInfo();
		result.setResult(-1);
		if (moduleID != COMMONMODULEID) {// 如果非公用系统，则检查用户是否有本系统权限
			String traderModule = frontCheckDAO.getTraderModuleEnable(traderID, moduleID);
			if (!"Y".equalsIgnoreCase(traderModule)) {
				result.setRecode("-5");
				result.setMessage("交易员没有模块" + moduleID + "权限");
				logger.info("交易员[" + traderID + "]没有模块" + moduleID + "权限");
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * 修改密码
	 * <br/>
	 * <br/>
	 * 
	 * @param traderID
	 *            交易员代码
	 * @param oldPassword
	 *            原密码
	 * @param newPassword
	 *            新密码
	 * @param logonIP
	 *            登录 IP
	 * @return int 1 成功；1- 原密码不正确；-2 交易员代码错误
	 */
	public int changePassowrd(String traderID, String oldPassword, String newPassword, String logonIP) {
		logger.info("交易员 " + traderID + " 修改密码，登录 IP 为 " + logonIP);

		TraderPO trader = frontCheckDAO.getTraderByID(traderID);

		if (trader == null) {
			logger.info("交易员" + traderID + "修改密码，交易员代码错误");
			return -2;
		}

		if (!trader.getPassword().equals(MD5.getMD5(traderID, oldPassword))) {
			logger.info("交易员" + traderID + "修改密码，原密码不正确");
			return -1;
		}

		// 修改交易员密码
		frontCheckDAO.updateTraderPassword(traderID, MD5.getMD5(traderID, newPassword));

		// 写全局日志
		frontCheckDAO.addGlobalLog(traderID, logonIP, PWD_MANAGE_OPERATORTYPR, "交易员修改密码成功", 1);

		return 1;
	}
}
