package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 系统日志对象.
 *
 * <p><a href="SysLog.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class SysLog implements Serializable {
	private static final long serialVersionUID = 3690197650654049811L;
	
	private Long ID; // ID
	
	private String userID; // 系统用户

	private Date createTime; // 创建时间

	private String note; // 备注
	
	/**
	 * 操作类型
	 * 1501  订单交易核心信息日志
	 * 1502  订单交易核心错误日志
	 */
	private int operateType;
	
	/**
	 * 操作结果
	 * 1 成功
	 * 2 失败
	 */
	private int operateResult;
	/**
	 * 0 表示其他、1 表示后台、2 表示前台、3 表示核心
	 */
	private int logType;
	
	public SysLog(String note)
	{
		this.userID = "system";
		this.note = note;
		this.logType=3;
	}

	public SysLog(String note,int operateType,int operateResult)
	{
		this.userID = "system";
		this.note = note;
		this.operateResult=operateResult;
		this.operateType=operateType;
		this.logType=3;
	}
	

    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Long getID() {
		return ID;
	}


	public void setID(Long id) {
		ID = id;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the operateType
	 */
	public int getOperateType() {
		return operateType;
	}

	/**
	 * @param operateType the operateType to set
	 */
	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	/**
	 * @return the operateResult
	 */
	public int getOperateResult() {
		return operateResult;
	}

	/**
	 * @param operateResult the operateResult to set
	 */
	public void setOperateResult(int operateResult) {
		this.operateResult = operateResult;
	}

	/**
	 * @return the logType
	 */
	public int getLogType() {
		return logType;
	}

	/**
	 * @param logType the logType to set
	 */
	public void setLogType(int logType) {
		this.logType = logType;
	}
	
	
	
	
}
