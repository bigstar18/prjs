package gnnt.MEBS.base.copy;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ParamUtil {
	public static void bindData(HttpServletRequest paramHttpServletRequest, Object paramObject) {
		Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
		List localList = getParamNames(paramObject, localEnumeration);
		for (int i = 0; i < localList.size(); i++) {
			try {
				String str1 = (String) localList.get(i);
				String str2 = paramHttpServletRequest.getParameter(str1);
				bindSubObject(paramObject, str1, str2);
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
	}

	public static void bindSubObject(Object paramObject, String paramString1, String paramString2) throws SecurityException, Exception {
		String str = getFunName(paramString1);
		Method localMethod1 = paramObject.getClass().getMethod("get" + str, null);
		Class localClass = localMethod1.getReturnType();
		if ("".equals(paramString2.trim())) {
			if (localClass == String.class) {
				Method localMethod2 = paramObject.getClass().getMethod("set" + str, new Class[] { localClass });
				localMethod2.invoke(paramObject, new Object[] { "" });
			}
			return;
		}
		Method localMethod2 = paramObject.getClass().getMethod("set" + str, new Class[] { localClass });
		if (localClass == String.class) {
			localMethod2.invoke(paramObject, new Object[] { paramString2 });
		} else {
			Object localObject1;
			Object localObject2;
			if ((localClass == Integer.class) || (localClass == Float.class) || (localClass == Long.class) || (localClass == Double.class)
					|| (localClass == Byte.class) || (localClass == Boolean.class) || (localClass == Character.class)
					|| (localClass == Short.class)) {
				localObject1 = localClass.getMethod("valueOf", new Class[] { String.class });
				localObject2 = ((Method) localObject1).invoke(localClass, new Object[] { paramString2 });
				localMethod2.invoke(paramObject, new Object[] { localObject2 });
			} else {
				Object localObject3;
				if ((localClass == Integer.TYPE) || (localClass == Float.TYPE) || (localClass == Long.TYPE) || (localClass == Double.TYPE)) {
					localObject1 = null;
					if (localClass == Integer.TYPE) {
						localObject1 = Integer.class;
					} else if (localClass == Float.TYPE) {
						localObject1 = Float.class;
					} else if (localClass == Long.TYPE) {
						localObject1 = Long.class;
					} else if (localClass == Double.TYPE) {
						localObject1 = Double.class;
					}
					localObject2 = ((Class) localObject1).getMethod("valueOf", new Class[] { String.class });
					localObject3 = ((Method) localObject2).invoke(localObject1, new Object[] { paramString2 });
					localMethod2.invoke(paramObject, new Object[] { localObject3 });
				} else if ((localClass == java.util.Date.class) || (localClass == java.sql.Date.class)) {
					localObject1 = null;
					localObject2 = null;
					localObject2 = "yyyy-MM-dd";
					if (localObject2 != null) {
						localObject1 = new SimpleDateFormat((String) localObject2);
						localObject3 = ((SimpleDateFormat) localObject1).parse(paramString2);
						Object localObject4 = localClass.newInstance();
						Method localMethod3 = localClass.getMethod("setTime", new Class[] { Long.TYPE });
						localMethod3.invoke(localObject4, new Object[] { Long.valueOf(((java.util.Date) localObject3).getTime()) });
						localMethod2.invoke(paramObject, new Object[] { localObject4 });
					}
				}
			}
		}
	}

	private static List<String> getParamNames(Object paramObject, Enumeration<String> paramEnumeration) {
		Class localClass = paramObject.getClass();
		Method[] arrayOfMethod = localClass.getMethods();
		ArrayList localArrayList = new ArrayList();
		String str1 = "";
		String str2 = "";
		while (paramEnumeration.hasMoreElements()) {
			str2 = (String) paramEnumeration.nextElement();
			str1 = "set" + getFunName(str2);
			for (int i = 0; i < arrayOfMethod.length; i++) {
				if (str1.equals(arrayOfMethod[i].getName())) {
					localArrayList.add(str2);
				}
			}
		}
		return localArrayList;
	}

	public static String getFunName(String paramString) {
		String str = paramString.substring(0, 1).toUpperCase();
		if (paramString.length() > 1) {
			str = str + paramString.substring(1, paramString.length());
		}
		return str;
	}
}
