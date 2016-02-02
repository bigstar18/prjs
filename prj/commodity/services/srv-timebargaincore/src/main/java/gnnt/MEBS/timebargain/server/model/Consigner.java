package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * 代为委托员对象.
 *
 * <p><a href="Consigner.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class Consigner implements Serializable {

	private static final long serialVersionUID = 3690197650654049933L;
	/**
	 * 代为委托员ID
	 */
	private String consignerID;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 类型 0代为委托员；1强平员
	 */
	private short type;
	
	/**
	 * 状态
	 */
	private short status;
	
	/**
	 * 可操作交易商
	 */
	private List operateFirmList;
	
	
	/**
	 * 登陆IP
	 */
	private String logonIP;
	
	
    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }


	public String getConsignerID() {
		return consignerID;
	}


	public void setConsignerID(String consignerID) {
		this.consignerID = consignerID;
	}


	public String getLogonIP() {
		return logonIP;
	}


	public void setLogonIP(String logonIP) {
		this.logonIP = logonIP;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List getOperateFirmList() {
		return operateFirmList;
	}


	public void setOperateFirmList(List operateFirmList) {
		this.operateFirmList = operateFirmList;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public short getStatus() {
		return status;
	}


	public void setStatus(short status) {
		this.status = status;
	}


	public short getType() {
		return type;
	}


	public void setType(short type) {
		this.type = type;
	}

}
