/*******************************************************************
 * BalanceVO.java   2013-5-23
 * Copyright2012  by GNNT Company. All Rights Reserved.
 * @author:liuchao
 * 
 ******************************************************************/
package gnnt.MEBS.common.communicate.model;

import java.io.Serializable;
import java.util.Date;

/**
 *<P>��˵����������ϢVO<br/>
 *<br/>
 *</p>
 *�޸ļ�¼:<br/>
 *<ul>
 *<li>                        |20120101|xxxx </li>
 *</ul>
 */

public class BalanceVO implements Serializable{

	/**
	 * �ֶ�˵��: <br/>
	 */
	private static final long serialVersionUID = -6092940889246563456L;
	/**
	 * �Ƿ���Խ���
	 */
	private boolean isBalanceStatus;
	/**
	 * ��������
	 */
	private Date tradeDate;
	/**
	 * ��Ϣ��ʾ
	 */
	private String message;
	/**
	 * @return the isBalanceStatus
	 */
	public boolean isBalanceStatus() {
		return isBalanceStatus;
	}
	/**
	 * @param isBalanceStatus the isBalanceStatus to set
	 */
	public void setBalanceStatus(boolean isBalanceStatus) {
		this.isBalanceStatus = isBalanceStatus;
	}
	/**
	 * @return the tradeDate
	 */
	public Date getTradeDate() {
		return tradeDate;
	}
	/**
	 * @param tradeDate the tradeDate to set
	 */
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
