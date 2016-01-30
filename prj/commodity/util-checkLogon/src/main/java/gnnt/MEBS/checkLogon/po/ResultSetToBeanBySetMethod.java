package gnnt.MEBS.checkLogon.po;

import java.lang.reflect.Method;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResultSetToBeanBySetMethod implements IResultSetToBean {
	private Log logger = LogFactory.getLog(ResultSetToBeanBySetMethod.class);

	@Override
	public Clone resultSetToBean(Clone object, ResultSet rs) {
		Class<? extends Clone> objClass = object.getClass();
		Clone obj = (Clone) object.clone();
		try {
			Method[] methods = objClass.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().substring(0, 3).equalsIgnoreCase("set")) {
					Object o = rs.getObject(method.getName().substring(3));
					if (object != null) {
						try {
							method.invoke(obj, o);
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("Class:"+objClass.getName()+" in methodName="+method.getName()+"invoke error!");
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return obj;
	}

}
