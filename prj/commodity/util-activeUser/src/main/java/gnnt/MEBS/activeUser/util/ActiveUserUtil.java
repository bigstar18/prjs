
package gnnt.MEBS.activeUser.util;

import java.util.Random;

/**
 * <P>类说明：
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-21下午01:26:11|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class ActiveUserUtil {
	/** 用来生成唯一sessionID的随机对象 */
	private static Random random = new Random();

	/**
	 * 
	 * 生成新的 SessionID
	 * <br/><br/>
	 * @return long sessionID
	 */
	public static long createSessionID() {
		long t1 = 0x000000007FFFFFFF & System.currentTimeMillis();
		return ((t1 << 32) | Math.abs(random.nextInt()));
	}
}

