package gnnt.MEBS.common.mgr.model;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

import java.util.Set;

/**
 * 树形菜单数据结构
 * 
 * @author xuejt
 * 
 */
public class Menu extends StandardModel {

	private static final long serialVersionUID = 2430870130134342514L;

	/**菜单代码*/
	@ClassDiscription(name="菜单代码",description="")
	private Long id;

	/**菜单名称*/
	@ClassDiscription(name="菜单名称",description="")
	private String name;

	/**菜单图标*/
	@ClassDiscription(name="菜单图标",description="")
	private String icon;

	/**菜单对应的url地址*/
	@ClassDiscription(name="菜单对应的url地址",description="")
	private String url;

	/**模块代码*/
	@ClassDiscription(name="模块代码",description="")
	private Integer moduleId;

	/**
	 * 是否可见 
	 * 0可见 、其他不可见
	 */
	@ClassDiscription(name="是否可见",description="是否可见 0：可见 ，其他：不可见")
	private Integer visible;

	/**序号 用于属于同一类型的菜单排序*/
	@ClassDiscription(name="序号",description="用于属于同一类型的菜单排序")
	private Integer seq;

	/**父菜单ID*/
	@ClassDiscription(name="父菜单ID",description="")
	private Long parentID;

	/**父菜单*/
	@ClassDiscription(name="父菜单",description="")
	private Menu parentMenu;

	/**子菜单集合*/
	@ClassDiscription(name="子菜单集合",description="")
	private Set<Menu> childMenuSet;

	/**
	 * 菜单代码
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 菜单代码
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 菜单名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 菜单名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 菜单图标
	 * 
	 * @return
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 菜单图标
	 * 
	 * @param icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 菜单对应的url地址
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 菜单对应的url地址
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 模块代码
	 * 
	 * @return
	 */
	public Integer getModuleId() {
		return moduleId;
	}

	/**
	 * 模块代码
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
	 * 子菜单集合
	 * 
	 * @return
	 */
	public Set<Menu> getChildMenuSet() {
		return childMenuSet;
	}

	/**
	 * 子菜单集合
	 * 
	 * @param menuSet
	 */
	public void setChildMenuSet(Set<Menu> childMenuSet) {
		this.childMenuSet = childMenuSet;
	}

	/**
	 * 父菜单ID
	 * 
	 * @return
	 */
	public Long getParentID() {
		return parentID;
	}

	/**
	 * 父菜单ID
	 */
	public void setParentID(Long parentID) {
		this.parentID = parentID;
	}

	/**
	 * 父菜单
	 * 
	 * @return
	 */
	public Menu getParentMenu() {
		return parentMenu;
	}

	/**
	 * 父菜单
	 * 
	 * @param menu
	 */
	public void setParentMenu(Menu menu) {
		this.parentMenu = menu;
	}

	@Override
	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("id", this.id);
	}

}
