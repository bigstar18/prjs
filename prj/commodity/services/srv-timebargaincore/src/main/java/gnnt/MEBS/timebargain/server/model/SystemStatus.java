package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
 
/**
 * 交易服务器状态对象.
 *
 * <p><a href="SystemStatus.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class SystemStatus implements Serializable {
	private static final long serialVersionUID = 3690197650654049113L;

	private Date tradeDate; // 交易日期
	
	private int status; // 系统状态
	
	private Integer sectionID; // 所属交易节编号
	
	private String note;  //备注
	
	private String recoverTime;  //暂停后自动恢复时间

	public SystemStatus()
	{
		
	}
	
	public SystemStatus(int status)
	{
		this.status = status;
	}
	
    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getSectionID() {
		return sectionID;
	}

	public void setSectionID(Integer sectionID) {
		this.sectionID = sectionID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getRecoverTime() {
		return recoverTime;
	}

	public void setRecoverTime(String recoverTime) {
		this.recoverTime = recoverTime;
	}
}
