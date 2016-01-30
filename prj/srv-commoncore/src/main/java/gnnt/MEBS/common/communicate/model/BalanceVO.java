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
 *<P>类说明：结算信息VO<br/>
 *<br/>
 *</p>
 *修改记录:<br/>
 *<ul>
 *<li>                        |20120101|xxxx </li>
 *</ul>
 */

public class BalanceVO implements Serializable{

	/**
	 * 字段说明: <br/>
	 */
	private static final long serialVersionUID = -6092940889246563456L;
	/**
	 * 是否可以结算
	 */
	private boolean isBalanceStatus;
	/**
	 * 交易日期
	 */
	private Date tradeDate;
	/**
	 * 消息提示
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
