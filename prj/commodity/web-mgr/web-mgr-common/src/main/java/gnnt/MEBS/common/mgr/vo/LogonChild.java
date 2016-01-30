
package gnnt.MEBS.common.mgr.vo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * <P>类说明：
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2013-11-22下午04:19:46|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class LogonChild {

	@XmlAttribute(name = "name")
	public String name;

	@XmlElement(name = "USER_ID")
	public String userID;

	@XmlElement(name = "SESSION_ID")
	public String sessionID;

	@XmlElement(name = "MODULE_ID")
	public String moduleID;
	
	@XmlElement(name = "LOGON_TYPE")
	public String logonType;

	
}

