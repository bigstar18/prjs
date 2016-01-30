
package gnnt.MEBS.checkLogon.po;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <P>类说明：PO 类的公用父类
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-18下午01:30:00|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public abstract class Clone implements Cloneable{
	/** 日志对象 */
	protected transient Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 获取本类的克隆对象
	 */
	public Object clone(){
		Object result = null;
		try{
			result = super.clone();
		}catch(Exception e){	
		}
		return result;
	}
}

