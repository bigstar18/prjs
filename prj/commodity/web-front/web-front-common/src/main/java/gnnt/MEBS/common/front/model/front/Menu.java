package gnnt.MEBS.common.front.model.front;

import java.util.Set;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class Menu extends StandardModel {
	private static final long serialVersionUID = 2430870130134342514L;
	@ClassDiscription(name = "菜单代码", description = "")
	private Long id;
	@ClassDiscription(name = "菜单名称", description = "")
	private String name;
	@ClassDiscription(name = "菜单图标", description = "")
	private String icon;
	@ClassDiscription(name = "菜单对应的url地址", description = "")
	private String url;
	@ClassDiscription(name = "模块代码", description = "")
	private Integer moduleId;
	@ClassDiscription(name = "是否可见 ", description = "是否：可见 0：可见  其他：不可见")
	private Integer visible;
	@ClassDiscription(name = "序号", description = " 用于属于同一类型的菜单排序")
	private Integer seq;
	@ClassDiscription(name = "父菜单ID", description = "")
	private Long parentID;
	@ClassDiscription(name = "父菜单", description = "")
	private Menu parentMenu;
	@ClassDiscription(name = "子菜单集合", description = "")
	private Set<Menu> childMenuSet;

	public Long getId() {
		return this.id;
	}

	public void setId(Long paramLong) {
		this.id = paramLong;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String paramString) {
		this.icon = paramString;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String paramString) {
		this.url = paramString;
	}

	public Integer getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Integer paramInteger) {
		this.moduleId = paramInteger;
	}

	public Integer getVisible() {
		return this.visible;
	}

	public void setVisible(Integer paramInteger) {
		this.visible = paramInteger;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer paramInteger) {
		this.seq = paramInteger;
	}

	public Set<Menu> getChildMenuSet() {
		return this.childMenuSet;
	}

	public void setChildMenuSet(Set<Menu> paramSet) {
		this.childMenuSet = paramSet;
	}

	public Long getParentID() {
		return this.parentID;
	}

	public void setParentID(Long paramLong) {
		this.parentID = paramLong;
	}

	public Menu getParentMenu() {
		return this.parentMenu;
	}

	public void setParentMenu(Menu paramMenu) {
		this.parentMenu = paramMenu;
	}

	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("id", this.id);
	}
}
