
package gnnt.MEBS.activeUser.vo;

import java.util.Map;

/**
 * <P>类说明：登录超时退出线程需要传入的对象
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-21下午05:43:36|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class AULogonTimeOutThreadVO extends AUBaseVO{
	/** 序列编号 */
	private static final long serialVersionUID = -2238209963458782972L;

	/** 线程休眠时间，以毫秒为单位 */
	private long timeSpace;

	/**
	 * AU 超时时间，以毫秒为单位<br/>
	 * key 登录类型(web web服务登录,pc 电脑客户端登录,mobile 手机客户端登录);value 超时时间
	 */
	private Map<String,Long> auExpireTimeMap;

	/**
	 * 
	 * 线程休眠时间，以毫秒为单位
	 * <br/><br/>
	 * @return
	 */
	public long getTimeSpace() {
		return timeSpace;
	}

	/**
	 * 
	 * 线程休眠时间，以毫秒为单位
	 * <br/><br/>
	 * @param timeSpace
	 */
	public void setTimeSpace(long timeSpace) {
		this.timeSpace = timeSpace;
	}

	/**
	 * 
	 * AU 超时时间，以毫秒为单位<br/>
	 * key 登录类型(web web服务登录,pc 电脑客户端登录,mobile 手机客户端登录);value 超时时间
	 * <br/><br/>
	 * @return
	 */
	public Map<String, Long> getAuExpireTimeMap() {
		return auExpireTimeMap;
	}

	/**
	 * 
	 * AU 超时时间，以毫秒为单位<br/>
	 * key 登录类型(web web服务登录,pc 电脑客户端登录,mobile 手机客户端登录);value 超时时间
	 * <br/><br/>
	 * @param auExpireTimeMap
	 */
	public void setAuExpireTimeMap(Map<String, Long> auExpireTimeMap) {
		this.auExpireTimeMap = auExpireTimeMap;
	}

}

