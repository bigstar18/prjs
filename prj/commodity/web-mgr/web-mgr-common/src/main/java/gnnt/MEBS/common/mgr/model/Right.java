package gnnt.MEBS.common.mgr.model;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

import java.util.HashSet;
import java.util.Set;

/**
 * 权限信息
 * 
 * @author Administrator
 * 
 */
public class Right extends StandardModel {

	private static final long serialVersionUID = -6128452830189088494L;

	/**权限代码*/
	@ClassDiscription(name="权限代码",description="")
	private Long id;

	/**权限名称*/
	@ClassDiscription(name="权限名称",description="")
	private String name;

	/**权限使用的图标*/
	@ClassDiscription(name="权限使用的图标",description="")
	private String icon;

	/**对应的权限路径*/
	@ClassDiscription(name="对应的权限路径",description="")
	private String url;

	/**对应的资源路径*/
	@ClassDiscription(name="对应的资源路径",description="")
	private String visiturl;

	/**所属模块号*/
	@ClassDiscription(name="所属模块号",description="")
	private Integer moduleId;

	/**
	 * 是否可见 
	 * 0可见、 其他不可见
	 */
	@ClassDiscription(name="是否可见",description="是否可见 0：可见、 其他：不可见")
	private Integer visible;

	/**序号 用于属于同一类型的菜单排序*/
	@ClassDiscription(name="序号",description="用于属于同一类型的菜单排序")
	private Integer seq;

	/**
	 * 权限类型
	 *  -3：只检查session不检查权限的url 
	 *  -2：无需判断权限的URL 
	 *  -1： 父菜单类型
	 *   0：子菜单类型 
	 *   1：页面内增删改查权限
	 */
	@ClassDiscription(name="权限类型",description="权限类型 -3：只检查session不检查权限的url -2：无需判断权限的URL  -1： 父菜单类型 0：子菜单类型  1：页面内增删改查权限")
	private Integer type;

	/**当前权限所拥有的子权限集合*/
	@ClassDiscription(name="当前权限所拥有的子权限集合",description="")
	private Set<Right> childRightSet = new HashSet<Right>();

	/**拥有此权限的用户集合*/
	@ClassDiscription(name="拥有此权限的用户集合",description="")
	private Set<User> userSet;

	/**拥有此权限的角色集合*/
	@ClassDiscription(name="拥有此权限的角色集合",description="")
	private Set<Role> roleSet;

	/**父权限*/
	@ClassDiscription(name="父权限",description="")
	private Right parentRight;

	/**日志对应的分类*/
	@ClassDiscription(name="日志对应的分类",description="")
	private LogCatalog logCatalog;

	/**
	 * 是否自动写日志
	 * 'Y' 写日志
	 * 'N' 不写日志
	 */
	@ClassDiscription(name="是否自动写日志",description="是否自动写日志 Y：写日志 N：不写日志")
	private String isWriteLog;

	/**
	 * 获取权限代码
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置权限代码
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 权限名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 权限名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 权限使用的图标
	 * 
	 * @return
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 权限使用的图标
	 * 
	 * @param icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 对应的权限路径
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 对应的权限路径
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 对应的资源路径
	 * 
	 * @return
	 */
	public String getVisiturl() {
		return visiturl;
	}

	/**
	 * 对应的资源路径
	 * 
	 * @param visiturl
	 */
	public void setVisiturl(String visiturl) {
		this.visiturl = visiturl;
	}

	/**
	 * 所属模块号
	 * 
	 * @return
	 */
	public Integer getModuleId() {
		return moduleId;
	}

	/**
	 * 所属模块号
	 * 
	 * @param moduleId
	 */
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * 是否可见 0可见 其他不可见
	 * 
	 * @return
	 */
	public Integer getVisible() {
		return visible;
	}

	/**
	 * 是否可见 0可见 其他不可见
	 * 
	 * @param visible
	 */
	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	/**
	 * 序号 用于属于同一类型的菜单排序
	 * 
	 * @return
	 */
	public Integer getSeq() {
		return seq;
	}

	/**
	 * 序号 用于属于同一类型的菜单排序
	 * 
	 * @param seq
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	/**
	 * 权限类型 -3：只检查session不检查权限的url -2：无需判断权限的URL -1： 父菜单类型 0：子菜单类型 1：页面内增删改查权限
	 * 
	 * @return
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 权限类型 -2：无需判断权限的URL -1： 父菜单类型 0：子菜单类型 1：页面内增删改查权限
	 * 
	 * @param type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	

	/**
	 * 父权限
	 * 
	 * @return
	 */
	public Right getParentRight() {
		return parentRight;
	}

	/**
	 * 父权限
	 * 
	 * @param right
	 */
	public void setParentRight(Right right) {
		this.parentRight = right;
	}

	/**
	 * 当前权限所拥有的子权限集合
	 * 
	 * @return
	 */
	public Set<Right> getChildRightSet() {
		return childRightSet;
	}

	/**
	 * 当前权限所拥有的子权限集合
	 * 
	 * @param rightSet
	 */
	public void setChildRightSet(Set<Right> rightSet) {
		this.childRightSet = rightSet;
	}

	/**
	 * 拥有此权限的用户集合
	 * 
	 * @return
	 */
	public Set<User> getUserSet() {
		return userSet;
	}

	/**
	 * 拥有此权限的用户集合
	 * 
	 * @param userSet
	 */
	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}

	/**
	 * 拥有此权限的角色集合
	 * 
	 * @return
	 */
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	/**
	 * 拥有此权限的角色集合
	 * 
	 * @param roleSet
	 */
	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	/**
	 * 日志对应的分类
	 * 
	 * @return
	 */
	public LogCatalog getLogCatalog() {
		return logCatalog;
	}

	/**
	 * 日志对应的分类
	 * 
	 * @param logCatalog
	 */
	public void setLogCatalog(LogCatalog logCatalog) {
		this.logCatalog = logCatalog;
	}

	/**
	 * 是否自动写日志
	 * 'Y' 写日志
	 * 'N' 不写日志
	 * @return String
	 */
	public String getIsWriteLog() {
		return isWriteLog;
	}

	/**
	 * 是否自动写日志
	 * 'Y' 写日志
	 * 'N' 不写日志
	 * @param isWriteLog
	 */
	public void setIsWriteLog(String isWriteLog) {
		this.isWriteLog = isWriteLog;
	}

	public boolean equals(Object o) {
		boolean sign = true;
		Right r = this;
		if (o instanceof Right) {
			Right r1 = (Right) o;
			if (!(r.getId() == r1.getId() && r.getUrl().equals(r1.getUrl()))) {
				sign = false;
			}
		} else
			sign = false;
		return sign;
	}

	@Override
	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("id", id);
	}

}
