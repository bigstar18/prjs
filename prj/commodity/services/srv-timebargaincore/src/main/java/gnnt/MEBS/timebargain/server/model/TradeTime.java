package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 交易节对象.
 *
 * <p><a href="TradeTime.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class TradeTime implements Serializable {
	private static final long serialVersionUID = 3690197650654049112L;

	private Integer sectionID; // 交易节编号

	private String name;  //交易节名称
	
	private String startTime;  //交易开始时间

	private String endTime;  //交易结束时间
	
	private Short status; // 状态
	
	private Short gatherBid; // 是否起用集合竞价
	
	private String bidStartTime;  //集合竞价开始时间

	private String bidEndTime;  //集合竞价结束时间

	private Date modifyTime; // 修改时间
	
	private long startTimeMillis;  //交易开始时间毫秒

	private long endTimeMillis;  //交易结束时间毫秒
	
	private long bidStartTimeMillis;  //集合竞价开始时间毫秒

	private long bidEndTimeMillis;  //集合竞价结束时间毫秒
	
	private String startDate;  //交易开始日期字符串，格式yyyy-MM-dd

	private String endDate;  //交易结束日期字符串，格式yyyy-MM-dd
	
	private String bidStartDate;  //交易开始日期字符串，格式yyyy-MM-dd

	private String bidEndDate;  //交易结束日期字符串，格式yyyy-MM-dd
	
	//是否起用集合竞价
    public static final short GATHERBID_NOT = 0;//不启用
	public static final short GATHERBID_YES = 1;// 启用

    //交易节状态
    public static final short STATUS_INVALID = 0;//无效
	public static final short STATUS_VALID = 1;// 正常
	
    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSectionID() {
		return sectionID;
	}

	public void setSectionID(Integer sectionID) {
		this.sectionID = sectionID;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getBidEndTime() {
		return bidEndTime;
	}

	public void setBidEndTime(String bidEndTime) {
		this.bidEndTime = bidEndTime;
	}

	public String getBidStartTime() {
		return bidStartTime;
	}

	public void setBidStartTime(String bidStartTime) {
		this.bidStartTime = bidStartTime;
	}

	public Short getGatherBid() {
		return gatherBid;
	}

	public void setGatherBid(Short gatherBid) {
		this.gatherBid = gatherBid;
	}

	public long getBidEndTimeMillis() {
		return bidEndTimeMillis;
	}

	public void setBidEndTimeMillis(long bidEndTimeMillis) {
		this.bidEndTimeMillis = bidEndTimeMillis;
	}

	public long getBidStartTimeMillis() {
		return bidStartTimeMillis;
	}

	public void setBidStartTimeMillis(long bidStartTimeMillis) {
		this.bidStartTimeMillis = bidStartTimeMillis;
	}

	public long getEndTimeMillis() {
		return endTimeMillis;
	}

	public void setEndTimeMillis(long endTimeMillis) {
		this.endTimeMillis = endTimeMillis;
	}

	public long getStartTimeMillis() {
		return startTimeMillis;
	}

	public void setStartTimeMillis(long startTimeMillis) {
		this.startTimeMillis = startTimeMillis;
	}

    public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBidStartDate() {
		return bidStartDate;
	}

	public void setBidStartDate(String bidStartDate) {
		this.bidStartDate = bidStartDate;
	}

	public String getBidEndDate() {
		return bidEndDate;
	}

	public void setBidEndDate(String bidEndDate) {
		this.bidEndDate = bidEndDate;
	}

}
