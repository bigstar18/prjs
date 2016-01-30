package gnnt.MEBS.common.front.model.front;

import java.util.Set;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class Role extends StandardModel {
	private static final long serialVersionUID = 377527086315861256L;
	@ClassDiscription(name = "角色代码", description = "")
	private Long id;
	@ClassDiscription(name = "角色名称", description = "")
	private String name;
	@ClassDiscription(name = "角色描述", description = "")
	private String description;
	@ClassDiscription(name = "获取角色拥有的权限集合", description = "")
	private Set<Right> rightSet;

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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String paramString) {
		this.description = paramString;
	}

	public Set<Right> getRightSet() {
		return this.rightSet;
	}

	public void setRightSet(Set<Right> paramSet) {
		this.rightSet = paramSet;
	}

	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("id", this.id);
	}
}
