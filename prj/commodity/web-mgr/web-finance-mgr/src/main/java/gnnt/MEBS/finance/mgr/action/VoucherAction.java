package gnnt.MEBS.finance.mgr.action;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.finance.mgr.model.Voucher;
import gnnt.MEBS.finance.mgr.model.VoucherEntry;
import gnnt.MEBS.finance.mgr.service.VoucherService;

@Controller("voucherAction")
@Scope("request")
public class VoucherAction extends EcsideAction {
	private static final long serialVersionUID = 8469324656244040914L;

	@Autowired
	@Qualifier("voucherService")
	private VoucherService voucherService;

	@Resource(name = "voucher_statusMap")
	private Map<String, String> voucher_statusMap;
	private List<VoucherEntry> voucherEntries;

	public Map<String, String> getVoucher_statusMap() {
		return this.voucher_statusMap;
	}

	public List<VoucherEntry> getVoucherEntries() {
		return this.voucherEntries;
	}

	public void setVoucherEntries(List<VoucherEntry> voucherEntries) {
		this.voucherEntries = voucherEntries;
	}

	public String listVoucher() throws Exception {
		this.logger.debug("enter listVoucher");

		PageRequest pageRequest = getPageRequest(this.request);
		QueryConditions qc = (QueryConditions) pageRequest.getFilters();
		qc.addCondition("primary.status", "=", "editing");
		pageRequest.setSortColumns(" order by voucherno desc");
		listByLimit(pageRequest);
		return "success";
	}

	public String listVoucherConfirm() throws Exception {
		this.logger.debug("enter listVoucherConfirm");

		PageRequest pageRequest = getPageRequest(this.request);
		QueryConditions qc = (QueryConditions) pageRequest.getFilters();
		qc.addCondition("primary.status", "=", "editing");
		pageRequest.setSortColumns(" order by voucherno desc");
		listByLimit(pageRequest);
		return "success";
	}

	public String listVoucherAudit() throws Exception {
		this.logger.debug("enter listVoucherAudit");

		PageRequest pageRequest = getPageRequest(this.request);
		QueryConditions qc = (QueryConditions) pageRequest.getFilters();
		qc.addCondition("primary.status", "=", "auditing");
		pageRequest.setSortColumns(" order by voucherno desc");
		listByLimit(pageRequest);
		return "success";
	}

	public String listVoucherCurrent() throws Exception {
		this.logger.debug("enter voucherCurrentList");

		PageRequest pageRequest = getPageRequest(this.request);
		QueryConditions qc = (QueryConditions) pageRequest.getFilters();
		qc.addCondition("primary.status", "!=", "accounted");
		pageRequest.setSortColumns(" order by voucherno desc");
		listByLimit(pageRequest);

		return "success";
	}

	public String listVoucherHistory() throws Exception {
		this.logger.debug("enter listVoucherHistory");

		PageRequest pageRequest = getPageRequest(this.request);
		QueryConditions qc = (QueryConditions) pageRequest.getFilters();
		qc.addCondition("primary.status", "=", "accounted");
		String accountCode = this.request.getParameter("accountCode");
		if ((accountCode != null) && (!"".equals(accountCode))) {
			qc.addCondition(" ", " ", "(select count(c.accountCode) from primary.voucherEntries as c where c.accountCode='" + accountCode + "')>0");
			this.request.setAttribute("accountCode", accountCode);
		}
		pageRequest.setSortColumns(" order by voucherno desc");
		listByLimit(pageRequest);
		return "success";
	}

	public String addVoucher() throws Exception {
		this.logger.debug("------------addVoucher 添加凭证--------------");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		List sqlList = getService().getListBySql("select seq_f_voucher.Nextval from dual");
		BigDecimal voucherNo = (BigDecimal) ((Map) sqlList.get(0)).get("NEXTVAL");
		Voucher voucher = (Voucher) this.entity;
		voucher.setVoucherNo(Long.valueOf(voucherNo.longValue()));
		voucher.setInputTime(getService().getSysDate());
		voucher.setInputUser(user.getUserId());
		voucher.setStatus("editing");
		getService().add(voucher);
		for (VoucherEntry entry : this.voucherEntries) {
			entry.setVoucherNo(Long.valueOf(voucherNo.longValue()));
			getService().add(entry);
		}

		addReturnValue(1, 119901L);
		return "success";
	}

	public String deleteVoucher() throws Exception {
		this.logger.debug("enter deleteVoucher。。");

		String[] ids = this.request.getParameterValues("ids");
		if ((ids == null) || (ids.length == 0)) {
			throw new IllegalArgumentException("删除主键数组不能为空！");
		}

		if (this.entity == null) {
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许集合 批量删除数据！");
		}

		if ((this.entity.fetchPKey() == null) || (this.entity.fetchPKey().getKey() == null) || (this.entity.fetchPKey().getKey().length() == 0)) {
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过集合 批量删除数据！");
		}

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

		String result = this.voucherService.deleteVoucher(this.entity, objIds);

		if ((result != null) && (!result.equals(""))) {
			addReturnValue(-1, 211199L, new String[] { "凭证号：" + result + ", 删除失败！凭证状态已改变，请重新选择" });
		} else {
			addReturnValue(1, 119903L);
		}

		return "success";
	}

	public String updateVoucher() {
		this.logger.debug("----------updateVoucher   修改凭证----------------");
		for (VoucherEntry entry : this.voucherEntries) {
			getService().update(entry);
		}
		getService().update(this.entity);

		addReturnValue(1, 119902L);
		return "success";
	}

	public String addVoucherFastForward() {
		List sqlList = getService().getListBySql(
				"select v.code,v.name,v.debitCode,v.creditCode,v.needContractNo,cast(v.summaryNo as varchar(5)) as summaryNo ,s.summary from f_vouchermodel v,F_Summary s where v.summaryNo=s.SummaryNo");
		this.request.setAttribute("voucherModelList", sqlList);
		return "success";
	}

	public String addVoucherFast() throws Exception {
		this.logger.debug("------------addVoucherFast 快捷录入凭证--------------");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		String summaryNo = this.request.getParameter("summaryNo");
		String summary = this.request.getParameter("summary");
		String debitCode = this.request.getParameter("debitCode");
		String creditCode = this.request.getParameter("creditCode");
		String contractNo = this.request.getParameter("contractNo");
		String money = this.request.getParameter("money");
		double result = this.voucherService.addVoucherFast(summaryNo, summary, debitCode, creditCode, contractNo, user.getUserId(), money);
		if (result > 0.0D) {
			addReturnValue(1, 211111L);
		} else {
			addReturnValue(-1, 211112L);
		}
		return "success";
	}

	public String updateAuditOne() throws Exception {
		this.logger.debug("----------updateAuditOne   凭证确认----------------");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		String[] ids = this.request.getParameterValues("ids");
		for (String id : ids) {
			Voucher voucher = new Voucher();
			voucher.setVoucherNo(Long.valueOf(Tools.strToLong(id)));
			voucher.setStatus("auditing");
			voucher.setAuditor(user.getUserId());
			voucher.setAuditTime(getService().getSysDate());
			getService().update(voucher);
		}

		addReturnValue(1, 211103L);
		return "success";
	}

	public String updateAuditAll() throws Exception {
		this.logger.debug("----------updateAuditAll   全部凭证确认----------------");
		User user = (User) this.request.getSession().getAttribute("CurrentUser");
		List sqlList = getService().getListBySql("select * from f_voucher where status='editing'");
		for (int i = 0; i < sqlList.size(); i++) {
			BigDecimal voucherNo = (BigDecimal) ((Map) sqlList.get(i)).get("VOUCHERNO");
			Voucher voucher = new Voucher();
			voucher.setVoucherNo(Long.valueOf(voucherNo.longValue()));
			voucher.setStatus("auditing");
			voucher.setAuditor(user.getUserId());
			voucher.setAuditTime(getService().getSysDate());
			getService().update(voucher);
		}

		addReturnValue(1, 211103L);
		return "success";
	}

	public String updateAuditVoucher() throws Exception {
		this.logger.debug("----------updateAuditVoucher   凭证审核----------------");
		String[] ids = this.request.getParameterValues("ids");
		String isPass = this.request.getParameter("isPass").toString();
		try {
			for (String id : ids) {
				int result = this.voucherService.auditVoucher(Long.valueOf(Tools.strToLong(id)), Tools.strToBoolean(isPass));
				if (result == -1) {
					addReturnValue(1, 211107L);
				} else if (result == -2) {
					addReturnValue(1, 211108L);
				} else {
					Voucher voucher = new Voucher();

					User user = (User) this.request.getSession().getAttribute("CurrentUser");
					voucher.setVoucherNo(Long.valueOf(Tools.strToLong(id)));

					if (isPass.equals("false")) {
						voucher.setAuditor(user.getUserId());

						voucher.setAuditTime(getService().getSysDate());

						voucher.setStatus("editing");
					}

					voucher.setAuditor(user.getUserId());

					getService().update(voucher);

					addReturnValue(1, 211103L);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			addReturnValue(1, 211110L);
		}
		return "success";
	}

	public String updateAuditVoucher1() throws Exception {
		this.logger.debug("----------updateAuditVoucher   凭证审核----------------");
		String id = this.request.getParameter("voucherNo");
		String isPass = this.request.getParameter("isPass").toString();
		try {
			int result = this.voucherService.auditVoucher(Long.valueOf(Tools.strToLong(id)), Tools.strToBoolean(isPass));
			if (result == -1) {
				addReturnValue(1, 211107L);
			} else if (result == -2) {
				addReturnValue(1, 211108L);
			} else {
				Voucher voucher = new Voucher();

				User user = (User) this.request.getSession().getAttribute("CurrentUser");
				voucher.setVoucherNo(Long.valueOf(Tools.strToLong(id)));

				if (isPass.equals("false")) {
					voucher.setAuditor(user.getUserId());

					voucher.setAuditTime(getService().getSysDate());

					voucher.setStatus("editing");
				}

				voucher.setAuditor(user.getUserId());

				getService().update(voucher);

				addReturnValue(1, 211103L);
			}
		} catch (Exception e) {
			e.printStackTrace();

			addReturnValue(1, 211110L);
		}
		return "success";
	}

	public VoucherService getVoucherService() {
		return this.voucherService;
	}

	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}
}