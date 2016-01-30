package gnnt.MEBS.integrated.mgr.action.commoditymanage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.StandardAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.commodity.Breed;
import gnnt.MEBS.integrated.mgr.model.commodity.BreedProps;
import gnnt.MEBS.integrated.mgr.model.commodity.CategoryProperty;
import net.sf.json.JSONArray;

@Controller("breedPropsAction")
@Scope("request")
public class BreedPropsAction extends StandardAction {
	private static final long serialVersionUID = 1L;
	private JSONArray jsonReturn;

	public JSONArray getJsonReturn() {
		return this.jsonReturn;
	}

	public String forwardAddBreedProps() throws Exception {
		String str = this.request.getParameter("breedId");
		Breed localBreed = new Breed();
		localBreed.setBreedId(Long.valueOf(Long.parseLong(str)));
		localBreed = (Breed) getService().get(localBreed);
		Set localSet = localBreed.getCategory().getPropertySet();
		ArrayList localArrayList = new ArrayList();
		if ((localSet != null) && (localSet.size() > 0)) {
			Iterator localIterator = localSet.iterator();
			while (localIterator.hasNext()) {
				CategoryProperty localCategoryProperty = (CategoryProperty) localIterator.next();
				if ("Y".equals(localCategoryProperty.getHasValueDict())) {
					localArrayList.add(localCategoryProperty);
				}
			}
		}
		this.request.setAttribute("propSet", localArrayList);
		this.request.setAttribute("breedId", str);
		return "success";
	}

	public String addBreedProps() {
		this.logger.debug("enter addBreedProps");
		BreedProps localBreedProps = (BreedProps) this.entity;
		getService().add(this.entity);
		CategoryProperty localCategoryProperty = new CategoryProperty();
		localCategoryProperty.setPropertyId(localBreedProps.getCategoryProperty().getPropertyId());
		localCategoryProperty = (CategoryProperty) getService().get(localCategoryProperty);
		addReturnValue(1, 119901L);
		writeOperateLog(1071,
				"在代码为(" + localBreedProps.getBreed().getBreedId() + ")的品名下,添加属性名称为：" + localCategoryProperty.getPropertyName() + "的属性信息", 1, "");
		return "success";
	}

	public String viewByIdProps() {
		BreedProps localBreedProps = (BreedProps) this.entity;
		String str = this.request.getParameter("entity.propertyValue");
		try {
			str = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
			localUnsupportedEncodingException.printStackTrace();
		}
		System.out.println(str);
		localBreedProps.setPropertyValue(str);
		localBreedProps = (BreedProps) getService().get(localBreedProps);
		Breed localBreed = (Breed) getService().get(localBreedProps.getBreed());
		this.request.setAttribute("propSet", localBreed.getCategory().getPropertySet());
		this.request.setAttribute("breedId", localBreedProps.getBreed().getBreedId());
		return "success";
	}

	public String modProps() {
		this.logger.debug("enter update");
		BreedProps localBreedProps1 = (BreedProps) this.entity;
		BreedProps localBreedProps2 = new BreedProps();
		localBreedProps2.setBreed(localBreedProps1.getBreed());
		localBreedProps2.setCategoryProperty(localBreedProps1.getCategoryProperty());
		localBreedProps2.setPropertyValue(this.request.getParameter("propertyValue"));
		if (localBreedProps2.getPropertyValue().equals(localBreedProps1.getPropertyValue())) {
			getService().update(this.entity);
		} else {
			getService().add(this.entity);
			getService().delete(localBreedProps2);
		}
		addReturnValue(1, 119902L);
		CategoryProperty localCategoryProperty = new CategoryProperty();
		localCategoryProperty.setPropertyId(localBreedProps1.getCategoryProperty().getPropertyId());
		localCategoryProperty = (CategoryProperty) getService().get(localCategoryProperty);
		writeOperateLog(1071, "在代码为(" + localBreedProps1.getBreed().getBreedId() + ")的品名下,修改属性名称为：" + localCategoryProperty.getPropertyName()
				+ "的属性值为:" + localBreedProps1.getPropertyValue() + "的信息", 1, "");
		return "success";
	}

	public String removeProps() {
		String str1 = this.request.getParameter("ids");
		String[] arrayOfString1 = str1.split(",");
		LinkedList localLinkedList = new LinkedList();
		for (int i = 0; i < arrayOfString1.length; i++) {
			String[] arrayOfString2 = arrayOfString1[i].split(";");
			BreedProps localObject = new BreedProps();
			Breed localBreed = new Breed();
			localBreed.setBreedId(Long.valueOf(Long.parseLong(arrayOfString2[0])));
			CategoryProperty localCategoryProperty = new CategoryProperty();
			localCategoryProperty.setPropertyId(Long.valueOf(Long.parseLong(arrayOfString2[1])));
			((BreedProps) localObject).setBreed(localBreed);
			((BreedProps) localObject).setCategoryProperty(localCategoryProperty);
			((BreedProps) localObject).setPropertyValue(arrayOfString2[2]);
			localLinkedList.add(localObject);
		}
		getService().deleteBYBulk(localLinkedList);
		addReturnValue(1, 111609L);
		String str2 = "";
		for (int j = 0; j < arrayOfString1.length; j++) {
			String[] localObject = arrayOfString1[j].split(";");
			str2 = str2 + "品名代码为:" + localObject[0] + ",属性代码为:" + localObject[1] + ",属性值为:" + localObject[2] + ";";
		}
		str2 = str2.substring(0, str2.length() - 1);
		writeOperateLog(1071, "删除(" + str2 + ")的品名属性信息", 1, "");
		return "success";
	}

	public String getBreedPropsValueType() {
		long l = Tools.strToLong(this.request.getParameter("propertyId"), -1000L);
		this.jsonReturn = new JSONArray();
		PageRequest localPageRequest = new PageRequest(" and primary.propertyId= " + l);
		Page localPage = getService().getPage(localPageRequest, new CategoryProperty());
		if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
			CategoryProperty localCategoryProperty = (CategoryProperty) localPage.getResult().get(0);
			JSONArray localJSONArray = new JSONArray();
			localJSONArray.add(localCategoryProperty.getFieldType());
			this.jsonReturn.add(localJSONArray);
		}
		return "success";
	}
}
