
package gnnt.MEBS.activeUser.vo;

import java.util.List;


/**
 * <P>类说明：强制退出用户，传入 VO 对象
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-21下午05:19:05|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class AUCompulsoryLogoffVO extends AUBaseVO{
	/** 序列编号 */
	private static final long serialVersionUID = 8274913725839151940L;

	/** 用户名 */
	private List<String> userIDList;

	/** 操作员 */
	private String operator;

	/** 操作 IP */
	private String logonIP;

	/**
	 * 
	 * 用户名集合
	 * <br/><br/>
	 * @return
	 */
	public List<String> getUserIDList() {
		return userIDList;
	}

	/**
	 * 
	 * 用户名集合
	 * <br/><br/>
	 * @param userID
	 */
	public void setUserIDList(List<String> userIDList) {
		this.userIDList = userIDList;
	}

	/**
	 * 
	 * 操作员
	 * <br/><br/>
	 * @return
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * 
	 * 操作员
	 * <br/><br/>
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 
	 * 操作 IP
	 * <br/><br/>
	 * @return
	 */
	public String getLogonIP() {
		return logonIP;
	}

	/**
	 * 
	 * 操作 IP
	 * <br/><br/>
	 * @param logonIP
	 */
	public void setLogonIP(String logonIP) {
		this.logonIP = logonIP;
	}

}

