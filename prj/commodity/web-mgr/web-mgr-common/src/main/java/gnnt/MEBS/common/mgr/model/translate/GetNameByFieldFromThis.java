package gnnt.MEBS.common.mgr.model.translate;

import java.lang.reflect.Field;

/**
 * 获取field本身的名称
 * 
 * @author xuejt
 * 
 */
public class GetNameByFieldFromThis implements IGetNameByField {

	public String getName(Field field) {
		return field.getName();
	}
}
