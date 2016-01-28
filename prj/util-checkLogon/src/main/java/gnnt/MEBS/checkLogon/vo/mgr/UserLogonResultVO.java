
package gnnt.MEBS.checkLogon.vo.mgr;
/**
 * <P>类说明：后台用户登录结果
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-29上午10:35:07|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class UserLogonResultVO {

	/**
	 * 登录结果<br/>
	 * 1 成功、-1 失败
	 */
	private int result;

	/**
	 * 错误码<br/>
	 * -1：管理代码不存在；-2：口令不正确；-3：禁止登录；-4：Key盘验证错误；-5：其他异常 -6交易板块被禁止
	 * 
	 */
	private String recode;

	/** 错误信息 */
	private String message;

	/** AU SessionID */
	private long sessionID;

	/**
	 * 
	 * 登录结果<br/>
	 * 1 成功、-1 失败
	 * <br/><br/>
	 * @return
	 */
	public int getResult() {
		return result;
	}

	/**
	 * 
	 * 登录结果<br/>
	 * 1 成功、-1 失败
	 * <br/><br/>
	 * @param result
	 */
	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * 
	 * 错误码<br/>
	 *  -1：管理代码不存在；-2：口令不正确；-3：禁止登录；-4：Key盘验证错误；-5：其他异常 -6交易板块被禁止
	 * <br/><br/>
	 * @return
	 */
	public String getRecode() {
		return recode;
	}

	/**
	 * 
	 * 错误码<br/>
	 *  -1：管理代码不存在；-2：口令不正确；-3：禁止登录；-4：Key盘验证错误；-5：其他异常 -6交易板块被禁止
	 * <br/><br/>
	 * @param recode
	 */
	public void setRecode(String recode) {
		this.recode = recode;
	}

	/**
	 * 
	 * 错误信息
	 * <br/><br/>
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * 错误信息
	 * <br/><br/>
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 * AU SessionID
	 * <br/><br/>
	 * @return
	 */
	public long getSessionID() {
		return sessionID;
	}

	/**
	 * 
	 * AU SessionID
	 * <br/><br/>
	 * @param sessionID
	 */
	public void setSessionID(long sessionID) {
		this.sessionID = sessionID;
	}

}

