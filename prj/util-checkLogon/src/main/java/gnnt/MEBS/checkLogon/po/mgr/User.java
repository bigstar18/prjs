
package gnnt.MEBS.checkLogon.po.mgr;

import gnnt.MEBS.checkLogon.po.Clone;

/**
 * <P>类说明：管理员用户表
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-29上午10:37:55|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class User extends Clone{
	/** 用户代码 */
	private String id;

	/** 用户名 */
	private String name;

	/** 用户密码 */
	private transient String password;

	/** 用户描述 */
	private String description;

	/** key盘中的代码 */
	private String keyCode;

	/**
	 * 用户类型<br/>
	 * DEFAULT_SUPER_ADMIN 默认超级管理员DEFAULT_ADMIN 高级管理员 ADMIN 普通管理员 <br/>
	 * 默认超级管理员不可删除
	 */
	private String type;

	/**
	 * 是否禁用状态<br/>
	 * N：可用 Y:禁用
	 */
	private String isForbid = "N";

	public String getUserID() {
		return id;
	}

	public void setUserID(String userID) {
		this.id = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsForbid() {
		return isForbid;
	}

	public void setIsForbid(String isForbid) {
		this.isForbid = isForbid;
	}

}

