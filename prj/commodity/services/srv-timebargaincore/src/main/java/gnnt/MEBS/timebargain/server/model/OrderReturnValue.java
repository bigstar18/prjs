/*******************************************************************
 * OrderReturnValue   2013-5-6
 * Copyright2012  by GNNT Company. All Rights Reserved.
 * @author:liuchao
 * 
 ******************************************************************/
package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *<P>
 * 类说明：下委托单返回Model<br/>
 * <br/>
 *</p>
 *修改记录:<br/>
 *<ul>
 *<li>|20120101|xxxx</li>
 *</ul>
 */
public class OrderReturnValue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8683444395724922312L;
	/**
	 * 下单返回值
	 */
	private int retCode;
	/**
	 * 返回信息
	 */
	private String retMessage;
	/**
	 * 委托单号（retCode=0时）
	 */
	private Long orderNo;
	/**
	 * 委托时间（retCode=0时）
	 */
	private String orderTime;
	
	
	
	
	public int getRetCode() {
		return retCode;
	}




	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}




	public String getRetMessage() {
		return retMessage;
	}




	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}




	public Long getOrderNo() {
		return orderNo;
	}




	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	




	public String getOrderTime() {
		return orderTime;
	}




	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}




	public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
	
	
}
