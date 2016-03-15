package cn.com.agree.eteller.generic.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectUtil {
	@Deprecated
	public static <T> Class<T> getRealClassGenericType(Class clazz) {
		while (clazz != Object.class) {
			Type t = clazz.getGenericSuperclass();
			if ((t instanceof ParameterizedType)) {
				Type[] args = ((ParameterizedType) t).getActualTypeArguments();
				if ((args[0] instanceof Class)) {
					return (Class) args[0];
				}
			}
			clazz = clazz.getSuperclass();
		}
		return (Class) Object.class;
	}

	public static <T> Class<T> getClassGenericType(Class<?> clazz) {
		return (Class) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
	}
}
