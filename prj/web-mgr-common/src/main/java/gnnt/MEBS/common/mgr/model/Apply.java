package gnnt.MEBS.common.mgr.model;

import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

import java.util.Date;

/**
 * 申请表
 * @author xuejt
 */
public class Apply extends StandardModel {
	private static final long serialVersionUID = -1375749907158173756L;

	/** 申请编号 */
	@ClassDiscription(name="申请编号",description="")
	private Long id;

	/** 申请类型 */
	@ClassDiscription(name="申请类型",description="")
	private String applyType;

	/** 
	 * 当前状态<br/>
	 * 0：待审核 <br/>
	 * 1：审核通过 <br/>
	 * 2：审核驳回 <br/>
	 * 3：撤销申请 
	 */
	@ClassDiscription(name="当前状态",description="当前状态 0：待审核  1：审核通过  2：审核驳回  3：撤销申请  ")
	private Integer status;

	/** 内容 */
	@ClassDiscription(name="内容 ",description="")
	private String content;

	/** 备注 */
	@ClassDiscription(name="备注",description="")
	private String note;

	/** 描述 */
	@ClassDiscription(name="描述",description="")
	private String discribe;

	/** 修改时间 */
	@ClassDiscription(name="修改时间 ",description="")
	private Date modTime;

	/** 创建时间 */
	@ClassDiscription(name="创建时间",description="")
	private Date createTime;

	/** 申请人 */
	@ClassDiscription(name="申请人",description="")
	private String applyUser;

	/** 
	 * 操作类型</br>
	 * update</br>
	 * add</br>
	 * delete</br>
	 * deleteCollection 
	 */
	@ClassDiscription(name="操作类型",description="")
	private String operateType;

	/** 业务对象类 */
	@ClassDiscription(name="业务对象类",description="")
	private String entityClass;

	/**
	 * 申请编号
	 * @return Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 申请编号
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 申请类型
	 * @return String
	 */
	public String getApplyType() {
		return applyType;
	}

	/**
	 * 申请类型
	 * @param applyType
	 */
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	/**
	 * 当前状态<br/>
	 * 0：待审核 <br/>
	 * 1：审核通过 <br/>
	 * 2：审核驳回 <br/>
	 * 3：撤销申请 
	 * @return Integer
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 当前状态<br/>
	 * 0：待审核 <br/>
	 * 1：审核通过 <br/>
	 * 2：审核驳回 <br/>
	 * 3：撤销申请 
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 内容
	 * @return String
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 内容
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 备注
	 * @return String
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 备注
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 描述
	 * @return String
	 */
	public String getDiscribe() {
		return discribe;
	}

	/**
	 * 描述
	 * @param discribe
	 */
	public void setDiscribe(String discribe) {
		this.discribe = discribe;
	}

	/**
	 * 修改时间
	 * @return Date
	 */
	public Date getModTime() {
		return modTime;
	}

	/**
	 * 修改时间
	 * @param modTime
	 */
	public void setModTime(Date modTime) {
		this.modTime = modTime;
	}

	/**
	 * 创建时间
	 * @return Date
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 创建时间
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 申请人
	 * @return String
	 */
	public String getApplyUser() {
		return applyUser;
	}

	/**
	 * 申请人
	 * @param applyUser
	 */
	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	/**
	 * 操作类型</br>
	 * update</br>
	 * add</br>
	 * delete</br>
	 * deleteCollection 
	 * @return String
	 */
	public String getOperateType() {
		return operateType;
	}

	/**
	 * 操作类型</br>
	 * update</br>
	 * add</br>
	 * delete</br>
	 * deleteCollection 
	 * @param operateType
	 */
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	/**
	 * 业务对象类
	 * @return String
	 */
	public String getEntityClass() {
		return entityClass;
	}

	/**
	 * 业务对象类
	 * @param entityClass
	 */
	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("id",id);
	}
	
}
