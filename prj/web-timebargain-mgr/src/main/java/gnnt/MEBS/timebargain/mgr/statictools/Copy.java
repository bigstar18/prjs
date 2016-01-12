package gnnt.MEBS.timebargain.mgr.statictools;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import gnnt.MEBS.common.mgr.model.StandardModel;

public class Copy {
	public static StandardModel copy(StandardModel fromObject, StandardModel toObject) {
		Method fromMethods[] = fromObject.getClass().getMethods();
		Method methods[] = toObject.getClass().getMethods();
		if (methods != null && methods.length > 0) {
			Map methodsMap = new HashMap();
			List toMethods = new ArrayList();
			Method amethod[];
			int j = (amethod = methods).length;
			for (int i = 0; i < j; i++) {
				Method method = amethod[i];
				methodsMap.put(method.getName(), method);
				if (method.getName().startsWith("get"))
					toMethods.add(method);
			}

			for (Iterator iterator = toMethods.iterator(); iterator.hasNext();) {
				Method method = (Method) iterator.next();
				String name = method.getName();
				Method amethod1[];
				int l = (amethod1 = fromMethods).length;
				for (int k = 0; k < l; k++) {
					Method method1 = amethod1[k];
					if (name.equals(method1.getName()) && !name.contains("TradeTime"))
						try {
							Object object = method1.invoke(fromObject, new Object[0]);
							Method target = (Method) methodsMap.get((new StringBuilder("set")).append(name.substring(3, name.length())).toString());
							if (target != null)
								target.invoke(toObject, new Object[] { object });
						} catch (Exception e) {
							e.printStackTrace();
						}
				}

			}

		}
		return toObject;
	}
}