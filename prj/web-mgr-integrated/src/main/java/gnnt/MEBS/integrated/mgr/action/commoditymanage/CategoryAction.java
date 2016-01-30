package gnnt.MEBS.integrated.mgr.action.commoditymanage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.StandardAction;
import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.TradeModule;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.statictools.Arith;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.SystemProps;
import gnnt.MEBS.integrated.mgr.model.commodity.Breed;
import gnnt.MEBS.integrated.mgr.model.commodity.Category;
import net.sf.json.JSONArray;

@Controller("categoryAction")
@Scope("request")
public class CategoryAction extends StandardAction {
	private static final long serialVersionUID = 1L;
	@Resource(name = "categoryPropTypeMap")
	protected Map<Integer, String> categoryPropTypeMap;
	@Resource(name = "isPickOrSubOrderMap")
	protected Map<String, String> isPickOrSubOrderMap;
	private JSONArray jsonReturn;
	private Category category;

	public JSONArray getJsonReturn() {
		return this.jsonReturn;
	}

	public Map<Integer, String> getCategoryPropTypeMap() {
		return this.categoryPropTypeMap;
	}

	public void setCategoryPropTypeMap(Map<Integer, String> paramMap) {
		this.categoryPropTypeMap = paramMap;
	}

	public Map<String, String> getIsPickOrSubOrderMap() {
		return this.isPickOrSubOrderMap;
	}

	public void setIsPickOrSubOrderMap(Map<String, String> paramMap) {
		this.isPickOrSubOrderMap = paramMap;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category paramCategory) {
		this.category = paramCategory;
	}

	public String categoryShow() {
		Category localCategory = new Category();
		localCategory.setCategoryId(Long.valueOf(-1L));
		this.category = ((Category) getService().get(localCategory));
		String str = this.category.getJsons(this.category.findTreeJsons());
		str = "[" + str + "]";
		this.request.setAttribute("json", str);
		return "success";
	}

	public String breedShow() {
		Category localCategory = new Category();
		localCategory.setCategoryId(Long.valueOf(-1L));
		this.category = ((Category) getService().get(localCategory));
		String str = this.category.getJsons(this.category.findBreedTreeJsons());
		str = "[" + str + "]";
		this.request.setAttribute("json", str);
		return "success";
	}

	public String addForwardCategory() {
		String str = this.request.getParameter("categoryId");
		PageRequest localPageRequest = new PageRequest(" and primary.parentCategory.categoryId=" + Tools.strToLong(str) + " and primary.status=1");
		Page localPage = getService().getPage(localPageRequest, new Category());
		if ("-1".equals(str)) {
			this.request.setAttribute("belongModuleMap", getBelongModuleMap("-1"));
		} else {
			this.request.setAttribute("belongModuleMap", getBelongModuleMap(str));
		}
		SystemProps localSystemProps = new SystemProps();
		localSystemProps.setPropsKey("Offset");
		localSystemProps = (SystemProps) getService().get(localSystemProps);
		double d = Tools.strToDouble(localSystemProps.getRunTimeValue(), 20.0D);
		this.request.setAttribute("OffSet", Tools.fmtDouble2(Arith.mul(d, 100.0F)));
		this.request.setAttribute("categoryList", localPage.getResult());
		this.request.setAttribute("parentId", str);
		return "success";
	}

	public String addCategory() {
		logger.debug("enter add");
		Category category1 = (Category) entity;
		String as[] = request.getParameterValues("belongModule");
		boolean flag = true;
		String s = "";
		Map map = getBelongModuleMap("-1");
		Map map1 = getBelongModuleMap((new StringBuilder()).append(category1.getParentCategory().getCategoryId()).append("").toString());
		if (as != null && as.length > 0) {
			String as1[] = as;
			int i = as1.length;
			for (int j = 0; j < i; j++) {
				String s1 = as1[j];
				if (!"".equals(s))
					s = (new StringBuilder()).append(s).append("|").toString();
				s = (new StringBuilder()).append(s).append(s1).toString();
				if (category1.getParentCategory().getCategoryId().longValue() == -1L) {
					if (map.get(Integer.valueOf(Tools.strToInt(s1))) != null)
						continue;
					flag = false;
					break;
				}
				if (map1.get(Integer.valueOf(Tools.strToInt(s1))) != null)
					continue;
				flag = false;
				break;
			}

			category1.setBelongModule(s);
		}
		PageRequest pagerequest = new PageRequest((new StringBuilder()).append(" and primary.parentCategory.categoryId=")
				.append(category1.getParentCategory().getCategoryId()).append(" and primary.status=1").toString());
		Page page = getService().getPage(pagerequest, new Category());
		request.setAttribute("belongModuleMap",
				getBelongModuleMap((new StringBuilder()).append(category1.getParentCategory().getCategoryId()).append("").toString()));
		request.setAttribute("categoryList", page.getResult());
		if (!flag) {
			addReturnValue(-1, 0x1b3f5L);
			writeOperateLog(1071, (new StringBuilder()).append("添加名称为：").append(category1.getCategoryName()).append("的分类").toString(), 0,
					ApplicationContextInit.getErrorInfo("111605"));
			return "success";
		}
		if (category1.getIsOffSet().equals("N"))
			category1.setOffSetRate_v(new BigDecimal(0));
		boolean flag1 = true;
		if (category1.getParentCategory().getCategoryId().longValue() != -1L) {
			Category category2 = new Category();
			category2.setCategoryId(category1.getParentCategory().getCategoryId());
			category2 = (Category) getService().get(category2);
			if (category2.getBreedSet() != null && category2.getBreedSet().size() > 0) {
				Iterator iterator = category2.getBreedSet().iterator();
				do {
					if (!iterator.hasNext())
						break;
					Breed breed = (Breed) iterator.next();
					if (breed.getStatus().intValue() != 1)
						continue;
					flag1 = false;
					break;
				} while (true);
				if (!flag1) {
					addReturnValue(-1, 0x1b3f0L);
					writeOperateLog(1071, (new StringBuilder()).append("添加名称为：").append(category1.getCategoryName()).append("的分类").toString(), 0,
							ApplicationContextInit.getErrorInfo("111608"));
				} else {
					category2.setType("category");
					getService().update(category2);
					getService().add(category1);
					addReturnValue(1, 0x1d45dL);
					writeOperateLog(1071, (new StringBuilder()).append("添加名称为：").append(category1.getCategoryName()).append("的分类").toString(), 1, "");
				}
			} else {
				category2.setType("category");
				getService().update(category2);
				getService().add(category1);
				addReturnValue(1, 0x1d45dL);
				writeOperateLog(1071, (new StringBuilder()).append("添加名称为：").append(category1.getCategoryName()).append("的分类").toString(), 1, "");
			}
		} else {
			category1.setType("leaf");
			getService().add(category1);
			addReturnValue(1, 0x1d45dL);
			writeOperateLog(1071, (new StringBuilder()).append("添加名称为：").append(category1.getCategoryName()).append("的分类").toString(), 1, "");
		}
		return "success";
	}

	public String viewByIdCategory() throws Exception {
		this.entity = getService().get(this.entity);
		Category localCategory = (Category) this.entity;
		if ("category".equals(localCategory.getType())) {
			PageRequest localObject = new PageRequest(
					" and primary.parentCategory.categoryId=" + localCategory.getCategoryId() + " and primary.status=1");
			((PageRequest) localObject).setSortColumns(" order by primary.sortNo");
			Page localPage = getService().getPage((PageRequest) localObject, new Category());
			this.request.setAttribute("categoryList", localPage.getResult());
		}
		if (localCategory.getCategoryId().longValue() == -1L) {
			this.request.setAttribute("belongModuleMap", null);
		} else if (localCategory.getParentCategory().getCategoryId().longValue() == -1L) {
			this.request.setAttribute("belongModuleMap", getBelongModuleMap("-1"));
		} else {
			this.request.setAttribute("belongModuleMap", getBelongModuleMap(localCategory.getParentCategory().getCategoryId() + ""));
		}
		Object localObject = new SystemProps();
		((SystemProps) localObject).setPropsKey("Offset");
		localObject = (SystemProps) getService().get((StandardModel) localObject);
		double d = Tools.strToDouble(((SystemProps) localObject).getRunTimeValue(), 20.0D);
		this.request.setAttribute("OffSet", Tools.fmtDouble2(Arith.mul(d, 100.0F)));
		this.request.setAttribute("propSet", localCategory.getPropertySet());
		this.request.setAttribute("categoryId", this.entity.fetchPKey().getValue());
		return "success";
	}

	public String updateCategory() throws Exception {
		Category category1 = (Category) entity;
		Category category2 = (Category) getService().get(entity);
		category1.setChildCategorySet(category2.getChildCategorySet());
		category1.setBreedSet(category2.getBreedSet());
		if ("N".equals(category1.getIsOffSet()))
			category1.setOffSetRate_v(new BigDecimal(0));
		String as[] = request.getParameterValues("belongModule");
		if (category2.getCategoryId().longValue() == -1L) {
			getService().update(category1);
			addReturnValue(1, 0x1d45eL);
		} else {
			Map map = getBelongModuleMap("-1");
			Map map1 = getBelongModuleMap((new StringBuilder()).append(category2.getParentCategory().getCategoryId()).append("").toString());
			boolean flag = true;
			if (as != null && as.length > 0) {
				String s = "";
				String as1[] = as;
				int i = as1.length;
				for (int j = 0; j < i; j++) {
					String s1 = as1[j];
					if (!"".equals(s))
						s = (new StringBuilder()).append(s).append("|").toString();
					s = (new StringBuilder()).append(s).append(s1).toString();
					if (category2.getParentCategory().getCategoryId().longValue() == -1L) {
						if (map.get(Integer.valueOf(Tools.strToInt(s1))) != null)
							continue;
						flag = false;
						break;
					}
					if (map1.get(Integer.valueOf(Tools.strToInt(s1))) != null)
						continue;
					flag = false;
					break;
				}

				category1.setBelongModule(s);
			} else {
				category1.setBelongModule("");
			}
			if (!flag) {
				addReturnValue(-1, 0x1b3f5L);
				writeOperateLog(1071, (new StringBuilder()).append("添加名称为：").append(category2.getCategoryName()).append("的分类").toString(), 0,
						ApplicationContextInit.getErrorInfo("111605"));
			} else {
				if (category1.getParentCategory().getCategoryId().longValue() == -1L) {
					if (!category1.getBelongModule().equals(category2.getBelongModule())) {
						List list = getCategoryListByCategoryId(category1, category1.getBelongModule());
						List list2 = getBreedListByCategoryId(category1, category1.getBelongModule());
						updateCategoryByCategory(category1, list);
						updateBreedByCategory(category1, list2);
					}
				} else if (!category1.getBelongModule().equals(category2.getBelongModule())) {
					List list1 = getBreedListByCategoryId(category1, category1.getBelongModule());
					updateBreedByCategory(category1, list1);
				}
				getService().update(category1);
				addReturnValue(1, 0x1d45eL);
			}
			if (category1.getParentCategory().getCategoryId().longValue() == -1L)
				request.setAttribute("belongModuleMap", map);
			else
				request.setAttribute("belongModuleMap", map1);
		}
		if ("category".equals(category2.getType())) {
			PageRequest pagerequest = new PageRequest((new StringBuilder()).append(" and primary.parentCategory.categoryId=")
					.append(category2.getCategoryId()).append(" and primary.status=1").toString());
			pagerequest.setSortColumns(" order by primary.sortNo");
			Page page = getService().getPage(pagerequest, new Category());
			request.setAttribute("categoryList", page.getResult());
		}
		SystemProps systemprops = new SystemProps();
		systemprops.setPropsKey("Offset");
		systemprops = (SystemProps) getService().get(systemprops);
		double d = Tools.strToDouble(systemprops.getRunTimeValue(), 20D);
		request.setAttribute("OffSet", Tools.fmtDouble2(Arith.mul(d, 100F)));
		request.setAttribute("propSet", category2.getPropertySet());
		request.setAttribute("categoryId", category1.getCategoryId());
		writeOperateLog(1071, (new StringBuilder()).append("修改代码为：").append(category2.getCategoryId()).append("的分类").toString(), 1, "");
		entity = category2;
		return "success";
	}

	public String removeCategory() {
		this.logger.debug("enter removeCategory");
		int i = 1;
		Category localCategory1 = (Category) getService().get(this.entity);
		int j = 0;
		Object localObject1;
		Object localObject2;
		if ((localCategory1.getChildCategorySet() != null) && (localCategory1.getChildCategorySet().size() > 0)) {
			localObject1 = localCategory1.getChildCategorySet().iterator();
			while (((Iterator) localObject1).hasNext()) {
				localObject2 = (Category) ((Iterator) localObject1).next();
				if (((Category) localObject2).getStatus().intValue() == 1) {
					j = 1;
					break;
				}
			}
		}
		if (j != 0) {
			addReturnValue(-1, 111601L);
			writeOperateLog(1071, "删除代码为：" + localCategory1.getCategoryId() + "的分类", 0, ApplicationContextInit.getErrorInfo("-1007"));
		} else {
			if ((localCategory1.getBreedSet() != null) && (localCategory1.getBreedSet().size() > 0)) {
				localObject1 = localCategory1.getBreedSet().iterator();
				while (((Iterator) localObject1).hasNext()) {
					localObject2 = (Breed) ((Iterator) localObject1).next();
					if (((Breed) localObject2).getStatus().intValue() == 1) {
						i = 0;
						break;
					}
				}
			} else {
				i = 1;
			}
			if (i == 0) {
				addReturnValue(-1, 111602L);
				writeOperateLog(1071, "删除代码为：" + localCategory1.getCategoryId() + "的分类", 0, ApplicationContextInit.getErrorInfo("-1012"));
			} else {
				localCategory1.setStatus(Integer.valueOf(2));
				getService().update(localCategory1);
				System.out.println("已删除子分类的名称：" + localCategory1.getCategoryName() + " 已删除子分类的状态：" + localCategory1.getStatus() + " 已删除子分类的类型："
						+ localCategory1.getType());
				if (localCategory1.getParentCategory().getCategoryId().longValue() != -1L) {
					localObject1 = localCategory1.getParentCategory();
					System.out.println("分类的分类号：" + ((Category) localObject1).getCategoryId() + " name：" + ((Category) localObject1).getCategoryName()
							+ " type：" + ((Category) localObject1).getType());
					if ((((Category) localObject1).getChildCategorySet() == null) || (((Category) localObject1).getChildCategorySet().size() <= 0)) {
						((Category) localObject1).setType("leaf");
						getService().update((StandardModel) localObject1);
					} else {
						if ((((Category) localObject1).getChildCategorySet() != null)
								&& (((Category) localObject1).getChildCategorySet().size() > 0)) {
							localObject2 = ((Category) localObject1).getChildCategorySet().iterator();
							while (((Iterator) localObject2).hasNext()) {
								Category localCategory2 = (Category) ((Iterator) localObject2).next();
								if (localCategory2.getStatus().intValue() == 1) {
									i = 0;
									break;
								}
							}
						} else {
							i = 1;
						}
						if (i == 0) {
							writeOperateLog(1071, "删除代码为：" + localCategory1.getCategoryId() + "的分类", 1, "");
						} else {
							((Category) localObject1).setType("leaf");
							getService().update((StandardModel) localObject1);
						}
					}
				}
				addReturnValue(1, 111609L);
				writeOperateLog(1071, "删除代码为：" + localCategory1.getCategoryId() + "的分类", 1, "");
			}
		}
		return "success";
	}

	public String getCommodityInfoByCategoryId() {
		this.logger.debug("---------通过分类ID获取其分类下的子分类以及品名------------getCommodityInfoByCategoryId------------");
		long l = Tools.strToLong(this.request.getParameter("categoryId"), -1000L);
		String str1 = this.request.getParameter("module");
		System.out.println(str1);
		this.jsonReturn = new JSONArray();
		Category localCategory1 = new Category();
		localCategory1.setCategoryId(Long.valueOf(l));
		localCategory1 = (Category) getService().get(localCategory1);
		if ((localCategory1 != null) && (localCategory1.getCategoryId().longValue() != -1L) && (localCategory1.getStatus().intValue() == 1)) {
			Object localObject1;
			if (localCategory1.getParentCategory().getCategoryId().longValue() == -1L) {
				localObject1 = new ArrayList();
				if ((localCategory1.getChildCategorySet() != null) && (localCategory1.getChildCategorySet().size() > 0)) {
					Iterator localObject2 = localCategory1.getChildCategorySet().iterator();
					while (((Iterator) localObject2).hasNext()) {
						Category localCategory2 = (Category) ((Iterator) localObject2).next();
						if ((localCategory2.getStatus().intValue() == 1) && (localCategory2.getBelongModule() != null)
								&& (!"".equals(localCategory2.getBelongModule()))) {
							String[] arrayOfString1 = localCategory2.getBelongModule().split("\\/");
							int i = 1;
							for (String str2 : arrayOfString1) {
								if (!str1.contains(str2)) {
									i = 0;
									break;
								}
							}
							if (i == 0) {
								((List) localObject1).add(localCategory2);
							}
						}
					}
				}
				Object localObject2 = getBreedListByCategoryId(localCategory1, str1);
				this.jsonReturn.add(Integer.valueOf(((List) localObject1).size()));
				this.jsonReturn.add(Integer.valueOf(((List) localObject2).size()));
			} else {
				localObject1 = getBreedListByCategoryId(localCategory1, str1);
				this.jsonReturn.add(Integer.valueOf(0));
				this.jsonReturn.add(Integer.valueOf(((List) localObject1).size()));
			}
		}
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
					if ("-1".equals(paramString)) {
						localTradeModule.setAddFirmFn((String) localMap.get("addFirmFn"));
						localTradeModule.setCnName((String) localMap.get("cnName"));
						localTradeModule.setDelFirmFn((String) localMap.get("delFirmFn"));
						localTradeModule.setEnName((String) localMap.get("enName"));
						localTradeModule.setIsFirmSet((String) localMap.get("isFirmSet"));
						localTradeModule.setModuleId(localInteger);
						localTradeModule.setShortName((String) localMap.get("shortName"));
						localTradeModule.setUpdateFirmStatusFn((String) localMap.get("updateFirmStatusFn"));
						localLinkedHashMap.put(localInteger, localTradeModule);
					} else {
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
		}
		return localLinkedHashMap;
	}

	private List<Category> getCategoryListByCategoryId(Category paramCategory, String paramString) {
		ArrayList localArrayList = new ArrayList();
		if ((paramCategory.getChildCategorySet() != null) && (paramCategory.getChildCategorySet().size() > 0)) {
			Iterator localIterator = paramCategory.getChildCategorySet().iterator();
			while (localIterator.hasNext()) {
				Category localCategory = (Category) localIterator.next();
				if ((localCategory.getStatus().intValue() == 1) && (localCategory.getBelongModule() != null)
						&& (!"".equals(localCategory.getBelongModule()))) {
					String[] arrayOfString1 = localCategory.getBelongModule().split("\\|");
					int i = 1;
					for (String str : arrayOfString1) {
						if (!paramString.contains(str)) {
							i = 0;
							break;
						}
					}
					if (i == 0) {
						localArrayList.add(localCategory);
					}
				}
			}
		}
		return localArrayList;
	}

	private List<Breed> getBreedListByCategoryId(Category paramCategory, String paramString) {
		ArrayList localArrayList = new ArrayList();
		Iterator localIterator;
		Object localObject1;
		int j;
		if ((paramCategory.getChildCategorySet() != null) && (paramCategory.getChildCategorySet().size() > 0)) {
			localIterator = paramCategory.getChildCategorySet().iterator();
			while (localIterator.hasNext()) {
				localObject1 = (Category) localIterator.next();
				if ((((Category) localObject1).getStatus().intValue() == 1) && (((Category) localObject1).getBreedSet() != null)
						&& (((Category) localObject1).getBreedSet().size() > 0)) {
					Iterator localObject2 = ((Category) localObject1).getBreedSet().iterator();
					while (((Iterator) localObject2).hasNext()) {
						Breed localBreed = (Breed) ((Iterator) localObject2).next();
						if ((localBreed.getStatus().intValue() == 1) && (localBreed.getBelongModule() != null)
								&& (!"".equals(localBreed.getBelongModule()))) {
							String[] localObject3 = localBreed.getBelongModule().split("\\|");
							j = 1;
							for (CharSequence localCharSequence2 : localObject3) {
								if (!paramString.contains(localCharSequence2)) {
									j = 0;
									break;
								}
							}
							if (j == 0) {
								localArrayList.add(localBreed);
							}
						}
					}
				}
			}
		}
		if ((paramCategory.getBreedSet() != null) && (paramCategory.getBreedSet().size() > 0)) {
			localIterator = paramCategory.getBreedSet().iterator();
			while (localIterator.hasNext()) {
				localObject1 = (Breed) localIterator.next();
				if ((((Breed) localObject1).getStatus().intValue() == 1) && (((Breed) localObject1).getBelongModule() != null)
						&& (!"".equals(((Breed) localObject1).getBelongModule()))) {
					String[] localObject2 = ((Breed) localObject1).getBelongModule().split("\\|");
					int i = 1;
					for (CharSequence localCharSequence1 : localObject2) {
						if (!paramString.contains(localCharSequence1)) {
							i = 0;
							break;
						}
					}
					if (i == 0) {
						localArrayList.add(localObject1);
					}
				}
			}
		}
		return localArrayList;
	}

	private void updateCategoryByCategory(Category paramCategory, List<Category> paramList) {
		if ((paramList != null) && (paramList.size() > 0)) {
			Iterator localIterator = paramList.iterator();
			while (localIterator.hasNext()) {
				Category localCategory = (Category) localIterator.next();
				String str1 = paramCategory.getBelongModule();
				if ((str1 != null) && (!"".equals(str1)) && (localCategory.getBelongModule() != null)) {
					String[] arrayOfString1 = str1.split("\\|");
					String str2 = "";
					for (String str3 : arrayOfString1) {
						if (localCategory.getBelongModule().contains(str3)) {
							if (!"".equals(str2)) {
								str2 = str2 + "|";
							}
							str2 = str2 + str3;
						}
					}
					if (!localCategory.getBelongModule().equals(str2)) {
						localCategory.setBelongModule(str2);
						getService().update(localCategory);
					}
				} else {
					localCategory.setBelongModule("");
					getService().update(localCategory);
				}
			}
		}
	}

	private void updateBreedByCategory(Category paramCategory, List<Breed> paramList) {
		if ((paramList != null) && (paramList.size() > 0)) {
			Iterator localIterator = paramList.iterator();
			while (localIterator.hasNext()) {
				Breed localBreed = (Breed) localIterator.next();
				String str1 = paramCategory.getBelongModule();
				if ((str1 != null) && (!"".equals(str1)) && (localBreed.getBelongModule() != null)) {
					String[] arrayOfString1 = str1.split("\\|");
					String str2 = "";
					for (String str3 : arrayOfString1) {
						if (localBreed.getBelongModule().contains(str3)) {
							if (!"".equals(str2)) {
								str2 = str2 + "|";
							}
							str2 = str2 + str3;
						}
					}
					if (!localBreed.getBelongModule().equals(str2)) {
						localBreed.setBelongModule(str2);
						getService().update(localBreed);
					}
				} else {
					localBreed.setBelongModule("");
					getService().update(localBreed);
				}
			}
		}
	}
}
