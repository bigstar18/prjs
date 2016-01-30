package gnnt.MEBS.integrated.mgr.action.commoditymanage;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.integrated.mgr.model.commodity.PropertyType;

@Controller("categoryPropertyTypeAction")
@Scope("request")
public class CategoryPropertyTypeAction extends EcsideAction {
	private static final long serialVersionUID = 1L;
	@Resource(name = "categoryPropTypeStatusMap")
	protected Map<Integer, String> categoryPropTypeStatusMap;

	public Map<Integer, String> getCategoryPropTypeStatusMap() {
		return this.categoryPropTypeStatusMap;
	}

	public String addPropertyType() {
		this.logger.debug("enter addPropertyType");
		add();
		addReturnValue(1, 119901L);
		PropertyType localPropertyType = (PropertyType) this.entity;
		writeOperateLog(1071, "添加属性类型编号为为：" + localPropertyType.getPropertyTypeID() + "，分类属性类型名称为【" + localPropertyType.getName() + "】的商品分类属性类型", 1,
				"");
		return "success";
	}

	public String updatePropertyType() throws Exception {
		this.logger.debug("enter updatePropertyType");
		PropertyType localPropertyType = (PropertyType) this.entity;
		getService().update(localPropertyType);
		addReturnValue(1, 119902L);
		writeOperateLog(1071, "修改属性类型编号为为：" + localPropertyType.getPropertyTypeID() + "，分类属性类型名称为【" + localPropertyType.getName() + "】的商品分类属性类型", 1,
				"");
		return "success";
	}

	public String deletePropertyType() {
		this.logger.debug("enter deletePropertyType ");
		String[] arrayOfString1 = this.request.getParameterValues("ids");
		String str1 = "";
		for (String str2 : arrayOfString1) {
			if (!"".equals(str1)) {
				str1 = str1 + ",";
			}
			str1 = str1 + str2;
		}
		if (arrayOfString1.length <= 1) {
			PropertyType propertyType = new PropertyType();
			for (String str3 : arrayOfString1) {
				Long localLong = Long.valueOf(Long.parseLong(str3));
				((PropertyType) propertyType).setPropertyTypeID(localLong);
				propertyType = (PropertyType) getService().get((StandardModel) propertyType);
				getService().delete((StandardModel) propertyType);
				addReturnValue(1, 101003L, new Object[] { "编号为：" + ((PropertyType) propertyType).getPropertyTypeID() + "的商品分类属性类型" });
			}
		} else {
			try {
				delete();
			} catch (SecurityException localSecurityException) {
				localSecurityException.printStackTrace();
			} catch (NoSuchFieldException localNoSuchFieldException) {
				localNoSuchFieldException.printStackTrace();
			}
		}
		writeOperateLog(1071, "删除属性类型编号为为：(" + str1 + ")的商品分类属性类型", 1, "");
		return "success";
	}
}
