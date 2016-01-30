package gnnt.MEBS.bill.mgr.action.stockmanage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gnnt.MEBS.bill.core.bo.StockOutApplyBO;
import gnnt.MEBS.bill.core.bo.StockOutAuditBO;
import gnnt.MEBS.bill.core.po.GoodsPropertyPO;
import gnnt.MEBS.bill.core.service.IKernelService;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.bill.mgr.model.commoditymanage.Breed;
import gnnt.MEBS.bill.mgr.model.commoditymanage.BreedProps;
import gnnt.MEBS.bill.mgr.model.commoditymanage.Category;
import gnnt.MEBS.bill.mgr.model.commoditymanage.CategoryProperty;
import gnnt.MEBS.bill.mgr.model.commoditymanage.PropertyType;
import gnnt.MEBS.bill.mgr.model.stockmanage.Dismantle;
import gnnt.MEBS.bill.mgr.model.stockmanage.GlobalLogAll;
import gnnt.MEBS.bill.mgr.model.stockmanage.GlobalLogAllHis;
import gnnt.MEBS.bill.mgr.model.stockmanage.Logistics;
import gnnt.MEBS.bill.mgr.model.stockmanage.OutStock;
import gnnt.MEBS.bill.mgr.model.stockmanage.Stock;
import gnnt.MEBS.bill.mgr.model.stockmanage.StockGoodsProperty;
import gnnt.MEBS.bill.mgr.model.stockmanage.Warehouse;
import gnnt.MEBS.bill.mgr.model.stockmanage.WarehouseLogCatalog;
import gnnt.MEBS.bill.mgr.model.warehouse.BiInvoiceinform;
import gnnt.MEBS.bill.mgr.model.warehouse.Wuser;
import gnnt.MEBS.bill.mgr.service.StockConfirmService;
import gnnt.MEBS.checkLogon.util.MD5;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.OperateLog;
import gnnt.MEBS.common.mgr.model.OperateLogHis;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.statictools.Tools;
import net.sf.json.JSONArray;

@Controller("stockAction")
@Scope("request")
public class StockAction extends EcsideAction {
	private static final long serialVersionUID = -2822059648015617297L;
	@Autowired
	@Qualifier("billKernelService")
	private IKernelService kernelService;
	@Autowired
	@Qualifier("stockConfirmService")
	private StockConfirmService stockConfirmService;
	@Resource
	private Map<Integer, String> stockStatusMap;
	@Resource
	private Map<Integer, String> warehouseStatusMap;
	@Autowired
	@Resource(name = "stockLogOperationMap")
	protected Map<Integer, String> stockLogOperationMap;
	@Autowired
	@Resource(name = "com_operatorTypeMap")
	private Map<String, String> com_operatorTypeMap;
	private Integer stockStatus;
	@Resource
	private Map<Character, String> stockOperationStatusMap;
	private Integer operationId;
	private String operation;
	private JSONArray jsonReturn;
	private String logType;
	private List<StockGoodsProperty> goodsPropertys;

	public StockConfirmService getStockConfirmService() {
		return this.stockConfirmService;
	}

	public void setStockConfirmService(StockConfirmService stockConfirmService) {
		this.stockConfirmService = stockConfirmService;
	}

	public IKernelService getKernelService() {
		return this.kernelService;
	}

	public void setKernelService(IKernelService kernelService) {
		this.kernelService = kernelService;
	}

	public String getLogType() {
		return this.logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	public Map<Integer, String> getStockLogOperationMap() {
		return this.stockLogOperationMap;
	}

	public Map<Integer, String> getWarehouseStatusMap() {
		return this.warehouseStatusMap;
	}

	public List<StockGoodsProperty> getGoodsPropertys() {
		return this.goodsPropertys;
	}

	public void setGoodsPropertys(List<StockGoodsProperty> goodsPropertys) {
		this.goodsPropertys = goodsPropertys;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Map<Integer, String> getStockStatusMap() {
		return this.stockStatusMap;
	}

	public Map<Character, String> getStockOperationStatusMap() {
		return this.stockOperationStatusMap;
	}

	public void setStockStatus(Integer stockStatus) {
		this.stockStatus = stockStatus;
	}

	public JSONArray getJsonReturn() {
		return this.jsonReturn;
	}

	public String stockListByStatus() throws Exception {
		this.logger.debug(":::::status: " + this.stockStatus);
		try {
			PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
			QueryConditions qc = (QueryConditions) pageRequest.getFilters();

			qc.addCondition("Stockstatus", "=", this.stockStatus);
			if (this.stockStatus.intValue() == 2) {
				pageRequest.setSortColumns(" order by  lastTime  desc");
			} else {
				pageRequest.setSortColumns(" order by  createTime desc,to_number(stockId)  desc");
			}
			listByLimit(pageRequest);

			int HaveWarehouse = Tools.strToInt(getKernelService().getConfigInfo("HaveWarehouse"), 0);
			this.logger.debug("有无仓库系统(0默认没有仓库1有仓库)===：" + HaveWarehouse);

			this.request.setAttribute("haveWarehouse", Integer.valueOf(HaveWarehouse));
		} catch (Exception e) {
			this.logger.error(Tools.getExceptionTrace(e));
		}
		return "success";
	}

	public String stockListByHxx() throws Exception {
		this.logger.debug(":::::status: " + this.stockStatus);
		try {
			PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
			QueryConditions qc = (QueryConditions) pageRequest.getFilters();

			qc.addCondition("Stockstatus", "=", this.stockStatus);
			if (this.stockStatus.intValue() == 2) {
				pageRequest.setSortColumns(" order by  lastTime  desc");
			} else {
				pageRequest.setSortColumns(" order by  createTime desc,to_number(stockId)  desc");
			}
			listByLimit(pageRequest);

			int HaveWarehouse = Tools.strToInt(getKernelService().getConfigInfo("HaveWarehouse"), 0);
			this.logger.debug("有无仓库系统(0默认没有仓库1有仓库)===：" + HaveWarehouse);

			this.request.setAttribute("haveWarehouse", Integer.valueOf(HaveWarehouse));
		} catch (Exception e) {
			this.logger.error(Tools.getExceptionTrace(e));
		}
		return "success";
	}

	public String addStockForward() {
		this.logger.debug("---录入仓单跳转  ------------addStockForward---");

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

		PageRequest<String> pageRequests = new PageRequest(" and status = 0 order by id ");
		Object warehousePage = getService().getPage(pageRequests, new Warehouse());

		this.request.setAttribute("warehouseList", ((Page) warehousePage).getResult());
		return "success";
	}

	public String addStock() throws Exception {
		this.logger.debug("---录入仓单 ------------addStock--");
		Stock stock = (Stock) this.entity;
		stock.setStockStatus(Integer.valueOf(0));
		stock.setCreateTime(getService().getSysDate());

		GoodsPropertyPO goodsPropertyPO = null;

		String warehouseId = this.request.getParameter("warehouseId");
		String[] ware = warehouseId.split("-");
		if ((ware != null) && (ware.length > 0)) {
			stock.setWarehouseId(ware[0]);
		}
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
			addReturnValue(1, 130101L);

			writeOperateLog(1310, "录入仓库原始凭证号为：" + stock.getRealStockCode() + ",所属交易商为：" + stock.getOwnerFirm() + "的仓单", 1, "");
		} else {
			String str = result.getErrorInfo();

			str = str.replaceAll(" ", "，");
			str = str.replaceAll(";", "。");
			addReturnValue(-1, -130101L, new Object[] { str });
			System.out.println(str);

			writeOperateLog(1310, "录入仓库原始凭证号为：" + stock.getRealStockCode() + ",所属交易商为：" + stock.getOwnerFirm() + "的仓单", 0,
					result.getErrorDetailInfo());
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

	public String registerStock() {
		this.logger.debug("enter 仓单注册");
		String stockIdStr = this.request.getParameter("stockId");
		try {
			this.logger.debug("注册仓单，仓单编号：" + stockIdStr);
			if ((stockIdStr != null) && (stockIdStr.length() > 0)) {
				ResultVO result = this.kernelService.registerStock(stockIdStr);
				if (result.getResult() == 1L) {
					addReturnValue(1, 130102L, new Object[] { stockIdStr });

					writeOperateLog(1310, "注册仓单" + stockIdStr, 1, null);
				} else {
					addReturnValue(-1, 999999L, new Object[] { result.getErrorInfo() });

					writeOperateLog(1310, "注册仓单" + stockIdStr, 0, result.getErrorInfo());
				}
			}
			return "success";
		} catch (Exception e) {
			addReturnValue(-1, -9000L, new Object[] { "注册仓单操作" });

			writeOperateLog(1310, "注册仓单号为：" + stockIdStr + "的仓单", 0, e.getMessage());
			this.logger.error(Tools.getExceptionTrace(e));
		}
		return "SysException";
	}

	public String dismantleStock() {
		this.logger.debug("仓单拆单！！！");
		String stockIdStr = this.request.getParameter("stockID");

		String[] amountArrStr = this.request.getParameterValues("amount");
		if ((stockIdStr != null) && (amountArrStr != null)) {
			Double[] amountArr = new Double[amountArrStr.length];
			for (int i = 0; i < amountArrStr.length; i++) {
				this.logger.debug("amountArr::" + amountArr);
				amountArr[i] = Double.valueOf(amountArrStr[i]);
			}
			try {
				ResultVO result = this.kernelService.dismantleStock(stockIdStr, amountArr);
				this.logger.debug("进了没！！" + result);
				if (result.getResult() == 1L) {
					this.logger.debug("申请拆单成功！！！");
					addReturnValue(1, 130103L, new Object[] { stockIdStr });

					writeOperateLog(1310, "仓单号为：" + stockIdStr + "的仓单拆单申请", 1, null);
				} else {
					this.logger.debug("申请拆单失败！！" + result.getErrorCode());
					addReturnValue(-1, -130103L, new Object[] { result.getErrorCode() });

					writeOperateLog(1310, "仓单号为：" + stockIdStr + "的仓单拆单申请", 0, result.getErrorInfo());
				}
				return "success";
			} catch (Exception e) {
				addReturnValue(-1, -10004L, new Object[] { "拆仓单操作" });

				writeOperateLog(1310, "仓单号为：" + stockIdStr + "的仓单拆单申请", 0, e.getMessage());
				this.logger.error(Tools.getExceptionTrace(e));
				return "SysException";
			}
		}
		this.logger.debug(((stockIdStr != null) && (amountArrStr != null)) + "==================");
		return "SysException";
	}

	public String forwardStockOut() throws Exception {
		this.logger.debug("enter forwardStockOut");
		viewById();

		int HaveWarehouse = Tools.strToInt(getKernelService().getConfigInfo("HaveWarehouse"), 0);
		this.request.setAttribute("HaveWarehouse", Integer.valueOf(HaveWarehouse));
		return "success";
	}

	public String stockOutBase() {
		String stockId = this.request.getParameter("stockId");

		int HaveWarehouse = Tools.strToInt(getKernelService().getConfigInfo("HaveWarehouse"), 0);
		try {
			if (HaveWarehouse == 1) {
				stockOut();
			} else {
				stockOutApply();
			}
			return "success";
		} catch (Exception e) {
			addReturnValue(-1, -9000L, new Object[] { "仓单出库操作" });

			writeOperateLog(1310, "仓单号为：" + stockId + "的仓单出库", 0, e.getMessage());
			this.logger.error(Tools.getExceptionTrace(e));
		}
		return "error";
	}

	public void stockOut() {
		String stockId = this.request.getParameter("stockId");

		String userId = this.request.getParameter("userId");
		String userName = this.request.getParameter("userName");
		String password = this.request.getParameter("password");
		this.logger.debug("有仓库版仓单出库===仓单号：" + stockId + "编号：" + userId + "账户号：" + userName + "验证码：" + password);
		if ((stockId != null) && (stockId.length() > 0) && (userId != null) && (userId.length() > 0) && (userName != null) && (userName.length() > 0)
				&& (password != null) && (password.length() > 0)) {
			ResultVO result = this.kernelService.stockOut(stockId, userId, userName, password);
			this.logger.debug("=============== key:" + result.getResult());
			if (result.getResult() > 0L) {
				addReturnValue(1, 130110L, new Object[] { stockId });

				writeOperateLog(1310, "仓单号为：" + stockId + "的仓单出库", 1, null);
			} else {
				addReturnValue(-1, 999999L, new Object[] { result.getErrorInfo().replace("\n", "") });

				writeOperateLog(1310, "仓单号为：" + stockId + "的仓单出库", 0, result.getErrorInfo().replace("\n", ""));
			}
			this.logger.debug("=============== key:::" + result.getErrorCode() + ",Map::" + result.getInfoMap());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void stockOutApply() {
		String stockId = this.request.getParameter("stockId");
		this.logger.debug("无仓库版仓单出库申请===仓单号：" + stockId);
		if ((stockId != null) && (stockId.length() > 0)) {
			String deliveryPerson = this.request.getParameter("deliveryPerson");

			String idnumber = this.request.getParameter("idnumber");

			String address = this.request.getParameter("address");
			String phone = this.request.getParameter("phone");
			String deliveryStatus = this.request.getParameter("deliveryStatus");

			BiInvoiceinform invoice = getInvoiceInfo();
			String errorinfo = "";
			if (deliveryStatus.equals("0")) {
				errorinfo = errorinfo + validateId(idnumber);
			}
			errorinfo = errorinfo + validateInvoice(invoice);
			if (!errorinfo.equals("")) {
				addReturnValue(-1, 999999L, new Object[] { errorinfo });
				return;
			}
			StockOutApplyBO stockOutApplyBO = new StockOutApplyBO();
			if (deliveryStatus.equals("0")) {
				stockOutApplyBO.setStockID(stockId);
				stockOutApplyBO.setIdnumber(idnumber);
				stockOutApplyBO.setDeliveryPerson(deliveryPerson);
				stockOutApplyBO.setAddress(null);
				stockOutApplyBO.setPhone(null);
				stockOutApplyBO.setDeliveryStatus(deliveryStatus);
			} else {
				stockOutApplyBO.setStockID(stockId);
				stockOutApplyBO.setIdnumber(null);
				stockOutApplyBO.setDeliveryPerson(deliveryPerson);
				stockOutApplyBO.setAddress(address);
				stockOutApplyBO.setPhone(phone);
				stockOutApplyBO.setDeliveryStatus(deliveryStatus);
			}
			ResultVO result = this.kernelService.stockOutApply(stockOutApplyBO);
			if ((result.getResult() != -1L) && (result.getResult() != 0L)) {
				saveInvoiceInfo(invoice, stockId);

				addReturnValue(1, 130104L, new Object[] { stockId, Long.valueOf(result.getResult()) });
			} else {
				addReturnValue(-1, 999999L, new Object[] { result.getErrorInfo().replace("\n", "") });
			}
			this.logger.debug("=============== key:::" + result.getErrorCode() + ",Map::" + result.getInfoMap());
		}
	}

	private String validateId(String idnumber) {
		String errorinfo = "";

		String regx = "([0-9]{17}([0-9]|X|x))|([0-9]{15})";
		Pattern pattern = Pattern.compile(regx);
		if (!pattern.matcher(idnumber).matches()) {
			errorinfo = errorinfo + " 身份证号码不合法！";
		}
		return errorinfo;
	}

	private String validateInvoice(BiInvoiceinform invoice) {
		if (invoice == null) {
			return "";
		}
		String errorinfo = "";
		if ((invoice.getName() == null) || (invoice.getName() == "")) {
			String regx = "(1[3|4|5|7|8][0-9]{9})|([0-9]{3,4}-[0-9]{7,8})";
			Pattern pattern = Pattern.compile(regx);
			if (!pattern.matcher(invoice.getPhone()).matches()) {
				errorinfo = errorinfo + " 注册号码不合法！";
			}
		} else {
			String regx = "(1[3|4|5|7|8][0-9]{9})";
			Pattern pattern = Pattern.compile(regx);
			if (!pattern.matcher(invoice.getPhone()).matches()) {
				errorinfo = errorinfo + " 手机号码不合法！";
			}
		}
		return errorinfo;
	}

	private BiInvoiceinform getInvoiceInfo() {
		BiInvoiceinform invoice = null;
		String invoiceStatusStr = this.request.getParameter("invoiceStatus");
		int invoiceStatus = Integer.valueOf(invoiceStatusStr).intValue();
		if (invoiceStatus != 0) {
			if (invoiceStatus == 1) {
				invoice = new BiInvoiceinform();
				invoice.setName(this.request.getParameter("invoicePerson").trim());
				invoice.setAddress(this.request.getParameter("invoicePersonAddress").trim());
				invoice.setPhone(this.request.getParameter("invoicePersonPhone").trim());
				invoice.setInvoicetype("0");
			} else if (invoiceStatus == 2) {
				invoice = new BiInvoiceinform();
				invoice.setCompanyname(this.request.getParameter("invoiceCompanyName").trim());
				invoice.setAddress(this.request.getParameter("invoiceCompanyAddress").trim());
				invoice.setBank(this.request.getParameter("invoiceCompanyBank").trim());
				invoice.setBankaccount(this.request.getParameter("invoiceCompanyBankAccount").trim());
				invoice.setDutyparagraph(this.request.getParameter("invoiceDutyParagraph").trim());
				invoice.setPhone(this.request.getParameter("invoiceCompanyPhone").trim());
				invoice.setInvoicetype("1");
			}
		}
		return invoice;
	}

	private void saveInvoiceInfo(BiInvoiceinform invoice, String stockid) {
		if (invoice != null) {
			invoice.setStockid(stockid);
			getService().add(invoice);
		}
	}

	public String stockOutApplyUpdate() {
		String stockId = this.request.getParameter("stockId");
		this.logger.debug("无仓库版仓单出库申请信息修改===仓单号：" + stockId);
		if ((stockId != null) && (stockId.length() > 0)) {
			String deliveryPerson = this.request.getParameter("deliveryPerson");

			String idnumber = this.request.getParameter("idnumber");

			String address = this.request.getParameter("address");
			String phone = this.request.getParameter("phone");
			String deliveryStatus = this.request.getParameter("deliveryStatus");
			String key = this.request.getParameter("key");

			StockOutApplyBO stockOutApplyBO = new StockOutApplyBO();
			if (deliveryStatus.equals("0")) {
				stockOutApplyBO.setStockID(stockId);
				stockOutApplyBO.setIdnumber(idnumber);
				stockOutApplyBO.setDeliveryPerson(deliveryPerson);
				stockOutApplyBO.setAddress(null);
				stockOutApplyBO.setPhone(null);
				stockOutApplyBO.setDeliveryStatus(deliveryStatus);
				stockOutApplyBO.setKey(key);
			} else {
				stockOutApplyBO.setStockID(stockId);
				stockOutApplyBO.setIdnumber(null);
				stockOutApplyBO.setDeliveryPerson(deliveryPerson);
				stockOutApplyBO.setAddress(address);
				stockOutApplyBO.setPhone(phone);
				stockOutApplyBO.setDeliveryStatus(deliveryStatus);
				stockOutApplyBO.setKey(key);
			}
			ResultVO result = this.kernelService.stockOutUpdate(stockOutApplyBO);
			System.out.println(result.getResult());
			if ((result.getResult() != -1L) && (result.getResult() != 0L)) {
				addReturnValue(1, 130113L, new Object[] { stockId, Long.valueOf(result.getResult()) });
			} else {
				addReturnValue(-1, 999999L, new Object[] { result.getErrorInfo().replace("\n", "") });
			}
			this.logger.debug("=============== key:::" + result.getErrorCode() + ",Map::" + result.getInfoMap());
		}
		updateInvoiceInfo(stockId);

		return "success";
	}

	public String logoutStock() {
		String stockIdStr = this.request.getParameter("stockId");
		this.logger.debug("logoutStock================stockId:: " + stockIdStr);
		try {
			if ((stockIdStr != null) && (stockIdStr.length() > 0)) {
				this.logger.debug("logoutStock================stockId:: " + stockIdStr);

				ResultVO result = this.kernelService.logoutStock(stockIdStr);
				if (result.getResult() == 1L) {
					addReturnValue(1, 130109L, new Object[] { stockIdStr });

					writeOperateLog(1310, "仓单号为：" + stockIdStr + "的仓单注销注册", 1, "");
				} else {
					addReturnValue(-1, -130109L, new Object[] { result.getErrorInfo() });
					this.logger.debug(result.getErrorInfo());

					writeOperateLog(1310, "仓单号为：" + stockIdStr + "的仓单注销注册", 0, result.getErrorInfo());
				}
			}
			return "success";
		} catch (Exception e) {
			addReturnValue(-1, -9000L, new Object[] { "注销仓单操作" });
			this.logger.error(Tools.getExceptionTrace(e));

			writeOperateLog(1310, "仓单号为：" + stockIdStr + "的仓单注销注册", 0, e.getMessage());
		}
		return "SysException";
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String stockOutReal() {
		String stockId = this.request.getParameter("stockId");
		this.logger.debug("出库申请列表出库=====仓单号为：" + stockId);
		if ((stockId != null) && (stockId.length() > 0)) {
			String company = this.request.getParameter("company");
			String logisticsOrder = this.request.getParameter("logisticsOrder");

			String deliveryPerson = this.request.getParameter("deliveryPerson");

			String key = this.request.getParameter("key");
			StockOutAuditBO stockOutAuditBO = new StockOutAuditBO();
			stockOutAuditBO.setStockID(stockId);
			stockOutAuditBO.setDeliveryPerson(deliveryPerson);
			stockOutAuditBO.setKey(key);

			ResultVO result = this.kernelService.stockOutAudit(stockOutAuditBO);
			if (result.getResult() > 0L) {
				Logistics logistics = new Logistics();
				logistics.setStockID(stockId);
				logistics.setLogisticsorder(logisticsOrder);
				logistics.setCompany(company);
				getService().add(logistics);

				addReturnValue(1, 130110L, new Object[] { stockId });

				writeOperateLog(1310, "仓单号为：" + stockId + "的仓单出库", 1, null);
			} else {
				addReturnValue(-1, 999999L, new Object[] { result.getErrorInfo().replace("\n", "") });

				writeOperateLog(1310, "仓单号为：" + stockId + "的仓单出库", 0, result.getErrorInfo().replace("\n", ""));
				this.logger.debug("=============== key:::" + result.getErrorCode() + ",Map::" + result.getInfoMap());
			}
		}
		return "success";
	}

	public String dismantleListByStatus() throws Exception {
		this.logger.debug("enter dismantleListByStatus ");
		PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);

		QueryConditions qc = (QueryConditions) pageRequest.getFilters();

		qc.addCondition("status", "in", "(1,2)");

		pageRequest.setSortColumns("order by dismantleId desc");

		listByLimit(pageRequest);
		return "success";
	}

	public String stockOperationByOperation() throws Exception {
		PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
		QueryConditions qc = (QueryConditions) pageRequest.getFilters();

		qc.addCondition("operationId", "=", this.operationId);

		pageRequest.setSortColumns("order by stock.lastTime desc");

		listByLimit(pageRequest);

		this.request.setAttribute("operation", this.operation);
		return "success";
	}

	public String queryStockList() {
		String stockId = this.request.getParameter("stockId");

		Stock stock = new Stock();
		stock.setStockId(stockId);
		stock = (Stock) getService().get(stock);
		if (stock != null) {
			System.out.println(stock.getGoodsProperties());
			putStockPropertys(stock.getGoodsProperties());
		}
		try {
			PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
			QueryConditions qc = (QueryConditions) pageRequest.getFilters();

			qc.addCondition("stockId", "=", stockId);
			pageRequest.setSortColumns("order by operationId");
			Page<StandardModel> page = getQueryService().getPage(pageRequest, this.entity);
			this.request.setAttribute("pageInfo", page);

			this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));
		} catch (Exception e) {
			addReturnValue(-1, -9000L, new Object[] { "仓单详情查看操作" });
			this.logger.error(Tools.getExceptionTrace(e));
		}
		return "success";
	}

	public String getTradeStock() throws Exception {
		String sqlForQuery = "select t.moduleid,t.cnname from C_TRADEMODULE t";
		this.logger.debug("=====系统模块SQL:=========：" + sqlForQuery);
		List<Map<Object, Object>> moduleSysList = getService().getDao().queryBySql(sqlForQuery);

		this.request.setAttribute("moduleSysList", moduleSysList);

		this.request.setAttribute("operation", this.operation);

		PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
		QueryConditions qc = (QueryConditions) pageRequest.getFilters();

		qc.addCondition("status", "=", new Integer(0));
		pageRequest.setSortColumns(" order by stock.lastTime desc");
		listByLimit(pageRequest);

		return "success";
	}

	public String getTradeStockDetail() {
		String stockId = this.request.getParameter("stockId");

		Stock stock = new Stock();
		stock.setStockId(stockId);
		stock = (Stock) getService().get(stock);
		if (stock != null) {
			System.out.println(stock.getGoodsProperties());
			putStockPropertys(stock.getGoodsProperties());
		}
		try {
			PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
			QueryConditions qc = (QueryConditions) pageRequest.getFilters();

			qc.addCondition("stockId", "=", stockId);
			qc.addCondition("status", "=", Integer.valueOf(0));

			Page<StandardModel> page = getQueryService().getPage(pageRequest, this.entity);
			this.request.setAttribute("pageInfo", page);

			this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));

			String sqlForQuery = "select t.moduleid,t.cnname from C_TRADEMODULE t";
			this.logger.debug("=====系统模块SQL:=========：" + sqlForQuery);
			List<Map<Object, Object>> moduleSysList = getService().getDao().queryBySql(sqlForQuery);

			this.request.setAttribute("moduleSysList", moduleSysList);
		} catch (Exception e) {
			addReturnValue(-1, -9000L, new Object[] { "仓单详情查看操作" });
			this.logger.error(Tools.getExceptionTrace(e));
		}
		return "success";
	}

	public String queryDisposeList() {
		String stockId = this.request.getParameter("stockId");
		try {
			PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
			QueryConditions qc = (QueryConditions) pageRequest.getFilters();

			qc.addCondition("stockId", "=", stockId);

			qc.addCondition("status", "=", "0");

			pageRequest.setSortColumns("order by dismantleId");
			Page<StandardModel> page = getService().getPage(pageRequest, this.entity);
			this.request.setAttribute("pageInfo", page);

			this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));
		} catch (Exception e) {
			addReturnValue(-1, -9000L, new Object[] { "待拆仓单信息查询操作" });
			this.logger.error(Tools.getExceptionTrace(e));
		}
		return "success";
	}

	public String disposeSuccess() {
		this.logger.debug("enter disposeSuccess");

		String[] stockIds = this.request.getParameterValues("stock.stockId");
		String[] dismantleIds = this.request.getParameterValues("dismantleId");
		try {
			if (dismantleIds.length > 0) {
				Map<String, String> map = new HashMap();

				String longStockId = stockIds[0];
				String[] arrayOfString1;
				int j = (arrayOfString1 = dismantleIds).length;
				for (int i = 0; i < j; i++) {
					String dismantleId = arrayOfString1[i];
					String realStockCode = this.request.getParameter("realStockCode" + dismantleId);
					map.put(dismantleId, realStockCode);
				}
				ResultVO resultVO = this.kernelService.dismantleStock(longStockId, true, map);
				if (resultVO.getResult() < 0L) {
					addReturnValue(-1, -10001L, new Object[] { resultVO.getErrorInfo() });

					writeOperateLog(1310, "处理仓单号为：" + longStockId + "的仓单拆单", 0, resultVO.getErrorInfo());
				} else {
					addReturnValue(1, 130105L);
					writeOperateLog(1310, "处理仓单号为：" + longStockId + "的仓单拆单", 1, "");
				}
			} else {
				addReturnValue(-1, -130107L);
				writeOperateLog(1310, "处理仓单拆单", 0, ApplicationContextInit.getErrorInfo("-130107"));
			}
		} catch (Exception e) {
			addReturnValue(-1, -130106L, new Object[] { "拆仓单操作" });
			this.logger.error(Tools.getExceptionTrace(e));

			writeOperateLog(1310, "处理仓单拆单", 0, e.getMessage());
		}
		return "success";
	}

	public String disposeFail() {
		this.logger.debug("enter disposeFail");

		String stockId = this.request.getParameter("stock.stockId");
		try {
			ResultVO resultVO = this.kernelService.dismantleStock(stockId, false, null);
			addReturnValue(1, -130105L);
			if (resultVO.getResult() < 0L) {
				addReturnValue(-1, -10001L, new Object[] { resultVO.getErrorInfo() });
				writeOperateLog(1310, "撤销仓单号为：" + stockId + "的仓单拆单", 0, resultVO.getErrorInfo());
			} else {
				addReturnValue(1, 130105L);
				writeOperateLog(1310, "撤销仓单号为：" + stockId + "的仓单拆单", 1, "");
			}
		} catch (Exception e) {
			addReturnValue(-1, -130106L, new Object[] { "拆仓单失败操作" });
			this.logger.error(Tools.getExceptionTrace(e));

			writeOperateLog(1310, "撤销仓单号为：" + stockId + "的仓单拆单", 0, e.getMessage());
		}
		return "success";
	}

	public String stockLogList() throws Exception {
		this.logger.debug("enter stockLogList");

		String type = this.request.getParameter("type");
		if ((type != null) && (type.equals("H"))) {
			this.entity = new OperateLogHis();
			type = "H";
		} else {
			this.entity = new OperateLog();
			type = "D";
		}
		PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
		QueryConditions qc = (QueryConditions) pageRequest.getFilters();

		qc.addCondition("primary.logCatalog.tradeModule.moduleId", "=", Integer.valueOf(Global.getSelfModuleID()));

		pageRequest.setSortColumns("order by operateTime desc");

		listByLimit(pageRequest);

		this.request.setAttribute("type", type);
		return "success";
	}

	public String deleteWareHouse() throws SecurityException, Exception {
		logger.debug("enter deleteWareHouse");
		String ids[] = request.getParameterValues("ids");
		String content = "";
		boolean flag = false;
		String as[];
		int j = (as = ids).length;
		for (int i = 0; i < j; i++) {
			String id = as[i];
			if (!"".equals(content))
				content = (new StringBuilder(String.valueOf(content))).append(",").toString();
			content = (new StringBuilder(String.valueOf(content))).append(id).toString();
			Long longId = Long.valueOf(Long.parseLong(id));
			Warehouse warehouse = new Warehouse();
			warehouse.setId(longId);
			warehouse = (Warehouse) getService().get(warehouse);
			String warehouseid = warehouse.getWarehouseId();
			String sqlForQuery = (new StringBuilder("select t.* from BI_Stock t where t.warehouseid='")).append(warehouseid)
					.append("' and t.stockstatus not in (2, 3) ").toString();
			logger.debug((new StringBuilder("=====仓单SQL:=========：")).append(sqlForQuery).toString());
			List stockList = getService().getDao().queryBySql(sqlForQuery);
			if (stockList == null || stockList.size() <= 0)
				continue;
			logger.debug((new StringBuilder("=====有未处理仓单的仓库:=========：")).append(warehouseid).toString());
			flag = true;
			break;
		}

		if (flag) {
			addReturnValue(-1, 0xfffffffffffe03c4L);
			writeOperateLog(1320, "仓库删除失败", 0, "所删除的仓库中有关联仓单");
		} else {
			if (ids.length <= 1) {
				Warehouse warehouse = new Warehouse();
				String as1[];
				int l = (as1 = ids).length;
				for (int k = 0; k < l; k++) {
					String wId = as1[k];
					warehouse.setId(Long.valueOf(Long.parseLong(wId)));
					warehouse = (Warehouse) getService().get(warehouse);
					getService().delete(warehouse);
					addReturnValue(1, 0x1d463L, new Object[] { warehouse.getWarehouseId() });
				}

			} else {
				delete();
			}
			writeOperateLog(1320, (new StringBuilder("编号为：(")).append(content).append(")的仓库删除成功").toString(), 1, "");
		}
		return "success";
	}

	public String getStockDetails() throws Exception {
		super.viewById();
		Stock stock = (Stock) this.entity;
		int num = 0;
		String num1 = this.request.getParameter("num");
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
					if ((num1 != null) && (!"".equals(num1))) {
						num = Integer.parseInt(num1);
					}
					this.request.setAttribute("num", Integer.valueOf(num));
				}
			}
			BiInvoiceinform invoice = (BiInvoiceinform) getService().getDao().getHibernateTemplate().get(BiInvoiceinform.class, stock.getStockId());
			if (invoice == null) {
				this.request.setAttribute("invoiceStatus", Integer.valueOf(0));
			} else {
				this.request.setAttribute("invoiceStatus", Integer.valueOf(1));
				this.request.setAttribute("invoiceinform", invoice);
			}
		}
		return "success";
	}

	public String getDismantleDetails() throws Exception {
		super.viewById();
		Dismantle dismantle = (Dismantle) this.entity;
		if ((dismantle != null) && (dismantle.getStock() != null)) {
			putStockPropertys(dismantle.getStock().getGoodsProperties());
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

	public String stockOutForward() {
		String stockId = this.request.getParameter("stockId");

		String sql = "select * from bi_outStock where stockid=" + stockId + " and status=0" + "and phone is not null";
		List<Map<Object, Object>> stockList = getService().getDao().queryBySql(sql);
		if ((stockList.size() == 0) || (stockList == null)) {
			this.request.setAttribute("deliverstyle", Integer.valueOf(0));
		} else {
			this.request.setAttribute("deliverstyle", Integer.valueOf(1));
		}
		if ((stockId != null) && (stockId.length() > 0)) {
			this.request.setAttribute("stockId", stockId);
		}
		return "success";
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String stockOutCancel() {
		String stockId = this.request.getParameter("stockId");
		if ((stockId != null) && (stockId.length() > 0)) {
			ResultVO result = this.kernelService.withdrowStockOutApply(stockId);
			if ((result.getResult() != -1L) && (result.getResult() != 0L)) {
				if (result.getResult() > 0L) {
					BiInvoiceinform invoice = new BiInvoiceinform(stockId);
					List<Map<Object, Object>> list = getService().getListBySql("select * from BI_invoiceInform where stockid = " + stockId);
					if ((list != null) && (list.size() > 0)) {
						getService().delete(invoice);
					}
					addReturnValue(1, 130111L, new Object[] { stockId });

					writeOperateLog(1310, "仓单号为：" + stockId + "的仓单出库申请撤销", 1, null);
				} else {
					addReturnValue(-1, 999999L, new Object[] { result.getErrorInfo().replace("\n", "") });

					writeOperateLog(1310, "仓单号为：" + stockId + "的仓单出库申请撤销", 0, result.getErrorInfo().replace("\n", ""));
				}
				this.logger.debug("=============== key:::" + result.getErrorCode() + ",Map::" + result.getInfoMap());
			}
		}
		return "success";
	}

	public String warehouseLogList() throws Exception {
		this.logger.debug("enter operateLogList");
		this.logger.debug("=========>" + this.com_operatorTypeMap);
		String type = this.request.getParameter("type");
		if ((type != null) && (type.equals("H"))) {
			this.entity = new GlobalLogAllHis();
			type = "H";
		} else {
			this.entity = new GlobalLogAll();
			type = "D";
		}
		PageRequest<String> pageRequestlog = new PageRequest("and primary.catalogID<>3203");
		Page<StandardModel> page = getService().getPage(pageRequestlog, new WarehouseLogCatalog());
		List<StandardModel> logList = page.getResult();

		PageRequest<QueryConditions> pageRequest = getPageRequest(this.request);
		QueryConditions qc = (QueryConditions) pageRequest.getFilters();
		Map<String, Object> mapForParameters = getParametersStartingWith(this.request, "gnnt_");
		if ((mapForParameters != null) && (mapForParameters.size() == 0)) {
			String date = Tools.fmtDate(new Date());
			String startDate = Tools.combineDateTime(date);
			String endDate = Tools.combineDateTime(date, 1);
			qc.addCondition("primary.operateTime", ">=", Tools.strToDateTime(startDate));
			qc.addCondition("primary.operateTime", "<=", Tools.strToDateTime(endDate));
		}
		if (this.logType.equals("logon")) {
			qc.addCondition("primary.logCatalog.catalogID", "=", Integer.valueOf(3203));
		} else if (this.logType.equals("oper")) {
			qc.addCondition("primary.logCatalog.catalogID", "<>", Integer.valueOf(3203));
		}
		pageRequest.setSortColumns("order by operateTime desc");
		listByLimit(pageRequest);
		this.request.setAttribute("type", type);
		this.request.setAttribute("logList", logList);
		this.request.setAttribute("logType", this.logType);
		this.request.setAttribute("com_operatorTypeMap", this.com_operatorTypeMap);
		return "success";
	}

	public String warehouseList() throws Exception {
		this.logger.debug("enter warehouseList");
		super.listByLimit();
		try {
			int HaveWarehouse = Tools.strToInt(getKernelService().getConfigInfo("HaveWarehouse"), 0);
			this.logger.debug("有无仓库系统(0默认没有仓库1有仓库)===：" + HaveWarehouse);

			this.request.setAttribute("haveWarehouse", Integer.valueOf(HaveWarehouse));
			if (HaveWarehouse == 0) {
				QueryConditions qc = new QueryConditions();
				qc.addCondition("type", "=", "DEFAULT_SUPER_ADMIN");
				PageRequest<QueryConditions> pageRequests = new PageRequest();
				pageRequests.setPageNumber(1);
				pageRequests.setPageSize(10000);
				pageRequests.setFilters(qc);
				pageRequests.setSortColumns(" order by userId desc");
				Page<StandardModel> pageUser = getService().getPage(pageRequests, new Wuser());
				System.out.println("=====超级仓库管理员====" + pageUser.getResult().size());

				Map<String, Wuser> map = new HashMap();
				for (StandardModel modelUser : pageUser.getResult()) {
					Wuser user = new Wuser();
					user = (Wuser) modelUser;
					map.put(user.getWarehouseID(), user);
				}
				this.request.setAttribute("map", map);
			}
		} catch (Exception e) {
			this.logger.error(Tools.getExceptionTrace(e));
		}
		return "success";
	}

	public String addWarehouseForward() {
		int HaveWarehouse = Tools.strToInt(getKernelService().getConfigInfo("HaveWarehouse"), 0);
		this.logger.debug("有无仓库系统(0默认没有仓库1有仓库)===：" + HaveWarehouse);

		this.request.setAttribute("haveWarehouse", Integer.valueOf(HaveWarehouse));

		forwardAdd();
		return "success";
	}

	public String addWarehouse() throws Exception {
		this.logger.debug("enter addWarehouse");

		Warehouse ware = (Warehouse) this.entity;
		ware.setCreateTime(getService().getSysDate());
		getService().add(ware);

		int HaveWarehouse = Tools.strToInt(getKernelService().getConfigInfo("HaveWarehouse"), 0);
		this.logger.debug("有无仓库系统(0默认没有仓库1有仓库)===：" + HaveWarehouse);
		String content = "";
		if (HaveWarehouse == 0) {
			String userId = this.request.getParameter("userId");
			String name = this.request.getParameter("name");
			String password = this.request.getParameter("password");
			String description = this.request.getParameter("description");
			Wuser user = new Wuser();
			user.setUserId(userId);
			user.setName(name);
			user.setPassword(MD5.getMD5(userId, password));
			user.setDescription(description);

			user.setType("DEFAULT_SUPER_ADMIN");

			user.setWarehouseID(ware.getWarehouseId());
			getService().add(user);

			content = "仓库超级管理员编号为：" + user.getUserId();
		}
		addReturnValue(1, 119901L);
		if (HaveWarehouse == 0) {
			writeOperateLog(1320, "仓库号为：" + ware.getWarehouseId() + "," + content + "的仓库添加成功", 1, "");
		} else {
			writeOperateLog(1320, "仓库号为：" + ware.getWarehouseId() + "的仓库添加成功", 1, "");
		}
		return "success";
	}

	public String stockConfirm() throws Exception {
		this.logger.debug("enter stockConfirm 进入确认收货action!");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		String stockId = this.request.getParameter("stockId");
		int result = this.stockConfirmService.stockConfirm(stockId, user);
		if (result == 1) {
			addReturnValue(1, 130114L, new Object[] { stockId });
		} else if (result == -1) {
			addReturnValue(1, 999999L, new Object[] { "操作失败" });
		} else if (result == 0) {
			addReturnValue(1, 999999L, new Object[] { "没有查询到此仓单交收配对信息!" });
		}
		return "success";
	}

	private void updateInvoiceInfo(String stockId) {
		BiInvoiceinform invoice = new BiInvoiceinform();
		String invoiceStatusStr = this.request.getParameter("invoiceStatus");
		int invoiceStatus = Integer.valueOf(invoiceStatusStr).intValue();
		if (invoiceStatus == -1) {
			invoice = (BiInvoiceinform) getService().getDao().getHibernateTemplate().get(BiInvoiceinform.class, stockId);
			if (invoice != null) {
				getService().delete(invoice);
			}
		} else if (invoiceStatus == 0) {
			invoice = (BiInvoiceinform) getService().getDao().getHibernateTemplate().get(BiInvoiceinform.class, stockId);
			boolean flag = true;
			if (invoice == null) {
				flag = false;
				invoice = new BiInvoiceinform();
			}
			invoice.setStockid(stockId);
			invoice.setName(this.request.getParameter("invoicePerson").trim());
			invoice.setAddress(this.request.getParameter("invoicePersonAddress").trim());
			invoice.setPhone(this.request.getParameter("invoicePersonPhone").trim());
			invoice.setInvoicetype("0");
			invoice.setBank("");
			invoice.setCompanyname("");
			invoice.setBankaccount("");
			invoice.setDutyparagraph("");
			if (flag) {
				getService().update(invoice);
			} else {
				getService().add(invoice);
			}
		} else if (invoiceStatus == 1) {
			invoice = (BiInvoiceinform) getService().getDao().getHibernateTemplate().get(BiInvoiceinform.class, stockId);
			boolean flag = true;
			if (invoice == null) {
				flag = false;
				invoice = new BiInvoiceinform();
			}
			invoice.setStockid(stockId);
			invoice.setCompanyname(this.request.getParameter("invoiceCompanyName").trim());
			invoice.setAddress(this.request.getParameter("invoiceCompanyAddress").trim());
			invoice.setBank(this.request.getParameter("invoiceCompanyBank").trim());
			invoice.setBankaccount(this.request.getParameter("invoiceCompanyBankAccount").trim());
			invoice.setDutyparagraph(this.request.getParameter("invoiceDutyParagraph").trim());
			invoice.setPhone(this.request.getParameter("invoiceCompanyPhone").trim());
			invoice.setInvoicetype("1");
			invoice.setName("");
			if (flag) {
				getService().update(invoice);
			} else {
				getService().add(invoice);
			}
		}
	}
}
