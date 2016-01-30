package gnnt.MEBS.common.front.model.integrated;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.front.Right;
import gnnt.MEBS.common.front.model.front.Role;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class User extends StandardModel {
	private static final long serialVersionUID = -3775440804644210338L;
	@ClassDiscription(name = "交易员ID", description = "")
	private String traderID;
	@ClassDiscription(name = "交易员用户名 *", description = "")
	private String userID;
	@ClassDiscription(name = "交易员名称", description = "")
	private String name;
	@ClassDiscription(name = "口令", description = "")
	private transient String password;
	@ClassDiscription(name = "状态", description = "状态： N：正常 Normal D：禁用 Disable")
	private String status;
	@ClassDiscription(name = "交易员类型", description = "交易员类型：A：管理员 N：一般交易员")
	private String type;
	@ClassDiscription(name = "创建时间", description = "")
	private Date createTime;
	@ClassDiscription(name = "修改时间 ", description = "")
	private Date modifyTime;
	@ClassDiscription(name = "Key码", description = "")
	private String keyCode;
	@ClassDiscription(name = "是否启用Key", description = "是否启用Key Y：启用 N：不启用")
	private String enableKey;
	@ClassDiscription(name = "信任Key", description = "")
	private String trustKey;
	@ClassDiscription(name = "是否修改口令", description = "是否修改口令：0：否 1：是")
	private Integer forceChangePwd;
	@ClassDiscription(name = "上次登录IP", description = "")
	private String lastIP;
	@ClassDiscription(name = "上次登录时间", description = "")
	private Date lastTime;
	@ClassDiscription(name = "用户sessionID 登录成功后由AU返回可以唯一标示用户身份", description = "")
	private Long sessionId;
	@ClassDiscription(name = "用户登录ip地址 ", description = "")
	private String ipAddress;
	@ClassDiscription(name = "用户使用的皮肤风格", description = "")
	private String skin = "default";
	@ClassDiscription(name = "所属交易商", description = "")
	private MFirm belongtoFirm;
	@ClassDiscription(name = "交易员权限集合", description = "")
	private Set<Right> rightSet;
	@ClassDiscription(name = "交易员角色集合", description = "")
	private Set<Role> roleSet;
	@ClassDiscription(name = "所属交易员模块", description = "")
	private Set<TraderModule> traderModule;
	@ClassDiscription(name = "客户权限map集合", description = "")
	private Map<Long, Right> rightMap;
	@ClassDiscription(name = "登录类型", description = "")
	private String logonType;
	@ClassDiscription(name = "模块编号", description = "")
	private Integer moduleID;

	public String getTraderID() {
		return this.traderID;
	}

	public void setTraderID(String paramString) {
		this.traderID = paramString;
	}

	public String getUserID() {
		return this.userID;
	}

	public void setUserID(String paramString) {
		this.userID = paramString;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String paramString) {
		this.name = paramString;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String paramString) {
		this.password = paramString;
	}

	public Integer getForceChangePwd() {
		return this.forceChangePwd;
	}

	public void setForceChangePwd(Integer paramInteger) {
		this.forceChangePwd = paramInteger;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String paramString) {
		this.status = paramString;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String paramString) {
		this.type = paramString;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date paramDate) {
		this.createTime = paramDate;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date paramDate) {
		this.modifyTime = paramDate;
	}

	public String getKeyCode() {
		return this.keyCode;
	}

	public void setKeyCode(String paramString) {
		this.keyCode = paramString;
	}

	public String getEnableKey() {
		return this.enableKey;
	}

	public void setEnableKey(String paramString) {
		this.enableKey = paramString;
	}

	public String getTrustKey() {
		return this.trustKey;
	}

	public void setTrustKey(String paramString) {
		this.trustKey = paramString;
	}

	public String getLastIP() {
		return this.lastIP;
	}

	public void setLastIP(String paramString) {
		this.lastIP = paramString;
	}

	public Date getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Date paramDate) {
		this.lastTime = paramDate;
	}

	public Long getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(Long paramLong) {
		this.sessionId = paramLong;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String paramString) {
		this.ipAddress = paramString;
	}

	public String getSkin() {
		return this.skin;
	}

	public void setSkin(String paramString) {
		this.skin = paramString;
	}

	public MFirm getBelongtoFirm() {
		return this.belongtoFirm;
	}

	public void setBelongtoFirm(MFirm paramMFirm) {
		this.belongtoFirm = paramMFirm;
	}

	public Set<Right> getRightSet() {
		return this.rightSet;
	}

	public void setRightSet(Set<Right> paramSet) {
		this.rightSet = paramSet;
	}

	public Set<Role> getRoleSet() {
		return this.roleSet;
	}

	public void setRoleSet(Set<Role> paramSet) {
		this.roleSet = paramSet;
	}

	public Set<TraderModule> getTraderModule() {
		return this.traderModule;
	}

	public void setTraderModule(Set<TraderModule> paramSet) {
		this.traderModule = paramSet;
	}

	public String getLogonType() {
		return this.logonType;
	}

	public void setLogonType(String paramString) {
		this.logonType = paramString;
	}

	public Integer getModuleID() {
		return this.moduleID;
	}

	public void setModuleID(Integer paramInteger) {
		this.moduleID = paramInteger;
	}

	public Map<Long, Right> getRightMap() {
		if (this.rightMap == null) {
			this.rightMap = new HashMap();
			Set localSet1 = this.rightSet;
			Set localSet2 = this.roleSet;
			Iterator localIterator1;
			if (localSet1 != null) {
				localIterator1 = localSet1.iterator();
				while (localIterator1.hasNext()) {
					Right localObject = (Right) localIterator1.next();
					if (!this.rightMap.containsKey(((Right) localObject).getId())) {
						this.rightMap.put(((Right) localObject).getId(), localObject);
					}
				}
			}
			if (localSet2 != null) {
				localIterator1 = localSet2.iterator();
				while (localIterator1.hasNext()) {
					Role localObject = (Role) localIterator1.next();
					Set localSet3 = ((Role) localObject).getRightSet();
					Iterator localIterator2 = localSet3.iterator();
					while (localIterator2.hasNext()) {
						Right localRight = (Right) localIterator2.next();
						if (!this.rightMap.containsKey(localRight.getId())) {
							this.rightMap.put(localRight.getId(), localRight);
						}
					}
				}
			}
		}
		return this.rightMap;
	}

	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("traderID", this.traderID);
	}
}
