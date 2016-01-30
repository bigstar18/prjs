package gnnt.MEBS.common.mgr.model.translate;

import java.lang.reflect.Field;

/**
 * 通过Field获取Field对应的名称
 * 
 * @author xuejt
 * 
 */
public interface IGetNameByField {
	/**
	 * 获取field对应的名称
	 * 
	 * @param field
	 * @return
	 */
	public String getName(Field field);
}
