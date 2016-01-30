
package gnnt.MEBS.activeUser.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <P>类说明：线程公用父类
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-21下午01:38:23|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public abstract class BaseThread extends Thread{
	/** 日志属性 */
	protected transient final Log logger = LogFactory.getLog(this.getClass());

	/** 线程停止标识 */
	protected volatile boolean stop = false;

	/** 线程休眠时间，以 毫秒 计算 */
	protected long timeSpace = 200;

	/**
	 * 
	 * 停止线程运行
	 * <br/><br/>
	 */
	public void pleaseStop(){
		this.stop = true;
		try{
			this.interrupt();
		}catch(Exception e){
		}
	}
}

