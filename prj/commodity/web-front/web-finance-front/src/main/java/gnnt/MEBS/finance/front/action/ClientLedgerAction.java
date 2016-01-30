package gnnt.MEBS.finance.front.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.front.action.StandardAction;
import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.common.QueryConditions;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.statictools.ActionUtil;
import gnnt.MEBS.common.front.statictools.Tools;
import gnnt.MEBS.finance.front.model.LedgerField;
import gnnt.MEBS.finance.front.model.SystemModule;

@Controller("clientLedgerAction")
@Scope("request")
public class ClientLedgerAction extends StandardAction {
	private static final long serialVersionUID = 2008642717200489838L;

	public String fundLedger() {
		this.logger.debug("enter fundLedger");

		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		PageRequest localPageRequest1 = null;
		try {
			localPageRequest1 = ActionUtil.getPageRequest(this.request);
		} catch (Exception localException) {
			localException.printStackTrace();
			return "error";// hxx
		}
		((QueryConditions) localPageRequest1.getFilters()).addCondition("firmId", "=", localUser.getBelongtoFirm().getFirmID());

		String beginDate = this.request.getParameter("beginDate");
		String endDate = this.request.getParameter("endDate");
		if ((beginDate != null) && (!beginDate.equals(""))) {
			Date localObject = Tools.strToDate(new SimpleDateFormat("yyyy-MM-dd"), beginDate);
			((QueryConditions) localPageRequest1.getFilters()).addCondition("b_Date", ">=", localObject);
		}
		if ((endDate != null) && (!endDate.equals(""))) {
			Date localObject = Tools.strToDate(new SimpleDateFormat("yyyy-MM-dd"), endDate);
			((QueryConditions) localPageRequest1.getFilters()).addCondition("b_Date", "<=", localObject);
		}
		if (((beginDate == null) || (beginDate.equals(""))) && ((endDate == null) || (endDate.equals("")))) {
			beginDate = endDate = Tools.fmtDate(new Date());
			((QueryConditions) localPageRequest1.getFilters()).addCondition("primary.b_Date", ">=",
					Tools.strToDate(new SimpleDateFormat("yyyy-MM-dd"), beginDate));
			((QueryConditions) localPageRequest1.getFilters()).addCondition("primary.b_Date", "<=",
					Tools.strToDate(new SimpleDateFormat("yyyy-MM-dd"), endDate));
		}
		this.request.setAttribute("beginDate", beginDate);
		this.request.setAttribute("endDate", endDate);

		Object pageInfo = getService().getPage(localPageRequest1, this.entity);
		this.request.setAttribute("pageInfo", pageInfo);
		this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));

		String tmp = "";
		String moduleId = this.request.getParameter("moduleId");
		if ((moduleId != null) && (!"".equals(moduleId)))
			tmp = " and moduleId=" + moduleId;
		String sql = "select t.* from f_ledgerfield t where 1=1 " + tmp + " order by t.ordernum";
		List localList = getService().getListBySql(sql, new LedgerField());
		this.logger.debug("大小:" + localList.size());
		this.request.setAttribute("fieldList", localList);
		this.request.setAttribute("moduleId", moduleId);

		PageRequest localPageRequest2 = new PageRequest(" ");
		Page localPage = getService().getPage(localPageRequest2, new SystemModule());
		this.request.setAttribute("moduleList", localPage.getResult());

		return "success";
	}
}