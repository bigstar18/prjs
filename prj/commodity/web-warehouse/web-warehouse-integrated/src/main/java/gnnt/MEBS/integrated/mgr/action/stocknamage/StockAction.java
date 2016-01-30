package gnnt.MEBS.integrated.mgr.action.stocknamage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.bill.core.po.GoodsPropertyPO;
import gnnt.MEBS.bill.core.service.IKernelService;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.commoditymanage.Breed;
import gnnt.MEBS.integrated.mgr.model.commoditymanage.BreedProps;
import gnnt.MEBS.integrated.mgr.model.commoditymanage.Category;
import gnnt.MEBS.integrated.mgr.model.commoditymanage.CategoryProperty;
import gnnt.MEBS.integrated.mgr.model.commoditymanage.PropertyType;
import gnnt.MEBS.integrated.mgr.model.stockmanage.OutStock;
import gnnt.MEBS.integrated.mgr.model.stockmanage.Stock;
import gnnt.MEBS.integrated.mgr.model.stockmanage.StockGoodsProperty;
import gnnt.MEBS.integrated.mgr.service.writeopreatelog.WriteOperateLogService;
import net.sf.json.JSONArray;

@Controller("stockAction")
@Scope("request")
public class StockAction extends EcsideAction {
	private static final long serialVersionUID = 3298008836351938195L;
	@Autowired
	@Qualifier("billKernelService")
	private IKernelService kernelService;
	@Autowired
	@Qualifier("writeOperateLogService")
	private WriteOperateLogService writeOperateLogService;
	@Resource(name = "stockStatusMap")
	private Map<String, String> stockStatusMap;
	private List<StockGoodsProperty> goodsPropertys;
	private JSONArray jsonReturn;

	public IKernelService getKernelService() {
		return this.kernelService;
	}

	public void setKernelService(IKernelService kernelService) {
		this.kernelService = kernelService;
	}

	public List<StockGoodsProperty> getGoodsPropertys() {
		return this.goodsPropertys;
	}

	public void setGoodsPropertys(List<StockGoodsProperty> goodsPropertys) {
		this.goodsPropertys = goodsPropertys;
	}

	public Map<String, String> getStockStatusMap() {
		return this.stockStatusMap;
	}

	public JSONArray getJsonReturn() {
		return this.jsonReturn;
	}

	public String getList() throws Exception {
		this.logger.debug("==可用仓单列表查询===enter getList===");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
		QueryConditions qc = new QueryConditions();
		qc = (QueryConditions) pageRequest.getFilters();
		qc.addCondition("warehouseID", "=", user.getWarehouseID());

		qc.addCondition("stockStatus", "in", "(0,1)");

		pageRequest.setSortColumns(" order by  createTime desc");
		listByLimit(pageRequest);

		return "success";
	}

	public String getStockDetails() throws Exception {
		super.viewById();
		Stock stock = (Stock) this.entity;
		if (stock != null) {
			putStockPropertys(stock.getGoodsProperties());
			if ((stock.getStockStatus().intValue() == 2) || (stock.getStockStatus().intValue() == 5)) {
				QueryConditions qc = new QueryConditions();
				qc.addCondition("stockID", "=", stock.getStockId());
				qc.addCondition(" ", " ", " (status=0 or status=2) ");
				PageRequest<QueryConditions> pageRequest = new PageRequest(1, 2, qc, " order by createTime desc ");
				Page<StandardModel> page = getService().getPage(pageRequest, new OutStock());
				if ((page != null) && (page.getResult() != null) && (page.getResult().size() > 0)) {
					this.request.setAttribute("outStock", page.getResult().get(0));
				}
			}
		}
		return "success";
	}

	public String addStockForward() {
		this.logger.debug("---录入仓单跳转  ------------addStockForward---");

		User user = (User) this.request.getSession().getAttribute("CurrentUser");

		PageRequest<String> pageRequest = new PageRequest(" and primary.type='leaf' and primary.status=1 ");
		pageRequest.setPageSize(100000);
		pageRequest.setSortColumns("order by primary.parentCategory.sortNo,primary.sortNo");
		Page<StandardModel> page = getService().getPage(pageRequest, new Category());
		List<StandardModel> categoryList = page.getResult();
		TreeSet<Category> categories = new TreeSet(new Comparator<Category>() {
			public int compare(Category o1, Category o2) {
				if ((o1.getParentCategory().getCategoryId().longValue() == -1L) && (o2.getParentCategory().getCategoryId().longValue() != -1L)) {
					if (o1.getSortNo().longValue() > o2.getParentCategory().getSortNo().longValue()) {
						return 1;
					}
					return -1;
				}
				if ((o1.getParentCategory().getCategoryId().longValue() != -1L) && (o2.getParentCategory().getCategoryId().longValue() == -1L)) {
					if (o1.getParentCategory().getSortNo().longValue() < o2.getSortNo().longValue()) {
						return -1;
					}
					return 1;
				}
				if ((o1.getParentCategory().getCategoryId().longValue() == -1L) && (o2.getParentCategory().getCategoryId().longValue() == -1L)) {
					if (o1.getSortNo().longValue() > o2.getSortNo().longValue()) {
						return 1;
					}
					return -1;
				}
				if ((o1.getParentCategory().getCategoryId().longValue() != -1L) && (o2.getParentCategory().getCategoryId().longValue() != -1L)) {
					if (o1.getParentCategory().getSortNo().longValue() < o2.getParentCategory().getSortNo().longValue()) {
						return -1;
					}
					if (o1.getParentCategory().getSortNo() == o2.getParentCategory().getSortNo()) {
						if (o1.getSortNo().longValue() > o2.getSortNo().longValue()) {
							return 1;
						}
						return -1;
					}
					if (o1.getParentCategory().getSortNo().longValue() > o2.getParentCategory().getSortNo().longValue()) {
						return 1;
					}
				}
				return 1;
			}
		});
		for (StandardModel model : categoryList) {
			categories.add((Category) model);
		}
		this.request.setAttribute("categoryList", categories);

		this.request.setAttribute("warehouseID", user.getWarehouseID());
		return "success";
	}

	public String addStock() throws Exception {
		this.logger.debug("---录入仓单 ------------addStock--");

		User currUser = (User) this.request.getSession().getAttribute("CurrentUser");
		Stock stock = (Stock) this.entity;
		stock.setStockStatus(Integer.valueOf(0));
		stock.setCreateTime(getService().getSysDate());
		stock.setWarehouseId(currUser.getWarehouseID());
		System.out.println(stock.getWarehouseId());

		GoodsPropertyPO goodsPropertyPO = null;

		List<GoodsPropertyPO> propertylist = new ArrayList();
		if (this.goodsPropertys != null) {
			for (StockGoodsProperty goodsProperty : this.goodsPropertys) {
				goodsPropertyPO = new GoodsPropertyPO();

				goodsPropertyPO.setPropertyName(goodsProperty.getPropertyName());
				goodsPropertyPO.setPropertyValue(goodsProperty.getPropertyValue());
				goodsPropertyPO.setPropertyTypeID(goodsProperty.getPropertyTypeID().longValue());
				System.out.println("PropertyName=======:" + goodsPropertyPO.getPropertyName() + "<--->" + "  PropertyValue===:"
						+ goodsPropertyPO.getPropertyValue());

				propertylist.add(goodsPropertyPO);
			}
		}
		ResultVO result = this.kernelService.addStock(stock.getStockPO(), propertylist);
		if (result.getResult() > 0L) {
			addReturnValue(1, 122001L);

			this.writeOperateLogService.writeOperateLog(1211, currUser, "录入仓单，仓库原始凭证号：" + stock.getRealStockCode() + ",所属交易商：" + stock.getOwnerFirm(),
					1, "");
		} else {
			addReturnValue(-1, -122001L, new Object[] { result.getErrorInfo() });

			this.writeOperateLogService.writeOperateLog(1211, currUser, "录入仓单，仓库原始凭证号：" + stock.getRealStockCode() + ",所属交易商：" + stock.getOwnerFirm(),
					0, result.getErrorDetailInfo());
		}
		return "success";
	}

	public String getBreedByCategoryID() {
		this.logger.debug("---通过商品分类ID查询品种信息并加入到json中-getBreedByCategoryID--");
		this.jsonReturn = new JSONArray();
		long categoryID = Tools.strToLong(this.request.getParameter("categoryId"), -1000L);
		if (categoryID < 0L) {
			return "success";
		}
		PageRequest<String> pageRequest = new PageRequest(" and primary.type='leaf' and primary.status=1 ");
		pageRequest.setSortColumns(" order by sortNo");
		Page<StandardModel> page = getService().getPage(pageRequest, new Category());
		if ((page.getResult() != null) && (page.getResult().size() > 0)) {
			for (StandardModel model : page.getResult()) {
				Category category = (Category) model;
				if (categoryID == category.getCategoryId().longValue()) {
					for (Breed breed : category.getBreedSet()) {
						if (breed.getStatus().intValue() == 1) {
							JSONArray breedArray = new JSONArray();
							breedArray.add(breed.getBreedId());
							breedArray.add(breed.getBreedName());
							breedArray.add(breed.getUnit());
							this.jsonReturn.add(breedArray);
						}
					}
					break;
				}
			}
		}
		return "success";
	}

	public String getPropertyValueByBreedID() {
		logger.debug("通过品名编号获取品名属性信息");
		long breedId = Tools.strToLong(request.getParameter("breedId"), -1000L);
		jsonReturn = new JSONArray();
		PageRequest pageRequest = new PageRequest(" and primary.type='leaf' and primary.status=1");
		pageRequest.setSortColumns(" order by sortNo");
		Page page = getService().getPage(pageRequest, new Category());
		if (page.getResult() != null && page.getResult().size() > 0) {
			for (Iterator iterator = page.getResult().iterator(); iterator.hasNext();) {
				StandardModel model = (StandardModel) iterator.next();
				Category category = (Category) model;
				for (Iterator iterator1 = category.getBreedSet().iterator(); iterator1.hasNext();) {
					Breed breed = (Breed) iterator1.next();
					if (breedId == breed.getBreedId().longValue()) {
						JSONArray propertyjson;
						for (Iterator iterator2 = category.getPropertySet().iterator(); iterator2.hasNext(); jsonReturn.add(propertyjson)) {
							CategoryProperty categoryProperty = (CategoryProperty) iterator2.next();
							propertyjson = new JSONArray();
							propertyjson.add(categoryProperty.getPropertyName());
							propertyjson.add(categoryProperty.getHasValueDict());
							if ("Y".equalsIgnoreCase(categoryProperty.getHasValueDict())) {
								JSONArray valuejson = new JSONArray();
								for (Iterator iterator3 = breed.getPropsSet().iterator(); iterator3.hasNext();) {
									BreedProps bProps = (BreedProps) iterator3.next();
									if (bProps.getCategoryProperty().getPropertyId() == categoryProperty.getPropertyId())
										valuejson.add(bProps.getPropertyValue());
								}

								propertyjson.add(valuejson);
							}
							propertyjson.add(categoryProperty.getIsNecessary());
							propertyjson.add(categoryProperty.getFieldType());
						}

						logger.debug("获取属性名称或者值");
					}
				}

			}

		}
		return "success";
	}

	public String getPropertyPagValueByBreedID() {
		this.logger.debug("通过品名编号查询品名属性信息");
		long breedId = Tools.strToLong(this.request.getParameter("breedId"), -1000L);
		if (breedId > 0L) {
			Breed breed = new Breed();
			breed.setBreedId(Long.valueOf(breedId));
			breed = (Breed) getService().get(breed);
			if (breed != null) {
				QueryConditions ptqc = new QueryConditions();
				ptqc.addCondition("status", "=", Integer.valueOf(0));
				PageRequest<QueryConditions> ptpageRequest = new PageRequest(1, 100, ptqc, " order by sortNo ");
				Page<StandardModel> ptpage = getService().getPage(ptpageRequest, new PropertyType());

				Map<Long, String> ptnameMap = new HashMap();

				Map<Long, Map<Long, List<StandardModel>>> ptmap = new LinkedHashMap();
				if ((ptpage != null) && (ptpage.getResult() != null)) {
					for (int i = 0; i < ptpage.getResult().size(); i++) {
						PropertyType pt = (PropertyType) ptpage.getResult().get(i);
						ptmap.put(pt.getPropertyTypeID(), new LinkedHashMap());
						ptnameMap.put(pt.getPropertyTypeID(), pt.getName());
					}
				}
				Long l = Long.valueOf(-1L);
				ptmap.put(l, new LinkedHashMap());
				ptnameMap.put(l, "其它属性");

				Set<CategoryProperty> cpSet = breed.getCategory().getPropertySet();
				Map<Long, CategoryProperty> categoryPropertyMap = new HashMap();
				for (CategoryProperty cp : cpSet) {
					Map<Long, List<StandardModel>> m = (Map) ptmap.get(cp.getPropertyTypeID());
					if (m == null) {
						m = (Map) ptmap.get(l);
					}
					m.put(cp.getPropertyId(), new ArrayList());
					categoryPropertyMap.put(cp.getPropertyId(), cp);
				}
				Map<Long, List<StandardModel>> cpMap;
				if (breed.getPropsSet() != null) {
					for (BreedProps bp : breed.getPropsSet()) {
						cpMap = (Map) ptmap.get(bp.getCategoryProperty().getPropertyTypeID());
						if (cpMap == null) {
							cpMap = (Map) ptmap.get(l);
						}
						List<StandardModel> bplist = (List) cpMap.get(bp.getCategoryProperty().getPropertyId());
						if (bplist == null) {
							bplist = new ArrayList();
							cpMap.put(bp.getCategoryProperty().getPropertyId(), bplist);
						}
						bplist.add(bp);
					}
				}
				Map<Long, Map<Long, List<StandardModel>>> map = new LinkedHashMap();
				for (Long key : ptmap.keySet()) {
					if (((Map) ptmap.get(key)).size() > 0) {
						map.put(key, (Map) ptmap.get(key));
					}
				}
				this.request.setAttribute("ptnameMap", ptnameMap);
				this.request.setAttribute("propertyMap", map);
				this.request.setAttribute("categoryPropertyMap", categoryPropertyMap);
			}
		}
		return "success";
	}

	private void putStockPropertys(Set<StockGoodsProperty> goodsProperties) {
		if ((goodsProperties != null) && (goodsProperties.size() > 0)) {
			Map<PropertyType, List<StockGoodsProperty>> map = new LinkedHashMap();
			QueryConditions ptqc = new QueryConditions();
			ptqc.addCondition("status", "=", Integer.valueOf(0));
			PageRequest<QueryConditions> ptpageRequest = new PageRequest(1, 100, ptqc, " order by sortNo ");
			Page<StandardModel> ptpage = getService().getPage(ptpageRequest, new PropertyType());
			if ((ptpage != null) && (ptpage.getResult() != null)) {
				for (int i = 0; i < ptpage.getResult().size(); i++) {
					PropertyType pt = (PropertyType) ptpage.getResult().get(i);
					map.put(pt, new ArrayList());
				}
			}
			List<StockGoodsProperty> other = new ArrayList();
			List<StockGoodsProperty> list;
			for (StockGoodsProperty sgp : goodsProperties) {
				list = null;
				for (PropertyType key : map.keySet()) {
					if ((sgp.getPropertyTypeID() != null) && (sgp.getPropertyTypeID().equals(key.getPropertyTypeID()))) {
						list = (List) map.get(key);
					}
				}
				if (list == null) {
					list = other;
				}
				list.add(sgp);
			}
			Map<PropertyType, List<StockGoodsProperty>> tpmap = new LinkedHashMap();
			for (PropertyType key : map.keySet()) {
				if (((List) map.get(key)).size() > 0) {
					tpmap.put(key, (List) map.get(key));
				}
			}
			if (other.size() > 0) {
				PropertyType pt = new PropertyType();
				pt.setName("其它属性");
				pt.setPropertyTypeID(Long.valueOf(-1L));
				tpmap.put(pt, other);
			}
			this.request.setAttribute("tpmap", tpmap);
		}
	}
}
