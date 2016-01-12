package gnnt.MEBS.timebargain.mgr.action.settle;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.bill.core.service.IKernelService;
import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.timebargain.mgr.model.settle.ApplyAheadSettle;
import gnnt.MEBS.timebargain.mgr.model.settle.SettleProps;
import gnnt.MEBS.timebargain.mgr.service.AheadSettleService;

@Controller("aheadSettleAction")
@Scope("request")
public class AheadSettleAction extends EcsideAction {
	private static final long serialVersionUID = 5131268112367465621L;

	@Autowired
	@Qualifier("billKernelService")
	private IKernelService kernelService;

	@Autowired
	@Qualifier("aheadSettleService")
	private AheadSettleService aheadSettleService;

	@Resource(name = "Audit_statusMap")
	private Map<String, String> audit_statusMap;

	public IKernelService getKernelService() {
		return this.kernelService;
	}

	public void setKernelService(IKernelService paramIKernelService) {
		this.kernelService = paramIKernelService;
	}

	public AheadSettleService getAheadSettleService() {
		return this.aheadSettleService;
	}

	public void setAheadSettleService(AheadSettleService paramAheadSettleService) {
		this.aheadSettleService = paramAheadSettleService;
	}

	public Map<String, String> getAudit_statusMap() {
		return this.audit_statusMap;
	}

	public void setAudit_statusMap(Map<String, String> paramMap) {
		this.audit_statusMap = paramMap;
	}

	public String forwardAddAheadSettle() throws Exception {
		this.logger.debug("------------forwardAddAheadSettle 添加跳转--------------");
		String str = "select distinct(commodityid) commodityId from t_commodity order by commodityId";
		List localList1 = getService().getListBySql(str);
		List localList2 = this.aheadSettleService.getCustomerList();
		StringBuffer localStringBuffer = new StringBuffer("[");
		for (int i = 0; i < localList2.size(); i++) {
			localStringBuffer.append("\"");
			localStringBuffer.append(localList2.get(i));
			localStringBuffer.append("\",");
		}
		localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
		localStringBuffer.append("]");
		this.request.setAttribute("json", localStringBuffer.toString());
		this.request.setAttribute("list", localList1);
		this.request.setAttribute("size", Integer.valueOf(0));
		return forwardAdd();
	}

	public String getBillList() throws Exception {
		logger.debug("------------getBillList 查询仓单信息--------------");
		long l = 0L;
		List list = null;
		Page page = null;
		String s = request.getParameter("entity.commodityId");
		String s1 = request.getParameter("entity.customerId_S");
		String s2 = request.getParameter("entity.price");
		String s3 = request.getParameter("entity.customerId_B");
		String s4 = request.getParameter("entity.remark1");
		String s5 = request.getParameter("entity.quantity");
		double d = 0.0D;
		if (s5 != null)
			d = Double.parseDouble(s5);
		String s6 = request.getParameter("priceType");
		List list1 = getService()
				.getListBySql((new StringBuilder()).append("select firmId from t_customer where customerId ='").append(s1).append("'").toString());
		String s7 = "";
		double d1 = 0.0D;
		if (list1 != null && list1.size() > 0) {
			String s8 = ((Map) list1.get(0)).get("FIRMID").toString();
			List list2 = getService()
					.getListBySql((new StringBuilder()).append("select * from t_commodity where commodityID = '").append(s).append("'").toString());
			if (list2 != null && list2.size() > 0) {
				long l1 = Long.parseLong(((Map) list2.get(0)).get("BREEDID").toString());
				double d2 = Double.parseDouble(((Map) list2.get(0)).get("CONTRACTFACTOR").toString());
				String s9 = (new StringBuilder()).append("select * from T_settleProps  where commodityId = '").append(s).append("'").toString();
				List list3 = getService().getListBySql(s9, entity);
				HashMap hashmap = new HashMap();
				Object obj = null;
				SettleProps settleprops;
				for (Iterator iterator = list3.iterator(); iterator.hasNext(); hashmap.put(settleprops.getPropertyName(),
						settleprops.getPropertyValue())) {
					StandardModel standardmodel = (StandardModel) iterator.next();
					settleprops = (SettleProps) standardmodel;
				}

				List list6 = null;
				try {
					list6 = kernelService.getUnusedStocks(l1, hashmap, s8, d * d2);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				if (list6 != null && list6.size() > 0) {
					String s11 = StringUtils.join(list6, ',');
					String s12 = (new StringBuilder())
							.append("select bs.stockid stockid,bs.warehouseid warehouseid,tc.breedname breedname,bs.quantity quantity,bs.unit unit,round(bs.quantity / tc.contractfactor,2) stockNum,bs.lasttime lasttime from BI_Stock bs,(select t.BreedID BreedID, t.ContractFactor ContractFactor,e.breedname breedname from t_commodity t, m_breed e  where t.breedId = e.breedId and t.commodityID = '")
							.append(s).append("') tc ").append("where tc.breedId = bs.breedId and stockid in (").append(s11)
							.append(") order by to_number(stockid)").toString();
					list = getService().getListBySql(s12);
					if (list != null && list.size() > 0)
						page = new Page(1, list.size(), list.size(), list);
				} else {
					request.setAttribute("result", "无任何信息！");
				}
			} else {
				request.setAttribute("result", "商品代码不存在！");
			}
		} else {
			request.setAttribute("result", "客户代码不存在！");
		}
		int i = 0;
		if (list != null)
			i = list.size();
		String s10 = "select distinct(commodityid) commodityId from t_commodity order by commodityId";
		List list4 = getService().getListBySql(s10);
		request.setAttribute("list", list4);
		List list5 = aheadSettleService.getCustomerList();
		StringBuffer stringbuffer = new StringBuffer("[");
		for (int j = 0; j < list5.size(); j++) {
			stringbuffer.append("\"");
			stringbuffer.append(list5.get(j));
			stringbuffer.append("\",");
		}

		stringbuffer.deleteCharAt(stringbuffer.length() - 1);
		stringbuffer.append("]");
		request.setAttribute("json", stringbuffer.toString());
		request.setAttribute("pageInfo", page);
		request.setAttribute("size", Integer.valueOf(i));
		request.setAttribute("priceType", s6);
		request.setAttribute("commodityId", s);
		request.setAttribute("customerId_S", s1);
		request.setAttribute("quantity", s5);
		request.setAttribute("customerId_B", s3);
		request.setAttribute("price", s2);
		request.setAttribute("remark1", s4);
		return "success";
	}

	public String addTransfer() throws Exception {
		this.logger.debug("------------addTransfer 添加提前交收信息--------------");
		int i = -1;
		List localList1 = getService().getListBySql("select status from t_systemstatus");
		String str1 = ((Map) localList1.get(0)).get("STATUS").toString();
		if (str1.equals("1")) {
			String str2 = this.request.getParameter("ids");
			str2 = str2.substring(0, str2.length() - 1);
			String[] arrayOfString = str2.split(",");
			String str3 = ((User) this.request.getSession().getAttribute("CurrentUser")).getUserId();
			ApplyAheadSettle localApplyAheadSettle = (ApplyAheadSettle) this.entity;
			localApplyAheadSettle.setStatus(Integer.valueOf(1));
			localApplyAheadSettle.setCreateTime(new Date());
			localApplyAheadSettle.setCreator(str3);
			List localList2 = getService()
					.getListBySql("select firmId from t_customer where customerId ='" + localApplyAheadSettle.getCustomerId_S() + "'");
			String str4 = "";
			if ((localList2 != null) && (localList2.size() > 0))
				str4 = ((Map) localList2.get(0)).get("FIRMID").toString();
			List localList3 = getService()
					.getListBySql("select firmId from t_customer where customerId ='" + localApplyAheadSettle.getCustomerId_B() + "'");
			String str5 = "";
			if ((localList3 != null) && (localList3.size() > 0)) {
				str5 = ((Map) localList3.get(0)).get("FIRMID").toString();
				i = this.aheadSettleService.addApplyAheadSettle(arrayOfString, localApplyAheadSettle, str5, str4);
			} else {
				i = -3;
			}
		} else {
			i = -4;
		}
		if (i == 0) {
			addReturnValue(1, 119901L);
			return "success";
		}
		if (i == -2) {
			this.request.setAttribute("result", "冻结仓单失败！");
			return "error";
		}
		if (i == -3) {
			this.request.setAttribute("result", "买方二级代码不存在！");
			return "error";
		}
		if (i == -4) {
			this.request.setAttribute("result", "申请必须在闭式状态下操作");
			return "error";
		}
		if (i == -5) {
			this.request.setAttribute("result", "买方可用持仓不足");
			return "error";
		}
		if (i == -6) {
			this.request.setAttribute("result", "卖方可用持仓不足");
			return "error";
		}
		this.request.setAttribute("result", "操作失败！");
		return "error";
	}

	public String getBillListByApplyId() throws Exception {
		this.logger.debug("------------getBillListByApplyId 根据提前交收申请号查询所冻结的仓单--------------");
		ApplyAheadSettle localApplyAheadSettle = (ApplyAheadSettle) this.entity;
		String str = "select bs.stockid stockid,bs.warehouseid warehouseid,mb.breedname breedname,bs.quantity quantity,bs.unit unit,bs.lasttime lasttime from BI_Stock bs,m_breed mb where mb.breedid = bs.breedId and stockid in (select billId from T_billFrozen where Operation = '"
				+ localApplyAheadSettle.getApplyId() + "') order by to_number(stockid)";
		List localList = getService().getListBySql(str);
		PageRequest localPageRequest = super.getPageRequest(this.request);
		Page localPage = new Page(localPageRequest.getPageNumber(), localPageRequest.getPageSize(), localList.size(), localList);
		this.request.setAttribute("applyId", localApplyAheadSettle.getApplyId());
		this.request.setAttribute("pageInfo", localPage);
		return "success";
	}

	public String viewByApplyId() throws Exception {
		this.logger.debug("------------viewByApplyId 提前交收审核跳转--------------");
		this.entity = getService().get(this.entity);
		ApplyAheadSettle localApplyAheadSettle = (ApplyAheadSettle) this.entity;
		String str = "select * from BI_Stock where stockid in (select billId from T_billFrozen where Operation = '"
				+ localApplyAheadSettle.getApplyId() + "')";
		List localList = getService().getListBySql(str);
		this.request.setAttribute("list", localList);
		return "success";
	}

	public String aheadSettleAuditPass() throws Exception {
		this.logger.debug("------------aheadSettleAudit 提前交收信息审核通过--------------");
		int i = 0;
		ApplyAheadSettle localApplyAheadSettle1 = (ApplyAheadSettle) this.entity;
		ApplyAheadSettle localApplyAheadSettle2 = (ApplyAheadSettle) getService().get(localApplyAheadSettle1).clone();
		if (localApplyAheadSettle2.getStatus().intValue() == 1) {
			String str1 = ((User) this.request.getSession().getAttribute("CurrentUser")).getUserId();
			String str2 = localApplyAheadSettle1.getRemark2();
			localApplyAheadSettle2.setStatus(Integer.valueOf(2));
			localApplyAheadSettle2.setModifier(str1);
			localApplyAheadSettle2.setRemark2(str2);
			localApplyAheadSettle2.setModifyTime(new Date());
			i = this.aheadSettleService.auditApplyAheadSettlePass(localApplyAheadSettle2);
		} else {
			i = -5;
		}
		if (i == 1) {
			addReturnValue(1, 119907L);
			return "success";
		}
		if (i == 2) {
			this.request.setAttribute("result", "已处理过！");
			return "error";
		}
		if (i == -1) {
			this.request.setAttribute("result", "可交收买持仓数量不足！");
			return "error";
		}
		if (i == -2) {
			this.request.setAttribute("result", "可交收买抵顶数量不足！");
			return "error";
		}
		if (i == -3) {
			this.request.setAttribute("result", "交收买持仓数量大于可交收买持仓数量！");
			return "error";
		}
		if (i == -4) {
			this.request.setAttribute("result", "交收买抵顶数量大于可买抵顶数量！");
			return "error";
		}
		if (i == -11) {
			this.request.setAttribute("result", "可交收卖持仓数量不足！");
			return "error";
		}
		if (i == -12) {
			this.request.setAttribute("result", "可交收卖抵顶数量不足！");
			return "error";
		}
		if (i == -13) {
			this.request.setAttribute("result", "交收卖持仓数量大于可交收卖持仓数量！");
			return "error";
		}
		if (i == -14) {
			this.request.setAttribute("result", "交收卖抵顶数量大于可卖抵顶数量！");
			return "error";
		}
		if (i == -100) {
			this.request.setAttribute("result", "其它错误！");
			return "error";
		}
		if (i == -5) {
			this.request.setAttribute("result", "状态已变更，审核失败！");
			return "error";
		}
		if (i == -6) {
			this.request.setAttribute("result", "解冻仓单失败！");
			return "error";
		}
		this.request.setAttribute("result", "操作失败！");
		return "error";
	}

	public String aheadSettleAuditFail() throws Exception {
		this.logger.debug("------------aheadSettleAudit 提前交收信息审核不通过--------------");
		int i = 0;
		ApplyAheadSettle localApplyAheadSettle1 = (ApplyAheadSettle) this.entity;
		ApplyAheadSettle localApplyAheadSettle2 = (ApplyAheadSettle) getService().get(localApplyAheadSettle1);
		if (localApplyAheadSettle2.getStatus().intValue() == 1) {
			String str1 = ((User) this.request.getSession().getAttribute("CurrentUser")).getUserId();
			String str2 = localApplyAheadSettle1.getRemark2();
			localApplyAheadSettle2.setStatus(Integer.valueOf(3));
			localApplyAheadSettle2.setModifier(str1);
			localApplyAheadSettle2.setRemark2(str2);
			localApplyAheadSettle2.setModifyTime(new Date());
			i = this.aheadSettleService.auditApplyAheadSettleFail(localApplyAheadSettle2);
		} else {
			i = -5;
		}
		if (i == 1) {
			addReturnValue(1, 119907L);
			return "success";
		}
		if (i == 2) {
			this.request.setAttribute("result", "已处理过！");
			return "error";
		}
		if (i == -1) {
			this.request.setAttribute("result", "可交收买持仓数量不足！");
			return "error";
		}
		if (i == -2) {
			this.request.setAttribute("result", "可交收买抵顶数量不足！");
			return "error";
		}
		if (i == -3) {
			this.request.setAttribute("result", "交收买持仓数量大于可交收买持仓数量！");
			return "error";
		}
		if (i == -4) {
			this.request.setAttribute("result", "交收买抵顶数量大于可买抵顶数量！");
			return "error";
		}
		if (i == -11) {
			this.request.setAttribute("result", "可交收卖持仓数量不足！");
			return "error";
		}
		if (i == -12) {
			this.request.setAttribute("result", "可交收卖抵顶数量不足！");
			return "error";
		}
		if (i == -13) {
			this.request.setAttribute("result", "交收卖持仓数量大于可交收卖持仓数量！");
			return "error";
		}
		if (i == -14) {
			this.request.setAttribute("result", "交收卖抵顶数量大于可卖抵顶数量！");
			return "error";
		}
		if (i == -100) {
			this.request.setAttribute("result", "其它错误！");
			return "error";
		}
		if (i == -5) {
			this.request.setAttribute("result", "状态已变更，审核失败！");
			return "error";
		}
		if (i == -6) {
			this.request.setAttribute("result", "解冻仓单失败！");
			return "error";
		}
		this.request.setAttribute("result", "操作失败！");
		return "error";
	}
}