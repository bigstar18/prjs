
package gnnt.MEBS.activeUser.vo;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <P>类说明：
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-21下午01:12:12|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public abstract class AUBaseVO implements Serializable{
	/** 序列编号 */
	private static final long serialVersionUID = 6773666333589225909L;

	/** 日志属性 */
	protected transient final Log logger = LogFactory.getLog(this.getClass());
}

