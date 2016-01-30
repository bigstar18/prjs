
package gnnt.MEBS.checkLogon.po.warehouse;

import gnnt.MEBS.checkLogon.po.Clone;

/**
 * <P>类说明：
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-29下午01:37:41|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class User extends Clone{

	/** 用户编号 */
	private String userID;

	/** 仓库编号 */
	private String warehouseID;

	/** 用户密码 */
	private String password;

	/**
	 * 是否被禁用</br>
	 * Y 禁用<br/>
	 * N 不仅用
	 */
	private String isforbid;

	/** key 盘码 */
	private String keyCode;

	/** 用户类型 */
	private String type;

	/**
	 * 
	 * 用户编号
	 * <br/><br/>
	 * @return
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * 
	 * 用户编号
	 * <br/><br/>
	 * @param userID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * 
	 * 仓库编号
	 * <br/><br/>
	 * @return
	 */
	public String getWarehouseID() {
		return warehouseID;
	}

	/**
	 * 
	 * 仓库编号
	 * <br/><br/>
	 * @param warehouseID
	 */
	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}

	/**
	 * 
	 * 用户密码
	 * <br/><br/>
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * 用户密码
	 * <br/><br/>
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * 是否被禁用</br>
	 * Y 禁用<br/>
	 * N 不仅用
	 * <br/><br/>
	 * @return
	 */
	public String getIsforbid() {
		return isforbid;
	}

	/**
	 * 
	 * 是否被禁用</br>
	 * Y 禁用<br/>
	 * N 不仅用
	 * <br/><br/>
	 * @param isforbid
	 */
	public void setIsforbid(String isforbid) {
		this.isforbid = isforbid;
	}

	/**
	 * 
	 * key 盘码
	 * <br/><br/>
	 * @return
	 */
	public String getKeyCode() {
		return keyCode;
	}

	/**
	 * 
	 * key 盘码
	 * <br/><br/>
	 * @param keyCode
	 */
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	/**
	 * 
	 * 用户类型
	 * <br/><br/>
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * 用户类型
	 * <br/><br/>
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

}

