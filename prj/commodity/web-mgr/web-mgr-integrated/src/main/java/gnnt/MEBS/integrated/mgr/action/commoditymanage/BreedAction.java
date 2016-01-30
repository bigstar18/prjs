package gnnt.MEBS.integrated.mgr.action.commoditymanage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.StandardAction;
import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.TradeModule;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.commodity.Breed;
import gnnt.MEBS.integrated.mgr.model.commodity.BreedProps;
import gnnt.MEBS.integrated.mgr.model.commodity.Category;
import gnnt.MEBS.integrated.mgr.model.commodity.CategoryProperty;
import gnnt.MEBS.integrated.mgr.model.commodity.PropertyType;

@Controller("breedAction")
@Scope("request")
public class BreedAction extends StandardAction {
	private static final long serialVersionUID = 1L;
	private File picture;
	@Resource(name = "breedModeMap")
	private Map<Integer, String> breedModeMap;
	private static byte[] defaultbreedpic;

	public File getPicture() {
		return this.picture;
	}

	public void setPicture(File paramFile) {
		this.picture = paramFile;
	}

	public Map<Integer, String> getBreedModeMap() {
		return this.breedModeMap;
	}

	public String addForwardBreed() {
		String str = this.request.getParameter("categoryId");
		this.request.setAttribute("belongModuleMap", getBelongModuleMap(str));
		this.request.setAttribute("parentId", str);
		return "success";
	}

	public String addBreed() {
		this.logger.debug("enter addBreed");
		Breed localBreed = (Breed) this.entity;
		String[] arrayOfString = this.request.getParameterValues("belongModule");
		String str1 = "";
		int i = 1;
		Map localMap = getBelongModuleMap(this.request.getParameter("parentId"));
		String str2 = (String) ServletActionContext.getRequest().getAttribute("ExceededError");
		if (str2 != null) {
			this.logger.debug(str2);
			addReturnValue(-1, 111607L, new Object[] { str2 });
			this.request.setAttribute("belongModuleMap", localMap);
			this.request.setAttribute("parentId", this.request.getParameter("parentId"));
			return "success";
		}
		if ((arrayOfString != null) && (arrayOfString.length > 0)) {
			for (String str3 : arrayOfString) {
				if (!"".equals(str1)) {
					str1 = str1 + "|";
				}
				str1 = str1 + str3;
				if (localMap.get(Integer.valueOf(Tools.strToInt(str3))) == null) {
					i = 0;
					break;
				}
			}
			localBreed.setBelongModule(str1);
		}
		if (i == 0) {
			addReturnValue(-1, 111606L);
			writeOperateLog(1071, "添加代码为：" + localBreed.getCategory().getCategoryId() + "的分类下,品名为：" + localBreed.getBreedName() + "的品名信息", 0,
					ApplicationContextInit.getErrorInfo("111606"));
			return "success";
		}
		if ((this.picture != null) && (this.picture.exists()) && (this.picture.isFile())) {
			byte[] abyte0 = new byte[(int) this.picture.length()];
			BufferedInputStream localBufferedInputStream = null;
			try {
				localBufferedInputStream = new BufferedInputStream(new FileInputStream(this.picture));
				localBufferedInputStream.read((byte[]) abyte0);
				localBreed.setPicture((byte[]) abyte0);
				try {
					localBufferedInputStream.close();
				} catch (IOException localIOException1) {
					this.logger.error(Tools.getExceptionTrace(localIOException1));
				}
				getService().add(localBreed);
			} catch (FileNotFoundException localFileNotFoundException) {
				this.logger.error(Tools.getExceptionTrace(localFileNotFoundException));
			} catch (IOException localIOException3) {
				this.logger.error(Tools.getExceptionTrace(localIOException3));
			} finally {
				try {
					localBufferedInputStream.close();
				} catch (IOException localIOException5) {
					this.logger.error(Tools.getExceptionTrace(localIOException5));
				}
			}
		}
		addReturnValue(1, 119901L);
		this.request.setAttribute("belongModuleMap", localMap);
		writeOperateLog(1071, "添加代码为：" + localBreed.getCategory().getCategoryId() + "的分类下,品名为：" + localBreed.getBreedName() + "的品名信息", 1, "");
		return "success";
	}

	public String updateFowardBreed() throws Exception {
		this.entity = getService().get(this.entity);
		Breed localBreed = (Breed) this.entity;
		Category localCategory = localBreed.getCategory();
		QueryConditions localQueryConditions1 = new QueryConditions();
		localQueryConditions1.addCondition("status", "=", Integer.valueOf(0));
		PageRequest localPageRequest = new PageRequest(1, 100, localQueryConditions1, " order by sortNo ");
		Page localPage1 = getService().getPage(localPageRequest, new PropertyType());
		LinkedHashMap localLinkedHashMap1 = new LinkedHashMap();
		if ((localPage1 != null) && (localPage1.getResult() != null) && (localPage1.getResult().size() > 0)) {
			for (int i = 0; i < localPage1.getResult().size(); i++) {
				PropertyType localObject1 = (PropertyType) localPage1.getResult().get(i);
				localLinkedHashMap1.put(localObject1, new LinkedHashMap());
			}
		}
		LinkedHashMap localLinkedHashMap2 = new LinkedHashMap();
		Object localObject1 = localCategory.getPropertySet();
		Object localObject2 = ((Set) localObject1).iterator();
		Object localObject4;
		while (((Iterator) localObject2).hasNext()) {
			CategoryProperty localObject3 = (CategoryProperty) ((Iterator) localObject2).next();
			localObject4 = getPageRequest(this.request);
			QueryConditions localQueryConditions2 = new QueryConditions();
			localQueryConditions2.addCondition("primary.breed.breedId", "=", localBreed.getBreedId());
			localQueryConditions2.addCondition("primary.categoryProperty.propertyId", "=", ((CategoryProperty) localObject3).getPropertyId());
			((PageRequest) localObject4).setSortColumns(" order by primary.sortNo,categoryProperty.sortNo");
			((PageRequest) localObject4).setFilters(localQueryConditions2);
			Page localPage2 = getService().getPage((PageRequest) localObject4, new BreedProps());
			Object localObject5 = null;
			Iterator localIterator = localLinkedHashMap1.keySet().iterator();
			while (localIterator.hasNext()) {
				PropertyType localPropertyType = (PropertyType) localIterator.next();
				if (localPropertyType.getPropertyTypeID().equals(((CategoryProperty) localObject3).getPropertyTypeID())) {
					localObject5 = (Map) localLinkedHashMap1.get(localPropertyType);
					break;
				}
			}
			if (localObject5 == null) {
				localObject5 = localLinkedHashMap2;
			}
			((Map) localObject5).put(((CategoryProperty) localObject3).getPropertyName(), localPage2.getResult());
		}
		localObject2 = new LinkedHashMap();
		Object localObject3 = localLinkedHashMap1.keySet().iterator();
		while (((Iterator) localObject3).hasNext()) {
			localObject4 = (PropertyType) ((Iterator) localObject3).next();
			if (((Map) localLinkedHashMap1.get(localObject4)).size() > 0) {
				((Map) localObject2).put(localObject4, localLinkedHashMap1.get(localObject4));
			}
		}
		if (localLinkedHashMap2.size() > 0) {
			localObject3 = new PropertyType();
			((PropertyType) localObject3).setName("其它属性");
			((PropertyType) localObject3).setPropertyTypeID(Long.valueOf(-1L));
			((Map) localObject2).put(localObject3, localLinkedHashMap2);
		}
		this.request.setAttribute("belongModuleMap", getBelongModuleMap(localCategory.getCategoryId() + ""));
		this.request.setAttribute("propsMap", localObject2);
		this.request.setAttribute("breedId", this.entity.fetchPKey().getValue());
		return "success";
	}

	public String updateBreed() throws Exception {
		System.out.println("dfffffffffff=============");
		this.logger.debug("enter update");
		Breed localBreed = (Breed) this.entity;
		String str = (String) ServletActionContext.getRequest().getAttribute("ExceededError");
		if (str != null) {
			this.logger.debug(str);
			addReturnValue(-1, 111607L, new Object[] { str });
			localBreed.setBreedId(Long.valueOf(Tools.strToLong(this.request.getParameter("breedId"))));
			localBreed = (Breed) getService().get(localBreed);
			Category localObject1 = localBreed.getCategory();
			Map localObject2 = new LinkedHashMap();
			Set<CategoryProperty> localObject3 = ((Category) localObject1).getPropertySet();
			Object localObject4 = ((Set) localObject3).iterator();
			while (((Iterator) localObject4).hasNext()) {
				CategoryProperty localObject5 = (CategoryProperty) ((Iterator) localObject4).next();
				PageRequest localObject6 = getPageRequest(this.request);
				QueryConditions localObject7 = new QueryConditions();
				((QueryConditions) localObject7).addCondition("primary.breed.breedId", "=", localBreed.getBreedId());
				((QueryConditions) localObject7).addCondition("primary.categoryProperty.propertyId", "=",
						((CategoryProperty) localObject5).getPropertyId());
				((PageRequest) localObject6).setSortColumns(" order by primary.sortNo,categoryProperty.sortNo");
				((PageRequest) localObject6).setFilters(localObject7);
				Page localPage1 = getService().getPage((PageRequest) localObject6, new BreedProps());
				((Map) localObject2).put(localObject5, localPage1.getResult());
			}
			this.request.setAttribute("belongModuleMap", getBelongModuleMap(localBreed.getCategory().getCategoryId() + ""));
			localObject4 = new QueryConditions();
			((QueryConditions) localObject4).addCondition("status", "=", Integer.valueOf(0));
			PageRequest localObject5 = new PageRequest(1, 100, localObject4, " order by sortNo ");
			Page<StandardModel> localObject6 = getService().getPage((PageRequest) localObject5, new PropertyType());
			Object localObject7 = new LinkedHashMap();
			if ((localObject6 != null) && (((Page) localObject6).getResult() != null) && (((Page) localObject6).getResult().size() > 0)) {
				for (int k = 0; k < ((Page) localObject6).getResult().size(); k++) {
					PropertyType localObject9 = (PropertyType) ((Page) localObject6).getResult().get(k);
					((Map) localObject7).put(localObject9, new LinkedHashMap());
				}
			}
			Object localObject8 = ((Map) localObject2).keySet().iterator();
			while (((Iterator) localObject8).hasNext()) {
				CategoryProperty localObject9 = (CategoryProperty) ((Iterator) localObject8).next();
				Iterator localObject10 = ((Map) localObject7).keySet().iterator();
				while (((Iterator) localObject10).hasNext()) {
					PropertyType localObject11 = (PropertyType) ((Iterator) localObject10).next();
					if (((CategoryProperty) localObject9).getPropertyTypeID().equals(((PropertyType) localObject11).getPropertyTypeID())) {
						((Map) ((Map) localObject7).get(localObject11)).put(((CategoryProperty) localObject9).getPropertyName(),
								((Map) localObject2).get(localObject9));
						break;
					}
				}
			}
			localObject8 = new LinkedHashMap();
			Iterator localObject9 = ((Map) localObject7).keySet().iterator();
			while (((Iterator) localObject9).hasNext()) {
				PropertyType localObject10 = (PropertyType) ((Iterator) localObject9).next();
				if (((Map) ((Map) localObject7).get(localObject10)).size() > 0) {
					((Map) localObject8).put(localObject10, ((Map) localObject7).get(localObject10));
				}
			}
			this.request.setAttribute("propsMap", localObject8);
			this.request.setAttribute("breedId", localBreed.getBreedId());
			this.entity = localBreed;
			return "success";
		}
		Object localObject1 = (Breed) getService().get(this.entity);
		localBreed.setTradeMode(((Breed) localObject1).getTradeMode());
		String[] localObject2 = null;
		if ((this.picture != null) && (this.picture.exists()) && (this.picture.isFile())) {
			byte[] localObject22 = new byte[(int) this.picture.length()];
			BufferedInputStream localObject3 = null;
			try {
				localObject3 = new BufferedInputStream(new FileInputStream(this.picture));
				((BufferedInputStream) localObject3).read((byte[]) localObject22);
				localBreed.setPicture((byte[]) localObject22);
				try {
					((BufferedInputStream) localObject3).close();
				} catch (IOException localIOException1) {
					this.logger.error(Tools.getExceptionTrace(localIOException1));
				}
				localObject2 = this.request.getParameterValues("belongModule");
			} catch (FileNotFoundException localFileNotFoundException) {
				this.logger.error(Tools.getExceptionTrace(localFileNotFoundException));
			} catch (IOException localIOException3) {
				this.logger.error(Tools.getExceptionTrace(localIOException3));
			} finally {
				try {
					((BufferedInputStream) localObject3).close();
				} catch (IOException localIOException5) {
					this.logger.error(Tools.getExceptionTrace(localIOException5));
				}
			}
		}
		Object localObject3 = "";
		int i = 1;
		Object localObject5 = getBelongModuleMap(((Breed) localObject1).getCategory().getCategoryId() + "");
		if ((localObject2 != null) && (localObject2.length > 0)) {
			for (String localObject9 : localObject2) {
				if (!"".equals(localObject3)) {
					localObject3 = (String) localObject3 + "|";
				}
				localObject3 = (String) localObject3 + (String) localObject9;
				if (((Map) localObject5).get(Integer.valueOf(Tools.strToInt((String) localObject9))) == null) {
					i = 0;
					break;
				}
			}
			localBreed.setBelongModule((String) localObject3);
		} else {
			localBreed.setBelongModule("");
		}
		if (i == 0) {
			addReturnValue(-1, 111606L);
			writeOperateLog(1071,
					"添加代码为：" + ((Breed) localObject1).getCategory().getCategoryId() + "的分类下,品名为：" + ((Breed) localObject1).getBreedName() + "的品名信息",
					0, ApplicationContextInit.getErrorInfo("111606"));
		} else {
			getService().update(localBreed);
			addReturnValue(1, 119902L);
			writeOperateLog(1071, "修改代码为：" + localBreed.getCategory().getCategoryId() + "的分类下,品名为：" + localBreed.getBreedName() + "的品名信息", 1, "");
		}
		Object localObject6 = ((Breed) localObject1).getCategory();
		HashMap localHashMap = new HashMap();
		Set localSet = ((Category) localObject6).getPropertySet();
		Object localObject9 = localSet.iterator();
		while (((Iterator) localObject9).hasNext()) {
			CategoryProperty localObject10 = (CategoryProperty) ((Iterator) localObject9).next();
			PageRequest localObject11 = getPageRequest(this.request);
			QueryConditions localObject13 = new QueryConditions();
			((QueryConditions) localObject13).addCondition("primary.breed.breedId", "=", ((Breed) localObject1).getBreedId());
			((QueryConditions) localObject13).addCondition("primary.categoryProperty.propertyId", "=",
					((CategoryProperty) localObject10).getPropertyId());
			((PageRequest) localObject11).setFilters(localObject13);
			Page localPage2 = getService().getPage((PageRequest) localObject11, new BreedProps());
			localHashMap.put(localObject10, localPage2.getResult());
		}
		localObject9 = new QueryConditions();
		((QueryConditions) localObject9).addCondition("status", "=", Integer.valueOf(0));
		Object localObject10 = new PageRequest(1, 100, localObject9, " order by sortNo ");
		Object localObject11 = getService().getPage((PageRequest) localObject10, new PropertyType());
		Object localObject13 = new LinkedHashMap();
		if ((localObject11 != null) && (((Page) localObject11).getResult() != null) && (((Page) localObject11).getResult().size() > 0)) {
			for (int n = 0; n < ((Page) localObject11).getResult().size(); n++) {
				PropertyType localObject15 = (PropertyType) ((Page) localObject11).getResult().get(n);
				((Map) localObject13).put(localObject15, new LinkedHashMap());
			}
		}
		Object localObject14 = localHashMap.keySet().iterator();
		Object localObject16;
		while (((Iterator) localObject14).hasNext()) {
			CategoryProperty localObject15 = (CategoryProperty) ((Iterator) localObject14).next();
			localObject16 = ((Map) localObject13).keySet().iterator();
			while (((Iterator) localObject16).hasNext()) {
				PropertyType localPropertyType = (PropertyType) ((Iterator) localObject16).next();
				if (((CategoryProperty) localObject15).getPropertyTypeID().equals(localPropertyType.getPropertyTypeID())) {
					((Map) ((Map) localObject13).get(localPropertyType)).put(((CategoryProperty) localObject15).getPropertyName(),
							localHashMap.get(localObject15));
					break;
				}
			}
		}
		localObject14 = new LinkedHashMap();
		Object localObject15 = ((Map) localObject13).keySet().iterator();
		while (((Iterator) localObject15).hasNext()) {
			localObject16 = (PropertyType) ((Iterator) localObject15).next();
			if (((Map) ((Map) localObject13).get(localObject16)).size() > 0) {
				((Map) localObject14).put(localObject16, ((Map) localObject13).get(localObject16));
			}
		}
		this.request.setAttribute("propsMap", localObject14);
		this.request.setAttribute("belongModuleMap", getBelongModuleMap(((Breed) localObject1).getCategory().getCategoryId() + ""));
		this.request.setAttribute("breedId", this.entity.fetchPKey().getValue());
		return "success";
	}

	public String removeBreed() {
		Breed localBreed = (Breed) this.entity;
		localBreed = (Breed) getService().get(localBreed);
		localBreed.setStatus(Integer.valueOf(2));
		getService().update(localBreed);
		addReturnValue(1, 111609L);
		PageRequest localPageRequest = new PageRequest(" and primary.breed.breedId=" + localBreed.getBreedId());
		Page localPage = getService().getPage(localPageRequest, new BreedProps());
		if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
			getService().deleteBYBulk(localPage.getResult());
		}
		writeOperateLog(1071, "删除代码为(" + localBreed.getBreedId() + ")的品名", 1, "");
		return "success";
	}

	private Map<Integer, TradeModule> getBelongModuleMap(String paramString) {
		LinkedHashMap localLinkedHashMap = new LinkedHashMap();
		if ((Global.modelContextMap != null) && (Global.modelContextMap.size() > 0)) {
			Set localSet = Global.modelContextMap.keySet();
			Iterator localIterator = localSet.iterator();
			while (localIterator.hasNext()) {
				Integer localInteger = (Integer) localIterator.next();
				Map localMap = (Map) Global.modelContextMap.get(localInteger);
				if ("Y".equalsIgnoreCase(localMap.get("ISNEEDBREED").toString())) {
					TradeModule localTradeModule = new TradeModule();
					Category localCategory = new Category();
					localCategory.setCategoryId(Long.valueOf(Tools.strToLong(paramString)));
					localCategory = (Category) getService().get(localCategory);
					String str = localCategory.getBelongModule();
					if ((str != null) && (str.contains(localInteger + ""))) {
						localTradeModule.setAddFirmFn((String) localMap.get("addFirmFn"));
						localTradeModule.setCnName((String) localMap.get("cnName"));
						localTradeModule.setDelFirmFn((String) localMap.get("delFirmFn"));
						localTradeModule.setEnName((String) localMap.get("enName"));
						localTradeModule.setIsFirmSet((String) localMap.get("isFirmSet"));
						localTradeModule.setModuleId(localInteger);
						localTradeModule.setShortName((String) localMap.get("shortName"));
						localTradeModule.setUpdateFirmStatusFn((String) localMap.get("updateFirmStatusFn"));
						localLinkedHashMap.put(localInteger, localTradeModule);
					}
				}
			}
		}
		return localLinkedHashMap;
	}

	public String getBreedPic() {
		byte[] arrayOfByte = null;
		long l = Tools.strToLong(this.request.getParameter("breedId"), -1000L);
		if (l > 0L) {
			Breed localBreed = new Breed();
			localBreed.setBreedId(Long.valueOf(l));
			localBreed = (Breed) getService().get(localBreed);
			if (localBreed != null) {
				arrayOfByte = localBreed.getPicture();
			}
		}
		if (arrayOfByte == null) {
			if (defaultbreedpic == null) {
				synchronized (this) {
					if (defaultbreedpic == null) {
						String str = System.getProperty("catalina.home") + "/webapps" + this.request.getContextPath()
								+ "/mgr/skinstyle/default/image/app/integrated_mgr/image/photo01.gif";
						File localFile = new File(str);
						if ((localFile.exists()) && (localFile.isFile())) {
							defaultbreedpic = new byte[(int) localFile.length()];
							BufferedInputStream localBufferedInputStream = null;
							try {
								localBufferedInputStream = new BufferedInputStream(new FileInputStream(localFile));
								localBufferedInputStream.read(defaultbreedpic);
								try {
									localBufferedInputStream.close();
								} catch (IOException localIOException3) {
									this.logger.error(Tools.getExceptionTrace(localIOException3));
								}
							} catch (FileNotFoundException localFileNotFoundException) {
								this.logger.error(Tools.getExceptionTrace(localFileNotFoundException));
							} catch (IOException localIOException5) {
								this.logger.error(Tools.getExceptionTrace(localIOException5));
							} finally {
								try {
									localBufferedInputStream.close();
								} catch (IOException localIOException7) {
									this.logger.error(Tools.getExceptionTrace(localIOException7));
								}
							}
						}
					}
				}
			}
			arrayOfByte = defaultbreedpic;
		}
		if (arrayOfByte != null) {
			ServletOutputStream sos = null;
			try {
				sos = this.response.getOutputStream();
				((ServletOutputStream) sos).write(arrayOfByte);
				return null;
			} catch (Exception localException) {
				this.logger.error(Tools.getExceptionTrace(localException));
			} finally {
				if (sos != null) {
					try {
						((ServletOutputStream) sos).close();
					} catch (IOException localIOException8) {
						this.logger.error(Tools.getExceptionTrace(localIOException8));
					}
				}
			}
		}
		return null;
	}
}
