package gnnt.MEBS.common.mgr.model.translate;

import java.lang.reflect.Field;

/**
 * 通过注解获取field对应的名称
 * @author xuejt
 *
 */
public class GetNameByFieldFromAnnotation implements IGetNameByField{

	public String getName(Field field) {
		ClassDiscription classDiscription=(ClassDiscription) field.getAnnotation(ClassDiscription.class);
		String name="";
		if(classDiscription!=null){
			name=classDiscription.name();
		}
		return name;
	}

}
