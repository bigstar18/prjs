package gnnt.MEBS.zcjs.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.zcjs.model.CommodityProperty;
import gnnt.MEBS.zcjs.model.FirmPermission;
import gnnt.MEBS.zcjs.model.ProsceniumShow;
import gnnt.MEBS.zcjs.model.TradeModule;
import gnnt.MEBS.zcjs.model.ValidRegstock;
import gnnt.MEBS.zcjs.model.WarehouseRegStock;
import gnnt.MEBS.zcjs.model.innerObject.KeyValue;
import gnnt.MEBS.zcjs.model.innerObject.QualityObject;
import gnnt.MEBS.zcjs.services.BreedService;
import gnnt.MEBS.zcjs.services.CommodityParameterService;
import gnnt.MEBS.zcjs.services.CommodityPropertyService;
import gnnt.MEBS.zcjs.services.FirmPermissionService;
import gnnt.MEBS.zcjs.services.ProsceniumShowService;
import gnnt.MEBS.zcjs.services.QualityService;
import gnnt.MEBS.zcjs.services.TradeInfoService;
import gnnt.MEBS.zcjs.services.TradeModuleService;
import gnnt.MEBS.zcjs.services.ValidRegstockService;
import gnnt.MEBS.zcjs.util.ParseXML;
import gnnt.MEBS.zcjs.util.SysData;

public class RegStockController extends BaseController {
	private final transient Log logger = LogFactory.getLog(RegStockController.class);

	private ValidRegstockService getBeanOfValidRegstockService() {
		ValidRegstockService localValidRegstockService = null;
		synchronized (ValidRegstockService.class) {
			if (localValidRegstockService == null) {
				localValidRegstockService = (ValidRegstockService) SysData.getBean("z_validRegstockService");
			}
		}
		return localValidRegstockService;
	}

	private ProsceniumShowService getBeanOfProsceniumShowService() {
		ProsceniumShowService localProsceniumShowService = null;
		synchronized (ProsceniumShowService.class) {
			if (localProsceniumShowService == null) {
				localProsceniumShowService = (ProsceniumShowService) SysData.getBean("z_prosceniumShowService");
			}
		}
		return localProsceniumShowService;
	}

	private TradeInfoService getBeanOfTradeInfoService() {
		TradeInfoService localTradeInfoService = null;
		synchronized (TradeInfoService.class) {
			if (localTradeInfoService == null) {
				localTradeInfoService = (TradeInfoService) SysData.getBean("z_tradeInfoService");
			}
		}
		return localTradeInfoService;
	}

	private CommodityPropertyService getBeanOfCommodityPropertyService() {
		CommodityPropertyService localCommodityPropertyService = null;
		synchronized (CommodityPropertyService.class) {
			if (localCommodityPropertyService == null) {
				localCommodityPropertyService = (CommodityPropertyService) SysData.getBean("z_commodityPropertyService");
			}
		}
		return localCommodityPropertyService;
	}

	private QualityService getBeanOfQualityService() {
		QualityService localQualityService = null;
		synchronized (QualityService.class) {
			if (localQualityService == null) {
				localQualityService = (QualityService) SysData.getBean("z_qualityService");
			}
		}
		return localQualityService;
	}

	private BreedService getBeanOfBreedService() {
		BreedService localBreedService = null;
		synchronized (BreedService.class) {
			if (localBreedService == null) {
				localBreedService = (BreedService) SysData.getBean("z_breedService");
			}
		}
		return localBreedService;
	}

	private CommodityParameterService getBeanOfCommodityParameterService() {
		CommodityParameterService localCommodityParameterService = null;
		synchronized (CommodityParameterService.class) {
			if (localCommodityParameterService == null) {
				localCommodityParameterService = (CommodityParameterService) SysData.getBean("z_commodityParameterService");
			}
		}
		return localCommodityParameterService;
	}

	private FirmPermissionService getBeanOfFirmService() {
		FirmPermissionService localFirmPermissionService = null;
		synchronized (FirmPermissionService.class) {
			if (localFirmPermissionService == null) {
				localFirmPermissionService = (FirmPermissionService) SysData.getBean("z_firmPermissionService");
			}
		}
		return localFirmPermissionService;
	}

	private TradeModuleService getBeanOfTradeModuleService() {
		TradeModuleService localTradeModuleService = null;
		synchronized (TradeModuleService.class) {
			if (localTradeModuleService == null) {
				localTradeModuleService = (TradeModuleService) SysData.getBean("z_tradeModuleService");
			}
		}
		return localTradeModuleService;
	}

	public ModelAndView getvalidRegStockList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		String str = " from Z_ValidRegstock zv,w_commodity w where zv.BreedId=w.id";
		TradeModule localTradeModule = getBeanOfTradeModuleService().getObject("3");
		if ((localTradeModule.getIsSettle() != null) && ("Y".equals(localTradeModule.getIsSettle()))) {
			return getValidRegStockList(paramHttpServletRequest, paramHttpServletResponse, str);
		}
		return getValidRegStockNoStockList(paramHttpServletRequest, paramHttpServletResponse, str);
	}

	public List<ProsceniumShow> getProList() {
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("ProsceniumApplication", "=", "BG_Z_ValidRegstock");
		return getBeanOfProsceniumShowService().getObjectList(localQueryConditions);
	}

	public ModelAndView getValidRegStockNoStockList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse,
			String paramString) throws Exception {
		this.logger.debug("entering 'list' method...");
		QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
		PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
		if (localPageInfo == null) {
			localPageInfo = new PageInfo(1, Condition.PAGESIZE, "RegstockId", false);
		}
		Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
		List localList = getProList();
		String str = "";
		Object localObject1 = localList.iterator();
		while (((Iterator) localObject1).hasNext()) {
			ProsceniumShow localObject2 = (ProsceniumShow) ((Iterator) localObject1).next();
			str = str + ((ProsceniumShow) localObject2).getNodeKey() + " " + ((ProsceniumShow) localObject2).getShowProperty() + ",";
		}
		str = str.substring(0, str.length() - 1);
		localObject1 = "select * from (select " + str + paramString + " ) ";
		Object localObject2 = getBeanOfTradeInfoService().getTradeInfoList((String) localObject1, localQueryConditions, localPageInfo);
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "validRegStockNoStock/validRegStockList");
		localModelAndView.addObject("resultList", localObject2);
		localModelAndView.addObject("prosceniumShowList", localList);
		localModelAndView.addObject("pageInfo", localPageInfo);
		localModelAndView.addObject("oldParams", localMap);
		localModelAndView.addObject("count", Integer.valueOf(localList.size()));
		return localModelAndView;
	}

	public ModelAndView addForwardRegStock(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "validRegStockNoStock/validRegStockAddManual");
		return localModelAndView;
	}

	public ModelAndView addSaleForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		List localList = getBeanOfBreedService().breedTableList(null, null);
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "validRegStockNoStock/validRegStockAddManual");
		localModelAndView.addObject("breedList", localList);
		return localModelAndView;
	}

	public ModelAndView ForwardRegStockNoStockView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		String str = paramHttpServletRequest.getParameter("regStockId");
		Map localMap = getBeanOfValidRegstockService().getRegStockView(str);
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "validRegStockNoStock/validRegStockView");
		localModelAndView.addObject("validRegstock", localMap.get("resultMap"));
		localModelAndView.addObject("qualityList", localMap.get("quality"));
		localModelAndView.addObject("propertyList", localMap.get("property"));
		return localModelAndView;
	}

	public ModelAndView addSaleNextForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		String str1 = "";
		ValidRegstock localValidRegstock1 = new ValidRegstock();
		ParamUtil.bindData(paramHttpServletRequest, localValidRegstock1);
		String str2 = getBeanOfBreedService().getBreedById((int) localValidRegstock1.getBreedId()).getBreedName();
		ModelAndView localModelAndView = null;
		FirmPermission localFirmPermission = getBeanOfFirmService().getFirmPermissionById(localValidRegstock1.getFirmId());
		ValidRegstock localValidRegstock2 = getBeanOfValidRegstockService().getRegStock(localValidRegstock1.getRegstockId());
		if (localValidRegstock2 != null) {
			str1 = "仓单号已存在！";
			setResultMsg(paramHttpServletRequest, str1);
			localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "regStockController.zcjs?funcflg=addSaleForward");
		} else if (localFirmPermission == null) {
			str1 = "交易商不存在！";
			setResultMsg(paramHttpServletRequest, str1);
			localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "regStockController.zcjs?funcflg=addSaleForward");
		} else {
			QueryConditions localQueryConditions1 = new QueryConditions();
			localQueryConditions1.addCondition("status", "=", Integer.valueOf(1));
			ArrayList localArrayList = new ArrayList();
			List localList1 = getBeanOfCommodityPropertyService().getObjectList(localQueryConditions1, null);
			QueryConditions localQueryConditions2 = null;
			if ((localList1 != null) && (localList1.size() > 0)) {
				Iterator localObject1 = localList1.iterator();
				while (((Iterator) localObject1).hasNext()) {
					CommodityProperty localObject2 = (CommodityProperty) ((Iterator) localObject1).next();
					KeyValue localKeyValue = new KeyValue();
					localQueryConditions2 = new QueryConditions();
					localQueryConditions2.addCondition("breedId", "=", Long.valueOf(localValidRegstock1.getBreedId()));
					localQueryConditions2.addCondition("commodityPropertyId", "=", Long.valueOf(((CommodityProperty) localObject2).getPropertyId()));
					localQueryConditions2.addCondition("parameterstatus", "=", Integer.valueOf(1));
					List localList2 = getBeanOfCommodityParameterService().getObjectList(localQueryConditions2, null);
					localKeyValue.setKey(localObject2);
					localKeyValue.setValue(localList2);
					localArrayList.add(localKeyValue);
				}
			}
			Object localObject1 = new QueryConditions();
			((QueryConditions) localObject1).addCondition("breedId", "=", Long.valueOf(localValidRegstock1.getBreedId()));
			((QueryConditions) localObject1).addCondition("status", "=", Integer.valueOf(1));
			Object localObject2 = getBeanOfQualityService().getList((QueryConditions) localObject1);
			localModelAndView = new ModelAndView(Condition.PATH + "validRegStockNoStock/validRegStockAddManualNext");
			localModelAndView.addObject("resultList", localArrayList);
			localModelAndView.addObject("validRegstock", localValidRegstock1);
			localModelAndView.addObject("qualityList", localObject2);
		}
		localModelAndView.addObject("breedName", str2);
		return localModelAndView;
	}

	public ModelAndView addSaleStock(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		ValidRegstock localValidRegstock = new ValidRegstock();
		ParamUtil.bindData(paramHttpServletRequest, localValidRegstock);
		String str1 = "";
		String str2 = AclCtrl.getLogonID(paramHttpServletRequest);
		QueryConditions localQueryConditions1 = new QueryConditions();
		localQueryConditions1.addCondition("status", "=", Integer.valueOf(1));
		List localList = getBeanOfCommodityPropertyService().getTableList(localQueryConditions1, null);
		String str3 = "<?xml version=\"1.0\" encoding=\"gbk\"?><root>";
		if ((localList != null) && (localList.size() > 0)) {
			for (int i = 0; i < localList.size(); i++) {
				Map localObject = (Map) localList.get(i);
				str3 = str3 + "<property key=\"" + ((Map) localObject).get("key") + "\">" + "<id>" + ((Map) localObject).get("propertyId") + "</id>"
						+ "<name><![CDATA[" + ((Map) localObject).get("PROPERTYNAME") + "]]></name>" + "<value><![CDATA["
						+ paramHttpServletRequest.getParameter(new StringBuilder().append("cp_").append(((Map) localObject).get("key")).toString())
						+ "]]></value></property>";
			}
			str3 = str3 + "</root>";
		}
		QueryConditions localQueryConditions2 = new QueryConditions();
		localQueryConditions2.addCondition("status", "=", Integer.valueOf(1));
		localQueryConditions2.addCondition("breedId", "=", Long.valueOf(localValidRegstock.getBreedId()));
		Object localObject = getBeanOfQualityService().getTableList(localQueryConditions2, null);
		String str4 = "<?xml version=\"1.0\" encoding=\"gbk\"?><root>";
		if ((localObject != null) && (((List) localObject).size() > 0)) {
			Iterator localIterator = ((List) localObject).iterator();
			while (localIterator.hasNext()) {
				Map localMap = (Map) localIterator.next();
				str4 = str4 + "<quality id=\"" + localMap.get("qualityId") + "\"" + ">" + "<name><![CDATA[" + localMap.get("qualityname")
						+ "]]></name>" + "<value><![CDATA["
						+ paramHttpServletRequest.getParameter(new StringBuilder().append("qu_").append(localMap.get("qualityId")).toString())
						+ "]]></value></quality>";
			}
			str4 = str4 + "</root>";
		}
		localValidRegstock.setCommodityProperties(str3);
		localValidRegstock.setQuality(str4);
		localValidRegstock.setStatus(1);
		localValidRegstock.setType(1);
		try {
			getBeanOfValidRegstockService().addSendRegStock(localValidRegstock, str2);
			str1 = "添加成功！";
		} catch (Exception localException) {
			localException.printStackTrace();
			str1 = "添加失败！";
		}
		setResultMsg(paramHttpServletRequest, str1);
		return new ModelAndView("redirect:" + Condition.PATH + "regStockController.zcjs?funcflg=getvalidRegStockList");
	}

	public ModelAndView deleteSaleRegStock(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
		String str1 = "";
		String str2 = "";
		String str3 = AclCtrl.getLogonID(paramHttpServletRequest);
		ArrayList localArrayList = new ArrayList();
		int i = 0;
		for (int j = 0; j < arrayOfString.length; j++) {
			ValidRegstock localValidRegstock = getBeanOfValidRegstockService().getRegStock(arrayOfString[j]);
			if (localValidRegstock.getStatus() != 1) {
				i = 1;
			}
		}
		if (i != 0) {
			str1 = "有效仓单状态不符，不能删除！";
		} else {
			try {
				getBeanOfValidRegstockService().deleteSaleRegstock(arrayOfString, str3);
				str1 = "删除成功，共删除" + arrayOfString.length + "条";
			} catch (Exception localException) {
				localException.printStackTrace();
				str1 = "删除失败！";
			}
		}
		setResultMsg(paramHttpServletRequest, str1);
		return new ModelAndView("redirect:" + Condition.PATH + "regStockController.zcjs?funcflg=getvalidRegStockList");
	}

	public ModelAndView getValidRegStockList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse,
			String paramString) throws Exception {
		this.logger.debug("entering 'list' method...");
		QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
		PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
		if (localPageInfo == null) {
			localPageInfo = new PageInfo(1, Condition.PAGESIZE, "RegstockId", false);
		}
		Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
		List localList = getProList();
		String str = "";
		Object localObject1 = localList.iterator();
		while (((Iterator) localObject1).hasNext()) {
			ProsceniumShow localObject2 = (ProsceniumShow) ((Iterator) localObject1).next();
			str = str + ((ProsceniumShow) localObject2).getNodeKey() + " " + ((ProsceniumShow) localObject2).getShowProperty() + ",";
		}
		str = str.substring(0, str.length() - 1);
		localObject1 = "select * from (select " + str + paramString + " ) ";
		Object localObject2 = getBeanOfTradeInfoService().getTradeInfoList((String) localObject1, localQueryConditions, localPageInfo);
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "validRegStock/validRegStockList");
		localModelAndView.addObject("resultList", localObject2);
		localModelAndView.addObject("prosceniumShowList", localList);
		localModelAndView.addObject("pageInfo", localPageInfo);
		localModelAndView.addObject("oldParams", localMap);
		localModelAndView.addObject("count", Integer.valueOf(localList.size()));
		return localModelAndView;
	}

	public ModelAndView deleteRegStock(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
		String str1 = "";
		String str2 = "";
		String str3 = AclCtrl.getLogonID(paramHttpServletRequest);
		ArrayList localArrayList = new ArrayList();
		try {
			for (int i = 0; i < arrayOfString.length; i++) {
				ValidRegstock localValidRegstock = getBeanOfValidRegstockService().getRegStock(arrayOfString[i]);
				if (localValidRegstock.getStatus() != 1) {
					localArrayList.add(localValidRegstock.getRegstockId());
				}
			}
			if (localArrayList.size() > 0) {
				for (int i = 0; i < localArrayList.size(); i++) {
					str2 = str2 + (String) localArrayList.get(i) + ",";
				}
				str2 = str2.substring(0, str2.length() - 1);
				str1 = "仓单号为" + str2 + "的有效仓单状态不符，不能删除！" + "只能删除未用状态的仓单";
			} else {
				getBeanOfValidRegstockService().deleteRegStock(arrayOfString, str3);
				str1 = "删除成功，共删除" + arrayOfString.length + "条";
			}
		} catch (Exception localException) {
			localException.printStackTrace();
			str1 = "删除失败";
		}
		setResultMsg(paramHttpServletRequest, str1);
		return new ModelAndView("redirect:" + Condition.PATH + "regStockController.zcjs?funcflg=getvalidRegStockList");
	}

	public ModelAndView addForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "validRegStock/validRegStockAdd");
		return localModelAndView;
	}

	public ModelAndView getRegStockId(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		paramHttpServletResponse.setCharacterEncoding("UTF-8");
		String str1 = paramHttpServletRequest.getParameter("firmId");
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("firmId", "=", str1);
		localQueryConditions.addCondition("Weight", ">", Integer.valueOf(0));
		localQueryConditions.addCondition("type", "=", Integer.valueOf(0));
		localQueryConditions.addCondition("frozenWeight", "=", Integer.valueOf(0));
		List localList = getBeanOfValidRegstockService().getWHRegStockList(localQueryConditions, null);
		String str2 = "";
		if ((localList != null) && (localList.size() > 0)) {
			str2 = "<select id=\"regStockId\" name=\"regStockId\" class=\"normal\" style=\"width: 120px\"><OPTION value=\"\">请选择</OPTION>";
			Iterator localObject = localList.iterator();
			while (((Iterator) localObject).hasNext()) {
				Map localMap = (Map) ((Iterator) localObject).next();
				str2 = str2 + "<option value=\"" + localMap.get("regStockId") + "\">" + localMap.get("regStockId") + "</option>";
			}
			str2 = str2 + "</select>";
		} else {
			str2 = "<font color=\"red\">此交易商没有注册仓单或者系统中不存在仓单所对应的品种，无法操作。请返回或重新选择交易商！</font>";
		}
		Object localObject = paramHttpServletResponse.getWriter();
		((PrintWriter) localObject).print(str2);
		return null;
	}

	public ModelAndView viewForwardRegStock(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		String str = paramHttpServletRequest.getParameter("regStockId");
		WarehouseRegStock localWarehouseRegStock = getBeanOfValidRegstockService().getWHRegStock(str);
		if (null == localWarehouseRegStock) {
			setResultMsg(paramHttpServletRequest, "此仓单所属品种不是挂牌的品种，无法添加！！！！");
			ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "validRegStock/validRegStockAdd");
			return localModelAndView;
		}
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "validRegStock/validRegStockAddView");
		localModelAndView.addObject("whRegStock", localWarehouseRegStock);
		localModelAndView.addObject("propertyList", localWarehouseRegStock.getPropertyObjectList());
		if (localWarehouseRegStock.getQualityObjectList() != null) {
			for (int i = 0; i < localWarehouseRegStock.getQualityObjectList().size(); i++) {
				QualityObject localQualityObject = (QualityObject) localWarehouseRegStock.getQualityObjectList().get(i);
			}
		}
		localModelAndView.addObject("qualityList", localWarehouseRegStock.getQualityObjectList());
		return localModelAndView;
	}

	public ModelAndView ForwardRegStockView(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
			throws Exception {
		String str = paramHttpServletRequest.getParameter("regStockId");
		Map localMap = getBeanOfValidRegstockService().getRegStockView(str);
		ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "validRegStock/validRegStockView");
		localModelAndView.addObject("validRegstock", localMap.get("resultMap"));
		localModelAndView.addObject("qualityList", localMap.get("quality"));
		localModelAndView.addObject("propertyList", localMap.get("property"));
		return localModelAndView;
	}

	public ModelAndView AddRegStock(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws Exception {
		String str1 = paramHttpServletRequest.getParameter("regStockId");
		String str2 = "";
		String str3 = AclCtrl.getLogonID(paramHttpServletRequest);
		WarehouseRegStock localWarehouseRegStock = getBeanOfValidRegstockService().getWHRegStock(str1);
		ValidRegstock localValidRegstock = new ValidRegstock();
		localValidRegstock.setRegstockId(str1);
		localValidRegstock.setBreedId(localWarehouseRegStock.getBreedId());
		localValidRegstock.setQuantity(localWarehouseRegStock.getWeight());
		localValidRegstock.setStatus(1);
		localValidRegstock.setType(1);
		localValidRegstock.setFirmId(localWarehouseRegStock.getFirmId());
		localValidRegstock.setCommodityProperties(ParseXML.getCommodityPropertyObjectXml(localWarehouseRegStock.getPropertyObjectList()));
		localValidRegstock.setQuality(ParseXML.getQualityObjectXml(localWarehouseRegStock.getQualityObjectList()));
		try {
			getBeanOfValidRegstockService().addRegStock(localValidRegstock, str1, str3);
			str2 = "添加成功";
		} catch (Exception localException) {
			localException.printStackTrace();
			str2 = "添加失败";
		}
		setResultMsg(paramHttpServletRequest, str2);
		return new ModelAndView("redirect:" + Condition.PATH + "regStockController.zcjs?funcflg=getvalidRegStockList");
	}
}
