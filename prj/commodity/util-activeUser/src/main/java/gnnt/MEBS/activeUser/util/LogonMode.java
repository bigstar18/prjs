
package gnnt.MEBS.activeUser.util;
/**
 * <P>类说明：登录模式
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-24上午11:54:09|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public enum LogonMode {
	/** 同一时间一个用户只允许一个登录，如果第二个同用户登录，则踢掉前一个用户 */
	SINGLE_MODE(1),
	/** 允许同一个用户在不同的地方同时登录 */
	MULTI_MODE(2);

	/** 设置结果 */
	private final int value;

	/**
	 * 
	 * 返回结果
	 * <br/><br/>
	 * @return
	 */
	public int getValue(){
		return value;
	}

	/**
	 * 
	 * 构造方法
	 * <br/><br/>
	 * @param value
	 */
	private LogonMode(int value){
		this.value = value;
	}
}

