package gnnt.MEBS.common.mgr.model.translate;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 从资源文件中获取field的名称
 * 
 * @author xuejt
 * 
 */
public class GetNameByFieldFromResource implements IGetNameByField {

	public String getName(Field field) {
		// 选择中文
		Locale locale = Locale.SIMPLIFIED_CHINESE;
		String propertiesName = field.getDeclaringClass().getSimpleName();
		String name = "";
		// 选择properties文件
		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(
					"ApplicationResources", locale);
			//key 为对象名.列名
			name = resourceBundle.getString(propertiesName+"."+field.getName());
		} catch (Exception e) {
			name = "";
		}

		return name;
	}
}
