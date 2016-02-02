package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 延期交易节对象.
 *
 * <p><a href="DelayTradeTime.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.2.2
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class DelayTradeTime implements Serializable {
	private static final long serialVersionUID = 3690197650654049212L;

	private Integer sectionID; // 交易节编号

	private String name;  //交易节名称
	
	private String startTime;  //交易开始时间

	private String endTime;  //交易结束时间
	
	private Short status; // 状态
	
	private Short type; // 交易时间类型
	
	private Date modifyTime; // 修改时间
	
	private long startTimeMillis;  //交易开始时间毫秒

	private long endTimeMillis;  //交易结束时间毫秒
	
    //交易时间类型
    public static final short TYPE_SETTLE = 0;//交收申报
	public static final short TYPE_NEUTRAL = 1;//中立仓申报

    //交易节状态
    public static final short STATUS_INVALID = 0;//无效
	public static final short STATUS_VALID = 1;// 正常
	
    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public Integer getSectionID() {
		return sectionID;
	}

	public void setSectionID(Integer sectionID) {
		this.sectionID = sectionID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public long getStartTimeMillis() {
		return startTimeMillis;
	}

	public void setStartTimeMillis(long startTimeMillis) {
		this.startTimeMillis = startTimeMillis;
	}

	public long getEndTimeMillis() {
		return endTimeMillis;
	}

	public void setEndTimeMillis(long endTimeMillis) {
		this.endTimeMillis = endTimeMillis;
	}

}
