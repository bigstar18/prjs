package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 非交易日对象.
 *
 * <p><a href="NotTradeDay.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class NotTradeDay implements Serializable {
	private static final long serialVersionUID = 3690197650654049926L;
	
	/**
	 * ID号
	 */
	private Integer ID; 
	
	/**
	 * 不可交易的星期几List
	 */
	private List weekList = new ArrayList();
	
	/**
	 * 不可交易的日期List
	 */
	private List dayList = new ArrayList();
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public List getDayList() {
		return dayList;
	}

	public void setDayList(List dayList) {
		this.dayList = dayList;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer id) {
		ID = id;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public List getWeekList() {
		return weekList;
	}

	public void setWeekList(List weekList) {
		this.weekList = weekList;
	}

}
