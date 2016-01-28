
package gnnt.MEBS.checkLogon.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * <P>类说明：DAO 公用 父类
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-18下午02:39:00|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class BaseDAOJdbc extends JdbcDaoSupport{
	/** 日志属性 */
	protected volatile Log logger = LogFactory.getLog(this.getClass());
}

