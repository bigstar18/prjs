package gnnt.MEBS.timebargain.server.model;

import java.io.*;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 交易员对象.
 *
 * <p><a href="Trader.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class Trader implements Serializable {

	private static final long serialVersionUID = 3690197650654049833L;
	/**
	 * 交易员ID
	 */
	private String traderID;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 状态
	 */
	private short status;
	
	/**
	 * 所属交易商
	 */
	private String firmID;
	
	/**
	 * 可操作客户
	 */
	private List operateCustomerList;
	
	/**
	 * 注册字
	 */
	private String regWord;
	
	/**
	 * KEY码
	 */
	private String keyCode;
	
	/**
	 * KEY码状态
	 */
	private short keyStatus;
	
	/**
	 * 登陆IP
	 */
	private String logonIP;
	
	private int logonMark;  // 登录标志，0：交易员代码登录；1：用户名登录
	
	/**
	 * 登录类型 (web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
	 */
	private String logonType;
	
	
    public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
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

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRegWord() {
		return regWord;
	}


	public void setRegWord(String regWord) {
		this.regWord = regWord;
	}


	public short getStatus() {
		return status;
	}


	public void setStatus(short status) {
		this.status = status;
	}


	public String getTraderID() {
		return traderID;
	}


	public void setTraderID(String traderID) {
		this.traderID = traderID;
	}


	public List getOperateCustomerList() {
		return operateCustomerList;
	}


	public void setOperateCustomerList(List operateCustomerList) {
		this.operateCustomerList = operateCustomerList;
	}


	public String getLogonIP() {
		return logonIP;
	}


	public void setLogonIP(String logonIP) {
		this.logonIP = logonIP;
	}


	public String getKeyCode() {
		return keyCode;
	}


	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}


	public short getKeyStatus() {
		return keyStatus;
	}


	public void setKeyStatus(short keyStatus) {
		this.keyStatus = keyStatus;
	}

    /**
     * 
     * 方法说明：获取登录标志<br/>
     * 0：交易员代码登录；1：用户名登录<br/>
     *
     * @return
     */
	public int getLogonMark() {
		return logonMark;
	}

    /**
     * 
     * 方法说明：设置登录标志<br/>
     * 0：交易员代码登录；1：用户名登录<br/>
     *
     * @param logonMark
     */
	public void setLogonMark(int logonMark) {
		this.logonMark = logonMark;
	}

    /**
     * 
     * 方法说明：登录类型<br/>
     * <br/>
     *  (web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
     * @return
     */
	public String getLogonType() {
		return logonType;
	}

    /**
     * 
     * 方法说明：登录类型<br/>
     * <br/>
     * (web web服务登录，pc 电脑客户端登录，mobile 手机客户端登录)
     * @param logonType
     */
	public void setLogonType(String logonType) {
		this.logonType = logonType;
	}
	
	
}
