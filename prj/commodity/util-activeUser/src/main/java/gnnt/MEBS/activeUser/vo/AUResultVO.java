
package gnnt.MEBS.activeUser.vo;


/**
 * <P>类说明：返回信息对象
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-21下午02:13:03|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class AUResultVO extends AUBaseVO{
	/** 序列编号 */
	private static final long serialVersionUID = 1753313027635011919L;

	/**
	 * 处理结果<br/>
	 * 1 成功;-1 失败
	 */
	private int result;

	/** 返回码 */
	private String recode;

	/** 返回信息 */
	private String message;

	/**
	 * 
	 * 处理结果<br/>
	 * 1 成功;-1 失败
	 * <br/><br/>
	 * @return
	 */
	public int getResult() {
		return result;
	}

	/**
	 * 
	 * 处理结果<br/>
	 * 1 成功;-1 失败
	 * <br/><br/>
	 * @param result
	 */
	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * 
	 * 返回码
	 * <br/><br/>
	 * @return
	 */
	public String getRecode() {
		return recode;
	}

	/**
	 * 
	 * 返回码
	 * <br/><br/>
	 * @param recode
	 */
	public void setRecode(String recode) {
		this.recode = recode;
	}

	/**
	 * 
	 * 返回信息
	 * <br/><br/>
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * 返回信息
	 * <br/><br/>
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}

