package gnnt.MEBS.common.mgr.model.translate;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 通过Field获取Field对应的名称
 * 
 * @author xuejt
 * 
 */
public class GetNameByField implements IGetNameByField {
	/**
	 * 通过Field获取Field对应的名称集合；
	 */
	private List<IGetNameByField> getNameByFieldList;

	/**
	 * 通过Field获取Field对应的名称集合；
	 */
	public List<IGetNameByField> getGetNameByFieldList() {
		return getNameByFieldList;
	}

	/**
	 * 通过Field获取Field对应的名称集合；
	 */
	public void setGetNameByFieldList(List<IGetNameByField> getNameByFieldList) {
		this.getNameByFieldList = getNameByFieldList;
	}

	public String getName(Field field) {
		// 初始化返回值
		String name = "";
		// 遍历集合 获取field的名称直到获取到名称为止
		if (getNameByFieldList != null && getNameByFieldList.size() > 0) {
			for (IGetNameByField getNameByField : getNameByFieldList) {
				name = getNameByField.getName(field);
				if (name!=null && name.length() > 0) {
					break;
				}
			}
		}
		return name;
	}
}
