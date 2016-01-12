package gnnt.MEBS.timebargain.mgr.action.settle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.ECSideContext;
import org.ecside.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.bill.core.service.IKernelService;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.timebargain.mgr.action.ECSideAction;
import gnnt.MEBS.timebargain.mgr.model.settle.BillFrozen;
import gnnt.MEBS.timebargain.mgr.model.settle.SettleProps;
import gnnt.MEBS.timebargain.mgr.service.BillService;

@Controller
@Scope("request")
public class BillAction extends ECSideAction {
	private static final long serialVersionUID = -7848335526358113367L;
	private List<?> cmdtySortList;

	@Autowired
	@Qualifier("billService")
	private BillService billService;

	@Autowired
	@Qualifier("billKernelService")
	private IKernelService kernelService;

	@Resource(name = "BS_flagMap")
	Map<String, String> bs_flagMap;

	@Resource(name = "settleMatch_settleTypeMap")
	Map<String, String> settleTypeMap;

	public List<?> getCmdtySortList() {
		return this.cmdtySortList;
	}

	public void setCmdtySortList(List<?> paramList) {
		this.cmdtySortList = paramList;
	}

	public static long getSerialversionuid() {
		return -7848335526358113367L;
	}

	public Map<String, String> getSettleTypeMap() {
		return this.settleTypeMap;
	}

	public void setSettleTypeMap(Map<String, String> paramMap) {
		this.settleTypeMap = paramMap;
	}

	public Map<String, String> getBs_flagMap() {
		return this.bs_flagMap;
	}

	public void setBs_flagMap(Map<String, String> paramMap) {
		this.bs_flagMap = paramMap;
	}

	public IKernelService getKernelService() {
		return this.kernelService;
	}

	public void setKernelService(IKernelService paramIKernelService) {
		this.kernelService = paramIKernelService;
	}

	public BillService getBillService() {
		return this.billService;
	}

	public void setBillService(BillService paramBillService) {
		this.billService = paramBillService;
	}

	public String gageBillList() throws Exception {
		logger.debug("------------gageBillList 跳转到仓单列表--------------");
		PageRequest pagerequest = super.getPageRequest(request);
		int i = RequestUtils.getPageNo(request);
		if (i <= 0)
			i = 1;
		pagerequest.setPageNumber(i);
		int j = RequestUtils.getCurrentRowsDisplayed(request);
		if (j <= 0)
			j = ECSideContext.DEFAULT_PAGE_SIZE;
		pagerequest.setPageSize(j);
		String s = trim(request.getParameter("gnnt_gageBill.commodityID"));
		String s1 = trim(request.getParameter("gnnt_gageBill.firmId"));
		String s2 = "SELECT e.id, e.firmID, e.commodityId, e.quantity, e.remark, e.createtime, e.modifytime FROM T_E_GageBill e where 1=1";
		if (!"".equals(s) && s != null)
			s2 = (new StringBuilder()).append(s2).append(" AND e.commodityId like '%").append(s).append("%'").toString();
		if (!"".equals(s1) && s1 != null)
			s2 = (new StringBuilder()).append(s2).append(" AND e.firmId like '%").append(s1).append("%'").toString();
		List list = getService().getListBySql(s2);
		Object obj = list.iterator();
		do {
			if (!((Iterator) (obj)).hasNext())
				break;
			Map map = (Map) ((Iterator) (obj)).next();
			String s3 = String.valueOf(map.get("REMARK"));
			if (s3 != null && s3.length() > 16) {
				s3 = s3.substring(0, 15);
				s3 = (new StringBuilder()).append(s3).append("…").toString();
				map.put("REMARK", s3);
			}
		} while (true);
		obj = new Page(pagerequest.getPageNumber(), pagerequest.getPageSize(), list.size(), list);
		request.setAttribute("pageInfo", obj);
		request.setAttribute("oldParams", getParametersStartingWith(request, "gnnt_"));
		return "success";
	}

	public String getCommodity() throws Exception {
		logger.debug("------------getCommodity 跳转到仓单添加仓单详细列表页面--------------");
		List list = getService().getListBySql("select commodityId,name from t_commodity ");
		request.setAttribute("list", list);
		List list1 = billService.getFirmList();
		StringBuffer stringbuffer = new StringBuffer("[");
		for (int i = 0; i < list1.size(); i++) {
			stringbuffer.append("\"");
			stringbuffer.append(list1.get(i));
			stringbuffer.append("\",");
		}

		stringbuffer.deleteCharAt(stringbuffer.length() - 1);
		stringbuffer.append("]");
		request.setAttribute("json", stringbuffer.toString());
		return "success";
	}

	public String getBillList() throws Exception {
		logger.debug("---------------根据交易商代码和商品代码查询仓单详细信息 开始------------");
		List list = getService().getListBySql("select commodityId,name from t_commodity ");
		request.setAttribute("list", list);
		List list1 = billService.getFirmList();
		StringBuffer stringbuffer = new StringBuffer("[");
		for (int i = 0; i < list1.size(); i++) {
			stringbuffer.append("\"");
			stringbuffer.append(list1.get(i));
			stringbuffer.append("\",");
		}

		stringbuffer.deleteCharAt(stringbuffer.length() - 1);
		stringbuffer.append("]");
		request.setAttribute("json", stringbuffer.toString());
		String s = request.getParameter("gnnt_stock.commodityID");
		String s1 = trim(request.getParameter("gnnt_stock.firmID"));
		if (!list1.contains(s1)) {
			request.setAttribute("oldParams", getParametersStartingWith(request, "gnnt_"));
			request.setAttribute("firmMsg", "交易商代码不存在！");
			return "success";
		}
		if (s == null || "".equals(s) || s1 == null || "".equals(s1)) {
			logger.debug("---------------根据交易商代码和商品代码查询仓单详细信息 结束(交易商代码或者商品代码为空)------------");
			return "success";
		}
		String s2 = (new StringBuilder()).append("SELECT s.* FROM T_settleProps s WHERE s.CommodityID = '").append(s).append("'").toString();
		List list2 = getService().getListBySql(s2, entity);
		logger.debug("根据商品代码查询商品属性信息 成功");
		HashMap hashmap = new HashMap();
		Object obj = null;
		SettleProps settleprops;
		for (Iterator iterator = list2.iterator(); iterator.hasNext(); hashmap.put(settleprops.getPropertyName(), settleprops.getPropertyValue())) {
			StandardModel standardmodel = (StandardModel) iterator.next();
			settleprops = (SettleProps) standardmodel;
		}

		List list3 = getService().getListBySql((new StringBuilder()).append("select BreedID, ContractFactor from t_commodity where commodityID = '")
				.append(s).append("'").toString());
		String s3 = String.valueOf(((Map) list3.get(0)).get("BREEDID"));
		BigDecimal bigdecimal = (BigDecimal) ((Map) list3.get(0)).get("CONTRACTFACTOR");
		List list4 = null;
		list4 = kernelService.getUnusedStocks(Long.valueOf(s3).longValue(), hashmap, s1);
		logger.debug("******调用rmi获取仓单编号集合 成功******");
		writeOperateLog(1508, "通过RMI获取仓单编号集合", 1, "");
		if (list4 == null || list4.size() == 0) {
			request.setAttribute("oldParams", getParametersStartingWith(request, "gnnt_"));
			logger.debug("---------------根据交易商代码和商品代码查询仓单详细信息 结束(回仓单号数组长度为0)------------");
			return "success";
		} else {
			Page page = getBillDetail(list4, bigdecimal);
			request.setAttribute("pageInfo", page);
			request.setAttribute("oldParams", getParametersStartingWith(request, "gnnt_"));
			logger.debug("---------------根据交易商代码和商品代码查询仓单详细信息 结束------------");
			return "success";
		}
	}

	private Page getBillDetail(List list, BigDecimal bigdecimal) throws Exception {
		PageRequest pagerequest = super.getPageRequest(request);
		int i = RequestUtils.getPageNo(request);
		if (i <= 0)
			i = 1;
		pagerequest.setPageNumber(i);
		int j = RequestUtils.getCurrentRowsDisplayed(request);
		if (j <= 0)
			j = ECSideContext.DEFAULT_PAGE_SIZE;
		pagerequest.setPageSize(j);
		String s = (new StringBuilder())
				.append("select bs.stockid, bs.realstockcode, bs.breedid, bs.warehouseid, bs.quantity, bs.unit, bs.ownerfirm, to_char(bs.lasttime,'yyyy-MM-dd HH24:MI:SS') lasttime, bs.createtime, bs.stockstatus from BI_Stock bs where bs.stockid in (")
				.append(StringUtils.join(list, ',')).append(")").toString();
		List list1 = getService().getListBySql(s);
		String s1 = "";
		if (list1.size() != 0) {
			List list2 = getService().getListBySql((new StringBuilder()).append("select BreedName from M_Breed where BreedID = ")
					.append(((Map) list1.get(0)).get("BREEDID")).toString());
			if (list2.size() != 0)
				s1 = String.valueOf(((Map) list2.get(0)).get("BREEDNAME"));
			else
				s1 = String.valueOf(((Map) list1.get(0)).get("BREEDID"));
		}
		Object obj = list1.iterator();
		do {
			if (!((Iterator) (obj)).hasNext())
				break;
			Map map = (Map) ((Iterator) (obj)).next();
			map.put("QUANTITYUNIT",
					(new StringBuilder()).append(map.get("QUANTITY")).append("(").append(String.valueOf(map.get("UNIT"))).append(")").toString());
			map.put("BREEDNAME", s1);
			BigDecimal bigdecimal1 = (BigDecimal) map.get("QUANTITY");
			if (bigdecimal != null) {
				double d = bigdecimal1.doubleValue() / bigdecimal.doubleValue();
				if ((double) Math.round(d) != d) {
					String s2 = String.valueOf(d);
					int k = s2.indexOf(".");
					if (s2.substring(k + 1).length() >= 4)
						s2 = s2.substring(0, k + 4);
					map.put("BILLNUM", s2);
				} else {
					map.put("BILLNUM", Long.valueOf((long) d));
				}
			}
		} while (true);
		obj = new Page(pagerequest.getPageNumber(), pagerequest.getPageSize(), list1.size(), list1);
		return ((Page) (obj));
	}

	public String addGageBill() throws Exception {
		logger.debug("------------addBill 添加仓单--------------");
		try {
			String as[] = request.getParameterValues("ids");
			if (as == null || as.length == 0)
				throw new IllegalArgumentException("添加仓单，仓单不能为空!");
			String s = request.getParameter("commodityID");
			String s1 = request.getParameter("remarkHdden");
			int i = billService.addBill(as, s, s1);
			if (i == 1) {
				addReturnValue(1, 0x24d19L);
				writeOperateLog(1508, "添加仓单成功", 1, "");
			} else if (i == -1) {
				addReturnValue(1, 0x24d29L);
				writeOperateLog(1508, "添加仓单失败", 0, "所选仓单数量出现小数");
			}
		} catch (Exception exception) {
			logger.debug((new StringBuilder()).append("添加仓单 出错：").append(exception.getMessage()).toString());
			exception.printStackTrace();
			addReturnValue(-1, 0x24d20L, new Object[] { (new StringBuilder()).append("操作失败，原因：").append(exception.getMessage()).toString() });
			writeOperateLog(1508, "添加仓单失败", 0, (new StringBuilder()).append("操作失败，原因：").append(exception.getMessage()).toString());
		}
		return "success";
	}

	public String gageBillDetail() throws Exception {
		logger.debug("-------------------仓单详情---------------");
		String s = request.getParameter("gageBillID");
		String s1 = request.getParameter("commodityID");
		List list = getService().getListBySql((new StringBuilder()).append("select BreedID, ContractFactor from t_commodity where commodityID = '")
				.append(s1).append("'").toString());
		BigDecimal bigdecimal = (BigDecimal) ((Map) list.get(0)).get("CONTRACTFACTOR");
		List list1 = getService().getListBySql(
				(new StringBuilder()).append("select * from T_billFrozen  where operation = '").append(s).append("'").toString(), entity);
		ArrayList arraylist = new ArrayList(list1.size());
		if (list1 != null && list1.size() != 0) {
			for (int i = 0; i < list1.size(); i++)
				arraylist.add(i, ((BillFrozen) list1.get(i)).getBillID());

			List list2 = getService()
					.getListBySql((new StringBuilder()).append("select remark from T_E_GageBill where id = '").append(s).append("'").toString());
			Page page = getBillDetail(arraylist, bigdecimal);
			request.setAttribute("remark", list2);
			request.setAttribute("gageBillID", s);
			request.setAttribute("pageInfo", page);
		}
		request.setAttribute("oldParams", getParametersStartingWith(request, "gnnt_"));
		return "success";
	}

	public String queryBillList() throws Exception {
		logger.debug("------------ 跳转到仓单撤销列表-------------");
		List list = getService().getListBySql("select commodityId,name from t_commodity ");
		request.setAttribute("list", list);
		List list1 = billService.getFirmList();
		StringBuffer stringbuffer = new StringBuffer("[");
		for (int i = 0; i < list1.size(); i++) {
			stringbuffer.append("\"");
			stringbuffer.append(list1.get(i));
			stringbuffer.append("\",");
		}

		stringbuffer.deleteCharAt(stringbuffer.length() - 1);
		stringbuffer.append("]");
		request.setAttribute("json", stringbuffer.toString());
		String s = request.getParameter("gnnt_stock.commodityID");
		String s1 = trim(request.getParameter("gnnt_stock.firmID"));
		String s2 = trim(request.getParameter("gnnt_stock.billID"));
		String s3 = "select e.id, b.id as billFrozenId, e.firmID, e.commodityId, e.quantity, e.remark, to_char(e.createtime,'yyyy-MM-dd HH24:MI:SS') createtime, e.modifytime, b.BillID, b.Operation from T_billFrozen b , T_E_GageBill e where 1=1 AND b.operationtype = 0 AND b.Operation = e.id ";
		if (s2 != null && !"".equals(s2))
			s3 = (new StringBuilder()).append(s3).append(" AND b.BillID = '").append(s2).append("'").toString();
		if (s1 != null && !"".equals(s1))
			s3 = (new StringBuilder()).append(s3).append(" AND e.firmID like '%").append(s1).append("%'").toString();
		if (s != null && !"".equals(s))
			s3 = (new StringBuilder()).append(s3).append(" AND e.commodityId = '").append(s).append("'").toString();
		s3 = (new StringBuilder()).append(s3).append(" order by e.createtime").toString();
		List list2 = getService().getListBySql(s3);
		if (list2 == null || list2.size() == 0) {
			request.setAttribute("oldParams", getParametersStartingWith(request, "gnnt_"));
			return "success";
		}
		ArrayList arraylist = new ArrayList();
		for (int j = 0; j < list2.size(); j++) {
			Map map = (Map) list2.get(j);
			arraylist.add(String.valueOf(map.get("BILLID")));
		}

		String s4 = (new StringBuilder()).append("select bs.stockid, bs.quantity, bs.unit from BI_Stock bs where bs.stockid in (")
				.append(StringUtils.join(arraylist, ',')).append(")").toString();
		List list3 = getService().getListBySql(s4);
		HashMap hashmap = new HashMap();
		Map map1;
		BigDecimal bigdecimal;
		for (Iterator iterator = list3.iterator(); iterator.hasNext(); hashmap.put(String.valueOf(map1.get("STOCKID")), bigdecimal)) {
			map1 = (Map) iterator.next();
			bigdecimal = (BigDecimal) map1.get("QUANTITY");
		}

		for (int k = 0; k < list2.size(); k++) {
			Map map2 = (Map) list2.get(k);
			String s5 = (String) map2.get("COMMODITYID");
			List list4 = getService().getListBySql((new StringBuilder()).append("select Name, ContractFactor from t_commodity where commodityID = '")
					.append(s5).append("'").toString());
			BigDecimal bigdecimal1 = (BigDecimal) ((Map) list4.get(0)).get("CONTRACTFACTOR");
			map2.put("COMMODITYNAME", ((Map) list4.get(0)).get("NAME"));
			map2.put("BILLNUM", Long.valueOf((long) (((BigDecimal) hashmap.get(map2.get("BILLID"))).doubleValue() / bigdecimal1.doubleValue())));
		}

		PageRequest pagerequest = super.getPageRequest(request);
		int l = RequestUtils.getPageNo(request);
		if (l <= 0)
			l = 1;
		pagerequest.setPageNumber(l);
		int i1 = RequestUtils.getCurrentRowsDisplayed(request);
		if (i1 <= 0)
			i1 = ECSideContext.DEFAULT_PAGE_SIZE;
		pagerequest.setPageSize(i1);
		Page page = new Page(pagerequest.getPageNumber(), pagerequest.getPageSize(), list2.size(), list2);
		request.setAttribute("pageInfo", page);
		request.setAttribute("oldParams", getParametersStartingWith(request, "gnnt_"));
		return "success";
	}

	public String billCancel() throws Exception {
		logger.debug("------------仓单撤销---------------");
		ArrayList arraylist = new ArrayList();
		ArrayList arraylist1 = new ArrayList();
		ArrayList arraylist2 = new ArrayList();
		try {
			String as[] = request.getParameterValues("ids");
			for (int i = 0; i < as.length; i++) {
				String as1[] = as[i].split("&");
				String s1 = as1[0];
				Long long1 = Long.valueOf(as1[1]);
				String s2 = as1[2];
				String s3 = as1[3];
				Long long2 = Long.valueOf(as1[4]);
				Long long3 = Long.valueOf(as1[5]);
				logger.debug((new StringBuilder()).append("待撤销仓单：仓单号【").append(s1).append("】仓单数量【").append(long1).append("】商品id【").append(s3)
						.append("】交易商id【").append(s2).append("】操作码【").append(long2).append("】id【").append(long3).append("】").toString());
				Long long4 = billService.billCancel(s1, s2, long1, s3, long2, long3);
				if (long4.longValue() == 1L)
					arraylist.add(s1);
				if (long4.longValue() == -1L)
					arraylist1.add(s1);
				if (long4.longValue() == -2L)
					arraylist2.add(s1);
			}

			String s = "";
			if (!arraylist2.isEmpty()) {
				s = (new StringBuilder()).append(s).append(arraylist2.toString()).append("失败,撤销后仓单将出现负数！\\n").toString();
				writeOperateLog(1508, (new StringBuilder()).append("撤销仓单").append(arraylist2.toString()).append("】失败,撤销后仓单将出现负数！").toString(), 0,
						"撤销后仓单将出现负数");
			}
			if (!arraylist1.isEmpty()) {
				s = (new StringBuilder()).append(s).append(arraylist1.toString()).append("失败,可用仓单数量不足！\\n").toString();
				writeOperateLog(1508, (new StringBuilder()).append("撤销仓单【").append(arraylist1.toString()).append("】失败,可用仓单数量不足！").toString(), 0,
						"可用仓单数量不足");
			}
			if (!arraylist.isEmpty()) {
				s = (new StringBuilder()).append(s).append(arraylist.toString()).append("撤销成功！").toString();
				writeOperateLog(1508, (new StringBuilder()).append("撤销仓单【").append(arraylist.toString()).append("】成功").toString(), 1, "");
			}
			addReturnValue(-1, 0x24d16L, new Object[] { s });
		} catch (Exception exception) {
			logger.debug((new StringBuilder()).append("仓单撤销 出错：").append(exception.getMessage()).toString());
			exception.printStackTrace();
			addReturnValue(-1, 0x24d18L, new Object[] { (new StringBuilder()).append("操作失败，原因：").append(exception.getMessage()).toString() });
			writeOperateLog(1508, "撤销仓单失败", 0, (new StringBuilder()).append("操作失败，原因：").append(exception.getMessage()).toString());
		}
		return "success";
	}

	public String gageDataQuery() throws Exception {
		logger.debug("------------抵顶数据查询---------------");
		PageRequest pagerequest = super.getPageRequest(request);
		int i = RequestUtils.getPageNo(request);
		if (i <= 0)
			i = 1;
		pagerequest.setPageNumber(i);
		int j = RequestUtils.getCurrentRowsDisplayed(request);
		if (j <= 0)
			j = ECSideContext.DEFAULT_PAGE_SIZE;
		pagerequest.setPageSize(j);
		String s = trim(request.getParameter("gnnt_gageBill.commodityID"));
		String s1 = trim(request.getParameter("gnnt_gageBill.firmId"));
		String s2 = "select t.firmid,t.customerid,t.commodityid,t.bs_flag flag,(t.HoldQty + t.GageQty) HoldQty,t.gageqty  from t_customerholdsum t where t.BS_Flag=2 and t.GageQty > 0";
		if (!"".equals(s) && s != null)
			s2 = (new StringBuilder()).append(s2).append(" AND t.commodityId like '%").append(s).append("%'").toString();
		if (!"".equals(s1) && s1 != null)
			s2 = (new StringBuilder()).append(s2).append(" AND t.firmId like '%").append(s1).append("%'").toString();
		List list = getService().getListBySql(s2);
		Page page = new Page(pagerequest.getPageNumber(), pagerequest.getPageSize(), list.size(), list);
		request.setAttribute("pageInfo", page);
		request.setAttribute("oldParams", getParametersStartingWith(request, "gnnt_"));
		return "success";
	}

	public String showSettleData() {
		return "success";
	}

	public String listSettleDataDetail() throws Exception {
		logger.debug("------------交收数据明细查询---------------");
		PageRequest pagerequest = super.getPageRequest(request);
		int i = RequestUtils.getPageNo(request);
		if (i <= 0)
			i = 1;
		pagerequest.setPageNumber(i);
		int j = RequestUtils.getCurrentRowsDisplayed(request);
		if (j <= 0)
			j = ECSideContext.DEFAULT_PAGE_SIZE;
		pagerequest.setPageSize(j);
		String s = trim(request.getParameter("gnnt_gageBill.commodityID"));
		String s1 = trim(request.getParameter("gnnt_gageBill.firmId"));
		String s2 = request.getParameter("gnnt_settleDate");
		String s3 = request.getParameter("gnnt_bsFlag");
		String s4 = request.getParameter("gnnt_settleType");
		String s5 = "select shp.settleprocessdate, shp.firmid, shp.commodityid, shp.bs_flag, (shp.HoldQty + shp.GageQty) settleQty, shp.a_HoldNo, shp.openQty, shp.settleMargin, shp.payout, shp.settleFee, shp.settle_PL, shp.SettleAddedTax, shp.Price, shp.SettlePrice, shp.SettleType from T_SettleHoldPosition shp where 1=1";
		if (!"".equals(s) && s != null)
			s5 = (new StringBuilder()).append(s5).append(" AND shp.commodityId like '%").append(s).append("%'").toString();
		if (!"".equals(s1) && s1 != null)
			s5 = (new StringBuilder()).append(s5).append(" AND shp.firmId like '%").append(s1).append("%'").toString();
		if (!"".equals(s2) && s2 != null)
			s5 = (new StringBuilder()).append(s5).append(" AND shp.SettleProcessDate = to_date('").append(s2).append("','yyyy-mm-dd')").toString();
		if (!"".equals(s2) && s2 != null) {
			s5 = (new StringBuilder()).append(s5).append(" AND shp.SettleProcessDate = to_date('").append(s2).append("','yyyy-mm-dd')").toString();
		} else {
			String s6 = Tools.fmtDate(new Date());
			s5 = (new StringBuilder()).append(s5).append(" AND shp.SettleProcessDate = to_date('").append(s6).append("','yyyy-mm-dd')").toString();
			request.setAttribute("settleDateDefault", s6);
		}
		if (!"0".equals(s3) && !"".equals(s3) && s3 != null)
			s5 = (new StringBuilder()).append(s5).append(" AND shp.bs_flag = ").append(s3).toString();
		if (!"0".equals(s4) && !"".equals(s4) && s4 != null)
			s5 = (new StringBuilder()).append(s5).append(" AND shp.SettleType = ").append(s4).toString();
		List list = getService().getListBySql(s5);
		Page page = new Page(pagerequest.getPageNumber(), pagerequest.getPageSize(), list.size(), list);
		request.setAttribute("pageInfo", page);
		request.setAttribute("oldParams", getParametersStartingWith(request, "gnnt_"));
		return "success";
	}

	public String notPairTotal() {
		List list = getService().getListBySql("select distinct(s.CommodityID) from T_SettleHoldPosition s");
		request.setAttribute("commodityIDList", list);
		String s = "select s.firmid, nvl(sum(s.holdqty+s.gageqty-s.Happenmatchqty),0) notPairTotal from T_SettleHoldPosition s where 1=1 ";
		String s1 = (new StringBuilder()).append(s).append(" AND s.bs_flag = 1 ").toString();
		String s2 = request.getParameter("gnnt_commodityID");
		if (!"".equals(s2) && s2 != null) {
			s1 = (new StringBuilder()).append(s1).append(" AND s.commodityid = '").append(s2).append("'").toString();
			request.setAttribute("commodityID", s2);
		} else if (list != null && list.size() != 0) {
			String s3 = String.valueOf(((Map) list.get(0)).get("COMMODITYID"));
			s1 = (new StringBuilder()).append(s1).append("AND s.commodityid = '").append(s3).append("'").toString();
			request.setAttribute("commodityID", s3);
		}
		String s4 = request.getParameter("gnnt_settleDate");
		if (!"".equals(s4) && s4 != null) {
			s1 = (new StringBuilder()).append(s1).append(" AND s.SettleProcessDate = to_date('").append(s4).append("','yyyy-mm-dd')").toString();
		} else {
			String s5 = Tools.fmtDate(new Date());
			s1 = (new StringBuilder()).append(s1).append(" AND s.SettleProcessDate = to_date('").append(s5).append("','yyyy-mm-dd')").toString();
			request.setAttribute("settleDateDefault", s5);
		}
		s1 = (new StringBuilder()).append(s1).append(" group by s.firmid").toString();
		List list1 = getService().getListBySql(s1);
		String s6 = s1.replace("bs_flag = 1", "bs_flag = 2");
		List list2 = getService().getListBySql(s6);
		request.setAttribute("buyerNotPairTotalList", list1);
		request.setAttribute("sellerNotPairTotalList", list2);
		request.setAttribute("oldParams", getParametersStartingWith(request, "gnnt_"));
		return "success";
	}

	public String alreadyPairTotal() {
		List list = getService().getListBySql("select distinct(s.CommodityID) from T_SettleHoldPosition s");
		request.setAttribute("commodityIDList", list);
		String s = "select s.firmid,nvl(sum(s.Happenmatchqty),0) alreadyPairTotal from T_SettleHoldPosition s where 1=1 ";
		String s1 = (new StringBuilder()).append(s).append(" AND s.bs_flag = 1 ").toString();
		String s2 = request.getParameter("gnnt_commodityID");
		if (!"".equals(s2) && s2 != null) {
			s1 = (new StringBuilder()).append(s1).append(" AND s.commodityid = '").append(s2).append("'").toString();
			request.setAttribute("commodityID", s2);
		} else if (list != null && list.size() != 0) {
			String s3 = String.valueOf(((Map) list.get(0)).get("COMMODITYID"));
			s1 = (new StringBuilder()).append(s1).append("AND s.commodityid = '").append(s3).append("'").toString();
			request.setAttribute("commodityID", s3);
		}
		String s4 = request.getParameter("gnnt_settleDate");
		if (!"".equals(s4) && s4 != null) {
			s1 = (new StringBuilder()).append(s1).append(" AND s.SettleProcessDate = to_date('").append(s4).append("','yyyy-mm-dd')").toString();
		} else {
			String s5 = Tools.fmtDate(new Date());
			s1 = (new StringBuilder()).append(s1).append(" AND s.SettleProcessDate = to_date('").append(s5).append("','yyyy-mm-dd')").toString();
			request.setAttribute("settleDateDefault", s5);
		}
		s1 = (new StringBuilder()).append(s1).append(" group by s.firmid").toString();
		List list1 = getService().getListBySql(s1);
		String s6 = s1.replace("bs_flag = 1", "bs_flag = 2");
		List list2 = getService().getListBySql(s6);
		request.setAttribute("buyeralreadyPairTotalList", list1);
		request.setAttribute("selleralreadyPairTotalList", list2);
		request.setAttribute("oldParams", getParametersStartingWith(request, "gnnt_"));
		return "success";
	}

	public String getValidGageBill() throws Exception {
		logger.debug("------------ 跳转有效仓单数量列表-------------");
		List list = getService().getListBySql("select commodityId,name from t_commodity ");
		request.setAttribute("list", list);
		List list1 = billService.getFirmList();
		StringBuffer stringbuffer = new StringBuffer("[");
		for (int i = 0; i < list1.size(); i++) {
			stringbuffer.append("\"");
			stringbuffer.append(list1.get(i));
			stringbuffer.append("\",");
		}

		stringbuffer.deleteCharAt(stringbuffer.length() - 1);
		stringbuffer.append("]");
		request.setAttribute("json", stringbuffer.toString());
		String s = request.getParameter("gnnt_stock.commodityID");
		String s1 = trim(request.getParameter("gnnt_stock.firmID"));
		String s2 = "select v.commodityid,t.name,v.firmid,v.quantity,v.frozenqty from T_ValidGageBill v,t_commodity t where t.commodityid = v.commodityid  ";
		if (s1 != null && !"".equals(s1))
			s2 = (new StringBuilder()).append(s2).append(" AND v.firmID like '%").append(s1).append("%'").toString();
		if (s != null && !"".equals(s))
			s2 = (new StringBuilder()).append(s2).append(" AND v.commodityId = '").append(s).append("'").toString();
		List list2 = getService().getListBySql(s2);
		PageRequest pagerequest = super.getPageRequest(request);
		int j = RequestUtils.getPageNo(request);
		if (j <= 0)
			j = 1;
		pagerequest.setPageNumber(j);
		int k = RequestUtils.getCurrentRowsDisplayed(request);
		if (k <= 0)
			k = ECSideContext.DEFAULT_PAGE_SIZE;
		pagerequest.setPageSize(k);
		Page page = new Page(pagerequest.getPageNumber(), pagerequest.getPageSize(), list2.size(), list2);
		request.setAttribute("pageInfo", page);
		request.setAttribute("oldParams", getParametersStartingWith(request, "gnnt_"));
		return "success";
	}

	private String trim(String s) {
		if (s == null)
			return null;
		else
			return s.replaceAll("\\s", "");
	}
}