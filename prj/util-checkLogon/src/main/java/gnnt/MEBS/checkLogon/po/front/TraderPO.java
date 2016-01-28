
package gnnt.MEBS.checkLogon.po.front;

import gnnt.MEBS.checkLogon.po.Clone;

import java.util.Date;

/**
 * <P>类说明：
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-24下午08:42:08|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class TraderPO extends Clone{

	/** 交易员ID */
	private String traderID;

	/** 交易员名称 */
	private String name;

	/** 密码 */
	private String password;

	/** 是否修改密码 */
	private Integer forceChangePwd;

	/**
	 * 状态<br/>
	 * N 正常 D 禁用
	 */
	private String status;

	/** 交易商ID */
	private String firmID;

	/** 交易商名称 */
	private String firmName;

	/**
	 * 交易员类型<br/>
	 * A 管理员 N 普通交易员
	 */
	private String type;

	/** Key码 */
	private String keyCode;

	/**
	 * 是否启用Key<br/>
	 * Y 使用 N 不使用
	 */
	private String enableKey;

	/** 创建时间 */
	private Date createTime;

	/** 修改时间 */
	private Date modifyTime;

	/** 信任key */
	private String trustKey;

	/** 上次登陆时间 */
	private String lastTime;

	/** 上次登陆ip */
	private String lastIP;

	/**
	 * 交易员代码
	 * 
	 * @return
	 */
	public String getTraderID() {
		return traderID;
	}

	/**
	 * 交易员代码
	 * 
	 * @param traderID
	 */
	public void setTraderID(String traderID) {
		this.traderID = traderID;
	}

	/**
	 * 交易员名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 交易员名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 密码
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 是否需要强制修改密码
	 * 
	 * @return
	 */
	public Integer getForceChangePwd() {
		return forceChangePwd;
	}

	/**
	 * 是否需要强制修改密码
	 * 
	 * @param forceChangePwd
	 */
	public void setForceChangePwd(Integer forceChangePwd) {
		this.forceChangePwd = forceChangePwd;
	}

	/**
	 * 交易商代码
	 * 
	 * @return
	 */
	public String getFirmID() {
		return firmID;
	}

	/**
	 * 交易商代码
	 * 
	 */
	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}
	
	
	/**
	 * @return 交易商名称
	 */
	public String getFirmName() {
		return firmName;
	}

	/**
	 * @param 交易商名称
	 */
	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	/**
	 * 交易员类型：A 管理员 N 普通交易员
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 交易员类型：A 管理员 N 普通交易员
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * key
	 * 
	 * @return
	 */
	public String getKeyCode() {
		return keyCode;
	}

	/**
	 * key
	 * 
	 * @param keyCode
	 */
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	/**
	 * 是否使用key Y 使用 N 不使用
	 * 
	 * @return
	 */
	public String getEnableKey() {
		return enableKey;
	}

	/**
	 * 是否使用key Y 使用 N 不使用
	 * 
	 * @param enableKey
	 */
	public void setEnableKey(String enableKey) {
		this.enableKey = enableKey;
	}

	/**
	 * 创建日期
	 * 
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 创建日期
	 * 
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 修改日期
	 * 
	 * @return
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 修改日期
	 * 
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 状态 N 正常 D 禁用
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 状态 N 正常 D 禁用
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return 信任key；如果客户端传入的key与服务端一致则不进行登录错误次数验证
	 */
	public String getTrustKey() {
		return trustKey;
	}

	/**
	 * @param trustKey
	 *            信任key；如果客户端传入的key与服务端一致则不进行登录错误次数验证
	 */
	public void setTrustKey(String trustKey) {
		this.trustKey = trustKey;
	}

	/**
	 * @return 上次登陆时间
	 */
	public String getLastTime() {
		return lastTime;
	}

	/**
	 * @param 上次登陆时间
	 */
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * @return 上次登陆IP
	 */
	public String getLastIP() {
		return lastIP;
	}

	/**
	 * @param 上次登陆IP
	 */
	public void setLastIP(String lastIP) {
		this.lastIP = lastIP;
	}
}

