package gnnt.MEBS.common.mgr.model;


import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

import java.util.Set;

/**
 * 角色信息
 * @author xuejt
 *
 */
public class Role extends StandardModel {

	private static final long serialVersionUID = 377527086315861256L;

	/**角色代码*/
	@ClassDiscription(name="角色代码",description="")
	private Long id;

	/**角色名称*/
	@ClassDiscription(name="角色名称",description="")
	private String name;

	/**角色描述*/
	@ClassDiscription(name="角色描述",description="")
	private String description;

	/**获取角色拥有的权限集合*/
	@ClassDiscription(name="获取角色拥有的权限集合",description="")
	private Set<Right> rightSet;

	/**获取拥有此角色的用户集合*/
	@ClassDiscription(name="获取拥有此角色的用户集合",description="")
	private Set<User> userSet;

	/**
	 * 获取角色代码
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置角色代码
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 角色名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 角色名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * 角色描述
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 角色描述
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取拥有此角色的用户集合
	 * @return
	 */
	public Set<User> getUserSet() {
		return userSet;
	}

	/**
	 * 设置拥有此角色的用户集合
	 * @param userSet
	 */
	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}

	/**
	 * 获取角色拥有的权限集合
	 * @return
	 */
	public Set<Right> getRightSet() {
		return rightSet;
	}

	/**
	 * 设置角色拥有的权限集合
	 * @param rightSet
	 */
	public void setRightSet(Set<Right> rightSet) {
		this.rightSet = rightSet;
	}

	
	@Override
	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("id", id);
	}

}
