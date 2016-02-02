package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
 
/**
 * 延期交易状态对象.
 *
 * <p><a href="DelayStatus.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.2.2
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class DelayStatus implements Serializable {
	private static final long serialVersionUID = 3690197650654049313L;

	private Date tradeDate; // 交易日期
	
	private int status; // 系统状态
	
	private Integer sectionID; // 所属交易节编号
	
	private String note;  //备注
	
	private String recoverTime;  //暂停后自动恢复时间
	
    //延期交易状态
	public static final int DELAY_STATUS_INIT_SUCCESS = 0;// 初始化完成
	public static final int DELAY_STATUS_SETTLE = 1;// 交收申报
	public static final int DELAY_STATUS_SECTION_PAUSE = 2;// 节间休息
	public static final int DELAY_STATUS_NEUTRAL = 3;// 中立仓申报
	public static final int DELAY_STATUS_PAUSE = 4;// 暂停交易
	public static final int DELAY_STATUS_SECTION_CLOSE = 5;// 交易结束
	
	public static final String[] DELAY_STATUS = {"初始化完成","交收申报","节间休息","中立仓申报","暂停交易" ,"交易结束"};
	

	public DelayStatus()
	{
		
	}
	
	public DelayStatus(int status)
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
