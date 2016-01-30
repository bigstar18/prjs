
package gnnt.MEBS.common.mgr.exception;
/**
 * <P>类说明：执行登录后回调加载相应系统加载信息，异常后抛出本异常
 * ，接收到本异常后将返回登录失败，清空本地 Session，但不调用 AU 退出
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-1-15上午11:26:23|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class CallbakErrorException extends RuntimeException{
	/** 序列编号 */
	private static final long serialVersionUID = -7451309143864648467L;

	/**
	 * 
	 * 构造方法
	 * <br/><br/>
	 * @param str 异常信息
	 */
	public CallbakErrorException(String str){
		super(str);
	}
}

