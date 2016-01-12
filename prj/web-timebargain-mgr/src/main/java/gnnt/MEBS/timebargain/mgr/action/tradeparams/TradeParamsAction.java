package gnnt.MEBS.timebargain.mgr.action.tradeparams;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.ecside.core.ECSideContext;
import org.ecside.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.Arith;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.timebargain.mgr.exception.DeleteCheckException;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Breed;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Commodity;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Market;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.NotTradeDay;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.SettleCommodity;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Tariff;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.TariffSimple;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.TradeTime;
import gnnt.MEBS.timebargain.mgr.service.DaySectionService;
import gnnt.MEBS.timebargain.mgr.service.TradeParamsService;
import gnnt.MEBS.timebargain.mgr.statictools.Copy;

@Controller
@Scope("request")
public class TradeParamsAction extends EcsideAction {
	private static final long serialVersionUID = 46845244024330686L;

	@Autowired
	@Qualifier("com_tradeParamsService")
	private TradeParamsService tps;

	@Resource(name = "commodity_statusMap")
	private Map<String, String> commodity_statusMap;

	@Resource(name = "tradetime_statusMap")
	private Map<String, String> tradetime_statusMap;
	private String sql;
	private String opr;

	@Autowired
	@Qualifier("com_daySectionService")
	private DaySectionService daySectionService;

	public Map<String, String> getCommodity_statusMap() {
		return this.commodity_statusMap;
	}

	public void setCommodity_statusMap(Map<String, String> commodityStatusMap) {
		this.commodity_statusMap = commodityStatusMap;
	}

	public Map<String, String> getTradetime_statusMap() {
		return this.tradetime_statusMap;
	}

	public void setTradetime_statusMap(Map<String, String> tradetimeStatusMap) {
		this.tradetime_statusMap = tradetimeStatusMap;
	}

	private String getSql() {
		return this.sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getOpr() {
		return this.opr;
	}

	public void setOpr(String opr) {
		this.opr = opr;
	}

	public String getMarket() throws Exception {
		this.logger.debug("enter getMarket");

		PageRequest pageRequest = getPageRequest(this.request);
		Page page = getService().getPage(pageRequest, this.entity);
		List list = page.getResult();

		if ((list != null) && (list.size() > 0)) {
			this.entity = ((StandardModel) list.get(0));

			Market market = (Market) this.entity;
			String typeFloat = "";
			if (market.getFloatingLossComputeType() != null) {
				String temp = market.getFloatingLossComputeType().toString();
				if (("0".equals(temp)) || ("1".equals(temp)) || ("2".equals(temp))) {
					typeFloat = "1";
				}
				if (("3".equals(temp)) || ("4".equals(temp))) {
					typeFloat = "2";
				}
			}
			this.request.setAttribute("typeFloat", typeFloat);

			return "success";
		}
		return "error";
	}

	public String addTradeTime() throws Exception {
		this.logger.debug("enter TradeParmasAction's addTradeTime");
		TradeTime tradeTime = (TradeTime) this.entity;
		tradeTime.setModifyTime(getService().getSysDate());
		getService().add(tradeTime);

		this.daySectionService.insertSection(tradeTime);

		addReturnValue(1, 119901L);
		writeOperateLog(1504, "添加交易节，编号：" + tradeTime.getSectionID(), 1, "");
		return "success";
	}

	public String updateTradeTimeForward() throws Exception {
		this.logger.debug("enter updateTradeTimeForward");
		this.entity = getService().get(this.entity);

		PageRequest pageRequest = getPageRequest(this.request);
		Page page = getService().getPage(pageRequest, new Market());
		Market market = (Market) page.getResult().get(0);
		this.request.setAttribute("tradeTimeType", market.getTradeTimeType());

		TradeTime tt = (TradeTime) this.entity;
		String psql = getSql().replace("?", tt.getSectionID().toString());
		List list = getService().getListBySql(psql);
		this.request.setAttribute("list", list);

		return "success";
	}

	public String deleteTradeTime() throws Exception {
		this.logger.debug("enter deleteTradeTime");
		String[] ids = this.request.getParameterValues("ids");
		if ((ids == null) || (ids.length == 0)) {
			throw new IllegalArgumentException("删除主键数组不能为空！");
		}

		if (this.entity == null) {
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量删除！");
		}

		if ((this.entity.fetchPKey() == null) || (this.entity.fetchPKey().getKey() == null) || (this.entity.fetchPKey().getKey().length() == 0)) {
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量删除！");
		}
		try {
			boolean isPass = true;
			if (ids != null) {
				for (String id : ids) {
					int result = this.daySectionService.checkTradeTime(id);
					if (result == -1) {
						addReturnValue(0, 151103L, new Object[] { id + "交易节与品种关联，不能删除请重新选择！" });
						isPass = false;
						break;
					}
					if (result == -2) {
						addReturnValue(0, 151103L, new Object[] { id + "交易节与商品关联，不能删除请重新选择！" });
						isPass = false;
						break;
					}
				}
			}

			if (isPass) {
				Type keyType = this.entity.getClass().getDeclaredField(this.entity.fetchPKey().getKey()).getType();
				Object[] objIds;
				if (keyType.equals(Long.class)) {
					objIds = new Long[ids.length];
					for (int i = 0; i < ids.length; i++) {
						objIds[i] = Long.valueOf(ids[i]);
					}

				} else if (keyType.equals(Integer.class)) {
					objIds = new Integer[ids.length];
					for (int i = 0; i < ids.length; i++)
						objIds[i] = Integer.valueOf(ids[i]);
				} else {
					objIds = ids;
				}

				getService().deleteBYBulk(this.entity, (Object[]) objIds);
				this.daySectionService.deleteDaySection((Object[]) objIds);

				addReturnValue(1, 119903L);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}

	public String getNotTradeDay() throws Exception {
		this.logger.debug("enter getNotTradeDay");
		PageRequest pageRequest = new PageRequest();

		pageRequest.setPageNumber(1);

		pageRequest.setPageSize(1);
		Page page = getService().getPage(pageRequest, new NotTradeDay());
		List list = page.getResult();

		if ((list != null) && (list.size() > 0)) {
			this.entity = ((StandardModel) list.get(0));
		}

		List tradeTimeTypeList = getService().getListBySql("select t.tradeTimeType from T_A_Market t where t.marketcode='99'");
		Map map = (Map) tradeTimeTypeList.get(0);
		String tradeTimeType = map.get("TRADETIMETYPE").toString();

		this.request.setAttribute("tradeTimeType", tradeTimeType);

		return "success";
	}

	public String detailCalendar() {
		this.logger.debug("enter detailCalendar");

		String year = this.request.getParameter("year");
		String month = this.request.getParameter("month");

		String[] days = new String[42];
		Map bcs = new HashMap();
		for (int i = 0; i < 42; i++) {
			days[i] = "";
		}

		Calendar thisMonth = Calendar.getInstance();
		thisMonth.set(5, 1);
		if ((month != null) && (!month.equals("null")))
			thisMonth.set(2, Integer.parseInt(month));
		if ((year != null) && (!year.equals("null")))
			thisMonth.set(1, Integer.parseInt(year));
		year = String.valueOf(thisMonth.get(1));
		int m = thisMonth.get(2) + 1;
		month = String.valueOf(thisMonth.get(2));
		String month1 = "";
		if (m < 10) {
			month1 = String.valueOf("0" + m);
		} else {
			month1 = String.valueOf(m);
		}
		String yAm = year + "-" + month1;
		this.request.setAttribute("year", year);
		this.request.setAttribute("month", month);
		this.request.setAttribute("days", days);
		this.request.setAttribute("bcs", bcs);
		this.request.setAttribute("yAm", yAm);
		this.request.setAttribute("thisMonth", thisMonth);

		String psql = getSql().replace("?", yAm);
		List list = getService().getListBySql(psql);
		this.request.setAttribute("list", list);

		list = getService().getListBySql("select week,day from T_A_NotTradeDay");
		this.request.setAttribute("list1", list);

		return "success";
	}

	public String updateTradeDay() throws Exception {
		this.logger.debug("enter getNotTradeDay");
		PageRequest pageRequest = new PageRequest();

		pageRequest.setPageNumber(1);

		pageRequest.setPageSize(1);
		Page page = getService().getPage(pageRequest, new NotTradeDay());
		List list = page.getResult();
		NotTradeDay day = (NotTradeDay) this.entity;
		day.setModifyTime(getService().getSysDate());
		if ((list != null) && (list.size() > 0)) {
			StandardModel model = (StandardModel) list.get(0);
			Copy.copy(day, model);
			getService().update(model, true);
			addReturnValue(1, 119902L);
			return "success";
		}
		return add();
	}

	public String getTariffs() throws Exception {
		this.logger.debug("enter getTariffs");

		Map allMap = new LinkedHashMap();
		allMap.put("T0", "加收0%");
		for (int i = 10; i < 31; i++) {
			String key = "T" + i + "0";
			String value = "加收" + i + "0%";
			allMap.put(key, value);
		}
		List allList = this.tps.queryTariffSimples(getSql(), "%%");
		Set allSet = new TreeSet(allList);
		if (allSet != null) {
			for (Iterator localIterator = allList.iterator(); localIterator.hasNext();) {
				Object tariff = localIterator.next();
				allMap.remove(((TariffSimple) tariff).getTariffID());
			}
		}
		this.request.setAttribute("allMap", allMap);

		PageRequest pageRequest = super.getPageRequest(this.request);

		int pageNumber = RequestUtils.getPageNo(this.request);
		if (pageNumber <= 0) {
			pageNumber = 1;
		}
		pageRequest.setPageNumber(pageNumber);

		int pageSize = RequestUtils.getCurrentRowsDisplayed(this.request);
		if (pageSize <= 0) {
			pageSize = ECSideContext.DEFAULT_PAGE_SIZE;
		}

		pageRequest.setPageSize(pageSize);
		String tariffID = this.request.getParameter("gnnt_primary.tariffID");
		if (tariffID == null) {
			tariffID = "";
		}
		List list = this.tps.queryTariffSimples(getSql(), "%" + tariffID + "%");
		Set tariffSet = new TreeSet(list);
		Page page = new Page(pageRequest.getPageNumber(), pageRequest.getPageSize(), tariffSet.size(), Arrays.asList(tariffSet.toArray()));
		this.request.setAttribute("pageInfo", page);

		this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));

		return "success";
	}

	public String addTariffs() throws Exception {
		this.logger.debug("enter getTariffs");

		String tariffID = this.request.getParameter("tariffID");
		String tariffName = this.request.getParameter("tariffName");
		List list = this.tps.executeSelect("findAllCommodity");
		if (list.size() > 0) {
			User user = (User) this.request.getSession().getAttribute("CurrentUser");
			this.tps.addTariffs(list, tariffID, tariffName, user);
			addReturnValue(1, 119901L);
			writeOperateLog(1504, "添加手续费套餐" + tariffID, 1, "");
		} else {
			addReturnValue(-1, 151102L, new Object[] { "没有可操作的商品" });
			writeOperateLog(1504, "添加手续费套餐" + tariffID + "失败", 0, "");
		}

		return "success";
	}

	public String deleteTariff() throws Exception {
		this.logger.debug("enter deleteTariff");

		String[] ids = this.request.getParameterValues("ids");
		if ((ids == null) || (ids.length == 0)) {
			throw new IllegalArgumentException("删除主键数组不能为空！");
		}

		if (this.entity == null) {
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量删除！");
		}

		if ((this.entity.fetchPKey() == null) || (this.entity.fetchPKey().getKey() == null) || (this.entity.fetchPKey().getKey().length() == 0)) {
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量删除！");
		}

		boolean isPass = true;
		if (ids != null) {
			for (String id : ids) {
				isPass = this.tps.checkTariff(id);
				if (!isPass) {
					addReturnValue(0, 151103L, new Object[] { id + "套餐，交易商正在使用，不能删除！" });
					break;
				}

			}

		}

		if (isPass) {
			Type keyType = this.entity.getClass().getDeclaredField(this.entity.fetchPKey().getKey()).getType();
			Object[] objIds;
			if (keyType.equals(Long.class)) {
				objIds = new Long[ids.length];
				for (int i = 0; i < ids.length; i++) {
					objIds[i] = Long.valueOf(ids[i]);
				}

			} else if (keyType.equals(Integer.class)) {
				objIds = new Integer[ids.length];
				for (int i = 0; i < ids.length; i++)
					objIds[i] = Integer.valueOf(ids[i]);
			} else {
				objIds = ids;
			}

			getService().deleteBYBulk(this.entity, (Object[]) objIds);

			addReturnValue(1, 119903L);
		}

		return "success";
	}

	public String forwardAddCommodity() throws Exception {
		this.logger.debug("enter TradeParmasAction's forwardAddCommodity");

		Breed breed = new Breed();
		breed.setBreedID(Long.valueOf(Long.parseLong(this.request.getParameter("breedID"))));
		this.request.setAttribute("breedID", breed.getBreedID());

		Copy.copy(getService().get(breed), this.entity);

		Commodity commodity = (Commodity) this.entity;
		modifyCommodityByMul(commodity);

		String type101 = "1";
		String type102 = "1";
		String type103 = "1";
		String type104 = "1";
		String type105 = "1";

		if ((commodity.getLimitCmdtyQty() != null) && ("-1".equals(commodity.getLimitCmdtyQty().toString()))) {
			type101 = "2";
		}
		if ((commodity.getFirmCleanQty() != null) && ("-1".equals(commodity.getFirmCleanQty().toString()))) {
			type102 = "2";
		}
		if ((commodity.getFirmMaxHoldQty() != null) && ("-1".equals(commodity.getFirmMaxHoldQty().toString()))) {
			type103 = "2";
		}
		if ((commodity.getOneMaxHoldQty() != null) && ("-1".equals(commodity.getOneMaxHoldQty().toString()))) {
			type104 = "2";
		}
		this.request.setAttribute("type101", type101);
		this.request.setAttribute("type102", type102);
		this.request.setAttribute("type103", type103);
		this.request.setAttribute("type104", type104);
		this.request.setAttribute("type105", type105);
		this.request.setAttribute("typeFeeAlgr", commodity.getFeeAlgr().toString());
		this.request.setAttribute("typeSettleFeeAlgr", commodity.getSettleFeeAlgr().toString());

		return "success";
	}

	public String getCmdtyByBreedID() throws Exception {
		this.logger.debug("enter getCmdtyByBreedID");

		String breedID = this.request.getParameter("breedID");

		if (breedID == null) {
			breedID = this.request.getAttribute("breedID").toString();
		}

		PageRequest pageRequest = super.getPageRequest(this.request);
		QueryConditions qc = (QueryConditions) pageRequest.getFilters();

		if ("cur".equals(getOpr())) {
			this.entity = new Commodity();
			setOpr("cur");
		} else if ("his".equals(getOpr())) {
			this.entity = new SettleCommodity();

			qc.addCondition("primary.commodityID", "not in", "(select commodityID from Commodity)");
			setOpr("his");
		}

		qc.addCondition("primary.breedID", "=", Long.valueOf(Long.parseLong(breedID)));

		this.request.setAttribute("breedID", breedID);

		listByLimit(pageRequest);

		return "success";
	}

	public String updateCommodityStatus() throws Exception {
		this.logger.debug("enter updateCommodityStatus");
		String breedID = this.request.getParameter("breedID");
		String status = this.request.getParameter("status");
		String[] ids = this.request.getParameterValues("ids");

		getService().updateBYBulk(this.entity, "status=" + status, ids);

		this.request.setAttribute("breedID", breedID);
		return "success";
	}

	public String viewByIdCommodity() throws Exception {
		this.logger.debug("enter viewByIdCommodity");

		String commodityID = this.request.getParameter("commodityID");
		this.request.setAttribute("opr", this.request.getParameter("opr"));

		Commodity commodity = new Commodity();
		commodity.setCommodityID(commodityID);
		commodity = (Commodity) getService().get(commodity);
		this.request.setAttribute("breedID", commodity.getBreedID());

		modifyCommodityByMul(commodity);

		String type101 = "1";
		String type102 = "1";
		String type103 = "1";
		String type104 = "1";
		String type105 = "1";

		if ((commodity.getLimitCmdtyQty() != null) && ("-1".equals(commodity.getLimitCmdtyQty().toString()))) {
			type101 = "2";
		}
		if ((commodity.getFirmCleanQty() != null) && ("-1".equals(commodity.getFirmCleanQty().toString()))) {
			type102 = "2";
		}
		if ((commodity.getFirmMaxHoldQty() != null) && ("-1".equals(commodity.getFirmMaxHoldQty().toString()))) {
			type103 = "2";
		}
		if ((commodity.getOneMaxHoldQty() != null) && ("-1".equals(commodity.getOneMaxHoldQty().toString()))) {
			type104 = "2";
		}
		this.request.setAttribute("type101", type101);
		this.request.setAttribute("type102", type102);
		this.request.setAttribute("type103", type103);
		this.request.setAttribute("type104", type104);
		this.request.setAttribute("type105", type105);
		this.request.setAttribute("typeFeeAlgr", commodity.getFeeAlgr().toString());
		this.request.setAttribute("typeSettleFeeAlgr", commodity.getSettleFeeAlgr().toString());

		this.entity = commodity;

		return "success";
	}

	public String viewByIdHisCommodity() throws Exception {
		this.logger.debug("enter viewByIdHisCommodity");

		String commodityID = this.request.getParameter("commodityID");
		String date = this.request.getParameter("d");
		setOpr(this.request.getParameter("opr"));

		SettleCommodity commodity = new SettleCommodity();
		commodity.setCommodityID(commodityID);

		commodity.setSettleProcessDate(Tools.strToDate(date));
		commodity = (SettleCommodity) getService().get(commodity);
		this.request.setAttribute("breedID", commodity.getBreedID());

		modifyCommodityByMul(commodity);

		String type101 = "1";
		String type102 = "1";
		String type103 = "1";
		String type104 = "1";
		String type105 = "1";

		if ((commodity.getLimitCmdtyQty() != null) && ("-1".equals(commodity.getLimitCmdtyQty().toString()))) {
			type101 = "2";
		}
		if ((commodity.getFirmCleanQty() != null) && ("-1".equals(commodity.getFirmCleanQty().toString()))) {
			type102 = "2";
		}
		if ((commodity.getFirmMaxHoldQty() != null) && ("-1".equals(commodity.getFirmMaxHoldQty().toString()))) {
			type103 = "2";
		}
		if ((commodity.getOneMaxHoldQty() != null) && ("-1".equals(commodity.getOneMaxHoldQty().toString()))) {
			type104 = "2";
		}
		this.request.setAttribute("type101", type101);
		this.request.setAttribute("type102", type102);
		this.request.setAttribute("type103", type103);
		this.request.setAttribute("type104", type104);
		this.request.setAttribute("type105", type105);
		this.request.setAttribute("typeFeeAlgr", commodity.getFeeAlgr().toString());
		this.request.setAttribute("typeSettleFeeAlgr", commodity.getSettleFeeAlgr().toString());

		this.entity = commodity;

		return "success";
	}

	public String addCommodity() throws Exception {
		this.logger.debug("enter TradeParmasAction's addCommodity");

		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		Commodity commodity = (Commodity) this.entity;
		modifyCommodityByDiv(commodity);
		this.tps.addCommodity(commodity, user);
		this.request.setAttribute("breedID", commodity.getBreedID());

		addReturnValue(1, 119901L);
		writeOperateLog(1504, "添加商品" + commodity.getCommodityID(), 1, "");
		return "success";
	}

	public String updateCommodity() throws Exception {
		this.logger.debug("enter TradeParmasAction's updateCommodity");

		Commodity commodity = (Commodity) this.entity;
		if (commodity.getHoldDaysLimit().intValue() == 0) {
			commodity.setMaxHoldPositionDay(null);
		}

		modifyCommodityByDiv(commodity);
		this.request.setAttribute("breedID", commodity.getBreedID());

		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		QueryConditions qc = new QueryConditions("primary.commodityID", "=", commodity.getCommodityID());
		PageRequest pageRequest = new PageRequest(qc);

		pageRequest.setPageNumber(1);

		pageRequest.setPageSize(10000);
		Page page = getService().getPage(pageRequest, new Tariff());

		this.tps.updateCommodity(commodity, page.getResult(), user.getName());

		addReturnValue(1, 119902L);
		writeOperateLog(1504, "修改商品" + commodity.getCommodityID(), 1, "");
		return "success";
	}

	public String deleteCommodity() throws Exception {
		this.logger.debug("enter TradeParmasAction's deleteCommodity");
		try {
			List list = (List) this.request.getAttribute("deletesList");
			this.tps.deleteCommodity(list);

			addReturnValue(1, 119903L);
		} catch (Exception ex) {
			if ((ex instanceof DeleteCheckException))
				addReturnValue(-1, 151102L, new Object[] { ex.getMessage() });
			else {
				throw ex;
			}
		}

		return "success";
	}

	public String viewCmdtyByTariffID() throws Exception {
		this.logger.debug("enter viewCmdtyByTariffID");

		String tariffID = this.request.getParameter("tariffID");
		this.request.setAttribute("gnnt_primary.tariffID[=]", tariffID);
		this.request.setAttribute("tariffID", tariffID);
		this.request.setAttribute("tariffName", "加收" + tariffID.substring(1) + "%");
		return listByLimit();
	}

	private void modifyCommodityByMul(StandardModel model) {
		if ((model instanceof Commodity)) {
			Commodity commodity = (Commodity) model;
			if (commodity.getSpreadAlgr().shortValue() == 1) {
				commodity.setSpreadUpLmt(Double.valueOf(Arith.mul(commodity.getSpreadUpLmt().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setSpreadDownLmt(Double.valueOf(Arith.mul(commodity.getSpreadDownLmt().doubleValue(), new Double(100.0D).doubleValue())));
			}

			if ((commodity.getFirmMaxHoldQtyAlgr() != null) && (commodity.getFirmMaxHoldQtyAlgr().shortValue() == 1)) {
				commodity.setMaxPercentLimit(Double.valueOf(Arith.mul(commodity.getMaxPercentLimit().doubleValue(), 100.0F)));
			}
			if (commodity.getFeeAlgr().shortValue() == 1) {
				commodity.setFeeRate_B(Double.valueOf(Arith.mul(commodity.getFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setFeeRate_S(Double.valueOf(Arith.mul(commodity.getFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setHistoryCloseFeeRate_B(
						Double.valueOf(Arith.mul(commodity.getHistoryCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setHistoryCloseFeeRate_S(
						Double.valueOf(Arith.mul(commodity.getHistoryCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setTodayCloseFeeRate_B(
						Double.valueOf(Arith.mul(commodity.getTodayCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setTodayCloseFeeRate_S(
						Double.valueOf(Arith.mul(commodity.getTodayCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setForceCloseFeeRate_B(
						Double.valueOf(Arith.mul(commodity.getForceCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setForceCloseFeeRate_S(
						Double.valueOf(Arith.mul(commodity.getForceCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
			}
			if (commodity.getSettleFeeAlgr().shortValue() == 1) {
				commodity.setSettleFeeRate_B(
						Double.valueOf(Arith.mul(commodity.getSettleFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setSettleFeeRate_S(
						Double.valueOf(Arith.mul(commodity.getSettleFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
			}
			if (commodity.getSettleMarginAlgr_B().shortValue() == 1) {
				commodity.setSettleMarginRate_B(
						Double.valueOf(Arith.mul(commodity.getSettleMarginRate_B().doubleValue(), new Double(100.0D).doubleValue())));
			}
			if (commodity.getSettleMarginAlgr_S().shortValue() == 1) {
				commodity.setSettleMarginRate_S(
						Double.valueOf(Arith.mul(commodity.getSettleMarginRate_S().doubleValue(), new Double(100.0D).doubleValue())));
			}
			if (commodity.getPayoutAlgr().shortValue() == 1) {
				commodity.setPayoutRate(Double.valueOf(Arith.mul(commodity.getPayoutRate().doubleValue(), new Double(100.0D).doubleValue())));
			}
			if (commodity.getMarginAlgr().shortValue() == 1) {
				commodity.setMarginItem1(Double.valueOf(Arith.mul(commodity.getMarginItem1().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItem1_S(Double.valueOf(Arith.mul(commodity.getMarginItem1_S().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItemAssure1(
						Double.valueOf(Arith.mul(commodity.getMarginItemAssure1().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItemAssure1_S(
						Double.valueOf(Arith.mul(commodity.getMarginItemAssure1_S().doubleValue(), new Double(100.0D).doubleValue())));
				if (commodity.getMarginItem2() != null) {
					commodity.setMarginItem2(Double.valueOf(Arith.mul(commodity.getMarginItem2().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItem2_S(
							Double.valueOf(Arith.mul(commodity.getMarginItem2_S().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure2(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure2().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure2_S(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure2_S().doubleValue(), new Double(100.0D).doubleValue())));
				}
				if (commodity.getMarginItem3() != null) {
					commodity.setMarginItem3(Double.valueOf(Arith.mul(commodity.getMarginItem3().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItem3_S(
							Double.valueOf(Arith.mul(commodity.getMarginItem3_S().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure3(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure3().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure3_S(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure3_S().doubleValue(), new Double(100.0D).doubleValue())));
				}
				if (commodity.getMarginItem4() != null) {
					commodity.setMarginItem4(Double.valueOf(Arith.mul(commodity.getMarginItem4().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItem4_S(
							Double.valueOf(Arith.mul(commodity.getMarginItem4_S().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure4(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure4().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure4_S(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure4_S().doubleValue(), new Double(100.0D).doubleValue())));
				}
				if (commodity.getMarginItem5() != null) {
					commodity.setMarginItem5(Double.valueOf(Arith.mul(commodity.getMarginItem5().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItem5_S(
							Double.valueOf(Arith.mul(commodity.getMarginItem5_S().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure5(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure5().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure5_S(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure5_S().doubleValue(), new Double(100.0D).doubleValue())));
				}
			}
			commodity.setAddedTax(Double.valueOf(Arith.mul(commodity.getAddedTax().doubleValue(), new Double(100.0D).doubleValue())));

			commodity.setDelayRecoupRate(Double.valueOf(Arith.mul(commodity.getDelayRecoupRate().doubleValue(), new Double(100.0D).doubleValue())));
			commodity.setDelayRecoupRate_S(
					Double.valueOf(Arith.mul(commodity.getDelayRecoupRate_S().doubleValue(), new Double(100.0D).doubleValue())));

			commodity.setMaxFeeRate(
					Double.valueOf(Arith.mul((commodity.getMaxFeeRate() == null ? new Double(0.0D) : commodity.getMaxFeeRate()).doubleValue(),
							new Double(100.0D).doubleValue())));
		} else {
			SettleCommodity commodity = (SettleCommodity) model;
			if (commodity.getSpreadAlgr().shortValue() == 1) {
				commodity.setSpreadUpLmt(Double.valueOf(Arith.mul(commodity.getSpreadUpLmt().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setSpreadDownLmt(Double.valueOf(Arith.mul(commodity.getSpreadDownLmt().doubleValue(), new Double(100.0D).doubleValue())));
			}

			if ((commodity.getFirmMaxHoldQtyAlgr() != null) && (commodity.getFirmMaxHoldQtyAlgr().shortValue() == 1)) {
				commodity.setMaxPercentLimit(Double.valueOf(Arith.mul(commodity.getMaxPercentLimit().doubleValue(), 100.0F)));
			}
			if (commodity.getFeeAlgr().shortValue() == 1) {
				commodity.setFeeRate_B(Double.valueOf(Arith.mul(commodity.getFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setFeeRate_S(Double.valueOf(Arith.mul(commodity.getFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setHistoryCloseFeeRate_B(
						Double.valueOf(Arith.mul(commodity.getHistoryCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setHistoryCloseFeeRate_S(
						Double.valueOf(Arith.mul(commodity.getHistoryCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setTodayCloseFeeRate_B(
						Double.valueOf(Arith.mul(commodity.getTodayCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setTodayCloseFeeRate_S(
						Double.valueOf(Arith.mul(commodity.getTodayCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setForceCloseFeeRate_B(
						Double.valueOf(Arith.mul(commodity.getForceCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setForceCloseFeeRate_S(
						Double.valueOf(Arith.mul(commodity.getForceCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
			}
			if (commodity.getSettleFeeAlgr().shortValue() == 1) {
				commodity.setSettleFeeRate_B(
						Double.valueOf(Arith.mul(commodity.getSettleFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setSettleFeeRate_S(
						Double.valueOf(Arith.mul(commodity.getSettleFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
			}
			if (commodity.getSettleMarginAlgr_B().shortValue() == 1) {
				commodity.setSettleMarginRate_B(
						Double.valueOf(Arith.mul(commodity.getSettleMarginRate_B().doubleValue(), new Double(100.0D).doubleValue())));
			}
			if (commodity.getSettleMarginAlgr_S().shortValue() == 1) {
				commodity.setSettleMarginRate_S(
						Double.valueOf(Arith.mul(commodity.getSettleMarginRate_S().doubleValue(), new Double(100.0D).doubleValue())));
			}
			if (commodity.getPayoutAlgr().shortValue() == 1) {
				commodity.setPayoutRate(Double.valueOf(Arith.mul(commodity.getPayoutRate().doubleValue(), new Double(100.0D).doubleValue())));
			}
			if (commodity.getMarginAlgr().shortValue() == 1) {
				commodity.setMarginItem1(Double.valueOf(Arith.mul(commodity.getMarginItem1().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItem1_S(Double.valueOf(Arith.mul(commodity.getMarginItem1_S().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItemAssure1(
						Double.valueOf(Arith.mul(commodity.getMarginItemAssure1().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItemAssure1_S(
						Double.valueOf(Arith.mul(commodity.getMarginItemAssure1_S().doubleValue(), new Double(100.0D).doubleValue())));
				if (commodity.getMarginItem2() != null) {
					commodity.setMarginItem2(Double.valueOf(Arith.mul(commodity.getMarginItem2().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItem2_S(
							Double.valueOf(Arith.mul(commodity.getMarginItem2_S().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure2(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure2().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure2_S(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure2_S().doubleValue(), new Double(100.0D).doubleValue())));
				}
				if (commodity.getMarginItem3() != null) {
					commodity.setMarginItem3(Double.valueOf(Arith.mul(commodity.getMarginItem3().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItem3_S(
							Double.valueOf(Arith.mul(commodity.getMarginItem3_S().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure3(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure3().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure3_S(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure3_S().doubleValue(), new Double(100.0D).doubleValue())));
				}
				if (commodity.getMarginItem4() != null) {
					commodity.setMarginItem4(Double.valueOf(Arith.mul(commodity.getMarginItem4().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItem4_S(
							Double.valueOf(Arith.mul(commodity.getMarginItem4_S().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure4(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure4().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure4_S(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure4_S().doubleValue(), new Double(100.0D).doubleValue())));
				}
				if (commodity.getMarginItem5() != null) {
					commodity.setMarginItem5(Double.valueOf(Arith.mul(commodity.getMarginItem5().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItem5_S(
							Double.valueOf(Arith.mul(commodity.getMarginItem5_S().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure5(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure5().doubleValue(), new Double(100.0D).doubleValue())));
					commodity.setMarginItemAssure5_S(
							Double.valueOf(Arith.mul(commodity.getMarginItemAssure5_S().doubleValue(), new Double(100.0D).doubleValue())));
				}
			}
			commodity.setAddedTax(Double.valueOf(Arith.mul(commodity.getAddedTax().doubleValue(), new Double(100.0D).doubleValue())));

			commodity.setDelayRecoupRate(Double.valueOf(Arith.mul(commodity.getDelayRecoupRate().doubleValue(), new Double(100.0D).doubleValue())));
			commodity.setDelayRecoupRate_S(
					Double.valueOf(Arith.mul(commodity.getDelayRecoupRate_S().doubleValue(), new Double(100.0D).doubleValue())));

			commodity.setMaxFeeRate(
					Double.valueOf(Arith.mul((commodity.getMaxFeeRate() == null ? new Double(0.0D) : commodity.getMaxFeeRate()).doubleValue(),
							new Double(100.0D).doubleValue())));
		}
	}

	private void modifyCommodityByDiv(Commodity commodity) {
		if (commodity.getSpreadAlgr().shortValue() == 1) {
			commodity.setSpreadUpLmt(Double.valueOf(Arith.div(commodity.getSpreadUpLmt().doubleValue(), new Double(100.0D).doubleValue())));
			commodity.setSpreadDownLmt(Double.valueOf(Arith.div(commodity.getSpreadDownLmt().doubleValue(), new Double(100.0D).doubleValue())));
		}
		if ((commodity.getFirmMaxHoldQtyAlgr() != null) && (commodity.getFirmMaxHoldQtyAlgr().shortValue() == 1)) {
			commodity.setMaxPercentLimit(Double.valueOf(Arith.div(commodity.getMaxPercentLimit().doubleValue(), 100.0F)));
		}
		if (commodity.getFeeAlgr().shortValue() == 1) {
			commodity.setFeeRate_B(Double.valueOf(Arith.div(commodity.getFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
			commodity.setFeeRate_S(Double.valueOf(Arith.div(commodity.getFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
			commodity.setHistoryCloseFeeRate_B(
					Double.valueOf(Arith.div(commodity.getHistoryCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
			commodity.setHistoryCloseFeeRate_S(
					Double.valueOf(Arith.div(commodity.getHistoryCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
			commodity.setTodayCloseFeeRate_B(
					Double.valueOf(Arith.div(commodity.getTodayCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
			commodity.setTodayCloseFeeRate_S(
					Double.valueOf(Arith.div(commodity.getTodayCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
			commodity.setForceCloseFeeRate_B(
					Double.valueOf(Arith.div(commodity.getForceCloseFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
			commodity.setForceCloseFeeRate_S(
					Double.valueOf(Arith.div(commodity.getForceCloseFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
		}
		if (commodity.getSettleFeeAlgr().shortValue() == 1) {
			commodity.setSettleFeeRate_B(Double.valueOf(Arith.div(commodity.getSettleFeeRate_B().doubleValue(), new Double(100.0D).doubleValue())));
			commodity.setSettleFeeRate_S(Double.valueOf(Arith.div(commodity.getSettleFeeRate_S().doubleValue(), new Double(100.0D).doubleValue())));
		}
		if (commodity.getSettleMarginAlgr_B().shortValue() == 1) {
			commodity.setSettleMarginRate_B(
					Double.valueOf(Arith.div(commodity.getSettleMarginRate_B().doubleValue(), new Double(100.0D).doubleValue())));
		}
		if (commodity.getSettleMarginAlgr_S().shortValue() == 1) {
			commodity.setSettleMarginRate_S(
					Double.valueOf(Arith.div(commodity.getSettleMarginRate_S().doubleValue(), new Double(100.0D).doubleValue())));
		}
		if (commodity.getPayoutAlgr().shortValue() == 1) {
			commodity.setPayoutRate(Double.valueOf(Arith.div(commodity.getPayoutRate().doubleValue(), new Double(100.0D).doubleValue())));
		}
		if (commodity.getMarginAlgr().shortValue() == 1) {
			commodity.setMarginItem1(Double.valueOf(Arith.div(commodity.getMarginItem1().doubleValue(), new Double(100.0D).doubleValue())));
			commodity.setMarginItem1_S(Double.valueOf(Arith.div(commodity.getMarginItem1_S().doubleValue(), new Double(100.0D).doubleValue())));
			commodity.setMarginItemAssure1(
					Double.valueOf(Arith.div(commodity.getMarginItemAssure1().doubleValue(), new Double(100.0D).doubleValue())));
			commodity.setMarginItemAssure1_S(
					Double.valueOf(Arith.div(commodity.getMarginItemAssure1_S().doubleValue(), new Double(100.0D).doubleValue())));
			if (commodity.getMarginItem2() != null) {
				commodity.setMarginItem2(Double.valueOf(Arith.div(commodity.getMarginItem2().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItem2_S(Double.valueOf(Arith.div(commodity.getMarginItem2_S().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItemAssure2(
						Double.valueOf(Arith.div(commodity.getMarginItemAssure2().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItemAssure2_S(
						Double.valueOf(Arith.div(commodity.getMarginItemAssure2_S().doubleValue(), new Double(100.0D).doubleValue())));
			}
			if (commodity.getMarginItem3() != null) {
				commodity.setMarginItem3(Double.valueOf(Arith.div(commodity.getMarginItem3().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItem3_S(Double.valueOf(Arith.div(commodity.getMarginItem3_S().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItemAssure3(
						Double.valueOf(Arith.div(commodity.getMarginItemAssure3().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItemAssure3_S(
						Double.valueOf(Arith.div(commodity.getMarginItemAssure3_S().doubleValue(), new Double(100.0D).doubleValue())));
			}
			if (commodity.getMarginItem4() != null) {
				commodity.setMarginItem4(Double.valueOf(Arith.div(commodity.getMarginItem4().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItem4_S(Double.valueOf(Arith.div(commodity.getMarginItem4_S().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItemAssure4(
						Double.valueOf(Arith.div(commodity.getMarginItemAssure4().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItemAssure4_S(
						Double.valueOf(Arith.div(commodity.getMarginItemAssure4_S().doubleValue(), new Double(100.0D).doubleValue())));
			}
			if (commodity.getMarginItem5() != null) {
				commodity.setMarginItem5(Double.valueOf(Arith.div(commodity.getMarginItem5().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItem5_S(Double.valueOf(Arith.div(commodity.getMarginItem5_S().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItemAssure5(
						Double.valueOf(Arith.div(commodity.getMarginItemAssure5().doubleValue(), new Double(100.0D).doubleValue())));
				commodity.setMarginItemAssure5_S(
						Double.valueOf(Arith.div(commodity.getMarginItemAssure5_S().doubleValue(), new Double(100.0D).doubleValue())));
			}
		}
		commodity.setAddedTax(Double.valueOf(Arith.div(commodity.getAddedTax().doubleValue(), new Double(100.0D).doubleValue())));

		commodity.setDelayRecoupRate(Double.valueOf(Arith.div(commodity.getDelayRecoupRate().doubleValue(), new Double(100.0D).doubleValue())));
		commodity.setDelayRecoupRate_S(Double.valueOf(Arith.div(commodity.getDelayRecoupRate_S().doubleValue(), new Double(100.0D).doubleValue())));

		commodity.setMaxFeeRate(Double.valueOf(Arith.div(
				(commodity.getMaxFeeRate() == null ? new Double(0.0D) : commodity.getMaxFeeRate()).doubleValue(), new Double(100.0D).doubleValue())));
	}

	public String setTradeDay() throws Exception {
		this.logger.debug("enter setTradeDay");

		PageRequest pageRequest = getPageRequest(this.request);

		pageRequest.setSortColumns("order by weekDay,sectionid");

		Page page = getService().getPage(pageRequest, this.entity);
		this.request.setAttribute("pageInfo", page);

		this.request.setAttribute("oldParams", getParametersStartingWith(this.request, "gnnt_"));

		List list = getService().getListBySql("select t.week from T_A_NotTradeDay t");

		String week = "";
		if ((list != null) && (list.size() > 0)) {
			Map map = (Map) list.get(0);
			if (map.get("WEEK") != null) {
				week = map.get("WEEK").toString();
			}
			this.daySectionService.updateDaySection(week);
		}

		this.request.setAttribute("week", week);
		return "success";
	}

	public String updateTradeDaySection() throws Exception {
		this.logger.debug("enter updateTradeDaySection");

		String[] weeks1 = this.request.getParameterValues("weeks1");
		String[] weeks2 = this.request.getParameterValues("weeks2");
		String[] weeks3 = this.request.getParameterValues("weeks3");
		String[] weeks4 = this.request.getParameterValues("weeks4");
		String[] weeks5 = this.request.getParameterValues("weeks5");
		String[] weeks6 = this.request.getParameterValues("weeks6");
		String[] weeks7 = this.request.getParameterValues("weeks7");
		Map mapWeek = new HashMap();
		for (int i = 1; i < 8; i++) {
			String ri = i + "";
			if ("1".equals(ri)) {
				mapWeek.put(ri, weeks1);
			}
			if ("2".equals(ri)) {
				mapWeek.put(ri, weeks2);
			}
			if ("3".equals(ri)) {
				mapWeek.put(ri, weeks3);
			}
			if ("4".equals(ri)) {
				mapWeek.put(ri, weeks4);
			}
			if ("5".equals(ri)) {
				mapWeek.put(ri, weeks5);
			}
			if ("6".equals(ri)) {
				mapWeek.put(ri, weeks6);
			}
			if ("7".equals(ri)) {
				mapWeek.put(ri, weeks7);
			}
		}

		this.daySectionService.updateDaySection(mapWeek);

		addReturnValue(1, 119902L);

		return "success";
	}

	public String forwardAdd1() {
		String sql = "select  k.name,k.sectionid,k.starttime,k.endtime from t_A_TradeTime k where k.sectionid = (select max(t.sectionid) from t_A_TradeTime t)";
		List list = getService().getListBySql(sql);
		if ((list != null) && (list.size() > 0)) {
			this.request.setAttribute("SECTIONID", ((Map) list.get(0)).get("SECTIONID"));
			this.request.setAttribute("STARTTIME", ((Map) list.get(0)).get("STARTTIME"));
			this.request.setAttribute("ENDTIME", ((Map) list.get(0)).get("ENDTIME"));
			this.request.setAttribute("NAME", ((Map) list.get(0)).get("NAME"));
		}
		return "success";
	}
}