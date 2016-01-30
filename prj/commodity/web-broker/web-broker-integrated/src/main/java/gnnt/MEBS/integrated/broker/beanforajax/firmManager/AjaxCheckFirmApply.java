package gnnt.MEBS.integrated.broker.beanforajax.firmManager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.broker.beanforajax.BaseAjax;
import gnnt.MEBS.common.broker.common.Page;
import gnnt.MEBS.common.broker.common.PageRequest;
import gnnt.MEBS.common.broker.common.QueryConditions;
import gnnt.MEBS.integrated.broker.model.firmManager.MFirmApply;

@Controller("ajaxCheckFirmApply")
@Scope("request")
public class AjaxCheckFirmApply extends BaseAjax {
	private boolean getTraderUserID(String paramString) {
		List localList = getService().getListBySql("select * from M_Trader where UserID='" + paramString + "'");
		return (localList != null) && (localList.size() > 0);
	}

	private boolean getFirmApplyUserID(String paramString) {
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("userID", "=", paramString);
		localQueryConditions.addCondition("status", "<>", new Short("2"));
		PageRequest localPageRequest = new PageRequest(1, 2, localQueryConditions, "");
		Page localPage = getService().getPage(localPageRequest, new MFirmApply());
		return (localPage != null) && (localPage.getResult() != null) && (localPage.getResult().size() > 0);
	}

	private boolean getFirmApplyUserID(String paramString1, String paramString2) {
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("applyID", "!=", paramString2);
		localQueryConditions.addCondition("userID", "=", paramString1);
		localQueryConditions.addCondition("status", "<>", new Short("2"));
		PageRequest localPageRequest = new PageRequest(1, 2, localQueryConditions, "");
		Page localPage = getService().getPage(localPageRequest, new MFirmApply());
		return (localPage != null) && (localPage.getResult() != null) && (localPage.getResult().size() > 0);
	}

	public String mouseCheckUserId() {
		HttpServletRequest localHttpServletRequest = getRequest();
		String str1 = localHttpServletRequest.getParameter("fieldId");
		String str2 = localHttpServletRequest.getParameter("fieldValue");
		if (getTraderUserID(str2))
			this.jsonValidateReturn = createJSONArray(new Object[] { str1, Boolean.valueOf(false), "用户名已被其他交易员使用" });
		else if (getFirmApplyUserID(str2))
			this.jsonValidateReturn = createJSONArray(new Object[] { str1, Boolean.valueOf(false), "用户名已经有人申请" });
		else
			this.jsonValidateReturn = createJSONArray(new Object[] { str1, Boolean.valueOf(true), "用户名可以使用" });
		return "success";
	}

	public String checkFirmApplyForm() {
		HttpServletRequest localHttpServletRequest = getRequest();
		String str = localHttpServletRequest.getParameter("entity.userId");
		BaseAjax.AjaxJSONArrayResponse localAjaxJSONArrayResponse = new BaseAjax.AjaxJSONArrayResponse(this, new Object[0]);
		if (getTraderUserID(str))
			localAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "userId", Boolean.valueOf(false), "用户名已被其他交易员使用" }) });
		else if (getFirmApplyUserID(str))
			localAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "userId", Boolean.valueOf(false), "用户名已经有人申请" }) });
		else
			localAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "userId", Boolean.valueOf(true), "用户名可以使用" }) });
		this.jsonValidateReturn = localAjaxJSONArrayResponse.toJSONArray();
		return "success";
	}

	public String checkFirmApplyUpdateForm() {
		HttpServletRequest localHttpServletRequest = getRequest();
		String str1 = localHttpServletRequest.getParameter("entity.userId");
		String str2 = localHttpServletRequest.getParameter("entity.applyId");
		BaseAjax.AjaxJSONArrayResponse localAjaxJSONArrayResponse = new BaseAjax.AjaxJSONArrayResponse(this, new Object[0]);
		if (getTraderUserID(str1))
			localAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "userId", Boolean.valueOf(false), "用户名已被其他交易员使用" }) });
		else if (getFirmApplyUserID(str1, str2))
			localAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "userId", Boolean.valueOf(false), "用户名已经有人申请" }) });
		else
			localAjaxJSONArrayResponse.addJSON(new Object[] { createJSONArray(new Object[] { "userId", Boolean.valueOf(true), "用户名可以使用" }) });
		this.jsonValidateReturn = localAjaxJSONArrayResponse.toJSONArray();
		return "success";
	}
}