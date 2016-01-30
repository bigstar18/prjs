package gnnt.MEBS.checkLogon.po;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResultSetToBeanByField implements IResultSetToBean {
	private Log logger = LogFactory.getLog(ResultSetToBeanByField.class);

	@Override
	public Clone resultSetToBean(Clone object, ResultSet rs) {
		Class<? extends Clone> objClass = object.getClass();
		Clone obj = (Clone) object.clone();
		Field[] fields = objClass.getDeclaredFields();
		for (Field field : fields) {
			// 如果结果中没有改field项则跳过
			try {
				rs.findColumn(field.getName());
			} catch (Exception e) {
				continue;
			}
			// 修改相应filed的权限
			boolean accessFlag = field.isAccessible();
			field.setAccessible(true);
			setFieldValue(obj, field, rs);
			field.setAccessible(accessFlag);
		}
		return obj;
	}

	public void setFieldValue(Object obj, Field field, ResultSet rs) {
		try {
			if (field.getType().equals(java.lang.String.class)) {
				field.set(obj, rs.getString(field.getName()));
			} else if (field.getType().equals(java.lang.Double.class)) {
				field.set(obj, new Double(rs.getDouble(field.getName())));
			} else if (field.getType().equals(java.lang.Integer.class)) {
				field.set(obj, rs.getInt(field.getName()));
			} else if (field.getType().equals(java.lang.Float.class)) {
				field.set(obj, new Float(rs.getFloat(field.getName())));
			} else if (field.getType().equals(java.util.Date.class)) {
				field.set(obj, (Date) rs.getTimestamp(field.getName()));
			} else if (field.getType().equals(java.sql.Date.class)) {
				if (rs.getTimestamp(field.getName()) != null) {
					field.set(obj, new java.sql.Date(rs.getTimestamp(
							field.getName()).getTime()));
				}
			} else if (field.getType().equals(double.class)) {
				field.set(obj, rs.getDouble(field.getName()));
			} else if (field.getType().equals(int.class)) {
				field.set(obj, rs.getInt(field.getName()));
			} else if (field.getType().equals(float.class)) {
				field.set(obj, rs.getFloat(field.getName()));
			} else if (field.getType().equals(long.class)) {
				field.set(obj, rs.getLong(field.getName()));
			} else {
				field.set(obj, rs.getObject(field.getName()));
			}
			logger.debug(field.getName() + ":" + field.get(obj));
		} catch (Exception e) {
			logger.error("name:" + field + " " + field.getType());
			logger.error(e);
		}
	}

	/*
	 * 根据类型对具体对象属性赋值
	 */
	public static void setFieldValue(Object form, Field field, String value) {

		String elemType = field.getType().toString();

		if (elemType.indexOf("boolean") != -1
				|| elemType.indexOf("Boolean") != -1) {
			try {
				field.set(form, Boolean.valueOf(value));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (elemType.indexOf("byte") != -1
				|| elemType.indexOf("Byte") != -1) {
			try {
				field.set(form, Byte.valueOf(value));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (elemType.indexOf("char") != -1
				|| elemType.indexOf("Character") != -1) {
			try {
				field.set(form, Character.valueOf(value.charAt(0)));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (elemType.indexOf("double") != -1
				|| elemType.indexOf("Double") != -1) {
			try {
				field.set(form, Double.valueOf(value));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (elemType.indexOf("float") != -1
				|| elemType.indexOf("Float") != -1) {
			try {
				field.set(form, Float.valueOf(value));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (elemType.indexOf("int") != -1
				|| elemType.indexOf("Integer") != -1) {
			try {
				field.set(form, Integer.valueOf(value));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (elemType.indexOf("long") != -1
				|| elemType.indexOf("Long") != -1) {
			try {
				field.set(form, Long.valueOf(value));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (elemType.indexOf("short") != -1
				|| elemType.indexOf("Short") != -1) {
			try {
				field.set(form, Short.valueOf(value));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			try {
				field.set(form, (Object) value);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static void setFieldValueBySet(Object form, Field field, String value) {
		Class<?> fieldType = field.getType();
		Method[] methods = form.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method methodSet = methods[i];
			String methodNameSet = methodSet.getName();
			if ("set".equals(methodNameSet.subSequence(0, 3))) {
				try {
					// 拼装set方法
					Method setMethod = form.getClass().getMethod(methodNameSet,
							new Class[] { fieldType });
					// 不同参数类型的set方法调用
					if (fieldType == String.class) {
						setMethod.invoke(form, new Object[] { value });
					} else if (fieldType == Integer.class
							|| fieldType == Float.class
							|| fieldType == Long.class
							|| fieldType == Double.class
							|| fieldType == Byte.class
							|| fieldType == Boolean.class
							|| fieldType == Character.class) {
						Method valueOf = fieldType.getMethod("valueOf",
								new Class[] { String.class });
						Object valueObj = valueOf.invoke(fieldType,
								new Object[] { value });
						setMethod.invoke(form, new Object[] { valueObj });
					} else if (fieldType == int.class
							|| fieldType == float.class
							|| fieldType == long.class
							|| fieldType == double.class) {
						Class<?> c2 = null;
						if (fieldType == int.class) {
							c2 = Integer.class;
						} else if (fieldType == float.class) {
							c2 = Float.class;
						} else if (fieldType == long.class) {
							c2 = Long.class;
						} else if (fieldType == double.class) {
							c2 = Double.class;
						}
						Method valueOf = c2.getMethod("valueOf",
								new Class[] { String.class });
						Object valueObj = valueOf.invoke(c2,
								new Object[] { value });
						setMethod.invoke(form, new Object[] { valueObj });
					} else if (fieldType == java.util.Date.class
							|| fieldType == java.sql.Date.class) {
						java.text.SimpleDateFormat simpleDateFormat = null;
						String formatChar = null;
						formatChar = "yyyy-MM-dd";
						if (formatChar != null) {
							simpleDateFormat = new java.text.SimpleDateFormat(
									formatChar);
							String str = simpleDateFormat.format(value);
							Date date = simpleDateFormat.parse(str);
							Object dateObj = fieldType.newInstance();
							Method setTime = fieldType.getMethod("setTime",
									new Class[] { long.class });
							setTime.invoke(dateObj, new Object[] { date
									.getTime() });
							setMethod.invoke(form, new Object[] { dateObj });
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
