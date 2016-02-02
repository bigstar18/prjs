package gnnt.MEBS.timebargain.server.model;

import java.io.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 交易客户对象.
 *
 * <p><a href="Customer.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class Customer implements Serializable {
    private static final long serialVersionUID = 3690197650654049821L;
	/**
	 * 交易客户ID
	 */
	private String customerID;
	
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 交易代码
	 */
	private String code;	
	
	/**
	 * 状态
	 */
	private short status;
	
	/**
	 * 所属交易商
	 */
	private String firmID;
	
    //交易客户状态
    public static final short STATUS_NORMAL = 0;// 正常
	public static final short STATUS_FORBID = 1;// 禁止交易
	
    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getFirmID() {
		return firmID;
	}

	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
