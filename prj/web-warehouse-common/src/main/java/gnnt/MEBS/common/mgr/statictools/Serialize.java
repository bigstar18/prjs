package gnnt.MEBS.common.mgr.statictools;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.common.mgr.model.Role;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.webframe.strutsinterceptor.WriteLogInterceptor;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 序列化反序列化model
 * 
 * @author xuejt
 * 
 */
public class Serialize {

	private transient final static Log logger = LogFactory
			.getLog(WriteLogInterceptor.class);

	public static String serializeToXml(StandardModel model) {
		XStream xstream = new XStream();

		return xstream.toXML(model);
	}

	public static StandardModel deSerializeFromXml(String str) {
		try {
			XStream xs = new XStream(new DomDriver());
			return (StandardModel) xs.fromXML(str);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("str=" + str + ";不能反序列化");
			return null;
		}
	}

	

	public static void main(String[] args) {
		User user = new User();
		user.setName("name");
		user.setIpAddress("ipAddress");
		Set<Role> roleSet = new HashSet<Role>();
		Role role = new Role();
		role.setName("roleName");
		roleSet.add(role);
		user.setRoleSet(roleSet);
		String str = Serialize.serializeToXml(user);
		System.out.println(str);
	}
}
