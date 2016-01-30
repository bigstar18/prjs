
package gnnt.MEBS.common.mgr.vo;

import gnnt.MEBS.common.mgr.statictools.filetools.XMLWork;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <P>类说明：
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2013-11-22下午04:17:10|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
@XmlRootElement(name = "GNNT")
public class LogonXML {
	@XmlElement(name = "REQ")
	public LogonChild req;

	public static void main(String args[]){
		String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?><GNNT><REQ name=\"check_user\"><USER_ID>xie</USER_ID><SESSION_ID>9137071206884378519</SESSION_ID><MODULE_ID>2</MODULE_ID></REQ></GNNT>";
		LogonXML x = (LogonXML)XMLWork.reader(LogonXML.class, xml);
		System.out.println(x.req.moduleID);
	}
}

