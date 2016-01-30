package gnnt.MEBS.common.mgr.model;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 用户信息
 * 
 * @author xuejt
 * 
 */
public class User extends StandardModel {

	private static final long serialVersionUID = -1185871472800004202L;

	/**用户代码*/
	@ClassDiscription(name="用户代码",description="")
	private String userId;

	/**用户名称*/
	@ClassDiscription(name="用户名称",description="")
	private String name;

	/**用户密码*/
	@ClassDiscription(name="用户密码",description="")
	private transient String password;

	/** 仓库编号 */
	@ClassDiscription(name="仓库编号",description="")
	private String warehouseID;

	/**用户描述*/
	@ClassDiscription(name="用户描述",description="")
	private String description;

	/**获取用户使用的皮肤风格*/
	@ClassDiscription(name="获取用户使用的皮肤风格",description="")
	private String skin = "default";

	/**key盘中的代码*/
	@ClassDiscription(name="key盘中的代码",description="")
	private String keyCode;

	/**用户本身拥有的权限集合*/
	@ClassDiscription(name="用户本身拥有的权限集合",description="")
	private Set<Right> rightSet;

	/**
	 * 用户类型 <br/>
	 * DEFAULT_SUPER_ADMIN 默认超级管理员<br/>
	 * DEFAULT_ADMIN 高级管理员 <br/>
	 * ADMIN 普通管理员 <br/>
	 * 默认超级管理员不可删除
	 */
	@ClassDiscription(name="用户类型",description="用户类型 DEFAULT_SUPER_ADMIN：默认超级管理员 DEFAULT_ADMIN：级管理员 ADMIN: 普通管理员 , 默认超级管理员不可删除 ")
	private String type;

	/**用户对应的角色集合*/
	@ClassDiscription(name="用户对应的角色集合",description="")
	private Set<Role> roleSet;

	/**用户权限Map集合*/
	@ClassDiscription(name="用户权限Map集合",description="")
	private Map<Long, Right> rightMap;

	/**
	 * 是否禁用状态
	 *  N：可用 Y:禁用
	 */
	@ClassDiscription(name="是否禁用状态",description="是否禁用状态  N：可用 Y:禁用")
	private String isForbid = "N";

	/**用户登录成功后的Ip地址*/
	@ClassDiscription(name="用户登录成功后的Ip地址",description="")
	private String ipAddress;

	/**用户sessionID 登录成功后由AU返回可以唯一标示用户身份*/
	@ClassDiscription(name="用户sessionID",description="登录成功后由AU返回可以唯一标示用户身份")
	private long sessionId;

	/** 登录类型 */
	@ClassDiscription(name="登录类型",description="")
	private String logonType;

	/** 登录模块 */
	@ClassDiscription(name="登录模块",description="")
	private int moduleID;

	/**
	 * 获取用户代码
	 * 
	 * @return 用户代码
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置用户代码
	 * 
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取用户密码
	 * 
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置用户密码
	 * 
	 * @param password
	 *            密码
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * 用户类型 DEFAULT_SUPER_ADMIN 默认超级管理员DEFAULT_ADMIN 高级管理员 ADMIN 普通管理员 <br>
	 * 默认超级管理员不可删除
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 用户类型 DEFAULT_SUPER_ADMIN 默认超级管理员 ADMIN 普通管理员 <br>
	 * 默认超级管理员不可删除
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 是否禁用状态 N：可用 Y:禁用
	 * 
	 * @return
	 */
	public String getIsForbid() {
		return isForbid;
	}

	/**
	 * 是否禁用状态 N：可用 Y:禁用
	 * 
	 */
	public void setIsForbid(String isForbid) {
		this.isForbid = isForbid;
	}

	/**
	 * 获取用户名称
	 * 
	 * @return 用户名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置用户名称
	 * 
	 * @param name
	 *            用户名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 用户描述
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 用户描述
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取用户使用的皮肤风格
	 * 
	 * @return 皮肤风格
	 */
	public String getSkin() {
		return skin;
	}

	/**
	 * 设置用户皮肤风格
	 * 
	 * @param skin
	 *            皮肤风格
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * key盘中的代码
	 * 
	 * @return key码
	 */
	public String getKeyCode() {
		return keyCode;
	}

	/**
	 * key盘中的代码
	 * 
	 * @param keyCode
	 *            key码
	 */
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	/**
	 * 用户登录成功后的Ip地址
	 * 
	 * @return
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * 用户登录成功后的IP地址
	 * 
	 * @param ipAddress
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * 用户sessionID 登录成功后由AU返回可以唯一标示用户身份
	 * 
	 * @return
	 */
	public long getSessionId() {
		return sessionId;
	}

	/**
	 * 用户sessionID 登录成功后由AU返回可以唯一标示用户身份
	 * 
	 */
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * 用户对应的角色集合
	 * 
	 * @return 对应的角色集合
	 */
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	/**
	 * 用户对应的角色集合
	 * 
	 * @param roleSet
	 */
	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	/**
	 * 用户本身拥有的权限集合
	 * 
	 * @return
	 */
	public Set<Right> getRightSet() {
		return rightSet;
	}

	/**
	 * 用户本身拥有的权限集合
	 * 
	 * @param rightSet
	 */
	public void setRightSet(Set<Right> rightSet) {
		this.rightSet = rightSet;
	}

	/**
	 * 
	 * 登录类型
	 * <br/><br/>
	 * @return
	 */
	public String getLogonType() {
		return logonType;
	}

	/**
	 * 
	 * 登录类型
	 * <br/><br/>
	 * @param logonType
	 */
	public void setLogonType(String logonType) {
		this.logonType = logonType;
	}

	/**
	 * 
	 * 登录模块
	 * <br/><br/>
	 * @return
	 */
	public int getModuleID() {
		return moduleID;
	}

	/**
	 * 
	 * 登录模块
	 * <br/><br/>
	 * @param selfModuleID
	 */
	public void setModuleID(int moduleID) {
		this.moduleID = moduleID;
	}

	/**
	 * 获取用户拥有的权限，包括所在角色用户的权限和本身所拥有的权限 以map形式返回
	 * 
	 * @return 用户拥有的权限 key：权限代码 value：权限实体对象
	 */
	public Map<Long, Right> getRightMap() {
		// 为了提高效率 用户拥有的权限值只生成一次
		if (rightMap == null) {
			rightMap = new HashMap<Long, Right>();

			Set<Right> rightSet = this.rightSet;
			Set<Role> roleSet = this.roleSet;

			/** 如果用户权限集合不为空 将用户权限放到权限map中 */
			if (rightSet != null) {
				for (Right right : rightSet) {
					if (!rightMap.containsKey(right.getId())) {
						rightMap.put(right.getId(), right);
					}
				}
			}

			/** 如果角色集合不为空遍历角色和角色下的权限将每个角色下的每个权限放入用户权限map中 */
			if (roleSet != null) {
				// 遍历角色集合
				for (Role role : roleSet) {
					Set<Right> roleRightSet = role.getRightSet();
					// 遍历角色对应的权限集合
					for (Right right : roleRightSet) {
						if (!rightMap.containsKey(right.getId())) {
							rightMap.put(right.getId(), right);
						}
					}
				}
			}
		}

		return rightMap;
	}

	@Override
	public boolean equals(Object o) {
		boolean sign = true;
		if (o instanceof User && o != null) {
			User user = (User) o;
			if (!this.getUserId().equals(user.getUserId())) {
				sign = false;
			}
		} else {
			sign = false;
		}
		return sign;
	}

	@Override
	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("userId", userId);
	}
}
