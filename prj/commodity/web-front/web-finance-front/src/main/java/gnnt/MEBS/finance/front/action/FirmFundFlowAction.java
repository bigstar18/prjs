package gnnt.MEBS.finance.front.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.front.action.StandardAction;
import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.common.QueryConditions;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.statictools.ActionUtil;
import gnnt.MEBS.common.front.statictools.Tools;
import gnnt.MEBS.finance.front.model.FundFlowF;
import gnnt.MEBS.finance.front.model.FundFlowHisF;
import gnnt.MEBS.finance.front.model.SummaryF;
import gnnt.MEBS.finance.front.model.SystemModule;

@Controller("firmFundFlowAction")
@Scope("request")
public class FirmFundFlowAction extends StandardAction {
	private static final long serialVersionUID = -6968862613594888662L;

	public String fundFlowList() throws Exception {
		User localUser = (User) this.request.getSession().getAttribute("CurrentUser");
		this.logger.debug("交易员代码firmid=[" + localUser.getBelongtoFirm().getFirmID() + "]");

		String type = this.request.getParameter("type");
		if ((type != null) && (type.equals("H"))) {
			this.entity = new FundFlowHisF();
			type = "H";
		} else {
			this.entity = new FundFlowF();
			type = "D";
		}
		this.request.setAttribute("type", type);

		PageRequest pageRequest = null;
		try {
			pageRequest = ActionUtil.getPageRequest(this.request);
			((QueryConditions) pageRequest.getFilters()).addCondition("firmId", "=", localUser.getBelongtoFirm().getFirmID());
		} catch (Exception localException) {
			this.logger.error(Tools.getExceptionTrace(localException));
			// 不return？
			return "error";
		}

		String balance = this.request.getParameter("balance");
		if ((balance != null) && (!balance.equals(""))) {
			String str3 = "=";
			if (balance.equals("B"))
				str3 = ">";
			else if (balance.equals("S"))
				str3 = "<";
			this.request.setAttribute("balance", balance);
			((QueryConditions) pageRequest.getFilters()).addCondition("balance", str3, Double.valueOf(Double.parseDouble("0")));
		}

		String amount = this.request.getParameter("amount");
		if ((amount != null) && (!amount.equals(""))) {
			this.request.setAttribute("amount", amount);
			// ((QueryConditions) pageRequest.getFilters()).addCondition("balance", "=", Double.valueOf(Double.parseDouble(amount)));................
			((QueryConditions) pageRequest.getFilters()).addCondition("amount", "=", Double.valueOf(Double.parseDouble(amount)));
		}

		Page page = getService().getPage(pageRequest, this.entity);
		this.request.setAttribute("pageInfo", page);
		this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));

		PageRequest localPageRequest2 = new PageRequest(" ");
		Page localPage2 = getService().getPage(localPageRequest2, new SystemModule());
		this.request.setAttribute("moduleList", localPage2.getResult());

		PageRequest localPageRequest3 = new PageRequest(" ");
		localPageRequest3.setPageSize(1000);
		Page localPage3 = getService().getPage(localPageRequest3, new SummaryF());
		this.request.setAttribute("summaryList", localPage3.getResult());

		return "success";
	}
}