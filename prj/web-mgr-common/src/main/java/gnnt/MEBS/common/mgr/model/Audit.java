package gnnt.MEBS.common.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

import java.util.Date;

/**
 * 审核对象
 * 
 * @author xuejt
 * 
 */
public class Audit extends StandardModel {
	private static final long serialVersionUID = -6336810614832268885L;

	/** 审核编号 */
	@ClassDiscription(name="审核编号",description="")
	private Long id; 

	/** 申请单ID */
	@ClassDiscription(name="申请单ID",description="")
	private Apply apply;

	/** 
	 * 状态<br/>
	 * 1：审核通过 <br/>
	 * 2：驳回申请
	 */
	@ClassDiscription(name="状态",description="状态 1：审核通过 2：驳回申请")
	private Integer status;

	/** 审核人 */
	@ClassDiscription(name="审核人",description="")
	private String auditUser;

	/** 修改时间 */
	@ClassDiscription(name="修改时间",description="")
	private Date modTime;

	/**
	 * 审核编号
	 * @return Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 审核编号
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 申请单ID
	 * @return Apply
	 */
	public Apply getApply() {
		return apply;
	}

	/**
	 * 申请单ID
	 * @param apply
	 */
	public void setApply(Apply apply) {
		this.apply = apply;
	}

	/**
	 * 状态<br/>
	 * 1：审核通过 <br/>
	 * 2：驳回申请
	 * @return Integer
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 状态<br/>
	 * 1：审核通过 <br/>
	 * 2：驳回申请
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 审核人
	 * @return String
	 */
	public String getAuditUser() {
		return auditUser;
	}

	/**
	 * 审核人
	 * @param auditUser
	 */
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
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

	@Override
	public PrimaryInfo fetchPKey() {
		return new PrimaryInfo("id",id);
	}

}
